
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RrCiudadesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class RrCiudadesFacade implements RrCiudadesFacadeLocal{

    private static final Logger LOGGER = LogManager.getLogger(RrCiudadesFacade.class);
    private final RrCiudadesManager rrCiudadesManager;

    public RrCiudadesFacade() {
        this.rrCiudadesManager = new RrCiudadesManager();
    }
    
    @Override
    public List<RrCiudades> findByCodregional(String codregional) throws ApplicationException {
        try {
            return rrCiudadesManager.findByCodregional(codregional);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.
                    getCurrentMethodName(this.getClass()) + "': " + e.
                    getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    @Override
    public List<RrCiudades> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrCiudades create(RrCiudades t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrCiudades update(RrCiudades t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(RrCiudades t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RrCiudades findById(RrCiudades sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public RrCiudades findById(String id) throws ApplicationException {
        return rrCiudadesManager.findById(id);        
    }
    
    @Override
    public RrCiudades findNombreCiudadByCodigo(String codigo) throws ApplicationException {
        return rrCiudadesManager.findNombreCiudadByCodigo(codigo);
    }
    
}
