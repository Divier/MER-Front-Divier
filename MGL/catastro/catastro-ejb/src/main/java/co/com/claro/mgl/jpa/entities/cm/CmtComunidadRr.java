/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@Entity
@Table(name = "CMT_COMUNIDAD_RR" , schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtComunidadRr.findByComunidadRegional", query = "SELECT c FROM CmtComunidadRr c WHERE c.ciudad.gpoId = :idCiudad  and c.tecnologia.codigoBasica = :codigoTecnologia and c.estadoRegistro = 1 ORDER BY c.nombreComunidad ASC "),
    @NamedQuery(name = "CmtComunidadRr.findAll", query = "SELECT c FROM CmtComunidadRr c WHERE  c.estadoRegistro = 1 ORDER BY c.nombreComunidad ASC"),
    @NamedQuery(name = "CmtComunidadRr.findComunidadByCodigo", query = "SELECT c FROM CmtComunidadRr c WHERE c.codigoRr = :codigoRr and c.estadoRegistro = 1 ORDER BY c.nombreComunidad ASC")})
public class CmtComunidadRr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtComunidadRr.CmtComunidadRrSq",
            sequenceName = "MGL_SCHEME.CMT_COMUNIDAD_RR_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtComunidadRr.CmtComunidadRrSq")
    @NotNull
    @Column(name = "COMUNIDAD_RR_ID", nullable = false)
    private BigDecimal comunidadRrId;
    
    @Size(max = 5)
    @Column(name = "CODIGO_RR")
    private String codigoRr;
    
    @Size(max = 100)
    @Column(name = "NOMBRE_COMUNIDAD")
    private String nombreComunidad;
    
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
    
    @JoinColumn(name = "CIUDAD_GPO_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID")
    @ManyToOne(optional = false)
    private GeograficoPoliticoMgl ciudad;
    
    @JoinColumn(name = "REGIONAL_RR_ID", referencedColumnName = "REGIONAL_RR_ID")
    @ManyToOne(optional = false)
    private CmtRegionalRr regionalRr;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "TECNOLOGIA_BAS_ID", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tecnologia;
    
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    
    @Column(name = "NOMBRE_CORTO_REGIONAL")
    private String nombreCortoRegional;
    
    @Column(name = "UBICACION")
    private BigDecimal ubicacion;
    
    @Transient
    private String codigoLocation;

    public CmtComunidadRr() {
    }

    public BigDecimal getComunidadRrId() {
        return comunidadRrId;
    }

    public void setComunidadRrId(BigDecimal comunidadRrId) {
        this.comunidadRrId = comunidadRrId;
    }

    public String getCodigoRr() {
        return codigoRr;
    }

    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
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

    public GeograficoPoliticoMgl getCiudad() {
        return ciudad;
    }

    public void setCiudad(GeograficoPoliticoMgl ciudad) {
        this.ciudad = ciudad;
    }

    public CmtRegionalRr getRegionalRr() {
        return regionalRr;
    }

    public void setRegionalRr(CmtRegionalRr regionalRr) {
        this.regionalRr = regionalRr;
    }

    public CmtBasicaMgl getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(CmtBasicaMgl tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreCortoRegional() {
        return nombreCortoRegional;
    }

    public void setNombreCortoRegional(String nombreCortoRegional) {
        this.nombreCortoRegional = nombreCortoRegional;
    }

    public BigDecimal getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(BigDecimal ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCodigoLocation() {
        return codigoLocation;
    }

    public void setCodigoLocation(String codigoLocation) {
        this.codigoLocation = codigoLocation;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comunidadRrId != null ? comunidadRrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtComunidadRr)) {
            return false;
        }
        CmtComunidadRr other = (CmtComunidadRr) object;
        if ((this.comunidadRrId == null && other.comunidadRrId != null) || (this.comunidadRrId != null && !this.comunidadRrId.equals(other.comunidadRrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.CmtComunidadRr[ comunidadRrId=" + comunidadRrId + " ]";
    }
}
