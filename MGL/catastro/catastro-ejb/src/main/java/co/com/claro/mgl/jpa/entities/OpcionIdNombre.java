/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement(name = "opcionIdNombre")
public class OpcionIdNombre  implements Comparable  {

    /* Variables */
    @XmlElement 
    private String idParametro;
    @XmlElement 
    private String descripcion;
    @XmlElement 
    private String idTipo;

    /* Getters and Setters */
    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }
    
    @Override
    public int compareTo(Object o) {
        OpcionIdNombre opcionIdNombre = (OpcionIdNombre) o;
        return this.descripcion.compareToIgnoreCase(opcionIdNombre.descripcion);
    }

}
