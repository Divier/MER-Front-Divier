package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.request.RequestDataCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.request.RequestDataConsultaPorInventarioEquipo;
import co.com.claro.cmas400.ejb.request.RequestDataGestionDeAvanzada;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInformacionAdicionalSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataInventarioCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.request.RequestDataNotasCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestListNotasCuentaMatriz;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseCompetenciaEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseConsultaPorInventarioEquipoList;
import co.com.claro.cmas400.ejb.respons.ResponseDataCompetenciaEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseDataCompetenciaEdificioBlackList;
import co.com.claro.cmas400.ejb.respons.ResponseGestionDeAvanzadaList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInformacionAdicionalSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseInventarioCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasCuentaMatrizList;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.UnitApiServiceManager;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientCuentasMatrices;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import co.com.claro.unitapi.wsclient.ResponseQueryStreet;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camargomf
 */
public class CmtCuentaMatrizRRMglManager {
    
    ParametrosMglManager parametrosMglManager;
    RestClientCuentasMatrices restClientCuentasMatrices;
    String BASE_URI;
    private final String SIN_DATOS = "  Sin datos";
    private static final Logger LOGGER = LogManager.getLogger(CmtCuentaMatrizRRMglManager.class);
    CmtBasicaMglManager cmtBasicaMglManager;
    CmtSubEdificioMglManager cmtSubEdificioMglManager;


    public CmtCuentaMatrizRRMglManager() throws ApplicationException {
        parametrosMglManager = new ParametrosMglManager();
          BASE_URI = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                .iterator().next().getParValor();
        restClientCuentasMatrices = new RestClientCuentasMatrices(BASE_URI);
    }
    

    /*
     * Tabla Competencia Edificio BlackList
     */
    //Consultar BlackList
    private List<ResponseDataCompetenciaEdificioBlackList> manttoCompetenciaEdificioBlackListQuery(CmtBlackListMgl cmtBlackListMgl)
            throws ApplicationException {
        try {
            RequestDataCompetenciaEdificioBlackList request = new RequestDataCompetenciaEdificioBlackList();
            request.setCodigoCuenta(
                    cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());

            ResponseCompetenciaEdificioBlackList responseCompetenciaEdificioBlackList =
                    restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_COMPETENCIA_EDIFICIO_BLACK_LIST_QUERY,
                    ResponseCompetenciaEdificioBlackList.class,
                    request);

            if (responseCompetenciaEdificioBlackList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioBlackList.getMensaje());
            }
            return responseCompetenciaEdificioBlackList.getListBlackList();
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado a (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
    }

