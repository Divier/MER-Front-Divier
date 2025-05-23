package co.com.telmex.catastro.services.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase ConsultaMasivaTable implementa Serialización
 *
 * @author Deiver Rovira.
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 */
public class ConsultaMasivaTable implements Serializable {

    private BigDecimal cm_idDireccion;
    private String cm_ciudad;
    private String cm_barrio;
    private String cm_direccion;
    private String cm_cuentaMatriz;
    private String cm_flagHhpp;
    private String cm_tipoRed;
    private String cm_nodo;
    private String cm_estadoHhpp;
    private String cm_nodoArea;
    private String cm_nodoDistrito;
    private String cm_nodoDivisional;
    private String cm_nodoUnidadGestion;
    private String cm_nodoZona;
    private String cm_tipoDireccion;
    private String cm_estrato;
    private String cm_nivelSocio;
    private String cm_confiabilidad;
    private String cm_localidad;
    private String cm_revisar;
    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
    private String nodoUno;
    private String nodoDos;
    private String nodoTres;
    //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
    //INICIO Direcciones Ivan Turriago
    private String cmDirManzanaCatastral;
    private String actividadEconomica;
    private String direccionAlterna;
    private String existencia;
    private String origen;
    //INICIO Direcciones Ivan Turriago

    /**
     *
     * @return
     */
    public String getCm_barrio() {
        return cm_barrio;
    }

    /**
     *
     * @param cm_barrio
     */
    public void setCm_barrio(String cm_barrio) {
        this.cm_barrio = cm_barrio;
    }

    /**
     *
     * @return
     */
    public String getCm_ciudad() {
        return cm_ciudad;
    }

    /**
     *
     * @param cm_ciudad
     */
    public void setCm_ciudad(String cm_ciudad) {
        this.cm_ciudad = cm_ciudad;
    }

    /**
     *
     * @return
     */
    public String getCm_cuentaMatriz() {
        return cm_cuentaMatriz;
    }

    /**
     *
     * @param cm_cuentaMatriz
     */
    public void setCm_cuentaMatriz(String cm_cuentaMatriz) {
        this.cm_cuentaMatriz = cm_cuentaMatriz;
    }

    /**
     *
     * @return
     */
    public String getCm_direccion() {
        return cm_direccion;
    }

    /**
     *
     * @param cm_direccion
     */
    public void setCm_direccion(String cm_direccion) {
        this.cm_direccion = cm_direccion;
    }

    /**
     *
     * @return
     */
    public String getCm_estadoHhpp() {
        return cm_estadoHhpp;
    }

    /**
     *
     * @param cm_estadoHhpp
     */
    public void setCm_estadoHhpp(String cm_estadoHhpp) {
        this.cm_estadoHhpp = cm_estadoHhpp;
    }

    /**
     *
     * @return
     */
    public String getCm_flagHhpp() {
        return cm_flagHhpp;
    }

    /**
     *
     * @param cm_flagHhpp
     */
    public void setCm_flagHhpp(String cm_flagHhpp) {
        this.cm_flagHhpp = cm_flagHhpp;
    }

    /**
     *
     * @return
     */
    public String getCm_nodoArea() {
        return cm_nodoArea;
    }

    /**
     *
     * @param cm_nodoArea
     */
    public void setCm_nodoArea(String cm_nodoArea) {
        this.cm_nodoArea = cm_nodoArea;
    }

    /**
     *
     * @return
     */
    public String getCm_nodoDistrito() {
        return cm_nodoDistrito;
    }

    /**
     *
     * @param cm_nodoDistrito
     */
    public void setCm_nodoDistrito(String cm_nodoDistrito) {
        this.cm_nodoDistrito = cm_nodoDistrito;
    }

    /**
     *
     * @return
     */
    public String getCm_nodoDivisional() {
        return cm_nodoDivisional;
    }

    /**
     *
     * @param cm_nodoDivisional
     */
    public void setCm_nodoDivisional(String cm_nodoDivisional) {
        this.cm_nodoDivisional = cm_nodoDivisional;
    }

    /**
     *
     * @return
     */
    public String getCm_nodoUnidadGestion() {
        return cm_nodoUnidadGestion;
    }

    /**
     *
     * @param cm_nodoUnidadGestion
     */
    public void setCm_nodoUnidadGestion(String cm_nodoUnidadGestion) {
        this.cm_nodoUnidadGestion = cm_nodoUnidadGestion;
    }

    /**
     *
     * @return
     */
    public String getCm_nodoZona() {
        return cm_nodoZona;
    }

    /**
     *
     * @param cm_nodoZona
     */
    public void setCm_nodoZona(String cm_nodoZona) {
        this.cm_nodoZona = cm_nodoZona;
    }

    /**
     *
     * @return
     */
    public String getCm_tipoRed() {
        return cm_tipoRed;
    }

