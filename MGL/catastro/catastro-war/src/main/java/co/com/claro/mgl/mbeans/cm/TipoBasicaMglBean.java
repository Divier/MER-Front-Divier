package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.FiltroConsultaTipoBasicaDto;
import co.com.claro.mgl.dtos.FiltroTablaTipoBasicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "tipoBasicaMglBean")
@ViewScoped
@Log4j2
public class TipoBasicaMglBean implements Serializable {

    /* ------------- Inyección de dependencias -------------- */
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @Inject
    private BlockReportServBean blockReportServBean;
    /* ------------ Atributos de la clase --------------- */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final String FORMULARIO = "TABLATIPOBASICAMGLLISTVIEW";
    
    //Opciones agregadas para Rol
    private final String TBLTPBSNV = "TBLTPBSNV";
    private final String TBLTPBSEX = "TBLTPBSEX";
    private final String TBLTPBSDE = "TBLTPBSDE";   
    private final String TBLTPBSSEL = "TBLTPBSSEL"; 
    
    public BigDecimal idTablasBasicas;
    private boolean guardado;
    private boolean otroEstado;
    private boolean renderAuditoria = false;
    private boolean pintarPaginado = true;
    private boolean cargar = true;
    private boolean btnActivo;
    private CmtBasicaMgl TablasBasicasMgl;
    private CmtTipoBasicaMgl tipoBasica;
    private FiltroConsultaTipoBasicaDto filtroConsultaTipoBasicaDto = new FiltroConsultaTipoBasicaDto();
    private FiltroTablaTipoBasicaDto filtroTablaTipoBasicaDto = new FiltroTablaTipoBasicaDto();
    private HtmlDataTable dataTable = new HtmlDataTable();
    private int perfilVT = 0;
    private int perfil = 0;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private int actual;
    private List<CmtTipoBasicaMgl> tablasTipoBasicasMglList;
    private List<String> brList;
    private List<Integer> paginaList;
    private List<String> modulosSelected;
    private List<Object> modulosRender;
    private List modulosSelectedToUpdate;
    private List<CmtBasicaMgl> modulosTblBasica;
    private List<BigDecimal> moduloSelected;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String usuarioVT = null;
    private String idSqlSelected;
    private String filtroTablaBasicaSelected;
    private String codigo;
    private String tablaTipoBasicaMgl;
    private String certificado;
    private String pageActual;
    private String numPagina = "1";
    private String message;
    private String descripcionBasica = "";
    private String tipoBasicaDescripcion = "";
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String selectedEstado;
    private SecurityLogin securityLogin;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String usuarioSesion;
    private boolean btnActualizar = false;
    private boolean btnCrear = true;
    private boolean mostrarPanelCrearModTipoBas;
    private boolean mostrarPanelListTiposBas;
    private HashMap<String, Object> paramsValidacionGuardar;
    private HashMap<String, Object> paramsValidacionActualizar;

