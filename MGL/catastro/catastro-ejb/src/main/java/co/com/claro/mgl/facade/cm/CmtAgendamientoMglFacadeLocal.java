package co.com.claro.mgl.facade.cm;

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

/**
 *
 * @author wgavidia
 * @version 21/11/2017
 */
public interface CmtAgendamientoMglFacadeLocal {

    /**
     * M&eacute;todo para setear usuario y perfil que esta realizando las
     * acciones
     *
     * @param usuario {@link String} usuario en sesi&oacute;n
     * @param perfil {@link Integer} perfil del usuario que realia la
     * transacci&oacute;n
     */
    void setUser(String usuario, Integer perfil);

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
    CmtAgendamientoMgl update(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil,
            String requestContextPath)
            throws ApplicationException;

    /**
     * CRUD de agendamiento - Create
     *
     * @param agenda {@link CmtAgendamientoMgl} Objeto de agendamiento
     * @return {@link CmtAgendamientoMgl} Objeto de agendamiento creado
     * @throws ApplicationException Excepci&oacute;n lanzada en la
     * creaci&oacute;n
     */
    CmtAgendamientoMgl create(CmtAgendamientoMgl agenda) throws ApplicationException;

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
    List<CapacidadAgendaDto> getCapacidad(CmtOrdenTrabajoMgl ot,
            String numeroOtOfsc, NodoMgl nodo) throws ApplicationException;

    /**
     * M&eacute;todo para realizar el agendamiento de una orden de trabajo
     *
     * @param capacidad {@link CapacidadAgendaDto} registro seleccionado en
     * capacidad
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR Numero de ot RR
     * @param requestContextPath Ruta del actual contexto Faces
     * @param tecnicoAnticipado si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     *
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    CmtAgendamientoMgl agendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,String numeroOTRR,
            String requestContextPath, boolean tecnicoAnticipado) throws ApplicationException;

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
    void reagendar(CapacidadAgendaDto capacidad,
            CmtAgendamientoMgl agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException;

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
    void cancelar(CmtAgendamientoMgl agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException;

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
    List<CmtAgendamientoMgl> buscarAgendaPorOtAndSubTipoWorkfoce(int firstResult,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException;

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce Id de la orden de Trabajo en Workforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    CmtAgendamientoMgl buscarAgendaPorIdOtWorkforce(BigDecimal otInWorkforce)
            throws ApplicationException;

    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer}
     * @return CmtAgendaAuditoria
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<CmtAgendaAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException;
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
    List<String> consultarDocumentos(CmtAgendamientoMgl agenda, String usuario, int perfil)
            throws ApplicationException;

    /**
     * Consulta para contar agendas por orden de Trabajo y subtipo de trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    int getCountAgendasByOtAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            String subTipoWorkfoce)
            throws ApplicationException;

    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<CmtAgendamientoMgl> agendasPorOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException;
    
        /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
  
    CmtAgendamientoMgl buscarAgendaAnteriorPosterior(CmtAgendamientoMgl agendaBase, int control)
            throws ApplicationException;
    
        /**
     * Consulta de ultima agenda Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     CmtAgendamientoMgl buscarUltimaAgenda(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException;
     
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
     List<CmtAgendamientoMgl> buscarAgendasHistoricosPorOt(int paginaSelected,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException;
     
         /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
      int getCountAgendasHistoricosPorOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException;
      
    
    /**
     * Consulta de agendas posteriores Autor: victor bocanegra
     *
     * @param agendaBase
     * @param idsEstados
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<CmtAgendamientoMgl> buscarAgendasrPosteriores(CmtAgendamientoMgl agendaBase,
            List<BigDecimal> idsEstados) throws ApplicationException;
    
            /**
     * Consulta de agendas pendientes(Menos canceladas) por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     List<CmtAgendamientoMgl> agendasPendientesByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException; 
     
         /**
     * Consulta de agendas canceladas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     List<CmtAgendamientoMgl> agendasCanceladasByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException;
     
     
     
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
    ApiFindTecnicosResponse consultarTecnicosDisponibles(CmtOrdenTrabajoMgl ot, 
            NodoMgl nodo, List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException;
    
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al notificar
     */
    void cargarInformacionForEnvioNotificacion(CmtAgendamientoMgl agendaMgl,
            int tipoNotificacion) throws ApplicationException;

    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    int getCountAgendamiento(CmtOrdenTrabajoMgl ordenTrabajoMgl, CmtAgendamientoMgl agenda)
            throws ApplicationException;

    /**
     * M&eacute;todo para buscar una agenda por Id
     *
     * @param idAgenda {@link BigDecimal} id de la agenda
     * @return {@link CmtAgendamientoMgl} agendamiento encontrado
     * @throws ApplicationException Excepci&oacute;n lanzada al buscar el
     * registro
     */
    CmtAgendamientoMgl findById(BigDecimal idAgenda) throws ApplicationException;
    
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
    CmtAgendamientoMgl updateAgendasForNotas(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException;
    
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
     CmtAgendamientoMgl updateAgendasForContacto(
            CmtAgendamientoMgl agendaMgl, String usuario, int perfil)
            throws ApplicationException;
     
    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de CM debe empezar con 1 para identificar que es una OT de CM
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    String armarNumeroOtOfsc(CmtOrdenTrabajoMgl orden) throws ApplicationException;

}
