/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoMgl;
import co.com.telmex.catastro.data.Geografico;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface GeograficoMglFacadeLocal extends BaseFacadeLocal<GeograficoMgl>{
    
  List<GeograficoMgl> findByIdGeograficoPolitico(BigDecimal idGeograficoPolitoco) 
          throws ApplicationException;

  public Geografico findBarrioHhpp(BigDecimal hhpId)throws ApplicationException;
    
}
