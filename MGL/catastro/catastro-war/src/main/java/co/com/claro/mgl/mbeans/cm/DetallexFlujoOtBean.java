/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaDetalleFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDetalleFlujoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "detallexFlujoOtBean")
@ViewScoped
public class DetallexFlujoOtBean {

    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtDetalleFlujoMglFacadeLocal cmtDetalleFlujoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    private String estiloObligatorio = "<font color='red'>*</font>";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(DetallexFlujoOtBean.class);
    private FiltroConsultaDetalleFlujoDto filtroConsultaDetalleFlujoDto = new FiltroConsultaDetalleFlujoDto();
    private final String FORMULARIO = "REGLAFLUJOOT";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtDetalleFlujoMgl cmtDetalleFlujoMgl;
    private String message;
    private List<CmtBasicaMgl> listTablaBasicaFlujoOT;
    private List<CmtBasicaMgl> listTablaBasicaEstadoInternoOt;
    private List<CmtBasicaMgl> listBasicaTecnologia;//valbuenayf
    private boolean btnActivo = true;
    private boolean cargar = true;
    private String estadoIntInicialSelected;
    private BigDecimal tecnologiaSelected;//valbuenayf
    private String tecnologiaFilter;//valbuenayf
    private String estadoIntInicialList;
    private String tipoOtlist;
    private String tipoOtSelected;
    private String estadoInternoFinalSelected;
    private String estadoInternoFinalList;
    private String aprobado = "false";
    private List<CmtDetalleFlujoMgl> listCmtDetalleFlujoMgl;

    private HashMap<String, Object> params;
    private boolean habilitaObj = false;
    private int actual;
    private String estadoxFlujoId;
    private String estadoInternoObj;
    private String tipoFlujoOtObj;
    private String cambiaCmEstadoObj;
    private String estadoInicialObj;
    private String esEstadoInicial;
    private String proximoEstado;
    private String numPagina;
    private boolean pintarPaginado;
    private List<Integer> paginaList;
    private String pageActual;
    private String detalleFlujoId;
    private SecurityLogin securityLogin;
    private String rolAprobador;
    private HashMap<String, Object> paramsValidacionActualizar;
    private HashMap<String, Object> paramsValidacionGuardar;
    private boolean mostrarPanelListReglaXFlujoOt;
    private boolean mostrarPanelCrearRegla;
    private List<CmtBasicaMgl> listBasicaRazonesNodone;
    private BigDecimal razonSelected;//valbuenayf
    
     //Opciones agregadas para Rol
    private final String NWBTNREFLUJ = "NWBTNREFLUJ";
    private final String IDBTNREFLUJ = "IDBTNREFLUJ";
    private final String DELBTNREFLUJ = "DELBTNREFLUJ";
    

    public DetallexFlujoOtBean() {
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
            cmtDetalleFlujoMgl = new CmtDetalleFlujoMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {

            cmtDetalleFlujoMglFacadeLocal.setUser(usuarioVT, perfilVT);
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_FLUJO_OT);
            listTablaBasicaFlujoOT = cmtBasicaMglFacadeLocal.findByTipoBasica(
                    tipoBasica);

            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
            listTablaBasicaEstadoInternoOt = cmtBasicaMglFacadeLocal.findByTipoBasica(
                    tipoBasica);
            //valbuenayf inicio lista tecnologia
            if (listBasicaTecnologia == null
                    || listBasicaTecnologia.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);
                listBasicaTecnologia = cmtBasicaMglFacadeLocal.findByTipoBasica(
                        tipoBasicaTecnologia);
            }
            //valbuenayf fin lista tecnologia 
            
