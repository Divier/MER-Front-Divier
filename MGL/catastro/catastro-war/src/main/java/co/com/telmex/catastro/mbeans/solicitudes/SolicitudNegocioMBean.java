package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.delegate.ConsultasDelegate;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.GeoreferenciaUtils;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase SolicitudNegocioMBean Extiende de BaseMBean
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "solicitudNegocioMBean")
public class SolicitudNegocioMBean extends BaseMBean {

    /**
     *
     */
    public static final String NOMBRE_FUNCIONALIDAD = "Crear Solicitud de creacion de HHPP Visitas Técnicas";
    private BigDecimal ID_THC = BigDecimal.ZERO;
    private String DOMINIO = "";
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
    public String son_tDireccion = "";
    /**
     *
     */
    public List<SelectItem> lstTDirecciones = null;
    /**
     *
     */
    public String son_dirNoEstandarizable = "";
    /**
     *
     */
    public boolean showDetail = false;
    /**
     *
     */
    public boolean showBotonTS = true;
    /**
     *
     */
    public boolean disableTS = false;
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
    public String son_letraCalle2;
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
    public List<SelectItem> listAptos = null;
    private List<Multivalor> aptos = null;
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
    public String son_letraPlaca2 = "";
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
    public String codigo_nodo = "";
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
    public String son_area = "";
    /**
     *
     */
    public List<SelectItem> listAreas = null;
    private List<Multivalor> areas = null;
    /**
     *
     */
    public String son_motivo = "";
    /**
     *
     */
    public String cod_solicitante;
    /**
     *
     */
    public String nom_solicitante;
    /**
     *
     */
    public String email_solicitante = "";
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
    public String seleccionar = "";
    /**
     *
     */
    public boolean activarSolicitante = false;
    private GeograficoPolitico city = null;
    private GeograficoPolitico state = null;
    private Geografico geografico = null;
    private TipoHhpp tHhpp = null;
    private TipoHhppConexion tipoConexion = null;
    private TipoHhppRed tipoRed = null;
    private Nodo nodo = null;
    private Nodo nodo1 = null;
    private Nodo nodo2 = null;
    private Nodo nodo3 = null;
    private AddressRequest address = null;
    private AddressService addressResult = null;
    private AddressGeodata addressGeodata = null;
    private String direccionNostandar = "";
    private BigDecimal idSon = null;
    private SolicitudNegocio solicitud = null;
    /**
     *
     */
    public String messagePopup = "";
    /**
     *
     */
    public boolean showPopUpOk = false;
    /**
     *
     */
    public boolean spDirNoExiste = false;
    /**
     *
     */
    public boolean spDirAlter = false;
    /**
     *
     */
    public boolean validadoOk = false;
    /**
     *
     */
    public boolean showSave = true;
    /**
     *
     */
    public boolean showNoStandar = false;
    /**
     *
     */
    public boolean showDirHpUni = false;
    /**
     *
     */
    public boolean showDirHpUniNoStand = false;
    /**
     *
     */
    public boolean disableAceptar = true;
    /**
     *
     */
    public boolean showDirCasas = false;
    /**
     *
     */
    public boolean showDirCasasNoStand = false;
    /**
     *
     */
    public boolean renderedBotones = false;
    /**
     *
     */
    public int contador = 2;
    /*UPDATE FIELDS*/
    private String idSol;
    private boolean showUpdate = false;
    private boolean hideQuery = true;
    /**
     *
     */
    public boolean disableCancel = false;
    private String nombreLog;
    private static final Logger LOGGER = LogManager.getLogger(SolicitudNegocioMBean.class);

    /**
     *
     * @throws IOException
     */
    public SolicitudNegocioMBean() {
        super();
        try {
            initListBegin();
            initLists();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean" + ex.getMessage(), ex, LOGGER);
        }
    }
    //Carga las variables de configuracion globales

    /**
     *
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();
        Parametros param;
        try {
            param = recursos.queryParametros(Constant.ID_THC);
            if (param != null) {
                ID_THC = new BigDecimal(param.getValor());
            }

            param = recursos.queryParametros(Constant.DOMINIO);
            if (param != null) {
                DOMINIO = param.getValor();
            }
        } catch (Exception ex) {
            LOGGER.error("Error al consultar parametros de configuración. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     */
    public void initVariables() {
        son_dirNoEstandarizable = "";
        son_tipoSolicitud = "";
        son_tDireccion = "";
        son_pais = "";
        son_regional = "";
        son_ciudad = "";
        son_barrio = "";
        son_calle = "";
        son_tcalle = "";
        tipocalle = "";
        son_letraCalle = "";
        son_letraCalle2 = "";
        son_prefijoCalle = "";
        son_cardinalCalle = "";
        cardinalCalle = "";
        son_placa1 = "";
        son_letraPlaca = "";
        son_letraPlaca2 = "";
        son_placa2 = "";
        son_prefijoPlaca = "";
        son_cardinalPlaca = "";
        cardinalPlaca = "";
        son_apto = "";
        son_complemento = "";
        codigo_nodo = "";
        son_tipo_hhpp = "";
        son_motivo = "";
        son_area = "";
        cel_solicitante = "";
        son_contacto = "";
        son_tel_contacto = "";
        son_estado = "";
        observaciones = "";
        seleccionar = "";
        listRegionales = null;
        regionales = null;
        listCiudades = null;
        ciudades = null;
        city = null;
        geografico = null;
        tHhpp = null;
        nodo = null;
        address = null;
        addressResult = null;
        addressGeodata = null;
        direccionNostandar = "";
        solicitud = null;
        disableAceptar = true;
        showDetail = false;
        showBotonTS = true;
        disableTS = false;
        spDirNoExiste = false;
        spDirAlter = false;
        showPopUpOk = false;
        validadoOk = false;
        showNoStandar = false;
        showDirHpUni = false;
        showDirHpUniNoStand = false;
        showDirCasas = false;
        showDirCasasNoStand = false;
        disableAceptar = true;
        renderedBotones = false;
        contador = 2;
    }

