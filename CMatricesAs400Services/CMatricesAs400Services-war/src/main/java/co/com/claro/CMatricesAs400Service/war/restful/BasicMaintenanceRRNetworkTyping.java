/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRTipificacionDeRedRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRTipificacionDeRedResponse;
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
 * REST Web Service
 *
 * @author OVelasquez
 */
@Path("basicMaintenanceRRNetworkTypifications")
public class BasicMaintenanceRRNetworkTyping {

    IMantenimientoBasicasRRFacade mantenimienttoBasicasRRFacadeLocal;
    @Context
    private UriInfo context;
    InitialContext ic;

    /**
     * Crea una nueva instancia de RestOrdenTrabajo
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRNetworkTyping() throws NamingException {
        ic = new InitialContext();
        mantenimienttoBasicasRRFacadeLocal = (IMantenimientoBasicasRRFacade) 
                ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRTipificacionDeRedResponse Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryNetworkTypifications")
    public MantenimientoBasicoRRTipificacionDeRedResponse obtenerTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.obtenerTipificacionDeRed(request);
    }

    /**
     * Metodo encargado de crear un registro en la tabla de mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("networkTypifications")
    public MantenimientoBasicoRRBaseResponse crearTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.crearTipificacionDeRed(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteNetworkTypifications")
    public MantenimientoBasicoRRBaseResponse eliminarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.eliminarTipificacionDeRed(request);
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla mantenimiento
     * tipificacion de red
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para capturar
     * los resultados de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("networkTypifications")
    public MantenimientoBasicoRRBaseResponse actualizarTipificacionDeRed(
            MantenimientoBasicoRRTipificacionDeRedRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.actualizarTipificacionDeRed(request);
    }

}
