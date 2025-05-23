package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.AtributoValorDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Parzifal de León
 */
public class ParametrosCallesDaoImpl extends GenericDaoImpl<ParametrosCalles> {

    public List<ParametrosCalles> findByTipo(String tipo) {
        TypedQuery<ParametrosCalles> q = entityManager.createQuery(
                "Select p from ParametrosCalles p where p.idTipo = :idTipo "
                        + "ORDER BY p.idParametro ASC", ParametrosCalles.class);
        q.setParameter("idTipo", tipo);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        List<ParametrosCalles> resultList = q.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<ParametrosCalles> findByIdParametro(String idParametro) {
        List<ParametrosCalles> resulList;
        TypedQuery<ParametrosCalles> q = entityManager.createQuery("SELECT p from ParametrosCalles p "
                + "WHERE p.idParametro = :idParametro ", ParametrosCalles.class);
        q.setParameter("idParametro", idParametro);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        resulList = q.getResultList();
        getEntityManager().clear();
        return resulList;
    }

    public List<ParametrosCalles> findByTipoPaginacion(int firstResult,
            int maxResults, String tipo) {
        TypedQuery<ParametrosCalles> q = entityManager.createQuery(
                "Select p from ParametrosCalles p where p.idTipo = :idTipo "
                + "ORDER BY p.idParametro ASC", ParametrosCalles.class);
        q.setParameter("idTipo", tipo);
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        List<ParametrosCalles> resulList = q.getResultList();
        getEntityManager().clear();
        return resulList;
    }

    public int countByTipo(String tipo) {
        int resultList;
        Query q = entityManager.createQuery(
                "Select Count (1) from ParametrosCalles p where p.idTipo = :idTipo "
                + "ORDER BY p.idParametro ASC");
        q.setParameter("idTipo", tipo);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        resultList = q.getSingleResult() == null ? 0
                : ((Long) q.getSingleResult()).intValue();
        return resultList;
    }


    public ParametrosCalles findByParametroId(String parametroId) {
        ParametrosCalles resultList;
        Query q = entityManager.createQuery(
                "Select p from ParametrosCalles p where p.idParametro = :idParametro "
                + "ORDER BY p.idParametro ASC");
        q.setParameter("idParametro", parametroId);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0 
        resultList = (ParametrosCalles) q.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    
     /**
   * Buscar los tipos parametros calle
   * 
   * Busca en la base de datos los tipos no repetidos de parametros calles
   * @author becerraarmr
   * @return listado encontrado
   * @throws ApplicationException  si hay problemas en la consulta en la base
   * de datos
   */
  public List<String> findAllGroupByTipo() 
          throws ApplicationException {
    List<String> resulList;  
    String sql="SELECT DISTINCT p.idTipo FROM ParametrosCalles p "
            + "ORDER BY p.idTipo";
    TypedQuery<String> q = entityManager.createQuery(sql, String.class);
    resulList = q.getResultList();
    getEntityManager().clear();
    return resulList;
  }
  
   /**
   * Buscar un listado
   * <p>
   * Busca un listado teniendo en cuenta el rango
   *
   * @author becerraarmr
   * @param range          rango de busqueda
   * @param valorAttribute atributo y valor para hacer la busqueda
   * @return listado de registros encontrados
   * @throws ApplicationException Error en la ejecución de la consulta.
   */
  public List<ParametrosCalles> findRangeByIdTipo(int[] range,
          AtributoValorDto valorAttribute)
          throws ApplicationException {
      
    List<ParametrosCalles> resulList;
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ParametrosCalles> cq = cb.createQuery(entityClass);
    Root<ParametrosCalles> c = cq.from(entityClass);
    cq.select(c).where(cb.equal(c.get(valorAttribute.getNombreAtributo()), 
              valorAttribute.getValorAtributo()));
    TypedQuery<ParametrosCalles> q = entityManager.createQuery(cq);
    q.setMaxResults(range[1] - range[0]+1);
    q.setFirstResult(range[0]);
    resulList = q.getResultList();
    getEntityManager().clear();
    return resulList;
  }

}
