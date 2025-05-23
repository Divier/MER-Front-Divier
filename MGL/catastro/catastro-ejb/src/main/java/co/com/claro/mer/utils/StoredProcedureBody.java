package co.com.claro.mer.utils;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.ProcedureType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.utils.enums.SchemesMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.EntityManagerUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStruct;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Struct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * DTO encargado de mapear los datos para procesar la ejecución de procedimientos almacenados.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/01/26
 */
@Log4j2
@Getter
@ToString
class StoredProcedureBody {

    private static final String EL_ATRIBUTO = "El atributo ";
    /**
     * Nombre del procedimiento a ejecutar.
     */
    @Setter
    private String procedureName;

    /**
     * Definición de parámetros de entrada
     */
    private final Map<String, Class<?>> parametersInput;

    /**
     * Definición de parámetros de salida
     */
    private final Map<String, Class<?>> parametersOutput;

    /**
     * Data para asignar a parámetros de entrada definidos.
     */
    private final Map<String, Object> inputData;

    /**
     * Inicia la instancia para ejecutar un procedimiento almacenado.
     *
     * @param procedureName {@link String} Nombre del procedimiento a ejecutar.
     * @author Gildardo Mora
     */
    public StoredProcedureBody(String procedureName) {
        this.procedureName = procedureName;
        parametersInput = new HashMap<>();
        parametersOutput = new HashMap<>();
        inputData = new HashMap<>();
    }

    /**
     * Permite agregar un nuevo parámetro de entrada en la lista que procesará el procedimiento almacenado.
     *
     * @param parameterName {@link String} Nombre del parámetro de entrada que se va a agregar.
     * @param classType     {@link Class} Tipo de clase al que pertenece el parámetro agregado.
     * @author Gildardo Mora
     */
    private void addInputParameter(String parameterName, Class<?> classType) {
        parametersInput.putIfAbsent(parameterName, classType);
    }

    /**
     * Permite agregar un nuevo parámetro de salida en la lista que procesará el procedimiento almacenado.
     *
     * @param parameterName {@link String} Nombre del parámetro de salida que se va a agregar.
     * @param classType     {@link Class} Tipo de clase al que pertenece el parámetro agregado.
     * @author Gildardo Mora
     */
    private void addOutputParameter(String parameterName, Class<?> classType) {
        parametersOutput.putIfAbsent(parameterName, classType);
    }

    /**
     * Permite agregar el valor de entrada a un parámetro específico que será procesado por el procedimiento almacenado.
     *
     * @param parameterName {@link String} Nombre del parámetro de entrada al cual se asignara el valor.
     * @param value         {@link Object} Valor que se asignará al parámetro de entrada especificado.
     * @author Gildardo Mora
     */
    private void addInputData(String parameterName, Object value) {
        inputData.putIfAbsent(parameterName, value);
    }

    /**
     * Permite limpiar el mapa de los datos cuando es necesario procesar una insercion en un bucle.
     * @author Johan Gómez
     */
    public void clearInputData() {
        inputData.clear();
    }

