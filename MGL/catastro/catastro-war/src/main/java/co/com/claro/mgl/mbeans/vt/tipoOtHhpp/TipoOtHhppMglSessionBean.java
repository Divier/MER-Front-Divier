/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.tipoOtHhpp;

import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Clase creada con el proposito de mantener en memoria durante la session
 * el valor del tipo de ot seleccionado.
 * 
 * @author Juan David Hernandez
 */
@ManagedBean(name = "tipoOtHhppMglSessionBean")
@SessionScoped
public class TipoOtHhppMglSessionBean implements Serializable{
    
   TipoOtHhppMgl TipoOtHhppMglSeleccionado;
    
     
     public void init(){
         
     }  

    public TipoOtHhppMgl getTipoOtHhppMglSeleccionado() {
        return TipoOtHhppMglSeleccionado;
    }

    public void setTipoOtHhppMglSeleccionado(TipoOtHhppMgl TipoOtHhppMglSeleccionado) {
        this.TipoOtHhppMglSeleccionado = TipoOtHhppMglSeleccionado;
    }
     
     
     
    
}
