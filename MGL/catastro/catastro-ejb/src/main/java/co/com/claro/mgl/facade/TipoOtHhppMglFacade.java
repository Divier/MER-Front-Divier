/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.TipoOtHhppMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class TipoOtHhppMglFacade implements TipoOtHhppMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;

    @Override
    public TipoOtHhppMgl update(TipoOtHhppMgl t) 
            throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager 
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.update(t);
    }

    @Override
    public boolean eliminarTipoOtHhpp(TipoOtHhppMgl t) throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.eliminarTipoOtHhpp(t,user,perfil);
    }
    
    
    @Override
    public boolean delete(TipoOtHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public TipoOtHhppMgl create(TipoOtHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoOtHhppMgl findById(TipoOtHhppMgl sqlData)
            throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.findById(sqlData);
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    
    
     /**
     * Listado de ot por id
     *
     * @author Juan David Hernandez
     * @param idHhpp
     * @return
     * @throws ApplicationException
     */
    @Override
    public TipoOtHhppMgl
            findTipoOtHhppById(BigDecimal idHhpp) 
            throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.findTipoOtHhppById(idHhpp);
    }
       
    
     /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez     *
     * @return listado de tipo de ot
     * @throws ApplicationException
     */
    @Override
    public List<TipoOtHhppMgl> findAll() throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.findAll();
    }
    
        
     /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez     *
     * @return listado de tipo de ot
     * @throws ApplicationException
     */
    @Override
    public int countAllTipoOtHhpp() throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.countAllTipoOtHhpp();
    }
    
    
        
     /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez     *
     * @param firstResult
     * @param maxResults
     * @return listado de tipo de ot
     * @throws ApplicationException
     */
    @Override
    public List<TipoOtHhppMgl> findAllTipoOtHhppPaginada(int firstResult,
            int maxResults) throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.findAllTipoOtHhppPaginada(firstResult, maxResults);
    }

    
         /**
     * Crea un tipo de ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public TipoOtHhppMgl crearTipoOtHhpp(TipoOtHhppMgl t) 
            throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.create(t,user, perfil);
    }
    
    
      /**
     * Tipo de ot por nombre
     *
     * @author Juan David Hernandez
     * @param nombreTipoOt
     * @return
     * @throws ApplicationException
     */
    @Override
    public TipoOtHhppMgl findTipoOtHhppByNombreTipoOt(String nombreTipoOt)
            throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.findTipoOtHhppByNombreTipoOt(nombreTipoOt);
    }
    
    
      /**
     * Validar que un tipo de ot no este en uso al estar asociada a una ot
     *
     * @author Juan David Hernandez
     * @param idTipoOt
     * @return verdadero si se encuentra en uso
     * @throws ApplicationException
     */
    @Override
       public boolean validarTipoOtHhppEnUso(BigDecimal idTipoOt) throws ApplicationException {
        TipoOtHhppMglManager tipoOtHhppMglManager
                = new TipoOtHhppMglManager();
        return tipoOtHhppMglManager.validarTipoOtHhppEnUso(idTipoOt);
    }


}
