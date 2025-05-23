
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RrRegionalesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class RrRegionalesFacade implements RrRegionalesFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(RrRegionalesFacade.class);
    private final RrRegionalesManager rrRegionalesManager;

    public RrRegionalesFacade() {
        this.rrRegionalesManager = new RrRegionalesManager();
    }

    @Override
    public List<CmtRegionalRr> findByUnibi(String unibi) throws ApplicationException {
        try {
            return rrRegionalesManager.findByUnibi(unibi);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public List<RrRegionales> findByUniAndBi() throws ApplicationException {
        try {
            return rrRegionalesManager.findByUniAndBi();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public List<CmtRegionalRr> findRegionales() throws ApplicationException {
        try {
            return rrRegionalesManager.findRegionales();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public List<RrRegionales> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrRegionales create(RrRegionales t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrRegionales update(RrRegionales t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RrRegionales t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrRegionales findById(RrRegionales sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrRegionales findById(String id) throws ApplicationException {
        return rrRegionalesManager.findById(id);
    }
    
    @Override
    public String findNombreRegionalByCodigo(String codigo) throws ApplicationException {
        return rrRegionalesManager.findNombreRegionalByCodigo(codigo);
    }
    
     @Override
      public List<CmtRegionalRr> findByCodigo(List<BigDecimal> codigo) throws ApplicationException{
          return rrRegionalesManager.findByCodigo(codigo);
      }
}
