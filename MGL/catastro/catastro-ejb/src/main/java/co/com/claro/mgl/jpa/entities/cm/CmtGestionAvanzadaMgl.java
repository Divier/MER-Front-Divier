
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_GESTIONAVANZADA
 *
 * alejandro.martinez.ext@claro.com.co
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_GESTIONAVANZADA",schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtGestionAvanzadaMgl.findAll", query = "SELECT c FROM CmtGestionAvanzadaMgl c")})
public class CmtGestionAvanzadaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtGestionAvanzadaMgl.CmtGestionAvanzadaSq",
            sequenceName = "MGL_SCHEME.CMT_GESTIONAVANZADA_SQ",
            allocationSize = 1)
    @GeneratedValue(generator =
            "CmtGestionAvanzadaMgl.CmtGestionAvanzadaSq")
    @Column(name = "GESTION_AVANZADA_ID")
    private BigDecimal idGestionAvanzada;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OT")
    private CmtOrdenTrabajoMgl otObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cmObj;
    @Column(name = "OBSERVACIONGES", nullable = true, length = 200)
    private String observaciones;
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

    public CmtGestionAvanzadaMgl() {
    }

    /**
     * @return the idGestionAvanzada
     */
    public BigDecimal getIdGestionAvanzada() {
        return idGestionAvanzada;
    }

    /**
     * @param idGestionAvanzada the idGestionAvanzada to set
     */
    public void setIdGestionAvanzada(BigDecimal idGestionAvanzada) {
        this.idGestionAvanzada = idGestionAvanzada;
    }

    /**
     * @return the otObj
     */
    public CmtOrdenTrabajoMgl getOtObj() {
        return otObj;
    }

    /**
     * @param otObj the otObj to set
     */
    public void setOtObj(CmtOrdenTrabajoMgl otObj) {
        this.otObj = otObj;
    }

    /**
     * @return the cmObj
     */
    public CmtCuentaMatrizMgl getCmObj() {
        return cmObj;
    }

    /**
     * @param cmObj the cmObj to set
     */
    public void setCmObj(CmtCuentaMatrizMgl cmObj) {
        this.cmObj = cmObj;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
}
