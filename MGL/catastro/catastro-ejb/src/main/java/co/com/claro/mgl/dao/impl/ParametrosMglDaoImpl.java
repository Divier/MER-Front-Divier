/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author User
 */
public class ParametrosMglDaoImpl extends GenericDaoImpl<ParametrosMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(ParametrosMglDaoImpl.class);
    
    public List<ParametrosMgl> findAll() throws ApplicationException {
      
       List<ParametrosMgl> resulList;
      try {
        TypedQuery<ParametrosMgl> query = entityManager.createNamedQuery("ParametrosMgl.findAll", ParametrosMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resulList =  query.getResultList();
        getEntityManager().clear();
        return resulList;
      } catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
        LOGGER.error(msg);
        throw new ApplicationException(e.getMessage(), e);
      }
    }

    public List<ParametrosMgl> findByAcronimo(String acronimo) throws ApplicationException {
        TypedQuery<ParametrosMgl> query = entityManager.createQuery("SELECT s FROM ParametrosMgl s WHERE UPPER(s.parAcronimo) like :parAcronimo  ORDER BY s.parAcronimo ASC  ",
                ParametrosMgl.class);
        query.setParameter("parAcronimo", "%" + acronimo.toUpperCase() + "%");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<ParametrosMgl> resultList = query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
   
    /**
     *  valbuenayf Metodo para buscar un parametro por el nombre
     * @param nombre
     * @return
     * @throws ApplicationException 
     */        
    public ParametrosMgl findByAcronimoName(String nombre) throws ApplicationException {
        ParametrosMgl result = null;
        if (nombre != null && !nombre.isEmpty()) {
            try {
                TypedQuery<ParametrosMgl> query = entityManager.createQuery(
                        "SELECT s FROM ParametrosMgl s WHERE UPPER(s.parAcronimo) = :parAcronimo  ORDER BY s.parAcronimo ASC  ",
                        ParametrosMgl.class);
                query.setParameter("parAcronimo", nombre.toUpperCase());
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
                result = query.getSingleResult();
                getEntityManager().clear();
            } catch (NoResultException e) {
                LOGGER.error("La consulta no obtuvo resultados: " + e.getMessage());
                return null;
            } catch (NonUniqueResultException e) {
                LOGGER.error("La consulta obtuvo más de un resultado: " + e.getMessage(), e);
                return null;
            } catch (Exception e) {
                LOGGER.error("Se produjo un error en la consulta: " + e.getMessage(), e);
                return null;
            }
        }
        return result;
  }
  
  /**
   * Buscar parametro
   * 
   * Se busca un ParametrosMgl que tenga el nombre parAcronimo
   * @author becerraarmr
   * @param parAcronimo nombre del acrónimo a buscar
   * @return null o el ParametrosMgl correspondiente
   * @throws ApplicationException is hay errores en la busqueda en la base de 
   * datos
   */
  public ParametrosMgl findParametroMgl(String parAcronimo) 
          throws ApplicationException {
    try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            TypedQuery<ParametrosMgl> query = entityManager.createNamedQuery("ParametrosMgl.findparAcronimo",
                    ParametrosMgl.class);
            query.setParameter("parAcronimo", parAcronimo);
            ParametrosMgl parametrosMgl=null;
            List<ParametrosMgl> list=query.getResultList();
            if(list.size()==1){
              return list.get(0);
            }
            entityManager.getTransaction().commit();
            return parametrosMgl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
  }
    /**
     * Buscar lista de parametros por tipo de parametro
     *
     * @author bocanegravm
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    public List<ParametrosMgl> findByTipoParametro(String tipoParam) throws ApplicationException {
        List<ParametrosMgl> resultList;
        TypedQuery<ParametrosMgl> query = entityManager.createQuery("SELECT s FROM ParametrosMgl s "
                + "WHERE s.parTipoParametro = :parTipo  ORDER BY s.parAcronimo ASC  ", ParametrosMgl.class);
        query.setParameter("parTipo", tipoParam);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = query.getResultList();
        return resultList;
    }
    
     /**
     * Buscar lista de parametros por tipo de parametro y acronimo
     *
     * @author bocanegravm
     * @param acronimo  a buscar
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    public List<ParametrosMgl> findByAcronimoAndTipoParam
        (String acronimo, String tipoParam) throws ApplicationException {
      List<ParametrosMgl> resultList;
      TypedQuery<ParametrosMgl> query = entityManager.createQuery("SELECT s FROM ParametrosMgl s "
              + " WHERE UPPER(s.parAcronimo) like :parAcronimo"
              + " AND s.parTipoParametro = :parTipo  ORDER BY s.parAcronimo ASC  ", ParametrosMgl.class);
      query.setParameter("parAcronimo", "%" + acronimo.toUpperCase() + "%");
      query.setParameter("parTipo", tipoParam);
      query.setHint("javax.persistence.cache.storeMode", "REFRESH");
      resultList = query.getResultList();
      return resultList;
    }

}