    /*Accion ejecutada cuando el usuario selecciona el Tipo de solicitud que desea crear para activar o desactivar los campos requeridos*/
    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onChangeTsolicitud(ValueChangeEvent event) {
        message = "";
        son_tipoSolicitud = event.getNewValue().toString();
        try {
            if (son_tipoSolicitud.equals("0")) {
                message = "Debe seleccionar un Tipo de Solicitud para poder continuar.";
            } else {
                showDetail = true;
            }
            initLists();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onChangeTsolicitud()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     * Habilita los componentes 
     */
    /**
     *
     * @param ev
     */
    public void doHabilitar(ActionEvent ev) {
        message = "";
        if (!son_tDireccion.equals("0") && !son_tipoSolicitud.equals("0")) {
            disableAceptar = false;
            renderedBotones = true;
            showDetail = true;
            disableTS = true;
            showSave = true;

            if (son_tipoSolicitud.equals(Constant.TIPO_SON_HHPPUNI)) {
                if (son_tDireccion.equals(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
                    showNoStandar = true;
                    showDirHpUniNoStand = true;
                } else if (son_tDireccion.equals(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
                    showDirHpUni = true;
                }
            } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA)) {
                if (son_tDireccion.equals(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
                    showDirCasasNoStand = true;
                    showNoStandar = true;
                } else if (son_tDireccion.equals(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
                    showDirCasas = true;
                }
            }
        } else if (son_tipoSolicitud.equals("0")) {
            message = "Tipo de Solicitud invalido, debe seleccionar un tipo de solicitud para poder continuar.";
        } else if (son_tDireccion.equals("0")) {
            message = "Tipo de Dirección invalido, debe seleccionar un tipo de solicitud para poder continuar.";
        }
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateRegionales(ValueChangeEvent event) {
        message = "";
        String value = event.getNewValue().toString();
        try {
            regionales = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            listRegionales = new ArrayList<SelectItem>();
            if (regionales != null) {
                for (GeograficoPolitico gpo : regionales) {
                    SelectItem item = new SelectItem();
                    item.setValue(gpo.getGpoId().toString());
                    item.setLabel(gpo.getGpoNombre());
                    listRegionales.add(item);
                }
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al actualizar regionales. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: updateRegionales()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     *Accion ejecutada cuando el usuario selecciona una Regional para cargar las ciudades correspondientes a la Regional seleccionada
     */
    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event) {
        String value = event.getNewValue().toString();
        try {
            ciudades = SolicitudNegocioDelegate.queryCiudades(new BigDecimal(value));
            listCiudades = new ArrayList<SelectItem>();
            if (ciudades != null) {
                for (GeograficoPolitico gpo : ciudades) {
                    SelectItem item = new SelectItem();
                    item.setValue(gpo.getGpoId().toString());
                    item.setLabel(gpo.getGpoNombre());
                    listCiudades.add(item);
                }
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al actualizar ciudades. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: updateCiudades()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     *Metodo que se ejecuta cuando el Usuario indica "Guardar" la solicitud de Negocio, toma los valores 
     * ingresados por el usuario y valida la información
     */
    /**
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionValidar() {
        try {
            message = fieldsValidator();
            if (message.isEmpty()) {
                city = loadCity();
                state = loadRegion();
                tHhpp = loadTipoHhpp();
                createDirNoStandar();
                addressValidator(direccionNostandar);
                if (city == null) {
                    message = "Ciudad es un campo obligatorios, por favor verifique.";
                } else if (tHhpp == null) {
                    message = "Tipo Vivienda es un campo obligatorios, por favor verifique.";
                }
                if (addressGeodata != null) {
                    validadorExistenciaGeografico();
                    if (city.getGpoMultiorigen().equalsIgnoreCase("1") && geografico == null) {
                        message = "barrio no existente en la ciudad ";
                    }
                }
                loadNodo();
                if (nodo == null) {
                    message = "No existe nodos para la ciudad Seleccionada.";
                } else {
                    spDirNoExiste = validateExistDir();
                    if (spDirNoExiste) {
                        message = "La dirección: " + direccionNostandar + ", No existe";
                    }
                    if (!addressGeodata.getDiralterna().isEmpty() && addressGeodata.getFuente().equals(Constant.DIR_FUENTE_ANTIGUA)) {
                        spDirAlter = true;
                        son_dirNoEstandarizable = addressGeodata.getDiralterna();
                    }
                }
            } else {
                contador++;
            }
            if (message.isEmpty() && !spDirNoExiste && !spDirAlter && city != null && tHhpp != null && nodo != null) {
                validadoOk = true;
                messagePopup = "Esta seguro de crear la solicitud?";
            }
            if (spDirNoExiste) {
                messagePopup = "Esta Solicitud será guardada con una dirección Pendiente de Validación.¿Esta seguro de crear la solicitud?";
            }
            if (spDirAlter) {
                messagePopup = "La dirección Dirección tiene una dirección Alterna nueva, desea crear la solicitud con está Dirección Alterna?";
            }
            if (spDirNoExiste || spDirAlter || validadoOk) {
                showPopUpOk = true;
                showSave = false;
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionValidar()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     *Metodo que se ejecuta cuando el Usuario indica "crear" la solicitud de Negocio, toma los valores 
     * ingresados por el usuario y crea la solicitud de Negocio informando al usuario el ID con el que fue creada o el error generado
     */
    /**
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionCreate() {
        try {
            if (spDirAlter) {
                String dirAlterna = addressGeodata.getDiralterna();
                addressGeodata = null;
                addressValidator(dirAlterna);
            }
            if (addressGeodata != null) {
                solicitud = new SolicitudNegocio();

                createSolicitud();
                idSon = BigDecimal.ZERO;
                idSon = SolicitudNegocioDelegate.insertSolicitudNegocio(NOMBRE_FUNCIONALIDAD, solicitud);
                if (idSon.compareTo(BigDecimal.ZERO) == 0) {
                    message = "Error al crear la solicitud";
                } else {
                    message = "La solicitud fue creada con exito con el id:" + idSon;
                    initVariables();

                }
            } else {
                message = "Error al crear la Solicitud";
            }
            spDirAlter = false;
            spDirNoExiste = false;
            validadoOk = false;
            showPopUpOk = false;
            disableAceptar = true;
            renderedBotones = false;
        } catch (ApplicationException ex) {
            message = "Error al crear la Solicitud";
            LOGGER.error("Error al crear la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionValidar()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @param ev
     * @throws IOException
     */
    private void reinit(ActionEvent ev) {
        try {
            UIComponent cp = ev.getComponent();
            UIComponent observacionespn = cp.findComponent("observaciones");
            UIComponent direccion = cp.findComponent("direccion");
            UIComponent placa = cp.findComponent("placa");
            UIComponent dirCasasNoStand = cp.findComponent("dircasasNoStand");
            UIComponent placaCasas = cp.findComponent("placaCasas");
            observacionespn.setRendered(false);
            direccion.setRendered(false);
            placa.setRendered(false);
            dirCasasNoStand.setRendered(false);
            placaCasas.setRendered(false);
            showDetail = false;
            disableTS = false;
            showDirHpUni = false;
            showDirHpUniNoStand = false;
            showDirCasas = false;
            showDirCasasNoStand = false;
            showSave = false;
            disableAceptar = true;
            renderedBotones = false;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: reinit()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @return
     */
    public boolean validateExistDir() {
        GeoreferenciaUtils geoUtils = new GeoreferenciaUtils();
        if (!geoUtils.validExistAddress(addressGeodata.getDirtrad(), addressGeodata.getEstado(), addressGeodata.getValagreg())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param ev
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionAceptar(ActionEvent ev) {
        try {
            UIComponent cp = ev.getComponent();
            UIComponent popup = cp.findComponent("popup");
            popup.setRendered(false);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionAceptar()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     *Consultar una solicitud para actualizarla
     */
    /**
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionQuery() {
        try {
            solicitud = SolicitudNegocioDelegate.querySolicitudNegocio(new BigDecimal(idSol));
            son_tipoSolicitud = solicitud.getSonTipoSolicitud();
            son_pais = solicitud.getGeograficoPolitico().getGeograficoPolitico().getGpoId().toString();
            son_regional = solicitud.getGeograficoPolitico().getGeograficoPolitico().getGpoId().toString();
            son_barrio = solicitud.getGeografico().getGeoNombre();
            son_tipo_hhpp = solicitud.getTipoHhpp().getThhId();
            son_motivo = solicitud.getSonMotivo();
            cod_solicitante = solicitud.getSonCodSolicitante();
            nom_solicitante = solicitud.getSonNomSolicitante();
            email_solicitante = solicitud.getSonEmailSolicitante();
            cel_solicitante = solicitud.getSonCelSolicitante();
            son_contacto = solicitud.getSonContacto();
            son_tel_contacto = solicitud.getSonTelContacto();
            observaciones = solicitud.getSonObservaciones();
            solicitud.getSonFormatoIgac().substring(0, 2);
            tipocalle.contains("0");
            son_cardinalCalle = solicitud.getSonServinformacion().substring(8, 9);
            son_tcalle = solicitud.getSonServinformacion().substring(10, 12);
            son_calle = solicitud.getSonServinformacion().substring(12, 15);
            son_letraCalle = solicitud.getSonServinformacion().substring(19, 20);
            son_prefijoCalle = solicitud.getSonServinformacion().substring(20, 23);
            son_placa1 = solicitud.getSonServinformacion().substring(15, 18);
            son_letraPlaca = solicitud.getSonServinformacion().substring(18, 19);
            son_prefijoPlaca = solicitud.getSonServinformacion().substring(20, 23);
            son_placa2 = solicitud.getSonServinformacion().substring(28, 30);
            son_complemento = solicitud.getSonServinformacion().substring(46, 54) + solicitud.getSonServinformacion().substring(62, 70);
            son_apto = solicitud.getSonServinformacion().substring(54, 62);
            showUpdate = true;
            hideQuery = false;
            disableCancel = true;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionQuery()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Cancelar el Registro de solicitud de creación de HHPP
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionCancel() {
        try {
            initListBegin();
            initLists();
            initVariables();
            showDirHpUni = false;
            showDirHpUniNoStand = false;
            showDirCasas = false;
            showDirCasasNoStand = false;
            message = "";
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionCancel()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     * Hace las validaciones de los campos segun el tipo de solicitud
     */
    private String fieldsValidator() {
        message = "";
        String resultado = "";
        try {
            if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA) || son_tipoSolicitud.equals(Constant.TIPO_SON_HHPPUNI)) {
                if (son_pais.equals("0")) {
                    resultado = createMessageRequieredField("País");
                } else if (son_ciudad.equals("0")) {
                    resultado = createMessageRequieredField("Ciudad");
                } else if (!son_ciudad.equals("0")) {
                    city = loadCity();
                    if (city.getGpoMultiorigen().equalsIgnoreCase("1") && son_barrio.isEmpty()) {
                        resultado = createMessageRequieredField("Barrio");
                    }
                } else if (son_tDireccion.equals(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
                    if (son_tcalle.equals("0")) {
                        resultado = Constant.MESSAGE_INVALID_DIR + "Tipo Calle";
                    } else if (son_calle.isEmpty()) {
                        resultado = Constant.MESSAGE_INVALID_DIR + "Calle";
                    } else if (!son_calle.isEmpty() && notContainsNumbers(son_calle)) {
                        resultado = createMessageWrongValueField("Calle");
                    } else if (!son_calle.isEmpty() && containsLetters(son_calle)) {
                        resultado = createMessageWrongValueField("Calle");
                    } else if (son_placa1.isEmpty()) {
                        resultado = Constant.MESSAGE_INVALID_DIR + "Placa";
                    } else if (!son_placa1.isEmpty() && notContainsNumbers(son_placa1)) {
                        resultado = createMessageWrongValueField("Placa");
                    } else if (!son_placa1.isEmpty() && containsLetters(son_placa1)) {
                        resultado = createMessageWrongValueField("Placa");
                    } else if (son_placa2.isEmpty()) {
                        resultado = Constant.MESSAGE_INVALID_DIR + "Placa";
                    } else if (!son_placa2.isEmpty() && notContainsNumbers(son_placa2)) {
                        resultado = Constant.MESSAGE_INVALID_DIR + "Placa";
                    } else if (!son_placa2.isEmpty() && containsLetters(son_placa2)) {
                        resultado = createMessageWrongValueField("Placa");
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA) && son_apto.equals("0")) {
                        resultado = createMessageRequieredField(" Apartamento");
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_HHPPUNI) && son_apto.isEmpty()) {
                        resultado = createMessageRequieredField(" Apartamento");
                    }
                } else if (son_tDireccion.equals(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
                    if (son_dirNoEstandarizable.isEmpty()) {
                        resultado = createMessageRequieredField(" Dirección ");
                    } else if (son_dirNoEstandarizable.length() < 10) {
                        resultado = createMessageWrongValueField(" Dirección ");
                    } else if (son_dirNoEstandarizable.length() > 256) {
                        resultado = createMessageValueFieldExceedSize(" Dirección ");
                    }
                }
                if (codigo_nodo.length() > 6) {
                    resultado = createMessageValueFieldExceedSize("Nodo");
                } else if (!(codigo_nodo.isEmpty()) && codigo_nodo.length() < 6) {
                    resultado = createMessageWrongValueField("Nodo");
                } else if (son_tipo_hhpp.equals("0")) {
                    resultado = createMessageRequieredField("Tipo Unidad");
                } else if (son_area.equals("0")) {
                    resultado = createMessageRequieredField("Area Solicitante");
                } else if (cel_solicitante.isEmpty()) {
                    resultado = createMessageRequieredField("Celular Solicitante");
                } else if (!cel_solicitante.isEmpty() && (notContainsNumbers(cel_solicitante) || containsLetters(cel_solicitante))) {
                    resultado = createMessageWrongValueField("Celular Solicitante");
                } else if (containsNumbers(son_contacto)) {
                    resultado = createMessageWrongValueField("Nombre Contacto");
                } else if (son_contacto.length() < 2) {
                    resultado = createMessageRequieredField("Nombre Contacto");
                } else if (!son_tel_contacto.isEmpty() && (notContainsNumbers(son_tel_contacto) || containsLetters(son_tel_contacto))) {
                    resultado = createMessageWrongValueField("Telefono de contacto");
                } else if (son_motivo.isEmpty()) {
                    resultado = createMessageRequieredField("Motivo");
                } else if (son_motivo.length() > 256) {
                    resultado = createMessageValueFieldExceedSize("Motivo");
                } else if (email_solicitante.length() > 100) {
                    resultado = createMessageValueFieldExceedSize("Email Solicitante");
                } else if (cel_solicitante.length() > 30) {
                    resultado = createMessageValueFieldExceedSize("Celular Solicitante");
                } else if (son_contacto.length() > 50) {
                    resultado = createMessageValueFieldExceedSize("Nombre Contacto");
                } else if (son_tel_contacto.length() > 20) {
                    resultado = createMessageValueFieldExceedSize("Telefono Contacto");
                } else if (observaciones.length() > 256) {
                    resultado = createMessageValueFieldExceedSize("Observaciones");
                } else if (observaciones.isEmpty()) {
                    resultado = createMessageRequieredField("observaciones");
                }
                if (rol.getRolLdap().equalsIgnoreCase(Constant.ROL_CGV)) {
                    if (email_solicitante.isEmpty()) {
                        resultado = createMessageRequieredField("Email Solicitante");
                    } else if (!email_solicitante.isEmpty() && (email_solicitante.contains("ejemplo") || !email_solicitante.contains("@") || !email_solicitante.contains("."))) {
                        resultado = createMessageWrongValueField("Email Solicitante");
                    } else if (cod_solicitante.isEmpty()) {
                        resultado = createMessageRequieredField("Codigo Solicitante");
                    } else if (cod_solicitante.length() > 50) {
                        resultado = createMessageValueFieldExceedSize("Codigo Solicitante");
                    } else if (nom_solicitante.isEmpty()) {
                        resultado = createMessageRequieredField("Nombre Solicitante");
                    } else if (nom_solicitante.length() > 100) {
                        resultado = createMessageValueFieldExceedSize("Nombre Solicitante");
                    }
                }
            } else {
                resultado = "El tipo de Solicitud es invalido";
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: onActionCancel()" + ex.getMessage(), ex, LOGGER);
        }
        return resultado;
    }

    /*
     * Crea un mensaje cuando los datos del Campo "nombreCampo" son vacios
     */
    private String createMessageRequieredField(String nombreCampo) {
        String messageFR = nombreCampo + ", es un Campo obligatorio, por favor diligencielo.";
        return messageFR;
    }

    /*
     * Crea un mensaje cuando los datos del Campo contienen valores no validos
     */
    private String createMessageWrongValueField(String nombreCampo) {
        String messageWV = nombreCampo + ", contiene valores no validos, por favor verifique.";
        return messageWV;
    }

    /*
     * Crea un mensaje cuando los datos del Campo contienen valores no validos
     */
    private String createMessageValueFieldExceedSize(String nombreCampo) {
        String messageWV = nombreCampo + ", Excede el tamaño , por favor verifique.";
        return messageWV;
    }

    /*
     *Valida si una cadena contiene numeros
     */
    private boolean containsNumbers(String cadena) {
        boolean res = false;
        if (cadena.contains("0") || cadena.contains("1") || cadena.contains("2") || cadena.contains("3") || cadena.contains("4") || cadena.contains("5")
                || cadena.contains("6") || cadena.contains("7") || cadena.contains("8") || cadena.contains("9")) {
            res = true;
        }
        return res;
    }

    /*
     *Valida si una cadena contiene numeros
     */
    private boolean notContainsNumbers(String cadena) {
        boolean res = false;
        if (!cadena.contains("0") && !cadena.contains("1") && !cadena.contains("2") && !cadena.contains("3") && !cadena.contains("4") && !cadena.contains("5")
                && !cadena.contains("6") && !cadena.contains("7") && !cadena.contains("8") && !cadena.contains("9")) {
            res = true;
        }
        return res;
    }

    /*
     *Valida si una cadena (numerica) contine letras o caracteres especiales
     */
    private boolean containsLetters(String cadena) {
        boolean res = false;
        if (cadena.contains("a") || cadena.contains("b") || cadena.contains("c") || cadena.contains("d") || cadena.contains("e") || cadena.contains("f")
                || cadena.contains("g") || cadena.contains("h") || cadena.contains("i") || cadena.contains("j") || cadena.contains("k")
                || cadena.contains("l") || cadena.contains("m") || cadena.contains("n") || cadena.contains("o") || cadena.contains("p")
                || cadena.contains("q") || cadena.contains("r") || cadena.contains("s") || cadena.contains("t") || cadena.contains("u")
                || cadena.contains("v") || cadena.contains("w") || cadena.contains("x") || cadena.contains("y") || cadena.contains("z")
                || cadena.contains("A") || cadena.contains("B") || cadena.contains("C") || cadena.contains("D") || cadena.contains("E")
                || cadena.contains("F") || cadena.contains("G") || cadena.contains("H") || cadena.contains("I") || cadena.contains("J")
                || cadena.contains("K") || cadena.contains("L") || cadena.contains("M") || cadena.contains("N") || cadena.contains("O")
                || cadena.contains("P") || cadena.contains("Q") || cadena.contains("R") || cadena.contains("S") || cadena.contains("T")
                || cadena.contains("U") || cadena.contains("V") || cadena.contains("W") || cadena.contains("X") || cadena.contains("Y")
                || cadena.contains("Z") || cadena.contains("«") || cadena.contains("»") || cadena.contains("‹") || cadena.contains("›")
                || cadena.contains("“") || cadena.contains("”") || cadena.contains("‘") || cadena.contains("’") || cadena.contains("„")
                || cadena.contains("‚") || cadena.contains("–") || cadena.contains("—") || cadena.contains("|") || cadena.contains("/")
                || cadena.contains("[") || cadena.contains("]") || cadena.contains("{") || cadena.contains("}") || cadena.contains("§")
                || cadena.contains("¶") || cadena.contains("¡") || cadena.contains("¿") || cadena.contains("?") || cadena.contains("±")
                || cadena.contains("×") || cadena.contains("~") || cadena.contains("˜") || cadena.contains("÷") || cadena.contains("?")
                || cadena.contains("p") || cadena.contains("†") || cadena.contains("‡") || cadena.contains("¥") || cadena.contains("€")
                || cadena.contains("$") || cadena.contains("¢") || cadena.contains("£") || cadena.contains("ß") || cadena.contains("©")
                || cadena.contains("®") || cadena.contains("@") || cadena.contains("™") || cadena.contains("°") || cadena.contains("‰")
                || cadena.contains("·") || cadena.contains(")") || cadena.contains("{") || cadena.contains("&") || cadena.contains("/")
                || cadena.contains("¬") || cadena.contains("!") || cadena.contains("#") || cadena.contains("%") || cadena.contains("(")
                || cadena.contains("=") || cadena.contains("#") || cadena.contains("*") || cadena.contains("+") || cadena.contains("_")
                || cadena.contains("-") || cadena.contains(".") || cadena.contains(":") || cadena.contains(";") || cadena.contains(",")
                || cadena.contains(">") || cadena.contains("<") || cadena.contains("]") || cadena.contains("^") || cadena.contains("'")
                || cadena.contains("?")) {
            res = true;
        }
        return res;
    }

    /**
     * Inicializar las lista basicas requeridas
     */
    private void initListBegin() {
        queryParametrosConfig();
        lstTSolicitud = new ArrayList<>();
        try {
            tSolicitudes = SolicitudNegocioDelegate.loadTiposSolicitud();
            for (Multivalor tsol : tSolicitudes) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor());
                item.setLabel(tsol.getDescripcion());
                lstTSolicitud.add(item);
            }
        } catch (ApplicationException ex) {
            message = "Error al cargar los Tipos de Solicitud.";
            LOGGER.error("Error al iniciar las listas básicas. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: initListBegin()" + ex.getMessage(), ex, LOGGER);
        }

        lstTDirecciones = new ArrayList<>();
        SelectItem si1 = new SelectItem();
        si1.setValue(Constant.SON_TDIRECCION_ESTANDARIZABLE);
        si1.setLabel(Constant.SON_TDIRECCION_ESTANDARIZABLE);
        lstTDirecciones.add(si1);
        SelectItem si = new SelectItem();
        si.setValue(Constant.SON_TDIRECCION_NOESTANDARIZABLE);
        si.setLabel(Constant.SON_TDIRECCION_NOESTANDARIZABLE);
        lstTDirecciones.add(si);

        if (!rol.getRolLdap().equalsIgnoreCase(Constant.ROL_CGV)) {
            activarSolicitante = true;
            cod_solicitante = user.getUsuLogin();
            nom_solicitante = user.getUsuNombre() + " " + user.getUsuApellidos();
            email_solicitante = user.getUsuLogin() + DOMINIO;
        } else {
            email_solicitante = "ejemplo@ejemplo.com";
        }
    }

    /*Inicializa las listas necesarias para crear la solicitud de Negocio */
    private void initLists() {
        listPaises = new ArrayList<>();
        listThhpp = new ArrayList<>();
        listAreas = new ArrayList<>();
        listTCalles = new ArrayList<>();
        listLetras = new ArrayList<>();
        listPrefijos = new ArrayList<>();
        listCardinales = new ArrayList<>();
        listAptos = new ArrayList<>();
        try {
            paises = SolicitudNegocioDelegate.queryPaises();
            tCalles = SolicitudNegocioDelegate.queryCalles();
            letras = SolicitudNegocioDelegate.queryLetras();
            prefijos = SolicitudNegocioDelegate.queryPrefijos();
            cardinales = SolicitudNegocioDelegate.queryCardinales();
            tiposhhpp = SolicitudNegocioDelegate.queryTiposHhpp();
            areas = SolicitudNegocioDelegate.loadMultivalores(Constant.GMULTI_AREA);
            aptos = SolicitudNegocioDelegate.loadMultivalores(Constant.GMULTI_APTOS);
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
            for (TipoHhpp tvi : tiposhhpp) {
                SelectItem item = new SelectItem();
                item.setValue(tvi.getThhId());
                item.setLabel(tvi.getThhValor());
                listThhpp.add(item);
            }
            for (Multivalor ar : areas) {
                SelectItem item = new SelectItem();
                item.setValue(ar.getMulValor());
                item.setLabel(ar.getMulValor());
                listAreas.add(item);
            }
            for (Multivalor apto : aptos) {
                SelectItem item = new SelectItem();
                item.setValue(apto.getMulValor());
                item.setLabel(apto.getMulValor());
                listAptos.add(item);
            }
        } catch (ApplicationException ex) {
            message = "Error al cargar las listas requeridas.";
            LOGGER.error("Error al iniciar las listas necesarias para crear las solicitudes de negocio. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: initLists()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /*
     * Crea la direccion no estandarizada con los valores ingresados por el usuario
     */
    private void createDirNoStandar() {
        if (son_tDireccion.equalsIgnoreCase(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
            loadTipoCalle();
            direccionNostandar = tipocalle + " " + son_calle;
            if (son_letraCalle.equalsIgnoreCase("")) {
                son_letraCalle = "";
            } else if (!son_letraCalle.equalsIgnoreCase("0")) {
                direccionNostandar = direccionNostandar + " " + son_letraCalle;
            }
            if (son_prefijoCalle.equalsIgnoreCase("")) {
                son_prefijoCalle = "";
            } else if (!son_prefijoCalle.equalsIgnoreCase("0")) {
                direccionNostandar = direccionNostandar + " " + son_prefijoCalle;
            }
            if (son_letraCalle2.equalsIgnoreCase("")) {
                son_letraCalle2 = "";
            } else if (!son_letraCalle2.equalsIgnoreCase("0")) {
                direccionNostandar = direccionNostandar + " " + son_letraCalle2;
            }
            if ((loadCardinalidad(son_cardinalCalle)) != null) {
                cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
                direccionNostandar = direccionNostandar + " " + cardinalCalle;
            }
            direccionNostandar = direccionNostandar + " " + son_placa1;
            if (!son_letraPlaca.equalsIgnoreCase("") && !son_letraPlaca.equalsIgnoreCase("0")) {
                direccionNostandar = direccionNostandar + " " + son_letraPlaca;
            }
            if (!son_prefijoPlaca.equalsIgnoreCase("") && !son_prefijoPlaca.equalsIgnoreCase("0")) {
                direccionNostandar = direccionNostandar + " " + son_prefijoPlaca;
            }
            if ((loadCardinalidad(son_cardinalPlaca)) != null) {
                cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
                direccionNostandar = direccionNostandar + " " + cardinalPlaca;
            }
            direccionNostandar = direccionNostandar + " " + son_placa2;
            if (!son_complemento.equals("")) {
                direccionNostandar = direccionNostandar + " " + son_complemento;
            }
            if (!son_apto.equals("")) {
                if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA)) {
                    direccionNostandar = direccionNostandar + " " + son_apto;
                } else {
                    direccionNostandar = direccionNostandar + " AP " + son_apto;
                }
            }
        } else if (son_tDireccion.equalsIgnoreCase(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
            direccionNostandar = son_dirNoEstandarizable.trim();
        }
    }

    /* Crea la solicitud de negocio con los valores estandarizados por el servicio y los valores ingresados por el usuario
     * 
     */
    private void createSolicitud() {
        solicitud = new SolicitudNegocio();
        BigDecimal confiabilidad = null;
        solicitud.setSonMotivo(son_motivo);
        solicitud.setSonAreaSolicitante(son_area);
        solicitud.setSonNomSolicitante(nom_solicitante);
        solicitud.setSonContacto(son_contacto);
        solicitud.setSonTelContacto(son_tel_contacto);
        solicitud.setSonUsuarioCreacion(user.getUsuNombre() + " " + user.getUsuApellidos());
        solicitud.setSonObservaciones(observaciones);
        solicitud.setSonFormatoIgac(addressGeodata.getDirtrad());
        solicitud.setSonServinformacion(addressGeodata.getCoddir());
        solicitud.setSonPlaca(addressGeodata.getCodencont());
        solicitud.setSonComplemento(addressGeodata.getCoddir().substring(addressGeodata.getCodencont().length()));
        solicitud.setSonNostandar(direccionNostandar);
        solicitud.setSonLocalidad(addressGeodata.getLocalidad());
        if (son_tipoSolicitud.equals(Constant.TIPO_SON_HHPPUNI)) {
            solicitud.setSonTipoSolicitud(Constant.TIPO_SON_HHPPUNI);
            solicitud.setSonEstadoUni(Constant.SON_HHPPUNI_ESTADOUNI);
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA)) {
            solicitud.setSonEstadoUni(Constant.SON_VERCASA_ESTADOUNI);
            solicitud.setSonTipoSolicitud(Constant.TIPO_SON_VERCASA);
        }
        if (addressGeodata.getEstrato() == null || addressGeodata.getEstrato().equals("")) {
            if (addressGeodata.getNivsocio() != null && !addressGeodata.getNivsocio().equals("")) {
                solicitud.setSonEstrato(addressGeodata.getNivsocio());
            }
        } else {
            solicitud.setSonEstrato(addressGeodata.getEstrato());
        }
        solicitud.setSonNivSocioeconomico(addressGeodata.getNivsocio());
        solicitud.setSonDiralterna(addressGeodata.getDiralterna());
        solicitud.setSonMz(addressGeodata.getManzana());
        solicitud.setSonLongitud(addressGeodata.getCx());
        solicitud.setSonLatitud(addressGeodata.getCy());
        if (addressGeodata.getValplaca() != null && !addressGeodata.getValplaca().equals("")) {
            confiabilidad = new BigDecimal(addressGeodata.getValplaca());
        }
        solicitud.setSonConfiabilidad(confiabilidad);

        solicitud.setSonManzanaCatastral(addressGeodata.getManzana());
        solicitud.setSonActividadEconomica(addressGeodata.getActeconomica());
        solicitud.setSonFuente(addressGeodata.getFuente());
        String ladoMz = buildLadoMz();
        solicitud.setSonLadomz(ladoMz);
        solicitud.setSonAreaSolicitante(son_area);
        solicitud.setSonNodoUsuario(codigo_nodo);
        solicitud.setSonHeadEnd(nodo.getNodHeadend());
        solicitud.setSonCodSolicitante(cod_solicitante);
        solicitud.setSonEmailSolicitante(email_solicitante);
        solicitud.setSonCelSolicitante(cel_solicitante);
        solicitud.setTipoHhpp(tHhpp);
        solicitud.setNodo(nodo);
        if (nodo.getComunidad() != null) {
            solicitud.setSonZipcode(nodo.getComunidad().getComZipcode());
        }
        city.setGeograficoPolitico(state);
        solicitud.setGeograficoPolitico(city);
        solicitud.setGeografico(geografico);
        GeoreferenciaUtils geoUtils = new GeoreferenciaUtils();
        boolean dirExiste = geoUtils.validExistAddress(addressGeodata.getDirtrad(), addressGeodata.getEstado(), addressGeodata.getValagreg());
        if (dirExiste) {
            solicitud.setSonDirExiste("true");
            solicitud.setSonDirValidada("true");
            solicitud.setSonEstado(Constant.ESTADO_SON_PROCESADA);
            solicitud.setSonTipoSolucion(Constant.SON_TIPO_SOL_HHPPCREADO);
            try {
                //TODO: Modificar estos valores por los valores defecto que defina el cliente
                tipoRed = ConsultasDelegate.queryTipoHhppRedByCodigo(nodo.getNodTipo().toString());
                tipoConexion = ConsultasDelegate.queryTipoHhppConexion(ID_THC);
            } catch (ApplicationException e) {
                LOGGER.error("Error en createSolicitud. " + e.getMessage(), e);
            }
            solicitud.setTipoHhppConexion(tipoConexion);
            solicitud.setTipoHhppRed(tipoRed);
        } else {
            solicitud.setSonDirExiste("false");
            solicitud.setSonDirValidada("false");
            solicitud.setSonEstado(Constant.ESTADO_SON_CREADA);
        }
    }

    /**
     * Aplicar logica de nodos para construir el lado manzana
     *
     * @return cadena descriptiva de lado manzana casa
     */
    public String buildLadoMz() {
        String codNodo1 = addressGeodata.getNodo1();
        String codNodo2 = addressGeodata.getNodo2();
        String codNodo3 = addressGeodata.getNodo3();
        String ladoMz = "";
        Nodo nodoBi = null;
        Nodo nodoUni = null;
        Nodo nodoUni2 = null;
        if (!codNodo1.isEmpty()) {
            try {
                nodo1 = SolicitudNegocioDelegate.queryNodo(codNodo1, city.getGpoId());
            } catch (ApplicationException e) {
                LOGGER.error("Error en buildLadoMz. " + e.getMessage(), e);
            }
        }
        if (!codNodo2.isEmpty()) {
            try {
                nodo2 = SolicitudNegocioDelegate.queryNodo(codNodo2, city.getGpoId());
            } catch (ApplicationException e) {
                LOGGER.error("Error en buildLadoMz. " + e.getMessage(), e);

            }
        }
        if (!codNodo3.isEmpty()) {
            try {
                nodo3 = SolicitudNegocioDelegate.queryNodo(codNodo3, city.getGpoId());
            } catch (ApplicationException e) {
                LOGGER.error("Error en isEmpty. " + e.getMessage(), e);

            }
        }

        if ((nodo1 != null && nodo2 != null && nodo3 != null) && son_tipoSolicitud.equals(Constant.TIPO_SON_HHPPUNI)) {
            if (nodo1.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                nodoBi = nodo1;
            } else if (nodo2.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                nodoBi = nodo2;
            } else if (nodo3.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                nodoBi = nodo3;
            }
            if (nodoBi != null) {
                if (nodo2.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_UNIDIRECCIONAL)) {
                    nodoUni = nodo2;
                } else if (nodoUni == null) {
                    if (nodo3.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_UNIDIRECCIONAL)) {
                        nodoUni = nodo3;
                    }
                }
            }
            if (nodoBi != null) {
                if (nodoUni != null) {
                    if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                        ladoMz = nodoBi.getNodCodigo() + " - " + nodoUni.getNodCodigo();
                    } else {
                        if (nodoUni2 == null) {
                            if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.ZBO)) {
                                ladoMz = nodoBi.getNodCodigo() + " - " + "NG" + " - " + nodoUni.getNodCodigo();
                            } else if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.SPV)) {
                                ladoMz = nodoBi.getNodCodigo() + " - " + nodoUni.getNodCodigo() + " - " + "NG";
                            }
                        }
                    }
                } else {
                    if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                        ladoMz = nodoBi.getNodCodigo() + " - " + "NG";
                    } else {
                        if (nodoUni2 == null) {
                            if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.ZBO)) {
                                ladoMz = nodoBi.getNodCodigo() + " - " + "NG" + " - " + "NG";
                            } else if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.SPV)) {
                                ladoMz = nodoBi.getNodCodigo() + " - " + "NG" + " - " + "NG";
                            }
                        }
                    }
                }
            } else if (nodoUni != null) {
                if (nodo.getGeograficoPolitico().getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                    ladoMz = "NG - " + nodoUni.getNodCodigo();
                } else {
                    if (nodoUni2 == null) {
                        ladoMz = "NG - NG - NG";
                    }
                }
            }
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERCASA)) {
            ladoMz = nodo.getNodCodigo();
        } else {
            ladoMz = "NG - NG - NG";
        }
        return ladoMz;
    }

    /*
     * Valida la direccion ingresada por el usuario
     */
    private void addressValidator(String direccionNostandar) {
        try {
            address = new AddressRequest();
            address.setAddress(direccionNostandar);
            address.setNeighborhood(son_barrio);
            address.setCity(city.getGpoNombre());
            address.setState(state.getGpoNombre());
            addressResult = null;

            addressGeodata = GeoreferenciaDelegate.queryAddressGeodata(address);
            GeoreferenciaUtils geoUtils = new GeoreferenciaUtils();
            if (addressGeodata != null) {
                if (!geoUtils.validExistAddress(addressGeodata.getDirtrad(), addressGeodata.getEstado(), addressGeodata.getValagreg())) {
                    message = "La dirección: " + direccionNostandar + ", No existe";
                }
            }
        } catch (ApplicationException ex) {
            message = "Error el realizar Georreferenciación";
            LOGGER.error("Error eal validar dirección. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: addressValidator()" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Validar la existencia de Localidad, Barrio Manzana en Repositorio
     */
    public void validadorExistenciaGeografico() {
        //Se valida cual geo_id se debe asociar a la ubicación
        if (!addressGeodata.getLocalidad().isEmpty()) {
            geografico = new Geografico();
            BigDecimal idLocalidad = BigDecimal.ZERO;
            BigDecimal geo_id = BigDecimal.ZERO;
            BigDecimal id_barrio = BigDecimal.ZERO;
            BigDecimal id_manzana = BigDecimal.ZERO;
            try {
                //Se consulta el geografico por localidad
                idLocalidad = GeoreferenciaDelegate.queryGeograficoLocalidadByNombre(addressGeodata.getLocalidad());
            } catch (ApplicationException e) {
                LOGGER.error("Error al validad la existencia de Localidad, Barrio Manzana . EX000 " + e.getMessage(), e);
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
            }

            if (!idLocalidad.equals(BigDecimal.ZERO)) {
                geo_id = idLocalidad;
            } else {
                //Se debe crear la localidad
                Geografico geo = new Geografico();
                geo.setGeoNombre(addressGeodata.getLocalidad());
                TipoGeografico tipoGeo = new TipoGeografico();
                tipoGeo.setTgeId(Constant.ID_TGE_LOCALIDAD);
                geo.setTipoGeografico(tipoGeo);
                GeograficoPolitico gpolitico = new GeograficoPolitico();
                gpolitico.setGpoId(city.getGpoId());
                geo.setGeograficoPolitico(gpolitico);
                geo.setGeoUsuarioCreacion(user.getUsuLogin());
                try {
                    //Se debe crear la localidad
                    idLocalidad = GeoreferenciaDelegate.insertGeograficoIntorepository(NOMBRE_FUNCIONALIDAD, geo);
                } catch (ApplicationException ex) {
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                } catch (Exception ex) {
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                }
                if (idLocalidad != null) {
                    geo_id = idLocalidad;
                }
            }

            if (!addressGeodata.getBarrio().isEmpty()) {
                try {
                    id_barrio = GeoreferenciaDelegate.queryGeograficoBarrioByIDLocalidad(idLocalidad.toString(), addressGeodata.getBarrio());
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                }
                if (!id_barrio.equals(BigDecimal.ZERO)) {
                    geo_id = id_barrio;
                } else {
                    //Se debe crear el barrio
                    Geografico geo = new Geografico();
                    geo.setGeoNombre(addressGeodata.getBarrio());
                    TipoGeografico tipoGeo = new TipoGeografico();
                    tipoGeo.setTgeId(Constant.ID_TGE_BARRIO);
                    geo.setTipoGeografico(tipoGeo);
                    Geografico geoLocalidad = new Geografico();
                    geoLocalidad.setGeoId(idLocalidad);
                    geo.setGeografico(geoLocalidad);
                    geo.setGeograficoPolitico(city);
                    geo.setGeoUsuarioCreacion(user.getUsuLogin());
                    try {
                        //Se debe crear el barrio
                        id_barrio = GeoreferenciaDelegate.insertGeograficoIntorepository(NOMBRE_FUNCIONALIDAD, geo);
                    } catch (ApplicationException e) {
                        FacesUtil.mostrarMensajeError("Error en validadorExistenciaGeografico()" + e.getMessage(), e, LOGGER);
                    } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error en validadorExistenciaGeografico()" + e.getMessage(), e, LOGGER);
                    }
                    if (!id_barrio.equals(BigDecimal.ZERO)) {
                        geo_id = id_barrio;
                    }
                }
            } else {
                //Si el barrio no viene informado se pasa el idlocalidad
                geo_id = idLocalidad;
            }

            if (addressGeodata.getCoddir().length() == 99) {
                //Es una direccion manzana casa
                String manzana = getAppletFromAddress(addressGeodata.getCoddir());
                try {
                    id_manzana = GeoreferenciaDelegate.queryGeograficoMzaByIdBarrio(id_barrio.toString(), manzana);
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
                }

                if (!id_manzana.equals(BigDecimal.ZERO)) {
                    geo_id = id_manzana;
                } else {

                    //Se debe crear LA MZ
                    Geografico geo = new Geografico();
                    geo.setGeoNombre(manzana);
                    TipoGeografico tipoGeo = new TipoGeografico();
                    tipoGeo.setTgeId(Constant.ID_TGE_MANZANA);
                    geo.setTipoGeografico(tipoGeo);
                    Geografico geoBarrio = new Geografico();
                    geoBarrio.setGeoId(id_barrio);
                    geo.setGeografico(geoBarrio);
                    geo.setGeograficoPolitico(city);
                    geo.setGeoUsuarioCreacion(user.getUsuLogin());
                    try {
                        //Se debe crear la manzana
                        id_manzana = GeoreferenciaDelegate.insertGeograficoIntorepository(NOMBRE_FUNCIONALIDAD, geo);
                    } catch (ApplicationException e) {
                        LOGGER.error("Error al validad existencia geográfico. EX000 " + e.getMessage(), e);
                    }
                    if (!id_manzana.equals(BigDecimal.ZERO)) {
                        geo_id = id_manzana;
                    }
                }
            } else {
                geo_id = id_barrio;
            }
            //Se encontro un geografico
            geografico.setGeoId(geo_id);
            //}
        } else {
            geografico = null;
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
            if (sol.getMulValor().equals(son_tipoSolicitud)) {
                soli = sol;
                i = tSolicitudes.size();
            }
        }
        return soli;
    }

    /**
     * Asigna el nodo bidireccional retornado por el georreferenciador
     */
    private void loadNodo() {
        String nodo1Cod = addressGeodata.getNodo1();
        String nodo2Cod = addressGeodata.getNodo2();
        String nodo3Cod = addressGeodata.getNodo3();

        try {
            if (!nodo1Cod.isEmpty()) {
                nodo1 = SolicitudNegocioDelegate.queryNodo(nodo1Cod, city.getGpoId());
            }
            if (!nodo2Cod.isEmpty()) {
                nodo2 = SolicitudNegocioDelegate.queryNodo(nodo2Cod, city.getGpoId());
            }
            if (!nodo3Cod.isEmpty()) {
                nodo3 = SolicitudNegocioDelegate.queryNodo(nodo3Cod, city.getGpoId());
            }
        } catch (ApplicationException ex) {
            message = "Excepcionó en metodo loadNodo con mensaje de Excepcion:" + ex.getMessage();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
        }
        if (nodo1 != null && nodo2 != null && nodo3 != null) {
            if (city.getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                if (nodo1 != null) {
                    if (nodo1.getComunidad().getComNombre().equalsIgnoreCase(Constant.ZBO)) {
                        nodo = nodo1;
                    }
                } else if (nodo2 != null) {
                    if (nodo2.getComunidad().getComNombre().equalsIgnoreCase(Constant.ZBO)) {
                        nodo = nodo2;
                    }
                } else if (nodo3 != null) {
                    if (nodo3.getComunidad().getComNombre().equalsIgnoreCase(Constant.ZBO)) {
                        nodo = nodo3;
                    }
                }
            } else if (nodo == null && city.getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                if (nodo1 != null || nodo2 != null || nodo3 != null) {
                    if (nodo1 != null && nodo == null) {
                        if (nodo1.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                            nodo = nodo1;
                        }
                    } else if (nodo2 != null && nodo == null) {
                        if (nodo2.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                            nodo = nodo2;
                        }
                    } else if (nodo3 != null && nodo == null) {
                        if (nodo3.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_BIDIRECCIONAL)) {
                            nodo = nodo3;
                        }
                    } else if ((nodo1 != null && nodo2 != null && nodo3 != null) && nodo == null) {
                        if (nodo1.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_UNIDIRECCIONAL)
                                || nodo2.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_UNIDIRECCIONAL)
                                || nodo3.getNodTipo().toString().equalsIgnoreCase(Constant.NODO_UNIDIRECCIONAL)) {
                            if (nodo1.getNodCodigo().equals(codigo_nodo) && nodo == null) {
                                nodo = nodo1;
                            } else if (nodo2.getNodCodigo().equals(codigo_nodo) && nodo == null) {
                                nodo = nodo2;
                            } else if (nodo3.getNodCodigo().equals(codigo_nodo) && nodo == null) {
                                nodo = nodo3;
                            }
                        }
                    }
                } else if (nodo1 == null && nodo2 == null && nodo3 == null) {
                    try {
                        nodo = SolicitudNegocioDelegate.queryNodoNFI(city.getGpoId());
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
                    } catch (Exception ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
                    }
                }
                if (nodo == null) {
                    message = "No hay un nodo valido para la ciudad seleccionada.";
                }
            } else if (!city.getGpoNombre().equalsIgnoreCase(Constant.BOGOTA)) {
                if (nodo1 != null) {
                    nodo = nodo1;
                } else if (nodo2 != null && nodo == null) {
                    nodo = nodo2;
                } else if (nodo3 != null && nodo == null) {
                    nodo = nodo3;
                }
            }
        } else if (nodo == null) {
            try {
                nodo = SolicitudNegocioDelegate.queryNodoNFI(city.getGpoId());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
            }
            if (nodo == null) {
                message = "No hay un nodo valido para la ciudad seleccionada.";
            }
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
    private GeograficoPolitico loadRegion() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < regionales.size(); i++) {
            GeograficoPolitico region = regionales.get(i);
            if (region.getGpoId().equals(new BigDecimal(this.son_regional))) {
                geo = region;
            }
        }
        return geo;
    }

    /**
     *
     * @return
     */
    private TipoHhpp loadTipoHhpp() {
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
     * Cargar tiop de calle seleccionada por el usuario
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
     * Cargar tipo de Cardinalidad seleccionada por el usuario
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
     * Obtener la maz de una direccion partiendo de el codigo de direccion
     * entregado por servinformacion
     *
     * @param address codigo de direccion entregado por servinformacion
     * @return Manzana de la direccion
     */
    private String getAppletFromAddress(String address) {
        String applet = "";
        if (address != null && address.length() == 99) {
            try {
                applet = "MZ" + address.substring(21, 27);
            } catch (NumberFormatException e) {
                applet = "";
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudNegocioMBean: loadNodo()" + ex.getMessage(), ex, LOGGER);
            }
        }
        return applet;
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
    public boolean isShowBotonTS() {
        return showBotonTS;
    }

    /**
     *
     * @param showBotonTS
     */
    public void setShowBotonTS(boolean showBotonTS) {
        this.showBotonTS = showBotonTS;
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
    public List<SelectItem> getListAptos() {
        return listAptos;
    }

    /**
     *
     * @param listAptos
     */
    public void setListAptos(List<SelectItem> listAptos) {
        this.listAptos = listAptos;
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
    public String getSon_letraPlaca2() {
        return son_letraPlaca2;
    }

    /**
     *
     * @param son_letraPlaca2
     */
    public void setSon_letraPlaca2(String son_letraPlaca2) {
        this.son_letraPlaca2 = son_letraPlaca2;
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
    public String getCodigo_nodo() {
        return codigo_nodo;
    }

    /**
     *
     * @param codigo_nodo
     */
    public void setCodigo_nodo(String codigo_nodo) {
        this.codigo_nodo = codigo_nodo;
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
    public String getIdSol() {
        return idSol;
    }

    /**
     *
     * @param idSol
     */
    public void setIdSol(String idSol) {
        this.idSol = idSol;
    }

    /**
     *
     * @return
     */
    public boolean isShowUpdate() {
        return showUpdate;
    }

    /**
     *
     * @param showUpdate
     */
    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    /**
     *
     * @return
     */
    public boolean isHideQuery() {
        return hideQuery;
    }

    /**
     *
     * @param hideQuery
     */
    public void setHideQuery(boolean hideQuery) {
        this.hideQuery = hideQuery;
    }

    /**
     *
     * @return
     */
    public boolean isDisableCancel() {
        return disableCancel;
    }

    /**
     *
     * @param disableCancel
     */
    public void setDisableCancel(boolean disableCancel) {
        this.disableCancel = disableCancel;
    }

    /**
     *
     * @return
     */
    public boolean isDisableTS() {
        return disableTS;
    }

    /**
     *
     * @param disableTS
     */
    public void setDisableTS(boolean disableTS) {
        this.disableTS = disableTS;
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
    public AddressService getAddressResult() {
        return addressResult;
    }

    /**
     *
     * @param addressResult
     */
    public void setAddressResult(AddressService addressResult) {
        this.addressResult = addressResult;
    }

    /**
     *
     * @return
     */
    public boolean isShowPopUpOk() {
        return showPopUpOk;
    }

    /**
     *
     * @param showPopUpOk
     */
    public void setShowPopUpOk(boolean showPopUpOk) {
        this.showPopUpOk = showPopUpOk;
    }

    /**
     *
     * @return
     */
    public boolean isSpDirAlter() {
        return spDirAlter;
    }

    /**
     *
     * @param spDirAlter
     */
    public void setSpDirAlter(boolean spDirAlter) {
        this.spDirAlter = spDirAlter;
    }

    /**
     *
     * @return
     */
    public boolean isSpDirNoExiste() {
        return spDirNoExiste;
    }

    /**
     *
     * @param spDirNoExiste
     */
    public void setSpDirNoExiste(boolean spDirNoExiste) {
        this.spDirNoExiste = spDirNoExiste;
    }

    /**
     *
     * @return
     */
    public boolean isValidadoOk() {
        return validadoOk;
    }

    /**
     *
     * @param validadoOk
     */
    public void setValidadoOk(boolean validadoOk) {
        this.validadoOk = validadoOk;
    }

    /**
     *
     * @return
     */
    public Boolean getShowPopUpOk() {
        return showPopUpOk;
    }

    /**
     *
     * @param showPopUpOk
     */
    public void setShowPopUpOk(Boolean showPopUpOk) {
        this.showPopUpOk = showPopUpOk;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getLstTDirecciones() {
        return lstTDirecciones;
    }

    /**
     *
     * @param lstTDirecciones
     */
    public void setLstTDirecciones(List<SelectItem> lstTDirecciones) {
        this.lstTDirecciones = lstTDirecciones;
    }

    /**
     *
     * @return
     */
    public String getSon_tDireccion() {
        return son_tDireccion;
    }

    /**
     *
     * @param son_tDireccion
     */
    public void setSon_tDireccion(String son_tDireccion) {
        this.son_tDireccion = son_tDireccion;
    }

    /**
     *
     * @return
     */
    public String getSon_dirNoEstandarizable() {
        return son_dirNoEstandarizable;
    }

    /**
     *
     * @param son_dirNoEstandarizable
     */
    public void setSon_dirNoEstandarizable(String son_dirNoEstandarizable) {
        this.son_dirNoEstandarizable = son_dirNoEstandarizable;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListAreas() {
        return listAreas;
    }

    /**
     *
     * @param listAreas
     */
    public void setListAreas(List<SelectItem> listAreas) {
        this.listAreas = listAreas;
    }

    /**
     *
     * @return
     */
    public String getSon_area() {
        return son_area;
    }

    /**
     *
     * @param son_area
     */
    public void setSon_area(String son_area) {
        this.son_area = son_area;
    }

    /**
     *
     * @return
     */
    public String getMessagePopup() {
        return messagePopup;
    }

    /**
     *
     * @param messagePopup
     */
    public void setMessagePopup(String messagePopup) {
        this.messagePopup = messagePopup;
    }

    /**
     *
     * @return
     */
    public boolean isShowSave() {
        return showSave;
    }

    /**
     *
     * @param showSave
     */
    public void setShowSave(boolean showSave) {
        this.showSave = showSave;
    }

    /**
     *
     * @return
     */
    public boolean isShowDirHpUni() {
        return showDirHpUni;
    }

    /**
     *
     * @param showDirHpUni
     */
    public void setShowDirHpUni(boolean showDirHpUni) {
        this.showDirHpUni = showDirHpUni;
    }

    /**
     *
     * @return
     */
    public boolean isShowDirHpUniNoStand() {
        return showDirHpUniNoStand;
    }

    /**
     *
     * @param showDirHpUniNoStand
     */
    public void setShowDirHpUniNoStand(boolean showDirHpUniNoStand) {
        this.showDirHpUniNoStand = showDirHpUniNoStand;
    }

    /**
     *
     * @return
     */
    public boolean isShowNoStandar() {
        return showNoStandar;
    }

    /**
     *
     * @param showNoStandar
     */
    public void setShowNoStandar(boolean showNoStandar) {
        this.showNoStandar = showNoStandar;
    }

    /**
     *
     * @return
     */
    public boolean isDisableAceptar() {
        return disableAceptar;
    }

    /**
     *
     * @param disableAceptar
     */
    public void setDisableAceptar(boolean disableAceptar) {
        this.disableAceptar = disableAceptar;
    }

    /**
     *
     * @return
     */
    public boolean isRenderedBotones() {
        return renderedBotones;
    }

    /**
     *
     * @param renderedBotones
     */
    public void setRenderedBotones(boolean renderedBotones) {
        this.renderedBotones = renderedBotones;
    }

    /**
     *
     * @return
     */
    public boolean isShowDirCasas() {
        return showDirCasas;
    }

    /**
     *
     * @param showDirCasas
     */
    public void setShowDirCasas(boolean showDirCasas) {
        this.showDirCasas = showDirCasas;
    }

    /**
     *
     * @return
     */
    public boolean isShowDirCasasNoStand() {
        return showDirCasasNoStand;
    }

    /**
     *
     * @param showDirCasasNoStand
     */
    public void setShowDirCasasNoStand(boolean showDirCasasNoStand) {
        this.showDirCasasNoStand = showDirCasasNoStand;
    }

    /**
     *
     * @return
     */
    public int getContador() {
        return contador;
    }

    /**
     *
     * @param contador
     */
    public void setContador(int contador) {
        this.contador = contador;
    }

}
