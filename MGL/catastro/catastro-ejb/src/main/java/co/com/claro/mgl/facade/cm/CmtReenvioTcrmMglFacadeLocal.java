/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.jpa.entities.cm.CmtReenvioTcrmMgl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author valbuenayf
 */
@Local
public interface CmtReenvioTcrmMglFacadeLocal {

    public List<CmtReenvioTcrmMgl> findAllCmtReenvioTcrmMgl();

    public CmtReenvioTcrmMgl crearCmtReenvioTcrmMgl(CmtReenvioTcrmMgl cmtReenvioTcrmMgl);
}
