/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DirEliminaMasivaDetalMglFacadeLocal extends BaseFacadeLocal<DirEliminaMasivaDetalMgl> {

    List<DirEliminaMasivaDetalMgl> findByLemId(BigDecimal bigDecimal) throws ApplicationException;

}
