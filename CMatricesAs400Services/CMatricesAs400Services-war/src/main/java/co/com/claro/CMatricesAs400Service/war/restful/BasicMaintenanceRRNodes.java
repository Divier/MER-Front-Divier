/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRNodosRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRNodosResponse;
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
@Path("basicMaintenanceRRNodes")
public class BasicMaintenanceRRNodes {

    IMantenimientoBasicasRRFacade mantenimientoBasicasRRFacade;

    @Context
    private UriInfo context;
    InitialContext ic;

    /**
     * Crea una nueva instancia de RestMantenimientoNodos
     *
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRNodes() throws NamingException {
        ic = new InitialContext();
        mantenimientoBasicasRRFacade = (IMantenimientoBasicasRRFacade) ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }

    /**
     * Metodo encargado de crear un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodes")
    public MantenimientoBasicoRRBaseResponse crearNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.crearNodo(alimentacion);
    }

    /**
     * Metodo encargado de obtener un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRNodosResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodesQuery")
    public MantenimientoBasicoRRNodosResponse obtenerNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.obtenerNodo(alimentacion);
    }

    /**
     * Metodo encargado de actualizar una nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("nodes")
    public MantenimientoBasicoRRBaseResponse actualizarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.actualizarNodo(alimentacion);
    }

    /**
     * Metodo encargado de eliminar un nodo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteNodes")
    public MantenimientoBasicoRRBaseResponse eliminarNodo(
            MantenimientoBasicoRRNodosRequest alimentacion) {
        return mantenimientoBasicasRRFacade.eliminarNodo(alimentacion);
    }
}
