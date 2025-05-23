/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

import co.com.claro.cmas400.ejb.request.HhppPaginationRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRAliadosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRREstadoNodosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRHomologacionRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRNodosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRPlantaRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRTipificacionDeRedRequest;
import co.com.claro.cmas400.ejb.respons.HhppPaginationResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRAliadosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRREstadoNodosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRHomologacionResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRNodosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRPlantaResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRTipificacionDeRedResponse;
import javax.ejb.Local;

/**
 *
 * @author jrodriguez
 */
@Local
public interface IMantenimientoBasicasRRFacade {

    /* Aliados*/
    public MantenimientoBasicoRRAliadosResponse obtenerAliado(
            MantenimientoBasicoRRAliadosRequest request);

    public MantenimientoBasicoRRBaseResponse crearAliado(
            MantenimientoBasicoRRAliadosRequest request);

    public MantenimientoBasicoRRBaseResponse eliminarAliado(
            MantenimientoBasicoRRAliadosRequest request);

    public MantenimientoBasicoRRBaseResponse actualizarAliado(
            MantenimientoBasicoRRAliadosRequest request);

    /* Tipificacion De Red */
    public MantenimientoBasicoRRTipificacionDeRedResponse obtenerTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request);

    public MantenimientoBasicoRRBaseResponse crearTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request);

    public MantenimientoBasicoRRBaseResponse eliminarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request);

    public MantenimientoBasicoRRBaseResponse actualizarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request);

    /* Nodos */
    public MantenimientoBasicoRRBaseResponse crearNodo(
            MantenimientoBasicoRRNodosRequest alimentacion);

    public MantenimientoBasicoRRNodosResponse obtenerNodo(
            MantenimientoBasicoRRNodosRequest alimentacion);

    public MantenimientoBasicoRRBaseResponse actualizarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion);

    public MantenimientoBasicoRRBaseResponse eliminarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion);

    /* Estado Nodos */
    public MantenimientoBasicoRRBaseResponse crearEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion);

    public MantenimientoBasicoRREstadoNodosResponse obtenerEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion);

    public MantenimientoBasicoRRBaseResponse actualizarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion);

    public MantenimientoBasicoRRBaseResponse eliminarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion);

    /*Mantenimiento Plantas*/
    
    public MantenimientoBasicoRRBaseResponse crearPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion);

    public MantenimientoBasicoRRBaseResponse eliminarPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion);
    
    public MantenimientoBasicoRRPlantaResponse obtenerPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion);
    
    public MantenimientoBasicoRRBaseResponse actualizarPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion);
    
    /*Mantenimiento Homologacion*/
    public MantenimientoBasicoRRBaseResponse crearHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion);
    
    public MantenimientoBasicoRRBaseResponse eliminarHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion);
    
    public MantenimientoBasicoRRHomologacionResponse obtenerHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion);
    
    public MantenimientoBasicoRRBaseResponse actualizarHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion);

    
     /* 
    * Mantenimiento Municipio y centros de poblado DANE 
     */
    public MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse obtenerMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request);

    public MantenimientoBasicoRRBaseResponse crearMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request);

    public MantenimientoBasicoRRBaseResponse eliminarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request);

    public MantenimientoBasicoRRBaseResponse actualizarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request);

    /* Paginacion HHPP */
    public HhppPaginationResponse obtenerHhpp(HhppPaginationRequest request);
}
