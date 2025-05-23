/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalRRFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ortizjaf
 */
@ManagedBean(name = "solicitudGestionarMglBean")
@ViewScoped
public class SolicitudGestionarMglBean implements Serializable {


    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacade;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal tipoSolicitudMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal solicitudCmMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudGestionarMglBean.class);
    private final String FORMULARIO_SOLICITUD = "SOLICITUDESGESTIONAR";
    private final String FORMULARIO_CM = "SOLICITUDESGESTIONARCM";
    private final String FORMULARIO_DESBLOQUEAR = "SOLICITUDESGESTIONARDESBLOQUEAR";
    //Opciones agregadas para Rol
    private final String FLTBTGSTL = "FLTBTGSTL";
    private final String SRHBTGSTL = "SRHBTGSTL";
    private final String VWBTNGSTL = "VWBTNGSTL";  
      
    private List<CmtBasicaMgl> tipoSegmentoList;
    private List<CmtTipoSolicitudMgl> tipoSolicitudList;
    private List<CmtSolicitudCmMgl> solicitudList;
    private String departamentoSelected;
    private String ciudadSelected;
    private String idSolicitudToFind;
    private CmtTipoSolicitudMgl tipoSolicitudSelected;
    private CmtBasicaMgl tipoSegmentoSelected;
    private boolean showFiltro;
    private int totalSolicitudesDia;
    private int totalSolicitudesVencidasDia;
    private int totalSolicitudesPorVencerDia;
    private int totalSolicitudesGestionadasDia;
    private boolean showFooterTable;
    private boolean consultaLlamadas;
    private int totalSolicitudesllamadas;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;

    private SecurityLogin securityLogin;
    private Boolean validarGestionSolicitud;
    private Boolean validarGestionCM;
    private Boolean validarGestionDesbloqueo;
    private String serverHost;
    
    private List<CmtRegionalRr> lstCmtRegionalRrs;
    private List<GeograficoPoliticoMgl> lstDeptosGeograficoPoriticoMgl;
    private List<GeograficoPoliticoMgl> lstCiudadesList;  
    @EJB
    private CmtRegionalRRFacadeLocal cmtRegionalRRFacadeLocal;
    
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacade;
    private boolean ordenMayorMenor;
    
  
    public SolicitudGestionarMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException ex) {
            LOGGER.error("Se generea error en SolicitudGestionarMglBean class ...", ex);
        } catch (Exception exusu){
            LOGGER.error("Error inicializando managebean SolicitudGestionarMglBean ", exusu);
        }
    }

    @PostConstruct
    private void init() {
        try {

            tipoSolicitudSelected = new CmtTipoSolicitudMgl();
            lstCmtRegionalRrs = cmtRegionalRRFacadeLocal.findAllRegional();
            solicitudCmMglFacadeLocal.setUser(securityLogin.getLoginUser(), securityLogin.getPerfilUsuario());
            lstDeptosGeograficoPoriticoMgl = geograficoPoliticoMglFacade.findDptos();
            tipoSegmentoSelected = new CmtBasicaMgl();
            serverHost = Utilidades.queryParametrosConfig(co.com.telmex.catastro.services.util.Constant.HOST_REDIR_VT);
            CmtTipoBasicaMgl tipoBasicaSegmento;
            tipoBasicaSegmento= cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_DE_SEGMENTO);

            tipoSegmentoList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaSegmento);
            tipoSolicitudList = tipoSolicitudMglFacadeLocal.getByUsuarioRol(facesContext);

            totalSolicitudesDia = solicitudCmMglFacadeLocal.getCountSolicitudCreateDay(tipoSolicitudList);
            totalSolicitudesPorVencerDia = solicitudCmMglFacadeLocal.
                    getCountAllSolicitudPorVencerDay(tipoSolicitudList);
            totalSolicitudesVencidasDia = solicitudCmMglFacadeLocal.
                    getCountAllSolicitudVencidasDay(tipoSolicitudList);
            totalSolicitudesGestionadasDia = solicitudCmMglFacadeLocal.
                    getCountSolicitudGestionadaDay(tipoSolicitudList);
            showFiltro = false;
            showFooterTable = true;
            consultaLlamadas = false;
            ordenMayorMenor = false;
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudGestionarMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudGestionarMglBean. " + e.getMessage(), e, LOGGER);
        }

    }

    private void cargarFiltrosCookie() {
        try {
            //cargamos el filtro para comunidad
            Cookie cookieDivision = getCookie(Constant.COOKIE_NAME_GEST_SOL_CM_DIVISION);
            if (cookieDivision != null
                    && cookieDivision.getValue() != null
                    && !cookieDivision.getValue().trim().isEmpty()) {
                if (cookieDivision.getValue().trim().equalsIgnoreCase("null")) {
                    departamentoSelected = null;
                } else {
                    departamentoSelected = cookieDivision.getValue();
                }

            }
            if (departamentoSelected != null
                    && !departamentoSelected.trim().isEmpty()) {
                //cargamos el filtro para comunidad
                Cookie cookieComunidad = getCookie(Constant.COOKIE_NAME_GEST_SOL_CM_COMUNIDAD);
                String comunidadCookieStr = "";
                if (cookieComunidad != null
                        && cookieComunidad.getValue() != null
                        && !cookieComunidad.getValue().trim().isEmpty()) {

                    if (cookieComunidad.getValue().trim().equalsIgnoreCase("null")) {
                        comunidadCookieStr = null;
                    } else {
                        comunidadCookieStr = cookieComunidad.getValue();
                    }
                }
                obtenerListaCiudades();
                ciudadSelected = comunidadCookieStr;

            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:cargarFiltrosCookie(). " + e.getMessage(), e, LOGGER);
        }
    }

    public Cookie getCookie(String name) {
        Cookie cookie = null;
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Cookie[] userCookies = request.getCookies();

            if (userCookies != null && userCookies.length > 0) {
                for (int i = 0; i < userCookies.length; i++) {
                    if (userCookies[i].getName().equals(name)) {
                        cookie = userCookies[i];
                        break;
                    }
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:getCookie. " + e.getMessage(), e, LOGGER);
        }

        return cookie;
    }

    private void setCookie(String name, String value) {
        try {

            HttpServletRequest request
                    = (HttpServletRequest) FacesContext.getCurrentInstance().
                            getExternalContext().getRequest();
            HttpServletResponse resp
                    = (HttpServletResponse) FacesContext.getCurrentInstance().
                            getExternalContext().getResponse();
            Cookie cookie = null;

            Cookie[] userCookies = request.getCookies();
            if (userCookies != null && userCookies.length > 0) {
                for (int i = 0; i < userCookies.length; i++) {
                    if (userCookies[i].getName().equals(name)) {
                        cookie = (Cookie) userCookies[i].clone();
                        userCookies[i].setValue(value);
                        userCookies[i].setMaxAge(0);
                    }
                }
            }
            if (cookie != null) {
                cookie.setValue(value);
                cookie.setMaxAge(60 * 60 * 24 * 7);
            } else {
                cookie = new Cookie(name, value);

                cookie.setMaxAge(60 * 60 * 24 * 7);
            }
            cookie.setPath("/");
            resp.addCookie(cookie);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:setCookie. " + e.getMessage(), e, LOGGER);
        }
    }

    public String obtenerListaCiudades()  {
        ciudadSelected = null;
        try {
            if (departamentoSelected != null && !departamentoSelected.trim().isEmpty()) {

                lstCiudadesList = geograficoPoliticoMglFacade.findCiudades(new BigDecimal(departamentoSelected));

            } else {
                lstCiudadesList = null;
            }
            filtrarInfo();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SolicitudGestionarMglBean:obtenerListaCiudades()  . " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String goCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        try {
            if (cuentaMatriz != null) {
                CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
                cuentaMatrizBean.setCuentaMatriz(cuentaMatriz);
                ConsultaCuentasMatricesBean consultaCuentasMatricesBean = (ConsultaCuentasMatricesBean) JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
                consultaCuentasMatricesBean.goCuentaMatrizEstadosSol(cuentaMatrizBean);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:goCuentaMatriz. " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String showFiltro() {
        showFiltro = !showFiltro;
        return null;
    }

    public String verTodas() {
        showFooterTable = true;
        consultaLlamadas = false;
        idSolicitudToFind = "";
        listInfoByPage(1);
        return null;
    }

    public String verSolicitudesLlamadas() {
        showFooterTable = true;
        consultaLlamadas = true;
        idSolicitudToFind = "";
        listInfoByPage(1);
        return null;
    }

    public String findById() {
        try {
            if (idSolicitudToFind != null && !idSolicitudToFind.trim().isEmpty() && idSolicitudToFind.trim().matches("^[0-9]*")) {
                BigDecimal idSol = new BigDecimal(idSolicitudToFind.trim());
                CmtSolicitudCmMgl solicitudFound = retornarSolicitud(idSol); 
                if (solicitudFound != null) {
                    if (solicitudFound.getEstadoSolicitudObj() != null
                            && solicitudFound.getEstadoSolicitudObj().getBasicaId() != null
                            && solicitudFound.getEstadoSolicitudObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE)||
                            solicitudFound.getEstadoSolicitudObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA)||
                            solicitudFound.getEstadoSolicitudObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP)||
                            solicitudFound.getEstadoSolicitudObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO)) 
                    {
                        solicitudList = new ArrayList<CmtSolicitudCmMgl>();
                        solicitudList.add(solicitudFound);
                        showFooterTable = false;
                    } else {
                        String msn = "La Solicitud se encuentra Finalizada";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }
                } else {
                    String msn = "No se encuentra o no tiene permiso para ver la solicitud";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "Debe Ingresar un numero de Solicitud";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:findById(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String desbloquearSolicitud(CmtSolicitudCmMgl solicitud) {
        try {
            if (solicitud != null && solicitud.getSolicitudCmId() != null) {
                solicitudCmMglFacadeLocal.bloquearDesbloquearSolicitud(solicitud, false);
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error desbloqueando la solicitud ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:desbloquearSolicitud. " + e.getMessage(), e, LOGGER);
        }
        return listInfoByPage(1);
    }

    public String irGestion(CmtSolicitudCmMgl solicitudCmMgl) {
        try {
            if (solicitudCmMgl == null
                    || solicitudCmMgl.getSolicitudCmId() == null
                    || solicitudCmMgl.getTipoSolicitudObj() == null
                    || solicitudCmMgl.getTipoSolicitudObj().getTipoSolicitudId() == null) {
                return null;
            }
            EncabezadoSolicitudModificacionCMBean encabezadoBean =
                    (EncabezadoSolicitudModificacionCMBean) JSFUtil.getSessionBean(EncabezadoSolicitudModificacionCMBean.class);
            CmtSolicitudCmMgl solicitudModCM = solicitudCmMglFacadeLocal.findById(solicitudCmMgl.getSolicitudCmId());
            boolean disponible = solicitudModCM.getDisponibilidadGestion() != null
                    && solicitudModCM.getDisponibilidadGestion().equalsIgnoreCase("1");

            if (disponible) {
                return encabezadoBean.ingresarGestionSolicitud(solicitudCmMgl);
            } else {
                if (solicitudModCM.getDisponibilidadGestion() == null) {
                    solicitudModCM.setDisponibilidadGestion("0");
                    solicitudModCM = solicitudCmMglFacadeLocal.update(solicitudModCM);
                }
                String msnErr = "La solicitud " + solicitudModCM.getSolicitudCmId() + " se encuentra en proceso de Gestion.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msnErr, ""));
            }
        } catch (ApplicationException e) {
            String msnErr = "Ha ocurrido un error redirigiendo a la Gestion.";
            FacesUtil.mostrarMensajeError(msnErr + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:irGestion. " + e.getMessage(), e, LOGGER);
        }
        return listInfoByPage(1);
    }

    public String filtrarInfo() {
        listInfoByPage(1);
        return null;
    }

    private List<CmtTipoSolicitudMgl> getListTipoFiltro() {
        List<CmtTipoSolicitudMgl> tipoSolicitudListFiltro;
        if (tipoSolicitudSelected != null
                && tipoSolicitudSelected.getTipoSolicitudId() != null) {
            tipoSolicitudListFiltro = new ArrayList<CmtTipoSolicitudMgl>();
            tipoSolicitudListFiltro.add(tipoSolicitudSelected);
        } else {
            tipoSolicitudListFiltro = tipoSolicitudList;
        }
        return tipoSolicitudListFiltro;
    }

    private String listInfoByPage(int page) {
        try {
            List<CmtTipoSolicitudMgl> tipoSolicitudListFiltro = getListTipoFiltro();
            totalSolicitudesllamadas = solicitudCmMglFacadeLocal.
                    getCountPendientesByFiltroParaGestion(departamentoSelected,
                    ciudadSelected, tipoSegmentoSelected, tipoSolicitudListFiltro, true);

            actual = page;
            getPageActual();
            solicitudList = solicitudCmMglFacadeLocal.
                    findPendientesByFiltroParaGestionPaginacion(page, ConstantsCM.PAGINACION_DIEZ_FILAS,
                    departamentoSelected, ciudadSelected,
                    tipoSegmentoSelected, tipoSolicitudListFiltro, consultaLlamadas, ordenMayorMenor);

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:listInfoByPage. " + e.getMessage(), e, LOGGER);
        }
        return null;
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
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:irPagina(). " + e.getMessage(), e, LOGGER);
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
        try {
            List<CmtTipoSolicitudMgl> tipoSolicitudListFiltro = getListTipoFiltro();
            int pageSol = solicitudCmMglFacadeLocal.
                    getCountPendientesByFiltroParaGestion(
                    departamentoSelected, ciudadSelected,
                    tipoSegmentoSelected, tipoSolicitudListFiltro, consultaLlamadas);
            int totalPaginas = (int) (
                    (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                        ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                        : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
               FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActual() {
        try {
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    /**
     * 
     * @return 
     */
    public boolean validarGestionSolicitud() {
        if (validarGestionSolicitud == null) {
            validarGestionSolicitud = validarAccion(FORMULARIO_SOLICITUD);
        }
        return validarGestionSolicitud;
    }

    /**
     * 
     * @return 
     */
    public boolean validarGestionCM() {
        if (validarGestionCM == null) {
            validarGestionCM = validarAccion(FORMULARIO_CM);
        }
        return validarGestionCM;
    }

    /**
     * 
     * @return 
     */
    public boolean validarGestionDesbloqueo() {
        if (validarGestionDesbloqueo == null) {
            validarGestionDesbloqueo = validarAccion(FORMULARIO_DESBLOQUEAR);
        }
        return validarGestionDesbloqueo;
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     * 
     * @param formulario String nombre del componente que se valida
     * @return boolean indicador para verificar si se visualizan o no los componentes
     */
    private boolean validarAccion(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
           
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:validarAccion. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtBasicaMgl> getTipoSegmentoList() {
        return tipoSegmentoList;
    }

    public void setTipoSegmentoList(List<CmtBasicaMgl> tipoSegmentoList) {
        this.tipoSegmentoList = tipoSegmentoList;
    }


    public String getDepartamentoSelected() {
        return departamentoSelected;
    }

    public void setDepartamentoSelected(String departamentoSelected) {
        this.departamentoSelected = departamentoSelected;
    }

    public String getCiudadSelected() {
        return ciudadSelected;
    }

    public void setCiudadSelected(String ciudadSelected) {
        this.ciudadSelected = ciudadSelected;
    }

    public List<CmtTipoSolicitudMgl> getTipoSolicitudList() {
        return tipoSolicitudList;
    }

    public void setTipoSolicitudList(List<CmtTipoSolicitudMgl> tipoSolicitudList) {
        this.tipoSolicitudList = tipoSolicitudList;
    }

    public CmtTipoSolicitudMgl getTipoSolicitudSelected() {
        return tipoSolicitudSelected;
    }

    public void setTipoSolicitudSelected(CmtTipoSolicitudMgl tipoSolicitudSelected) {
        this.tipoSolicitudSelected = tipoSolicitudSelected;
    }

    public List<CmtSolicitudCmMgl> getSolicitudList() {
        return solicitudList;
    }

    public void setSolicitudList(List<CmtSolicitudCmMgl> solicitudList) {
        this.solicitudList = solicitudList;
    }

    public CmtBasicaMgl getTipoSegmentoSelected() {
        return tipoSegmentoSelected;
    }

    public void setTipoSegmentoSelected(CmtBasicaMgl tipoSegmentoSelected) {
        this.tipoSegmentoSelected = tipoSegmentoSelected;
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

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_DIEZ_FILAS;
    }

    public boolean isShowFiltro() {
        return showFiltro;
    }

    public void setShowFiltro(boolean showFiltro) {
        this.showFiltro = showFiltro;
    }

    public int getTotalSolicitudesDia() {
        return totalSolicitudesDia;
    }

    public void setTotalSolicitudesDia(int totalSolicitudesDia) {
        this.totalSolicitudesDia = totalSolicitudesDia;
    }

    public int getTotalSolicitudesVencidasDia() {
        return totalSolicitudesVencidasDia;
    }

    public void setTotalSolicitudesVencidasDia(int totalSolicitudesVencidasDia) {
        this.totalSolicitudesVencidasDia = totalSolicitudesVencidasDia;
    }

    public int getTotalSolicitudesPorVencerDia() {
        return totalSolicitudesPorVencerDia;
    }

    public void setTotalSolicitudesPorVencerDia(int totalSolicitudesPorVencerDia) {
        this.totalSolicitudesPorVencerDia = totalSolicitudesPorVencerDia;
    }

    public int getTotalSolicitudesGestionadasDia() {
        return totalSolicitudesGestionadasDia;
    }

    public void setTotalSolicitudesGestionadasDia(int totalSolicitudesGestionadasDia) {
        this.totalSolicitudesGestionadasDia = totalSolicitudesGestionadasDia;
    }

    public String getIdSolicitudToFind() {
        return idSolicitudToFind;
    }

    public void setIdSolicitudToFind(String idSolicitudToFind) {
        this.idSolicitudToFind = idSolicitudToFind;
    }

    public boolean isShowFooterTable() {
        return showFooterTable;
    }

    public void setShowFooterTable(boolean showFooterTable) {
        this.showFooterTable = showFooterTable;
    }

    public int getTotalSolicitudesllamadas() {
        return totalSolicitudesllamadas;
    }

    public void setTotalSolicitudesllamadas(int totalSolicitudesllamadas) {
        this.totalSolicitudesllamadas = totalSolicitudesllamadas;
    }


    public List<CmtRegionalRr> getLstCmtRegionalRrs() {
        return lstCmtRegionalRrs;
    }

    public void setLstCmtRegionalRrs(List<CmtRegionalRr> lstCmtRegionalRrs) {
        this.lstCmtRegionalRrs = lstCmtRegionalRrs;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public List<GeograficoPoliticoMgl> getLstDeptosGeograficoPoriticoMgl() {
        return lstDeptosGeograficoPoriticoMgl;
    }

    public void setLstDeptosGeograficoPoriticoMgl(List<GeograficoPoliticoMgl> lstGeograficoPoriticoMegl) {
        this.lstDeptosGeograficoPoriticoMgl = lstGeograficoPoriticoMegl;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return lstCiudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.lstCiudadesList = ciudadesList;
    }

    public CmtSolicitudCmMgl retornarSolicitud(BigDecimal idSolicitud){
         CmtSolicitudCmMgl solicitudCmMglId = null;
        List<CmtTipoSolicitudMgl> tipoSolicitudListFiltro = getListTipoFiltro();
        try {
            solicitudCmMglId = solicitudCmMglFacadeLocal.
                    findBySolicitudPorPermisos(tipoSolicitudListFiltro, idSolicitud);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudGestionarMglBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
       
        return solicitudCmMglId;
    }
    
    public void cambiarOrdenMayorMenorListado() {
        if (ordenMayorMenor) {
            ordenMayorMenor = false;
        } else {
            ordenMayorMenor = true;
        }
        listInfoByPage(1);
    }

    
    public boolean validarOpcionGestSol() {
        return validarEdicionRol(SRHBTGSTL);
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
