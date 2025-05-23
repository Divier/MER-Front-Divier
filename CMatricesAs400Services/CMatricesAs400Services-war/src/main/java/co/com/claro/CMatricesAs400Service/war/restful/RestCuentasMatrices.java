/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.CMatricesAs400Service.war.restful;

import co.com.claro.cmas400.ejb.facade.CuentasMatricesFacadeLocal;
import co.com.claro.cmas400.ejb.request.RequestDataBlackListLog;
import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaComunidad;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaInventario;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaPorInventarioEquipo;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaPorTelefono;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaUnidades;
import co.com.claro.cmas400.ejb.request.RequestDataCuentaMatrizByCod;
import co.com.claro.cmas400.ejb.request.RequestDataCuentaMatrizByDir;
import co.com.claro.cmas400.ejb.request.RequestDataEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataEquiposEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataGestionDeAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInventarioCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataNotasCuentaMatriz;
import co.com.claro.cmas400.ejb.respons.ResponseBlackListLogList;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaComunidadList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaDivisionList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaInventarioList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaPorInventarioEquipoList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaPorTelefonoList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaUnidadesList;
import co.com.claro.cmas400.ejb.respons.ResponseCuentaMatriz;
import co.com.claro.cmas400.ejb.respons.ResponseEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseEquiposEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseGestionDeAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInventarioCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasCuentaMatrizList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("RestCuentasMatricesService")
public class RestCuentasMatrices {

    CuentasMatricesFacadeLocal cuentasMatricesFacadeLocal;
    @Context
    private UriInfo context;
    InitialContext ic;

