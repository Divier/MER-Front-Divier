package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaExtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaExtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
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
import java.util.Collections;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ManagedBean(name = "tablasBasicasMglBean")
@ViewScoped
@Log4j2
public class TablasBasicasMglBean implements Serializable {

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;

    @EJB
    private CmtTipoBasicaExtMglFacadeLocal cmtTipoBasicaExtMglFacadeLocal;
    @EJB
    private CmtBasicaExtMglFacadeLocal cmtBasicaExtMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

      //Opciones agregadas para Rol
    private final String TBLBASEXP = "TBLBASEXP";
    private final String TBLBASCRE = "TBLBASCRE";
    private final String TBLBASCOD = "TBLBASCOD";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtBasicaMgl TablasBasicasMgl = null;
    private List<CmtBasicaMgl> tablasBasicasMglList;
    private List<CmtTipoBasicaMgl> TablasTipoBasicasMglList;
    private String idSqlSelected;
    private String filtroTablaBasicaSelected;
    private String codigo;
    private String tablaTipoBasicaMgl;
    private boolean guardado;
    private String certificado;
    public BigDecimal idTablasBasicas;
    private boolean otroEstado;
    private List<String> brList;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private String message;
    private CmtTipoBasicaMgl cmtTipoBasicaMglSelected;
    private boolean renderAuditoria = false;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionBasica = "";
    private String tipoBasicaDescripcion = "";
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
    private List<CmtBasicaMgl> modulosTblBasica;

    private SecurityLogin securityLogin;
    private Boolean crearBasica = Boolean.FALSE;
    private Boolean editarBasica = Boolean.FALSE;
    private Boolean borrarBasica = Boolean.FALSE;
    private Boolean cargar = true;
    private Boolean tblEstCM;
    private List<CmtTipoBasicaExtMgl> listCamposTipoBasicaExt;
    private List<CmtBasicaExtMgl> listCamposBasicaExtMostrar;
    private List<CmtBasicaExtMgl> basicaExtMgls;
    private List<String> seleccionYN;
    private HashMap<String, Object> paramsValidacionGuardar;

