/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.seguridad;

import co.com.claro.mer.utils.SesionUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;


/**
 *
 * @author hernandezjuat
 */
@Log4j2
@ManagedBean(name = "usuarioLogueado")
@SessionScoped
public class UsuarioLogueado implements Serializable {
    
   private SecurityLogin securityLogin; 
   private FacesContext facesContext = FacesContext.getCurrentInstance();
   @Setter
   public String usuarioLogueado;

    public UsuarioLogueado() throws IOException{
          securityLogin = new SecurityLogin(facesContext);
        if (securityLogin.isLogin() && securityLogin.getLoginUser() != null) {
            usuarioLogueado = securityLogin.getLoginUser();
        } else {
            usuarioLogueado = "";
        }
    }

    /**
     * Obtiene el perfil del usuario en sesión.
     * @return {@link String} Retorna el perfil del usuario en sesión.
     * @author Gildardo Mora
     */
    public String getIdPerfilUsuario() {
            return SesionUtil.getIdPerfil();
    }

    /**
     * Obtiene el usuario logueado en la sesión.
     *
     * @return {@link String} Retorna el usuario en sesión.
     */
    public String getUsuarioLogueado() {
        return SesionUtil.getUsuarioLogueado();
    }

}
