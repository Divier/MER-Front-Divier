/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.ptlus.MglSecurityManager;
import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class MglSecurityFacade implements MglSecurityFacadeLocal {

    /**
     * Busca los datos del usuario y roles aosciados a este
     *
     * @author Victor Bocanegra
     * @param Usuario
     * @param urlServiceLogin
     * @param Verificacion
     * @return String
     * @throws ApplicationException
     */
    @Override
    public String obtenerDataRoles(String Usuario, String Verificacion, String urlServiceLogin)
            throws ApplicationException {
        
        
        MglSecurityManager mglSecurityManager = new MglSecurityManager();
        return mglSecurityManager.obtenerDataRoles(Usuario, Verificacion, urlServiceLogin);
    }
}
