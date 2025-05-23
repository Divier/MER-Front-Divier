package co.com.claro.mgl.facade;


import co.com.claro.mgl.businessmanager.address.ParametrosCallesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class ParametrosCallesFacade implements ParametrosCallesFacadeLocal{
    
    private final ParametrosCallesManager parametrosCallesManager;
    private static final Logger LOGGER = LogManager.getLogger(ParametrosCallesFacade.class);

    public ParametrosCallesFacade() {
        this.parametrosCallesManager = new ParametrosCallesManager();
    }

    @Override
    public List<ParametrosCalles> findByTipo(String tipo) throws ApplicationException {
        try {
            List<ParametrosCalles> listaNivel5 = parametrosCallesManager.findByTipo(tipo);
               Collections.sort(listaNivel5, new Comparator<ParametrosCalles>() {
            @Override
            public int compare(ParametrosCalles obj1, ParametrosCalles obj2) {
                return obj1.getDescripcion().compareTo(obj2.getDescripcion());
            }
        });
            return listaNivel5;
            
         
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public List<ParametrosCalles> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParametrosCalles create(ParametrosCalles t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParametrosCalles update(ParametrosCalles t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(ParametrosCalles t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParametrosCalles findById(ParametrosCalles sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<ParametrosCalles> findByTipoPaginacion(int page, int maxResult, String tipo) throws ApplicationException {
        try {
            return parametrosCallesManager.findByTipoPaginacion(page, maxResult, tipo);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public int countByTipo(String tipo) throws ApplicationException {
        try {
            return parametrosCallesManager.countByTipo(tipo);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }
    
    /**
   * Contar según un atributo
   * 
   * Contar registros encontrados según el filtro de un atributo
   * @author becerraarmr
   * 
   * @param valor para realizar el conteo
   * @return un valor entero
   * @throws co.com.claro.mgl.error.ApplicationException Si hay error en la 
   * consulta
   */
    public int countByIdTipo(String valor)throws  ApplicationException{
    return parametrosCallesManager.countByIdTipo(valor);
  }

  /**
   * Buscar registros por rango y un atributo
   * 
   * Busca en la base de datos los registros que cumplan las condiciones
   * del rango y del atributo.
   * 
   * @param range rango de busqueda
   * @param valorAtribute valor del atriburo id parametro
   * @return un listado con lo encontrado
   * @throws ApplicationException si hay errores la realizar la busqueda
   */
  @Override
  public List<ParametrosCalles> findRangeByIdTipo(int[] range, 
          String valorAtribute)throws ApplicationException {
    return parametrosCallesManager.findRangeByIdTipo(range,valorAtribute);
  }

  /**
   *
   * @return
   * @throws ApplicationException
   */
  @Override
  public List<String> findAllGroupByTipo() 
          throws ApplicationException {
    return parametrosCallesManager.findAllGroupByTipo();
  }

  @Override
  public List<ParametrosCalles> findRange(int[] i) {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ParametrosCalles edit(ParametrosCalles current) {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean remove(ParametrosCalles current) {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ParametrosCalles find(String id) throws ApplicationException {
    return parametrosCallesManager.find(id);
  }
    }
