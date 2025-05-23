package co.com.claro.mgl.mbeans.coberturas;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.dtos.CmtReporteFactDto;
import co.com.claro.mgl.dtos.CmtReporteFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.FactibilidadMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "reporteFactibilidadBean")
@ViewScoped
@Log4j2
public class ReporteFactibilidadBean {
    /* ----------- Inyección de dependencias ----------- */
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private FactibilidadMglFacadeLocal factibilidadMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoFacadeLocal;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;

    /* -------------- Atributos de la clase -------------------- */
    private SecurityLogin securityLogin;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private List<CmtBasicaMgl> listaTablaBasicaEstratos;
    private BigDecimal tecnologia;
    private CmtBasicaMgl basicaTecnologia;
    private Date fechaInicio;
    private Date fechaFinal;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String factibilidad;
    private BigDecimal estrato;
    private List<CmtReporteFactDto> listaReporteFact;
    private List<CmtReporteFactibilidadDto> listCmtReporteFactibilidadDto;
    private HashMap<String, Object> params;
    private int cantRegistros;
    private boolean panelPintarPaginado;
    
    private static final String[] LST_HEADER_EXCEL = {
        "ID Factibilidad", "Id Fact Vigente", "Sub Edificio",
        "Departamento", "Ciudad", "Centro Poblado", "Complemento",
        "Apartamento", "Longitud", "Latitud", "Direccion", "Fecha y Hora completa de consulta",
        "Usuario", "Id RR CCMM", "Id Mer Ccmm", "Nombre CCMM",
        "Cuentacliente", "Dirección es CCMM", "Tecnologia",
        "SDS", "Nodo MER", "Factibilidad", "Arrendatario",
        "Tiempo Instalacion UM", "Estado Tecnologia", "Nodo SitiData",
        "Nodo Aproximado", "Distancia Nodo Aproximado", "Proyecto", "Estrato"
    };

    private static final String[] LST_CAMPOSHEADER_OMITIR = {
        "fechahoraConsulta",
        "edificio",
        "DireccionCCMM",
        "cobertura",
        "nodoEstado",
        "dirDetallada",
        "fechaVencimiento"
    };
    
    private int numRegTotal;
    private boolean flag;
    List<CmtGeograficoPoliticoDto> centroPobladoCiudadList;
    private String nombreCentroPoblado;
    private List<ParametrosCalles> listEstadoFactibilidad;
    private String estadoFactSelected;
    private List<GeograficoPoliticoMgl> listaCentroPoblado;
    private String centroPobladoSelected;
    private GeograficoPoliticoMgl centroPoblado;
    private String  cantMaxRegExcel;
    private String  cantMaxRegCvs;
    private List<String> tecnologiaList;
    private List<String> factibilidadList;
    private List<String> centroPobladoList;

    //Opciones agregadas para Rol
    private final String REPORTEFACTIBILIDAD = "REPORTEFACTIBILIDAD";

    public ReporteFactibilidadBean() {
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                /* Anteriormente estaba un return, pero si vemos mas adelante no hay mas flujos o procesos */
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error IO al ReportePenetracion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePenetracion. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            CmtTipoBasicaMgl tipoBasicaTecnologias;
            tipoBasicaTecnologias = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologias);

            centroPobladoCiudadList = geograficoPoliticoFacadeLocal.findAllCentroPoblados(); 
            
            listEstadoFactibilidad = parametrosCallesFacade
                    .findByTipo(Constant.RESULTADO_GESTION_VAL_COBER);

