package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;

/**
 * Clase que implementa el acceso a datos para la tabla
 * CMT_RELACION_ESTADO_CM_TIPO_GA.
 * 
* @author alejandro.martine.ext@claro.com.co
 * @versión 1.0
 */
public class CmtRelacionEstadoCmTipoGaMglDaoImpl extends GenericDaoImpl<CmtRelacionEstadoCmTipoGaMgl> {
    private static final Logger LOGGER = LogManager.getLogger(CmtRelacionEstadoCmTipoGaMglDaoImpl.class);

    /**
     * Consulta todos los registros.
     *
     * @return Lista de todos los datoss de la tabla.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findAll()
            throws ApplicationException {
        try {
            List<CmtRelacionEstadoCmTipoGaMgl> resultList;
            Query query =
                    entityManager.createNamedQuery("CmtRelacionEstadoCmTipoGaMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtRelacionEstadoCmTipoGaMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta resgistros por el codigo resgristrado por el usuario.
     *
     * @param recCodigo codigo del registro
     * @return El/los registros que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByCodigo(String recCodigo) throws ApplicationException {
        try {
            List<CmtRelacionEstadoCmTipoGaMgl> resultList;

            String queryStr =
                    "SELECT c FROM CmtRelacionEstadoCmTipoGaMgl c WHERE c.estadoRegistro=1 ";

            if (recCodigo != null && !recCodigo.isEmpty()) {
                queryStr = queryStr.concat(" AND c.codigo = :codigo");
            }

            Query query =
                    entityManager.createQuery(queryStr, CmtRelacionEstadoCmTipoGaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (recCodigo != null && !recCodigo.equals("")) {
                query.setParameter("codigo", recCodigo);
            }

            resultList = (List<CmtRelacionEstadoCmTipoGaMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta resgistros por el codigo y el estado de la cuenta matriz
     * resgristrado por el usuario
     *
     * @param recCodigo
     * @param estadoCm
     * @return El/los registros que contengan el codigo y estado de cuenta
     * matriz especificados.
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByCodigoEstadoCm(String recCodigo, CmtBasicaMgl estadoCm) throws ApplicationException {
        try {
            List<CmtRelacionEstadoCmTipoGaMgl> resultList;

            String queryStr =
                    "SELECT c FROM CmtRelacionEstadoCmTipoGaMgl c WHERE c.estadoRegistro=1 ";

            if (recCodigo != null && !recCodigo.equals("")) {
                queryStr = queryStr.concat(" AND c.codigo <> :codigo");
            }

            if (estadoCm != null && estadoCm.getBasicaId() != null) {
                queryStr = queryStr.concat(" AND c.basicaEstadoCMObj.basicaId = :estadoCM");
            }

            Query query =
                    entityManager.createQuery(queryStr, CmtRelacionEstadoCmTipoGaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (recCodigo != null && !recCodigo.equals("")) {
                query.setParameter("codigo", recCodigo);
            }

            if (estadoCm != null && estadoCm.getBasicaId() != null) {
                query.setParameter("estadoCM", estadoCm.getBasicaId());
            }

            resultList = (List<CmtRelacionEstadoCmTipoGaMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta resgistros por el estado de la cuenta matriz resgristrado por el
     * usuario
     *
     * @param estadoCmFiltro
     * @return El/los registros que contengan estado de cuenta matriz
     * especificados.
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByEstadoCm(CmtBasicaMgl estadoCmFiltro) throws ApplicationException {
        try {
            List<CmtRelacionEstadoCmTipoGaMgl> resultList;

            String queryStr =
                    "SELECT c FROM CmtRelacionEstadoCmTipoGaMgl c WHERE c.estadoRegistro=1 ";

            if (estadoCmFiltro != null && estadoCmFiltro.getBasicaId() != null) {
                queryStr = queryStr.concat(" AND c.basicaEstadoCMObj.basicaId = :estadoCM");
            }

            Query query =
                    entityManager.createQuery(queryStr, CmtRelacionEstadoCmTipoGaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (estadoCmFiltro != null && estadoCmFiltro.getBasicaId() != null) {
                query.setParameter("estadoCM", estadoCmFiltro.getBasicaId());
            }

            resultList = (List<CmtRelacionEstadoCmTipoGaMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Consulta los registros que cumplan con los criterios de busqueda
     * comprendidos por los parametros.
     *
     * @param codigoFiltro codigo del registro
     * @param estadoCmFiltro estado de cuenta matriz
     * @param tipoGaFiltro tipo de gestion avanzada
     * @param descripcionFiltro descripcion del registro
     * @param estadoFiltro estado del registro
     * @return
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByFiltro(String codigoFiltro,
            CmtBasicaMgl estadoCmFiltro,
            CmtBasicaMgl tipoGaFiltro,
            String descripcionFiltro,
            String estadoFiltro) throws ApplicationException {

        try {
            List<CmtRelacionEstadoCmTipoGaMgl> resultList;
            String queryStr = "SELECT c FROM CmtRelacionEstadoCmTipoGaMgl c WHERE c.estadoRegistro=1";

            if (codigoFiltro != null && !codigoFiltro.equals("")) {
                queryStr = queryStr.concat(" AND");
                queryStr = queryStr.concat(" c.codigo = :codigo");
            }

            if (estadoCmFiltro != null && estadoCmFiltro.getBasicaId() != null) {
                queryStr = queryStr.concat(" AND");
                queryStr = queryStr.concat(" c.basicaEstadoCMObj.basicaId = :estadoCM");
            }

            if (tipoGaFiltro != null && tipoGaFiltro.getBasicaId() != null) {
                queryStr = queryStr.concat(" AND");
                queryStr = queryStr.concat(" c.basicaTiipoGa.basicaId = :tipoGa");
            }

            if (descripcionFiltro != null && !descripcionFiltro.equals("")) {
                queryStr = queryStr.concat(" AND");
                queryStr =
                        queryStr.concat((" c.descripcion LIKE :descripcion"));
            }

            if (estadoFiltro != null) {
                queryStr = queryStr.concat(" AND");
                queryStr = queryStr.concat((" c.estado LIKE :estado"));
            }

            Query query =
                    entityManager.createQuery(queryStr, CmtRelacionEstadoCmTipoGaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (codigoFiltro != null && !codigoFiltro.equals("")) {
                query.setParameter("codigo", codigoFiltro);
            }
            if (estadoCmFiltro != null
                    && estadoCmFiltro.getBasicaId() != null) {
                query.setParameter("estadoCM", estadoCmFiltro.getBasicaId());
            }
            if (tipoGaFiltro != null && tipoGaFiltro.getBasicaId() != null) {
                query.setParameter("tipoGa", tipoGaFiltro.getBasicaId());
            }
            if (descripcionFiltro != null && !descripcionFiltro.equals("")) {
                query.setParameter("descripcion", "%" + descripcionFiltro + "%");
            }
            if (estadoFiltro != null) {
                query.setParameter("estado", estadoFiltro);
            }

            resultList =
                    (List<CmtRelacionEstadoCmTipoGaMgl>) query.getResultList();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
