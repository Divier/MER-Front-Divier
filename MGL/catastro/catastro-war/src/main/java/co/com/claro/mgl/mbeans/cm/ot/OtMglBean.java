package co.com.claro.mgl.mbeans.cm.ot;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mgl.businessmanager.cm.CmtTipoOtMglManager;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dao.procesomasivo.CmtNotasOtHijaDto;
import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.OnyxOtCmDirlFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.cm.ConsultaCuentasMatricesBean;
import co.com.claro.mgl.mbeans.cm.CuentaMatrizBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.ConstantsCM.RESULTADO_FACTIBILIDAD_TEC_CM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.util.cm.ValidatePenetrationCuentaMatriz;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.util.ConsultaNodoSitiData;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static co.com.claro.mer.utils.costants.OtConstants.*;
import static co.com.claro.mgl.mbeans.util.ConstantsCM.RESULTADO_FACTIBILIDAD_TEC_CM.*;

/**
 *
 * @author ortizjaf
 */
@Log4j2
@ManagedBean(name = "OtMglBean")
@SessionScoped
public class OtMglBean implements Serializable {

    @EJB
    private CmtTipoOtMglFacadeLocal tipoOtMglFacadeLocal;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal estadoxFlujoMglFacadeLocal;
    @EJB
    private CmtEstadoIntxExtMglFacaceLocal estadoIntxExtMglFacaceLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtNotaOtMglFacadeLocal notaOtMglFacadeLocal;
    @EJB
    private CmtNotaOtComentarioMglFacadeLocal comentarioNotaOtMglFacadeLocal;
    @EJB
    private CmtDetalleFlujoMglFacadeLocal detalleFlujoMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal cmtEstadoxFlujoMglFacadeLocal;
    @EJB
    private CmtAgendamientoMglFacadeLocal cmtAgendamientoMglFacadeLocal;
    @EJB
    private CmtSubEdificiosVtFacadeLocal subEdificiosVtFacadeLocal;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal cmtVisitaTecnicaMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private OnyxOtCmDirlFacadeLocal onyxOtCmDirlFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private SolicitudFacadeLocal solicitudFacadeLocal;

    private static final long serialVersionUID = 1L;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @Getter
    private static final String TAB_NOTAS_OT_CCMM = "TABNOTASOTCCMM";
    @Getter
    private static final String TAB_OT_ONIX_OT_CCMM = "TABOTONIXOTCCMM";
    private String selectedTab = "GENERAL_OT";
    private CmtOrdenTrabajoMgl ordenTrabajo;
    private List<CmtBasicaMgl> tipoSegmentoOtList;
    private CmtBasicaMgl tipoNotaOtSelected;
    private List<CmtBasicaMgl> tipoNotaOtList;
    private List<CmtEstadoxFlujoMgl> estadosFlujoList;
    private CmtEstadoIntxExtMgl estadoInternoExterno;
    private CmtNotaOtMgl notaOtMgl;
    private String notaOtComentarioStr;
    private List<CmtNotaOtMgl> notaOtMglList;
    private List<CmtDetalleFlujoMgl> detalleFlujoEstActual;
    private BigDecimal estadoOtId;
    private boolean habilitaFormVT = false;
    private boolean habilitaFormAC = false;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private String cedulaUsuarioVT = null;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private List<CmtBasicaMgl> listTablaBasicaTipoOt;
    private BigDecimal subTipoTrabajoSelected;
    private BigDecimal tipoTrabajoOtSelected;
    private List<CmtTipoOtMgl> listTipoTrabajoOt;
    private BigDecimal basicaTipoOtSelected;
    private SecurityLogin securityLogin;
    private List<CmtTipoOtMgl> subTipoOrdenTrabajo;
    private List<CmtBasicaMgl> tipoGeneralOrdenTrabajo;
    private CmtBasicaMgl basicaTecnologiaOT;
    //valbuenayf
    private List<CmtBasicaMgl> claseOtList;
    //Autor: Victor Bocanegra
    private boolean isVisible = false;
    //
    private List<AuditoriaDto> informacionAuditoria = null; //valbuenayf
    private CmtOrdenTrabajoMgl ordenTrabajoRferencia;
    private List<CmtDetalleFlujoMgl> detalleFlujoSolAprobacion;
    private List<CmtDetalleFlujoMgl> lstDetalleFlujoSolAprobacionCrear;
    private List<CmtSubEdificiosVt> lstSubEdiVT;
    private UploadedFile uploadedFile;
    private List<CmtArchivosVtMgl> lstArchivosAcoMgls;
    private List<CmtArchivosVtMgl> lstArchivosVtMgls;
    private CmtVisitaTecnicaMgl visitaTecnicaAct;
    private boolean mostrarBotonGuardarComentario;
    private boolean mostrarBotonGuardarNota;
    private boolean mostrarListaNotas;
    private boolean mostrarPanelCrearNotas;
    private boolean mostrarPanelListComentarios;
    private boolean mostrarPanelCrearComentarios;
    private List<CmtNotaOtComentarioMgl> lstCmtNotaOtComentarioMgls;

    private BigDecimal numeroOtHija;

    private String OnyxNitCliente = "";
    private String OnyxNombreCliente = "";
    private String OnyxNombreOtHija = "";
    private String OnyxNumeroOtPadre = "";
    private String OnyxNumeroOtHija = "";
    private String OnyxFechaCreOtHija = "";
    private String OnyxFechaCreOtPadre = "";
    private String OnyxContactoTecOtPadre = "";
    private String OnyxTelContactoTecOtPadre = "";
    private String OnyxDescripcion = "";
    private String OnyxDireccion = "";
    private String OnyxSegmento = "";
    private String OnyxTipoServicio = "";
    private String OnyxServicios = "";
    private String OnyxRecurrenteMensual = "";
    private String OnyxCodigoServicio = "";
    private String OnyxVendedor = "";
    private String OnyxTelefono = "";
    private String OnyxNotasOtHija = "";
    private String OnyxEstadoOtHija = "";
    private String OnyxEstadoOtPadre = "";
    private String OnyxFechaCompromisoOtPadre = "";
    private String OnyxCodResolOtPadre1 = "";
    private String OnyxCodResolOtPadre2 = "";
    private String OnyxCodResolOtPadre3 = "";
    private String OnyxCodResolOtPadre4 = "";
    private String OnyxCodResolOtHija1 = "";
    private String OnyxCodResolOtHija2 = "";
    private String OnyxCodResolOtHija3 = "";
    private String OnyxCodResolOtHija4 = "";
    private String OnyxComplejidad = "";
     
    private String estiloObligatorio = "<font color='red'>*</font>";

    private int actualCap;
    private String numPaginaCap = "1";
    private List<Integer> paginaListcap;
    private String pageActualCap;
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    private List<CmtNotaOtComentarioMgl> lstCmtNotaOtComentarioMglsAux;

    private int actualNot;
    private String numPaginaNot = "1";
    private List<Integer> paginaListNot;
    private String pageActualNot;
    private int filasPagNot = ConstantsCM.PAGINACION_CINCO_FILAS;
    private List<CmtNotaOtMgl> lstCmtNotaOtMglsAux;
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private CmtOnyxResponseDto cmtOnyxResponseDto;
    private boolean selectTodos = true;
    private boolean deshacerTodos = false;
    private boolean ultimaAgenda = false;
    private String urlArchivoSoporte;
    private boolean activacionUCM;
    private BigDecimal tecnologiaListSelect;
    private BigDecimal segmentoListSelect;
    private BigDecimal tipoTrabajoListSelect;
    private BigDecimal subTipoTrabaoListSelect;
    private BigDecimal claseOTListSelect;
    private List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDto;
    private int actualNotOtHij;
    private String numPaginaNotOtHij = "1";
    private List<Integer> paginaListNotOtHij;
    private String pageActualNotOtHij;
    private int filasPagNotOtHij = ConstantsCM.PAGINACION_CUATRO_FILAS;
    private List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDtoAux;
    private OnyxOtCmDir onyxOtCmDir = null;
    private String nombreUsuario = "";
    private String areaResponsable = "";
    private List<CapacidadAgendaDto> capacidad = new ArrayList<>();
    private CmtBasicaMgl estadoAnteriorRazonado;
    private boolean isRequestGral;
    private String json;
    private String[] partesMensaje;
    private List<CmtAgendamientoMgl> agendasListConsulta;
    private List<String> agendasList;
    private GeograficoPoliticoMgl centroPoblado;
    private String msgFactibilidadOt = null;

