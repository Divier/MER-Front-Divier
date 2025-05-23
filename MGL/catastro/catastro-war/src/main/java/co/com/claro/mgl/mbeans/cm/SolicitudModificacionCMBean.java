
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.cmas400.ejb.respons.ResponseDataManttoEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.dtos.CmtNodoValidado;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.RRNodosFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtModCcmmAudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtNotasSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidadorDireccionesFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtModificacionesCcmmAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.CmtCoverEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hitss Colombia
 *
 */
@ManagedBean(name = "solicitudModificacionCMBean")
@SessionScoped
public class SolicitudModificacionCMBean implements Serializable {
    
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioFacadeLocal;
    @EJB
    private CmtCompaniaMglFacadeLocal companiaFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtDireccionSolicitudMglFacadeLocal direccionSolMglFacadeLocal;
    @EJB
    private CmtDireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudFacadeLocal;
    @EJB
    private CmtSolicitudSubEdificioMglFacadeLocal solicitudSubEdificioFacadeLocal;
    @EJB
    private CmtNotasSolicitudMglFacadeLocal cmtNotasSolicitudMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal validadorDireccionFacadeLocal;
    @EJB
    private RRNodosFacadeLocal rRNodosFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private  DireccionMglFacadeLocal  direccionMglFacadeLocal1;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtModCcmmAudMglFacadeLocal cmtModCcmmAudMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
            
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudModificacionCMBean.class);
    private final String FORMULARIO = "SOLICITUDMODIFICARSUBEDIFICIOSCM";
    private String usuarioVT = null;
    private String direccion = "";
    private String nombreGenSubEdiText = "";
    private String nombreGenSubEdiSel = "";
    private CmtBasicaMgl tipoEdificioNuevo;
    private int conteoNombreGenSubEdi = 0;
    private DrDireccion drDireccion;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private Boolean modoGestion;
    private Boolean mostrarGestionHHPP;
    private Boolean mostrarGestionDR;
    private Boolean mostrarCrearSubedificio;
    private Boolean hayModDirPrincipal;
    private Boolean hayModSubedificios;
    private Boolean hayModDirecciones;
    private Boolean creacionExitosa;
    private Boolean otroNodoSolicitud = false;
    private Boolean cambioNodoCm = false;
    private Boolean bloquearCambioPropiaDireccion = false;
    private Boolean bloquearCambioNombreEdificio = false;
    public boolean hayCambioDatosCm = false;
    public boolean hayCambioDireccion = false;
    public boolean hayCambioSubEdificios = false;
    public boolean hayCambioCobertura = false;
    boolean isMultiEdificio;
    boolean isSubEdiDirPropia;
    private int perfilVT = 0;
    private String cedulaUsuarioVT = null;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private CmtSolicitudCmMgl solicitudModCM;
    private CmtSolicitudSubEdificioMgl solSubEdificioNuevo;
    private CmtSolicitudSubEdificioMgl solSubEdificioSelecMod;
    private CmtSolicitudSubEdificioMgl solCoberturaSelecMod;
    private CmtDireccionSolicitudMgl direccionSolSelecMod;
    private CmtSolicitudSubEdificioMgl subEdificioGeneralMod;
    private EncabezadoSolicitudModificacionCMBean encabezadoSolModiCMBean;
    private ValidadorDireccionBean validadorDireccionBean;
    private UsuariosServicesDTO usuarioLogin;
    private List<CmtSolicitudSubEdificioMgl> subEdificiosModList;
    private List<CmtSolicitudSubEdificioMgl> coberturaModList;
    private List<CmtSolicitudSubEdificioMgl> solicitudesSubEdificiosList;
    private List<CmtSolicitudSubEdificioMgl> solicitudesCoberturasList;
    private List<CmtDireccionSolicitudMgl> direccionSolModList;
    private List<CmtDireccionSolicitudMgl> solicitudesDireccionesList;
    private List<CmtCompaniaMgl> companiaAdministacionList;
    private List<CmtCompaniaMgl> companiaAscensoresList;
    private List<CmtNotasSolicitudMgl> notasSolicitudList;
    private List<CmtBasicaMgl> tipoSubEdificioList;
    private List<CmtBasicaMgl> tipoEdificioList;
    private HashMap<BigDecimal, List<NodoMgl>> listaNodosSubEdificio;
    private HashMap<BigDecimal, CmtCuentaMatrizMgl> listaOtrasCmDireccion;
    private HashMap<BigDecimal, List<String>> listaBarriosDireccion;
    private List<NodoMgl> listaNodosDireccionPrincipal;
    private List<CmtBasicaMgl> estadoSubEdificioList;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private CmtCuentaMatrizMgl cmtCuentaMatrizMglOtraDireccion = null;
    private CmtBasicaMgl basicaEstadoSinVisitaTecnica;
    private SecurityLogin securityLogin;
    private Boolean validarEdicion;
    private Boolean validarBorrado;
    private List<CmtBasicaMgl> listaTecnologiasBasica;
    private List<CmtTecnologiaSubMgl> lstCmtTecnologiaSubMgls;
    private boolean mostrarTabla;
    private CmtBasicaMgl tecnologiaSelect;
    private List<NodoMgl> lstNodosCobertura;    
    private NodoMgl nodoCobertura;
    private int control = 0;
    private boolean habilitarCombo;
    private Map<CmtSolicitudSubEdificioMgl, String> datosGestion;
    private List<String> barrioslist;
    private String selectedBarrio;
    private boolean otroBarrio = false;
    private final String VALIDARCREARSOLMODCM= "VALIDARCREARSOLMODCM";

    /**
     * Crea una nueva instancia se SolicitudModificacionCMBean
     */
    public SolicitudModificacionCMBean() {
        
        try {
            datosGestion = new HashMap<>();
            
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();
            
        } catch (IOException ex) {
            LOGGER.error("Se genera error en " + SolicitudModificacionCMBean.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        } catch (Exception ex) {
            LOGGER.error("Se genera error en " + SolicitudModificacionCMBean.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        }
    }
    
    @PostConstruct
    public void init() {
        
        generarListasSeleccionFormulario();
        
    }

    /**
     * Metodo de ingreso para la creacion o gestión de la Solicitud. Modo de
     * Ingreso 'Creación': Solo permite el ingreso si se encuentra sobre el
     * detalle de una cuenta matriz. Modo de Ingreso 'Gestión': Solo permite el
     * ingreso si se encuentra sobre el detalle de una solicitud.
     *
     * @param modoIngreso
     *
     * @return la pagina de inicio para empezar el proceso de creación o gestión
     * d ela solicitud.
     */
    public String ingresarModificacionCM(String modoIngreso) {
        
        obtenerDatosEncabezadoFormulario();
        generarOtrasListasSeleccionFormulario();
        datosGestion = new HashMap<>();
        hayCambioDatosCm = false;
        hayCambioDireccion = false;
        hayCambioSubEdificios = false;
        hayCambioCobertura = false;
        if (modoIngreso.equals(ConstantsCM.MODO_INGRESO_SOLICITUD)) {
            modoGestion = false;
            creacionExitosa = false;
            otroNodoSolicitud = false;
            tecnologiaSelect =  new CmtBasicaMgl();
            mostrarTabla = false;
            habilitarCombo=false;
            limpiarDataSubedificioGeneralModificar();
            limpiarDataSubEdificiosModificar();
            limpiarDataDireccionesModificar();
            
        } else {
            modoGestion = true;
            otroNodoSolicitud = false;
            obtenerInformacionGestion();
            
        }
        
        return "/view/MGL/CM/solicitudes/modificacionCM/principalSolicitudModificarCM";
    }
    
        /**
     * Funcion utilizada para bloquear la opcion de cambio de direccion a 
     * un subedificio con su propia dirección al seleccionar cambio
     * de nombre del subedificio
     *    
     * @author Juan David Hernandez 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public void bloquearCambioPropiaDireccion() {
        try {
            if(solSubEdificioSelecMod != null && solSubEdificioSelecMod.isCheckModNombreSubedificio()){                

                bloquearCambioPropiaDireccion = true;
                if (solSubEdificioSelecMod != null && solSubEdificioSelecMod.getDireccionSolicitudObj() != null) {
                    solSubEdificioSelecMod.getDireccionSolicitudObj().setDirFormatoIgac(null);
                    solSubEdificioSelecMod.setCheckModDireccion(false);
                }
            } else {               
                bloquearCambioPropiaDireccion = false;

                if (solSubEdificioSelecMod.getDireccionSolicitudObj() != null) {
                    solSubEdificioSelecMod.getDireccionSolicitudObj().setDirFormatoIgac(null);
                }
                solSubEdificioSelecMod.setCheckModDireccion(false);
            }
        } catch (NullPointerException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz." + e.getMessage();
            FacesUtil.mostrarMensajeError(msnErr+ e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error enal SolicitudModificacionCMBean: bloquearCambioPropiaDireccion(). " + e.getMessage(), e, LOGGER);
        }
    }


    /**
     * Método que obtiene detalle de la solicitud y otras variables para la
     * creación/gestión debido que es informacion compartida para todos los
     * tipos de solicitudes.
     */
    public void obtenerDatosEncabezadoFormulario() {
        try {
            validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            encabezadoSolModiCMBean = (EncabezadoSolicitudModificacionCMBean) JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            cuentaMatriz = encabezadoSolModiCMBean.getCuentaMatriz();
            solicitudModCM = encabezadoSolModiCMBean.getSolicitudModCM();
            
            usuarioLogin = encabezadoSolModiCMBean.getUsuarioLogin();
            
            solicitudFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudSubEdificioFacadeLocal.setUser(usuarioVT, perfilVT);
            direccionSolMglFacadeLocal.setUser(usuarioVT, perfilVT);
            direccionMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificioFacadeLocal.setUser(usuarioVT, perfilVT);
            
            hayModDirPrincipal = false;
            hayModSubedificios = false;
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz." ;
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:obtenerDatosEncabezadoFormulario(). " + e.getMessage(), e, LOGGER);
        }
        
    }

    /**
     * Metodo encargado de guardar en variables la informacion que se desea
     * modificar de los datos de la Cuenta Matriz. La informacion no se persiste
     * en base de datos con este metodo.
     *
     * @return la pagina de inicio para empezar el proceso de creación o gestión
     * d ela solicitud. 'Modificar CM'
     */
    public String preGuardarModificarDatosCM() {
        CmtSolicitudSubEdificioMgl nuevaSolSubEdi;
        boolean ocurrioError = false;
        try {        
             // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                            String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            
            // correo copia
            if (solicitudModCM.getCorreoCopiaSolicitud() != null
                    && !solicitudModCM.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaSolicitud() != null && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaSolicitud())) {
                            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            if (subEdificioGeneralMod.validarHayCambioSubedificio()) {
                nuevaSolSubEdi = (subEdificioGeneralMod.clone()).limpiarObjetosVaciosSubedificio();
                nuevaSolSubEdi.removerSolicitudSubedificio(solicitudesSubEdificiosList);
                nuevaSolSubEdi.setSolicitudModificacion(true);
                nuevaSolSubEdi.setFechaCreacion(new Date());
                nuevaSolSubEdi.setUsuarioCreacion(usuarioVT);
                nuevaSolSubEdi.setPerfilCreacion(perfilVT);
                nuevaSolSubEdi.setSolicitudCMObj(solicitudModCM);
                nuevaSolSubEdi.setEstadoRegistro(1);
                nuevaSolSubEdi.setSolModDatos(new Short("1"));
                
                solicitudesSubEdificiosList.add(nuevaSolSubEdi);
                
                hayCambioDatosCm = true;
            
                String msn = "Se guardaron los datos digitados en la opción 'Modificar Datos CM'."
                        + "Recuerde que para generar la solicitud debe seleccionar 'Crear Solicitud'";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                String msn = "No se identificó ningún cambio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
            
        } catch (CloneNotSupportedException | NumberFormatException e) {
            String msnErr = "Ha ocurrido un error agregando modificacion de Datos CM" ;
            FacesUtil.mostrarMensajeError(msnErr+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarDatosCM(). " + e.getMessage(), e, LOGGER);
        }
        return "principalSolicitudModificarCM";
    }
    
	public boolean validarDominioCorreos(String correoCopia){
        boolean resulto = false;
        if (correoCopia != null && !correoCopia.isEmpty()){
            if (correoCopia.toLowerCase().contains("claro.com.co")
                    || correoCopia.toLowerCase().contains("telmex.com.co")
                    || correoCopia.toLowerCase().contains("comcel.com.co")
                    || correoCopia.toLowerCase().contains("telmexla.com")
                    || correoCopia.toLowerCase().contains("telmex.com")){
            return true;
        }else{
            return false;
        }
    }else{
            return true;
        }
    }

    /**
     * Metodo encargado de guardar en variables la informacion que se desea
     * modificar las direcciones (Principal y Alternas) de la Cuenta Matriz. La
     * informacion no se persiste en base de datos con este metodo.
     *
     * @return la pagina de inicio para empezar el proceso de creación o gestión
     * d ela solicitud. 'Modificar Direcciones'
     */
    public String preGuardarModificarDireccion() {
        String actualDir = "";
        boolean ocurrioError = false;
        try {
               // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                            String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            
            // correo copia
            if (solicitudModCM.getCorreoCopiaSolicitud() != null
                    && !solicitudModCM.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaSolicitud() != null && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaSolicitud())) {
                            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            
            boolean hayCambio = false;
            direccionSolMglFacadeLocal.setUser(usuarioVT, perfilVT);
            solicitudesDireccionesList = new ArrayList<>();
            hayModDirPrincipal = false;
            listaOtrasCmDireccion = new HashMap<>();
            if (solicitudModCM.getCiudadGpo().getGpoMultiorigen().equalsIgnoreCase("1")) {
                if (selectedBarrio == null) {
                    String msn = "Ud no ha seleccionado un barrio";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    return null;
                }
            
            }
            for (CmtDireccionSolicitudMgl sd : direccionSolModList) {
                actualDir = sd.getDirFormatoIgac();
                if (actualDir != null && !"".equals(actualDir)) {
                    if (sd.isValidado()) {
                        if (validarDireccionExiteOEnCurso(sd)) {
                            sd.setSolicitudModificacion(true);
                            sd.setSolicitudCMObj(solicitudModCM);
                            sd.setEstadoRegistro(1);
                            solicitudesDireccionesList.add(sd);
                            hayCambio = true;
                            if (sd.getCmtDirObj().getTdiId() == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
                                hayModDirPrincipal = true;
                            }
                        }
                    } else {
                        String msn = "No se han validado todas las direcciones a modificar.";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                        return null;
                    }
                }
            }
            
            if (!hayCambio) {
                String msn = "No se identificó ningún cambio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                hayCambioDireccion = true;
                
                String msn = "Se guardaron los datos digitados en la opción 'Modificar Dirección'."
                        + " Recuerde que para generar la solicitud debe hacer click en 'Crear Solicitud'";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error agregando modificación de Direcciones. " + " Direccion:" + actualDir;
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarDireccion(). " + e.getMessage(), e, LOGGER);
        }
        return "solicitudModificarDireccionCM";
    }
    
    public boolean validarDireccionExiteOEnCurso(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) {
        boolean esValida = true;
        try {
            DrDireccion drDireccionAntigua;
            DrDireccion camposDrDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
            List<CmtDireccionDetalladaMgl> cmtDireccionDetalladaMgl;
            String msg;
            cmtDireccionDetalladaMgl  = cmtDireccionDetalleMglFacadeLocal.findDireccionDetalladaByDrDireccion(drDireccion, cmtDireccionSolictudMgl.getCmtDirObj().getCuentaMatrizObj().getCentroPoblado().getGpoId());
            if (cmtDireccionDetalladaMgl.isEmpty()) {
            direccionSolMglFacadeLocal.siExistenSolictudesEnCurso(camposDrDireccion, solicitudModCM.getComunidad());
            if (camposDrDireccion.getIdTipoDireccion().equalsIgnoreCase("CK") && cmtDireccionSolictudMgl.getDireccionAntigua() != null
                    && !cmtDireccionSolictudMgl.getDireccionAntigua().isEmpty()) {
                try {
                    drDireccionAntigua = validadorDireccionFacadeLocal.convertirDireccionStringADrDireccion(cmtDireccionSolictudMgl.getDireccionAntigua(), solicitudModCM.getCentroPobladoGpo().getGpoId());
                    if (drDireccionAntigua != null && solicitudModCM.getCentroPobladoGpo().getGpoMultiorigen().equalsIgnoreCase("1")) {
                        if(selectedBarrio!= null && !selectedBarrio.isEmpty()){
                            drDireccionAntigua.setBarrio(selectedBarrio);
                        }else{
                        drDireccionAntigua.setBarrio(cmtDireccionSolictudMgl.getBarrio());
                    }
                        
                    }
                    if (drDireccionAntigua != null) {
                        direccionSolMglFacadeLocal.siExistenSolictudesEnCurso(drDireccionAntigua, solicitudModCM.getComunidad());
                    }
                } catch (ApplicationExceptionCM ex2) {
                        msg = "Exite cuenta matriz N° <b>"
                            + ex2.getMatrizMgl().getNumeroCuenta().toString()
                            + "</b> con direccion antigua, debe solicitar la actualizacion a la nueva direccion";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    if (ex2.getMatrizMgl() != null) {
                        cmtDireccionSolictudMgl.setMostrarLinkOtraDireccion(true);
                        cmtCuentaMatrizMglOtraDireccion = ex2.getMatrizMgl();
                        listaOtrasCmDireccion.put(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId(), cmtCuentaMatrizMglOtraDireccion);
                    }
                    return false;
                } catch (ApplicationException ex) {
                        msg = "Error validando direccion antigua:..." + ex.getMessage();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    LOGGER.error(msg);
                    return false;
                }
            }
            
            if (solicitudModCM.getCiudadGpo().getGpoMultiorigen().equalsIgnoreCase("1") && camposDrDireccion.getIdTipoDireccion().equalsIgnoreCase("CK")) {

                if (selectedBarrio != null && !selectedBarrio.isEmpty()) {
                    camposDrDireccion.setBarrio(selectedBarrio);
                } else {
                    camposDrDireccion.setBarrio(cmtDireccionSolictudMgl.getBarrio());
                }
                boolean dirExiste = false;
                if (barrioslist != null && !barrioslist.isEmpty()) {
                    for (String bar : barrioslist) {
                    if (cmtDireccionSolictudMgl.getBarrio().toUpperCase().trim().equalsIgnoreCase(bar.toUpperCase().trim())) {
                        dirExiste = true;
                    }
                }
                if (dirExiste) {
                    if (responseValidarDireccionDto.getAddressService().getExist().equalsIgnoreCase("true")) {
                        msg = "La direccion existe con el barrio asociado " + cmtDireccionSolictudMgl.getBarrio();
                    } else {
                        msg = "La direccion no existe";
                    }
                } else {
                    msg = "La direccion no existe con el barrio asociado " + cmtDireccionSolictudMgl.getBarrio();
                }
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msg, ""));
            }
            
            }
            
            cmtDireccionSolictudMgl.setMostrarLinkOtraDireccion(false);
            cmtCuentaMatrizMglOtraDireccion = null;
            
            } else {
                DireccionMgl direccionMgl = direccionMglFacadeLocal1.findById(cmtDireccionDetalladaMgl.get(0).getDireccion());
                CmtDireccionMgl cmtDireccionMgl = direccionMglFacadeLocal.findCmtDireccion(direccionMgl.getDirId());
                if(cmtDireccionMgl != null){
                      msg = "Esta direccion esta asociada a la Cuenta Matriz No.: " + cmtDireccionMgl.getCuentaMatrizObj().getCuentaId().substring(11, cmtDireccionMgl.getCuentaMatrizObj().getCuentaId().length());
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msg, ""));
                return false;
                }
            }

        } catch (ApplicationExceptionCM ex) {
            String msg = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msg + " Dirección:" + cmtDireccionSolictudMgl.getDirFormatoIgac(), ""));
            LOGGER.error(msg);
            if (ex.getMatrizMgl() != null) {
                cmtDireccionSolictudMgl.setMostrarLinkOtraDireccion(true);
                cmtCuentaMatrizMglOtraDireccion = ex.getMatrizMgl();
                listaOtrasCmDireccion.put(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId(), cmtCuentaMatrizMglOtraDireccion);
            }
            return false;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:validarDireccionExiteOEnCurso. " + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:validarDireccionExiteOEnCurso. " + e.getMessage(), e, LOGGER);
        }
        return esValida;
    }
    
    public void irCuentaCMdir(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) {
        try {
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            if (listaOtrasCmDireccion.containsKey(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId())) {
                cuentaMatrizBean.setCuentaMatriz(listaOtrasCmDireccion.get(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId()));
                FacesUtil.navegarAPagina("/view/MGL/CM/tabs/general.jsf");
            }
        } catch (ApplicationException e) {
            String msn = co.com.claro.mgl.utils.Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:irCuentaCMdir. " + e.getMessage(), e, LOGGER);
        }
        
        
    }

    /**
     * Metodo encargado de guardar en variables la informacion que se desea
     * modificar los sub-edificios que pertenecen a la Cuenta Matriz. La
     * informacion no se persiste en base de datos con este metodo.
     *
     * @return la pagina de inicio para empezar el proceso de creación o gestión
     * d ela solicitud. 'Modificar Subedificios'
     */
    public String preGuardarModificarsubEdificio() {
        CmtSolicitudSubEdificioMgl nuevaSolSubEdi;
        try {
            hayModSubedificios= false;
            boolean ocurrioError = false;
            if (solSubEdificioSelecMod != null) {
                
                // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                            String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            
            // correo copia
            if (solicitudModCM.getCorreoCopiaSolicitud() != null
                    && !solicitudModCM.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaSolicitud() != null && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaSolicitud())) {
                            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
                
                
                if (solSubEdificioSelecMod.isCheckModNombreSubedificio()) {
                    if ((solSubEdificioSelecMod.getNombreSubedificio() == null || solSubEdificioSelecMod.getNombreSubedificio().isEmpty())) {
                        String msn = "Campo 'Nombre Edificio' es requerido";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                        return "solicitudModificarSubedificiosCM";
                    } else {
                        for (CmtSolicitudSubEdificioMgl sse : subEdificiosModList) {
                            if (sse.getSubEdificioObj() != null
                                    && solSubEdificioSelecMod.getNombreSubedificio().equals(sse.getSubEdificioObj().getNombreSubedificio())) {
                                String msn = "Ya existe un subedificio con este nombre.";
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                return "solicitudModificarSubedificiosCM";
                            }
                            
                            if (sse.getSubEdificioObj() != null
                                    && sse.getSubEdificioObj().getSubEdificioId() != solSubEdificioSelecMod.getSubEdificioObj().getSubEdificioId()
                                    && solSubEdificioSelecMod.getNombreSubedificio().equals(sse.getNombreSubedificio())) {
                                String msn = "Ya existe una solicitud de creación/modificación subedificio con este nombre.";
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                                return "solicitudModificarSubedificiosCM";
                            }
                        }
                    }
                    
                }
                
                if (solSubEdificioSelecMod.existeSolSubEdiEliminar(solicitudesSubEdificiosList) && (solSubEdificioSelecMod.isSolicitudModificacion())) {
                    String msn = "No se puede eliminar y modificar datos para el mismo subedificio  "
                            + solSubEdificioSelecMod.getSubEdificioObj().getNombreSubedificio();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    return "solicitudModificarSubedificiosCM";
                }
                
                if (solSubEdificioSelecMod.isSolicitudEliminar()
                        && solSubEdificioSelecMod.getSubEdificioObj().getListHhpp() != null
                        && solSubEdificioSelecMod.getSubEdificioObj().getListHhpp().size() > 0) {
                    String msn = "No se puede eliminar el Sub-Edificio  "
                            + solSubEdificioSelecMod.getSubEdificioObj().getNombreSubedificio()
                            + ", ya que tiene HHPP asociados.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    return "solicitudModificarSubedificiosCM";
                }
                
                if (solSubEdificioSelecMod.isCheckModDireccion() && (solSubEdificioSelecMod.getDireccionSolicitudObj() != null
                        && (solSubEdificioSelecMod.getDireccionSolicitudObj().getDirFormatoIgac() == null
                        || "".equals(solSubEdificioSelecMod.getDireccionSolicitudObj().getDirFormatoIgac())))) {
                    String msn = "Campo 'Dirección Subedificio' es requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return "solicitudModificarSubedificiosCM";
                } else {
                    if (solSubEdificioSelecMod.isCheckModDireccion()) {
                        if (solSubEdificioSelecMod.getDireccionSolicitudObj() != null
                                && solSubEdificioSelecMod.getDireccionSolicitudObj().isValidado()) {
                        } else {
                            String msn = "No se ha validado la dirección a modificar.";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                            return null;
                        }
                    }
                    
                }

                if (solSubEdificioSelecMod.getSubEdificioObj() == null) {
                    String msn = "Seleccionó una solicitud de creación de Sub-Edificio. No se permite modificarla ó eliminarla.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                } else {
                    if (solSubEdificioSelecMod.validarHayCambioSubedificio() || solSubEdificioSelecMod.isSolicitudEliminar()) {
                        nuevaSolSubEdi = (solSubEdificioSelecMod.clone()).limpiarObjetosVaciosSubedificio();
                        nuevaSolSubEdi.removerSolicitudSubedificio(solicitudesSubEdificiosList);
                        nuevaSolSubEdi.setFechaCreacion(new Date());
                        nuevaSolSubEdi.setUsuarioCreacion(usuarioVT);
                        nuevaSolSubEdi.setPerfilCreacion(perfilVT);
                        nuevaSolSubEdi.setSolicitudCMObj(solicitudModCM);
                        nuevaSolSubEdi.setEstadoRegistro(1);
                        if (nuevaSolSubEdi.getDireccionSolicitudObj() != null) {
                            nuevaSolSubEdi.getDireccionSolicitudObj().setSolicitudCMObj(solicitudModCM);
                        }
                        if (nuevaSolSubEdi.isSolicitudEliminar()) {
                            nuevaSolSubEdi.setSolModEliminacion(new Short("1"));
                        } else {
                            nuevaSolSubEdi.setSolModDatos(new Short("1"));
                        }
                        solicitudesSubEdificiosList.add(nuevaSolSubEdi);
                        
                        hayCambioSubEdificios = true;
                        
                        String msn = "Se guardaron los datos digitados en la opción 'Modificar Subedificios' para el subedificio seleccionado."
                                + " Recuerde que para generar la solicitud debe hacer click en 'Crear Solicitud'";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                        
                        hayModSubedificios=true;
                    } else {
                        String msn = "No se identificó ningún cambio";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    }
                }
                
            } else {
                String msn = "No se identificó ningún cambio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
            
            
        } catch (CloneNotSupportedException e) {
            String msnErr = "Ha ocurrido creando la solicitud de Modificación de Sub-Edificio";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarsubEdificio(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarsubEdificio(). " + e.getMessage(), e, LOGGER);
        }
        return "solicitudModificarSubedificiosCM";
    }
    
    public String preguardarSolicitudCrearSubedificio() {
        try {
            
            NodoMgl nodo;
            String msn = "";
            
            hayModSubedificios= false;
            
            if (solSubEdificioNuevo.getNombreSubedificio() == null || solSubEdificioNuevo.getNombreSubedificio().isEmpty()) {
                msn = "Campo 'Nombre Edificio' es requerido";
            } else {
                for (CmtSolicitudSubEdificioMgl sse : subEdificiosModList) {
                    if (sse.getSubEdificioObj() != null) {
                        if (solSubEdificioNuevo.getNombreSubedificio().equals(sse.getSubEdificioObj().getNombreSubedificio())) {
                            msn = "Ya existe un subedificio con este nombre.";
                        }
                        if (solSubEdificioNuevo.getNombreSubedificio().equals(sse.getNombreSubedificio())) {
                            msn = "Ya existe una solicitud de modificación subedificio con este nombre.";
                        }
                        
                    } else {
                        if (sse.getNombreSubedificio() != null && solSubEdificioNuevo.getNombreSubedificio().equals(sse.getNombreSubedificio())) {
                            msn = "Ya se solicitó la creación de un subedificio con este nombre.";
                        }
                        
                    }
                }
            }
            
            
            
            if (!msn.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                nodo = validarNodo(solSubEdificioNuevo.getNodoObj().getNodCodigo());
                if (nodo == null) {
                    return null;
                } else {
                    solSubEdificioNuevo.setNodoObj(nodo);
                    solSubEdificioNuevo.setEstadoSubEdificioObj(basicaEstadoSinVisitaTecnica);
                    solSubEdificioNuevo.setSolicitudModificacion(true);
                    solSubEdificioNuevo.setTipoEdificioObj(tipoEdificioNuevo);
                    solSubEdificioNuevo.setFechaCreacion(new Date());
                    solSubEdificioNuevo.setUsuarioCreacion(usuarioVT);
                    solSubEdificioNuevo.setPerfilCreacion(perfilVT);
                    solSubEdificioNuevo.setSolicitudCMObj(solicitudModCM);
                    solSubEdificioNuevo.setEstadoRegistro(1);
                    solSubEdificioNuevo.setSolModDatos(new Short("1"));
                    solSubEdificioNuevo.limpiarObjetosVaciosSubedificio();
                    
                    solicitudesSubEdificiosList.add(solSubEdificioNuevo);
                    subEdificiosModList.add(solSubEdificioNuevo);
                    mostrarCrearSubedificio = false;
                    
                    hayCambioSubEdificios = true;
                    
                    hayCambioSubEdificios = true;
                    
                    msn = "Se agregó correctamente la solcitud de creación de subedificio"
                            + "Recuerde que para generar la solicitud debe seleccionar 'Crear Solicitud'";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
                
                
                
                solSubEdificioNuevo = new CmtSolicitudSubEdificioMgl();
            }
        } catch (NumberFormatException e) {
            String msnErr = "Ha ocurrido creando la solicitud de Creación de Sub-Edificio. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msnErr = "Ha ocurrido creando la solicitud de Creación de Sub-Edificio. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        }
        return "solicitudModificarSubedificiosCM";
    }

    /**
     * Metodo encargado de guardar en variables la informacion que se desea
     * modificar cobertura de los subedificios de la cuenta matriz. La
     * informacion no se persiste en base de datos con este metodo.
     *
     * @return la pagina de inicio para empezar el proceso de creación o gestión
     * d ela solicitud. 'Modificar Cobertura'
     */
    public String preGuardarModificarCobertura() {
        CmtSolicitudSubEdificioMgl nuevaSolSubEdi;
        String msn;
        String msn2 = "";
        Severity severidad;
        boolean hayCambios = false;
        boolean ocurrioError = false;
        try {
                 // correo asesor
            if (solicitudModCM.getCorreoAsesor() != null
                    && !solicitudModCM.getCorreoAsesor().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoAsesor())) {
                    String msg = "El campo correo asesor no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoAsesor() != null && !solicitudModCM.getCorreoAsesor().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                            String msg = "El campo asesor debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
            
            // correo copia
            if (solicitudModCM.getCorreoCopiaSolicitud() != null
                    && !solicitudModCM.getCorreoCopiaSolicitud().trim().isEmpty()) {
                if (!validarCorreo(solicitudModCM.getCorreoCopiaSolicitud())) {
                    String msg = "El campo copia a no tiene el formato requerido";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
                    return null;
                } else {
                    if (solicitudModCM.getCorreoCopiaSolicitud() != null && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()) {
                        if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaSolicitud())) {
                            String msg = "El campo copia a debe ser un correo con dominio telmexla.com, telmex.com.co, telmex.com, comcel.com.co o claro.com.co";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msg, ""));
                            ocurrioError = true;
                            return "";
                        }
                    }
                }
            }
                  
            solicitudesCoberturasList = new ArrayList<>();
            for (CmtSolicitudSubEdificioMgl solCob : coberturaModList) {
                if (solCob.isSeleccionado()) {
                    if (solCob.validarHayCambioSubedificio()) {
                        nuevaSolSubEdi = (solCob.clone()).limpiarObjetosVaciosSubedificio();
                        if (!nuevaSolSubEdi.existeSolSubEdiEliminar(solicitudesSubEdificiosList)) {
                            NodoMgl nodo = validarNodo(nuevaSolSubEdi.getNodoObj().getNodCodigo());
                            if (nodo == null) {
                                return null;
                            } else {
                                nuevaSolSubEdi.setNodoObj(nodo);
                                if (nuevaSolSubEdi.getNodoDisenoObj() != null
                                        && nuevaSolSubEdi.getNodoDisenoObj().getNodCodigo() != null
                                        && !nuevaSolSubEdi.getNodoDisenoObj().getNodCodigo().isEmpty()) {

                                    NodoMgl nodoDise = validarNodo(nuevaSolSubEdi.getNodoDisenoObj().getNodCodigo());
                                    nuevaSolSubEdi.setNodoDisenoObj(nodoDise);
                                }

                            }
                            nuevaSolSubEdi.setSolicitudCMObj(solicitudModCM);
                            nuevaSolSubEdi.setSolModCobertura(new Short("1"));
                            nuevaSolSubEdi.setFechaCreacion(new Date());
                            nuevaSolSubEdi.setUsuarioCreacion(usuarioVT);
                            nuevaSolSubEdi.setPerfilCreacion(perfilVT);
                            nuevaSolSubEdi.setEstadoRegistro(1);
                           
                            solicitudesCoberturasList.add(nuevaSolSubEdi);

                            hayCambios = true;
                            
                        } else {
                            msn2 = nuevaSolSubEdi.getNombreSubedificio() + "\n";
                        }
                    }
                }
            }
            
            if (hayCambios) {
                if (!"".equals(msn2)) {
                    msn = "Se solicitó eliminar y modificar cobertura para los siguientes Sub-Edificios:  \n" + msn2;
                    severidad = FacesMessage.SEVERITY_WARN;
                    habilitarCombo=true;
                    control=1;
                } else {
                    hayCambioCobertura = true;
                    msn = "Se guardaron los datos digitados en la opción 'Modificar Cobertura' para los Sub-Edificios seleccionados."
                            + " Recuerde que para generar la solicitud debe hacer click en 'Crear Solicitud'";
                    severidad = FacesMessage.SEVERITY_INFO;
                    habilitarCombo=true;
                    control=1;
                }
            } else {
                msn = "No hay ningún Sub-Edificio seleccionado ó modificado.";
                severidad = FacesMessage.SEVERITY_WARN;
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, msn, ""));
            
        } catch (CloneNotSupportedException e) {
            String msnErr = "Ha ocurrido creando la solicitud de Modificación de Cobertura. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarCobertura(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:preGuardarModificarCobertura(). " + e.getMessage(), e, LOGGER);
        }
        
        
        
        return "solicitudModificarCoberturaCM";
    }

    /**
     * Carga la lista de nodos para un Sub-Edificio.Si hay nodos de Cobertura
 se carga este listado, de lo contrario se carga un listado con nodos
 cercanos.
     * @param sse
     * @return 
     */
    public String cargarListaNodos(CmtSolicitudSubEdificioMgl sse) {
        AddressService cobertura;
        CmtDireccionSolicitudMgl cmtDirSolCalCob = null;
        List<NodoMgl> nodosList = new ArrayList<>();
        NodoMgl nFind;
        CmtDireccionMgl dirSubEdi = null;
        try {
            if (sse != null) {
                dirSubEdi = direccionMglFacadeLocal.findByIdSubEdificio(sse.getSubEdificioObj().getSubEdificioId());
            }
            
            if (dirSubEdi != null) {
                cmtDirSolCalCob = dirSubEdi.mapearCamposCmtDireccionMgl();
            } else {
                dirSubEdi = cuentaMatriz.getDireccionPrincipal();
                if (dirSubEdi != null) {
                    cmtDirSolCalCob = dirSubEdi.mapearCamposCmtDireccionMgl();
                }
            }

            /**
             * Calculando Nodos Cobertura
             */
            if (cmtDirSolCalCob != null) {
                
                cmtDirSolCalCob.setSolicitudCMObj(solicitudModCM);
                
                cobertura = validadorDireccionFacadeLocal.calcularCobertura(cmtDirSolCalCob);
                
                if (cobertura != null) {
                    nFind = construirNodoBusqueda(cobertura.getNodoUno(), "B");
                    if (nFind != null) {
                        nodosList.add(nFind);
                    }
                    nFind = construirNodoBusqueda(cobertura.getNodoDos(), "U");
                    if (nFind != null) {
                        nodosList.add(nFind);
                    }
                    nFind = construirNodoBusqueda(cobertura.getNodoTres(), "U");
                    if (nFind != null) {
                        nodosList.add(nFind);
                    }  
                    
                } else {
                    /**
                     * Calculando Nodos Cercanos
                     */
                    HashMap<String, NodoDto> listNodosCercanos = validadorDireccionFacadeLocal.calcularNodoCercano(cmtDirSolCalCob);
                    if (listNodosCercanos != null) {
                        List<NodoDto> list = new ArrayList<>(listNodosCercanos.values());
                        for (NodoDto nd : list) {
                            nFind = construirNodoBusqueda(nd.getCodigo(), nd.getTipoRed());
                            if (nFind != null) {
                                nodosList.add(nFind);
                            }
                        }
                        
                    }
                    
                }
                
                if (sse == null) {
                    listaNodosDireccionPrincipal = nodosList;
                } else {
                    if (listaNodosSubEdificio.containsKey(sse.getSubEdificioObj().getSubEdificioId())) {
                        listaNodosSubEdificio.remove(sse.getSubEdificioObj().getSubEdificioId());
                    }
                    listaNodosSubEdificio.put(sse.getSubEdificioObj().getSubEdificioId(), nodosList);
                }
                
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:cargarListaNodos. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:cargarListaNodos. " + e.getMessage(), e, LOGGER);
        }
        
        return "solicitudModificarCoberturaCM";
    }

    /**
     * Ontiene la lista de nodos para un Sub-Edificio.Si hay nodos de Cobertura
 se carga este listado; de lo contrario se carga un listado con nodos
 cercanos.
     * @param sse
     * @return 
     */
    public List<NodoMgl> getListaNodosSubEdificio(CmtSolicitudSubEdificioMgl sse) {
        List<NodoMgl> nodos = listaNodosSubEdificio.get(sse.getSubEdificioObj().getSubEdificioId());
        return nodos;
        
    }

    /**
     * Metodo encargado de validar los nodos seleccionados para modificar
     * cobertura.
     * @return 
     */
    public String validarNodos() {
        boolean haySeleccion = false;
        try {
            control=0; 
            for (CmtSolicitudSubEdificioMgl solCob : coberturaModList) {
                if (modoGestion) {
                    if (datosGestion.size() > 0) {
                        Iterator<Map.Entry<CmtSolicitudSubEdificioMgl, String>> iterator = datosGestion.entrySet().iterator();

                        while (iterator.hasNext()) {

                            Map.Entry<CmtSolicitudSubEdificioMgl, String> n = iterator.next();
                            if (solCob.getSolSubEdificioId().compareTo(n.getKey().getSolSubEdificioId()) == 0) {
                                NodoMgl nodoGestion = new NodoMgl();
                                nodoGestion.setNodCodigo(n.getValue());
                                solCob.setNodoDisGestion(nodoGestion);
                            }

                        }
                    }
                    
                    if (solCob.getNodoDisGestion() != null && solCob.getNodoDisGestion().getNodCodigo() != null && !solCob.getNodoDisGestion().getNodCodigo().isEmpty()) {
                        haySeleccion = true;
                        validarNodo(solCob.getNodoDisGestion().getNodCodigo());
                    } else {
                        haySeleccion = false;
                    }
                } else {
                    if (solCob.isSeleccionado()) {

                        if (solCob.getNodoDisenoObj() != null && solCob.getNodoDisenoObj().getNodCodigo() != null && !solCob.getNodoDisenoObj().getNodCodigo().isEmpty()) {  //Cambia getNodoObj() por getNodoDisenoObj()
                            haySeleccion = true;
                            validarNodo(solCob.getNodoDisenoObj().getNodCodigo()); //Cambia getNodoObj() por getNodoDisenoObj()
                        } else {
                            String msg = "Ingrese un nodo valido. ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                            control = 1;
                        }

                    }

                }      
            }

            if (!haySeleccion) {
                if (modoGestion) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Los campos 'Nodo Diseño' son obligatorios.", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No hay ningún Sub-Edificio seleccionado ó modificado.", ""));
                }

            } else {
                control = 1;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Nodos validados satisfactoriamente.", ""));
            }
           
        } catch (Exception e) {
             control = 1;
             String msnErr = "Ha ocurrido un error haciendo la validacion de los nodos  en validarNodos(). \n";
            FacesUtil.mostrarMensajeError(msnErr+ e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Método que persiste la solicitud en base de datos, segun lo que haya
     * modificado el usuario segun las diferentes opciones de modificación
     * 'Modificacion Datos CM', 'Modificación Direcciones', 'Modificar
     * Subedificios' y 'Modificar Cobertura' para la creación de la solicitud
     * existe solo el estado PENDIENTE (Pendiente por Gestionar)
     * @return 
     */
    public String crearSolicitud() {
        try {
            CmtSolicitudCmMgl solRespuesta;
            List<CmtSolicitudSubEdificioMgl> listTemp;
            String msn;
            creacionExitosa = false;

            if (subEdificioGeneralMod.validarHayCambioSubedificio()) {
                solicitudModCM.setModDatosCm((short) 1);
                if (solicitudesSubEdificiosList != null && solicitudesSubEdificiosList.size() == 1) {
                    solicitudModCM.setModSubedificios((short) 0);
                }
            } else {
                solicitudModCM.setModDatosCm((short) 0);
                if (solicitudesSubEdificiosList != null && solicitudesSubEdificiosList.size() > 0) {
                    solicitudModCM.setModSubedificios((short) 1);
                } else {
                    solicitudModCM.setModSubedificios((short) 0);
                }
            }
            
            if (solicitudesCoberturasList != null && solicitudesCoberturasList.size() > 0) {
                solicitudModCM.setModCobertura((short) 1);
            } else {
                solicitudModCM.setModCobertura((short) 0);
            }
            
            if (solicitudesDireccionesList != null && solicitudesDireccionesList.size() > 0) {
                solicitudModCM.setModDireccion((short) 1);
            } else {
                solicitudModCM.setModDireccion((short) 0);
            }
            
            if ((solicitudesSubEdificiosList != null && solicitudesSubEdificiosList.size() > 0)
                    || (solicitudesCoberturasList != null && solicitudesCoberturasList.size() > 0)
                    || (solicitudesDireccionesList != null && solicitudesDireccionesList.size() > 0)) {
                solicitudModCM.setTempSolicitud(encabezadoSolModiCMBean.getTimeSol());
                CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
                estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
                solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                solicitudModCM.setEstadoRegistro(1);
                listTemp = solicitudesSubEdificiosList;
                if (solicitudesCoberturasList != null) {
                    solicitudesCoberturasList.forEach((ssc) -> {
                        listTemp.add(ssc);
                    });
                }
                
                solicitudModCM.setListCmtSolicitudSubEdificioMgl(listTemp);
                solicitudModCM.setListCmtDireccionesMgl(solicitudesDireccionesList);
                solicitudModCM.setDisponibilidadGestion("1");
                if(tecnologiaSelect != null && tecnologiaSelect.getBasicaId() != null){
                     solicitudModCM.setBasicaIdTecnologia(tecnologiaSelect);
                }else{
                     solicitudModCM.setBasicaIdTecnologia(null);
                }
               
                solicitudFacadeLocal.setUser(usuarioVT, perfilVT);
                if (solicitudModCM != null && solicitudModCM.getCorreoCopiaSolicitud() != null
                        && !solicitudModCM.getCorreoCopiaSolicitud().isEmpty()
                        && encabezadoSolModiCMBean.getUsuarioSolicitudCreador() != null && encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail() != null
                        && !encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail().isEmpty()) {
                    solicitudModCM.setCorreoCopiaSolicitud(solicitudModCM.getCorreoCopiaSolicitud() + "," + encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail());
                } else {
                    if (encabezadoSolModiCMBean.getUsuarioSolicitudCreador() != null && encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail() != null
                            && !encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail().isEmpty()) {
                        solicitudModCM.setCorreoCopiaSolicitud(encabezadoSolModiCMBean.getUsuarioSolicitudCreador().getEmail());
                    }
                }
                solRespuesta = solicitudFacadeLocal.crearSol(solicitudModCM);
                encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);

                if (solRespuesta != null && solRespuesta.getSolicitudCmId() != null) {
                    solicitudModCM = solRespuesta;
                    crearNotaSolicitud("Creación Solicitud Modificación de Cuenta Matriz");
                    solicitudModCM = solicitudFacadeLocal.findById(solicitudModCM.getSolicitudCmId());
                    encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);
                    msn = "La Solicitud N° <b>"
                            + solicitudModCM.getSolicitudCmId()
                            + "</b> de Modificación de Cuenta Matriz fue creada con éxito, en estado Pendiente para la validación por el area de HHPP";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    creacionExitosa = true;
                }
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se identificó ningún cambio", ""));
            }
            
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. (Datos Solicitud)";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean:  crearSolicitud(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Metodo que permite persistir una nota asociada a la creación o gestión de
     * la solicitud; segun sea el caso.
     *
     * @param NotaDesc
     * @return 
     */
    public String crearNotaSolicitud(String NotaDesc) {
        try {
            
            CmtNotasSolicitudMgl nota = new CmtNotasSolicitudMgl();
            nota.setDescripcion(NotaDesc);
            nota.setNota(encabezadoSolModiCMBean.getCmtNotasSolicitudMgl() != null ? encabezadoSolModiCMBean.getCmtNotasSolicitudMgl().getNota() : "");
            nota.setSolicitudCm(solicitudModCM);
            CmtBasicaMgl tipoNota = new CmtBasicaMgl();
            tipoNota.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_NOTA_MODIFICACION_CUENTA_MATRIZ).getBasicaId());
            nota.setTipoNotaObj(tipoNota);
            nota.setEstadoRegistro(1);
            cmtNotasSolicitudMglFacadeLocal.setUser(usuarioVT, perfilVT);
            cmtNotasSolicitudMglFacadeLocal.crearNotSol(nota);
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. (Notas)" ;
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: crearNotaSolicitud(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Método que obtiene todos los datos a una solicitud para poder realizar su
     * respectiva gestión.
     * @return 
     */
    public String obtenerInformacionGestion() {
        try {
            hayModDirecciones = false;
            solicitudesSubEdificiosList = solicitudModCM.getListCmtSolicitudSubEdificioMgl();
            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
            
            CmtSubEdificioMgl se = solicitudModCM.getCuentaMatrizObj().getSubEdificioGeneral();
            subEdificiosModList = new ArrayList<>();
            coberturaModList = new ArrayList<>();
            
            if (solicitudesSubEdificiosList != null) {
                /*Obteniendo datos de Modificacion de Datos CM, Sub-Edificios y Cobertura*/
                for (CmtSolicitudSubEdificioMgl sse : solicitudesSubEdificiosList) {
                    sse.setSeleccionado(true);
                    sse.setNodoDisGestion(new NodoMgl());
                    if (sse.getSubEdificioObj() != null && sse.getSubEdificioObj().getSubEdificioId() != null
                            && sse.getSubEdificioObj().getSubEdificioId().equals(se.getSubEdificioId())
                            && sse.getNodoObj() == null) {
                        subEdificioGeneralMod = sse;
                        hayCambioDatosCm = true;
                        sse.setEdificioGeneral(true);
                        if (sse.getSolModCobertura() == 1) {
                            coberturaModList.add(sse);
                            cmtTecnologiaSubMgl = se.getListTecnologiasSub().get(0);
                        }
                           tecnologiaSelect = solicitudModCM.getBasicaIdTecnologia();
                    } else {
                        sse.setEdificioGeneral(false);
                        if (sse.getSolModDatos() == 1 || sse.getSolModEliminacion() == 1) {
                            subEdificiosModList.add(sse);
                        }
                        if (sse.getSolModCobertura() == 1) {
                            sse.setNodoDisGestion(sse.getNodoDisenoObj());
                            coberturaModList.add(sse);
                             cmtTecnologiaSubMgl = se.getListTecnologiasSub().get(0);
                        }
                        tecnologiaSelect = solicitudModCM.getBasicaIdTecnologia();
                    }
                }
            }
            
            if (!subEdificiosModList.isEmpty()) {
                solSubEdificioSelecMod = subEdificiosModList.get(0);
                hayCambioSubEdificios = true;
            }
            if (!coberturaModList.isEmpty()) {
                solCoberturaSelecMod = coberturaModList.get(0);
                hayCambioCobertura = true;
            }

            /*Obteniendo datos de Modificacion de Direcciones*/
            direccionSolModList = solicitudModCM.getListCmtDireccionesMgl();
            if (!direccionSolModList.isEmpty()) {
                direccionSolSelecMod = direccionSolModList.get(0);
                solicitudesDireccionesList = direccionSolModList;
                hayModDirecciones = true;
                hayCambioDireccion = true;
            }
            
            
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: obtenerInformacionGestion(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: obtenerInformacionGestion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Persiste en base de datos la Gestion de los siguientes tipos de
     * Modificación de Cuenta Matriz: 'Modificacion Datos CM', 'Modificación
     * Direcciones' y 'Modificar Subedificios'. La solcitud no queda Gestionada
     * hasta que se realice la Gestión por el área de HHPP y la Gestión por el
     * área de Diseño de Red. Hay 4 estados para la Gestión de la solicitud de
     * Modificacion de Cuenta Matriz: PENDIENTE: No se a realizado nungún tipo e
     * Gestión sobre la solicitud PENDIENTE COBERTURA: Se realizó Gestión de
     * HHPP y queda pendiente la gestión de COBERTURA PENDIENTE HHPP: Se realizó
     * Gestión de COBERTURA y queda pendiente la gestión de HHPP
     *
     * @return la pagina de inicio gestión de la solicitud. 'Modificar Datos CM'
     */
    public String gestionarHHPP() {
        try {
            CmtSubEdificioMgl subEdiMod = null;
            CmtBasicaMgl rtaTempSolModDatos;
            CmtBasicaMgl rtaTempSolModDir;
            CmtBasicaMgl rtaTempSolModSubE;
            CmtBasicaMgl estadoFinalizado;
            CmtBasicaMgl estadoPendienteCobertura;
            String msn = "";
            String msnDatosCM = "";
            String msnDireccion = "";
            String msnSubEdificios = "";
             boolean direccionExiste = false;
            
            
            Date fechaInicioTemp;
            BigDecimal selecionVacio = BigDecimal.ZERO;
            if (encabezadoSolModiCMBean.isActivoCheckModDatosCM() && (selecionVacio.equals(encabezadoSolModiCMBean.getRespuestaModDatosSelect()) || encabezadoSolModiCMBean.getRespuestaModDatosSelect() == null)) {
                msn = msn + "- 'MODIFICAR DATOS CM' -";
            }
            if (encabezadoSolModiCMBean.isActivoCheckModDireccion() && (selecionVacio.equals(encabezadoSolModiCMBean.getRespuestaModDireccionSelect()) || encabezadoSolModiCMBean.getRespuestaModDireccionSelect() == null)) {
                msn = msn + "- 'MODIFICAR DIRECCIÓN' -";
            }
            if (encabezadoSolModiCMBean.isActivoCheckModSubedificios() && (selecionVacio.equals(encabezadoSolModiCMBean.getRespuestaModSubEdiSelect()) || encabezadoSolModiCMBean.getRespuestaModSubEdiSelect() == null)) {
                msn = msn + "- 'MODIFICAR SUB-EDIFICIOS' -";
            }            
           
            if (!"".equals(msn)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe diligenciar la ACCIÓN y RESULTADO GESTIÓN de las pestañas " + msn, ""));
                return null;
            }
            
             if (encabezadoSolModiCMBean.getSolicitudModCM().getRespuestaActual() == null
                    || "".equals(encabezadoSolModiCMBean.getSolicitudModCM().getRespuestaActual())) {                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese la respuesta Actual para las solicitudes por favor", ""));
                return null;
            }
            
             
            //Valida si la solicitud ya se gestionó y se encuentra en estado PENDIENTE DE COBERTURA y solo puede gestionar la pestaña de modificar cobertura
            if (solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getBasicaId() != null) {
                CmtBasicaMgl estadoActualSolicitud = cmtBasicaMglFacadeLocal.findById(solicitudModCM.getEstadoSolicitudObj().getBasicaId());
                if ((estadoActualSolicitud != null && estadoActualSolicitud.getIdentificadorInternoApp() != null
                        && estadoActualSolicitud.getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LA solicitud ya fue gestionada y se encuentra en estado PENDIENTE DE COBERTURA, solo es posible gestionar la pestaña de MODIFICAR COBERTURA.", ""));
                    return null;
                }
               
                //Valida si la solicitud ya se gestionó y se encuentra en estado FINALIZADO y no es necesario volver a gestionar
               if ((estadoActualSolicitud != null && estadoActualSolicitud.getIdentificadorInternoApp() != null
                        && estadoActualSolicitud.getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LA solicitud ya fue gestionada y se encuentra en estado FINALIZADO, no es necesario gestionarla nuevamente.", ""));
                    return null;
                }
                
            }
            
            /*GESTIONANDO SOLICITUD SOBRE LA CUENTA MATRIZ*/
            /*Calculando Cambios pestaña Direcciones*/
            if (encabezadoSolModiCMBean.isActivoCheckModDireccion()) {
                if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                    String msg = "Correo asesor debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co, telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaGestion())) {
                    String msg = "Correo copia a: debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co , telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                rtaTempSolModDir = new CmtBasicaMgl();
                rtaTempSolModDir.setBasicaId(
                        encabezadoSolModiCMBean.getRespuestaModDireccionSelect());
                solicitudModCM.setResultGestionModDir(rtaTempSolModDir);
                if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_DIRECCION).getTipoBasicaId().
                        equals(encabezadoSolModiCMBean.getAccionModDireccionSelect())) {
                    if (modificarDireccionCuentaMatriz()) {
                        direccionExiste = false;
                        msnDireccion = "La Dirección de la Cuenta Matriz <b>"
                                + cuentaMatriz.getNumeroCuenta()
                                + "</b> ha sido modificada.";
                    }else{
                          direccionExiste = true;
                    }
                }
            }

            /*Calculando Cambios pestaña Datos CM*/
            if (encabezadoSolModiCMBean.isActivoCheckModDatosCM()) {
                if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                    String msg = "Correo asesor debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co, telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaGestion())) {
                    String msg = "Correo copia a: debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co , telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                rtaTempSolModDatos = new CmtBasicaMgl();
                rtaTempSolModDatos.setBasicaId(encabezadoSolModiCMBean.getRespuestaModDatosSelect());
                solicitudModCM.setResultGestionModDatosCm(rtaTempSolModDatos);
                if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_CM).getTipoBasicaId().equals(
                        encabezadoSolModiCMBean.getAccionModDatosSelect())) {
                    subEdiMod = subEdificioFacadeLocal.findById(subEdificioGeneralMod.getSubEdificioObj());
                    guardarRegOriginales(subEdiMod, solicitudModCM ,subEdificioGeneralMod,null,null, 1);
                    subEdiMod = subEdiMod.mapearCamposCmtSubEdificioMglMod(subEdificioGeneralMod);
                    subEdificioFacadeLocal.updateSubedificio(subEdiMod);
                    msnDatosCM = "Los Datos de la Cuenta Matriz <b>"
                            + cuentaMatriz.getNumeroCuenta()
                            + "</b> ha sido modificada";
                }
            }

            /*GESTIONANDO SOLICITUD SOBRE LOS SUB-EDICIFIOS DE LA CUENTA MATRIZ*/
            boolean modificacionSE = false;
            if (encabezadoSolModiCMBean.isActivoCheckModSubedificios()) {
                if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                    String msg = "Correo asesor debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co, telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaGestion())) {
                    String msg = "Correo copia a: debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co , telmexla.com ó telmex.com";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msg, ""));
                    return "";
                }
                rtaTempSolModSubE = new CmtBasicaMgl();
                rtaTempSolModSubE.setBasicaId(encabezadoSolModiCMBean.getRespuestaModSubEdiSelect());
                solicitudModCM.setResultGestionModSubEdi(rtaTempSolModSubE);
                
                for (CmtSolicitudSubEdificioMgl sse : subEdificiosModList) {
                    if (sse.getSubEdificioObj() == null) {//Crear Sub-Edificio
                        subEdiMod = sse.mapearCamposCmtSubEdificioMglMod();
                        subEdiMod.setCmtCuentaMatrizMglObj(cuentaMatriz);
                        subEdiMod.setEstrato(cuentaMatriz.getSubEdificioGeneral().getEstrato());
                        if (solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().
                                compareTo(Constant.NUMERO_CM_MGL) == 0) {
                            subEdiMod = subEdificioFacadeLocal.createCm(subEdiMod);
                        } else {
                            subEdiMod = subEdificioFacadeLocal.create(subEdiMod, true);
                        }
                        msnSubEdificios = msnSubEdificios + "Se ha creado el Sub-Edificio "
                                + sse.getNombreSubedificio()
                                + " para la Cuenta Matriz "
                                + cuentaMatriz.getNumeroCuenta() + ". \n";
                    } else {
                        if (sse.isSolicitudEliminar()) {//Eliminar Sub-Edificio
                            if (solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().
                                    compareTo(Constant.NUMERO_CM_MGL) == 0) {
                                subEdificioFacadeLocal.deleteCm(sse.getSubEdificioObj());
                            } else {
                                subEdificioFacadeLocal.delete(sse.getSubEdificioObj());                                
                            }
                            msnSubEdificios = msnSubEdificios + "Se ha eliminado el Sub-Edificio "
                                    + sse.getSubEdificioObj().getNombreSubedificio()
                                    + " para la Cuenta Matriz "
                                    + cuentaMatriz.getNumeroCuenta() + ". \n";
                        } else {//Modificar Sub-Edificio
                            /*Calculando Cambios Direcciones*/
                            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS).getTipoBasicaId().
                                    equals(encabezadoSolModiCMBean.getAccionModSubEdiSelect())) {
                                if (modificarDireccionSubEdificio(sse)) {
                                    modificacionSE = true;
                                }
                            }
                            /*Calculando Cambios  pestaña Sub-Edificios*/
                            if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS).getTipoBasicaId().
                                    equals(encabezadoSolModiCMBean.getAccionModSubEdiSelect())) {
                                subEdiMod = subEdificioFacadeLocal.findById(sse.getSubEdificioObj());
                                guardarRegOriginales(subEdiMod, solicitudModCM, sse,null, null, 2);
                                subEdiMod = subEdiMod.mapearCamposCmtSubEdificioMglMod(sse);
                                modificacionSE = true;
                            }
                            /*Actualizando el Sub-Edificio con todos los cambios encontrados durante la gestión*/
                            if (modificacionSE == true) {
                                boolean cambioNombreSuEdificioPropiaDir = false;
                                if(subEdiMod != null 
                                        && subEdiMod.getNombreSubedificio() != null 
                                        && !subEdiMod.getNombreSubedificio().isEmpty()
                                        && subEdiMod.getListDireccionesMgl() != null 
                                        && !subEdiMod.getListDireccionesMgl().isEmpty()){
                                    cambioNombreSuEdificioPropiaDir = true;
                                }
                                
                                //JDHT
                                /*Se realiza ajuste para que despues de actualizar un sudedificio y hhpp asociados con su propia dir 
                                no lo haga de nuevo y se pierdan los hhpp en RR */
                                boolean actualizarSubEdificioPropiaDirHhpp = false;
                                if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                        Constant.TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS).getTipoBasicaId().
                                        equals(encabezadoSolModiCMBean.getAccionModSubEdiSelect())) {
                                    if (sse.getDireccionSolicitudObj() != null
                                            && sse.getDireccionSolicitudObj().getCmtDirObj() != null
                                            && (Constant.ID_TIPO_DIRECCION_SUBEDIFICIO == sse.getDireccionSolicitudObj().getCmtDirObj().getTdiId())) {
                                        actualizarSubEdificioPropiaDirHhpp = true;
                                    }
                                }
                                
                                //JDHT
                                if(!actualizarSubEdificioPropiaDirHhpp){
                                subEdificioFacadeLocal.update(subEdiMod, true, cambioNombreSuEdificioPropiaDir);  
                                }
                                                             
                                msnSubEdificios = msnSubEdificios + "Se ha actualizado el Sub-Edificio "
                                        + sse.getSubEdificioObj().getNombreSubedificio()
                                        + " para la Cuenta Matriz "
                                        + cuentaMatriz.getNumeroCuenta() + ". \n";
                            }
                        }
                    }
                }
            }

            if (!direccionExiste) {
                
                CmtBasicaMgl estadoActualSolicitudGestionada = new CmtBasicaMgl();
                if (solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getBasicaId() != null) {
                 estadoActualSolicitudGestionada = cmtBasicaMglFacadeLocal.findById(solicitudModCM.getEstadoSolicitudObj().getBasicaId());
                 solicitudModCM.setEstadoSolicitudObj(estadoActualSolicitudGestionada);
                }
                
                /*Actualizando el resultado de la Gestion*/
                CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
                estadoFinalizado = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO);
                estadoPendienteCobertura = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA);
                
                
                //Si no hay solicitud de modificar cobertura o si se encuentra en estado PENDIENTE DE HHPP
                if ((solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp() != null 
                        && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP))
                        || (!encabezadoSolModiCMBean.isActivoCheckModCobertura())) {
                    
                    estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                    solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                    solicitudModCM.setFechaGestion(new Date());
                    solicitudModCM.setTempGestion(solicitudModCM.getTiempoTotalGestionSolicitud());
                    //Actualizo el estado de la gestion.
                    if (encabezadoSolModiCMBean.getRespuestaModDatosSelect() != null) {
                        CmtBasicaMgl resultado = cmtBasicaMglFacadeLocal.
                                findById(encabezadoSolModiCMBean.getRespuestaModDatosSelect());

                        if (resultado.getBasicaId() != null) {
                            solicitudModCM.setResultGestion(resultado);
                        }
                    }
                    if (cuentaMatriz.getSubEdificioGeneral().getNodoObj() != null) {
                        solicitudModCM.setBasicaIdTecnologia(cuentaMatriz.getSubEdificioGeneral().getNodoObj().getNodTipo());
                    }
                    solicitudFacadeLocal.update(solicitudModCM);
                    encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);
                    msn = "Se gestionó con éxito la solicitud N° <b>"
                            + solicitudModCM.getSolicitudCmId()
                            + "</b>, quedando en <i>"
                            + estadoFinalizado.getNombreBasica() + "</i>. \n"
                            + msnDatosCM + " \n"
                            + msnDireccion + " \n"
                            + msnSubEdificios + " \n";
                } else {
                    
                    //Si existe una solicitud de modificar cobertura o si no se encontraba en estado PENDIENTE DE HHPP                    
                    String estado;
                    if (!validarDominioCorreos(solicitudModCM.getCorreoAsesor())) {
                        String msg = "Correo asesor debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co, telmexla.com ó telmex.com";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msg, ""));
                        return "";
                    }
                    if (!validarDominioCorreos(solicitudModCM.getCorreoCopiaGestion())) {
                        String msg = "Correo copia a: debe ser de dominio claro.com.co, telmex.com.co, comcel.com.co , telmexla.com ó telmex.com";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msg, ""));
                        return "";
                    }
                    
                    //Si existe una solicitud de modificar cobertura o si no se encontraba en estado PENDIENTE DE HHPP    
                    if (encabezadoSolModiCMBean.isActivoCheckModCobertura()
                            && solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp() != null
                            && estadoPendienteCobertura.getIdentificadorInternoApp() != null 
                            && !solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(estadoPendienteCobertura.getIdentificadorInternoApp()) ) {
                        estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA).getBasicaId());
                        estado = estadoPendienteCobertura.getNombreBasica();
                    } else {
                        estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                        estado = estadoFinalizado.getNombreBasica();
                        solicitudModCM.setFechaGestion(new Date());
                        solicitudModCM.setTempGestion(solicitudModCM.getTiempoTotalGestionSolicitud());
                    }
                    solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                    fechaInicioTemp = solicitudModCM.getFechaInicioGestionCobertura();
                    solicitudModCM.setFechaInicioGestionCobertura(fechaInicioTemp);
                    if (usuarioLogin.getCedula() != null) {
                        solicitudModCM.setUsuarioGestionCoberturaId(new BigDecimal(usuarioLogin.getCedula()));
                    }
                    solicitudModCM = solicitudFacadeLocal.update(solicitudModCM);
                    encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);
                    msn = "Se gestionó con éxito la solicitud N° <b>"
                            + solicitudModCM.getSolicitudCmId()
                            + "</b>, quedando en <i>"
                            + estado + "</i>. \n"
                            + msnDatosCM + " \n"
                            + msnDireccion + " \n"
                            + msnSubEdificios + " \n";
                }
                crearNotaSolicitud("Gestión Solicitud Modificación de Cuenta Matriz - Área HHPP. ");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                String destinatariosCopia = "";
                if (usuarioLogin != null && usuarioLogin.getEmail() != null
                        && !usuarioLogin.getEmail().isEmpty()) {
                    // Se adiciona el email del usuario solicitante.
                    destinatariosCopia = usuarioLogin.getEmail();
                }
                // los destinatarios se separan por comma.
                if (!destinatariosCopia.isEmpty()) {
                    destinatariosCopia += ",";
                }
                destinatariosCopia += solicitudModCM.getCorreoCopiaSolicitud();
                solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);

                solicitudFacadeLocal.envioCorreoGestion(solicitudModCM);
            }
            
        } catch (ApplicationException ex) {
            LOGGER.error("Se genera error en " + SolicitudModificacionCMBean.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la gestión HHPP. " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        } catch (Exception ex) {
            LOGGER.error("Se genera error en " + SolicitudModificacionCMBean.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la gestión HHPP. " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        }
        return encabezadoSolModiCMBean.cambiarTab(encabezadoSolModiCMBean.getSelectedTab());
    }

    /**
     * Persiste en base de datos y RR la modificación de direcciones.Esta
 modificación se realiza basandose en las siguientes condiciones: CAMBIA
 DIRECCIÓN PRINCIPAL CUENTA MATRIZ: a.Los Sub-Edificios de la Cuenta
 Matriz tienen dirección propia (Tipo Direccion = 3) Cambia la dirección
 de la Cuenta Matriz y HHPP b. Los Sub-Edificios de la Cuenta Matriz no
 tienen dirección propia (Tipo Direccion = 3) Solo Cambia la dirección de
 la Cuenta Matriz c. Para los SubEdificios con o sin direccion propia
 cuando se modifica la direccion de la Cuenta Matriz; se debe buscar los
 HHPP SALAVENTAS y CAMPAMENTOS donde no se debe contemplar ni el nombre ni
 direccion del subedificio, solo la direccion de la cuenta matriz. CAMBIA
 DIRECCIÓN ALTERNA CUENTA MATRIZ: Solo Cambia la dirección de la Cuenta
 Matriz
     *
     * @param sse Solicitud de Modificacion sobre un Sub-Edificio
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationExceptionCM 
     */
    public boolean modificarDireccionCuentaMatriz()  {
        CmtDireccionMgl dirMod;
        List<CmtSubEdificioMgl> listSubEdi;
        List<HhppMgl> listHhpp;
        boolean mod = false;
        int estrato = 0;
        try {
            /*Modificación de la direccion de la cuenta matriz*/
            for (CmtDireccionSolicitudMgl dir : direccionSolModList) {
                estrato= dir.getCmtDirObj().getDireccionObj().getDirEstrato().intValueExact();
                if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_DIRECCION).getTipoBasicaId().
                        equals(encabezadoSolModiCMBean.getAccionModDireccionSelect())) {
                    if (dir.getCmtDirObj().getTdiId() == Constant.ID_TIPO_DIRECCION_ALTERNA) {
                        dirMod = dir.mapearCamposCmtDireccionMgl();
                        dirMod.setCuentaMatrizObj(cuentaMatriz);
                        dirMod.setTdiId(new Integer(dir.getCmtDirObj().getTdiId() + ""));
                        dirMod.setDireccionId(dir.getCmtDirObj().getDireccionId());
                        guardarRegOriginales(null, solicitudModCM, null, dir.getCmtDirObj(), dir, 3);
                        direccionMglFacadeLocal.actualizarDireccionCmOSubEdificio(dirMod, dir, dir.getCmtDirObj().getTdiId(), dir.getDirFormatoIgac(),
                                cuentaMatriz.getSubEdificioGeneral(), usuarioVT, true, usuarioVT, perfilVT, dir.getCamposDrDireccion());
                    }
  
                    if (dir.getCmtDirObj().getTdiId() == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
                        dirMod = dir.mapearCamposCmtDireccionMgl();
                        dirMod.setCuentaMatrizObj(cuentaMatriz);
                        dirMod.setTdiId(new Integer(dir.getCmtDirObj().getTdiId() + ""));
                        dirMod.setDireccionId(dir.getCmtDirObj().getDireccionId());
                        guardarRegOriginales(null, solicitudModCM, null, dir.getCmtDirObj(), dir, 3);
                            dirMod = direccionMglFacadeLocal.actualizarDireccionCmOSubEdificio(dirMod, dir, dir.getCmtDirObj().getTdiId(),
                                    dir.getDirFormatoIgac(), cuentaMatriz.getSubEdificioGeneral(), usuarioVT, true, usuarioVT, perfilVT, dir.getCamposDrDireccion());
                            if (dirMod != null) {
                                listSubEdi = cuentaMatriz.getListCmtSubEdificioMglActivos();
                                // se organizan los subedificios para saber el nombre del primer subedificio
                                List<CmtSubEdificioMgl> listSubEdificios = new ArrayList<>();
                                for (CmtSubEdificioMgl listaSub : listSubEdi) {
                                    if (!cmtBasicaMglFacadeLocal.findByCodigoInternoApp(
                                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId().
                                            equals(listaSub.getEstadoSubEdificioObj().getBasicaId())) {
                                        listSubEdificios.add(listaSub);
                                    }
                                }
                                Collections.sort(listSubEdificios, (CmtSubEdificioMgl a, CmtSubEdificioMgl b) -> a.getNombreSubedificio().compareTo(b.getNombreSubedificio()));
                                String nombreEdifSalYCamp = listSubEdificios.get(0).getNombreSubedificio();
                                if (listSubEdi.size() > 0) {
                                    for (CmtSubEdificioMgl se : listSubEdi) {
                                        LOGGER.error("Primera Torre" + listSubEdificios.get(0).getNombreSubedificio());
                                        // cambio de direcciones de hhpp escenario subedifico con propia direccion
                                        listHhpp = se.getListHhpp();
                                        for (HhppMgl hhpp : listHhpp) {
                                            if (!hhpp.getHhpApart().equalsIgnoreCase("CAMPAMENTO") && !hhpp.getHhpApart().equalsIgnoreCase("SALAVENTAS")) {
                                                if (se.getListDireccionesMgl().size() > 0 && hhpp.getHhpApart() != null && hhpp.getHhpCalle() != null && hhpp.getHhpCalle().contains(" EN ")) {
                                                    // cambio de direcciones de hhpp con entrada en el subedificio
                                                    // unicoedificio
                                                    String nomnreSubedificio = "";
                                                    if (se.getEstadoSubEdificioObj() != null && se.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                                                            && !se.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                                                            equals(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA)) {
                                                        // multiedificio
                                                        nomnreSubedificio = se.getNombreSubedificio();
                                                    }
                                                    direccionMglFacadeLocal.actualizarDireccionHHPPModCm(
                                                            hhpp, hhpp, dirMod,
                                                            dir.getDirFormatoIgac(),
                                                            nomnreSubedificio, solicitudModCM.getSolicitudCmId() + "",
                                                            solicitudModCM.getTipoSolicitudObj().getNombreTipo(),
                                                            usuarioVT, usuarioVT, perfilVT, se, dir.getCamposDrDireccion(), true, nombreEdifSalYCamp);
                                                } else {
                                                    String nomnreSubedificio;
                                                     // multiedificio
                                                        nomnreSubedificio = se.getNombreSubedificio();                                                    
                                                    // cambio de direcciones de hhpp escenario normal
                                                    direccionMglFacadeLocal.actualizarDireccionHHPPModCm(
                                                            hhpp, hhpp, dirMod,
                                                            dir.getDirFormatoIgac(),
                                                            nomnreSubedificio, solicitudModCM.getSolicitudCmId() + "",
                                                            solicitudModCM.getTipoSolicitudObj().getNombreTipo(),
                                                            usuarioVT, usuarioVT, perfilVT, se, dir.getCamposDrDireccion(), false, nombreEdifSalYCamp);
                                                }
                                            } else {
                                                LOGGER.error("Primera Torre" + listSubEdificios.get(0).getNombreSubedificio());
                                                // cambio de direcciones de hhpp salaventas y campamentos multiedificio
                                                direccionMglFacadeLocal.actualizarDireccionHHPPModCm(
                                                        hhpp, hhpp, dirMod,
                                                        dir.getDirFormatoIgac(),
                                                        "", solicitudModCM.getSolicitudCmId() + "",
                                                        solicitudModCM.getTipoSolicitudObj().getNombreTipo(),
                                                        usuarioVT, usuarioVT, perfilVT, se, dir.getCamposDrDireccion(), false, nombreEdifSalYCamp);
                                            }

                                        }


                                    }

                                }
                            } else {
                                mod = false;
                            }
                        
             
                    }
                }
                mod = true;
            }
            
        } catch ( NumberFormatException e) {
            String msnErr = "Ha ocurrido un error modificando la dirección de CM";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
            throw e;
        } catch (ApplicationException | ApplicationExceptionCM e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: modificarDireccionCuentaMatriz(). " + e.getMessage(), e, LOGGER);
        }
        return mod;
    }

    /**
     * Persiste en base de datos y RR la modificación de direcciones.Esta
 modificación se realiza basandose en las siguientes condiciones: CAMBIA
 DIRECCION PROPIA SUBEDIFICIO Cambia la dirección del Sub-Edificio y HHPP
 asociados a ese Sub-Edificio.El nombre del subedifio no se debe
 contemplar en la construccion de la dirección. CAMBIA NOMBRE DE
 SUBEDIFICIO CON DIRECCION PROPIA No se debe cambiar la dirección de los
 HHPP asociados a ese Sub-Edificio. No se debe cambiar la direccion del
 Sub-Edificio. Solo el nombre. CAMBIA NOMBRE DE SUBEDIFICIO SIN DIRECCION
 PROPIA No existe dirección del Sub-Edificio; por lo tanto solo se
 modifica el nombre. Cambia la direccion del HHPP asociados a ese
 Sub-Edificio.
     *
     * @param sse Solicitud de Modificacion sobre un Sub-Edificio
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean modificarDireccionSubEdificio(CmtSolicitudSubEdificioMgl sse)  {
        CmtDireccionMgl dirMod;
        List<HhppMgl> listHhpp;
        boolean procesoExitoso = false;
        try {
            //CAMBIA DIRECCION PROPIA SUBEDIFICIO
            int condicion1 = 0;
            // CAMBIA NOMBRE DE SUBEDIFICIO CON DIRECCION PROPIA
            int condicion2 = 0;
            //CAMBIA NOMBRE DE SUBEDIFICIO SIN DIRECCION PROPIA
            int condicion3 = 0;
            
            if (sse.getDireccionSolicitudObj() != null
                    && sse.getDireccionSolicitudObj().getCmtDirObj() != null 
                    &&(Constant.ID_TIPO_DIRECCION_SUBEDIFICIO == sse.getDireccionSolicitudObj().getCmtDirObj().getTdiId())) {
                condicion1 = 1;
            } else {
                dirMod = direccionMglFacadeLocal.findByIdSubEdificio(cuentaMatriz.getSubEdificioGeneral().getSubEdificioId());
                if ((dirMod == null || !(Constant.ID_TIPO_DIRECCION_SUBEDIFICIO == dirMod.getTdiId()))
                        && (sse.getNombreSubedificio() != null && !sse.getNombreSubedificio().equals(""))) {
                    condicion3 = 1;
                }
            }
            if (condicion1 == 1) {
                //Actualizando dirección de Sub-Edificio
                if (sse.getDireccionSolicitudObj() != null) {
                    dirMod = sse.getDireccionSolicitudObj().mapearCamposCmtDireccionMgl();
                    dirMod.setCuentaMatrizObj(cuentaMatriz);
                    dirMod.setTdiId(new Integer(sse.getDireccionSolicitudObj().getCmtDirObj().getTdiId() + ""));
                    dirMod.setDireccionId(sse.getDireccionSolicitudObj().getCmtDirObj().getDireccionId());
                    direccionMglFacadeLocal.actualizarDireccionCmOSubEdificio(dirMod, sse.getDireccionSolicitudObj(),
                            sse.getDireccionSolicitudObj().getCmtDirObj().getTdiId(),
                            sse.getDireccionSolicitudObj().getDirFormatoIgac(),
                            sse.getSubEdificioObj(), usuarioVT, true, usuarioVT, perfilVT, sse.getDireccionSolicitudObj().getCamposDrDireccion());
                    DrDireccion drdirSubEdificio = sse.getDireccionSolicitudObj().getCamposDrDireccion();
                    listHhpp = sse.getSubEdificioObj().getListHhpp();
                    for (HhppMgl hhpp : listHhpp) {
                        //if (hhpp.getHhpApart() != null && !(hhpp.getHhpApart().equals("CAMPAMENTO") || hhpp.getHhpApart().equals("SALAVENTAS"))) {
                            DrDireccion drdirTemp = drdirSubEdificio.clone();//limpia el objeto
                            CmtDireccionDetalleMglManager cmtDirDetMag = new CmtDireccionDetalleMglManager();
                            BigDecimal temSubID = null;
                            if (hhpp.getSdiId() != null) {
                                temSubID = hhpp.getSdiId();
                            }
                            List<CmtDireccionDetalladaMgl> lisDirDetTemporal = cmtDirDetMag.findDireccionDetallaByDirIdSdirId(hhpp.getDirId(), temSubID);
                            if (lisDirDetTemporal != null && lisDirDetTemporal.size() > 0) {//debo llenar con los datos del complemento la subdireccion que cambiaron
                                CmtDireccionDetalladaMgl dirDetTemporal = lisDirDetTemporal.get(0);
                                if (dirDetTemporal.getCpTipoNivel5() != null && !dirDetTemporal.getCpTipoNivel5().isEmpty()) {
                                    drdirTemp.setCpTipoNivel5(dirDetTemporal.getCpTipoNivel5());
                                }
                                if (dirDetTemporal.getCpValorNivel5() != null && !dirDetTemporal.getCpValorNivel5().isEmpty()) {
                                    drdirTemp.setCpValorNivel5(dirDetTemporal.getCpValorNivel5());
                                }
                                if (dirDetTemporal.getCpTipoNivel6() != null && !dirDetTemporal.getCpTipoNivel6().isEmpty()) {
                                    drdirTemp.setCpTipoNivel6(dirDetTemporal.getCpTipoNivel6());
                                }
                                if (dirDetTemporal.getCpValorNivel6() != null && !dirDetTemporal.getCpValorNivel6().isEmpty()) {
                                    drdirTemp.setCpValorNivel6(dirDetTemporal.getCpValorNivel6());
                                }
                            }
                            direccionMglFacadeLocal.actualizarDireccionHHPP(hhpp, hhpp, dirMod, sse.getDireccionSolicitudObj().getDirFormatoIgac(),
                                    "", solicitudModCM.getSolicitudCmId() + "", solicitudModCM.getTipoSolicitudObj().getNombreTipo(),
                                    usuarioVT, usuarioVT, perfilVT, drdirTemp);
                        //}
                    }
                    procesoExitoso = true;
                } else {
                    procesoExitoso = false;
                }
            }
            if (condicion3 == 1) {
                dirMod = cuentaMatriz.getDireccionPrincipal();
                listHhpp = sse.getSubEdificioObj().getListHhpp();
                if (sse.getDireccionSolicitudObj() != null) {
                    DrDireccion drdirSubEdificio = sse.getDireccionSolicitudObj().getCamposDrDireccion();
                    for (HhppMgl hhpp : listHhpp) {
                        if (hhpp.getHhpApart() != null) {
                            DrDireccion drdirTemp = drdirSubEdificio.clone();//limpia el objeto
                            CmtDireccionDetalleMglManager cmtDirDetMag = new CmtDireccionDetalleMglManager();
                            CmtDireccionDetalladaMgl dirDetTemporal = cmtDirDetMag.findByHhPP(hhpp);
                            if (cmtDirDetMag != null) {//debo llenar con los datos del complemento la subdireccion que cambiaron
                                if (dirDetTemporal.getCpTipoNivel5() != null && !dirDetTemporal.getCpTipoNivel5().isEmpty()) {
                                    drdirTemp.setCpTipoNivel5(dirDetTemporal.getCpTipoNivel5());
                                }
                                if (dirDetTemporal.getCpValorNivel5() != null && !dirDetTemporal.getCpValorNivel5().isEmpty()) {
                                    drdirTemp.setCpValorNivel5(dirDetTemporal.getCpValorNivel5());
                                }
                                if (dirDetTemporal.getCpTipoNivel6() != null && !dirDetTemporal.getCpTipoNivel6().isEmpty()) {
                                    drdirTemp.setCpTipoNivel6(dirDetTemporal.getCpTipoNivel6());
                                }
                                if (dirDetTemporal.getCpValorNivel6() != null && !dirDetTemporal.getCpValorNivel6().isEmpty()) {
                                    drdirTemp.setCpValorNivel6(dirDetTemporal.getCpValorNivel6());
                                }
                            }
                            direccionMglFacadeLocal.actualizarDireccionHHPP(hhpp, hhpp, dirMod, dirMod.getDireccionObj().getDirFormatoIgac(),
                                    sse.getNombreSubedificio() != null ? sse.getNombreSubedificio() : sse.getSubEdificioObj().getNombreSubedificio(),
                                    solicitudModCM.getSolicitudCmId() + "", solicitudModCM.getTipoSolicitudObj().getNombreTipo(),
                                    usuarioVT, usuarioVT, perfilVT, drdirTemp);
                        }
                    }
                    procesoExitoso = true;
                } else {
                    procesoExitoso = false;
                }
            }
        } catch (ApplicationException | ApplicationExceptionCM | CloneNotSupportedException | NumberFormatException e) {
            String msnErr = "Ha ocurrido un error modificando la dirección de Sub-Edificio";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        }
        return procesoExitoso;
    }

    /**
     * Persiste en base de datos y RR la modificación de la cobertura de los
     * HHPP.Esta modificación se realiza basandose en las siguientes
 condiciones: CAMBIA COBERTURA DE CUENTA MATRIZ MULTI EDIFICIO a.Unico
 Sub-Edificio: Actualiza la cobertura de la Cuenta Matriz y HHPP
 asociados. b. Varios Sub-Edificios: Solo modifica la cobertura de la
 Cuenta Matriz. CAMBIA COBERTURA DE CUENTA MATRIZ NO MULTI EDIFICIO Solo
 modifica la cobertura de la Cuenta Matriz CAMBIA COBERTURA DE
 SUB-EDIFICIO PERTENECIENTE A UNA CUENTA MATRIZ MULTI EDIFICIO Modifica la
 cobertura del Sub-Edificio y los HHPP asociados.
     *
     * @param sse Solicitud de Modificacion sobre un Sub-Edificio /Cuenta Matriz
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String modificarCoberturaHHPP(CmtSubEdificioMgl sse)  {
        try {
            List<HhppMgl> listHhpp = null;
            //Verificamos si se trata del SubEdificio General
            if (sse.getSubEdificioId().compareTo(
                    cuentaMatriz.getSubEdificioGeneral().getSubEdificioId()) == 0) {
                /*Validamos si una cm MultiEdificio*/
                if (!cuentaMatriz.getSubEdificioGeneral().getEstadoSubEdificioObj()
                        .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    /*Cuando es Único Edificio*/
                    listHhpp = sse.getListHhpp();
                }
            } else {/*Cuando es Sub-Edificio*/
                listHhpp = sse.getListHhpp();
            }
            String msnCobertura = "";
            if (listHhpp != null && !listHhpp.isEmpty()) {
                for (HhppMgl hhpp : listHhpp) {
                    if (sse.getNodoObj().getNodTipo().getBasicaId().
                            compareTo(hhpp.getNodId().getNodTipo().getBasicaId()) == 0) {

                        hhpp.setNodId(sse.getNodoObj());
                        if (solicitudModCM.getCuentaMatrizObj().getNumeroCuenta().
                                compareTo(Constant.NUMERO_CM_MGL) == 0) {
                            hhppMglFacadeLocal.update(hhpp);
                        } else {
                            hhppMglFacadeLocal.actualizarNodoHhpp(hhpp, solicitudModCM.getSolicitudCmId() + "", solicitudModCM.getTipoSolicitudObj().getNombreTipo(), usuarioVT);
                        }
                    }
                }
                msnCobertura = msnCobertura + "Se ha actualizado la cobertura del los HHPP para el Sub-Edificio "
                        + sse.getNombreSubedificio()
                        + ". \n";

            }
            if (msnCobertura != null && !msnCobertura.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msnCobertura, ""));
            }
        } catch (ApplicationException | NumberFormatException e) {
            String msnErr = "Ha ocurrido un error modificando la dirección de Sub-Edificio";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } 
        return null;
    }

    /**
     * Persiste en base de datos la Gestion de los siguientes tipos de
     * Modificación de Cuenta Matriz: 'Modificar Cobertura'. La solcitud no
     * queda Gestionada hasta que se realice la Gestión por el área de HHPP y la
     * Gestión por el área de Diseño de Red. Hay 4 estados para la Gestión de la
     * solicitud de Modificacion de Cuenta Matriz: PENDIENTE: No se a realizado
     * nungún tipo e Gestión sobre la solicitud PENDIENTE COBERTURA: Se realizó
     * Gestión de HHPP y queda pendiente la gestión de COBERTURA PENDIENTE HHPP:
     * Se realizó Gestión de COBERTURA y queda pendiente la gestión de HHPP
     *
     * @return la pagina de inicio gestión de la solicitud. 'Modificar Datos CM'
     */
    public String gestionarDisenoRed() {
        try {
            CmtSubEdificioMgl subEdiMod;
            List<CmtSubEdificioMgl> listaAprobModCob = new ArrayList<CmtSubEdificioMgl>();
            String msn = "";
            String msnCobertura = "";
            Date fechaInicioTemp;
            CmtBasicaMgl rtaTempSolModCob = new CmtBasicaMgl();
            BigDecimal selecionVacio = BigDecimal.ZERO;
            NodoMgl nodiDis;
            CmtBasicaMgl estadoFinalizado;
            CmtBasicaMgl estadoPendienteHHPP;
            boolean habilitarRR = false;            
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            
            if (encabezadoSolModiCMBean.isActivoCheckModCobertura() && (selecionVacio.equals(encabezadoSolModiCMBean.getRespuestaModCoberturaSelect()) || encabezadoSolModiCMBean.getRespuestaModCoberturaSelect() == null)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Campo 'Resultado Gestión Cobertura' es requeridos", ""));
                return null;
            }
            
            if (solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getBasicaId() != null) {
                CmtBasicaMgl estadoActualSolicitud = cmtBasicaMglFacadeLocal.findById(solicitudModCM.getEstadoSolicitudObj().getBasicaId());
                if (hayModDirecciones && Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE.equals(estadoActualSolicitud.getIdentificadorInternoApp())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede realizar la operacion "
                            + "hasta que se realice la Gestión HHPP. \n "
                            + "Existe una solicitud de cambio de dirección y la cobertura quedaría sobre la dirección antigua.", ""));
                    return null;
                } else {
                    //Valida si la solicitud ya se gestionó y se encuentra en estado FINALIZADO y no es necesario volver a gestionar
                    if ((estadoActualSolicitud != null && estadoActualSolicitud.getIdentificadorInternoApp() != null
                            && estadoActualSolicitud.getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO))) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LA solicitud ya fue gestionada y se encuentra en estado FINALIZADO, no es necesario gestionarla nuevamente.", ""));
                        return null;
                    }
                }
            }           
            
            estadoFinalizado = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO);
            estadoPendienteHHPP = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP);
            
            for (CmtSolicitudSubEdificioMgl sse : coberturaModList) {
                if (cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ACCION_MODIFICAR_COBERTURA).getTipoBasicaId()
                        .equals(encabezadoSolModiCMBean.getAccionModCoberturaSelect())) {
                    
                     if (datosGestion.size() > 0) {
                        Iterator<Map.Entry<CmtSolicitudSubEdificioMgl, String>> iterator = datosGestion.entrySet().iterator();

                        while (iterator.hasNext()) {

                            Map.Entry<CmtSolicitudSubEdificioMgl, String> n = iterator.next();
                            if (sse.getSolSubEdificioId().compareTo(n.getKey().getSolSubEdificioId()) == 0) {
                                NodoMgl nodoGestion = new NodoMgl();
                                nodoGestion.setNodCodigo(n.getValue());
                                sse.setNodoDisGestion(nodoGestion);
                            }

                        }
                    }

                    if (sse.getNodoDisGestion() != null && sse.getNodoDisGestion().getNodCodigo()
                            != null && !sse.getNodoDisGestion().getNodCodigo().isEmpty()) {
                        nodiDis = validarNodo(sse.getNodoDisGestion().getNodCodigo());
                        if (nodiDis != null) {
                            sse.setNodoDisenoObj(nodiDis);
                            solicitudSubEdificioFacadeLocal.update(sse);
                            subEdiMod = subEdificioFacadeLocal.findById(sse.getSubEdificioObj());
                            subEdiMod.setNodoObj(nodiDis);
                            listaAprobModCob.add(subEdiMod);
                        }
                    } else {
                        msn = "Los Campos 'Nodo Diseño' son obigatorios";
                    }
                }
                
            }
   
            if (listaAprobModCob != null && !listaAprobModCob.isEmpty()) {
                //se actualiza el nodo del edificio general
                CmtSubEdificioMgl subEdiModGeneral = subEdificioFacadeLocal.findById(listaAprobModCob.get(0).getCuentaMatrizObj().getSubEdificioGeneral());
                if (subEdiModGeneral != null && !subEdiModGeneral.getCuentaMatrizObj().isUnicoSubEdificioBoolean()) {
                    subEdiModGeneral.setNodoObj(coberturaModList.get(0).getNodoDisGestion());
                    listaAprobModCob.add(subEdiModGeneral);
                }
            }
         
            if ("".equals(msn)) {
                if (listaAprobModCob != null && !listaAprobModCob.isEmpty()) {
                    for (CmtSubEdificioMgl se : listaAprobModCob) {

                        if (habilitarRR) {

                            String nombreSubedificioRR;
                        boolean igualTecno = false;

                        //Consulto si son la misma tecnologia en MGL y en RR
                        CmtCuentaMatrizMgl cuenta = cmtCuentaMatrizMglFacadeLocal.findByIdCM(se.getCuentaMatrizObj().getCuentaMatrizId());
                        if (cuenta != null && cuenta.isUnicoSubEdificioBoolean()) {
                            
                            ResponseManttoEdificioList unicoEdi
                                    = subEdificioFacadeLocal.consultaUnicoSubedificioRR(cuenta);

                            if (unicoEdi.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                LOGGER.error(unicoEdi.getMensaje());
                            } else {
                                List<ResponseDataManttoEdificio> lstDataManttoEdificios
                                        = unicoEdi.getListManttoEdificios();

                                if (lstDataManttoEdificios.size() > 0) {
                                    ResponseDataManttoEdificio manttoEdificio = lstDataManttoEdificios.get(0);
                                    String codigoNodo = manttoEdificio.getNodo().trim();
                                    if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                        NodoMgl nodoMgl = nodoMglFacadeLocal.findByCodigo(codigoNodo);
                                        if (nodoMgl != null && se.getNodoObj() != null) {
                                            if (nodoMgl.getNodTipo().getBasicaId().
                                                    compareTo(se.getNodoObj().getNodTipo().getBasicaId()) == 0) {
                                                //Si son las mismas tecnologias 
                                                igualTecno = true;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //Es Multiedificio
                            ResponseManttoSubEdificiosList edificioRr;
                            edificioRr = subEdificioFacadeLocal.consultaSubedificioRR(se);

                            if (edificioRr.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                LOGGER.error(edificioRr.getMensaje());
                            } else {
                                if (edificioRr.getListManttSubEdificios() != null && edificioRr.getListManttSubEdificios().size() > 0) {
                                    for (ResponseDataManttoSubEdificios subedificios : edificioRr.getListManttSubEdificios()) {
                                        if (subedificios.getCodigo().trim().
                                                equalsIgnoreCase(se.getCodigoRr())) {
                                            String codigoNodo = subedificios.getNodo().trim();
                                            nombreSubedificioRR = subedificios.getDescripcion().trim();
                                            se.setNombreEntSubedificio(nombreSubedificioRR);
                                            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                                NodoMgl nodoMgl = nodoMglFacadeLocal.findByCodigo(codigoNodo);
                                                if (nodoMgl != null && se.getNodoObj() != null) {
                                                    if (nodoMgl.getNodTipo().getBasicaId().
                                                            compareTo(se.getNodoObj().getNodTipo().getBasicaId()) == 0) {
                                                        //Si son las mismas tecnologias 
                                                        igualTecno = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (igualTecno) {
                            subEdificioFacadeLocal.updateSubedificio(se);
                        }
                    }

                    msnCobertura = msnCobertura + "Se ha actualizado la cobertura  del Sub-Edificio "
                            + se.getNombreSubedificio()
                            + " para la Cuenta Matriz "
                            + cuentaMatriz.getNumeroCuenta() + ". \n";
                    //Se Modifica la tecnologia en los hhpp del subedificio si tiene
                    //Autor: victor bocanegra 
                    modificarCoberturaHHPP(se);
                    //Se Modifica la tecnologia sub del subedificio si tiene
                    //Autor: victor bocanegra 
                    CmtTecnologiaSubMgl cmtTecnologiaSubMgl = cmtTecnologiaSubMglFacadeLocal.
                            findBySubEdificioTecnologia(se, tecnologiaSelect);
                    if (cmtTecnologiaSubMgl != null && cmtTecnologiaSubMgl.getTecnoSubedificioId() != null) {
                            if (se.getNodoObj() != null) {
                                cmtTecnologiaSubMgl.setNodoId(se.getNodoObj());
                            }
                            cmtTecnologiaSubMglFacadeLocal.updateTecnoSub(cmtTecnologiaSubMgl, usuarioVT, perfilVT);

                        }

                    }
                }

                CmtBasicaMgl estadoSolicitud = new CmtBasicaMgl();
                rtaTempSolModCob.setBasicaId(encabezadoSolModiCMBean.getRespuestaModCoberturaSelect());
                solicitudModCM.setResultGestionModCobertura(rtaTempSolModCob);
                
                //se realiza busqueda del estado actual de la solicitud ya que esta llegando null el identificador app pero si llega el id.
                CmtBasicaMgl estadoActualSolicitud = null;
                if(solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getBasicaId() != null){
                 estadoActualSolicitud = cmtBasicaMglFacadeLocal.findById(solicitudModCM.getEstadoSolicitudObj().getBasicaId());   
                }
                if(estadoActualSolicitud != null
                        && estadoActualSolicitud.getIdentificadorInternoApp() != null 
                        && !estadoActualSolicitud.getIdentificadorInternoApp().isEmpty() ){
                    solicitudModCM.setEstadoSolicitudObj(estadoActualSolicitud);
                }
                
                
                //si la solicitud se encuentra en PENDIENTE COBERTURA o no se creo ningun solicitud de DATOS, DIRECCION o SUBEDIFICIO
                // ya han gestionado alguna de las otras pestañas o solo se creo esta solicitud de cobertura.
              if ((solicitudModCM.getEstadoSolicitudObj() != null 
                      && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp() != null 
                      && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA))
                        || (!encabezadoSolModiCMBean.isActivoCheckModDatosCM()
                        && !encabezadoSolModiCMBean.isActivoCheckModDireccion()
                        && !encabezadoSolModiCMBean.isActivoCheckModSubedificios())) {
                  
                    estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                    solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                    if (usuarioLogin.getCedula() != null) {
                        solicitudModCM.setUsuarioGestionCoberturaId(new BigDecimal(usuarioLogin.getCedula()));
                    }
                    solicitudModCM.setFechaGestionCobertura(new Date());
                    solicitudModCM.setTempGestion(solicitudModCM.getTiempoGestionCoberturaSolicitud());
                  if (encabezadoSolModiCMBean.isActivoCheckModDatosCM() || 
                          encabezadoSolModiCMBean.isActivoCheckModDireccion() || 
                          encabezadoSolModiCMBean.isActivoCheckModCobertura()) {
                      solicitudModCM.setFechaGestion(new Date());
                  }
                    solicitudFacadeLocal.update(solicitudModCM);
                    encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);
                    msn = "Se gestionó con éxito la solicitud N° <b>"
                            + solicitudModCM.getSolicitudCmId()
                            + "</b>, quedando en "
                            + estadoFinalizado.getNombreBasica() + ". \n "
                            + msnCobertura;
                } else {
                    String estado;
                    if (encabezadoSolModiCMBean.isActivoCheckModDatosCM() || encabezadoSolModiCMBean.isActivoCheckModDireccion() || encabezadoSolModiCMBean.isActivoCheckModCobertura()) {
                        estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP).getBasicaId());
                        estado = estadoPendienteHHPP.getNombreBasica();
                    } else {
                        estadoSolicitud.setBasicaId(cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                        estado = estadoFinalizado.getNombreBasica();
                    }
                    
                    solicitudModCM.setEstadoSolicitudObj(estadoSolicitud);
                    fechaInicioTemp = solicitudModCM.getFechaInicioGestionHhpp();
                    solicitudModCM.setFechaInicioGestionHhpp(null);
                    solicitudModCM.setUsuarioGestionId(null);
                    solicitudModCM.setFechaGestionCobertura(new Date());
                    solicitudModCM.setTempGestion(solicitudModCM.getTiempoGestionCoberturaSolicitud());
                    solicitudModCM = solicitudFacadeLocal.update(solicitudModCM);
                    solicitudModCM.setFechaInicioGestionHhpp(fechaInicioTemp);
                    if (usuarioLogin.getCedula() != null) {
                        solicitudModCM.setUsuarioGestionId(new BigDecimal(usuarioLogin.getCedula()));
                    }
                    encabezadoSolModiCMBean.setSolicitudModCM(solicitudModCM);
                    
                    msn = "Se gestionó con éxito la solicitud N° <b>"
                            + solicitudModCM.getSolicitudCmId()
                            + "</b>, quedando en "
                            + estado + ". \n"
                            + msnCobertura;
                }
                crearNotaSolicitud("Gestión Solicitud Modificación de Cuenta Matriz - Área Diseño. ");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                String destinatariosCopia = "";
                if (usuarioLogin != null && usuarioLogin.getEmail() != null
                        && !usuarioLogin.getEmail().isEmpty()) {
                    // Se adiciona el email del usuario solicitante.
                    destinatariosCopia = usuarioLogin.getEmail();
                }
                // los destinatarios se separan por comma.
                if (!destinatariosCopia.isEmpty()) {
                    destinatariosCopia += ",";
                }
                destinatariosCopia += solicitudModCM.getCorreoCopiaSolicitud();
                solicitudModCM.setCorreoCopiaSolicitud(destinatariosCopia);

                solicitudFacadeLocal.envioCorreoGestion(solicitudModCM);
                
                
            

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return null;
            }
            
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la gestión Diseño Red. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (UniformInterfaceException | IOException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean : gestionarDisenoRed(). " + e.getMessage(), e, LOGGER);
        }
        return encabezadoSolModiCMBean.cambiarTab(encabezadoSolModiCMBean.getSelectedTab());
    }
    
    public String limpiarDataSubedificioGeneralModificar() {
        try {
            
            subEdificioGeneralMod = new CmtSolicitudSubEdificioMgl();
            subEdificioGeneralMod.setSubEdificioObj(cuentaMatriz.getSubEdificioGeneral());
            subEdificioGeneralMod.setTipoEdificioObj(new CmtBasicaMgl());
            subEdificioGeneralMod.setDireccionSolicitudObj(new CmtDireccionSolicitudMgl());
            subEdificioGeneralMod.setCompaniaAdministracionObj(new CmtCompaniaMgl());
            subEdificioGeneralMod.setCompaniaAscensorObj(new CmtCompaniaMgl());
            subEdificioGeneralMod.setNodoObj(new NodoMgl());
            subEdificioGeneralMod.setNodoDisenoObj(new NodoMgl());
            subEdificioGeneralMod.setSolModCobertura(new Short("0"));
            subEdificioGeneralMod.setSolModDatos(new Short("0"));
            subEdificioGeneralMod.setSolModEliminacion(new Short("0"));
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudModificacionCMBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudModificacionCMBean. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Genera el listado de registros que pueden ser modificados para las
     * pestañas: Modificar Sub-Edificios y Modificar Cobertura.La generación de
 esta lista se realiza de la siguiente manera: LISTA DE SUB-EDIFICIOS -
 PESTAÑA Modificar Sub-Edificios Muestra todos los Sub-Edificios asociados
 a una Cuenta Matriz MultiEdificio. LISTA DE SUB-EDIFICIOS - PESTAÑA
 Modificar Cobertura a. Si la Cuenta Matriz es MultiEdificio solo se carga
 el Sub-Edificio General b. Si la Cuenta Matriz no es MultiEdificio solo
 se cargan los Sub-Edificios de la Cuenta Matriz. No se debe carga el
 Sub-Edificio General
     *
     * @return 
     */
    public String limpiarDataSubEdificiosModificar() {
        try {
            mostrarCrearSubedificio = false;
            nombreGenSubEdiText = "";
            nombreGenSubEdiSel = "";
            subEdificiosModList = new ArrayList<>();
            coberturaModList = new ArrayList<>();
            solicitudesCoberturasList = new ArrayList<>();
            CmtDireccionSolicitudMgl dirMod;
            CmtSolicitudSubEdificioMgl solSeMod;
            List<CmtSubEdificioMgl> subEList = cuentaMatriz.getSubEdificiosMglNoGeneral();
            
            isSubEdiDirPropia = false;
            isMultiEdificio = cuentaMatriz.getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            
            
            solSeMod = new CmtSolicitudSubEdificioMgl();
            solSeMod.setSubEdificioObj(cuentaMatriz.getSubEdificioGeneral());
            solSeMod.setCompaniaAdministracionObj(new CmtCompaniaMgl());
            solSeMod.setDireccionSolicitudObj(new CmtDireccionSolicitudMgl());
            solSeMod.setCompaniaAscensorObj(new CmtCompaniaMgl());
            solSeMod.setNodoObj(new NodoMgl());
            solSeMod.setNodoDisenoObj(new NodoMgl());
            solSeMod.setTipoEdificioObj(new CmtBasicaMgl());
            solSeMod.setSolModCobertura(new Short("0"));
            solSeMod.setSolModDatos(new Short("0"));
            solSeMod.setSolModEliminacion(new Short("0"));
            solSeMod.setEdificioGeneral(true);
            coberturaModList.add(solSeMod);
            
            
            CmtDireccionMgl dir;
            for (CmtSubEdificioMgl se : subEList) {
                
                solSeMod = new CmtSolicitudSubEdificioMgl();
                solSeMod.setSubEdificioObj(se);
                solSeMod.setCompaniaAdministracionObj(new CmtCompaniaMgl());
                solSeMod.setCompaniaAscensorObj(new CmtCompaniaMgl());
                solSeMod.setNodoObj(new NodoMgl());
                solSeMod.setNodoDisenoObj(new NodoMgl());
                solSeMod.setTipoEdificioObj(new CmtBasicaMgl());
                solSeMod.setSolModCobertura(new Short("0"));
                solSeMod.setSolModDatos(new Short("0"));
                solSeMod.setSolModEliminacion(new Short("0"));
                solSeMod.setEdificioGeneral(false);
                
                if (se.getListDireccionesMgl() != null && se.getListDireccionesMgl().size() > 0) {
                    if (se.getListDireccionesMgl().get(0).getTdiId() == Constant.ID_TIPO_DIRECCION_SUBEDIFICIO) {
                        dirMod = new CmtDireccionSolicitudMgl();
                        dirMod.setCmtDirObj(se.getListDireccionesMgl().get(0));
                        solSeMod.setDireccionSolicitudObj(dirMod);
                        isSubEdiDirPropia = true;
                    }
                }
                if (se.getSubEdificioId().compareTo(cuentaMatriz.getSubEdificioGeneral().getSubEdificioId()) != 0) {
                    subEdificiosModList.add(solSeMod);
                }
                if (isMultiEdificio) {
                    coberturaModList.add(solSeMod);
                }
                
            }
            
            if (!subEdificiosModList.isEmpty()) {
                solSubEdificioSelecMod = subEdificiosModList.get(0);
            }
            
            solicitudesSubEdificiosList = new ArrayList<CmtSolicitudSubEdificioMgl>();
            solicitudesDireccionesList = new ArrayList<CmtDireccionSolicitudMgl>();
            
            solSubEdificioNuevo = new CmtSolicitudSubEdificioMgl();
            
            
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: limpiarDataSubEdificiosModificar(). " + e.getMessage(), e, LOGGER);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: limpiarDataSubEdificiosModificar(). " + e.getMessage(), e, LOGGER);

        }
        return null;
    }
    
    public String limpiarDataDireccionesModificar() {
        try {
            cuentaMatriz.getDireccionPrincipal().getDireccionObj().getUbicacion().getGpoIdObj().getGpoNombre();//string

            CmtDireccionSolicitudMgl dirMod;
            direccionSolModList = new ArrayList<>();
            List<CmtDireccionMgl> direccionMglList = new ArrayList<>();
            CmtDireccionMgl dirPrincipal = cuentaMatriz.getDireccionPrincipal();
            List<CmtDireccionMgl> dirAlternas = cuentaMatriz.getDireccionAlternaList();
            if (dirPrincipal != null) {
                direccionMglList.add(dirPrincipal);
            }
            if (dirAlternas != null && dirAlternas.size() > 0) {
                direccionMglList.addAll(dirAlternas);
            }
            
            for (CmtDireccionMgl dir : direccionMglList) {
                dirMod = new CmtDireccionSolicitudMgl();
                dirMod.setCmtDirObj(dir);
                
                direccionSolModList.add(dirMod);
            }
            if (!direccionSolModList.isEmpty()) {
                direccionSolSelecMod = direccionSolModList.get(0);
                direccionSolSelecMod.setSeleccionado(true);
            }
            
            listaBarriosDireccion = new HashMap<>();
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: limpiarDataDireccionesModificar(). " + e.getMessage(), e, LOGGER);

        }
        return null;
    }
    
    public String generarNombreSubEdificio(CmtSolicitudSubEdificioMgl solSubEdi)  {
        try {
            if (solSubEdi.getNombreSubedificio() == null) {
                solSubEdi.setNombreSubedificio("");
            }

            if (nombreGenSubEdiSel == null) {
                nombreGenSubEdiSel = "";
            }

            if (!"".equals(nombreGenSubEdiText)) {
                nombreGenSubEdiText = " " + nombreGenSubEdiText;
            }

            if (!"".equals(solSubEdi.getNombreSubedificio())) {
                nombreGenSubEdiSel = " " + nombreGenSubEdiSel;
            }

            String tempText = solSubEdi.getNombreSubedificio() + nombreGenSubEdiSel + nombreGenSubEdiText;
            tempText = tempText.replaceAll("  ", " ");

            if ("".equals(solSubEdi.getNombreSubedificio()) && !"".equals(nombreGenSubEdiSel)) {
                conteoNombreGenSubEdi = 1;
                CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.
                        findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
                tipoEdificioNuevo = cmtBasicaMglFacadeLocal.findByNombre(
                        cmtTipoBasicaMgl.getTipoBasicaId(), nombreGenSubEdiSel);
            } else {
                conteoNombreGenSubEdi++;
            }

            String msn = "";
            if (tempText.length() <= 200) {
                if (conteoNombreGenSubEdi <= 5) {
                    solSubEdi.setNombreSubedificio(tempText);
                    nombreGenSubEdiText = "";
                    nombreGenSubEdiSel = "";

                } else {
                    msn = "El nombre del subedificio supera el tamaño máximo (5) de niveles";
                }

            } else {
                msn = "El nombre del subedificio supera el tamaño máximo";
            }

            if (!"".equals(msn)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: generarNombreSubEdificio(). " + e.getMessage(), e, LOGGER);

        }
        
        return null;
    }
    
    public String limpiarNombreSubEdificio(CmtSolicitudSubEdificioMgl solSubEdi) {
        solSubEdi.setNombreSubedificio(null);
        return null;
    }
    
    public String generarListasSeleccionFormulario() {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            datosGestion = new HashMap<>();
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
            tipoSubEdificioList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ);
            tipoEdificioList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            estadoSubEdificioList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            CmtBasicaMgl basicaMultiEdificio = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (basicaMultiEdificio != null) {
                estadoSubEdificioList = basicaMultiEdificio.remover(estadoSubEdificioList);
            }
            
            basicaEstadoSinVisitaTecnica = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA);
            
            if (!tipoSubEdificioList.isEmpty()) {
                nombreGenSubEdiText = tipoSubEdificioList.get(0).getNombreBasica();
            }
            
            listaNodosSubEdificio = new HashMap<>();
            
            CmtTipoBasicaMgl tipoBasicaTecnologia;
            tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            listaTecnologiasBasica = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            
            tecnologiaSelect = new CmtBasicaMgl();
            nodoCobertura = new NodoMgl();
            habilitarCombo=false;
            selectedBarrio = "";
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: generarListasSeleccionFormulario(). " + e.getMessage(), e, LOGGER);

        }
        return null;
    }
    
    public String generarOtrasListasSeleccionFormulario() {
        try {
            
            FiltroConsultaCompaniasDto filtroConsulta = new FiltroConsultaCompaniasDto();
            filtroConsulta.setTipoCompania(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_ADMINISTRACION));
            filtroConsulta.setEstado("Y");
            filtroConsulta.setMunicipio(cuentaMatriz.getMunicipio().getGpoId());
            
            companiaAdministacionList = companiaFacadeLocal.findByfiltro(filtroConsulta,false);
            Collections.sort(companiaAdministacionList, (CmtCompaniaMgl f1, CmtCompaniaMgl f2) -> f1.getNombreCompania().compareTo(f2.getNombreCompania()));
            
            filtroConsulta = new FiltroConsultaCompaniasDto();
            filtroConsulta.setTipoCompania(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_ASCENSORES));
            filtroConsulta.setEstado("Y");
            filtroConsulta.setMunicipio(cuentaMatriz.getMunicipio().getGpoId());
            
            companiaAscensoresList = companiaFacadeLocal.findByfiltro(filtroConsulta,false);
            Collections.sort(companiaAscensoresList, (CmtCompaniaMgl f1, CmtCompaniaMgl f2) -> f1.getNombreCompania().compareTo(f2.getNombreCompania()));
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error generando la solicitud de Modificación Cuenta Matriz. ";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: generarOtrasListasSeleccionFormulario(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public String mostrarCrearSubedificio() {
        
        mostrarCrearSubedificio = true;
        solSubEdificioNuevo = new CmtSolicitudSubEdificioMgl();
        solSubEdificioNuevo.setNodoObj(new NodoMgl());
        solSubEdificioNuevo.setEstadoSubEdificioObj(new CmtBasicaMgl());
        solSubEdificioNuevo.setSolModCobertura(new Short("0"));
        solSubEdificioNuevo.setSolModDatos(new Short("0"));
        solSubEdificioNuevo.setSolModEliminacion(new Short("0"));
        cargarListaNodos(null);
        habilitarCombo= false;
        return null;
    }
    
    public String ocultarCrearSubedificio() {
        mostrarCrearSubedificio = false;
        habilitarCombo= false;
        return null;
        
    }
    
    public String seleccionarSubEdificioMod(CmtSolicitudSubEdificioMgl se) {
        solSubEdificioSelecMod.setSeleccionado(false);
        solSubEdificioSelecMod = se;
        solSubEdificioSelecMod.setSeleccionado(true);
        habilitarCombo= false;
        return null;
    }
    
    public String seleccionarDireccionMod(CmtDireccionSolicitudMgl dir) {
        direccionSolSelecMod.setSeleccionado(false);
        direccionSolSelecMod = dir;
        direccionSolSelecMod.setSeleccionado(true);
        habilitarCombo= false;
        return null;
    }
    
    public String recargar(ResponseValidarDireccionDto direccionDto) {
        responseValidarDireccionDto = direccionDto;
        barrioslist = responseValidarDireccionDto.getBarrios();
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        direccion = responseValidarDireccionDto.getDireccion();
        
        direccionSolSelecMod.mapearCamposDrDireccion(drDireccion);
        
        direccionSolSelecMod.setCalleRr(responseValidarDireccionDto.getDireccionRREntity().getCalle());
        direccionSolSelecMod.setUnidadRr(responseValidarDireccionDto.getDireccionRREntity().getNumeroUnidad());

        direccionSolSelecMod.setCmtDirObj(direccionSolSelecMod.getCmtDirObj());
        direccionSolSelecMod.setDirFormatoIgac(direccion);
        direccionSolSelecMod.setDireccionAntigua(responseValidarDireccionDto.getDireccionAntigua());
        direccionSolSelecMod.setValidado(true);
        habilitarCombo= false;
        return null;
    }
    
    public String irPopUpDireccion(CmtDireccionSolicitudMgl cmtDireccionMgl) {
        try {

            direccionSolSelecMod = cmtDireccionMgl;
            direccion = cmtDireccionMgl.getDirFormatoIgac();
            drDireccion = new DrDireccion();
            validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            validadorDireccionBean.setUsuarioLogin(usuarioLogin);
            validadorDireccionBean.setCiudadDelBean(cuentaMatriz.getMunicipio());
            validadorDireccionBean.setIdCentroPoblado(cuentaMatriz.getCentroPoblado().getGpoId());
            validadorDireccionBean.setTecnologia(null);
            validadorDireccionBean.validarDireccion(drDireccion, direccion, cuentaMatriz.getCentroPoblado(), null, this, SolicitudModificacionCMBean.class, Constant.TIPO_VALIDACION_DIR_CM,Constant.MODIFICACION_CM);
              
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: irPopUpDireccion(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: irPopUpDireccion() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public NodoMgl construirNodoBusqueda(String codNodo, String tipoRed) {
            NodoMgl nAdd = null;
        try {
            NodoMgl nFind;
            nFind = nodoMglFacadeLocal.findByCodigo(codNodo);
            nAdd = new NodoMgl();

            if (nFind != null && nFind.getNodId() != null) {
                nAdd.setNodId(nFind.getNodId());
                if (tipoRed.equals("") || tipoRed.isEmpty()) {
                    nAdd.setNodCodigo(codNodo);
                } else {
                    nAdd.setNodCodigo(tipoRed + ":" + codNodo);
                }
            } else {
                nAdd = null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: construirNodoBusqueda(). " + e.getMessage(), e, LOGGER);
        }
        return nAdd;
    }
    
    public void verificarValidacionDireccion(CmtDireccionSolicitudMgl dir) {
        
        if (dir.getDirFormatoIgac() == null || "".equals(dir.getDirFormatoIgac())) {
            dir.setValidado(false);
        }
        
    }
    
    public void verificarCambioDireccion() {
        for (CmtDireccionSolicitudMgl sd : direccionSolModList) {
            boolean existe = false;
            if (solicitudesDireccionesList != null && !solicitudesDireccionesList.isEmpty()) {
                for (CmtDireccionSolicitudMgl sdc : solicitudesDireccionesList) {
                    if (sdc.getDirFormatoIgac().equals(sd.getDirFormatoIgac())) {
                        existe = true;
                        break;
                    }
                }
            }
            if (!existe) {
                sd.setDirFormatoIgac("");
                sd.setBarrio("");
                sd.setOtroBarrio(false);
                listaBarriosDireccion = new HashMap<>();
            }
        }
    }
    
    public String obtenerTipoDireccion(int cod) {
        String tipoDireccion = "";
        if (cod == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
            tipoDireccion = "PRINCIPAL";
        }
        if (cod == Constant.ID_TIPO_DIRECCION_ALTERNA) {
            tipoDireccion = "ALTERNA";
        }
        if (cod == Constant.ID_TIPO_DIRECCION_SUBEDIFICIO) {
            tipoDireccion = "SUBEDIFICIO";
        }
        return tipoDireccion;
    }
    
    public List<String> consultarBarrios(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) {
        if (listaBarriosDireccion != null && listaBarriosDireccion.containsKey(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId())) {
            return listaBarriosDireccion.get(cmtDireccionSolictudMgl.getCmtDirObj().getDireccionId());
        }
        return new ArrayList<>();
    }
    
    public String mostrarOtroNodoSolicitud() {
        otroNodoSolicitud = !otroNodoSolicitud;
        return null;
    }
    
    public NodoMgl validarNodo(String codigoNodo) {
        NodoMgl nodoResultado = null;
        
            CmtBasicaMgl cmtBasicaMgl = null;
            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                try {
                    if (tecnologiaSelect.getBasicaId() != null) {
                        cmtBasicaMgl = cmtBasicaMglFacadeLocal.findById(tecnologiaSelect.getBasicaId());
                    }
                    CmtNodoValidado cmtNodoValidado = nodoMglFacadeLocal.
                            validarNodoModCM(codigoNodo, solicitudModCM.getCuentaMatrizObj().getCentroPoblado(), cmtBasicaMgl);//nodoMglFacadeLocal.validarNodo(codigoNodo, solicitudModCM.getComunidad(), usuarioVT);
                    if (cmtNodoValidado != null) {

                        nodoResultado = cmtNodoValidado.getNodoMgl();
                        if (cmtNodoValidado.getMessage() != null && !cmtNodoValidado.getMessage().isEmpty()) {
                            String msg = cmtNodoValidado.getMessage();
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msg, ""));
                        }

                    } else {
                        String msg = "Error en la validacion del nodo " + codigoNodo + ". ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        return null;
                    }
              
                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: validarNodo(). " + e.getMessage(), e, LOGGER);
                }
            }
            return nodoResultado;
    }
    
    public void accionSeleccionCoberturaCm(CmtSolicitudSubEdificioMgl sse) {
        if (sse.isSeleccionado()) {
            sse.setSeleccionado(false);
        } else {
            sse.setSeleccionado(true);
        }
        if (sse.isEdificioGeneral()) {
            cambioNodoCm = sse.isSeleccionado();
            
            accionCambiarCoberturaCm(sse);
        }
    }
    
    public String accionCambiarCoberturaCm(CmtSolicitudSubEdificioMgl sse) {
        if (sse.isEdificioGeneral()) {
            if (cambioNodoCm) {
                coberturaModList.forEach((sseNoGeneral) -> {
                    try {
                        sseNoGeneral.setNodoObj(sse.getNodoObj().clone());
                        sseNoGeneral.setSeleccionado(true);
                    } catch (CloneNotSupportedException e) {
                        FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: accionCambiarCoberturaCm(). " + e.getMessage(), e, LOGGER);
                    } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: accionCambiarCoberturaCm() " + e.getMessage(), e, LOGGER);
                    }
                });
            } else {
                for (CmtSolicitudSubEdificioMgl sseNoGeneral : coberturaModList) {
                    sseNoGeneral.setNodoObj(new NodoMgl());
                    sseNoGeneral.setSeleccionado(false);
                }
            }
        }
        return null;
    }
    
    public boolean verificarPreguardado() {
        String msg = "";
        boolean permitirNavegacion = true;
        
        if (encabezadoSolModiCMBean != null) {
            if ("DATOSCM".equals(encabezadoSolModiCMBean.getSelectedTab())
                    && encabezadoSolModiCMBean.isActivoCheckModDatosCM()
                    && !hayCambioDatosCm) {
                msg = "No ha guardado ningún cambio en la pestaña 'Modificar Datos CM'";
            }
            if ("DIRECCION".equals(encabezadoSolModiCMBean.getSelectedTab())
                    && encabezadoSolModiCMBean.isActivoCheckModDireccion()
                    && !hayCambioDireccion) {
                msg = "No ha guardado ningún cambio en la pestaña 'Modificar Direccion'";
            }
            if ("SUBEDIFICIOS".equals(encabezadoSolModiCMBean.getSelectedTab())
                    && encabezadoSolModiCMBean.isActivoCheckModSubedificios()
                    && !hayCambioSubEdificios) {
                msg = "No ha guardado ningún cambio en la pestaña 'Modificar Subedificios'";
            }
            if ("COBERTURA".equals(encabezadoSolModiCMBean.getSelectedTab())
                    && encabezadoSolModiCMBean.isActivoCheckModCobertura()
                    && !hayCambioCobertura) {
                msg = "No ha guardado ningún cambio en la pestaña 'Modificar Cobertura'";
            }
        }
        
        if (!msg.isEmpty()) {
            permitirNavegacion = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msg, ""));
        }
        return permitirNavegacion;
    }

    /*
     * GETTERS Y SETTERS
     */
    public Boolean getMostrarGestionHHPP() {
        try {
            if (solicitudModCM.getEstadoSolicitudObj() != null && solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp() != null) {
                mostrarGestionHHPP = solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE)
                        || solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP);
            }
        } catch (NullPointerException ex) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: getMostrarGestionDR(). " + ex.getMessage(), ex, LOGGER);
            mostrarGestionHHPP = false;
        }

        return mostrarGestionHHPP;
    }

    public void setMostrarGestionHHPP(Boolean mostrarGestionHHPP) {
        this.mostrarGestionHHPP = mostrarGestionHHPP;
    }
    
    public Boolean getMostrarGestionDR() {
        
        try {
            mostrarGestionDR = solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE)
                    || solicitudModCM.getEstadoSolicitudObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA);
        } catch (NullPointerException e) {
               mostrarGestionDR = false;
             FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: getMostrarGestionDR(). " + e.getMessage(), e, LOGGER);
         
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: getMostrarGestionDR(). " + e.getMessage(), e, LOGGER);
        }

        return mostrarGestionDR;
    }

    /**
     *
     * @return
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        if (validarEdicion == null) {
            validarEdicion = validarAccion(ValidacionUtil.OPC_EDICION);
        }
        return validarEdicion;
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        if (validarBorrado == null) {
            validarBorrado = validarAccion(ValidacionUtil.OPC_BORRADO);
        }
        return validarBorrado;
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public void buscaNodosCoberturaTec() {
        
        lstNodosCobertura = null;
        coberturaModList = new ArrayList<>();
        boolean haySubTecnogias = false;
        int contador = 0;

        try {
            if (tecnologiaSelect.getBasicaId() != null) {
                CmtBasicaMgl cmtBasicaMgl = cmtBasicaMglFacadeLocal.findById(tecnologiaSelect.getBasicaId());

                for (CmtSubEdificioMgl cmtSubEdificioMgl
                        : solicitudModCM.getCuentaMatrizObj().getListCmtSubEdificioMgl()) {

                    if (cmtSubEdificioMgl.getEstadoRegistro() == 1) {
                        lstCmtTecnologiaSubMgls = cmtTecnologiaSubMglFacadeLocal.findTecnoSubBySubEdiTec(cmtSubEdificioMgl, cmtBasicaMgl);

                    } else {
                        lstCmtTecnologiaSubMgls = new ArrayList<>();
                    }

                    if (lstCmtTecnologiaSubMgls != null && lstCmtTecnologiaSubMgls.size() > 0) {
                        for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : lstCmtTecnologiaSubMgls) {


                            if (cmtSubEdificioMgl.getSubEdificioId().compareTo(
                                    cuentaMatriz.getSubEdificioGeneral().getSubEdificioId()) == 0) {
                                /*Validamos si una cm MultiEdificio*/
                                if (!cuentaMatriz.getSubEdificioGeneral().getEstadoSubEdificioObj()
                                        .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    /*Cuando es Único Edificio*/
                                    CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl = new CmtSolicitudSubEdificioMgl();
                                    cmtSolicitudSubEdificioMgl.setSolicitudCMObj(solicitudModCM);
                                    cmtSolicitudSubEdificioMgl.setNombreSubedificio(cmtSubEdificioMgl.getNombreSubedificio());
                                    cmtSolicitudSubEdificioMgl.setNodoObj(cmtTecnologiaSubMgl.getNodoId());
                                    cmtSolicitudSubEdificioMgl.setSubEdificioObj(cmtSubEdificioMgl);
                                    coberturaModList.add(cmtSolicitudSubEdificioMgl);
                                    mostrarTabla = true;
                                    haySubTecnogias = true;
                                    contador++;
                                }
                            } else {/*Cuando es Sub-Edificio*/
                                CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl = new CmtSolicitudSubEdificioMgl();
                                cmtSolicitudSubEdificioMgl.setSolicitudCMObj(solicitudModCM);
                                cmtSolicitudSubEdificioMgl.setNombreSubedificio(cmtSubEdificioMgl.getNombreSubedificio());
                                cmtSolicitudSubEdificioMgl.setNodoObj(cmtTecnologiaSubMgl.getNodoId());
                                cmtSolicitudSubEdificioMgl.setSubEdificioObj(cmtSubEdificioMgl);
                                coberturaModList.add(cmtSolicitudSubEdificioMgl);
                                mostrarTabla = true;
                                haySubTecnogias = true;
                                contador++;
                            }

                        }

                    } else {
                        haySubTecnogias = false;
                    }

                }
                if (!haySubTecnogias && contador == 0) {
                    String msg = "No existen Tecnologias asociados. Ud debe culminar el proceso de VT creando los HHPP  " + cmtBasicaMgl.getNombreBasica() + "";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msg, ""));
                    control = 0;
                } else {

                    encuentraNodosCobertura(cmtBasicaMgl);
                    control = 0;
                }

            } else {
                String msg = "Debe seleccionar una tecnologia";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msg, ""));
                control = 0;
            }

        } catch (ApplicationException e) {
            String msg = "Error al consultar nodos cercanos"
                    + "asociados a una tecnologia";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
            control = 0;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: buscaNodosCoberturaTec(). " + e.getMessage(), e, LOGGER);
        }
    }
    
    public String accionCambiarNodoCm(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl) {

        cmtSolicitudSubEdificioMgl.setNodoDisenoObj(nodoCobertura);

        return "";
    }
    
    public String escribeNombreGestion(CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl) {

        NodoMgl nodoDiseno = new NodoMgl();
        if(control == 1){
           nodoDiseno.setNodCodigo(cmtSolicitudSubEdificioMgl.getNodoDisenoObj().getNodCodigo());  
        }else{
          nodoDiseno.setNodCodigo(cmtSolicitudSubEdificioMgl.getNodoObj().getNodCodigo());  
        }
    
        cmtSolicitudSubEdificioMgl.setNodoDisenoObj(nodoDiseno);
        return cmtSolicitudSubEdificioMgl.getNodoObj().getNodNombre();
    }
    
    public void encuentraNodosCobertura(CmtBasicaMgl cmtBasicaMgl){
    
        NodoMgl nodoMglCober;
        lstNodosCobertura = new ArrayList<>();
        try {
            CmtDireccionMgl cmtDireccionMgl = solicitudModCM.getCuentaMatrizObj().getDireccionesList().get(0);
            if (cmtDireccionMgl != null) {
                DireccionMgl direccionMgl = direccionMglFacadeLocal1.findById(cmtDireccionMgl.getDireccionObj());
                if (direccionMgl != null) {
                    if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_UNO.getCodigo())) {
                        if (direccionMgl.getDirNodouno() != null && !direccionMgl.getDirNodouno().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNodouno());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }

                    } else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_DOS.getCodigo())) {
                        if (direccionMgl.getDirNododos() != null && !direccionMgl.getDirNododos().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNododos());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    } else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_DTH.getCodigo())) {
                        if (direccionMgl.getDirNodoDth() != null && !direccionMgl.getDirNodoDth().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNodoDth());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    } else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_MOVIL.getCodigo())) {
                        if (direccionMgl.getDirNodoMovil() != null && !direccionMgl.getDirNodoMovil().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNodoMovil());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    } else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_FTTH.getCodigo())) {
                        if (direccionMgl.getDirNodoFtth() != null && !direccionMgl.getDirNodoFtth().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNodoFtth());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    } else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_WIFI.getCodigo())) {
                        if (direccionMgl.getDirNodoWifi() != null && !direccionMgl.getDirNodoWifi().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getDirNodoWifi());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    }  else if (cmtBasicaMgl.getCodigoBasica().equalsIgnoreCase(CmtCoverEnum.NODO_FOU.getCodigo())) {
                        if (direccionMgl.getGeoZonaUnifilar() != null && !direccionMgl.getGeoZonaUnifilar().isEmpty()) {
                            nodoMglCober = nodoMglFacadeLocal.findByCodigo(direccionMgl.getGeoZonaUnifilar());
                            if (nodoMglCober != null && nodoMglCober.getNodId() != null) {
                                lstNodosCobertura.add(nodoMglCober);
                            }
                        }
                    }

                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: encuentraNodosCobertura(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: encuentraNodosCobertura(). " + e.getMessage(), e, LOGGER);
        }

    }
    
    public CmtSolicitudSubEdificioMgl guardaNodoGestion
            (CmtSolicitudSubEdificioMgl cmtSolicitudSubEdificioMgl){
        try {
            if (cmtSolicitudSubEdificioMgl != null) {
                NodoMgl nodoDiseno = null;
                if (modoGestion) {
                    if (cmtSolicitudSubEdificioMgl.getNodoDisGestion() != null) {
                        nodoDiseno = validarNodo(cmtSolicitudSubEdificioMgl.getNodoDisGestion().getNodCodigo());
                    }
                } else {
                    if (cmtSolicitudSubEdificioMgl.getNodoDisenoObj() != null) {
                        nodoDiseno = validarNodo(cmtSolicitudSubEdificioMgl.getNodoDisenoObj().getNodCodigo());
                    }
                }

                if (nodoDiseno != null) {
                    if (modoGestion) {
                        cmtSolicitudSubEdificioMgl.setNodoDisGestion(nodoDiseno);
                    } else {
                        cmtSolicitudSubEdificioMgl.setNodoDisenoObj(nodoDiseno);
                    }

                }
                else {
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible establecer el nodo gestión, ya que no fue posible encontrar el nodo de diseño.", ""));
                }
                
                if (cmtSolicitudSubEdificioMgl.getNodoDisGestion() != null && cmtSolicitudSubEdificioMgl.getNodoDisGestion().getNodCodigo() != null) {
                    datosGestion.put(cmtSolicitudSubEdificioMgl, cmtSolicitudSubEdificioMgl.getNodoDisGestion().getNodCodigo());
                }
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible establecer el nodo gestión, ya que la solicitud subedificio es nula.", ""));
            }
            
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: guardaNodoGestion(). " + e.getMessage(), e, LOGGER);
        }
        
        return cmtSolicitudSubEdificioMgl;
    }
    
         
    public void guardarRegOriginales(CmtSubEdificioMgl subEdificioMglAnt,
            CmtSolicitudCmMgl solicitudCmMgl, CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl, 
            CmtDireccionMgl direccionMglAnt, CmtDireccionSolicitudMgl direccionSolicitudMgl,int mod){
  
       try {
           
           //Modifica datos cuenta matriz
           switch (mod) {
               case 1:
                   {
                       CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl = new CmtModificacionesCcmmAudMgl();
                       cmtModificacionesCcmmAudMgl.setSolicitudCMObj(solicitudCmMgl);
                       cmtModificacionesCcmmAudMgl.setSubEdificioIdObj(subEdificioMglAnt);
                       cmtModificacionesCcmmAudMgl.setCuentaMatrizObj(subEdificioMglAnt.getCmtCuentaMatrizMglObj());
                       cmtModificacionesCcmmAudMgl.setNombreSubedificioAnt(subEdificioMglAnt.getNombreSubedificio());
                       cmtModificacionesCcmmAudMgl.setTipoEdificioObjAnt(subEdificioMglAnt.getTipoEdificioObj());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteriaAnt(subEdificioMglAnt.getTelefonoPorteria());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteria2Ant(subEdificioMglAnt.getTelefonoPorteria2());
                       cmtModificacionesCcmmAudMgl.setAdministradorAnt(subEdificioMglAnt.getAdministrador());
                       cmtModificacionesCcmmAudMgl.setCompaniaAdministracionObjAnt(subEdificioMglAnt.getCompaniaAdministracionObj());
                       cmtModificacionesCcmmAudMgl.setCompaniaAscensorObjAnt(subEdificioMglAnt.getCompaniaAscensorObj());
                       cmtModificacionesCcmmAudMgl.setFechaEntregaEdificioAnt(subEdificioMglAnt.getFechaEntregaEdificio());
                       cmtModificacionesCcmmAudMgl.setNombreSubedificioDes(solicitudSubEdificioMgl.getNombreSubedificio());
                       cmtModificacionesCcmmAudMgl.setTipoEdificioObjDes(solicitudSubEdificioMgl.getTipoEdificioObj());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteriaDes(solicitudSubEdificioMgl.getTelefonoPorteria());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteria2Des(solicitudSubEdificioMgl.getTelefonoPorteria2());
                       cmtModificacionesCcmmAudMgl.setAdministradorDes(solicitudSubEdificioMgl.getAdministrador());
                       cmtModificacionesCcmmAudMgl.setCompaniaAdministracionObjDes(solicitudSubEdificioMgl.getCompaniaAdministracionObj());
                       cmtModificacionesCcmmAudMgl.setCompaniaAscensorObjDes(solicitudSubEdificioMgl.getCompaniaAscensorObj());
                       cmtModificacionesCcmmAudMgl.setFechaEntregaEdificioDes(solicitudSubEdificioMgl.getFechaEntregaEdificio());
                       cmtModificacionesCcmmAudMgl = cmtModCcmmAudMglFacadeLocal.crear(cmtModificacionesCcmmAudMgl, usuarioVT, perfilVT);
                       if (cmtModificacionesCcmmAudMgl.getModCcmmAudId() != null) {
                           LOGGER.info("Registro original de CCMM guardado en el repositorio");
                       }      break;
                   }
               case 2:
                   {
                       //Modifica datos subedificios
                       CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl = new CmtModificacionesCcmmAudMgl();
                       cmtModificacionesCcmmAudMgl.setSolicitudCMObj(solicitudCmMgl);
                       cmtModificacionesCcmmAudMgl.setSubEdificioIdObj(subEdificioMglAnt);
                       cmtModificacionesCcmmAudMgl.setCuentaMatrizObj(subEdificioMglAnt.getCmtCuentaMatrizMglObj());
                       cmtModificacionesCcmmAudMgl.setNombreSubedificioAnt(subEdificioMglAnt.getNombreSubedificio());
                       cmtModificacionesCcmmAudMgl.setTipoEdificioObjAnt(subEdificioMglAnt.getTipoEdificioObj());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteriaAnt(subEdificioMglAnt.getTelefonoPorteria());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteria2Ant(subEdificioMglAnt.getTelefonoPorteria2());
                       cmtModificacionesCcmmAudMgl.setAdministradorAnt(subEdificioMglAnt.getAdministrador());
                       cmtModificacionesCcmmAudMgl.setCompaniaAdministracionObjAnt(subEdificioMglAnt.getCompaniaAdministracionObj());
                       cmtModificacionesCcmmAudMgl.setCompaniaAscensorObjAnt(subEdificioMglAnt.getCompaniaAscensorObj());
                       cmtModificacionesCcmmAudMgl.setFechaEntregaEdificioAnt(subEdificioMglAnt.getFechaEntregaEdificio());
                       cmtModificacionesCcmmAudMgl.setNombreSubedificioDes(solicitudSubEdificioMgl.getNombreSubedificio());
                       cmtModificacionesCcmmAudMgl.setTipoEdificioObjDes(solicitudSubEdificioMgl.getTipoEdificioObj());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteriaDes(solicitudSubEdificioMgl.getTelefonoPorteria());
                       cmtModificacionesCcmmAudMgl.setTelefonoPorteria2Des(solicitudSubEdificioMgl.getTelefonoPorteria2());
                       cmtModificacionesCcmmAudMgl.setAdministradorDes(solicitudSubEdificioMgl.getAdministrador());
                       cmtModificacionesCcmmAudMgl.setCompaniaAdministracionObjDes(solicitudSubEdificioMgl.getCompaniaAdministracionObj());
                       cmtModificacionesCcmmAudMgl.setCompaniaAscensorObjDes(solicitudSubEdificioMgl.getCompaniaAscensorObj());
                       cmtModificacionesCcmmAudMgl.setFechaEntregaEdificioDes(solicitudSubEdificioMgl.getFechaEntregaEdificio());
                       cmtModificacionesCcmmAudMgl = cmtModCcmmAudMglFacadeLocal.crear(cmtModificacionesCcmmAudMgl, usuarioVT, perfilVT);
                       if (cmtModificacionesCcmmAudMgl.getModCcmmAudId() != null) {
                           LOGGER.info("Registro original de SUBEDIFICIO guardado en el repositorio");
                       }      break;
                   }
               case 3:
                   {
                       //Modifica direccion de CCMM
                       CmtModificacionesCcmmAudMgl cmtModificacionesCcmmAudMgl = new CmtModificacionesCcmmAudMgl();
                       cmtModificacionesCcmmAudMgl.setSolicitudCMObj(solicitudCmMgl);
                       cmtModificacionesCcmmAudMgl.setCuentaMatrizObj(cuentaMatriz);
                       cmtModificacionesCcmmAudMgl.setDireccionCcmmAnt(direccionMglAnt.getDireccionObj().getDirFormatoIgac());
                       cmtModificacionesCcmmAudMgl.setBarrioCcmmAnt(direccionMglAnt.getBarrio());
                       cmtModificacionesCcmmAudMgl.setDireccionCcmmDes(direccionSolicitudMgl.getDirFormatoIgac());
                       cmtModificacionesCcmmAudMgl.setBarrioCcmmDes(direccionSolicitudMgl.getBarrio());
                       cmtModificacionesCcmmAudMgl = cmtModCcmmAudMglFacadeLocal.crear(cmtModificacionesCcmmAudMgl, usuarioVT, perfilVT);
                       if (cmtModificacionesCcmmAudMgl.getModCcmmAudId() != null) {
                           LOGGER.info("Registro original de CCMM guardado en el repositorio");
                       }      break;
                   }
               default:
                   break;
           }
            
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudModificacionCMBean: guardarRegOriginales(). " + e.getMessage(), e, LOGGER);
        }
        
    }
    
    public String buscarNodoNuevo(CmtSolicitudSubEdificioMgl solicitudSubEdificioMgl){
        String nodoNew = "";
        try {
            CmtSolicitudSubEdificioMgl solicitudSubEdificioMglBD
                    = solicitudSubEdificioFacadeLocal.findById(solicitudSubEdificioMgl);

            if (solicitudSubEdificioMglBD != null) {
                nodoNew = solicitudSubEdificioMglBD.getNodoDisenoObj().getNodCodigo();
            }
        } catch (ApplicationException | NumberFormatException e) {
            String msnErr = "Ha ocurrido un error modificando la dirección de Sub-Edificio";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } 
        return nodoNew;
    }


    
    /**
     * Obtiene la direcci&oacute;n del subedificio.
     * 
     * @param cmtSubEdificioMgl
     * @return Direcci&oacute;n.
     */
    public String getDireccionSubEdificio (CmtSubEdificioMgl cmtSubEdificioMgl) {
        String direccionSub = "";
        
        if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getListDireccionesMgl() != null && !cmtSubEdificioMgl.getListDireccionesMgl().isEmpty()) {
            CmtDireccionMgl cmtDireccionMgl = cmtSubEdificioMgl.getListDireccionesMgl().get(0);
            direccionSub = cmtDireccionMgl.getCalleRr() + " " + cmtDireccionMgl.getUnidadRr();
        }
        
        return (direccionSub);
    }
    
    
    /**
     * cardenaslb metodo para validar correo
     *
     * @param correo
     * @return
     */
    private boolean validarCorreo(String correo) {
        boolean respuesta = false;
        try {
            String mail = "([\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z])?";
            Pattern pattern = Pattern.compile(mail);
            Matcher matcher = pattern.matcher(correo);
            respuesta = matcher.matches();
        } catch (Exception e) {
            LOGGER.error("Error en validarCorreo de InfoCreaCMBean: " + e);
        }
        return respuesta;
    }
    
    
    public boolean validarCrearSolModCm() {
        return validarEdicion(VALIDARCREARSOLMODCM);
    }

    private boolean validarEdicion(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    } 

    public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        selectedBarrio = "";
        return null;
    }

    public void setMostrarGestionDR(Boolean mostrarGestionDR) {
        this.mostrarGestionDR = mostrarGestionDR;
    }
    
    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }
    
    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }
    
    public CmtSolicitudCmMgl getSolicitudModCM() {
        return solicitudModCM;
    }
    
    public void setSolicitudModCM(CmtSolicitudCmMgl solicitudModCM) {
        this.solicitudModCM = solicitudModCM;
    }
    
    public CmtSolicitudSubEdificioMgl getSolSubEdificioSelecMod() {
        return solSubEdificioSelecMod;
    }
    
    public void setSolSubEdificioSelecMod(CmtSolicitudSubEdificioMgl solSubEdificioSelecMod) {
        this.solSubEdificioSelecMod = solSubEdificioSelecMod;
    }
    
    public List<CmtCompaniaMgl> getCompaniaAdministacionList() {
        return companiaAdministacionList;
    }
    
    public void setCompaniaAdministacionList(List<CmtCompaniaMgl> companiaAdministacionList) {
        this.companiaAdministacionList = companiaAdministacionList;
    }
    
    public List<CmtCompaniaMgl> getCompaniaAscensoresList() {
        return companiaAscensoresList;
    }
    
    public void setCompaniaAscensoresList(List<CmtCompaniaMgl> companiaAscensoresList) {
        this.companiaAscensoresList = companiaAscensoresList;
    }
    
    public CmtSolicitudSubEdificioMgl getSubEdificioGeneralMod() {
        return subEdificioGeneralMod;
    }
    
    public void setSubEdificioGeneralMod(CmtSolicitudSubEdificioMgl subEdificioGeneralMod) {
        this.subEdificioGeneralMod = subEdificioGeneralMod;
    }
    
    public List<CmtBasicaMgl> getTipoSubEdificioList() {
        return tipoSubEdificioList;
    }
    
    public void setTipoSubEdificioList(List<CmtBasicaMgl> tipoSubEdificioList) {
        this.tipoSubEdificioList = tipoSubEdificioList;
    }
    
    public CmtSolicitudSubEdificioMgl getSubSolSubEdificioSelecMod() {
        return solSubEdificioSelecMod;
    }
    
    public void setSubSolSubEdificioSelecMod(CmtSolicitudSubEdificioMgl solSubEdificioSelecMod) {
        this.solSubEdificioSelecMod = solSubEdificioSelecMod;
    }
    
    public List<CmtSolicitudSubEdificioMgl> getSubEdificiosModList() {
        return subEdificiosModList;
    }
    
    public void setSubEdificiosModList(List<CmtSolicitudSubEdificioMgl> subEdificiosModList) {
        this.subEdificiosModList = subEdificiosModList;
    }
    
    public CmtDireccionSolicitudMgl getDireccionSolSelecMod() {
        return direccionSolSelecMod;
    }
    
    public void setDireccionSolSelecMod(CmtDireccionSolicitudMgl direccionSolSelecMod) {
        this.direccionSolSelecMod = direccionSolSelecMod;
    }
    
    public List<CmtDireccionSolicitudMgl> getDireccionSolModList() {
        return direccionSolModList;
    }
    
    public void setDireccionSolModList(List<CmtDireccionSolicitudMgl> direccionSolModList) {
        this.direccionSolModList = direccionSolModList;
    }
    
    public List<CmtNotasSolicitudMgl> getNotasSolicitudList() {
        return notasSolicitudList;
    }
    
    public void setNotasSolicitudList(List<CmtNotasSolicitudMgl> notasSolicitudList) {
        this.notasSolicitudList = notasSolicitudList;
    }
    
    public Boolean getModoGestion() {
        return modoGestion;
    }
    
    public void setModoGestion(Boolean modoGestion) {
        this.modoGestion = modoGestion;
    }
    
    public List<CmtSolicitudSubEdificioMgl> getCoberturaModList() {
        return coberturaModList;
    }
    
    public void setCoberturaModList(List<CmtSolicitudSubEdificioMgl> coberturaModList) {
        this.coberturaModList = coberturaModList;
    }
    
    public CmtSolicitudSubEdificioMgl getSolCoberturaSelecMod() {
        return solCoberturaSelecMod;
    }
    
    public void setSolCoberturaSelecMod(CmtSolicitudSubEdificioMgl solCoberturaSelecMod) {
        this.solCoberturaSelecMod = solCoberturaSelecMod;
    }
    
    public CmtSolicitudSubEdificioMgl getSolSubEdificioNuevo() {
        return solSubEdificioNuevo;
    }
    
    public void setSolSubEdificioNuevo(CmtSolicitudSubEdificioMgl solSubEdificioNuevo) {
        this.solSubEdificioNuevo = solSubEdificioNuevo;
    }
    
    public Boolean getMostrarCrearSubedificio() {
        return mostrarCrearSubedificio;
    }
    
    public void setMostrarCrearSubedificio(Boolean mostrarCrearSubedificio) {
        this.mostrarCrearSubedificio = mostrarCrearSubedificio;
    }
    
    public String getNombreGenSubEdiText() {
        return nombreGenSubEdiText;
    }
    
    public void setNombreGenSubEdiText(String nombreGenSubEdiText) {
        this.nombreGenSubEdiText = nombreGenSubEdiText;
    }
    
    public String getNombreGenSubEdiSel() {
        return nombreGenSubEdiSel;
    }
    
    public void setNombreGenSubEdiSel(String nombreGenSubEdiSel) {
        this.nombreGenSubEdiSel = nombreGenSubEdiSel;
    }
    
    public List<CmtSolicitudSubEdificioMgl> getSolicitudesCoberturasList() {
        return solicitudesCoberturasList;
    }
    
    public void setSolicitudesCoberturasList(List<CmtSolicitudSubEdificioMgl> solicitudesCoberturasList) {
        this.solicitudesCoberturasList = solicitudesCoberturasList;
    }
    
    public Boolean getHayModDirPrincipal() {
        return hayModDirPrincipal;
    }
    
    public void setHayModDirPrincipal(Boolean hayModDirPrincipal) {
        this.hayModDirPrincipal = hayModDirPrincipal;
    }
    
    public boolean isIsMultiEdificio() {
        return isMultiEdificio;
    }
    
    public void setIsMultiEdificio(boolean isMultiEdificio) {
        this.isMultiEdificio = isMultiEdificio;
    }
    
    public boolean isIsSubEdiDirPropia() {
        return isSubEdiDirPropia;
    }
    
    public void setIsSubEdiDirPropia(boolean isSubEdiDirPropia) {
        this.isSubEdiDirPropia = isSubEdiDirPropia;
    }
    
    public Boolean getHayModDirecciones() {
        return hayModDirecciones;
    }
    
    public void setHayModDirecciones(Boolean hayModDirecciones) {
        this.hayModDirecciones = hayModDirecciones;
    }
    
    public List<CmtBasicaMgl> getTipoEdificioList() {
        return tipoEdificioList;
    }
    
    public void setTipoEdificioList(List<CmtBasicaMgl> tipoEdificioList) {
        this.tipoEdificioList = tipoEdificioList;
    }
    
    public List<NodoMgl> getListaNodosDireccionPrincipal() {
        return listaNodosDireccionPrincipal;
    }
    
    public void setListaNodosDireccionPrincipal(List<NodoMgl> listaNodosDireccionPrincipal) {
        this.listaNodosDireccionPrincipal = listaNodosDireccionPrincipal;
    }
    
    public List<CmtBasicaMgl> getEstadoSubEdificioList() {
        return estadoSubEdificioList;
    }
    
    public void setEstadoSubEdificioList(List<CmtBasicaMgl> estadoSubEdificioList) {
        this.estadoSubEdificioList = estadoSubEdificioList;
    }
    
    public Boolean getCreacionExitosa() {
        return creacionExitosa;
    }
    
    public void setCreacionExitosa(Boolean creacionExitosa) {
        this.creacionExitosa = creacionExitosa;
    }
    
    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }
    
    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }
    
    public HashMap<BigDecimal, List<String>> getListaBarriosDireccion() {
        return listaBarriosDireccion;
    }
    
    public void setListaBarriosDireccion(HashMap<BigDecimal, List<String>> listaBarriosDireccion) {
        this.listaBarriosDireccion = listaBarriosDireccion;
    }
    
    public Boolean getOtroNodoSolicitud() {
        return otroNodoSolicitud;
    }
    
    public void setOtroNodoSolicitud(Boolean otroNodoSolicitud) {
        this.otroNodoSolicitud = otroNodoSolicitud;
    }
    
    public Boolean getCambioNodoCm() {
        return cambioNodoCm;
    }
    
    public void setCambioNodoCm(Boolean cambioNodoCm) {
        this.cambioNodoCm = cambioNodoCm;
    }
    
    public List<CmtBasicaMgl> getListaTecnologiasBasica() {
        return listaTecnologiasBasica;
    }
    
    public void setListaTecnologiasBasica(List<CmtBasicaMgl> listaTecnologiasBasica) {
        this.listaTecnologiasBasica = listaTecnologiasBasica;
    }
    
    public CmtBasicaMgl getTecnologiaSelect() {
        return tecnologiaSelect;
    }
    
    public void setTecnologiaSelect(CmtBasicaMgl tecnologiaSelect) {
        this.tecnologiaSelect = tecnologiaSelect;
    }
    
    public List<NodoMgl> getLstNodosCobertura() {
        return lstNodosCobertura;
    }
    
    public void setLstNodosCobertura(List<NodoMgl> lstNodosCobertura) {
        this.lstNodosCobertura = lstNodosCobertura;
    }
    
    public NodoMgl getNodoCobertura() {
        return nodoCobertura;
    }
    
    public void setNodoCobertura(NodoMgl nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }
    
    public List<CmtTecnologiaSubMgl> getLstCmtTecnologiaSubMgls() {
        return lstCmtTecnologiaSubMgls;
    }
    
    public void setLstCmtTecnologiaSubMgls(List<CmtTecnologiaSubMgl> lstCmtTecnologiaSubMgls) {
        this.lstCmtTecnologiaSubMgls = lstCmtTecnologiaSubMgls;
    }
    
    public boolean isMostrarTabla() {
        return mostrarTabla;
    }
    
    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public boolean isHabilitarCombo() {
        return habilitarCombo;
    }

    public void setHabilitarCombo(boolean habilitarCombo) {
        this.habilitarCombo = habilitarCombo;
    }

    public Boolean getBloquearCambioPropiaDireccion() {
        return bloquearCambioPropiaDireccion;
    }

    public void setBloquearCambioPropiaDireccion(Boolean bloquearCambioPropiaDireccion) {
        this.bloquearCambioPropiaDireccion = bloquearCambioPropiaDireccion;
    }
    public Boolean getHayModSubedificios() {
        return hayModSubedificios;
    }

    public void setHayModSubedificios(Boolean hayModSubedificios) {
        this.hayModSubedificios = hayModSubedificios;
    }

    public Boolean getBloquearCambioNombreEdificio() {
        return bloquearCambioNombreEdificio;
    }

    public void setBloquearCambioNombreEdificio(Boolean bloquearCambioNombreEdificio) {
        this.bloquearCambioNombreEdificio = bloquearCambioNombreEdificio;
    }
    
    public List<String> getBarrioslist() {
        return barrioslist;
    }
    
    public void setBarrioslist(List<String> barrioslist) {
        this.barrioslist = barrioslist;
    }

    public String getSelectedBarrio() {
        return selectedBarrio;
}

    public void setSelectedBarrio(String selectedBarrio) {
        this.selectedBarrio = selectedBarrio;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public boolean isHayCambioDatosCm() {
        return hayCambioDatosCm;
    }

    public void setHayCambioDatosCm(boolean hayCambioDatosCm) {
        this.hayCambioDatosCm = hayCambioDatosCm;
    }

    public boolean isHayCambioDireccion() {
        return hayCambioDireccion;
    }

    public void setHayCambioDireccion(boolean hayCambioDireccion) {
        this.hayCambioDireccion = hayCambioDireccion;
    }

    public boolean isHayCambioSubEdificios() {
        return hayCambioSubEdificios;
    }

    public void setHayCambioSubEdificios(boolean hayCambioSubEdificios) {
        this.hayCambioSubEdificios = hayCambioSubEdificios;
    }

    public boolean isHayCambioCobertura() {
        return hayCambioCobertura;
    }

    public void setHayCambioCobertura(boolean hayCambioCobertura) {
        this.hayCambioCobertura = hayCambioCobertura;
    }
    
    
    
    

}
