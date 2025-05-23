/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.telmex.catastro.util.FacesUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author User
 */
@ManagedBean(name = "fichaMenuBean")
@ViewScoped
public class FichaMenuBean {
    
    private static final Logger LOGGER = LogManager.getLogger(FichaMenuBean.class);
    private String menuFicha;
    private String menuFichaAccion;
    
    
    public String menuFichaAccionChange() {
        String page = "";
        try {
            if(menuFicha.equalsIgnoreCase("1")){
                if (menuFichaAccion.equalsIgnoreCase("1")){
                    page = "generarPreFicha";
                }else if (menuFichaAccion.equalsIgnoreCase("2")){
                    page = "validarPreFicha";
                }else if (menuFichaAccion.equalsIgnoreCase("3")){
                    page = "crearPreFicha";
                }
            }else if(menuFicha.equalsIgnoreCase("2")){
                if (menuFichaAccion.equalsIgnoreCase("1")){
                    page = "generarFicha";
                }else if (menuFichaAccion.equalsIgnoreCase("2")){
                    page = "validarFicha";
                }else if (menuFichaAccion.equalsIgnoreCase("3")){
                    page = "crearFicha";
                }
            }
            
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("error en  menuFichaAccionChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("error en  menuFichaAccionChange. ", e, LOGGER);
        }
        return page;
    }

    public String getMenuFicha() {
        return menuFicha;
    }

    public void setMenuFicha(String menuFicha) {
        this.menuFicha = menuFicha;
    }

    public String getMenuFichaAccion() {
        return menuFichaAccion;
    }

    public void setMenuFichaAccion(String menuFichaAccion) {
        this.menuFichaAccion = menuFichaAccion;
    }
    
}
