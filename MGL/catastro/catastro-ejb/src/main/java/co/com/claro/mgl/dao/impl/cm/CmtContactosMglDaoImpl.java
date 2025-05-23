/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtContactosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author rodriguezluim
 */
public class CmtContactosMglDaoImpl extends GenericDaoImpl<CmtContactosMgl> {

    public List<CmtContactosMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        Query query = entityManager.createQuery("SELECT c FROM CmtContactosMgl c "
                + "where c.ccmmId = :cuentaMatriz "
                + "and c.estadoRegistro = 1 ");
        query.setParameter("cuentaMatriz", cuentaMatrizMgl);
        return query.getResultList();
    }

    public List<CmtContactosMgl> findByOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajoMgl) {
        Query query = entityManager.createQuery("SELECT c FROM CmtContactosMgl c "
                + "where c.otId = :ordenTrabajo "
                + "and c.estadoRegistro = 1 ");
        query.setParameter("ordenTrabajo", ordenTrabajoMgl);
        return query.getResultList();
    }

    /**
     * *Victor Bocanegra Metodo para buscar los contactos asociados a una OT
     * paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param cmtOrdenTrabajoMgl
     * @return List<CmtContactosMgl>
     * @throws ApplicationException
     */
    public List<CmtContactosMgl> findByOrdenTrabajo(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {

        List<CmtContactosMgl> resultList;

        if (cmtOrdenTrabajoMgl == null
                || cmtOrdenTrabajoMgl.getIdOt() == null
                || cmtOrdenTrabajoMgl.getIdOt().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("La Orden de trabajo es obligatoria");
        }

        String queryTipo = "SELECT c FROM CmtContactosMgl c "
                + "WHERE c.otId = :ordenTrabajo "
                + "AND c.estadoRegistro = 1 ";

        Query query = entityManager.createQuery(queryTipo);
        query.setParameter("ordenTrabajo", cmtOrdenTrabajoMgl);

        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtContactosMgl>) query.getResultList();

        return resultList;
    }

    /**
     * *Victor Bocanegra Metodo para contar los contactos asociados a una OT
     * paginados en la tabla
     *
     * @param cmtOrdenTrabajoMgl
     * @return Long
     * @throws ApplicationException
     */
    public Long countContactosByOT(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;

        if (cmtOrdenTrabajoMgl == null
                || cmtOrdenTrabajoMgl.getIdOt() == null
                || cmtOrdenTrabajoMgl.getIdOt().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("La Orden de trabajo es obligatoria");
        }

        queryTipo = "SELECT COUNT(1) FROM CmtContactosMgl c "
                + "WHERE c.otId = :ordenTrabajo "
                + "AND c.estadoRegistro = 1 ";

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("ordenTrabajo", cmtOrdenTrabajoMgl);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }
}
