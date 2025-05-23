/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ortizjaf
 */
public interface CmtNotaOtMglFacadeLocal {
    
    CmtNotaOtMgl crear(CmtNotaOtMgl notaOtMgl) throws ApplicationException ;
    List<CmtNotaOtMgl> findByOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException;
    void setUser(String mUser, int mPerfil) throws ApplicationException;
    List<CmtNotaOtMgl> findByCm(CmtCuentaMatrizMgl cuentaMatriz,BigDecimal tipoNota) throws ApplicationException;
    
}
