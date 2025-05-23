package co.com.claro.mer.utils;

import co.com.claro.mgl.utils.ClassUtils;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase utilitaria para procesos de conversion.
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class Utils {
    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String result = "";
        try {
            result = gson.toJson(object);
        } catch (Exception e) {
            String msgError = "Error en la conversión del objeto a JSON en: "
                    + ClassUtils.getCurrentMethodName(Utils.class);
            LOGGER.error(msgError, e);
        }
        return result;
    }
}
