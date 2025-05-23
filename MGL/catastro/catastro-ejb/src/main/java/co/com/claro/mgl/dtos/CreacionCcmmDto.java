/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class CreacionCcmmDto {
    
    

    private BigDecimal numeroCuenta;

    private String nombreCuenta;

    private Date fechaCreacion;

    private String usuarioCreacion;

    private Date fechaEdicion;

    private String usuarioEdicion;

    private String comunidad;

    private String division;

    private int estadoRegistro;

    private int perfilCreacion;

    private int perfilEdicion;
    
    private CmtBasicaMgl origenSolicitud;

    private GeograficoPoliticoMgl departamento;

    private GeograficoPoliticoMgl municipio;

    private GeograficoPoliticoMgl centroPoblado;

    private List<CmtDireccionSolicitudMgl> listCmtDireccionesMgl;
    
    private List<CmtSolicitudSubEdificioMgl> listCmtSolicitudSubEdificioMgl;
     
    private String origenFicha;
    
    private boolean casaaEdificio;
    
    private BigDecimal cantidadTorres;
    
    private String nombreEdificioGeneral;
    
    

    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    public GeograficoPoliticoMgl getMunicipio() {
        return municipio;
    }

    public void setMunicipio(GeograficoPoliticoMgl municipio) {
        this.municipio = municipio;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public List<CmtDireccionSolicitudMgl> getListCmtDireccionesMgl() {
        return listCmtDireccionesMgl;
    }

    public void setListCmtDireccionesMgl(List<CmtDireccionSolicitudMgl> listCmtDireccionesMgl) {
        this.listCmtDireccionesMgl = listCmtDireccionesMgl;
    }

    public List<CmtSolicitudSubEdificioMgl> getListCmtSolicitudSubEdificioMgl() {
        return listCmtSolicitudSubEdificioMgl;
    }

    public void setListCmtSolicitudSubEdificioMgl(List<CmtSolicitudSubEdificioMgl> listCmtSolicitudSubEdificioMgl) {
        this.listCmtSolicitudSubEdificioMgl = listCmtSolicitudSubEdificioMgl;
    }
    

    public String getOrigenFicha() {
        return origenFicha;
    }

    public void setOrigenFicha(String origenFicha) {
        this.origenFicha = origenFicha;
    }

    public CmtBasicaMgl getOrigenSolicitud() {
        return origenSolicitud;
    }

    public void setOrigenSolicitud(CmtBasicaMgl origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    public boolean isCasaaEdificio() {
        return casaaEdificio;
    }

    public void setCasaaEdificio(boolean casaaEdificio) {
        this.casaaEdificio = casaaEdificio;
    }

    public BigDecimal getCantidadTorres() {
        return cantidadTorres;
    }

    public void setCantidadTorres(BigDecimal cantidadTorres) {
        this.cantidadTorres = cantidadTorres;
    }

    public String getNombreEdificioGeneral() {
        return nombreEdificioGeneral;
    }

    public void setNombreEdificioGeneral(String nombreEdificioGeneral) {
        this.nombreEdificioGeneral = nombreEdificioGeneral;
    }
    
}
