package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Remote;



/**
 * Interfase ResourceEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface ResourceEJBRemote {

    /**
     * 
     */
    public void postConstruct();

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Parametros queryParametros(String acronimo) throws ApplicationException;
    
    /**
     * Realiza la consulta del sobre mgl_parametros_trabajos por nombre
     *
     * @param nombre String nombre del parametro a buscar
     * @return ParametrosTareasProgramadas Primer valor de la lista de valores
     * encontrados.
     * @throws ApplicationException
     */
    ParametrosTareasProgramadas queryParametrosTareasProgramadasByName(String nombre)
            throws ApplicationException;
}
