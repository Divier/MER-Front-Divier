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
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "RR_NODOS_DTH", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RrNodosDth.findAll", query = "SELECT rn FROM RrNodosDth rn")})
public class RrNodosDth implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CODCIUDAD")
    private String codCiudad;
    @Column(name = "CODREGIONAL")
    private String codRegional;
    @Column(name = "CODEQ")
    private String codEQ;
    @Column(name = "UPDATED")
    private String updated;
    @Column(name = "INHABILITADO")
    private String inhabilitado;
    @Column(name = "CODDISTRITO")
    private String codDistrito;
    @Column(name = "CODZONA")
    private String codZona;
    @Column(name = "CODDIVISION")
    private String codDivision;
    @Column(name = "CODAREA")
    private String codArea;
    @Column(name = "CODUNIDAD")
    private String codUnidad;
    @Column(name = "REFERENCIA")
    private String referencia;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getInhabilitado() {
        return inhabilitado;
    }

    public void setInhabilitado(String inhabilitado) {
        this.inhabilitado = inhabilitado;
    }

    public String getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(String codDistrito) {
        this.codDistrito = codDistrito;
    }

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodDivision() {
        return codDivision;
    }

    public void setCodDivision(String codDivision) {
        this.codDivision = codDivision;
    }

    public String getCodArea() {
        return codArea;
    }

    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }  
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
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

}
