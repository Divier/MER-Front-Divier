/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMAuditoriaDto;
import co.com.claro.mgl.businessmanager.cm.CtmGestionSegCMAuditoriaManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCMAuditoria;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Ejb de la pagina de seguridad. Permite manejar la
 * logica de administracion de la seguridad de una cuenta matriz
 * y su auditoria.
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Stateless
public class CtmGestionSegCMAuditoriaFacade implements ICtmGestionSegCmAuditoriaFacadeLocal{

    @Override
    public List<CtmGestionSegCMAuditoriaDto> findAllAuditParentAccount(BigDecimal cuentaMatriz) throws ApplicationException {
        
        CtmGestionSegCMAuditoriaManager seguridad=new CtmGestionSegCMAuditoriaManager();
        return seguridad.findAllAuditParentAccount(cuentaMatriz);
    }

    @Override
    public List<CtmGestionSegCMAuditoria> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCMAuditoria create(CtmGestionSegCMAuditoria t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCMAuditoria update(CtmGestionSegCMAuditoria t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CtmGestionSegCMAuditoria t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CtmGestionSegCMAuditoria findById(CtmGestionSegCMAuditoria sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
