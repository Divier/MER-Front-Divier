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
@Table(name = "RR_NODOS", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RRNodos.findAll", query = "SELECT rn FROM RRNodos rn")})
public class RRNodos implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ESTADO")
    private String municipio;
    @Column(name = "CODCIUDAD")
    private String codCiudad;
    @Column(name = "CODREGIONAL")
    private String codRegional;
    @Column(name = "CODEQ")
    private String codEQ;

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

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
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
