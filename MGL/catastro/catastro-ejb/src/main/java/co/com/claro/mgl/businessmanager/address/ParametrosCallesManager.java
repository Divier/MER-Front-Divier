/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ParametrosCallesDaoImpl;
import co.com.claro.mgl.dtos.AtributoValorDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 *
 * @author Parzifal de León
 *
 */
public class ParametrosCallesManager {
  /**
   * Log de seguimiento
   */
  private static final Logger LOGGER = LogManager.getLogger(ParametrosCallesManager.class);
  private static final String ERROR_EN = "Error en ";


    public List<ParametrosCalles> findByTipo(String tipo) {
        ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
        return daoImpl.findByTipo(tipo);
    }

    public List<ParametrosCalles> findByIdParametro(String idParametro) {
        ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
        return daoImpl.findByIdParametro(idParametro);
    }
    
      public List<ParametrosCalles> findByTipoPaginacion(int page, int maxResults, String tipo) {
        ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
       
        int firstResult = 0;
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        return daoImpl.findByTipoPaginacion(firstResult, maxResults, tipo);
    }
      
    public int countByTipo(String tipo) {
        ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
        return daoImpl.countByTipo(tipo);
    }
    public ParametrosCalles findByParametroId(String parametroId) {
        ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
        return daoImpl.findByParametroId(parametroId);
    }
	
	
    /**
   * Contar por parámetro
   * <p>
   * Cuenta según el parámetro.
   *
   * @author becerraarmr
   *
   * @param valor valor con el que se va a filtrar
   *
   * @return un entero
   *
   * @throws ApplicationException Erro presentado.
   */
  public int countByIdTipo(String valor)
          throws ApplicationException {
    ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
    if (valor != null) {
      AtributoValorDto item
              = new AtributoValorDto("idTipo", valor);
      return daoImpl.count(item);
    }
    return 0;
  }
  
  /**
   * Buscar según el rango y le atributo 
   * 
   * Busca los registros según el rango y el valor del atributo idParametro
   * @author becerraarmr
   * @param range rango de busqueda
   * @param valorAtribute valor del atributo idparametro para realizar la
   * busqueda
   * @return el listado de registros encontrados.
   * @throws ApplicationException si hay un error en la busqueda.
   */
  public List<ParametrosCalles> findRangeByIdTipo(int[] range,
          String valorAtribute) throws ApplicationException {
    ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
    try {
      AtributoValorDto item
              = new AtributoValorDto("idTipo", valorAtribute);
      return daoImpl.findRangeByIdTipo(range, item);
    } catch (ApplicationException e) {
      String msgError = ERROR_EN + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + e.getMessage();
      LOGGER.error(msgError);
      throw new ApplicationException(e);
    }
  }
  
  public List<String> findAllGroupByTipo() throws ApplicationException {
    ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
    try {
      return daoImpl.findAllGroupByTipo();
    } catch (ApplicationException e) {
      String msgError = ERROR_EN + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + e.getMessage();
      LOGGER.error(msgError);
      throw new ApplicationException(e);
    }
  }
  
  public ParametrosCalles find(String id) throws ApplicationException {
    ParametrosCallesDaoImpl daoImpl = new ParametrosCallesDaoImpl();
    try {
      return daoImpl.find(id);
    } catch (ApplicationException e) {
      String msgError = ERROR_EN + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + e.getMessage();
      LOGGER.error(msgError);
      throw new ApplicationException(e);
    }
  }
}
