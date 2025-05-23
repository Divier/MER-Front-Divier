/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.agendamiento.AgendamientoHhppWorkForceMglManager;
import co.com.claro.mgl.dao.impl.AgendamientoHhppMglDaoImpl;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccionAuditoria;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.ofscCore.findMatchingResources.ApiFindTecnicosResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Orlando Velasquez
 */
public class AgendamientoHhppMglManager {

    /**
     * Consulta de agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param paginaSelected
     * @param maxResults
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendaPorOt(int paginaSelected,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl,String subTipoWorkfoce)
            throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendaPorOt(firstResult, maxResults, ordenTrabajoHhppMgl,subTipoWorkfoce);
    }

    /**
     * Consulta de todas agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasPorOt(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasPorOt(ordenTrabajoHhppMgl);
    }
    
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer}
     * @return {@link List}&lt;{@link MglAgendaDireccionAuditoria}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccionAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
            throws ApplicationException{
         AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
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
    public List<CapacidadAgendaDto> getCapacidadOtDireccion(
            OtHhppMgl otDireccion, UsuariosServicesDTO usuario,
            String numeroOtOfsc, NodoMgl nodoMgl) throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.getCapacidadOtDireccion(otDireccion, usuario, numeroOtOfsc, nodoMgl);
    }

    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param ot
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int cantidadAgendasPorOtHhpp(OtHhppMgl ot, String subTipoWorkfoce)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.cantidadAgendasPorOtHhpp(ot,subTipoWorkfoce);

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

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.selectMaximoSecXOt(idOt);
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
    public int buscarAgendasActivas(OtHhppMgl ordenTrabajoMgl)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasActivas(ordenTrabajoMgl);
    }

    /**
     * Metodo para crear la agenda en la base de datos
     *
     * @param agenda objeto a ser persistido
     * @param user
     * @param perfil
     * @Author Orlando Velasquez
     * @return agenda creada
     * @throws ApplicationException Excepci&oacute;n lanzada en la insercion
     */
    public MglAgendaDireccion create(MglAgendaDireccion agenda, String user, Integer perfil)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.createCm(agenda, user, perfil);

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
     * @param tecnicoAnticipado  si el agendamiento es de tecnico anticipado.
     * @return CmtAgendamientoMgl agenda para almacenar en MGL
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public MglAgendaDireccion agendar(CapacidadAgendaDto capacidad,
            MglAgendaDireccion agendaMgl, String numeroOTRR,
            CmtCuentaMatrizMgl cuentaMatrizAgrupadora,
            boolean tecnicoAnticipado) throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.agendar(capacidad, agendaMgl,numeroOTRR, cuentaMatrizAgrupadora, tecnicoAnticipado);

    }

    /**
     * Metodo para validar la disponibilidad de las tecnologias seleccionadas en
     * WorkForce
     *
     * @param otDireccion
     * @Author Orlando Velasquez
     * @throws co.com.claro.mgl.error.ApplicationException
     * @return boolean disponibilidad de las tecnologias
     */
    public boolean validarTecnologiasWorkForce(OtHhppMgl otDireccion) throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.validarTecnologiasWorkForce(otDireccion);

    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaPorIdOtWorkforce(long otInWorkforce)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendaPorOtIdWorkforce(otInWorkforce);
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
            MglAgendaDireccion agendaMgl,
            String razonReagenda, String comentarios, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        agendamiento.reagendar(capacidad, agendaMgl, razonReagenda, comentarios, usuario, perfil);
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
    public MglAgendaDireccion updateAgendaMgl(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.updateAgendaMgl(agendaMgl, usuario, perfil);
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
    public void cancelar(MglAgendaDireccion agenda,
            String razonCancela, String comentarios,
            String usuario, int perfil) throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        agendamiento.cancelar(agenda, razonCancela, comentarios, usuario, perfil);
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
    public List<String> consultarDocumentos(MglAgendaDireccion agenda, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.consultarDocumentos(agenda, usuario, perfil);
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
    public MglAgendaDireccion update(MglAgendaDireccion agenda,
            String user, Integer perfil) throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.updateCm(agenda, user, perfil);
    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en MGL Autor:
     * victor bocanegra
     *
     * @param ofpsOtId Id de la orden de Trabajo en Workforce
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaPorOtIdMgl(String ofpsOtId)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendaPorOtIdMgl(ofpsOtId);
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
    public List<MglAgendaDireccion> buscarAgendasByOtAndSubtipopOfsc
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasByOtAndSubtipopOfsc(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
        
    /**
     * Consulta de agenda anterior o posterior Autor: victor bocanegra
     *
     * @param agendaBase
     * @param control
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaAnteriorPosteriorHhpp(MglAgendaDireccion agendaBase, int control)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendaAnteriorPosteriorHhpp(agendaBase, control);
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
    public MglAgendaDireccion buscarUltimaAgendaHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarUltimaAgendaHhpp(ordenTrabajoHhppMgl, idsEstados, subTipoWorkfoce);
    }
    
    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param paginaSelected
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasHistoricosPorOtHhpp(int paginaSelected,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {
        
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasHistoricosPorOtHhpp(firstResult, maxResults, ordenTrabajoHhppMgl);
    }
    
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendasHistoricosPorOtHhpp(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {
        
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.getCountAgendasHistoricosPorOtHhpp(ordenTrabajoHhppMgl);
    }
    
    /**
     * Consulta de agendas para cancelar Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param idAgenda ultima agenda
     * @param subTipoWorkfoce
     * @param fechaAgenda
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> agendasForCancelarHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, 
            String subTipoWorkfoce, Date fechaAgenda) throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.agendasForCancelarHhpp(ordenTrabajoHhppMgl, idsEstados,
                idAgenda, subTipoWorkfoce, fechaAgenda);
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
    public List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfsc(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasPendientesByOtAndSubtipopOfsc(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
	
	/**
     * Consulta las agendas iniciadas(menos las canceladas) asociadas a una
     * orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasIniciadasByOtAndSubtipopOfsc
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce) throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasIniciadasByOtAndSubtipopOfsc(ordenTrabajoHhppMgl, subTipoWorkfoce);
    }
        
        /**
     * Consulta de agendas que tengan tecnico asociado Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> agendasWithTecnicoHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce) throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.agendasWithTecnicoHhpp(ordenTrabajoHhppMgl, idsEstados, subTipoWorkfoce);
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
    public List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(ordenTrabajoHhppMgl, subTipoWorkfoce);
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
    public List<MglAgendaDireccion> agendasHhppCanceladasByOrdenAndSubTipoWorkfoce(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.agendasHhppCanceladasByOrdenAndSubTipoWorkfoce(ordenTrabajoHhppMgl, subTipoWorkfoce);
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
    public ApiFindTecnicosResponse consultarTecnicosDisponibles(OtHhppMgl ot, NodoMgl nodo,
            List<String> fechas)
            throws ApplicationException, MalformedURLException, IOException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.consultarTecnicosDisponibles(ot, nodo, fechas);

    }
  
    /**
     * Consulta de agendas para actualizar tecnico Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param idAgenda ultima agenda
     * @param subTipoWorkfoce
     * @param fechaAgenda
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> agendasForUpdateTecnico(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda,
            String subTipoWorkfoce, Date fechaAgenda) throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.agendasForUpdateTecnico(ordenTrabajoHhppMgl, idsEstados, idAgenda, subTipoWorkfoce, fechaAgenda);
    }
    
    /**
     * M&eacute;todo para enviar una notificacion por agendamiento
     *
     * @param agendaMgl {@link MglAgendaDireccion} agenda que sva anotificar
     * @param tipoNotificacion tipo de notificacion
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public void cargarInformacionForEnvioNotificacion(MglAgendaDireccion agendaMgl,
            int tipoNotificacion) throws ApplicationException {

        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        agendamiento.cargarInformacionForEnvioNotificacion(agendaMgl, tipoNotificacion);
    }
 
    /**
     * Consulta para contar agendas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @param agendaMgl
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendamiento(MglAgendaDireccion agendaMgl)
            throws ApplicationException {

        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.getCountAgendamiento(agendaMgl);
    }

    /**
     * Consulta de agenda por identificador de la agenda Autor: victor bocanegra
     *
     * @param idAgenda
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaByIdAgenda(long idAgenda)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.buscarAgendaByIdAgenda(idAgenda);
    }

    /**
     * Consulta de todas las agendas por id Enlace desde APP externa Autor:
     * victor bocanegra
     *
     * @param idEnlace
     * @return List<MglAgendaDireccion>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> agendasByIdEnlace(String idEnlace)
            throws ApplicationException {
        AgendamientoHhppMglDaoImpl dao = new AgendamientoHhppMglDaoImpl();
        return dao.agendasByIdEnlace(idEnlace);
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
    public MglAgendaDireccion updateAgendasForNotas(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {

        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
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
    public MglAgendaDireccion updateAgendasForContacto(
            MglAgendaDireccion agendaMgl, String usuario, int perfil)
            throws ApplicationException {
        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.updateAgendasForContacto(agendaMgl, usuario, perfil);
    }
    
    /**
     * Metodo para construir el Appt_number para enviar a WorkForce en el caso
     * de los Hhpp debe empezar con 2 para identificar que es una OT de Hhpp
     *
     * @param orden orden de trabajo de un Hhpp
     * @return boolean disponibilidad de las tecnologias
     */
    public String armarNumeroOtOfscHhpp(OtHhppMgl orden) throws ApplicationException {

        AgendamientoHhppWorkForceMglManager agendamiento = new AgendamientoHhppWorkForceMglManager();
        return agendamiento.armarNumeroOtOfscHhpp(orden);

    }
}
