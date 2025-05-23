/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@Entity
@Table(name = "US_PERFILES",schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsPerfil.findAll", query = "SELECT p FROM UsPerfil p")})
public class UsPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERFIL")
    private BigDecimal idPerfil;
    @Column(name = "COD_PERFIL", nullable = true, length = 10)
    private String codPerfil;
    @Column(name = "DESCRIPCION", nullable = true, length = 40)
    private String descripcion;
    @Column(name = "ESTADO", nullable = true, length = 2)
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAREA", nullable = false)
    private UsArea area;

    public BigDecimal getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(String codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UsArea getArea() {
        return area;
    }

    public void setArea(UsArea area) {
        this.area = area;
    }
}
