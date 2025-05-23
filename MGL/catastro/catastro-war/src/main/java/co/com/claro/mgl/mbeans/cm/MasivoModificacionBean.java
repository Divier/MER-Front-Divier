package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtMasivoCuentaMatrizDtoMgl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.rest.dtos.MasivoModificacionDto;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author valbuenayf
 */
@ManagedBean(name = "masivoModificacionBean")
@ViewScoped
public class MasivoModificacionBean implements Serializable, Observer {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(MasivoModificacionBean.class);
    private static final String NO_REGISTROS = "No hay registros a procesar ";
    private static final String ARCHIVO_INVALIDO = "No es valido el tipo de archivo ";
    private static final String ARCHIVO_NULL = "Por favor seleccionar un archivo para cargar...";
    private static final String LINEA = "LINEA";
    private static final String NUMERO_CUENTA_MATRIZ = "NUMERO CUENTA";
    private static final String PROCESADO = "PROCESADO";
    private static final String MENSAJE_SUB_EDIFICIO = "MENSAJE SUB EDIFICIO";
    private static final String MENSAJE_TECNOLOGIA_SUB = "MENSAJE TECNOLOGIA_SUB";
    private static final String MENSAJE_CUENTA_MATRIZ = "MENSAJE CUENTA MATRIZ";
    private static final String MENSAJE_NO = "-----------------------";
    private static final String SI = "SI";
    private static final String ERROR = "ERROR";
    private static final String OK = "OK";
    private static final String SHEET_NAME = "Reporte de carga archivo";
    private static final String FILE_NAME = "Reporte";
    private static final String NO_HAY_REGISTROS = "No Existen Registros a exportar";
    private static final String ARCHIVO_EN_PROCESO = "Se esta procesando un archivo";
    private static final String ARCHIVO_NO_CUMPLE = "El contenido del archivo no cumple con los requisitos ";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<MasivoModificacionDto> listaModificacion;
    private UploadedFile upFile;
    private String nombre;
    private boolean enproceso;
    private boolean mensajeError;
    private List<MasivoModificacionDto> reporteFinal;
    private Integer cantidadRegistros;
    private Integer totalRegistros;
    private String xlsMaxReg;
    private String csvMaxReg;
    private String txtMaxReg;
    private boolean finish;
    private String usuarioProceso;
    private boolean render;
    private int minutos;
    private Integer refresh;
    private static final Integer REFRESH_INI = 100000;
    private static final Integer REFRESH_FINAL = 10000;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacade;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    

