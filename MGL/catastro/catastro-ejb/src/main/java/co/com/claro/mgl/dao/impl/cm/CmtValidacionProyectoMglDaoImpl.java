package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtValidacionProyectoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 * clase que implementa operaciones del DAO correpondiente a
 * CMT_VALIDACION_PROYECTO. 
 * Implementacion de las posibles operaciones del DAO.
 * 
 * @author ortizjaf
 * @versión 1.0 revision 16/05/2017.
 * @see GenericDaoImpl
 */
public class CmtValidacionProyectoMglDaoImpl extends GenericDaoImpl<CmtValidacionProyectoMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtValidacionProyectoMglDaoImpl.class);

    /**
     * Buscar todas las validaciones.
     * Busca todas las validaciones de los proyectos.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return La lista de validaciones.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtValidacionProyectoMgl> findAll() throws ApplicationException {
        List<CmtValidacionProyectoMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtValidacionProyectoMgl.findAll");
        resultList = (List<CmtValidacionProyectoMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Buscar la viabilidad.
     * Busca la viabilidad.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param basicaProyecto Proycto a buscar.
     * @param basica Validación a buscar.
     * @return Viabilidad coincidente con los parametros de busqueda.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
    public String findViabilidad(CmtBasicaMgl basicaProyecto, CmtBasicaMgl basica)
            throws ApplicationException {
        if (basicaProyecto == null || basica == null) {
            return null;
        }

        String sql = "select viabilidad "
                + "from "+Constant.MGL_DATABASE_SCHEMA+"."+ "cmt_validacion_proyecto "
                + "where basica_id_proyecto=" + basicaProyecto.getBasicaId() + " "
                + "and basica_id_validacion=" + basica.getBasicaId() + " "
                + "and estado_registro=1 and rownum=1 "
                + "order by validacion_proyecto_id desc";

        Query query = entityManager.createNativeQuery(sql);

        List<String> list = query.getResultList();
        String aux = null;
        if (!list.isEmpty()) {
            aux = list.get(0);
        }
        return aux;
    }

    /**
     * Buscar las validaciones por los filtros dados en los parametros.
     * Buscar las validaciones por los filtros proyecto y validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param proyecto Proyecto en el cual se busca la validacion.
     * @param validacion Validacion que se desea encontrar en el proyecto.
     * @return Lista de validaciones que coincidad con los parametros de busqueda.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public CmtValidacionProyectoMgl findByProyectoAndValidacion(
            CmtBasicaMgl proyecto, CmtBasicaMgl validacion)
            throws ApplicationException {
        try {
            CmtValidacionProyectoMgl result;
            Query query = entityManager.createNamedQuery(
                    "CmtValidacionProyectoMgl.findByProyectoAndValidacion");
            query.setParameter("proyecto", proyecto);
            query.setParameter("validacion", validacion);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            result = (CmtValidacionProyectoMgl) query.getSingleResult();

            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
      * Buscar la validación del proyecto por paginación.Busca la validación del proyecto por paginación.
      * 
      * @author Jhoimer Rios.
      * @version 1.0 revision 16/05/2017.
     * @param firstResult
      * @param maxResults Número máximo de registros a mostrar.
      * @param proyecto Proyecto en el cual se buscan las validaciones.
      * @return Lista de validaciones coincidentes con los criterios de búsqueda.
      * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
      * a ejecución de la sentencia.
      */
    public List<CmtValidacionProyectoMgl> findByFiltroPaginado(
            int firstResult, int maxResults, CmtBasicaMgl proyecto)
            throws ApplicationException {

        try {
            List<CmtValidacionProyectoMgl> resultList;
            String queryStr = "SELECT c FROM CmtValidacionProyectoMgl c WHERE "
                    + "c.estadoRegistro = 1 ";
            if (proyecto != null && proyecto.getBasicaId() != null) {
                queryStr += " AND c.tipoBasicaProyectoId = :proyecto ";
            }
            queryStr += " ORDER BY c.tipoBasicaValidacionId.tipoBasicaObj.nombreTipo ASC"
                    + ", c.tipoBasicaValidacionId.nombreBasica ASC ";
            Query query = entityManager.createQuery(queryStr);

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (proyecto != null && proyecto.getBasicaId() != null) {
                query.setParameter("proyecto", proyecto);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtValidacionProyectoMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Obtener la cantidad de registros.
     * Obtiener la cantidad de registros de validacion por el 
     * proyecto dado.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param proyecto Poryecto al que se consulta las validaciones asociadas.
     * @return Cantidad de registos existentes de la consulta.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en a ejecución de la sentencia.
     */
    public int countByFiltroPaginado(CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            int result = 0;
            String queryStr = "SELECT COUNT(1) FROM CmtValidacionProyectoMgl c "
                    + "WHERE c.estadoRegistro = 1 ";
            if (proyecto != null && proyecto.getBasicaId() != null) {
                queryStr += " AND c.tipoBasicaProyectoId = :proyecto ";
            }
            Query query = entityManager.createQuery(queryStr);

            if (proyecto != null && proyecto.getBasicaId() != null) {
                query.setParameter("proyecto", proyecto);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getSingleResult() == null
                    ? 0 : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception e) {
             String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}