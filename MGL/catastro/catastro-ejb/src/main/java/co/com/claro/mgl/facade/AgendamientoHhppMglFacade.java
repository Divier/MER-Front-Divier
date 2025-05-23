/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.AgendamientoHhppMglManager;
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
import javax.ejb.Stateless;

/**
 *
 * @author Orlando Velasquez
 */
@Stateless
public class AgendamientoHhppMglFacade implements AgendamientoHhppMglFacadeLocal {

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
     * Consulta de agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param firstResult
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @param maxResults
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<MglAgendaDireccion> buscarAgendaPorOt(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl,String subTipoWorkfoce)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendaPorOt(firstResult, maxResults, ordenTrabajoHhppMgl,subTipoWorkfoce);

    }

    /**
     * Consulta de todas agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<MglAgendaDireccion> buscarAgendasPorOt(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasPorOt(ordenTrabajoHhppMgl);

    }
    
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer}
     * @return {@link List}&lt;{@link MglAgendaDireccionAuditoria}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<MglAgendaDireccionAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException{
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
    }

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
    @Override
    public List<CapacidadAgendaDto> getCapacidadOtDireccion(
            OtHhppMgl otDireccion, UsuariosServicesDTO usuario,
            String numeroOtOfsc, NodoMgl nodoMgl) throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.getCapacidadOtDireccion(otDireccion, usuario, numeroOtOfsc,nodoMgl);
    }

    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link OtHhppMgl} orden de Trabajo a la que fue
     * asociada la agenda
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public int cantidadAgendasPorOtHhpp(OtHhppMgl ordenTrabajoMgl,  String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.cantidadAgendasPorOtHhpp(ordenTrabajoMgl, subTipoWorkfoce);
    }

    
    /**
     * Consulta Agendas en estado Agendado o reagendado por orden de trabajo
     *
     * @Author Orlando Velasquez
     * @param ordenTrabajoMgl {@link OtHhppMgl} orden de Trabajo a la que fue
     * asociada la agenda
     * @return cantidad de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public int buscarAgendasActivas(OtHhppMgl ordenTrabajoMgl)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasActivas(ordenTrabajoMgl);
    }

    /**
     * Metodo para crear la agenda en la base de datos
     *
     * @param agenda objeto a ser persistido
     * @Author Orlando Velasquez
     * @return agenda creada
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public MglAgendaDireccion create(MglAgendaDireccion agenda)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.create(agenda, usuario, perfil);

    }

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
    @Override
    public MglAgendaDireccion agendar(CapacidadAgendaDto capacidad,
            MglAgendaDireccion agendaMgl, String numeroOTRR,
            CmtCuentaMatrizMgl cuentaMatrizAgrupadora,
            boolean tecnicoAnticipado) throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.agendar(capacidad, agendaMgl, numeroOTRR, cuentaMatrizAgrupadora, tecnicoAnticipado);

    }

    /**
     * Metodo para validar la disponibilidad de las tecnologias seleccionadas en
     * WorkForce
     *
     * @Author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     * @return boolean disponibilidad de las tecnologias
     */
    @Override
    public boolean validarTecnologiasWorkForce(OtHhppMgl otDireccion) throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.validarTecnologiasWorkForce(otDireccion);

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
    public MglAgendaDireccion buscarAgendaPorIdOtWorkforce(long otInWorkforce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendaPorIdOtWorkforce(otInWorkforce);
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
            MglAgendaDireccion agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        manager.reagendar(capacidad, agendaMgl, razonReagenda, comentarios, usuario, perfil);
    }

    /**
     * Metodo para realizar la actualizacion de una agenda en OFSC y MGL Autor:
     * Victor Bocanegra
     *
     * @param agendaMgl {@link CmtAgendamientoMgl} agenda que se va actualizar
     * en OFSC y MGL
     * @param usuario que ejecuta la actualizacion
     * @param perfil del usuario
     * @return CmtAgendamientoMgl agenda actualizada
     * @throws ApplicationException Excepcion lanzada al actulizar
     */
    @Override
    public MglAgendaDireccion update(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.updateAgendaMgl(agendaMgl, usuario, perfil);
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
    public void cancelar(MglAgendaDireccion agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        manager.cancelar(agenda, razonCancela, comentarios, usuario, perfil);
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
    public List<String> consultarDocumentos(MglAgendaDireccion agenda, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.consultarDocumentos(agenda, usuario, perfil);
    }
    
    /**
     * Consulta las agendas asociadas a una orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<MglAgendaDireccion> buscarAgendasByOtAndSubtipopOfsc
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasByOtAndSubtipopOfsc(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
        
    /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override    
    public MglAgendaDireccion buscarAgendaAnteriorPosteriorHhpp(MglAgendaDireccion agendaBase, int control)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendaAnteriorPosteriorHhpp(agendaBase, control);
    }
    
    /**
     * Consulta de ultima agenda Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public MglAgendaDireccion buscarUltimaAgendaHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarUltimaAgendaHhpp(ordenTrabajoHhppMgl, idsEstados, subTipoWorkfoce);
    }
    
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
    @Override
    public List<MglAgendaDireccion> buscarAgendasHistoricosPorOtHhpp(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasHistoricosPorOtHhpp(firstResult, maxResults, ordenTrabajoHhppMgl);
    }
    
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public int getCountAgendasHistoricosPorOtHhpp(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.getCountAgendasHistoricosPorOtHhpp(ordenTrabajoHhppMgl);
    }
    
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
    @Override
    public List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfsc(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasPendientesByOtAndSubtipopOfsc(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
    
    
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
    @Override
    public List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
    
    /**
     * Consulta de agendas canceladas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<MglAgendaDireccion> agendasHhppCanceladasByOrdenAndSubTipoWorkfoce
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.agendasHhppCanceladasByOrdenAndSubTipoWorkfoce(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
    
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
    @Override
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(OtHhppMgl ot, NodoMgl nodo,
            List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.consultarTecnicosDisponibles(ot, nodo, fechas);
    }
    
        
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link MglAgendaDireccion} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    @Override
    public void cargarInformacionForEnvioNotificacion(MglAgendaDireccion agendaMgl,
            int tipoNotificacion) throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        manager.cargarInformacionForEnvioNotificacion(agendaMgl, tipoNotificacion);
    }
    

      @Override
    public int getCountAgendamiento(MglAgendaDireccion agendaMgl) throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.getCountAgendamiento(agendaMgl);
    }

    /**
     * Consulta de agenda por identificador de la agenda Autor: victor bocanegra
     *
     * @param idAgenda
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public MglAgendaDireccion buscarAgendaByIdAgenda(long idAgenda)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.buscarAgendaByIdAgenda(idAgenda);
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
    public MglAgendaDireccion updateAgendasForNotas(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
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
    public MglAgendaDireccion updateAgendasForContacto(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.updateAgendasForContacto(agendaMgl, usuario, perfil);
    }
    
        /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de los Hhpp debe empezar con 2 para identificar que es una OT de Hhpp
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    @Override
    public String armarNumeroOtOfscHhpp(OtHhppMgl orden) throws ApplicationException {

        AgendamientoHhppMglManager manager = new AgendamientoHhppMglManager();
        return manager.armarNumeroOtOfscHhpp(orden);
    }

}
