package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRAliadosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRREstadoNodosRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRTipificacionDeRedRequest;
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
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.ResponseAdminCompanyList;
import co.com.claro.cmas400.ejb.respons.ResponseAsignarAsesorAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseCiaAscensoresList;
import co.com.claro.cmas400.ejb.respons.ResponseCodigoBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoBarrios;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoEstadoEdificio;
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
import co.com.claro.cmas400.ejb.respons.ResponseTipoMaterialesList;
import co.com.claro.cmas400.ejb.respons.ResponseTipoProyectoList;
import co.com.claro.mer.utils.TransactionParameters;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRAllies;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRNetworkTyping;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRStateNodes;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientTablasBasicas;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressService;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author camargomf
 */
public class CmtTablasBasicasRRMglManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtTablasBasicasRRMglManager.class);

    ParametrosMglManager parametrosMglManager;
    RestClientTablasBasicas restClientTablasBasicas;
    RestClientBasicMaintenanceRRAllies restClientBasicMaintenanceRRAllies;
    RestClientBasicMaintenanceRRStateNodes restClientBasicMaintenanceRRStateNodes;
    RestClientBasicMaintenanceRRNetworkTyping restClientBasicMaintenanceRRNetworkTyping;
    String BASE_URI;
    String BASE_URI_RR;

    public CmtTablasBasicasRRMglManager() {
       
    }

    /**
     * Creación de Tablas Básicas desde en RR
     *
     * @param cmtBasicaMgl
     * @param usuario
     * @return updated
     * @throws ApplicationException
     */
    public boolean actionCreate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        boolean created = true;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ)) {
            return estadoCuentaMatrizAdd(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_NOTAS)) {
            return tipoDeNotasAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ)) {
            return tipoDeEdificioAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA)) {
            return tipoDeAlimentacionElectricaAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_DISTRIBUCION)) {
            return tipoDistribucionInternaAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PUNTO_INICIAL_ACOMETIDA)) {
            return puntoInicialAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_COMPETENCIA)) {
            return tipoCompetenciaAdd(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_TRABAJO)) {
            return tipoDeTrabajoAdd(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_PROYECTO)) {
            return tipoDeProyectoAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_CAUSAS_DE_MANTENIMIENTO)) {
            return causasDeMantenimientoAdd(cmtBasicaMgl, usuario);
        }
        //Verificar Tipo Basica Estado Interno OT con Estado Resultado OT
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_RESULTADO)) {
            return estadoResultadoOtAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_BLACK_LIST_CM)) {
            return blackListCmAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ORIGEN_DE_DATOS)) {
            return origenDatosAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_UBICACION_EQUIPOS)) {
            return ubicacionCajaAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_MATERIAL)) {
            return tipoDeMaterialAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTRATO)) {
            return estratoAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_ACOMETIDA)) {
            return tipoAcometidaAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PRODUCTO)) {
            return productoAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_INTERNO_GA)) {
            return manttoEstadosAvanzadaAdd(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ALIADOS)) {
            return manttoAliadoCreate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_NODO)) {
            return estadoNodoAdd(cmtBasicaMgl, usuario);
        }
        /*Validar Tipo Basica Estado Interno con identificador  */
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TECNOLOGIA)) {
            return crearTipificacionRed(cmtBasicaMgl, usuario);
        }

        return created;
    }

    /**
     * Modificación de Tablas Básicas en RR.
     *
     * @param cmtBasicaMgl
     * @param usuario
     * @return updated
     * @throws ApplicationException
     */
    public boolean actionUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        boolean updated = true;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
      
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ)) {
            return estadoCuentaMatrizUpdate(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_NOTAS)) {
            return tipoDeNotasUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ)) {
            return tipoDeEdificioUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA)) {
            return tipoDeAlimentacionElectricaUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_DISTRIBUCION)) {
            return tipoDistribucionInternaUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PUNTO_INICIAL_ACOMETIDA)) {
            return puntoInicialUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_COMPETENCIA)) {
            return tipoCompetenciaUpdate(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_TRABAJO)) {
            return tipoDeTrabajoUpdate(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_PROYECTO)) {
            return tipoDeProyectoUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_CAUSAS_DE_MANTENIMIENTO)) {
            return causasDeMantenimientoUpdate(cmtBasicaMgl, usuario);
        }
        //Listo
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_RESULTADO)) {
            return estadoResultadoOtUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_BLACK_LIST_CM)) {
            return blackListCmUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ORIGEN_DE_DATOS)) {
            return origenDatosUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_UBICACION_EQUIPOS)) {
            return ubicacionCajaUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_MATERIAL)) {
            return tipoDeMaterialUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTRATO)) {
            return estratoUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_ACOMETIDA)) {
            return tipoAcometidaUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PRODUCTO)) {
            return productoUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_INTERNO_GA)) {
            return manttoEstadosAvanzadaUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ALIADOS)) {
            return manttoAliadoUpdate(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_NODO)) {
            return estadoNodoUpdate(cmtBasicaMgl, usuario);
        }
        /*Validar Tipo Basica Estado Interno con identificador  */
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TECNOLOGIA)) {
            return actualizarTipificacionRed(cmtBasicaMgl, usuario);
        }
        return updated;
    }

    /**
     * Eliminación de Tablas Básicas en RR.
     *
     * @param cmtBasicaMgl
     * @param usuario
     * @return deleted
     * @throws ApplicationException
     */
    public boolean actionDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        boolean deleted = true;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ)) {
            return estadoCuentaMatrizDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_NOTAS)) {
            return tipoDeNotasDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ)) {
            return tipoDeEdificioDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA)) {
            return tipoDeAlimentacionElectricaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_DISTRIBUCION)) {
            return tipoDistribucionInternaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PUNTO_INICIAL_ACOMETIDA)) {
            return puntoInicialDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_COMPETENCIA)) {
            return tipoCompetenciaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_TRABAJO)) {
            return tipoDeTrabajoDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_DE_PROYECTO)) {
            return tipoDeProyectoDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_CAUSAS_DE_MANTENIMIENTO)) {
            return causasDeMantenimientoDelete(cmtBasicaMgl, usuario);
        }
        //***********Verificar Tipo Basica Estado Interno OT con Estado Resultado OT***********
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_RESULTADO)) {
            return estadoResultadoOtDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_BLACK_LIST_CM)) {
            return blackListCmDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ORIGEN_DE_DATOS)) {
            return origenDatosDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_UBICACION_EQUIPOS)) {
            return ubicacionCajaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_MATERIAL)) {
            return tipoDeMaterialDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTRATO)) {
            return estratoDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TIPO_ACOMETIDA)) {
            return tipoAcometidaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_PRODUCTO)) {
            return productoDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_INTERNO_GA)) {
            return manttoEstadosAvanzadaDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ALIADOS)) {
            return manttoAliadoDelete(cmtBasicaMgl, usuario);
        }
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_ESTADO_NODO)) {
            return estadoNodoDelete(cmtBasicaMgl, usuario);
        }
        /*Validar Tipo Basica Estado Interno con identificador  */
        if (cmtBasicaMgl.getTipoBasicaObj().getIdentificadorInternoApp()
                .equals(Constant.TIPO_BASICA_TECNOLOGIA)) {
            return eliminarTipificacionRed(cmtBasicaMgl, usuario);
        }
        return deleted;
    }

    /*
     * Tabla RR Mantenimiento Edificio, Nombre tipo: Edificio
     */
    public boolean edificioAdd(NodoMgl nodoMgl, //NodoRR nodoRR
            AddressService addressService,
            CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            String usuarioOriginal, boolean isFichasNodos) throws ApplicationException {
        ResponseManttoEdificiosList responseManttoEdificiosList;
        CmtCuentaMatrizRRMglManager cmtCuentaMatrizRRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean isSuedificioGeneral;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        try {
            NodoMgl nodoSolicitud;
            //crear CM as 400
            NodoMglManager  nodoMglManager  = new NodoMglManager ();
            nodoSolicitud = nodoMglManager.findById(nodoMgl.getNodId());
            
            String comunidad = null;
            String division = null;
            if (nodoSolicitud.getComId() != null) {
                comunidad = nodoSolicitud.getComId().getCodigoRr();
                if (nodoSolicitud.getComId().getRegionalRr() != null) {
                    division = nodoSolicitud.getComId().getRegionalRr().getCodigoRr();
                }
            }

            CmtDireccionMgl cmtDireccionMgl = cmtCuentaMatrizMgl.getDireccionPrincipal();
            RequestDataManttoEdificios requestDataManttoEdificios = new RequestDataManttoEdificios();
            requestDataManttoEdificios.setDescripcion(cmtCuentaMatrizMgl.getNombreCuenta());
            requestDataManttoEdificios.setCodigoDivision(division);
            requestDataManttoEdificios.setCodigoComunidad(comunidad); 

            String Barrio = "N.N";
            if (cmtDireccionMgl.getBarrio() != null && !cmtDireccionMgl.getBarrio().isEmpty()) {
                Barrio = cmtDireccionMgl.getBarrio();
            } else if (addressService.getNeighborhood() != null && !addressService.getNeighborhood().isEmpty()) {
                Barrio = addressService.getNeighborhood();
            }
            if (cmtDireccionMgl.getBarrio() != null && cmtDireccionMgl.getBarrio().equals("-")) {
                Barrio = "N.N";
            }
            String codBarrioRr = "";
            ResponseManttoInformacionBarriosList responseBarrios = null;
            if (Barrio != null
                    && !Barrio.isEmpty()) {
                //Validacion en longitud de campo barrio, si es > a 25 se toman los primeros 25 para insertarse en RR
                if(Barrio.length() > 25){
                    Barrio = Barrio.substring(0, 25);
                }
                responseBarrios = barrioConsulta(cmtCuentaMatrizMgl, usuario, Barrio);
            }
            if (responseBarrios != null
                    && responseBarrios.getListManttoBarrios() != null
                    && !responseBarrios.getListManttoBarrios().isEmpty()) {
                for (ResponseDataManttoBarrios barrio : responseBarrios.getListManttoBarrios()) {
                    
                    //Validacion en longitud de campo barrio, si es > a 25 se toman los primeros 25 para insertarse en RR
                    if(barrio.getDescripcion().length() > 25){
                        if(barrio.getDescripcion().substring(0, 25).trim().equalsIgnoreCase(Barrio != null ? Barrio.trim():"")){
                            codBarrioRr = barrio.getCodigo();
                            break;
                        }
                    }
                    else{
                        if (barrio.getDescripcion().trim().equalsIgnoreCase(Barrio != null ? Barrio.trim():"")) {
                            codBarrioRr = barrio.getCodigo();
                            break;
                        }
                    }
                     
                }
            }
            //si no se encontro el barrio se debe crear sobre RR
            if (codBarrioRr == null || codBarrioRr.isEmpty()) {
                ResponseDataManttoBarrios barrioCreado = barrioAdd(Barrio,
                        division, 
                        comunidad, usuario); 
                if (barrioCreado != null) {
                    codBarrioRr = barrioCreado.getCodigo();
                }
            }
            requestDataManttoEdificios.setCodigoBarrio(codBarrioRr);
            requestDataManttoEdificios.setNombreUsuario(usuario);
            //implementa escritura de tiempos de respuesta en log para creacion de cuenta Matriz en RR
            LOGGER.info("Ingresa al metodo edificioAdd");
            TransactionParameters ini = TransactionParameters.iniciarTransaccion();
            
            long timeIni = 0;
            long timeFin = 0;
            int dataSize = 1024 * 1024;

            Runtime runtime = Runtime.getRuntime();
            timeIni = System.currentTimeMillis();
            //TODO: Crear Calle y cruce en RR.
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            responseManttoEdificiosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_EDIFICIOS_ADD,
                    ResponseManttoEdificiosList.class,
                    requestDataManttoEdificios);
            
            timeFin = System.currentTimeMillis() - timeIni;
            LOGGER.info("Tiempo de procesamiento Transaccion: " +ini.getUuid().toString()+ " - " + timeFin + " ms");
            LOGGER.info("Memoria total: " + runtime.totalMemory() / dataSize + "MB " + 
                        "Memoria libre: " + runtime.freeMemory() / dataSize + "MB " +  
                        "Memoria usada: " + (runtime.totalMemory() - runtime.freeMemory()) / dataSize + "MB");
            if (responseManttoEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage(), ex);
        }
        cmtCuentaMatrizMgl.setNumeroCuenta(
                new BigDecimal(responseManttoEdificiosList.getListManttoEdificios().get(0).getCodigo()));
        
        String identificadorSubGeneral = cmtCuentaMatrizMgl.getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null ? 
                cmtCuentaMatrizMgl.getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() : "";
        
        boolean isMultiedificio = identificadorSubGeneral.equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
        CmtNotasMgl cmtNotasMglCmCrea = new CmtNotasMgl();
        Date hoy = new Date();
        if (isMultiedificio) {
            for (CmtSubEdificioMgl csem : cmtCuentaMatrizMgl.getListCmtSubEdificioMglActivos()) {
                isSuedificioGeneral = csem.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && csem.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                        .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);

                if (isSuedificioGeneral) {
                    //JDHT Si ya se aplico la logica de corregimiento, no aplicarla más
                    csem.setCorregimientoAplicado(cmtCuentaMatrizMgl.isCorregimientoAplicado());
                    if (!cmtCuentaMatrizRRMglManager.addInfoCm(csem, usuario)) {
                        throw new ApplicationException("(As400) Error creando la informacion de la CM");
                    }
                    cmtNotasMglCmCrea.setSubEdificioObj(csem);
                    cmtNotasMglCmCrea.setDescripcion("CONTACTO ADMINISTRACION");
                    if (csem.getAdministrador() != null && !csem.getAdministrador().isEmpty()) {
                        cmtNotasMglCmCrea.setNota("CONTACTO: " + csem.getAdministrador());
                    } else {
                        cmtNotasMglCmCrea.setNota("CONTACTO: NA.");
                    }
                    cmtNotasMglCmCrea.setFechaCreacion(hoy);
                    cmtCuentaMatrizRRMglManager.notasCuentaMatrizAdd(cmtNotasMglCmCrea, usuario);
                    if (addressService != null) {
                        cmtNotasMglCmCrea.setDescripcion("INFO CATASTRAL");
                        String notaCatastral = "";
                        notaCatastral += "DIRECCION: " + addressService.getAddress() + "; \n";
                        notaCatastral += "BARRIO: " + addressService.getNeighborhood() + "; \n";
                        notaCatastral += "NIVEL SOCIO ECONOMICO: " + addressService.getLeveleconomic() + "; \n";
                        notaCatastral += "CX: " + addressService.getCx() + "; \n";
                        notaCatastral += "CY: " + addressService.getCy() + "; \n";
                        notaCatastral += "LOCALIDAD: " + addressService.getLocality() + "; \n";
                        notaCatastral += "NODO-BI: " + addressService.getNodoUno() + "; \n";
                        notaCatastral += "NODO-UN1: " + addressService.getNodoDos() + "; \n";
                        notaCatastral += "NODO-UN2: " + addressService.getNodoTres() + "; \n";
                        notaCatastral += "MAYA: " + addressService.getSource() + "; \n";
                        notaCatastral += "ESTADO: " + addressService.getState() + "; \n";
                        notaCatastral += "DIRECCION ANTIGUA: " + addressService.getAlternateaddress() + "; \n";
                        cmtNotasMglCmCrea.setNota(notaCatastral);

                        cmtCuentaMatrizRRMglManager.notasCuentaMatrizAdd(cmtNotasMglCmCrea, usuario);
                    }
                    if (csem.getListCmtBlackListMgl() != null) {
                        for (CmtBlackListMgl blackListMgl : csem.getListCmtBlackListMgl()) {
                            cmtCuentaMatrizRRMglManager.createBlackListCm(blackListMgl, usuario);
                        }
                    }
                } else {
                    if (!cmtCuentaMatrizRRMglManager.manttoSubEdificiosAdd(csem, usuario)) {
                        throw new ApplicationException("(As400) Error creando sub edificio");
                    }

                    if (!cmtCuentaMatrizRRMglManager.updateInfoAdicionalSubEdificio(csem, isMultiedificio, usuario)) {
                        throw new ApplicationException("(As400) Agregando informacion adicinal subedificios");
                    }
                    DireccionRREntity direccionRREntitySubEdificio = new DireccionRREntity();
                    direccionRREntitySubEdificio.setComunidad(cmtCuentaMatrizMgl.getComunidad());
                    direccionRREntitySubEdificio.setDivision(cmtCuentaMatrizMgl.getDivision());
                    direccionRREntitySubEdificio.setCalle(cmtCuentaMatrizMgl.getDireccionPrincipal().getCalleRr() + " " + csem.getNombreSubedificio());
                    direccionRREntitySubEdificio.setNumeroUnidad(cmtCuentaMatrizMgl.getDireccionPrincipal().getUnidadRr());
                    createDireccionStreetCruseRR(direccionRREntitySubEdificio, cmtCuentaMatrizMgl.getDireccionPrincipal().getCodTipoDir());
                }
            }
        } else {
            //JDHT Si ya se aplico la logica de corregimiento, no aplicarla más
            cmtCuentaMatrizMgl.getSubEdificioGeneral().setCorregimientoAplicado(cmtCuentaMatrizMgl.isCorregimientoAplicado());
            if (!cmtCuentaMatrizRRMglManager.addInfoCm(cmtCuentaMatrizMgl.getSubEdificioGeneral(), usuario)) {
                throw new ApplicationException("(As400) Error creando la informacion de la CM");
            }
            if (!cmtCuentaMatrizRRMglManager.updateInfoAdicionalSubEdificio(cmtCuentaMatrizMgl.getSubEdificioGeneral(), isMultiedificio, usuario)) {
                throw new ApplicationException("(As400) Agregando informacion adicional unico edificio");
            }
            cmtNotasMglCmCrea.setSubEdificioObj(cmtCuentaMatrizMgl.getSubEdificioGeneral());
            cmtNotasMglCmCrea.setDescripcion("CONTACTO ADMINISTRACION");
            cmtNotasMglCmCrea.setNota("CONTACTO: " + cmtCuentaMatrizMgl.getSubEdificioGeneral().getAdministrador());
            cmtNotasMglCmCrea.setFechaCreacion(hoy);
            cmtCuentaMatrizRRMglManager.notasCuentaMatrizAdd(cmtNotasMglCmCrea, usuario);
            if (addressService != null) {
                cmtNotasMglCmCrea.setDescripcion("INFO CATASTRAL");
                String notaCatastral = "";
                notaCatastral += "DIRECCION: " + addressService.getAddress() + "; \n";
                notaCatastral += "BARRIO: " + addressService.getNeighborhood() + "; ";
                notaCatastral += "NIVEL SOCIO ECONOMICO: " + addressService.getLeveleconomic() + "; \n";
                notaCatastral += "CX: " + addressService.getCx() + "; \n";
                notaCatastral += "CY: " + addressService.getCy() + "; \n";
                notaCatastral += "LOCALIDAD: " + addressService.getLocality() + "; \n";
                notaCatastral += "NODO-BI: " + addressService.getNodoUno() + "; \n";
                notaCatastral += "NODO-UN1: " + addressService.getNodoDos() + "; \n";
                notaCatastral += "NODO-UN2: " + addressService.getNodoTres() + "; \n";
                notaCatastral += "MAYA: " + addressService.getSource() + "; \n";
                notaCatastral += "ESTADO: " + addressService.getState() + "; \n";
                notaCatastral += "DIRECCION ANTIGUA: " + addressService.getAlternateaddress() + "; \n";
                cmtNotasMglCmCrea.setNota(notaCatastral);
                cmtCuentaMatrizRRMglManager.notasCuentaMatrizAdd(cmtNotasMglCmCrea, usuario);
            }
            if (!isFichasNodos) {
                if (cmtCuentaMatrizMgl.getSubEdificioGeneral().getListCmtBlackListMgl() != null) {
                    for (CmtBlackListMgl blackListMgl : cmtCuentaMatrizMgl.getSubEdificioGeneral().getListCmtBlackListMgl()) {
                        cmtCuentaMatrizRRMglManager.createBlackListCm(blackListMgl, usuario);
                    }
                }
            }

        }

        cmtCuentaMatrizMgl.setNumeroCuenta(new BigDecimal(responseManttoEdificiosList.getListManttoEdificios().get(0).getCodigo()));
        return true;
    }

    public boolean createDireccionStreetCruseRR(DireccionRREntity direccionRREntity, String tipoDireccion) throws ApplicationException {
        boolean resultado = false;
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);

            if (direccionRREntity != null
                    && direccionRREntity.getCalle() != null
                    && !direccionRREntity.getCalle().trim().isEmpty()) {
                if (direccionRRManager.crearStreetHHPPRR(direccionRREntity.getComunidad(), direccionRREntity.getDivision(),
                        direccionRREntity.getCalle().toUpperCase())) {
                    if (direccionRRManager.crearCruceRR(direccionRREntity.getComunidad(), direccionRREntity.getDivision(), direccionRREntity.getCalle(),
                            direccionRREntity.getNumeroUnidad(), tipoDireccion)) {
                    }
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
       }
        return resultado;
    }

    public boolean edificioUpdate(CmtCuentaMatrizMgl cuentaMatriz, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoEdificios requestDataManttoEdificios = new RequestDataManttoEdificios();
            requestDataManttoEdificios.setCodigo(cuentaMatriz.getNumeroCuenta().toString());

            requestDataManttoEdificios.setCodigoDivision(cuentaMatriz.getDivision());
            requestDataManttoEdificios.setCodigoComunidad(cuentaMatriz.getComunidad());
            requestDataManttoEdificios.setDescripcion(cuentaMatriz.getNombreCuenta());

            //buscamos el barrio en RR
            String Barrio = "N.N";
            if (cuentaMatriz.getDireccionPrincipal().getBarrio() != null && !cuentaMatriz.getDireccionPrincipal().getBarrio().isEmpty()) {
                Barrio = cuentaMatriz.getDireccionPrincipal().getBarrio();
            }
            String codBarrioRr = "";
            ResponseManttoInformacionBarriosList responseBarrios = null;
            if (Barrio != null
                    && !Barrio.isEmpty()) {
                responseBarrios = barrioConsulta(cuentaMatriz, usuario, Barrio);
            }
            if (responseBarrios != null
                    && responseBarrios.getListManttoBarrios() != null
                    && !responseBarrios.getListManttoBarrios().isEmpty()) {
                for (ResponseDataManttoBarrios barrio : responseBarrios.getListManttoBarrios()) {
                    if (barrio.getDescripcion().equalsIgnoreCase(Barrio)) {
                        codBarrioRr = barrio.getCodigo();
                        break;
                    }
                }
            }
            //si no se encontro el barrio se debe crear sobre RR
            if (codBarrioRr == null || codBarrioRr.isEmpty()) {
                ResponseDataManttoBarrios barrioCreado = barrioAdd(Barrio,
                        cuentaMatriz.getDivision(),
                        cuentaMatriz.getComunidad(), usuario);
                if (barrioCreado != null) {
                    codBarrioRr = barrioCreado.getCodigo();
                }
            }
            requestDataManttoEdificios.setCodigoBarrio(codBarrioRr);
            requestDataManttoEdificios.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEdificiosList responseManttoEdificiosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_EDIFICIOS_UPDATE,
                    ResponseManttoEdificiosList.class,
                    requestDataManttoEdificios);
            if (responseManttoEdificiosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)-ws" + ex.getMessage());
        }
        return true;
    }

    public boolean edificioDelete(CmtCuentaMatrizMgl cuentaMatrizMgl, String usuario) throws ApplicationException {
        try {
            
            String nameUser = StringUtils.retornaNameUser(usuario);
            
            RequestDataManttoEdificios requestDataManttoEdificios = new RequestDataManttoEdificios();
            requestDataManttoEdificios.setCodigo(cuentaMatrizMgl.getNumeroCuenta().toString());
            requestDataManttoEdificios.setDescripcion(cuentaMatrizMgl.getNombreCuenta());
            requestDataManttoEdificios.setNombreUsuario(nameUser);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEdificiosList responseManttoEdificiosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_EDIFICIOS_DELETE,
                    ResponseManttoEdificiosList.class,
                    requestDataManttoEdificios);
            if (responseManttoEdificiosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Mantenimiento Barrio, Nombre tipo: Barrio
     */
    private ResponseManttoInformacionBarriosList barrioConsulta(
            CmtCuentaMatrizMgl cuentaMatriz, String usuarioOriginal, String barrio) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
             
            RequestDataManttoInformacionBarrios requestDataManttoInformacionBarrios = new RequestDataManttoInformacionBarrios();

            requestDataManttoInformacionBarrios.setNombreBarrioB(barrio);
            requestDataManttoInformacionBarrios.setCodigoDivisionB(cuentaMatriz.getDivision());
            requestDataManttoInformacionBarrios.setCodigoComunidadB(cuentaMatriz.getComunidad());
            requestDataManttoInformacionBarrios.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInformacionBarriosList responseManttoInformacionBarriosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_BARRIOS_QUERY,
                    ResponseManttoInformacionBarriosList.class,
                    requestDataManttoInformacionBarrios);
            if (responseManttoInformacionBarriosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                if (!responseManttoInformacionBarriosList.getMensaje().
                        contains("No hay datos para para la consulta")) {
                    throw new ApplicationException("(As400)" + responseManttoInformacionBarriosList.getMensaje());
                }
            }
            return responseManttoInformacionBarriosList;
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
    }

    public ResponseDataManttoBarrios barrioAdd(String nombreBarrio, String division, String comunidad, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoInformacionBarrios requestDataManttoInformacionBarrios = new RequestDataManttoInformacionBarrios();

            requestDataManttoInformacionBarrios.setDescripcion(nombreBarrio.toUpperCase());
            if (division != null) {
                requestDataManttoInformacionBarrios.setCodigoDivision(division.toUpperCase());
            }
            if (comunidad != null) {
                requestDataManttoInformacionBarrios.setCodigoComunidad(comunidad.toUpperCase());
            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataManttoInformacionBarrios.setNombreUsuario(usuario);
            ResponseManttoInformacionBarriosList responseManttoInformacionBarriosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_BARRIOS_ADD,
                    ResponseManttoInformacionBarriosList.class,
                    requestDataManttoInformacionBarrios);
            if (responseManttoInformacionBarriosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInformacionBarriosList.getMensaje());
            }
            if (responseManttoInformacionBarriosList.getListManttoBarrios() != null
                    && !responseManttoInformacionBarriosList.getListManttoBarrios().isEmpty()) {
                return responseManttoInformacionBarriosList.getListManttoBarrios().get(0);
            } else {
                return null;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
    }

    private boolean barrioUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoInformacionBarrios requestDataManttoInformacionBarrios = new RequestDataManttoInformacionBarrios();
            requestDataManttoInformacionBarrios.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoInformacionBarrios.setDescripcion(cmtBasicaMgl.getDescripcion());
            requestDataManttoInformacionBarrios.setCodigoDivision("");
            requestDataManttoInformacionBarrios.setNombreDivision("");
            requestDataManttoInformacionBarrios.setCodigoComunidad("");
            requestDataManttoInformacionBarrios.setNombreComunidad("");
            requestDataManttoInformacionBarrios.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInformacionBarriosList responseManttoInformacionBarriosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_BARRIOS_UPDATE,
                    ResponseManttoInformacionBarriosList.class,
                    requestDataManttoInformacionBarrios);
            if (responseManttoInformacionBarriosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInformacionBarriosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean barrioDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoInformacionBarrios requestDataManttoInformacionBarrios = new RequestDataManttoInformacionBarrios();
            requestDataManttoInformacionBarrios.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoInformacionBarrios.setDescripcion(cmtBasicaMgl.getDescripcion());
            requestDataManttoInformacionBarrios.setCodigoDivision("");
            requestDataManttoInformacionBarrios.setNombreDivision("");
            requestDataManttoInformacionBarrios.setCodigoComunidad("");
            requestDataManttoInformacionBarrios.setNombreComunidad("");
            requestDataManttoInformacionBarrios.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInformacionBarriosList responseManttoInformacionBarriosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_BARRIOS_DELETE,
                    ResponseManttoInformacionBarriosList.class,
                    requestDataManttoInformacionBarrios);
            if (responseManttoInformacionBarriosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInformacionBarriosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Estados Edificio, Nombre tipo: Estados Cuenta Matriz
     */
    private boolean estadoCuentaMatrizAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
         
            RequestDataManttoEstadoEdificio requestDataManttoEstadoEdificio = new RequestDataManttoEstadoEdificio();
            requestDataManttoEstadoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadoEdificio.setDescripcion(cmtBasicaMgl.getNombreBasica());

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setEstado")) {
                    requestDataManttoEstadoEdificio.setEstado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setExcepcionRadiografia")) {
                    requestDataManttoEstadoEdificio.setExcepcionRadiografia(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setValidarEstado")) {
                    requestDataManttoEstadoEdificio.setValidarEstado(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataManttoEstadoEdificio.setNombreUsuario(usuario);  
              
            ResponseManttoEstadoEdificioList responseManttoEstadoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADO_EDIFICIO_ADD,
                    ResponseManttoEstadoEdificioList.class,
                    requestDataManttoEstadoEdificio);
            if (responseManttoEstadoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400-ws)" + responseManttoEstadoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Estados Edificio, Nombre tipo: Estados Cuenta Matriz
     */
    private ResponseManttoEstadoEdificioList estadoCuentaMatrizQuery() throws ApplicationException {
        ResponseManttoEstadoEdificioList responseManttoEstadoEdificioList = null;
        try {
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            responseManttoEstadoEdificioList = restClientTablasBasicas.callWebServiceMethodGet(
                    EnumeratorServiceName.TB_MANTTO_ESTADO_EDIFICIO_QUERY,
                    ResponseManttoEstadoEdificioList.class);
            if (responseManttoEstadoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400-ws)" + responseManttoEstadoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
        return responseManttoEstadoEdificioList;
    }

    private ResponseDataManttoEstadoEdificio estadoCuentaMatrizQueryByCode(String code) throws ApplicationException {
        ResponseDataManttoEstadoEdificio resutlEstado = null;
        try {
            ResponseManttoEstadoEdificioList listEstados = estadoCuentaMatrizQuery();
            for (ResponseDataManttoEstadoEdificio e : listEstados.getListEstadoEdificio()) {
                String codEstadoRr = e.getCodigo();
                while (codEstadoRr.startsWith("0")) {
                    codEstadoRr = codEstadoRr.substring(1);
                }
                if (!codEstadoRr.trim().isEmpty()) {
                    if (codEstadoRr.equalsIgnoreCase(code)) {
                        resutlEstado = e;
                        break;
                    }
                }
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
        return resutlEstado;
    }

    private boolean estadoCuentaMatrizUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);

            RequestDataManttoEstadoEdificio requestDataManttoEstadoEdificio = new RequestDataManttoEstadoEdificio();
            requestDataManttoEstadoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadoEdificio.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoEstadoEdificio.setNombreUsuario(usuario);
            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setEstado")) {
                    requestDataManttoEstadoEdificio.setEstado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setExcepcionRadiografia")) {
                    requestDataManttoEstadoEdificio.setExcepcionRadiografia(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setValidarEstado")) {
                    requestDataManttoEstadoEdificio.setValidarEstado(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstadoEdificioList responseManttoEstadoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADO_EDIFICIO_UPDATE,
                    ResponseManttoEstadoEdificioList.class,
                    requestDataManttoEstadoEdificio);
            if (responseManttoEstadoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstadoEdificioList.getMensaje());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estadoCuentaMatrizDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoEstadoEdificio requestDataManttoEstadoEdificio = new RequestDataManttoEstadoEdificio();
            requestDataManttoEstadoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadoEdificio.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoEstadoEdificio.setEstado("N");
            requestDataManttoEstadoEdificio.setExcepcionRadiografia("N");
            requestDataManttoEstadoEdificio.setValidarEstado("N");
            requestDataManttoEstadoEdificio.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstadoEdificioList responseManttoEstadoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADO_EDIFICIO_DELETE,
                    ResponseManttoEstadoEdificioList.class,
                    requestDataManttoEstadoEdificio);
            if (responseManttoEstadoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstadoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo Edificio, Nombre Tipo: Tipo Cuenta Matriz
     */
    private boolean tipoDeEdificioAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoTipoEdificio requestDataManttoTipoEdificio = new RequestDataManttoTipoEdificio();
            requestDataManttoTipoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoEdificio.setNombre(cmtBasicaMgl.getNombreBasica());
                        
            requestDataManttoTipoEdificio.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoEdificioList responseManttoTipoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_EDIFICIO_ADD,
                    ResponseManttoTipoEdificioList.class,
                    requestDataManttoTipoEdificio);
            if (responseManttoTipoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeEdificioUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);

            RequestDataManttoTipoEdificio requestDataManttoTipoEdificio = new RequestDataManttoTipoEdificio();
            requestDataManttoTipoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoEdificio.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoEdificio.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoEdificioList responseManttoTipoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_EDIFICIO_UPDATE,
                    ResponseManttoTipoEdificioList.class,
                    requestDataManttoTipoEdificio);
            if (responseManttoTipoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeEdificioDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);

            RequestDataManttoTipoEdificio requestDataManttoTipoEdificio = new RequestDataManttoTipoEdificio();
            requestDataManttoTipoEdificio.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoEdificio.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoEdificio.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoEdificioList responseManttoTipoEdificioList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_EDIFICIO_DELETE,
                    ResponseManttoTipoEdificioList.class,
                    requestDataManttoTipoEdificio);
            if (responseManttoTipoEdificioList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)-ws" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Competencia, Nombre Tipo: Competencia
     */
    public boolean competenciaAdd(
            CmtCompetenciaTipoMgl competencia, String usuarioOriginal)
            throws ApplicationException, ApplicationException, ApplicationException, ApplicationException, ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoCompetencia requestDataManttoCompetencia = new RequestDataManttoCompetencia();
            requestDataManttoCompetencia.setCodigo(competencia.getCodigoRr());
            requestDataManttoCompetencia.setNombre(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setTipo(
                    competencia.getServicioCompetencia().getCodigoBasica());
            requestDataManttoCompetencia.setDescripcion(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoCompetenciaList responseManttoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_COMPETENCIA_ADD,
                    ResponseManttoCompetenciaList.class,
                    requestDataManttoCompetencia);
            if (responseManttoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)"
                        + responseManttoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    public boolean competenciaUpdate(CmtCompetenciaTipoMgl competencia, String usuarioOriginal)
            throws ApplicationException, ApplicationException, ApplicationException, ApplicationException, ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoCompetencia requestDataManttoCompetencia = new RequestDataManttoCompetencia();
            requestDataManttoCompetencia.setCodigo(competencia.getCodigoRr());
            requestDataManttoCompetencia.setNombre(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setTipo(
                    competencia.getServicioCompetencia().getCodigoBasica());
            requestDataManttoCompetencia.setDescripcion(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setNombreUsuario(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoCompetenciaList responseManttoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_COMPETENCIA_UPDATE,
                    ResponseManttoCompetenciaList.class,
                    requestDataManttoCompetencia);
            if (responseManttoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)"
                        + responseManttoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    public boolean competenciaDelete(CmtCompetenciaTipoMgl competencia, String usuarioOriginal)
            throws ApplicationException, ApplicationException, ApplicationException, ApplicationException, ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoCompetencia requestDataManttoCompetencia = new RequestDataManttoCompetencia();
            requestDataManttoCompetencia.setCodigo(competencia.getCodigoRr());
            requestDataManttoCompetencia.setNombre(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setTipo(
                    competencia.getServicioCompetencia().getCodigoBasica());
            requestDataManttoCompetencia.setDescripcion(
                    competencia.getProveedorCompetencia().getNombreBasica());
            requestDataManttoCompetencia.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoCompetenciaList responseManttoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_COMPETENCIA_DELETE,
                    ResponseManttoCompetenciaList.class,
                    requestDataManttoCompetencia);
            if (responseManttoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)"
                        + responseManttoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)-ws" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo de Competencia, Nombre Tipo: Tipo de Competencia
     */
    private boolean tipoCompetenciaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
                   
            RequestDataManttoTipoCompetencia requestDataManttoTipoCompetencia = new RequestDataManttoTipoCompetencia();
            requestDataManttoTipoCompetencia.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoCompetencia.setDescripcion(cmtBasicaMgl.getNombreBasica());
            
            requestDataManttoTipoCompetencia.setNombreUsuario(usuario);
            
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoCompetenciaList responseManttoTipoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_COMPETENCIA_ADD,
                    ResponseManttoTipoCompetenciaList.class,
                    requestDataManttoTipoCompetencia);
            if (responseManttoTipoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoCompetenciaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            
            RequestDataManttoTipoCompetencia requestDataManttoTipoCompetencia = new RequestDataManttoTipoCompetencia();
            requestDataManttoTipoCompetencia.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoCompetencia.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoCompetencia.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoCompetenciaList responseManttoTipoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_COMPETENCIA_UPDATE,
                    ResponseManttoTipoCompetenciaList.class,
                    requestDataManttoTipoCompetencia);
            if (responseManttoTipoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoCompetenciaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            
            RequestDataManttoTipoCompetencia requestDataManttoTipoCompetencia = new RequestDataManttoTipoCompetencia();
            requestDataManttoTipoCompetencia.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoCompetencia.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoCompetencia.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoCompetenciaList responseManttoTipoCompetenciaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_COMPETENCIA_DELETE,
                    ResponseManttoTipoCompetenciaList.class,
                    requestDataManttoTipoCompetencia);
            if (responseManttoTipoCompetenciaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoCompetenciaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo de Trabajo, Nombre Tipo: Tipo de Trabajo
     */
    private boolean tipoDeTrabajoAdd(CmtBasicaMgl cmtBasicaMgl, String usuario) throws ApplicationException {
        try {
            RequestDataTipoTrabajo requestDataTipoTrabajo = new RequestDataTipoTrabajo();
            requestDataTipoTrabajo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoTrabajo.setNombre(cmtBasicaMgl.getNombreBasica());
            
            String userName = StringUtils.retornaNameUser(usuario);

            requestDataTipoTrabajo.setUserId(userName); 
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setNotasTrabajo")) {
                    requestDataTipoTrabajo.setNotasTrabajo(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCostoPresupuesto")) {
                    requestDataTipoTrabajo.setCostoPresupuesto(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCostoReal")) {
                    requestDataTipoTrabajo.setCostoReal(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAsignacionTecnico")) {
                    requestDataTipoTrabajo.setAsignacionTecnico(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setRazonArreglo")) {
                    requestDataTipoTrabajo.setRazonArreglo(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAsignacionInventario")) {
                    requestDataTipoTrabajo.setAsignacionInventario(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setDesvinculaInventario")) {
                    requestDataTipoTrabajo.setDesvinculaInventario(basicaExtMgl.getValorExtendido());
                }
            }

            ResponseManttoTipoTrabajoList responseManttoTipoTrabajoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_TRABAJO_ADD,
                    ResponseManttoTipoTrabajoList.class,
                    requestDataTipoTrabajo);
            if (responseManttoTipoTrabajoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoTrabajoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage(), ex);
        }
        return true;
    }

    private boolean tipoDeTrabajoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoTrabajo requestDataTipoTrabajo = new RequestDataTipoTrabajo();
            requestDataTipoTrabajo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoTrabajo.setNombre(cmtBasicaMgl.getNombreBasica());

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setNotasTrabajo")) {
                    requestDataTipoTrabajo.setNotasTrabajo(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCostoPresupuesto")) {
                    requestDataTipoTrabajo.setCostoPresupuesto(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCostoReal")) {
                    requestDataTipoTrabajo.setCostoReal(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAsignacionTecnico")) {
                    requestDataTipoTrabajo.setAsignacionTecnico(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setRazonArreglo")) {
                    requestDataTipoTrabajo.setRazonArreglo(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAsignacionInventario")) {
                    requestDataTipoTrabajo.setAsignacionInventario(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setDesvinculaInventario")) {
                    requestDataTipoTrabajo.setDesvinculaInventario(basicaExtMgl.getValorExtendido());
                }

            }
            requestDataTipoTrabajo.setUserId(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoTrabajoList responseManttoTipoTrabajoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_TRABAJO_UPDATE,
                    ResponseManttoTipoTrabajoList.class,
                    requestDataTipoTrabajo);
            if (responseManttoTipoTrabajoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoTrabajoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeTrabajoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);

            RequestDataTipoTrabajo requestDataTipoTrabajo = new RequestDataTipoTrabajo();
            requestDataTipoTrabajo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoTrabajo.setAsignacionInventario("N");
            requestDataTipoTrabajo.setAsignacionTecnico("N");
            requestDataTipoTrabajo.setCostoPresupuesto("N");
            requestDataTipoTrabajo.setDesvinculaInventario("N");
            requestDataTipoTrabajo.setNotasTrabajo("N");
            requestDataTipoTrabajo.setRazonArreglo("N");
            requestDataTipoTrabajo.setUserId(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoTrabajoList responseManttoTipoTrabajoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_TRABAJO_DELETE,
                    ResponseManttoTipoTrabajoList.class,
                    requestDataTipoTrabajo);
            if (responseManttoTipoTrabajoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoTrabajoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo de Notas, Nombre Tipo: Tipo de Notas
     */
    private boolean tipoDeNotasAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
 
            RequestDataManttoTipoNotas requestDataManttoTipoNotas = new RequestDataManttoTipoNotas();
            requestDataManttoTipoNotas.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoNotas.setNombre(cmtBasicaMgl.getNombreBasica());

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setPuntoInicial")) {
                    requestDataManttoTipoNotas.setPuntoInicial(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setTipoAcometida")) {
                    requestDataManttoTipoNotas.setTipoAcometida(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAlimentacionElectrica")) {
                    requestDataManttoTipoNotas.setAlimentacionElectrica(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setTipoDistribucion")) {
                    requestDataManttoTipoNotas.setTipoDistribucion(basicaExtMgl.getValorExtendido());
                }

                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setUbicacionCaja")) {
                    requestDataManttoTipoNotas.setUbicacionCaja(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataManttoTipoNotas.setNombreUsuario(usuario);
                 
            ResponseManttoTipoNotasList responseManttoTipoNotasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_NOTAS_ADD,
                    ResponseManttoTipoNotasList.class,
                    requestDataManttoTipoNotas);
            if (responseManttoTipoNotasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoNotasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeNotasUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
          
            RequestDataManttoTipoNotas requestDataManttoTipoNotas = new RequestDataManttoTipoNotas();
            requestDataManttoTipoNotas.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoNotas.setNombre(cmtBasicaMgl.getNombreBasica());

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setAlimentacionElectrica")) {
                    requestDataManttoTipoNotas.setAlimentacionElectrica(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setPuntoInicial")) {
                    requestDataManttoTipoNotas.setPuntoInicial(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setTipoAcometida")) {
                    requestDataManttoTipoNotas.setTipoAcometida(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setTipoDistribucion")) {
                    requestDataManttoTipoNotas.setTipoDistribucion(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setUbicacionCaja")) {
                    requestDataManttoTipoNotas.setUbicacionCaja(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataManttoTipoNotas.setNombreUsuario(usuario);
            ResponseManttoTipoNotasList responseManttoTipoNotasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_NOTAS_UPDATE,
                    ResponseManttoTipoNotasList.class,
                    requestDataManttoTipoNotas);
            if (responseManttoTipoNotasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoNotasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeNotasDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoTipoNotas requestDataManttoTipoNotas = new RequestDataManttoTipoNotas();
            requestDataManttoTipoNotas.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoNotas.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoNotas.setAlimentacionElectrica("N");
            requestDataManttoTipoNotas.setPuntoInicial("N");
            requestDataManttoTipoNotas.setTipoAcometida("N");
            requestDataManttoTipoNotas.setTipoDistribucion("N");
            requestDataManttoTipoNotas.setUbicacionCaja("N");
            requestDataManttoTipoNotas.setNombreUsuario(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoNotasList responseManttoTipoNotasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_NOTAS_DELETE,
                    ResponseManttoTipoNotasList.class,
                    requestDataManttoTipoNotas);
            if (responseManttoTipoNotasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoNotasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo Distribucion Interna, Nombre Tipo: Tipo Distribucion
     */
    private boolean tipoDistribucionInternaAdd(CmtBasicaMgl cmtBasicaMgl, String usuario) throws ApplicationException {
        try {
            RequestDataManttoTipoDistribucionRedInterna requestDataManttoTipoDistribucionRedInterna = new RequestDataManttoTipoDistribucionRedInterna();
            requestDataManttoTipoDistribucionRedInterna.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoDistribucionRedInterna.setNombre(cmtBasicaMgl.getNombreBasica());
            
            String userName = StringUtils.retornaNameUser(usuario);
            
            requestDataManttoTipoDistribucionRedInterna.setNombreUsuario(userName);
                               
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoDistribucionRedInternaList responseManttoTipoDistribucionRedInternaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_DISTRIBUCION_RED_INTERNA_ADD,
                    ResponseManttoTipoDistribucionRedInternaList.class,
                    requestDataManttoTipoDistribucionRedInterna);
            if (responseManttoTipoDistribucionRedInternaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoDistribucionRedInternaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDistribucionInternaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoTipoDistribucionRedInterna requestDataManttoTipoDistribucionRedInterna = new RequestDataManttoTipoDistribucionRedInterna();
            requestDataManttoTipoDistribucionRedInterna.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoDistribucionRedInterna.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoDistribucionRedInterna.setNombreUsuario(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoDistribucionRedInternaList responseManttoTipoDistribucionRedInternaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_DISTRIBUCION_RED_INTERNA_UPDATE,
                    ResponseManttoTipoDistribucionRedInternaList.class,
                    requestDataManttoTipoDistribucionRedInterna);
            if (responseManttoTipoDistribucionRedInternaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoDistribucionRedInternaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDistribucionInternaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoTipoDistribucionRedInterna requestDataManttoTipoDistribucionRedInterna = new RequestDataManttoTipoDistribucionRedInterna();
            requestDataManttoTipoDistribucionRedInterna.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoTipoDistribucionRedInterna.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoTipoDistribucionRedInterna.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoTipoDistribucionRedInternaList responseManttoTipoDistribucionRedInternaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_DISTRIBUCION_RED_INTERNA_DELETE,
                    ResponseManttoTipoDistribucionRedInternaList.class,
                    requestDataManttoTipoDistribucionRedInterna);
            if (responseManttoTipoDistribucionRedInternaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoTipoDistribucionRedInternaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Ubicación Caja, Nombre Tipo: Ubicacion Equipos
     */
    private boolean ubicacionCajaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataUbicacionCaja requestDataUbicacionCaja = new RequestDataUbicacionCaja();
            requestDataUbicacionCaja.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataUbicacionCaja.setNombre(cmtBasicaMgl.getNombreBasica());
                        
            requestDataUbicacionCaja.setUserId(usuario);
             
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoUbicacionCajaList responseManttoUbicacionCajaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_UBICACION_CAJA_ADD,
                    ResponseManttoUbicacionCajaList.class,
                    requestDataUbicacionCaja);
            if (responseManttoUbicacionCajaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoUbicacionCajaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean ubicacionCajaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
                  
            RequestDataUbicacionCaja requestDataUbicacionCaja = new RequestDataUbicacionCaja();
            requestDataUbicacionCaja.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataUbicacionCaja.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataUbicacionCaja.setUserId(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoUbicacionCajaList responseManttoUbicacionCajaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_UBICACION_CAJA_UPDATE,
                    ResponseManttoUbicacionCajaList.class,
                    requestDataUbicacionCaja);
            if (responseManttoUbicacionCajaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoUbicacionCajaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean ubicacionCajaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataUbicacionCaja requestDataUbicacionCaja = new RequestDataUbicacionCaja();
            requestDataUbicacionCaja.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataUbicacionCaja.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataUbicacionCaja.setUserId(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoUbicacionCajaList responseManttoUbicacionCajaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_UBICACION_CAJA_DELETE,
                    ResponseManttoUbicacionCajaList.class,
                    requestDataUbicacionCaja);
            if (responseManttoUbicacionCajaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoUbicacionCajaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {           
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();           
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Punto Inicial, Nombre Tipo: Punto Inicial
     */
    private boolean puntoInicialAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoPuntoInicial requestDataManttoPuntoInicial = new RequestDataManttoPuntoInicial();
            requestDataManttoPuntoInicial.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoPuntoInicial.setNombre(cmtBasicaMgl.getNombreBasica());
           

            requestDataManttoPuntoInicial.setNombreUsuario(usuario);
            

             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoPuntoInicialList responseManttoPuntoInicialList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_PUNTO_INICIAL_ADD,
                    ResponseManttoPuntoInicialList.class,
                    requestDataManttoPuntoInicial);
            if (responseManttoPuntoInicialList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoPuntoInicialList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean puntoInicialUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoPuntoInicial requestDataManttoPuntoInicial = new RequestDataManttoPuntoInicial();
            requestDataManttoPuntoInicial.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoPuntoInicial.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoPuntoInicial.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoPuntoInicialList responseManttoPuntoInicialList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_PUNTO_INICIAL_UPDATE,
                    ResponseManttoPuntoInicialList.class,
                    requestDataManttoPuntoInicial);
            if (responseManttoPuntoInicialList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoPuntoInicialList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean puntoInicialDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoPuntoInicial requestDataManttoPuntoInicial = new RequestDataManttoPuntoInicial();
            requestDataManttoPuntoInicial.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoPuntoInicial.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoPuntoInicial.setNombre(usuario);
            requestDataManttoPuntoInicial.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoPuntoInicialList responseManttoPuntoInicialList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_PUNTO_INICIAL_DELETE,
                    ResponseManttoPuntoInicialList.class,
                    requestDataManttoPuntoInicial);
            if (responseManttoPuntoInicialList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoPuntoInicialList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400+ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo Acometica, Nombre Tipo: Tipo Acometida
     */
    private boolean tipoAcometidaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoAcometida requestDataTipoAcometida = new RequestDataTipoAcometida();
            requestDataTipoAcometida.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoAcometida.setNombre(cmtBasicaMgl.getNombreBasica());
                       
            requestDataTipoAcometida.setNombreUsuario(usuario);
                  
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoAcometidaList responseTipoAcometidaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_ACOMETIDA_ADD,
                    ResponseTipoAcometidaList.class,
                    requestDataTipoAcometida);
            if (responseTipoAcometidaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoAcometidaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400+ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoAcometidaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoAcometida requestDataTipoAcometida = new RequestDataTipoAcometida();
            requestDataTipoAcometida.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoAcometida.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoAcometida.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoAcometidaList responseTipoAcometidaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_ACOMETIDA_UPDATE,
                    ResponseTipoAcometidaList.class,
                    requestDataTipoAcometida);
            if (responseTipoAcometidaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoAcometidaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoAcometidaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoAcometida requestDataTipoAcometida = new RequestDataTipoAcometida();
            requestDataTipoAcometida.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoAcometida.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoAcometida.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoAcometidaList responseTipoAcometidaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_ACOMETIDA_DELETE,
                    ResponseTipoAcometidaList.class,
                    requestDataTipoAcometida);
            if (responseTipoAcometidaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoAcometidaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Alimentacion Elec, Nombre Tipo: Tipo de Alimnetacion Electrica
     */
    private boolean tipoDeAlimentacionElectricaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAlimentacionElectrica requestDataAlimentacionElectrica = new RequestDataAlimentacionElectrica();
            requestDataAlimentacionElectrica.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataAlimentacionElectrica.setDescripcion(cmtBasicaMgl.getNombreBasica());
            
            requestDataAlimentacionElectrica.setNombreUsuario(usuario);
                                  
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAlimentacionElectList responseManttoAlimentacionElectList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ALIMENTACION_ELECT_ADD,
                    ResponseManttoAlimentacionElectList.class,
                    requestDataAlimentacionElectrica);
            if (responseManttoAlimentacionElectList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAlimentacionElectList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeAlimentacionElectricaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAlimentacionElectrica requestDataAlimentacionElectrica = new RequestDataAlimentacionElectrica();
            requestDataAlimentacionElectrica.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataAlimentacionElectrica.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataAlimentacionElectrica.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAlimentacionElectList responseManttoAlimentacionElectList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ALIMENTACION_ELECT_UPDATE,
                    ResponseManttoAlimentacionElectList.class,
                    requestDataAlimentacionElectrica);
            if (responseManttoAlimentacionElectList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAlimentacionElectList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();           LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeAlimentacionElectricaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAlimentacionElectrica requestDataAlimentacionElectrica = new RequestDataAlimentacionElectrica();
            requestDataAlimentacionElectrica.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataAlimentacionElectrica.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataAlimentacionElectrica.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAlimentacionElectList responseManttoAlimentacionElectList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ALIMENTACION_ELECT_DELETE,
                    ResponseManttoAlimentacionElectList.class,
                    requestDataAlimentacionElectrica);
            if (responseManttoAlimentacionElectList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAlimentacionElectList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Causas de Mantenimiento, Nombre Tipo: Causas de Mantenimiento
     */
    private boolean causasDeMantenimientoAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataRazonArreglo requestDataRazonArreglo = new RequestDataRazonArreglo();
            requestDataRazonArreglo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataRazonArreglo.setNombre(cmtBasicaMgl.getNombreBasica());
            
            requestDataRazonArreglo.setUserId(usuario);
                               
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseRazonArregloList responseRazonArregloList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_RAZON_ARREGLO_ADD,
                    ResponseRazonArregloList.class,
                    requestDataRazonArreglo);
            if (responseRazonArregloList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseRazonArregloList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean causasDeMantenimientoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataRazonArreglo requestDataRazonArreglo = new RequestDataRazonArreglo();
            requestDataRazonArreglo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataRazonArreglo.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataRazonArreglo.setUserId(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseRazonArregloList responseRazonArregloList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_RAZON_ARREGLO_UPDATE,
                    ResponseRazonArregloList.class,
                    requestDataRazonArreglo);
            if (responseRazonArregloList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseRazonArregloList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400)-ws" + ex.getMessage());
        }
        return true;
    }

    private boolean causasDeMantenimientoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataRazonArreglo requestDataRazonArreglo = new RequestDataRazonArreglo();
            requestDataRazonArreglo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataRazonArreglo.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataRazonArreglo.setUserId(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseRazonArregloList responseRazonArregloList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_RAZON_ARREGLO_DELETE,
                    ResponseRazonArregloList.class,
                    requestDataRazonArreglo);
            if (responseRazonArregloList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseRazonArregloList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Estado Resultado OT, Nombre Tipo: Estado Interno
     */
    private boolean estadoResultadoOtAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataEstadoResultadoOt requestDataEstadoResultadoOt = new RequestDataEstadoResultadoOt();
            requestDataEstadoResultadoOt.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstadoResultadoOt.setNombre(cmtBasicaMgl.getNombreBasica());
            
            requestDataEstadoResultadoOt.setNombreUsuario(usuario);
                       
            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setProgramado")) {
                    requestDataEstadoResultadoOt.setProgramado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setEnCurso")) {
                    requestDataEstadoResultadoOt.setEnCurso(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setRealizado")) {
                    requestDataEstadoResultadoOt.setRealizado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCancelado")) {
                    requestDataEstadoResultadoOt.setCancelado(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataEstadoResultadoOt.setEstadoCodigo("A");
            ResponseEstadoResultadoOtList responseEstadoResultadoOtList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_ESTADO_RESULTADO_OT_ADD,
                    ResponseEstadoResultadoOtList.class,
                    requestDataEstadoResultadoOt);
            if (responseEstadoResultadoOtList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseEstadoResultadoOtList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estadoResultadoOtUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
                       
            RequestDataEstadoResultadoOt requestDataEstadoResultadoOt = new RequestDataEstadoResultadoOt();
            requestDataEstadoResultadoOt.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstadoResultadoOt.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstadoResultadoOt.setNombreUsuario(usuario);

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setProgramado")) {
                    requestDataEstadoResultadoOt.setProgramado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setEnCurso")) {
                    requestDataEstadoResultadoOt.setEnCurso(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setRealizado")) {
                    requestDataEstadoResultadoOt.setRealizado(basicaExtMgl.getValorExtendido());
                }
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setCancelado")) {
                    requestDataEstadoResultadoOt.setCancelado(basicaExtMgl.getValorExtendido());
                }

            }
            
              parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataEstadoResultadoOt.setEstadoCodigo("A");
            ResponseEstadoResultadoOtList responseEstadoResultadoOtList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_ESTADO_RESULTADO_OT_UPDATE,
                    ResponseEstadoResultadoOtList.class,
                    requestDataEstadoResultadoOt);
            if (responseEstadoResultadoOtList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseEstadoResultadoOtList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estadoResultadoOtDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataEstadoResultadoOt requestDataEstadoResultadoOt = new RequestDataEstadoResultadoOt();
            requestDataEstadoResultadoOt.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstadoResultadoOt.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstadoResultadoOt.setNombreUsuario(usuario);
            requestDataEstadoResultadoOt.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstadoResultadoOt.setProgramado("N");
            requestDataEstadoResultadoOt.setEnCurso("N");
            requestDataEstadoResultadoOt.setRealizado("N");
            requestDataEstadoResultadoOt.setCancelado("N");
            requestDataEstadoResultadoOt.setEstadoCodigo("A");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseEstadoResultadoOtList responseEstadoResultadoOtList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_ESTADO_RESULTADO_OT_DELETE,
                    ResponseEstadoResultadoOtList.class,
                    requestDataEstadoResultadoOt);
            if (responseEstadoResultadoOtList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseEstadoResultadoOtList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Materiales, Nombre Tipo: Materiales
     */
    private boolean materialesAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataManttoMateriales requestDataManttoMateriales = new RequestDataManttoMateriales();
            requestDataManttoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMateriales.setNombreUsuarios(usuario);
            requestDataManttoMateriales.setTipo("");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialesList responseManttoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIALES_ADD,
                    ResponseManttoMaterialesList.class,
                    requestDataManttoMateriales);
            if (responseManttoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean materialesUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoMateriales requestDataManttoMateriales = new RequestDataManttoMateriales();
            requestDataManttoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMateriales.setTipo("");
            requestDataManttoMateriales.setNombreUsuarios(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialesList responseManttoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIALES_UPDATE,
                    ResponseManttoMaterialesList.class,
                    requestDataManttoMateriales);
            if (responseManttoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean materialesDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoMateriales requestDataManttoMateriales = new RequestDataManttoMateriales();
            requestDataManttoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMateriales.setNombreUsuarios(usuario);
            requestDataManttoMateriales.setTipo("");
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialesList responseManttoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIALES_DELETE,
                    ResponseManttoMaterialesList.class,
                    requestDataManttoMateriales);
            if (responseManttoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Materiales por Proveedor, Nombre Tipo: Materiales por Proveedor
     */
    private boolean materialesPorProveedorAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoMaterialProveedor requestDataManttoMaterialProveedor = new RequestDataManttoMaterialProveedor();
            requestDataManttoMaterialProveedor.setCodigoProveedor(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMaterialProveedor.setNombreProveedor(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMaterialProveedor.setCodigoMaterial("");
            requestDataManttoMaterialProveedor.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialProveedorList responseManttoMaterialProveedorList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIAL_PROVEEDOR_ADD,
                    ResponseManttoMaterialProveedorList.class,
                    requestDataManttoMaterialProveedor);
            if (responseManttoMaterialProveedorList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialProveedorList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean materialesPorProveedorUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoMaterialProveedor requestDataManttoMaterialProveedor = new RequestDataManttoMaterialProveedor();
            requestDataManttoMaterialProveedor.setCodigoProveedor(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMaterialProveedor.setNombreProveedor(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMaterialProveedor.setCodigoMaterial("");
            requestDataManttoMaterialProveedor.setTipoMaterial("");
            requestDataManttoMaterialProveedor.setFecha("");
            requestDataManttoMaterialProveedor.setNombreProveedor(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialProveedorList responseManttoMaterialProveedorList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIAL_PROVEEDOR_UPDATE,
                    ResponseManttoMaterialProveedorList.class,
                    requestDataManttoMaterialProveedor);
            if (responseManttoMaterialProveedorList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialProveedorList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean materialesPorProveedorDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoMaterialProveedor requestDataManttoMaterialProveedor = new RequestDataManttoMaterialProveedor();
            requestDataManttoMaterialProveedor.setCodigoProveedor(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoMaterialProveedor.setNombreProveedor(cmtBasicaMgl.getNombreBasica());
            requestDataManttoMaterialProveedor.setCodigoMaterial("");
            requestDataManttoMaterialProveedor.setTipoMaterial("");
            requestDataManttoMaterialProveedor.setFecha("");
            requestDataManttoMaterialProveedor.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMaterialProveedorList responseManttoMaterialProveedorList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MATERIAL_PROVEEDOR_DELETE,
                    ResponseManttoMaterialProveedorList.class,
                    requestDataManttoMaterialProveedor);
            if (responseManttoMaterialProveedorList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMaterialProveedorList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tipo de Material, Nombre Tipo: Tipo de Material
     */
    private boolean tipoDeMaterialAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoMateriales requestDataTipoMateriales = new RequestDataTipoMateriales();
            requestDataTipoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());          
            
            
             requestDataTipoMateriales.setNombreUsuario(usuario);
                        
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoMaterialesList responseTipoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_MATERIALES_ADD,
                    ResponseTipoMaterialesList.class,
                    requestDataTipoMateriales);
            if (responseTipoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeMaterialUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataTipoMateriales requestDataTipoMateriales = new RequestDataTipoMateriales();
            requestDataTipoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoMateriales.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoMaterialesList responseTipoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_MATERIALES_UPDATE,
                    ResponseTipoMaterialesList.class,
                    requestDataTipoMateriales);
            if (responseTipoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeMaterialDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
                       
            RequestDataTipoMateriales requestDataTipoMateriales = new RequestDataTipoMateriales();
            requestDataTipoMateriales.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoMateriales.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoMateriales.setNombreUsuario(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoMaterialesList responseTipoMaterialesList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_MATERIALES_DELETE,
                    ResponseTipoMaterialesList.class,
                    requestDataTipoMateriales);
            if (responseTipoMaterialesList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoMaterialesList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            
        LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /* 
     * Tabla RR Black List Cuenta, Nombre Tipo: Black List CM
     */
    private boolean blackListCmAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCodigoBlackList requestDataCodigoBlackList = new RequestDataCodigoBlackList();
            requestDataCodigoBlackList.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataCodigoBlackList.setNombre(cmtBasicaMgl.getNombreBasica());
            

            requestDataCodigoBlackList.setNombreUsuario(usuario);
            
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCodigoBlackList responseCodigoBlackList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CODIGO_BLACK_LIST_ADD,
                    ResponseCodigoBlackList.class,
                    requestDataCodigoBlackList);
            if (responseCodigoBlackList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCodigoBlackList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean blackListCmUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCodigoBlackList requestDataCodigoBlackList = new RequestDataCodigoBlackList();
            requestDataCodigoBlackList.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataCodigoBlackList.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataCodigoBlackList.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCodigoBlackList responseCodigoBlackList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CODIGO_BLACK_LIST_UPDATE,
                    ResponseCodigoBlackList.class,
                    requestDataCodigoBlackList);
            if (responseCodigoBlackList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCodigoBlackList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean blackListCmDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCodigoBlackList requestDataCodigoBlackList = new RequestDataCodigoBlackList();
            requestDataCodigoBlackList.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataCodigoBlackList.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataCodigoBlackList.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCodigoBlackList responseCodigoBlackList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CODIGO_BLACK_LIST_DELETE,
                    ResponseCodigoBlackList.class,
                    requestDataCodigoBlackList);
            if (responseCodigoBlackList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCodigoBlackList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Origen De Datos, Nombre Tipo: Origen de Datos
     */
    private boolean origenDatosAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOrigenDatos requestDataOrigenDatos = new RequestDataOrigenDatos();
            requestDataOrigenDatos.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataOrigenDatos.setDescripcion(cmtBasicaMgl.getNombreBasica());
            

            requestDataOrigenDatos.setNombreUsuario(usuario);
            
                       
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseOrigenDatosList responseOrigenDatosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ORIGEN_DATOS_ADD,
                    ResponseOrigenDatosList.class,
                    requestDataOrigenDatos);
            if (responseOrigenDatosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOrigenDatosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean origenDatosUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOrigenDatos requestDataOrigenDatos = new RequestDataOrigenDatos();
            requestDataOrigenDatos.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataOrigenDatos.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataOrigenDatos.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseOrigenDatosList responseOrigenDatosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ORIGEN_DATOS_UPDATE,
                    ResponseOrigenDatosList.class,
                    requestDataOrigenDatos);
            if (responseOrigenDatosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOrigenDatosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean origenDatosDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOrigenDatos requestDataOrigenDatos = new RequestDataOrigenDatos();
            requestDataOrigenDatos.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataOrigenDatos.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataOrigenDatos.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseOrigenDatosList responseOrigenDatosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ORIGEN_DATOS_DELETE,
                    ResponseOrigenDatosList.class,
                    requestDataOrigenDatos);
            if (responseOrigenDatosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOrigenDatosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Tip Proyecto, Nombre Tipo: Tipo de Proyecto
     */
    private boolean tipoDeProyectoAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataTipoProyecto requestDataTipoProyecto = new RequestDataTipoProyecto();
            requestDataTipoProyecto.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoProyecto.setNombre(cmtBasicaMgl.getNombreBasica());
            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setIndicador")) {
                    requestDataTipoProyecto.setIndicador(basicaExtMgl.getValorExtendido());
                }

            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataTipoProyecto.setIndicador("N");
            
            requestDataTipoProyecto.setNombreUsuario(usuario);
                       
            ResponseTipoProyectoList responseTipoProyectoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_PROYECTO_ADD,
                    ResponseTipoProyectoList.class,
                    requestDataTipoProyecto);
            if (responseTipoProyectoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoProyectoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.
            getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeProyectoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoProyecto requestDataTipoProyecto = new RequestDataTipoProyecto();
            requestDataTipoProyecto.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoProyecto.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoProyecto.setNombreUsuario(usuario);

            for (CmtBasicaExtMgl basicaExtMgl : cmtBasicaMgl.getListCmtBasicaExtMgl()) {
                if (basicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().equalsIgnoreCase("setIndicador")) {
                    requestDataTipoProyecto.setIndicador(basicaExtMgl.getValorExtendido().contains("Y") ? "S" : "N");
                }
            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoProyectoList responseTipoProyectoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_PROYECTO_UPDATE,
                    ResponseTipoProyectoList.class,
                    requestDataTipoProyecto);
            if (responseTipoProyectoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoProyectoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean tipoDeProyectoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataTipoProyecto requestDataTipoProyecto = new RequestDataTipoProyecto();
            requestDataTipoProyecto.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataTipoProyecto.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataTipoProyecto.setIndicador("N");
            requestDataTipoProyecto.setNombreUsuario(usuario);
            
             parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseTipoProyectoList responseTipoProyectoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_TIPO_PROYECTO_DELETE,
                    ResponseTipoProyectoList.class,
                    requestDataTipoProyecto);
            if (responseTipoProyectoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseTipoProyectoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Estrato, Nombre Tipo: Estrato
     */
    private boolean estratoAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataEstrato requestDataEstrato = new RequestDataEstrato();
            requestDataEstrato.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstrato.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstrato.setUserId(usuario);

  
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstratoList responseManttoEstratoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTRATO_ADD,
                    ResponseManttoEstratoList.class,
                    requestDataEstrato);
            if (responseManttoEstratoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstratoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estratoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataEstrato requestDataEstrato = new RequestDataEstrato();
            requestDataEstrato.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstrato.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstrato.setUserId(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstratoList responseManttoEstratoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTRATO_UPDATE,
                    ResponseManttoEstratoList.class,
                    requestDataEstrato);
            if (responseManttoEstratoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstratoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estratoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataEstrato requestDataEstrato = new RequestDataEstrato();
            requestDataEstrato.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataEstrato.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataEstrato.setUserId(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstratoList responseManttoEstratoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTRATO_DELETE,
                    ResponseManttoEstratoList.class,
                    requestDataEstrato);
            if (responseManttoEstratoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstratoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Productos, Nombre Tipo: Producto
     */
    private boolean productoAdd(CmtBasicaMgl cmtBasicasMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProductos requestDataProductos = new RequestDataProductos();
            requestDataProductos.setCodigo(cmtBasicasMgl.getCodigoBasica());
            requestDataProductos.setNombre(cmtBasicasMgl.getNombreBasica());
            requestDataProductos.setNombreUsuario(usuario);
            
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProductosList responseProductosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PRODUCTOS_ADD,
                    ResponseProductosList.class,
                    requestDataProductos);
            if (responseProductosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProductosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean productoUpdate(CmtBasicaMgl cmtBasicasMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProductos requestDataProductos = new RequestDataProductos();
            requestDataProductos.setCodigo(cmtBasicasMgl.getCodigoBasica());
            requestDataProductos.setNombre(cmtBasicasMgl.getNombreBasica());
            requestDataProductos.setNombreUsuario(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProductosList responseProductosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PRODUCTOS_UPDATE,
                    ResponseProductosList.class,
                    requestDataProductos);
            if (responseProductosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProductosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean productoDelete(CmtBasicaMgl cmtBasicasMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProductos requestDataProductos = new RequestDataProductos();
            requestDataProductos.setCodigo(cmtBasicasMgl.getCodigoBasica());
            requestDataProductos.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProductosList responseProductosList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PRODUCTOS_DELETE,
                    ResponseProductosList.class,
                    requestDataProductos);
            if (responseProductosList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProductosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Información Nodo, Nombre Tipo: Información Nodo
     */
    private boolean informacionNodoAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoInfoNodo requestDataManttoInfoNodo = new RequestDataManttoInfoNodo();
            requestDataManttoInfoNodo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoInfoNodo.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoInfoNodo.setFechaApertura("2011/10/25");
            requestDataManttoInfoNodo.setCostoRed("133,00");
            requestDataManttoInfoNodo.setLimites("CON LIMITES");
            requestDataManttoInfoNodo.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_ADD,
                    ResponseManttoInfoNodoList.class,
                    requestDataManttoInfoNodo);
            if (responseManttoInfoNodoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean informacionNodoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoInfoNodo requestDataManttoInfoNodo = new RequestDataManttoInfoNodo();
            requestDataManttoInfoNodo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoInfoNodo.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoInfoNodo.setNombreUsuario(usuario);
            requestDataManttoInfoNodo.setFechaApertura("2011/10/25");
            requestDataManttoInfoNodo.setCostoRed("133,00");
            requestDataManttoInfoNodo.setLimites("CON LIMITES");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_UPDATE,
                    ResponseManttoInfoNodoList.class,
                    requestDataManttoInfoNodo);
            if (responseManttoInfoNodoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + "'"+ClassUtils.getCurrentMethodName(this.getClass())+"': " +
                    ex.getMessage();            
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean informacionNodoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoInfoNodo requestDataManttoInfoNodo = new RequestDataManttoInfoNodo();
            requestDataManttoInfoNodo.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoInfoNodo.setNombre(cmtBasicaMgl.getNombreBasica());
            requestDataManttoInfoNodo.setNombreUsuario(usuario);
            requestDataManttoInfoNodo.setFechaApertura("2011/10/25");
            requestDataManttoInfoNodo.setCostoRed("133,00");
            requestDataManttoInfoNodo.setLimites("CON LIMITES");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_DELETE,
                    ResponseManttoInfoNodoList.class,
                    requestDataManttoInfoNodo);
            if (responseManttoInfoNodoList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Supervisor Avanzada, Nombre Tipo: Supervisor
     */
    ////////////////////////////////////////////////////////////
    //********** REVISAR TABLAS DE GESTIÓN AVANZADA **********//
    ////////////////////////////////////////////////////////////
    private boolean supervisorAvanzadaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataSupervisorAvanzada requestDataSupervisorAvanzada = new RequestDataSupervisorAvanzada();
            requestDataSupervisorAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataSupervisorAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataSupervisorAvanzada.setCodigoDivision("TVC");
            requestDataSupervisorAvanzada.setNombreDivision("TV CABLE");
            requestDataSupervisorAvanzada.setCelular("0000000000");
            requestDataSupervisorAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseSupervisorAvanzadaList responseSupervisorAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_SUPERVISOR_AVANZADA_ADD,
                    ResponseSupervisorAvanzadaList.class,
                    requestDataSupervisorAvanzada);
            if (responseSupervisorAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseSupervisorAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    ////////////////////////////////////////////////////////////
    //********** REVISAR TABLAS DE GESTIÓN AVANZADA **********//
    ////////////////////////////////////////////////////////////
    private boolean supervisorAvanzadaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataSupervisorAvanzada requestDataSupervisorAvanzada = new RequestDataSupervisorAvanzada();
            requestDataSupervisorAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataSupervisorAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataSupervisorAvanzada.setCodigoDivision("TVC");
            requestDataSupervisorAvanzada.setNombreDivision("TV CABLE");
            requestDataSupervisorAvanzada.setCelular("0000000000");
            requestDataSupervisorAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseSupervisorAvanzadaList responseSupervisorAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_SUPERVISOR_AVANZADA_UPDATE,
                    ResponseSupervisorAvanzadaList.class,
                    requestDataSupervisorAvanzada);
            if (responseSupervisorAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseSupervisorAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    ////////////////////////////////////////////////////////////
    //********** REVISAR TABLAS DE GESTIÓN AVANZADA **********//
    ////////////////////////////////////////////////////////////
    private boolean supervisorAvanzadaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) 
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataSupervisorAvanzada requestDataSupervisorAvanzada = new RequestDataSupervisorAvanzada();
            requestDataSupervisorAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataSupervisorAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataSupervisorAvanzada.setCodigoDivision("TVC");
            requestDataSupervisorAvanzada.setNombreDivision("TV CABLE");
            requestDataSupervisorAvanzada.setCelular("0000000000");
            requestDataSupervisorAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseSupervisorAvanzadaList responseSupervisorAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_SUPERVISOR_AVANZADA_DELETE,
                    ResponseSupervisorAvanzadaList.class,
                    requestDataSupervisorAvanzada);
            if (responseSupervisorAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseSupervisorAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Asesor Gestión de Avanzada
     */
    private boolean manttoAsesorGestionDeAvanzadaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoAsesorGestionDeAvanzada requestDataManttoAsesorGestionDeAvanzada = new RequestDataManttoAsesorGestionDeAvanzada();
            requestDataManttoAsesorGestionDeAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoAsesorGestionDeAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoAsesorGestionDeAvanzada.setCelular("");
            requestDataManttoAsesorGestionDeAvanzada.setCodigoSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setDivision("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAsesorGestionDeAvanzadaList responseManttoAsesorGestionDeAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ASESOR_GESTION_DE_AVANZADA_ADD,
                    ResponseManttoAsesorGestionDeAvanzadaList.class,
                    requestDataManttoAsesorGestionDeAvanzada);
            if (responseManttoAsesorGestionDeAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAsesorGestionDeAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoAsesorGestionDeAvanzadaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoAsesorGestionDeAvanzada requestDataManttoAsesorGestionDeAvanzada = new RequestDataManttoAsesorGestionDeAvanzada();
            requestDataManttoAsesorGestionDeAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoAsesorGestionDeAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoAsesorGestionDeAvanzada.setCelular("");
            requestDataManttoAsesorGestionDeAvanzada.setCodigoSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setDivision("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAsesorGestionDeAvanzadaList responseManttoAsesorGestionDeAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ASESOR_GESTION_DE_AVANZADA_UPDATE,
                    ResponseManttoAsesorGestionDeAvanzadaList.class,
                    requestDataManttoAsesorGestionDeAvanzada);
            if (responseManttoAsesorGestionDeAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAsesorGestionDeAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoAsesorGestionDeAvanzadaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoAsesorGestionDeAvanzada requestDataManttoAsesorGestionDeAvanzada = new RequestDataManttoAsesorGestionDeAvanzada();
            requestDataManttoAsesorGestionDeAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoAsesorGestionDeAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoAsesorGestionDeAvanzada.setCelular("");
            requestDataManttoAsesorGestionDeAvanzada.setCodigoSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreSupervisor("");
            requestDataManttoAsesorGestionDeAvanzada.setDivision("");
            requestDataManttoAsesorGestionDeAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoAsesorGestionDeAvanzadaList responseManttoAsesorGestionDeAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ASESOR_GESTION_DE_AVANZADA_DELETE,
                    ResponseManttoAsesorGestionDeAvanzadaList.class,
                    requestDataManttoAsesorGestionDeAvanzada);
            if (responseManttoAsesorGestionDeAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoAsesorGestionDeAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Estados Gestión Avanzada
     */
    private boolean manttoEstadosAvanzadaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoEstadosAvanzada requestDataManttoEstadosAvanzada = new RequestDataManttoEstadosAvanzada();
            requestDataManttoEstadosAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadosAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());          

            requestDataManttoEstadosAvanzada.setNombreUsuario(usuario);
                      
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstadosAvanzadaList responseManttoEstadosAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADOS_AVANZADA_ADD,
                    ResponseManttoEstadosAvanzadaList.class,
                    requestDataManttoEstadosAvanzada);
            if (responseManttoEstadosAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException(responseManttoEstadosAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoEstadosAvanzadaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoEstadosAvanzada requestDataManttoEstadosAvanzada = new RequestDataManttoEstadosAvanzada();
            requestDataManttoEstadosAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadosAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoEstadosAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstadosAvanzadaList responseManttoEstadosAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADOS_AVANZADA_UPDATE,
                    ResponseManttoEstadosAvanzadaList.class,
                    requestDataManttoEstadosAvanzada);
            if (responseManttoEstadosAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException(responseManttoEstadosAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoEstadosAvanzadaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoEstadosAvanzada requestDataManttoEstadosAvanzada = new RequestDataManttoEstadosAvanzada();
            requestDataManttoEstadosAvanzada.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataManttoEstadosAvanzada.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataManttoEstadosAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoEstadosAvanzadaList responseManttoEstadosAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ESTADOS_AVANZADA_DELETE,
                    ResponseManttoEstadosAvanzadaList.class,
                    requestDataManttoEstadosAvanzada);
            if (responseManttoEstadosAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEstadosAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Asignar Asesor Gestión Avanzada
     */
    private boolean asignarAsesorAvanzadaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataAsignarAsesorAvanzada requestDataAsignarAsesorAvanzada = new RequestDataAsignarAsesorAvanzada();
            requestDataAsignarAsesorAvanzada.setCodigoAsesor("");
            requestDataAsignarAsesorAvanzada.setNombreAsesor("");
            requestDataAsignarAsesorAvanzada.setCodigoEdificio("");
            requestDataAsignarAsesorAvanzada.setNombreEdificio("");
            requestDataAsignarAsesorAvanzada.setComunidad("");
            requestDataAsignarAsesorAvanzada.setComunidadConsulta("");
            requestDataAsignarAsesorAvanzada.setCuentaConsulta("");
            requestDataAsignarAsesorAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseAsignarAsesorAvanzadaList responseAsignarAsesorAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_ASIGNAR_ASESOR_AVANZADA_ADD,
                    ResponseAsignarAsesorAvanzadaList.class,
                    requestDataAsignarAsesorAvanzada);
            if (responseAsignarAsesorAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseAsignarAsesorAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean asignarAsesorAvanzadaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAsignarAsesorAvanzada requestDataAsignarAsesorAvanzada = new RequestDataAsignarAsesorAvanzada();
            requestDataAsignarAsesorAvanzada.setCodigoAsesor("");
            requestDataAsignarAsesorAvanzada.setNombreAsesor("");
            requestDataAsignarAsesorAvanzada.setCodigoEdificio("");
            requestDataAsignarAsesorAvanzada.setNombreEdificio("");
            requestDataAsignarAsesorAvanzada.setComunidad("");
            requestDataAsignarAsesorAvanzada.setComunidadConsulta("");
            requestDataAsignarAsesorAvanzada.setCuentaConsulta("");
            requestDataAsignarAsesorAvanzada.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseAsignarAsesorAvanzadaList responseAsignarAsesorAvanzadaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_ASIGNAR_ASESOR_AVANZADA_UPDATE,
                    ResponseAsignarAsesorAvanzadaList.class,
                    requestDataAsignarAsesorAvanzada);
            if (responseAsignarAsesorAvanzadaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseAsignarAsesorAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Motivos de Cambios de Fecha
     */
    private boolean manttoMotivosCambioFechaAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataMotivosCambioFecha requestDataMotivosCambioFecha = new RequestDataMotivosCambioFecha();
            requestDataMotivosCambioFecha.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataMotivosCambioFecha.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataMotivosCambioFecha.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMotivosCambioFechaList responseManttoMotivosCambioFechaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MOTIVOS_CAMBIO_FECHA_ADD,
                    ResponseManttoMotivosCambioFechaList.class,
                    requestDataMotivosCambioFecha);
            if (responseManttoMotivosCambioFechaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMotivosCambioFechaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoMotivosCambioFechaUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataMotivosCambioFecha requestDataMotivosCambioFecha = new RequestDataMotivosCambioFecha();
            requestDataMotivosCambioFecha.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataMotivosCambioFecha.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataMotivosCambioFecha.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMotivosCambioFechaList responseManttoMotivosCambioFechaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MOTIVOS_CAMBIO_FECHA_UPDATE,
                    ResponseManttoMotivosCambioFechaList.class,
                    requestDataMotivosCambioFecha);
            if (responseManttoMotivosCambioFechaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoMotivosCambioFechaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoMotivosCambioFechaDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataMotivosCambioFecha requestDataMotivosCambioFecha = new RequestDataMotivosCambioFecha();
            requestDataMotivosCambioFecha.setCodigo(cmtBasicaMgl.getCodigoBasica());
            requestDataMotivosCambioFecha.setDescripcion(cmtBasicaMgl.getNombreBasica());
            requestDataMotivosCambioFecha.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseManttoMotivosCambioFechaList responseManttoMotivosCambioFechaList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_MOTIVOS_CAMBIO_FECHA_DELETE,
                    ResponseManttoMotivosCambioFechaList.class,
                    requestDataMotivosCambioFecha);
            if (responseManttoMotivosCambioFechaList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400-ws)" + responseManttoMotivosCambioFechaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /* ************************************ */
    /* ************* COMPAÑIAS ************ */
    /* ************************************ */
    /**
     * Creación de compañias en RR.Compañias de Administración, Ascensores,
 Proveedores y Constructoras.
     *
     * @param cmtCompaniaMgl
     * @param usuario
     * @return updated
     * @throws ApplicationException
     */
    public boolean companiaCreate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal) throws ApplicationException {
        boolean created = true;
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)
                || cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ADMON_NATURAL)) {
            return companiasDeAdministracionAdd(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ASCENSORES)) {
            return companiasDeAscensoresAdd(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_PROOVEDORES)) {
            return companiasDeProveedoresAdd(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS)) {
            return companiasDeConstructorasAdd(cmtCompaniaMgl, usuario);
        }
        return created;
    }

    /**
     * Modificación de compañias en RR.Compañias de Administración, Ascensores,
 Proveedores y Constructoras.
     *
     * @param cmtCompaniaMgl
     * @param usuario
     * @return updated
     * @throws ApplicationException
     */
    public boolean companiaUpdate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal) throws ApplicationException {
        boolean updated = true;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)
                || cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ADMON_NATURAL)) {
            return companiasDeAdministracionUpdate(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ASCENSORES)) {
            return companiasDeAscensoresUpdate(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_PROOVEDORES)) {
            return companiasDeProveedoresUpdate(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS)) {
            return companiasDeConstructorasUpdate(cmtCompaniaMgl, usuario);
        }

        return updated;
    }

    /**
     * Eliminación de compañias en RR.Compañias de Administración, Ascensores,
 Proveedores y Constructoras.
     *
     * @param cmtCompaniaMgl
     * @param usuario
     * @return deleted
     * @throws ApplicationException
     */
    public boolean companiaDelete(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal) throws ApplicationException {
        boolean deleted = true;
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)
                || cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ADMON_NATURAL)) {
            return companiasDeAdministracionDelete(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_ASCENSORES)) {
            return companiasDeAscensoresDelete(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_PROOVEDORES)) {
            return companiasDeProveedoresDelete(cmtCompaniaMgl, usuario);
        }
        if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                .equals(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS)) {
            return companiasDeConstructorasDelete(cmtCompaniaMgl, usuario);
        }
        return deleted;
    }

    /*
     * Tabla RR Compañia de Administración, Nombre Tipo: Compania de
     * administracion
     */
    private boolean companiasDeAdministracionAdd(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataAdminCompany requestDataAdminCompany = new RequestDataAdminCompany();
            if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                    .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)) {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion(cmtCompaniaMgl.getDireccion());
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            } else {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion("N/A");
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataAdminCompany.setNombreUsuario(usuario);
            ResponseAdminCompanyList responseAdminCompanyList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ADMIN_COMPANY_ADD,
                    ResponseAdminCompanyList.class,
                    requestDataAdminCompany);
            if (responseAdminCompanyList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseAdminCompanyList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage(); 
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeAdministracionUpdate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAdminCompany requestDataAdminCompany = new RequestDataAdminCompany();
            if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                    .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)) {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion(cmtCompaniaMgl.getDireccion());
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            } else {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion("N/A");
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            }
            requestDataAdminCompany.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseAdminCompanyList responseAdminCompanyList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ADMIN_COMPANY_UPDATE,
                    ResponseAdminCompanyList.class,
                    requestDataAdminCompany);
            if (responseAdminCompanyList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseAdminCompanyList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeAdministracionDelete(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataAdminCompany requestDataAdminCompany = new RequestDataAdminCompany();
            if (cmtCompaniaMgl.getTipoCompaniaObj().getTipoCompaniaId()
                    .equals(Constant.TIPO_COMPANIA_ID_ADMINISTRACION)) {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion(cmtCompaniaMgl.getDireccion());
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            } else {
                requestDataAdminCompany.setCodigo(cmtCompaniaMgl.getCodigoRr());
                requestDataAdminCompany.setDireccion("N/A");
                requestDataAdminCompany.setEmail1(cmtCompaniaMgl.getEmail());
                requestDataAdminCompany.setIdentificacion(cmtCompaniaMgl.getNitCompania() != null
                        && !cmtCompaniaMgl.getNitCompania().trim().isEmpty()
                        ? cmtCompaniaMgl.getNitCompania().replace("-", "") : "000000000");
                requestDataAdminCompany.setNombre(cmtCompaniaMgl.getNombreCompania());
                requestDataAdminCompany.setNombreAdmin(cmtCompaniaMgl.getNombreContacto());
                requestDataAdminCompany.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
                requestDataAdminCompany.setTelefono1(cmtCompaniaMgl.getTelefonos());
                requestDataAdminCompany.setTelefono2(cmtCompaniaMgl.getTelefono2());
                requestDataAdminCompany.setTelefono3(cmtCompaniaMgl.getTelefono3());
                requestDataAdminCompany.setTelefono4(cmtCompaniaMgl.getTelefono4());
            }
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            requestDataAdminCompany.setNombreUsuario(usuario);
            ResponseAdminCompanyList responseAdminCompanyList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_ADMIN_COMPANY_DELETE,
                    ResponseAdminCompanyList.class,
                    requestDataAdminCompany);
            if (responseAdminCompanyList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseAdminCompanyList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Compañia de Ascensores
     */
    private boolean companiasDeAscensoresAdd(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataCiaAscensores requestDataCiaAscensores = new RequestDataCiaAscensores();
            requestDataCiaAscensores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataCiaAscensores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataCiaAscensores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataCiaAscensores.setEmail2("");
            requestDataCiaAscensores.setIdentificacion(cmtCompaniaMgl.getNitCompania());
            requestDataCiaAscensores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataCiaAscensores.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataCiaAscensores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataCiaAscensores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataCiaAscensores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataCiaAscensores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataCiaAscensores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataCiaAscensores.setNombreUsuario(usuario);
            requestDataCiaAscensores.setConvenio("N");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCiaAscensoresList responseCiaAscensoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CIA_ASCENSORES_ADD,
                    ResponseCiaAscensoresList.class,
                    requestDataCiaAscensores);
            if (responseCiaAscensoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCiaAscensoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeAscensoresUpdate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataCiaAscensores requestDataCiaAscensores = new RequestDataCiaAscensores();
            requestDataCiaAscensores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataCiaAscensores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataCiaAscensores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataCiaAscensores.setEmail2("");
            requestDataCiaAscensores.setIdentificacion(cmtCompaniaMgl.getNitCompania());
            requestDataCiaAscensores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataCiaAscensores.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataCiaAscensores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataCiaAscensores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataCiaAscensores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataCiaAscensores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataCiaAscensores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataCiaAscensores.setNombreUsuario(usuario);
            requestDataCiaAscensores.setConvenio("N");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCiaAscensoresList responseCiaAscensoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CIA_ASCENSORES_UPDATE,
                    ResponseCiaAscensoresList.class,
                    requestDataCiaAscensores);
            if (responseCiaAscensoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCiaAscensoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) { 
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeAscensoresDelete(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCiaAscensores requestDataCiaAscensores = new RequestDataCiaAscensores();
            requestDataCiaAscensores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataCiaAscensores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataCiaAscensores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataCiaAscensores.setEmail2("");
            requestDataCiaAscensores.setIdentificacion(cmtCompaniaMgl.getNitCompania());
            requestDataCiaAscensores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataCiaAscensores.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataCiaAscensores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataCiaAscensores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataCiaAscensores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataCiaAscensores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataCiaAscensores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataCiaAscensores.setNombreUsuario(usuario);
            requestDataCiaAscensores.setConvenio("N");
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseCiaAscensoresList responseCiaAscensoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_MANTTO_CIA_ASCENSORES_DELETE,
                    ResponseCiaAscensoresList.class,
                    requestDataCiaAscensores);
            if (responseCiaAscensoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCiaAscensoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Proveedores
     */
    private boolean companiasDeProveedoresAdd(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProveedores requestDataProveedores = new RequestDataProveedores();
            requestDataProveedores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataProveedores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataProveedores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataProveedores.setEmail2("");
            requestDataProveedores.setNit(cmtCompaniaMgl.getNitCompania());
            requestDataProveedores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataProveedores.setGerente("");
            requestDataProveedores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataProveedores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataProveedores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataProveedores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataProveedores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataProveedores.setPersonaContacto1(cmtCompaniaMgl.getNombreContacto());
            requestDataProveedores.setNombreUsuario(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProveedoresList responseProveedoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PROVEEDORES_ADD,
                    ResponseProveedoresList.class,
                    requestDataProveedores);
            if (responseProveedoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProveedoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeProveedoresUpdate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProveedores requestDataProveedores = new RequestDataProveedores();
            requestDataProveedores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataProveedores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataProveedores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataProveedores.setEmail2("");
            requestDataProveedores.setNit(cmtCompaniaMgl.getNitCompania());
            requestDataProveedores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataProveedores.setGerente("");
            requestDataProveedores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataProveedores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataProveedores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataProveedores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataProveedores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataProveedores.setPersonaContacto1(cmtCompaniaMgl.getNombreContacto());
            requestDataProveedores.setNombreUsuario(usuario);

            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProveedoresList responseProveedoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PROVEEDORES_UPDATE,
                    ResponseProveedoresList.class,
                    requestDataProveedores);
            if (responseProveedoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProveedoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeProveedoresDelete(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataProveedores requestDataProveedores = new RequestDataProveedores();
            requestDataProveedores.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataProveedores.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataProveedores.setEmail1(cmtCompaniaMgl.getEmail());
            requestDataProveedores.setEmail2("");
            requestDataProveedores.setNit(cmtCompaniaMgl.getNitCompania());
            requestDataProveedores.setNombre(cmtCompaniaMgl.getNombreCompania());
            requestDataProveedores.setGerente("");
            requestDataProveedores.setPaginaWeb(cmtCompaniaMgl.getPaginaWeb());
            requestDataProveedores.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataProveedores.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataProveedores.setTelefono3(cmtCompaniaMgl.getTelefono3());
            requestDataProveedores.setTelefono4(cmtCompaniaMgl.getTelefono4());
            requestDataProveedores.setPersonaContacto1(cmtCompaniaMgl.getNombreContacto());
            requestDataProveedores.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseProveedoresList responseProveedoresList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_PROVEEDORES_DELETE,
                    ResponseProveedoresList.class,
                    requestDataProveedores);
            if (responseProveedoresList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseProveedoresList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     * Tabla RR Constructoras
     */
    private boolean companiasDeConstructorasAdd(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            RequestDataConstructoras requestDataConstructoras = new RequestDataConstructoras();
            requestDataConstructoras.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataConstructoras.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataConstructoras.setCorreoElectronico(cmtCompaniaMgl.getEmail());
            requestDataConstructoras.setDescripcion(cmtCompaniaMgl.getNombreCompania());
            requestDataConstructoras.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataConstructoras.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataConstructoras.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataConstructoras.setObservacion1("");
            requestDataConstructoras.setObservacion2("");
            requestDataConstructoras.setObservacion3("");
            requestDataConstructoras.setObservacion4("");
            requestDataConstructoras.setObservacion5("");
            requestDataConstructoras.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseConstructorasList responseConstructorasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_CONSTRUCTORAS_ADD,
                    ResponseConstructorasList.class,
                    requestDataConstructoras);
            if (responseConstructorasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseConstructorasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) { 
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeConstructorasUpdate(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataConstructoras requestDataConstructoras = new RequestDataConstructoras();
            requestDataConstructoras.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataConstructoras.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataConstructoras.setCorreoElectronico(cmtCompaniaMgl.getEmail());
            requestDataConstructoras.setDescripcion(cmtCompaniaMgl.getNombreCompania());
            requestDataConstructoras.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataConstructoras.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataConstructoras.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataConstructoras.setObservacion1("");
            requestDataConstructoras.setObservacion2("");
            requestDataConstructoras.setObservacion3("");
            requestDataConstructoras.setObservacion4("");
            requestDataConstructoras.setObservacion5("");
            requestDataConstructoras.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseConstructorasList responseConstructorasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_CONSTRUCTORAS_UPDATE,
                    ResponseConstructorasList.class,
                    requestDataConstructoras);
            if (responseConstructorasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseConstructorasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean companiasDeConstructorasDelete(CmtCompaniaMgl cmtCompaniaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataConstructoras requestDataConstructoras = new RequestDataConstructoras();
            requestDataConstructoras.setCodigo(cmtCompaniaMgl.getCodigoRr());
            requestDataConstructoras.setDireccion(cmtCompaniaMgl.getDireccion());
            requestDataConstructoras.setCorreoElectronico(cmtCompaniaMgl.getEmail());
            requestDataConstructoras.setDescripcion(cmtCompaniaMgl.getNombreCompania());
            requestDataConstructoras.setTelefono1(cmtCompaniaMgl.getTelefonos());
            requestDataConstructoras.setTelefono2(cmtCompaniaMgl.getTelefono2());
            requestDataConstructoras.setNombreContacto(cmtCompaniaMgl.getNombreContacto());
            requestDataConstructoras.setObservacion1("");
            requestDataConstructoras.setObservacion2("");
            requestDataConstructoras.setObservacion3("");
            requestDataConstructoras.setObservacion4("");
            requestDataConstructoras.setObservacion5("");
            requestDataConstructoras.setNombreUsuario(usuario);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            ResponseConstructorasList responseConstructorasList = restClientTablasBasicas.callWebService(
                    EnumeratorServiceName.TB_CONSTRUCTORAS_DELETE,
                    ResponseConstructorasList.class,
                    requestDataConstructoras);
            if (responseConstructorasList.getResultado()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseConstructorasList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Aliados
     */
    private boolean manttoAliadoCreate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRAllies = new RestClientBasicMaintenanceRRAllies(BASE_URI_RR);
        
            MantenimientoBasicoRRAliadosRequest mantenimientoBasicoRRAliadosRequest = new MantenimientoBasicoRRAliadosRequest();
            mantenimientoBasicoRRAliadosRequest.setCODIGO(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRRAliadosRequest.setDESCRIP(cmtBasicaMgl.getNombreBasica());
            
            mantenimientoBasicoRRAliadosRequest.setIDUSER(usuario);
                       
          
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                mantenimientoBasicoRRAliadosRequest.setESTADO("A");
            } else {
                mantenimientoBasicoRRAliadosRequest.setESTADO("I");
            }

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRAllies.callWebServiceMethodPost(
                    EnumeratorServiceName.BASICARR_ALLIES_CREAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRRAliadosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase("INS0000")) {
                throw new ApplicationException(mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /*
     *  Aliados
     */
    private boolean manttoAliadoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {

        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRAllies = new RestClientBasicMaintenanceRRAllies(BASE_URI_RR);
        
            MantenimientoBasicoRRAliadosRequest mantenimientoBasicoRRAliadosRequest = new MantenimientoBasicoRRAliadosRequest();
            mantenimientoBasicoRRAliadosRequest.setCODIGO(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRRAliadosRequest.setDESCRIP(cmtBasicaMgl.getNombreBasica());
            mantenimientoBasicoRRAliadosRequest.setIDUSER(usuario);

            if (cmtBasicaMgl.getActivado().equals("Y")) {
                mantenimientoBasicoRRAliadosRequest.setESTADO("A");
            } else {
                mantenimientoBasicoRRAliadosRequest.setESTADO("I");
            }

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRAllies.callWebServiceMethodPut(
                    EnumeratorServiceName.BASICARR_ALLIES_ACTUALIZAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRRAliadosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase("INS0000")) {
                throw new ApplicationException("(As400)" + mantenimientoBasicoRRBaseResponse.
                        getMensajeDeRespuesta());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean manttoAliadoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRAllies = new RestClientBasicMaintenanceRRAllies(BASE_URI_RR);
        
            MantenimientoBasicoRRAliadosRequest mantenimientoBasicoRRAliadosRequest = new MantenimientoBasicoRRAliadosRequest();
            mantenimientoBasicoRRAliadosRequest.setCODIGO(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRRAliadosRequest.setDESCRIP(cmtBasicaMgl.getNombreBasica());
            mantenimientoBasicoRRAliadosRequest.setIDUSER(usuario);

            if (cmtBasicaMgl.getActivado().equals("Y")) {
                mantenimientoBasicoRRAliadosRequest.setESTADO("A");
            } else {
                mantenimientoBasicoRRAliadosRequest.setESTADO("I");
            }

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRAllies.callWebServiceMethodDelete(
                    EnumeratorServiceName.BASICARR_ALLIES_ELIMINAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRRAliadosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase("INS0000")) {
                throw new ApplicationException("(As400)" + mantenimientoBasicoRRBaseResponse.
                        getMensajeDeRespuesta());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    private boolean estadoNodoAdd(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {

        boolean inserto = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            LOGGER.info("---------------------AGREGANDO ESTADO NODO---------------------");
            MantenimientoBasicoRREstadoNodosRequest mantenimientoBasicoRREstadoNodosRequest = new MantenimientoBasicoRREstadoNodosRequest();

            
            mantenimientoBasicoRREstadoNodosRequest.setIDUSER(usuario);
                              
            mantenimientoBasicoRREstadoNodosRequest.setNDCODEP(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRREstadoNodosRequest.setNDDESEP(cmtBasicaMgl.getNombreBasica());
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRStateNodes = new RestClientBasicMaintenanceRRStateNodes(BASE_URI_RR);

            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }

            mantenimientoBasicoRREstadoNodosRequest.setNDSTATP(estado);

            LOGGER.error("Usuario: " + usuario);
            LOGGER.error("Codigo basica: " + cmtBasicaMgl.getCodigoBasica());
            LOGGER.error("Descripcion: " + cmtBasicaMgl.getDescripcion());
            LOGGER.error("Estado registro: " + cmtBasicaMgl.getEstadoRegistro());

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRStateNodes.callWebServiceMethodPOST(
                    EnumeratorServiceName.BASICRR_NODESTATE_CREAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRREstadoNodosRequest);
            LOGGER.error("Codigo " + mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    + " Mensaje " + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());

            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().equalsIgnoreCase("INS0000")) {

                inserto = false;
            }

            if (mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException(mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }

        return inserto;
    }

    private boolean estadoNodoUpdate(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {

        boolean inserto = true;
        try {
            
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            LOGGER.info("---------------------ACTUALIZANDO ESTADO NODO---------------------");
            MantenimientoBasicoRREstadoNodosRequest mantenimientoBasicoRREstadoNodosRequest = new MantenimientoBasicoRREstadoNodosRequest();

            mantenimientoBasicoRREstadoNodosRequest.setIDUSER(usuario);
            mantenimientoBasicoRREstadoNodosRequest.setNDCODEP(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRREstadoNodosRequest.setNDDESEP(cmtBasicaMgl.getNombreBasica());
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRStateNodes = new RestClientBasicMaintenanceRRStateNodes(BASE_URI_RR);

            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }

            mantenimientoBasicoRREstadoNodosRequest.setNDSTATP(estado);

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRStateNodes.callWebServiceMethodPUT(
                    EnumeratorServiceName.BASICRR_NODESTATE_ACTUALIZAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRREstadoNodosRequest);

            LOGGER.error("Codigo " + mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    + " Mensaje " + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());

            if (mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException(mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().equalsIgnoreCase("INS0000")) {

                inserto = false;
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
             ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage(); 
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return inserto;

    }

    private boolean estadoNodoDelete(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal) throws ApplicationException {
        boolean inserto = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
           
            LOGGER.info("---------------------ELIMINANDO ESTADO NODO---------------------");
            MantenimientoBasicoRREstadoNodosRequest mantenimientoBasicoRREstadoNodosRequest = new MantenimientoBasicoRREstadoNodosRequest();

            mantenimientoBasicoRREstadoNodosRequest.setIDUSER(usuario);
            mantenimientoBasicoRREstadoNodosRequest.setNDCODEP(cmtBasicaMgl.getCodigoBasica());
            mantenimientoBasicoRREstadoNodosRequest.setNDDESEP(cmtBasicaMgl.getNombreBasica());
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRStateNodes = new RestClientBasicMaintenanceRRStateNodes(BASE_URI_RR);

            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }

            mantenimientoBasicoRREstadoNodosRequest.setNDSTATP(estado);

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse = restClientBasicMaintenanceRRStateNodes.callWebServiceMethodDELETE(
                    EnumeratorServiceName.BASICRR_NODESTATE_ELIMINAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    mantenimientoBasicoRREstadoNodosRequest);

            LOGGER.error("Codigo " + mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    + " Mensaje " + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());

            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta().equalsIgnoreCase("INS0000")) {

                inserto = false;
            }
            if (mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException(mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }

        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return inserto;

    }

    private boolean crearTipificacionRed(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        boolean validacion = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            MantenimientoBasicoRRTipificacionDeRedRequest tipificacionDeRedRequest = new MantenimientoBasicoRRTipificacionDeRedRequest();
            
            tipificacionDeRedRequest.setIDUSER(usuario);
                        
            tipificacionDeRedRequest.setTRCODR(cmtBasicaMgl.getAbreviatura());
            tipificacionDeRedRequest.setTRDESR(cmtBasicaMgl.getNombreBasica());
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRNetworkTyping = new RestClientBasicMaintenanceRRNetworkTyping(BASE_URI_RR);

            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }
            tipificacionDeRedRequest.setTRSTAT(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse = restClientBasicMaintenanceRRNetworkTyping.callWebServiceMethodPOST(
                    EnumeratorServiceName.BASICRR_TIPIFICACION_RED_CREAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    tipificacionDeRedRequest);

            LOGGER.error(basicoRRBaseResponse.getCodigoDeRespuesta() + " "
                    + basicoRRBaseResponse.getMensajeDeRespuesta());

            if (basicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + basicoRRBaseResponse.getMensajeDeRespuesta());
            }
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) { 
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    private boolean actualizarTipificacionRed(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        boolean validacion = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            MantenimientoBasicoRRTipificacionDeRedRequest tipificacionDeRedRequest = new MantenimientoBasicoRRTipificacionDeRedRequest();
            tipificacionDeRedRequest.setIDUSER(usuario);
            tipificacionDeRedRequest.setTRCODR(cmtBasicaMgl.getAbreviatura());
            tipificacionDeRedRequest.setTRDESR(cmtBasicaMgl.getNombreBasica());
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRNetworkTyping = new RestClientBasicMaintenanceRRNetworkTyping(BASE_URI_RR);
            
            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }
            tipificacionDeRedRequest.setTRSTAT(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse = restClientBasicMaintenanceRRNetworkTyping.callWebServiceMethodPUT(
                    EnumeratorServiceName.BASICRR_TIPIFICACION_RED_ACTUALIZAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    tipificacionDeRedRequest);
            if (basicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + basicoRRBaseResponse.getMensajeDeRespuesta());
            }
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) { 
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    private boolean eliminarTipificacionRed(CmtBasicaMgl cmtBasicaMgl, String usuarioOriginal)
            throws ApplicationException {
        boolean validacion = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            MantenimientoBasicoRRTipificacionDeRedRequest tipificacionDeRedRequest = new MantenimientoBasicoRRTipificacionDeRedRequest();
            tipificacionDeRedRequest.setIDUSER(usuario);
            tipificacionDeRedRequest.setTRCODR(cmtBasicaMgl.getAbreviatura());
            tipificacionDeRedRequest.setTRDESR(cmtBasicaMgl.getNombreBasica());
            
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI);

            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRNetworkTyping = new RestClientBasicMaintenanceRRNetworkTyping(BASE_URI_RR);
            
            String estado;
            if (cmtBasicaMgl.getActivado().equals("Y")) {
                estado = "A";
            } else {
                estado = "I";
            }
            tipificacionDeRedRequest.setTRSTAT(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse = restClientBasicMaintenanceRRNetworkTyping.callWebServiceMethodPUT(
                    EnumeratorServiceName.BASICRR_TIPIFICACION_RED_ELIMINAR,
                    MantenimientoBasicoRRBaseResponse.class,
                    tipificacionDeRedRequest);
            if (basicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + basicoRRBaseResponse.getMensajeDeRespuesta());
            }
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }
}
