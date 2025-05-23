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
 * @author Admin
 */
@Entity
@Table(name = "MGL_BASICA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtBasicaAuditoriaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BASICAAUD_ID", nullable = false)
    private BigDecimal basicaAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID", nullable = false)
    private CmtBasicaMgl basicaObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_BASICA_ID")
    private CmtTipoBasicaMgl tipoBasicaObj;
    
    @NotNull
    @Column(name = "CODIGO_BASICA")
    private String codigoBasica;
    
    @Basic(optional = false)
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    
    @Basic(optional = false)
    @Column(name = "NOMBRE_BASICA", nullable = true, length = 100)
    private String nombreBasica;
    
    @Basic(optional = false)
    @Column(name = "DESCRIPCION", nullable = true, length = 500)
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
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
    
    @Column(name = "ACTIVADO")
    private String activado;
    
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;

    /**
     * @return the tipoBasicaObj
     */
    public CmtTipoBasicaMgl getTipoBasicaObj() {
        return tipoBasicaObj;
    }

    /**
     * @param tipoBasicaObj the tipoBasicaObj to set
     */
    public void setTipoBasicaObj(CmtTipoBasicaMgl tipoBasicaObj) {
        this.tipoBasicaObj = tipoBasicaObj;
    }

    /**
     * @return the codigoBasica
     */
    public String getCodigoBasica() {
        return codigoBasica;
    }

    /**
     * @param codigoBasica the codigoBasica to set
     */
    public void setCodigoBasica(String codigoBasica) {
        this.codigoBasica = codigoBasica;
    }

    /**
     * @return the abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * @param abreviatura the abreviatura to set
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * @return the nombreBasica
     */
    public String getNombreBasica() {
        return nombreBasica;
    }

    /**
     * @param nombreBasica the nombreBasica to set
     */
    public void setNombreBasica(String nombreBasica) {
        this.nombreBasica = nombreBasica;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public BigDecimal getBasicaAudId() {
        return basicaAuditoriaId;
    }

    public BigDecimal getBasicaAuditoriaId() {
        return basicaAuditoriaId;
    }

    public void setBasicaAuditoriaId(BigDecimal basicaAuditoriaId) {
        this.basicaAuditoriaId = basicaAuditoriaId;
    }

    public void setBasicaObj(CmtBasicaMgl basicaObj) {
        this.basicaObj = basicaObj;
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

    public BigDecimal getBasicaId() {
        return basicaObj.getBasicaId();
    }

    public String getActivado() {
        return activado;
    }

    public void setActivado(String eliminado) {
        this.activado = eliminado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }
}
