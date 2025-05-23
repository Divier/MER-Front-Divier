/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.math.BigDecimal;

/**
 *
 * @author cardenaslb
 */
public class CmtPenetracionCMDto {

    String idVt;
    String tecnologia;
    String estadoTecnologia;
    int cantHhpp;
    int totalClientes;
    BigDecimal rentaMensual;
    BigDecimal costoAcometida;
    BigDecimal costoVT;
    BigDecimal porcPenetracion;
    BigDecimal cumplimiento;
    BigDecimal meta;
    String fechaHabilitacion;
    BigDecimal tiempoRecuperacion;
    BigDecimal idOt;
    String idCm;
    String numeroCuentaCm;
    String nombreCm;
    String barrio;
    String direccionCm;
    String ciudad;
    String centroPoblado;
    int numeroTorres;
    int numeroPisos;
    String nodo;
    String estrato;
    String nombreTorre;
    BigDecimal tecnologiaBasicaId;
    
    private CmtTecnologiaSubMgl cmtTecnologiaSubMgl;

    public String getNombreTorre() {
        return nombreTorre;
    }

    public void setNombreTorre(String nombreTorre) {
        this.nombreTorre = nombreTorre;
    }
          
    public String getIdVt() {
        return idVt;
    }

    public void setIdVt(String idVt) {
        this.idVt = idVt;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public int getCantHhpp() {
        return cantHhpp;
    }

    public void setCantHhpp(int cantHhpp) {
        this.cantHhpp = cantHhpp;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public BigDecimal getRentaMensual() {
        return rentaMensual;
    }

    public void setRentaMensual(BigDecimal rentaMensual) {
        this.rentaMensual = rentaMensual;
    }

    public BigDecimal getCostoAcometida() {
        return costoAcometida;
    }

    public void setCostoAcometida(BigDecimal costoAcometida) {
        this.costoAcometida = costoAcometida;
    }

    public BigDecimal getCostoVT() {
        return costoVT;
    }

    public void setCostoVT(BigDecimal costoVT) {
        this.costoVT = costoVT;
    }

    public BigDecimal getPorcPenetracion() {
        return porcPenetracion;
    }

    public void setPorcPenetracion(BigDecimal porcPenetracion) {
        this.porcPenetracion = porcPenetracion;
    }

    public BigDecimal getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(BigDecimal cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

   

    public BigDecimal getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(BigDecimal tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public BigDecimal getIdOt() {
        return idOt;
    }

    public void setIdOt(BigDecimal idOt) {
        this.idOt = idOt;
    }

    public String getIdCm() {
        return idCm;
    }

    public void setIdCm(String idCm) {
        this.idCm = idCm;
    }

    public String getNumeroCuentaCm() {
        return numeroCuentaCm;
    }

    public void setNumeroCuentaCm(String numeroCuentaCm) {
        this.numeroCuentaCm = numeroCuentaCm;
    }

    public String getNombreCm() {
        return nombreCm;
    }

    public void setNombreCm(String nombreCm) {
        this.nombreCm = nombreCm;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccionCm() {
        return direccionCm;
    }

    public void setDireccionCm(String direccionCm) {
        this.direccionCm = direccionCm;
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

    public int getNumeroTorres() {
        return numeroTorres;
    }

    public void setNumeroTorres(int numeroTorres) {
        this.numeroTorres = numeroTorres;
    }

    public int getNumeroPisos() {
        return numeroPisos;
    }

    public void setNumeroPisos(int numeroPisos) {
        this.numeroPisos = numeroPisos;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(String fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    /**
     * @return the cmtTecnologiaSubMgl
     */
    public CmtTecnologiaSubMgl getCmtTecnologiaSubMgl() {
        return cmtTecnologiaSubMgl;
    }

    /**
     * @param cmtTecnologiaSubMgl the cmtTecnologiaSubMgl to set
     */
    public void setCmtTecnologiaSubMgl(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) {
        this.cmtTecnologiaSubMgl = cmtTecnologiaSubMgl;
    }

    public BigDecimal getTecnologiaBasicaId() {
        return tecnologiaBasicaId;
    }

    public void setTecnologiaBasicaId(BigDecimal tecnologiaBasicaId) {
        this.tecnologiaBasicaId = tecnologiaBasicaId;
    }
    
    
}
