/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ResultModFacDirMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.CmtRequestConsByTokenDto;
import co.com.claro.mgl.rest.dtos.CmtResponseDirByToken;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class ResulModFacDirMglFacade  implements  ResulModFacDirMglFacadeLocal{
    
    
      @Override
      public CmtResponseDirByToken findDirByToken(CmtRequestConsByTokenDto cmtRequestConsByTokenDto)
            throws ApplicationException {

        ResultModFacDirMglManager mananager = new ResultModFacDirMglManager();
        return mananager.findDirByToken(cmtRequestConsByTokenDto);
    }
    
}
