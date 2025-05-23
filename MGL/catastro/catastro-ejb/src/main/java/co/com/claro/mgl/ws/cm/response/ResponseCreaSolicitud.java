package co.com.claro.mgl.ws.cm.response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class ResponseCreaSolicitud {
    
    @XmlElement
    private String mensaje;
    @XmlElement
    private String tipoRespuesta;
    @XmlElement
    private String idSolicitud;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
