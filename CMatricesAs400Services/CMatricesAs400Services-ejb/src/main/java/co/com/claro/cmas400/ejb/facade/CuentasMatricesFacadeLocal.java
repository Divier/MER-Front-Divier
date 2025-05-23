/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

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
import javax.ejb.Local;

/**
 *
 * @author camargomf
 */
@Local
public interface CuentasMatricesFacadeLocal {

    /* Black List Competencia por Edificio */
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListQuery(
            RequestDataCompetenciaEdificioBlackList alimentacion);

    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListAdd(
            RequestDataCompetenciaEdificioBlackList alimentacion);

    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListDelete(
            RequestDataCompetenciaEdificioBlackList alimentacion);

    /* Competencia Edificio */
    public ResponseCompetenciaEdificioList competenciaEdificioQuery(
            RequestDataCompetenciaEdificioList alimentacion);

    public ResponseCompetenciaEdificioList competenciaEdificioAdd(
            RequestDataCompetenciaEdificioList alimentacion);

    public ResponseCompetenciaEdificioList competenciaEdificioDelete(
            RequestDataCompetenciaEdificioList alimentacion);

    /* Black List Log - Histórico de listas negras por edificio. */
    public ResponseBlackListLogList manttoBlackListLogQuery(
            RequestDataBlackListLog alimentacion);

    /* Ayuda Sub edificio Información Adicional */
    public ResponseInformacionSubEdificioList informacionSubEdificioQuery(
            RequestDataInformacionSubEdificio alimentacion);

    /* Equipos Edificio */
    public ResponseEquiposEdificioList equiposEdificioQuery(
            RequestDataEquiposEdificio alimentacion);

    /* Información Adicional de Edificios */
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioQuery(
            RequestDataInformacionAdicionalEdificio alimentacion);

    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioUpdate(
            RequestDataInformacionAdicionalEdificio alimentacion);

    /* Información Adicional de SubEdificios */
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdficioQuery(
            RequestDataInformacionAdicionalSubEdificio alimentacion);

    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioUpdate(
            RequestDataInformacionAdicionalSubEdificio alimentacion);

    /* Mantenimiento SubEdficios */
    public ResponseManttoSubEdificiosList manttoSubEdificiosQuery(
            RequestDataManttoSubEdificios alimentacion);

    public ResponseManttoSubEdificiosList manttoSubEdificiosAdd(
            RequestDataManttoSubEdificios alimentacion);
    
    public ResponseManttoSubEdificiosList manttoSubEdificiosUpdate(
            RequestDataManttoSubEdificios alimentacion);
    
    public ResponseManttoSubEdificiosList manttoSubEdificiosDelete(
            RequestDataManttoSubEdificios alimentacion);

    /* Mantenimiento Edificio */
    public ResponseManttoEdificioList manttoEdificioQuery(
            RequestDataManttoEdificio alimentacion);

    public ResponseManttoEdificioList manttoEdificioAdd(
            RequestDataManttoEdificio alimentacion);

    public ResponseManttoEdificioList manttoEdificioUpdate(
            RequestDataManttoEdificio alimentacion);

    /* Consulta Edificio */
    public ResponseEdificioList edificioQuery(
            RequestDataEdificio aliemtacion);

    /* Consulta Cuenta Matriz */
    public ResponseCuentaMatriz cuentaMatrizByCodQuery(
            RequestDataCuentaMatrizByCod aliemtacion);

    public ResponseCuentaMatriz cuentaMatrizByDirQuery(
            RequestDataCuentaMatrizByDir aliemtacion);

    /* Consulta Edificios */
    public ResponseConsultaEdificiosList consultaEdificiosQuery(
            RequestDataConsultaEdificios alimentacion);

    public ResponseConsultaPorInventarioEquipoList consultaPorInventarioEquipoQuery(
            RequestDataConsultaPorInventarioEquipo alimentacion);

    public ResponseConsultaPorTelefonoList consultaPorTelefonoQuery(
            RequestDataConsultaPorTelefono alimentacion);
    
    /* Unidades */
    public ResponseConsultaUnidadesList consultaUnidadesQuery(
            RequestDataConsultaUnidades alimentacion);
    
    /* Division */
    public ResponseConsultaDivisionList consultaDivisionHelp();
    
    /* Comunidad */
    public ResponseConsultaComunidadList consultaComunidadHelp(
            RequestDataConsultaComunidad alimentacion);
    
    /* Inventario Cuenta Matriz */
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizQuery(
            RequestDataInventarioCuentaMatriz alimentacion);
    
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizAdd(
            RequestDataInventarioCuentaMatriz alimentacion);
    
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizDelete(
            RequestDataInventarioCuentaMatriz alimentacion);
    
    public ResponseConsultaInventarioList consultaInventarioHelp(
            RequestDataConsultaInventario alimentacion);
    
    /* Notas Cuenta Matriz */
    public ResponseNotasCuentaMatrizList listNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion);
    
    public ResponseNotasCuentaMatrizList descripcionNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion);
    
    public ResponseNotasCuentaMatrizList notasCuentaMatrizAdd(
            RequestDataNotasCuentaMatriz alimentacion);
    
    public ResponseNotasCuentaMatrizList notasCuentaMatrizUpdate(
            RequestDataNotasCuentaMatriz alimentacion);
    
    /* Gestion de Avanzada */
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaQuery(
            RequestDataGestionDeAvanzada alimentacion); 
    
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaAdd(
            RequestDataGestionDeAvanzada alimentacion);
}
