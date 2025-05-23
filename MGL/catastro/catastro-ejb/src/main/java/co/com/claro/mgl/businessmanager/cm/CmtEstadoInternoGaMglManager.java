/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtEstadoInternoGaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author User
 */
public class CmtEstadoInternoGaMglManager {

    public List<CmtEstadoInternoGaMgl> findAll() throws ApplicationException {
        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        return daoImpl.findAll();
    }
    
    public boolean existeCodigo(String codigo) throws ApplicationException {
        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        List<CmtEstadoInternoGaMgl> listCmtEstadoInternoGaMgl=daoImpl.findByCodigo(codigo);
        if(listCmtEstadoInternoGaMgl!= null && listCmtEstadoInternoGaMgl.size()>0){
            return true;
        }
        return false;
    }    

    public CmtEstadoInternoGaMgl create(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl, String user, int perfil) throws ApplicationException {
        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        return daoImpl.createCm(cmtEstadoInternoGaMgl,user,perfil);
    }

    public CmtEstadoInternoGaMgl update(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl, String user, int perfil) throws ApplicationException {
        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        return daoImpl.updateCm(cmtEstadoInternoGaMgl,user,perfil);
    }

    public boolean delete(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl, String user, int perfil) throws ApplicationException {
        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        return daoImpl.deleteCm(cmtEstadoInternoGaMgl,user,perfil);
    }

    public List<CmtEstadoInternoGaMgl> findByFiltro(CmtBasicaMgl estadoIniCmFiltro,
            CmtBasicaMgl estadoExternoAntGaFiltro,
            CmtBasicaMgl estadoInternoGaFiltro,
            CmtBasicaMgl estadoExternoPostGaFiltro,
            CmtBasicaMgl estadoFinalCmFiltro,
            CmtBasicaMgl tipoGaFiltro) throws ApplicationException {

        CmtEstadoInternoGaMglDaoImpl daoImpl = new CmtEstadoInternoGaMglDaoImpl();
        return daoImpl.findByFiltro(estadoIniCmFiltro, estadoExternoAntGaFiltro, 
                estadoInternoGaFiltro, estadoExternoPostGaFiltro, estadoFinalCmFiltro,
                tipoGaFiltro);

    }

    public List<AuditoriaDto> construirAuditoria(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtEstadoInternoGaMgl, CmtEstadoInternoGaAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtEstadoInternoGaMgl, CmtEstadoInternoGaAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtEstadoInternoGaMgl);
        return listAuditoriaDto;
    }    
    
}
