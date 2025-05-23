/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitasTecnicas.business;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class NodoRR implements Serializable{

    private String codigo;
    private String nombre;
    private String estado;
    private String codCiudad;
    private String codEq;
    private String input;
    private String updated;
    private String inhabilitado;
    private String codRegional;
    private String codDistrito;
    private String codZona;
    private String dicDivision;
    private String codArea;
    private String codUnidad;
    private String referencia;

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

    public String getCodEq() {
        return codEq;
    }

    public void setCodEq(String codEq) {
        this.codEq = codEq;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

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

    public String getCodRegional() {
        return codRegional;
    }

    public void setCodRegional(String codRegional) {
        this.codRegional = codRegional;
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

    public String getDicDivision() {
        return dicDivision;
    }

    public void setDicDivision(String dicDivision) {
        this.dicDivision = dicDivision;
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
}
