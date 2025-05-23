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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity CmtTipoCerraduraElectronica. Permite manejar
 * los los tipos de cerradura electronica
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Entity
@Table(name = "cmt_tip_cerrad_elect", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtTipoCerraduraElectronica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoCerraduraElectronica.CmtTipoCerraduraElectronicaSq",
            sequenceName = "MGL_SCHEME.CMT_TIPCERRAELECT_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoCerraduraElectronica.CmtTipoCerraduraElectronicaSq")
    @Column(name = "ID", nullable = false, length = 15)
    private BigDecimal id;
    @Column(name = "TIPO_CERRAD_ELECT", nullable = false, length = 15)
    private String tipoCerraduraElectronica;
    @Column(name = "DESCRIPCION", nullable = false, length = 500)
    private String descripcion;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }   

    public String getTipoCerraduraElectronica() {
        return tipoCerraduraElectronica;
    }

    public void setTipoCerraduraElectronica(String tipoCerraduraElectronica) {
        this.tipoCerraduraElectronica = tipoCerraduraElectronica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "CmtTipoCerraduraElectronica{" + "id=" + id + ", tipoCerraduraElectronica=" + tipoCerraduraElectronica + ", descripcion=" + descripcion + '}';
    }
    
    
    
}
