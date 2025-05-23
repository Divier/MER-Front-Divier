/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author espinosadiea
 */
@Entity
@Table(name = "TEC_MARCAS_TECNOLOGIA_HAB$AUD" , schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findAll", query = "SELECT t FROM TecMarcasTecnologiaHabAud t"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByMarcasTecnologiaHabAudId", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.marcasTecnologiaHabAudId = :marcasTecnologiaHabAudId"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByMarcasTecnologiaHabId", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.marcasTecnologiaHabId = :marcasTecnologiaHabId ORDER BY t.marcasTecnologiaHabAudId DESC "),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByFechaCreacion", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByUsuarioCreacion", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByUsuarioEdicion", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.usuarioEdicion = :usuarioEdicion"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByTecnologiaHabilitadaId", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.tecnologiaHabilitadaId = :tecnologiaHabilitadaId"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByMarcasId", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.marcasId = :marcasId"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByAudAction", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.audAction = :audAction"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByAudTimestamp", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.audTimestamp = :audTimestamp"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByAudUser", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.audUser = :audUser"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findBySessionId", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.sessionId = :sessionId"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByObservaciones", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.observaciones = :observaciones"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByEstadoRegistro", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.estadoRegistro = :estadoRegistro"),
    @NamedQuery(name = "TecMarcasTecnologiaHabAud.findByFechaEdicion", query = "SELECT t FROM TecMarcasTecnologiaHabAud t WHERE t.fechaEdicion = :fechaEdicion")})
public class TecMarcasTecnologiaHabAud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARCAS_TECNOLOGIA_HAB_$AUD_ID")
    private Long marcasTecnologiaHabAudId;
    @NotNull
    @JoinColumn(name = "MARCAS_TECNOLOGIA_HAB_ID")
    private MarcasHhppMgl marcasTecnologiaHabId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 100)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Size(max = 100)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @NotNull
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID")
    private HhppMgl tecnologiaHabilitadaId;
    @NotNull
    @JoinColumn(name = "MARCAS_ID")
    private MarcasMgl marcasId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "AUD_ACTION")
    private String audAction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audTimestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "AUD_USER")
    private String audUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private BigInteger sessionId;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "ESTADO_REGISTRO")
    private Short estadoRegistro;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    public TecMarcasTecnologiaHabAud() {
    }

    public TecMarcasTecnologiaHabAud(Long marcasTecnologiaHabAudId) {
        this.marcasTecnologiaHabAudId = marcasTecnologiaHabAudId;
    }

    public TecMarcasTecnologiaHabAud(Long marcasTecnologiaHabAudId, MarcasHhppMgl marcasTecnologiaHabId, 
            Date fechaCreacion, HhppMgl tecnologiaHabilitadaId, MarcasMgl marcasId, String audAction, 
            Date audTimestamp, String audUser, BigInteger sessionId) {
        this.marcasTecnologiaHabAudId = marcasTecnologiaHabAudId;
        this.marcasTecnologiaHabId = marcasTecnologiaHabId;
        this.fechaCreacion = fechaCreacion;
        this.tecnologiaHabilitadaId = tecnologiaHabilitadaId;
        this.marcasId = marcasId;
        this.audAction = audAction;
        this.audTimestamp = audTimestamp;
        this.audUser = audUser;
        this.sessionId = sessionId;
    }

    public Long getMarcasTecnologiaHabAudId() {
        return marcasTecnologiaHabAudId;
    }

    public void setMarcasTecnologiaHabAudId(Long marcasTecnologiaHabAudId) {
        this.marcasTecnologiaHabAudId = marcasTecnologiaHabAudId;
    }

    public MarcasHhppMgl getMarcasTecnologiaHabId() {
        return marcasTecnologiaHabId;
    }

    public void setMarcasTecnologiaHabId(MarcasHhppMgl marcasTecnologiaHabId) {
        this.marcasTecnologiaHabId = marcasTecnologiaHabId;
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

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public HhppMgl getTecnologiaHabilitadaId() {
        return tecnologiaHabilitadaId;
    }

    public void setTecnologiaHabilitadaId(HhppMgl tecnologiaHabilitadaId) {
        this.tecnologiaHabilitadaId = tecnologiaHabilitadaId;
    }

    public MarcasMgl getMarcasId() {
        return marcasId;
    }

    public void setMarcasId(MarcasMgl marcasId) {
        this.marcasId = marcasId;
    }

    public String getAudAction() {
        return audAction;
    }

    public void setAudAction(String audAction) {
        this.audAction = audAction;
    }

    public Date getAudTimestamp() {
        return audTimestamp;
    }

    public void setAudTimestamp(Date audTimestamp) {
        this.audTimestamp = audTimestamp;
    }

    public String getAudUser() {
        return audUser;
    }

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public BigInteger getSessionId() {
        return sessionId;
    }

    public void setSessionId(BigInteger sessionId) {
        this.sessionId = sessionId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Short getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Short estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marcasTecnologiaHabAudId != null ? marcasTecnologiaHabAudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TecMarcasTecnologiaHabAud)) {
            return false;
        }
        TecMarcasTecnologiaHabAud other = (TecMarcasTecnologiaHabAud) object;
        if ((this.marcasTecnologiaHabAudId == null && other.marcasTecnologiaHabAudId != null) || (this.marcasTecnologiaHabAudId != null && !this.marcasTecnologiaHabAudId.equals(other.marcasTecnologiaHabAudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud[ marcasTecnologiaHabAudId=" + marcasTecnologiaHabAudId + " ]";
    }
    
}
