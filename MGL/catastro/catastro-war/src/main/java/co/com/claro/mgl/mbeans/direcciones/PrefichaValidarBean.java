/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author User
 */
@ManagedBean(name = "validarPrefichaBean")
@ViewScoped
public class PrefichaValidarBean {

    private static final Logger LOGGER = LogManager.getLogger(PrefichaValidarBean.class);
    private static final String FASE_GENERACION = "GENERACION";
    private static final String FASE_VALIDACION = "VALIDACION";
    private static final String EXT_FILE_XLS = "XLS";
    private static final String EXT_FILE_XLSX = "XLSX";
    private HtmlDataTable dataTable = new HtmlDataTable();
    private UploadedFile uploadedFile;
    private BigDecimal idPrefichaSelected;
    private List<PreFichaMgl> preFichaValidarList = new ArrayList<>();
    private List<PreFichaMgl> preFichaFiltradaList  = new ArrayList();    
    private List<PreFichaXlsMgl> edificiosVtXls;
    private List<PreFichaXlsMgl> casasRedExternaXls;
    private List<PreFichaXlsMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> hhppSNXls;
    private List<PreFichaXlsMgl> preFichaXlsMglList;
    private PreFichaMgl prefichaValidar;
    private String saveAvailable = String.valueOf(false);
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean seleccionarTodo = false;
    private boolean todasLasPreFichasMarcadas = false;
    private boolean hayFichasSeleccionadas = false;   
    private boolean eliminarTodoSelected = false;
    private boolean registrosFiltrados;
    private SecurityLogin securityLogin;
    /**
     *  El flag filtro aplicado se desactiva si se cambia la fecha inicial o la fecha final
     *  El flag filtro aplicado se activa al dar realizar correctamente el filtrado de la lista por el rango de fechas incluidad las fecha nullas
     *  si el filtro ha sido aplicado se pueden habilitar los botones de navegacion
     *  filtro aplicado se diferencia de registros filtrados en que incluye el rango con fechas nulas
     */
    private boolean filtroAplicado = true;
    private boolean rangoFechasValido = true;    
    
    @EJB
    PreFichaMglFacadeLocal preFichaMglFacadeLocal;  
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtTipoBasicaMgl tipoBasica;
     
