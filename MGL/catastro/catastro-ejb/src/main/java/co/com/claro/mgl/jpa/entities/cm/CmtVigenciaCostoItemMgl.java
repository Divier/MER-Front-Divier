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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_VIGENCIACOSTOITEM
 *
 * alejandro.martinez.ext@claro.com.co
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_VIGENCIA_COSTO_ITEM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtVigenciaCostoItemMgl.findAll", query = "SELECT c FROM CmtVigenciaCostoItemMgl c"),
    @NamedQuery(name = "CmtVigenciaCostoItemMgl.findByItemVigencia", query =
            "SELECT c FROM CmtVigenciaCostoItemMgl c WHERE c.itemObj = :itemObj "
            + "AND CURRENT_DATE BETWEEN c.fechaInicio and c.fechaFin")})
public class CmtVigenciaCostoItemMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtVigenciaCostoItemMgl.CmtVigenciaCostoItemSq",
            sequenceName = "MGL_SCHEME.CMT_VIGENCIACOSTOITEM_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtVigenciaCostoItemMgl.CmtVigenciaCostoItemSq")
    @Column(name = "VIGENCIA_COSTO_ITEM_ID")
    private BigDecimal idVigenciaCostoItem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private CmtItemMgl itemObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COSTO")
    private BigDecimal costo;
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

    public CmtVigenciaCostoItemMgl() {
    }

    /**
     * @return the idVigenciaCostoItem
     */
    public BigDecimal getIdVigenciaCostoItem() {
        return idVigenciaCostoItem;
    }

    /**
     * @param idVigenciaCostoItem the idVigenciaCostoItem to set
     */
    public void setIdVigenciaCostoItem(BigDecimal idVigenciaCostoItem) {
        this.idVigenciaCostoItem = idVigenciaCostoItem;
    }

    /**
     * @return the tipoFlujoOt
     */
    public CmtItemMgl getTipoFlujoOt() {
        return itemObj;
    }

    /**
     * @param itemObj
     */
    public void setTipoFlujoOt(CmtItemMgl itemObj) {
        this.itemObj = itemObj;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the costo
     */
    public BigDecimal getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
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

}
