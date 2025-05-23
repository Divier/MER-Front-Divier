/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "parametrosMglBean")
@ViewScoped
public class ParametrosMglBean {

    private static final Logger LOGGER = LogManager.getLogger(ParametrosMglBean.class);
    private static final String SISTEMA = "SISTEMA";
    private static final String USUARIOS = "USUARIOS";
    private static final String ID_PARAMETROS_MGL = "idParametrosMgl";
    private static final String PARAMETROS_MGL_VIEW = "parametrosMglView";
    private static final String PARAMETROS_MGL_LIST_VIEW = "parametrosMglListView";
    private static final String ATR_MESSAGE = "message";
    private static final String PROCESO_FALLO = "Proceso falló";
    @Setter
    @Getter
    private ParametrosMgl parametrosMgl = null;
    @Setter
    @Getter
    private List<ParametrosMgl> parametrosMglList;
    @Getter
    @Setter
    private String idSqlSelected;
    @Setter
    @Getter
    private String acronimo;
    @Setter
    @Getter
    private HtmlDataTable dataTable = new HtmlDataTable();
    @Setter
    private String pageActual;
    @Getter
    @Setter
    private String numPagina = "1";
    @Setter
    @Getter
    private List<Integer> paginaList;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
    private String estiloObligatorio = "<font color='red'>*</font>";
    private SecurityLogin securityLogin;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final String FORMULARIO = "ADMIN_PARAMETROS";
    private static final String OPCION_USUARIO = "menuCmAdminParametrosUser";
    private static final String OPCION_SISTEMA = "menuCmAdminParametrosSystem";
    private boolean permisosParamUser;
    private boolean permisosParamSystem;
    @Setter
    @Getter
    private boolean controlUpdate;
    //Opciones agregadas para Rol
    private static final String NEWBTNGST = "NEWBTNGST";
    //Opciones agregadas para Rol
    private static final String FLTBTNGST = "FLTBTNGST";
    //Opciones agregadas para Rol
    private static final String VRTDSBTGS = "VRTDSBTGS";
    private static final String ROLOPCVERGESTPAR = "ROLOPCVERGESTPAR";
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private ParametroFacadeLocal parametroFacade;

    /**
     * Creates a new instance of ParametrosMglBean
     */
    public ParametrosMglBean() throws IOException {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }

            ParametrosMgl idNodoMgl = (ParametrosMgl) session.getAttribute(ID_PARAMETROS_MGL);
            session.removeAttribute(ID_PARAMETROS_MGL);
            if (idNodoMgl != null) {
                parametrosMgl = idNodoMgl;
                irParametrosMgl();
                controlUpdate=true;
            } else {
                parametrosMgl = new ParametrosMgl();
                controlUpdate=false;
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en ParametrosMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    private void fillSqlList() {
        try {
            parametrosMglList = new ArrayList<>();
            permisosParamUser = validarPermisos(OPCION_USUARIO);
            permisosParamSystem = validarPermisos(OPCION_SISTEMA);

            if (permisosParamUser && permisosParamSystem) {
                parametrosMglList = parametroFacade.findAllParameters();
                return;
            }

            if (permisosParamUser) {
                parametrosMglList = parametroFacade.findParametersByType(USUARIOS);
                return;
            }

            if (permisosParamSystem) {
                parametrosMglList = parametroFacade.findParametersByType(SISTEMA);
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en fillSqlList. "+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en fillSqlList. "+ e.getMessage(), e, LOGGER);
        }
    }

    public String goActualizar() {
        try {
            parametrosMgl = (ParametrosMgl) dataTable.getRowData();
            session.setAttribute(ID_PARAMETROS_MGL, parametrosMgl);
            return PARAMETROS_MGL_VIEW;
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return PARAMETROS_MGL_LIST_VIEW;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return PARAMETROS_MGL_LIST_VIEW;
            }
        }
        return null;
    }

    public String irParametrosMgl() {
        try {
            return PARAMETROS_MGL_VIEW;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irParametrosMgl" + e.getMessage() , e, LOGGER);
        }
        return "";
    }

    public String nuevoParametrosMgl() {
        parametrosMgl = new ParametrosMgl();
        return PARAMETROS_MGL_VIEW;
    }

