/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Juan David Hernandez
 */
import co.com.claro.mgl.businessmanager.cm.CmtGrupoProyectoValidacionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class CmtGrupoProyectoValidacionMglFacade implements CmtGrupoProyectoValidacionMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtGrupoProyectoValidacionMglFacade.class);
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtGrupoProyectoValidacionMgl> findAll() throws ApplicationException {
        CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager = new CmtGrupoProyectoValidacionMglManager();
        return cmtGrupoProyectoValidacionMglManager.findAll();
    }

    @Override
    public CmtGrupoProyectoValidacionMgl create(CmtGrupoProyectoValidacionMgl t) throws ApplicationException {
        CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager = new CmtGrupoProyectoValidacionMglManager();
        return cmtGrupoProyectoValidacionMglManager.create(t, user, perfil);
    }

    @Override
    public CmtGrupoProyectoValidacionMgl update(CmtGrupoProyectoValidacionMgl t) throws ApplicationException {
        CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager = new CmtGrupoProyectoValidacionMglManager();
        return cmtGrupoProyectoValidacionMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtGrupoProyectoValidacionMgl t) throws ApplicationException {
        CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager = new CmtGrupoProyectoValidacionMglManager();
        try {
            return cmtGrupoProyectoValidacionMglManager.delete(t, user, perfil);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public CmtGrupoProyectoValidacionMgl findById(CmtGrupoProyectoValidacionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public List<CmtGrupoProyectoValidacionMgl> findPendientesParaGestionPaginacion(int page, int filasPag) {
        try {
            CmtGrupoProyectoValidacionMglManager cmtGrupoProyectoValidacionMglManager = new CmtGrupoProyectoValidacionMglManager();

            return cmtGrupoProyectoValidacionMglManager.findPendientesParaGestionPaginacion(page, filasPag);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }
    }
    }
