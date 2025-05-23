/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;
import co.com.claro.mer.email.domain.models.enums.EmailTemplatesHtmlEnum;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoArchivo;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoReporte;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.PaginationList;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static co.com.claro.mgl.mbeans.vt.migracion.beans.enums.EstadoSolicitud.*;

/**
 * Controlar la generación de reportes
 * <p>
 * Clase para controlar la creación de un reporte general o detallado
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
@ManagedBean(name = "reporteBean")
@ViewScoped
@Log4j2
public class ReporteBean implements Serializable {
    
    /**
     * Para saber el tipo de reporte seleccionado
     */
    protected TipoReporte tipoReporteSeleccionado;
    /**
     * Para saber el tipo de solicitud seleccionada Muestra la idTipoSolicitud
     */
    protected String tipoSolicitud;
    /**
     * Fecha inicial para la solicitud del reporte
     */
    protected Date fechaInicial;
    /**
     * Fecha Final para la solicitud del reporte
     */
    protected Date fechaFinalizacion;
    /**
     * Estado con el cual se solicitará el reporte
     */
    protected String estado;
    /**
     * Tipo de archivos para exportar
     */
    private TipoArchivo tipoArchivo;
    /**
     * Muestra el estado si puede exportar o no.
     */
    private boolean puedeExportar = false;
    private PaginationList paginationReporteGeneral;
    private boolean habilitarExcel = false;
    private boolean habilitarCsv = false;
    private boolean habilitarTxt = false;
    
    private int countRegistros;
    private List<Object[]> listReporteGeneral;
    private static final String[] NOM_COLUMNAS_DETALLADO = {"USLOGUEO", "IDSOLICITUD",
            "CUENTAMATRIZ", "CUENTASUSCRIPTOR",
            "CALLE", "PLACA",
            "TIPOVIVIENDA", "COMPLEMENTO",
            "SOLICITANTE", "CONTACTO",
            "TELCONTACTO", "REGIONAL",
            "CIUDAD", "NODO",
            "TIPOSOL", "FECHAINGRESO",
            "FECHA_MODIFICACION", "FECHA_FINALIZACION",
            "TIEMPO", "MOTIVO",
            "ESTADO", "AREA",
            "USUARIO_GESTIÓN", "USUARIO_VERIFICADOR",
            "RGESTION", "RESPUESTA",
            "CORREGIR_HHPPP", "CAMBIO_NODO",
            "NOMBRE_NUEVOEDIFICIO", "NUEVO_PRODUCTO",
            "ESTRATO_ANTIGUO", "ESTRATO_NUEVO",
            "BARRIO", "HHP_ID",
            "DIRECCION_NO_ESTANDARIZADA",
            "NOD_NOMBRE", "TIPO_UNIDAD", "SDI_FORMATO_IGAC",
            "TIPO_CONEXION", "TIPO_RED",
            "HHP_FECHA_CREACION", "HHP_USUARIO_CREACION",
            "HHP_FECHA_MODIFICACION", "HHP_USUARIO_MODIFICACION",
            "ESTADO_HHPP", "HHP_ID_RR",
            "HHP_CALLE", "HHP_PLACA",
            "HHP_APART", "HHP_COMUNIDAD",
            "HHP_DIVISION", "HHP_ESTADO_UNIT",
            "HHP_VENDEDOR", "HHP_CODIGO_POSTAL",
            "HHP_EDIFICIO", "HHP_TIPO_ACOMET",
            "HHP_ULT_UBICACION", "HHP_HEAD_END",
            "HHP_TIPO", "HHP_TIPO_UNIDAD",
            "HHP_TIPO_CBL_ACOMETIDA", "HHP_FECHA_AUDIT",
            "CONFIABILIDAD_DIRECCION",
            "CONFIABILIDAD_COMPLEMENTO", "ESTADO_DIR",
            "NOTAADD", "DIRECCION", "DEPARTAMENTO",
            "CIUDAD","CENTRO_POBLADO","TIPO_DOCUMENTO","NUMERO_DOCUMENTO",
            "TIPO", "#_CCMM_CTA_SUC",
            "#_SOLICITUD_OT", "TIPO_GESTION"};
    
    private static final String[] NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA = {"USLOGUEO", "IDSOLICITUD",
            "CUENTAMATRIZ", "CUENTASUSCRIPTOR",
            "CALLE", "PLACA",
            "TIPOVIVIENDA", "COMPLEMENTO",
            "SOLICITANTE", "CONTACTO",
            "TELCONTACTO", "REGIONAL",
            "CIUDAD", "NODO",
            "TIPOSOL", "FECHAINGRESO",
            "FECHA_MODIFICACION", "FECHA_FINALIZACION",
            "TIEMPO", "MOTIVO",
            "ESTADO", "AREA",
            "USUARIO_GESTIÓN", "USUARIO_VERIFICADOR",
            "RGESTION", "RESPUESTA",
            "CORREGIR_HHPPP", "CAMBIO_NODO",
            "NOMBRE_NUEVOEDIFICIO", "NUEVO_PRODUCTO",
            "ESTRATO_ANTIGUO", "ESTRATO_NUEVO",
            "BARRIO", "HHP_ID",
            "DIRECCION_NO_ESTANDARIZADA",
            "NOD_NOMBRE", "TIPO_UNIDAD", "SDI_FORMATO_IGAC",
            "TIPO_CONEXION", "TIPO_RED",
            "HHP_FECHA_CREACION", "HHP_USUARIO_CREACION",
            "HHP_FECHA_MODIFICACION", "HHP_USUARIO_MODIFICACION",
            "ESTADO_HHPP", "HHP_ID_RR",
            "HHP_CALLE", "HHP_PLACA",
            "HHP_APART", "HHP_COMUNIDAD",
            "HHP_DIVISION", "HHP_ESTADO_UNIT",
            "HHP_VENDEDOR", "HHP_CODIGO_POSTAL",
            "HHP_EDIFICIO", "HHP_TIPO_ACOMET",
            "HHP_ULT_UBICACION", "HHP_HEAD_END",
            "HHP_TIPO", "HHP_TIPO_UNIDAD",
            "HHP_TIPO_CBL_ACOMETIDA", "HHP_FECHA_AUDIT",
            "CONFIABILIDAD_DIRECCION",
            "CONFIABILIDAD_COMPLEMENTO", "ESTADO_DIR",
            "NOTAADD", "DIRECCION", "DEPARTAMENTO",
            "CIUDAD","CENTRO_POBLADO","TIPO_DOCUMENTO","NUMERO_DOCUMENTO",
            "TIPO", "#_CCMM_CTA_SUC",
            "#_SOLICITUD_OT", "TIPO_GESTION",
            "TIPO_SOLICITUD","TIPO_SERVICIO","TIPO_VALIDACION","BW","TIPO_SITIO"};
    

     private static final String[] NOM_COLUMNAS_GENERAL = 
     {"Cantidad", "Fecha de Ingreso", "Estado", "Descripción", "Regional"};
     protected String estadoRepGral;

    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    /**
     * Control para las solicitudes a la base de datos de la entidad
     * CmtTipoSolicitudMgl
     */
    @EJB
    protected CmtTipoSolicitudMglFacadeLocal ejbCmtTipoSolicitudMgl;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    private SecurityLogin securityLogin;
    private String usuarioVT = null;

    private Map<String, TipoReporte> tipoReportesMap;
    private Map<String, String> tipoSolicitudesMap;
    private String opcionPantalla;
    private boolean validarOpcionGenerarReporte;
    private String estiloObligatorio = "<font color='red'>*</font>";
    //Opciones agregadas para Rol
    private final String BTNGNRRPT = "BTNGNRRPT";
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    
    /**
     * Creates a new instance of ReporteBean
     */
    public ReporteBean() {
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try{
            opcionPantalla = "C";
            tipoReportesMap = JsfUtil.getTipoReportes();
            tipoSolicitudesMap = JsfUtil.getCmtTipoSolicitudMgl();
            validarOpcionGenerarReporte = validarOpcionGenerarReporte();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        }catch (Exception e){
            FacesUtil.mostrarMensajeError("Ocurrió un error consultando realizando las validaciones iniciales.", e, LOGGER);
        }
    }
   
    /**
     * Buscar el valor del atributo tipoReporteSeleccionado.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public TipoReporte getTipoReporteSeleccionado() {
        return tipoReporteSeleccionado;
    }

    /**
     * Actualizar el valor del atributo tipoReporteSeleccionado.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param tipoReporteSeleccionado valor actualizar
     */
    public void setTipoReporteSeleccionado(
            TipoReporte tipoReporteSeleccionado) {
        this.tipoReporteSeleccionado = tipoReporteSeleccionado;
    }

    /**
     * Buscar los tipos de reportes que se pueden realizar
     * <p>
     * Muestra el map con el valor para mostrar y el objeto que lo respalda
     *
     * @author becerraarmr
     *
     * @return el Map correspondiente
     */
    public Map<String, TipoReporte> getTipoReportes() {
        return JsfUtil.getTipoReportes();
    }

    /**
     * Buscar los tipos de solicitudes que se pueden realizar
     * <p>
     * Muestra el map con el valor para mostrar y el objeto que lo respalda
     *
     * @author becerraarmr
     *
     * @return el Map correspondiente
     */
    public Map<String, String> getTipoSolicitudes() {
        return JsfUtil.getCmtTipoSolicitudMgl();
    }

    /**
     * Buscar una instancia CmtTipoSolicitudMgl.
     * <p>
     * Solicita a la base de datos si existe un registro con el id
     * correspondiente
     *
     * @author becerraarmr
     * @param tipoSolicitudId valor que representa la llave primaria
     *
     * @return null si no encuentra la instancia, de lo contrario devuelve la
     * instancia correspondiente
     */
    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl(BigDecimal tipoSolicitudId) {
        return ejbCmtTipoSolicitudMgl.find(tipoSolicitudId);
    }

    /**
     * Buscar el valor del atributo fechaInicial.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Actualizar el valor del atributo fechaInicial.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param dateNuevo valor actualizar
     */
    public void setFechaInicial(Date dateNuevo) {
        this.fechaInicial = dateNuevo;
    }

    /**
     * Buscar el valor del atributo fechaFinalizacion.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Actualizar el valor del atributo fechaFinalizacion.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param dateNuevo valor actualizar
     */
    public void setFechaFinalizacion(Date dateNuevo) {
        this.fechaFinalizacion = dateNuevo;
    }

    /**
     * Buscar el valor del atributo estado.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Actualizar el valor del atributo estado.
     * <p>
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param estado valor actualizar
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Buscar el valor del atributo tipoSolicitud.
     * <p>
     * Busca el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * Actualizar el valor del atributo .
     * <p>
     * Actualiza el valor del atributo tipoSolicitud.
     *
     * @author becerraarmr
     *
     * @param tipoSolicitud valor actualizar
     */
    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * Solicitar la realización del reporte
     * <p>
     * Teniendo en cuenta el tipo de reporte DETALLADO o GENERAL se solicita y
     * se procesa.
     *
     * @author becerraarmr
     *
     */
    public void solicitarReporte() {
        try{
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Generación de reportes")) return;

            if(tipoReporteSeleccionado == null || tipoReporteSeleccionado.getValor().isEmpty()){
                throw new ApplicationException("EL filtro Tipo Reporte es obligatorio.");
            }
            if(tipoSolicitud == null || tipoSolicitud.isEmpty()){
                throw new ApplicationException("EL filtro Tipo Solicitud es obligatorio.");
            }
            if(fechaInicial == null || fechaInicial.toString().isEmpty()){
                throw new ApplicationException("EL filtro Fecha Inicial es obligatorio.");
            }
            if(fechaFinalizacion == null || fechaFinalizacion.toString().isEmpty()){
                throw new ApplicationException("EL filtro Fecha Final es obligatorio.");
            }

            notificarReporteEnProceso();
            countRegistros = 0;

            if (tipoReporteSeleccionado.getValor().equalsIgnoreCase("Detallado")) {
                countRegistros = countRegistrosReporteDetallado();
                if (countRegistros > 0) {
                    puedeExportar = true;
                    opcionPantalla = "D";
                } else {
                    clearScreen();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron datos para su solicitud", ""));
                }
            } else if (tipoReporteSeleccionado.getValor().equalsIgnoreCase("General")) {
                listReporteGeneral = findDataReporteGeneral(null);
                if (!listReporteGeneral.isEmpty()) {
                    countRegistros = listReporteGeneral.size();
                    puedeExportar = true;
                    opcionPantalla = "G";
                } else {
                    clearScreen();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron datos para su solicitud", ""));

                }
            }
        }catch (ApplicationException e){
            LOGGER.error(e);
            FacesUtil.mostrarMensajeError("Error generando Reporte: " + e.getMessage(), e, LOGGER);
        }catch (Exception e){
            LOGGER.error(e);
            FacesUtil.mostrarMensajeError("Error generando Reporte: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Realiza la notificación por correo de la ejecución del proceso de reportes
     *
     * @author Gildardo Mora
     */
    private void notificarReporteEnProceso() {
        EmailDetailsReportDto detailsReportDto = new EmailDetailsReportDto();
        detailsReportDto.setReportName("Generación de reportes ");
        String msgDesc = "Reporte " + tipoReporteSeleccionado.getValor()
                + " solicitado por " + usuarioVT + " en " + fechaInicial.toString()
                + " al " + fechaFinalizacion.toString();
        detailsReportDto.setReportDescription(msgDesc);
        detailsReportDto.setModule("Reportes");
        detailsReportDto.setSessionUser(usuarioLogin.getUsuario());
        detailsReportDto.setSessionUserName(usuarioLogin.getNombre());
        detailsReportDto.setMessage("Generación de reporte en proceso .. " + tipoSolicitud);
        detailsReportDto.setTemplateHtml(EmailTemplatesHtmlEnum.DETAILS_REPORTS);
        blockReportServBean.notifyReportGeneration(detailsReportDto);
    }

    /**
     * Exportar archivo a formato CSV
     * <p>
     * Solicita la exportación del archivo a formato csv
     *
     * @author becerraarmr
     *
     */
    public void exportarReporteDetalladoCsv() throws ApplicationException {
        tipoArchivo = TipoArchivo.CSV;
        exportarReporteDetallado();
    }

    /**
     * Exportar archivo a formato Excel
     * <p>
     * Solicita la exportación del archivo a formato xls
     *
     * @author becerraarmr
     *
     */
    public void exportarReporteDetalladoExcel() throws ApplicationException {
        tipoArchivo = TipoArchivo.XLS;
        exportarReporteDetallado();
    }

    /**
     * Exportar archivo a formato TXT
     * <p>
     * Solicita la exportación del archivo a formato TXT
     *
     * @author becerraarmr
     *
     */
    public void exportarReporteDetalladoTxt() throws ApplicationException {
        tipoArchivo = TipoArchivo.TXT;
        exportarReporteDetallado();
    }

    private void exportarReporteDetallado() throws ApplicationException {
        switch (tipoArchivo) {
            case XLS: {
                exportExcel();
                break;
            }
            case TXT:
            case CSV: {
                downloadCvsTxt();
                break;

            }

        }
    }

    public void clearScreen() {
        this.estado = "";
        this.fechaFinalizacion = null;
        this.fechaInicial = null;
        this.tipoReporteSeleccionado = null;
        this.tipoSolicitud = null;
        this.puedeExportar = false;
        this.habilitarExcel=false;
        this.habilitarCsv = false;
        this.habilitarTxt =false;
        opcionPantalla = "C";
    }

    /**
     * Buscar un listado de registros
     * <p>
     * Se busca un listado de registros que cumplan con las condiciones
     * correspondientes.
     *
     * @author becerraarmr
     * @param rango
     *
     * @return un listado con la busqueda solicitada
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<Object[]> findDataReporteDetallado(int[] rango) throws ApplicationException {

        return ejbCmtTipoSolicitudMgl.buscarReporteDetalle(
                fechaInicial, fechaFinalizacion,
                tipoSolicitud, estado,
                rango[1], rango[0], usuarioVT);
    }

    public List<Object[]> findDataReporteGeneral(int[] rango) throws ApplicationException {

        estadoRepGral = retornaEstado(estado);

        return ejbCmtTipoSolicitudMgl.buscarReporteGeneral(
                fechaInicial, fechaFinalizacion,
                tipoSolicitud, estadoRepGral, rango);
    }

    public void exportarReporteGeneralExcel() throws ApplicationException {

        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("SOLICITUDES");
            //Blank workbook

            int expLonPag = 500;

            long totalPag = getCountRegistros();

            Row rowCabecera = sheet.createRow(0);

            int countCol = NOM_COLUMNAS_GENERAL.length;

            for (int j = 0; j < countCol; j++) {
                Cell cell = rowCabecera.createCell(j);
                cell.setCellValue(NOM_COLUMNAS_GENERAL[j]);

            }

            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0));
                    exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }

                int rowCount = 1;

                //consulta paginada de los resultados que se van a imprimir en el reporte
                List<Object[]> reporteGralList = findDataReporteGeneral(new int[]{inicioRegistros,
                    expLonPag});

                //listado de nodos cargados      
                if (reporteGralList != null && !reporteGralList.isEmpty()) {

                    Iterator<Object[]> it = reporteGralList.iterator();

                    while (it.hasNext()) {

                        Object[] objects = it.next();
                        Row rowData = sheet.createRow(rowCount);

                        for (int c = 0; c < countCol; c++) {
                            Cell cell = rowData.createCell(c);

                            if (objects[c] != null) {
                                if (objects[c].getClass().getTypeName().equals("java.sql.Timestamp")) {
                                    String fechaHora = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(objects[c]);
                                    cell.setCellValue(fechaHora == null ? "" : fechaHora + "");
                                } else {
                                    cell.setCellValue(objects[c] == null ? "" : objects[c] + "");
                                }

                            } else {
                                cell.setCellValue(objects[c] == null ? "" : objects[c] + "");
                            }
                        }
                        rowCount++;
                    }
                    System.gc();
                }

            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm");

            response1.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_General" + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response1.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ReporteBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

      public String exportExcel() throws ApplicationException  {
        try {

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("SOLICITUDES");
            //Blank workbook
            
            int expLonPag = 50;
     
            long totalPag = getCountRegistros();

            Row rowCabecera = sheet.createRow(0);

            int countCol = NOM_COLUMNAS_DETALLADO.length;

            if(tipoSolicitud.equals("12")){
                countCol = NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA.length; 
                for (int j = 0; j < countCol; j++) {
                    Cell cell = rowCabecera.createCell(j);
                    cell.setCellValue(NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA[j]);
                }
            }else{
                for (int j = 0; j < countCol; j++) {
                    Cell cell = rowCabecera.createCell(j);
                    cell.setCellValue(NOM_COLUMNAS_DETALLADO[j]);
                }
            }
            
             int rowCount = 1;                         
            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0));
                    exPagina++) {
               
                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }               
                
                //consulta paginada de los resultados que se van a imprimir en el reporte
                List<Object[]> reporteDetaList = findDataReporteDetallado(new int[]{inicioRegistros,
                    expLonPag});
             
               
                //listado de nodos cargados      
                if (reporteDetaList != null && !reporteDetaList.isEmpty()) {
                    
                     Iterator<Object[]> it = reporteDetaList.iterator();  
                         
                     while (it.hasNext()) {
                         
                        Object[] objects = it.next();
                        Row rowData = sheet.createRow(rowCount);
                        
                         for (int c = 0; c < countCol; c++) {
                             Cell cell = rowData.createCell(c);
                             cell.setCellValue(objects[c] + "");

                         }
                        rowCount++;
                    }
                     System.gc();
                }

            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");

            final SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm");

            response1.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_" + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response1.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ReporteBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }


     public String downloadCvsTxt() throws ApplicationException {
        try {
            //Cantidad de registros por pagina a consultar de la DB            
            int expLonPag = 50;
            int numRegistrosResultado;

            //numero total de registros del reporte
            numRegistrosResultado = getCountRegistros();

            long totalPag = numRegistrosResultado;
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            if(tipoSolicitud.equals("12")){
                for (int j = 0; j < NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA.length; j++) {
                    sb.append(NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA[j]);
                    if (j < NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA.length) {
                        sb.append(";");
                    }
                }
            }else{
                for (int j = 0; j < NOM_COLUMNAS_DETALLADO.length; j++) {
                    sb.append(NOM_COLUMNAS_DETALLADO[j]);
                    if (j < NOM_COLUMNAS_DETALLADO.length) {
                        sb.append(";");
                    }
                }
            }
            sb.append("\n");

            String todayStr = formato.format(new Date());
            String fileName = "Reporte_" + todayStr + "." + tipoArchivo.getValor();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");
            response1.setCharacterEncoding("UTF-8");

            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);

            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {

                int inicioRegistros = 0;
                if (exPagina > 1) {
                    inicioRegistros = (expLonPag * (exPagina - 1));
                }

                //consulta paginada de los resultados que se van a imprimir en el reporte
                List<Object[]> reporteDetaList = findDataReporteDetallado(new int[]{inicioRegistros,
                    expLonPag});

                //listado de nodos cargados      
                if (reporteDetaList != null && !reporteDetaList.isEmpty()) {

                    Iterator<Object[]> it = reporteDetaList.iterator();
                    int countCol = NOM_COLUMNAS_DETALLADO.length;

                    if(tipoSolicitud.equals("12")){
                        countCol = NOM_COLUMNAS_DETALLADO_VALIDACION_COBERTURA.length;
                    }
                    
                    while (it.hasNext()) {
                        
                         if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                        Object[] objects = it.next();

                        for (int c = 0; c < countCol; c++) {

                            sb.append(objects[c]);

                            if (c != countCol) {
                                sb.append(";");
                            }
                        }

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

        } catch (IOException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public String prepareReporte() {
        return "/view/MGL/VT/Migracion/Solicitudes/VerReporte.xhtml?faces-redirect=true";
    }

    public int countRegistrosReporteDetallado() throws ApplicationException{

        estado = retornaEstado(estado);

        return ejbCmtTipoSolicitudMgl.numRegistroReporteDetalle(
                fechaInicial, fechaFinalizacion, tipoSolicitud, estado);
    }

    public boolean isPuedeExportar() {
        return puedeExportar;
    }

    public String retornaEstado(String value) {

        String estadoSol = "";
        if (value == null) value = "";

        if (StringUtils.isNotBlank(value)) {
            if (value.equalsIgnoreCase("P")) {
                estadoSol = PENDIENTE.getValor();
            } else if (value.equalsIgnoreCase("F")) {
                estadoSol = FINALIZADO.getValor();
            } else if (value.equalsIgnoreCase("V")) {
                estadoSol = VERIFICADO.getValor();
            } else if (value.equalsIgnoreCase("R")) {
                estadoSol = RECHAZADO.getValor();
            }
        }
        return estadoSol;

    }

    public boolean isHabilitarExcel() {
        return habilitarExcel;
    }

    public void setHabilitarExcel(boolean habilitarExcel) {
        this.habilitarExcel = habilitarExcel;
    }

    public boolean isHabilitarCsv() {
        return habilitarCsv;
    }

    public void setHabilitarCsv(boolean habilitarCsv) {
        this.habilitarCsv = habilitarCsv;
    }

    public boolean isHabilitarTxt() {
        return habilitarTxt;
    }

    public void setHabilitarTxt(boolean habilitarTxt) {
        this.habilitarTxt = habilitarTxt;
    }

    public int getCountRegistros() {
        return countRegistros;
    }

    public void setCountRegistros(int countRegistros) {
        this.countRegistros = countRegistros;
    }

    public List<Object[]> getListReporteGeneral() {
        return listReporteGeneral;
    }

    public void setListReporteGeneral(List<Object[]> listReporteGeneral) {
        this.listReporteGeneral = listReporteGeneral;
    }
    
        // Validar Opciones por Rol
    
    public boolean validarOpcionGenerarReporte() {
        return validarEdicionRol(BTNGNRRPT);
    }

    public String getOpcionPantalla() {
        return opcionPantalla;
    }

    public void setOpcionPantalla(String opcionPantalla) {
        this.opcionPantalla = opcionPantalla;
    }

    public boolean isValidarOpcionGenerarReporte() {
        return validarOpcionGenerarReporte;
    }

    public void setValidarOpcionGenerarReporte(boolean validarOpcionGenerarReporte) {
        this.validarOpcionGenerarReporte = validarOpcionGenerarReporte;
    }

    public Map<String, TipoReporte> getTipoReportesMap() {
        return tipoReportesMap;
    }

    public void setTipoReportesMap(Map<String, TipoReporte> tipoReportesMap) {
        this.tipoReportesMap = tipoReportesMap;
    }

    public Map<String, String> getTipoSolicitudesMap() {
        return tipoSolicitudesMap;
    }

    public void setTipoSolicitudesMap(Map<String, String> tipoSolicitudesMap) {
        this.tipoSolicitudesMap = tipoSolicitudesMap;
    }
    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
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
