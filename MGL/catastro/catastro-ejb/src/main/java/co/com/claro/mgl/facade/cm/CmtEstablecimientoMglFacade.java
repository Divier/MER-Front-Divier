/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtEstablecimientoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ADMIN
 */
@Stateless
public class CmtEstablecimientoMglFacade implements CmtEstablecimientoMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private CmtEstablecimientoMglManager cmtEstablecimientoMglManager = new CmtEstablecimientoMglManager();

    @Override
    public CmtEstablecimientoCmMgl create(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException {
        return cmtEstablecimientoMglManager.create(cmtEstablecimientoCmMgl, user, perfil);
    }

    @Override
    public CmtEstablecimientoCmMgl update(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException {
        return cmtEstablecimientoMglManager.update(cmtEstablecimientoCmMgl, user, perfil);
    }

    @Override
    public boolean delete(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl) throws ApplicationException {
        return cmtEstablecimientoMglManager.delete(cmtEstablecimientoCmMgl, user, perfil);
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
    public CmtEstablecimientoCmMgl finById(BigDecimal id) throws ApplicationException {
        return cmtEstablecimientoMglManager.finById(id);
    }

    @Override
    public List<CmtEstablecimientoCmMgl> finBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) {
        return cmtEstablecimientoMglManager.finBySubEdificio(cmtSubEdificioMgl);
    }
    @Override
    public List<AuditoriaDto> construirAuditoria(CmtEstablecimientoCmMgl cmtEstablecimientoCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
     return cmtEstablecimientoMglManager.construirAuditoria(cmtEstablecimientoCmMgl);
    }
}
