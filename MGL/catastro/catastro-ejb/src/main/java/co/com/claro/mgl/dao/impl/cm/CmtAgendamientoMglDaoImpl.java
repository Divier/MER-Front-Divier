package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendaAuditoria;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
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
 * Clase para realizar las operaciones de base de datos sobre la entidad
 * {@link CmtAgendamientoMgl}
 *
 * @author wgavidia
 * @version 23/11/2017
 */
public class CmtAgendamientoMglDaoImpl extends GenericDaoImpl<CmtAgendamientoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtAgendamientoMglDaoImpl.class);
    private static final String CONSULTA_AGENDA_AUD_SP = Constant.MGL_DATABASE_SCHEMA+".AGENDA_DIRECCION_AUD_PKG.PRTBCMTAGENDADIRAUD";

    /**
     * Consulta de agendas por orden de Trabajo y subtipo
     *
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subt
     * ipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings({"unchecked", "unchecked"})
    public List<CmtAgendamientoMgl> buscarAgendaPorOtAndSubTipoWorkfoce(int firstResult,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {
        
        List<CmtAgendamientoMgl> resultList = null;
        
        if (ordenTrabajoMgl != null) {
            try {
                String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                        + " WHERE a.ordenTrabajo.idOt = :idOt  ";
                
                if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                    consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce";
                }

                consulta += " ORDER BY (func('trunc', a.fechaAgenda))ASC, a.timeSlot ASC ";

                Query query = entityManager.createQuery(consulta);

                if (firstResult == 0 && maxResults == 0) {
                    LOGGER.info("Consulta sin paginado");
                } else {
                    query.setFirstResult(firstResult);
                    query.setMaxResults(maxResults);
                }

                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }

                if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                    query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

                }
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                resultList = (List<CmtAgendamientoMgl>) query.getResultList();

            } catch (PersistenceException e) {
                LOGGER.error("Error consultando las agendas. ", e);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
        
        return (resultList);
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
        
        int result = 0;

        try {
            String consulta = "SELECT COUNT(1) FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt  ";
            if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                    consulta += " AND a.subTipoWorkFoce = :subTipoWorkFoce ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
             if (subTipoWorkfoce != null && !subTipoWorkfoce.isEmpty()) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);
            }
            
            
            if (ordenTrabajoMgl != null && ordenTrabajoMgl.getIdOt() != null) {
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
     * Consulta de agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasPorOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt AND a.subTipoWorkFoce = :subTipoWorkFoce";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

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
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaPorOtIdWorkforce(BigDecimal otInWorkforce)
            throws ApplicationException {
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.WorkForceId  = :WorkForceId  and a.estadoRegistro = 1";

            List<CmtAgendamientoMgl> agendaList = (List<CmtAgendamientoMgl>) entityManager
                    .createQuery(consulta)
                    .setParameter("WorkForceId", otInWorkforce)
                    .getResultList();
            
            if (!agendaList.isEmpty()) {
                return agendaList.get(0);
            } else {
                CmtAgendamientoMgl cmtAgendamientoMgl = new CmtAgendamientoMgl();
                return cmtAgendamientoMgl;
            }
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    /**
     * Consulta las auditorias de la agenda seleccionada por agendaId
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer} Id de la agenda
     * @return CmtAgendaAuditoria
     */
    public List<CmtAgendaAuditoria> buscarAgendasAuditoriaPorHistoricosAgenda(Integer agendaId)
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
            
            List<CmtAgendaAuditoria> agendasAuditoria = new ArrayList<>();
            
            if(codigo != 0){
                LOGGER.error("No se encuentra Log Estados para esta agenda");
                throw new ApplicationException("Se produjo un error " + codigo + descripcion);
            }
            if (!listaAgendasResult.isEmpty()) {

                listaAgendasResult.forEach(
                        resultado -> {
                            CmtAgendaAuditoria agendaDatos = new CmtAgendaAuditoria();
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
     * Consulta el maximo de la secuencia por Ot Autor: victor bocanegra
     *
     * @param idOt Id de la orden de trabajo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOt(BigDecimal idOt)
            throws ApplicationException {

        int maximo;
        try {
            Query query = entityManager.createNamedQuery("CmtAgendamientoMgl.findByMaximoSec");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (idOt != null) {
                query.setParameter("idOt", idOt);
            }
            maximo = (Integer) query.getSingleResult();
            return maximo;
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
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaPorOtIdMgl(String ofpsOtId)
            throws ApplicationException {
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ofpsOtId  = :ofpsOtId  and a.estadoRegistro = 1";

            return (CmtAgendamientoMgl) entityManager
                    .createQuery(consulta)
                    .setParameter("ofpsOtId", ofpsOtId)
                    .getSingleResult();
        } catch (NoResultException e) {
            CmtAgendamientoMgl cmtAgendamientoMgl = new CmtAgendamientoMgl();
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            return cmtAgendamientoMgl;
        } catch (Exception e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasForCancelar(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, Date fechaAgenda) throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt AND a.fechaAgenda >= :fechaAgenda ";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }
            if (idAgenda != null) {
                consulta += " AND a.id <> :agendaId ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }
            if (idAgenda != null) {
                query.setParameter("agendaId", idAgenda);
            }
            query.setParameter("fechaAgenda", fechaAgenda);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Consulta de agendas por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasPorOT(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
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
     * @return CmtAgendamientoMgl
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public CmtAgendamientoMgl buscarAgendaAnteriorPosterior(CmtAgendamientoMgl agendaBase, int control)
            throws ApplicationException {

        CmtAgendamientoMgl agenda = null;
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
                consulta = "SELECT a FROM CmtAgendamientoMgl a "
                        + " WHERE a.ordenTrabajo = :ordenTrabajo and a.estadoRegistro = 1  "
                        + " AND a.secMltiOfsc > :secMltiOfsc  AND a.timeSlot IS NOT NULL "
                        + " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst "
                        + " ORDER BY a.secMltiOfsc ASC";
            } else {
                consulta = "SELECT a FROM CmtAgendamientoMgl a "
                        + " WHERE a.ordenTrabajo = :ordenTrabajo and a.estadoRegistro = 1  "
                        + " AND a.secMltiOfsc < :secMltiOfsc  AND a.timeSlot IS NOT NULL "
                        + " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst "
                        + " ORDER BY a.secMltiOfsc DESC";
            }
            
            Query query = entityManager.createQuery(consulta);

            if (agendaBase.getOrdenTrabajo() != null) {
                query.setParameter("ordenTrabajo", agendaBase.getOrdenTrabajo());
            }
            
            query.setParameter("secMltiOfsc", agendaBase.getSecMltiOfsc());
            
            if (lstEst.size() > 0) {
                query.setParameter("idEst", lstEst);
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CmtAgendamientoMgl> agendas = query.getResultList();
            
            if(agendas != null && agendas.size()> 0){
                agenda = agendas.get(0);
            }
            return agenda;

        } catch (ApplicationException e) {
            LOGGER.error("Error consultando la agenda. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        CmtAgendamientoMgl agenda = null;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo = :ordenTrabajo "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce  "
                    + " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList  "
                    + " AND a.timeSlot IS NOT NULL "
                    + " ORDER BY a.secMltiOfsc DESC";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                query.setParameter("ordenTrabajo", ordenTrabajoMgl);
            }

            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            @SuppressWarnings("unchecked")
            List<CmtAgendamientoMgl> agendas = query.getResultList();

            if (agendas != null && agendas.size() > 0) {
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
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> buscarAgendasHistoricosPorOt(int firstResult,
            int maxResults, CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        List<CmtAgendamientoMgl> resultList = null;

        if (ordenTrabajoMgl != null) {
            try {
                String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                        + " WHERE a.ordenTrabajo.idOt = :idOt  ";

                consulta += " ORDER BY (func('trunc', a.fechaAgenda))ASC, a.timeSlot ASC ";

                Query query = entityManager.createQuery(consulta);

                if (firstResult == 0 && maxResults == 0) {
                    LOGGER.info("Consulta sin paginado");
                } else {
                    query.setFirstResult(firstResult);
                    query.setMaxResults(maxResults);
                }

                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }

                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                resultList = (List<CmtAgendamientoMgl>) query.getResultList();

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
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @return int
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int getCountAgendasHistoricosPorOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        int result = 0;

        try {
            String consulta = "SELECT COUNT(1) FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt  ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (ordenTrabajoMgl != null && ordenTrabajoMgl.getIdOt() != null) {
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
     * Consulta de agendas posteriores
     * Autor: victor bocanegra
     *
     * @param agendaBase
     * @param idsEstados
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings({"unchecked"})
    public List<CmtAgendamientoMgl> buscarAgendasrPosteriores(CmtAgendamientoMgl agendaBase,
            List<BigDecimal> idsEstados)
            throws ApplicationException {

        List<CmtAgendamientoMgl> agendas;
        String consulta;
        try {
             consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo = :ordenTrabajo and a.estadoRegistro = 1  "
                    + " AND a.secMltiOfsc > :secMltiOfsc ";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }
            consulta += " ORDER BY a.secMltiOfsc DESC";
            
            Query query = entityManager.createQuery(consulta);

            if (agendaBase.getOrdenTrabajo() != null) {
                query.setParameter("ordenTrabajo", agendaBase.getOrdenTrabajo());
            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }
            
            query.setParameter("secMltiOfsc", agendaBase.getSecMltiOfsc());
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            agendas = (List<CmtAgendamientoMgl>) query.getResultList();

            return agendas;

        } catch (Exception e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }        
    }
    /**
     * Consulta de agendas pendientes(Menos canceladas)
     * por orden de Trabajo
     *
     * @param ordenTrabajoMgl {@link CmtOrdenTrabajoMgl} orden de Trabajo a la
     * que fue asociada la agenda
     * @param subTipoWorkfoce subtipo de trabajo en OFSC
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasPendientesByOrdenAndSubTipoWorkfoce(CmtOrdenTrabajoMgl ordenTrabajoMgl, String subTipoWorkfoce)
            throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);
        List<BigDecimal> lstEst = new ArrayList<>();

        if (estadoCanceladoBasicaMgl != null) {
            lstEst.add(estadoCanceladoBasicaMgl.getBasicaId());
        }

        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce "
                    + " AND a.basicaIdEstadoAgenda.basicaId NOT IN :idEst ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }

            if (lstEst.size() > 0) {
                query.setParameter("idEst", lstEst);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasWithTecnico(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, String subTipoWorkfoce) throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt"
                    + " AND a.identificacionTecnico IS NOT NULL  "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce "
                    + " AND a.estadoRegistro = 1";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

		} catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        List<CmtAgendamientoMgl> resultList;
        CmtBasicaMglManager manager = new CmtBasicaMglManager();

        CmtBasicaMgl estadoCanceladoBasicaMgl = manager.
                findByCodigoInternoApp(Constant.ESTADO_AGENDA_CANCELADA);

        BigDecimal canceladaEst = null;

        if (estadoCanceladoBasicaMgl != null) {
            canceladaEst = estadoCanceladoBasicaMgl.getBasicaId();
        }

        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt "
                    + " AND a.subTipoWorkFoce = :subTipoWorkFoce "
                    + " AND a.basicaIdEstadoAgenda.basicaId = :idEst ";

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (subTipoWorkfoce != null) {
                query.setParameter("subTipoWorkFoce", subTipoWorkfoce);

            }

            if (canceladaEst != null) {
                query.setParameter("idEst", canceladaEst);

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
      /**
     * Consulta de agendas para actualizar tecnico 
     * Autor: Victor Bocanegra
     * @param ordenTrabajoMgl  orden a consultarle las agendas
     * @param idsEstados  estados de agenda a consultar
     * @param idAgenda  ultima agenda
     * @param fechaAgenda
     * @return {@link List}&lt;{@link CmtAgendamientoMgl}> Listado de agendas
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtAgendamientoMgl> agendasForUpdateTecnico(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            List<BigDecimal> idsEstados, BigDecimal idAgenda, Date fechaAgenda) throws ApplicationException {

        List<CmtAgendamientoMgl> resultList;
        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.ordenTrabajo.idOt = :idOt AND a.fechaAgenda >= :fechaAgenda "
                    + " AND a.tecnicoAnticipado= 'N'";

            if (idsEstados != null && !idsEstados.isEmpty()) {
                consulta += " AND a.basicaIdEstadoAgenda.basicaId IN :estadosList ";
            }
            if (idAgenda != null) {
                consulta += " AND a.id <> :agendaId ";
            }

            Query query = entityManager.createQuery(consulta);

            if (ordenTrabajoMgl != null) {
                if (ordenTrabajoMgl.getIdOt() != null) {
                    query.setParameter("idOt", ordenTrabajoMgl.getIdOt());
                }
            }
            if (idsEstados != null && !idsEstados.isEmpty()) {
                query.setParameter("estadosList", idsEstados);
            }
            if (idAgenda != null) {
                query.setParameter("agendaId", idAgenda);
            }
            query.setParameter("fechaAgenda", fechaAgenda);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtAgendamientoMgl>) query.getResultList();
            return resultList;

        } catch (PersistenceException e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
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

        CmtAgendamientoMgl result = null;

        try {
            String consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " WHERE a.id = :agendaId ";

            Query query = entityManager.createQuery(consulta);

            if (agenda != null) {
                if (agenda.getId() != null) {
                    query.setParameter("agendaId", agenda.getId());
                }
            }
            if (agenda != null && agenda.getId() != null) {
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = query.getSingleResult() == null ? null : (CmtAgendamientoMgl) query.getSingleResult();
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
     * Consulta de todas las agendas por id Enlace desde APP externa Autor:
     * victor bocanegra
     *
     * @param idEnlace
     * @return List<CmtAgendamientoMgl>
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtAgendamientoMgl> agendasByIdEnlace(String idEnlace)
            throws ApplicationException {

        List<CmtAgendamientoMgl> agendas;
        String consulta;
        try {
            consulta = "SELECT a FROM CmtAgendamientoMgl a "
                    + " JOIN CmtOrdenTrabajoMgl o ON  a.ordenTrabajo.idOt = o.idOt "
                    + " WHERE  a.estadoRegistro = 1  AND  o.estadoRegistro = 1 "
                    + " AND o.enlaceId = :enlaceId ";

            Query query = entityManager.createQuery(consulta);

            if (idEnlace != null && !idEnlace.isEmpty()) {
                query.setParameter("enlaceId", idEnlace);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            agendas = (List<CmtAgendamientoMgl>) query.getResultList();

            return agendas;

        } catch (Exception e) {
            LOGGER.error("Error consultando las agendas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
