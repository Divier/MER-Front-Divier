/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.IMantenimientoBasicasRRFacade;
import co.com.claro.cmas400.ejb.request.HhppPaginationRequest;
import co.com.claro.cmas400.ejb.respons.HhppPaginationResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
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
@Path("hhppPagination")
public class HhppPagination {
    
    IMantenimientoBasicasRRFacade mantenimientoBasicasRRFacade;
    @Context
    private UriInfo context;
    InitialContext ic;
    
    /**
     * Crea una nueva instancia de HhppPagination
     * @throws javax.naming.NamingException
     */ 
    public HhppPagination() throws NamingException {
        ic = new InitialContext();
        mantenimientoBasicasRRFacade = (IMantenimientoBasicasRRFacade) 
                ic.lookup("java:comp/env/ejb/MantenimientoBasicasRRFacade");
    }
    
     /**
     * Metodo encargado de consultar los registros de la tabla de mantenimiento
     * aliados
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseQueryData Objeto utilizado para capturar los resultados
     * de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("hhppId")
    public HhppPaginationResponse obtenerHhppId(HhppPaginationRequest request) {
        return mantenimientoBasicasRRFacade.obtenerHhpp(request);
    }
}
