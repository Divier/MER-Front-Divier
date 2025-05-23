/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CommandCall;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.data.ProgramCallDocument;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import net.telmex.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author John Arevalo
 */
@Stateless
public class Manager implements ManagerLocal {

    private AS400 as400;
    private ProgramCallDocument pcmlDoc;
    private String libraries;
    private Properties configuration;
    private static final Logger LOGGER = LogManager.getLogger(Manager.class);

    private static final String MESSAGE_ERROR = "Se produjo un error al momento de ejecutar el metodo ";

    /**
     * N&uacute;mero m&aacute;ximo de reintentos de conexi&oacute;n al servicio
     * de <i>PCML</i>.
     */
    private final int NUMERO_MAXIMO_REINTENTOS_CONEXION = 2;

    public Manager() {
        resetConnection();
    }

    public Manager(Properties configuration) {
        this.configuration = configuration;
        resetConnection();
    }

    @Override
    public void invoke(Object pcml) throws PcmlException {
        validateConfiguration();
        Program program = pcml.getClass().getAnnotation(Program.class);
        try {
            pcmlDoc = new ProgramCallDocument(as400, program.documentName());
            Field[] fields = pcml.getClass().getDeclaredFields();
            for (Field field : fields) {
                setValue(field, pcml, program.programName());
            }

            // cantidad de reintentos de reconexion.
            int cantidadReintentos = 0;
            // Flag que determina si el PCML fue ejecutado.
            boolean pcmlEjecutado = false;

            do {
                try {
                    pcmlDoc.callProgram(program.programName());
                    pcmlEjecutado = true;
                } catch (com.ibm.as400.data.PcmlException ex) {
                    String errorMsg = ex.getMessage();
                    LOGGER.error(errorMsg);

                    // En caso tal que la conexión haya caído:
                    if (errorMsg != null && errorMsg.contains("com.ibm.as400.access.ConnectionDroppedException")) {
                        // Volver a reconectar.
                        resetConnection();
                        cantidadReintentos++;

                        try {
                            // Duerme unos milisegundos, después de la reconexión.
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            LOGGER.error("Error el método '"
                                    .concat(ClassUtils.getCurrentMethodName(this.getClass())
                                            .concat("' al momento de ejecutar el sleep.")));
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        throw ex;
                    }
                }
            } while (!pcmlEjecutado && cantidadReintentos < NUMERO_MAXIMO_REINTENTOS_CONEXION);

            if (!pcmlEjecutado) {
                throw new PcmlException("Se ha superado la cantidad de reintentos de conexión ("
                        + NUMERO_MAXIMO_REINTENTOS_CONEXION + ").");
            }

            AS400Message[] messageList = pcmlDoc.getProgramCall().getMessageList();

            if (messageList != null && messageList.length > 0) {
                StringBuilder buffer = new StringBuilder();
                for (AS400Message message : messageList) {
                    buffer.append(message.getText()).append(System.getProperty("line.separator"));
                }
                throw new PcmlException(buffer.toString());
            }
            for (Field field : fields) {
                getValue(field, pcml, program.programName());
            }
        } catch (com.ibm.as400.data.PcmlException exc) {
            resetConnection();
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(exc.getMessage());
            LOGGER.error(msg);
            throw new PcmlException("Error de conectividad con el servicio de PCML: " + exc.getMessage(), exc);
        } catch (PcmlException | ApplicationException | SecurityException ex) {
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(ex.getMessage());
            LOGGER.error(msg);
            throw new PcmlException("Se produjo un error al momento de invocar el PCML: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(ex.getMessage());
            LOGGER.error(msg);
            throw new PcmlException("Se produjo un error al momento de invocar el PCML: " + ex.getMessage(), ex);
        }
    }

    private void resetConnection() {
        validateConfiguration();

        if (as400 != null) {
            try {
                as400.disconnectAllServices();
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de Cerrar las conexiones de RR '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg, ex);
            }
        }

        libraries = configuration.getProperty(Constantes.AS400_PCML_LIBRARIES);
        as400 = new AS400(
                configuration.getProperty(Constantes.AS400_PCML_HOST),
                configuration.getProperty(Constantes.AS400_PCML_USUARIO),
                configuration.getProperty(Constantes.AS400_PCML_PASSWORD));

        CommandCall commandCall = new CommandCall(as400);
        try {
            commandCall.run("CHGLIBL LIBL(" + libraries + ")");
        } catch (AS400SecurityException | ErrorCompletingRequestException | PropertyVetoException | IOException
                | InterruptedException ex) {
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(ex.getMessage());
            LOGGER.error(msg);
            Thread.currentThread().interrupt();
        }

        int logLevel = 4;
        int logSeverity = 30;
        try {
            commandCall.run("CHGJOB LOG("
                    + logLevel
                    + " "
                    + logSeverity
                    + " *SECLVL) LOGCLPGM(*YES) INQMSGRPY(*DFT) LOGOUTPUT(*JOBEND)");
        } catch (AS400SecurityException | ErrorCompletingRequestException | PropertyVetoException | IOException
                | InterruptedException ex) {
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(ex.getMessage());
            LOGGER.error(msg);
            Thread.currentThread().interrupt();
        }
    }
    
    private void setValue(Field field, Object valueField, String path) throws ApplicationException {
        try {
            if (field.isAnnotationPresent(Data.class)) {

                Data pcmlData = field.getAnnotation(Data.class);
                if (!pcmlData.usage().equals(UsageType.INPUT)
                        && !pcmlData.usage().equals(UsageType.INPUTOUTPUT)) {
                    return;
                }
                Object value = getField(field, valueField);
                path = path + "." + pcmlData.pcmlName();

                if (field.getType().isAnnotationPresent(Struct.class)) {
                    for (Field structField : field.getType().getDeclaredFields()) {
                        setValue(structField, value, path);
                    }
                } else {
                    if (value instanceof String) {
                        value = StringUtil.completeWith((String) value, pcmlData.completeWith(), pcmlData.length());
                    }
                    pcmlDoc.setValue(path, value);
                }

            } else if (field.isAnnotationPresent(Array.class)) {
                Object value;
                Array pcmlArray = field.getAnnotation(Array.class);
                if (!pcmlArray.usage().equals(UsageType.INPUT)
                        && !pcmlArray.usage().equals(UsageType.INPUTOUTPUT)) {
                    return;
                }
                path = path + "." + pcmlArray.pcmlName();
                List<?> arrayValue = (List<?>) getField(field, valueField);

                if (pcmlArray.type().isAnnotationPresent(Struct.class)) {
                    for (Field structField : pcmlArray.type().getDeclaredFields()) {
                        if (structField.isAnnotationPresent(Data.class)) {
                            Data pcmlData = structField.getAnnotation(Data.class);
                            int[] indices = new int[1];
                            for (int i = 0; i < pcmlArray.size(); i++) {
                                indices[0] = i;
                                try {
                                    value = getField(structField, arrayValue.get(i));
                                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException
                                        | InvocationTargetException ex) {
                                    String msg = MESSAGE_ERROR
                                            .concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                                            .concat(ex.getMessage());
                                    LOGGER.error(msg);
                                    value = "0";
                                }
                                try {
                                    if (value instanceof String) {
                                        value = StringUtil.completeWith((String) value, pcmlData.completeWith(),
                                                pcmlData.length());
                                    }
                                    pcmlDoc.setValue(path + "." + pcmlData.pcmlName(), indices, value);
                                } catch (com.ibm.as400.data.PcmlException exc) {
                                    String msg = MESSAGE_ERROR
                                            .concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                                            .concat(exc.getMessage());
                                    LOGGER.error(msg);
                                    throw new ApplicationException("Error setting \"" + path + "." + pcmlData.pcmlName()
                                            + "\" with value '" + value + "' (" + exc.getMessage() + ")");
                                }
                            }
                        }
                    }
                } else {
                    int[] indices = new int[1];
                    for (int i = 0; i < pcmlArray.size(); i++) {
                        indices[0] = i;
                        try {
                            value = arrayValue.get(i);
                        } catch (Exception ex) {
                            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                                    .concat(ex.getMessage());
                            LOGGER.error(msg);
                            value = " ";
                        }
                        pcmlDoc.setValue(path, indices, value);
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | com.ibm.as400.data.PcmlException ex) {
            String msg = MESSAGE_ERROR.concat(ClassUtils.getCurrentMethodName(this.getClass()) + "': ")
                    .concat(ex.getMessage());
            LOGGER.error(msg);
            throw new ApplicationException("Error en setValue. EX000 " + ex.getMessage(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void getValue(Field field, Object valueField, String path) throws ApplicationException {
        try {
            if (field.isAnnotationPresent(Data.class)) {
                Data pcmlData = field.getAnnotation(Data.class);
                if (field.getType().isAnnotationPresent(Struct.class)) {
                    Object struct = getField(field, valueField);

                    for (Field structField : field.getType().getDeclaredFields()) {
                        getValue(structField, struct, path + "." + pcmlData.pcmlName());
                    }
                } else {
                    Object value = pcmlDoc.getValue(path + "." + pcmlData.pcmlName());
                    setField(field, valueField, value);
                }
            } else if (field.isAnnotationPresent(Array.class)) {
                Array pcmlArray = field.getAnnotation(Array.class);
                List<Object> array = (List<Object>) getField(field, valueField);
                array.clear();
                path = path + "." + pcmlArray.pcmlName();
                int[] indices = new int[1];
                if (pcmlArray.type().isAnnotationPresent(Struct.class)) {
                    for (int i = 0; i < pcmlArray.size(); i++) {
                        indices[0] = i;
                        Object elementArray = pcmlArray.type().getDeclaredConstructor().newInstance();
                        for (Field structField : pcmlArray.type().getDeclaredFields()) {
                            if (structField.isAnnotationPresent(Data.class)) {
                                Data pcmlData = structField.getAnnotation(Data.class);
                                Object value = pcmlDoc.getValue(path + "." + pcmlData.pcmlName(), indices);
                                setField(structField, elementArray, value);
                            }
                        }

                        array.add(elementArray);
                    }
                } else {
                    for (int i = 0; i < pcmlArray.size(); i++) {
                        indices[0] = i;
                        Object value = pcmlDoc.getValue(path, indices);
                        array.add(value);
                    }
                    setField(field, valueField, array);
                }
            }
        } catch (com.ibm.as400.data.PcmlException | IllegalAccessException | IllegalArgumentException
                | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en setValue. EX000: " + ex.getMessage(), ex);
        }
    }

    public static Object getField(Field field, Object object)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String methodName = "get" + StringUtil.toCamelCase(field.getName());
        Method method = field.getDeclaringClass().getMethod(methodName);
        return method.invoke(object);
    }

    public static void setField(Field field, Object object, Object value)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String methodName = "set" + StringUtil.toCamelCase(field.getName());
        Method method = field.getDeclaringClass().getMethod(methodName, value.getClass());
        method.invoke(object, value);
    }

    /**
     * Check if this instance has been configured
     */
    private void validateConfiguration() throws IllegalStateException {
        if (configuration == null) {
            throw new IllegalStateException("This instance has not been configured yet");
        }

        if (StringUtil.isEmpty(configuration.getProperty("as400.pcml.host"))) {
            throw new IllegalStateException("as400.pcml.host property is required");
        }
        if (StringUtil.isEmpty(configuration.getProperty("as400.pcml.user"))) {
            throw new IllegalStateException("as400.pcml.host property is "
                    + "required");
        }
        if (StringUtil.isEmpty(configuration.getProperty("as400.pcml.password"))) {
            throw new IllegalStateException("as400.pcml.host property is "
                    + "required");
        }
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }
}
