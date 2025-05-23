package co.com.telmex.catastro.mbeans.seguridad;

import co.com.telmex.catastro.data.Usuario;
import co.com.telmex.catastro.delegate.SeguridadDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.ConstantWEB;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase Login
 * Extiende de BaseMBean
 *
 * @author 	Jose Luis Caicedo
 * @version	1.0
 */
@ManagedBean
public class Login extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(Login.class);

    private String userName = "";
    private String pwd = "";
    private String nombreLog;

    /**
     * 
     * @throws IOException
     */
    public Login() throws IOException {
        super();
    }

    /**
     * 
     * @return
     */
    public String onEnter() {
        String response = "login";
        try {
            Usuario user = SeguridadDelegate.processAuthentic(userName, pwd);
            if (user.getUsuId() == null) {
                message = user.getMessage();
            } else {
                addDataOnSession(user);
                response = "selectionrol";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en onEnter" + e.getMessage() , e, LOGGER);            
        }
        return response;
    }

    /**
     * 
     * @param user 
     */
    private void addDataOnSession(Usuario user) {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        httpSession.setAttribute(ConstantWEB.USER_SESSION, user);
    }

    /**
     * 
     * @return
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     */
    public String getNombreLog() {
        return nombreLog;
    }
}