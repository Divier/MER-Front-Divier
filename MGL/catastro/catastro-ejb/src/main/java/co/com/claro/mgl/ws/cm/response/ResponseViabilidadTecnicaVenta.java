package co.com.claro.mgl.ws.cm.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@XmlRootElement(name = "ResponseViabilidadTecnicaVenta")
public class ResponseViabilidadTecnicaVenta {
    @XmlElement(name = "viable")
    private String viable = "";    
    @XmlElement(name = "codEstadoNodo")
    private String codEstadoNodo = "";
    @XmlElement(name = "estadoNodo")
    private String estadoNodo = "";
    @XmlElement(name = "codEstadoCm")
    private String codEstadoCm = "";
    @XmlElement(name = "estadoCm")
    private String estadoCm = "";
    @XmlElement(name = "codEstadoHhpp")
    private String codEstadoHhpp = "";
    @XmlElement(name = "estadoHhpp")
    private String estadoHhpp = "";
    @XmlElement(name = "mensaje")
    private String mensaje = "";
    @XmlElement(name = "esCuentaMatriz")
    private String esCuentaMatriz = "";
    @XmlElement(name = "tipoRespuesta")
    private String tipoRespuesta = "";
    @XmlElement(name = "estrato")
    private String estrato = "";

    public String getViable() {
        return viable;
    }

    public void setViable(String viable) {
        this.viable = viable;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public String getEstadoCm() {
        return estadoCm;
    }

    public void setEstadoCm(String estadoCm) {
        this.estadoCm = estadoCm;
    }

    public String getEstadoHhpp() {
        return estadoHhpp;
    }

    public void setEstadoHhpp(String estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEsCuentaMatriz() {
        return esCuentaMatriz;
    }

    public void setEsCuentaMatriz(String esCuentaMatriz) {
        this.esCuentaMatriz = esCuentaMatriz;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public String getCodEstadoNodo() {
        return codEstadoNodo;
    }

    public void setCodEstadoNodo(String codEstadoNodo) {
        this.codEstadoNodo = codEstadoNodo;
    }

    public String getCodEstadoCm() {
        return codEstadoCm;
    }

    public void setCodEstadoCm(String codEstadoCm) {
        this.codEstadoCm = codEstadoCm;
    }

    public String getCodEstadoHhpp() {
        return codEstadoHhpp;
    }

    public void setCodEstadoHhpp(String codEstadoHhpp) {
        this.codEstadoHhpp = codEstadoHhpp;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }
}