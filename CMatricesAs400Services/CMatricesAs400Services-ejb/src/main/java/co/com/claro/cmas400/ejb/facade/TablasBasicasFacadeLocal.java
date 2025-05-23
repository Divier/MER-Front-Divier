/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.facade;

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
import javax.ejb.Local;

/**
 *
 * @author Alquiler
 */
@Local
public interface TablasBasicasFacadeLocal {
    /* Alimentacion Electrica */

    public ResponseManttoAlimentacionElectList manttoAlimentacionElectAdd(
            RequestDataAlimentacionElectrica alimentacion);

    public ResponseManttoAlimentacionElectList manttoAlimentacionElectQuery();

    public ResponseManttoAlimentacionElectList manttoAlimentacionElectUpdate(
            RequestDataAlimentacionElectrica alimentacion);

    public ResponseManttoAlimentacionElectList manttoAlimentacionElectDelete(
            RequestDataAlimentacionElectrica alimentacion);

    /* Compañia Addministracion */
    public ResponseAdminCompanyList manttoAdminCompanyQuery(
            RequestDataAdminCompany alimentacionRequest);

    public ResponseAdminCompanyList manttoAdminCompanyAdd(
            RequestDataAdminCompany alimentacionRequest);

    public ResponseAdminCompanyList manttoAdminCompanyUpdate(
            RequestDataAdminCompany alimentacionRequest);

    public ResponseAdminCompanyList manttoAdminCompanyDelete(
            RequestDataAdminCompany alimentacionRequest);

    /* Compañias de Ascensores */
    public ResponseCiaAscensoresList manttoCiaAscensoresQuery();

    public ResponseCiaAscensoresList manttoCiaAscensoresAdd(
            RequestDataCiaAscensores alimentacion);

    public ResponseCiaAscensoresList manttoCiaAscensoresUpdate(
            RequestDataCiaAscensores alimentacion);

    public ResponseCiaAscensoresList manttoCiaAscensoresDelete(
            RequestDataCiaAscensores alimentacion);

    /* Razon Arreglo */
    public ResponseRazonArregloList manttoRazonArregloQuery();

    public ResponseRazonArregloList manttoRazonArregloAdd(
            RequestDataRazonArreglo alimentacion);

    public ResponseRazonArregloList manttoRazonArregloUpdate(
            RequestDataRazonArreglo alimentacion);

    public ResponseRazonArregloList manttoRazonArregloDelete(
            RequestDataRazonArreglo alimentacion);

    /* Black List */
    public ResponseCodigoBlackList manttoCodigoBlackListQuery();

    public ResponseCodigoBlackList manttoCodigoBlackListAdd(
            RequestDataCodigoBlackList alimentacion);

    public ResponseCodigoBlackList manttoCodigoBlackListUpdate(
            RequestDataCodigoBlackList alimentacion);

    public ResponseCodigoBlackList manttoCodigoBlackListDelete(
            RequestDataCodigoBlackList alimentacion);

    /* Origen Datos */
    public ResponseOrigenDatosList manttoOrigenDatosQuery();

    public ResponseOrigenDatosList manttoOrigenDatosAdd(
            RequestDataOrigenDatos alimentacion);

    public ResponseOrigenDatosList manttoOrigenDatosUpdate(
            RequestDataOrigenDatos alimentacion);

    public ResponseOrigenDatosList manttoOrigenDatosDelete(
            RequestDataOrigenDatos alimentacion);

    /* Tipo Acometida */
    public ResponseTipoAcometidaList manttoTipoAcometidaQuery();

    public ResponseTipoAcometidaList manttoTipoAcometidaAdd(
            RequestDataTipoAcometida alimentacion);

    public ResponseTipoAcometidaList manttoTipoAcometidaUpdate(
            RequestDataTipoAcometida alimentacion);

    public ResponseTipoAcometidaList manttoTipoAcometidaDelete(
            RequestDataTipoAcometida alimentacion);

    /* Tipos Materiales */
    public ResponseTipoMaterialesList manttoTipoMaterialesQuery();

    public ResponseTipoMaterialesList manttoTipoMaterialesAdd(
            RequestDataTipoMateriales alimentacion);

