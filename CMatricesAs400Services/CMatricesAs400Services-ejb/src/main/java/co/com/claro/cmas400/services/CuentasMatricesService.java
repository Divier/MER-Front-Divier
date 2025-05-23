/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.services;

import co.com.claro.cmas400.ejb.manager.CmCuentasMatricesManager;
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
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Alquiler
 */
@WebService(serviceName = "CuentasMatricesService")
@Stateless()
public class CuentasMatricesService {

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Mantenimiento competencia x edificio.Black list CM.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaEdificioBlackListQuery")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListQuery(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de
     * Mantenimiento competencia x edificio. Black list CM.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaEdificioBlackListAdd")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListAdd(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de
     * Mantenimiento competencia x edificio. Black list CM.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioBlackList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoCompetenciaEdificioBlackListDelete")
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListDelete(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Competencia por Edificio.
     *
     * @param alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "competenciaEdificioQuery")
    public ResponseCompetenciaEdificioList competenciaEdificioQuery(
            RequestDataCompetenciaEdificioList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.competenciaEdificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla de Competencia
     * por Edificio.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "competenciaEdificioAdd")
    public ResponseCompetenciaEdificioList competenciaEdificioAdd(
            RequestDataCompetenciaEdificioList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.competenciaEdificioAdd(alimentacion);
    }

