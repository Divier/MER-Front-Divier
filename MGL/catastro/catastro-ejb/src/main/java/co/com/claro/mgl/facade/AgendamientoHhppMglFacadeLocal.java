/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccionAuditoria;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

/**
 *
 * @author Orlando Velasquez
 */
public interface AgendamientoHhppMglFacadeLocal {

    /**
     * {@inheritDoc }
     *
     * @param usuario {@link String} usuario en sesi&oacute;n
     * @param perfil {@link Integer} perfil del usuario que realia la
     * transacci&oacute;n
     */
    void setUser(String usuario, Integer perfil);

    /**
     * Consulta de agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param firstResult primer registro a mostrar
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @param maxResults ultimo registro a mostrar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<MglAgendaDireccion> buscarAgendaPorOt(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException;

    /**
     * Consulta de todas agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<MglAgendaDireccion> buscarAgendasPorOt(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException;

    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer}
     * @return {@link List<MglAgendaDireccionAuditoria>} Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<MglAgendaDireccionAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException;
    
    
   /**
     * Metodo para consultar la disponibilidad en el agendamiento
     *
     * @param otDireccion {@link OtHhppMgl} ot para la cual se busca la
     * capacidad
     * @param usuario {@link UsuariosServicesDTO} usuario de sesion
     * @param numeroOtOfsc {@link String} numero de la ot en OFSC
     * @param nodoMgl {@link NodoMgl} nodo para la peticion
     * @return {@link List}&lt{@link CapacidadAgendaDto}> Lista con la capacidad
     * existente
     * @throws ApplicationException Excepci&oacute;n lanzada al realizar el
     * consumo del ws
     */
   List<CapacidadAgendaDto> getCapacidadOtDireccion(
            OtHhppMgl otDireccion, UsuariosServicesDTO usuario,
            String numeroOtOfsc, NodoMgl nodoMgl) throws ApplicationException;

    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link OtHhppMgl} orden de Trabajo a la que fue
     * asociada la agenda
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    int cantidadAgendasPorOtHhpp(OtHhppMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException;

    
    /**
     * Consulta Agendas en estado Agendado o reagendado por orden de trabajo
     *
     * @Author Orlando Velasquez
     * @param ordenTrabajoMgl {@link OtHhppMgl} orden de Trabajo a la que fue
     * asociada la agenda
     * @return cantidad de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    int buscarAgendasActivas(OtHhppMgl ordenTrabajoMgl)
            throws ApplicationException;

    /**
     * Metodo para crear la agenda en la base de datos
     *
     * @param agenda objeto a ser persistido
     * @Author Orlando Velasquez
     * @return agenda creada
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    MglAgendaDireccion create(MglAgendaDireccion agenda)
            throws ApplicationException;

    /**
     * Metodo para realizar el agendamiento de una orden de trabajo
     *
     * @param capacidad {@link CapacidadAgendaDto} registro seleccionado en
     * capacidad
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va almacenar en
     * OFSC la agenda
     * @param numeroOTRR
     * @param cuentaAgrupadora
     * @param tecnicoAnticipado si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    MglAgendaDireccion agendar(CapacidadAgendaDto capacidad,
            MglAgendaDireccion agendaMgl, String numeroOTRR,
            CmtCuentaMatrizMgl cuentaMatrizAgrupadora,
            boolean tecnicoAnticipado) throws ApplicationException;

    /**
     * Metodo para validar la disponibilidad de las tecnologias seleccionadas en
     * WorkForce
     *
     * @param otDireccion
     * @Author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     * @return boolean disponibilidad de las tecnologias
     */
    boolean validarTecnologiasWorkForce(OtHhppMgl otDireccion) throws ApplicationException;

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce Id de la orden de Trabajo en Workforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    MglAgendaDireccion buscarAgendaPorIdOtWorkforce(long otInWorkforce)
            throws ApplicationException;

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
            MglAgendaDireccion agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException;

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link MglAgendaDireccion} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return MglAgendaDireccion agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    MglAgendaDireccion update(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
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
    void cancelar(MglAgendaDireccion agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException;

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
    List<String> consultarDocumentos(MglAgendaDireccion agenda, String usuario, int perfil)
            throws ApplicationException;
    
        /**
     * Consulta las agendas asociadas a una orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
      List<MglAgendaDireccion> buscarAgendasByOtAndSubtipopOfsc
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException;
        
            /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    MglAgendaDireccion buscarAgendaAnteriorPosteriorHhpp(MglAgendaDireccion agendaBase, int control)
            throws ApplicationException;
    
        /**
     * Consulta de ultima agenda Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
   MglAgendaDireccion buscarUltimaAgendaHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException;
   
       /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     List<MglAgendaDireccion> buscarAgendasHistoricosPorOtHhpp(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException;
     
         /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     int getCountAgendasHistoricosPorOtHhpp(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException;
     
         /**
     * Consulta las agendas pendientes(menos las canceladas) asociadas a una
     * orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfsc(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException;

     
              /**
     * Consulta las agendas pendientes(menos las canceladas-nodone y cerradas)
     * asociadas a una orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
     List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException;
        
            /**
     * Consulta de agendas canceladas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<MglAgendaDireccion> agendasHhppCanceladasByOrdenAndSubTipoWorkfoce
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException;
    
    /**
     * Metodo para consultar tecnicos disponibles para el dia Autor: Victor
     * Bocanegra
     *
     * @param ot orden de trabajo
     * @param nodo para la consulta
     * @param fechas para la consulta
     * @return ApiFindTecnicosResponse
     * @throws ApplicationException Excepcion
     */
    ApiFindTecnicosResponse consultarTecnicosDisponibles(OtHhppMgl ot, NodoMgl nodo,
            List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException;
    
            
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link MglAgendaDireccion} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    void cargarInformacionForEnvioNotificacion(MglAgendaDireccion agendaMgl,
            int tipoNotificacion) throws ApplicationException;
    
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @param agendaMgl
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    int getCountAgendamiento(MglAgendaDireccion agendaMgl)
            throws ApplicationException;

    /**
     * Consulta de agenda por identificador de la agenda Autor: victor bocanegra
     *
     * @param idAgenda
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    MglAgendaDireccion buscarAgendaByIdAgenda(long idAgenda)
            throws ApplicationException;
    
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
     MglAgendaDireccion updateAgendasForNotas(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
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
     MglAgendaDireccion updateAgendasForContacto(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException;
   
    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de los Hhpp debe empezar con 2 para identificar que es una OT de Hhpp
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     * @throws co.com.claro.mgl.error.ApplicationException Excepción de la App
     */
    String armarNumeroOtOfscHhpp(OtHhppMgl orden) throws ApplicationException;

}
