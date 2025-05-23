/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoEstablecimientoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class CmtTipoEstablecimientoFacade implements CmtTipoEstablecimientoFacadeLocal {

    CmtTipoEstablecimientoManager tipoEstablecimientoManager = new CmtTipoEstablecimientoManager();

    @Override
    public List<CmtEstablecimientoCmMgl> findByCompania(BigDecimal id) {
        return tipoEstablecimientoManager.findByCompania(id);
    }

    @Override
    public List<CmtEstablecimientoCmMgl> findAll() throws ApplicationException {
        CmtTipoEstablecimientoManager cmtTipoEstablecimientoManager = new CmtTipoEstablecimientoManager();
        return cmtTipoEstablecimientoManager.findAll();

    }

    @Override
    public CmtEstablecimientoCmMgl create(CmtEstablecimientoCmMgl t) throws ApplicationException {
        return tipoEstablecimientoManager.create(t);
    }

    @Override
    public CmtEstablecimientoCmMgl update(CmtEstablecimientoCmMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtEstablecimientoCmMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtEstablecimientoCmMgl findById(CmtEstablecimientoCmMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
