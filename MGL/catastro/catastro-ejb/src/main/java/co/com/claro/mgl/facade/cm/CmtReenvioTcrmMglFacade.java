/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtReenvioTcrmMglManager;
import co.com.claro.mgl.jpa.entities.cm.CmtReenvioTcrmMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author valbuenayf
 */
@Stateless
public class CmtReenvioTcrmMglFacade implements CmtReenvioTcrmMglFacadeLocal {

    @Override
    public List<CmtReenvioTcrmMgl> findAllCmtReenvioTcrmMgl() {
        CmtReenvioTcrmMglManager cmtReenvioTcrmMglManager = new CmtReenvioTcrmMglManager();
        return cmtReenvioTcrmMglManager.findAllCmtReenvioTcrmMgl();
    }

    @Override
    public CmtReenvioTcrmMgl crearCmtReenvioTcrmMgl(CmtReenvioTcrmMgl cmtReenvioTcrmMgl) {
        CmtReenvioTcrmMglManager cmtReenvioTcrmMglManager = new CmtReenvioTcrmMglManager();
        return cmtReenvioTcrmMglManager.crearCmtReenvioTcrmMgl(cmtReenvioTcrmMgl);
    }
}
