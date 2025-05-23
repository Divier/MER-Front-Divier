/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author bocanegravm
 */
@Getter
@Setter
public class CmtFiltroConsultaNodosDto {

    public BigDecimal geograficoPolitico;
    public String nombreNodo;
    public String codigoNodo;
    public String fechaActivacion;
    public BigDecimal unidadGestion;
    public BigDecimal zonaIdNodo;
    public String distritoNodo;
    public BigDecimal divicionNodo;
    public BigDecimal areaNodo;
    public String headendNodo;
    public String campoAdi1Nodo;
    public String campoAdi2Nodo;
    public String campoAdi3Nodo;
    public String campoAdi4Nodo;
    public String campoAdi5Nodo;
    public String tipoNodo;
    public String comIdNodo;
    private String aliado;
    private String estado;
    private String opera;
    private String olt;
    private String oltNodo;
    private String ot;

    public BigDecimal getGeograficoPolitico() {
        return geograficoPolitico;
    }

    public void setGeograficoPolitico(BigDecimal geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    public String getNombreNodo() {
        return nombreNodo;
    }

    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public String getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public BigDecimal getUnidadGestion() {
        return unidadGestion;
    }

    public void setUnidadGestion(BigDecimal unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    public BigDecimal getZonaIdNodo() {
        return zonaIdNodo;
    }

    public void setZonaIdNodo(BigDecimal zonaIdNodo) {
        this.zonaIdNodo = zonaIdNodo;
    }

    public String getDistritoNodo() {
        return distritoNodo;
    }

    public void setDistritoNodo(String distritoNodo) {
        this.distritoNodo = distritoNodo;
    }

    public BigDecimal getDivicionNodo() {
        return divicionNodo;
    }

    public void setDivicionNodo(BigDecimal divicionNodo) {
        this.divicionNodo = divicionNodo;
    }

    public BigDecimal getAreaNodo() {
        return areaNodo;
    }

    public void setAreaNodo(BigDecimal areaNodo) {
        this.areaNodo = areaNodo;
    }

    public String getHeadendNodo() {
        return headendNodo;
    }

    public void setHeadendNodo(String headendNodo) {
        this.headendNodo = headendNodo;
    }

    public String getCampoAdi1Nodo() {
        return campoAdi1Nodo;
    }

    public void setCampoAdi1Nodo(String campoAdi1Nodo) {
        this.campoAdi1Nodo = campoAdi1Nodo;
    }

    public String getCampoAdi2Nodo() {
        return campoAdi2Nodo;
    }

    public void setCampoAdi2Nodo(String campoAdi2Nodo) {
        this.campoAdi2Nodo = campoAdi2Nodo;
    }

    public String getCampoAdi3Nodo() {
        return campoAdi3Nodo;
    }

    public void setCampoAdi3Nodo(String campoAdi3Nodo) {
        this.campoAdi3Nodo = campoAdi3Nodo;
    }

    public String getCampoAdi4Nodo() {
        return campoAdi4Nodo;
    }

    public void setCampoAdi4Nodo(String campoAdi4Nodo) {
        this.campoAdi4Nodo = campoAdi4Nodo;
    }

    public String getCampoAdi5Nodo() {
        return campoAdi5Nodo;
    }

    public void setCampoAdi5Nodo(String campoAdi5Nodo) {
        this.campoAdi5Nodo = campoAdi5Nodo;
    }

    public String getComIdNodo() {
        return comIdNodo;
    }

    public void setComIdNodo(String comIdNodo) {
        this.comIdNodo = comIdNodo;
    }

    public String getAliado() {
        return aliado;
    }

    public void setAliado(String aliado) {
        this.aliado = aliado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
