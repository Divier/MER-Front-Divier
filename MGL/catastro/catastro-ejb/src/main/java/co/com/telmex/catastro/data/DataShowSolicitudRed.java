/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author carlos.villa.ext
 */
public class DataShowSolicitudRed implements Serializable{
    private BigDecimal id;
    private String observaciones;
    private String ciudad;
    private String fechaCreacion;
    private String nodo;
    private String registros;        
    private String estado;        

    /**
     * @return the id
     */

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the fechaCreacion
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * @param nodo the nodo to set
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * @return the registros
     */
    public String getRegistros() {
        return registros;
    }

    /**
     * @param registros the registros to set
     */
    public void setRegistros(String registros) {
        this.registros = registros;
    }

    /**
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
