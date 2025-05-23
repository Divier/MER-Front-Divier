package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "CMT_GRUPO_PROYECTO_VAL", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtGrupoProyectoValidacionMgl.findAll", query = "SELECT c FROM CmtGrupoProyectoValidacionMgl c WHERE c.estadoRegistro=:estado Order by c.grupoProyectoId DESC")})
public class CmtGrupoProyectoValidacionMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtGrupoProyectoValidacionMgl.CmtGrupoProyectoValidacionMglSq",
            sequenceName = "MGL_SCHEME.CMT_GRUPO_PROYECTO_VAL_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtGrupoProyectoValidacionMgl.CmtGrupoProyectoValidacionMglSq")
    @Column(name = "GRUPO_PROYECTO_ID", nullable = false)
    private BigDecimal grupoProyectoId;
    @Column(name = "GRUPO_PROYECTO_BASICA_ID", nullable = false)
    private BigDecimal grupoProyectoBasicaId;
    @Column(name = "PROYECTO_BASICA_ID", nullable = false)
    private BigDecimal proyectoBasicaId;
    @NotNull
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
    @Transient
    private String abreviaturaGrupoProyecto;
    @Transient
    private String nombreGrupoProyecto;
    @Transient
    private String abreviaturaProyectoBasica;
    @Transient
    private String nombreProyectoBasica;

    public BigDecimal getGrupoProyectoId() {
        return grupoProyectoId;
    }

    public void setGrupoProyectoId(BigDecimal grupoProyectoId) {
        this.grupoProyectoId = grupoProyectoId;
    }

    public BigDecimal getGrupoProyectoBasicaId() {
        return grupoProyectoBasicaId;
    }

    public void setGrupoProyectoBasicaId(BigDecimal grupoProyectoBasicaId) {
        this.grupoProyectoBasicaId = grupoProyectoBasicaId;
    }

    public BigDecimal getProyectoBasicaId() {
        return proyectoBasicaId;
    }

    public void setProyectoBasicaId(BigDecimal proyectoBasicaId) {
        this.proyectoBasicaId = proyectoBasicaId;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
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

    @Override
    public CmtGrupoProyectoValidacionMgl clone() throws CloneNotSupportedException {
        return (CmtGrupoProyectoValidacionMgl) super.clone();
    }

    public String getAbreviaturaGrupoProyecto() {
        return abreviaturaGrupoProyecto;
    }

    public void setAbreviaturaGrupoProyecto(String abreviaturaGrupoProyecto) {
        this.abreviaturaGrupoProyecto = abreviaturaGrupoProyecto;
    }

    public String getNombreGrupoProyecto() {
        return nombreGrupoProyecto;
    }

    public void setNombreGrupoProyecto(String nombreGrupoProyecto) {
        this.nombreGrupoProyecto = nombreGrupoProyecto;
    }

    public String getAbreviaturaProyectoBasica() {
        return abreviaturaProyectoBasica;
    }

    public void setAbreviaturaProyectoBasica(String abreviaturaProyectoBasica) {
        this.abreviaturaProyectoBasica = abreviaturaProyectoBasica;
    }

    public String getNombreProyectoBasica() {
        return nombreProyectoBasica;
    }

    public void setNombreProyectoBasica(String nombreProyectoBasica) {
        this.nombreProyectoBasica = nombreProyectoBasica;
    }

}
