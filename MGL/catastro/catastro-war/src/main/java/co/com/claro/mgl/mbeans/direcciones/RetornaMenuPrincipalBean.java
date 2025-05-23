/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Clase realizada con el motivo de ser la unica clase controladora
 * del regreso al menú principal desde cualquier bean página.
 * 
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "retornaMenuPrincipalBean")
public class RetornaMenuPrincipalBean implements Serializable {
    
    private static final Logger LOGGER = LogManager.getLogger(RetornaMenuPrincipalBean.class);
    
    private String regresarMenu = "<- Regresar Menú";

    public RetornaMenuPrincipalBean() {
    }

    /**
     * Función que redirecciona al menú principal del a aplicación.
     *
     * @author Juan David Hernandez
     * @throws java.io.IOException
     */
    public void redirecToMenu() throws IOException {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en redirecToMenu. Al itentar regresar al menú principal", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en redirecToMenu. Al itentar regresar al menú principal", e, LOGGER);
        }
    }

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }
    
    
    
}
