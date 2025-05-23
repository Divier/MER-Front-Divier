/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.DirLogEliminacionMasivaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DirLogEliminacionMasivaMglManager {

    public List<DirLogEliminacionMasivaMgl> findAll() throws ApplicationException {

        List<DirLogEliminacionMasivaMgl> resulList;
        DirLogEliminacionMasivaMglDaoImpl dirLogEliminacionMasivaMglDaoImpl = new DirLogEliminacionMasivaMglDaoImpl();

        resulList = dirLogEliminacionMasivaMglDaoImpl.findAll();

        return resulList;
    }

    public DirLogEliminacionMasivaMgl create(DirLogEliminacionMasivaMgl logEliminacionMasiva) throws ApplicationException {
        DirLogEliminacionMasivaMglDaoImpl dirLogEliminacionMasivaMglDaoImpl = new DirLogEliminacionMasivaMglDaoImpl();
        return dirLogEliminacionMasivaMglDaoImpl.create(logEliminacionMasiva);

    }

    public DirLogEliminacionMasivaMgl findDirLogEliminacionMasivaMglDaoImplByDate(Date fecha) throws ApplicationException {
        DirLogEliminacionMasivaMglDaoImpl dirLogEliminacionMasivaMglDaoImpl = new DirLogEliminacionMasivaMglDaoImpl();
        DirLogEliminacionMasivaMgl result;
        result = dirLogEliminacionMasivaMglDaoImpl.findDirLogEliminacionMasivaMglDaoImplByDate(fecha);
        return result;


    }

    public List<DirLogEliminacionMasivaMgl> findByBetweenDate(Date fechaInicial, Date fechaFinal) throws ApplicationException {
        DirLogEliminacionMasivaMglDaoImpl dirLogEliminacionMasivaMglDaoImpl = new DirLogEliminacionMasivaMglDaoImpl();
        List<DirLogEliminacionMasivaMgl> result;
        result = dirLogEliminacionMasivaMglDaoImpl.findByBetweenDate(fechaInicial, fechaFinal);
        return result;
    }
}
