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
 * Entidad mapeo tabla CMT_ITEM
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ITEM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtItemMgl.findAll", query = "SELECT c FROM CmtItemMgl c"),
    @NamedQuery(name = "CmtItemMgl.findByTipoItem", query = "SELECT c FROM CmtItemMgl c WHERE c.tipoItem = :tipoItem")})
public class CmtItemMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtItemMgl.CmtItemSq",
            sequenceName = "MGL_SCHEME.CMT_ITEM_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtItemMgl.CmtItemSq")
    @Column(name = "ITEM_ID")
    private BigDecimal idItem;
    @Column(name = "CODIGO_ITEM", nullable = false, length = 15)
    private String codigoItem;
    @Column(name = "DESC_ITEM", nullable = false, length = 200)
    private String descItem;
    @Column(name = "TIPO_ITEM", nullable = false, length = 2)
    private String tipoItem;
    @JoinColumn(name = "UNIDADMEDIDA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtUnidadesMedidaMgl idUnidad;
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

    public CmtItemMgl() {
    }

    /**
     * @return the idItem
     */
    public BigDecimal getIdItem() {
        return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(BigDecimal idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the codigoItem
     */
    public String getCodigoItem() {
        return codigoItem;
    }

    /**
     * @param codigoItem the codigoItem to set
     */
    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    /**
     * @return the descItem
     */
    public String getDescItem() {
        return descItem;
    }

    /**
     * @param descItem the descItem to set
     */
    public void setDescItem(String descItem) {
        this.descItem = descItem;
    }

    /**
     * @return the tipoItem
     */
    public String getTipoItem() {
        return tipoItem;
    }

    /**
     * @param tipoItem the tipoItem to set
     */
    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    /**
     * @return the idUnidad
     */
    public CmtUnidadesMedidaMgl getIdUnidad() {
        return idUnidad;
    }

    /**
     * @param idUnidad the idUnidad to set
     */
    public void setIdUnidad(CmtUnidadesMedidaMgl idUnidad) {
        this.idUnidad = idUnidad;
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
    
}
