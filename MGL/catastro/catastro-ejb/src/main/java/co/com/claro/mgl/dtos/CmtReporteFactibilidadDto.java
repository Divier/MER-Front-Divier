/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cardenaslb
 */
public class CmtReporteFactibilidadDto {

    /* Se debe conservar el orden los campos para el reporte de factibilidad */
    private String idFactibilidad;
    private String idFactibilidadVigente;
    private String subEdificio;
    private String departamento;
    private String ciudad;
    private String centroPoblado;
    private String complemento;
    private String apartamento;
    private String coordenadalongitud;
    private String coordenadaLatitud;
    private String direccionCompleta;
    private String fechaHoraConsultaReporte;
    private String usuario;
    private String idRRCCMM;
    private String idMerCcmm;
    private String NombreCCMM;
    private String cuentacliente;
    private String isCm;
    private String tecnologia;
    private String sDS;
    private String nodoCobertura;
    private String factibilidad;
    private String arrendatario;
    private String tiempoInstalacionUM;
    private String estadoTecnologia;
    private String nodoSitiData;
    private String nodoAproximado;
    private String distanciaNodoAproximado;
    private String proyecto;
    private BigDecimal estrato;

    /* Para el reporte de factibilidad no se usan estos campos */
    private Date fechahoraConsulta;
    private String edificio;
    private String DireccionCCMM;
    private String cobertura;
    private String nodoEstado;
    private BigDecimal dirDetallada;
    private Date fechaVencimiento;

    public String getIdFactibilidad() {
        return idFactibilidad;
    }

    public void setIdFactibilidad(String idFactibilidad) {
        this.idFactibilidad = idFactibilidad;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getCoordenadalongitud() {
        return coordenadalongitud;
    }

    public void setCoordenadalongitud(String coordenadalongitud) {
        this.coordenadalongitud = coordenadalongitud;
    }

    public String getCoordenadaLatitud() {
        return coordenadaLatitud;
    }

    public void setCoordenadaLatitud(String coordenadaLatitud) {
        this.coordenadaLatitud = coordenadaLatitud;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public String getIdFactibilidadVigente() {
        return idFactibilidadVigente;
    }

    public void setIdFactibilidadVigente(String idFactibilidadVigente) {
        this.idFactibilidadVigente = idFactibilidadVigente;
    }

    public Date getFechahoraConsulta() {
        return fechahoraConsulta;
    }

    public void setFechahoraConsulta(Date fechahoraConsulta) {
        this.fechahoraConsulta = fechahoraConsulta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdRRCCMM() {
        return idRRCCMM;
    }

    public void setIdRRCCMM(String idRRCCMM) {
        this.idRRCCMM = idRRCCMM;
    }

    public String getIdMerCcmm() {
        return idMerCcmm;
    }

    public void setIdMerCcmm(String idMerCcmm) {
        this.idMerCcmm = idMerCcmm;
    }

    public String getNombreCCMM() {
        return NombreCCMM;
    }

    public void setNombreCCMM(String NombreCCMM) {
        this.NombreCCMM = NombreCCMM;
    }

    public String getCuentacliente() {
        return cuentacliente;
    }

    public void setCuentacliente(String cuentacliente) {
        this.cuentacliente = cuentacliente;
    }

    public String getSubEdificio() {
        return subEdificio;
    }

    public void setSubEdificio(String subEdificio) {
        this.subEdificio = subEdificio;
    }

    public String getDireccionCCMM() {
        return DireccionCCMM;
    }

    public void setDireccionCCMM(String DireccionCCMM) {
        this.DireccionCCMM = DireccionCCMM;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getsDS() {
        return sDS;
    }

    public void setsDS(String sDS) {
        this.sDS = sDS;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getFactibilidad() {
        return factibilidad;
    }

    public void setFactibilidad(String factibilidad) {
        this.factibilidad = factibilidad;
    }

    public String getArrendatario() {
        return arrendatario;
    }

    public void setArrendatario(String arrendatario) {
        this.arrendatario = arrendatario;
    }

    public String getTiempoInstalacionUM() {
        return tiempoInstalacionUM;
    }

    public void setTiempoInstalacionUM(String tiempoInstalacionUM) {
        this.tiempoInstalacionUM = tiempoInstalacionUM;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public String getNodoCobertura() {
        return nodoCobertura;
    }

    public void setNodoCobertura(String nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }

    public String getNodoEstado() {
        return nodoEstado;
    }

    public void setNodoEstado(String nodoEstado) {
        this.nodoEstado = nodoEstado;
    }

    public String getIsCm() {
        return isCm;
    }

    public void setIsCm(String isCm) {
        this.isCm = isCm;
    }

    public BigDecimal getDirDetallada() {
        return dirDetallada;
    }

    public void setDirDetallada(BigDecimal dirDetallada) {
        this.dirDetallada = dirDetallada;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaHoraConsultaReporte() {
        return fechaHoraConsultaReporte;
    }

    public void setFechaHoraConsultaReporte(String fechaHoraConsultaReporte) {
        this.fechaHoraConsultaReporte = fechaHoraConsultaReporte;
    }

    public String getNodoAproximado() {
        return nodoAproximado;
    }

    public void setNodoAproximado(String nodoAproximado) {
        this.nodoAproximado = nodoAproximado;
    }

    public String getDistanciaNodoAproximado() {
        return distanciaNodoAproximado;
    }

    public void setDistanciaNodoAproximado(String distanciaNodoAproximado) {
        this.distanciaNodoAproximado = distanciaNodoAproximado;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /* Brief 98062 */
    public String getNodoSitiData() {
        return nodoSitiData;
    }

    public void setNodoSitiData(String nodoSitiData) {
        this.nodoSitiData = nodoSitiData;
    }

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }    
}
