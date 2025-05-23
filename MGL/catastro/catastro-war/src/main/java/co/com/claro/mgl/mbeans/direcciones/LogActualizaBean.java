/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.LogActualizaDetalleFacadeLocal;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import co.com.claro.mgl.facade.cm.LogActualizaFacadeLocal;
import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * Bean para administracion del Modulo LOG ACTUALIZACIONES NAP.
 *
 * @author yduarte
 */
@ManagedBean(name = "logActualizaBean")
@ViewScoped
public class LogActualizaBean {

    private static final Logger LOGGER = LogManager.getLogger(LogActualizaBean.class);
    private HtmlDataTable dataTable = new HtmlDataTable();
    private List<LogActualizaMaster> logActualizaList = new ArrayList<>();
    private List<LogActualizaMaster> logActualizaTroncalList = new ArrayList<>();
    private List<LogActualizaMaster> logActualizaTipoTecList = new ArrayList<>();
    private List<LogActualizaMaster> logActualizaDepartamentoList = new ArrayList<>();
    private List<LogActualizaMaster> logActualizaCiudadList = new ArrayList<>();
    private List<LogActualizaMaster> logActualizaCentroPobladoList = new ArrayList<>();
    private LogActualizaMaster logNapListSelecionado = new LogActualizaMaster();
    private String isInfoProcesada = String.valueOf(false);
    private String labelHeaderTableLists;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private Date fechaInicial;
    private Date fechaFinal;
    private String troncal;
    private String tipoTecnologia;
    private String nombreArchivo = "";
    private String departamento;
    private String ciudad;
    private String centroPoblado;
    private String usuarioCreacion = "";
    private boolean registrosFiltrados;
    private List<LogActualizaDetalle> registrosNoProcesados;
    private List<LogActualizaDetalle> registrosProcesados;
    private List<LogActualizaDetalle> totalRegistros;
    private HashMap<String, Object> params = new HashMap<>();
    
    /**
     * El flag filtro aplicado se desactiva si se cambia la fecha inicial o la
     * fecha final El flag filtro aplicado se activa al dar realizar
     * correctamente el filtrado de la lista por el rango de fechas incluidad
     * las fecha nullas si el filtro ha sido aplicado se pueden habilitar los
     * botones de navegacion filtro aplicado se diferencia de registros
     * filtrados en que incluye el rango con fechas nulas
     */
    private boolean filtroAplicado = true;
    private boolean rangoFechasValido = true;
    private boolean botonFiltar = false;
    private boolean filtrarV2 = false;
    private String filtroV2 = null;
    
    private boolean botonError = true;
    private boolean inputTroncal = true;
    private boolean inputTipoTecnologia = true;
    private boolean inputNombreArchivo = true;
    private boolean inputDepartamento = true;
    private boolean inputCiudad = true;
    private boolean inputCentroPoblado = true;
    private boolean inputUsuarioCreacion = true;
    
    @EJB
    private LogActualizaFacadeLocal logActualizaMglFacadeLocal;
    @EJB
    private LogActualizaDetalleFacadeLocal logActualizaDetalleMglFacadeLocal;
    
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpServletRequest httpRequest = FacesUtil.getServletRequest();
    private HttpSession httpSession = httpRequest.getSession();
    
