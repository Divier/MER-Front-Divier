package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtAgendamientoMglManager;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendaAuditoria;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wgavidia
 */
@Stateless
public class CmtAgendamientoMglFacade implements CmtAgendamientoMglFacadeLocal {

    private String usuario;
    private Integer perfil;

    /**
     * {@inheritDoc }
     *
     * @param usuario {@link String} usuario en sesi&oacute;n
     * @param perfil {@link Integer} perfil del usuario que realia la
     * transacci&oacute;n
     */
    @Override
    public void setUser(String usuario, Integer perfil) {
        this.usuario = usuario;
        this.perfil = perfil;
    }

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @param requestContextPath Ruta del actual contexto Faces
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    @Override
    public CmtAgendamientoMgl update(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil, 
            String requestContextPath)
            throws ApplicationException {

        CmtAgendamientoMglManager agendamientoManager = new CmtAgendamientoMglManager();
        return agendamientoManager.updateAgendaMgl(agendaMgl, usuario, perfil, requestContextPath);
    }

    /**
     * CRUD de agendamiento - Create
     *
     * @param agenda {@link CmtAgendamientoMgl} Objeto de agendamiento
     * @return {@link CmtAgendamientoMgl} Objeto de agendamiento creado
     * @throws ApplicationException Excepci&oacute;n lanzada en la
     * creaci&oacute;n
     */
    @Override
    public CmtAgendamientoMgl create(CmtAgendamientoMgl agenda) throws ApplicationException {

        CmtAgendamientoMglManager agendamientoManager = new CmtAgendamientoMglManager();
        return agendamientoManager.create(agenda, usuario, perfil);
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
    @Override
    public List<CapacidadAgendaDto> getCapacidad(CmtOrdenTrabajoMgl ot,
            String numeroOtOfsc, NodoMgl nodo) throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.getCapacidad(ot, numeroOtOfsc, nodo);
    }

    /**
     * M&eacute;todo para realizar el agendamiento de una orden de trabajo
     *
     * @param agenda
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR RNumero de ot RR.
     * @param requestContextPath Ruta del actual contexto Faces.
     * @param tecnicoAnticipado  si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     *
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    @Override
    public CmtAgendamientoMgl agendar(CapacidadAgendaDto agenda,
            CmtAgendamientoMgl agendaMgl,String numeroOTRR, 
            String requestContextPath, boolean tecnicoAnticipado) throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.agendar(agenda, agendaMgl,numeroOTRR,requestContextPath,tecnicoAnticipado);
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
    @Override
    public void reagendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        manager.reagendar(capacidad, agendaMgl, razonReagenda, comentarios, usuario, perfil);
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
    @Override
    public void cancelar(CmtAgendamientoMgl agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        manager.cancelar(agenda, razonCancela, comentarios, usuario, perfil);
    }

    /**
     * Consulta de agendas por orden de Trabajo y subtipo
     *
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<CmtAgendamientoMgl> buscarAgendaPorOtAndSubTipoWorkfoce(int firstResult,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendaPorOtAndSubTipoWorkfoce(firstResult, maxResults, ordenTrabajoMgl, subTipoWorkfoce);
    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce Id de la orden de Trabajo en Workforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public CmtAgendamientoMgl buscarAgendaPorIdOtWorkforce(BigDecimal otInWorkforce)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendaPorIdOtWorkforce(otInWorkforce);
    }
    
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer} Id de agenda
     * @return CmtAgendaAuditoria
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<CmtAgendaAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException{
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
    }
    /**
     * metodo para consultar los documentos adjuntos de una actividad Autor:
     * victor bocanegra
     *
     * @param agenda
     * @param usuario
     * @param perfil
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<String> consultarDocumentos(CmtAgendamientoMgl agenda, String usuario, int perfil)
            throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.consultarDocumentos(agenda, usuario, perfil);
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
    @Override
    public int getCountAgendasByOtAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            String subTipoWorkfoce)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.getCountAgendasByOtAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
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
    @Override
    public List<CmtAgendamientoMgl> agendasPorOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.agendasPorOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
    }
    
            
    /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public CmtAgendamientoMgl buscarAgendaAnteriorPosterior(CmtAgendamientoMgl agendaBase, int control)
            throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendaAnteriorPosterior(agendaBase, control);

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
    @Override
    public CmtAgendamientoMgl buscarUltimaAgenda(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarUltimaAgenda(ordenTrabajoMgl, idsEstados, subTipoWorkfoce);

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
    @Override
    public List<CmtAgendamientoMgl> buscarAgendasHistoricosPorOt(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {
        
         CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendasHistoricosPorOt(paginaSelected, maxResults, ordenTrabajoMgl);
        
    }
    
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public int getCountAgendasHistoricosPorOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.getCountAgendasHistoricosPorOt(ordenTrabajoMgl);

    }
    
    
    /**
     * Consulta de agendas posteriores Autor: victor bocanegra
     *
     * @param agendaBase
     * @param idsEstados
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<CmtAgendamientoMgl> buscarAgendasrPosteriores(CmtAgendamientoMgl agendaBase,
            List<BigDecimal> idsEstados) throws ApplicationException {
        
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.buscarAgendasrPosteriores(agendaBase, idsEstados);
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
    @Override
    public List<CmtAgendamientoMgl> agendasPendientesByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {
        
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.agendasPendientesByOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
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
    @Override
    public List<CmtAgendamientoMgl> agendasCanceladasByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.agendasCanceladasByOrdenAndSubTipoWorkfoce(ordenTrabajoMgl, subTipoWorkfoce);
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
    @Override
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(CmtOrdenTrabajoMgl ot, 
            NodoMgl nodo, List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.consultarTecnicosDisponibles(ot, nodo, fechas);
    }
  
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al notificar
     */
    @Override
    public void cargarInformacionForEnvioNotificacion(CmtAgendamientoMgl agendaMgl,
            int tipoNotificacion) throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        manager.cargarInformacionForEnvioNotificacion(agendaMgl, tipoNotificacion);
    }
    

    @Override
    public int getCountAgendamiento(CmtOrdenTrabajoMgl ordenTrabajoMgl, CmtAgendamientoMgl agenda) throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.getCountAgendamiento(ordenTrabajoMgl, agenda);
    }

    /**
     * M&eacute;todo para buscar una agenda por Id
     *
     * @param idAgenda {@link BigDecimal} id de la agenda
     * @return {@link CmtAgendamientoMgl} agendamiento encontrado
     * @throws ApplicationException Excepci&oacute;n lanzada al buscar el
     * registro
     */
    @Override
    public CmtAgendamientoMgl findById(BigDecimal idAgenda) throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.findById(idAgenda);
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
    @Override
    public CmtAgendamientoMgl updateAgendasForNotas(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.updateAgendasForNotas(agendaMgl, usuario, perfil);
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
    @Override
    public CmtAgendamientoMgl updateAgendasForContacto(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.updateAgendasForContacto(agendaMgl, usuario, perfil);
    }
    
    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de CM debe empezar con 1 para identificar que es una OT de CM
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    @Override
    public String armarNumeroOtOfsc(CmtOrdenTrabajoMgl orden) throws ApplicationException {

        CmtAgendamientoMglManager manager = new CmtAgendamientoMglManager();
        return manager.armarNumeroOtOfsc(orden);
    }
}
