/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.utils.pagination.PaginatorUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtAgendamientoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxFlujoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendaAuditoria;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bocanegravm
 */
@ViewScoped
@ManagedBean(name = "cmtHistoricoAgendasOtMglBean")
public class CmtHistoricoAgendasOtMglBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CmtHistoricoAgendasOtMglBean.class);
    private static final long serialVersionUID = 1L;

    @EJB
    private CmtAgendamientoMglFacadeLocal agendamientoFacade;
    @EJB
    private CmtEstadoxFlujoMglFacadeLocal cmtEstadoxFlujoMglFacadeLocal;
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;

    private List<CmtAgendamientoMgl> agendas;
    private CmtOrdenTrabajoMgl ot;
    private List<String> nombresArchivos;
    private SecurityLogin securityLogin;
    private String usuarioVt;
    private Integer perfilVt;
    private OtMglBean otMglBean;
    private String pageActual;
    private boolean showLogEstado; 
    private String numPagina = "1";
    private List<Integer> paginaList;
    private boolean showFooterTable;
    private int actual;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String urlArchivoSoporte;
    private List<CmtAgendaAuditoria> agendasAuditoria;
    private List<CmtAgendaAuditoria> listaAuditorias = new ArrayList<>();
    private PaginatorUtil<CmtAgendaAuditoria> paginator;
    private List<Integer> paginaLista;
    private int numeroPagina = 1;
    private boolean activacionUCM;

    @PostConstruct
    public void init() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            usuarioVt = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            this.showLogEstado = Boolean.FALSE; 
            this.otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
            if (otMglBean != null) {
                ot = otMglBean.getOrdenTrabajo();
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
            FacesUtil.mostrarMensajeError("Se genera error CmtHistoricoAgendasOtMglBean: init(): " + e.getMessage(), e, LOGGER);
        }
    }

    private String listInfoByPage(int page) {
        try {
            if (ot.getIdOt() != null) {
                actual = page;
                getPageActual();
                agendas = agendamientoFacade.buscarAgendasHistoricosPorOt(page, ConstantsCM.PAGINACION_DIEZ_FILAS, ot);
            } else {
                FacesUtil.mostrarMensajeWarn( "Se requiere crear una Ot previamente");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSN_PROCESO_FALLO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en cargar listas HistoricoAgendaOtMglBean: listInfoByPage()" + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String getPageActual() {
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
            FacesUtil.mostrarMensajeError("Se genera error en la paginacion  en HistoricoAgendaOtMglBean: getPageActual() " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Se genera error en la paginacion  en HistoricoAgendaOtMglBean: pageFirst() "  + ex.getMessage(), ex);
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
            LOGGER.error("Se genera error en la paginacion  en HistoricoAgendaOtMglBean: pagePrevious() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Error al momento de navegar a la página. EX000: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en la paginacion  en HistoricoAgendaOtMglBean: irPagina() " + e.getMessage(), e, LOGGER);
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
             FacesUtil.mostrarMensajeError("Se genera error en la paginacion en HistoricoAgendaOtMglBean: pageNext() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Error al momento de navegar a la siguiente página. EX000: " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
             FacesUtil.mostrarMensajeError("Se genera error en la paginacion en HistoricoAgendaOtMglBean: pageLast() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Error al momento de navegar a la última página. EX000: " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            
            int pageSol = agendamientoFacade.getCountAgendasHistoricosPorOt(ot);
            int totalPaginas = (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al momento de consultar el total de páginas. EX000: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en la paginacion en HistoricoAgendaOtMglBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }


    public void mostrarDocumentosAdjuntos(List<CmtAgendamientoMgl> agendas) {
        try {

            if (agendas != null && !agendas.isEmpty()) {
                for (CmtAgendamientoMgl agendaOT : agendas) {

                    if (agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                            .equalsIgnoreCase(Constant.ESTADO_AGENDA_NODONE)
                            || agendaOT.getBasicaIdEstadoAgenda().getIdentificadorInternoApp()
                                    .equalsIgnoreCase(Constant.ESTADO_AGENDA_CERRADA)) {

                        if (agendaOT.getDocAdjuntos() == null) {
                            consultarDocumentos(agendaOT);
                        }

                    }
                }
            } else {
                LOGGER.info("No existen agendas, por lo cual no se encontraron documentos adjuntos.");
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al mostrar documento adjuntos en HistoricoAgendaOtMglBean: mostrarDocumentosAdjuntos() " + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarDocumentos(CmtAgendamientoMgl agenda) {
        try {

            nombresArchivos = agendamientoFacade.
                    consultarDocumentos(agenda, usuarioVt, perfilVt);
        } catch (ApplicationException ex) {
            LOGGER.error("Error consultado los documentos adjuntos", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ocurrió un error al consultar los documentos adjuntos: " + ex.getMessage() + "", null));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar documento  en HistoricoAgendaOtMglBean: consultarDocumentos() "+ e.getMessage(), e, LOGGER);
        }

    }

    public List<CmtArchivosVtMgl> consultaArchivosAge(CmtAgendamientoMgl agenda) {

        List<CmtArchivosVtMgl> archivosAge = new ArrayList<>();
        try {
            archivosAge = cmtArchivosVtMglFacadeLocal.findByIdOtAndAge(agenda);
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar archivos de agendas: "+ ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar archivos de agendas: "+ e.getMessage(), e, LOGGER);
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
     * @return CmtAgendaAuditoria
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public boolean irLogEstadoAgenda(Integer agendaId) throws ApplicationException {
        showLogEstado = !showLogEstado;
        agendasAuditoria = agendamientoFacade.buscarAgendasAuditoriaPorHistoricosAgenda(agendaId);
        listaInfoPaginaLogEstados(1);
        if(agendasAuditoria == null){
            FacesUtil.mostrarMensajeError("No se encuentran log estados para esta agenda.", null, LOGGER);
            showLogEstado = false;
            return showLogEstado;
        }
        return showLogEstado;
    }
    /**
     * Redirecciona a la lista de historicos de agendamiento.
     * @return showLogEstado
     */
    public boolean regresarHistorico(){
        showLogEstado = !showLogEstado;
        return showLogEstado;
    } 
    
    /**
     * Lista la información a visualizar en la tabla de log estados
     *
     * @param numPage Número de página de la que se quiere mostrar las auditorias
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
     * Obtiene la información de la página actual seleccionada en
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
    
    public boolean isShowLogEstado() {
        return showLogEstado;
    }
    public void setShowLogEstado(boolean showLogEstado) {
        this.showLogEstado = showLogEstado;
    }
    public List<String> getNombresArchivos() {
        return nombresArchivos;
    }
    public List<CmtAgendaAuditoria> getListaAuditorias() {
        return listaAuditorias;
    }
    public void setListaAuditorias(List<CmtAgendaAuditoria> listaAuditorias) {
        this.listaAuditorias = listaAuditorias;
    }
    public List<Integer> getPaginaLista() {
        return paginaLista;
    }

    public void setPaginaLista(List<Integer> paginaLista) {
        this.paginaLista = paginaLista;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    public void setNombresArchivos(List<String> nombresArchivos) {
        this.nombresArchivos = nombresArchivos;
    }

    public CmtOrdenTrabajoMgl getOt() {
        return ot;
    }

    public void setOt(CmtOrdenTrabajoMgl ot) {
        this.ot = ot;
    }

    public List<CmtAgendamientoMgl> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<CmtAgendamientoMgl> agendas) {
        this.agendas = agendas;
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

    public boolean isShowFooterTable() {
        return showFooterTable;
    }

    public void setShowFooterTable(boolean showFooterTable) {
        this.showFooterTable = showFooterTable;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
    }
    
    

}
