/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_NOTAS_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNotasSolicitudMgl.findAll", query = "SELECT n FROM CmtNotasSolicitudMgl n ")})
public class CmtNotasSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtNotasSolicitudMgl.CmtNotasSolicitudMglSeq",
            sequenceName = "MGL_SCHEME.CMT_NOTAS_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNotasSolicitudMgl.CmtNotasSolicitudMglSeq")
    @Column(name = "NOTAS_ID", nullable = false)
    private BigDecimal notasId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_NOTA", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoNotaObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl solicitudCm;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
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
    @Column(name = "NOTA")
    private String nota;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @OneToMany(mappedBy = "notaSolicitud", fetch = FetchType.LAZY)
    @OrderBy("notasDetalleId ASC")
    private List<CmtNotasSolicitudDetalleMgl> comentarioList;
    
    
    
    @Transient
    private String tipoNota;
    @Transient
    private String ultimoUsuario;
    @Transient
    private String areaUsuario;
    @Transient
    private String telefonoUsuario;
    @Transient
    private String linkNota;
    
    @Transient
    private BigDecimal idUsuarioModificacion;
    @Transient
    private BigDecimal idUsuario;

    public BigDecimal getNotasId() {
        return notasId;
    }

    public void setNotasId(BigDecimal notasId) {
        this.notasId = notasId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public CmtBasicaMgl getTipoNotaObj() {
        return tipoNotaObj;
    }

    public void setTipoNotaObj(CmtBasicaMgl tipoNotaObj) {
        this.tipoNotaObj = tipoNotaObj;
    }

    public CmtSolicitudCmMgl getSolicitudCm() {
        return solicitudCm;
    }

    public void setSolicitudCm(CmtSolicitudCmMgl solicitudCmId) {
        this.solicitudCm = solicitudCmId;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    public String getAreaUsuario() {
        return areaUsuario;
    }

    public void setAreaUsuario(String areaUsuario) {
        this.areaUsuario = areaUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getLinkNota() {
        return linkNota;
    }

    public void setLinkNota(String linkNota) {
        this.linkNota = linkNota;
    }

    public BigDecimal getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(BigDecimal idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<CmtNotasSolicitudDetalleMgl> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<CmtNotasSolicitudDetalleMgl> comentarioList) {
        this.comentarioList = comentarioList;
    }
    
    public CmtNotasSolicitudMgl crearNotSol(CmtNotasSolicitudMgl cmtNotasSolicitudMgl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
