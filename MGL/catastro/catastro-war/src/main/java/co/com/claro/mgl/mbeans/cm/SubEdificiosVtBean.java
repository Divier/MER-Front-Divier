package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificioMglManager;
import co.com.claro.mgl.dtos.ResponseCreateHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 */
@ManagedBean(name = "subEdificiosVtBean")
@ViewScoped
public class SubEdificiosVtBean implements Serializable {

    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private CmtSubEdificiosVtFacadeLocal subEdificiosVtFacadeLocal;
    @EJB
    private CmtHhppVtMglFacadeLocal tecnologiaHabilitadaFacadeLocal;
    @EJB
    private CmtVisitaTecnicaMglFacadeLocal cmtVisitaTecnicaMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacade;
    @EJB
    private CmtTecnologiaSubedificioMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal cmtTecnoSubMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private static final Logger LOGGER = LogManager.getLogger(SubEdificiosVtBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private CmtSubEdificiosVt subEdificioVt;
    private List<CmtSubEdificiosVt> subEdificioVtList;
    private CmtVisitaTecnicaMgl vt;
    private CmtTipoBasicaMgl tipoSubEdificio;
    private List<CmtBasicaMgl> tipoSubEdificioList;
    private List<CmtSubEdificioMgl> subEdificioSinVtList;
    private CmtBasicaMgl tipoSubEdi1;
    private CmtBasicaMgl tipoSubEdi2;
    private CmtBasicaMgl tipoSubEdi3;
    private CmtBasicaMgl tipoSubEdi4;
    private CmtBasicaMgl tipoSubEdi5;
    private String valorNivel1;
    private String valorNivel2;
    private String valorNivel3;
    private String valorNivel4;
    private String valorNivel5;
    private Integer cantidadSubEdificio = 0;
    private boolean showDialog = false;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private boolean habilitaProcesoRr;
    private VisitaTecnicaBean visitaTecnicaBean;
    private boolean disableButtonChangeSub = false;
    private boolean cambioAUni = false;
    private BigDecimal optipoNivel1;
    private BigDecimal optipoNivel2;
    private BigDecimal optipoNivel3;
    private BigDecimal optipoNivel4;
    ResponseCreateHhppDto responseCreateHhppDto = new ResponseCreateHhppDto();
    private boolean nodoCargado = false;
    private List<CmtArchivosVtMgl> lstArchivosVtMgls;
    private VisitaTecnicaBean vtMglBean;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ot;
    private SecurityLogin securityLogin;
   
    public SubEdificiosVtBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
             vtMglBean = JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            this.vt = vtMglBean.getVt();
            if (vt == null) {
                String msn2 = "Error no hay una vt activa";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }


        } catch (IOException ex) {
            String msn2 = "Error al cargar el formulario de Multiedificio: " + ex.getMessage();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2, ex);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            subEdificiosVtFacadeLocal.setUser(usuarioVT, perfilVt);
            subEdificioMglFacadeLocal.setUser(usuarioVT, perfilVt);
            cmtVisitaTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVt);
            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            ot = otMglBean.getOrdenTrabajo();
            tipoSubEdificio = new CmtTipoBasicaMgl();
            tipoSubEdi1 = new CmtBasicaMgl();
            tipoSubEdi2 = new CmtBasicaMgl();
            tipoSubEdi3 = new CmtBasicaMgl();
            tipoSubEdi4 = new CmtBasicaMgl();
            tipoSubEdi5 = new CmtBasicaMgl();

            tipoSubEdificio.setTipoBasicaId(
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_SUBEDIFICIO)
                    .getTipoBasicaId());
            tipoSubEdificioList = basicaMglFacadeLocal.findByTipoBasica(tipoSubEdificio);
            subEdificioVtList = subEdificiosVtFacadeLocal.findByVt(vt);
             cargarCamposNuevos();
            subEdificioVtList = posSetListas(subEdificioVtList);
            vt = cmtVisitaTecnicaMglFacadeLocal.findById(vt);
            //Cargamos los subEdificios sin Visita Tecnica asociados a la CM
            subEdificioSinVtList =
                    cmtTecnologiaSubMglFacadeLocal.getSubEdificiosWithOutTecnology(vt.getOtObj());
            if (subEdificioVtList != null && !subEdificioVtList.isEmpty()
                    && subEdificioSinVtList != null && !subEdificioSinVtList.isEmpty()) {
                for (CmtSubEdificiosVt svt : subEdificioVtList) {
                    for (CmtSubEdificioMgl ssvt : subEdificioSinVtList) {
                        if (svt.getSubEdificioObj() != null
                                && svt.getSubEdificioObj().getSubEdificioId() != null
                                && ssvt != null && ssvt.getSubEdificioId() != null
                                && svt.getSubEdificioObj().getSubEdificioId().compareTo(
                                ssvt.getSubEdificioId()) == 0) {
                            //subEdificioSinVtList.remove(ssvt);
                            break;
                        }
                    }
                }
            }

            habilitaProcesoRr = ordenTrabajoMglFacadeLocal.
                    validaProcesoOt(vt.getOtObj(),
                    Constant.PARAMETRO_VALIDACION_OT_HABILITA_CREA_RR);
            
            CmtSubEdificioMgl cmtSubEdificioMgl = vt.getOtObj().getCmObj().getSubEdificioGeneral();
            
            if (cmtSubEdificioMgl.getCambioestado().equals("Y")) {
                disableButtonChangeSub = true;
            }
            if (subEdificioVtList.isEmpty()) {
                nodoCargado = false;
            } else {
                nodoCargado = true;
            }
            if (vtMglBean.getSelectedTab().equalsIgnoreCase("COSTOS")) {
                lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_COSTOS);
            } else {
                lstArchivosVtMgls = cmtArchivosVtMglFacadeLocal.findAllByVtxOrigen(vt, Constant.ORIGEN_CARGA_ARCHIVO_FINANCIERA);
            }
          
        } catch (ApplicationException e) {
            String msn2 = "Error al cargar listas basicas del formulario de subedificios: " + e.getMessage();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2, e);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para crear subEdificios en la visita técnica
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrearSubEdificios() {
        try {
            return ValidacionUtil.validarVisualizacion("TABMULTIEDIFICIOHOTCCMM",
                    "BTNCREARSUBEDIFICIOS", cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos para crear subEdificios. " , e);
        }

        return false;
    }

    private CmtBasicaMgl getCloneTipoSubEdi(CmtBasicaMgl tipoSub) {
        try {
            if (tipoSub != null && tipoSub.getBasicaId() != null
                    && tipoSub.getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                return tipoSub.clone();
            }
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Error al momento de clonar el tipo de subedifcio. EX000: " + e.getMessage(), e);
        }
        return null;
    }

    public String adicionarSubEd(CmtSubEdificioMgl edificioSelected) {
        try {
            if (otMglBean.tieneAgendasPendientes(ot)) {
                if (edificioSelected != null && edificioSelected.getEstadoSubEdificioObj() != null 
                        && edificioSelected.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && edificioSelected.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                        equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    String msn2 = "Ud no puede modificar el edificio general ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                } else {
                    if (subEdificioVtList != null && subEdificioVtList.size() > 0) {
                        if (validarNivelesSubEdVt(subEdificioVtList)) {
                            editarSubEdificios();
                            validarTecnologiaOrden(edificioSelected);
                        } else {
                            subEdificioVtList = posSetListas(subEdificioVtList);
                        }
                        cargarCamposNuevos();
                    } else {
                        validarTecnologiaOrden(edificioSelected);
                        cargarCamposNuevos();
                    }
                }
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
        } catch (ApplicationException e) {
            String msn = "No Fue posible adicionar el SubEdificio: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn, e, LOGGER);
        }
        return null;
    }

    /**
     * Perimte eliminar un subedificio desde la visita tecnica, el cual no tenga
     * ningun registro relacionado ni yna visita tecnica iniciada.
     *
     * @author Carlos Leonardo villamil.
     * @param edificioSelected
     * @param CmtSubEdificioMgl Cuenta Matriz el estado especificado
     * @return String
     */
    public String elminarSubEd(CmtSubEdificioMgl edificioSelected) {
        try {
            if (otMglBean.tieneAgendasPendientes(ot)) {
                if (edificioSelected != null && edificioSelected.getEstadoSubEdificioObj() != null 
                        && edificioSelected.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && edificioSelected.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                        equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {

                    String msn2 = "Ud no puede eliminar el edificio general ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                } else {
                    subEdificioMglFacadeLocal.deleteSubEdificioOnProcesVt(edificioSelected);
                    subEdificioSinVtList =
                            cmtTecnologiaSubMglFacadeLocal.getSubEdificiosWithOutTecnology(vt.getOtObj());
                    if (subEdificioVtList != null && !subEdificioVtList.isEmpty()
                            && subEdificioSinVtList != null && !subEdificioSinVtList.isEmpty()) {
                        for (CmtSubEdificiosVt svt : subEdificioVtList) {
                            for (CmtSubEdificioMgl ssvt : subEdificioSinVtList) {
                                if (svt.getSubEdificioObj() != null
                                        && svt.getSubEdificioObj().getSubEdificioId() != null
                                        && ssvt != null && ssvt.getSubEdificioId() != null
                                        && svt.getSubEdificioObj().getSubEdificioId().compareTo(
                                        ssvt.getSubEdificioId()) == 0) {
                                    subEdificioSinVtList.remove(ssvt);
                                    break;
                                }
                            }
                        }
                    }
                    String formTabSeleccionado = "";
                    formTabSeleccionado = "/view/MGL/CM/tabsVt/multiedificio";
                    FacesContext contextGeneral = FacesContext.getCurrentInstance();
                    NavigationHandler navigationHandlerGeneral = contextGeneral.getApplication().getNavigationHandler();
                    navigationHandlerGeneral.handleNavigation(contextGeneral, null, formTabSeleccionado + "?faces-redirect=true");
                }
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
        } catch (ApplicationException e) {
            String msn = "No Fue posible Eliminar el subedificio: ".concat(e.getMessage());
            FacesUtil.mostrarMensajeError(msn, e, LOGGER);
        }
        return null;
    }

    public void crearSubEdificios() {
        try {
            NodoMgl nodoCobertura = null;
            if (cantidadSubEdificio != null && cantidadSubEdificio > 0) {
                if (optipoNivel1 != null) {

                    for (int counter = 0; counter < cantidadSubEdificio; counter++) {
                        subEdificioVt = new CmtSubEdificiosVt();
                        // metodo que cargar las opciones de los niveles
                        cargarCamposActuales();
                        // consultar en el Geo nuevos Subedificios
                        nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                        if (nodoCobertura != null) {
                            subEdificioVt.setCodigoNodo(subEdificioVt.getCodigoNodo());
                            subEdificioVt.setNodo(nodoCobertura);
                        }
                        subEdificioVt.setVtObj(vt);
                        subEdificioVt = subEdificiosVtFacadeLocal.createCm(subEdificioVt);
                        subEdificioVtList.add(subEdificioVt);
                    }
                    subEdificioVtList = posSetListas(subEdificioVtList);
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));
                } else {
                    String msn2 = "Debe Seleccionar el Nivel 1";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                }
            } else {
                String msn2 = "La cantidad de sub edificios Debe ser Mayor a 0 ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
        } catch (ApplicationException e) {
            String msn2 = "Error al crear subedificios: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(e.getMessage(), e);
        }
    }
    public void cargarCamposActuales() throws ApplicationException {
        if (optipoNivel1 != null) {
            CmtBasicaMgl tipoSubEdi1Temp = basicaMglFacadeLocal.findById(optipoNivel1);
            subEdificioVt.setOptipoNivel1(optipoNivel1);
            if (tipoSubEdi1Temp != null
                    && tipoSubEdi1Temp.getBasicaId() != null) {
                subEdificioVt.setTipoNivel1(tipoSubEdi1Temp);
            }
        }
        if (optipoNivel2 != null) {
            CmtBasicaMgl tipoSubEdi2Temp = basicaMglFacadeLocal.findById(optipoNivel2);
            subEdificioVt.setTipoNivel2(tipoSubEdi2Temp);
            if (tipoSubEdi2Temp != null
                    && tipoSubEdi2Temp.getBasicaId() != null) {
                subEdificioVt.setTipoNivel2(basicaMglFacadeLocal.
                        findById(tipoSubEdi2Temp.getBasicaId()));
            }
        }
        if (optipoNivel3 != null) {
            CmtBasicaMgl tipoSubEdi3Temp = basicaMglFacadeLocal.findById(optipoNivel3);
            subEdificioVt.setTipoNivel3(tipoSubEdi3Temp);
            if (tipoSubEdi3Temp != null
                    && tipoSubEdi3Temp.getBasicaId() != null) {
                subEdificioVt.setTipoNivel3(basicaMglFacadeLocal.
                        findById(tipoSubEdi3Temp.getBasicaId()));
            }
        }
        if (optipoNivel4 != null) {
            CmtBasicaMgl tipoSubEdi4Temp = basicaMglFacadeLocal.findById(optipoNivel4);
            subEdificioVt.setTipoNivel4(tipoSubEdi4Temp);
            if (tipoSubEdi4Temp != null
                    && tipoSubEdi4Temp.getBasicaId() != null) {
                subEdificioVt.setTipoNivel4(basicaMglFacadeLocal.
                        findById(tipoSubEdi4Temp.getBasicaId()));
            }
        }

    }

        public String editarSubEdificios() {
        try {
            if (otMglBean.tieneAgendasPendientes(ot)) {
                boolean isValidacionOk = true;
                  List<CmtDireccionDetalladaMgl> listaDeta = null;
                visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
                CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
                CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
                if (visitaTecnicaBean.getDrDireccion() != null) {
                    for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                        // validacion para no ingresar direcciones diferente a la de la cuenta matriz
                        if (subEdi.getDireccionSubEdificio() != null && !subEdi.getDireccionSubEdificio().isEmpty() && !subEdi.getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCodTipoDir().
                                equalsIgnoreCase(visitaTecnicaBean.getDrDireccion().getIdTipoDireccion())) {
                            isValidacionOk = false;
                        }
                    }

                } else {
                    isValidacionOk = true;
                }

                if (!isValidacionOk) {
                    String msn2 = "Ud no ha validado la direccion o  ha ingresado un tipo de direccion que no corresponde con  la cuenta matriz";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    return null;
                }

                if (validarNivelesSubEdVt(subEdificioVtList)) {
                    for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                        if (subEdi.getCodigoRr() == null) {
                            // cuando es unico edifico no se concatena el nombre del nivel
                            if(subEdi.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj() != null 
                        && subEdi.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && !subEdi.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                        equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)){
                                 subEdi.setNombramientoRr(subEdi.getNombreSubEdificio()); 
                                 
                            }else{
                                 subEdi.setNombramientoRr(subEdi.getDireccionSubEdificio()
                                    + " " + subEdi.getNombreSubEdificio()); 
                            }
                          
                            if (subEdi.getValorNivel1() != null && !subEdi.getValorNivel1().isEmpty()) {
                                subEdi.setValorNivel1(subEdi.getValorNivel1().toUpperCase());
                            }
                            if (subEdi.getValorNivel2() != null && !subEdi.getValorNivel2().isEmpty()) {
                                subEdi.setValorNivel2(subEdi.getValorNivel2().toUpperCase());
                            }
                            if (subEdi.getValorNivel3() != null && !subEdi.getValorNivel3().isEmpty()) {
                                subEdi.setValorNivel3(subEdi.getValorNivel3().toUpperCase());
                            }
                            if (subEdi.getValorNivel4() != null && !subEdi.getValorNivel4().isEmpty()) {
                                subEdi.setValorNivel4(subEdi.getValorNivel4().toUpperCase());
                            }
                            //JDHT
                            if(ot != null && ot.getCmObj().getDireccionPrincipal() != null 
                                    && ot.getCmObj().getDireccionPrincipal().getBarrio() != null
                                    && !ot.getCmObj().getDireccionPrincipal().getBarrio().isEmpty()){
                                 subEdi.setBarrio(ot.getCmObj().getDireccionPrincipal().getBarrio().toUpperCase());
                            }
                            // se actualiza el subedifcio en MGL   
                            subEdi = subEdificiosVtFacadeLocal.update(subEdi,usuarioVT, perfilVt);
                            // se calcula el estado del subedificio en funcion al tipo de ot, el estado interno de la ot 
                            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                                    subEdi.getVtObj().getOtObj().getTipoOtObj().getTipoFlujoOt(), subEdi.getVtObj().getOtObj().getEstadoInternoObj(), subEdi.getVtObj().getOtObj().getBasicaIdTecnologia());
                            CmtSubEdificioMgl subEdiMgl = subEdi.mapearCamposCmtSubEdificioMgl(subEdi,
                                    cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());

                            boolean isCreate = subEdi.getSubEdificioObj() == null;
                            List<CmtTecnologiaSubMgl> listatecnologiaSubMgls;
                            List<String> listaBasicaTec = new ArrayList<>();
                            listatecnologiaSubMgls = cmtTecnoSubMglFacadeLocal.findTecnoSubBySubEdi(vt.getOtObj().getCmObj().getSubEdificioGeneral());
                            for(CmtTecnologiaSubMgl cmtTecnologiaSubMgl : listatecnologiaSubMgls) {
                                listaBasicaTec.add(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp());
                            }
                            if (isCreate) {
                                //se crean los subedificios nuevos en la pestaña multiedificios  en RR y MGL
                                if (listaBasicaTec.contains(Constant.RED_FO)) {
                                    CmtBasicaMgl basicaMgl;
                                    CmtTipoBasicaMgl cmtTipoBasicaMgl;
                                    cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
                                    basicaMgl = cmtTipoBasicaMgl.
                                            findBasicaByCampoEntidadAs400ExtendidoAndValorExtendido(Constant.RED_FO, "Y");
                                    subEdiMgl.setEstadoSubEdificioObj(basicaMgl);
                                }
                                subEdiMgl = cmtSubEdificioMglManager.create(subEdiMgl, usuarioVT, perfilVt, false);
                                if (subEdi.getSubEdificioObj() == null) {
                                    subEdi.setCodigoNodo(subEdi.getCodigoNodo());
                                }
                                subEdi.setSubEdificioObj(subEdiMgl);

                            } else {
                                 //se actualizan los subedificios existentes en RR y MGL
                                 // se actualiza en RR con el estado combinado cuando hay tecnologia FO
                                if (listaBasicaTec.contains(Constant.RED_FO)) {
                                    CmtBasicaMgl basicaMgl;
                                    CmtTipoBasicaMgl cmtTipoBasicaMgl;
                                    cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
                                    basicaMgl = cmtTipoBasicaMgl.
                                            findBasicaByCampoEntidadAs400ExtendidoAndValorExtendido(Constant.RED_FO, "Y");
                                    subEdiMgl.setEstadoSubEdificioObj(basicaMgl);
                                }
                                 
                                subEdiMgl = cmtSubEdificioMglManager.update(subEdiMgl, usuarioVT, perfilVt, false, false);
                                if (subEdi.getSubEdificioObj() == null) {
                                    subEdi.setCodigoNodo(subEdi.getCodigoNodo());
                                }
                                subEdi.setSubEdificioObj(subEdiMgl);
                            }
                           
                          
                              // se valida si la ot es de la misma tecnologia de la cm
                              // si es diferente debe mostrar en la pestaña costos el nodo que entrego el geo al momento de la creacion de cm 
                            CmtTecnologiaSubMgl tecnologiaSubMgls;
                            tecnologiaSubMgls = cmtTecnoSubMglFacadeLocal.findBySubEdificioTecnologia(subEdiMgl, vt.getOtObj().getBasicaIdTecnologia());
                            NodoMgl nodoCobertura;
                            if (tecnologiaSubMgls != null
                                    && tecnologiaSubMgls.getTecnoSubedificioId() != null) {
                                LOGGER.info("Tecnologias iguales");
                                if (!tecnologiaSubMgls.getNodoId().getNodCodigo().contains("NFI")) {
                                    subEdi.setNodo(tecnologiaSubMgls.getNodoId());
                                } else {
                                    //si el nodo es de tipo NFI se georeferencia 
                                    nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                                    if (nodoCobertura != null) {
                                        subEdi.setNodo(nodoCobertura);
                                    }
                                }
                            } else {
                                // tecnologias diferentes se asigna el nodo del edificio general
                                 LOGGER.info("Tecnologias diferentes");
                                tecnologiaSubMgls = cmtTecnoSubMglFacadeLocal.findBySubEdificioTecnologia(vt.getOtObj().getCmObj().getSubEdificioGeneral(), vt.getOtObj().getBasicaIdTecnologia());
                                if (tecnologiaSubMgls != null && tecnologiaSubMgls.getNodoId()!= null) {
                                    if (!tecnologiaSubMgls.getNodoId().getNodCodigo().contains("NFI")) {
                                        subEdi.setNodo(tecnologiaSubMgls.getNodoId());
                                    } else {
                                       //si el nodo es de tipo NFI se georeferencia 
                                        nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                                        if (nodoCobertura != null) {
                                            subEdi.setNodo(nodoCobertura);
                                        }
                                    }
                                } else {
                                     tecnologiaSubMgls = cmtTecnoSubMglFacadeLocal.
                                             findBySubEdificioTecnologia(vt.getOtObj().getCmObj().getSubEdificioGeneral(), vt.getOtObj().getBasicaIdTecnologia());
                                    //si no se encuentra el nodo en tecnologia_sub se geoferencia
                                    nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                                    if (nodoCobertura != null) {
                                        subEdi.setNodo(nodoCobertura);
                                    }
                                }
                               
                               
                            }
                            subEdi = subEdificiosVtFacadeLocal.update(subEdi,usuarioVT, perfilVt);
                        }
                    }
                    
                    subEdificioVtList = subEdificiosVtFacadeLocal.findByVt(vt);
                    //metodo que carga variables nuevas de las listas de los niveles
                    cargarCamposNuevos();
                    subEdificioVtList = posSetListas(subEdificioVtList);

                    if (subEdificioVtList.size() > 0) {
                        boolean hayErrorSubEdi = false;
                        for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                            if (subEdi.getNumeroPisosCasas() == 0) {
                                hayErrorSubEdi = true;
                            }
                        }
                        VisitaTecnicaBean vtMglBean =
                                (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
                        vtMglBean.setHayErrorSubEdi(hayErrorSubEdi);
                    }

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));
                } else {
                    subEdificioVtList = posSetListas(subEdificioVtList);
                }

                
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
            String msn2 = "Error al editar subedificios: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
        }
        return null;
    }
    
    
    
    public void cargarCamposNuevos(){
        for (CmtSubEdificiosVt subModVt : subEdificioVtList) {

            if (subModVt.getTipoNivel1() != null) {
                if (subModVt.getTipoNivel1().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                    subModVt.setOptipoNivel1(subModVt.getTipoNivel1().getBasicaId());
                }
            }

            if (subModVt.getTipoNivel2() != null) {
                if (subModVt.getTipoNivel2().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                    subModVt.setOptipoNivel2(subModVt.getTipoNivel2().getBasicaId());
                }
            }
            if (subModVt.getTipoNivel3() != null) {
                if (subModVt.getTipoNivel3().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                    subModVt.setOptipoNivel3(subModVt.getTipoNivel3().getBasicaId());
                }
             }
            if (subModVt.getTipoNivel4() != null) {
                if (subModVt.getTipoNivel4().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
                    subModVt.setOptipoNivel4(subModVt.getTipoNivel4().getBasicaId());
                }
            }
           

        }
    }

    public boolean validarDireccionCalleRR(CmtSubEdificiosVt subEdi, int linea) throws ApplicationException {
        GeograficoPoliticoMgl multiorigen;
        ValidadorDireccionBean validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
        boolean valida = false;
        DetalleDireccionEntity detalleDireccionEntity;
        if (subEdi.getDireccionSubEdificio() != null) {
            detalleDireccionEntity = validadorDireccionBean.getResponseValidarDireccionDto().getDrDireccion().convertToDetalleDireccionEntity();
            String calleRR = subEdi.getCalleRr() + " " + "EN " + " ";
            String numeroUnidadRR = "";
            String numeroApartamentoRR = "";
            LOGGER.error("Inicio Validacion direccion");
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            multiorigen = geograficoPoliticoMglFacadeLocal.findCityByComundidad(subEdi.getVtObj().getOtObj().getCmObj().getCentroPoblado().getGeoCodigoDane());
            detalleDireccionEntity.setMultiOrigen(multiorigen.getGpoMultiorigen());
            ArrayList<String> arrDirRR = direccionRRManager.generarDirFormatoRR(detalleDireccionEntity);
            if (arrDirRR != null && arrDirRR.size() == 3) {
                calleRR += arrDirRR.get(0);
                numeroUnidadRR += arrDirRR.get(1);
                numeroApartamentoRR += arrDirRR.get(2);

                if (calleRR.trim().length() > 50) {
                    valida = false;
                    String msn = "Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "]" + " " + "Linea : " + linea;
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else if (numeroUnidadRR.trim().length() > 10) {
                    valida = false;
                    String msn = "El campo Número de Unidad ha superado el número máximo de caracteres en formato RR [" + numeroUnidadRR + "]" + " " + "Linea : " + linea;
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else if (numeroApartamentoRR.trim().length() > 10) {
                    valida = false;
                    String msn = "El campo Número de Apartamento ha superado el número máximo de caracteres en formato RR [" + numeroApartamentoRR + "]" + " " + "Linea : " + linea;
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    valida = true;
                }
            }
        } else {
            valida = true;
        }

        return valida;
    }

    private boolean validarNivelesSubEdVt(List<CmtSubEdificiosVt> subEdVtList) {
        int linea = 1;
        boolean isValidacionOk = true;
        int numDir = 0;
        try {//Revisamos los niveles para cargar el Objeto completo al SubEdificio

            for(CmtSubEdificiosVt subModVt : subEdificioVtList){
                
                if (subModVt.getOptipoNivel1() != null) {
                    CmtBasicaMgl OptipoNivel1 = basicaMglFacadeLocal.findById(subModVt.getOptipoNivel1());
                    subModVt.setOptipoNivel1(subModVt.getOptipoNivel1());
                    subModVt.setTipoNivel1(OptipoNivel1);
                }
                if (subModVt.getOptipoNivel2() != null) {
                    CmtBasicaMgl OptipoNivel2 = basicaMglFacadeLocal.findById(subModVt.getOptipoNivel2());
                     subModVt.setOptipoNivel2(subModVt.getOptipoNivel2());
                    subModVt.setTipoNivel2(OptipoNivel2);
                }
                if (subModVt.getOptipoNivel3() != null) {
                    CmtBasicaMgl OptipoNivel3 = basicaMglFacadeLocal.findById(subModVt.getOptipoNivel3());
                     subModVt.setOptipoNivel3(subModVt.getOptipoNivel3());
                    subModVt.setTipoNivel3(OptipoNivel3);
                }
                if (subModVt.getOptipoNivel4() != null) {
                    CmtBasicaMgl OptipoNivel4 = basicaMglFacadeLocal.findById(subModVt.getOptipoNivel4());
                     subModVt.setOptipoNivel4(subModVt.getOptipoNivel4());
                    subModVt.setTipoNivel4(OptipoNivel4);
                }
                
            }
            subEdificioVtList = preSetListas(subEdificioVtList);
                for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                    if (subEdi.getEstadoRegistro() == 1) {
                        if (subEdi.getTipoNivel1() != null
                                && subEdi.getTipoNivel1().getBasicaId() != null
                                && subEdi.getTipoNivel1().getNombreBasica() == null) {
                            CmtBasicaMgl level =
                                    basicaMglFacadeLocal.findById(subEdi.getTipoNivel1());
                             subEdi.setOptipoNivel1(subEdi.getOptipoNivel1());
                            subEdi.setTipoNivel1(level);
                        }
                        if (subEdi.getTipoNivel2() != null
                                && subEdi.getTipoNivel2().getBasicaId() != null
                                && subEdi.getTipoNivel2().getNombreBasica() == null) {
                            CmtBasicaMgl level =
                                    basicaMglFacadeLocal.findById(subEdi.getTipoNivel2());
                             subEdi.setOptipoNivel2(subEdi.getOptipoNivel2());
                            subEdi.setTipoNivel2(level);
                        }
                        if (subEdi.getTipoNivel3() != null
                                && subEdi.getTipoNivel3().getBasicaId() != null
                                && subEdi.getTipoNivel3().getNombreBasica() == null) {
                            CmtBasicaMgl level =
                                    basicaMglFacadeLocal.findById(subEdi.getTipoNivel3());
                            subEdi.setOptipoNivel3(subEdi.getOptipoNivel3());
                            subEdi.setTipoNivel3(level);
                        }
                        if (subEdi.getTipoNivel4() != null
                                && subEdi.getTipoNivel4().getBasicaId() != null
                                && subEdi.getTipoNivel4().getNombreBasica() == null) {
                            CmtBasicaMgl level =
                                    basicaMglFacadeLocal.findById(subEdi.getTipoNivel4());
                            subEdi.setOptipoNivel4(subEdi.getOptipoNivel4());
                            subEdi.setTipoNivel4(level);
                        }
                        if (subEdi.getTipoNivel5() != null
                                && subEdi.getTipoNivel5().getBasicaId() != null
                                && subEdi.getTipoNivel5().getNombreBasica() == null) {
                            CmtBasicaMgl level =
                                    basicaMglFacadeLocal.findById(subEdi.getTipoNivel5());
                             
                            subEdi.setTipoNivel5(level);
                        }
                    }
                }
            
                for (CmtSubEdificiosVt subEdi : subEdVtList) {
                    if (subEdi.getEstadoRegistro() == 1) {
                        String continuidad = "";

                        if (subEdi.getTipoNivel1() == null || subEdi.getTipoNivel1().getBasicaId() == null) {
                            String msn2 = "Se debe seleccionar el primer nivel del eficio.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            isValidacionOk = false;
                        } else if (subEdi.getValorNivel1() == null || subEdi.getValorNivel1().isEmpty()) {
                            isValidacionOk = false;
                            String msn2 = "Se debe ingresar un valor para el nivel 1, para la linea("
                                    + linea + ")";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        } else {
                            continuidad += "1";
                        }

                        if (subEdi.getTipoNivel2() != null && subEdi.getTipoNivel2().getBasicaId() != null) {
                            if (subEdi.getValorNivel2() == null || subEdi.getValorNivel2().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Se debe ingresar un valor para el nivel 2, para la linea("
                                        + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            } else {
                                continuidad += "2";
                            }
                        } else {
                            if (subEdi.getValorNivel2() != null && !subEdi.getValorNivel2().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Los valores deben especificar un tipo en el Nivel 2 (" + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }
                        }

                        if (subEdi.getTipoNivel3() != null && subEdi.getTipoNivel3().getBasicaId() != null) {
                            if (subEdi.getValorNivel3() == null || subEdi.getValorNivel3().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Se debe ingresar un valor para el nivel 3, para la linea(" + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            } else {
                                continuidad += "3";
                            }
                        } else {
                            if (subEdi.getValorNivel3() != null && !subEdi.getValorNivel3().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Los valores deben especificar un tipo en el Nivel 3 (" + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }
                        }

                        if (subEdi.getTipoNivel4() != null && subEdi.getTipoNivel4().getBasicaId() != null) {
                            if (subEdi.getValorNivel4() == null || subEdi.getValorNivel4().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Se debe ingresar un valor para el nivel 4, para la linea(" + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            } else {
                                continuidad += "4";

                            }
                        } else {
                            if (subEdi.getValorNivel4() != null && !subEdi.getValorNivel4().isEmpty()) {
                                isValidacionOk = false;
                                String msn2 = "Los valores deben especificar un tipo en el Nivel 4 (" + linea + ")";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }
                        }
                        if (subEdi.getDireccionSubEdificio() != null && !subEdi.getDireccionSubEdificio().isEmpty()) {
                            if (subEdi.isClickValidarDir()) {
                                continuidad += "5";

                            } else {
                                if (subEdi.getCalleRr() != null && subEdi.getUnidadRr() != null) {
                                    continuidad += "5";
                                } else {
                                    isValidacionOk = false;
                                    String MensajeComplementa;
                                    MensajeComplementa = "Ud no ha validado ".concat(" la direccion ingresada");
                                    String msn2 = "Debe validar la direccion para la linea(" + linea + "). ".
                                            concat(MensajeComplementa);
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                                }

                            }
                        } else {
                            if (!subEdi.isClickValidarDir()) {
                                continuidad += "5";

                            } else {
                                isValidacionOk = false;
                                String MensajeComplementa = "";
                                MensajeComplementa = "Ud no ha validado ".concat(" la direccion ingresada");
                                String msn2 = "Debe validar la direccion para la linea(" + linea + "). ".
                                        concat(MensajeComplementa);
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }
                        }
                        if (!"12345".contains(continuidad) && !"156".contains(continuidad) && !"1256".contains(continuidad)) {
                            isValidacionOk = false;
                            String msn2 = "Los niveles deben llenarse en orden para la linea(" + linea + ")";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        }
                        if (subEdi.getDireccionSubEdificio() != null
                                && !subEdi.getDireccionSubEdificio().isEmpty()) {
                            numDir += 1;
                        }

                        if (subEdi.getNumeroPisosCasas() == 0) {
                            isValidacionOk = false;
                            String msn2 = "Se debe especificar un valor diferente a cero para el numero de pisos (" + linea + ")";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        }
                        linea += 1;
                    }
                }


            if (!isValidacionOk) {
                String msn2 = "No se ha guardado ningun registros";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }

            //Validamos que no se creen dos subEdificios con el mismo nombre
            boolean isEqualName = false;
                for (int i = 0; i < subEdVtList.size() && !isEqualName; i++) {
                    for (int j = 0; j < subEdVtList.size() && !isEqualName; j++) {
                        if (i != j) {
                            if (subEdVtList.get(i).getNombreFromLevels() != null
                                    && subEdVtList.get(j).getNombreFromLevels() != null
                                    && subEdVtList.get(i).getNombreFromLevels().
                                    equalsIgnoreCase(subEdVtList.get(j).getNombreFromLevels())) {
                                isEqualName = true;
                            }
                        }
                    }
                }
           
            if (isEqualName) {
                isValidacionOk = false;
                String msn2 = "Existen SubEdificios con el mismo nombre";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de validar los niveles del subedificio. EX000: " + e.getMessage(), e);
            String msn2 = "Ocurrio un error validando los SubEdificios: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            isValidacionOk = false;
        }
        return isValidacionOk;
    }

    public ResponseCreateHhppDto consolidarSubEdi() throws ApplicationException, ExceptionDB, IOException {
        try {
            cmtVisitaTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVt);
            if (otMglBean.tieneAgendasPendientes(ot)) {
                Integer linea = 0;
                // ajuste nombre RR para casos especiales 
                TecnologiaHabilitadaVtBean tecnologiaHabilitadaVtBean = (TecnologiaHabilitadaVtBean) JSFUtil.getBean("tecnologiaHabilitadaVtBean");
                for (CmtHhppVtMgl cmtHhppVtMgl : tecnologiaHabilitadaVtBean.getListHhppBySubEdificio()) {
                    tecnologiaHabilitadaFacadeLocal.updateCm(cmtHhppVtMgl);
                }
                List<CmtHhppVtMgl> listaPanel = tecnologiaHabilitadaFacadeLocal.findByVt(vt);
                subEdificioVtList = subEdificiosVtFacadeLocal.findByVt(vt);
                // 
                boolean nodo = false;
                for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                    if (subEdi.getEstadoRegistro() == 1) {
                        //valbuenayf inicio ajuste campo nodo
                        if (subEdi.getNodo() == null) {
                            nodo = true;
                        }
                    }
                    linea += 1;
                }
                // vaidacion para no dejar en blanco el campo NombreValidoRR
                boolean otrosVacio = false;
                for (CmtHhppVtMgl cmtHhppVtMgl : listaPanel) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                        if ((cmtHhppVtMgl.getNombreValidoRR() == null && cmtHhppVtMgl.getValorNivel5() != null
                                && cmtHhppVtMgl.getValorNivel5().length() > 10 && cmtHhppVtMgl.getProcesado() == 0)) {
                            otrosVacio = true;

                        }
                    }
                }
                boolean otroValor = false;
                for (CmtHhppVtMgl cmtHhppVtMgl : listaPanel) {
                    if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                        if ((cmtHhppVtMgl.getNombreValidoRR() != null && cmtHhppVtMgl.getProcesado() == 0)) {
                            otroValor = true;

                        }
                    }
                }
                if (otroValor == true) {
                    String msn = "Ud no ha seleccionado el nivel Otros para el valor RR";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn, null));
                }

                if (otrosVacio == false && otroValor == false) {
                    if (nodo == false) {
                        responseCreateHhppDto = cmtVisitaTecnicaMglFacadeLocal.consolidacionSubEdiVt(subEdificioVtList);
                        subEdificioVtList = cmtVisitaTecnicaMglFacadeLocal.findById(vt).getListCmtSubEdificiosVt();
                        TecnologiaHabilitadaVtBean hhppVtBean = JSFUtil.getSessionBean(TecnologiaHabilitadaVtBean.class);
                        if (responseCreateHhppDto.isCreacionExitosa()) {
                            hhppVtBean.setListHhppVtDto(new ArrayList<>());
                            hhppVtBean.cargarGridTorres();
                            hhppVtBean.cargarGridCasas();
                            hhppVtBean.cargarTodos();
                            String msn2 = "Proceso Exitoso ";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));

                        } else {
                            hhppVtBean.cargarGridTorres();
                            hhppVtBean.cargarGridCasas();
                            hhppVtBean.cargarTodos();
                            String msg = responseCreateHhppDto.getValidationMessages() != null && !responseCreateHhppDto.getValidationMessages().isEmpty() ? responseCreateHhppDto.getValidationMessages().get(0) : "La creación no fue exitosa.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        }
                    } else {
                        String msn = "Ud no ha ingresado valor del nodo en la pestaña Costos";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn, null));
                    }
                } else {
                    String msn2 = "Ud no ha ingresado datos en el campo valor RR, No se han procesado los Hhpp ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn2, null));
                }
            } else {
                String msg = "La orden de trabajo No: "+ot.getIdOt()+" tiene agendas pendientes por cerrar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg, ""));
                return null;
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de consolidar el subedificio. EX000: " + e.getMessage(), e);
            String msn2 = "Ocurrio un error Consolidando la informacion en RR: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
        }
        return responseCreateHhppDto;
    }

    public List<CmtSubEdificiosVt> preSetListas(List<CmtSubEdificiosVt> subEdificioVtList) {
        try {
            if (subEdificioVtList != null && !subEdificioVtList.isEmpty()) {
                for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                    subEdi = preProcessEdificio(subEdi);
                }
            }
  
            return subEdificioVtList;
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de preestablecer las listas: " + e.getMessage(), e);
        }
        return null;
    }

    private CmtSubEdificiosVt preProcessEdificio(CmtSubEdificiosVt subEdi) throws ApplicationException {
        
        if (subEdi.getTipoNivel1() != null
                && subEdi.getTipoNivel1().getBasicaId() != null
                && subEdi.getTipoNivel1().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
            subEdi.setTipoNivel1(basicaMglFacadeLocal.findById(subEdi.getTipoNivel1().getBasicaId()));
        } else {
            subEdi.setTipoNivel1(null);
        }
        if (subEdi.getTipoNivel2() != null
                && subEdi.getTipoNivel2().getBasicaId() != null
                && subEdi.getTipoNivel2().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
            subEdi.setTipoNivel2(basicaMglFacadeLocal.findById(subEdi.getTipoNivel2().getBasicaId()));
        } else {
            subEdi.setTipoNivel2(null);
        }
        if (subEdi.getTipoNivel3() != null
                && subEdi.getTipoNivel3().getBasicaId() != null
                && subEdi.getTipoNivel3().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
            subEdi.setTipoNivel3(basicaMglFacadeLocal.findById(subEdi.getTipoNivel3().getBasicaId()));
        } else {
            subEdi.setTipoNivel3(null);
        }
        if (subEdi.getTipoNivel4() != null
                && subEdi.getTipoNivel4().getBasicaId() != null
                && subEdi.getTipoNivel4().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
            subEdi.setTipoNivel4(basicaMglFacadeLocal.findById(subEdi.getTipoNivel4().getBasicaId()));
        } else {
            subEdi.setTipoNivel4(null);
        }
        if (subEdi.getTipoNivel5() != null
                && subEdi.getTipoNivel5().getBasicaId() != null
                && subEdi.getTipoNivel5().getBasicaId().compareTo(BigDecimal.ZERO) != 0) {
            subEdi.setTipoNivel5(basicaMglFacadeLocal.findById(subEdi.getTipoNivel5().getBasicaId()));
        } else {
            subEdi.setTipoNivel5(null);
        }
        return subEdi;
    }

    public List<CmtSubEdificiosVt> posSetListas(List<CmtSubEdificiosVt> subEdificioVtList) {
        try {
            for (CmtSubEdificiosVt subEdi : subEdificioVtList) {
                if (subEdi.getTipoNivel1() == null || subEdi.getTipoNivel1().getBasicaId() == null) {
                    subEdi.setTipoNivel1(new CmtBasicaMgl());
                    subEdi.getTipoNivel1().setBasicaId(BigDecimal.ZERO);
                }
                if (subEdi.getTipoNivel2() == null || subEdi.getTipoNivel2().getBasicaId() == null) {
                    subEdi.setTipoNivel2(new CmtBasicaMgl());
                    subEdi.getTipoNivel2().setBasicaId(BigDecimal.ZERO);
                }
                if (subEdi.getTipoNivel3() == null || subEdi.getTipoNivel3().getBasicaId() == null) {
                    subEdi.setTipoNivel3(new CmtBasicaMgl());
                    subEdi.getTipoNivel3().setBasicaId(BigDecimal.ZERO);
                }
                if (subEdi.getTipoNivel4() == null || subEdi.getTipoNivel4().getBasicaId() == null) {
                    subEdi.setTipoNivel4(new CmtBasicaMgl());
                    subEdi.getTipoNivel4().setBasicaId(BigDecimal.ZERO);
                }
                if (subEdi.getTipoNivel5() == null || subEdi.getTipoNivel5().getBasicaId() == null) {
                    subEdi.setTipoNivel5(new CmtBasicaMgl());
                    subEdi.getTipoNivel5().setBasicaId(BigDecimal.ZERO);
                }

                //valbuenayf inicio  ajuste campo nodo
                if (subEdi.getNodo() != null && subEdi.getNodo().getNodId() != null) {
                    subEdi.setCodigoNodo(subEdi.getNodo().getNodCodigo());
            }
//                }
                //valbuenayf fin  ajuste campo nodo
            }
            return subEdificioVtList;
        } catch (Exception e) {
            LOGGER.error("Error al momento posterior del establecimiento de las listas: " + e.getMessage(), e);
        }
        return null;
    }

    public void deleteSubEdificioVt(CmtSubEdificiosVt cmtSubEdificiosVt) {
        try {
            cmtSubEdificiosVt = preProcessEdificio(cmtSubEdificiosVt);
            List<CmtHhppVtMgl> listTecHabilitadasSubEdificio =
                    tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(cmtSubEdificiosVt);

            if (listTecHabilitadasSubEdificio.size() > 0) {
                String msn2 = "No es posible borrar el Sub-Edificion, debido a que ya tiene HHPP asociados.";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            } else {
                if (subEdificiosVtFacadeLocal.delete(cmtSubEdificiosVt)) {
                    subEdificioVtList = subEdificiosVtFacadeLocal.findByVt(vt);
                    subEdificioVtList = posSetListas(subEdificioVtList);
                    FacesContext.getCurrentInstance().addMessage(
                            null, new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsCM.MSN_PROCESO_EXITOSO, ""));
                    FacesUtil.navegarAPagina("/view/MGL/CM/tabsVt/multiedificio.jsf");
                }
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando el registro : " + e.getMessage();
            LOGGER.error(msn, e);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }

    public boolean validarNodo(String codigoNodo, BigDecimal centroPob, BigDecimal tecnologia) {
        boolean existe = false;
        try {
            if (nodoMglFacadeLocal.findByCodigoNodo(codigoNodo, centroPob, tecnologia) != null) {
                existe = true;
            }

        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar el nodo: " + ex.getMessage(), ex);
        }
        return existe;

    }

    public void cambiarTipoSubEdificio(String cambio) throws ApplicationException {
        
        String formTabSeleccionado;

            if (cambio.contains(Constant.PROCESO_CAMBIO_SUBEDIFICIO)) {
                // CAMBIO DE MULTIEDIFICIO A UNICO

                if (existeOtAbiertas()) {
                    String msn2 = "No es posible hacer este cambio, existen Ordenes de trabajo Abiertas "
                            + "Proceda a Cerrarlas o Finalizarlas ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                } else if (existeSubedificiosVtActivos()) {
                    String msn2 = "No es posible hacer este cambio, existen Subedificios VT , "
                            + "Proceda a Eliminar los SubEdificios vt Manualmente";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                } 
                else if (existeSubedificiosActivos()) {
                    String msn2 = "No es posible hacer este cambio, existen Subedificios "
                            + "Proceda a Eliminar los SubEdificios Manualmente ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                }

            } else {
                // CAMBIO DE UNICO EDIFICIO A MULTIEDIFICIO
                if(subEdificioVtList != null && subEdificioVtList.size() == 1){
                    if (subEdificioVtList.get(0) != null 
                            && subEdificioVtList.get(0).getSubEdificioObj() != null 
                            && subEdificioVtList.get(0).getSubEdificioObj().getEstadoSubEdificioObj() != null
                            && subEdificioVtList.get(0).getSubEdificioObj().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                            && subEdificioVtList.get(0).getSubEdificioObj().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA)) {
                        String msn2 = "Ud no puede modificar el edificio general, eliminar para continuar con el proceso ";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    }
                }else{
                    if(existeHhpp()){
                        String msn2 = "No es posible hacer este cambio, existen HHPP asociados al Subedificio General";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    }else if(existeOtAbiertas()) {
                        String msn2 = "No es posible hacer este cambio, existen Ordenes de trabajo Abiertas "
                                + "Proceda a Cerrarlas o Finalizarlas ";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    }  else {
                        if (cambiarTiposubedificio(new BigDecimal(50))) {
                            String msn2 = "Se ha modificado el SubEdificio con exito ";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            cambioAUni = true;
                            disableButtonChangeSub = true;
                            formTabSeleccionado = "/view/MGL/CM/tabsVt/multiedificio";
                            FacesContext contextGeneral = FacesContext.getCurrentInstance();
                            NavigationHandler navigationHandlerGeneral = contextGeneral.getApplication().getNavigationHandler();
                            navigationHandlerGeneral.handleNavigation(contextGeneral, null, formTabSeleccionado + "?faces-redirect=true");
                        }else{
                             cambioAUni = false;
                            String msn2 = "No es posible hacer este cambio, No hubo respuesta del servicio ";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                        }
                    }
                }
   
            }
    }

        public boolean cambiarTiposubedificio(BigDecimal cambioA) {
        boolean cambio = false;
        try {

            CmtSubEdificioMgl cmtSubEdificioMglActualizado = null;
            CmtSubEdificioMgl cmtSubEdificioMgl = vt.getOtObj().getCmObj().getSubEdificioGeneral();
            cmtSubEdificioMgl.setEstadoSubNuevo(cambioA);
            cmtSubEdificioMgl.setCambioestado("Y");
            cmtSubEdificioMglActualizado = subEdificioMglFacadeLocal.update(cmtSubEdificioMgl);
           if (cmtSubEdificioMglActualizado != null) {
                cambio = true;
                List<CmtTecnologiaSubMgl> listaTec = cmtTecnoSubMglFacadeLocal.findTecnoSubBySubEdi(vt.getOtObj().getCmObj().getSubEdificioGeneral());
                //A las tecnologias que tenga el edificio principal les pone el estado MULTIEDIFICIO o el estado que le haya llegado menos a RED FO
                if (listaTec != null && !listaTec.isEmpty()) {
                   for (CmtTecnologiaSubMgl tecno : listaTec) {
                       if (cambioA.compareTo(basicaMglFacadeLocal.findByCodigoInternoApp(
                               Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId()) == 0) {
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.DTH)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);

                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                               tecno.setBasicaIdEstadosTec(cmtSubEdificioMglActualizado.getEstadoSubEdificioObj());
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                       } else {

                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.EST_INI_HFC_UNI);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.EST_INI_HFC_BID);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.DTH)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.EST_INI_DTH);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }

                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.EST_INI_FIBRA_FTTTH);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.EST_INI_FIBRA_OP_GPON);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.HFC_BID);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);

                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.HFC_BID);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                           if (tecno.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                               CmtBasicaMgl estIni = basicaMglFacadeLocal.findByCodigoInternoApp(Constant.HFC_BID);
                               if (estIni != null) {
                                   tecno.setBasicaIdEstadosTec(estIni);
                               }
                               cmtTecnoSubMglFacadeLocal.updateTecnoSub(tecno, usuarioVT, perfilVt);
                           }
                       }
        

                    }
                }
            }else{
                CmtSubEdificioMgl estadoOriginal = subEdificioMglFacadeLocal.findById(cmtSubEdificioMgl);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(estadoOriginal.getEstadoSubEdificioObj());
                cmtSubEdificioMgl.setCambioestado("N");
                cambio = false;
            }


        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de cambiar el tipo de subedificio: " + ex.getMessage(), ex);
        }
        return cambio;

    }

    public boolean existeSubedificiosActivos() {
        boolean existe = false;
        String formTabSeleccionado;
        try {
            List<CmtSubEdificioMgl> listSubEdificios;
            listSubEdificios = subEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(vt.getOtObj().getCmObj());
            if (listSubEdificios.size() > 1) {
                // validar que existen varios subedificios a eliminar
                for (CmtSubEdificioMgl cmtSubEdificioMgl : listSubEdificios) {
                    if (cmtSubEdificioMgl != null 
                            && cmtSubEdificioMgl.getEstadoSubEdificioObj() != null 
                            && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                            && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA)) {
                        existe = true;
                        break;
                    }
                }
            } else {
                
                // CAMBIO DE MULTIEDIFICIO EDIFICIO A  UNICO
                if (cambiarTiposubedificio(new BigDecimal(21))) {
                    String msn2 = "Se actualizo el subedificio con exito ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, null));
                   
                    disableButtonChangeSub = true;
                    formTabSeleccionado = "/view/MGL/CM/tabsVt/multiedificio";
                    FacesContext contextGeneral = FacesContext.getCurrentInstance();
                    NavigationHandler navigationHandlerGeneral = contextGeneral.getApplication().getNavigationHandler();
                    navigationHandlerGeneral.handleNavigation(contextGeneral, null, formTabSeleccionado + "?faces-redirect=true");
                } else {
                    String msn2 = "Hubo un error al actualizar el subedificio ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                }

            }


        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la existencia de subedificios activos: " + ex.getMessage(), ex);
        }
        return existe;

    }

    public boolean existeHhpp() {
        boolean existe = false;
        try {
            List<CmtSubEdificioMgl> listSubEdificios;
            List<HhppMgl> listHhpp;
            listSubEdificios = subEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(vt.getOtObj().getCmObj());
            if (listSubEdificios != null && listSubEdificios.size() > 1) {
                        for (CmtSubEdificioMgl cmtSubEdificioMgl : listSubEdificios) {
                            listHhpp = hhppMglFacadeLocal.findHhppBySubEdificioId(cmtSubEdificioMgl.getSubEdificioId());
                            if (listHhpp != null) {
                                if (!listHhpp.isEmpty()) {
                                    existe = true;
                                    break;
                                }
                            } else {
                                existe = false;
                            }
                        }
            }
             existe = false;

        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la existencia del HHPP: " + ex.getMessage(), ex);
        }
        return existe;

    }

    public boolean existeSubedificiosVtActivos() {
        boolean existe = false;
        try {
            List<CmtSubEdificiosVt> listSubEdificiosVt = null;
            listSubEdificiosVt = subEdificiosVtFacadeLocal.findByVt(vt);
            if (listSubEdificiosVt != null && !listSubEdificiosVt.isEmpty()) {
                existe = true;
            } else {
                existe = false;
            }

        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la existencia de subedificios VT activos: " + ex.getMessage(), ex);
        }
        return existe;

    }

    public boolean existeTecnologiaAsociada() {
        boolean existe = false;
        // preguntar si debo eliminar las tecnologias 
        try {
            List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglList;
            cmtTecnologiaSubMglList = cmtTecnologiaSubMglFacadeLocal.findTecnoSubBySubEdi(vt.getOtObj().getCmObj().getSubEdificioGeneral());
            if (cmtTecnologiaSubMglList != null && !cmtTecnologiaSubMglList.isEmpty()) {
                for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtTecnologiaSubMglList) {
                    if (cmtTecnologiaSubMgl != null 
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null 
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp() != null
                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SIN_VISITA_TECNICA)) {
                        cmtTecnoSubMglFacadeLocal.deleteSubEdificioTecnologia(cmtTecnologiaSubMgl, usuarioVT, perfilVt);
                    } else {
                        existe = true;

                    }
                }
            } else {
                existe = false;
            }

        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la existencia de tecnología asiciada: " + ex.getMessage(), ex);
        }
        return existe;

    }

    public boolean existeOtAbiertas() {
        boolean existe = false;
        try {
            List<CmtOrdenTrabajoMgl> listaOrdenesACtivas;
            listaOrdenesACtivas = ordenTrabajoMglFacadeLocal.ordenesTrabajoActivas(vt.getOtObj().getCmObj());
            if (listaOrdenesACtivas != null && !listaOrdenesACtivas.isEmpty()) {
                for (CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl : listaOrdenesACtivas) {
                    if (cmtOrdenTrabajoMgl.getEstadoInternoObj().getBasicaId().equals(basicaMglFacadeLocal.findByCodigoInternoApp(
                            Constant.BASICA_EST_INT_CANCELADO).getBasicaId())) {
                        if (!cmtOrdenTrabajoMgl.getIdOt().equals(vt.getOtObj().getIdOt())) {
                            existe = false;
                        }
                    } else {
                        if(!cmtOrdenTrabajoMgl.getIdOt().equals(vt.getOtObj().getIdOt()) &&
                                !cmtOrdenTrabajoMgl.getBasicaIdTecnologia().getIdentificadorInternoApp()
                                        .equalsIgnoreCase(Constant.RED_FO)) {
                            existe = true; 
                        } else {
                            existe = false;
                        }
                    }
                }
            } else {
                existe = false;
            }

        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la existencia de OT abiertas: " + ex.getMessage(), ex);
        }
        return existe;

    }
    
    public void validarTecnologiaOrden(CmtSubEdificioMgl subSeleted)
            throws ApplicationException {
        CmtTecnologiaSubMgl tecnologiaSubMgls;
        tecnologiaSubMgls = cmtTecnoSubMglFacadeLocal.findBySubEdificioTecnologia(subSeleted, vt.getOtObj().getBasicaIdTecnologia());
        try {
            NodoMgl nodoCobertura = null;
            if (tecnologiaSubMgls != null
                    && tecnologiaSubMgls.getTecnoSubedificioId() != null) {
                LOGGER.info("Tecnologias iguales");
                // Si las tecnologias son iguales y el nodo de la cm comienza por NFI se consulta el geo 
                if (tecnologiaSubMgls.getNodoId() != null) {
                    if (tecnologiaSubMgls.getNodoId().getNodCodigo().startsWith("NFI")) {
                        nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                    } else {
                        nodoCobertura = tecnologiaSubMgls.getNodoId();
                    }
                }
                CmtSubEdificiosVt subEdificioVtCreated
                        = subEdificiosVtFacadeLocal.creaSubEdVtfromSubEd(subSeleted, vt, nodoCobertura);
                if (subEdificioVtCreated != null
                        && subEdificioVtCreated.getIdEdificioVt() != null) {
                    subEdificioVtList.add(subEdificioVtCreated);
                    subEdificioVtList = posSetListas(subEdificioVtList);
                    subEdificioSinVtList.remove(subSeleted);
                }
            } else {
                if (tecnologiaSubMgls != null
                        && tecnologiaSubMgls.getTecnoSubedificioId() == null) {
                    LOGGER.info("Subedificio sin tecnologia");
                    nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                } else {
                    LOGGER.info("Tecnologias distintas se consulta el geo para traer nodo");
                    nodoCobertura = nodoMglFacadeLocal.consultaGeo(vt.getOtObj());
                }

                if (nodoCobertura != null) {
                    CmtSubEdificiosVt subEdificioVtCreated
                            = subEdificiosVtFacadeLocal.creaSubEdVtfromSubEd(subSeleted, vt, nodoCobertura);
                    if (subEdificioVtCreated != null
                            && subEdificioVtCreated.getIdEdificioVt() != null) {
                        subEdificioVtList.add(subEdificioVtCreated);
                        subEdificioVtList = posSetListas(subEdificioVtList);
                        subEdificioSinVtList.remove(subSeleted);
                    }
                } else {
                    LOGGER.error("No existe nodo de cobertura para la tecnologia de la Ot");
                    CmtSubEdificiosVt subEdificioVtCreated
                            = subEdificiosVtFacadeLocal.creaSubEdVtfromSubEd(subSeleted, vt, null);
                    if (subEdificioVtCreated != null
                            && subEdificioVtCreated.getIdEdificioVt() != null) {
                        subEdificioVtList.add(subEdificioVtCreated);
                        subEdificioVtList = posSetListas(subEdificioVtList);
                        subEdificioSinVtList.remove(subSeleted);
                    }
                }
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Ocurrió un error al momento de validar la tecnologia de la OT : " + ex.getMessage(), ex);
        }


    }

    public CmtSubEdificiosVt getSubEdificioVt() {
        return subEdificioVt;
    }

    public void setSubEdificioVt(CmtSubEdificiosVt subEdificioVt) {
        this.subEdificioVt = subEdificioVt;
    }

    public List<CmtSubEdificiosVt> getSubEdificioVtList() {
        return subEdificioVtList;
    }

    public void setSubEdificioVtList(List<CmtSubEdificiosVt> subEdificioVtList) {
        this.subEdificioVtList = subEdificioVtList;
    }

    public CmtTipoBasicaMgl getTipoSubEdificio() {
        return tipoSubEdificio;
    }

    public void setTipoSubEdificio(CmtTipoBasicaMgl tipoSubEdificio) {
        this.tipoSubEdificio = tipoSubEdificio;
    }

    public List<CmtBasicaMgl> getTipoSubEdificioList() {
        return tipoSubEdificioList;
    }

    public void setTipoSubEdificioList(List<CmtBasicaMgl> tipoSubEdificioList) {
        this.tipoSubEdificioList = tipoSubEdificioList;
    }

    public CmtBasicaMgl getTipoSubEdi1() {
        return tipoSubEdi1;
    }

    public void setTipoSubEdi1(CmtBasicaMgl tipoSubEdi1) {
        this.tipoSubEdi1 = tipoSubEdi1;
    }

    public CmtBasicaMgl getTipoSubEdi2() {
        return tipoSubEdi2;
    }

    public void setTipoSubEdi2(CmtBasicaMgl tipoSubEdi2) {
        this.tipoSubEdi2 = tipoSubEdi2;
    }

    public CmtBasicaMgl getTipoSubEdi3() {
        return tipoSubEdi3;
    }

    public void setTipoSubEdi3(CmtBasicaMgl tipoSubEdi3) {
        this.tipoSubEdi3 = tipoSubEdi3;
    }

    public CmtBasicaMgl getTipoSubEdi4() {
        return tipoSubEdi4;
    }

    public void setTipoSubEdi4(CmtBasicaMgl tipoSubEdi4) {
        this.tipoSubEdi4 = tipoSubEdi4;
    }

    public CmtBasicaMgl getTipoSubEdi5() {
        return tipoSubEdi5;
    }

    public void setTipoSubEdi5(CmtBasicaMgl tipoSubEdi5) {
        this.tipoSubEdi5 = tipoSubEdi5;
    }

    public String getValorNivel1() {
        return valorNivel1;
    }

    public void setValorNivel1(String valorNivel1) {
        this.valorNivel1 = valorNivel1;
    }

    public String getValorNivel2() {
        return valorNivel2;
    }

    public void setValorNivel2(String valorNivel2) {
        this.valorNivel2 = valorNivel2;
    }

    public String getValorNivel3() {
        return valorNivel3;
    }

    public void setValorNivel3(String valorNivel3) {
        this.valorNivel3 = valorNivel3;
    }

    public String getValorNivel4() {
        return valorNivel4;
    }

    public void setValorNivel4(String valorNivel4) {
        this.valorNivel4 = valorNivel4;
    }

    public String getValorNivel5() {
        return valorNivel5;
    }

    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    public Integer getCantidadSubEdificio() {
        return cantidadSubEdificio;
    }

    public void setCantidadSubEdificio(Integer cantidadSubEdificio) {
        this.cantidadSubEdificio = cantidadSubEdificio;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_SEIS_FILAS;
    }

    public List<CmtSubEdificioMgl> getSubEdificioSinVtList() {
        return subEdificioSinVtList;
    }

    public void setSubEdificioSinVtList(List<CmtSubEdificioMgl> subEdificioSinVtList) {
        this.subEdificioSinVtList = subEdificioSinVtList;
    }

    public boolean isHabilitaProcesoRr() {
        return habilitaProcesoRr;
    }

    public void setHabilitaProcesoRr(boolean habilitaProcesoRr) {
        this.habilitaProcesoRr = habilitaProcesoRr;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public boolean isCambioAUni() {
        return cambioAUni;
    }

    public void setCambioAUni(boolean cambioAUni) {
        this.cambioAUni = cambioAUni;
    }

    public boolean isDisableButtonChangeSub() {
        return disableButtonChangeSub;
    }
 
    public void setDisableButtonChangeSub(boolean disableButtonChangeSub) {
        this.disableButtonChangeSub = disableButtonChangeSub;
    }

    public BigDecimal getOptipoNivel1() {
        return optipoNivel1;
    }

    public void setOptipoNivel1(BigDecimal optipoNivel1) {
        this.optipoNivel1 = optipoNivel1;
    }

    public BigDecimal getOptipoNivel2() {
        return optipoNivel2;
    }

    public void setOptipoNivel2(BigDecimal optipoNivel2) {
        this.optipoNivel2 = optipoNivel2;
    }

    public BigDecimal getOptipoNivel3() {
        return optipoNivel3;
    }

    public void setOptipoNivel3(BigDecimal optipoNivel3) {
        this.optipoNivel3 = optipoNivel3;
    }

    public BigDecimal getOptipoNivel4() {
        return optipoNivel4;
    }

    public void setOptipoNivel4(BigDecimal optipoNivel4) {
        this.optipoNivel4 = optipoNivel4;
    }

    public ResponseCreateHhppDto getResponseCreateHhppDto() {
        return responseCreateHhppDto;
    }

    public void setResponseCreateHhppDto(ResponseCreateHhppDto responseCreateHhppDto) {
        this.responseCreateHhppDto = responseCreateHhppDto;
    }

    public boolean isNodoCargado() {
        return nodoCargado;
    }

    public void setNodoCargado(boolean nodoCargado) {
        this.nodoCargado = nodoCargado;
    }

    public List<CmtArchivosVtMgl> getLstArchivosVtMgls() {
        return lstArchivosVtMgls;
    }

    public void setLstArchivosVtMgls(List<CmtArchivosVtMgl> lstArchivosVtMgls) {
        this.lstArchivosVtMgls = lstArchivosVtMgls;
    }

}
