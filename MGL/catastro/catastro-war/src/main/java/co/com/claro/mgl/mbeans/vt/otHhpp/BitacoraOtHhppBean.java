/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mer.utils.pagination.PaginatorUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaHhppDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *  Bean asociado a la vista de bitacora de OT de HHPP
 *
 * @author Luz Villalobos
 * @version 1.0
 */
@ManagedBean(name = "bitacoraOtHhppBean")
@ViewScoped
public class BitacoraOtHhppBean {

    public static final String ERROR_METODO = "Se produjo un error al momento de ejecutar el método '";
    public static final String VIEW_MGL_VT_OT_HHPP = "/view/MGL/VT/otHhpp/";
    private String selectedTab = "BITACORA";
    private boolean activacionUCM;
    private OtHhppMgl otHhppSeleccionado;
    private int numPagina = 1;
    private List<Integer> paginaList;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoGpo;
    private GeograficoPoliticoMgl ciudadGpo;
    private List<AuditoriaHhppDto> informacionAuditoria = null;  
    private PaginatorUtil<AuditoriaHhppDto> paginator;
    private List<AuditoriaHhppDto> listaAuditorias = new ArrayList<>();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(BitacoraOtHhppBean.class);
       
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
   
    

    public BitacoraOtHhppBean() {
        
        SecurityLogin securityLogin;
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin() && !response.isCommitted()) {
                response.sendRedirect(securityLogin.redireccionarLogin());
            }

        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Error en Bitacora : " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Exp: Error en Bitacora : " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            cargarOtHhppSeleccionada();
            mostrarAuditoria();
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en Bitacora en BitacoraBean: init(): " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en Bitacora en BitacoraBean: init(): " + e.getMessage(), e, LOGGER);
        }
    }
    
     /**
     * Metodo para mostrar la informacion de la tabla de auditorias de una orden de trabajo
     * @author Luz Villalobos
     */
    public void mostrarAuditoria() {
         
        if (otHhppSeleccionado != null) {
            try {
                informacionAuditoria = otHhppMglFacadeLocal.construirAuditoria(otHhppSeleccionado);
                listInfoByPage(1);
            } catch (InvocationTargetException e) {
                FacesUtil.mostrarMensajeError("Error al mostarAuditoria: " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al mostarAuditoria. " + e.getMessage(), e, LOGGER);
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }
    
    public void cargarOtHhppSeleccionada() throws ApplicationException {
        try {
            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
            OtHhppMgl otHhpp = new OtHhppMgl();
            otHhpp.setOtHhppId(otHhppMglSessionBean.getOtHhppMglSeleccionado().getOtHhppId());
            otHhppSeleccionado = otHhppMglFacadeLocal.findById(otHhpp);           
            obtenerCiudadDepartamentoByCentroPobladoId(otHhppSeleccionado.getDireccionId().getUbicacion().getGpoIdObj().getGpoId());
        } catch (RuntimeException e) {
            String msg = ERROR_METODO
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (ApplicationException e) {
            String msg = ERROR_METODO
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "' : " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }
    /**
     * Función que obtiene listado de unidades asociadas al predio
     *
     * @author Luz Villalobos
     * @param centroPobladoId
     */
    public void obtenerCiudadDepartamentoByCentroPobladoId(BigDecimal centroPobladoId) throws ApplicationException {
        try {
            centroPobladoGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoId);

            ciudadGpo = geograficoPoliticoMglFacadeLocal
                    .findById(centroPobladoGpo.getGeoGpoId());
            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(ciudadGpo.getGeoGpoId()));

        } catch (ApplicationException e) {
            String msg = ERROR_METODO
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método: '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg + e.getMessage(), e);
        }
    }
    
    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @param sSeleccionado
     */
    public void cambiarTab(String sSeleccionado) {
        try {
            ConstantsCM.TABS_HHPP seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(sSeleccionado);
            switch (seleccionado) {
                case GENERAL:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                                            + "editarOtHhpp.jsf");
                    break;
                case AGENDAMIENTO:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                                    + "agendamientoOtHhpp.jsf");
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case NOTAS:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                                    + "notasOtHhpp.jsf");
                    selectedTab = "NOTAS";
                    break;
                case ONYX:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                            + "otOnixHhpp.jsf");
                    selectedTab = "ONYX";
                    break;
                case HISTORICO:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                            + "historicoAgendasOtHhpp.jsf");
                    selectedTab = "HISTORICO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case BITACORA:
                    FacesUtil.navegarAPagina(VIEW_MGL_VT_OT_HHPP
                            + "bitacoraOtHhpp.jsf");
                    selectedTab = "BITACORA";
                    break;
                default:
                    break;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab: ", e, LOGGER);
        }
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
                } else if (valor.equalsIgnoreCase("1")) {
                    activacionUCM = true;
                } else {
                    activacionUCM = false;
                }

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
     /**
     * Lista la información a visualizar en la tabla de auditorias
     *
     * @param numPage Número de página de la que se quiere mostrar las auditorias
     */
    public void listInfoByPage(int numPage) {
        try {
            if (Objects.isNull(paginator)) paginator = new PaginatorUtil<>();

            if (informacionAuditoria != null) {
                paginator.pagingProcess(numPage, ConstantsCM.PAGINACION_TREINTA_FILAS, informacionAuditoria);
                listaAuditorias = paginator.getData().getPaginatedList();
            }
        } catch (ApplicationException | NullPointerException e) {
            String msgError = "Ocurrió un error al paginar la información de Auditorias: "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            FacesUtil.mostrarMensajeError(msgError, e);
        }
    }
    
    /**
     * Obtiene la información de la página actual seleccionada en
     * la tabla de auditorias
     *
     * @return Retorna la descripción de la posición actual de la página de
     * los resultados de auditorias y la cantidad de páginas disponibles.
     */
    public String getPageActual(){
        paginaList = paginator.getData().getPageNumberSelector();
        numPagina = paginator.getData().getSelectedPage();
        return paginator.getData().getDescriptionPages();
    }

  
    /**
     * Carga los resultados de la primera página disponible por mostrar.
     */
    public void pageFirst(){
        listInfoByPage(paginator.goFirstPage());
    }

   
    /**
     * Carga los resultados de reportes de la página anterior disponible por mostrar.
     */
    public void pagePrevious(){
        listInfoByPage(paginator.goPreviousPage());
    }

    /**
     * Redirige hacia una posición de página de resultados de auditorias seleccionados.
     */
    public void irPagina(){
        listInfoByPage(numPagina);
    }

   
     /**
     * Carga los resultados de auditorias de la página siguiente disponible por mostrar.
     */
    public void pageNext(){
        listInfoByPage(paginator.goNextPage());
    }

    /**
     * Carga los resultados de auditorias de la Última pÃ¡gina disponible por mostrar.
     */
    public void pageLast(){
        int totalPages = paginator.goLastPage();
        listInfoByPage(totalPages);
    }
    
    public List<AuditoriaHhppDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaHhppDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }
    public String getSelectedTab() {
        return selectedTab;
    }
    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public OtHhppMgl getOtHhppSeleccionado() {
        return otHhppSeleccionado;
    }
    public void setOtHhppSeleccionado(OtHhppMgl otHhppSeleccionado) {
        this.otHhppSeleccionado = otHhppSeleccionado;
    }
    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }
    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }
    public GeograficoPoliticoMgl getCentroPobladoGpo() {
        return centroPobladoGpo;
    }

    public void setCentroPobladoGpo(GeograficoPoliticoMgl centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }
    public int getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }
   
    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }
    public List<AuditoriaHhppDto> getListaAuditorias() {
        return listaAuditorias;
    }

    public void setListaAuditorias(List<AuditoriaHhppDto> listaAuditorias) {
        this.listaAuditorias = listaAuditorias;
    }
}
