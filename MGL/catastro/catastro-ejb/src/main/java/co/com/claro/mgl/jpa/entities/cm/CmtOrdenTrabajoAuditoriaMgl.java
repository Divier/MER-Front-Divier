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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_ORDENTRABAJO$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtOrdenTrabajoAuditoriaMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "OTAUD_ID", nullable = false)
    private BigDecimal otAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID", nullable = false)
    private CmtOrdenTrabajoMgl otIdObj;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cmObj;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl segmento;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_ID", referencedColumnName = "TIPO_OT_ID", nullable = false)
    private CmtTipoOtMgl tipoOtObj;
    
    @Column(name = "FECHA_CIERRE_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierreOt;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADOINTERNO", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInternoObj;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DETALLE", columnDefinition = "default 'EDICION'")
    private String detalle;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
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
    
    @Column(name = "FECHA_PROGRAMACION_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "OBSERVACION")
    private String observacion;
    
    @Column(name = "ID_USUARIO_CREACION", nullable = false)
    private BigDecimal usuarioCreacionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID", nullable = true)
    private CmtSolicitudCmMgl solicitud;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO")
    private CmtBasicaMgl basicaIdTipoTrabajo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", nullable = true)
    private CmtBasicaMgl basicaIdTecnologia;
    
    @JoinColumn(name = "BASICA_ID_CLASE_ORDEN", referencedColumnName = "BASICA_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CmtBasicaMgl claseOrdenTrabajo;
    
    @Column(name = "ORDEN_TRABAJO_REFERENCIA", nullable = true)
    private BigDecimal ordenTrabajoReferencia;
    
    @Column(name = "ONYX_OT_HIJA", nullable = true)
    private BigDecimal onyxOtHija;
    
    @Column(name = "PERSONA_RECIBE_VT")
    private String personaRecVt;
       
    
    @Column(name = "DOC_PER_RECIBE_VT")
    private String  docPerRecVt;
      
    
    @Column(name = "TEL_PER_RECIBA_VT")
    private String telPerRecVt;
    
    
    @Column(name = "PREREQUISITOS_VT")
    private String prerequisitosVT;
    
    
    @Column(name = "COMPLEJIDAD_SERVICIO")
    private String complejidadServicio;
    
    
    @Column(name = "EMAIL_PER_RECIBE_VT")
    private String emailPerRecVT;
    
    
    @Column(name = "FECHA_HABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHabilitacion;
    
    @Column(name = "DISPONIBILIDAD_GESTION")
    private String disponibilidadGestion;
    
    @Column(name = "ESTADOEXTINTOT", nullable = true)
    private BigDecimal estadoGeneralOt;
    
    @Column(name = "ENLACE_ID")
    private String enlaceId;
        
    
    public BigDecimal getOrdenTrabajoReferencia() {
        return ordenTrabajoReferencia;
    }

    public void setOrdenTrabajoReferencia(BigDecimal ordenTrabajoReferencia) {
        this.ordenTrabajoReferencia = ordenTrabajoReferencia;
    }
    
    

    public BigDecimal getOtAuditoriaId() {
        return otAuditoriaId;
    }

    public void setOtAuditoriaId(BigDecimal otAuditoriaId) {
        this.otAuditoriaId = otAuditoriaId;
    }

    public CmtOrdenTrabajoMgl getOtIdObj() {
        return otIdObj;
    }

    public void setOtIdObj(CmtOrdenTrabajoMgl otIdObj) {
        this.otIdObj = otIdObj;
    }

    public CmtCuentaMatrizMgl getCmObj() {
        return cmObj;
    }

    public void setCmObj(CmtCuentaMatrizMgl cmObj) {
        this.cmObj = cmObj;
    }

    public CmtBasicaMgl getSegmento() {
        return segmento;
    }

    public void setSegmento(CmtBasicaMgl segmento) {
        this.segmento = segmento;
    }

    public CmtTipoOtMgl getTipoOtObj() {
        return tipoOtObj;
    }

    public void setTipoOtObj(CmtTipoOtMgl tipoOtObj) {
        this.tipoOtObj = tipoOtObj;
    }

    public Date getFechaCierreOt() {
        return fechaCierreOt;
    }

    public void setFechaCierreOt(Date fechaCierreOt) {
        this.fechaCierreOt = fechaCierreOt;
    }

    public CmtBasicaMgl getEstadoInternoObj() {
        return estadoInternoObj;
    }

    public void setEstadoInternoObj(CmtBasicaMgl estadoInternoObj) {
        this.estadoInternoObj = estadoInternoObj;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    public BigDecimal getIdOt() {
        return otIdObj.getIdOt();
    }

    public BigDecimal getUsuarioCreacionId() {
        return usuarioCreacionId;
    }

    public void setUsuarioCreacionId(BigDecimal usuarioCreacionId) {
        this.usuarioCreacionId = usuarioCreacionId;
    }


    public CmtSolicitudCmMgl getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(CmtSolicitudCmMgl solicitud) {
        this.solicitud = solicitud;
    }   

    public CmtBasicaMgl getBasicaIdTipoTrabajo() {
        return basicaIdTipoTrabajo;
    }

    public void setBasicaIdTipoTrabajo(CmtBasicaMgl basicaIdTipoTrabajo) {
        this.basicaIdTipoTrabajo = basicaIdTipoTrabajo;
    }

    public CmtBasicaMgl getBasicaIdTecnologia() {
        return basicaIdTecnologia;
    }

    public void setBasicaIdTecnologia(CmtBasicaMgl basicaIdTecnologia) {
        this.basicaIdTecnologia = basicaIdTecnologia;
    }

    public CmtBasicaMgl getClaseOrdenTrabajo() {
        return claseOrdenTrabajo;
    }

    public void setClaseOrdenTrabajo(CmtBasicaMgl claseOrdenTrabajo) {
        this.claseOrdenTrabajo = claseOrdenTrabajo;
    }

    public BigDecimal getOnyxOtHija() {
        return onyxOtHija;
    }

    public void setOnyxOtHija(BigDecimal onyxOtHija) {
        this.onyxOtHija = onyxOtHija;
    }

    public String getPersonaRecVt() {
        return personaRecVt;
    }

    public void setPersonaRecVt(String personaRecVt) {
        this.personaRecVt = personaRecVt;
    }

    public String getDocPerRecVt() {
        return docPerRecVt;
    }

    public void setDocPerRecVt(String docPerRecVt) {
        this.docPerRecVt = docPerRecVt;
    }

    public String getTelPerRecVt() {
        return telPerRecVt;
    }

    public void setTelPerRecVt(String telPerRecVt) {
        this.telPerRecVt = telPerRecVt;
    }

    public String getPrerequisitosVT() {
        return prerequisitosVT;
    }

    public void setPrerequisitosVT(String prerequisitosVT) {
        this.prerequisitosVT = prerequisitosVT;
    }

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }

    public String getEmailPerRecVT() {
        return emailPerRecVT;
    }

    public void setEmailPerRecVT(String emailPerRecVT) {
        this.emailPerRecVT = emailPerRecVT;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    /**
     * @return the disponibilidadGestion
     */
    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    /**
     * @param disponibilidadGestion the disponibilidadGestion to set
     */
    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }

    public BigDecimal getEstadoGeneralOt() {
        return estadoGeneralOt;
    }

    public void setEstadoGeneralOt(BigDecimal estadoGeneralOt) {
        this.estadoGeneralOt = estadoGeneralOt;
    }

    public String getEnlaceId() {
        return enlaceId;
    }

    public void setEnlaceId(String enlaceId) {
        this.enlaceId = enlaceId;
    }
    
    
    
}
