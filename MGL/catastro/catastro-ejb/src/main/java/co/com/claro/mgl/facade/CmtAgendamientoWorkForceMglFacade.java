/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.atencionInmediata.agenda.request.RequestAgendaInmediata;
import co.com.claro.atencionInmediata.agenda.request.ResponseAgendaInmediata;
import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgw.agenda.enrutar.AsignarRecursoRequest;
import co.com.claro.mgw.agenda.hardclose.OrderCompleteRequest;
import co.com.claro.mgw.agenda.iniciar_visita.IniciarVisitaRequest;
import co.com.claro.mgw.agenda.nodone.UnrealizedActivityRequest;
import co.com.claro.mgw.agenda.suspender_visita.SuspenderVisitaRequest;
import co.com.claro.mgw.agenda.util.ServicesAgendamientosResponse;
import co.com.claro.mgw.softclose.SoftCloseRequest;
import javax.ejb.Stateless;


/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtAgendamientoWorkForceMglFacade implements CmtAgendamientoWorkForceMglFacadeLocal {

    /**
     * metodo para asignar un tecnico a la agenda de OT Autor: victor bocanegra
     *
     * @param asignarRecursoRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public ServicesAgendamientosResponse asignarRecursoAgendaMgl(AsignarRecursoRequest asignarRecursoRequest) throws ApplicationException {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.asignarRecursoAgendaMgl(asignarRecursoRequest);
    }

    /**
     * metodo para informar del inicio de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param iniciarVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public ServicesAgendamientosResponse iniciarVisitaAgendaMgl(IniciarVisitaRequest iniciarVisitaRequest) throws ApplicationException {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.iniciarVisitaAgendaMgl(iniciarVisitaRequest);
    }

    /**
     * metodo no_Done para informar de la no realizacion de la actividad Autor:
     * victor bocanegra
     *
     * @param unrealizedActivityRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public ServicesAgendamientosResponse actividadNoRealizadaAgendaMgl(UnrealizedActivityRequest unrealizedActivityRequest) throws ApplicationException {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.actividadNoRealizadaAgendaMgl(unrealizedActivityRequest);
    }

    /**
     * metodo para informar a MGL que una OT termino Autor: victor bocanegra
     *
     * @param orderCompleteRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public ServicesAgendamientosResponse ordenTerminadaAgendaMgl(OrderCompleteRequest orderCompleteRequest) throws ApplicationException {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.ordenTerminadaAgendaMgl(orderCompleteRequest);
    }

    /**
     * metodo para informar a MGL de las actividades realizadas y materiales
     * utilizados en la visita Autor: victor bocanegra
     *
     * @param softCloseRequest
     * @return ServicesAgendamientosResponse
     */
    @Override
    public ServicesAgendamientosResponse actividadesRealizadasAgendaMgl(SoftCloseRequest softCloseRequest) {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.actividadesRealizadasAgendaMgl(softCloseRequest);
    }

    /**
     * metodo para informar de la suspencion de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param suspenderVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public ServicesAgendamientosResponse suspenderVisitaMgl(SuspenderVisitaRequest suspenderVisitaRequest) throws ApplicationException {

        CmtAgendamientoMglManager cmtAgendamientoMglManager = new CmtAgendamientoMglManager();
        return cmtAgendamientoMglManager.suspenderVisitaMgl(suspenderVisitaRequest);
    }
    
    /**
     * metodo para realizar un agendamiento inmediato con la informacion del
     * Iframe Autor: victor bocanegra
     *
     * @param requestAgendaInmediata
     * @return ResponseAgendaInmediata
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public ResponseAgendaInmediata WSManageSchedulesAppointmentsMgl(RequestAgendaInmediata requestAgendaInmediata)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.WSManageSchedulesAppointmentsMgl(requestAgendaInmediata);
    }
}
