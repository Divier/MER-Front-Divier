package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.CmtReporteComComercialDto;
import co.com.claro.mgl.dtos.CmtReporteCompromisoComercialDto;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "reportesCompComercialBean")
@ViewScoped
@Log4j2
public class ReporteCompComercialBean {

    private String message;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean cargar;
    private Date fechaInicio;
    private Date fechaFinal;
    private SecurityLogin securityLogin;
    private int perfilVT = 0;
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;

    private List<CuentaMatrizCompComercialDto> listCmtCuentaMatrizMglCompComercial;
    private BigDecimal estadoSelected;
    private List<CmtReporteComComercialDto> listCmtReporteDetalladoDto;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina;
    private boolean panelPintarPaginado = false;
    private boolean pintarPaginado = true;
    private boolean panelExportar = false;
    private boolean cantRegistros = false;
    private boolean mensajeExcel = false;
    private boolean mensajeCvsTxt = false;
    private boolean btnExcel = false;
    private boolean btnCvsTxt = false;
    private boolean btnVolver = false;
    private boolean btnReporte = true;
    private boolean panelFormulario = true;
    private List<Integer> paginaList;
    private int page = 0;
    private int numRegAProcesar;
    private int numRegProcesados;
    private int numRegTotal;
    private static final String[] NOM_COLUMNAS = {"Id CM",
        "Numero de CM", "Nombre CM", "No Torres", "Direccion CM",
        "Comunidad", "Division", "Zona", "CentroPoblado", "Departamento", "Fecha de Habilitacion", "Meta",
        "Cumplimiento", "Tecnologia", "Nodo", "Estrato", "Estado"
    };
    
    private FacesContext facesContextTh = FacesContext.getCurrentInstance();
    private HttpSession sessionTh = (HttpSession) facesContextTh.getExternalContext().getSession(true);
    private boolean finalizado = false;
    private boolean iniciado = false;
    private List<CmtBasicaMgl> listCmtSolicitudCmMgl;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private BigDecimal tecnologia;
    private String nodo;
    private BigDecimal estrato;
    private List<CmtBasicaMgl> listaTablaBasicaEstratos;
    private Integer refresh;
    private static int REFRESH_MAX = 100000;
    private static int REFRESH_MIN = 10000;
    private boolean finish;
    private boolean progress;
    private int minutos;
    private String xlsMaxReg;
    private String usuarioProceso;
    private String csvMaxReg;
    private String txtMaxReg;
    private int numRegistrosResultado ;
    private int cantMaxRegExcel;
    private int cantMaxRegCvs; 
    private int cantRegConsulta;    
    private final String VALIDARREPORCOMPCOM = "VALIDARREPORCOMPCOM";
    
 

    public ReporteCompComercialBean() {
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


        } catch (IOException e) {
           FacesUtil.mostrarMensajeError("Error al ReporteCompComercial. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReporteCompComercial. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void fillSqlList() {

        try {
            this.minutos = tiempoReporte(Constant.TIEMPO_REPORTE);
            btnVolver = true;
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_SOLICITUD);
            listCmtSolicitudCmMgl = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            CmtTipoBasicaMgl tipoBasicaTecnologias;
            tipoBasicaTecnologias = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologias);
            CmtTipoBasicaMgl listaEstratos;
            listaEstratos = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTRATO);
            listaTablaBasicaEstratos = basicaMglFacadeLocal.findByTipoBasica(listaEstratos);
             // cant de registros configurados en bd
 
