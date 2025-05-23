/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Local;

/**
 *
 * @author bocanegravm
 */
@Local
public interface MglSecurityFacadeLocal {

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
    String obtenerDataRoles(String Usuario, String Verificacion, String urlServiceLogin)
            throws ApplicationException;
}
