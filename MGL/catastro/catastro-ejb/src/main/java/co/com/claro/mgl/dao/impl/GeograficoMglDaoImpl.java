/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.data.Geografico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class GeograficoMglDaoImpl extends GenericDaoImpl<GeograficoMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(GeograficoMglDaoImpl.class);

  public List<GeograficoMgl> findByIdGeograficoPolitico(BigDecimal gpoId) throws ApplicationException {
    List<GeograficoMgl> resultList;
    Query query = entityManager.createNamedQuery("GeograficoMgl.findByIdGeograficoPolitico");
    query.setParameter("gpoId", gpoId);
    query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
    resultList = (List<GeograficoMgl>) query.getResultList();
    return resultList;
  }

  
  public Geografico findBarrioHhpp(BigDecimal hhpId) 
          throws ApplicationException {
    try {
      if (!entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().begin();
      }
      entityManager.clear();
      entityManager.flush();
      String sqlHhpp = "select geo.* "
              + " from ((("+Constant.MGL_DATABASE_SCHEMA+"."+ "tec_tecnologia_habilitada h inner join "+Constant.MGL_DATABASE_SCHEMA+"."+ "mgl_direccion d on h.direccion_id = d.direccion_id) "
              + " inner join "+Constant.MGL_DATABASE_SCHEMA+"."+ "mgl_ubicacion u on d.ubicacion_id = u.ubicacion_id)"
              + " inner join "+Constant.MGL_DATABASE_SCHEMA+"."+ "mgl_geografico geo on geo.geografico_id = u.geografico_id) "
              + " where h.tecnologia_habilitada_id = ?";
      Query query = entityManager.createNativeQuery(sqlHhpp);
      query.setParameter(1, hhpId+"");
      List<Object[]> lista = query.getResultList();
      Geografico aux=new Geografico();
      if(!lista.isEmpty()){
        Object[] aux1=lista.get(0);
        aux.setGeoId(new BigDecimal(aux1[0]+""));
        aux.setGeoNombre(aux1[4]+"");
      }

      entityManager.getTransaction().commit();
      return aux;
    } catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
        LOGGER.error(msg);      
      throw new ApplicationException(msg, e);
    }
  }

}
