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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "CMT_INVENTARIOS_TECNOLOGIA", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findAll", query = "SELECT c FROM CmtInventariosTecnologiaMgl c"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByInventarioTecnologiaId", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.inventarioTecnologiaId = :inventarioTecnologiaId"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByTecnoSubedificioId", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.tecnoSubedificioId = :tecnoSubedificioId"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findBySistemaInventarioId", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.sistemaInventarioId = :sistemaInventarioId"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByTipoInventario", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.tipoInventario = :tipoInventario"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByClaseInventario", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.claseInventario = :claseInventario"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByFabricante", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.fabricante = :fabricante"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findBySerial", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.serial = :serial"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByReferencia", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.referencia = :referencia"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByEstado", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.estado = :estado"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByMac", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.mac = :mac"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByFechaCreacion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByUsuarioCreacion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByPerfilCreacion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.perfilCreacion = :perfilCreacion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByFechaEdicion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.fechaEdicion = :fechaEdicion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByUsuarioEdicion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.usuarioEdicion = :usuarioEdicion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByPerfilEdicion", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.perfilEdicion = :perfilEdicion"),
    @NamedQuery(name = "CmtInventariosTecnologiaMgl.findByEstadoRegistro", query = "SELECT c FROM CmtInventariosTecnologiaMgl c WHERE c.estadoRegistro = :estadoRegistro")})
public class CmtInventariosTecnologiaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtInventariosTecnologiaMgl.CmtInventariosTecnologiaMglSq",
            sequenceName = "MGL_SCHEME.CMT_INVENTARIOS_TECNOLOGIA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtInventariosTecnologiaMgl.CmtInventariosTecnologiaMglSq")
    @NotNull
    @Column(name = "INVENTARIO_TECNOLOGIA_ID")
    private BigDecimal inventarioTecnologiaId;
    @NotNull
    @Size(min = 1, max = 20)
    @JoinColumn(name = "TECNO_SUBEDIFICIO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtTecnologiaSubMgl tecnoSubedificioId;
    @Column(name = "SISTEMA_INVENTARIO_ID")
    private String sistemaInventarioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO_INVENTARIO")
    private String tipoInventario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CLASE_INVENTARIO")
    private String claseInventario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FABRICANTE")
    private String fabricante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SERIAL")
    private String serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private short estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAC")
    private String mac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    public CmtInventariosTecnologiaMgl() {
    }

    public CmtInventariosTecnologiaMgl(BigDecimal inventarioTecnologiaId) {
        this.inventarioTecnologiaId = inventarioTecnologiaId;
    }

    public CmtInventariosTecnologiaMgl(BigDecimal inventarioTecnologiaId, String sistemaInventarioId, String tipoInventario, String claseInventario, String fabricante, String serial, String referencia, short estado, String mac, Date fechaCreacion, String usuarioCreacion, int perfilCreacion, int perfilEdicion, int estadoRegistro) {
        this.inventarioTecnologiaId = inventarioTecnologiaId;
        this.sistemaInventarioId = sistemaInventarioId;
        this.tipoInventario = tipoInventario;
        this.claseInventario = claseInventario;
        this.fabricante = fabricante;
        this.serial = serial;
        this.referencia = referencia;
        this.estado = estado;
        this.mac = mac;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.perfilCreacion = perfilCreacion;
        this.perfilEdicion = perfilEdicion;
        this.estadoRegistro = estadoRegistro;
    }

    public BigDecimal getInventarioTecnologiaId() {
        return inventarioTecnologiaId;
    }

    public void setInventarioTecnologiaId(BigDecimal inventarioTecnologiaId) {
        this.inventarioTecnologiaId = inventarioTecnologiaId;
    }

    public String getSistemaInventarioId() {
        return sistemaInventarioId;
    }

    public void setSistemaInventarioId(String sistemaInventarioId) {
        this.sistemaInventarioId = sistemaInventarioId;
    }

    public String getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(String tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public String getClaseInventario() {
        return claseInventario;
    }

    public void setClaseInventario(String claseInventario) {
        this.claseInventario = claseInventario;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtTecnologiaSubMgl getTecnoSubedificioId() {
        return tecnoSubedificioId;
    }

    public void setTecnoSubedificioId(CmtTecnologiaSubMgl tecnoSubedificioId) {
        this.tecnoSubedificioId = tecnoSubedificioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventarioTecnologiaId != null ? inventarioTecnologiaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtInventariosTecnologiaMgl)) {
            return false;
        }
        CmtInventariosTecnologiaMgl other = (CmtInventariosTecnologiaMgl) object;
        if ((this.inventarioTecnologiaId == null && other.inventarioTecnologiaId != null) || (this.inventarioTecnologiaId != null && !this.inventarioTecnologiaId.equals(other.inventarioTecnologiaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl[ inventarioTecnologiaId=" + inventarioTecnologiaId + " ]";
    }

}
