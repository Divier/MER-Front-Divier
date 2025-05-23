/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;


import co.com.claro.mgl.businessmanager.cm.OnyxOtCmDirManager;
import co.com.claro.mgl.dtos.SegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class OnyxOtCmDirFacade implements OnyxOtCmDirlFacadeLocal{

    @Override
    public List<OnyxOtCmDir> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OnyxOtCmDir create(OnyxOtCmDir t) throws ApplicationException {
        OnyxOtCmDirManager onyxOtCmDirManager
                = new OnyxOtCmDirManager();
        return onyxOtCmDirManager.create(t);
    }

    @Override
    public OnyxOtCmDir update(OnyxOtCmDir t) throws ApplicationException {
        OnyxOtCmDirManager onyxOtCmDirManager
                = new OnyxOtCmDirManager();
        return onyxOtCmDirManager.update(t);
    }

    @Override
    public boolean delete(OnyxOtCmDir t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OnyxOtCmDir findById(OnyxOtCmDir sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OnyxOtCmDir> findOnyxOtCmDirById(BigDecimal idOt) throws ApplicationException {
        OnyxOtCmDirManager onyxOtCmDirManager
                = new OnyxOtCmDirManager();
        return onyxOtCmDirManager.findOnyxOtCmById(idOt);
    }

    @Override
    public List<OnyxOtCmDir> findOnyxOtHhppById(BigDecimal idOt) throws ApplicationException {
        OnyxOtCmDirManager onyxOtCmDirManager
                = new OnyxOtCmDirManager();
        return onyxOtCmDirManager.findOnyxOtHHppById(idOt);
    }

    @Override
    public List<SegmentoDto> findAllSegmento() throws ApplicationException {
          OnyxOtCmDirManager onyxOtCmDirManager
                = new OnyxOtCmDirManager();
        return onyxOtCmDirManager.findAllSegmento();
    }
    
   
   
    
  
}
