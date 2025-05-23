package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificiosVtFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 */
@ManagedBean(name = "tecnologiaHabilitadaVtBean")
@ViewScoped
public class TecnologiaHabilitadaVtBean implements Serializable {

    @EJB
    private CmtSubEdificiosVtFacadeLocal cmtSubEdificiosVtFacadeLocal;
    @EJB
    private CmtHhppVtMglFacadeLocal tecnologiaHabilitadaFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal CmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
     @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    @EJB
    private DrDireccionFacadeLocal drDireccionFacadeLocal;
    private List<OpcionIdNombre> ckList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> aptoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> complementoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(TecnologiaHabilitadaVtBean.class);
    private final String FORMULARIO = "HHPP";
    private CmtVisitaTecnicaMgl vt;
    private CmtSubEdificiosVt selectedSubEdificioVt;
    private List<CmtSubEdificiosVt> listCmtSubEdificiosVt;
    private SubEdificiosVtBean subEdificiosVtBean;
    private CmtHhppVtMgl tecnologiaHabilitada;
    private List<CmtHhppVtMgl> listTecnologiaHabilitada = new ArrayList<CmtHhppVtMgl>();
    private List<CmtHhppVtMgl> listHhppBySubEdificio = new ArrayList<CmtHhppVtMgl>();
    private List<CmtHhppVtMgl> listHhppByCasas = new ArrayList<CmtHhppVtMgl>();
    private List<CmtHhppVtMgl> listHhppCargue = new ArrayList<CmtHhppVtMgl>();
    private CmtSubEdificiosVt cmtSubEdificiosVtSub;
    private HhppVtDto hhppVtDto;
    private String tipoCreacionHhpp;
    private List<HhppVtDto> listHhppVtDto = new ArrayList<HhppVtDto>();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private List<ParametrosCalles> dirNivel5List;
    private List<ParametrosCalles> dirNivel6List;
    private SecurityLogin securityLogin;
    private Boolean validarEdicion;
    private List<CmtBasicaMgl> listaInterioresCasas;
    private Boolean validarCasas = false;
    private Boolean validarTorre = false;
    private Boolean validarCampus = false;
    private Boolean validarTorreManual = false;
    private Boolean validarCargaDatos = false;
    private String serverHost;
    private String direccionValidada;
    private ValidadorDireccionBean validadorDireccionBean;
    private List<HhppVtCasasDto> listHhppVtCasasDto = new ArrayList<HhppVtCasasDto>();
    private HhppVtCasasDto hhppDto = new HhppVtCasasDto();
    private boolean habilitarColums = false;
    private boolean habilitarColumsComp = false;
    private boolean quitarComplemeto = false;
    private ConfigurationAddressComponent configurationAddressComponent;
    private boolean direccionGeo = false;
    private boolean nombreRR = false;
    private boolean habilitaCampoTextoProcesoRr;
    private boolean habilitarCampoRR = false;
    private VisitaTecnicaBean visitaTecnicaBean;
    private String nuevoApartamentoRr;
    private OtMglBean otMglBean;
    private CmtOrdenTrabajoMgl ot;
    private List<TipoHhppMgl> listaTipoHhpp;
    private String tipoHhppMgl = null;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    private boolean validarFlujoOt = false;

