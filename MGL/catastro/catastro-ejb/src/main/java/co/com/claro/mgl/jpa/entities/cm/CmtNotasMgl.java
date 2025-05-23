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
@Table(name = "CMT_NOTAS", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNotasMgl.findAll", query = "SELECT n FROM CmtNotasMgl n "),
    @NamedQuery(name = "CmtNotasMgl.findBySubEdificio", query = "SELECT n FROM CmtNotasMgl n WHERE n.subEdificioObj.subEdificioId = :subEdificio AND n.estadoRegistro = 1 ORDER BY n.fechaCreacion DESC"),
    @NamedQuery(name = "CmtNotasMgl.getCountBySubEdificio", query = "SELECT COUNT(DISTINCT c) FROM CmtNotasMgl c  WHERE c.subEdificioObj.subEdificioId = :subEdificio AND c.estadoRegistro = 1 ORDER BY c.fechaCreacion DESC")
})
public class CmtNotasMgl
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtNotasMgl.cmtNotasMglSq",
            sequenceName = "MGL_SCHEME.CMT_NOTAS_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNotasMgl.cmtNotasMglSq")
    @Column(name = "NOTAS_ID", nullable = false)
    private BigDecimal notasId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_NOTA")
    private CmtBasicaMgl tipoNotaObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;
    @Column(name = "DESCRIPCION", nullable = true, length = 200)
    private String descripcion;
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
    @OneToMany(mappedBy = "notaObj", fetch = FetchType.LAZY)
    private List<CmtNotasDetalleMgl> comentarioList;
    @OneToMany(mappedBy = "notasId", fetch = FetchType.LAZY)
    private List<CmtNotasAuditoriaMgl> listAuditoria;

    /**
     * @return the notasId
     */
    public BigDecimal getNotasId() {
        return notasId;
    }

    /**
     * @param notasId the notasId to set
     */
    public void setNotasId(BigDecimal notasId) {
        this.notasId = notasId;
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
     * @return the subEdificioObj
     */
    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    /**
     * @param subEdificioObj the subEdificioObj to set
     */
    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
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

    /**
     * @return the comentarioList
     */
    public List<CmtNotasDetalleMgl> getComentarioList() {
        return comentarioList;
    }

    /**
     * @param comentarioList the comentarioList to set
     */
    public void setComentarioList(List<CmtNotasDetalleMgl> comentarioList) {
        this.comentarioList = comentarioList;
    }

  

    public List<CmtNotasAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(
            List<CmtNotasAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

}
