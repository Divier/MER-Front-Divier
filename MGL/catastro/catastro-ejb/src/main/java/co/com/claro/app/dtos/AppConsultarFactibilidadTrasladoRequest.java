/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import co.com.claro.mgl.rest.dtos.CmtDireccionTabuladaDto;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement
public class AppConsultarFactibilidadTrasladoRequest extends CmtDefaultBasicResquest{
    
    
    @XmlElement
    private String idEnlace;
    @XmlElement
    private String codigoDane;
    @XmlElement
    private CmtDireccionTabuladaDto direccionTabulada;
    
    
    public String getIdEnlace() {
        return idEnlace;
    }

    public void setIdEnlace(String idEnlace) {
        this.idEnlace = idEnlace;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public CmtDireccionTabuladaDto getDireccionTabulada() {
        return direccionTabulada;
    }

    public void setDireccionTabulada(CmtDireccionTabuladaDto direccionTabulada) {
        this.direccionTabulada = direccionTabulada;
    } 
}
