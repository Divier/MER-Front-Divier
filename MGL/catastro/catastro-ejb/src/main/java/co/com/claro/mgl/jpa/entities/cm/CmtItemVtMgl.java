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
 * Entidad mapeo tabla CMT_ITEMVT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ITEM_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtItemVtMgl.findAll", query = "SELECT c FROM CmtItemVtMgl c"),
    @NamedQuery(name = "CmtItemVtMgl.findItemVtByVt", query = "SELECT c FROM CmtItemVtMgl c WHERE c.estadoRegistro = 1 AND c.vtObj = :vtObj AND c.itemObj.tipoItem = :tipoItem"),
    @NamedQuery(name = "CmtItemVtMgl.getCountByVt", query = "SELECT COUNT(1) FROM CmtItemVtMgl c WHERE c.estadoRegistro = 1 AND c.vtObj = :vtObj AND c.itemObj.tipoItem = :tipoItem"),
    @NamedQuery(name = "CmtItemVtMgl.findItemVtByItem", query = "SELECT c FROM CmtItemVtMgl c WHERE c.estadoRegistro = 1 AND c.vtObj = :vtObj AND c.itemObj.idItem = :idItem")})
public class CmtItemVtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtItemVtMgl.CmtItemVtSq",
            sequenceName = "MGL_SCHEME.CMT_ITEMVT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator
            = "CmtItemVtMgl.CmtItemVtSq")
    @Column(name = "ITEM_VT_ID")
    private BigDecimal idItemVt;
    @JoinColumn(name = "VT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtVisitaTecnicaMgl vtObj;
    @JoinColumn(name = "ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtItemMgl itemObj;
    @JoinColumn(name = "VIGENCIA_COSTO_ITEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtVigenciaCostoItemMgl vigenciaCostoItemObj;
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "COSTO_TOTAL", nullable = true)
    private BigDecimal costoTotal;
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
    @Column(name = "DETALLE")
    private String detalle;

    public CmtItemVtMgl() {
    }

    /**
     * @return the idItemVt
     */
    public BigDecimal getIdItemVt() {
        return idItemVt;
    }

    /**
     * @param idItemVt the idItemVt to set
     */
    public void setIdItemVt(BigDecimal idItemVt) {
        this.idItemVt = idItemVt;
    }

    /**
     * @return the vtObj
     */
    public CmtVisitaTecnicaMgl getVtObj() {
        return vtObj;
    }

    /**
     * @param vtObj the vtObj to set
     */
    public void setVtObj(CmtVisitaTecnicaMgl vtObj) {
        this.vtObj = vtObj;
    }

    /**
     * @return the itemObj
     */
    public CmtItemMgl getItemObj() {
        return itemObj;
    }

    /**
     * @param itemObj the itemObj to set
     */
    public void setItemObj(CmtItemMgl itemObj) {
        this.itemObj = itemObj;
    }

    /**
     * @return the vigenciaCostoItemObj
     */
    public CmtVigenciaCostoItemMgl getVigenciaCostoItemObj() {
        return vigenciaCostoItemObj;
    }

    /**
     * @param vigenciaCostoItemObj the vigenciaCostoItemObj to set
     */
    public void setVigenciaCostoItemObj(CmtVigenciaCostoItemMgl vigenciaCostoItemObj) {
        this.vigenciaCostoItemObj = vigenciaCostoItemObj;
    }

    /**
     * @return the cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

}
