/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface TipoOtHhppMglFacadeLocal extends BaseFacadeLocal<TipoOtHhppMgl>{

      /**
     * Listado de ot por id 
     *
     * @author Juan David Hernandez
     * @param idTipoOtHhpp
     * @return
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtHhppById(BigDecimal idTipoOtHhpp)
            throws ApplicationException;
    
      /**
     * Listado de todos los tipo de ot de la base de datos
     *
     * @author Juan David Hernandez
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<TipoOtHhppMgl> findAll() throws ApplicationException;
    
      /**
     * Crea una tipo de ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public TipoOtHhppMgl crearTipoOtHhpp(TipoOtHhppMgl t)
            throws ApplicationException;
    
    /**
     * Elimina una ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    public boolean eliminarTipoOtHhpp(TipoOtHhppMgl t)
            throws ApplicationException;
    
  
    void setUser(String mUser, int mPerfil) throws ApplicationException;
 
     /**
     * Cuenta de todos los tipo de ot de la base de datos
     *
     * @author Juan David Hernandez
     * @return
     * @throws ApplicationException
     */
    public int countAllTipoOtHhpp() throws ApplicationException;
    
    
    /**
     * Listado de todos los tipo de ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param firstResult
     * @param idTipoOtHhpp
     * @param maxResults
     * @return
     * @throws ApplicationException
     */
    public List<TipoOtHhppMgl> findAllTipoOtHhppPaginada(int firstResult, 
            int maxResults) throws ApplicationException;
    
     /**
     * Tipo de ot por nombre
     *
     * @author Juan David Hernandez
     * @param nombreTipoOtHhpp
     * @return
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtHhppByNombreTipoOt(String nombreTipoOtHhpp)
            throws ApplicationException;
    
     /**
     * Validar que un tipo de ot no este en uso al estar asociada a una ot
     *
     * @author Juan David Hernandez
     * @param idTipoOt
     * @return verdadero si se encuentra en uso
     * @throws ApplicationException
     */
    public boolean validarTipoOtHhppEnUso(BigDecimal idTipoOt)
            throws ApplicationException;
    
}