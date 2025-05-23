/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtEstadoInternoGaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CmtEstadoInternoGaMglFacade implements CmtEstadoInternoGaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtEstadoInternoGaMgl> findAll() throws ApplicationException {
        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.findAll();
    }

    @Override
    public boolean existeCodigo(String codigo) throws ApplicationException {
        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.existeCodigo(codigo);
    }    
    
    @Override
    public CmtEstadoInternoGaMgl create(CmtEstadoInternoGaMgl t) throws ApplicationException {
        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.create(t, user, perfil);
    }

    @Override
    public CmtEstadoInternoGaMgl update(CmtEstadoInternoGaMgl t) throws ApplicationException {
        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtEstadoInternoGaMgl t) throws ApplicationException {
        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.delete(t,user,perfil);
    }

    @Override
    public CmtEstadoInternoGaMgl findById(CmtEstadoInternoGaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CmtEstadoInternoGaMgl> findByFiltro(CmtBasicaMgl estadoIniCmFiltro,
            CmtBasicaMgl estadoExternoAntGaFiltro,
            CmtBasicaMgl estadoInternoGaFiltro,
            CmtBasicaMgl estadoExternoPostGaFiltro,
            CmtBasicaMgl estadoFinalCmFiltro,
            CmtBasicaMgl tipoGaFiltro) throws ApplicationException {

        CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
        return manager.findByFiltro(estadoIniCmFiltro, estadoExternoAntGaFiltro,
                estadoInternoGaFiltro, estadoExternoPostGaFiltro, estadoFinalCmFiltro,tipoGaFiltro);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    @Override
    public List<AuditoriaDto> construirAuditoria(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
    
            CmtEstadoInternoGaMglManager manager = new CmtEstadoInternoGaMglManager();
            return manager.construirAuditoria(cmtEstadoInternoGaMgl);
    }

}