            cantMaxRegExcel = parametrosMglFacadeLocal.findParametroMgl(Constant.REPORTE_EXCEL_REG_MAX).getParValor();
            cantMaxRegCvs = parametrosMglFacadeLocal.findParametroMgl(Constant.REPORTE_CVS_TXT_REG_MAX).getParValor();
            limpiarValores();
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        }
    }

    public void getReporte() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Reporte de Factibilidad")) return;

            buscar();
           
            if (validarFechas()) {
                if (centroPobladoList != null && centroPobladoList.isEmpty()) {
                    String msn = "El campo centro poblado es obligatorio ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                }else{
                    cantRegistros = factibilidadMglFacadeLocal.countgetReporte(params);
                    
                    if (cantRegistros == 0) {
                        String msn = "No se encontró ningún registro para la consulta realizada ";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }

                }

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al Factibilizar. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al Factibilidad. " + e.getMessage(), e, LOGGER);
        }
    }
    
    private String getHeaderDocument() {
        StringBuilder headers = new StringBuilder("");
        for (int j = 0; j < LST_HEADER_EXCEL.length; j++) {
            headers.append(LST_HEADER_EXCEL[j]);
            if (j < LST_HEADER_EXCEL.length) {
                headers.append(";");
            }
        }
        headers.append("\n");
        return headers.toString();
    }

    
        public boolean validarFechas() {
             boolean respuesta = false;
            try {
                if (validarFormatoFecha(fechaInicio) && validarFormatoFecha(fechaFinal)) {
                    if (fechaInicio.before(fechaFinal)) {
                        respuesta = true;
                    } else {
                        if (fechaFinal.before(fechaInicio)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La Fecha Inicio no puede ser mayor a la Fecha Fin", ""));
                        }
                        respuesta = !(fechaFinal.before(fechaInicio));
                    }
                } else {
                     respuesta = true;
                }
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
            }

        return respuesta;
    }
        
            
        public static boolean validarFormatoFecha(Date fecha){
            try {
                if (fecha != null) {
                    String fechaInicial;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    fechaInicial = format.format(fecha);
                    format.setLenient(false);
                    format.parse(fechaInicial);
                } else {
                    return false;
                }
            } catch (ParseException e) {
				String msg = "Se produjo un error al momento de ejecutar el método 'validarFormatoFecha' " 
                                        + e.getMessage();
				LOGGER.error(msg,e);
                return false;
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
            }
        return  true;
    }

    public void buscar() {
        try {
            params = new HashMap<>();

            params.put("all", "1");
            params.put("tecnologia", this.tecnologiaList);
            params.put("estadoFactSelected", this.factibilidadList);
            params.put("centroPoblado", this.centroPobladoList);
            params.put("fechaInicio", this.fechaInicio);
            params.put("fechaFinal", this.fechaFinal);
         
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al armar la consulta de CM.", ""));
        }
    }

    public String exportExcelOld() throws ApplicationException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("FACTIBILIDAD");
            
            int rownum = 0;
            if (listCmtReporteFactibilidadDto != null && !listCmtReporteFactibilidadDto.isEmpty()) {
                for (CmtReporteFactibilidadDto report : listCmtReporteFactibilidadDto) {
                    Row row = sheet.createRow(rownum);
                    int cellnum = 0;
                    if (rownum == 0) {
                        for (String c : LST_HEADER_EXCEL) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                        }
                        cellnum = 0;
                        rownum += 1;
                        row = sheet.createRow(rownum);
                        Cell cell1 = row.createCell(cellnum++);
                        cell1.setCellValue(report.getIdFactibilidad());
                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(report.getIdFactibilidadVigente());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(report.getSubEdificio());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(report.getDepartamento());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(report.getCiudad() != null ? report.getCiudad() : "");
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(report.getCentroPoblado());
                        Cell cel17 = row.createCell(cellnum++);
                        cel17.setCellValue(report.getComplemento() != null ? report.getComplemento() : "");
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(report.getApartamento());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(report.getCoordenadaLatitud());
                        Cell cell10 = row.createCell(cellnum++);
                        cell10.setCellValue(report.getCoordenadalongitud());
                        Cell cell11 = row.createCell(cellnum++);
                        cell11.setCellValue(report.getDireccionCompleta());
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(report.getFechaHoraConsultaReporte());
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(report.getUsuario() != null ? report.getUsuario() : "");
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(report.getIdRRCCMM());
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(report.getIdMerCcmm());
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(report.getNombreCCMM());
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(report.getCuentacliente() != null ? report.getCuentacliente() : "");
                        Cell cell118 = row.createCell(cellnum++);
                        cell118.setCellValue(report.getIsCm() != null ? report.getIsCm() : "");
                        Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue(report.getTecnologia() != null ? report.getTecnologia() : "");
                        Cell cell20 = row.createCell(cellnum++);
                        cell20.setCellValue(report.getsDS() != null ? report.getsDS() : "");
                        Cell cell21 = row.createCell(cellnum++);
                        cell21.setCellValue(report.getNodoCobertura() != null ? report.getNodoCobertura() : "");
                        Cell cell122 = row.createCell(cellnum++);
                        cell122.setCellValue(report.getFactibilidad());
                        Cell cell123 = row.createCell(cellnum++);
                        cell123.setCellValue(report.getArrendatario() != null ? report.getArrendatario() : "");
                        Cell cell124 = row.createCell(cellnum++);
                        cell124.setCellValue(report.getTiempoInstalacionUM() != null ? report.getTiempoInstalacionUM() : "");
                        Cell cell125 = row.createCell(cellnum++);
                        cell125.setCellValue(report.getEstadoTecnologia() != null ? report.getEstadoTecnologia() : "");
                        Cell cell126 = row.createCell(cellnum++);
                        cell126.setCellValue(report.getNodoSitiData() != null ? report.getNodoSitiData() : "");
                        Cell cell127 = row.createCell(cellnum++);
                        cell127.setCellValue(report.getNodoAproximado() != null ? report.getNodoAproximado() : "");
                        Cell cell128 = row.createCell(cellnum++);
                        cell128.setCellValue(report.getDistanciaNodoAproximado() != null ? report.getDistanciaNodoAproximado() + " Mts" : "");
                        Cell cell129 = row.createCell(cellnum++);
                        cell129.setCellValue(report.getProyecto() != null ? report.getProyecto() : "");
                        Cell cell130 = row.createCell(cellnum++);
                        if (report.getEstrato() == null) {
                            cell130.setCellValue("NG");
                        } else {
                            int valEstrato = Integer.parseInt(String.valueOf(report.getEstrato()));
                            if (valEstrato <= 6 && valEstrato >= 0) {
                                cell130.setCellValue(String.valueOf(report.getEstrato()));
                            } else {
                                cell130.setCellValue("NG");
                            }

                        }

                    } else {

                        Cell cell1 = row.createCell(cellnum++);
                        if (report.getIdFactibilidad() != null) {
                            cell1.setCellValue(report.getIdFactibilidad());
                        }

                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(report.getIdFactibilidadVigente());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(report.getSubEdificio());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(report.getDepartamento());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(report.getCiudad() != null ? report.getCiudad() : "");
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(report.getCentroPoblado());
                        Cell cel17 = row.createCell(cellnum++);
                        cel17.setCellValue(report.getComplemento() != null ? report.getComplemento() : "");
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(report.getApartamento());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(report.getCoordenadaLatitud());
                        Cell cell10 = row.createCell(cellnum++);
                        cell10.setCellValue(report.getCoordenadalongitud());
                        Cell cell11 = row.createCell(cellnum++);
                        cell11.setCellValue(report.getDireccionCompleta());
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(report.getFechaHoraConsultaReporte());
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(report.getUsuario() != null ? report.getUsuario() : "");
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(report.getIdRRCCMM());
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(report.getIdMerCcmm());
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(report.getNombreCCMM());
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(report.getCuentacliente() != null ? report.getCuentacliente() : "");
                        Cell cell118 = row.createCell(cellnum++);
                        cell118.setCellValue(report.getIsCm() != null ? report.getIsCm() : "");
                        Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue(report.getTecnologia() != null ? report.getTecnologia() : "");
                        Cell cell20 = row.createCell(cellnum++);
                        cell20.setCellValue(report.getsDS() != null ? report.getsDS() : "");
                        Cell cell21 = row.createCell(cellnum++);
                        cell21.setCellValue(report.getNodoCobertura() != null ? report.getNodoCobertura() : "");
                        Cell cell122 = row.createCell(cellnum++);
                        cell122.setCellValue(report.getFactibilidad());
                        Cell cell123 = row.createCell(cellnum++);
                        cell123.setCellValue(report.getArrendatario() != null ? report.getArrendatario() : "");
                        Cell cell124 = row.createCell(cellnum++);
                        cell124.setCellValue(report.getTiempoInstalacionUM() != null ? report.getTiempoInstalacionUM() : "");
                        Cell cell125 = row.createCell(cellnum++);
                        cell125.setCellValue(report.getEstadoTecnologia() != null ? report.getEstadoTecnologia() : "");
                        Cell cell126 = row.createCell(cellnum++);
                        cell126.setCellValue(report.getNodoSitiData() != null ? report.getNodoSitiData() : "");
                        Cell cell127 = row.createCell(cellnum++);
                        cell127.setCellValue(report.getNodoAproximado() != null ? report.getNodoAproximado() : "");
                        Cell cell128 = row.createCell(cellnum++);
                        cell128.setCellValue(report.getDistanciaNodoAproximado() != null ? report.getDistanciaNodoAproximado() + " Mts" : "");
                        Cell cell129 = row.createCell(cellnum++);
                        cell129.setCellValue(report.getProyecto() != null ? report.getProyecto() : "");
                        Cell cell130 = row.createCell(cellnum++);
                        if (report.getEstrato() == null) {
                            cell130.setCellValue("NG");
                        } else {
                            int valEstrato = Integer.parseInt(String.valueOf(report.getEstrato()));
                            if (valEstrato <= 6 && valEstrato >= 0) {
                                cell130.setCellValue(String.valueOf(report.getEstrato()));
                            } else {
                                cell130.setCellValue("NG");
                            }

                        }
                    }
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_Factibilidad" + "_" + formato.format(new Date())
                        + ".xlsx\"");
                try (OutputStream output = response.getOutputStream()) {
                    workbook.write(output);
                    output.flush();
                }
                fc.responseComplete();
            } else {
                panelPintarPaginado = false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron registros para el reporte", ""));
            }
        } catch (IOException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error al ReporteFactibilidad. " + e.getMessage(), e, LOGGER);
        }
        return "OK";
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

    public String downloadCvsTxtGen(String ext) {
           try {
                StringBuilder sb = new StringBuilder();
               SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
               if (!listCmtReporteFactibilidadDto.isEmpty()) {
                   int numeroRegistros = listCmtReporteFactibilidadDto.size();
                   byte[] csvData;
                   if (numeroRegistros > 0) {
                       sb.append(this.getHeaderDocument());

                for (CmtReporteFactibilidadDto cmt : listCmtReporteFactibilidadDto) {

                        String idFactibilidad = cmt.getIdFactibilidad() == null ? " " : cmt.getIdFactibilidad();
                        sb.append(idFactibilidad);
                        sb.append(";");
                        String idFactVigente = cmt.getIdFactibilidadVigente()== null ? " " : cmt.getIdFactibilidadVigente();
                        sb.append(idFactVigente);
                        sb.append(";");
                        String subEdificio = cmt.getSubEdificio() == null ? " " : cmt.getSubEdificio();
                        sb.append(subEdificio);
                        sb.append(";");
                        String departamento = cmt.getDepartamento() == null ? " " : cmt.getDepartamento();
                        sb.append(departamento);
                        sb.append(";");
                        String ciudad = cmt.getCiudad()== null ? " " : cmt.getCiudad();
                        sb.append(ciudad);
                        sb.append(";");
                        String centroPoblado1 = cmt.getCentroPoblado()== null ? " " : cmt.getCentroPoblado();
                        sb.append(centroPoblado1);
                        sb.append(";");
                        String complemento = cmt.getComplemento() == null ? " " : cmt.getComplemento();
                        sb.append(complemento);
                        sb.append(";");
                        String apartamento =  cmt.getApartamento() == null ? " " : cmt.getApartamento();
                        sb.append(apartamento);
                        sb.append(";");
                        String coordenadaLatitud = cmt.getCoordenadaLatitud() == null ? " " : cmt.getCoordenadaLatitud();
                        sb.append(coordenadaLatitud);
                        sb.append(";");
                        String coordenadalongitud = cmt.getCoordenadalongitud() == null ? " " : cmt.getCoordenadalongitud();
                        sb.append(coordenadalongitud);
                        sb.append(";");
                        String direccionCompleta = cmt.getDireccionCompleta() == null ? " " : cmt.getDireccionCompleta();
                        sb.append(direccionCompleta);
                        sb.append(";");
                        String fechahoraConsulta = cmt.getFechahoraConsulta().toString() == null ? " " : cmt.getFechahoraConsulta().toString();
                        sb.append(fechahoraConsulta);
                        sb.append(";");
                        String usuario = cmt.getUsuario() == null ? " " : cmt.getUsuario();
                        sb.append(usuario);
                        sb.append(";");
                        String idRRCCMM = cmt.getIdRRCCMM() == null ? " " : cmt.getIdRRCCMM();
                        sb.append(idRRCCMM);
                        sb.append(";");
                        String idMerCcmm = cmt.getIdMerCcmm() == null ? " " : cmt.getIdMerCcmm();
                        sb.append(idMerCcmm);
                        sb.append(";");
                        String nombreCCMM =  cmt.getNombreCCMM() == null ? " " : cmt.getNombreCCMM();
                        sb.append(nombreCCMM);
                        sb.append(";");
                        String cuentacliente = cmt.getCuentacliente() == null ? " " : cmt.getCuentacliente();
                        sb.append(cuentacliente);
                        sb.append(";");
                        String IsCm = cmt.getIsCm() == null ? " " : cmt.getIsCm();
                        sb.append(IsCm);
                        sb.append(";");
                        String tecnologia1 = cmt.getTecnologia() == null ? " " : cmt.getTecnologia();
                        sb.append(tecnologia1);
                        sb.append(";");
                        String sDS = cmt.getsDS() == null ? " " : cmt.getsDS();
                        sb.append(sDS);
                        sb.append(";");
                        String nodoCobertura = cmt.getNodoCobertura() == null ? " " : cmt.getNodoCobertura();
                        sb.append(nodoCobertura);
                        sb.append(";");
                        String factibilidad1 = cmt.getFactibilidad() == null ? " " : cmt.getFactibilidad();
                        sb.append(factibilidad1);
                        sb.append(";");
                        String arrendatario = cmt.getArrendatario() == null ? " " : cmt.getArrendatario();
                        sb.append(arrendatario);
                        sb.append(";");
                        String tiempoInstalacionUM = cmt.getTiempoInstalacionUM() == null ? " " : cmt.getTiempoInstalacionUM();
                        sb.append(tiempoInstalacionUM);
                        sb.append(";");
                        String estadoTecnologia = cmt.getEstadoTecnologia() == null ? " " : cmt.getEstadoTecnologia();
                        sb.append(estadoTecnologia);
                        sb.append(";");
                        /* brief 98062 */
                        String nodoSitiData = cmt.getNodoSitiData() == null ? " " : cmt.getNodoSitiData();
                        sb.append(nodoSitiData);
                        /* Cierre Brief 98062*/
                        sb.append(";");
                        String nodoAproximado = cmt.getNodoAproximado() == null ? " " : cmt.getNodoAproximado();
                        sb.append(nodoAproximado);
                        sb.append(";");
                        String distNodoAproximado = cmt.getDistanciaNodoAproximado() == null ? " " : cmt.getDistanciaNodoAproximado()+" Mts";
                        sb.append(distNodoAproximado);
                        sb.append(";");
                        String proyecto = cmt.getProyecto() == null ? " " : cmt.getProyecto();
                        sb.append(proyecto);
                        sb.append(";");
                         String estratos;
                            int datoEstrato = Integer.parseInt(String.valueOf(cmt.getEstrato()));
                        if(cmt.getEstrato() == null){
                                estratos="NG";
                            }else{
                            if (datoEstrato<=6 && datoEstrato>=0 ){
                            estratos = estrato == null ? "NG" : String.valueOf(datoEstrato);
                            }
                            else{
                            estratos="NG";
                            }

                    }
                       sb.append(estratos);
                        sb.append(";");

                    sb.append("\n");
                    }

                   }
                   csvData = sb.toString().getBytes();
                   String todayStr = formato.format(new Date());
                   String fileName = "Reporte_Factibilidad" + "_" + todayStr + "." + ext;
                   FacesContext fc = FacesContext.getCurrentInstance();
                   HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                   response.setHeader("Content-disposition", "attached; filename=\""
                           + fileName + "\"");
                   response.setContentType("application/force.download");
                   response.getOutputStream().write(csvData);
                   response.getOutputStream().flush();
                   response.getOutputStream().close();
                   fc.responseComplete();
               } else {
                   FacesContext.getCurrentInstance().addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                           "No se encontraron registros para el reporte", ""));
               }

          } catch (IOException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en ReporteFactibilidadBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void volverList() {
        limpiarValores();
    }
    public void limpiarValores() {
        tecnologia = null;
        fechaInicio = null;
        fechaFinal = null;
        estadoFactSelected = null;
        centroPobladoSelected = null;
        cantRegistros = 0;
        factibilidad = null;
        tecnologiaList = null;
        factibilidadList = null;
        centroPobladoList = null;
    }
   
    public String exportCsvTxt(String ext) throws ApplicationException {
        try {
            int CANTIDAD_TOTAL = cantRegistros;
            final int CANTIDAD_LIMITADA_BD = 100;

            if (this.flag) {
                String msn = "Ya se encuentra en proceso una descarga, intente en unos minutos. ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                this.setFlag(true);

                StringBuilder sb = new StringBuilder();
                sb.append(this.getHeaderDocument());
                byte[] csvData;
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
                
                String todayStr = formato.format(new Date());
                String fileName = "Reporte_Factibilidad" + "_" + todayStr + "." + ext;
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.setHeader("Content-disposition", "attached; filename=\"" + fileName + "\"");
                response.setContentType((ext.equals("csv")) ? "text/csv;charset=UTF-8;" : "text/plain;charset=UTF-8;");
                response.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response.getOutputStream().write(csvData);

                for (int iterador = 0; iterador < CANTIDAD_TOTAL; iterador += CANTIDAD_LIMITADA_BD) {

                    int registrosRestantes = CANTIDAD_TOTAL - iterador;
                    int registrosEstaConsulta = Math.min(CANTIDAD_LIMITADA_BD, registrosRestantes);

                    List<CmtReporteFactibilidadDto> factibilidadReportList = factibilidadMglFacadeLocal.getReporte(params, iterador, registrosEstaConsulta);

                    if (factibilidadReportList != null) {
                        if (!factibilidadReportList.isEmpty()) {
                            for (CmtReporteFactibilidadDto cmt : factibilidadReportList) {
                                if (sb.toString().length() > 1) {
                                    sb.delete(0, sb.toString().length());
                                }
                                try {
                                    Class<?> classGenerica = cmt.getClass();
                                    Field[] lstVariables = classGenerica.getDeclaredFields();
                                    for (Field itemVariable : lstVariables) {
                                        Field field = classGenerica.getDeclaredField(itemVariable.getName());
                                        field.setAccessible(true);
                                        Object value = field.get(cmt);
                                        sb.append((value == null) ? " " : value.toString());
                                        sb.append(";");
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                                    String infError = "Ocurrio un error interno al momento de asignar el valor : 'exportCsvTxt'";
                                    LOGGER.error(infError);
                                }

                                String estratos;
                                int datoEstrato = 0;
                                if (cmt.getEstrato() != null) {
                                    datoEstrato = Integer.parseInt(String.valueOf(cmt.getEstrato()));
                                }
                                if (cmt.getEstrato() == null) {
                                    estratos = "NG";
                                } else {
                                    if (datoEstrato <= 6 && datoEstrato >= 0) {
                                        estratos = cmt.getEstrato() == null ? "NG" : String.valueOf(cmt.getEstrato());
                                    } else {
                                        estratos = "NG";
                                    }
                                }
                                sb.append(estratos);
                                sb.append(";");
                                sb.append("\n");

                                csvData = sb.toString().getBytes();
                                response.getOutputStream().write(csvData);
                                response.getOutputStream().flush();
                                response.flushBuffer();
                            }
                        }
                    }
                }
                response.getOutputStream().close();
                fc.responseComplete();
                this.setFlag(false);
            }
        } catch (IOException e) {
            this.setFlag(false);
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    public String exportExcel() {
        try {
            int CANTIDAD_TOTAL = cantRegistros;
            final int CANTIDAD_LIMITADA_BD = 100;
        
            if (this.flag) {
                String msn = "Ya se encuentra en proceso una descarga, intente en unos minutos. ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                this.setFlag(true);
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("FACTIBILIDAD");
                final List<String> lstCamposOmitir = Arrays.asList(LST_CAMPOSHEADER_OMITIR);

                int numeroFileExcel = 0;
                for (int iterador = 0; iterador < CANTIDAD_TOTAL; iterador += CANTIDAD_LIMITADA_BD) {

                    int registrosRestantes = CANTIDAD_TOTAL - iterador;
                    int registrosEstaConsulta = Math.min(CANTIDAD_LIMITADA_BD, registrosRestantes);

                    List<CmtReporteFactibilidadDto> listCmtReporteFactibilidadDto1 = factibilidadMglFacadeLocal.getReporte(params, iterador, registrosEstaConsulta);

                    if (listCmtReporteFactibilidadDto1 != null) {                    
                        if (!listCmtReporteFactibilidadDto1.isEmpty()) {
                            for (CmtReporteFactibilidadDto a : listCmtReporteFactibilidadDto1) {
                                Row row = sheet.createRow(numeroFileExcel);
                                int numeroCeldaExcel = 0;
                                if (numeroFileExcel == 0) {
                                    for (String celdas : LST_HEADER_EXCEL) {
                                        Cell cell = row.createCell(numeroCeldaExcel++);
                                        cell.setCellValue(celdas);
                                    }
                                    numeroCeldaExcel = 0;
                                    numeroFileExcel++;
                                    row = sheet.createRow(numeroFileExcel);
                                }

                                try {
                                    Class<?> classGenerica = a.getClass();
                                    Field[] lstVariables = classGenerica.getDeclaredFields();
                                    for (Field itemVariable : lstVariables) {

                                        if (!lstCamposOmitir.contains(itemVariable.getName())) {
                                            Field field = classGenerica.getDeclaredField(itemVariable.getName());
                                            field.setAccessible(true);
                                            Object value = field.get(a);
                                            Cell celda = row.createCell(numeroCeldaExcel++);

                                            if (itemVariable.getName().equals("estrato")) {
                                                int datoEstrato = 0;
                                                if (value != null) {
                                                    datoEstrato = Integer.parseInt(String.valueOf(value.toString()));
                                                }

                                                if (value == null) {
                                                    celda.setCellValue("NG");
                                                } else {
                                                    celda.setCellValue((datoEstrato <= 6 && datoEstrato >= 0)
                                                            ? String.valueOf(value.toString()) : "NG");
                                                }
                                            } else {
                                                if (itemVariable.getName().equals("distanciaNodoAproximado")) {
                                                    celda.setCellValue((value != null) ? value.toString() + " Mts" : "");
                                                } else {
                                                    celda.setCellValue((value != null) ? value.toString() : "");
                                                }
                                            }
                                        }
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                                    String infError = "Ocurrio un error interno al momento de asignar el valor : 'exportCsvTxt'";
                                    LOGGER.error(infError);
                                }
                                numeroFileExcel++;
                            }
                        } else {
                            LOGGER.warn("No se puede realizar el reporte de factibilidad, ya que la lista esta vacia");
                        }
                    } else {
                        LOGGER.warn("No se puede realizar el reporte de factibilidad, ya que la lista es nula");
                    }
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseHttp = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseHttp.reset();
                responseHttp.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                responseHttp.setHeader("Content-Disposition", "attachment; filename=\"" + "Factibilidad" + formato.format(new Date()) + ".xlsx\"");
                try (OutputStream output = responseHttp.getOutputStream()) {
                    workbook.write(output);
                    output.flush();
                }
                fc.responseComplete();
                this.setFlag(false);
            }
        } catch (IOException | ApplicationException e) {
            this.setFlag(false);
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

      // Validar Opciones por Rol
    public boolean validarOpcionReporte() {
        return validarEdicionRol(REPORTEFACTIBILIDAD);
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
            FacesUtil.mostrarMensajeError("Error al SubtipoOtVtTecBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /* ------------- Getters and Setters ------------- */
    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public CmtBasicaMgl getBasicaTecnologia() {
        return basicaTecnologia;
    }

    public void setBasicaTecnologia(CmtBasicaMgl basicaTecnologia) {
        this.basicaTecnologia = basicaTecnologia;
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

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public String getFactibilidad() {
        return factibilidad;
    }

    public void setFactibilidad(String factibilidad) {
        this.factibilidad = factibilidad;
    }

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    public List<CmtBasicaMgl> getListaTablaBasicaEstratos() {
        return listaTablaBasicaEstratos;
    }

    public void setListaTablaBasicaEstratos(List<CmtBasicaMgl> listaTablaBasicaEstratos) {
        this.listaTablaBasicaEstratos = listaTablaBasicaEstratos;
    }

    public List<CmtReporteFactDto> getListaReporteFact() {
        return listaReporteFact;
    }

    public void setListaReporteFact(List<CmtReporteFactDto> listaReporteFact) {
        this.listaReporteFact = listaReporteFact;
    }


    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
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

    public List<CmtGeograficoPoliticoDto> getCentroPobladoCiudadList() {
        return centroPobladoCiudadList;
    }

    public void setCentroPobladoCiudadList(List<CmtGeograficoPoliticoDto> centroPobladoCiudadList) {
        this.centroPobladoCiudadList = centroPobladoCiudadList;
    }


    public String getNombreCentroPoblado() {
        return nombreCentroPoblado;
    }

    public void setNombreCentroPoblado(String nombreCentroPoblado) {
        this.nombreCentroPoblado = nombreCentroPoblado;
    }

    public List<ParametrosCalles> getListEstadoFactibilidad() {
        return listEstadoFactibilidad;
    }

    public void setListEstadoFactibilidad(List<ParametrosCalles> listEstadoFactibilidad) {
        this.listEstadoFactibilidad = listEstadoFactibilidad;
    }

    public String getEstadoFactSelected() {
        return estadoFactSelected;
    }

    public void setEstadoFactSelected(String estadoFactSelected) {
        this.estadoFactSelected = estadoFactSelected;
    }

    public List<GeograficoPoliticoMgl> getListaCentroPoblado() {
        return listaCentroPoblado;
    }

    public void setListaCentroPoblado(List<GeograficoPoliticoMgl> listaCentroPoblado) {
        this.listaCentroPoblado = listaCentroPoblado;
    }

    public List<CmtReporteFactibilidadDto> getListCmtReporteFactibilidadDto() {
        return listCmtReporteFactibilidadDto;
    }

    public void setListCmtReporteFactibilidadDto(List<CmtReporteFactibilidadDto> listCmtReporteFactibilidadDto) {
        this.listCmtReporteFactibilidadDto = listCmtReporteFactibilidadDto;
    }

    public String getCentroPobladoSelected() {
        return centroPobladoSelected;
    }

    public void setCentroPobladoSelected(String centroPobladoSelected) {
        this.centroPobladoSelected = centroPobladoSelected;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getCantMaxRegExcel() {
        return cantMaxRegExcel;
    }

    public void setCantMaxRegExcel(String cantMaxRegExcel) {
        this.cantMaxRegExcel = cantMaxRegExcel;
    }

    public String getCantMaxRegCvs() {
        return cantMaxRegCvs;
    }

    public void setCantMaxRegCvs(String cantMaxRegCvs) {
        this.cantMaxRegCvs = cantMaxRegCvs;
    }

    public List<String> getTecnologiaList() {
        return tecnologiaList;
    }

    public void setTecnologiaList(List<String> tecnologiaList) {
        this.tecnologiaList = tecnologiaList;
    }

    public List<String> getFactibilidadList() {
        return factibilidadList;
    }

    public void setFactibilidadList(List<String> factibilidadList) {
        this.factibilidadList = factibilidadList;
    }


    public List<String> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<String> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}