    /**
     * Crea una nueva instancia de RestCuentasMatrices
     * @throws javax.naming.NamingException
     */
    public RestCuentasMatrices() throws NamingException {
        ic = new InitialContext();
        cuentasMatricesFacadeLocal = (CuentasMatricesFacadeLocal) ic.lookup("java:comp/env/ejb/CuentasMatricesFacadeLocal");
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Mantenimiento
     * competencia x edificio.Black list CM.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaEdificioBlackListQuery/")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListQuery(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        return cuentasMatricesFacadeLocal.manttoCompetenciaEdificioBlackListQuery(alimentacion);
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Mantenimiento
     * competencia x edificio.Black list CM.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaEdificioBlackListAdd/")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListAdd(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        return cuentasMatricesFacadeLocal.manttoCompetenciaEdificioBlackListAdd(alimentacion);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Mantenimiento
     * competencia x edificio.Black list CM.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoCompetenciaEdificioBlackListDelete/")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListDelete(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        return cuentasMatricesFacadeLocal.manttoCompetenciaEdificioBlackListDelete(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Competencia por
     * Edificio.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("competenciaEdificioQuery/")
    public ResponseCompetenciaEdificioList competenciaEdificioQuery(
            RequestDataCompetenciaEdificioList alimentacion) {
        return cuentasMatricesFacadeLocal.competenciaEdificioQuery(alimentacion);
    }

    /**
     * Metodo encargado de insertar un registro en la tabla Competencia por
     * Edificio.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("competenciaEdificioAdd/")
    public ResponseCompetenciaEdificioList competenciaEdificioAdd(
            RequestDataCompetenciaEdificioList alimentacion) {
        return cuentasMatricesFacadeLocal.competenciaEdificioAdd(alimentacion);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Competencia por
     * Edificio.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("competenciaEdificioDelete/")
    public ResponseCompetenciaEdificioList competenciaEdificioDelete(
            RequestDataCompetenciaEdificioList alimentacion) {
        return cuentasMatricesFacadeLocal.competenciaEdificioDelete(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Histórico de
     * listas negras por edificio.Black list Log.
     *
     * @param alimentacion
     * @return ResponseBlackListLogList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoBlackListLogQuery/")
    public ResponseBlackListLogList manttoBlackListLogQuery(
            RequestDataBlackListLog alimentacion) {
        return cuentasMatricesFacadeLocal.manttoBlackListLogQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Ayuda SubEdificio
     * Información Adicional
     *
     * @param alimentacion
     * @return ResponseInformacionSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionSubEdificioQuery/")
    public ResponseInformacionSubEdificioList informacionSubEdificioQuery(
            RequestDataInformacionSubEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.informacionSubEdificioQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla Equipos Edificio
     *
     * @param request Objeto que almacena los datos de la alimentacion
     * @return ResponseEquiposEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("equiposEdificioQuery/")
    public ResponseEquiposEdificioList equiposEdificioQuery(
            RequestDataEquiposEdificio request) {
        return cuentasMatricesFacadeLocal.equiposEdificioQuery(request);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionAdicionalEdificioQuery/")
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioQuery(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.informacionAdicionalEdificioQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla información
     * Adicional de Edificios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionAdicionalEdificioUpdate/")
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioUpdate(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.informacionAdicionalEdificioUpdate(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla información
     * Adicional de Edificios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionAdicionalSubEdificioQuery/")
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioQuery(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.informacionAdicionalSubEdficioQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla información
     * Adicional de Edificios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("informacionAdicionalSubEdificioUpdate/")
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioUpdate(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.informacionAdicionalSubEdificioUpdate(alimentacion);
    }

    /**
     * Metodo encargado de consultar los registros de la tabla información
     * Mantenimiento SubEdificios.
     *
     * @param alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSubEdificiosQuery/")
    public ResponseManttoSubEdificiosList manttoSubEdificiosQuery(
            RequestDataManttoSubEdificios alimentacion) {
        return cuentasMatricesFacadeLocal.manttoSubEdificiosQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de insertar los registros de la tabla informacion
     * Mantenimiento SubEdificios
     * 
     * @param alimentacion 
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSubEdificiosAdd/")
    public ResponseManttoSubEdificiosList manttoSubEdificiosAdd(
            RequestDataManttoSubEdificios alimentacion) {
        return cuentasMatricesFacadeLocal.manttoSubEdificiosAdd(alimentacion);
    }
    
    
    /**
     * Metodo encargado de modificar el registro de la table Mantenimiento
     * SubEdificios
     * @param alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSubEdificiosUpdate/")
    public ResponseManttoSubEdificiosList manttoSubEdificiosUpdate(
            RequestDataManttoSubEdificios alimentacion) {
        return cuentasMatricesFacadeLocal.manttoSubEdificiosUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de eliminar un subedificio
     * @param alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoSubEdificiosDelete/")
    public ResponseManttoSubEdificiosList manttoSubEdificiosDelete(
            RequestDataManttoSubEdificios alimentacion) {
        return cuentasMatricesFacadeLocal.manttoSubEdificiosDelete(alimentacion);
    }

    /**
     * Metodo encargado de consultar la tabla Mantenimiento de Edificios.
     *
     * @param alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificioQuery/")
    public ResponseManttoEdificioList manttoEdificioQuery(
            RequestDataManttoEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.manttoEdificioQuery(alimentacion);
    }

    /**
     * Metodo encargado de insertar un registro de la tabla Mantenimiento de
     * Edificios.
     *
     * @param alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificioAdd/")
    public ResponseManttoEdificioList manttoEdificioAdd(
            RequestDataManttoEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.manttoEdificioAdd(alimentacion);
    }

    /**
     * Metodo encargado de modificar un registro de la tabla Mantenimiento de
     * Edificios.
     *
     * @param alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manttoEdificioUpdate/")
    public ResponseManttoEdificioList manttoEdificioUpdate(
            RequestDataManttoEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.manttoEdificioUpdate(alimentacion);
    }

    /**
     * Metodo encargado de consultar edificios.
     *
     * @param alimentacion
     * @return ResponseEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edificioQuery/")
    public ResponseEdificioList edificioQuery(
            RequestDataEdificio alimentacion) {
        return cuentasMatricesFacadeLocal.edificioQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar un edificio o Cuenta Matriz por Código.
     *
     * @param alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cuentaMatrizByCodQuery/")
    public ResponseCuentaMatriz cuentaMatrizByCodQuery(
            RequestDataCuentaMatrizByCod alimentacion) {
        return cuentasMatricesFacadeLocal.cuentaMatrizByCodQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar un edificio o Cuenta Matriz por Dirección.
     *
     * @param alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cuentaMatrizByDirQuery/")
    public ResponseCuentaMatriz cuentaMatrizByDirQuery(
            RequestDataCuentaMatrizByDir alimentacion) {
        return cuentasMatricesFacadeLocal.cuentaMatrizByDirQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar los edificios registrados
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaEdificiosQuery/")
    public ResponseConsultaEdificiosList consultaEdificiosQuery(
            RequestDataConsultaEdificios alimentacion) {
        return cuentasMatricesFacadeLocal.consultaEdificiosQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar un edificio por medio de la informacion del
     * inventario de los equipos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorInventarioEquipoList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaPorInventarioEquipoQuery/")
    public ResponseConsultaPorInventarioEquipoList consultaPorInventarioEquipoQuery(
            RequestDataConsultaPorInventarioEquipo alimentacion) {
        return cuentasMatricesFacadeLocal.consultaPorInventarioEquipoQuery(alimentacion);
    }

    /**
     * Metodo encargado de consultar un edificio por medio del numero de
     * telefono
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorInventarioEquipoList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaPorTelefonoQuery/")
    public ResponseConsultaPorTelefonoList consultaPorTelefonoQuery(
            RequestDataConsultaPorTelefono alimentacion) {
        return cuentasMatricesFacadeLocal.consultaPorTelefonoQuery(alimentacion);

    }
    
    /**
     * Metodo encargado de consultar las unidades de un edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaUnidadesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaUnidadesQuery/")
    public ResponseConsultaUnidadesList consultaUnidadesQuery(
            RequestDataConsultaUnidades alimentacion) {
        return cuentasMatricesFacadeLocal.consultaUnidadesQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar las Divisiones
     * @return ResponseConsultaDivisionList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaDivisionHelp/")
    public ResponseConsultaDivisionList consultaDivisionHelp(){
        return cuentasMatricesFacadeLocal.consultaDivisionHelp();
    }
    
    /**
     * Metodo encargado de consultar las Comunidades
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaComunidadList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaComunidadHelp/")
    public ResponseConsultaComunidadList consultaComunidadHelp(
            RequestDataConsultaComunidad alimentacion) {
        return cuentasMatricesFacadeLocal.consultaComunidadHelp(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar el inventario de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inventarioCuentaMatrizQuery/")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizQuery(
            RequestDataInventarioCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.inventarioCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de asignar inventario a un equipo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inventarioCuentaMatrizAdd/")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizAdd(
            RequestDataInventarioCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.inventarioCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * Metodo encargado de desvincular el inventario de un edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inventarioCuentaMatrizDelete/")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizDelete(
            RequestDataInventarioCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.inventarioCuentaMatrizDelete(alimentacion);
    }
    
    /**
     * Metodo que permite consultar los inventarios para seleccionar
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaInventarioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultaInventarioHelp/")
    public ResponseConsultaInventarioList consultaInventarioHelp(
            RequestDataConsultaInventario alimentacion) {
        return cuentasMatricesFacadeLocal.consultaInventarioHelp(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar las notas de una cuenta matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listNotasCuentaMatrizQuery/")
    public ResponseNotasCuentaMatrizList listNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.listNotasCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Metodo encarcado de consultar la descripcion de las notas de una Cuenta
     * Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("descripcionNotasCuentaMatrizQuery/")
    public ResponseNotasCuentaMatrizList descripcionNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.descripcionNotasCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Metodo que permite crear una nota en una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasCuentaMatrizAdd/")
    public ResponseNotasCuentaMatrizList notasCuentaMatrizAdd(
            RequestDataNotasCuentaMatriz alimentacion){
        return cuentasMatricesFacadeLocal.notasCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * Metodo que permite actualizar una nota en una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("notasCuentaMatrizUpdate/")
    public ResponseNotasCuentaMatrizList notasCuentaMatrizUpdate(
            RequestDataNotasCuentaMatriz alimentacion) {
        return cuentasMatricesFacadeLocal.notasCuentaMatrizUpdate(alimentacion);
    }
    
    /**
     * Metodo encargado de consultar la tabla de Gestion De Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gestionDeAvanzadaQuery/")
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaQuery(
            RequestDataGestionDeAvanzada alimentacion) {
        return cuentasMatricesFacadeLocal.gestionDeAvanzadaQuery(alimentacion);
    }
    
    /**
     * Metodo encargado de insertar un registro en la tabla de Gestion de Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gestionDeAvanzadaAdd/")
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaAdd(
            RequestDataGestionDeAvanzada alimentacion) {
        return cuentasMatricesFacadeLocal.gestionDeAvanzadaAdd(alimentacion);
    }
}
