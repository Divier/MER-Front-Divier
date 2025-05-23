/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionAlternaMgl;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author User
 */
public interface CmtDireccionAlternaMglFacadeLocal extends BaseFacadeLocal<CmtDireccionAlternaMgl> {
    
    public List<CmtDireccionAlternaMgl> findTipoBasicaId(BigDecimal tipoBasicaId) throws ApplicationException;

    public CmtDireccionAlternaMgl findById(BigDecimal id) throws ApplicationException;

    @Override
    public CmtDireccionAlternaMgl findById(CmtDireccionAlternaMgl sqlData) throws ApplicationException;
}
