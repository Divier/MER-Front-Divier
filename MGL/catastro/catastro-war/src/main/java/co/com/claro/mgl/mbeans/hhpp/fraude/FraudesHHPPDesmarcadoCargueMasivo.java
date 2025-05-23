/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.hhpp.fraude;

import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mgl.dtos.CmtFraudesMasivoHHPPDesmarcadoDtoMgl;
import co.com.claro.mgl.dtos.FraudesHHPPDesmarcadoMasivoDto;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.procesomasivo.CargueArchivoLogItemFacadeLocal;
import co.com.claro.mgl.facade.procesomasivo.HhppCargueArchivoLogFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import static co.com.claro.mgl.utils.Constant.SHEET_NAME;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author enriquedm
 */
@ManagedBean(name = "fraudesHHPPDesmarcadoCargueMasivo")
@ViewScoped
public class FraudesHHPPDesmarcadoCargueMasivo implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(FraudesHHPPDesmarcadoCargueMasivo.class);
    private String nameFile;
    private String msgCargaReporte;
    private boolean showPanelCargueMarcado;
    private boolean showPanelTablaProcesos;
    private UploadedFile upFile;
    private boolean enproceso;
    private Integer refresh;
    private boolean finish;
    private int minutos;
    private String nombre;
    private boolean mensajeError;
    private List<FraudesHHPPDesmarcadoMasivoDto> listaModificacion = null;
    private List<FraudesHHPPDesmarcadoMasivoDto> reporteFinal;

    private List<HhppCargueArchivoLog> itemsArchivosUsuarioLogin = null;
    private List<HhppCargueArchivoLog> itemsProcesosUsuarioLoginAux = null;
    List<CargueArchivoLogItem> registrosBd = null;
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    private short estadoUsuarioLogin;
    private short estado;
    private int actualCap;
    private List<Integer> paginaListcap;
    private String pageActualCap;
    private String numPaginaCap = "1";
    private int numRegAProcesar;
    private int numRegProcesados;
    private String usuarioProceso;

    private List<String[]> registros;
    private String[] encabezado = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private SecurityLogin securityLogin;
    private String usuario = null;
    private int perfil = 0;
    private boolean progress;

    private static final Integer REFRESH_MIN = 100000;
    private static final Integer REFRESH_MAX = 10000;
    private static final String ARCHIVO_EN_PROCESO = "Se esta procesando un archivo";
    private static final String ARCHIVO_NO_CUMPLE = "El contenido del archivo no cumple con los requisitos ";
    private static final String ARCHIVO_INVALIDO = "No es valido el tipo de archivo ";
    private static final String ARCHIVO_NULL = "Por favor seleccionar un archivo para cargar ";
    private static final String NO_REGISTROS = "No hay registros a procesar ";
    private static final String NO_HAY_REGISTROS = "No existen registros a exportar";

    private static final String LINEA = "Línea";
    private static final String ID_DIRECCION_DETALLADA = "ID Dirección Detallada";
    private static final String DEPARTAMENTO = "Departamento";
    private static final String MUNICIPIO = "Municipio";
    private static final String CENTRO_POBLADO = "Centro Poblado";
    private static final String NODO = "Nodo";
    private static final String DIRECCION = "Dirección";
    private static final String COD_MARCAS_FRAUDE = "Código Marcas de Fraude";
    List<FraudesHHPPDesmarcadoMasivoDto> listGeneral = new ArrayList<FraudesHHPPDesmarcadoMasivoDto>();
    
    //Opciones agregadas para Rol
    private final String BTPROARCHDESMAR = "BTPROARCHDESMAR"; 

    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal direccionDetalleMglFacade;
    @EJB
    private HhppCargueArchivoLogFacadeLocal ejbHhppCargueArchivoLog;
    @EJB
    private CargueArchivoLogItemFacadeLocal cargueArchivoLogItemFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    /**
     * Creates a new instance of MasivoModificacionBean
     */
    public FraudesHHPPDesmarcadoCargueMasivo() {
        try {
//            this.refresh = REFRESH_MIN;
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuario = securityLogin.getLoginUser();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al FraudesHHPPDesmarcadoCargueMasivo. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al FraudesHHPPDesmarcadoCargueMasivo. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {
        showPanelCargueMarcado = true;
        showPanelTablaProcesos = true;
        this.minutos = tiempoReporte(Constant.TIEMPO_REPORTE);
        limpiarValores();
        if (!CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsProcessing()) {
            if (CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getEndProcessDate() != null && new Date().before(sumarTiempoFecha(CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getEndProcessDate(), this.minutos))) {
                numRegProcesados = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosProcesados();
                numRegAProcesar = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosAProcesar();
                nameFile = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNombreArchivo();
                setProgress(true);
                this.usuarioProceso = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getUserRunningProcess();
            } else {
                limpiarValores();
                setProgress(false);
                setFinish(false);
                try {
                    CmtFraudesMasivoHHPPDesmarcadoDtoMgl.cleanBeforeStart();
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                }
            }
        } else {
            numRegProcesados = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosProcesados();
            numRegAProcesar = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosAProcesar();
            setProgress(true);
            this.usuarioProceso = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getUserRunningProcess();
        }
        pageFirstCap();

    }

    public void showHideValidarCargue() {
        if (showPanelCargueMarcado) {
            showPanelCargueMarcado = false;
        } else {
            showPanelCargueMarcado = true;
        }
    }

    public void showHideTablaProcesos() {
        if (showPanelTablaProcesos) {
            showPanelTablaProcesos = false;
        } else {
            showPanelTablaProcesos = true;
        }
    }

    /**
     * Metodo para cargar el archivo de las modificaciones
     */
    public void cargarArchivo() {
        try {
//            this.refresh = REFRESH_MIN;
//            if (CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsProcessing()) {
//                String msn = ARCHIVO_EN_PROCESO;
//                FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
//                return;
//            } else {
//                CmtFraudesMasivoHHPPDesmarcadoDtoMgl.cleanBeforeStart();
//            }
            this.mensajeError = false;
            this.listaModificacion = null;
            this.reporteFinal = null;
            if (upFile == null && upFile.getFileName() != null) {
                String msn = ARCHIVO_NULL;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                this.nombre = null;
                return;
            } else if (upFile.getFileName() == null) {
                String msn = ARCHIVO_NULL;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                this.nombre = null;
                return;
            } else {
                this.nombre = upFile.getFileName();
            }
            String ext = upFile.getFileName().substring(upFile.getFileName().length() - 4, upFile.getFileName().length());
            if (!ext.toUpperCase().equals(".CSV")) {
                String msn = ARCHIVO_INVALIDO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                this.nombre = null;
                return;
            }

            listaModificacion = generarListaGeneral();
            if (listaModificacion == null) {
                throw new ApplicationException("Error en la estructura del archivo. No corresponde, debe contar con toda la información de la cabecera");
            }

            if (itemsProcesosUsuarioLoginAux != null) {
                if (itemsProcesosUsuarioLoginAux.size() > 0) {
                    if (itemsProcesosUsuarioLoginAux.get(0).getNombreArchivoCargue() == this.nombre) {
                        throw new ApplicationException("El archivo que intenta procesar, se encuentra activo");
                    }
                }
            }

            if (listaModificacion.size() > 0) {
//                this.refresh = REFRESH_MIN;
                if (listaModificacion != null && !listaModificacion.isEmpty()) {
                    direccionDetalleMglFacade.actualizarCargueFraudesMasivoHHPPDesmarcado(listaModificacion, usuario, perfil, this.nombre);
//                    this.finish = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsFinished();
                }
                if (listaModificacion.size() > 0) {
                    setProgress(true);
                    this.usuarioProceso = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getUserRunningProcess();
                } else {
                    setProgress(false);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "No se encontraron resultados para el cargue.", ""));
                }
            }
         
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo de FraudesHHPPDesmarcadoCargueMasivo.  " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en FraudesHHPPDesmarcadoCargueMasivo. " + e.getMessage(), e, LOGGER);
        }
    }

    private List<FraudesHHPPDesmarcadoMasivoDto> generarListaGeneral() throws IOException {
        String line;
        String cvsSplitBy = ";";
        File archivo = null;

        try {
            Date fecha = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
            String fechaN = format.format(fecha);
            String fileName = fechaN;
            fileName += upFile.getFileName();
            String[] fileArr = fileName.split(".csv");
            String nombreFinal = fileArr[0];
            File file = new File(System.getProperty("user.dir"));
            archivo = File.createTempFile(nombreFinal, ".csv", file);
            FileOutputStream output = new FileOutputStream(archivo);
            output.write(upFile.getContent());
            output.close();

            int i = 0;
            encabezado = null;
            listGeneral = new ArrayList<FraudesHHPPDesmarcadoMasivoDto>();
            BufferedReader bre = new BufferedReader(new FileReader(archivo));
            while ((line = bre.readLine()) != null) {
                FraudesHHPPDesmarcadoMasivoDto masivo = new FraudesHHPPDesmarcadoMasivoDto();
                if (!line.isEmpty()) {
                    if (i == 0) {
                        LOGGER.error("Esta línea no se lee");
                        encabezado = line.split(cvsSplitBy);
                    } else {
                        String[] datos = line.split(cvsSplitBy);
                        masivo.setLinea(i);
                        if (datos != null) {
                            BigDecimal idDireccionDetallada = BigDecimal.ZERO;

                            if (datos[0] != null && !datos[0].isEmpty()) {
                                idDireccionDetallada = new BigDecimal(datos[0]);
                                masivo.setIdDireccionDetallada(idDireccionDetallada);
                            }else{
                                masivo.setIdDireccionDetallada(null);
                            }
                            masivo.setDepartamento(datos[1] != null ? datos[1] : null);
                            masivo.setMunicipio(datos[2] != null ? datos[2] : null);
                            masivo.setCentroPoblado(datos[3] != null ? datos[3] : null);
                            masivo.setNodo(datos[4] != null ? datos[4] : null);
                            masivo.setDireccion(datos[5] != null ? datos[5] : null);
                            if (datos.length >= 6) {
                                if (datos.length == 6) {
                                    throw new ApplicationException("Error en la estructura del archivo. No corresponde, debe contar con toda la información de la cabecera");
                                } else {
                                masivo.setCodMarcaNuevo(datos[6] != null ? datos[6] : null);
                                    if (datos.length > 7) {
                                        throw new ApplicationException("Error en la estructura del archivo. No corresponde, debe contar con toda la información de la cabecera");
                                    } 
                                }
                            }
                        }
                        listGeneral.add(masivo);
                    }
                }
                i++;
            }

            listaModificacion = listGeneral;
            bre.close();
            FileToolUtils.deleteFile(archivo);
        } catch (IOException e) {
            LOGGER.info("Error en generarListaGeneral de FraudesHHPPDesmarcadoCargueMasivo : " + e);
            if (archivo != null) {
                try {
                    Files.deleteIfExists(archivo.toPath());
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
        } catch (ApplicationException ex) {
            LOGGER.error(ex);
            if (archivo != null) {
                try {
                    Files.deleteIfExists(archivo.toPath());
                } catch (IOException exc) {
                    LOGGER.error(exc.getMessage());
                }
            }
            return null;
        }
        return listGeneral;
    }

    /**
     * metodo para sumar minutos a la fecha de terminacion del reporte
     *
     * @param fecha
     * @param minutos
     * @return
     */
    private Date sumarTiempoFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     *
     * @param nombreParametro
     * @return
     */
    private Integer tiempoReporte(String nombreParametro) {
        Integer numero;
        Integer DEFAULT = 5;
        try {
            ParametrosMgl parametrosMgl = parametrosFacade.findByAcronimoName(nombreParametro);
            if (parametrosMgl != null) {
                numero = Integer.parseInt(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            LOGGER.error("Error tiempoMaximo de FraudesHHPPDesmarcadoCargueMasivo " + e.getMessage());
            return DEFAULT;
        }
        return numero;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameFile() {
        nameFile = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNombreArchivo();
        return nameFile;
    }

    /**
     * Diana -> metodo para llamar los respectivos tipos de descargar de archivos
     *
     * @param num
     */
    public void generarReporteFinal(int opcion, HhppCargueArchivoLog archivoLog) {

        if (archivoLog != null) {
            try {
                construirArchivo(opcion, archivoLog);
            } catch (ApplicationException ex) {
                String mensaje = "Ocurrió un error en generarReporteFinal";
                FacesUtil.mostrarMensajeError(mensaje + ex.getMessage(), ex, LOGGER);
            }
        }else{
             FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "El proceso aun no se ha terminado", ""));
        }
        pageFirstCap();
    }

    /**
     * Diana -> metodo para exportar el resultado a xls
     *
     * @param reporte
     */
    private void exportarArchivoXls(List<FraudesHHPPDesmarcadoMasivoDto> reporte) {

        try {
            if (reporte != null && !reporte.isEmpty()) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                mapDataEstado.put(String.valueOf(fila++), cabeceraXls());
                for (FraudesHHPPDesmarcadoMasivoDto r : reporte) {
                    String linea = String.valueOf(r.getLinea());
                    String idDireccionDetallada = r.getIdDireccionDetallada() != null ? r.getIdDireccionDetallada().toString() : "";

                    Object[] registros = new Object[]{linea, idDireccionDetallada, r.getDepartamento(), r.getMunicipio(),
                        r.getCentroPoblado(), r.getNodo(), r.getDireccion(), r.getCodMarca()};
                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                fillSheetbookCsv(workbook, SHEET_NAME, mapDataEstado);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRls = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRls.reset();
                responseRls.setContentType("application/vnd.ms-excel");
                SimpleDateFormat formato = new SimpleDateFormat("-yyyy_MMM_dd-hh_mm");
                Calendar c_getDate = Calendar.getInstance();
                c_getDate.setTime(new Date());
                formato.format(c_getDate);
                String nombreArchivo = this.nombre.substring(0, this.nombre.length() - 4);
                responseRls.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + "_" + formato + ".xls");
                OutputStream output = responseRls.getOutputStream();
                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, NO_HAY_REGISTROS, ""));
            }
        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel xls";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en exportarArchivoXls class FraudesHHPPDesmarcadoCargueMasivo " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Diana -> método para exportar el resultado a Csv
     *
     * @param reporte
     */
    private void exportarArchivoCsv(List<FraudesHHPPDesmarcadoMasivoDto> reporte) {
        try {
            if (reporte != null && !reporte.isEmpty()) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                mapDataEstado.put(String.valueOf(fila++), cabeceraCsv());
                for (FraudesHHPPDesmarcadoMasivoDto r : reporte) {
                    String linea = String.valueOf(r.getLinea());
                    String idDireccionDetallada = r.getIdDireccionDetallada() != null ? r.getIdDireccionDetallada().toString() : "";

                    StringBuilder registro = new StringBuilder();
                    registro.append(linea).append(Constant.SEPARADOR).append(idDireccionDetallada).append(Constant.SEPARADOR).append(r.getDepartamento())
                            .append(Constant.SEPARADOR).append(r.getMunicipio()).append(Constant.SEPARADOR).append(r.getCentroPoblado())
                            .append(Constant.SEPARADOR).append(r.getNodo()).append(Constant.SEPARADOR).append(r.getDireccion())
                            .append(Constant.SEPARADOR).append(r.getCodMarca());
                    Object[] registros = new Object[]{registro.toString()};
                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                
                fillSheetbookCsv(workbook, SHEET_NAME, mapDataEstado);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRls = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRls.reset();
                responseRls.setContentType("application/vnd.ms-excel");
                SimpleDateFormat formato = new SimpleDateFormat("-yyyy_MMM_dd-hh_mm");
                Calendar c_getDate = Calendar.getInstance();
                c_getDate.setTime(new Date());
                formato.format(c_getDate);
                String nombreArchivo = this.nombre.substring(0, this.nombre.length() - 4);
                responseRls.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + "_" + formato + ".csv");
                OutputStream output = responseRls.getOutputStream();
                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, NO_HAY_REGISTROS, ""));
            }
        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel xls";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en exportarArchivoCsv class FraudesHHPPDesmarcadoCargueMasivo " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @throws Exception
     */
    private void fillSheetbookCsv(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data) {
        try {
            //Create a blank sheet
            HSSFSheet sheet = workbook.createSheet(sheetName);
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);
            //Iterate over data and write to sheet
            int rownum = 0;
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    }
                }
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generó error en fillSheetbook de FraudesHHPPDesmarcadoCargueMasivo" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Diana -> método para generar la cabecera de archivo xls
     *
     * @return
     */
    private static Object[] cabeceraXls() {
        Object[] cabeceraDataGral = new Object[]{LINEA, ID_DIRECCION_DETALLADA, DEPARTAMENTO, MUNICIPIO, CENTRO_POBLADO, NODO, DIRECCION, COD_MARCAS_FRAUDE};
        return cabeceraDataGral;
    }

    /**
     * Diana -> método para generar la cabecera de archivo csv
     *
     * @return
     */
    private static Object[] cabeceraCsv() {
        StringBuilder registro = new StringBuilder();
        registro.append(LINEA).append(Constant.SEPARADOR).append(ID_DIRECCION_DETALLADA).append(Constant.SEPARADOR).append(DEPARTAMENTO).append(Constant.SEPARADOR).append(MUNICIPIO)
                .append(Constant.SEPARADOR).append(CENTRO_POBLADO).append(Constant.SEPARADOR).append(NODO).append(Constant.SEPARADOR).append(DIRECCION).append(Constant.SEPARADOR).append(COD_MARCAS_FRAUDE);
        Object[] cabeceraDataGral = new Object[]{registro.toString()};
        return cabeceraDataGral;
    }

    public void construirArchivo(int opcion, HhppCargueArchivoLog archivoLog) throws ApplicationException {
        try {
            CargueArchivoLogItem archivoOne;
            String[] campos;
            String[] headerFiltro;
            String[] contenidoFiltro;
            String[] header;
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemFacadeLocal.findByIdArchivoFraude(archivoLog.getIdArchivoLog());
                if (registrosBd.size() > 0) {
                    archivoOne = registrosBd.get(0);
                    campos = archivoOne.getEncabezadoCampos().split("\\/");
                    header = campos[0].split("\\|");
                    downloadCSV(opcion, header, archivoLog.getNombreArchivoCargue());
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en construirArchivo. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en construirArchivo. " + e.getMessage(), e);
            throw e;
        }
    }

    public String downloadCSV(int opcion, String[] headers, String filename) throws ApplicationException {
        try {
            final StringBuffer sb = new StringBuffer();
            String[] datosMod;
            String[] datosOri;

            //header[k] += "FechaProcesamiento";

            if (registrosBd != null) {
                int numeroRegistros = registrosBd.size();
                byte[] csvData;
                if (numeroRegistros > 0) {
                    for (String item : headers) {
                        if (opcion == 1 && item.contains("Detalle")) { //Exportar Original
                            sb.append(item.replace("Detalle", ""));
                        } else if(opcion == 1 && item.contains("Linea")){
                            sb.append(item.replace("Linea", ""));                            
                        } else if(opcion == 1 && item.contains("ID Direccion Detallada")){
                            sb.append(item.replace("ID Direccion Detallada", "Id Direccion detallada"));                            
                            sb.append(";");
                        }  else if(opcion == 1 && item.contains("Centro Poblado")){
                            sb.append(item.replace("Centro Poblado", "Centro poblado")); 
                            sb.append(";");
                        }else{
                            sb.append(item.replace("|Detalle", "|Detalle|FechaProcesamiento"));
                            sb.append(";");
                        }
                    }
                    sb.append("\n");
                    if (opcion == 1) { //Exportar Original
                        for (CargueArchivoLogItem archivo : registrosBd) {
                            datosOri = archivo.getInfo().split("\\|");

                            for (String dato : datosOri) {
                                String celda = dato;
                                sb.append(celda);
                                sb.append(";");
                            }
                            sb.append("\n");
                        }
                    } else {
                         boolean contieneAmbas = false;
                        for (CargueArchivoLogItem archivo : registrosBd) {
                            datosMod = archivo.getInfoMod().split("\\|");
                            List registros = new ArrayList<String>(Arrays.asList(datosMod));
                            if (registros.contains("F") && registros.contains("G")) {
                                contieneAmbas = true;
                            }else{
                                 contieneAmbas = false;
                            }
                           int count = 0;
                           String marca = "";
                            for(int i=0; i < registros.size() ; i++) {
//                            for (String dato : datosMod) {
                                String celda = registros.get(i).toString();
                                if (celda.equals("F") || celda.equals("G")) {
                                     count++;
                                     marca += celda + "|";
                                    if (contieneAmbas) {
                                        if (marca.length() == 4 ) {
                                            if (!marca.isEmpty()) {
                                                marca = marca.substring(0, marca.length() - 1);
                                            }
                                            sb.append(marca);
                                            sb.append(";");
                                        }
                                    } else {
                                        sb.append(celda);
                                        sb.append(";");
                                    }
                                }else{
                                    if (!celda.equals("") ) {
                                        sb.append(celda);
                                        sb.append(";");
                                    }else{
                                        if (i == 8) {
                                            sb.append("-");
                                            sb.append(";");
                                        } 
                                       
                                    }
                                    
                                }
                            }
                            // filas finales
                            String celdaEst = archivo.getEstadoProceso();
                            sb.append(celdaEst);
                            sb.append(";");

                            String celdaDet = archivo.getDetalle();
                            sb.append(celdaDet);
                            sb.append(";");

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
                     }
                csvData = sb.toString().getBytes("UTF-8");

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.setContentType("application/force.download");
                response.setHeader("Content-disposition", "filename=" + filename);
                response.setCharacterEncoding("UTF-8");
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
            LOGGER.error("Error en downloadCSV. " + e.getMessage(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error en downloadCSV. " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
        return "case1";
    }

    public void pageFirstCap() {
        listInfoByPageCap(1);
    }

    public void listInfoByPageCap(int page) {
        try {
            this.estadoUsuarioLogin = 1;
            this.estado = 1;
            itemsProcesosUsuarioLoginAux = ejbHhppCargueArchivoLog.findRangeEstadoByOrigen(null, estado, usuario);

            actualCap = page;
            getTotalPaginasCap();
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }

            //Obtenemos el rango de la paginación
            if (itemsProcesosUsuarioLoginAux.size() > 0) {

                int maxResult;
                if ((firstResult + filasPag) > itemsProcesosUsuarioLoginAux.size()) {
                    maxResult = itemsProcesosUsuarioLoginAux.size();
                } else {
                    maxResult = (firstResult + filasPag);
                }

                itemsArchivosUsuarioLogin = new ArrayList<HhppCargueArchivoLog>();
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
    
    public void limpiarValores() {
        numRegAProcesar = 0;
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.setNombreArchivo("");
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.setNumeroRegistrosAProcesar(0);
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.setNumeroRegistrosProcesados(0);
        CmtFraudesMasivoHHPPDesmarcadoDtoMgl.setUserRunningProcess("");
    }


    public void pageLastCap() {
        int totalPaginas = getTotalPaginasCap();
        listInfoByPageCap(totalPaginas);
    }

    public boolean isShowPanelCargueMarcado() {
        return showPanelCargueMarcado;
    }

    public void setShowPanelCargueMarcado(boolean showPanelCargueMarcado) {
        this.showPanelCargueMarcado = showPanelCargueMarcado;
    }

    public boolean isShowPanelTablaProcesos() {
        return showPanelTablaProcesos;
    }

    public void setShowPanelTablaProcesos(boolean showPanelTablaProcesos) {
        this.showPanelTablaProcesos = showPanelTablaProcesos;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public boolean isEnproceso() {
        return enproceso;
    }

    public void setEnproceso(boolean enproceso) {
        this.enproceso = enproceso;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public boolean isFinish() {
        this.finish = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsFinished();
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public List<HhppCargueArchivoLog> getItemsArchivosUsuarioLogin() {
        return itemsArchivosUsuarioLogin;
    }

    public void setItemsArchivosUsuarioLogin(List<HhppCargueArchivoLog> itemsArchivosUsuarioLogin) {
        this.itemsArchivosUsuarioLogin = itemsArchivosUsuarioLogin;
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
    
    public String getUsuarioProceso() {
        usuarioProceso = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getUserRunningProcess();
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }
    
    public void refreshCantReg() {
        reporteFinal = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getListFraudesHHPPDesmarcadoMasivoDto();
        numRegProcesados = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosProcesados();
        numRegAProcesar = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosAProcesar();
        if (numRegProcesados == numRegAProcesar) {
            if(CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsFinished()){
                 pageFirstCap();
            }
            this.refresh = REFRESH_MAX;
            
        }
    }
    
    public int getNumRegAProcesar() {
        refreshCantReg();
        numRegAProcesar = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosAProcesar();
        return numRegAProcesar;
    }

    public void setNumRegAProcesar(int numRegAProcesar) {
        this.numRegAProcesar = numRegAProcesar;
    }

    public int getNumRegProcesados() {
        numRegProcesados = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getNumeroRegistrosProcesados();
        return numRegProcesados;
    }

    public void setNumRegProcesados(int numRegProcesados) {
        this.numRegProcesados = numRegProcesados;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    /**
     * bocanegravm metodo para verificar si ya termino el proceso del reporte
     *
     * @return
     */
    public boolean getReady() {
        if (CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getEndProcessDate() != null
                && !new Date().before(sumarTiempoFecha(CmtFraudesMasivoHHPPDesmarcadoDtoMgl.getEndProcessDate(), this.minutos))) {
            limpiarValores();
            setProgress(false);
            setFinish(false);
            try {
                CmtFraudesMasivoHHPPDesmarcadoDtoMgl.cleanBeforeStart();
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formPrincipal");
            FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
        }


        if (this.progress) {
            this.finish = CmtFraudesMasivoHHPPDesmarcadoDtoMgl.isIsFinished();
            if (this.finish) {
                this.refresh = REFRESH_MIN;
            }
        } else {
            this.finish = false;
        }
        return this.finish;
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionProcesarArchivo() {
        return validarEdicionRol(BTPROARCHDESMAR);
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
}
