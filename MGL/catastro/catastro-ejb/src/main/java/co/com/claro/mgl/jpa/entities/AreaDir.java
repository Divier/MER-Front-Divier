/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "MGL_AREA_DIR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaDir.findAll", query = "SELECT a FROM AreaDir a"),
    @NamedQuery(name = "AreaDir.findByIdDiv", query = "SELECT a FROM AreaDir a where a.divId = :divId")})
public class AreaDir implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "AreaDir.MGL_AREA_SQ",
            sequenceName = "MGL_SCHEME.MGL_AREA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "AreaDir.MGL_AREA_SQ")
    @Basic(optional = false)
    @Column(name = "AREA_ID", nullable = false)
    private BigDecimal areId;
    @Column(name = "AREA_NOMBRE")
    private String areNombre;
    @Column(name = "DIVISIONAL_ID", nullable = false)
    private BigDecimal divId;

    public BigDecimal getAreId() {
        return areId;
    }

    public void setAreId(BigDecimal areId) {
        this.areId = areId;
    }

    public String getAreNombre() {
        return areNombre;
    }

    public void setAreNombre(String areNombre) {
        this.areNombre = areNombre;
    }

    public BigDecimal getDivId() {
        return divId;
    }

    public void setDivId(BigDecimal divId) {
        this.divId = divId;
    }   
    
}
