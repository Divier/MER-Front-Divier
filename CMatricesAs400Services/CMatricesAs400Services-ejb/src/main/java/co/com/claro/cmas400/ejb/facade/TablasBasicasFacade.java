/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

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

/**
 *
 * @author Alquiler
 */
@Stateless
public class TablasBasicasFacade implements TablasBasicasFacadeLocal {
    
    private final CmTablasBasicasManager cmTablasBasicas = new CmTablasBasicasManager();

    @Override
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectAdd(
            RequestDataAlimentacionElectrica alimentacion) {
        
        return cmTablasBasicas.manttoAlimentacionElectAdd(alimentacion);
    }

    @Override
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectQuery() {
        
        return cmTablasBasicas.manttoAlimentacionElectQuery();
    }

    @Override
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectUpdate(
            RequestDataAlimentacionElectrica alimentacion) {
        
        return cmTablasBasicas.manttoAlimentacionElectUpdate(alimentacion);
    }

    @Override
    public ResponseManttoAlimentacionElectList manttoAlimentacionElectDelete(
            RequestDataAlimentacionElectrica alimentacion) {
        
        return cmTablasBasicas.manttoAlimentacionElectDelete(alimentacion);
    }

    @Override
    public ResponseAdminCompanyList manttoAdminCompanyQuery(
            RequestDataAdminCompany alimentacionRequest) {
        
        return cmTablasBasicas.manttoAdminCompanyQuery(alimentacionRequest);
    }

    @Override
    public ResponseAdminCompanyList manttoAdminCompanyAdd(
            RequestDataAdminCompany alimentacionRequest) {
        
        return cmTablasBasicas.manttoAdminCompanyAdd(alimentacionRequest);
    }

    @Override
    public ResponseAdminCompanyList manttoAdminCompanyUpdate(
            RequestDataAdminCompany alimentacionRequest) {
        
        return cmTablasBasicas.manttoAdminCompanyUpdate(alimentacionRequest);
    }

    @Override
    public ResponseAdminCompanyList manttoAdminCompanyDelete(
            RequestDataAdminCompany alimentacionRequest) {
        
        return cmTablasBasicas.manttoAdminCompanyDelete(alimentacionRequest);
    }

    @Override
    public ResponseCiaAscensoresList manttoCiaAscensoresQuery() {
        
        return cmTablasBasicas.manttoCiaAscensoresQuery();
    }

    @Override
    public ResponseCiaAscensoresList manttoCiaAscensoresAdd(
            RequestDataCiaAscensores alimentacion) {
        
        return cmTablasBasicas.manttoCiaAscensoresAdd(alimentacion);

    }

    @Override
    public ResponseCiaAscensoresList manttoCiaAscensoresUpdate(
            RequestDataCiaAscensores alimentacion) {
        
        return cmTablasBasicas.manttoCiaAscensoresUpdate(alimentacion);
    }

    @Override
    public ResponseCiaAscensoresList manttoCiaAscensoresDelete(
            RequestDataCiaAscensores alimentacion) {
        
        return cmTablasBasicas.manttoCiaAscensoresDelete(alimentacion);
    }

    @Override
    public ResponseRazonArregloList manttoRazonArregloQuery() {
        
        return cmTablasBasicas.manttoRazonArregloQuery();
    }

    @Override
    public ResponseRazonArregloList manttoRazonArregloAdd(
            RequestDataRazonArreglo alimentacion) {
        
        return cmTablasBasicas.manttoRazonArregloAdd(alimentacion);
    }

    @Override
    public ResponseRazonArregloList manttoRazonArregloUpdate(
            RequestDataRazonArreglo alimentacion) {
        
        return cmTablasBasicas.manttoRazonArregloUpdate(alimentacion);
    }

    @Override
    public ResponseRazonArregloList manttoRazonArregloDelete(
            RequestDataRazonArreglo alimentacion) {
        
        return cmTablasBasicas.manttoRazonArregloDelete(alimentacion);
    }

    @Override
    public ResponseCodigoBlackList manttoCodigoBlackListQuery() {
        
        return cmTablasBasicas.manttoCodigoBlackListQuery();
    }

    @Override
    public ResponseCodigoBlackList manttoCodigoBlackListAdd(
            RequestDataCodigoBlackList alimentacion) {
        
        return cmTablasBasicas.manttoCodigoBlackListAdd(alimentacion);
    }

