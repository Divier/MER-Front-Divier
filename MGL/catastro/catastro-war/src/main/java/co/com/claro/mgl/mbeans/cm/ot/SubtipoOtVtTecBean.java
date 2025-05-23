package co.com.claro.mgl.mbeans.cm.ot;

import co.com.claro.mgl.businessmanager.cm.CmtTipoOtMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoOtTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubtipoOtVtTecFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "subtipoOtVtTecBean")
@ViewScoped
public class SubtipoOtVtTecBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(SubtipoOtVtTecBean.class);
    private static final long serialVersionUID = 1L;
    
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FiltroConsultaSubTipoOtTecDto filtroConsultaSubTipoOtTecDto = new FiltroConsultaSubTipoOtTecDto();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "SUBTIPOOTTEC";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<CmtBasicaMgl> listTablaBasicaFlujoOT;
    private List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt;
    private List<CmtBasicaMgl> listTablaBasicaEstadoCM;
    private List<CmtBasicaMgl> listTablaBasicaForm;
    private List<CmtBasicaMgl> listBasicaTecnologia;//valbuenayf
    private List<CmtBasicaMgl> listBasicaSubtipoOrdenOFSC;
    private BigDecimal tipoOtlist;
    private String estadoCMlist;
    private String estadoInternolist;
    private String formVtList;
    private String estadoCMSelected;
    private String estadoInternoSelected;
    private String estadoInicialSelected;
    private String formVtSelected;
    private String rolSelected;
    private BigDecimal tecnologiaSelected;//valbuenayf
    private String tecnologiaFilter;//valbuenayf
    private String message;
    
    private HashMap<String, Object> params;
    private HashMap<String, Object> paramsValidacionGuardar;
    private HashMap<String, Object> paramsValidacionActualizar;
   
    private boolean habilitaObj = false;
    private int actual;
   
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
    private boolean mostrarPanelCrearSubTipoOT;
    private boolean mostrarPanelListSubTipoOT;
    
    private List<CmtBasicaMgl> listadoTipodeTrabajo;
    private List<CmtBasicaMgl> listadoEstadoCierre;
    private List<CmtBasicaMgl> listadoEstadoInicial;
    
    private BigDecimal tipoTrabajoRRSelect;
    private BigDecimal estadoResultadoInicialRRSelect;
    private BigDecimal estadoResultadoCierreRRSelect;
    private String tiempoAgendaOfsc;
    private List<CmtSubtipoOrdenVtTecMgl> listCmtSubtipoOrdenVtTecMgl;
    private CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl;
    private List<CmtTipoOtMgl> subTipoOrdenTrabajo;
    private BigDecimal tipoOtSelected;
    @EJB
    private CmtSubtipoOtVtTecFacadeLocal cmtSubtipoOtVtTecFacadeLocal;
    private boolean desactivarTecnologia = false;
    private List<AuditoriaDto> informacionAuditoria = null;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    private String descripcionSubtipo = "";
    private String tipoBasicaDescripcion = "";
    private boolean renderAuditoria = false;
    
    //Opciones agregadas para Rol
    private final String BTVTOTSUB = "BTVTOTSUB";    
    private final String BTVTOTSUBCOD = "BTVTOTSUBCOD";    
    private final String BTVTOTSUBDEL = "BTVTOTSUBDEL";    
    
    public SubtipoOtVtTecBean() {
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
            cmtSubtipoOrdenVtTecMgl = new CmtSubtipoOrdenVtTecMgl();
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se genera error al momento de iniciar: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se produce un error al momento de iniciar: " + e.getMessage(), e, LOGGER);
        }
    }
    
    @PostConstruct
    public void cargarListas() {
        try {
            cmtSubtipoOtVtTecFacadeLocal.setUser(usuarioVT, perfilVT);

            //Consulta los tipos de flujo
             CmtTipoOtMglManager otMglManager = new CmtTipoOtMglManager();
                subTipoOrdenTrabajo = otMglManager.findAllTipoOtVts();

          
            //Consulta Las Tecnologias
            if (listBasicaTecnologia == null
                    || listBasicaTecnologia.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);
                listBasicaTecnologia = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

          
            pintarPaginado = true;
            buscar();
            listInfoByPage(1);
            mostrarPanelCrearSubTipoOT=false;
            mostrarPanelListSubTipoOT=true; 
            
           
           
        } catch (ApplicationException e) {
            LOGGER.error("Error al cargar listas. EX000 " + e.getMessage(), e);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : cargarListas()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (ConstantsCM.PAGINACION_OCHO_FILAS * (page - 1));
            }
            filtroConsultaSubTipoOtTecDto = cmtSubtipoOtVtTecFacadeLocal.findSubtipoOtTec(
                    params, ConstantsCM.PAGINACION_DATOS, firstResult, ConstantsCM.PAGINACION_OCHO_FILAS);
            listCmtSubtipoOrdenVtTecMgl = filtroConsultaSubTipoOtTecDto.getListaCmtSubtipoOrdenVtTecMgl();
            habilitaObj = !listCmtSubtipoOrdenVtTecMgl.isEmpty();
            if (listCmtSubtipoOrdenVtTecMgl.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            if (listCmtSubtipoOrdenVtTecMgl.size() == 1) {
                cmtSubtipoOrdenVtTecMgl = listCmtSubtipoOrdenVtTecMgl.get(0);
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
        listaEstadosInt = new ArrayList<BigDecimal>();

        return listaEstadosInt.contains(new BigDecimal(estadoInternolist));
    }

    public boolean validarEstadosactualizar() {
        List<BigDecimal> listaEstadosInt;
        listaEstadosInt = new ArrayList<BigDecimal>();

        return listaEstadosInt.contains(new BigDecimal(estadoInternoSelected));
    }
    
    public void guardarSubtipoOtVtTec() {
        if (!validarCamposGuardar()) {
            String msn = "Faltan campos obligatorios por llenar";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        }

        if (validarConfigDuplicadaGuardar()) {
            String msn = "La configuracion que esta ingresando ya existe ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        } else {
            crearSubtipoOtVtTec();
        }
        
    }
    
   public void actualizaSubtipoOtVtTec() {
        String msn;
        desactivarTecnologia = true;
        if (!validarCamposAct()) {
            msn = "Existen campos obligatorios  sin completar ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        }
        
        if (validarConfigDuplicadaGuardar()) {
            String msn2 = "La configuracion que esta ingresando ya existe ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, ""));
            return;
        } else {
            if (validarAllTecnologies() && cmtSubtipoOrdenVtTecMgl.getTipoFlujoOtObj().getIdTipoOt().compareTo(tipoOtlist)== 0) {
                tecnologiaSelected = cmtSubtipoOrdenVtTecMgl.getBasicaTecnologia().getBasicaId();
                String msn1 = "Todas las Tecnologias estan configuradas, Por favor cambie el Sub Tipo Ordenes de Ot ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn1, ""));
                return;
            } else {
                actualizarSubtipoOtVtTec();
            }
        }

        
     
    }

    
    public void crearSubtipoOtVtTec() {
        try {
            if (!validarCamposGuardar()) {
                return;
            }
            CmtSubtipoOrdenVtTecMgl subTipos = new CmtSubtipoOrdenVtTecMgl();
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
             CmtTipoOtMgl subOrden = new CmtTipoOtMgl();
            subOrden.setIdTipoOt(tipoOtlist);
            subTipos.setBasicaTecnologia(tecnologia);
            subTipos.setTipoFlujoOtObj(subOrden);
            subTipos.setFechaCreacion(new Date());
            subTipos.setFechaEdicion(null);
            subTipos.setPerfilEdicion(0);
            subTipos.setEstadoRegistro(1);
            subTipos = cmtSubtipoOtVtTecFacadeLocal.create(subTipos, usuarioVT, perfilVT);
            if (subTipos != null) {
                listInfoByPage(1);
                limpiarCampos();
                String msn = "Proceso exitoso, se ha creado el estado x flujo con el Id: <b>" + subTipos.getIdSubtipoOtVtTec()+ "</b>";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                mostrarPanelCrearSubTipoOT=false;
                mostrarPanelListSubTipoOT=true;
            }
        } catch (ApplicationException | NumberFormatException ex) {
             limpiarCampos();
            cmtSubtipoOrdenVtTecMgl.setIdSubtipoOtVtTec(BigDecimal.ZERO);
            String msn = "Proceso fallo: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : crearEstadoxFlujo()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public void actualizarSubtipoOtVtTec() {
        try {
            if (tipoOtlist == null || tecnologiaSelected == null) {
                String msn = "No hay un registro seleccionado";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            CmtTipoOtMgl subOrden = new CmtTipoOtMgl();
            subOrden.setIdTipoOt(tipoOtlist);
            cmtSubtipoOrdenVtTecMgl.setBasicaTecnologia(tecnologia);
            cmtSubtipoOrdenVtTecMgl.setTipoFlujoOtObj(subOrden);
            cmtSubtipoOrdenVtTecMgl.setFechaCreacion(new Date());
            cmtSubtipoOrdenVtTecMgl.setFechaEdicion(null);
            cmtSubtipoOrdenVtTecMgl.setPerfilEdicion(0);

            cmtSubtipoOrdenVtTecMgl = cmtSubtipoOtVtTecFacadeLocal.update(cmtSubtipoOrdenVtTecMgl, usuarioVT, perfilVT);
            if (cmtSubtipoOrdenVtTecMgl != null) {
                listInfoByPage(1);
                nuevoRegistro();//valbuenayf ajuste boton nuevo
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                mostrarPanelCrearSubTipoOT = false;
                mostrarPanelListSubTipoOT = true;
            }
        } catch (ApplicationException ex) {
            String msn = "Proceso fallo: " + ex.getMessage();
            FacesUtil.mostrarMensajeError(msn, ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : actualizarSubtipoOtVtTec()" + e.getMessage(), e, LOGGER);
        }
    }
    
        public boolean validarCamposGuardar() {
        
        boolean validos = true;
        if (tipoOtlist == null ||  tecnologiaSelected == null ) {
            validos = false;
        }
        return validos;
    }
    
    public boolean validarCamposAct() {
        
        boolean validos = true;
        if (tipoOtlist == null || tecnologiaSelected == null ) {
            
            validos = false;
        }
        
        return validos;
    }
    
    public void validarCamposMensaje(String Campo) {
        
        String msn = "El Campo " + Campo + " Es Obligatorio";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        
    }
    
    public void eliminarActualizarSubtipoOtVtTec(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) {
        try {
            if (cmtSubtipoOrdenVtTecMgl.getIdSubtipoOtVtTec()== null) {
                String msn = "Seleccione un registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtSubtipoOtVtTecFacadeLocal.delete(cmtSubtipoOrdenVtTecMgl, usuarioVT, perfilVT);
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
    
    public void goActualizar(CmtSubtipoOrdenVtTecMgl idSubtipoOtTec) {
        try {
            desactivarTecnologia = true;
            cmtSubtipoOrdenVtTecMgl = cmtSubtipoOtVtTecFacadeLocal.findById(idSubtipoOtTec);
                //valbuenayf inicio ajuste campo tecnologia
            if (cmtSubtipoOrdenVtTecMgl.getBasicaTecnologia() != null) {
                tecnologiaSelected = cmtSubtipoOrdenVtTecMgl.getBasicaTecnologia().getBasicaId();
            } else {
                tecnologiaSelected = null;
            }
            
            //valbuenayf inicio ajuste campo tecnologia
            if (cmtSubtipoOrdenVtTecMgl != null) {
                tipoOtlist = cmtSubtipoOrdenVtTecMgl.getTipoFlujoOtObj().getIdTipoOt();
            } else {
                tipoOtlist = null;
            }
            
            change();
            mostrarPanelCrearSubTipoOT=true;
            mostrarPanelListSubTipoOT=false;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : goActualizar()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public void change() {
        try {
            cargar = false;
            btnActivo = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : change()" + e.getMessage(), e, LOGGER);
        }
    }
    
    public void limpiarCampos() {
        cmtSubtipoOrdenVtTecMgl = new CmtSubtipoOrdenVtTecMgl();
        tecnologiaSelected = null;//valbuenayf ajuste campo tecnologia
        tipoOtlist = null;
 
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
                filtroConsultaSubTipoOtTecDto = cmtSubtipoOtVtTecFacadeLocal.findSubtipoOtTec(params, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtroConsultaSubTipoOtTecDto.getNumRegistros();
                int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                        ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                        : count / ConstantsCM.PAGINACION_OCHO_FILAS);
                return totalPaginas;
            } catch (ApplicationException ex) {
                LOGGER.error("Error al obtener el total de páginas de la consulta. EX000 " + ex.getMessage(), ex);
            } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : getTotalPaginas()" + e.getMessage(), e, LOGGER);
        }
        }
        return 1;
    }
    
    public String getPageActual() {
        paginaList = new ArrayList<Integer>();
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
            params = new HashMap<String, Object>();
            params.put("all", "1");
            params.put("basicaTecnologia", this.tecnologiaSelected);
            params.put("tipoFlujoOtObj", this.tipoOtSelected);
           
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
            FacesUtil.mostrarMensajeError("Error en SubtipoOtVtTecBean : validarPermisos()" + e.getMessage(), e, LOGGER);
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
    
    
    public void mostrarAuditoria(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) {
        if (cmtSubtipoOrdenVtTecMgl != null) {
            try {
                informacionAuditoria = cmtSubtipoOtVtTecFacadeLocal.construirAuditoria(cmtSubtipoOrdenVtTecMgl);
                descripcionSubtipo = cmtSubtipoOrdenVtTecMgl.getTipoFlujoOtObj().getDescTipoOt();
                tipoBasicaDescripcion = cmtSubtipoOrdenVtTecMgl.getBasicaTecnologia().getNombreBasica();
                renderAuditoria = true;
                 mostrarPanelListSubTipoOT = false;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al TablasBasicasMglBean: mostrarAuditoria(). " + e.getMessage(), e, LOGGER);
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }
       public boolean validarConfigDuplicadaGuardar() {
        boolean duplicado = false;
        try {
            List<CmtSubtipoOrdenVtTecMgl> cmtSubtipoOrdenVtTecMglList;
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            CmtTipoOtMgl cmtTipoOtMgl = new CmtTipoOtMgl();
            cmtTipoOtMgl.setIdTipoOt(tipoOtlist);
            cmtSubtipoOrdenVtTecMglList = cmtSubtipoOtVtTecFacadeLocal.findConfSubtipoOtxTecno(tecnologia,cmtTipoOtMgl);
            if (!cmtSubtipoOrdenVtTecMglList.isEmpty()) {
                duplicado = true;
            }
            
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + ex.getMessage(), ex, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + e.getMessage(), e, LOGGER);
        }
        return duplicado;
    }
           public boolean validarAllTecnologies() {
        List<CmtSubtipoOrdenVtTecMgl> listaCmtSubtipoOrdenVtTecMgl = null;
 
        try {
            listaCmtSubtipoOrdenVtTecMgl = cmtSubtipoOtVtTecFacadeLocal.findAll();
        } catch (ApplicationException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        List<BigDecimal> listaBasica = new ArrayList<>();
        for (CmtSubtipoOrdenVtTecMgl tec : listaCmtSubtipoOrdenVtTecMgl) {
            listaBasica.add(tec.getBasicaTecnologia().getBasicaId());
        }
        boolean validos = false;
        if (listaBasica.contains(tecnologiaSelected)) {
            validos = true;
        }
        return validos;
    }
    
     public void ocultarAuditoria() {
        renderAuditoria = false;
         mostrarPanelListSubTipoOT = true;
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

    public BigDecimal getTipoOtlist() {
        return tipoOtlist;
    }

    public void setTipoOtlist(BigDecimal tipoOtlist) {
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

    public BigDecimal getTipoOtSelected() {
        return tipoOtSelected;
    }

    public void setTipoOtSelected(BigDecimal tipoOtSelected) {
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

    public boolean isMostrarPanelCrearSubTipoOT() {
        return mostrarPanelCrearSubTipoOT;
    }

    public void setMostrarPanelCrearSubTipoOT(boolean mostrarPanelCrearSubTipoOT) {
        this.mostrarPanelCrearSubTipoOT = mostrarPanelCrearSubTipoOT;
    }

    public boolean isMostrarPanelListSubTipoOT() {
        return mostrarPanelListSubTipoOT;
    }

    public void setMostrarPanelListSubTipoOT(boolean mostrarPanelListSubTipoOT) {
        this.mostrarPanelListSubTipoOT = mostrarPanelListSubTipoOT;
    }

 
    
    public void crearNuevoSubtipoOtVtTec() {

        mostrarPanelCrearSubTipoOT = true;
        mostrarPanelListSubTipoOT = false;
        nuevoRegistro();

    }

    public void volver() {
        mostrarPanelCrearSubTipoOT = false; 
        mostrarPanelListSubTipoOT = true;
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(BTVTOTSUB);
    }
    
    public boolean validarOpcionLinkCod() {
        return validarEdicionRol(BTVTOTSUBCOD);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(BTVTOTSUBDEL);
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
            FacesUtil.mostrarMensajeError("Error al SubtipoOtVtTecBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
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

    public List<CmtSubtipoOrdenVtTecMgl> getListCmtSubtipoOrdenVtTecMgl() {
        return listCmtSubtipoOrdenVtTecMgl;
    }

    public void setListCmtSubtipoOrdenVtTecMgl(List<CmtSubtipoOrdenVtTecMgl> listCmtSubtipoOrdenVtTecMgl) {
        this.listCmtSubtipoOrdenVtTecMgl = listCmtSubtipoOrdenVtTecMgl;
    }

    public CmtSubtipoOrdenVtTecMgl getCmtSubtipoOrdenVtTecMgl() {
        return cmtSubtipoOrdenVtTecMgl;
    }

    public void setCmtSubtipoOrdenVtTecMgl(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) {
        this.cmtSubtipoOrdenVtTecMgl = cmtSubtipoOrdenVtTecMgl;
    }

    public List<CmtTipoOtMgl> getSubTipoOrdenTrabajo() {
        return subTipoOrdenTrabajo;
    }

    public void setSubTipoOrdenTrabajo(List<CmtTipoOtMgl> subTipoOrdenTrabajo) {
        this.subTipoOrdenTrabajo = subTipoOrdenTrabajo;
    }

    public boolean isDesactivarTecnologia() {
        return desactivarTecnologia;
    }

    public void setDesactivarTecnologia(boolean desactivarTecnologia) {
        this.desactivarTecnologia = desactivarTecnologia;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public String getDescripcionSubtipo() {
        return descripcionSubtipo;
    }

    public void setDescripcionSubtipo(String descripcionSubtipo) {
        this.descripcionSubtipo = descripcionSubtipo;
    }

    public String getTipoBasicaDescripcion() {
        return tipoBasicaDescripcion;
    }

    public void setTipoBasicaDescripcion(String tipoBasicaDescripcion) {
        this.tipoBasicaDescripcion = tipoBasicaDescripcion;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }
    
    
    
}
