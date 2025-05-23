package co.com.telmex.catastro.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.CargueGeografico;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import co.com.telmex.catastro.delegate.CargueGeograficoDelegate;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase CargueGeograficoMBean Extiende de BaseMBean
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ManagedBean(name = "cargueGeograficoMBean")
public class CargueGeograficoMBean extends BaseMBean {

    /**
     *
     */
    public String son_tipoSolicitud = "";
    /**
     *
     */
    public List<SelectItem> lstTSolicitud = null;
    private List<Multivalor> tSolicitudes = null;
    /**
     *
     */
    public boolean showDetail = false;
    /**
     *
     */
    public boolean showCuentaMatriz = false;
    /**
     *
     */
    public String son_cuenta_matriz = "";
    /**
     *
     */
    public String son_pais = "";
    /**
     *
     */
    public List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> paises;
    /**
     *
     */
    public boolean disableRegionales = false;
    /**
     *
     */
    public String son_regional = "";
    /**
     *
     */
    public List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> regionales = null;
    /**
     *
     */
    public String son_ciudad = "";
    /**
     *
     */
    public String son_barrio = "";
    /**
     *
     */
    public List<SelectItem> listCiudades = null;
    private List<GeograficoPolitico> ciudades = null;
    /**
     *
     */
    public String son_calle = "";
    /**
     *
     */
    public String son_tcalle = "";
    /**
     *
     */
    public String tipocalle = "";
    /**
     *
     */
    public List<SelectItem> listTCalles = null;
    private List<Multivalor> tCalles = null;
    /**
     *
     */
    public String son_letraCalle = "";
    /**
     *
     */
    public List<SelectItem> listLetras = null;
    private List<Multivalor> letras = null;
    /**
     *
     */
    public String son_prefijoCalle = "";
    /**
     *
     */
    public List<SelectItem> listPrefijos = null;
    private List<Multivalor> prefijos = null;
    /**
     *
     */
    public String son_cardinalCalle = "";
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
    public String son_placa1 = "";
    /**
     *
     */
    public String son_letraPlaca = "";
    /**
     *
     */
    public String son_placa2 = "";
    /**
     *
     */
    public String son_prefijoPlaca = "";
    /**
     *
     */
    public String son_cardinalPlaca = "";
    /**
     *
     */
    public String cardinalPlaca = "";
    /**
     *
     */
    public String son_apto = "";
    /**
     *
     */
    public String son_complemento = "";
    /**
     *
     */
    public String nombre_nodo = "";
    /**
     *
     */
    public String son_tipo_hhpp = "";
    /**
     *
     */
    public List<SelectItem> listThhpp = null;
    private List<TipoHhpp> tiposhhpp = null;
    /**
     *
     */
    public String son_tipo_red = "";
    /**
     *
     */
    public List<SelectItem> listTred = null;
    private List<TipoHhppRed> tiposred = null;
    /**
     *
     */
    public String son_tipo_conexion = "";
    /**
     *
     */
    public List<SelectItem> listTconexion = null;
    private List<TipoHhppConexion> tiposconexion = null;
    /**
     *
     */
    public String son_tipo_blackl = "";
    /**
     *
     */
    public List<SelectItem> listTblackL = null;
    private List<TipoMarcas> tiposBlackL = null;
    /**
     *
     */
    public String son_motivo = "";
    /**
     *
     */
    public String cod_solicitante = "";
    /**
     *
     */
    public String nom_solicitante = "";
    /**
     *
     */
    public String email_solicitante = "ejemplo@ejemplo.com";
    /**
     *
     */
    public String cel_solicitante = "";
    /**
     *
     */
    public String son_contacto = "";
    /**
     *
     */
    public String son_tel_contacto = "";
    /**
     *
     */
    public String son_estado = "";
    /**
     *
     */
    public String observaciones = "";
    /**
     *
     */
    public boolean disableCiudades = true;
    /**
     *
     */
    public String seleccionar = "";
    /**
     *
     */
    public boolean activarSolicitante = false;
    private GeograficoPolitico city = null;
    private Geografico neighborhood = null;
    private TipoHhpp thhpp = null;
    private TipoMarcas tipoMarcas;
    private TipoHhppRed tipoHhppRed;
    private TipoHhppConexion tipoHhppConexion;
    private Nodo nodo = null;
    private AddressRequest address = null;
    private AddressService addressResult = null;
    private AddressGeodata addressGeodata = null;
    private String direccionNostandar = "";
    private BigDecimal idSon = null;
    private CargueGeografico solicitud = null;
    /**
     *
     */
    public SelectItem[] opciones;

