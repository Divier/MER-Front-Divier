package co.com.claro.mer.utils;

import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utilería para operaciones con cadenas de texto, para
 * realizar validaciones de tipos de dato
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/06/06
 */
public class StringToolUtils {

    private static final Logger LOGGER = LogManager.getLogger(StringToolUtils.class);

    private StringToolUtils() {
        //impedir instancias invalidas
    }

    /* ------------- Validaciones de tipo de datos ---------------- */

    /**
     * Verifica si la cadena recibida solo contiene números
     *
     * @param cadenaEvaluada {@link String} cadena que se va a comprobar
     * @return Retorna true si la cadena es numérica
     */
    public static boolean containsOnlyNumbers(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return cadenaEvaluada.matches("\\d+");
    }

    /**
     * Verifica si una cadena recibida solo contiene letras
     *
     * @param cadenaEvaluada {@link String} cadena que se va a verificar
     * @return Retorna true si la cadena recibida solo contiene letras.
     */
    public static boolean containsOnlyLetters(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return cadenaEvaluada.matches("[a-zA-Z]+");
    }

    /**
     * Verifica si una cadena de texto contiene letras y espacios solamente.
     *
     * @param cadenaEvaluada {@link String} cadena que se va a verificar
     * @return Retorna true si la cadena evaluada solo contiene letras y espacios.
     */
    public static boolean containsOnlyLettersWithSpaces(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return cadenaEvaluada.matches("[a-z A-Z]+");
    }

    /**
     * Verifica si la cadena recibida contiene solo letras y números
     *
     * @param cadenaEvaluada {@link String} cadena que se va a verificar
     * @return Retorna true cuando la cadena evaluada contiene solo letras y números
     */
    public static boolean containsOnlyLettersAndNumbers(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return cadenaEvaluada.matches("\\w+");
    }

    /**
     * Verifica si la cadena recibida es alfanumérica (Números, letras y espacios)
     *
     * @param cadenaEvaluada {@link String} cadena que se va a verificar
     * @return Retorna true cuando la cadena evaluada es alfanumérica
     */
    public static boolean containsAlfanumeric(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return cadenaEvaluada.matches("[a-z A-Z\\d]+");
    }

    /**
     * Verifica si la cadena recibida contiene caracteres especiales
     *
     * @param cadenaEvaluada {@link String} cadena que se va a verificar
     * @return Retorna true cuando la cadena evaluada contiene caracteres especiales
     */
    public static boolean containsEspecialCharacter(String cadenaEvaluada) {
        Objects.requireNonNull(cadenaEvaluada);
        return !cadenaEvaluada.matches("[a-z A-Z\\d]+");
    }

    /* ------------- Conversiones ______________*/

    /**
     * Convierte una lista de String a un String separado por un elemento
     *
     * @param listaDatos            {@link List<String>}
     * @param delimitador           {@link DelimitadorEnum}
     * @param quitarElementosVacios boolean Indica si se deben o no conservar los espacios vacios de elementos
     * @return {@link String} Retorna el string con los elementos separados por el delimitador
     * @see DelimitadorEnum
     */
    public static String convertListToString(List<String> listaDatos, DelimitadorEnum delimitador,
            boolean quitarElementosVacios) throws ApplicationException {

        try {
            Objects.requireNonNull(listaDatos, "La lista no puede ser nula");
            Objects.requireNonNull(delimitador, "El delimitador no puede ser nulo");
            Predicate<String> filtrarVacios = quitarElementosVacios ? dato -> !dato.isEmpty() : dato -> true;

            listaDatos = listaDatos.stream()
                    .map(String::trim)
                    .filter(filtrarVacios)
                    .collect(Collectors.toList());
            return String.join(delimitador.getDelimitador(), listaDatos);

        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(StringToolUtils.class) + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(e);
        }

    }

