package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.FiltroConsultaCuentaMatriz;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalRRFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.CmtCoverEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase ManagedBean para consulta de Cuentas Matrices.
 *
 * @author camargomf
 *
 */
@ManagedBean(name = "consultaCuentasMatricesBean")
@ViewScoped
@Log4j2
public class ConsultaCuentasMatricesBean implements Serializable {

    private String usuarioVT = null;

    /* Injection EJB */
    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacade;
   //@EJB
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal opcionesRolMglFacadeLocal;
    @EJB
    private CmtRegionalRRFacadeLocal cmtRegionalRRFacadeLocal;
     @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuarioServicesFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;

    /* Class Instances */
    private DrDireccion drDireccion = new DrDireccion();   

    private ArrayList<String> listBarrios = new ArrayList<String>();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "CUENTASMATRICES";
    private final String VALIDARBUSCM = "VALIDARBUSCM";

    /* Variables */
    private String numeroCuenta;
    //espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl
    private String numeroCuentaMGL;
    private String numeroOt;
    private String serialEquipo;
    private String cuentaSucriptor;
    private String nombreCuenta;
    private String rrRegional;
    private String rrCiudad;
    private String administracion;
    private String administrador;
    private String telefono;
    private BigDecimal departamento = null;
    private BigDecimal ciudad = null;
    private BigDecimal municipio = null;
    private BigDecimal centroProblado = null;
    private String barrio;
    private String selectedBarrio;
    private List<String> barrioList = new ArrayList<String>();
    private List<RrCiudades> ciudadesList;
    private boolean habilitaObj = false;
    public List<GeograficoPoliticoMgl> departamentoList;
    public List<GeograficoPoliticoMgl> ciudadList;
    public List<GeograficoPoliticoMgl> centroPobladoList;
    private String multiorigen;
    private List<CmtCuentaMatrizMgl> cuentaMatrizList;
    
    private CmtCuentaMatrizMgl cuentaMatriz;
    private String direccionBusqueda;
    private boolean otroBarrio;
    private boolean componenteEsVisible = false;
    private boolean ciudadObligatoria = true;
    private boolean direccionValidada = false;
    private int actual;
    private HashMap<String, Object> params;
    private String pageActual;
    private String numPagina;
    private List<Integer> paginaList;
    private boolean pintarPaginado = true;
    private List<String> coberturasListTecnologias = new ArrayList<>();
    SecurityLogin securityLogin;
    private List<String> barrioslist;
    private List<CmtRegionalRr> lstCmtRegionalRrs;
    private  List<String> tecnologiasCables;
    private  List<String> tecnologiasVarias;
    private int nuevaPageActual;
    private List<CmtCuentaMatrizMgl> cuentaMatrizListAux;
    private List<CmtBasicaMgl> tecnologiasBasicaList;
    private List<String> tecnologiasList;
    private boolean isRojo=false;
    private boolean isAzul=false;
    private boolean isVerde=false;
    private boolean validaDireccion;
    private boolean direccionMultiorigen;
    private ValidadorDireccionBean validadorDireccionBean;
    private UsuariosServicesDTO usuarioLogin;
    private GeograficoPoliticoMgl centroPobladoSeleccionado;
    private GeograficoPoliticoMgl ciudadSeleccionada;
    private String direccionRespuestaGeoBean;


