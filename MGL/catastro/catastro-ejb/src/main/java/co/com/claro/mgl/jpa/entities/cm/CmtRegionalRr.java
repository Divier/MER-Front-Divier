/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@Entity
@Table(name = "CMT_REGIONAL_RR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtRegionalRr.findAll", query = "SELECT c FROM CmtRegionalRr c"),
    @NamedQuery(name = "CmtRegionalRr.findEstado1", query = "SELECT c FROM CmtRegionalRr c WHERE  c.estadoRegistro = 1 order by c.nombreRegional"),
    @NamedQuery(name = "CmtRegionalRr.findByCodigo", query = "SELECT c FROM CmtRegionalRr c  WHERE  c.estadoRegistro = 1 AND c.codigoRr= :codigoRr")
})
    
public class CmtRegionalRr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(
            name = "CmtRegionalRr.CmtRegionalRrSeq",
            sequenceName = "MGL_SCHEME.CMT_REGIONAL_RR_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtRegionalRr.CmtRegionalRrSeq")  
    @Column(name = "REGIONAL_RR_ID")
    private BigDecimal regionalRrId;
    @Size(max = 5)
    @Column(name = "CODIGO_RR")
    private String codigoRr;
    @Size(max = 100)
    @Column(name = "NOMBRE_REGIONAL")
    private String nombreRegional;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Size(max = 15)
    @Column(name = "PERFIL_CREACION")
    private String perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Size(max = 15)
    @Column(name = "PERFIL_EDICION")
    private String perfilEdicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionalRr")
    private List<CmtComunidadRr> listComunidadRr;

    public CmtRegionalRr() {
    }

    public BigDecimal getRegionalRrId() {
        return regionalRrId;
    }

    public void setRegionalRrId(BigDecimal regionalRrId) {
        this.regionalRrId = regionalRrId;
    }

    public String getCodigoRr() {
        return codigoRr;
    }

    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    public String getNombreRegional() {
        return nombreRegional;
    }

    public void setNombreRegional(String nombreRegional) {
        this.nombreRegional = nombreRegional;
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

    public String getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(String perfilCreacion) {
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

    public String getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(String perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public List<CmtComunidadRr> getListComunidadRr() {
        return listComunidadRr;
    }

    public void setListComunidadRr(List<CmtComunidadRr> listComunidadRr) {
        this.listComunidadRr = listComunidadRr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionalRrId != null ? regionalRrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtRegionalRr)) {
            return false;
        }
        CmtRegionalRr other = (CmtRegionalRr) object;
        if ((this.regionalRrId == null && other.regionalRrId != null) || (this.regionalRrId != null && !this.regionalRrId.equals(other.regionalRrId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "datos.CmtRegionalRr[ regionalRrId=" + regionalRrId + " ]";
    }
 
}
