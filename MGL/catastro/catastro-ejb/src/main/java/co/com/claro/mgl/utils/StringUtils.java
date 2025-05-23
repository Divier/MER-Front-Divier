package co.com.claro.mgl.utils;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria con funciones genericas para el uso de Strins
 * @author bernaldl
 * @version 24/05/2018
 */
public final class StringUtils {
    
    private static final String  MAIL_PATTERN = "^[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";

    private static final String TEXT_PATTERN = "[a-zA-Z\\s]+";
    private static final Logger LOGGER = LogManager.getLogger(StringUtils.class);
    
    private StringUtils(){
        throw new UnsupportedOperationException ("Operation Not Supported");
    }
    
    private static boolean isEmpty(String value){
       return (value == null || value.trim().length() == 0) ? true : false;
    }

    public static boolean exceedsMaxLengthValue(String value, int maxLength){
        return (ObjectUtils.isNotNull(value) && value.length() > maxLength);
    }
    
    public static boolean isTextValue(String value){
        return (!isEmpty(value) ? value.matches(TEXT_PATTERN) : true);
    }
    
      public static boolean isValidMail(String value){
        return (!isEmpty(value) ? value.matches(MAIL_PATTERN) : true);
    }
      
      public static boolean isMinAndMaxRangeValid(String value, int min, int max){
          return !isEmpty(value) ? value.length() >= min && value.length() <= max : false;
      }
    
