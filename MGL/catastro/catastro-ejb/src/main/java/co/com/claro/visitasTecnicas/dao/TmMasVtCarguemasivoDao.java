package co.com.claro.visitasTecnicas.dao;

import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.persistence.Query;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public class TmMasVtCarguemasivoDao extends GenericDaoImpl<TmMasVtCarguemasivo>{
  
  private static final Logger LOGGER = LogManager.getLogger(TmMasVtCarguemasivoDao.class);

  public TmMasVtCarguemasivoDao() {
  }
  

  public BigDecimal findMaxArchivo() {
    BigDecimal valor=null;
    try {
    String sql = "select (CASE WHEN max (cast (archivo as number)) IS NULL "
            + "THEN '0' ELSE  to_char(max(cast (archivo as number))) END) "
            + "AS MAX_ARCHIVO from TM_MAS_VT_CARGUEMASIVO";
    Query query=entityManager.createNativeQuery(sql);
    
    String stringValor=(String)query.getSingleResult();
    if(stringValor!=null){
      valor=new BigDecimal(stringValor);
    }
    } catch (Exception e) {
        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
        LOGGER.error(msg);
    }
    return valor;
  }
   

}
