/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtSubedificiosSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author GLAFH
 */
public interface CmtSubedificiosSolicitudMglFacadeLocal extends BaseFacadeLocal<CmtSubedificiosSolicitudMgl> {

    public List<CmtSubedificiosSolicitudMgl> findByIdMultiSol(BigDecimal idMultiSol) throws ApplicationException;
}
