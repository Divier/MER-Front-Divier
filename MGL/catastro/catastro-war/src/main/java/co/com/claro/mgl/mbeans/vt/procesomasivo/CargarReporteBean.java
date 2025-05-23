/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;


import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.procesomasivo.CargueMasivoHhppFacadeLocal;
import co.com.claro.mgl.facade.procesomasivo.HhppCargueArchivoLogFacadeLocal;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.procesomasivo.CargueArchivoLogItemFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.manager.procesomasivo.CargueArchivoLogItemManager;
import co.com.claro.mgl.manager.procesomasivo.CargueMasivoHhppManager;
import static co.com.claro.mgl.manager.procesomasivo.CargueMasivoHhppManager.headerDirTCRM;
import static co.com.claro.mgl.manager.procesomasivo.CargueMasivoHhppManager.headerEnviarArchivoEOC_BCSC;
import static co.com.claro.mgl.manager.procesomasivo.CargueMasivoHhppManager.headerEnviarArchivoTCRM;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.SFTPUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.mgl.vo.cm.DetalleCargaHhppMgl;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Cargar el reporte solicitado.
 * <p>
 * Proceso masivo requiere realizar un reporte según los datos que son
 * seleccionados. CargarReporteBean es la clase encargada de controlar el
 * llamado del reporte y de enviar el resultado de la petición.
 *
 * @author becerraarmr
 * @see Serializable
 */
