/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionRRFacadeLocal;
import co.com.claro.mgl.facade.DireccionesValidacionFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "consultaDireccionBean")
public class ConsultaDireccionBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaDireccionBean.class);
    private ResponseConstruccionDireccion responseConstruirDireccion
            = new ResponseConstruccionDireccion();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response
            = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private RequestConstruccionDireccion request
            = new RequestConstruccionDireccion();
    private List<AddressSuggested> barrioSugeridoList = new ArrayList<AddressSuggested>();
    private String usuarioVT = null;
    private String cedulaUsuarioVT = null;
    private BigDecimal idCentroPoblado;
    private GeograficoPoliticoMgl ciudadGpo;
    private GeograficoPoliticoMgl departamentoGpo;
    private GeograficoPoliticoMgl centroPobladoSeleccionado;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private String espacio = "&nbsp;";
    private String barrioSugeridoSeleccionado;
    private String departamento;
    private BigDecimal ciudad;
    private String comunidad;
    private String division;
    private String tipoDireccion;
    private String direccionCk;
    private String barrioCk;
    private String barrioCkTxt;
    private String nivelValorBm;
    private String nivelValorIt;
    private String tipoNivelBm;
    private String tipoNivelIt;
    private String tipoNivelApartamento;
    private String tipoNivelComplemento;
    private String tipoNivelNuevoApartamento;
    private String valorNivelNuevoApartamento;
    private String apartamento;
    private String complemento;
    private String direccionLabel;
    private String rrRegional;
    private String barrioSugeridoStr;
    private ConfigurationAddressComponent configurationAddressComponent;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<RrRegionales> regionalList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<RrCiudades> comunidadList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<OpcionIdNombre> ckList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> bmList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> itList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> aptoList = new ArrayList<OpcionIdNombre>();
    private List<OpcionIdNombre> complementoList = new ArrayList<OpcionIdNombre>();
    private DrDireccion drDireccion = new DrDireccion();
    private boolean showCKPanel;
    private boolean showBMPanel;
    private boolean showITPanel;
    private SecurityLogin securityLogin;
    private static final int DIRECCIONCK = 1;
    private static final int DIRECCIONBM = 2;
    private static final int DIRECCIONIT = 3;
    private static final int DIRECCIONAPTO = 4;
    private static final int DIRECCIONCOMPLEMENTO = 5;

    
    @EJB
    private DireccionRRFacadeLocal direccionRRFacadeLocal;
    @EJB
    private DireccionesValidacionFacadeLocal direccionesValidacionFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal componenteDireccionesMglFacade;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;

    public ConsultaDireccionBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            cedulaUsuarioVT = securityLogin.getIdUser();

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ConsultaDireccionBean, validando auttenticación. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ConsultaDireccionBean, validando auttenticación. ", e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        HttpSession session; 
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("CONSULTAR_DIRECCION_BEAN", this);
        try {
            obtenerDepartamentoList();
            obtenerListaRegionales();
            showCK();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
        } catch (ApplicationException e) {
            LOGGER.error("Error en init,al realizar cargue de configuración de listados. " +e.getMessage(), e);  
        } catch (Exception e) {
            LOGGER.error("Error en init,al realizar cargue de configuración de listados. " +e.getMessage(), e);  
        }
    }

    /**
     * Listar los barrios sugeridos multiorigen
     *
     * @author becerraarmr
     * @return listado de barrios
     * @throws ApplicationException si hay algún error en la consulta.
     */
    public SelectItem[] getBarriosMultiOrigen() throws ApplicationException {
        return ProcesoMasivoUtil.getSelectItems(barrioSugeridoList, true);
    }
    
    /**
     * Función que obtiene el valor completo de el centro poblado seleccionado
     * por el usuario en pantalla.
     *
     * @author Juan David Hernandez
     * @param idCentroPoblado
     */
    public void obtenerCentroPobladoSeleccionado(BigDecimal idCentroPoblado) throws ApplicationException {
        try {
            if (idCentroPoblado != null) {
                centroPobladoSeleccionado = geograficoPoliticoMglFacadeLocal.findById(idCentroPoblado);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCentroPobladoSeleccionado. ", e, LOGGER);
            throw new ApplicationException("Error en obtenerCentroPobladoSeleccionado. ",e);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCentroPobladoSeleccionado. ", e, LOGGER);
            throw new ApplicationException("Error en obtenerCentroPobladoSeleccionado. ",e);
        }
    }

 /**
     * Función que se utiliza para validar el barrio sugerido seleccionado
     *
     * @author Juan David Hernandez
     */
    public void validarBarrioSugeridoSeleccionado() {
        try {
            /*Si selecciona otro renderiza campo de texto en la pantalla para 
             * ingresar manualmente el barrio*/
            if (barrioSugeridoSeleccionado == null || barrioSugeridoSeleccionado.equalsIgnoreCase("Otro") 
                    || barrioCk.equalsIgnoreCase("---") ) {
                barrioCk = "";
                barrioCkTxt = "";
                request.setBarrio(null);
                direccionLabel = "Debe escribir un Barrio Sugerido ya que la ciudad es Multi-origen";
                return;
            } else {
                /*Si selecciona un barrio del listado, este se asigna de inmediato
                 * a la dirección*/
                if (!barrioSugeridoSeleccionado.equalsIgnoreCase("Otro")
                        && !barrioSugeridoSeleccionado.equalsIgnoreCase("---")
                        && !barrioSugeridoSeleccionado.isEmpty()
                        && !barrioSugeridoSeleccionado.equals("")) {
                    barrioCk = barrioSugeridoSeleccionado;
                    request.setBarrio(barrioSugeridoSeleccionado);
                    return;
                }
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en validarBarrioSugeridoSeleccionado. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarBarrioSugeridoSeleccionado. ", e, LOGGER);
        }
    }

    /**
     * Función que extrae los valores tipo dirección y los agrupa en un solo
     * listado.
     *
     * @author becerraarmr
     */
    public void tipoDireccionSeleccionada() {
        try {
            limpiarCamposTipoDireccion();
            if ("CK".equalsIgnoreCase(tipoDireccion)) {
                limpiarCamposTipoDireccion();
                showCK();
            } else if ("BM".equalsIgnoreCase(tipoDireccion)) {
                limpiarCamposTipoDireccion();
                showBM();
            } else if ("IT".equalsIgnoreCase(tipoDireccion)) {
                limpiarCamposTipoDireccion();
                showIT();
            } else if ("".equalsIgnoreCase(tipoDireccion)) {
                limpiarCamposTipoDireccion();
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en tipoDireccionSeleccionada, al validar el tipo de dirección seleccionado. ", e, LOGGER);
            direccionLabel = "Error al validar el tipo de dirección seleccionado.";
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en tipoDireccionSeleccionada, al validar el tipo de dirección seleccionado. ", e, LOGGER);
            direccionLabel = "Error al validar el tipo de dirección seleccionado.";
        }
    }

    /**
     * Función que limpiar los valores de la pantalla de tipo dirección
     *
     * @author Juan David Hernandez
     */
    public void limpiarCamposTipoDireccion() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = ";";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        barrioCk="";
        barrioCkTxt="";
    }

    /**
     * Función que limpiar los valores de la pantalla
     *
     * @author Juan David Hernandez
     */
    public void limpiarBusqueda() {
        drDireccion = new DrDireccion();
        responseConstruirDireccion.setDrDireccion(null);
        direccionCk = "";
        nivelValorIt = "";
        nivelValorBm = "";
        tipoNivelBm = ";";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        direccionLabel = "";
        ciudad = null;
        departamento = "";
        idCentroPoblado = null;
        division = "";
        comunidad = "";
        tipoDireccion = "CK";
        comunidadList = new ArrayList();
        ciudadesList = new ArrayList();
        centroPobladoList = new ArrayList();
        complemento = "";
        apartamento = "";

    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Calle-Carrera.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio.
     * @return 
     */
    public boolean validarDatosDireccionCk() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                direccionLabel = "Seleccione el TIPO DE DIRECCIÓN que "
                        + "desea ingresar por favor.";
                return false;
            } else if (direccionCk == null || direccionCk.isEmpty()) {
                direccionLabel = "Ingrese la dirección por favor.";
                return false;
            } else if (ciudad == null) {
                direccionLabel = "Seleccione la CIUDAD por favor.";
                return false;
            }
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionCk. EX. " +e.getMessage(), e);       
            direccionLabel = "Error al validar los datos de la dirección "
                    + "calle-carrera.";
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionCk. EX. " +e.getMessage(), e);       
            direccionLabel = "Error al validar los datos de la dirección "
                    + "calle-carrera.";
            return false;
        }
        return true;
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Barrio-Manzana.
     *
     * @author Juan David Hernandez return boolean false si algun dato se
     * encuentra vacio
     * @return 
     */
    public boolean validarDatosDireccionBm() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                direccionLabel = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                return false;
            } else {
                if (tipoNivelBm == null || tipoNivelBm.isEmpty()) {
                    direccionLabel = "Seleccione el TIPO DE NIVEL que desea"
                            + " agregar por favor.";
                    return false;
                } else {
                    if (ciudad == null) {
                        direccionLabel = "Seleccione la CIUDAD por favor.";
                        return false;
                    } else {
                        if (nivelValorBm == null || nivelValorBm.isEmpty()) {
                            direccionLabel = "Ingrese un valor en el campo de "
                                    + "nivel barrio-manzana por favor.";
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionBm. " +e.getMessage(), e);       
            direccionLabel = "Error al validar los datos de la dirección "
                    + "barrio-manzana.";
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionBm. " +e.getMessage(), e);       
            direccionLabel = "Error al validar los datos de la dirección "
                    + "barrio-manzana.";
            return false;
        }
    }

    /**
     * Función para validar datos nulos al momento de construir.una dirección
 tipo Intraducible.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionIt() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                direccionLabel = "Seleccione el TIPO DE DIRECCIÓN "
                        + "que desea ingresar por favor.";
                return false;
            } else {
                if (tipoNivelIt == null || tipoNivelIt.isEmpty()) {
                    direccionLabel = "Seleccione el TIPO DE NIVEL que "
                            + "desea agregar por favor.";
                    return false;
                } else {
                    if (ciudad == null) {
                        direccionLabel = "Seleccione la CIUDAD por favor.";
                        return false;
                    } else {
                        if (nivelValorIt == null || nivelValorIt.isEmpty()) {
                            direccionLabel = "Ingrese un valor en el campo "
                                    + "de nivel intraducible por favor";
                            return false;
                        } else {
                            if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                    && nivelValorIt.length() > 7) {
                                direccionLabel = "El valor para Contador no "
                                        + "puede exceder los 7 caracteres";
                                return false;
                            } else {
                                if (tipoNivelIt.equalsIgnoreCase("CONTADOR")
                                        && !isNumeric(nivelValorIt)) {
                                    direccionLabel = "El valor para Contador "
                                            + "debe ser númerico.";
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionIt. " +e.getMessage(), e);       
            direccionLabel = "Error al validar datos de dirección intraducible ";
            return false;

        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionIt. " +e.getMessage(), e);       
            direccionLabel = "Error al validar datos de dirección intraducible ";
            return false;

        }
    }

    /**
     * Función para validar si el dato recibido es númerico
     *
     * @author Juan David Hernandez} return true si el dato es úumerico
     * @param cadena
     * @return 
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            LOGGER.error("Error en isNumeric. " +e.getMessage(), e);       
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en isNumeric. " +e.getMessage(), e);       
            return false;
        }
    }

    /**
     * Construir la dirección Construye la dirección según la ciudad y el tipo
     * de dirección recibida
     *
     * @author becerraarmr
     * @param procesarReporteBean bean donde se carga la ciudad y el valor del
     * atributo
     * @param tipoDireccion tipo de direccion DIRECCIONCK=1; DIRECCIONBM=2;
     * DIRECCIONIT=3; DIRECCIONAPTO=4; DIRECCIONCOMPLEMENTO=5;
     */
    public void construir(ProcesarReporteBean procesarReporteBean, int tipoDireccion) {
        try {
        
            if (procesarReporteBean.getCentroPoblado() != null && procesarReporteBean.getCiudad() != null) {
                this.ciudad = new BigDecimal(procesarReporteBean.getCiudad());
                this.idCentroPoblado = new BigDecimal(procesarReporteBean.getCentroPoblado());
                obtenerCentroPobladoSeleccionado(idCentroPoblado);
            } else {
                direccionLabel = "Debe seleccionar un Centro Poblado para construir la dirección";
                return;
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en construir. " +e.getMessage(), e);  
            direccionLabel = "No hay ciudad para construir dirección";
            return;
        } catch (Exception e) {
            LOGGER.error("Error en construir. " +e.getMessage(), e);  
            direccionLabel = "No hay ciudad para construir dirección";
            return;
        }
        switch (tipoDireccion) {
            case DIRECCIONBM: {
                construirDireccionBm();
                break;
            }
            case DIRECCIONCK: {
                responseConstruirDireccion = new ResponseConstruccionDireccion();

                construirDireccionCk();

                procesarReporteBean.validaCiudadMultiorigen();
                
                if (procesarReporteBean.isCiudadMultiOrigen()) {
                    
                    if((barrioSugeridoSeleccionado == null || barrioSugeridoSeleccionado.isEmpty() || 
                            barrioSugeridoSeleccionado.equalsIgnoreCase("---")) &&
                             (barrioCkTxt == null || barrioCkTxt.isEmpty())) {
                         direccionLabel = "Debe ingresar un Barrio Sugerido ya que la ciudad es Multi-origen";
                        if (barrioSugeridoList.isEmpty()) {
                            barrioSugeridoList = direccionesValidacionFacadeLocal.obtenerBarrioSugerido(request);
                            if (barrioSugeridoList == null) {
                                barrioSugeridoList = new ArrayList();
                            }
                            AddressSuggested ad = new AddressSuggested();
                            ad.setAddress("Otro");
                            ad.setNeighborhood("Otro");
                            barrioSugeridoList.add(ad);
                        }
                      return ;
                    }

                    if(barrioSugeridoSeleccionado != null &&
                            !barrioSugeridoSeleccionado.isEmpty() &&                            
                            barrioSugeridoSeleccionado.equalsIgnoreCase("Otro") &&
                            barrioCkTxt!=null && !barrioCkTxt.isEmpty()){
                        barrioCk = barrioCkTxt.toUpperCase();
                        request.setBarrio(barrioCk);
                        construirDireccionCk();
                    }else{
                        
                        if (barrioSugeridoSeleccionado != null
                                && !barrioSugeridoSeleccionado.isEmpty()
                                && !barrioSugeridoSeleccionado.equalsIgnoreCase("Otro")
                                && !barrioSugeridoSeleccionado.equalsIgnoreCase("---")) {
                            barrioCk = barrioSugeridoSeleccionado.toUpperCase();
                            request.setBarrio(barrioCk);
                            construirDireccionCk();
                        } else {
                            direccionLabel = "Debe escribir un Barrio Sugerido ya que la ciudad es Multi-origen";
                            return;
                        }
                    }
                }
                break;
            }
            case DIRECCIONIT: {
                construirDireccionIt();
                break;
            }
            case DIRECCIONAPTO: {
                construirDireccionApartamento();
                break;
            }
            case DIRECCIONCOMPLEMENTO: {
                construirDireccionComplemento();
                break;
            }
        }
        if (request != null && request.getDrDireccion() != null
                && direccionLabel != null && !direccionLabel.isEmpty()) {
            request.getDrDireccion().setGeoReferenciadaString(direccionLabel);
            procesarReporteBean.setValorAtributo(request.getDrDireccion());
        }
    }

    /**
     * Función utilizada para construir dirección de tipo calle-carrera
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionCk() {
        try {
            if (validarDatosDireccionCk()) {
                request.setDireccionStr(direccionCk);
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoAdicion("N");
                request.setTipoNivel("N");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                //Consume servicio que retorna la dirección calle-carrera traducida.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);

                //Direccion traducida correctamente
                if (responseConstruirDireccion.getDireccionStr() != null
                        && !responseConstruirDireccion.getDireccionStr().isEmpty()
                        && responseConstruirDireccion.getResponseMesaje().
                                getTipoRespuesta().equalsIgnoreCase("I")) {

                    direccionLabel
                            = responseConstruirDireccion.getDireccionStr().
                                    toString();
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                } else {
                    //Dirección que no pudo ser traducida
                    if (responseConstruirDireccion.getDireccionStr() == null
                            || responseConstruirDireccion.getDireccionStr()
                                    .isEmpty() && responseConstruirDireccion
                                    .getResponseMesaje().getTipoRespuesta()
                                    .equalsIgnoreCase("E")) {

                        direccionLabel = direccionCk;
                        String msnError = "La dirección calle-carrera"
                                + " no pudo ser traducida."
                                + responseConstruirDireccion.getResponseMesaje()
                                        .getMensaje().toString();
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionCk, al construir dirección Calle-Carrera. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionCk, al construir dirección Calle-Carrera. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionBm() {
        try {
            if (validarDatosDireccionBm()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelBm);
                request.setValorNivel(nivelValorBm.trim().toUpperCase());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                /* Consume servicio que retorna la dirección barrio-manzana 
                traducida. */
                responseConstruirDireccion = direccionRRFacadeLocal
                        .construirDireccionSolicitud(request);

                // Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    String msnError = responseConstruirDireccion.getResponseMesaje()
                            .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        String barrio = responseConstruirDireccion.getDrDireccion()
                                .getBarrio() != null ? responseConstruirDireccion
                                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion.getDireccionStr()
                                .toString();
                        tipoNivelBm = "";
                        nivelValorBm = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionBm, al construir dirección Barrio-Manzana. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionBm, al construir dirección Barrio-Manzana. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionIt() {
        try {
            if (validarDatosDireccionIt()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelIt);
                request.setValorNivel(nivelValorIt.trim().toUpperCase());
                request.setTipoAdicion("P");
                request.setIdUsuario(usuarioLogin.getCedula().toString());
                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion
                            .getDrDireccion());
                }
                // Consume servicio que retorna la dirección intraducible traducida.
                responseConstruirDireccion = direccionRRFacadeLocal
                        .construirDireccionSolicitud(request);
                //Dirección no construida correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    direccionLabel = responseConstruirDireccion
                            .getResponseMesaje().getMensaje().toString();
                } else {
                    //Dirección construida correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        request.setDrDireccion(responseConstruirDireccion
                                .getDrDireccion());
                        
                        String barrioDireccion = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        tipoNivelIt = "";
                        nivelValorIt = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionIt, al construir dirección Intraducible. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionIt, al construir dirección Intraducible. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para construir dirección con complemento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionComplemento() {
        try {
            if (validarDatosDireccionComplemento()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelComplemento);
                request.setValorNivel(complemento);
                request.setTipoAdicion("C");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Consume servicio que retorna la dirección con complemento.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);
                //Complemento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    String msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                } else {
                    //Complemento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        String barrio = responseConstruirDireccion
                                .getDrDireccion().getBarrio() != null
                                        ? responseConstruirDireccion.getDrDireccion()
                                                .getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        complemento = "";
                        tipoNivelComplemento = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionComplemento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionComplemento. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para construir dirección con apartamento
     *
     * @author Juan David Hernandez
     */
    public void construirDireccionApartamento() {
        try {
            if (validarDatosDireccionApartamento()) {
                request.setComunidad(centroPobladoSeleccionado.getGeoCodigoDane());
                drDireccion.setIdTipoDireccion(tipoDireccion);
                request.setDrDireccion(drDireccion);
                request.setTipoNivel(tipoNivelApartamento);
                request.setValorNivel(apartamento);
                request.setTipoAdicion("A");
                request.setIdUsuario(usuarioLogin.getCedula().toString());

                if (responseConstruirDireccion != null
                        && responseConstruirDireccion.getDrDireccion() != null) {
                    request.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                }
                // Consume servicio que retorna la dirección con apartamento.
                responseConstruirDireccion = direccionRRFacadeLocal.
                        construirDireccionSolicitud(request);

                // Apartamento no agregado a la dirección correctamente
                if (responseConstruirDireccion == null
                        || responseConstruirDireccion.getResponseMesaje()
                                .getTipoRespuesta().equals("E")) {
                    String msnError
                            = responseConstruirDireccion.getResponseMesaje()
                                    .getMensaje().toString();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));

                } else {
                    // Apartamento agregado a la dirección correctamente
                    if (responseConstruirDireccion != null
                            && responseConstruirDireccion.getResponseMesaje()
                                    .getTipoRespuesta().equals("I")) {
                        String barrio
                                = responseConstruirDireccion.getDrDireccion()
                                        .getBarrio() != null ? responseConstruirDireccion
                                                .getDrDireccion().getBarrio() : "";
                        direccionLabel = responseConstruirDireccion
                                .getDireccionStr().toString();
                        apartamento = "";
                        tipoNivelApartamento = "";
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionApartamento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en construirDireccionApartamento. ", e, LOGGER);
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con apartamento.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionApartamento() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                direccionLabel = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                return false;
            } else {
                if (ciudad == null) {
                    direccionLabel = "Seleccione la CIUDAD por favor.";
                    return false;
                } else {

                    if (tipoNivelApartamento == null
                            || tipoNivelApartamento.isEmpty()) {
                        direccionLabel = "Seleccione el TIPO DE NIVEL de"
                                + " apartamento que desea agregar por favor.";
                        return false;
                    } else {
                        if (tipoNivelApartamento.equalsIgnoreCase("CASA")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("ADMINISTRACION")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("FUENTE")
                                || tipoNivelApartamento
                                        .equalsIgnoreCase("RECEPTOR")) {
                        } else {
                            if (apartamento == null || apartamento.isEmpty()) {
                                direccionLabel = "Ingrese un valor en el campo"
                                        + " apartamento por favor.";
                                return false;
                            } else {
                                if (tipoNivelApartamento.contains("PI")
                                        && !(apartamento.equalsIgnoreCase("1")
                                        || apartamento.equalsIgnoreCase("2")
                                        || apartamento.equalsIgnoreCase("3"))) {

                                    direccionLabel = "El valor permitido"
                                            + " para PISO solo puede "
                                            + "ser 1, 2 o 3";
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionApartamento. " +e.getMessage(), e);     
            direccionLabel = "Error al validar datos de direccion apartamento.";
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionApartamento. " +e.getMessage(), e);     
            direccionLabel = "Error al validar datos de direccion apartamento.";
            return false;
        }
    }

    /**
     * Función para validar datos nulos al momento de construir una dirección
     * con complemento.
     *
     * @author Juan David Hernandez return false si algun dato se encuentra
     * vacio
     * @return 
     */
    public boolean validarDatosDireccionComplemento() {
        try {
            if (tipoDireccion == null || tipoDireccion.isEmpty()) {
                String msnError = "Seleccione el TIPO DE DIRECCIÓN que desea"
                        + " ingresar por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
                return false;
            } else {
                if (tipoNivelComplemento == null
                        || tipoNivelComplemento.isEmpty()) {
                    String msnError = "Seleccione TIPO DE NIVEL del complemento"
                            + " por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                    return false;
                } else {
                    if (ciudad == null) {
                        String msnError = "Seleccione la CIUDAD por favor.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        msnError, ""));
                        return false;
                    } else {
                        if (complemento == null || complemento.isEmpty()) {
                            String msnError = "Ingrese un valor en el campo "
                                    + "del complemento por favor.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            msnError, ""));
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (RuntimeException e) {
            LOGGER.error("Error en validarDatosDireccionComplemento. " +e.getMessage(), e);    
            direccionLabel = "Error al validar datos de direccion apartamento.";
            return false;
        } catch (Exception e) {
            LOGGER.error("Error en validarDatosDireccionComplemento. " +e.getMessage(), e);    
            direccionLabel = "Error al validar datos de direccion apartamento.";
            return false;
        }
    }

    /**
     * Función para validar la construccion de una direccion bidireccional
     *
     * @author Juan David Hernandez return false si la dirección no cumple con
     * algun criterio de validación
     * @param apartamento
     * @return 
     */
    public boolean validarDatosDireccionApartamentoBiDireccional(DrDireccion drDireccion, String tipoNivelApartamento, String apartamento) {
        try {
            return direccionesValidacionFacadeLocal.
                    validarConstruccionApartamentoBiDireccional(drDireccion,
                            "U", tipoNivelApartamento, apartamento);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarDatosDireccionApartamentoBiDireccional, al validar apartamento para tecnologia. ", e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarDatosDireccionApartamentoBiDireccional, al validar apartamento para tecnologia. ", e, LOGGER);
            return false;
        }
    }  
 
    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadesList() {
        try {
            if (departamento != null
                    && !departamento.isEmpty()) {

                //Obtenemos el listado de ciudades para el filtro de la pantalla
                ciudadesList
                        = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(departamento));
                centroPobladoList = new ArrayList();
            }
            ciudadList = new ArrayList();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadesList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadesList. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener el listado de regiones desde la base
     * datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerListaRegionales() {
        try {
            cargarListadosConfiguracion();
            ciudadesList = null;
            centroPobladoList = null;
            rrRegional = null;
            ciudad = null;
            idCentroPoblado = null;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerListaRegionales. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerListaRegionales. ", e, LOGGER);
        }
    }

    /**
     * Obtiene el listado de comunidades de la base de datos por división.
     *
     * @author Juan David Hernandez
     */
    public void obtenerComunidadesList() {
        try {
            ciudad = null;
            departamento = "";
            comunidad = "";
            idCentroPoblado = null;
            ciudadesList = new ArrayList();
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();
            if (division != null && !division.isEmpty()) {
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList, al obtener listado de comunidades. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerComunidadesList, al obtener listado de comunidades. ", e, LOGGER);
        }
    }

    /**
     * Funcion utilizada para obtener los listados utilizados en la pantalla
     * desde la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarListadosConfiguracion() throws ApplicationException {
        try {
            //Obtiene lista de listas de los componentes de la dirección.          
            configurationAddressComponent
                    = componenteDireccionesMglFacade
                            .getConfiguracionComponente("U");

            ckList = componenteDireccionesMglFacade
                    .obtenerCalleCarreraList(configurationAddressComponent);
            bmList = componenteDireccionesMglFacade
                    .obtenerBarrioManzanaList(configurationAddressComponent);
            itList = componenteDireccionesMglFacade
                    .obtenerIntraducibleList(configurationAddressComponent);
            aptoList = componenteDireccionesMglFacade
                    .obtenerApartamentoList(configurationAddressComponent);
            complementoList = componenteDireccionesMglFacade
                    .obtenerComplementoList(configurationAddressComponent);
            configurationAddressComponent.getAptoValues();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosConfiguracion, al realizar consultas para obtener configuración. ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarListadosConfiguracion, al realizar consultas para obtener configuración. ", e, LOGGER);            
        }
    }

    /**
     * Función utilizada para validar la ciudad seleccionada y limpiar los
     * paneles de dirección en la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void validarCiudadSeleccionada() {
        try {
            if (comunidad == null || comunidad.isEmpty()) {
                hideTipoDireccion();
            } else {
                //Obtiene centro Poblado
                obtenerGeograficoPoliticoList();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarCiudadSeleccionada. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para obtener del georeferenciador el departamento,
     * ciudad y el listado de centro poblado.
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerGeograficoPoliticoList() throws ApplicationException {
        try {
            GeograficoPoliticoMgl geograficoPoliticoMgl
                    = geograficoPoliticoMglFacadeLocal
                            .findCityByComundidad(comunidad);
            departamentoGpo = (geograficoPoliticoMglFacadeLocal
                    .findById(geograficoPoliticoMgl.getGeoGpoId()));
            ciudadGpo = geograficoPoliticoMgl;
            centroPobladoList = geograficoPoliticoMglFacadeLocal
                    .findCentroPoblado(ciudadGpo.getGpoId());

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerGeograficoPoliticoList. " +e.getMessage(), e);    
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerGeograficoPoliticoList. " +e.getMessage(), e);    
            throw new ApplicationException("Error en obtenerGeograficoPoliticoList. ",e);
        }

    }

    /**
     * Función que obtiene asigna el valor del departamento y la ciudad al ser
     * seleccionada una comunidad.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCiudadDepartamentoByComunidad() {
        try {
            departamento = "";
            ciudad = null;
            idCentroPoblado = null;
            centroPobladoList = new ArrayList();
            ciudadList = new ArrayList();

            if (comunidad != null && !comunidad.isEmpty()) {
                GeograficoPoliticoMgl geograficoPoliticoMgl
                        = geograficoPoliticoMglFacadeLocal
                                .findCityByComundidad(comunidad);
                if (geograficoPoliticoMgl != null) {
                    obtenerDepartamentoList();
                    departamento = geograficoPoliticoMgl.getGeoGpoId() + "";
                    obtenerCiudadesList();
                    ciudad = geograficoPoliticoMgl.getGpoId();
                    obtenerCentroPobladoList();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad, al obtener listado ciudad y departamento. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerCiudadDepartamentoByComunidad, al obtener listado ciudad y departamento. ", e, LOGGER);
        }
    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerCentroPobladoList() throws ApplicationException {
        try {
            if (ciudad != null) {
                centroPobladoList = geograficoPoliticoMglFacadeLocal
                        .findCentroPoblado(ciudad);
            } else {
                centroPobladoList = new ArrayList();
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerCentroPobladoList. " +e.getMessage(), e);    
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerCentroPobladoList. " +e.getMessage(), e);    
            throw new ApplicationException("Error en obtenerCentroPobladoList. ",e);
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void obtenerDepartamentoList() throws ApplicationException {
        try {
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            LOGGER.error("Error en obtenerDepartamentoList. " +e.getMessage(), e);   
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en obtenerDepartamentoList. " +e.getMessage(), e);   
            throw new ApplicationException("Error en obtenerDepartamentoList. " ,e);
        }
    }

    /**
     * Función utilizada para reiniciar la dirección al realizar un cambio de
     * ciudad.
     *
     * @author Juan David Hernandez
     */
    public void reiniciarDireccion() {
        request = new RequestConstruccionDireccion();
        responseConstruirDireccion = new ResponseConstruccionDireccion();
        barrioSugeridoList = new ArrayList<AddressSuggested>();
        drDireccion = new DrDireccion();
        tipoDireccion = "";
        direccionCk = "";
        nivelValorBm = "";
        nivelValorIt = "";
        tipoNivelBm = "";
        tipoNivelIt = "";
        tipoNivelApartamento = "";
        tipoNivelComplemento = "";
        apartamento = "";
        complemento = "";
        direccionLabel = "";
        barrioCk = "";
        ciudad = null;
        idCentroPoblado = null;
        centroPobladoList = null;
        hideTipoDireccion();
        showCK();
        tipoDireccion = "CK";
    }

    /**
     * Función que renderiza paneles de tipo dirección en la pantalla.
     *
     * @author Juan David Hernandez
     */
    private void hideTipoDireccion() {
        showBMPanel = false;
        showITPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección calle-carrera en la
     * pantalla
     *
     * @author Juan David Hernandez
     */
    private void showCK() {
        showCKPanel = true;
        showBMPanel = false;
        showITPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección barrio-manzana en la
     * pantalla
     *
     * @author Juan David Hernandez
     */
    private void showBM() {
        showBMPanel = true;
        showITPanel = false;
        showCKPanel = false;
    }

    /**
     * Función que renderiza panel de tipo dirección intraducible en la pantalla
     *
     * @author Juan David Hernandez
     */
    private void showIT() {
        showITPanel = true;
        showBMPanel = false;
        showCKPanel = false;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public ResponseConstruccionDireccion getResponseConstruirDireccion() {
        return responseConstruirDireccion;
    }

    public void setResponseConstruirDireccion(ResponseConstruccionDireccion responseConstruirDireccion) {
        this.responseConstruirDireccion = responseConstruirDireccion;
    }

     public String getDireccionCk() {
        return direccionCk;
    }

    public void setDireccionCk(String direccionCk) {
        this.direccionCk = direccionCk;
    }

    public String getNivelValorBm() {
        return nivelValorBm;
    }

    public void setNivelValorBm(String nivelValorBm) {
        this.nivelValorBm = nivelValorBm;
    }

    public String getNivelValorIt() {
        return nivelValorIt;
    }

    public void setNivelValorIt(String nivelValorIt) {
        this.nivelValorIt = nivelValorIt;
    }

    public String getTipoNivelBm() {
        return tipoNivelBm;
    }

    public void setTipoNivelBm(String tipoNivelBm) {
        this.tipoNivelBm = tipoNivelBm;
    }

    public String getTipoNivelIt() {
        return tipoNivelIt;
    }

    public void setTipoNivelIt(String tipoNivelIt) {
        this.tipoNivelIt = tipoNivelIt;
    }

    public String getTipoNivelApartamento() {
        return tipoNivelApartamento;
    }

    public void setTipoNivelApartamento(String tipoNivelApartamento) {
        this.tipoNivelApartamento = tipoNivelApartamento;
    }

    public String getTipoNivelComplemento() {
        return tipoNivelComplemento;
    }

    public void setTipoNivelComplemento(String tipoNivelComplemento) {
        this.tipoNivelComplemento = tipoNivelComplemento;
    }

    public String getTipoNivelNuevoApartamento() {
        return tipoNivelNuevoApartamento;
    }

    public void setTipoNivelNuevoApartamento(String tipoNivelNuevoApartamento) {
        this.tipoNivelNuevoApartamento = tipoNivelNuevoApartamento;
    }

    public String getValorNivelNuevoApartamento() {
        return valorNivelNuevoApartamento;
    }

    public void setValorNivelNuevoApartamento(String valorNivelNuevoApartamento) {
        this.valorNivelNuevoApartamento = valorNivelNuevoApartamento;
    }

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public boolean isShowCKPanel() {
        return showCKPanel;
    }

    public void setShowCKPanel(boolean showCKPanel) {
        this.showCKPanel = showCKPanel;
    }

    public boolean isShowBMPanel() {
        return showBMPanel;
    }

    public void setShowBMPanel(boolean showBMPanel) {
        this.showBMPanel = showBMPanel;
    }

    public boolean isShowITPanel() {
        return showITPanel;
    }

    public void setShowITPanel(boolean showITPanel) {
        this.showITPanel = showITPanel;
    }

    public List<OpcionIdNombre> getCkList() {
        return ckList;
    }

    public void setCkList(List<OpcionIdNombre> ckList) {
        this.ckList = ckList;
    }

    public List<OpcionIdNombre> getBmList() {
        return bmList;
    }

    public void setBmList(List<OpcionIdNombre> bmList) {
        this.bmList = bmList;
    }

    public List<OpcionIdNombre> getItList() {
        return itList;
    }

    public void setItList(List<OpcionIdNombre> itList) {
        this.itList = itList;
    }

    public List<OpcionIdNombre> getAptoList() {
        return aptoList;
    }

    public void setAptoList(List<OpcionIdNombre> aptoList) {
        this.aptoList = aptoList;
    }

    public List<OpcionIdNombre> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<OpcionIdNombre> complementoList) {
        this.complementoList = complementoList;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public BigDecimal getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(BigDecimal idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<RrRegionales> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<RrRegionales> regionalList) {
        this.regionalList = regionalList;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<RrCiudades> getComunidadList() {
        return comunidadList;
    }

    public void setComunidadList(List<RrCiudades> comunidadList) {
        this.comunidadList = comunidadList;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    /**
     * @return the DIRECCIONCK
     */
    public int getDIRECCIONCK() {
        return DIRECCIONCK;
    }

    /**
     * @return the DIRECCIONBM
     */
    public int getDIRECCIONBM() {
        return DIRECCIONBM;
    }

    /**
     * @return the DIRECCIONIT
     */
    public int getDIRECCIONIT() {
        return DIRECCIONIT;
    }

    /**
     * @return the DIRECCIONAPTO
     */
    public int getDIRECCIONAPTO() {
        return DIRECCIONAPTO;
    }

    /**
     * @return the DIRECCIONCOMPLEMENTO
     */
    public int getDIRECCIONCOMPLEMENTO() {
        return DIRECCIONCOMPLEMENTO;
    }

    /**
     * @return the barrioCk
     */
    public String getBarrioCk() {
        return barrioCk;
    }

    /**
     * @param barrioCk the barrioCk to set
     */
    public void setBarrioCk(String barrioCk) {
        this.barrioCk = barrioCk;
    }

    /**
     * @return the barrioCkTxt
     */
    public String getBarrioCkTxt() {
        return barrioCkTxt;
    }

    /**
     * @param barrioCkTxt the barrioCkTxt to set
     */
    public void setBarrioCkTxt(String barrioCkTxt) {
        this.barrioCkTxt = barrioCkTxt;
    }

    public String getBarrioSugeridoSeleccionado() {
        return barrioSugeridoSeleccionado;
    }

    public void setBarrioSugeridoSeleccionado(String barrioSugeridoSeleccionado) {
        this.barrioSugeridoSeleccionado = barrioSugeridoSeleccionado;
    }
    
    
    
}
