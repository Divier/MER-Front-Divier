/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DirLogEliminacionMasivaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class DirLogEliminacionMasivaMglFacade implements DirLogEliminacionMasivaMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(DirLogEliminacionMasivaMglFacade.class);

    @Override
    public List<DirLogEliminacionMasivaMgl> findAll() throws ApplicationException {
        DirLogEliminacionMasivaMglManager dirLogEliminacionMasivaMglManager = new DirLogEliminacionMasivaMglManager();
        return dirLogEliminacionMasivaMglManager.findAll();
    }

    @Override
    public DirLogEliminacionMasivaMgl findDirLogEliminacionMasivaMglDaoImplByDate(Date fecha) {
        DirLogEliminacionMasivaMglManager dirLogEliminacionMasivaMglManager = new DirLogEliminacionMasivaMglManager();
        try {
            return dirLogEliminacionMasivaMglManager.findDirLogEliminacionMasivaMglDaoImplByDate(fecha);
        } catch (ApplicationException e) {
            LOGGER.error("Error en findDirLogEliminacionMasivaMglDaoImplByDate. " +e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en findDirLogEliminacionMasivaMglDaoImplByDate. " +e.getMessage(), e);  
        }
        return null;
    }

    @Override
    public DirLogEliminacionMasivaMgl create(DirLogEliminacionMasivaMgl logEliminacionMasiva) throws ApplicationException {
        DirLogEliminacionMasivaMglManager dirLogEliminacionMasivaMglManager = new DirLogEliminacionMasivaMglManager();
        return dirLogEliminacionMasivaMglManager.create(logEliminacionMasiva);
    }

    @Override
    public DirLogEliminacionMasivaMgl update(DirLogEliminacionMasivaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(DirLogEliminacionMasivaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DirLogEliminacionMasivaMgl findById(DirLogEliminacionMasivaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DirLogEliminacionMasivaMgl> findByBetweenDate(Date fechaInicial, Date fechaFinal) throws ApplicationException {
         DirLogEliminacionMasivaMglManager dirLogEliminacionMasivaMglManager = new DirLogEliminacionMasivaMglManager();
        return dirLogEliminacionMasivaMglManager.findByBetweenDate(fechaInicial, fechaFinal);
    }
}
