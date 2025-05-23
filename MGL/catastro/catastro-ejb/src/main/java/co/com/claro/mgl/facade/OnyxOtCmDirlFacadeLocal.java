/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.SegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author cardenaslb
 */

public interface OnyxOtCmDirlFacadeLocal extends BaseFacadeLocal<OnyxOtCmDir>{
    
   List<OnyxOtCmDir> findOnyxOtCmDirById(BigDecimal idOt) throws ApplicationException;
   
   List<OnyxOtCmDir> findOnyxOtHhppById(BigDecimal idOt) throws ApplicationException;
   
   List<SegmentoDto> findAllSegmento() throws ApplicationException;
   
}