    public ConsultaCuentasMatricesBean() {
        
            try {
                securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                 usuarioVT = securityLogin.getLoginUser();

                  
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }
            } catch (IOException e) {
                 FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
            }

    }

    /**
     * Método inicial que es ejecutado después de que la inyeccion de
     * dependencias haya finzalizado
     */
    @PostConstruct
    public void init() {     
        try {
            lstCmtRegionalRrs = cmtRegionalRRFacadeLocal.findAllRegional();
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
            cuentaMatriz = cuentaMatrizBean.getCuentaMatriz();
            //JDHT
            obtenerTipoTecnologiaList();
            tecnologiasCoberturaCuentaMatriz();
            tecnologiasCables = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz, true);
            tecnologiasVarias = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz,false);
            usuarioLogin = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
           
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError(ConsultaCuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }

    }
    
     /**
     * Función que obtiene tipo de tecnología
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoTecnologiaList() {
        try {
            //Obtiene valores de tipo de tecnología
            tecnologiasList = new ArrayList<>();
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiasBasicaList
                    = cmtBasicaMglFacade.findByTipoBasica(tipoBasicaTipoTecnologia);
            if (tecnologiasBasicaList != null && !tecnologiasBasicaList.isEmpty()) {
                for (CmtBasicaMgl basicaMgl : tecnologiasBasicaList) {
                    tecnologiasList.add(basicaMgl.getCodigoBasica());
                }
            }
            
        } catch (ApplicationException e) {
            String msn = "Error al obtener el tipo de tecnología.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtener la lista de comunidades.
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerListaCiudades()  {
        try {
            rrCiudad = "";
            ciudadesList = rrCiudadesFacade.findByCodregional(rrRegional);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void obtenerGeograficoPolitico() {
        try {
            if (rrCiudad != null) {
                GeograficoPoliticoMgl geograficoPoliticoMgl = geograficoPoliticoMglFacadeLocal.findCityByComundidad(rrCiudad);
                departamento = geograficoPoliticoMgl.getGeoGpoId();
                municipio = geograficoPoliticoMgl.getGpoId();
                ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(departamento);
                centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(municipio);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @throws ApplicationException
     */
    public void consultarCiudades() {
        try {
            if(departamento != null){
            municipio = null;
            centroProblado = null;
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(departamento);
            }else{
                ciudadSeleccionada = null;
                centroProblado = null;
                ciudadList = new ArrayList();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * Obtener la lista de Centros Poblados.
     *
     * @throws ApplicationException
     */
    public void consultarCentroPoblado()  {
        try {
            centroProblado = null;
            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(municipio);
            esMultiorigen();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
     /**
     * Obtiene el objeto completo del centro poblado seleccionado
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoSeleccionado()  {
        try {
            if(centroProblado != null && !centroProblado.equals(BigDecimal.ZERO)
                    && municipio != null && !municipio.equals(BigDecimal.ZERO) ){
                centroPobladoSeleccionado = geograficoPoliticoMglFacadeLocal.findById(centroProblado);
                ciudadSeleccionada = geograficoPoliticoMglFacadeLocal.findById(municipio);
                                
            }else{
                centroPobladoSeleccionado = null;
            }
            esMultiorigen();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera consultando el centro poblado object " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @throws ApplicationException
     */
    public void esMultiorigen() {
        try {
            multiorigen = geograficoPoliticoMglFacadeLocal.esMultiorigen(municipio);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @throws ApplicationException
     */
    public void latestCmCreated() {
        try {
            List<CmtCuentaMatrizMgl> listasCM = new ArrayList<CmtCuentaMatrizMgl>();
            habilitaObj = true;
            cuentaMatriz = null;
            cuentaMatrizList = cuentaMatrizFacadeLocal.findLastTenRecords();
            cuentaMatrizListAux = cuentaMatrizList;
            pintarPaginado = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void verficarConsulta() {
        //espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl
        if (    (numeroCuenta == null || numeroCuenta.isEmpty())
                && (numeroCuentaMGL == null || numeroCuentaMGL.isEmpty())
                && (numeroOt == null || numeroOt.isEmpty())
                && (cuentaSucriptor == null || cuentaSucriptor.isEmpty())
                && (serialEquipo == null || serialEquipo.isEmpty())) {
            ciudadObligatoria = true;
        } else {
            ciudadObligatoria = false;
        }
    }

    public String generarConsulta() {
        try {            
            if(direccionBusqueda != null && !direccionBusqueda.isEmpty()
                    && centroProblado != null && !centroProblado.equals(BigDecimal.ZERO)
                    && !direccionValidada){
                 FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Es necesario validar la dirección digitada.", ""));
                 return "";
            }
            
            //JDHT busqueda de CM por dirección
            if (direccionBusqueda != null && !direccionBusqueda.isEmpty()
                    && drDireccion != null && drDireccion.getIdTipoDireccion() != null
                    && !drDireccion.getIdTipoDireccion().isEmpty()
                    && centroProblado != null && !centroProblado.equals(BigDecimal.ZERO)
                    && direccionValidada) {
                if(direccionMultiorigen && (selectedBarrio == null || selectedBarrio.isEmpty())){                    
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "La dirección es multiorigen y es necesario ingresar o seleccionar un barrio.", ""));   
                    return "";                    
                }
                
                //si le direccion AVENIDA respuesta del geo esta llena se envia para ser buscada
                if(direccionRespuestaGeoBean != null && !direccionRespuestaGeoBean.isEmpty()){
                    drDireccion.setDireccionRespuestaGeo(direccionRespuestaGeoBean);
                }
                
                cuentaMatrizList = cuentaMatrizFacadeLocal
                        .findCuentasMatricesByDrDireccion(centroPobladoSeleccionado, drDireccion);
                if (cuentaMatrizList == null || cuentaMatrizList.isEmpty()) {
                     habilitaObj = false;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "No se obtuvieron Cuentas Matrices con la dirección digitada.", ""));                    
                }else{
                    habilitaObj = true;
                }
            } else {
                pintarPaginado = true;
                buscar();
                listInfoByPage(1);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     *
     * Execute the search of CuentaMatriz list with params
     *
     */
    public void buscar() {
        try {
            params = new HashMap<String, Object>();

            params.put("cuentaSucriptor", this.cuentaSucriptor);
            params.put("serialEquipo", this.serialEquipo);

            params.put("numeroCuenta", this.numeroCuenta);
            //espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl
            params.put("numeroCuentaMGL", this.numeroCuentaMGL);
            params.put("idOt", this.numeroOt);
            params.put("nombreCuenta", this.nombreCuenta);

            params.put("geoGpoId", this.departamento);
            params.put("gpoId", this.municipio);

            params.put("codRegional", this.rrRegional);
            params.put("codCiudad", this.rrCiudad);
            params.put("idTipoDireccion", this.drDireccion.getIdTipoDireccion());
            params.put("direccionBusqueda", this.direccionBusqueda);

            /**
             * Nuevos campos de busqueda
             *
             */
            params.put("administracion", this.administracion);
            params.put("administrador", this.administrador);
            params.put("telefono", this.telefono);
            params.put("centroPoblado", this.centroProblado);

            if (null == drDireccion) {
                return ;
            }
            // Validate the tipoDireccion field to send the correct params 
            // to the query. 
            if ("CK".equals(drDireccion.getIdTipoDireccion())) {
                //FIXME set parameters to search via CALLE-CARRERA
                params.put("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                params.put("numViaPrincipal", drDireccion.getNumViaPrincipal());
                params.put("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                params.put("nlPostViaP", drDireccion.getNlPostViaP());
                params.put("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                params.put("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                params.put("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                params.put("numViaGeneradora", drDireccion.getNumViaGeneradora());
                params.put("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                params.put("nlPostViaG", drDireccion.getNlPostViaG());
                params.put("letra3G", drDireccion.getLetra3G());
                params.put("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                params.put("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                params.put("placaDireccion", drDireccion.getPlacaDireccion());
            } else if ("BM".equals(drDireccion.getIdTipoDireccion())) {
                //FIXME set parameters to search via BARRIO-MANZANA
                params.put("barrio", drDireccion.getBarrio());
                params.put("mzTipoNivel1", drDireccion.getMzTipoNivel1());
                params.put("mzValorNivel1", drDireccion.getMzValorNivel1());
                params.put("mzTipoNivel2", drDireccion.getMzTipoNivel2());
                params.put("mzValorNivel2", drDireccion.getMzValorNivel2());
                params.put("mzTipoNivel3", drDireccion.getMzTipoNivel3());
                params.put("mzValorNivel3", drDireccion.getMzValorNivel3());
                params.put("mzTipoNivel4", drDireccion.getMzTipoNivel4());
                params.put("mzValorNivel4", drDireccion.getMzValorNivel4());
                params.put("mzTipoNivel5", drDireccion.getMzTipoNivel5());
                params.put("mzValorNivel5", drDireccion.getMzValorNivel5());
            } else if ("IT".equals(drDireccion.getIdTipoDireccion())) {
                //FIXME set parameters to search via INTRADUCIBLE
                params.put("cpTipoNivel1", drDireccion.getCpTipoNivel1());
                params.put("cpValorNivel1", drDireccion.getCpValorNivel1());
                params.put("cpTipoNivel2", drDireccion.getCpTipoNivel2());
                params.put("cpValorNivel2", drDireccion.getCpValorNivel2());
                params.put("cpValorNivel2", drDireccion.getCpValorNivel2());
                params.put("cpTipoNivel3", drDireccion.getCpTipoNivel3());
                params.put("cpValorNivel3", drDireccion.getCpValorNivel3());
                params.put("cpTipoNivel4", drDireccion.getCpTipoNivel4());
                params.put("cpValorNivel4", drDireccion.getCpValorNivel4());
                params.put("cpTipoNivel5", drDireccion.getCpTipoNivel5());
                params.put("cpValorNivel5", drDireccion.getCpValorNivel5());
                params.put("cpTipoNivel6", drDireccion.getCpTipoNivel6());
                params.put("cpValorNivel6", drDireccion.getCpValorNivel6());
                params.put("itTipoPlaca", drDireccion.getItTipoPlaca());
                params.put("itValorPlaca", drDireccion.getItValorPlaca());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al armar la consulta de CM.", ""));
        }
    }

    /**
     * Select a CuentaMatriz entity from data list and redirect to cuenta-matriz
     * manager page
     *
     * @param cuentaMatrizBean
     * @return
     */
    public String goCuentaMatrizEstadosSol(CuentaMatrizBean cuentaMatrizBean) {
        try {
            cuentaMatriz = cuentaMatrizBean.getCuentaMatriz();
            
            if (null != this.cuentaMatriz) {
                
                obtenerTipoTecnologiaList();
                tecnologiasCoberturaCuentaMatriz();
                tecnologiasCables = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz,true);
                tecnologiasVarias = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz,false);
                cuentaMatriz.setCoberturasList(tecnologiasList);
                FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se presentó un error al procesar la petición. Por favor intente más tarde" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
        /**
     * Select a CuentaMatriz entity from data list and redirect to cuenta-matriz
     * manager page
     *
     * @param cuentaMatrizBean
     * @return
     */
    public String goCuentaHorarioCcmm(CuentaMatrizBean cuentaMatrizBean) {
        try {
            cuentaMatriz = cuentaMatrizBean.getCuentaMatriz();
            
            if (null != this.cuentaMatriz) {
                
                obtenerTipoTecnologiaList();
                tecnologiasCoberturaCuentaMatriz();
                tecnologiasCables = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz,true);
                tecnologiasVarias = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz,false);
                cuentaMatriz.setCoberturasList(tecnologiasList);
                FacesUtil.navegarAPagina("/view/MGL/CM/tabs/horario.jsf");
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se presentó un error al procesar la petición. Por favor intente más tarde" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String goCuentaMatriz() {
        try {
            if (Objects.isNull(this.cuentaMatriz)) {
                LOGGER.warn("El objeto cuenta matriz es nulo, no es posible navegar a la vista");
                return "";
            }

            CuentaMatrizBean cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getBean("cuentaMatrizBean");
            cuentaMatrizBean.setCuentaMatriz(this.cuentaMatriz);
            obtenerTipoTecnologiaList();
            tecnologiasCoberturaCuentaMatriz();
            tecnologiasCables = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz, true);
            tecnologiasVarias = cuentaMatrizFacadeLocal.getTecnologiasInst(cuentaMatriz, false);
            cuentaMatriz.setCoberturasList(tecnologiasList);
            List<CmtSubEdificioMgl> subEdificiosMgl = cmtSubEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(cuentaMatriz);
            CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cuentaMatriz;

            if (CollectionUtils.isNotEmpty(subEdificiosMgl)) {
                cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(subEdificiosMgl);
            }

            cuentaMatrizBean.setCuentaMatriz(cmtCuentaMatrizMgl);
            FacesUtil.navegarAPagina("/view/MGL/CM/tabs/hhpp.jsf");
        } catch (ApplicationException e) {
               FacesUtil.mostrarMensajeError("Se presentó un error al procesar"
                    + " la petición. Por favor intente más tarde" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     *
     * @return @throws ApplicationException
     */
    public String exportExcel()  {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock(
                    "Exportar Tabla en Consulta Cuentas Matrices")) return StringUtils.EMPTY;

            latestCmCreated();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("LatestCMCreated");
            String[] objArr = {"Número Cuenta", "Nombre Edificio",
                "Estado", "Dirección", "Comunidad", "Barrio"};
            int rownum = 0;
            for (CmtCuentaMatrizMgl a : cuentaMatrizListAux) {
                String barrioAux="";
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
                    Cell cell = row.createCell(cellnum++);
                    if(a.getNumeroCuenta() != null){
                     cell.setCellValue(a.getNumeroCuenta().toString());   
                    }
                    Cell cell2 = row.createCell(cellnum++);
                    cell2.setCellValue(a.getNombreCuenta());
                    Cell cell3 = row.createCell(cellnum++);
                    if(a.getSubEdificioGeneral() != null){
                    cell3.setCellValue(a.getSubEdificioGeneral().getEstadoSubEdificioObj().getNombreBasica());    
                    }
                    Cell cell4 = row.createCell(cellnum++);
                    if (a.getSubEdificioGeneral() != null) {
                        cell4.setCellValue(a.getSubEdificioGeneral().getDireccion());
                    }
                   
                    Cell cell5 = row.createCell(cellnum++);
                    cell5.setCellValue(a.getComunidad());
                    Cell cell6 = row.createCell(cellnum++);
                    if (a.getDireccionPrincipal() != null && a.getDireccionPrincipal().getDireccionId() != null
                            && a.getDireccionPrincipal().getBarrio() != null && !a.getDireccionPrincipal().getBarrio().isEmpty()) {
                        barrioAux = a.getDireccionPrincipal().getBarrio();
                    }
                    cell6.setCellValue(barrioAux);
                } else {
                    Cell cell = row.createCell(cellnum++);
                    if(a.getNumeroCuenta() != null){
                    cell.setCellValue(a.getNumeroCuenta().toString());
                    }
                    Cell cell2 = row.createCell(cellnum++);
                    cell2.setCellValue(a.getNombreCuenta());
                    
                    if(a.getSubEdificioGeneral()!= null){
                    Cell cell3 = row.createCell(cellnum++);    
                    cell3.setCellValue(a.getSubEdificioGeneral().getEstadoSubEdificioObj().getNombreBasica());
                    Cell cell4 = row.createCell(cellnum++);
                    cell4.setCellValue(a.getSubEdificioGeneral().getDireccion());
                    }
                   
                    Cell cell5 = row.createCell(cellnum++);
                    cell5.setCellValue(a.getComunidad());
                    Cell cell6 = row.createCell(cellnum++);
                    if (a.getDireccionPrincipal() != null && a.getDireccionPrincipal().getDireccionId() != null
                            && a.getDireccionPrincipal().getBarrio() != null && !a.getDireccionPrincipal().getBarrio().isEmpty()) {
                        barrioAux = a.getDireccionPrincipal().getBarrio();
                    }
                    cell6.setCellValue(barrioAux);
                }
                rownum++;
            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response
                    = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + "LatestCMCreated" + formato.format(new Date())
                    + ".xlsx\"");
            OutputStream output = response.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

     /**
     * Funcion que se utiliza para validar una direccion 
     * CK por texto, en caso de no ser posible despliega
     * popup de panel de dirección.
     * 
     * @author Juan David Hernandez
     * @return @throws ApplicationException
     */
       public String irPopUpDireccion() throws ApplicationException{
        try {
             if(centroPobladoSeleccionado != null && ciudadSeleccionada != null){
                validaDireccion = false;
                drDireccion = new DrDireccion();
                direccionRespuestaGeoBean = "";
                validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
               
                validadorDireccionBean.setCiudadDelBean(ciudadSeleccionada);
                validadorDireccionBean.setIdCentroPoblado(centroProblado);
                validadorDireccionBean.setUsuarioLogin(usuarioLogin);
                //JDHT
                String barrio = "";
                //se asigna CK porque se ingresa por texto la direccion
                drDireccion.setIdTipoDireccion("CK");
                validadorDireccionBean.validarDireccion(drDireccion, direccionBusqueda, 
                        centroPobladoSeleccionado, barrio, this, 
                        ConsultaCuentasMatricesBean.class, Constant.TIPO_VALIDACION_DIR_CM, Constant.DIFERENTE_MODIFICACION_CM);
             } else {
                 FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Es necesario seleccionar una cuidad y un centro poblado para construir una dirección.", ""));
                return "";
             }

        } catch (Exception e) {
           FacesUtil.mostrarMensajeError("Se genera error en InfoCreaCMBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
       
    /**
     * Funcion que utiliza reflection y se usa desde el BEAN que valida la
     * dirección del popup de panel de direccion para modificar variables de
     * este BEAN despues de validar la dirección.
     *
     * @author Juan David Hernandez
     * @param responseValidarDireccionDto
     */
    public void recargar(ResponseValidarDireccionDto responseValidarDireccionDto) {
        //JDHT
        direccionValidada = responseValidarDireccionDto.isValidacionExitosa();

        if (!direccionValidada) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "La dirección no fue traducida de manera exitosa. Intente "
                            + "ingresar la dirección de manera tabulada", ""));
            return;
        }
        
         drDireccion = responseValidarDireccionDto.getDrDireccion();
         direccionBusqueda = responseValidarDireccionDto.getDireccion();
        if (responseValidarDireccionDto.getDireccionRespuestaGeo() != null
                && !responseValidarDireccionDto.getDireccionRespuestaGeo().isEmpty()) {
            direccionBusqueda = responseValidarDireccionDto.getDireccionRespuestaGeo();
            direccionRespuestaGeoBean = responseValidarDireccionDto.getDireccionRespuestaGeo();
        }

        if (responseValidarDireccionDto.isMultiOrigen()
                && responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("CK")) {
            direccionMultiorigen = responseValidarDireccionDto.isMultiOrigen();
            if (responseValidarDireccionDto.getBarrios() != null
                    && !responseValidarDireccionDto.getBarrios().isEmpty()) {                
                barrioslist = responseValidarDireccionDto.getBarrios();                
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "La dirección es MULTIORIGEN, por favor ingrese un BARRIO", ""));
        } else {
            direccionMultiorigen = false;
        }
        
        if (responseValidarDireccionDto.getBarrios() != null
                && !responseValidarDireccionDto.getBarrios().isEmpty()) {
            barrioslist = responseValidarDireccionDto.getBarrios();
        }

        // Para la multiorigen, en caso tal que el GEO no obtenga el listado de 
        // barrios, se adiciona al listado el campo "barrioGeo" (si existe).
        if ((barrioslist == null || barrioslist.isEmpty())
                && responseValidarDireccionDto.isMultiOrigen()
                && responseValidarDireccionDto.getBarrioGeo() != null
                && !responseValidarDireccionDto.getBarrioGeo().isEmpty()) {
            barrioslist = new ArrayList();
            barrioslist.add(responseValidarDireccionDto.getBarrioGeo());
        }

        if (responseValidarDireccionDto.getDrDireccion() != null
                && responseValidarDireccionDto.getBarrioGeo() != null
                && !responseValidarDireccionDto.getBarrioGeo().isEmpty()) {
            selectedBarrio = responseValidarDireccionDto.getBarrioGeo().toUpperCase();
            drDireccion.setBarrio(responseValidarDireccionDto.getBarrioGeo().toUpperCase());
        } else {
            if (responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")
                    || responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")) {
                selectedBarrio = drDireccion.getBarrio();
            }
        }
    }
       
    public void limpiarDireccion() {
        direccionValidada = false;
        direccionBusqueda = "";
        drDireccion = new DrDireccion();
        habilitaObj = false;
        cuentaMatrizList = new ArrayList();
        cuentaMatrizListAux = new ArrayList();
        barrioList = new ArrayList();
        direccionMultiorigen = false;
        otroBarrio = false;
        selectedBarrio = "";
        direccionRespuestaGeoBean = "";
    }


    public String listInfoByPage(int page) {
        try {
            cuentaMatriz = null;
            FiltroConsultaCuentaMatriz filtroConsultaCuentaMatriz;
            int firstResult = 0;
            if (page > 1) {
                firstResult = (ConstantsCM.PAGINACION_OCHO_FILAS * (page - 1));
            }

            filtroConsultaCuentaMatriz = cuentaMatrizFacadeLocal.findSolicitudesCuentaMatriz(
                    params, ConstantsCM.PAGINACION_DATOS, firstResult, ConstantsCM.PAGINACION_OCHO_FILAS);
            
            cuentaMatrizList = filtroConsultaCuentaMatriz.getListaCm();
            
            habilitaObj = !cuentaMatrizList.isEmpty();
            if (cuentaMatrizList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            // validar la direccion principal de la cuenta matriz
            // cuando se le 
            if (nuevaPageActual == 0) {
                if (cuentaMatrizList.size() == 1) {
                    cuentaMatriz = cuentaMatrizList.get(0);
                    goCuentaMatriz();
                }
            }
     
            actual = page;
        } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.."  + e.getMessage(), e, LOGGER);
            return "";

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void pageFirst() {
        try {
            nuevaPageActual=1;
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
             nuevaPageActual = actual - 1;
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
             nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
             nuevaPageActual = actual + 1;
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
            nuevaPageActual=totalPaginas;
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        if (pintarPaginado) {
            //condición cuando es una búsqueda de dirección de cm por texto y no por filtros
            if (direccionBusqueda != null && !direccionBusqueda.isEmpty()
                    && drDireccion != null && drDireccion.getIdTipoDireccion() != null
                    && !drDireccion.getIdTipoDireccion().isEmpty()
                    && centroProblado != null && !centroProblado.equals(BigDecimal.ZERO)
                    && direccionValidada) {
                
                if(cuentaMatrizList != null && !cuentaMatrizList.isEmpty()){
                    
                     int count = cuentaMatrizList.size();
                    int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                            ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                            : count / ConstantsCM.PAGINACION_OCHO_FILAS);
                    return totalPaginas;
                }
                
            }else{
       
                try {
                    buscar();
                    FiltroConsultaCuentaMatriz filtroConsultaCuentaMatriz;
                    filtroConsultaCuentaMatriz = cuentaMatrizFacadeLocal.findSolicitudesCuentaMatriz(params, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                    Long count = filtroConsultaCuentaMatriz.getNumRegistros();
                    int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_OCHO_FILAS != 0)
                            ? (count / ConstantsCM.PAGINACION_OCHO_FILAS) + 1
                            : count / ConstantsCM.PAGINACION_OCHO_FILAS);
                    return totalPaginas;
                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
                }
            }
        }
        return 1;

    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<Integer>();
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
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void ocultarComponente() {
        componenteEsVisible = false;
        drDireccion = new DrDireccion();
    }

    public boolean validarEdicion() {
        return validarPermisos(ValidacionUtil.OPC_EDICION);
    }

    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, accion, opcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public void tecnologiasCoberturaCuentaMatriz() {


        try {
            if (cuentaMatriz != null) {
                if (cuentaMatriz.getDireccionPrincipal() != null) {
                    DireccionMgl direccionMgl = cuentaMatriz.getDireccionPrincipal().getDireccionObj();
                    coberturasListTecnologias.clear();

                    if (direccionMgl.getDirNodouno() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_UNO.getCodigo());
                    }
                    if (direccionMgl.getDirNododos() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_DOS.getCodigo());
                    }
                    if (direccionMgl.getDirNodotres() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_TRES.getCodigo());
                    }
                    if (direccionMgl.getDirNodoDth() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_DTH.getCodigo());
                    }
                    if (direccionMgl.getDirNodoFtth() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_FTTH.getCodigo());
                    }
                    if (direccionMgl.getDirNodoMovil() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_MOVIL.getCodigo());
                    }
                    if (direccionMgl.getDirNodoWifi() != null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_WIFI.getCodigo());
                    }
                    if (direccionMgl.getGeoZonaUnifilar()!= null) {
                        coberturasListTecnologias.add(CmtCoverEnum.NODO_FOU.getCodigo());
                    }

                }
            }
           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(ConsultaCuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    
    
    public String validarCobertura(String codigo) {

        isAzul = false;
        isVerde = false;
        isRojo = true;

        //Verificamos si tiene cobertura
        for (String codigoTec : coberturasListTecnologias) {

            if (codigo.equalsIgnoreCase(codigoTec)) {
                isAzul = true;
            }
        }
        //Verificamos si se gestiono una tecnologia
        for (String codigoTec : tecnologiasVarias) {

            if (codigo.equalsIgnoreCase(codigoTec)) {
                isAzul = true;
            }
        }
        
        //Verificamos si esta en cable 
        for (String codigoTec : tecnologiasCables) {

            if (codigo.equalsIgnoreCase(codigoTec)) {
                isVerde = true;
            }
        }

        if (isAzul && isVerde) {
            isVerde = true;
            isAzul = false;
            isRojo = false;
        } else if (isAzul && isRojo) {
            isAzul = true;
            isVerde = false;
            isRojo = false;
        } else if (isVerde && isRojo) {
            isVerde = true;
            isAzul = false;
            isRojo = false;
        }

        return "";
    }

    
         /**
     * Funcion que recibe el codigo de una tecnologia y devuelve el nombre.Es utilizado para el tooltip de la pantalla de cuenta matriz.
     * 
     * @author Juan David Hernandez
     * @param codigo
     * @return 
     */
    public String obtenerTooltip(String codigo) {

        if(tecnologiasBasicaList != null && !tecnologiasBasicaList.isEmpty() 
                && codigo != null && !codigo.isEmpty()){
            for (CmtBasicaMgl tecnologia : tecnologiasBasicaList) {
                if(tecnologia.getCodigoBasica().equalsIgnoreCase(codigo)){
                    return tecnologia.getDescripcion();
                }                
            }            
        }
        return "";
    }
    
    
    public boolean validarBusCM() {
        return validarEdicion(VALIDARBUSCM);
    }

   private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }  

    
    public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        selectedBarrio = "";
        return null;
    }

    
    //<editor-fold defaultstate="collapsed" desc="Getters && Setters">
    
    public DrDireccion getDrDireccion() {
        return drDireccion;
    }
    
    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }    
    
    public ArrayList<String> getListBarrios() {
        return listBarrios;
    }
    
    public void setListBarrios(ArrayList<String> listBarrios) {
        this.listBarrios = listBarrios;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
//espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl
    public String getNumeroCuentaMGL() {
        return numeroCuentaMGL;
    }
//espinosadiea: modificacion 09 de junio 2018 agregar campo numero de cuenta de mgl
    public void setNumeroCuentaMGL(String numeroCuentaMGL) {
        this.numeroCuentaMGL = numeroCuentaMGL;
    }
    
    public String getNumeroOt() {
        return numeroOt;
    }
    
    public void setNumeroOt(String numeroOt) {
        this.numeroOt = numeroOt;
    }
    
    public String getSerialEquipo() {
        return serialEquipo;
    }
    
    public void setSerialEquipo(String serialEquipo) {
        this.serialEquipo = serialEquipo;
    }
    
    public String getCuentaSucriptor() {
        return cuentaSucriptor;
    }
    
    public void setCuentaSucriptor(String cuentaSucriptor) {
        this.cuentaSucriptor = cuentaSucriptor;
    }
    
    public String getNombreCuenta() {
        return nombreCuenta;
    }
    
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }
    
    public String getRrRegional() {
        return rrRegional;
    }
    
    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }
    
    public String getRrCiudad() {
        return rrCiudad;
    }
    
    public void setRrCiudad(String rrCiudad) {
        this.rrCiudad = rrCiudad;
    }
    
    public BigDecimal getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(BigDecimal departamento) {
        this.departamento = departamento;
    }
    
    public BigDecimal getMunicipio() {
        return municipio;
    }
    
    public void setMunicipio(BigDecimal municipio) {
        this.municipio = municipio;
    }
    
    public BigDecimal getCentroProblado() {
        return centroProblado;
    }
    
    public void setCentroProblado(BigDecimal centroProblado) {
        this.centroProblado = centroProblado;
    }
    
    public String getBarrio() {
        return barrio;
    }
    
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    
    
    public List<RrCiudades> getCiudadesList() {
        return ciudadesList;
    }
    
    public void setCiudadesList(List<RrCiudades> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }
    
    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }
    
    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }
    
    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }
    
    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }
    
    public BigDecimal getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }
    
    public List<String> getBarrioList() {
        return barrioList;
    }
    
    public void setBarrioList(List<String> barrioList) {
        this.barrioList = barrioList;
    }
    
    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }
    
    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }
    
    public boolean isHabilitaObj() {
        return habilitaObj;
    }
    
    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }
    
    public String getMultiorigen() {
        return multiorigen;
    }
    
    public void setMultiorigen(String multiorigen) {
        this.multiorigen = multiorigen;
    }
    
    public List<CmtCuentaMatrizMgl> getCuentaMatrizList() {
        return cuentaMatrizList;
    }
    
    public void setCuentaMatrizList(List<CmtCuentaMatrizMgl> cuentaMatrizList) {
        this.cuentaMatrizList = cuentaMatrizList;
    }
    
    /**
     * @return the cuentaMatriz
     */
    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }
    
    /**
     * @param cuentaMatriz the cuentaMatriz to set
     */
    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }
    
    public String getDireccionBusqueda() {
        return direccionBusqueda;
    }
    
    public void setDireccionBusqueda(String direccionBusqueda) {
        this.direccionBusqueda = direccionBusqueda;
    }
    
    public boolean isComponenteEsVisible() {
        return componenteEsVisible;
    }
    
    public void setComponenteEsVisible(boolean componenteEsVisible) {
        this.componenteEsVisible = componenteEsVisible;
    }
    
    public boolean isCiudadObligatoria() {
        return ciudadObligatoria;
    }
    
    public void setCiudadObligatoria(boolean ciudadObligatoria) {
        this.ciudadObligatoria = ciudadObligatoria;
    }
    
    public int getActual() {
        return actual;
    }
    
    public void setActual(int actual) {
        this.actual = actual;
    }
    
    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
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
    
    public int getFilasPag() {
        return ConstantsCM.PAGINACION_OCHO_FILAS;
    }
    
    public boolean getPintarPaginado() {
        return pintarPaginado;
    }
    
    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }
    
    public String getAdministracion() {
        return administracion;
    }
    
    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }
    
    public String getAdministrador() {
        return administrador;
    }
    
    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    //</editor-fold>

    public List<CmtRegionalRr> getLstCmtRegionalRrs() {
        return lstCmtRegionalRrs;
    }

    public void setLstCmtRegionalRrs(List<CmtRegionalRr> lstCmtRegionalRrs) {
        this.lstCmtRegionalRrs = lstCmtRegionalRrs;
    }

    public List<String> getTecnologiasCables() {
        return tecnologiasCables;
    }

    public void setTecnologiasCables(List<String> tecnologiasCables) {
        this.tecnologiasCables = tecnologiasCables;
    }

    public List<String> getCoberturasListTecnologias() {
        return coberturasListTecnologias;
    }

    public void setCoberturasListTecnologias(List<String> coberturasListTecnologias) {
        this.coberturasListTecnologias = coberturasListTecnologias;
    }

    public List<String> getTecnologiasList() {
        return tecnologiasList;
    }

    public void setTecnologiasList(List<String> tecnologiasList) {
        this.tecnologiasList = tecnologiasList;
    }

   

    public List<CmtCuentaMatrizMgl> getCuentaMatrizListAux() {
        return cuentaMatrizListAux;
    }

    public void setCuentaMatrizListAux(List<CmtCuentaMatrizMgl> cuentaMatrizListAux) {
        this.cuentaMatrizListAux = cuentaMatrizListAux;
    }

    public boolean isIsRojo() {
        return isRojo;
    }

    public void setIsRojo(boolean isRojo) {
        this.isRojo = isRojo;
    }

    public boolean isIsAzul() {
        return isAzul;
    }

    public void setIsAzul(boolean isAzul) {
        this.isAzul = isAzul;
    }

    public boolean isIsVerde() {
        return isVerde;
    }

    public void setIsVerde(boolean isVerde) {
        this.isVerde = isVerde;
    }

    public boolean isDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(boolean direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public List<String> getBarrioslist() {
        return barrioslist;
    }

    public void setBarrioslist(List<String> barrioslist) {
        this.barrioslist = barrioslist;
    }

    public boolean isDireccionMultiorigen() {
        return direccionMultiorigen;
    }

    public void setDireccionMultiorigen(boolean direccionMultiorigen) {
        this.direccionMultiorigen = direccionMultiorigen;
    }

    public String getSelectedBarrio() {
        return selectedBarrio;
    }

    public void setSelectedBarrio(String selectedBarrio) {
        this.selectedBarrio = selectedBarrio;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public List<String> getTecnologiasVarias() {
        return tecnologiasVarias;
    }

    public void setTecnologiasVarias(List<String> tecnologiasVarias) {
        this.tecnologiasVarias = tecnologiasVarias;
    }  
    
}
