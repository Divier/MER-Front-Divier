package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.CmtPenetracionCMDto;
import co.com.claro.mgl.dtos.CmtPestanaPenetracionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "reportePenetracion")
@ViewScoped
@Log4j2
public class ReportePenetracionBean implements Serializable {

    private SecurityLogin securityLogin;
    private int perfilVT = 0;
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private List<CmtBasicaMgl> listaTablaBasicaEstratos;
    private BigDecimal tecnologia;
    private CmtBasicaMgl basicaTecnologia;
    private Date fechaInicio;
    private Date fechaFinal;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String nodo;
    private BigDecimal estrato;
    private List<CmtPenetracionCMDto> listaPenetracionTecnologias;
    private CmtPestanaPenetracionDto cmtPestanaPenetracionDto;
    
    @EJB
    private CmtTecnologiaSubMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private HashMap<String, Object> params;
    private int cantRegistros;
    private boolean panelPintarPaginado;
    private static final String[] NOM_COLUMNAS = { "Id Cm", "Número de Cm","Nombre de Cm", "Torre","Dirección", "Barrio","Ciudad", "CentroPoblado", "No Torres", 
    "Pisos","Nodo",  "Estrato", "Tecnología", "Estado de Tecnología", "Cant Hhpp","Total Clientes Activos",  "Meta","% Cumplimiento",
        "% Penetración","Costo Acometida","ID VT",  "ID OT","CostoVT", "Fecha Habilitación", "Tiempo Recuperación", "Renta mensual"
    };
    private int numRegTotal;
    private final String SCVBNRCMM = "SCVBNRCMM"; 

    public ReportePenetracionBean() {
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
             FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void cargarListas() {
        try {
            CmtTipoBasicaMgl tipoBasicaTecnologias;
            tipoBasicaTecnologias = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologias);
            CmtTipoBasicaMgl listaEstratos;
            listaEstratos = tipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTRATO);
            listaTablaBasicaEstratos = basicaMglFacadeLocal.findByTipoBasica(listaEstratos);

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
            if (blockReportServBean.isReportGenerationBlock("Reporte de Penetracion CCMM")) return;

            buscar();
            
            if (validarFechas()) {

                listaPenetracionTecnologias = new ArrayList<>();
                cmtPestanaPenetracionDto = cmtTecnologiaSubMglFacadeLocal.findListCMxTecnologias(params);
                listaPenetracionTecnologias = cmtPestanaPenetracionDto.getListaPenetracionTecnologias();
                if(listaPenetracionTecnologias != null){
                    cantRegistros = listaPenetracionTecnologias.size();
                    if (cantRegistros == 0) {
                        String msn = "No se encontró ningún registro para la consulta realizada ";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }
                }else{
                    String msn = "No se encontró ningún registro para la consulta realizada ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
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
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "La Fecha Inicio no puede ser mayor a la Fecha Fin", ""));
                    }
                    respuesta = !(fechaFinal.before(fechaInicio));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El campo Fecha Inicial e Final es requerido", ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        }

