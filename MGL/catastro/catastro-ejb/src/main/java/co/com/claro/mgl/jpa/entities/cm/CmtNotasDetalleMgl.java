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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_NOTAS_DETALLE", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNotasDetalleMgl.findAll", query = "SELECT n FROM CmtNotasDetalleMgl n ")})
public class CmtNotasDetalleMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtNotasDetalleMgl.CmtNotasDetalleMglSq",
            sequenceName = "MGL_SCHEME.CMT_NOTAS_DETALLE_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNotasDetalleMgl.CmtNotasDetalleMglSq")
    @Column(name = "NOTAS_DETALLE_ID", nullable = false)
    private BigDecimal notasDetalleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTAS_ID")
    private CmtNotasMgl notaObj;
    @Column(name = "NOTA", nullable = true, length = 4000)
    private String nota;
    @Column(name = "NOMBRE_ARCHIVO", nullable = true, length = 200)
    private String nombreArchivo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "DETALLE", nullable = false, length = 20)
    private String detalle;
    @OneToMany(mappedBy = "notasDetalleObj", fetch = FetchType.LAZY)
    List<CmtNotasDetalleAuditoriaMgl> listAuditoria;

    /**
     * @return the notasDetalleId
     */
    public BigDecimal getNotasDetalleId() {
        return notasDetalleId;
    }

    /**
     * @param notasDetalleId the notasDetalleId to set
     */
    public void setNotasDetalleId(BigDecimal notasDetalleId) {
        this.notasDetalleId = notasDetalleId;
    }

    /**
     * @return the notaObj
     */
    public CmtNotasMgl getNotaObj() {
        return notaObj;
    }

    /**
     * @param notaObj the notaObj to set
     */
    public void setNotaObj(CmtNotasMgl notaObj) {
        this.notaObj = notaObj;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public List<CmtNotasDetalleAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtNotasDetalleAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }
    
    @PrePersist
    public void prePersist() {
        if (this.detalle == null || this.detalle.trim().isEmpty()) {
            this.detalle = "CREACION";
        }
        this.estadoRegistro = 1;
    }

}