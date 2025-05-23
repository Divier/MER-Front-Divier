/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.services;

import co.com.claro.cmas400.ejb.manager.CmOrdenTrabajoManager;
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
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Alquiler
 */
@WebService(serviceName = "OrdeTrabajoService")
@Stateless()
public class OrdeTrabajoService {
    
    private CmOrdenTrabajoManager cmOrdenTrabajo = new CmOrdenTrabajoManager();
    /**
     * Web Service encargado de consultar los registro de la tabla Informacion
     * VT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInformacionVtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionVtQuery")
    public ResponseInformacionVtList informacionVtQuery(
            RequestDataInformacionVt alimentacion) {
        return cmOrdenTrabajo.informacionVtQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar las OTs de un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoEdificioQuery")
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de crear una OT de un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoEdificioAdd")
    public ResponseOtEdificiosList ordenTrabajoEdificioAdd(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioAdd(alimentacion);
    }
    
    /**
     * Web Services encargado de actualizar la informacion de una OT para un 
     * Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoEdificioUpdate")
    public ResponseOtEdificiosList ordenTrabajoEdificioUpdate(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioUpdate(alimentacion);
    }
    
    /**
     * Web Services encargado de cancelar una OT para un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoEdificioDelete")
    public ResponseOtEdificiosList ordenTrabajoEdificioDelete(
            RequestDataOtEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoEdificioDelete(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar las OTs de un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoSubEdificioQuery")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioQuery(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de crear una OT para un SubEdificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoSubEdificioAdd")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioAdd(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioAdd(alimentacion);
    }
    
    /**
     * Web Serivices encargado de modificar una OT para un SubEdificio
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoSubEdificioUpdate")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioUpdate(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioUpdate(alimentacion);
    }
    
    /**
     * Web Services encargado de cancelar una OT para un SubEdificio
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseOtSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "ordenTrabajoSubEdificioDelete")
    public ResponseOtSubEdificiosList ordenTrabajoSubEdificioDelete(
            RequestDataOtSubEdificio alimentacion) {
        return cmOrdenTrabajo.ordenTrabajoSubEdificioDelete(alimentacion);
    }
    
    /**
     * Web Services encargado de generar la lista de ayuda para seleccionar el 
     * Dealer
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseConsultaDealerList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaDealerHelp")
    public ResponseConsultaDealerList consultaDealerHelp(
            RequestDataConsultaDealer alimentacion) {
        return cmOrdenTrabajo.consultaDealerHelp(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar la lista de notas de una Cuenta Matriz en 
     * realizadas en la Orden de Trabajo
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCuentaMatrizListQuery")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizListQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizListQuery(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar la descripcion de una nota existente para
     * Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCuentaMatrizDescripcionQuery")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizDescripcionQuery(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizDescripcionQuery(alimentacion);
    }
    
    /**
     * WebServices encargado de crear una Nota en la Orden de trabajo de una Cuenta
     * Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCuentaMatrizAdd")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizAdd(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de crear una linea en una Nota Existente en la Orden de 
     * trabajo de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCuentaMatrizUpdate")
    public ResponseNotasOtCuentaMatrizList notasOtCuentaMatrizUpdate(
            RequestDataNotasOtCuentaMatriz alimentacion) {
        return cmOrdenTrabajo.notasOtCuentaMatrizUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar la lista de notas de una OT para los 
     * SubEdificios de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCmSubEdificioListQuery")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioListQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioListQuery(alimentacion);
    }
    
    /**
     * WebServices ncargado de consultar la descripcion de una nota existente para
     * un SubEdificion de una Cuenta Matriz 
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCmSubEdificioDescripcionQuery")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioDescripcionQuery(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioDescripcionQuery(alimentacion);
    }
    
    /**
     * WebServices encargado de crear una nota para un SubEdificio de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCmSubEdificioAdd")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioAdd(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de adicionar un linea a un nota ya existente en la 
     * Orden de Trabajo de un SubEdificio de una cuenta matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasOtCmSubEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasOtCmSubEdificioUpdate")
    public ResponseNotasOtCmSubEdificioList notasOtCmSubEdificioUpdate(
            RequestDataNotasOtCmSubEdificio alimentacion) {
        return cmOrdenTrabajo.notasOtCmSubEdificioUpdate(alimentacion);
    }
}