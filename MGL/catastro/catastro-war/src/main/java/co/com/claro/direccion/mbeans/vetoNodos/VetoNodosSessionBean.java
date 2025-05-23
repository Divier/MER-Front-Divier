/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direccion.mbeans.vetoNodos;

import co.com.claro.mgl.jpa.entities.VetoMgl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Clase creada con el proposito de mantener en memoria durante la session
 * el valor del nodo seleccionado.
 * 
 * @author Juan David Hernandez
 */
@ManagedBean(name = "vetoNodosSessionBean")
@SessionScoped
public class VetoNodosSessionBean {
    
 private VetoMgl vetoSeleccionado;


     public void init(){
         
     }   
  
 
    public VetoMgl getVetoSeleccionado() {
        return vetoSeleccionado;
    }

    public void setVetoSeleccionado(VetoMgl vetoSeleccionado) {
        this.vetoSeleccionado = vetoSeleccionado;
    }
 
 
 
    
}