    @Override
    public ResponseCodigoBlackList manttoCodigoBlackListUpdate(
            RequestDataCodigoBlackList alimentacion) {
        
        return cmTablasBasicas.manttoCodigoBlackListUpdate(alimentacion);
    }

    @Override
    public ResponseCodigoBlackList manttoCodigoBlackListDelete(
            RequestDataCodigoBlackList alimentacion) {
        
        return cmTablasBasicas.manttoCodigoBlackListDelete(alimentacion);
    }

    @Override
    public ResponseOrigenDatosList manttoOrigenDatosQuery() {
        
        return cmTablasBasicas.manttoOrigenDatosQuery();
    }

    @Override
    public ResponseOrigenDatosList manttoOrigenDatosAdd(
            RequestDataOrigenDatos alimentacion) {
        
        return cmTablasBasicas.manttoOrigenDatosAdd(alimentacion);
    }

    @Override
    public ResponseOrigenDatosList manttoOrigenDatosUpdate(
            RequestDataOrigenDatos alimentacion) {
        
        return cmTablasBasicas.manttoOrigenDatosUpdate(alimentacion);
    }

    @Override
    public ResponseOrigenDatosList manttoOrigenDatosDelete(
            RequestDataOrigenDatos alimentacion) {
        
        return cmTablasBasicas.manttoOrigenDatosDelete(alimentacion);
    }

    @Override
    public ResponseTipoAcometidaList manttoTipoAcometidaQuery() {
        
        return cmTablasBasicas.manttoTipoAcometidaQuery();
    }

    @Override
    public ResponseTipoAcometidaList manttoTipoAcometidaAdd(
            RequestDataTipoAcometida alimentacion) {
        
        return cmTablasBasicas.manttoTipoAcometidaAdd(alimentacion);
    }

    @Override
    public ResponseTipoAcometidaList manttoTipoAcometidaUpdate(
            RequestDataTipoAcometida alimentacion) {
        
        return cmTablasBasicas.manttoTipoAcometidaUpdate(alimentacion);
    }

    @Override
    public ResponseTipoAcometidaList manttoTipoAcometidaDelete(
            RequestDataTipoAcometida alimentacion) {
        
        return cmTablasBasicas.manttoTipoAcometidaDelete(alimentacion);
    }

    @Override
    public ResponseTipoMaterialesList manttoTipoMaterialesQuery() {
        
        return cmTablasBasicas.manttoTipoMaterialesQuery();
    }

    @Override
    public ResponseTipoMaterialesList manttoTipoMaterialesAdd(
            RequestDataTipoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoTipoMaterialesAdd(alimentacion);
    }

    @Override
    public ResponseTipoMaterialesList manttoTipoMaterialesUpdate(
            RequestDataTipoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoTipoMaterialesUpdate(alimentacion);
    }

    @Override
    public ResponseTipoMaterialesList manttoTipoMaterialesDelete(
            RequestDataTipoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoTipoMaterialesDelete(alimentacion);
    }

    @Override
    public ResponseTipoProyectoList manttoTipoProyectoQuery() {
        
        return cmTablasBasicas.manttoTipoProyectoQuery();
    }

    @Override
    public ResponseTipoProyectoList manttoTipoProyectoAdd(
            RequestDataTipoProyecto alimentacion) {
        
        return cmTablasBasicas.manttoTipoProyectoAdd(alimentacion);
    }

    @Override
    public ResponseTipoProyectoList manttoTipoProyectoUpdate(
            RequestDataTipoProyecto alimentacion) {
        
        return cmTablasBasicas.manttoTipoProyectoUpdate(alimentacion);
    }

    @Override
    public ResponseTipoProyectoList manttoTipoProyectoDelete(
            RequestDataTipoProyecto alimentacion) {
        
        return cmTablasBasicas.manttoTipoProyectoDelete(alimentacion);
    }

    @Override
    public ResponseManttoNodosList manttoNodosQuery(
            RequestDataManttoNodos alimentacion) {
        
        return cmTablasBasicas.manttoNodosQuery(alimentacion);
    }

    /* Estados Edificio */
    @Override
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioQuery() {
        
        return cmTablasBasicas.manttoEstadoEdificioQuery();
    }

    @Override
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioAdd(
            RequestDataManttoEstadoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoEstadoEdificioAdd(alimentacion);
    }

    @Override
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioDelete(
            RequestDataManttoEstadoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoEstadoEdificioDelete(alimentacion);
    }

    @Override
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioUpdate(
            RequestDataManttoEstadoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoEstadoEdificioUpdate(alimentacion);
    }

