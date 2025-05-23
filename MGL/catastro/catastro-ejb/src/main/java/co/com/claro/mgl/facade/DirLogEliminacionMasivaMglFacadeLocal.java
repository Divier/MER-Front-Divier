/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DirLogEliminacionMasivaMglFacadeLocal extends BaseFacadeLocal<DirLogEliminacionMasivaMgl> {

    public DirLogEliminacionMasivaMgl findDirLogEliminacionMasivaMglDaoImplByDate(Date fecha);

    public List<DirLogEliminacionMasivaMgl> findByBetweenDate(Date fechaInicial, Date fechaFinal) throws ApplicationException;
}
