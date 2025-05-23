/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtTpMultiedificioSolicitudMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtTpMultiedificioSolicitudMglFacadeLocal extends BaseFacadeLocal<CmtTpMultiedificioSolicitudMgl> {

    public List<CmtTpMultiedificioSolicitudMgl> findByIdSol(BigDecimal IdSolcm) throws ApplicationException;
}
