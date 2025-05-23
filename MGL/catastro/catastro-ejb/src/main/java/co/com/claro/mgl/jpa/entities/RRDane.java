/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
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
 * @author User
 */
@Entity
@Table(name = "RR_DANE", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RRDane.findAll", query = "SELECT rd FROM RRDane rd"),
    @NamedQuery(name = "RRDane.findByDane", query = "SELECT rd FROM RRDane rd where rd.codigoDane = :codDane AND rd.estado=:estado"),
    @NamedQuery(name = "RRDane.findByCodCidad", query = "SELECT rd FROM RRDane rd where rd.codCiudad = :codCiudad")
})
public class RRDane implements Serializable{
    @Id
    @Basic(optional = false)
    @Column(name = "CODCIUDAD", nullable = false)
    private String codCiudad;    
    @Column(name = "CODIGO")
    private String codigoDane;
    @Column(name = "MUNICIPIO")
    private String municipio;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CODREGIONAL")
    private String codRegional;
    @Column(name = "CODEQ")
    private String codEQ;
    @Column(name = "DEPARTAMENTO")
    private String departamento;
    @Column(name = "REGION")
    private String region;
    @Column(name = "ESMUNICIPIO")
    private String esMunicipio;
    @Column(name = "UBICACION")
    private String ubicacion;

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodRegional() {
        return codRegional;
    }

    public void setCodRegional(String codRegional) {
        this.codRegional = codRegional;
    }

    public String getCodEQ() {
        return codEQ;
    }

    public void setCodEQ(String codEQ) {
        this.codEQ = codEQ;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEsMunicipio() {
        return esMunicipio;
    }

    public void setEsMunicipio(String esMunicipio) {
        this.esMunicipio = esMunicipio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
