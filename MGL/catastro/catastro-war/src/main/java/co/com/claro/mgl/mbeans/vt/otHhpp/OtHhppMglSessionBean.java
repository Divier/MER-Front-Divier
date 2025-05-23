/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Clase creada con el proposito de mantener en memoria durante la session
 * el valor de la ot seleccionado.
 * 
 * @author Juan David Hernandez
 */
@ManagedBean(name = "otHhppMglSessionBean")
@SessionScoped
public class OtHhppMglSessionBean implements Serializable{
    
    OtHhppMgl OtHhppMglSeleccionado;
    boolean detalleOtHhpp;
    
    
    public void init(){
         
     }  

    public OtHhppMgl getOtHhppMglSeleccionado() {
        return OtHhppMglSeleccionado;
    }

    public void setOtHhppMglSeleccionado(OtHhppMgl OtHhppMglSeleccionado) {
        this.OtHhppMglSeleccionado = OtHhppMglSeleccionado;
    }

    public boolean isDetalleOtHhpp() {
        return detalleOtHhpp;
    }

    public void setDetalleOtHhpp(boolean detalleOtHhpp) {
        this.detalleOtHhpp = detalleOtHhpp;
    }
    
    

    
}
