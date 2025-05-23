/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_OT_DIRECCION$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class OtHhppMglAuditoria implements Serializable{
    
     private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "OT_AUD_DIRECCION_ID", nullable = false)
    private BigDecimal otAudDirId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", nullable = false)
    private OtHhppMgl otHhppMgl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_DIRECCION_ID", referencedColumnName = "TIPO_OT_ID", nullable = false)
    private TipoOtHhppMgl tipoOtHhppId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_GENERAL", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoGeneral;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_INTERNO_INICIAL", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInternoInicial;
    
    @NotNull
    @Column(name = "NOMBRE_CONTACTO")
    private String nombreContacto;
    @NotNull
    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;
    @NotNull
    @Column(name = "CORREO_CONTACTO")
    private String correoContacto;
    @Column(name = "FECHA_CREACION_OT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacionOt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID", referencedColumnName = "DIRECCION_ID")
    private DireccionMgl direccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_DIRECCION_ID", referencedColumnName = "SUB_DIRECCION_ID")
    private SubDireccionMgl subDireccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEC_SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID")
    private Solicitud solicitudId;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO_HHPP")
    private CmtBasicaMgl basicaIdTipoTrabajo;
   
    @Column(name = "ONYX_OT_HIJA", nullable = true)
    private BigDecimal onyxOtHija;
    @Column(name = "COMPLEJIDAD_SERVICIO")
    private String complejidadServicio;
    @Column(name = "DISPONIBILIDAD_GESTION")
    private String disponibilidadGestion;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO")
    private CmtBasicaMgl segmento;
    @Column(name = "ENLACE_ID")
    private String enlaceId;
    
    public BigDecimal getOtAudDirId() {
        return otAudDirId;
    }

    public void setOtAudDirId(BigDecimal otAudDirId) {
        this.otAudDirId = otAudDirId;
    }

    public OtHhppMgl getOtHhppMgl() {
        return otHhppMgl;
    }
    public BigDecimal getOtHhppId() {
        return otHhppMgl.getOtHhppId();
    }

    public void setOtHhppMgl(OtHhppMgl otHhppMgl) {
        this.otHhppMgl = otHhppMgl;
    }

    public TipoOtHhppMgl getTipoOtHhppId() {
        return tipoOtHhppId;
    }

    public void setTipoOtHhppId(TipoOtHhppMgl tipoOtHhppId) {
        this.tipoOtHhppId = tipoOtHhppId;
    }

    public CmtBasicaMgl getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(CmtBasicaMgl estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public CmtBasicaMgl getEstadoInternoInicial() {
        return estadoInternoInicial;
    }

    public void setEstadoInternoInicial(CmtBasicaMgl estadoInternoInicial) {
        this.estadoInternoInicial = estadoInternoInicial;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Date getFechaCreacionOt() {
        return fechaCreacionOt;
    }

    public void setFechaCreacionOt(Date fechaCreacionOt) {
        this.fechaCreacionOt = fechaCreacionOt;
    }

    public DireccionMgl getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(DireccionMgl direccionId) {
        this.direccionId = direccionId;
    }

    public SubDireccionMgl getSubDireccionId() {
        return subDireccionId;
    }

    public void setSubDireccionId(SubDireccionMgl subDireccionId) {
        this.subDireccionId = subDireccionId;
    }

    public Solicitud getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Solicitud solicitudId) {
        this.solicitudId = solicitudId;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtBasicaMgl getBasicaIdTipoTrabajo() {
        return basicaIdTipoTrabajo;
    }

    public void setBasicaIdTipoTrabajo(CmtBasicaMgl basicaIdTipoTrabajo) {
        this.basicaIdTipoTrabajo = basicaIdTipoTrabajo;
    }

  

    public BigDecimal getOnyxOtHija() {
        return onyxOtHija;
    }

    public void setOnyxOtHija(BigDecimal onyxOtHija) {
        this.onyxOtHija = onyxOtHija;
    }

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }
    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }
    public CmtBasicaMgl getSegmento() {
        return segmento;
    }

    public void setSegmento(CmtBasicaMgl segmento) {
        this.segmento = segmento;
    }
    public String getEnlaceId() {
        return enlaceId;
    }

    public void setEnlaceId(String enlaceId) {
        this.enlaceId = enlaceId;
    }
    
    
}