@ManagedBean
@ViewScoped
public class CargarReporteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nombre del archivo temporal donde se dejan los datos procesados
     */
    private String nameFile;

    /**
     * Tamaño del archivo temporal.
     */
    private int sizeFile;

    /**
     * Describir el estado del reporte
     */
    private String msgCargaReporte;

    /**
     * para saber el estado del proceso
     */
    private String estadoProceso;
    /**
     * Muestra si se puede exportar a Excel
     */
    private boolean puedeExportarXls;
    /**
     * Muestra si se puede exportar a txt
     */
    private boolean puedeExportarTxt;
    /**
     * Muestra si se puede exportar a csv
     */
    private boolean puedeExportarCsv;
    /**
     * Muestra si se puede exportar
     */
    private boolean puedeExportar;

    /**
     * Items de los hhpp_cargue_archivo_log
     */
    private List<HhppCargueArchivoLog> itemsPendientesPorProcesar = null;
    /**
     * Items de los hhpp_cargue_archivo_log que corresponden al usuario logeado
     */
    private List<HhppCargueArchivoLog> itemsProcesosUsuarioLogin = null;
    
    /**
     * Items de los hhpp_cargue_archivo_log que corresponden al usuario logeado
     */
    private List<HhppCargueArchivoLog> itemsArchivosUsuarioLogin = null;
    /**
     * Items de los hhpp_cargue_archivo_log que corresponden al usuario logeado lista auxiliar
     */
    private List<HhppCargueArchivoLog> itemsProcesosUsuarioLoginAux = null;
    /**
     * Paginador para el listado e HhppCargueArchivoLog
     */
    private PaginationHelper<HhppCargueArchivoLog> pagination;
    /**
     * Paginador para el listado e HhppCargueArchivoLog del usuario logeado
     */
    private PaginationHelper<HhppCargueArchivoLog> paginationProcesosLogin;

    /**
     * Tamaño del paginador por defecto
     */
    private int tamPagination = 10;//Por defecto muestra 10 registros

    /**
     * Estado del HhppCargueArchivoLog a procesar
     */
    private short estado;
    /**
     * Estado del HhppCargueArchivoLog a procesar
     */
    private short estadoUsuarioLogin;
    /**
     * Nombre del usuario que está logeado en este momento.
     */
    private String usuario;
    List<CargueArchivoLogItem> registrosBd= null;
    private static int NUM_REGISTROS_PAGINA = 50;
    private static final Logger LOGGER = LogManager.getLogger(CargarReporteBean.class);
    
    private int actualCap;
    private String numPaginaCap = "1";
    private List<Integer> paginaListcap;
    private String pageActualCap;
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    
    ///////////////////////////////////////
    private SFTPUtils sftpUtils;
    private List<String> nombreArchivos;
    private String usuarioSftp="";
    private String passSftp="";
    private String hostSftp="";
    private int puertoSftp=0;
    private String rutaBajarArchivosSftp="";
    private String rutaBajarTempoArchivosSftp="";
    private CargueArchivoLogItem cargueArchivoLogItem;
    private boolean mensajeFinalizado;
    private boolean finalizaCargue = false;
    private int perfil = 0;
    private final Locale LOCALE = new Locale("es", "CO");
    private String mensajeReversion="";
    private List<String[]>registrosRev;
    private String[] encabezado = null;
    private SecurityLogin securityLogin;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal direccionDetalleMglFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private ResourceEJBRemote resourceEJB;
    @EJB
    private CargueArchivoLogItemFacadeLocal cargueArchivoLogItemFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
        /**
     * Ejb para la acciones a realizar de HhppCargueArchivoLog
     */
    @EJB
    private HhppCargueArchivoLogFacadeLocal ejbHhppCargueArchivoLog;
    /**
     * Ejb para la acciones a realizar con cargue masivo.
     */
    @EJB
    private CargueMasivoHhppFacadeLocal ejbCargueMasivoHhpp;
    
    
    //Opciones agregadas para Rol
    private final String BTNSBARHP = "BTNSBARHP";
    //Opciones agregadas para Rol
    private final String BTNRGPRHP = "BTNRGPRHP";
    //Opciones agregadas para Rol
    private final String BTNUPDHHP = "BTNUPDHHP";
    
    ///////////////////////////////////////
    /**
     * Crear una nueva instancia CargarReporteBean
     */
    public CargarReporteBean(){
        cargarUsuario(); 
        procesarMensaje();
    }

    /**
     * Cargar el usuario logeado Se cargar el usuario el usuario logeado
     *
     * @author becerraarmr
     */
    private void cargarUsuario(){
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());        
            if (!securityLogin.isLogin()) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.
                        getCurrentInstance().getExternalContext().getResponse();
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }

            usuario = securityLogin.getLoginUser();
            perfil = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en cargarUsuario,validando autenticacion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarUsuario,validando autenticacion. ", e, LOGGER);
        } 
    }
    
    
    @PostConstruct
    public void init() {
        listInfoByPageCap(1);

    }

    /**
     * Mostrar en pantalla archivo Excel
     * <p>
     * Permite mostrar el archivo en Excel que fue procesado según los filtros
     * seleccionados.
     *
     * @author becerraarmr
     *
     * @param workbook objeto que contiene la data para el archivo en Excel.
     */
    private void responder(String fileNameProcesado, String tipoArchivo) {
        if (fileNameProcesado == null || tipoArchivo == null) {
            return;
        }
        if (!(".xlsx".equalsIgnoreCase(tipoArchivo)
                || ".txt".equalsIgnoreCase(tipoArchivo)
                || ".csv".equalsIgnoreCase(tipoArchivo))) {
            return;
        }

        ServletOutputStream outputStream = null;
        try {
            SimpleDateFormat formato
                    = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fec = formato.format(new Date());
            String filename = "Resultado_" + fec;
            filename += tipoArchivo;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response
                    = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setHeader("Content-Disposition", "filename=" + filename);
            outputStream = response.getOutputStream();
            if (".xlsx".equalsIgnoreCase(tipoArchivo)) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                outputStream.write(converterFileToByte(new File("tmp", fileNameProcesado)));
            } else if (".txt".equalsIgnoreCase(tipoArchivo) || ".csv".equalsIgnoreCase(tipoArchivo)) {
                response.setContentType("application/force.download");
                Workbook workbook = new XSSFWorkbook(new FileInputStream(new File("tmp", fileNameProcesado)));
                outputStream.write(converterXlsToCsv(workbook));
            }
            outputStream.flush();
            outputStream.close();
            context.responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en responder. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en responder. ", e, LOGGER);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    FacesUtil.mostrarMensajeError("Error en responder. ", e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en responder. ", e, LOGGER);
                }
            }
        }
    }

    /**
     * Convierte un archivo de excel a CSV o TXT
     * <p>
     * Lee el archivo del reporte y lo convierte a byte.
     *
     * @author becerraarmr
     * @return vector de bytes que representan el archivo
     */
    private byte[] converterXlsToCsv(Workbook workbook) throws ApplicationException {
        try {
            if (workbook == null) {
                return null;
            }
            // For storing data into CSV files
            StringBuilder data = new StringBuilder();

            // Get first sheet from the workbook
            int ns = 1;
            Sheet sheet = workbook.getSheet(ns + "");

            while (sheet != null) {
                Cell cell;
                Row row;

                // Iterate through each rows from first sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    if (ns > 1) {
                        rowIterator.next();
                        rowIterator.next();
                        rowIterator.next();
                    }
                    row = rowIterator.next();
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                data.append(cell.getBooleanCellValue());
                                break;

                            case Cell.CELL_TYPE_NUMERIC:
                                data.append(cell.getNumericCellValue());
                                break;

                            case Cell.CELL_TYPE_STRING:
                                data.append(cell.getStringCellValue());
                                break;

                            case Cell.CELL_TYPE_BLANK:
                                data.append("");
                                break;

                            default:
                                data.append(cell);
                        }
                        if (cellIterator.hasNext()) {
                            data.append(',');
                        }
                    }
                    data.append('\n');
                }
                ns++;
                sheet = workbook.getSheet(ns + "");
            }
            return data.toString().getBytes("UTF-8");
        } catch (IOException e) {
            LOGGER.error("Error en converterXlsToCsv. " +e.getMessage(), e);   
            throw new ApplicationException("Error en converterXlsToCsv. ",e);
        } catch (Exception e) {
            LOGGER.error("Error en converterXlsToCsv. " +e.getMessage(), e);   
            throw new ApplicationException("Error en converterXlsToCsv. ",e);
        }
    }

    /**
     * Volver al menú principal
     * <p>
     * vuelve al menú principal para solictar otra acción.
     *
     * @author becerraarmr
     * @throws java.io.IOException
     */
    public void volver()  {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                    getExternalContext().getSession(false);
            msgCargaReporte = "";
            nameFile = "";
            if (session != null) {
                Object object = session.getAttribute("THREADCARGAMASIVA_MGL");
                if (object instanceof CargueMasivoHhppManager) {
                    CargueMasivoHhppManager thread
                            = (CargueMasivoHhppManager) object;
                    if (thread.isArchivoProcesado()) {
                        session.setAttribute("THREADCARGAMASIVA_MGL", null);
                    } else {
                        msgCargaReporte = thread.getMsgProceso();
                        nameFile = thread.getFileName();
                    }
                }
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en volver. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en volver. ", e, LOGGER);
        }
    }

    /**
     * Prepara para carga nueva.
     * <p>
     * Prepara el ambiente para una carga nueva.
     *
     * @author becerraarmr
     */
    public void prepareNuevo() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        msgCargaReporte = "";
        nameFile = "";
        sizeFile = 0;
        estadoProceso = "";
        puedeExportarXls = false;
        puedeExportarTxt = false;
        puedeExportarCsv = false;
        puedeExportar = false;
        if (session != null) {
            Object object = session.getAttribute("THREADCARGAMASIVA_MGL");
            if (object instanceof CargueMasivoHhppManager) {
                session.setAttribute("THREADCARGAMASIVA_MGL", null);
                 mensajeFinalizado=false;
                 finalizaCargue = true;
            }
        }
        listInfoByPageCap(1);
    }

    /**
     * Notificar procesamiento
     * <p>
     * Notifica el estado del procesamiento a la presentación de trabajo.
     *
     * @author becerraarmr
     */
    public void notificar() {
        FacesContext.getCurrentInstance().
                getPartialViewContext().getRenderIds().add("form_action");
        FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
    }

    /**
     * Buscar el nombre del archivo
     * <p>
     * Muestra el nombre del archivo que se proceso con la data solicitada.
     *
     * @author becerraarmr
     *
     * @return El String con el nombre del archivo. Null en caso que no lo
     * encuentre
     */
    public String getNameFile() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        nameFile = "No hay Archivo";
        if (session != null) {
            Object object = session.getAttribute("THREADCARGAMASIVA_MGL");
            if (object instanceof CargueMasivoHhppManager) {
                CargueMasivoHhppManager thread
                        = (CargueMasivoHhppManager) session.
                                getAttribute("THREADCARGAMASIVA_MGL");
                if (thread != null) {
                    nameFile = thread.getFileName();
                } else {
                    nameFile = "No hay Archivo";
                }
            }
        } 
        return nameFile;
    }

    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param nameFile Nombre de archivo actualizar.
     */
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    /**
     * Ver mensaje de reportes. Se muestra el muestra el mensaje del reporte
     * según la acción realizada.
     *
     * @author becerraarmr
     * @return El String con el mensaje reportado
     */
    public String getMsgCargaReporte() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        msgCargaReporte = "";
        if (session != null) {
            Object object = session.getAttribute("THREADCARGAMASIVA_MGL");
            if (object instanceof CargueMasivoHhppManager) {
                CargueMasivoHhppManager thread
                        = (CargueMasivoHhppManager) session.
                                getAttribute("THREADCARGAMASIVA_MGL");
                if (thread != null) {
                    msgCargaReporte = thread.getMsgProceso();
                } else {
                    msgCargaReporte = "Sin procesar";
                }
            }
        }
        return msgCargaReporte;
    }
 
   
    
    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param msgCargaReporte nueva descripción del mensaje
     */
    public void setMsgCargaReporte(String msgCargaReporte) {
        this.msgCargaReporte = msgCargaReporte;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public int getSizeFile() {
        return sizeFile;
    }

    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param sizeFile Nuevo tamaño del archivo
     */
    public void setSizeFile(int sizeFile) {
        this.sizeFile = sizeFile;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return la descipción con el estado del proceso
     */
    public String getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param estadoProceso nueva descripcion del estado del proceso
     */
    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * Activar tipo de exportar Si ya terminó de procesar el archivo activar el
     * tipo que se necesita
     *
     * @author becerraarmr
     */
    public void verificarEstadoExportar() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        if (session != null) {
            CargueMasivoHhppManager thread
                    = (CargueMasivoHhppManager) session.
                            getAttribute("THREADCARGAMASIVA_MGL");
            if (thread != null) {
                message(thread);
                if (thread.isArchivoProcesado()) {
                    puedeExportarCsv = true;
                    puedeExportarTxt = true;
                    puedeExportarXls = true;
                    puedeExportar = true;
                    mensajeFinalizado=false;
                    listInfoByPageCap(1);
                    
                }
            }
        }
    }

    /**
     * Mostrar valor para ver si puede exportar
     * <p>
     * Busca si ya se puede activar el button de la presentación para poder
     * exportar.
     *
     * @author becerraarmr
     * @return true si el proceso se ha terminado y activa para exportar, false
     * en caso contrario
     */
    public boolean isPuedeExportar() {
        return puedeExportar;
    }

    /**
     * Mostrar valor para ver si puede exportar
     * <p>
     * Busca si ya se puede activar el button de la presentación para poder
     * exportar.
     *
     * @author becerraarmr
     * @return true si el proceso se ha terminado y activa para exportar, false
     * en caso contrario
     */
    public boolean isPuedeExportarXls() {
        return puedeExportarXls;
    }

    /**
     * Mostrar valor para ver si puede exportar
     * <p>
     * Busca si ya se puede activar el button de la presentación para poder
     * exportar.
     *
     * @author becerraarmr
     * @return true si el proceso se ha terminado y activa para exportar, false
     * en caso contrario
     */
    public boolean isPuedeExportarCsv() {
        return puedeExportarCsv;
    }

    /**
     * Mostrar valor para ver si puede exportar
     * <p>
     * Busca si ya se puede activar el button de la presentación para poder
     * exportar.
     *
     * @author becerraarmr
     * @return true si el proceso se ha terminado y activa para exportar, false
     * en caso contrario
     */
    public boolean isPuedeExportarTxt() {
        return puedeExportarTxt;
    }

    /**
     * Solita exportar el reporte a TXT
     * <p>
     * Solicita al método general de exportar Csv y Txt que lo exporte en CSV.
     *
     * @author becerraarmr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void exportarTxt() {
        try {
            if (exportar(".txt")) {
                msgCargaReporte = "Reporte TXT Exportado";
            } else {
                msgCargaReporte = "No se pudo realizar el reporte TXT";
            }
            listInfoByPageCap(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportarTxt. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportarTxt. ", e, LOGGER);
        }
    }

    /**
     * Solicitar exportar a CSV
     * <p>
     * Solicita al método exportarTxtCsv que exporte el reporte a CSV
     *
     * @author becerraarmr
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public void exportarCsv() throws ApplicationException {
        if (exportar(".csv")) {
            msgCargaReporte = "Reporte CSV Exportado";
        } else {
            msgCargaReporte = "No se pudo realizar el reporte CSV";
        }
        listInfoByPageCap(1);
    }

    /**
     * Exportar a Excel
     * <p>
     * Exporta el archivo procesado a formato excel para poder mostrar al
     * usuario que realiza la petición.
     *
     * @author becerraarmr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void exportarXls() throws ApplicationException {
        if (exportar(".xlsx")) {
            msgCargaReporte = "Reporte Excel Exportado";
        } else {
            msgCargaReporte = "No se pudo realizar el reporte Excel";
        }
        listInfoByPageCap(1);
    }

    /**
     * Notificar que se acaba de exportar.
     * <p>
     * Notifica para ver sigue exportando en otros formatos que realiza la
     * petición.
     *
     * @author becerraarmr
     */
    public void notificarExportar() {
        if (!puedeExportarXls && !puedeExportarCsv && !puedeExportarTxt) {
            puedeExportar = false;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                    getExternalContext().getSession(false);
            if (session != null) {
                session.setAttribute("THREADCARGAMASIVA_MGL", null);
            }
        }
    }

    /**
     * Exportar a a tipo de archivo
     * <p>
     * Exporta el archivo al tipo especificado-.
     *
     * @author becerraarmr
     * @param tipoArchivo tipo de archivo a exportar txt csv xlsx
     *
     * @return true si pudo exportar y false en caso contrario.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean exportar(String tipoArchivo) throws ApplicationException  {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                    getExternalContext().getSession(false);
            if (session != null) {
                CargueMasivoHhppManager thread
                        = (CargueMasivoHhppManager) session.
                                getAttribute("THREADCARGAMASIVA_MGL");
                if (thread != null) {
                    if (thread.isArchivoProcesado()) {
                        String fileNameProcesado = thread.getFileNameProcesado();
                        if (fileNameProcesado != null) {
                            construirArchivo(fileNameProcesado, tipoArchivo);
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (ApplicationException e) {
            LOGGER.error("Error en exportar. " +e.getMessage(), e);  
           throw new ApplicationException("Error en exportar. ",e);
        } catch (Exception e) {
            LOGGER.error("Error en exportar. " +e.getMessage(), e);  
            throw new ApplicationException("Error en exportar. ",e);
        }
    }

    /**
     * Exportar a Excel
     * <p>
     * Exporta el archivo procesado a formato excel para poder mostrar al
     * usuario que realiza la petición.
     *
     * @author becerraarmr
     * @param cargueArchivoLog registro con los datos para realizar el cargue.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void exportarProceso(HhppCargueArchivoLog cargueArchivoLog) {
        if (cargueArchivoLog != null) {
            String nombreArchivo = cargueArchivoLog.getNombreArchivoCargue();
            File file = new File("tmp", nombreArchivo);
            if (file.exists()) {
                if (file.canRead()) {
                    responder(nombreArchivo, ".xlsx");
                }
            }
        }
    }
    
     /**
     * Exportar a Excel
     * <p>
     * Exporta el archivo procesado a formato excel para poder mostrar al
     * usuario que realiza la petición.
     *
     * @author bocanegra Vm
     * @param cargueArchivoLog registro con los datos para realizar el cargue desde la BD.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void exportarProcesoBD(HhppCargueArchivoLog cargueArchivoLog) throws ApplicationException {
        if (cargueArchivoLog != null) {
            String nombreArchivo = cargueArchivoLog.getNombreArchivoCargue();
            construirArchivo(nombreArchivo, ".xlsx");
        }
    }

    
         /**
     * Exportar a Excel
     * <p>
     * Exporta el archivo procesado a formato excel para poder mostrar al
     * usuario que realiza la petición.
     *
     * @author bocanegra Vm
     * @param cargueArchivoLog registro con los datos para realizar el cargue desde la BD.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void descargarArchivoTcrm(HhppCargueArchivoLog cargueArchivoLog) {
        try {
            if (cargueArchivoLog != null) {
                construirArchivoTcrm(cargueArchivoLog);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivoTcrm. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivoTcrm. ", e, LOGGER);
        }
    }
    
       /**
     * Exportar a txt
     * <p>
     * Exporta el archivo procesado a formato txt para poder mostrar al
     * usuario que realiza la petición.
     *
     * @author bocanegra Vm
     * @param cargueArchivoLog registro con los datos para realizar el cargue desde la BD.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void descargarArchivoEOC_BCSC(HhppCargueArchivoLog cargueArchivoLog) {
        try {
            if (cargueArchivoLog != null) {
                construirArchivoEOC_BCSC(cargueArchivoLog);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivoTcrm. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en descargarArchivoTcrm. ", e, LOGGER);
        }
    }
    

    /**
     * Pregunta si puede exportar un HhppCargueArchivoLog
     * <p>
     * Evalúa un HhppCargueArchivoLog con el fin de poder establecer si se puede
     * exportar a xls o no.
     *
     * @author becerraarmr
     * @param cargueArchivoLog Objeto con los datos para verificar
     *
     * @return true si puede exportar y false en caso contrario.
     */
    public boolean puedeExportar(HhppCargueArchivoLog cargueArchivoLog) {
        if (cargueArchivoLog != null) {
            String nombreArchivo = cargueArchivoLog.getNombreArchivoCargue();
            File file = new File("tmp", nombreArchivo);
            if (file.exists()) {
                if (file.canRead()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Mostrar listado de items
     * <p>
     * Muestra el listado de HhppCargueArchivoLog según como está solicitado en
     * la paginación.
     *
     * @author becerraarmr
     * @return El Listado con la data econtrada por el paginadro
     *
     * @see HhppCargueArchivoLog
     */
    public List<HhppCargueArchivoLog> getItemsPendientesPorProcesar() {
        try {
            if (itemsPendientesPorProcesar == null) {
                itemsPendientesPorProcesar = new ArrayList<HhppCargueArchivoLog>(); 
            }
            verificarItemsPendientesPorProcesar();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getItemsPendientesPorProcesar, al buscar los items pendientes por procesar. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getItemsPendientesPorProcesar, al buscar los items pendientes por procesar. ", e, LOGGER);
        }
        return itemsPendientesPorProcesar;
    }

    /**
     * Verificar los items pendientes por procesar.
     *
     * @becerraarmr
     * @throws ApplicationException
     */
    private void verificarItemsPendientesPorProcesar() throws ApplicationException {
        try {
            List<HhppCargueArchivoLog> lista = new LinkedList<HhppCargueArchivoLog>();
            this.estado = 0;// se asigna el valor del estado 0 indica no procesado 
            itemsPendientesPorProcesar = getPagination().createPageData();
            for (HhppCargueArchivoLog item : itemsPendientesPorProcesar) {
                if (item.getEstado() == 0 && item.getCantidadTotal().equals(item.getCantidadProcesada())) {
                    Short aux = 1;
                    item.setEstado(aux);
                    ejbHhppCargueArchivoLog.update(item);
                } else {
                    lista.add(item);
                }
            }
            itemsPendientesPorProcesar = lista;
        } catch (ApplicationException e) {
            LOGGER.error("Error en verificarItemsPendientesPorProcesar. " +e.getMessage(), e);   
            throw new ApplicationException("Error en verificarItemsPendientesPorProcesar. ",e);
        } catch (Exception e) {
            LOGGER.error("Error en verificarItemsPendientesPorProcesar. " +e.getMessage(), e);   
            throw new ApplicationException("Error en verificarItemsPendientesPorProcesar. ",e);
        }
    }

    /**
     * Mostrar listado de items
     * <p>
     * Muestra el listado de HhppCargueArchivoLog según como está solicitado en
     * la paginación.
     *
     * @author becerraarmr
     * @return El Listado con la data econtrada por el paginadro
     */
    public List<HhppCargueArchivoLog> getItemsProcesosUsuarioLogin() {
        try {
            if (itemsProcesosUsuarioLogin == null) {
                //Se buscan los procesos terminados
                this.estadoUsuarioLogin = 1;


                itemsProcesosUsuarioLogin = getPaginationProcesosLogin().createPageData();
            }
            prepareList(itemsProcesosUsuarioLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al momento de obtener los items procesados: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error al momento de obtener los items procesados: " + e.getMessage(), e, LOGGER);
        }
        
        return itemsProcesosUsuarioLogin;
    }
    
        /**
     * Mostrar listado de items
     * <p>
     * Muestra el listado de HhppCargueArchivoLog según el usuario en sesion
     *
     * @author bocanegraVm
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void getItemsProcesosUsuarioLoginBD() throws ApplicationException {
        
        if (itemsProcesosUsuarioLoginAux == null) {
            //Se buscan los procesos terminados
            this.estadoUsuarioLogin = 1;
            this.estado = 1;
            itemsProcesosUsuarioLoginAux = ejbHhppCargueArchivoLog.findRangeEstado(null, estado, usuario);
        }
    }
    
    /**
     * Prepara lista que se pueda descargar 
     * @author becerraarmr
     * @param list - lista a revisar con los parametros.
     */
    private void prepareList(List<HhppCargueArchivoLog> list) {
        if(list!=null && !list.isEmpty()){
            List<HhppCargueArchivoLog> aux=new LinkedList<HhppCargueArchivoLog>();
            for (HhppCargueArchivoLog item : list) {
                if(puedeExportar(item)){
                    aux.add(item);
                }
            }
            itemsProcesosUsuarioLogin=aux;
        }
    }
    
    
    public void listInfoByPageCap(int page) {
        try {
            this.estadoUsuarioLogin = 1;
            this.estado = 1;
            itemsProcesosUsuarioLoginAux = ejbHhppCargueArchivoLog.findRangeEstado(null, estado, usuario);
            
            actualCap = page;
            getTotalPaginasCap();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
 
            //Obtenemos el rango de la paginación
            if (!itemsProcesosUsuarioLoginAux.isEmpty()) {
                
                int maxResult;
                if ((firstResult + filasPag) > itemsProcesosUsuarioLoginAux.size()) {
                    maxResult = itemsProcesosUsuarioLoginAux.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }

                itemsArchivosUsuarioLogin = new ArrayList<>();
                for (int k = firstResult; k < maxResult; k++) {
                    itemsArchivosUsuarioLogin.add(itemsProcesosUsuarioLoginAux.get(k));
                }
            }
  
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageCap, en lista de paginación. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPageCap, en lista de paginación. ", e, LOGGER);
        }
    }

    public void pageFirstCap() {
            listInfoByPageCap(1);
    }

  
    public void pagePreviousCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = actualCap - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousCap. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePreviousCap. ", e, LOGGER);
        }
    }


    public void irPaginaCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = Integer.parseInt(numPaginaCap);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaCap, direccionando a página ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irPaginaCap, direccionando a página ", e, LOGGER);
        }
    }


    public void pageNextCap() {
        try {
            int totalPaginas = getTotalPaginasCap();
            int nuevaPageActual = actualCap + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPageCap(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en pageNextCap, direccionando a la siguiente página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNextCap, direccionando a la siguiente página. ", e, LOGGER);
        }
    }


    public void pageLastCap() {

            int totalPaginas = getTotalPaginasCap();
            listInfoByPageCap(totalPaginas);

    }

    public int getTotalPaginasCap() {
        try {
            //obtener la lista original para conocer su tamaño total                 
            int pageSol = itemsProcesosUsuarioLoginAux.size();
            return (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCap, direccionando a la primera página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginasCap, direccionando a la primera página. ", e, LOGGER);
        }
        return 1;
    }


    public String getPageActualCap() {
        paginaListcap = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasCap();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListcap.add(i);
        }
        pageActualCap = String.valueOf(actualCap) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaCap == null) {
            numPaginaCap = "1";
        }
        numPaginaCap = String.valueOf(actualCap);
        return pageActualCap;
    }

    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param usuario valor actualizar
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Buscar el valor del atributo usuario.
     *
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor String que representa el atributo usuario
     */
    public String getUsuario() {
        return this.usuario;
    }

    /**
     * Buscar el valor de la paginación.
     * <p>
     * Busca el valor de la paginación correspondiente.
     *
     * @author becerraarmr
     *
     * @return Un objeto con la información de la paginación
     */
    public PaginationHelper<HhppCargueArchivoLog> getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper<HhppCargueArchivoLog>(tamPagination) {
                /**
                 * Buscar el valor del atributo.
                 * <p>
                 * Busca la cantidad de items encontrados
                 *
                 * @author becerraarmr
                 *
                 * @return la cantidad de items encontrados
                 */
                @Override
                public int getItemsCount() {
                    try {
                        return ejbHhppCargueArchivoLog.countEstado(estado, null);
                    } catch (ApplicationException e) {
                        LOGGER.error("Error en getItemsCount. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    } catch (Exception e) {
                        LOGGER.error("Error en getItemsCount. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    }
                }

                /**
                 * Buscar el valor del atributo.
                 * <p>
                 * Busca el listado de registros según un rango establecido
                 *
                 * @author becerraarmr
                 *
                 * @return un Listado con los registros encontrados
                 */
                @Override
                public List<HhppCargueArchivoLog> createPageData() {
                    try {
                        return ejbHhppCargueArchivoLog.
                                findRangeEstado(new int[]{getPageFirstItem(),
                            getPageLastItem()}, estado, null);
                    } catch (ApplicationException | NumberFormatException e) {
                        LOGGER.error("Error encreatePageData. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    } catch (Exception e) {
                        LOGGER.error("Error encreatePageData. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    }
                }
            };
        }
        return pagination;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return un objeto con la información de la paginación
     */
    public PaginationHelper<HhppCargueArchivoLog> getPaginationProcesosLogin() {
        if (paginationProcesosLogin == null) {
            paginationProcesosLogin = new PaginationHelper<HhppCargueArchivoLog>(tamPagination) {
                /**
                 * Buscar el valor del atributo.
                 * <p>
                 * Busca la cantidad de items encontrados
                 *
                 * @author becerraarmr
                 *
                 */
                @Override
                public int getItemsCount() {
                    try {
                        return ejbHhppCargueArchivoLog.countEstado(estadoUsuarioLogin, usuario);
                    } catch (ApplicationException e) {
                        LOGGER.error("Error en getItemsCount. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    } catch (Exception e) {
                        LOGGER.error("Error en getItemsCount. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    }
                }

                /**
                 * Buscar el valor del atributo.
                 * <p>
                 * Busca el listado de registros según un rango establecido
                 *
                 * @author becerraarmr
                 *
                 * @return un Listado con los registros encontrados
                 */
                @Override
                public List<HhppCargueArchivoLog> createPageData() {
                    try {
                        return ejbHhppCargueArchivoLog.
                                findRangeEstado(new int[]{getPageFirstItem(),
                            getPageLastItem()}, estadoUsuarioLogin, usuario);
                    } catch (ApplicationException | NumberFormatException e) {
                        LOGGER.error("Error en createPageData. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    } catch (Exception e) {
                        LOGGER.error("Error en createPageData. " +e.getMessage(), e);  
                        throw new Error(e.getMessage());
                    }
                }
            };
        }
        return paginationProcesosLogin;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public int getTamPagination() {
        return tamPagination;
    }

    /**
     * Actualizar el valor del atributo.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param tamPagination valor actualizar
     */
    public void setTamPagination(int tamPagination) {
        this.tamPagination = tamPagination;
    }

    /**
     * Solicitar verificar el estado de las listas Cuando se de clic en
     * solicitar el estado se reinician las listas con el fin de poder crear de
     * nuevo las tablas.
     *
     * @author becerraarmr
     */
    public void verificarEstado() {
        verificarEstadoExportar();
    }
    
    public void limpiarArchivoCargado() {
        nameFile = "";
        msgCargaReporte = "";
    }

    /**
     * Activar tiempo nocturno
     * <p>
     * Se activa los parámetros de tiempos nocturnos.
     *
     * @author becerraarmr
     */
    public void activarTiempoNocturno() {
        ejbCargueMasivoHhpp.activarTimerProcesoNocturno();
        listInfoByPageCap(1);
    }

    /**
     * Mostrar los parámetros de proceso nocturno
     *
     * @author becerraarmr
     * @return el String con los parámetros.
     */
    public String parametrosNocturnos() {
        String parametros = null;
        try {
            parametros = ejbCargueMasivoHhpp.verParametros();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de consultar los parámetros nocturnos: " + e.getMessage(), e, LOGGER);
        }
        
        return (parametros);
    }

    /**
     * Convertir un archivo a byte
     * <p>
     * Convierte un archivo a su representación en Bytes.
     *
     * @author becerraarmr
     *
     * @param fileXls archivo a convertir
     *
     * @return un vector de bytes
     */
    private byte[] converterFileToByte(File fileXls) {
        try {
            byte[] bytesArray = new byte[(int) fileXls.length()];
            FileInputStream fis = new FileInputStream(fileXls);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            return bytesArray;
        } catch (IOException e) {
            LOGGER.error("Error en converterFileToByte. " +e.getMessage(), e);  
            throw new Error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error en converterFileToByte. " +e.getMessage(), e);  
            throw new Error(e.getMessage());
        }
    }
    
        /**
     * Mostrar en pantalla archivo Excel
     * <p>
     * Permite mostrar el archivo en Excel que fue procesado según los filtros
     * seleccionados.
     *
     * @author bocanegra vm
     *
     * @param workbook objeto que contiene la data para el archivo en Excel.
     * @param tipoArchivo
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void construirArchivo(String fileNameProcesado, String tipoArchivo) throws ApplicationException 
             {
        try {
            if (fileNameProcesado == null || tipoArchivo == null) {
                return;
            }
            if (!(".xlsx".equalsIgnoreCase(tipoArchivo)
                    || ".txt".equalsIgnoreCase(tipoArchivo)
                    || ".csv".equalsIgnoreCase(tipoArchivo))) {
                return;
            }
            HhppCargueArchivoLog archivoLog = ejbHhppCargueArchivoLog.findArchivoByNombre(fileNameProcesado);
            CargueArchivoLogItem archivoOne;
            String[] campos;
            String[] headerFiltro;
            String[] contenidoFiltro;
            String[] header;
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemFacadeLocal.findByIdArchivoGeneral(archivoLog.getIdArchivoLog());
                if (!registrosBd.isEmpty()) {
                    archivoOne = registrosBd.get(0);
                    campos = archivoOne.getEncabezadoCampos().split("\\/");
                    headerFiltro = campos[0].split("\\|");
                    contenidoFiltro = campos[1].split("\\|");
                    header = campos[2].split("\\|");
                    if (tipoArchivo.equalsIgnoreCase(".xlsx")) {
                        exportExcel(headerFiltro, contenidoFiltro, header, fileNameProcesado);
                    } else {
                        downloadCvsTxtDet(headerFiltro, contenidoFiltro, header, tipoArchivo, fileNameProcesado);
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "No existen registros por exportar en el archivo", ""));
                }
            }else{
                    FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "El archivo no fue encontrado, no es posible realizar el Export", ""));
                }
        } catch (ApplicationException e) {
            LOGGER.error("Error en construirArchivo. " +e.getMessage(), e);  
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en construirArchivo. " +e.getMessage(), e);  
            throw e;
        }
    }
    
    public void construirArchivoEOC_BCSC(HhppCargueArchivoLog archivoLog)
            throws ApplicationException {

        try {

            String[] header;
            CargueArchivoLogItemManager cargueArchivoLogItemManager = new CargueArchivoLogItemManager();
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemManager.findByIdArchivoGeneralAndProcesado(archivoLog.getIdArchivoLog());
                if (!registrosBd.isEmpty()) {

                    header = headerEnviarArchivoEOC_BCSC();

                    StringBuilder sb = new StringBuilder();

                    String[] datosMod;

                    int numeroRegistros = registrosBd.size();
                    int numPaginas = numeroRegistros / NUM_REGISTROS_PAGINA;
                    byte[] csvData;
                    if (numeroRegistros > 0) {
                        for (int i = 0; i <= numPaginas; i++) {
                            for (int j = 0; j < header.length; j++) {
                                sb.append(header[j]);
                                if (j < header.length) {
                                    sb.append(",");
                                }
                            }
                        }
                        sb.append("\n");

                        CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();
                        CmtDireccionDetalladaMgl detalladaMgl;

                        for (CargueArchivoLogItem archivo : registrosBd) {

                            datosMod = archivo.getInfoMod().split("\\|");

                            if (datosMod[0] != null) {
                                BigDecimal idHhpp = new BigDecimal(datosMod[0]);
                                HhppMgl hhppMglreg = hhppMglFacadeLocal.findById(idHhpp);
                                if (hhppMglreg != null) {
                                    String idHhp = hhppMglreg.getHhpId().toString();
                                    detalladaMgl = detalleMglManager.findByHhPP(hhppMglreg);
                                    String direccionIgac = "";
                                    String codigoDane;
                                    String centroPoblado = "";
                                    String codigoDaneCity = "";
                                    String codigoDaneDpto = "";
                                    GeograficoPoliticoMgl geograficoPoliticoMgl;
                                    String estrato = "";

                                    String idReg = archivo.getIdComplemento().toString();
                                    sb.append(idReg);
                                    sb.append(",");

                                    sb.append(hhppMglreg.getSuscriptor() != null
                                            ? hhppMglreg.getSuscriptor() : "");
                                    sb.append(",");

                                    sb.append(detalladaMgl.getDireccionDetalladaId() != null
                                            ? detalladaMgl.getDireccionDetalladaId() : "");
                                    sb.append(",");

                                    SubDireccionMgl subDir = hhppMglreg.getSubDireccionObj();
                                    DireccionMgl dir = hhppMglreg.getDireccionObj();

                                    if (subDir != null) {
                                        direccionIgac = subDir.getSdiFormatoIgac();
                                        estrato = subDir.getSdiEstrato().toString();
                                    } else if (dir != null) {
                                        direccionIgac = dir.getDirFormatoIgac();
                                        estrato = dir.getDirEstrato().toString();
                                    }

                                    sb.append(direccionIgac);
                                    sb.append(",");

                                    sb.append(hhppMglreg.getHhpComunidad() != null
                                            ? hhppMglreg.getHhpComunidad() : "");
                                    sb.append(",");

                                    geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                    codigoDane = geograficoPoliticoMgl != null
                                            ? geograficoPoliticoMgl.getGeoCodigoDane() : "";

                                    if (geograficoPoliticoMgl != null) {
                                        centroPoblado = geograficoPoliticoMgl.getGpoNombre();

                                        GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.
                                                findById(geograficoPoliticoMgl.getGeoGpoId());

                                        if (city != null) {
                                            codigoDaneCity = city.getGeoCodigoDane();

                                            GeograficoPoliticoMgl dpto = geograficoPoliticoMglFacadeLocal.
                                                    findById(city.getGeoGpoId());

                                            if (dpto != null) {
                                                codigoDaneDpto = dpto.getGeoCodigoDane();
                                            }
                                        }
                                    }

                                    sb.append(centroPoblado);
                                    sb.append(",");

                                    sb.append(codigoDane);
                                    sb.append(",");

                                    sb.append(codigoDaneCity);
                                    sb.append(",");

                                    sb.append(codigoDaneDpto);
                                    sb.append(",");

                                    sb.append(estrato);
                                    sb.append(",");

                                    sb.append(idHhp);
                                    sb.append(",");

                                    sb.append(hhppMglreg.getNodId() != null ? hhppMglreg.getNodId() : "");
                                    sb.append(",");

                                    sb.append("");
                                    sb.append(",");
                                }

                                sb.append("\n");
                            }
                        }
                    }
                    csvData = sb.toString().getBytes("UTF-8");

                    String fileName = archivoLog.getNombreArchivoEOC();

                    FacesContext fc = FacesContext.getCurrentInstance();
                    HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                    response1.setContentType("application/force.download");
                    response1.setHeader("Content-disposition", "filename=" + fileName);
                    response1.setCharacterEncoding("UTF-8");
                    response1.getOutputStream().write(csvData);
                    response1.getOutputStream().flush();
                    response1.getOutputStream().close();
                    fc.responseComplete();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "No se encontraron registros para el reporte", ""));
                }
            }
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error en construirArchivoEOC_BCSC: " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    public void construirArchivoTcrm(HhppCargueArchivoLog archivoLog)
            throws ApplicationException {

        try {

            String[] header;
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemFacadeLocal.findByIdArchivoGeneralAndProcesado(archivoLog.getIdArchivoLog());
                if (!registrosBd.isEmpty()) {

                    header = headerEnviarArchivoTCRM();

                    StringBuilder sb = new StringBuilder();

                    String[] datosMod;
                    String[] datosOrg;
                    String[] nuevosValores;

                    int numeroRegistros = registrosBd.size();
                    int numPaginas = numeroRegistros / NUM_REGISTROS_PAGINA;
                    byte[] csvData;
                    if (numeroRegistros > 0) {
                        for (int i = 0; i <= numPaginas; i++) {
                            for (int j = 0; j < header.length; j++) {
                                sb.append(header[j]);
                                if (j < header.length) {
                                    sb.append(",");
                                }
                            }
                        }
                        sb.append("\n");

                        CmtDireccionDetalladaMgl detalladaMgl;

                        for (CargueArchivoLogItem archivo : registrosBd) {

                            datosMod = archivo.getInfoMod().split("\\|");
                            datosOrg = archivo.getInfo().split("\\|");
                            nuevosValores = archivo.getNuevosValores().split("\\|");

                            if (datosMod[0] != null) {
                                BigDecimal idHhpp = new BigDecimal(datosMod[0]);
                                HhppMgl hhppMglreg = hhppMglFacadeLocal.findById(idHhpp);
                                if (hhppMglreg != null) {
                                    String idHhp = hhppMglreg.getHhpId().toString();
                                    detalladaMgl = direccionDetalleMglFacade.findByHhPP(hhppMglreg);
                                    String dirDetAnt;
                                    String dirDetNue = "---";
                                    String codigoDane;
                                    String estadoUnit;
                                    GeograficoPoliticoMgl geograficoPoliticoMgl;
                                    String direccionEst = "---";
                                    String estrato = "---";
                                    String nodo = "---";
                                    String na = "---";
                                    String tipoUnidadViviendaHhpp = "---";

                                    switch (archivoLog.getTipoMod()) {
                                        case 1:
                                            //Modificaron cobertura
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            nodo = nuevosValores[0];
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }
                                            break;
                                        case 2:
                                            //Modificaron estrato
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            estrato = nuevosValores[0];
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }
                                            break;
                                        case 3:
                                            //Modificaron direccion
                                            dirDetAnt = datosOrg[1];
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            dirDetNue = nuevosValores[0];
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            detalladaMgl = direccionDetalleMglFacade.findById(new BigDecimal(dirDetNue));
                                            direccionEst = detalladaMgl != null ? detalladaMgl.getDireccionTexto() : "---";
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            int k = 0;
                                            int j = datosMod.length - 3;
                                            for (String dato : datosMod) {
                                                if (k >= 5 && k < j) {
                                                    String celda = dato;
                                                    sb.append(celda);
                                                    sb.append(",");
                                                }
                                                k++;
                                            }
                                            break;
                                        case 4:
                                            //Modificaron tipo unidad (vivienda)
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = nuevosValores[0];
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }
                                            break;
                                        default:
                                            //Modificaron Gral
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            estrato = nuevosValores[0];
                                            sb.append(estrato);
                                            sb.append(",");
                                            nodo = nuevosValores[1];
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }
                                            break;
                                    }
                                }

                                // filas finales
                                String idReg = archivo.getIdComplemento().toString();
                                sb.append(idReg);
                                sb.append(",");

                                String celdaDet = archivo.getDetalle();
                                if (celdaDet != null) {
                                    celdaDet = celdaDet.replaceAll("\\r\\n|\\r|\\n", " ");
                                    sb.append(celdaDet);
                                } else {
                                    sb.append("---");
                                }
                                sb.append(",");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                String fechaProcesamiento = "";
                                if (archivo.getFechaProcesamiento() != null) {
                                    fechaProcesamiento = sdf.format(archivo.getFechaProcesamiento());
                                }

                                String celdaFecha = fechaProcesamiento;
                                sb.append(celdaFecha);

                                sb.append("\n");
                            }
                        }
                    }

                    csvData = sb.toString().getBytes("UTF-8");

                    String fileName = archivoLog.getNombreArchivoTcrm();

                    FacesContext fc = FacesContext.getCurrentInstance();
                    HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                    response1.setContentType("application/force.download");
                    response1.setHeader("Content-disposition", "filename=" + fileName);
                    response1.setCharacterEncoding("UTF-8");
                    response1.getOutputStream().write(csvData);
                    response1.getOutputStream().flush();
                    response1.getOutputStream().close();
                    fc.responseComplete();
                } else {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "No se encontraron registros para el reporte", ""));
                }
            }
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error en construirArchivoTcrm. " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }
        
    
     /**
     * Autor: Victor Bocanegra Reporte para la consulta de nodos
     *
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String exportExcel(String[] headerFiltro,String[] contenidoFiltro,
            String[] header, String nombreArchivo) throws ApplicationException  {
        try {
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("HhppMGLResult");

            int rownum = 0;
            int cellnum = 0;
            
            String[] datosMod;
            
            for (CargueArchivoLogItem archivo : registrosBd) {

                datosMod = archivo.getInfoMod().split("\\|");
                
                Row row = sheet.createRow(rownum);
                if (rownum == 0) {
                    for (String c : headerFiltro) {
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue(c);
                    }
                    rownum++;
                }
                cellnum = 0;
                row = sheet.createRow(rownum);
                if (rownum == 1) {
                    for (String c : contenidoFiltro) {
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue(c);
                    }
                    rownum++;
                }
                cellnum = 0;
                row = sheet.createRow(rownum);
                if (rownum == 2){
                    for (String c : header) {
                        Cell cell = row.createCell(cellnum++);
                        cell.setCellValue(c);
                    }
                    Cell celEst = row.createCell(cellnum++);
                    celEst.setCellValue("ESTADO_PROCESO");
                    Cell celDet = row.createCell(cellnum++);
                    celDet.setCellValue("DETALLE");
                    Cell celFecPro = row.createCell(cellnum++);
                    celFecPro.setCellValue("FECHA_PROCESAMIENTO");
                    rownum++;
                }
                cellnum = 0;
                row = sheet.createRow(rownum);
                

               for(String dato : datosMod){
                   
                Cell cel = row.createCell(cellnum++);
                cel.setCellValue(dato);
                
               }
               Cell cellEstado = row.createCell(cellnum++);
               cellEstado.setCellValue(archivo.getEstadoProceso());
               
               Cell cellDetalle = row.createCell(cellnum++);
               cellDetalle.setCellValue(archivo.getDetalle());
               
               SimpleDateFormat sdf =   new SimpleDateFormat("yyyy_MM_dd-HH:mm:ss");
               String fechaProcesamiento = "";
                if (archivo.getFechaProcesamiento() != null) {
                    fechaProcesamiento = sdf.format(archivo.getFechaProcesamiento());
                }
               Cell cellFechaProceso = row.createCell(cellnum++);
               cellFechaProceso.setCellValue(fechaProcesamiento);
               
                rownum++;
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse responseNod = (HttpServletResponse) fc.getExternalContext().getResponse();
            responseNod.reset();
            responseNod.setContentType("application/vnd.ms-excel");
            
            responseNod.setHeader("Content-Disposition", "attachment; filename=\""
                    + nombreArchivo);
            OutputStream output = responseNod.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();

        } catch (IOException e) {
            LOGGER.error("Error en exportExcel. " +e.getMessage(), e); 
            throw new ApplicationException("Error en exportExcel. ",e);
        } catch (Exception e) {
            LOGGER.error("Error en exportExcel. " +e.getMessage(), e); 
            throw new ApplicationException("Error en exportExcel. ",e);
        }
        return null;
    }
    
    public String downloadCvsTxtDet(String[] headers, String[] contenidos, 
            String[] campos, String ext, String filename) throws ApplicationException {
        try {
            final StringBuffer sb = new StringBuffer();
            String[] datosMod;
            
            String[] headerFiltros = new String[headers.length];
            String[] contenidoFiltros = new String[contenidos.length];
            String[] camposFi = new String[campos.length + 3];
            
            int k = 0;

            
            
            for(String header: headers){
                headerFiltros[k]=header;
                k++;
            }
            k = 0;
            for(String contenido: contenidos){
                contenidoFiltros[k]=contenido;
                k++;
            }
            k = 0;
            for(String campo: campos){
                camposFi[k]=campo;
                k++;
            }
          
            camposFi[k++] = "ESTADO_PROCESO";
            camposFi[k++] = "DETALLE";
            camposFi[k++] = "FECHA_PROCESAMIENTO";



            if (registrosBd != null) {
                int numeroRegistros = registrosBd.size();
                byte[] csvData;
                if (numeroRegistros > 0) {
                    for (int j = 0; j < headerFiltros.length; j++) {
                        sb.append(headerFiltros[j]);
                        if (j < headerFiltros.length) {
                            sb.append(",");
                        }
                    }
                    sb.append("\n");
                    for (int j = 0; j < contenidoFiltros.length; j++) {
                        sb.append(contenidoFiltros[j]);
                        if (j < contenidoFiltros.length) {
                            sb.append(",");
                        }
                    }
                    sb.append("\n");
                    for (int j = 0; j < camposFi.length; j++) {
                        sb.append(camposFi[j]);
                        if (j < camposFi.length) {
                            sb.append(",");
                        }
                    }

                    sb.append("\n");

                    for (CargueArchivoLogItem archivo : registrosBd) {
                        datosMod = archivo.getInfoMod().split("\\|");

                        for (String dato : datosMod) {
                            String celda = dato;
                            sb.append(celda);
                            sb.append(",");
                        }
                        // filas finales
                        String celdaEst = archivo.getEstadoProceso();
                        sb.append(celdaEst);
                        sb.append(",");

                        String celdaDet = archivo.getDetalle();
                        sb.append(celdaDet);
                        sb.append(",");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd-HH:mm:ss");
                        String fechaProcesamiento = "";
                        if (archivo.getFechaProcesamiento() != null) {
                            fechaProcesamiento = sdf.format(archivo.getFechaProcesamiento());
                        }

                        String celdaFecha = fechaProcesamiento;
                        sb.append(celdaFecha);

                        sb.append("\n");
                    }
                 
                }     
                csvData = sb.toString().getBytes("UTF-8");
                String nameArchivo = filename.substring(0, filename.lastIndexOf('.'));
                String fileName = nameArchivo + ext;

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setContentType("application/force.download");
                response1.setHeader("Content-disposition", "filename=" + fileName);
                response1.setCharacterEncoding("UTF-8");
                response1.getOutputStream().write(csvData);
                response1.getOutputStream().flush();
                response1.getOutputStream().close();
                fc.responseComplete();
            } else {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron registros para el reporte", ""));
            }

        } catch (IOException e) {
            LOGGER.error("Error en downloadCvsTxtDet. " +e.getMessage(), e);  
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error en downloadCvsTxtDet. " +e.getMessage(), e);  
            throw new ApplicationException(e);
        }
        return "case1";
    }
    /**
     * Metodo para revertir las modificaciones para el archivo seleccionado
     * @author bocanegra Vm
     * @param archivoResumen archivo para revertir.
     */
    public void revertirCambios(HhppCargueArchivoLog archivoResumen) {

        BufferedReader br = null;

        try {
            mensajeReversion="";
            
            sftpUtils = new SFTPUtils();

            Parametros param = resourceEJB.queryParametros(Constant.USARIO_TCRM_SFTP);
            if (param != null) {
                usuarioSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.PASSWORD_TCRM_SFTP);
            if (param != null) {
                passSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.HOST_TCRM_SFTP);
            if (param != null) {
                hostSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.PUERTO_TCRM_SFTP);
            if (param != null) {
                puertoSftp = Integer.parseInt(param.getValor());
            }
            param = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.RUTA_ALMACENAR_TEMP_ARCHIVO_TCRM);
            if (param != null) {
                rutaBajarTempoArchivosSftp = param.getValor();
            }

            param = resourceEJB.queryParametros(Constant.RUTA_BAJAR_ARCHIVO_TCRM);
            if (param != null) {
                rutaBajarArchivosSftp = param.getValor();
            }

            sftpUtils.connect(usuarioSftp, passSftp, hostSftp, puertoSftp);

            File archivos = new File(rutaBajarTempoArchivosSftp);
            //Creo el directorio si no existe
            archivos.mkdirs();

            String fileName = archivoResumen.getNombreArchivoTcrm();

            sftpUtils.getFile(rutaBajarArchivosSftp, rutaBajarTempoArchivosSftp, fileName);

            List<String> archivoArever = sftpUtils.listarFicherosPorCarpeta(archivos);


            if (archivoArever.size() == 1) {
                
                String nameFileCsv = archivoArever.get(0);
                
                if (nameFileCsv.equalsIgnoreCase(archivoResumen.getNombreArchivoTcrm())) {
                    //Valido si ya fue revertido el archivo
                    if (archivoResumen.getUtilizoRollback().equalsIgnoreCase("N")) {
                        //Si no se ha revertido valido el contenido del archivo
                        File archivo = new File(rutaBajarTempoArchivosSftp + File.separator + nameFileCsv);
                        FileReader fr = new FileReader(archivo);

                        String line;
                        //Se define separador ","
                        String cvsSplitBy = ",";

                        br = new BufferedReader(fr);
                        int i = 0;
                        int revertio = 0;
                        int noRevertio = 0;
                        encabezado = null;
                        registrosRev=new ArrayList<String[]>();

                        while ((line = br.readLine()) != null) {
                            if (!line.isEmpty()) {
                                if (i == 0) {
                                    LOGGER.error("Esta linea no se lee");
                                    encabezado = line.split(cvsSplitBy);
                                } else {
                                    String[] datos = line.split(cvsSplitBy);
                                    String estadoUnit;
                                    //Busco el registro por id complemento y id del archivo gral
                                    BigDecimal complemento = new BigDecimal(datos[datos.length - 3]);
                                    cargueArchivoLogItem = cargueArchivoLogItemFacadeLocal.
                                            findByIdArchivoLogAndIdComplemento(archivoResumen.getIdArchivoLog(), complemento);
                                    if (cargueArchivoLogItem != null) {
                                        //Si hay registro para revertir tomo la info original
                                        String[] datosOriginales = cargueArchivoLogItem.getInfo().split("\\|");

                                        String[] regRev = new String[datos.length + 2];
                                        
                                        estadoUnit = datos[4];
                                        
                                        int k = 0;
                                        for (String dato : datos) {
                                            regRev[k] = dato;
                                            k++;
                                        }
                                        
                                        //Envio el registro para que se realize el proceso de modificacion nuevamente
                                        DetalleCargaHhppMgl procesarItem =
                                                ejbCargueMasivoHhpp.
                                                procesarDataRevertir(datosOriginales,
                                                DetalleCargaHhppMgl.PROCESADO, archivoResumen,estadoUnit);

                                        cargueArchivoLogItem.setLlegoRollback("Y");
                                        if (procesarItem.getEstado().equalsIgnoreCase("PROCESADO")) {
                                            cargueArchivoLogItem.setModRoolback("Y");
                                            revertio++;
                                        } else {
                                            cargueArchivoLogItem.setModRoolback("N");
                                            noRevertio++;
                                        }
                                        regRev[k++] = procesarItem.getEstado();
                                        regRev[k++] = procesarItem.getDetalle();
                                        registrosRev.add(regRev);
                                        //actualizo el registro despues del proceso
                                        cargueArchivoLogItemFacadeLocal.update(cargueArchivoLogItem);
                                    } else {
                                        LOGGER.error("La informacion del registro no es la misma de la BD.");
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "La informacion del registro no es la misma de la BD.", ""));
                                    }
                                }
                                i++;
                            }
                        }
                        //Cierro las linea del archivo
                        br.close();
                        // Marco el archivo resumen
                        archivoResumen.setUtilizoRollback("Y");

                        ejbHhppCargueArchivoLog.update(archivoResumen);
                        exportExcelResultReverter();

                        mensajeReversion = "Se realizo rollback de:  " + revertio + " registros y "
                                + " no se pudieron revertir: " + noRevertio + " registros";

                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                mensajeReversion, ""));
                    } else {
                        LOGGER.info("El archivo ya fue revertido");
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "El archivo ya fue revertido", ""));
                    }
                } else {
                    LOGGER.error("El Nombre del archivo bajado de TCRM no "
                            + "corresponde al guardado en la Base de datos.");
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El Nombre del archivo bajado de TCRM no "
                            + "corresponde al guardado en la Base de datos.", ""));
                }

                listInfoByPageCap(1);
                notificar();
            } else {
                LOGGER.error("Hay mas de un archivo para procesar en la ruta: " + rutaBajarTempoArchivosSftp + "");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Hay mas de un archivo para procesar en la ruta: " + rutaBajarTempoArchivosSftp + "", ""));
            }
        } catch (JSchException | IllegalAccessException | IOException | SftpException | ApplicationException | NumberFormatException e) {
             FacesUtil.mostrarMensajeError("Error en revertirCambios. ", e, LOGGER);
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en revertirCambios. ", e, LOGGER);
        } finally {
            if (br != null) {
                try {
                    br.close();
                    
                    //Borro las temporales que se crearon para el proceso
                    sftpUtils.deleteWithChildren(rutaBajarTempoArchivosSftp);
                    
                } catch (IOException e) {
                    FacesUtil.mostrarMensajeError("Error en revertirCambios. ", e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en revertirCambios. ", e, LOGGER);
                }
            }
        }


    }
    /**
     * Metodo para validar que archivo fue revertido
     * @author bocanegra Vm
     * @param cargueArchivoLog
     * @param archivoResumen archivo para validar.
     * @return 
     */
    public boolean validarRevertirCambios(HhppCargueArchivoLog cargueArchivoLog) {

        boolean respuesta = false;
        sftpUtils = new SFTPUtils();

        try {

            Parametros param = resourceEJB.queryParametros(Constant.USARIO_TCRM_SFTP);
            if (param != null) {
                usuarioSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.PASSWORD_TCRM_SFTP);
            if (param != null) {
                passSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.HOST_TCRM_SFTP);
            if (param != null) {
                hostSftp = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.PUERTO_TCRM_SFTP);
            if (param != null) {
                puertoSftp = Integer.parseInt(param.getValor());
            }

            param = resourceEJB.queryParametros(Constant.RUTA_BAJAR_ARCHIVO_TCRM);
            if (param != null) {
                rutaBajarArchivosSftp = param.getValor();
            }

            sftpUtils.connect(usuarioSftp, passSftp, hostSftp, puertoSftp);
            nombreArchivos = sftpUtils.getNameFiles(rutaBajarArchivosSftp);

            if (!nombreArchivos.isEmpty()) {
                for (String nombreFile : nombreArchivos) {
                    
                    //Valido nombre de archivo seleccionado con los de la lista de archivos sftp
                    if (nombreFile.equalsIgnoreCase(cargueArchivoLog.getNombreArchivoTcrm())) {
                        //Si son iguales pregunto si ya tiene roolback
                        if (cargueArchivoLog.getUtilizoRollback().equalsIgnoreCase("Y")) {
                            respuesta = false;
                        } else {
                            respuesta = true;
                        }
                    } else {
                        LOGGER.info("Nombre de archivo diferentes");
                    }

                }
            }

        } catch (ApplicationException | JSchException | SftpException | IOException | IllegalAccessException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en validarRevertirCambios. ", e, LOGGER);
        } 
        return respuesta;
    }

    /**
     * Valida si un archivo fue enviado a TCRM
     *
     * @author bocanegra Vm
     * @param cargueArchivoLog a validar.
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean seEnvioArchivoTcrm(HhppCargueArchivoLog cargueArchivoLog)
            throws ApplicationException {

        boolean envio = false;

        if (cargueArchivoLog.getEnvioTcrm().equalsIgnoreCase("Y")) {
            envio = true;
        }

        return envio;
    }
    
    /**
     * Valida si un archivo fue enviado a EOC
     *
     * @author bocanegra Vm
     * @param cargueArchivoLog a validar.
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public boolean seEnvioArchivoEOC_BCSC(HhppCargueArchivoLog cargueArchivoLog)
            throws ApplicationException {

        boolean envio = false;

        if (cargueArchivoLog.getEnvioEocBcsc() != null && 
                cargueArchivoLog.getEnvioEocBcsc().equalsIgnoreCase("Y")) {
            envio = true;
        }

        return envio;
    }

    
    /**
     * metodo que se encarga de verificar que se este procesando el archivo de
     * cargue
     *
     * @author Andres Leal
     * @param thread
     */
    public void message(CargueMasivoHhppManager thread) {
        FacesMessage facesMessage;
        do {
            msgCargaReporte = thread.getMsgProceso();
            if (thread.isAlive() != true) {
                if (thread.getMsgProceso().equals(Constant.MSG_REPORTE_EXITOSO)) {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            Constant.MSG_REPORTE_EXITOSO, "");
                    setMensajesThread(thread);                    
                } else if(thread.getMsgProceso().equals(Constant.MSG_REPORTE_NOMBRE_ARCHIVO)) {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Constant.MSG_REPORTE_NOMBRE_ARCHIVO, "");
                    setMensajesThread(thread);
                } else  if(thread.getMsgProceso().contains(Constant.MSG_REPORTE_NOCTURNO)) {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            thread.getMsgProceso(), "");
                    setMensajesThread(thread);  
                }
                else {
                    facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            thread.getMsgProceso(), "");
                    setMensajesThread(thread);
                }
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                msgCargaReporte = thread.getMsgProceso();
                mensajeFinalizado = false;
                finalizaCargue = false;
            }
                      
        } while (thread.isAlive());        
    }
    
    /**
     * Vacia los valores de las variables del hilo
     * @param thread 
     */
    public void setMensajesThread(CargueMasivoHhppManager thread){
        thread.setMsgProceso("");
        thread.setFileName("");
    }

    /**
     * metodo que se encarga de verificar que se encarga de verificar que el
     * hilo se encuentre vivo
     *
     * @author Jonathan Peña
     */
    private void procesarMensaje() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        msgCargaReporte = "";
        if (session != null) {
            Object object = session.getAttribute("THREADCARGAMASIVA_MGL");
            if (object instanceof CargueMasivoHhppManager) {
                CargueMasivoHhppManager thread
                        = (CargueMasivoHhppManager) session.
                                getAttribute("THREADCARGAMASIVA_MGL");
                if (thread != null) {
                    if (thread.isAlive()) {
                        mensajeFinalizado = true;
                    } else {
                        mensajeFinalizado = false;
                    }
                } else {
                    mensajeFinalizado = false;
                }
            }
        }
    }

     
    public String getNumPaginaCap() {
        return numPaginaCap;
    }

    public void setNumPaginaCap(String numPaginaCap) {
        this.numPaginaCap = numPaginaCap;
    }

    public List<Integer> getPaginaListcap() {
        return paginaListcap;
    }

    public void setPaginaListcap(List<Integer> paginaListcap) {
        this.paginaListcap = paginaListcap;
    }

    public List<HhppCargueArchivoLog> getItemsArchivosUsuarioLogin() {
        return itemsArchivosUsuarioLogin;
    }

    public void setItemsArchivosUsuarioLogin(List<HhppCargueArchivoLog> itemsArchivosUsuarioLogin) {
        this.itemsArchivosUsuarioLogin = itemsArchivosUsuarioLogin;
    }

    public boolean isMensajeFinalizado() {
        return mensajeFinalizado;
    }

    public void setMensajeFinalizado(boolean mensajeFinalizado) {
        this.mensajeFinalizado = mensajeFinalizado;
    }

    public boolean isFinalizaCargue() {
        return finalizaCargue;
    }

    public void setFinalizaCargue(boolean finalizaCargue) {
        this.finalizaCargue = finalizaCargue;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfilVt) {
        this.perfil = perfilVt;
    }
    
    public void exportExcelResultReverter() throws ApplicationException {
        try {
            
            String[] camposFi;
            camposFi = new String[encabezado.length + 2];
            
            int k = 0;
            for(String campo: encabezado){
                camposFi[k]=campo;
                k++;
            }
            camposFi[k++] = "ESTADO_RESULTADO_REVERSION";
            camposFi[k++] = "DETALLE_RESULTADO_REVERSION";
            
          
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("RevertirCambiosHHPP");
            XSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            XSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            style.setFont(font);
            
            int rownum = 0;
            if (!registrosRev.isEmpty()) {
                int b = 0;
                int d = 0;
                int i = 0;
                for (String[] a : registrosRev) {
                    Row row = sheet.createRow(rownum);
                    int cellnum = 0;
                    if (rownum == 0) {
                        for (String c : camposFi) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                            if (c.equalsIgnoreCase("ESTADO_RESULTADO_REVERSION")) {
                                cell.setCellStyle(style);
                                b = i;
                            }
                            if (c.equalsIgnoreCase("DETALLE_RESULTADO_REVERSION")) {
                                cell.setCellStyle(style);
                                d = i;
                            }
                            i++;
                        }
                        rownum++;
                    }
                    cellnum = 0;
                    row = sheet.createRow(rownum);
                    int f=0;
                    
                    for (String dato : a) {
                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(dato);
                        if (f == b || f == d) {
                            cell2.setCellStyle(style);
                        }
                        f++;
                    }
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.reset();
                response1.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

                response1.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_Resultado_Reversion" + "_" + formato.format(new Date())
                        + ".xlsx\"");
                OutputStream output = response1.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron registros para el reporte", ""));
            }
        } catch (IOException e) {
            LOGGER.error("Error en exportExcelResultReverter. " +e.getMessage(), e); 
            throw new ApplicationException("Error en exportExcelResultReverter. ",e);
        } catch (Exception e) {
            LOGGER.error("Error en exportExcelResultReverter. " +e.getMessage(), e); 
            throw new ApplicationException("Error en exportExcelResultReverter. ",e);
        }
    }

    public String getMensajeReversion() {
        return mensajeReversion;
    }

    public void setMensajeReversion(String mensajeReversion) {
        this.mensajeReversion = mensajeReversion;
    }
    
   private String findValor(String[] data, int i) {
        try {
            return data[i];
        } catch (RuntimeException e) {
            //Si hay algún error al buscar en el arreglo returne null.
            FacesUtil.mostrarMensajeError("Error en findValor. ", e, LOGGER);
            return null;
        } catch (Exception e) {
            //Si hay algún error al buscar en el arreglo returne null.
            FacesUtil.mostrarMensajeError("Error en findValor. ", e, LOGGER);
            return null;
        }
    }
   
       
    public boolean validarOpcionSubirFile() {
        return validarEdicionRol(BTNSBARHP);
    }
    
    
    public boolean validarOpcionRegistro() {
        return validarEdicionRol(BTNRGPRHP);
    }
       
    public boolean validarOpcionactualizar() {
        return validarEdicionRol(BTNUPDHHP);
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