    /**
     * Creates a new instance of LogActualizaBean
     */
    public LogActualizaBean() {
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
        } catch (IOException e) {
            LOGGER.error("Error en LogActualizaBean. ", e);
        } catch (Exception e) {
            LOGGER.error("Error en LogActualizaBean. ", e);
        }
    }

    @PostConstruct
    public void loadList() {
        try {
            //Entra al paginado
            listInfoByPage(1);
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage(), e);
        }
    }
    
    private String listInfoByPage(int page) {
        try {
            params.put("troncal", troncal);
            params.put("tipoTecnologia", tipoTecnologia);
            params.put("nombreArchivo", nombreArchivo);
            params.put("departamento", departamento);
            params.put("ciudad", ciudad);
            params.put("centroPoblado", centroPoblado);
            params.put("usuarioCreacion", usuarioCreacion);
            //se carga log segun filtro
            llenarFiltrosSelect();
            logActualizaList = logActualizaMglFacadeLocal.getListFichaToCreateByDate(page, ConstantsCM.PAGINACION_DIEZ_FILAS, true, params, fechaInicial, fechaFinal);

            actual = page;

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            LOGGER.error(msn + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error cargando lista en FichaGenerarBean: listInfoByPage() ", e);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageFirst() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pagePrevious() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pagePrevious() " + ex.getMessage(), ex);
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
            LOGGER.error("Ocurrió un error en FichaGenerarBean class: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: irPagina() " + e.getMessage(), e);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pageNext() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en FichaGenerarBean: pageLast() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en FichaGenerarBean: pageLast() " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {

            List<LogActualizaMaster> logNaoListCon = logActualizaMglFacadeLocal.getListFichaToCreateByDate(0, 0, false, params, fechaInicial, fechaFinal);
            int pageSol = logNaoListCon.size();
            return (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrió un error en LogActualizaBean class: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error en la paginacion en LogActualizaBean: getTotalPaginas() " + e.getMessage(), e);
        }
        return 1;
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
            LOGGER.error("Ocurrió un error en la paginacion en LogActualizaBean: getPageActual() " + e.getMessage(), e);
        }
        return pageActual;
    }

    public void filtrarLista() {
        registrosFiltrados = false;
        filtroAplicado = false;
        botonFiltar = true;
        if (fechaInicial != null && fechaFinal != null) {

            if (fechaInicial.compareTo(fechaFinal) > 0) {
                String msn = "La fecha inicial debe ser anterior a la final.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
            } else {

                pageFirst();
                this.registrosFiltrados = true;
                this.filtroAplicado = true;

                if (logActualizaList == null || logActualizaList.isEmpty()) {
                    String msn = "No se encontraron registros para el rango de fechas suministrado.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, msn, ""));
                }
            }

        } else {
            if (fechaInicial == null && fechaFinal == null) {
                pageFirst();
                this.filtroAplicado = true;
            } else {
                String msn = "Por favor, indique las fechas a filtrar con el formato dd/mm/yyyy";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));

            }
        }

    }
    public void filtrarListaV2() {
        registrosFiltrados = false;
        filtroAplicado = false;
        filtrarV2 = false;
        activarInputFiltros();
        if (troncal != null || tipoTecnologia != null || nombreArchivo != null || departamento != null || ciudad != null || centroPoblado != null || usuarioCreacion != null) {
            this.filtrarV2 = true;
            pageFirst();
            this.registrosFiltrados = true;
            this.filtroAplicado = true;

            if (logActualizaList == null || logActualizaList.isEmpty()) {
                String msn = "No se encontraron registros para el rango de fechas suministrado.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, msn, ""));
            }


        } else {
            if (fechaInicial == null && fechaFinal == null) {
                pageFirst();
                this.filtroAplicado = true;
            } else {
                String msn = "Por favor, indique las fechas a filtrar con el formato dd/mm/yyyy";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));

            }
        }
    }

    public void fechaAlterada() {
        filtroAplicado = false;
        rangoFechasValido = false;

        if (fechaInicial == null && fechaFinal == null) {
            rangoFechasValido = true;
        } else {

            if (fechaInicial != null && fechaFinal != null) {

                if (fechaInicial.compareTo(fechaFinal) <= 0) {

                    rangoFechasValido = true;
                }

            }

        }
    }

    /**
     * Método que carga el detalle del log selecionado
     * @param logActualizaMasterSelect
     */
    public void cargarDetalleLog(LogActualizaMaster logActualizaMasterSelect) {
        try {
            logNapListSelecionado = new LogActualizaMaster();
            logNapListSelecionado = logActualizaMasterSelect;
            isInfoProcesada = String.valueOf(false);
            registrosNoProcesados = new ArrayList<>();
            registrosProcesados = new ArrayList<>();
            totalRegistros = new ArrayList<>();
            labelHeaderTableLists = "DETALLE_" + logNapListSelecionado.getNombreArchivo();
            totalRegistros = logActualizaDetalleMglFacadeLocal.findDetalleByIdMasterAndEstado("A", logNapListSelecionado);
            registrosNoProcesados = logActualizaDetalleMglFacadeLocal.findDetalleByIdMasterAndEstado("E", logNapListSelecionado);
            registrosProcesados = logActualizaDetalleMglFacadeLocal.findDetalleByIdMasterAndEstado("P", logNapListSelecionado);
            String msn = "Total registros: " + ((totalRegistros == null) ? 0 : totalRegistros.size())+ "\n"
                    + "Procesados: " + ((registrosProcesados == null) ? 0: registrosProcesados.size()) + "\n"
                    + "No procesados: " + ((registrosNoProcesados == null) ? 0 : registrosNoProcesados.size());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

        } catch (ApplicationException e) {
            LOGGER.error("Error en cargarDetalleLog. Al cargar la informacion detalle del log seleccionado ", e);
        }
    }

    private void aplicarFiltros() {
        try {
            if (troncal == null && tipoTecnologia == null && nombreArchivo == null && departamento == null && ciudad == null && centroPoblado == null && usuarioCreacion == null) {
                filtroV2 = null;
            } else {
                if (troncal != null && tipoTecnologia == null && nombreArchivo == null && departamento == null && ciudad == null && centroPoblado == null && usuarioCreacion == null) {
                    filtroV2 = "troncal";
                }
                if (troncal == null && tipoTecnologia != null && nombreArchivo == null && departamento == null && ciudad == null && centroPoblado == null && usuarioCreacion == null) {
                    filtroV2 = "tipoTecnologia";
                }
                if (troncal == null && tipoTecnologia == null && nombreArchivo != null && departamento == null && ciudad == null && centroPoblado == null && usuarioCreacion == null) {
                    filtroV2 = "nombreArchivo";
                }
                if (troncal == null && tipoTecnologia == null && nombreArchivo == null && departamento != null && ciudad == null && centroPoblado == null && usuarioCreacion == null) {
                    filtroV2 = "departamento";
                }
                if (troncal == null && tipoTecnologia == null && nombreArchivo == null && departamento == null && ciudad != null && centroPoblado == null && usuarioCreacion == null) {
                    filtroV2 = "ciudad";
                }
                if (troncal == null && tipoTecnologia == null && nombreArchivo == null && departamento == null && ciudad == null && centroPoblado != null && usuarioCreacion == null) {
                    filtroV2 = "centroPoblado";
                }
                if (troncal == null && tipoTecnologia == null && nombreArchivo == null && departamento == null && ciudad == null && centroPoblado == null && usuarioCreacion != null) {
                    filtroV2 = "usuarioCreacion";
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en aplicarFiltros", e);
        }
    }
    
    private void activarInputFiltros() {
        if (troncal == null || troncal.isEmpty()) {
            troncal = null;
        } else {
            inputTroncal = true;
        }
        if (tipoTecnologia == null || tipoTecnologia.isEmpty()) {
            tipoTecnologia = null;
        } else {
            inputTipoTecnologia = true;
        }
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            nombreArchivo = "";
        } else {
            inputNombreArchivo = true;
        }
        if (departamento == null || departamento.isEmpty()) {
            departamento = null;
        } else {
            inputDepartamento = true;
        }
        if (ciudad == null || ciudad.isEmpty()) {
            ciudad = null;
        } else {
            inputCiudad = true;
        }
        if (centroPoblado == null || centroPoblado.isEmpty()) {
            centroPoblado = null;
        } else {
            inputCentroPoblado = true;
        }
        if (usuarioCreacion == null || usuarioCreacion.isEmpty()) {
            usuarioCreacion = "";
        } else {
            inputUsuarioCreacion = true;
        }
        if (!inputTroncal && !inputTipoTecnologia && !inputNombreArchivo && !inputDepartamento && !inputCiudad && !inputCentroPoblado && !inputUsuarioCreacion) {
           inputTroncal = true; 
           inputTipoTecnologia = true; 
           inputNombreArchivo = true; 
           inputDepartamento = true; 
           inputCiudad = true; 
           inputCentroPoblado = true; 
           inputUsuarioCreacion = true; 
        }
    }
    
    private void llenarFiltrosSelect() {
        try {
            logActualizaTroncalList = logActualizaMglFacadeLocal.getListLogActualizaGroupByTroncal();
            logActualizaTipoTecList = logActualizaMglFacadeLocal.getListLogActualizaGroupByTipoTec();
            logActualizaDepartamentoList = logActualizaMglFacadeLocal.getListLogActualizaGroupByDepartamento();
            logActualizaCiudadList = logActualizaMglFacadeLocal.getListLogActualizaGroupByCiudad();
            logActualizaCentroPobladoList = logActualizaMglFacadeLocal.getListLogActualizaGroupByCentroPoblado();
        } catch (ApplicationException e) {
            LOGGER.error("Error en llenarFiltrosSelect.", e);
        }
        
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<LogActualizaMaster> getLogActualizaList() {
        return logActualizaList;
    }

    public void setLogActualizaList(List<LogActualizaMaster> logActualizaList) {
        this.logActualizaList = logActualizaList;
    }

    public List<LogActualizaDetalle> getRegistrosNoProcesados() {
        return registrosNoProcesados;
    }

    public void setRegistrosNoProcesados(List<LogActualizaDetalle> registrosNoProcesados) {
        this.registrosNoProcesados = registrosNoProcesados;
    }

    public List<LogActualizaDetalle> getRegistrosProcesados() {
        return registrosProcesados;
    }

    public void setRegistrosProcesados(List<LogActualizaDetalle> registrosProcesados) {
        this.registrosProcesados = registrosProcesados;
    }

    public List<LogActualizaDetalle> getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(List<LogActualizaDetalle> totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
    
    public String getLabelHeaderTableLists() {
        return labelHeaderTableLists;
    }

    public void setLabelHeaderTableLists(String labelHeaderTableLists) {
        this.labelHeaderTableLists = labelHeaderTableLists;
    }

    public String getIsInfoProcesada() {
        return isInfoProcesada;
    }

    public void setIsInfoProcesada(String isInfoProcesada) {
        this.isInfoProcesada = isInfoProcesada;
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

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getTroncal() {
        return troncal;
    }

    public void setTroncal(String troncal) {
        this.troncal = troncal;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }
    
    public boolean isRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(boolean registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public boolean isFiltroAplicado() {
        return filtroAplicado;
    }

    public void setFiltroAplicado(boolean filtroAplicado) {
        this.filtroAplicado = filtroAplicado;
    }

    public boolean isBotonFiltar() {
        return botonFiltar;
    }

    public void setBotonFiltar(boolean botonFiltar) {
        this.botonFiltar = botonFiltar;
    }

    public boolean isFiltrarV2() {
        return filtrarV2;
    }

    public void setFiltrarV2(boolean filtrarV2) {
        this.filtrarV2 = filtrarV2;
    }

    public boolean isInputTroncal() {
        return inputTroncal;
    }

    public void setInputTroncal(boolean inputTroncal) {
        this.inputTroncal = inputTroncal;
    }

    public boolean isInputTipoTecnologia() {
        return inputTipoTecnologia;
    }

    public void setInputTipoTecnologia(boolean inputTipoTecnologia) {
        this.inputTipoTecnologia = inputTipoTecnologia;
    }

    public boolean isInputNombreArchivo() {
        return inputNombreArchivo;
    }

    public void setInputNombreArchivo(boolean inputNombreArchivo) {
        this.inputNombreArchivo = inputNombreArchivo;
    }

    public boolean isInputDepartamento() {
        return inputDepartamento;
    }

    public void setInputDepartamento(boolean inputDepartamento) {
        this.inputDepartamento = inputDepartamento;
    }

    public boolean isInputCiudad() {
        return inputCiudad;
    }

    public void setInputCiudad(boolean inputCiudad) {
        this.inputCiudad = inputCiudad;
    }

    public boolean isInputCentroPoblado() {
        return inputCentroPoblado;
    }

    public void setInputCentroPoblado(boolean inputCentroPoblado) {
        this.inputCentroPoblado = inputCentroPoblado;
    }

    public boolean isInputUsuarioCreacion() {
        return inputUsuarioCreacion;
    }

    public void setInputUsuarioCreacion(boolean inputUsuarioCreacion) {
        this.inputUsuarioCreacion = inputUsuarioCreacion;
    }
    
    public boolean isRangoFechasValido() {
        return rangoFechasValido;
    }

    public void setRangoFechasValido(boolean rangoFechasValido) {
        this.rangoFechasValido = rangoFechasValido;
    }

    public List<LogActualizaMaster> getLogActualizaTroncalList() {
        return logActualizaTroncalList;
    }

    public void setLogActualizaTroncalList(List<LogActualizaMaster> logActualizaTroncalList) {
        this.logActualizaTroncalList = logActualizaTroncalList;
    }

    public List<LogActualizaMaster> getLogActualizaTipoTecList() {
        return logActualizaTipoTecList;
    }

    public void setLogActualizaTipoTecList(List<LogActualizaMaster> logActualizaTipoTecList) {
        this.logActualizaTipoTecList = logActualizaTipoTecList;
    }

    public List<LogActualizaMaster> getLogActualizaDepartamentoList() {
        return logActualizaDepartamentoList;
    }

    public void setLogActualizaDepartamentoList(List<LogActualizaMaster> logActualizaDepartamentoList) {
        this.logActualizaDepartamentoList = logActualizaDepartamentoList;
    }

    public List<LogActualizaMaster> getLogActualizaCiudadList() {
        return logActualizaCiudadList;
    }

    public void setLogActualizaCiudadList(List<LogActualizaMaster> logActualizaCiudadList) {
        this.logActualizaCiudadList = logActualizaCiudadList;
    }

    public List<LogActualizaMaster> getLogActualizaCentroPobladoList() {
        return logActualizaCentroPobladoList;
    }

    public void setLogActualizaCentroPobladoList(List<LogActualizaMaster> logActualizaCentroPobladoList) {
        this.logActualizaCentroPobladoList = logActualizaCentroPobladoList;
    }
        
    public boolean isBotonError() {
        return botonError;
    }

    public void setBotonError(boolean botonError) {
        this.botonError = botonError;
    }

}
