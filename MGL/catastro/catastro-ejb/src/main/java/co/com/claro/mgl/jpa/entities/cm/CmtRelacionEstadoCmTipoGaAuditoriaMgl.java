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
 * Entidad de la tabla CMT_RELACION_ESTADO_CM_TIPO_GA que contiene los campos,
 * Código, Estado CM, Tipo GA, Descripcion y Estado, para especificar la
 * relacion entre el primer y tercer campo.
 * 
* @author alejandro.martine.ext@claro.com.co
 * @versión 1.0
 */
@Entity
@Table(name = "CMT_ESTADO_CM_TIPO_GA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtRelacionEstadoCmTipoGaAuditoriaMgl implements Serializable {

    @Id
    @Basic(optional = false)

    @Column(name = "ESTADO_CM_TIPO_GAAUD_ID", nullable = false)
    private BigDecimal estadoCmTipoGaAuditoriaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_CM_TIPO_GA_ID", nullable = false)
    private CmtRelacionEstadoCmTipoGaMgl estadoCmTipoGaObj;

    @Column(name = "CODIGO", nullable = false, length = 10)
    private String codigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_CM",
            referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl basicaEstadoCMObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_GA",
            referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl basicaTiipoGa;
    @Column(name = "DESCRIPCION", nullable = true, length = 500)
    private String descripcion;
    @Column(name = "ACTIVADO", nullable = false, length = 1)
    private String estado;
    @Column(name = "JUSTIFICACION", nullable = true, length = 500)
    private String justificacion;
    @Column(name = "DETALLE", nullable = false, length = 20)
    private String detalleOperacion;
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
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;

    public BigDecimal getEstadoCmTipoGaAuditoriaId() {
        return estadoCmTipoGaAuditoriaId;
    }

    public void setEstadoCmTipoGaAuditoriaId(BigDecimal estadoCmTipoGaAuditoriaId) {
        this.estadoCmTipoGaAuditoriaId = estadoCmTipoGaAuditoriaId;
    }

    
    public CmtRelacionEstadoCmTipoGaMgl getEstadoCmTipoGaObj() {
        return estadoCmTipoGaObj;
    }

    public void setEstadoCmTipoGaObj(CmtRelacionEstadoCmTipoGaMgl estadoCmTipoGaObj) {
        this.estadoCmTipoGaObj = estadoCmTipoGaObj;
    }

    public BigDecimal getEstadoCmTipoGaId() {
        return estadoCmTipoGaObj.getEstadoCmTipoGaId();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String Codigo) {
        this.codigo = Codigo;
    }

    public CmtBasicaMgl getBasicaEstadoCMObj() {
        return basicaEstadoCMObj;
    }

    public void setBasicaEstadoCMObj(CmtBasicaMgl basicaEstadoCMObj) {
        this.basicaEstadoCMObj = basicaEstadoCMObj;
    }

    public CmtBasicaMgl getBasicaTiipoGa() {
        return basicaTiipoGa;
    }

    public void setBasicaTiipoGa(CmtBasicaMgl basicaTiipoGa) {
        this.basicaTiipoGa = basicaTiipoGa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getDetalleOperacion() {
        return detalleOperacion;
    }

    public void setDetalleOperacion(String detalleOperacion) {
        this.detalleOperacion = detalleOperacion;
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
