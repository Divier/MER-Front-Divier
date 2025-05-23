package co.com.claro.mgl.mbeans.util;

import org.primefaces.PrimeFaces;


/**
 * Utilitarios para PrimeFaces.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
public class PrimeFacesUtil {
    
    /**
     * Actualiza un componente en el contexto PrimeFaces 
     * (En reemplazo de: org.primefaces.context.RequestContext.getCurrentInstance().update(componentId)).
     * @param componentId Identificador del componente a actualizar.
     */
    public static void update (String componentId) {
        // Para PrimeFaces >= 6.3
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update(componentId);
    }
    
    
    /**
     * Ejecuta una sentencia en el contexto PrimeFaces 
     * (En reemplazo de: org.primefaces.context.RequestContext.getCurrentInstance().execute(statement)).
     * @param statement Sentencia a ejecutar.
     */
    public static void execute (String statement) {
        // Para PrimeFaces >= 6.3
        PrimeFaces.current().executeScript(statement);
    }
    
    
    /**
     * Establece <i>Focus</i> en el componente requerido.
     * @param componentId Identificador del componente a realizar <i>Focus</i>.
     */
    public static void focus (String componentId) {
        // Para PrimeFaces >= 6.3
        PrimeFaces.current().focus(componentId);
    }
    
    
    /**
     * Realiza un Scroll hacia el componente especificado.
     * @param componentId Identificador del componente a realizar <i>Scroll</i>.
     */
    public static void scrollTo (String componentId) {
        // Para PrimeFaces >= 6.3
        PrimeFaces.current().scrollTo(componentId);
    }
}