    public PrefichaValidarBean() {
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
            tipoBasica = new CmtTipoBasicaMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void loadList() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage());
        }
    }

    public void habilitarCargaArchivo() {
        try {
            prefichaValidar = (PreFichaMgl) dataTable.getRowData();
        } catch (RuntimeException e) {
            LOGGER.error("Error en habilitarCargaArchivo, preficha seleccionada: " + prefichaValidar.getPrfId() + "" +e.getMessage(), e);     
        } catch (Exception e) {
            LOGGER.error("Error en habilitarCargaArchivo, preficha seleccionada: " + prefichaValidar.getPrfId() + "" +e.getMessage(), e);     
        }
    }

    public String uploadFile() {
        try {
            
            if(uploadedFile != null && uploadedFile.getFileName() != null){
                
            }else{
                return null;
            }
            int numSheetEdificiosVt = 100;
            int numSheetCasasRedExt = 100;
            int numSheetConjCsVt = 100;
            int numSheetHhppSN = 100;

            String extFile = FilenameUtils.getExtension(uploadedFile.getFileName());
            //validacion del tipo de archivo cargado
            if (!extFile.toUpperCase().contains(EXT_FILE_XLS) && !extFile.toUpperCase().contains(EXT_FILE_XLSX)) {
                String msn = "Tipo de Archivo no permitio para cargar la informacion, debe ser un archivo EXCEL ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            //validacion para determinar si el archivo cargado corresponde a la preficha seleccionada
            XSSFWorkbook workbook = new XSSFWorkbook(uploadedFile.getInputStream());
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

                XSSFSheet sheet = workbook.getSheetAt(i);
                BigDecimal idPreFichaSheet = new BigDecimal(sheet.getRow(1).getCell(0).getNumericCellValue(), MathContext.UNLIMITED);
                LOGGER.error("Preficha Sheet " + i + ": " + workbook.getSheetName(i) + " idPreficha" + idPreFichaSheet);
                if (idPrefichaSelected.compareTo(idPreFichaSheet) != 0) {
                    String msn = "La preficha de la Hoja " + workbook.getSheetName(i) + " no corresponde a la Preficha seleccionada para validar";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return null;
                }
                if (workbook.getSheetName(i).trim().equalsIgnoreCase("EDIFICIOS VT")) {
                    numSheetEdificiosVt = i;
                } else if (workbook.getSheetName(i).trim().equalsIgnoreCase("CASAS RED EXTERNA")) {
                    numSheetCasasRedExt = i;
                } else if (workbook.getSheetName(i).trim().equalsIgnoreCase("CONJ CASAS VT")) {
                    numSheetConjCsVt = i;
                } else if (workbook.getSheetName(i).trim().equalsIgnoreCase("HHPP SN")) {
                    numSheetHhppSN = i;
                }
            }
            preFichaXlsMglList = new ArrayList<>();
            //cargamos la informacion del archivo para validarla
            if (numSheetEdificiosVt != 100) {

                XSSFSheet sheetEdificiosVt = workbook.getSheetAt(numSheetEdificiosVt);
                edificiosVtXls = fillEntityListFromSheet(sheetEdificiosVt);
            }
            if (numSheetCasasRedExt != 100) {
                XSSFSheet sheetCasasRedExterna = workbook.getSheetAt(numSheetCasasRedExt);
                casasRedExternaXls = fillEntityListFromSheet(sheetCasasRedExterna);
            }
            if (numSheetConjCsVt != 100) {
                XSSFSheet sheetConjCasasVt = workbook.getSheetAt(numSheetConjCsVt);
                conjCasasVtXls = fillEntityListFromSheet(sheetConjCasasVt);
            }
            if (numSheetHhppSN != 100) {
                XSSFSheet sheethhppSN = workbook.getSheetAt(numSheetHhppSN);
                hhppSNXls = fillEntityListFromSheet(sheethhppSN);
            }
            idPrefichaSelected = null;
            saveAvailable = String.valueOf(true);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. En el cargue del archivo, revise el formato de archivo cargado", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. En el cargue del archivo, revise el formato de archivo cargado", e, LOGGER);
        }
        return null;

    }

    private List<PreFichaXlsMgl> fillEntityListFromSheet(XSSFSheet sheet) {
        
        List<PreFichaXlsMgl> resultList = new ArrayList<PreFichaXlsMgl>();
        Row lastRow = sheet.getRow(sheet.getLastRowNum());
        sheet.removeRow(lastRow);
        //obtemenos el nombre de la hoja 
        String nameSheet = sheet.getSheetName();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //avanzamos la fila 0 que contiene la informacion del nodo
        rowIterator.next();
        //avanzamos la fila 1 que contiene el titulo
        rowIterator.next();
        //avanzamos la fila 2 que contiene que contiene los encabezados
        Row rowCabecera = rowIterator.next();
        int lastColumnas = rowCabecera.getLastCellNum();
        if (!isCellFill(rowCabecera.getCell(lastColumnas))){
            lastColumnas -= 1;
        }
        int numeroColInfoAntes = (lastColumnas / 2)+1;

        while (rowIterator.hasNext()) {
            PreFichaXlsMgl preFichaXlsMglOriginal = new PreFichaXlsMgl();
            Row row = rowIterator.next();
            //obetenemos la informacion para asignarla a una entidad
            for (int i = 0; i < numeroColInfoAntes; i++) {
                LOGGER.error("Procesando Original: " + nameSheet + " fila: " + row.getRowNum() + " cell: " + i);
                Cell cell = row.getCell(i);

                int j = i;
                if (nameSheet.equalsIgnoreCase("CASAS RED EXTERNA") && i >= 7) {
                    j = i + 1;
                }
                switch (j) {
                    case 0:
                        preFichaXlsMglOriginal.setIdPfx(getDoubleValueFromCell(cell));
                        break;
                    case 1:
                        preFichaXlsMglOriginal.setNombreConj(getStringValueFromCell(cell));
                        break;
                    case 2:
                        preFichaXlsMglOriginal.setViaPrincipal(getStringValueFromCell(cell));
                        break;
                    case 3:
                        preFichaXlsMglOriginal.setPlaca(getStringValueFromCell(cell));
                        break;
                    case 4:
                        preFichaXlsMglOriginal.setTotalHHPP(getDoubleValueFromCell(cell));
                        break;
                    case 5:
                        preFichaXlsMglOriginal.setAptos(getDoubleValueFromCell(cell));
                        break;
                    case 6:
                        preFichaXlsMglOriginal.setLocales(getDoubleValueFromCell(cell));
                        break;
                    case 7:
                        preFichaXlsMglOriginal.setOficinas(getDoubleValueFromCell(cell));
                        break;
                    case 8:
                        preFichaXlsMglOriginal.setPisos(getDoubleValueFromCell(cell));
                        break;
                    case 9:
                        preFichaXlsMglOriginal.setBarrio(getStringValueFromCell(cell));
                        break;
                    case 10:
                        preFichaXlsMglOriginal.setDistribucion(getStringValueFromCell(cell));
                        break;
                    case 11:
                            preFichaXlsMglOriginal.setNodo(getStringValueFromCell(cell));
                        break;
                    case 12:
                            preFichaXlsMglOriginal.setClasificacion(getStringValueFromCell(cell));
                        break;
                    case 13:
                        preFichaXlsMglOriginal.setIdTipoDireccion(getStringValueFromCell(cell));
                        break;
                    default:
                        LOGGER.error("Columna sin procesar: " + i + " contenido: " + cell.getStringCellValue());
                        break;
                }


            }
            preFichaXlsMglOriginal.setPrfId(idPrefichaSelected);
            preFichaXlsMglOriginal.setSelected(true);
            preFichaXlsMglOriginal.setModified(false);
            preFichaXlsMglOriginal.setFase(FASE_VALIDACION);
            //validamos si existen modificaciones al registro procesado anteriormente
            PreFichaXlsMgl preFichaXlsMglModified;

            if (validarCambio(row, lastColumnas, numeroColInfoAntes)) {
                
                preFichaXlsMglModified = copyEntity(preFichaXlsMglOriginal);
                //asignamos el id 
                preFichaXlsMglModified.setIdPfx(new BigDecimal(row.getCell(0).getNumericCellValue()));

                for (int i = numeroColInfoAntes; i <= lastColumnas; i++) {
                    LOGGER.error("Procesando Modificacion: " + nameSheet + " fila: " + row.getRowNum() + " cell: " + i);
                    Cell cell = row.getCell(i);

                    int j = i - numeroColInfoAntes + 1;
                    if (nameSheet.equalsIgnoreCase("CASAS RED EXTERNA") && j >= 7) {
                        j = j + 1;
                    }
                    String valueStr = "";
                    switch (j) {
                        case 1:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setNombreConj(valueStr);
                            }
                            break;
                        case 2:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setViaPrincipal(valueStr);
                            }
                            break;
                        case 3:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setPlaca(valueStr);
                            }
                            break;
                        case 4:
                            BigDecimal valueCell4 = getDoubleValueFromCell(cell);
                            if (valueCell4 != null) {
                                preFichaXlsMglModified.setTotalHHPP(valueCell4);
                            }
                            break;
                        case 5:
                            BigDecimal valueCell5 = getDoubleValueFromCell(cell);
                            if (valueCell5 != null) {
                                preFichaXlsMglModified.setAptos(valueCell5);
                            }
                            break;
                        case 6:
                            BigDecimal valueCell6 = getDoubleValueFromCell(cell);
                            if (valueCell6 != null) {
                                preFichaXlsMglModified.setLocales(valueCell6);
                            }
                            break;
                        case 7:
                            BigDecimal valueCell7 = getDoubleValueFromCell(cell);
                            if (valueCell7 != null) {
                                preFichaXlsMglModified.setOficinas(valueCell7);
                            }
                            break;
                        case 8:
                            BigDecimal valueCell8 = getDoubleValueFromCell(cell);
                            if (valueCell8 != null) {
                                preFichaXlsMglModified.setPisos(valueCell8);
                            }
                            break;
                        case 9:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setBarrio(valueStr);
                            }
                            break;
                        case 10:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setDistribucion(valueStr);
                            }
                            break;
                        case 11:
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setNodo(valueStr);
                            }
                            break;
                        case 12:
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setClasificacion(valueStr);
                            }
                            break;
                        case 13:
                            valueStr = "";
                            valueStr = getStringValueFromCell(cell);
                            if (!valueStr.trim().isEmpty()) {
                                preFichaXlsMglModified.setIdTipoDireccion(valueStr);
                            }
                            break;
                        
                    }


                }
                preFichaXlsMglOriginal.setSelected(false);
                
                preFichaXlsMglModified.setPrfId(idPrefichaSelected);
                preFichaXlsMglModified.setSelected(true);
                preFichaXlsMglModified.setModified(true);
                setClasificacion(preFichaXlsMglModified, nameSheet);
                resultList.add(preFichaXlsMglModified);
                if(preFichaXlsMglModified.isSelected()){
                    preFichaXlsMglList.add(preFichaXlsMglModified);
                }
                
            }
            resultList.add(preFichaXlsMglOriginal);
            if (preFichaXlsMglOriginal.isSelected()){
                preFichaXlsMglList.add(preFichaXlsMglOriginal);
            }
            
        }
        
        return resultList;
    }
    
    private boolean validarCambio(Row row, int lastColumn, int numeroColInfoAntes) {
        boolean result = false;

        for (int i = numeroColInfoAntes + 1; i <= lastColumn; i++) {
            Cell cell = row.getCell(i);
            result = isCellFill(cell);
            if (result) {
                break;
            }
        }
        return result;
    }

    private boolean isCellFill(Cell cell) {
        boolean result = false;
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String valueStr = "";
            valueStr = cell.getStringCellValue();
            if (!valueStr.trim().isEmpty()) {
                result = true;
            }
        } else if (cell != null &&  cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double valueDouble = -1.0;
            valueDouble = cell.getNumericCellValue();
            if (valueDouble != -1.0) {
                result = true;
            }
        }
        return result;
    }

    private String getStringValueFromCell(Cell cell) {
        String valueStr = "";
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
            valueStr = cell.getStringCellValue();
        }
        return valueStr;
    }

    private BigDecimal getDoubleValueFromCell(Cell cell) {
        BigDecimal resutl = null;
        double valueDouble = -10000.0;
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            valueDouble = cell.getNumericCellValue();
        }
        if (valueDouble != -10000.0) {
            resutl = new BigDecimal(valueDouble);
        }
        return resutl;
    }
    
    private PreFichaXlsMgl copyEntity (PreFichaXlsMgl original){
        PreFichaXlsMgl copy = new PreFichaXlsMgl();
        
        copy.setAptos(original.getAptos());
        copy.setBarrio(original.getBarrio());
        copy.setClasificacion(original.getClasificacion());
        copy.setDistribucion(original.getDistribucion());
        copy.setDetalleHHPPList(original.getDetalleHHPPList());
        copy.setDireccionStrList(original.getDireccionStrList());
        copy.setDrDireccionList(original.getDrDireccionList());
        copy.setFase(original.getFase());
        copy.setId(original.getId());
        copy.setIdPfx(original.getIdPfx());
        copy.setIdTipoDireccion(original.getIdTipoDireccion());
        copy.setLocales(original.getLocales());
        copy.setNombreConj(original.getNombreConj());
        copy.setOficinas(original.getOficinas());
        copy.setPisos(original.getPisos());
        copy.setPlaca(original.getPlaca());
        copy.setTotalHHPP(original.getTotalHHPP());
        copy.setViaPrincipal(original.getViaPrincipal());
        copy.setModified(false);
        return copy;
        
    }
    
    private void setClasificacion (PreFichaXlsMgl preFichaXlsMgl, String nameSheet){
        
        if(nameSheet.equalsIgnoreCase("EDIFICIOS VT")){
            preFichaXlsMgl.setClasificacion("EDIFICIOSVT");
        }else if(nameSheet.equalsIgnoreCase("CASAS RED EXTERNA")){
             preFichaXlsMgl.setClasificacion("CASASREDEXTERNA");
        }else if(nameSheet.equalsIgnoreCase("CONJ CASAS VT")){
            preFichaXlsMgl.setClasificacion("CONJUNTOCASASVT");
        }else if(nameSheet.equalsIgnoreCase("HHPP SN")){
            preFichaXlsMgl.setClasificacion("HHPPSN");
        }
    }
    
    public void guardarPreficha() throws ApplicationException {
        //cambiamos de estado la preficha
        prefichaValidar.setPrfFechaValidacion(new Date());
        prefichaValidar.setPrfUsuarioValidacion("HITSS");
        prefichaValidar.setFase(FASE_VALIDACION);
        
        prefichaValidar = preFichaMglFacadeLocal.updatePreFicha(prefichaValidar);
        
        preFichaXlsMglList = preFichaMglFacadeLocal.savePrefichaXlsList(prefichaValidar, preFichaXlsMglList);
        if (preFichaXlsMglList != null && preFichaXlsMglList.size() >0 && prefichaValidar != null) {
            idPrefichaSelected = null;
            prefichaValidar = null;
            preFichaXlsMglList = null;
            saveAvailable = String.valueOf(false); 
            String msn = "La informacion de la preficha ha sido cargada existosamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        }
        
        
        
    }
    
    
     private String listInfoByPage(int page) {
        try {
            
            List<String> faseList = new ArrayList<>();
            faseList.add(FASE_GENERACION);
            preFichaValidarList = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList,page,ConstantsCM.PAGINACION_DIEZ_FILAS, true, fechaInicial,fechaFinal);
            if (todasLasPreFichasMarcadas) {
                marcarListaFichas();
            }
            
            actual = page;
            
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error cargando lista en PrefichaBean: listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaBean: pageFirst() " + ex.getMessage(), ex);
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
              FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: pagePrevious() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaBean: pagePrevious() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en PrefichaBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: irPagina() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: pageNext() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: pageLast() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaBean: pageLast() "+ ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            List<String> faseList = new ArrayList<>();
            faseList.add(FASE_GENERACION);
            List<PreFichaMgl> preFichaValidarListContar = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList,0,0, false,fechaInicial,fechaFinal);
            int pageSol = preFichaValidarListContar.size();
            
            int totalPaginas = (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
            return totalPaginas;
        } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaBean: getPageActual() " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void filtrarLista() {
        eliminarTodoSelected = false;
        todasLasPreFichasMarcadas = false;
        hayFichasSeleccionadas = false;
        registrosFiltrados = false;
        filtroAplicado = false;        
        
        if (fechaInicial != null && fechaFinal != null) {

            if (fechaInicial.compareTo(fechaFinal) > 0) {
                String msn = "La fecha inicial debe ser anterior a la final.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                
                pageFirst();
                this.registrosFiltrados = true;
                this.filtroAplicado = true;                

                if (preFichaValidarList == null || preFichaValidarList.isEmpty()) {
                    String msn = "No se encontraron registros para el rango de fechas suministrado.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, msn, ""));
                }
            }

        } else {
            if (fechaInicial == null && fechaFinal == null) {
                pageFirst();
               this.filtroAplicado = true;
            } else {
                String msn = "Por favor, indique las fechas a filtrar con el formato dd/mm/yyyy";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
                
            }
        }
        seleccionarTodo = false;

    }
    
    public String getSelectedFichasPorEliminar() {
        
        try {
            if (preFichaValidarList != null && !preFichaValidarList.isEmpty()) {
                if (todasLasPreFichasMarcadas) {
                    for (PreFichaMgl ficha : preFichaFiltradaList) {

                        ficha.setEstadoRegistro(0);
                        preFichaMglFacadeLocal.updatePreFicha(ficha);

                    }
                    seleccionarTodo = false;

                } else {
                    for (PreFichaMgl ficha : getPreFichaValidarList()) {
                        if (ficha.isSelected()) {

                            ficha.setEstadoRegistro(0);
                            preFichaMglFacadeLocal.updatePreFicha(ficha);

                        }

                    }
                }
                String msn = "La eliminacion ha sido exitosa.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
                todasLasPreFichasMarcadas = false;
                hayFichasSeleccionadas = false;
                eliminarTodoSelected = false;

            } else {
                String msn = "Marque primero las prefichas a Eliminar.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, msn, ""));
            }                        
           
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarFichas. " + e.getMessage(), e, LOGGER);
        }
        fechaInicial = null;
        fechaFinal = null;
        pageFirst();
        return null;
    }
    
    public String evaluarSiHayFichasMarcadas() {
        hayFichasSeleccionadas = false;
        for (PreFichaMgl ficha : preFichaValidarList) {
            if (ficha.isSelected()) {
                hayFichasSeleccionadas = true;
                break;
            }

        }
        
        return null;
    }
    
    public String marcarTodo() {
        
        todasLasPreFichasMarcadas = true;
       
        try {

            if (fechaInicial != null && fechaFinal != null) {
                seleccionarTodo = !seleccionarTodo;
                

                List<String> faseList = new ArrayList<>();
                faseList.add(FASE_GENERACION);
                preFichaFiltradaList = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList, 0, 0, false, fechaInicial, fechaFinal);
                if (preFichaFiltradaList.size()!=0) {
                    hayFichasSeleccionadas = seleccionarTodo;
                }
                marcarListaFichas();
            } else {
                    String msn = "Por favor, indique las fechas a filtrar";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, msn, ""));
                    eliminarTodoSelected = false;
                    todasLasPreFichasMarcadas = false;
                    hayFichasSeleccionadas = false;
                }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en marcarTodo. " + e.getMessage(), e, LOGGER);
        }
        return null;

    }

        public void fechaAlterada() {
        filtroAplicado = false;
        rangoFechasValido = false;

        if (fechaInicial == null && fechaFinal == null) {
            rangoFechasValido = true;
        } else {

            if (fechaInicial != null && fechaFinal != null) {

                if (fechaInicial.compareTo(fechaFinal) <= 0) {

                    rangoFechasValido = true;
                } 

            } 

        }
    }

    public boolean isTodasLasPreFichasMarcadas() {
        return todasLasPreFichasMarcadas;
    }

    public void setTodasLasPreFichasMarcadas(boolean todasLasPreFichasMarcadas) {
        this.todasLasPreFichasMarcadas = todasLasPreFichasMarcadas;
    }

    private void marcarListaFichas() {
        getPreFichaValidarList().forEach((ficha) -> {
            ficha.setSelected(seleccionarTodo);
        });
    }    
    
    public List<PreFichaMgl> getPreFichaValidarList() {
        return preFichaValidarList;
    }

    public void setPreFichaValidarList(List<PreFichaMgl> preFichaValidarList) {
        this.preFichaValidarList = preFichaValidarList;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public BigDecimal getIdPrefichaSelected() {
        return idPrefichaSelected;
    }

    public void setIdPrefichaSelected(BigDecimal idPrefichaSelected) {
        this.idPrefichaSelected = idPrefichaSelected;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<PreFichaXlsMgl> getEdificiosVtXls() {
        return edificiosVtXls;
    }

    public void setEdificiosVtXls(List<PreFichaXlsMgl> edificiosVtXls) {
        this.edificiosVtXls = edificiosVtXls;
    }

    public List<PreFichaXlsMgl> getCasasRedExternaXls() {
        return casasRedExternaXls;
    }

    public void setCasasRedExternaXls(List<PreFichaXlsMgl> casasRedExternaXls) {
        this.casasRedExternaXls = casasRedExternaXls;
    }

    public List<PreFichaXlsMgl> getConjCasasVtXls() {
        return conjCasasVtXls;
    }

    public void setConjCasasVtXls(List<PreFichaXlsMgl> conjCasasVtXls) {
        this.conjCasasVtXls = conjCasasVtXls;
    }

    public List<PreFichaXlsMgl> getHhppSNXls() {
        return hhppSNXls;
    }

    public void setHhppSNXls(List<PreFichaXlsMgl> hhppSNXls) {
        this.hhppSNXls = hhppSNXls;
    }

    public List<PreFichaXlsMgl> getPreFichaXlsMglList() {
        return preFichaXlsMglList;
    }

    public void setPreFichaXlsMglList(List<PreFichaXlsMgl> preFichaXlsMglList) {
        this.preFichaXlsMglList = preFichaXlsMglList;
    }

    public String getSaveAvailable() {
        return saveAvailable;
    }

    public void setSaveAvailable(String saveAvailable) {
        this.saveAvailable = saveAvailable;
    }
    
     public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }
    
     public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isHayFichasSeleccionadas() {
        return hayFichasSeleccionadas;
    }

    public void setHayFichasSeleccionadas(boolean hayFichasSeleccionadas) {
        this.hayFichasSeleccionadas = hayFichasSeleccionadas;
    }
     
    public boolean isEliminarTodoSelected() {
        return eliminarTodoSelected;
    }

    public void setEliminarTodoSelected(boolean eliminarTodoSelected) {
        this.eliminarTodoSelected = eliminarTodoSelected;
    }

    public boolean isRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(boolean registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }
    
    public boolean isFiltroAplicado() {
        return filtroAplicado;
    }

    public void setFiltroAplicado(boolean filtroAplicado) {
        this.filtroAplicado = filtroAplicado;
    }

    public boolean isRangoFechasValido() {
        return rangoFechasValido;
    }

    public void setRangoFechasValido(boolean rangoFechasValido) {
        this.rangoFechasValido = rangoFechasValido;
    }    
    
    
    //Opciones agregadas para Rol
    private final String BTNVALFIC = "BTNVALFIC";
    private final String BTNELFIC = "BTNELFIC";    
    
        
    // Validar Opciones por Rol    
    public boolean validarOpcionValidarPreficha() {
        return validarEdicionRol(BTNVALFIC);
    }
    
    public boolean validarOpcionEliminarPreficha() {
        return validarEdicionRol(BTNELFIC);
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
