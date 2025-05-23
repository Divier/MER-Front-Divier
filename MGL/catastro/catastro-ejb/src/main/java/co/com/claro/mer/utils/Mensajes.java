package co.com.claro.mer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase asociada al manejo de propiedades de respuesta de mensajes.
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class Mensajes {

    private Mensajes() {
        //impedir instancias invalidas
    }

    private static final Properties PROPERTIE;
   private static final Logger LOGGER = LogManager.getLogger(Mensajes.class);

    static {
        PROPERTIE = new Properties();

        try (InputStream propertiesStream
                     = Mensajes.class.getClassLoader()
                .getResourceAsStream("mensaje.properties")) {
            PROPERTIE.load(propertiesStream);

        } catch (IOException ex) {
            String msgError = "Ocurrió un error al cargar y leer el archivo: mensaje.properties " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
    }

    public static String getProperty(String key, Object... args) {
        String message = PROPERTIE.getProperty(key);

        if (args != null && args.length > 0) {
            message = String.format(message, args);
        }
        return message;
    }
}
