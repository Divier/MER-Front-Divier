
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.facade.TipoDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@ManagedBean(name = "crearPrefichaBean")
@ViewScoped
public class PrefichaCrearBean {

    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private String cedulaUsuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(PrefichaCrearBean.class);
    private static final String FASE_PREFICHA_MODIFICACION = "MODIFICACION";
    private static final String FASE_PREFICHA_GENERACION = "GENERACION";
    private static final String FASE_PREFICHA_VALIDACION = "VALIDACION";
    private static final String FASE_PREFICHA_GEORREF = "GEORREFERENCIADA";
    private static final String NO_GEORREFERENCIADO = "-1";
    private HtmlDataTable dataTable = new HtmlDataTable();
    private BigDecimal idPrefichaSelected;
    private List<PreFichaMgl> preFichaToCreateList = new ArrayList<>();
    private List<PreFichaMgl> preFichaFiltradaList  = new ArrayList();
    private List<PreFichaXlsMgl> edificiosVtXls;
    private List<PreFichaXlsMgl> casasRedExternaXls;
    private List<PreFichaXlsMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> hhppSNXls;
    private List<PreFichaXlsMgl> IngresoNuevoNXls;
    private List<PreFichaXlsMgl> preFichaXlsMglList;
    private List<PreFichaXlsMgl> preFichaProcessXlsList;
    private List<PreFichaGeorreferenciaMgl> georreferenciaEdificiosVtXls;
    private List<PreFichaGeorreferenciaMgl> georreferenciaCasasRedExternaXls;
    private List<PreFichaGeorreferenciaMgl> georreferenciaConjCasasVtXls;
    private List<PreFichaGeorreferenciaMgl> georreferenciaIngresoNuevoNXls;
    private PreFichaMgl prefichaCrear;
    private String saveAvailable = String.valueOf(false);
    private String labelHeaderTableLists = "Información de la Preficha";
    private boolean seGeorreferencio =true;
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
    /**
     *  El flag filtro aplicado se desactiva si se cambia la fecha inicial o la fecha final
     *  El flag filtro aplicado se activa al dar realizar correctamente el filtrado de la lista por el rango de fechas incluidad las fecha nullas
     *  si el filtro ha sido aplicado se pueden habilitar los botones de navegacion
     *  filtro aplicado se diferencia de registros filtrados en que incluye el rango con fechas nulas
     */
    private boolean filtroAplicado = true;
    private boolean rangoFechasValido = true;    
    
