/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.mbeans.preficha.util.PrefichaUtil;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.mbeans.util.VerificadorExpresiones;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 * @author User
 */
@ManagedBean(name = "prefichaBean")
@ViewScoped
public class PrefichaBean {

    private static final Logger LOGGER = LogManager.getLogger(PrefichaBean.class);
    private static final String FASE_GENERACION = "GENERACION";
    private static final String EXT_FILE = "TXT";
    private String ciudadStr;
    private String dptoStr;
    private String nodoStr;
    private NodoMgl nodoSelected;
    private List<GeograficoPoliticoMgl> dptoList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<NodoMgl> nodoList;
    private List<PreFichaTxtMgl> preFichaTxtList;
    private List<PreFichaTxtMgl> edificiosVt;
    private List<PreFichaTxtMgl> casasRedExterna;
    private List<PreFichaTxtMgl> conjCasasVt;
    private List<PreFichaTxtMgl> hhppSN;
    private List<PreFichaXlsMgl> edificiosVtXls;
    private List<PreFichaXlsMgl> casasRedExternaXls;
    private List<PreFichaXlsMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> hhppSNXls;
    private List<PreFichaXlsMgl> preFichaXlsList;
    private UploadedFile uploadedFile;
    private String fileName;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private List<PreFichaMgl> preFichaValidarList = new ArrayList();
    private List<PreFichaMgl> preFichaFiltradaList = new ArrayList();
    private String downloadAvailable;
    private boolean uploadAvailable;
    private boolean generarPrefichaAvailable;
    private String guardarPrefichaAvailable;
    private BigDecimal idPrefichaSelected;
    private String isValidPreficha;
    private String isValidTxt;
    private HttpServletRequest httpRequest = FacesUtil.getServletRequest();
    private HttpSession httpSession = httpRequest.getSession();
    private List<CmtBasicaMgl> tecnologiaList;
    private String tecnologiasStr;
    private List<GeograficoPoliticoMgl> centrosPobladosList;
    private String centroPobladoStr;
    private String tipoDireccion;
    private List<String> corregimientosList;
    private String corregimientoStr;
    private GeograficoPoliticoMgl centroObj;
    private GeograficoPoliticoMgl ciudadObj;
    private boolean tieneCorregimiento = false;
    private boolean showPanelTxtCargado = true;
    private List<String> listadoObservacionesArchivo;
    private List<String> lineasArchivo;
    private int cantidadObservaciones;

    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private Date fechaInicial;
    private Date fechaFinal;
    private boolean seleccionarTodo = false;
    /**
     * Flag que determina si los registros han sido filtrados.
     */
    private boolean registrosFiltrados = false;
    /**
     * Mapa que contiene el lista de registros que ser&aacute;n marcados para
     * eliminar.
     */
    private Map<BigDecimal, Boolean> mapRegistrosMarcadosEliminar = new HashMap<>();
    private boolean todasLasPreFichasMarcadas = false;
    private boolean hayFichasSeleccionadas = false;
    private boolean eliminarTodoSelected = false;
    /**
     * El flag filtro aplicado se desactiva si se cambia la fecha inicial o la
     * fecha final El flag filtro aplicado se activa al dar realizar
     * correctamente el filtrado de la lista por el rango de fechas incluidad
     * las fecha nullas si el filtro ha sido aplicado se pueden habilitar los
     * botones de navegacion filtro aplicado se diferencia de registros
     * filtrados en que incluye el rango con fechas nulas
     */
    private boolean filtroAplicado = true;
    private boolean rangoFechasValido = true;

    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    /**
     * Para buscar los parámetros configurados para las expresioens regulares
     */
    @EJB
    private ParametrosMglFacadeLocal parametrosMglManager;
    private List<PreFichaTxtMgl> preFichaTxtListDescarga;
    private List<PreFichaTxtMgl> edificiosVtTxtDesc;
    private List<PreFichaTxtMgl> casasRedExternaTxtDesc;
    private List<PreFichaTxtMgl> conjCasasVtTxtDesc;
    private List<PreFichaTxtMgl> hhppSNTxtDesc;

    private void distribuirMultiplaca(List<PreFichaTxtMgl> preFichaTxtListMultiPlaca) {
        //FCP Validar que se aplica una multiplaca mas 
        if (preFichaTxtListMultiPlaca == null) {
            return;
        }
        PreFichaTxtMgl placaInicial = preFichaTxtListMultiPlaca.get(0);
        if (!(placaInicial.getPisos().intValue() > 3 && placaInicial.getBlockName().equals("N_Casa".toUpperCase().toUpperCase())
                || placaInicial.getBlockName().toUpperCase().equals("N_Edificio".toUpperCase()))) {

            int totalAptos = placaInicial.getAptos().intValue();
            int totalLocales = placaInicial.getLocales().intValue();
            for (int i = 0; i < preFichaTxtListMultiPlaca.size(); i++) {
                PreFichaTxtMgl placaHija = preFichaTxtListMultiPlaca.get(i);
                placaHija.setDistribucion("");
                if (totalAptos != 0) {
                    placaHija.setAptos(BigDecimal.ONE);
                    placaHija.setLocales(BigDecimal.ZERO);
                    totalAptos--;
                } else if (totalLocales != 0) {
                    placaHija.setAptos(BigDecimal.ZERO);
                    placaHija.setLocales(BigDecimal.ONE);
                    totalLocales--;
                } else {
                    preFichaTxtList.remove(placaHija);
                }
            }
            placaInicial.setAptos(placaInicial.getAptos().add(new BigDecimal(totalAptos)));
            placaInicial.setLocales(placaInicial.getLocales().add(new BigDecimal(totalLocales)));
            placaInicial.setDistribucion("");
        }
    }

    public static enum COLUMN_NAME {

        PLACAUNIDA, BARRIO, HANDLE, PISOS, AMP, NO, DISTRIBUCION, OFICINAS,
        PLACAUNIDAANT2, PLACAUNIDAANT3, LOCALES, INT, NOMBRE_ED, PI, NOMBRECALLANT3,
        NOMBRECALL, NOMBRECALLANT2, BLOCKNAME, NUMINT, NOMBRE_CONJ, APTOS, NUM_CASAS
    }

    public static enum DIST_TYPE {