    /**
     * <p>
     *     Este método se encarga de procesar los datos de entrada de un procedimiento almacenado, para ello se debe
     *     anotar los atributos de la clase DTO con la anotación {@link StoredProcedureData} y posteriormente
     *     invocar este método.
     *     <br>
     *     <b>Ejemplo:</b>
     *     <br>
     *     <pre>
     *         {@code
     *         @StoredProcedureData(parameterName = "p_id", parameterType = Long.class)
     *         private Long id;
     *         }
     *
     *         <b>En el ejemplo anterior se define un atributo de clase llamado id, el cual
     *         Será procesado por el procedimiento almacenado, el nombre del parámetro de
     *         entrada será p_id y el tipo de dato será Long.</b>
     *
     *         <b>Para agregar los datos de entrada al procedimiento almacenado se debe hacer
     *         de la siguiente manera:</b>
     *         {@code
     *         StoredProcedureUtil  storedProcedureUtil= new StoredProcedureUtil();
     *         storedProcedureUtil.addRequestData(requestDto);
     *         }
     *      </pre>
     *      <b>Nota:</b> El nombre del atributo debe coincidir con el nombre del parámetro de entrada del procedimiento
     *      almacenado.
     *      <br>
     *      <b>Nota:</b> El tipo de dato del atributo debe coincidir con el tipo de dato del parámetro de entrada del
     *      procedimiento almacenado.
     *  </p>
     * @param requestDto {@link Object} Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @see StoredProcedureData Anotación para definir los datos de entrada y salida de un procedimiento almacenado.
     * @see ProcedureType Anotación para enviar clases personalizadas a los procedimientos almacenados representando
     * los Types de la base de datos Oracle.
     * @see StoredProcedureUtil Clase encargada de procesar la ejecución de procedimientos almacenados.
     * @throws ApplicationException Excepción que controla los errores en el aplicativo MER.
     * @author Manuel Hernández
     */
    public void addRequestData(Object requestDto) throws ApplicationException {
        for (Field field : requestDto.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(StoredProcedureData.class)) {
                throw new ApplicationException(EL_ATRIBUTO + field.getName() + " no tiene la anotación @StoredProcedureData"
                        + " requerida para identificar los parámetros de entrada del procedimiento almacenado " + getProcedureName());
            }

            if (field.isAnnotationPresent(ProcedureType.class)) {
                processDataOracleType(requestDto, field);
                return;
            }

            procesarStoredProcedureData(requestDto, field);
        }
    }

    /**
     * Procesa los datos de entrada de un procedimiento almacenado que recibe elementos de tipo StoredProcedureData
     *
     * @param requestDto Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @param field Atributo de la clase DTO
     * @throws ApplicationException Excepción que controla los errores en el aplicativo MER.
     * @author Gildardo Mora
     */
    private void procesarStoredProcedureData(Object requestDto, Field field) throws ApplicationException {
        try {
            StoredProcedureData annotation = field.getAnnotation(StoredProcedureData.class);
            addInputParameter(annotation.parameterName(), annotation.parameterType());
            Method getter = obtainGet(field, requestDto.getClass());
            addInputData(annotation.parameterName(), getter.invoke(requestDto));
        } catch (IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new ApplicationException("Error al obtener los datos de entrada del procedimiento almacenado.", e);
        }
    }

    /**
     * Procesa los datos de entrada de un procedimiento almacenado que recibe elementos de tipo OracleType
     * @param requestDto Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @param field    Atributo de la clase DTO
     * @throws ApplicationException Excepción que controla los errores en el aplicativo MER.
     * @author Gildardo Mora
     */
    private void processDataOracleType(Object requestDto, Field field) throws ApplicationException {
        EntityManager em = null;

        try {
            em = EntityManagerUtils.getEntityManager(SchemesMerEnum.STORED_PROCEDURE, this.getClass());                        
            processOracleType(requestDto, field, em);
        } catch (Exception e) {
            String msgError = "Error al procesar los datos de entrada del procedimiento almacenado. " + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        } finally {
            if (Objects.nonNull(em) && em.isOpen()) {
                em.clear();
                em.close();
            }
        }
    }

    /**
     * Procesa los datos de tipo OracleType
     *
     * @param requestDto Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @param field Atributo de la clase DTO
     * @param em EntityManager para obtener la conexión a la base de datos
     * @throws ApplicationException Excepción que controla los errores en el aplicativo MER.
     * @author Gildardo Mora
     */
    private void processOracleType(Object requestDto, Field field, EntityManager em) throws ApplicationException {
        try (Connection connection = em.unwrap(Connection.class)) {                                 
            ProcedureType procedureType = field.getAnnotation(ProcedureType.class);
            String nameOfType = "MGL_SCHEME." + procedureType.nameOfType();
            String nameOfListType = StringUtils.isNotBlank(procedureType.nameOfListType())
                    ? "MGL_SCHEME." + procedureType.nameOfListType() : StringUtils.EMPTY;
            OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);
            @SuppressWarnings("deprecation")
            StructDescriptor structDescriptor = StructDescriptor.createDescriptor(nameOfType, oracleConnection);
            Method getter = obtainGet(field, requestDto.getClass());
            Object internalRequestObject = getter.invoke(requestDto);

            if (StringUtils.isNotBlank(nameOfListType)) {
                procesarDataListType(field, internalRequestObject, structDescriptor, oracleConnection, nameOfListType);
                return;
            }

            processDataOracleType(field, internalRequestObject, structDescriptor, oracleConnection);
        } catch (Exception e) {
            String msgError = "Error al agregar la data del request del OracleType al procedimiento almacenado.";
            LOGGER.error(msgError, e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Procesa los datos de la lista de tipo OracleType
     *
     * @param field Atributo de la clase DTO
     * @param invoke Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @param structDescriptor Descriptor del tipo de dato Oracle
     * @param oracleConnection Conexión a la base de datos
     * @author Gildardo Mora
     */
    @SuppressWarnings("deprecation")
    private void processDataOracleType(Field field, Object invoke, StructDescriptor structDescriptor,
                                       OracleConnection oracleConnection) throws ApplicationException {
        try {
            int numberOfAttributes = invoke.getClass().getDeclaredFields().length;
            Object[] values = new Object[numberOfAttributes];

            for (int i = 0; i < numberOfAttributes; i++) {
                Method method = obtainGet(invoke.getClass().getDeclaredFields()[i], invoke.getClass());
                Object invoke1 = method.invoke(invoke);
                values[i] = invoke1;
            }

            @SuppressWarnings("deprecation")
            STRUCT struct = new STRUCT(structDescriptor, oracleConnection, values);
            StoredProcedureData storedProcedureData = field.getAnnotation(StoredProcedureData.class);
            addInputParameter(storedProcedureData.parameterName(), OracleStruct.class);
            addInputData(storedProcedureData.parameterName(), struct);
        } catch (Exception e) {
            String msgError = "Error al agregar la data del request del OracleType al procedimiento almacenado. " + e.getMessage();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Procesa los datos de tipo lista
     *
     * @param field Atributo de la clase DTO
     * @param internalRequestObject Objeto que contiene los datos de entrada para el procedimiento almacenado.
     * @param structDescriptor Descriptor del tipo de dato Oracle
     * @param oracleConnection Conexión a la base de datos
     * @param nameOfListType Nombre del type Oracle que representa la lista
     * @author Gildardo Mora
     */
    @SuppressWarnings("deprecation")
    private void procesarDataListType(Field field, Object internalRequestObject, StructDescriptor structDescriptor,
                                      OracleConnection oracleConnection, String nameOfListType) throws ApplicationException {
        try {
            if (!(internalRequestObject instanceof List)) {
                throw new ApplicationException(EL_ATRIBUTO + field.getName() + " no es una lista."
                        + " Pero esta anotado con @ProcedureType con valor en nameOfListType. "
                        + "Por favor verifique la anotación del atributo."
                );
            }

            List<?> list = (List<?>) internalRequestObject;
            Struct[] structs = new Struct[list.size()];

            for (int i = 0; i < list.size(); i++) { // recorre los elementos de la lista
                Object objetoDeLista = list.get(i);
                Object[] values = new Object[objetoDeLista.getClass().getDeclaredFields().length];

                for (int j = 0; j < values.length; j++) {// recorre los elementos del objeto
                    Method method = obtainGet(objetoDeLista.getClass().getDeclaredFields()[j], objetoDeLista.getClass());
                    Object invoke1 = method.invoke(objetoDeLista);
                    values[j] = invoke1;
                }

                structs[i] = new STRUCT(structDescriptor, oracleConnection, values);
            }

            OracleArray oracleArray = (OracleArray) oracleConnection.createOracleArray(nameOfListType, structs);
            StoredProcedureData storedProcedureData = field.getAnnotation(StoredProcedureData.class);
            addInputParameter(storedProcedureData.parameterName(), OracleArray.class);
            addInputData(storedProcedureData.parameterName(), oracleArray);
        } catch (Exception e) {
            String msgError = "Error al agregar la dataList del request del OracleType al procedimiento almacenado.";
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * <p>
     *      Permite insertar parametros de entrada y su valor al procedimiento almacenado sin el uso de DTO.
     *      <br>
     *      <b>Ejemplo:</b>
     *      <br>
     *      <pre>
     *          {@code
     *          StoredProcedureUtil  storedProcedureUtil= new StoredProcedureUtil();
     *          storedProcedureUtil.addRequestData("p_id", Long.class, 1L);
     *           }
     *     </pre>
     *     <b>Nota:</b> El nombre del atributo debe coincidir con el nombre del parámetro de entrada del procedimiento
     *     almacenado.
     *     <br>
     * </p>
     * @param parameterName Nombre del parámetro de entrada al cual se asignara el valor.
     * @param parameterType Tipo de clase al que pertenece el parámetro agregado.
     * @param value        Valor que se asignará al parámetro de entrada especificado.
     * @author Manuel Hernández
     */
    public void addRequestData(String parameterName, Class<?> parameterType, Object value) {
        addInputParameter(parameterName, parameterType);
        addInputData(parameterName, value);

    }

    /**
     * <p>
     *     Este método se encarga de procesar los datos de salida de un procedimiento almacenado, para ello se debe
     *     anotar los atributos de la clase DTO con la anotación {@link StoredProcedureData} y {@link CursorType}
     *     (cuando se asocia a un cursor) posteriormente invocar este método.
     *     <br>
     *     <b>Ejemplo:</b>
     *     <br>
     *     <pre>
     *         {@code
     *         @StoredProcedureData(parameterName = "PO_ID", parameterType = Long.class)
     *         private Long id;
     *         }
     *
     *         <b>En el ejemplo anterior se define un atributo de clase llamado id, el cual
     *         será procesado por el procedimiento almacenado, el nombre del parámetro de
     *         salida será PO_ID y el tipo de dato será Long.</b>
     *
     *         {@code
     *           @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = List.class)
     *           @CursorType(type = TipoCursorDto.class)
     *           private List<TipoCursorDto> informacionCursorList;
     *         }
     *
     *         <b>En el ejemplo anterior se define un cursor de clase llamado informacionCursorList, el cual
     *         será procesado por el procedimiento almacenado, el nombre del parámetro de
     *         salida será PO_CURSOR y sera de tipo List. Ademas esta lista sera del tipo TipoCursorDto
     *         la cual representa la firma del cursor</b>
     *
     *         <b>Nota:</b> El nombre del atributo debe coincidir con el nombre del parámetro de salida del procedimiento
     *         almacenado.
     *
     *         <b>Nota:</b> El tipo de dato del atributo debe coincidir con el tipo de dato del parámetro de salida del
     *         procedimiento almacenado.
     *         <br>
     *     </pre>
     * </p>
     * @param responseDto {@link Class<T>} Objeto que contiene los datos de salida para el procedimiento almacenado.
     * @see StoredProcedureData Anotación para definir los datos de entrada y salida de un procedimiento almacenado.
     * @see CursorType Anotación para definir el tipo de cursor que se obtendrá como resultado de la ejecución de un procedimiento almacenado.
     * @see StoredProcedureUtil Clase encargada de procesar la ejecución de procedimientos almacenados.
     * @throws ApplicationException Excepción que controla los errores en el aplicativo MER.
     * @author Manuel Hernández
     */
    protected <T> void addResponseData(Class<T> responseDto) throws ApplicationException {

        for (Field field : responseDto.getDeclaredFields()) {

            if (!field.isAnnotationPresent(StoredProcedureData.class)) {
                throw new ApplicationException(EL_ATRIBUTO + field.getName() + " no tiene la anotación " +
                        StoredProcedureData.class.getName() + " para definir los datos de salida del procedimiento almacenado.");
            }

            StoredProcedureData annotation = field.getAnnotation(StoredProcedureData.class);
            addOutputParameter(annotation.parameterName(), annotation.parameterType());
        }
    }

    /**
     * <p>
     *     Obtiene el método get del atributo de la clase para asignar el valor al parámetro de entrada del procedimiento almacenado.
     * </p>
     * @param field  Atributo de la clase
     * @param tClass Clase del atributo
     * @param <T>   Tipo de dato de la clase
     * @return Método get del atributo
     * @throws NoSuchMethodException Excepción lanzada al no encontrar el método get del atributo
     * @author Manuel Hernández
     */
    private <T> Method obtainGet(Field field, Class<T> tClass) throws NoSuchMethodException {
        String name = field.getName();
        String nameMethod = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return tClass.getDeclaredMethod(nameMethod);

    }

    /**
     * <p>
     *     Obtiene el método set del atributo de la clase para asignar el valor al parámetro de salida del procedimiento almacenado.
     * </p>
     * @param field Atributo de la clase
     * @param tClass Clase del atributo
     * @param parameterType Tipo de dato del parámetro
     * @param <T> Tipo de dato de la clase
     * @return Método set del atributo
     * @throws NoSuchMethodException Excepción lanzada al no encontrar el método set del atributo
     */
    protected <T> Method obtainSett(Field field, Class<T> tClass, Class<?> parameterType) throws NoSuchMethodException {
        String name = field.getName();
        String nameMethod = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return tClass.getDeclaredMethod(nameMethod, parameterType);

    }

}