        return respuesta;
    }
        
    public static boolean validarFormatoFecha(Date fecha) {
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
            LOGGER.error(msg, e);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        }
        return true;
    }

    public void buscar() {
        try {
            params = new HashMap<>();
            params.put("all", "1");
            params.put("tecnologia", this.tecnologia);
            params.put("fechaInicio", this.fechaInicio);
            params.put("fechaFinal", this.fechaFinal);
            params.put("nodo", this.nodo);
            params.put("estrato", this.estrato);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al armar la consulta de CM.", ""));
        }
    }

    public String exportExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("SolicitudesCM");
            String[] objArr = {
                "Id Cm", 
                "Número de Cm",
                "Nombre de Cm", 
                "Torre",
                "Dirección",
                "Barrio",
                "Ciudad",
                "CentroPoblado",
                "No Torres", 
                "No Pisos",
                "Nodo", 
                "Estrato", 
                "Tecnología", 
                "Estado de Tecnología", 
                "Cant Hhpp",
                "Total Clientes Activos", 
                "Meta",
                "% Cumplimiento",
                "% Penetración",
                "Costo Acometida",
                "ID VT", 
                "ID OT",
                "CostoVT",
                "Fecha Habilitación", 
                "Tiempo Recuperación",
                "Renta mensual"
                
            };

            int rownum = 0;
            if (!listaPenetracionTecnologias.isEmpty()) {
                for (CmtPenetracionCMDto a : listaPenetracionTecnologias) {
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
                        cell1.setCellValue(a.getIdCm());
                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(a.getNumeroCuentaCm());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getNombreCm());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getNombreTorre()!=null ? a.getNombreTorre(): "");
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDireccionCm());
                        Cell cel16 = row.createCell(cellnum++);
                        cel16.setCellValue(a.getBarrio() != null ? a.getBarrio() : "");
                        Cell cell7 = row.createCell(cellnum++);
                        cell7.setCellValue(a.getCiudad());
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(a.getCentroPoblado());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(a.getNumeroTorres());
                        Cell cell10 = row.createCell(cellnum++);
                        cell10.setCellValue(a.getNumeroPisos());
                        Cell cell11 = row.createCell(cellnum++);
                        cell11.setCellValue(a.getNodo());
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(a.getEstrato() != null ? a.getEstrato() : "");
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(a.getTecnologia());
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(a.getEstadoTecnologia());
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(a.getCantHhpp());
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(a.getTotalClientes());
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(a.getMeta() != null ? a.getMeta().toString() : "");
                        Cell cell18 = row.createCell(cellnum++);
                        cell18.setCellValue(a.getCumplimiento() != null ? a.getCumplimiento().toString() : "");
                        Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue( a.getPorcPenetracion() != null ? a.getPorcPenetracion().toString() : "");
                        Cell cell20= row.createCell(cellnum++);
                        cell20.setCellValue(a.getCostoAcometida() != null ? a.getCostoAcometida().toString() : "");
                        Cell cell121 = row.createCell(cellnum++);
                        cell121.setCellValue(a.getIdVt());
                        Cell cell122 = row.createCell(cellnum++);
                        cell122.setCellValue(a.getIdOt()!=null ? a.getIdOt().doubleValue(): 0);
                        Cell cell123 = row.createCell(cellnum++);
                        cell123.setCellValue(a.getCostoVT() != null ? a.getCostoVT().toString() : "");
                        Cell cell124 = row.createCell(cellnum++);
                        cell124.setCellValue(a.getFechaHabilitacion() != null ? a.getFechaHabilitacion() : "");
                        Cell cell125 = row.createCell(cellnum++);
                        cell125.setCellValue(a.getTiempoRecuperacion() != null ? a.getTiempoRecuperacion().toString() : "");
                        Cell cell126 = row.createCell(cellnum++);
                        cell126.setCellValue(a.getRentaMensual()!=null ? a.getRentaMensual().doubleValue(): 0);
                        
                    } else {

                        Cell cell1 = row.createCell(cellnum++);
                        if (a.getIdCm() != null) {
                            cell1.setCellValue(a.getIdCm());
                        }

                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(a.getNumeroCuentaCm());
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getNombreCm());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getNombreTorre()!=null ? a.getNombreTorre(): "");
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDireccionCm());
                        Cell cel16 = row.createCell(cellnum++);
                        cel16.setCellValue(a.getBarrio() != null ? a.getBarrio() : "");
                        Cell cell7 = row.createCell(cellnum++);
                        cell7.setCellValue(a.getCiudad());
                        Cell cell8 = row.createCell(cellnum++);
                        cell8.setCellValue(a.getCentroPoblado());
                        Cell cell9 = row.createCell(cellnum++);
                        cell9.setCellValue(a.getNumeroTorres());
                        Cell cell10 = row.createCell(cellnum++);
                        cell10.setCellValue(a.getNumeroPisos());
                        Cell cell11 = row.createCell(cellnum++);
                        cell11.setCellValue(a.getNodo());
                        Cell cell12 = row.createCell(cellnum++);
                        cell12.setCellValue(a.getEstrato() != null ? a.getEstrato() : "");
                        Cell cell13 = row.createCell(cellnum++);
                        cell13.setCellValue(a.getTecnologia());
                        Cell cell14 = row.createCell(cellnum++);
                        cell14.setCellValue(a.getEstadoTecnologia());
                        Cell cell15 = row.createCell(cellnum++);
                        cell15.setCellValue(a.getCantHhpp());
                        Cell cell16 = row.createCell(cellnum++);
                        cell16.setCellValue(a.getTotalClientes());
                        Cell cell17 = row.createCell(cellnum++);
                        cell17.setCellValue(a.getMeta() != null ? a.getMeta().toString() : "");
                        Cell cell18 = row.createCell(cellnum++);
                        cell18.setCellValue(a.getCumplimiento() != null ? a.getCumplimiento().toString() : "");
                        Cell cell19 = row.createCell(cellnum++);
                        cell19.setCellValue( a.getPorcPenetracion() != null ? a.getPorcPenetracion().toString() : "");
                        Cell cell20= row.createCell(cellnum++);
                        cell20.setCellValue(a.getCostoAcometida() != null ? a.getCostoAcometida().toString() : "");
                        Cell cell121 = row.createCell(cellnum++);
                        cell121.setCellValue(a.getIdVt());
                        Cell cell122 = row.createCell(cellnum++);
                        cell122.setCellValue(a.getIdOt()!=null ? a.getIdOt().doubleValue(): 0);
                        Cell cell123 = row.createCell(cellnum++);
                        cell123.setCellValue(a.getCostoVT() != null ? a.getCostoVT().toString() : "");
                        Cell cell124 = row.createCell(cellnum++);
                        cell124.setCellValue(a.getFechaHabilitacion() != null ? a.getFechaHabilitacion() : "");
                        Cell cell125 = row.createCell(cellnum++);
                        cell125.setCellValue(a.getTiempoRecuperacion() != null ? a.getTiempoRecuperacion().toString() : "");
                        Cell cell126 = row.createCell(cellnum++);
                        cell126.setCellValue(a.getRentaMensual()!=null ? a.getRentaMensual().doubleValue(): 0);
                     
                    }
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseServlet = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseServlet.reset();
                responseServlet.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                responseServlet.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_Penetracion" + "_" + formato.format(new Date())
                        + ".xlsx\"");
                try (OutputStream output = responseServlet.getOutputStream()) {
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
        } catch (IOException e) {
         FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportePeneracion. " + e.getMessage(), e, LOGGER);
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
               final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
               if (!listaPenetracionTecnologias.isEmpty()) {
                   int numeroRegistros = listaPenetracionTecnologias.size();
                   byte[] csvData = null;
                   if (numeroRegistros > 0) {
                           for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                               sb.append(NOM_COLUMNAS[j]);
                               if (j < NOM_COLUMNAS.length) {
                                   sb.append(";");
                               }
                           }
                        
                           sb.append("\n");

                          for (CmtPenetracionCMDto cmt : listaPenetracionTecnologias) {

                            String idCm = cmt.getIdCm() == null ? " " : cmt.getIdCm();
                            sb.append(idCm);
                            sb.append(";");
                            String numeroCuenta = cmt.getNumeroCuentaCm() == null ? " " : cmt.getNumeroCuentaCm();
                            sb.append(numeroCuenta);
                            sb.append(";");
                            String nombreCm = cmt.getNombreCm() == null ? " " : cmt.getNombreCm();
                            sb.append(nombreCm);
                            sb.append(";");
                            String torre = cmt.getNombreTorre()== null ? " " : cmt.getNombreTorre();
                            sb.append(torre);
                            sb.append(";");
                            String direccion = cmt.getDireccionCm() == null ? " " : cmt.getDireccionCm();
                            sb.append(direccion);
                            sb.append(";");
                            String barrio = cmt.getBarrio()== null ? " " : cmt.getBarrio();
                            sb.append(barrio);
                            sb.append(";");
                             String ciudad = cmt.getCiudad() == null ? " " : cmt.getCiudad();
                            sb.append(ciudad);
                            sb.append(";");
                            String centroPoblado = cmt.getCentroPoblado() == null ? " " : cmt.getCentroPoblado();
                            sb.append(centroPoblado);
                            sb.append(";");
                            String cantTorres = cmt.getNumeroTorres() == 0 ? "" : Integer.toString(cmt.getNumeroTorres());
                            sb.append(cantTorres);
                            sb.append(";");
                            String pisos = cmt.getNumeroPisos() == 0 ? "0" : String.valueOf(cmt.getNumeroPisos());
                            sb.append(pisos);
                            sb.append(";");
                            String nodo = String.valueOf(cmt.getNodo());
                            sb.append(nodo);
                            sb.append(";");
                            String estrato = String.valueOf(cmt.getEstrato());
                            sb.append(estrato);
                            sb.append(";");
                             String tec = cmt.getTecnologia() == null ? " " : cmt.getTecnologia();
                            sb.append(tec);
                            sb.append(";");
                             String estadoTec = cmt.getEstadoTecnologia() == null ? " " : cmt.getEstadoTecnologia();
                            sb.append(estadoTec);
                            sb.append(";");
                              String hhppCant = String.valueOf(cmt.getCantHhpp());
                            sb.append(hhppCant);
                            sb.append(";");
                            String totalClientes = cmt.getTotalClientes() == 0 ? "" :String.valueOf(cmt.getTotalClientes());
                            sb.append(totalClientes);
                            sb.append(";");
                            String meta = String.valueOf(cmt.getMeta());
                            sb.append(meta);
                            sb.append(";");
                            String cumplimiento = String.valueOf(cmt.getCumplimiento());
                            sb.append(cumplimiento);
                            sb.append(";");
                            
                            String porcPenetracion = String.valueOf(cmt.getPorcPenetracion());
                            sb.append(porcPenetracion);
                            sb.append(";");
                            String costoAcom = String.valueOf(cmt.getCostoAcometida());
                            sb.append(costoAcom);
                            sb.append(";");
                             String idVt = cmt.getIdVt();
                            sb.append(idVt);
                            sb.append(";");
                            String idOt = cmt.getIdOt() != null ? "" : cmt.getIdOt().toString();
                            sb.append(idOt);
                            sb.append(";");
                            String costoVt = String.valueOf(cmt.getCostoVT());
                            sb.append(costoVt);
                            sb.append(";");
                            String fechaHab = String.valueOf(cmt.getFechaHabilitacion() != null ? cmt.getFechaHabilitacion() : "");
                            sb.append(fechaHab);
                            sb.append(";");
                            String tiempoRec = String.valueOf(cmt.getTiempoRecuperacion());
                            sb.append(tiempoRec);
                            sb.append(";");
                            String rentaMensual = String.valueOf(cmt.getRentaMensual());
                            sb.append(rentaMensual);
                            sb.append(";");

                            sb.append("\n");
                        }

                   }
                   csvData = sb.toString().getBytes();
                   String todayStr = formato.format(new Date());
                  String fileName = "Reporte_penetracion" + "_" + todayStr + "." + ext;
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

          } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ReportePenetracionBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportePenetracionBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }
    
        public void volverList() {
   
            limpiarValores();
       
    }
        public void limpiarValores(){
            tecnologia = null;
            fechaInicio = null;
            fechaFinal = null;
            nodo = null;
            estrato = null;
            cantRegistros = 0;
        }
        
    // Validar Opciones por Rol    
    public boolean validarReportePenetracion() {
        return validarEdicionRol(SCVBNRCMM); 
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

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
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

    public List<CmtPenetracionCMDto> getListaPenetracionTecnologias() {
        return listaPenetracionTecnologias;
    }

    public void setListaPenetracionTecnologias(List<CmtPenetracionCMDto> listaPenetracionTecnologias) {
        this.listaPenetracionTecnologias = listaPenetracionTecnologias;
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
}
