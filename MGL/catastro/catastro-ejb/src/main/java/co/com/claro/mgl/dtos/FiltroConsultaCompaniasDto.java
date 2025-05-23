/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class FiltroConsultaCompaniasDto {
    public BigDecimal tipoCompania;
    public String nombre="";
    public String nit="";
    public BigDecimal departamento;
    public BigDecimal municipio;
    public BigDecimal centroPoblado;
    public String Barrio="";
    public String estado="";

    public BigDecimal getTipoCompania() {
        return tipoCompania;
    }

    public void setTipoCompania(BigDecimal tipoCompania) {
        this.tipoCompania = tipoCompania;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public BigDecimal getDepartamento() {
        return departamento;
    }

    public void setDepartamento(BigDecimal departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getMunicipio() {
        return municipio;
    }

    public void setMunicipio(BigDecimal municipio) {
        this.municipio = municipio;
    }

    public BigDecimal getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(BigDecimal centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String Barrio) {
        this.Barrio = Barrio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
