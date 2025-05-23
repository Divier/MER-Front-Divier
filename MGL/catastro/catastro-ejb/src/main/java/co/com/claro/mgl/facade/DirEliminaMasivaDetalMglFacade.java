/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DirEliminaMasivaDetalMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class DirEliminaMasivaDetalMglFacade implements DirEliminaMasivaDetalMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(DirEliminaMasivaDetalMglFacade.class);

    @Override
    public List<DirEliminaMasivaDetalMgl> findAll() throws ApplicationException {
        DirEliminaMasivaDetalMglManager dirEliminaMasivaDetalMglManager = new DirEliminaMasivaDetalMglManager();
        return dirEliminaMasivaDetalMglManager.findAll();
    }

    @Override
    public DirEliminaMasivaDetalMgl create(DirEliminaMasivaDetalMgl dirEliminaMasivaDetalMgl) throws ApplicationException {
        DirEliminaMasivaDetalMglManager dirEliminaMasivaDetalMglManager = new DirEliminaMasivaDetalMglManager();
        try {
            return dirEliminaMasivaDetalMglManager.create(dirEliminaMasivaDetalMgl);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public DirEliminaMasivaDetalMgl update(DirEliminaMasivaDetalMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(DirEliminaMasivaDetalMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DirEliminaMasivaDetalMgl findById(DirEliminaMasivaDetalMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DirEliminaMasivaDetalMgl> findByLemId(BigDecimal lemId) throws ApplicationException {
        DirEliminaMasivaDetalMglManager dirEliminaMasivaDetalMglManager = new DirEliminaMasivaDetalMglManager();
        return dirEliminaMasivaDetalMglManager.findByLemId(lemId);
    }
}
