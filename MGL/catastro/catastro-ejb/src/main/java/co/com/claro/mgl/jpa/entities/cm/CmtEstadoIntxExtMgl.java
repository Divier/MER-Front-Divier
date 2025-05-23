package co.com.claro.mgl.jpa.entities.cm;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_ESTADOINTXEXT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ESTADOINTXEXT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstadoIntxExtMgl.findAll", query = "SELECT c FROM CmtEstadoIntxExtMgl c where c.estadoRegistro =:estado"),
    @NamedQuery(name = "CmtEstadoIntxExtMgl.findByEstadoInt", query = "SELECT c FROM CmtEstadoIntxExtMgl c WHERE c.idEstadoInt = :estadoInterno  AND  c.estadoRegistro = 1 order by c.idEstadoInt.nombreBasica ASC")})
public class CmtEstadoIntxExtMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtEstadoIntxExtMgl.CmtEstadoIntXExtSq",
            sequenceName = "MGL_SCHEME.CMT_ESTADOINTXEXT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtEstadoIntxExtMgl.CmtEstadoIntXExtSq")
    @Size(min = 1, max = 30)
    @Column(name = "ESTADO_INT_EXT_ID")
    private BigDecimal idEstadoIntExt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_INT")
    private CmtBasicaMgl idEstadoInt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_EXT")
    private CmtBasicaMgl idEstadoExt;
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;
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

    public CmtEstadoIntxExtMgl() {
    }

    /**
     * @return the idEstadoIntExt
     */
    public BigDecimal getIdEstadoIntExt() {
        return idEstadoIntExt;
    }

    /**
     * @param idEstadoIntExt the idEstadoIntExt to set
     */
    public void setIdEstadoIntExt(BigDecimal idEstadoIntExt) {
        this.idEstadoIntExt = idEstadoIntExt;
    }

    /**
     * @return the idEstadoInt
     */
    public CmtBasicaMgl getIdEstadoInt() {
        return idEstadoInt;
    }

    /**
     * @param idEstadoInt the idEstadoInt to set
     */
    public void setIdEstadoInt(CmtBasicaMgl idEstadoInt) {
        this.idEstadoInt = idEstadoInt;
    }

    /**
     * @return the idEstadoExt
     */
    public CmtBasicaMgl getIdEstadoExt() {
        return idEstadoExt;
    }

    /**
     * @param idEstadoExt the idEstadoExt to set
     */
    public void setIdEstadoExt(CmtBasicaMgl idEstadoExt) {
        this.idEstadoExt = idEstadoExt;
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
    
    @Override
    public CmtEstadoIntxExtMgl clone() throws CloneNotSupportedException {
        return (CmtEstadoIntxExtMgl) super.clone();
    }
}
