package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DirLogEliminacionMasivaMglFacadeLocal;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.EstadoHhppMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.MarcasMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressRequest;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.EliminarHhppMasivoManagedThreads.EliminarHhppMasivoManagedThreadsInt;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFileDefaultMemoryImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 * Clase encargada de manipular los componentes de la vista
 * cambioEstratoMasivo.xhtml
 *
 * @author Gonzalo Andrés Galindo
 * @version 1.0
 */
@ManagedBean(name = "eliminarHHPPMasivoBean")
@ViewScoped
public class EliminarHHPPMasivoBean {

    private HhppMgl hhppMgl = null;
    private List<GeograficoPoliticoMgl> geoPoliticoList;
    private GeograficoPoliticoMgl geoPolitico;
    private String idSqlSelected;
    private String message;
    private String marcas;
    private String nodo;
    private String tipoUnidad;
    private String estadoUnidad;
    private String geoGpo;
    private String procesoH = "I";
    private String estadoHHPP;
    private String tipoHHPP;
    private String geoHHPP;
    private String blackLisHHPP;
    private String calleHHPP;
    private String placaHHPP;
    private String apartamentoHHPP;
    private String hhppIdHHPP;
    private String nodomHHPP;
    private String fechaCreacionHHPP;
    public static BigDecimal logId;
    public List<MarcasMgl> listMarcasMgl = null;
    private List<GeograficoPoliticoMgl> dptoList;
    private List<GeograficoPoliticoMgl> ciudadList;
    public List<HhppMgl> hhppMglList;
    public static List<HhppMgl> hhppListDele;
    public static List<HhppMgl> hhppListNoDele;
    public static boolean finHilo;
    public static int count;
    public List<NodoMgl> listNodoMgl = null;
    public List<EstadoHhppMgl> listEstadoHhppMgl = null;
    public List<TipoHhppMgl> listTipoHhppMgl = null;
    private boolean deshabilitar = false;
    private String msn;
    /**
     * Paginacion Tabla
     */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private HtmlDataTable dataTableD = new HtmlDataTable();
    private String pageActualD;
    private String numPaginaD = "1";
    private List<Integer> paginaListD;
    private HtmlDataTable dataTableND = new HtmlDataTable();
    private String pageActualND;
    private String numPaginaND = "1";
    private List<Integer> paginaListND;
    /**
     *
     */
    private HtmlSelectOneRadio tipoEliminacion;
    private HtmlSelectOneMenu departamento;
    private HtmlPanelGrid eliminacionInd;
    private HtmlPanelGrid eliminacionArch;
    private String renderFormCAM;
    private String renderFormUNI;
    private GeograficoPoliticoMgl geograficoPoliticoDep;
    private GeograficoPoliticoMgl geograficoPoliticoCiu;
    private DireccionMglFacadeLocal direccionMglFacade;
    private String usuario;/*Atributo para el usuario, se ingresa en la interfaz gráfica*/

    private Date fechaCargue;/*Atributo para la fecha de cargue del archivo, se ingresa en la interfaz gráfica*/

    private String perfil;/*Atributo para perfil de un usuario, se ingresa en la interfaz gráfica*/

    private String detalle;/*Atributo para la razón del cargue del archivo, se ingresa en la interfaz gráfica*/

    private UploadedFile uploadedFile;
    /*Atributo para el archivo a procesar, se carga en la interfaz gráfica*/
    private String nombreArchivo;
    public List<HhppMgl> hhppListArchivo;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geoPoliticoFacade;
    @EJB
    private MarcasMglFacadeLocal marcasMglFacadeLocal;
    @EJB
    private HhppMglFacadeLocal hhppMglFacade;
    @EJB
    private NodoMglFacadeLocal NodoMglFacade;
    @EJB
    private EstadoHhppMglFacadeLocal EstadoHhppMglFacade;
    @EJB
    private TipoHhppMglFacadeLocal TipoHhppMglFacade;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacade;
    @EJB
    private DirLogEliminacionMasivaMglFacadeLocal dirLogEliminacionMasivaMglFacade;
    private static final Logger LOGGER = LogManager.getLogger(EliminarHHPPMasivoBean.class);
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private boolean selectedAllHHPP = false;