    @Override
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioHelp() {
        
        return cmTablasBasicas.manttoEstadoEdificioHelp();
    }

    /* Estado Resultado OT */
    @Override
    public ResponseEstadoResultadoOtList estadoResultadoOtQuery() {
        
        return cmTablasBasicas.estadoResultadoOtQuery();
    }

    @Override
    public ResponseEstadoResultadoOtList estadoResultadoOtAdd(
            RequestDataEstadoResultadoOt alimentacion) {
        
        return cmTablasBasicas.estadoResultadoOtAdd(alimentacion);
    }

    @Override
    public ResponseEstadoResultadoOtList estadoResultadoOtDelete(
            RequestDataEstadoResultadoOt alimentacion) {
        
        return cmTablasBasicas.estadoResultadoOtDelete(alimentacion);
    }

    @Override
    public ResponseEstadoResultadoOtList estadoResultadoOtUpdate(
            RequestDataEstadoResultadoOt alimentacion) {
        
        return cmTablasBasicas.estadoResultadoOtUpdate(alimentacion);
    }

    /* Mantenimiento Competencia */
    @Override
    public ResponseManttoCompetenciaList manttoCompetenciaQuery() {
        
        return cmTablasBasicas.manttoCompetenciaQuery();
    }

    @Override
    public ResponseManttoCompetenciaList manttoCompetenciaAdd(
            RequestDataManttoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoCompetenciaAdd(alimentacion);
    }

    @Override
    public ResponseManttoCompetenciaList manttoCompetenciaDelete(
            RequestDataManttoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoCompetenciaDelete(alimentacion);
    }

    @Override
    public ResponseManttoCompetenciaList manttoCompetenciaUpdate(
            RequestDataManttoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoCompetenciaUpdate(alimentacion);
    }

    /* Productos */
    @Override
    public ResponseProductosList productosQuery() {
        
        return cmTablasBasicas.productosQuery();
    }

    @Override
    public ResponseProductosList productosAdd(
            RequestDataProductos alimentacion) {
        
        return cmTablasBasicas.productosAdd(alimentacion);
    }

    @Override
    public ResponseProductosList productosDelete(
            RequestDataProductos alimentacion) {
        
        return cmTablasBasicas.productosDelete(alimentacion);
    }

    @Override
    public ResponseProductosList productosUpdate(
            RequestDataProductos alimentacion) {
        
        return cmTablasBasicas.productosUpdate(alimentacion);
    }

    /* Proveedores */
    @Override
    public ResponseProveedoresList proveedoresQuery() {
        
        return cmTablasBasicas.proveedoresQuery();
    }

    @Override
    public ResponseProveedoresList proveedoresAdd(
            RequestDataProveedores alimentacion) {
        
        return cmTablasBasicas.proveedoresAdd(alimentacion);
    }

    @Override
    public ResponseProveedoresList proveedoresDelete(
            RequestDataProveedores alimentacion) {
        
        return cmTablasBasicas.proveedoresDelete(alimentacion);
    }

    @Override
    public ResponseProveedoresList proveedoresUpdate(
            RequestDataProveedores alimentacion) {
        
        return cmTablasBasicas.proveedoresUpdate(alimentacion);
    }

    /* Mantenimiento Punto Inicial */
    @Override
    public ResponseManttoPuntoInicialList manttoPuntoInicialQuery() {
        
        return cmTablasBasicas.manttoPuntoInicialQuery();
    }

    @Override
    public ResponseManttoPuntoInicialList manttoPuntoInicialAdd(
            RequestDataManttoPuntoInicial alimentacion) {
        
        return cmTablasBasicas.manttoPuntoInicialAdd(alimentacion);
    }

    @Override
    public ResponseManttoPuntoInicialList manttoPuntoInicialDelete(
            RequestDataManttoPuntoInicial alimentacion) {
        
        return cmTablasBasicas.manttoPuntoInicialDelete(alimentacion);
    }

    @Override
    public ResponseManttoPuntoInicialList manttoPuntoInicialUpdate(
            RequestDataManttoPuntoInicial alimentacion) {
        
        return cmTablasBasicas.manttoPuntoInicialUpdate(alimentacion);
    }

    /* Mantenimiento de tipo de distribución de red interna */
    @Override
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaQuery() {
        
        return cmTablasBasicas.manttoTipoDistribucionRedInternaQuery();
    }

