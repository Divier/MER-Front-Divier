/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import lombok.*;

/**
 *
 * @author Juan David Hernandez
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NodoMerDto {
    
    private String departamento;
    private String ciudad;
    private String centroPoblado;
    private String codigo;
    private String nombre;
    private String comunidad;
    private String division;
    private String area;
    private String distrito;
    private String zona;
    private String unidadGestion;
    private String tipoNodo;
    private String estadoNodo;
    private String usuarioCreacion;
    private String fechaCreacion;
    private String fechaModificacion;
    private String fechaApertura;
    private String opera;
    private String olt;
    private String oltNodo;
    private String ot;

    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
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

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getUnidadGestion() {
        return unidadGestion;
    }

    public void setUnidadGestion(String unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    public String getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    
    public String getOpera() {
        return opera;
    }

    public void setOpera(String opera) {
        this.opera = opera;
    }

    public String getOlt() {
        return olt;
    }

    public void setOlt(String olt) {
        this.olt = olt;
    }

    public String getOltNodo() {
        return oltNodo;
    }

    public void setOltNodo(String oltNodo) {
        this.oltNodo = oltNodo;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    
   
}