    @PostConstruct
    public void init() {
        try {
            try {
                try {
                    hhppMglList = (List<HhppMgl>) session.getAttribute("hhppMglList");
                    SecurityLogin securityLogin = new SecurityLogin(facesContext);
                    if (!securityLogin.isLogin()) {
                        if (!response.isCommitted()) {
                            response.sendRedirect(securityLogin.redireccionarLogin());
                        }
                        return;
                    }
                    renderFormCAM = "false";
                    renderFormUNI = "false";
                    usuarioVT = securityLogin.getLoginUser();
                    
                    if (usuarioVT == null) {
                        session.getAttribute("usuarioM");
                        usuarioVT = (String) session.getAttribute("usuarioM");
                    } else {
                        session = (HttpSession) facesContext.getExternalContext().getSession(false);
                        session.setAttribute("usuarioM", usuarioVT);
                    }
                } catch (IOException e) {
                    LOGGER.error("Error en init. " + e.getMessage(), e);
                } catch (Exception e) {
                    LOGGER.error("Error en init. " + e.getMessage(), e);
                }
                
                Object deleteHHPPFlash = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("tipo");
                if (deleteHHPPFlash != null) {
                    if (deleteHHPPFlash.equals("UNI")) {
                        renderFormCAM = "false";
                        renderFormUNI = "true";
                    } else {
                        renderFormCAM = "true";
                        renderFormUNI = "false";
                    }
                }
                Object geoPoliticoFlash = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("gpo");
                
                deshabilitar = false;
                if (geoPoliticoFlash != null) {
                    geoPolitico = (GeograficoPoliticoMgl) geoPoliticoFlash;
                    if (geoPolitico.getGpoNombre() != null && !geoPolitico.getGpoNombre().isEmpty()) {
                        deshabilitar = true;
                    }
                    
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gpo", null);
                    
                    if (dptoList == null || dptoList.isEmpty()) {
                        dptoList = geoPoliticoFacade.findDptos();
                        if (ciudadList == null || ciudadList.isEmpty()) {
                            consultarCiudades();
                        }
                    }
                }
                if (geoPoliticoList == null || geoPoliticoList.isEmpty()) {
                    geoPoliticoList = new ArrayList<GeograficoPoliticoMgl>();
                    geoPoliticoList = geoPoliticoFacade.findAll();
                }
                GeograficoPoliticoMgl gio = geoPolitico;
            } catch (ApplicationException e) {
                LOGGER.error("Error en init. " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en init. " + e.getMessage(), e);
            }

            try {
                listMarcasMgl = new ArrayList<MarcasMgl>();
                listMarcasMgl = marcasMglFacadeLocal.findAll();
                
                listNodoMgl = new ArrayList<NodoMgl>();
                listNodoMgl = NodoMglFacade.findAll();
                
                listEstadoHhppMgl = new ArrayList<EstadoHhppMgl>();
                listEstadoHhppMgl = EstadoHhppMglFacade.findAll();
                
                listTipoHhppMgl = new ArrayList<TipoHhppMgl>();
                listTipoHhppMgl = TipoHhppMglFacade.findAll();
            } catch (ApplicationException e) {
                LOGGER.error("Error en init. " +e.getMessage(), e);  
            } catch (Exception e) {
                LOGGER.error("Error en init. " +e.getMessage(), e);  
            }
            hhppMgl = new HhppMgl();
            hhppMgl.setNodId(new NodoMgl());
            if (count == 0) {
                actualizarlParametrosMgl("I");
            }
            
            if (getProcesoH().equals("A")) {
                msn = "Eliminacion efectuandose";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (Exception e) {
            LOGGER.error("Error en init. " +e.getMessage(), e);  
        }
    }

    public void consultarCiudades() throws ApplicationException {
        try {
            if (geoPolitico.getGeoGpoId() != null && !geoPolitico.getGeoGpoId().toString().isEmpty()) {
                ciudadList = geoPoliticoFacade.findCiudades(geoPolitico.getGeoGpoId());
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER); 
            throw e;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER); 
            throw e;
        }
    }

    public String button1_Action() {
        if (getTipoEliminacion().getValue() == null) {
            message = "Seleccionar tipo de eliminación ";
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Seleccionar tipo de eliminación ."));
            return "eliminarHHPPMasivo";
        }
        String opcion = getTipoEliminacion().getValue().toString();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("tipo", opcion);
        geoPolitico = new GeograficoPoliticoMgl();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gpo", geoPolitico);
        return "eliminarHHPPMasivoDetalle";
    }

    public void buscarHhppMgl() {
        message = "";
        if (hhppListDele != null) {
            hhppListDele.clear();
        }
        if (hhppListNoDele != null) {
            hhppListNoDele.clear();
        }
        try {
            String geoId = getGeoGpo();
            geoId = geoId == null || geoId.trim().isEmpty() ? null : geoId;
            BigDecimal geo = new BigDecimal(geoId);
            geo = geo.equals(BigDecimal.ZERO) ? null : geo;

            String blackLis = getMarcas();
            BigDecimal blackLisDec = new BigDecimal(blackLis);
            blackLis = blackLis == null || blackLis.trim().isEmpty() || blackLis.equals("0") ? null : blackLis;
            blackLisDec = blackLis == null ? null : blackLisDec;

            String tipoUnidad = getTipoUnidad();
            tipoUnidad = tipoUnidad == null || tipoUnidad.trim().isEmpty() || tipoUnidad.equals("0") ? null : tipoUnidad;

            String estadoUnidad = getEstadoUnidad();
            estadoUnidad = estadoUnidad == null || estadoUnidad.trim().isEmpty() || estadoUnidad.equals("0") ? null : estadoUnidad;
            String calle = hhppMgl.getHhpCalle();
            calle = calle == null || calle.trim().isEmpty() ? null : calle;
            String placa = hhppMgl.getHhpPlaca();
            placa = placa == null || placa.trim().isEmpty() ? null : placa;
            String apartamento = hhppMgl.getHhpApart();
            apartamento = apartamento == null || apartamento.trim().isEmpty() ? null : apartamento;
            BigDecimal hhppId = hhppMgl.getHhpId();
            hhppId = hhppId == null || hhppId.equals(BigDecimal.ZERO) ? null : hhppId;

            NodoMgl nodom = new NodoMgl();
            nodom.setNodId(new BigDecimal(nodo));
            hhppMgl.setNodId(nodom);
            if (hhppMgl.getNodId().getNodId().equals(BigDecimal.ZERO)) {
                nodom = null;
            }

            Date fechaCreacion = hhppMgl.getFechaCreacion();

            String comunidad = null;
            String division = null;

            if (geo == null && blackLis == null && tipoUnidad == null &&
                    estadoUnidad == null && calle == null && placa == null &&
                    apartamento == null && hhppId == null && nodom == null && fechaCreacion == null) {
                message = "Selecciona por lo menos un campo es requerido";
                session.setAttribute("message", message);
                return;
            }
            hhppMglList = hhppMglFacade
                    .findHhppBySelect(geo, blackLisDec, tipoUnidad, estadoUnidad,
                    calle, placa, apartamento, hhppId, nodom, fechaCreacion, comunidad, division);

            estadoHHPP = estadoUnidad;
            tipoHHPP = tipoUnidad;
            geoHHPP = geo == null || geo.equals(BigDecimal.ZERO) ? null : geo.toString();
            blackLisHHPP = blackLis;
            calleHHPP = calle;
            placaHHPP = placa;
            apartamentoHHPP = apartamento;
            hhppIdHHPP = hhppId == null || hhppId.equals(BigDecimal.ZERO) ? null : hhppId.toString();
            nodomHHPP = nodom == null || nodom.getNodId().equals(BigDecimal.ZERO) ? null : nodom.getNodId().toString();
            fechaCreacionHHPP = fechaCreacion == null || fechaCreacion.toString().trim().isEmpty() ? null : fechaCreacion.toString();

            session.setAttribute("hhppMglList", hhppMglList);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhppMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhppMgl. ", e, LOGGER);
        }
    }

    public void eliminarHHPPsMasivo() {
        try {
            if (hhppMglList == null || hhppMglList.isEmpty()) {
                message = "Lista HHPP  vacia";
                session.setAttribute("message", message);
                return;
            }
            if (!finHilo && !((hhppListDele == null || hhppListDele.isEmpty()) && (hhppListNoDele == null || hhppListNoDele.isEmpty()))) {
                message = "Hacer Validacion de busqueda";
                session.setAttribute("message", message);
                return;
            }
            if (!finHilo && (hhppListDele == null || hhppListDele.isEmpty()) && (hhppListNoDele == null || hhppListNoDele.isEmpty())) {
                
                if (hhppListDele != null) {
                    hhppListDele.clear();
                }
                if (hhppListNoDele != null) {
                    hhppListNoDele.clear();
                }
                
                List<HhppMgl> hhppList = new ArrayList<HhppMgl>();
                for (HhppMgl hp : hhppMglList) {
                    if (hp.isSelected()) {
                        hhppList.add(hp);
                    }
                }
                
                if (hhppList.isEmpty()) {
                    message = "No se ha seleccionado HHPP a eliminar";
                    session.setAttribute("message", message);
                    return;
                }
                
                if (count == 0) {
                    count++;
                    actualizarlParametrosMgl("A");
                    
                    String Filtros = "FILTROS:  ";
                    if (estadoHHPP != null) {
                        String filtroEstado = null;
                        for (EstadoHhppMgl estado : listEstadoHhppMgl) {
                            if (estado.getEhhID().equals(estadoHHPP)) {
                                filtroEstado = estado.getEhhNombre();
                                break;
                            }
                        }
                        Filtros += " Estado de HHPP = " + filtroEstado + ", ";
                    }
                    
                    if (tipoHHPP != null) {
                        String filtro = null;
                        for (TipoHhppMgl tipo : listTipoHhppMgl) {
                            if (tipo.getThhID().equals(tipoHHPP)) {
                                filtro = tipo.getThhValor();
                                break;
                            }
                        }
                        Filtros += " Tipo de HHPP = " + filtro + ", ";
                    }
                    
                    if (geoHHPP != null) {
                        String filtro = null;
                        for (GeograficoPoliticoMgl ciudad : ciudadList) {
                            if (ciudad.getGpoId().equals(new BigDecimal(geoHHPP))) {
                                filtro = ciudad.getGpoNombre();
                                break;
                            }
                        }
                        Filtros += " Ciudad = " + filtro + ", ";
                    }
                    
                    if (blackLisHHPP != null) {
                        String filtro = null;
                        for (MarcasMgl blackLis : listMarcasMgl) {
                            if (blackLis.getMarId().equals(new BigDecimal(blackLisHHPP))) {
                                filtro = blackLis.getMarNombre();
                                break;
                            }
                        }
                        Filtros += " Black List = " + filtro + ", ";
                    }
                    
                    if (calleHHPP != null) {
                        Filtros += " Calle = " + calleHHPP + ", ";
                    }
                    
                    if (placaHHPP != null) {
                        Filtros += " Placa = " + placaHHPP + ", ";
                    }
                    
                    if (apartamentoHHPP != null) {
                        Filtros += " Apartamento = " + apartamentoHHPP + ", ";
                    }
                    
                    if (hhppIdHHPP != null) {
                        Filtros += " Id HHPP = " + hhppIdHHPP + ", ";
                    }
                    
                    if (nodomHHPP != null) {
                        String filtro = null;
                        for (NodoMgl nodoL : listNodoMgl) {
                            if (nodoL.getNodId().equals(new BigDecimal(nodomHHPP))) {
                                filtro = nodoL.getNodNombre();
                                break;
                            }
                        }
                        Filtros += " Nodo = " + filtro + ", ";
                    }
                    
                    if (fechaCreacionHHPP != null) {
                        Filtros += " Fecha Creacion HHPP = " + fechaCreacionHHPP + ", ";
                    }
                    
                    DirLogEliminacionMasivaMgl dirLogEliminacionMasivaMgl = new DirLogEliminacionMasivaMgl();
                    dirLogEliminacionMasivaMgl.setLemArchivo(null);
                    dirLogEliminacionMasivaMgl.setLemFechaOperacion(new Date());
                    dirLogEliminacionMasivaMgl.setLemFiltros(Filtros);
                    dirLogEliminacionMasivaMgl.setLemUsuarioOperacion(usuarioVT);
                    try {
                        dirLogEliminacionMasivaMgl = dirLogEliminacionMasivaMglFacade.create(dirLogEliminacionMasivaMgl);
                    } catch (ApplicationException e) {
                        FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivo. ", e, LOGGER);
                    }catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivo. ", e, LOGGER);
                    }
                    
                    logId = dirLogEliminacionMasivaMgl.getLemID();
                    
                    try {
                        dirLogEliminacionMasivaMgl = dirLogEliminacionMasivaMglFacade.findDirLogEliminacionMasivaMglDaoImplByDate(dirLogEliminacionMasivaMgl.getLemFechaOperacion());
                    } catch (RuntimeException e) {
                        FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivo. ", e, LOGGER);
                    }catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivo. ", e, LOGGER);
                    }
                    
                    logId = dirLogEliminacionMasivaMgl.getLemID();
                    EliminarHhppMasivoManagedThreadsInt eHhppMt
                            = new EliminarHhppMasivoManagedThreadsInt(hhppList, tipoHHPP, estadoHHPP);
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivo. ", e, LOGGER);
        }
    }

    public void eliminarHHPPsMasivoArchivo() {
        try {
            if (hhppListArchivo == null || hhppListArchivo.isEmpty()) {
                message = "El Archivo no contiene HHPP";
                session.setAttribute("message", message);
                return;
            }
            
            if (!finHilo && !((hhppListDele == null || hhppListDele.isEmpty())
                    && (hhppListNoDele == null || hhppListNoDele.isEmpty()))) {
                message = "Hacer Carga de Archivo";
                session.setAttribute("message", message);
                return;
            }
            if (!finHilo && (hhppListDele == null || hhppListDele.isEmpty())
                    && (hhppListNoDele == null || hhppListNoDele.isEmpty())) {
                
                if (hhppListDele != null) {
                    hhppListDele.clear();
                }
                if (hhppListNoDele != null) {
                    hhppListNoDele.clear();
                }
            }
            
            tipoHHPP = null;
            estadoHHPP = null;
            if (count == 0) {
                count++;
                actualizarlParametrosMgl("A");
                
                DirLogEliminacionMasivaMgl dirLogEliminacionMasivaMgl = new DirLogEliminacionMasivaMgl();
                dirLogEliminacionMasivaMgl.setLemArchivo(nombreArchivo);
                dirLogEliminacionMasivaMgl.setLemFechaOperacion(new Date());
                dirLogEliminacionMasivaMgl.setLemFiltros(null);
                dirLogEliminacionMasivaMgl.setLemUsuarioOperacion(usuarioVT);
                
                try {
                    dirLogEliminacionMasivaMgl = dirLogEliminacionMasivaMglFacade.create(dirLogEliminacionMasivaMgl);
                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivoArchivo. ", e, LOGGER);
                }catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivoArchivo. ", e, LOGGER);
                }
                
                logId = dirLogEliminacionMasivaMgl.getLemID();
                EliminarHhppMasivoManagedThreadsInt eHhppMt
                        = new EliminarHhppMasivoManagedThreadsInt(hhppListArchivo, tipoHHPP, estadoHHPP);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHHPPsMasivoArchivo. ", e, LOGGER);
        }
    }

    public static void eliminarHHPPsMasivoRR(List<HhppMgl> hhppListD, List<HhppMgl> hhppListND) {
        if (hhppListDele == null || hhppListDele.isEmpty()) {
            hhppListDele = hhppListD;
        } else if (hhppListDele != null) {
            hhppListDele.addAll(hhppListD);
        }
        if (hhppListNoDele == null || hhppListNoDele.isEmpty()) {
            hhppListNoDele = hhppListND;
        } else if (hhppListNoDele != null) {
            hhppListNoDele.addAll(hhppListND);
        }

        ValueChangeEvent event = null;
    }

    public String actualizar(ValueChangeEvent event) {
        if (event != null) {
            event.getClass();
        }
        return "eliminarHHPPMasivoDetalle";
    }

    public String buscarParametroByAcronimo() {
        String valor = "I";
        try {
            List<ParametrosMgl> parametrosMglList;
            parametrosMglList = parametrosMglFacade.findByAcronimo(Constant.FLAG_PROCESHILO_ELIMINARMASIVOHHPP);
            valor = parametrosMglList.get(0).getParValor();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarParametroByAcronimo. ", e, LOGGER);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarParametroByAcronimo. ", e, LOGGER);
        }
        return valor;
    }

    public void actualizarlParametrosMgl(String valor) {
        try {
            List<ParametrosMgl> parametrosMglList;
            parametrosMglList = parametrosMglFacade.findByAcronimo(Constant.FLAG_PROCESHILO_ELIMINARMASIVOHHPP);
            ParametrosMgl parametrosMgl = parametrosMglList.get(0);
            parametrosMgl.setParValor(valor);
            parametrosMglFacade.update(parametrosMgl);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarlParametrosMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarlParametrosMgl. ", e, LOGGER);
        }
    }

    public void eliminarHhppMgl(HhppMgl hhppMgl) {
        try {
            hhppMglFacade.delete(hhppMgl);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHhppMgl. ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHhppMgl. ", e, LOGGER);

        }
    }

    public void selectAllHHPP() {
        selectedAllHHPP = !selectedAllHHPP;
        for (HhppMgl h : hhppMglList) {
            h.setSelected(selectedAllHHPP);
        }
    }

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        UploadedFileDefaultMemoryImpl vl = (UploadedFileDefaultMemoryImpl) value;
        if (vl.getSize() <= 87) {
            msgs.add(new FacesMessage("Archivo muy pequeño"));
        }
        if (!"application/vnd.ms-excel".equalsIgnoreCase(vl.getContentType())) {
            if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(vl.getContentType())) {
                msgs.add(new FacesMessage("Formato no admitido"));
            }
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public void cargarArchivo() throws ApplicationException {

        if (hhppListDele != null) {
            hhppListDele.clear();
        }
        if (hhppListNoDele != null) {
            hhppListNoDele.clear();
        }
        if (uploadedFile == null && uploadedFile.getFileName() != null) {
            message = "Seleccionar Archivo";
            session.setAttribute("message", message);
            return;
        }

        try {
            hhppListArchivo = new ArrayList<HhppMgl>();
            nombreArchivo = uploadedFile.getFileName();
            String file = procesarArchivo(uploadedFile);
            if (file.equalsIgnoreCase("csv")) {

                String splitBy = "\n";
                String splitBy2 = ";";

                InputStream csvFileToRead = uploadedFile.getInputStream();
                String theString = IOUtils.toString(csvFileToRead);
                String[] data = theString.split(splitBy);
                //recorremos cada Fila del archivo
                for (int i = 0; i < data.length; i++) {
                    String[] data2 = data[i].split(splitBy2);
                    int j = 0;
                    LOGGER.error("comunidad: " + data2[j++]);
                    LOGGER.error("Division: " + data2[j++]);
                    LOGGER.error("Calle: " + data2[j++]);
                    LOGGER.error("Placa: " + data2[j++]);
                    LOGGER.error("Apartamento: " + data2[j++]);
                    LOGGER.error("estado: " + data2[j++]);

                    String comunidad = data2[0].trim();
                    String division = data2[1].trim();
                    String calle = data2[2].trim();
                    String placa = data2[3].trim();
                    String apartamento = data2[4].trim();

                    List<HhppMgl> hpList = hhppMglFacade.findHhppBySelect(null, null, null, null, calle, placa, apartamento, null, null, null, comunidad, division);

                    if (hpList != null && !hpList.isEmpty()) {
                        HhppMgl hp = hpList.get(0);
                        hhppListArchivo.add(hp);
                    } else {
                        HhppMgl hp = new HhppMgl();
                        hp.setHhpId(null);
                        hp.setHhpCalle(calle);
                        hp.setHhpPlaca(placa);
                        hp.setHhpApart(apartamento);
                        hp.setHhpComunidad(comunidad);
                        hp.setHhpDivision(division);
                        hhppListArchivo.add(hp);

                    }
                }

                String result = "Archivo cvs Procesado Exitosamente.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, ""));
            } else if (file.equalsIgnoreCase("excel")) {

                XSSFWorkbook workbook = new XSSFWorkbook(uploadedFile.getInputStream());

                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.rowIterator();
                //avanzamos le primera fila que contiene los encabezados
                rowIterator.next();
                //Recorremos cada fila del archivo
                List<ChangeUnitAddressRequest> changeUnitAddressList = new ArrayList<ChangeUnitAddressRequest>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    int i = 0;


                    String comunidad = valueCell(row.getCell(0)).trim();
                    String division = valueCell(row.getCell(1)).trim();
                    String calle = valueCell(row.getCell(2)).trim();
                    String placa = valueCell(row.getCell(3)).toUpperCase().trim();
                    String apartamento = valueCell(row.getCell(4)).replace(".0", "").trim();
                    List<HhppMgl> hpList = hhppMglFacade.findHhppBySelect(null, null, null, null, calle, placa, apartamento, null, null, null, comunidad, division);

                    if (hpList != null && !hpList.isEmpty()) {
                        HhppMgl hp = hpList.get(0);
                        hhppListArchivo.add(hp);
                    } else {
                        HhppMgl hp = new HhppMgl();
                        hp.setHhpId(null);
                        hp.setHhpCalle(calle);
                        hp.setHhpPlaca(placa);
                        hp.setHhpApart(apartamento);
                        hp.setHhpComunidad(comunidad);
                        hp.setHhpDivision(division);
                        hhppListArchivo.add(hp);                        

                    }
                }
                List<ChangeUnitAddressRequest> noProcesados = null;
                if (!(noProcesados != null && !noProcesados.isEmpty())) {
                    String result = "Archivo excel Procesado Exitosamente.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, ""));   
                }
            } else if (file.trim().equals("xls")) {
                Workbook w;
                w = Workbook.getWorkbook(uploadedFile.getInputStream());
                Sheet hoja = w.getSheet(0);
                hoja.getCell(0, 0);
                hoja.getRows();

                for (int i = 0; i < hoja.getRows(); i++) {
                    String comunidad = hoja.getCell(0, i).getContents().trim();
                    String division = hoja.getCell(1, i).getContents().trim();
                    String calle = hoja.getCell(2, i).getContents().trim();
                    String placa = hoja.getCell(3, i).getContents().trim();
                    String apartamento = hoja.getCell(4, i).getContents().trim();

                    List<HhppMgl> hpList = hhppMglFacade.findHhppBySelect(null, null, null, null, calle, placa, apartamento, null, null, null, comunidad, division);
                    if (hpList != null && !hpList.isEmpty()) {
                        HhppMgl hp = hpList.get(0);
                        hhppListArchivo.add(hp);
                    } else {
                        HhppMgl hp = new HhppMgl();
                        hp.setHhpId(null);
                        hp.setHhpCalle(calle);
                        hp.setHhpPlaca(placa);
                        hp.setHhpApart(apartamento);
                        hp.setHhpComunidad(comunidad);
                        hp.setHhpDivision(division);
                        hhppListArchivo.add(hp);                       

                    }
                }
                String result = "Archivo cvs Procesado Exitosamente.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, result, ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(file));
            }
        } catch (ApplicationException | IOException | IndexOutOfBoundsException | BiffException e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo. ", e, LOGGER);
        }
    }

