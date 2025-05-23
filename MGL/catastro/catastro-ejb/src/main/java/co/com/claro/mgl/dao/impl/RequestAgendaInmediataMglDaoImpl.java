/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class RequestAgendaInmediataMglDaoImpl extends GenericDaoImpl<RequestAgendaInmediataMgl>{

    private static final Logger LOGGER = LogManager.getLogger(RequestAgendaInmediataMglDaoImpl.class);
    
    /**
     * Consulta de agendas pendientes por agendar
     * Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo de CCMM
     * @param otHhppMglorden de trabajo direcciones
     * @return {@link List}&lt;{@link RequestAgendaInmediataMgl}> Listado de pendientes
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<RequestAgendaInmediataMgl> agendasPendienteByOrden(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            OtHhppMgl otHhppMgl)
            throws ApplicationException {

        List<RequestAgendaInmediataMgl> resultList;
        try {
            String consulta = "SELECT r FROM RequestAgendaInmediataMgl r "
                    + " WHERE r.estado IN (0,2) ";

            if (ordenTrabajoMgl != null) {
                consulta += " AND r.ordenTrabajoMgl = :ordenTrabajoMgl ";
            }

            if (otHhppMgl != null) {
                consulta += " AND r.ordenDirHhppMgl = :ordenDirHhppMgl ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                query.setParameter("ordenTrabajoMgl", ordenTrabajoMgl);
            }

            if (otHhppMgl != null) {
                query.setParameter("ordenDirHhppMgl", otHhppMgl);

            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<RequestAgendaInmediataMgl>) query.getResultList();
            getEntityManager().clear();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando los pendientes de agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
