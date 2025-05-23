/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.atencionInmediata.agenda.request.RequestAgendaInmediata;
import co.com.claro.atencionInmediata.agenda.request.ResponseAgendaInmediata;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.CmtAgendamientoWorkForceMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgw.agenda.enrutar.AsignarRecursoRequest;
import co.com.claro.mgw.agenda.hardclose.OrderCompleteRequest;
import co.com.claro.mgw.agenda.iniciar_visita.IniciarVisitaRequest;
import co.com.claro.mgw.agenda.nodone.UnrealizedActivityRequest;
import co.com.claro.mgw.agenda.restrition_ccmm.RestritionCcmmRequest;
import co.com.claro.mgw.agenda.restrition_ccmm.SchedulerRestrictionResponse;
import co.com.claro.mgw.agenda.suspender_visita.SuspenderVisitaRequest;
import co.com.claro.mgw.agenda.user.UserAut;
import co.com.claro.mgw.agenda.util.ServicesAgendamientosResponse;
import co.com.claro.mgw.softclose.SoftCloseRequest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bocanegravm
 */
@Path("agendamientos")
@Stateless
public class RestAgendamientosOtServices {

    @EJB
    private CmtAgendamientoWorkForceMglFacadeLocal cmtAgendamientoWorkForceMglFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal cmtHorarioRestriccionMglFacadeLocal;

    /**
     * metodo para informar el tecnico que asignado a una OT Autor: victor
     * bocanegra
     *
     * @param recursoRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("asignarRecurso")
    public ServicesAgendamientosResponse asignarRecurso(AsignarRecursoRequest recursoRequest) throws ApplicationException {
        UserAut userAut = new UserAut();
        userAut.setLogin("OFSC");
        recursoRequest.setUser(userAut);
        return cmtAgendamientoWorkForceMglFacadeLocal.asignarRecursoAgendaMgl(recursoRequest);

    }

    /**
     * metodo para informar del inicio de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param iniciarVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("iniciarVisita")
    public ServicesAgendamientosResponse iniciarVisitaAgendaMgl(IniciarVisitaRequest iniciarVisitaRequest) throws ApplicationException {
        UserAut userAut = new UserAut();
        userAut.setLogin("OFSC");
        iniciarVisitaRequest.setUser(userAut);
        return cmtAgendamientoWorkForceMglFacadeLocal.
                iniciarVisitaAgendaMgl(iniciarVisitaRequest);
    }

    /**
     * metodo no_Done para informar de la no realizacion de la actividad Autor:
     * victor bocanegra
     *
     * @param unrealizedActivityRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("noDone")
    public ServicesAgendamientosResponse actividadNoRealizadaAgendaMgl(UnrealizedActivityRequest unrealizedActivityRequest)
            throws ApplicationException {
        UserAut userAut = new UserAut();
        userAut.setLogin("OFSC");
        unrealizedActivityRequest.setUser(userAut);
        return cmtAgendamientoWorkForceMglFacadeLocal.
                actividadNoRealizadaAgendaMgl(unrealizedActivityRequest);
    }

    /**
     * metodo para informar a MGL que una OT termino Autor: victor bocanegra
     *
     * @param orderCompleteRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("hardClose")
    public ServicesAgendamientosResponse ordenTerminadaAgendaMgl(OrderCompleteRequest orderCompleteRequest)
            throws ApplicationException {
        UserAut userAut = new UserAut();
        userAut.setLogin("OFSC");
        orderCompleteRequest.setUser(userAut);
        return cmtAgendamientoWorkForceMglFacadeLocal.
                ordenTerminadaAgendaMgl(orderCompleteRequest);
    }

    /**
     * metodo para informar a MGL de las actividades realizadas y materiales
     * utilizados en la visita Autor: victor bocanegra
     *
     * @param softCloseRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("softClose")
    public ServicesAgendamientosResponse actividadesRealizadasAgendaMgl(SoftCloseRequest softCloseRequest) throws ApplicationException {

        return cmtAgendamientoWorkForceMglFacadeLocal.
                actividadesRealizadasAgendaMgl(softCloseRequest);
    }

    /**
     * metodo para consultar las restricciones de horario en una ccmm o
     * subedificio Autor: victor bocanegra
     *
     * @param restritionCcmmRequest
     * @return SchedulerRestrictionResponse
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("restritionCcmm")
    public SchedulerRestrictionResponse schedulerCcmmRestrition(RestritionCcmmRequest restritionCcmmRequest) {

        return cmtHorarioRestriccionMglFacadeLocal.
                schedulerCcmmRestrition(restritionCcmmRequest);
    }

    /**
     * metodo para informar de la suspencion de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param suspenderVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("suspenderVisita")
    public ServicesAgendamientosResponse suspenderVisitaMgl(SuspenderVisitaRequest suspenderVisitaRequest) throws ApplicationException {
        UserAut userAut = new UserAut();
        userAut.setLogin("OFSC");
        suspenderVisitaRequest.setUser(userAut);
        return cmtAgendamientoWorkForceMglFacadeLocal.
                suspenderVisitaMgl(suspenderVisitaRequest);
    }
    
    /**
     * metodo para realizar un agendamiento inmediato con la informacion del
     * Iframe Autor: victor bocanegra
     *
     * @param requestAgendaInmediata
     * @return ResponseAgendaInmediata
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("WSManageSchedulesAppointments/{workOrder}/{type}")
    public ResponseAgendaInmediata WSManageSchedulesAppointmentsMgl(
            @PathParam("workOrder") Long workOrder,
            @PathParam("type") String type,
            @QueryParam("techncianId") String techncianId,
            @QueryParam("start") String start,
            @QueryParam("end") String end,
            @QueryParam("userId") String userId) throws ApplicationException {

        RequestAgendaInmediata requestAgendaInmediata = new RequestAgendaInmediata();
        requestAgendaInmediata.setWorkOrder(workOrder);
        requestAgendaInmediata.setType(type);
        requestAgendaInmediata.setTechncianId(techncianId);
        requestAgendaInmediata.setStart(start);
        requestAgendaInmediata.setEnd(end);
        requestAgendaInmediata.setUserId(userId);
        return cmtAgendamientoWorkForceMglFacadeLocal.WSManageSchedulesAppointmentsMgl(requestAgendaInmediata);
    }
}