            cantMaxRegExcel = Integer.parseInt(parametrosFacade.findParametroMgl(Constant.REPORTE_EXCEL_REG_MAX).getParValor());
            cantMaxRegCvs = Integer.parseInt(parametrosFacade.findParametroMgl(Constant.REPORTE_CVS_TXT_REG_MAX).getParValor());
            cantRegConsulta = Integer.parseInt(parametrosFacade.findParametroMgl(Constant.REPORTE_REG_MAX_CONSULTA).getParValor());

        } catch (ApplicationException e) {
          FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:fillSqlList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:fillSqlList() " + e.getMessage(), e, LOGGER);
        }

    }

    public void getReporte() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Reporte Compromiso Comercial")) return;

            btnExcel = false;
            btnCvsTxt = false;
            NodoMgl nodoConsultado;
            if (!nodo.isEmpty()) {
                nodoConsultado = nodoMglFacadeLocal.findByCodigo(nodo);
                if (nodoConsultado != null && nodoConsultado.getNodTipo() != null 
                        && nodoConsultado.getNodTipo().getBasicaId() != null 
                        && nodoConsultado.getNodTipo().getBasicaId().equals(tecnologia)) {
                    obtenerReporte();
                } else {
                    btnReporte = true;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El nodo Ingresado no es de la tecnologia seleccionada ("+tecnologia+").", ""));
                }
            } else {
                obtenerReporte();
            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }
    
    
    public void obtenerReporte() {
        try {
            if (fechaInicio != null && fechaFinal != null) {
                if (validarFechas()) {

                    btnVolver = true;
                    this.refresh = REFRESH_MIN;
                    panelFormulario = true;
                    btnReporte = true;
                    listCmtCuentaMatrizMglCompComercial = new ArrayList<CuentaMatrizCompComercialDto>();
                    numRegistrosResultado = cmtCuentaMatrizMglFacadeLocal.getCuentasMatricesCompComercialCount(tecnologia, estrato, nodo, fechaInicio, fechaFinal, 0, 0, usuarioVT);

                    if (numRegistrosResultado > 0) {
                        btnExcel = true;
                        btnCvsTxt = true;
                        setProgress(true);
                        xlsMaxReg = this.numRegistrosResultado > cantMaxRegExcel ? Constant.EXPORT_XLS_MAX : "";
                        csvMaxReg = this.numRegistrosResultado > cantMaxRegCvs ? Constant.EXPORT_CSV_MAX : "";
                        txtMaxReg = this.numRegistrosResultado > cantMaxRegCvs ? Constant.EXPORT_TXT_MAX : "";
                    } else {
                        panelPintarPaginado = false;
                        setProgress(false);
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "No se encontraron resultados para la consulta.", ""));
                    }
                } else {
                    btnReporte = true;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "La fecha inicial debe ser menor que la fecha final.", ""));
                }
            } else {
                btnReporte = true;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Los campos de fecha son obligatorios", ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:obtenerReporte(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * bocanegravm metodo para resetear los parametros de busqueda
     */
    public void limpiarValores() {
        numRegAProcesar = 0;
        btnCvsTxt = false;
        btnExcel = false;
        fechaInicio = null;
        fechaFinal = null;
        estrato = null;
        nodo = null;
        tecnologia = null;
        numRegistrosResultado = 0;
    }


 

    public void volverList() {
        try {
            limpiarValores();
            setProgress(false);
            setFinish(false);
            CmtReporteCompromisoComercialDto.cleanBeforeStart();
            this.refresh = REFRESH_MAX;
            panelPintarPaginado = false;
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }



    public void exportExcel() {
        try {
            exportExcelDet();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:exportExcel(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String exportExcelDet()  {
        try {
              int expLonPag = 50;
               List<CuentaMatrizCompComercialDto> reporteOtResultList = new ArrayList();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Compromiso Comercial");
            String[] objArr = {"Id Cm", "Nombre de Cm", " Numero de Cm", "Direccion CM", "No Torres", "Comunidad", "Division", "Zona", "CentroPoblado", "Departamento",
                "Fecha Habilitacion", "Meta", "Cumplimiento", "Tecnologia", "Nodo", "Estrato", "Estado"
            };
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            int rownum = 0;
            long totalPag = numRegistrosResultado;
            int inicioRegistros = 0;
             for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {
                progress = true;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }
                reporteOtResultList = cmtCuentaMatrizMglFacadeLocal.getCuentasMatricesCompComercial(tecnologia, estrato,
                        nodo, fechaInicio, fechaFinal, inicioRegistros, expLonPag, usuarioVT);
            if (!reporteOtResultList.isEmpty()) {
                for (CuentaMatrizCompComercialDto a : reporteOtResultList) {
                    Row row = sheet.createRow(rownum);
                    int cellnum = 0;
                    if (rownum == 0) {
                        for (String c : objArr) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                        }
                        cellnum = 0;
                        rownum += 1;
                        row = sheet.createRow(rownum);
                        Cell cell1 = row.createCell(cellnum++);
                        cell1.setCellValue(a.getIdCuentaMatriz());
                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(a.getNombreCuenta());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getNumeroCuenta());
                        Cell cell18 = row.createCell(cellnum++);
                        cell18.setCellValue(a.getDireccion());
                         Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue(a.getNumeroTorres());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getComunidadArea());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDivision());
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(a.getZona());
                        Cell cell7 = row.createCell(cellnum++);
                        cell7.setCellValue(a.getCentroPoblado());
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(a.getDepartamento());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(a.getFechaCreacion() != null ? a.getFechaCreacion() : "");
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(a.getCompromisoComercial() != null ? a.getCompromisoComercial().toString()+"%" : "");
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(a.getPorcCumplido() != null ? a.getPorcCumplido().toString()+"%" : "");
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(a.getTecnologia() != null ? a.getTecnologia().toString() : "");
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(a.getNodo() != null ? a.getNodo().toString() : "");
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(a.getEstrato() != null ? a.getEstrato().toString() : "");
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(a.getEstadoCumplimiento() != null ? a.getEstadoCumplimiento().toString() : "");


                    } else {

                        Cell cell1 = row.createCell(cellnum++);
                        if (a.getIdCuentaMatriz() != null) {
                            cell1.setCellValue(a.getIdCuentaMatriz());
                        }


                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(a.getNombreCuenta());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getNumeroCuenta());
                        Cell cell18 = row.createCell(cellnum++);
                        cell18.setCellValue(a.getDireccion());
                        Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue(a.getNumeroTorres());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getComunidadArea());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDivision());
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(a.getZona());
                        Cell cell7 = row.createCell(cellnum++);
                        cell7.setCellValue(a.getCentroPoblado());
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(a.getDepartamento());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(a.getFechaCreacion() != null ? a.getFechaCreacion() : "");
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(a.getCompromisoComercial() != null ? a.getCompromisoComercial().toString()+"%" : "");
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(a.getPorcCumplido() != null ? a.getPorcCumplido().toString()+"%" : "");
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(a.getTecnologia() != null ? a.getTecnologia().toString() : "");
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(a.getNodo() != null ? a.getNodo().toString() : "");
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(a.getEstrato() != null ? a.getEstrato().toString() : "");
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(a.getEstadoCumplimiento() != null ? a.getEstadoCumplimiento().toString() : "");

                    }
                    rownum++;
                }
            }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseHttp = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseHttp.reset();
                responseHttp.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_Compromiso_Comercial" + "_" + formato.format(new Date())
                        + ".xlsx\"");
                  try (OutputStream output = response.getOutputStream()) {
                      workbook.write(output);
                      output.flush();
                  }
                fc.responseComplete();
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:exportExcelDet() " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:exportExcelDet() " + e.getMessage(), e, LOGGER);
        }
        return "OK";
    }

    public String downloadCvsTxtGen(String ext) {
           try {
              
               int expLonPag = 50;
               SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
                List<CuentaMatrizCompComercialDto> reporteOtResultList = new ArrayList<>();
               long totalPag = numRegistrosResultado;
               final StringBuffer sb = new StringBuffer();
               byte[] csvData;
               for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                   sb.append(NOM_COLUMNAS[j]);
                   if (j < NOM_COLUMNAS.length) {
                       sb.append(";");
                   }
               }

               sb.append("\n");
               String todayStr = formato.format(new Date());
               String fileName = "Reporte_Compromiso_Comercial" + "_" + todayStr + "." + ext;
               FacesContext fc = FacesContext.getCurrentInstance();
               HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
               response.setHeader("Content-disposition", "attached; filename=\""
                       + fileName + "\"");
               response.setContentType("application/force.download");
               csvData = sb.toString().getBytes();
               response.getOutputStream().write(csvData);


            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {
                
                progress = true;

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }
                
                reporteOtResultList = cmtCuentaMatrizMglFacadeLocal.getCuentasMatricesCompComercial(tecnologia, estrato, 
                        nodo, fechaInicio, fechaFinal, inicioRegistros, expLonPag,usuarioVT);
                if (reporteOtResultList != null && !reporteOtResultList.isEmpty()) {
                for (CuentaMatrizCompComercialDto cmt : reporteOtResultList) {
                     if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                    String idCm = cmt.getIdCuentaMatriz() == null ? " " : cmt.getIdCuentaMatriz();
                    sb.append(idCm);
                    sb.append(";");
                    String numeroCuenta = cmt.getNumeroCuenta() == null ? " " : cmt.getNumeroCuenta();
                    sb.append(numeroCuenta);
                    sb.append(";");
                    String nombreCm = cmt.getNombreCuenta() == null ? " " : cmt.getNombreCuenta();
                    sb.append(nombreCm);
                    sb.append(";");
                    String noTorres = cmt.getNumeroTorres() == null ? " " : cmt.getNumeroTorres();
                    sb.append(noTorres);
                    sb.append(";");
                    String direccionCM = cmt.getDireccion() == null ? " " : cmt.getDireccion();
                    sb.append(direccionCM);
                    sb.append(";");
                    String com = cmt.getComunidadArea() == null ? " " : cmt.getComunidadArea();
                    sb.append(com);
                    sb.append(";");
                    String div = cmt.getDivision() == null ? " " : cmt.getDivision();
                    sb.append(div);
                    sb.append(";");
                    String zona = cmt.getZona() == null ? " " : cmt.getZona();
                    sb.append(zona);
                    sb.append(";");
                    String centroPoblado = cmt.getCentroPoblado() == null ? " " : cmt.getCentroPoblado();
                    sb.append(centroPoblado);
                    sb.append(";");
                    String dep = cmt.getDepartamento() == null ? " " : cmt.getDepartamento();
                    sb.append(dep);
                     sb.append(";");
                    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaCreacion = String.valueOf(cmt.getFechaCreacion() != null ? cmt.getFechaCreacion() : "");
                    sb.append(fechaCreacion);
                    sb.append(";");

                    String meta = String.valueOf(cmt.getCompromisoComercial() + "%");
                    sb.append(meta);
                    sb.append(";");
                    String porcCumplido = String.valueOf(cmt.getPorcCumplido() + "%");
                    sb.append(porcCumplido);
                    sb.append(";");
                    String tec = String.valueOf(cmt.getTecnologia());
                    sb.append(tec);
                    sb.append(";");
                    String nodo = String.valueOf(cmt.getNodo());
                    sb.append(nodo);
                    sb.append(";");
                    String estrato = String.valueOf(cmt.getEstrato());
                    sb.append(estrato);
                    sb.append(";");
                    String cumplimiento = String.valueOf(cmt.getEstadoCumplimiento());
                    sb.append(cumplimiento);
                    sb.append(";");

                    sb.append("\n");
                    csvData = sb.toString().getBytes();
                    response.getOutputStream().write(csvData);
                    response.getOutputStream().flush();
                    response.flushBuffer();

                }
                System.gc();
                }
            }

            response.getOutputStream().close();
            fc.responseComplete();
             progress = false;

        } catch (IOException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void downloadCvsTxt(String ext) {
        try {
            downloadCvsTxtGen(ext);
        } catch (Exception e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn, e);
        }
    }

    public boolean validarFechas() {
           boolean respuesta = false;
        try {
            if (validarFormatoFecha(fechaInicio) && validarFormatoFecha(fechaFinal)) {
                if (fechaInicio.before(fechaFinal)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    if (fechaFinal.before(fechaInicio)) {
                        System.err.println("La Fecha Inicio es Mayor ");
                        respuesta = false;
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La fecha Inicial es Mayor a la Final.", ""));
                    } else {
                        respuesta = true;
                    }
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:validarFechas(). " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }

    /**
     * Función que valida si el formato de la fecha es correcto por el usuario
     * en pantalla.
     *
     * @author cardenaslb
     * @param fecha
     * @return 
     */
    public static boolean validarFormatoFecha(Date fecha) {

        try {
            if (fecha != null) {
                String fechaInicial = null;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicial = format.format(fecha);
                format.setLenient(false);
                format.parse(fechaInicial);
            } else {
                return false;
            }


        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:validarFormatoFecha. " + e.getMessage(), e, LOGGER);
        }
        return true;
    }
       /**
     * valbuenayf Metodo para buscar el numero tiempo de espera para borrar el
     * reporte
     *
     * @param nombreParametro
     * @return
     */
    private Integer tiempoReporte(String nombreParametro) {
        Integer numero = null;
        Integer DEFAULT = 5;
        try {
            ParametrosMgl parametrosMgl = parametrosFacade.findByAcronimoName(nombreParametro);
            if (parametrosMgl != null) {
                numero = Integer.valueOf(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error  en tiempoMaximo de ReporteCompComercial:tiempoReporte "  + e.getMessage(), e, LOGGER);
            return DEFAULT;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReporteCompComercial:tiempoReporte " + e.getMessage(), e, LOGGER);
        }
        return numero;
    }
    
    
      public boolean validarReporteCompCom() {
        return validarEdicion(VALIDARREPORCOMPCOM);
    }

      private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }  


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public Date getFechaInicio() {
       return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public BigDecimal getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(BigDecimal estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumRegAProcesar() {
       
        return numRegAProcesar;
    }

    public void setNumRegAProcesar(int numRegAProcesar) {
        this.numRegAProcesar = numRegAProcesar;
    }

  
    
    public int getNumRegProcesados() {
          numRegProcesados = CmtReporteCompromisoComercialDto.getNumeroRegistrosProcesados();
        return numRegProcesados;
    }

    public void setNumRegProcesados(int numRegProcesados) {
        this.numRegProcesados = numRegProcesados;
    }

    public boolean isPanelExportar() {
        return panelExportar;
    }

    public void setPanelExportar(boolean panelExportar) {
        this.panelExportar = panelExportar;
    }

    public boolean isCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(boolean cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public boolean isPanelPintarPaginado() {
        return panelPintarPaginado;
    }

    public void setPanelPintarPaginado(boolean panelPintarPaginado) {
        this.panelPintarPaginado = panelPintarPaginado;
    }

    public int getNumRegTotal() {
        return numRegTotal;
    }

    public void setNumRegTotal(int numRegTotal) {
        this.numRegTotal = numRegTotal;
    }

    public boolean isMensajeExcel() {
        return mensajeExcel;
    }

    public void setMensajeExcel(boolean mensajeExcel) {
        this.mensajeExcel = mensajeExcel;
    }

    public boolean isMensajeCvsTxt() {
        return mensajeCvsTxt;
    }

    public void setMensajeCvsTxt(boolean mensajeCvsTxt) {
        this.mensajeCvsTxt = mensajeCvsTxt;
    }

    public boolean isBtnExcel() {
        return btnExcel;
    }

    public void setBtnExcel(boolean btnExcel) {
        this.btnExcel = btnExcel;
    }

    public boolean isBtnCvsTxt() {
        return btnCvsTxt;
    }

    public void setBtnCvsTxt(boolean btnCvsTxt) {
        this.btnCvsTxt = btnCvsTxt;
    }

    public boolean isPanelFormulario() {
        return panelFormulario;
    }

    public void setPanelFormulario(boolean panelFormulario) {
        this.panelFormulario = panelFormulario;
    }

    public boolean isBtnVolver() {
        return btnVolver;
    }

    public void setBtnVolver(boolean btnVolver) {
        this.btnVolver = btnVolver;
    }

    public boolean isBtnReporte() {
        return btnReporte;
    }

    public void setBtnReporte(boolean btnReporte) {
        this.btnReporte = btnReporte;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public List<CuentaMatrizCompComercialDto> getListCmtCuentaMatrizMglCompComercial() {
        return listCmtCuentaMatrizMglCompComercial;
    }

    public void setListCmtCuentaMatrizMglCompComercial(List<CuentaMatrizCompComercialDto> listCmtCuentaMatrizMglCompComercial) {
        this.listCmtCuentaMatrizMglCompComercial = listCmtCuentaMatrizMglCompComercial;
    }

    public List<CmtReporteComComercialDto> getListCmtReporteDetalladoDto() {
        return listCmtReporteDetalladoDto;
    }

    public void setListCmtReporteDetalladoDto(List<CmtReporteComComercialDto> listCmtReporteDetalladoDto) {
        this.listCmtReporteDetalladoDto = listCmtReporteDetalladoDto;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public List<CmtBasicaMgl> getListaTablaBasicaEstratos() {
        return listaTablaBasicaEstratos;
    }

    public void setListaTablaBasicaEstratos(List<CmtBasicaMgl> listaTablaBasicaEstratos) {
        this.listaTablaBasicaEstratos = listaTablaBasicaEstratos;
    }

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getXlsMaxReg() {
        return xlsMaxReg;
    }

    public void setXlsMaxReg(String xlsMaxReg) {
        this.xlsMaxReg = xlsMaxReg;
    }

    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public String getTxtMaxReg() {
        return txtMaxReg;
    }

    public void setTxtMaxReg(String txtMaxReg) {
        this.txtMaxReg = txtMaxReg;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getCsvMaxReg() {
        return csvMaxReg;
    }

    public void setCsvMaxReg(String csvMaxReg) {
        this.csvMaxReg = csvMaxReg;
    }

    public int getNumRegistrosResultado() {
        return numRegistrosResultado;
    }

    public void setNumRegistrosResultado(int numRegistrosResultado) {
        this.numRegistrosResultado = numRegistrosResultado;
    }

    public int getCantMaxRegExcel() {
        return cantMaxRegExcel;
    }

    public void setCantMaxRegExcel(int cantMaxRegExcel) {
        this.cantMaxRegExcel = cantMaxRegExcel;
    }

    public int getCantMaxRegCvs() {
        return cantMaxRegCvs;
    }

    public void setCantMaxRegCvs(int cantMaxRegCvs) {
        this.cantMaxRegCvs = cantMaxRegCvs;
    }

    public int getCantRegConsulta() {
        return cantRegConsulta;
    }

    public void setCantRegConsulta(int cantRegConsulta) {
        this.cantRegConsulta = cantRegConsulta;
    }

    
}