    public static boolean isNumberText(String value){
        try{
            if(!isEmpty(value))
                Long.parseLong(value);
            return true;
        }catch(NumberFormatException e){
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(StringUtils.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }
    
    
    /**
     * Genera el siguiente valor de la secuencia
     * alfab&eacute;tica/num&eacute;rica, correspondiente al par&aacute;metro
     * ingresado.
     *
     * @param oldSequenceValue Valor anterior de la secuencia.
     * @return Para <i>AAZ</i> retorna <i>ABA</i> (de <i>9<i> pasa a <i>0</i> y
     * de <i>Z</i> pasa a <i>A</i>).
     */
    public static String getNextSequenceValue(String oldSequenceValue) {
        String newSequenceValue = null;

        if (oldSequenceValue != null) {
            Pattern compile = Pattern.compile("^(.*?)([9Z]*)$");
            Matcher matcher = compile.matcher(oldSequenceValue);
            String left = "";
            String right = "";
            if (matcher.matches()) {
                left = matcher.group(1);
                right = matcher.group(2);
            }
            newSequenceValue = !left.isEmpty() ? Long.toString(Long.parseLong(left, 36) + 1, 36) : "";
            newSequenceValue += right.replace("Z", "A").replace("9", "0");
            newSequenceValue = newSequenceValue.toUpperCase();
            while (newSequenceValue.length() < oldSequenceValue.length()) {
                newSequenceValue = "0" + newSequenceValue;
            }
        }
        return (newSequenceValue);
    }

    
    /**
     * Genera el siguiente valor de la secuencia alfanum&eacute;rica,
     * correspondiente al par&aacute;metro ingresado.
     *
     * @param oldSequenceValue Valor anterior de la secuencia.
     * @return Para <i>ZBZ</i> retorna <i>ZC0</i> (de <i>9<i> pasa a <i>A</i> y
     * de <i>Z</i> pasa a <i>0</i>).
     */
    public static String getNextAlphanumericSequenceValue(String oldSequenceValue) {
        char[] cars = oldSequenceValue.toUpperCase().toCharArray();
        for (int i = cars.length - 1; i >= 0; i--) {
            if (cars[i] == 'Z') {
                cars[i] = '0';
            } else if (cars[i] == '9') {
                cars[i] = 'A';
                break;
            } else {
                cars[i]++;
                break;
            }
        }
        return String.valueOf(cars);
    }
    
    
    
    /**
     * Recorta el nombre de usuario por el n&uacute;mero de caracteres dado.
     *
     * @param username Nombre de Usuario.
     * @param maxNumCaracteres N&uacute;mero m&aacute;ximo de caracteres.
     * @return Nombre de Usuario recortado al n&uacute;mero de caracteres dado.
     * <b>Ejemplo:</b> para "<i>juanito.perez.ext</i>" devolver&iacute;a "<i>jperezext</i>"
     * con un m&aacute;ximo de 10 caracteres.  
     */
    public static String recortarNombreUsuario(String username, int maxNumCaracteres) {
        String resultado = "";

        if (username != null && maxNumCaracteres > 0) {
            if (maxNumCaracteres >= username.length()) {
                resultado = username;
            } else {
                // eliminar puntos (.)
                String temp = username.replaceAll("\\.", "");
                if (maxNumCaracteres >= temp.length()) {
                    resultado = temp;
                } else if (username.contains(".")) {
                    // Descomponer en nombre, apellido y complementos.
                    StringTokenizer tokenizer = new StringTokenizer(username, ".");
                    StringBuilder builder = new StringBuilder();
                    if (tokenizer.hasMoreTokens()) {
                        // nombre
                        String nombre = tokenizer.nextToken();
                        if (nombre != null && !nombre.isEmpty()) {
                            String apellido = null;
                            // apellido
                            if (tokenizer.hasMoreTokens()) {
                                apellido = tokenizer.nextToken();
                            }

                            if ((apellido == null && maxNumCaracteres >= nombre.length()) 
                                    || (apellido != null && maxNumCaracteres >= nombre.length() + apellido.length())) {
                                builder.append(nombre);
                            } else {
                                // primera letra del nombre.
                                builder.append(nombre.charAt(0));
                            }

                            if (apellido != null) {
                                builder.append(apellido);
                            }

                            // agregar otros campos seguidos del "." de ser posible.
                            while (maxNumCaracteres > builder.length() && tokenizer.hasMoreTokens()) {
                                String token = tokenizer.nextToken();
                                if (token != null && maxNumCaracteres >= builder.length() + token.length()) {
                                    builder.append(token);
                                }
                            }

                            if (maxNumCaracteres >= builder.length()) {
                                resultado = builder.toString();
                            } else {
                                resultado = builder.substring(0, maxNumCaracteres);
                            }
                        }
                    }
                } else {
                    resultado = temp.substring(0, maxNumCaracteres);
                }
            }
        }

        return (resultado);
    }

    /**
     * Devuelve una cadena, donde se asocia may&uacute;scula a la primera letra 
     * de cada palabra, y min&uacute;scula a las dem&aacute;s. 
    /**
     * Devuelve una cadena, donde se eliminan caracteres especiales 
     * @param cadena Cadena a convertir.
     * @return <i>hola mundo</i> => <i>Hola Mundo</i>.
     */
    public static String caracteresEspeciales(String cadena) {
        String caracterEsp = cadena.trim();
        if (caracterEsp != null) {
           
            if (caracterEsp != null && !caracterEsp.equals("")) {
                if (caracterEsp.contains(";")) {
                    caracterEsp = caracterEsp.replace(";", "-");
                }
                if (caracterEsp.endsWith("\"")) {
                    caracterEsp = caracterEsp.replace("\"", " ");
                }
                if (caracterEsp.startsWith("\"")) {
                    caracterEsp = caracterEsp.replace("\"", " ");
                }
                if (caracterEsp.contains("/r")) {
                    caracterEsp = caracterEsp.replace("/r", " ");
                }
                if (caracterEsp.contains("\\n")) {
                    caracterEsp = caracterEsp.replace("\\n", " ");
                }
                if (caracterEsp.contains("\\r\\n")) {
                    caracterEsp = caracterEsp.replace("\\\r\\n", " ");
                }
                if (caracterEsp.contains("  ")) {
                    caracterEsp = caracterEsp.replace("  ", " ");
                }
                if (cadena.contains("//")) {
                    caracterEsp = caracterEsp.replace("//", " ");
                    caracterEsp = caracterEsp.replace("\"", "");
                }
                if (caracterEsp.contains("\r")) {
                    caracterEsp = caracterEsp.replace("\r", "");
                }
                if (caracterEsp.contains("\n")) {
                    caracterEsp = caracterEsp.replaceAll("\n", "");
                }
                if (caracterEsp.contains("\\R")){
                     caracterEsp = caracterEsp.replace("\\R", "");
                }
                if (caracterEsp.contains("\r\n")) {
                    caracterEsp = caracterEsp.replace("\r\n", "");
                }
                if (caracterEsp.contains("(\n|\r)")) {
                    caracterEsp = caracterEsp.replace("(\n|\r)", "");
                }
                if (caracterEsp.contains("//")) {
                    caracterEsp = caracterEsp.replace("//", "");
                }
                String separador = System.getProperty("line.separator");
                caracterEsp= caracterEsp.replaceAll(separador, "");
                 
            }
        }


        return (caracterEsp);
    }
    
    /**
     * Funcion para evaluar el tamaño de un nombre segun el parametro en BD
     * @param nameUser nombre a evaluar.
     * @return String el nombre 
     */
     public static String retornaNameUser(String nameUser) throws ApplicationException {

        String name = "";
        int tamaño;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parameter = parametrosMglManager.findParametroMgl(Constant.TAMAÑO_NAME_USERS);

        if (parameter != null) {
            try {
                tamaño = Integer.parseInt(parameter.getParValor().trim());
                if (nameUser != null && !nameUser.isEmpty() && nameUser.length() > tamaño) {
                    name = nameUser.substring(0, tamaño);
                } else {
                    name = nameUser;
                }
            } catch (NumberFormatException ex) {
                tamaño = 9;
                if (nameUser != null && !nameUser.isEmpty() && nameUser.length() > tamaño) {
                    name = nameUser.substring(0, tamaño);
                } else {
                    name = nameUser;
                }

            }
        }

        return name;
    }
}