            //bocanegravm inicio lista razones nodone
              if (listBasicaRazonesNodone == null
                    || listBasicaRazonesNodone.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaRazonesNodone;
                tipoBasicaRazonesNodone = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_RAZONES_NODONE);
                listBasicaRazonesNodone = cmtBasicaMglFacadeLocal.findByTipoBasica(
                        tipoBasicaRazonesNodone);
            }
             //bocanegravm fin lista razones nodone  
            pintarPaginado = true;
            buscar();
            listInfoByPage(1);
            mostrarPanelCrearRegla=false;
            mostrarPanelListReglaXFlujoOt=true;
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (ConstantsCM.PAGINACION_OCHO_FILAS * (page - 1));
            }
            filtroConsultaDetalleFlujoDto = cmtDetalleFlujoMglFacadeLocal.findTablasDetalleFlujo(
                    params, ConstantsCM.PAGINACION_DATOS, firstResult, ConstantsCM.PAGINACION_OCHO_FILAS);
            listCmtDetalleFlujoMgl = filtroConsultaDetalleFlujoDto.getListaCmtEstadoxFlujoMgl();
            habilitaObj = !listCmtDetalleFlujoMgl.isEmpty();
            if (listCmtDetalleFlujoMgl.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            if (listCmtDetalleFlujoMgl.size() == 1) {
                cmtDetalleFlujoMgl = listCmtDetalleFlujoMgl.get(0);
            }
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);
            return "";

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void goActualizar(CmtDetalleFlujoMgl item) {
        try {
          
            cmtDetalleFlujoMgl = item;
            int bdtipoOt = cmtDetalleFlujoMgl.getTipoFlujoOtObj().getBasicaId().intValueExact();
            tipoOtSelected = String.valueOf(bdtipoOt);
            tipoOtlist= tipoOtSelected;
            int bdEstadoInicialObj = cmtDetalleFlujoMgl.getEstadoInicialObj().getBasicaId().intValueExact();
            estadoIntInicialSelected = String.valueOf(bdEstadoInicialObj);
            int bdProximoEstado = cmtDetalleFlujoMgl.getProximoEstado().getBasicaId().intValueExact();
            estadoInternoFinalSelected = String.valueOf(bdProximoEstado);
            aprobado = cmtDetalleFlujoMgl.getAprobacion();
            //valbuenayf inicio ajuste campo tecnologia
            if (cmtDetalleFlujoMgl.getBasicaTecnologia() != null) {
                tecnologiaSelected = cmtDetalleFlujoMgl.getBasicaTecnologia().getBasicaId();
            } else {
                tecnologiaSelected = null;
            }
             if (cmtDetalleFlujoMgl.getBasicaRazonNodone() != null) {
                razonSelected = cmtDetalleFlujoMgl.getBasicaRazonNodone().getBasicaId();
            } else {
                razonSelected = null;
            }
            rolAprobador = cmtDetalleFlujoMgl.getRolAprobador();
            //valbuenayf fin ajuste campo tecnologia
            change();
            mostrarPanelCrearRegla=true;
            mostrarPanelListReglaXFlujoOt=false;            
       
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void change()  {
        try {
            cargar = false;
            btnActivo = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DetallexFlujoOtBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarCampos() {
        tipoOtlist = "";
        tipoOtSelected = "";
        estadoIntInicialList = "";
        estadoIntInicialSelected = "";
        estadoInternoFinalList = "";
        estadoInternoFinalSelected = "";
        aprobado = "false";
        rolAprobador = "";
        tecnologiaSelected = null;//valbuenayf ajuste campo tecnologia
        razonSelected = null;
        this.tipoFlujoOtObj = null;//valbuenayf ajuste filtros
        this.tecnologiaFilter = null;//valbuenayf ajuste filtros
        this.cmtDetalleFlujoMgl.setDetalleFlujoId(null);//valbuenayf ajuste filtros
        findByFiltro();
    }

    
        public void guardarEstadoxFlujo() {
        if (!validarFlujoGuardar()) {
            String msn = "Existen campos obligatorios sin completar ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        }
        if (validarConfigDuplicadaGuardar()) {
             String msn = "La configuracion que esta ingresando  ya existe ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
             return;
        } else{
              crearReglaFlujo();
        }
    }
  
    public void actualizaEstadoxFlujo() {
        if (!validarFlujoAct()) {
            String msn = "Existen campos obligatorios sin completar ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        }
        if (validarConfigDuplicadaActualizar()) {
            String msn = "La configuracion que esta ingresando ya existe ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return;
        } else {
            actualizarReglaxFlujoMgl();
        }

    }

    
        public boolean validarConfigDuplicadaGuardar() {
        boolean duplicado = false;
        try {
            List<CmtDetalleFlujoMgl> cmtCmtDetalleFlujoMglList;
            camposValidarGuardar();
            cmtCmtDetalleFlujoMglList = cmtDetalleFlujoMglFacadeLocal.findByAllFields(paramsValidacionGuardar);
            if (!cmtCmtDetalleFlujoMglList.isEmpty()) {
                duplicado = true;
            }
            
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return duplicado;
    }
        
       public boolean validarConfigDuplicadaActualizar() {
        boolean duplicado = false;
        try {
            List<CmtDetalleFlujoMgl> cmtCmtDetalleFlujoMglList;
            camposValidarActualizar();
            cmtCmtDetalleFlujoMglList = cmtDetalleFlujoMglFacadeLocal.findByAllFields(paramsValidacionActualizar);
            if (!cmtCmtDetalleFlujoMglList.isEmpty()) {
                duplicado = true;
            }
            
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return duplicado;
    }
    
        
    public boolean validarFlujoAct() {
        boolean validos = true;
        if (tipoOtSelected == null || estadoIntInicialSelected == null || estadoInternoFinalSelected == null || tecnologiaSelected == null
                || (aprobado.equalsIgnoreCase("Y") && rolAprobador.isEmpty() || rolAprobador == null)) {
            validos = false;
        }
        return validos;
    }
    
    public void camposValidarActualizar() {
        try {
            paramsValidacionActualizar = new HashMap<>();
            paramsValidacionActualizar.put("all", "1");
            paramsValidacionActualizar.put("tipoOtlist", this.tipoOtlist);
            paramsValidacionActualizar.put("aprobado", this.aprobado);
            paramsValidacionActualizar.put("estadoIntInicialList", this.estadoIntInicialSelected);
            paramsValidacionActualizar.put("estadoInternoFinalList", this.estadoInternoFinalSelected);
            paramsValidacionActualizar.put("tecnologiaSelected", this.tecnologiaSelected);
            paramsValidacionActualizar.put("rolAprobador", this.rolAprobador);
            paramsValidacionActualizar.put("razonSelected", this.razonSelected);
           
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }
    
    
    public boolean validarFlujoGuardar() {
         boolean validos = true;
        if (tipoOtlist == null || estadoIntInicialList == null || estadoInternoFinalList == null || tecnologiaSelected == null
           ||  (aprobado.equalsIgnoreCase("Y") && rolAprobador.isEmpty() || rolAprobador == null )) {
            validos = false;
        }
           return validos;  
        }
    
       
    
       public void camposValidarGuardar() {
        try {
            paramsValidacionGuardar = new HashMap<>();
            paramsValidacionGuardar.put("all", "1");
            paramsValidacionGuardar.put("tipoOtlist", this.tipoOtlist);
            paramsValidacionGuardar.put("aprobado", this.aprobado);
            paramsValidacionGuardar.put("estadoIntInicialList", this.estadoIntInicialList);
            paramsValidacionGuardar.put("estadoInternoFinalList", this.estadoInternoFinalList);
            paramsValidacionGuardar.put("tecnologiaSelected", this.tecnologiaSelected);
            paramsValidacionGuardar.put("rolAprobador", this.rolAprobador);
            paramsValidacionGuardar.put("razonSelected", this.razonSelected);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }


    public void crearReglaFlujo() {
        try {
            if (tipoOtlist == null || estadoIntInicialList == null || estadoInternoFinalList == null || tecnologiaSelected == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (aprobado.equalsIgnoreCase("Y") && rolAprobador.equalsIgnoreCase("") || rolAprobador == null) {
                String msn = "Debe ingresar un rol aprobador";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtDetalleFlujoMgl = new CmtDetalleFlujoMgl();
            CmtBasicaMgl basicatipoOt = new CmtBasicaMgl();
            basicatipoOt.setBasicaId(new BigDecimal(tipoOtlist));
            cmtDetalleFlujoMgl.setTipoFlujoOtObj(basicatipoOt);

            CmtBasicaMgl basicaEstadoInterno = new CmtBasicaMgl();
            basicaEstadoInterno.setBasicaId(new BigDecimal(estadoIntInicialList));
            cmtDetalleFlujoMgl.setEstadoInicialObj(basicaEstadoInterno);

            CmtBasicaMgl basicaEstadoCm = new CmtBasicaMgl();
            basicaEstadoCm.setBasicaId(new BigDecimal(estadoInternoFinalList));
            cmtDetalleFlujoMgl.setProximoEstado(basicaEstadoCm);

            cmtDetalleFlujoMgl.setAprobacion(aprobado);
            cmtDetalleFlujoMgl.setEstadoRegistro(1);
            cmtDetalleFlujoMgl.setDetalle("EDICION");
            cmtDetalleFlujoMgl.setUsuarioCreacion(usuarioVT);
            cmtDetalleFlujoMgl.setFechaCreacion(new Date());
            cmtDetalleFlujoMgl.setPerfilCreacion(perfilVT);
            //valbuenayf inicio ajuste campo tecnologia
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            cmtDetalleFlujoMgl.setBasicaTecnologia(tecnologia);
            //valbuenayf fin ajuste campo tecnologia
            cmtDetalleFlujoMgl.setRolAprobador(rolAprobador);
            
            if (razonSelected != null) {
                CmtBasicaMgl razonNodone = new CmtBasicaMgl();
                razonNodone.setBasicaId(razonSelected);
                cmtDetalleFlujoMgl.setBasicaRazonNodone(razonNodone);
            }

            cmtDetalleFlujoMgl = cmtDetalleFlujoMglFacadeLocal.create(cmtDetalleFlujoMgl);
            if (cmtDetalleFlujoMgl != null) {
                listInfoByPage(1);
                String msn = "Proceso exitoso: se ha creado la regla de flujo <b>"
                        + cmtDetalleFlujoMgl.getDetalleFlujoId() + "</b>  "
                        + "satisfactoriamente";
                limpiarCampos();
                mostrarPanelCrearRegla=false;
                mostrarPanelListReglaXFlujoOt=true;
            
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException ex) {
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }

    public void actualizarReglaxFlujoMgl() {
        try {

            CmtBasicaMgl basicatipoOt = new CmtBasicaMgl();
            basicatipoOt.setBasicaId(new BigDecimal(tipoOtSelected));
            cmtDetalleFlujoMgl.setTipoFlujoOtObj(basicatipoOt);

            CmtBasicaMgl basicaEstadoInterno = new CmtBasicaMgl();
            basicaEstadoInterno.setBasicaId(new BigDecimal(estadoIntInicialSelected));
            cmtDetalleFlujoMgl.setEstadoInicialObj(basicaEstadoInterno);

            CmtBasicaMgl basicaEstadoCm = new CmtBasicaMgl();
            basicaEstadoCm.setBasicaId(new BigDecimal(estadoInternoFinalSelected));
            cmtDetalleFlujoMgl.setProximoEstado(basicaEstadoCm);

            cmtDetalleFlujoMgl.setAprobacion(aprobado);

            cmtDetalleFlujoMgl.setUsuarioCreacion(cmtDetalleFlujoMgl.getUsuarioCreacion());
            cmtDetalleFlujoMgl.setFechaCreacion(cmtDetalleFlujoMgl.getFechaCreacion());
            cmtDetalleFlujoMgl.setFechaEdicion(new Date());
            cmtDetalleFlujoMgl.setUsuarioEdicion(usuarioVT);
            cmtDetalleFlujoMgl.setPerfilEdicion(perfilVT);
            //valbuenayf ajuste campo tecnologia
            CmtBasicaMgl tecnologia = new CmtBasicaMgl();
            tecnologia.setBasicaId(tecnologiaSelected);
            cmtDetalleFlujoMgl.setBasicaTecnologia(tecnologia);
            
            cmtDetalleFlujoMgl.setRolAprobador(rolAprobador);
            
            if (razonSelected != null) {
                CmtBasicaMgl razonNodone = new CmtBasicaMgl();
                razonNodone.setBasicaId(razonSelected);
                cmtDetalleFlujoMgl.setBasicaRazonNodone(razonNodone);
            }else{
                cmtDetalleFlujoMgl.setBasicaRazonNodone(null);
            }

            CmtDetalleFlujoMgl cmtDetalle;
            cmtDetalle = cmtDetalleFlujoMglFacadeLocal.update(cmtDetalleFlujoMgl);
            if (cmtDetalle != null) {
                listInfoByPage(1);
                nuevoRegistro();//valbuenayf ajuste boton nuevo
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                mostrarPanelCrearRegla=false;
                mostrarPanelListReglaXFlujoOt=true;
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }

    public void eliminarReglaFlujoMgl(CmtDetalleFlujoMgl cmtDetalleFlujoMgl) {
        try {
            if (cmtDetalleFlujoMgl.getDetalleFlujoId() == null) {
                String msn = "Seleccione un registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtDetalleFlujoMglFacadeLocal.delete(cmtDetalleFlujoMgl);
            listInfoByPage(1);
            limpiarCampos();
            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            mostrarPanelCrearRegla=false;
            mostrarPanelListReglaXFlujoOt=true;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            String msn = "Proceso falló : " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
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
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a página. EX000 " + ex.getMessage(), ex);
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
                FiltroConsultaDetalleFlujoDto filtro
                        = cmtDetalleFlujoMglFacadeLocal.findTablasDetalleFlujo(params, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtro.getNumRegistros();
                int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                        ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                        : count / ConstantsCM.PAGINACION_OCHO_FILAS);
                return totalPaginas;
            } catch (ApplicationException ex) {
                LOGGER.error("Error al traer el total de páginas. EX000 " + ex.getMessage(), ex);
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
            params.put("detalleFlujoId", this.detalleFlujoId);
            params.put("tipoFlujoOtObj", this.tipoFlujoOtObj);
            params.put("estadoInicialObj", this.estadoInicialObj);
            params.put("proximoEstado", this.proximoEstado);
            params.put("aprobacion", this.aprobado);
            params.put("estadoRegistro", this.esEstadoInicial);
            params.put("tecnologia", this.tecnologiaFilter);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }

    public void accionAprobado() {

        if (!aprobado.equals("Y")) {
            rolAprobador = "";
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
        }
    }

    /**
     * valbuenayf metodo que limpia la informacion para un Reglas de Flujos OT
     *
     */
    public void nuevoRegistro() {
        try {
            limpiarCampos();
            cargar = true;
            btnActivo = true;
        } catch (Exception e) {
            LOGGER.error("Error en nuevoRegistro " + e.getMessage(),e);
        }
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVT() {
        return perfilVT;
    }

    public void setPerfilVT(int perfilVT) {
        this.perfilVT = perfilVT;
    }

    public CmtDetalleFlujoMgl getCmtDetalleFlujoMgl() {
        return cmtDetalleFlujoMgl;
    }

    public void setCmtDetalleFlujoMgl(CmtDetalleFlujoMgl cmtDetalleFlujoMgl) {
        this.cmtDetalleFlujoMgl = cmtDetalleFlujoMgl;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public String getTipoOtSelected() {
        return tipoOtSelected;
    }

    public void setTipoOtSelected(String tipoOtSelected) {
        this.tipoOtSelected = tipoOtSelected;
    }

    public String getTipoOtlist() {
        return tipoOtlist;
    }

    public void setTipoOtlist(String tipoOtlist) {
        this.tipoOtlist = tipoOtlist;
    }

    public String getEstadoIntInicialSelected() {
        return estadoIntInicialSelected;
    }

    public void setEstadoIntInicialSelected(String estadoIntInicialSelected) {
        this.estadoIntInicialSelected = estadoIntInicialSelected;
    }

    public String getEstadoIntInicialList() {
        return estadoIntInicialList;
    }

    public void setEstadoIntInicialList(String estadoIntInicialList) {
        this.estadoIntInicialList = estadoIntInicialList;
    }

    public String getEstadoInternoFinalSelected() {
        return estadoInternoFinalSelected;
    }

    public void setEstadoInternoFinalSelected(String estadoInternoFinalSelected) {
        this.estadoInternoFinalSelected = estadoInternoFinalSelected;
    }

    public String getEstadoInternoFinalList() {
        return estadoInternoFinalList;
    }

    public void setEstadoInternoFinalList(String estadoInternoFinalList) {
        this.estadoInternoFinalList = estadoInternoFinalList;
    }

    public String getAprobado() {
        if (aprobado.equals("Y")) {
            aprobado = "true";
        } else {
            aprobado = "false";
        }
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        if (aprobado.equals("true")) {
            aprobado = "Y";
        } else {
            aprobado = "N";
        }
        this.aprobado = aprobado;
    }

    public List<CmtDetalleFlujoMgl> getListCmtDetalleFlujoMgl() {
        return listCmtDetalleFlujoMgl;
    }

    public void setListCmtDetalleFlujoMgl(List<CmtDetalleFlujoMgl> listCmtDetalleFlujoMgl) {
        this.listCmtDetalleFlujoMgl = listCmtDetalleFlujoMgl;
    }

    public FiltroConsultaDetalleFlujoDto getFiltroConsultaDetalleFlujoDto() {
        return filtroConsultaDetalleFlujoDto;
    }

    public void setFiltroConsultaDetalleFlujoDto(FiltroConsultaDetalleFlujoDto filtroConsultaDetalleFlujoDto) {
        this.filtroConsultaDetalleFlujoDto = filtroConsultaDetalleFlujoDto;
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

    public String getEsEstadoInicial() {
        return esEstadoInicial;
    }

    public void setEsEstadoInicial(String esEstadoInicial) {
        this.esEstadoInicial = esEstadoInicial;
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

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public String getEstadoInicialObj() {
        return estadoInicialObj;
    }

    public void setEstadoInicialObj(String estadoInicialObj) {
        this.estadoInicialObj = estadoInicialObj;
    }

    public String getProximoEstado() {
        return proximoEstado;
    }

    public void setProximoEstado(String proximoEstado) {
        this.proximoEstado = proximoEstado;
    }

    public String getDetalleFlujoId() {
        return detalleFlujoId;
    }

    public void setDetalleFlujoId(String detalleFlujoId) {
        this.detalleFlujoId = detalleFlujoId;
    }

    public BigDecimal getTecnologiaSelected() {
        return tecnologiaSelected;
    }

    public void setTecnologiaSelected(BigDecimal tecnologiaSelected) {
        this.tecnologiaSelected = tecnologiaSelected;
    }

    public List<CmtBasicaMgl> getListBasicaTecnologia() {
        return listBasicaTecnologia;
    }

    public void setListBasicaTecnologia(List<CmtBasicaMgl> listBasicaTecnologia) {
        this.listBasicaTecnologia = listBasicaTecnologia;
    }

    public String getTecnologiaFilter() {
        return tecnologiaFilter;
    }

    public void setTecnologiaFilter(String tecnologiaFilter) {
        this.tecnologiaFilter = tecnologiaFilter;
    }

    public String getRolAprobador() {
        return rolAprobador;
    }

    public void setRolAprobador(String rolAprobador) {
        this.rolAprobador = rolAprobador;
    }

    public HashMap<String, Object> getParamsValidacionActualizar() {
        return paramsValidacionActualizar;
    }

    public void setParamsValidacionActualizar(HashMap<String, Object> paramsValidacionActualizar) {
        this.paramsValidacionActualizar = paramsValidacionActualizar;
    }

    public HashMap<String, Object> getParamsValidacionGuardar() {
        return paramsValidacionGuardar;
    }

    public void setParamsValidacionGuardar(HashMap<String, Object> paramsValidacionGuardar) {
        this.paramsValidacionGuardar = paramsValidacionGuardar;
    }

    public boolean isMostrarPanelListReglaXFlujoOt() {
        return mostrarPanelListReglaXFlujoOt;
    }

    public void setMostrarPanelListReglaXFlujoOt(boolean mostrarPanelListReglaXFlujoOt) {
        this.mostrarPanelListReglaXFlujoOt = mostrarPanelListReglaXFlujoOt;
    }

    public boolean isMostrarPanelCrearRegla() {
        return mostrarPanelCrearRegla;
    }

    public void setMostrarPanelCrearRegla(boolean mostrarPanelCrearRegla) {
        this.mostrarPanelCrearRegla = mostrarPanelCrearRegla;
    }

    public List<CmtBasicaMgl> getListBasicaRazonesNodone() {
        return listBasicaRazonesNodone;
    }

    public void setListBasicaRazonesNodone(List<CmtBasicaMgl> listBasicaRazonesNodone) {
        this.listBasicaRazonesNodone = listBasicaRazonesNodone;
    }

    public BigDecimal getRazonSelected() {
        return razonSelected;
    }

    public void setRazonSelected(BigDecimal razonSelected) {
        this.razonSelected = razonSelected;
    }

    public void crearNuevaRegla() {

        mostrarPanelCrearRegla = true;
        mostrarPanelListReglaXFlujoOt = false;
    }

    public void volver() {

        mostrarPanelCrearRegla = false;
        mostrarPanelListReglaXFlujoOt = true;
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(NWBTNREFLUJ);
    }
    
    public boolean validarOpcionLinkIdDetalle() {
        return validarEdicionRol(IDBTNREFLUJ);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(DELBTNREFLUJ);
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
