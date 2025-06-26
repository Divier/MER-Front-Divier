package co.com.claro.mgl.mbeans.cm;

/**
 *
 * @author bocanegravm
 */
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadesRrDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.dtos.FiltroConsultaExtendidaTipoTrabajoDtos;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTipoTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaExtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;


@ManagedBean(name = "cmtInfoAdiMantenimientoBean")
@ViewScoped
public class CmtInfoAdiMantenimientoBean implements Serializable {
    
    private static final int MAX_LENGTH_VALUE = 200;

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    @EJB
    private CmtExtendidaTecnologiaMglFacadeLocal mglExtendidaTecnologiaFacadeLocal;
    @EJB
    private CmtTipoBasicaExtMglFacadeLocal tipoBasicaExtFacade;
    @EJB
    private CmtComunidadRrFacadeLocal cmtComunidadRrFacadeLocal;
    @EJB
    private CmtExtendidaTipoTrabajoMglFacadeLocal cmtExtendidaTipoTrabajoMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
        
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(CmtInfoAdiMantenimientoBean.class);
    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<CmtBasicaMgl> tablasBasicasMglList;
    private List<CmtTipoBasicaMgl> TablasTipoBasicasMglList;
    private String idSqlSelected;
    private String filtroTablaBasicaSelected;
    private String codigo;
    private String tablaTipoBasicaMgl;
    private boolean guardado;
    private String certificado;
    public BigDecimal idTablasBasicas;
    private boolean otroEstado;
    private List<String> brList;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private CmtTipoBasicaMgl cmtTipoBasicaMglSelected;
    private boolean renderAuditoria = false;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionBasica = "";
    private String tipoBasicaDescripcion = "";
    private FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
    private List<CmtBasicaMgl> modulosTblBasica;
    private SecurityLogin securityLogin;
    private Boolean crearBasica = Boolean.FALSE;
    private Boolean editarBasica = Boolean.FALSE;
    private Boolean borrarBasica = Boolean.FALSE;
    private CmtBasicaMgl TablasBasicasMgl = null;
    private String message;
    private static final String NO_APLICA = "No Aplica";
    private static final String NO_REQUERIDO = "No Requerido";
    private boolean tblTecnologia = false;
    private boolean tblTiposTrabajo = false;
    private boolean activeBottonInfoAdi = false;
    private boolean habilitaPanelInfoAdi = false;
    private boolean selectTodos = true;
    private boolean deshacerTodos = false;
    private int tipoVivienda;
    private String[] tipoViviendaSelected;
    private List<ParametrosCalles> listTipoNivel5;
    private CmtExtendidaTecnologiaMgl mglExtendidaTecnologia;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private List<CmtTipoBasicaExtMgl> listCamposTipoBasicaExt;
    private List<CmtBasicaExtMgl> basicaExtMgls;//valbuenayf
    private List<CmtComunidadRr> lstComunidadesMgls;
    private CmtFiltroConsultaComunidadesRrDto cmtFiltroConsultaComunidadesRrDto;
    private static final int numeroRegistrosConsulta = 10;
    private int actual;
    private List<CmtExtendidaTipoTrabajoMgl> lstInfoAdiTipoTrabajoMgls;
    private CmtExtendidaTipoTrabajoMgl cmtExtendidaTipoTrabajoMgl;
    private List<CmtBasicaMgl> basicasMglNombresTecOFSClist;
    private BigDecimal nombreTecnoOFSCSelected;
    private boolean mostrarPopupCom;
    private List<CmtComunidadRr> lstCmtComunidadRrs = new ArrayList<>();
    private String pageActualExtTipoWork;
    private String numPaginaExtTipoWork = "1";
    private List<Integer> paginaListExtTipoWork;
    private static final int numeroRegistrosConsultaExtTipoWork = 4;
    private int actualExtTipoWork;
    private boolean lblAbreviatura;
    private boolean lblCodigoOfsc;
    private FiltroConsultaExtendidaTipoTrabajoDtos filtroConsultaExtendidaTipoTrabajoDtos= new FiltroConsultaExtendidaTipoTrabajoDtos();
    private UploadedFile fileMasivoUbicacion;
    private static final String NOM_COLUMNAS = "TIPOTRABAJO;COMUNIDAD;CODIGOLOCATION";
    private List<String> lineasArchivoRetorno;
    private String msgError;
    private final Locale LOCALE = new Locale("es", "CO");
    private boolean isResultado;
    private CmtBasicaExtMgl cuadrantesNodo;
    private String ofsc;
    private String moduloGestion;
    private String sgo;
    private String campoCuadrantes ="CUADRANTES_A_NODOS";

    public CmtInfoAdiMantenimientoBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }

            if (session == null) {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
            }


            if (session != null) {
                usuarioVT = securityLogin.getLoginUser();
                perfilVT = securityLogin.getPerfilUsuario();
                mglExtendidaTecnologia = new CmtExtendidaTecnologiaMgl();
                cmtExtendidaTipoTrabajoMgl = new CmtExtendidaTipoTrabajoMgl();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }

                CmtBasicaMgl idTablasBasicasMgl = (CmtBasicaMgl) session.getAttribute("idTablasBasicasMgl");
                session.setAttribute("tablasBasicasMgl", idTablasBasicasMgl);

                if (idTablasBasicasMgl != null) {
                    session.removeAttribute("idTablasBasicasMgl");
                    TablasBasicasMgl = idTablasBasicasMgl;
                    idTablasBasicasMgl.setJustificacion("");
                    if (TablasBasicasMgl.getTipoBasicaObj() != null) {
                        if (TablasBasicasMgl.getTipoBasicaObj().getComplemento() != null) {
                            if (TablasBasicasMgl.getTipoBasicaObj().getComplemento().contains("infoAdi")) {
                                activeBottonInfoAdi = true;
                            } else {
                                activeBottonInfoAdi = false;
                            }
                        }
                    }
                } else {
                    cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
                    TablasBasicasMgl = new CmtBasicaMgl();
                    TablasBasicasMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                    TablasBasicasMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);
                    TablasBasicasMgl.setBasicaId(BigDecimal.ZERO);
                    TablasBasicasMgl.setActivado("N");
                    TablasBasicasMgl.setCodigoBasica("");
                    if (cmtTipoBasicaMglSelected != null) {
                        TablasBasicasMgl.setAbreviatura(cmtTipoBasicaMglSelected.getInicialesAbreviatura());
                    }
                }
            }
            else {
                throw new ApplicationException("No fue posible obtener la sesión. Por favor ingrese nuevamente.");
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error al momento de instanciar el formulario: " + e.getMessage(), e, LOGGER);
        }
    }
    

    @PostConstruct
    public void fillSqlList() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacade.findByCodigoInternoApp(Constant.TIPO_BASICA_MODULOS_APLICAR_TIPO_BASICA);
            modulosTblBasica = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            cmtBasicaMglFacade.setUser(usuarioVT, perfilVT);
            tablasBasicasMglList = new ArrayList<>();
            TablasTipoBasicasMglList = new ArrayList<>();
            basicasMglNombresTecOFSClist = new ArrayList<>();
            TablasTipoBasicasMglList = cmtTipoBasicaMglFacade.findAll();
            cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
            cmtFiltroConsultaComunidadesRrDto = new CmtFiltroConsultaComunidadesRrDto();
            filtroConsultaExtendidaTipoTrabajoDtos = new FiltroConsultaExtendidaTipoTrabajoDtos();
            
            CmtTipoBasicaMgl tipoBasicaNombresTecOFSC;
            tipoBasicaNombresTecOFSC = cmtTipoBasicaMglFacade.findByCodigoInternoApp(Constant.TIPO_BASICA_NOMBRES_TECNOLOGIAS_OFSC);
           
            if (tipoBasicaNombresTecOFSC != null && tipoBasicaNombresTecOFSC.getTipoBasicaId() != null) {

                basicasMglNombresTecOFSClist = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaNombresTecOFSC);
            }

            if (cmtTipoBasicaMglSelected == null) {
                cmtTipoBasicaMglSelected = new CmtTipoBasicaMgl();
                cmtTipoBasicaMglSelected.setNombreTipo("");
            } else {
                tablaTipoBasicaMgl = cmtTipoBasicaMglSelected.getTipoBasicaId().toString();
                validarAcciones(cmtTipoBasicaMglSelected.getNombreTipo());
                filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
                filtroConjsultaBasicasDto.setIdTipoBasica(cmtTipoBasicaMglSelected.getTipoBasicaId());
                tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
                if (TablasBasicasMgl.getTipoBasicaObj() != null && TablasBasicasMgl.getTipoBasicaObj().getTipoBasicaId() != null) {
                    if (TablasBasicasMgl.getCodigoBasica().equals("")) {
                        TablasBasicasMgl.setCodigoBasica(
                                cmtBasicaMglFacade.buscarUltimoCodigoNumerico(cmtTipoBasicaMglSelected));
                    }
                }
            }
            if (TablasBasicasMgl.getTipoBasicaObj().getNombreTipo().
                    equalsIgnoreCase(Constant.TECNOLOGIA)) {
                cargarInformacion();
                lblAbreviatura=true;
                lblCodigoOfsc= false;
            } else {
                cargarInformacionTipoTrabajo();
                 lblAbreviatura=false;
                lblCodigoOfsc= true;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName()+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarPanelInfoAdicional() {
         tipoVivienda=0;
         isResultado = false;
        if (TablasBasicasMgl.getTipoBasicaObj().getNombreTipo().
                equalsIgnoreCase(Constant.TECNOLOGIA)) {
            tblTecnologia = true;
        } else {
            try {
                tblTiposTrabajo = true;
                lstComunidadesMgls = cmtComunidadRrFacadeLocal.findAll();
                listInfoByPageExtTipoWork(1);
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName() + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            }

        }

    }

    public void cargarInformacionTipoTrabajo() {

        try {
            
            listCamposTipoBasicaExt = tipoBasicaExtFacade.getCamposAdic(cmtTipoBasicaMglSelected);
            if (TablasBasicasMgl.getBasicaId() == null) {
                for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                    CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                    basicaExtMgl.setIdTipoBasicaExt(extMgl);
                    basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                    basicaExtMgls.add(basicaExtMgl);
                }
            } else {
                basicaExtMgls = TablasBasicasMgl.getListCmtBasicaExtMgl();

                if (basicaExtMgls == null || basicaExtMgls.isEmpty()) {
                    basicaExtMgls = new ArrayList<>();
                    for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                        CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                        basicaExtMgl.setIdTipoBasicaExt(extMgl);
                        basicaExtMgl.setFechaCreacion(new Date());
                        basicaExtMgl.setEstadoRegistro(1);
                        basicaExtMgl.setUsuarioCreacion(usuarioVT);
                        basicaExtMgl.setPerfilCreacion(perfilVT);
                        basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                        basicaExtMgl.setValorExtendido("N");
                        basicaExtMgls.add(basicaExtMgl);
                    }
                }
                TablasBasicasMgl.setListCmtBasicaExtMgl(basicaExtMgls);
            }
            
            listInfoByPageExtTipoWork(1);
            lstComunidadesMgls = cmtComunidadRrFacadeLocal.findAll();
            if (lstInfoAdiTipoTrabajoMgls.size() > 0) {
                tblTiposTrabajo = true;
            } else {
                tblTiposTrabajo = false;
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void cargarInformacion() {
        try {
            //inicio ajuste sincroniza con RR valbuenayf
            listCamposTipoBasicaExt = tipoBasicaExtFacade.getCamposAdic(cmtTipoBasicaMglSelected);
            if (TablasBasicasMgl.getBasicaId() == null) {
                for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                    CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                    basicaExtMgl.setIdTipoBasicaExt(extMgl);
                    basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                    basicaExtMgls.add(basicaExtMgl);
                                    if(extMgl.getLabelCampo().equals(campoCuadrantes)){
                        cuadrantesNodo = basicaExtMgl;
                    }else{
                        basicaExtMgls.add(basicaExtMgl);
                    }
                }
            } else {
                basicaExtMgls = TablasBasicasMgl.getListCmtBasicaExtMgl();
                
                if (basicaExtMgls == null || basicaExtMgls.isEmpty()) {
                    basicaExtMgls = new ArrayList<>();
                    for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                        CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                        basicaExtMgl.setIdTipoBasicaExt(extMgl);
                        basicaExtMgl.setFechaCreacion(new Date());
                        basicaExtMgl.setEstadoRegistro(1);
                        basicaExtMgl.setUsuarioCreacion(usuarioVT);
                        basicaExtMgl.setPerfilCreacion(perfilVT);
                        basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                        basicaExtMgls.add(basicaExtMgl);
                        if(extMgl.getLabelCampo().equals(campoCuadrantes)){
                            cuadrantesNodo = basicaExtMgl;
                        }else{
                            basicaExtMgls.add(basicaExtMgl);
                        }
                    }
                }else{
                    for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                        if(extMgl.getLabelCampo().equals(campoCuadrantes)){
                            CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                            basicaExtMgl.setIdTipoBasicaExt(extMgl);
                            basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                            basicaExtMgl.setValorExtendido("N");
                            cuadrantesNodo = basicaExtMgl;
                            break;
                        }
                    }
                }
                for(CmtBasicaExtMgl basicaExt: basicaExtMgls ){
                    if(basicaExt.getIdTipoBasicaExt().getLabelCampo().equals(campoCuadrantes)){
                        basicaExtMgls.remove(basicaExt);
                        break;
                    }
                }
                TablasBasicasMgl.setListCmtBasicaExtMgl(basicaExtMgls);
            }
            
            ofsc = cuadrantesNodo.getValorExtendido();
            moduloGestion = cuadrantesNodo.getValorExtendido();
            sgo = cuadrantesNodo.getValorExtendido();
            //Fin ajuste sincroniza con RR valbuenayf
            buscarTipoVivienda();
            tblTecnologia = false;
            mglExtendidaTecnologia = mglExtendidaTecnologiaFacadeLocal.findBytipoTecnologiaObj(TablasBasicasMgl);
 
            if(mglExtendidaTecnologia.getLegados()!= null){
                if(mglExtendidaTecnologia.getLegados().contains("MG")){
                    moduloGestion = "Y";
                }
                
                if(mglExtendidaTecnologia.getLegados().contains("SGO")){
                    sgo = "Y";
                }
                
                if(mglExtendidaTecnologia.getLegados().contains("OFSC")){
                    ofsc = "Y";
                }
            }
            
            if (mglExtendidaTecnologia.getIdExtTec() != null) {
                tblTecnologia = true;
                habilitaPanelInfoAdi = false;
                tipoVivienda = 1;
                if (mglExtendidaTecnologia.getTiposVivienda().equals(NO_APLICA)) {
                    tipoVivienda = 3;
                } else if (mglExtendidaTecnologia.getTiposVivienda().equals(NO_REQUERIDO)) {
                    tipoVivienda = 2;
                } else {
                    habilitaPanelInfoAdi = true;
                    tipoViviendaSelected = mglExtendidaTecnologia.getTiposVivienda().split("\\|");
                }
                if (mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                    nombreTecnoOFSCSelected = mglExtendidaTecnologia.getNomTecnologiaOFSC().getBasicaId();
                }
            } else {
                tblTecnologia = false;
            }
        } catch (ApplicationException e) {
             String msn = "Proceso fall\u00F3: No se encontraron registros";
              FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void crearInformacionAdicional() {
        isResultado = false;
        if (tipoVivienda == 1) {
            if (tipoViviendaSelected.length == 0) {
                String msn = "Proceso fall\u00F3:  Debe seleccionar alguna unidad";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            String armaTipViv = regresaCadenaTipoVivienda(tipoViviendaSelected);
            mglExtendidaTecnologia.setTiposVivienda(armaTipViv);

            mglExtendidaTecnologia.setFechaCreacion(new Date());
            mglExtendidaTecnologia.setTipoTecnologiaObj(TablasBasicasMgl);

            if (nombreTecnoOFSCSelected != null) {
                try {
                    CmtBasicaMgl nombreTecOFSC = cmtBasicaMglFacadeLocal.findById(nombreTecnoOFSCSelected);
                    if (nombreTecOFSC.getBasicaId() != null) {
                        mglExtendidaTecnologia.setNomTecnologiaOFSC(nombreTecOFSC);
                    }
                } catch (ApplicationException e) {
                     FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName() + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
                }
            }
            try {
                mglExtendidaTecnologiaFacadeLocal.create(mglExtendidaTecnologia, usuarioVT, perfilVT);
                if (mglExtendidaTecnologia.getIdExtTec() != null) {
                    String msn = "Proceso Exitoso: se ha creado la información adicional "
                            + "  asociada a la tecnolog&iacute;a <b>"
                            + TablasBasicasMgl.getCodigoBasica() + "</b>";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    String msn = "Proceso fall\u00F3: ha ocurrido un problema al momento de crear el registro ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError( "Proceso fall\u00F3: " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            }
        } else {
            if (tipoVivienda == 2) {
                mglExtendidaTecnologia.setTiposVivienda("No Requerido");
            } else {
                mglExtendidaTecnologia.setTiposVivienda("No Aplica");
            }

            mglExtendidaTecnologia.setFechaEdicion(new Date());
            mglExtendidaTecnologia.setTipoTecnologiaObj(TablasBasicasMgl);

            if (nombreTecnoOFSCSelected != null) {
                try {
                    CmtBasicaMgl nombreTecOFSC = cmtBasicaMglFacadeLocal.findById(nombreTecnoOFSCSelected);
                    if (nombreTecOFSC.getBasicaId() != null) {
                        mglExtendidaTecnologia.setNomTecnologiaOFSC(nombreTecOFSC);
                    }
                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName() + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
                }
            }
            try {
                mglExtendidaTecnologiaFacadeLocal.create(mglExtendidaTecnologia, usuarioVT, perfilVT);
                if (mglExtendidaTecnologia.getIdExtTec() != null) {
                    String msn = "Proceso Exitoso: se ha creado la información adicional "
                            + "  asociada a la tecnolog&iacute;a <b>"
                            + TablasBasicasMgl.getCodigoBasica() + "</b>";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    String msn = "Proceso fall\u00F3: ha ocurrido un problema al momento de crear el registro ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } catch (ApplicationException e) {
                 FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            }
        }
    }

    public void actualizarInformacionAdicional() {
        isResultado = false;
        String nodosActualizar = null;
        if(moduloGestion!= null && moduloGestion.equals("Y")){
            nodosActualizar = "MG";
        }
        
        if(sgo!= null && sgo.equals("Y")){
            if(nodosActualizar!=null){
                nodosActualizar = nodosActualizar + "/" +"SGO";
            }else{
                nodosActualizar = "SGO";
            }
        }
        
        if(ofsc!=null && ofsc.equals("Y")){
            if(nodosActualizar!=null){
                nodosActualizar = nodosActualizar + "/" +"OFSC";
            }else{
                nodosActualizar = "OFSC";
            }
        }
        mglExtendidaTecnologia.setLegados(nodosActualizar);
        if (tipoVivienda == 1) {
            if (tipoViviendaSelected.length > 0) {
                String armaTipViv = regresaCadenaTipoVivienda(tipoViviendaSelected);
                mglExtendidaTecnologia.setTiposVivienda(armaTipViv);
                mglExtendidaTecnologia.setFechaEdicion(new Date());
                if (nombreTecnoOFSCSelected != null) {
                    try {
                        CmtBasicaMgl nombreTecOFSC = cmtBasicaMglFacadeLocal.findById(nombreTecnoOFSCSelected);
                        if (nombreTecOFSC.getBasicaId() != null) {
                            mglExtendidaTecnologia.setNomTecnologiaOFSC(nombreTecOFSC);
                        }
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                    }
                }
                try {
                    mglExtendidaTecnologiaFacadeLocal.update(mglExtendidaTecnologia, usuarioVT, perfilVT);
                    if (mglExtendidaTecnologia.getIdExtTec() != null) {
                        String msn = "Proceso Exitoso: se ha modificado la información adicional "
                                + "  asociada a la tecnolog&iacute;a <b>"
                                + TablasBasicasMgl.getCodigoBasica() + "</b>";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } else {
                        String msn = "Proceso fall\u00F3: ha ocurrido un problema al momento de actualizar el registro ";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }

                } catch (ApplicationException e) {
                     FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
                }
            } else {
                String msn = "Proceso fall\u00F3:  Debe seleccionar alguna unidad";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } else {
            if (tipoVivienda == 2) {
                mglExtendidaTecnologia.setTiposVivienda("No Requerido");
            } else {
                mglExtendidaTecnologia.setTiposVivienda("No Aplica");
            }

            mglExtendidaTecnologia.setFechaEdicion(new Date());
            if (nombreTecnoOFSCSelected != null) {
                try {
                    CmtBasicaMgl nombreTecOFSC = cmtBasicaMglFacadeLocal.findById(nombreTecnoOFSCSelected);
                    if (nombreTecOFSC.getBasicaId() != null) {
                        mglExtendidaTecnologia.setNomTecnologiaOFSC(nombreTecOFSC);
                    }
                } catch (ApplicationException e) {
                     FacesUtil.mostrarMensajeError(CmtInfoAdiMantenimientoBean.class.getName() + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
                }
            }
            try {
                mglExtendidaTecnologiaFacadeLocal.update(mglExtendidaTecnologia, usuarioVT, perfilVT);
                if (mglExtendidaTecnologia.getIdExtTec() != null) {
                    String msn = "Proceso Exitoso: se ha modificado la información adicional "
                            + "  asociada a la tecnologia <b>"
                            + TablasBasicasMgl.getCodigoBasica() + "</b>";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    String msn = "Proceso fall\u00F3: ha ocurrido un problema al momento de actualizar el registro ";
                    FacesContext.getCurrentInstance()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            }
        }
    }

    public void eliminarInformacionAdicional() {
        try {
            isResultado = false;
            boolean respuesta =
                    mglExtendidaTecnologiaFacadeLocal.delete(mglExtendidaTecnologia, usuarioVT, perfilVT);
            tipoViviendaSelected = new String[0];
            if (respuesta) {
                String msn = "Proceso Exitoso: se ha eliminado la información adicional "
                        + " asociada a la tecnolog&iacute;a <b>"
                        + TablasBasicasMgl.getCodigoBasica() + "</b>";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                cargarInformacion();
            } else {
                String msn = "Proceso fall\u00F3: ha ocurrido un problema al momento de eliminar el registro ";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public String regresaCadenaTipoVivienda(String[] tipoVivienda) {
        String tipVda = "";
        int i = 0;
        for (String s : tipoVivienda) {
            if (i == 0) {
                tipVda = s;
            } else {
                tipVda = tipVda + "|" + s;
            }
            i++;
        }
        return tipVda;
    }

    public void findByFiltro() {
        try {
            tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
            if (tablasBasicasMglList != null && !tablasBasicasMglList.isEmpty()) {
                pageFirst();
            }
        } catch (ApplicationException e) {
              String msn = "Se presento un al aplicar filtro";
              FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void buscarTipoVivienda() {
        try {
            listTipoNivel5 = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTO");
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void validacambio() {
        if (tipoVivienda == 1) {
            habilitaPanelInfoAdi = true;
        } else {
            habilitaPanelInfoAdi = false;
        }
    }

    public void seleccionarTodos() {
        isResultado = false;
        if (selectTodos) {
            String tipVda = "";
            int i = 0;
            for (ParametrosCalles parametrosCalles : listTipoNivel5) {
                if (i == 0) {
                    tipVda = parametrosCalles.getDescripcion();
                } else {
                    tipVda = tipVda + "|" + parametrosCalles.getDescripcion();
                }
                i++;
            }
            tipoViviendaSelected = tipVda.split("\\|");
            deshacerTodos = true;
            selectTodos = false;
        } else {
            tipoViviendaSelected = null;
            deshacerTodos = false;
            selectTodos = true;
        }
    }

    public String volverList() {
        session.setAttribute("regresaMto", true);
        return "mantenimientoTablas";
    }

    public void ocultarAuditoria() {
        renderAuditoria = false;
    }

    /**
     * M&eacute;todo para validar las acciones permitidas por rol para el tipo
     * de b&aacute;sica seleccionada
     *
     * @param tipoBasica String tipo de tabla b&aacute;sica seleccionada
     */
    private void validarAcciones(String tipoBasica) {
        this.crearBasica = validarAccion(tipoBasica, ValidacionUtil.OPC_CREACION);
        this.editarBasica = validarAccion(tipoBasica, ValidacionUtil.OPC_EDICION);
        this.borrarBasica = validarAccion(tipoBasica, ValidacionUtil.OPC_BORRADO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @param formulario String con el tipo de tabla b&acute;sica a validar
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String formulario, String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de validar la acción. EX000: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error al momento de validar la acción: " + e.getMessage(), e);
            return false;
        }
    }

    public void actualizarlTablasBasicasMgl() {
        try {
            if (StringUtils.exceedsMaxLengthValue(TablasBasicasMgl.getDescripcion(), MAX_LENGTH_VALUE)) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (StringUtils.exceedsMaxLengthValue(TablasBasicasMgl.getJustificacion(), MAX_LENGTH_VALUE)) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            isResultado = false;
            cmtBasicaMglFacade.update(TablasBasicasMgl);
            String msn = "Proceso exitoso: Se ha modificado la tabla b&aacute;sica: <b>"
                    + TablasBasicasMgl.getBasicaId() + "</b>";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            String msn = "Proceso fall\u00F3: " + e.getMessage();
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void crearTablasBasicasMgl() {
        try {
            if (StringUtils.exceedsMaxLengthValue(TablasBasicasMgl.getDescripcion(), MAX_LENGTH_VALUE)) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
            TablasBasicasMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);

            if (TablasBasicasMgl.getTipoBasicaObj() == null) {
                String msn = "Se debe seleccionar un tipo de tabla Basica en la pantalla anterior.";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            isResultado = false;
            TablasBasicasMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            TablasBasicasMgl.setBasicaId(null);
            TablasBasicasMgl.setCodigoBasica(TablasBasicasMgl.getCodigoBasica().toUpperCase());
            cmtBasicaMglFacade.create(TablasBasicasMgl);
            setGuardado(false);
            String msn = "Proceso exitoso: Se ha creado el la tabla "
                    + "b&aacute;sica:  <b>" + TablasBasicasMgl.getBasicaId() + "</b>";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            session.setAttribute("message", message);
            activeBottonInfoAdi = true;
        } catch (ApplicationException e) {
            setGuardado(false);
            TablasBasicasMgl.setBasicaId(BigDecimal.ZERO);
            TablasBasicasMgl.setCodigoBasica("");
            String msn = "Proceso fall\u00F3: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public String eliminarlTablasBasicasMgl() {
        try {
            if (StringUtils.exceedsMaxLengthValue(TablasBasicasMgl.getDescripcion(), MAX_LENGTH_VALUE)) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return "";
            }
            if (StringUtils.exceedsMaxLengthValue(TablasBasicasMgl.getJustificacion(), MAX_LENGTH_VALUE)) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return "";
            }
            isResultado = false;
            cmtBasicaMglFacade.delete(TablasBasicasMgl);
            String msn = "Proceso exitoso: Se ha eliminado la "
                    + "tabla b&aacute;sica: <b>" + TablasBasicasMgl.getBasicaId() + "</b>";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            session.setAttribute("message", message);
            return "mantenimientoTablas";
        } catch (ApplicationException e) {
            String msn = "Proceso fall\u00F3 : " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return "mantenimientoTablas";
    }

    public void findByFiltroCom() {
            pageFirstCom();
    }

    public void pageFirstCom() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            String msn = "Se genere error al cargar lista de Comunidades:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public String listInfoByPage(int page) {

        try {
            PaginacionDto<CmtComunidadRr> paginacionDto = cmtComunidadRrFacadeLocal.
                    findAllPaginado(page, numeroRegistrosConsulta, cmtFiltroConsultaComunidadesRrDto);
            
            if ( paginacionDto != null && paginacionDto.getListResultado() != null 
                    &&  !paginacionDto.getListResultado().isEmpty()  ){
                lstComunidadesMgls = paginacionDto.getListResultado();
            }else{
                lstComunidadesMgls = null;
            }
            
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pagePreviousCom() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de Comunidades:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtComunidadRr> paginacionDto =
                    cmtComunidadRrFacadeLocal.findAllPaginado(0, numeroRegistrosConsulta,
                    cmtFiltroConsultaComunidadesRrDto);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se genera error al cargar lista de Comunidades:" + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActualCom() {
        try {
            paginaList = new ArrayList<>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void pageNextCom() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de Comunidades:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pageLastCom() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de Comunidades:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }

    }

    public void irPaginaCom() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            String msn = "Se genera error al cargar lista de Comunidades:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }
    
    ////////////PAGINADO EXTENDIDAS TIPO TRABAJO
    
    public String listInfoByPageExtTipoWork(int page) {

        try {
            lstInfoAdiTipoTrabajoMgls = cmtExtendidaTipoTrabajoMglFacadeLocal.
                    findBytipoTrabajoObj(filtroConsultaExtendidaTipoTrabajoDtos, page, numeroRegistrosConsultaExtTipoWork, TablasBasicasMgl);
            actualExtTipoWork = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirstExtTipoWork() {
        try {
            listInfoByPageExtTipoWork(1);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pagePreviousExtTipoWork() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actualExtTipoWork - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageExtTipoWork(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void irPaginaExtTipoWork() {
        try {
            int totalPaginas = getTotalPaginasExtTipoWork();
            int nuevaPageActual = Integer.parseInt(numPaginaExtTipoWork);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageExtTipoWork(nuevaPageActual);
        } catch (NumberFormatException e) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNextExtTipoWork() {
        try {
            int totalPaginas = getTotalPaginasExtTipoWork();
            int nuevaPageActual = actualExtTipoWork + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageExtTipoWork(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pageLastExtTipoWork() {
        try {
            int totalPaginas = getTotalPaginasExtTipoWork();
            listInfoByPageExtTipoWork(totalPaginas);
        } catch (Exception ex) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }

    }

    public String getPageActualExtTipoWork() {
        try {
            paginaListExtTipoWork = new ArrayList<>();
            int totalPaginas = getTotalPaginasExtTipoWork();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaListExtTipoWork.add(i);
            }
            pageActualExtTipoWork = String.valueOf(actualExtTipoWork) + " de "
                    + String.valueOf(totalPaginas);

            if (numPaginaExtTipoWork == null) {
                numPaginaExtTipoWork = "1";
            }
            numPaginaExtTipoWork = String.valueOf(actualExtTipoWork);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return pageActualExtTipoWork;
    }

    public int getTotalPaginasExtTipoWork() {
        try {
                        
            Long count =  cmtExtendidaTipoTrabajoMglFacadeLocal.findExtendidasSearchContar(filtroConsultaExtendidaTipoTrabajoDtos, TablasBasicasMgl);
            int totalPaginas = (int) ((count % numeroRegistrosConsultaExtTipoWork != 0)
                    ? (count / numeroRegistrosConsultaExtTipoWork) + 1
                    : count / numeroRegistrosConsultaExtTipoWork);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se genera error al cargar lista de extendidas Tipo Trabajo:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    
    public String updateInfoAdiTipoTrabajo(CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl) {
          
        isResultado= false;
        if (extendidaTipoTrabajoMgl.getLocationCodigo().isEmpty()
                || extendidaTipoTrabajoMgl.getLocationCodigo().equalsIgnoreCase(null)) {
            String msn = "Proceso fallo: Debe ingresar un codigo Location ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn, ""));
            LOGGER.info(msn);
            return "";
        } else {
            try {
                cmtExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                        update(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);


                String msn = "Proceso exitoso:" + " " + "Se ha modificado el complemento  "
                        + " <b>" + extendidaTipoTrabajoMgl.getIdExtTipTra() + "</b>  para el tipo de trabajo "
                        + " <b>" + TablasBasicasMgl.getNombreBasica() + "</b>  ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msn, ""));
                fillSqlList();
                LOGGER.info(msn);

            } catch (ApplicationException e) {
                String msn = "Proceso fallo: ";
                FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
            }

        }     
       return ""; 
    }
    
    public String guardaComunidadLocationBD() {
        
       isResultado = false;
        if (lstCmtComunidadRrs.size() > 0) {
            int contador = 0;
            if (validarUnicaComunidadXTipoTrabajo()) {
                for (CmtComunidadRr comunidadRr : lstCmtComunidadRrs) {

                    cmtExtendidaTipoTrabajoMgl = new CmtExtendidaTipoTrabajoMgl();
                    cmtExtendidaTipoTrabajoMgl.setTipoTrabajoObj(TablasBasicasMgl);
                    cmtExtendidaTipoTrabajoMgl.setComunidadRrObj(comunidadRr);
                    cmtExtendidaTipoTrabajoMgl.setLocationCodigo(comunidadRr.getCodigoLocation());
                    cmtExtendidaTipoTrabajoMgl.setTecnicoAnticipado("0");
                    cmtExtendidaTipoTrabajoMgl.setAgendaInmediata("0");
                    
                    try {
                        cmtExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                                create(cmtExtendidaTipoTrabajoMgl, usuarioVT, perfilVT);
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                    }

                    if (cmtExtendidaTipoTrabajoMgl.getIdExtTipTra() != null) {
                        contador++;
                    }
                }
                if (contador == lstCmtComunidadRrs.size()) {
                    String msn = "Proceso exitoso:" + " " + "Se han creado los complementos "
                            + " para el tipo de trabajo "
                            + " <b>" + TablasBasicasMgl.getNombreBasica() + "</b>  ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msn, ""));
                    fillSqlList();
                    cerrarPopupCom();
                    LOGGER.info(msn);
                } else {
                    String msn = "Proceso fallo: Ocurrio al menos un error al momento de crear "
                            + " un registro  ";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msn, ""));
                    LOGGER.info(msn);
                    cerrarPopupCom();
                }
            } else {
                String msn = "Proceso fallo: Existen comunidades ya asociadas a  "
                        + "este Tipo de trabajo por favor verifique";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msn, ""));
                LOGGER.info(msn);
                cerrarPopupCom();
            }
        } else {
            String msn = "Proceso fallo: debe agregar al menos un registro para "
                    + "almacenar escriba el 'LOCATION'  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn, ""));
            LOGGER.info(msn);

        }
        return "";
    }
    public boolean validarUnicaComunidadXTipoTrabajo() {

        boolean respuesta = true;

        for (CmtComunidadRr comunidadRr : lstCmtComunidadRrs) {
            try {
                List<CmtExtendidaTipoTrabajoMgl> extendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                        findBytipoTrabajoObjAndCom(comunidadRr, TablasBasicasMgl);
                if (extendidaTipoTrabajoMgl != null && !extendidaTipoTrabajoMgl.isEmpty()) {
                    respuesta = false;
                }
            } catch (ApplicationException e) {
                respuesta = false;
            } catch (Exception e) {
               respuesta = false;
            }
        }
        return respuesta;
    }
    
    public void mostrarTablaComunidades() {

        mostrarPopupCom = true;
        cmtFiltroConsultaComunidadesRrDto.setCodigoComunidad("");
        cmtFiltroConsultaComunidadesRrDto.setNombreComunidad("");
        
        listInfoByPage(1); 

    }
    public void cerrarPopupCom() {

        mostrarPopupCom = false;
        for(CmtComunidadRr comunidadRr: lstCmtComunidadRrs){
            comunidadRr.setCodigoLocation("");
        }
        lstCmtComunidadRrs=new ArrayList<>();

    }

    public CmtBasicaMgl getTablasBasicasMgl() {
        return TablasBasicasMgl;
    }

    public void setTablasBasicasMgl(CmtBasicaMgl TablasBasicasMgl) {
        this.TablasBasicasMgl = TablasBasicasMgl;
    }

    public List<CmtBasicaMgl> getTablasBasicasMglList() {
        return tablasBasicasMglList;
    }

    public void setTablasBasicasMglList(List<CmtBasicaMgl> tablasBasicasMglList) {
        this.tablasBasicasMglList = tablasBasicasMglList;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getFiltroTablaBasicaSelected() {
        return filtroTablaBasicaSelected;
    }

    public void setFiltroTablaBasicaSelected(String filtroTablaBasicaSelected) {
        this.filtroTablaBasicaSelected = filtroTablaBasicaSelected;
    }

    public void pageFirst() {
        dataTable.setFirst(0);
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        HtmlDataTable t = dataTable;
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public String getPageActual() {
        try {
            int count = dataTable.getRowCount();
            int rows = dataTable.getRows();
            int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
            int actual = (dataTable.getFirst() / rows) + 1;

            paginaList = new ArrayList<>();

            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }

            pageActual = actual + " de " + totalPaginas;

            if (numPagina == null) {
                numPagina = "1";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void irPagina() {
        String value = numPagina;
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
    }

    public void eliminarComplemento(CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl) {

        boolean respuesta;
        isResultado = false;
        
        try {
            respuesta = cmtExtendidaTipoTrabajoMglFacadeLocal.delete(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);

            if (respuesta) {
                String msn = "Proceso Exitoso: se ha eliminado el complemento   "
                        + "<b>" + extendidaTipoTrabajoMgl.getIdExtTipTra() + "</b>  "
                        + "asociado al tipo de trabajo  <b>" + extendidaTipoTrabajoMgl.
                        getTipoTrabajoObj().getNombreBasica() + "</b>";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                LOGGER.info(msn);
                fillSqlList();
            } else {
                String msn = "Proceso falló: ha ocurrido un problema "
                        + " al momento de eliminar el registro ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                LOGGER.info(msn);
            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }
    }
    
    public void activarTecnicoAnticipado(CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl) {

        isResultado = false;
        
        if (extendidaTipoTrabajoMgl.getTecnicoAnticipado() != null && 
                extendidaTipoTrabajoMgl.getTecnicoAnticipado().equalsIgnoreCase("0")) {
            extendidaTipoTrabajoMgl.setTecnicoAnticipado("1");

        } else if(extendidaTipoTrabajoMgl.getTecnicoAnticipado() != null && 
                extendidaTipoTrabajoMgl.getTecnicoAnticipado().equalsIgnoreCase("1")) {
            extendidaTipoTrabajoMgl.setTecnicoAnticipado("0");
        }else{
            if(extendidaTipoTrabajoMgl.getTecnicoAnticipado() == null){
                extendidaTipoTrabajoMgl.setTecnicoAnticipado("0");
            }
        }

        try {
            cmtExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                    update(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);

            String msn = "Proceso exitoso:" + " " + "Se ha modificado el complemento  "
                    + " <b>" + extendidaTipoTrabajoMgl.getIdExtTipTra() + "</b>  para el tipo de trabajo "
                    + " <b>" + TablasBasicasMgl.getNombreBasica() + "</b>  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msn, ""));
            fillSqlList();
            LOGGER.info(msn);

        } catch (ApplicationException e) {
            String msn = "Proceso fallo: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }

    }
    
     public void activarAgendaInmediata(CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl) {

        if (extendidaTipoTrabajoMgl.getAgendaInmediata() != null && 
                extendidaTipoTrabajoMgl.getAgendaInmediata().equalsIgnoreCase("0")) {
            extendidaTipoTrabajoMgl.setAgendaInmediata("1");

        } else if(extendidaTipoTrabajoMgl.getAgendaInmediata() != null && 
                extendidaTipoTrabajoMgl.getAgendaInmediata().equalsIgnoreCase("1")) {
            extendidaTipoTrabajoMgl.setAgendaInmediata("0");
        }else{
            if(extendidaTipoTrabajoMgl.getAgendaInmediata() == null){
                extendidaTipoTrabajoMgl.setAgendaInmediata("0");
            }
        }

        try {
            cmtExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                    update(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);

            String msn = "Proceso exitoso:" + " " + "Se ha modificado el complemento  "
                    + " <b>" + extendidaTipoTrabajoMgl.getIdExtTipTra() + "</b>  para el tipo de trabajo "
                    + " <b>" + TablasBasicasMgl.getNombreBasica() + "</b>  ";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msn, ""));
            fillSqlList();
            LOGGER.info(msn);

        } catch (ApplicationException e) {
            String msn = "Proceso fallo: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }

    }

    
    public void guardaComunidadLocation(CmtComunidadRr comunidadRr){
        
      
       lstCmtComunidadRrs.add(comunidadRr);
        
        
    }
   
    
    public void quitarInfoAdiTipoTrabajo(CmtComunidadRr comunidadRr){
        try {
            if (lstCmtComunidadRrs.size() > 0) {
                comunidadRr.setCodigoLocation(null);
                lstCmtComunidadRrs.remove(comunidadRr);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error InfoAdiMantenimientoBean:" + e.getMessage(), e, LOGGER);
        }

    }
    
    public boolean disableAbreviatura() {

        boolean respuesta = false;
        BigDecimal cero = new BigDecimal(BigInteger.ZERO);
        
        if (TablasBasicasMgl != null && TablasBasicasMgl.getBasicaId() != null) {
            if (TablasBasicasMgl.getBasicaId().compareTo(cero) == 0
                    || TablasBasicasMgl.getTipoBasicaObj().getNombreTipo().
                    equalsIgnoreCase(Constant.TECNOLOGIA)) {
                respuesta = true;
            }
        }
        return respuesta;

    }
    
    public String filtrarInfo() {
        listInfoByPageExtTipoWork(1);
        return null;
    }
    
    public String procesarArchivo() {

        try {
            isResultado = false;
            
            if (fileMasivoUbicacion.getFileName() == null) {
                String msn = "Por favor cargue un archivo valido para ser procesado.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
            } else {
                String ext = fileMasivoUbicacion.getFileName().substring(fileMasivoUbicacion.getFileName().length() - 4,
                        fileMasivoUbicacion.getFileName().length());

                if (!ext.toUpperCase().equals(".CSV")) {
                    String msn = "Extension de archivo invalida";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));

                } else {
                    BufferedReader contenidoArchivo;
                    contenidoArchivo = new BufferedReader(new InputStreamReader(this.fileMasivoUbicacion.getInputStream(),
                            "ISO-8859-1"));
                    String cabeceraFile;
                    cabeceraFile = contenidoArchivo.readLine().toUpperCase();
                    lineasArchivoRetorno = new ArrayList<>();
                    lineasArchivoRetorno.add(cabeceraFile + ";" + "RESULTADO_PROCESO");
                    int h = 0;
                    Map cabecera = new HashMap();

                    if (cabeceraFile != null && !cabeceraFile.isEmpty()) {
                        for (String s : cabeceraFile.split(";")) {
                            cabecera.put(s, h++);
                        }
                    }
                    String lineActual;
                    int longitudCabecera = cabecera.size();

                    if (cabeceraFile != null && cabeceraFile.equalsIgnoreCase(NOM_COLUMNAS.toUpperCase())) {
                        LOGGER.error("Inicia el procesamiento del archivo CSV");
                        while (contenidoArchivo.ready()) {
                            msgError = "";
                            lineActual = contenidoArchivo.readLine().toUpperCase();
                            if (lineActual != null && !lineActual.isEmpty()) {
                                String[] strSplit = lineActual.split(";", longitudCabecera);

                                int indexTipoTrabajo = (Integer) cabecera.get("TIPOTRABAJO");
                                String tipoTrabajo = strSplit[indexTipoTrabajo];

                                int indexComunidad = (Integer) cabecera.get("COMUNIDAD");
                                String comunidad = strSplit[indexComunidad];

                                int indexCodigo = (Integer) cabecera.get("CODIGOLOCATION");
                                String codigoLo = strSplit[indexCodigo];

                                List<CmtBasicaMgl> tipoTra;
                                CmtBasicaMgl tipoTrabajoBas;
                                CmtComunidadRr comunidadRr;
                                List<CmtExtendidaTipoTrabajoMgl> lstExtendidaTipoTrabajoMgl;
                                CmtExtendidaTipoTrabajoMgl extendidaTipoTrabajoMgl;

                                //Buscamos el tipo de trabajo por abreviatura
                                if (cmtTipoBasicaMglSelected != null) {
                                    tipoTra = cmtBasicaMglFacadeLocal.findByTipoBAndAbreviatura(cmtTipoBasicaMglSelected.getTipoBasicaId(),
                                            tipoTrabajo);

                                    if (tipoTra != null && !tipoTra.isEmpty()) {
                                        
                                        tipoTrabajoBas =tipoTra.get(0);
                                        //Buscamos la comunidad
                                        comunidadRr = cmtComunidadRrFacadeLocal.findByCodigoRR(comunidad);
                                        if (comunidadRr != null) {
                                            
                                            if (codigoLo != null && !codigoLo.isEmpty()) {

                                                //Buscamos la extendida de tipo trabajo
                                                lstExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                                                        findBytipoTrabajoObjAndCom(comunidadRr, tipoTrabajoBas);
                                                if (lstExtendidaTipoTrabajoMgl != null
                                                        && !lstExtendidaTipoTrabajoMgl.isEmpty()) {
                                                    extendidaTipoTrabajoMgl = lstExtendidaTipoTrabajoMgl.get(0);
                                                    extendidaTipoTrabajoMgl.setLocationCodigo(codigoLo);
                                                    cmtExtendidaTipoTrabajoMglFacadeLocal.
                                                            update(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);
                                                    msgError = "Registro Modificado";
                                                } else {
                                                    //No esta la crea
                                                    extendidaTipoTrabajoMgl = new CmtExtendidaTipoTrabajoMgl();
                                                    extendidaTipoTrabajoMgl.setTipoTrabajoObj(tipoTrabajoBas);
                                                    extendidaTipoTrabajoMgl.setComunidadRrObj(comunidadRr);
                                                    extendidaTipoTrabajoMgl.setLocationCodigo(codigoLo);
                                                    extendidaTipoTrabajoMgl.setTecnicoAnticipado("1");
                                                    extendidaTipoTrabajoMgl.setAgendaInmediata("1");

                                                    extendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMglFacadeLocal.
                                                            create(extendidaTipoTrabajoMgl, usuarioVT, perfilVT);
                                                    if (extendidaTipoTrabajoMgl != null
                                                            && extendidaTipoTrabajoMgl.getIdExtTipTra() != null) {
                                                        msgError = "Registro Creado";
                                                    } else {
                                                        msgError = "Ocurrio un error al crear el registro";
                                                    }
                                                }
                                            } else {
                                                msgError = "Código Location se encuentra vacio.";
                                            }
                                        } else {
                                            msgError = "No existe comunidad RR con el codigo: " + comunidad + "";
                                        }
                                    } else {
                                        msgError = "No existe el tipo de trabajo en la tabla basica con nombre: " + tipoTrabajo + "";
                                    }

                                }
                               lineasArchivoRetorno.add(lineActual.toUpperCase()+";"+ msgError);//linea con punto y coma + el Error de la linea
                            }
                        }
                        contenidoArchivo.close();
                        session.setAttribute("resultUbicacion", lineasArchivoRetorno);
                        isResultado = true;
                        String msn = "Proceso Exitoso: Archivo procesado.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO, msn, null));
                    } else {
                        String msn = "El archivo no tiene un encabezado valido"
                                + " para su correcto procesamiento por favor cambielo por este:"
                                + "" + NOM_COLUMNAS + " ";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    }
                }
            }
        } catch (IOException | ApplicationException ex) {
            LOGGER.error("Error en  CmtInfoAdiMantenimientoBean:procesarArchivo:" + ex.getMessage());
        }
        return "";
    }
   
    public boolean desactivarOfsc() throws ApplicationException{
        ParametrosMgl result = parametrosFacade.findByAcronimoName(Constant.DESACTIVAR_OPCION_OFSC);
        boolean activacion = Boolean.parseBoolean(result.getParValor());
        return activacion;
    }
    
    public boolean desactivarModuloGestion() throws ApplicationException{
        ParametrosMgl result = parametrosFacade.findByAcronimoName(Constant.DESACTIVAR_OPCION_MG);
        boolean activacion = Boolean.parseBoolean(result.getParValor());
        return activacion;
    }
    
    public boolean desactivarSgo() throws ApplicationException{
        ParametrosMgl result = parametrosFacade.findByAcronimoName(Constant.DESACTIVAR_OPCION_SGO);
        boolean activacion = Boolean.parseBoolean(result.getParValor());
        return activacion;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<String> getBrList() {
        return brList;
    }

    public void setBrList(List<String> brList) {
        this.brList = brList;
    }

    public BigDecimal getIdTablasBasicas() {
        return idTablasBasicas;
    }

    public void setIdTablasBasicas(BigDecimal idTablasBasicas) {
        this.idTablasBasicas = idTablasBasicas;
    }

    public String getTablaTipoBasicaMgl() {
        return tablaTipoBasicaMgl;
    }

    public void setTablaTipoBasicaMgl(String tablaBasicaMgl) {
        this.tablaTipoBasicaMgl = tablaBasicaMgl;
    }

    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return TablasTipoBasicasMglList;
    }

    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> TablasTipoBasicasMglList) {
        this.TablasTipoBasicasMglList = TablasTipoBasicasMglList;
    }

    public boolean isOtroEstado() {
        return otroEstado;
    }

    public void setOtroEstado(boolean otroEstado) {
        this.otroEstado = otroEstado;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public void setPageActual(String pageActual) {

        this.pageActual = pageActual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public String getDescripcionBasica() {
        return descripcionBasica;
    }

    public void setDescripcionBasica(String descripcionBasica) {
        this.descripcionBasica = descripcionBasica;
    }

    public String getTipoBasicaDescripcion() {
        return tipoBasicaDescripcion;
    }

    public void setTipoBasicaDescripcion(String tipoBasicaDescripcion) {
        this.tipoBasicaDescripcion = tipoBasicaDescripcion;
    }

    public CmtTipoBasicaMgl getCmtTipoBasicaMglSelected() {
        return cmtTipoBasicaMglSelected;
    }

    public void setCmtTipoBasicaMglSelected(CmtTipoBasicaMgl cmtTipoBasicaMglSelected) {
        this.cmtTipoBasicaMglSelected = cmtTipoBasicaMglSelected;
    }

    public FiltroConjsultaBasicasDto getFiltroConjsultaBasicasDto() {
        return filtroConjsultaBasicasDto;
    }

    public void setFiltroConjsultaBasicasDto(FiltroConjsultaBasicasDto filtroConjsultaBasicasDto) {
        this.filtroConjsultaBasicasDto = filtroConjsultaBasicasDto;
    }

    public List<CmtBasicaMgl> getModulosTblBasica() {
        return modulosTblBasica;
    }

    public void setModulosTblBasica(List<CmtBasicaMgl> modulosTblBasica) {
        this.modulosTblBasica = modulosTblBasica;
    }

    public Boolean getCrearBasica() {
        return crearBasica;
    }

    public Boolean getEditarBasica() {
        return editarBasica;
    }

    public Boolean getBorrarBasica() {
        return borrarBasica;
    }

    public boolean isTblTecnologia() {
        return tblTecnologia;
    }

    public void setTblTecnologia(boolean tblTecnologia) {
        this.tblTecnologia = tblTecnologia;
    }

    public List<ParametrosCalles> getListTipoNivel5() {
        return listTipoNivel5;
    }

    public void setListTipoNivel5(List<ParametrosCalles> listTipoNivel5) {
        this.listTipoNivel5 = listTipoNivel5;
    }

    public boolean isActiveBottonInfoAdi() {
        return activeBottonInfoAdi;
    }

    public void setActiveBottonInfoAdi(boolean activeBottonInfoAdi) {
        this.activeBottonInfoAdi = activeBottonInfoAdi;
    }

    public int getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(int tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public boolean isHabilitaPanelInfoAdi() {
        return habilitaPanelInfoAdi;
    }

    public void setHabilitaPanelInfoAdi(boolean habilitaPanelInfoAdi) {
        this.habilitaPanelInfoAdi = habilitaPanelInfoAdi;
    }

    public CmtExtendidaTecnologiaMgl getMglExtendidaTecnologia() {
        return mglExtendidaTecnologia;
    }

    public void setMglExtendidaTecnologia(CmtExtendidaTecnologiaMgl mglExtendidaTecnologia) {
        this.mglExtendidaTecnologia = mglExtendidaTecnologia;
    }

    public String[] getTipoViviendaSelected() {
        return tipoViviendaSelected;
    }

    public void setTipoViviendaSelected(String[] tipoViviendaSelected) {
        this.tipoViviendaSelected = tipoViviendaSelected;
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

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public List<CmtBasicaExtMgl> getBasicaExtMgls() {
        return basicaExtMgls;
    }

    public void setBasicaExtMgls(List<CmtBasicaExtMgl> basicaExtMgls) {
        this.basicaExtMgls = basicaExtMgls;
    }

    public boolean isTblTiposTrabajo() {
        return tblTiposTrabajo;
    }

    public void setTblTiposTrabajo(boolean tblTiposTrabajo) {
        this.tblTiposTrabajo = tblTiposTrabajo;
    }

    public List<CmtComunidadRr> getLstComunidadesMgls() {
        return lstComunidadesMgls;
    }

    public void setLstComunidadesMgls(List<CmtComunidadRr> lstComunidadesMgls) {
        this.lstComunidadesMgls = lstComunidadesMgls;
    }

    public CmtFiltroConsultaComunidadesRrDto getCmtFiltroConsultaComunidadesRrDto() {
        return cmtFiltroConsultaComunidadesRrDto;
    }

    public void setCmtFiltroConsultaComunidadesRrDto(CmtFiltroConsultaComunidadesRrDto cmtFiltroConsultaComunidadesRrDto) {
        this.cmtFiltroConsultaComunidadesRrDto = cmtFiltroConsultaComunidadesRrDto;
    }
    
    public List<CmtExtendidaTipoTrabajoMgl> getLstInfoAdiTipoTrabajoMgls() {
        return lstInfoAdiTipoTrabajoMgls;
    }

    public void setLstInfoAdiTipoTrabajoMgls(List<CmtExtendidaTipoTrabajoMgl> lstInfoAdiTipoTrabajoMgls) {
        this.lstInfoAdiTipoTrabajoMgls = lstInfoAdiTipoTrabajoMgls;
    }

    public CmtExtendidaTipoTrabajoMgl getCmtExtendidaTipoTrabajoMgl() {
        return cmtExtendidaTipoTrabajoMgl;
    }

    public void setCmtExtendidaTipoTrabajoMgl(CmtExtendidaTipoTrabajoMgl cmtExtendidaTipoTrabajoMgl) {
        this.cmtExtendidaTipoTrabajoMgl = cmtExtendidaTipoTrabajoMgl;
    }

    public List<CmtBasicaMgl> getBasicasMglNombresTecOFSClist() {
        return basicasMglNombresTecOFSClist;
    }

    public void setBasicasMglNombresTecOFSClist(List<CmtBasicaMgl> basicasMglNombresTecOFSClist) {
        this.basicasMglNombresTecOFSClist = basicasMglNombresTecOFSClist;
    }

    public BigDecimal getNombreTecnoOFSCSelected() {
        return nombreTecnoOFSCSelected;
    }

    public void setNombreTecnoOFSCSelected(BigDecimal nombreTecnoOFSCSelected) {
        this.nombreTecnoOFSCSelected = nombreTecnoOFSCSelected;
    }

    public boolean isMostrarPopupCom() {
        return mostrarPopupCom;
    }

    public void setMostrarPopupCom(boolean mostrarPopupCom) {
        this.mostrarPopupCom = mostrarPopupCom;
    }

    public String getNumPaginaExtTipoWork() {
        return numPaginaExtTipoWork;
    }

    public void setNumPaginaExtTipoWork(String numPaginaExtTipoWork) {
        this.numPaginaExtTipoWork = numPaginaExtTipoWork;
    }

    public List<Integer> getPaginaListExtTipoWork() {
        return paginaListExtTipoWork;
    }

    public void setPaginaListExtTipoWork(List<Integer> paginaListExtTipoWork) {
        this.paginaListExtTipoWork = paginaListExtTipoWork;
    }

    public boolean isLblAbreviatura() {
        return lblAbreviatura;
    }

    public void setLblAbreviatura(boolean lblAbreviatura) {
        this.lblAbreviatura = lblAbreviatura;
    }

    public boolean isLblCodigoOfsc() {
        return lblCodigoOfsc;
    }

    public void setLblCodigoOfsc(boolean lblCodigoOfsc) {
        this.lblCodigoOfsc = lblCodigoOfsc;
    }

    public FiltroConsultaExtendidaTipoTrabajoDtos getFiltroConsultaExtendidaTipoTrabajoDtos() {
        return filtroConsultaExtendidaTipoTrabajoDtos;
    }

    public void setFiltroConsultaExtendidaTipoTrabajoDtos(FiltroConsultaExtendidaTipoTrabajoDtos filtroConsultaExtendidaTipoTrabajoDtos) {
        this.filtroConsultaExtendidaTipoTrabajoDtos = filtroConsultaExtendidaTipoTrabajoDtos;
    }

    public UploadedFile getFileMasivoUbicacion() {
        return fileMasivoUbicacion;
    }

    public void setFileMasivoUbicacion(UploadedFile fileMasivoUbicacion) {
        this.fileMasivoUbicacion = fileMasivoUbicacion;
    }

    public boolean isIsResultado() {
        return isResultado;
    }

    public void setIsResultado(boolean isResultado) {
        this.isResultado = isResultado;
    }

    
    public CmtBasicaExtMgl getCuadrantesNodo() {
        return cuadrantesNodo;
    }

    public void setCuadrantesNodo(CmtBasicaExtMgl cuadrantesNodo) {
        this.cuadrantesNodo = cuadrantesNodo;
    }

    /**
     * @return the ofsc
     */
    public String getOfsc() {
        return ofsc;
    }

    /**
     * @param ofsc the osfc to set
     */
    public void setOfsc(String ofsc) {
        this.ofsc = ofsc;
    }

    /**
     * @return the moduloGestion
     */
    public String getModuloGestion() {
        return moduloGestion;
    }

    /**
     * @param moduloGestion the moduloGestion to set
     */
    public void setModuloGestion(String moduloGestion) {
        this.moduloGestion = moduloGestion;
    }

    /**
     * @return the Sgo
     */
    public String getSgo() {
        return sgo;
    }
       
    public void setSgo(String sgo) {
        this.sgo = sgo;
    }
}
