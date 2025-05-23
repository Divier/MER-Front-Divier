/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_ESTANDARES_RR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstandaresMgl.findByValor", query = "SELECT c.valor_rr FROM CmtEstandaresMgl c WHERE c.valor_catastro = :valor_catastro")})
public class CmtEstandaresMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtEstandaresMgl.CmtEstandaresMglSq",
            sequenceName = "MGL_SCHEME.MGL_MGL_ESTANDARES_RR_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtEstandaresMgl.CmtEstandaresMglSq")
   
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "VALOR_CATASTRO")
    private String valor_catastro;
    @Column(name = "VALOR_RR")
    private String valor_rr;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getValor_catastro() {
        return valor_catastro;
    }

    public void setValor_catastro(String valor_catastro) {
        this.valor_catastro = valor_catastro;
    }

    public String getValor_rr() {
        return valor_rr;
    }

    public void setValor_rr(String valor_rr) {
        this.valor_rr = valor_rr;
    }

    
    
    
    
}
