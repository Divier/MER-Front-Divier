package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtVigenciaCostoItemMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVigenciaCostoItemMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Contiene la logiga de negocio relacionada con la clase
 * CmtVigenciaCostoItemMgl.
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
public class CmtVigenciaCostoItemMglManager {

    /**
     * findByItem es empleado para consultar los registros de VigenciaCosto por
     * un Item especifico
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param idItem
     * @return
     * @throws ApplicationException
     */
    public List<CmtVigenciaCostoItemMgl> findByItem(BigDecimal idItem) throws ApplicationException {
        CmtVigenciaCostoItemMglDaoImpl daoImpl = new CmtVigenciaCostoItemMglDaoImpl();
        return daoImpl.findByItem(idItem);
    }

    /**
     * findByPeriod es empleado para consultar los registros de VigenciaCosto
     * especificando una fecha inicial y una final que delimitan el periodo para
     * el que sera aplicado el costo registrado.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws ApplicationException
     */
    public List<CmtVigenciaCostoItemMgl> findByPeriod
            (Date fechaInicio, Date fechaFin) throws ApplicationException {
        CmtVigenciaCostoItemMglDaoImpl daoImpl = new CmtVigenciaCostoItemMglDaoImpl();
        return daoImpl.findByPeriod(fechaInicio, fechaFin);
    }

    /**
     * findByItemPeriod es empleado para consultar los registros de
     * VigenciaCosto especificando una fecha inicial y una final que delimitan
     * el periodo para el que sera aplicado el costo registrado y tambien
     * especificando el item al que aplica el periodo.alejandro.martinez.ext@claro.com.co
     *
     *
     * @param itemObj
     * @return
     * @throws ApplicationException
     */
    public CmtVigenciaCostoItemMgl findByItemVigencia
            (CmtItemMgl itemObj) throws ApplicationException {
        CmtVigenciaCostoItemMglDaoImpl daoImpl = new CmtVigenciaCostoItemMglDaoImpl();
        return daoImpl.findByItemVigencia(itemObj);
    }
}
