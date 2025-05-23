/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRREstadoNodosRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRREstadoNodosResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Felipe Garcia
 */
@Path("basicMaintenanceRRStateNodes")
public class BasicMaintenanceRRStateNodes {
    
    IMantenimientoBasicasRRFacade mantenimientoBasicasRRFacade;
    
    @Context
    private UriInfo context;
    InitialContext ic;
    
     /**
     * Crea una nueva instancia de RestMantenimientoNodos
     *
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRStateNodes() throws NamingException {
        ic = new InitialContext();
        mantenimientoBasicasRRFacade = (IMantenimientoBasicasRRFacade) ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }
    
    /**
     * Metodo encargado de crear el estado de un nodo 
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodesState")
    public MantenimientoBasicoRRBaseResponse crearEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.crearEstadoNodo(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar el estado de un nodo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodesStateDelete")
    public MantenimientoBasicoRRBaseResponse eliminarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.eliminarEstadoNodo(alimentacion);
    }
    
    /**
     * Metodo encargado de obtener el estado de un nodo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRREstadoNodosResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodesStateQuery")
    public MantenimientoBasicoRREstadoNodosResponse obtenerEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.obtenerEstadoNodo(alimentacion);
    }
    
     /**
     * Metodo encargado de actualizar el estado de un nodo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoNodosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodesState")
    public MantenimientoBasicoRRBaseResponse actualizarEstadoNodo(
            MantenimientoBasicoRREstadoNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.actualizarEstadoNodo(alimentacion);
    }
}
