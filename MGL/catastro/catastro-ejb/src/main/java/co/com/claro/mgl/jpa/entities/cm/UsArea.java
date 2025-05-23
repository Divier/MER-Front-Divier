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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@Entity
@Table(name = "US_AREA", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsArea.findAll", query = "SELECT a FROM UsArea a")})
public class UsArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDAREA")
    private BigDecimal idArea;
    @Column(name = "AREA", nullable = false, length = 50)
    private String areaNombre;
    @Column(name = "ESTADO", nullable = true, length = 1)
    private String estado;

    public BigDecimal getIdArea() {
        return idArea;
    }

    public void setIdArea(BigDecimal idArea) {
        this.idArea = idArea;
    }

    public String getAreaNombre() {
        return areaNombre;
    }

    public void setAreaNombre(String areaNombre) {
        this.areaNombre = areaNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    
}