    public ResponseTipoMaterialesList manttoTipoMaterialesUpdate(
            RequestDataTipoMateriales alimentacion);

    public ResponseTipoMaterialesList manttoTipoMaterialesDelete(
            RequestDataTipoMateriales alimentacion);

    /* Tipo Proyecto */
    public ResponseTipoProyectoList manttoTipoProyectoQuery();

    public ResponseTipoProyectoList manttoTipoProyectoAdd(
            RequestDataTipoProyecto alimentacion);

    public ResponseTipoProyectoList manttoTipoProyectoUpdate(
            RequestDataTipoProyecto alimentacion);

    public ResponseTipoProyectoList manttoTipoProyectoDelete(
            RequestDataTipoProyecto alimentacion);

    /* Informacion Nodos */
    public ResponseManttoNodosList manttoNodosQuery(
            RequestDataManttoNodos alimentacion);

    /* Estados Edificio */
    public ResponseManttoEstadoEdificioList manttoEstadoEdificioQuery();

    public ResponseManttoEstadoEdificioList manttoEstadoEdificioAdd(
            RequestDataManttoEstadoEdificio alimentacion);

    public ResponseManttoEstadoEdificioList manttoEstadoEdificioDelete(
            RequestDataManttoEstadoEdificio alimentacion);

    public ResponseManttoEstadoEdificioList manttoEstadoEdificioUpdate(
            RequestDataManttoEstadoEdificio alimentacion);

    public ResponseManttoEstadoEdificioList manttoEstadoEdificioHelp();

    /* Estado Resultado OT */
    public ResponseEstadoResultadoOtList estadoResultadoOtQuery();

    public ResponseEstadoResultadoOtList estadoResultadoOtAdd(
            RequestDataEstadoResultadoOt alimentacion);

    public ResponseEstadoResultadoOtList estadoResultadoOtDelete(
            RequestDataEstadoResultadoOt alimentacion);

    public ResponseEstadoResultadoOtList estadoResultadoOtUpdate(
            RequestDataEstadoResultadoOt alimentacion);

    /* Mantenimiento Competencia */
    public ResponseManttoCompetenciaList manttoCompetenciaQuery();

    public ResponseManttoCompetenciaList manttoCompetenciaAdd(
            RequestDataManttoCompetencia alimentacion);

    public ResponseManttoCompetenciaList manttoCompetenciaDelete(
            RequestDataManttoCompetencia alimentacion);

    public ResponseManttoCompetenciaList manttoCompetenciaUpdate(
            RequestDataManttoCompetencia alimentacion);

    /* Productos */
    public ResponseProductosList productosQuery();

    public ResponseProductosList productosAdd(
            RequestDataProductos alimentacion);

    public ResponseProductosList productosDelete(
            RequestDataProductos alimentacion);

    public ResponseProductosList productosUpdate(
            RequestDataProductos alimentacion);

    /* Proveedores */
    public ResponseProveedoresList proveedoresQuery();

    public ResponseProveedoresList proveedoresAdd(
            RequestDataProveedores alimentacion);

    public ResponseProveedoresList proveedoresDelete(
            RequestDataProveedores alimentacion);

    public ResponseProveedoresList proveedoresUpdate(
            RequestDataProveedores alimentacion);

    /* Mantenimiento Punto Inicial */
    public ResponseManttoPuntoInicialList manttoPuntoInicialQuery();

    public ResponseManttoPuntoInicialList manttoPuntoInicialAdd(
            RequestDataManttoPuntoInicial alimentacion);

    public ResponseManttoPuntoInicialList manttoPuntoInicialDelete(
            RequestDataManttoPuntoInicial alimentacion);

    public ResponseManttoPuntoInicialList manttoPuntoInicialUpdate(
            RequestDataManttoPuntoInicial alimentacion);

    /* Mantenimiento de tipo de distribución de red interna */
    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaQuery();

    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaAdd(
            RequestDataManttoTipoDistribucionRedInterna alimentacion);

    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaDelete(
            RequestDataManttoTipoDistribucionRedInterna alimentacion);

    public ResponseManttoTipoDistribucionRedInternaList manttoTipoDistribucionRedInternaUpdate(
            RequestDataManttoTipoDistribucionRedInterna alimentacion);

