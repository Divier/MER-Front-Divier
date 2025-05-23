package co.com.claro.mgl.jpa.entities.cm;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entidad <b>US_ROL_PERFIL</b>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Entity
@Table(name = "US_ROL_PERFIL", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsRolPerfil.findAll", query = "SELECT rp FROM UsRolPerfil rp"),
    @NamedQuery(name = "UsRolPerfil.findByPerfil", query = "SELECT rp FROM UsRolPerfil rp WHERE rp.idPerfil = :idPerfil AND rp.estadoRol = :estadoRol ")
})
public class UsRolPerfil implements Serializable {
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ROLPERFIL")
    private String idRolperfil;
    @Column(name = "COD_ROL", nullable = true, length = 10)
    private String codRol;
    @Column(name = "ESTADO", nullable = true, length = 2)
    private String estadoRol;
    @Column(name = "ID_PERFIL", nullable = true)
    private BigDecimal idPerfil;
    @Column(name = "FECHA_CAMBIO", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaCambio;
    @Column(name = "USU_MODIFICO", nullable = true, length = 20)
    private BigInteger usuModifico;
    
    private transient String entityClass;
    private transient String descripcionRol;
             

    public UsRolPerfil() {
    }

    public String getCodRol() {
        return codRol;
    }

    public void setCodRol(String codRol) {
        this.codRol = codRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public String getEstadoRol() {
        return estadoRol;
    }

    public void setEstadoRol(String estadoRol) {
        this.estadoRol = estadoRol;
    }

    public BigDecimal getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdRolperfil() {
        return idRolperfil;
    }

    public void setIdRolperfil(String idRolperfil) {
        this.idRolperfil = idRolperfil;
    }

    /**
     * @return the fechaCambio
     */
    public Date getFechaCambio() {
        return fechaCambio;
    }

    /**
     * @param fechaCambio the fechaCambio to set
     */
    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /**
     * @return the usuModifico
     */
    public BigInteger getUsuModifico() {
        return usuModifico;
    }

    /**
     * @param usuModifico the usuModifico to set
     */
    public void setUsuModifico(BigInteger usuModifico) {
        this.usuModifico = usuModifico;
    }

    /**
     * @return the entityClass
     */
    public String getEntityClass() {
        return entityClass;
    }

    /**
     * @param entityClass the entityClass to set
     */
    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }
    
    
}
