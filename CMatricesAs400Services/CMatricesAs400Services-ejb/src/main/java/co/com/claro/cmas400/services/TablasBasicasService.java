/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.services;

import co.com.claro.cmas400.ejb.manager.CmTablasBasicasManager;
import co.com.claro.cmas400.ejb.request.RequestDataAdminCompany;
import co.com.claro.cmas400.ejb.request.RequestDataAlimentacionElectrica;
import co.com.claro.cmas400.ejb.request.RequestDataAsignarAsesorAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataCiaAscensores;
import co.com.claro.cmas400.ejb.request.RequestDataCodigoBlackList;
import co.com.claro.cmas400.ejb.request.RequestDataConstructoras;
import co.com.claro.cmas400.ejb.request.RequestDataEstadoResultadoOt;
import co.com.claro.cmas400.ejb.request.RequestDataEstrato;
import co.com.claro.cmas400.ejb.request.RequestDataManttoAsesorGestionDeAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataManttoCompetencia;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEstadoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEstadosAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataManttoInfoNodo;
import co.com.claro.cmas400.ejb.request.RequestDataManttoInformacionBarrios;
import co.com.claro.cmas400.ejb.request.RequestDataManttoMaterialProveedor;
import co.com.claro.cmas400.ejb.request.RequestDataManttoMateriales;
import co.com.claro.cmas400.ejb.request.RequestDataManttoNodos;
import co.com.claro.cmas400.ejb.request.RequestDataManttoPuntoInicial;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoCompetencia;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoDistribucionRedInterna;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoTipoNotas;
import co.com.claro.cmas400.ejb.request.RequestDataMotivosCambioFecha;
import co.com.claro.cmas400.ejb.request.RequestDataOrigenDatos;
import co.com.claro.cmas400.ejb.request.RequestDataProductos;
import co.com.claro.cmas400.ejb.request.RequestDataProveedores;
import co.com.claro.cmas400.ejb.request.RequestDataRazonArreglo;
import co.com.claro.cmas400.ejb.request.RequestDataSupervisorAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataTipoAcometida;
import co.com.claro.cmas400.ejb.request.RequestDataTipoMateriales;
import co.com.claro.cmas400.ejb.request.RequestDataTipoProyecto;
import co.com.claro.cmas400.ejb.request.RequestDataTipoTrabajo;
import co.com.claro.cmas400.ejb.request.RequestDataUbicacionCaja;
import co.com.claro.cmas400.ejb.respons.ResponseAdminCompanyList;
import co.com.claro.cmas400.ejb.respons.ResponseAsignarAsesorAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseCiaAscensoresList;
import co.com.claro.cmas400.ejb.respons.ResponseCodigoBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.cmas400.ejb.respons.ResponseEstadoResultadoOtList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoAlimentacionElectList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoAsesorGestionDeAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstadoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstadosAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEstratoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoInfoNodoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoInformacionBarriosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMaterialProveedorList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMaterialesList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoMotivosCambioFechaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoNodosList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoPuntoInicialList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoDistribucionRedInternaList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoNotasList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoTipoTrabajoList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoUbicacionCajaList;
import co.com.claro.cmas400.ejb.respons.ResponseOrigenDatosList;
import co.com.claro.cmas400.ejb.respons.ResponseProductosList;
import co.com.claro.cmas400.ejb.respons.ResponseProveedoresList;
import co.com.claro.cmas400.ejb.respons.ResponseRazonArregloList;
import co.com.claro.cmas400.ejb.respons.ResponseSupervisorAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoAcometidaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoCompetenciaList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoMaterialesList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoProyectoList;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Alquiler
 */
@WebService(serviceName = "TablasBasicasService")
@Stateless()
public class TablasBasicasService {
    
