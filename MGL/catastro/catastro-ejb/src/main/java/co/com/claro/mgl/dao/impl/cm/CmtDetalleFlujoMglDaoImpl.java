/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaDetalleFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Dao Detalle Flujo de un tipo de Orden de Trabajo. Contiene la logica de
 * acceso a datos al detalle del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtDetalleFlujoMglDaoImpl extends GenericDaoImpl<CmtDetalleFlujoMgl> {
  private static final Logger LOGGER = LogManager.getLogger(CmtDetalleFlujoMglDaoImpl.class);
    /**
     * Busca el datalle del Flujo por el tipo del Flujo.Permite realizar la
 busqueda del detalle del Flujo por medio del tipo de Flujo en el
 repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tecnologia) 
            throws ApplicationException {
        List<CmtDetalleFlujoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByTipoFlujo");
        query.setParameter("tipoFlujoOt", tipoFlujoOt);
        query.setParameter("tecnologia", tecnologia);
        resultList = (List<CmtDetalleFlujoMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Busca el datalle del Flujo por el tipo del Flujo y el estado Inicial.Permite realizar la busqueda del detalle del Flujo por medio del tipo de
 Flujo y el estado Inicial en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param estadoInicial orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIni(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInicial,CmtBasicaMgl tecnologia) throws ApplicationException {
        List<CmtDetalleFlujoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByFlujoAndEstadoIni");
        query.setParameter("tipoFlujoOt", tipoFlujoOt);
        query.setParameter("estadoInicial", estadoInicial);
        query.setParameter("tecnologia", tecnologia);
        resultList = (List<CmtDetalleFlujoMgl>) query.getResultList();
        return resultList;
    }
    
    public FiltroConsultaDetalleFlujoDto findTablasTipoBasicaSearch(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        FiltroConsultaDetalleFlujoDto filtroConsultaDetalleFlujoDto = new FiltroConsultaDetalleFlujoDto();
        if (contar) {
            filtroConsultaDetalleFlujoDto.setNumRegistros(findTablasDetallexFlujoContar(params));
        } else {
            filtroConsultaDetalleFlujoDto.setListaCmtEstadoxFlujoMgl(findTablasDetallexFlujoSearchData(params, firstResult, maxResults));
        }
        return filtroConsultaDetalleFlujoDto;
    }

    public long findTablasDetallexFlujoContar(HashMap<String, Object> params) throws ApplicationException {

        try {
            StringBuilder sql = new StringBuilder();
            Query q = entityManager.createNamedQuery("CmtDetalleFlujoMgl.countByFilter");
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
              if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            } else {
                q.setParameter("tipoFlujo", "%%");
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                q.setParameter("tecnologia", "%" + params.get("tecnologia").toString().trim().toUpperCase() + "%");
            } else {
                q.setParameter("tecnologia", "%%");
            }
            return (Long) q.getSingleResult();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }
    }

    public List<CmtDetalleFlujoMgl> findTablasDetallexFlujoSearchData(HashMap<String, Object> params, int firstResult, int maxResults) throws ApplicationException {
        try {
            Query q = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByFilter");
            
            if (params.get("tipoFlujoOtObj") != null && !params.get("tipoFlujoOtObj").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujo", "%" + params.get("tipoFlujoOtObj").toString().trim().toUpperCase() + "%");
            } else {
                q.setParameter("tipoFlujo", "%%");
            }
            if (params.get("tecnologia") != null && !params.get("tecnologia").toString().trim().isEmpty()) {
                q.setParameter("tecnologia", "%" + params.get("tecnologia").toString().trim().toUpperCase() + "%");
            } else {
                q.setParameter("tecnologia", "%%");
            }
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            return q.getResultList();
        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Se presentó un error al consultar "
                    + "los datos para la Cuenta Matriz. Intente más tarde.");
        }

    }
    
    /**
     * Busca una lista de  detalle flujo por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return List<CmtDetalleFlujoMgl> lista encontrada
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        List<CmtDetalleFlujoMgl> result;
        try {
            Query query = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByCodigoInternoApp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("identificadorInternoApp", codigoInternoApp);
            result = query.getResultList();
            return result;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("No se encontraron registros para el codigoApp del tipo detalle");
        }
    }
    
    
    
    
     /**
     * Valia si existen configuraciones duplicadas
     * tipo de flujo.
     *
     * @author Lenis Cardenas
     * @param params
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    
        public List<CmtDetalleFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        
        try {
            String queryTipo = "SELECT n FROM CmtDetalleFlujoMgl n WHERE n.estadoRegistro = 1  ";

            if (params.get("tipoOtlist") != null && !params.get("tipoOtlist").toString().trim().isEmpty()) {
                queryTipo += "AND n.tipoFlujoOtObj.basicaId = :tipoFlujoOtObj ";
            }
            if (params.get("tecnologiaSelected") != null && !params.get("tecnologiaSelected").toString().trim().isEmpty()) {
                queryTipo += "AND  n.basicaTecnologia.basicaId = :basicaTecnologia ";
            }
            if (params.get("estadoIntInicialList") != null && !params.get("estadoIntInicialList").toString().trim().isEmpty()) {
                queryTipo += "AND  n.estadoInicialObj.basicaId = :estadoInicialObj ";
            }
            if (params.get("estadoInternoFinalList") != null && !params.get("estadoInternoFinalList").toString().trim().isEmpty()) {
                queryTipo += "AND  n.proximoEstado.basicaId = :proximoEstado ";
            }

            if (params.get("rolAprobador") != null && !params.get("rolAprobador").toString().trim().isEmpty()) {
                queryTipo += "AND  UPPER (n.rolAprobador) like :rolAprobador ";
            }
            
            if (params.get("razonSelected") != null && !params.get("razonSelected").toString().trim().isEmpty()) {
                queryTipo += "AND  n.basicaRazonNodone.basicaId = :basicaRazonNodone ";
            } else {
                queryTipo += "AND  n.basicaRazonNodone.basicaId  IS NULL";
            }


            Query q = entityManager.createQuery(queryTipo);

            if (params.get("tipoOtlist") != null && !params.get("tipoOtlist").toString().trim().isEmpty()) {
                q.setParameter("tipoFlujoOtObj",   new BigDecimal(params.get("tipoOtlist").toString()));
            }
            if (params.get("tecnologiaSelected") != null && !params.get("tecnologiaSelected").toString().trim().isEmpty()) {
                q.setParameter("basicaTecnologia",   new BigDecimal(params.get("tecnologiaSelected").toString()));
            }
            if (params.get("estadoIntInicialList") != null && !params.get("estadoIntInicialList").toString().trim().isEmpty()) {
                q.setParameter("estadoInicialObj",  new BigDecimal(params.get("estadoIntInicialList").toString()));
            }
            if (params.get("estadoInternoFinalList") != null && !params.get("estadoInternoFinalList").toString().trim().isEmpty()) {
                q.setParameter("proximoEstado",  new BigDecimal(params.get("estadoInternoFinalList").toString()));
            }

            if (params.get("rolAprobador") != null && !params.get("rolAprobador").toString().trim().isEmpty()) {
                q.setParameter("rolAprobador", "%" + params.get("rolAprobador").toString().trim().toUpperCase() + "%");
            }
            
            if (params.get("razonSelected") != null && !params.get("razonSelected").toString().trim().isEmpty()) {
                q.setParameter("basicaRazonNodone",   new BigDecimal(params.get("razonSelected").toString()));
            }
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();
            
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
        }
        
       /**
     * Busca el datalle del Flujo por el tipo del Flujo, estado
     * Proximo, tecnologia que tengan estado razonado.
     *
     * @author Victor Bocanegra 
     * @param tipoFlujoOt orden de trabajo
     * @param proximoEstado orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoProAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl proximoEstado, CmtBasicaMgl tecnologia) throws ApplicationException {
        List<CmtDetalleFlujoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByFlujoAndEstadoProAndTecRazon");
        query.setParameter("tipoFlujoOt", tipoFlujoOt);
        query.setParameter("proximoEstado", proximoEstado);
        query.setParameter("tecnologia", tecnologia);
        resultList = (List<CmtDetalleFlujoMgl>) query.getResultList();
        return resultList;
    }
    
        
       /**
     * Busca el datalle del Flujo por el tipo del Flujo, estado
     * Inicial, tecnologia que tengan estado razonado.
     *
     * @author Victor Bocanegra 
     * @param tipoFlujoOt orden de trabajo
     * @param estadoInicial orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIniAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInicial, CmtBasicaMgl tecnologia) throws ApplicationException {
        List<CmtDetalleFlujoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDetalleFlujoMgl.findByFlujoAndEstadoIniAndTecRazon");
        query.setParameter("tipoFlujoOt", tipoFlujoOt);
        query.setParameter("estadoInicial", estadoInicial);
        query.setParameter("tecnologia", tecnologia);
        resultList = (List<CmtDetalleFlujoMgl>) query.getResultList();
        return resultList;
    }
}
