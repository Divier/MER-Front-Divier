/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

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

/**
 *
 * @author Alquiler
 */
@Stateless
public class CuentasMatricesFacade implements CuentasMatricesFacadeLocal {
    
    private final CmCuentasMatricesManager cmCuentasMatrices = new CmCuentasMatricesManager(); 
    /* Black List Competencia por Edificio */
    @Override
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListQuery(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListQuery(alimentacion);
    }

    @Override
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListAdd(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListAdd(alimentacion);
    }

    @Override
    public ResponseCompetenciaEdificioBlackList manttoCompetenciaEdificioBlackListDelete(
            RequestDataCompetenciaEdificioBlackList alimentacion) {
        
        return cmCuentasMatrices.manttoCompetenciaEdificioBlackListDelete(alimentacion);
    }

    /* Competencia por Edificio */
    @Override
    public ResponseCompetenciaEdificioList competenciaEdificioQuery(
            RequestDataCompetenciaEdificioList alimentacion) {
        
        return cmCuentasMatrices.competenciaEdificioQuery(alimentacion);
    }

    @Override
    public ResponseCompetenciaEdificioList competenciaEdificioAdd(
            RequestDataCompetenciaEdificioList alimentacion) {
        
        return cmCuentasMatrices.competenciaEdificioAdd(alimentacion);
    }

    @Override
    public ResponseCompetenciaEdificioList competenciaEdificioDelete(
            RequestDataCompetenciaEdificioList alimentacion) {
        
        return cmCuentasMatrices.competenciaEdificioDelete(alimentacion);
    }

    /* Black List Competencia Log*/
    @Override
    public ResponseBlackListLogList manttoBlackListLogQuery(
            RequestDataBlackListLog alimentacion) {
        
        return cmCuentasMatrices.manttoBlackListLogQuery(alimentacion);
    }

    /* Ayuda Sub edificio Información Adicional*/
    @Override
    public ResponseInformacionSubEdificioList informacionSubEdificioQuery(
            RequestDataInformacionSubEdificio alimentacion) {
        
        return cmCuentasMatrices.informacionSubEdificioQuery(alimentacion);
    }

    /* Equipos Edificio */
    @Override
    public ResponseEquiposEdificioList equiposEdificioQuery(
            RequestDataEquiposEdificio alimentacion) {
        
        return cmCuentasMatrices.equiposEdificioQuery(alimentacion);
    }

    /* Información Adicional de Edificios */
    @Override
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioQuery(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        
        return cmCuentasMatrices.informacionAdicionalEdificioQuery(alimentacion);
    }

    @Override
    public ResponseInformacionAdicionalEdificioList informacionAdicionalEdificioUpdate(
            RequestDataInformacionAdicionalEdificio alimentacion) {
        
        return cmCuentasMatrices.informacionAdicionalEdificioUpdate(alimentacion);
    }

    /* Información adicional  SubEdificios*/
    @Override
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdficioQuery(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        
        return cmCuentasMatrices.informacionAdicionalSubEdificioQuery(alimentacion);
    }

    @Override
    public ResponseInformacionAdicionalSubEdificioList informacionAdicionalSubEdificioUpdate(
            RequestDataInformacionAdicionalSubEdificio alimentacion) {
        
        return cmCuentasMatrices.informacionAdicionalSubEdificioUpdate(alimentacion);
    }

    /*  Mantenimiento SubEdificios */
    @Override
    public ResponseManttoSubEdificiosList manttoSubEdificiosQuery(
            RequestDataManttoSubEdificios alimentacion) {
        
        return cmCuentasMatrices.manttoSubEdificiosQuery(alimentacion);
    }
    
    @Override
    public ResponseManttoSubEdificiosList manttoSubEdificiosAdd(
            RequestDataManttoSubEdificios alimentacion) {
        
        return cmCuentasMatrices.manttoSubEdificiosAdd(alimentacion);
    }
    
    @Override
    public ResponseManttoSubEdificiosList manttoSubEdificiosUpdate(
            RequestDataManttoSubEdificios alimentacion) {
        
        return cmCuentasMatrices.manttoSubEdificiosUpdate(alimentacion);
    }
    
    @Override
    public ResponseManttoSubEdificiosList manttoSubEdificiosDelete(
            RequestDataManttoSubEdificios alimentacion) {
        
        return cmCuentasMatrices.manttoSubEdificiosDelete(alimentacion);
    }

    /* Mantenimiento Edificio */
    @Override
    public ResponseManttoEdificioList manttoEdificioQuery(
            RequestDataManttoEdificio alimentacion) {
        
        return cmCuentasMatrices.manttoEdificioQuery(alimentacion);
    }

