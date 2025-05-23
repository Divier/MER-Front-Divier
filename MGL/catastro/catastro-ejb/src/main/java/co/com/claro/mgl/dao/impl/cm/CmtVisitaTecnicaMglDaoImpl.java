package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Clase que implementa el acceso a datos para la tabla CMT_VISITATECNICA
 *
 * @author alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
public class CmtVisitaTecnicaMglDaoImpl extends GenericDaoImpl<CmtVisitaTecnicaMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtVisitaTecnicaMglDaoImpl.class);

    /**
     * Consulta todos los registros.
     *
     * @return Lista de todos los datoss de la tabla.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtVisitaTecnicaMgl> findAll() throws ApplicationException {
        try {
            List<CmtVisitaTecnicaMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtVisitaTecnicaMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtVisitaTecnicaMgl>) query.getResultList();
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
     * Consulta resgistros por el codigo resgristrado por el usuario.
     *
     * @param idVt identificador unico del registro
     * @return El/los registros que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public List<CmtVisitaTecnicaMgl> findByIdVt(BigDecimal idVt) throws ApplicationException {
        try {
            List<CmtVisitaTecnicaMgl> resultList;

            String queryStr =
                    "SELECT v FROM CmtVisitaTecnicaMgl v WHERE v.idVt = :idVt";

            Query query =
                    entityManager.createQuery(queryStr, CmtVisitaTecnicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");

            if (idVt != null && !idVt.equals("")) {
                query.setParameter("idVt", idVt);
            }

            resultList = (List<CmtVisitaTecnicaMgl>) query.getResultList();
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
     * Consulta resgistros por el codigo resgristrado por el usuario.
     *
     * @param cmtVisitaTecnicaMgl
     * @return Elregistro que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl findByIdVt(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl) throws ApplicationException {
        CmtVisitaTecnicaMgl result = null;
        if (cmtVisitaTecnicaMgl != null) {
            try {
                String queryStr =
                        "SELECT v FROM CmtVisitaTecnicaMgl v WHERE v = :idVt";

                Query query =
                        entityManager.createQuery(queryStr, CmtVisitaTecnicaMgl.class);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");


                    query.setParameter("idVt", cmtVisitaTecnicaMgl);


                result = (CmtVisitaTecnicaMgl) query.getSingleResult();

            } catch (NoResultException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
                LOGGER.error(msg);
                // retorna una VT en blanco, ya que no fue encontrada.
                result = new CmtVisitaTecnicaMgl();
                result.setIdVt(cmtVisitaTecnicaMgl.getIdVt());
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(msg, e);
            }
        }
        
        return result;
    }
    
    /**
     * Consulta resgistros por el numero de Ot a la que se encuentra asociada la
     * visita tecnica.
     *
     * @param idOt codigo del registro
     * @return El/los registros que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public List<CmtVisitaTecnicaMgl> findByIdOt(CmtOrdenTrabajoMgl idOt) throws ApplicationException {
        try {
            List<CmtVisitaTecnicaMgl> resultList;

            String queryStr = "SELECT v FROM CmtVisitaTecnicaMgl v WHERE v.otObj = :otObj";

            Query query = entityManager.createQuery(queryStr, CmtVisitaTecnicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("otObj", idOt);
            resultList = (List<CmtVisitaTecnicaMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public CmtVisitaTecnicaMgl findVtbyIdOt(BigDecimal idOt) throws ApplicationException {
        try {
            CmtVisitaTecnicaMgl result;
            Query query = entityManager.createNamedQuery("CmtVisitaTecnicaMgl.findVtByOt");
            query.setParameter("otObj", idOt);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = (CmtVisitaTecnicaMgl) query.getSingleResult();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);           
            if (e instanceof NoResultException) {
                return null;
            } else {
                throw new ApplicationException(msg,e);
            }
        }
    }

    /**
     * Buscar Visitas tecnicas activas de una derminada OT
     *
     * @param ordenTrabajo orden de trabajo como condicional de la busqueda
     * @return Retorna el objeto activo de una determinada ot Visitas tegnicas
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl findVTActiveByIdOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        try {
            CmtVisitaTecnicaMgl resultado = null;

            String queryStr = "SELECT v FROM CmtVisitaTecnicaMgl v WHERE  "
                    + " v.otObj = :otObj and v.estadoVisitaTecnica = 1  "
                    + " order by v.idVt desc";
            
            Query query = entityManager.createQuery(queryStr, CmtVisitaTecnicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("otObj", ordenTrabajo);
            resultado = (CmtVisitaTecnicaMgl) query.getSingleResult();
            return resultado;
        } catch (NoResultException ex) {            
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
        /**
     * Consulta vt por un id especifico de vt.
     *
     * @param idVt
     * @return Elregistro que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl findVtById(BigDecimal idVt) throws ApplicationException {
        try {
            CmtVisitaTecnicaMgl resultList;

            String queryStr =
                    "SELECT v FROM CmtVisitaTecnicaMgl v WHERE v.idVt = :idVt and  v.estadoRegistro = 1";

            Query query =
                    entityManager.createQuery(queryStr, CmtVisitaTecnicaMgl.class);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");


            query.setParameter("idVt", idVt);


            resultList = (CmtVisitaTecnicaMgl) query.getSingleResult();
            return resultList;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
}