    @Override
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaAdd(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        
        return cmTablasBasicas.manttoTipoDistribucionRedInternaAdd(alimentacion);
    }

    @Override
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaDelete(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        
        return cmTablasBasicas.manttoTipoDistribucionRedInternaDelete(alimentacion);
    }

    @Override
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaUpdate(
            RequestDataManttoTipoDistribucionRedInterna alimentacion) {
        
        return cmTablasBasicas.manttoTipoDistribucionRedInternaUpdate(alimentacion);
    }

    /* Mantenimiento de tipos de edificios */
    @Override
    public ResponseManttoTipoEdificioList manttoTipoEdificioQuery() {
        
        return cmTablasBasicas.manttoTipoEdificioQuery();
    }

    @Override
    public ResponseManttoTipoEdificioList manttoTipoEdificioAdd(
            RequestDataManttoTipoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoTipoEdificioAdd(alimentacion);
    }

    @Override
    public ResponseManttoTipoEdificioList manttoTipoEdificioDelete(
            RequestDataManttoTipoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoTipoEdificioDelete(alimentacion);
    }

    @Override
    public ResponseManttoTipoEdificioList manttoTipoEdificioUpdate(
            RequestDataManttoTipoEdificio alimentacion) {
        
        return cmTablasBasicas.manttoTipoEdificioUpdate(alimentacion);
    }

    /* Mantenimiento de tipos de Notas */
    @Override
    public ResponseManttoTipoNotasList manttoTipoNotasQuery() {
        
        return cmTablasBasicas.manttoTipoNotasQuery();
    }

    @Override
    public ResponseManttoTipoNotasList manttoTipoNotasAdd(
            RequestDataManttoTipoNotas alimentacion) {
        
        return cmTablasBasicas.manttoTipoNotasAdd(alimentacion);
    }

    @Override
    public ResponseManttoTipoNotasList manttoTipoNotasDelete(
            RequestDataManttoTipoNotas alimentacion) {
        
        return cmTablasBasicas.manttoTipoNotasDelete(alimentacion);
    }

    @Override
    public ResponseManttoTipoNotasList manttoTipoNotasUpdate(
            RequestDataManttoTipoNotas alimentacion) {
        
        return cmTablasBasicas.manttoTipoNotasUpdate(alimentacion);
    }

    /* Tipo de Competencia */
    @Override
    public ResponseTipoCompetenciaList tipoCompetenciaQuery() {
        
        return cmTablasBasicas.tipoCompetenciaQuery();
    }

    /* Mantenimiento Edificios */
    @Override
    public ResponseManttoEdificiosList manttoEdificiosQuery(
            RequestDataManttoEdificios alimentacion) {
        
        return cmTablasBasicas.manttoEdificiosQuery(alimentacion);
    }

    @Override
    public ResponseManttoEdificiosList manttoEdificiosAdd(
            RequestDataManttoEdificios alimentacion) {
        
        return cmTablasBasicas.manttoEdificiosAdd(alimentacion);
    }

    @Override
    public ResponseManttoEdificiosList manttoEdificiosDelete(
            RequestDataManttoEdificios alimentacion) {
        
        return cmTablasBasicas.manttoEdificiosDelete(alimentacion);
    }

    @Override
    public ResponseManttoEdificiosList manttoEdificiosUpdate(
            RequestDataManttoEdificios alimentacion) {
        
        return cmTablasBasicas.manttoEdificiosUpdate(alimentacion);
    }

    /* Mantenimiento Edificios */
    @Override
    public ResponseManttoMaterialesList manttoMaterialesQuery(
            RequestDataManttoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoMaterialesQuery(alimentacion);
    }

    @Override
    public ResponseManttoMaterialesList manttoMaterialesAdd(
            RequestDataManttoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoMaterialesAdd(alimentacion);
    }

    @Override
    public ResponseManttoMaterialesList manttoMaterialesDelete(
            RequestDataManttoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoMaterialesDelete(alimentacion);
    }

    @Override
    public ResponseManttoMaterialesList manttoMaterialesUpdate(
            RequestDataManttoMateriales alimentacion) {
        
        return cmTablasBasicas.manttoMaterialesUpdate(alimentacion);
    }

    /* Mantenimiento Materiales por Proveedor */
    @Override
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorQuery(
            RequestDataManttoMaterialProveedor alimentacion) {
        
        return cmTablasBasicas.manttoMaterialProveedorQuery(alimentacion);
    }

