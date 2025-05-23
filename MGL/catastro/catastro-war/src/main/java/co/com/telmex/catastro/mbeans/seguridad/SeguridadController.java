/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.seguridad;

import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jrodriguez
 */
public class SeguridadController {

    private static final Logger LOGGER = LogManager.getLogger(SeguridadController.class);

    private SeguridadController(){
    }
    
    public static SeguridadController newInstance(){
        return new SeguridadController();
    }
    
    public void check() {
        HttpServletResponse response = JSFUtil.getHttpServletResponse();
        HttpSession session = JSFUtil.getHttpSession();

        try {
            SecurityLogin securityLogin = new SecurityLogin(JSFUtil.getFacesContext());
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            String usuarioVT = securityLogin.getLoginUser();

            if (usuarioVT == null) {
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session.setAttribute("usuarioM", usuarioVT);
            }
            int usuarioID = securityLogin.getPerfilUsuario();
            
            if (usuarioID == 0) {
                usuarioID = Integer.parseInt((String) session.getAttribute("usuarioIDM"));
            } else {
                session.setAttribute("usuarioIDM", usuarioID);
            }
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }

}
