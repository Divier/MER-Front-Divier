/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mer.utils.pagination.PaginatorUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.AgendamientoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccionAuditoria;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 *
 * @author bocanegravm
 */
@ViewScoped
@ManagedBean(name = "historicoAgendasOtHhppBean")
public class HistoricoAgendasOtHhppBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(HistoricoAgendasOtHhppBean.class);
    private static final long serialVersionUID = 1L;


    @EJB
    private AgendamientoHhppMglFacadeLocal agendamientoHhppFacade;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;

    private List<MglAgendaDireccion> agendas;
    private List<MglAgendaDireccionAuditoria> agendasAuditoria;
    private List<MglAgendaDireccion> agendasTotal = new ArrayList<>();
    private OtHhppMgl otHhppSeleccionado;
    private List<String> nombresArchivos;
    private SecurityLogin securityLogin;
    private String usuarioVt;
    private Integer perfilVt;
    private PaginatorUtil<MglAgendaDireccionAuditoria> paginator;
    private PaginatorUtil<MglAgendaDireccion> paginador;
    private List<MglAgendaDireccionAuditoria> listaAuditorias = new ArrayList<>();
    private int numPagina = 1;
    private int numeroPagina = 1;
    private List<Integer> paginaList;
    private List<Integer> paginaLista;
    private boolean showLogEstado;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String urlArchivoSoporte;
    private boolean activacionUCM;
    private String selectedTab = "HISTORICO";

    @PostConstruct
    public void init() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            usuarioVt = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            this.showLogEstado = Boolean.FALSE; 
            OtHhppMglSessionBean otHhppMglSessionBean
                    = JSFUtil.getSessionBean(OtHhppMglSessionBean.class);

            if (otHhppMglSessionBean != null) {
                OtHhppMgl otHhpp = new OtHhppMgl();
            otHhpp.setOtHhppId(otHhppMglSessionBean.getOtHhppMglSeleccionado().getOtHhppId());
            otHhppSeleccionado = otHhppMglFacadeLocal.findById(otHhpp); 
            } else {
                FacesUtil.mostrarMensajeError("No fue posible cargar el bean de OT.", null, LOGGER);
            }
            listInfoByPage(1);
            if (session != null && session.getAttribute("activaUCM") != null) {
                activacionUCM = (boolean) session.getAttribute("activaUCM");
                session.removeAttribute("activaUCM");
                if (activacionUCM) {
                    mostrarDocumentosAdjuntos(agendas);
                    listInfoByPage(1);
                }
            } else {
                FacesUtil.mostrarMensajeError("Se ha perdido la sesión al momento de instanciar el histórico de agendas.", null, LOGGER);
            }
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se ha producido un error al momento de instanciar el histórico de agendas: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error HistoricoAgendasOtHhppBean: init(): " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que permite navegar entre Tabs en la pantalla
     *
     * @author Victor Bocanegra
     * @param tabSeleccionada
     */
    public void cambiarTab(String tabSeleccionada) {
        try {
            ConstantsCM.TABS_HHPP Seleccionado
                    = ConstantsCM.TABS_HHPP.valueOf(tabSeleccionada);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            switch (Seleccionado) {
                case GENERAL:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "editarOtHhpp.jsf");
                    break;
                case AGENDAMIENTO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "agendamientoOtHhpp.jsf");
                    selectedTab = "AGENDAMIENTO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case NOTAS:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "notasOtHhpp.jsf");
                    selectedTab = "NOTAS";
                    break;
                case ONYX:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "otOnixHhpp.jsf");
                    selectedTab = "ONYX";
                    break;
                case HISTORICO:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "historicoAgendasOtHhpp.jsf");
                    selectedTab = "HISTORICO";
                    activacionUCM = activaDesactivaUCM();
                    session.setAttribute("activaUCM", activacionUCM);
                    break;
                case BITACORA:
                    FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "bitacoraOtHhpp.jsf");
                    selectedTab = "BITACORA";
                    break;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambiarTab. ", e, LOGGER);
        }
    }
    
    /**
     * Función que permite consultar los historicos de la agenda y paginarlos. 
     */
    private void listInfoByPage(int numPage) {
        try {
            agendas = agendamientoHhppFacade.buscarAgendasPorOt(otHhppSeleccionado);
            if (Objects.isNull(paginador)) paginador = new PaginatorUtil<>();

            if (agendas != null) {
                paginador.pagingProcess(numPage, ConstantsCM.PAGINACION_DIEZ_FILAS, agendas);
                agendasTotal = paginador.getData().getPaginatedList();
            }
        } catch (ApplicationException | NullPointerException e) {
            String msgError = "Ocurrió un error al paginar la información de Auditorias: "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            FacesUtil.mostrarMensajeError(msgError, e);
        }
    }
    public String getPageActual() {
        paginaList = paginador.getData().getPageNumberSelector();
        numPagina = paginador.getData().getSelectedPage();
        return paginador.getData().getDescriptionPages();
    }

    public void pageFirst() {
       listInfoByPage(paginador.goFirstPage());
    }

    public void pagePrevious() {
        listInfoByPage(paginador.goPreviousPage());
    }

    public void irPagina() {
        listInfoByPage(numPagina);
    }

    public void pageNext() {
        listInfoByPage(paginador.goNextPage());
    }

    public void pageLast() {
        int totalPages = paginador.goLastPage();
        listInfoByPage(totalPages);
    }
    
    public void mostrarDocumentosAdjuntos(List<MglAgendaDireccion> agendas) {

        for (MglAgendaDireccion agendaOT : agendas) {

            if (agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)
                    || agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {

                if (agendaOT.getDocAdjuntos() == null) {
                    consultarDocumentos(agendaOT);
                }

            }
        }
    }
    
    public void consultarDocumentos(MglAgendaDireccion agenda) {
        try {

            nombresArchivos = agendamientoHhppFacade.
                    consultarDocumentos(agenda, usuarioVt, perfilVt);
        } catch (ApplicationException ex) {
            LOGGER.error("Error consultado los documentos adjuntos", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ex.getMessage(), "Ocurrió un error al consultar los documentos adjuntos"));
        } catch (Exception ex) {
            LOGGER.error("Error consultado los documentos adjuntos", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            ex.getMessage(), "Ocurrió un error al consultar los documentos adjuntos"));
        }

    }
    
    public List<CmtArchivosVtMgl> consultaArchivosAge(MglAgendaDireccion agenda) {

        List<CmtArchivosVtMgl> archivosAge = new ArrayList<>();
        try {
            archivosAge = cmtArchivosVtMglFacadeLocal.findByIdOtHhppAndAge(agenda);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar archivo de la agenda: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error consultando archivos en AgendamientoOtHhppBean: consultaArchivosAge()" + e.getMessage(), e, LOGGER);
        }
        return archivosAge;
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
    /**
     * Consulta las auditorias de la agenda seleccionada y redirecciona a la vista log estados
     * Autor: Luz Villalobos
     *
     * @param agendaId {@link Integer}
     * @return MglAgendaDireccionAuditoria 
     * @throws ApplicationException Excepción lanzada en la consulta
     */
    public boolean irLogEstadoAgenda(Integer agendaId) throws ApplicationException {
        showLogEstado = !showLogEstado;
        try{
            agendasAuditoria = agendamientoHhppFacade.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
            listaInfoPaginaLogEstados(1);
            if(agendasAuditoria == null){
                FacesUtil.mostrarMensajeError("No se encuentran log estados para esta agenda.", null, LOGGER);
                showLogEstado = false;
                return  showLogEstado;
            }
        }
        catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al consultar las Auditorias de la agenda " + e.getMessage(), e, LOGGER);
        }
        return showLogEstado;
    }
    /**
     * Redirecciona a la lista de historicos de agendamiento.
     * @return {@code boolean}
     */
    public boolean regresarHistorico(){
        showLogEstado = !showLogEstado;
        return showLogEstado;
    } 
    
    public boolean activaDesactivaUCM() {

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
     * Carga los resultados de la primera página disponible por mostrar.
     */
    public void paginaPrimera(){
        listaInfoPaginaLogEstados(paginator.goFirstPage());
    }
    
    /**
     * Carga los resultados de reportes de la página anterior disponible por mostrar.
     */
    public void paginaAnterior(){
        listaInfoPaginaLogEstados(paginator.goPreviousPage());
    }
    
    /**
     * Obtiene la información de la pógina actual seleccionada en
     * la tabla de auditorias
     *
     * @return Retorna la descripción de la posición actual de la página de
     * los resultados de auditorias y la cantidad de páginas disponibles.
     */
    public String getPaginaActual(){
        paginaLista = paginator.getData().getPageNumberSelector();
        numeroPagina = paginator.getData().getSelectedPage();
        return paginator.getData().getDescriptionPages();
    }
    
    /**
     * Redirige hacia una posición de página de resultados de auditorias seleccionados.
     */
    public void irPage(){
        listaInfoPaginaLogEstados(numeroPagina);
    }
     /**
     * Carga los resultados de auditorias de la página siguiente disponible por mostrar.
     */
    public void paginaSiguiente(){
        listaInfoPaginaLogEstados(paginator.goNextPage());
    }
    /**
     * Carga los resultados de auditorias de la Última página disponible por mostrar.
     */
    public void paginaUltima(){
        int totalPages = paginator.goLastPage();
        listaInfoPaginaLogEstados(totalPages);
    }
    
    /**
     * Lista la información a visualizar en la tabla de log estados
     *
     * @param numPage Número de página de la que se quiere mostrar los log estados. 
     */
    public void listaInfoPaginaLogEstados(int numPage) {
        try {
            if (Objects.isNull(paginator)) paginator = new PaginatorUtil<>();

            if (agendasAuditoria != null) {
                paginator.pagingProcess(numPage, ConstantsCM.PAGINACION_DIEZ_FILAS, agendasAuditoria);
                listaAuditorias = paginator.getData().getPaginatedList();
            }
        } catch (ApplicationException | NullPointerException e) {
            String msgError = "Ocurrió un error al paginar la información de Auditorias: "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            FacesUtil.mostrarMensajeError(msgError, e);
        }
    }
    
    public List<String> getNombresArchivos() {
        return nombresArchivos;
    }

    public void setNombresArchivos(List<String> nombresArchivos) {
        this.nombresArchivos = nombresArchivos;
    }

    public OtHhppMgl getOtHhppSeleccionado() {
        return otHhppSeleccionado;
    }

    public void setOtHhppSeleccionado(OtHhppMgl otHhppSeleccionado) {
        this.otHhppSeleccionado = otHhppSeleccionado;
    }

    public List<MglAgendaDireccion> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<MglAgendaDireccion> agendas) {
        this.agendas = agendas;
    }
    
    public List<MglAgendaDireccionAuditoria> getAgendasAuditoria() {
        return agendasAuditoria;
    }

    public void setAgendasAuditoria(List<MglAgendaDireccionAuditoria> agendasAuditoria) {
        this.agendasAuditoria = agendasAuditoria;
    }
    public int getNumPagina() {
        return numPagina;
    }
     public boolean isShowLogEstado() {
        return showLogEstado;
    }
    public void setShowLogEstado(boolean showLogEstado) {
        this.showLogEstado = showLogEstado;
    }
    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public List<Integer> getPaginaLista() {
        return paginaLista;
    }

    public void setPaginaLista(List<Integer> paginaLista) {
        this.paginaLista = paginaLista;
    }

    public List<MglAgendaDireccionAuditoria> getListaAuditorias() {
        return listaAuditorias;
    }

    public void setListaAuditorias(List<MglAgendaDireccionAuditoria> listaAuditorias) {
        this.listaAuditorias = listaAuditorias;
    }
    
    public List<MglAgendaDireccion> getAgendasTotal() {
        return agendasTotal;
    }

    public void setAgendasTotal(List<MglAgendaDireccion> agendasTotal) {
        this.agendasTotal = agendasTotal;
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
    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

}