    @Override
    public ResponseManttoEdificioList manttoEdificioAdd(
            RequestDataManttoEdificio alimentacion) {
        
        return cmCuentasMatrices.manttoEdificioAdd(alimentacion);
    }

    @Override
    public ResponseManttoEdificioList manttoEdificioUpdate(
            RequestDataManttoEdificio alimentacion) {
        
        return cmCuentasMatrices.manttoEdificioUpdate(alimentacion);
    }

    /* Consulta Edificio */
    @Override
    public ResponseEdificioList edificioQuery(
            RequestDataEdificio alimentacion) {
        
        return cmCuentasMatrices.edificioQuery(alimentacion);
    }

    /* Consulta Cuenta Matriz */
    @Override
    public ResponseCuentaMatriz cuentaMatrizByCodQuery(
            RequestDataCuentaMatrizByCod alimentacion) {
        
        return cmCuentasMatrices.cuentaMatrizByCodQuery(alimentacion);
    }

    @Override
    public ResponseCuentaMatriz cuentaMatrizByDirQuery(
            RequestDataCuentaMatrizByDir alimentacion) {
        
        return cmCuentasMatrices.cuentaMatrizByDirQuery(alimentacion);
    }

    /* Consulta Edificios */
    @Override
    public ResponseConsultaEdificiosList consultaEdificiosQuery(
            RequestDataConsultaEdificios alimentacion) {
        
        return cmCuentasMatrices.consultaEdificiosQuery(alimentacion);
    }

    @Override
    public ResponseConsultaPorInventarioEquipoList consultaPorInventarioEquipoQuery(
            RequestDataConsultaPorInventarioEquipo alimentacion) {
        
        return cmCuentasMatrices.consultaPorInventarioEquipoQuery(alimentacion);
    }

    @Override
    public ResponseConsultaPorTelefonoList consultaPorTelefonoQuery(
            RequestDataConsultaPorTelefono alimentacion) {
        
        return cmCuentasMatrices.consultaPorTelefonoQuery(alimentacion);

    }
    
    /* Unidades */
    @Override
    public ResponseConsultaUnidadesList consultaUnidadesQuery(
            RequestDataConsultaUnidades alimentacion) {
        
        return cmCuentasMatrices.consultaUnidadesQuery(alimentacion);
    }
    
    /* Division */
    @Override
    public ResponseConsultaDivisionList consultaDivisionHelp() {
        
        return cmCuentasMatrices.consultaDivisionHelp();
    }
    
    /* Comunidad */
    @Override
    public ResponseConsultaComunidadList consultaComunidadHelp(
            RequestDataConsultaComunidad alimentacion) {
        
        return cmCuentasMatrices.consultaComunidadHelp(alimentacion);
    }
    
    /* Inventario Cuenta Matriz */
    @Override
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizQuery(
            RequestDataInventarioCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.inventarioCuentaMatrizQuery(alimentacion);
    }
    
    @Override
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizAdd(
            RequestDataInventarioCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.inventarioCuentaMatrizAdd(alimentacion);
    }
    
    @Override
    public ResponseInventarioCuentaMatrizList inventarioCuentaMatrizDelete(
            RequestDataInventarioCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.inventarioCuentaMatrizDelete(alimentacion);
    }
    
    @Override
    public ResponseConsultaInventarioList consultaInventarioHelp(
            RequestDataConsultaInventario alimentacion) {
        
        return cmCuentasMatrices.consultaInventarioHelp(alimentacion);
    }
    
    /* Notas Cuenta Matriz */
    @Override
    public ResponseNotasCuentaMatrizList listNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.listNotasCuentaMatrizQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasCuentaMatrizList descripcionNotasCuentaMatrizQuery(
            RequestDataNotasCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.descripcionNotasCuentaMatrizQuery(alimentacion);
    }
    
    @Override
    public ResponseNotasCuentaMatrizList notasCuentaMatrizAdd(
            RequestDataNotasCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.notasCuentaMatrizAdd(alimentacion);
    }
    
    @Override
    public ResponseNotasCuentaMatrizList notasCuentaMatrizUpdate(
            RequestDataNotasCuentaMatriz alimentacion) {
        
        return cmCuentasMatrices.notasCuentaMatrizUpdate(alimentacion);
    }
    
    /* Gestion de Avanzada */
    @Override
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaQuery(
            RequestDataGestionDeAvanzada alimentacion) {
        
        return cmCuentasMatrices.gestionDeAvanzadaQuery(alimentacion);
    }
    
    @Override
    public ResponseGestionDeAvanzadaList gestionDeAvanzadaAdd(
            RequestDataGestionDeAvanzada alimentacion) {
        
        return cmCuentasMatrices.gestionDeAvanzadaAdd(alimentacion);
    }
}