    private static final Logger LOGGER = LogManager.getLogger(CargueGeograficoMBean.class);

    /**
     *
     */
    public CargueGeograficoMBean() {
        super();
        lstTSolicitud = new ArrayList<SelectItem>();

        try {
            tSolicitudes = CargueGeograficoDelegate.loadTiposSolicitud();
            for (Multivalor tsol : tSolicitudes) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulId().toString());
                item.setLabel(tsol.getMulValor());
                lstTSolicitud.add(item);
            }
            if (!rol.getRolLdap().equalsIgnoreCase(Constant.ROL_CGV)) {
                activarSolicitante = true;
                cod_solicitante = user.getUsuLogin();
                nom_solicitante = user.getUsuNombre() + " " + user.getUsuApellidos();
                if (user.getUsuEmail() != null && user.getUsuEmail().length() > 4) {
                    email_solicitante = user.getUsuEmail();
                }
            }
            initLists();
            loadRegionales();
        } catch (ApplicationException ex) {
            LOGGER.error("Error al cargar geográfico. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*Accion ejecutada cuando el usuario selecciona el Tipo de solicitud que desea crear para activar o desactivar los campos requeridos*/
    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onChangeTsolicitud(ValueChangeEvent event)  {
        showDetail = true;
        son_tipoSolicitud = event.getNewValue().toString();
        try {
            if (son_tipoSolicitud.equals("62")) {
                showCuentaMatriz = true;
            } else if (son_tipoSolicitud.equals("0")) {
                message = "Debe seleccionar un Pais para poder continuar.";
            }

            initLists();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: onChangeTsolicitud()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*Accion ejecutada cuando el usuario selecciona un Pais para cargar las Regionales correspondientes a la Regional seleccionada*/
    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onChangePais(ValueChangeEvent event)  {
        son_pais = event.getNewValue().toString();
        try {
            if (son_pais.equals("0")) {
                message = "Debe seleccionar un Tipo de solicitud para poder continuar.";
            } else {
                loadRegionales();
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: onChangePais()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @param ev
     */
    public void doHabilitar(ActionEvent ev) {
        UIComponent cp = ev.getComponent();
        UIComponent btnver = cp.findComponent("detail");
        btnver.setRendered(true);
    }

    /**
     * Accion ejecutada cuando el usuario selecciona una Regional para cargar
     * las ciudades correspondientes a la Regional seleccionada
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onChangeRegional(ValueChangeEvent event) {
        son_regional = event.getNewValue().toString();
        try {
            if (son_regional.equals("0")) {
                message = "Debe seleccionar una Regional para poder continuar.";
            } else {
                citi(event.getNewValue().toString());
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: onChangeRegional()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Metodo que se ejecuta cuando el Usuario indica "crear" la solicitud de
     * Negocio, toma los valores ingresados por el usuario y crea la solicitud
     * de Negocio informando al usuario el ID con el que fue creada o el error
     * generado
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionCreate()  {
        try {
            city = loadCity();
            neighborhood = CargueGeograficoDelegate.queryNeighborhood(son_barrio, new BigDecimal(son_ciudad));
            thhpp = loadTipoHhpp();
            tipoHhppRed = loadTipoHhppRed();
            tipoHhppConexion = loadTipoHhppConexion();
            tipoMarcas = loadTipoMarcas();
            message = fieldsValidator();
            createDirNoStandar();
            addressValidator(direccionNostandar);
            loadNodo();
            solicitud = new CargueGeografico();
            createSolicitud();
            idSon = BigDecimal.ZERO;
            idSon = CargueGeograficoDelegate.insertSolicitudNegocio(solicitud);
            if (idSon.intValue() == 0) {
                message = "Error al crear la solicitud";
            } else {
                message = "La solicitud fue creada con exito con el id:" + idSon;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al crea la solicitud de Negocio . EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: onActionCreate()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*Hace las validaciones de los campos segun el tipo de solicitud*/
    private String fieldsValidator() {
        if (son_tipoSolicitud.equals("62") || son_tipoSolicitud.equals("63") || son_tipoSolicitud.equals("64")) {
            if (son_ciudad.equals("0")) {
                return createMessageRequieredField("Ciudad");
            } else if (son_barrio.isEmpty()) {
                return createMessageRequieredField("Barrio");
            } else if (son_tcalle.equals("0")) {
                return "Direccion invalida, por favor verifique";
            } else if (son_calle.isEmpty()) {
                return Constant.MESSAGE_INVALID_DIR;
            } else if (son_placa1.isEmpty()) {
                return Constant.MESSAGE_INVALID_DIR;
            } else if (son_placa2.isEmpty()) {
                return Constant.MESSAGE_INVALID_DIR;
            } else if (son_apto.isEmpty()) {
                return Constant.MESSAGE_INVALID_DIR;
            } else if (nombre_nodo.isEmpty()) {
                return createMessageRequieredField("Nodo");
            } else if (son_tipo_hhpp.isEmpty()) {
                return createMessageRequieredField("Tipo Unidad");
            } else if (son_tipo_conexion.isEmpty()) {
                return createMessageRequieredField("Tipo Conexion");
            } else if (cod_solicitante.isEmpty()) {
                return createMessageRequieredField("Codigo Solicitante");
            } else if (nom_solicitante.isEmpty()) {
                return createMessageRequieredField("Nombre Solicitante");
            } else if (email_solicitante.isEmpty()) {
                return createMessageRequieredField("Email Solicitante");
            } else if (cel_solicitante.isEmpty()) {
                return createMessageRequieredField("Celular Solicitante");
            } else if (!cel_solicitante.isEmpty()) {
                try {
                    Integer.getInteger(cel_solicitante);
                } catch (Exception ex) {
                    LOGGER.error("Error en _______. EX000 " + ex.getMessage(), ex);
                    return "Celular de Solicitante invalido";
                }
            } else if (!containsNumbers(son_contacto)) {
                createMessageWrongValueField("Nombre Contacto");
            } else if (son_contacto.length() < 2) {
                return createMessageRequieredField("Nombre Contacto");
            } else if (!son_tel_contacto.isEmpty()) {
                try {
                    Integer.getInteger(son_tel_contacto);
                } catch (Exception ex) {
                    LOGGER.error("Error al validar campos. EX000 " + ex.getMessage(), ex);
                    return "Telefono de contacto invalido";
                }
            }
        }
        return "El tipo de Solicitud es invalido";
    }

    /*
     * Crea un mensaje cuando los datos del Campo "nombreCampo" son vacios
     */
    private String createMessageRequieredField(String nombreCampo) {
        return nombreCampo + ", es un Campo obligatorio, por favor diligencielo.";
    }

    /*
     * Crea un mensaje cuando los datos del Campo contienen valores no validos
     */
    private String createMessageWrongValueField(String nombreCampo) {
        return nombreCampo + ", contiene valores no validos, por favor verifique.";
    }

    /**
     *
     * @param cadena
     * @return
     */
    private boolean containsNumbers(String cadena) {
        boolean res = false;
        if (cadena.contains("0") || cadena.contains("1") || cadena.contains("2") ||
                cadena.contains("3") || cadena.contains("4") || cadena.contains("5") ||
                cadena.contains("6") || cadena.contains("7") || cadena.contains("8") || cadena.contains("9")) {
            res = true;
        }
        return res;
    }

    /*Inicializa las listas necesarias para crear la solicitud de Negocio */
    private void initLists() {
        listPaises = new ArrayList<>();
        listThhpp = new ArrayList<>();
        listTred = new ArrayList<>();
        listTconexion = new ArrayList<>();
        listTblackL = new ArrayList<>();
        listTCalles = new ArrayList<>();
        listLetras = new ArrayList<>();
        listPrefijos = new ArrayList<>();
        listCardinales = new ArrayList<>();
        try {
            paises = CargueGeograficoDelegate.queryPaises();
            tCalles = CargueGeograficoDelegate.queryCalles();
            letras = CargueGeograficoDelegate.queryLetras();
            prefijos = CargueGeograficoDelegate.queryPrefijos();
            cardinales = CargueGeograficoDelegate.queryCardinales();
            tiposhhpp = CargueGeograficoDelegate.queryTiposHhpp();
            tiposred = CargueGeograficoDelegate.queryTipoRed();
            tiposconexion = CargueGeograficoDelegate.queryTipoConexion();
            tiposBlackL = CargueGeograficoDelegate.queryBlackList();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
            for (Multivalor calle : tCalles) {
                SelectItem item = new SelectItem();
                item.setValue(calle.getMulId());
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
            for (TipoHhpp thh : tiposhhpp) {
                SelectItem item = new SelectItem();
                item.setValue(thh.getThhId());
                item.setLabel(thh.getThhValor());
                listThhpp.add(item);
            }
            for (TipoHhppRed thr : tiposred) {
                SelectItem item = new SelectItem();
                item.setValue(thr.getThrId().toString());
                item.setLabel(thr.getThrCodigo());
                listTred.add(item);
            }
            for (TipoHhppConexion thc : tiposconexion) {
                SelectItem item = new SelectItem();
                item.setValue(thc.getThcId().toString());
                item.setLabel(thc.getThcCodigo());
                listTconexion.add(item);
            }
            for (TipoMarcas tbl : tiposBlackL) {
                SelectItem item = new SelectItem();
                item.setValue(tbl.getTmaId().toString());
                item.setLabel(tbl.getTmaCodigo());
                listTblackL.add(item);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al iniciar listas necesarias para crear la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: initLists()" + ex.getMessage(), ex, LOGGER);
        }
    }
    /*Carga las Regionales del pais Seleccionado por el usuario*/

    private void loadRegionales() {
        try {
            regionales = CargueGeograficoDelegate.queryRegionales(new BigDecimal("6"));
            listRegionales = new ArrayList<SelectItem>();
            disableRegionales = true;
            for (GeograficoPolitico gpo : regionales) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listRegionales.add(item);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al cargar las regionales. EX000 " + ex.getMessage(), ex);
         } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: loadRegionales()" + ex.getMessage(), ex, LOGGER);
        }
    }
    /*Carga las ciudades de la Regional seleccionada por el usuario*/

    private void citi(String regional) {
        opciones = new SelectItem[]{
            new SelectItem("01", "Opción 1"),
            new SelectItem("02", "Opción 2"),
            new SelectItem("03", "Opción 3"),};
    }

    private void loadCities(String regional) {
        try {
            if (ciudades == null) {
                disableCiudades = false;
                listCiudades = new ArrayList<SelectItem>();

                opciones = new SelectItem[]{
                    new SelectItem("01", "Opción 1"),
                    new SelectItem("02", "Opción 2"),
                    new SelectItem("03", "Opción 3"),};

            }
        } catch (Exception ex) {
            LOGGER.error("Error al cargar ciudades. EX000 " + ex.getMessage(), ex);
        }
    }
    /*Crea la direccion no estandarizada con los valores ingresados por el usuario*/

    /**
     *
     * @return
     */
    public String createDirNoStandar() {
        loadTipoCalle();
        if ((loadCardinalidad(son_cardinalCalle)) != null) {
            cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
        }
        if ((loadCardinalidad(son_cardinalPlaca)) != null) {
            cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
        }
        //son_tcalle+son_calle+son_letraCalle+son_prefijoCalle+son_cardinalCalle+son_placa1+son_letraPlaca+son_prefijoPlaca+son_cardinalPlaca+son_placa2+
        if (son_prefijoCalle.equalsIgnoreCase("null") || son_prefijoCalle.equalsIgnoreCase("0")) {
            son_prefijoCalle = "";
        }
        if (son_prefijoPlaca.equalsIgnoreCase("null") || son_prefijoPlaca.equalsIgnoreCase("0")) {
            son_prefijoPlaca = "";
        }
        if (son_letraCalle.equalsIgnoreCase("null") || son_letraCalle.equalsIgnoreCase("0")) {
            son_letraCalle = "";
        }
        if (son_letraPlaca.equalsIgnoreCase("null") || son_letraPlaca.equalsIgnoreCase("0")) {
            son_letraPlaca = "";
        }

        direccionNostandar = tipocalle + " " + son_calle + " " + son_letraCalle + " " + son_prefijoCalle + " " + cardinalCalle
                + " " + son_placa1 + " " + son_letraPlaca + " " + son_prefijoPlaca + " " + cardinalPlaca + " " + son_placa2 + " "
                + son_complemento + " " + son_apto;
        return direccionNostandar;
    }

    /* Crea la solicitud de negocio con los valores estandarizados por el servicio y los valores ingresados por el usuario
     * */
    private void createSolicitud() {
        solicitud = new CargueGeografico();
        if (son_tipoSolicitud.equals("62")) {
            solicitud.setSonCuentaMatriz(son_cuenta_matriz);
        }
        solicitud.setSonMotivo(son_motivo);
        solicitud.setSonNomSolicitante(nom_solicitante);
        solicitud.setSonContacto(son_contacto);
        solicitud.setSonTelContacto(son_tel_contacto);
        solicitud.setSonEstado(Constant.ESTADO_SON_INICIAL);
        solicitud.setSonUsuarioCreacion(user.getUsuUsuarioCreacion());
        solicitud.setSonObservaciones(observaciones);
        solicitud.setSonFormatoIgac(addressResult.getAddress());
        solicitud.setSonServinformacion(addressGeodata.getCoddir());
        solicitud.setSonNostandar(direccionNostandar);
        if (son_tipoSolicitud.equals("64")) {
            solicitud.setSonTipoSolicitud(Constant.TIPO_SON_HHPPUNI);
        } else if (son_tipoSolicitud.equals("63")) {
            solicitud.setSonEstadoUni("N");
            solicitud.setSonTipoSolicitud(Constant.TIPO_SON_VERCASA);
        } else {
            solicitud.setSonEstadoUni("E");
        }
        solicitud.setSonEstrato(addressResult.getLeveleconomic());
        solicitud.setSonDiralterna(addressResult.getAlternateaddress());
        solicitud.setSonMz(addressResult.getAppletstandar());
        solicitud.setSonLongitud(addressGeodata.getCx());
        solicitud.setSonLatitud(addressGeodata.getCy());
        String ladoMz = "";
        if (son_tipoSolicitud.equals("64")) {
            if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                ladoMz = "Nodo BI que entrega el GEO" + " - " + "Nodo UNI que entrega el GEO";
            } else if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.ZBO)) {
                ladoMz = "Nodo BI que entrega el GEO" + " - " + "tercer Nodo UNI que entrega el GEO(TERCER CAMPO)" + " - " + "Nodo UNI que entrega el GEO";
            } else if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.SPV)) {
                ladoMz = "Nodo BI que entrega el GEO" + " - " + "Nodo UNI que entrega el GEO" + " - " + "tercer Nodo UNI que entrega el GEO(TERCER CAMPO)";
            }
        }
        solicitud.setSonLadomz(ladoMz);
        solicitud.setSonHeadEnd(nodo.getNodHeadend());
        solicitud.setSonCodSolicitante(cod_solicitante);
        solicitud.setSonEmailSolicitante(email_solicitante);
        solicitud.setSonCelSolicitante(cel_solicitante);
        solicitud.setSonEstadoSol(Constant.ESTADO_SON_INICIAL);
        solicitud.setSonTipoSolicitud(loadTipoSolicitud().getMulValor());
        solicitud.setTipoHhpp(thhpp);
        solicitud.setTipoMarcas(tipoMarcas);
        solicitud.setTipoHhppRed(tipoHhppRed);
        solicitud.setTipoHhppConexion(tipoHhppConexion);
        solicitud.setNodo(nodo);
        solicitud.setGeograficoPolitico(city);
        solicitud.setGeografico(neighborhood);
    }

    /*Valida la direccion ingresada por el usuario*/
    private void addressValidator(String direccionNostandar) {
        try {
            address = new AddressRequest();
            address.setAddress(direccionNostandar);
            address.setNeighborhood(son_barrio);
            //DEBERIA TENER son_ciudad
            address.setCity(city.getGpoNombre());
            addressResult = new AddressService();
            addressResult = GeoreferenciaDelegate.queryAddress(address);
            addressGeodata = GeoreferenciaDelegate.queryAddressGeodata(address);
            if (addressResult == null) {
                message = "Imposible encontrar Dirección:" + direccionNostandar;
            } else if (addressResult.getExist().matches("false")) {
                message = "La dirección: " + direccionNostandar + ", No existe";
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al validar dirección. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: addressValidator()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @return
     */
    private Multivalor loadTipoSolicitud() {
        Multivalor soli = new Multivalor();
        for (int i = 0; i < tSolicitudes.size(); i++) {
            Multivalor sol = tSolicitudes.get(i);
            if (sol.getMulId().equals(new BigDecimal(son_tipoSolicitud))) {
                soli = sol;
                i = tSolicitudes.size();
            }
        }
        return soli;
    }

    /**
     *
     */
    private void loadNodo() {
        try {
            nodo = CargueGeograficoDelegate.queryNodo(nombre_nodo, city.getGpoId());
            if (nodo == null) {
                message = "No se Encontro el nodo con nombre" + nombre_nodo;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al cargar nodo EX000 ".concat(ex.getMessage()), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en CargueGeograficoMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < ciudades.size(); i++) {
            GeograficoPolitico ciudad = ciudades.get(i);
            son_ciudad = "3";
            if (ciudad.getGpoId().equals(new BigDecimal(this.son_ciudad))) {
                geo = ciudad;
            }
        }
        return geo;
    }

    /**
     *
     * @return
     */
    public TipoHhpp loadTipoHhpp() {
        TipoHhpp th = new TipoHhpp();
        for (int i = 0; i < tiposhhpp.size(); i++) {
            TipoHhpp thh = tiposhhpp.get(i);
            if (thh.getThhId().equals(this.son_tipo_hhpp)) {
                th = thh;
            }
        }
        return th;
    }

    /**
     *
     */
    public void loadTipoCalle() {
        for (int i = 0; i < tCalles.size(); i++) {
            Multivalor calle = tCalles.get(i);
            if (calle.getMulId().equals(new BigDecimal(this.son_tcalle))) {
                tipocalle = calle.getDescripcion();
            }
        }
    }

    /**
     *
     * @param cardib
     * @return
     */
    public Multivalor loadCardinalidad(String cardib) {
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
    private TipoMarcas loadTipoMarcas() {
        TipoMarcas tbl = new TipoMarcas();
        for (int i = 0; i < tiposBlackL.size(); i++) {
            TipoMarcas tbls = tiposBlackL.get(i);
            if (tbls.getTmaId().equals(new BigDecimal(this.son_tipo_blackl))) {
                tbl = tbls;
            }
        }
        return tbl;
    }

    /**
     *
     * @return
     */
    private TipoHhppRed loadTipoHhppRed() {
        TipoHhppRed thr = new TipoHhppRed();
        if (son_tipo_red.equals("0")) {
            return thr;
        } else {
            for (int i = 0; i < tiposred.size(); i++) {
                TipoHhppRed thre = tiposred.get(i);
                if (thre.getThrId().equals(new BigDecimal(this.son_tipo_red))) {
                    thr = thre;
                }
            }
            return thr;
        }
    }

    /**
     *
     * @return
     */
    private TipoHhppConexion loadTipoHhppConexion() {
        TipoHhppConexion thc = new TipoHhppConexion();
        for (int i = 0; i < tiposconexion.size(); i++) {
            TipoHhppConexion thco = tiposconexion.get(i);
            if (thco.getThcId().equals(new BigDecimal(this.son_tipo_conexion))) {
                thc = thco;
            }
        }
        return thc;
    }

    /**
     *
     * @return
     */
    public String getSon_tipoSolicitud() {
        return son_tipoSolicitud;
    }

    /**
     *
     * @param son_tipoSolicitud
     */
    public void setSon_tipoSolicitud(String son_tipoSolicitud) {
        this.son_tipoSolicitud = son_tipoSolicitud;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getLstTSolicitud() {
        return lstTSolicitud;
    }

    /**
     *
     * @param lstTSolicitud
     */
    public void setLstTSolicitud(List<SelectItem> lstTSolicitud) {
        this.lstTSolicitud = lstTSolicitud;
    }

    /**
     *
     * @return
     */
    public boolean isShowDetail() {
        return showDetail;
    }

    /**
     *
     * @return
     */
    public boolean isShowCuentaMatriz() {
        return showCuentaMatriz;
    }

    /**
     *
     * @param showCuentaMatriz
     */
    public void setShowCuentaMatriz(boolean showCuentaMatriz) {
        this.showCuentaMatriz = showCuentaMatriz;
    }

    /**
     *
     * @param showDetail
     */
    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
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
    public String getSon_pais() {
        return son_pais;
    }

    /**
     *
     * @return
     */
    public boolean isDisableRegionales() {
        return disableRegionales;
    }

    /**
     *
     * @param disableRegionales
     */
    public void setDisableRegionales(boolean disableRegionales) {
        this.disableRegionales = disableRegionales;
    }

    /**
     *
     * @param son_pais
     */
    public void setSon_pais(String son_pais) {
        this.son_pais = son_pais;
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
    public List<SelectItem> getListThhpp() {
        return listThhpp;
    }

    /**
     *
     * @param listThhpp
     */
    public void setListThhpp(List<SelectItem> listThhpp) {
        this.listThhpp = listThhpp;
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
    public String getNombre_nodo() {
        return nombre_nodo;
    }

    /**
     *
     * @param nombre_nodo
     */
    public void setNombre_nodo(String nombre_nodo) {
        this.nombre_nodo = nombre_nodo;
    }

    /**
     *
     * @return
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
    public String getSon_ciudad() {
        return son_ciudad;
    }

    /**
     *
     * @param son_ciudad
     */
    public void setSon_ciudad(String son_ciudad) {
        this.son_ciudad = son_ciudad;
    }

    /**
     *
     * @return
     */
    public String getSon_barrio() {
        return son_barrio;
    }

    /**
     *
     * @param son_barrio
     */
    public void setSon_barrio(String son_barrio) {
        this.son_barrio = son_barrio;
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
    public String getCod_solicitante() {
        return cod_solicitante;
    }

    /**
     *
     * @param cod_solicitante
     */
    public void setCod_solicitante(String cod_solicitante) {
        this.cod_solicitante = cod_solicitante;
    }

    /**
     *
     * @return
     */
    public String getNom_solicitante() {
        return nom_solicitante;
    }

    /**
     *
     * @param nom_solicitante
     */
    public void setNom_solicitante(String nom_solicitante) {
        this.nom_solicitante = nom_solicitante;
    }

    /**
     *
     * @return
     */
    public String getEmail_solicitante() {
        return email_solicitante;
    }

    /**
     *
     * @param email_solicitante
     */
    public void setEmail_solicitante(String email_solicitante) {
        this.email_solicitante = email_solicitante;
    }

    /**
     *
     * @return
     */
    public String getCel_solicitante() {
        return cel_solicitante;
    }

    /**
     *
     * @param cel_solicitante
     */
    public void setCel_solicitante(String cel_solicitante) {
        this.cel_solicitante = cel_solicitante;
    }

    /**
     *
     * @return
     */
    public String getSon_contacto() {
        return son_contacto;
    }

    /**
     *
     * @param son_contacto
     */
    public void setSon_contacto(String son_contacto) {
        this.son_contacto = son_contacto;
    }

    /**
     *
     * @return
     */
    public String getSon_cuenta_matriz() {
        return son_cuenta_matriz;
    }

    /**
     *
     * @param son_cuenta_matriz
     */
    public void setSon_cuenta_matriz(String son_cuenta_matriz) {
        this.son_cuenta_matriz = son_cuenta_matriz;
    }

    /**
     *
     * @return
     */
    public String getSon_estado() {
        return son_estado;
    }

    /**
     *
     * @param son_estado
     */
    public void setSon_estado(String son_estado) {
        this.son_estado = son_estado;
    }

    /**
     *
     * @return
     */
    public String getSon_motivo() {
        return son_motivo;
    }

    /**
     *
     * @param son_motivo
     */
    public void setSon_motivo(String son_motivo) {
        this.son_motivo = son_motivo;
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
    public String getSon_regional() {
        return son_regional;
    }

    /**
     *
     * @param son_regional
     */
    public void setSon_regional(String son_regional) {
        this.son_regional = son_regional;
    }

    /**
     *
     * @return
     */
    public String getSon_tel_contacto() {
        return son_tel_contacto;
    }

    /**
     *
     * @param son_tel_contacto
     */
    public void setSon_tel_contacto(String son_tel_contacto) {
        this.son_tel_contacto = son_tel_contacto;
    }

    /**
     *
     * @return
     */
    public String getSon_tipo_hhpp() {
        return son_tipo_hhpp;
    }

    /**
     *
     * @param son_tipo_hhpp
     */
    public void setSon_tipo_hhpp(String son_tipo_hhpp) {
        this.son_tipo_hhpp = son_tipo_hhpp;
    }

    /**
     *
     * @return
     */
    public boolean isDisableCiudades() {
        return disableCiudades;
    }

    /**
     *
     * @param disableCiudades
     */
    public void setDisableCiudades(boolean disableCiudades) {
        this.disableCiudades = disableCiudades;
    }

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
    public boolean isActivarSolicitante() {
        return activarSolicitante;
    }

    /**
     *
     * @param activarSolicitante
     */
    public void setActivarSolicitante(boolean activarSolicitante) {
        this.activarSolicitante = activarSolicitante;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListTblackL() {
        return listTblackL;
    }

    /**
     *
     * @param listTblackL
     */
    public void setListTblackL(List<SelectItem> listTblackL) {
        this.listTblackL = listTblackL;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListTconexion() {
        return listTconexion;
    }

    /**
     *
     * @param listTconexion
     */
    public void setListTconexion(List<SelectItem> listTconexion) {
        this.listTconexion = listTconexion;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListTred() {
        return listTred;
    }

    /**
     *
     * @param listTred
     */
    public void setListTred(List<SelectItem> listTred) {
        this.listTred = listTred;
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
    public String getSon_tipo_blackl() {
        return son_tipo_blackl;
    }

    /**
     *
     * @param son_tipo_blackl
     */
    public void setSon_tipo_blackl(String son_tipo_blackl) {
        this.son_tipo_blackl = son_tipo_blackl;
    }

    /**
     *
     * @return
     */
    public String getSon_tipo_conexion() {
        return son_tipo_conexion;
    }

    /**
     *
     * @param son_tipo_conexion
     */
    public void setSon_tipo_conexion(String son_tipo_conexion) {
        this.son_tipo_conexion = son_tipo_conexion;
    }

    /**
     *
     * @return
     */
    public String getSon_tipo_red() {
        return son_tipo_red;
    }

    /**
     *
     * @param son_tipo_red
     */
    public void setSon_tipo_red(String son_tipo_red) {
        this.son_tipo_red = son_tipo_red;
    }
    
    private String opcionSeleccionada;

    /**
     * Creates a new instance of FormBean
     *
     * @return
     */
    public String getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    /**
     *
     * @param opcionSeleccionada
     */
    public void setOpcionSeleccionada(String opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getOpciones() {
        return opciones;
    }

    /**
     *
     * @param opciones
     */
    public void setOpciones(SelectItem[] opciones) {
        this.opciones = opciones;
    }

}
