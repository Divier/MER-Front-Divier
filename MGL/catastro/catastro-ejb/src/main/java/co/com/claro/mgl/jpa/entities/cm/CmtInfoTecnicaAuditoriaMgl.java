/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_INFO_TECNICA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtInfoTecnicaAuditoriaMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "INFO_TEC_AUD_ID", nullable = false)
    private BigDecimal infoTecAudId;
    
    @Column(name = "ID_INF_TEC", nullable = false)
    private BigDecimal idInfTec;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUBEDIFICIO")
    private CmtSubEdificioMgl idSubedificio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_1")
    private CmtBasicaMgl basicaIdInfoN1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_2")
    private CmtBasicaMgl basicaIdInfoN2;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_3")
    private CmtBasicaMgl basicaIdInfoN3;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_4")
    private CmtBasicaMgl basicaIdInfoN4;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_SUB_ID")
    private CmtTecnologiaSubMgl tecnologiaSubMglObj;
    
    @Column(name = "FECHACREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "USUARIOCREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    
    @Column(name = "PERFILCREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHAEDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)   
    private Date fechaEdicion;
    
    @Column(name = "USUARIOEDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
    @Column(name = "PERFILEDICION")
    private int perfilEdicion;
    
    @NotNull
    @Column(name = "ESTADOREGISTRO")
    private int estadoRegistro;
    
    @Column(name = "AUD_ACTION",length = 3)
    private String accionAuditoria;
    
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    
    @Column(name = "AUD_USER",length = 30)
    private String usuarioAuditoria;
    
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;

    public BigDecimal getInfoTecAudId() {
        return infoTecAudId;
    }

    public void setInfoTecAudId(BigDecimal infoTecAudId) {
        this.infoTecAudId = infoTecAudId;
    }

    public BigDecimal getIdInfTec() {
        return idInfTec;
    }

    public void setIdInfTec(BigDecimal idInfTec) {
        this.idInfTec = idInfTec;
    }

    public CmtSubEdificioMgl getIdSubedificio() {
        return idSubedificio;
    }

    public void setIdSubedificio(CmtSubEdificioMgl idSubedificio) {
        this.idSubedificio = idSubedificio;
    }

    public CmtBasicaMgl getBasicaIdInfoN1() {
        return basicaIdInfoN1;
    }

    public void setBasicaIdInfoN1(CmtBasicaMgl basicaIdInfoN1) {
        this.basicaIdInfoN1 = basicaIdInfoN1;
    }

    public CmtBasicaMgl getBasicaIdInfoN2() {
        return basicaIdInfoN2;
    }

    public void setBasicaIdInfoN2(CmtBasicaMgl basicaIdInfoN2) {
        this.basicaIdInfoN2 = basicaIdInfoN2;
    }

    public CmtBasicaMgl getBasicaIdInfoN3() {
        return basicaIdInfoN3;
    }

    public void setBasicaIdInfoN3(CmtBasicaMgl basicaIdInfoN3) {
        this.basicaIdInfoN3 = basicaIdInfoN3;
    }

    public CmtBasicaMgl getBasicaIdInfoN4() {
        return basicaIdInfoN4;
    }

    public void setBasicaIdInfoN4(CmtBasicaMgl basicaIdInfoN4) {
        this.basicaIdInfoN4 = basicaIdInfoN4;
    }

    public CmtTecnologiaSubMgl getTecnologiaSubMglObj() {
        return tecnologiaSubMglObj;
    }

    public void setTecnologiaSubMglObj(CmtTecnologiaSubMgl tecnologiaSubMglObj) {
        this.tecnologiaSubMglObj = tecnologiaSubMglObj;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
    }

    
}
