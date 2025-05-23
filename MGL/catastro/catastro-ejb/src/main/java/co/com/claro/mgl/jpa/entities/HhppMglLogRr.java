/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "TEC_TECNO_HABILITADA_LOG_RR", schema = "MGL_SCHEME")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "HhppMglLogRr.trucateLogHhppRr",
                                    procedureName = "MGL_SCHEME.TRUNCATE_LOG_HHPP_RR")
})
public class HhppMglLogRr implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "HhppMglLogRr.TEC_TECNO_HABILITADA_LOG_RR_SQ",
            sequenceName = "MGL_SCHEME.TEC_TECNO_HABILITADA_LOG_RR_SQ", allocationSize = 1)
    @GeneratedValue(generator = "HhppMglLogRr.TEC_TECNO_HABILITADA_LOG_RR_SQ")
    @Column(name = "TEC_TECNO_HAB_LOG_RR_ID", nullable = false)
    private BigDecimal hhppLogRrId;

    @Column(name = "FECHA_AUD_LOG_SIG")
    private BigDecimal fechaAudLogSiglo;
    @Column(name = "FECHA_AUD_LOG_AÑO")
    private BigDecimal fechaAudLogAño;
    @Column(name = "FECHA_AUD_LOG_MES")
    private BigDecimal fechaAudLogMes;
    @Column(name = "FECHA_AUD_LOG_DIA")
    private BigDecimal fechaAudLogDia;

    @Column(name = "FECHA_AUD_LOG_SIG_2")
    private BigDecimal fechaAudLogSiglo2;
    @Column(name = "FECHA_AUD_LOG_AÑO_2")
    private BigDecimal fechaAudLogAño2;
    @Column(name = "FECHA_AUD_LOG_MES_2")
    private BigDecimal fechaAudLogMes2;
    @Column(name = "FECHA_AUD_LOG_DIA_2")
    private BigDecimal fechaAudLogDia2;

    @Column(name = "COMUNIDAD")
    private String comunidad;

    @Column(name = "DIVISION")
    private String division;

    @Column(name = "VENDEDOR")
    private String vendedor;

    @Column(name = "TIPO_ACOMETIDA")
    private String tipoAcometida;

    @Column(name = "TIPO_CABLE")
    private String tipoCable;

    @Column(name = "HEAD_END")
    private String headEnd;

    @Column(name = "TIPO_HHPP")
    private String tipoHhpp;

    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;

    @Column(name = "NODO")
    private String nodo;

    @Column(name = "ULT_UBICACION")
    private String ultimaUbicacion;

    @Column(name = "ESTRATO")
    private String estrato;

    @Column(name = "ID_RR")
    private BigDecimal idRR;

    public BigDecimal getHhppLogRrId() {
        return hhppLogRrId;
    }

    public void setHhppLogRrId(BigDecimal hhppLogRrId) {
        this.hhppLogRrId = hhppLogRrId;
    }

    public BigDecimal getFechaAudLogSiglo() {
        return fechaAudLogSiglo;
    }

    public void setFechaAudLogSiglo(BigDecimal fechaAudLogSiglo) {
        this.fechaAudLogSiglo = fechaAudLogSiglo;
    }

    public BigDecimal getFechaAudLogAño() {
        return fechaAudLogAño;
    }

    public void setFechaAudLogAño(BigDecimal fechaAudLogAño) {
        this.fechaAudLogAño = fechaAudLogAño;
    }

    public BigDecimal getFechaAudLogMes() {
        return fechaAudLogMes;
    }

    public void setFechaAudLogMes(BigDecimal fechaAudLogMes) {
        this.fechaAudLogMes = fechaAudLogMes;
    }

    public BigDecimal getFechaAudLogDia() {
        return fechaAudLogDia;
    }

    public void setFechaAudLogDia(BigDecimal fechaAudLogDia) {
        this.fechaAudLogDia = fechaAudLogDia;
    }

    public BigDecimal getFechaAudLogSiglo2() {
        return fechaAudLogSiglo2;
    }

    public void setFechaAudLogSiglo2(BigDecimal fechaAudLogSiglo2) {
        this.fechaAudLogSiglo2 = fechaAudLogSiglo2;
    }

    public BigDecimal getFechaAudLogAño2() {
        return fechaAudLogAño2;
    }

    public void setFechaAudLogAño2(BigDecimal fechaAudLogAño2) {
        this.fechaAudLogAño2 = fechaAudLogAño2;
    }

    public BigDecimal getFechaAudLogMes2() {
        return fechaAudLogMes2;
    }

    public void setFechaAudLogMes2(BigDecimal fechaAudLogMes2) {
        this.fechaAudLogMes2 = fechaAudLogMes2;
    }

    public BigDecimal getFechaAudLogDia2() {
        return fechaAudLogDia2;
    }

    public void setFechaAudLogDia2(BigDecimal fechaAudLogDia2) {
        this.fechaAudLogDia2 = fechaAudLogDia2;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getTipoAcometida() {
        return tipoAcometida;
    }

    public void setTipoAcometida(String tipoAcometida) {
        this.tipoAcometida = tipoAcometida;
    }

    public String getTipoCable() {
        return tipoCable;
    }

    public void setTipoCable(String tipoCable) {
        this.tipoCable = tipoCable;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getTipoHhpp() {
        return tipoHhpp;
    }

    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getUltimaUbicacion() {
        return ultimaUbicacion;
    }

    public void setUltimaUbicacion(String ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public BigDecimal getIdRR() {
        return idRR;
    }

    public void setIdRR(BigDecimal idRR) {
        this.idRR = idRR;
    }
    
}
