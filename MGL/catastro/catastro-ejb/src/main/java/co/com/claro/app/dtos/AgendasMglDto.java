/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

/**
 *
 * @author bocanegravm
 */


import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AgendasMglDto {
    
    @XmlElement
    private Date fechaAgenda;
    @XmlElement
    private Date fechaReagenda;
    @XmlElement
    private String timeSlot;
    @XmlElement
    private String basicaIdrazones;
    @XmlElement
    private String ofpsOtId;
    @XmlElement
    private BigDecimal workForceId;
    @XmlElement
    private Date fechaInivioVt;
    @XmlElement
    private String nombreTecnico;
    @XmlElement
    private Date fechaFinVt;
    @XmlElement
    private String basicaIdEstadoAgenda;
    @XmlElement
    private String observacionesTecnico;
    @XmlElement
    private String idOtenrr;
    @XmlElement
    private BigDecimal idOt;

    public Date getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(Date fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public Date getFechaReagenda() {
        return fechaReagenda;
    }

    public void setFechaReagenda(Date fechaReagenda) {
        this.fechaReagenda = fechaReagenda;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getOfpsOtId() {
        return ofpsOtId;
    }

    public void setOfpsOtId(String ofpsOtId) {
        this.ofpsOtId = ofpsOtId;
    }

    public Date getFechaInivioVt() {
        return fechaInivioVt;
    }

    public void setFechaInivioVt(Date fechaInivioVt) {
        this.fechaInivioVt = fechaInivioVt;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public Date getFechaFinVt() {
        return fechaFinVt;
    }

    public void setFechaFinVt(Date fechaFinVt) {
        this.fechaFinVt = fechaFinVt;
    }

    public String getObservacionesTecnico() {
        return observacionesTecnico;
    }

    public void setObservacionesTecnico(String observacionesTecnico) {
        this.observacionesTecnico = observacionesTecnico;
    }

    public String getIdOtenrr() {
        return idOtenrr;
    }

    public void setIdOtenrr(String idOtenrr) {
        this.idOtenrr = idOtenrr;
    }

    public String getBasicaIdrazones() {
        return basicaIdrazones;
    }

    public void setBasicaIdrazones(String basicaIdrazones) {
        this.basicaIdrazones = basicaIdrazones;
    }

    public String getBasicaIdEstadoAgenda() {
        return basicaIdEstadoAgenda;
    }

    public void setBasicaIdEstadoAgenda(String basicaIdEstadoAgenda) {
        this.basicaIdEstadoAgenda = basicaIdEstadoAgenda;
    }

    public BigDecimal getWorkForceId() {
        return workForceId;
    }

    public void setWorkForceId(BigDecimal workForceId) {
        this.workForceId = workForceId;
    }

    public BigDecimal getIdOt() {
        return idOt;
    }

    public void setIdOt(BigDecimal idOt) {
        this.idOt = idOt;
    }

    
}