        LC, AP, IN, BL
    }

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    public PrefichaBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en PrefichaBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en PrefichaBean. ", e, LOGGER);
        }
        isValidPreficha = String.valueOf(false);
        volver();
    }

    @PostConstruct
    public void loadList() {
        try {
            dptoList = new ArrayList<>();
            dptoList = geograficoPoliticoMglFacadeLocal.findDptos();
            listInfoByPage(1);
            loadTechnology();
        } catch (ApplicationException e) {
            LOGGER.error("Error en loadList. " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage());
        }
    }

    public void loadCities() {
        try {
            nodoList = null;
            limpiarListas();
            if (dptoStr.equalsIgnoreCase("0")) {
                ciudadList = null;
            } else {
                ciudadList = new ArrayList<>();
                ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(dptoStr));

            }
            centroPobladoStr = "";
            centrosPobladosList = new ArrayList<>();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadCities. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadCities. ", e, LOGGER);
        }
    }

    public void loadTechnology() {
        try {
            limpiarListas();
            tecnologiaList = new ArrayList<>();

            tecnologiaList = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA));

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadTechnology. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadTechnology. ", e, LOGGER);
        }
    }

    public void loadCenters() {
        try {
            limpiarListas();
            if (ciudadStr.equalsIgnoreCase("0")) {
                ciudadStr = null;
                centroPobladoStr = "0";
                centrosPobladosList = new ArrayList<>();
            } else {
                ciudadObj = geograficoPoliticoMglFacadeLocal.findById(new BigDecimal(ciudadStr));
                centrosPobladosList = new ArrayList<>();
                centrosPobladosList = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(new BigDecimal(ciudadStr));

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadCenters. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadCenters. ", e, LOGGER);
        }
    }

    public void loadNodeCenters() {
        try {
            limpiarListas();
            if (centroPobladoStr == null || centroPobladoStr.isEmpty()
                    || centroPobladoStr.equalsIgnoreCase("0")) {
                nodoList = null;
                centroPobladoStr = "";
                centrosPobladosList = null;
            } else {
                try {
                    centroObj = geograficoPoliticoMglFacadeLocal.findById(new BigDecimal(centroPobladoStr));
                } catch (NumberFormatException | ApplicationException e) {
                    LOGGER.error("Error numérico en el centroPoblado " + centroPobladoStr, e);
                    centroObj = null;
                }
                nodoList = new ArrayList<>();
                CmtBasicaMgl tec = cmtBasicaMglFacadeLocal.findById(new BigDecimal(tecnologiasStr));
                nodoList = nodoMglFacadeLocal.findNodosCentroPobladoAndTipoTecnologia(new BigDecimal(centroPobladoStr), tec);

            }
            if (centroObj != null && centroObj.getCorregimiento() != null
                    && centroObj.getCorregimiento().equalsIgnoreCase("Y")
                    && ciudadObj != null && ciudadObj.getCorregimiento() != null
                    && ciudadObj.getCorregimiento().equalsIgnoreCase("Y")) {
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadNodeCenters. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadNodeCenters. ", e, LOGGER);
        }
    }

    public void loadCorregimientos() {
        try {
            limpiarListas();
            if (centroPobladoStr.equalsIgnoreCase("0")) {
            } else {

                String corregimientos = parametrosMglFacadeLocal.findByAcronimo(Constant.CORREGIMIENTO_FICHAS).get(0).getParValor();
                String[] corre = corregimientos.split("\\|");
                corregimientosList = Arrays.asList(corre);

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadCorregimientos. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadCorregimientos. ", e, LOGGER);
        }
    }

    public void volver() {
        downloadAvailable = "false";
        uploadAvailable = true;
        generarPrefichaAvailable = false;
        guardarPrefichaAvailable = "false";
        isValidPreficha = String.valueOf(false);
        limpiarListas();
        tecnologiasStr = "";
        dptoStr = "";
        ciudadStr = "";
        centroPobladoStr = "";
        nodoStr = "";
        tipoDireccion = "";
        cantidadObservaciones = 0;
        lineasArchivo = new ArrayList<>();
        listadoObservacionesArchivo = new ArrayList<>();
    }

    public String uploadFile() {

        BufferedReader in = null;
        limpiarListas();
        isValidPreficha = String.valueOf(false);
        lineasArchivo = new ArrayList<>();
        cantidadObservaciones = 0;
        try {
            if (tecnologiasStr == null || tecnologiasStr.isEmpty() || tecnologiasStr.equals("0")
                    || dptoStr == null || dptoStr.isEmpty() || dptoStr.equals("0")
                    || ciudadStr == null || ciudadStr.isEmpty() || ciudadStr.equals("0")
                    || centroPobladoStr == null || centroPobladoStr.isEmpty() || centroPobladoStr.equals("0")
                    || nodoStr == null || nodoStr.isEmpty() || nodoStr.equals("0")) {
                String msn = "Aun quedan campos obligatorios por diligenciar.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {

                if (uploadedFile != null && uploadedFile.getFileName() != null) {
                    fileName = FilenameUtils.getName(uploadedFile.getFileName());
                    String extFile = FilenameUtils.getExtension(uploadedFile.getFileName());
                    if (!extFile.toUpperCase().contains(EXT_FILE)) {
                        String msn = "tipo de Archivo no permitio para cargar la informacion, debe ser un archivo TXT ";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        return null;
                    }
                } else {
                    return null;
                }

                in = new BufferedReader(new InputStreamReader(uploadedFile.getInputStream(), "ISO-8859-1"));
                String cabeceraFile = in.readLine().toUpperCase();
                lineasArchivo.add(cabeceraFile);
                Map<String, Object> cabecera = new HashMap<>();

                if (!cabeceraFile.isEmpty()) {
                    String[] cabPrev = cabeceraFile.split("\t");
                    for (int i = 0; i < cabPrev.length; i++) {
                        if ("NO.".equalsIgnoreCase(cabPrev[i])) {
                            cabecera.put("NO", i);
                        } else {
                            cabecera.put(cabPrev[i], i);
                        }
                    }
                    if (cabPrev.length != cabecera.size()){
                        throw new ApplicationException("Existen columnas duplicadas");
                    }
                }


                preFichaTxtList = new ArrayList<>();
                //cargamos la informacion del txt en una lista de objetos
                LOGGER.error("Inicia el cargue de archivo TXT");
                isValidTxt = String.valueOf(true);
                while (in.ready()) {
                    String inputText = in.readLine().toUpperCase();
                    lineasArchivo.add(inputText.toUpperCase());
                    if (!inputText.isEmpty()) {
                        String[] strSplit = inputText.split("\t", cabecera.size());

                        int indexPlaca = (Integer) cabecera.get("PLACAUNIDA");
                        String placa = strSplit[indexPlaca];

                        String auxPlaca;
                        String[] cantPlacas;

                        if (!placa.contains("S/N") && !placa.contains("S-N")) {
                            cantPlacas = placa.substring(placa.indexOf("-") + 1).split("/");
                        } else {
                            cantPlacas = new String[]{placa.substring(placa.indexOf("-") + 1)};
                        }

                        PreFichaTxtMgl preFichaTxtMgl;
                        boolean isMultiPlaca = false;
                        List<PreFichaTxtMgl> preFichaTxtListMultiPlaca = null;
                        if (cantPlacas.length > 1) {
                            isMultiPlaca = true;
                            preFichaTxtListMultiPlaca = new ArrayList<>();
                        }

                        for (int i = 0; i < cantPlacas.length; i++) {
                            auxPlaca = placa.substring(0, placa.indexOf("-") + 1) + cantPlacas[i];
                            strSplit[indexPlaca] = auxPlaca;
                            preFichaTxtMgl = fillEntityPreficha(strSplit, cabecera);
                            if ((preFichaTxtMgl.getPisos().intValue() > 3 && preFichaTxtMgl.getBlockName().equalsIgnoreCase("N_Casa".toUpperCase()))
                                    || preFichaTxtMgl.getBlockName().equalsIgnoreCase("N_Edificio".toUpperCase()) && cantPlacas.length > 1) {
                                preFichaTxtMgl.setPlacaUnida(placa);
                                i = cantPlacas.length + 2;
                                isMultiPlaca = false;
                            } else if (isMultiPlaca) {
                                preFichaTxtListMultiPlaca.add(preFichaTxtMgl);
                            }
                            preFichaTxtList.add(preFichaTxtMgl);
                        }
                        if (isMultiPlaca) {
                            distribuirMultiplaca(preFichaTxtListMultiPlaca);
                        }
                    }
                }

                LOGGER.error("Fin cargue de archivo TXT");
                if (preFichaTxtList.size() > 0) {
                    if (isValidTxt.equalsIgnoreCase(String.valueOf(true))) {
                        listadoObservacionesArchivo = new ArrayList<>();
                        validateInfoFromTxt(preFichaTxtList);
                        preFichaTxtList.sort(new PreFichaTxtMgl());
                        if (isValidPreficha.equalsIgnoreCase(String.valueOf(true))) {
                            //se clasifica la informacion en los diferentes tipos de las pestañas de la preficha
                            clasificarInfoTxt();

                            downloadAvailable = "false";
                            uploadAvailable = false;
                            guardarPrefichaAvailable = "false";
                            generarPrefichaAvailable = true;
                        } else if (cantidadObservaciones > 0) {
                            generarPrefichaAvailable = false;
                            String msn = "El archivo TXT cargado no tiene la estructura adecuada, por favor revise las observaciones y realice la corrección pertinente";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        } else {
                            generarPrefichaAvailable = false;
                            String msn = "El archivo TXT cargado no tiene la estructura adecuada, por favor revise las observaciones y realice la corrección pertinente";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        }

                    } else {
                        generarPrefichaAvailable = false;
                        String msn = "El archivo TXT cargado no tiene la estructura adecuada, por favor revise las observaciones y realice la corrección pertinente";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                } else {
                    generarPrefichaAvailable = false;
                    String msn = "El archivo TXT cargado no contiene registros a procesar";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            }
        } catch (ApplicationException | IOException | NullPointerException e) {
            String msn;
            if (e instanceof NullPointerException) {
                msn = "Error al Cargar el Archivo: Estructura Invalida ";
            } else if (e instanceof ApplicationException){
                msn = "Error al Cargar el Archivo: " +  e.getMessage();
            } else {
                msn = "Error al Cargar el Archivo";
            }
            FacesUtil.mostrarMensajeError(msn, e, LOGGER);
        } catch (Exception e) {
            String msn = "Error en uploadFile al Cargar el Archivo: ";
            FacesUtil.mostrarMensajeError(msn, e, LOGGER);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LOGGER.error("Error cerrando BufferedReader" + e.getMessage());
            }
        }

        return null;
    }

    public void generarPreficha() {

        edificiosVtXls = null;
        casasRedExternaXls = null;
        conjCasasVtXls = null;
        hhppSNXls = null;
        preFichaXlsList = new ArrayList<>();
        showPanelTxtCargado = false;

        if (edificiosVt != null && edificiosVt.size() > 0) {
            edificiosVtXls = listProcessEdificiosVt(edificiosVt);
            preFichaXlsList.addAll(edificiosVtXls);
        }
        if (casasRedExterna != null && casasRedExterna.size() > 0) {
            casasRedExternaXls = listProcessCasasRedExterna(casasRedExterna);
            preFichaXlsList.addAll(casasRedExternaXls);
        }
        if (conjCasasVt != null && conjCasasVt.size() > 0) {
            conjCasasVtXls = listProcessConjCasasVt(conjCasasVt);
            preFichaXlsList.addAll(conjCasasVtXls);
        }
        if (hhppSN != null && hhppSN.size() > 0) {
            hhppSNXls = listProcesshhppSN(hhppSN);
            preFichaXlsList.addAll(hhppSNXls);
        }
        downloadAvailable = "false";
        uploadAvailable = false;
        generarPrefichaAvailable = false;
        guardarPrefichaAvailable = "true";
    }

    public void downloadPrefichaTable() {
        try {
            edificiosVtTxtDesc = new ArrayList<>();
            casasRedExternaTxtDesc = new ArrayList<>();
            conjCasasVtTxtDesc = new ArrayList<>();
            hhppSNTxtDesc = new ArrayList<>();

            PreFichaMgl prefichaDownload = (PreFichaMgl) dataTable.getRowData();
            preFichaTxtListDescarga = preFichaMglFacadeLocal.getListadoPrefichasTxtPorTab(prefichaDownload.getPrfId());

            for (PreFichaTxtMgl li : preFichaTxtListDescarga) {
                if (li.getPlacaUnida() != null
                        && !li.getPlacaUnida().trim().isEmpty()
                        && (li.getPlacaUnida().contains("SN")
                        || li.getPlacaUnida().contains("S-N")
                        || li.getPlacaUnida().contains("CD")
                        || li.getPlacaUnida().contains("S/N")
                        || li.getPlacaUnida().contains("NN"))) {
                    hhppSNTxtDesc.add(li);
                } else if (li.getBlockName() != null
                        && li.getBlockName().toUpperCase().equalsIgnoreCase("N_Edificio".toUpperCase())) {
                    edificiosVtTxtDesc.add(li);
                } else if (li.getBlockName().toUpperCase().equals("N_Casa".toUpperCase())) {
                    if (li.getPisos() != null
                            && li.getPisos().intValue() > 3) {
                        edificiosVtTxtDesc.add(li);
                    } else if ((li.getNombreConj() != null && !li.getNombreConj().trim().isEmpty()
                            && !li.getNombreConj().trim().equalsIgnoreCase("<>")
                            && isNameConjunto(li.getNombreConj().trim()))
                            || (li.getNumCasas() != null && li.getNumCasas().intValue() > 1)) {
                        conjCasasVtTxtDesc.add(li);
                    } else {
                        casasRedExternaTxtDesc.add(li);
                    }
                }

            }

            generarXlsValidacion(prefichaDownload);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en downloadPrefichaTable. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en downloadPrefichaTable. ", e, LOGGER);
        }
    }

    public void generarXlsValidacion(PreFichaMgl preFichaMgl) throws ApplicationException {

        try {
            //Blank workbook            
            XSSFWorkbook workbook = new XSSFWorkbook();
            NodoMgl nodoCodigo = preFichaMgl.getNodoMgl();
            Object[] cabeceraDataGral = new Object[]{"ID", "NOMBRE CONJUNTO / USO DE PREDIO", "VIA PRINCIPAL", "PLACA", "TOTAL HHPP", "APTOS", "LOCALES", "OFICINAS", "PISOS", "BARRIO", "DISTRIBUCION", "NODO", "CLASIFICACION", "TIPO DIRECCION"};
            Object[] cabeceraRedExt = new Object[]{"ID", "NOMBRE CONJUNTO / USO DE PREDIO", "VIA PRINCIPAL", "PLACA", "TOTAL HHPP", "APTOS", "LOCALES", "PISOS", "BARRIO", "DISTRIBUCION", "NODO", "CLASIFICACION", "TIPO DIRECCION"};

            if (edificiosVtTxtDesc != null && edificiosVtTxtDesc.size() > 0) {
                Map<String, Object[]> dataEdificiosVt = new TreeMap<>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                dataEdificiosVt.put(String.valueOf(fila++), new Object[]{""});
                dataEdificiosVt.put(String.valueOf(fila++), new Object[]{""});
                dataEdificiosVt.put(String.valueOf(fila++), new Object[]{""});
                BigDecimal totalHhhp = BigDecimal.ZERO;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaTxtMgl p : edificiosVtTxtDesc) {
                    BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                    totalHhhp = totalHhhp.add(totalHHPPPreficha);
                    dataEdificiosVt.put(String.valueOf(fila++),
                            new Object[]{p.getId(), p.getNombreConj(),
                                    p.getNombreCall(),
                                    p.getPlacaUnida(),
                                    totalHHPPPreficha,
                                    p.getAptos(),
                                    p.getLocales(),
                                    p.getOficinas(),
                                    p.getPisos(),
                                    p.getBarrio(),
                                    p.getDistribucion(),
                                    preFichaMgl.getNodoMgl().getNodCodigo(),
                                    p.getBlockName().toUpperCase().equals("N_EDIFICIO") ? "EDIFICIOS VT" : "CASAS RED EXTERNA",
                                    p.getTipoDireccion()});
                }
                dataEdificiosVt.put(String.valueOf(fila++), new Object[]{"", "", "", "Total HHPP", totalHhhp});
                fillSheetbook(workbook, "EDIFICIOS VT", dataEdificiosVt, 12, nodoCodigo, cabeceraDataGral, preFichaMgl.getPrfId());
            }
            if (casasRedExternaTxtDesc != null && casasRedExternaTxtDesc.size() > 0) {
                Map<String, Object[]> dataCasasRedExt = new TreeMap<>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                dataCasasRedExt.put(String.valueOf(fila++), new Object[]{""});
                dataCasasRedExt.put(String.valueOf(fila++), new Object[]{""});
                dataCasasRedExt.put(String.valueOf(fila++), new Object[]{""});
                BigDecimal totalHhhp = BigDecimal.ZERO;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaTxtMgl p : casasRedExternaTxtDesc) {
                    BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                    totalHhhp = totalHhhp.add(totalHHPPPreficha);
                    dataCasasRedExt.put(String.valueOf(fila++),
                            new Object[]{p.getId(), p.getNombreConj(),
                                    p.getNombreCall(),
                                    p.getPlacaUnida(),
                                    totalHHPPPreficha,
                                    p.getAptos(),
                                    p.getLocales(),
                                    p.getPisos(),
                                    p.getBarrio(),
                                    p.getDistribucion(),
                                    preFichaMgl.getNodoMgl().getNodCodigo(),
                                    "CASAS RED EXTERNA",
                                    p.getTipoDireccion()});
                }
                dataCasasRedExt.put(String.valueOf(fila++), new Object[]{"", "", "", "Total HHPP", totalHhhp});
                fillSheetbook(workbook, "CASAS RED EXTERNA", dataCasasRedExt, 11, nodoCodigo, cabeceraRedExt, preFichaMgl.getPrfId());
            }
            if (conjCasasVtTxtDesc != null && conjCasasVtTxtDesc.size() > 0) {
                Map<String, Object[]> dataConjCasasVt = new TreeMap<>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                dataConjCasasVt.put(String.valueOf(fila++), new Object[]{""});
                dataConjCasasVt.put(String.valueOf(fila++), new Object[]{""});
                dataConjCasasVt.put(String.valueOf(fila++), new Object[]{""});
                BigDecimal totalHhhp = BigDecimal.ZERO;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaTxtMgl p : conjCasasVtTxtDesc) {
                    BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                    totalHhhp = totalHhhp.add(totalHHPPPreficha);
                    dataConjCasasVt.put(String.valueOf(fila++),
                            new Object[]{p.getId(), p.getNombreConj(),
                                    p.getNombreCall(),
                                    p.getPlacaUnida(),
                                    totalHHPPPreficha,
                                    p.getAptos(),
                                    p.getLocales(),
                                    p.getOficinas(),
                                    p.getPisos(),
                                    p.getBarrio(),
                                    p.getDistribucion(),
                                    preFichaMgl.getNodoMgl().getNodCodigo(),
                                    "CONJUNTO DE CASAS VT",
                                    p.getTipoDireccion()});
                }
                dataConjCasasVt.put(String.valueOf(fila++), new Object[]{"", "", "", "Total HHPP", totalHhhp});
                fillSheetbook(workbook, "CONJ CASAS VT", dataConjCasasVt, 12, nodoCodigo, cabeceraDataGral, preFichaMgl.getPrfId());
            }
            if (hhppSNTxtDesc != null && hhppSNTxtDesc.size() > 0) {
                Map<String, Object[]> dataHHppSN = new TreeMap<>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                dataHHppSN.put(String.valueOf(fila++), new Object[]{""});
                dataHHppSN.put(String.valueOf(fila++), new Object[]{""});
                dataHHppSN.put(String.valueOf(fila++), new Object[]{""});
                BigDecimal totalHhhp = BigDecimal.ZERO;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaTxtMgl p : hhppSNTxtDesc) {
                    BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                    totalHhhp = totalHhhp.add(totalHHPPPreficha);
                    dataHHppSN.put(String.valueOf(fila++),
                            new Object[]{p.getId(), p.getNombreConj(),
                                    p.getNombreCall(),
                                    p.getPlacaUnida(),
                                    totalHHPPPreficha,
                                    p.getAptos(),
                                    p.getLocales(),
                                    p.getOficinas(),
                                    p.getPisos(),
                                    p.getBarrio(),
                                    p.getDistribucion(),
                                    preFichaMgl.getNodoMgl().getNodCodigo(),
                                    "HHPP SN",
                                    p.getTipoDireccion()});
                }
                dataHHppSN.put(String.valueOf(fila++), new Object[]{"", "", "", "Total HHPP", totalHhhp});
                fillSheetbook(workbook, "HHPP SN", dataHHppSN, 12, nodoCodigo, cabeceraDataGral, preFichaMgl.getPrfId());
            }

            String fileName1 = "PrefichaValidacion-" + nodoCodigo.getNodCodigo() + "-";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

            response1.setHeader("Content-Disposition", "attachment; filename=" + fileName1 + formato.format(new Date()) + ".xlsx");
            OutputStream output = response1.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();

        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error en generarXlsValidacion. " + e.getMessage(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error en generarXlsValidacion. " + e.getMessage(), e);
            throw new ApplicationException("Error en generarXlsValidacion. ", e);
        }
    }

    public void CreacionCMInfoVtEdiConj() throws ApplicationException {

        GeograficoPoliticoMgl departamento = geograficoPoliticoMglFacadeLocal.findById(new BigDecimal(dptoStr));

        GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.findById(new BigDecimal(ciudadStr));

        CmtBasicaMgl tecnologia = cmtBasicaMglFacadeLocal.findById(new BigDecimal(tecnologiasStr));

        if (edificiosVtXls != null) {

            for (PreFichaXlsMgl p : edificiosVtXls) {

                LOGGER.error("Ciudad: " + ciudad.getGeoGpoId()
                        + " Departamento: " + departamento.getGeoGpoId()
                        + " Tecnologia :" + tecnologia.getBasicaId()
                        + " Via principal: " + p.getViaPrincipal()
                        + " Placa: " + p.getPlaca()
                        + " Total HHPP: " + p.getTotalHHPP());

                CmtCuentaMatrizMgl cm = new CmtCuentaMatrizMgl();

                cm.setDepartamento(departamento);
                cm.setMunicipio(ciudad);
                cm.setNombreCuenta(p.getViaPrincipal() + " " + p.getPlaca());
                cm.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
                cm.setComunidad("NA");
                cm.setDivision("NA");
                cm.setEstadoRegistro(1);
                cm.setPerfilCreacion(1);

                cmtCuentaMatrizMglFacadeLocal.create(cm);

            }
        }
        if (conjCasasVtXls != null) {

            for (PreFichaXlsMgl p : conjCasasVtXls) {

                LOGGER.error("Ciudad: " + ciudad.getGeoGpoId()
                        + " Departamento: " + departamento.getGeoGpoId()
                        + " Tecnologia :" + tecnologia.getBasicaId()
                        + " Via principal: " + p.getViaPrincipal()
                        + " Placa: " + p.getPlaca()
                        + " Total HHPP: " + p.getTotalHHPP());

                CmtCuentaMatrizMgl cm = new CmtCuentaMatrizMgl();

                cm.setDepartamento(departamento);
                cm.setMunicipio(ciudad);
                cm.setNombreCuenta(p.getViaPrincipal() + " " + p.getPlaca());
                cm.setUsuarioCreacion(httpSession.getAttribute("loginUserSecurity").toString());
                cm.setComunidad("NA");
                cm.setDivision("NA");
                cm.setEstadoRegistro(1);
                cm.setPerfilCreacion(1);
            }
        }

    }

    public void guardarPreficha() throws ApplicationException {
        try {

            Date date1 = new Date();
            String usuario = httpSession.getAttribute("loginUserSecurity").toString();

            PreFichaMgl preFichaMgl = new PreFichaMgl();
            preFichaMgl.setNodoMgl(nodoSelected);
            preFichaMgl.setPrfFechaGenera(date1);
            preFichaMgl.setPrfUsuarioGenera(usuario);
            preFichaMgl.setFase(FASE_GENERACION);
            preFichaMgl.setNombreArchivo(fileName);
            preFichaMgl.setGeorreferenciada("0");
            preFichaMgl.setFichaCreada("0");
            preFichaMgl.setPrfFechaValidacion(date1);
            preFichaMgl.setPrfFechaModifica(date1);
            preFichaMgl.setFechaCreacion(date1);
            preFichaMgl.setUsuarioCreacion(usuario);
            preFichaMgl.setFechaEdicion(date1);
            preFichaMgl.setUsuarioEdicion(usuario);
            preFichaMgl.setTipoDireccion(tipoDireccion);
            preFichaMgl.setEstadoRegistro(1);

            preFichaMgl = preFichaMglFacadeLocal.savePreficha(preFichaMgl);

            preFichaTxtList = preFichaMglFacadeLocal.savePrefichaTxtList(preFichaMgl, preFichaTxtList);

            for (PreFichaXlsMgl preficha : preFichaXlsList) {
                switch (preficha.getClasificacion()) {
                    case Constant.EDIFICIOSVT:
                        if (prefichaXlsEsValida(preficha)) {
                            preficha.setPestana(Constant.EDIFICIOSVT);
                        } else {
                            preficha.setPestana(Constant.EDIFICIOSVT_NO_PROCESADOS);
                        }
                        break;
                    case Constant.CASAS_RED_EXTERNA:
                        if (prefichaXlsEsValida(preficha)) {
                            if (preficha.getPisos().intValue() > 3) {
                                preficha.setPestana(Constant.EDIFICIOSVT);
                            } else {
                                preficha.setPestana(Constant.CASAS_RED_EXTERNA);
                            }
                        } else {
                            if (preficha.getPisos().intValue() > 3) {
                                preficha.setPestana(Constant.EDIFICIOSVT_NO_PROCESADOS);
                            } else {
                                preficha.setPestana(Constant.CASAS_RED_EXTERNA_NO_PROCESADOS);
                            }
                        }
                        break;
                    case Constant.HHPPSN:
                        preficha.setPestana(Constant.HHPPSN);
                        break;
                    case Constant.CONJUNTOCASASVT:
                        if (prefichaXlsEsValida(preficha)) {
                            preficha.setPestana(Constant.CONJUNTOCASASVT);
                        } else {
                            preficha.setPestana(Constant.CONJUNTOCASASVT_NO_PROCESADOS);
                        }
                        break;
                }
            }
            preFichaXlsList = preFichaMglFacadeLocal.savePrefichaXlsList(preFichaMgl, preFichaXlsList);

            PreFichaAlertaMgl preFichaAlertaMgl = new PreFichaAlertaMgl();
            preFichaAlertaMgl.setPrefichaMgl(preFichaMgl);
            preFichaAlertaMgl.setProcesado("0");
            preFichaAlertaMgl.setFechaCreacion(date1);

            preFichaAlertaMgl.setUsuarioCreacion(usuario);
            preFichaAlertaMgl.setUsuarioEdicion(usuario);
            preFichaAlertaMgl.setFechaCreacionAlerta(date1);
            preFichaAlertaMgl.setFechaEnvio(date1);
            preFichaAlertaMgl.setFechaEdicion(date1);

            preFichaMglFacadeLocal.savePreFichaAlerta(preFichaAlertaMgl);

            listInfoByPage(1);

            downloadAvailable = "true";
            uploadAvailable = false;
            generarPrefichaAvailable = false;
            guardarPrefichaAvailable = "false";

            LOGGER.error("Cargue exitoso");
            String msn = "Cargue exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarPreficha. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarPreficha. ", e, LOGGER);
        }

    }

    private void fillSheetbook(XSSFWorkbook workbook, String sheetName,
                               Map<String, Object[]> data, int columns,
                               NodoMgl nodo, Object[] cabeceraDataGral,
                               BigDecimal idPreficha) throws ApplicationException {
        try {
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet(sheetName);
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            //esconder una columna
            sheet.setColumnHidden(0, true);

            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor((short) 70);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setColor((short) 0);
            cellStyle.setFont(font);

            //Iterate over data and write to sheet
            int rownum = 0;
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(cellStyle);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    }
                }
                for (int u = objArr.length; u <= (objArr.length * 2) - 1; u++) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(unlockedCellStyle);
                    cell.setCellValue(" ");
                }
            }
            //insertamos el id del nodo y de la preficha
            sheet.getRow(0).createCell(0).setCellValue(nodo.getNodId().doubleValue());
            sheet.getRow(1).createCell(0).setCellValue(idPreficha.doubleValue());
            //Se crean las cabeceras del archivo
            sheet.getRow(0).createCell(1).setCellValue("NODO " + nodo.getNodCodigo());
            sheet.getRow(0).getCell(1).setCellStyle(cellStyle);
            sheet.getRow(0).createCell(columns + 1).setCellValue("NODO " + nodo.getNodCodigo());
            sheet.getRow(1).createCell(1).setCellValue("ANTES INFORMACION BASICA NODO");
            sheet.getRow(1).getCell(1).setCellStyle(cellStyle);
            sheet.getRow(1).createCell(columns + 1).setCellValue("DESPUES INFORMACION BASICA NODO");

            Row rowCabecera = sheet.createRow(2);
            for (int i = 0; i < cabeceraDataGral.length; i++) {
                Cell cell = rowCabecera.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue((String) cabeceraDataGral[i]);
            }
            for (int i = 0; i < cabeceraDataGral.length - 1; i++) {
                Cell cell = rowCabecera.createCell(cabeceraDataGral.length + i);
                cell.setCellValue((String) cabeceraDataGral[i + 1]);
            }
            //Combinamos las columnas de las dos primeras filas para tener en encabezado de la tabla
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, columns));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, columns));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, columns + 1, columns * 2));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, columns + 1, columns * 2));
        } catch (RuntimeException e) {
            LOGGER.error("Error en fillSheetbook. " + e.getMessage(), e);
            throw new ApplicationException("Error en fillSheetbook. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en fillSheetbook. " + e.getMessage(), e);
            throw new ApplicationException("Error en fillSheetbook. " + e.getMessage(), e);
        }
    }

    private List<PreFichaXlsMgl> listProcessEdificiosVt(List<PreFichaTxtMgl> txtList) {
        List<PreFichaXlsMgl> result = new ArrayList<>();
        for (PreFichaTxtMgl t : txtList) {
            PreFichaXlsMgl x = new PreFichaXlsMgl();
            x.setNombreConj(t.getNombreEd());
            x.setViaPrincipal(t.getNombreCall());
            x.setPlaca(t.getPlacaUnida());
            x.setRegistroValido(t.getRegistroValido());

            BigDecimal totalHHpp = t.getNumCasas() == null ? BigDecimal.ZERO : t.getNumCasas();
            totalHHpp = t.getAptos() == null ? totalHHpp : totalHHpp.add(t.getAptos());
            x.setAptos(totalHHpp);
            totalHHpp = t.getLocales() == null ? totalHHpp : totalHHpp.add(t.getLocales());
            totalHHpp = t.getOficinas() == null ? totalHHpp : totalHHpp.add(t.getOficinas());
            x.setTotalHHPP(totalHHpp);
            x.setLocales(t.getLocales());
            x.setOficinas(t.getOficinas());
            if (t.getBlockName().equalsIgnoreCase("N_Casa".toUpperCase())) {
                x.setPisos(t.getPisos());
                x.setClasificacion("CASASREDEXTERNA");
            } else {
                x.setPisos(t.getPI());
                x.setClasificacion("EDIFICIOSVT");
            }
            x.setBarrio(t.getBarrio());
            x.setDistribucion(t.getDistribucion());
            x.setFase(FASE_GENERACION);
            x.setIdTipoDireccion(t.getTipoDireccion());
            x.setAmp(t.getAmp());
            result.add(x);
        }
        return result;
    }

    private List<PreFichaXlsMgl> listProcessCasasRedExterna(List<PreFichaTxtMgl> txtList) {
        List<PreFichaXlsMgl> result = new ArrayList<>();
        for (PreFichaTxtMgl t : txtList) {
            PreFichaXlsMgl x = new PreFichaXlsMgl();
            x.setNombreConj(t.getNombreConj());
            x.setViaPrincipal(t.getNombreCall());
            x.setPlaca(t.getPlacaUnida());
            x.setRegistroValido(t.getRegistroValido());

            BigDecimal totalHHpp = t.getNumCasas() == null ? BigDecimal.ZERO : t.getNumCasas();
            totalHHpp = t.getAptos() == null ? totalHHpp : totalHHpp.add(t.getAptos());
            x.setAptos(totalHHpp);
            totalHHpp = t.getLocales() == null ? totalHHpp : totalHHpp.add(t.getLocales());
            x.setTotalHHPP(totalHHpp);
            x.setLocales(t.getLocales());
            x.setPisos(t.getPisos());
            x.setBarrio(t.getBarrio());
            x.setDistribucion(t.getDistribucion());
            x.setClasificacion("CASASREDEXTERNA");
            x.setFase(FASE_GENERACION);
            x.setIdTipoDireccion(t.getTipoDireccion());
            x.setAmp(t.getAmp());
            result.add(x);
        }
        return result;
    }

    private List<PreFichaXlsMgl> listProcessConjCasasVt(List<PreFichaTxtMgl> txtList) {
        List<PreFichaXlsMgl> result = new ArrayList<>();
        for (PreFichaTxtMgl t : txtList) {
            PreFichaXlsMgl x = new PreFichaXlsMgl();
            x.setNombreConj(t.getNombreConj());
            x.setViaPrincipal(t.getNombreCall());
            x.setPlaca(t.getPlacaUnida());
            x.setRegistroValido(t.getRegistroValido());

            BigDecimal totalHHpp = t.getNumCasas() == null ? BigDecimal.ZERO : t.getNumCasas();
            totalHHpp = t.getAptos() == null ? totalHHpp : totalHHpp.add(t.getAptos());
            totalHHpp = t.getLocales() == null ? totalHHpp : totalHHpp.add(t.getLocales());
            totalHHpp = t.getOficinas() == null ? totalHHpp : totalHHpp.add(t.getOficinas());

            x.setTotalHHPP(totalHHpp);
            x.setAptos(t.getNumCasas());
            x.setLocales(t.getLocales());
            x.setOficinas(t.getOficinas());
            x.setPisos(t.getPisos());
            x.setBarrio(t.getBarrio());
            x.setDistribucion(t.getDistribucion());
            x.setClasificacion("CONJUNTOCASASVT");
            x.setFase(FASE_GENERACION);
            x.setIdTipoDireccion(t.getTipoDireccion());
            x.setAmp(t.getAmp());
            result.add(x);
        }
        return result;
    }

    private List<PreFichaXlsMgl> listProcesshhppSN(List<PreFichaTxtMgl> txtList) {
        List<PreFichaXlsMgl> result = new ArrayList<>();
        for (PreFichaTxtMgl t : txtList) {
            PreFichaXlsMgl x = new PreFichaXlsMgl();
            x.setNombreConj(t.getNombreConj());
            x.setViaPrincipal(t.getNombreCall());
            x.setPlaca(t.getPlacaUnida());
            x.setRegistroValido(t.getRegistroValido());

            BigDecimal totalHHpp = t.getNumCasas() == null ? BigDecimal.ZERO : t.getNumCasas();
            totalHHpp = t.getAptos() == null ? totalHHpp : totalHHpp.add(t.getAptos());
            x.setAptos(totalHHpp);
            totalHHpp = t.getLocales() == null ? totalHHpp : totalHHpp.add(t.getLocales());
            totalHHpp = t.getOficinas() == null ? totalHHpp : totalHHpp.add(t.getOficinas());
            x.setTotalHHPP(totalHHpp);
            x.setLocales(t.getLocales());
            x.setOficinas(t.getOficinas());
            BigDecimal totalPisos = t.getPisos() == null ? BigDecimal.ZERO : t.getPisos();
            totalPisos = t.getPI() == null ? totalPisos : totalPisos.add(t.getPI());
            x.setPisos(totalPisos);
            x.setBarrio(t.getBarrio());
            x.setDistribucion(t.getDistribucion());
            x.setClasificacion("HHPPSN");
            x.setFase(FASE_GENERACION);
            x.setIdTipoDireccion(t.getTipoDireccion());
            x.setAmp(t.getAmp());
            result.add(x);
        }
        return result;
    }

    private void validateInfoToXLS(PreFichaXlsMgl px) {
        try {
            isValidPreficha = String.valueOf(true);
            //validacion para que el numero Total de HHPP no sea cero
            if (px.getTotalHHPP() == null || px.getTotalHHPP().compareTo(BigDecimal.ZERO) == 0) {
                isValidPreficha = String.valueOf(false);
            }
            //validacion de la estructura de la placa
            if (!isValidPlaca(px.getPlaca())) {
                isValidPreficha = String.valueOf(false);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validateInfoToXLS. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validateInfoToXLS. ", e, LOGGER);
        }
    }

    private void clasificarInfoTxt() {
        edificiosVt = new ArrayList<>();
        casasRedExterna = new ArrayList<>();
        conjCasasVt = new ArrayList<>();
        hhppSN = new ArrayList<>();
        for (PreFichaTxtMgl preFichaTxtMgl : preFichaTxtList) {

            String placa = preFichaTxtMgl.getPlacaUnida();
            placa = placa.substring(placa.indexOf("-") + 1, placa.length());

            if ((preFichaTxtMgl.getPlacaUnida() != null
                    && !preFichaTxtMgl.getPlacaUnida().trim().isEmpty()
                    && (preFichaTxtMgl.getPlacaUnida().contains("SN")
                    || preFichaTxtMgl.getPlacaUnida().contains("S-N")
                    || preFichaTxtMgl.getPlacaUnida().contains("CD")
                    || preFichaTxtMgl.getPlacaUnida().contains("S/N")
                    || preFichaTxtMgl.getPlacaUnida().contains("NN")))) {
                preFichaTxtMgl.setPlacaUnida(preFichaTxtMgl.getPlacaUnida().replace("S-N", "SN").replace("S/N", "SN").replace("NN", "SN"));
                hhppSN.add(preFichaTxtMgl);
            } else if (preFichaTxtMgl.getBlockName() != null
                    && preFichaTxtMgl.getBlockName().equalsIgnoreCase("N_Edificio".toUpperCase())) {
                edificiosVt.add(preFichaTxtMgl);
            } else if (preFichaTxtMgl.getBlockName() != null
                    && preFichaTxtMgl.getBlockName().equalsIgnoreCase("N_Casa".toUpperCase())) {

                if (preFichaTxtMgl.getPisos() != null
                        && preFichaTxtMgl.getPisos().intValue() > 3) {
                    edificiosVt.add(preFichaTxtMgl);
                } else if ((preFichaTxtMgl.getNombreConj() != null && !preFichaTxtMgl.getNombreConj().trim().isEmpty()
                        && !preFichaTxtMgl.getNombreConj().trim().equalsIgnoreCase("<>")
                        && isNameConjunto(preFichaTxtMgl.getNombreConj().trim()))
                        || (preFichaTxtMgl.getNumCasas() != null && preFichaTxtMgl.getNumCasas().intValue() > 1)) {
                    conjCasasVt.add(preFichaTxtMgl);
                } else {
                    casasRedExterna.add(preFichaTxtMgl);
                }
            }

        }

    }

    private boolean isNameConjunto(String nameToValidate) {

        String regExpression = "^(CO\\s)([^\\s])+";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(nameToValidate.trim()).matches();
    }

    private void validateInfoFromTxt(List<PreFichaTxtMgl> txtList) throws ApplicationException {

        isValidPreficha = String.valueOf(true);
        LOGGER.error("Tamaño lista procesar: " + txtList.size());
        int y = 0;
        for (PreFichaTxtMgl pt : txtList) {
            LOGGER.error("registro procesando: " + (y++) + " prefichaTxt: " + pt.getNombreCall() + " " + pt.getPlacaUnida());
            //unificamos informacion en un columna segun sea el caso
            //validamos el numero de interiores (columnas INT, NUMINT)y se envia toda la info a INT
            BigDecimal numInt = pt.getNumInt() == null ? BigDecimal.ZERO : pt.getNumInt();
            pt.setNumInt(BigDecimal.ZERO);
            BigDecimal interiores = pt.getInteriores() == null ? BigDecimal.ZERO : pt.getInteriores();
            pt.setInteriores(numInt.add(interiores));
            //unificamos la distribucion en la columna distribucion
            unifyDistribution(pt);
            String observacionesGral = pt.getObservaciones() == null ? "" : pt.getObservaciones();

            //inificamos el nombre del edificio y conjunto
            if (pt.getNombreConj() != null && pt.getNombreConj().trim().isEmpty()
                    && pt.getNombreEd() != null && !pt.getNombreEd().trim().isEmpty()) {
                pt.setNombreConj(pt.getNombreEd());
            }

            //Validacion para que el nombre no sea menor a 4 caracteres cuando es una CM
            if (pt.getBlockName() != null && !pt.getBlockName().isEmpty()) {
                if (pt.getBlockName().toUpperCase().equalsIgnoreCase("N_EDIFICIO")
                        || (pt.getNumCasas() != null && pt.getNumCasas().intValue() >= 2)) {
                    if (pt.getNombreConj() != null && !pt.getNombreConj().trim().isEmpty()) {
                        if (pt.getNombreConj().length() < 4) {
                            observacionesGral += "Si se ingresa nombre del conjunto debe ser mayor a 4 caracteres por favor. ";
                            isValidPreficha = String.valueOf(false);
                        }
                    }

                    if (pt.getNombreEd() != null && !pt.getNombreEd().trim().isEmpty()) {
                        if (pt.getNombreEd().length() < 4) {
                            observacionesGral += "Si se ingresa nombre del edificio debe ser mayor a 4 caracteres por favor. ";
                            isValidPreficha = String.valueOf(false);
                        }
                    }
                }
            }


            //validacion para que el numero Total de HHPP no sea cero
            BigDecimal totalHHpp = pt.getNumCasas() == null ? BigDecimal.ZERO : pt.getNumCasas();
            totalHHpp = pt.getAptos() == null ? totalHHpp : totalHHpp.add(pt.getAptos());
            totalHHpp = pt.getLocales() == null ? totalHHpp : totalHHpp.add(pt.getLocales());
            totalHHpp = pt.getOficinas() == null ? totalHHpp : totalHHpp.add(pt.getOficinas());

            GeograficoPoliticoMgl ciudad = null;
            try {
                ciudad = geograficoPoliticoMglFacadeLocal.findById(new BigDecimal(ciudadStr));
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error en validateInfoFromTxt. ", e, LOGGER);
            }

            boolean dist = pt.getDistribucion() != null && !pt.getDistribucion().isEmpty() && pt.getDistribucion().contains("IN");

            if (totalHHpp.compareTo(BigDecimal.ZERO) == 0 && !dist) {
                observacionesGral += "El numero total de HHPP no puede ser cero - ";
                isValidPreficha = String.valueOf(false);
            }
            //validacion de la estructura de la placa y la calle,
            //para decidir tipo de direccion
            VerificadorExpresiones ve = JSFUtil.getBean("verificadorExpresiones", VerificadorExpresiones.class);
            ve.validarTipoDireccion(pt);

            //validamos la estructura de la distribucion
            if (ciudad != null && ciudad.getGpoMultiorigen().equals("1")) {
                if ((pt.getBarrio() == null || pt.getBarrio().isEmpty()) && (pt.getTipoDireccion() != null && pt.getTipoDireccion().equals(Constant.ACRONIMO_CK))) {
                    observacionesGral += "Barrio requerido ciudad multiorigen - ";
                    isValidPreficha = String.valueOf(false);
                }

            }

            if (pt.getAptos().intValue() != 0 && pt.getNumCasas().intValue() != 0) {
                pt.setRegistroValido(0);
                observacionesGral += "Existe numero de casas y apartamentos, uno de los dos valores debe ser 0-";
                isValidPreficha = String.valueOf(false);
            }

            if (!ve.validaBarrio(pt.getBarrio())) {
                pt.setRegistroValido(0);
            }

            if (pt.getDistribucion()
                    != null && !pt.getDistribucion().trim().isEmpty()) {
                if (!isValidDistribucion(pt.getDistribucion().trim())) {
                    observacionesGral += "La distribucion no tiene una estructura valida - ";
                    isValidPreficha = String.valueOf(false);
                } else {
                    //si tiene un distribucion valida se verifica que no tenga unidades mayores a la cantidad de pisos señala en la ficha
                    if (pt.getBlockName().toUpperCase().equals("N_EDIFICIO")) {
                        if (VerificadorExpresiones.isDistribucionEsValida(pt.getPi().intValue(), pt.getDistribucion()) == false) {
                            observacionesGral += "La distribucion tiene unidades que exceden la cantidad de pisos total que es de " + pt.getPi().intValue() + " - ";
                            isValidPreficha = String.valueOf(false);
                        }
                    }
                    if (pt.getBlockName().toUpperCase().equals("N_CASA")) {
                        if (VerificadorExpresiones.isDistribucionEsValida(pt.getPisos().intValue(), pt.getDistribucion()) == false) {
                            observacionesGral += "La distribucion tiene unidades que exceden la cantidad de pisos total que es de " + pt.getPisos().intValue() + " - ";
                            isValidPreficha = String.valueOf(false);
                        }
                    }
                    //si tiene un distribucion valida vericamos que el numero de HHPP de cada tipo correspondan
                    if (!isValidCountDistribucion(pt.getDistribucion(), pt)) {
                        observacionesGral += (pt.getObservaciones() == null ? "" : pt.getObservaciones());
                        isValidPreficha = String.valueOf(false);
                    }
                }
            } else {

                pt.setOficinas(Objects.isNull(pt.getOficinas()) ? BigDecimal.ZERO : pt.getOficinas());

                //Validacione No Procesados cuando CASAS no contiene Distribucion
                if (pt.getBlockName().trim().equals("N_CASA") && pt.getPisos().intValue() == 4) {
                    int totalHHPP = pt.getAptos().intValue() + pt.getLocales().intValue() + pt.getOficinas().intValue();
                    if (totalHHPP > 4) {
                        pt.setRegistroValido(0);
                    }
                }
                if (pt.getBlockName().trim().equals("N_CASA") && pt.getPisos().intValue() <= 3) {
                    int totalHHPP = pt.getAptos().intValue() + pt.getLocales().intValue() + pt.getOficinas().intValue();
                    if (totalHHPP > 4) {
                        pt.setRegistroValido(0);
                    }
                }
                if (pt.getBlockName().trim().equals("N_CASA") && pt.getPisos().intValue() > 4) {
                    pt.setRegistroValido(0);
                }
            }

            if (pt.getBlockName().toUpperCase().equals("N_CASA") && pt.getPisos().intValue() <= 0) {
                observacionesGral += ("Columna PISOS no puede tener valor de 0 o negativo ");
                isValidPreficha = String.valueOf(false);
            }

            if (pt.getBlockName().toUpperCase().equals("N_EDIFICIO") && pt.getPisos().intValue() <= 0) {
                observacionesGral += ("Columna PI no puede tener valor de 0 o negativo ");
                isValidPreficha = String.valueOf(false);
            }

            List<Object> lsParameters = new ArrayList<>();
            lsParameters.add(nodoSelected);
            lsParameters.add(tecnologiaList);
            lsParameters.add(isValidPreficha);
            
            List<String> lsFilterValues = new ArrayList<>();
            lsFilterValues.add(tecnologiasStr);
            lsFilterValues.add(nodoStr);
            
            Map<String, Object> mapa = PrefichaUtil.validateTypeOfTechnology(pt, lsParameters, lsFilterValues);
            observacionesGral += mapa.get(PrefichaUtil.getObs());
            isValidPreficha = String.valueOf(mapa.get(PrefichaUtil.getIsValid()));
            
            pt.setObservaciones(observacionesGral);

            if (!("").equals(observacionesGral)) {
                cantidadObservaciones++;
            }

            listadoObservacionesArchivo.add(observacionesGral);
        }
    }       

    private boolean isValidPlaca(String placaToValidate) throws ApplicationException {
        try {
            ParametrosMgl parametroBM;
            ParametrosMgl parametroIT;
            ParametrosMgl parametroCK;
            String regExpression = "";

            parametroBM = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_BM_FICHAS_NODO);
            parametroIT = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_IT_FICHAS_NODO);
            parametroCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO);

            if (tipoDireccion.equals("CK")) {
                regExpression = parametroCK.getParValor();

            }
            if (tipoDireccion.equals("BM")) {
                regExpression = parametroBM.getParValor();

            }
            if (tipoDireccion.equals("IT")) {
                regExpression = parametroIT.getParValor();

            }

            Pattern patter;
            patter = Pattern.compile(regExpression);
            return patter.matcher(placaToValidate.trim()).matches();
        } catch (ApplicationException e) {
            LOGGER.error("Error en isValidPlaca. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en isValidPlaca. " + e.getMessage(), e);
        }
        return false;
    }

    private void unifyDistribution(PreFichaTxtMgl pt) {
        //validamos la informacion de la distribucion para migrarlas a la columna distribucion
        String distTotal = "";
        boolean isvalidDist = true;
        if (isvalidDist && containsDistribution(pt.getPlacaUnidaAnt3())) {
            String distTemp = getDistributionInColumn(pt.getPlacaUnidaAnt3()).trim() + " ";
            if (isValidDistribucion(distTemp)) {
                distTotal += distTemp;
            } else {
                isvalidDist = false;
                isValidPreficha = String.valueOf(false);
            }
        }
        if (isvalidDist && containsDistribution(pt.getPlacaUnidaAnt2())) {
            distTotal += getDistributionInColumn(pt.getPlacaUnidaAnt2()).trim() + " ";
        }
        if (isvalidDist && containsDistribution(pt.getNombreCallAnt3())) {
            distTotal += getDistributionInColumn(pt.getNombreCallAnt3()).trim() + " ";
        }
        if (isvalidDist && containsDistribution(pt.getNombreCallAnt2())) {
            String distTemp = getDistributionInColumn(pt.getNombreCallAnt2()).trim() + " ";
            if (isValidDistribucion(distTemp)) {
                distTotal += distTemp.trim() + " ";
            } else {
                isvalidDist = false;
                isValidPreficha = String.valueOf(false);
            }
        }
        if (isvalidDist && containsDistribution(pt.getPlacaUnida())) {
            String distInPlaca = getDistributionInColumn(pt.getPlacaUnida()).trim();
            if (isValidDistribucion(distInPlaca)) {
                //limpiamos la placa del registro de la distribucion que contiene
                String newPlaca = pt.getPlacaUnida().replace(distInPlaca, "");
                pt.setPlacaUnida(newPlaca.trim());
                distTotal += distInPlaca + " ";
            } else {
                isvalidDist = false;
                isValidPreficha = String.valueOf(false);
            }

        }
        if (isvalidDist && containsDistribution(pt.getDistribucion())) {
            distTotal += getDistributionInColumn(pt.getDistribucion()).trim() + " ";
        }

        if (isvalidDist && !distTotal.trim().isEmpty()) {
            String regExpression = "((AP|LC|IN|BL)\\s((?!\\-)(\\-?\\w+)+)(\\s*))";
            Pattern patter;
            patter = Pattern.compile(regExpression);
            Matcher matcher = patter.matcher(distTotal);
            List<String> gruposStr = new ArrayList<>();
            //obtenemos los grupos de distribuciones para validar si se encuentras repetidas
            while (matcher.find()) {
                gruposStr.add(matcher.group());
            }

            if (gruposStr.size() > 0) {
                for (int i = 0; i < gruposStr.size(); i++) {
                    String tipoBuscar = gruposStr.get(i).trim().split("\\s")[0];
                    for (int j = 0; j < gruposStr.size(); j++) {
                        if (i != j) {
                            String tipoComparar = gruposStr.get(j).trim().split("\\s")[0];
                            if (tipoComparar.equalsIgnoreCase(tipoBuscar)) {
                                ArrayList<String> valoresBuscar = new ArrayList<>(Arrays.asList(gruposStr.get(i).trim().split("\\s")[1].split("-")));
                                ArrayList<String> valoresComparar = new ArrayList<>(Arrays.asList(gruposStr.get(j).trim().split("\\s")[1].split("-")));
                                for (String s : valoresBuscar) {
                                    for (int k = 0; k < valoresComparar.size(); k++) {
                                        if (valoresComparar.get(k).equalsIgnoreCase(s)) {
                                            valoresComparar.remove(k);
                                        }
                                    }
                                }
                                String valoresTotalesTipo = gruposStr.get(i).trim().split("\\s")[1];
                                String valoesCompararFinalStr = "";

                                if (valoresComparar.size() > 0) {
                                    for (String s : valoresComparar) {
                                        valoesCompararFinalStr += s + "-";
                                    }
                                    valoresTotalesTipo += "-" + valoesCompararFinalStr.substring(0, valoesCompararFinalStr.length() - 1);

                                }
                                gruposStr.set(i, tipoBuscar + " " + valoresTotalesTipo);
                                gruposStr.remove(j);
                            }
                        }
                    }
                }
            }

            if (gruposStr.size() > 0) {
                String disFinal = "";
                for (String s : gruposStr) {
                    disFinal += s.trim() + " ";
                }
                pt.setDistribucion(disFinal.trim());
            }

        }
        if (!isvalidDist) {
            pt.setObservaciones("La distribucion no tiene una estructura valida - ");
        }
    }

    private boolean containsDistribution(String distribucionToValidate) {
        boolean result = false;
        if (distribucionToValidate != null && !distribucionToValidate.isEmpty()) {
            String regExpression = ".*((AP|LC|IN|BL).*((?!\\-)(\\-?\\w+)+)(\\s*)).*";
            Pattern patter;
            patter = Pattern.compile(regExpression);
            result = patter.matcher(distribucionToValidate.toUpperCase()).matches();
        }
        return result;
    }

    private String getDistributionInColumn(String distribucionToValidate) {
        String regExpression = "(((AP|LC|IN|BL).*((?!\\-)(\\-?\\w+)+)(\\s*)).*)+";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        Matcher matcher = patter.matcher(distribucionToValidate.toUpperCase());
        matcher.find();
        return matcher.group();
    }

    private boolean isValidDistribucion(String distribucionToValidate) {
        String regExpression = "^((AP|LC|IN|BL)\\s((?!\\-)(\\-?\\w+)+)( ?))+$";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(distribucionToValidate.toUpperCase()).matches();
    }

    private boolean isValidCountDistribucion(String distribucionToValidate, PreFichaTxtMgl preFichaTxtMgl) {
        boolean resutl = true;
        String regExpression = "((AP|LC|IN|BL)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        if (patter.matcher(distribucionToValidate).find()) {
            List<String> gruposStr = new ArrayList<>();
            Matcher matcher = patter.matcher(distribucionToValidate);
            while (matcher.find()) {
                String grupoStr = matcher.group();
                gruposStr.add(grupoStr);
            }
            if (gruposStr.size() > 0) {

                int cantDistLocales = 0;
                int cantDistAptos = 0;
                String observacionesValidacionNumHhpp = " ";
                for (String s : gruposStr) {

                    String tipo = s.trim().split("\\s")[0];
                    String[] dist = s.trim().split("\\s")[1].split("-");
                    PrefichaBean.DIST_TYPE type = PrefichaBean.DIST_TYPE.valueOf(tipo);
                    switch (type) {
                        case LC:
                            cantDistLocales = dist.length;
                            break;
                        case AP:
                            cantDistAptos = dist.length;
                            break;
                    }
                }
                if (cantDistLocales != preFichaTxtMgl.getLocales().intValue()) {

                    observacionesValidacionNumHhpp += "El numero de Locales no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (cantDistAptos != preFichaTxtMgl.getAptos().intValue()) {
                    observacionesValidacionNumHhpp += "El numero de Apto no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (!observacionesValidacionNumHhpp.trim().isEmpty()) {
                    observacionesValidacionNumHhpp += (preFichaTxtMgl.getObservaciones() == null ? "" : preFichaTxtMgl.getObservaciones());
                    preFichaTxtMgl.setObservaciones(observacionesValidacionNumHhpp);
                }
            }
        }
        return resutl;
    }

    private PreFichaTxtMgl fillEntityPreficha(String[] strSplit, Map<String, Object> cabecera) {
        PreFichaTxtMgl entityFill = new PreFichaTxtMgl();
        //validamos que el numero de columnas de la cabecera sea igual que el numero de columnas de los datos
        if (strSplit.length == cabecera.size()) {
            for (String key : cabecera.keySet()) {
                int pos = ((Integer) cabecera.get(key));
                if (!"CORDX".equals(key) && !"CORDY".equals(key)) {

                    COLUMN_NAME column = COLUMN_NAME.valueOf(key);
                    
                    switch (column) {
                        case PLACAUNIDA:
                            entityFill.setPlacaUnida(getStringValueColumn(strSplit[pos]));
                            break;
                        case BARRIO:
                            entityFill.setBarrio(getStringValueColumn(strSplit[pos]));
                            break;
                        case HANDLE:
                            entityFill.setHandle(getStringValueColumn(strSplit[pos]));
                            break;
                        case PI:
                        case PISOS:
                            entityFill.setPisos(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case AMP:
                            entityFill.setAmp(getStringValueColumn(strSplit[pos]));
                            break;
                        case NO:
                            entityFill.setNo(getStringValueColumn(strSplit[pos]));
                            break;
                        case DISTRIBUCION:
                            entityFill.setDistribucion(getStringValueColumn(strSplit[pos]));
                            break;
                        case OFICINAS:
                            entityFill.setOficinas(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case PLACAUNIDAANT2:
                            entityFill.setPlacaUnidaAnt2(getStringValueColumn(strSplit[pos]));
                            break;
                        case PLACAUNIDAANT3:
                            entityFill.setPlacaUnidaAnt3(getStringValueColumn(strSplit[pos]));
                            break;
                        case LOCALES:
                            entityFill.setLocales(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case INT:
                            entityFill.setInteriores(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case NOMBRE_ED:
                            entityFill.setNombreEd(getStringValueColumn(strSplit[pos]));
                            break;
                        case NOMBRECALLANT3:
                            entityFill.setNombreCallAnt3(getStringValueColumn(strSplit[pos]));
                            break;
                        case NOMBRECALL:
                            entityFill.setNombreCall(getStringValueColumn(strSplit[pos]));
                            break;
                        case NOMBRECALLANT2:
                            entityFill.setNombreCallAnt2(getStringValueColumn(strSplit[pos]));
                            break;
                        case BLOCKNAME:
                            entityFill.setBlockName(getStringValueColumn(strSplit[pos]));
                            break;
                        case NUMINT:
                            entityFill.setNumInt(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case NOMBRE_CONJ:
                            entityFill.setNombreConj(getStringValueColumn(strSplit[pos]));
                            break;
                        case APTOS:
                            entityFill.setAptos(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        case NUM_CASAS:
                            entityFill.setNumCasas(getBigDecimalValueColumn(strSplit[pos]));
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            entityFill.setHandle(getStringValueColumn(strSplit[((Integer) cabecera.get("HANDLE"))]));
            entityFill.setPlacaUnida(getStringValueColumn(strSplit[((Integer) cabecera.get("PLACAUNIDA"))]));
            entityFill.setNombreCall(getStringValueColumn(strSplit[((Integer) cabecera.get("NOMBRECALL"))]));
            entityFill.setBlockName(getStringValueColumn(strSplit[((Integer) cabecera.get("BLOCKNAME"))]));
            entityFill.setObservaciones("El Numero de datos no concuerda con el numero de columnas");
            isValidTxt = String.valueOf(false);
        }

        return entityFill;
    }

    private BigDecimal getBigDecimalValueColumn(String value) {

        if (value != null && !value.trim().isEmpty()) {
            Pattern pat = Pattern.compile("\\d+");
            if (pat.matcher(value).matches()) {
                return new BigDecimal(value);
            }
        }
        return BigDecimal.ZERO;
    }

    private String getStringValueColumn(String value) {

        if (value != null && !value.trim().isEmpty()
                && !value.trim().equalsIgnoreCase("<>")) {
            return value.trim().toUpperCase();
        }
        return "";
    }

    public void limpiarListas() {
        preFichaTxtList = null;
        edificiosVt = null;
        casasRedExterna = null;
        conjCasasVt = null;
        hhppSN = null;
        edificiosVtXls = null;
        casasRedExternaXls = null;
        conjCasasVtXls = null;
        hhppSNXls = null;
        preFichaXlsList = null;
    }

    public void changeArchivo() {
        LOGGER.error("ha entrado changeArchivo");
        uploadAvailable = true;
    }

    public void changeNodo() {
        try {
            nodoSelected = nodoMglFacadeLocal.findById(new BigDecimal(nodoStr));
            LOGGER.error("nodo: " + nodoSelected.getNodId() + "-" + nodoSelected.getNodCodigo());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en changeNodo. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en changeNodo. ", e, LOGGER);
        }

    }

    public void showHideTablaCargada() {
        if (showPanelTxtCargado) {
            showPanelTxtCargado = false;
        } else {
            showPanelTxtCargado = true;
        }
    }

    public String menuFichaAction() {
        return "fichaMenu";
    }

    public void descargarObservacionesArchivo() throws IOException {
        FacesContext facesContext1 = FacesContext.getCurrentInstance();
        HttpServletResponse response1
                = (HttpServletResponse) facesContext1.getExternalContext().getResponse();
        response1.reset();
        response1.setHeader("Content-Type", "application/octet-stream");
        response1.setHeader("Content-Disposition", "attachment;filename=" + fileName.replace(".txt", "") + " Observaciones.txt");
        OutputStream responseOutputStream = response1.getOutputStream();
        StringBuilder contenidoArchivo = new StringBuilder();
        if (lineasArchivo != null && !lineasArchivo.isEmpty() && listadoObservacionesArchivo != null && !listadoObservacionesArchivo.isEmpty()) {

            contenidoArchivo.append(lineasArchivo.get(0)).append("\tOBSERVACIONES\n");
            for (int i = 1; i < lineasArchivo.size(); i++) {
                if (i == lineasArchivo.size() - 1) {
                    if ("".equals(listadoObservacionesArchivo.get(i - 1))) {
                        contenidoArchivo.append(lineasArchivo.get(i));
                    } else {
                        contenidoArchivo.append(lineasArchivo.get(i)).append("\t").append(listadoObservacionesArchivo.get(i - 1)).append("\n");
                    }
                } else {
                    contenidoArchivo.append(lineasArchivo.get(i)).append("\t").append(listadoObservacionesArchivo.get(i - 1)).append("\n");
                }
            }
        }
        InputStream stream = new ByteArrayInputStream((contenidoArchivo.toString()).getBytes(CharEncoding.UTF_8));
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = stream.read(bytesBuffer)) > 0) {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }
        responseOutputStream.flush();
        stream.close();
        responseOutputStream.close();

        facesContext1.responseComplete();
    }

    public boolean prefichaXlsEsValida(PreFichaXlsMgl preficha) {
        if (preficha.getRegistroValido() == 1
                && ((preficha.getIdTipoDireccion() != null && preficha.getIdTipoDireccion().equals("IT"))
                || (preficha.getIdTipoDireccion() != null && preficha.getIdTipoDireccion().equals("BM"))
                || (preficha.getIdTipoDireccion() != null && preficha.getIdTipoDireccion().equals("CK")))) {

            return true;
        } else {
            return false;
        }
    }

    private String listInfoByPage(int page) {
        try {

            List<String> faseList = new ArrayList<>();
            faseList.add(FASE_GENERACION);
            preFichaValidarList = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList, page, ConstantsCM.PAGINACION_DIEZ_FILAS, true, fechaInicial, fechaFinal);
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
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaBean: pageLast() " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            List<String> faseList = new ArrayList<>();
            faseList.add(FASE_GENERACION);
            List<PreFichaMgl> preFichaValidarListContar = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList, 0, 0, false, fechaInicial, fechaFinal);
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

    /**
     * Obtiene el flag de status del registro (marcado/desmarcado para
     * eliminaci&oacute;n).
     *
     * @param idRegistro Identificador del registro a eliminar.
     * @return Booleano que contiene la marca.
     */
    public boolean obtenerMarcaRegistroEliminar(BigDecimal idRegistro) {
        boolean marcado = false;
        if (idRegistro != null) {
            if (mapRegistrosMarcadosEliminar.containsKey(idRegistro)) {
                marcado = mapRegistrosMarcadosEliminar.get(idRegistro);
            }
        }
        return (marcado);
    }

    /**
     * Marca/Desmarca un elemento de la lista para eliminar.
     *
     * @param idRegistro Identificador del registro a eliminar.
     */
    public void modificarMarcaRegistroEliminar(BigDecimal idRegistro) {
        if (idRegistro != null) {
            if (mapRegistrosMarcadosEliminar.containsKey(idRegistro)
                    && mapRegistrosMarcadosEliminar.get(idRegistro)) {
                // Se elimina el registro del mapa, en caso tal que ya se encontrara previamente marcado.
                mapRegistrosMarcadosEliminar.remove(idRegistro);
            } else {
                // Adiciona el registro al mapa, ya que previamente se encontraba desmarcado.
                mapRegistrosMarcadosEliminar.put(idRegistro, true);
            }
        }
    }

    /**
     * Marca/Desmarca los registros a eliminar masivamente.
     */
    public void modificarMarcaRegistrosMasivo() {

        if (preFichaValidarList != null && !preFichaValidarList.isEmpty()) {
            // Recorrer el listado filtrado de registros, para marcar/desmarcar todos.
            for (PreFichaMgl preficha : preFichaValidarList) {
                if (preficha != null && preficha.getPrfId() != null) {
                    if (mapRegistrosMarcadosEliminar.containsKey(preficha.getPrfId())
                            && mapRegistrosMarcadosEliminar.get(preficha.getPrfId())) {
                        // Se elimina el registro del mapa, en caso tal que ya se encontrara previamente marcado.
                        mapRegistrosMarcadosEliminar.remove(preficha.getPrfId());
                    } else {
                        // Adiciona el registro al mapa, ya que previamente se encontraba desmarcado.
                        mapRegistrosMarcadosEliminar.put(preficha.getPrfId(), true);
                    }
                }
            }
        }

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
                if (preFichaFiltradaList.size() != 0) {
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

    public String getCiudadStr() {
        return ciudadStr;
    }

    public void setCiudadStr(String ciudadStr) {
        this.ciudadStr = ciudadStr;
    }

    public String getDptoStr() {
        return dptoStr;
    }

    public void setDptoStr(String dptoStr) {
        this.dptoStr = dptoStr;
    }

    public List<GeograficoPoliticoMgl> getDptoList() {
        return dptoList;
    }

    public void setDptoList(List<GeograficoPoliticoMgl> dptoList) {
        this.dptoList = dptoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public String getNodoStr() {
        return nodoStr;
    }

    public void setNodoStr(String nodoStr) {
        this.nodoStr = nodoStr;
    }

    public List<NodoMgl> getNodoList() {
        return nodoList;
    }

    public void setNodoList(List<NodoMgl> nodoList) {
        this.nodoList = nodoList;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<PreFichaTxtMgl> getPreFichaTxtList() {
        return preFichaTxtList;
    }

    public void setPreFichaTxtList(List<PreFichaTxtMgl> preFichaTxtList) {
        this.preFichaTxtList = preFichaTxtList;
    }

    public List<PreFichaXlsMgl> getPreFichaXlsList() {
        return preFichaXlsList;
    }

    public void setPreFichaXlsList(List<PreFichaXlsMgl> preFichaXlsList) {
        this.preFichaXlsList = preFichaXlsList;
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

    public NodoMgl getNodoSelected() {
        return nodoSelected;
    }

    public void setNodoSelected(NodoMgl nodoSelected) {
        this.nodoSelected = nodoSelected;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<PreFichaMgl> getPreFichaValidarList() {
        return preFichaValidarList;
    }

    public void setPreFichaValidarList(List<PreFichaMgl> preFichaValidarList) {
        this.preFichaValidarList = preFichaValidarList;
    }

    public String getDownloadAvailable() {
        return downloadAvailable;
    }

    public void setDownloadAvailable(String downloadAvailable) {
        this.downloadAvailable = downloadAvailable;
    }

    public BigDecimal getIdPrefichaSelected() {
        return idPrefichaSelected;
    }

    public void setIdPrefichaSelected(BigDecimal idPrefichaSelected) {
        this.idPrefichaSelected = idPrefichaSelected;
    }

    public boolean getUploadAvailable() {
        return uploadAvailable;
    }

    public void setUploadAvailable(boolean uploadAvailable) {
        this.uploadAvailable = uploadAvailable;
    }

    public boolean getGenerarPrefichaAvailable() {
        return generarPrefichaAvailable;
    }

    public void setGenerarPrefichaAvailable(boolean generarPrefichaAvailable) {
        this.generarPrefichaAvailable = generarPrefichaAvailable;
    }

    public String getGuardarPrefichaAvailable() {
        return guardarPrefichaAvailable;
    }

    public void setGuardarPrefichaAvailable(String guardarPrefichaAvailable) {
        this.guardarPrefichaAvailable = guardarPrefichaAvailable;
    }

    public String getTecnologiasStr() {
        return tecnologiasStr;
    }

    public void setTecnologiasStr(String tecnologiasStr) {
        this.tecnologiasStr = tecnologiasStr;
    }

    public List<CmtBasicaMgl> getTecnologiaList() {
        return tecnologiaList;
    }

    public void setTecnologiaList(List<CmtBasicaMgl> tecnologiaList) {
        this.tecnologiaList = tecnologiaList;
    }

    public List<GeograficoPoliticoMgl> getCentrosPobladosList() {
        return centrosPobladosList;
    }

    public void setCentrosPobladosList(List<GeograficoPoliticoMgl> centrosPobladosList) {
        this.centrosPobladosList = centrosPobladosList;
    }

    public String getCentroPobladoStr() {
        return centroPobladoStr;
    }

    public void setCentroPobladoStr(String centroPobladoStr) {
        this.centroPobladoStr = centroPobladoStr;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public List<String> getCorregimientosList() {
        return corregimientosList;
    }

    public void setCorregimientosList(List<String> corregimientosList) {
        this.corregimientosList = corregimientosList;
    }

    public String getCorregimientoStr() {
        return corregimientoStr;
    }

    public void setCorregimientoStr(String corregimientoStr) {
        this.corregimientoStr = corregimientoStr;
    }

    public GeograficoPoliticoMgl getCentroObj() {
        return centroObj;
    }

    public void setCentroObj(GeograficoPoliticoMgl centroObj) {
        this.centroObj = centroObj;
    }

    public GeograficoPoliticoMgl getCiudadObj() {
        return ciudadObj;
    }

    public void setCiudadObj(GeograficoPoliticoMgl ciudadObj) {
        this.ciudadObj = ciudadObj;
    }

    public boolean isTieneCorregimiento() {
        return tieneCorregimiento;
    }

    public void setTieneCorregimiento(boolean tieneCorregimiento) {
        this.tieneCorregimiento = tieneCorregimiento;
    }

    public boolean isShowPanelTxtCargado() {
        return showPanelTxtCargado;
    }

    public void setShowPanelTxtCargado(boolean showPanelTxtCargado) {
        this.showPanelTxtCargado = showPanelTxtCargado;
    }

    public List<String> getListadoObservacionesArchivo() {
        return listadoObservacionesArchivo;
    }

    public void setListadoObservacionesArchivo(List<String> listadoObservacionesArchivo) {
        this.listadoObservacionesArchivo = listadoObservacionesArchivo;
    }

    public List<String> getLineasArchivo() {
        return lineasArchivo;
    }

    public void setLineasArchivo(List<String> lineasArchivo) {
        this.lineasArchivo = lineasArchivo;
    }

    public int getCantidadObservaciones() {
        return cantidadObservaciones;
    }

    public void setCantidadObservaciones(int cantidadObservaciones) {
        this.cantidadObservaciones = cantidadObservaciones;
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

    public boolean isRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(boolean registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public Map<BigDecimal, Boolean> getMapRegistrosMarcadosEliminar() {
        return mapRegistrosMarcadosEliminar;
    }

    public void setMapRegistrosMarcadosEliminar(Map<BigDecimal, Boolean> mapRegistrosMarcadosEliminar) {
        this.mapRegistrosMarcadosEliminar = mapRegistrosMarcadosEliminar;
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
    private final String BTNCRGPRE = "BTNCRGPRE";
    private final String BTNDELFIC = "BTNDELFIC";

    // Validar Opciones por Rol

    public boolean validarOpcionCargarFicha() {
        return validarEdicionRol(BTNCRGPRE);
    }

    public boolean validarOpcionEliminar() {
        return validarEdicionRol(BTNDELFIC);
    }


    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

}
