/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface TipoHhppMglFacadeLocal extends BaseFacadeLocal<TipoHhppMgl>{
    
        /**
     * Listado de todos los ot de la base de datos
     *
     * @author Lenis Cardenas     *
     * @return listado de tipos de hhpp 
     * @throws ApplicationException
     */
    @Override
    public List<TipoHhppMgl> findAll() throws ApplicationException ;
    
    public TipoHhppMgl findTipoHhppMglById(String thhID) throws ApplicationException ;
    
    
}