    /**
     * Creates a new instance of MasivoModificacionBean
     */
    public MasivoModificacionBean() {
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
            LOGGER.error("Se genero error en MasivoModificacionBean de  MasivoModificacionBean class ...", ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        this.refresh = REFRESH_INI;
        this.minutos = tiempoReporte(Constant.TIEMPO_REPORTE);

        listaModificacion = new ArrayList<>();
        this.enproceso = CmtMasivoCuentaMatrizDtoMgl.isIsProcessing();
        if (!CmtMasivoCuentaMatrizDtoMgl.isIsProcessing()) {
         
            if (CmtMasivoCuentaMatrizDtoMgl.getEndProcessDate() != null && new Date().before(sumarTiempoFecha(CmtMasivoCuentaMatrizDtoMgl.getEndProcessDate(), this.minutos))) {
            } else {
                try {
                    CmtMasivoCuentaMatrizDtoMgl.cleanBeforeStart();
                } catch (ApplicationException ex) {
                    LOGGER.error("Se genero error en init de MasivoModificacionBean class ...", ex);
    }
            }
        } 
    }

    /**
     * Metodo para cargar el archivo de las modificaciones
     */
    public void cargarArchivo() {
        try {
            this.refresh = REFRESH_FINAL;
            if (CmtMasivoCuentaMatrizDtoMgl.isIsProcessing()) {
                String msn = ARCHIVO_EN_PROCESO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                return;
            } else {
                CmtMasivoCuentaMatrizDtoMgl.cleanBeforeStart();
            } 
            this.mensajeError = false;
            this.listaModificacion = null;
            this.reporteFinal = null;
            if (upFile == null && upFile.getFileName() != null) {
                String msn = ARCHIVO_NULL;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                this.nombre = null;
                return;
            } else {
                this.nombre = upFile.getFileName();
            }
            String ext = upFile.getFileName().substring(upFile.getFileName().length() - 4, upFile.getFileName().length());
            if (!ext.toUpperCase().equals(".XLS")) {
                String msn = ARCHIVO_INVALIDO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                this.nombre = null;
                return;
            }
            InputStream stream = upFile.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            HSSFRow hssfRow;
            int rows = hssfSheet.getLastRowNum();
            hssfRow = hssfSheet.getRow(0);
            switch (hssfRow.getLastCellNum()) {
                case 34:
                    CmtMasivoCuentaMatrizDtoMgl.setTipoArchivo("General");
                    listaModificacion = generarListaGeneral(hssfRow, hssfSheet, rows);
                    if (listaModificacion != null && !listaModificacion.isEmpty()) {
                        tecnologiaSubMglFacade.actualizarCargueMasivoCuentaMatriz(this, listaModificacion, usuarioVT, 1, perfilVT, this.nombre);
                        this.enproceso = CmtMasivoCuentaMatrizDtoMgl.isIsProcessing();
                        this.finish = CmtMasivoCuentaMatrizDtoMgl.isIsFinished();
                    } else {
                        if (!this.mensajeError) {
                            String msnE = NO_REGISTROS;
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnE, null));
                        }
                        this.nombre = null;
                        return;
                    }   break;
                case 19:
                    CmtMasivoCuentaMatrizDtoMgl.setTipoArchivo("Detallado");
                    listaModificacion = generarListaDetallado(hssfRow, hssfSheet, rows);
                    if (listaModificacion != null && !listaModificacion.isEmpty()) {
                        tecnologiaSubMglFacade.actualizarCargueMasivoCuentaMatriz(this, listaModificacion, usuarioVT, 2, perfilVT, this.nombre);
                        this.enproceso = CmtMasivoCuentaMatrizDtoMgl.isIsProcessing();
                        this.finish = CmtMasivoCuentaMatrizDtoMgl.isIsFinished();
                    } else {
                        if (!this.mensajeError) {
                            String msnE = NO_REGISTROS;
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msnE, null));
                        }
                        this.nombre = null;
                        return;
                    }   break;
                default:
                    String msn = ARCHIVO_NO_CUMPLE;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    this.nombre = null;
                    return;
            }
            this.enproceso = true;
        } catch (ApplicationException | IOException  e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo  de MasivoModificacionBean :  class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para generar la lista de general
     *
     * @param hssfRow
     * @param hssfSheet
     * @param rows
     * @return
     */
    private List<MasivoModificacionDto> generarListaGeneral(HSSFRow hssfRow, HSSFSheet hssfSheet, int rows) {
        List<MasivoModificacionDto> listGeneral = new ArrayList<>();
        try {
            int linea = 2;
            for (int r = 1; r <= rows; r++) {
                MasivoModificacionDto masivo = new MasivoModificacionDto();
                masivo.setLinea(linea);
                hssfRow = hssfSheet.getRow(r);
                if (hssfRow == null) {
                    break;
                } else {
                    BigDecimal idTecnoSubedificio = BigDecimal.ZERO;
                    BigDecimal idSubedificio = BigDecimal.ZERO;
                    BigDecimal idCuentamatriz = BigDecimal.ZERO;
                    BigDecimal idBlacklist = null;

                    if (hssfRow.getCell(0) != null) {
                        idTecnoSubedificio = new BigDecimal(hssfRow.getCell(0).toString());
                    }
                    if (hssfRow.getCell(1) != null) {
                        idSubedificio = new BigDecimal(hssfRow.getCell(1).toString());
                    }
                    masivo.setNombreSubedificio(hssfRow.getCell(2) != null ? hssfRow.getCell(2).toString() : null);
                    //espinosadiea el dato tres no se utilizara direccion del subedificio
                    masivo.setTipoSubedificio(hssfRow.getCell(4) != null ? hssfRow.getCell(4).toString() : null);
                    if (hssfRow.getCell(5) != null) {
                        idCuentamatriz = new BigDecimal(hssfRow.getCell(5).toString());
                    }
                    masivo.setIdTecnoSubedificio(idTecnoSubedificio);
                    masivo.setIdSubedificio(idSubedificio);
                    masivo.setIdCuentamatriz(idCuentamatriz);
                    if (hssfRow.getCell(6) != null) {
                        masivo.setNumeroCuenta(new BigDecimal(hssfRow.getCell(6).toString()));
                    }
                    masivo.setTipoTecnologia(hssfRow.getCell(7) != null ? hssfRow.getCell(7).toString() : null);
                    masivo.setRegional(hssfRow.getCell(8) != null ? hssfRow.getCell(8).toString() : null);
                    masivo.setCiudad(hssfRow.getCell(9) != null ? hssfRow.getCell(9).toString() : null);
                    masivo.setIdCentroPoblado(hssfRow.getCell(10) != null ? hssfRow.getCell(10).toString() : null);
                    masivo.setCentroPoblado(hssfRow.getCell(11) != null ? hssfRow.getCell(11).toString() : null);
                    masivo.setBarrio(hssfRow.getCell(12) != null ? hssfRow.getCell(12).toString() : null);  
                    masivo.setDireccion(hssfRow.getCell(13) != null ? hssfRow.getCell(13).toString() : null);
                    masivo.setNombreCuenta(hssfRow.getCell(14) != null ? hssfRow.getCell(14).toString() : null);
                    masivo.setCompaniaAdministracion(hssfRow.getCell(15) != null ? hssfRow.getCell(15).toString() : null);
                    masivo.setCompaniaAscensor(hssfRow.getCell(16) != null ? hssfRow.getCell(16).toString() : null);
                    masivo.setAdministrador(hssfRow.getCell(17) != null ? hssfRow.getCell(17).toString() : null);
                    masivo.setTelefonoUno(hssfRow.getCell(18) != null ? hssfRow.getCell(18).toString() : null);
                    masivo.setTelefonoDos(hssfRow.getCell(19) != null ? hssfRow.getCell(19).toString() : null);
                    masivo.setTipoProyecto(hssfRow.getCell(20) != null ? hssfRow.getCell(20).toString() : null);
                    masivo.setOrigenDatos(hssfRow.getCell(21) != null ? hssfRow.getCell(21).toString() : null);

                    if (hssfRow.getCell(22) != null && !hssfRow.getCell(22).toString().isEmpty()) {
                        masivo.setCodigoNodo(hssfRow.getCell(22).toString());
                    } else {
                        masivo.setCodigoNodo(null);
                    }
                    if (hssfRow.getCell(23) != null && !hssfRow.getCell(23).toString().isEmpty()) {
                        masivo.setEstadoTecnologia(hssfRow.getCell(23).toString());
                    } else {
                        masivo.setEstadoTecnologia(null);
                    }

                    if (hssfRow.getCell(24) != null && !hssfRow.getCell(24).toString().isEmpty()) {
                        masivo.setTipoConfiguracion(hssfRow.getCell(24).toString());
                    } else {
                        masivo.setTipoConfiguracion(null);
                    }
                    if (hssfRow.getCell(25) != null && !hssfRow.getCell(25).toString().isEmpty()) {
                        masivo.setAlimentacionElectrica(hssfRow.getCell(25).toString());
                    } else {
                        masivo.setAlimentacionElectrica(null);
                    }
                    if (hssfRow.getCell(26) != null && !hssfRow.getCell(26).toString().isEmpty()) {
                        masivo.setTipoDistribucion(hssfRow.getCell(26).toString());
                    } else {
                        masivo.setTipoDistribucion(null);
                    }
                   //////////////////
                    if (hssfRow.getCell(27) != null && !hssfRow.getCell(27).toString().isEmpty()) {
                        masivo.setTipoCcmm(hssfRow.getCell(27).toString());
                    } else {
                        masivo.setTipoCcmm(null);
                    }
                    
                    if (hssfRow.getCell(28) != null && !hssfRow.getCell(28).toString().isEmpty()) {
                        masivo.setCompaniaConstructora(hssfRow.getCell(28).toString());
                    } else {
                        masivo.setCompaniaConstructora(null);
                    }
                    
                    if (hssfRow.getCell(29) != null && !hssfRow.getCell(29).toString().isEmpty()) {
                        masivo.setBlacklistTecnologia(hssfRow.getCell(29).toString());
                    } else {
                        masivo.setBlacklistTecnologia(null);
                    }
                    //////////////////
                    
                    masivo.setNota(hssfRow.getCell(30) != null ? hssfRow.getCell(30).toString() : null);
                    if (hssfRow.getCell(31) != null && !hssfRow.getCell(31).toString().isEmpty()) {
                        idBlacklist = new BigDecimal(hssfRow.getCell(31).toString());
                    }
                    masivo.setIdBlacklistTecnologia(idBlacklist);
                    masivo.setFechaCreacion(hssfRow.getCell(32) != null ? hssfRow.getCell(32).toString() : null);
                    masivo.setFechaUltMod(hssfRow.getCell(33) != null ? hssfRow.getCell(33).toString() : null);
                }
                listGeneral.add(masivo);
                linea += 1;
            }
        } catch (Exception e) {
            LOGGER.info("Error en generarListaGeneral  de MasivoModificacionBean : " + e);
        }
        return listGeneral;
    }

    /**
     * valbuenayf metodo para generar la lista de detallado
     *
     * @param hssfRow
     * @param hssfSheet
     * @param rows
     * @return
     */
    private List<MasivoModificacionDto> generarListaDetallado(HSSFRow hssfRow, HSSFSheet hssfSheet, int rows) {
        List<MasivoModificacionDto> listDetallado = new ArrayList<>();
        try {
            int col = 14;
            int nuevo = col + 1;
            HSSFRow cabecera = hssfSheet.getRow(0);
            int num = generarNumero(cabecera, col);
            int linea = 2;
            for (int r = 1; r <= rows; r++) {
                MasivoModificacionDto masivo = new MasivoModificacionDto();
                masivo.setLinea(linea);
                masivo.setNumDetallado(num);
                hssfRow = hssfSheet.getRow(r);
                if (hssfRow == null) {
                    break;
                } else {
                    BigDecimal idTecnoSubedificio = BigDecimal.ZERO;
                    BigDecimal idSubedificio = BigDecimal.ZERO;
                    BigDecimal idCuentamatriz = BigDecimal.ZERO;

                    if (hssfRow.getCell(0) != null) {
                        idTecnoSubedificio = new BigDecimal(hssfRow.getCell(0).toString());
                    }
                    if (hssfRow.getCell(1) != null) {
                        idSubedificio = new BigDecimal(hssfRow.getCell(1).toString());
                    }
                    masivo.setNombreSubedificio(hssfRow.getCell(2).toString());
                    masivo.setTipoSubedificio(hssfRow.getCell(3).toString());
                    
                    if (hssfRow.getCell(4) != null) {
                        idCuentamatriz = new BigDecimal(hssfRow.getCell(4).toString());
                    }
                    masivo.setIdTecnoSubedificio(idTecnoSubedificio);
                    masivo.setIdSubedificio(idSubedificio);
                    masivo.setIdCuentamatriz(idCuentamatriz);
                    if (hssfRow.getCell(5) != null) {
                        masivo.setNumeroCuenta(new BigDecimal(hssfRow.getCell(5).toString()));
                    }
                    masivo.setTipoTecnologia(hssfRow.getCell(6).toString());
                    masivo.setRegional(hssfRow.getCell(7).toString());
                    masivo.setCiudad(hssfRow.getCell(8).toString());
                    masivo.setIdCentroPoblado(hssfRow.getCell(9).toString());
                    masivo.setCentroPoblado(hssfRow.getCell(10).toString());
                    masivo.setBarrio(hssfRow.getCell(11).toString());
                    masivo.setDireccion(hssfRow.getCell(12).toString());
                    masivo.setBlacklistTecnologia(hssfRow.getCell(13) != null ? hssfRow.getCell(13).toString() : null);
                    
                    //Inicio validacion del atributo que se va editar
                    switch (num) {
                        case 1:
                            masivo.setNombreCuenta(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 2:
                            masivo.setCompaniaAdministracion(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 3:
                            masivo.setCompaniaAscensor(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 4:
                            masivo.setAdministrador(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 5:
                            masivo.setTelefonoUno(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 6:
                            masivo.setTelefonoDos(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                        case 7:
                            masivo.setTipoProyecto(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 8:
                            masivo.setOrigenDatos(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
                            break;
                        case 9:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setCodigoNodo(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setCodigoNodo(null);
                            }
                            break;
                        case 10:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setEstadoTecnologia(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setEstadoTecnologia(null);
                            }
                            break;
                        case 11:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setTipoConfiguracion(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setTipoConfiguracion(null);
                            }
                            break;
                        case 12:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setAlimentacionElectrica(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setAlimentacionElectrica(null);
                            }
                            break;
                        case 13:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setTipoDistribucion(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setTipoDistribucion(null);
                            }
                            break;
                        case 14:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setTipoCcmm(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setTipoCcmm(null);
                            }
                            break;
                        case 15:
                            if (hssfRow.getCell(nuevo) != null && !hssfRow.getCell(nuevo).toString().isEmpty()) {
                                masivo.setCompaniaConstructora(hssfRow.getCell(nuevo).toString());
                            } else {
                                masivo.setCompaniaConstructora(null);
                            }
                            break;
                    }
                    
                    
                    masivo.setNuevoDato(hssfRow.getCell(nuevo) != null ? hssfRow.getCell(nuevo).toString() : null);
   
                    masivo.setNota(hssfRow.getCell(16) != null ? hssfRow.getCell(16).toString() : null);
                    masivo.setFechaCreacion(hssfRow.getCell(17).toString());
                    masivo.setFechaUltMod(hssfRow.getCell(18).toString());
                }
                //Fin validacion del atributo que se va editar
                listDetallado.add(masivo);
                linea += 1;
            }
        } catch (Exception e) {
            LOGGER.info("Error en generarListaDetallado  de MasivoModificacionBean : " + e);
        }
        return listDetallado;
    }

    /**
     * valbuenayf metodo para saber por que columna se va hacer el filtro
     *
     * @param cabecera
     * @param col
     * @return
     */
    private int generarNumero(HSSFRow cabecera, int col) {
        int num = 0;
        if (cabecera.getCell(col).toString().equals(Constant.NOMBRE)) {
            num = 1;
        } else if (cabecera.getCell(col).toString().equals(Constant.ADMINISTRACION)) {
            num = 2;
        } else if (cabecera.getCell(col).toString().equals(Constant.ASCENSOR)) {
            num = 3;
        } else if (cabecera.getCell(col).toString().equals(Constant.ADMINISTRADOR)) {
            num = 4;
        } else if (cabecera.getCell(col).toString().equals(Constant.TEL_UNO)) {
            num = 5;
        } else if (cabecera.getCell(col).toString().equals(Constant.TEL_DOS)) {
            num = 6;
        } else if (cabecera.getCell(col).toString().equals(Constant.PROYECTO)) {
            num = 7;
        } else if (cabecera.getCell(col).toString().equals(Constant.DATOS)) {
            num = 8;
        } else if (cabecera.getCell(col).toString().equals(Constant.NODO)) {
            num = 9;
        } else if (cabecera.getCell(col).toString().equals(Constant.ESTADO)) {
            num = 10;
        } else if (cabecera.getCell(col).toString().equals(Constant.CONFIGURACION)) {
            num = 11;
        } else if (cabecera.getCell(col).toString().equals(Constant.ALIMENTACION)) {
            num = 12;
        } else if (cabecera.getCell(col).toString().equals(Constant.DISTRIBUCION)) {
            num = 13;
        } else if (cabecera.getCell(col).toString().equals(Constant.TIPO_CCMM)) {
            num = 14;
        } else if (cabecera.getCell(col).toString().equals(Constant.CONSTRUCTORA)) {
            num = 15;
        }
        return num;
    }

    // inicio generacion reporte
    /**
     * valbuenayf metodo para llamar los respectivos tipos de descargar de
     * archivos
     *
     * @param num
     */
    public void generarReporteFinal(int num) {

        this.reporteFinal = CmtMasivoCuentaMatrizDtoMgl.getListMasivoModificacionDto();

        if (reporteFinal == null || reporteFinal.isEmpty()) {
            LOGGER.info("No hay registros para procesar");
            return;
        }
        switch (num) {
            case 1:
                exportarArchivoXls(this.reporteFinal);
                break;
            case 2:
                exportarArchivoCsv(this.reporteFinal);
                break;
            case 3:
                exportarArchivoTxt(this.reporteFinal);
                break;
        }
    }

    /**
     * valbuenayf metodo para exportar el resultado a xls
     *
     * @param reporte
     */
    private void exportarArchivoXls(List<MasivoModificacionDto> reporte) {

        try {
            if (reporte != null && !reporte.isEmpty()) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<>();
                int fila = 1;
                mapDataEstado.put(String.valueOf(fila++), cabeceraXls());
                for (MasivoModificacionDto r : reporte) {
                    String linea = String.valueOf(r.getLinea());
                    String numeroCuentaMatriz = r.getNumeroCuenta() != null ? r.getNumeroCuenta().toString() : "";
                    String procesado;
                    String mensajeTecnologiaSub;
                    String mensajeSubEdificio;
                    String mensajeCuentaMatriz;
                    if (r.isProcesado()) {
                        procesado = SI;
                        mensajeTecnologiaSub = MENSAJE_NO;
                        mensajeSubEdificio = MENSAJE_NO;
                        mensajeCuentaMatriz = MENSAJE_NO;
                    } else {
                        procesado = ERROR;
                        if (r.getMensajeTecnologiaSub() != null && !r.getMensajeTecnologiaSub().isEmpty()) {
                            mensajeTecnologiaSub = r.getMensajeTecnologiaSub();
                        } else {
                            mensajeTecnologiaSub = MENSAJE_NO;
                        }
                        if (r.getMensajeSubEdificio() != null && !r.getMensajeSubEdificio().isEmpty()) {
                            mensajeSubEdificio = r.getMensajeSubEdificio();
                        } else {
                            mensajeSubEdificio = MENSAJE_NO;
                        }
                        if (r.getMensajeCuentaMatriz() != null && !r.getMensajeCuentaMatriz().isEmpty()) {
                            mensajeCuentaMatriz = r.getMensajeCuentaMatriz();
                        } else {
                            mensajeCuentaMatriz = MENSAJE_NO;
                        }
                    }

                    Object[] registros = new Object[]{linea, numeroCuentaMatriz, procesado, mensajeTecnologiaSub, mensajeSubEdificio, mensajeCuentaMatriz};
                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                fillSheetbookCsv(workbook, SHEET_NAME, mapDataEstado);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRls = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRls.reset();
                responseRls.setContentType("application/vnd.ms-excel");
                SimpleDateFormat formato = new SimpleDateFormat("-yyyy_MMM_dd-hh_mm");
                String nombreArchivo = this.nombre.substring(0,this.nombre.length() - 4);
                responseRls.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo+".xls");
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
            FacesUtil.mostrarMensajeError("Se generea error en MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * valbuenayf metodo para exportar el resultado a Csv
     *
     * @param reporte
     */
    private void exportarArchivoCsv(List<MasivoModificacionDto> reporte) {
        try {
            if (reporte != null && !reporte.isEmpty()) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<>();
                int fila = 1;
                mapDataEstado.put(String.valueOf(fila++), cabeceraCsv());
                for (MasivoModificacionDto r : reporte) {
                    String linea = String.valueOf(r.getLinea());
                    String numeroCuentaMatriz = r.getNumeroCuenta() != null ? r.getNumeroCuenta().toString() : "";
                    String procesado;
                    String mensajeTecnologiaSub;
                    String mensajeSubEdificio;
                    String mensajeCuentaMatriz;
                    if (r.isProcesado()) {
                        procesado = SI;
                        mensajeTecnologiaSub = MENSAJE_NO;
                        mensajeSubEdificio = MENSAJE_NO;
                        mensajeCuentaMatriz = MENSAJE_NO;
                    } else {
                        procesado = ERROR;
                        if (r.getMensajeTecnologiaSub() != null && !r.getMensajeTecnologiaSub().isEmpty()) {
                            mensajeTecnologiaSub = r.getMensajeTecnologiaSub();
                        } else {
                            mensajeTecnologiaSub = MENSAJE_NO;
                        }
                        if (r.getMensajeSubEdificio() != null && !r.getMensajeSubEdificio().isEmpty()) {
                            mensajeSubEdificio = r.getMensajeSubEdificio();
                        } else {
                            mensajeSubEdificio = MENSAJE_NO;
                        }
                        if (r.getMensajeCuentaMatriz() != null && !r.getMensajeCuentaMatriz().isEmpty()) {
                            mensajeCuentaMatriz = r.getMensajeCuentaMatriz();
                        } else {
                            mensajeCuentaMatriz = MENSAJE_NO;
                        }
                    }
                    StringBuilder registro = new StringBuilder();
                    registro.append(linea).append(Constant.SEPARADOR).append(procesado).append(Constant.SEPARADOR).append(numeroCuentaMatriz).append(Constant.SEPARADOR).append(mensajeTecnologiaSub);
                    registro.append(Constant.SEPARADOR).append(mensajeSubEdificio).append(Constant.SEPARADOR).append(mensajeCuentaMatriz);
                    Object[] registros = new Object[]{registro.toString()};
                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                fillSheetbookCsv(workbook, SHEET_NAME, mapDataEstado);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRls = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRls.reset();
                responseRls.setContentType("application/vnd.ms-excel");
                SimpleDateFormat formato = new SimpleDateFormat("-yyyy_MMM_dd-hh_mm");
                String nombreArchivo = this.nombre.substring(0,this.nombre.length() - 4);
                responseRls.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo+ ".csv");
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
            FacesUtil.mostrarMensajeError(mensaje+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para exportar el resultado a txt
     *
     * @param reporte
     */
    private void exportarArchivoTxt(List<MasivoModificacionDto> reporte) {
        try {
            if (reporte != null && !reporte.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRt = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRt.reset();
                responseRt.setContentType("application/octet-stream");
                SimpleDateFormat formato = new SimpleDateFormat("-yyyy_MMM_dd-hh_mm");
                String nombreArchivo = this.nombre.substring(0,this.nombre.length() - 4);
                responseRt.setHeader("Content-Disposition", "attachment; filename="+nombreArchivo + ".txt");
                ServletOutputStream ouputStream = responseRt.getOutputStream();
                ouputStream.println(cabeceraTxt());
                for (MasivoModificacionDto r : reporte) {
                    String linea = String.valueOf(r.getLinea());
                    String numeroCuentaMatriz = r.getNumeroCuenta() != null ? r.getNumeroCuenta().toString() : "";
                    String procesado;
                    String mensajeTecnologiaSub;
                    String mensajeSubEdificio;
                    String mensajeCuentaMatriz;
                    if (r.isProcesado()) {
                        procesado = SI;
                        mensajeTecnologiaSub = MENSAJE_NO;
                        mensajeSubEdificio = MENSAJE_NO;
                        mensajeCuentaMatriz = MENSAJE_NO;
                    } else {
                        procesado = ERROR;
                        if (r.getMensajeTecnologiaSub() != null && !r.getMensajeTecnologiaSub().isEmpty()) {
                            mensajeTecnologiaSub = r.getMensajeTecnologiaSub();
                        } else {
                            mensajeTecnologiaSub = MENSAJE_NO;
                        }
                        if (r.getMensajeSubEdificio() != null && !r.getMensajeSubEdificio().isEmpty()) {
                            mensajeSubEdificio = r.getMensajeSubEdificio();
                        } else {
                            mensajeSubEdificio = MENSAJE_NO;
                        }
                        if (r.getMensajeCuentaMatriz() != null && !r.getMensajeCuentaMatriz().isEmpty()) {
                            mensajeCuentaMatriz = r.getMensajeCuentaMatriz();
                        } else {
                            mensajeCuentaMatriz = MENSAJE_NO;
                        }
                    }
                    StringBuilder registro = new StringBuilder();
                    registro.append(linea).append(Constant.SEPARADOR).append(numeroCuentaMatriz).append(Constant.SEPARADOR).append(procesado).append(Constant.SEPARADOR).append(mensajeTecnologiaSub);
                    registro.append(Constant.SEPARADOR).append(mensajeSubEdificio).append(Constant.SEPARADOR).append(mensajeCuentaMatriz);
                    ouputStream.println(registro.toString());
                }
                ouputStream.flush();
                ouputStream.close();
                fc.responseComplete();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, NO_HAY_REGISTROS, ""));
            }
        } catch (IOException e) {
            String mensaje = "Ocurrió un error en la creación del archivo excel xls";
            FacesUtil.mostrarMensajeError(mensaje + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @throws Exception
     */
    private void fillSheetbookCsv(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data)  {
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
            FacesUtil.mostrarMensajeError("Se genero error en fillSheetbook de MasivoModificacionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metoso para generar la cabecera de archivo xls
     *
     * @return
     */
    private static Object[] cabeceraXls() {
        Object[] cabeceraDataGral = new Object[]{LINEA, NUMERO_CUENTA_MATRIZ, PROCESADO, MENSAJE_TECNOLOGIA_SUB, MENSAJE_SUB_EDIFICIO, MENSAJE_CUENTA_MATRIZ};
        return cabeceraDataGral;
    }

    /**
     * valbuenayf metoso para generar la cabecera de archivo csv
     *
     * @return
     */
    private static Object[] cabeceraCsv() {
        StringBuilder registro = new StringBuilder();
        registro.append(LINEA).append(Constant.SEPARADOR).append(NUMERO_CUENTA_MATRIZ).append(Constant.SEPARADOR).append(PROCESADO).append(Constant.SEPARADOR).append(MENSAJE_TECNOLOGIA_SUB);
        registro.append(Constant.SEPARADOR).append(MENSAJE_SUB_EDIFICIO).append(Constant.SEPARADOR).append(MENSAJE_CUENTA_MATRIZ);
        Object[] cabeceraDataGral = new Object[]{registro.toString()};
        return cabeceraDataGral;
    }

    /**
     * valbuenayf metoso para generar la cabecera de archivo txt
     *
     * @return
     */
    private static String cabeceraTxt() {
        StringBuilder registro = new StringBuilder();
        registro.append(LINEA).append(Constant.SEPARADOR).append(NUMERO_CUENTA_MATRIZ).append(Constant.SEPARADOR).append(PROCESADO).append(Constant.SEPARADOR).append(MENSAJE_TECNOLOGIA_SUB);
        registro.append(Constant.SEPARADOR).append(MENSAJE_SUB_EDIFICIO).append(Constant.SEPARADOR).append(MENSAJE_CUENTA_MATRIZ);
        return registro.toString();
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
     * valbuenayf Metodo para buscar el numero tiempo de espera para borrar el
     * reporte
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
            LOGGER.error("Error tiempoMaximo de MasivoModificacionBean " + e.getMessage());
            return DEFAULT;
        }
        return numero;
    }

// Getter and Setter
    public List<MasivoModificacionDto> getListaModificacion() {
        return listaModificacion;
    }

    public void setListaModificacion(List<MasivoModificacionDto> listaModificacion) {
        this.listaModificacion = listaModificacion;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public String getNombre() {
        this.nombre = CmtMasivoCuentaMatrizDtoMgl.getNombreArchivo();
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnproceso() {
        return enproceso;
    }

    public void setEnproceso(boolean enproceso) {
        this.enproceso = enproceso;
    }

    public Integer getCantidadRegistros() {
        this.cantidadRegistros = CmtMasivoCuentaMatrizDtoMgl.getNumeroRegistrosProcesados();
        return cantidadRegistros;
    }

    public void setCantidadRegistros(Integer cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public String getXlsMaxReg() {
        return xlsMaxReg;
    }

    public void setXlsMaxReg(String xlsMaxReg) {
        this.xlsMaxReg = xlsMaxReg;
    }

    public String getCsvMaxReg() {
        return csvMaxReg;
    }

    public void setCsvMaxReg(String csvMaxReg) {
        this.csvMaxReg = csvMaxReg;
    }

    public String getTxtMaxReg() {
        return txtMaxReg;
    }

    public void setTxtMaxReg(String txtMaxReg) {
        this.txtMaxReg = txtMaxReg;
    }

    public boolean isFinish() {
        this.finish = CmtMasivoCuentaMatrizDtoMgl.isIsFinished();
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getUsuarioProceso() {
        this.usuarioProceso = CmtMasivoCuentaMatrizDtoMgl.getUserRunningProcess();
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public Integer getTotalRegistros() {
        this.totalRegistros = CmtMasivoCuentaMatrizDtoMgl.getNumeroRegistrosAProcesar();
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }
    
    @Override
    public void update(Observable obs, Object o) {
        if (o instanceof MasivoModificacionDto) {//retorna por cada registro procesado de la lista
            MasivoModificacionDto mmDto = (MasivoModificacionDto) o;
        }else if ( o instanceof List ){//retorna cuando el proceso termino
        }
    }
    
     public void volverList() {
        try {
            enproceso = false;
            setFinish(false);
            CmtMasivoCuentaMatrizDtoMgl.cleanBeforeStart();
            this.refresh = REFRESH_FINAL;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en MasivoModificacionBean:volverList(). " + e.getMessage(), e, LOGGER);
        }
    }
     
     
      //Opciones agregadas para Rol
    private final String UPFLBTNCM = "UPFLBTNCM";
    // Validar Opciones por Rol
    public boolean validarOpcionSubirArchivo() {
        return validarEdicionRol(UPFLBTNCM);
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