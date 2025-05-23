/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.Query;
/**
 *
 * @author Juan David Hernandez
 */
public class CmtReglaEstadoCmMglDaoImpl extends GenericDaoImpl <CmtReglaEstadoCmMgl>{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtReglaEstadoCmMglDaoImpl.class);
    
        /**
     * Obtiene el listado de numero de reglas existentes.
     * 
     * @author Juan David Hernandez
     * @return Listado de numeros de id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<String> findNumeroReglaList()
            throws ApplicationException {
        try {
            List<String> resulList;
            String sql = "select n.numeroRegla "
                    + " from CmtReglaEstadoCmMgl n WHERE n.estadoRegistro = 1 "
                    + " group by n.numeroRegla ";
            
            Query query = entityManager.createQuery(sql);
            resulList = query.getResultList();

            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    
    
            /**
     * Obtiene una regla por numero de regla.
     * 
     * @author Juan David Hernandez
     * @param numeroRegla
     * @return Listado de numeros de id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtReglaEstadoCmMgl> findReglaByNumeroRegla(String numeroRegla)
            throws ApplicationException {
        try {
            List<CmtReglaEstadoCmMgl> resulList;
            String sql = "select n "
                    + " from CmtReglaEstadoCmMgl n where n.numeroRegla = :numeroRegla "
                    + " AND n.estadoRegistro = 1";
            
            Query query = entityManager.createQuery(sql);
            query.setParameter("numeroRegla", numeroRegla);
            resulList = query.getResultList();

            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    

    /*
     * Angel Gonzalez, Estados combinados en MER de MGL_INSPIRA : 23814 
     * Migrando cambios de estados combinados
    */
    public List<CmtReglaEstadoCmMgl> findAllByState(int ruleState) throws ApplicationException {
        try {
            String sql = "SELECT n "
                    + " FROM CmtReglaEstadoCmMgl n "
                    + " WHERE n.estadoRegistro = ?1 "
                    + " ORDER BY  n.numeroRegla DESC ";
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, ruleState);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public int countRules(String tipoFiltro, String contentFiltro) throws ApplicationException {
        try {
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT COUNT(1) "
                    + " FROM CmtReglaEstadoCmMgl n  "
                    + " WHERE n.estadoRegistro = ?1 ");

            // ADICCION DE FILTROS
            if (tipoFiltro != null && !tipoFiltro.isEmpty()
                    && contentFiltro != null && !contentFiltro.isEmpty()) {
                switch (tipoFiltro) {
                    case "IDRULE":
                        querySql.append(" AND  n.numeroRegla = ?2 ");
                        break;
                    case "ESTNAME":
                        querySql.append(" AND  n.estadoTecBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;
                    case "TECNAME":
                        querySql.append(" AND  n.tecnologiaBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;
                    case "ESTCOMB":
                        querySql.append(" AND  n.estadoCmBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;

                }
            }
            querySql.append(" ORDER BY  n.numeroRegla DESC ");
            Query query = entityManager.createQuery(querySql.toString());
            query.setParameter(1, 1);
            if (contentFiltro != null && !contentFiltro.isEmpty()) {
                query.setParameter(2, contentFiltro);
            }
            int result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public String findMaxIdRule() throws ApplicationException {
        try {
            String sql = "SELECT MAX(n.numeroRegla) + 1 AS NUMERO_REGLA"
                    + " FROM CmtReglaEstadoCmMgl n ";
            Query query = entityManager.createQuery(sql);
            String resulList = query.getSingleResult() == null ? "1"
                    : ((String) query.getSingleResult().toString());
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    public Boolean disabledRule(String ruleNumber) throws ApplicationException {
        Boolean result = Boolean.FALSE;
        try {
            entityManager.getTransaction().begin();
            String sql = "UPDATE CmtReglaEstadoCmMgl n  "
                    + " SET n.estadoRegistro = 0 "
                    + " WHERE n.numeroRegla = ?1 ";
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, ruleNumber);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            result = Boolean.TRUE;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return result;
    }

    public List<CmtReglaEstadoCmMgl> findRulesPaginacion(int firstResult, int maxResults, int state, String tipoFiltro, String contentFiltro) throws ApplicationException {
        try {
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT n "
                    + " FROM CmtReglaEstadoCmMgl n "
                    + " WHERE n.estadoRegistro = ?1 ");
            // ADICCION DE FILTROS
            if (tipoFiltro != null && !tipoFiltro.isEmpty()
                    && contentFiltro != null && !contentFiltro.isEmpty()) {
                switch (tipoFiltro) {
                    case "IDRULE":
                        querySql.append(" AND  n.numeroRegla = ?2 ");
                        break;
                    case "ESTNAME":
                        querySql.append(" AND  n.estadoTecBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;
                    case "TECNAME":
                        querySql.append(" AND  n.tecnologiaBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;
                    case "ESTCOMB":
                        querySql.append(" AND  n.estadoCmBasicaId.nombreBasica LIKE CONCAT(CONCAT('%', ?2), '%') ");
                        break;

                }
            }
            querySql.append(" ORDER BY  n.numeroRegla DESC ");
            Query query = entityManager.createQuery(querySql.toString());
            query.setParameter(1, state);
            if (contentFiltro != null && !contentFiltro.isEmpty()) {
                query.setParameter(2, contentFiltro);
            }
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            List<CmtReglaEstadoCmMgl> result = query.getResultList();
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
