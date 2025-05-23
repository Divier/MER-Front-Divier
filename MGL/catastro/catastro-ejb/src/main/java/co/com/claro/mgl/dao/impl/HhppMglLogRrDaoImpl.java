/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMglLogRr;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author bocanegravm
 */
public class HhppMglLogRrDaoImpl extends GenericDaoImpl<HhppMglLogRr> {

    private static final Logger LOGGER = LogManager.getLogger(HhppMglLogRrDaoImpl.class);

    /**
     * Funcion para el truncate de la tabla que alberga el registro de los hhpp
     * a modificar en MGL
     *
     * Autor:Victor Bocanegra
     *
     * @return true
     * @throws ApplicationException
     */
    public boolean truncateTable() throws ApplicationException {
        try {
            StoredProcedureQuery trunca = entityManager.createNamedStoredProcedureQuery("HhppMglLogRr.trucateLogHhppRr");
            return trunca.execute();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta los datos en la tabla de log de hhpp en MGL
     *
     * @author Victor Bocanegra
     * @return Long total hhpp encontrados.
     */
    public Long countHhppMglLogRr() {

        Long resultCount = 0L;
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT h FROM HhppMglLogRr h ");

            Query query = entityManager.createQuery(sql.toString());

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HhppMglLogRr> lst = (List<HhppMglLogRr>) query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                int registros = lst.size();
                resultCount = new Long(registros);
            }
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando en la tabla de logs de hhpp. ", e);
        }

        return resultCount;
    }

    /**
     * consulta los hhpp en la tabla de log de hhpp en MGL
     *
     * @author Victor Bocanegra
     * @param paginaSelected
     * @param maxResults
     * @return List<HhppMglLogRr> hhpp encontrados.
     */
    public List<HhppMglLogRr> findHhppMglLogRr(int paginaSelected,
            int maxResults) {

        List<HhppMglLogRr> resultList = null;
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT h FROM HhppMglLogRr h ");

            Query query = entityManager.createQuery(sql.toString());
            query.setFirstResult(paginaSelected);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            resultList = (List<HhppMglLogRr>) query.getResultList();

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando en la tabla de logs de hhpp. ", e);
        }

        return resultList;
    }

}