    /* Mantenimiento de tipos de edificios */
    public ResponseManttoTipoEdificioList manttoTipoEdificioQuery();

    public ResponseManttoTipoEdificioList manttoTipoEdificioAdd(
            RequestDataManttoTipoEdificio alimentacion);

    public ResponseManttoTipoEdificioList manttoTipoEdificioDelete(
            RequestDataManttoTipoEdificio alimentacion);

    public ResponseManttoTipoEdificioList manttoTipoEdificioUpdate(
            RequestDataManttoTipoEdificio alimentacion);

    /* Mantenimiento de tipos de Notas */
    public ResponseManttoTipoNotasList manttoTipoNotasQuery();

    public ResponseManttoTipoNotasList manttoTipoNotasAdd(
            RequestDataManttoTipoNotas alimentacion);

    public ResponseManttoTipoNotasList manttoTipoNotasDelete(
            RequestDataManttoTipoNotas alimentacion);

    public ResponseManttoTipoNotasList manttoTipoNotasUpdate(
            RequestDataManttoTipoNotas alimentacion);

    /* Tipo de Competencia*/
    public ResponseTipoCompetenciaList tipoCompetenciaQuery();

    /* Mantenimiento Edificios */
    public ResponseManttoEdificiosList manttoEdificiosQuery(
            RequestDataManttoEdificios alimentacion);

    public ResponseManttoEdificiosList manttoEdificiosAdd(
            RequestDataManttoEdificios alimentacion);

    public ResponseManttoEdificiosList manttoEdificiosDelete(
            RequestDataManttoEdificios alimentacion);

    public ResponseManttoEdificiosList manttoEdificiosUpdate(
            RequestDataManttoEdificios alimentacion);

    /* Mantenimiento Materiales */
    public ResponseManttoMaterialesList manttoMaterialesQuery(
            RequestDataManttoMateriales alimentacion) ;

    public ResponseManttoMaterialesList manttoMaterialesAdd(
            RequestDataManttoMateriales alimentacion);

    public ResponseManttoMaterialesList manttoMaterialesDelete(
            RequestDataManttoMateriales alimentacion);

    public ResponseManttoMaterialesList manttoMaterialesUpdate(
            RequestDataManttoMateriales alimentacion);

    /* Mantenimiento Material por Proveedor */
    public ResponseManttoMaterialProveedorList manttoMaterialProveedorQuery(
            RequestDataManttoMaterialProveedor alimentacion);

    public ResponseManttoMaterialProveedorList manttoMaterialProveedorAdd(
            RequestDataManttoMaterialProveedor alimentacion);

    public ResponseManttoMaterialProveedorList manttoMaterialProveedorDelete(
            RequestDataManttoMaterialProveedor alimentacion);

    public ResponseManttoMaterialProveedorList manttoMaterialProveedorUpdate(
            RequestDataManttoMaterialProveedor alimentacion);

    /* Tipo de Trabajo */
    public ResponseManttoTipoTrabajoList manttoTipoTrabajoQuery();

    public ResponseManttoTipoTrabajoList manttoTipoTrabajoAdd(
            RequestDataTipoTrabajo alimentacion);

    public ResponseManttoTipoTrabajoList manttoTipoTrabajoUpdate(
            RequestDataTipoTrabajo alimentacion);

    public ResponseManttoTipoTrabajoList manttoTipoTrabajoDelete(
            RequestDataTipoTrabajo alimentacion);


    /* Supervisor Avanzada */
    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaQuery();

    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaAdd(
            RequestDataSupervisorAvanzada alimentacion);

    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaUpdate(
            RequestDataSupervisorAvanzada alimentacion);

    public ResponseSupervisorAvanzadaList manttoSupervisorAvanzadaDelete(
            RequestDataSupervisorAvanzada alimentacion);

    /* Constructoras */
    public ResponseConstructorasList constructorasQuery(
            RequestDataConstructoras alimentacion);

    public ResponseConstructorasList constructorasAdd(
            RequestDataConstructoras alimentacion);

    public ResponseConstructorasList constructorasUpdate(
            RequestDataConstructoras alimentacion);

    public ResponseConstructorasList constructorasDelete(
            RequestDataConstructoras alimentacion);

