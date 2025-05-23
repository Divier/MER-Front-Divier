package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCmSubEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataNotasOtCuentaMatriz;
import co.com.claro.cmas400.ejb.request.RequestDataOtEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataOtSubEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCmSubEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseNotasOtCuentaMatrizList;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.cmas400.ejb.respons.ResponseOtSubEdificiosList;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoRrMglDaoImpl;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientOrdenTrabajo;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoRrMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import com.google.gson.Gson;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Orden de Trabajo RR. Contiene la logica de negocio de las ordenes de
 * trabajo de RR en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtOrdenTrabajoRrMglManager {

    private final CmtOrdenTrabajoRrMglDaoImpl daoImpl = new CmtOrdenTrabajoRrMglDaoImpl();
    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoRrMglManager.class);
    ParametrosMglManager parametrosMglManager;
    RestClientOrdenTrabajo restClientOrdenTrabajo;
    OnyxOtCmDirManager onyxOtCmDirManager;
    String BASE_URI;

    public CmtOrdenTrabajoRrMglManager() throws ApplicationException {
        parametrosMglManager = new ParametrosMglManager();
        BASE_URI = parametrosMglManager.findByAcronimo(
                co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                .iterator().next().getParValor();
        restClientOrdenTrabajo = new RestClientOrdenTrabajo(BASE_URI);
        onyxOtCmDirManager = new OnyxOtCmDirManager();
                
    }

    public CmtOrdenTrabajoRrMgl create(CmtOrdenTrabajoRrMgl otRr,
            String usuarioOriginal, int perfil) throws ApplicationException {
        //TODO: crear Ot en RR
        String usuario = StringUtils.retornaNameUser(usuarioOriginal);
        
        String numeroOtRr = ordenTrabajoEdificioAdd(otRr, usuario);
        if (numeroOtRr != null && !numeroOtRr.isEmpty()) {
            otRr.setIdRr(new BigDecimal(numeroOtRr));
            return daoImpl.createCm(otRr, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible crear la Orden de trabajo en RR");
        }
    }

    public String crearOrdenTrabajoRr(CmtOrdenTrabajoRrMgl otRr, String usuario, int perfil) throws ApplicationException {
        String numeroOtRr = ordenTrabajoEdificioAdd(otRr, usuario);
        return numeroOtRr;
    }

    /*
     * Tabla Orden de Trabajo Edificio
     */
    //Modificar Recibe Parametros
    private String ordenTrabajoEdificioAdd(CmtOrdenTrabajoRrMgl otRr, String usuarioOriginal) throws ApplicationException {
        String result = "";
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            CmtCuentaMatrizMgl cuentaMatriz = otRr.getOtObj().getCmObj();
            RequestDataOtEdificio request = new RequestDataOtEdificio();
            request.setTipoTrabajo(otRr.getTipoTrabajoRR().getCodigoBasica());//TipoTrabajo
            request.setCodigoCuenta(cuentaMatriz.getNumeroCuenta().toString());
            request.setDealer("9999");
            if (otRr.getOtObj() != null && otRr.getOtObj().getOnyxOtHija() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtCmAndOtHija(otRr.getOtObj().getIdOt(), otRr.getOtObj().getOnyxOtHija().toString());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    request.setNitCliente(objOnyx.getNit_Cliente_Onyx());
                    request.setNumeroOTPadre(objOnyx.getOnyx_Ot_Padre_Cm());
                    request.setCodigoServicio(objOnyx.getServicios_Onyx());
                    request.setNombreCliente(objOnyx.getNombre_Cliente_Onyx());
                    request.setSegmento(objOnyx.getSegmento_Onyx());
                    request.setNombreOTHija(objOnyx.getNombre_Ot_Hija_Onyx());
                    request.setContactoOTPadre(objOnyx.getContacto_Tecnico_Ot_Padre_Onyx());
                    request.setTipoServicio(objOnyx.getTipo_Servicio_Onyx());
                    request.setNumeroOThija(objOnyx.getOnyx_Ot_Hija_Cm());
                    request.setServicios(objOnyx.getServicios_Onyx());
                }
            }
            
            //TODO:Observacion
            request.setObservacion1("Orden de Trabajo desde MGL");

            request.setNombreUsuario(usuario);
            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_ADD,
                    ResponseOtEdificiosList.class,
                    request);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
            if (responseOtEdificiosList.getListOtEdificios() != null
                    && !responseOtEdificiosList.getListOtEdificios().isEmpty()
                    && responseOtEdificiosList.getMensaje() != null
                    && !responseOtEdificiosList.getMensaje().trim().isEmpty()) {
                result = responseOtEdificiosList.getMensaje().substring(responseOtEdificiosList.getMensaje().length() - 5);
            } else {
                throw new ApplicationException("(As400) Orden de trabajo no creado");
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return result;
    }

    public boolean actualizarEstadoResultadoOTRR(CmtOrdenTrabajoRrMgl otRr, String numeroOt, 
            String usuarioOriginal, CmtEstadoxFlujoMgl estadoFlujo, boolean isIncial, String notaCierre) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            CmtCuentaMatrizMgl cuentaMatriz = otRr.getOtObj().getCmObj();
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();
            requestDataOtEdificio.setNumeroTrabajo(numeroOt);
            requestDataOtEdificio.setNombreUsuario(usuario);
            requestDataOtEdificio.setCodigoCuenta(cuentaMatriz.getNumeroCuenta().toString());
            if (isIncial) {
                requestDataOtEdificio.setResultadoModificacion(estadoFlujo.getEstadoOtRRInicial().getCodigoBasica());
            } else {
                requestDataOtEdificio.setObservacion1(notaCierre);
                requestDataOtEdificio.setResultadoModificacion(estadoFlujo.getEstadoOtRRFinal().getCodigoBasica());
            }
            
            if (otRr.getOtObj() != null && otRr.getOtObj().getOnyxOtHija() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtCmAndOtHija(otRr.getOtObj().getIdOt(), otRr.getOtObj().getOnyxOtHija().toString());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    requestDataOtEdificio.setNitCliente(objOnyx.getNit_Cliente_Onyx());
                    requestDataOtEdificio.setNumeroOTPadre(objOnyx.getOnyx_Ot_Padre_Cm());
                    requestDataOtEdificio.setCodigoServicio(objOnyx.getServicios_Onyx());
                    requestDataOtEdificio.setNombreCliente(objOnyx.getNombre_Cliente_Onyx());
                    requestDataOtEdificio.setSegmento(objOnyx.getSegmento_Onyx());
                    requestDataOtEdificio.setNombreOTHija(objOnyx.getNombre_Ot_Hija_Onyx());
                    requestDataOtEdificio.setContactoOTPadre(objOnyx.getContacto_Tecnico_Ot_Padre_Onyx());
                    requestDataOtEdificio.setTipoServicio(objOnyx.getTipo_Servicio_Onyx());
                    requestDataOtEdificio.setNumeroOThija(objOnyx.getOnyx_Ot_Hija_Cm());
                    requestDataOtEdificio.setServicios(objOnyx.getServicios_Onyx());
                }
            }

            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_UPDATE,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            Gson converter = new Gson();
            LOGGER.info("Request: "+converter.toJson(requestDataOtEdificio)+"\n\n");
            LOGGER.info("Response: "+converter.toJson(responseOtEdificiosList)+"\n\n");
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros    
    public boolean ordenTrabajoEdificioUpdate(CmtOrdenTrabajoRrMgl otRr, String usuarioOriginal) throws ApplicationException {
        try {
            //FCP
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();
            requestDataOtEdificio.setUsuarioGrabacion(usuario);
            requestDataOtEdificio.setEstado(String.valueOf(otRr.getEstadoRegistro()));

            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_UPDATE,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }
/**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param otRr orden de Rr
     * @param numeroOt numero de ot a eliminar
     * @param usuario que realiza la transacion
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     */

    public boolean ordenTrabajoEdificioDelete(CmtOrdenTrabajoRrMgl otRr, String numeroOt, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();
            CmtCuentaMatrizMgl cuentaMatriz = otRr.getOtObj().getCmObj();
            requestDataOtEdificio.setNumeroTrabajo(numeroOt);
            requestDataOtEdificio.setNombreUsuario(usuario);
            requestDataOtEdificio.setNumeroEdificio(cuentaMatriz.getNumeroCuenta().toString());
            requestDataOtEdificio.setObservacion2("Orden de trabajo cancelada desde MER  por error al momento de agendar");
            
            if (otRr.getOtObj() != null && otRr.getOtObj().getOnyxOtHija() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtCmAndOtHija(otRr.getOtObj().getIdOt(), otRr.getOtObj().getOnyxOtHija().toString());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    requestDataOtEdificio.setNitCliente(objOnyx.getNit_Cliente_Onyx());
                    requestDataOtEdificio.setNumeroOTPadre(objOnyx.getOnyx_Ot_Padre_Cm());
                    requestDataOtEdificio.setCodigoServicio(objOnyx.getServicios_Onyx());
                    requestDataOtEdificio.setNombreCliente(objOnyx.getNombre_Cliente_Onyx());
                    requestDataOtEdificio.setSegmento(objOnyx.getSegmento_Onyx());
                    requestDataOtEdificio.setNombreOTHija(objOnyx.getNombre_Ot_Hija_Onyx());
                    requestDataOtEdificio.setContactoOTPadre(objOnyx.getContacto_Tecnico_Ot_Padre_Onyx());
                    requestDataOtEdificio.setTipoServicio(objOnyx.getTipo_Servicio_Onyx());
                    requestDataOtEdificio.setNumeroOThija(objOnyx.getOnyx_Ot_Hija_Cm());
                    requestDataOtEdificio.setServicios(objOnyx.getServicios_Onyx());
                }
            }

            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_DELETE,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Tabla Orden de Trabajo SubEdificio
     */
    //Modificar Recibe Parametros
    private boolean ordenTrabajoSubEdificioAdd(CmtOrdenTrabajoRrMgl otRr,
            String usuarioOriginal) throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtSubEdificio request = new RequestDataOtSubEdificio();
            CmtCuentaMatrizMgl cuentaMatriz = otRr.getOtObj().getCmObj();
            CmtSubEdificioMgl subEdificioGeneral = cuentaMatriz.getSubEdificioGeneral();

            request.setTipoTrabajo(otRr.getTipoOtRrObj().getTipoOtRr().getCodigoBasica());
            request.setNombreTipoTrabajo(otRr.getTipoOtRrObj().getTipoOtRr().getNombreBasica());

            request.setCodigoCuenta(cuentaMatriz.getNumeroCuenta().toString());
            request.setNombreCuenta(cuentaMatriz.getNombreCuenta());
            request.setNumeroTrabajo("00000");

            request.setTipoEdificio(subEdificioGeneral.getTipoEdificioObj().getCodigoBasica());
            request.setNombreTipoEdificio(subEdificioGeneral.getTipoEdificioObj().getNombreBasica());

            request.setNombreEstado(subEdificioGeneral.getEstadoSubEdificioObj().getNombreBasica());
            //revisar
            request.setDireccion(subEdificioGeneral.getDireccion());

            request.setTelefono1(subEdificioGeneral.getTelefonoPorteria());
            request.setTelefono2(subEdificioGeneral.getTelefonoPorteria2());
            //Revisar
            request.setContacto(subEdificioGeneral.getAdministrador());

            request.setTotalUnidades(String.valueOf(subEdificioGeneral.getTotalUnidadePlaneadas()));
            request.setTotalHomePassed(String.valueOf(subEdificioGeneral.getHogares()));

            request.setUsuarioGrabacion(otRr.getUsuarioCreacion());

            request.setDealer("9999");
            request.setNombreDealer("CODIGO STANDAR");

            //TODO:Observacion
            request.setObservacion1("Prueba Observacion");

            request.setArregloCodigo("0000");
            request.setArregloNombre("");
            request.setFechaProgramacion("20160329");
            request.setHoraProgramacion("000000");
            request.setNombreUsuario(usuario);

            ResponseOtSubEdificiosList responseOtSubEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_SUBEDIFICIO_ADD,
                    ResponseOtSubEdificiosList.class,
                    request);
            if (responseOtSubEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtSubEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros
    private boolean ordenTrabajoSubEdificioUpdate() throws ApplicationException {
        try {
            RequestDataOtSubEdificio requestDataOtSubEdificio = new RequestDataOtSubEdificio();
            ResponseOtSubEdificiosList responseOtSubEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_SUBEDIFICIO_UPDATE,
                    ResponseOtSubEdificiosList.class,
                    requestDataOtSubEdificio);
            if (responseOtSubEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtSubEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Notas OT Cuenta Matriz
     */
    //Modificar Recibe Parametros
    private boolean notasOtCuentaMatrizAdd() throws ApplicationException {
        try {
            RequestDataNotasOtCuentaMatriz requestDataNotasOtCuentaMatriz = new RequestDataNotasOtCuentaMatriz();
            ResponseNotasOtCuentaMatrizList responseNotasOtCuentaMatrizList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_NOTAS_OT_CUENTA_MATRIZ_ADD,
                    ResponseNotasOtCuentaMatrizList.class,
                    requestDataNotasOtCuentaMatriz);
            if (responseNotasOtCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseNotasOtCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros
    private boolean notasOtCuentaMatrizUpdate() throws ApplicationException {
        try {
            RequestDataNotasOtCuentaMatriz requestDataNotasOtCuentaMatriz = new RequestDataNotasOtCuentaMatriz();
            ResponseNotasOtCuentaMatrizList responseNotasOtCuentaMatrizList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_NOTAS_OT_CUENTA_MATRIZ_UPDATE,
                    ResponseNotasOtCuentaMatrizList.class,
                    requestDataNotasOtCuentaMatriz);
            if (responseNotasOtCuentaMatrizList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseNotasOtCuentaMatrizList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /*
     * Notas OT Cuenta Matriz SubEdificio
     */
    //Modificar Recibe Parametros
    private boolean notasOtCmSubEdificioAdd() throws ApplicationException {
        try {
            RequestDataNotasOtCmSubEdificio requestDataNotasOtCmSubEdificio = new RequestDataNotasOtCmSubEdificio();
            ResponseNotasOtCmSubEdificioList responseNotasOtCmSubEdificioList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_NOTAS_OT_CM_SUBEDIFICIO_ADD,
                    ResponseNotasOtCmSubEdificioList.class,
                    requestDataNotasOtCmSubEdificio);
            if (responseNotasOtCmSubEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseNotasOtCmSubEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    //Modificar Recibe Parametros
    private boolean notasOtCmSubEdificioUpdate() throws ApplicationException {
        try {
            RequestDataNotasOtCmSubEdificio requestDataNotasOtCmSubEdificio = new RequestDataNotasOtCmSubEdificio();
            ResponseNotasOtCmSubEdificioList responseNotasOtCmSubEdificioList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_NOTAS_OT_CM_SUBEDIFICIO_UPDATE,
                    ResponseNotasOtCmSubEdificioList.class,
                    requestDataNotasOtCmSubEdificio);
            if (responseNotasOtCmSubEdificioList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseNotasOtCmSubEdificioList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
           String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return true;
    }

    /**
     * Metodo para consultar los trabajos que tiene una CM en RR
     *
     * @author Victor bocanegra
     * @param numeroCuenta cnumero de la cuenta matriz
     * @return ResponseOtEdificiosList encontrado
     * @throws ApplicationException
     */
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(String numeroCuenta)
            throws ApplicationException {

        ResponseOtEdificiosList responseOtEdificiosList = new ResponseOtEdificiosList();
        try {
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();
            requestDataOtEdificio.setNumeroEdificio(numeroCuenta);

            responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_QUERY,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                if (responseOtEdificiosList.getListOtEdificios().isEmpty()
                        && responseOtEdificiosList.getMensaje().equalsIgnoreCase(Constant.NO_TIENE_TRABAJOS)) {
                    return responseOtEdificiosList;
                } else {
                    throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
                }
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  ex);
        }
        return responseOtEdificiosList;
    }
    
    public String crearOrdenTrabajoRrHhpp(String numeroCuentaParametro, 
            String codTipoTrabajo, String usuario, OtHhppMgl ot) throws ApplicationException {
        String numeroOtRr = ordenTrabajoEdificioAddHhpp(numeroCuentaParametro, codTipoTrabajo, usuario, ot);
        return numeroOtRr;
    }

    
    /**
     * Actualiza la una ot en RR creada desde direcciones
     *
     * @author victor bocanegra
     * @param numeroCuentaParametro numero de la cuenta a la que esta asociada
     * la orden
     * @param numeroOT numero de la orden en RR
     * @param usuario
     * @param tipoOtHhppMgl
     * @param isIncial tipo de cambio de estado
     * @param idOt identificacion de la orden de direcciones
     * @return true or false
     */
    public boolean actualizarEstadoResultadoOTRRHhpp(String numeroCuentaParametro, String numeroOt,
            String usuarioOriginal, TipoOtHhppMgl tipoOtHhppMgl, boolean isIncial,
            OtHhppMgl ot, String notaCierre)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();
            requestDataOtEdificio.setNumeroTrabajo(numeroOt);
            requestDataOtEdificio.setNombreUsuario(usuario);
            requestDataOtEdificio.setCodigoCuenta(numeroCuentaParametro);
            if (isIncial) {
                requestDataOtEdificio.setResultadoModificacion(tipoOtHhppMgl.getEstadoOtRRInicial().getCodigoBasica());
            } else {
                requestDataOtEdificio.setObservacion1(notaCierre);
                requestDataOtEdificio.setResultadoModificacion(tipoOtHhppMgl.getEstadoOtRRFinal().getCodigoBasica());
            }
            
            if (ot.getOtHhppId() != null && ot.getOnyxOtHija() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtHhppAndOtHija(ot.getOtHhppId(), ot.getOnyxOtHija().toString());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    requestDataOtEdificio.setNitCliente(objOnyx.getNit_Cliente_Onyx());
                    requestDataOtEdificio.setNumeroOTPadre(objOnyx.getOnyx_Ot_Padre_Dir());
                    requestDataOtEdificio.setCodigoServicio(objOnyx.getServicios_Onyx());
                    requestDataOtEdificio.setNombreCliente(objOnyx.getNombre_Cliente_Onyx());
                    requestDataOtEdificio.setSegmento(objOnyx.getSegmento_Onyx());
                    requestDataOtEdificio.setNombreOTHija(objOnyx.getNombre_Ot_Hija_Onyx());
                    requestDataOtEdificio.setContactoOTPadre(objOnyx.getContacto_Tecnico_Ot_Padre_Onyx());
                    requestDataOtEdificio.setTipoServicio(objOnyx.getTipo_Servicio_Onyx());
                    requestDataOtEdificio.setNumeroOThija(objOnyx.getOnyx_Ot_Hija_Dir());
                    requestDataOtEdificio.setServicios(objOnyx.getServicios_Onyx());
                }
            }

            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_UPDATE,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return true;
    }

    /*
     * Tabla Orden de Trabajo Edificio
     */
    private String ordenTrabajoEdificioAddHhpp(String numeroCuentaParametro, String codTipoTrabajo,
            String usuarioOriginal, OtHhppMgl ot) throws ApplicationException {
        String result = "";
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtEdificio request = new RequestDataOtEdificio();
            request.setTipoTrabajo(codTipoTrabajo);//TipoTrabajo
            request.setCodigoCuenta(numeroCuentaParametro);
            request.setDealer("9999");
            
            if (ot.getOtHhppId() != null && ot.getOnyxOtHija() != null) {
                List<OnyxOtCmDir> listaOnyx = onyxOtCmDirManager.findOnyxByOtHhppAndOtHija(ot.getOtHhppId(), ot.getOnyxOtHija().toString());
                if (listaOnyx != null && !listaOnyx.isEmpty()) {
                    OnyxOtCmDir objOnyx = listaOnyx.get(0);
                    request.setNitCliente(objOnyx.getNit_Cliente_Onyx());
                    request.setNumeroOTPadre(objOnyx.getOnyx_Ot_Padre_Dir());
                    request.setCodigoServicio(objOnyx.getServicios_Onyx());
                    request.setNombreCliente(objOnyx.getNombre_Cliente_Onyx());
                    request.setSegmento(objOnyx.getSegmento_Onyx());
                    request.setNombreOTHija(objOnyx.getNombre_Ot_Hija_Onyx());
                    request.setContactoOTPadre(objOnyx.getContacto_Tecnico_Ot_Padre_Onyx());
                    request.setTipoServicio(objOnyx.getTipo_Servicio_Onyx());
                    request.setNumeroOThija(objOnyx.getOnyx_Ot_Hija_Dir());
                    request.setServicios(objOnyx.getServicios_Onyx());
                }
            }

            //TODO:Observacion
            request.setObservacion1("Orden de Trabajo desde MGL");

            request.setNombreUsuario(usuario);
            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_ADD,
                    ResponseOtEdificiosList.class,
                    request);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
            if (responseOtEdificiosList.getListOtEdificios() != null
                    && !responseOtEdificiosList.getListOtEdificios().isEmpty()
                    && responseOtEdificiosList.getMensaje() != null
                    && !responseOtEdificiosList.getMensaje().trim().isEmpty()) {
                result = responseOtEdificiosList.getMensaje().substring(responseOtEdificiosList.getMensaje().length() - 5);
            } else {
                throw new ApplicationException("(As400) Orden de trabajo no creado");
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return result;
    }

    /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param numeroCuentaParametro
     * @param numeroOt numero de ot a eliminar
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean ordenTrabajoEdificioDeleteHhpp(String numeroCuentaParametro, String numeroOt, String usuarioOriginal)
            throws ApplicationException {
        try {
            String usuario = StringUtils.retornaNameUser(usuarioOriginal);
            
            RequestDataOtEdificio requestDataOtEdificio = new RequestDataOtEdificio();

            requestDataOtEdificio.setNumeroTrabajo(numeroOt);
            requestDataOtEdificio.setNombreUsuario(usuario);
            requestDataOtEdificio.setNumeroEdificio(numeroCuentaParametro);
            requestDataOtEdificio.setObservacion2("Orden de trabajo cancelada desde MER  por error al momento de agendar");

            ResponseOtEdificiosList responseOtEdificiosList = restClientOrdenTrabajo.callWebService(
                    EnumeratorServiceName.OT_ORDEN_TRABAJO_EDIFICIO_DELETE,
                    ResponseOtEdificiosList.class,
                    requestDataOtEdificio);
            if (responseOtEdificiosList.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                throw new ApplicationException("(As400)" + responseOtEdificiosList.getMensaje());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar (As400-ws) '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return true;
    }
    
}
