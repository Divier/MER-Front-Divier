/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtConstructuraSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtConstructuraSolicitudMglFacadeLocal extends BaseFacadeLocal<CmtConstructuraSolicitudMgl> {

    public List<CmtConstructuraSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException;
}