    /**
     * Web Service encargado de adicionar un registro a la tabla de alimentacion
     * electrica
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAlimentacionElectAdd")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectAdd(
            @WebParam(name = "alimentacionRequest") RequestDataAlimentacionElectrica alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAlimentacionElectAdd(alimentacionRequest);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * alimentacion electrica
     *
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAlimentacionElectQuery")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAlimentacionElectQuery();
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * alimentacion electrica
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAlimentacionElectUpdate")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectUpdate(
            @WebParam(name = "alimentacionRequest") RequestDataAlimentacionElectrica alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAlimentacionElectUpdate(alimentacionRequest);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * alimentacion electrica
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAlimentacionElectDelete")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectDelete(
            @WebParam(name = "alimentacionRequest") RequestDataAlimentacionElectrica alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAlimentacionElectDelete(alimentacionRequest);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Compañia
     * Administracion
     *
     * @param alimentacionRequest
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAdminCompanyQuery")
    public ResponseAdminCompanyList manttoAdminCompanyQuery(
            RequestDataAdminCompany alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAdminCompanyQuery(alimentacionRequest);
    }

    /**
     * Web Services encargado de crear un registro de la tabla de Compañia
     * Administracion
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAdminCompanyAdd")
    public ResponseAdminCompanyList manttoAdminCompanyAdd(
            @WebParam(name = "alimentacionRequest") RequestDataAdminCompany alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAdminCompanyAdd(alimentacionRequest);
    }

    /**
     * Web Services encargado de modificar un registro de la tabla de Compañia
     * Administracion
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAdminCompanyUpdate")
    public ResponseAdminCompanyList manttoAdminCompanyUpdate(
            @WebParam(name = "alimentacionRequest") RequestDataAdminCompany alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAdminCompanyUpdate(alimentacionRequest);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de Compañia
     * Administracion
     *
     * @param alimentacionRequest Objeto que almacena los datos de la
     * alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAdminCompanyDelete")
    public ResponseAdminCompanyList manttoAdminCompanyDelete(
            @WebParam(name = "alimentacionRequest") RequestDataAdminCompany alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAdminCompanyDelete(alimentacionRequest);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Compañias de Ascensores
     *
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCiaAscensoresQuery")
    public ResponseCiaAscensoresList manttoCiaAscensoresQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCiaAscensoresQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCiaAscensoresAdd")
    public ResponseCiaAscensoresList manttoCiaAscensoresAdd(
            RequestDataCiaAscensores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCiaAscensoresAdd(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Estados
     * de Edificio
     *
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadoEdificioQuery")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadoEdificioQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Estados de
     * Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadoEdificioAdd")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioAdd(
            RequestDataManttoEstadoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadoEdificioAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de Estados
     * Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadoEdificioDelete")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioDelete(
            @WebParam(name = "alimentacion") RequestDataManttoEstadoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadoEdificioDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de Estados
     * Edificio
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadoEdificioUpdate")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoEstadoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadoEdificioUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Estados
     * Edificio para las pantallas de ayuda
     *
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadoEdificioHelp")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioHelp() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadoEdificioHelp();
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Estado
     * Resultado OT
     *
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "estadoResultadoOtQuery")
    public ResponseEstadoResultadoOtList estadoResultadoOtQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.estadoResultadoOtQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Estado
     * Resultado OT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "estadoResultadoOtAdd")
    public ResponseEstadoResultadoOtList estadoResultadoOtAdd(
            RequestDataEstadoResultadoOt alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.estadoResultadoOtAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de Estado
     * Resultado OT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "estadoResultadoOtDelete")
    public ResponseEstadoResultadoOtList estadoResultadoOtDelete(
            @WebParam(name = "alimentacion") RequestDataEstadoResultadoOt alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.estadoResultadoOtDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla Estado
     * Resultado OT
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "estadoResultadoOtUpdate")
    public ResponseEstadoResultadoOtList estadoResultadoOtUpdate(
            @WebParam(name = "alimentacion") RequestDataEstadoResultadoOt alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.estadoResultadoOtUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla
     * Mantenimiento Competencia
     *
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaQuery")
    public ResponseManttoCompetenciaList manttoCompetenciaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCompetenciaQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Mantenimiento
     * Competencia
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaAdd")
    public ResponseManttoCompetenciaList manttoCompetenciaAdd(
            RequestDataManttoCompetencia alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCompetenciaAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * Mantenimiento Competencia
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaDelete")
    public ResponseManttoCompetenciaList manttoCompetenciaDelete(
            @WebParam(name = "alimentacion") RequestDataManttoCompetencia alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCompetenciaDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla
     * Mantenimiento Competencia
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaUpdate")
    public ResponseManttoCompetenciaList manttoCompetenciaUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoCompetencia alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCompetenciaUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Productos
     *
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "productosQuery")
    public ResponseProductosList productosQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.productosQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Productos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "productosAdd")
    public ResponseProductosList productosAdd(
            RequestDataProductos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.productosAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla Productos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "productosDelete")
    public ResponseProductosList productosDelete(
            @WebParam(name = "alimentacion") RequestDataProductos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.productosDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla Productos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "productosUpdate")
    public ResponseProductosList productosUpdate(
            @WebParam(name = "alimentacion") RequestDataProductos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.productosUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Proveedores
     *
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "proveedoresQuery")
    public ResponseProveedoresList proveedoresQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.proveedoresQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de Proveedores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "proveedoresAdd")
    public ResponseProveedoresList proveedoresAdd(
            RequestDataProveedores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.proveedoresAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de Proveedores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "proveedoresDelete")
    public ResponseProveedoresList proveedoresDelete(
            @WebParam(name = "alimentacion") RequestDataProveedores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.proveedoresDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * Proveedores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "proveedoresUpdate")
    public ResponseProveedoresList proveedoresUpdate(
            @WebParam(name = "alimentacion") RequestDataProveedores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.proveedoresUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento Punto Inicial
     *
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoPuntoInicialQuery")
    public ResponseManttoPuntoInicialList manttoPuntoInicialQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoPuntoInicialQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoPuntoInicialAdd")
    public ResponseManttoPuntoInicialList manttoPuntoInicialAdd(
            RequestDataManttoPuntoInicial alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoPuntoInicialAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * Mantenimiento Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoPuntoInicialDelete")
    public ResponseManttoPuntoInicialList manttoPuntoInicialDelete(
            @WebParam(name = "alimentacion") RequestDataManttoPuntoInicial alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoPuntoInicialDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * Mantenimiento Punto Inicial
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoPuntoInicialUpdate")
    public ResponseManttoPuntoInicialList manttoPuntoInicialUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoPuntoInicial alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoPuntoInicialUpdate(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCiaAscensoresUpdate")
    public ResponseCiaAscensoresList manttoCiaAscensoresUpdate(
            RequestDataCiaAscensores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCiaAscensoresUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Compañias de
     * Ascensores
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCiaAscensoresDelete")
    public ResponseCiaAscensoresList manttoCiaAscensoresDelete(
            RequestDataCiaAscensores alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCiaAscensoresDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros en la tabla Razon
     * Arreglo
     *
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoRazonArregloQuery")
    public ResponseRazonArregloList manttoRazonArregloQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoRazonArregloQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Razon Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoRazonArregloAdd")
    public ResponseRazonArregloList manttoRazonArregloAdd(
            RequestDataRazonArreglo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoRazonArregloAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Razon del
     * Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoRazonArregloUpdate")
    public ResponseRazonArregloList manttoRazonArregloUpdate(
            RequestDataRazonArreglo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoRazonArregloUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Razon del
     * Arreglo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoRazonArregloDelete")
    public ResponseRazonArregloList manttoRazonArregloDelete(
            RequestDataRazonArreglo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoRazonArregloDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registro en la tabla BlackList
     *
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCodigoBlackListQuery")
    public ResponseCodigoBlackList manttoCodigoBlackListQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCodigoBlackListQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla BlackList
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCodigoBlackListAdd")
    public ResponseCodigoBlackList manttoCodigoBlackListAdd(
            RequestDataCodigoBlackList alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCodigoBlackListAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro de la tabla BlackList
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCodigoBlackListUpdate")
    public ResponseCodigoBlackList manttoCodigoBlackListUpdate(
            RequestDataCodigoBlackList alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCodigoBlackListUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla BlackList
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCodigoBlackListDelete")
    public ResponseCodigoBlackList manttoCodigoBlackListDelete(
            RequestDataCodigoBlackList alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoCodigoBlackListDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Origen
     * Datos
     *
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoOrigenDatosQuery")
    public ResponseOrigenDatosList manttoOrigenDatosQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoOrigenDatosQuery();
    }

    /**
     * Web Services encargado de insertar un nuevo registro de la tabla Origen
     * Datos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoOrigenDatosAdd")
    public ResponseOrigenDatosList manttoOrigenDatosAdd(
            RequestDataOrigenDatos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoOrigenDatosAdd(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla Origen Datos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoOrigenDatosUpdate")
    public ResponseOrigenDatosList manttoOrigenDatosUpdate(
            RequestDataOrigenDatos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoOrigenDatosUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla Origen Datos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoOrigenDatosDelete")
    public ResponseOrigenDatosList manttoOrigenDatosDelete(
            RequestDataOrigenDatos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoOrigenDatosDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Tipo
     * Acometida
     *
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoAcometidaQuery")
    public ResponseTipoAcometidaList manttoTipoAcometidaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoAcometidaQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Tipo Acometida
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoAcometidaAdd")
    public ResponseTipoAcometidaList manttoTipoAcometidaAdd(
            RequestDataTipoAcometida alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoAcometidaAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Tipo
     * Acometida
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoAcometidaUpdate")
    public ResponseTipoAcometidaList manttoTipoAcometidaUpdate(
            RequestDataTipoAcometida alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoAcometidaUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Tipo Acometida
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoAcometidaDelete")
    public ResponseTipoAcometidaList manttoTipoAcometidaDelete(
            RequestDataTipoAcometida alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoAcometidaDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Tipo
     * Materiales
     *
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoMaterialesQuery")
    public ResponseTipoMaterialesList manttoTipoMaterialesQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoMaterialesQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Tipo
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoMaterialesAdd")
    public ResponseTipoMaterialesList manttoTipoMaterialesAdd(
            RequestDataTipoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoMaterialesAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Tipo
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoMaterialesUpdate")
    public ResponseTipoMaterialesList manttoTipoMaterialesUpdate(
            RequestDataTipoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoMaterialesUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Tipo
     * Materiales
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoMaterialesDelete")
    public ResponseTipoMaterialesList manttoTipoMaterialesDelete(
            RequestDataTipoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoMaterialesDelete(alimentacion);
    }


    /**
     * Web Services encargado de consultar los registros de la tabla Tipo
     * Proyecto
     *
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoProyectoQuery")
    public ResponseTipoProyectoList manttoTipoProyectoQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoProyectoQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoProyectoAdd")
    public ResponseTipoProyectoList manttoTipoProyectoAdd(
            RequestDataTipoProyecto alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoProyectoAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoProyectoUpdate")
    public ResponseTipoProyectoList manttoTipoProyectoUpdate(
            RequestDataTipoProyecto alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoProyectoUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Tipo Proyecto
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoProyectoDelete")
    public ResponseTipoProyectoList manttoTipoProyectoDelete(
            RequestDataTipoProyecto alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoProyectoDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Informacion
     * Nodos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoNodosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoNodosQuery")
    public ResponseManttoNodosList manttoNodosQuery(
            RequestDataManttoNodos alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoNodosQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento de tipo de distribución de red interna.
     *
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoDistribucionRedInternaQuery")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoDistribucionRedInternaQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento de tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoDistribucionRedInternaAdd")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaAdd(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoDistribucionRedInternaAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * Mantenimiento de tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoDistribucionRedInternaDelete")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaDelete(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoDistribucionRedInternaDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * Mantenimiento de tipo de distribución de red interna.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoDistribucionRedInternaUpdate")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoDistribucionRedInternaUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento de tipos de edificios.
     *
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoEdificioQuery")
    public ResponseManttoTipoEdificioList manttoTipoEdificioQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoEdificioQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento de tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoEdificioAdd")
    public ResponseManttoTipoEdificioList manttoTipoEdificioAdd(
            RequestDataManttoTipoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoEdificioAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * Mantenimiento de tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoEdificioDelete")
    public ResponseManttoTipoEdificioList manttoTipoEdificioDelete(
            @WebParam(name = "alimentacion") RequestDataManttoTipoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoEdificioDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * Mantenimiento de tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoEdificioUpdate")
    public ResponseManttoTipoEdificioList manttoTipoEdificioUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoTipoEdificio alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoEdificioUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento de tipos de Notas.
     *
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoNotasQuery")
    public ResponseManttoTipoNotasList manttoTipoNotasQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoNotasQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento de tipos de Notas.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoNotasAdd")
    public ResponseManttoTipoNotasList manttoTipoNotasAdd(
            RequestDataManttoTipoNotas alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoNotasAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla de
     * Mantenimiento de tipos de Notas.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoNotasDelete")
    public ResponseManttoTipoNotasList manttoTipoNotasDelete(
            @WebParam(name = "alimentacion") RequestDataManttoTipoNotas alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoNotasDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * Mantenimiento de tipos de edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoNotasUpdate")
    public ResponseManttoTipoNotasList manttoTipoNotasUpdate(
            @WebParam(name = "alimentacion") RequestDataManttoTipoNotas alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoNotasUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Tipo de
     * Competencia
     *
     * @return ResponseTipoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "tipoCompetenciaQuery")
    public ResponseTipoCompetenciaList tipoCompetenciaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.tipoCompetenciaQuery();
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento Edificios
     *
     * @param alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificiosQuery")
    public ResponseManttoEdificiosList manttoEdificiosQuery(
            RequestDataManttoEdificios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEdificiosQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificiosAdd")
    public ResponseManttoEdificiosList manttoEdificiosAdd(
            RequestDataManttoEdificios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEdificiosAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de
     * Mantenimiento Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificiosDelete")
    public ResponseManttoEdificiosList manttoEdificiosDelete(
            RequestDataManttoEdificios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEdificiosDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro en la tabla de
     * Mantenimiento Edificios.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificiosUpdate")
    public ResponseManttoEdificiosList manttoEdificiosUpdate(
            RequestDataManttoEdificios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEdificiosUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento Materiales
     *
     * @param alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialesQuery")
    public ResponseManttoMaterialesList manttoMaterialesQuery(
            RequestDataManttoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialesQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento Materiales.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialesAdd")
    public ResponseManttoMaterialesList manttoMaterialesAdd(
            RequestDataManttoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialesAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de
     * Mantenimiento Materiales.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialesDelete")
    public ResponseManttoMaterialesList manttoMaterialesDelete(
            RequestDataManttoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialesDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro en la tabla de
     * Mantenimiento Materiales.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialesUpdate")
    public ResponseManttoMaterialesList manttoMaterialesUpdate(
            RequestDataManttoMateriales alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialesUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento Material Proveedor
     *
     * @param alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialProveedorQuery")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorQuery(
            RequestDataManttoMaterialProveedor alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialProveedorQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento Material Proveedor.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialProveedorAdd")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorAdd(
            RequestDataManttoMaterialProveedor alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialProveedorAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de
     * Mantenimiento Material Proveedor.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialProveedorDelete")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorDelete(
            RequestDataManttoMaterialProveedor alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialProveedorDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro en la tabla de
     * Mantenimiento Material Proveddor.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMaterialProveedorUpdate")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorUpdate(
            RequestDataManttoMaterialProveedor alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMaterialProveedorUpdate(alimentacion);
    }

    /* Web Services encargado de consultar los registros en la tabla de
     * Supervisor Avanzado
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */

