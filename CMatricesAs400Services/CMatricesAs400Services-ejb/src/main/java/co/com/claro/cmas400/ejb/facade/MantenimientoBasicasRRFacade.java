/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

import co.com.claro.cmas400.ejb.manager.MantenimientoBasicasRRManager;
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
import javax.ejb.Stateless;

/**
 *
 * @author jrodriguez
 */
@Stateless
public class MantenimientoBasicasRRFacade implements IMantenimientoBasicasRRFacade {

    private final MantenimientoBasicasRRManager mantenimientoBasicasRR = new MantenimientoBasicasRRManager();

    /* Aliados*/
    @Override
    public MantenimientoBasicoRRAliadosResponse obtenerAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimientoBasicasRR.obtenerAliado(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse crearAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimientoBasicasRR.crearAliado(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimientoBasicasRR.eliminarAliado(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse actualizarAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimientoBasicasRR.actualizarAliado(request);
    }

    /* Tipificacion De Red */
    @Override
    public MantenimientoBasicoRRTipificacionDeRedResponse obtenerTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimientoBasicasRR.obtenerTipificacionDeRed(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse crearTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimientoBasicasRR.crearTipificacionDeRed(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimientoBasicasRR.eliminarTipificacionDeRed(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse actualizarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimientoBasicasRR.actualizarTipificacionDeRed(request);
    }

    /* Nodos */
    @Override
    public MantenimientoBasicoRRNodosResponse obtenerNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRR.obtenerNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse crearNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRR.crearNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse actualizarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRR.actualizarNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRR.eliminarNodo(alimentacion);
    }

    /* Estado nodos */
    @Override
    public MantenimientoBasicoRRBaseResponse crearEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRR.crearEstadoNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRR.eliminarEstadoNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRREstadoNodosResponse obtenerEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRR.obtenerEstadoNodo(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse actualizarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRR.actualizarEstadoNodo(alimentacion);
    }

    /* Plant Mantenice*/ 
    @Override
    public MantenimientoBasicoRRBaseResponse crearPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion){
        return mantenimientoBasicasRR.crearPlanta(alimentacion);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion){
        return mantenimientoBasicasRR.eliminarPlanta(alimentacion);
    }
    
    @Override
    public MantenimientoBasicoRRPlantaResponse obtenerPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion){
        return mantenimientoBasicasRR.obtenerPlanta(alimentacion);
    }
   
    @Override
    public MantenimientoBasicoRRBaseResponse actualizarPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion){
        return mantenimientoBasicasRR.actualizarPlanta(alimentacion);
    }
    
    /* Mantenimiento Homolo*/
    @Override
    public MantenimientoBasicoRRBaseResponse crearHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion){
        return mantenimientoBasicasRR.crearHomologacionDane(alimentacion);
    }
    
    @Override
    public MantenimientoBasicoRRBaseResponse eliminarHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion){
        return mantenimientoBasicasRR.eliminarHomologacionDane(alimentacion);
    }
    
    @Override
    public MantenimientoBasicoRRHomologacionResponse obtenerHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion){
        return mantenimientoBasicasRR.obtenerHomologacionDane(alimentacion);
    }
    
    @Override
    public MantenimientoBasicoRRBaseResponse actualizarHomologacionDane(
           MantenimientoBasicoRRHomologacionRequest alimentacion){
         return mantenimientoBasicasRR.actualizarHomologacionDane(alimentacion);
    }
    
    /*
    * Mantenimiento municipio centros poblados DANE
     */

    @Override
    public MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse obtenerMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRR.obtenerMunicipioCentrosPobladoDane(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse crearMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRR.crearMunicipioCentrosPobladoDane(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse eliminarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRR.eliminarMunicipioCentrosPobladoDane(request);
    }

    @Override
    public MantenimientoBasicoRRBaseResponse actualizarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRR.actualizarMunicipioCentrosPobladoDane(request);
    }
    
    @Override
    public HhppPaginationResponse obtenerHhpp(HhppPaginationRequest request) {
        return mantenimientoBasicasRR.consultaHhpp(request);
    }
}
