package co.com.claro.mer.utils;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.utils.enums.SchemesMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * Utilitario para realizar ejecución de procedimientos almacenados
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/01/26
 */
public class StoredProcedureUtil extends StoredProcedureBody {

    private static final Logger LOGGER = LogManager.getLogger(StoredProcedureUtil.class);
    private static final String DOT = ".";

    /**
     * Inicia la instancia para ejecutar un procedimiento almacenado.
     *
     * @param procedureName {@link String} Nombre del procedimiento almacenado que se va a ejecutar.
     */
    public StoredProcedureUtil(String procedureName) {
        super(procedureName);
    }

    /**
     * Realiza la ejecución del procedimiento almacenado, devolviendo el resultado de la ejecución en el DTO recibido.
     *
     * @param dtoOutPut {@link Class<T>} Clase DTO que se desea retornar con los valores asignados del resultado de la ejecución del procedimiento.
     * @return {@link T} Retorna el objeto del DTo recibido, con los valores de respuesta del procedimiento asignados en los atributos correspondientes.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public <T> T executeStoredProcedure(Class<T> dtoOutPut) throws ApplicationException {
        return executeStoredProcedure(dtoOutPut, SchemesMerEnum.MGL_SCHEME);
    }

    /**
     * Realiza la ejecución del procedimiento almacenado, devolviendo el resultado de la ejecución en el DTO recibido.
     *
     * @param dtoOutPut {@link Class<T>} Clase DTO que se desea retornar con los valores asignados del resultado de la ejecución del procedimiento.
     * @param scheme    {@link SchemesMerEnum} El esquema sobre el que se desea ejecutar el procedimiento.
     * @return {@link T} Retorna el objeto del DTo recibido, con los valores de respuesta del procedimiento asignados en los atributos correspondientes.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    public <T> T executeStoredProcedure(Class<T> dtoOutPut, SchemesMerEnum scheme) throws ApplicationException{
        addResponseData(dtoOutPut);
        T instancia;
        try {
            instancia = dtoOutPut.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            String msgError = "Error al instanciar el DTO "+ dtoOutPut +"para la respuesta del procedimiento "+ getProcedureName();
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
        return generateProcedureResponse(scheme, instancia);
    }

    /**
     * Realiza la ejecución del procedimiento almacenado, devolviendo el resultado de la ejecución en el DTO recibido.
     *
     * @param scheme    {@link SchemesMerEnum} El esquema que se desea ejecutar.
     * @param dtoOutPut {@link T} DTO que se desea retornar con los valores asignados.
     * @see StoredProcedureUtil#getResultMap(SchemesMerEnum) Se ejecuta el metodo para obtener el resultado del procedimiento.
     * @return {@link T} Retorna el objeto del DTo recibido, con los valores de respuesta del procedimiento asignados en los atributos correspondientes.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Manuel Hernández
     */
    private <T> T generateProcedureResponse(SchemesMerEnum scheme, T dtoOutPut) throws ApplicationException {

        Map<String, Object> results = getResultMap(scheme);
        AtomicReference<T> objectOutput = new AtomicReference<>(dtoOutPut);

        for (Field field : objectOutput.get().getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(StoredProcedureData.class)) {
                StoredProcedureData annotation = field.getAnnotation(StoredProcedureData.class);

                try {
                    if (field.isAnnotationPresent(CursorType.class)){
                        CursorType cursorType = field.getAnnotation(CursorType.class);
                        @SuppressWarnings("unchecked")
                        List<Object[]> dataList = (List<Object[]>) results.get(annotation.parameterName());
                        Method setter = obtainSett(field, objectOutput.get().getClass(), field.getType());
                        setter.invoke(objectOutput.get(), dataList == null ? null : getCursorData(dataList, cursorType.type()));
                    }else{
                        Method setter = obtainSett(field, objectOutput.get().getClass(), annotation.parameterType());
                        setter.invoke(objectOutput.get(), results.get(annotation.parameterName()));
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    String msgError = "Error al asignar los valores de respuesta del procedimiento almacenado."+ getProcedureName();
                    LOGGER.error(msgError, e);
                    throw new ApplicationException(msgError, e);
                }
            }
        }
        return objectOutput.get();
    }

