/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
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
@Table(name = "CMT_ORDENTRABAJO_INVENTARIO", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findAll", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByOrdentrabajoInventarioId", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.ordentrabajoInventarioId = :ordentrabajoInventarioId"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByOtId", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.cmtOrdenTrabajoMglObj.idOt = :otId and c.estadoRegistro = :estadoRegistro"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findBySistemaInventarioId", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.sistemaInventarioId = :sistemaInventarioId"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByTipoInventario", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.tipoInventario = :tipoInventario"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByClaseInventario", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.claseInventario = :claseInventario"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByFabricante", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.fabricante = :fabricante"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findBySerial", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.serial = :serial"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByReferencia", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.referencia = :referencia"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByEstado", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.estado = :estado"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByMac", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.mac = :mac"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByFechaCreacion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByUsuarioCreacion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByPerfilCreacion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.perfilCreacion = :perfilCreacion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByFechaEdicion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.fechaEdicion = :fechaEdicion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByUsuarioEdicion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.usuarioEdicion = :usuarioEdicion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByPerfilEdicion", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.perfilEdicion = :perfilEdicion"),
    @NamedQuery(name = "CmtOrdenTrabajoInventarioMgl.findByEstadoRegistro", query = "SELECT c FROM CmtOrdenTrabajoInventarioMgl c WHERE c.estadoRegistro = :estadoRegistro")})
public class CmtOrdenTrabajoInventarioMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "CmtOrdenTrabajoInventarioMgl.CmtOrdenTrabajoInventarioMglSq",
            sequenceName = "MGL_SCHEME.CMT_ORDENTRABAJO_INVENTARIO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtOrdenTrabajoInventarioMgl.CmtOrdenTrabajoInventarioMglSq")
    @Column(name = "ORDENTRABAJO_INVENTARIO_ID", nullable = false)
    private Long ordentrabajoInventarioId;    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID", nullable = true)
    private CmtOrdenTrabajoMgl cmtOrdenTrabajoMglObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_VT_ID", nullable = true)
    private CmtSubEdificiosVt cmtSubEdificiosVtObj;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTARIO_TECNOLOGIA_ID")
    private CmtInventariosTecnologiaMgl inventarioTecObj;    
    @Column(name = "VT_ID")
    private Long VtId;
    @Basic(optional = false)

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SISTEMA_INVENTARIO_ID", nullable = false, length = 20)
    private String sistemaInventarioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIPO_INVENTARIO", nullable = false, length = 20)
    private String tipoInventario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CLASE_INVENTARIO", nullable = false, length = 20)
    private String claseInventario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FABRICANTE", nullable = false, length = 20)
    private String fabricante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SERIAL", nullable = false, length = 20)
    private String serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "REFERENCIA", nullable = false, length = 20)
    private String referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO", nullable = false)
    private short estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MAC", nullable = false, length = 20)
    private String mac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 20)
    @Column(name = "USUARIO_EDICION", length = 20)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @Column(name = "ACTION_INV_TEC_OT", length = 2)
    private char actionInvTecOt;

    
    
    public CmtOrdenTrabajoInventarioMgl() {
    }

    public Long getOrdentrabajoInventarioId() {
        return ordentrabajoInventarioId;
    }

    public void setOrdentrabajoInventarioId(Long ordentrabajoInventarioId) {
        this.ordentrabajoInventarioId = ordentrabajoInventarioId;
    }

    public CmtOrdenTrabajoMgl getCmtOrdenTrabajoMglObj() {
        return cmtOrdenTrabajoMglObj;
    }

    public void setCmtOrdenTrabajoMglObj(CmtOrdenTrabajoMgl CmtOrdenTrabajoMglObj) {
        this.cmtOrdenTrabajoMglObj = CmtOrdenTrabajoMglObj;
    }

    public CmtSubEdificiosVt getCmtSubEdificiosVtObj() {
        return cmtSubEdificiosVtObj;
    }

    public void setCmtSubEdificiosVtObj(CmtSubEdificiosVt cmtSubEdificiosVtObj) {
        this.cmtSubEdificiosVtObj = cmtSubEdificiosVtObj;
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

    public char getActionInvTecOt() {
        return actionInvTecOt;
    }

    public void setActionInvTecOt(char actionInvTecOt) {
        this.actionInvTecOt = actionInvTecOt;
    }

    public Long getVtId() {
        return VtId;
    }

    public CmtInventariosTecnologiaMgl getInventarioTecObj() {
        return inventarioTecObj;
    }

    public void setInventarioTecObj(CmtInventariosTecnologiaMgl inventarioTecObj) {
        this.inventarioTecObj = inventarioTecObj;
    }

    public void setVtId(Long VtId) {
        this.VtId = VtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordentrabajoInventarioId != null ? ordentrabajoInventarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtOrdenTrabajoInventarioMgl)) {
            return false;
        }
        CmtOrdenTrabajoInventarioMgl other = (CmtOrdenTrabajoInventarioMgl) object;
        if ((this.ordentrabajoInventarioId == null && other.ordentrabajoInventarioId != null) || (this.ordentrabajoInventarioId != null && !this.ordentrabajoInventarioId.equals(other.ordentrabajoInventarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl[ ordentrabajoInventarioId=" + ordentrabajoInventarioId + " ]";
    }
   
}
