/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.OrdenTrabajoFacadeLocal;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaDealer;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionVt;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCmSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataOtEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataOtSubEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaDealerList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionVtList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCmSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseOtSubEdificiosList;
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
 * REST Web Service
 *
 * @author Alquiler 
 */
@Path("RestOrdenTrabajoService")
public class RestOrdenTrabajo {

    OrdenTrabajoFacadeLocal ordenTrabajoFacadeLocal;
    @Context
    private UriInfo context;
    InitialContext ic;

    /**
     * Crea una nueva instancia de RestOrdenTrabajo
     * @throws javax.naming.NamingException
     */
    public RestOrdenTrabajo() throws NamingException {
        ic = new InitialContext();
        ordenTrabajoFacadeLocal = (OrdenTrabajoFacadeLocal) ic.lookup("java:comp/env/ejb/OrdenTrabajoFacadeLocal");
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Informacion VT
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseInformacionVtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionVtQuery/")
    public ResponseInformacionVtList informacionVtQuery(
            RequestDataInformacionVt request) {
        return ordenTrabajoFacadeLocal.informacionVtQuery(request);
    }
    
    /**
     * Metodo encargado de consultar las OTs de un Edificio
     * 
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoEdificioQuery/")
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(
            RequestDataOtEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoEdificioQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de crear una OT para un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoEdificioAdd/")
    public ResponseOtEdificiosList ordenTrabajoEdificioAdd(
            RequestDataOtEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoEdificioAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar una OT para un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoEdificioUpdate/")
    public ResponseOtEdificiosList ordenTrabajoEdificioUpdate(
            RequestDataOtEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoEdificioUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de cancelar una OT para un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoEdificioDelete/")
    public ResponseOtEdificiosList ordenTrabajoEdificioDelete(
            RequestDataOtEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoEdificioDelete(alimentacion);
    }
    
    /**
     * Metodo encarcado de consultar las OTs de un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoSubEdificioQuery/")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioQuery(
            RequestDataOtSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoSubEdificioQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de crear una OT para un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los 
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoSubEdificioAdd/")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioAdd(
            RequestDataOtSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoSubEdificioAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de modificar una OT para un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoSubEdificioUpdate/")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioUpdate(
            RequestDataOtSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoSubEdificioUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de cancelar una OT de un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ordenTrabajoSubEdificioDelete/")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioDelete(
            RequestDataOtSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.ordenTrabajoSubEdificioDelete(alimentacion);
    }
    
    /**
     * Metodo encargado de generar la lista de ayuda para seleccionar el Dealer
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaDealerList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaDealerHelp/")
    public ResponseConsultaDealerList consultaDealerHelp(
            RequestDataConsultaDealer alimentacion) {
        return ordenTrabajoFacadeLocal.consultaDealerHelp(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar la lista de notas de una Cuenta Matriz en 
     * realizadas en la Orden de Trabajo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCuentaMatrizListQuery/")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizListQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCuentaMatrizListQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar la descripcion de una nota existente para
     * Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCuentaMatrizDescripcionQuery/")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizDescripcionQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCuentaMatrizDescripcionQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de crear una Nota en la Orden de trabajo de una Cuenta
     * Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCuentaMatrizDescripcionAdd/")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizAdd(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de crear una linea en una Nota Existente en la Orden de 
     * trabajo de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCuentaMatrizUpdate/")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizUpdate(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCuentaMatrizUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar la lista de notas de una OT para los 
     * SubEdificios de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCmSubEdificioListQuery/")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioListQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCmSubEdificioListQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar la descripcion de una nota existente para
     * un SubEdificion de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCmSubEdificioDescripcionQuery/")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioDescripcionQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCmSubEdificioDescripcionQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de crear una nota para un SubEdificio de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCmSubEdificioAdd/")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioAdd(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCmSubEdificioAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de adicionar un linea a un nota ya existente en la Orden
     * de Trabajo de un SubEdificio de una cuenta matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasOtCmSubEdificioUpdate/")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioUpdate(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return ordenTrabajoFacadeLocal.notasOtCmSubEdificioUpdate(alimentacion);
    }
}
