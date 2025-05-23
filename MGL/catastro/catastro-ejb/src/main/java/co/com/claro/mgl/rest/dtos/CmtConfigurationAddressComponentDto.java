/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.jpa.entities.AptoValues;
import co.com.claro.mgl.jpa.entities.BmValues;
import co.com.claro.mgl.jpa.entities.CkValues;
import co.com.claro.mgl.jpa.entities.ComplementoValues;
import co.com.claro.mgl.jpa.entities.DrTipoDireccion;
import co.com.claro.mgl.jpa.entities.ItValues;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "configurationAddressComponent")
public class CmtConfigurationAddressComponentDto extends CmtDefaultBasicResponse{
   
    /* Variables */
    @XmlElement
    private List<DrTipoDireccion> tiposDireccion;
    @XmlElement
    private CkValues ckValues = new CkValues();
    @XmlElement
    private BmValues bmValues = new BmValues();
    @XmlElement
    private ItValues itValues = new ItValues();
    @XmlElement
    private ComplementoValues complementoValues = new ComplementoValues();
    @XmlElement
    private AptoValues aptoValues;

    /* Getters and Setters */
    public List<DrTipoDireccion> getTiposDireccion() { 
        return tiposDireccion;
    }

    public void setTiposDireccion(List<DrTipoDireccion> tiposDireccion) {
        this.tiposDireccion = tiposDireccion;
    }

    public CkValues getCkValues() {
        return ckValues;
    }

    public void setCkValues(CkValues ckValues) {
        this.ckValues = ckValues;
    }

    public BmValues getBmValues() {
        return bmValues;
    }

    public void setBmValues(BmValues bmValues) {
        this.bmValues = bmValues;
    }

    public ItValues getItValues() {
        return itValues;
    }

    public void setItValues(ItValues itValues) {
        this.itValues = itValues;
    }

    public AptoValues getAptoValues() {
        return aptoValues;
    }

    public void setAptoValues(AptoValues aptoValues) {
        this.aptoValues = aptoValues;
    }

    public ComplementoValues getComplementoValues() {
        return complementoValues;
    }

    public void setComplementoValues(ComplementoValues complementoValues) {
        this.complementoValues = complementoValues;
    }

}