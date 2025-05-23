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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_NOTA_OT
 *
 * Johnnatan Ortiz
 *
 * @version 1.00.0000
 */
@Entity
@Table(name = "CMT_NOTA_OT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNotaOtMgl.findAll", query = "SELECT n FROM CmtNotaOtMgl n "),
    @NamedQuery(name = "CmtNotaOtMgl.findByOt", query = "SELECT n FROM CmtNotaOtMgl n WHERE n.ordenTrabajoObj = :ordenTrabajo"),
    @NamedQuery(name = "CmtNotaOtMgl.findByCm", query = "SELECT n FROM CmtNotaOtMgl n WHERE n.estadoRegistro = 1 AND(:tipoNota IS NULL or n.tipoNotaObj.basicaId = :tipoNota) AND n.ordenTrabajoObj.cmObj = :cuentaMatriz ORDER BY n.notaOtId ASC")
})
public class CmtNotaOtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtNotaOtMgl.cmtNotaOtMglSq",
            sequenceName = "MGL_SCHEME.CMT_NOTA_OT_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNotaOtMgl.cmtNotaOtMglSq")
    @Column(name = "NOTA_OT_ID", nullable = false)
    private BigDecimal notaOtId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_NOTA", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoNotaObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID")
    private CmtOrdenTrabajoMgl ordenTrabajoObj;
    @Column(name = "DESCRIPCION", nullable = false, length = 200)
    private String descripcion;
    @Column(name = "NOTA", nullable = false, length = 4000)
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
    @Column(name = "PERFIL_EDICION", nullable = true)
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @OneToMany(mappedBy = "notaOtObj", fetch = FetchType.LAZY)
    @OrderBy("notaOtComentarioId ASC")
    private List<CmtNotaOtComentarioMgl> comentarioList;

    /**
     * @return the notaOtId
     */
    public BigDecimal getNotaOtId() {
        return notaOtId;
    }

    /**
     * @param notaOtId the notasId to set
     */
    public void setNotaOtId(BigDecimal notaOtId) {
        this.notaOtId = notaOtId;
    }

    /**
     * @return the tipoNotaObj
     */
    public CmtBasicaMgl getTipoNotaObj() {
        return tipoNotaObj;
    }

    /**
     * @param tipoNotaObj the tipoNotaObj to set
     */
    public void setTipoNotaObj(CmtBasicaMgl tipoNotaObj) {
        this.tipoNotaObj = tipoNotaObj;
    }

    /**
     * @return the ordenTrabajoObj
     */
    public CmtOrdenTrabajoMgl getOrdenTrabajoObj() {
        return ordenTrabajoObj;
    }

    /**
     * @param ordenTrabajoObj the subEdificioObj to set
     */
    public void setOrdenTrabajoObj(CmtOrdenTrabajoMgl ordenTrabajoObj) {
        this.ordenTrabajoObj = ordenTrabajoObj;
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

    public List<CmtNotaOtComentarioMgl> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<CmtNotaOtComentarioMgl> comentarioList) {
        this.comentarioList = comentarioList;
    }
    
    @PrePersist
    public void prePersist() {
        this.estadoRegistro = 1;
    }
 
}
