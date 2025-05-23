/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mgl.jpa.entities.Solicitud;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Clase creada con el proposito de mantener en memoria durante la session
 * el valor de la solicitud seleccionada y la pagina actual en la que se 
 * encuentra el usuario en el listado.
 * 
 * @author Juan David Hernandez
 */
@ManagedBean(name = "solicitudSessionBean")
@SessionScoped
public class SolicitudSessionBean {
    
     private Solicitud solicitudDthSeleccionada;
     private int paginaActual = 1;
     private boolean detalleSolicitud = false;
      
      
     public void init(){
         
     }   

    public Solicitud getSolicitudDthSeleccionada() {
        return solicitudDthSeleccionada;
    }

    public void setSolicitudDthSeleccionada(Solicitud solicitudDthSeleccionada) {
        this.solicitudDthSeleccionada = solicitudDthSeleccionada;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public boolean isDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(boolean detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }
    
    
    
    
      
      
    
}
