/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtComentarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Dao Comentario Nota Orden de Trabajo. Contiene la logica de acceso a datos de
 * los comentatirios de las notas de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtNotaOtComentarioMglDaoImpl extends GenericDaoImpl<CmtNotaOtComentarioMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtNotaOtComentarioMglDaoImpl.class);

    /**
     * Consulta lista de comentarios asociados a una nota de OT.
     *
     * @author Victor Bocanegra Ortiz
     * @param notaOtMgl nota de la orden de trabajo
     * @return List<CmtNotaOtComentarioMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtNotaOtComentarioMgl> findByNotaOt(CmtNotaOtMgl notaOtMgl)
            throws ApplicationException {


        List<CmtNotaOtComentarioMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtNotaOtComentarioMgl a "
                    + " WHERE a.notaOtObj = :notaOtObj AND a.estadoRegistro = 1";

            Query query = entityManager.createQuery(consulta);

            if (notaOtMgl != null) {
                query.setParameter("notaOtObj", notaOtMgl);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtNotaOtComentarioMgl>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando los comentarios de la nota. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
