/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package co.com.claro.mgl.manager.procesomasivo;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.RrCiudadesManager;
import co.com.claro.mgl.businessmanager.address.RrRegionalesManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.DrDireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Geografico;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 */
public class ReporteMasivoHhppManager{
    
    private static final Logger LOGGER = LogManager.getLogger(ReporteMasivoHhppManager.class);
  /**
   * Tipo de reporte a procesar.
   */
  private String tipoReporte;
  /**
   * Regional o división donde quiere buscar.
   */
  private String division;
  /**
   * Comunidad o ciudad donde se quiere realizar la busqueda
   */
  private String comunidad;
  /**
   * Centro poblado de una ciudad seleccionada
   */
  private String centroPoblado;
  /**
   * Tipo de tecnología para trabajar
   */
  private String tipoTecnologia;
  
  String idCcmmMgl;
  String idCcmmRr;
  /**
   * Departamento a revisar
   */
  private String departamento;
  /**
   * Ciudad según el departamento seleccionado
   */
  private String ciudad;
  /**
   * Nodo con el que se quiere realizar el reporte
   */
  private String nodo;
  /**
   * Atributo para el reporte detallado
   */
  private String atributo;
  /**
   * Valor del atributo según el atributo sea un String o un DrDireccion
   */
  private Object valorAtributo;
  /**
   * Para controlar si se muestran o no algunos atributos
   */
  private boolean atributosVisible;
  /**
   * Para ver si se puede exportar el archivo
   */
  private boolean puedeExportarXls;
    
  private boolean puedeExportarTxt;
  
  private boolean puedeExportarCsv;
/**
 * Establecer si se puede verificar
 */
  private boolean puedeVerificar;

  private boolean puedeProcesar = true;

  private boolean procesandoSolicitud;

  private boolean mostrarPanelMensaje;

  private String msgProcesamiento = "";

  private String nameFileTmp;

  private StringBuilder dataCsvTxt = null;

  private final String TIPO_TECNOLOGIA = "TIPO_TECNOLOGIA";

  private final String ATRIBUTOS_HHPP = "ATRIBUTOS HHPP";

  private final String REPORTE_GENERAL = "REPORTE_GENERAL";

  private final String REPORTE_DETALLADO = "REPORTE_DETALLADO";

  private ProcesarReporteThread procesarReporteThread;

  
  private GeograficoMglManager ejbGeografico;
  
  private DireccionMglManager ejbDireccion;
  
  private CmtBasicaMglManager ejbBasica;
  
  private CmtTipoBasicaMglManager ejbTipoBasica;
  
  private GeograficoPoliticoManager ejbGeograficoPolitico;
  
  private NodoMglManager ejbNodo;
  
  private HhppMglManager ejbHhpp;
  
  private RrRegionalesManager ejbRegionales;
  
  private RrCiudadesManager ejbComunidades;
  private String etiqueta;

  /**
   * Creates a new instance of ProcesoMasivoController
   */
  public ReporteMasivoHhppManager() {

  }

    /**
     * Verificar el estado del proceso
     * <p>
     * Verifica como va el procesamiento de la solicitud de reporte
     *
     * @author becerraarmr
     *
     */
    public void verificarEstado() {
        if (procesarReporteThread.getRegistrosProcesados() > 0) {
            msgProcesamiento = "Se ha procesado un total de: "
                    + procesarReporteThread.getRegistrosProcesados();
        }
        if (!procesandoSolicitud) {
            msgProcesamiento = "Se ha procesado todo";
            if (procesarReporteThread.getRegistrosEncontrados() > 0) {
                puedeExportarXls = true;
                puedeExportarTxt = true;
                puedeExportarCsv = true;
            }
            puedeVerificar = false;
        }
    }

    /**
     * Procesar el reporte
     * <p>
     * Valida los valores de tipo Tecnología, ciudad y tipo de reporte para
     * luego solicitar al Thread ProcesarReporteThread que procese la petición.
     *
     * @author becerraarmr
     * @throws ApplicationException Si ocurre un error al encontrar los nodos o
     * al contar la data del reporte.
     */
    public void procesarReporte() throws ApplicationException {
        if (tipoTecnologia != null && getCiudad() != null && tipoReporte != null) {
            procesandoSolicitud = true;
            procesarReporteThread = new ProcesarReporteThread(this);
            procesarReporteThread.start();
            puedeVerificar = true;
            puedeProcesar = false;
            msgProcesamiento = "Procesando el archivo";
        }
        notificar();
    }
  
  
    public String notificar() {
        FacesContext.getCurrentInstance().
                getPartialViewContext().getRenderIds().add("formReporte:msgProcesamiento");
        FacesContext.getCurrentInstance().
                getPartialViewContext().getRenderIds().add("formReporte:exportar");
        return "";
    }
  
    /**
     * Preparar una nueva petición
     * <p>
     * Se inicializan las variables de petición con el fin de poder establecer
     * una nueva petición de reporte.
     *
     * @author becerraarmr
     */
    public void prepareNuevo() {
        tipoReporte = null;
        division = null;
        comunidad = null;
        centroPoblado = null;
        tipoTecnologia = null;
        departamento = null;
        ciudad = null;
        nodo = null;
        atributo = null;
        valorAtributo = null;
        atributosVisible = false;
        puedeExportarXls = false;
        puedeExportarTxt = false;
        puedeExportarCsv = false;
        puedeProcesar = true;
        puedeVerificar = false;
        procesandoSolicitud = false;
        procesarReporteThread = null;
        msgProcesamiento = "";
        dataCsvTxt = null;
    }

    public void volverMenu() {
        if (!procesandoSolicitud) {
            prepareNuevo();
        }
    }