    @WebMethod(operationName = "manttoSupervisorAvanzadaQuery")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoSupervisorAvanzadaQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de Supervisor
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoSupervisorAvanzadaAdd")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaAdd(
            RequestDataSupervisorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoSupervisorAvanzadaAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Supervisor
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoSupervisorAvanzadaUpdate")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaUpdate(
            RequestDataSupervisorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoSupervisorAvanzadaUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Supervisor
     * Avanzada
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoSupervisorAvanzadaDelete")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaDelete(
            RequestDataSupervisorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoSupervisorAvanzadaDelete(alimentacion);
    }

    /* Web Services encargado de consultar los registros en la tabla de
     * Constructoras.
     * @return ResponseConstructorasList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "constructorasQuery")
    public ResponseConstructorasList constructorasQuery(
            RequestDataConstructoras alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.constructorasQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Constructoras.
     *
     *
     * /**
     * Web Services encargado de consultar los registros de la tabla tipo
     * Estrato
     *
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstratoQuery")
    public ResponseManttoEstratoList manttoEstratoQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstratoQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseManttoEstratoList manttoEstratoAdd(
            RequestDataEstrato alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstratoAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro de la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstratoUpdate")
    public ResponseManttoEstratoList manttoEstratoUpdate(
            RequestDataEstrato alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstratoUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla Tipo Estrato
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstratoDelete")
    public ResponseManttoEstratoList manttoEstratoDelete(
            RequestDataEstrato alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstratoDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registro de la tabla Tipo Trabajo
     *
     * @param alimentacion
     * @return ResponseTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "constructorasAdd")
    public ResponseConstructorasList constructorasAdd(
            RequestDataConstructoras alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.constructorasAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de
     * Constructoras.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "constructorasDelete")
    public ResponseConstructorasList constructorasDelete(
            RequestDataConstructoras alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.constructorasDelete(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro en la tabla de
     * Constructoras.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "constructorasUpdate")
    public ResponseConstructorasList constructorasUpdate(
            RequestDataConstructoras alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.constructorasUpdate(alimentacion);

    }

    @WebMethod(operationName = "manttoTipoTrabajoQuery")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoTrabajoQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoTrabajoAdd")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoAdd(
            RequestDataTipoTrabajo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoTrabajoAdd(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoTrabajoUpdate")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoUpdate(
            RequestDataTipoTrabajo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoTrabajoUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro de la tabla Tipo Trabajo
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoTrabajoDelete")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoDelete(
            RequestDataTipoTrabajo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoTrabajoDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla Ubicacion
     * Caja
     *
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoUbicacionCajaQuery")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoUbicacionCajaQuery();
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Ubicacion Caja
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoUbicacionCajaAdd")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaAdd(
            RequestDataUbicacionCaja alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoUbicacionCajaAdd(alimentacion);
    }

    /**
     * Web Services encargado de actualizar un registro en la tabla Ubicacion
     * Caja
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoUbicacionCajaUpdate")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaUpdate(
            RequestDataUbicacionCaja alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoUbicacionCajaUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Ubicacion Caja
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoUbicacionCajaDelete")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaDelete(
            RequestDataUbicacionCaja alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoUbicacionCajaDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar en la tabla Mantenimiento Información
     * Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionNodoQuery")
    public ResponseManttoInfoNodoList manttoInformacionNodoQuery(
            RequestDataManttoInfoNodo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionNodoQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionNodoAdd")
    public ResponseManttoInfoNodoList manttoInformacionNodoAdd(
            RequestDataManttoInfoNodo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionNodoAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionNodoUpdate")
    public ResponseManttoInfoNodoList manttoInformacionNodoUpdate(
            RequestDataManttoInfoNodo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionNodoUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Mantenimiento
     * Información Nodo.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionNodoDelete")
    public ResponseManttoInfoNodoList manttoInformacionNodoDelete(
            RequestDataManttoInfoNodo alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionNodoDelete(alimentacion);

    }

    /**
     * Web Services encargado de consultar los registros de la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucin del PCML
     */
    @WebMethod(operationName = "manttoInformacionBarriosQuery")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosQuery(
            RequestDataManttoInformacionBarrios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionBarriosQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucin del PCML
     */
    @WebMethod(operationName = "manttoInformacionBarriosAdd")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosAdd(
            RequestDataManttoInformacionBarrios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionBarriosAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionBarriosUpdate")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosUpdate(
            RequestDataManttoInformacionBarrios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionBarriosUpdate(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla Barrios
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoInformacionBarriosDelete")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosDelete(
            RequestDataManttoInformacionBarrios alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoInformacionBarriosDelete(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar los registros de la tabla de Mantenimiento
     * Asesor de Avanzada
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAsesorGestionDeAvanzadaQuery")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaQuery();
    }
    
    /**
     * WebServices encargado de insertar un registro en la tabla de Mantenimiento de
     * Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAsesorGestionDeAvanzadaAdd")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaAdd(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de actualizar un registro en la tabla Mantenimiento
     * Asesor Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAsesorGestionDeAvanzadaUpdate")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaUpdate(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de eliminar un registro en la tabla Mantenimiento 
     * Asesor Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoAsesorGestionDeAvanzadaDelete")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaDelete(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaDelete(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar los registros de la tabla Mantenimiento
     * Estados Avanzada
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadosAvanzadaQuery")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadosAvanzadaQuery();
    }
    
    /**
     * WebServices encargado de insertar un registro de la tabla Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadosAvanzadaAdd")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaAdd(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadosAvanzadaAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de actualizar un registro en la tabla Mantenimiento
     * Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadosAvanzadaUpdate")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaUpdate(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadosAvanzadaUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de eliminar un registro de la tabla Mantenimiento
     * Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEstadosAvanzadaDelete")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaDelete(
            RequestDataManttoEstadosAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoEstadosAvanzadaDelete(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar los registros de la tabla Asignar
     * Asesor Gestion Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "asignarAsesorAvanzadaQuery")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaQuery(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.asignarAsesorAvanzadaQuery(alimentacion);
    }
    
    /**
     * WebServices que permite Asignar un Asesor en Gestion Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "asignarAsesorAvanzadaAdd")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaAdd(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.asignarAsesorAvanzadaAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de actualizar una Asignacion del Asesor en Gestion 
     * De Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "asignarAsesorAvanzadaUpdate")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaUpdate(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.asignarAsesorAvanzadaUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar los registros de la tabla de 
     * Mantenimiento Motivos Cambio Fecha
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMotivosCambioFechaQuery")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMotivosCambioFechaQuery();
    }
    
    /**
     * WebServices encargado de insertar un registro en la tabla de
     * Mantenimiento Motivos Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMotivosCambioFechaAdd")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaAdd(
            RequestDataMotivosCambioFecha alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMotivosCambioFechaAdd(alimentacion);
    }
    
    /**
     * WebServices encargado de actualizar un registro en la tabla Mantenimiento
     * Motivos Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMotivosCambioFechaUpdate")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaUpdate(
            RequestDataMotivosCambioFecha alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMotivosCambioFechaUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de eliminar un registro en la tabla Mantenimiento 
     * Motivo Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoMotivosCambioFechaDelete")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaDelete(
            RequestDataMotivosCambioFecha alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoMotivosCambioFechaDelete(alimentacion);
    }
    
    /**
     * WebServices encargado de consultar los registros de la tabla Mantenimiento
     * Tipo Competencia
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoCompetenciaQuery")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaQuery() {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoCompetenciaQuery();
    }
    
    /**
     * WebServices encargado de insertar un registro en la tabla de Mantenimiento
     * Tipo Competencia
     * @param alimentacionRequest
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoCompetenciaAdd")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaAdd(
            RequestDataManttoTipoCompetencia alimentacionRequest) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoCompetenciaAdd(alimentacionRequest);
    }
    
    /**
     * WebServices encargado de actualizar un registro en la tabla de Mantenimiento
     * Tipo Competencia
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoCompetenciaUpdate")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaUpdate(
            RequestDataManttoTipoCompetencia alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoCompetenciaUpdate(alimentacion);
    }
    
    /**
     * WebServices encargado de eliminar un registro en la tabla de Mantenimiento
     * Tipo Competencia
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoTipoCompetenciaDelete")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaDelete(
            RequestDataManttoTipoCompetencia alimentacion) {
        CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();
        return cmTablasBasicas.manttoTipoCompetenciaDelete(alimentacion);
    }
    
}