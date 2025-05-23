package co.com.claro.mer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

/**
 * Utilitario para obtener datos del servidor donde se encuentra desplegada la aplicación MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/30
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerInfoUtil {

    /**
     * Obtiene el nombre del cluster del servidor donde se encuentra desplegada la aplicación
     *
     * @return {@link String} Retorna el nombre del cluster
     * @author Gildardo Mora
     */
    public static String getClusterName() {
        try {
            String nameCluster = System.getProperty("weblogic.cluster");
            return StringUtils.isNotBlank(nameCluster) ? nameCluster : "NA";
        } catch (Exception e) {
            LOGGER.error("No se pudo obtener el nombre del cluster.. ", e);
            return "NA";
        }
    }

    /**
     * Obtiene el nombre del servidor donde se encuentra desplegada la aplicación MER.
     *
     * @return {@link String} Retorna el nombre del servidor
     * @author Gildardo Mora
     */
    public static String getServerName() {
        try {
            String nameServer = System.getProperty("weblogic.Name");
            return StringUtils.isNotBlank(nameServer) ? nameServer : "NA";
        } catch (Exception e) {
            LOGGER.error("No se pudo obtener el nombre del servidor.. ", e);
            return "NA";
        }
    }

    /**
     * Obtiene el nombre de la máquina donde se encuentra desplegada la aplicación
     *
     * @return {@link String} Retorna el nombre de la máquina
     * @author Gildardo Mora
     */
    public static String getMachineName() {
        try {
            String nameMachine = InetAddress.getLocalHost().getHostName();
            return StringUtils.isNotBlank(nameMachine) ? nameMachine : "NA";
        } catch (Exception e) {
            LOGGER.error("No se pudo obtener el nombre de la maquina.. ", e);
            return "NA";
        }
    }

}
