/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.rr;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "UNITLOG", schema = "CABLEDTA")
public class LogHhppRr implements Serializable { 
    

    @Id
    @Column(name = "UNAKYÑ")
    private BigDecimal idUnitLog;
    @Column(name = "SULOGC")
    private BigDecimal  fechaLogSiglo;
    @Column(name = "SULOGY")
    private BigDecimal  fechaLogAño;
    @Column(name = "SULOGM")
    private BigDecimal  fechaLogMes;
    @Column(name = "SULOGD")
    private BigDecimal  fechaLogDia;
    @Column(name = "SULOGT")
    private BigDecimal  hora;

    public BigDecimal getIdUnitLog() {
        return idUnitLog;
    }

    public void setIdUnitLog(BigDecimal idUnitLog) {
        this.idUnitLog = idUnitLog;
    }

    public BigDecimal getFechaLogSiglo() {
        return fechaLogSiglo;
    }

    public void setFechaLogSiglo(BigDecimal fechaLogSiglo) {
        this.fechaLogSiglo = fechaLogSiglo;
    }

    public BigDecimal getFechaLogAño() {
        return fechaLogAño;
    }

    public void setFechaLogAño(BigDecimal fechaLogAño) {
        this.fechaLogAño = fechaLogAño;
    }

    public BigDecimal getFechaLogMes() {
        return fechaLogMes;
    }

    public void setFechaLogMes(BigDecimal fechaLogMes) {
        this.fechaLogMes = fechaLogMes;
    }

    public BigDecimal getFechaLogDia() {
        return fechaLogDia;
    }

    public void setFechaLogDia(BigDecimal fechaLogDia) {
        this.fechaLogDia = fechaLogDia;
    }

    public BigDecimal getHora() {
        return hora;
    }

    public void setHora(BigDecimal hora) {
        this.hora = hora;
    }
   
}
