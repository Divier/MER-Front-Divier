/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse;
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
 * @author aleal
 */
@Path("basicMaintenanceRRMunicipalityCenterPopulatedDane")
public class BasicMaintenanceRRMunicipalityCenterPopulatedDane {
    
    IMantenimientoBasicasRRFacade mantenimientoBasicasRRFacade;
    @Context
    private UriInfo context;
    InitialContext ic;
    
    /**
     * Crea una nueva instancia de RestOrdenTrabajo
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRMunicipalityCenterPopulatedDane() throws NamingException {
        ic = new InitialContext();
        mantenimientoBasicasRRFacade = (IMantenimientoBasicasRRFacade) 
                ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }
    
    /**
     * Metodo encargado de obtener los registros de mantenimiento
     * municipio y centros de poblado DANE
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseQueryData Objeto utilizado para capturar los resultados
     * de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("MunicipalitiesCenterPopulatedDaneQuery")
    public MantenimientoBasicoRRMunicipioCentroPobladoDaneResponse obtenerMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRRFacade.obtenerMunicipioCentrosPobladoDane(request);
    }
    
    
    /**
     * Metodo encargado de crear los registros de mantenimiento
     * municipio y centros de poblado DANE
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseQueryData Objeto utilizado para capturar los resultados
     * de laejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("MunicipalitiesCenterPopulatedDane")
    public MantenimientoBasicoRRBaseResponse crearMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRRFacade.crearMunicipioCentrosPobladoDane(request);
    }

    

    /**
     * Metodo encargado de eliminar los registros de mantenimiento
     * municipio y centros de poblado DANE
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseQueryData Objeto utilizado para capturar los resultados
     * de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("MunicipalitiesCenterPopulatedDaneDelete")
    public MantenimientoBasicoRRBaseResponse eliminarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRRFacade.eliminarMunicipioCentrosPobladoDane(request);
    }
    
    
     /**
     * Metodo encargado de actualizar los registros de mantenimiento
     * municipio y centros de poblado DANE
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseQueryData Objeto utilizado para capturar los resultados
     * de laejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("MunicipalitiesCenterPopulatedDane")
    public MantenimientoBasicoRRBaseResponse actualizarMunicipioCentrosPobladoDane(
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest request) {
        return mantenimientoBasicasRRFacade.actualizarMunicipioCentrosPobladoDane(request);
    }
}
