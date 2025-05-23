/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "hhppDetalleSessionBean")
@SessionScoped
public class HhppDetalleSessionBean implements Serializable {
    
    HhppMgl hhppSeleccionado;
    CmtDireccionDetalladaMgl direccionDetalladaSeleccionada;
    
     public void init(){
         
     }   

    public HhppMgl getHhppSeleccionado() {
        return hhppSeleccionado;
    }

    public void setHhppSeleccionado(HhppMgl hhppSeleccionado) {
        this.hhppSeleccionado = hhppSeleccionado;
    }    

    public CmtDireccionDetalladaMgl getDireccionDetalladaSeleccionada() {
        return direccionDetalladaSeleccionada;
    }

    public void setDireccionDetalladaSeleccionada(CmtDireccionDetalladaMgl direccionDetalladaSeleccionada) {
        this.direccionDetalladaSeleccionada = direccionDetalladaSeleccionada;
    }    
     
    
}
