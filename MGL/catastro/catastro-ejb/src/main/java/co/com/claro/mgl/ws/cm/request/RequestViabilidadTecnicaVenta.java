package co.com.claro.mgl.ws.cm.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADMIN
 */
@XmlRootElement(name = "RequestViabilidadTecnicaVenta")
public class RequestViabilidadTecnicaVenta {
    @XmlElement(name = "comunidad")
    private String comunidad = "";
    @XmlElement(name = "divicion")
    private String divicion = "";
    @XmlElement(name = "calle")
    private String calle = "";    
    @XmlElement(name = "unidad")
    private String unidad = "";    
    @XmlElement(name = "apartamento")
    private String apartamento = "";    
    @XmlElement(name = "segmento")
    private String segmento = "";    

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivicion() {
        return divicion;
    }

    public void setDivicion(String divicion) {
        this.divicion = divicion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }
    
}
