/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtReporteDetalladoDto;
import co.com.claro.mgl.dtos.CmtReporteGeneralDto;
import co.com.claro.mgl.dtos.FiltroReporteSolicitudCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.StringUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "reportesSolCMBean")
@ViewScoped
public class ReportesSolBean {

    private String message;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private boolean cargar;
    private Date fechaInicio;
    private Date fechaFinal;
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ReportesSolBean.class);
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private CmtSolicitudCmMglFacadeLocal cmtSolicitudCmMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private List<CmtTipoSolicitudMgl> listCmtTipoSolicitudMgl;
    private BigDecimal tipoSolicitudSelected;
    private String tipoReporteSelected;
    private BigDecimal estadoSelected;
    private List<CmtBasicaMgl> listCmtSolicitudCmMgl;
    private List<CmtReporteDetalladoDto> listCmtReporteDetalladoDto;
    private List<CmtReporteGeneralDto> listCmtReporteGeneralDto;

    private CmtSolicitudCmMgl cmtSolicitudCmMgl;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private boolean habilitaObj = false;
    private int actual;
    private FiltroReporteSolicitudCMDto filtroReporteSolicitudCMDto;
    private String numPagina;
    private boolean pintarPaginado = true;
    private boolean panelExportar = false;
    private boolean mensajeExcel = false;
    private boolean mensajeCvsTxt = false;
    private boolean btnExcel = false;
    private boolean btnCvsTxt = false;
    private boolean btnVolver = false;
    private boolean btnReporte = true;
    private boolean panelFormulario = true;
    private List<Integer> paginaList;
    private String pageActual;
    private int page = 0;
    private int numRegAProcesar;
    private int numRegProcesados;
    private int numRegTotal;
    
    //Opciones agregadas para Rol
    private final String XLSBNRCMM = "XLSBNRCMM";

    
    private static final String[] NOM_COLUMNAS = {"CANTIDAD",
        "FECHA DE CREACION", "ESTADO",
        "TIPO SOLICITUD", "DEPARTAMENTO"
    };
    private static final String[] NOM_COLUMNAS_DETALLADO = {"ASESOR","SOLICITUD_ID","NUMERO_CUENTA_MGL","NUMERO_CUENTA_RR","DIRECCION_CM",
            "BARRIO_CM","NOMBRE_CM","TIPO_EDIFICIO","USUARIO_SOLICITANTE","CORREO_USUARIO_SOLICITANTE",
            "TELEFONO_SOLICITANTE","CORREO_ASESOR","CORREO_COPIA_A","NODO_BI","TIPO_SOLICITUD",
            "FECHA_INI_CREACION","TIEMPO_SOLICITUD","FECHA_FIN_GESTION","TIEMPO_GESTION","CANTIDAD_TORRES_CM",
            "ESTADO_SOLICITUD","ORIGEN_SOLICITUD","USUARIO_GESTIONADOR","RESPUESTA_ACTUAL",
            "RESULTADO_GESTION","NOTAS_USUARIO_SOLICITA","FECHA_INICIO_GESTION","FECHA_FIN_CREACION",
            "CANTIDAD_HHPP_CM","CONTACTO_CONSTRUCTORA_CM","ADMINISTRACION_CM","CONTACTO_ADMINISTRACION_CM",
            "ASCENSORES_CM","CONTACTO_ASCENSORES_CM","DIRECCION_ANTIGUA","TELEFONO_PORTERIA_1",
            "TELEFONO_PORTERIA_2","DIRECCION_TRADUCCIDA","FUENTE","TECNOLOGIA_SOLICITUD_GESTION",
            "BARRIO_GEOREFERENCIADO","NIVEL_SOCIECONOMICO_GEO","COORDENADA_X","COORDENADA_Y",
            "DTH","FIBRA_FTTH","FIBRA_OPTICA_GPON","FIBRA_OPTICA_UNIFILAR","HFC_BIDIRECCIONAL",
            "HFC_UNIDIRECCIONAL","AREA_SOLICITUD","FECHA_ENTREGA_CM","NODO_UNI_1",
            "NODO_UNI_2","NODO_DTH","NODO_MOVIL","NODO_FTTH","NODO_WIFI","LTE","MOVIL",
            "MODIFICO_SUBEDIFICIO","MODIFICO_DIRECCION","CONFIABILIDAD_DIRECCION","CONFIABILIDAD_COMPLEMENTO",
            "ESTADO_DIR","MODIFICO_DATOS_CM","MODIFICO_COBERTURA",
            
            "DEPARTAMENTO_CM","CIUDAD_CM","CENTRO_POBLADO_CM","NOMBRE_CM_DESPUES","TIPO_EDIFICIO_DESPUES",
            "TELEFONO_PORTERIA_1_DESPUES","TELEFONO_PORTERIA_2_DESPUES","ADMINISTRACION_CM_DESPUES",
            "CONTACTO_ADMINISTRACION_CM_DESPUES","ASCENSORES_CM_DESPUES","SUBEDIFICIOS_ANTES",
            "SUBEDIFICIOS_DESPUES","DIRECCION_CM_ANTES","DIRECCION_CM_DESPUES",
            
            "COBERTURA_CM_ANTES","COBERTURA_CM_DESPUES","RESULTADO_GES_MOD_DATOS_CM","RESULTADO_GES_MOD_DIR",
            "RESULTADO_GES_MOD_SUB","RESULTADO_GES_MOD_COB","FECHA_INI_GESTION_COB","FECHA_GESTION_COB",
            "USUARIO_GESTION_COB","CANTIDAD_SUBEDIFICIOS_MOD","CANTIDAD_HHPP_MOD","HHPP_CM_MODIFICADOS",
            "CANTIDAD_HHPP_CREADOS","HHPP_CM_CREADOS","FECHA_INI_GESTION_HHPP","FECHA_GESTION_HHPP",
            "ACONDICIONAMIENTO_VT","TIPO_SEGMENTO_VT","TECNOLOGIA_VT","FECHA_PROGRAMACION_VT",
            "CANTIDAD_TORRES_VT","ADMINISTRADOR_VT","CELULAR_ADMINISTRADOR_VT","CORREO_ADMINISTRADOR_VT",
            "TELEFONO_ADMINISTRADOR_VT","NOMBRE_CONTACTO_VT","CONSTRUCTORA_CM"};
    private FacesContext facesContextTh = FacesContext.getCurrentInstance();
    private boolean finalizado = false;
    private boolean iniciado = false;
    private Integer refresh;   
    private List<CmtReporteDetalladoDto> reporteExcelCvsList;
    private final Locale LOCALE = new Locale("es", "CO");     
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    private String xlsMaxReg;
    private String usuarioProceso;
    private String csvMaxReg;
    private String txtMaxReg;
    private boolean panelPintarPaginado = false;
    private List<CmtReporteGeneralDto> listCmtReporteGeneralDtoAux;

    public ReportesSolBean() {
        try {           
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            cmtSolicitudCmMgl = new CmtSolicitudCmMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al ReportesSolBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportesSolBean. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void fillSqlList() {

        try {         
            btnVolver = true;
            listCmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal.findByTipoApplication();
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_SOLICITUD);
            listCmtSolicitudCmMgl = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            // codigo que verifica en clase los valores del reporte            
            limpiarValores();          
            listCmtReporteGeneralDtoAux = new ArrayList();
      
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        }

    }

    public void getReporte() {
        try {
            btnExcel = false;
            btnCvsTxt = false;
            if (tipoReporteSelected != null) {
                if (fechaInicio != null && fechaFinal != null) {
                    if (validarFechas()) {
                        BigDecimal ZERO = new BigDecimal(0);
                        if (tipoSolicitudSelected.compareTo(ZERO) != 0) {
                            if (estadoSelected.compareTo(ZERO) != 0) {
                                // Reporte general
                                 if (tipoReporteSelected.equals("1")) {
                                    // consulta datatable  
                                    listCmtReporteDetalladoDto = null;
                                    filtroReporteSolicitudCMDto = cmtSolicitudCmMglFacadeLocal.getReporteGeneralSolicitudesSearchFinalCon(tipoReporteSelected, tipoSolicitudSelected, fechaInicio, fechaFinal, estadoSelected,0,0);
                                   listCmtReporteGeneralDtoAux = filtroReporteSolicitudCMDto.getListaCmtReporteGeneralDto();
                                   listInfoByPage(1);
                                    numRegTotal = listCmtReporteGeneralDtoAux.size();
                                    habilitaObj = !listCmtSolicitudCmMgl.isEmpty();
                                    listCmtReporteDetalladoDto = null;
                                    if (listCmtReporteGeneralDtoAux.isEmpty()) {
                                        panelPintarPaginado = false;
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "No se encontraron resultados para la consulta.", ""));
                                    } else {
                                        panelPintarPaginado = true;
                                        btnExcel = true;
                                        btnCvsTxt = true;
                                        btnReporte = true;
                                    }                                    

                                } else {
                                     listCmtReporteDetalladoDto = null;
                                     filtroReporteSolicitudCMDto = null;
                                     listCmtReporteGeneralDtoAux = null;                                   
                                     numRegTotal = 0;                                  
                                    listCmtReporteDetalladoDto = null;
                                    btnVolver = true;                                 
                                    panelFormulario = true;
                                    btnReporte = true;                                 
                                    CmtTipoSolicitudMgl cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
                                    cmtTipoSolicitudMgl.setTipoSolicitudId(tipoSolicitudSelected);                                    
                              
                                    int regAPro = cmtSolicitudCmMglFacadeLocal.generarReporteDetalladoContar(tipoReporteSelected, cmtTipoSolicitudMgl, fechaInicio,
                                            fechaFinal, estadoSelected, 0, 0, usuarioVT);
                                 
                                    numRegAProcesar = regAPro;
                                    if (regAPro > 0) {                                                                          
                                        xlsMaxReg = this.numRegAProcesar > 2000 ? Constant.EXPORT_XLS_MAX : "";                                       
                                    } else {
                                        numRegAProcesar = 0;                                       
                                        panelPintarPaginado = false;                                       
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "No se encontraron resultados para la consulta.", ""));
                                    }

                                }
                            } else {
                                btnReporte = true;
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Campo Estado es obligatorio.", ""));
                            }

                        } else {
                            btnReporte = true;
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Campo Tipo de Solicitud es obligatorio.", ""));

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

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:getReporte(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:getReporte(). " + e.getMessage(), e, LOGGER);
        }
    }    

   
    private Date sumarTiempoFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     * bocanegravm metodo para resetear los parametros de busqueda
     */
    public void limpiarValores() {
        numRegAProcesar = 0;
        btnCvsTxt = false;
        btnExcel = false;
        tipoReporteSelected = null;
        fechaInicio = null;
        fechaFinal = null;
        tipoSolicitudSelected = null;
        estadoSelected = null;
    }

    public String listInfoByPage(int page) {
    
            actual = page;
            
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }

            int maxResult;
            if ((firstResult + filasPag) > listCmtReporteGeneralDtoAux.size()) {
                maxResult = listCmtReporteGeneralDtoAux.size();
            } else {
                maxResult = (firstResult + filasPag);
            }

            listCmtReporteGeneralDto = new ArrayList<>();
            for (int k = firstResult; k < maxResult; k++) {
                listCmtReporteGeneralDto.add(listCmtReporteGeneralDtoAux.get(k));
            }

            if (listCmtReporteGeneralDto.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron resultados para la consulta.", ""));
                return "";
            }

        return "";
    }

    public void exportExcel() {
        try {
            if (listCmtReporteGeneralDtoAux != null) {
                exportExcelGen(listCmtReporteGeneralDtoAux);
                listCmtReporteDetalladoDto = null;
            } else {
                exportExcelDet();
                listCmtReporteGeneralDtoAux = null;
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:exportExcel() " + e.getMessage(), e, LOGGER);
        }
    }

    public void volverList() {
        try {
            limpiarValores();           
            
            panelPintarPaginado = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:volverList(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void downloadCvsTxt(String ext) {
        try {
            if (listCmtReporteGeneralDtoAux != null) {
                downloadCvsTxtGen(listCmtReporteGeneralDtoAux, ext);
                listCmtReporteDetalladoDto = null;
            } else {
                exportCvsTxtDet(ext);                
            }
        } catch (Exception e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn, e);
        }
    }
    
    
        public String exportCvsTxtDet(String ext) throws ApplicationException {
        try {            
            int expLonPag = 50;
             CmtTipoSolicitudMgl cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
                                    cmtTipoSolicitudMgl.setTipoSolicitudId(tipoSolicitudSelected);
                                    
            int numRegistrosTotal = cmtSolicitudCmMglFacadeLocal.generarReporteDetalladoContar(tipoReporteSelected, cmtTipoSolicitudMgl, fechaInicio,
                                            fechaFinal, estadoSelected, 0, 0, usuarioVT);

            long totalPag = numRegistrosTotal;           
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            for (int j = 0; j < NOM_COLUMNAS_DETALLADO.length; j++) {
                sb.append(NOM_COLUMNAS_DETALLADO[j]);
                if (j < NOM_COLUMNAS_DETALLADO.length) {
                    sb.append(";");
                }
            }
            sb.append("\n");
            
            String todayStr = formato.format(new Date());
            String fileName = "Reporte_Detallado_Sol_CCMM" + "_" + todayStr + "." + ext;
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");           
            response1.setCharacterEncoding("UTF-8");
            
            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);
            

            for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }
                                   
              List<CmtReporteDetalladoDto> resulSolicitudestList =  cmtSolicitudCmMglFacadeLocal.getSolicitudesSearchDetalle(tipoReporteSelected,
                                            cmtTipoSolicitudMgl, fechaInicio, fechaFinal, estadoSelected,
                                            inicioRegistros, expLonPag, usuarioVT, inicioRegistros, expLonPag );
                
                //listado de nodos cargados      
                if (resulSolicitudestList != null && !resulSolicitudestList.isEmpty()) {
                    
                   for (CmtReporteDetalladoDto a : resulSolicitudestList) {

                        if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                        String celda1 = a.getAsesor();
                        sb.append(celda1);
                        sb.append(";");
                        
                        String celda2 = Integer.toString(a.getSolicitudCmId() != null ? a.getSolicitudCmId().intValueExact() : null);
                        sb.append(celda2);
                        sb.append(";");
                        
                        String celda3 = a.getCuentaMatrizId() != null ? String.valueOf(a.getCuentaMatrizId()) : "";
                        sb.append(celda3);
                        sb.append(";");
                        
                        String celda4 = a.getCuentaMatrizIdRR() != null ? String.valueOf(a.getCuentaMatrizIdRR()): "";
                        sb.append(celda4);
                        sb.append(";");
                        
                        String celda5 = a.getDireccionCm()!= null ?  a.getDireccionCm(): "";
                        sb.append(celda5);
                        sb.append(";");
                        
                        String celda6 = a.getBarrio()!= null ? a.getBarrio(): "";
                        sb.append(celda6);
                        sb.append(";");
                        
                        String celda7 = a.getCuentaMatrizNombre();
                        sb.append(celda7);
                        sb.append(";");
                        
                        String celda8 = a.getTipoEdificio();
                        sb.append(celda8);
                        sb.append(";");
                        
                        String celda9 = a.getUsuarioSolicitudNombre();
                        sb.append(celda9);
                        sb.append(";");
                        
                        String celda10 = a.getCorreoUsuarioSolicitante();
                        sb.append(celda10);
                        sb.append(";");
                        
                        String celda11 = a.getTelefonoSolicitante();
                        sb.append(celda11);
                        sb.append(";");
                        
                        String celda12 = a.getCorreoAsesor();
                        sb.append(celda12);
                        sb.append(";");
                        
                        String celda13 = a.getCopiaA();
                        sb.append(celda13);
                        sb.append(";");
                        
                        String celda14 = a.getNodoBi() != null ? a.getNodoBi() : "";
                        sb.append(celda14);
                        sb.append(";");
                        
                        String celda15 = a.getNombreTipoSolicitud();
                        sb.append(celda15);
                        sb.append(";");
                        
                        String fechaIniCreacion = "";
                        if (a.getFechaIniCreacionSol() != null) {
                            fechaIniCreacion = formato.format(a.getFechaIniCreacionSol());
                        }
                        String celda16 = fechaIniCreacion;
                        sb.append(celda16);
                        sb.append(";");
                        
                        String celda17 = a.getTempSolicitud();
                        sb.append(celda17);
                        sb.append(";");
                        
                        String celda18 = "";
                        if (a.getFechaGestion() != null) {
                            celda18 = formato.format(a.getFechaGestion());
                        }
                        sb.append(celda18);
                        sb.append(";");
                        
                        String celda19 = a.getTempGestion();
                        sb.append(celda19);
                        sb.append(";");
                        
                        String celda20 = String.valueOf(a.getCantidadTorres());
                        sb.append(celda20);
                        sb.append(";");
                        
                        String celda21 = String.valueOf(a.getNombreEstadoSolicitud());
                        sb.append(celda21);
                        sb.append(";");
                        
                        String celda22 = a.getOrigenSolicitud();
                        sb.append(celda22);
                        sb.append(";");
                        
                        String celda23 = a.getUsuarioGestionNombre() != null ? a.getUsuarioGestionNombre() : "";
                        sb.append(celda23);
                        sb.append(";");
                        
                        String celda24 = a.getRespuestaActual() != null ? StringUtils.caracteresEspeciales(a.getRespuestaActual()) : "";
                        sb.append(celda24);
                        sb.append(";");
                        
                        String celda25 = a.getResultadoGestion() != null ? StringUtils.caracteresEspeciales(a.getResultadoGestion()) : "";
                        sb.append(celda25);
                        sb.append(";");
                        
                        String celda26 = a.getNotasSolicitante() != null ? StringUtils.caracteresEspeciales(a.getNotasSolicitante()) : "";
                        sb.append(celda26);
                        sb.append(";");
                        
                        String celda27 = "";
                        if (a.getFechaInicioGestion() != null) {
                            celda27 = formato.format(a.getFechaInicioGestion());
                        }
                        sb.append(celda27);
                        sb.append(";");
                        
                        String fechaFinCreacion = "";
                        if (a.getFechaFinCreacionSol() != null) {
                            fechaFinCreacion = formato.format(a.getFechaFinCreacionSol());
                        }
                        String celda28 = fechaFinCreacion;
                        sb.append(celda28);
                        sb.append(";");
                        
                        String celda29 = String.valueOf(a.getCantidadHhpp());
                        sb.append(celda29);
                        sb.append(";");
                        
                        String celda30 = a.getContactoConstructora() != null ? a.getContactoConstructora() : "";
                        sb.append(celda30);
                        sb.append(";");
                        
                        String celda31 = a.getAdministracion() != null ? a.getAdministracion() : "";
                        sb.append(celda31);
                        sb.append(";");
                        
                        String celda32 = a.getContactoAdministracion() != null ? a.getContactoAdministracion() : "";
                        sb.append(celda32);
                        sb.append(";");
                        
                        String celda33 = a.getAscensores() != null ? a.getAscensores() : "";
                        sb.append(celda33);
                        sb.append(";");
                        
                        String celda34 = a.getContactoAscensores() != null ? a.getContactoAscensores() : "";
                        sb.append(celda34);
                        sb.append(";");
                        
                        String celda35 = a.getDirAntigua() != null ? a.getDirAntigua() : "";
                        sb.append(celda35);
                        sb.append(";");
                        
                        String celda36 = a.getTelefonoPorteria1() != null ? a.getTelefonoPorteria1() : "";
                        sb.append(celda36);
                        sb.append(";");
                        
                        String celda37 = a.getTelefonoPorteria2() != null ? a.getTelefonoPorteria2() : "";
                        sb.append(celda37);
                        sb.append(";");
                        
                        String celda38 = a.getDirTraducida() != null ? a.getDirTraducida() : "";
                        sb.append(celda38);
                        sb.append(";");
                        
                        String celda39 = a.getFuente() != null ? a.getFuente() : "";
                        sb.append(celda39);
                        sb.append(";");
                        
                        String celda40 = a.getTecnologiaSolGes() != null ? a.getTecnologiaSolGes() : "";
                        sb.append(celda40);
                        sb.append(";");
                        
                        String celda41 = a.getBarrioGeo() != null ? a.getBarrioGeo() : "";
                        sb.append(celda41);
                        sb.append(";");
                        
                        String celda42 = a.getNivelSoGeo() != null ? a.getNivelSoGeo() : "";
                        sb.append(celda42);
                        sb.append(";");
                        
                        String celda43 = a.getCx() != null ? a.getCx() : "";
                        sb.append(celda43);
                        sb.append(";");
                        
                        String celda44 = a.getCy() != null ? a.getCy() : "";
                        sb.append(celda44);
                        sb.append(";");
                        
                        String celda45 = a.getTecnoGestionDTH() != null ? a.getTecnoGestionDTH() : "";
                        sb.append(celda45);
                        sb.append(";");
                        
                        String celda46 = a.getTecnoGestionFTTH() != null ? a.getTecnoGestionFTTH() : "";
                        sb.append(celda46);
                        sb.append(";");
                        
                        String celda47 = a.getTecnoGestionFOG()!= null ? a.getTecnoGestionFOG() : "";
                        sb.append(celda47);
                        sb.append(";");
                        
                        String celda48 = a.getTecnoGestionFOU() != null ? a.getTecnoGestionFOU() : "";
                        sb.append(celda48);
                        sb.append(";");
                        
                        String celda49 = a.getTecnoGestionBID() != null ? a.getTecnoGestionBID() : "";
                        sb.append(celda49);
                        sb.append(";");
                        
                        String celda50 = a.getTecnoGestionUNI() != null ? a.getTecnoGestionUNI() : "";
                        sb.append(celda50);
                        sb.append(";");
                        
                        String celda51 = a.getAreaSolictud();
                        sb.append(celda51);
                        sb.append(";");
                        
                        String celda52 = "";
                        if (a.getFechaEntrega() != null) {
                            celda52 = formato.format(a.getFechaEntrega());
                        }
                        sb.append(celda52);
                        sb.append(";");
                        
                        String celda53 = a.getNodoUni1() != null ? a.getNodoUni1() : "";
                        sb.append(celda53);
                        sb.append(";");
                        
                        String celda54 = a.getNodoUni2() != null ? a.getNodoUni2() : "";
                        sb.append(celda54);
                        sb.append(";");
                        
                        String celda55 = a.getNodoDth() != null ? a.getNodoDth() : "";
                        sb.append(celda55);
                        sb.append(";");
                        
                        String celda56 = a.getNodoMovil() != null ? a.getNodoMovil() : "";
                        sb.append(celda56);
                        sb.append(";");
                        
                        String celda57 = a.getNodoFtth() != null ? a.getNodoFtth() : "";
                        sb.append(celda57);
                        sb.append(";");
                        
                        String celda58 = a.getNodoWifi() != null ? a.getNodoWifi() : "";
                        sb.append(celda58);
                        sb.append(";");
                        
                        String celda59 = a.getTecnoGestionLTE() != null ? a.getTecnoGestionLTE() : "";
                        sb.append(celda59);
                        sb.append(";");
                        
                        String celda60 = a.getTecnoGestionMOV() != null ? a.getTecnoGestionMOV() : "";
                        sb.append(celda60);
                        sb.append(";");
                        
                        Short celda61 = a.getModSubedificios();
                        sb.append(celda61);
                        sb.append(";");
                        
                        Short celda62 = a.getModDireccion();
                        sb.append(celda62);
                        sb.append(";");
                        
                        String celda63 = a.getConfiabilidad() != null ? a.getConfiabilidad() : "";
                        sb.append(celda63);
                        sb.append(";");
                        
                        String celda64 = a.getConfiabilidadComplemento() != null ? a.getConfiabilidadComplemento() : "";
                        sb.append(celda64);
                        sb.append(";");
                        
                        String celda65 = a.getEstado() != null ? a.getEstado() : "";
                        sb.append(celda65);
                        sb.append(";");
                        
                        Short celda66 = a.getModDatosCm();
                        sb.append(celda66);
                        sb.append(";");
                        
                        Short celda67 = a.getModCobertura();
                        sb.append(celda67);
                        sb.append(";");
                        
                        String celda68 = a.getDepartamentoGpo();
                        sb.append(celda68);
                        sb.append(";");
                        
                        String celda69 = a.getCiudad();
                        sb.append(celda69);
                        sb.append(";");
                        
                        String celda70 = a.getCentroPobladoGpo();
                        sb.append(celda70);
                        sb.append(";");
                        
                        String celda71 = a.getCuentaMatrizNombreDespues() != null ? a.getCuentaMatrizNombreDespues() : "";
                        sb.append(celda71);
                        sb.append(";");
                        
                        String celda72 = a.getTipoEdificioDespues() != null ? a.getTipoEdificioDespues() : "";
                        sb.append(celda72);
                        sb.append(";");
                        
                        String celda73 = a.getTelefonoPorteria1Despues() != null ? a.getTelefonoPorteria1Despues() : "";
                        sb.append(celda73);
                        sb.append(";");
                        
                        String celda74 = a.getTelefonoPorteria2Despues() != null ? a.getTelefonoPorteria2Despues() : "";
                        sb.append(celda74);
                        sb.append(";");
                        
                        String celda75 = a.getAdministracionDespues() != null ? a.getAdministracionDespues() : "";
                        sb.append(celda75);
                        sb.append(";");
                        
                        String celda76 = a.getContactoAdministracionDespues() != null ? a.getContactoAdministracionDespues() : "";
                        sb.append(celda76);
                        sb.append(";");
                        
                        String celda77 = a.getAscensoresDespues() != null ? a.getAscensoresDespues() : "";
                        sb.append(celda77);
                        sb.append(";");
                        
                        String celda78 = a.getSubedificiosAntes() != null ? a.getSubedificiosAntes() : "";
                        sb.append(celda78);
                        sb.append(";");
                        
                        String celda79 = a.getSubedificiosDespues() != null ? a.getSubedificiosDespues() : "";
                        sb.append(celda79);
                        sb.append(";");
                        
                        String celda80 = a.getDireccionCmAntes() != null ? a.getDireccionCmAntes() : "";
                        sb.append(celda80);
                        sb.append(";");
                        
                        String celda81 = a.getDireccionCmDespues() != null ? a.getDireccionCmDespues() : "";
                        sb.append(celda81);
                        sb.append(";");
                        
                        String celda82 = a.getCoberturaCmAntes() != null ? a.getCoberturaCmAntes() : "";
                        sb.append(celda82);
                        sb.append(";");
                        
                        String celda83 = a.getCoberturaCmDespues() != null ? a.getCoberturaCmDespues() : "";
                        sb.append(celda83);
                        sb.append(";");
                        
                        String celda84 = a.getResultGestionModDatosCm() != null ? a.getResultGestionModDatosCm().replaceAll(";", "") : "";
                        sb.append(celda84);
                        sb.append(";");
                        
                        String celda85 = a.getResultGestionModDir() != null ? a.getResultGestionModDir().replaceAll(";", "") : "";;
                        sb.append(celda85);
                        sb.append(";");
                        
                        String celda86 = a.getResultGestionModSubEdi() != null ? a.getResultGestionModSubEdi().replaceAll(";", "") : "";
                        sb.append(celda86);
                        sb.append(";");
                        
                        String celda87 = a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura().replaceAll(";", "") : "";
                        sb.append(celda87);
                        sb.append(";");
                        
                        String celda88 = "";
                        if (a.getFechaInicioGestionCobertura() != null) {
                            celda88 = formato.format(a.getFechaInicioGestionCobertura());
                        }
                        sb.append(celda88);
                        sb.append(";");

                        String celda89 = "";
                        if (a.getFechaGestionCobertura() != null) {
                            celda89 = formato.format(a.getFechaGestionCobertura());
                        }
                        sb.append(celda89);
                        sb.append(";");
                        
                        String celda90 = a.getUsuarioGestionCobertura() != null ? a.getUsuarioGestionCobertura() : "";
                        sb.append(celda90);
                        sb.append(";");
                        
                        String celda91 = String.valueOf(a.getCantidadSubedicifiosMod());
                        sb.append(celda91);
                        sb.append(";");
                        
                        //Datos creacion/modificacion Hhpp
                        String celda92 = String.valueOf(a.getCantidadHhppMod());
                        sb.append(celda92);
                        sb.append(";");
                        
                        String celda93 = a.getHhppCmModificados() != null ? a.getHhppCmModificados() : "";
                        sb.append(celda93);
                        sb.append(";");
                        
                        String celda94 = String.valueOf(a.getCantidadHhppCre());
                        sb.append(celda94);
                        sb.append(";");
                        
                        String celda95 = a.getHhppCmCreados() != null ? a.getHhppCmCreados() : "";
                        sb.append(celda95);
                        sb.append(";");
                        
                        String celda96 = "";
                        if (a.getFechaInicioGestionHhpp() != null) {
                            celda96 = formato.format(a.getFechaInicioGestionHhpp());
                        }
                        sb.append(celda96);
                        sb.append(";");

                        String celda97 = "";
                        if (a.getFechaGestionHhpp() != null) {
                            celda97 = formato.format(a.getFechaGestionHhpp());
                        }
                        sb.append(celda97);
                        sb.append(";");
                        

                        //Datos Visita Tecnica
                        String celda98 = a.getAcondicionamientoVT() != null ? a.getAcondicionamientoVT() : "";
                        sb.append(celda98);
                        sb.append(";");
                        
                        String celda99 = a.getTipoSegmentoVT() != null ? a.getTipoSegmentoVT() : "";
                        sb.append(celda99);
                        sb.append(";");
                        
                        String celda100 = a.getTecnologiaVt() != null ? a.getTecnologiaVt() : "";
                        sb.append(celda100);
                        sb.append(";");

                        String celda101 = "";
                        if (a.getFechaProgramacionVT() != null) {
                            celda101 = formato.format(a.getFechaProgramacionVT());
                        }
                        sb.append(celda101);
                        sb.append(";");
                        
                        String celda102 = String.valueOf(a.getCantidadTorresVt());;
                        sb.append(celda102);
                        sb.append(";");
                        
                        String celda103 = a.getAdministradorVT() != null ? a.getAdministradorVT() : "";
                        sb.append(celda103);
                        sb.append(";");
                        
                        String celda104 = a.getCelAdministradorVT() != null ? a.getCelAdministradorVT() : "";
                        sb.append(celda104);
                        sb.append(";");
                        
                        String celda105 = a.getCorreoAdministradorVT() != null ? a.getCorreoAdministradorVT() : "";
                        sb.append(celda105);
                        sb.append(";");
                        
                        String celda106 = a.getTelAdministradorVT() != null ? a.getTelAdministradorVT() : "";
                        sb.append(celda106);
                        sb.append(";");
                        
                        String celda107 = a.getNombreContactoVt() != null ? a.getNombreContactoVt() : "";
                        sb.append(celda107);
                        sb.append(";");
                        
                        String celda108 = a.getConstructora() != null ? a.getConstructora() : "";
                        sb.append(celda108);
                        sb.append(";");
                        
                        sb.append("\n");
                        csvData = sb.toString().getBytes();
                        response1.getOutputStream().write(csvData);
                        response1.getOutputStream().flush();
                        response1.flushBuffer();

                    }
                    System.gc();
                }
            }     
            response1.getOutputStream().close();
            fc.responseComplete();
          
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    public String exportExcelDetOld(List<CmtReporteDetalladoDto> cmtReporteDetalladoDto) throws ApplicationException, IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ReporteDetalladoSolCM");
            String[] titulos = titulosReporteDetallado();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            int rownum = 0;
            int cellnum = 0;
            
              int expLonPag = 50;
             CmtTipoSolicitudMgl cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
                                    cmtTipoSolicitudMgl.setTipoSolicitudId(tipoSolicitudSelected);
                                    
            int numRegistrosTotal = cmtSolicitudCmMglFacadeLocal.generarReporteDetalladoContar(tipoReporteSelected, cmtTipoSolicitudMgl, fechaInicio,
                                            fechaFinal, estadoSelected, 0, 0, usuarioVT);
 
            
            int totalPag = numRegistrosTotal;
            for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }
                                   
                List<CmtReporteDetalladoDto> resulSolicitudestList = cmtSolicitudCmMglFacadeLocal.getSolicitudesSearchDetalle(tipoReporteSelected,
                        cmtTipoSolicitudMgl, fechaInicio, fechaFinal, estadoSelected,
                        inicioRegistros, expLonPag, usuarioVT, inicioRegistros, expLonPag);

            
            if (resulSolicitudestList != null && !resulSolicitudestList.isEmpty()) {

                for (CmtReporteDetalladoDto a : resulSolicitudestList) {
 
                    Row row = sheet.createRow(rownum);
                    if (rownum == 0) {
                        for (String c : titulos) {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(c);
                        }
                        rownum++;
                    }
                    cellnum = 0;
                    row = sheet.createRow(rownum);
                    
                    //Datos De la solicitud
                    Cell celda1 = row.createCell(cellnum++);
                    celda1.setCellValue(a.getAsesor());
                    Cell celda2 = row.createCell(cellnum++);
                    celda2.setCellValue(a.getSolicitudCmId().toString());
                    Cell celda3 = row.createCell(cellnum++);
                    celda3.setCellValue(a.getCuentaMatrizId() != null ? a.getCuentaMatrizId().toString() : "");
                    Cell celda4 = row.createCell(cellnum++);
                    celda4.setCellValue(a.getCuentaMatrizIdRR() != null ? a.getCuentaMatrizIdRR().toString() : "");
                    Cell celda5 = row.createCell(cellnum++);
                    celda5.setCellValue(a.getDireccionCm());
                    //Datos De la solicitud

                    //Informacion cuenta matriz 
                    Cell celda6 = row.createCell(cellnum++);
                    celda6.setCellValue(a.getBarrio() != null ? a.getBarrio() : "");
                    Cell celda7 = row.createCell(cellnum++);
                    celda7.setCellValue(a.getCuentaMatrizNombre() != null ? a.getCuentaMatrizNombre() : "");
                    Cell celda8 = row.createCell(cellnum++);
                    celda8.setCellValue(a.getTipoEdificio() != null ? a.getTipoEdificio() : "");
                    Cell celda9 = row.createCell(cellnum++);
                    celda9.setCellValue(a.getUsuarioSolicitudNombre() != null ? a.getUsuarioSolicitudNombre() : "");
                    Cell celda10 = row.createCell(cellnum++);
                    celda10.setCellValue(a.getCorreoUsuarioSolicitante() != null ? a.getCorreoUsuarioSolicitante() : "");
                    Cell celda11 = row.createCell(cellnum++);
                    celda11.setCellValue(a.getTelefonoSolicitante());
                    Cell celda12 = row.createCell(cellnum++);
                    celda12.setCellValue(a.getCorreoAsesor());
                    Cell celda13 = row.createCell(cellnum++);
                    celda13.setCellValue(a.getCopiaA());
                    Cell celda14 = row.createCell(cellnum++);
                    celda14.setCellValue(a.getNodoBi() != null ? a.getNodoBi() : "");
                    Cell celda15 = row.createCell(cellnum++);
                    celda15.setCellValue(a.getNombreTipoSolicitud());
                    Cell celda16 = row.createCell(cellnum++);
                    String fechaInicreacion = "";
                    if (a.getFechaIniCreacionSol() != null) {
                        fechaInicreacion = formato.format(a.getFechaIniCreacionSol());
                    }
                    celda16.setCellValue(fechaInicreacion);
                    Cell celda17 = row.createCell(cellnum++);
                    celda17.setCellValue(a.getTempSolicitud());
                    Cell celda18 = row.createCell(cellnum++);
                    String fechaGes = "";
                    if (a.getFechaGestion() != null) {
                        fechaGes = formato.format(a.getFechaGestion());
                    }
                    celda18.setCellValue(fechaGes);
                    Cell celda19 = row.createCell(cellnum++);
                    celda19.setCellValue(a.getTempGestion());
                    Cell celda20 = row.createCell(cellnum++);
                    celda20.setCellValue(a.getCantidadTorres());
                    Cell celda21 = row.createCell(cellnum++);
                    celda21.setCellValue(a.getNombreEstadoSolicitud());
                    Cell celda22 = row.createCell(cellnum++);
                    celda22.setCellValue(a.getOrigenSolicitud());
                    Cell celda23 = row.createCell(cellnum++);
                    celda23.setCellValue(a.getUsuarioGestionNombre() != null ? a.getUsuarioGestionNombre() : "");
                    Cell celda24 = row.createCell(cellnum++);
                    celda24.setCellValue(a.getRespuestaActual());
                    Cell celda25 = row.createCell(cellnum++);
                    celda25.setCellValue(a.getResultadoGestion());
                    //Informacion cuenta matriz 

                    //Datos Solicitante
                    Cell celda26 = row.createCell(cellnum++);
                    celda26.setCellValue(a.getNotasSolicitante() != null ? a.getNotasSolicitante() : "");
                    Cell celda27 = row.createCell(cellnum++);
                    String fechaIniGes = "";
                    if (a.getFechaInicioGestion() != null) {
                        fechaIniGes = formato.format(a.getFechaInicioGestion());
                    }
                    celda27.setCellValue(fechaIniGes);
                    Cell celda28 = row.createCell(cellnum++);
                    String fechaFincreacion = "";
                    if (a.getFechaFinCreacionSol() != null) {
                        fechaFincreacion = formato.format(a.getFechaFinCreacionSol());
                    }
                    celda28.setCellValue(fechaFincreacion);
                    Cell celda29 = row.createCell(cellnum++);
                    celda29.setCellValue(a.getCantidadHhpp());
                    Cell celda33 = row.createCell(cellnum++);
                    celda33.setCellValue(a.getContactoConstructora() != null ? a.getContactoConstructora() : "");
                    Cell celda34 = row.createCell(cellnum++);
                    celda34.setCellValue(a.getAdministracion() != null ? a.getAdministracion() : "");
                    Cell celda35 = row.createCell(cellnum++);
                    celda35.setCellValue(a.getContactoAdministracion() != null ? a.getContactoAdministracion() : "");
                    Cell celda36 = row.createCell(cellnum++);
                    celda36.setCellValue(a.getAscensores() != null ? a.getAscensores() : "");
                    Cell celda37 = row.createCell(cellnum++);
                    celda37.setCellValue(a.getContactoAscensores() != null ? a.getContactoAscensores() : "");
                    //Datos Solicitante

                    //Datos General Gestion
                    Cell celda38 = row.createCell(cellnum++);
                    celda38.setCellValue(a.getDirAntigua() != null ? a.getDirAntigua() : "");
                    Cell celda39 = row.createCell(cellnum++);
                    celda39.setCellValue(a.getTelefonoPorteria1() != null ? a.getTelefonoPorteria1() : "");
                    Cell celda40 = row.createCell(cellnum++);
                    celda40.setCellValue(a.getTelefonoPorteria2() != null ? a.getTelefonoPorteria2() : "");
                    Cell celda41 = row.createCell(cellnum++);
                    celda41.setCellValue(a.getDirTraducida() != null ? a.getDirTraducida() : "");
                    Cell celda42 = row.createCell(cellnum++);
                    celda42.setCellValue(a.getFuente() != null ? a.getFuente() : "");
                    Cell celda43 = row.createCell(cellnum++);
                    celda43.setCellValue(a.getTecnologiaSolGes() != null ? a.getTecnologiaSolGes() : "");
                    Cell celda44 = row.createCell(cellnum++);
                    celda44.setCellValue(a.getBarrioGeo() != null ? a.getBarrioGeo() : "");
                    //Datos General Gestion

                    //Datos Creacion CM
                    //Geo
                    //Se reorganiza la confiablidad y confiabilidad Placa, con Estado_dir
                    Cell celda45 = row.createCell(cellnum++);
                    celda45.setCellValue(a.getNivelSoGeo() != null ? a.getNivelSoGeo() : "");
                    Cell celda46 = row.createCell(cellnum++);
                    celda46.setCellValue(a.getCx() != null ? a.getCx() : "");
                    Cell celda47 = row.createCell(cellnum++);
                    celda47.setCellValue(a.getCy() != null ? a.getCy() : "");
                    Cell celda48 = row.createCell(cellnum++);
                    celda48.setCellValue(a.getTecnoGestionDTH()!= null ? a.getTecnoGestionDTH() : "");
                    Cell celda49 = row.createCell(cellnum++);
                    celda49.setCellValue(a.getTecnoGestionFTTH()!= null ? a.getTecnoGestionFTTH() : "");
                    Cell celda50 = row.createCell(cellnum++);
                    celda50.setCellValue(a.getTecnoGestionFOG()!= null ? a.getTecnoGestionFOG() : "");
                    Cell celda51 = row.createCell(cellnum++);
                    celda51.setCellValue(a.getTecnoGestionFOU()!= null ? a.getTecnoGestionFOU() : "");
                    Cell celda52 = row.createCell(cellnum++);
                    celda52.setCellValue(a.getTecnoGestionBID()!= null ? a.getTecnoGestionBID() : "");
                    Cell celda53 = row.createCell(cellnum++);
                    celda53.setCellValue(a.getTecnoGestionUNI()!= null ? a.getTecnoGestionUNI() : "");
                    Cell celda54 = row.createCell(cellnum++);
                    celda54.setCellValue(a.getAreaSolictud());
                    Cell celda55 = row.createCell(cellnum++);
                    String fechaEntrega = "";
                    if (a.getFechaEntrega() != null) {
                        fechaEntrega = formato.format(a.getFechaEntrega());
                    }
                    celda55.setCellValue(fechaEntrega);
                    Cell celda56 = row.createCell(cellnum++);
                    celda56.setCellValue(a.getNodoUni1() != null ? a.getNodoUni1() : "");
                    Cell celda57 = row.createCell(cellnum++);
                    celda57.setCellValue(a.getNodoUni2() != null ? a.getNodoUni2() : "");
                    Cell celda58 = row.createCell(cellnum++);
                    celda58.setCellValue(a.getNodoDth() != null ? a.getNodoDth() : "");
                    Cell celda59 = row.createCell(cellnum++);
                    celda59.setCellValue(a.getNodoMovil() != null ? a.getNodoMovil() : "");
                    Cell celda60 = row.createCell(cellnum++);
                    celda60.setCellValue(a.getNodoFtth() != null ? a.getNodoFtth() : "");
                    Cell celda61 = row.createCell(cellnum++);
                    celda61.setCellValue(a.getNodoWifi() != null ? a.getNodoWifi() : "");
                    //Geo

                    //Gestion Cm
                    Cell celda62 = row.createCell(cellnum++);
                    celda62.setCellValue(a.getTecnoGestionLTE()!= null ? a.getTecnoGestionLTE() : "");
                    Cell celda63 = row.createCell(cellnum++);
                    celda63.setCellValue(a.getTecnoGestionMOV()!= null ? a.getTecnoGestionMOV() : "");
                    Cell celda64 = row.createCell(cellnum++);
                    celda64.setCellValue(a.getModSubedificios());
                    Cell celda65 = row.createCell(cellnum++);
                    celda65.setCellValue(a.getModDireccion());
                    Cell celda66 = row.createCell(cellnum++);
                    celda66.setCellValue(a.getConfiabilidad() != null ? a.getConfiabilidad() : "");
                    Cell celda67 = row.createCell(cellnum++);
                    celda67.setCellValue(a.getConfiabilidadComplemento()!= null ? a.getConfiabilidadComplemento() : "");
                    Cell celda68 = row.createCell(cellnum++);
                    celda68.setCellValue(a.getEstado() != null ? a.getEstado() : "");
                    Cell celda69 = row.createCell(cellnum++);
                    celda69.setCellValue(a.getModDatosCm());
                    Cell celda70 = row.createCell(cellnum++);
                    celda70.setCellValue(a.getModCobertura());
                    //Gestion Cm
                    //Datos Creacion CM

                    //Datos Modificacion CM
                    Cell celda71 = row.createCell(cellnum++);
                    celda71.setCellValue(a.getDepartamentoGpo());
                    Cell celda72 = row.createCell(cellnum++);
                    celda72.setCellValue(a.getCiudad());
                    Cell celda73 = row.createCell(cellnum++);
                    celda73.setCellValue(a.getCentroPobladoGpo());
                    Cell celda74 = row.createCell(cellnum++);
                    celda74.setCellValue(a.getCuentaMatrizNombreDespues() != null ? a.getCuentaMatrizNombreDespues() : "");
                    Cell celda75 = row.createCell(cellnum++);
                    celda75.setCellValue(a.getTipoEdificioDespues() != null ? a.getTipoEdificioDespues() : "");
                    Cell celda76 = row.createCell(cellnum++);
                    celda76.setCellValue(a.getTelefonoPorteria1Despues() != null ? a.getTelefonoPorteria1Despues() : "");
                    Cell celda77 = row.createCell(cellnum++);
                    celda77.setCellValue(a.getTelefonoPorteria2Despues() != null ? a.getTelefonoPorteria2Despues() : "");
                    Cell celda78 = row.createCell(cellnum++);
                    celda78.setCellValue(a.getAdministracionDespues() != null ? a.getAdministracionDespues() : "");
                    Cell celda79 = row.createCell(cellnum++);
                    celda79.setCellValue(a.getContactoAdministracionDespues() != null ? a.getContactoAdministracionDespues() : "");
                    Cell celda80 = row.createCell(cellnum++);
                    celda80.setCellValue(a.getAscensoresDespues() != null ? a.getAscensoresDespues() : "");
                    Cell celda81 = row.createCell(cellnum++);
                    celda81.setCellValue(a.getSubedificiosAntes() != null ? a.getSubedificiosAntes() : "");
                    Cell celda82 = row.createCell(cellnum++);
                    celda82.setCellValue(a.getSubedificiosDespues() != null ? a.getSubedificiosDespues() : "");
                    Cell celda83 = row.createCell(cellnum++);
                    celda83.setCellValue(a.getDireccionCmAntes() != null ? a.getDireccionCmAntes() : "");
                    Cell celda84 = row.createCell(cellnum++);
                    celda84.setCellValue(a.getDireccionCmDespues() != null ? a.getDireccionCmDespues() : "");
                    Cell celda85 = row.createCell(cellnum++);
                    celda85.setCellValue(a.getCoberturaCmAntes() != null ? a.getCoberturaCmAntes() : "");
                    Cell celda86 = row.createCell(cellnum++);
                    celda86.setCellValue(a.getCoberturaCmDespues() != null ? a.getCoberturaCmDespues() : "");
                    Cell celda87 = row.createCell(cellnum++);
                    celda87.setCellValue(a.getResultGestionModDatosCm() != null ? a.getResultGestionModDatosCm() : "");


                    Cell celda88 = row.createCell(cellnum++);
                    celda88.setCellValue(a.getResultGestionModDir() != null ? a.getResultGestionModDir() : "");
                    Cell celda89 = row.createCell(cellnum++);
                    celda89.setCellValue(a.getResultGestionModSubEdi() != null ? a.getResultGestionModSubEdi() : "");
                    Cell celda90 = row.createCell(cellnum++);
                    celda90.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    Cell celda91 = row.createCell(cellnum++);                    
                    celda91.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    String fechaIniCob = "";
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    celda91.setCellValue(fechaIniCob);
                    Cell celda92 = row.createCell(cellnum++);
                    String fechaGesCob = "";
                    if (a.getFechaGestionCobertura() != null) {
                        fechaGesCob = formato.format(a.getFechaGestionCobertura());
                    }
                    celda92.setCellValue(fechaGesCob);
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    Cell celda93 = row.createCell(cellnum++);
                    celda93.setCellValue(a.getUsuarioGestionCobertura() != null ? a.getUsuarioGestionCobertura() : "");

                    Cell celda94 = row.createCell(cellnum++);
                    celda94.setCellValue(a.getCantidadSubedicifiosMod());
                    
                    Cell celda95 = row.createCell(cellnum++);
                    celda95.setCellValue(a.getCantidadHhppMod());

                    //Datos Modificacion CM
                    //Datos creacion/modificacion Hhpp
                    Cell celda96 = row.createCell(cellnum++);
                    celda96.setCellValue(a.getHhppCmModificados() != null ? a.getHhppCmModificados() : "");
                    Cell celda97 = row.createCell(cellnum++);
                    celda97.setCellValue(a.getCantidadHhppCre());                    
                    Cell celda98 = row.createCell(cellnum++);
                    celda98.setCellValue(a.getHhppCmCreados() != null ? a.getHhppCmCreados() : "");                    
                    Cell celda99 = row.createCell(cellnum++);
                    String fechaIniGeshh = "";
                    if (a.getFechaInicioGestionHhpp() != null) {
                        fechaIniGeshh = formato.format(a.getFechaInicioGestionHhpp());
                    }
                    celda99.setCellValue(fechaIniGeshh);

                    Cell celda100 = row.createCell(cellnum++);
                    String fechaGeshh = "";
                    if (a.getFechaGestionHhpp() != null) {
                        fechaGeshh = formato.format(a.getFechaGestionHhpp());
                    }
                    celda100.setCellValue(fechaGeshh);
                    
                    Cell celda101 = row.createCell(cellnum++);
                    celda101.setCellValue(a.getAcondicionamientoVT() != null ? a.getAcondicionamientoVT() : "");

                    //Datos Visita Tecnica
                    Cell celda102 = row.createCell(cellnum++);
                    celda102.setCellValue(a.getTipoSegmentoVT() != null ? a.getTipoSegmentoVT() : "");
                    Cell celda103 = row.createCell(cellnum++);
                    celda103.setCellValue(a.getTecnologiaVt() != null ? a.getTecnologiaVt() : "");
                    Cell celda104 = row.createCell(cellnum++);
                    String fechaPro = "";
                    if (a.getFechaProgramacionVT() != null) {
                        fechaPro = formato.format(a.getFechaProgramacionVT());
                    }
                    celda104.setCellValue(fechaPro);
                    
                    Cell celda105 = row.createCell(cellnum++);
                    celda105.setCellValue(a.getCantidadTorresVt());

                    Cell celda106 = row.createCell(cellnum++);
                    celda106.setCellValue(a.getAdministradorVT() != null ? a.getAdministradorVT() : "");
                    Cell celda107 = row.createCell(cellnum++);
                    celda107.setCellValue(a.getCelAdministradorVT() != null ? a.getCelAdministradorVT() : "");
                    Cell celda108 = row.createCell(cellnum++);                    
                    celda108.setCellValue(a.getCorreoAdministradorVT() != null ? a.getCorreoAdministradorVT() : "");
                    Cell celda109 = row.createCell(cellnum++);
                    celda109.setCellValue(a.getTelAdministradorVT() != null ? a.getTelAdministradorVT() : "");
                    Cell celda110 = row.createCell(cellnum++);
                    celda110.setCellValue(a.getNombreContactoVt() != null ? a.getNombreContactoVt() : "");
                    Cell celda111 = row.createCell(cellnum++);
                    celda111.setCellValue(a.getConstructora() != null ? a.getConstructora() : "");
                   
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.reset();
                response1.setContentType("application/vnd.ms-excel");

                response1.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_Detallado_Sol_CCMM" + "_" + formato.format(new Date())
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
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:exportExcelDet. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:exportExcelDet. " + e.getMessage(), e, LOGGER);
        }
        return "OK";
    }
    
         public String exportExcelDet() {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteDetalladoSolCM");
            //Blank workbook
            String[] objArr = {"ASESOR","SOLICITUD_ID","NUMERO_CUENTA_MGL","NUMERO_CUENTA_RR","DIRECCION_CM",
            "BARRIO_CM","NOMBRE_CM","TIPO_EDIFICIO","USUARIO_SOLICITANTE","CORREO_USUARIO_SOLICITANTE",
            "TELEFONO_SOLICITANTE","CORREO_ASESOR","CORREO_COPIA_A","NODO_BI","TIPO_SOLICITUD",
            "FECHA_INI_CREACION","TIEMPO_SOLICITUD","FECHA_FIN_GESTION","TIEMPO_GESTION","CANTIDAD_TORRES_CM",
            "ESTADO_SOLICITUD","ORIGEN_SOLICITUD","USUARIO_GESTIONADOR","RESPUESTA_ACTUAL",
            "RESULTADO_GESTION","NOTAS_USUARIO_SOLICITA","FECHA_INICIO_GESTION","FECHA_FIN_CREACION",
            "CANTIDAD_HHPP_CM","CONTACTO_CONSTRUCTORA_CM","ADMINISTRACION_CM","CONTACTO_ADMINISTRACION_CM",
            "ASCENSORES_CM","CONTACTO_ASCENSORES_CM","DIRECCION_ANTIGUA","TELEFONO_PORTERIA_1",
            "TELEFONO_PORTERIA_2","DIRECCION_TRADUCCIDA","FUENTE","TECNOLOGIA_SOLICITUD_GESTION",
            "BARRIO_GEOREFERENCIADO","NIVEL_SOCIECONOMICO_GEO","COORDENADA_X","COORDENADA_Y",
            "DTH","FIBRA_FTTH","FIBRA_OPTICA_GPON","FIBRA_OPTICA_UNIFILAR","HFC_BIDIRECCIONAL",
            "HFC_UNIDIRECCIONAL","AREA_SOLICITUD","FECHA_ENTREGA_CM","NODO_UNI_1",
            "NODO_UNI_2","NODO_DTH","NODO_MOVIL","NODO_FTTH","NODO_WIFI","LTE","MOVIL",
            "MODIFICO_SUBEDIFICIO","MODIFICO_DIRECCION","CONFIABILIDAD_DIRECCION","CONFIABILIDAD_COMPLEMENTO",
            "ESTADO_DIR","MODIFICO_DATOS_CM","MODIFICO_COBERTURA",
            
            "DEPARTAMENTO_CM","CIUDAD_CM","CENTRO_POBLADO_CM","NOMBRE_CM_DESPUES","TIPO_EDIFICIO_DESPUES",
            "TELEFONO_PORTERIA_1_DESPUES","TELEFONO_PORTERIA_2_DESPUES","ADMINISTRACION_CM_DESPUES",
            "CONTACTO_ADMINISTRACION_CM_DESPUES","ASCENSORES_CM_DESPUES","SUBEDIFICIOS_ANTES",
            "SUBEDIFICIOS_DESPUES","DIRECCION_CM_ANTES","DIRECCION_CM_DESPUES",
            
            "COBERTURA_CM_ANTES","COBERTURA_CM_DESPUES","RESULTADO_GES_MOD_DATOS_CM","RESULTADO_GES_MOD_DIR",
            "RESULTADO_GES_MOD_SUB","RESULTADO_GES_MOD_COB","FECHA_INI_GESTION_COB","FECHA_GESTION_COB",
            "USUARIO_GESTION_COB","CANTIDAD_SUBEDIFICIOS_MOD","CANTIDAD_HHPP_MOD","HHPP_CM_MODIFICADOS",
            "CANTIDAD_HHPP_CREADOS","HHPP_CM_CREADOS","FECHA_INI_GESTION_HHPP","FECHA_GESTION_HHPP",
            "ACONDICIONAMIENTO_VT","TIPO_SEGMENTO_VT","TECNOLOGIA_VT","FECHA_PROGRAMACION_VT",
            "CANTIDAD_TORRES_VT","ADMINISTRADOR_VT","CELULAR_ADMINISTRADOR_VT","CORREO_ADMINISTRADOR_VT",
            "TELEFONO_ADMINISTRADOR_VT","NOMBRE_CONTACTO_VT","CONSTRUCTORA_CM"};
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            int rownum = 0;
            int expLonPag = 50;
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
                                    cmtTipoSolicitudMgl.setTipoSolicitudId(tipoSolicitudSelected);
                                    
            int totalPag = cmtSolicitudCmMglFacadeLocal.generarReporteDetalladoContar(tipoReporteSelected, cmtTipoSolicitudMgl, fechaInicio,
                                            fechaFinal, estadoSelected, 0, 0, usuarioVT);
            
             for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }

                //consulta paginada de los resultados que se van a imprimir en el reporte
                 List<CmtReporteDetalladoDto> resulSolicitudestList = cmtSolicitudCmMglFacadeLocal.getSolicitudesSearchDetalle(tipoReporteSelected,
                        cmtTipoSolicitudMgl, fechaInicio, fechaFinal, estadoSelected,
                        inicioRegistros, expLonPag, usuarioVT, inicioRegistros, expLonPag);

               
                if (resulSolicitudestList != null && !resulSolicitudestList.isEmpty()) {
                    for (CmtReporteDetalladoDto a : resulSolicitudestList) {
                         Row row = sheet.createRow(rownum);

                        int cellnum = 0;

                        if (rownum == 0) {
                            for (String c : objArr) {
                                Cell cell = row.createCell(cellnum++);
                                cell.setCellValue(c);
                            }
                            rownum++;
                            row = sheet.createRow(rownum);
                            cellnum = 0;
                             //Datos De la solicitud
                    Cell celda1 = row.createCell(cellnum++);
                    celda1.setCellValue(a.getAsesor());
                    Cell celda2 = row.createCell(cellnum++);
                    celda2.setCellValue(a.getSolicitudCmId().toString());
                    Cell celda3 = row.createCell(cellnum++);
                    celda3.setCellValue(a.getCuentaMatrizId() != null ? a.getCuentaMatrizId().toString() : "");
                    Cell celda4 = row.createCell(cellnum++);
                    celda4.setCellValue(a.getCuentaMatrizIdRR() != null ? a.getCuentaMatrizIdRR().toString() : "");
                    Cell celda5 = row.createCell(cellnum++);
                    celda5.setCellValue(a.getDireccionCm());
                    //Datos De la solicitud

                    //Informacion cuenta matriz 
                    Cell celda6 = row.createCell(cellnum++);
                    celda6.setCellValue(a.getBarrio() != null ? a.getBarrio() : "");
                    Cell celda7 = row.createCell(cellnum++);
                    celda7.setCellValue(a.getCuentaMatrizNombre() != null ? a.getCuentaMatrizNombre() : "");
                    Cell celda8 = row.createCell(cellnum++);
                    celda8.setCellValue(a.getTipoEdificio() != null ? a.getTipoEdificio() : "");
                    Cell celda9 = row.createCell(cellnum++);
                    celda9.setCellValue(a.getUsuarioSolicitudNombre() != null ? a.getUsuarioSolicitudNombre() : "");
                    Cell celda10 = row.createCell(cellnum++);
                    celda10.setCellValue(a.getCorreoUsuarioSolicitante() != null ? a.getCorreoUsuarioSolicitante() : "");
                    Cell celda11 = row.createCell(cellnum++);
                    celda11.setCellValue(a.getTelefonoSolicitante());
                    Cell celda12 = row.createCell(cellnum++);
                    celda12.setCellValue(a.getCorreoAsesor());
                    Cell celda13 = row.createCell(cellnum++);
                    celda13.setCellValue(a.getCopiaA());
                    Cell celda14 = row.createCell(cellnum++);
                    celda14.setCellValue(a.getNodoBi() != null ? a.getNodoBi() : "");
                    Cell celda15 = row.createCell(cellnum++);
                    celda15.setCellValue(a.getNombreTipoSolicitud());
                    Cell celda16 = row.createCell(cellnum++);
                    String fechaInicreacion = "";
                    if (a.getFechaIniCreacionSol() != null) {
                        fechaInicreacion = formato.format(a.getFechaIniCreacionSol());
                    }
                    celda16.setCellValue(fechaInicreacion);
                    Cell celda17 = row.createCell(cellnum++);
                    celda17.setCellValue(a.getTempSolicitud());
                    Cell celda18 = row.createCell(cellnum++);
                    String fechaGes = "";
                    if (a.getFechaGestion() != null) {
                        fechaGes = formato.format(a.getFechaGestion());
                    }
                    celda18.setCellValue(fechaGes);
                    Cell celda19 = row.createCell(cellnum++);
                    celda19.setCellValue(a.getTempGestion());
                    Cell celda20 = row.createCell(cellnum++);
                    celda20.setCellValue(a.getCantidadTorres());
                    Cell celda21 = row.createCell(cellnum++);
                    celda21.setCellValue(a.getNombreEstadoSolicitud());
                    Cell celda22 = row.createCell(cellnum++);
                    celda22.setCellValue(a.getOrigenSolicitud());
                    Cell celda23 = row.createCell(cellnum++);
                    celda23.setCellValue(a.getUsuarioGestionNombre() != null ? a.getUsuarioGestionNombre() : "");
                    Cell celda24 = row.createCell(cellnum++);
                    celda24.setCellValue(a.getRespuestaActual());
                    Cell celda25 = row.createCell(cellnum++);
                    celda25.setCellValue(a.getResultadoGestion());
                    //Informacion cuenta matriz 

                    //Datos Solicitante
                    Cell celda26 = row.createCell(cellnum++);
                    celda26.setCellValue(a.getNotasSolicitante() != null ? a.getNotasSolicitante() : "");
                    Cell celda27 = row.createCell(cellnum++);
                    String fechaIniGes = "";
                    if (a.getFechaInicioGestion() != null) {
                        fechaIniGes = formato.format(a.getFechaInicioGestion());
                    }
                    celda27.setCellValue(fechaIniGes);
                    Cell celda28 = row.createCell(cellnum++);
                    String fechaFincreacion = "";
                    if (a.getFechaFinCreacionSol() != null) {
                        fechaFincreacion = formato.format(a.getFechaFinCreacionSol());
                    }
                    celda28.setCellValue(fechaFincreacion);
                    Cell celda29 = row.createCell(cellnum++);
                    celda29.setCellValue(a.getCantidadHhpp());
                    Cell celda33 = row.createCell(cellnum++);
                    celda33.setCellValue(a.getContactoConstructora() != null ? a.getContactoConstructora() : "");
                    Cell celda34 = row.createCell(cellnum++);
                    celda34.setCellValue(a.getAdministracion() != null ? a.getAdministracion() : "");
                    Cell celda35 = row.createCell(cellnum++);
                    celda35.setCellValue(a.getContactoAdministracion() != null ? a.getContactoAdministracion() : "");
                    Cell celda36 = row.createCell(cellnum++);
                    celda36.setCellValue(a.getAscensores() != null ? a.getAscensores() : "");
                    Cell celda37 = row.createCell(cellnum++);
                    celda37.setCellValue(a.getContactoAscensores() != null ? a.getContactoAscensores() : "");
                    //Datos Solicitante

                    //Datos General Gestion
                    Cell celda38 = row.createCell(cellnum++);
                    celda38.setCellValue(a.getDirAntigua() != null ? a.getDirAntigua() : "");
                    Cell celda39 = row.createCell(cellnum++);
                    celda39.setCellValue(a.getTelefonoPorteria1() != null ? a.getTelefonoPorteria1() : "");
                    Cell celda40 = row.createCell(cellnum++);
                    celda40.setCellValue(a.getTelefonoPorteria2() != null ? a.getTelefonoPorteria2() : "");
                    Cell celda41 = row.createCell(cellnum++);
                    celda41.setCellValue(a.getDirTraducida() != null ? a.getDirTraducida() : "");
                    Cell celda42 = row.createCell(cellnum++);
                    celda42.setCellValue(a.getFuente() != null ? a.getFuente() : "");
                    Cell celda43 = row.createCell(cellnum++);
                    celda43.setCellValue(a.getTecnologiaSolGes() != null ? a.getTecnologiaSolGes() : "");
                    Cell celda44 = row.createCell(cellnum++);
                    celda44.setCellValue(a.getBarrioGeo() != null ? a.getBarrioGeo() : "");
                    //Datos General Gestion

                    //Datos Creacion CM
                    //Geo
                    //Se reorganiza la confiablidad y confiabilidad Placa, con Estado_dir
                    Cell celda45 = row.createCell(cellnum++);
                    celda45.setCellValue(a.getNivelSoGeo() != null ? a.getNivelSoGeo() : "");
                    Cell celda46 = row.createCell(cellnum++);
                    celda46.setCellValue(a.getCx() != null ? a.getCx() : "");
                    Cell celda47 = row.createCell(cellnum++);
                    celda47.setCellValue(a.getCy() != null ? a.getCy() : "");
                    Cell celda48 = row.createCell(cellnum++);
                    celda48.setCellValue(a.getTecnoGestionDTH()!= null ? a.getTecnoGestionDTH() : "");
                    Cell celda49 = row.createCell(cellnum++);
                    celda49.setCellValue(a.getTecnoGestionFTTH()!= null ? a.getTecnoGestionFTTH() : "");
                    Cell celda50 = row.createCell(cellnum++);
                    celda50.setCellValue(a.getTecnoGestionFOG()!= null ? a.getTecnoGestionFOG() : "");
                    Cell celda51 = row.createCell(cellnum++);
                    celda51.setCellValue(a.getTecnoGestionFOU()!= null ? a.getTecnoGestionFOU() : "");
                    Cell celda52 = row.createCell(cellnum++);
                    celda52.setCellValue(a.getTecnoGestionBID()!= null ? a.getTecnoGestionBID() : "");
                    Cell celda53 = row.createCell(cellnum++);
                    celda53.setCellValue(a.getTecnoGestionUNI()!= null ? a.getTecnoGestionUNI() : "");
                    Cell celda54 = row.createCell(cellnum++);
                    celda54.setCellValue(a.getAreaSolictud());
                    Cell celda55 = row.createCell(cellnum++);
                    String fechaEntrega = "";
                    if (a.getFechaEntrega() != null) {
                        fechaEntrega = formato.format(a.getFechaEntrega());
                    }
                    celda55.setCellValue(fechaEntrega);
                    Cell celda56 = row.createCell(cellnum++);
                    celda56.setCellValue(a.getNodoUni1() != null ? a.getNodoUni1() : "");
                    Cell celda57 = row.createCell(cellnum++);
                    celda57.setCellValue(a.getNodoUni2() != null ? a.getNodoUni2() : "");
                    Cell celda58 = row.createCell(cellnum++);
                    celda58.setCellValue(a.getNodoDth() != null ? a.getNodoDth() : "");
                    Cell celda59 = row.createCell(cellnum++);
                    celda59.setCellValue(a.getNodoMovil() != null ? a.getNodoMovil() : "");
                    Cell celda60 = row.createCell(cellnum++);
                    celda60.setCellValue(a.getNodoFtth() != null ? a.getNodoFtth() : "");
                    Cell celda61 = row.createCell(cellnum++);
                    celda61.setCellValue(a.getNodoWifi() != null ? a.getNodoWifi() : "");
                    //Geo

                    //Gestion Cm
                    Cell celda62 = row.createCell(cellnum++);
                    celda62.setCellValue(a.getTecnoGestionLTE()!= null ? a.getTecnoGestionLTE() : "");
                    Cell celda63 = row.createCell(cellnum++);
                    celda63.setCellValue(a.getTecnoGestionMOV()!= null ? a.getTecnoGestionMOV() : "");
                    Cell celda64 = row.createCell(cellnum++);
                    celda64.setCellValue(a.getModSubedificios());
                    Cell celda65 = row.createCell(cellnum++);
                    celda65.setCellValue(a.getModDireccion());
                    Cell celda66 = row.createCell(cellnum++);
                    celda66.setCellValue(a.getConfiabilidad() != null ? a.getConfiabilidad() : "");
                    Cell celda67 = row.createCell(cellnum++);
                    celda67.setCellValue(a.getConfiabilidadComplemento()!= null ? a.getConfiabilidadComplemento() : "");
                    Cell celda68 = row.createCell(cellnum++);
                    celda68.setCellValue(a.getEstado() != null ? a.getEstado() : "");
                    Cell celda69 = row.createCell(cellnum++);
                    celda69.setCellValue(a.getModDatosCm());
                    Cell celda70 = row.createCell(cellnum++);
                    celda70.setCellValue(a.getModCobertura());
                    //Gestion Cm
                    //Datos Creacion CM

                    //Datos Modificacion CM
                    Cell celda71 = row.createCell(cellnum++);
                    celda71.setCellValue(a.getDepartamentoGpo());
                    Cell celda72 = row.createCell(cellnum++);
                    celda72.setCellValue(a.getCiudad());
                    Cell celda73 = row.createCell(cellnum++);
                    celda73.setCellValue(a.getCentroPobladoGpo());
                    Cell celda74 = row.createCell(cellnum++);
                    celda74.setCellValue(a.getCuentaMatrizNombreDespues() != null ? a.getCuentaMatrizNombreDespues() : "");
                    Cell celda75 = row.createCell(cellnum++);
                    celda75.setCellValue(a.getTipoEdificioDespues() != null ? a.getTipoEdificioDespues() : "");
                    Cell celda76 = row.createCell(cellnum++);
                    celda76.setCellValue(a.getTelefonoPorteria1Despues() != null ? a.getTelefonoPorteria1Despues() : "");
                    Cell celda77 = row.createCell(cellnum++);
                    celda77.setCellValue(a.getTelefonoPorteria2Despues() != null ? a.getTelefonoPorteria2Despues() : "");
                    Cell celda78 = row.createCell(cellnum++);
                    celda78.setCellValue(a.getAdministracionDespues() != null ? a.getAdministracionDespues() : "");
                    Cell celda79 = row.createCell(cellnum++);
                    celda79.setCellValue(a.getContactoAdministracionDespues() != null ? a.getContactoAdministracionDespues() : "");
                    Cell celda80 = row.createCell(cellnum++);
                    celda80.setCellValue(a.getAscensoresDespues() != null ? a.getAscensoresDespues() : "");
                    Cell celda81 = row.createCell(cellnum++);
                    celda81.setCellValue(a.getSubedificiosAntes() != null ? a.getSubedificiosAntes() : "");
                    Cell celda82 = row.createCell(cellnum++);
                    celda82.setCellValue(a.getSubedificiosDespues() != null ? a.getSubedificiosDespues() : "");
                    Cell celda83 = row.createCell(cellnum++);
                    celda83.setCellValue(a.getDireccionCmAntes() != null ? a.getDireccionCmAntes() : "");
                    Cell celda84 = row.createCell(cellnum++);
                    celda84.setCellValue(a.getDireccionCmDespues() != null ? a.getDireccionCmDespues() : "");
                    Cell celda85 = row.createCell(cellnum++);
                    celda85.setCellValue(a.getCoberturaCmAntes() != null ? a.getCoberturaCmAntes() : "");
                    Cell celda86 = row.createCell(cellnum++);
                    celda86.setCellValue(a.getCoberturaCmDespues() != null ? a.getCoberturaCmDespues() : "");
                    Cell celda87 = row.createCell(cellnum++);
                    celda87.setCellValue(a.getResultGestionModDatosCm() != null ? a.getResultGestionModDatosCm() : "");


                    Cell celda88 = row.createCell(cellnum++);
                    celda88.setCellValue(a.getResultGestionModDir() != null ? a.getResultGestionModDir() : "");
                    Cell celda89 = row.createCell(cellnum++);
                    celda89.setCellValue(a.getResultGestionModSubEdi() != null ? a.getResultGestionModSubEdi() : "");
                    Cell celda90 = row.createCell(cellnum++);
                    celda90.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    Cell celda91 = row.createCell(cellnum++);                    
                    celda91.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    String fechaIniCob = "";
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    celda91.setCellValue(fechaIniCob);
                    Cell celda92 = row.createCell(cellnum++);
                    String fechaGesCob = "";
                    if (a.getFechaGestionCobertura() != null) {
                        fechaGesCob = formato.format(a.getFechaGestionCobertura());
                    }
                    celda92.setCellValue(fechaGesCob);
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    Cell celda93 = row.createCell(cellnum++);
                    celda93.setCellValue(a.getUsuarioGestionCobertura() != null ? a.getUsuarioGestionCobertura() : "");

                    Cell celda94 = row.createCell(cellnum++);
                    celda94.setCellValue(a.getCantidadSubedicifiosMod());
                    
                    Cell celda95 = row.createCell(cellnum++);
                    celda95.setCellValue(a.getCantidadHhppMod());

                    //Datos Modificacion CM
                    //Datos creacion/modificacion Hhpp
                    Cell celda96 = row.createCell(cellnum++);
                    celda96.setCellValue(a.getHhppCmModificados() != null ? a.getHhppCmModificados() : "");
                    Cell celda97 = row.createCell(cellnum++);
                    celda97.setCellValue(a.getCantidadHhppCre());                    
                    Cell celda98 = row.createCell(cellnum++);
                    celda98.setCellValue(a.getHhppCmCreados() != null ? a.getHhppCmCreados() : "");                    
                    Cell celda99 = row.createCell(cellnum++);
                    String fechaIniGeshh = "";
                    if (a.getFechaInicioGestionHhpp() != null) {
                        fechaIniGeshh = formato.format(a.getFechaInicioGestionHhpp());
                    }
                    celda99.setCellValue(fechaIniGeshh);

                    Cell celda100 = row.createCell(cellnum++);
                    String fechaGeshh = "";
                    if (a.getFechaGestionHhpp() != null) {
                        fechaGeshh = formato.format(a.getFechaGestionHhpp());
                    }
                    celda100.setCellValue(fechaGeshh);
                    
                    Cell celda101 = row.createCell(cellnum++);
                    celda101.setCellValue(a.getAcondicionamientoVT() != null ? a.getAcondicionamientoVT() : "");

                    //Datos Visita Tecnica
                    Cell celda102 = row.createCell(cellnum++);
                    celda102.setCellValue(a.getTipoSegmentoVT() != null ? a.getTipoSegmentoVT() : "");
                    Cell celda103 = row.createCell(cellnum++);
                    celda103.setCellValue(a.getTecnologiaVt() != null ? a.getTecnologiaVt() : "");
                    Cell celda104 = row.createCell(cellnum++);
                    String fechaPro = "";
                    if (a.getFechaProgramacionVT() != null) {
                        fechaPro = formato.format(a.getFechaProgramacionVT());
                    }
                    celda104.setCellValue(fechaPro);
                    
                    Cell celda105 = row.createCell(cellnum++);
                    celda105.setCellValue(a.getCantidadTorresVt());

                    Cell celda106 = row.createCell(cellnum++);
                    celda106.setCellValue(a.getAdministradorVT() != null ? a.getAdministradorVT() : "");
                    Cell celda107 = row.createCell(cellnum++);
                    celda107.setCellValue(a.getCelAdministradorVT() != null ? a.getCelAdministradorVT() : "");
                    Cell celda108 = row.createCell(cellnum++);                    
                    celda108.setCellValue(a.getCorreoAdministradorVT() != null ? a.getCorreoAdministradorVT() : "");
                    Cell celda109 = row.createCell(cellnum++);
                    celda109.setCellValue(a.getTelAdministradorVT() != null ? a.getTelAdministradorVT() : "");
                    Cell celda110 = row.createCell(cellnum++);
                    celda110.setCellValue(a.getNombreContactoVt() != null ? a.getNombreContactoVt() : "");
                    Cell celda111 = row.createCell(cellnum++);
                    celda111.setCellValue(a.getConstructora() != null ? a.getConstructora() : "");
                        } else {
                             //Datos De la solicitud
                    Cell celda1 = row.createCell(cellnum++);
                    celda1.setCellValue(a.getAsesor());
                    Cell celda2 = row.createCell(cellnum++);
                    celda2.setCellValue(a.getSolicitudCmId().toString());
                    Cell celda3 = row.createCell(cellnum++);
                    celda3.setCellValue(a.getCuentaMatrizId() != null ? a.getCuentaMatrizId().toString() : "");
                    Cell celda4 = row.createCell(cellnum++);
                    celda4.setCellValue(a.getCuentaMatrizIdRR() != null ? a.getCuentaMatrizIdRR().toString() : "");
                    Cell celda5 = row.createCell(cellnum++);
                    celda5.setCellValue(a.getDireccionCm());
                    //Datos De la solicitud

                    //Informacion cuenta matriz 
                    Cell celda6 = row.createCell(cellnum++);
                    celda6.setCellValue(a.getBarrio() != null ? a.getBarrio() : "");
                    Cell celda7 = row.createCell(cellnum++);
                    celda7.setCellValue(a.getCuentaMatrizNombre() != null ? a.getCuentaMatrizNombre() : "");
                    Cell celda8 = row.createCell(cellnum++);
                    celda8.setCellValue(a.getTipoEdificio() != null ? a.getTipoEdificio() : "");
                    Cell celda9 = row.createCell(cellnum++);
                    celda9.setCellValue(a.getUsuarioSolicitudNombre() != null ? a.getUsuarioSolicitudNombre() : "");
                    Cell celda10 = row.createCell(cellnum++);
                    celda10.setCellValue(a.getCorreoUsuarioSolicitante() != null ? a.getCorreoUsuarioSolicitante() : "");
                    Cell celda11 = row.createCell(cellnum++);
                    celda11.setCellValue(a.getTelefonoSolicitante());
                    Cell celda12 = row.createCell(cellnum++);
                    celda12.setCellValue(a.getCorreoAsesor());
                    Cell celda13 = row.createCell(cellnum++);
                    celda13.setCellValue(a.getCopiaA());
                    Cell celda14 = row.createCell(cellnum++);
                    celda14.setCellValue(a.getNodoBi() != null ? a.getNodoBi() : "");
                    Cell celda15 = row.createCell(cellnum++);
                    celda15.setCellValue(a.getNombreTipoSolicitud());
                    Cell celda16 = row.createCell(cellnum++);
                    String fechaInicreacion = "";
                    if (a.getFechaIniCreacionSol() != null) {
                        fechaInicreacion = formato.format(a.getFechaIniCreacionSol());
                    }
                    celda16.setCellValue(fechaInicreacion);
                    Cell celda17 = row.createCell(cellnum++);
                    celda17.setCellValue(a.getTempSolicitud());
                    Cell celda18 = row.createCell(cellnum++);
                    String fechaGes = "";
                    if (a.getFechaGestion() != null) {
                        fechaGes = formato.format(a.getFechaGestion());
                    }
                    celda18.setCellValue(fechaGes);
                    Cell celda19 = row.createCell(cellnum++);
                    celda19.setCellValue(a.getTempGestion());
                    Cell celda20 = row.createCell(cellnum++);
                    celda20.setCellValue(a.getCantidadTorres());
                    Cell celda21 = row.createCell(cellnum++);
                    celda21.setCellValue(a.getNombreEstadoSolicitud());
                    Cell celda22 = row.createCell(cellnum++);
                    celda22.setCellValue(a.getOrigenSolicitud());
                    Cell celda23 = row.createCell(cellnum++);
                    celda23.setCellValue(a.getUsuarioGestionNombre() != null ? a.getUsuarioGestionNombre() : "");
                    Cell celda24 = row.createCell(cellnum++);
                    celda24.setCellValue(a.getRespuestaActual());
                    Cell celda25 = row.createCell(cellnum++);
                    celda25.setCellValue(a.getResultadoGestion());
                    //Informacion cuenta matriz 

                    //Datos Solicitante
                    Cell celda26 = row.createCell(cellnum++);
                    celda26.setCellValue(a.getNotasSolicitante() != null ? a.getNotasSolicitante() : "");
                    Cell celda27 = row.createCell(cellnum++);
                    String fechaIniGes = "";
                    if (a.getFechaInicioGestion() != null) {
                        fechaIniGes = formato.format(a.getFechaInicioGestion());
                    }
                    celda27.setCellValue(fechaIniGes);
                    Cell celda28 = row.createCell(cellnum++);
                    String fechaFincreacion = "";
                    if (a.getFechaFinCreacionSol() != null) {
                        fechaFincreacion = formato.format(a.getFechaFinCreacionSol());
                    }
                    celda28.setCellValue(fechaFincreacion);
                    Cell celda29 = row.createCell(cellnum++);
                    celda29.setCellValue(a.getCantidadHhpp());
                    Cell celda33 = row.createCell(cellnum++);
                    celda33.setCellValue(a.getContactoConstructora() != null ? a.getContactoConstructora() : "");
                    Cell celda34 = row.createCell(cellnum++);
                    celda34.setCellValue(a.getAdministracion() != null ? a.getAdministracion() : "");
                    Cell celda35 = row.createCell(cellnum++);
                    celda35.setCellValue(a.getContactoAdministracion() != null ? a.getContactoAdministracion() : "");
                    Cell celda36 = row.createCell(cellnum++);
                    celda36.setCellValue(a.getAscensores() != null ? a.getAscensores() : "");
                    Cell celda37 = row.createCell(cellnum++);
                    celda37.setCellValue(a.getContactoAscensores() != null ? a.getContactoAscensores() : "");
                    //Datos Solicitante

                    //Datos General Gestion
                    Cell celda38 = row.createCell(cellnum++);
                    celda38.setCellValue(a.getDirAntigua() != null ? a.getDirAntigua() : "");
                    Cell celda39 = row.createCell(cellnum++);
                    celda39.setCellValue(a.getTelefonoPorteria1() != null ? a.getTelefonoPorteria1() : "");
                    Cell celda40 = row.createCell(cellnum++);
                    celda40.setCellValue(a.getTelefonoPorteria2() != null ? a.getTelefonoPorteria2() : "");
                    Cell celda41 = row.createCell(cellnum++);
                    celda41.setCellValue(a.getDirTraducida() != null ? a.getDirTraducida() : "");
                    Cell celda42 = row.createCell(cellnum++);
                    celda42.setCellValue(a.getFuente() != null ? a.getFuente() : "");
                    Cell celda43 = row.createCell(cellnum++);
                    celda43.setCellValue(a.getTecnologiaSolGes() != null ? a.getTecnologiaSolGes() : "");
                    Cell celda44 = row.createCell(cellnum++);
                    celda44.setCellValue(a.getBarrioGeo() != null ? a.getBarrioGeo() : "");
                    //Datos General Gestion

                    //Datos Creacion CM
                    //Geo
                    //Se reorganiza la confiablidad y confiabilidad Placa, con Estado_dir
                    Cell celda45 = row.createCell(cellnum++);
                    celda45.setCellValue(a.getNivelSoGeo() != null ? a.getNivelSoGeo() : "");
                    Cell celda46 = row.createCell(cellnum++);
                    celda46.setCellValue(a.getCx() != null ? a.getCx() : "");
                    Cell celda47 = row.createCell(cellnum++);
                    celda47.setCellValue(a.getCy() != null ? a.getCy() : "");
                    Cell celda48 = row.createCell(cellnum++);
                    celda48.setCellValue(a.getTecnoGestionDTH()!= null ? a.getTecnoGestionDTH() : "");
                    Cell celda49 = row.createCell(cellnum++);
                    celda49.setCellValue(a.getTecnoGestionFTTH()!= null ? a.getTecnoGestionFTTH() : "");
                    Cell celda50 = row.createCell(cellnum++);
                    celda50.setCellValue(a.getTecnoGestionFOG()!= null ? a.getTecnoGestionFOG() : "");
                    Cell celda51 = row.createCell(cellnum++);
                    celda51.setCellValue(a.getTecnoGestionFOU()!= null ? a.getTecnoGestionFOU() : "");
                    Cell celda52 = row.createCell(cellnum++);
                    celda52.setCellValue(a.getTecnoGestionBID()!= null ? a.getTecnoGestionBID() : "");
                    Cell celda53 = row.createCell(cellnum++);
                    celda53.setCellValue(a.getTecnoGestionUNI()!= null ? a.getTecnoGestionUNI() : "");
                    Cell celda54 = row.createCell(cellnum++);
                    celda54.setCellValue(a.getAreaSolictud());
                    Cell celda55 = row.createCell(cellnum++);
                    String fechaEntrega = "";
                    if (a.getFechaEntrega() != null) {
                        fechaEntrega = formato.format(a.getFechaEntrega());
                    }
                    celda55.setCellValue(fechaEntrega);
                    Cell celda56 = row.createCell(cellnum++);
                    celda56.setCellValue(a.getNodoUni1() != null ? a.getNodoUni1() : "");
                    Cell celda57 = row.createCell(cellnum++);
                    celda57.setCellValue(a.getNodoUni2() != null ? a.getNodoUni2() : "");
                    Cell celda58 = row.createCell(cellnum++);
                    celda58.setCellValue(a.getNodoDth() != null ? a.getNodoDth() : "");
                    Cell celda59 = row.createCell(cellnum++);
                    celda59.setCellValue(a.getNodoMovil() != null ? a.getNodoMovil() : "");
                    Cell celda60 = row.createCell(cellnum++);
                    celda60.setCellValue(a.getNodoFtth() != null ? a.getNodoFtth() : "");
                    Cell celda61 = row.createCell(cellnum++);
                    celda61.setCellValue(a.getNodoWifi() != null ? a.getNodoWifi() : "");
                    //Geo

                    //Gestion Cm
                    Cell celda62 = row.createCell(cellnum++);
                    celda62.setCellValue(a.getTecnoGestionLTE()!= null ? a.getTecnoGestionLTE() : "");
                    Cell celda63 = row.createCell(cellnum++);
                    celda63.setCellValue(a.getTecnoGestionMOV()!= null ? a.getTecnoGestionMOV() : "");
                    Cell celda64 = row.createCell(cellnum++);
                    celda64.setCellValue(a.getModSubedificios());
                    Cell celda65 = row.createCell(cellnum++);
                    celda65.setCellValue(a.getModDireccion());
                    Cell celda66 = row.createCell(cellnum++);
                    celda66.setCellValue(a.getConfiabilidad() != null ? a.getConfiabilidad() : "");
                    Cell celda67 = row.createCell(cellnum++);
                    celda67.setCellValue(a.getConfiabilidadComplemento()!= null ? a.getConfiabilidadComplemento() : "");
                    Cell celda68 = row.createCell(cellnum++);
                    celda68.setCellValue(a.getEstado() != null ? a.getEstado() : "");
                    Cell celda69 = row.createCell(cellnum++);
                    celda69.setCellValue(a.getModDatosCm());
                    Cell celda70 = row.createCell(cellnum++);
                    celda70.setCellValue(a.getModCobertura());
                    //Gestion Cm
                    //Datos Creacion CM

                    //Datos Modificacion CM
                    Cell celda71 = row.createCell(cellnum++);
                    celda71.setCellValue(a.getDepartamentoGpo());
                    Cell celda72 = row.createCell(cellnum++);
                    celda72.setCellValue(a.getCiudad());
                    Cell celda73 = row.createCell(cellnum++);
                    celda73.setCellValue(a.getCentroPobladoGpo());
                    Cell celda74 = row.createCell(cellnum++);
                    celda74.setCellValue(a.getCuentaMatrizNombreDespues() != null ? a.getCuentaMatrizNombreDespues() : "");
                    Cell celda75 = row.createCell(cellnum++);
                    celda75.setCellValue(a.getTipoEdificioDespues() != null ? a.getTipoEdificioDespues() : "");
                    Cell celda76 = row.createCell(cellnum++);
                    celda76.setCellValue(a.getTelefonoPorteria1Despues() != null ? a.getTelefonoPorteria1Despues() : "");
                    Cell celda77 = row.createCell(cellnum++);
                    celda77.setCellValue(a.getTelefonoPorteria2Despues() != null ? a.getTelefonoPorteria2Despues() : "");
                    Cell celda78 = row.createCell(cellnum++);
                    celda78.setCellValue(a.getAdministracionDespues() != null ? a.getAdministracionDespues() : "");
                    Cell celda79 = row.createCell(cellnum++);
                    celda79.setCellValue(a.getContactoAdministracionDespues() != null ? a.getContactoAdministracionDespues() : "");
                    Cell celda80 = row.createCell(cellnum++);
                    celda80.setCellValue(a.getAscensoresDespues() != null ? a.getAscensoresDespues() : "");
                    Cell celda81 = row.createCell(cellnum++);
                    celda81.setCellValue(a.getSubedificiosAntes() != null ? a.getSubedificiosAntes() : "");
                    Cell celda82 = row.createCell(cellnum++);
                    celda82.setCellValue(a.getSubedificiosDespues() != null ? a.getSubedificiosDespues() : "");
                    Cell celda83 = row.createCell(cellnum++);
                    celda83.setCellValue(a.getDireccionCmAntes() != null ? a.getDireccionCmAntes() : "");
                    Cell celda84 = row.createCell(cellnum++);
                    celda84.setCellValue(a.getDireccionCmDespues() != null ? a.getDireccionCmDespues() : "");
                    Cell celda85 = row.createCell(cellnum++);
                    celda85.setCellValue(a.getCoberturaCmAntes() != null ? a.getCoberturaCmAntes() : "");
                    Cell celda86 = row.createCell(cellnum++);
                    celda86.setCellValue(a.getCoberturaCmDespues() != null ? a.getCoberturaCmDespues() : "");
                    Cell celda87 = row.createCell(cellnum++);
                    celda87.setCellValue(a.getResultGestionModDatosCm() != null ? a.getResultGestionModDatosCm() : "");


                    Cell celda88 = row.createCell(cellnum++);
                    celda88.setCellValue(a.getResultGestionModDir() != null ? a.getResultGestionModDir() : "");
                    Cell celda89 = row.createCell(cellnum++);
                    celda89.setCellValue(a.getResultGestionModSubEdi() != null ? a.getResultGestionModSubEdi() : "");
                    Cell celda90 = row.createCell(cellnum++);
                    celda90.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    Cell celda91 = row.createCell(cellnum++);                    
                    celda91.setCellValue(a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "");
                    String fechaIniCob = "";
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    celda91.setCellValue(fechaIniCob);
                    Cell celda92 = row.createCell(cellnum++);
                    String fechaGesCob = "";
                    if (a.getFechaGestionCobertura() != null) {
                        fechaGesCob = formato.format(a.getFechaGestionCobertura());
                    }
                    celda92.setCellValue(fechaGesCob);
                    if (a.getFechaInicioGestionCobertura() != null) {
                        fechaIniCob = formato.format(a.getFechaInicioGestionCobertura());
                    }
                    Cell celda93 = row.createCell(cellnum++);
                    celda93.setCellValue(a.getUsuarioGestionCobertura() != null ? a.getUsuarioGestionCobertura() : "");

                    Cell celda94 = row.createCell(cellnum++);
                    celda94.setCellValue(a.getCantidadSubedicifiosMod());
                    
                    Cell celda95 = row.createCell(cellnum++);
                    celda95.setCellValue(a.getCantidadHhppMod());

                    //Datos Modificacion CM
                    //Datos creacion/modificacion Hhpp
                    Cell celda96 = row.createCell(cellnum++);
                    celda96.setCellValue(a.getHhppCmModificados() != null ? a.getHhppCmModificados() : "");
                    Cell celda97 = row.createCell(cellnum++);
                    celda97.setCellValue(a.getCantidadHhppCre());                    
                    Cell celda98 = row.createCell(cellnum++);
                    celda98.setCellValue(a.getHhppCmCreados() != null ? a.getHhppCmCreados() : "");                    
                    Cell celda99 = row.createCell(cellnum++);
                    String fechaIniGeshh = "";
                    if (a.getFechaInicioGestionHhpp() != null) {
                        fechaIniGeshh = formato.format(a.getFechaInicioGestionHhpp());
                    }
                    celda99.setCellValue(fechaIniGeshh);

                    Cell celda100 = row.createCell(cellnum++);
                    String fechaGeshh = "";
                    if (a.getFechaGestionHhpp() != null) {
                        fechaGeshh = formato.format(a.getFechaGestionHhpp());
                    }
                    celda100.setCellValue(fechaGeshh);
                    
                    Cell celda101 = row.createCell(cellnum++);
                    celda101.setCellValue(a.getAcondicionamientoVT() != null ? a.getAcondicionamientoVT() : "");

                    //Datos Visita Tecnica
                    Cell celda102 = row.createCell(cellnum++);
                    celda102.setCellValue(a.getTipoSegmentoVT() != null ? a.getTipoSegmentoVT() : "");
                    Cell celda103 = row.createCell(cellnum++);
                    celda103.setCellValue(a.getTecnologiaVt() != null ? a.getTecnologiaVt() : "");
                    Cell celda104 = row.createCell(cellnum++);
                    String fechaPro = "";
                    if (a.getFechaProgramacionVT() != null) {
                        fechaPro = formato.format(a.getFechaProgramacionVT());
                    }
                    celda104.setCellValue(fechaPro);
                    
                    Cell celda105 = row.createCell(cellnum++);
                    celda105.setCellValue(a.getCantidadTorresVt());

                    Cell celda106 = row.createCell(cellnum++);
                    celda106.setCellValue(a.getAdministradorVT() != null ? a.getAdministradorVT() : "");
                    Cell celda107 = row.createCell(cellnum++);
                    celda107.setCellValue(a.getCelAdministradorVT() != null ? a.getCelAdministradorVT() : "");
                    Cell celda108 = row.createCell(cellnum++);                    
                    celda108.setCellValue(a.getCorreoAdministradorVT() != null ? a.getCorreoAdministradorVT() : "");
                    Cell celda109 = row.createCell(cellnum++);
                    celda109.setCellValue(a.getTelAdministradorVT() != null ? a.getTelAdministradorVT() : "");
                    Cell celda110 = row.createCell(cellnum++);
                    celda110.setCellValue(a.getNombreContactoVt() != null ? a.getNombreContactoVt() : "");
                    Cell celda111 = row.createCell(cellnum++);
                    celda111.setCellValue(a.getConstructora() != null ? a.getConstructora() : "");
                        }

                        rownum++;
                    }
                    System.gc();
                }

            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");            

            response1.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_Detallado_Sol_CCMM" + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response1.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (IOException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }


    public String exportExcelGen(List<CmtReporteGeneralDto> listaGeneral)  {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("SolicitudesCM");
            String[] objArr = {"Cantidad",
                "Fecha de Creacion", "Estado", "Tipo de Solicitud", "Departamento"};
            int rownum = 0;
            if (!listaGeneral.isEmpty()) {
                for (CmtReporteGeneralDto a : listaGeneral) {
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
                        Cell cell2 = row.createCell(cellnum++);
                        cell2.setCellValue(a.getCantSol() != 0 ? a.getCantSol() : 0);
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getFechaCreacionSol());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getEstadoSol());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDescripcion());
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(a.getRegional());

                    } else {

                        Cell cell2 = row.createCell(cellnum++);
                        if (a.getCantSol() != 0) {
                            cell2.setCellValue(a.getCantSol());
                        }
                        Cell cell3 = row.createCell(cellnum++);
                        cell3.setCellValue(a.getFechaCreacionSol());
                        Cell cell4 = row.createCell(cellnum++);
                        cell4.setCellValue(a.getEstadoSol());
                        Cell cell5 = row.createCell(cellnum++);
                        cell5.setCellValue(a.getDescripcion());
                        Cell cell6 = row.createCell(cellnum++);
                        cell6.setCellValue(a.getRegional());
                    }
                    rownum++;
                }

                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.reset();
                response1.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);

                response1.setHeader("Content-Disposition", "attachment; filename=\""
                        + "Reporte_General_Sol_CCMM" + "_" + formato.format(new Date())
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
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:exportExcelGen. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:exportExcelGen. " + e.getMessage(), e, LOGGER);
        }
        return "OK";
    }

    public String downloadCvsTxtGen(List<CmtReporteGeneralDto> listaGeneral, String ext) {
        try {
            final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);
            if (!listaGeneral.isEmpty()) {
                int numeroRegistros = numRegTotal;
                byte[] csvData;
                if (numeroRegistros > 0) {

                        for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                            sb.append(NOM_COLUMNAS[j]);
                            if (j < NOM_COLUMNAS.length) {
                                sb.append(";");
                            }
                        }
                        sb.append("\n");
                        for (CmtReporteGeneralDto cmt : listaGeneral) {

                            String torres = cmt.getCantSol() == 0 ? " " : Integer.toString(cmt.getCantSol());
                            sb.append(torres);
                            sb.append(";");
                            formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);
                            String fechaGestion = cmt.getFechaCreacionSol() == null ? " " : cmt.getFechaCreacionSol();
                            sb.append(fechaGestion);
                            sb.append(";");
                            String estado = cmt.getEstadoSol() == null ? " " : cmt.getEstadoSol();
                            sb.append(estado);
                            sb.append(";");
                            String detalle = cmt.getDescripcion() == null ? " " : cmt.getDescripcion();
                            sb.append(detalle);
                            sb.append(";");
                            String regional = cmt.getRegional() == null ? " " : cmt.getRegional();
                            sb.append(regional);
                            sb.append(";");

                            sb.append("\n");
                        }
                }
                csvData = sb.toString().getBytes();
                String todayStr = formato.format(new Date());
                String fileName = "Reporte_General_Sol_CCMM" + "_" + todayStr + "." + ext;
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/force.download");
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
           FacesUtil.mostrarMensajeError("Error en ReportesSolBean:downloadCvsTxtGen. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:downloadCvsTxtGen " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public String downloadCvsTxtDet(List<CmtReporteDetalladoDto> cmtReporteDetalladoDto, String ext) {
        try {
            final StringBuffer sb = new StringBuffer();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String[] titulos = titulosReporteDetallado();

            if (reporteExcelCvsList != null) {
                int numeroRegistros = reporteExcelCvsList.size();
                byte[] csvData;
                if (numeroRegistros > 0) {
                    for (int j = 0; j < titulos.length; j++) {
                        sb.append(titulos[j]);
                        if (j < titulos.length) {
                            sb.append(";");
                        }
                    }

                    sb.append("\n");
                    for (CmtReporteDetalladoDto a : reporteExcelCvsList) {
                        // fila 1
                        
                        String celda1 = a.getAsesor();
                        sb.append(celda1);
                        sb.append(";");
                        
                        String celda2 = Integer.toString(a.getSolicitudCmId() != null ? a.getSolicitudCmId().intValueExact() : null);
                        sb.append(celda2);
                        sb.append(";");
                        
                        String celda3 = a.getCuentaMatrizId() != null ? String.valueOf(a.getCuentaMatrizId()) : "";
                        sb.append(celda3);
                        sb.append(";");
                        
                        String celda4 = a.getCuentaMatrizIdRR() != null ? String.valueOf(a.getCuentaMatrizIdRR()): "";
                        sb.append(celda4);
                        sb.append(";");
                        
                        String celda5 = a.getDireccionCm()!= null ?  a.getDireccionCm(): "";
                        sb.append(celda5);
                        sb.append(";");
                        
                        String celda6 = a.getBarrio()!= null ? a.getBarrio(): "";
                        sb.append(celda6);
                        sb.append(";");
                        
                        String celda7 = a.getCuentaMatrizNombre();
                        sb.append(celda7);
                        sb.append(";");
                        
                        String celda8 = a.getTipoEdificio();
                        sb.append(celda8);
                        sb.append(";");
                        
                        String celda9 = a.getUsuarioSolicitudNombre();
                        sb.append(celda9);
                        sb.append(";");
                        
                        String celda10 = a.getCorreoUsuarioSolicitante();
                        sb.append(celda10);
                        sb.append(";");
                        
                        String celda11 = a.getTelefonoSolicitante();
                        sb.append(celda11);
                        sb.append(";");
                        
                        String celda12 = a.getCorreoAsesor();
                        sb.append(celda12);
                        sb.append(";");
                        
                        String celda13 = a.getCopiaA();
                        sb.append(celda13);
                        sb.append(";");
                        
                        String celda14 = a.getNodoBi() != null ? a.getNodoBi() : "";
                        sb.append(celda14);
                        sb.append(";");
                        
                        String celda15 = a.getNombreTipoSolicitud();
                        sb.append(celda15);
                        sb.append(";");
                        
                        String fechaIniCreacion = "";
                        if (a.getFechaIniCreacionSol() != null) {
                            fechaIniCreacion = formato.format(a.getFechaIniCreacionSol());
                        }
                        String celda16 = fechaIniCreacion;
                        sb.append(celda16);
                        sb.append(";");
                        
                        String celda17 = a.getTempSolicitud();
                        sb.append(celda17);
                        sb.append(";");
                        
                        String celda18 = "";
                        if (a.getFechaGestion() != null) {
                            celda18 = formato.format(a.getFechaGestion());
                        }
                        sb.append(celda18);
                        sb.append(";");
                        
                        String celda19 = a.getTempGestion();
                        sb.append(celda19);
                        sb.append(";");
                        
                        String celda20 = String.valueOf(a.getCantidadTorres());
                        sb.append(celda20);
                        sb.append(";");
                        
                        String celda21 = String.valueOf(a.getNombreEstadoSolicitud());
                        sb.append(celda21);
                        sb.append(";");
                        
                        String celda22 = a.getOrigenSolicitud();
                        sb.append(celda22);
                        sb.append(";");
                        
                        String celda23 = a.getUsuarioGestionNombre() != null ? a.getUsuarioGestionNombre() : "";
                        sb.append(celda23);
                        sb.append(";");
                        
                        String celda24 = a.getRespuestaActual();
                        sb.append(celda24);
                        sb.append(";");
                        
                        String celda25 = a.getResultadoGestion();
                        sb.append(celda25);
                        sb.append(";");
                        
                        String celda26 = a.getNotasSolicitante() != null ? a.getNotasSolicitante() : "";
                        sb.append(celda26);
                        sb.append(";");
                        
                        String celda27 = "";
                        if (a.getFechaInicioGestion() != null) {
                            celda27 = formato.format(a.getFechaInicioGestion());
                        }
                        sb.append(celda27);
                        sb.append(";");
                        
                        String fechaFinCreacion = "";
                        if (a.getFechaFinCreacionSol() != null) {
                            fechaFinCreacion = formato.format(a.getFechaFinCreacionSol());
                        }
                        String celda28 = fechaFinCreacion;
                        sb.append(celda28);
                        sb.append(";");
                        
                        String celda29 = String.valueOf(a.getCantidadHhpp());
                        sb.append(celda29);
                        sb.append(";");
                        
                        String celda30 = a.getContactoConstructora() != null ? a.getContactoConstructora() : "";
                        sb.append(celda30);
                        sb.append(";");
                        
                        String celda31 = a.getAdministracion() != null ? a.getAdministracion() : "";
                        sb.append(celda31);
                        sb.append(";");
                        
                        String celda32 = a.getContactoAdministracion() != null ? a.getContactoAdministracion() : "";
                        sb.append(celda32);
                        sb.append(";");
                        
                        String celda33 = a.getAscensores() != null ? a.getAscensores() : "";
                        sb.append(celda33);
                        sb.append(";");
                        
                        String celda34 = a.getContactoAscensores() != null ? a.getContactoAscensores() : "";
                        sb.append(celda34);
                        sb.append(";");
                        
                        String celda35 = a.getDirAntigua() != null ? a.getDirAntigua() : "";
                        sb.append(celda35);
                        sb.append(";");
                        
                        String celda36 = a.getTelefonoPorteria1() != null ? a.getTelefonoPorteria1() : "";
                        sb.append(celda36);
                        sb.append(";");
                        
                        String celda37 = a.getTelefonoPorteria2() != null ? a.getTelefonoPorteria2() : "";
                        sb.append(celda37);
                        sb.append(";");
                        
                        String celda38 = a.getDirTraducida() != null ? a.getDirTraducida() : "";
                        sb.append(celda38);
                        sb.append(";");
                        
                        String celda39 = a.getFuente() != null ? a.getFuente() : "";
                        sb.append(celda39);
                        sb.append(";");
                        
                        String celda40 = a.getTecnologiaSolGes() != null ? a.getTecnologiaSolGes() : "";
                        sb.append(celda40);
                        sb.append(";");
                        
                        String celda41 = a.getBarrioGeo() != null ? a.getBarrioGeo() : "";
                        sb.append(celda41);
                        sb.append(";");
                        
                        String celda42 = a.getNivelSoGeo() != null ? a.getNivelSoGeo() : "";
                        sb.append(celda42);
                        sb.append(";");
                        
                        String celda43 = a.getCx() != null ? a.getCx() : "";
                        sb.append(celda43);
                        sb.append(";");
                        
                        String celda44 = a.getCy() != null ? a.getCy() : "";
                        sb.append(celda44);
                        sb.append(";");
                        
                        String celda45 = a.getTecnoGestionDTH() != null ? a.getTecnoGestionDTH() : "";
                        sb.append(celda45);
                        sb.append(";");
                        
                        String celda46 = a.getTecnoGestionFTTH() != null ? a.getTecnoGestionFTTH() : "";
                        sb.append(celda46);
                        sb.append(";");
                        
                        String celda47 = a.getTecnoGestionFOG()!= null ? a.getTecnoGestionFOG() : "";
                        sb.append(celda47);
                        sb.append(";");
                        
                        String celda48 = a.getTecnoGestionFOU() != null ? a.getTecnoGestionFOU() : "";
                        sb.append(celda48);
                        sb.append(";");
                        
                        String celda49 = a.getTecnoGestionBID() != null ? a.getTecnoGestionBID() : "";
                        sb.append(celda49);
                        sb.append(";");
                        
                        String celda50 = a.getTecnoGestionUNI() != null ? a.getTecnoGestionUNI() : "";
                        sb.append(celda50);
                        sb.append(";");
                        
                        String celda51 = a.getAreaSolictud();
                        sb.append(celda51);
                        sb.append(";");
                        
                        String celda52 = "";
                        if (a.getFechaEntrega() != null) {
                            celda52 = formato.format(a.getFechaEntrega());
                        }
                        sb.append(celda52);
                        sb.append(";");
                        
                        String celda53 = a.getNodoUni1() != null ? a.getNodoUni1() : "";
                        sb.append(celda53);
                        sb.append(";");
                        
                        String celda54 = a.getNodoUni2() != null ? a.getNodoUni2() : "";
                        sb.append(celda54);
                        sb.append(";");
                        
                        String celda55 = a.getNodoDth() != null ? a.getNodoDth() : "";
                        sb.append(celda55);
                        sb.append(";");
                        
                        String celda56 = a.getNodoMovil() != null ? a.getNodoMovil() : "";
                        sb.append(celda56);
                        sb.append(";");
                        
                        String celda57 = a.getNodoFtth() != null ? a.getNodoFtth() : "";
                        sb.append(celda57);
                        sb.append(";");
                        
                        String celda58 = a.getNodoWifi() != null ? a.getNodoWifi() : "";
                        sb.append(celda58);
                        sb.append(";");
                        
                        String celda59 = a.getTecnoGestionLTE() != null ? a.getTecnoGestionLTE() : "";
                        sb.append(celda59);
                        sb.append(";");
                        
                        String celda60 = a.getTecnoGestionMOV() != null ? a.getTecnoGestionMOV() : "";
                        sb.append(celda60);
                        sb.append(";");
                        
                        Short celda61 = a.getModSubedificios();
                        sb.append(celda61);
                        sb.append(";");
                        
                        Short celda62 = a.getModDireccion();
                        sb.append(celda62);
                        sb.append(";");
                        
                        String celda63 = a.getConfiabilidad() != null ? a.getConfiabilidad() : "";
                        sb.append(celda63);
                        sb.append(";");
                        
                        String celda64 = a.getConfiabilidadComplemento() != null ? a.getConfiabilidadComplemento() : "";
                        sb.append(celda64);
                        sb.append(";");
                        
                        String celda65 = a.getEstado() != null ? a.getEstado() : "";
                        sb.append(celda65);
                        sb.append(";");
                        
                        Short celda66 = a.getModDatosCm();
                        sb.append(celda66);
                        sb.append(";");
                        
                        Short celda67 = a.getModCobertura();
                        sb.append(celda67);
                        sb.append(";");
                        
                        String celda68 = a.getDepartamentoGpo();
                        sb.append(celda68);
                        sb.append(";");
                        
                        String celda69 = a.getCiudad();
                        sb.append(celda69);
                        sb.append(";");
                        
                        String celda70 = a.getCentroPobladoGpo();
                        sb.append(celda70);
                        sb.append(";");
                        
                        String celda71 = a.getCuentaMatrizNombreDespues() != null ? a.getCuentaMatrizNombreDespues() : "";
                        sb.append(celda71);
                        sb.append(";");
                        
                        String celda72 = a.getTipoEdificioDespues() != null ? a.getTipoEdificioDespues() : "";
                        sb.append(celda72);
                        sb.append(";");
                        
                        String celda73 = a.getTelefonoPorteria1Despues() != null ? a.getTelefonoPorteria1Despues() : "";
                        sb.append(celda73);
                        sb.append(";");
                        
                        String celda74 = a.getTelefonoPorteria2Despues() != null ? a.getTelefonoPorteria2Despues() : "";
                        sb.append(celda74);
                        sb.append(";");
                        
                        String celda75 = a.getAdministracionDespues() != null ? a.getAdministracionDespues() : "";
                        sb.append(celda75);
                        sb.append(";");
                        
                        String celda76 = a.getContactoAdministracionDespues() != null ? a.getContactoAdministracionDespues() : "";
                        sb.append(celda76);
                        sb.append(";");
                        
                        String celda77 = a.getAscensoresDespues() != null ? a.getAscensoresDespues() : "";
                        sb.append(celda77);
                        sb.append(";");
                        
                        String celda78 = a.getSubedificiosAntes() != null ? a.getSubedificiosAntes() : "";
                        sb.append(celda78);
                        sb.append(";");
                        
                        String celda79 = a.getSubedificiosDespues() != null ? a.getSubedificiosDespues() : "";
                        sb.append(celda79);
                        sb.append(";");
                        
                        String celda80 = a.getDireccionCmAntes() != null ? a.getDireccionCmAntes() : "";
                        sb.append(celda80);
                        sb.append(";");
                        
                        String celda81 = a.getDireccionCmDespues() != null ? a.getDireccionCmDespues() : "";
                        sb.append(celda81);
                        sb.append(";");
                        
                        String celda82 = a.getCoberturaCmAntes() != null ? a.getCoberturaCmAntes() : "";
                        sb.append(celda82);
                        sb.append(";");
                        
                        String celda83 = a.getCoberturaCmDespues() != null ? a.getCoberturaCmDespues() : "";
                        sb.append(celda83);
                        sb.append(";");
                        
                        String celda84 = a.getResultGestionModDatosCm() != null ? a.getResultGestionModDatosCm() : "";
                        sb.append(celda84);
                        sb.append(";");
                        
                        String celda85 = a.getResultGestionModDir() != null ? a.getResultGestionModDir() : "";;
                        sb.append(celda85);
                        sb.append(";");
                        
                        String celda86 = a.getResultGestionModSubEdi() != null ? a.getResultGestionModSubEdi() : "";
                        sb.append(celda86);
                        sb.append(";");
                        
                        String celda87 = a.getResultGestionModCobertura() != null ? a.getResultGestionModCobertura() : "";
                        sb.append(celda87);
                        sb.append(";");
                        
                        String celda88 = "";
                        if (a.getFechaInicioGestionCobertura() != null) {
                            celda88 = formato.format(a.getFechaInicioGestionCobertura());
                        }
                        sb.append(celda88);
                        sb.append(";");

                        String celda89 = "";
                        if (a.getFechaGestionCobertura() != null) {
                            celda89 = formato.format(a.getFechaGestionCobertura());
                        }
                        sb.append(celda89);
                        sb.append(";");
                        
                        String celda90 = a.getUsuarioGestionCobertura() != null ? a.getUsuarioGestionCobertura() : "";
                        sb.append(celda90);
                        sb.append(";");
                        
                        String celda91 = String.valueOf(a.getCantidadSubedicifiosMod());
                        sb.append(celda91);
                        sb.append(";");
                        
                        //Datos creacion/modificacion Hhpp
                        String celda92 = String.valueOf(a.getCantidadHhppMod());
                        sb.append(celda92);
                        sb.append(";");
                        
                        String celda93 = a.getHhppCmModificados() != null ? a.getHhppCmModificados() : "";
                        sb.append(celda93);
                        sb.append(";");
                        
                        String celda94 = String.valueOf(a.getCantidadHhppCre());
                        sb.append(celda94);
                        sb.append(";");
                        
                        String celda95 = a.getHhppCmCreados() != null ? a.getHhppCmCreados() : "";
                        sb.append(celda95);
                        sb.append(";");
                        
                        String celda96 = "";
                        if (a.getFechaInicioGestionHhpp() != null) {
                            celda96 = formato.format(a.getFechaInicioGestionHhpp());
                        }
                        sb.append(celda96);
                        sb.append(";");

                        String celda97 = "";
                        if (a.getFechaGestionHhpp() != null) {
                            celda97 = formato.format(a.getFechaGestionHhpp());
                        }
                        sb.append(celda97);
                        sb.append(";");
                        

                        //Datos Visita Tecnica
                        String celda98 = a.getAcondicionamientoVT() != null ? a.getAcondicionamientoVT() : "";
                        sb.append(celda98);
                        sb.append(";");
                        
                        String celda99 = a.getTipoSegmentoVT() != null ? a.getTipoSegmentoVT() : "";
                        sb.append(celda99);
                        sb.append(";");
                        
                        String celda100 = a.getTecnologiaVt() != null ? a.getTecnologiaVt() : "";
                        sb.append(celda100);
                        sb.append(";");

                        String celda101 = "";
                        if (a.getFechaProgramacionVT() != null) {
                            celda101 = formato.format(a.getFechaProgramacionVT());
                        }
                        sb.append(celda101);
                        sb.append(";");
                        
                        String celda102 = String.valueOf(a.getCantidadTorresVt());;
                        sb.append(celda102);
                        sb.append(";");
                        
                        String celda103 = a.getAdministradorVT() != null ? a.getAdministradorVT() : "";
                        sb.append(celda103);
                        sb.append(";");
                        
                        String celda104 = a.getCelAdministradorVT() != null ? a.getCelAdministradorVT() : "";
                        sb.append(celda104);
                        sb.append(";");
                        
                        String celda105 = a.getCorreoAdministradorVT() != null ? a.getCorreoAdministradorVT() : "";
                        sb.append(celda105);
                        sb.append(";");
                        
                        String celda106 = a.getTelAdministradorVT() != null ? a.getTelAdministradorVT() : "";
                        sb.append(celda106);
                        sb.append(";");
                        
                        String celda107 = a.getNombreContactoVt() != null ? a.getNombreContactoVt() : "";
                        sb.append(celda107);
                        sb.append(";");
                        
                        String celda108 = a.getConstructora() != null ? a.getConstructora() : "";
                        sb.append(celda108);
                        sb.append(";");
                        
                        sb.append("\n");

                    }
                }
                csvData = sb.toString().getBytes("UTF-8");
                String todayStr = formato.format(new Date());
                String fileName = "Reporte_Detallado_Sol_CCMM" + "_" + todayStr + "." + ext;

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
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:downloadCvsTxtDet. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:downloadCvsTxtDet. " + e.getMessage(), e, LOGGER);
        }
        return "case1";
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:irPagina(). " + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        if (pintarPaginado) {
           int count = listCmtReporteGeneralDtoAux.size();
            int totalPaginas = (count % filasPag != 0)
                    ? (count / filasPag) + 1
                    : count / filasPag;

            return totalPaginas;
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
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public boolean validarFechas() {
           boolean respuesta = false;
        try {
         
            if (validarFormatoFecha(fechaInicio) && validarFormatoFecha(fechaFinal)) {
                if (fechaInicio.before(fechaFinal)) {
                    System.err.println("Fecha Inicio  es menor ");
                    respuesta = true;
                } else {
                    respuesta = !(fechaFinal.before(fechaInicio));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Formato de Fechas no valido", ""));
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:validarFechas(). " + e.getMessage(), e, LOGGER);
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
            String fechaInicial;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            fechaInicial = format.format(fecha);
            format.setLenient(false);
            format.parse(fechaInicial);

        } catch (ParseException e) {
          FacesUtil.mostrarMensajeError("Error en ReportesSolBean:validarFormatoFecha. " + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:validarFormatoFecha. " + e.getMessage(), e, LOGGER);
        }
        return true;
    }
    
    public String[] titulosReporteDetallado() {
        
        String[] titulos = {"ASESOR","SOLICITUD_ID","NUMERO_CUENTA_MGL","NUMERO_CUENTA_RR","DIRECCION_CM",
            "BARRIO_CM","NOMBRE_CM","TIPO_EDIFICIO","USUARIO_SOLICITANTE","CORREO_USUARIO_SOLICITANTE",
            "TELEFONO_SOLICITANTE","CORREO_ASESOR","CORREO_COPIA_A","NODO_BI","TIPO_SOLICITUD",
            "FECHA_INI_CREACION","TIEMPO_SOLICITUD","FECHA_FIN_GESTION","TIEMPO_GESTION","CANTIDAD_TORRES_CM",
            "ESTADO_SOLICITUD","ORIGEN_SOLICITUD","USUARIO_GESTIONADOR","RESPUESTA_ACTUAL",
            "RESULTADO_GESTION","NOTAS_USUARIO_SOLICITA","FECHA_INICIO_GESTION","FECHA_FIN_CREACION",
            "CANTIDAD_HHPP_CM","CONTACTO_CONSTRUCTORA_CM","ADMINISTRACION_CM","CONTACTO_ADMINISTRACION_CM",
            "ASCENSORES_CM","CONTACTO_ASCENSORES_CM","DIRECCION_ANTIGUA","TELEFONO_PORTERIA_1",
            "TELEFONO_PORTERIA_2","DIRECCION_TRADUCCIDA","FUENTE","TECNOLOGIA_SOLICITUD_GESTION",
            "BARRIO_GEOREFERENCIADO","NIVEL_SOCIECONOMICO_GEO","COORDENADA_X","COORDENADA_Y",
            "DTH","FIBRA_FTTH","FIBRA_OPTICA_GPON","FIBRA_OPTICA_UNIFILAR","HFC_BIDIRECCIONAL",
            "HFC_UNIDIRECCIONAL","AREA_SOLICITUD","FECHA_ENTREGA_CM","NODO_UNI_1",
            "NODO_UNI_2","NODO_DTH","NODO_MOVIL","NODO_FTTH","NODO_WIFI","LTE","MOVIL",
            "MODIFICO_SUBEDIFICIO","MODIFICO_DIRECCION","CONFIABILIDAD_DIRECCION","CONFIABILIDAD_COMPLEMENTO",
            "ESTADO_DIR","MODIFICO_DATOS_CM","MODIFICO_COBERTURA",
            
            "DEPARTAMENTO_CM","CIUDAD_CM","CENTRO_POBLADO_CM","NOMBRE_CM_DESPUES","TIPO_EDIFICIO_DESPUES",
            "TELEFONO_PORTERIA_1_DESPUES","TELEFONO_PORTERIA_2_DESPUES","ADMINISTRACION_CM_DESPUES",
            "CONTACTO_ADMINISTRACION_CM_DESPUES","ASCENSORES_CM_DESPUES","SUBEDIFICIOS_ANTES",
            "SUBEDIFICIOS_DESPUES","DIRECCION_CM_ANTES","DIRECCION_CM_DESPUES",
            
            "COBERTURA_CM_ANTES","COBERTURA_CM_DESPUES","RESULTADO_GES_MOD_DATOS_CM","RESULTADO_GES_MOD_DIR",
            "RESULTADO_GES_MOD_SUB","RESULTADO_GES_MOD_COB","FECHA_INI_GESTION_COB","FECHA_GESTION_COB",
            "USUARIO_GESTION_COB","CANTIDAD_SUBEDIFICIOS_MOD","CANTIDAD_HHPP_MOD","HHPP_CM_MODIFICADOS",
            "CANTIDAD_HHPP_CREADOS","HHPP_CM_CREADOS","FECHA_INI_GESTION_HHPP","FECHA_GESTION_HHPP",
            "ACONDICIONAMIENTO_VT","TIPO_SEGMENTO_VT","TECNOLOGIA_VT","FECHA_PROGRAMACION_VT",
            "CANTIDAD_TORRES_VT","ADMINISTRADOR_VT","CELULAR_ADMINISTRADOR_VT","CORREO_ADMINISTRADOR_VT",
            "TELEFONO_ADMINISTRADOR_VT","NOMBRE_CONTACTO_VT","CONSTRUCTORA_CM"};

        return titulos;
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
                numero = Integer.parseInt(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:tiempoReporte. " + e.getMessage(), e, LOGGER);
            return DEFAULT;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReportesSolBean:tiempoReporte. " + e.getMessage(), e, LOGGER);
        }
        return numero;
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

    public List<CmtTipoSolicitudMgl> getListCmtTipoSolicitudMgl() {
        return listCmtTipoSolicitudMgl;
    }

    public void setListCmtTipoSolicitudMgl(List<CmtTipoSolicitudMgl> listCmtTipoSolicitudMgl) {
        this.listCmtTipoSolicitudMgl = listCmtTipoSolicitudMgl;
    }

    public BigDecimal getTipoSolicitudSelected() {
        return tipoSolicitudSelected;
    }

    public void setTipoSolicitudSelected(BigDecimal tipoSolicitudSelected) {
        this.tipoSolicitudSelected = tipoSolicitudSelected;
    }

    public String getTipoReporteSelected() {
        return tipoReporteSelected;
    }

    public void setTipoReporteSelected(String tipoReporteSelected) {
        this.tipoReporteSelected = tipoReporteSelected;
    }

    public BigDecimal getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(BigDecimal estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<CmtBasicaMgl> getListCmtSolicitudCmMgl() {
        return listCmtSolicitudCmMgl;
    }

    public void setListCmtSolicitudCmMgl(List<CmtBasicaMgl> listCmtSolicitudCmMgl) {
        this.listCmtSolicitudCmMgl = listCmtSolicitudCmMgl;
    }

    public CmtSolicitudCmMgl getCmtSolicitudCmMgl() {
        return cmtSolicitudCmMgl;
    }

    public void setCmtSolicitudCmMgl(CmtSolicitudCmMgl cmtSolicitudCmMgl) {
        this.cmtSolicitudCmMgl = cmtSolicitudCmMgl;
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

    public FiltroReporteSolicitudCMDto getFiltroReporteSolicitudCMDto() {
        return filtroReporteSolicitudCMDto;
    }

    public void setFiltroReporteSolicitudCMDto(FiltroReporteSolicitudCMDto filtroReporteSolicitudCMDto) {
        this.filtroReporteSolicitudCMDto = filtroReporteSolicitudCMDto;
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

    public int getNumRegTotal() {
        return numRegTotal;
    }

    public void setNumRegTotal(int numRegTotal) {
        this.numRegTotal = numRegTotal;
    }

    public List<CmtReporteGeneralDto> getListCmtReporteGeneralDto() {
        return listCmtReporteGeneralDto;
    }

    public void setListCmtReporteGeneralDto(List<CmtReporteGeneralDto> listCmtReporteGeneralDto) {
        this.listCmtReporteGeneralDto = listCmtReporteGeneralDto;
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

    public List<CmtReporteDetalladoDto> getListCmtReporteDetalladoDto() {
        return listCmtReporteDetalladoDto;
    }

    public void setListCmtReporteDetalladoDto(List<CmtReporteDetalladoDto> listCmtReporteDetalladoDto) {
        this.listCmtReporteDetalladoDto = listCmtReporteDetalladoDto;
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

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public List<CmtReporteDetalladoDto> getReporteExcelCvsList() {
        return reporteExcelCvsList;
    }

    public void setReporteExcelCvsList(List<CmtReporteDetalladoDto> reporteExcelCvsList) {
        this.reporteExcelCvsList = reporteExcelCvsList;
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

    public boolean isPanelPintarPaginado() {
        return panelPintarPaginado;
    }

    public void setPanelPintarPaginado(boolean panelPintarPaginado) {
        this.panelPintarPaginado = panelPintarPaginado;
    }
    
    // Validar Opciones por Rol 
    public boolean validarReporteSolCm() {
        return validarEdicionRol(XLSBNRCMM);
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
