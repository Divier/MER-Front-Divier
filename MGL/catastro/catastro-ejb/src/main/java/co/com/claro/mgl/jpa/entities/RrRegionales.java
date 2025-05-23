/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parzifal de León
 */
@Entity
@Table(name = "RR_REGIONALES", schema = "GESTIONNEW")
@NamedQueries({
    @NamedQuery(name = "RrRegionales.findUniAndBi", query = "SELECT r FROM RrRegionales r WHERE  r.codigo NOT IN (:cal) AND r.unibi IN (:uni,:bi) order by r.unibi,r.nombre"),
         @NamedQuery(name = "RrRegionales.findNombreRegionalByCodigo", query = "SELECT r.nombre FROM RrRegionales r WHERE  r.codigo=:codigo")})
@XmlRootElement
public class RrRegionales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    
    @Column(name="NOMBRE")
    private String nombre;
    
    @Column(name="ESTADO")
    private String estado;
    
    @Column(name="INPUT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date input;

    @Column(name="UPDATED")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;
    
    @Column(name="UNIBI")
    private String unibi;
    
    @Column(name="REFERENCIA")
    private String referencia;
    
    @Column(name="REFERENCIA1")
    private String referencia1;
    
    
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

    public Date getInput() {
        return input;
    }

    public void setInput(Date input) {
        this.input = input;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdate(Date updated) {
        this.updated = updated;
    }

    public String getUnibi() {
        return unibi;
    }

    public void setUnibi(String unibi) {
        this.unibi = unibi;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferencia1() {
        return referencia1;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }
    
    

    
}
