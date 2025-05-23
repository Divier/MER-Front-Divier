package co.com.claro.cmas400.ejb.utils;


/**
 * Utilitarios empleados en los servicios.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class ServiceUtils {
    
    /**
     * Recorta el nombre de usuario por el n&uacute;mero de caracteres dado.
     * 
     * @param username Nombre de Usuario.
     * @param maxNumCaracteres N&uacute;mero m&aacute;ximo de caracteres.
     * @return Nombre de Usuario recortado al n&uacute;mero de caracteres dado.
     */
    public static String recortarNombreUsuario(String username, int maxNumCaracteres) {
        String resultado = "";
        
        if (username != null && maxNumCaracteres > 0) {
            if (maxNumCaracteres >= username.length()) {
                resultado = username;
            }
            else {
                // eliminar puntos (.)
                String temp = username.replaceAll("\\.", "");
                if (maxNumCaracteres >= temp.length()) {
                    resultado = temp;
                }
                else {
                    resultado = temp.substring(0, maxNumCaracteres);
                }
            }
        }
        
        return (resultado);
    }
    
}
