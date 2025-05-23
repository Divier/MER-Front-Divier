/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement
public class GeoPoliticoDto implements Serializable {

    @XmlElement
    private BigDecimal gpoId;
    @XmlElement
    private String gpoNombre;
    @XmlElement
    private String gpoTipo;
    @XmlElement
    private String geoCodigoDane;

    public BigDecimal getGpoId() {
        return gpoId;
    }

    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    public String getGpoNombre() {
        return gpoNombre;
    }

    public void setGpoNombre(String gpoNombre) {
        this.gpoNombre = gpoNombre;
    }

    public String getGpoTipo() {
        return gpoTipo;
    }

    public void setGpoTipo(String gpoTipo) {
        this.gpoTipo = gpoTipo;
    }

    public String getGeoCodigoDane() {
        return geoCodigoDane;
    }

    public void setGeoCodigoDane(String geoCodigoDane) {
        this.geoCodigoDane = geoCodigoDane;
    }
    
}