    @Override
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorAdd(
            RequestDataManttoMaterialProveedor alimentacion) {
        
        return cmTablasBasicas.manttoMaterialProveedorAdd(alimentacion);
    }

    @Override
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorDelete(
            RequestDataManttoMaterialProveedor alimentacion) {
        
        return cmTablasBasicas.manttoMaterialProveedorDelete(alimentacion);
    }

    @Override
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorUpdate(
            RequestDataManttoMaterialProveedor alimentacion) {
        
        return cmTablasBasicas.manttoMaterialProveedorUpdate(alimentacion);
    }

    /* Tipo de Trabajo  */
    @Override
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaQuery() {
        
        return cmTablasBasicas.manttoSupervisorAvanzadaQuery();
    }

    @Override
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaAdd(
            RequestDataSupervisorAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoSupervisorAvanzadaAdd(alimentacion);
    }

    @Override
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaUpdate(
            RequestDataSupervisorAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoSupervisorAvanzadaUpdate(alimentacion);
    }

    @Override
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaDelete(
            RequestDataSupervisorAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoSupervisorAvanzadaDelete(alimentacion);
    }

    @Override
    public ResponseManttoEstratoList manttoEstratoQuery() {
        
        return cmTablasBasicas.manttoEstratoQuery();
    }

    @Override
    public ResponseManttoEstratoList manttoEstratoAdd(
            RequestDataEstrato alimentacion) {
        
        return cmTablasBasicas.manttoEstratoAdd(alimentacion);
    }

    @Override
    public ResponseManttoEstratoList manttoEstratoUpdate(
            RequestDataEstrato alimentacion) {
        
        return cmTablasBasicas.manttoEstratoUpdate(alimentacion);
    }

    @Override
    public ResponseManttoEstratoList manttoEstratoDelete(
            RequestDataEstrato alimentacion) {
        
        return cmTablasBasicas.manttoEstratoDelete(alimentacion);
    }

    @Override
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoQuery() {
        
        return cmTablasBasicas.manttoTipoTrabajoQuery();
    }


    /* Constructoras */
    @Override
    public ResponseConstructorasList constructorasQuery(
            RequestDataConstructoras alimentacion) {
        
        return cmTablasBasicas.constructorasQuery(alimentacion);
    }

    @Override
    public ResponseConstructorasList constructorasAdd(
            RequestDataConstructoras alimentacion) {
        
        return cmTablasBasicas.constructorasAdd(alimentacion);
    }

    @Override
    public ResponseConstructorasList constructorasUpdate(
            RequestDataConstructoras alimentacion) {
        
        return cmTablasBasicas.constructorasUpdate(alimentacion);
    }

    @Override
    public ResponseConstructorasList constructorasDelete(
            RequestDataConstructoras alimentacion) {
        
        return cmTablasBasicas.constructorasDelete(alimentacion);
    }

    /* Mantenimiento Tipo de Trabajo */
    @Override
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoAdd(
            RequestDataTipoTrabajo alimentacion) {
        
        return cmTablasBasicas.manttoTipoTrabajoAdd(alimentacion);
    }

    @Override
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoUpdate(
            RequestDataTipoTrabajo alimentacion) {
        
        return cmTablasBasicas.manttoTipoTrabajoUpdate(alimentacion);
    }

    @Override
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoDelete(
            RequestDataTipoTrabajo alimentacion) {
        
        return cmTablasBasicas.manttoTipoTrabajoDelete(alimentacion);
    }

    /* Mantenimiento Ubicacion Caja */
    @Override
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaQuery() {
        
        return cmTablasBasicas.manttoUbicacionCajaQuery();
    }

    @Override
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaAdd(
            RequestDataUbicacionCaja alimentacion) {
        
        return cmTablasBasicas.manttoUbicacionCajaAdd(alimentacion);
    }

    @Override
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaUpdate(
            RequestDataUbicacionCaja alimentacion) {
        
        return cmTablasBasicas.manttoUbicacionCajaUpdate(alimentacion);
    }

    @Override
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaDelete(
            RequestDataUbicacionCaja alimentacion) {
        
        return cmTablasBasicas.manttoUbicacionCajaDelete(alimentacion);
    }

    /* Mantenimiento Información Nodo */
    @Override
    public ResponseManttoInfoNodoList manttoInformacionNodoQuery(
            RequestDataManttoInfoNodo alimentacion) {
        
        return cmTablasBasicas.manttoInformacionNodoQuery(alimentacion);
    }

