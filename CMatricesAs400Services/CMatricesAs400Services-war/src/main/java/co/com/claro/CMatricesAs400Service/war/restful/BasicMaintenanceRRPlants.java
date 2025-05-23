/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRPlantaRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRPlantaResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import  javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author JPeña
 */
@Path("basicMaintenanceRRNPlants")
public class BasicMaintenanceRRPlants {
    
    IMantenimientoBasicasRRFacade mantenimientoBasicasRRFacadeLocal;

    @Context
    private UriInfo context;
    InitialContext ic;
    
     /**
     * Crea una nueva instancia de RestMantenimientoPlantMantenice
     *
     * @throws javax.naming.NamingException
     */
    public BasicMaintenanceRRPlants() throws NamingException {
        ic = new InitialContext();
        mantenimientoBasicasRRFacadeLocal = (IMantenimientoBasicasRRFacade) 
        ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }
    
    /**
     * Metodo encargado de Insertar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("plants")
    public MantenimientoBasicoRRBaseResponse crearPlanta(
           MantenimientoBasicoRRPlantaRequest alimentacion) {
        return mantenimientoBasicasRRFacadeLocal.crearPlanta(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletePlants")
    public MantenimientoBasicoRRBaseResponse eliminarPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {
        return mantenimientoBasicasRRFacadeLocal.eliminarPlanta(alimentacion);
    }
    
    /**
     * Metodo encargado de obtener un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("queryPlants")
    public MantenimientoBasicoRRPlantaResponse obtenerPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {
        return mantenimientoBasicasRRFacadeLocal.obtenerPlanta(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar un registro en la tabla Planta
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return BaseResponse Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updatePlants")
    public MantenimientoBasicoRRBaseResponse actualizarPlanta(
            MantenimientoBasicoRRPlantaRequest alimentacion) {
        return mantenimientoBasicasRRFacadeLocal.actualizarPlanta(alimentacion);
    }
    
}
   
