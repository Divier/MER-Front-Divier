/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appReagendarOtRequest")
public class AppReagendarOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String otLocation;
    @XmlElement
    private BigDecimal idAgenda;
    @XmlElement
    private CapacidadAgendaDto capacidad;
    @XmlElement
    private String razonReagendar;
    @XmlElement
    private String comentarioReagendar;
    @XmlElement
    private String ofpsOtId;

    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }

    public BigDecimal getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(BigDecimal idAgenda) {
        this.idAgenda = idAgenda;
    }

    public CapacidadAgendaDto getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(CapacidadAgendaDto capacidad) {
        this.capacidad = capacidad;
    }

    public String getRazonReagendar() {
        return razonReagendar;
    }

    public void setRazonReagendar(String razonReagendar) {
        this.razonReagendar = razonReagendar;
    }

    public String getComentarioReagendar() {
        return comentarioReagendar;
    }

    public void setComentarioReagendar(String comentarioReagendar) {
        this.comentarioReagendar = comentarioReagendar;
    }

    public String getOfpsOtId() {
        return ofpsOtId;
    }

    public void setOfpsOtId(String ofpsOtId) {
        this.ofpsOtId = ofpsOtId;
    }
    
}