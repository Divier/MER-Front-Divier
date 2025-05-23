/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "direccionRequest")
public class CmtDireccionRequestDto extends CmtDefaultBasicResquest {

    @XmlElement
    private String codigoDane;
    @XmlElement
    private String direccion;
    @XmlElement
    private CmtDireccionTabuladaDto direccionTabulada;
    @XmlElement
    private boolean isDth;
    @XmlElement
    private String nodoGestion;
    @XmlElement
    private String estrato;

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CmtDireccionTabuladaDto getDireccionTabulada() {
        return direccionTabulada;
    }

    public void setDireccionTabulada(CmtDireccionTabuladaDto direccionTabulada) {
        this.direccionTabulada = direccionTabulada;
    }

    public boolean isIsDth() {
        return isDth;
    }

    public void setIsDth(boolean isDth) {
        this.isDth = isDth;
    }

    public String getNodoGestion() {
        return nodoGestion;
    }

    public void setNodoGestion(String nodoGestion) {
        this.nodoGestion = nodoGestion;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }
}
