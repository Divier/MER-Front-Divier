/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mgl.businessmanager.cm.CtmGestionSegCMManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCM;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Ejb de la pagina de seguridad. Permite manejar la
 * logica de administracion de la seguridad de una cuenta matriz
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Stateless
public class CtmGestionSegCMFacade implements  ICtmGestionSegCmFacadeLocal{
    
    @Override
    public List<CtmGestionSegCM> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCM create(CtmGestionSegCM t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public CtmGestionSegCMDto findAllManagementAccount(BigDecimal cuentaMatriz)throws ApplicationException{
                CtmGestionSegCMManager ctmGestionSegCMManager=new CtmGestionSegCMManager();
        return ctmGestionSegCMManager.findAllManagementAccount(cuentaMatriz);
    }

    @Override
    public CtmGestionSegCM update(CtmGestionSegCM t) throws ApplicationException {
         CtmGestionSegCMManager ctmGestionSegCMManager=new CtmGestionSegCMManager();
        return ctmGestionSegCMManager.update(t);
     }
    @Override
    public boolean delete(CtmGestionSegCM t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCM findById(CtmGestionSegCM sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCMDto save(CtmGestionSegCMDto t) throws ApplicationException {
        CtmGestionSegCMManager ctmGestionSegCMManager=new CtmGestionSegCMManager();
        return ctmGestionSegCMManager.create(t);
    }
    
}
