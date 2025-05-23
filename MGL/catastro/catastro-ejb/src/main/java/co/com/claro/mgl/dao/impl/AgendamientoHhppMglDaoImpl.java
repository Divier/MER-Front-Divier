/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccionAuditoria;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.EntityManagerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Orlando Velasquez
 */
public class AgendamientoHhppMglDaoImpl extends GenericDaoImpl<MglAgendaDireccion> {

    private static final Logger LOGGER = LogManager.getLogger(AgendamientoHhppMglDaoImpl.class);
    private static final String CONSULTA_AGENDA_AUD_SP = Constant.MGL_DATABASE_SCHEMA+".AGENDA_DIRECCION_AUD_PKG.PRTBMGLAGENDADIRAUD";

    /**
     * Consulta las agendas asociadas a una orden de trabajo para Hhpp
     *
     * @param firstResult
     * @param maxResults
     * @param subTipoWorkfoce
     * @Author Orlando Velasquez
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendaPorOt(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce) throws ApplicationException {

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    /**
     * Consulta de todas agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param ordenTrabajoHhppMgl Orden De Trabajo Hhpp
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasPorOt(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId";

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer} Identificador de la agenda
     * @return MglAgendaDireccionAuditoria
     */
    public List<MglAgendaDireccionAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
     {
         EntityManager em = null;
         try {
            em = EntityManagerUtils.getEntityManager(this.getClass().getName());
            StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery(CONSULTA_AGENDA_AUD_SP);
             
            storedProcedureQuery.registerStoredProcedureParameter("PI_AGENDA_ID", Integer.class, ParameterMode.IN);
            storedProcedureQuery.registerStoredProcedureParameter("PO_REST_AGENDA_AUD", void.class, ParameterMode.REF_CURSOR);
            storedProcedureQuery.registerStoredProcedureParameter("PO_CODIGO", Integer.class, ParameterMode.OUT);
            storedProcedureQuery.registerStoredProcedureParameter("PO_DESCRIPCION", String.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter("PI_AGENDA_ID", agendaId);

            storedProcedureQuery.execute();
            @SuppressWarnings("unchecked")
            List<Object[]> listaAgendasResult = storedProcedureQuery.getResultList();
            int codigo = (int) storedProcedureQuery.getOutputParameterValue("PO_CODIGO");
            String descripcion = (String) storedProcedureQuery.getOutputParameterValue("PO_DESCRIPCION");
            
            List<MglAgendaDireccionAuditoria> agendasAuditoria = new ArrayList<>();
            
            if(codigo != 0){
                LOGGER.error("No se encuentra Log Estados para esta agenda");
                throw new ApplicationException("Se produjo un error " + codigo + descripcion);
            }
            if (!listaAgendasResult.isEmpty()) {

                listaAgendasResult.forEach(
                        resultado -> {
                            MglAgendaDireccionAuditoria agendaDatos = new MglAgendaDireccionAuditoria();
                            agendaDatos.setUsuarioEdicion(resultado[0] != null ? (String) resultado[0] : null);
                            agendaDatos.setFechaEdicion(resultado[1] != null ?(Date) resultado[1] : null);
                            CmtBasicaMgl nombreBasica = new CmtBasicaMgl();
                            nombreBasica.setNombreBasica(resultado[2]!= null ? (String) resultado[2] : null);
                            agendaDatos.setBasicaIdEstadoAgenda(nombreBasica);
                            agendasAuditoria.add(agendaDatos);
                        });

                return agendasAuditoria;
            }

             LOGGER.error("No se encuentra Log Estados para esta agenda");
             return agendasAuditoria;

        } catch (NoResultException ex) {
            LOGGER.error("Excepcion en el Procedimiento almacenado CONSULTA_AGENDA_AUD_SP" + ex);
            return null;

        } catch (ApplicationException e) {
            LOGGER.error("Error en el Procedimiento almacenado CONSULTA_AGENDA_AUD_SP" + e);
            return null;
        } finally{
             if(em != null && em.isOpen()){
                em.clear();
                em.close();
             }
            
         }
    }
    /**
     * Consulta la cantidad de agendas por orden de trabajo de Hhpp
     *
     * @param subTipoWorkfoce
     * @Author Orlando Velasquez
     * @param ordenTrabajoHhppMgl orden de trabajo
     * @return cantidad de agendas por orden de trabajo Hhpp
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int cantidadAgendasPorOtHhpp(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {

        int cantidad;
        try {
            String consulta = "SELECT COUNT(a) FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            cantidad = ((Long) query.getSingleResult()).intValue();
            return cantidad;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta el maximo de la secuencia de agenda por Ot
     *
     * @Author Orlando Velasquez
     * @param otHhppId
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOt(BigDecimal otHhppId)
            throws ApplicationException {

        int maximo;
        try {
            Query query = entityManager.createNamedQuery("MglAgendaDireccion.findByMaximoSec");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (otHhppId != null) {
                query.setParameter("otHhppId", otHhppId);
            }
            maximo = (Integer) query.getSingleResult();
            return maximo;
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta Agendas en estado Agendado o reagendado por orden de trabajo
     *
     * @param ordenTrabajoHhppMgl orden de Trabajo a la que fue asociada la
     * agenda
     * @Author Orlando Velasquez
     * @return cantidad de agendas agendadas o reagendadas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int buscarAgendasActivas(OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {

        int numeroAgendadas;
        try {
            String consulta = "SELECT COUNT(a) FROM MglAgendaDireccion a "
                    + "WHERE (a.basicaIdEstadoAgenda.identificadorInternoApp = :estadoAgendado "
                    + "OR a.basicaIdEstadoAgenda.identificadorInternoApp = :estadoReAgendado ) "
                    + "AND a.ordenTrabajo.otHhppId = :otHhppId";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("estadoAgendado", Constant.ESTADO_AGENDA_AGENDADA);
                    query.setParameter("estadoReAgendado", Constant.ESTADO_AGENDA_REAGENDADA);
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            numeroAgendadas = ((Long) query.getSingleResult()).intValue();
            return numeroAgendadas;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en Workforce
     * Autor: victor bocanegra
     *
     * @param otInWorkforce
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaPorOtIdWorkforce(long otInWorkforce)
            throws ApplicationException {
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ofpsId  = :WorkForceId  and a.estadoRegistro = 1";

            return (MglAgendaDireccion) entityManager
                    .createQuery(consulta)
                    .setParameter("WorkForceId", otInWorkforce)
                    .getSingleResult();
        } catch (NoResultException e) {
            MglAgendaDireccion mglAgendaHhpp = new MglAgendaDireccion();
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            return mglAgendaHhpp;
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta de agenda por identificador de la orden de Trabajo en MGL Autor:
     * victor bocanegra
     *
     * @param ofpsOtId Id de la orden de Trabajo en Workforce
     * @return MglAgendaDireccion
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public MglAgendaDireccion buscarAgendaPorOtIdMgl(String ofpsOtId)
            throws ApplicationException {
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ofpsOtId  = :ofpsOtId  and a.estadoRegistro = 1";

            return (MglAgendaDireccion) entityManager
                    .createQuery(consulta)
                    .setParameter("ofpsOtId", ofpsOtId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return new MglAgendaDireccion();
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     /**
     * Consulta las agendas asociadas a una orden de trabajo para Hhpp
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasByOtAndSubtipopOfsc
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce) 
            throws ApplicationException {

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

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

        MglAgendaDireccion agenda = null;
        String consulta;
        try {
            CmtBasicaMglManager manager = new CmtBasicaMglManager();

            CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                    findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);
            CmtBasicaMgl estadoCerradaBasicaMgl = manager.
                    findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);
            CmtBasicaMgl estadoNodoneBasicaMgl = manager.
                    findByCodigoInternoApp(Constant.ESTADO_AGENDA_NODONE);

            List<BigDecimal> lstEst = new ArrayList<>();

            if (estadoCanceladoBasicaMgl != null) {
                lstEst.add(estadoCanceladoBasicaMgl.getBasicaId());
            }
            if (estadoCerradaBasicaMgl != null) {
                lstEst.add(estadoCerradaBasicaMgl.getBasicaId());
            }
            if (estadoNodoneBasicaMgl != null) {
                lstEst.add(estadoNodoneBasicaMgl.getBasicaId());
            }

            if (control == 1) {
                consulta = "SELECT a FROM MglAgendaDireccion a "
                        + " WHERE a.ordenTrabajo = :ordenTrabajo and a.estadoRegistro = 1  "
                        + " AND a.secMltiOfsc > :secMltiOfsc AND a.timeSlot IS NOT NULL "
                        + " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst "
                        + " ORDER BY a.secMltiOfsc ASC";
            } else {
                consulta = "SELECT a FROM MglAgendaDireccion a "
                        + " WHERE a.ordenTrabajo = :ordenTrabajo and a.estadoRegistro = 1  "
                        + " AND a.secMltiOfsc < :secMltiOfsc AND a.timeSlot IS NOT NULL "
                        + " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst "
                        + " ORDER BY a.secMltiOfsc DESC";
            }

            Query query = entityManager.createQuery(consulta);

            if (agendaBase.getOrdenTrabajo() != null) {
                query.setParameter("ordenTrabajo", agendaBase.getOrdenTrabajo());
            }

            query.setParameter("secMltiOfsc", agendaBase.getSecMltiOfsc());
            
            if (!lstEst.isEmpty()) {
                query.setParameter("idEst", lstEst);
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> agendas = query.getResultList();

            if (agendas != null && !agendas.isEmpty()) {
                agenda = agendas.get(0);
            }
            return agenda;

        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        MglAgendaDireccion agenda = null;
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo = :ordenTrabajo "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce  "
                    + " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList  "
                    + " AND a.timeSlot IS NOT NULL  "
                    + " ORDER BY a.secMltiOfsc  DESC";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                query.setParameter("ordenTrabajo", ordenTrabajoHhppMgl);
            }

            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> agendas = query.getResultList();

            if (agendas != null && !agendas.isEmpty()) {
                agenda = agendas.get(0);
            }
            return agenda;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la que
     * fue asociada la agenda
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings({"unchecked", "unchecked", "unchecked"})
    public List<MglAgendaDireccion> buscarAgendasHistoricosPorOtHhpp(int firstResult,
            int maxResults, OtHhppMgl ordenTrabajoHhppMgl)
            throws ApplicationException {

        List<MglAgendaDireccion> resultList = null;

        if (ordenTrabajoHhppMgl != null) {
            try {
                String consulta = "SELECT a FROM MglAgendaDireccion a "
                        + " WHERE a.ordenTrabajo.otHhppId = :idOt  ";

                consulta += " ORDER BY (func('trunc', a.fechaAgenda))ASC, a.timeSlot ASC ";

                Query query = entityManager.createQuery(consulta);

                if (firstResult == 0 && maxResults == 0) {
                    LOGGER.info("Consulta sin paginado");
                } else {
                    query.setFirstResult(0);
                    query.setMaxResults(maxResults);
                }

                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("idOt", ordenTrabajoHhppMgl.getOtHhppId());
                }

                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                resultList = (List<MglAgendaDireccion>) query.getResultList();

            } catch (PersistenceException e) {
                LOGGER.error("Error consultando las agendas. ", e);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
        return (resultList);
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

        int result = 0;

        try {
            String consulta = "SELECT COUNT(1) FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :idOt  ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("idOt", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (ordenTrabajoHhppMgl != null && ordenTrabajoHhppMgl.getOtHhppId() != null) {
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            }
            return result;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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
    @SuppressWarnings("unchecked")
    public List<MglAgendaDireccion> agendasForCancelarHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, 
            String subTipoWorkfoce, Date fechaAgenda) throws ApplicationException {

        List<MglAgendaDireccion> resultList;
      
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :idOt AND a.fechaAgenda >= :fechaAgenda ";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }
            if (subTipoWorkfoce != null) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce ";

            }
            if (idAgenda != null) {
                consulta += " AND a.agendaId <> :agendaId ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("idOt", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }
            if (idAgenda != null) {
                query.setParameter("agendaId", idAgenda);
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            query.setParameter("fechaAgenda", fechaAgenda);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);
        List<BigDecimal> lstEst = new ArrayList<>();

        if (estadoCanceladoBasicaMgl != null) {
            lstEst.add(estadoCanceladoBasicaMgl.getBasicaId());
        }

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            if (!lstEst.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst ";
            }

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (!lstEst.isEmpty()) {
                query.setParameter("idEst", lstEst);

            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

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
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
                
            throws ApplicationException {

        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);
        List<BigDecimal> lstEst = new ArrayList<>();

        if (estadoCanceladoBasicaMgl != null) {
            lstEst.add(estadoCanceladoBasicaMgl.getBasicaId());
        }

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId "
                    + " AND a.fechaInivioVt IS NOT NULL "
                    + " AND a.fechaFinVt IS NULL";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            if (!lstEst.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst ";
            }

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (!lstEst.isEmpty()) {
                query.setParameter("idEst", lstEst);

            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
        
      /**
     * Consulta de agendas que tengan tecnico asociado Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param subTipoWorkfoce
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<MglAgendaDireccion> agendasWithTecnicoHhpp(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce) throws ApplicationException {

        List<MglAgendaDireccion> resultList;
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId"
                    + " AND a.identificacionTecnico IS NOT NULL  "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce "
                    + " AND a.estadoRegistro = 1";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
       /**
     * Consulta las agendas pendientes(menos las canceladas-nodone y cerradas) asociadas a una
     * orden de trabajo para Hhpp
     *
     * @Author Victor Bocanegra
     * @param subTipoWorkfoce
     * @param ordenTrabajoHhppMgl
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<MglAgendaDireccion> buscarAgendasPendientesByOtAndSubtipopOfscUnicaAgenda(OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {

        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);

        CmtBasicaMgl estadoNodoneBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_NODONE);
        
        CmtBasicaMgl estadoCerradaBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CERRADA);

        List<BigDecimal> lstEst = new ArrayList<>();

        if (estadoCanceladoBasicaMgl != null) {
            lstEst.add(estadoCanceladoBasicaMgl.getBasicaId());
        }

        if (estadoNodoneBasicaMgl != null) {
            lstEst.add(estadoNodoneBasicaMgl.getBasicaId());
        }
        
        if (estadoCerradaBasicaMgl != null) {
            lstEst.add(estadoCerradaBasicaMgl.getBasicaId());
        }

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :otHhppId ";

            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
            }

            if (!lstEst.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst ";
            }

            consulta += " ORDER BY a.fechaAgenda ASC ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("otHhppId", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (!lstEst.isEmpty()) {
                query.setParameter("idEst", lstEst);

            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<MglAgendaDireccion> resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
    
    /**
     * Consulta de agendas canceladas por orden de Trabajo
     *
     * @param ordenTrabajoHhppMgl {@link OtHhppMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<MglAgendaDireccion> agendasHhppCanceladasByOrdenAndSubTipoWorkfoce
        (OtHhppMgl ordenTrabajoHhppMgl, String subTipoWorkfoce)
            throws ApplicationException {

        List<MglAgendaDireccion> resultList;
        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);

        BigDecimal canceladaEst = null;

        if (estadoCanceladoBasicaMgl != null) {
            canceladaEst = estadoCanceladoBasicaMgl.getBasicaId();
        }

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :idOt "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce "
                    + " AND a.basicaIdEstadoAgenda.basicaId = :idEst ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("idOt", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }

            if (canceladaEst != null) {
                query.setParameter("idEst", canceladaEst);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Consulta de agendas para actualizar tecnico
     * Autor: Victor Bocanegra
     *
     * @param ordenTrabajoHhppMgl orden a consultarle las agendas
     * @param idsEstados estados de agenda a consultar
     * @param idAgenda ultima agenda
     * @param subTipoWorkfoce
     * @param fechaAgenda
     * @return {@link List}&lt;{@link MglAgendaDireccion}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<MglAgendaDireccion> agendasForUpdateTecnico(OtHhppMgl ordenTrabajoHhppMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, 
            String subTipoWorkfoce, Date fechaAgenda) throws ApplicationException {

        List<MglAgendaDireccion> resultList;
      
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.ordenTrabajo.otHhppId = :idOt AND a.fechaAgenda >= :fechaAgenda "
                    + " AND a.tecnicoAnticipado = 'N' ";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }
            if (subTipoWorkfoce != null) {
                consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce ";

            }
            if (idAgenda != null) {
                consulta += " AND a.agendaId <> :agendaId ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoHhppMgl != null) {
                if (ordenTrabajoHhppMgl.getOtHhppId() != null) {
                    query.setParameter("idOt", ordenTrabajoHhppMgl.getOtHhppId());
                }
            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }
            if (idAgenda != null) {
                query.setParameter("agendaId", idAgenda);
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            query.setParameter("fechaAgenda", fechaAgenda);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<MglAgendaDireccion>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
     
    /**
     * Consulta de todas agendas por orden de Trabajo para Hhpp
     *
     * @author Orlando Velasquez
     * @param agendaMgl
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendamiento(MglAgendaDireccion agendaMgl)
            throws ApplicationException {
        MglAgendaDireccion result = null;

        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.agendaId = :agendaId ";

            Query query = entityManager.createQuery(consulta);

            if (agendaMgl != null) {
                if (agendaMgl.getAgendaId() != null) {
                    query.setParameter("agendaId", agendaMgl.getAgendaId());
                }
            }
            if (agendaMgl != null && agendaMgl.getAgendaId() != null) {
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = query.getSingleResult() == null ? null : (MglAgendaDireccion) query.getSingleResult();
            }
            int cant = 0;
            if (result != null && result.getCantAgendas() != null) {
                cant = Integer.parseInt(result.getCantAgendas());
                cant++;
            } else {
                cant++;
            }
            return cant;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }

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
        try {
            String consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " WHERE a.agendaId  = :agendaId  and a.estadoRegistro = 1";

            return (MglAgendaDireccion) entityManager
                    .createQuery(consulta)
                    .setParameter("agendaId", idAgenda)
                    .getSingleResult();
        } catch (NoResultException e) {
            MglAgendaDireccion mglAgendaHhpp = new MglAgendaDireccion();
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return mglAgendaHhpp;
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        List<MglAgendaDireccion> agendas;
        String consulta;
        try {
            consulta = "SELECT a FROM MglAgendaDireccion a "
                    + " JOIN OtHhppMgl o ON  a.ordenTrabajo.otHhppId = o.otHhppId "
                    + " WHERE  a.estadoRegistro = 1  AND  o.estadoRegistro = 1 "
                    + " AND o.enlaceId = :enlaceId ";

            Query query = entityManager.createQuery(consulta);

            if (idEnlace != null && !idEnlace.isEmpty()) {
                query.setParameter("enlaceId", idEnlace);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            agendas = (List<MglAgendaDireccion>) query.getResultList();

            return agendas;

        } catch (Exception e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
