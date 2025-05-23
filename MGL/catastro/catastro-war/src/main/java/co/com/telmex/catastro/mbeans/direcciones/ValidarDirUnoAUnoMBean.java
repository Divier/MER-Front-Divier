package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressAggregated;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Privilegios;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.PrivilegiosRolDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.delegate.ValidacionDirDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase ValidarDirUnoAUnoMBean Extiende de BaseMBean
 *
 * @author Deiver Rovira
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version	1.2 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 */
@ViewScoped
@ManagedBean(name = "validarDirUnoAUnoMBean")
public class ValidarDirUnoAUnoMBean extends BaseMBean {

    private static final String MENSAJE_DESEA_GUARDAR_DIRECCION_CONFIRMAR = "Desea guardar esta dirección en el repositorio?";
    private static final String MENSAJE_DESEA_GUARDAR_COMO_DIRECCION_NO_VÁLIDA = "Desea guardar como dirección no válida para próxima validación?";
    private static final String MENSAJE_EXISTE_UNA_NUEVA_VERSION_DE_LA_DIRECCION = "Existe una nueva versión de la dirección consultada, cual desea guardar?";
    /**
     *
     */
    public static final String NOMBRE_FUNCIONALIDAD = "VALIDAR DIRECCIONES ONLINE UNO A UNO";
    private static int PORCENTAJE_CONFIABILIDAD_ALTA;
    private static final String ACRONIMO_NIVEL_CONFIABILIDAD = "NIVEL_CONFIABILIDAD";
    private String dir_pais;
    private String dir_regional;
    private String dir_ciudad;
    private String dir_barrio;
    private String dir_tipoConsulta;
    private String dir_ingresadaTexto;
    private String dir_NoEstandar;
    private String vacio = "";
    private List<GeograficoPolitico> paises;
    private List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> regionales = null;
    private List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> ciudades = null;
    private List<SelectItem> listCiudades = null;
    //Variables de respuesta del WebService y del repositorio
    private String dirRta_estandar;
    private String dirRta_barrio;
    private String dirRta_estrato;
    private String dirRta_levelEconomic;
    private String dirRta_actEconomica;
    private String dirRta_manzana;
    private String dirRta_alterna;
    private String dirRta_calificador;
    private String dirRta_existencia;
    private String dirRta_nvlCalidad;
    private String dirRta_recomendaciones;
    private String dirRta_flags;
    private String dirRta_fuente;
    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
    private String dirRta_nodoUno;
    private String dirRta_nodoDos;
    private String dirRta_nodoTres;
    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
    private String dirRta_estratoDef; //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218 V 1.2   

    //Estilos para cada uno de los campos de pantalla
    private String dirRta_estandar_css = "";
    private String dirRta_barrio_css = "";
    private String dirRta_estrato_css = "";
    private String dirRta_levelEconomic_css = "";
    private String dirRta_actEconomica_css = "";
    private String dirRta_manzana_css = "";
    private String dirRta_alterna_css = "";
    private String dirRta_calificador_css = "";
    private String dirRta_existencia_css = "";
    private String dirRta_nvlCalidad_css = "";
    private String dirRta_recomendaciones_css = "";
    private String dirRta_flags_css = "";
    private String dirRta_fuente_css = "";
    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
    private String dirRta_nodoUno_css;
    private String dirRta_nodoDos_css;
    private String dirRta_nodoTres_css;
    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218

    //Fin estilos
    private AddressService responseSrv;
    private List<SolicitudConsulta> direcciones = null;
    //Atributos de la direccion parametrizada
    private String seleccionar = "";
    private String son_tcalle = "";
    private String son_calle = "";
    private String son_letraCalle = "";
    private String son_calle2 = "";
    private String son_letraCalle2 = "";
    private String son_prefijoCalle = "";
    private String son_calle3 = "";
    private String son_letraCalles = "";
    private String son_cardinalCalle = "";
    //Atributos de la placa
    private String son_tcalle2 = "";
    private String son_placa1 = "";
    private String son_letraPlaca = "";
    private String son_calle4 = "";
    private String son_letraCalle3 = "";
    private String son_prefijoPlaca = "";
    private String son_letraCalle4 = "";
    private String son_cardinalPlaca = "";
    //Placa parte 2
    private String son_placa2 = "";
    private String son_cardinalPlaca2 = "";
    private String tipocalle = "";
    private String tipocalle2 = "";
    private List<SelectItem> listTCalles = null;
    private List<Multivalor> tCalles = null;
    private List<SelectItem> listLetras = null;
    private List<Multivalor> letras = null;
    private List<SelectItem> listLetrasyNumeros = null;
    private List<Multivalor> letrasynumeros = null;
    private List<SelectItem> listPrefijos = null;
    private List<Multivalor> prefijos = null;
    private String cardinalCalle = "";
    private List<SelectItem> listCardinales = null;
    private List<Multivalor> cardinales = null;
    private String cardinalPlaca = "";
    private String cardinalPlaca2 = "";
    private String son_apto = "";
    private String son_complemento = "";
    //Variables para consumir el WS
    private GeograficoPolitico city = null;
    private GeograficoPolitico dpto = null;
    private boolean mostrarGuardar;
    private boolean showTipoBogota;
    private boolean showTipoMedellin;
    private boolean showTipoCali;
    private boolean showDireccionesAgregadas = false;
    private boolean showDireccionesSugeridas = false;
    //Variable para determinar si se debe validar el barrio
    private boolean validarBarrio = false;
    //variable de log para la clase
    private String nombreLog;
    //Lista de privilegios para esta funcionalidad
    List<Privilegios> privilegios = null;
    private List<AddressAggregated> direccionesAgregadas = new ArrayList<AddressAggregated>();
    private List<AddressSuggested> direccionesSugeridas = new ArrayList<AddressSuggested>();
    private String tipoDireccion = "";
    private String flagConfirmar = "";
    private String showFlagConfirmar = "";
    private boolean showBotones = true;
    private String mensajeUsuario = "";
    private boolean showPanelConfirmar = false;
    private boolean showPanelRevisarEnCampo = false;
    private boolean showPanelDecision = false;
    private boolean showPopUp = false;
    private boolean showBotonConfirmar = false;

    //Variables de secion
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    private static final Logger LOGGER = LogManager.getLogger(ValidarDirUnoAUnoMBean.class);

