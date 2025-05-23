/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "direccionDetalladaRequest")
public class CmtDireccionDetalladaRequestDto {

    @XmlElement
    private BigDecimal idDireccion;
    @XmlElement
    private String segmento;
    @XmlElement
    private String proyecto;

    public BigDecimal getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(BigDecimal idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }
}