    /**
     * Convierte un String con elementos separados por un delimitador en una lista de Strings
     *
     * @param cadena                {@link String} String con elementos separados por delimitador
     * @param delimitador           {@link DelimitadorEnum} Indica el tipo de separador que trae la cadena para sus elementos.
     * @param quitarElementosVacios boolean, Indica si la lista generada puede contener elementos vacíos
     * @return {@link List<String>}  Lista generada
     * @author Gildardo Mora
     * @see DelimitadorEnum
     */
    public static List<String> convertStringToList(String cadena, DelimitadorEnum delimitador, boolean quitarElementosVacios)
            throws ApplicationException {

        if (Objects.isNull(cadena) || cadena.isEmpty()) return new ArrayList<>();

        try {
            String delimiter = getDelimiter(delimitador);
            List<String> resultList = new ArrayList<>(Arrays.asList(cadena.trim().split(delimiter)));
            if (quitarElementosVacios) resultList.removeIf(String::isEmpty);
            return resultList;

        } catch (Exception e) {
            String msgError = "Ocurrió un error en la conversión del String a Lista: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(e);
        }
    }

    /**
     * Verifica y retorna el delimitador requerido
     *
     * @param delimitador {@link DelimitadorEnum}
     * @return {@link String} Retorna el delimitador verificado
     */
    private static String getDelimiter(DelimitadorEnum delimitador) {
        Objects.requireNonNull(delimitador, "No se asignó un delimitador valido");
        switch (delimitador) {
            case PIPE:
                return delimitador.getDelimitador().equals(DelimitadorEnum.PIPE.getDelimitador())
                        ? Pattern.quote("|") : delimitador.getDelimitador();
            case PUNTO:
                return "\\.";
            default:
                return delimitador.getDelimitador();
        }
    }

    /**
     * Realiza el encriptado MD5 de una cadena de texto.
     *
     * @param texto {@link String} Texto a encriptar.
     * @param llave {@link String} Llave de encriptación.
     * @return {@link String} Retorna el texto encriptado.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public static String encriptarTexto(String texto, String llave) throws ApplicationException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(llave.getBytes(StandardCharsets.UTF_8));
            SecretKey key = new SecretKeySpec(Arrays.copyOf(digestOfPassword, 24), "DESede");

            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] buf = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(buf));
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error al encriptar el texto: {}", ex.getMessage());
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * Convierte un Map a un String para consumir en el procedimiento de generacion de informes
     * @author Manuel Hernández
     * @param mapa con los parametros a convertir
     * @return cadena de texto con el formato "CLAVE = VALOR; CLAVE = VALOR;"
     */
    public static String mapToString(Map<?, ?> mapa) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : mapa.entrySet()) {
            if(entry.getKey().toString().equals("ORIGEN")){
                sb.append(entry.getKey().toString())
                .append(" ")
                .append(entry.getValue().toString())
                .append("; ");
            }else{
                sb.append(entry.getKey().toString())
                .append(" = ")
                .append(entry.getValue().toString())
                .append("; ");
            }
        }
        if (!mapa.isEmpty()) {
            sb.setLength(sb.length() - 2); // elimina la última coma y el espacio
        }
        return sb.toString();
    }

     /**
     * Convertir un caracter especificado de una cadena a mayúsculas
     * @param cadena cadena de texto a convertir
     * @param caracter caracter a convertir a mayúsculas
     * @return cadena de texto con los caracteres especificados convertidos a mayúsculas
     *
     * @author Manuel Hernández
     */
    public static String convertirCaracteresMayusculas(String cadena, char caracter){
        return cadena.replaceAll("["+caracter+"]", String.valueOf(caracter).toUpperCase());
    }

    public static String reemplazarCaracteres(String cadena, char caracter, char reemplazo){
        return cadena.replaceAll("["+caracter+"]", String.valueOf(reemplazo));
    }

    /**
     * Obtiene el usuario en sesion de acuerdo al tipo de login
     *
     * @return nombre de usuario en sesion o cadena vacia si no hay usuario en sesion
     * @author Manuel Hernández
     * @see SesionUtil Se movio el método a la clase SesionUtil.
     * @deprecated Se deberá usar la clase SesionUtil para obtener el usuario logueado.
     */
    public static String getUsuarioLogueado()  {
        return  SesionUtil.getUsuarioLogueado();
    }
}
