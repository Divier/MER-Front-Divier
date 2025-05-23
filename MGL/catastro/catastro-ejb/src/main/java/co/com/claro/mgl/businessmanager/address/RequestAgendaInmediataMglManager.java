/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RequestAgendaInmediataMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class RequestAgendaInmediataMglManager {

    /**
     * Crea un RequestAgendaInmediataMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param requestAgendaInmediataMgl
     * @return RequestAgendaInmediataMgl creado en el repositorio
     * @throws ApplicationException
     */
    public RequestAgendaInmediataMgl create(RequestAgendaInmediataMgl requestAgendaInmediataMgl)
            throws ApplicationException {

        RequestAgendaInmediataMglDaoImpl dao = new RequestAgendaInmediataMglDaoImpl();
        return dao.create(requestAgendaInmediataMgl);
    }

    /**
     * Modifica un RequestAgendaInmediataMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param requestAgendaInmediataMgl
     * @return RequestAgendaInmediataMgl modificado en el repositorio
     * @throws ApplicationException
     */
    public RequestAgendaInmediataMgl update(RequestAgendaInmediataMgl requestAgendaInmediataMgl)
            throws ApplicationException {

        RequestAgendaInmediataMglDaoImpl dao = new RequestAgendaInmediataMglDaoImpl();
        return dao.update(requestAgendaInmediataMgl);
    }
    
    /**
     * Consulta de agendas pendientes por agendar Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo de
     * CCMM
     * @param otHhppMglorden de trabajo direcciones
     * @return {@link List}&lt;{@link RequestAgendaInmediataMgl}> Listado de
     * pendientes
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<RequestAgendaInmediataMgl> agendasPendienteByOrden(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            OtHhppMgl otHhppMgl)
            throws ApplicationException {

        RequestAgendaInmediataMglDaoImpl dao = new RequestAgendaInmediataMglDaoImpl();
        return dao.agendasPendienteByOrden(ordenTrabajoMgl, otHhppMgl);
    }

}
