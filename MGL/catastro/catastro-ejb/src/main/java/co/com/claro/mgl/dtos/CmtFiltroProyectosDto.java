/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 * DTO que representa el filtro para request de CRUD a tabla CMT_COM_TECHNICALSITESAP.
 *
 * @author Johan Gomez
 * @version 1.0, 2024/12/10
 */
public class CmtFiltroProyectosDto {
 
    private String opcion;

    private String sitio;
    
    private String daneMunicipio;
    
    private String centroPoblado;
    
    private String ubicacionTecnica;
    
    private String daneCp;
    
    private String idSitio;
    
    private Integer ccmm;
    
    private String tipoSitio;
    
    private String disponibilidad;
    
    private String usuarioCreacion;
    
    private String usuarioEdicion;
    
    private Integer estadoRegistro;

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getDaneMunicipio() {
        return daneMunicipio;
    }

    public void setDaneMunicipio(String daneMunicipio) {
        this.daneMunicipio = daneMunicipio;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getUbicacionTecnica() {
        return ubicacionTecnica;
    }

    public void setUbicacionTecnica(String ubicacionTecnica) {
        this.ubicacionTecnica = ubicacionTecnica;
    }

    public String getDaneCp() {
        return daneCp;
    }

    public void setDaneCp(String daneCp) {
        this.daneCp = daneCp;
    }

    public String getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(String idSitio) {
        this.idSitio = idSitio;
    }

    public Integer getCcmm() {
        return ccmm;
    }

    public void setCcmm(Integer ccmm) {
        this.ccmm = ccmm;
    }

    public String getTipoSitio() {
        return tipoSitio;
    }

    public void setTipoSitio(String tipoSitio) {
        this.tipoSitio = tipoSitio;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    
}
