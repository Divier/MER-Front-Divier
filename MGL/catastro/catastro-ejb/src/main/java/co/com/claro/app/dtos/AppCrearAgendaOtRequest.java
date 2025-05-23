/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appCrearAgendaOtRequest")
public class AppCrearAgendaOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String otLocation;
    @XmlElement
    private List<CapacidadAgendaDto> capacidad;
    @XmlElement
    private String idOt;
    @XmlElement
    private String personaRecVt;
    @XmlElement
    private String emailPerRecVT;
    @XmlElement
    private String telPerRecVt;
    @XmlElement
    private List<AppCrearAgendaOtPropertyRequest> property;
    
    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }

    public List<CapacidadAgendaDto> getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(List<CapacidadAgendaDto> capacidad) {
        this.capacidad = capacidad;
    }

    public String getIdOt() {
        return idOt;
    }

    public void setIdOt(String idOt) {
        this.idOt = idOt;
    }

    public String getPersonaRecVt() {
        return personaRecVt;
    }

    public void setPersonaRecVt(String personaRecVt) {
        this.personaRecVt = personaRecVt;
    }

    public String getEmailPerRecVT() {
        return emailPerRecVT;
    }

    public void setEmailPerRecVT(String emailPerRecVT) {
        this.emailPerRecVT = emailPerRecVT;
    }

    public String getTelPerRecVt() {
        return telPerRecVt;
    }

    public void setTelPerRecVt(String telPerRecVt) {
        this.telPerRecVt = telPerRecVt;
    }

    public List<AppCrearAgendaOtPropertyRequest> getProperty() {
        return property;
    }

    public void setProperty(List<AppCrearAgendaOtPropertyRequest> property) {
        this.property = property;
    }
}