    private String procesarArchivo(UploadedFile uf) throws ApplicationException {
        try {
            if (uf.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                String splitBy = "\n";
                String splitBy2 = ";";

                InputStream csvFileToRead = uf.getInputStream();
                String theString = IOUtils.toString(csvFileToRead);
                String[] data = theString.split(splitBy);
                //recorremos cada Fila del archivo
                for (int i = 1; i < data.length; i++) {
                    String[] data2 = data[i].split(splitBy2);
                    //recorremos cada columna de la fila
                    for (int j = 0; j < data2.length; j++) {
                        if (data2[j].equalsIgnoreCase("")) {
                            return "error en el Archivo valor faltante linea: " + (j + 1) + " fila: " + i;
                        }

                        if (j == 6) {
                            j = data2.length;
                        }
                    }
                }

                if (data.length <= 1) {
                    return "Estructura del archivo incorrecta";
                } else {
                    try {
                        Workbook.getWorkbook(uf.getInputStream());
                    } catch (IOException | BiffException e) {
                        LOGGER.error("Error en procesarArchivo. " +e.getMessage(), e);   
                        return "csv";
                    }catch (Exception e) {
                        LOGGER.error("Error en procesarArchivo. " +e.getMessage(), e);   
                        return "csv";
                    }
                    return "xls";
                }
            } else {
                XSSFWorkbook workbookOri = new XSSFWorkbook(uf.getInputStream());
                XSSFSheet sheet = workbookOri.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                //recorremos cada Fila del archivo
                int numfila = 0; //contador de fila
                while (rowIterator.hasNext()) {
                    numfila++;
                    int numcell = 0;// contador de celda
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    //se omiten los encabezados
                    cellIterator.next();
                    //recorremos cada columna de la fila

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        //Check the cell type and format accordingly
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                Double valueCell = cell.getNumericCellValue();
                                if (valueCell.toString().trim().isEmpty()) {
                                    return "Error en el Archivo valor faltante";
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                String valueCellStr = cell.getStringCellValue();
                                if (valueCellStr.equalsIgnoreCase("") || valueCellStr.toString().trim().isEmpty()) {
                                    return "Error en el Archivo valor faltante";
                                }
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                try {
                                    if (cellIterator.next().getCellType() != Cell.CELL_TYPE_BLANK) {
                                        return "Error en el Archivo valor faltante en la fila: " + numfila + " en la celda: " + (numcell + 1);
                                    } else if (rowIterator.hasNext()) {
                                        if (cellIterator.next().getCellType() != Cell.CELL_TYPE_BLANK) {
                                            return "Error en el Archivo valor faltante en la fila: " + numfila + " en la celda: " + (numcell + 1);
                                        }
                                    }
                                } catch (NoSuchElementException e) {
                                    LOGGER.error("Error en procesarArchivo " + e.getMessage(), e);
                                }catch (Exception e) {
                                    LOGGER.error("Error en procesarArchivo " + e.getMessage(), e);
                                }
                        }
                    }
                }
                return "excel";
            }
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en procesarArchivo " + e.getMessage(), e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error en procesarArchivo " + e.getMessage(), e);
        }
    }

    private String valueCell(Cell cell) {
        if (cell != null) {
            String value;
            int tipoCelda = cell.getCellType();
            switch (tipoCelda) {
                case Cell.CELL_TYPE_NUMERIC:
                    Double valueCell = cell.getNumericCellValue();
                    if (!(valueCell.toString().trim().isEmpty())) {
                        value = valueCell.toString();
                        return value;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    String valueCellStr = cell.getStringCellValue();
                    if (!(valueCellStr == null) || !(valueCellStr.toString().trim().isEmpty())) {
                        return valueCellStr;
                    }
                    break;
            }
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error en el Archivo valor faltante "));
            return "Celda Vacia";
        }
    }

    public String summitPage() {
        return null;
    }

    public HtmlSelectOneRadio getTipoEliminacion() {
        return tipoEliminacion;
    }

    public void setTipoEliminacion(HtmlSelectOneRadio tipoEliminacion) {
        this.tipoEliminacion = tipoEliminacion;
    }

    public List<GeograficoPoliticoMgl> getGeoPoliticoList() {
        return geoPoliticoList;
    }

    public void setGeoPoliticoList(List<GeograficoPoliticoMgl> geoPoliticoList) {
        this.geoPoliticoList = geoPoliticoList;
    }

    public GeograficoPoliticoMgl getGeoPolitico() {
        return geoPolitico;
    }

    public void setGeoPolitico(GeograficoPoliticoMgl geoPolitico) {
        this.geoPolitico = geoPolitico;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
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

    public String getMarcas() {
        return marcas;
    }

    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }

    public List<MarcasMgl> getListMarcasMgl() {
        return listMarcasMgl;
    }

    public void setListMarcasMgl(List<MarcasMgl> listMarcasMgl) {
        this.listMarcasMgl = listMarcasMgl;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<HhppMgl> getHhppMglList() {
        return hhppMglList;
    }

    public void setHhppMglList(List<HhppMgl> hhppMglList) {
        this.hhppMglList = hhppMglList;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public List<NodoMgl> getListNodoMgl() {
        return listNodoMgl;
    }

    public void setListNodoMgl(List<NodoMgl> listNodoMgl) {
        this.listNodoMgl = listNodoMgl;
    }

    public List<HhppMgl> getHhppListDele() {
        return hhppListDele;
    }

    public void setHhppListDele(List<HhppMgl> hhppListDele) {
        EliminarHHPPMasivoBean.hhppListDele = hhppListDele;
    }

    public List<HhppMgl> getHhppListNoDele() {
        return hhppListNoDele;
    }

    public void setHhppListNoDele(List<HhppMgl> hhppListNoDele) {
        EliminarHHPPMasivoBean.hhppListNoDele = hhppListNoDele;
    }

    public HtmlDataTable getDataTableD() {
        return dataTableD;
    }

    public void setDataTableD(HtmlDataTable dataTableD) {
        this.dataTableD = dataTableD;
    }

    public HtmlDataTable getDataTableND() {
        return dataTableND;
    }

    public void setDataTableND(HtmlDataTable dataTableND) {
        this.dataTableND = dataTableND;
    }

    public String getProcesoH() {
        this.procesoH = buscarParametroByAcronimo();
        return procesoH;
    }

    public void setProcesoH(String procesoH) {
        this.procesoH = procesoH;
    }

    public List<EstadoHhppMgl> getListEstadoHhppMgl() {
        return listEstadoHhppMgl;
    }

    public void setListEstadoHhppMgl(List<EstadoHhppMgl> listEstadoHhppMgl) {
        this.listEstadoHhppMgl = listEstadoHhppMgl;
    }

    public List<TipoHhppMgl> getListTipoHhppMgl() {
        return listTipoHhppMgl;
    }

    public void setListTipoHhppMgl(List<TipoHhppMgl> listTipoHhppMgl) {
        this.listTipoHhppMgl = listTipoHhppMgl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGeoGpo() {
        return geoGpo;
    }

    public void setGeoGpo(String geoGpo) {
        this.geoGpo = geoGpo;
    }

    public boolean isFinHilo() {
        return finHilo;
    }

    public void setFinHilo(boolean finHilo) {
        EliminarHHPPMasivoBean.finHilo = finHilo;
    }

    public GeograficoPoliticoMgl getGeograficoPoliticoDep() {
        return geograficoPoliticoDep;
    }

    public void setGeograficoPoliticoDep(GeograficoPoliticoMgl geograficoPoliticoDep) {
        this.geograficoPoliticoDep = geograficoPoliticoDep;
    }

    public HtmlSelectOneMenu getDepartamento() {
        return departamento;
    }

    public void setDepartamento(HtmlSelectOneMenu departamento) {
        this.departamento = departamento;
    }

    public String getRenderFormCAM() {
        return renderFormCAM;
    }

    public void setRenderFormCAM(String renderFormCAM) {
        this.renderFormCAM = renderFormCAM;
    }

    public String getRenderFormUNI() {
        return renderFormUNI;
    }

    public void setRenderFormUNI(String renderFormUNI) {
        this.renderFormUNI = renderFormUNI;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public HtmlPanelGrid getEliminacionArch() {
        return eliminacionArch;
    }

    public void setEliminacionArch(HtmlPanelGrid eliminacionArch) {
        this.eliminacionArch = eliminacionArch;
    }

    public HtmlPanelGrid getEliminacionInd() {
        return eliminacionInd;
    }

    public void setEliminacionInd(HtmlPanelGrid eliminacionInd) {
        this.eliminacionInd = eliminacionInd;
    }

    public DireccionMglFacadeLocal getDireccionMglFacade() {
        return direccionMglFacade;
    }

    public void setDireccionMglFacade(DireccionMglFacadeLocal direccionMglFacade) {
        this.direccionMglFacade = direccionMglFacade;
    }

    public GeograficoPoliticoMgl getGeograficoPoliticoCiu() {
        return geograficoPoliticoCiu;
    }

    public void setGeograficoPoliticoCiu(GeograficoPoliticoMgl geograficoPoliticoCiu) {
        this.geograficoPoliticoCiu = geograficoPoliticoCiu;
    }

    // Actions Paging--------------------------------------------------------------------------
    public void pageFirst() {
        dataTable.setFirst(0);
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        HtmlDataTable t = dataTable;
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public String getPageActual() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
        int actual = (dataTable.getFirst() / rows) + 1;

        paginaList = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }

        pageActual = actual + " de " + totalPaginas;

        if (numPagina == null) {
            numPagina = "1";
        }
        return pageActual;
    }

    public void irPagina(ValueChangeEvent event) {
        try {
            String value = event.getNewValue().toString();
            dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
        } catch (NumberFormatException ex) {
            LOGGER.error("Error en irPagina " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en irPagina " + ex.getMessage(), ex);
        }
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

    public void setPageActual(String pageActual) {

        this.pageActual = pageActual;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------  

    // Actions Paging--------------------------------------------------------------------------
    public void pageFirstD() {
        dataTableD.setFirst(0);
        numPagina = (dataTableD.getFirst() / dataTableD.getRows()) + 1 + "";
    }

    public void pagePreviousD() {
        dataTableD.setFirst(dataTableD.getFirst() - dataTableD.getRows());
        numPagina = (dataTableD.getFirst() / dataTableD.getRows()) + 1 + "";
    }

    public void pageNextD() {
        dataTableD.setFirst(dataTableD.getFirst() + dataTableD.getRows());
        numPagina = (dataTableD.getFirst() / dataTableD.getRows()) + 1 + "";
    }

    public void pageLastD() {
        int count = dataTableD.getRowCount();
        int rows = dataTableD.getRows();
        HtmlDataTable t = dataTableD;
        dataTableD.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTableD.getFirst() / dataTableD.getRows()) + 1 + "";
    }

    public String getPageActualD() {
        int count = dataTableD.getRowCount();
        int rows = dataTableD.getRows();
        int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
        int actual = (dataTableD.getFirst() / rows) + 1;

        paginaListD = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaListD.add(i);
        }

        pageActualD = actual + " de " + totalPaginas;

        if (numPaginaD == null) {
            numPaginaD = "1";
        }
        return pageActualD;
    }

    public void irPaginaD(ValueChangeEvent event) {
        try {
            String value = event.getNewValue().toString();
            dataTableD.setFirst((new Integer(value) - 1) * dataTableD.getRows());
        } catch (NumberFormatException ex) {
            LOGGER.error("Error en irPaginaD " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en irPaginaD " + ex.getMessage(), ex);
        }

    }

    public void setPageActualD(String pageActualD) {
        this.pageActualD = pageActualD;
    }

    public String getNumPaginaD() {
        return numPaginaD;
    }

    public void setNumPaginaD(String numPaginaD) {
        this.numPaginaD = numPaginaD;
    }

    public List<Integer> getPaginaListD() {
        return paginaListD;
    }

    public void setPaginaListD(List<Integer> paginaListD) {
        this.paginaListD = paginaListD;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------  
    // Actions Paging--------------------------------------------------------------------------
    public void pageFirstND() {
        dataTableND.setFirst(0);
        numPaginaND = (dataTableND.getFirst() / dataTableND.getRows()) + 1 + "";
    }

    public void pagePreviousND() {
        dataTableND.setFirst(dataTableND.getFirst() - dataTableND.getRows());
        numPaginaND = (dataTableND.getFirst() / dataTableND.getRows()) + 1 + "";
    }

    public void pageNextND() {
        dataTableND.setFirst(dataTableND.getFirst() + dataTableND.getRows());
        numPaginaND = (dataTableND.getFirst() / dataTableND.getRows()) + 1 + "";
    }

    public void pageLastND() {
        int count = dataTableND.getRowCount();
        int rows = dataTableND.getRows();
        HtmlDataTable t = dataTableND;
        dataTableND.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPaginaND = (dataTableND.getFirst() / dataTableND.getRows()) + 1 + "";
    }

    public String getPageActualND() {
        int count = dataTableND.getRowCount();
        int rows = dataTableND.getRows();
        int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
        int actual = (dataTableND.getFirst() / rows) + 1;

        paginaListND = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaListND.add(i);
        }

        pageActualND = actual + " de " + totalPaginas;

        if (numPaginaND == null) {
            numPaginaND = "1";
        }
        return pageActualND;
    }

    public void irPaginaND(ValueChangeEvent event) {
        try {
            String value = event.getNewValue().toString();
            dataTableND.setFirst((new Integer(value) - 1) * dataTableND.getRows());
        } catch (NumberFormatException ex) {
            LOGGER.error("Error en irPaginaND. " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error en irPaginaND. " + ex.getMessage(), ex);
        }
    }

    public void setPageActualND(String pageActualND) {
        this.pageActualND = pageActualND;
    }

    public String getNumPaginaND() {
        return numPaginaND;
    }

    public void setNumPaginaND(String numPaginaND) {
        this.numPaginaND = numPaginaND;
    }

    public List<Integer> getPaginaListND() {
        return paginaListND;
    }

    public void setPaginaListND(List<Integer> paginaListND) {
        this.paginaListND = paginaListND;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------  
    public void crearHhppMgl800() {

        DireccionRRManager manager = new DireccionRRManager(true);

        String placa = "8-21";
        String calle = "CL 207";
        for (int i = 101; i <= 520; i++) {
            if (i == 121) {
                i = 201;
            }
            if (i == 221) {
                i = 301;
            }
            if (i == 321) {
                i = 401;
            }
            if (i == 421) {
                i = 501;
            }
            String ok = manager.rHRs(i + "", placa, calle);
            if (ok.equals("OK")) {
                try {
                    HhppMgl hhpp = new HhppMgl();
                    hhpp.setDirId(new BigDecimal(6583));
                    NodoMgl nodom = new NodoMgl();
                    nodom.setNodId(new BigDecimal(183));
                    hhpp.setNodId(nodom);
                    hhpp.setThhId("C");
                    hhpp.setThcId(new BigDecimal(9));
                    hhpp.setThrId(BigDecimal.ONE);
                    hhpp.setFechaCreacion(new Date());
                    EstadoHhppMgl estadoHhppMgl = new EstadoHhppMgl();
                    estadoHhppMgl.setEhhID("C");
                    hhpp.setEhhId(estadoHhppMgl);
                    hhpp.setHhpCalle(calle);
                    hhpp.setHhpPlaca(placa);
                    hhpp.setHhpApart(i + "");
                    hhpp.setHhpComunidad("PER");
                    hhpp.setHhpDivision("REC");


                    hhppMglFacade.create(hhpp);
                    message = "Proceso exitoso";
                    session.setAttribute("message", message);
                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError("Error en crearHhppMgl800. ", e, LOGGER);
                    message = "Proceso falló";
                    session.setAttribute("message", message);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en crearHhppMgl800. ", e, LOGGER);
                    message = "Proceso falló";
                    session.setAttribute("message", message);
                }
            }
        }
    }

}
