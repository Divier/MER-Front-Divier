/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtNotaOtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ortizjaf
 */
@Stateless
public class CmtNotaOtMglFacade implements CmtNotaOtMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public CmtNotaOtMgl crear(CmtNotaOtMgl notaOtMgl) throws ApplicationException {
        CmtNotaOtMglManager manager = new CmtNotaOtMglManager();
        return manager.crear(notaOtMgl, user, perfil);
    }

    @Override
    public List<CmtNotaOtMgl> findByOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        CmtNotaOtMglManager manager = new CmtNotaOtMglManager();
        return manager.findByOt(ordenTrabajo);
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
    public List<CmtNotaOtMgl> findByCm(CmtCuentaMatrizMgl cuentaMatriz, BigDecimal tipoNota) throws ApplicationException {
        CmtNotaOtMglManager manager = new CmtNotaOtMglManager();
        return manager.findByCm(cuentaMatriz, tipoNota);
    }
}