    /**
     * Creates a new instance of TablasBasicasMglBean
     */
    public TipoBasicaMglBean() {
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            tipoBasica = new CmtTipoBasicaMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }

    }

    
    @PostConstruct
    public void fillSqlList() {

        try {
             securityLogin = new SecurityLogin(facesContext);
            usuarioSesion = securityLogin.getLoginUser();
            perfil = securityLogin.getPerfilUsuario();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
            
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacade.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_MODULOS_APLICAR_TIPO_BASICA);
            modulosTblBasica = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            //Asignamos el usuario y perfil a los FacadeLocal
            cmtTipoBasicaMglFacade.setUser(usuarioVT, perfilVT);
            String codigoCon = cmtTipoBasicaMglFacade.selectMaxCodigo();
            if (codigoCon != null) {
                tipoBasica.setCodigoTipo(codigoCon);
            }
            cargar = true;
            pintarPaginado = true;
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        }

    }

    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
            filtroConsultaTipoBasicaDto = cmtTipoBasicaMglFacade.findTablasTipoBasica(filtroTablaTipoBasicaDto, ConstantsCM.PAGINACION_DATOS, firstResult, filasPag);
            tablasTipoBasicasMglList = filtroConsultaTipoBasicaDto.getListaTablasTipoBasica();
            if (tablasTipoBasicasMglList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            if (tablasTipoBasicasMglList.size() == 1) {
                tipoBasica = tablasTipoBasicasMglList.get(0);
            }
            actual = page;
            mostrarPanelListTiposBas = true;
            mostrarPanelCrearModTipoBas = false;

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);
            return "";

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void goActualizar(CmtTipoBasicaMgl item) {
        try {
            tipoBasica = item;
            item.setJustificacion("");
            modulosRender = new ArrayList<>();
            if (tipoBasica.getModulo() != null) {
                List<String> cadenaMod = Arrays.asList(tipoBasica.getModulo().split(","));
                for (String modulos : cadenaMod) {
                    modulosRender.add(modulos);

                }
            }
            change();
            mostrarPanelCrearModTipoBas = true;
            mostrarPanelListTiposBas = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: goActualizar(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void change() {
        try {
            cargar = false;
            btnActivo = false;
            btnCrear = false;
            btnActualizar = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: change(). " + e.getMessage(), e, LOGGER);
        }
    }

      public void guardarTipoBasica() {
               try {
                if (tipoBasica.getTipoBasicaId() == null) {
                    tipoBasica.setNombreTipo(tipoBasica.getNombreTipo());
                    tipoBasica.setCodigoTipo(tipoBasica.getCodigoTipo());
                    tipoBasica.setDescripcionTipo(tipoBasica.getDescripcionTipo());
                    tipoBasica.setFechaCreacion(new Date());
                    tipoBasica.setUsuarioCreacion(usuarioVT);
                    tipoBasica.setFechaEdicion(null);
                    tipoBasica.setUsuarioEdicion("");
                    tipoBasica.setEstadoRegistro(tipoBasica.getEstadoRegistro());
                    tipoBasica.setPerfilCreacion(perfilVT);
                    tipoBasica.setPerfilEdicion(0);
                    tipoBasica.setLongitudCodigo(0);
                    tipoBasica.setTipoDatoCodigo("A");
                    tipoBasica.setInicialesAbreviatura("");
                    tipoBasica.setJustificacion(tipoBasica.getJustificacion());
                    tipoBasica.setIdentificadorInternoApp("NA");

                    CmtTipoBasicaMgl tipotablaCreated = cmtTipoBasicaMglFacade.create(tipoBasica);
                    listInfoByPage(1);
                    limpiarCamposAndCreate();
                    setGuardado(true);
                    String msn = "Proceso exitoso";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

                }

        } catch (ApplicationException e) {
            setGuardado(false);
            tipoBasica.setTipoBasicaId(BigDecimal.ZERO);
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: crearTipoTablasBasicasMgl(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void crearTipoTablasBasicasMgl() {
        if (validacionesTipoTabla()) {
            if (validarConfigDuplicadaGuardar()) {
                String msn = "La configuracion que esta ingresando ya existe ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            } else {
                guardarTipoBasica();
            }
        }
    }

    public void actualizarlTablasBasicasMgl() {
        try {
             if (validacionesTipoTabla()) {
                 if (validarConfigDuplicadaActualizar()) {
                     String msn = "La configuracion que esta ingresando ya existe ";
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                     return;
                 } else {
                     guardarTipoBasica();
                 }
             }
            tipoBasica.setFechaCreacion(new Date());
            tipoBasica.setUsuarioCreacion(usuarioVT);
            cmtTipoBasicaMglFacade.update(tipoBasica);
            listInfoByPage(1);
            limpiarCamposAndCreate();
            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: actualizarlTablasBasicasMgl(). " + e.getMessage(), e, LOGGER);
        }

    }

    public void eliminarlTablasBasicasMgl(CmtTipoBasicaMgl cmtTipoBasicaMgl) {

        try {
            if (cmtTipoBasicaMgl.getTipoBasicaId() != null) {
                cmtTipoBasicaMglFacade.delete(cmtTipoBasicaMgl);

                listInfoByPage(1);
                limpiarCamposAndCreate();
                reload();
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                session.setAttribute("message", message);
            } else {
                String msn = "Debe seleccionar el registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                session.setAttribute("message", message);
            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean: eliminarlTablasBasicasMgl. " + e.getMessage(), e, LOGGER);
        }

    }

    public void mostrarAuditoria(CmtTipoBasicaMgl cmtTipoBasicaMgl) {
        if (cmtTipoBasicaMgl != null) {
            try {
                informacionAuditoria = cmtTipoBasicaMglFacade.construirAuditoria(cmtTipoBasicaMgl);
                tipoBasicaDescripcion = cmtTipoBasicaMgl.getNombreTipo();

                renderAuditoria = true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } 
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }

    public void exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Tablas Tipos Basicas")) return;

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteAsc");
            //Blank workbook
            String[] objArr = {"Código", "Nombre", "Descripción", "Estado", "Modulo"};
            int rownum = 0;
            int cellnum = 0;
            List<String> modulos;
            for (CmtTipoBasicaMgl a : tablasTipoBasicasMglList) {

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
                cell.setCellValue(a.getTipoBasicaId().toString());
                Cell cell2 = row.createCell(cellnum++);
                cell2.setCellValue(a.getNombreTipo());
                Cell cell3 = row.createCell(cellnum++);
                cell3.setCellValue(a.getDescripcionTipo());

                Cell cell4 = row.createCell(cellnum++);
                cell4.setCellValue(a.getEstadoRegistro() == 1 ? "Activo" : "Inactivo");
                Cell cell5 = row.createCell(cellnum++);

                rownum++;
            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_TablasTipoBasicas_" + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: exportExcel(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: exportExcel(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarCampos() {
        tipoBasica.setNombreTipo("");
        tipoBasica.setCodigoTipo("");
        tipoBasica.setDescripcionTipo("");
        tipoBasica.setEstadoRegistro(1);
        tipoBasica.setJustificacion("");
        modulosRender = new ArrayList();
        modulosSelected = new ArrayList<>();
    }

    public void limpiarCamposAndCreate() {

        tipoBasica = new CmtTipoBasicaMgl();
        String codigoCon = "";
        try {
            codigoCon = cmtTipoBasicaMglFacade.selectMaxCodigo();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: limpiarCamposAndCreate(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error enTipoBasicaMglBean: limpiarCamposAndCreate(). " + e.getMessage(), e, LOGGER);
        }
        if (codigoCon != null) {
            tipoBasica.setCodigoTipo(codigoCon);
        }
        modulosRender = new ArrayList();
        modulosSelected = new ArrayList<>();
        btnCrear = true;
        btnActualizar = false;
    }

    public String volverList() {

        return "tablasBasicasMglListView";
    }

    public void ocultarAuditoria() {
        renderAuditoria = false;
    }

    public void reload() {
        try {
            FacesUtil.navegarAPagina(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
                    .getRequestURI());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: reload(). " + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: reload(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageFirst() {
        listInfoByPage(1);
    }

    public void pagePrevious() {
        irPagina(actual - 1);
    }

    public void irPagina() {
        irPagina(Integer.parseInt(numPagina));
    }

    public void pageNext() {
        irPagina(actual + 1);
    }

    private void irPagina(Integer pagina) {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = pagina;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageLast() {
        int totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);
    }

    public int getTotalPaginas() {
        if (!pintarPaginado) {
            return 1;
        }
        int totalPaginas = 0;
        try {
            FiltroConsultaTipoBasicaDto filtro
                    = cmtTipoBasicaMglFacade.findTablasTipoBasica(filtroTablaTipoBasicaDto, ConstantsCM.PAGINACION_CONTAR, 0, 0);
            Long count = filtro.getNumRegistros();
            totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoBasicaMglBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return totalPaginas;
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

 
    public boolean validacionesTipoTabla() {

        if (tipoBasica.getCodigoTipo() == null || tipoBasica.getCodigoTipo().isEmpty()
                && tipoBasica.getNombreTipo() == null || tipoBasica.getNombreTipo().isEmpty()
                && tipoBasica.getDescripcionTipo() == null || tipoBasica.getDescripcionTipo().isEmpty()
               ) {
            String msn = "Todos los campos son obligatorios";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }
        if (tipoBasica.getNombreTipo() == null
                || tipoBasica.getNombreTipo().isEmpty()) {
            String msn = "El campo nombre es Requerido";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else if (tipoBasica.getNombreTipo().length() > 50) {
            String msn = "El campo nombre debe contener un maximo  de 50 caracteres";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }

        if (tipoBasica.getCodigoTipo() == null
                || tipoBasica.getCodigoTipo().isEmpty()) {
            String msn = "El campo Código es Requerido";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else if (tipoBasica.getCodigoTipo().length() > 3) {
            String msn = "El campo Código debe contener un maximo  de 3 caracteres";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }

        if (tipoBasica.getDescripcionTipo() == null
                || tipoBasica.getDescripcionTipo().isEmpty()) {
            String msn = "La Descripción es requerida";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else if (tipoBasica.getDescripcionTipo().length() > 200) {
            String msn = "El campo Descripción debe contener un maximo  de 200 caracteres";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }

        if (tipoBasica.getJustificacion() == null
                || tipoBasica.getJustificacion().isEmpty()) {
            String msn = "El campo justificación es Requerido";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        } else if (tipoBasica.getJustificacion().length() > 200) {
            String msn = "El campo justificación debe contener un maximo  de 200 caracteres";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            return false;
        }

        return true;
    }
    
    public boolean validarConfigDuplicadaGuardar() {
        boolean duplicado = false;
        try {
            List<CmtTipoBasicaMgl> cmtCmtTipoBasicaMglList;
            camposValidarGuardar();
            cmtCmtTipoBasicaMglList = cmtTipoBasicaMglFacade.findByAllFields(paramsValidacionGuardar);
            if (!cmtCmtTipoBasicaMglList.isEmpty()) {
                duplicado = true;
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + e.getMessage(), e, LOGGER);
        }
        return duplicado;
    }

    public boolean validarConfigDuplicadaActualizar() {
        boolean duplicado = false;
        try {
            List<CmtTipoBasicaMgl> cmtCmtTipoBasicaMglList;
            camposValidarGuardar();
            cmtCmtTipoBasicaMglList = cmtTipoBasicaMglFacade.findByAllFields(paramsValidacionGuardar);
            if (!cmtCmtTipoBasicaMglList.isEmpty()) {
                duplicado = true;
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de validar la información duplicada en el guardado: " + e.getMessage(), e, LOGGER);
        }
        return duplicado;
    }

    public void camposValidarGuardar() {
        try {
            paramsValidacionGuardar = new HashMap<>();
            paramsValidacionGuardar.put("all", "1");
            paramsValidacionGuardar.put("nombreTipo", tipoBasica.getNombreTipo());
            paramsValidacionGuardar.put("estadoRegistro", tipoBasica.getEstadoRegistro());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION);
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public String volverListTiposBasicas() {

        mostrarPanelCrearModTipoBas = false;
        mostrarPanelListTiposBas = true;
        return "";
    }

    public String crearTiposBasicas() {

        mostrarPanelCrearModTipoBas = true;
        mostrarPanelListTiposBas = false;
        limpiarCamposAndCreate();
        return "";
    }
    
    public void filtrarInfo(){
        listInfoByPage(1);
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public boolean isBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(boolean btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public boolean isBtnCrear() {
        return btnCrear;
    }

    public void setBtnCrear(boolean btnCrear) {
        this.btnCrear = btnCrear;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters && Setters">
    public CmtBasicaMgl getTablasBasicasMgl() {
        return TablasBasicasMgl;
    }

    public void setTablasBasicasMgl(CmtBasicaMgl TablasBasicasMgl) {
        this.TablasBasicasMgl = TablasBasicasMgl;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getFiltroTablaBasicaSelected() {
        return filtroTablaBasicaSelected;
    }

    public void setFiltroTablaBasicaSelected(String filtroTablaBasicaSelected) {
        this.filtroTablaBasicaSelected = filtroTablaBasicaSelected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<String> getBrList() {
        return brList;
    }

    public void setBrList(List<String> brList) {
        this.brList = brList;
    }

    public BigDecimal getIdTablasBasicas() {
        return idTablasBasicas;
    }

    public void setIdTablasBasicas(BigDecimal idTablasBasicas) {
        this.idTablasBasicas = idTablasBasicas;
    }

    public String getTablaTipoBasicaMgl() {
        return tablaTipoBasicaMgl;
    }

    public void setTablaTipoBasicaMgl(String tablaBasicaMgl) {
        this.tablaTipoBasicaMgl = tablaBasicaMgl;
    }

    public boolean isOtroEstado() {
        return otroEstado;
    }

    public void setOtroEstado(boolean otroEstado) {
        this.otroEstado = otroEstado;
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

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
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

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public FiltroConsultaTipoBasicaDto getFiltroConsultaTipoBasicaDto() {
        return filtroConsultaTipoBasicaDto;
    }

    public void setFiltroConsultaTipoBasicaDto(FiltroConsultaTipoBasicaDto filtroConsultaTipoBasicaDto) {
        this.filtroConsultaTipoBasicaDto = filtroConsultaTipoBasicaDto;
    }

    public CmtTipoBasicaMgl getTipoBasica() {
        return tipoBasica;
    }

    public void setTipoBasica(CmtTipoBasicaMgl tipoBasica) {
        this.tipoBasica = tipoBasica;
    }

    public String getSelectedEstado() {
        return selectedEstado;
    }

    public void setSelectedEstado(String selectedEstado) {
        this.selectedEstado = selectedEstado;
    }

    public List<BigDecimal> getModuloSelected() {
        return moduloSelected;
    }

    public void setModuloSelected(List<BigDecimal> moduloSelected) {
        this.moduloSelected = moduloSelected;
    }

    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return tablasTipoBasicasMglList;
    }

    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> tablasTipoBasicasMglList) {
        this.tablasTipoBasicasMglList = tablasTipoBasicasMglList;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public List<String> getModulosSelected() {
        return modulosSelected;
    }

    public void setModulosSelected(List<String> modulosSelected) {
        this.modulosSelected = modulosSelected;
    }

    public List<CmtBasicaMgl> getModulosTblBasica() {
        return modulosTblBasica;
    }

    public void setModulosTblBasica(List<CmtBasicaMgl> modulosTblBasica) {
        this.modulosTblBasica = modulosTblBasica;
    }

    public List<BigDecimal> getModulosSelectedUpdate() {
        return modulosSelectedToUpdate;
    }

    public void setModulosSelectedUpdate(List<BigDecimal> modulosSelectedUpdate) {
        this.modulosSelectedToUpdate = modulosSelectedUpdate;
    }

    public List<Object> getModulosRender() {
        return modulosRender;
    }

    public void setModulosRender(List<Object> modulosRender) {
        this.modulosRender = modulosRender;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isMostrarPanelCrearModTipoBas() {
        return mostrarPanelCrearModTipoBas;
    }

    public void setMostrarPanelCrearModTipoBas(boolean mostrarPanelCrearModTipoBas) {
        this.mostrarPanelCrearModTipoBas = mostrarPanelCrearModTipoBas;
    }

    public boolean isMostrarPanelListTiposBas() {
        return mostrarPanelListTiposBas;
    }

    public void setMostrarPanelListTiposBas(boolean mostrarPanelListTiposBas) {
        this.mostrarPanelListTiposBas = mostrarPanelListTiposBas;
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

    public FiltroTablaTipoBasicaDto getFiltroTablaTipoBasicaDto() {
        return filtroTablaTipoBasicaDto;
    }

    public void setFiltroTablaTipoBasicaDto(FiltroTablaTipoBasicaDto filtroTablaTipoBasicaDto) {
        this.filtroTablaTipoBasicaDto = filtroTablaTipoBasicaDto;
    }
    
    
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(TBLTPBSNV);
    }
    
    public boolean validarOpcionExportar() {
        return validarEdicionRol(TBLTPBSEX);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(TBLTPBSDE);
    }
    
    public boolean validarOpcionSeleccionarId() {
        return validarEdicionRol(TBLTPBSSEL);
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
