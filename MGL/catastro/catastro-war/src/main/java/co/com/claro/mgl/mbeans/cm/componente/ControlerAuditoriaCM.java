package co.com.claro.mgl.mbeans.cm.componente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 * @author Admin
 */
@FacesComponent("co.com.claro.mgl.mbeans.cm.componente.ControlerAuditoriaCM")
public class ControlerAuditoriaCM extends UINamingContainer { 
    public ControlerAuditoriaCM() {
    }

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }
}