    /**
     *
     * @throws IOException
     */
    public ValidarDirUnoAUnoMBean() throws IOException {
        super();
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ValidarDirUnoAUnoMBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ValidarDirUnoAUnoMBean. " + e.getMessage(), e, LOGGER);
        }
        responseSrv = new AddressService();
        initLists();
        obtenerRestricciones();

    }

    /**
     * Valida la direccion ingresada por el usuario
     *
     * @return
     */
    public boolean validarDir() {
        message = "";
        showPanelDecision = false;
        showPanelRevisarEnCampo = false;

        tipocalle = "";

        createDirNoStandar();
        //Se restablecen las direcciones agregadas
        direccionesAgregadas = new ArrayList<AddressAggregated>();
        direccionesSugeridas = new ArrayList<AddressSuggested>();
        showDireccionesSugeridas = false;
        validarMostrarListaDirecciones();

        //Consultar el WebService parera obtener los datos de la direccion ingresada
        AddressRequest requestSrv = new AddressRequest();

        //Obtenemos el departamento
        dpto = loadRegional();
        requestSrv.setState(dpto.getGpoNombre());

        //Obtenemos la ciudad
        city = loadCity();
        requestSrv.setCity(city.getGpoNombre());

        //Se debe definir con cual direccion voy a consumir el servicio
        if (!dir_NoEstandar.equals("") && dir_NoEstandar.trim().length() > 0) {
            requestSrv.setAddress(dir_NoEstandar);
        } else if (!dir_ingresadaTexto.equals("")) {
            requestSrv.setAddress(dir_ingresadaTexto);
        }

        if (!"".equals(dir_barrio) && dir_barrio != null) {
            requestSrv.setNeighborhood(dir_barrio);
        }

        if (!"".equals(dir_tipoConsulta) && dir_tipoConsulta != null) {
            requestSrv.setLevel(dir_tipoConsulta);
        } else {
            requestSrv.setLevel(Constant.TIPO_CONSULTA_SENCILLA);
        }

        try {
            responseSrv = GeoreferenciaDelegate.queryAddressEnrich(requestSrv);

            if (responseSrv != null) {
                if (!"".equals(responseSrv.getAddress())) {
                    dirRta_estandar = responseSrv.getAddress();
                    dirRta_barrio = responseSrv.getNeighborhood();
                    dirRta_estrato = responseSrv.getLeveleconomic();
                    dirRta_actEconomica = responseSrv.getActivityeconomic();
                    dirRta_manzana = responseSrv.getAppletstandar();
                    dirRta_alterna = responseSrv.getAlternateaddress();
                    dirRta_fuente = responseSrv.getChagenumber();
                    //Valores obtenidos del Repositorio
                    dirRta_existencia = convertirMensaje(responseSrv.getExist());
                    dirRta_nvlCalidad = responseSrv.getLevellive();
                    dirRta_calificador = responseSrv.getQualifiers();
                    dirRta_recomendaciones = responseSrv.getRecommendations();
                    dirRta_flags = "Flags retornadas, faltan por definir cuales";
                    direccionesAgregadas = responseSrv.getAddressAggregated();
                    direccionesSugeridas = responseSrv.getAddressSuggested();
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
                    dirRta_nodoUno = responseSrv.getNodoUno();
                    dirRta_nodoDos = responseSrv.getNodoDos();
                    dirRta_nodoTres = responseSrv.getNodoTres();
                    //Inicio Modificacion Carlos Villamil Direcciones Fase I 20121218
                    dirRta_estratoDef = responseSrv.getEstratoDef();//Modificacion Carlos Villamil Direcciones Fase I 20121218 V 1.2
                    validarMostrarListaDirecciones();
                } else {
                    LimpiarResultados();
                    dirRta_recomendaciones = "La dirección no se ha traducido." + responseSrv.getRecommendations();
                }
            }

            //Se valida si el usuario puede guardar direcciones
            habilitarBotonCrearDireccion();

        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en validarDir. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarDir. " + e.getMessage(), e, LOGGER);
        }
        return true;
    }

    /**
     *
     */
    private void LimpiarResultados() {
        dirRta_estandar = "";
        dirRta_barrio = "";
        dirRta_estrato = "";
        dirRta_actEconomica = "";
        dirRta_manzana = "";
        dirRta_alterna = "";
        dirRta_fuente = "";
        dirRta_existencia = "";
        dirRta_nvlCalidad = "";
        dirRta_calificador = "";
        dirRta_recomendaciones = "";
        dirRta_flags = "";
        direccionesAgregadas = new ArrayList<AddressAggregated>();
        direccionesSugeridas = new ArrayList<AddressSuggested>();
        showDireccionesAgregadas = false;
    }

    /**
     *
     */
    private void validarMostrarListaDirecciones() {
        if ((direccionesAgregadas != null) && (direccionesAgregadas.size() > 0)) {
            showDireccionesAgregadas = true;
        } else {
            showDireccionesAgregadas = false;
        }
    }

    /**
     *
     * @param idAddress
     * @return
     */
    private String validarExistenciaOnRepositorio(String idAddress) {
        String existe = Constant.DIR_NO_EXISTE_MSJ;
        if (!"0".equals(idAddress) && idAddress != null) {
            existe = Constant.DIR_SI_EXISTE_MSJ;
        }
        return existe;
    }

    /**
     *
     * @param srvExist
     * @return
     */
    private String convertirMensaje(String srvExist) {
        String existe = Constant.DIR_NO_EXISTE_MSJ;
        if (!"false".equals(srvExist) && srvExist != null) {
            existe = Constant.DIR_SI_EXISTE_MSJ;
        }
        return existe;
    }

    /**
     * Implementacion DUMMY del servicio
     */
    private void dummyServicio() {
        //TODO: Implementacion DUMMY
        responseSrv.setActivityeconomic("dumy_act_economica");
        responseSrv.setAddress("dumy_address");
        responseSrv.setAlternateaddress("dumy_direccionAlterna");
        responseSrv.setAppletstandar("dumy_codigo_manzana");
        responseSrv.setCategory("dumy_categoria");
        responseSrv.setChagenumber("dumy_nueva o antigua");
        responseSrv.setExist("dumy_existencia");
        responseSrv.setIdaddress("dumy_id_Direccion");
        responseSrv.setLeveleconomic("dumy_Nivel_socioEconomico Estrato");
        responseSrv.setLevellive("dumy_Nivel de calidad");
        responseSrv.setNeighborhood("dumy_Barrio");
        responseSrv.setQualifiers("dumy_calificadores");
        responseSrv.setRecommendations("dumy_Recomendaciones");
        responseSrv.setTraslate("dumy_traslate");
        //Se adicionan las direcciones sugeridas
        List<AddressSuggested> dirSugeridas = new ArrayList<AddressSuggested>();
        AddressSuggested dirSug = new AddressSuggested();
        dirSug.setAddress("dummy_dirSugerida_Direccion");
        dirSug.setNeighborhood("dummy_dirSugerida_Barrio");
        dirSugeridas.add(dirSug);
        AddressSuggested dirSug2 = new AddressSuggested();
        dirSug2.setAddress("dummy_dirSugerida_Direccion2");
        dirSug2.setNeighborhood("dummy_dirSugerida_Barrio2");
        dirSugeridas.add(dirSug2);
        responseSrv.setAddressSuggested(dirSugeridas);

    }

    /**
     *
     * @param ev
     */
    public void doLimpiar(ActionEvent ev) {
        limpiarCamposTipoDireccion();
        dir_ingresadaTexto = "";
        UIComponent cp = ev.getComponent();
        UIComponent rtaSencilla = cp.findComponent("respuestaSencilla");
        UIComponent rtaCompleta = cp.findComponent("respuestaCompleta");
        rtaSencilla.setRendered(false);
        rtaCompleta.setRendered(false);
        showPanelConfirmar = false;
        showPanelRevisarEnCampo = false;
        showBotonConfirmar = false;
        mostrarGuardar = false;
        showPanelDecision = false;
        showPopUp = false;
        message = "";
    }

    /**
     * ActionListener ejecutado al solicitar la validacion de la direccion
     *
     * @param ev
     */
    public void doValidar(ActionEvent ev) {
        showPopUp = false;
        showPanelConfirmar = false;
        showPanelRevisarEnCampo = false;
        showBotonConfirmar = false;
        message = "";
        //Se validan los campos obligatorios
        message = validarCamposObligatorios(validarBarrio);
        UIComponent cp = ev.getComponent();
        UIComponent rtaSencilla = cp.findComponent("respuestaSencilla");
        UIComponent rtaCompleta = cp.findComponent("respuestaCompleta");
        if (message.isEmpty()) {
            mostrarGuardar = true;
            showPanelDecision = true;
            //Se muestra el panel de consulta sencilla
            if (dir_tipoConsulta.equals("S")) {
                rtaSencilla.setRendered(true);
                rtaCompleta.setRendered(false);
            } else if (dir_tipoConsulta.equals("C")) {
                //Se muestra el panel de consulta completa
                rtaCompleta.setRendered(true);
                rtaSencilla.setRendered(false);
            }
            //Se procede a validar la direccion
            validarDir();
        } else {
            mostrarGuardar = false;
            rtaCompleta.setRendered(false);
            rtaSencilla.setRendered(false);
        }
    }

    /**
     * ActionListener ejecutado al seleccionar el tipo de consulta
     *
     * @param ev
     */
    public void doEnviar(ActionEvent ev) {
        mostrarGuardar = false;
        LOGGER.error("entro------------------------------------------------------------------");
        showDireccionesAgregadas = false;
        limpiarCamposTipoDireccion();
        cargarPaises();

        //Se carga el formulario de ingreso
        UIComponent cp = ev.getComponent();
        UIComponent panelPcpal = cp.findComponent("panelPrincipal");
        UIComponent rtaCompleta = cp.findComponent("respuestaCompleta");
        UIComponent rtaSencilla = cp.findComponent("respuestaSencilla");
        if (dir_tipoConsulta.equals(Constant.TIPO_CONSULTA_SENCILLA) || dir_tipoConsulta.equals(Constant.TIPO_CONSULTA_COMPLETA)) {
            panelPcpal.setRendered(true);
            message = "";
        } else {
            message = Constant.OBLIGATORIO_TIPO_CONSULTA;
            panelPcpal.setRendered(false);
        }
        rtaCompleta.setRendered(false);
        rtaSencilla.setRendered(false);

    }

    /**
     * Muestra al usuario la opcion indicada, de acuerdo a la logica de la
     * direccion consultada
     *
     * @param ev
     */
    public void doValidarAccion(ActionEvent ev) {
        showPopUp = false;
        showBotonConfirmar = false;
        mostrarGuardar = false;
        if (!"".equals(dirRta_alterna) && Constant.DIR_FUENTE_ANTIGUA.equals(dirRta_fuente)) {
            mensajeUsuario = MENSAJE_EXISTE_UNA_NUEVA_VERSION_DE_LA_DIRECCION;
            showPanelDecision = true;
            showPanelRevisarEnCampo = false;
            showPanelConfirmar = false;
        } else if ((Constant.DIR_NO_EXISTE_MSJ.equals(dirRta_existencia))
                && "0".equals(responseSrv.getIdaddress())) {
            mensajeUsuario = MENSAJE_DESEA_GUARDAR_COMO_DIRECCION_NO_VÁLIDA;
            showPanelRevisarEnCampo = true;
            showPanelDecision = false;
            showPanelConfirmar = false;
        } else {
            //Implementar logica para mostrar pop-up
            tipoDireccion = Constant.DIR_FUENTE_ANTIGUA;
            mensajeUsuario = MENSAJE_DESEA_GUARDAR_DIRECCION_CONFIRMAR;
            showPanelConfirmar = true;
            showPanelDecision = false;
        }
    }

    /**
     *
     */
    public void doGuardarDireccionNueva() {
        showPanelDecision = false;
        showBotonConfirmar = true;
        showPopUp = true;
        tipoDireccion = Constant.DIR_FUENTE_NUEVA;
    }

    /**
     *
     */
    public void doGuardarDireccionAntigua() {
        showPanelDecision = false;
        showBotonConfirmar = true;
        showPopUp = true;
        tipoDireccion = Constant.DIR_FUENTE_ANTIGUA;
    }

    /**
     *
     */
    public void doGuardarDireccionRevisarEnCampo() {
        showPanelRevisarEnCampo = false;
        showBotonConfirmar = true;
        showPanelConfirmar = false;
        showPopUp = true;
        //El servicio se encarga de almacenarlo con el flag de revisar en campo
    }

    /**
     *
     * @param ev
     */
    public void doCancelar(ActionEvent ev) {
        //Cancela la operacion
        showPanelDecision = false;
        showPanelRevisarEnCampo = false;
        showPanelConfirmar = false;
        mostrarGuardar = false;
        showBotonConfirmar = false;
    }

    /**
     *
     */
    public void doGuardarDireccion() {
        ResponseMessage response = new ResponseMessage();
        mostrarGuardar = Boolean.FALSE;
        showPopUp = false;
        showPanelConfirmar = false;
        showPanelDecision = false;
        showPanelRevisarEnCampo = false;
        showBotonConfirmar = false;

        try {
            AddressRequest req = new AddressRequest();
            if (Constant.DIR_FUENTE_ANTIGUA.equals(tipoDireccion) || tipoDireccion.isEmpty()) {
                //Se debe definir con cual direccion voy a consumir el servicio
                if (!dir_NoEstandar.equals("") && dir_NoEstandar.trim().length() > 0) {
                    req.setAddress(dir_NoEstandar);
                } else if (!dir_ingresadaTexto.equals("")) {
                    req.setAddress(dir_ingresadaTexto);
                }
            } else if (Constant.DIR_FUENTE_NUEVA.equals(tipoDireccion) && (!"".equals(dirRta_alterna))) {
                req.setAddress(dirRta_alterna);
            }
            req.setCity(city.getGpoNombre());
            req.setState(dpto.getGpoNombre());

            req.setNeighborhood(dir_barrio);
            
            response = GeoreferenciaDelegate.createAddress(req, this.user.getUsuLogin(), NOMBRE_FUNCIONALIDAD, "");

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en doGuardarDireccion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en doGuardarDireccion. " + e.getMessage(), e, LOGGER);
        }

        message = response.getMessageText();
    }

    /**
     * Actualiza la lista de Regionales de acuerdo al pais seleccionado
     *
     * @param event
     * @throws ApplicationException
     */
    public void updateRegionales(ValueChangeEvent event) throws ApplicationException {
        String value = event.getNewValue().toString();
        try {
            regionales = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            listRegionales = new ArrayList<SelectItem>();
            for (GeograficoPolitico gpo : regionales) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listRegionales.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en updateRegionales. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateRegionales. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Actualiza la lista de ciudades de acuerdo a la region seleccionada
     *
     * @param event
     * @throws ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event) throws ApplicationException {
        String value = event.getNewValue().toString();
        try {
            ciudades = SolicitudNegocioDelegate.queryCiudades(new BigDecimal(value));
            listCiudades = new ArrayList<SelectItem>();
            for (GeograficoPolitico gpo : ciudades) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listCiudades.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en updateCiudades. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateCiudades. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateTipoDireccion(ValueChangeEvent event) throws ApplicationException {
        String codCiudad = event.getNewValue().toString();
        String tipoCiudad = null;
        showDireccionesAgregadas = false;
        try {
            tipoCiudad = ValidacionDirDelegate.queryTipoCiudadByID(codCiudad);

            if (tipoCiudad != null) {
                if (tipoCiudad.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_MEDELLIN)) {
                    showTipoMedellin = true;
                    showTipoBogota = false;
                    showTipoCali = false;
                } else if (tipoCiudad.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_BOGOTA)) {
                    showTipoBogota = true;
                    showTipoMedellin = false;
                    showTipoCali = false;
                } else if (tipoCiudad.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_CALI)) {
                    showTipoCali = true;
                    showTipoMedellin = false;
                    showTipoBogota = false;
                } else {
                    message = "Error, la ciudad seleccionada no tiene un tipo de codigo de dirección.";
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateTipoDireccion. " + e.getMessage(), e, LOGGER);
        }

        limpiarCamposTipoDireccion();
        validarBarrio = false;
        try {
            validarBarrio = ValidacionDirDelegate.queryCiudadMultiorigen(codCiudad);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error determinando si es multiorigen. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Limpia los campos que componen la direccion
     */
    private void limpiarCamposTipoDireccion() {
        tipocalle = "";
        son_tcalle = "0";
        son_calle = "";
        son_letraCalle = "";
        son_calle2 = "";
        son_letraCalle2 = "";
        son_prefijoCalle = "";
        son_calle3 = "";
        son_letraCalles = "";
        son_cardinalCalle = "0";
        son_tcalle2 = "0";
        son_placa1 = "";
        son_letraPlaca = "";
        son_calle4 = "";
        son_letraCalle3 = "";
        son_prefijoPlaca = "";
        son_letraCalle4 = "";
        son_cardinalPlaca = "0";
        son_placa2 = "";
        son_cardinalPlaca2 = "0";
        son_complemento = "";
        son_apto = "";
    }

    /**
     * Carga los paises de la Base de datos
     */
    public void cargarPaises() {
        listPaises = new ArrayList<SelectItem>();
        try {
            paises = SolicitudNegocioDelegate.queryPaises();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarPaises. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarPaises. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Valida los campos obligatorios del formulario
     *
     * @return
     */
    private String validarCamposObligatorios(boolean validarBarrio) {
        message = "";

        if (("".equals(son_tcalle) || "".equals(son_calle)) && (dir_ingresadaTexto == null || "".equals(dir_ingresadaTexto))) {
            message = Constant.OBLIGATORIO_DIRECCION;
        }
        if ("".equals(dir_tipoConsulta) || dir_tipoConsulta == null) {
            message = Constant.OBLIGATORIO_TIPO_CONSULTA;
        }
        if ("0".equals(dir_pais) || dir_pais == null) {
            message = Constant.OBLIGATORIO_PAIS;
        } else if ("0".equals(dir_regional) || dir_regional == null) {
            message = Constant.OBLIGATORIO_DEPARTAMENTO;
        } else if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = Constant.OBLIGATORIO_CIUDAD;
        }
        if (validarBarrio) {
            //Se valida el barrio como obligatorio
            if ("".equals(dir_barrio) || dir_barrio == null) {
                message = Constant.OBLIGATORIO_BARRIO;
            }
        }
        return message;
    }

    /*Crea la direccion no estandarizada con los valores ingresados por el usuario*/
    private void createDirNoStandar() {
        loadTipoCalle();
        loadTipoCalle2();
        if ((loadCardinalidad(son_cardinalCalle)) != null) {
            cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
        }
        if (son_cardinalCalle.equals("0")) {
            cardinalCalle = "";
        }
        if (son_tcalle.equals("0")) {
            son_tcalle = "";
        }
        if (son_tcalle2.equals("0")) {
            son_tcalle2 = "";
        }

        if (!son_cardinalPlaca.isEmpty() && (loadCardinalidad(son_cardinalPlaca)) != null) {
            cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
        }
        if (son_cardinalPlaca.equals("0")) {
            cardinalPlaca = "";
        }

        if (!son_cardinalPlaca2.isEmpty() && (loadCardinalidad(son_cardinalPlaca2)) != null) {
            cardinalPlaca2 = (loadCardinalidad(son_cardinalPlaca2)).getDescripcion();
        }
        if (son_cardinalPlaca2.equals("0")) {
            cardinalPlaca2 = "";
        }

        if (son_prefijoCalle.equalsIgnoreCase("null") || son_prefijoCalle.equalsIgnoreCase("0")) {
            son_prefijoCalle = "";
        }
        if (son_prefijoPlaca.equalsIgnoreCase("null") || son_prefijoPlaca.equalsIgnoreCase("0")) {
            son_prefijoPlaca = "";
        }
        if (son_letraCalle.equalsIgnoreCase("null") || son_letraCalle.equalsIgnoreCase("0")) {
            son_letraCalle = "";
        }
        if (son_letraCalle2.equalsIgnoreCase("null") || son_letraCalle2.equalsIgnoreCase("0")) {
            son_letraCalle2 = "";
        }
        if (son_letraCalles.equalsIgnoreCase("null") || son_letraCalles.equalsIgnoreCase("0")) {
            son_letraCalles = "";
        }
        if (son_letraPlaca.equalsIgnoreCase("null") || son_letraPlaca.equalsIgnoreCase("0")) {
            son_letraPlaca = "";
        }
        if (son_letraCalle3.equalsIgnoreCase("null") || son_letraCalle3.equalsIgnoreCase("0")) {
            son_letraCalle3 = "";
        }
        if (son_letraCalle4.equalsIgnoreCase("null") || son_letraCalle4.equalsIgnoreCase("0")) {
            son_letraCalle4 = "";
        }

        if (!son_apto.isEmpty() && !son_apto.startsWith(Constant.PREFIJO_COMPLEMENTO)) {
            son_apto = "AP " + son_apto;
        }

        int bool = 0;
        try {
            bool = Integer.parseInt(son_letraCalle);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en createDirNoStandar son_letraCalle. " + e.getMessage(), e, LOGGER);
            bool = 0;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en createDirNoStandar son_letraCalle. " + e.getMessage(), e, LOGGER);
            bool = 0;
        }

        String separadorCalleCarrera = "";
        if (bool != 0) {
            separadorCalleCarrera = "-";
        }

        int bool2 = 0;
        try {
            bool2 = Integer.parseInt(son_letraPlaca);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en createDirNoStandar son_letraPlaca. " + e.getMessage(), e, LOGGER);
            bool2 = 0;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en createDirNoStandar son_letraPlaca. " + e.getMessage(), e, LOGGER);
            bool2 = 0;
        }

        String separadorPlacaParte1 = "";
        if (bool2 != 0) {
            separadorPlacaParte1 = "-";
        }

        String CalleCarrera = tipocalle + " " + son_calle + " " + separadorCalleCarrera + son_letraCalle + " " + son_calle2 + " " + son_letraCalle2 + " " + son_prefijoCalle + " " + son_calle3 + " " + son_letraCalles + " " + cardinalCalle;
        String PlacaParte1 = " " + tipocalle2 + " " + son_placa1 + " " + separadorPlacaParte1 + son_letraPlaca + " " + son_calle4 + " " + son_letraCalle3 + " " + son_prefijoPlaca + " " + son_letraCalle4;
        String PlacaParte2 = " " + cardinalPlaca + " " + son_placa2 + " " + cardinalPlaca2 + " ";
        String Complemento = son_complemento + " " + son_apto;

        if (!CalleCarrera.equals("") && CalleCarrera.trim().length() > 0) {
            dir_NoEstandar
                    = //Calle - carrera
                    CalleCarrera
                    //Separador #
                    + " # "
                    //Placa parte 1
                    + PlacaParte1
                    //Placa parte 2
                    + PlacaParte2
                    //Complemento
                    + Complemento;
        } else {
            dir_NoEstandar = "";
        }
    }

    /**
     *
     */
    private void loadTipoCalle() {
        for (int i = 0; i < tCalles.size(); i++) {
            Multivalor calle = tCalles.get(i);
            if (calle.getMulValor().equals(this.son_tcalle)) {
                tipocalle = calle.getDescripcion();
            }
        }
    }

    /**
     *
     */
    private void loadTipoCalle2() {
        for (int i = 0; i < tCalles.size(); i++) {
            Multivalor calle = tCalles.get(i);
            if (calle.getMulValor().equals(this.son_tcalle2)) {
                tipocalle2 = calle.getDescripcion();
            }
        }
    }

    /**
     *
     * @param cardib
     * @return
     */
    private Multivalor loadCardinalidad(String cardib) {
        Multivalor cardi = new Multivalor();
        if (cardib.equalsIgnoreCase("0") || cardib.equalsIgnoreCase("null")) {
            cardi = null;
        } else {
            for (int i = 0; i < cardinales.size(); i++) {
                Multivalor car = cardinales.get(i);
                if (car.getMulId().equals(new BigDecimal(cardib))) {
                    cardi = car;
                }
            }
        }
        return cardi;
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < ciudades.size(); i++) {
            GeograficoPolitico ciudad = ciudades.get(i);
            if (ciudad.getGpoId().equals(new BigDecimal(this.dir_ciudad))) {
                geo = ciudad;
            }
        }
        return geo;
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadRegional() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < regionales.size(); i++) {
            GeograficoPolitico region = regionales.get(i);
            if (region.getGpoId().equals(new BigDecimal(this.dir_regional))) {
                geo = region;
            }
        }
        return geo;
    }


    /*Inicializa las listas necesarias para crear la solicitud de Negocio */
    /**
     *
     */
    private void initLists() {
        listPaises = new ArrayList<SelectItem>();
        listTCalles = new ArrayList<SelectItem>();
        listLetras = new ArrayList<SelectItem>();
        listLetrasyNumeros = new ArrayList<SelectItem>();
        listPrefijos = new ArrayList<SelectItem>();
        listCardinales = new ArrayList<SelectItem>();
        try {
            tCalles = SolicitudNegocioDelegate.queryCalles();
            letras = SolicitudNegocioDelegate.queryLetras();
            letrasynumeros = SolicitudNegocioDelegate.queryLetrasyNumeros();
            prefijos = SolicitudNegocioDelegate.queryPrefijos();
            cardinales = SolicitudNegocioDelegate.queryCardinales();

            for (Multivalor calle : tCalles) {
                SelectItem item = new SelectItem();
                item.setValue(calle.getMulValor());
                item.setLabel(calle.getDescripcion());
                listTCalles.add(item);
            }
            for (Multivalor letra : letras) {
                SelectItem item = new SelectItem();
                item.setValue(letra.getMulValor());
                item.setLabel(letra.getDescripcion());
                listLetras.add(item);
            }
            for (Multivalor letra : letrasynumeros) {
                SelectItem item = new SelectItem();
                item.setValue(letra.getMulValor());
                item.setLabel(letra.getDescripcion());
                listLetrasyNumeros.add(item);
            }
            for (Multivalor prefijo : prefijos) {
                SelectItem item = new SelectItem();
                item.setValue(prefijo.getMulValor());
                item.setLabel(prefijo.getDescripcion());
                listPrefijos.add(item);
            }
            for (Multivalor cardinal : cardinales) {
                SelectItem item = new SelectItem();
                item.setValue(cardinal.getMulId());
                item.setLabel(cardinal.getMulValor());
                listCardinales.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en initLists. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en initLists. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Habilita el boton de guardar direccion dependiendo del rol del usuario
     *
     * @throws NumberFormatException
     */
    private void habilitarBotonCrearDireccion() throws NumberFormatException {
        boolean permiteGuardar = false;
        mostrarGuardar = Boolean.FALSE;
        try {
            PORCENTAJE_CONFIABILIDAD_ALTA = obtenerParametros();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al otener los valores de la tabla parametro. " + e.getMessage(), e, LOGGER);
        }
        BigDecimal[] roles = null;
        if ("0".equals(responseSrv.getIdaddress())
                && "true".equals(responseSrv.getExist())
                && Integer.valueOf(responseSrv.getLevellive()) >= PORCENTAJE_CONFIABILIDAD_ALTA) {
            permiteGuardar = true;

        } else if (("0".equals(responseSrv.getIdaddress())) && ("false".equals(responseSrv.getExist()))) {
            showDireccionesSugeridas = true;
            permiteGuardar = true;

            //Se debe preguntar al usuario ""desea guardar como dirección no válida para próxima validación?" pop-up)
            //En caso afirmativo se almacena con revisarEnCampo=1
        } else if ("-1".equals(responseSrv.getIdaddress())) {
            //Direccion intraducible
        }

        if (permiteGuardar) {
            //Se valida el rol
            try {
                roles = ValidacionDirDelegate.queryRolesAdministradores();
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al cargar los roles Adminsitrativos. " + e.getMessage(), e, LOGGER);
            }
            for (int i = 0; i < roles.length; i++) {
                //Se valida el rol 
                if (this.rol.getRolId().equals(roles[i])) {
                    //Se habilita el boton de guardar
                    mostrarGuardar = Boolean.TRUE;
                    break;
                } else {
                    mostrarGuardar = Boolean.FALSE;
                }
            }
        }
    }

    /**
     * Obtiene la lista de los campos a los cuales se les aplica una
     * restriccion, la cual esta configurada en la base de datos discriminada
     * por funcionalidad y atributo
     */
    private void obtenerRestricciones() {
        try {
            privilegios = PrivilegiosRolDelegate.queryPrivilegiosRol(this.rol.getRolId(), NOMBRE_FUNCIONALIDAD);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al cargar los privilegios. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar los privilegios. " + e.getMessage(), e, LOGGER);
        }
        if (privilegios != null) {
            for (Privilegios privilegio : privilegios) {
                String estiloCss = privilegio.getAtrNombre() + "_css";
                if (estiloCss.equals("dirRta_alterna_css")) {
                    dirRta_alterna_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_estandar_css")) {
                    dirRta_estandar_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_barrio_css")) {
                    dirRta_barrio_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_estrato_css")) {
                    dirRta_estrato_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_levelEconomic_css")) {
                    dirRta_levelEconomic_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_actEconomica_css")) {
                    dirRta_actEconomica_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_manzana_css")) {
                    dirRta_manzana_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_calificador_css")) {
                    dirRta_calificador_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_existencia_css")) {
                    dirRta_existencia_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_nvlCalidad_css")) {
                    dirRta_nvlCalidad_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_recomendaciones_css")) {
                    dirRta_recomendaciones_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_flags_css")) {
                    dirRta_flags_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_fuente_css")) {
                    dirRta_fuente_css = privilegio.getAtrEstilo();
//               INICIO Modificacion Fase I nodos Carlos Villamil V 1.1                    
                } else if (estiloCss.equals("dirRta_nodoUno_css")) {
                    dirRta_nodoUno_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_nodoDos_css")) {
                    dirRta_nodoDos_css = privilegio.getAtrEstilo();
                } else if (estiloCss.equals("dirRta_nodoTres_css")) {
                    dirRta_nodoTres_css = privilegio.getAtrEstilo();
                }
//               FIN Modificacion Fase I nodos Carlos Villamil V 1.1                    
            }
        }
    }

    /**
     *
     * @return @throws ApplicationException
     */
    private int obtenerParametros() throws ApplicationException {
        int nivelConfiabilidad = PORCENTAJE_CONFIABILIDAD_ALTA;
        nivelConfiabilidad = ValidacionDirDelegate.queryNivelConfiabilidadFromParametros(ACRONIMO_NIVEL_CONFIABILIDAD);
        return nivelConfiabilidad;
    }

    /**
     *
     * @return
     */
    public List<GeograficoPolitico> getCiudades() {
        return ciudades;
    }

    /**
     *
     * @param ciudades
     */
    public void setCiudades(List<GeograficoPolitico> ciudades) {
        this.ciudades = ciudades;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListCiudades() {
        return listCiudades;
    }

    /**
     *
     * @param listCiudades
     */
    public void setListCiudades(List<SelectItem> listCiudades) {
        this.listCiudades = listCiudades;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListPaises() {
        return listPaises;
    }

    /**
     *
     * @param listPaises
     */
    public void setListPaises(List<SelectItem> listPaises) {
        this.listPaises = listPaises;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListRegionales() {
        return listRegionales;
    }

    /**
     *
     * @param listRegionales
     */
    public void setListRegionales(List<SelectItem> listRegionales) {
        this.listRegionales = listRegionales;
    }

    /**
     *
     * @return
     */
    public List<GeograficoPolitico> getPaises() {
        return paises;
    }

    /**
     *
     * @param paises
     */
    public void setPaises(List<GeograficoPolitico> paises) {
        this.paises = paises;
    }

    /**
     *
     * @return
     */
    public List<GeograficoPolitico> getRegionales() {
        return regionales;
    }

    /**
     *
     * @param regionales
     */
    public void setRegionales(List<GeograficoPolitico> regionales) {
        this.regionales = regionales;
    }

    /**
     *
     * @return
     */
    public String getDir_NoEstandar() {
        return dir_NoEstandar;
    }

    /**
     *
     * @param dir_NoEstandar
     */
    public void setDir_NoEstandar(String dir_NoEstandar) {
        this.dir_NoEstandar = dir_NoEstandar;
    }

    /**
     *
     * @return
     */
    public String getDir_barrio() {
        return dir_barrio;
    }

    /**
     *
     * @param dir_barrio
     */
    public void setDir_barrio(String dir_barrio) {
        this.dir_barrio = dir_barrio;
    }

    /**
     *
     * @return
     */
    public String getDir_ciudad() {
        return dir_ciudad;
    }

    /**
     *
     * @param dir_ciudad
     */
    public void setDir_ciudad(String dir_ciudad) {
        this.dir_ciudad = dir_ciudad;
    }

    /**
     *
     * @return
     */
    public String getDir_pais() {
        return dir_pais;
    }

    /**
     *
     * @param dir_pais
     */
    public void setDir_pais(String dir_pais) {
        this.dir_pais = dir_pais;
    }

    /**
     *
     * @return
     */
    public String getDir_regional() {
        return dir_regional;
    }

    /**
     *
     * @param dir_regional
     */
    public void setDir_regional(String dir_regional) {
        this.dir_regional = dir_regional;
    }

    /**
     *
     * @return
     */
    public List<SolicitudConsulta> getCatalogs() {
        return direcciones;
    }

    /**
     *
     * @param direcciones
     */
    public void setCatalogs(List<SolicitudConsulta> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     *
     * @return
     */
    public String getDir_tipoConsulta() {
        return dir_tipoConsulta;
    }

    /**
     *
     * @param dir_tipoConsulta
     */
    public void setDir_tipoConsulta(String dir_tipoConsulta) {
        this.dir_tipoConsulta = dir_tipoConsulta;
    }

    /**
     *
     * @return
     */
    public List<SolicitudConsulta> getDirecciones() {
        return direcciones;
    }

    /**
     *
     * @param direcciones
     */
    public void setDirecciones(List<SolicitudConsulta> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     *
     * @return
     */
    public String getVacio() {
        return vacio;
    }

    /**
     *
     * @param vacio
     */
    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    /**
     *
     * @return
     */
    public String getDir_ingresadaTexto() {
        return dir_ingresadaTexto;
    }

    /**
     *
     * @param dir_ingresadaTexto
     */
    public void setDir_ingresadaTexto(String dir_ingresadaTexto) {
        this.dir_ingresadaTexto = dir_ingresadaTexto;
    }

    /**
     *
     * @return
     */
    public String getDirRta_actEconomica() {
        return dirRta_actEconomica;
    }

    /**
     *
     * @param dirRta_actEconomica
     */
    public void setDirRta_actEconomica(String dirRta_actEconomica) {
        this.dirRta_actEconomica = dirRta_actEconomica;
    }

    /**
     *
     * @return
     */
    public String getDirRta_alterna() {
        return dirRta_alterna;
    }

    /**
     *
     * @param dirRta_alterna
     */
    public void setDirRta_alterna(String dirRta_alterna) {
        this.dirRta_alterna = dirRta_alterna;
    }

    /**
     *
     * @return
     */
    public String getDirRta_barrio() {
        return dirRta_barrio;
    }

    /**
     *
     * @param dirRta_barrio
     */
    public void setDirRta_barrio(String dirRta_barrio) {
        this.dirRta_barrio = dirRta_barrio;
    }

    /**
     *
     * @return
     */
    public String getDirRta_calificador() {
        return dirRta_calificador;
    }

    /**
     *
     * @param dirRta_calificador
     */
    public void setDirRta_calificador(String dirRta_calificador) {
        this.dirRta_calificador = dirRta_calificador;
    }

    /**
     *
     * @return
     */
    public String getDirRta_estandar() {
        return dirRta_estandar;
    }

    /**
     *
     * @param dirRta_estandar
     */
    public void setDirRta_estandar(String dirRta_estandar) {
        this.dirRta_estandar = dirRta_estandar;
    }

    /**
     *
     * @return
     */
    public String getDirRta_estrato() {
        return dirRta_estrato;
    }

    /**
     *
     * @param dirRta_estrato
     */
    public void setDirRta_estrato(String dirRta_estrato) {
        this.dirRta_estrato = dirRta_estrato;
    }

    /**
     *
     * @return
     */
    public String getDirRta_existencia() {
        return dirRta_existencia;
    }

    /**
     *
     * @param dirRta_existencia
     */
    public void setDirRta_existencia(String dirRta_existencia) {
        this.dirRta_existencia = dirRta_existencia;
    }

    /**
     *
     * @return
     */
    public String getDirRta_manzana() {
        return dirRta_manzana;
    }

    /**
     *
     * @param dirRta_manzana
     */
    public void setDirRta_manzana(String dirRta_manzana) {
        this.dirRta_manzana = dirRta_manzana;
    }

    /**
     *
     * @return
     */
    public String getDirRta_nvlCalidad() {
        return dirRta_nvlCalidad;
    }

    /**
     *
     * @param dirRta_nvlCalidad
     */
    public void setDirRta_nvlCalidad(String dirRta_nvlCalidad) {
        this.dirRta_nvlCalidad = dirRta_nvlCalidad;
    }

    /**
     *
     * @return
     */
    public String getDirRta_recomendaciones() {
        return dirRta_recomendaciones;
    }

    /**
     *
     * @param dirRta_recomendaciones
     */
    public void setDirRta_recomendaciones(String dirRta_recomendaciones) {
        this.dirRta_recomendaciones = dirRta_recomendaciones;
    }

    /**
     *
     * @return
     */
    public String getDirRta_flags() {
        return dirRta_flags;
    }

    /**
     *
     * @param dirRta_flags
     */
    public void setDirRta_flags(String dirRta_flags) {
        this.dirRta_flags = dirRta_flags;
    }

    //Atributos de la direccion
    /**
     *
     * @return
     */
    public String getSeleccionar() {
        return seleccionar;
    }

    /**
     *
     * @param seleccionar
     */
    public void setSeleccionar(String seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     *
     * @return
     */
    public String getCardinalCalle() {
        return cardinalCalle;
    }

    /**
     *
     * @param cardinalCalle
     */
    public void setCardinalCalle(String cardinalCalle) {
        this.cardinalCalle = cardinalCalle;
    }

    /**
     *
     * @return
     */
    public String getCardinalPlaca() {
        return cardinalPlaca;
    }

    /**
     *
     * @param cardinalPlaca
     */
    public void setCardinalPlaca(String cardinalPlaca) {
        this.cardinalPlaca = cardinalPlaca;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> getCardinales() {
        return cardinales;
    }

    /**
     *
     * @param cardinales
     */
    public void setCardinales(List<Multivalor> cardinales) {
        this.cardinales = cardinales;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> getLetras() {
        return letras;
    }

    /**
     *
     * @param letras
     */
    public void setLetras(List<Multivalor> letras) {
        this.letras = letras;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListCardinales() {
        return listCardinales;
    }

    /**
     *
     * @param listCardinales
     */
    public void setListCardinales(List<SelectItem> listCardinales) {
        this.listCardinales = listCardinales;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListLetras() {
        return listLetras;
    }

    /**
     *
     * @param listLetras
     */
    public void setListLetras(List<SelectItem> listLetras) {
        this.listLetras = listLetras;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListLetrasyNumeros() {
        return listLetrasyNumeros;
    }

    /**
     *
     * @param listLetrasyNumeros
     */
    public void setListLetrasyNumeros(List<SelectItem> listLetrasyNumeros) {
        this.listLetrasyNumeros = listLetrasyNumeros;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListPrefijos() {
        return listPrefijos;
    }

    /**
     *
     * @param listPrefijos
     */
    public void setListPrefijos(List<SelectItem> listPrefijos) {
        this.listPrefijos = listPrefijos;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListTCalles() {
        return listTCalles;
    }

    /**
     *
     * @param listTCalles
     */
    public void setListTCalles(List<SelectItem> listTCalles) {
        this.listTCalles = listTCalles;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> getPrefijos() {
        return prefijos;
    }

    /**
     *
     * @param prefijos
     */
    public void setPrefijos(List<Multivalor> prefijos) {
        this.prefijos = prefijos;
    }

    /**
     *
     * @return
     */
    public String getSon_apto() {
        return son_apto;
    }

    /**
     *
     * @param son_apto
     */
    public void setSon_apto(String son_apto) {
        this.son_apto = son_apto;
    }

    /**
     *
     * @return
     */
    public String getSon_calle() {
        return son_calle;
    }

    /**
     *
     * @param son_calle
     */
    public void setSon_calle(String son_calle) {
        this.son_calle = son_calle;
    }

    /**
     *
     * @return
     */
    public String getSon_cardinalCalle() {
        return son_cardinalCalle;
    }

    /**
     *
     * @param son_cardinalCalle
     */
    public void setSon_cardinalCalle(String son_cardinalCalle) {
        this.son_cardinalCalle = son_cardinalCalle;
    }

    /**
     *
     * @return
     */
    public String getSon_cardinalPlaca() {
        return son_cardinalPlaca;
    }

    /**
     *
     * @param son_cardinalPlaca
     */
    public void setSon_cardinalPlaca(String son_cardinalPlaca) {
        this.son_cardinalPlaca = son_cardinalPlaca;
    }

    /**
     *
     * @return
     */
    public String getSon_complemento() {
        return son_complemento;
    }

    /**
     *
     * @param son_complemento
     */
    public void setSon_complemento(String son_complemento) {
        this.son_complemento = son_complemento;
    }

    /**
     *
     * @return
     */
    public String getSon_letraCalle() {
        return son_letraCalle;
    }

    /**
     *
     * @param son_letraCalle
     */
    public void setSon_letraCalle(String son_letraCalle) {
        this.son_letraCalle = son_letraCalle;
    }

    /**
     *
     * @return
     */
    public String getSon_letraCalles() {
        return son_letraCalles;
    }

    /**
     *
     * @param son_letraCalles
     */
    public void setSon_letraCalles(String son_letraCalles) {
        this.son_letraCalles = son_letraCalles;
    }

    /**
     *
     * @return
     */
    public String getSon_letraPlaca() {
        return son_letraPlaca;
    }

    /**
     *
     * @param son_letraPlaca
     */
    public void setSon_letraPlaca(String son_letraPlaca) {
        this.son_letraPlaca = son_letraPlaca;
    }

    /**
     *
     * @return
     */
    public String getSon_placa1() {
        return son_placa1;
    }

    /**
     *
     * @param son_placa1
     */
    public void setSon_placa1(String son_placa1) {
        this.son_placa1 = son_placa1;
    }

    /**
     *
     * @return
     */
    public String getSon_placa2() {
        return son_placa2;
    }

    /**
     *
     * @param son_placa2
     */
    public void setSon_placa2(String son_placa2) {
        this.son_placa2 = son_placa2;
    }

    /**
     *
     * @return
     */
    public String getSon_prefijoCalle() {
        return son_prefijoCalle;
    }

    /**
     *
     * @param son_prefijoCalle
     */
    public void setSon_prefijoCalle(String son_prefijoCalle) {
        this.son_prefijoCalle = son_prefijoCalle;
    }

    /**
     *
     * @return
     */
    public String getSon_prefijoPlaca() {
        return son_prefijoPlaca;
    }

    /**
     *
     * @param son_prefijoPlaca
     */
    public void setSon_prefijoPlaca(String son_prefijoPlaca) {
        this.son_prefijoPlaca = son_prefijoPlaca;
    }

    /**
     *
     * @return
     */
    public String getSon_tcalle() {
        return son_tcalle;
    }

    /**
     *
     * @param son_tcalle
     */
    public void setSon_tcalle(String son_tcalle) {
        this.son_tcalle = son_tcalle;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> gettCalles() {
        return tCalles;
    }

    /**
     *
     * @param tCalles
     */
    public void settCalles(List<Multivalor> tCalles) {
        this.tCalles = tCalles;
    }

    /**
     *
     * @return
     */
    public String getTipocalle() {
        return tipocalle;
    }

    /**
     *
     * @param tipocalle
     */
    public void setTipocalle(String tipocalle) {
        this.tipocalle = tipocalle;
    }

    /**
     *
     * @return
     */
    public String getSon_calle2() {
        return son_calle2;
    }

    /**
     *
     * @param son_calle2
     */
    public void setSon_calle2(String son_calle2) {
        this.son_calle2 = son_calle2;
    }

    /**
     *
     * @return
     */
    public String getSon_calle3() {
        return son_calle3;
    }

    /**
     *
     * @param son_calle3
     */
    public void setSon_calle3(String son_calle3) {
        this.son_calle3 = son_calle3;
    }

    /**
     *
     * @return
     */
    public String getSon_calle4() {
        return son_calle4;
    }

    /**
     *
     * @param son_calle4
     */
    public void setSon_calle4(String son_calle4) {
        this.son_calle4 = son_calle4;
    }

    /**
     *
     * @return
     */
    public String getSon_cardinalPlaca2() {
        return son_cardinalPlaca2;
    }

    /**
     *
     * @param son_cardinalPlaca2
     */
    public void setSon_cardinalPlaca2(String son_cardinalPlaca2) {
        this.son_cardinalPlaca2 = son_cardinalPlaca2;
    }

    /**
     *
     * @return
     */
    public String getSon_letraCalle2() {
        return son_letraCalle2;
    }

    /**
     *
     * @param son_letraCalle2
     */
    public void setSon_letraCalle2(String son_letraCalle2) {
        this.son_letraCalle2 = son_letraCalle2;
    }

    /**
     *
     * @return
     */
    public String getSon_letraCalle3() {
        return son_letraCalle3;
    }

    /**
     *
     * @param son_letraCalle3
     */
    public void setSon_letraCalle3(String son_letraCalle3) {
        this.son_letraCalle3 = son_letraCalle3;
    }

    /**
     *
     * @return
     */
    public String getSon_letraCalle4() {
        return son_letraCalle4;
    }

    /**
     *
     * @param son_letraCalle4
     */
    public void setSon_letraCalle4(String son_letraCalle4) {
        this.son_letraCalle4 = son_letraCalle4;
    }

    /**
     *
     * @return
     */
    public String getSon_tcalle2() {
        return son_tcalle2;
    }

    /**
     *
     * @param son_tcalle2
     */
    public void setSon_tcalle2(String son_tcalle2) {
        this.son_tcalle2 = son_tcalle2;
    }

    /**
     *
     * @return
     */
    public String getNombreLog() {
        return nombreLog;
    }

    /**
     *
     * @return
     */
    public String getDirRta_levelEconomic() {
        return dirRta_levelEconomic;
    }

    /**
     *
     * @param dirRta_levelEconomic
     */
    public void setDirRta_levelEconomic(String dirRta_levelEconomic) {
        this.dirRta_levelEconomic = dirRta_levelEconomic;
    }

    /**
     *
     * @return
     */
    public boolean isMostrarGuardar() {
        return mostrarGuardar;
    }

    /**
     *
     * @param mostrarGuardar
     */
    public void setMostrarGuardar(boolean mostrarGuardar) {
        this.mostrarGuardar = mostrarGuardar;
    }

    /**
     *
     * @return
     */
    public boolean isShowTipoBogota() {
        return showTipoBogota;
    }

    /**
     *
     * @param showTipoBogota
     */
    public void setShowTipoBogota(boolean showTipoBogota) {
        this.showTipoBogota = showTipoBogota;
    }

    /**
     *
     * @return
     */
    public boolean isShowTipoCali() {
        return showTipoCali;
    }

    /**
     *
     * @param showTipoCali
     */
    public void setShowTipoCali(boolean showTipoCali) {
        this.showTipoCali = showTipoCali;
    }

    /**
     *
     * @return
     */
    public boolean isShowTipoMedellin() {
        return showTipoMedellin;
    }

    /**
     *
     * @param showTipoMedellin
     */
    public void setShowTipoMedellin(boolean showTipoMedellin) {
        this.showTipoMedellin = showTipoMedellin;
    }

    /**
     *
     * @return
     */
    public List<AddressAggregated> getDireccionesAgregadas() {
        return direccionesAgregadas;
    }

    /**
     *
     * @param direccionesAgregadas
     */
    public void setDireccionesAgregadas(List<AddressAggregated> direccionesAgregadas) {
        this.direccionesAgregadas = direccionesAgregadas;
    }

    /**
     *
     * @return
     */
    public boolean isShowDireccionesAgregadas() {
        return showDireccionesAgregadas;
    }

    /**
     *
     * @param showDireccionesAgregadas
     */
    public void setShowDireccionesAgregadas(boolean showDireccionesAgregadas) {
        this.showDireccionesAgregadas = showDireccionesAgregadas;
    }

    /**
     *
     * @return
     */
    public boolean isShowDireccionesSugeridas() {
        return showDireccionesSugeridas;
    }

    /**
     *
     * @param showDireccionesSugeridas
     */
    public void setShowDireccionesSugeridas(boolean showDireccionesSugeridas) {
        this.showDireccionesSugeridas = showDireccionesSugeridas;
    }

    /**
     *
     * @return
     */
    public List<AddressSuggested> getDireccionesSugeridas() {
        return direccionesSugeridas;
    }

    /**
     *
     * @param direccionesSugeridas
     */
    public void setDireccionesSugeridas(List<AddressSuggested> direccionesSugeridas) {
        this.direccionesSugeridas = direccionesSugeridas;
    }

    /**
     *
     * @return
     */
    public String getDirRta_actEconomica_css() {
        return dirRta_actEconomica_css;
    }

    /**
     *
     * @param dirRta_actEconomica_css
     */
    public void setDirRta_actEconomica_css(String dirRta_actEconomica_css) {
        this.dirRta_actEconomica_css = dirRta_actEconomica_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_alterna_css() {
        return dirRta_alterna_css;
    }

    /**
     *
     * @param dirRta_alterna_css
     */
    public void setDirRta_alterna_css(String dirRta_alterna_css) {
        this.dirRta_alterna_css = dirRta_alterna_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_barrio_css() {
        return dirRta_barrio_css;
    }

    /**
     *
     * @param dirRta_barrio_css
     */
    public void setDirRta_barrio_css(String dirRta_barrio_css) {
        this.dirRta_barrio_css = dirRta_barrio_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_calificador_css() {
        return dirRta_calificador_css;
    }

    /**
     *
     * @param dirRta_calificador_css
     */
    public void setDirRta_calificador_css(String dirRta_calificador_css) {
        this.dirRta_calificador_css = dirRta_calificador_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_estandar_css() {
        return dirRta_estandar_css;
    }

    /**
     *
     * @param dirRta_estandar_css
     */
    public void setDirRta_estandar_css(String dirRta_estandar_css) {
        this.dirRta_estandar_css = dirRta_estandar_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_estrato_css() {
        return dirRta_estrato_css;
    }

    /**
     *
     * @param dirRta_estrato_css
     */
    public void setDirRta_estrato_css(String dirRta_estrato_css) {
        this.dirRta_estrato_css = dirRta_estrato_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_existencia_css() {
        return dirRta_existencia_css;
    }

    /**
     *
     * @param dirRta_existencia_css
     */
    public void setDirRta_existencia_css(String dirRta_existencia_css) {
        this.dirRta_existencia_css = dirRta_existencia_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_flags_css() {
        return dirRta_flags_css;
    }

    /**
     *
     * @param dirRta_flags_css
     */
    public void setDirRta_flags_css(String dirRta_flags_css) {
        this.dirRta_flags_css = dirRta_flags_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_fuente() {
        return dirRta_fuente;
    }

    /**
     *
     * @param dirRta_fuente
     */
    public void setDirRta_fuente(String dirRta_fuente) {
        this.dirRta_fuente = dirRta_fuente;
    }

    /**
     *
     * @return
     */
    public String getDirRta_fuente_css() {
        return dirRta_fuente_css;
    }

    /**
     *
     * @param dirRta_fuente_css
     */
    public void setDirRta_fuente_css(String dirRta_fuente_css) {
        this.dirRta_fuente_css = dirRta_fuente_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_levelEconomic_css() {
        return dirRta_levelEconomic_css;
    }

    /**
     *
     * @param dirRta_levelEconomic_css
     */
    public void setDirRta_levelEconomic_css(String dirRta_levelEconomic_css) {
        this.dirRta_levelEconomic_css = dirRta_levelEconomic_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_manzana_css() {
        return dirRta_manzana_css;
    }

    /**
     *
     * @param dirRta_manzana_css
     */
    public void setDirRta_manzana_css(String dirRta_manzana_css) {
        this.dirRta_manzana_css = dirRta_manzana_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_nvlCalidad_css() {
        return dirRta_nvlCalidad_css;
    }

    /**
     *
     * @param dirRta_nvlCalidad_css
     */
    public void setDirRta_nvlCalidad_css(String dirRta_nvlCalidad_css) {
        this.dirRta_nvlCalidad_css = dirRta_nvlCalidad_css;
    }

    /**
     *
     * @return
     */
    public String getDirRta_recomendaciones_css() {
        return dirRta_recomendaciones_css;
    }

    /**
     *
     * @param dirRta_recomendaciones_css
     */
    public void setDirRta_recomendaciones_css(String dirRta_recomendaciones_css) {
        this.dirRta_recomendaciones_css = dirRta_recomendaciones_css;
    }

    /**
     *
     * @return
     */
    public List<Privilegios> getPrivilegios() {
        return privilegios;
    }

    /**
     *
     * @param privilegios
     */
    public void setPrivilegios(List<Privilegios> privilegios) {
        this.privilegios = privilegios;
    }

    /**
     *
     * @return
     */
    public String getFlagConfirmar() {
        return flagConfirmar;
    }

    /**
     *
     * @param flagConfirmar
     */
    public void setFlagConfirmar(String flagConfirmar) {
        this.flagConfirmar = flagConfirmar;
    }

    /**
     *
     * @return
     */
    public String getShowFlagConfirmar() {
        return showFlagConfirmar;
    }

    /**
     *
     * @param showFlagConfirmar
     */
    public void setShowFlagConfirmar(String showFlagConfirmar) {
        this.showFlagConfirmar = showFlagConfirmar;
    }

    /**
     *
     * @return
     */
    public boolean isShowBotones() {
        return showBotones;
    }

    /**
     *
     * @param showBotones
     */
    public void setShowBotones(boolean showBotones) {
        this.showBotones = showBotones;
    }

    /**
     *
     * @return
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     *
     * @param mensajeUsuario
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     *
     * @return
     */
    public String getTipoDireccion() {
        return tipoDireccion;
    }

    /**
     *
     * @param tipoDireccion
     */
    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    /**
     *
     * @return
     */
    public boolean isShowPopUp() {
        return showPopUp;
    }

    /**
     *
     * @param showPopUp
     */
    public void setShowPopUp(boolean showPopUp) {
        this.showPopUp = showPopUp;
    }

    /**
     *
     * @return
     */
    public boolean isShowPanelConfirmar() {
        return showPanelConfirmar;
    }

    /**
     *
     * @param showPanelConfirmar
     */
    public void setShowPanelConfirmar(boolean showPanelConfirmar) {
        this.showPanelConfirmar = showPanelConfirmar;
    }

    /**
     *
     * @return
     */
    public boolean isShowPanelDecision() {
        return showPanelDecision;
    }

    /**
     *
     * @param showPanelDecision
     */
    public void setShowPanelDecision(boolean showPanelDecision) {
        this.showPanelDecision = showPanelDecision;
    }

    /**
     *
     * @return
     */
    public boolean isShowPanelRevisarEnCampo() {
        return showPanelRevisarEnCampo;
    }

    /**
     *
     * @param showPanelRevisarEnCampo
     */
    public void setShowPanelRevisarEnCampo(boolean showPanelRevisarEnCampo) {
        this.showPanelRevisarEnCampo = showPanelRevisarEnCampo;
    }

    /**
     *
     * @return
     */
    public boolean isShowBotonConfirmar() {
        return showBotonConfirmar;
    }

    /**
     *
     * @param showBotonConfirmar
     */
    public void setShowBotonConfirmar(boolean showBotonConfirmar) {
        this.showBotonConfirmar = showBotonConfirmar;
    }

    /**
     * @return the dirRta_nodoUno V 1.1
     */
    public String getDirRta_nodoUno() {
        return dirRta_nodoUno;
    }

    /**
     * @param dirRta_nodoUno the dirRta_nodoUno to set V 1.1
     */
    public void setDirRta_nodoUno(String dirRta_nodoUno) {
        this.dirRta_nodoUno = dirRta_nodoUno;
    }

    /**
     * @return the dirRta_nodoDos V 1.1
     */
    public String getDirRta_nodoDos() {
        return dirRta_nodoDos;
    }

    /**
     * @param dirRta_nodoDos the dirRta_nodoDos to set V 1.1
     */
    public void setDirRta_nodoDos(String dirRta_nodoDos) {
        this.dirRta_nodoDos = dirRta_nodoDos;
    }

    /**
     * @return the dirRta_nodoTres V 1.1
     */
    public String getDirRta_nodoTres() {
        return dirRta_nodoTres;
    }

    /**
     * @param dirRta_nodoTres the dirRta_nodoTres to set V 1.1
     */
    public void setDirRta_nodoTres(String dirRta_nodoTres) {
        this.dirRta_nodoTres = dirRta_nodoTres;
    }

    /**
     * @return the dirRta_nodoUno_css V 1.1
     */
    public String getDirRta_nodoUno_css() {
        return dirRta_nodoUno_css;
    }

    /**
     * @param dirRta_nodoUno_css the dirRta_nodoUno_css to set V 1.1
     */
    public void setDirRta_nodoUno_css(String dirRta_nodoUno_css) {
        this.dirRta_nodoUno_css = dirRta_nodoUno_css;
    }

    /**
     * @return the dirRta_nodoDos_css V 1.1
     */
    public String getDirRta_nodoDos_css() {
        return dirRta_nodoDos_css;
    }

    /**
     * @param dirRta_nodoDos_css the dirRta_nodoDos_css to set V 1.1
     */
    public void setDirRta_nodoDos_css(String dirRta_nodoDos_css) {
        this.dirRta_nodoDos_css = dirRta_nodoDos_css;
    }

    /**
     * @return the dirRta_nodoTres_css V 1.1
     */
    public String getDirRta_nodoTres_css() {
        return dirRta_nodoTres_css;
    }

    /**
     * @param dirRta_nodoTres_css the dirRta_nodoTres_css to set V 1.1
     */
    public void setDirRta_nodoTres_css(String dirRta_nodoTres_css) {
        this.dirRta_nodoTres_css = dirRta_nodoTres_css;
    }

    /**
     * @return the dirRta_estratoDef Modificacion Carlos Villamil Direcciones
     * Fase I 20121218 V 1.2
     */
    public String getDirRta_estratoDef() {
        return dirRta_estratoDef;
    }

    /**
     * @param dirRta_estratoDef the dirRta_estratoDef to set Modificacion Carlos
     * Villamil Direcciones Fase I 20121218 V 1.2
     */
    public void setDirRta_estratoDef(String dirRta_estratoDef) {
        this.dirRta_estratoDef = dirRta_estratoDef;
    }
}
