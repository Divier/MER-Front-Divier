/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SlaEjecucionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class SlaEjecucionMglDaoImpl extends GenericDaoImpl<SlaEjecucionMgl> {

    private static final Logger LOGGER = LogManager.getLogger(SlaEjecucionMglDaoImpl.class);

    /**
     * Autor: victor bocanegra Metodo para consultar todos los Sla de ejecucion
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<SlaEjecucionMgl> findAll() throws ApplicationException {
        Query query = entityManager.createNamedQuery("SlaEjecucionMgl.findAll");
        return query.getResultList();
    }

    /**
     * Autor: victor bocanegra Metodo para consultar un sla de ejecucion por
     * tecnologia y tipo de ejecucion activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public SlaEjecucionMgl findByTecnoAndEjecucion(CmtBasicaMgl tecBasicaMgl, String tipoEjecucion)
            throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("SlaEjecucionMgl.findByTecnoAndEjecucion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (tecBasicaMgl != null) {
                query.setParameter("basicaIdTecnologia", tecBasicaMgl);
            }
            if (tipoEjecucion != null) {
                query.setParameter("tipoEjecucion", tipoEjecucion);
            }

            List<?> list = query.getResultList();
            return list.isEmpty()?null:(SlaEjecucionMgl)list.get(0);

        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

}
