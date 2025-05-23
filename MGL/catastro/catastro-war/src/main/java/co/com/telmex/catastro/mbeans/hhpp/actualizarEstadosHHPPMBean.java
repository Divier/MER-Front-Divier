package co.com.telmex.catastro.mbeans.hhpp;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.HhppConsulta;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Privilegios;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.HhppDelegate;
import co.com.telmex.catastro.delegate.PrivilegiosRolDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase actualizarEstadosHHPPMBean
 * Extiende de BaseMBean
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "actualizarEstadosHHPPMBean")
public class actualizarEstadosHHPPMBean extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(actualizarEstadosHHPPMBean.class);
    //Mensajes de error por pantalla

    /**
     * 
     */
    public static final String ACTUALIZAR_ESTADOS_BEAN_NO_HHPP_POR_DIRECCION = "No se encontraron HHPP asociados a la dirección ingresada, por favor intentelo nuevamente.";
    /**
     * 
     */
    public static final String DIRECCION_ESTADO_INTRADUCIBLE = "C";
    /**
     * 
     */
    public static final String LA_DIRECCION_INTRODUCIDA_ES_INTRADUCIBLE = "La dirección introducida es intraducible.";
    /**
     * 
     */
    public static final String LA_DIRECCION_INTRODUCIDA_ES_CTA_MATRIZ = "La dirección ingresada es Cuenta Matriz, por lo tanto no se muestran registros asociados a esta dirección.";
    /**
     * 
     */
    public static final String MSJ_CONFIRMAR_ACTUALIZACION_HHPP = "Está seguro que desea actualizar el estado de este HHPP?";
    /**
     * 
     */
    public static final String OBLIGATORIO_ESTADO_FINAL = "Debe seleccionar un estado final.";
    /**
     * 
     */
    public static final String ERROR_HHPP_NO_ENCONTRADO = "No se encontraron todos los datos para el HHPP seleccionado.";
    /**
     * 
     */
    public static final String ERROR_HHPP_NULL = "Ocurrió un error al consultar los datos del HHPP";
    /**
     * 
     */
    public static final String ERROR_AL_CONSULTAR_LOS_HHPP_POR_LA_DIRECC = "ERROR: Al consultar los HHPP por la dirección ingresada.";
    /**
     * 
     */
    public static final String NOMBRE_FUNCIONALIDAD = "ACTUALIZAR ESTADO DE HHPP";
    /**
     * 
     */
    public String dir_pais;
    /**
     * 
     */
    public String dir_regional;
    /**
     * 
     */
    public String dir_ciudad;
    /**
     * 
     */
    public String dir_NoEstandar;
    /**
     * 
     */
    public String dir_barrio;
    /**
     * 
     */
    public String vacio = "";
    /**
     * 
     */
    public String seleccione = "-- Seleccione --";
    private List<GeograficoPolitico> paises;
    /**
     * 
     */
    public List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> regionales = null;
    /**
     * 
     */
    public List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> ciudades = null;
    /**
     * 
     */
    public List<SelectItem> listCiudades = null;
    //Variables de respuesta del WebService y del repositorio
    /**
     * 
     */
    public String dirRta_estandar;
    /**
     * 
     */
    public String dirRta_barrio;
    /**
     * 
     */
    public String dirRta_estrato;
    /**
     * 
     */
    public String dirRta_actEconomica;
    /**
     * 
     */
    public String dirRta_manzana;
    /**
     * 
     */
    public String dirRta_alterna;
    /**
     * 
     */
    public String dirRta_calificador;
    /**
     * 
     */
    public String dirRta_existencia;
    /**
     * 
     */
    public String dirRta_nvlCalidad;
    /**
     * 
     */
    public String dirRta_recomendaciones;
    /**
     * 
     */
    public String dirRta_flags;
    /**
     * 
     */
    public AddressService responseSrv;
    /**
     * 
     */
    public List<HhppConsulta> direcciones = new ArrayList<HhppConsulta>();
    //Atributos de la direccion parametrizada
    //Atributos de la direccion parametrizada
    /**
     * 
     */
    public String seleccionar = "";
    /**
     * 
     */
    public String son_tcalle = "";
    /**
     * 
     */
    public String son_calle = "";
    /**
     * 
     */
    public String son_letraCalle = "";
    /**
     * 
     */
    public String son_calle2 = "";
    /**
     * 
     */
    public String son_letraCalle2 = "";
    /**
     * 
     */
    public String son_prefijoCalle = "";
    /**
     * 
     */
    public String son_calle3 = "";
    /**
     * 
     */
    public String son_letraCalles = "";
    /**
     * 
     */
    public String son_cardinalCalle = "";
    //Atributos de la placa
    /**
     * 
     */
    public String son_tcalle2 = "";
    /**
     * 
     */
    public String son_placa1 = "";
    /**
     * 
     */
    public String son_letraPlaca = "";
    /**
     * 
     */
    public String son_calle4 = "";
    /**
     * 
     */
    public String son_letraCalle3 = "";
    /**
     * 
     */
    public String son_prefijoPlaca = "";
    /**
     * 
     */
    public String son_letraCalle4 = "";
    /**
     * 
     */
    public String son_cardinalPlaca = "";
    //Placa parte 2
    /**
     * 
     */
    public String son_placa2 = "";
    /**
     * 
     */
    public String son_cardinalPlaca2 = "";
    /**
     * 
     */
    public String tipocalle = "";
    /**
     * 
     */
    public String tipocalle2 = "";
    /**
     * 
     */
    public List<SelectItem> listTCalles = null;
    private List<Multivalor> tCalles = null;
    /**
     * 
     */
    public List<SelectItem> listLetras = null;
    private List<Multivalor> letras = null;
    /**
     * 
     */
    public List<SelectItem> listPrefijos = null;
    private List<Multivalor> prefijos = null;
    /**
     * 
     */
    public String cardinalCalle = "";
    /**
     * 
     */
    public List<SelectItem> listCardinales = null;
    private List<Multivalor> cardinales = null;
    /**
     * 
     */
    public String cardinalPlaca = "";
    /**
     * 
     */
    public String cardinalPlaca2 = "";
    /**
     * 
     */
    public String son_apto = "";
    /**
     * 
     */
    public String son_complemento = "";
    //Variables para consumir el WS
    private GeograficoPolitico city = null;
    private GeograficoPolitico dpto = null;
    private boolean showTipoBogota;
    private boolean showTipoMedellin;
    private boolean showTipoCali;
    private boolean showTablaResultado;
    private boolean showBotonActualizar = true;
    private boolean showBotonConfirmar = false;
    /**
     * 
     */
    public boolean showPopUp = false;
    //Mensaje a mostrar al usuario
    /**
     * 
     */
    public String mensajeUsuario = "";
    //Variable para determinar si se debe validar el barrio
    private boolean validarBarrio = false;
    //Lista de privilegios para esta funcionalidad
    List<Privilegios> privilegios = null;
    private String nombreLog;
    /**
     * 
     */
    public List<Hhpp> hhpps = null;
    //Variables del HHPP seleccionado
    /**
     * 
     */
    public String idHhpp = "";
    /**
     * 
     */
    public String dirHhpp = "";
    /**
     * 
     */
    public String estadoHhpp = "";
    /**
     * 
     */
    public String idEstadoinicial = "";
    /**
     * 
     */
    public Hhpp homePass = null;
    /**
     * 
     */
    public String hhpp_idEstadoInicial;
    /**
     * 
     */
    public String hhpp_estadoInicial;
    /**
     * 
     */
    public String hhpp_estadoFinal;
    /**
     * 
     */
    public List<SelectItem> listEstadosFinal = null;
    /**
     * 
     */
    public String hhpp_nodo;
    /**
     * 
     */
    public String hhpp_tipoConexion;
    /**
     * 
     */
    public String hhpp_tipoRed;
    /**
     * 
     */
    public String hhpp_subDireccionIgac;
    /**
     * 
     */
    public String hhpp_tipoHhpp;
    /**
     * 
     */
    public boolean disableBoton = false;
    /**
     * 
     */
    public boolean disableEstadoFinal = false;
    /**
     * 
     */
    public String message2;
    //Variables de estilos para la consulta de HHPP
    /**
     * 
     */
    public String idHhpp_css;
    /**
     * 
     */
    public String hhpp_nodo_css;
    /**
     * 
     */
    public String hhpp_tipoConexion_css;
    /**
     * 
     */
    public String dirHhpp_css;
    /**
     * 
     */
    public String hhpp_tipoRed_css;
    /**
     * 
     */
    public String hhpp_tipoHhpp_css;
    /**
     * 
     */
    public String estadoHhpp_css;
    private Hhpp hhppAuditoria = new Hhpp();
    private EstadoHhpp estadoHhppAuditoria = new EstadoHhpp();

    /**
     * 
     * @throws IOException
     */
    public actualizarEstadosHHPPMBean() throws IOException {
        super();
        limpiarFormularios();
        responseSrv = new AddressService();
        initLists();
        obtenerRestricciones();
        showTablaResultado = false;
    }

    /**
     * Determina si es una subdireccion o direccion pra realizar la busqueda en la tabla correspondiente
     * @param direccionEstandar
     * @param addressGeodata
     * @param dirCtamatriz
     * @return 
     */
    private boolean validarTipoDireccion(String direccionEstandar, AddressGeodata addressGeodata, boolean dirCtamatriz, boolean ok) {
        //Se consulta como subdirecciones
        if ((son_complemento != null && !son_complemento.isEmpty()) || (son_apto != null && !son_apto.isEmpty())) {

            //Se consulta como SubDireccion
            try {
                direcciones = HhppDelegate.queryHhppByIdSubDir(direccionEstandar);
                if (!(direcciones != null && !direcciones.isEmpty())) {
                    ok = false;
                    showTablaResultado = false;
                    message = actualizarEstadosHHPPMBean.ACTUALIZAR_ESTADOS_BEAN_NO_HHPP_POR_DIRECCION;
                }
            } catch (ApplicationException ex) {
                ok = false;
                showTablaResultado = false;
                message = ERROR_AL_CONSULTAR_LOS_HHPP_POR_LA_DIRECC;
                FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
            } catch (Exception e) {
                ok = false;
                showTablaResultado = false;
                message = ERROR_AL_CONSULTAR_LOS_HHPP_POR_LA_DIRECC;
                FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
            }
        } else {
            ok = consultarComoDireccion(addressGeodata, dirCtamatriz, direccionEstandar, ok);
        }
        return ok;
    }

    /**
     * Se realiza la consulta en la tabla dirección
     * @param addressGeodata
     * @param dirCtamatriz
     * @param direccionEstandar
     * @return 
     */
    private boolean consultarComoDireccion(AddressGeodata addressGeodata, boolean dirCtamatriz, String direccionEstandar, boolean ok) {
        //Si la direccion es cta matriz no se deben mostrar los resultados
        try {
            dirCtamatriz = HhppDelegate.queryCuentaMatrizOnDir(addressGeodata.getCodencont());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error: al consultar la direccion e ubicacion con codigo" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error: al consultar la direccion e ubicacion con codigo" + e.getMessage() , e, LOGGER);
        }

        if (dirCtamatriz) {
            message = LA_DIRECCION_INTRODUCIDA_ES_CTA_MATRIZ;
            ok = false;
            showTablaResultado = false;
        } else {
            //Se consulta como direccion
            try {
                direcciones = HhppDelegate.queryHhppByIdDir(direccionEstandar);
                if (!(direcciones != null && !direcciones.isEmpty())) {
                    ok = false;
                    showTablaResultado = false;
                    message = actualizarEstadosHHPPMBean.ACTUALIZAR_ESTADOS_BEAN_NO_HHPP_POR_DIRECCION;   
                }
            } catch (ApplicationException ex) {
                ok = false;
                showTablaResultado = false;
                message = ERROR_AL_CONSULTAR_LOS_HHPP_POR_LA_DIRECC;
                FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
            } catch (Exception e) {
                ok = false;
                showTablaResultado = false;
                message = ERROR_AL_CONSULTAR_LOS_HHPP_POR_LA_DIRECC;
                FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
            }
        }
        return ok;
    }

    /**
     * Consulta los HHPP de acuerdo a la direccion ingresada
     * @return 
     */
    public boolean consultarHHPP() {
        boolean ok = true;

        AddressGeodata addressGeodata = new AddressGeodata();
        //creacion de la direccion no estandar
        createDirNoStandar();

        //Consultar el WebService para obtener los datos de la direccion ingresada
        AddressRequest requestSrv = new AddressRequest();

        //Obtenemos la region 
        dpto = loadRegional();
        requestSrv.setState(dpto.getGpoNombre());

        //Obtenemos la ciudad
        city = loadCity();
        requestSrv.setCity(city.getGpoNombre());

        if (!"".equals(dir_barrio) && dir_barrio != null) {
            requestSrv.setNeighborhood(dir_barrio);
        }

        //Se debe definir con cual direccion voy a consumir el servicio
        if (!dir_NoEstandar.equals("") && dir_NoEstandar.trim().length() > 0) {
            requestSrv.setAddress(dir_NoEstandar);
        }

        try {
            addressGeodata = GeoreferenciaDelegate.queryAddressGeodata(requestSrv);
        } catch (ApplicationException ex) {
            ok = false;
            showTablaResultado = false;
            FacesUtil.mostrarMensajeError("Error: al invocar al metodo queryAddressGeodata()" + ex.getMessage() , ex, LOGGER);
        } catch (Exception e) {
            ok = false;
            showTablaResultado = false;
            FacesUtil.mostrarMensajeError("Error: al invocar al metodo queryAddressGeodata()" + e.getMessage() , e, LOGGER);
        }

        String direccionEstandar = null;
        if (addressGeodata != null) {
            boolean dirCtamatriz = false;
            if (DIRECCION_ESTADO_INTRADUCIBLE.equals(addressGeodata.getEstado())) {
                message = LA_DIRECCION_INTRODUCIDA_ES_INTRADUCIBLE;
                ok = false;
                showTablaResultado = false;
            } else {
                direccionEstandar = addressGeodata.getDirtrad();
                ok = validarTipoDireccion(direccionEstandar, addressGeodata, dirCtamatriz, ok);
            }
        } else {
            message = Constant.GEODATA_FUERA_DE_SERVICIO;
            ok = false;
            showTablaResultado = false;
        }

        return ok;
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
     * ActionListener ejecutado al solicitar la validacion de la direccion
     * @param ev 
     */
    public void doValidar(ActionEvent ev) {
        message = "";
        //Se validan los campos obligatorios
        message = validarCamposObligatorios(validarBarrio);
        limpiarPantallaActualizar();
        if (message.isEmpty()) {
            //Se procede a consultar la direccion ingresada
            if (consultarHHPP()) {
                //Se muestra la tabla de resultado
                showTablaResultado = true;
            }
        }
    }

    /**
     * ActionListener que captura el id del HHPP seleccionado
     * @param ev
     * @throws ApplicationException  
     */
    public void onSeleccionar(ActionEvent ev) throws ApplicationException {
        this.idHhpp = ((BigDecimal) (((UIParameter) ev.getComponent().findComponent("idHhpp")).getValue())).toString();
        this.dirHhpp = (String) (((UIParameter) ev.getComponent().findComponent("dirHhpp")).getValue());
        this.estadoHhpp = (String) (((UIParameter) ev.getComponent().findComponent("estadoHhpp")).getValue());
        this.idEstadoinicial = (String) (((UIParameter) ev.getComponent().findComponent("idEstadoinicial")).getValue());
        estadoHhppAuditoria.setEhhId(idEstadoinicial);
        estadoHhppAuditoria.setEhhNombre(estadoHhpp);
        hhppAuditoria.setEstadoHhpp(estadoHhppAuditoria);
        boolean hayError = false;
        disableBoton = Boolean.FALSE;
        disableEstadoFinal = false;
        hhpp_estadoFinal = "";
        message2 = "";
        showBotonActualizar = true;
        showBotonConfirmar = false;
        limpiarFormularios();
        showTablaResultado = false;
        //Se consulta el Hhpp del repositorio por el idSeleccionado para cargar todos sus atributos.
        try {
            homePass = HhppDelegate.queryHhppById(idHhpp);
        } catch (ApplicationException ex) {
            hayError = true;
            message = actualizarEstadosHHPPMBean.ERROR_HHPP_NULL;
            FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
        } catch (Exception e) {
            hayError = true;
            message = actualizarEstadosHHPPMBean.ERROR_HHPP_NULL;
            FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
        }

        if (homePass == null) {
            hayError = true;
            message = actualizarEstadosHHPPMBean.ERROR_HHPP_NO_ENCONTRADO;
        }

        if (!hayError) {
            hhppAuditoria = homePass;
            llenarCamposForm();
        } else {
            message2 = message;
            //Consultar los estados posibles para desplegarlos en pantalla teniendo el id del estado actual
            cargarEstadosFinales(idEstadoinicial);
        }
    }

    /**
     * 
     */
    public void modificar() {
        message2 = "";

        //En este momento hhpp_estadoFinal tiene el id del estado seleccionado
        if (null == hhpp_estadoFinal || "0".equals(hhpp_estadoFinal)) {
            message2 = actualizarEstadosHHPPMBean.OBLIGATORIO_ESTADO_FINAL;
        } else {
            showBotonConfirmar = true;
            showPopUp = true;
        }
    }

    /**
     * Actualiza el estado del HHPP 
     */
    public void actualizarEstadoConfirmar() {
        boolean hayError = false;
        showPopUp = false;
        try {

            hhppAuditoria.setHhppId(new BigDecimal(idHhpp));

            message2 = actualizarEstado(hhpp_estadoFinal, this.user.getUsuNombre(), hhppAuditoria);
            disableBoton = Boolean.TRUE;

            disableEstadoFinal = true;
            showBotonActualizar = false;
            showTablaResultado = false;

            limpiarFormularios();
            limpiarCamposTipoDireccion();
            limpiarPantallaActualizar();
            hayError = true;

        } catch (Exception ex) {
            message2 = "ERROR: Al tratar de actualizar el HHPP con id: " + idHhpp;
            FacesUtil.mostrarMensajeError(message2 + ex.getMessage() , ex, LOGGER);
        }
        if (!hayError) {
            showBotonActualizar = false;
        }
    }

    private String actualizarEstado(String idEstadoFinal, String usuario, Hhpp hhpp) throws ApplicationException {
        String msj = "";
        msj = HhppDelegate.updateEstadoHhpp(idEstadoFinal, this.user.getUsuNombre(), hhpp, NOMBRE_FUNCIONALIDAD);
        return msj;
    }

    /**
     * Evento ejecutado al cancelar la operacion de actualizar el estado
     * @param ev 
     */
    public void doCancelar(ActionEvent ev) {
        //Cancela la operacion
        showBotonConfirmar = false;
    }

    /**
     * Evento ejecutado al confirmar que desea actualizar el estado
     * 
     */
    public void doActualizarEstado() {
        //En este momento hhpp_estadoFinal tiene el id del estado seleccionado
        //Se procede a actualizar el estado
        actualizarEstadoConfirmar();
    }

    /**
     * 
     */
    public void doValidarObligatorios() {
        //En este momento hhpp_estadoFinal tiene el id del estado seleccionado
        if (null == hhpp_estadoFinal || "".equals(hhpp_estadoFinal)) {
            message2 = actualizarEstadosHHPPMBean.OBLIGATORIO_ESTADO_FINAL;
            showBotonConfirmar = false;
        } else {
            showBotonConfirmar = true;
        }
    }

    /**
     * 
     * @param ev
     */
    public void doActualizarHhppConfirmar(ActionEvent ev) {
        limpiarFormularios();
        limpiarCamposTipoDireccion();
        showTablaResultado = false;
        //En este momento hhpp_estadoFinal tiene el id del estado seleccionado
        if (null == hhpp_estadoFinal || "".equals(hhpp_estadoFinal)) {
            message2 = actualizarEstadosHHPPMBean.OBLIGATORIO_ESTADO_FINAL;
            showBotonConfirmar = false;
        } else {
            message2 = "";
            doActualizarEstado();
            showBotonConfirmar = true;
            mensajeUsuario = MSJ_CONFIRMAR_ACTUALIZACION_HHPP;
        }
    }

    /**
     * Llena los campos del formulario con la respuesta del query de HHPP
     */
    private void llenarCamposForm() throws ApplicationException {
        if (homePass != null && !"".equals(estadoHhpp)) {
            hhpp_idEstadoInicial = homePass.getEstadoHhpp().getEhhId().toString();
            hhpp_estadoInicial = estadoHhpp;
            hhpp_nodo = homePass.getNodo().getNodNombre();
            hhpp_tipoConexion = homePass.getTipoConexionHhpp().getThcCodigo();
            hhpp_tipoRed = homePass.getTipoRedHhpp().getThrCodigo();
            hhpp_tipoHhpp = homePass.getTipoHhpp().getThhValor();

            //Consultar los estados posibles para desplegarlos en pantalla teniendo el id del estado actual
            cargarEstadosFinales(homePass.getEstadoHhpp().getEhhId().toString());
        }
    }

    /**
     * Llena la lista de los estados finales posibles del hhpp seleccionado
     * @param estadoInicial 
     */
    private void llenarEstadosFinales(String estadoInicial) {
        listEstadosFinal = new ArrayList<SelectItem>();
        SelectItem item1 = new SelectItem();
        item1.setLabel("P");
        item1.setValue("P");
        listEstadosFinal.add(item1);
        SelectItem item2 = new SelectItem();
        item2.setLabel("N");
        item2.setValue("N");
        listEstadosFinal.add(item2);

    }

    /**
     * Action que me lleva a la pagina de resultado
     * @return
     * @throws ApplicationException 
     */
    public String onIrAccion() throws ApplicationException {
        return "modificarHHPP";
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
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
            FacesUtil.mostrarMensajeError("Error en update Regionales" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al update Regionales. " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * 
     * @param event
     */
    public void validaEstadoFinal(ValueChangeEvent event) {
        message2 = "";
        message = "";
        String value = event.getNewValue().toString();
        if (value != null) {
            hhpp_estadoFinal = value;
            if (null == hhpp_estadoFinal || "0".equals(hhpp_estadoFinal)) {
                showBotonActualizar = false;
                message2 = actualizarEstadosHHPPMBean.OBLIGATORIO_ESTADO_FINAL;
            } else {
                showBotonActualizar = true;
            }
        }
        limpiarFormularios();
        limpiarCamposTipoDireccion();
    }

    /**
     * Actualiza la lista de ciudades de acuerdo a la region seleccionada
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
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en update Ciudades" + ex.getMessage() , ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al update Ciudades. " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Actualiza el tipod e direccion de acuerdo a la ciudad seleccionada
     * @param event
     * @throws ApplicationException 
     */
    public void updateTipoDireccion(ValueChangeEvent event) throws ApplicationException {
        direcciones = new ArrayList<HhppConsulta>();
        showTablaResultado = false;
        limpiarFormularios();
        String codCiudad = event.getNewValue().toString();
        String tipoCiudad = null;
        try {
            tipoCiudad = HhppDelegate.queryTipoCiudadByID(codCiudad);

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
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en queryTipoCiudadByID" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en queryTipoCiudadByID" + e.getMessage() , e, LOGGER);
        }

        limpiarCamposTipoDireccion();
        validarBarrio = false;
        try {
            validarBarrio = HhppDelegate.queryCiudadMultiorigen(codCiudad);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al momento de actualizar el tipo de dirección, determinando si es multiorigen. EX000: " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de actualizar el tipo de dirección, determinando si es multiorigen. EX000: " + e.getMessage() , e, LOGGER);
        }
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
            FacesUtil.mostrarMensajeError("Error al cargar Paises. " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar Paises. " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Valida los campos obligatorios del formulario
     * @return 
     */
    private String validarCamposObligatorios(boolean validarBarrio) {
        message = "";

        if ("".equals(son_tcalle) || "".equals(son_calle)) {
            message = Constant.OBLIGATORIO_DIRECCION;
        }
        if ("0".equals(dir_pais) || dir_pais == null) {
            message = Constant.OBLIGATORIO_PAIS;
        } else if ("0".equals(dir_regional) || dir_regional == null) {
            message = Constant.OBLIGATORIO_REGION;
        } else if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = Constant.OBLIGATORIO_CIUDAD;
        } else if (validarBarrio) {
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
        if ((loadCardinalidad(son_cardinalCalle)) != null) {
            cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
        }
        if ((loadCardinalidad(son_cardinalPlaca)) != null) {
            cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
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
        if (son_letraCalles.equalsIgnoreCase("null") || son_letraCalles.equalsIgnoreCase("0")) {
            son_letraCalles = "";
        }
        if (son_letraPlaca.equalsIgnoreCase("null") || son_letraPlaca.equalsIgnoreCase("0")) {
            son_letraPlaca = "";
        }
        if (!son_apto.isEmpty()) {
            son_apto = "AP " + son_apto;
        }

        dir_NoEstandar = tipocalle + " " + son_calle + " " + son_letraCalle + " " + son_prefijoCalle + " " + son_letraCalles + " " + cardinalCalle
                + " " + son_placa1 + " " + son_letraPlaca + " " + son_prefijoPlaca + " " + cardinalPlaca + " " + son_placa2 + " "
                + son_complemento + " " + son_apto;
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
        paises = new ArrayList<GeograficoPolitico>();
        listPaises = new ArrayList<SelectItem>();
        listTCalles = new ArrayList<SelectItem>();
        listLetras = new ArrayList<SelectItem>();
        listPrefijos = new ArrayList<SelectItem>();
        listCardinales = new ArrayList<SelectItem>();
        try {
            paises = SolicitudNegocioDelegate.queryPaises();
            tCalles = SolicitudNegocioDelegate.queryCalles();
            letras = SolicitudNegocioDelegate.queryLetras();
            prefijos = SolicitudNegocioDelegate.queryPrefijos();
            cardinales = SolicitudNegocioDelegate.queryCardinales();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
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
            FacesUtil.mostrarMensajeError("Error en las listas necesarias para crear la solicitud de Negocio. " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en las listas necesarias para crear la solicitud de Negocio. " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * 
     * @param idHhpp 
     */
    private void cargarEstadosFinales(String idHhpp) {
        //Consultar los estados posibles para desplegarlos en pantalla teniendo el id del estado actual
        try {
            listEstadosFinal = HhppDelegate.queryEstadosFinales(idHhpp);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al cargar los estados finales para el HHPP con id: {0}: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar los estados finales para el HHPP con id: {0}: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Restablece los campos del form
     */
    private void limpiarFormularios() {
        dir_pais = "";
        dir_regional = "";
        dir_ciudad = "";
        dir_barrio = "";
        dir_NoEstandar = null;
        dirRta_estandar = null;
        dirRta_barrio = "";
        dirRta_estrato = "";
        dirRta_actEconomica = "";
        dirRta_manzana = "";
        dirRta_alterna = "";
        dirRta_calificador = "";
        dirRta_existencia = "";
        dirRta_nvlCalidad = "";
        dirRta_recomendaciones = "";
        son_calle = "";
        son_tcalle = "";
        tipocalle = "";
        son_letraCalle = "";
        son_letraCalles = "";
        son_prefijoCalle = "";
        son_cardinalCalle = "";
        cardinalCalle = "";
        son_placa1 = "";
        son_letraPlaca = "";
        son_placa2 = "";
        son_prefijoPlaca = "";
        son_cardinalPlaca = "";
        cardinalPlaca = "";
        son_apto = "";
        son_complemento = "";
        direcciones = new ArrayList<HhppConsulta>();
        message = "";
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
     * 
     */
    private void limpiarPantallaActualizar() {
        hhpp_estadoFinal = "";
    }

    /**
     * Obtiene la lista de los campos a los cuales se les aplica una restriccion, la cual esta configurada
     * en la base de datos discriminada por funcionalidad y atributo 
     */
    private void obtenerRestricciones() {
        try {
            privilegios = PrivilegiosRolDelegate.queryPrivilegiosRol(this.rol.getRolId(), NOMBRE_FUNCIONALIDAD);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtener Restricciones. " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtener Restricciones. " + e.getMessage() , e, LOGGER);
        }

        for (Privilegios privilegio : privilegios) {
            String estiloCss = privilegio.getAtrNombre() + "_css";
            if (estiloCss.equals("idHhpp_css")) {
                idHhpp_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("hhpp_nodo_css")) {
                hhpp_nodo_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("hhpp_tipoConexion_css")) {
                hhpp_tipoConexion_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("dirHhpp_css")) {
                dirHhpp_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("hhpp_tipoRed_css")) {
                hhpp_tipoRed_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("hhpp_tipoHhpp_css")) {
                hhpp_tipoHhpp_css = privilegio.getAtrEstilo();
            } else if (estiloCss.equals("estadoHhpp_css")) {
                estadoHhpp_css = privilegio.getAtrEstilo();
            }

        }
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
    public List<HhppConsulta> getDirecciones() {
        return direcciones;
    }

    /**
     * 
     * @param direcciones
     */
    public void setDirecciones(List<HhppConsulta> direcciones) {
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
    public List<Hhpp> getHhpps() {
        return hhpps;
    }

    /**
     * 
     * @param hhpps
     */
    public void setHhpps(List<Hhpp> hhpps) {
        this.hhpps = hhpps;
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
    public String getDirHhpp() {
        return dirHhpp;
    }

    /**
     * 
     * @param dirHhpp
     */
    public void setDirHhpp(String dirHhpp) {
        this.dirHhpp = dirHhpp;
    }

    /**
     * 
     * @return
     */
    public String getIdHhpp() {
        return idHhpp;
    }

    /**
     * 
     * @param idHhpp
     */
    public void setIdHhpp(String idHhpp) {
        this.idHhpp = idHhpp;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_estadoInicial() {
        return hhpp_estadoInicial;
    }

    /**
     * 
     * @param hhpp_estadoInicial
     */
    public void setHhpp_estadoInicial(String hhpp_estadoInicial) {
        this.hhpp_estadoInicial = hhpp_estadoInicial;
    }

    /**
     * 
     * @return
     */
    public Hhpp getHomePass() {
        return homePass;
    }

    /**
     * 
     * @param homePass
     */
    public void setHomePass(Hhpp homePass) {
        this.homePass = homePass;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_nodo() {
        return hhpp_nodo;
    }

    /**
     * 
     * @param hhpp_nodo
     */
    public void setHhpp_nodo(String hhpp_nodo) {
        this.hhpp_nodo = hhpp_nodo;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_subDireccionIgac() {
        return hhpp_subDireccionIgac;
    }

    /**
     * 
     * @param hhpp_subDireccionIgac
     */
    public void setHhpp_subDireccionIgac(String hhpp_subDireccionIgac) {
        this.hhpp_subDireccionIgac = hhpp_subDireccionIgac;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoConexion() {
        return hhpp_tipoConexion;
    }

    /**
     * 
     * @param hhpp_tipoConexion
     */
    public void setHhpp_tipoConexion(String hhpp_tipoConexion) {
        this.hhpp_tipoConexion = hhpp_tipoConexion;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoHhpp() {
        return hhpp_tipoHhpp;
    }

    /**
     * 
     * @param hhpp_tipoHhpp
     */
    public void setHhpp_tipoHhpp(String hhpp_tipoHhpp) {
        this.hhpp_tipoHhpp = hhpp_tipoHhpp;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoRed() {
        return hhpp_tipoRed;
    }

    /**
     * 
     * @param hhpp_tipoRed
     */
    public void setHhpp_tipoRed(String hhpp_tipoRed) {
        this.hhpp_tipoRed = hhpp_tipoRed;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_estadoFinal() {
        return hhpp_estadoFinal;
    }

    /**
     * 
     * @param hhpp_estadoFinal
     */
    public void setHhpp_estadoFinal(String hhpp_estadoFinal) {
        this.hhpp_estadoFinal = hhpp_estadoFinal;
    }

    /**
     * 
     * @return
     */
    public List<SelectItem> getListEstadosFinal() {
        return listEstadosFinal;
    }

    /**
     * 
     * @param listEstadosFinal
     */
    public void setListEstadosFinal(List<SelectItem> listEstadosFinal) {
        this.listEstadosFinal = listEstadosFinal;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_idEstadoInicial() {
        return hhpp_idEstadoInicial;
    }

    /**
     * 
     * @param hhpp_idEstadoInicial
     */
    public void setHhpp_idEstadoInicial(String hhpp_idEstadoInicial) {
        this.hhpp_idEstadoInicial = hhpp_idEstadoInicial;
    }

    /**
     * 
     * @return
     */
    public String getMessage2() {
        return message2;
    }

    /**
     * 
     * @param message2
     */
    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    /**
     * 
     * @return
     */
    public String getEstadoHhpp() {
        return estadoHhpp;
    }

    /**
     * 
     * @param estadoHhpp
     */
    public void setEstadoHhpp(String estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    /**
     * 
     * @return
     */
    public String getIdEstadoinicial() {
        return idEstadoinicial;
    }

    /**
     * 
     * @param idEstadoinicial
     */
    public void setIdEstadoinicial(String idEstadoinicial) {
        this.idEstadoinicial = idEstadoinicial;
    }

    /**
     * 
     * @return
     */
    public boolean isDisableBoton() {
        return disableBoton;
    }

    /**
     * 
     * @param disablerBoton
     */
    public void setDisableBoton(boolean disablerBoton) {
        this.disableBoton = disablerBoton;
    }

    /**
     * 
     * @return
     */
    public String getCardinalPlaca2() {
        return cardinalPlaca2;
    }

    /**
     * 
     * @param cardinalPlaca2
     */
    public void setCardinalPlaca2(String cardinalPlaca2) {
        this.cardinalPlaca2 = cardinalPlaca2;
    }

    /**
     * 
     * @return
     */
    public GeograficoPolitico getCity() {
        return city;
    }

    /**
     * 
     * @param city
     */
    public void setCity(GeograficoPolitico city) {
        this.city = city;
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
    public AddressService getResponseSrv() {
        return responseSrv;
    }

    /**
     * 
     * @param responseSrv
     */
    public void setResponseSrv(AddressService responseSrv) {
        this.responseSrv = responseSrv;
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
    public String getTipocalle2() {
        return tipocalle2;
    }

    /**
     * 
     * @param tipocalle2
     */
    public void setTipocalle2(String tipocalle2) {
        this.tipocalle2 = tipocalle2;
    }

    /**
     * 
     * @return
     */
    public boolean isValidarBarrio() {
        return validarBarrio;
    }

    /**
     * 
     * @param validarBarrio
     */
    public void setValidarBarrio(boolean validarBarrio) {
        this.validarBarrio = validarBarrio;
    }

    /**
     * 
     * @return
     */
    public boolean isShowTablaResultado() {
        return showTablaResultado;
    }

    /**
     * 
     * @param showTablaResultado
     */
    public void setShowTablaResultado(boolean showTablaResultado) {
        this.showTablaResultado = showTablaResultado;
    }

    /**
     * 
     * @return
     */
    public boolean isShowBotonActualizar() {
        return showBotonActualizar;
    }

    /**
     * 
     * @param showBotonActualizar
     */
    public void setShowBotonActualizar(boolean showBotonActualizar) {
        this.showBotonActualizar = showBotonActualizar;
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
     * 
     * @return
     */
    public String getDirHhpp_css() {
        return dirHhpp_css;
    }

    /**
     * 
     * @param dirHhpp_css
     */
    public void setDirHhpp_css(String dirHhpp_css) {
        this.dirHhpp_css = dirHhpp_css;
    }

    /**
     * 
     * @return
     */
    public String getEstadoHhpp_css() {
        return estadoHhpp_css;
    }

    /**
     * 
     * @param estadoHhpp_css
     */
    public void setEstadoHhpp_css(String estadoHhpp_css) {
        this.estadoHhpp_css = estadoHhpp_css;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_nodo_css() {
        return hhpp_nodo_css;
    }

    /**
     * 
     * @param hhpp_nodo_css
     */
    public void setHhpp_nodo_css(String hhpp_nodo_css) {
        this.hhpp_nodo_css = hhpp_nodo_css;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoConexion_css() {
        return hhpp_tipoConexion_css;
    }

    /**
     * 
     * @param hhpp_tipoConexion_css
     */
    public void setHhpp_tipoConexion_css(String hhpp_tipoConexion_css) {
        this.hhpp_tipoConexion_css = hhpp_tipoConexion_css;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoHhpp_css() {
        return hhpp_tipoHhpp_css;
    }

    /**
     * 
     * @param hhpp_tipoHhpp_css
     */
    public void setHhpp_tipoHhpp_css(String hhpp_tipoHhpp_css) {
        this.hhpp_tipoHhpp_css = hhpp_tipoHhpp_css;
    }

    /**
     * 
     * @return
     */
    public String getHhpp_tipoRed_css() {
        return hhpp_tipoRed_css;
    }

    /**
     * 
     * @param hhpp_tipoRed_css
     */
    public void setHhpp_tipoRed_css(String hhpp_tipoRed_css) {
        this.hhpp_tipoRed_css = hhpp_tipoRed_css;
    }

    /**
     * 
     * @return
     */
    public String getIdHhpp_css() {
        return idHhpp_css;
    }

    /**
     * 
     * @param idHhpp_css
     */
    public void setIdHhpp_css(String idHhpp_css) {
        this.idHhpp_css = idHhpp_css;
    }

    /**
     * 
     * @return
     */
    public String getSeleccione() {
        return seleccione;
    }

    /**
     * 
     * @return
     */
    public boolean isDisableEstadoFinal() {
        return disableEstadoFinal;
    }

    /**
     * 
     * @param disableEstadoFinal
     */
    public void setDisableEstadoFinal(boolean disableEstadoFinal) {
        this.disableEstadoFinal = disableEstadoFinal;
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
}
