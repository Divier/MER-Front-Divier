/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtNotasSolicitudVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudVtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtNotasSolicitudVtMglFacade implements CmtNotasSolicitudVtMglFacadeLocal {
    
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
    public List<CmtNotasSolicitudVtMgl> findTipoNotasId(BigDecimal tipoNotasId) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager 
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.findTipoNotasId(tipoNotasId);
    }

     /**
     * Listado de notas por id de solicitud
     *
     * @author Juan David Hernandez
     * @param idSolicitudCm
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<CmtNotasSolicitudVtMgl> 
            findNotasBySolicitudId(BigDecimal idSolicitudCm) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.findNotasBySolicitudId(idSolicitudCm);
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
    public CmtNotasSolicitudVtMgl crearNotSol(CmtNotasSolicitudVtMgl t) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.create(t,user, perfil);
    }

    public CmtNotasSolicitudVtMgl update(CmtNotasSolicitudVtMgl t) 
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager 
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.update(t);
    }

    public boolean delete(CmtNotasSolicitudVtMgl t) throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.delete(t);
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
    public CmtNotasSolicitudVtMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasMglMglManager 
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasMglMglManager.findById(id);
    }

    public CmtNotasSolicitudVtMgl findById(CmtNotasSolicitudVtMgl sqlData)
            throws ApplicationException {
        CmtNotasSolicitudVtMglManager cmtNotasSolicitudVtMglManager
                = new CmtNotasSolicitudVtMglManager();
        return cmtNotasSolicitudVtMglManager.findById(sqlData);
    }
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
}
