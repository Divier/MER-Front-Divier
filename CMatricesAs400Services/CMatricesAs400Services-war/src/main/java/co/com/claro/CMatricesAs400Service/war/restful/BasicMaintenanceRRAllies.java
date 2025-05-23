/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRAliadosRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRAliadosResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
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
@Path("basicMaintenanceRRAllies")
public class BasicMaintenanceRRAllies {

    IMantenimientoBasicasRRFacade mantenimienttoBasicasRRFacadeLocal;
    @Context
    private UriInfo context;
    InitialContext ic;

    /**
     * Crea una nueva instancia de RestOrdenTrabajo
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRAllies() throws NamingException {
        ic = new InitialContext();
        mantenimienttoBasicasRRFacadeLocal = (IMantenimientoBasicasRRFacade) ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de mantenimiento
     * aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRAliadosResponse Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryAllies")
    public MantenimientoBasicoRRAliadosResponse obtenerAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.obtenerAliado(request);
    }

    /**
     * Metodo encargado de crear un registro en la tabla mantenimiento aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para
     * capturar los resultados de laejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allies")
    public MantenimientoBasicoRRBaseResponse crearAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.crearAliado(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla mantenimiento aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para
     * capturar los resultados de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("alliesDelete")
    public MantenimientoBasicoRRBaseResponse eliminarAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.eliminarAliado(request);
    }

    /**
     * Metodo encargado de actualizar un registro en la tabla mantenimiento aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return MantenimientoBasicoRRBaseResponse Objeto utilizado para
     * capturar los resultados de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allies")
    public MantenimientoBasicoRRBaseResponse actualizarAliado(
            MantenimientoBasicoRRAliadosRequest request) {
        return mantenimienttoBasicasRRFacadeLocal.actualizarAliado(request);
    }

}