    public void crearlParametrosMgl() {
        try {
            if (parametrosMgl.getParAcronimo() == null || parametrosMgl.getParAcronimo().isEmpty()) {
                message = "Campo Acrónimo es requerido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                return;
            }
            if (parametrosMgl.getParValor() == null || parametrosMgl.getParValor().isEmpty()) {
                message = "Campo Valor es requerido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                return;
            }
            if (parametrosMgl.getParTipoParametro() == null || parametrosMgl.getParTipoParametro().isEmpty()) {
                message = "Campo Tipo Parametro es requerido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                return;
            }

            parametrosMgl.setParAcronimo(parametrosMgl.getParAcronimo().toUpperCase());
            ParametrosMgl parCreado = parametroFacade.findParameterByAcronym(parametrosMgl.getParAcronimo()).orElse(null);
            if (parCreado != null && parCreado.getParAcronimo() != null) {
                message = "Error: Ya existe un parametro con el acronimo: "+parametrosMgl.getParAcronimo();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
            } else {
                parametroFacade.createParameter(parametrosMgl);
                message = "Proceso exitoso: se ha creado el parametro satisfactoriamente";
                controlUpdate = true;
                session.setAttribute(ATR_MESSAGE, message);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en crearlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        }
    }

    public void actualizarlParametrosMgl() {
        try {
            if (StringUtils.isBlank(parametrosMgl.getParValor())) {
                message = "Campo Valor es requerido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                return;
            }

            if (StringUtils.isBlank(parametrosMgl.getParTipoParametro())) {
                message = "Campo Tipo Parametro es requerido";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                return;
            }

            parametrosMgl.setParAcronimo(parametrosMgl.getParAcronimo().toUpperCase());
            parametroFacade.updateParameter(parametrosMgl);
            message = "Proceso exitoso: se ha modificado el parametro satisfatoriamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en actualizarlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        }
    }

    public String eliminarlParametrosMgl() {
        try {
            parametroFacade.deleteParameter(parametrosMgl);
            message = "Proceso exitoso: se ha eliminado el registro del sistema";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en eliminarlParametrosMgl. ", e, LOGGER);
            message = PROCESO_FALLO;
            session.setAttribute(ATR_MESSAGE, message);
        }
         return PARAMETROS_MGL_LIST_VIEW;
    }

    public void buscarParametroByAcronimo() {
        if (StringUtils.isBlank(acronimo)) {
            FacesUtil.mostrarMensajePopUp(MessageSeverityEnum.WARN, "No ha ingresado un acrónimo para la búsqueda");
            return;
        }

        try {
            acronimo = acronimo.toUpperCase();
            permisosParamUser = validarPermisos(OPCION_USUARIO);
            permisosParamSystem = validarPermisos(OPCION_SISTEMA);

            if (permisosParamUser && permisosParamSystem) {
                parametrosMglList = parametroFacade.findParametersByAcronymLike(acronimo);
                return;
            }

            if (permisosParamUser) {
                parametrosMglList = parametroFacade.findParametersByAcronymAndType(acronimo, USUARIOS);
                return;
            }

            if (permisosParamSystem) {
                parametrosMglList = parametroFacade.findParametersByAcronymAndType(acronimo, SISTEMA);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarlParametrosMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en eliminarlParametrosMgl. ", e, LOGGER);
        }
        pageFirst();
    }

    public void buscarParametroTodos() throws ApplicationException {
        permisosParamUser = validarPermisos(OPCION_USUARIO);
        permisosParamSystem = validarPermisos(OPCION_SISTEMA);
        if (permisosParamUser && permisosParamSystem) {
            parametrosMglList = parametroFacade.findAllParameters();
        } else if (permisosParamUser) {
            parametrosMglList = parametroFacade.findParametersByType(USUARIOS);
        } else if (permisosParamSystem) {
            parametrosMglList = parametroFacade.findParametersByType(SISTEMA);
        }
    }

    // Actions Paging--------------------------------------------------------------------------
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
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public String getPageActual() {
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
        return pageActual;
    }

    public void irPagina(ValueChangeEvent event) {
        String value = event.getNewValue() != null ? event.getNewValue().toString() : "1";
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
    }
    
    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacionRol(FORMULARIO, accion, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar permisos. EX000 {}" ,e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }

    // Validar Opciones por Rol
    public boolean validarOpcionNuevoLista() {
        return validarEdicionRol(NEWBTNGST);
    }
    
      // Validar Opciones por Rol    
    public boolean validarOpcionFilltrar() {
        return validarEdicionRol(FLTBTNGST);
    }
    
      // Validar Opciones por Rol    
    public boolean validarOpcionVerTodas() {
        return validarEdicionRol(VRTDSBTGS);
    }
    
      // Validar Opciones por Rol    
    public boolean validarOpcionVerRol() {
        return validarEdicionRol(ROLOPCVERGESTPAR);
    }
    
    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario,
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
}
