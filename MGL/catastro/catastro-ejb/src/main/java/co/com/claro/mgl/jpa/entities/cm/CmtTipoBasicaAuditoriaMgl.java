/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_TIPO_BASICA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtTipoBasicaAuditoriaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_AUDBASICA_ID", nullable = false)
    private BigDecimal basicaAuditoriaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_BASICA_ID", nullable = false)
    private CmtTipoBasicaMgl tipoBasicaObj;
    @Column(name = "NOMBRE_TIPO", nullable = false, length = 200)
    private String nombreTipo;
    @Column(name = "CODIGO_TIPO", nullable = false, length = 20)
    private String codigoTipo;
    @Column(name = "DESCRIPCION_TIPO", nullable = false, length = 1000)
    private String descripcionTipo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;    
    @Column(name = "LONGITUD_CODIGO")
    private int longitudCodigo;
    @Column(name = "TIPO_DATO_CODIGO", length = 2)
    private String tipoDatoCodigo;
    @Column(name = "INICIAL_ABREVIATURA", length = 1)
    private String inicialesAbreviatura;
    @Column(name = "JUSTIFICACION", nullable = false, length = 1000)
    private String justificacion;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "MODULO")
    private String modulo;
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;    
    @Transient
    private String moduloVista;
   

    public BigDecimal getBasicaAuditoriaId() {
        return basicaAuditoriaId;
    }

    public void setBasicaAuditoriaId(BigDecimal basicaAuditoriaId) {
        this.basicaAuditoriaId = basicaAuditoriaId;
    }

    public CmtTipoBasicaMgl getTipoBasicaObj() {
        return tipoBasicaObj;
    }

    
        
    
    public void setTipoBasicaObj(CmtTipoBasicaMgl tipoBasicaObj) {
        this.tipoBasicaObj = tipoBasicaObj;
    }

/**
     * @return the tipoBasicaId
     */
    public BigDecimal getTipoBasicaId() {
        return tipoBasicaObj.getTipoBasicaId();
    }
    
    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
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

    public int getLongitudCodigo() {
        return longitudCodigo;
    }

    public void setLongitudCodigo(int longitudCodigo) {
        this.longitudCodigo = longitudCodigo;
    }

    public String getTipoDatoCodigo() {
        return tipoDatoCodigo;
    }

    public void setTipoDatoCodigo(String tipoDatoCodigo) {
        this.tipoDatoCodigo = tipoDatoCodigo;
    }

    public String getInicialesAbreviatura() {
        return inicialesAbreviatura;
    }

    public void setInicialesAbreviatura(String inicialesAbreviatura) {
        this.inicialesAbreviatura = inicialesAbreviatura;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getModuloVista() {
        return moduloVista;
    }

    public void setModuloVista(String moduloVista) {
        this.moduloVista = moduloVista;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }
    
}
