
package co.com.claro.mgl.mbeans.util;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
@ManagedBean(name = "tabsVisitaTecnica")
@SessionScoped
public class TabsVisitaTecnica implements Serializable {
    private static final long serialVersionUID = -445112386128125761L;
    private String formulario;
    private String currentTab;
    private String path;
    public TabsVisitaTecnica() {
        int a = 5;
        formulario="aaaa";
    }

    public String getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(String currentTab) {
        this.currentTab = currentTab;
    }

    public String cambiarTab(int numTab) {
        path = "/view/MGL/CM/tabsVt/";
        switch (numTab) {
            case 1:
                currentTab = "edificio";
                return path + currentTab;
            case 2:
                currentTab = "acometida";
                return path + currentTab;
            case 3:
                currentTab = "autorizacion";
                return path + currentTab;
            case 4:
                currentTab = "hhpp";
                return path + currentTab;
            case 5:
                currentTab = "item";
                return path + currentTab;
            case 6:
                currentTab = "item";
                return path + currentTab;
            case 7:
                currentTab = "costos";
                return path + currentTab;
            case 8:
                currentTab = "plano";
                return path + currentTab;
            case 9:
                currentTab = "horario";
                return path + currentTab;
            default:
                currentTab = "edificio";
                return path + currentTab;
        }
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }
}