    /**
     * Web Services encargado de eliminar un registro en la tabla de Competencia
     * por Edificio.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseCompetenciaEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "competenciaEdificioDelete")
    public ResponseCompetenciaEdificioList competenciaEdificioDelete(
            RequestDataCompetenciaEdificioList alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.competenciaEdificioDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * Histórico de listas negras por edificio.Black List Log.
     *
     * @param alimentacion
     * @return ResponseBlackListLogList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoBlackListLogQuery")
    public ResponseBlackListLogList manttoBlackListLogQuery(
            RequestDataBlackListLog alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoBlackListLogQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de Ayuda
     * Sub edificio Información Adicional.
     *
     * @param alimentacion
     * @return ResponseInformacionSubEdificioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionSubEdificioQuery")
    public ResponseInformacionSubEdificioList informacionSubEdificioQuery(
            RequestDataInformacionSubEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.informacionSubEdificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * información Adicional de Eficios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionAdicionalEdificioQuery")
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioQuery(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.informacionAdicionalEdificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * información Adicional de Eficios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionAdicionalEdificioUpdate")
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioUpdate(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.informacionAdicionalEdificioUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * información Adicional de SubEdificios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalSubEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionAdicionalSubEdificioQuery")
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioQuery(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.informacionAdicionalSubEdificioQuery(alimentacion);

    }

    /**
     * Web Services encargado de consultar los registros de la tabla de
     * información Adicional de SubEdificios.
     *
     * @param alimentacion
     * @return ResponseInformacionAdicionalSubEdificioList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "informacionAdicionalSubEdificioUpdate")
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioUpdate(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.informacionAdicionalSubEdificioUpdate(alimentacion);

    }

    /**
     * Web Services encargadode consultar los registros de la tabla Equipos
     * Edificio.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseEquiposEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "equiposEdificioQuery")
    public ResponseEquiposEdificioList equiposEdificioQuery(
            RequestDataEquiposEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.equiposEdificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar los edificios registrados
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaEdificiosQuery")
    public ResponseConsultaEdificiosList consultaEdificiosQuery(
            RequestDataConsultaEdificios alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaEdificiosQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar un edificio por medio de la
     * informacion del inventario de los equipos
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorInventarioEquipoList Objeto utilizado para
     * capturar los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaPorInventarioEquipoQuery")
    public ResponseConsultaPorInventarioEquipoList consultaPorInventarioEquipoQuery(
            RequestDataConsultaPorInventarioEquipo alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaPorInventarioEquipoQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar un edificio por medio del numero de
     * telefono
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaPorTelefonoList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaPorTelefonoQuery")
    public ResponseConsultaPorTelefonoList consultaPorTelefonoQuery(
            RequestDataConsultaPorTelefono alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaPorTelefonoQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar la tabla de Mantenimiento
     * Subedificio.
     *
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseManttoSubEdificiosList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoSubEdificiosQuery")
    public ResponseManttoSubEdificiosList manttoSubEdificiosQuery(
            RequestDataManttoSubEdificios alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoSubEdificiosQuery(alimentacion);
    }
    
    @WebMethod(operationName = "manttoSubEdificiosAdd")
    public ResponseManttoSubEdificiosList manttoSubEdificiosAdd(
            RequestDataManttoSubEdificios alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoSubEdificiosAdd(alimentacion);
    }    
    
    @WebMethod(operationName = "manttoSubEdificiosUpdate")
    public ResponseManttoSubEdificiosList manttoSubEdificiosUpdate(
            RequestDataManttoSubEdificios alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoSubEdificiosUpdate(alimentacion);
    }
    
    @WebMethod(operationName = "manttoSubEdificiosDelete")
    public ResponseManttoSubEdificiosList manttoSubEdificiosDelete(
            RequestDataManttoSubEdificios alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoSubEdificiosDelete(alimentacion);
    }

    /**
     * Web Services encargado de consultar la tabla Mantenimiento de Edificios.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificioQuery")
    public ResponseManttoEdificioList manttoEdificioQuery(
            RequestDataManttoEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoEdificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de insertar un registro en la tabla Mantenimiento
     * de Edificios.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificioAdd")
    public ResponseManttoEdificioList manttoEdificioAdd(
            RequestDataManttoEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoEdificioAdd(alimentacion);
    }

    /**
     * Web Services encargado de modificar un registro en la tabla Mantenimiento
     * de Edificios.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseManttoEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "manttoEdificioUpdate")
    public ResponseManttoEdificioList manttoEdificioUpdate(
            RequestDataManttoEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.manttoEdificioUpdate(alimentacion);
    }

    /**
     * Web Services encargado de consultar edificios.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseEdificioList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "edificioQuery")
    public ResponseEdificioList edificioQuery(
            RequestDataEdificio alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.edificioQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar un edificio o Cuenta Matriz por
     * Código.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "cuentaMatrizByCodQuery")
    public ResponseCuentaMatriz cuentaMatrizByCodQuery(
            RequestDataCuentaMatrizByCod alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.cuentaMatrizByCodQuery(alimentacion);
    }

    /**
     * Web Services encargado de consultar un edificio o Cuenta Matriz por
     * Dirección.
     *
     * @param alimentacion Objeto quse almacena los datos de la alimentacion
     * @return ResponseCuentaMatriz Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "cuentaMatrizByDirQuery")
    public ResponseCuentaMatriz cuentaMatrizByDirQuery(
            RequestDataCuentaMatrizByDir alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.cuentaMatrizByDirQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar las Unidades de un Edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaUnidadesList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaUnidadesQuery")
    public ResponseConsultaUnidadesList consultaUnidadesQuery(
            RequestDataConsultaUnidades alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaUnidadesQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar las Divisiones
     * @return ResponseConsultaDivisionList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaDivisionHelp")
    public ResponseConsultaDivisionList consultaDivisionHelp() {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaDivisionHelp();
    }
    
    /**
     * Web Services encargado de consultar las Comunidades
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaComunidadList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaComunidadHelp")
    public ResponseConsultaComunidadList consultaComunidadHelp(
            RequestDataConsultaComunidad alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaComunidadHelp(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar el inventario de una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "inventarioCuentaMatrizQuery")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizQuery(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.inventarioCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de asignar inventario a un equipo
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "inventarioCuentaMatrizAdd")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizAdd(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.inventarioCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * Web Services encargado de desvincular el inventario de un edificio
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseInventarioCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "inventarioCuentaMatrizDelete")
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizDelete(
            RequestDataInventarioCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.inventarioCuentaMatrizDelete(alimentacion);
    }
    
    /**
     * Web Services que permite consultar los inventarios para seleccionar
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseConsultaInventarioList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "consultaInventarioHelp")
    public ResponseConsultaInventarioList consultaInventarioHelp(
            RequestDataConsultaInventario alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.consultaInventarioHelp(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar las notas de una cuenta matriz
     * 
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "listNotasCuentaMatrizQuery")
    public ResponseNotasCuentaMatrizList listNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.listNotasCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de consultar la descripcion de las notas de una 
     * cuenta matriz
     * 
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "descripcionNotasCuentaMatrizQuery")
    public ResponseNotasCuentaMatrizList descripcionNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.descripcionNotasCuentaMatrizQuery(alimentacion);
    }
    
    /**
     * Web Services que permite crear una nota en una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar
     * los resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasCuentaMatrizAdd")
    public ResponseNotasCuentaMatrizList notasCuentaMatrizAdd(
            RequestDataNotasCuentaMatriz alimentacion){
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.notasCuentaMatrizAdd(alimentacion);
    }
    
    /**
     * Web Services que permite actualizar una nota en una Cuenta Matriz
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseNotasCuentaMatrizList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "notasCuentaMatrizUpdate")
    public ResponseNotasCuentaMatrizList notasCuentaMatrizUpdate(
            RequestDataNotasCuentaMatriz alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.notasCuentaMatrizUpdate(alimentacion);
    }
    
    /**
     * Web Services que permite consultar la tabla de Gestion de Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    @WebMethod(operationName = "gestionDeAvanzadaQuery")
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaQuery(
            RequestDataGestionDeAvanzada alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.gestionDeAvanzadaQuery(alimentacion);
    }
    
    /**
     * Web Services encargado de insertar un registro en la tabla Gestion De Avanzada
     * @param alimentacion Objeto que almacena los datos de la alimentacion
     * @return ResponseGestionDeAvanzadaList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     */
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaAdd(
            RequestDataGestionDeAvanzada alimentacion) {
        CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager();
        return cmCuentasMatrices.gestionDeAvanzadaAdd(alimentacion);
    }
    
}