/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.ptlus;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.net.cable.Agendamiento.client.ClientLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bocanegravm
 */
public class MglSecurityManager {

    
    private static final Logger LOGGER = LogManager.getLogger(MglSecurityManager.class);
    /**
     * Busca los datos del usuario y roles aosciados a este
     *
     * @author Victor Bocanegra
     * @param Usuario
     * @param Verificacion
     * @param urlServiceLogin
     * @return String
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String obtenerDataRoles(String Usuario, String Verificacion, String urlServiceLogin)
            throws ApplicationException {

        try {
            return ClientLogin.obtenerDataRoles(Usuario, Verificacion, urlServiceLogin);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al obtener la información de los roles. EX000: " + ex.getMessage(), ex);
        }
    }
}
