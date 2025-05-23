/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface CmtTipoEstablecimientoFacadeLocal extends BaseFacadeLocal<CmtEstablecimientoCmMgl>{
    public List<CmtEstablecimientoCmMgl> findByCompania(BigDecimal id);
}