    public OtMglBean() {
        try {
            this.setSubTipoTrabaoListSelect(BigDecimal.ZERO);
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
        } catch (IOException ioe) {
            String msg = "Se produse un error de carga informacion, vuelva a realizar el intento de nuevo: IOExepcion:"+ioe.getMessage()+"";
            FacesUtil.mostrarMensajeError(msg, ioe);
        } catch (Exception ex) {
            String msg = "Se produse un error de construccion, vuelva a realizar el intento de nuevo: Error Generico:"+ex.getMessage()+"";
            FacesUtil.mostrarMensajeError(msg, ex);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            comentarioNotaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVT);
            mostrarBotonGuardarComentario = false;
            mostrarBotonGuardarNota = true;
            mostrarListaNotas = true;
            mostrarPanelCrearNotas = false;
            mostrarPanelListComentarios = false;
            mostrarPanelCrearComentarios = false;
            cargarListasOt();
        } catch (ApplicationException ae) {
            FacesUtil.mostrarMensajeError(ae.getMessage(), ae);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(e.getMessage(), e);
        }
        
    }

    public void goActualizar(CmtTipoOtMgl item) {
        String a = "";
    }
    

    public String cambiarTabDet(String tabSelected) {
        String formTabDetSeleccionado;
        ConstantsCM.TABS_DET_OT Seleccionado = ConstantsCM.TABS_DET_OT.valueOf(tabSelected);
        switch (Seleccionado) {
            case GENERAL_OT:
                formTabDetSeleccionado = "otTabGeneral";
                selectedTab = "true";
                break;
            case AGENDA:
                formTabDetSeleccionado = "otTabAgenda";
                selectedTab = "AGENDA";
                break;
            case NOTAS:
                formTabDetSeleccionado = "otTabNotas";

                selectedTab = "NOTAS";
                break;
            case INVENTARIO:
                formTabDetSeleccionado = "otTabInventario";
                selectedTab = "INVENTARIO";
                break;
            case CONTACTOS:
                formTabDetSeleccionado = "otTabContactos";
                selectedTab = "CONTACTOS";
                break;
            case BITACORA:
                formTabDetSeleccionado = "otTabsBitacora";
                selectedTab = "BITACORA";
                break;
            default:
                formTabDetSeleccionado = "otTabGeneral";
                selectedTab = "GENERAL";
                break;
        }
        return ConstantsCM.PATH_VIEW_OT + formTabDetSeleccionado;
    }

    public String cambiarTabPenetracion(String tabSelected) {
        String formTabDetSeleccionado;
        ConstantsCM.TABS_DET_OT_PENETRACION Seleccionado = ConstantsCM.TABS_DET_OT_PENETRACION.valueOf(tabSelected);
        switch (Seleccionado) {
            case TECNOLOGIAS:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/tecnologiasGeo";
                selectedTab = "TECNOLOGIAS";
                break;
            case TECNOLOGIASINST:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/tecnologiasInst";
                selectedTab = "TECNOLOGIASINST";
                break;
            case META:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/meta";
                selectedTab = "META";
                break;
            case RESUMEN:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/resumen";
                selectedTab = "RESUMEN";
                break;
            case COSTOS:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/costos";
                selectedTab = "COSTOS";
                break;
            default:
                formTabDetSeleccionado = "/view/MGL/CM/tabs/tecnologiasGeo";
                selectedTab = "TECNOLOGIAS";
                break;
        }
        return ConstantsCM.PATH_VIEW_OT + formTabDetSeleccionado;
    }

    public String cambiarTab(String tabSelected) {
        try {
            String formTabSeleccionado;
            ConstantsCM.TABS_OT Seleccionado = ConstantsCM.TABS_OT.valueOf(tabSelected);
            switch (Seleccionado) {
                case GENERAL:
                    if(ordenTrabajo != null && ordenTrabajo.getIdOt() != null){
                        refrescarOt(ordenTrabajo);
                    }
                    formTabSeleccionado = "generalOt";
                    selectedTab = "GENERAL";
                    break;
                case CREAR:
                    formTabSeleccionado = "crearOt";
                    selectedTab = "CREAR";
                    break;
                case FVT:
                    formTabSeleccionado = "visitaTecnicaOtList";
                    selectedTab = "FVT";
                    break;
                case FAC:
                    formTabSeleccionado = "acometidasOtList";
                    selectedTab = "FAC";
                    break;
                case FGA:
                    formTabSeleccionado = "gestionAvanzadaOtList";
                    selectedTab = "FGA";
                    break;
                case TRACK:
                    formTabSeleccionado = "trackOt";
                    selectedTab = "TRACK";
                    break;
                case NOTAS:
                    if(ordenTrabajo != null && ordenTrabajo.getIdOt() != null){
                        refrescarOt(ordenTrabajo);
                    }
                    formTabSeleccionado = "notasOt";
                    selectedTab = "NOTAS";
                    break;
                case OTRR:
                    formTabSeleccionado = "OtrR";
                    selectedTab = "OTRR";
                    break;
                case INVENTARIOS_OT:
                    formTabSeleccionado = "inventariosOt";
                    selectedTab = "INVENTARIOS_OT";
                    break;
                case AGENDA:
                    formTabSeleccionado = "otTabAgenda";
                    selectedTab = "AGENDA";
                    break;
                case CONTACTOS:
                    formTabSeleccionado = "otTabContactos";
                    selectedTab = "CONTACTOS";
                    break;
                case BITACORA:
                    formTabSeleccionado = "otTabsBitacora";
                    selectedTab = "BITACORA";
                    mostrarAuditoria();
                    break;
                case BACKLOG:
                    formTabSeleccionado = "otTabBacklog";
                    selectedTab = "BACKLOG";
                    break;
                case AGENDAMIENTO:
                    formTabSeleccionado = "agendamientosOt";
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case GENERALOTEJECUCION:
                    formTabSeleccionado = "generalOtEjecucion";
                    selectedTab = "GENERALOTEJECUCION";
                    activacionUCM = activaDesactivaUCM();
                    break;
                case NOTASOTEJECUCION:
                    formTabSeleccionado = "notasOtEjecucion";
                    selectedTab = "NOTASOTEJECUCION";
                    break;
                case OTONYX:
                    formTabSeleccionado = "otTabOtOnyx";
                    selectedTab = "OTONYX";
                    consultaInicialOtOnyx();
                    break;
                case HISTORICO_AGENDAS:
                    formTabSeleccionado = "historicoAgendasOt";
                    selectedTab = "HISTORICO_AGENDAS";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                default:
                    formTabSeleccionado = "generalOt";
                    selectedTab = "GENERAL";
                    break;
            }
            return ConstantsCM.PATH_VIEW_OT + formTabSeleccionado;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab:"+e.getMessage()+" ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab:"+e.getMessage()+" ", e, LOGGER);
        }
        return null;
    }

    public String ingresar() {
        try {
            isVisible = false;
            ordenTrabajo = new CmtOrdenTrabajoMgl();
            estadoInternoExterno = null;
            //valbuenayf ajuste clase OT
            if (ordenTrabajo.getClaseOrdenTrabajo() == null) {
                ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
            }
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            CmtCuentaMatrizMgl cm = cuentaMatrizBean.getCuentaMatriz();

            if (cm == null || cm.getCuentaMatrizId() == null) {
                String msn = "Debe estar sobre el detalle de una cuenta matriz para realizar la creacion de una OT";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);

            cargarListasOt();

            notaOtMgl = new CmtNotaOtMgl();

            ordenTrabajo = new CmtOrdenTrabajoMgl();
            //valbuenayf ajuste clase OT
            ordenTrabajo.setClaseOrdenTrabajo(new CmtBasicaMgl());
            //Asignamos la CM a la orden de trabajo 
            ordenTrabajo.setCmObj(cm);
            //Asignamos el tipo de Segmento a la orden trabajo
            ordenTrabajo.setSegmento(new CmtBasicaMgl());
            ordenTrabajo.setFechaCreacion(new Date());
            //Asignamos el tipo orden de trabaja
            CmtTipoOtMgl cmtTipoOtMgl = new CmtTipoOtMgl();
            cmtTipoOtMgl.setIdTipoOt(BigDecimal.ZERO);
            ordenTrabajo.setTipoOtObj(cmtTipoOtMgl);
            //Asignamos el usario de la creacion
            if (usuarioLogin.getCedula() != null) {
                ordenTrabajo.setUsuarioCreacionId(new BigDecimal(usuarioLogin.getCedula()));
            }
            CmtBasicaMgl basicaMglTc = new CmtBasicaMgl();
            basicaMglTc.setBasicaId(BigDecimal.ZERO);
            ordenTrabajo.setBasicaIdTecnologia(basicaMglTc);
            CmtBasicaMgl basicaMglTt = new CmtBasicaMgl();
            basicaMglTt.setBasicaId(BigDecimal.ZERO);
            ordenTrabajo.setBasicaIdTipoTrabajo(basicaMglTt);
            this.setSubTipoTrabaoListSelect(new BigDecimal(BigInteger.ZERO));

            return cambiarTab("GENERAL");

        } catch (ApplicationException e) {
            LOGGER.error("Error en ingresar. " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en ingresar. " + e.getMessage(), e);
            return null;
        }
    }

    public String ingresarGestion(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) {
        try {

            ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(cmtOrdenTrabajoMgl.getIdOt());

            if (ordenTrabajo == null) {
                String msn = "No fue posible encontrar la OT";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }

            notaOtMgl = new CmtNotaOtMgl();

            CmtBasicaMgl flujoTipoOtMgl = ordenTrabajo.getTipoOtObj().getTipoFlujoOt();
            CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
            //Autor: Victor Bocanegra
            //Capturo el flag del  tipo de OT para validar si maneja inventario
            int idTipoOT = ordenTrabajo.getTipoOtObj().getFlagInv();

            if (idTipoOT == 1) {
                isVisible = true;
            } else {
                isVisible = false;
            }

            searchSubTipoOrdenByTipoGeneralOt(ordenTrabajo);
            estadosFlujoList = estadoxFlujoMglFacadeLocal.getEstadosByTipoFlujo(flujoTipoOtMgl, tecnologia);
            estadoInternoExterno = estadoIntxExtMglFacaceLocal.
                    findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());

            //cargamos los estados a los cuales puede pasar
            //con base en el estado actual de la OT
            cargarDetalleFlujo(ordenTrabajo);

            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                notaOtMglList = notaOtMglFacadeLocal.findByOt(ordenTrabajo);
            }
            lstCmtNotaOtMglsAux = notaOtMglList;
            subTipoTrabaoListSelect = ordenTrabajo.getTipoOtObj().getIdTipoOt();
            cargarListas();
            ordenTrabajo = ordenTrabajoMglFacadeLocal.bloquearDesbloquearOrden(ordenTrabajo, true);
            return cambiarTab("GENERAL");

        } catch (ApplicationException e) {
            LOGGER.error("Error en ingresarGestion. " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en ingresarGestion. " + e.getMessage(), e);
            return null;
        }
    }
    
    public void refrescarOt(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) {
        try {

            ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(cmtOrdenTrabajoMgl.getIdOt());
            notaOtMgl = new CmtNotaOtMgl();

            CmtBasicaMgl flujoTipoOtMgl = ordenTrabajo.getTipoOtObj().getTipoFlujoOt();
            CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
            //Autor: Victor Bocanegra
            //Capturo el flag del  tipo de OT para validar si maneja inventario
            int idTipoOT = ordenTrabajo.getTipoOtObj().getFlagInv();

            if (idTipoOT == 1) {
                isVisible = true;
            } else {
                isVisible = false;
            }

            searchSubTipoOrdenByTipoGeneralOt(ordenTrabajo);
            estadosFlujoList = estadoxFlujoMglFacadeLocal.getEstadosByTipoFlujo(flujoTipoOtMgl, tecnologia);
            estadoInternoExterno = estadoIntxExtMglFacaceLocal.
                    findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());

            //cargamos los estados a los cuales puede pasar
            //con base en el estado actual de la OT
            cargarDetalleFlujo(ordenTrabajo);

            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                notaOtMglList = notaOtMglFacadeLocal.findByOt(ordenTrabajo);
            }
            lstCmtNotaOtMglsAux = notaOtMglList;
            subTipoTrabaoListSelect = ordenTrabajo.getTipoOtObj().getIdTipoOt();
            mostrarListaNotas = true;
            mostrarPanelCrearNotas = false;
            mostrarPanelListComentarios = false;

        } catch (ApplicationException e) {
            LOGGER.error("Error en refrescarOt. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en refrescarOt. " + e.getMessage(), e);
        }
    }

    public String ingresarGestionOtEjecucion(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) {
        try {

            ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(cmtOrdenTrabajoMgl.getIdOt());

            if (ordenTrabajo == null) {
                String msn = "No fue posible encontrar la OT";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            visitaTecnicaAct = cmtVisitaTecnicaMglFacadeLocal.
                    findVTActiveByIdOt(ordenTrabajo);

            if (visitaTecnicaAct == null) {
                String msn = "No hay una visita tecnica activa para la orden";
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            lstSubEdiVT = subEdificiosVtFacadeLocal.findByVtAndAcometida(visitaTecnicaAct);

            if (lstSubEdiVT.size() > 0) {
                for (CmtSubEdificiosVt subEdificiosVt : lstSubEdiVT) {
                    if (subEdificiosVt.getGeneraAcometida() != null) {
                        if (subEdificiosVt.getGeneraAcometida().equalsIgnoreCase("Y")) {
                            subEdificiosVt.setGenAco(true);
                        } else {
                            subEdificiosVt.setGenAco(false);
                        }
                    }
                }
            }

            lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findFilesVTByIdOt(ordenTrabajo);
            lstArchivosAcoMgls = cmtArchivosVtMglFacadeLocal.findFilesByIdOt(ordenTrabajo);

            notaOtMgl = new CmtNotaOtMgl();

            CmtBasicaMgl flujoTipoOtMgl = ordenTrabajo.getTipoOtObj().getTipoFlujoOt();
            CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();

            searchSubTipoOrdenByTipoGeneralOt(ordenTrabajo);
            estadosFlujoList = estadoxFlujoMglFacadeLocal.getEstadosByTipoFlujo(flujoTipoOtMgl, tecnologia);
            estadoInternoExterno = estadoIntxExtMglFacaceLocal.
                    findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());

            //cargamos los estados a los cuales puede pasar
            //con base en el estado actual de la OT
            cargarDetalleFlujo(ordenTrabajo);

            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                notaOtMglList = notaOtMglFacadeLocal.findByOt(ordenTrabajo);
            }
            cargarListas();
            selectTodos = true;
            deshacerTodos = false;
            return cambiarTab("GENERALOTEJECUCION");

        } catch (ApplicationException e) {
            LOGGER.error("Error en ingresarGestionOtEjecucion. " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en ingresarGestionOtEjecucion. " + e.getMessage(), e);
            return null;
        }
    }

    public String crearOT() {
        msgFactibilidadOt = null;//Brief 98062
        try {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            //valida los campos del formulario antes de proceder con la creación de la OT
            if ( ordenTrabajo.getBasicaIdTecnologia().getBasicaId() != null
                    && !ordenTrabajo.getBasicaIdTecnologia().getBasicaId().equals(BigDecimal.ZERO)
                    && ordenTrabajo.getBasicaIdTipoTrabajo().getBasicaId() != null
                    && !ordenTrabajo.getBasicaIdTipoTrabajo().getBasicaId().equals(BigDecimal.ZERO)
                    && ordenTrabajo.getSegmento().getBasicaId() != null
                    && !ordenTrabajo.getSegmento().getBasicaId().equals(BigDecimal.ZERO)
                    && ordenTrabajo.getTipoOtObj().getIdTipoOt() != null
                    && !ordenTrabajo.getTipoOtObj().getIdTipoOt().equals(BigDecimal.ZERO)
                    && ordenTrabajo.getEstadoInternoObj().getBasicaId() != null
                    && !ordenTrabajo.getEstadoInternoObj().getBasicaId().equals(BigDecimal.ZERO)
                    ) {
                if (ordenTrabajo.getFechaProgramacion() == null || ordenTrabajo.getFechaProgramacion().toString().isEmpty()) {
                    String msg = "Debe Ingresar Fecha de Entrega";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    return null;
                } else {
                    if (ordenTrabajo.getFechaProgramacion().compareTo(new Date()) < 0) {
                        String msg = "La fecha de entrega debe ser mayor a hoy";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));
                        return null;

                    }
                    Calendar fechaSelected = new GregorianCalendar();
                    fechaSelected.setTime(ordenTrabajo.getFechaProgramacion());
                    int dia = fechaSelected.get(Calendar.DAY_OF_MONTH);

                }
                //consulto el estado general de la ot segun el estado interno
                if (ordenTrabajo.getEstadoInternoObj() != null) {
                    estadoInternoExterno = estadoIntxExtMglFacaceLocal.
                            findByEstadoInterno(ordenTrabajo.getEstadoInternoObj());
                    ordenTrabajo.setEstadoGeneralOt(estadoInternoExterno.getIdEstadoExt().getBasicaId());
                }
                /*Brief 98062 */
                /* Cierre brief 98062*/
                ordenTrabajo = ordenTrabajoMglFacadeLocal.crearOt(ordenTrabajo);
                if (ordenTrabajo.getIdOt() != null) {
                    ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(ordenTrabajo.getIdOt());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            ConstantsCM.MSN_PROCESO_EXITOSO + ", Se ha creado la Orden de trabajo: <b>"
                            + ordenTrabajo.getIdOt().toString() + "</b>", ""));
                    //cargamos los estados a los cuales puede pasar
                    //con base en el estado actual de la OT
                    cargarDetalleFlujo(ordenTrabajo);
                    //Autor: Victor Bocanegra
                    //Capturo el flag del  tipo de OT para validar si maneja inventario
                    int idTipoOT = ordenTrabajo.getTipoOtObj().getFlagInv();

                    isVisible = idTipoOT == 1;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        ConstantsCM.MSN_PROCESO_FALLO + "El formualario no se ha completado, exiten campos vacios: ", ""));
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearOT:"+e.getMessage()+" " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearOT:"+e.getMessage()+" " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);
        }
        return cambiarTab("GENERAL");
    }

    /**
     * Determina cual es la tecnologia seleccionada en la creación de la OT,
     * y si requiere validación de factibilidad, realiza la comprobación.
     *
     * @return Retorna true si la tecnología seleccionada es factible
     * @throws ApplicationException Excepción personalizada
     * @author Gildardo Mora
     */
    private boolean tecnologiaSeleccionadaEsFactible() throws ApplicationException {

        if (listTablaBasicaTecnologias.isEmpty() || Objects.isNull(ordenTrabajo)
                || Objects.isNull(ordenTrabajo.getBasicaIdTecnologia())
                || Objects.isNull(ordenTrabajo.getBasicaIdTecnologia().getBasicaId())) {
            throw new ApplicationException("Ocurrió un error al determinar la tecnología seleccionada para la OT");
        }

        //obtiene el valor de la tecnología seleccionada en la orden de trabajo
        String tecnologiaSeleccionada = listTablaBasicaTecnologias.stream()
                .filter(x -> x.getBasicaId().equals(
                        ordenTrabajo.getBasicaIdTecnologia().getBasicaId()))
                .map(CmtBasicaMgl::getCodigoBasica)
                .findAny().orElse(null);

        if (Objects.isNull(tecnologiaSeleccionada) || tecnologiaSeleccionada.isEmpty()) {
            msgFactibilidadOt = "No hay tecnología seleccionada en la OT";
            return false;
        }

        boolean isTecnologiaFactible = true;
        //Si la tecnología lo requiere se valida su factibilidad
        try {
            switch (tecnologiaSeleccionada) {
                case "FOG":
                case "FOU":
                case "RFO":
                    isTecnologiaFactible = validarFactibilidadTecnologia(tecnologiaSeleccionada);
                    break;
            }
            return isTecnologiaFactible;

        } catch (ApplicationException e) {
            String msgError = ClassUtils.getCurrentMethodName(this.getClass())
                    +" " + e.getMessage();
            LOGGER.error(msgError + e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Validar la factibilidad para la tecnología seleccionada al momento de crear la OT
     *
     * @param tecnologiaSeleccionada {@link String} Tecnología seleccionada en la creación de la OT
     * @return Retorna true si es factible la tecnología
     * @author Gildardo Mora
     */
    private boolean validarFactibilidadTecnologia(String tecnologiaSeleccionada) throws ApplicationException {

        try {
            //Recupera los datos del bean en sesion de la cuenta matriz
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            Objects.requireNonNull(cuentaMatrizBean, "No se pudo leer los datos de la Cuenta Matriz");
            CmtCuentaMatrizMgl cuentaMatrizMgl = cuentaMatrizBean.getCuentaMatriz();//recupera los datos de la Cuenta Matriz
            // Asigna datos requeridos para luego consultar a siti data
            DrDireccion drDireccion = assignDatosDrDireccion(cuentaMatrizMgl);
            /* ---------- valida si la tecnología seleccionada para crear la OT es factible ---------- */
            // Validación  de factibilidad para tecnología fibra óptica GPON
            if (tecnologiaSeleccionada.equals("FOG")) {
                //consulta al servicio SITIDATA para obtener los nodos cobertura
                AddressService responseSitiData = callSitiDataAddressService(drDireccion);

                if (responseSitiData.getNodoTres() == null
                        || responseSitiData.getNodoTres().isEmpty()
                        || responseSitiData.getNodoTres().equals("")
                        || responseSitiData.getNodoTres().startsWith("N")) {
                    return validarEscalamientoCobertura(tecnologiaSeleccionada, cuentaMatrizMgl);
                }
            }

            // Validación de factibilidad para tecnología Fibra Óptica Unifilar
            if (tecnologiaSeleccionada.equals("FOU")) {
                //consulta al servicio SITIDATA para obtener los nodos cobertura
                AddressService responseSitiData = callSitiDataAddressService(drDireccion);

                if (responseSitiData.getNodoZonaUnifilar() == null
                        || responseSitiData.getNodoZonaUnifilar().isEmpty()
                        || responseSitiData.getNodoZonaUnifilar().equals("")
                        || responseSitiData.getNodoZonaUnifilar().startsWith("S")) {
                    return validarEscalamientoCobertura(tecnologiaSeleccionada, cuentaMatrizMgl);
                }
            }
            // Validación de factibilidad para tecnología Red FO
            if (tecnologiaSeleccionada.equals("RFO")) {
                /* Para la tecnología RFO no se valida la cobertura con SITIDATA, sino que
                 * se hace validando si esta o no en la penetración de nodos de la CCMM en MER */
                List<String> penetracionCoberturaCuentaMatriz = findPenetracionCoberturaCuentaMatriz(cuentaMatrizMgl);
                if (!penetracionCoberturaCuentaMatriz.contains("RFO")) {
                    msgFactibilidadOt = generateMsgFactibilidadOt(tecnologiaSeleccionada, NO_EXISTE_NODO_COBERTURA);
                    return false;
                }
            }

            return true;

        } catch (ApplicationException ae) {
            LOGGER.error("AppExc: OtMglBean: " + ClassUtils.getCurrentMethodName(this.getClass()), ae);
            throw new ApplicationException(ae.getMessage(), ae);
        }
    }

    /**
     * Ejecuta el llamado al servicio SITIDATA
     *
     * @param drDireccion {@link DrDireccion}
     * @return responseSitiData {@link AddressService}
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    private AddressService callSitiDataAddressService(DrDireccion drDireccion) throws ApplicationException {
        ConsultaNodoSitiData consultaNodoSitiData = new ConsultaNodoSitiData();
        AddressService responseSitiData = consultaNodoSitiData.findNodosSitiData(drDireccion, centroPoblado);
        Objects.requireNonNull(responseSitiData, "No se obtuvo respuesta efectiva desde el servicio SITIDATA "
                + "en OtMglBean " + ClassUtils.getCurrentMethodName(this.getClass()));
        return responseSitiData;
    }

    /**
     * Asigna los datos de dirección requeridos
     *
     * @param ccmm {@link CmtCuentaMatrizMgl} Cuenta Matriz
     * @return drDireccion {@link DrDireccion}
     * @throws ApplicationException Excepción de la aplicación
     * @author Gildaro Mora
     */
    private DrDireccion assignDatosDrDireccion(CmtCuentaMatrizMgl ccmm) throws ApplicationException {

        try {
            centroPoblado = null;
            CmtDireccionMgl cmtDireccionMgl = new CmtDireccionMgl();
            DrDireccion drDireccion = new DrDireccion();

            if (Objects.isNull(ccmm.getCentroPoblado()) || ccmm.getDireccionesList().isEmpty()){
                throw new ApplicationException("No se pudo determinar la información de dirección");
            }

            centroPoblado = ccmm.getCentroPoblado();

            if (Objects.nonNull(ccmm.getDireccionPrincipal())){
                cmtDireccionMgl = ccmm.getDireccionPrincipal();
            }

            if(Objects.isNull(cmtDireccionMgl)){
                cmtDireccionMgl = ccmm.getDireccionesList().get(0);//asigna  la drDireccion por defecto
            }

            drDireccion.setIdTipoDireccion(cmtDireccionMgl.getCodTipoDir());
            drDireccion.setDirPrincAlt(cmtDireccionMgl.getDetalleDireccionEntity().getDirprincalt());
            drDireccion.setBarrio(cmtDireccionMgl.getBarrio());
            drDireccion.setTipoViaPrincipal(cmtDireccionMgl.getTipoViaPrincipal());
            drDireccion.setNumViaPrincipal(cmtDireccionMgl.getNumViaPrincipal());
            drDireccion.setLtViaPrincipal(cmtDireccionMgl.getDetalleDireccionEntity().getLtviaprincipal());
            drDireccion.setNlPostViaP(cmtDireccionMgl.getNlPostViaP());
            drDireccion.setBisViaPrincipal(cmtDireccionMgl.getBisViaPrincipal());
            drDireccion.setCuadViaPrincipal(cmtDireccionMgl.getCuadViaPrincipal());
            drDireccion.setTipoViaGeneradora(cmtDireccionMgl.getDetalleDireccionEntity().getTipoviageneradora());
            drDireccion.setNumViaGeneradora(cmtDireccionMgl.getNumViaGeneradora());
            drDireccion.setLtViaGeneradora(cmtDireccionMgl.getDetalleDireccionEntity().getLtviageneradora());
            drDireccion.setNlPostViaG(cmtDireccionMgl.getNlPostViaG());
            drDireccion.setBisViaGeneradora(cmtDireccionMgl.getBisViaGeneradora());
            drDireccion.setCuadViaGeneradora(cmtDireccionMgl.getDetalleDireccionEntity().getCuadviageneradora());
            drDireccion.setPlacaDireccion(cmtDireccionMgl.getPlacaDireccion());
            drDireccion.setCpTipoNivel1(cmtDireccionMgl.getCpTipoNivel1());
            drDireccion.setCpTipoNivel2(cmtDireccionMgl.getCpTipoNivel2());
            drDireccion.setCpTipoNivel3(cmtDireccionMgl.getCpTipoNivel3());
            drDireccion.setCpTipoNivel4(cmtDireccionMgl.getCpTipoNivel4());
            drDireccion.setCpTipoNivel5(cmtDireccionMgl.getCpTipoNivel5());
            drDireccion.setCpTipoNivel6(cmtDireccionMgl.getCpTipoNivel6());
            drDireccion.setCpValorNivel1(cmtDireccionMgl.getCpValorNivel1());
            drDireccion.setCpValorNivel2(cmtDireccionMgl.getCpValorNivel2());
            drDireccion.setCpValorNivel3(cmtDireccionMgl.getCpValorNivel3());
            drDireccion.setCpValorNivel4(cmtDireccionMgl.getCpValorNivel4());
            drDireccion.setCpValorNivel5(cmtDireccionMgl.getCpValorNivel5());
            drDireccion.setCpValorNivel6(cmtDireccionMgl.getCpValorNivel6());
            drDireccion.setMzTipoNivel1(cmtDireccionMgl.getMzTipoNivel1());
            drDireccion.setMzTipoNivel2(cmtDireccionMgl.getMzTipoNivel2());
            drDireccion.setMzTipoNivel3(cmtDireccionMgl.getMzTipoNivel3());
            drDireccion.setMzTipoNivel4(cmtDireccionMgl.getMzTipoNivel4());
            drDireccion.setMzTipoNivel5(cmtDireccionMgl.getMzTipoNivel5());
            drDireccion.setMzTipoNivel6(cmtDireccionMgl.getMzTipoNivel6());
            drDireccion.setMzValorNivel1(cmtDireccionMgl.getMzValorNivel1());
            drDireccion.setMzValorNivel2(cmtDireccionMgl.getMzValorNivel2());
            drDireccion.setMzValorNivel3(cmtDireccionMgl.getMzValorNivel3());
            drDireccion.setMzValorNivel4(cmtDireccionMgl.getMzValorNivel4());
            drDireccion.setMzValorNivel5(cmtDireccionMgl.getMzValorNivel5());
            drDireccion.setMzValorNivel6(cmtDireccionMgl.getMzValorNivel6());
            drDireccion.setItTipoPlaca(cmtDireccionMgl.getItTipoPlaca());
            drDireccion.setItValorPlaca(cmtDireccionMgl.getItValorPlaca());
            drDireccion.setEstadoDirGeo(cmtDireccionMgl.getEstadoDirGeo());
            drDireccion.setLetra3G(cmtDireccionMgl.getLetra3G());
            drDireccion.setEstadoRegistro(1);
            drDireccion.setFechaCreacion(cmtDireccionMgl.getDireccionObj().getFechaCreacion());
            drDireccion.setUsuarioCreacion(cmtDireccionMgl.getDireccionObj().getUsuarioCreacion());
            drDireccion.setId(cmtDireccionMgl.getDireccionId());

            drDireccion.setDirEstado(cmtDireccionMgl.getEstadoDirGeo());
            drDireccion.setDireccionRespuestaGeo(cmtDireccionMgl.getDireccionObj().getDirFormatoIgac());
            drDireccion.setMultiOrigen(ccmm.getCentroPoblado().getGpoMultiorigen());
            return drDireccion;
        } catch (ApplicationException ae){
            LOGGER.error(ae);
            throw new ApplicationException("Error: " + ClassUtils.getCurrentMethodName(this.getClass()), ae);
        }
    }

    /**
     * Realiza la validación de la solicitud de cobertura
     *
     * @param solicitudCobertura {@link Solicitud} Solicitud de cobertura
     * @param tecnologiaSeleccionada {@link String} Tecnología seleccionada en la orden de trabajo
     * @return Retorna true si la solicitud de cobertura fue cerrada como positiva
     * @author Gildardo Mora
     */
    private boolean validarSolicitudCobertura(Solicitud solicitudCobertura, String tecnologiaSeleccionada)
            throws ApplicationException {

       if (solicitudCobertura.getIdSolicitud() == null){
           return false;
       }

        //valida si ya fue gestionada la Solicitud reciente
        if (solicitudCobertura.getEstado().equals("FINALIZADO")) {
            //valida si la solicitud tiene respuesta negativa y si cumple la vigencia
            if (solicitudCobertura.getRptGestion().equals("FACTIBILIDAD NEGATIVA")) {
                //valida si la fecha de cancelación esta fuera de la vigencia
                msgFactibilidadOt = isValidaVigenciaFechaSolicitud(solicitudCobertura.getFechaCancelacion()) ?
                        generateMsgFactibilidadOt(tecnologiaSeleccionada, ESCALAMIENTO_RESULTADO_NEGATIVO)
                        : generateMsgFactibilidadOt(tecnologiaSeleccionada, REQUIERE_ESCALAMIENTO);

                return false;
            }

            if (solicitudCobertura.getRptGestion().equals("FACTIBILIDAD POSITIVA")) {
                //valida si la fecha de cancelación esta fuera de la vigencia
                if (!isValidaVigenciaFechaSolicitud(solicitudCobertura.getFechaCancelacion())) {
                    msgFactibilidadOt = generateMsgFactibilidadOt(tecnologiaSeleccionada, REQUIERE_ESCALAMIENTO);
                    return false;
                }

                return true;
            }
        }

        if (solicitudCobertura.getEstado().equals("PENDIENTE")) {
            msgFactibilidadOt = generateMsgFactibilidadOt(tecnologiaSeleccionada, ESCALAMIENTO_VIGENTE);
        }

        return false;
    }

    /**
     * Obtiene la lista de tecnologías de penetreración de coberturas de la CCMM
     *
     * @param ccmm {@link CmtCuentaMatrizMgl} Cuenta Matriz
     * @return tecnologiasPenetracion {@link List<String>}
     * @throws ApplicationException Excepcion personalizada de la aplicación
     * @author Gildardo Mora
     */
    private List<String> findPenetracionCoberturaCuentaMatriz(CmtCuentaMatrizMgl ccmm) throws ApplicationException {
        ValidatePenetrationCuentaMatriz validatePenetrationCuentaMatriz = new ValidatePenetrationCuentaMatriz();
        return validatePenetrationCuentaMatriz.findTecnologiasPenetracionCuentaMatriz(ccmm);
    }

    /**
     * Genera el mensaje en función del resultado de factibilidad y escalamiento
     *
     * @param tecnologia {@link String} Tecnología seleccionada en la OT
     * @param tipoAccion {@link RESULTADO_FACTIBILIDAD_TEC_CM} Acción resultante en la factibilidad de tecnologia de CCMM
     * @return msgFactibilidad {@link String}
     * @author Gildardo Mora
     */
    private String generateMsgFactibilidadOt(String tecnologia, RESULTADO_FACTIBILIDAD_TEC_CM tipoAccion) {
        String msgFactibilidad = null;

        switch (tipoAccion) {

            case REQUIERE_ESCALAMIENTO:
                msgFactibilidad = "<html> No se puede realizar la OT:<ul><li> "
                        + "Debe realizar un escalamiento de factibilidad "
                        + "</li><li> para la tecnología "
                        + "<b>" + tecnologia + "</b>"
                        + "</li></ul></html> ";
                break;

            case ESCALAMIENTO_RESULTADO_NEGATIVO:
                msgFactibilidad = "<html> No se puede realizar la OT: <br><ul><li> "
                        + "No es posible generar la OT, ya que la factibilidad "
                        + "</li><li> para la tecnología <b>"
                        + tecnologia
                        + "</b> para esta CCMM es <b>NEGATIVA</b></li></ul></html> ";
                break;

            case ESCALAMIENTO_VIGENTE:
                msgFactibilidad = "<html> No se puede realizar la OT:<ul><li> "
                        + "No es posible generar la OT, hay una solicitud "
                        + "</li><li> de escalamiento <b>PENDIENTE</b> para la tecnología <b>"
                        + tecnologia
                        + "</b> </li></ul></html> ";
                break;

            case NO_EXISTE_NODO_COBERTURA:
                msgFactibilidad = "<html> No se puede realizar la OT:"
                        + "<ul><li> No existe nodo de cobertura para la tecnología "
                        + " <b>" + tecnologia
                        + "</b></li> </ul></html> ";
                break;

        }
        return msgFactibilidad;

    }

    /**
     * Verifica la solicitud de escalamiento sobre la tecnologia seleccionada en la OT
     * Para los casos de FOG y FOU se valida la existencia de solicitud de escalamiento.
     *
     * @param cuentaMatrizMgl        {@link CmtCuentaMatrizMgl} Información de la CCMM
     * @return si presenta escalamiento válido de factibilidad
     * @throws ApplicationException Excepción de la aplicación
     * @author Gildardo Mora
     */
    private boolean validarEscalamientoCobertura(String tecnologiaSeleccionada,
            CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {

        CmtDireccionMgl cmtDireccionMglPrincipal = cuentaMatrizMgl.getDireccionPrincipal();
        if (Objects.isNull(cmtDireccionMglPrincipal.getDireccionObj().getDirId())
                || Objects.isNull(ordenTrabajo.getBasicaIdTecnologia().getBasicaId())) {
            throw new ApplicationException("No se obtuvieron los valores necesarios para consultar la solicitud");
        }

        BigDecimal idDireccion = cmtDireccionMglPrincipal.getDireccionObj().getDirId();
        BigDecimal idBasica = ordenTrabajo.getBasicaIdTecnologia().getBasicaId();
        Solicitud solicitudReciente = solicitudFacadeLocal.findUltimaSolicitudEscalamientoCobertura(idDireccion, idBasica);

        if (Objects.nonNull(solicitudReciente) && Objects.nonNull(solicitudReciente.getIdSolicitud())) {
            return validarSolicitudCobertura(solicitudReciente, tecnologiaSeleccionada);
        }

        //Asigna el mensaje de requerimiento de escalamiento
        msgFactibilidadOt = generateMsgFactibilidadOt(tecnologiaSeleccionada, REQUIERE_ESCALAMIENTO);
        return false;
    }

    /**
     * Determina si es vigente o no, la Solicitud de validación de cobertura procesada
     *
     * @param fechaCancelacion {@link Date} Fecha de cancelación o cierre de la solicitud
     * @return Retorna true si es válida la vigencia de la solicitud
     * @author Gildardo Mora
     */
    private boolean isValidaVigenciaFechaSolicitud(Date fechaCancelacion) throws ApplicationException {
        if (fechaCancelacion != null) {
            int diasTranscurridos = DateToolUtils.getNumberOfDaysElapsedUntilToday(fechaCancelacion);
            //si está en el rango de 2 dias de vigencia permitida de haberse concluido la solicitud.
            return (diasTranscurridos >= 0 && diasTranscurridos <= 2);
        }
        return true;
    }

    public String actualizarEstadoOT() {
        try {

            isRequestGral = false;
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            comentarioNotaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVT);
            
            if (ordenTrabajo.getFechaProgramacion() == null
                    || ordenTrabajo.getFechaProgramacion().toString().isEmpty()) {
                String msg = "Debe Ingresar Fecha de Entrega";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                return null;
            } else {
                if (ordenTrabajo.getFechaProgramacion().compareTo(new Date()) < 0) {
                    String msg = "La fecha de entrega debe ser mayor a hoy";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    return null;

                }
            }
           //Verifico si al estado que se pasa es razonado 
           boolean estadoRazonado = pasaEstadoRazonado(ordenTrabajo, estadoOtId);
           
            if (!estadoRazonado) {
                if (tieneAgendasPendientesOCanceladas(ordenTrabajo)) {
                    LOGGER.info("NO HAY AGENDAS PENDIENTES");
                } else {
                    String msg = "La orden de trabajo No: " + ordenTrabajo.getIdOt() + "  tiene agendas pendientes por cerrar";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    return null;
                }
            }
            //Verifico si el estado es razonado debe volver al estado anterior
            boolean cambioEstadorazonado = validaCambioEstadoRazonado(ordenTrabajo, estadoOtId);

            if (cambioEstadorazonado && estadoAnteriorRazonado != null) {
                LOGGER.info("PUEDE REALIZAR EL CAMBIO DE ESTADO");
            } else if (!cambioEstadorazonado && estadoAnteriorRazonado != null) {
                String msg = "Al ser configurado como estado razonado "
                        + "debe pasar al estado anterior: " + estadoAnteriorRazonado.getNombreBasica() + "";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                return null;
            }
            //actualizo el estado general de la Ot 
            if (estadoOtId != null) {
                CmtBasicaMgl basicaEstado = new CmtBasicaMgl();
                basicaEstado.setBasicaId(estadoOtId);
                estadoInternoExterno = estadoIntxExtMglFacaceLocal.
                        findByEstadoInterno(basicaEstado);
                ordenTrabajo.setEstadoGeneralOt(estadoInternoExterno.getIdEstadoExt().getBasicaId());
            }
            //Verifico si el estado es razonado debe volver al estado anterior
            ordenTrabajo = ordenTrabajoMglFacadeLocal.actualizarOt(ordenTrabajo, estadoOtId);
            //cargamos los estados a los cuales puede pasar
            //con base en el estado actual de la OT
            cargarDetalleFlujo(ordenTrabajo);

 
                if (ordenTrabajo != null && ordenTrabajo.getTipoOtObj() != null 
                        && ordenTrabajo.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("N")) {
                    //verificamos si cancelan la orden
                    CmtBasicaMgl cancelado = basicaMglFacadeLocal.
                            findByCodigoInternoApp(Constant.BASICA_EST_INT_CANCELADO);
                    CmtBasicaMgl cable = basicaMglFacadeLocal.
                            findByCodigoInternoApp(Constant.BASICA_EST_INT_CABLE);
                    if (cancelado.getBasicaId() != null) {
                        if (estadoOtId.compareTo(cancelado.getBasicaId()) == 0) {
                            //buscamos la ot de referencia
                            CmtOrdenTrabajoMgl ordenTrabajoRef = ordenTrabajoMglFacadeLocal.
                                    findOtById(ordenTrabajo.getOrdenTrabajoReferencia());
                            if (ordenTrabajoRef.getIdOt() != null) {
                                //buscamos la vt activa  
                                CmtBasicaMgl estIntPenDocCierreComercial
                                        = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);

                                visitaTecnicaAct = cmtVisitaTecnicaMglFacadeLocal.
                                        findVTActiveByIdOt(ordenTrabajoRef);
                                if (visitaTecnicaAct!= null && visitaTecnicaAct.getIdVt() != null) {
                                    lstSubEdiVT = subEdificiosVtFacadeLocal.findByVt(visitaTecnicaAct);
                                    if (lstSubEdiVT != null && lstSubEdiVT.size() > 0) {

                                        //Actualizamos los subedificios Vt
                                        for (CmtSubEdificiosVt subVt : lstSubEdiVT) {

                                            if (subVt.getOtAcometidaId() != null) {
                                                subVt.setOtAcometidaId(null);
                                                subVt.setGeneraAcometida("N");
                                                subVt = subEdificiosVtFacadeLocal.update(subVt,usuarioVT,perfilVT);

                                                if (subVt.getOtAcometidaId() == null) {
                                                    LOGGER.info("Se actualiza el subedificio VT a null acometida");
                                                } else {
                                                    LOGGER.error("Ocurrio un error al actualizar el subedificio Vt");
                                                }
                                            }
                                        }
                                        if (estIntPenDocCierreComercial != null) {
                                            ordenTrabajoMglFacadeLocal.actualizarOt(ordenTrabajoRef, estIntPenDocCierreComercial.getBasicaId());
                                        }
                                    }

                                }
                            }
                        } else if (cable.getBasicaId() != null) {
                            if (estadoOtId.compareTo(cable.getBasicaId()) == 0) {
                                //Actualizamos la ot  con la fecha de habilitacion 
                                ordenTrabajo.setFechaHabilitacion(new Date());
                                ordenTrabajoMglFacadeLocal.actualizarOtCcmm(ordenTrabajo, usuarioVT, perfilVT);
                            }
                        }
                    }

                }
            String msg = ordenTrabajo.getMensajeTecnologiaRR() == null  ? " " : ordenTrabajo.getMensajeTecnologiaRR();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    ConstantsCM.MSN_PROCESO_EXITOSO + " "+ msg , ""));

            ingresarGestion(ordenTrabajo);

        } catch (ApplicationException | EJBException e) {
            LOGGER.error("Error en actualizarEstadoOT. " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(e.getMessage(), new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    ConstantsCM.MSN_ERROR_PROCESO + e.getMessage(), ""));
        }
        return cambiarTab("GENERAL");
    }

    public String actualizarEstadoOTEjecucion() {
        try {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            comentarioNotaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVT);

            CmtBasicaMgl cierreComercial = basicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.BASICA_EST_INT_CIERRECOM);

            if (cierreComercial.getBasicaId() != null) {
                if (cierreComercial.getBasicaId().compareTo(estadoOtId) == 0) {

                    int con = 0;
                    for (CmtSubEdificiosVt subEdificiosVt : lstSubEdiVT) {

                        if (subEdificiosVt.isGenAco()) {
                            con++;
                        }
                    }
                    if (con > 0) {
                        //Creo la Ot de acometida   
                        crearOtAcometida();

                        lstSubEdiVT = subEdificiosVtFacadeLocal.findByVtAndAcometida(visitaTecnicaAct);

                        if (lstSubEdiVT.size() > 0) {
                            for (CmtSubEdificiosVt subEdificiosVt : lstSubEdiVT) {
                                if (subEdificiosVt.getGeneraAcometida() != null) {
                                    if (subEdificiosVt.getGeneraAcometida().equalsIgnoreCase("Y")) {
                                        subEdificiosVt.setGenAco(true);
                                    } else {
                                        subEdificiosVt.setGenAco(false);
                                    }
                                }
                            }
                        }
                        CmtBasicaMgl penDocCierreComercial = basicaMglFacadeLocal.
                                findByCodigoInternoApp(Constant.BASICA_EST_INT_PENDOCCIECOM);

                        if (penDocCierreComercial.getBasicaId() != null) {

                            if (lstSubEdiVT.isEmpty()) {
                                ordenTrabajo = ordenTrabajoMglFacadeLocal.actualizarOtCierreComercial(ordenTrabajo, cierreComercial.getBasicaId());
                            } else {
                                ordenTrabajo = ordenTrabajoMglFacadeLocal.actualizarOtCierreComercial(ordenTrabajo, penDocCierreComercial.getBasicaId());
                            }

                            //cargamos los estados a los cuales puede pasar
                            //con base en el estado actual de la OT
                            cargarDetalleFlujo(ordenTrabajo);

                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    ConstantsCM.MSN_PROCESO_EXITOSO, ""));

                            ingresarGestion(ordenTrabajo);
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Ocurrio un error cargando el estado interno", ""));
                            return null;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Debe seleccionar un subedificio para generar Ot de acometida", ""));
                        return null;
                    }
                } else {
                    ordenTrabajo = ordenTrabajoMglFacadeLocal.actualizarOtCierreComercial(ordenTrabajo, estadoOtId);
                    //cargamos los estados a los cuales puede pasar
                    //con base en el estado actual de la OT
                    cargarDetalleFlujo(ordenTrabajo);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            ConstantsCM.MSN_PROCESO_EXITOSO, ""));

                    ingresarGestion(ordenTrabajo);
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarEstadoOTEjecucion: "+e.getMessage()+"" + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarEstadoOTEjecucion:"+e.getMessage()+" " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);            
        }
        return cambiarTab("GENERALOTEJECUCION");
    }

    public void limpiarCamposNotaOt() {
        notaOtMgl = new CmtNotaOtMgl();
        tipoNotaOtSelected = new CmtBasicaMgl();
    }

    public void guardarNotaOt() {
        try {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            comentarioNotaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVT);

            if (agendasList != null && !agendasList.isEmpty()) {
                if (validarUpdateAgenda()) {
                    notaOtMgl.setOrdenTrabajoObj(ordenTrabajo);
                    notaOtMgl.setTipoNotaObj(tipoNotaOtSelected);
                    notaOtMgl = notaOtMglFacadeLocal.crear(notaOtMgl);

                    if (notaOtMgl != null && notaOtMgl.getNotaOtId() != null) {
                        ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(ordenTrabajo.getIdOt());
                        tipoNotaOtSelected = new CmtBasicaMgl();
                        notaOtMgl = new CmtNotaOtMgl();
                        notaOtMglList = notaOtMglFacadeLocal.findByOt(ordenTrabajo);
                        lstCmtNotaOtMglsAux = notaOtMglList;
                        listInfoByPageNot(1);
                    }
                    mostrarListaNotas = true;
                    mostrarPanelCrearNotas = false;
                    mostrarPanelCrearComentarios = false;
                    mostrarPanelListComentarios = false;
                    String mensajeFin="";
                    
                    for (String idAgenda : agendasList) {
                        for (CmtAgendamientoMgl agendas : agendasListConsulta) {
                            if (agendas.getId().toString().equalsIgnoreCase(idAgenda)) {

                                cmtAgendamientoMglFacadeLocal.updateAgendasForNotas
                                  (agendas, usuarioVT, perfilVT);
                                String msn = "Se ha modificado la agenda:  " + agendas.getOfpsOtId() + "  "
                                        + "  para la ot: " + agendas.getOrdenTrabajo().getIdOt() + "\n";
                                
                                mensajeFin = mensajeFin += msn;
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            ConstantsCM.MSN_PROCESO_EXITOSO +mensajeFin, ""));
                }
            } else {
                notaOtMgl.setOrdenTrabajoObj(ordenTrabajo);
                notaOtMgl.setTipoNotaObj(tipoNotaOtSelected);
                notaOtMgl = notaOtMglFacadeLocal.crear(notaOtMgl);

                if (notaOtMgl != null && notaOtMgl.getNotaOtId() != null) {
                    ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(ordenTrabajo.getIdOt());
                    tipoNotaOtSelected = new CmtBasicaMgl();
                    notaOtMgl = new CmtNotaOtMgl();
                    notaOtMglList = notaOtMglFacadeLocal.findByOt(ordenTrabajo);
                    lstCmtNotaOtMglsAux = notaOtMglList;
                    listInfoByPageNot(1);
                }
                mostrarListaNotas = true;
                mostrarPanelCrearNotas = false;
                mostrarPanelCrearComentarios = false;
                mostrarPanelListComentarios = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        ConstantsCM.MSN_PROCESO_EXITOSO, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarNotaOt: " + e.getMessage() + " " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);
        }
    }

    public String mostarComentario() {
        tipoNotaOtSelected = notaOtMgl.getTipoNotaObj();
        mostrarListaNotas = false;
        mostrarPanelCrearNotas = false;
        mostrarPanelListComentarios = false;
        mostrarPanelCrearComentarios = true;
        return cambiarTab("NOTAS");
    }

    public String mostarComentarioOtEjecucion() {
        tipoNotaOtSelected = notaOtMgl.getTipoNotaObj();
        mostrarBotonGuardarComentario = true;
        mostrarBotonGuardarNota = false;
        mostrarListaNotas = false;
        mostrarPanelCrearNotas = false;
        mostrarPanelListComentarios = false;
        mostrarPanelCrearComentarios = true;
        return cambiarTab("NOTASOTEJECUCION");
    }

    public void guardarComentarioNotaOt() {
        try {
             ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            comentarioNotaOtMglFacadeLocal.setUser(usuarioVT, perfilVT);
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVT);
            CmtNotaOtComentarioMgl notaOtComentarioMgl = new CmtNotaOtComentarioMgl();
            notaOtComentarioMgl.setComentario(notaOtComentarioStr);
            notaOtComentarioMgl.setNotaOtObj(notaOtMgl);
            notaOtComentarioMgl = comentarioNotaOtMglFacadeLocal.crear(notaOtComentarioMgl);
            if (notaOtComentarioMgl.getNotaOtComentarioId() != null) {
                notaOtComentarioStr = "";
                ordenTrabajo = ordenTrabajoMglFacadeLocal.findOtById(ordenTrabajo.getIdOt());
            }
            mostrarBotonGuardarNota = true;
            mostrarBotonGuardarComentario = false;
            mostrarListaNotas = false;
            mostrarPanelCrearNotas = false;
            mostrarPanelCrearComentarios = false;
            mostrarPanelListComentarios = true;
            lstCmtNotaOtComentarioMglsAux = comentarioNotaOtMglFacadeLocal.findByNotaOt(notaOtMgl);
            listInfoByPageCap(1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    ConstantsCM.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNotaOt: "+e.getMessage()+" " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarComentarioNotaOt: "+e.getMessage()+" " + ConstantsCM.MSN_ERROR_PROCESO, e, LOGGER);            
        }

    }

    private void cargarDetalleFlujo(CmtOrdenTrabajoMgl ordenTrabajoVal) throws ApplicationException {
        try {
            //cargamos el detalle del flujo con base en el estado actual de
            //la orden de trabajo
            if (ordenTrabajoVal != null && ordenTrabajoVal.getTipoOtObj() != null) {
                detalleFlujoEstActual = detalleFlujoMglFacadeLocal.
                        findByTipoFlujoAndEstadoIni(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                                ordenTrabajoVal.getEstadoInternoObj(), ordenTrabajoVal.getBasicaIdTecnologia());
            }

            detalleFlujoSolAprobacion = new ArrayList<>();
            
            if (detalleFlujoEstActual != null) {
                for (CmtDetalleFlujoMgl detalleFlujoMgl : detalleFlujoEstActual) {
                    if (detalleFlujoMgl.getAprobacion().equalsIgnoreCase("Y")) {
                        detalleFlujoSolAprobacion.add(detalleFlujoMgl);
                    }

                }
            }

            //asignamos el estado actual de la OT al selectList
            if (ordenTrabajoVal != null && ordenTrabajoVal.getEstadoInternoObj() != null) {
                estadoOtId = ordenTrabajoVal.getEstadoInternoObj().getBasicaId();
            }
            
            habilitaFormVT = ordenTrabajoMglFacadeLocal.
                    validaProcesoOt(ordenTrabajoVal,
                            Constant.PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_VT);
            habilitaFormAC = ordenTrabajoMglFacadeLocal.
                    validaProcesoOt(ordenTrabajoVal,
                            Constant.PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_AC);
        } catch (ApplicationException ex) {
            LOGGER.error("Error en cargarDetalleFlujo. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en cargarDetalleFlujo: "+ex.getMessage()+" ",ex);
        } catch (Exception ex) {
            LOGGER.error("Error en cargarDetalleFlujo. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en cargarDetalleFlujo:"+ex.getMessage()+" ",ex);
        }
    }

    public void cargarListasOt() {

        try {
            CmtTipoBasicaMgl tipoGeneralOrdenId;
            tipoGeneralOrdenId = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            tipoGeneralOrdenTrabajo = basicaMglFacadeLocal.findByTipoBasica(tipoGeneralOrdenId);
            
            CmtTipoBasicaMgl tipoBasicaSegmento;
            tipoBasicaSegmento = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);
            tipoBasicaSegmento = tipoBasicaMglFacadeLocal.findById(tipoBasicaSegmento);
            tipoSegmentoOtList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaSegmento);

            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoBasicaNotaOt = tipoBasicaMglFacadeLocal.findById(tipoBasicaNotaOt);
            tipoNotaOtList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaNotaOt);

            //valbuenayf ajuste clase OT
            CmtTipoBasicaMgl tipoBasicaClaseOt;
            tipoBasicaClaseOt = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_CLASE_ORDEN_TRABAJO);
            claseOtList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaClaseOt);

            CmtTipoBasicaMgl tipoBasicaTecnologias;
            tipoBasicaTecnologias = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = basicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTecnologias);

            CmtTipoBasicaMgl tipoBasicaTipoGeneralTrabajo;
            tipoBasicaTipoGeneralTrabajo = tipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            listTablaBasicaTipoOt = basicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoGeneralTrabajo);
            tipoNotaOtSelected = new CmtBasicaMgl();
            lstDetalleFlujoSolAprobacionCrear = new ArrayList<>();
        } catch (ApplicationException ex) {
            String msg = "Error en cargarListasOt. EX000 " + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de cargar el listado de OTs: " + ex.getMessage();
            FacesUtil.mostrarMensajeError(msg, ex, LOGGER);
        }

    }

    public boolean validarUsuarioAprueba(CmtDetalleFlujoMgl detalleFlujoMgl) {
        boolean respuesta = false;
        
        try {
            if (detalleFlujoMgl.getRolAprobador() != null) {
                String rolAprueba = detalleFlujoMgl.getRolAprobador();
                respuesta = ValidacionUtil.usuarioTieneRolAprobador(rolAprueba, securityLogin);
            }
        }
        catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar el usuario: " + ex.getMessage(), ex, LOGGER);
        }
        catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de validar el usuario: " + ex.getMessage(), ex, LOGGER);
        }
                       
        return respuesta;
    }

    public boolean validarPestanaGeneral() {
        try {
            //incidente 1116 Creaciones ordenes de trabajo; programa se revienta en este punto por null
            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                CmtTipoOtMgl cmtTipoOtMgl;
                CmtTipoOtMglManager otMglManager = new CmtTipoOtMglManager();
                cmtTipoOtMgl = otMglManager.findById(ordenTrabajo.getTipoOtObj().getIdTipoOt());
                ordenTrabajo.setTipoOtObj(cmtTipoOtMgl);
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Se produjo un error al validarPestanaGeneral: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Se produjo un error al validarPestanaGeneral: " + ex.getMessage(), ex, LOGGER);
        }
        return validarPermisos(TAB_GENERAL, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaVT() {
        return validarPermisos(TAB_VT, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaAC() {
        return validarPermisos(TAB_ACO, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaGestion() {
        return validarPermisos(TAB_GESTION, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaTrack() {
        return validarPermisos(TAB_TRACK, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaNotas() {
        return validarPermisos(TAB_NOTAS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaRR() {
        return validarPermisos(TAB_RR, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTGeneral() {
        return validarPermisos(TAB_OT_GENERAL, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTAgenda() {
        return validarPermisos(TAB_OT_AGENDA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTNotas() {
        return validarPermisos(TAB_OT_NOTAS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTInventarios() {
        return validarPermisos(TAB_OT_INVENTARIOS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTContactos() {
        return validarPermisos(TAB_OT_CONTACTOS, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTBitacora() {
        return validarPermisos(TAB_OT_BITACORA, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTBacklog() {
        return validarPermisos(TAB_OT_BACKLOG, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaOTAgendamientos() {
        return validarPermisos(TAB_OT_AGENDAMIENTOS, ValidacionUtil.OPC_EDICION);
    }
    
      public boolean validarPestanaHistoricoAgendas() {
        return validarPermisos(TAB_HISTORICO_AGENDAS, ValidacionUtil.OPC_EDICION);
    }


    public boolean validarEdicionGeneral() {
        return validarPermisos(FORMULARIO_GENERAL, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarEdicionVT() {
        return validarPermisos(FORMULARIO_VT, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarCreacionNota() {
        return validarPermisos(FORMULARIO_NOTAS, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarAdicionComentario() {
        return validarPermisos(FORMULARIO_NOTAS_COMENTARIO, ValidacionUtil.OPC_CREACION);
    }

    public boolean validarPestanaGeneralOtEjecucion() {
        return validarPermisos(TAB_GENERAL_OT_EJECUCION, ValidacionUtil.OPC_EDICION);
    }

    public boolean validarPestanaNotasOtEjecucion() {

        return validarPermisos(TAB_NOTAS_OT_EJECUCION, ValidacionUtil.OPC_EDICION);
    }

    private boolean validarPermisos(String formulario, String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, accion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage() , e,LOGGER);            
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage() , e,LOGGER);            
            return false;
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de editar
     * @return  {@code boolean} true si tiene permisos de editar, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean validarEditarRol() {
        return validarEdicion(ROLOPCCREATEEDITAR);
    }

    /**
     * Metodo para validar si el usuario tiene permisos de editar
     * @param {@code String} formulario sobre el que se validara
     * @return  {@code boolean} true si tiene permisos de editar, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEdicion(String formulario){
        return validarPermisos(formulario, ValidacionUtil.OPC_EDICION);
    }

    /**
     * Metodo para validar si el usuario tiene permisos de crear
     * @return  {@code boolean} true si tiene permisos de crear, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCreacion(){
        return validarPermisos(TAB_VISITA_TECNICA_OT_CCMM, ValidacionUtil.OPC_CREACION);
    }

    /**
     * Metodo para validar si el usuario tiene permisos de crear
     * @param {@code String} formulario sobre el que se validara
     * @return  {@code boolean} true si tiene permisos de crear, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCreacion(String formulario){
        return validarPermisos(formulario, ValidacionUtil.OPC_CREACION);
    }


    /**
     * valbuenayf Metodo para consultar la auditoria de la OT
     */
    public void mostrarAuditoria() {
        if (ordenTrabajo != null) {
            try {
                informacionAuditoria = ordenTrabajoMglFacadeLocal.construirAuditoria(ordenTrabajo);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                FacesUtil.mostrarMensajeError("Error al mostarAuditoria. " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al mostarAuditoria. " + e.getMessage(), e, LOGGER);
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }

    public boolean validarReglasPestaniaAgenda() {

        boolean respuesta = false;

        try {
            if (ordenTrabajo != null && ordenTrabajo.getTipoOtObj() != null && ordenTrabajo.getTipoOtObj().getOtAgendable() != null) {
                if (!ordenTrabajo.getTipoOtObj().getOtAgendable().isEmpty() && ordenTrabajo.getTipoOtObj().getOtAgendable().equalsIgnoreCase("Y")) {
                    if (ordenTrabajo.getTipoOtObj().getTipoFlujoOt() != null
                            && ordenTrabajo.getEstadoInternoObj() != null && ordenTrabajo.getBasicaIdTecnologia() != null) {

                        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                                findByTipoFlujoAndEstadoInt(ordenTrabajo.getTipoOtObj().getTipoFlujoOt(),
                                        ordenTrabajo.getEstadoInternoObj(),
                                        ordenTrabajo.getBasicaIdTecnologia());

                        if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                            if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                                respuesta = true;
                            } else {
                                respuesta = false;
                            }
                        } else {
                            respuesta = false;
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al validarReglasPestaniaAgenda. " + e.getMessage() , e,LOGGER);  
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validarReglasPestaniaAgenda. " + e.getMessage() , e,LOGGER);  
        }
        return respuesta;
    }

    public void usuarioAprobo(CmtDetalleFlujoMgl detalleFlujoMgl) {
        try {
            if (detalleFlujoMgl.isApruebaCambioEstado()) {
                lstDetalleFlujoSolAprobacionCrear.add(detalleFlujoMgl);
            } else {
                lstDetalleFlujoSolAprobacionCrear.remove(detalleFlujoMgl);
            }
        } catch (RuntimeException ex) {
            FacesUtil.mostrarMensajeError("Error al validar usuario aprobador: " + ex.getMessage() , ex, LOGGER); 
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al validar usuario aprobador: " + ex.getMessage() , ex, LOGGER); 
        }
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<CmtEstadoxFlujoMgl> getEstadosFlujoList() {
        return estadosFlujoList;
    }

    public void setEstadosFlujoList(List<CmtEstadoxFlujoMgl> estadosFlujoList) {
        this.estadosFlujoList = estadosFlujoList;
    }

    public CmtEstadoIntxExtMgl getEstadoInternoExterno() {
        return estadoInternoExterno;
    }

    public void setEstadoInternoExterno(CmtEstadoIntxExtMgl estadoInternoExterno) {
        this.estadoInternoExterno = estadoInternoExterno;
    }

    public List<CmtBasicaMgl> getTipoSegmentoOtList() {
        return tipoSegmentoOtList;
    }

    public void setTipoSegmentoOtList(List<CmtBasicaMgl> tipoSegmentoOtList) {
        this.tipoSegmentoOtList = tipoSegmentoOtList;
    }

    public CmtNotaOtMgl getNotaOtMgl() {
        return notaOtMgl;
    }

    public void setNotaOtMgl(CmtNotaOtMgl notaOtMgl) {
        this.notaOtMgl = notaOtMgl;
    }

    public List<CmtNotaOtMgl> getNotaOtMglList() {
        return notaOtMglList;
    }

    public void setNotaOtMglList(List<CmtNotaOtMgl> notaOtMglList) {
        this.notaOtMglList = notaOtMglList;
    }

    public List<CmtBasicaMgl> getTipoNotaOtList() {
        return tipoNotaOtList;
    }

    public void setTipoNotaOtList(List<CmtBasicaMgl> tipoNotaOtList) {
        this.tipoNotaOtList = tipoNotaOtList;
    }

    public CmtBasicaMgl getTipoNotaOtSelected() {
        return tipoNotaOtSelected;
    }

    public void setTipoNotaOtSelected(CmtBasicaMgl tipoNotaOtSelected) {
        this.tipoNotaOtSelected = tipoNotaOtSelected;
    }

    public String getNotaOtComentarioStr() {
        return notaOtComentarioStr;
    }

    public void setNotaOtComentarioStr(String notaOtComentarioStr) {
        this.notaOtComentarioStr = notaOtComentarioStr;
    }

    public List<CmtDetalleFlujoMgl> getDetalleFlujoEstActual() {
        return detalleFlujoEstActual;
    }

    public void setDetalleFlujoEstActual(List<CmtDetalleFlujoMgl> detalleFlujoEstActual) {
        this.detalleFlujoEstActual = detalleFlujoEstActual;
    }

    public BigDecimal getEstadoOtId() {
        return estadoOtId;
    }

    public void setEstadoOtId(BigDecimal estadoOtId) {
        this.estadoOtId = estadoOtId;
    }

    public boolean isHabilitaFormVT() {
        return habilitaFormVT;
    }

    public void setHabilitaFormVT(boolean habilitaFormVT) {
        this.habilitaFormVT = habilitaFormVT;
    }

    public void initializedBean() {
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public BigDecimal getSubTipoTrabajoSelected() {
        return subTipoTrabajoSelected;
    }

    public void setSubTipoTrabajoSelected(BigDecimal subTipoTrabajoSelected) {
        this.subTipoTrabajoSelected = subTipoTrabajoSelected;
    }

    public List<CmtTipoOtMgl> getListTipoTrabajoOt() {
        return listTipoTrabajoOt;
    }

    public void setListTipoTrabajoOt(List<CmtTipoOtMgl> listTipoTrabajoOt) {
        this.listTipoTrabajoOt = listTipoTrabajoOt;
    }

    public BigDecimal getTipoTrabajoOtSelected() {
        return tipoTrabajoOtSelected;
    }

    public void setTipoTrabajoOtSelected(BigDecimal tipoTrabajoOtSelected) {
        this.tipoTrabajoOtSelected = tipoTrabajoOtSelected;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTipoOt() {
        return listTablaBasicaTipoOt;
    }

    public void setListTablaBasicaTipoOt(List<CmtBasicaMgl> listTablaBasicaTipoOt) {
        this.listTablaBasicaTipoOt = listTablaBasicaTipoOt;
    }

    public BigDecimal getBasicaTipoOtSelected() {
        return basicaTipoOtSelected;
    }

    public void setBasicaTipoOtSelected(BigDecimal basicaTipoOtSelected) {
        this.basicaTipoOtSelected = basicaTipoOtSelected;
    }

    public List<CmtTipoOtMgl> getSubTipoOrdenTrabajo() {
        return subTipoOrdenTrabajo;
    }

    public void setSubTipoOrdenTrabajo(List<CmtTipoOtMgl> subTipoOrdenTrabajo) {
        this.subTipoOrdenTrabajo = subTipoOrdenTrabajo;
    }

    public List<CmtBasicaMgl> getTipoGeneralOrdenTrabajo() {
        return tipoGeneralOrdenTrabajo;
    }

    public void setTipoGeneralOrdenTrabajo(List<CmtBasicaMgl> tipoGeneralOrdenTrabajo) {
        this.tipoGeneralOrdenTrabajo = tipoGeneralOrdenTrabajo;
    }

    public CmtBasicaMgl getBasicaTecnologiaOT() {
        return basicaTecnologiaOT;
    }

    public void setBasicaTecnologiaOT(CmtBasicaMgl basicaTecnologiaOT) {
        this.basicaTecnologiaOT = basicaTecnologiaOT;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public List<CmtBasicaMgl> getClaseOtList() {
        return claseOtList;
    }

    public void setClaseOtList(List<CmtBasicaMgl> claseOtList) {
        this.claseOtList = claseOtList;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public boolean isHabilitaFormAC() {
        return habilitaFormAC;
    }

    public void setHabilitaFormAC(boolean habilitaFormAC) {
        this.habilitaFormAC = habilitaFormAC;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajoRferencia() {
        return ordenTrabajoRferencia;
    }

    public void setOrdenTrabajoRferencia(CmtOrdenTrabajoMgl ordenTrabajoRferencia) {
        this.ordenTrabajoRferencia = ordenTrabajoRferencia;
    }

    public List<CmtDetalleFlujoMgl> getDetalleFlujoSolAprobacion() {
        return detalleFlujoSolAprobacion;
    }

    public void setDetalleFlujoSolAprobacion(List<CmtDetalleFlujoMgl> detalleFlujoSolAprobacion) {
        this.detalleFlujoSolAprobacion = detalleFlujoSolAprobacion;
    }

    public List<CmtSubEdificiosVt> getLstSubEdiVT() {
        return lstSubEdiVT;
    }

    public void setLstSubEdiVT(List<CmtSubEdificiosVt> lstSubEdiVT) {
        this.lstSubEdiVT = lstSubEdiVT;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<CmtArchivosVtMgl> getLstArchivosVtMgls() {
        return lstArchivosVtMgls;
    }

    public void setLstArchivosVtMgls(List<CmtArchivosVtMgl> lstArchivosVtMgls) {
        this.lstArchivosVtMgls = lstArchivosVtMgls;
    }

    public boolean isMostrarBotonGuardarComentario() {
        return mostrarBotonGuardarComentario;
    }

    public void setMostrarBotonGuardarComentario(boolean mostrarBotonGuardarComentario) {
        this.mostrarBotonGuardarComentario = mostrarBotonGuardarComentario;
    }

    public boolean isMostrarBotonGuardarNota() {
        return mostrarBotonGuardarNota;
    }

    public void setMostrarBotonGuardarNota(boolean mostrarBotonGuardarNota) {
        this.mostrarBotonGuardarNota = mostrarBotonGuardarNota;
    }

    public boolean isMostrarListaNotas() {
        return mostrarListaNotas;
    }

    public void setMostrarListaNotas(boolean mostrarListaNotas) {
        this.mostrarListaNotas = mostrarListaNotas;
    }

    public boolean isMostrarPanelCrearNotas() {
        return mostrarPanelCrearNotas;
    }

    public void setMostrarPanelCrearNotas(boolean mostrarPanelCrearNotas) {
        this.mostrarPanelCrearNotas = mostrarPanelCrearNotas;
    }

    public boolean isMostrarPanelListComentarios() {
        return mostrarPanelListComentarios;
    }

    public void setMostrarPanelListComentarios(boolean mostrarPanelListComentarios) {
        this.mostrarPanelListComentarios = mostrarPanelListComentarios;
    }

    public boolean isMostrarPanelCrearComentarios() {
        return mostrarPanelCrearComentarios;
    }

    public List<CmtNotaOtComentarioMgl> getLstCmtNotaOtComentarioMgls() {
        return lstCmtNotaOtComentarioMgls;
    }

    public void setLstCmtNotaOtComentarioMgls(List<CmtNotaOtComentarioMgl> lstCmtNotaOtComentarioMgls) {
        this.lstCmtNotaOtComentarioMgls = lstCmtNotaOtComentarioMgls;
    }

    public void setMostrarPanelCrearComentarios(boolean mostrarPanelCrearComentarios) {
        this.mostrarPanelCrearComentarios = mostrarPanelCrearComentarios;
    }

    public String getNumPaginaCap() {
        return numPaginaCap;
    }

    public void setNumPaginaCap(String numPaginaCap) {
        this.numPaginaCap = numPaginaCap;
    }

    public List<Integer> getPaginaListcap() {
        return paginaListcap;
    }

    public void setPaginaListcap(List<Integer> paginaListcap) {
        this.paginaListcap = paginaListcap;
    }

    public String getNumPaginaNot() {
        return numPaginaNot;
    }

    public void setNumPaginaNot(String numPaginaNot) {
        this.numPaginaNot = numPaginaNot;
    }

    public int getFilasPagNot() {
        return filasPagNot;
    }

    public void setFilasPagNot(int filasPagNot) {
        this.filasPagNot = filasPagNot;
    }

    public List<Integer> getPaginaListNot() {
        return paginaListNot;
    }

    public void setPaginaListNot(List<Integer> paginaListNot) {
        this.paginaListNot = paginaListNot;
    }

    public String getNumPaginaNotOtHij() {
        return numPaginaNotOtHij;
    }

    public void setNumPaginaNotOtHij(String numPaginaNotOtHij) {
        this.numPaginaNotOtHij = numPaginaNotOtHij;
    }

    public List<Integer> getPaginaListNotOtHij() {
        return paginaListNotOtHij;
    }

    public void setPaginaListNotOtHij(List<Integer> paginaListNotOtHij) {
        this.paginaListNotOtHij = paginaListNotOtHij;
    }

    public int getFilasPagNotOtHij() {
        return filasPagNotOtHij;
    }

    public void setFilasPagNotOtHij(int filasPagNotOtHij) {
        this.filasPagNotOtHij = filasPagNotOtHij;
    }
    
    

    public void searchSubTipoOrdenByTipoGeneralOt(CmtOrdenTrabajoMgl orden) {
        try {
            CmtTipoOtMglManager otMglManager = new CmtTipoOtMglManager();
            if (orden.getTipoOtObj() != null && orden.getTipoOtObj().getEsTipoVT().equalsIgnoreCase("N")) {
                subTipoOrdenTrabajo = otMglManager.findByBasicaId(orden.getBasicaIdTipoTrabajo());
            } else {
                subTipoOrdenTrabajo = otMglManager.findByTipoTrabajoAndIsVT(orden.getBasicaIdTipoTrabajo());
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al buscar Sub Tipo Orden por TipoGeneralOt (searchSubTipoOrdenByTipoGeneralOt). " + ex.getMessage() , ex, LOGGER);  
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al buscar Sub Tipo Orden por TipoGeneralOt (searchSubTipoOrdenByTipoGeneralOt). " + ex.getMessage() , ex, LOGGER);  
        }
    }

    public boolean validarEstadoFlujoOT() {
        try {
            if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
                CmtBasicaMgl tipoFlujo = ordenTrabajo.getTipoOtObj().getTipoFlujoOt();
                CmtBasicaMgl estadoInterno = ordenTrabajo.getEstadoInternoObj();
                CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
                return cmtEstadoxFlujoMglFacadeLocal.finalizoEstadosxFlujoDto(tipoFlujo, estadoInterno, tecnologia);
            } else {
                return false;
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al validarEstadoFlujoOT. " + ex.getMessage() , ex, LOGGER);
            return true;//TODO revisar si se devolveria false.
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al validarEstadoFlujoOT. " + ex.getMessage() , ex, LOGGER);
            return true;//TODO revisar si se devolveria false.
        }
    }

    public void searchListTiposTrabajo() throws ApplicationException  {
        try {
            detalleFlujoEstActual = new ArrayList<>();
            CmtTipoOtMglManager otMglManager = new CmtTipoOtMglManager();
            if (ordenTrabajo != null) {
                if (ordenTrabajo.getEstadoInternoObj() != null) {
                    ordenTrabajo.getEstadoInternoObj().setBasicaId(BigDecimal.ZERO);
                }
                if (ordenTrabajo.getTipoOtObj() != null) {
                    ordenTrabajo.getTipoOtObj().setIdTipoOt(BigDecimal.ZERO);
                }
            }
            
             List<CmtBasicaMgl> tipoGeneralOrdenTrabajoCompleta = basicaMglFacadeLocal.findByTipoBasicaTecno(ordenTrabajo.getBasicaIdTecnologia());
             
            tipoGeneralOrdenTrabajo = new ArrayList();
            if (ordenTrabajo != null && ordenTrabajo.getBasicaIdTecnologia() != null) {

                /*SE DESEA HALLAR SI EL TIPO DE TRABAJO TIENE SUBTIPO SINO NO SE MUESTRA EN PANTALLA*/
                if (tipoGeneralOrdenTrabajoCompleta != null && !tipoGeneralOrdenTrabajoCompleta.isEmpty()) {
                    for (CmtBasicaMgl cmtBasicaMgl : tipoGeneralOrdenTrabajoCompleta) {
                      List<CmtTipoOtMgl>  subTipoOrdenTrabajoTmp = otMglManager.findByTipoTrabajoAndTecno(cmtBasicaMgl, ordenTrabajo.getBasicaIdTecnologia());
                        if (subTipoOrdenTrabajoTmp != null & !subTipoOrdenTrabajoTmp.isEmpty()) {
                            tipoGeneralOrdenTrabajo.add(cmtBasicaMgl);
                        }
                    }

                }
            }
            
             if(tipoGeneralOrdenTrabajo!= null){
                  subTipoOrdenTrabajo = null;
             }
              
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno OT (searchListTiposTrabajo). " + ex.getMessage() , ex, LOGGER);
        }
    }

    public void searchListSubTiposTrabajo() throws ApplicationException  {
        try {
            detalleFlujoEstActual = new ArrayList<>();
            
            if(ordenTrabajo.getTipoOtObj()!=null){
                ordenTrabajo.getTipoOtObj().setIdTipoOt(BigDecimal.ZERO);
            }
            if (ordenTrabajo.getEstadoInternoObj()!=null){
                ordenTrabajo.getEstadoInternoObj().setBasicaId(BigDecimal.ZERO);
            }
            
            if (ordenTrabajo.getBasicaIdTipoTrabajo().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                CmtTipoOtMglManager otMglManager = new CmtTipoOtMglManager();
                subTipoOrdenTrabajo = otMglManager.findByTipoTrabajoAndTecno(ordenTrabajo.getBasicaIdTipoTrabajo(),ordenTrabajo.getBasicaIdTecnologia());
           
            }else{
                subTipoOrdenTrabajo = new ArrayList<>();
                detalleFlujoEstActual = new ArrayList<>();
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno OT (searchListSubTiposTrabajo). " + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno OT (searchListSubTiposTrabajo). " + ex.getMessage() , ex, LOGGER);
        }
    }
    
    public void searchListEstadoInternoOT() throws ApplicationException  {
        try {
            ordenTrabajo.getTipoOtObj().setIdTipoOt(getSubTipoTrabaoListSelect());
            if (ordenTrabajo.getBasicaIdTipoTrabajo().getBasicaId().compareTo(BigDecimal.ZERO) != 0
                    && ordenTrabajo.getBasicaIdTecnologia().getBasicaId().compareTo(BigDecimal.ZERO) != 0
                    && ordenTrabajo.getTipoOtObj().getIdTipoOt().compareTo(BigDecimal.ZERO) != 0) {
                CmtTipoOtMgl tipoOtMgl = tipoOtMglFacadeLocal.findById(ordenTrabajo.getTipoOtObj().getIdTipoOt());
                CmtBasicaMgl flujoTipoOtMgl = tipoOtMgl.getTipoFlujoOt();
                CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
                estadosFlujoList = estadoxFlujoMglFacadeLocal.getEstadosByTipoFlujo(flujoTipoOtMgl, tecnologia);
                searchEstadoInternoInicialOT();
            } else {
                estadosFlujoList = new ArrayList<>();
                ordenTrabajo.setEstadoInternoObj(new CmtBasicaMgl());
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno OT (searchListEstadoInternoOT). " + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno OT (searchListEstadoInternoOT). " + ex.getMessage() , ex, LOGGER);
        }
    }
    public void searchEstadoInternoInicialOT()throws ApplicationException  {
        try {
            CmtTipoOtMgl tipoOtMgl = tipoOtMglFacadeLocal.findById(ordenTrabajo.getTipoOtObj().getIdTipoOt());
            CmtBasicaMgl flujoTipoOtMgl = tipoOtMgl.getTipoFlujoOt();
            CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
            CmtEstadoxFlujoMgl estadoInicialFlujo = estadoxFlujoMglFacadeLocal.getEstadoInicialFlujo(flujoTipoOtMgl, tecnologia);
            ordenTrabajo.setEstadoInternoObj(estadoInicialFlujo.getEstadoInternoObj());
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno Inicial OT (searchEstadoInternoInicialOT). " + ex.getMessage(), ex, LOGGER);
            ordenTrabajo.setEstadoInternoObj(null);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al buscar la lista de  Estado Interno Inicial OT (searchEstadoInternoInicialOT). " + ex.getMessage(), ex, LOGGER);
            ordenTrabajo.setEstadoInternoObj(null);
        }
    }

    public String ingresarGestionCm() throws ApplicationException {
        CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
        if (ordenTrabajo != null) {
            cuentaMatrizBean.setCuentaMatriz(ordenTrabajo.getCmObj());            
        }
        ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
        if (ordenTrabajo != null && ordenTrabajo.getIdOt() != null) {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            ordenTrabajo = ordenTrabajoMglFacadeLocal.bloquearDesbloquearOrden(ordenTrabajo, false);
        }
        consultaCuentasMatricesBean.goCuentaMatriz();
        return null;
    }

    public String volverListaOtAprobadas() {
                   
        try {
            FacesUtil.navegarAPagina("/view/MGL/CM/ot/otEjecucionGestionar.jsf");
            
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al volverListaOtAprobadas. " + ex.getMessage() , ex, LOGGER);
        }  catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al volverListaOtAprobadas. " + ex.getMessage() , ex, LOGGER);
        }
        return null;
    }
    
     public String volverListaOrdenes() {

        try {
            ordenTrabajo = ordenTrabajoMglFacadeLocal.bloquearDesbloquearOrden(ordenTrabajo, false);
            FacesUtil.navegarAPagina("/view/MGL/CM/ot/otGestionar.jsf");

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al volverListaOrdenes. " + ex.getMessage() , ex, LOGGER);
        }  catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al volverListaOrdenes. " + ex.getMessage() , ex, LOGGER);
        }
        return null;
    }

    public void crearAcoForSub(CmtSubEdificiosVt subEdificiosVt) {
        try {
            if (subEdificiosVt.isGenAco()) {
                subEdificiosVt.setGeneraAcometida("Y");
            } else {
                subEdificiosVt.setGeneraAcometida("N");
            }
            subEdificiosVtFacadeLocal.update(subEdificiosVt,usuarioVT,perfilVT);

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al crearAcoForSub. " + ex.getMessage() , ex, LOGGER);
        }  catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al crearAcoForSub. " + ex.getMessage() , ex, LOGGER);
        }
    }

    public void cargarArchivo() {

        String usuario = usuarioVT;
        boolean responseAux;
        if (uploadedFile != null && uploadedFile.getFileName() != null) {

            try {
                responseAux = ordenTrabajoMglFacadeLocal.
                        cargarArchivoCierreComercial(ordenTrabajo, uploadedFile, usuario,
                                perfilVT, Constant.ORIGEN_CARGA_ARCHIVO_CIERRE_OT);

                if (responseAux) {
                    String msg = "Archivo cargado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    msg, ""));

                    lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findFilesVTByIdOt(ordenTrabajo);
                    lstArchivosAcoMgls = cmtArchivosVtMglFacadeLocal.findFilesByIdOt(ordenTrabajo);
                } else {
                    String msg = "Ocurrio un error al guardar el archivo";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                }

            } catch (ApplicationException | FileNotFoundException | MalformedURLException 
                    | SearchCuentasMatricesFault | UploadCuentasMatricesFault e) {
                FacesUtil.mostrarMensajeError("Error al cargarArchivo. " + e.getMessage() , e, LOGGER);
            }

        } else {
            String msg = "Debe seleccionar un archivo para guardar";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));

        }

    }

    public String crearOtAcometida() {
        try {
            ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);

            CmtOrdenTrabajoMgl ordenTrabajoAcometidaMgl = new CmtOrdenTrabajoMgl();
            ordenTrabajoAcometidaMgl.setBasicaIdTecnologia(ordenTrabajo.getBasicaIdTecnologia());
            ordenTrabajoAcometidaMgl.setSegmento(ordenTrabajo.getSegmento());

            CmtEstadoxFlujoMgl estadoInicialFlujo = null;
            CmtTipoOtMgl tipoOtMglAco= null;
            
            if (ordenTrabajo.getTipoOtObj() != null && ordenTrabajo.getTipoOtObj().getOtAcoAGenerar() != null) {
                //Busca el tipo de trabajo de acometida
                tipoOtMglAco = tipoOtMglFacadeLocal.findById(ordenTrabajo.getTipoOtObj().getOtAcoAGenerar());
            } 
            
            if (tipoOtMglAco != null && tipoOtMglAco.getIdTipoOt() != null) {

                CmtBasicaMgl flujoTipoOtMgl = tipoOtMglAco.getTipoFlujoOt();
                CmtBasicaMgl tecnologia = ordenTrabajo.getBasicaIdTecnologia();
                estadoInicialFlujo = estadoxFlujoMglFacadeLocal.getEstadoInicialFlujo(flujoTipoOtMgl, tecnologia);
                ordenTrabajoAcometidaMgl.setEstadoInternoObj(estadoInicialFlujo.getEstadoInternoObj());
                ordenTrabajoAcometidaMgl.setTipoOtObj(tipoOtMglAco);
                ordenTrabajoAcometidaMgl.setBasicaIdTipoTrabajo(tipoOtMglAco.getBasicaIdTipoOt());
                ordenTrabajoAcometidaMgl.setObservacion("Creacion orden de trabajo de acometida");
                ordenTrabajoAcometidaMgl.setCmObj(ordenTrabajo.getCmObj());
            }

            ordenTrabajoAcometidaMgl.setOrdenTrabajoReferencia(ordenTrabajo.getIdOt());
            ordenTrabajoAcometidaMgl = ordenTrabajoMglFacadeLocal.crearOt(ordenTrabajoAcometidaMgl);

            if (ordenTrabajoAcometidaMgl.getIdOt() != null) {

                //Actualizamos los subedificios Vt
                for (CmtSubEdificiosVt subVt : lstSubEdiVT) {
                    if (subVt.getGeneraAcometida() != null) {
                        if (subVt.getGeneraAcometida().equalsIgnoreCase("Y")) {
                            subVt.setOtAcometidaId(ordenTrabajoAcometidaMgl.getIdOt());
                            subVt = subEdificiosVtFacadeLocal.update(subVt,usuarioVT,perfilVT);
                            if (subVt.getOtAcometidaId() != null) {
                                LOGGER.info("Se actualiza el subedificio VT con la acometida generada");
                            } else {
                                LOGGER.error("Ocurrio un error al actualizar el subedificio Vt");
                            }
                        }
                    }

                }
                if (estadoInicialFlujo != null) {

                    ordenTrabajoMglFacadeLocal.changeStatusCmByFlowAco(ordenTrabajoAcometidaMgl, usuarioVT, perfilVT);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            ConstantsCM.MSN_PROCESO_EXITOSO + ", Se ha creado la Orden de trabajo de Acometida: <b>"
                            + ordenTrabajoAcometidaMgl.getIdOt().toString() + "</b>", ""));
                }

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al crearOtAcometida. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al crearOtAcometida. " + e.getMessage(), e, LOGGER);
        }
        return cambiarTab("GENERALOTEJECUCION");
    }

    public void eliminarArchivo(CmtArchivosVtMgl archivosVtMgl) {
        try {
            boolean elimina = cmtArchivosVtMglFacadeLocal.delete(archivosVtMgl, usuarioVT, perfilVT);
            if (elimina) {
                String msg = "Se elimino el registro " + archivosVtMgl.getIdArchivosVt() + " de la base de datos";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                msg, ""));
                LOGGER.error(msg);
                lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findFilesVTByIdOt(ordenTrabajo);
            lstArchivosAcoMgls = cmtArchivosVtMglFacadeLocal.findFilesByIdOt(ordenTrabajo);
            } else {
                String msg = "Ocurrio un problema al tratar de eliminar el registro ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
                LOGGER.error(msg);
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al eliminarArchivo. " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al eliminarArchivo. " + ex.getMessage(), ex, LOGGER);
        }

    }

    public String nuevaNotaOt() {

        try {
            if (ordenTrabajo.getIdOt() != null) {
                mostrarListaNotas = false;
                mostrarPanelListComentarios = false;
                mostrarPanelCrearComentarios = false;
                notaOtMgl = new CmtNotaOtMgl();
                tipoNotaOtSelected = new CmtBasicaMgl();
                mostrarPanelCrearNotas = true;
                agendasList = null;

                //Consulto agendas para actualizar notas
                CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                        findByTipoFlujoAndEstadoInt(ordenTrabajo.getTipoOtObj().getTipoFlujoOt(),
                                ordenTrabajo.getEstadoInternoObj(),
                                ordenTrabajo.getBasicaIdTecnologia());

                String subTipoWorForce = "";

                if (cmtEstadoxFlujoMgl != null) {
                    if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                        subTipoWorForce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                    }
                }
                //Consultamos si la orden tiene agendas pendientes
                agendasListConsulta = cmtAgendamientoMglFacadeLocal.
                        agendasPendientesByOrdenAndSubTipoWorkfoce(ordenTrabajo, subTipoWorForce);

            } else {
                String msg = "Se requiere crear una Ot previamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msg, ""));
            }

            return "";
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en nuevaNotaOt. " + ex.getMessage(), ex, LOGGER);
        }
        return null;
    }

    public String volverListNotaOt() {

        mostrarListaNotas = true;
        mostrarPanelCrearNotas = false;
        mostrarPanelListComentarios = false;

        return "";
    }

    public String verComentariosList(CmtNotaOtMgl notaOt) {

        try {
            lstCmtNotaOtComentarioMglsAux = new ArrayList<>();
            mostrarListaNotas = false;
            mostrarPanelCrearNotas = false;
            mostrarPanelCrearComentarios = false;
            mostrarPanelListComentarios = true;
            notaOtMgl = notaOt;
            lstCmtNotaOtComentarioMglsAux = comentarioNotaOtMglFacadeLocal.findByNotaOt(notaOtMgl);
            
            listInfoByPageCap(1);
            return "";
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en verComentariosList. " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en verComentariosList. " + ex.getMessage(), ex, LOGGER);
        }
        return "";

    }

    public String volverListNotas() {

        mostrarListaNotas = true;
        mostrarPanelCrearNotas = false;
        mostrarPanelListComentarios = false;

        return "";

    }

    public String volverListComentarios() {

        mostrarListaNotas = false;
        mostrarPanelCrearNotas = false;
        mostrarPanelCrearComentarios = false;
        mostrarPanelListComentarios = true;

        return "";
    }

    public void consultaInicialOtOnyx() throws ApplicationException {
        
        List<OnyxOtCmDir> listaOtOnyx = onyxOtCmDirlFacadeLocal.findOnyxOtCmDirById(ordenTrabajo.getIdOt());
        String id;
        BigDecimal idOtOnyxMgl = null;
        if(listaOtOnyx != null && !listaOtOnyx.isEmpty()){
             id = listaOtOnyx.get(0).getOnyx_Ot_Hija_Cm();
             idOtOnyxMgl = new BigDecimal(id);
        }
		
        limpiarCamposOnyx();
        try {
            if (idOtOnyxMgl != null) {
                //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio

                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(idOtOnyxMgl.toString());

                if (cmtOnyxResponseDto != null) {
                    setOnyx(cmtOnyxResponseDto);
                    OnyxComplejidad = listaOtOnyx != null ? listaOtOnyx.get(0).getComplejidadServicio() : "";
                    listInfoByPageNotOtHija(1);
                } else {
                    String msnError = "OT con ID " + idOtOnyxMgl.toString() + " no encontrada en Onyx "
                            + "o no se encuentra disponible el servicio de consulta.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msnError, ""));
                    limpiarCamposOnyx();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("OT con ID " + numeroOtHija + " no encontrada en Onyx.", e, LOGGER);
            limpiarCamposOnyx();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al consultaInicialOtOnyx con ID " + numeroOtHija + " en Onyx: " + e.getMessage(), e, LOGGER);
            limpiarCamposOnyx();
        }
    }

    public void consultarOTOnyx() throws ApplicationException {
        List<OnyxOtCmDir> onyxOtCmBD;
        ordenTrabajoMglFacadeLocal.setUser(usuarioVT, perfilVT);        
        
        if (!numeroOtHija.toString().isEmpty()) {
            try {
                 //Validamos disponibilidad del servicio
                ParametrosMgl wsdlOtHija
                        = parametrosMglFacadeLocal.findParametroMgl(Constant.WSDL_OT_HIJA_ONIX);

                if (wsdlOtHija == null) {
                    throw new ApplicationException("No se encuentra configurado el parámetro " + Constant.WSDL_OT_HIJA_ONIX);
                }

                URL urlCon = new URL(wsdlOtHija.getParValor());

                ConsumoGenerico.conectionWsdlTest(urlCon, Constant.WSDL_OT_HIJA_ONIX);
                //Fin Validacion disponibilidad del servicio
                
                cmtOnyxResponseDto = ordenTrabajoMglFacadeLocal.consultarOTHijaOnyx(numeroOtHija.toString());
                
                if (cmtOnyxResponseDto != null) {
                    setOnyx(cmtOnyxResponseDto);
                    // persistencia de los campos Onyx en BD
                    onyxOtCmDir = setFieldsOnyxToEntity(cmtOnyxResponseDto);
                    // se valida si la ot ya esta almacenada en la tabla Onyx
                    onyxOtCmBD = onyxOtCmDirlFacadeLocal.findOnyxOtCmDirById(ordenTrabajo.getIdOt());
                    if (onyxOtCmBD != null && !onyxOtCmBD.isEmpty()) {
                        for (OnyxOtCmDir onyxOtCm : onyxOtCmBD) {
                            onyxOtCm.setEstadoRegistro(0);
                            onyxOtCmDirlFacadeLocal.update(onyxOtCm);
                        }
                        onyxOtCmDirlFacadeLocal.create(onyxOtCmDir);
                    }else{
                         onyxOtCmDirlFacadeLocal.create(onyxOtCmDir);
                    }
                    
                    try {
                        MERNotifyResponseType responseType = ordenTrabajoMglFacadeLocal.notificarOrdenOnix(ordenTrabajo, numeroOtHija.toString());
                        if (responseType != null && responseType.getIcodOperacion().equalsIgnoreCase("1")) {
                            String msnError = "Operacion Exitosa: " + responseType.getVchDescOperacion() + "";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            msnError, ""));
                        }
                    } catch (ApplicationException e) {
                        String msnError = "No fue posible realizar la notificación "
                                + "de la orden a Onyx por disponibilidad en el servicio de notificación:"+e.getMessage()+"";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                    }

                    listInfoByPageNotOtHija(1); 
                } else {
                    String msnError = "OT con ID " + numeroOtHija + " no encontrada en Onyx.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msnError, ""));
                    limpiarCamposOnyx();
                }

            } catch (ApplicationException |IOException |ParseException e) {
                FacesUtil.mostrarMensajeError("Error al consultarOTOnyx OT con ID " + numeroOtHija + ""
                        + " no encontrada en Onyx: " + e.getMessage(), e, LOGGER);                
                limpiarCamposOnyx();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar"
                            + " Id de incidente OT Onyx", "Incidente Id OT Onyx"));
        }
    }

    public void setOnyx(CmtOnyxResponseDto cmtOnyxResponseDto) {

        if (cmtOnyxResponseDto != null) {

            OnyxNitCliente = cmtOnyxResponseDto.getNIT_Cliente();
            OnyxNombreCliente = cmtOnyxResponseDto.getNombre();
            OnyxNombreOtHija = cmtOnyxResponseDto.getNombre_OT_Hija();
            OnyxNumeroOtPadre = String.valueOf(cmtOnyxResponseDto.getOTP());
            OnyxNumeroOtHija = String.valueOf(cmtOnyxResponseDto.getOTH());
            OnyxFechaCreOtHija = cmtOnyxResponseDto.getFechaCreacionOTH();
            OnyxFechaCreOtPadre = cmtOnyxResponseDto.getFechaCreacionOTP();
            OnyxContactoTecOtPadre = cmtOnyxResponseDto.getContactoTecnicoOTP();
            OnyxTelContactoTecOtPadre = cmtOnyxResponseDto.getTelefonoContacto();
            OnyxDescripcion = cmtOnyxResponseDto.getDescripcion();
            OnyxDireccion = cmtOnyxResponseDto.getDireccion();
            OnyxSegmento = cmtOnyxResponseDto.getSegmento();
            OnyxTipoServicio = cmtOnyxResponseDto.getTipoServicio();
            OnyxServicios = cmtOnyxResponseDto.getServicios();
            OnyxRecurrenteMensual = cmtOnyxResponseDto.getRecurrenteMensual().toString();
            OnyxCodigoServicio = cmtOnyxResponseDto.getCodigoServicio();
            OnyxVendedor = cmtOnyxResponseDto.getVendedor();
            OnyxTelefono = cmtOnyxResponseDto.getTelefono();
            OnyxNotasOtHija = cmtOnyxResponseDto.getNotasOTH();
            cargarListaNotas(OnyxNotasOtHija);
            OnyxEstadoOtHija = cmtOnyxResponseDto.getEstadoOTH();
            OnyxEstadoOtPadre = cmtOnyxResponseDto.getEstadoOTP();
            OnyxFechaCompromisoOtPadre = cmtOnyxResponseDto.getFechaCompromisoOTP();
            OnyxCodResolOtPadre1 = cmtOnyxResponseDto.getCodResolucion1OTP();
            OnyxCodResolOtPadre2 = cmtOnyxResponseDto.getCodResolucion2OTP();
            OnyxCodResolOtPadre3 = cmtOnyxResponseDto.getCodResolucion3OTP();
            OnyxCodResolOtPadre4 = cmtOnyxResponseDto.getCodResolucion4OTP();
            OnyxCodResolOtHija1 = cmtOnyxResponseDto.getCodResolucion1OTH();
            OnyxCodResolOtHija2 = cmtOnyxResponseDto.getCodResolucion2OTH();
            OnyxCodResolOtHija3 = cmtOnyxResponseDto.getCodResolucion3OTH();
            OnyxCodResolOtHija4 = cmtOnyxResponseDto.getCodResolucion4OTH();
            numeroOtHija = new BigDecimal(OnyxNumeroOtHija);
        }
    }

    public void limpiarCamposOnyx() {

        OnyxComplejidad = "";
        OnyxNitCliente = "";
        OnyxNombreCliente = "";
        OnyxNombreOtHija = "";
        OnyxNumeroOtPadre = "";
        OnyxNumeroOtHija = "";
        OnyxFechaCreOtHija = "";
        OnyxFechaCreOtPadre = "";
        OnyxContactoTecOtPadre = "";
        OnyxTelContactoTecOtPadre = "";
        OnyxDescripcion = "";
        OnyxDireccion = "";
        OnyxSegmento = "";
        OnyxTipoServicio = "";
        OnyxServicios = "";
        OnyxRecurrenteMensual = "";
        OnyxCodigoServicio = "";
        OnyxVendedor = "";
        OnyxTelefono = "";
        OnyxNotasOtHija = "";
        OnyxEstadoOtHija = "";
        OnyxEstadoOtPadre = "";
        OnyxFechaCompromisoOtPadre = "";
        OnyxCodResolOtPadre1 = "";
        OnyxCodResolOtPadre2 = "";
        OnyxCodResolOtPadre3 = "";
        OnyxCodResolOtPadre4 = "";
        OnyxCodResolOtHija1 = "";
        OnyxCodResolOtHija2 = "";
        OnyxCodResolOtHija3 = "";
        OnyxCodResolOtHija4 = "";
        numeroOtHija = null;
        lstCmtNotasOtHijaDto= new ArrayList<>();

    }
    
        public OnyxOtCmDir setFieldsOnyxToEntity(CmtOnyxResponseDto cmtOnyxResponseDto) throws ParseException{
        if (cmtOnyxResponseDto != null) {
            onyxOtCmDir = new OnyxOtCmDir();
            onyxOtCmDir.setOt_Id_Cm(ordenTrabajo);
            onyxOtCmDir.setOnyx_Ot_Hija_Cm(String.valueOf(cmtOnyxResponseDto.getOTH()));
            onyxOtCmDir.setOnyx_Ot_Hija_Dir(null);
            onyxOtCmDir.setOnyx_Ot_Padre_Cm(String.valueOf(cmtOnyxResponseDto.getOTP()));
            onyxOtCmDir.setOnyx_Ot_Padre_Dir(null);
            onyxOtCmDir.setNit_Cliente_Onyx(cmtOnyxResponseDto.getNIT_Cliente());
            onyxOtCmDir.setNombre_Cliente_Onyx(cmtOnyxResponseDto.getNombre());
            onyxOtCmDir.setNombre_Ot_Hija_Onyx(cmtOnyxResponseDto.getNombre_OT_Hija());
           if (cmtOnyxResponseDto.getFechaCreacionOTH() != null && !cmtOnyxResponseDto.getFechaCreacionOTH().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTH());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Hija_Onyx(null);
            }

             if (cmtOnyxResponseDto.getFechaCreacionOTP() != null && !cmtOnyxResponseDto.getFechaCreacionOTP().isEmpty()) {
                SimpleDateFormat formatterFecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_Creacion_Ot_Hija_Onyx = formatterFecha_Creacion_Ot_Hija_Onyx.parse(cmtOnyxResponseDto.getFechaCreacionOTP());
                DateFormat  formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(dateCreate);
            } else {
                onyxOtCmDir.setFecha_Creacion_Ot_Padre_Onyx(null);
            }
            onyxOtCmDir.setContacto_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getContactoTecnicoOTP());
            onyxOtCmDir.setTelefono_Tecnico_Ot_Padre_Onyx(cmtOnyxResponseDto.getTelefonoContacto());
            onyxOtCmDir.setDescripcion_Onyx(cmtOnyxResponseDto.getDescripcion());
            onyxOtCmDir.setDireccion_Onyx(cmtOnyxResponseDto.getDireccion());
            
            onyxOtCmDir.setSegmento_Onyx(cmtOnyxResponseDto.getSegmento());
            onyxOtCmDir.setTipo_Servicio_Onyx(StringUtils.stripAccents(cmtOnyxResponseDto.getTipoServicio()));
            onyxOtCmDir.setRecurrente_Mensual_Onyx(cmtOnyxResponseDto.getRecurrenteMensual().toString());
            onyxOtCmDir.setCodigo_Servicio_Onyx(cmtOnyxResponseDto.getCodigoServicio());
            
            onyxOtCmDir.setVendedor_Onyx(cmtOnyxResponseDto.getVendedor());
            onyxOtCmDir.setTelefono_Vendedor_Onyx(cmtOnyxResponseDto.getTelefono());
            onyxOtCmDir.setServicios_Onyx(cmtOnyxResponseDto.getServicios());
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Cm(cmtOnyxResponseDto.getEstadoOTH());
            
            
            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Cm(cmtOnyxResponseDto.getEstadoOTP());
            onyxOtCmDir.setEstado_Ot_Hija_Onyx_Dir(null);
            onyxOtCmDir.setEstado_Ot_Padre_Onyx_Dir(null);
              if (cmtOnyxResponseDto.getFechaCompromisoOTP() != null 
                      && !cmtOnyxResponseDto.getFechaCompromisoOTP().isEmpty()) {
                try {
                    SimpleDateFormat inputFormatFechaComp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date fecha_Creacion_Ot_Hija_Onyx = inputFormatFechaComp.parse(cmtOnyxResponseDto.getFechaCompromisoOTP());
                    DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date dateCreate = formato.parse(formato.format(fecha_Creacion_Ot_Hija_Onyx));
                    onyxOtCmDir.setFecha_Compromiso_Ot_Padre_Onyx(dateCreate);
                } catch (ParseException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }

            }
            onyxOtCmDir.setOt_Padre_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTP());
            onyxOtCmDir.setOt_Padre_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTP());
            onyxOtCmDir.setOt_Hija_Resolucion_1_Onyx(cmtOnyxResponseDto.getCodResolucion1OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_2_Onyx(cmtOnyxResponseDto.getCodResolucion2OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_3_Onyx(cmtOnyxResponseDto.getCodResolucion3OTH());
            onyxOtCmDir.setOt_Hija_Resolucion_4_Onyx(cmtOnyxResponseDto.getCodResolucion4OTH());
            
            onyxOtCmDir.setEstadoRegistro(1);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date dateCreate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaCreacion(dateCreate);
            Date dateUpdate = formato.parse(formato.format(new Date()));
            onyxOtCmDir.setFechaEdicion(dateUpdate);
            onyxOtCmDir.setUsuarioCreacion(usuarioVT);
            onyxOtCmDir.setUsuarioEdicion(usuarioVT);
            
            onyxOtCmDir.setCellTec(cmtOnyxResponseDto.getCellTec());
            onyxOtCmDir.setEmailTec(cmtOnyxResponseDto.getEmailCTec());
            onyxOtCmDir.setCodigoProyecto(cmtOnyxResponseDto.getCodProyecto());
            onyxOtCmDir.setRegionalOrigen(cmtOnyxResponseDto.getRegionalDestino());
            onyxOtCmDir.setaImplement(cmtOnyxResponseDto.getaImplement());
            onyxOtCmDir.setCiudadFact(cmtOnyxResponseDto.getCiudadFacturacion());
            onyxOtCmDir.setComplejidadServicio(OnyxComplejidad);
           
         }
         return onyxOtCmDir;
    }

    public BigDecimal getNumeroOtHija() {
        return numeroOtHija;
    }

    public void setNumeroOtHija(BigDecimal numeroOtHija) {
        this.numeroOtHija = numeroOtHija;
    }

    public String getOnyxNitCliente() {
        return OnyxNitCliente;
    }

    public void setOnyxNitCliente(String OnyxNitCliente) {
        this.OnyxNitCliente = OnyxNitCliente;
    }

    public String getOnyxNombreCliente() {
        return OnyxNombreCliente;
    }

    public void setOnyxNombreCliente(String OnyxNombreCliente) {
        this.OnyxNombreCliente = OnyxNombreCliente;
    }

    public String getOnyxNombreOtHija() {
        return OnyxNombreOtHija;
    }

    public void setOnyxNombreOtHija(String OnyxNombreOtHija) {
        this.OnyxNombreOtHija = OnyxNombreOtHija;
    }

    public String getOnyxNumeroOtPadre() {
        return OnyxNumeroOtPadre;
    }

    public void setOnyxNumeroOtPadre(String OnyxNumeroOtPadre) {
        this.OnyxNumeroOtPadre = OnyxNumeroOtPadre;
    }

    public String getOnyxNumeroOtHija() {
        return OnyxNumeroOtHija;
    }

    public void setOnyxNumeroOtHija(String OnyxNumeroOtHija) {
        this.OnyxNumeroOtHija = OnyxNumeroOtHija;
    }

    public String getOnyxFechaCreOtHija() {
        return OnyxFechaCreOtHija;
    }

    public void setOnyxFechaCreOtHija(String OnyxFechaCreOtHija) {
        this.OnyxFechaCreOtHija = OnyxFechaCreOtHija;
    }

    public String getOnyxFechaCreOtPadre() {
        return OnyxFechaCreOtPadre;
    }

    public void setOnyxFechaCreOtPadre(String OnyxFechaCreOtPadre) {
        this.OnyxFechaCreOtPadre = OnyxFechaCreOtPadre;
    }

    public String getOnyxContactoTecOtPadre() {
        return OnyxContactoTecOtPadre;
    }

    public void setOnyxContactoTecOtPadre(String OnyxContactoTecOtPadre) {
        this.OnyxContactoTecOtPadre = OnyxContactoTecOtPadre;
    }

    public String getOnyxTelContactoTecOtPadre() {
        return OnyxTelContactoTecOtPadre;
    }

    public void setOnyxTelContactoTecOtPadre(String OnyxTelContactoTecOtPadre) {
        this.OnyxTelContactoTecOtPadre = OnyxTelContactoTecOtPadre;
    }

    public String getOnyxDescripcion() {
        return OnyxDescripcion;
    }

    public void setOnyxDescripcion(String OnyxDescripcion) {
        this.OnyxDescripcion = OnyxDescripcion;
    }

    public String getOnyxDireccion() {
        return OnyxDireccion;
    }

    public void setOnyxDireccion(String OnyxDireccion) {
        this.OnyxDireccion = OnyxDireccion;
    }

    public String getOnyxSegmento() {
        return OnyxSegmento;
    }

    public void setOnyxSegmento(String OnyxSegmento) {
        this.OnyxSegmento = OnyxSegmento;
    }

    public String getOnyxTipoServicio() {
        return OnyxTipoServicio;
    }

    public void setOnyxTipoServicio(String OnyxTipoServicio) {
        this.OnyxTipoServicio = OnyxTipoServicio;
    }

    public String getOnyxServicios() {
        return OnyxServicios;
    }

    public void setOnyxServicios(String OnyxServicios) {
        this.OnyxServicios = OnyxServicios;
    }

    public String getOnyxRecurrenteMensual() {
        return OnyxRecurrenteMensual;
    }

    public void setOnyxRecurrenteMensual(String OnyxRecurrenteMensual) {
        this.OnyxRecurrenteMensual = OnyxRecurrenteMensual;
    }

    public String getOnyxCodigoServicio() {
        return OnyxCodigoServicio;
    }

    public void setOnyxCodigoServicio(String OnyxCodigoServicio) {
        this.OnyxCodigoServicio = OnyxCodigoServicio;
    }

    public String getOnyxVendedor() {
        return OnyxVendedor;
    }

    public void setOnyxVendedor(String OnyxVendedor) {
        this.OnyxVendedor = OnyxVendedor;
    }

    public String getOnyxTelefono() {
        return OnyxTelefono;
    }

    public void setOnyxTelefono(String OnyxTelefono) {
        this.OnyxTelefono = OnyxTelefono;
    }

    public String getOnyxNotasOtHija() {
        return OnyxNotasOtHija;
    }

    public void setOnyxNotasOtHija(String OnyxNotasOtHija) {
        this.OnyxNotasOtHija = OnyxNotasOtHija;
    }

    public String getOnyxEstadoOtHija() {
        return OnyxEstadoOtHija;
    }

    public void setOnyxEstadoOtHija(String OnyxEstadoOtHija) {
        this.OnyxEstadoOtHija = OnyxEstadoOtHija;
    }

    public String getOnyxEstadoOtPadre() {
        return OnyxEstadoOtPadre;
    }

    public void setOnyxEstadoOtPadre(String OnyxEstadoOtPadre) {
        this.OnyxEstadoOtPadre = OnyxEstadoOtPadre;
    }

    public String getOnyxFechaCompromisoOtPadre() {
        return OnyxFechaCompromisoOtPadre;
    }

    public void setOnyxFechaCompromisoOtPadre(String OnyxFechaCompromisoOtPadre) {
        this.OnyxFechaCompromisoOtPadre = OnyxFechaCompromisoOtPadre;
    }

    public String getOnyxCodResolOtPadre1() {
        return OnyxCodResolOtPadre1;
    }

    public void setOnyxCodResolOtPadre1(String OnyxCodResolOtPadre1) {
        this.OnyxCodResolOtPadre1 = OnyxCodResolOtPadre1;
    }

    public String getOnyxCodResolOtPadre2() {
        return OnyxCodResolOtPadre2;
    }

    public void setOnyxCodResolOtPadre2(String OnyxCodResolOtPadre2) {
        this.OnyxCodResolOtPadre2 = OnyxCodResolOtPadre2;
    }

    public String getOnyxCodResolOtPadre3() {
        return OnyxCodResolOtPadre3;
    }

    public void setOnyxCodResolOtPadre3(String OnyxCodResolOtPadre3) {
        this.OnyxCodResolOtPadre3 = OnyxCodResolOtPadre3;
    }

    public String getOnyxCodResolOtPadre4() {
        return OnyxCodResolOtPadre4;
    }

    public void setOnyxCodResolOtPadre4(String OnyxCodResolOtPadre4) {
        this.OnyxCodResolOtPadre4 = OnyxCodResolOtPadre4;
    }

    public String getOnyxCodResolOtHija1() {
        return OnyxCodResolOtHija1;
    }

    public void setOnyxCodResolOtHija1(String OnyxCodResolOtHija1) {
        this.OnyxCodResolOtHija1 = OnyxCodResolOtHija1;
    }

    public String getOnyxCodResolOtHija2() {
        return OnyxCodResolOtHija2;
    }

    public void setOnyxCodResolOtHija2(String OnyxCodResolOtHija2) {
        this.OnyxCodResolOtHija2 = OnyxCodResolOtHija2;
    }

    public String getOnyxCodResolOtHija3() {
        return OnyxCodResolOtHija3;
    }

    public void setOnyxCodResolOtHija3(String OnyxCodResolOtHija3) {
        this.OnyxCodResolOtHija3 = OnyxCodResolOtHija3;
    }

    public String getOnyxCodResolOtHija4() {
        return OnyxCodResolOtHija4;
    }

    public void setOnyxCodResolOtHija4(String OnyxCodResolOtHija4) {
        this.OnyxCodResolOtHija4 = OnyxCodResolOtHija4;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public boolean isIsRequestGral() {
        return isRequestGral;
    }

    public void setIsRequestGral(boolean isRequestGral) {
        this.isRequestGral = isRequestGral;
    }

    //////////////Paginado Comentarios Ot//////////////
    public void listInfoByPageCap(int page) throws ApplicationException {
        try {
            actualCap = page;
            getTotalPaginasCap();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
            lstCmtNotaOtComentarioMgls = new ArrayList<>();

            //Obtenemos el rango de la paginación
            if (lstCmtNotaOtComentarioMglsAux.size() > 0) {

                int maxResult;
                if ((firstResult + filasPag) > lstCmtNotaOtComentarioMglsAux.size()) {
                    maxResult = lstCmtNotaOtComentarioMglsAux.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }

                for (int k = firstResult; k < maxResult; k++) {
                    lstCmtNotaOtComentarioMgls.add(lstCmtNotaOtComentarioMglsAux.get(k));
                }
            }

        } catch (NumberFormatException e) {
            String msnError = "Error en lista de paginación:"+e.getMessage()+" " + e.getMessage();
            FacesUtil.mostrarMensajeError(msnError, e, LOGGER);
            throw new ApplicationException(msnError,e);
        }  catch (Exception e) {
            String msnError = "Error en lista de paginación:"+e.getMessage()+" " + e.getMessage();
            FacesUtil.mostrarMensajeError(msnError, e, LOGGER);
            throw new ApplicationException(msnError,e);
        }
    }

    public void pageFirstCap() {
        try {
            listInfoByPageCap(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstCap direccionando a la primera página" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstCap direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
    }

    public void pagePreviousCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = actualCap - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousCap direccionandoa la página anterior. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousCap direccionandoa la página anterior. " + e.getMessage(), e, LOGGER);
        }
    }

    public void irPaginaCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = Integer.parseInt(numPaginaCap);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (NumberFormatException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaCap direccionandoa la página. " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNextCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = actualCap + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextCap direccionandoa a la siguiente página. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextCap direccionandoa a la siguiente página. " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageLastCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            listInfoByPageCap(totalPaginas);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLastCap direccionandoa a la última página. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLastCap direccionandoa a la última página. " + e.getMessage(), e, LOGGER);
        }
    }

    public int getTotalPaginasCap() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = lstCmtNotaOtComentarioMglsAux.size();
            return (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCap direccionando a la primera página. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCap direccionando a la primera página. " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActualCap() {
        paginaListcap = new ArrayList<>();
        int totalPaginas = getTotalPaginasCap();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListcap.add(i);
        }
        pageActualCap = String.valueOf(actualCap) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaCap == null) {
            numPaginaCap = "1";
        }
        numPaginaCap = String.valueOf(actualCap);
        return pageActualCap;
    }

    //////////////Paginado Notas Ot//////////////
    public void listInfoByPageNot(int page) throws ApplicationException {
        try {
            actualNot = page;
            getTotalPaginasNot();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPagNot * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (lstCmtNotaOtMglsAux != null && lstCmtNotaOtMglsAux.size() > 0) {

                int maxResult;
                if ((firstResult + filasPagNot) > lstCmtNotaOtMglsAux.size()) {
                    maxResult = lstCmtNotaOtMglsAux.size();
                } else {
                    maxResult = (firstResult + filasPagNot);
                }

                notaOtMglList = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    notaOtMglList.add(lstCmtNotaOtMglsAux.get(k));
                }
            }

        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNot en lista de paginación:"+e.getMessage()+" ", e, LOGGER);
            throw new ApplicationException(e);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNot en lista de paginación:"+e.getMessage()+" ", e, LOGGER);
            throw new ApplicationException(e);
        }
    }

    public void pageFirstNot() {
        try {
            listInfoByPageNot(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNot direccionando a la primera página: "+e.getMessage()+" ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNot direccionando a la primera página: "+e.getMessage()+" ", e, LOGGER);
        }
    }

    public void pagePreviousNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = actualNot - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNot direccionando a página anterior: "+e.getMessage()+" ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNot direccionando a página anterior: "+e.getMessage()+" ", e, LOGGER);            
        }
    }

    public void irPaginaNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = Integer.parseInt(numPaginaNot);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaNot direccionando a página:"+e.getMessage()+" ", e, LOGGER);            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaNot direccionando a página:"+e.getMessage()+" ", e, LOGGER); 
        } catch(Exception e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaNot direccionando a página:"+e.getMessage()+" ", e, LOGGER); 
        }
    }

    public void pageNextNot() {
        try {
            int totalPaginas = getTotalPaginasNot();
            int nuevaPageActual = actualNot + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNot(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNot direccionando a la siguiente página:"+e.getMessage()+" ", e, LOGGER); 
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNot direccionando a la siguiente página:"+e.getMessage()+" ", e, LOGGER); 
        }
    }

    public void pageLastNot() {
        try {
            if (selectedTab.equals(TAB_NOTAS_OT_EJECUCION)) {
            int totalPaginas = getTotalPaginasNot();
            listInfoByPageNot(totalPaginas);
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNot direccionando a la última página:"+e.getMessage()+" ", e, LOGGER);             
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNot direccionando a la última página:"+e.getMessage()+" ", e, LOGGER);             
        }
    }

    public int getTotalPaginasNot() {
        try {
            if(lstCmtNotaOtMglsAux != null){
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = lstCmtNotaOtMglsAux.size();
            return (int) ((pageSol % filasPagNot != 0)
                    ? (pageSol / filasPagNot) + 1 : pageSol / filasPagNot);
            }
           
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNot direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);                
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNot direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);                
        }
        return 1;
    }

    public String getPageActualNot() {
        paginaListNot = new ArrayList<>();
        int totalPaginas = getTotalPaginasNot();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListNot.add(i);
        }
        pageActualNot = String.valueOf(actualNot) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaNot == null) {
            numPaginaNot = "1";
        }
        numPaginaNot = String.valueOf(actualNot);
        return pageActualNot;
    }

    public String irAgendar() {

        String irAgenda;
        try {
            CmtCuentaMatrizMgl cm = ordenTrabajo.getCmObj();
            CmtSubEdificioMgl subEdificioMgl;
            subEdificioMgl = cm.getSubEdificioGeneral();
            NodoMgl nodoMgl;

            CmtTecnologiaSubMgl tecnologiaSubMgl = tecnologiaSubMglFacadeLocal.
                    findBySubEdificioTecnologia(subEdificioMgl,
                            ordenTrabajo.getBasicaIdTecnologia());

            if (tecnologiaSubMgl != null && tecnologiaSubMgl.getTecnoSubedificioId() != null) {
                nodoMgl = tecnologiaSubMgl.getNodoId();
            } else {
                //Consulto el geo
                nodoMgl = nodoMglFacadeLocal.consultaGeo(ordenTrabajo);
                if (nodoMgl == null) {
                    throw new ApplicationException("No existe nodo de cobertura para la tecnologia de la Ot");
                }
            }
            
            this.capacidad = cmtAgendamientoMglFacadeLocal.getCapacidad(ordenTrabajo, null, nodoMgl);
            irAgenda = cambiarTab("AGENDAMIENTO");
            session.setAttribute("capacity", "capacity");
        } catch (ApplicationException e) {
            String msg;
             if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequestGral = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error consultando la capacidad: " + msg, e, LOGGER);
            irAgenda = null;
        } catch (Exception e) {
             String msg;
             if (e.getMessage().contains("$")) {
                partesMensaje = e.getMessage().split("\\$");
                msg = partesMensaje[0];
            } else {
                msg = e.getMessage();
            }

            if (partesMensaje != null && partesMensaje[1] != null && !partesMensaje[1].isEmpty()) {
                isRequestGral = true;
                json = partesMensaje[1];
                session.setAttribute("json", json);
            }
            FacesUtil.mostrarMensajeError("Ocurrió un error consultando la capacidad: " + msg, e, LOGGER);
            irAgenda = null;
        }
        return irAgenda;
    }

    public boolean isSelectTodos() {
        return selectTodos;
    }

    public void setSelectTodos(boolean selectTodos) {
        this.selectTodos = selectTodos;
    }

    public boolean isDeshacerTodos() {
        return deshacerTodos;
    }

    public void setDeshacerTodos(boolean deshacerTodos) {
        this.deshacerTodos = deshacerTodos;
    }

    public void seleccionarTodos() {

        try {
            if (selectTodos) {
                if (lstSubEdiVT.size() > 0) {
                    for (CmtSubEdificiosVt subEdificiosVt : lstSubEdiVT) {
                        
                        subEdificiosVt.setGenAco(true);
                        subEdificiosVt.setGeneraAcometida("Y");
                        subEdificiosVtFacadeLocal.update(subEdificiosVt,usuarioVT,perfilVT);
                        
                    }
                    deshacerTodos = true;
                    selectTodos = false;
                }
                
            } else {
                if (lstSubEdiVT.size() > 0) {
                    for (CmtSubEdificiosVt subEdificiosVt : lstSubEdiVT) {
                        
                        subEdificiosVt.setGenAco(false);
                        subEdificiosVt.setGeneraAcometida("F");
                        subEdificiosVtFacadeLocal.update(subEdificiosVt,usuarioVT,perfilVT);
                        
                    }
                    deshacerTodos = false;
                    selectTodos = true;
                }
                
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en seleccionarTodos:"+e.getMessage()+" ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en seleccionarTodos:"+e.getMessage()+" ", e, LOGGER);            
        }
    }

    public List<CmtDetalleFlujoMgl> cargarDetalleFlujoDocumenPendiente(CmtOrdenTrabajoMgl ordenTrabajoVal) {
        try {
            //Concultamos el estado aprobación financiera - pte gestión
            CmtBasicaMgl cmtBasicaMglEstadoInterno = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_EST_INT_APROB_FIN_PEN_GESTION);
            detalleFlujoEstActual = detalleFlujoMglFacadeLocal.
                    findByTipoFlujoAndEstadoIni(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                            cmtBasicaMglEstadoInterno, ordenTrabajoVal.getBasicaIdTecnologia());

            detalleFlujoSolAprobacion = new ArrayList<>();

            for (CmtDetalleFlujoMgl detalleFlujoMgl : detalleFlujoEstActual) {
                detalleFlujoSolAprobacion.add(detalleFlujoMgl);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFlujoDocumenPendiente:"+e.getMessage()+" ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFlujoDocumenPendiente:"+e.getMessage()+" ", e, LOGGER);
        }
        return detalleFlujoSolAprobacion;
    }

    public boolean tieneAgendasPendientes(CmtOrdenTrabajoMgl ot) throws ApplicationException {
        
        try {
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                    findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());
            
            String subTipoWorForce = "";
            
            if (cmtEstadoxFlujoMgl != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWorForce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                }
            }
            //Consultamos si la orden tiene agendas pendientes
            List<CmtAgendamientoMgl> agendas = cmtAgendamientoMglFacadeLocal.
                    agendasPendientesByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
            if (agendas.size() > 0) {
                ultimaAgenda = false;
                
                for (CmtAgendamientoMgl agenda : agendas) {
                    if (agenda.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        ultimaAgenda = true;
                        break;
                    }
                }
            } else {
                ultimaAgenda = true;
            }            
            return ultimaAgenda;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes:" + msg + " ", e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + msg + " ", e);
        }
    }
    
      public boolean tieneAgendasPendientesOCanceladas(CmtOrdenTrabajoMgl ot) 
              throws ApplicationException {

        try {
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.
                    findByTipoFlujoAndEstadoInt(ot.getTipoOtObj().getTipoFlujoOt(),
                            ot.getEstadoInternoObj(),
                            ot.getBasicaIdTecnologia());

            String subTipoWorForce = "";

            if (cmtEstadoxFlujoMgl != null) {
                if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                    subTipoWorForce = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getNombreBasica();
                }
            }
            //Consultamos si la orden tiene agendas pendientes
            List<CmtAgendamientoMgl> agendas = cmtAgendamientoMglFacadeLocal.
                    agendasPendientesByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
            if (agendas.size() > 0) {
                ultimaAgenda = false;

                for (CmtAgendamientoMgl agenda : agendas) {
                    if (agenda.getUltimaAgenda().equalsIgnoreCase("Y")) {
                        ultimaAgenda = true;
                        break;
                    }
                }
            } else {
                    ultimaAgenda = true;
                    if (ordenTrabajo.getEstadoInternoObj().getBasicaId().compareTo(estadoOtId) != 0) {
                    //Consultamos si la orden tiene agendas canceladas
                    List<CmtAgendamientoMgl> agendasCan = cmtAgendamientoMglFacadeLocal.
                            agendasCanceladasByOrdenAndSubTipoWorkfoce(ot, subTipoWorForce);
                    if (agendasCan.size() > 0) {
                        //Cierro ot en rr
                        ParametrosMgl parametroHabilitarRR = parametrosMglFacadeLocal.findParametroMgl(Constant.HABILITAR_RR);

                        if (parametroHabilitarRR != null
                                && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {

                            CmtAgendamientoMgl agendaCan = agendasCan.get(0);
                            //capturo el numero de la ot en rr
                            String numeroOtRr = agendaCan.getIdOtenrr();
                            //Busco el estado cerrado OK
                            CmtTipoBasicaMgl tipoBasicaEstadoResultado
                                    = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_RESULTADO);

                            List<String> tiposEstadosCierreRR = Arrays.asList("REALIZADO");
                            List<CmtBasicaMgl> listadoEstadoCierre
                                    = basicaMglFacadeLocal.findEstadoResultadoOTRR(tiposEstadosCierreRR, tipoBasicaEstadoResultado);
                            CmtBasicaMgl estadoCierreOK = null;
                            if (listadoEstadoCierre != null && !listadoEstadoCierre.isEmpty()) {
                                for (CmtBasicaMgl estadosCierre : listadoEstadoCierre) {
                                    if (estadosCierre.getCodigoBasica().trim().equalsIgnoreCase("OK")) {
                                        estadoCierreOK = estadosCierre;
                                    }
                                }
                            }
                            if (estadoCierreOK != null && cmtEstadoxFlujoMgl != null) {
                                cmtEstadoxFlujoMgl.setEstadoOtRRFinal(estadoCierreOK);
                                boolean cerrarOtRr = ordenTrabajoMglFacadeLocal.
                                        cerrarOTRR(ot, numeroOtRr, cmtEstadoxFlujoMgl, usuarioVT, perfilVT);
                                if (cerrarOtRr) {
                                    LOGGER.info("Orden en RR cerrada satisfatoriamente");
                                } else {
                                    LOGGER.error("No se pudo cerrar la orden en RR");
                                }
                            }
                        }

                    }
                }
            }
            return ultimaAgenda;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes:" + msg + " ", e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en tieneAgendasPendientes: " + msg + " ", e);
        }
    }

    public boolean isUltimaAgenda() {
        return ultimaAgenda;
    }

    public void setUltimaAgenda(boolean ultimaAgenda) {
        this.ultimaAgenda = ultimaAgenda;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public String armarUrl(CmtArchivosVtMgl archivosVtMgl) {

        // Documento original de UCM
        String urlOriginal = archivosVtMgl.getRutaArchivo();

        String requestContextPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestContextPath();

        // URL correspondiente al Servlet que descarga imagenes de CCMM desde UCM.
        urlOriginal = requestContextPath + "/view/MGL/document/download/" + urlOriginal;

        return urlArchivoSoporte = " <a href=\"" + urlOriginal
                + "\"  target=\"blank\">" + archivosVtMgl.getNombreArchivo() + "</a>";

    }

    public List<CmtArchivosVtMgl> getLstArchivosAcoMgls() {
        return lstArchivosAcoMgls;
}

    public void setLstArchivosAcoMgls(List<CmtArchivosVtMgl> lstArchivosAcoMgls) {
        this.lstArchivosAcoMgls = lstArchivosAcoMgls;
    }
    
     public boolean activaDesactivaUCM(){

        String msn;
        try {
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimoName(Constant.ACTIVACION_ENVIO_UCM);
            String valor;
            if (parametrosMgl != null) {
                valor = parametrosMgl.getParValor();
                if (!valor.equalsIgnoreCase("1") && !valor.equalsIgnoreCase("0")) {
                    msn = "El valor configurado para el parametro:  "
                            + "" + Constant.ACTIVACION_ENVIO_UCM + " debe ser '1' o '0'  "
                            + "actualmente se encuentra el valor: " + valor + "";
                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    activacionUCM = false;
                } else activacionUCM = valor.equalsIgnoreCase("1");

            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + ex.getMessage(), ex, LOGGER);
            activacionUCM = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + e.getMessage(), e, LOGGER);
            activacionUCM = false;
        }
        return activacionUCM;
    }
     
    public void cargarListaNotas(String cadenaNotas) {

        String cadenaSinEspacios = cadenaNotas.replaceAll("\\s+", " ");
        String[] parts = cadenaSinEspacios.split(Constant.SPLIT_CADENA_NOTAS_OT_HIJA);
        String[] datosNotas;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        try {

            if (parts.length > 0) {
                lstCmtNotasOtHijaDto = new ArrayList<>();
                
                int inicioCad;
                for (String part : parts) {

                    if (!part.isEmpty()) {

                        CmtNotasOtHijaDto notasOtHijaDto = new CmtNotasOtHijaDto();
                        datosNotas = part.split("(?=\\s)");
                        notasOtHijaDto.setUsuarioIngresaNota(datosNotas[0]);
                        Date fechaIngresoNota = formatter.parse(datosNotas[1]);
                        notasOtHijaDto.setFechaIngresaNota(fechaIngresoNota); 
                        inicioCad = datosNotas[0].length() + datosNotas[1].length();
                        String cuerpo = part.substring(inicioCad, part.length());
                        String [] notaPar = cuerpo.split(";");
                        String notaFin="";
                        for(String nota : notaPar){
                            notaFin = notaFin+nota+";"+"\n";
                        }
                        notasOtHijaDto.setDescripcionNota(notaFin);
                        lstCmtNotasOtHijaDto.add(notasOtHijaDto);
                    }
                }
                Collections.sort(lstCmtNotasOtHijaDto);
                lstCmtNotasOtHijaDtoAux = lstCmtNotasOtHijaDto;
                
            }
        } catch (ParseException ex) {
            FacesUtil.mostrarMensajeError("Error al parsear la fecha: " + ex.getMessage(), ex, LOGGER);

        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al cargar lista de notas : " + ex.getMessage(), ex, LOGGER);
        }
    }
    
    public void cargarNota(String notaOtHi) {

        FacesContext.getCurrentInstance().addMessage
        (null, new FacesMessage(FacesMessage.SEVERITY_INFO, notaOtHi, ""));

    }
    
    
    ////////Paginado notas Ot HIja///////////
    public void listInfoByPageNotOtHija(int page) throws ApplicationException {
        try {
            actualNotOtHij = page;
            getTotalPaginasNotOtHija();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPagNotOtHij * (page - 1));
            }
            //Obtenemos el rango de la paginación
            if (lstCmtNotasOtHijaDtoAux != null && lstCmtNotasOtHijaDtoAux.size() > 0) {

                int maxResult;
                if ((firstResult + filasPagNotOtHij) > lstCmtNotasOtHijaDtoAux.size()) {
                    maxResult = lstCmtNotasOtHijaDtoAux.size();
                } else {
                    maxResult = (firstResult + filasPagNotOtHij);
                }

                lstCmtNotasOtHijaDto = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    lstCmtNotasOtHijaDto.add(lstCmtNotasOtHijaDtoAux.get(k));
                }
            }

        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNotOtHija en lista de paginación:"+e.getMessage()+" ", e, LOGGER);
            throw new ApplicationException(e);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageNotOtHija en lista de paginación: "+e.getMessage()+" ", e, LOGGER);
            throw new ApplicationException(e);
        }
    }

    public void pageFirstNotOtHija() {
        try {
            listInfoByPageNotOtHija(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNotOtHija direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirstNotOtHija direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);
        }
    }

    public void pagePreviousNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = actualNotOtHij - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNotOtHija direccionando a página anterior:"+e.getMessage()+" ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousNotOtHija direccionando a página anterior:"+e.getMessage()+" ", e, LOGGER);            
        }
    }

    public void irPaginaNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = Integer.parseInt(numPaginaNotOtHij);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (NumberFormatException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaNotOtHija direccionando a página:"+e.getMessage()+" ", e, LOGGER);            
        }
    }

    public void pageNextNotOtHija() {
        try {
            int totalPaginas = getTotalPaginasNotOtHija();
            int nuevaPageActual = actualNotOtHij + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageNotOtHija(nuevaPageActual);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNotOtHija direccionando a la siguiente página:"+e.getMessage()+" ", e, LOGGER); 
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextNotOtHija direccionando a la siguiente página:"+e.getMessage()+" ", e, LOGGER); 
        }
    }

    public void pageLastNotOtHija() {
        try {

            int totalPaginas = getTotalPaginasNotOtHija();
            listInfoByPageNotOtHija(totalPaginas);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNotOtHija direccionando a la última página:"+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLastNotOtHija direccionando a la última página:"+e.getMessage()+" ", e, LOGGER);
        }
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATECREAR);
    }




    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public int getTotalPaginasNotOtHija() {
        try {
            if(lstCmtNotasOtHijaDtoAux != null){
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = lstCmtNotasOtHijaDtoAux.size();
            return (pageSol % filasPagNotOtHij != 0)
                    ? (pageSol / filasPagNotOtHij) + 1 : pageSol / filasPagNotOtHij;
            }
           
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNotOtHija direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);                
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasNotOtHija direccionando a la primera página:"+e.getMessage()+" ", e, LOGGER);                
        }
        return 1;
    }

    public String getPageActualNotOtHija() {
        paginaListNotOtHij = new ArrayList<>();
        int totalPaginas = getTotalPaginasNotOtHija();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListNotOtHij.add(i);
        }
        pageActualNotOtHij = String.valueOf(actualNotOtHij) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaNotOtHij == null) {
            numPaginaNotOtHij = "1";
        }
        numPaginaNotOtHij = String.valueOf(actualNotOtHij);
        return pageActualNotOtHij;
    }
    /////////////////////////////////////////

    public boolean isActivacionUCM() {
        return activacionUCM;
    }

    public void setActivacionUCM(boolean activacionUCM) {
        this.activacionUCM = activacionUCM;
    }

    public BigDecimal getTecnologiaListSelect() {
        return tecnologiaListSelect;
    }

    public void setTecnologiaListSelect(BigDecimal tecnologiaListSelect) {
        this.tecnologiaListSelect = tecnologiaListSelect;
    }

    public BigDecimal getSegmentoListSelect() {
        return segmentoListSelect;
    }

    public void setSegmentoListSelect(BigDecimal segmentoListSelect) {
        this.segmentoListSelect = segmentoListSelect;
    }

    public BigDecimal getTipoTrabajoListSelect() {
        return tipoTrabajoListSelect;
    }

    public void setTipoTrabajoListSelect(BigDecimal tipoTrabajoListSelect) {
        this.tipoTrabajoListSelect = tipoTrabajoListSelect;
    }

    public BigDecimal getSubTipoTrabaoListSelect() {
        return subTipoTrabaoListSelect;
    }

    public void setSubTipoTrabaoListSelect(BigDecimal subTipoTrabaoListSelect) {
        this.subTipoTrabaoListSelect = subTipoTrabaoListSelect;
    }

    public BigDecimal getClaseOTListSelect() {
        return claseOTListSelect;
    }

    public void setClaseOTListSelect(BigDecimal claseOTListSelect) {
        this.claseOTListSelect = claseOTListSelect;
    }

    public List<CmtNotasOtHijaDto> getLstCmtNotasOtHijaDto() {
        return lstCmtNotasOtHijaDto;
    }

    public void setLstCmtNotasOtHijaDto(List<CmtNotasOtHijaDto> lstCmtNotasOtHijaDto) {
        this.lstCmtNotasOtHijaDto = lstCmtNotasOtHijaDto;
    }

    public List<CmtAgendamientoMgl> getAgendasListConsulta() {
        return agendasListConsulta;
    }

    public void setAgendasListConsulta(List<CmtAgendamientoMgl> agendasListConsulta) {
        this.agendasListConsulta = agendasListConsulta;
    }

    public List<String> getAgendasList() {
        return agendasList;
    }

    public void setAgendasList(List<String> agendasList) {
        this.agendasList = agendasList;
    }

    public String getOnyxComplejidad() {
        return OnyxComplejidad;
    }

    public void setOnyxComplejidad(String OnyxComplejidad) {
        this.OnyxComplejidad = OnyxComplejidad;
    }
 
    public String getNombreUsuario() {
        if (ordenTrabajo.getIdOt() != null && !ordenTrabajo.getIdOt().equals(BigDecimal.ZERO)
                && ordenTrabajo.getUsuarioCreacionId() != null) {
            try {
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(ordenTrabajo.getUsuarioCreacion());
                if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                    nombreUsuario = usuarioLogin.getNombre();
                }
            } catch (ApplicationException ex) {
                FacesUtil.mostrarMensajeError("Error al obtener el Nombre del usuario del solicitante:"+ex.getMessage()+"", ex, LOGGER);
            }
        }

        return nombreUsuario;
    }

    public String getAreaResponsable() {
        if (ordenTrabajo.getIdOt() != null && !ordenTrabajo.getIdOt().equals(BigDecimal.ZERO)
                && ordenTrabajo.getUsuarioCreacionId() != null) {
            try {
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(ordenTrabajo.getUsuarioCreacion());
                if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                    areaResponsable = usuarioLogin.getDescripcionArea();
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error al obtener el Área Responsable del solicitante:"+e.getMessage()+" ", e, LOGGER);
            }
        }
        return areaResponsable;
    }
    
    private boolean pasaEstadoRazonado(CmtOrdenTrabajoMgl ordenTrabajoVal, BigDecimal idEstadoProximo) throws ApplicationException {
        try {
            CmtBasicaMgl estadoProximo = basicaMglFacadeLocal.findById(idEstadoProximo);

            List<CmtDetalleFlujoMgl> detalleFlujoEstProximo = null;
            if (estadoProximo != null) {
                if (ordenTrabajoVal != null && ordenTrabajoVal.getTipoOtObj() != null) {
                    detalleFlujoEstProximo = detalleFlujoMglFacadeLocal.
                            findByTipoFlujoAndEstadoProAndTecRazon(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                                    estadoProximo, ordenTrabajoVal.getBasicaIdTecnologia());
                }
            }
            
            if (detalleFlujoEstProximo != null && !detalleFlujoEstProximo.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error en pasaEstadoRazonado. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en pasaEstadoRazonado: " + ex.getMessage() + " ", ex);
        } catch (Exception ex) {
            LOGGER.error("Error en pasaEstadoRazonado. " + ex.getMessage(), ex);
            throw new ApplicationException("Error en pasaEstadoRazonado:" + ex.getMessage() + " ", ex);
        }
    }
    
    private boolean validaCambioEstadoRazonado(CmtOrdenTrabajoMgl ordenTrabajoVal, BigDecimal estadoNuevo) throws ApplicationException {

        List<CmtDetalleFlujoMgl> detalleFlujoEstIni;
        boolean resultado = false;
        estadoAnteriorRazonado= null;

        if (ordenTrabajoVal != null && ordenTrabajoVal.getTipoOtObj() != null) {
            detalleFlujoEstIni = detalleFlujoMglFacadeLocal.
                    findByTipoFlujoAndEstadoProAndTecRazon(ordenTrabajoVal.getTipoOtObj().getTipoFlujoOt(),
                            ordenTrabajoVal.getEstadoInternoObj(), ordenTrabajoVal.getBasicaIdTecnologia());

            if (detalleFlujoEstIni != null && !detalleFlujoEstIni.isEmpty()) {
                CmtOrdenTrabajoAuditoriaMgl ultimoEstado
                        = ordenTrabajoMglFacadeLocal.findUltimoEstadoOtRazonada(ordenTrabajoVal, ordenTrabajoVal.getEstadoInternoObj().getBasicaId());
                estadoAnteriorRazonado = detalleFlujoEstIni.get(0).getEstadoInicialObj();
                if (ultimoEstado != null
                        && (ultimoEstado.getEstadoInternoObj().getBasicaId().compareTo(estadoNuevo) == 0)) {
                    resultado = true;
                }else{
                  resultado = false;  
                }
            } else {
                resultado = false;
            }
        }
        return resultado;
    }
    
    public void notificaOrdenInOnix(){
        
        if(ordenTrabajo != null && 
                ordenTrabajo.getIdOt() != null){
            
        }
    }
    
    public boolean validarUpdateAgenda() {
        
        boolean respuesta = false;
        
        try {

            Date fechaHoy = new Date();
            String mensajesValidacion;
            String mensajesValidacionFinal= "";

            if (agendasList != null && !agendasList.isEmpty()) {
                for (String idAgendaLis : new ArrayList<>(agendasList)) {
                    CmtAgendamientoMgl agenda = cmtAgendamientoMglFacadeLocal.findById(new BigDecimal(idAgendaLis));

                    if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CANCELADA)) {
                        mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra cancelada y no se puede editar.";
                        mensajesValidacionFinal += mensajesValidacion;
                        for (String idAgenda : new ArrayList<>(agendasList)) {
                            if (agenda.getId().toString().equalsIgnoreCase(idAgenda)) {
                                agendasList.remove(idAgenda);
                            }
                        }
                    } else if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {
                        mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra cerrada y no se puede editar.";
                        mensajesValidacionFinal += mensajesValidacion;
                        for (String idAgenda : new ArrayList<>(agendasList)) {
                            if (agenda.getId().toString().equalsIgnoreCase(idAgenda)) {
                                agendasList.remove(idAgenda);
                            }
                        }
                    } else if (agenda != null && agenda.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)) {
                        mensajesValidacion = "La agenda " + agenda.getOfpsOtId() + " se encuentra nodone(cerrada) y no se puede editar.";
                        mensajesValidacionFinal += mensajesValidacion;
                        for (String idAgenda : new ArrayList<>(agendasList)) {
                            if (agenda.getId().toString().equalsIgnoreCase(idAgenda)) {
                                agendasList.remove(idAgenda);
                            }
                        }
                    } else if (agenda != null) {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String fechaAgenda = format.format(agenda.getFechaAgenda());
                        String fechaActual = format.format(fechaHoy);
                        Date fechaDate1;
                        Date fechaDate2;
                        boolean valida;
                        try {
                            fechaDate1 = format.parse(fechaAgenda);
                            fechaDate2 = format.parse(fechaActual);
                            if (fechaDate1.before(fechaDate2)) {
                                LOGGER.info("La Fecha de la agenda " + agenda.getOfpsOtId() + "  es menor a la de hoy ");
                                valida = false;
                            } else if (fechaDate2.before(fechaDate1)) {
                                LOGGER.info("La Fecha de la agenda " + agenda.getOfpsOtId() + " es Mayor a la de hoy ");
                                valida = true;
                            } else {
                                LOGGER.info("Las Fechas Son iguales ");
                                valida = true;
                            }
                            if (valida) {
                                LOGGER.info("Se puede actualizar la agenda.");
                            } else {
                                mensajesValidacion = "No se puede actualizar la agenda " + agenda.getOfpsOtId() + "  con fecha de agendamiento  "
                                        + "menor al dia de hoy.";
                                mensajesValidacionFinal += mensajesValidacion;
                                for (String idAgenda : new ArrayList<>(agendasList)) {
                                    if (agenda.getId().toString().equalsIgnoreCase(idAgenda)) {
                                        agendasList.remove(idAgenda);
                                    }
                                }
                            }
                        } catch (ParseException ex) {
                            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                            LOGGER.error(msg);
                        }
                    }
                }
                if (!mensajesValidacionFinal.isEmpty()) {
                    respuesta=false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    mensajesValidacionFinal, null));
                }else{
                    respuesta=true;
                }
            } else {
                respuesta = true;
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error actualizando la Agenda  en CmtAgendamientoMglBean: updateAgenda()" + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }
    
    public String devuelveFechaFormater(Date fechaAgenda) {

        String date="";
        if (fechaAgenda != null) {
           date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaAgenda);
        }
        return date;
    }
}
