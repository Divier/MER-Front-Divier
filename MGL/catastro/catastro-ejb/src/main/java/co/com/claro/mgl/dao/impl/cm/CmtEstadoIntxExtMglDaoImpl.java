/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Dao Relacion de Estados Internos y Externos. Contiene la logica de acceso a
 * datos de la relacion de estados internos y externos en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtEstadoIntxExtMglDaoImpl extends GenericDaoImpl<CmtEstadoIntxExtMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtEstadoIntxExtMglDaoImpl.class);

    /**
     * Obtiene la relacion estado interno externo.Obtiene la relacion estado
 interno por estado externo por medio del estado interno.
     *
     * @author Johnnatan Ortiz
     * @param estadoInterno Estado Interno
     * @return Relacion de estado Interno y Externo
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtEstadoIntxExtMgl findByEstadoInterno(CmtBasicaMgl estadoInterno) throws ApplicationException {
        try {
            CmtEstadoIntxExtMgl result;
            Query query = entityManager.createNamedQuery("CmtEstadoIntxExtMgl.findByEstadoInt");
            query.setParameter("estadoInterno", estadoInterno);
            result = (CmtEstadoIntxExtMgl) query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            LOGGER.error("Error al momento de buscar el estado interno. EX000 " + e.getMessage());
            return null;
        } catch (NonUniqueResultException e) {
            String msg = "La consulta del estado interno arrojó más de un resultado. EX000 " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            LOGGER.error("Error al momento de buscar el estado interno. EX000 " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }  
       
    
    public CmtEstadoIntxExtMgl findByEstadoInternoXExterno(BigDecimal estadoInterno,
            BigDecimal estadoExterno, int estado) throws ApplicationException {

        try {
            CmtEstadoIntxExtMgl result = null;
            StringBuilder querySql = new StringBuilder();

            querySql.append("SELECT c FROM CmtEstadoIntxExtMgl c where c.idEstadoInt.basicaId =:estadoInterno ");
            if (estadoExterno != null) {
                querySql.append("AND c.idEstadoExt.basicaId =:estadoExterno AND c.estadoRegistro =:estado");
            } else {
                querySql.append("AND c.estadoRegistro =:estado ");

            }
            Query query = entityManager.createQuery(querySql.toString());

            query.setParameter("estado", estado);
            query.setParameter("estadoInterno",estadoInterno);
            if (estadoExterno != null) {
                query.setParameter("estadoExterno", estadoExterno);
            }

            result = (CmtEstadoIntxExtMgl) query.getSingleResult();

            return result;

        } catch (NoResultException e) {
            LOGGER.error("Error al momento de consultar el estado interno.externo. EX000: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el estado interno.externo. EX000: " + e.getMessage(), e);
            return null;
        }
    }
    
    public int countEstadoInternoExterno() throws ApplicationException {

        try {
            String sql = "SELECT Count (1) FROM CmtEstadoIntxExtMgl c where c.estadoRegistro =:estado ";
            Query query = entityManager.createQuery(sql);
            query.setParameter("estado", 1);
            int result = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (NoResultException e) {
            LOGGER.error("Error al momento de consultar el conteo de estados interno.externo. EX000: " + e.getMessage(), e);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el conteo de estados interno.externo. EX000: " + e.getMessage(), e);
            return 0;
        }
    }

    public List<CmtEstadoIntxExtMgl> findAllEstadoInternoExternoList(int firstResult, int maxResults) throws ApplicationException {

        try {
            List<CmtEstadoIntxExtMgl> resulList;
            String sql = "SELECT c FROM CmtEstadoIntxExtMgl c "
                    + "where c.estadoRegistro =:estado order by c.idEstadoInt.nombreBasica ASC ";
            Query query = entityManager.createQuery(sql);

            query.setParameter("estado", 1);
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);

            resulList = query.getResultList();

            return resulList;

        } catch (NoResultException e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        }
    }
    
        public List<CmtEstadoIntxExtMgl> findByEstadoInternoList(int firstResult, int maxResults) throws ApplicationException {

        try {
            List<CmtEstadoIntxExtMgl> resulList;
            String sql = "SELECT c FROM CmtEstadoIntxExtMgl c "
                    + "where c.estadoRegistro =:estado order by c.fechaCreacion DESC ";
            Query query = entityManager.createQuery(sql);

            query.setParameter("estado", 1);
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);

            resulList = query.getResultList();

            return resulList;

        } catch (NoResultException e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        }
    }
        
    public List<CmtEstadoIntxExtMgl> findAll() throws ApplicationException {
       try {
            List<CmtEstadoIntxExtMgl> resulList;
            String sql = "SELECT c FROM CmtEstadoIntxExtMgl c "
                    + "where c.estadoRegistro = 1 ";
            Query query = entityManager.createQuery(sql);
            resulList = query.getResultList();
            return resulList;

        } catch (NoResultException e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar los estados interno-externo. EX000: " + e.getMessage(), e);
            return null;
        }
    }
}
