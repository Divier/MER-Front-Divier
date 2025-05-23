/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.CmtNotasHhppVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtNotasHhppVtMglFacade implements CmtNotasHhppVtMglFacadeLocal {
    
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
    public List<CmtNotasHhppVtMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasHhppVtMglManager();
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
    public List<CmtNotasHhppVtMgl> 
            findNotasByHhppId(BigDecimal idHhpp) 
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager
                = new CmtNotasHhppVtMglManager();
        return cmtNotasHhppVtMglManager.findNotasByHhppId(idHhpp);
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
    public CmtNotasHhppVtMgl crearNotSol(CmtNotasHhppVtMgl t) 
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager
                = new CmtNotasHhppVtMglManager();
        return cmtNotasHhppVtMglManager.create(t,user, perfil);
    }

    public CmtNotasHhppVtMgl update(CmtNotasHhppVtMgl t) 
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasHhppVtMglManager();
        return cmtNotasHhppVtMglManager.update(t);
    }

    public boolean delete(CmtNotasHhppVtMgl t) throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager
                = new CmtNotasHhppVtMglManager();
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
    public CmtNotasHhppVtMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager 
                = new CmtNotasHhppVtMglManager();
        return cmtNotasHhppVtMglManager.findById(id);
    }

    public CmtNotasHhppVtMgl findById(CmtNotasHhppVtMgl sqlData)
            throws ApplicationException {
        CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager
                = new CmtNotasHhppVtMglManager();
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

    @Override
    public CmtNotasHhppVtMgl create(CmtNotasHhppVtMgl cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
     CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager
                = new CmtNotasHhppVtMglManager();
        return cmtNotasHhppVtMglManager.create(cmtNotasMgl, usuario, perfil);
    }
    
}