    public TecnologiaHabilitadaVtBean() {
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
            VisitaTecnicaBean vtMglBean = JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            validadorDireccionBean = JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            this.vt = vtMglBean.getVt();
            if (vt == null) {
                String msn2 = "Error no hay una vt activa";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
            // limpiar la vaidacion que se dejo pendiente por guardar
            if (vtMglBean.getSubEdiModDir() != null) {
                vtMglBean.getSubEdiModDir().setClickValidarDir(false);

            }




        } catch (IOException e) {
            String msn2 = "Error al cargar Bean HHPP:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TecnologiaHabilitadaVtBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            this.otMglBean = (OtMglBean) JSFUtil.getSessionBean(OtMglBean.class);
            ot = otMglBean.getOrdenTrabajo();
            serverHost = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
            tipoCreacionHhpp = "Automatica";
            tecnologiaHabilitadaFacadeLocal.setUser(usuarioVT, perfilVt);
            dirNivel5List = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTO");
            dirNivel6List = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTOC");
            listCmtSubEdificiosVt = new ArrayList<>();
            listCmtSubEdificiosVt = cmtSubEdificiosVtFacadeLocal.findByVt(vt);
            listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
            listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findByVt(vt);
            listaInterioresCasas = cmtBasicaMglFacadeLocal.findByTipoBasica(
                    CmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_CASAS_PROPIA_DIRECCION));
            SubEdificiosVtBean cubEdificiosVtBean = (SubEdificiosVtBean) JSFUtil.getBean("subEdificiosVtBean");
            habilitaCampoTextoProcesoRr = cubEdificiosVtBean.isHabilitaProcesoRr();
            cargarListadosConfiguracion();
            ocultarPanel();
           
            Collections.sort(listCmtSubEdificiosVt, new Comparator<CmtSubEdificiosVt>() {
                @Override
                public int compare(CmtSubEdificiosVt a, CmtSubEdificiosVt b) {
                    return a.getIdEdificioVt().compareTo(b.getIdEdificioVt());
                }
            });


        } catch (ApplicationException e) {
            String msn2 = "Error al cargar listas basicas del bean:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarListas(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String selectSubEdificioVt() {
        try {

            if (otMglBean.tieneAgendasPendientes(ot)) {
                if ("Automatica".equals(tipoCreacionHhpp)) {

                    if (selectedSubEdificioVt.getTipoNivel1()
                            .getIdentificadorInternoApp().equals(Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION)) {
                        validarCasas = true;
                        validarCargaDatos = true;
                        validarTorre = false;
                        validarTorreManual = false;
                        habilitarColums = false;
                        direccionGeo = true;
                        cargarFormDireccionCasas();
                        cargarGridCasas();
                        limpiarPanelCargue();
                    } else {
                        if (selectedSubEdificioVt.getTipoNivel1()
                                .getIdentificadorInternoApp().equals(Constant.BASICA_CAMPUS_BATALLON)) {
                            cargarGridBat();
                        } else {
                            validadorDireccionBean = null;
                            cargarGridCasas();
                            cargarFormDireccionCasas();
                            validarCargaDatos = false;
                            validarTorreManual = true;
                            validarTorre = true;
                            validarCasas = false;
                            habilitarCampoRR = false;
                            direccionGeo = false;
                            cargarGridAutomatic();
                            listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);
                                 listaTipoHhpp = tipoHhppMglFacadeLocal.findAll();
                            List<String> listaSubEdificios = new ArrayList<String>();
                            for (CmtSubEdificiosVt cmtSubEdificiosVt : listCmtSubEdificiosVt) {
                                listaSubEdificios.add(cmtSubEdificiosVt.getNombreSubEdificio());
                            }
                        }
                    }
                } else if ("Manual".equals(tipoCreacionHhpp)) {

                    if (selectedSubEdificioVt.getTipoNivel1()
                            .getIdentificadorInternoApp().equals(Constant.BASICA_CAMPUS_BATALLON)) {
                        cargarGridBat();
                        cargarFormDireccionCasas();
                        limpiarPanelCargue();
                        habilitarCampoRR = true;
                    } else {
                        validadorDireccionBean = null;
                        // edificios con su propia direccion
                        cargarGridCasas();
                        cargarFormDireccionCasas();
                        cargarSubDireccion();
                        limpiarPanelCargue();
                        quitarComplemeto = true;
                        habilitarColumsComp = true;
                        direccionGeo = true;
                        habilitarColums = false;
                        validarCargaDatos = true;
                        validarTorreManual = false;
                        validarTorre = false;
                        validarCasas = true;
                        habilitarCampoRR = true;
                        cargarGridAutomatic();
                        listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);
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
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarListas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarListas(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void cargarGridAutomatic() {
        listHhppVtDto = new ArrayList<HhppVtDto>();
        if (listHhppVtDto.isEmpty()) {
            for (int i = 1; i <= selectedSubEdificioVt.getNumeroPisosCasas(); i++) {
                hhppVtDto = new HhppVtDto();
                hhppVtDto.setValorNivel5(Integer.toString(i));
                listHhppVtDto.add(hhppVtDto);
            }
        }
    }

    public void cargarGridBat() {
        validadorDireccionBean = null;
        validarCargaDatos = true;
        cargarFormDireccionCasas();
        cargarGridTorres();
        validarCasas = false;
        validarTorre = false;
        validarTorreManual = true;
        direccionGeo = false;
        quitarComplemeto = false;
        habilitarColumsComp = true;
        if (habilitaCampoTextoProcesoRr == true) {
            habilitarCampoRR = true;
        }
        habilitarColums = false;



    }

    public void cargarGridCasas() {
        try {
            if (selectedSubEdificioVt != null) {
                listHhppByCasas = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);
            }
        } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarGridCasas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarGridCasas(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarSubDireccion() {
        try {
            cmtSubEdificiosVtSub = cmtSubEdificiosVtFacadeLocal.findById(selectedSubEdificioVt.getIdEdificioVt());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarSubDireccion(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarSubDireccion(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarGridTorres() {
        try {
            if(selectedSubEdificioVt != null){
                 listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);
            }
           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarGridTorres(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarGridTorres(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarTodos() {
        try {
            listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findByVt(vt);

        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean:  cargarTodos(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean:  cargarTodos(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarFormDireccionCasas() {
        tecnologiaHabilitada = new CmtHhppVtMgl();
        tecnologiaHabilitada.setDireccionValidada("");
        tecnologiaHabilitada.setDireccionValidada(null);
        if (listHhppCargue.isEmpty()) {
            listHhppCargue.add(tecnologiaHabilitada);
        }

    }

    public void ocultarPanel() {

        validarCampus = false;
        validarCasas = false;
        validarTorre = false;
        validarTorreManual = true;

    }

    public void creationTypeHhpp(ValueChangeEvent event) {
        Object newValue = event.getNewValue();
        try {
            if ("Automatica".equals(newValue)) {
                listHhppVtDto = new ArrayList<HhppVtDto>();
                if (listHhppVtDto.isEmpty()) {
                    for (int i = 1; i <= selectedSubEdificioVt.getNumeroPisosCasas(); i++) {
                        hhppVtDto = new HhppVtDto();
                        hhppVtDto.setValorNivel5(Integer.toString(i));
                        listHhppVtDto.add(hhppVtDto);
                    }
                }
                cargarGridAutomatic();
                validarCargaDatos = false;
                listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);

            } else if ("Manual".equals(newValue)) {
                if (selectedSubEdificioVt != null) {
                    if (selectedSubEdificioVt.getTipoNivel1()
                            .getIdentificadorInternoApp().equals(Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION)) {
                        cargarFormDireccionCasas();
                        cargarGridCasas();
                        habilitarColums = false;
                        direccionGeo = true;
                    } else {

                        if (selectedSubEdificioVt.getTipoNivel1()
                                .getIdentificadorInternoApp().equals(Constant.BASICA_CAMPUS_BATALLON)) {
                            cargarGridBat();
                        } else {
                            cargarFormDireccionCasas();
                            habilitarColums = false;
                            tecnologiaHabilitada.setDireccionValidada("");
                            validarCargaDatos = false;
                            validarTorreManual = true;
                            validarTorre = true;
                            validarCasas = false;
                            if (listHhppVtDto.isEmpty()) {

                                for (int i = 1; i <= selectedSubEdificioVt.getNumeroPisosCasas(); i++) {
                                    hhppVtDto = new HhppVtDto();
                                    hhppVtDto.setValorNivel5(Integer.toString(i));
                                    listHhppVtDto.add(hhppVtDto);
                                }
                            }
                            listHhppBySubEdificio = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(selectedSubEdificioVt);
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: creationTypeHhpp(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: creationTypeHhpp(). " + e.getMessage(), e, LOGGER);
        }
    }

    public boolean validarLongitudNombreRR(String nombre) {
        if (nombre.trim().length() > 10) {
            nombreRR = true;
        }
        return true;
    }

    public String createHhppCasasManual() {
        try {
            CmtHhppVtMgl hhppVtValidada = new CmtHhppVtMgl();
            hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
            if (validateNivelesHhppVt(hhppVtValidada)) {
                if (listHhppBySubEdificio.isEmpty()) {
                    listHhppBySubEdificio = new ArrayList<CmtHhppVtMgl>();
                    listHhppBySubEdificio.add(tecnologiaHabilitada);
                }
                tecnologiaHabilitada.setSubEdificioVtObj(selectedSubEdificioVt);
                tecnologiaHabilitadaFacadeLocal.createCm(tecnologiaHabilitada);
                listHhppBySubEdificio.add(0, tecnologiaHabilitada);
            }
        } catch (ApplicationException e) {
            String msn = "Error Creando El HHPP" + e.getMessage();
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: createHhppCasasManual(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String createHhppManual(CmtHhppVtMgl cmtHhppVtMgl) {
        try {
            CmtHhppVtMgl hhppVtValidada = new CmtHhppVtMgl();
            visitaTecnicaBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
            // para obtener las direcciones de ingresadas en la pestaña multiedificio
            CmtSubEdificiosVt cmtSubEdificiosVt = cmtSubEdificiosVtFacadeLocal.findById(selectedSubEdificioVt.getIdEdificioVt());
            // DrDireccion para validar 
            CmtDireccionMgl dirSubEdi = cmtSubEdificiosVt.mapearCamposCmtDireccion(cmtSubEdificiosVt);
            DrDireccion nuevadireccion = new DrDireccion();
            nuevadireccion.obtenerFromCmtDireccionMgl(dirSubEdi);
            boolean isValidacionOk = true;
            DrDireccion direccionCasa = null;
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimo(Constant.NIVELES_CASAS_CON_DIRECCION).get(0);
            String nivel = "";
            if (parametrosMgl != null) {
                nivel = parametrosMgl.getParValor();
            }
            if (!nivel.equals("")) {
                String[] stringniv = nivel.split("\\|");
                List<String> niveles = Arrays.asList(stringniv);
                if (cmtHhppVtMgl.getOpcionNivel5() != null && !cmtHhppVtMgl.getOpcionNivel5().isEmpty()) {
                    if (niveles.contains(cmtHhppVtMgl.getOpcionNivel5())) {
                        nivel = cmtHhppVtMgl.getOpcionNivel5();
                    }
                }
            }
                    
            if (cmtHhppVtMgl.getOpcionNivel5() == null) {
                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            } else if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                isValidacionOk = true;
            } else {
                // validacion que no permite guardar el hhpp sin previa validacion de la direccion 
                if (!cmtHhppVtMgl.getDireccionValidada().isEmpty() && 
                       (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) &&
                         cmtHhppVtMgl.getValorNivel5().equals(""))) {
                    if (visitaTecnicaBean.getDrDireccion() != null) {
                        if (visitaTecnicaBean.getSubEdiModDir().isClickValidarDir()) {
                            if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)  
                                && tipoCreacionHhpp.contains("Manual")) {
                                if (cmtHhppVtMgl.getDireccionValidada() != null
                                        && !visitaTecnicaBean.getDrDireccion().getIdTipoDireccion().
                                        equalsIgnoreCase(cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCodTipoDir())) {
                                    isValidacionOk = false;
                                    visitaTecnicaBean.getSubEdiModDir().setClickValidarDir(false);
                                    visitaTecnicaBean.setDrDireccion(null);
                                } else {
                                  
                                    direccionCasa = guardarHhppvtCasas(visitaTecnicaBean.getDrDireccion(),cmtSubEdificiosVt,cmtHhppVtMgl);
                                    isValidacionOk = true;
                                    visitaTecnicaBean.getSubEdiModDir().setClickValidarDir(false);
                                    visitaTecnicaBean.setDrDireccion(null);
                                }
                            }
                           
                        } else {
                            if (cmtHhppVtMgl.getCalleRr() != null && cmtHhppVtMgl.getUnidadRr() != null) {
                                if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                                    cmtSubEdificiosVt.setCpValorNivel1(null);
                                    direccionCasa = guardarHhppvtCasas(visitaTecnicaBean.getDrDireccion(),cmtSubEdificiosVt,cmtHhppVtMgl);
                                }
                                isValidacionOk = true;
                                visitaTecnicaBean.setDrDireccion(null);
                            } else {
                                visitaTecnicaBean.setDrDireccion(null);
                                isValidacionOk = false;
                                String MensajeComplementa = "";
                                MensajeComplementa = "Ud no ha validado ".concat(" la direccion ingresada");
                                String msn2 = "Debe validar la direccion . ".
                                        concat(MensajeComplementa);
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                            }

                        }
                    } else {
                        isValidacionOk = false;
                        String MensajeComplementa = "";
                        MensajeComplementa = "Ud no ha validado ".concat(" la direccion ingresada");
                        String msn2 = "Debe validar la direccion . ".
                                concat(MensajeComplementa);
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    }
                
                } else {
                   
                    if (!cmtHhppVtMgl.getDireccionValidada().isEmpty()) {
                        if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) 
                                && cmtHhppVtMgl.getDireccionValidada().equals("") ) {
                         
                            cmtSubEdificiosVt.setCpValorNivel1(null);
                            direccionCasa = guardarHhppvtCasas(visitaTecnicaBean.getDrDireccion(),cmtSubEdificiosVt, cmtHhppVtMgl);
                        }
                         visitaTecnicaBean.setSubEdiModDir(null);
                        visitaTecnicaBean.setDrDireccion(null);
                        isValidacionOk = true;
                    } else {
                        if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)  && 
                                 !cmtHhppVtMgl.getValorNivel5().equals("")) {
                             isValidacionOk = true;
                        } else {
                            isValidacionOk = true;
                           
                        }
                       
                    }
                }
             }


            if (!isValidacionOk) {
                String msn2 = "No ha validado la direccion o  ha ingresado un tipo de direccion que no corresponde a la de la cuenta matriz";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                return null;
            } else {
                
                if (cmtSubEdificiosVt.getCalleRr() != null && cmtSubEdificiosVt.getUnidadRr() != null) {
                    if (selectedSubEdificioVt.getTipoNivel1()!= null) {
                        // carga manual torres  con su propia direccion de tipo direcciones BM e IT
                        if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCodTipoDir().contains("IT")
                                || cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCodTipoDir().contains("BM")) {
                            // cargue de direcciones de tipo BN y IT
                            if (cmtHhppVtMgl.getOpcionNivel5() == null
                                    || cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            if (cmtHhppVtMgl.getOpcionNivel5() != null
                                    && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                                if (cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                        && (cmtHhppVtMgl.getOpcionNivel6() == null)) {
                                    String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                    return null;
                                }
                                if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase("CASA")) {
                                    if (cmtHhppVtMgl.getValorNivel5() == null
                                            || cmtHhppVtMgl.getValorNivel5().trim().isEmpty()) {
                                        String msn = "Debe Seleccionar el valor nivel 5, ya que es requerido para la generación de los HHPP";
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                        return null;
                                    }
                                }
                   
                                if (!cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                        && (cmtHhppVtMgl.getOpcionNivel6() != null)) {
                                    String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                    return null;
                                }
                            }
                            boolean isSoloN5 = true;
                            if (cmtHhppVtMgl.getOpcionNivel5() != null
                                    && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()
                                    && cmtHhppVtMgl.getOpcionNivel6() != null
                                    && !cmtHhppVtMgl.getOpcionNivel6().trim().isEmpty()) {
                                isSoloN5 = false;
                            }
                            hhppVtValidada.setDireccionValidada(direccionValidada);
                            hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
                            hhppVtValidada.setOpcionNivel5(cmtHhppVtMgl.getOpcionNivel5());
                            hhppVtValidada.setValorNivel5(cmtHhppVtMgl.getValorNivel5());
                            hhppVtValidada.setOpcionNivel6(cmtHhppVtMgl.getOpcionNivel6());
                            hhppVtValidada.setValorNivel6(cmtHhppVtMgl.getValorNivel6());
                            hhppVtValidada.setThhVtId(cmtHhppVtMgl.getThhVtId());
                              if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                                  if(direccionCasa != null){
                                       hhppVtValidada.setIdrDireccionCasa(direccionCasa.getId());
                                  }
                                   
                              }
                          
                           if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                                hhppVtValidada.setCalleRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getCalleRr() : null);
                                hhppVtValidada.setUnidadRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getUnidadRr() : null);
                            }

                            hhppVtValidada.setMensajeProceso("");
                        } else {
                            // Subedificios con direccion en el multiedificio 
                            if (cmtHhppVtMgl.getOpcionNivel5() == null
                                    || cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                                String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            if (cmtHhppVtMgl.getOpcionNivel5() != null
                                    && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {

                                if (cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                        && (cmtHhppVtMgl.getOpcionNivel6() == null)) {
                                    String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                    return null;
                                }
                                if (!cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                        && (cmtHhppVtMgl.getOpcionNivel6() != null)) {
                                    String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                    return null;
                                }
                            }
                            if (tipoCreacionHhpp.contains("Manual") && 
                                    cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                                String msn = "Ud no debe ingresar valor en el campo valor Ud Debe validar una direccion  ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)
                                    && (cmtHhppVtMgl.getValorNivel5().equals(""))) {
                                String msn = "Ud no ha ingresado en el campo valor ";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            boolean isSoloN5 = true;
                            if (cmtHhppVtMgl.getOpcionNivel5() != null
                                    && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()
                                    && cmtHhppVtMgl.getOpcionNivel6() != null
                                    && !cmtHhppVtMgl.getOpcionNivel6().trim().isEmpty()) {
                                isSoloN5 = false;
                            }
                            hhppVtValidada.setDireccionValidada(direccionValidada);
                            hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
                            hhppVtValidada.setOpcionNivel5(cmtHhppVtMgl.getOpcionNivel5());
                            hhppVtValidada.setValorNivel5(cmtHhppVtMgl.getValorNivel5());
                            hhppVtValidada.setOpcionNivel6(cmtHhppVtMgl.getOpcionNivel6());
                            hhppVtValidada.setValorNivel6(cmtHhppVtMgl.getValorNivel6());
                            hhppVtValidada.setThhVtId(cmtHhppVtMgl.getThhVtId());
                               if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                                    if(direccionCasa != null){
                                         hhppVtValidada.setIdrDireccionCasa(direccionCasa.getId());
                                    }
                               }
                              
                            if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                                hhppVtValidada.setCalleRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getCalleRr() : null);
                                hhppVtValidada.setUnidadRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getUnidadRr() : null);
                            }
                            hhppVtValidada.setMensajeProceso("");
                        }
                    }
                } else {
                   if (selectedSubEdificioVt.getTipoNivel1()!= null) {

                        if (cmtHhppVtMgl.getOpcionNivel5() == null
                                || cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                            String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                            return null;
                        }
                        if (cmtHhppVtMgl.getOpcionNivel5() != null
                                && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                            if (cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                    && (cmtHhppVtMgl.getOpcionNivel6() == null)) {
                                String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            if (!cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                    && (cmtHhppVtMgl.getOpcionNivel6() != null)) {
                                String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                        }

                       if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)
                               && (cmtHhppVtMgl.getValorNivel5().equals(""))) {
                           String msn = "Ud no ha ingresado en el campo valor ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                        // direccion validada + nivel 5 difrente a CASA +  valor 
                       if (!cmtHhppVtMgl.getDireccionValidada().isEmpty() 
                               && !cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                           String msn = "Para ingresar casas con propia direccion, Ud debe seleccionar solo CASA en nivel Apartamento sin Valor  ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                       // direccion validada + tipo manual nivel 5 igual a CASA + valor 
                       if (!cmtHhppVtMgl.getDireccionValidada().isEmpty() &&  tipoCreacionHhpp.contains("Manual") 
                               && cmtHhppVtMgl.getOpcionNivel5().equals(nivel)  
                               && !cmtHhppVtMgl.getValorNivel5().equals("")) {
                           String msn = "Para ingresar casas con propia direccion, Ud no debe ingresar valor en el Nivel Apartamento, elimine este valor ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                       // direccion validada +  tipo manual + nivel 5 diferente  a CASA + sin valor 
                         if (!cmtHhppVtMgl.getDireccionValidada().isEmpty() && tipoCreacionHhpp.contains("Manual") 
                                 && !cmtHhppVtMgl.getOpcionNivel5().equals(nivel)) {
                           String msn = "Para ingresar casas con propia direccion, Ud debe seleccionar solo CASA en nivel Apartamento sin Valor ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                        boolean isSoloN5 = true;
                        if (cmtHhppVtMgl.getOpcionNivel5() != null
                                && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()
                                && cmtHhppVtMgl.getOpcionNivel6() != null
                                && !cmtHhppVtMgl.getOpcionNivel6().trim().isEmpty()) {
                            isSoloN5 = false;
                        }
                        hhppVtValidada.setDireccionValidada(direccionValidada);
                        hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
                        hhppVtValidada.setOpcionNivel5(cmtHhppVtMgl.getOpcionNivel5());
                        hhppVtValidada.setValorNivel5(cmtHhppVtMgl.getValorNivel5());
                        hhppVtValidada.setOpcionNivel6(cmtHhppVtMgl.getOpcionNivel6());
                        hhppVtValidada.setValorNivel6(cmtHhppVtMgl.getValorNivel6());
                        hhppVtValidada.setThhVtId(cmtHhppVtMgl.getThhVtId());
                         if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel) ){
                             if(direccionCasa != null){
                                       hhppVtValidada.setIdrDireccionCasa(direccionCasa.getId());
                                
                             }
                         }
                        
                        // caso casas con su propia direccion se guarda la placa de la direccion de la casa 
                        if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                            hhppVtValidada.setCalleRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getCalleRr() : null);
                            hhppVtValidada.setUnidadRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getUnidadRr() : null);
                        }

                        hhppVtValidada.setMensajeProceso("");

                    } else {

                        if (cmtHhppVtMgl.getOpcionNivel5() == null
                                || cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                            String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                            return null;
                        }
                        if (cmtHhppVtMgl.getOpcionNivel5() != null
                                && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()) {
                            if (cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                    && (cmtHhppVtMgl.getOpcionNivel6() == null)) {
                                String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                            if (!cmtHhppVtMgl.getOpcionNivel5().trim().contains("+")
                                    && (cmtHhppVtMgl.getOpcionNivel6() != null)) {
                                String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                                return null;
                            }
                        }
                       if (tipoCreacionHhpp.contains("Manual") 
                               && cmtHhppVtMgl.getOpcionNivel5().equals(nivel) 
                               && !cmtHhppVtMgl.getValorNivel5().equals("")) {
                           String msn = "Ud no debe ingresar valor en el campo valor Ud Debe validar una direccion  ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                       if (cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_OTROS)
                               && (cmtHhppVtMgl.getValorNivel5().equals(""))) {
                           String msn = "Ud no ha ingresado en el campo valor ";
                           FacesContext.getCurrentInstance().addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                           return null;
                       }
                        boolean isSoloN5 = true;
                        if (cmtHhppVtMgl.getOpcionNivel5() != null
                                && !cmtHhppVtMgl.getOpcionNivel5().trim().isEmpty()
                                && cmtHhppVtMgl.getOpcionNivel6() != null
                                && !cmtHhppVtMgl.getOpcionNivel6().trim().isEmpty()) {
                            isSoloN5 = false;
                        }
                        hhppVtValidada.setDireccionValidada(direccionValidada);
                        hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
                        hhppVtValidada.setOpcionNivel5(cmtHhppVtMgl.getOpcionNivel5());
                        hhppVtValidada.setValorNivel5(cmtHhppVtMgl.getValorNivel5());
                        hhppVtValidada.setOpcionNivel6(cmtHhppVtMgl.getOpcionNivel6());
                        hhppVtValidada.setValorNivel6(cmtHhppVtMgl.getValorNivel6());
                        hhppVtValidada.setThhVtId(cmtHhppVtMgl.getThhVtId());
                              if (cmtHhppVtMgl.getOpcionNivel5().equals(nivel)){
                                  if(direccionCasa != null){
                                      hhppVtValidada.setIdrDireccionCasa(direccionCasa.getId());
                                  }
                         }
                         
                        // caso casas con su propia direccion se guarda la placa de la direccion de la casa 
                          if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(Constant.OPCION_NIVEL_OTROS)) {
                            hhppVtValidada.setCalleRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getCalleRr().toUpperCase() : null);
                            hhppVtValidada.setUnidadRr(visitaTecnicaBean.getSubEdiModDir() != null ? visitaTecnicaBean.getSubEdiModDir().getUnidadRr().toUpperCase() : null);
                        }

                        hhppVtValidada.setMensajeProceso("");
                         
                       }
                }
            }

            hhppVtValidada.setSubEdificioVtObj(selectedSubEdificioVt);
            if (cmtSubEdificiosVt.getTipoViaGeneradora() != null || cmtSubEdificiosVt.getMzTipoNivel4() != null) {
                if (cmtSubEdificiosVt.getTipoViaGeneradora().contains("KDX") || cmtSubEdificiosVt.getMzTipoNivel4().contains("KDX")) {
                    guardarHhppVt(hhppVtValidada);
                } else {
                    if (validarDireccionCalleRR(hhppVtValidada, cmtSubEdificiosVt, 0, nuevadireccion)) {
                        guardarHhppVt(hhppVtValidada);
                    }
                }
            }else{
                if (validarDireccionCalleRR(hhppVtValidada, cmtSubEdificiosVt, 0, nuevadireccion)) {
                        guardarHhppVt(hhppVtValidada);
                    }
            }
            

        } catch (ApplicationException e) {
            String msn = "Error Creando El HHPP" + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: createHhppManual(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void guardarHhppVt(CmtHhppVtMgl hhppVtValidada)  {
        try {
            tecnologiaHabilitadaFacadeLocal.createCm(hhppVtValidada);

            if (selectedSubEdificioVt.getTipoNivel1()
                    .getIdentificadorInternoApp().equals(Constant.BASICA_CAMPUS_BATALLON)) {
                habilitaCampoTextoProcesoRr = false;
                cargarGridBat();
                cargarGridCasas();
                limpiarPanelCargue();
                String msn = "Registro Creado con Exito";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                habilitaCampoTextoProcesoRr = true;
                cargarGridTorres();
                cargarGridCasas();
                limpiarPanelCargue();
                String msn = "Registro Creado con Exito";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
            setHabilitarColums(false);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: guardarHhppVt(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarListadosConfiguracion()  {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent =
                    componenteDireccionesMglFacade
                    .getConfiguracionComponente(Constant.RR_DIR_CREA_HHPP);

            ckList = componenteDireccionesMglFacade
                    .obtenerCalleCarreraList(configurationAddressComponent);
            bmList = componenteDireccionesMglFacade
                    .obtenerBarrioManzanaList(configurationAddressComponent);
            itList = componenteDireccionesMglFacade
                    .obtenerIntraducibleList(configurationAddressComponent);
            aptoList = componenteDireccionesMglFacade
                    .obtenerApartamentoList(configurationAddressComponent);
            complementoList = componenteDireccionesMglFacade
                    .obtenerComplementoList(configurationAddressComponent);
            configurationAddressComponent.getAptoValues();
        } catch (ApplicationException e) {
            String msn = "Error al realizar consultas para obtener configuración "
                    + "de listados.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: cargarListadosConfiguracion(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void validateHhpp()  {
        try {
            listTecnologiaHabilitada = tecnologiaHabilitadaFacadeLocal.findByVt(vt);
            if (listTecnologiaHabilitada.size() > 0) {
                boolean hayErrorHhpp = false;
                for (CmtHhppVtMgl hhpp : listTecnologiaHabilitada) {
                    if ((hhpp.getValorNivel5().isEmpty() || hhpp.getValorNivel5() == null)
                            && (hhpp.getValorNivel6() == null || hhpp.getValorNivel6().isEmpty())) {
                        hayErrorHhpp = true;
                    }
                }
                VisitaTecnicaBean vtMglBean = (VisitaTecnicaBean) JSFUtil.getSessionBean(VisitaTecnicaBean.class);
                vtMglBean.setHayErrorHhpp(hayErrorHhpp);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: validateHhpp(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: validateHhpp(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String editHhppManual(CmtHhppVtMgl tecnologiaHabilitada) {
        try {
        List<CmtHhppVtMgl> listaBdTecnologia = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(tecnologiaHabilitada.getSubEdificioVtObj());
         
        boolean repetido = validarHhppRepEdicion( tecnologiaHabilitada, Integer.parseInt(tecnologiaHabilitada.getValorNivel5()),listaBdTecnologia);
            if (!repetido) {
                if (validateNivelesHhppVt(tecnologiaHabilitada)) {
                    tecnologiaHabilitadaFacadeLocal.updateCm(tecnologiaHabilitada);
                    cargarGridCasas();
                    cargarGridTorres();
                    String msn = "Registro Modificado con Exito";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
            } else {
                String msn = "Esta unidad ya existe";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Error Editando El HHPP" + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: editHhppManual(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    private boolean validateNivelesHhppVt(CmtHhppVtMgl hhppVt) {
        
        if(hhppVt.getValorNivel5().equals(String.valueOf(0))){
             String msn = "Ud debe ingresar el valor del nivel 5";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }
         if (hhppVt.getValorNivel5().startsWith("0")) {
            String msn = "El valor nivel 5 no puede comenzar por 0";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }
        //Validaciones de Seleccion de los nivel 5 y 6
        if (hhppVt.getOpcionNivel5() == null) {
            String msn = "Debe Seleccionar nivel 5, ya que es requerido";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else if (hhppVt.getOpcionNivel5().trim().isEmpty()) {
            String msn = "Debe Seleccionar nivel 5, ya que es requerido";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else {
            //Si se selecciono Nivel 5 se valida el Valor
            if (hhppVt.getValorNivel5() == null) {
                String msn = "Debe Ingresar Valor para el nivel 5";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return false;
            }

            //Validamos la informacion del Nivel 6
            if (hhppVt.getOpcionNivel5().contains("+")) {
                if (hhppVt.getOpcionNivel6() == null) {
                    String msn = "Debe Seleccionar nivel 6, ya que es requerido";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return false;
                } else if (hhppVt.getOpcionNivel6().trim().isEmpty()) {
                    String msn = "Debe Seleccionar nivel 6, ya que es requerido";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return false;
                }
            }

        }
        if (hhppVt.getOpcionNivel6() != null
                && !hhppVt.getOpcionNivel6().trim().isEmpty()) {
            //Si se selecciono Nivel 6 se valida el Valor
            if (hhppVt.getValorNivel6() == null) {
                String msn = "Debe Ingresar Valor para el nivel 6";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return false;
            } else if (hhppVt.getValorNivel6().trim().isEmpty()) {
                String msn = "Debe Ingresar Valor para el nivel 6";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return false;
            }
        } else if (hhppVt.getValorNivel6() != null
                && !hhppVt.getValorNivel6().trim().isEmpty()) {
            if (!selectedSubEdificioVt.getTipoNivel1()
                    .getIdentificadorInternoApp().equals(Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION)) {
                String msn = "No Debe Ingresar Valor para el nivel 6";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return false;
            }
        }
        return true;
    }

    public String createHhppAutomatic(HhppVtDto hhppVtDto) {
        try {
            int cmtHhppVtMglExito = 0;
            boolean repetido = false;
            // escenario automatico para torres
            // para obtener las direcciones  ingresadas en la pestaña multiedificio
            CmtSubEdificiosVt cmtSubEdificiosVt = cmtSubEdificiosVtFacadeLocal.findById(selectedSubEdificioVt.getIdEdificioVt());
            List<CmtHhppVtMgl> listaBdTecnologia = tecnologiaHabilitadaFacadeLocal.findBySubEdiVt(cmtSubEdificiosVt);
            // para obtener las direcciones de ingresadas en la pestaña hhpp
            CmtDireccionMgl dirSubEdi = cmtSubEdificiosVt.mapearCamposCmtDireccion(cmtSubEdificiosVt);
            DrDireccion nuevadireccion = new DrDireccion();
            nuevadireccion.obtenerFromCmtDireccionMgl(dirSubEdi);
            // se coloca el nivel que corresponde a el nivel 1 y valor 1 en null ya que es unico edificio
            if(cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                        equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)){
                nuevadireccion.setCpTipoNivel1(null);
                nuevadireccion.setCpValorNivel1(null);
            }
            if (cmtSubEdificiosVt.getCalleRr() != null && cmtSubEdificiosVt.getUnidadRr() != null) {

                if (hhppVtDto.opcionNivel5 == null
                        || hhppVtDto.opcionNivel5.trim().isEmpty()) {
                    String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (hhppVtDto.opcionNivel5 != null
                        && !hhppVtDto.opcionNivel5.trim().isEmpty()) {
                    if (hhppVtDto.opcionNivel5.trim().contains("+")
                            && (hhppVtDto.opcionNivel6 == null)) {
                        String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (!hhppVtDto.opcionNivel5.trim().contains("+")
                            && (hhppVtDto.opcionNivel6 != null)) {
                        String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
 
                }
                boolean isSoloN5 = true;
                if (hhppVtDto.opcionNivel5 != null
                        && !hhppVtDto.opcionNivel5.trim().isEmpty()
                        && hhppVtDto.opcionNivel6 != null
                        && !hhppVtDto.opcionNivel6.trim().isEmpty()) {
                    isSoloN5 = false;
                }

                if ("Automatica".equals(tipoCreacionHhpp)) {
                    int linea = 0;
                    for (int hhpp = hhppVtDto.iniRango; hhpp < hhppVtDto.iniRango + hhppVtDto.cantidad; hhpp++) {

                        tecnologiaHabilitada = new CmtHhppVtMgl();
                        // torres con direccion en el subedificio
                        if (isSoloN5) {
                            tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                            tecnologiaHabilitada.setValorNivel5(Integer.toString(hhpp).toUpperCase());

                        } else {
                            tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                            tecnologiaHabilitada.setValorNivel5(hhppVtDto.valorNivel5.toUpperCase());
                            tecnologiaHabilitada.setOpcionNivel6(hhppVtDto.opcionNivel6.toUpperCase());
                            tecnologiaHabilitada.setValorNivel6(Integer.toString(hhpp).toUpperCase());

                        }
                        // cuando se selecciona el tipo de vivienda 
                        String tipoVivienda = "";
                        if (hhppVtDto.thhVtIdSelect != null) {         
                            tecnologiaHabilitada.setThhVtId(hhppVtDto.thhVtIdSelect);
                            // cargue automatico
                        } else {
                            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
                            tipoVivienda = direccionRRManager.obtenerTipoEdificio(hhppVtDto.opcionNivel5.toUpperCase(), usuarioVT, null);
                            tecnologiaHabilitada.setThhVtId(tipoVivienda);
                        }
                        tecnologiaHabilitada.setSubEdificioVtObj(selectedSubEdificioVt);
                        tecnologiaHabilitada.setPiso(Integer.parseInt(hhppVtDto.getValorNivel5()));
                       
                        if (validarDireccionCalleRR(tecnologiaHabilitada, cmtSubEdificiosVt, linea, nuevadireccion)) {
                            tecnologiaHabilitadaFacadeLocal.createCm(tecnologiaHabilitada);
                            hhppVtDto.btnDisable = true;
                            cmtHhppVtMglExito++;
                            linea += 1;
                        }
                        
                        
                   
                        linea += 1;
                    }
                    if (cmtHhppVtMglExito > 0) {

                        if ((selectedSubEdificioVt.getTipoNivel1()
                                .getIdentificadorInternoApp().equals(Constant.BASICA_TORRE)
                                || selectedSubEdificioVt.getTipoNivel1()
                                .getIdentificadorInternoApp().equals(Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION))) {
                            cargarGridTorres();
                            cargarTodos();
                            String msn = "Se crearon " + cmtHhppVtMglExito + " Hhpp's  con Exito";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                        } else {
                            cargarGridTorres();
                            cargarTodos();
                            String msn = "Se crearon " + cmtHhppVtMglExito + " Hhpp's  con Exito";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                        }

                    } else {

                        String msn = "No fue posible guardar los hhpp's";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }


                }

            } else {

                if (hhppVtDto.opcionNivel5 == null
                        || hhppVtDto.opcionNivel5.trim().isEmpty()) {
                    String msn = "Debe Seleccionar nivel 5, ya que es requerido para la generación de los HHPP";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (hhppVtDto.opcionNivel5 != null
                        && !hhppVtDto.opcionNivel5.trim().isEmpty()) {
                    if (hhppVtDto.opcionNivel5.trim().contains("+")
                            && (hhppVtDto.opcionNivel6 == null)) {
                        String msn = "Debe Seleccionar nivel 6, ya que es requerido para la generación de los HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (!hhppVtDto.opcionNivel5.trim().contains("+")
                            && (hhppVtDto.opcionNivel6 != null)) {
                        String msn = "No debe Seleccionar nivel 6, para la generación de los HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if(hhppVtDto.iniRango == 0 ){
                        String msn = "Ud debe introducir el rango de creacion de HHPP";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    if (hhppVtDto.cantidad == 0) {
                        String msn = "Ud debe introducir la cantidad de HHPP ha generar";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                    
                }
                boolean isSoloN5 = true;
                if (hhppVtDto.opcionNivel5 != null
                        && !hhppVtDto.opcionNivel5.trim().isEmpty()
                        && hhppVtDto.opcionNivel6 != null
                        && !hhppVtDto.opcionNivel6.trim().isEmpty()) {
                    isSoloN5 = false;
                }

                if ("Automatica".equals(tipoCreacionHhpp)) {
                    int linea = 0;
                    for (int hhpp = hhppVtDto.iniRango; hhpp < hhppVtDto.iniRango + hhppVtDto.cantidad; hhpp++) {
                        tecnologiaHabilitada = new CmtHhppVtMgl();
                        // sin direccion en multiedificio
                        repetido = validarHhppRep(hhppVtDto,hhpp, listaBdTecnologia);
                        if (!repetido) {
                            if (selectedSubEdificioVt.getDireccionSubEdificio() == null) {
                                if (isSoloN5) {
                                    tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel5(Integer.toString(hhpp).toUpperCase());
                                    tecnologiaHabilitada.setCalleRr(null);
                                    tecnologiaHabilitada.setUnidadRr(null);
                                } else {
                                    tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel5(hhppVtDto.valorNivel5.toUpperCase());
                                    tecnologiaHabilitada.setOpcionNivel6(hhppVtDto.opcionNivel6.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel6(Integer.toString(hhpp).toUpperCase());
                                    tecnologiaHabilitada.setCalleRr(null);
                                    tecnologiaHabilitada.setUnidadRr(null);
                                }

                            } else {
                                // guardar torres con direccion en el subedificio con nivel 5
                                if (isSoloN5) {
                                    tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel5(Integer.toString(hhpp).toUpperCase());
                                    tecnologiaHabilitada.setCalleRr(cmtSubEdificiosVt.getCalleRr().toUpperCase());
                                    tecnologiaHabilitada.setUnidadRr(cmtSubEdificiosVt.getUnidadRr().toUpperCase());
                                } else {
                                    // guardar torres con direccion en el subedificio con nivel 5 y 6
                                    tecnologiaHabilitada.setOpcionNivel5(hhppVtDto.opcionNivel5.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel5(hhppVtDto.valorNivel5.toUpperCase());
                                    tecnologiaHabilitada.setOpcionNivel6(hhppVtDto.opcionNivel6.toUpperCase());
                                    tecnologiaHabilitada.setValorNivel6(Integer.toString(hhpp).toUpperCase());
                                    tecnologiaHabilitada.setCalleRr(cmtSubEdificiosVt.getCalleRr().toUpperCase());
                                    tecnologiaHabilitada.setUnidadRr(cmtSubEdificiosVt.getUnidadRr().toUpperCase());
                                }
                            }
                            // cuando se selecciona el tipo de vivienda 
                            String tipoVivienda = "";
                            if (hhppVtDto.thhVtIdSelect != null) {
                                tecnologiaHabilitada.setThhVtId(hhppVtDto.thhVtIdSelect);
                                // cargue automatico
                            } else {
                                DireccionRRManager direccionRRManager = new DireccionRRManager(true);
                                tipoVivienda = direccionRRManager.obtenerTipoEdificio(hhppVtDto.opcionNivel5.toUpperCase(), usuarioVT, null);
                                tecnologiaHabilitada.setThhVtId(tipoVivienda);
                            }

                            tecnologiaHabilitada.setSubEdificioVtObj(selectedSubEdificioVt);
                            tecnologiaHabilitada.setPiso(Integer.parseInt(hhppVtDto.getValorNivel5()));
                            if (validarDireccionCalleRR(tecnologiaHabilitada, cmtSubEdificiosVt, linea, nuevadireccion)) {
                                tecnologiaHabilitadaFacadeLocal.createCm(tecnologiaHabilitada);
                                hhppVtDto.btnDisable = true;
                                cmtHhppVtMglExito++;
                                linea += 1;
                            }

                        } else {
                            break;
                        }
                    }
                        if (cmtHhppVtMglExito > 0) {
                            if ((selectedSubEdificioVt.getTipoNivel1()
                                    .getIdentificadorInternoApp().equals(Constant.BASICA_TORRE)
                                    || selectedSubEdificioVt.getTipoNivel1()
                                            .getIdentificadorInternoApp().equals(Constant.BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION))) {
                                cargarGridTorres();
                                String msn = "Se crearon " + cmtHhppVtMglExito + " Hhpp's  con Exito";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                            } else {
                                cargarGridTorres();
                                String msn = "Se crearon " + cmtHhppVtMglExito + " Hhpp's  con Exito";
                                FacesContext.getCurrentInstance()
                                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                            }
                        }

                    if(repetido){
                          String msn = "Ud tiene elementos repetidos, verifique !";
                            FacesContext.getCurrentInstance()
                                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    }

                
                }
            }


        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: createHhppAutomatic(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: createHhppAutomatic(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
      /**
     * Metodo que guarda la direccion de las casas en la tabla TEC_SOLICITUD_DIRECCION
     *
     * @param drDireccion objeto de la direccion validada
     * @param cmtSubEdificiosVt datos del nombre del nivel del subedificio
     * componente
     * @param cmtHhppVtMgl
     * @return Drdireccion con la direccion completa de la casa que se guardo 
     */
    
        public DrDireccion guardarHhppvtCasas(DrDireccion drDireccion,
                CmtSubEdificiosVt cmtSubEdificiosVt, CmtHhppVtMgl cmtHhppVtMgl) {
        try {
         
            drDireccion.setFechaCreacion(new Date());
            drDireccion.setUsuarioCreacion(usuarioVT);
            drDireccion.setUsuarioEdicion("hectorg");
            drDireccion.setFechaEdicion(new Date());
            drDireccion.setPerfilCreacion(perfilVt);
            if (!drDireccion.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                if ((cmtHhppVtMgl.getOpcionNivel5().equals(Constant.OPCION_NIVEL_5_CASAS)
                        || cmtHhppVtMgl.getOpcionNivel5().equals("LOCAL"))) {
                    cmtSubEdificiosVt.setCpValorNivel1(null);
                } else {
                    drDireccion.setCpTipoNivel1(cmtSubEdificiosVt.getTipoNivel1().getNombreBasica());
                    drDireccion.setCpValorNivel1(cmtSubEdificiosVt.getValorNivel1());
                }

            }
           
            drDireccion = drDireccionFacadeLocal.create(drDireccion);
            } catch (ApplicationException e) {
                String msn = "Ocurrio un error Guardanado el registro : " + e.getMessage();
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: guardarHhppvtCasas(). " + e.getMessage(), e, LOGGER);
            }
        return drDireccion;
    }

    public void deleteHhppVt(CmtHhppVtMgl tecnologiaHabilitada) {
        try {
            if (tecnologiaHabilitadaFacadeLocal.deleteCm(tecnologiaHabilitada)) {

                cargarGridCasas();
                cargarTodos();
                cargarGridTorres();
                String msn = "Registro Eliminado con Exito";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

                List<CmtHhppVtMgl> lstCmtHhppVtMgls = tecnologiaHabilitadaFacadeLocal.
                        findBySubedificioVtAndPiso(tecnologiaHabilitada.getSubEdificioVtObj(), tecnologiaHabilitada.getPiso());

                if (lstCmtHhppVtMgls.isEmpty()) {
                    for (HhppVtDto hhppVtDtoR : listHhppVtDto) {
                        if (hhppVtDtoR.isBtnDisable() && hhppVtDtoR.valorNivel5.
                                equalsIgnoreCase(String.valueOf(tecnologiaHabilitada.getPiso()))) {
                            hhppVtDtoR.btnDisable = false;
                            hhppVtDtoR.setCantidad(0);
                            hhppVtDtoR.setIniRango(0);
                            hhppVtDtoR.setOpcionNivel5("");
                            hhppVtDtoR.setOpcionNivel6("");
                        }
                    }
                }

            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando el registro : " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: deleteHhppVt(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarPanelCargue() {
        listHhppCargue = new ArrayList<CmtHhppVtMgl>();
        tecnologiaHabilitada = new CmtHhppVtMgl();
        tecnologiaHabilitada.setDireccionValidada("");
        tecnologiaHabilitada.setDireccionValidada(null);
        tecnologiaHabilitada.setOpcionNivel5(null);
        tecnologiaHabilitada.setValorNivel5(null);
        if (listHhppCargue.isEmpty()) {
            listHhppCargue.add(tecnologiaHabilitada);
        }

    }

    public void exportExcel() {
        try {
            listTecnologiaHabilitada = tecnologiaHabilitadaFacadeLocal.findByVt(vt);
            if (listTecnologiaHabilitada != null
                    && listTecnologiaHabilitada.size() > 0) {
                //Libro en blanco           
                XSSFWorkbook workbook = new XSSFWorkbook();
                String sheetName = "Lista de HHPP";
                Object[] cabeceraDataGral = new Object[]{"Direccion", "Nombre SubEdificio",
                    "Nivel 5", "Valor nivel 5", "Nivel 6", "Valor nivel 6","Direccion Casa"};

                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), cabeceraDataGral);
                for (CmtHhppVtMgl e : listTecnologiaHabilitada) {
                    mapDataEstado.put(String.valueOf(fila++),
                            new Object[]{e.getSubEdificioVtObj().getSubEdificioObj().getDireccion(),
                        e.getSubEdificioVtObj().getNombreSubEdificio(),
                        e.getOpcionNivel5(),
                        e.getValorNivel5(),
                        e.getOpcionNivel6(),
                        e.getValorNivel6(),
                        e.getCalleRr() != null && e.getCalleRr() != null ? e.getCalleRr()+" "+e.getUnidadRr(): "",
                        });
                }
                fillSheetbook(workbook, sheetName, mapDataEstado);
                String fileName = "ListaHHPP";
                FacesContext fc = FacesContext.getCurrentInstance();
                response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                response.setHeader("Content-Disposition", "attachment; filename=" + fileName
                        + formato.format(new Date()) + ".xlsx");
                OutputStream output = response.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException | IOException e) {
            String msn = "Ocurrio un error en la creacion del archivo excel";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: exportExcel(). " + e.getMessage(), e, LOGGER);
        }
    }

    private void fillSheetbook(XSSFWorkbook workbook, String sheetName,
            Map<String, Object[]> data) {
        try {
            //Crea un libro en blanco
            XSSFSheet sheet = workbook.createSheet(sheetName);

            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);

            //Itera sobre los datos que seran escritos en el libro
            int rownum = 0;
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    }
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: fillSheetbook()(). " + e.getMessage(), e, LOGGER);
        }
    }

    // Metodo que carga los nombres abreviados del apartamento para casos especiales
    public void cargarLista(CmtHhppVtMgl nombreRR) {


        for (CmtHhppVtMgl cmtHhppVtMgl : listHhppBySubEdificio) {

            if (cmtHhppVtMgl.getIdHhppVt().equals(nombreRR.getIdHhppVt())) {
                try {
                    cmtHhppVtMgl.setNombreValidoRR(nombreRR.getNombreValidoRR());
                    tecnologiaHabilitadaFacadeLocal.updateCm(cmtHhppVtMgl);
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                    LOGGER.error(msg, ex);
                }

            }
        }



    }


    public boolean validarDireccionCalleRR(CmtHhppVtMgl tecnologiaHabilitada, CmtSubEdificiosVt subEdi, int linea, DrDireccion nuevaDireccion) {
        GeograficoPoliticoMgl multiorigen = null;
        boolean valida = false;
        DetalleDireccionEntity detalleDireccionEntity = null;
        CmtDireccionMgl dirCm = null;
        try {
            
            if(subEdi.getSubEdificioObj()  == null ){
                
            }
            // Subedificios nuevos que no tienen subedificioId
            if(subEdi.getSubEdificioObj()  == null ){
                  if (subEdi.getDireccionSubEdificio() != null) {
                      dirCm = tecnologiaHabilitada.getSubEdificioVtObj().getVtObj().getOtObj().getCmObj().getDireccionPrincipal();
                       
                  }else{
                      dirCm = tecnologiaHabilitada.getSubEdificioVtObj().getVtObj().getOtObj().getCmObj().getDireccionPrincipal();
                  }
               
            }else{
                if (subEdi.getDireccionSubEdificio() != null) {
                   dirCm =  subEdi.getSubEdificioObj().getCmtCuentaMatrizMglObj().getDireccionPrincipal();
                }else{
                      dirCm =  subEdi.getSubEdificioObj().getCmtCuentaMatrizMglObj().getDireccionPrincipal();
                }
                
            }
            DrDireccion nuevadireccion = new DrDireccion();
            String dirCMRR = "";
            String calleRR = "";
            String numeroUnidadRR = "";
            String numeroApartamentoRR = "";
            nuevadireccion.obtenerFromCmtDireccionMgl(dirCm);
            detalleDireccionEntity = nuevaDireccion.convertToDetalleDireccionEntity();
            detalleDireccionEntity.setCptiponivel5(tecnologiaHabilitada.getOpcionNivel5());
            detalleDireccionEntity.setCpvalornivel5(tecnologiaHabilitada.getValorNivel5());
            detalleDireccionEntity.setCptiponivel6(tecnologiaHabilitada.getOpcionNivel6());
            detalleDireccionEntity.setCpvalornivel6(tecnologiaHabilitada.getValorNivel6());
            detalleDireccionEntity.setBarrio(tecnologiaHabilitada.getSubEdificioVtObj()
                    .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getBarrio());
          
            // si el subedificiovt no tiene subedificio  se construye
            subEdi.setIdTipoDireccion(tecnologiaHabilitada.getSubEdificioVtObj().getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCodTipoDir());

                if (subEdi.getIdTipoDireccion().equals("CK")) {
                    detalleDireccionEntity.setTipoviaprincipal(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getTipoViaPrincipal());
                    detalleDireccionEntity.setNumviaprincipal(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getNumViaPrincipal());
                    detalleDireccionEntity.setNumviageneradora(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getNumViaGeneradora());
                    detalleDireccionEntity.setPlacadireccion(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getPlacaDireccion());
                    detalleDireccionEntity.setBarrio(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getBarrio());

                } else if (subEdi.getIdTipoDireccion().equals("BM")) {
                    detalleDireccionEntity.setMztiponivel4(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel4());
                    detalleDireccionEntity.setMztiponivel5(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel5());
                    detalleDireccionEntity.setCpvalornivel4(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getCpValorNivel4());
                    detalleDireccionEntity.setMzvalornivel5(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel5());
                    detalleDireccionEntity.setBarrio(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getBarrio());
                } else {

                    detalleDireccionEntity.setMztiponivel1(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel1());
                    detalleDireccionEntity.setMztiponivel2(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel2());
                    detalleDireccionEntity.setMztiponivel3(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel3());
                    detalleDireccionEntity.setMztiponivel4(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel4());
                    detalleDireccionEntity.setMztiponivel5(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel5());
                    detalleDireccionEntity.setMztiponivel6(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzTipoNivel6());
                 
                    detalleDireccionEntity.setMzvalornivel1(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel1());
                    detalleDireccionEntity.setMzvalornivel2(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel2());
                    detalleDireccionEntity.setMzvalornivel3(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel3());
                    detalleDireccionEntity.setMzvalornivel4(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel4());
                    detalleDireccionEntity.setMzvalornivel5(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel5());
                    detalleDireccionEntity.setMzvalornivel6(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getMzValorNivel6());
                    detalleDireccionEntity.setBarrio(tecnologiaHabilitada.getSubEdificioVtObj()
                            .getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getBarrio());

                }



            LOGGER.error("Inicio Validacion Longitud direccion");
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            multiorigen = geograficoPoliticoMglFacadeLocal.findCityByComundidad(subEdi.getVtObj().getOtObj().getCmObj().getCentroPoblado().getGeoCodigoDane());
            if(multiorigen != null){
                 detalleDireccionEntity.setMultiOrigen(multiorigen.getGpoMultiorigen());
            }
            detalleDireccionEntity.setIdtipodireccion(dirCm.getCodTipoDir());
            if(subEdi != null && subEdi.getSubEdificioObj()!= null){
            if (subEdi.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && !subEdi.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                detalleDireccionEntity.setCptiponivel1(null);
                detalleDireccionEntity.setCpvalornivel1(null);
            }
            }

            ArrayList<String> arrDirRR = direccionRRManager.generarDirFormatoRR(detalleDireccionEntity);
            if (arrDirRR != null && arrDirRR.size() == 3) {
                calleRR = arrDirRR.get(0);
                numeroUnidadRR += arrDirRR.get(1);
                numeroApartamentoRR += arrDirRR.get(2);
                if (subEdi.getDireccionSubEdificio() != null) {
                    dirCMRR = dirCm.getCalleRr();
                    calleRR = dirCMRR + " " + "EN " + subEdi.getCalleRr() + " " + subEdi.getUnidadRr();

                }

                if (calleRR.length() > 50) {
                    valida = false;
                    String msn = "Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "]" + " " + "Linea : " + linea + " " + "Longitud:" + calleRR.trim().length();
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                } else if (numeroUnidadRR.trim().length() > 10) {
                    valida = false;
                    String msn = "El campo Número de Unidad ha superado el número máximo de caracteres en formato RR [" + numeroUnidadRR + "]" + " " + "Linea : " + linea + " " + "Longitud:" + numeroUnidadRR.trim().length();
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));

                 
                } else {
                    if (!tecnologiaHabilitada.getOpcionNivel5().equalsIgnoreCase("Otros") && numeroApartamentoRR.trim().length() > 10) {
                        valida = false;
                        String msn2 = "El campo Número de Apartamento ha superado el número máximo de caracteres en formato RR [" + numeroApartamentoRR + "]" + " " + "Linea : " + linea + " " + "Longitud:" + numeroApartamentoRR.trim().length();
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn2, ""));
                    } else {
                        valida = true;
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: validarDireccionCalleRR(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TecnologiaHabilitadaVtBean: validarDireccionCalleRR() " + e.getMessage(), e, LOGGER);
        }


        return valida;
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
        return validarAccion(ValidacionUtil.OPC_BORRADO);
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
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TecnologiaHabilitadaVtBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    /**
     * M&eacute;todo para cargar el tipo de vivienda segun la regla
     *
     */
    public void cargarRegla(String tipoViviendaSeleccionadaNivel5) {
        try {
            String tipoViviendaByRegla = "";
           
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            String carpeta = Constant.CARPETA_CUENTA_MATRIZ;
            tipoViviendaByRegla = direccionRRManager.obtenerTipoEdificio(tipoViviendaSeleccionadaNivel5, carpeta, "");

            if ("Automatica".equals(tipoCreacionHhpp)) {
                List<HhppVtDto> copyHhppVtDto = new ArrayList<HhppVtDto>(listHhppVtDto);
                List<HhppVtDto> finalHhppVtDto = new ArrayList<HhppVtDto>();
                for (HhppVtDto hhppVtDto : copyHhppVtDto) {
                    HhppVtDto hhppNew = new HhppVtDto();
                    hhppNew.setValorNivel5(hhppVtDto.getValorNivel5());
                    hhppNew.setCantidad(hhppVtDto.getCantidad());
                    hhppNew.setIniRango(hhppVtDto.getIniRango());
                    hhppNew.setFinRango(hhppVtDto.getFinRango());
                    hhppNew.setThhVtIdSelect(tipoViviendaByRegla);
                    finalHhppVtDto.add(hhppNew);
                }
                listHhppVtDto = finalHhppVtDto;
            } else {
                List<CmtHhppVtMgl> copyCmtHhppVtMgl = new ArrayList<CmtHhppVtMgl>(listHhppCargue);
                List<CmtHhppVtMgl> finalCmtHhppVtMgl = new ArrayList<CmtHhppVtMgl>();
                for (CmtHhppVtMgl cmtHhppVtMgl : copyCmtHhppVtMgl) {
                    CmtHhppVtMgl cmtHhppVtMglNew = new CmtHhppVtMgl();
                    cmtHhppVtMglNew.setOpcionNivel5(cmtHhppVtMgl.getOpcionNivel5());
                    cmtHhppVtMglNew.setThhVtId(tipoViviendaByRegla);
                    finalCmtHhppVtMgl.add(cmtHhppVtMglNew);
                }
                listHhppCargue = finalCmtHhppVtMgl;
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TecnologiaHabilitadaVtBean. " + e.getMessage(), e, LOGGER);
        }
       
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

    public CmtSubEdificiosVt getSelectedSubEdificioVt() {
        return selectedSubEdificioVt;
    }

    public void setSelectedSubEdificioVt(CmtSubEdificiosVt selectedSubEdificioVt) {
        this.selectedSubEdificioVt = selectedSubEdificioVt;
    }

    public SubEdificiosVtBean getSubEdificiosVtBean() {
        return subEdificiosVtBean;
    }

    public void setSubEdificiosVtBean(SubEdificiosVtBean subEdificiosVtBean) {
        this.subEdificiosVtBean = subEdificiosVtBean;
    }

    public CmtHhppVtMgl getTecnologiaHabilitada() {
        return tecnologiaHabilitada;
    }

    public void setTecnologiaHabilitada(CmtHhppVtMgl tecnologiaHabilitada) {
        this.tecnologiaHabilitada = tecnologiaHabilitada;
    }

    public List<CmtHhppVtMgl> getListTecnologiaHabilitada() {
        return listTecnologiaHabilitada;
    }

    public void setListTecnologiaHabilitada(List<CmtHhppVtMgl> listTecnologiaHabilitada) {
        this.listTecnologiaHabilitada = listTecnologiaHabilitada;
    }

    public List<CmtHhppVtMgl> getListHhppBySubEdificio() {
        return listHhppBySubEdificio;
    }

    public void setListHhppBySubEdificio(List<CmtHhppVtMgl> listHhppBySubEdificio) {
        this.listHhppBySubEdificio = listHhppBySubEdificio;
    }

    public HhppVtDto getHhppVtDto() {
        return hhppVtDto;
    }

    public void setHhppVtDto(HhppVtDto hhppVtDto) {
        this.hhppVtDto = hhppVtDto;
    }

    public List<HhppVtDto> getListHhppVtDto() {
        return listHhppVtDto;
    }

    public void setListHhppVtDto(List<HhppVtDto> listHhppVtDto) {
        this.listHhppVtDto = listHhppVtDto;
    }

    public String getTipoCreacionHhpp() {
        return tipoCreacionHhpp;
    }

    public void setTipoCreacionHhpp(String tipoCreacionHhpp) {
        this.tipoCreacionHhpp = tipoCreacionHhpp;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public List<CmtSubEdificiosVt> getListCmtSubEdificiosVt() {
        listCmtSubEdificiosVt.sort(new Comparator<CmtSubEdificiosVt>(){
            @Override
            public int compare(CmtSubEdificiosVt o1, CmtSubEdificiosVt o2) {
                String o1St = o1.getVtObj().getOtObj().getCmObj().isUnicoSubEdificioBoolean()?o1.getVtObj().getOtObj().getCmObj().getNombreCuenta():o1.getNombreSubEdificio();
                String o2St = o2.getVtObj().getOtObj().getCmObj().isUnicoSubEdificioBoolean()?o2.getVtObj().getOtObj().getCmObj().getNombreCuenta():o2.getNombreSubEdificio();
                o1St = (o1St == null )? "" :o1St;
                o2St = (o2St == null )? "" :o2St;
                return o1St.compareTo(o2St);
            }
        });
        return listCmtSubEdificiosVt;
    }
    
    public boolean validarHhppRep(HhppVtDto hhppVtDto,int hhpp,List<CmtHhppVtMgl> listaBdTecnologia){
        boolean repetido = false;
        if (listaBdTecnologia != null && !listaBdTecnologia.isEmpty()) {
            for (CmtHhppVtMgl cmtHhppVtMgl : listaBdTecnologia) {
                if (hhpp != 0 && hhppVtDto.getOpcionNivel5() != null && hhppVtDto.getOpcionNivel6() != null) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equals(hhppVtDto.opcionNivel5)
                            && cmtHhppVtMgl.getOpcionNivel6().equals(hhppVtDto.opcionNivel6)
                            && cmtHhppVtMgl.getValorNivel5().equals(hhppVtDto.valorNivel5)
                            && cmtHhppVtMgl.getValorNivel6().equals(String.valueOf(hhpp))) {
                        repetido = true;
                        break;
                    }
                } else {
                    if (cmtHhppVtMgl.getOpcionNivel5().equals(hhppVtDto.opcionNivel5)
                            && cmtHhppVtMgl.getValorNivel5().equals(String.valueOf(hhpp))) {
                        repetido = true;
                        break;
                    }
                }

            }
        }
        return repetido;
    }
    
    public boolean validarHhppRepEdicion(CmtHhppVtMgl hhppVtMgl, int hhpp, List<CmtHhppVtMgl> listaBdTecnologia) {
        boolean repetido = false;
        if (listaBdTecnologia != null && !listaBdTecnologia.isEmpty()) {
            for (CmtHhppVtMgl cmtHhppVtMgl : listaBdTecnologia) {
                if (hhpp != 0 && hhppVtMgl.getOpcionNivel5() != null && hhppVtMgl.getOpcionNivel6() != null) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equals(hhppVtMgl.getOpcionNivel5())
                            && cmtHhppVtMgl.getOpcionNivel6().equals(hhppVtMgl.getOpcionNivel6())
                            && cmtHhppVtMgl.getValorNivel5().equals(hhppVtMgl.getValorNivel5())
                            && cmtHhppVtMgl.getValorNivel6().equals(String.valueOf(hhpp))) {
                        repetido = true;
                        break;
                    }
                } else {
                    if (cmtHhppVtMgl.getOpcionNivel5().equals(hhppVtMgl.getOpcionNivel5())
                            && cmtHhppVtMgl.getValorNivel5().equals(String.valueOf(hhpp))) {
                        repetido = true;
                        break;
                    }
                }

            }
        }
 
        return repetido;
    }

    public void setListCmtSubEdificiosVt(List<CmtSubEdificiosVt> listCmtSubEdificiosVt) {
        this.listCmtSubEdificiosVt = listCmtSubEdificiosVt;
    }

    public List<ParametrosCalles> getDirNivel5List() {
        return dirNivel5List;
    }

    public void setDirNivel5List(List<ParametrosCalles> dirNivel5List) {
        this.dirNivel5List = dirNivel5List;
    }

    public List<ParametrosCalles> getDirNivel6List() {
        return dirNivel6List;
    }

    public void setDirNivel6List(List<ParametrosCalles> dirNivel6List) {
        this.dirNivel6List = dirNivel6List;
    }

    public List<CmtBasicaMgl> getListaInterioresCasas() {
        return listaInterioresCasas;
    }

    public void setListaInterioresCasas(List<CmtBasicaMgl> listaInterioresCasas) {
        this.listaInterioresCasas = listaInterioresCasas;
    }

    public Boolean getValidarEdicion() {
        return validarEdicion;
    }

    public void setValidarEdicion(Boolean validarEdicion) {
        this.validarEdicion = validarEdicion;
    }

    public Boolean getValidarCasas() {
        return validarCasas;
    }

    public void setValidarCasas(Boolean validarCasas) {
        this.validarCasas = validarCasas;
    }

    public Boolean getValidarTorre() {
        return validarTorre;
    }

    public void setValidarTorre(Boolean validarTorre) {
        this.validarTorre = validarTorre;
    }

    public Boolean getValidarTorreManual() {
        return validarTorreManual;
    }

    public void setValidarTorreManual(Boolean validarTorreManual) {
        this.validarTorreManual = validarTorreManual;
    }

    public class HhppVtDto {
        //TODO volver contantes los tipos de creacion

        private int cantidad;
        private int iniRango;
        private int finRango;
        private String direccionSub;
        private String nombreSub;
        private String opcionNivel5;
        private String valorNivel5;
        private String opcionNivel6;
        private String valorNivel6;
        private boolean btnDisable;
        private String thhVtIdPreCargado;
        private String thhVtIdSelect;

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public int getIniRango() {
            return iniRango;
        }

        public void setIniRango(int iniRango) {
            this.iniRango = iniRango;
        }

        public int getFinRango() {
            return finRango;
        }

        public void setFinRango(int finRango) {
            this.finRango = finRango;
        }

        public String getOpcionNivel5() {
            return opcionNivel5;
        }

        public void setOpcionNivel5(String opcionNivel5) {
            this.opcionNivel5 = opcionNivel5;
        }

        public String getValorNivel5() {
            return valorNivel5;
        }

        public void setValorNivel5(String valorNivel5) {
            this.valorNivel5 = valorNivel5;
        }

        public String getOpcionNivel6() {
            return opcionNivel6;
        }

        public void setOpcionNivel6(String opcionNivel6) {
            this.opcionNivel6 = opcionNivel6;
        }

        public String getValorNivel6() {
            return valorNivel6;
        }

        public void setValorNivel6(String valorNivel6) {
            this.valorNivel6 = valorNivel6;
        }

        public String getDireccionSub() {
            return direccionSub;
        }

        public void setDireccionSub(String direccionSub) {
            this.direccionSub = direccionSub;
        }

        public String getNombreSub() {
            return nombreSub;
        }

        public void setNombreSub(String nombreSub) {
            this.nombreSub = nombreSub;
        }

        public boolean isBtnDisable() {
            return btnDisable;
        }

        public void setBtnDisable(boolean btnDisable) {
            this.btnDisable = btnDisable;
        }

        public String getThhVtIdPreCargado() {
            return thhVtIdPreCargado;
        }

        public void setThhVtIdPreCargado(String thhVtIdPreCargado) {
            this.thhVtIdPreCargado = thhVtIdPreCargado;
        }

        public String getThhVtIdSelect() {
            return thhVtIdSelect;
        }

        public void setThhVtIdSelect(String thhVtIdSelect) {
            this.thhVtIdSelect = thhVtIdSelect;
        }

    
        
    }

    public class HhppVtCasasDto {

        private CmtSubEdificiosVt cmtSubEdificiosVtSelected;
        private String direccionSub;
        private String nombreSub;
        private String opcionNivel4;
        private String valorNivel4;
        private String opcionNivel5;
        private String valorNivel5;
        private String opcionNivel6;
        private String valorNivel6;
        private String itTipoPlaca6;
        private String itValorPlaca6;
        private String direccionAValidar;
        private int procesado = 0;
        private String thhVtId;

        public String getOpcionNivel5() {
            return opcionNivel5;
        }

        public void setOpcionNivel5(String opcionNivel5) {
            this.opcionNivel5 = opcionNivel5;
        }

        public String getValorNivel5() {
            return valorNivel5;
        }

        public void setValorNivel5(String valorNivel5) {
            this.valorNivel5 = valorNivel5;
        }

        public String getOpcionNivel6() {
            return opcionNivel6;
        }

        public void setOpcionNivel6(String opcionNivel6) {
            this.opcionNivel6 = opcionNivel6;
        }

        public String getValorNivel6() {
            return valorNivel6;
        }

        public void setValorNivel6(String valorNivel6) {
            this.valorNivel6 = valorNivel6;
        }

        public String getDireccionSub() {
            return direccionSub;
        }

        public void setDireccionSub(String direccionSub) {
            this.direccionSub = direccionSub;
        }

        public String getNombreSub() {
            return nombreSub;
        }

        public void setNombreSub(String nombreSub) {
            this.nombreSub = nombreSub;
        }

        public String getOpcionNivel4() {
            return opcionNivel4;
        }

        public void setOpcionNivel4(String opcionNivel4) {
            this.opcionNivel4 = opcionNivel4;
        }

        public String getValorNivel4() {
            return valorNivel4;
        }

        public void setValorNivel4(String valorNivel4) {
            this.valorNivel4 = valorNivel4;
        }

        public CmtSubEdificiosVt getCmtSubEdificiosVtSelected() {
            return cmtSubEdificiosVtSelected;
        }

        public void setCmtSubEdificiosVtSelected(CmtSubEdificiosVt cmtSubEdificiosVtSelected) {
            this.cmtSubEdificiosVtSelected = cmtSubEdificiosVtSelected;
        }

        public int getProcesado() {
            return procesado;
        }

        public void setProcesado(int procesado) {
            this.procesado = procesado;
        }

        public String getItTipoPlaca6() {
            return itTipoPlaca6;
        }

        public void setItTipoPlaca6(String itTipoPlaca6) {
            this.itTipoPlaca6 = itTipoPlaca6;
        }

        public String getItValorPlaca6() {
            return itValorPlaca6;
        }

        public void setItValorPlaca6(String itValorPlaca6) {
            this.itValorPlaca6 = itValorPlaca6;
        }

        public String getDireccionAValidar() {
            return direccionAValidar;
        }

        public void setDireccionAValidar(String direccionAValidar) {
            this.direccionAValidar = direccionAValidar;
        }

        public String getThhVtId() {
            return thhVtId;
    }

        public void setThhVtId(String thhVtId) {
            this.thhVtId = thhVtId;
        }
        
        
        
        
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Boolean getValidarCampus() {
        return validarCampus;
    }

    public void setValidarCampus(Boolean validarCampus) {
        this.validarCampus = validarCampus;
    }

    public String getDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(String direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public List<OpcionIdNombre> getCkList() {
        return ckList;
    }

    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
    }

    public List<OpcionIdNombre> getItList() {
        return itList;
    }

    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    public List<OpcionIdNombre> getAptoList() {
        return aptoList;
    }

    public void setAptoList(List<OpcionIdNombre> aptoList) {
        this.aptoList = aptoList;
    }

    public List<OpcionIdNombre> getBmList() {
        return bmList;
    }

    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    public List<OpcionIdNombre> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    public HhppVtCasasDto getHhppDto() {
        return hhppDto;
    }

    public void setHhppDto(HhppVtCasasDto hhppDto) {
        this.hhppDto = hhppDto;
    }

    public List<CmtHhppVtMgl> getListHhppByCasas() {
        return listHhppByCasas;
    }

    public void setListHhppByCasas(List<CmtHhppVtMgl> listHhppByCasas) {
        this.listHhppByCasas = listHhppByCasas;
    }

    public List<CmtHhppVtMgl> getListHhppCargue() {
        return listHhppCargue;
    }

    public void setListHhppCargue(List<CmtHhppVtMgl> listHhppCargue) {
        this.listHhppCargue = listHhppCargue;
    }

    public boolean isHabilitarColums() {
        return habilitarColums;
    }

    public void setHabilitarColums(boolean habilitarColums) {
        this.habilitarColums = habilitarColums;
    }

    public List<HhppVtCasasDto> getListHhppVtCasasDto() {
        return listHhppVtCasasDto;
    }

    public void setListHhppVtCasasDto(List<HhppVtCasasDto> listHhppVtCasasDto) {
        this.listHhppVtCasasDto = listHhppVtCasasDto;
    }

    public Boolean getValidarCargaDatos() {
        return validarCargaDatos;
    }

    public void setValidarCargaDatos(Boolean validarCargaDatos) {
        this.validarCargaDatos = validarCargaDatos;
    }

    public boolean isHabilitarColumsComp() {
        return habilitarColumsComp;
    }

    public void setHabilitarColumsComp(boolean habilitarColumsComp) {
        this.habilitarColumsComp = habilitarColumsComp;
    }

    public boolean isQuitarComplemeto() {
        return quitarComplemeto;
    }

    public void setQuitarComplemeto(boolean quitarComplemeto) {
        this.quitarComplemeto = quitarComplemeto;
    }

    public boolean isDireccionGeo() {
        return direccionGeo;
    }

    public void setDireccionGeo(boolean direccionGeo) {
        this.direccionGeo = direccionGeo;
    }

    public CmtSubEdificiosVt getCmtSubEdificiosVtSub() {
        return cmtSubEdificiosVtSub;
    }

    public void setCmtSubEdificiosVtSub(CmtSubEdificiosVt cmtSubEdificiosVtSub) {
        this.cmtSubEdificiosVtSub = cmtSubEdificiosVtSub;
    }

    public boolean isNombreRR() {
        return nombreRR;
    }

    public void setNombreRR(boolean nombreRR) {
        this.nombreRR = nombreRR;
    }

    public boolean isHabilitaCampoTextoProcesoRr() {
        return habilitaCampoTextoProcesoRr;
    }

    public void setHabilitaCampoTextoProcesoRr(boolean habilitaCampoTextoProcesoRr) {
        this.habilitaCampoTextoProcesoRr = habilitaCampoTextoProcesoRr;
    }

    public boolean isHabilitarCampoRR() {
        return habilitarCampoRR;
    }

    public void setHabilitarCampoRR(boolean habilitarCampoRR) {
        this.habilitarCampoRR = habilitarCampoRR;
    }

    public void setNuevoApartamentoRr(String nuevoApartamentoRr) {
        this.nuevoApartamentoRr = nuevoApartamentoRr;
    }

    public List<TipoHhppMgl> getListaTipoHhpp() {
        return listaTipoHhpp;
    }

    public void setListaTipoHhpp(List<TipoHhppMgl> listaTipoHhpp) {
        this.listaTipoHhpp = listaTipoHhpp;
    }

    public String getTipoHhppMgl() {
        return tipoHhppMgl;
    }

    public void setTipoHhppMgl(String tipoHhppMgl) {
        this.tipoHhppMgl = tipoHhppMgl;
    }

    public boolean isValidarFlujoOt() {
        return validarFlujoOt;
    }

    public void setValidarFlujoOt(boolean validarFlujoOt) {
        this.validarFlujoOt = validarFlujoOt;
    }
    
    
    }