    /**
     * Exportar el reporte a Excel
     * <p>
     * Se prepara para reportar al archivo realizado a quien realiza la
     * petición.
     *
     * @author becerraarmr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void exportarXls() throws ApplicationException {
        ServletOutputStream outputStream = null;
        try {
            java.util.Date fecha = new Date();
            SimpleDateFormat formato
                    = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fec = formato.format(fecha);
            String filename = tipoReporte + "_" + fec + ".xlsx";
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response
                    = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "filename=" + filename);
            outputStream = response.getOutputStream();
            outputStream.write(converterFileToByte(new File("tmp", nameFileTmp)));
            outputStream.flush();
            outputStream.close();
            context.responseComplete();
            this.puedeExportarXls = false;
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw  e;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException(msg, e);
                }
            }
        }
    }

    /**
     * Solita exportar el reporte a TXT
     * <p>
     * Solicita al método general de exportar Csv y Txt que lo exporte en CSV.
     *
     * @author becerraarmr
     */
    public void exportarTxt() throws ApplicationException {
        exportarTxtCsv("txt");
        puedeExportarTxt = false;
        notificar();
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
    private byte[] converterFileToByte(File fileXls) throws ApplicationException {
        try {
            byte[] bytesArray = new byte[(int) fileXls.length()];
            FileInputStream fis = new FileInputStream(fileXls);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            return bytesArray;
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Exporta el reporte a CSC o TXT
     * <p>
     * Según la petición del usuario se exporta el archivo del reporte a su
     * correspondiente CSVo TXT.
     *
     * @author becerraarmr
     *
     * @param tipoArchivo CSV o TXT
     */
    private void exportarTxtCsv(String tipoArchivo) throws ApplicationException {
        try {
            java.util.Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fec = formato.format(fecha);
            String filename = tipoReporte + "_" + fec + "." + tipoArchivo;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setContentType("application/force.download");
            response.setHeader("Content-Disposition", "filename=" + filename);
            byte[] bytesArray = getDataCsvTxt().toString().getBytes("UTF-8");
            response.getOutputStream().write(bytesArray);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            
        } catch (IOException e) {
            String msg = ">> Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
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
    private byte[] converterXlsToCsv(File file) throws ApplicationException {
        try {
            // For storing data into CSV files
            StringBuilder data = new StringBuilder();
            // Get the workbook object for XLS file
            FileInputStream inputStream = new FileInputStream(file);

            Workbook workbook = new XSSFWorkbook(inputStream);

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
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Solicitar exportar a CSV
     * <p>
     * Solicita al método exportarTxtCsv que exporte el reporte a CSV
     *
     * @author becerraarmr
     *
     */
    public void exportarCsv() throws ApplicationException {
        exportarTxtCsv("csv");
        puedeExportarCsv = false;
        notificar();

    }

    /**
     * Cambiar el valor del atributo tipo reporte
     * <p>
     * Actualiza la variable tipo reporte y la atributos visibles con el fin de
     * notificar la acción realizada.
     *
     * @author becerraarmr
     *
     * @param event
     */
    public void tipoReporteChange(ValueChangeEvent event) {
        tipoReporte = (String) event.getNewValue();
        atributosVisible = tipoReporte.equalsIgnoreCase(REPORTE_DETALLADO);
    }

    /**
     * Actualizar el valor del atributo tipotecnologia
     * <p>
     * Al momento de seleccionar el tipo de tecnología se debe actualizar la
     * variable.
     *
     * @author becerraarmr
     * @param event
     */
    public void tiposTecnologiasChange(ValueChangeEvent event) {
        tipoTecnologia = (String) event.getNewValue();
    }

    /**
     * Actualizar el valor del atributo división
     * <p>
     * Al momento de seleccionar la división se debe actualizar y limpiar
     * comunidad, departamento, ciudad, centroPoblado y nodo.
     *
     * @author becerraarmr
     * @param event
     */
    public void divisionChange(ValueChangeEvent event) {
        division = (String) event.getNewValue();
        comunidad = null;
        departamento = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;

    }

    public void comunidadChange(ValueChangeEvent event)
            throws ApplicationException {
        comunidad = (String) event.getNewValue();
        departamento = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;
        GeograficoPoliticoMgl geograficoPoliticoMgl
                = ejbGeograficoPolitico.findCityByComundidad(comunidad);
        if (geograficoPoliticoMgl != null) {
            departamento = geograficoPoliticoMgl.getGeoGpoId() + "";
            ciudad = geograficoPoliticoMgl.getGpoId() + "";
        }
    }

    public void departamentosChange(ValueChangeEvent event) {
        departamento = (String) event.getNewValue();
        division = null;
        comunidad = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;
    }

    public void ciudadesChange(ValueChangeEvent event) {
        ciudad = (String) event.getNewValue();
        centroPoblado = null;
        nodo = null;
    }

    public void centroPobladoChange(ValueChangeEvent event) {
        centroPoblado = (String) event.getNewValue();
        nodo = null;
    }

    /**
     * Buscar los nodos que corresponden al filtro.
     * <p>
     * Solicita la busqueda de los nodos que correspondan con la ciudad/centro
     * poblado y tipo de tecnologia
     *
     * @author becerraarmr
     *
     * @return un SelectItem[] con la información encontrada
     *
     * @throws ApplicationException si se presenta un error en la creación del
     * SelectItem[]
     */
    public SelectItem[] getItemsNodos()
            throws ApplicationException {
        List<NodoMgl> lista = new ArrayList<NodoMgl>();
        //Verificar conexion con el EJB y que la ciudad y el tipo de tecnología 
        //tengan información
        if (getEjbNodo() != null && getCiudad() != null && tipoTecnologia != null) {
            CmtBasicaMgl tipoTecnologiaBD = getCmtBasicaMgl(tipoTecnologia);
            if (centroPoblado != null) {
                if (!centroPoblado.isEmpty()) {
                    BigDecimal centroPobladoBD = new BigDecimal(centroPoblado);
                    lista = getEjbNodo().
                            findNodosCentroPobladoAndTipoTecnologia(centroPobladoBD, tipoTecnologiaBD);
                }
            } else if (!ciudad.isEmpty()) {
                BigDecimal aux = new BigDecimal(getCiudad());
                lista = getEjbNodo().
                        findNodosCiudadAndTipoTecnologia(aux, tipoTecnologiaBD);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsCentrosPoblados()
            throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<GeograficoPoliticoMgl>();
        if (ejbGeograficoPolitico != null && getCiudad() != null) {
            if (!ciudad.isEmpty()) {
                BigDecimal aux = new BigDecimal(getCiudad());
                lista = ejbGeograficoPolitico.findCentroPoblado(aux);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsDepartamentos() throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<GeograficoPoliticoMgl>();
        if (ejbGeograficoPolitico != null) {
            lista = ejbGeograficoPolitico.findDptos();
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsTiposTecnologias()
            throws ApplicationException {
        List<CmtBasicaMgl> lista = new ArrayList<CmtBasicaMgl>();
        if (ejbBasica != null && ejbTipoBasica != null) {
            CmtTipoBasicaMgl tipoBasica
                    = ejbTipoBasica.findNombreTipoBasica(TIPO_TECNOLOGIA);
            if (tipoBasica != null) {
                lista = ejbBasica.findByTipoBasica(tipoBasica);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsRegionales()
            throws ApplicationException {
        List<CmtRegionalRr> lista = new ArrayList<CmtRegionalRr>();
        if (ejbRegionales != null) {
            lista = ejbRegionales.findRegionales();
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsComunidades() throws ApplicationException {
        List<RrCiudades> lista = new ArrayList<RrCiudades>();
        if (ejbComunidades != null && division != null) {
            if (!division.isEmpty()) {

                lista = ejbComunidades.findByCodregional(division);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    public SelectItem[] getItemsCiudades() throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<GeograficoPoliticoMgl>();
        if (ejbGeograficoPolitico != null && departamento != null) {
            if (!departamento.isEmpty()) {
                BigDecimal idCiudad = new BigDecimal(departamento);
                lista = ejbGeograficoPolitico.findCiudades(idCiudad);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Mostrar el listado de reportes
     * <p>
     * Muestra un listado con los dos reportes que se pueden realizar.
     *
     * @author becerraarmr
     * @return un vector SelectItem con la data relacionada.
     *
     * @throws ApplicationException
     */
    public SelectItem[] getItemstipoReportes()
            throws ApplicationException {
        SelectItem[] items = new SelectItem[2];
        items[0] = new SelectItem(REPORTE_GENERAL, "General");
        items[1] = new SelectItem(REPORTE_DETALLADO, "Detallado");
        return items;
    }

    /**
     * Mostrar el listado de atributos a seleccionar
     * <p>
     * Prepara un listado de atributos a seleccionar para realizar el reporte
     * detallado.
     *
     * @author becerraarmr
     * @return un vector SelectItem con la data relacionada.
     *
     * @throws ApplicationException si hay un error al consultar los atributos.
     */
    public SelectItem[] getItemsAtributos()
            throws ApplicationException {
        List<CmtBasicaMgl> lista = new ArrayList<CmtBasicaMgl>();
        if (ejbBasica != null && ejbTipoBasica != null) {
            CmtTipoBasicaMgl tipoBasica
                    = ejbTipoBasica.findNombreTipoBasica(ATRIBUTOS_HHPP);
            if (tipoBasica != null) {
                lista = ejbBasica.findByTipoBasica(tipoBasica);
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Adherir un mensaje de error
     * <p>
     * Adhiere al FacesMessage un mensaje de error con el valor del parametro.
     *
     * @author becerraarmr
     * @param msg mensaje a mostrar.
     */
    public void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    /**
     * Adherir un mensaje de satisfactorio
     * <p>
     * Adhiere al FacesMessage un mensaje satisfactorio con el valor del
     * parametro.
     *
     * @author becerraarmr
     * @param msg mensaje a mostrar.
     */
    public void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    /**
     * @return the tipoTecnologia
     */
    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    /**
     * @param tipoTecnologia the tipoTecnologia to set
     */
    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * @param nodo the nodo to set
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * @return the tipoReporte
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     * @param tipoReporte the tipoReporte to set
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the comunidad
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * @param comunidad the comunidad to set
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * @return the centroPoblado
     */
    public String getCentroPoblado() {
        return centroPoblado;
    }

    /**
     * @param centroPoblado the centroPoblado to set
     */
    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    /**
     * @return the valorAtributo
     */
    public Object getValorAtributo() {
        return valorAtributo;
    }

    /**
     * @param valorAtributo the valorAtributo to set
     */
    public void setValorAtributo(Object valorAtributo) {
        this.valorAtributo = valorAtributo;
    }

    /**
     * @return the atributosVisible
     */
    public boolean isAtributosVisible() {
        return atributosVisible;
    }

    /**
     * @param atributosVisible the atributosVisible to set
     */
    public void setAtributosVisible(boolean atributosVisible) {
        this.atributosVisible = atributosVisible;
    }

    /**
     * @return the atributo
     */
    public String getAtributo() {
        return atributo;
    }

    /**
     * @param atributo the atributo to set
     */
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    
    
    

    /**
     * Buscar el valor del geográfico politico Busca el valor del objeto
     * geográfico político correspondiente según la id.
     *
     * @author becerraarmr
     * @param geo_id
     *
     * @return el objeto relacionado.
     */
    public GeograficoPoliticoMgl getGeograficoMgl(String geo_id) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(geo_id);
        if (valor != null) {
            try {
                GeograficoPoliticoMgl valorCiudad
                        = ejbGeograficoPolitico.findById(valor);
                if (valorCiudad != null) {
                    return valorCiudad;
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                        + e.getMessage();
                LOGGER.error(msg);
            }
        }
        return null;
    }

    /**
     * Buscar la básica
     * <p>
     * Busca el objeto CmtBasicaMgl correpondiente según el parámetro insertado.
     *
     * @author becerraarmr
     * @param idBasica
     *
     * @return
     */
    public CmtBasicaMgl getCmtBasicaMgl(String idBasica) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(idBasica);
        if (valor != null) {
            try {
                CmtBasicaMgl basica = ejbBasica.findById(valor);
                if (basica != null) {
                    return basica;
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
                LOGGER.error(msg);
            }
        }
        return null;
    }

    public Geografico getGeografico(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            try {
                Geografico barrio = ejbGeografico.findBarrioHhpp(hhppMgl.getHhpId());
                if (barrio != null) {
                    return barrio;
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
                LOGGER.error(msg);
            }
        }
        return null;
    }

    public DireccionMgl getDireccionMgl(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            if (hhppMgl.getDirId() != null) {
                try {
                    DireccionMgl basica = new DireccionMgl();
                    basica.setDirId(hhppMgl.getDirId());
                    basica = ejbDireccion.findById(basica);
                    if (basica != null) {
                        return basica;
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
                    LOGGER.error(msg);
                }
            }
        }
        return null;
    }

    /**
     * Buscar el nodo
     * <p>
     * Busca el objeto nodo según el parámetro.
     *
     * @author becerraarmr
     *
     * @param idNodo id del nodo a buscar.
     *
     * @return un objeto NodoMgl
     */
    public NodoMgl getNodoMgl(String idNodo) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(idNodo);
        if (valor != null) {
            try {
                NodoMgl aux = getEjbNodo().findById(valor);
                if (aux != null) {
                    return aux;
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
                LOGGER.error(msg);;
            }
        }
        return null;
    }

    /**
     * @return the ejbNodo
     */
    public NodoMglManager getEjbNodo() {
        return ejbNodo;
    }

    /**
     * @return the ejbHhpp
     */
    public HhppMglManager getEjbHhpp() {
        return ejbHhpp;
    }

    /**
     * @return the puedeExportarXls
     */
    public boolean isPuedeExportarXls() {
        return puedeExportarXls;
    }

    /**
     * @return the puedeExportarCsv
     */
    public boolean isPuedeExportarCsv() {
        return puedeExportarCsv;
    }

    /**
     *
     * @return the puedeExportarTxt
     */
    public boolean isPuedeExportarTxt() {
        return puedeExportarTxt;
    }

    /**
     * @return the procesandoSolicitud
     */
    public boolean isProcesandoSolicitud() {
        return procesandoSolicitud;
    }

    public void setProcesandoSolicitud(boolean procesandoSolicitud) {
        this.procesandoSolicitud = procesandoSolicitud;
    }

    /**
     * Contar la data que va a procesar
     * <p>
     * Se solicita a la base de datos la cantidad de registros que se van a
     * procesar según los filtros.
     *
     * @author becerraarmr
     *
     * @return un entero con el valor
     */
    public int countDataReporte() throws ApplicationException {
        try {
            BigDecimal idBasicaTecnologia
                    = ProcesoMasivoUtil.getBigDecimal(tipoTecnologia);
            BigDecimal idGpoCiudad = ProcesoMasivoUtil.getBigDecimal(getCiudad());
            BigDecimal idGpoCentroPoblado
                    = ProcesoMasivoUtil.getBigDecimal(centroPoblado);
            BigDecimal idNodo = ProcesoMasivoUtil.getBigDecimal(nodo);
            CmtBasicaMgl atributoBasica = getCmtBasicaMgl(atributo);
            BigDecimal etiquetaSeleccionada = ProcesoMasivoUtil.getBigDecimal(etiqueta);
            String nameAtributo = atributoBasica != null
                    ? atributoBasica.getNombreBasica() : null;
            //JDHT 
            BigDecimal identificadorCcmmMgl = BigDecimal.ZERO;
            BigDecimal identificadorCcmmRr = BigDecimal.ZERO;
            if(idCcmmMgl != null && !idCcmmRr.isEmpty()){
                identificadorCcmmMgl = new BigDecimal(idCcmmMgl);
            }else{
                if(idCcmmRr != null && !idCcmmMgl.isEmpty()){
                    identificadorCcmmRr = new BigDecimal(idCcmmRr);
                }
            }            
            
            if (idGpoCiudad == null || idBasicaTecnologia == null) {
                return 0;
            }
            return ejbHhpp.countHhpp(idGpoCiudad, idGpoCentroPoblado, idNodo,
                    idBasicaTecnologia, nameAtributo, valorAtributo,
                    null, null, null, false, false,etiquetaSeleccionada, identificadorCcmmMgl, identificadorCcmmRr);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Solicitar un Hhpp según los datos del filtro.
     * <p>
     * Se solicita a la base de datos los Hhpp que cumplen la condición con el:
     * tipo de tecnología, ciudad, nodo y el rango a busscar
     *
     * @author becerraarmr
     *
     * @param rango Arreglo con el valor inicial y final del rango
     *
     * @return un Listado con los Hhpp que cumplen las condiciones
     *
     * @see HhppMgl
     */
    public List<HhppMgl> findDataReporte(int[] rango) throws ApplicationException {
        try {
            BigDecimal idBasicaTecnologia
                    = ProcesoMasivoUtil.getBigDecimal(tipoTecnologia);
            BigDecimal idGpoCiudad = ProcesoMasivoUtil.getBigDecimal(getCiudad());
            BigDecimal idGpoCentroPoblado
                    = ProcesoMasivoUtil.getBigDecimal(centroPoblado);
            BigDecimal idNodo = ProcesoMasivoUtil.getBigDecimal(nodo);
            CmtBasicaMgl atributoBasica = getCmtBasicaMgl(atributo);
            String nameAtributo = atributoBasica != null
                    ? atributoBasica.getNombreBasica() : null;
             BigDecimal etiquetaSeleccionada = ProcesoMasivoUtil.getBigDecimal(etiqueta);
                         //JDHT 
            BigDecimal identificadorCcmmMgl = BigDecimal.ZERO;
            BigDecimal identificadorCcmmRr = BigDecimal.ZERO;
            if(idCcmmMgl != null && !idCcmmRr.isEmpty()){
                identificadorCcmmMgl = new BigDecimal(idCcmmMgl);
            }else{
                if(idCcmmRr != null && !idCcmmMgl.isEmpty()){
                    identificadorCcmmRr = new BigDecimal(idCcmmRr);
                }
            }  
            if (idGpoCiudad == null || idBasicaTecnologia == null) {
                return null;
            }
            return ejbHhpp.findHhpp(idGpoCiudad, idGpoCentroPoblado, idNodo,
                    idBasicaTecnologia, nameAtributo, valorAtributo, null, null, rango, null, false,
                    false,etiquetaSeleccionada,identificadorCcmmMgl,identificadorCcmmRr);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * @return the mostrarPanelMensaje
     */
    public boolean isMostrarPanelMensaje() {
        return mostrarPanelMensaje;
    }

    /**
     * Buscar los nodos Busca los nodos que hacen parte de la ciudad o del
     * codnodo
     *
     * @author becerraarmr
     *
     * @return un lista con los NodoMgl correspondientes
     *
     * @see NodoMgl entidad que identifica al nodo
     */
    public List<NodoMgl> findNodos() throws ApplicationException {
        try {
            BigDecimal gpoId = ProcesoMasivoUtil.getBigDecimal(getCiudad());
            if (tipoTecnologia != null && gpoId != null) {
                CmtBasicaMgl basTipoTecnologia = getCmtBasicaMgl(tipoTecnologia);
                return ejbNodo.findNodos(basTipoTecnologia, gpoId, nodo);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        return null;
    }

    public String getMsgProcesamiento() {
        return msgProcesamiento;
    }

    public void setMsgProcesamiento(String msgProcesamiento) {
        this.msgProcesamiento = msgProcesamiento;
    }

    public boolean isPuedeVerificar() {
        return puedeVerificar;
    }

    public boolean isPuedeProcesar() {
        return puedeProcesar;
    }

    public void setNameFileTmp(String nameFileTmp) {
        this.nameFileTmp = nameFileTmp;
    }

    public StringBuilder getDataCsvTxt() {
        if (dataCsvTxt == null) {
            dataCsvTxt = new StringBuilder();
        }
        return dataCsvTxt;
    }

    /**
     * Buscar los registrados encontrados
     *
     * Se muestra los registros encontrados como un mensaje.
     *
     * @author becerraarmr
     * @return el mensaje de los registros encontrdos
     */
    public String getRegistrosEncontrados() {
        if (procesarReporteThread != null) {
            int registrosEncontrados
                    = procesarReporteThread.getRegistrosEncontrados();
            if (registrosEncontrados != -1) {
                return "Registros encontrados: " + registrosEncontrados;
            }
        }
        return "";
    }
  
    /**
     * Mostrar el nombre del atributo.
     *
     * Se muestra el nombre del atributo que corresponde al idBasica que llega.
     *
     * @author becerraarmr
     * @param idBasica id de la básica que está en Cmt_Basica
     * @return "NA" si no hay dato o el valor correspondiente al idBasica
     * ingresado
     */
    public String mostrarNombreAtributo(String idBasica) {
        if (idBasica != null) {
            if (!idBasica.isEmpty()) {
                CmtBasicaMgl basica = getCmtBasicaMgl(idBasica);
                if (basica != null) {
                    return basica.getNombreBasica();
                }
            }
        }
        return "NA";
    }

    /**
     * Actualizar el valor del atributo.
     *
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param puedeExportarXls actualizar
     */
    public void setPuedeExportarXls(boolean puedeExportarXls) {
        this.puedeExportarXls = puedeExportarXls;
    }
}

class ProcesarReporteThread extends Thread{
    
    private static final Logger LOGGER = LogManager.getLogger(ProcesarReporteThread.class);
    /**
     * Registros encontrados
     */
    private int registrosEncontrados = -1;//Inicia sin datos

    private final ReporteMasivoHhppManager control;
    /**
     * Paginador
     */
    private PaginationHelper pagination;
    /**
     * Tamaño de la página
     */
    private final int pageSize = 10000;
    /**
     * Cantidad de registros procesados
     */
    private int registrosProcesados = 0;

    /**
     * Para guardar la data encontrada en la base de datos.
     */
    private Workbook workbook;

    /**
     * Número de filas procesadas
     */
    private int rownum = 0;

    /**
     * Máximo de filas por hoja
     */
    private final int MAX_ROW_SHEET = 50000;

    /**
     * Número de hojas creadas
     */
    private int numSheet = 0;

    /**
     * Atributo para la exportación de archivos Csv y Txt
     */
    private StringBuilder headerCsvTxt;

    /**
     * Crear la instancia
     * <p>
     * Crea una instancia
     *
     * @author becerraarmr
     *
     */
    public ProcesarReporteThread(ReporteMasivoHhppManager control) {
        this.control = control;
    }

    /**
     * Ejecutar el Thread
     * <p>
     * Pone en ejecución el Thread, solicitando la creación del archivo y
     * escribiendo el archivo.
     *
     * @author becerraarmr
     */
    @Override
    public void run() {
        synchronized (this) {
            try {
                registrosEncontrados = control.countDataReporte();
                if (registrosEncontrados == 0) {
                    control.setMsgProcesamiento("No hay registros con los parámetros insertados");
                } else {
                    control.setMsgProcesamiento("Se empieza a procesar el reporte");
                    workbook = new SXSSFWorkbook(-1);
                    headerCsvTxt = new StringBuilder();
                    createSheetWithHeader();
                    control.getDataCsvTxt().append(headerCsvTxt);
                    cargarData();
                    while (getPagination().isHasNextPage()) {
                        getPagination().nextPage();
                        cargarData();
                    }
                    createFileTmp();
                }
                control.setProcesandoSolicitud(false);

            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
                LOGGER.error(msg);
            }

        }
    }

    /**
     * Crear Archivo temporal
     * <p>
     * Crea un archivo con el fin de guardarlo una ves termine de realizar el
     * reporte.
     *
     * @author becerraarmr
     */
    private void createFileTmp() throws ApplicationException {
        try {
            SimpleDateFormat formato
                    = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fecha = formato.format(new Date());
            String filename = "PM_TMP_" + fecha + ".xlsx";
            File file = new File("tmp", filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            control.setNameFileTmp(filename);
        } catch (FileNotFoundException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
    }

    /**
     * Cargar la lista de datos en las variables de session.
     * <p>
     * Se carga la lista de datos en las variables de session.
     *
     * @author becerraarmr
     *
     * @param listaData lista de datos a cargar
     */
    private void cargarData() {
        try {
            List<HhppMgl> lista = getPagination().createPageData();
            if (lista == null) {
                return;
            }
            if (lista.isEmpty()) {
                return;
            }
            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);
            CellStyle lockedCellStyle = workbook.createCellStyle();
            lockedCellStyle.setLocked(true);
            Sheet sheet = workbook.getSheet(numSheet + "");
            while (!lista.isEmpty()) {
                if (rownum == MAX_ROW_SHEET) {
                    rownum = 0;
                    sheet = createSheetWithHeader();
                }
                HhppMgl hhppMgl = lista.remove(0);
                DireccionMgl dirMgl = control.getDireccionMgl(hhppMgl);
                Geografico geografico = control.getGeografico(hhppMgl);
                Object[] datos = ProcesoMasivoUtil.valoresHeaders(hhppMgl,
                        dirMgl, geografico);
                Row row = sheet.createRow(rownum);
                int i = 1;
                for (Object header : datos) {
                    Cell cell = row.createCell(i - 1);
                    String item = header == null ? "" : header.toString();

                    cell.setCellValue(item);

                    if (rownum > 2 && (i == 6 || i == 7 || i > 10)) {
                        cell.setCellStyle(unlockedCellStyle);
                    }
                    control.getDataCsvTxt().append(item);
                    if (i == datos.length) {
                        continue;
                    }
                    control.getDataCsvTxt().append(",");
                    i++;
                }
                if ("REPORTE_DETALLADO".equalsIgnoreCase(control.getTipoReporte())) {
                    String atributo = control.getAtributo();
                    CmtBasicaMgl basicaDir = control.getCmtBasicaMgl(atributo);
                    String nombreBasica = "";
                    if (basicaDir != null) {
                        nombreBasica = basicaDir.getNombreBasica();
                    }
                    if ("DIRECCION".equalsIgnoreCase(nombreBasica)) {
                        String[] headerDir = ProcesoMasivoUtil.headerDirecciones();
                        int j = 0;
                        for (Object header : headerDir) {
                            Cell cell = row.createCell(i);
                            String item = "";// Se deja en blanco

                            cell.setCellValue(item);

                            if (rownum > 2) {
                                cell.setCellStyle(unlockedCellStyle);
                            }
                            control.getDataCsvTxt().append(item);
                            if (j == headerDir.length) {
                                continue;
                            }
                            control.getDataCsvTxt().append(",");
                            i++;
                            j++;
                        }
                    }
                }
                rownum++;
                registrosProcesados++;
                if (lista.isEmpty()) {
                    continue;
                }
                control.getDataCsvTxt().append("\n");
            }

            ((SXSSFSheet) sheet).flushRows();
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Crea la data del filtro seleccionado
     * <p>
     * Se crea la data del filtro seleccionado; el tipo de reporte, el tipo de
     * tec nología, el departamento, la ciudad, el centro poblado, el nodo,
     * buscando en las tablas el valor correspondiente
     *
     * @author becerraarmr
     *
     * @return String[] con los valores de filtro
     */
    private String[] getDataFilters() {
        String tipoReporte = control.getTipoReporte();
        String tipoTecnologia = control.getTipoTecnologia();
        String departamento = control.getDepartamento();
        String ciudad = control.getCiudad();
        String centroPoblado = control.getCentroPoblado();
        String nodo = control.getNodo();
        String atributo = control.getAtributo();
        Object valorAtributo = control.getValorAtributo();

        String valorAtributoString;
        if (valorAtributo instanceof DrDireccion) {
            valorAtributoString = ((DrDireccion) valorAtributo).getGeoReferenciadaString();
        } else {
            valorAtributoString = (String) valorAtributo;
        }

        String[] filtrosValores = {tipoReporte, tipoTecnologia, departamento, ciudad,
            centroPoblado, nodo, atributo, valorAtributoString};
        CmtBasicaMgl basTipoTecnologia = control.getCmtBasicaMgl(tipoTecnologia);
        filtrosValores[1] = basTipoTecnologia != null
                ? basTipoTecnologia.getNombreBasica() : "";
        GeograficoPoliticoMgl geoDptoCiudadCentroPob
                = control.getGeograficoMgl(departamento);
        filtrosValores[2] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        geoDptoCiudadCentroPob = control.getGeograficoMgl(ciudad);
        filtrosValores[3] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        geoDptoCiudadCentroPob = control.getGeograficoMgl(centroPoblado);
        filtrosValores[4] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        NodoMgl nodoMglFilt = control.getNodoMgl(nodo);
        filtrosValores[5] = nodoMglFilt != null
                ? nodoMglFilt.getNodNombre() : "";
        basTipoTecnologia = control.getCmtBasicaMgl(atributo);
        filtrosValores[6] = basTipoTecnologia != null
                ? basTipoTecnologia.getNombreBasica() : "";
        return filtrosValores;
    }

    /**
     * Crear cabecera del archivo de excel
     * <p>
     * Crea la cabecera en tres filas, donde la primera son los datos del
     * filtro, la segunda los valores del filtro y la tercera las cabeceras del
     * Hhpp.
     *
     * @author becerraarmr
     *
     * @return XSSFWorkbook con los valores de inicio del archivo
     */
    private Sheet createSheetWithHeader() throws ApplicationException {
        try {
            String[] filters = getDataFilters();
            numSheet++;
            Sheet sheet = workbook.createSheet(numSheet + "");

            Row row = sheet.createRow(rownum);//Create 1 row
            rownum++;
            for (int i = 0; i < ProcesoMasivoUtil.filtrosHeaders().length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(ProcesoMasivoUtil.filtrosHeaders()[i]);
                headerCsvTxt.append(ProcesoMasivoUtil.filtrosHeaders()[i]);
                if (i < ProcesoMasivoUtil.filtrosHeaders().length) {
                    headerCsvTxt.append(",");
                }
            }
            headerCsvTxt.append("\n");
            row = sheet.createRow(rownum);//Create 2 row
            rownum++;
            for (int i = 0; i < filters.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(filters[i]);
                headerCsvTxt.append(filters[i]);
                if (i < ProcesoMasivoUtil.filtrosHeaders().length) {
                    headerCsvTxt.append(",");
                }
            }
            headerCsvTxt.append("\n");
            row = sheet.createRow(rownum);//Create 3 row
            rownum++;
            String[] header = ProcesoMasivoUtil.header();
            int i = 0;
            for (; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                headerCsvTxt.append(header[i]);
                if (i < header.length) {
                    headerCsvTxt.append(",");
                }
            }
            if ("REPORTE_DETALLADO".equalsIgnoreCase(control.getTipoReporte())) {
                String atributo = control.getAtributo();
                CmtBasicaMgl basicaDir = control.getCmtBasicaMgl(atributo);
                String nombreBasica = "";
                if (basicaDir != null) {
                    nombreBasica = basicaDir.getNombreBasica();
                }
                if ("DIRECCION".equalsIgnoreCase(nombreBasica)) {
                    String[] headerDir = ProcesoMasivoUtil.headerDirecciones();
                    for (int j = 0; j < headerDir.length; j++, i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(headerDir[j]);
                        headerCsvTxt.append(headerDir[j]);
                        if (i < headerDir.length) {
                            headerCsvTxt.append(",");
                        }
                    }
                }
            }
            headerCsvTxt.append("\n");

            return sheet;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
    }

    /**
     * Crear el paginador de accesso a los datos
     * <p>
     * Se crea el paginador que permite acceder a la base de datos
     *
     * @author becerraarmr
     *
     * @return PaginationHelper que controlará el llamado a los datos
     */
    private PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(pageSize) {
                @Override
                public int getItemsCount() {
                    return registrosEncontrados;
                }

                @Override
                public List<HhppMgl> createPageData() {
                    try {
                        return control.findDataReporte(new int[]{getPageFirstItem(),
                            getPageFirstItem() + getPageSize()});
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"
                                + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                                + e.getMessage();
                        LOGGER.error(msg);
                        return null;
                    } catch (Exception e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"
                                + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                                + e.getMessage();
                        LOGGER.error(msg);
                        return null;
                    }                   
                }
            };
        }
        return pagination;
    }

    /**
     * Buscar el valor de regProcesados.
     * <p>
     * Muestra el valor de registros que han sido procesados
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public int getRegistrosProcesados() {
        return registrosProcesados;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Muestra el valor que contiene registrosEncontrados
     *
     * @author becerraarmr
     *
     * @return el entero con el valor encontrado
     */
    public int getRegistrosEncontrados() {
        return registrosEncontrados;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Muestra el valor del workbook
     *
     * @author becerraarmr
     *
     * @return Workbook con el valor
     */
    public Workbook getWorkbook() {
        return workbook;
    }

}

abstract class PaginationHelper<T> {

    /**
     * Tamaño de la página
     */
    private final int pageSize;
    /**
     * Para contrar las páginas
     */
    private int page;

    /**
     * Crear la instancia
     * <p>
     * Crea una instancia PaginationHelper
     *
     * @author becerraarmr
     * @param pageSize el tamaño de la página
     */
    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Contar la cantidad de registros
     *
     * Se cuenta la cantida de registros que se encuentran en la consulta.
     *
     * @author becerraarmr
     * @return un entero con el valor establecido.
     */
    public abstract int getItemsCount();

    /**
     * Crear el listado con la data
     *
     * Se crea el listado con la data según la paginación establecida
     *
     * @return un lista con la data
     */
    public abstract List<T> createPageData();

    /**
     * Buscar el primer item de la página
     *
     * Se busca el primer item de la página que está en funcionamiento.
     *
     * @author becerraarmr
     * @return un entero con el valor
     */
    public int getPageFirstItem() {
        return page * pageSize;
    }

    /**
     * Buscar el último valor de la página
     *
     * Se busca el último valor de la página que está en funcionamiento
     *
     * @author becerraarmr
     * @return un entero con el valor
     */
    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    /**
     * Buscar si hay más página
     *
     * Según la página donde se encuentre, establece si hay no una página
     * siguiente.
     *
     * @author becerraarmr
     * @return un true o un false con el valor correspondiente
     */
    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    /**
     * Buscar la página siguiente
     *
     * Solicita aumentar a la página siguiente.
     *
     * @author becerraarmr
     *
     */
    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    /**
     * Buscar página anterior
     *
     * Establece si hay página anterior
     *
     * @author becerraarmr
     * @return true si lo hay y false en caso contrario
     */
    public boolean isHasPreviousPage() {
        return page > 0;
    }

    /**
     * Volver a la página anterior Vuelve a la página anterior disminuyendo el
     * atributo page.
     *
     * @author becerraarmr
     */
    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    /**
     * Mostrar el valor del atributo pageSize.
     *
     * Muestra el valor del atributo pageSize que correponde al tamaño de la
     * página.
     *
     * @author becerraarmr
     *
     * @return un entero con el valor correspondiente.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Mostrar la página
     *
     * Muestra el valor que tiene la página en ese momento.
     *
     * @author becerraarmr
     * @return entero con el valor representativo.
     */
    public int getPage() {
        return page;
    }
}

/**
 * Representar datos de proceso masivo
 * <p>
 * <p>
 * Con el fin de conocer la cabecera del archivo que se va a generar y la data
 * que se va a leer para convertirla en un objeto HhppMgl.
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
class ProcesoMasivoUtil {
    
    private static final Logger LOGGER = LogManager.getLogger(ProcesoMasivoUtil.class);
    

    /**
     * Convertir los parámetros de entrada en un arreglo de objetos
     * <p>
     * Se convierten cada uno de los datos que contiene el parámetro hhppMgl con
     * direccionesMgl y Geografico, pasando cada uno de los datos a un arreglo
     * de objetos.
     *
     * @author becerraarmr
     * @param hhppMgl valor con la data principal
     * @param direccionMgl direccion relacionada con el hhppMgl
     * @param geografico valor relacionado con direccionMgl
     *
     * @return un arreglo de objetos.
     */
    public static Object[] valoresHeaders(HhppMgl hhppMgl,
            DireccionMgl direccionMgl, Geografico geografico) {

        String estrato = "";
        String nivelSocio = "";
        Object[] retornar = null;

        if (hhppMgl != null) {
            if (hhppMgl.getDireccionObj() != null && hhppMgl.getSubDireccionObj() != null) {
                estrato = hhppMgl.getSubDireccionObj() != null
                        ? ("NULL".equalsIgnoreCase(hhppMgl.getSubDireccionObj().getSdiEstrato() + "")
                        ? "" : hhppMgl.getSubDireccionObj().getSdiEstrato() + "") : "";
                nivelSocio = hhppMgl.getSubDireccionObj() != null
                        ? ("NULL".equalsIgnoreCase(hhppMgl.getSubDireccionObj().getSdiNivelSocioecono() + "")
                        ? "" : hhppMgl.getSubDireccionObj().getSdiNivelSocioecono() + "") : "";
            } else if (hhppMgl.getDireccionObj() != null) {
                estrato = direccionMgl != null ? ("NULL".equalsIgnoreCase(direccionMgl.getDirEstrato() + "")
                        ? "" : direccionMgl.getDirEstrato() + "") : "";
                nivelSocio = direccionMgl != null
                        ? ("NULL".equalsIgnoreCase(direccionMgl.getDirNivelSocioecono() + "")
                        ? "" : direccionMgl.getDirNivelSocioecono() + "") : "";
            }

            String barrio = geografico != null
                    ? ("NULL".equalsIgnoreCase(geografico.getGeoNombre() + "")
                    ? "" : geografico.getGeoNombre() + "") : "";

            Object[] header = {
                hhppMgl.getHhpId(), hhppMgl.getHhpIdrR(),
                hhppMgl.getHhpCalle(), hhppMgl.getHhpPlaca(),
                hhppMgl.getHhpApart(), estrato, nivelSocio, barrio, hhppMgl.getHhpComunidad(),
                hhppMgl.getHhpDivision(), hhppMgl.getHhpEstadoUnit(),
                hhppMgl.getHhpVendedor(), hhppMgl.getHhpCodigoPostal(),
                hhppMgl.getHhpTipoAcomet(), hhppMgl.getHhpUltUbicacion(),
                hhppMgl.getHhpHeadEnd(), hhppMgl.getHhpTipo(),
                hhppMgl.getHhpEdificio(), hhppMgl.getHhpTipoUnidad(),
                hhppMgl.getHhpTipoCblAcometida(), ""};

            retornar = header;

        }

        return retornar;
    }

    /**
     * Cargar los nombres de los filtros con que se hace el reporte.
     * <p>
     * Se carga cada uno de los valores que hacen parte del filtro en el que se
     * puede realizar reporte.
     *
     * @author becerraarmr
     * @return el String[] con los valores de los filtros.
     */
    public static String[] filtrosHeaders() {
        String[] filtrosHeaders = {"Tipo Reporte", "Tipo Tecnologia", "Departamento",
            "Ciudad", "Centro Poblado", "Nodo", "Atributo", "ValorAtributo"};
        return filtrosHeaders;
    }

    /**
     * Cargar la cabecera del archivo que se crea al realizar el reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] header() {
        String aux = "hhpId,HhpIdrR,HhpCalle,HhpPlaca,HhpApart,Estrato,Niv_Socio,"
                + "Barrio,HhpComunidad,HhpDivision,HhpEstadoUnit,HhpVendedor,HhpCodigoPostal,"
                + "HhpTipoAcomet,HhpUltUbicacion,HhpHeadEnd,HhpTipo,HhpEdificio,"
                + "HhpTipoUnidad,HhpTipoCblAcometida,Notas";

        return aux.split(",");
    }
  
    /**
     * Cargar la cabecera de direcciones
     *
     * Se prepara la cabecera de direcciones según los atributos que tiene el
     * objeto DrDireccionesMgl, exceptuando algunos campos.
     *
     * @author becerraarmr
     *
     * @return el String[] correspondiente.
     */
    public static String[] headerDirecciones() {
        Class p = DrDireccionMgl.class;
        Field[] f = p.getDeclaredFields();
        StringBuilder aux = new StringBuilder();
        for (int i = 0; i < f.length; i++) {
            Field field = f[i];
            if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Id".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdSolicitud".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Estrato".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("EstadoDirGeo".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdDirCatastro".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_primaryKey".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_listener".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_fetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_shouldRefreshFetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_session".equalsIgnoreCase(field.getName())) {
                continue;
            }
            aux.append(field.getName());
            if (i != f.length) {
                aux.append(",");
            }
        }
        return aux.toString().split(",");
    }

    /**
     * Cargar la data de HhppMgl
     * <p>
     * Recibe un vector e intenta setearlos a cada uno de los parámetros que
     * corresponden al HhppMgl
     *
     * @author becerraarmr
     * @param data vector a convertir.
     *
     * @return null o el HhppMgl correspondiente.
     */
    public static HhppMgl cargaData(String[] data) {
        if (data == null) {
            return null;//no hay data
        }
        if (data.length != header().length) {
            return null;// no tiene el mismo tamaño
        }
        BigDecimal auxHhpId = getBigDecimal(data[0]);
        if (auxHhpId == null) {
            return null;//no se validó el id del hhpp
        }
        HhppMgl hhppMgl = new HhppMgl();

        hhppMgl.setHhpId(auxHhpId);
        hhppMgl.setHhpIdrR(data[1]);
        hhppMgl.setHhpCalle(data[2]);
        hhppMgl.setHhpPlaca(data[3]);
        hhppMgl.setHhpApart(data[4]);
        hhppMgl.setHhpComunidad(data[8]);
        hhppMgl.setHhpDivision(data[9]);
        hhppMgl.setHhpEstadoUnit(data[10]);
        hhppMgl.setHhpVendedor(data[11]);
        hhppMgl.setHhpCodigoPostal(data[12]);
        hhppMgl.setHhpTipoAcomet(data[13]);
        hhppMgl.setHhpUltUbicacion(data[14]);
        hhppMgl.setHhpHeadEnd(data[15]);
        hhppMgl.setHhpTipo(data[16]);
        hhppMgl.setHhpEdificio(data[17]);
        hhppMgl.setHhpTipoUnidad(data[18]);
        hhppMgl.setHhpTipoCblAcometida(data[19]);
        return hhppMgl;
    }

    /**
     * Convertir el valor a BigDecimal
     * <p>
     * Toma un valor String y trata de convertirlo a BigDecimal
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el BigDecimal representativo.
     */
    public static BigDecimal getBigDecimal(String valor) {
        try {
            return (valor == null || valor.isEmpty()) ? null : new BigDecimal(valor);
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de convertir el valor " + valor + " a BigDecimal: " + e.getMessage();
            LOGGER.error(msg, e);

            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de convertir el valor " + valor + " a BigDecimal: " + e.getMessage();
            LOGGER.error(msg, e);

            return null;
        }
    }

    /**
     * Convertir el valor a Integer
     * <p>
     * Toma un valor String y trata de convertirlo a Integer
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el Integer representativo.
     */
    public static Integer getInteger(String valor) {
        try {
            return new Integer(valor);
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de convertir el valor " + valor + " a Integer: " + e.getMessage();
            LOGGER.error(msg, e);

            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de convertir el valor " + valor + " a Integer: " + e.getMessage();
            LOGGER.error(msg, e);

            return null;
        }
    }

    /**
     * Verificar si es cabecera
     * <p>
     * Verifica el parametro header y establece si es o no cabecera.
     *
     * @author becerraarmr
     * @param headerIn vector con la data a verificar
     *
     * @return un true o un false según la verificación.
     */
    public static boolean isHeaders(String[] headerIn) {
        if (headerIn == null) {
            return false;// no hay data
        }
        String[] aux;

        if (headerIn.length == header().length) {
            aux = header();
        } else if (headerIn.length == (headerDirecciones().length + header().length)) {
            aux = new String[headerDirecciones().length + header().length];
            int i = 0;
            for (; i < header().length; i++) {
                aux[i] = header()[i];
            }

            for (int j = 0; j < headerDirecciones().length; i++, j++) {
                aux[i] = headerDirecciones()[j];
            }
        } else {
            return false;//no tiene la cabeceras correctas
        }

        for (int i = 0; i < aux.length; i++) {
            if (!aux[i].equals(headerIn[i])) {
                return false;//Columna no válida
            }
        }
        return true;//Validada correctamente
    }

    /**
     * Cargar el SelectItem Se carga el arreglo SelectItem con los datos
     * correspondientes.
     *
     * @author becerraarmr
     * @param entities lista de entidades
     * @param selectOne si puede o no ingresar dato nulo.
     *
     * @return un SelectItem[]
     */
    public static SelectItem[] getSelectItems(
            List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            if (x instanceof CmtBasicaMgl) {
                CmtBasicaMgl aux = (CmtBasicaMgl) x;
                items[i++] = new SelectItem(aux.getBasicaId() + "", aux.getNombreBasica());
            } else if (x instanceof GeograficoPoliticoMgl) {
                GeograficoPoliticoMgl aux = (GeograficoPoliticoMgl) x;
                items[i++] = new SelectItem(aux.getGpoId() + "", aux.getGpoNombre());
            } else if (x instanceof NodoMgl) {
                NodoMgl aux = (NodoMgl) x;
                items[i++] = new SelectItem(aux.getNodId(), aux.getNodCodigo());
            } else if (x instanceof RrRegionales) {
                RrRegionales aux = (RrRegionales) x;
                items[i++] = new SelectItem(aux.getCodigo(), aux.getNombre());
            } else if (x instanceof RrCiudades) {
                RrCiudades aux = (RrCiudades) x;
                items[i++] = new SelectItem(aux.getCodigo(), aux.getNombre());
            }
        }
        return items;
    }

}