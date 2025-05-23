package co.com.claro.mgl.mbeans.cm;
import co.com.claro.mer.email.domain.models.enums.EmailTemplatesHtmlEnum;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtExtendidaTipoTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "mantenimientoTablasBean")
@ViewScoped
@Log4j2
public class MantenimientoTablasBean {

    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtExtendidaTipoTrabajoMglFacadeLocal cmtExtendidaTipoTrabajoMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    private final String TBLMANCRE = "TBLMANCRE";
    private final String TBLMANCREAREG = "TBLMANCREAREG";
    private final String TBLMANCOD = "TBLMANCOD";
    
    private String message;
    private String usuarioVT = null;
    private int perfilVT;
    private CmtTipoBasicaMgl tipoBasicaTbl;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private List<String> modulosSelected;
    private boolean cargar = false;
    private List<CmtBasicaMgl> modulosTblBasica;
    private String selectedEstado;
    private List<CmtTipoBasicaMgl> tablasTipoBasicasMglList;

    private String tablaTipoBasicaSelected;
    private List modulosRender;
    private boolean tblNodo = false;
    private boolean tblTecnologia = false;
    private boolean tipoViviendaActive = false;
    private boolean blank = false;
    private boolean tipoViviendaSelected;
    private boolean soporteSelected;
    private NodoMgl nodoMgl;
    private CmtTipoBasicaMgl cmtTipoBasicaMglSelected;
    private boolean btnActivo;
    private List<CmtBasicaMgl> tablasBasicasMglList;
    private FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();

    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private Boolean crearBasica = Boolean.FALSE;
    private Boolean editarBasica = Boolean.FALSE;
    private Boolean borrarBasica = Boolean.FALSE;
    private CmtBasicaMgl TablasBasicasMgl = null;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionBasica = "";
    private String tipoBasicaDescripcion = "";
    private boolean renderAuditoria = false;
    private SecurityLogin securityLogin;
    public BigDecimal idTablasBasicas;
    private boolean guardado;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();

