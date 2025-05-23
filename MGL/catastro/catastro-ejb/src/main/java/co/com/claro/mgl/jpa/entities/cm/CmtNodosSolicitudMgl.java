/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.NodoMgl;
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
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_NODOS_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtNodosSolicitudMgl.findBySolicitudId",
    query = "SELECT c FROM CmtNodosSolicitudMgl c WHERE c.cmtSolicitudCmMglObj = :cmtSolicitudCmMglObj and c.estadoRegistro = 1 ")})
public class CmtNodosSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtNodosSolicitudMgl.CmtNodosSolicitudMglSeq",
            sequenceName = "MGL_SCHEME.CMT_NODOS_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtNodosSolicitudMgl.CmtNodosSolicitudMglSeq")
    @Column(name = "ID_NODOS_SOLICITUD", nullable = false)
    private BigDecimal nodosSolicitudId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID", referencedColumnName = "SOLICITUD_CM_ID", nullable = false)
    private CmtSolicitudCmMgl cmtSolicitudCmMglObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TECNOLOGIA", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoTecnologiaObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    private NodoMgl nodoMglObj;
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
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
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    public BigDecimal getNodosSolicitudId() {
        return nodosSolicitudId;
    }

    public void setNodosSolicitudId(BigDecimal nodosSolicitudId) {
        this.nodosSolicitudId = nodosSolicitudId;
    }

    public CmtSolicitudCmMgl getCmtSolicitudCmMglObj() {
        return cmtSolicitudCmMglObj;
    }

    public void setCmtSolicitudCmMglObj(CmtSolicitudCmMgl cmtSolicitudCmMglObj) {
        this.cmtSolicitudCmMglObj = cmtSolicitudCmMglObj;
    }

    public CmtBasicaMgl getTipoTecnologiaObj() {
        return tipoTecnologiaObj;
    }

    public void setTipoTecnologiaObj(CmtBasicaMgl tipoTecnologiaObj) {
        this.tipoTecnologiaObj = tipoTecnologiaObj;
    }

    public NodoMgl getNodoMglObj() {
        return nodoMglObj;
    }

    public void setNodoMglObj(NodoMgl nodoMglObj) {
        this.nodoMglObj = nodoMglObj;
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
}