    //Opciones agregadas para Rol
    private final String BTNCARPREFIC = "BTNCARPREFIC";
    private final String BTNELPREFIC = "BTNELPREFIC";
    
    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;
    @EJB
    private AddressEJBRemote addressEJBRemote;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private TipoDireccionMglFacadeLocal tipoDireccionMglFacadeLocal;
    String isInfoProcesada = String.valueOf(false);
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    public PrefichaCrearBean() {
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
            cedulaUsuarioVT = securityLogin.getIdUser();

        } catch (IOException ex) {
            String msg = "Error al iniciar el formulario de solicitud de creaci&oacute;n CM:..." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
            LOGGER.error(msg, ex);
        } catch (Exception ex) {
            String msg = "Error al iniciar el formulario de solicitud de creaci&oacute;n CM:..." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
            LOGGER.error(msg, ex);
        }

    }

    public static enum CLASIFICACION_NAME {

        EDIFICIOSVT, CASASREDEXTERNA, CONJUNTOCASASVT, HHPPSN, INGRESONUEVO
    }

    public static enum DIST_TYPE {

        LC, AP, IN
    }

    @PostConstruct
    public void loadList() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage(), e);
        }
    }

    public void cargarDetallePreficha() {
        try {
            seGeorreferencio=true;
            isInfoProcesada = String.valueOf(false);
            prefichaCrear = (PreFichaMgl) dataTable.getRowData();
            preFichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(prefichaCrear.getPrfId(), prefichaCrear.getFase());
            edificiosVtXls = new ArrayList<PreFichaXlsMgl>();
            casasRedExternaXls = new ArrayList<PreFichaXlsMgl>();
            conjCasasVtXls = new ArrayList<PreFichaXlsMgl>();
            hhppSNXls = new ArrayList<PreFichaXlsMgl>();
            IngresoNuevoNXls = new ArrayList<PreFichaXlsMgl>();

            for (PreFichaXlsMgl p : preFichaXlsMglList) {
                p.setFase(FASE_PREFICHA_GEORREF);
                CLASIFICACION_NAME clasificacion = CLASIFICACION_NAME.valueOf(p.getClasificacion());
                switch (clasificacion) {
                    case EDIFICIOSVT:
                        edificiosVtXls.add(p);
                        break;
                    case CASASREDEXTERNA:
                        if (p.getPisos().intValue() > 3) {
                            edificiosVtXls.add(p);
                        } else {
                            casasRedExternaXls.add(p);
                        }
                        break;
                    case CONJUNTOCASASVT:
                        conjCasasVtXls.add(p);
                        break;
                    case HHPPSN:
                        hhppSNXls.add(p);
                        break;
                    case INGRESONUEVO:
                        IngresoNuevoNXls.add(p);
                        break;
                }

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallePreficha, al cargar la informacion detalle de la preficha seleccionada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallePreficha, al cargar la informacion detalle de la preficha seleccionada. ", e, LOGGER);
        }
    }


    public void guardarGeorreferenciacion() {
        try {
            LOGGER.error("preficha crear: " + prefichaCrear.getPrfId() + " " + prefichaCrear.getNodoMgl().getNodCodigo());
            if (georreferenciaEdificiosVtXls != null && georreferenciaEdificiosVtXls.size() > 0) {
                for (PreFichaGeorreferenciaMgl edificios : georreferenciaEdificiosVtXls) {
                    edificios.setCambioEstrato(edificios.getLevelEconomic());
                }
                preFichaMglFacadeLocal.savePreFichaGeoList(georreferenciaEdificiosVtXls);
            }
            if (georreferenciaCasasRedExternaXls != null && georreferenciaCasasRedExternaXls.size() > 0) {
                for (PreFichaGeorreferenciaMgl casas : georreferenciaCasasRedExternaXls) {
                    casas.setCambioEstrato(casas.getLevelEconomic());
                }
                preFichaMglFacadeLocal.savePreFichaGeoList(georreferenciaCasasRedExternaXls);
            }
            if (georreferenciaConjCasasVtXls != null && georreferenciaConjCasasVtXls.size() > 0) {
                for (PreFichaGeorreferenciaMgl conjunto : georreferenciaConjCasasVtXls) {
                    conjunto.setCambioEstrato(conjunto.getLevelEconomic());
                }
                preFichaMglFacadeLocal.savePreFichaGeoList(georreferenciaConjCasasVtXls);
            }
            if (georreferenciaIngresoNuevoNXls != null && georreferenciaIngresoNuevoNXls.size() > 0) {
                preFichaMglFacadeLocal.savePreFichaGeoList(georreferenciaIngresoNuevoNXls);
            }
            saveAvailable = String.valueOf(false);

            //actualizamos el estado de la preficha al estado GEORREFERENCIADA
            prefichaCrear.setGeorreferenciada("1");

            preFichaMglFacadeLocal.updatePreFicha(prefichaCrear);
        } catch (ApplicationException e) {
            LOGGER.error("Error en guardarGeorreferenciacion. " +e.getMessage(), e);      
        } catch (Exception e) {
            LOGGER.error("Error en guardarGeorreferenciacion. " +e.getMessage(), e);      
        }
    }

    public void georreferenciarPreficha() {
        try {

            isInfoProcesada = String.valueOf(false);
            BigDecimal idGeoPol = prefichaCrear.getNodoMgl().getGpoId();

            GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(idGeoPol);
            GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
            GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

            if (edificiosVtXls != null && edificiosVtXls.size() > 0) {
                georreferenciaEdificiosVtXls = new ArrayList<>();
                for (PreFichaXlsMgl p : edificiosVtXls) {
                    PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = georreferenciarPrefichaElement(p, state.getGpoNombre(), centro.getGpoNombre(), centro.getGeoCodigoDane());
                    preFichaGeorreferenciaMgl.setChangeNumber("1");
                    preFichaGeorreferenciaMgl.setFechaCreacion(p.getFechaCreacion());
                    preFichaGeorreferenciaMgl.setUsuarioCreacion(p.getUsuarioCreacion());
                    preFichaGeorreferenciaMgl.setPerfilCreacion(p.getPerfilCreacion());
                    preFichaGeorreferenciaMgl.setFechaEdicion(p.getFechaEdicion());
                    preFichaGeorreferenciaMgl.setUsuarioEdicion(p.getUsuarioEdicion());
                    preFichaGeorreferenciaMgl.setPerfilEdicion(p.getPerfilEdicion());
                    preFichaGeorreferenciaMgl.setEstadoRegistro(p.getEstadoRegistro());

                    georreferenciaEdificiosVtXls.add(preFichaGeorreferenciaMgl);

                }
            }
            if (casasRedExternaXls != null && casasRedExternaXls.size() > 0) {
                georreferenciaCasasRedExternaXls = new ArrayList<>();
                for (PreFichaXlsMgl p : casasRedExternaXls) {
                    PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = georreferenciarPrefichaElement(p, state.getGpoNombre(), centro.getGpoNombre(), centro.getGeoCodigoDane());
                    preFichaGeorreferenciaMgl.setChangeNumber("1");
                    preFichaGeorreferenciaMgl.setFechaCreacion(p.getFechaCreacion());
                    preFichaGeorreferenciaMgl.setUsuarioCreacion(p.getUsuarioCreacion());
                    preFichaGeorreferenciaMgl.setPerfilCreacion(p.getPerfilCreacion());
                    preFichaGeorreferenciaMgl.setFechaEdicion(p.getFechaEdicion());
                    preFichaGeorreferenciaMgl.setUsuarioEdicion(p.getUsuarioEdicion());
                    preFichaGeorreferenciaMgl.setPerfilEdicion(p.getPerfilEdicion());
                    preFichaGeorreferenciaMgl.setEstadoRegistro(p.getEstadoRegistro());

                    georreferenciaCasasRedExternaXls.add(preFichaGeorreferenciaMgl);

                }
            }
            if (conjCasasVtXls != null && conjCasasVtXls.size() > 0) {
                georreferenciaConjCasasVtXls = new ArrayList<PreFichaGeorreferenciaMgl>();
                for (PreFichaXlsMgl p : conjCasasVtXls) {

                    PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = georreferenciarPrefichaElement(p, state.getGpoNombre(), centro.getGpoNombre(), centro.getGeoCodigoDane());
                    preFichaGeorreferenciaMgl.setChangeNumber("1");
                    preFichaGeorreferenciaMgl.setFechaCreacion(p.getFechaCreacion());
                    preFichaGeorreferenciaMgl.setUsuarioCreacion(p.getUsuarioCreacion());
                    preFichaGeorreferenciaMgl.setPerfilCreacion(p.getPerfilCreacion());
                    preFichaGeorreferenciaMgl.setFechaEdicion(p.getFechaEdicion());
                    preFichaGeorreferenciaMgl.setUsuarioEdicion(p.getUsuarioEdicion());
                    preFichaGeorreferenciaMgl.setPerfilEdicion(p.getPerfilEdicion());
                    preFichaGeorreferenciaMgl.setEstadoRegistro(p.getEstadoRegistro());

                    georreferenciaConjCasasVtXls.add(preFichaGeorreferenciaMgl);

                }
            }
            if (IngresoNuevoNXls != null && IngresoNuevoNXls.size() > 0) {
                georreferenciaIngresoNuevoNXls = new ArrayList<PreFichaGeorreferenciaMgl>();
                for (PreFichaXlsMgl p : IngresoNuevoNXls) {

                    PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = georreferenciarPrefichaElement(p, state.getGpoNombre(), centro.getGpoNombre(), centro.getGeoCodigoDane() );
                    preFichaGeorreferenciaMgl.setChangeNumber("1");
                    preFichaGeorreferenciaMgl.setFechaCreacion(p.getFechaCreacion());
                    preFichaGeorreferenciaMgl.setUsuarioCreacion(p.getUsuarioCreacion());
                    preFichaGeorreferenciaMgl.setPerfilCreacion(p.getPerfilCreacion());
                    preFichaGeorreferenciaMgl.setFechaEdicion(p.getFechaEdicion());
                    preFichaGeorreferenciaMgl.setUsuarioEdicion(p.getUsuarioEdicion());
                    preFichaGeorreferenciaMgl.setPerfilEdicion(p.getPerfilEdicion());
                    preFichaGeorreferenciaMgl.setEstadoRegistro(p.getEstadoRegistro());

                    georreferenciaIngresoNuevoNXls.add(preFichaGeorreferenciaMgl);

                }
            }
            isInfoProcesada = String.valueOf(true);
            saveAvailable = String.valueOf(true);
            seGeorreferencio =false;
        } catch (ApplicationException e) {
            LOGGER.error("Error en georreferenciarPreficha. " +e.getMessage(), e);      
        } catch (Exception e) {
            LOGGER.error("Error en georreferenciarPreficha. " +e.getMessage(), e);      
        }

    }

    private String getDirToGeorreferenciar(PreFichaXlsMgl pft) {
        String calle = pft.getViaPrincipal();
        String placa = "";
        //si tenemos multiplaca tomamos la primera placa
        if (isMultiplePlaca(pft.getPlaca())) {
            placa = pft.getPlaca().substring(0, pft.getPlaca().indexOf("-") + 1) + pft.getPlaca().substring(pft.getPlaca().indexOf("-") + 1).trim().split("/")[0];
        } else {
            placa = pft.getPlaca();
        }
        return calle + " # " + placa;
    }

    private PreFichaGeorreferenciaMgl georreferenciarPrefichaElement(PreFichaXlsMgl p, String state, String city, String codigoDane) throws ApplicationException {


        AddressRequest request = new AddressRequest();
        String barrio = p.getBarrio() == null ? "" : p.getBarrio();
        request.setAddress(getDirToGeorreferenciar(p));
        request.setState(state);
        request.setCity(city);
        request.setNeighborhood(barrio);
        request.setLevel("C");
        request.setCodDaneVt(codigoDane);
        PreFichaGeorreferenciaMgl preFichaGeorreferenciaMgl = new PreFichaGeorreferenciaMgl();
        AddressService responseGeo = null;
        if (p.getIdTipoDireccion() != null && !p.getIdTipoDireccion().isEmpty() && !p.getIdTipoDireccion().equals("IT")) {
            responseGeo = addressEJBRemote.queryAddress(request);
            preFichaGeorreferenciaMgl = copyResposeGeoToPreFicha(responseGeo);

        }

        preFichaGeorreferenciaMgl.setPreFichaXlsMgl(p);


        if (responseGeo != null) {
            LOGGER.error("responseGeo: " + responseGeo.getAddress() + " " + getDirToGeorreferenciar(p));
        }

        return preFichaGeorreferenciaMgl;

    }

    private PreFichaGeorreferenciaMgl copyResposeGeoToPreFicha(AddressService responseGeo) {
        PreFichaGeorreferenciaMgl result = new PreFichaGeorreferenciaMgl();

        result.setActivityEconomic(responseGeo.getActivityeconomic());
        result.setAddress(responseGeo.getAddress());
        result.setAddressCode(responseGeo.getAddressCode());
        result.setAddressCodeFound(responseGeo.getAddressCodeFound());
        result.setAlternateAddress(responseGeo.getAlternateaddress());
        result.setAppletStandar(responseGeo.getAppletstandar());
        result.setCategory(responseGeo.getCategory());
        result.setChangeNumber(responseGeo.getChagenumber());
        result.setCodDaneMcpio(responseGeo.getCoddanemcpio());

        result.setDaneCity(responseGeo.getDaneCity());
        result.setDanePopArea(responseGeo.getDanePopulatedArea());
        result.setExist(responseGeo.getExist());
        result.setIdAddress(responseGeo.getIdaddress());

        result.setLevelLive(responseGeo.getLevellive());
        result.setLocality(responseGeo.getLocality());

        if (responseGeo.getAddressSuggested() != null && !responseGeo.getAddressSuggested().isEmpty()) {
            for (AddressSuggested obj : responseGeo.getAddressSuggested()) {
                if (obj.getNeighborhood().trim().equals(responseGeo.getNeighborhood())) {
                    result.setNeighborhood(responseGeo.getNeighborhood());
                    break;
                }
            }
            if (result.getNeighborhood() == null || result.getNeighborhood().isEmpty()) {
                result.setNeighborhood(responseGeo.getAddressSuggested().get(0).getNeighborhood());
            }

        } else {
            result.setNeighborhood(responseGeo.getNeighborhood());
        }
        result.setNodoUno(responseGeo.getNodoUno());
        result.setNodoDos(responseGeo.getNodoDos());
        result.setNodoTres(responseGeo.getNodoTres());
        result.setQualifiers(responseGeo.getQualifiers());
        result.setSource(responseGeo.getSource());
        result.setState(responseGeo.getState());
        result.setTranslate(responseGeo.getTraslate());
        result.setZipCode(responseGeo.getZipCode());
        result.setZipCodeDistrict(responseGeo.getZipCodeDistrict());
        result.setZipCodeState(responseGeo.getZipCodeState());

        result.setLevelEconomic(responseGeo.getLeveleconomic());
        result.setCx(responseGeo.getCx());
        result.setCy(responseGeo.getCy());
        //se valida si el geo entrego informacion de estrato y coordenadas 
        //si no se marca el resultado como NO GEORREFERENCIADO
        if (responseGeo.getLeveleconomic() == null || responseGeo.getLeveleconomic().trim().isEmpty()) {
            result.setLevelEconomic(NO_GEORREFERENCIADO);
        }

        return result;

    }

    public void procesarPreficha() {
        preFichaProcessXlsList = new ArrayList<PreFichaXlsMgl>();
        for (PreFichaXlsMgl p : preFichaXlsMglList) {
            if (!p.getClasificacion().equalsIgnoreCase("HHPPSN")) {
                List<String> direccionList = new ArrayList<String>();
                String calle = p.getViaPrincipal();
                String placa = "";
                //si tenemos multiplaca tomamos la primera placa
                if (isMultiplePlaca(p.getPlaca())) {
                    placa = p.getPlaca().substring(0, p.getPlaca().indexOf("-") + 1) + p.getPlaca().substring(p.getPlaca().indexOf("-") + 1).trim().split("/")[0];
                } else {
                    placa = p.getPlaca();
                }

                if (p.getDistribucion() != null && !p.getDistribucion().trim().isEmpty()) {
                    List<String> gruposStr = getGruposFromDist(p.getDistribucion());
                    if (gruposStr != null && gruposStr.size() > 0) {
                        for (String str : gruposStr) {
                            String tipo = str.split("\\s")[0];
                            String[] valores = str.split("\\s")[1].split("-");
                            DIST_TYPE type = DIST_TYPE.valueOf(tipo);
                            switch (type) {
                                case LC:
                                    for (int i = 0; i < valores.length; i++) {
                                        direccionList.add(calle + " # " + placa + "| LOCAL " + valores[i]);
                                    }
                                    break;
                                case AP:
                                    for (int i = 0; i < valores.length; i++) {
                                        direccionList.add(calle + " # " + placa + "| APARTAMENTO " + valores[i]);
                                    }
                                    break;
                                case IN:
                                    for (int i = 0; i < valores.length; i++) {
                                        direccionList.add(calle + " # " + placa + "| INTERIOR " + valores[i]);
                                    }
                                    break;
                            }
                        }
                    }


                } else {//si el registro no posee distribucion

                    if (p.getAptos() != null && p.getAptos().compareTo(BigDecimal.ZERO) != 0) {
                        for (int i = 1; i <= p.getAptos().intValue(); i++) {
                            direccionList.add(calle + " # " + placa + "| PISO1 APARTAMENTO " + i);
                        }
                    }

                    if (p.getOficinas() != null && p.getOficinas().compareTo(BigDecimal.ZERO) != 0) {
                        for (int i = 1; i <= p.getOficinas().intValue(); i++) {
                            direccionList.add(calle + " # " + placa + "| PISO1 OFICINA " + i);
                        }
                    }

                    if (p.getLocales() != null && p.getLocales().compareTo(BigDecimal.ZERO) != 0) {
                        for (int i = 1; i <= p.getLocales().intValue(); i++) {
                            direccionList.add(calle + " # " + placa + "| PISO1 LOCAL " + i);
                        }
                    }
                }

                if (direccionList.size() > 0) {
                    p.setDireccionStrList(direccionList);
                }
                if (p.getDireccionStrList() != null && p.getDireccionStrList().size() > 0) {
                    LOGGER.error("Direcciones Preficha : tipo-" + p.getClasificacion() + "; " + calle + " # " + placa);
                    for (String s : p.getDireccionStrList()) {
                        LOGGER.error(s);
                    }
                }
                preFichaProcessXlsList.add(p);
            }
           
        }
        isInfoProcesada = String.valueOf(true);
    }

    private boolean isMultiplePlaca(String placa) {
        String regExpression = "\\d{1,3}[a-zA-Z0-9]{0,2}\\s*-\\s*\\d{1,3}(/\\d{1,3})+";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(placa).matches();
    }

    private List<String> getGruposFromDist(String distribucion) {
        List<String> gruposStr = null;

        String regExpGrupoApLcOf = "((AP|LC|IN)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        Pattern patter = Pattern.compile(regExpGrupoApLcOf);

        if (patter.matcher(distribucion).find()) {
            gruposStr = new ArrayList<String>();
            Matcher matcher = patter.matcher(distribucion);
            while (matcher.find()) {
                gruposStr.add(matcher.group());
            }
        }
        return gruposStr;
    }
    
    
     private String listInfoByPage(int page) {
        try {
          
            //se cargan las Preficha que hayan sido validadas por el area comercial
            List<String> faseList = new ArrayList<String>();
            faseList.add(FASE_PREFICHA_GENERACION);
            faseList.add(FASE_PREFICHA_VALIDACION);
            faseList.add(FASE_PREFICHA_MODIFICACION);
            preFichaToCreateList = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList, page, ConstantsCM.PAGINACION_DIEZ_FILAS, true, fechaInicial,fechaFinal);
            if (todasLasPreFichasMarcadas) {
                marcarListaFichas();
            }
            actual = page;
            
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error cargando lista en PrefichaCrearBean: listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaCrearBean: pageFirst() " + ex.getMessage(), ex);
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
              FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: pagePrevious() " + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaCrearBean: pagePrevious() " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en PrefichaCrearBean class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: irPagina() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: pageNext() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaCrearBean: pageNext() " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: pageLast() " + ex.getMessage() + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Ocurrió un error en la paginacion en PrefichaCrearBean: pageLast() "+ ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            List<String> faseList = new ArrayList<>();
            faseList.add(FASE_PREFICHA_GENERACION);
            faseList.add(FASE_PREFICHA_VALIDACION);
            faseList.add(FASE_PREFICHA_MODIFICACION);
            List<PreFichaMgl> preFichaValidarListContar = preFichaMglFacadeLocal.getListPrefichaByFaseAndDate(faseList,0,0, false,fechaInicial,fechaFinal);
            int pageSol = preFichaValidarListContar.size();
            
            int totalPaginas = (pageSol % ConstantsCM.PAGINACION_DIEZ_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_DIEZ_FILAS;
            return totalPaginas;
        } catch (ApplicationException e) {
              FacesUtil.mostrarMensajeError("Ocurrió un error en Agendamiento class: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Ocurrió un error en la paginacion en PrefichaCrearBean: getPageActual() " + e.getMessage(), e, LOGGER);
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

                if (preFichaToCreateList == null || preFichaToCreateList.isEmpty()) {
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
            if (preFichaToCreateList != null && !preFichaToCreateList.isEmpty()) {
                if (todasLasPreFichasMarcadas) {
                for (PreFichaMgl ficha : preFichaFiltradaList) {

                    ficha.setEstadoRegistro(0);
                    preFichaMglFacadeLocal.updatePreFicha(ficha);

                }
                seleccionarTodo = false;
                                
            } else {
                for (PreFichaMgl ficha : getPreFichaToCreateList()) {
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
        for (PreFichaMgl ficha : preFichaToCreateList) {
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
                faseList.add(FASE_PREFICHA_GENERACION);
                faseList.add(FASE_PREFICHA_VALIDACION);
                faseList.add(FASE_PREFICHA_MODIFICACION);
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
        getPreFichaToCreateList().forEach((ficha) -> {
            ficha.setSelected(seleccionarTodo);
        });
    }    
    
    // Validar Opciones por Rol    
    public boolean validarOpcionCargarPreficha() {
        return validarEdicionRol(BTNCARPREFIC);
    }
    
    public boolean validarOpcionEliminarPreficha() {
        return validarEdicionRol(BTNELPREFIC);
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

    public List<PreFichaXlsMgl> getIngresoNuevoNXls() {
        return IngresoNuevoNXls;
    }

    public void setIngresoNuevoNXls(List<PreFichaXlsMgl> IngresoNuevoNXls) {
        this.IngresoNuevoNXls = IngresoNuevoNXls;
    }

    public List<PreFichaXlsMgl> getPreFichaXlsMglList() {
        return preFichaXlsMglList;
    }

    public void setPreFichaXlsMglList(List<PreFichaXlsMgl> preFichaXlsMglList) {
        this.preFichaXlsMglList = preFichaXlsMglList;
    }

    public PreFichaMgl getPrefichaCrear() {
        return prefichaCrear;
    }

    public void setPrefichaCrear(PreFichaMgl prefichaCrear) {
        this.prefichaCrear = prefichaCrear;
    }

    public String getSaveAvailable() {
        return saveAvailable;
    }

    public void setSaveAvailable(String saveAvailable) {
        this.saveAvailable = saveAvailable;
    }

    public String getLabelHeaderTableLists() {
        return labelHeaderTableLists;
    }

    public void setLabelHeaderTableLists(String labelHeaderTableLists) {
        this.labelHeaderTableLists = labelHeaderTableLists;
    }

    public List<PreFichaMgl> getPreFichaToCreateList() {
        return preFichaToCreateList;
    }

    public void setPreFichaToCreateList(List<PreFichaMgl> preFichaToCreateList) {
        this.preFichaToCreateList = preFichaToCreateList;
    }

    public List<PreFichaXlsMgl> getPreFichaProcessXlsList() {
        return preFichaProcessXlsList;
    }

    public void setPreFichaProcessXlsList(List<PreFichaXlsMgl> preFichaProcessXlsList) {
        this.preFichaProcessXlsList = preFichaProcessXlsList;
    }

    public String getIsInfoProcesada() {
        return isInfoProcesada;
    }

    public void setIsInfoProcesada(String isInfoProcesada) {
        this.isInfoProcesada = isInfoProcesada;
    }

    public List<PreFichaGeorreferenciaMgl> getGeorreferenciaEdificiosVtXls() {
        return georreferenciaEdificiosVtXls;
    }

    public void setGeorreferenciaEdificiosVtXls(List<PreFichaGeorreferenciaMgl> georreferenciaEdificiosVtXls) {
        this.georreferenciaEdificiosVtXls = georreferenciaEdificiosVtXls;
    }

    public List<PreFichaGeorreferenciaMgl> getGeorreferenciaCasasRedExternaXls() {
        return georreferenciaCasasRedExternaXls;
    }

    public void setGeorreferenciaCasasRedExternaXls(List<PreFichaGeorreferenciaMgl> georreferenciaCasasRedExternaXls) {
        this.georreferenciaCasasRedExternaXls = georreferenciaCasasRedExternaXls;
    }

    public List<PreFichaGeorreferenciaMgl> getGeorreferenciaConjCasasVtXls() {
        return georreferenciaConjCasasVtXls;
    }

    public void setGeorreferenciaConjCasasVtXls(List<PreFichaGeorreferenciaMgl> georreferenciaConjCasasVtXls) {
        this.georreferenciaConjCasasVtXls = georreferenciaConjCasasVtXls;
    }

    public List<PreFichaGeorreferenciaMgl> getGeorreferenciaIngresoNuevoNXls() {
        return georreferenciaIngresoNuevoNXls;
    }

    public void setGeorreferenciaIngresoNuevoNXls(List<PreFichaGeorreferenciaMgl> georreferenciaIngresoNuevoNXls) {
        this.georreferenciaIngresoNuevoNXls = georreferenciaIngresoNuevoNXls;
    }

    public boolean isSeGeorreferencio() {
        return seGeorreferencio;
    }

    public void setSeGeorreferencio(boolean seGeorreferencio) {
        this.seGeorreferencio = seGeorreferencio;
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


}
