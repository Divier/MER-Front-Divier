/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTipoEstablecimientoDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Parzifal de León
 */
public class CmtTipoEstablecimientoManager {

    CmtTipoEstablecimientoDaoImpl cmtTipoEstablecimientoDao = new CmtTipoEstablecimientoDaoImpl();

    public List<CmtEstablecimientoCmMgl> findByCompania(BigDecimal id) {
        return cmtTipoEstablecimientoDao.findByCompania(id);
    }

    public CmtEstablecimientoCmMgl create(CmtEstablecimientoCmMgl cmtBasicaMgl) throws ApplicationException {
        return cmtTipoEstablecimientoDao.create(cmtBasicaMgl);
    }

    public List<CmtEstablecimientoCmMgl> findAll() throws ApplicationException {
        return cmtTipoEstablecimientoDao.findAll();
    }
}
