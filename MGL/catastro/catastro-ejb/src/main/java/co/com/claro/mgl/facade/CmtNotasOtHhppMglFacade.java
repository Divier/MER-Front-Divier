/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.CmtNotasOtHhppMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtNotasOtHhppMglFacade implements CmtNotasOtHhppMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;
    
      /**
     * Lista los diferentes tipos de notas por el ID.
     *
     * @author Juan David Hernandez
     * @param tipoNotasId
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<CmtNotasOtHhppMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.findTipoNotasId(tipoNotasId);
    }

     /**
     * Listado de notas por id de hhpp
     *
     * @author Juan David Hernandez
     * @param idHhpp
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<CmtNotasOtHhppMgl> 
            findNotasByOtHhppId(BigDecimal idHhpp) 
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.findNotasByOtHhppId(idHhpp);
    }

     /**
     * Crea una nota en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtNotasOtHhppMgl crearNotSol(CmtNotasOtHhppMgl t) 
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.create(t,user, perfil);
    }

    public CmtNotasOtHhppMgl update(CmtNotasOtHhppMgl t) 
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.update(t);
    }

    public boolean delete(CmtNotasOtHhppMgl t) throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.delete(t);
    }

    /**
     * Lista notas tipos por el ID.
     *
     * @author Juan David Hernandez
     * @param id
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtNotasOtHhppMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.findById(id);
    }

    public CmtNotasOtHhppMgl findById(CmtNotasOtHhppMgl sqlData)
            throws ApplicationException {
        CmtNotasOtHhppMglManager cmtNotasHhppVtMglManager
                = new CmtNotasOtHhppMglManager();
        return cmtNotasHhppVtMglManager.findById(sqlData);
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
}
