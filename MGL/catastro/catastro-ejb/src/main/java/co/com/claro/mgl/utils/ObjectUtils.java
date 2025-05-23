package co.com.claro.mgl.utils;

/**
 *
 * @author bernaldl
 * @version 24/05/2018
 */
public class ObjectUtils {
    
     private ObjectUtils(){
        throw new UnsupportedOperationException ("Operation Not Supported");
    }
     
     public static boolean isNotNull(Object value){
         return value != null ? true : false;
     }
}
