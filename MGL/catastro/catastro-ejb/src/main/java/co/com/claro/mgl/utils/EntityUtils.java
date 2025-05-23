package co.com.claro.mgl.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Utilitarios relacionados con <b>Entidades</b> y sus respectivos Data Transfer Objects (<b>DTO</b>s)
 * @author Camilo Miranda (<i>mirandaca</i>).
 * @param <E> Entidad.
 * @param <D> Data Transfer Object (DTO).
 */
public class EntityUtils<E,D> {
    
    /** Log del aplicativo. */
    private static final Logger LOGGER = LogManager.getLogger(EntityUtils.class);
    
    /** Objeto que representa el DTO. */
    private D d;
    
    
    /**
     * Constructor.
     * @param d Objeto que representa el DTO.
     */
    public EntityUtils(D d) {
        this.d = d;
    }
    
    
    /**
     * Realiza la conversi&oacute;n de una Entidad a su respectivo DTO.
     * @param entidad Entidad a convertir.
     * @return Data Transfer Object (DTO).
     */
    public D convertirEntidadADto(E entidad) {
        
        if (entidad != null) {
            Method[] dtoMethods = d.getClass().getDeclaredMethods();
            // Por cada metodo del DTO:
            for (Method dtoMethod: dtoMethods) {
                boolean found = true;
                // si es un metodo setter:
                if (isSetter(dtoMethod)) {
                    try {
                        // busca su correspondiente getter en la Entidad:
                        Method entityGetterMethod = null;
                        try {
                            entityGetterMethod = entidad.getClass().getMethod(dtoMethod.getName().replace("set", "get"));
                        } catch (NoSuchMethodException e) {
                            entityGetterMethod = entidad.getClass().getMethod(dtoMethod.getName().replace("set", "is"));
                        }
                        
                        if (entityGetterMethod != null) {
                            // Invocar los metodos para establecer los valores correspondientes:
                            dtoMethod.invoke(d, entityGetterMethod.invoke(entidad));
                        }
                        
                    } catch (NoSuchMethodException | SecurityException ex) {
                        found = false;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error("Error al momento de invocar el método: " + e.getMessage(), e);
                    }
                }
            }
            
            return (d);
        }
        
        return (null);
    }
    
    
    /**
     * Valida si el m&eacute;todo suministrado es un Getter.
     * @param method M&eacute;todo a evaluar.
     * @return Es un Getter?
     */
    private boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*")
                    && !method.getReturnType().equals(void.class)) {
                return true;
            }
            if (method.getName().matches("^is[A-Z].*")
                    && method.getReturnType().equals(boolean.class)) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Valida si el m&eacute;todo suministrado es un Setter.
     * @param method M&eacute;todo a evaluar.
     * @return Es un Setter?
     */
    private boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(void.class)
                && method.getParameterTypes().length == 1
                && method.getName().matches("^set[A-Z].*");
    }
}
