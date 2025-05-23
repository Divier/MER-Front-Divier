package co.com.claro.mgl.utils;

import co.com.claro.mer.utils.constants.ConstantsCargueMasivo;
import co.com.claro.mgl.error.ApplicationException;
import com.csvreader.CsvReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidades para el manejo de archivos CSV
 *
 * @author Manuel Hernández Rivas
 * @version 1.0
 */
public final class CsvUtils {

    private static final Logger LOGGER = LogManager.getLogger(CsvUtils.class);

    /**
     * Válida si la extension de un archivo es CSV
     *
     * @param fileToValidate Archivo al que se desea validar extension
     * @return True = es CVS
     * @author Manuel Hernández Rivas
     */
    public static boolean isCsvFile(Part fileToValidate) {
        return fileToValidate.getSubmittedFileName().equalsIgnoreCase(ConstantsCargueMasivo.EXTENSION_CSV);
    }

    /**
     * Válida si la extension de un archivo es CSV
     *
     * @param fileToValidate Archivo al que se desea validar extension
     * @return True = es CSV
     */
    public static boolean isCsvFile(String fileToValidate) {
        return FilenameUtils.getExtension(fileToValidate).equalsIgnoreCase(ConstantsCargueMasivo.EXTENSION_CSV);
    }

    /**
     * Lee un archivo en formato CSV y retorna su informacion en forma de List<String>
     *
     * @param file        Archivo a leer
     * @param delimitador Delimitador que separa las columnas del archivo
     * @return Lista con el archivo lista para ser validada
     * @throws ApplicationException manejo de excepciones
     * @author Manuel Hernández Rivas
     */
    public static List<String> leerCVS(InputStream file, String delimitador) throws ApplicationException {
        List<String> resultado = new ArrayList<>();
        CsvReader reader = null;

        try {
            reader = new CsvReader(new InputStreamReader(file, "ISO-8859-1"), delimitador.charAt(0));
            while (reader.readRecord()) {
                resultado.add(reader.getRawRecord());
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                LOGGER.error("Error al cerrar el archivo CVS", e);
            }
        }
        return resultado;
    }

    /**
     * Valida si la cabecera de un archivo es la esperada
     *
     * @param archivo     Archivo a validar
     * @param cabecera    Cabecera esperada
     * @param delimitador Delimitador que separa las columnas del archivo
     * @return True = la cabecera es la esperada
     */
    public static boolean validarCabecera(UploadedFile archivo, String cabecera, String delimitador) {
        try {
            List<String> cabeceraArchivo = leerCVS(archivo.getInputStream(), delimitador);
            return cabeceraArchivo.get(0).equals(cabecera);
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error al validar la cabecera del archivo", e);
            return false;
        }
    }

}
