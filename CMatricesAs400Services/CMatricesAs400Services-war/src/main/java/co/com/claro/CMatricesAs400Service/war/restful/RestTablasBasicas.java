/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;
 
import co.com.claro.cmas400.ejb.facade.TablasBasicasFacadeLocal;
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
import javax.naming.InitialContext;
import javax.naming.NamingException; 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Alquiler
 */
@Path("RestTablasBasicasService")
public class RestTablasBasicas {

    TablasBasicasFacadeLocal tablasBasicasFacadeLocal;
    InitialContext ic;
    
    

    /**
     * Crea una nueva instancia de RestTablasBasicas
     * @throws javax.naming.NamingException
     */
    public RestTablasBasicas() throws NamingException {
        ic = new InitialContext();
        tablasBasicasFacadeLocal = (TablasBasicasFacadeLocal) ic.lookup("java:comp/env/ejb/TablasBasicasFacadeLocal");
    }

    /**
     * Metodo encargado de adicionar un registro a la tabla de alimentacion
     * electrica
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAlimentacionElectAdd/")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectAdd(
            RequestDataAlimentacionElectrica request) {
        return tablasBasicasFacadeLocal.manttoAlimentacionElectAdd(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de alimentacion
     * electrica
     *
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAlimentacionElectQuery/")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectQuery() {
        return tablasBasicasFacadeLocal.manttoAlimentacionElectQuery();
    }

    /**
     * Web Services encargado de actualizar un registro de la tabla de
     * alimentacion electrica
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAlimentacionElectUpdate/")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectUpdate(
            RequestDataAlimentacionElectrica request) {
        return tablasBasicasFacadeLocal.manttoAlimentacionElectUpdate(request);
    }

    /**
     * Web Service encargado de eliminar un registro de la tabla de alimentacion
     * electrica
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAlimentacionElectList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAlimentacionElectDelete/")
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectDelete(
            RequestDataAlimentacionElectrica request) {
        return tablasBasicasFacadeLocal.manttoAlimentacionElectDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros en la tabla de Compañia
     * Administracion
     * @param reques
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAdminCompanyQuery/")
    public ResponseAdminCompanyList manttoAdminCompanyQuery(
            RequestDataAdminCompany reques) {
        return tablasBasicasFacadeLocal.manttoAdminCompanyQuery(reques);
    }

    /**
     * Metodo encargado de crear un registro en la ttabla de Compañia
     * Administracion
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAdminCompanyAdd/")
    public ResponseAdminCompanyList manttoAdminCompanyAdd(
            RequestDataAdminCompany request) {
        return tablasBasicasFacadeLocal.manttoAdminCompanyAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Compañia
     * Administracion
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAdminCompanyUpdate/")
    public ResponseAdminCompanyList manttoAdminCompanyUpdate(
            RequestDataAdminCompany request) {
        return tablasBasicasFacadeLocal.manttoAdminCompanyUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Compañia
     * Administracion
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseAdminCompanyList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAdminCompanyDelete/")
    public ResponseAdminCompanyList manttoAdminCompanyDelete(
            RequestDataAdminCompany request) {
        return tablasBasicasFacadeLocal.manttoAdminCompanyDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Compañia de
     * Ascensores
     *
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCiaAscensoresQuery/")
    public ResponseCiaAscensoresList manttoCiaAscensoresQuery() {
        return tablasBasicasFacadeLocal.manttoCiaAscensoresQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Compañia de
     * Ascensores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCiaAscensoresAdd/")
    public ResponseCiaAscensoresList manttoCiaAscensoresAdd(
            RequestDataCiaAscensores request) {
        return tablasBasicasFacadeLocal.manttoCiaAscensoresAdd(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estados
     * Edificio
     *
     * @return ResponseCiaAscensoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadoEdificioQuery/")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioQuery() {
        return tablasBasicasFacadeLocal.manttoEstadoEdificioQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Estados Edificio
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadoEdificioAdd/")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioAdd(
            RequestDataManttoEstadoEdificio request) {
        return tablasBasicasFacadeLocal.manttoEstadoEdificioAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Estados Edificio
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadoEdificioDelete/")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioDelete(
            RequestDataManttoEstadoEdificio request) {
        return tablasBasicasFacadeLocal.manttoEstadoEdificioDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Estados
     * Edificio
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadoEdificioUpdate/")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioUpdate(
            RequestDataManttoEstadoEdificio request) {
        return tablasBasicasFacadeLocal.manttoEstadoEdificioUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estados
     * Edificio para las pantallas de ayuda
     *
     * @return ResponseManttoEstadoEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadoEdificioHelp/")
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioHelp() {
        return tablasBasicasFacadeLocal.manttoEstadoEdificioHelp();
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Estado
     * Resultado OT
     *
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("estadoResultadoOtQuery/")
    public ResponseEstadoResultadoOtList estadoResultadoOtQuery() {
        return tablasBasicasFacadeLocal.estadoResultadoOtQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Estado Resultado
     * OT
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("estadoResultadoOtAdd/")
    public ResponseEstadoResultadoOtList estadoResultadoOtAdd(
            RequestDataEstadoResultadoOt request) {
        return tablasBasicasFacadeLocal.estadoResultadoOtAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Estado Resultado
     * OT
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("estadoResultadoOtDelete/")
    public ResponseEstadoResultadoOtList estadoResultadoOtDelete(
            RequestDataEstadoResultadoOt request) {
        return tablasBasicasFacadeLocal.estadoResultadoOtDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Estado
     * Resultado OT
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseEstadoResultadoOtList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("estadoResultadoOtUpdate/")
    public ResponseEstadoResultadoOtList estadoResultadoOtUpdate(
            RequestDataEstadoResultadoOt request) {
        return tablasBasicasFacadeLocal.estadoResultadoOtUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento
     * Competencia
     *
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaQuery/")
    public ResponseManttoCompetenciaList manttoCompetenciaQuery() {
        return tablasBasicasFacadeLocal.manttoCompetenciaQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Competencia
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaAdd/")
    public ResponseManttoCompetenciaList manttoCompetenciaAdd(
            RequestDataManttoCompetencia request) {
        return tablasBasicasFacadeLocal.manttoCompetenciaAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Competencia
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaDelete/")
    public ResponseManttoCompetenciaList manttoCompetenciaDelete(
            RequestDataManttoCompetencia request) {
        return tablasBasicasFacadeLocal.manttoCompetenciaDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Mantenimiento
     * Competencia
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaUpdate/")
    public ResponseManttoCompetenciaList manttoCompetenciaUpdate(
            RequestDataManttoCompetencia request) {
        return tablasBasicasFacadeLocal.manttoCompetenciaUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Productos
     *
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("productosQuery/")
    public ResponseProductosList productosQuery() {
        return tablasBasicasFacadeLocal.productosQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Productos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("productosAdd/")
    public ResponseProductosList productosAdd(RequestDataProductos request) {
        return tablasBasicasFacadeLocal.productosAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Productos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("productosDelete/")
    public ResponseProductosList productosDelete(RequestDataProductos request) {
        return tablasBasicasFacadeLocal.productosDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Productos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProductosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("productosUpdate/")
    public ResponseProductosList productosUpdate(RequestDataProductos request) {
        return tablasBasicasFacadeLocal.productosUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Proveedores
     *
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("proveedoresQuery/")
    public ResponseProveedoresList proveedoresQuery() {
        return tablasBasicasFacadeLocal.proveedoresQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Proveedores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("proveedoresAdd/")
    public ResponseProveedoresList proveedoresAdd(
            RequestDataProveedores request) {
        return tablasBasicasFacadeLocal.proveedoresAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Proveedores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("proveedoresDelete/")
    public ResponseProveedoresList proveedoresDelete(
            RequestDataProveedores request) {
        return tablasBasicasFacadeLocal.proveedoresDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Proveedores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseProveedoresList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("proveedoresUpdate/")
    public ResponseProveedoresList proveedoresUpdate(
            RequestDataProveedores request) {
        return tablasBasicasFacadeLocal.proveedoresUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoPuntoInicialQuery/")
    public ResponseManttoPuntoInicialList manttoPuntoInicialQuery() {
        return tablasBasicasFacadeLocal.manttoPuntoInicialQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoPuntoInicialAdd/")
    public ResponseManttoPuntoInicialList manttoPuntoInicialAdd(
            RequestDataManttoPuntoInicial request) {
        return tablasBasicasFacadeLocal.manttoPuntoInicialAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoPuntoInicialDelete/")
    public ResponseManttoPuntoInicialList manttoPuntoInicialDelete(
            RequestDataManttoPuntoInicial request) {
        return tablasBasicasFacadeLocal.manttoPuntoInicialDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Punto Inicial
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoPuntoInicialList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoPuntoInicialUpdate/")
    public ResponseManttoPuntoInicialList manttoPuntoInicialUpdate(
            RequestDataManttoPuntoInicial request) {
        return tablasBasicasFacadeLocal.manttoPuntoInicialUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipo de distribución de red interna.
     *
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoDistribucionRedInternaQuery/")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaQuery() {
        return tablasBasicasFacadeLocal.manttoTipoDistribucionRedInternaQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipo de distribución de red interna.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoDistribucionRedInternaAdd/")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaAdd(
            RequestDataManttoTipoDistribucionRedInterna request) {
        return tablasBasicasFacadeLocal.manttoTipoDistribucionRedInternaAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * tipo de distribución de red interna.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoDistribucionRedInternaDelete/")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaDelete(
            RequestDataManttoTipoDistribucionRedInterna request) {
        return tablasBasicasFacadeLocal.manttoTipoDistribucionRedInternaDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipo de distribución de red interna.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoDistribucionRedInternaList Objeto utilizado
     * para capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoDistribucionRedInternaUpdate/")
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaUpdate(
            RequestDataManttoTipoDistribucionRedInterna request) {
        return tablasBasicasFacadeLocal.manttoTipoDistribucionRedInternaUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipos de edificios.
     *
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoEdificioQuery/")
    public ResponseManttoTipoEdificioList manttoTipoEdificioQuery() {
        return tablasBasicasFacadeLocal.manttoTipoEdificioQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipos de edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoEdificioAdd/")
    public ResponseManttoTipoEdificioList manttoTipoEdificioAdd(
            RequestDataManttoTipoEdificio request) {
        return tablasBasicasFacadeLocal.manttoTipoEdificioAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * tipos de edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoEdificioDelete/")
    public ResponseManttoTipoEdificioList manttoTipoEdificioDelete(
            RequestDataManttoTipoEdificio request) {
        return tablasBasicasFacadeLocal.manttoTipoEdificioDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipos de edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoEdificioList Objeto utilizado para capturar los
     * resultmes(MediaType.APPLICATION_JSON)
     * @Path(ados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoEdificioUpdate/")
    public ResponseManttoTipoEdificioList manttoTipoEdificioUpdate(
            RequestDataManttoTipoEdificio request) {
        return tablasBasicasFacadeLocal.manttoTipoEdificioUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * de tipos de Notas.
     *
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoNotasQuery/")
    public ResponseManttoTipoNotasList manttoTipoNotasQuery() {
        return tablasBasicasFacadeLocal.manttoTipoNotasQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento de
     * tipos de Notas.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoNotasAdd/")
    public ResponseManttoTipoNotasList manttoTipoNotasAdd(
            RequestDataManttoTipoNotas request) {
        return tablasBasicasFacadeLocal.manttoTipoNotasAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento de
     * tipos de Notas.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoNotasDelete/")
    public ResponseManttoTipoNotasList manttoTipoNotasDelete(
            RequestDataManttoTipoNotas request) {
        return tablasBasicasFacadeLocal.manttoTipoNotasDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * de tipos de Notas.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoNotasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoNotasUpdate/")
    public ResponseManttoTipoNotasList manttoTipoNotasUpdate(
            RequestDataManttoTipoNotas request) {
        return tablasBasicasFacadeLocal.manttoTipoNotasUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Tipo de
     * Competencia
     *
     * @return ResponseTipoCompetenciaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tipoCompetenciaQuery/")
    public ResponseTipoCompetenciaList tipoCompetenciaQuery() {
        return tablasBasicasFacadeLocal.tipoCompetenciaQuery();
    }

    /**
     * Metodo encargado de consultar registros de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificiosQuery/")
    public ResponseManttoEdificiosList manttoEdificiosQuery(
            RequestDataManttoEdificios request) {
        return tablasBasicasFacadeLocal.manttoEdificiosQuery(request);
    }

    /**
     * Metodo encargado de Insertar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificiosAdd/")
    public ResponseManttoEdificiosList manttoEdificiosAdd(
            RequestDataManttoEdificios request) {
        return tablasBasicasFacadeLocal.manttoEdificiosAdd(request);
    }

    /**
     * Metodo encargado de Eliminar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificiosDelete/")
    public ResponseManttoEdificiosList manttoEdificiosDelete(
            RequestDataManttoEdificios request) {
        return tablasBasicasFacadeLocal.manttoEdificiosDelete(request);
    }

    /**
     * Metodo encargado de Eliminar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificiosUpdate/")
    public ResponseManttoEdificiosList manttoEdificiosUpdate(
            RequestDataManttoEdificios request) {
        return tablasBasicasFacadeLocal.manttoEdificiosUpdate(request);
    }

    /**
     * Metodo encargado de consultar registros de la tabla de Mantenimiento
     * Materiales.
     *
     * @param alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialesQuery/")
    public ResponseManttoMaterialesList manttoMaterialesQuery(RequestDataManttoMateriales alimentacion) {
        return tablasBasicasFacadeLocal.manttoMaterialesQuery(alimentacion);
    }

    /**
     * Metodo encargado de Insertar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialesAdd/")
    public ResponseManttoMaterialesList manttoMaterialesAdd(
            RequestDataManttoMateriales request) {
        return tablasBasicasFacadeLocal.manttoMaterialesAdd(request);
    }

    /**
     * Metodo encargado de Eliminar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialesDelete/")
    public ResponseManttoMaterialesList manttoMaterialesDelete(
            RequestDataManttoMateriales request) {
        return tablasBasicasFacadeLocal.manttoMaterialesDelete(request);
    }

    /**
     * Metodo encargado de Eliminar un registro de la tabla de Mantenimiento
     * Edificios.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialesUpdate/")
    public ResponseManttoMaterialesList manttoMaterialesUpdate(
            RequestDataManttoMateriales request) {
        return tablasBasicasFacadeLocal.manttoMaterialesUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla de Mantenimiento
     * Materiales Proveedor
     *
     * @param alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialProveedorQuery/")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorQuery(
            RequestDataManttoMaterialProveedor alimentacion) {
        return tablasBasicasFacadeLocal.manttoMaterialProveedorQuery(alimentacion);
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento
     * Materiales Proveedor
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialProveedorAdd/")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorAdd(
            RequestDataManttoMaterialProveedor request) {
        return tablasBasicasFacadeLocal.manttoMaterialProveedorAdd(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Mantenimiento
     * Materiales Proveedor
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialProveedorDelete/")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorDelete(
            RequestDataManttoMaterialProveedor request) {
        return tablasBasicasFacadeLocal.manttoMaterialProveedorDelete(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla de Mantenimiento
     * Materiales Proveedor
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoMaterialProveedorList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMaterialProveedorUpdate/")
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorUpdate(
            RequestDataManttoMaterialProveedor request) {
        return tablasBasicasFacadeLocal.manttoMaterialProveedorUpdate(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Compañia de
     * Ascensores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return manttoCiaAscensoresUpdate Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCiaAscensoresUpdate/")
    public ResponseCiaAscensoresList manttoCiaAscensoresUpdate(
            RequestDataCiaAscensores request) {
        return tablasBasicasFacadeLocal.manttoCiaAscensoresUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla de Compañia de
     * Ascensores
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return manttoCiaAscensoresDelete Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCiaAscensoresDelete/")
    public ResponseCiaAscensoresList manttoCiaAscensoresDelete(
            RequestDataCiaAscensores request) {
        return tablasBasicasFacadeLocal.manttoCiaAscensoresDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros en la tabla de Razon de
     * Arreglo
     *
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoRazonArregloQuery/")
    public ResponseRazonArregloList manttoRazonArregloQuery() {
        return tablasBasicasFacadeLocal.manttoRazonArregloQuery();
    }

    /**
     * Metodo encagado de insertar un registro en la tabla de Razon de Arreglo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoRazonArregloAdd/")
    public ResponseRazonArregloList manttoRazonArregloAdd(
            RequestDataRazonArreglo request) {
        return tablasBasicasFacadeLocal.manttoRazonArregloAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla de Razon de Arreglo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoRazonArregloUpdate/")
    public ResponseRazonArregloList manttoRazonArregloUpdate(
            RequestDataRazonArreglo request) {
        return tablasBasicasFacadeLocal.manttoRazonArregloUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla de Razon de Arreglo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseRazonArregloList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoRazonArregloDelete/")
    public ResponseRazonArregloList manttoRazonArregloDelete(
            RequestDataRazonArreglo request) {
        return tablasBasicasFacadeLocal.manttoRazonArregloDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Black List
     *
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCodigoBlackListQuery/")
    public ResponseCodigoBlackList manttoCodigoBlackListQuery() {
        return tablasBasicasFacadeLocal.manttoCodigoBlackListQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Black List
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCodigoBlackListAdd/")
    public ResponseCodigoBlackList manttoCodigoBlackListAdd(
            RequestDataCodigoBlackList request) {
        return tablasBasicasFacadeLocal.manttoCodigoBlackListAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla BlackList
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCodigoBlackListUpdate/")
    public ResponseCodigoBlackList manttoCodigoBlackListUpdate(
            RequestDataCodigoBlackList request) {
        return tablasBasicasFacadeLocal.manttoCodigoBlackListUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla BlackList
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseCodigoBlackList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCodigoBlackListDelete/")
    public ResponseCodigoBlackList manttoCodigoBlackListDelete(
            RequestDataCodigoBlackList request) {
        return tablasBasicasFacadeLocal.manttoCodigoBlackListDelete(request);
    }

    /**
     * Metodo para consultar los registros en la tabla Origen Datos
     *
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoOrigenDatosQuery/")
    public ResponseOrigenDatosList manttoOrigenDatosQuery() {
        return tablasBasicasFacadeLocal.manttoOrigenDatosQuery();
    }

    /**
     * Metodo para insertar un registro en la tabla Origen Datos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoOrigenDatosAdd/")
    public ResponseOrigenDatosList manttoOrigenDatosAdd(
            RequestDataOrigenDatos request) {
        return tablasBasicasFacadeLocal.manttoOrigenDatosAdd(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Origen Datos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoOrigenDatosUpdate/")
    public ResponseOrigenDatosList manttoOrigenDatosUpdate(
            RequestDataOrigenDatos request) {
        return tablasBasicasFacadeLocal.manttoOrigenDatosUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Origen Datos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseOrigenDatosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoOrigenDatosDelete/")
    public ResponseOrigenDatosList manttoOrigenDatosDelete(
            RequestDataOrigenDatos request) {
        return tablasBasicasFacadeLocal.manttoOrigenDatosDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Acometida
     *
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoAcometidaQuery/")
    public ResponseTipoAcometidaList manttoTipoAcometidaQuery() {
        return tablasBasicasFacadeLocal.manttoTipoAcometidaQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla de Tipo Acometida
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoAcometidaAdd/")
    public ResponseTipoAcometidaList manttoTipoAcometidaAdd(
            RequestDataTipoAcometida request) {
        return tablasBasicasFacadeLocal.manttoTipoAcometidaAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla Tipo Acometida
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoAcometidaUpdate/")
    public ResponseTipoAcometidaList manttoTipoAcometidaUpdate(
            RequestDataTipoAcometida request) {
        return tablasBasicasFacadeLocal.manttoTipoAcometidaUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Acometida
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoAcometidaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoAcometidaDelete/")
    public ResponseTipoAcometidaList manttoTipoAcometidaDelete(
            RequestDataTipoAcometida request) {
        return tablasBasicasFacadeLocal.manttoTipoAcometidaDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipos Materiales
     *
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoMaterialesQuery/")
    public ResponseTipoMaterialesList manttoTipoMaterialesQuery() {
        return tablasBasicasFacadeLocal.manttoTipoMaterialesQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipos Materiales
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoMaterialesAdd/")
    public ResponseTipoMaterialesList manttoTipoMaterialesAdd(
            RequestDataTipoMateriales request) {
        return tablasBasicasFacadeLocal.manttoTipoMaterialesAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Tipo Materiales
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoMaterialesUpdate/")
    public ResponseTipoMaterialesList manttoTipoMaterialesUpdate(
            RequestDataTipoMateriales request) {
        return tablasBasicasFacadeLocal.manttoTipoMaterialesUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Materiales
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoMaterialesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoMaterialesDelete/")
    public ResponseTipoMaterialesList manttoTipoMaterialesDelete(
            RequestDataTipoMateriales request) {
        return tablasBasicasFacadeLocal.manttoTipoMaterialesDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Proyecto
     *
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoProyectoQuery/")
    public ResponseTipoProyectoList manttoTipoProyectoQuery() {
        return tablasBasicasFacadeLocal.manttoTipoProyectoQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Proyecto
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoProyectoAdd/")
    public ResponseTipoProyectoList manttoTipoProyectoAdd(
            RequestDataTipoProyecto request) {
        return tablasBasicasFacadeLocal.manttoTipoProyectoAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla Tipo Proyecto
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoProyectoUpdate/")
    public ResponseTipoProyectoList manttoTipoProyectoUpdate(
            RequestDataTipoProyecto request) {
        return tablasBasicasFacadeLocal.manttoTipoProyectoUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Tipo Proyecto
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseTipoProyectoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoProyectoDelete/")
    public ResponseTipoProyectoList manttoTipoProyectoDelete(
            RequestDataTipoProyecto request) {
        return tablasBasicasFacadeLocal.manttoTipoProyectoDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros en la tabla Informacion Nodos
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoNodosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoNodosQuery/")
    public ResponseManttoNodosList manttoNodosQuery(
            RequestDataManttoNodos request) {
        return tablasBasicasFacadeLocal.manttoNodosQuery(request);
    }

    /**
     * Metodo encargado de consultar los registros en la tabla Supervisor
     * Avanzada
     *
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSupervisorAvanzadaQuery/")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaQuery() {
        return tablasBasicasFacadeLocal.manttoSupervisorAvanzadaQuery();
    }

    /**
     * Meotodo encargado de crear un registro en la tabla Supervisor Avanzada
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSupervisorAvanzadaAdd/")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaAdd(
            RequestDataSupervisorAvanzada request) {
        return tablasBasicasFacadeLocal.manttoSupervisorAvanzadaAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Supervisor Avanzada
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSupervisorAvanzadaUpdate/")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaUpdate(
            RequestDataSupervisorAvanzada request) {
        return tablasBasicasFacadeLocal.manttoSupervisorAvanzadaUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Supervisor Avanzada
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseSupervisorAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSupervisorAvanzadaDelete/")
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaDelete(
            RequestDataSupervisorAvanzada request) {
        return tablasBasicasFacadeLocal.manttoSupervisorAvanzadaDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros en la tabla Constructoras.
     *
     * @param request
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("constructorasQuery/")
    public ResponseConstructorasList constructorasQuery(
            RequestDataConstructoras request) {
        return tablasBasicasFacadeLocal.constructorasQuery(request);
    }

    /**
     * Metodo encargado de Insertar un registro de la tabla de Constructoras.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("constructorasAdd/")
    public ResponseConstructorasList constructorasAdd(
            RequestDataConstructoras request) {
        return tablasBasicasFacadeLocal.constructorasAdd(request);
    }

    /**
     * Metodo encargado de Eliminar un registro de la tabla de Constructoras.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("constructorasDelete/")
    public ResponseConstructorasList constructorasDelete(
            RequestDataConstructoras request) {
        return tablasBasicasFacadeLocal.constructorasDelete(request);
    }

    /**
     * Metodo encargado de Actualizar un registro de la tabla de Constructoras.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("constructorasUpdate/")
    public ResponseConstructorasList constructorasUpdate(
            RequestDataConstructoras request) {
        return tablasBasicasFacadeLocal.constructorasUpdate(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Estrato
     *
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstratoQuery/")
    public ResponseManttoEstratoList manttoEstratoQuery() {
        return tablasBasicasFacadeLocal.manttoEstratoQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Estrato
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstratoAdd/")
    public ResponseManttoEstratoList manttoEstratoAdd(
            RequestDataEstrato request) {
        return tablasBasicasFacadeLocal.manttoEstratoAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Tipo Estrato
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstratoUpdate/")
    public ResponseManttoEstratoList manttoEstratoUpdate(
            RequestDataEstrato request) {
        return tablasBasicasFacadeLocal.manttoEstratoUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Estrato
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoEstratoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstratoDelete/")
    public ResponseManttoEstratoList manttoEstratoDelete(
            RequestDataEstrato request) {
        return tablasBasicasFacadeLocal.manttoEstratoDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Tipo Trabajo
     *
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoTrabajoQuery/")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoQuery() {
        return tablasBasicasFacadeLocal.manttoTipoTrabajoQuery();
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Tipo Trabajo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoTrabajoAdd/")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoAdd(
            RequestDataTipoTrabajo request) {
        return tablasBasicasFacadeLocal.manttoTipoTrabajoAdd(request);
    }

    /**
     * Metodo encargado de actualizar un registro de la tabla Tipo Trabajo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoTrabajoUpdate/")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoUpdate(
            RequestDataTipoTrabajo request) {
        return tablasBasicasFacadeLocal.manttoTipoTrabajoUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro de la tabla Tipo Trabajo
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoTipoTrabajoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoTrabajoDelete/")
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoDelete(
            RequestDataTipoTrabajo request) {
        return tablasBasicasFacadeLocal.manttoTipoTrabajoDelete(request);
    }

    /**
     * Metodo para consultar los registros de la tabla Ubicacion Caja
     *
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoUbicacionCajaQuery/")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaQuery() {
        return tablasBasicasFacadeLocal.manttoUbicacionCajaQuery();
    }

    /**
     * Metodo para insertar un registro en la tabla Ubicacion Caja
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoUbicacionCajaAdd/")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaAdd(
            RequestDataUbicacionCaja request) {
        return tablasBasicasFacadeLocal.manttoUbicacionCajaAdd(request);
    }

    /**
     * Metodo para actualizar un registro de la tabla Ubicacion Caja
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoUbicacionCajaUpdate/")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaUpdate(
            RequestDataUbicacionCaja request) {
        return tablasBasicasFacadeLocal.manttoUbicacionCajaUpdate(request);
    }

    /**
     * Metodo para eliminar un registro de la tabla Ubicacion Caja
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoUbicacionCajaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoUbicacionCajaDelete/")
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaDelete(
            RequestDataUbicacionCaja request) {
        return tablasBasicasFacadeLocal.manttoUbicacionCajaDelete(request);
    }

    /**
     * Metodo para consultar la tabla Mantenimiento Información Nodo.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionNodoQuery/")
    public ResponseManttoInfoNodoList manttoInformacionNodoQuery(
            RequestDataManttoInfoNodo request) {
        return tablasBasicasFacadeLocal.manttoInformacionNodoQuery(request);
    }

    /**
     * Metodo para insertar un registro en la tabla Mantenimiento Información
     * Nodo.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionNodoAdd/")
    public ResponseManttoInfoNodoList manttoInformacionNodoAdd(
            RequestDataManttoInfoNodo request) {
        return tablasBasicasFacadeLocal.manttoInformacionNodoAdd(request);
    }

    /**
     * Metodo para modificar un registro en la tabla Mantenimiento Información
     * Nodo.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionNodoUpdate/")
    public ResponseManttoInfoNodoList manttoInformacionNodoUpdate(
            RequestDataManttoInfoNodo request) {
        return tablasBasicasFacadeLocal.manttoInformacionNodoUpdate(request);
    }

    /**
     * Metodo para eliminar un registro en la tabla Mantenimiento Información
     * Nodo.
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInfoNodoList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionNodoDelete/")
    public ResponseManttoInfoNodoList manttoInformacionNodoDelete(
            RequestDataManttoInfoNodo request) {
        return tablasBasicasFacadeLocal.manttoInformacionNodoDelete(request);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Barrios
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionBarriosQuery/")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosQuery(
            RequestDataManttoInformacionBarrios request) {
        return tablasBasicasFacadeLocal.manttoInformacionBarriosQuery(request);
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Barrios
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionBarriosAdd/")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosAdd(
            RequestDataManttoInformacionBarrios request) {
        return tablasBasicasFacadeLocal.manttoInformacionBarriosAdd(request);
    }

    /**
     * Metodo encargado de modificar un registro en la tabla Barrios
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionBarriosUpdate/")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosUpdate(
            RequestDataManttoInformacionBarrios request) {
        return tablasBasicasFacadeLocal.manttoInformacionBarriosUpdate(request);
    }

    /**
     * Metodo encargado de eliminar un registro en la tabla Barrios
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoInformacionBarriosList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoInformacionBarriosDelete/")
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosDelete(
            RequestDataManttoInformacionBarrios request) {
        return tablasBasicasFacadeLocal.manttoInformacionBarriosDelete(request);
    }
    
    /**
     * Metodo encargado de consultar la tabla de Mantenimiento Asesor de Avanzada
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAsesorGestionDeAvanzadaQuery/")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaQuery() {
        return tablasBasicasFacadeLocal.manttoAsesorGestionDeAvanzadaQuery();
    }
    
    /**
     * Metodo encargado de insertar un registro en la tabla de Mantenimiento 
     * Asesor de Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAsesorGestionDeAvanzadaAdd/")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaAdd(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoAsesorGestionDeAvanzadaAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar un registro en la tabla Mantenimiento
     * Asesor Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAsesorGestionDeAvanzadaUpdate/")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaUpdate(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoAsesorGestionDeAvanzadaUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un registro en la tabla de Mantenimiento
     * Asesor Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseManttoAsesorGestionDeAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoAsesorGestionDeAvanzadaDelete/")
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaDelete(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoAsesorGestionDeAvanzadaDelete(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento
     * Estados Avanzada
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar 
     * los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadosAvanzadaQuery/")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaQuery() {
        return tablasBasicasFacadeLocal.manttoEstadosAvanzadaQuery();
    }
    
    /**
     * Metodo encargado de insertar un registro en la tabla Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadosAvanzadaAdd/")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaAdd(
            RequestDataManttoEstadosAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoEstadosAvanzadaAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar un registro de la tabla Mantenimiento
     * Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadosAvanzadaUpdate/")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaUpdate(
            RequestDataManttoEstadosAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoEstadosAvanzadaUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un registro de la tabla Mantenimiento
     * Estados Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseManttoEstadosAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEstadosAvanzadaDelete/")
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaDelete(
            RequestDataManttoEstadosAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.manttoEstadosAvanzadaDelete(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar los registros de la tabla Asignar Asesor
     * Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("asignarAsesorAvanzadaQuery/")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaQuery(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.asignarAsesorAvanzadaQuery(alimentacion);
    }
    
    /**
     * Metodo que permite Asignar un Asesor en Gestion Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion.
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("asignarAsesorAvanzadaAdd/")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaAdd(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.asignarAsesorAvanzadaAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar una Asignacion del Asesor en Gestion De
     * Avanzada
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseAsignarAsesorAvanzadaList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("asignarAsesorAvanzadaUpdate/")
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaUpdate(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        return tablasBasicasFacadeLocal.asignarAsesorAvanzadaUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento
     * Cambio Fecha
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMotivosCambioFechaQuery/")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaQuery() {
        return tablasBasicasFacadeLocal.manttoMotivosCambioFechaQuery();
    }
    
    /**
     * Metodo encargado de insertar un registro en la tabla Mantenimiento
     * Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMotivosCambioFechaAdd/")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaAdd(
            RequestDataMotivosCambioFecha alimentacion) {
        return tablasBasicasFacadeLocal.manttoMotivosCambioFechaAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar un registro en la tabla Mantenimiento 
     * Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMotivosCambioFechaUpdate/")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaUpdate(
            RequestDataMotivosCambioFecha alimentacion) {
        return tablasBasicasFacadeLocal.manttoMotivosCambioFechaUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un registro en la tabla Mantenimiento Motivos
     * Cambio Fecha
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoMotivosCambioFechaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoMotivosCambioFechaDelete/")
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaDelete(
            RequestDataMotivosCambioFecha alimentacion) {
        return tablasBasicasFacadeLocal.manttoMotivosCambioFechaDelete(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento
     * Tipo Competencia
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoCompetenciaQuery/")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaQuery() {
        return tablasBasicasFacadeLocal.manttoTipoCompetenciaQuery();
    }
    
    /**
     * Metodo encargado de insertar un registro en la tabla Manteniemiento
     * Tipo Competencia
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoCompetenciaAdd/")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaAdd(
            RequestDataManttoTipoCompetencia alimentacion) {
        return tablasBasicasFacadeLocal.manttoTipoCompetenciaAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de actualizar un registro en la tabla Mantenimiento
     * Tipo Competencia
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoCompetenciaUpdate/")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaUpdate(
            RequestDataManttoTipoCompetencia alimentacion) {
        return tablasBasicasFacadeLocal.manttoTipoCompetenciaUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un registro en la tabla Mantenimiento
     * Tipo Acometida
     * @param alimentacion Objeto que almacena los datos de alimentacion
     * @return ResponseManttoTipoCompetenciaList Objeto utilizado para 
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoTipoCompetenciaDelete/")
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaDelete(
            RequestDataManttoTipoCompetencia alimentacion) {
        return tablasBasicasFacadeLocal.manttoTipoCompetenciaDelete(alimentacion);
    }
    
}
