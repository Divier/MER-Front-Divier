/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean(name = "NodeServerManagerBean")
@RequestScoped 
public class NodeServerManagerBean {
    
    private static final Logger LOGGER = LogManager.getLogger(NodeServerManagerBean.class);
    
    String serverName;

    public NodeServerManagerBean() {
        serverName = System.getProperty("weblogic.Name");        
    }
    

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public String menuFichaAction(){
        return "fichaMenu";
    }
    public void menuRegresarAction(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en menuRegresarAction. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en menuRegresarAction. ", e, LOGGER);
        }
        
    }
    
}
