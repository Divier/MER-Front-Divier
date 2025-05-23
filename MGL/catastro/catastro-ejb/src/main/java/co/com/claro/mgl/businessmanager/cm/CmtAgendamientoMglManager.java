package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.atencionInmediata.agenda.request.RequestAgendaInmediata;
import co.com.claro.atencionInmediata.agenda.request.ResponseAgendaInmediata;
import co.com.claro.mgl.businessmanager.agendamiento.AgendamientoWorkForceMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtAgendamientoMglDaoImpl;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendaAuditoria;
import co.com.claro.mgw.agenda.enrutar.AsignarRecursoRequest;
import co.com.claro.mgw.agenda.hardclose.OrderCompleteRequest;
import co.com.claro.mgw.agenda.iniciar_visita.IniciarVisitaRequest;
import co.com.claro.mgw.agenda.nodone.UnrealizedActivityRequest;
import co.com.claro.mgw.agenda.suspender_visita.SuspenderVisitaRequest;
import co.com.claro.mgw.agenda.util.ServicesAgendamientosResponse;
import co.com.claro.mgw.softclose.SoftCloseRequest;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wgavidia
 */
public class CmtAgendamientoMglManager {

    /**
     * M&eacute;todo para persistir el agendamiento realizado
     *
     * @param agenda {@link CmtAgendamientoMgl} agenda solicitada
     * @param user {@link String} Usaurio que realiza la acci&oacute;n
     * @param perfil {@link String} perfil del usuario
     * @return {@link CmtAgendamientoMgl} agendamiento persistido
     * @throws ApplicationException Excepci&oacute;n lanzada al crear el
     * registro
     */
    public CmtAgendamientoMgl create(CmtAgendamientoMgl agenda,
            String user, Integer perfil) throws ApplicationException {
        CmtAgendamientoMglDaoImpl agendamientoDao = new CmtAgendamientoMglDaoImpl();
        return agendamientoDao.createCm(agenda, user, perfil);
    }

    /**
     * M&eacute;todo para actualizar el agendamiento
     *
     * @param agenda {@link CmtAgendamientoMgl} agenda para actualizar
     * @param user {@link String} Usaurio que realiza la accion
     * @param perfil {@link String} perfil del usuario
     * @return {@link CmtAgendamientoMgl} agendamiento actualizado
     * @throws ApplicationException Excepci&oacute;n lanzada al actualizar el
     * registro
     */
    public CmtAgendamientoMgl update(CmtAgendamientoMgl agenda,
            String user, Integer perfil) throws ApplicationException {
        CmtAgendamientoMglDaoImpl agendamientoDao = new CmtAgendamientoMglDaoImpl();
        return agendamientoDao.updateCm(agenda, user, perfil);
    }