    @Override
    public ResponseManttoInfoNodoList manttoInformacionNodoAdd(
            RequestDataManttoInfoNodo alimentacion) {
        
        return cmTablasBasicas.manttoInformacionNodoAdd(alimentacion);
    }

    @Override
    public ResponseManttoInfoNodoList manttoInformacionNodoUpdate(
            RequestDataManttoInfoNodo alimentacion) {
        
        return cmTablasBasicas.manttoInformacionNodoUpdate(alimentacion);
    }

    @Override
    public ResponseManttoInfoNodoList manttoInformacionNodoDelete(
            RequestDataManttoInfoNodo alimentacion) {
        
        return cmTablasBasicas.manttoInformacionNodoDelete(alimentacion);
    }

    @Override
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosQuery(
            RequestDataManttoInformacionBarrios alimentacion) {
        
        return cmTablasBasicas.manttoInformacionBarriosQuery(alimentacion);
    }

    @Override
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosAdd(
            RequestDataManttoInformacionBarrios alimentacion) {
        
        return cmTablasBasicas.manttoInformacionBarriosAdd(alimentacion);
    }

    @Override
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosUpdate(
            RequestDataManttoInformacionBarrios alimentacion) {
        
        return cmTablasBasicas.manttoInformacionBarriosUpdate(alimentacion);
    }

    @Override
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosDelete(
            RequestDataManttoInformacionBarrios alimentacion) {
        
        return cmTablasBasicas.manttoInformacionBarriosDelete(alimentacion);
    }
    
    /* Mantenimiento Asesor Gestion de Avanzada */
    @Override
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaQuery() {
        
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaQuery();
    }
    
    @Override
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaAdd(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaAdd(alimentacion);
    }
    
    @Override
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaUpdate(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaUpdate(alimentacion);
    }
    
    @Override
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaDelete(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoAsesorGestionDeAvanzadaDelete(alimentacion);
    }
    
    /* Mantenimiento Estados Avanzada */
    @Override
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaQuery() {
        
        return cmTablasBasicas.manttoEstadosAvanzadaQuery();
    }
    
    @Override
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaAdd(
            RequestDataManttoEstadosAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoEstadosAvanzadaAdd(alimentacion);
    }
    
    @Override
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaUpdate(
            RequestDataManttoEstadosAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoEstadosAvanzadaUpdate(alimentacion);
    }
    
    @Override
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaDelete(
            RequestDataManttoEstadosAvanzada alimentacion) {
        
        return cmTablasBasicas.manttoEstadosAvanzadaDelete(alimentacion);
    }
    
    /* Asignar Asesor Gestion Avanzada */
    @Override
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaQuery(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        
        return cmTablasBasicas.asignarAsesorAvanzadaQuery(alimentacion);
    }
    
    @Override
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaAdd(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        
        return cmTablasBasicas.asignarAsesorAvanzadaAdd(alimentacion);
    }
    
    @Override
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaUpdate(
            RequestDataAsignarAsesorAvanzada alimentacion) {
        
        return cmTablasBasicas.asignarAsesorAvanzadaUpdate(alimentacion);
    }
    
    /* Mantenimiento Motivos Cambio Fecha */
    @Override
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaQuery() {
        
        return cmTablasBasicas.manttoMotivosCambioFechaQuery();
    }
    
    @Override
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaAdd(
            RequestDataMotivosCambioFecha alimentacion) {
        
        return cmTablasBasicas.manttoMotivosCambioFechaAdd(alimentacion);
    }
    
    @Override
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaUpdate(
            RequestDataMotivosCambioFecha alimentacion) {
        
        return cmTablasBasicas.manttoMotivosCambioFechaUpdate(alimentacion);
    }
    
    @Override
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaDelete(
            RequestDataMotivosCambioFecha alimentacion) {
        
        return cmTablasBasicas.manttoMotivosCambioFechaDelete(alimentacion);
    }
    
    /* Mantenimiento Tipo Competencia */
    @Override
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaQuery() {
        
        return cmTablasBasicas.manttoTipoCompetenciaQuery();
    }
    
    @Override
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaAdd(
            RequestDataManttoTipoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoTipoCompetenciaAdd(alimentacion);
    }
    
    @Override
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaUpdate(
            RequestDataManttoTipoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoTipoCompetenciaUpdate(alimentacion);
    }
    
    @Override
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaDelete(
            RequestDataManttoTipoCompetencia alimentacion) {
        
        return cmTablasBasicas.manttoTipoCompetenciaDelete(alimentacion);
    }
}