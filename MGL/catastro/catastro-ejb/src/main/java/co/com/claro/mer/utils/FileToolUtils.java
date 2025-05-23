package co.com.claro.mer.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Utilitario para procesos con archivos
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/05
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileToolUtils {

    /**
     * Se encarga de eliminar un archivo en la caché del sistema.
     *
     * @param file {@link File} Archivo a eliminar de la caché.
     * @author Gildardo Mora
     */
    public static synchronized void deleteFile(File file) {
        deleteFile(file, StringUtils.EMPTY);
    }

    /**
     * Se encarga de eliminar un archivo en la caché del sistema.
     *
     * @param file     {@link File} Archivo que se requiere eliminar de la caché.
     * @param filename {@link String} Nombre del archivo que se requiere eliminar de la caché.
     * @author Gildardo Mora
     */
    public static synchronized void deleteFile(File file, String filename) {

        if (file == null) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            String msgWarn = "El archivo recibido desde " + methodName + " es nulo ";
            LOGGER.warn(msgWarn);
            return;
        }

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            if (StringUtils.isBlank(filename)) {
                filename = Optional.of(file).map(File::getName).orElse("");
            }

            String msgError = String.format("Error al borrar el archivo %s", filename);
            LOGGER.error(msgError, e);
        }
    }
}
