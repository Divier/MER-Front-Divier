/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RequestAgendaInmediataMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.RequestAgendaInmediataMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class RequestAgendaInmediataMglFacade implements RequestAgendaInmediataMglFacadeLocal {

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
    @Override
    public List<RequestAgendaInmediataMgl> agendasPendienteByOrden(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            OtHhppMgl otHhppMgl)
            throws ApplicationException {
        RequestAgendaInmediataMglManager manager = new RequestAgendaInmediataMglManager();
        return manager.agendasPendienteByOrden(ordenTrabajoMgl, otHhppMgl);
    }

}
