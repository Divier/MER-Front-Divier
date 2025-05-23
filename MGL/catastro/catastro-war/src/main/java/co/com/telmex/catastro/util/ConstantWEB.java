package co.com.telmex.catastro.util;

/**
 * Clase ConstantWEB
 * Extiende de BaseMBean
 * Implementa Serialización
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class ConstantWEB {

    /**
     * 
     */
    public static String USER_SESSION = "sesionuser";
    /**
     * 
     */
    public static String ROL_SESSION = "sesionrol";
    /**
     * 
     */
    public static String MENU_SESSION = "menu";
    
    public static Integer MAX_NUMERO_COLUM_ARCHIVO_RED = 13;
    
    public static enum NAME_COLUMN {
        NOMBRE, VIAPRINCIPAL, VIAGENERADORA, TOTALHHPP,APTOS,LOCALES,OFICINAS, PISOS, BARRIO, DISTRIBUCION, NODO, ESTRATO,VALIDAR
    }
    
    
}
