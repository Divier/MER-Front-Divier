package co.com.claro.visitasTecnicas.business;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.dao.TmMasVtCarguemasivoDao;
import co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;



/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public class TmMasVtCarguemasivoManager {
  private static final Logger LOGGER = LogManager.getLogger(TmMasVtCarguemasivoManager.class);

  public TmMasVtCarguemasivoManager() {
  }
  
  public BigDecimal findMaxArchivo() {
    TmMasVtCarguemasivoDao dao=new TmMasVtCarguemasivoDao();
    return dao.findMaxArchivo();
  }
  
  public boolean find(TmMasVtCarguemasivo aux) {
    try {
      TmMasVtCarguemasivoDao dao=new TmMasVtCarguemasivoDao();
      TmMasVtCarguemasivo item=dao.find(aux.getIdSolicitud());
      return item!=null;
    } catch (ApplicationException e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
      throw new Error(e.getMessage());
    }
  }

  
  public boolean create(TmMasVtCarguemasivo aux){
    
    try {
    TmMasVtCarguemasivoDao dao=new TmMasVtCarguemasivoDao();
    dao.create(aux);
    return true;
    } catch (ApplicationException e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);      
      throw new Error(e.getMessage());
    }
  }

}
