package co.com.claro.mgl.mbeans.solicitudes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Clase creada con el proposito de mantener en memoria durante la session el
 * valor de la solicitud seleccionada y la pagina actual en la que se encuentra
 * el usuario en el listado.
 *
 * @author Divier Casas
 * @version 1.0
 */
@ManagedBean(name = "nodoCuadranteSolicitudSessionBean")
@SessionScoped
public class NodoCuadranteSolicitudSessionBean {

    private int paginaActual = 1;
    private boolean detalleSolicitud = false;

    public void init() {

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
