package co.com.claro.mgl.mbeans.cm.ot;

import co.com.claro.mgl.businessmanager.cm.CmtFlujosInicialesCmManager;
import co.com.claro.mgl.dtos.FiltroConsultaEstadosxFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtFlujosInicialesCm;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import co.com.claro.mgl.facade.cm.CmtFlujosInicialesCmFacadeLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "estadosxFlujosBean")
@ViewScoped
public class EstadosxFlujosBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(EstadosxFlujosBean.class);
    private static final long serialVersionUID = 1L;
    
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal cmtEstadoxFlujoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FiltroConsultaEstadosxFlujoDto filtroConsultaEstadosxFlujoDto = new FiltroConsultaEstadosxFlujoDto();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "ESTADOXFLUJOOT";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<CmtBasicaMgl> listTablaBasicaFlujoOT;
    private List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt;
    private List<CmtBasicaMgl> listTablaBasicaEstadoCM;
    private List<CmtBasicaMgl> listTablaBasicaForm;
    private List<CmtBasicaMgl> listBasicaTecnologia;//valbuenayf
    private List<CmtBasicaMgl> listBasicaSubtipoOrdenOFSC;
    private String tipoOtlist;
    private String estadoCMlist;
    private String estadoInternolist;
    private String formVtList;
    private String tipoOtSelected;
    private String estadoCMSelected;
    private String estadoInternoSelected;
    private String estadoInicialSelected;
    private String formVtSelected;
    private String rolSelected;
    private BigDecimal tecnologiaSelected;//valbuenayf
    private String tecnologiaFilter;//valbuenayf
    private String message;
    private CmtTipoOtMgl cmtTipoOtMgl;
    
    private HashMap<String, Object> params;
    private HashMap<String, Object> paramsValidacionGuardar;
    private HashMap<String, Object> paramsValidacionActualizar;
    private List<CmtEstadoxFlujoMgl> listCmtEstadoxFlujoMgl;
    private boolean habilitaObj = false;
    private int actual;
    private CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl;
    private String estadoxFlujoId;
    private String estadoInternoObj;
    private String tipoFlujoOtObj;
    private String cambiaCmEstadoObj;
    private String formulario;
    private String esEstadoInicial;
    private String gestionRol;
    private String numPagina;
    private boolean pintarPaginado;
    private List<Integer> paginaList;
    private String pageActual;
    private boolean btnActivo = true;
    private boolean cargar = true;
    private SecurityLogin securityLogin;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private BigDecimal subTipoOrdenOfscSelected;
    private String subTipoOrdenOFSCFilter;
    private String dias;
    private boolean mostrarPanelCrearEstadoXFlujo;
    private boolean mostrarPanelListEstXFlujo;
    
    private List<CmtBasicaMgl> listadoTipodeTrabajo;
    private List<CmtBasicaMgl> listadoEstadoCierre;
    private List<CmtBasicaMgl> listadoEstadoInicial;
    
    private BigDecimal tipoTrabajoRRSelect;
    private BigDecimal estadoResultadoInicialRRSelect;
    private BigDecimal estadoResultadoCierreRRSelect;
    private String tiempoAgendaOfsc;
    boolean estadoCCMMVisible;
    private List<String> listEstadoCCMMParaFlujoSelected;
    private List<String> listEstadoCCMMParaFlujoIniciales;
    @EJB
    private CmtFlujosInicialesCmFacadeLocal cmtEstadosInicialesCCMMFacadeLocal;
    
     //Opciones agregadas para Rol
    private final String NWBTNFLUJ = "NWBTNFLUJ";
    private final String CODBTNFLUJ = "CODBTNFLUJ";
    private final String DELBTNFLUJ = "DELBTNFLUJ";
    

    public EstadosxFlujosBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            if (usuarioVT == null) {
                session.getAttribute("usuarioM");
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioM", usuarioVT);
            }
            cmtEstadoxFlujoMgl = new CmtEstadoxFlujoMgl();
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se genera error al momento de iniciar: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se produce un error al momento de iniciar: " + e.getMessage(), e, LOGGER);
        }
    }
    
    @PostConstruct
    public void cargarListas() {
        try {
            cmtEstadoxFlujoMglFacadeLocal.setUser(usuarioVT, perfilVT);

            //Consulta los tipos de flujo
            CmtTipoBasicaMgl tipoBasicaTipoFlujoOt;
            tipoBasicaTipoFlujoOt = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_FLUJO_OT);
            listTablaBasicaFlujoOT = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoFlujoOt);

            //Consulta los estados internos
            CmtTipoBasicaMgl tipoBasicaTipoEstadoInternoOt;
            tipoBasicaTipoEstadoInternoOt = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
            listTablaBasicaEstadoInternoOt = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoEstadoInternoOt);

            //Consulta los Estado De Cuenta Matriz
            CmtTipoBasicaMgl tipoBasicaEstadosCm;
            tipoBasicaEstadosCm = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            listTablaBasicaEstadoCM = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstadosCm);

            //Consulta Los Formularios 
            CmtTipoBasicaMgl tipoBasicaFormularios;
            tipoBasicaFormularios = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_FORMULARIOS_VT);
            listTablaBasicaForm = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaFormularios);

            //Consulta Las Tecnologias
            if (listBasicaTecnologia == null
                    || listBasicaTecnologia.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);
                listBasicaTecnologia = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            //Consulta Los Sub_Tipo Orden OFSC
            if (listBasicaSubtipoOrdenOFSC == null
                    || listBasicaSubtipoOrdenOFSC.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaSubTipoOrdenOFSC;
                tipoBasicaSubTipoOrdenOFSC = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
                listBasicaSubtipoOrdenOFSC = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaSubTipoOrdenOFSC);
            }
            pintarPaginado = true;
            buscar();
            listInfoByPage(1);
            mostrarPanelCrearEstadoXFlujo=false;
            mostrarPanelListEstXFlujo=true; 
            
            CmtTipoBasicaMgl tipoBasicaEstadoResultado =cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_RESULTADO); 
            List<String> tiposEstadosInicialRR = Arrays.asList("PROGRAMADO", "EN CURSO");
            listadoEstadoInicial = cmtBasicaMglFacadeLocal.findEstadoResultadoOTRR(tiposEstadosInicialRR , tipoBasicaEstadoResultado);
            List<String> tiposEstadosCierreRR = Arrays.asList("REALIZADO");
            listadoEstadoCierre = cmtBasicaMglFacadeLocal.findEstadoResultadoOTRR(tiposEstadosCierreRR, tipoBasicaEstadoResultado);
            CmtTipoBasicaMgl tipoBasicaTipoTrabajo = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_TRABAJO);
            listadoTipodeTrabajo = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTrabajo);
           
        } catch (ApplicationException e) {
            LOGGER.error("Error al cargar listas. EX000 " + e.getMessage(), e);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : cargarListas()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (ConstantsCM.PAGINACION_OCHO_FILAS * (page - 1));
            }
            filtroConsultaEstadosxFlujoDto = cmtEstadoxFlujoMglFacadeLocal.findTablasEstadoxFlujo(
                    params, ConstantsCM.PAGINACION_DATOS, firstResult, ConstantsCM.PAGINACION_OCHO_FILAS);
            listCmtEstadoxFlujoMgl = filtroConsultaEstadosxFlujoDto.getListaCmtEstadoxFlujoMgl();
            habilitaObj = !listCmtEstadoxFlujoMgl.isEmpty();
            if (listCmtEstadoxFlujoMgl.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            if (listCmtEstadoxFlujoMgl.size() == 1) {
                cmtEstadoxFlujoMgl = listCmtEstadoxFlujoMgl.get(0);
            }
            actual = page;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de realizar la consulta: " + ex.getMessage(), ex, LOGGER);
            return "";
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de listar la información de la página: " + e.getMessage(), e, LOGGER);
        }
        return "";
    }
 
    public boolean validarEstadosGuardar() {
        List<BigDecimal> listaEstadosInt;
        listaEstadosInt = new ArrayList<>();

        return listaEstadosInt.contains(new BigDecimal(estadoInternolist));
    }

    public boolean validarEstadosactualizar() {
        List<BigDecimal> listaEstadosInt;
        listaEstadosInt = new ArrayList<>();

        return listaEstadosInt.contains(new BigDecimal(estadoInternoSelected));
    }
    
    public void guardarEstadoxFlujo() {
        if (!validarCamposGuardar()) {
            String msn = "Faltan campos obligatorios por llenar";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        }
        if (validarConfigDuplicadaGuardar()) {
            String msn = "La configuracion que esta ingresando ya existe ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        } else {
            crearEstadoxFlujo();
        }
    }
    
    public void actualizaEstadoxFlujo() {
        String msn;
        if (!validarCamposAct()) {
            msn = "Existen campos obligatorios  sin completar ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        } else if (tiempoAgendaOfsc != null && !tiempoAgendaOfsc.isEmpty()) {
            try {
                if (Integer.parseInt(tiempoAgendaOfsc) < 0) {
                    msn = "El campo 'Se puede agendar', no permite valores negativos";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                } else if ((tiempoAgendaOfsc.contains("+"))) {
                    msn = "El campo 'Se puede agendar', solo permite valores numéricos.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                } else if (Integer.parseInt(tiempoAgendaOfsc) > 60) {
                    msn = "El campo 'Se puede agendar', permite máximo 60 minutos";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
            } catch (NumberFormatException e) {
                LOGGER.error("El valor ingresado en tiempo agenda no es numérico: " + tiempoAgendaOfsc);
                msn = "El campo 'Se puede agendar', solo permite valores numéricos.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
        }
        if (validarConfigDuplicadaAct()) {
            if (estadoInicialSelected.equalsIgnoreCase("Y")
                    && listEstadoCCMMParaFlujoSelected != null) {
                if (equalLists(listEstadoCCMMParaFlujoSelected,
                        listEstadoCCMMParaFlujoIniciales)) {
                    msn = "La configuracion que esta ingresando  ya existe ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

                } else {
                    actualizarEstadosxFlujoMgl();
                }
            } else {
                msn = "La configuracion que esta ingresando  ya existe ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } else {
            actualizarEstadosxFlujoMgl();
        }
    }
    
        public boolean validarConfigDuplicadaAct() {
        boolean duplicado = false;
        try {
            List<CmtEstadoxFlujoMgl> cmtEstadoxFlujoMglList;
            camposValidarActualizar();
            cmtEstadoxFlujoMglList = cmtEstadoxFlujoMglFacadeLocal.findByAllFields(paramsValidacionActualizar);
            if (!cmtEstadoxFlujoMglList.isEmpty()) {
                duplicado = true;
            }
            
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada: " + ex.getMessage(), ex, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada: " + e.getMessage(), e, LOGGER);
        }
        return duplicado;
    }
    
    public boolean validarConfigDuplicadaGuardar() {
        boolean duplicado = false;
        try {
            List<CmtEstadoxFlujoMgl> cmtEstadoxFlujoMglList;
             camposValidarGuardar();
            cmtEstadoxFlujoMglList = cmtEstadoxFlujoMglFacadeLocal.findByAllFields(paramsValidacionGuardar);
            if (!cmtEstadoxFlujoMglList.isEmpty()) {
                duplicado = true;
            }
            
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + ex.getMessage(), ex, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + e.getMessage(), e, LOGGER);
        }
        return duplicado;
    }
    
    public void camposValidarActualizar() {
        try {
            paramsValidacionActualizar = new HashMap<>();
            paramsValidacionActualizar.put("all", "1");
            paramsValidacionActualizar.put("estadoInternoObj", this.estadoInternoSelected);
            paramsValidacionActualizar.put("tipoFlujoOtObj", this.tipoOtSelected);
            paramsValidacionActualizar.put("cambiaCmEstadoObj", this.estadoCMSelected);
            paramsValidacionActualizar.put("basicaTecnologia", this.tecnologiaSelected);
            paramsValidacionActualizar.put("formulario", this.formVtSelected);
            paramsValidacionActualizar.put("esEstadoInicial", this.estadoInicialSelected);
            paramsValidacionActualizar.put("gestionRol", cmtEstadoxFlujoMgl.getGestionRol());
            paramsValidacionActualizar.put("subTipoOrdenOFSC", this.subTipoOrdenOfscSelected);
            paramsValidacionActualizar.put("diasAMostrarAgenda", this.dias);
            paramsValidacionActualizar.put("tipoOTRR", this.tipoTrabajoRRSelect);
            paramsValidacionActualizar.put("estadoInicialOTRR" , this.estadoResultadoInicialRRSelect);
            paramsValidacionActualizar.put("estadoCierreOTRR" ,this.estadoResultadoCierreRRSelect );
            paramsValidacionActualizar.put("tiempoAgendaOfsc", this.tiempoAgendaOfsc);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }
    
       public void camposValidarGuardar() {
        try {
            paramsValidacionGuardar = new HashMap<>();
            paramsValidacionGuardar.put("all", "1");
            paramsValidacionGuardar.put("estadoInternoObj", this.estadoInternolist);
            paramsValidacionGuardar.put("tipoFlujoOtObj", this.tipoOtlist);
            paramsValidacionGuardar.put("cambiaCmEstadoObj", this.estadoCMlist);
            paramsValidacionGuardar.put("basicaTecnologia", this.tecnologiaSelected);
            paramsValidacionGuardar.put("formulario", this.formVtList);
            paramsValidacionGuardar.put("esEstadoInicial", this.estadoInicialSelected);
            paramsValidacionGuardar.put("gestionRol", cmtEstadoxFlujoMgl.getGestionRol());
            paramsValidacionGuardar.put("subTipoOrdenOFSC", this.subTipoOrdenOfscSelected);
            paramsValidacionGuardar.put("diasAMostrarAgenda", this.dias);
            paramsValidacionGuardar.put("tipoOTRR", this.tipoTrabajoRRSelect);
            paramsValidacionGuardar.put("estadoInicialOTRR" , this.estadoResultadoInicialRRSelect);
            paramsValidacionGuardar.put("estadoCierreOTRR" ,this.estadoResultadoCierreRRSelect );
            paramsValidacionGuardar.put("tiempoAgendaOfsc", this.tiempoAgendaOfsc);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }
    
    public void crearEstadoxFlujo() {
        try {
            if (!validarCamposGuardar()) {
                return;
            }
            
            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() != null) {
                String msn = "No puede crear un registro con este codigo ya existe uno en Base de datos.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                limpiarCampos();
                return;
            }
            
            CmtBasicaMgl basicatipoOt = new CmtBasicaMgl();
            basicatipoOt.setBasicaId(new BigDecimal(tipoOtlist));
            cmtEstadoxFlujoMgl.setTipoFlujoOtObj(basicatipoOt);
            
            CmtBasicaMgl basicaEstadoInterno = new CmtBasicaMgl();
            basicaEstadoInterno.setBasicaId(new BigDecimal(estadoInternolist));
            cmtEstadoxFlujoMgl.setEstadoInternoObj(basicaEstadoInterno);
            
            CmtBasicaMgl basicaEstadoCm = new CmtBasicaMgl();
            if (estadoCMlist!=null) {
                basicaEstadoCm.setBasicaId(new BigDecimal(estadoCMlist));
            } else {
                basicaEstadoCm=null;
            }
            cmtEstadoxFlujoMgl.setCambiaCmEstadoObj(basicaEstadoCm);
            
            CmtBasicaMgl basicaFormVt = new CmtBasicaMgl();
            basicaFormVt.setBasicaId(new BigDecimal(formVtList));
            //valbuenayf ajuste campo tecnologia
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            
            if(subTipoOrdenOfscSelected != null){
                CmtBasicaMgl tipoTrabajoRR = new CmtBasicaMgl();
                tipoTrabajoRR.setBasicaId(tipoTrabajoRRSelect);
                cmtEstadoxFlujoMgl.setTipoTrabajoRR(tipoTrabajoRR);
                
                CmtBasicaMgl estadoResultadoIniRR = new CmtBasicaMgl();
                estadoResultadoIniRR.setBasicaId(estadoResultadoInicialRRSelect);
                cmtEstadoxFlujoMgl.setEstadoOtRRInicial(estadoResultadoIniRR);
                
                CmtBasicaMgl estadoResultadoCieRR = new CmtBasicaMgl();
                estadoResultadoCieRR.setBasicaId(estadoResultadoCierreRRSelect);
                cmtEstadoxFlujoMgl.setEstadoOtRRFinal(estadoResultadoCieRR);
                
                
            }
            
            cmtEstadoxFlujoMgl.setFormulario(basicaFormVt);
            cmtEstadoxFlujoMgl.setGestionRol(cmtEstadoxFlujoMgl.getGestionRol());
            cmtEstadoxFlujoMgl.setEsEstadoInicial(estadoInicialSelected);
            cmtEstadoxFlujoMgl.setUsuarioCreacion(usuarioVT);
            cmtEstadoxFlujoMgl.setFechaCreacion(new Date());
            cmtEstadoxFlujoMgl.setFechaEdicion(null);
            cmtEstadoxFlujoMgl.setUsuarioEdicion("");
            cmtEstadoxFlujoMgl.setPerfilCreacion(perfilVT);
            cmtEstadoxFlujoMgl.setPerfilEdicion(0);
            cmtEstadoxFlujoMgl.setBasicaTecnologia(tecnologia); //valbuenayf ajuste campo tecnologia
            if (dias != null && !dias.isEmpty()) {
                cmtEstadoxFlujoMgl.setDiasAMostrarAgenda(Integer.valueOf(dias));
            }

            CmtBasicaMgl subTipoOrdenOFSC = new CmtBasicaMgl();
            if (subTipoOrdenOfscSelected != null) {
                subTipoOrdenOFSC.setBasicaId(subTipoOrdenOfscSelected);
                cmtEstadoxFlujoMgl.setSubTipoOrdenOFSC(subTipoOrdenOFSC);
            } else {
                cmtEstadoxFlujoMgl.setSubTipoOrdenOFSC(null);
            }

            if (estadoInicialSelected.equalsIgnoreCase("Y") && 
                    (listEstadoCCMMParaFlujoSelected == null || 
                    listEstadoCCMMParaFlujoSelected.isEmpty())) {
                String msn = "Ud no ha seleccionado el Estado Inicial de CCMM para el flujo.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            } 
            
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMglActualizada;
            
            cmtEstadoxFlujoMglActualizada = cmtEstadoxFlujoMglFacadeLocal.create(cmtEstadoxFlujoMgl, usuarioVT, perfilVT);
            if (cmtEstadoxFlujoMglActualizada != null) {
                guardarEstadosInicialesCCMM(listEstadoCCMMParaFlujoSelected, cmtEstadoxFlujoMglActualizada);
                String msn = "Proceso exitoso, se ha creado el estado x flujo con el Id: <b>" + cmtEstadoxFlujoMglActualizada.getEstadoxFlujoId() + "</b>";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                mostrarPanelCrearEstadoXFlujo=false;
                mostrarPanelListEstXFlujo=true;
                limpiarCampos();
                listInfoByPage(1);
            }
        } catch (ApplicationException | NumberFormatException ex) {
            cmtEstadoxFlujoMgl.setEstadoxFlujoId(BigDecimal.ZERO);
            String msn = "Proceso fallo: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
         } 
    }
    public void actualizarEstadosxFlujoMgl() {
        try {
            if (tipoOtSelected == null || estadoInternoSelected == null || 
                     formVtSelected == null
                    || estadoInicialSelected == null || cmtEstadoxFlujoMgl.getGestionRol() == null 
                    || tecnologiaSelected == null) {
                String msn = "No hay un registro seleccionado";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMglActualizada;
            CmtBasicaMgl basicatipoOt = new CmtBasicaMgl();
            basicatipoOt.setBasicaId(new BigDecimal(tipoOtSelected));
            cmtEstadoxFlujoMgl.setTipoFlujoOtObj(basicatipoOt);
            
            CmtBasicaMgl basicaEstadoInterno = new CmtBasicaMgl();
            basicaEstadoInterno.setBasicaId(new BigDecimal(estadoInternoSelected));
            cmtEstadoxFlujoMgl.setEstadoInternoObj(basicaEstadoInterno);
            
            CmtBasicaMgl basicaEstadoCm = new CmtBasicaMgl();
            if(estadoCMSelected!=null){
                basicaEstadoCm.setBasicaId(new BigDecimal(estadoCMSelected));
            }else{
                basicaEstadoCm=null;
            }
            cmtEstadoxFlujoMgl.setCambiaCmEstadoObj(basicaEstadoCm);
            
            CmtBasicaMgl basicaFormVt = new CmtBasicaMgl();
            basicaFormVt.setBasicaId(new BigDecimal(formVtSelected));
            cmtEstadoxFlujoMgl.setFormulario(basicaFormVt);

            //valbuenayf ajuste campo tecnologia
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            
            cmtEstadoxFlujoMgl.setEsEstadoInicial(estadoInicialSelected);
            cmtEstadoxFlujoMgl.setGestionRol(cmtEstadoxFlujoMgl.getGestionRol());
            cmtEstadoxFlujoMgl.setUsuarioCreacion(cmtEstadoxFlujoMgl.getUsuarioCreacion());
            cmtEstadoxFlujoMgl.setFechaCreacion(cmtEstadoxFlujoMgl.getFechaCreacion());
            cmtEstadoxFlujoMgl.setFechaEdicion(new Date());
            cmtEstadoxFlujoMgl.setUsuarioEdicion(usuarioVT);
            cmtEstadoxFlujoMgl.setPerfilEdicion(perfilVT);
            cmtEstadoxFlujoMgl.setBasicaTecnologia(tecnologia); //valbuenayf ajuste campo tecnologia
            
            if(subTipoOrdenOfscSelected != null){
                CmtBasicaMgl tipoTrabajoRR = new CmtBasicaMgl();
                tipoTrabajoRR.setBasicaId(tipoTrabajoRRSelect);
                cmtEstadoxFlujoMgl.setTipoTrabajoRR(tipoTrabajoRR);
                
                CmtBasicaMgl estadoResultadoIniRR = new CmtBasicaMgl();
                estadoResultadoIniRR.setBasicaId(estadoResultadoInicialRRSelect);
                cmtEstadoxFlujoMgl.setEstadoOtRRInicial(estadoResultadoIniRR);
                
                CmtBasicaMgl estadoResultadoCieRR = new CmtBasicaMgl();
                estadoResultadoCieRR.setBasicaId(estadoResultadoCierreRRSelect);
                cmtEstadoxFlujoMgl.setEstadoOtRRFinal(estadoResultadoCieRR);
                
                String tiempoAgendaOfscStr = this.getTiempoAgendaOfsc();
                if (tiempoAgendaOfscStr != null && !tiempoAgendaOfscStr.isEmpty()) {
                    cmtEstadoxFlujoMgl.setTiempoAgendaOfsc(Integer.valueOf(tiempoAgendaOfscStr));
                }
            }
            
            if (dias != null && !dias.isEmpty()) {
                try {
                    cmtEstadoxFlujoMgl.setDiasAMostrarAgenda(Integer.valueOf(dias));
                } catch (NumberFormatException e) {
                    String error = "Formato incorrecto para el número de días a mostrar agenda.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, error, ""));
                }
            }

            CmtBasicaMgl subTipoOrdenOFSC = new CmtBasicaMgl();
            if (subTipoOrdenOfscSelected != null) {
                subTipoOrdenOFSC.setBasicaId(subTipoOrdenOfscSelected);
                cmtEstadoxFlujoMgl.setSubTipoOrdenOFSC(subTipoOrdenOFSC);
            } else {
                cmtEstadoxFlujoMgl.setSubTipoOrdenOFSC(null);
            }
            

            if (estadoInicialSelected.equalsIgnoreCase("Y") && 
                    (listEstadoCCMMParaFlujoSelected == null || 
                    listEstadoCCMMParaFlujoSelected.isEmpty())) {
                String msn = "Ud no ha seleccionado el Estado Inicial de CCMM para el flujo.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            
            cmtEstadoxFlujoMglActualizada = cmtEstadoxFlujoMglFacadeLocal.update(cmtEstadoxFlujoMgl, usuarioVT, perfilVT);
            if (cmtEstadoxFlujoMglActualizada != null) {
                actualizarEstadosInicialesCCMM(listEstadoCCMMParaFlujoSelected, cmtEstadoxFlujoMgl);
                nuevoRegistro();//valbuenayf ajuste boton nuevo
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                mostrarPanelCrearEstadoXFlujo=false;
                mostrarPanelListEstXFlujo=true;
                listInfoByPage(1);
            }
        } catch (ApplicationException ex) {
            String msn = "Proceso fallo: " + ex.getMessage();
            FacesUtil.mostrarMensajeError(msn, ex, LOGGER);
         } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : actualizarEstadosxFlujoMgl()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public boolean validarCamposGuardar() {

        boolean validos = true;
        if (tipoOtlist == null || estadoInternolist == null || listTablaBasicaForm == null
                || estadoInicialSelected == null || cmtEstadoxFlujoMgl.getGestionRol() == null
                || tecnologiaSelected == null || formVtList == null
                || cmtEstadoxFlujoMgl.getGestionRol() == null || cmtEstadoxFlujoMgl.getGestionRol().isEmpty()) {

            validos = false;
        }

        if (subTipoOrdenOfscSelected != null) {
            if (tipoTrabajoRRSelect == null || estadoResultadoInicialRRSelect == null || estadoResultadoCierreRRSelect == null || (tiempoAgendaOfsc == null || tiempoAgendaOfsc.isEmpty())) {
                validos = false;
            }
        }

        return validos;
    }
    
    public boolean validarCamposAct() {
        
        boolean validos = true;
        if (tipoOtSelected == null || estadoInternoSelected == null || formVtSelected == null
                || estadoInicialSelected == null || cmtEstadoxFlujoMgl.getGestionRol().isEmpty()
                || tecnologiaSelected == null) {

            validos = false;
        }

        if (subTipoOrdenOfscSelected != null) {
            if (tipoTrabajoRRSelect == null || estadoResultadoInicialRRSelect == null || estadoResultadoCierreRRSelect == null || (tiempoAgendaOfsc == null || tiempoAgendaOfsc.isEmpty())) {
                validos = false;
            }
        }
        
        return validos;
    }
    
     public void validarCamposMensaje(String Campo) {
        
        String msn = "El Campo " + Campo + " Es Obligatorio";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        
    }
    
  public void eliminarEstadoxFlujoMgl(CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) {
        try {
            if (cmtEstadoxFlujoMgl.getEstadoxFlujoId() == null) {
                String msn = "Seleccione un registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtEstadoxFlujoMglFacadeLocal.delete(cmtEstadoxFlujoMgl, usuarioVT, perfilVT);
            listInfoByPage(1);
            limpiarCampos();
            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException ex) {
            String msn = "Proceso fall\u00f3 : " + ex.getMessage();
            FacesUtil.mostrarMensajeError(msn, ex, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al eliminar el registro de estado por flujo: " + e.getMessage(), e, LOGGER);
        }
    }
    
    public void goActualizar(BigDecimal idEstadosXflujo) {
        try {
              CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
            cmtEstadoxFlujoMgl = cmtEstadoxFlujoMglFacadeLocal.findById(idEstadosXflujo);
            
            if(cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj()!=null && 
                    cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId()!=null){
                int bdCambiaCmEstadoObj = cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId().intValueExact();
                estadoCMSelected = String.valueOf(bdCambiaCmEstadoObj);
            }
            int bdEstadoInternoObj = cmtEstadoxFlujoMgl.getEstadoInternoObj().getBasicaId().intValueExact();
            estadoInternoSelected = String.valueOf(bdEstadoInternoObj);

            int bdTipoFlujoOtObj = cmtEstadoxFlujoMgl.getTipoFlujoOtObj().getBasicaId().intValueExact();
            tipoOtSelected = String.valueOf(bdTipoFlujoOtObj);

            int bdForms = cmtEstadoxFlujoMgl.getFormulario().getBasicaId().intValueExact();
            formVtSelected = String.valueOf(bdForms);

            gestionRol = cmtEstadoxFlujoMgl.getGestionRol();
            estadoInicialSelected = cmtEstadoxFlujoMgl.getEsEstadoInicial();
            
            //valbuenayf inicio ajuste campo tecnologia
            if (cmtEstadoxFlujoMgl.getBasicaTecnologia() != null) {
                tecnologiaSelected = cmtEstadoxFlujoMgl.getBasicaTecnologia().getBasicaId();
            } else {
                tecnologiaSelected = null;
            }
            //valbuenayf fin ajuste campo tecnologia
            if (cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC() != null) {
                subTipoOrdenOfscSelected = cmtEstadoxFlujoMgl.getSubTipoOrdenOFSC().getBasicaId();
                tipoTrabajoRRSelect = cmtEstadoxFlujoMgl.getTipoTrabajoRR() != null ?  cmtEstadoxFlujoMgl.getTipoTrabajoRR().getBasicaId() : null;
                estadoResultadoInicialRRSelect = cmtEstadoxFlujoMgl.getEstadoOtRRInicial() != null ? cmtEstadoxFlujoMgl.getEstadoOtRRInicial().getBasicaId() : null;
                estadoResultadoCierreRRSelect = cmtEstadoxFlujoMgl.getEstadoOtRRFinal() != null ? cmtEstadoxFlujoMgl.getEstadoOtRRFinal().getBasicaId() : null;
                tiempoAgendaOfsc = String.valueOf(cmtEstadoxFlujoMgl.getTiempoAgendaOfsc() != 0 ? cmtEstadoxFlujoMgl.getTiempoAgendaOfsc() : 0);
            } else {
                subTipoOrdenOfscSelected = null;
                tipoTrabajoRRSelect = null;
                estadoResultadoInicialRRSelect = null;
                estadoResultadoCierreRRSelect = null;
                tiempoAgendaOfsc = null;
            }
            
            if (cmtEstadoxFlujoMgl.getDiasAMostrarAgenda() > 0) {
                this.dias = cmtEstadoxFlujoMgl.getDiasAMostrarAgenda()+"";
            }else{
                this.dias = null;
            }
            
            estadoCCMMVisible = cmtEstadoxFlujoMgl.getEsEstadoInicial().equalsIgnoreCase("Y");
            
            listEstadoCCMMParaFlujoSelected = new ArrayList<>();
            listEstadoCCMMParaFlujoIniciales = new ArrayList<>();
            List<CmtFlujosInicialesCm> listaEstadosFlujoBd = cmtEstadosInicialesCmManager.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
            if (listaEstadosFlujoBd != null && !listaEstadosFlujoBd.isEmpty()) {
                for (CmtFlujosInicialesCm estadosflujosTodos : listaEstadosFlujoBd) {
                    listEstadoCCMMParaFlujoSelected.add(estadosflujosTodos.getIdBasicaEstadoCm().getBasicaId().toString());
                    listEstadoCCMMParaFlujoIniciales.add(estadosflujosTodos.getIdBasicaEstadoCm().getBasicaId().toString());
                }
            }
            change();
            mostrarPanelCrearEstadoXFlujo=true;
            mostrarPanelListEstXFlujo=false;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : goActualizar()" + e.getMessage(), e, LOGGER);
        }
    }
    
   public void change() {
        try {
            cargar = false;
            btnActivo = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : change()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public void limpiarCampos() {
        cmtEstadoxFlujoMgl = new CmtEstadoxFlujoMgl();
        cmtEstadoxFlujoMgl.setGestionRol("");
        estadoCMSelected = null;
        estadoInternoSelected = null;
        tipoOtSelected = null;
        formVtSelected = null;
        tecnologiaSelected = null;//valbuenayf ajuste campo tecnologia
        this.tipoFlujoOtObj = null;//valbuenayf ajuste filtros
        this.tecnologiaFilter = null;//valbuenayf ajuste filtros

        subTipoOrdenOfscSelected = null;
        this.subTipoOrdenOFSCFilter = null;
        estadoCMlist = null;
        estadoInternolist = null;
        tipoOtlist = null;
        formVtList = null;
        gestionRol = "";
        estadoInicialSelected = null;
        this.dias = null;
        tipoTrabajoRRSelect = null;
        estadoResultadoInicialRRSelect = null;
        estadoResultadoCierreRRSelect = null;
        this.tiempoAgendaOfsc = null;
        listEstadoCCMMParaFlujoSelected = null;
        findByFiltro();
        estadoCCMMVisible  = false;
    }
    
      public void findByFiltro() {
        buscar();
        pageFirst();
    }
    
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
        }
    }
    
    public void pagePrevious() {
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
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
        }
    }
    
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException ex) {
            LOGGER.error("Error al ir a la página seleccionada. EX000 " + ex.getMessage(), ex);
        }
    }
    
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }
    
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }
    }
    
    public int getTotalPaginas() {
        if (pintarPaginado) {
            try {
                FiltroConsultaEstadosxFlujoDto filtroEstadosxFlujoDto;
                filtroEstadosxFlujoDto = cmtEstadoxFlujoMglFacadeLocal.findTablasEstadoxFlujo(params, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtroEstadosxFlujoDto.getNumRegistros();
                int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                        ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                        : count / ConstantsCM.PAGINACION_OCHO_FILAS);
                return totalPaginas;
            } catch (ApplicationException ex) {
                LOGGER.error("Error al obtener el total de páginas de la consulta. EX000 " + ex.getMessage(), ex);
            } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : getTotalPaginas()" + e.getMessage(), e, LOGGER);
        }
        }
        return 1;
    }
    
    public String getPageActual() {
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
        return pageActual;
    }
    
    public void buscar() {
        try {
            params = new HashMap<>();
            params.put("all", "1");
            params.put("estadoxFlujoId", this.estadoxFlujoId);
            params.put("tipoFlujoOtObj", this.tipoFlujoOtObj);
            params.put("nombreTipo", this.estadoInternoObj);
            params.put("codigoTipo", this.cambiaCmEstadoObj);
            params.put("descripcionTipo", this.formulario);
            params.put("estadoRegistro", this.esEstadoInicial);
            params.put("tecnologia", this.tecnologiaFilter);
            params.put("subTipoOrden", this.subTipoOrdenOFSCFilter);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }
    
    public boolean validarCreacion() {
        return validarPermisos(ValidacionUtil.OPC_CREACION);
    }
    
    public boolean validarEdicion() {
        return validarPermisos(ValidacionUtil.OPC_EDICION);
    }
    
    public boolean validarBorrado() {
        return validarPermisos(ValidacionUtil.OPC_BORRADO);
    }
    
    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, accion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar permisos. EX000 " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadosxFlujosBean : validarPermisos()" + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * valbuenayf metodo que limpia la informacion para un nuevo Estados x
     * Flujos
     */
    public void nuevoRegistro() {
        try {
            limpiarCampos();
            cargar = true;
            btnActivo = true;
        } catch (Exception e) {
            LOGGER.error("Error en nuevo Registro" + e.getMessage(),e);
        }
    }
    
    public List<CmtBasicaMgl> getListTablaBasicaFlujoOT() {
        return listTablaBasicaFlujoOT;
    }
    
    public void setListTablaBasicaFlujoOT(List<CmtBasicaMgl> listTablaBasicaFlujoOT) {
        this.listTablaBasicaFlujoOT = listTablaBasicaFlujoOT;
    }
    
    public List<CmtBasicaMgl> getListTablaBasicaEstadoInternoOt() {
        return listTablaBasicaEstadoInternoOt;
    }
    
    public void setListTablaBasicaEstadoInternoOt(List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt) {
        this.listTablaBasicaEstadoInternoOt = listTablaBasicaEstadoInternoOt;
    }
    
    public List<CmtBasicaMgl> getListTablaBasicaEstadoCM() {
        return listTablaBasicaEstadoCM;
    }
    
    public void setListTablaBasicaEstadoCM(List<CmtBasicaMgl> listTablaBasicaEstadoCM) {
        this.listTablaBasicaEstadoCM = listTablaBasicaEstadoCM;
    }
    
    public CmtEstadoxFlujoMgl getCmtEstadoxFlujoMgl() {
        return cmtEstadoxFlujoMgl;
    }
    
    public void setCmtEstadoxFlujoMgl(CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) {
        this.cmtEstadoxFlujoMgl = cmtEstadoxFlujoMgl;
    }
    
    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }
    
    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }
    
    public String getEstadoInicialSelected() {
        return estadoInicialSelected;
    }
    
    public void setEstadoInicialSelected(String estadoInicialSelected) {
        this.estadoInicialSelected = estadoInicialSelected;
    }
    
    public String getRolSelected() {
        return rolSelected;
    }
    
    public void setRolSelected(String rolSelected) {
        this.rolSelected = rolSelected;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<CmtEstadoxFlujoMgl> getListCmtEstadoxFlujoMgl() {
        return listCmtEstadoxFlujoMgl;
    }
    
    public void setListCmtEstadoxFlujoMgl(List<CmtEstadoxFlujoMgl> listCmtEstadoxFlujoMgl) {
        this.listCmtEstadoxFlujoMgl = listCmtEstadoxFlujoMgl;
    }
    
    public boolean isHabilitaObj() {
        return habilitaObj;
    }
    
    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }
    
    public int getActual() {
        return actual;
    }
    
    public void setActual(int actual) {
        this.actual = actual;
    }
    
    public HashMap<String, Object> getParams() {
        return params;
    }
    
    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
    
    public int getFilasPag() {
        return ConstantsCM.PAGINACION_OCHO_FILAS;
    }
    
    public String getEstadoxFlujoId() {
        return estadoxFlujoId;
    }
    
    public void setEstadoxFlujoId(String estadoxFlujoId) {
        this.estadoxFlujoId = estadoxFlujoId;
    }
    
    public String getEstadoInternoObj() {
        return estadoInternoObj;
    }
    
    public void setEstadoInternoObj(String estadoInternoObj) {
        this.estadoInternoObj = estadoInternoObj;
    }
    
    public String getTipoFlujoOtObj() {
        return tipoFlujoOtObj;
    }
    
    public void setTipoFlujoOtObj(String tipoFlujoOtObj) {
        this.tipoFlujoOtObj = tipoFlujoOtObj;
    }
    
    public String getCambiaCmEstadoObj() {
        return cambiaCmEstadoObj;
    }
    
    public void setCambiaCmEstadoObj(String cambiaCmEstadoObj) {
        this.cambiaCmEstadoObj = cambiaCmEstadoObj;
    }
    
    public String getFormulario() {
        return formulario;
    }
    
    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }
    
    public String getEsEstadoInicial() {
        return esEstadoInicial;
    }
    
    public void setEsEstadoInicial(String esEstadoInicial) {
        this.esEstadoInicial = esEstadoInicial;
    }
    
    public String getGestionRol() {
        return gestionRol;
    }
    
    public void setGestionRol(String gestionRol) {
        this.gestionRol = gestionRol;
    }
    
    public String getNumPagina() {
        return numPagina;
    }
    
    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }
    
    public boolean isPintarPaginado() {
        return pintarPaginado;
    }
    
    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }
    
    public List<Integer> getPaginaList() {
        return paginaList;
    }
    
    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }
    
    public boolean isBtnActivo() {
        return btnActivo;
    }
    
    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }
    
    public String getTipoOtlist() {
        return tipoOtlist;
    }
    
    public void setTipoOtlist(String tipoOtlist) {
        this.tipoOtlist = tipoOtlist;
    }
    
    public String getEstadoCMlist() {
        return estadoCMlist;
    }
    
    public void setEstadoCMlist(String estadoCMlist) {
        this.estadoCMlist = estadoCMlist;
    }
    
    public String getEstadoInternolist() {
        return estadoInternolist;
    }
    
    public void setEstadoInternolist(String estadoInternolist) {
        this.estadoInternolist = estadoInternolist;
    }
    
    public String getTipoOtSelected() {
        return tipoOtSelected;
    }
    
    public void setTipoOtSelected(String tipoOtSelected) {
        this.tipoOtSelected = tipoOtSelected;
    }
    
    public String getEstadoCMSelected() {
        return estadoCMSelected;
    }
    
    public void setEstadoCMSelected(String estadoCMSelected) {
        this.estadoCMSelected = estadoCMSelected;
    }
    
    public String getEstadoInternoSelected() {
        return estadoInternoSelected;
    }
    
    public void setEstadoInternoSelected(String estadoInternoSelected) {
        this.estadoInternoSelected = estadoInternoSelected;
    }
    
    public boolean isCargar() {
        return cargar;
    }
    
    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }
    
    public List<CmtBasicaMgl> getListTablaBasicaForm() {
        return listTablaBasicaForm;
    }
    
    public void setListTablaBasicaForm(List<CmtBasicaMgl> listTablaBasicaForm) {
        this.listTablaBasicaForm = listTablaBasicaForm;
    }
    
    public String getFormVtList() {
        return formVtList;
    }
    
    public void setFormVtList(String formVtList) {
        this.formVtList = formVtList;
    }
    
    public String getFormVtSelected() {
        return formVtSelected;
    }
    
    public void setFormVtSelected(String formVtSelected) {
        this.formVtSelected = formVtSelected;
    }
    
    public List<CmtBasicaMgl> getListBasicaTecnologia() {
        return listBasicaTecnologia;
    }
    
    public void setListBasicaTecnologia(List<CmtBasicaMgl> listBasicaTecnologia) {
        this.listBasicaTecnologia = listBasicaTecnologia;
    }
    
    public BigDecimal getTecnologiaSelected() {
        return tecnologiaSelected;
    }
    
    public void setTecnologiaSelected(BigDecimal tecnologiaSelected) {
        this.tecnologiaSelected = tecnologiaSelected;
    }
    
    public String getTecnologiaFilter() {
        return tecnologiaFilter;
    }
    
    public void setTecnologiaFilter(String tecnologiaFilter) {
        this.tecnologiaFilter = tecnologiaFilter;
    }
    
    public BigDecimal getSubTipoOrdenOfscSelected() {
        return subTipoOrdenOfscSelected;
    }
    
    public void setSubTipoOrdenOfscSelected(BigDecimal subTipoOrdenOfscSelected) {
        this.subTipoOrdenOfscSelected = subTipoOrdenOfscSelected;
    }
    
    public List<CmtBasicaMgl> getListBasicaSubtipoOrdenOFSC() {
        return listBasicaSubtipoOrdenOFSC;
    }
    
    public void setListBasicaSubtipoOrdenOFSC(List<CmtBasicaMgl> listBasicaSubtipoOrdenOFSC) {
        this.listBasicaSubtipoOrdenOFSC = listBasicaSubtipoOrdenOFSC;
    }
    
    public String getSubTipoOrdenOFSCFilter() {
        return subTipoOrdenOFSCFilter;
    }
    
    public void setSubTipoOrdenOFSCFilter(String subTipoOrdenOFSCFilter) {
        this.subTipoOrdenOFSCFilter = subTipoOrdenOFSCFilter;
    }
    
    public HtmlDataTable getDataTable() {
        return dataTable;
    }
    
    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public HashMap<String, Object> getParamsValidacionGuardar() {
        return paramsValidacionGuardar;
    }

    public void setParamsValidacionGuardar(HashMap<String, Object> paramsValidacionGuardar) {
        this.paramsValidacionGuardar = paramsValidacionGuardar;
    }

    public HashMap<String, Object> getParamsValidacionActualizar() {
        return paramsValidacionActualizar;
    }

    public void setParamsValidacionActualizar(HashMap<String, Object> paramsValidacionActualizar) {
        this.paramsValidacionActualizar = paramsValidacionActualizar;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public boolean isMostrarPanelCrearEstadoXFlujo() {
        return mostrarPanelCrearEstadoXFlujo;
    }

    public void setMostrarPanelCrearEstadoXFlujo(boolean mostrarPanelCrearEstadoXFlujo) {
        this.mostrarPanelCrearEstadoXFlujo = mostrarPanelCrearEstadoXFlujo;
    }

    public boolean isMostrarPanelListEstXFlujo() {
        return mostrarPanelListEstXFlujo;
    }

    public void setMostrarPanelListEstXFlujo(boolean mostrarPanelListEstXFlujo) {
        this.mostrarPanelListEstXFlujo = mostrarPanelListEstXFlujo;
    }
    
    public void crearNuevoEstadoXFlujo() {

        mostrarPanelCrearEstadoXFlujo = true;
        mostrarPanelListEstXFlujo = false;
        nuevoRegistro();

    }

    public void volver() {
        mostrarPanelCrearEstadoXFlujo = false; 
        mostrarPanelListEstXFlujo = true;
    }
    
    public void mostrarEstadoCCMMIni() {
        if (estadoInicialSelected != null && !estadoInicialSelected.isEmpty()) {
            if (estadoInicialSelected.equalsIgnoreCase("Y")) {
                estadoCCMMVisible = true;
            } else {
                estadoCCMMVisible = false;
            }
        } else {
            estadoCCMMVisible = false;
        }
    }

    public List<CmtBasicaMgl> getListadoTipodeTrabajo() {
        return listadoTipodeTrabajo;
    }

    public void setListadoTipodeTrabajo(List<CmtBasicaMgl> listadoTipodeTrabajo) {
        this.listadoTipodeTrabajo = listadoTipodeTrabajo;
    }

    public List<CmtBasicaMgl> getListadoEstadoCierre() {
        return listadoEstadoCierre;
    }

    public void setListadoEstadoCierre(List<CmtBasicaMgl> listadoEstadoCierre) {
        this.listadoEstadoCierre = listadoEstadoCierre;
    }

    public List<CmtBasicaMgl> getListadoEstadoInicial() {
        return listadoEstadoInicial;
    }

    public void setListadoEstadoInicial(List<CmtBasicaMgl> listadoEstadoInicial) {
        this.listadoEstadoInicial = listadoEstadoInicial;
    }

    public BigDecimal getTipoTrabajoRRSelect() {
        return tipoTrabajoRRSelect;
    }

    public void setTipoTrabajoRRSelect(BigDecimal tipoTrabajoRRSelect) {
        this.tipoTrabajoRRSelect = tipoTrabajoRRSelect;
    }

    public BigDecimal getEstadoResultadoInicialRRSelect() {
        return estadoResultadoInicialRRSelect;
    }

    public void setEstadoResultadoInicialRRSelect(BigDecimal estadoResultadoInicialRRSelect) {
        this.estadoResultadoInicialRRSelect = estadoResultadoInicialRRSelect;
    }

    public BigDecimal getEstadoResultadoCierreRRSelect() {
        return estadoResultadoCierreRRSelect;
    }

    public void setEstadoResultadoCierreRRSelect(BigDecimal estadoResultadoCierreRRSelect) {
        this.estadoResultadoCierreRRSelect = estadoResultadoCierreRRSelect;
    }
     public void guardarEstadosInicialesCCMM(List<String> listaCmtFlujosInicialesCm , CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {
      
         
         if (listaCmtFlujosInicialesCm != null && !listaCmtFlujosInicialesCm.isEmpty()) {
             for (String estado : listaCmtFlujosInicialesCm) {
                 if (estado != null) {
                     CmtBasicaMgl cmtBasicaMglEstado = new CmtBasicaMgl();
                     cmtBasicaMglEstado.setBasicaId(new BigDecimal(estado));
                     CmtFlujosInicialesCm cmtFlujosInicialesCm = new CmtFlujosInicialesCm();
                     cmtFlujosInicialesCm.setIdEstadoFlujo(cmtEstadoxFlujoMgl);
                     cmtFlujosInicialesCm.setIdBasicaEstadoCm(cmtBasicaMglEstado);
                     cmtEstadosInicialesCCMMFacadeLocal.setUser(usuarioVT, perfilVT);
                     try {
                         cmtEstadosInicialesCCMMFacadeLocal.create(cmtFlujosInicialesCm, usuarioVT, perfilVT);
                     } catch (ApplicationException ex) {
                         String msgError = "Error al guardar estados iniciales de CCMM: " + ex.getMessage();
                         LOGGER.error(msgError, ex);
                     }

                 }
             }
         }
        
    }
    public void actualizarEstadosInicialesCCMM(List<String> listaCmtFlujosInicialesCm, CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl) throws ApplicationException {
        if (listaCmtFlujosInicialesCm != null && !listaCmtFlujosInicialesCm.isEmpty()) {
            List<String> listaEstadosString = new ArrayList<>();
            List<CmtFlujosInicialesCm> listaEstadosGuardados;
            CmtFlujosInicialesCmManager cmtEstadosInicialesCmManager = new CmtFlujosInicialesCmManager();
            listaEstadosGuardados = cmtEstadosInicialesCmManager.getEstadosIniCMByEstadoxFlujoId(cmtEstadoxFlujoMgl);
            for (CmtFlujosInicialesCm estadosflujosTodos : listaEstadosGuardados) {
                listaEstadosString.add(estadosflujosTodos.getIdBasicaEstadoCm().getBasicaId().toString());
            }
            if (listaEstadosGuardados != null) {
                for (String estado : listaCmtFlujosInicialesCm) {

                    cmtEstadosInicialesCCMMFacadeLocal.setUser(usuarioVT, perfilVT);
                    if (!listaEstadosString.contains(estado)) {
                        //se crea 
                        CmtBasicaMgl cmtBasicaMglEstado = new CmtBasicaMgl();
                        cmtBasicaMglEstado.setBasicaId(new BigDecimal(estado));
                        CmtFlujosInicialesCm cmtFlujosInicialesCm = new CmtFlujosInicialesCm();
                        cmtFlujosInicialesCm.setIdEstadoFlujo(cmtEstadoxFlujoMgl);
                        cmtFlujosInicialesCm.setIdBasicaEstadoCm(cmtBasicaMglEstado);
                        cmtEstadosInicialesCCMMFacadeLocal.setUser(usuarioVT, perfilVT);
                        try {
                            cmtEstadosInicialesCCMMFacadeLocal.create(cmtFlujosInicialesCm, usuarioVT, perfilVT);
                        } catch (ApplicationException ex) {
                            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                            LOGGER.error(msgError, ex);
                        }
                    }
                }
                for (CmtFlujosInicialesCm estadoBd : listaEstadosGuardados) {

                    if (!listaCmtFlujosInicialesCm.contains(estadoBd.getIdBasicaEstadoCm().getBasicaId().toString())) {
                        //se crea 
                        estadoBd.setEstadoRegistro(0);
                        try {
                            cmtEstadosInicialesCCMMFacadeLocal.update(estadoBd, usuarioVT, perfilVT);
                        } catch (ApplicationException ex) {
                            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + ex.getMessage();
                            LOGGER.error(msgError, ex);
                        }
                    }
                }
            }
        }

    }
    
    public boolean equalLists(List<String> a, List<String> b) {
        // comprobar que tienen el mismo tamaño y que no son nulos
        if ((a.size() != b.size()) || 
                (a == null && b != null) ||
                (a != null && b == null)) {
            return false;
        }
        // ordenar las ArrayList y comprobar que son iguales          
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

    /**
     * @return the tiempoAgendaOfsc
     */
    public String getTiempoAgendaOfsc() {
        return tiempoAgendaOfsc;
    }

    /**
     * @param tiempoAgendaOfsc the tiempoAgendaOfsc to set
     */
    public void setTiempoAgendaOfsc(String tiempoAgendaOfsc) {
        this.tiempoAgendaOfsc = tiempoAgendaOfsc;
    }

    public boolean isEstadoCCMMVisible() {
        return estadoCCMMVisible;
    }

    public void setEstadoCCMMVisible(boolean estadoCCMMVisible) {
        this.estadoCCMMVisible = estadoCCMMVisible;
    }

    public List<String> getListEstadoCCMMParaFlujoSelected() {
        return listEstadoCCMMParaFlujoSelected;
    }

    public void setListEstadoCCMMParaFlujoSelected(List<String> listEstadoCCMMParaFlujoSelected) {
        this.listEstadoCCMMParaFlujoSelected = listEstadoCCMMParaFlujoSelected;
    }
    
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(NWBTNFLUJ);
    }
    
    public boolean validarOpcionLinkCodigo() {
        return validarEdicionRol(CODBTNFLUJ);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(DELBTNFLUJ);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
   
    
}
