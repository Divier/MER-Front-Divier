package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.data.Usuario;
import co.com.telmex.catastro.util.ConstantWEB;
import co.com.telmex.catastro.util.FacesUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase  BaseMbean
 *
 * @author 	Jose Luis Caicedo.
 * @version	1.0
 */
public class BaseMBean {

    /**
     * 
     */
    public Usuario user = null;
    /**
     * 
     */
    public Rol rol = null;
    /**
     * 
     */
    public String menu = "";
    /**
     * 
     */
    public String operacion;
    /**  maneja el sentido de la operacion para interfaz de doble funcionalidad  */
    public String message;

    /**
     * 
     */
    public BaseMBean() {
        this.operacion = "";
        this.message = "";
        menu = getMenuSession();
        user = getUserSession();
        rol = geRolSession();
    }

    /**
     * Consulta usuario de session
     * @return 
     */
    private String getMenuSession() {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        String menusession = (String) httpSession.getAttribute(ConstantWEB.MENU_SESSION);
        return menusession;
    }

    /**
     * Consulta usuario de session
     * @return 
     */
    private Usuario getUserSession() {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        Usuario usersession = (Usuario) httpSession.getAttribute(ConstantWEB.USER_SESSION);
        return usersession;
    }

    /**
     * Consulta Rol de session
     * @return 
     */
    private Rol geRolSession() {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        Rol rolsession = (Rol) httpSession.getAttribute(ConstantWEB.ROL_SESSION);
        return rolsession;
    }

    /**
     * 
     * @return
     */
    public String getMenu() {
        return menu;
    }

    /**
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @param operacion
     */
    public BaseMBean(String operacion) {
        this.operacion = operacion;
    }

    /**
     * 
     * @return
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * 
     * @param operacion
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * 
     * @return
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * 
     * @param rol
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * 
     * @return
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * 
     * @param user
     */
    public void setUser(Usuario user) {
        this.user = user;
    }
}
