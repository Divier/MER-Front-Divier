/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_ARCHIVOS_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtArchivosVtMgl.findAll", query = "SELECT c FROM CmtArchivosVtMgl c "
            + "WHERE c.estadoRegistro = 1 ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdVt",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.visitaTecnicaMglobj "
            + " = :visitaTecnicaMglobj AND c.estadoRegistro = 1 AND c.archivoAge IS NULL"
            + " ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdOtOrigenCO",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.ordenTrabajoMglobj "
            + " = :ordenTrabajoMglobj AND c.estadoRegistro = 1 AND c.origenArchivos = :origenArchivo "
            + " AND c.archivoAge IS NULL ORDER BY c.idArchivosVt ASC "),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdOtAndAge",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.ordenTrabajoMglobj "
            + " = :ordenTrabajoMglobj AND c.estadoRegistro = 1 AND c.archivoAge = :actividadAge "
            + "ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdOtHhppAndAge",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.ordenTrabajoHhppMglobj "
            + " = :ordenTrabajoHhppMglobj AND c.estadoRegistro = 1 AND c.archivoAge = :actividadAge "
            + "ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByMaximoSecOt",
            query = "SELECT MAX(a.secArchivo) FROM CmtArchivosVtMgl a WHERE a.ordenTrabajoMglobj = :ordenTrabajoMglobj  AND "
            + "a.origenArchivos = :origenArchivos"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByMaximoSecOtHhpp",
            query = "SELECT MAX(a.secArchivo) FROM CmtArchivosVtMgl a WHERE a.ordenTrabajoHhppMglobj = :ordenTrabajoHhppMglobj  AND "
            + "a.origenArchivos = :origenArchivos"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByMaximoSecVt",
            query = "SELECT MAX(a.secArchivo) FROM CmtArchivosVtMgl a WHERE a.visitaTecnicaMglobj = :visitaTecnicaMglobj AND "
            + "a.origenArchivos = :origenArchivos"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdSol",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.solicitudCmMgl "
            + " = :solicitudCmMgl AND c.estadoRegistro = 1 AND c.archivoAge IS NULL "
            + " ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdVtxOrigen",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.visitaTecnicaMglobj "
            + " = :visitaTecnicaMglobj AND c.origenArchivos = :origenArchivo AND c.estadoRegistro = 1 AND c.archivoAge IS NULL"
            + " ORDER BY c.idArchivosVt ASC"),
    @NamedQuery(name = "CmtArchivosVtMgl.findByIdSolHhpp",
            query = "SELECT c FROM CmtArchivosVtMgl c WHERE c.solicitudHhpp "
            + " = :solicitudHhpp AND c.estadoRegistro = 1 AND c.archivoAge IS NULL "
            + " ORDER BY c.idArchivosVt ASC")})
public class CmtArchivosVtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtArchivosVtMgl.CmtArchivosVtSq",
            sequenceName = "MGL_SCHEME.CMT_ARCHIVOS_VT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtArchivosVtMgl.CmtArchivosVtSq")
    @Column(name = "ARCHIVOS_VT_ID")
    private BigDecimal idArchivosVt;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VT_ID", referencedColumnName = "VT_ID", nullable = false)
    private CmtVisitaTecnicaMgl visitaTecnicaMglobj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID", referencedColumnName = "OT_ID", nullable = false)
    private CmtOrdenTrabajoMgl ordenTrabajoMglobj;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RUTA_ARCHIVO")
    private String rutaArchivo;
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "SECUENCIA_ARCHIVO")
    private int secArchivo;
    @Column(name = "ARCHIVO_AGENDA")
    private BigDecimal archivoAge;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID", referencedColumnName = "SOLICITUD_CM_ID", nullable = false)
    private CmtSolicitudCmMgl solicitudCmMgl;
    @Column(name = "ORIGEN_ARCH")
    private String origenArchivos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", referencedColumnName = "OT_DIRECCION_ID", nullable = false)
    private OtHhppMgl ordenTrabajoHhppMglobj; 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)
    private Solicitud  solicitudHhpp;

    public BigDecimal getIdArchivosVt() {
        return idArchivosVt;
    }

    public void setIdArchivosVt(BigDecimal idArchivosVt) {
        this.idArchivosVt = idArchivosVt;
    }

    public CmtVisitaTecnicaMgl getVisitaTecnicaMglobj() {
        return visitaTecnicaMglobj;
    }

    public void setVisitaTecnicaMglobj(CmtVisitaTecnicaMgl visitaTecnicaMglobj) {
        this.visitaTecnicaMglobj = visitaTecnicaMglobj;
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

    public CmtOrdenTrabajoMgl getOrdenTrabajoMglobj() {
        return ordenTrabajoMglobj;
    }

    public void setOrdenTrabajoMglobj(CmtOrdenTrabajoMgl ordenTrabajoMglobj) {
        this.ordenTrabajoMglobj = ordenTrabajoMglobj;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getSecArchivo() {
        return secArchivo;
    }

    public void setSecArchivo(int secArchivo) {
        this.secArchivo = secArchivo;
    }

    public BigDecimal getArchivoAge() {
        return archivoAge;
    }

    public void setArchivoAge(BigDecimal archivoAge) {
        this.archivoAge = archivoAge;
    }

    public CmtSolicitudCmMgl getSolicitudCmMgl() {
        return solicitudCmMgl;
    }

    public void setSolicitudCmMgl(CmtSolicitudCmMgl solicitudCmMgl) {
        this.solicitudCmMgl = solicitudCmMgl;
    }

    public String getOrigenArchivos() {
        return origenArchivos;
    }

    public void setOrigenArchivos(String origenArchivos) {
        this.origenArchivos = origenArchivos;
    }

    public OtHhppMgl getOrdenTrabajoHhppMglobj() {
        return ordenTrabajoHhppMglobj;
    }

    public void setOrdenTrabajoHhppMglobj(OtHhppMgl ordenTrabajoHhppMglobj) {
        this.ordenTrabajoHhppMglobj = ordenTrabajoHhppMglobj;
    }

    public Solicitud getSolicitudHhpp() {
        return solicitudHhpp;
    }

    public void setSolicitudHhpp(Solicitud solicitudHhpp) {
        this.solicitudHhpp = solicitudHhpp;
    }
    
    

}
