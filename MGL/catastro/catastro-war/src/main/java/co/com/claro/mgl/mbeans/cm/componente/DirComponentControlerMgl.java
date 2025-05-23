package co.com.claro.mgl.mbeans.cm.componente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 * @author camargomf
 */
@FacesComponent("co.com.claro.mgl.mbeans.cm.componente.DirComponentControlerMgl")
public class DirComponentControlerMgl extends UINamingContainer {
    private String estiloObligatorio = "<font color='red'>*</font>";
    public DirComponentControlerMgl() {
    }

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }
    
}
