package co.com.claro.mgl.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utilitarios relacionados con Clases.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class ClassUtils {
    
    private static final Logger LOGGER = LogManager.getLogger(ClassUtils.class);

    private ClassUtils() {
        //impedir instancias invalidas
    }

    /**
     * Obtiene el nombre del método que est&aacute; invocando este
     * llamado.
     *
     * @return Nombre del método que invoca este método.
     */
    public static String getCurrentMethodName() {
        return ( getCurrentMethodName(null) );
    }
    
    
    /**
     * Obtiene el nombre del método que est&aacute; invocando este
     * llamado.
     *
     * @param clase Clase a la cual pertenece el método, utilizada para
     * concatenar el nombre de la clase, seguido del nombre del método
     * (<i>OPCIONAL</i>).
     * @return Nombre del método que invoca este método.
     */
    public static <T> String getCurrentMethodName(Class <T> clase) {
        String methodName = "";
        try {
            if (clase != null) {
                methodName += clase.getSimpleName() + ".";
            }
            
            /*       
            La posicion 0 hace referencia al metodo 'getStackTrace()',
            La posicion 1 hace referencia al presente metodo 'getCurrentMethodName(Class)'.
            La posicion 2 hace referencia al metodo que invoca este metodo.
             */
            methodName += Thread.currentThread().getStackTrace()[2].getMethodName();
            
        } catch (ArrayIndexOutOfBoundsException e) {
            String msgError = "Error al momento de obtener el nombre del método: " + e.getMessage();
            LOGGER.error(msgError, e);
        } catch (Exception e) {
            String msgError = "Se produjo un error al momento de obtener el nombre del método: " + e.getMessage();
            LOGGER.error(msgError, e);
        }

        return (methodName);
    }
}
