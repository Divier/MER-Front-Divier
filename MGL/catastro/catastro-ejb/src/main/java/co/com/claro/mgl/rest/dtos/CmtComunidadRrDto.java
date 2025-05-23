/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jrodriguez
 */
@XmlRootElement(name = "cmtComunidadRr")
public class CmtComunidadRrDto {

    @XmlElement
    private String codigo;
    @XmlElement
    private String nombreComunidad;
    @XmlElement
    private CmtRegionDto cmtRegional;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
    }

    public CmtRegionDto getCmtRegional() {
        return cmtRegional;
    }

    public void setCmtRegional(CmtRegionDto cmtRegional) {
        this.cmtRegional = cmtRegional;
    }

}