    /**
     * <p>Asigan los datos de salida de un cursor a una lista de un tipo generico definido.</p>
     *
     * @param dataList  Lista de objetos que se obtienen del cursor
     * @param dtoOutPut Clase del DTO de salida
     * @param <T>      Tipo de dato del DTO de salida
     * @return Lista de objetos del DTO de salida para el cursor
     * @throws NoSuchMethodException Cuanno no se encuentra el método para asignar el valor
     * @throws InvocationTargetException Cuando no se puede invocar el método
     * @throws ApplicationException Cuando ocurre un error en la aplicación
     * @author Manuel Hernández
     */
    private <T> List<T> getCursorData(List<Object[]> dataList, Class<T> dtoOutPut)
            throws NoSuchMethodException, InvocationTargetException, ApplicationException {

        List<T> lista = new ArrayList<>();
        for (Object[] objects : dataList) {
            try {
                T instancia = dtoOutPut.getDeclaredConstructor().newInstance();
                int i = 0;
                if (isJavaClass(instancia.getClass())){
                    instancia = dtoOutPut.getDeclaredConstructor(dtoOutPut).newInstance(objects[i]);
                }else{
                    for (Field field : instancia.getClass().getDeclaredFields()){
                        if(!Modifier.isTransient(field.getModifiers())) {
                            Optional<Object> objeto = castearObjeto(objects[i++], field.getType());
                            Method setter = obtainSett(field, instancia.getClass(), field.getType());
                            setter.invoke(instancia, objeto.orElse(null));
                        }
                    }
                }
                lista.add(instancia);
            } catch (InstantiationException | IllegalAccessException e) {
                String msgError = "Error al asignar los valores de salida al cursor";
                LOGGER.error(msgError, e);
                throw new ApplicationException(msgError, e);
            }
        }
        return lista;
    }

    /**
     * <p>Valida si una clase pertenece a los paquetes del JDK</p>
     * @param clazz    Clase a validar
     * @return {@code boolean} Retorna {@code true} si la clase es de java, {@code false} en caso contrario.
     * @author Manuel Hernández
     */
    private boolean isJavaClass(Class<?> clazz) {
        return clazz.getPackage().getName().startsWith("java");
    }