    public MantenimientoTablasBean() {
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
        } catch (IOException e) {
         FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoProyecto
                    = cmtTipoBasicaMglFacade.findByCodigoInternoApp(Constant.TIPO_BASICA_MODULOS_APLICAR_TIPO_BASICA);
            modulosTblBasica = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            tablasTipoBasicasMglList = new ArrayList<>();
            tablasTipoBasicasMglList = cmtTipoBasicaMglFacade.findByComplemento(Constant.COMPLEMENTOS_TIPO_BASICA);
            boolean regresarMto = false;
            if (session.getAttribute("regresaMto") != null) {

                regresarMto = (Boolean) session.getAttribute("regresaMto");

                if (regresarMto) {
                    cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
                    session.removeAttribute("regresaMto");
                }
            }

            if (cmtTipoBasicaMglSelected == null) {
                cmtTipoBasicaMglSelected = new CmtTipoBasicaMgl();
                cmtTipoBasicaMglSelected.setNombreTipo("");
            } else {
                tablaTipoBasicaSelected = cmtTipoBasicaMglSelected.getTipoBasicaId().toString();
            }

            consultarByTipoTablaBasica();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarTipoVivienda() {
        tipoViviendaSelected = true;
    }

    public void panelActive() {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
            if (tablaTipoBasicaSelected != null
                    && !tablaTipoBasicaSelected.isEmpty()
                    && !tablaTipoBasicaSelected.equals("0")) {
                cmtTipoBasicaMgl.setTipoBasicaId(new BigDecimal(tablaTipoBasicaSelected));
                cmtTipoBasicaMglSelected = cmtTipoBasicaMglFacade.findById(cmtTipoBasicaMgl);
                if (cmtTipoBasicaMglSelected.getComplemento().contains("tblTec")) {
                    cargar = false;
                    tblTecnologia = true;
                    tblNodo = false;
                } else if (cmtTipoBasicaMglSelected.getComplemento().contains("tblNodo")) {
                    tblNodo = true;
                    tblTecnologia = false;
                } else {
                    blank = true;
                    tblNodo = false;
                    tblTecnologia = false;
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void guardarCamposAdicionalesMgl() {
        try {
            if (nodoMgl == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            session.setAttribute("message", message);
        }
    }

    public void consultarByTipoTablaBasica() {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
            CmtBasicaMgl idTablasBasicasMgl = (CmtBasicaMgl) session.getAttribute("tablasBasicasMgl");

            if (idTablasBasicasMgl != null) {
                tablaTipoBasicaSelected = idTablasBasicasMgl.getTipoBasicaObj().getTipoBasicaId().toString();
                session.removeAttribute("tablasBasicasMgl");
            }

            if (tablaTipoBasicaSelected != null
                    && !tablaTipoBasicaSelected.isEmpty()
                    && !tablaTipoBasicaSelected.equals("0")) {
                cmtTipoBasicaMgl.setTipoBasicaId(new BigDecimal(tablaTipoBasicaSelected));
                cmtTipoBasicaMglSelected = cmtTipoBasicaMglFacade.findById(cmtTipoBasicaMgl);
                filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
                filtroConjsultaBasicasDto.setIdTipoBasica(cmtTipoBasicaMgl.getTipoBasicaId());
                tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
                validarAcciones(cmtTipoBasicaMglSelected.getNombreTipo());
                if (tablasBasicasMglList != null && !tablasBasicasMglList.isEmpty()) {
                    pageFirst();
                }
            } else {
                cmtTipoBasicaMgl.setTipoBasicaId(null);
                tablasBasicasMglList = new ArrayList<>();
            }
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void findByFiltro() {
        try {
            tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
            if (tablasBasicasMglList != null && !tablasBasicasMglList.isEmpty()) {
                pageFirst();
            }
        } catch (ApplicationException e) {
            String msn = "Se presento un al aplcar filtro";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String goActualizar() {
        try {
            TablasBasicasMgl = (CmtBasicaMgl) dataTable.getRowData();
            session.setAttribute("idTablaTipoBasicaMgl", cmtTipoBasicaMglSelected);
            session.setAttribute("idTablasBasicasMgl", TablasBasicasMgl);
            session.setAttribute("getComplemento", cmtTipoBasicaMglSelected.getComplemento());
            return "infoAdiMantenimientoView";
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    public void mostrarAuditoria(CmtBasicaMgl tablasBasicasMgl) {
        if (tablasBasicasMgl != null) {
            try {
                informacionAuditoria = cmtBasicaMglFacade.construirAuditoria(tablasBasicasMgl);
                descripcionBasica = tablasBasicasMgl.getNombreBasica();
                tipoBasicaDescripcion = tablasBasicasMgl.getTipoBasicaObj().getNombreTipo();
                renderAuditoria = true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                String msn = "Se presenta error al mostrar la auditoria: " + ex.getMessage();
                LOGGER.error(msn);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } else {
            String msn = "No se encontro informacion de auditoria.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
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
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CmtTipoBasicaMgl getTipoBasicaTbl() {
        return tipoBasicaTbl;
    }

    public void setTipoBasicaTbl(CmtTipoBasicaMgl tipoBasicaTbl) {
        this.tipoBasicaTbl = tipoBasicaTbl;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public List<String> getModulosSelected() {
        return modulosSelected;
    }

    public void setModulosSelected(List<String> modulosSelected) {
        this.modulosSelected = modulosSelected;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public List<CmtBasicaMgl> getModulosTblBasica() {
        return modulosTblBasica;
    }

    public void setModulosTblBasica(List<CmtBasicaMgl> modulosTblBasica) {
        this.modulosTblBasica = modulosTblBasica;
    }

    public String getSelectedEstado() {
        return selectedEstado;
    }

    public void setSelectedEstado(String selectedEstado) {
        this.selectedEstado = selectedEstado;
    }

    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return tablasTipoBasicasMglList;
    }

    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> tablasTipoBasicasMglList) {
        this.tablasTipoBasicasMglList = tablasTipoBasicasMglList;
    }

    public String getTablaTipoBasicaSelected() {
        return tablaTipoBasicaSelected;
    }

    public void setTablaTipoBasicaSelected(String tablaTipoBasicaSelected) {
        this.tablaTipoBasicaSelected = tablaTipoBasicaSelected;
    }

    public List getModulosRender() {
        return modulosRender;
    }

    public void setModulosRender(List modulosRender) {
        this.modulosRender = modulosRender;
    }

    public boolean isTblTecnologia() {
        return tblTecnologia;
    }

    public void setTblTecnologia(boolean tblTecnologia) {
        this.tblTecnologia = tblTecnologia;
    }

    public boolean isTipoViviendaActive() {
        return tipoViviendaActive;
    }

    public void setTipoViviendaActive(boolean tipoViviendaActive) {
        this.tipoViviendaActive = tipoViviendaActive;
    }

    public boolean isTipoViviendaSelected() {
        return tipoViviendaSelected;
    }

    public void setTipoViviendaSelected(boolean tipoViviendaSelected) {
        this.tipoViviendaSelected = tipoViviendaSelected;
    }

    public boolean isSoporteSelected() {
        return soporteSelected;
    }

    public void setSoporteSelected(boolean soporteSelected) {
        this.soporteSelected = soporteSelected;
    }

    public boolean isTblNodo() {
        return tblNodo;
    }

    public void setTblNodo(boolean tblNodo) {
        this.tblNodo = tblNodo;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public CmtTipoBasicaMgl getCmtTipoBasicaMglSelected() {
        return cmtTipoBasicaMglSelected;
    }

    public void setCmtTipoBasicaMglSelected(CmtTipoBasicaMgl cmtTipoBasicaMglSelected) {
        this.cmtTipoBasicaMglSelected = cmtTipoBasicaMglSelected;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public List<CmtBasicaMgl> getTablasBasicasMglList() {
        return tablasBasicasMglList;
    }

    public void setTablasBasicasMglList(List<CmtBasicaMgl> tablasBasicasMglList) {
        this.tablasBasicasMglList = tablasBasicasMglList;
    }

    public FiltroConjsultaBasicasDto getFiltroConjsultaBasicasDto() {
        return filtroConjsultaBasicasDto;
    }

    public void setFiltroConjsultaBasicasDto(FiltroConjsultaBasicasDto filtroConjsultaBasicasDto) {
        this.filtroConjsultaBasicasDto = filtroConjsultaBasicasDto;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
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

    public Boolean getCrearBasica() {
        return crearBasica;
    }

    public void setCrearBasica(Boolean crearBasica) {
        this.crearBasica = crearBasica;
    }

    public Boolean getEditarBasica() {
        return editarBasica;
    }

    public void setEditarBasica(Boolean editarBasica) {
        this.editarBasica = editarBasica;
    }

    public Boolean getBorrarBasica() {
        return borrarBasica;
    }

    public void setBorrarBasica(Boolean borrarBasica) {
        this.borrarBasica = borrarBasica;
    }

    public CmtBasicaMgl getTablasBasicasMgl() {
        return TablasBasicasMgl;
    }

    public void setTablasBasicasMgl(CmtBasicaMgl TablasBasicasMgl) {
        this.TablasBasicasMgl = TablasBasicasMgl;
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

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public BigDecimal getIdTablasBasicas() {
        return idTablasBasicas;
    }

    public void setIdTablasBasicas(BigDecimal idTablasBasicas) {
        this.idTablasBasicas = idTablasBasicas;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String nuevoTablasBasicasMgl() {
        if (tablaTipoBasicaSelected == null
                || tablaTipoBasicaSelected.isEmpty()
                || tablaTipoBasicaSelected.equals("0")) {
            String msn = "Se debe seleccionar un tipo de tabla Basica.";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return null;
        }
        TablasBasicasMgl = null;
        TablasBasicasMgl = new CmtBasicaMgl();
        TablasBasicasMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);
        TablasBasicasMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
        session.setAttribute("idTablaTipoBasicaMgl", cmtTipoBasicaMglSelected);
        setGuardado(true);
        return "infoAdiMantenimientoView";
    }

    public String exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            String nombreExport = "Exportar MANTENIMIENTO TABLAS";
            if (blockReportServBean.isReportGenerationBlock(nombreExport)) return StringUtils.EMPTY;
            notifyExportInProgress(nombreExport);
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteAsc");
            //Blank workbook

            if (cmtTipoBasicaMglSelected.getNombreTipo().
                    equalsIgnoreCase(Constant.TECNOLOGIA)) {
                
                String[] objArr = {"Código", "Abreviatura", "Nombre Tabla", "Descripción", "Estado"};
                int rownum = 0;
                int cellnum = 0;
                for (CmtBasicaMgl a : tablasBasicasMglList) {
                    Row row = sheet.createRow(rownum);
                    if (rownum == 0) {
                        for (String c : objArr) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                        }
                        rownum++;
                    }
                    cellnum = 0;
                    row = sheet.createRow(rownum);
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue(a.getCodigoBasica());
                    Cell cell2 = row.createCell(cellnum++);
                    cell2.setCellValue(a.getAbreviatura());
                    Cell cell3 = row.createCell(cellnum++);
                    cell3.setCellValue(a.getNombreBasica());
                    Cell cell4 = row.createCell(cellnum++);
                    cell4.setCellValue(a.getDescripcion());
                    Cell cell5 = row.createCell(cellnum++);
                    cell5.setCellValue(a.getEstadoRegistro() == 1 ? "Activo" : "Inactivo");
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseL = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseL.reset();
                responseL.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                responseL.setHeader(
                        "Content-Disposition", "attachment; filename=\""
                        + "Reporte_TablasMantenimiento_" + formato.format(new Date()) + ".xlsx\"");
                OutputStream output = responseL.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                //Es tipo de trabajo reporte con extendida
                
                String[] objArr = {"Código", "Abreviatura", "Nombre Tabla", "Descripción", "Estado", "Codigo Localización", "Cómunidad", "CodigoRR"};
                int rownum = 0;
                int cellnum = 0;
                List<CmtExtendidaTipoTrabajoMgl> tiposTrabajosList;
                
                int expLonPag = 1000;
                long totalPag = cmtExtendidaTipoTrabajoMglFacadeLocal.findExtendidasTrabajoCount();
                
                for (int exPagina = 1; exPagina <= ((totalPag / expLonPag)
                        + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {

                    tiposTrabajosList = cmtExtendidaTipoTrabajoMglFacadeLocal.findExtendidasTipoTrabajo(exPagina, expLonPag);

                    if (tiposTrabajosList != null && !tiposTrabajosList.isEmpty()) {
                        
                        for (CmtExtendidaTipoTrabajoMgl a : tiposTrabajosList) {
                            Row row = sheet.createRow(rownum);
                            if (rownum == 0) {
                                for (String c : objArr) {
                                    Cell cell = row.createCell(cellnum++);
                                    cell.setCellValue(c);
                                }
                                rownum++;
                            }
                            cellnum = 0;
                            row = sheet.createRow(rownum);
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(a.getTipoTrabajoObj().getCodigoBasica());
                            Cell cell2 = row.createCell(cellnum++);
                            cell2.setCellValue(a.getTipoTrabajoObj().getAbreviatura());
                            Cell cell3 = row.createCell(cellnum++);
                            cell3.setCellValue(a.getTipoTrabajoObj().getNombreBasica());
                            Cell cell4 = row.createCell(cellnum++);
                            cell4.setCellValue(a.getTipoTrabajoObj().getDescripcion());
                            Cell cell5 = row.createCell(cellnum++);
                            cell5.setCellValue(a.getEstadoRegistro() == 1 ? "Activo" : "Inactivo");
                            Cell cell6 = row.createCell(cellnum++);
                            cell6.setCellValue(a.getLocationCodigo());
                            Cell cell7 = row.createCell(cellnum++);
                            cell7.setCellValue(a.getComunidadRrObj().getNombreComunidad());
                            Cell cell8 = row.createCell(cellnum++);
                            cell8.setCellValue(a.getComunidadRrObj().getCodigoRr());
                            rownum++;
                        }
                        System.gc();
                    }

                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseL = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseL.reset();
                responseL.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                responseL.setHeader(
                        "Content-Disposition", "attachment; filename=\""
                        + "Reporte_TablasMantenimiento_" + formato.format(new Date()) + ".xlsx\"");
                OutputStream output = responseL.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            }

        } catch (IOException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en MantenimientoTablasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Notifica el proceso de exportación en progreso
     *
     * @param nombreExport {@link String}
     * @author Gildardo Mora
     */
    private void notifyExportInProgress(String nombreExport) {
        EmailDetailsReportDto detailsReportDto = new EmailDetailsReportDto();
        detailsReportDto.setReportName(nombreExport);
        detailsReportDto.setReportDescription("Exportar datos para el tipo de tabla básica "
                + cmtTipoBasicaMglSelected.getNombreTipo());
        detailsReportDto.setModule("Mantenimiento de tablas");
        detailsReportDto.setSessionUser(usuarioLogin.getUsuario());
        detailsReportDto.setSessionUserName(usuarioLogin.getNombre());
        detailsReportDto.setMessage("Mantenimiento de tablas : Exportación de datos para el Tipo de tabla básica: .. "
                + cmtTipoBasicaMglSelected.getNombreTipo());
        detailsReportDto.setTemplateHtml(EmailTemplatesHtmlEnum.DETAILS_REPORTS);
        blockReportServBean.notifyReportGeneration(detailsReportDto);
    }

    public void pageFirst() {
        dataTable.setFirst(0);
        if (dataTable.getRows() > 0) {
            numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
        }
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

    public void irPagina() {
        String value = numPagina;
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
    }

    
      // Validar Opciones por Rol
    
    public boolean validarOpcionCrear() {
        return validarEdicionRol(TBLMANCRE);
    }
    
    public boolean validarOpcionCrearRegistro() {
        return validarEdicionRol(TBLMANCREAREG);
    }
    
    public boolean validarOpcionLinkCod() {
        return validarEdicionRol(TBLMANCOD);
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
