package co.com.claro.mer.utils.constants;

/**
 * Constantes asociadas al proceso de bloqueo de generación de reportes
 * y de exportar datos en MER.
 *
 * @version 1.0, 2023/09/07
 * @author Gildardo Mora
 */
public class BlockReportConstants {

    private BlockReportConstants() {
        //Impedir instancias invalidas.
    }

    /**
     * Llave para manejar el mensaje complementario que se muestra en el popup
     * cuando se tiene activo el bloqueo de generación de reportes.
     */
    public static final String MSG_BLOCK_REPORT = "msgBlockReport";
    /**
     * Llave para identificar si está habilitado el bloqueo de reportes.
     */
    public static final String IS_ENABLED_BLOCK_REPORT = "isEnabledBlockReport";

}