    /**
     * Realiza la ejecución del procedimiento almacenado, definiendo el esquema a trabajar en MER.
     *
     * @param scheme {@link SchemesMerEnum} El esquema a trabajar en MER.
     * @return {@code Map<String, Object>} Retorna el resultado de la ejecución en un Map.
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Gildardo Mora
     */
    private Map<String, Object> getResultMap(SchemesMerEnum scheme) throws ApplicationException {
        EntityManager entityManager = null;

        if (StringUtils.isBlank(super.getProcedureName())) {
            throw new ApplicationException("No se asignó el nombre del procedimiento a ejecutar.");
        }

        try {
            String nameProcedure = scheme.getNombreEsquema() + DOT + super.getProcedureName();
            Map<String, Class<?>> parametersInput = super.getParametersInput();
            Map<String, Class<?>> parametersOutput = super.getParametersOutput();
            entityManager = EntityManagerUtils.getEntityManager(scheme, this.getClass());
            StoredProcedureQuery spQuery = entityManager.createStoredProcedureQuery(nameProcedure);
            registerInputParameters(parametersInput, spQuery);
            registerOutputParameters(parametersOutput, spQuery);
            assignInputData(parametersInput, spQuery);
            spQuery.execute();
            return assignOutput(parametersOutput, spQuery);

        } catch (NoResultException nre) {
            String msgWarn = "No hubo resultados en la ejecución de " + super.getProcedureName();
            LOGGER.warn(msgWarn);
            return new HashMap<>();

        } catch (Exception e) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            String msgError = "Ocurrió un error en: " + ClassUtils.getCurrentMethodName(this.getClass())
                    + " llamado desde " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + StringUtils.SPACE + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);

        } finally {
            if (Objects.nonNull(entityManager) && entityManager.isOpen()) {
                entityManager.clear();
                entityManager.close();
            }
        }
    }

    /**
     * Asigna los datos de salida obtenidos por la respuesta del procedimiento almacenado.
     *
     * @param parametersOutput {@code Map<String, Class>}
     * @param spQuery          {@link StoredProcedureQuery}
     * @return {@code Map<String, Object>} Retorna el mapa con los datos del resultado.
     * @author Gildardo Mora
     */
    private  Map<String, Object> assignOutput(Map<String, Class<?>> parametersOutput, StoredProcedureQuery spQuery) {

        Map<String, Object> results = new HashMap<>();

        parametersOutput.forEach(
                (nombreParametro, tipoParametro) -> {
                    if (tipoParametro.isAssignableFrom(String.class)) {
                        results.putIfAbsent(nombreParametro, spQuery.getOutputParameterValue(nombreParametro));
                        return;
                    }

                    if (tipoParametro.isAssignableFrom(Integer.class) || tipoParametro.isAssignableFrom(int.class)) {
                        Object outputParameterValue = spQuery.getOutputParameterValue(nombreParametro);
                        results.putIfAbsent(nombreParametro, Objects.nonNull(outputParameterValue) ? Integer.parseInt(String.valueOf(outputParameterValue)) : null);
                        return;
                    }

                    if (tipoParametro.isAssignableFrom(BigDecimal.class)) {
                        results.putIfAbsent(nombreParametro, spQuery.getOutputParameterValue(nombreParametro));
                        return;
                    }

                    if (tipoParametro.isAssignableFrom(void.class) || tipoParametro.isAssignableFrom(Class.class) || tipoParametro.isAssignableFrom(List.class)) {
                        @SuppressWarnings("unchecked")
                        List<Object[]> resultList = spQuery.getResultList();
                        results.putIfAbsent(nombreParametro, resultList);
                    }

                }
        );

        return results;
    }

    /**
     * Asigna los datos de entrada proporcionados para el procedimiento almacenado definido.
     *
     * @param parametersInput    {@code Map<String, Class>}
     * @param spQuery            {@link StoredProcedureQuery}
     * @author Gildardo Mora
     */
    private void assignInputData( Map<String, Class<?>> parametersInput, StoredProcedureQuery spQuery) {
        super.getInputData().forEach(
                (nombreParametroEntrada, dato) -> {
                    if (parametersInput.containsKey(nombreParametroEntrada)) {
                        spQuery.setParameter(nombreParametroEntrada, dato);
                    }
                }
        );
    }

    /**
     * Registra los parámetros de salida que se definieron para la ejecución del procedimiento almacenado.
     *
     * @param parametersOutput {@code Map<String, Class>}
     * @param spQuery          {@link StoredProcedureQuery}
     * @author Gildardo Mora
     */
    private void registerOutputParameters(Map<String, Class<?>> parametersOutput, StoredProcedureQuery spQuery) {
        parametersOutput.forEach(
                (nombreParametroSalida, tipoParametro) -> {
                    if (tipoParametro.isAssignableFrom(void.class) || tipoParametro.isAssignableFrom(Class.class) || tipoParametro.isAssignableFrom(List.class)) {
                        spQuery.registerStoredProcedureParameter(nombreParametroSalida, Class.class, ParameterMode.REF_CURSOR);
                        return;
                    }

                    spQuery.registerStoredProcedureParameter(nombreParametroSalida, tipoParametro, ParameterMode.OUT);
                }
        );
    }

    /**
     * Registra los parámetros de entrada que se definieron para la ejecución del procedimiento almacenado.
     *
     * @param parametersInput {@code Map<String, Class>}
     * @param spQuery         {@link StoredProcedureQuery}
     * @author Gildardo Mora
     */
    private void registerInputParameters(Map<String, Class<?>> parametersInput, StoredProcedureQuery spQuery) {
        parametersInput.forEach(
                (nombreParametroEntrada, tipoParametro) -> spQuery.registerStoredProcedureParameter(
                        nombreParametroEntrada, tipoParametro, ParameterMode.IN)
        );
    }

    /**
     * Realiza la conversión del objeto recibido al tipo de dato indicado.
     *
     * @param object {@link Object}
     * @param type   {@link Class}
     * @return {@link Optional<Object>} Retorna el objeto convertido al tipo de dato definido para el parámetro.
     * @author Gildardo Mora
     */
    private Optional<Object> castearObjeto(Object object, Class<?> type) {

        if (Objects.isNull(object)) {
            return Optional.empty();
        }

        if (object.getClass().isAssignableFrom(type)) {
            return Optional.of(object);
        }

        Predicate<Class<?>> esNumeroInt = c -> c.isAssignableFrom(int.class) || c.isAssignableFrom(Integer.class);
        Predicate<Class<?>> esNumeroLong = c -> c.isAssignableFrom(long.class) || c.isAssignableFrom(Long.class);
        Predicate<Object> esBigDecimal = o -> o.getClass().isAssignableFrom(BigDecimal.class);

        if (esNumeroInt.test(type) && esBigDecimal.test(object)) {
            return Optional.of(Integer.parseInt(object.toString()));
        }

        if (esNumeroLong.test(type) && esBigDecimal.test(object)) {
            return Optional.of(Long.parseLong(object.toString()));
        }

        if (type.isAssignableFrom(LocalDateTime.class) && object instanceof java.sql.Timestamp) {
            return Optional.ofNullable(((java.sql.Timestamp) object).toLocalDateTime());
        }

        if (type.isAssignableFrom(LocalDate.class) && object instanceof java.sql.Timestamp) {
            return Optional.ofNullable(((java.sql.Timestamp) object).toLocalDateTime().toLocalDate());
        }

        return Optional.of(object);
    }

}