    /**
     * M&eacute;todo para consultar la disponibilidad en el agendamiento
     *
     * @param ot {@link CmtOrdenTrabajoMgl} ot para la cual se busca la
     * capacidad
     * @param usuario {@link Usuarios} usuario de sesion
     * @param numeroOtOfsc {@link String} numero de la ot en OFSC
     * @param nodo {@link String} nodo para el agendamiento
     * @return {@link List}&lt{@link CapacidadAgendaDto}> Lista con la capacidad
     * existente
     * @throws ApplicationException Excepci&oacute;n lanzada al realizar el
     * consumo del ws
     */
    public List<CapacidadAgendaDto> getCapacidad(CmtOrdenTrabajoMgl ot,
            String numeroOtOfsc, NodoMgl nodo) throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.getCapacidad(ot, numeroOtOfsc, nodo);
    }

    /**
     * M&eacute;todo para realizar el agendamiento de una orden de trabajo
     *
     * @param capacidad {@link CapacidadAgendaDto} registro seleccionado en
     * capacidad
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR numero de ot RR.
     * @param requestContextPath Ruta del actual contexto Faces.
     * @param tecnicoAnticipado  si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     *
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public CmtAgendamientoMgl agendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,String numeroOTRR,
            String requestContextPath, boolean tecnicoAnticipado ) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.agendar(capacidad, agendaMgl,numeroOTRR,requestContextPath,tecnicoAnticipado);
    }

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @param requestContextPath Ruta del actual contexto Faces.
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public CmtAgendamientoMgl updateAgendaMgl(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil,
            String requestContextPath)
            throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.updateAgendaMgl(agendaMgl, usuario, perfil, requestContextPath);
    }

    /**
     * Metodo para realizar el reagendamiento en una orden de trabajo Autor:
     * Victor Bocanegra
     *
     * @param capacidad nuevo capacidad de agendamiento
     * @param agendaMgl agenda la cual se va a modificar
     * @param razonReagenda razon del reagendamiento
     * @param comentarios comentarios adicionales.
     * @param usuario usuario de sesion que reagenda
     * @param perfil perfil del usuario
     * @throws ApplicationException Excepcion lanzada al reagendar
     */
    public void reagendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        agendamiento.reagendar(capacidad, agendaMgl, razonReagenda, comentarios, usuario, perfil);
    }

    /**
     * Metodo para cancelar el agendamiento de una orden de trabajo. Autor:
     * Victor Bocanegra
     *
     * @param agenda {@link CmtAgendamientoMgl} Agenda de la orden de trabajo a
     * cancelar
     * @param razonCancela razon por la cual cancelan agendamiento
     * @param comentarios comentarios adicionales.
     * @param usuario usuario de sesion que cancela
     * @param perfil perfil del usuario
     * @throws ApplicationException Excepcion lanzada al cancelar
     */
    public void cancelar(CmtAgendamientoMgl agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        agendamiento.cancelar(agenda, razonCancela, comentarios, usuario, perfil);
    }

    /**
     * Consulta de agendas por orden de Trabajo y subtipo
     *
     * @param paginaSelected
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> buscarAgendaPorOtAndSubTipoWorkfoce(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendaPorOtAndSubTipoWorkfoce(firstResult, maxResults,
                ordenTrabajoMgl, subTipoWorkfoce);
    }

    /**
     * metodo para asignar un tecnico a la agenda de OT Autor: victor bocanegra
     *
     * @param asignarRecursoRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse asignarRecursoAgendaMgl(AsignarRecursoRequest asignarRecursoRequest) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.asignarRecursoAgendaMgl(asignarRecursoRequest);

    }

    /**
     * metodo para informar del inicio de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param iniciarVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse iniciarVisitaAgendaMgl(IniciarVisitaRequest iniciarVisitaRequest) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.iniciarVisitaAgendaMgl(iniciarVisitaRequest);
    }

    /**
     * metodo no_Done para informar de la no realizacion de la actividad Autor:
     * victor bocanegra
     *
     * @param unrealizedActivityRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse actividadNoRealizadaAgendaMgl(UnrealizedActivityRequest unrealizedActivityRequest)
            throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.actividadNoRealizadaAgendaMgl(unrealizedActivityRequest);
    }

    /**
     * metodo para informar a MGL que una OT termino Autor: victor bocanegra
     *
     * @param orderCompleteRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse ordenTerminadaAgendaMgl(OrderCompleteRequest orderCompleteRequest) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.ordenTerminadaAgendaMgl(orderCompleteRequest);
    }

    /**
     * metodo para informar a MGL de las actividades realizadas y materiales
     * utilizados en la visita Autor: victor bocanegra
     *
     * @param softCloseRequest
     * @return ServicesAgendamientosResponse
     */
    public ServicesAgendamientosResponse actividadesRealizadasAgendaMgl(SoftCloseRequest softCloseRequest) {
        return null;
    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaPorIdOtWorkforce(BigDecimal otInWorkforce)
            throws ApplicationException {
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendaPorOtIdWorkforce(otInWorkforce);
    }
    
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer} Id de la agenda
     * @return CmtAgendaAuditoria
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendaAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException{
         CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
    }

    /**
     * Consulta el maximo de la secuencia por Ot Autor: victor bocanegra
     *
     * @param idOt Id de la orden de trabajo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOt(BigDecimal idOt)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.selectMaximoSecXOt(idOt);
    }

    /**
     * metodo para informar de la suspencion de una visita a MGL Autor: victor
     * bocanegra
     *
     * @param suspenderVisitaRequest
     * @return ServicesAgendamientosResponse
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public ServicesAgendamientosResponse suspenderVisitaMgl(SuspenderVisitaRequest suspenderVisitaRequest)
            throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.suspenderVisitaMgl(suspenderVisitaRequest);
    }

    /**
     * metodo para consultar los documentos adjuntos de una actividad Autor:
     * victor bocanegra
     *
     * @param agenda
     * @param usuario
     * @param perfil
     * @return List<String>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<String> consultarDocumentos(CmtAgendamientoMgl agenda, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.consultarDocumentos(agenda, usuario, perfil);
    }

    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasPorOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasPorOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);

    }

    /**
     * Consulta para contar agendas por orden de Trabajo y subtipo de trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendasByOtAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            String subTipoWorkfoce)
            throws ApplicationException {
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.getCountAgendasByOtAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en MGL Autor:
     * victor bocanegra
     *
     * @param ofpsOtId Id de la orden de Trabajo en Workforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaPorOtIdMgl(String ofpsOtId)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendaPorOtIdMgl(ofpsOtId);
    }

     /**
     * Consulta de agendas para cancelar 
     * Autor: Victor Bocanegra
     * @param ordenTrabajoMgl  orden a consultarle las agendas
     * @param idsEstados  estados de agenda a consultar
     * @param idAgenda  ultima agenda
     * @param fechaAgenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasForCancelar(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, Date fechaAgenda) throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasForCancelar(ordenTrabajoMgl, idsEstados, idAgenda,fechaAgenda);

    }

    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasPorOT(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasPorOT(ordenTrabajoMgl);

    }
    
        
    /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaAnteriorPosterior(CmtAgendamientoMgl agendaBase, int control)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendaAnteriorPosterior(agendaBase, control);
    }
    
    /**
     * Consulta de ultima agenda Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarUltimaAgenda(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException {
        
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarUltimaAgenda(ordenTrabajoMgl, idsEstados, subTipoWorkfoce);
    }
    
    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> buscarAgendasHistoricosPorOt(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendasHistoricosPorOt(firstResult, maxResults, ordenTrabajoMgl);
    }
    
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendasHistoricosPorOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.getCountAgendasHistoricosPorOt(ordenTrabajoMgl);
    }
    
    /**
     * Consulta de agendas posteriores Autor: victor bocanegra
     *
     * @param agendaBase
     * @param idsEstados
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> buscarAgendasrPosteriores(CmtAgendamientoMgl agendaBase,
            List<BigDecimal> idsEstados) throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.buscarAgendasrPosteriores(agendaBase, idsEstados);
    }
    
    /**
     * Consulta de agendas pendientes(Menos canceladas) por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasPendientesByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasPendientesByOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
    }

	/**
     * Consulta de agendas que tengan tecnico asociado Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasWithTecnico(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce) throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasWithTecnico(ordenTrabajoMgl, idsEstados, subTipoWorkfoce);
    }
    
        
    /**
     * Consulta de agendas canceladas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasCanceladasByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasCanceladasByOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);

    }
    
    /**
     * Metodo para consultar tecnicos disponibles para el dia
     * Autor: Victor Bocanegra
     *
     * @param ot orden de trabajo
     * @param nodo para la consulta
     * @param fechas para la consulta
     * @return ApiFindTecnicosResponse 
     * @throws ApplicationException Excepcion 
     */
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(CmtOrdenTrabajoMgl ot, 
            NodoMgl nodo, List<String> fechas)
            throws ApplicationException, IOException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.consultarTecnicosDisponibles(ot, nodo, fechas);
    }
    
    /**
     * Consulta de agendas para actualizar tecnico Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param idAgenda ultima agenda
     * @param fechaAgenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasForUpdateTecnico(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, Date fechaAgenda) throws ApplicationException {
        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.agendasForUpdateTecnico(ordenTrabajoMgl, idsEstados, idAgenda, fechaAgenda);

    }
  
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public void cargarInformacionForEnvioNotificacion(CmtAgendamientoMgl agendaMgl,
            int tipoNotificacion) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        agendamiento.cargarInformacionForEnvioNotificacion(agendaMgl, tipoNotificacion);
    }
            
       /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendamiento(CmtOrdenTrabajoMgl ordenTrabajoMgl, CmtAgendamientoMgl agenda)
            throws ApplicationException {

        CmtAgendamientoMglDaoImpl dao = new CmtAgendamientoMglDaoImpl();
        return dao.getCountAgendamiento(ordenTrabajoMgl, agenda);
    }

    /**
     * metodo para realizar un agendamiento inmediato con la informacion del
     * Iframe Autor: victor bocanegra
     *
     * @param requestAgendaInmediata
     * @return ResponseAgendaInmediata
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ResponseAgendaInmediata WSManageSchedulesAppointmentsMgl(RequestAgendaInmediata requestAgendaInmediata)
            throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.WSManageSchedulesAppointmentsMgl(requestAgendaInmediata);
    }
    
        /**
     * M&eacute;todo para buscar una agenda por Id
     *
     * @param idAgenda {@link BigDecimal} id de la agenda
     * @return {@link CmtAgendamientoMgl} agendamiento encontrado
     * @throws ApplicationException Excepci&oacute;n lanzada al buscar el
     * registro
     */
    public CmtAgendamientoMgl findById(BigDecimal idAgenda) throws ApplicationException {
        CmtAgendamientoMglDaoImpl agendamientoDao = new CmtAgendamientoMglDaoImpl();
        return agendamientoDao.find(idAgenda);
    }

    /**
     * Consulta de todas las agendas por id Enlace desde APP externa Autor:
     * victor bocanegra
     *
     * @param idEnlace
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasByIdEnlace(String idEnlace)
            throws ApplicationException {
        CmtAgendamientoMglDaoImpl agendamientoDao = new CmtAgendamientoMglDaoImpl();
        return agendamientoDao.agendasByIdEnlace(idEnlace);
    }
    
    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC por notas
     * Autor: Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public CmtAgendamientoMgl updateAgendasForNotas(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.updateAgendasForNotas(agendaMgl, usuario, perfil);
    }
    
    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC por contacto
     * Autor: Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    public CmtAgendamientoMgl updateAgendasForContacto(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.updateAgendasForContacto(agendaMgl, usuario, perfil);
    }
    
    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de CM debe empezar con 1 para identificar que es una OT de CM
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    public String armarNumeroOtOfsc(CmtOrdenTrabajoMgl orden) throws ApplicationException {

        AgendamientoWorkForceMglManager agendamiento = new AgendamientoWorkForceMglManager();
        return agendamiento.armarNumeroOtOfsc(orden);
    }
}