    /* Tipo Estrato */
    public ResponseManttoEstratoList manttoEstratoQuery();

    public ResponseManttoEstratoList manttoEstratoAdd(
            RequestDataEstrato alimentacion);

    public ResponseManttoEstratoList manttoEstratoUpdate(
            RequestDataEstrato alimentacion);

    public ResponseManttoEstratoList manttoEstratoDelete(
            RequestDataEstrato alimentacion);

    /* Ubicacion Caja */
    public ResponseManttoUbicacionCajaList manttoUbicacionCajaQuery();

    public ResponseManttoUbicacionCajaList manttoUbicacionCajaAdd(
            RequestDataUbicacionCaja alimentacion);

    public ResponseManttoUbicacionCajaList manttoUbicacionCajaUpdate(
            RequestDataUbicacionCaja alimentacion);

    public ResponseManttoUbicacionCajaList manttoUbicacionCajaDelete(
            RequestDataUbicacionCaja alimentacion);

    /* Mantenimiento Información Nodo */
    public ResponseManttoInfoNodoList manttoInformacionNodoQuery(
            RequestDataManttoInfoNodo alimentacion);

    public ResponseManttoInfoNodoList manttoInformacionNodoAdd(
            RequestDataManttoInfoNodo alimentacion);

    public ResponseManttoInfoNodoList manttoInformacionNodoUpdate(
            RequestDataManttoInfoNodo alimentacion);

    public ResponseManttoInfoNodoList manttoInformacionNodoDelete(
            RequestDataManttoInfoNodo alimentacion);

    /* Informacion Barrios */
    public ResponseManttoInformacionBarriosList manttoInformacionBarriosQuery(
            RequestDataManttoInformacionBarrios alimentacion);

    public ResponseManttoInformacionBarriosList manttoInformacionBarriosAdd(
            RequestDataManttoInformacionBarrios alimentacion);

    public ResponseManttoInformacionBarriosList manttoInformacionBarriosUpdate(
            RequestDataManttoInformacionBarrios alimentacion);

    public ResponseManttoInformacionBarriosList manttoInformacionBarriosDelete(
            RequestDataManttoInformacionBarrios alimentacion);
    
    /* Mantenimiento Asesor Gestion de Avanzada */
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaQuery();
    
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaAdd(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion);
    
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaUpdate(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion);
    
    public ResponseManttoAsesorGestionDeAvanzadaList manttoAsesorGestionDeAvanzadaDelete(
            RequestDataManttoAsesorGestionDeAvanzada alimentacion);
    
    /* Mantenimiento Estados Avanzada */
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaQuery();
    
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaAdd(
            RequestDataManttoEstadosAvanzada alimentacion);
    
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaUpdate(
            RequestDataManttoEstadosAvanzada alimentacion);
    
    public ResponseManttoEstadosAvanzadaList manttoEstadosAvanzadaDelete(
            RequestDataManttoEstadosAvanzada alimentacion);
    
    /* Asignar Asesor Gestion Avanzada */
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaQuery(
            RequestDataAsignarAsesorAvanzada alimentacion);
    
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaAdd(
            RequestDataAsignarAsesorAvanzada alimentacion);
    
    public ResponseAsignarAsesorAvanzadaList asignarAsesorAvanzadaUpdate(
            RequestDataAsignarAsesorAvanzada alimentacion);
    
    /* Mantenimiento Motivos Cambio Fecha */
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaQuery();
    
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaAdd(
            RequestDataMotivosCambioFecha alimentacion);
    
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaUpdate(
            RequestDataMotivosCambioFecha alimentacion);
    
    public ResponseManttoMotivosCambioFechaList manttoMotivosCambioFechaDelete(
            RequestDataMotivosCambioFecha alimentacion);
    
    /* Mantenimiento Tipo Competencia */
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaQuery();
    
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaAdd(
            RequestDataManttoTipoCompetencia alimentacion);
    
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaUpdate(
            RequestDataManttoTipoCompetencia alimentacion);
    
    public ResponseManttoTipoCompetenciaList manttoTipoCompetenciaDelete(
            RequestDataManttoTipoCompetencia alimentacion);
}
