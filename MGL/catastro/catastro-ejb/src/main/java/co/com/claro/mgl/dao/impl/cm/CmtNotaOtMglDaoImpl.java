/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao Nota Orden de Trabajo. Contiene la logica de acceso a datos de las notas
 * de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtNotaOtMglDaoImpl extends GenericDaoImpl<CmtNotaOtMgl> {

    /**
     * Busca las notas asociadas a una Ordene de Trabajo. Permite realizar la
     * busqueda de las notas de una Ordene de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ordenTrabajo orden de trabajo
     * @return notas asociadas a una orden de trabajo
     * @throws ApplicationException
     */
    public List<CmtNotaOtMgl> findByOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        List<CmtNotaOtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotaOtMgl.findByOt");
        query.setParameter("ordenTrabajo", ordenTrabajo);
        resultList = (List<CmtNotaOtMgl>) query.getResultList();
        return resultList;
    }
    
    /**
     * Busca las notas asociadas a una Cuenta Matriz.Permite realizar la
 busqueda de las notas de una Cuenta Matriz en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz Cuenta Matriz
     * @param tipoNota
     * @return notas asociadas a una Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtNotaOtMgl> findByCm(CmtCuentaMatrizMgl cuentaMatriz,BigDecimal tipoNota) throws ApplicationException {
        List<CmtNotaOtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtNotaOtMgl.findByCm");
        query.setParameter("cuentaMatriz", cuentaMatriz);
        query.setParameter("tipoNota", tipoNota);
        resultList = (List<CmtNotaOtMgl>) query.getResultList();
        return resultList;
    }
}