    /**
     *
     * @param cm_tipoRed
     */
    public void setCm_tipoRed(String cm_tipoRed) {
        this.cm_tipoRed = cm_tipoRed;
    }

    /**
     *
     * @return
     */
    public String getCm_nodo() {
        return cm_nodo;
    }

    /**
     *
     * @param cm_nodo
     */
    public void setCm_nodo(String cm_nodo) {
        this.cm_nodo = cm_nodo;
    }

    /**
     *
     * @return
     */
    public String getCm_confiabilidad() {
        return cm_confiabilidad;
    }

    /**
     *
     * @param cm_confiabilidad
     */
    public void setCm_confiabilidad(String cm_confiabilidad) {
        this.cm_confiabilidad = cm_confiabilidad;
    }

    /**
     *
     * @return
     */
    public String getCm_estrato() {
        return cm_estrato;
    }

    /**
     *
     * @param cm_estrato
     */
    public void setCm_estrato(String cm_estrato) {
        this.cm_estrato = cm_estrato;
    }

    /**
     *
     * @return
     */
    public String getCm_localidad() {
        return cm_localidad;
    }

    /**
     *
     * @param cm_localidad
     */
    public void setCm_localidad(String cm_localidad) {
        this.cm_localidad = cm_localidad;
    }

    /**
     *
     * @return
     */
    public String getCm_nivelSocio() {
        return cm_nivelSocio;
    }

    /**
     *
     * @param cm_nivelSocio
     */
    public void setCm_nivelSocio(String cm_nivelSocio) {
        this.cm_nivelSocio = cm_nivelSocio;
    }

    /**
     *
     * @return
     */
    public String getCm_revisar() {
        return cm_revisar;
    }

    /**
     *
     * @param cm_revisar
     */
    public void setCm_revisar(String cm_revisar) {
        this.cm_revisar = cm_revisar;
    }

    /**
     *
     * @return
     */
    public String getCm_tipoDireccion() {
        return cm_tipoDireccion;
    }

    /**
     *
     * @param cm_tipoDireccion
     */
    public void setCm_tipoDireccion(String cm_tipoDireccion) {
        this.cm_tipoDireccion = cm_tipoDireccion;
    }

    /**
     *
     * @return
     */
    public BigDecimal getCm_idDireccion() {
        return cm_idDireccion;
    }

    /**
     *
     * @param cm_idDireccion
     */
    public void setCm_idDireccion(BigDecimal cm_idDireccion) {
        this.cm_idDireccion = cm_idDireccion;
    }

    /**
     * @return the nodoUno Direcciones face I Carlos Vilamil 2013-05-11 V 1.1
     */
    public String getNodoUno() {
        return nodoUno;
    }

    /**
     * @param nodoUno the nodoUno to set Direcciones face I Carlos Vilamil
     * 2013-05-11 V 1.1
     */
    public void setNodoUno(String nodoUno) {
        this.nodoUno = nodoUno;
    }

    /**
     * @return the nodoDos Direcciones face I Carlos Vilamil 2013-05-11 V 1.1
     */
    public String getNodoDos() {
        return nodoDos;
    }

    /**
     * @param nodoDos the nodoDos to set Direcciones face I Carlos Vilamil
     * 2013-05-11 V 1.1
     */
    public void setNodoDos(String nodoDos) {
        this.nodoDos = nodoDos;
    }

    /**
     * @return the nodoTres Direcciones face I Carlos Vilamil 2013-05-11 V 1.1
     */
    public String getNodoTres() {
        return nodoTres;
    }

    /**
     * @param nodoTres the nodoTres to set Direcciones face I Carlos Vilamil
     * 2013-05-11 V 1.1
     */
    public void setNodoTres(String nodoTres) {
        this.nodoTres = nodoTres;
    }

    /**
     * @return the cmDirManzanaCatastral
     */
    public String getCmDirManzanaCatastral() {
        return cmDirManzanaCatastral;
    }

    /**
     * @param cmDirManzanaCatastral indica el codigo de la manzana segun
     * catastro
     */
    public void setCmDirManzanaCatastral(String cmDirManzanaCatastral) {
        this.cmDirManzanaCatastral = cmDirManzanaCatastral;
    }

    /**
     * @return the actividadEconomica
     */
    public String getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * @param actividadEconomica indica la actividad economica
     */
    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    /**
     * @return the direccionAlterna
     */
    public String getDireccionAlterna() {
        return direccionAlterna;
    }

    /**
     * @param direccionAlterna indica la direccion alterna otorgada por el cliente
     */
    public void setDireccionAlterna(String direccionAlterna) {
        this.direccionAlterna = direccionAlterna;
    }
    
    /**
     * @return the existencia
     */
    public String getExistencia() {
        return existencia;
    }

    /**
     * @param existencia indica si la direccion existe o no
     */
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }
 
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
}