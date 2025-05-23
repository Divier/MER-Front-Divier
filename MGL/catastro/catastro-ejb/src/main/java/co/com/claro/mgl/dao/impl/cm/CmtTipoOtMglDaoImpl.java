/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.TipoOtCmDirDto;
import co.com.claro.mgl.dtos.FiltroConsultaSlaOtDto;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Dao Tipo Orden de Trabajo. Contiene la logica de acceso a datos de los tipos
 * de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtTipoOtMglDaoImpl extends GenericDaoImpl<CmtTipoOtMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtEstadoxFlujoMglDaoImpl.class);


    public FiltroConsultaSlaOtDto findListTablaSlaOt(FiltroConsultaSubTipoDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaSlaOtDto filtroConsultaSlaOtDto = new FiltroConsultaSlaOtDto();
        if (contar) {
            filtroConsultaSlaOtDto.setNumRegistros(findListTablaSlaOtData(filtro));
        } else {
            filtroConsultaSlaOtDto.setListaCmtTipoOtMgl(findListTablaSlaOtData(filtro, firstResult, maxResults));
        }
        return filtroConsultaSlaOtDto;
    }

    public long findListTablaSlaOtData(FiltroConsultaSubTipoDto filtro) throws ApplicationException {
        try {
            String queryStr = "SELECT COUNT(DISTINCT c) FROM CmtTipoOtMgl c WHERE 1=1";

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.idTipoOt = :idTipoOt ";
            }

            if (filtro.getDescrSeleccionada() != null && !filtro.getDescrSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.descTipoOt) LIKE :descTipoOt ";
            }

            if (filtro.getAgeSeleccionada() != null && !filtro.getAgeSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.otAgendable) LIKE :otAgendable ";
            }

            if (filtro.getInvSeleccionada() != null && !filtro.getInvSeleccionada().isEmpty()) {
                queryStr += " AND c.flagInv = :flagInv  ";
            }

            if (filtro.getSlaSeleccionada() > 0) {
                queryStr += " AND c.ans = :ans ";
            }

            if (filtro.getSlaAvisoSeleccionada() > 0) {
                queryStr += " AND c.ansAviso = :ansAviso ";
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                queryStr += " AND c.estadoRegistro = :estadoRegistro  ";
            }

            if (filtro.getEsVtSeleccionado() != null && !filtro.getEsVtSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.esTipoVT) LIKE :esTipoVT ";
            }

            if (filtro.getAcoGenSeleccionado() != null && !filtro.getAcoGenSeleccionado().isEmpty()) {
                queryStr += " AND c.otAcoAGenerar IN :otAcoAGenerar  ";
            }

            Query query = entityManager.createQuery(queryStr);

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                query.setParameter("idTipoOt", filtro.getCodigoSeleccionado());
            }

            if (filtro.getDescrSeleccionada() != null && !filtro.getDescrSeleccionada().isEmpty()) {
                query.setParameter("descTipoOt", "%" + filtro.getDescrSeleccionada().trim().toUpperCase() + "%");
            }

            if (filtro.getAgeSeleccionada() != null && !filtro.getAgeSeleccionada().isEmpty()) {
                query.setParameter("otAgendable", "%" + filtro.getAgeSeleccionada().trim().toUpperCase() + "%");
            }

            if (filtro.getInvSeleccionada() != null && !filtro.getInvSeleccionada().isEmpty()) {
                String inv = filtro.getInvSeleccionada().trim().toUpperCase();
                int i;
                if (inv.equalsIgnoreCase("N")) {
                    i = 0;
                } else if (inv.equalsIgnoreCase("Y")) {
                    i = 1;
                } else {
                    i = 3;
                }
                query.setParameter("flagInv", i);
            }

            if (filtro.getSlaSeleccionada() > 0) {
                query.setParameter("ans", filtro.getSlaSeleccionada());
            }

            if (filtro.getSlaAvisoSeleccionada() > 0) {
                query.setParameter("ansAviso", filtro.getSlaAvisoSeleccionada());
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                String est = filtro.getEstadoSeleccionado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            if (filtro.getEsVtSeleccionado() != null && !filtro.getEsVtSeleccionado().isEmpty()) {
                query.setParameter("esTipoVT", "%" + filtro.getEsVtSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getAcoGenSeleccionado() != null && !filtro.getAcoGenSeleccionado().isEmpty()) {
                String aco = filtro.getAcoGenSeleccionado().trim().toUpperCase();
                List<BigDecimal> tiposOt = findByDescripcion(aco);

                if (tiposOt != null) {
                    query.setParameter("otAcoAGenerar", tiposOt);
                }

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return (Long) query.getSingleResult();
        } catch (NoResultException nre) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + nre.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public List<CmtTipoOtMgl> findListTablaSlaOtData(FiltroConsultaSubTipoDto filtro, int firstResult, int maxResults) 
            throws ApplicationException {

        List <CmtTipoOtMgl> result;
        try {
            String queryStr = "SELECT DISTINCT c FROM CmtTipoOtMgl c  WHERE 1=1";

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                queryStr += " AND c.idTipoOt = :idTipoOt ";
            }

            if (filtro.getDescrSeleccionada() != null && !filtro.getDescrSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.descTipoOt) LIKE :descTipoOt ";
            }

            if (filtro.getAgeSeleccionada() != null && !filtro.getAgeSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(c.otAgendable) LIKE :otAgendable ";
            }

            if (filtro.getInvSeleccionada() != null && !filtro.getInvSeleccionada().isEmpty()) {
                queryStr += " AND c.flagInv = :flagInv  ";
            }

            if (filtro.getSlaSeleccionada() > 0) {
                queryStr += " AND c.ans = :ans ";
            }

            if (filtro.getSlaAvisoSeleccionada() > 0) {
                queryStr += " AND c.ansAviso = :ansAviso ";
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                queryStr += " AND c.estadoRegistro = :estadoRegistro  ";
            }

            if (filtro.getEsVtSeleccionado() != null && !filtro.getEsVtSeleccionado().isEmpty()) {
                queryStr += " AND UPPER(c.esTipoVT) LIKE :esTipoVT ";
            }

            if (filtro.getAcoGenSeleccionado() != null && !filtro.getAcoGenSeleccionado().isEmpty()) {
                queryStr += " AND c.otAcoAGenerar IN :otAcoAGenerar  ";
            }

            Query query = entityManager.createQuery(queryStr);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (filtro.getCodigoSeleccionado() != null
                    && !filtro.getCodigoSeleccionado().equals(BigDecimal.ZERO)) {
                query.setParameter("idTipoOt", filtro.getCodigoSeleccionado());
            }

            if (filtro.getDescrSeleccionada() != null && !filtro.getDescrSeleccionada().isEmpty()) {
                query.setParameter("descTipoOt", "%" + filtro.getDescrSeleccionada().trim().toUpperCase() + "%");
            }

            if (filtro.getAgeSeleccionada() != null && !filtro.getAgeSeleccionada().isEmpty()) {
                query.setParameter("otAgendable", "%" + filtro.getAgeSeleccionada().trim().toUpperCase() + "%");
            }

            if (filtro.getInvSeleccionada() != null && !filtro.getInvSeleccionada().isEmpty()) {
                String inv = filtro.getInvSeleccionada().trim().toUpperCase();
                int i;
                if (inv.equalsIgnoreCase("N")) {
                    i = 0;
                } else if (inv.equalsIgnoreCase("Y")) {
                    i = 1;
                } else {
                    i = 3;
                }
                query.setParameter("flagInv", i);
            }

            if (filtro.getSlaSeleccionada() > 0) {
                query.setParameter("ans", filtro.getSlaSeleccionada());
            }

            if (filtro.getSlaAvisoSeleccionada() > 0) {
                query.setParameter("ansAviso", filtro.getSlaAvisoSeleccionada());
            }

            if (filtro.getEstadoSeleccionado() != null && !filtro.getEstadoSeleccionado().isEmpty()) {
                String est = filtro.getEstadoSeleccionado().trim().toUpperCase();
                int e;
                if (est.equalsIgnoreCase("ACTIVADO")) {
                    e = 1;
                } else if (est.equalsIgnoreCase("DESACTIVADO")) {
                    e = 0;
                } else {
                    e = 3;
                }
                query.setParameter("estadoRegistro", e);
            }

            if (filtro.getEsVtSeleccionado() != null && !filtro.getEsVtSeleccionado().isEmpty()) {
                query.setParameter("esTipoVT", "%" + filtro.getEsVtSeleccionado().trim().toUpperCase() + "%");
            }

            if (filtro.getAcoGenSeleccionado() != null && !filtro.getAcoGenSeleccionado().isEmpty()) {
                String aco = filtro.getAcoGenSeleccionado().trim().toUpperCase();
                List<BigDecimal> tiposOt = findByDescripcion(aco);

                if (tiposOt != null) {
                    query.setParameter("otAcoAGenerar", tiposOt);
                }

            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (NoResultException nre) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + nre.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }

    }

    public List<CmtTipoOtMgl> findByBasicaId(CmtBasicaMgl cmtBasicaMgl) {
        List<CmtTipoOtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoOtMgl.findByBasicaId");
        query.setParameter("basicaIdTipoOt", cmtBasicaMgl);
        resultList = query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
      /**
     * Busca el subtipo de ot por identificador interno de la app
     *
     * @author Victor Bocanegra 
     * @param codigoInternoApp codigo interno de la tabla
     * @return CmtTipoOtMgl encontrado
     * @throws ApplicationException
     */
        
    public CmtTipoOtMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtTipoOtMgl result;
        try {
            Query query = entityManager.createNamedQuery("CmtTipoOtMgl.findByCodigoInternoApp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("identificadorInternoApp", codigoInternoApp);
            result = (CmtTipoOtMgl) query.getSingleResult();
            getEntityManager().clear();
            return result;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para el codigoApp del tipo Ot");
        }
    }
          /**
     * Busca los subtipo de ot escluyendo los de acometida u otro
     *
     * @author Victor Bocanegra 
     * @param idAco id de acometidas u otro
     * @return List<CmtTipoOtMgl> encontrado
     * @throws ApplicationException
     */
       
     public List<CmtTipoOtMgl> findByNoIdAco(List<BigDecimal> idAco) throws ApplicationException {

        StringBuilder sql = new StringBuilder();
        List<CmtTipoOtMgl> resulList;
        try {
            sql.append("SELECT c FROM CmtTipoOtMgl c WHERE c.idTipoOt NOT IN :idAcos  AND c.estadoRegistro= 1");

            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("idAcos", idAco);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = q.getResultList();
            getEntityManager().clear();
            return resulList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }
    
     /**
     * Obtiene todos los Tipo de Ordenes de Trabajo que generan OT de acometidas
     * que existen en el repositorio.
     *
     * @param tipoOtAcometida tipo OT acometida
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtGeneraAco(CmtTipoOtMgl tipoOtAcometida)
            throws ApplicationException {

        StringBuilder sql = new StringBuilder();
        List<CmtTipoOtMgl> resulList;
        try {
            sql.append("SELECT c FROM CmtTipoOtMgl c WHERE c.otAcoAGenerar = :idAco"
                    + "  AND c.estadoRegistro= 1");

            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("idAco", tipoOtAcometida.getIdTipoOt());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = q.getResultList();
            getEntityManager().clear();
            return resulList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo que generan acometidas
     * en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtAcometidas()
            throws ApplicationException {

        StringBuilder sql = new StringBuilder();
        List<CmtTipoOtMgl> resulList;
        try {
            sql.append("SELECT c FROM CmtTipoOtMgl c WHERE c.esTipoVT = 'N'"
                    + "  AND c.estadoRegistro= 1");

            Query q = entityManager.createQuery(sql.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = q.getResultList();
            getEntityManager().clear();
            return resulList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }
    
     /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo de un tipode trabajo
     * que no son acometidas son VTs en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl Tipo de trabajo
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByTipoTrabajoAndIsVT(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        List<CmtTipoOtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoOtMgl.findByTipoTrabajoAndIsVT");
        query.setParameter("basicaIdTipoOt", cmtBasicaMgl);
        resultList = (List<CmtTipoOtMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
     /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo Vts 
     * que generan acometidas
     * en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtVts()
            throws ApplicationException {

        StringBuilder sql = new StringBuilder();
        List<CmtTipoOtMgl> resulList;
        try {
            sql.append("SELECT c FROM CmtTipoOtMgl c WHERE c.esTipoVT = 'Y'"
                    + "  AND c.estadoRegistro= 1");

            Query q = entityManager.createQuery(sql.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            resulList = q.getResultList();
            getEntityManager().clear();
            return resulList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }
   
    public List<CmtTipoOtMgl> findById(BigDecimal id) {
        List<CmtTipoOtMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTipoOtMgl.findByBasicaId");
        query.setParameter("basicaIdTipoOt", id);
        resultList = (List<CmtTipoOtMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
    
    /**
     * Obtiene todos los Sub Tipo de Ordenes de Trabajo segun tipo de Ot y
     * tecnologias
     *
     * @param tipoOt
     * @author cardenaslb
     * @param basicaTecnologia
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByTipoTrabajoAndTecno(CmtBasicaMgl tipoOt, CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {
        List<CmtTipoOtMgl> listaOt;
        try {
            Query query = entityManager.createQuery("SELECT b FROM CmtTipoOtMgl b  "
                    + " WHERE b.basicaIdTipoOt.basicaId =:basicaIdTipoOt AND b.esTipoVT='Y' "
                    + " AND b.estadoRegistro=1 AND b.tipoFlujoOt.basicaId IN "
                    + "( SELECT  t.tipoFlujoOtObj.basicaId FROM CmtEstadoxFlujoMgl t "
                    + " WHERE t.basicaTecnologia.basicaId = :basicaTecnologia "
                    + " AND t.esEstadoInicial like 'Y' AND t.estadoRegistro=1) ", CmtBasicaMgl.class);
            query.setParameter("basicaIdTipoOt", tipoOt.getBasicaId());
            query.setParameter("basicaTecnologia", basicaTecnologia.getBasicaId());
            listaOt = (List<CmtTipoOtMgl>) query.getResultList();
            getEntityManager().clear();
            return listaOt;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }

    public List<TipoOtCmDirDto> findAllSubTipoOtCmHhpp()
            throws ApplicationException {
        List<TipoOtCmDirDto> listaTipoOtCmDir = new ArrayList<>();
        try {
            CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
            CmtTipoBasicaMgl tipoGeneralOrdenId;
            tipoGeneralOrdenId = cmtTipoBasicaMglDaoImpl.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES);
            List<Object[]> resultListTipoOt = null;
            List<CmtTipoOtMgl> resultList1;
            
                Query q1 = entityManager.createQuery("Select t from  CmtTipoOtMgl t WHERE t.estadoRegistro = :estadoRegistro ");
                q1.setParameter("estadoRegistro", 1);
                q1.setHint("javax.persistence.cache.storeMode", "REFRESH");
                resultList1 = (List<CmtTipoOtMgl>) q1.getResultList();
                int idTipoOtCm = 0;
                for (CmtTipoOtMgl tipoOt : resultList1) {
                    idTipoOtCm++;
                    BigDecimal id = new BigDecimal(idTipoOtCm);
                    TipoOtCmDirDto tipoOtCmDirDto = new TipoOtCmDirDto();
                    tipoOtCmDirDto.setIdTipoOt(tipoOt.getIdTipoOt());
                    tipoOtCmDirDto.setNombre(tipoOt.getDescTipoOt());
                    listaTipoOtCmDir.add(tipoOtCmDirDto);
                }
            if (tipoGeneralOrdenId != null) {
                Query q2 = entityManager.createQuery("SELECT t.nombreTipoOt, t.tipoOtId, c.codigoBasica  "
                        + " FROM CmtBasicaMgl c INNER JOIN TipoOtHhppMgl t on c.basicaId = t.subTipoOrdenOFSC.basicaId "
                        + " AND  t.identificadorInterno = '@NA' "
                        + " AND c.estadoRegistro = 1 AND c.activado='Y' AND c.identificadorInternoApp IS NULL  and c.tipoBasicaObj.tipoBasicaId = :tipoBasicaId ");
                q2.setHint("javax.persistence.cache.storeMode", "REFRESH");
                q2.setParameter("tipoBasicaId", tipoGeneralOrdenId.getTipoBasicaId());
                resultListTipoOt = (List<Object[]>) q2.getResultList();
                for (Object[] tipoOt : resultListTipoOt) {

                    TipoOtCmDirDto tipoOtCmDirDto = new TipoOtCmDirDto();
                    tipoOtCmDirDto.setIdTipoOt(((BigDecimal) tipoOt[1]));
                    tipoOtCmDirDto.setNombre(((String) tipoOt[0]) + " (" + ((String) tipoOt[2]) + " )");
                    tipoOtCmDirDto.setCodigoBasica(((String) tipoOt[2]));
                    listaTipoOtCmDir.add(tipoOtCmDirDto);
                }
            }

            return listaTipoOtCmDir;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<TipoOtCmDirDto> findAllSubTipoOtCmHhpp2()
            throws ApplicationException {
        List<TipoOtCmDirDto> listaTipoOtCmDir = new ArrayList<>();
        try {
            List<CmtTipoOtMgl> resultList1;
            Query q1 = entityManager.createQuery("Select t from " + entityClass.getSimpleName()
                    + " t WHERE t.estadoRegistro = :estadoRegistro");
            q1.setParameter("estadoRegistro", 1);
            q1.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList1 = (List<CmtTipoOtMgl>) q1.getResultList();
            for (CmtTipoOtMgl tipoOt : resultList1) {
                TipoOtCmDirDto tipoOtCmDirDto = new TipoOtCmDirDto();
                tipoOtCmDirDto.setIdTipoOt(tipoOt.getIdTipoOt());
                tipoOtCmDirDto.setNombre(tipoOt.getDescTipoOt());
                listaTipoOtCmDir.add(tipoOtCmDirDto);
            }
            List<TipoOtHhppMgl> resultList2;
            Query q2 = entityManager.createQuery("SELECT c FROM "
                    + "TipoOtHhppMgl c WHERE c.estadoRegistro = :estado ORDER BY c.fechaCreacion DESC");
            q2.setParameter("estado", 1);
            q2.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList2 = (List<TipoOtHhppMgl>) q2.getResultList();  
            for (TipoOtHhppMgl tipoOt : resultList2) {
                TipoOtCmDirDto tipoOtCmDirDto = new TipoOtCmDirDto();
                tipoOtCmDirDto.setIdTipoOt(tipoOt.getTipoOtId());
                tipoOtCmDirDto.setNombre(tipoOt.getNombreTipoOt());
                listaTipoOtCmDir.add(tipoOtCmDirDto);
            }

            return listaTipoOtCmDir;
        } catch (PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Busca el subtipo de ot por nombre
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la tabla
     * @return CmtTipoOtMgl encontrado
     * @throws ApplicationException
     */
    public List<BigDecimal> findByDescripcion(String nombre) throws ApplicationException {
        
        List<BigDecimal> result = null;
 
        try {
            Query query = entityManager.createQuery("SELECT d.idTipoOt FROM CmtTipoOtMgl d  "
                    + "WHERE d.estadoRegistro=1  AND UPPER(d.descTipoOt) LIKE :descTipoOt ");

            query.setParameter("descTipoOt", "%" + nombre.trim().toUpperCase() + "%");

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            result = query.getResultList();
           
            return result;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            return result;
        }
    }
    
    /**
     * Obtiene todos los Sub Tipo de Ordenes de Trabajo segun la tecnologia
     *
     * @author bocanegravm
     * @param basicaTecnologia
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findSubTipoOtByTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {
        
        List<CmtTipoOtMgl> listaOt;
        try {
            Query query = entityManager.createQuery("SELECT DISTINCT b FROM CmtTipoOtMgl b  "
                    + "WHERE  b.estadoRegistro=1 AND b.tipoFlujoOt.basicaId IN "
                    + "( SELECT  DISTINCT(t.tipoFlujoOtObj.basicaId) FROM CmtEstadoxFlujoMgl t "
                    + "WHERE t.basicaTecnologia.basicaId = :basicaTecnologia AND t.estadoRegistro=1) ", CmtBasicaMgl.class);

            query.setParameter("basicaTecnologia", basicaTecnologia.getBasicaId());
            listaOt = (List<CmtTipoOtMgl>) query.getResultList();
            return listaOt;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para los parametros");
        }
    }
}
