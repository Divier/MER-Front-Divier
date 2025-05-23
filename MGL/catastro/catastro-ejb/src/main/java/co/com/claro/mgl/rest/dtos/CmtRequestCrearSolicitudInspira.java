/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */

@XmlRootElement
public class CmtRequestCrearSolicitudInspira  {
    
    @XmlElement
    private String idUsuario;
    @XmlElement
    private String observaciones;
    @XmlElement
    private String contacto;
    @XmlElement
    private String telefonoContacto;
    @XmlElement
    private String canalVentas;
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private CmtCityEntityDto  cmtCityEntityDto;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCanalVentas() {
        return canalVentas;
    }

    public void setCanalVentas(String canalVentas) {
        this.canalVentas = canalVentas;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public CmtCityEntityDto getCmtCityEntityDto() {
        return cmtCityEntityDto;
    }

    public void setCmtCityEntityDto(CmtCityEntityDto cmtCityEntityDto) {
        this.cmtCityEntityDto = cmtCityEntityDto;
    }


}
