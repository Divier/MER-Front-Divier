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
@Table(name = "RR_CIUDADES", schema = "GESTIONNEW")
@NamedQueries({
    @NamedQuery(name = "RrCiudades.findNombreCiudadByCodigo", query = "SELECT r FROM RrCiudades r WHERE  r.codigo=:codigo")})

@XmlRootElement
public class RrCiudades implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    
    @Column(name="NOMBRE")
    private String nombre;
    
    @Column(name="ESTADO")
    private String estado;
    
    @Column(name="CODREGIONAL")
    private String codregional;
    
    @Column(name="INPUT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date input;

    @Column(name="UPDATED")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updated;
    
    @Column(name="ID_CIUDAD_DANE")
    private String idCiudadDane;
    
    @Column(name="PRIORIDAD_VENTA")
    private Integer prioridadVenta;
    
    @Column(name="REFERENCIA")
    private String referencia;
    
    @Column(name="DEPARTAMENTO")
    private String departamento;
    
    @Column(name="CPOSTAL")
    private String cpostal;
    
    @Column(name="TIPO_RED")
    private String tipoRed;
    

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

    public String getCodregional() {
        return codregional;
    }

    public void setCodregional(String codregional) {
        this.codregional = codregional;
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

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getIdCiudadDane() {
        return idCiudadDane;
    }

    public void setIdCiudadDane(String idCiudadDane) {
        this.idCiudadDane = idCiudadDane;
    }

    public Integer getPrioridadVenta() {
        return prioridadVenta;
    }

    public void setPrioridadVenta(Integer prioridadVenta) {
        this.prioridadVenta = prioridadVenta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCpostal() {
        return cpostal;
    }

    public void setCpostal(String cpostal) {
        this.cpostal = cpostal;
    }

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }
    
    
}
