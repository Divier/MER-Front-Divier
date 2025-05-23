/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtEstablecimientoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class CmtEstablecimientoMglManager {

    private CmtEstablecimientoMglDaoImpl cmtEstablecimientoMglDaoImpl = new CmtEstablecimientoMglDaoImpl();

    public CmtEstablecimientoCmMgl create(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl, String usuario, int perfil) throws ApplicationException {
        return cmtEstablecimientoMglDaoImpl.createCm(cmtEstablecimientoCmMgl, usuario, perfil);
    }

    public CmtEstablecimientoCmMgl update(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl, String usuario, int perfil) throws ApplicationException {
        return cmtEstablecimientoMglDaoImpl.updateCm(cmtEstablecimientoCmMgl, usuario, perfil);
    }

    public boolean delete(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl, String usuario, int perfil) throws ApplicationException {
        return cmtEstablecimientoMglDaoImpl.deleteCm(cmtEstablecimientoCmMgl, usuario, perfil);
    }

    public CmtEstablecimientoCmMgl finById(BigDecimal id) throws ApplicationException {
        return cmtEstablecimientoMglDaoImpl.finById(id);
    }

    public List<CmtEstablecimientoCmMgl> finBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) {
        return cmtEstablecimientoMglDaoImpl.finBySubEdificio(cmtSubEdificioMgl);
    }
    
    public List<AuditoriaDto> construirAuditoria(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtEstablecimientoCmMgl, CmtEstablecimientoCmAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtEstablecimientoCmMgl, CmtEstablecimientoCmAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtEstablecimientoCmMgl);
        return listAuditoriaDto;
    }      
}