    //Modificar Recibe Parametros
    private boolean manttoCompetenciaEdificioBlackListAdd(CmtBlackListMgl cmtBlackListMgl, String usuarioOriginal)
            throws ApplicationException {
        boolean result = true;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCompetenciaEdificioBlackList request = new RequestDataCompetenciaEdificioBlackList();
            request.setCodigoCuenta(
                    cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
            request.setCodigo(cmtBlackListMgl.getBlackListObj().getCodigoBasica());
            request.setNombre(cmtBlackListMgl.getBlackListObj().getNombreBasica());
            request.setNombreUsuario(usuario);
            ResponseCompetenciaEdificioBlackList responseCompetenciaEdificioBlackList =
                    restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_COMPETENCIA_EDIFICIO_BLACK_LIST_ADD,
                    ResponseCompetenciaEdificioBlackList.class,
                    request);
            if (responseCompetenciaEdificioBlackList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioBlackList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            result = false;
            String msg = "Se produjo un error al momento de ejecutar llamado (RR)-ws '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return result;
    }

    //Modificar Recibe Parametros
    private boolean manttoCompetenciaEdificioBlackListDelete(CmtBlackListMgl cmtBlackListMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCompetenciaEdificioBlackList request = new RequestDataCompetenciaEdificioBlackList();
            request.setCodigoCuenta(
                    cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
            request.setCodigo(cmtBlackListMgl.getBlackListObj().getCodigoBasica());
            request.setNombre(cmtBlackListMgl.getBlackListObj().getNombreBasica());
            request.setNombreUsuario(usuario);

            ResponseCompetenciaEdificioBlackList responseCompetenciaEdificioBlackList =
                    restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_COMPETENCIA_EDIFICIO_BLACK_LIST_DELETE,
                    ResponseCompetenciaEdificioBlackList.class,
                    request);
            if (responseCompetenciaEdificioBlackList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioBlackList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (RR)-ws '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Competencia Edificio
     */
    public boolean crearCompetencia(CmtCompetenciaMgl cmtCompetenciaMgl, String usuario, int perfil)
            throws ApplicationException {
        boolean result;
        CmtSubEdificioMgl cmtSubEdificioMgl = cmtCompetenciaMgl.getSubEdificioObj();
        CmtCuentaMatrizMgl cuentaMatriz = cmtSubEdificioMgl.getCuentaMatrizObj();
        ResponseCompetenciaEdificioList responseCompetenciaEdificioList;
        CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();
        boolean isMultiEdificio = subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
        && subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
        if (isMultiEdificio) {
            boolean isEdificioSelectedGeneral = cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (isEdificioSelectedGeneral) {
                responseCompetenciaEdificioList = competenciaEdificioQuery(cmtCompetenciaMgl, usuario);
                for (ResponseDataCompetenciaEdificio dataCompetenciaEdificio : responseCompetenciaEdificioList.getListCompetenciaEdificio()) {
                    if (dataCompetenciaEdificio.getCodigo().equals(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).substring(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).length() - 4))) {
                        throw new ApplicationException("(As400) Esta competencia ya esta asociada al Edificio");
                    }
                }
                competenciaEdificioAdd(cmtCompetenciaMgl, usuario, perfil);
                result = true;
            } else {
                result = true;
            }
        } else {
            responseCompetenciaEdificioList = competenciaEdificioQuery(cmtCompetenciaMgl, usuario);
            for (ResponseDataCompetenciaEdificio dataCompetenciaEdificio : responseCompetenciaEdificioList.getListCompetenciaEdificio()) {
                if (dataCompetenciaEdificio.getCodigo().equals(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).substring(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).length() - 4))) {
                    throw new ApplicationException("(As400) Esta competencia ya esta asociada al Edificio");
                }
            }
            competenciaEdificioAdd(cmtCompetenciaMgl, usuario, perfil);
            result = true;
        }
        return result;
    }

    private ResponseCompetenciaEdificioList competenciaEdificioQuery(CmtCompetenciaMgl cmtCompetenciaMgl, String usuarioOriginal) throws ApplicationException {
        ResponseCompetenciaEdificioList responseCompetenciaEdificioList;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCompetenciaEdificioList requestDataCompetenciaEdificioList = new RequestDataCompetenciaEdificioList();
            requestDataCompetenciaEdificioList.setCodigoEdificio(cmtCompetenciaMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
            requestDataCompetenciaEdificioList.setCodigoCompetencia(cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr());
            requestDataCompetenciaEdificioList.setDescripcion(cmtCompetenciaMgl.getCompetenciaTipo().getProveedorCompetencia().getNombreBasica());
            requestDataCompetenciaEdificioList.setTipo(cmtCompetenciaMgl.getCompetenciaTipo().getServicioCompetencia().getCodigoBasica());
            requestDataCompetenciaEdificioList.setNombreUsuario(usuario);
            responseCompetenciaEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_COMPETENCIA_EDIFICIO_QUERY,
                    ResponseCompetenciaEdificioList.class,
                    requestDataCompetenciaEdificioList);
            if (responseCompetenciaEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (RR)-ws '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return responseCompetenciaEdificioList;
    }

    private boolean competenciaEdificioAdd(CmtCompetenciaMgl cmtCompetenciaMgl, String usuarioOriginal, int perfil)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCompetenciaEdificioList requestDataCompetenciaEdificioList = new RequestDataCompetenciaEdificioList();
            requestDataCompetenciaEdificioList.setCodigoEdificio(cmtCompetenciaMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
            requestDataCompetenciaEdificioList.setCodigoCompetencia(cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr());
            requestDataCompetenciaEdificioList.setDescripcion(cmtCompetenciaMgl.getCompetenciaTipo().getProveedorCompetencia().getNombreBasica());
            requestDataCompetenciaEdificioList.setTipo(cmtCompetenciaMgl.getCompetenciaTipo().getServicioCompetencia().getCodigoBasica());
            requestDataCompetenciaEdificioList.setNombreUsuario(usuario);
            ResponseCompetenciaEdificioList responseCompetenciaEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_COMPETENCIA_EDIFICIO_ADD,
                    ResponseCompetenciaEdificioList.class,
                    requestDataCompetenciaEdificioList);
            if (responseCompetenciaEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (RR)-ws '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    public boolean eliminarCompetencia(CmtCompetenciaMgl cmtCompetenciaMgl, String usuario, int perfil)
            throws ApplicationException {
        boolean result;
        boolean encontrado = false;
        CmtSubEdificioMgl cmtSubEdificioMgl = cmtCompetenciaMgl.getSubEdificioObj();
        CmtCuentaMatrizMgl cuentaMatriz = cmtSubEdificioMgl.getCuentaMatrizObj();
        CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();
        ResponseCompetenciaEdificioList responseCompetenciaEdificioList;

        boolean isMultiEdificio = subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                && subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
        if (isMultiEdificio) {
            boolean isEdificioSelectedGeneral = cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (isEdificioSelectedGeneral) {
                responseCompetenciaEdificioList = competenciaEdificioQuery(cmtCompetenciaMgl, usuario);
                for (ResponseDataCompetenciaEdificio dataCompetenciaEdificio : responseCompetenciaEdificioList.getListCompetenciaEdificio()) {
                    if (dataCompetenciaEdificio.getCodigo().equals(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).substring(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).length() - 4))) {
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    throw new ApplicationException("(As400) Esta competencia no esta asociada al edificio");
                }
                responseCompetenciaEdificioList = competenciaEdificioQuery(cmtCompetenciaMgl, usuario);
                for (ResponseDataCompetenciaEdificio dataCompetenciaEdificio : responseCompetenciaEdificioList.getListCompetenciaEdificio()) {
                    if (dataCompetenciaEdificio.getCodigo().equals(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).substring(("0000" + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()).length() - 4))) {
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    throw new ApplicationException("(As400) Esta competencia no esta asociada al edificio");
                }
                competenciaEdificioDelete(cmtCompetenciaMgl, usuario, perfil);
                result = true;
            } else {
                result = true;
            }
        } else {
            competenciaEdificioDelete(cmtCompetenciaMgl, usuario, perfil);
            result = true;
        }
        return result;
    }

    private boolean competenciaEdificioDelete(CmtCompetenciaMgl cmtCompetenciaMgl, String usuarioOriginal, int perfil)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataCompetenciaEdificioList requestDataCompetenciaEdificioList = new RequestDataCompetenciaEdificioList();
            requestDataCompetenciaEdificioList.setCodigoEdificio(cmtCompetenciaMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
            requestDataCompetenciaEdificioList.setCodigoCompetencia(cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr());
            requestDataCompetenciaEdificioList.setDescripcion(cmtCompetenciaMgl.getCompetenciaTipo().getProveedorCompetencia().getNombreBasica());
            requestDataCompetenciaEdificioList.setTipo(cmtCompetenciaMgl.getCompetenciaTipo().getServicioCompetencia().getCodigoBasica());
            requestDataCompetenciaEdificioList.setNombreUsuario(usuario);
            ResponseCompetenciaEdificioList responseCompetenciaEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_COMPETENCIA_EDIFICIO_DELETE,
                    ResponseCompetenciaEdificioList.class,
                    requestDataCompetenciaEdificioList);
            if (responseCompetenciaEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseCompetenciaEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Información Adicional SubEdificio
     */
    //Modificar Recibe Parametros
    private boolean informacionAdicionalSubEdificioUpdate()
            throws ApplicationException {
        try {
            RequestDataInformacionAdicionalSubEdificio requestDataInformacionAdicionalSubEdificio = new RequestDataInformacionAdicionalSubEdificio();
            ResponseInformacionAdicionalSubEdificioList responseInformacionAdicionalSubEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_INFORMACION_ADICIONAL_SUBEDIFICIO_UPDATE,
                    ResponseInformacionAdicionalSubEdificioList.class,
                    requestDataInformacionAdicionalSubEdificio);
            if (responseInformacionAdicionalSubEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseInformacionAdicionalSubEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Información Adicional SubEdificio
     */
    //Modificar Recibe Parametros
    private boolean informacionAdicionalEdificioUpdate()
            throws ApplicationException {
        try {
            RequestDataInformacionAdicionalEdificio requestDataInformacionAdicionalEdificio = new RequestDataInformacionAdicionalEdificio();
            ResponseInformacionAdicionalEdificioList responseInformacionAdicionalEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_INFORMACION_ADICIONAL_EDIFICIO_UPDATE,
                    ResponseInformacionAdicionalEdificioList.class,
                    requestDataInformacionAdicionalEdificio);
            if (responseInformacionAdicionalEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseInformacionAdicionalEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Mantenimiento SubEdificios
     */
    //Modificar Recibe Parametros
    public boolean manttoSubEdificiosAdd(CmtSubEdificioMgl subEdificioMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);

            //Obtenemos el nombre del SubEdificio en Estandar RR
            // actualizar entrada del subedificio
            RequestDataManttoSubEdificios requestDataManttoSubEdificios = new RequestDataManttoSubEdificios();
            CmtSubEdificioMglManager subEdificioManager = new CmtSubEdificioMglManager();
            if (subEdificioMgl.getNombreEntSubedificio() != null && !subEdificioMgl.getNombreEntSubedificio().isEmpty()) {
                requestDataManttoSubEdificios.setDescripcion(subEdificioMgl.getNombreEntSubedificio());
            } else {
                String subEdName = subEdificioManager.obtenerNombreSubEdEstandarRr(subEdificioMgl);
                requestDataManttoSubEdificios.setDescripcion(subEdName);
            }
            requestDataManttoSubEdificios.setCodigoCuenta(subEdificioMgl.getCuentaMatrizObj().getNumeroCuenta().toString());
            requestDataManttoSubEdificios.setCodigoEstado(CmtUtilidadesCM.strCNum(subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica().toString(), 4));
            requestDataManttoSubEdificios.setEstado(subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());
            requestDataManttoSubEdificios.setNodo(subEdificioMgl.getNodoObj().getNodCodigo());
        requestDataManttoSubEdificios.setNombreUsuario(usuario);
            ResponseManttoSubEdificiosList responseManttoSubEdificiosList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_ADD,
                    ResponseManttoSubEdificiosList.class,
                    requestDataManttoSubEdificios);
            if (responseManttoSubEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoSubEdificiosList.getMensaje());
            }
            subEdificioMgl.setCodigoRr(responseManttoSubEdificiosList.getMensaje().replace("Adición  exitosa ,  Seq : ", "").trim());
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    private boolean manttoSubEdificiosUpdate(CmtSubEdificioMgl subEdificioMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            
            CmtSubEdificioMglManager subEdificioManager = new CmtSubEdificioMglManager();
            RequestDataManttoSubEdificios requestDataManttoSubEdificios = new RequestDataManttoSubEdificios();
           
              if (subEdificioMgl.getNombreEntSubedificio() != null && !subEdificioMgl.getNombreEntSubedificio().isEmpty()) {
              // subedificios con su propia direccion desde vt
                    requestDataManttoSubEdificios.setDescripcion(subEdificioMgl.getNombreEntSubedificio());
            } else {
                  // subedificios con su propia direccion desde la pestaña general
                  CmtSubEdificioMgl cmtSubEdificioMglExistente = subEdificioManager.findById(subEdificioMgl.getSubEdificioId());
                  if (cmtSubEdificioMglExistente.getListDireccionesMgl() != null && cmtSubEdificioMglExistente.getListDireccionesMgl().size() > 0) {
                      String nombreSub = "EN " + subEdificioMgl.getListDireccionesMgl().get(0).getCalleRr() + " " + subEdificioMgl.getListDireccionesMgl().get(0).getUnidadRr();
                      requestDataManttoSubEdificios.setDescripcion(nombreSub);
                  } else {

                      String subEdName = subEdificioManager.obtenerNombreSubEdEstandarRr(subEdificioMgl);
                      requestDataManttoSubEdificios.setDescripcion(subEdName);
                  }
               
            }
            //Obtenemos el nombre del SubEdificio en Estandar RR
            requestDataManttoSubEdificios.setCodigo(subEdificioMgl.getCodigoRr());
            requestDataManttoSubEdificios.setCodigoCuenta(subEdificioMgl.getCuentaMatrizObj().getNumeroCuenta().toString());
            requestDataManttoSubEdificios.setCodigoEstado(CmtUtilidadesCM.strCNum(subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica(), 4));
            requestDataManttoSubEdificios.setEstado(subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());
            if(subEdificioMgl.getNodoObj()!= null){
                     requestDataManttoSubEdificios.setNodo(subEdificioMgl.getNodoObj().getNodCodigo());
            }else{
                 throw new ApplicationException("(As400) " + "No hay un nodo asignado en el Subedificio" +subEdificioMgl.getNombreEntSubedificio() );
            }
            requestDataManttoSubEdificios.setNombreUsuario(usuario);
            String codigoEstado = requestDataManttoSubEdificios.getCodigoEstado();
            ResponseManttoSubEdificiosList responseManttoSubEdificiosList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_UPDATE,
                    ResponseManttoSubEdificiosList.class,
                    requestDataManttoSubEdificios);
            if (responseManttoSubEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                if (responseManttoSubEdificiosList.getMensaje().equalsIgnoreCase(Constant.ERROR_MENSAJE_RESULTADO_RR)) {
                    throw new ApplicationException("(As400) " + Constant.ERROR_MENSAJE_RESULTADO_RR +
                            ". El codigo Estado "+ codigoEstado + " no existe en RR");
                } else {
                    throw new ApplicationException("(As400)" + responseManttoSubEdificiosList.getMensaje());
                }
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    public boolean manttoSubEdificiosDelete(CmtSubEdificioMgl subEdificioMgl, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataManttoSubEdificios requestDataManttoSubEdificios = new RequestDataManttoSubEdificios();
            requestDataManttoSubEdificios.setCodigo(subEdificioMgl.getCodigoRr());
            requestDataManttoSubEdificios.setCodigoCuenta(subEdificioMgl.getCuentaMatrizObj().getNumeroCuenta().toString());
            requestDataManttoSubEdificios.setDescripcion(subEdificioMgl.getNombreSubedificio());
            requestDataManttoSubEdificios.setCodigoEstado(subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica());
            requestDataManttoSubEdificios.setEstado(subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());
            requestDataManttoSubEdificios.setNodo(subEdificioMgl.getNodoObj().getNodCodigo());
            requestDataManttoSubEdificios.setNombreUsuario(usuario);
            ResponseManttoSubEdificiosList responseManttoSubEdificiosList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_DELETE,
                    ResponseManttoSubEdificiosList.class,
                    requestDataManttoSubEdificios);
            if (responseManttoSubEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoSubEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
           String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Mantenimiento SubEdificios
     */
    //Modificar Recibe Parametros
    private boolean manttoEdificioAdd(CmtSubEdificioMgl subEdificioMgl, String Usuario)
            throws ApplicationException {
        try {
            RequestDataManttoEdificio requestDataManttoEdificio = new RequestDataManttoEdificio();
            ResponseManttoEdificioList responseManttoEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_EDIFICIO_ADD,
                    ResponseManttoEdificioList.class,
                    requestDataManttoEdificio);
            if (responseManttoEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    private boolean manttoEdificioUpdate(CmtSubEdificioMgl subEdificioMgl)
            throws ApplicationException {
        try {
            RequestDataManttoEdificio requestDataManttoEdificio = new RequestDataManttoEdificio();
            ResponseManttoEdificioList responseManttoEdificioList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_MANTTO_EDIFICIO_UPDATE,
                    ResponseManttoEdificioList.class,
                    requestDataManttoEdificio);
            if (responseManttoEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseManttoEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Inventario Cuenta Matriz
     */
    //Modificar Recibe Parametros
    private boolean inventarioCuentaMatrizAdd()
            throws ApplicationException {
        try {
            RequestDataManttoEdificio requestDataManttoEdificio = new RequestDataManttoEdificio();
            ResponseInventarioCuentaMatrizList responseInventarioCuentaMatrizList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_INVENTARIO_CUENTA_MATRIZ_ADD,
                    ResponseInventarioCuentaMatrizList.class,
                    requestDataManttoEdificio);
            if (responseInventarioCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)-ws" + responseInventarioCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros
    private boolean inventarioCuentaMatrizDelete() throws ApplicationException {
        try {
            RequestDataInventarioCuentaMatriz requestDataInventarioCuentaMatriz = new RequestDataInventarioCuentaMatriz();
            ResponseInventarioCuentaMatrizList responseInventarioCuentaMatrizList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_INVENTARIO_CUENTA_MATRIZ_DELETE,
                    ResponseInventarioCuentaMatrizList.class,
                    requestDataInventarioCuentaMatriz);
            if (responseInventarioCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseInventarioCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Notas de Cuenta Matriz
     */
    //Modificar Recibe Parametros
    public boolean notasCuentaMatrizAdd(CmtNotasMgl cmtNotas, String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            SimpleDateFormat dateFormatRR = new SimpleDateFormat("yyyyMMdd");
            RequestListNotasCuentaMatriz requestDataNotasCMLinea;
            RequestDataNotasCuentaMatriz requestDataNotasCM = new RequestDataNotasCuentaMatriz();
            List<RequestListNotasCuentaMatriz> listNotas = new ArrayList<>();

            /// Logica para conocer si una cuenta matriz es MultiEdificio
            CmtCuentaMatrizMgl cuentaMatriz = cmtNotas.getSubEdificioObj().getCmtCuentaMatrizMglObj();
            CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();

            boolean isMultiEdificio = subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            String lineaNota = "";

            //condicion No es edificio general
            if (isMultiEdificio && !cmtNotas.getSubEdificioObj().getEstadoSubEdificioObj()
                    .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                lineaNota += "SubEdificio: " + cmtNotas.getSubEdificioObj().getNombreSubedificio() + ": ";
            }

            for (int contLin = 0; contLin <= cmtNotas.getNota().trim().length(); contLin++) {
                if ((contLin != 0 && contLin % 74 == 0) || contLin == cmtNotas.getNota().trim().length()) {
                    if (contLin != cmtNotas.getNota().trim().length()) {
                        lineaNota += cmtNotas.getNota().trim().substring(contLin, contLin + 1);
                    }
                    requestDataNotasCMLinea = new RequestListNotasCuentaMatriz();
                    requestDataNotasCMLinea.setFechaCreacion(cmtNotas.getFechaCreacion() == null ? ""
                            : dateFormatRR.format(cmtNotas.getFechaCreacion()));
                    requestDataNotasCMLinea.setNombreUsuario(usuario == null ? "" : usuario);
                    requestDataNotasCMLinea.setDescripcionNota(lineaNota);
                    listNotas.add(requestDataNotasCMLinea);
                    lineaNota = "";
                } else {
                    lineaNota += cmtNotas.getNota().trim().substring(contLin, contLin + 1);
                }
            }
            requestDataNotasCM.setUsuarioModificador(usuario);
            requestDataNotasCM.setNumeroEdificio(cuentaMatriz.getNumeroCuenta() == null ? ""
                    : cuentaMatriz.getNumeroCuenta().toString());
            requestDataNotasCM.setDataNotasCuentaMatriz(listNotas);


            ResponseNotasCuentaMatrizList responseNotasCuentaMatrizList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_NOTAS_CUENTA_MATRIZ_ADD,
                    ResponseNotasCuentaMatrizList.class, requestDataNotasCM);


            if (responseNotasCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseNotasCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros
    private boolean notasCuentaMatrizUpdate()
            throws ApplicationException {
        try {
            RequestDataNotasCuentaMatriz requestDataNotasCuentaMatriz = new RequestDataNotasCuentaMatriz();
            ResponseNotasCuentaMatrizList responseNotasCuentaMatrizList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_NOTAS_CUENTA_MATRIZ_UPDATE,
                    ResponseNotasCuentaMatrizList.class,
                    requestDataNotasCuentaMatriz);
            if (responseNotasCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)-ws" + responseNotasCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Notas de Cuenta Matriz
     */
    //Modificar Recibe Parametros
    private boolean gestionDeAvanzadaAdd()
            throws ApplicationException {
        try {
            RequestDataGestionDeAvanzada requestDataGestionDeAvanzada = new RequestDataGestionDeAvanzada();
            ResponseGestionDeAvanzadaList responseGestionDeAvanzadaList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_GESTION_DE_AVANZADA_ADD,
                    ResponseGestionDeAvanzadaList.class,
                    requestDataGestionDeAvanzada);
            if (responseGestionDeAvanzadaList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseGestionDeAvanzadaList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
           String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    public String findBySerial(String fabricante, String tipo, String Serial) throws ApplicationException {
        try {
            RequestDataConsultaPorInventarioEquipo requestDataConsultaPorInventarioEquipo = new RequestDataConsultaPorInventarioEquipo();
            requestDataConsultaPorInventarioEquipo.setFabricaEquipo(fabricante.toUpperCase());
            requestDataConsultaPorInventarioEquipo.setTipoEquipo(tipo.toUpperCase());
            requestDataConsultaPorInventarioEquipo.setSerieEquipo(Serial);
            ResponseConsultaPorInventarioEquipoList responseConsultaPorInventarioEquipoList = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_CONSULTA_POR_INVENTARIO_EQUIPO_QUERY,
                    ResponseConsultaPorInventarioEquipoList.class,
                    requestDataConsultaPorInventarioEquipo);
            if (responseConsultaPorInventarioEquipoList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseConsultaPorInventarioEquipoList.getMensaje());
            }
            return responseConsultaPorInventarioEquipoList.getCodigoEdificio();
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }

    }

    /**
     * Actualiza la Informacion del SubEdificio.Permite actualizar la
 informacion del SubEdificio en RR.
     *
     * @author Johnnatan Ortiz
     * @param subEdificioMgl subEdificio a actualizar
     * @param usuario
     * @return si se realizo la actualizacion del subEdificio
     * @throws ApplicationException
     */
    public boolean updateInfoSubEdificio(CmtSubEdificioMgl subEdificioMgl, String usuario)
            throws ApplicationException {
        boolean result;
        CmtBasicaMgl identidicadorBasicaMgl;
        //Obtenemos la cuenta matriz a la cual pertenece el subedificio
        CmtCuentaMatrizMgl cuentaMatriz = subEdificioMgl.getCuentaMatrizObj();
        //Obtenemos el subEdificio General de la  cuenta matriz
        CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();
        //validamos el tipo de cuenta Matriz
          boolean isMultiEdificio = false;
        if(subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null){
              isMultiEdificio = subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO); 
        }
        if (isMultiEdificio) {
              boolean isEdificioSelectedGeneral = false;
             if(subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null){
                   isEdificioSelectedGeneral = subEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
             }
            if (isEdificioSelectedGeneral) {
                //save admin  ascensores en CM
                
//                  ajuste para cambio de estado del subedificio
                if(subEdificioMgl.getEstadoSubNuevo() != null){
                      CmtBasicaMglManager basicaManager = new  CmtBasicaMglManager();
                     subEdificioMgl.setEstadoSubEdificioObj(basicaManager.findById(subEdificioMgl.getEstadoSubNuevo()));
                     result = updateInfoCm(subEdificioMgl, usuario);
                }else{
                     result = updateInfoCm(subEdificioMgl, usuario);
                }
            } else {
                 result = updateInfoAdicionalSubEdificio(subEdificioMgl, isMultiEdificio, usuario);
            }
        } else {
            //save admin  ascensores en CM
            if (subEdificioMgl.getEstadoSubNuevo() != null) {
                CmtBasicaMglManager basicaManager = new CmtBasicaMglManager();
                subEdificioMgl.setEstadoSubEdificioObj(basicaManager.findById(subEdificioMgl.getEstadoSubNuevo()));
                result = updateInfoCm(subEdificioMgl, usuario);
            } else {
                result = updateInfoCm(subEdificioMgl, usuario);
            }
                //save Contructora en CM}
            if (result) {
                result = updateInfoAdicionalSubEdificio(subEdificioMgl, isMultiEdificio, usuario);
            }
        }
        return result;
    }

    /**
     * Actualiza la Infomacion general de la CM. Permite actualizar la
     * informacion general de la CM en RR.
     *
     * @author Johnnatan Ortiz
     * @param subEdificioMgl subEdificio general o unico a actualizar
     * @param usuario Usuario.
     * @return si se realizo la actualizacion del subEdificio
     * @throws ApplicationException
     */
    public boolean updateInfoCm(CmtSubEdificioMgl subEdificioMgl, String usuarioOriginal)
            throws ApplicationException {
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        boolean result = false;
        try {
            CmtCuentaMatrizMgl cuentaMatrizMgl = subEdificioMgl.getCmtCuentaMatrizMglObj();
            cuentaMatrizMgl.setNombreCuenta(subEdificioMgl.getNombreSubedificio());
            RequestDataManttoEdificio request = new RequestDataManttoEdificio();

            request.setCodigoEdificio(cuentaMatrizMgl.getNumeroCuenta().toString());

            CmtDireccionMgl direccionPrincipal = cuentaMatrizMgl.getDireccionPrincipal();
            //verificamos que exista direccion principal
            if (direccionPrincipal != null
                    && direccionPrincipal.getCalleRr() != null
                    && !direccionPrincipal.getCalleRr().trim().isEmpty()) {

                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                        direccionPrincipal.getCalleRr().toUpperCase())) {
                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionPrincipal.getCalleRr(), direccionPrincipal.getUnidadRr(), direccionPrincipal.getCodTipoDir())) {
                        UnitApiServiceManager unitApiServiceManager = new UnitApiServiceManager();
                        //consultamos el codigo en rr de la calle de la direccion principal
                        ResponseQueryStreet responseQueryStreetP = unitApiServiceManager.streetQueryManager(
                                cuentaMatrizMgl.getComunidad(),
                                cuentaMatrizMgl.getDivision(),
                                direccionPrincipal.getCalleRr());

                        if (responseQueryStreetP != null
                                && responseQueryStreetP.getSTRN() != null
                                && !responseQueryStreetP.getSTRN().trim().isEmpty()) {
                            //Direccion Principal
                            request.setCodCalle(responseQueryStreetP.getSTRN());
                            request.setDescCalle(direccionPrincipal.getCalleRr());
                            request.setNumCasa(direccionPrincipal.getUnidadRr());
                        }


                        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                        List<CmtDireccionMgl> direccionAlternaList =   cmtDireccionMglManager.findDireccionesByCuentaMatrizAlternas(cuentaMatrizMgl);

                        //Direccion Alterna 1
                        if (direccionAlternaList != null
                                && direccionAlternaList.size() > 0) {
                            if (direccionAlternaList.get(0) != null
                                    && direccionAlternaList.get(0).getCalleRr() != null
                                    && !direccionAlternaList.get(0).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(0).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(0).getCalleRr(), direccionAlternaList.get(0).getUnidadRr(), direccionAlternaList.get(0).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(0).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir1(responseQueryStreetA.getSTRN());
                                            request.setDescDir1(direccionAlternaList.get(0).getCalleRr());
                                            request.setCasaDir1(direccionAlternaList.get(0).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                            //Direccion Alterna 2
                            if (direccionAlternaList.size() > 1
                                    && direccionAlternaList.get(1) != null
                                    && direccionAlternaList.get(1).getCalleRr() != null
                                    && !direccionAlternaList.get(1).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(1).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(1).getCalleRr(), direccionAlternaList.get(1).getUnidadRr(), direccionAlternaList.get(1).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(1).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir2(responseQueryStreetA.getSTRN());
                                            request.setDescDir2(direccionAlternaList.get(1).getCalleRr());
                                            request.setCasaDir2(direccionAlternaList.get(1).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }
                            }
                            //Direccion Alterna 3
                            if (direccionAlternaList.size() > 2
                                    && direccionAlternaList.get(2) != null
                                    && direccionAlternaList.get(2).getCalleRr() != null
                                    && !direccionAlternaList.get(2).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(2).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(2).getCalleRr(), direccionAlternaList.get(2).getUnidadRr(), direccionAlternaList.get(2).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(2).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir3(responseQueryStreetA.getSTRN());
                                            request.setDescDir3(direccionAlternaList.get(2).getCalleRr());
                                            request.setCasaDir3(direccionAlternaList.get(2).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                            //Direccion Alterna 4
                            if (direccionAlternaList.size() > 3
                                    && direccionAlternaList.get(3) != null
                                    && direccionAlternaList.get(3).getCalleRr() != null
                                    && !direccionAlternaList.get(3).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(3).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(3).getCalleRr(), direccionAlternaList.get(3).getUnidadRr(), direccionAlternaList.get(3).getCodTipoDir())) {

                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(3).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir4(responseQueryStreetA.getSTRN());
                                            request.setDescDir4(direccionAlternaList.get(3).getCalleRr());
                                            request.setCasaDir4(direccionAlternaList.get(3).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                        }

                        request.setCodEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                                : subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica());
                        request.setDescEstad(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                                : subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());

                        request.setCodEstrato(subEdificioMgl.getEstrato() == null ? ""
                                : subEdificioMgl.getEstrato().getCodigoBasica());
                        request.setDescEstrato(subEdificioMgl.getEstrato() == null ? ""
                                : subEdificioMgl.getEstrato().getNombreBasica());

                        request.setCodTipoEdificio(subEdificioMgl.getTipoEdificioObj() == null ? ""
                                : subEdificioMgl.getTipoEdificioObj().getCodigoBasica());
                        request.setDescTipoEdificio(subEdificioMgl.getTipoEdificioObj() == null ? ""
                                : subEdificioMgl.getTipoEdificioObj().getNombreBasica());

                        request.setNomEdificio(cuentaMatrizMgl.getNombreCuenta() == null ? ""
                                : cuentaMatrizMgl.getNombreCuenta());

                        request.setCodproducto(subEdificioMgl.getProductoObj() == null ? ""
                                : subEdificioMgl.getProductoObj().getCodigoBasica());

                        request.setCostoApartament(subEdificioMgl.getCostoEjecucion() == null ? ""
                                : subEdificioMgl.getCostoEjecucion().toString());

                        request.setCodAdministracion(subEdificioMgl.getCompaniaAdministracionObj() == null ? "2000"
                                : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaAdministracionObj().getCodigoRr(), 4));
                        request.setDescAdministracion(subEdificioMgl.getCompaniaAdministracionObj() == null ? "Sin compania"
                                : subEdificioMgl.getCompaniaAdministracionObj().getNombreCompania());
                        //JDHT
                        if (subEdificioMgl.getCompaniaAscensorObj() != null) {
                            if (subEdificioMgl.getCompaniaAscensorObj().getCodigoRr() != null
                                    && !subEdificioMgl.getCompaniaAscensorObj().getCodigoRr().isEmpty()) {
                                if (subEdificioMgl.getCompaniaAscensorObj().getCodigoRr().equalsIgnoreCase("0000")) {
                                    subEdificioMgl.getCompaniaAscensorObj().setCodigoRr("2000");
                                }
                            }
                        }

                        request.setCodCiaAscensores(subEdificioMgl.getCompaniaAscensorObj() == null ? "2000"
                                : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaAscensorObj().getCodigoRr(), 4));
                        request.setDescCiaAscensores(subEdificioMgl.getCompaniaAscensorObj() == null ? "Sin Compania"
                                : subEdificioMgl.getCompaniaAscensorObj().getNombreCompania());

                        request.setHeadEnd(subEdificioMgl.getHeadEnd() == null ? ""
                                : subEdificioMgl.getHeadEnd());

                        request.setNodo(subEdificioMgl.getNodoObj() == null ? ""
                                : subEdificioMgl.getNodoObj().getNodCodigo());

                        request.setTipo(subEdificioMgl.getTipoLoc() == null ? ""
                                : subEdificioMgl.getTipoLoc());


                        if (subEdificioMgl.getAdministrador() == null || subEdificioMgl.getAdministrador().isEmpty()) {
                            subEdificioMgl.setAdministrador("NA.");
                        }

                        request.setNomContacto(subEdificioMgl.getAdministrador() == null ? ""
                                : subEdificioMgl.getAdministrador());

                        request.setNomEdificio(cuentaMatrizMgl.getNombreCuenta() == null ? ""
                                : cuentaMatrizMgl.getNombreCuenta());

                        request.setTelOtro(subEdificioMgl.getTelefonoPorteria2() == null ? ""
                                : subEdificioMgl.getTelefonoPorteria2());

                        request.setTotalUnidades(String.valueOf(subEdificioMgl.getUnidadesEstimadas()));
                        //TODO: Revisar
                        request.setTelPorteria(subEdificioMgl.getTelefonoPorteria() == null ? "1"
                                : subEdificioMgl.getTelefonoPorteria());
                        request.setNombreUsuario(usuario);


                        ResponseManttoEdificioList response =
                                restClientCuentasMatrices.callWebService(
                                EnumeratorServiceName.CM_MANTTO_EDIFICIO_UPDATE,
                                ResponseManttoEdificioList.class,
                                request);
                        if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                            result = false;
                            throw new ApplicationException("(As400)" + response.getMensaje());

                        } else {
                            result = true;
                        }
                        //Llamar a tablas basicas de mantto edificio
                        if (result) {
                            CmtTablasBasicasRRMglManager basicasRRMglManager = new CmtTablasBasicasRRMglManager();
                            result = basicasRRMglManager.edificioUpdate(cuentaMatrizMgl, usuario);
                        }

                    } else {
                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                    }
                } else {
                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                }
            }
        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            LOGGER.error("Error al momento de actualizar la CCMM. EX000: " + e.getMessage(), e);
            throw new ApplicationException("(As400-ws) " + e.getMessage(), e);
        }
        return result;

    }

    /**
     * crea la Infomacion general de la CM. Permite crear la informacion general
     * de la CM en RR.
     *
     * @author Johnnatan Ortiz
     * @param subEdificioMgl subEdificio general o unico a actualizar
     * @param usuario Usuario.
     * @return si se realizo la creacion del subEdificio
     * @throws ApplicationException
     */
    public boolean addInfoCm(CmtSubEdificioMgl subEdificioMgl, String usuarioOriginal)
            throws ApplicationException {
        
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        boolean result = false;
        String calleRr="";
        try {
            CmtCuentaMatrizMgl cuentaMatrizMgl = subEdificioMgl.getCmtCuentaMatrizMglObj();
            RequestDataManttoEdificio request = new RequestDataManttoEdificio();

            request.setCodigoEdificio(cuentaMatrizMgl.getNumeroCuenta().toString());

            CmtDireccionMgl direccionPrincipal = cuentaMatrizMgl.getDireccionPrincipal();
            //verificamos que exista direccion principal
            if (direccionPrincipal != null
                    && direccionPrincipal.getCalleRr() != null
                    && !direccionPrincipal.getCalleRr().trim().isEmpty()) {
 
                /*JDHT Si al crear una CM ya se aplico la logica de corregimiento no se debe realizar
                nuevamente debido a que duplica el corregimiento en la calle de la direccion */
                if(!subEdificioMgl.isCorregimientoAplicado()){
                    calleRr = obtenerCalleWithCorregimiento(cuentaMatrizMgl, direccionPrincipal.getCalleRr());
                } else {
                    calleRr = direccionPrincipal.getCalleRr();
                }
                
                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                        calleRr.toUpperCase())) {
                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), calleRr, direccionPrincipal.getUnidadRr(), direccionPrincipal.getCodTipoDir())) {
                        UnitApiServiceManager unitApiServiceManager = new UnitApiServiceManager();
                        //consultamos el codigo en rr de la calle de la direccion principal
                        ResponseQueryStreet responseQueryStreetP = unitApiServiceManager.streetQueryManager(
                                cuentaMatrizMgl.getComunidad(),
                                cuentaMatrizMgl.getDivision(),
                                calleRr);

                        if (responseQueryStreetP != null
                                && responseQueryStreetP.getSTRN() != null
                                && !responseQueryStreetP.getSTRN().trim().isEmpty()) {
                            //Direccion Principal
                            request.setCodCalle(responseQueryStreetP.getSTRN());
                            request.setDescCalle(calleRr);
                            request.setNumCasa(direccionPrincipal.getUnidadRr());
                        }



                        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
                        List<CmtDireccionMgl> direccionAlternaList =   cmtDireccionMglManager.findDireccionesByCuentaMatrizAlternas(cuentaMatrizMgl);


                        //Direccion Alterna 1
                        if (direccionAlternaList != null
                                && direccionAlternaList.size() > 0) {
                            if (direccionAlternaList.get(0) != null
                                    && direccionAlternaList.get(0).getCalleRr() != null
                                    && !direccionAlternaList.get(0).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(0).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(0).getCalleRr(), direccionAlternaList.get(0).getUnidadRr(), direccionAlternaList.get(0).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(0).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir1(responseQueryStreetA.getSTRN());
                                            request.setDescDir1(direccionAlternaList.get(0).getCalleRr());
                                            request.setCasaDir1(direccionAlternaList.get(0).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                            //Direccion Alterna 2
                            if (direccionAlternaList.size() > 1
                                    && direccionAlternaList.get(1) != null
                                    && direccionAlternaList.get(1).getCalleRr() != null
                                    && !direccionAlternaList.get(1).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(1).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(1).getCalleRr(), direccionAlternaList.get(1).getUnidadRr(), direccionAlternaList.get(1).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(1).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir2(responseQueryStreetA.getSTRN());
                                            request.setDescDir2(direccionAlternaList.get(1).getCalleRr());
                                            request.setCasaDir2(direccionAlternaList.get(1).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }
                            }
                            //Direccion Alterna 3
                            if (direccionAlternaList.size() > 2
                                    && direccionAlternaList.get(2) != null
                                    && direccionAlternaList.get(2).getCalleRr() != null
                                    && !direccionAlternaList.get(2).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(2).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(2).getCalleRr(), direccionAlternaList.get(2).getUnidadRr(), direccionAlternaList.get(2).getCodTipoDir())) {
                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(2).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir3(responseQueryStreetA.getSTRN());
                                            request.setDescDir3(direccionAlternaList.get(2).getCalleRr());
                                            request.setCasaDir3(direccionAlternaList.get(2).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                            //Direccion Alterna 4
                            if (direccionAlternaList.size() > 3
                                    && direccionAlternaList.get(3) != null
                                    && direccionAlternaList.get(3).getCalleRr() != null
                                    && !direccionAlternaList.get(3).getCalleRr().trim().isEmpty()) {
                                if (direccionRRManager.crearStreetHHPPRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(),
                                        direccionAlternaList.get(3).getCalleRr().toUpperCase())) {
                                    if (direccionRRManager.crearCruceRR(cuentaMatrizMgl.getComunidad(), cuentaMatrizMgl.getDivision(), direccionAlternaList.get(3).getCalleRr(), direccionAlternaList.get(3).getUnidadRr(), direccionAlternaList.get(3).getCodTipoDir())) {

                                        ResponseQueryStreet responseQueryStreetA = unitApiServiceManager.streetQueryManager(
                                                cuentaMatrizMgl.getComunidad(),
                                                cuentaMatrizMgl.getDivision(),
                                                direccionAlternaList.get(3).getCalleRr());
                                        if (responseQueryStreetA != null
                                                && responseQueryStreetA.getSTRN() != null
                                                && !responseQueryStreetA.getSTRN().trim().isEmpty()) {
                                            request.setCodDir4(responseQueryStreetA.getSTRN());
                                            request.setDescDir4(direccionAlternaList.get(3).getCalleRr());
                                            request.setCasaDir4(direccionAlternaList.get(3).getUnidadRr());
                                        }
                                    } else {
                                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                                    }
                                } else {
                                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                                }

                            }
                        }

                        request.setCodEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                                : subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica());
                        request.setDescEstad(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                                : subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());

                        request.setCodEstrato(subEdificioMgl.getEstrato() == null ? ""
                                : subEdificioMgl.getEstrato().getCodigoBasica());
                        request.setDescEstrato(subEdificioMgl.getEstrato() == null ? ""
                                : subEdificioMgl.getEstrato().getNombreBasica());

                        request.setCodTipoEdificio(subEdificioMgl.getTipoEdificioObj() == null ? ""
                                : subEdificioMgl.getTipoEdificioObj().getCodigoBasica());
                        request.setDescTipoEdificio(subEdificioMgl.getTipoEdificioObj() == null ? ""
                                : subEdificioMgl.getTipoEdificioObj().getNombreBasica());

                        request.setNomEdificio(cuentaMatrizMgl.getNombreCuenta() == null ? ""
                                : cuentaMatrizMgl.getNombreCuenta());

                        request.setCodproducto(subEdificioMgl.getProductoObj() == null ? ""
                                : subEdificioMgl.getProductoObj().getCodigoBasica());

                        request.setCostoApartament(subEdificioMgl.getCostoEjecucion() == null ? ""
                                : subEdificioMgl.getCostoEjecucion().toString());

                        request.setCodAdministracion(subEdificioMgl.getCompaniaAdministracionObj() == null ? "2000"
                                : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaAdministracionObj().getCodigoRr(), 4));
                        request.setDescAdministracion(subEdificioMgl.getCompaniaAdministracionObj() == null ? "Sin compania"
                                : subEdificioMgl.getCompaniaAdministracionObj().getNombreCompania());

                        request.setCodCiaAscensores(subEdificioMgl.getCompaniaAscensorObj() == null ? "2000"
                                : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaAscensorObj().getCodigoRr(), 4));
                        request.setDescCiaAscensores(subEdificioMgl.getCompaniaAscensorObj() == null ? "Sin Compania"
                                : subEdificioMgl.getCompaniaAscensorObj().getNombreCompania());

                        request.setCodproducto(subEdificioMgl.getProductoObj() == null ? "0009"
                                : CmtUtilidadesCM.strCNum(subEdificioMgl.getProductoObj().getCodigoBasica(), 4));
                        request.setNomProducto(subEdificioMgl.getProductoObj() == null ? "TRIPLE PLAY"
                                : subEdificioMgl.getProductoObj().getNombreBasica());

                        request.setHeadEnd(subEdificioMgl.getHeadEnd() == null ? ""
                                : subEdificioMgl.getHeadEnd());

                        request.setNodo(subEdificioMgl.getNodoObj() == null ? ""
                                : subEdificioMgl.getNodoObj().getNodCodigo());

                        request.setTipo(subEdificioMgl.getTipoLoc() == null ? ""
                                : subEdificioMgl.getTipoLoc());


                        if (subEdificioMgl.getAdministrador() == null || subEdificioMgl.getAdministrador().isEmpty()) {
                            subEdificioMgl.setAdministrador("NA.");
                        }

                        request.setNomContacto(subEdificioMgl.getAdministrador() == null || subEdificioMgl.getAdministrador().isEmpty() ? "NA."
                                : subEdificioMgl.getAdministrador());

                        request.setNomEdificio(cuentaMatrizMgl.getNombreCuenta() == null ? ""
                                : cuentaMatrizMgl.getNombreCuenta());

                        request.setTelOtro(subEdificioMgl.getTelefonoPorteria2() == null ? ""
                                : subEdificioMgl.getTelefonoPorteria2());

                        request.setTotalUnidades(String.valueOf(subEdificioMgl.getUnidades()));
                        //TODO: Revisar
                        request.setTelPorteria(subEdificioMgl.getTelefonoPorteria() == null ? "1"
                                : subEdificioMgl.getTelefonoPorteria());
                        request.setTotalUnidades(Integer.toString(subEdificioMgl.getUnidadesEstimadas()));

                        request.setNombreUsuario(usuario);
                         if(request.getNomEdificio() != null && !request.getNomEdificio().isEmpty()){
                            String nomEdificio = request.getNomEdificio().toUpperCase().replace("Ñ", "N");
                            request.setNomEdificio(nomEdificio);
                        }
                         
                         if(request.getDescCalle() != null && !request.getDescCalle().isEmpty()){
                            String desCalle= request.getDescCalle().toUpperCase().replace("Ñ", "N");
                            request.setDescCalle(desCalle);
                        }
                         
                         
                     
                        ResponseManttoEdificioList response =
                                restClientCuentasMatrices.callWebService(
                                EnumeratorServiceName.CM_MANTTO_EDIFICIO_ADD,
                                ResponseManttoEdificioList.class,
                                request);
                        if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                             if(!cuentaMatrizMgl.getNumeroCuenta().equals(0)){
                                   cmtTablasBasicasRRMglManager.edificioDelete(cuentaMatrizMgl, usuario);
                             }
                            result = false;
                            throw new ApplicationException("(As400)" + response.getMensaje());

                        }
                    } else {
                        throw new ApplicationException("Error al crear cruce RR de la direccion principal");
                    }
                } else {
                    throw new ApplicationException("Error al crear la calle RR de la direccion principal");
                }
            }

            result = true;
        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            LOGGER.error("Error al momento de actualizar la CCMM. EX000: " + e.getMessage(), e);
            throw new ApplicationException("(As400-ws) " + e.getMessage(), e);
        }
        return result;

    }

    /**
     * Actualiza la Informacion adicional de un SubEdificio. Permite actualizar
     * la informacion adicional de un SubEdificio en RR.
     *
     * @author Johnnatan Ortiz
     * @param subEdificioMgl subEdificio a actualizar
     * @param isMultiEdificio indica si la CM es multiedificio
     * @param usuario Usuario.
     * @return si se realizo la actualizacion del subEdificio
     * @throws ApplicationException
     */
    public boolean updateInfoAdicionalSubEdificio(CmtSubEdificioMgl subEdificioMgl,
            boolean isMultiEdificio, String usuarioOriginal)
            throws ApplicationException {

        boolean result = false;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            CmtCuentaMatrizMgl cuentaMatriz = subEdificioMgl.getCmtCuentaMatrizMglObj();
            SimpleDateFormat dateFormatRr = new SimpleDateFormat("yyyyMMdd");
            if (isMultiEdificio) {

                RequestDataInformacionAdicionalSubEdificio request =
                        new RequestDataInformacionAdicionalSubEdificio();

                request.setCodigoCuenta(cuentaMatriz.getNumeroCuenta().toString());//cm
                request.setNombreEdificio(cuentaMatriz.getNombreCuenta());//cm
                 
                request.setCodigoSubEdificio(subEdificioMgl.getCodigoRr() == null ? ""
                        : subEdificioMgl.getCodigoRr());
                     request.setDescripcionSubedificio(subEdificioMgl.getNombreSubedificio() == null ? ""
                        : subEdificioMgl.getNombreSubedificio());

                request.setCodigoMotivo("999");//
                request.setDescripcionMotivo("ACTUALIZACION MGL");

                //TODO: obtener info de la calle del sub edificio
                request.setNombreCalle(subEdificioMgl.getCuentaMatrizObj().getDireccionPrincipal().getCalleRr());

                /*
                 * Inicio datos a modificar
                 */
                request.setCodigoOrigenDatos(subEdificioMgl.getOrigenDatosObj() == null ? "1"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getOrigenDatosObj().getCodigoBasica(), 4));
                request.setDescripcionOrigenDatos(subEdificioMgl.getOrigenDatosObj() == null ? "BARRIDOS CALLE"
                        : subEdificioMgl.getOrigenDatosObj().getNombreBasica());

                request.setCodigoTipoProyecto(subEdificioMgl.getTipoProyectoObj() == null ? "0001"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getTipoProyectoObj().getCodigoBasica(), 4));
                request.setNombreTipoProyecto(subEdificioMgl.getTipoProyectoObj() == null ? "CONSTRUCTORA RESIDENCIAL"
                        : subEdificioMgl.getTipoProyectoObj().getNombreBasica());

                request.setCodigoConstructor(subEdificioMgl.getCompaniaConstructoraObj() == null ? "2000"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaConstructoraObj().getCodigoRr(), 4));
                request.setNombreConstructor(subEdificioMgl.getCompaniaConstructoraObj() == null ? "SIN COMPANIA"
                        : subEdificioMgl.getCompaniaConstructoraObj().getNombreCompania());

                request.setFechaEntrega(subEdificioMgl.getFechaEntregaEdificio() == null ? "19000101"
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaEdificio()));

                request.setCodigoEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica());
                request.setDescripcionEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica());

                //TODO:buscar codigos
                request.setCodigoAsesor("9999");//ASESOR DE AVANZADA
                request.setNombreAsesor("CODIGO STANDAR");

                request.setCodigoEspecialista("9999");//ESPECIALISTA DE AVANZADA
                request.setNombreEspecialista("CODIGO STANDAR");

                request.setCodigoSupervisor("9999");//SUPERVISOR VT
                request.setNombreSupervisor("CODIGO STANDAR");

                request.setCodigoTecnico("9999");//
                request.setNombreTecnico("CODIGO STANDAR");

                //TODO: buscar VT Cerrada--> ot Visita Tecnica
                request.setVt(subEdificioMgl.getVisitaTecnica() == null ? ""
                        : subEdificioMgl.getVisitaTecnica());//s/n
                request.setFechaVT(subEdificioMgl.getFechaVisitaTecnica() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaVisitaTecnica()));//sub-fecha ejecucion vt-->OT
                request.setCostoVT(subEdificioMgl.getCostoVisitaTecnica() == null ? ""
                        : subEdificioMgl.getCostoVisitaTecnica().toString());//sub - lo colocan de la VT

                request.setReDiseno(subEdificioMgl.getReDiseno() == null ? ""
                        : subEdificioMgl.getReDiseno());//S/N
                request.setFechaReporteDiseno(subEdificioMgl.getFechaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaReDiseno()));
                request.setFechaRespuestaDiseno(subEdificioMgl.getFechaRespuestaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaRespuestaReDiseno()));

                request.setCierre(subEdificioMgl.getCierre() == null ? ""
                        : subEdificioMgl.getCierre());//S/N
                request.setFechaRecibidoCierre(subEdificioMgl.getFechaRecibidoCierre() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaRecibidoCierre()));
                request.setMeta(subEdificioMgl.getMeta() == null ? ""
                        : subEdificioMgl.getMeta().toString());
                request.setFechaInicioEjecucion(subEdificioMgl.getFechaInicioEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaInicioEjecuion()));
                request.setFechaFinEjecucion(subEdificioMgl.getFechaFinEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaFinEjecuion()));
                request.setCostoEjecucion(subEdificioMgl.getCostoEjecucion() == null ? ""
                        : subEdificioMgl.getCostoEjecucion().toString());

                request.setRePlanos(subEdificioMgl.getPlanos() == null ? ""
                        : subEdificioMgl.getPlanos());//S/N
                request.setFechaSolicitudPlanos(subEdificioMgl.getFechaSolicitudPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaSolicitudPlanos()));
                request.setFechaEntregaPlanos(subEdificioMgl.getFechaEntregaPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaPlanos()));

                request.setConectRCorriente(subEdificioMgl.getConexionCorriente() == null ? ""
                        : subEdificioMgl.getConexionCorriente());//S/N
                request.setFechaSolicitudConectRCorriente(subEdificioMgl.getFechaSolicitudConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaSolicitudConexionCorriente()));
                request.setFechaEntregaConectRCorriente(subEdificioMgl.getFechaEntregaConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaConexionCorriente()));

                request.setNombreUsuario(usuario);
                /*
                 * Fin datos a modificar
                 */

                ResponseInformacionAdicionalSubEdificioList response =
                        restClientCuentasMatrices.callWebService(
                        EnumeratorServiceName.CM_INFORMACION_ADICIONAL_SUBEDIFICIO_UPDATE,
                        ResponseInformacionAdicionalSubEdificioList.class,
                        request);

                if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                    result = false;
                    throw new ApplicationException("(As400)" + response.getMensaje());
                } else {
                    result = true;
                }
                //Lladamo a manttosubEdificio
                // por aqui creo va a rr
                if (result) {
                        result = manttoSubEdificiosUpdate(subEdificioMgl, usuario);
                    }
            } else {
                RequestDataInformacionAdicionalEdificio request = new RequestDataInformacionAdicionalEdificio();

                request.setCodigoEdificio(subEdificioMgl.getCmtCuentaMatrizMglObj().getNumeroCuenta() == null ? ""
                        : subEdificioMgl.getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
                request.setDescripcionEdificio(subEdificioMgl.getNombreSubedificio() == null ? ""
                        : subEdificioMgl.getNombreSubedificio());

                request.setCodigoMotivo("");//
                request.setDescripcionMotivo("");

                /*
                 * Inicio datos a modificar
                 */
                request.setCodigoEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioMgl.getEstadoSubEdificioObj().getCodigoBasica());
                request.setDescripcionEstado(subEdificioMgl.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioMgl.getEstadoSubEdificioObj().getNombreBasica().toUpperCase());

                request.setCodigoOrigenDatos(subEdificioMgl.getOrigenDatosObj() == null ? "1"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getOrigenDatosObj().getCodigoBasica(), 4));
                request.setDescripcionOrigenDatos(subEdificioMgl.getOrigenDatosObj() == null ? "BARRIDOS CALLE"
                        : subEdificioMgl.getOrigenDatosObj().getNombreBasica());

                request.setCodigoTipoProyecto(subEdificioMgl.getTipoProyectoObj() == null ? "0001"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getTipoProyectoObj().getCodigoBasica(), 4));
                request.setNombreTipoProyecto(subEdificioMgl.getTipoProyectoObj() == null ? "CONSTRUCTORA RESIDENCIAL"
                        : subEdificioMgl.getTipoProyectoObj().getNombreBasica());

                request.setCodigoConstructor(subEdificioMgl.getCompaniaConstructoraObj() == null ? "2000"
                        : CmtUtilidadesCM.strCNum(subEdificioMgl.getCompaniaConstructoraObj().getCodigoRr(), 4));
                request.setNombreConstructor(subEdificioMgl.getCompaniaConstructoraObj() == null ? "SIN COMPANIA"
                        : subEdificioMgl.getCompaniaConstructoraObj().getNombreCompania());

                request.setFechaEntrega(subEdificioMgl.getFechaEntregaEdificio() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaEdificio()));

                //TODO:buscar codigos
                request.setCodigoAsesor("9999");//ASESOR DE AVANZADA
                request.setNombreAsesor("CODIGO STANDAR");

                request.setCodigoEspecialista("9999");//ESPECIALISTA DE AVANZADA
                request.setNombreEspecialista("CODIGO STANDAR");

                request.setCodigoSupervisor("9999");//SUPERVISOR VT
                request.setNombreSupervisor("CODIGO STANDAR");

                request.setCodigoTecnico("9999");//
                request.setNombreTecnico("CODIGO STANDAR");

                //TODO: buscar VT Cerrada--> ot Visita Tecnica
                request.setVt(subEdificioMgl.getVisitaTecnica() == null ? ""
                        : subEdificioMgl.getVisitaTecnica());//s/n
                request.setFechaVT(subEdificioMgl.getFechaVisitaTecnica() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaVisitaTecnica()));//sub-fecha ejecucion vt-->OT
                request.setCostoVT(subEdificioMgl.getCostoVisitaTecnica() == null ? ""
                        : subEdificioMgl.getCostoVisitaTecnica().toString());//sub - lo colocan de la VT

                request.setReDiseno(subEdificioMgl.getReDiseno() == null ? ""
                        : subEdificioMgl.getReDiseno());//S/N
                request.setFechaReporteDiseno(subEdificioMgl.getFechaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaReDiseno()));
                request.setFechaReporteDiseno(subEdificioMgl.getFechaRespuestaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaRespuestaReDiseno()));

                request.setCierre(subEdificioMgl.getCierre() == null ? ""
                        : subEdificioMgl.getCierre());//S/N
                request.setFechaRecibidoCierre(subEdificioMgl.getFechaRecibidoCierre() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaRecibidoCierre()));
                request.setMeta(subEdificioMgl.getMeta() == null ? ""
                        : subEdificioMgl.getMeta().toString());
                request.setFechaInicioEjecucion(subEdificioMgl.getFechaInicioEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaInicioEjecuion()));
                request.setFechaFinEjecucion(subEdificioMgl.getFechaFinEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaFinEjecuion()));
                request.setCostoEjecucion(subEdificioMgl.getCostoEjecucion() == null ? ""
                        : subEdificioMgl.getCostoEjecucion().toString());

                request.setRePlanos(subEdificioMgl.getPlanos() == null ? ""
                        : subEdificioMgl.getPlanos());//S/N
                request.setFechaSolicitudPlanos(subEdificioMgl.getFechaSolicitudPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaSolicitudPlanos()));
                request.setFechaEntregaPlanos(subEdificioMgl.getFechaEntregaPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaPlanos()));

                request.setConectRCorriente(subEdificioMgl.getConexionCorriente() == null ? ""
                        : subEdificioMgl.getConexionCorriente());//S/N
                request.setFechaSolicitudConectRCorriente(subEdificioMgl.getFechaSolicitudConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaSolicitudConexionCorriente()));
                request.setFechaEntregaConectRCorriente(subEdificioMgl.getFechaEntregaConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioMgl.getFechaEntregaConexionCorriente()));

                request.setNombreUsuario(usuario);
                /*
                 * Fin datos a modificar
                 */
                ResponseInformacionAdicionalEdificioList response =
                        restClientCuentasMatrices.callWebService(
                        EnumeratorServiceName.CM_INFORMACION_ADICIONAL_EDIFICIO_UPDATE,
                        ResponseInformacionAdicionalEdificioList.class,
                        request);
                if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                    result = false;
                    throw new ApplicationException("(As400)" + response.getMensaje());
                } else {
                    result = true;
                }
                //Llamar a tablas basicas de mantto edificio
                if (result) {
                    CmtTablasBasicasRRMglManager basicasRRMglManager = new CmtTablasBasicasRRMglManager();
                    basicasRRMglManager.edificioUpdate(cuentaMatriz, usuario);
                }
            }

        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            LOGGER.error("Error al momento de actualizar la CCMM. EX000: " + e.getMessage(), e);
            throw new ApplicationException("(As400-ws) " + e.getMessage(), e);
        }
        return result;

    }

    /**
     * Rgistra un BlackList a una cuenta en especifico en RR
     *
     * @param cmtBlackListMgl
     * @param usuario
     * @return boolean
     * @throws ApplicationException
     */
    public boolean createBlackListCm(CmtBlackListMgl cmtBlackListMgl, String usuario)
            throws ApplicationException {
        boolean result;
        boolean isMultiEdificio = cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().
                getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                && cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().
                getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);

        if (isMultiEdificio) {
            boolean isEdificioSelectedGeneral = cmtBlackListMgl.getSubEdificioObj().
                    getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtBlackListMgl.getSubEdificioObj().
                    getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (isEdificioSelectedGeneral) {
                result = manttoCompetenciaEdificioBlackListAdd(cmtBlackListMgl, usuario);
            } else {
                result = true;
            }
        } else {
            result = manttoCompetenciaEdificioBlackListAdd(cmtBlackListMgl, usuario);
        }

        return result;

    }

    /**
     * Elimina un blackList Asignado de la cuenta registrado en RR
     *
     * @param cmtBlackListMgl
     * @param usuario
     * @return boolean
     * @throws ApplicationException
     */
    public boolean updateBlackListCm(CmtBlackListMgl cmtBlackListMgl, String usuario)
            throws ApplicationException {
        boolean result;
        boolean isMultiEdificio = cmtBlackListMgl.getSubEdificioObj().getCmtCuentaMatrizMglObj().
                getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);

        if (isMultiEdificio) {
            boolean isEdificioSelectedGeneral = cmtBlackListMgl.getSubEdificioObj().getEstadoSubEdificioObj().
                    getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (isEdificioSelectedGeneral) {
                boolean validate = validateBlackList(cmtBlackListMgl);
                if (cmtBlackListMgl.getActivado().equalsIgnoreCase("N") && validate) {
                    result = manttoCompetenciaEdificioBlackListDelete(cmtBlackListMgl, usuario);
                } else if (cmtBlackListMgl.getActivado().equalsIgnoreCase("Y") && !validate) {
                    result = manttoCompetenciaEdificioBlackListAdd(cmtBlackListMgl, usuario);
                } else {
                    result = true;
                }
            } else {
                result = true;
            }
        } else {
            boolean validate = validateBlackList(cmtBlackListMgl);

            if (cmtBlackListMgl.getActivado().equalsIgnoreCase("N") && validate) {
                result = manttoCompetenciaEdificioBlackListDelete(cmtBlackListMgl, usuario);
            } else if (cmtBlackListMgl.getActivado().equalsIgnoreCase("Y") && !validate) {
                result = manttoCompetenciaEdificioBlackListAdd(cmtBlackListMgl, usuario);
            } else {
                result = true;
            }
        }

        return result;
    }

    /**
     * Validar si ya existe un BlackList Asignado a la cuenta
     *
     * @param cmtBlackListMgl
     * @return boolean
     * @throws ApplicationException
     */
    public boolean validateBlackList(CmtBlackListMgl cmtBlackListMgl)
            throws ApplicationException {
        boolean result = false;
        int position;

        String codigoBlackList = cmtBlackListMgl.getBlackListObj().getCodigoBasica();

        List<ResponseDataCompetenciaEdificioBlackList> blackListCuenta = manttoCompetenciaEdificioBlackListQuery(cmtBlackListMgl);

        for (position = 0; position < blackListCuenta.size(); position++) {
            if (blackListCuenta.get(position).getCodigo().equals(codigoBlackList)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * M&eacute;todo para generar el llamado del servicio web de RR para consultar inventario por CM
     * 
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} Cuenta matriz a verificar
     * @return {@link Boolean} validaci&oacute;n de inventario sobre la cuenta matriz
     * @throws ApplicationException Excepci&oacute;n lanzada por la petici&oacute;
     */
    public Boolean existeInventarioCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        try {
            RequestDataInventarioCuentaMatriz request = new RequestDataInventarioCuentaMatriz();
            request.setCodigoEdificio(cuentaMatriz.getNumeroCuenta().toString());
            ResponseInventarioCuentaMatrizList response = restClientCuentasMatrices.callWebService(
                    EnumeratorServiceName.CM_INVENTARIO_CUENTA_MATRIZ_QUERY,
                    ResponseInventarioCuentaMatrizList.class, request);
            if ("E".equals(response.getResultado()) && !SIN_DATOS.equals(response.getMensaje())) {
                throw new ApplicationException(String.format(
                        "Error consultando RR con la CM %s.\n%s",
                        cuentaMatriz.getCuentaMatrizId(),
                        response.getMensaje()));
            }
            return response.getListConsultaInventario() != null
                    && !response.getListConsultaInventario().isEmpty();
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar llamado (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }

    }

    /**
     * Actualiza la Informacion adicional de un SubEdificio en base a la 
     * información de VT ficha contructora. 
     * Permite actualizar la informacion adicional de un SubEdificio en RR.
     *
     * @author Andrés Leal
     * @param cmtVisitaTecnicaMgl Visita T&eacute;cnica.
     * @param usuarioOriginal Usuario.
     * @return si se realizo la actualizacion del subEdificio
     * @throws ApplicationException
     */
    public boolean updateInfoAdicionalSubEdificioVT(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl,
            String usuarioOriginal) throws ApplicationException {

        boolean result = false;
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
             //Obtenemos la cuenta matriz a la cual pertenece el subedificio
            CmtCuentaMatrizMgl cuentaMatriz = cmtVisitaTecnicaMgl.getOtObj().getCmObj();
            SimpleDateFormat dateFormatRr = new SimpleDateFormat("yyyyMMdd");
            //Obtenemos el subEdificio General de la  cuenta matriz
            CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();
            //validamos el tipo de cuenta Matriz
            boolean isMultiEdificio = subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                    && subEdificioGeneral.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (isMultiEdificio) {

                RequestDataInformacionAdicionalSubEdificio request =
                        new RequestDataInformacionAdicionalSubEdificio();

                request.setCodigoCuenta(cuentaMatriz.getNumeroCuenta().toString());//cm
                request.setNombreEdificio(cuentaMatriz.getNombreCuenta());//cm
                
                request.setCodigoSubEdificio(cuentaMatriz.getSubEdificiosMglNoGeneral().isEmpty() || cuentaMatriz.getSubEdificiosMglNoGeneral().get(0).getCodigoRr()== null ? ""
                        : cuentaMatriz.getSubEdificiosMglNoGeneral().get(0).getCodigoRr());
                request.setDescripcionSubedificio(cuentaMatriz.getSubEdificiosMglNoGeneral().isEmpty() || cuentaMatriz.getSubEdificiosMglNoGeneral().get(0).getNombreSubedificio() == null ? ""
                        : cuentaMatriz.getSubEdificiosMglNoGeneral().get(0).getNombreSubedificio());

                request.setCodigoMotivo("999");//
                request.setDescripcionMotivo("ACTUALIZACION MGL");

                //TODO: obtener info de la calle del sub edificio
                request.setNombreCalle(subEdificioGeneral.getCuentaMatrizObj().getDireccionPrincipal().getCalleRr());

                /*
                 * Inicio datos a modificar
                 */
                request.setCodigoOrigenDatos(subEdificioGeneral.getOrigenDatosObj() == null ? "1"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getOrigenDatosObj().getCodigoBasica(), 4));
                request.setDescripcionOrigenDatos(subEdificioGeneral.getOrigenDatosObj() == null ? "BARRIDOS CALLE"
                        : subEdificioGeneral.getOrigenDatosObj().getNombreBasica());

                request.setCodigoTipoProyecto(subEdificioGeneral.getTipoProyectoObj() == null ? "0001"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getTipoProyectoObj().getCodigoBasica(), 4));
                request.setNombreTipoProyecto(subEdificioGeneral.getTipoProyectoObj() == null ? "CONSTRUCTORA RESIDENCIAL"
                        : subEdificioGeneral.getTipoProyectoObj().getNombreBasica());

                request.setCodigoConstructor(subEdificioGeneral.getCompaniaConstructoraObj() == null ? "2000"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getCompaniaConstructoraObj().getCodigoRr(), 4));
                request.setNombreConstructor(subEdificioGeneral.getCompaniaConstructoraObj() == null ? "SIN COMPANIA"
                        : subEdificioGeneral.getCompaniaConstructoraObj().getNombreCompania());

                request.setFechaEntrega(subEdificioGeneral.getFechaEntregaEdificio() == null ? "19000101"
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaEdificio()));

                request.setCodigoEstado(subEdificioGeneral.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioGeneral.getEstadoSubEdificioObj().getCodigoBasica());
                request.setDescripcionEstado(subEdificioGeneral.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioGeneral.getEstadoSubEdificioObj().getNombreBasica());

                //TODO:buscar codigos
                request.setCodigoAsesor("9999");//ASESOR DE AVANZADA
                request.setNombreAsesor("CODIGO STANDAR");

                request.setCodigoEspecialista("9999");//ESPECIALISTA DE AVANZADA
                request.setNombreEspecialista("CODIGO STANDAR");

                request.setCodigoSupervisor("9999");//SUPERVISOR VT
                request.setNombreSupervisor("CODIGO STANDAR");

                request.setCodigoTecnico("9999");//
                request.setNombreTecnico("CODIGO STANDAR");

                //TODO: buscar VT Cerrada--> ot Visita Tecnica
                request.setVt(subEdificioGeneral.getVisitaTecnica() == null ? ""
                        : subEdificioGeneral.getVisitaTecnica());//s/n
                request.setFechaVT(subEdificioGeneral.getFechaVisitaTecnica() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaVisitaTecnica()));//sub-fecha ejecucion vt-->OT
                request.setCostoVT(subEdificioGeneral.getCostoVisitaTecnica() == null ? ""
                        : subEdificioGeneral.getCostoVisitaTecnica().toString());//sub - lo colocan de la VT

                request.setReDiseno(subEdificioGeneral.getReDiseno() == null ? ""
                        : subEdificioGeneral.getReDiseno());//S/N
                request.setFechaReporteDiseno(subEdificioGeneral.getFechaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaReDiseno()));
                request.setFechaRespuestaDiseno(subEdificioGeneral.getFechaRespuestaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaRespuestaReDiseno()));

                request.setCierre(subEdificioGeneral.getCierre() == null ? ""
                        : subEdificioGeneral.getCierre());//S/N
                request.setFechaRecibidoCierre(subEdificioGeneral.getFechaRecibidoCierre() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaRecibidoCierre()));
                request.setMeta(subEdificioGeneral.getMeta() == null ? ""
                        : subEdificioGeneral.getMeta().toString());
                request.setFechaInicioEjecucion(subEdificioGeneral.getFechaInicioEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaInicioEjecuion()));
                request.setFechaFinEjecucion(subEdificioGeneral.getFechaFinEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaFinEjecuion()));
                request.setCostoEjecucion(subEdificioGeneral.getCostoEjecucion() == null ? ""
                        : subEdificioGeneral.getCostoEjecucion().toString());

                request.setRePlanos(subEdificioGeneral.getPlanos() == null ? ""
                        : subEdificioGeneral.getPlanos());//S/N
                request.setFechaSolicitudPlanos(subEdificioGeneral.getFechaSolicitudPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaSolicitudPlanos()));
                request.setFechaEntregaPlanos(subEdificioGeneral.getFechaEntregaPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaPlanos()));

                request.setConectRCorriente(subEdificioGeneral.getConexionCorriente() == null ? ""
                        : subEdificioGeneral.getConexionCorriente());//S/N
                request.setFechaSolicitudConectRCorriente(subEdificioGeneral.getFechaSolicitudConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaSolicitudConexionCorriente()));
                request.setFechaEntregaConectRCorriente(subEdificioGeneral.getFechaEntregaConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaConexionCorriente()));

                request.setNombreUsuario(usuario);
                /*
                 * Fin datos a modificar
                 */

                ResponseInformacionAdicionalSubEdificioList response =
                        restClientCuentasMatrices.callWebService(
                        EnumeratorServiceName.CM_INFORMACION_ADICIONAL_SUBEDIFICIO_UPDATE,
                        ResponseInformacionAdicionalSubEdificioList.class,
                        request);

                if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                    result = false;
                    throw new ApplicationException("(As400)" + response.getMensaje());
                } else {
                    result = true;
                }

            } else {
                RequestDataInformacionAdicionalEdificio request = new RequestDataInformacionAdicionalEdificio();

                request.setCodigoEdificio(subEdificioGeneral.getCmtCuentaMatrizMglObj().getNumeroCuenta() == null ? ""
                        : subEdificioGeneral.getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());
                request.setDescripcionEdificio(subEdificioGeneral.getNombreSubedificio() == null ? ""
                        : subEdificioGeneral.getNombreSubedificio());

                request.setCodigoMotivo("");//
                request.setDescripcionMotivo("");

                /*
                 * Inicio datos a modificar
                 */
                request.setCodigoEstado(subEdificioGeneral.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioGeneral.getEstadoSubEdificioObj().getCodigoBasica());
                request.setDescripcionEstado(subEdificioGeneral.getEstadoSubEdificioObj() == null ? ""
                        : subEdificioGeneral.getEstadoSubEdificioObj().getNombreBasica().toUpperCase());

                request.setCodigoOrigenDatos(subEdificioGeneral.getOrigenDatosObj() == null ? "1"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getOrigenDatosObj().getCodigoBasica(), 4));
                request.setDescripcionOrigenDatos(subEdificioGeneral.getOrigenDatosObj() == null ? "BARRIDOS CALLE"
                        : subEdificioGeneral.getOrigenDatosObj().getNombreBasica());

                request.setCodigoTipoProyecto(subEdificioGeneral.getTipoProyectoObj() == null ? "0001"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getTipoProyectoObj().getCodigoBasica(), 4));
                request.setNombreTipoProyecto(subEdificioGeneral.getTipoProyectoObj() == null ? "CONSTRUCTORA RESIDENCIAL"
                        : subEdificioGeneral.getTipoProyectoObj().getNombreBasica());

                request.setCodigoConstructor(subEdificioGeneral.getCompaniaConstructoraObj() == null ? "2000"
                        : CmtUtilidadesCM.strCNum(subEdificioGeneral.getCompaniaConstructoraObj().getCodigoRr(), 4));
                request.setNombreConstructor(subEdificioGeneral.getCompaniaConstructoraObj() == null ? "SIN COMPANIA"
                        : subEdificioGeneral.getCompaniaConstructoraObj().getNombreCompania());

                request.setFechaEntrega(subEdificioGeneral.getFechaEntregaEdificio() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaEdificio()));

                //TODO:buscar codigos
                request.setCodigoAsesor("9999");//ASESOR DE AVANZADA
                request.setNombreAsesor("CODIGO STANDAR");

                request.setCodigoEspecialista("9999");//ESPECIALISTA DE AVANZADA
                request.setNombreEspecialista("CODIGO STANDAR");

                request.setCodigoSupervisor("9999");//SUPERVISOR VT
                request.setNombreSupervisor("CODIGO STANDAR");

                request.setCodigoTecnico("9999");//
                request.setNombreTecnico("CODIGO STANDAR");

                //TODO: buscar VT Cerrada--> ot Visita Tecnica
                request.setVt(subEdificioGeneral.getVisitaTecnica() == null ? ""
                        : subEdificioGeneral.getVisitaTecnica());//s/n
                request.setFechaVT(subEdificioGeneral.getFechaVisitaTecnica() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaVisitaTecnica()));//sub-fecha ejecucion vt-->OT
                request.setCostoVT(subEdificioGeneral.getCostoVisitaTecnica() == null ? ""
                        : subEdificioGeneral.getCostoVisitaTecnica().toString());//sub - lo colocan de la VT

                request.setReDiseno(subEdificioGeneral.getReDiseno() == null ? ""
                        : subEdificioGeneral.getReDiseno());//S/N
                request.setFechaReporteDiseno(subEdificioGeneral.getFechaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaReDiseno()));
                request.setFechaReporteDiseno(subEdificioGeneral.getFechaRespuestaReDiseno() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaRespuestaReDiseno()));

                request.setCierre(subEdificioGeneral.getCierre() == null ? ""
                        : subEdificioGeneral.getCierre());//S/N
                request.setFechaRecibidoCierre(subEdificioGeneral.getFechaRecibidoCierre() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaRecibidoCierre()));
                request.setMeta(subEdificioGeneral.getMeta() == null ? ""
                        : subEdificioGeneral.getMeta().toString());
                request.setFechaInicioEjecucion(subEdificioGeneral.getFechaInicioEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaInicioEjecuion()));
                request.setFechaFinEjecucion(subEdificioGeneral.getFechaFinEjecuion() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaFinEjecuion()));
                request.setCostoEjecucion(subEdificioGeneral.getCostoEjecucion() == null ? ""
                        : subEdificioGeneral.getCostoEjecucion().toString());

                request.setRePlanos(subEdificioGeneral.getPlanos() == null ? ""
                        : subEdificioGeneral.getPlanos());//S/N
                request.setFechaSolicitudPlanos(subEdificioGeneral.getFechaSolicitudPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaSolicitudPlanos()));
                request.setFechaEntregaPlanos(subEdificioGeneral.getFechaEntregaPlanos() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaPlanos()));

                request.setConectRCorriente(subEdificioGeneral.getConexionCorriente() == null ? ""
                        : subEdificioGeneral.getConexionCorriente());//S/N
                request.setFechaSolicitudConectRCorriente(subEdificioGeneral.getFechaSolicitudConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaSolicitudConexionCorriente()));
                request.setFechaEntregaConectRCorriente(subEdificioGeneral.getFechaEntregaConexionCorriente() == null ? ""
                        : dateFormatRr.format(subEdificioGeneral.getFechaEntregaConexionCorriente()));

                request.setNombreUsuario(usuario);
                /*
                 * Fin datos a modificar
                 */
                ResponseInformacionAdicionalEdificioList response =
                        restClientCuentasMatrices.callWebService(
                        EnumeratorServiceName.CM_INFORMACION_ADICIONAL_EDIFICIO_UPDATE,
                        ResponseInformacionAdicionalEdificioList.class,
                        request);
                if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                    result = false;
                    throw new ApplicationException("(As400)" + response.getMensaje());
                } else {
                    result = true;
                }

            }

        } catch (ApplicationException | UniformInterfaceException | IOException e) {
            LOGGER.error("Error al momento de actualizar la CCMM. EX000: " + e.getMessage(), e);
            throw new ApplicationException("(As400-ws) " + e.getMessage(), e);
        }
        return result;

    }
    
    public String obtenerCalleWithCorregimiento(CmtCuentaMatrizMgl cuenta, String calleRr) throws ApplicationException {
   
        if (cuenta != null
                && cuenta.getCentroPoblado() != null
                && cuenta.getCentroPoblado().getGpoId() != null) {

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPobladoCompleto;
            centroPobladoCompleto = geograficoPoliticoManager.findGeoPoliticoById(cuenta.getCentroPoblado().getGpoId());
            
            if (centroPobladoCompleto != null) {
                if (centroPobladoCompleto.getCorregimiento() != null
                        && !centroPobladoCompleto.getCorregimiento().isEmpty()
                        && centroPobladoCompleto.getCorregimiento().equalsIgnoreCase("Y")) {
                    
                    calleRr += " ";
                    String [] corregimiento = centroPobladoCompleto.getGpoNombre().split(" ");
                    for (int i = 0; i < corregimiento.length; i++) {
                        calleRr += corregimiento[i];
                    }                    
                   
                    if (calleRr.trim().length() > 50) {
                        throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRr + "] al añadir el corregimiento.");
                    }
                }
            }
        }     
        return calleRr;
    }
}