    public TablasBasicasMglBean() {
        try {
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

            } catch (IOException ex) {
                LOGGER.error("Se generea error en ValidarDirUnoAUnoMBean class ...", ex);
            }

            CmtBasicaMgl idTablasBasicasMgl = (CmtBasicaMgl) session.getAttribute("idTablasBasicasMgl");
            CmtBasicaMgl nd = TablasBasicasMgl;

            if (idTablasBasicasMgl != null) {
                session.removeAttribute("idTablasBasicasMgl");
                TablasBasicasMgl = idTablasBasicasMgl;
                idTablasBasicasMgl.setJustificacion("");
            } else {
                cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
                TablasBasicasMgl = new CmtBasicaMgl();
                TablasBasicasMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                TablasBasicasMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);
                TablasBasicasMgl.setBasicaId(BigDecimal.ZERO);
                TablasBasicasMgl.setActivado("N");
                TablasBasicasMgl.setCodigoBasica("");
                if (cmtTipoBasicaMglSelected != null) {
                    TablasBasicasMgl.setAbreviatura(cmtTipoBasicaMglSelected.getInicialesAbreviatura());
                }
            }
      
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }

    }

    @PostConstruct
    public void fillSqlList() {

        try {
            cmtBasicaMglFacade.setUser(usuarioVT, perfilVT);
            tablasBasicasMglList = new ArrayList<>();
            TablasTipoBasicasMglList = new ArrayList<>();
            TablasTipoBasicasMglList = cmtTipoBasicaMglFacade.findAllSinInfoAdi((Constant.COMPLEMENTOS_TIPO_BASICA));
            cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
            if (cmtTipoBasicaMglSelected == null) {
                cmtTipoBasicaMglSelected = new CmtTipoBasicaMgl();
                cmtTipoBasicaMglSelected.setNombreTipo("");
            } else {
                tablaTipoBasicaMgl = cmtTipoBasicaMglSelected.getTipoBasicaId().toString();
                validarAcciones(cmtTipoBasicaMglSelected.getNombreTipo());
                filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
                filtroConjsultaBasicasDto.setIdTipoBasica(cmtTipoBasicaMglSelected.getTipoBasicaId());
                tablasBasicasMglList = cmtBasicaMglFacade.findByFiltro(filtroConjsultaBasicasDto);
                if (TablasBasicasMgl.getTipoBasicaObj() != null && 
                        TablasBasicasMgl.getTipoBasicaObj().getTipoBasicaId() != null) {
                    if (TablasBasicasMgl.getCodigoBasica().equals("")) {
                        TablasBasicasMgl.setCodigoBasica(
                            cmtBasicaMglFacade.buscarUltimoCodigoNumerico(cmtTipoBasicaMglSelected));
                            if (isNumeric(TablasBasicasMgl.getCodigoBasica())) {
                                int num = Integer.parseInt(TablasBasicasMgl.getCodigoBasica());
                                int sum = (num + 1);
                                BigDecimal cod = new BigDecimal(sum);
                                TablasBasicasMgl.setCodigoBasica(cod.toString());
                            }
                    }
                }
            }
            if (cmtTipoBasicaMglSelected.getTipoBasicaId() != null) {
                List<CmtTipoBasicaExtMgl> camposTipoBasicaExtMglList = cmtTipoBasicaExtMglFacadeLocal.findAll();
                List<BigDecimal> listaTipoBaasicaExt = new ArrayList<>();
                for (CmtTipoBasicaExtMgl campos : camposTipoBasicaExtMglList) {
                    if (!listaTipoBaasicaExt.contains(campos.getIdTipoBasica().getTipoBasicaId())) {
                        listaTipoBaasicaExt.add(campos.getIdTipoBasica().getTipoBasicaId());
                    }
                }
                if (listaTipoBaasicaExt.contains(cmtTipoBasicaMglSelected.getTipoBasicaId())) {
                    getCamposAdic();
                }
            }

        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en el método TablasBasicasMglBean. " + e.getMessage(), e, LOGGER);
        }

    }

    public String goActualizar() {
        try {

            session.setAttribute("btnAct", "noEntrar");
            TablasBasicasMgl = (CmtBasicaMgl) dataTable.getRowData();
            session.setAttribute("idTablaTipoBasicaMgl", cmtTipoBasicaMglSelected);
            session.setAttribute("idTablasBasicasMgl", TablasBasicasMgl);
            return "tablasBasicasMglView";
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            return null;
        }
    }

    public String nuevoTablasBasicasMgl() {
        if (tablaTipoBasicaMgl == null
                || tablaTipoBasicaMgl.isEmpty()
                || tablaTipoBasicaMgl.equals("0")) {
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

        return "tablasBasicasMglView";
    }
    
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + nfe.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }

    public void getCamposAdic() {
        try {
            listCamposTipoBasicaExt = new ArrayList<>();
            listCamposTipoBasicaExt = cmtTipoBasicaExtMglFacadeLocal.getCamposAdic(cmtTipoBasicaMglSelected);
            CmtBasicaExtMgl[] arraysBasicaExtMgls = null;
            if (TablasBasicasMgl.getBasicaId() == null) {
                for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                    CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                    basicaExtMgl.setIdTipoBasicaExt(extMgl);
                    basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                    basicaExtMgls.add(basicaExtMgl);
                }
            } else {
                basicaExtMgls = TablasBasicasMgl.getListCmtBasicaExtMgl();

                if (basicaExtMgls != null) {
                    arraysBasicaExtMgls = new CmtBasicaExtMgl[basicaExtMgls.size()];
                    int i = 0;
                    for (CmtBasicaExtMgl basicaExtMgl : basicaExtMgls) {
                        arraysBasicaExtMgls[i] = basicaExtMgl;
                        i++;
                    }
                    Arrays.sort(arraysBasicaExtMgls);
                }

                if (basicaExtMgls == null || basicaExtMgls.isEmpty()) {
                    basicaExtMgls = new ArrayList<>();
                    for (CmtTipoBasicaExtMgl extMgl : listCamposTipoBasicaExt) {
                        CmtBasicaExtMgl basicaExtMgl = new CmtBasicaExtMgl();
                        basicaExtMgl.setIdTipoBasicaExt(extMgl);
                        basicaExtMgl.setFechaCreacion(new Date());
                        basicaExtMgl.setEstadoRegistro(1);
                        basicaExtMgl.setUsuarioCreacion(usuarioVT);
                        basicaExtMgl.setPerfilCreacion(perfilVT);
                        basicaExtMgl.setIdBasicaObj(TablasBasicasMgl);
                        basicaExtMgls.add(basicaExtMgl);
                    }

                }
                if (arraysBasicaExtMgls != null && arraysBasicaExtMgls.length > 0) {
                    basicaExtMgls = new ArrayList<>();
                    for (int j = 0; j < arraysBasicaExtMgls.length; j++) {

                        basicaExtMgls.add(arraysBasicaExtMgls[j]);
                    }
                }

                TablasBasicasMgl.setListCmtBasicaExtMgl(basicaExtMgls);
            }

            cargar = false;
            tblEstCM = true;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: getCamposAdic(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: getCamposAdic(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarCamposAdic() {
        try {
            listCamposBasicaExtMostrar = cmtBasicaExtMglFacadeLocal.getCamposBasicaExt(TablasBasicasMgl);
            cargar = false;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: mostrarCamposAdic(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: mostrarCamposAdic(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void crearTablasBasicasMgl() {
        try {
            if (TablasBasicasMgl.getCodigoBasica() != null
                    && !TablasBasicasMgl.getCodigoBasica().isEmpty()
                    && !TablasBasicasMgl.getCodigoBasica().equalsIgnoreCase("0")) {
                int tamanio = TablasBasicasMgl.getCodigoBasica().length();
                if (tamanio > TablasBasicasMgl.getTipoBasicaObj().getLongitudCodigo()) {
                    int num = Integer.parseInt(TablasBasicasMgl.getCodigoBasica());
                    int res = (num - 1);

                    String msg = "El código es máximo de " + TablasBasicasMgl.getTipoBasicaObj().getLongitudCodigo()
                            + " caracteres, por favor corregir en la Base de datos el codigo anterior " + res;
                    throw new ApplicationException(msg);
                }
            }
            
            if (TablasBasicasMgl.getDescripcion() == null || TablasBasicasMgl.getDescripcion().trim().isEmpty() || TablasBasicasMgl.getDescripcion().length() > 200) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtTipoBasicaMglSelected = (CmtTipoBasicaMgl) session.getAttribute("idTablaTipoBasicaMgl");
            TablasBasicasMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);

            if (TablasBasicasMgl.getTipoBasicaObj() == null) {
                String msn = "Se debe seleccionar un tipo de tabla Basica en la pantalla anterior.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            TablasBasicasMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            TablasBasicasMgl.setBasicaId(null);
            TablasBasicasMgl.setCodigoBasica(TablasBasicasMgl.getCodigoBasica().toUpperCase());
            if(TablasBasicasMgl.getIdentificadorInternoApp() == null 
                    || TablasBasicasMgl.getIdentificadorInternoApp().isEmpty()){
                TablasBasicasMgl.setIdentificadorInternoApp("NA");
            }
            if (validarConfigDuplicadaGuardar() && validarConfigBasicaExt()) {
                String msn = "La configuracion que esta ingresando ya existe ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtBasicaMglFacade.create(TablasBasicasMgl);
                setGuardado(false);
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                session.setAttribute("message", message);
            }
           
        } catch (ApplicationException e) {
            setGuardado(false);
            TablasBasicasMgl.setBasicaId(BigDecimal.ZERO);
            TablasBasicasMgl.setCodigoBasica("");
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: crearTablasBasicasMgl(). " + e.getMessage(), e, LOGGER);
        }
    }
    
    public void actualizarlTablasBasicasMgl() {
        try {
            if (TablasBasicasMgl.getDescripcion() == null || TablasBasicasMgl.getDescripcion().trim().isEmpty() || TablasBasicasMgl.getDescripcion().length() > 200) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (TablasBasicasMgl.getJustificacion() == null || TablasBasicasMgl.getJustificacion().trim().isEmpty() || TablasBasicasMgl.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            TablasBasicasMgl.setListCmtBasicaExtMgl(basicaExtMgls);
            if (validarConfigDuplicadaGuardar() && validarConfigBasicaExt()) {
                String msn = "La configuracion que esta ingresando ya existe ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtBasicaMglFacade.update(TablasBasicasMgl);
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                session.setAttribute("message", message);
            }
           
        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: actualizarlTablasBasicasMgl(). " + e.getMessage(), e, LOGGER);
        }

    }

    public void eliminarlTablasBasicasMgl() {

        try {
            if (TablasBasicasMgl.getDescripcion() == null || TablasBasicasMgl.getDescripcion().trim().isEmpty() || TablasBasicasMgl.getDescripcion().length() > 200) {
                String msn = "Campo descripcion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (TablasBasicasMgl.getJustificacion() == null || TablasBasicasMgl.getJustificacion().trim().isEmpty() || TablasBasicasMgl.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            cmtBasicaMglFacade.delete(TablasBasicasMgl);
            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: eliminarlTablasBasicasMgl(). " + e.getMessage(), e, LOGGER);
        }

    }

    public String exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Tablas Básicas")) return StringUtils.EMPTY;

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteAsc");
            //Blank workbook
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
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_TablasBasicas_" + formato.format(new Date()) + ".xlsx\"");
            try (OutputStream output = response.getOutputStream()) {
                workbook.write(output);
                output.flush();
            }
            fc.responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: exportExcel(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: exportExcel(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void consultarByTipoTablaBasica() {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = new CmtTipoBasicaMgl();
            if (tablaTipoBasicaMgl != null
                    && !tablaTipoBasicaMgl.isEmpty()
                    && !tablaTipoBasicaMgl.equals("0")) {
                
                cmtTipoBasicaMgl.setTipoBasicaId(new BigDecimal(tablaTipoBasicaMgl));
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
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: consultarByTipoTablaBasica(). " + e.getMessage(), e, LOGGER);
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
           FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: findByFiltro(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarAuditoria(CmtBasicaMgl tablasBasicasMgl) {
        if (tablasBasicasMgl != null) {
            try {
                informacionAuditoria = cmtBasicaMglFacade.construirAuditoria(tablasBasicasMgl);
                descripcionBasica = tablasBasicasMgl.getNombreBasica();
                tipoBasicaDescripcion = tablasBasicasMgl.getTipoBasicaObj().getNombreTipo();
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

    public String volverList() {

        return "tablasBasicasMglListView";
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
            FacesUtil.mostrarMensajeError("Error al TablasBasicasMglBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
     public boolean validarConfigDuplicadaGuardar() {
        boolean duplicado = false;
        try {
            List<CmtBasicaMgl> cmtCmtBasicaMglList;
            camposValidarGuardar();
            cmtCmtBasicaMglList = cmtBasicaMglFacade.findByAllFields(paramsValidacionGuardar);
            if (cmtCmtBasicaMglList != null && !cmtCmtBasicaMglList.isEmpty()) {
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
            paramsValidacionGuardar.put("nombreBasica", TablasBasicasMgl.getNombreBasica());
            paramsValidacionGuardar.put("tipoBasicaId", TablasBasicasMgl.getTipoBasicaObj().getTipoBasicaId());
            paramsValidacionGuardar.put("abreviatura",  TablasBasicasMgl.getAbreviatura());
             paramsValidacionGuardar.put("activado",  TablasBasicasMgl.getActivado());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));
        }
    }
    public boolean validarConfigBasicaExt() throws ApplicationException {
        boolean duplicado = false;

        if (TablasBasicasMgl.getListCmtBasicaExtMgl() != null && !TablasBasicasMgl.getListCmtBasicaExtMgl().isEmpty()) {
            // lista guardada en base de la vista 
            List<CmtBasicaExtMgl> listaBasicaExtVista = TablasBasicasMgl.getListCmtBasicaExtMgl();
            if (listaBasicaExtVista != null && !listaBasicaExtVista.isEmpty()) {
                List<CmtBasicaExtMgl> cloned_listaBasicaExtVista = new ArrayList<>(listaBasicaExtVista);
                List<String> listaStringVista = new ArrayList<>();
                for (CmtBasicaExtMgl elementosSeleccionados : cloned_listaBasicaExtVista) {
                    listaStringVista.add(elementosSeleccionados.getValorExtendido());
                }
                Collections.sort(listaStringVista);
                // lista de la bd 
                List<String> listaStringBD = new ArrayList<>();
                List<CmtBasicaExtMgl> listaBasicaExtBd = cmtBasicaExtMglFacadeLocal.getCamposBasicaExt(TablasBasicasMgl);
                for (CmtBasicaExtMgl elementosBd : listaBasicaExtBd) {
                    listaStringBD.add(elementosBd.getValorExtendido());
                }
                Collections.sort(listaStringBD);
                if (listaStringBD.equals(listaStringVista)) {
                    duplicado = true;
                }
            }
        }

        return duplicado;
    }

    public CmtBasicaMgl getTablasBasicasMgl() {
        return TablasBasicasMgl;
    }

    public void setTablasBasicasMgl(CmtBasicaMgl TablasBasicasMgl) {
        this.TablasBasicasMgl = TablasBasicasMgl;
    }

    public List<CmtBasicaMgl> getTablasBasicasMglList() {
        return tablasBasicasMglList;
    }

    public void setTablasBasicasMglList(List<CmtBasicaMgl> tablasBasicasMglList) {
        this.tablasBasicasMglList = tablasBasicasMglList;
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

    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return TablasTipoBasicasMglList;
    }

    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> TablasTipoBasicasMglList) {
        this.TablasTipoBasicasMglList = TablasTipoBasicasMglList;
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

    public CmtTipoBasicaMgl getCmtTipoBasicaMglSelected() {
        return cmtTipoBasicaMglSelected;
    }

    public void setCmtTipoBasicaMglSelected(CmtTipoBasicaMgl cmtTipoBasicaMglSelected) {
        this.cmtTipoBasicaMglSelected = cmtTipoBasicaMglSelected;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public FiltroConjsultaBasicasDto getFiltroConjsultaBasicasDto() {
        return filtroConjsultaBasicasDto;
    }

    public void setFiltroConjsultaBasicasDto(FiltroConjsultaBasicasDto filtroConjsultaBasicasDto) {
        this.filtroConjsultaBasicasDto = filtroConjsultaBasicasDto;
    }

    public List<CmtBasicaMgl> getModulosTblBasica() {
        return modulosTblBasica;
    }

    public void setModulosTblBasica(List<CmtBasicaMgl> modulosTblBasica) {
        this.modulosTblBasica = modulosTblBasica;
    }

    public Boolean getCrearBasica() {
        return crearBasica;
    }

    public Boolean getEditarBasica() {
        return editarBasica;
    }

    public Boolean getBorrarBasica() {
        return borrarBasica;
    }

    public Boolean getCargar() {
        return cargar;
    }

    public void setCargar(Boolean cargar) {
        this.cargar = cargar;
    }

    public Boolean getTblEstCM() {
        return tblEstCM;
    }

    public void setTblEstCM(Boolean tblEstCM) {
        this.tblEstCM = tblEstCM;
    }

    public List<CmtTipoBasicaExtMgl> getListCamposTipoBasicaExt() {
        return listCamposTipoBasicaExt;
    }

    public void setListCamposTipoBasicaExt(List<CmtTipoBasicaExtMgl> listCamposTipoBasicaExt) {
        this.listCamposTipoBasicaExt = listCamposTipoBasicaExt;
    }

    public List<CmtBasicaExtMgl> getBasicaExtMgls() {
        return basicaExtMgls;
    }

    public void setBasicaExtMgls(List<CmtBasicaExtMgl> basicaExtMgls) {
        this.basicaExtMgls = basicaExtMgls;
    }

    public List<String> getSeleccionYN() {
        return seleccionYN;
    }

    public void setSeleccionYN(List<String> seleccionYN) {
        this.seleccionYN = seleccionYN;
    }

    public List<CmtBasicaExtMgl> getListCamposBasicaExtMostrar() {
        return listCamposBasicaExtMostrar;
    }

    public void setListCamposBasicaExtMostrar(List<CmtBasicaExtMgl> listCamposBasicaExtMostrar) {
        this.listCamposBasicaExtMostrar = listCamposBasicaExtMostrar;
    }

    
       // Validar Opciones por Rol
    
    public boolean validarOpcionCrear() {
        return validarEdicionRol(TBLBASCRE);
    }
    
      public boolean validarOpcionExportar() {
        return validarEdicionRol(TBLBASEXP);
    }
      
    public boolean validarOpcionlinkCod() {
        return validarEdicionRol(TBLBASCOD);
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
