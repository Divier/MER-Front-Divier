
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_ESTADOVT
 *
 *alejandro.martinez.ext@claro.com.co
 * 
 * @versión 1.00.0000
 */

@Entity
@Table(name = "CMT_ESTADO_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstadoVtMgl.findAll", query = "SELECT c FROM CmtEstadoVtMgl c")})
public class CmtEstadoVtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO_VT")
    private BigDecimal idEstadoVt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESC_ESTADO_VT")
    private String descEstadoVt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PERFIL_CREACION")
    private String perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 200)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Size(max = 200)
    @Column(name = "PERFIL_EDICION")
    private String perfilEdicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DETALLE")
    private String detalle;

    public CmtEstadoVtMgl() {
    }

    /**
     * @return the idEstadoVt
     */
    public BigDecimal getIdEstadoVt() {
        return idEstadoVt;
    }

    /**
     * @param idEstadoVt the idEstadoVt to set
     */
    public void setIdEstadoVt(BigDecimal idEstadoVt) {
        this.idEstadoVt = idEstadoVt;
    }

    /**
     * @return the descEstadoVt
     */
    public String getDescEstadoVt() {
        return descEstadoVt;
    }

    /**
     * @param descEstadoVt the descEstadoVt to set
     */
    public void setDescEstadoVt(String descEstadoVt) {
        this.descEstadoVt = descEstadoVt;
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
    public String getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(String perfilCreacion) {
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
    public String getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(String perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
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
}