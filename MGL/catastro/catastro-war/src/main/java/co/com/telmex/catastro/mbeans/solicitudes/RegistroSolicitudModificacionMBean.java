package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.delegate.ConsultasDelegate;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.HhppDelegate;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase RegistroSolicitudModificacionMBean
 * Extiende de BaseMBean
 * manipular información necesario para crear una solicitud de modificación de Hhpp
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "solicitudModificacionMBean")
public class RegistroSolicitudModificacionMBean extends BaseMBean implements Serializable {
    
    private static final Logger LOGGER = LogManager.getLogger(RegistroSolicitudModificacionMBean.class);

    //CADENAS
    private static String NOMBRE_FUNCIONALIDAD = "REGISTRO SOLICITUD MODIFICACION HHPP";
    private String nombreLog;
    private String DOMINIO = "";
    /**
     * 
     */
    public String son_tipoSolicitud = "";
    /**
     * 
     */
    public String son_tDireccion = "";
    /**
     * 
     */
    public String seleccionar = "";
    /**
     * 
     */
    public String son_pais = "";
    /**
     * 
     */
    public String son_depto = "";
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
    public String son_dirNoEstandarizable = "";
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
    public String son_letraCalle = "";
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
    public String son_cardinalCalle = "";
    /**
     * 
     */
    public String cardinalCalle = "";
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
    public String direccionNostandar = "";
    /**
     * 
     */
    public String newDirNoStandar = "";
    /**
     * 
     */
    public String sonMotivo = "";
    /**
     * 
     */
    public String sonCodSolicitante = "";
    /**
     * 
     */
    public String sonNomSolicitante = "";
    /**
     * 
     */
    public String sonEmailSolicitante = "";
    /**
     * 
     */
    public String sonCelSolicitante = "";
    /**
     * 
     */
    public String sonContacto = "";
    /**
     * 
     */
    public String sonTelContacto = "";
    /**
     * 
     */
    public String observaciones = "";
    /**
     * 
     */
    public String son_nuevoNodo = "";
    /**
     * 
     */
    public String son_nuevoEstrato = "";
    /**
     * 
     */
    public String son_nuevoEstado = "";
    /**
     * 
     */
    public String son_area = "";
    /**
     * 
     */
    public String dir_estrato = "";

    private BigDecimal idSon = BigDecimal.ZERO;
    //Enteros
    /**
     * 
     */
    public int contador = 2;
    //LISTAS
    /**
     * 
     */
    public List<SelectItem> lstTSolicitud = null;
    private List<Multivalor> tSolicitudes = null;
    /**
     * 
     */
    public List<SelectItem> lstTDirecciones = null;
    /**
     * 
     */
    public List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> paises;
    /**
     * 
     */
    public List<SelectItem> listDeptos = null;
    private List<GeograficoPolitico> departamentos = null;
    /**
     * 
     */
    public List<SelectItem> listCiudades = null;
    private List<GeograficoPolitico> ciudades = null;
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
    public List<SelectItem> listCardinales = null;
    private List<Multivalor> cardinales = null;
    private List<SelectItem> listEstados = null;
    /**
     * 
     */
    public List<SelectItem> listAreas = null;
    private List<Multivalor> areas = null;
    // VARIABLES BOOLEAN
    /**
     * 
     */
    public boolean disableTS = false;
    /**
     * 
     */
    public boolean renderedAceptar = true;
    /**
     * 
     */
    public boolean showDirNoStand = false;
    /**
     * 
     */
    public boolean showDirEstan = false;
    /**
     * 
     */
    public boolean showConsultar = false;
    /**
     * 
     */
    public boolean renderedPais = false;
    /**
     * 
     */
    public boolean showHhpp = false;
    /**
     * 
     */
    public boolean activarSolicitante = false;
    /**
     * 
     */
    public boolean showGrid = false;
    /**
     * 
     */
    public boolean showNodo = false;
    /**
     * 
     */
    public boolean showEstrato = false;
    /**
     * 
     */
    public boolean showEstado = false;
    /**
     * 
     */
    public boolean showHpDirNS = false;
    /**
     * 
     */
    public boolean showHpDirEstan = false;
    /**
     * 
     */
    public boolean showGuardar = false;
    /**
     * 
     */
    public boolean showPopUpOk = false;
    //ENTIDADES
    /**
     * 
     */
    public GeograficoPolitico city = null;
    private GeograficoPolitico state = null;
    private AddressRequest address = null;
    private AddressGeodata addressGeodata = null;
    /**
     * 
     */
    public Geografico barrio = null;
    /**
     * 
     */
    public Hhpp hhpp = null;
    /**
     * 
     */
    public Comunidad comunidad = null;
    /**
     * 
     */
    public Nodo nodo_nuevo = null;
    private Nodo nodo = null;
    private Nodo nodo1 = null;
    private Nodo nodo2 = null;
    private Nodo nodo3 = null;
    /**
     * 
     */
    public SolicitudNegocio solicitud = null;

    /**
     * 
     * @throws IOException
     */
    public RegistroSolicitudModificacionMBean()  {
        super();
        try {
            initVariables();
            initListBegin();
            initLists();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * 
     */
    private void initVariables() {
        son_tipoSolicitud = "";
        son_tDireccion = "";
        seleccionar = "";
        son_pais = "";
        son_depto = "";
        son_ciudad = "";
        son_barrio = "";
        son_dirNoEstandarizable = "";
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
        direccionNostandar = "";
        newDirNoStandar = "";
        sonTelContacto = "";
        sonContacto = "";
        sonNomSolicitante = "";
        sonCodSolicitante = "";
        sonCelSolicitante = "";
        sonEmailSolicitante = "";
        sonMotivo = "";
        observaciones = "";
        son_nuevoEstado = "";
        son_nuevoEstrato = "";
        son_nuevoNodo = "";
        son_area = "";
        dir_estrato = "";

        idSon = BigDecimal.ZERO;
        //LISTAS
        lstTSolicitud = null;
        tSolicitudes = null;
        lstTDirecciones = null;
        listPaises = null;
        listDeptos = null;
        departamentos = null;
        listCiudades = null;
        ciudades = null;
        listEstados = null;
        // VARIABLES BOOLEAN
        disableTS = false;
        renderedAceptar = true;
        showDirNoStand = false;
        showDirEstan = false;
        showConsultar = false;
        renderedPais = false;
        activarSolicitante = false;
        showHhpp = false;
        showGrid = false;
        showNodo = false;
        showEstrato = false;
        showEstado = false;
        showHpDirEstan = false;
        showHpDirNS = false;
        showGuardar = false;
        showPopUpOk = false;
        //ENTIDADES
        city = null;
        barrio = null;
        hhpp = null;
        comunidad = null;
        nodo_nuevo = null;
        solicitud = null;
        address = null;
        addressGeodata = null;
    }

    /**
     * 
     */
    private void initListBegin() {
        queryParametrosConfig();
        lstTSolicitud = new ArrayList<SelectItem>();
        try {
            //TODO modificar por carga de tipos solicitud Modificación validar si se puede modificar mul7 para q cargue segun modificación o creación.
            tSolicitudes = SolicitudNegocioDelegate.loadTiposSolicitudModificacion();
            for (Multivalor tsol : tSolicitudes) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor());
                item.setLabel(tsol.getDescripcion());
                lstTSolicitud.add(item);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: initListBegin()" + e.getMessage(), e, LOGGER);
        }

        lstTDirecciones = new ArrayList<SelectItem>();
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
            sonCodSolicitante = user.getUsuLogin();
            sonNomSolicitante = user.getUsuNombre() + " " + user.getUsuApellidos();
            sonEmailSolicitante = user.getUsuLogin() + DOMINIO;
        } else {
            sonEmailSolicitante = "ejemplo@ejemplo.com";
        }
    }

    /**
     * 
     */

    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();
        Parametros param = null;
        try {
            param = recursos.queryParametros(Constant.DOMINIO);
            if (param != null) {
                DOMINIO = param.getValor();
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }

    }

    /*Inicializa las listas necesarias para crear la solicitud de Negocio */
    private void initLists() {
        listPaises = new ArrayList<SelectItem>();
        listTCalles = new ArrayList<SelectItem>();
        listLetras = new ArrayList<SelectItem>();
        listPrefijos = new ArrayList<SelectItem>();
        listCardinales = new ArrayList<SelectItem>();
        listAreas = new ArrayList<SelectItem>();

        try {
            paises = SolicitudNegocioDelegate.queryPaises();
            tCalles = SolicitudNegocioDelegate.queryCalles();
            letras = SolicitudNegocioDelegate.queryLetras();
            prefijos = SolicitudNegocioDelegate.queryPrefijos();
            cardinales = SolicitudNegocioDelegate.queryCardinales();
            areas = SolicitudNegocioDelegate.loadMultivalores(Constant.GMULTI_AREA);

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
            for (Multivalor ar : areas) {
                SelectItem item = new SelectItem();
                item.setValue(ar.getMulValor());
                item.setLabel(ar.getMulValor());
                listAreas.add(item);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: initLists()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * 
     */
    public void doHabilitar() {
        message = "";
        if (!son_tDireccion.equals("0") && !son_tipoSolicitud.equals("0")) {
            renderedAceptar = false;
            disableTS = true;
            showConsultar = true;
            renderedPais = true;

            if (son_tDireccion.equals(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
                showDirNoStand = true;
            } else if (son_tDireccion.equals(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
                showDirEstan = true;
            }
        } else if (son_tipoSolicitud.equals("0")) {
            message = "Tipo de Solicitud invalido, debe seleccionar un tipo de solicitud para poder continuar.";
        } else if (son_tDireccion.equals("0")) {
            message = "Tipo de Dirección invalido, debe seleccionar un tipo de solicitud para poder continuar.";
        }
        if (!message.isEmpty()) {
            contador++;
        }
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
     * @param event
     * @throws ApplicationException 
     */
    public void updateRegionales(ValueChangeEvent event) {
        message = "";
        String value = event.getNewValue().toString();
        try {
            departamentos = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            listDeptos = new ArrayList<SelectItem>();
            if (departamentos != null) {
                for (GeograficoPolitico gpo : departamentos) {
                    SelectItem item = new SelectItem();
                    item.setValue(gpo.getGpoId().toString());
                    item.setLabel(gpo.getGpoNombre());
                    listDeptos.add(item);
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
         } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: updateRegionales()" + e.getMessage(), e, LOGGER);
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
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: updateCiudades()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * 
     * @return 
     */
    private GeograficoPolitico loadRegion() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < departamentos.size(); i++) {
            GeograficoPolitico region = departamentos.get(i);
            if (region.getGpoId().equals(new BigDecimal(this.son_depto))) {
                geo = region;
            }
        }
        return geo;
    }

    /**
     * consultar un Hhpp asociado a una dirección
     * 
     */
    public void queryHhpp() {
        message = "";
        try {
            message = fieldsValidator();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: queryHhpp()" + e.getMessage(), e, LOGGER);
        }
        if (message.isEmpty()) {
            try {
                city = loadCity();
                state = loadRegion();
                direccionNostandar = createDirNoStandar();
                addressValidator(direccionNostandar);
                hhpp = HhppDelegate.queryHhppByDireccion(direccionNostandar, addressGeodata);
                if (hhpp == null) {
                    if (addressGeodata.getDiralterna() != null) {
                        if (!son_complemento.equals("")) {
                            direccionNostandar = addressGeodata.getDiralterna() + " " + son_complemento + " AP " + son_apto;
                        } else {
                            direccionNostandar = addressGeodata.getDiralterna();
                        }
                        addressValidator(direccionNostandar);
                        hhpp = HhppDelegate.queryHhppByDireccion(direccionNostandar, addressGeodata);
                        if (hhpp == null) {
                            message = "No existe Hhpp para la dirección Ingresada. ";
                        }
                    } else {
                        message = "No existe Hhpp para la dirección Ingresada .";
                    }

                }
                if (hhpp != null) {
                    showHhpp = true;
                    showConsultar = false;
                    if (hhpp.getDireccion().getDirEstrato() != null) {
                        dir_estrato = hhpp.getDireccion().getDirEstrato().toString();
                    } else if (hhpp.getSubDireccion() != null) {
                        if (hhpp.getSubDireccion().getSdiEstrato() != null) {
                            dir_estrato = hhpp.getSubDireccion().getSdiEstrato().toString();
                        }
                    }
                    if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
                        if (!validarTipoDireccion().equalsIgnoreCase(Constant.DIR_INTRADUCIBLE)) {
                            showHpDirEstan = true;
                        } else {
                            showHpDirNS = true;
                        }
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_GRID)) {
                        comunidad = ConsultasDelegate.queryComunidad(hhpp.getNodo().getComunidad().getComId());
                        showGrid = true;
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIONOD)) {
                        showNodo = true;
                        message = "";
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIOEST) || son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
                        showEstrato = true;
                        message = "Para este tipo de modificación debe enviar correo al área HHPP indicando número de la solicitud y adjuntando un documento soporte con el estrato al que desea pasar el HHPP";
                    } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERIFICACION) || son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
                        try {
                            listEstados = HhppDelegate.queryEstadosFinales(hhpp.getEstadoHhpp().getEhhId().toString());
                            showEstado = true;
                        } catch (ApplicationException ex) {
                            message = "Error al cargar estados finales del estado actual de Hhpp";
                            LOGGER.error("Error al cargar estados finales del estado actual de Hhpp" + ex.getMessage(), ex);
                        }
                    }
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: queryHhpp()" + e.getMessage(), e, LOGGER);
            }
        }
    }

    /** Validar los campos necesarios para crear la solicitud de modificación de Hhpp solicitada por el usuario.
     */
    public void onActionValidar() {
        try {
            message = fieldsValidatorSolicitud();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: onActionValidar()" + e.getMessage(), e, LOGGER);
        }
        if (message.isEmpty()) {
            showPopUpOk = true;
            showGuardar = true;
        } else {
            contador++;
        }
    }

    /**
     * 
     */
    public void onActionCreate() {
        boolean valOk = true;
        try {
            message = fieldsValidatorSolicitud();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: onActionCreate()" + e.getMessage(), e, LOGGER);
        }
        if (message.isEmpty()) {
            try {
                if (addressGeodata != null) {
                    solicitud = new SolicitudNegocio();
                    if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIONOD)) {
                        if (nodo_nuevo == null) {
                            message = "No es posible crear solicitud con nodo no existente";
                            valOk = false;
                        }
                    }
                    if (valOk) {
                        createSolicitud();
                        idSon = SolicitudNegocioDelegate.insertSolicitudNegocio(NOMBRE_FUNCIONALIDAD, solicitud);
                        if (idSon.equals(BigDecimal.ZERO)) {
                            message = "Error al crear la solicitud";
                        } else {
                            message = "La solicitud fue creada con exito con el id:" + idSon;
                            initVariables();
                            initListBegin();
                            initLists();
                        }
                    } else {
                        message = "Error al crear la Solicitud";
                    }
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: onActionCreate()" + e.getMessage(), e, LOGGER);
            }
        }
    }

    /* Crea la solicitud de negocio con los valores estandarizados por el servicio y los valores ingresados por el usuario
     * 
     */
    private void createSolicitud() {
        solicitud = new SolicitudNegocio();
        BigDecimal confiabilidad = null;
        solicitud.setSonTipoSolicitud(son_tipoSolicitud);
        solicitud.setSonMotivo(sonMotivo);
        solicitud.setSonAreaSolicitante(son_area);
        solicitud.setSonNomSolicitante(sonNomSolicitante);
        solicitud.setSonContacto(sonContacto);
        solicitud.setSonTelContacto(sonTelContacto);
        solicitud.setSonUsuarioCreacion(user.getUsuNombre() + " " + user.getUsuApellidos());
        solicitud.setSonObservaciones(observaciones);
        solicitud.setSonFormatoIgac(addressGeodata.getDirtrad());
        solicitud.setSonServinformacion(addressGeodata.getCoddir());
        solicitud.setSonPlaca(addressGeodata.getCodencont());
        solicitud.setSonComplemento(addressGeodata.getCoddir().substring(addressGeodata.getCodencont().length()));
        solicitud.setSonNostandar(direccionNostandar);
        solicitud.setSonLocalidad(addressGeodata.getLocalidad());
        solicitud.setHhpp(hhpp);
        solicitud.setNodo(hhpp.getNodo());

        if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIOEST)) {
            solicitud.setSonEstrato(son_nuevoEstrato);
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERIFICACION)) {
            solicitud.setSonEstadoUni(son_nuevoEstado);
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIONOD)) {
            solicitud.setNodo(nodo_nuevo);
            solicitud.setSonNodoUsuario(son_nuevoNodo);
            solicitud.setSonHeadEnd(nodo_nuevo.getNodHeadend());
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
            solicitud.setSonNostandar(newDirNoStandar);
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
        solicitud.setSonCodSolicitante(sonCodSolicitante);
        solicitud.setSonEmailSolicitante(sonEmailSolicitante);
        solicitud.setSonCelSolicitante(sonCelSolicitante);
        solicitud.setTipoHhpp(hhpp.getTipoHhpp());
        solicitud.setTipoHhppConexion(hhpp.getTipoConexionHhpp());
        solicitud.setTipoHhppRed(hhpp.getTipoRedHhpp());
        solicitud.setSonEstado(Constant.ESTADO_SON_CREADA);
        if (comunidad != null) {
            solicitud.setSonZipcode(comunidad.getComZipcode());
        } else if (nodo_nuevo != null) {
            if (nodo_nuevo.getComunidad() != null) {
                solicitud.setSonZipcode(nodo_nuevo.getComunidad().getComZipcode());
            }
        }
        city.setGeograficoPolitico(state);
        solicitud.setGeograficoPolitico(city);
        GeoreferenciaUtils geoUtils = new GeoreferenciaUtils();
        boolean dirExiste = geoUtils.validExistAddress(addressGeodata.getDirtrad(), addressGeodata.getEstado(), addressGeodata.getValagreg());
        if (dirExiste) {
            solicitud.setSonDirExiste("true");
            solicitud.setSonDirValidada("true");
        } else {
            solicitud.setSonDirExiste("false");
            solicitud.setSonDirValidada("false");
        }
    }

    /**
     * 
     * @return
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
            } catch (ApplicationException ex) {
                LOGGER.error("Error al consulgtar el nodo 1" + ex.getMessage(), ex);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: buildLadoMz()" + e.getMessage(), e, LOGGER);
            }
        }
        if (!codNodo2.isEmpty()) {
            try {
                nodo2 = SolicitudNegocioDelegate.queryNodo(codNodo2, city.getGpoId());
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: buildLadoMz()" + e.getMessage(), e, LOGGER);
            }
        }
        if (!codNodo3.isEmpty()) {
            try {
                nodo3 = SolicitudNegocioDelegate.queryNodo(codNodo3, city.getGpoId());
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: buildLadoMz()" + e.getMessage(), e, LOGGER);
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
     * Hace las validaciones de los campos segun el tipo de solicitud
     */
    private String fieldsValidatorSolicitud() {
        message = "";
        String resultado = "";
        if (!son_tipoSolicitud.equals("")) {
            if (son_area.equals("0")) {
                resultado = createMessageRequieredField("Area Solicitante");
            } else if (sonCelSolicitante.isEmpty()) {
                resultado = createMessageRequieredField("Celular Solicitante");
            } else if (!sonCelSolicitante.isEmpty() && (notContainsNumbers(sonCelSolicitante) || containsLetters(sonCelSolicitante))) {
                resultado = createMessageWrongValueField("Celular Solicitante");
            } else if (sonContacto.isEmpty()) {
                resultado = createMessageRequieredField("Nombre Contacto");
            } else if (containsNumbers(sonContacto)) {
                resultado = createMessageWrongValueField("Nombre Contacto");
            } else if (sonContacto.length() < 2) {
                resultado = createMessageRequieredField("Nombre Contacto");
            } else if (sonContacto.length() > 50) {
                resultado = createMessageValueFieldExceedSize("Nombre Contacto");
            } else if (!sonTelContacto.isEmpty() && (notContainsNumbers(sonTelContacto) || containsLetters(sonTelContacto))) {
                resultado = createMessageWrongValueField("Telefono de contacto");
            } else if (sonTelContacto.length() > 20) {
                resultado = createMessageValueFieldExceedSize("Telefono Contacto");
            } else if (sonMotivo.isEmpty()) {
                resultado = createMessageRequieredField("Motivo");
            } else if (sonMotivo.length() > 256) {
                resultado = createMessageValueFieldExceedSize("Motivo");
            } else if (sonEmailSolicitante.length() > 100) {
                resultado = createMessageValueFieldExceedSize("Email Solicitante");
            } else if (sonCelSolicitante.length() > 30) {
                resultado = createMessageValueFieldExceedSize("Celular Solicitante");
            } else if (observaciones.isEmpty()) {
                resultado = createMessageRequieredField("observaciones");
            } else if (observaciones.length() > 256) {
                resultado = createMessageValueFieldExceedSize("Observaciones");
            } else {
                if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR)) {
                    resultado = validarDireccion();
                    if (resultado.isEmpty()) {
                        newDirNoStandar = createNewDirNoStandar();
                        addressValidator(newDirNoStandar);
                    }
                } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIOEST)) {
                    if (son_nuevoEstrato.equals("") || containsLetters(son_nuevoEstrato)) {
                        resultado = createMessageWrongValueField("Nuevo Estrato");
                    }
                } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIONOD)) {
                    if (!(son_nuevoNodo.isEmpty()) && son_nuevoNodo.length() < 6) {
                        resultado = createMessageWrongValueField("Nuevo Nodo");
                    } else {
                        try {
                            nodo_nuevo = SolicitudNegocioDelegate.queryNodo(son_nuevoNodo, city.getGpoId());
                            if (nodo_nuevo == null) {
                                resultado = "No existe nodo con código :" + son_nuevoNodo;
                            }
                        } catch (ApplicationException e) {
                            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                            LOGGER.error(msg);
                        } catch (Exception e) {
                            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                            LOGGER.error(msg);
                            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: fieldsValidatorSolicitud()" + e.getMessage(), e, LOGGER);
                        }
                    }
                } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_VERIFICACION)) {
                    if (son_nuevoEstado.equals("") || son_nuevoEstado.equals("0")) {
                        resultado = createMessageWrongValueField("Nuevo Estado");
                    }
                }
            }
            if (rol.getRolLdap().equalsIgnoreCase(Constant.ROL_CGV)) {
                if (sonEmailSolicitante.isEmpty()) {
                    resultado = createMessageRequieredField("Email Solicitante");
                } else if (!sonEmailSolicitante.isEmpty() && (sonEmailSolicitante.contains("ejemplo") || !sonEmailSolicitante.contains("@") || !sonEmailSolicitante.contains("."))) {
                    resultado = createMessageWrongValueField("Email Solicitante");
                } else if (sonCodSolicitante.isEmpty()) {
                    resultado = createMessageRequieredField("Codigo Solicitante");
                } else if (sonCodSolicitante.length() > 50) {
                    resultado = createMessageValueFieldExceedSize("Codigo Solicitante");
                } else if (sonNomSolicitante.isEmpty()) {
                    resultado = createMessageRequieredField("Nombre Solicitante");
                } else if (sonNomSolicitante.length() > 100) {
                    resultado = createMessageValueFieldExceedSize("Nombre Solicitante");
                }
            }

        } else if (son_tipoSolicitud.equals("")) {
            resultado = "Tipo de solicitud invalido";
        }
        return resultado;
    }

    /**
     * 
     * @return 
     */
    private String createNewDirNoStandar() {
        String direccion = "";
        if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR) && showHpDirEstan) {

            loadTipoCalle();
            direccion = tipocalle + " " + son_calle;
            if (son_letraCalle.equalsIgnoreCase("")) {
                son_letraCalle = "";
            } else if (!son_letraCalle.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraCalle;
            }
            if (son_prefijoCalle.equalsIgnoreCase("")) {
                son_prefijoCalle = "";
            } else if (!son_prefijoCalle.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_prefijoCalle;
            }
            if (son_letraCalle2.equalsIgnoreCase("")) {
                son_letraCalle2 = "";
            } else if (!son_letraCalle2.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraCalle2;
            }
            if ((loadCardinalidad(son_cardinalCalle)) != null) {
                cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
                direccion = direccion + " " + cardinalCalle;
            }
            direccion = direccion + " " + son_placa1;
            if (!son_letraPlaca.equalsIgnoreCase("") && !son_letraPlaca.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraPlaca;
            }
            if (!son_prefijoPlaca.equalsIgnoreCase("") && !son_prefijoPlaca.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_prefijoPlaca;
            }
            if ((loadCardinalidad(son_cardinalPlaca)) != null) {
                cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
                direccion = direccion + " " + cardinalPlaca;
            }
            direccion = direccion + " " + son_placa2;
            if (!son_complemento.equals("")) {
                direccion = direccion + " " + son_complemento;
            }
            if (!son_apto.equals("")) {
                if (!son_complemento.equals("")) {
                    direccion = direccion + son_apto;
                } else {
                    direccion = direccion + " AP " + son_apto;
                }
            }
        } else if (son_tipoSolicitud.equals(Constant.TIPO_SON_CAMBIODIR) && showHpDirNS) {
            direccion = newDirNoStandar.trim();
        }
        return direccion;
    }

    /*
     * Crea la direccion no estandarizada con los valores ingresados por el usuario
     */
    private String createDirNoStandar() {
        String direccion = "";
        if (son_tDireccion.equalsIgnoreCase(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
            loadTipoCalle();
            direccion = tipocalle + " " + son_calle;
            if (son_letraCalle.equalsIgnoreCase("")) {
                son_letraCalle = "";
            } else if (!son_letraCalle.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraCalle;
            }
            if (son_prefijoCalle.equalsIgnoreCase("")) {
                son_prefijoCalle = "";
            } else if (!son_prefijoCalle.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_prefijoCalle;
            }
            if (son_letraCalle2.equalsIgnoreCase("")) {
                son_letraCalle2 = "";
            } else if (!son_letraCalle2.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraCalle2;
            }
            if ((loadCardinalidad(son_cardinalCalle)) != null) {
                cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
                direccion = direccion + " " + cardinalCalle;
            }
            direccion = direccion + " " + son_placa1;
            if (!son_letraPlaca.equalsIgnoreCase("") && !son_letraPlaca.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_letraPlaca;
            }
            if (!son_prefijoPlaca.equalsIgnoreCase("") && !son_prefijoPlaca.equalsIgnoreCase("0")) {
                direccion = direccion + " " + son_prefijoPlaca;
            }
            if ((loadCardinalidad(son_cardinalPlaca)) != null) {
                cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
                direccion = direccion + " " + cardinalPlaca;
            }
            direccion = direccion + " " + son_placa2;
            if (!son_complemento.equals("")) {
                direccion = direccion + " " + son_complemento;
            }
            if (!son_apto.equals("")) {
                direccion = direccion + " AP " + son_apto;
            }
        } else if (son_tDireccion.equalsIgnoreCase(Constant.SON_TDIRECCION_NOESTANDARIZABLE)) {
            direccion = son_dirNoEstandarizable.trim();
        }
        return direccion;
    }

    /*
     * Valida la direccion ingresada por el usuario
     */
    private void addressValidator(String direccionNostandar) {
        try {
            addressGeodata = null;
            address = new AddressRequest();
            address.setAddress(direccionNostandar);
            address.setNeighborhood(son_barrio);
            address.setCity(city.getGpoNombre());
            address.setState(state.getGpoNombre());
            addressGeodata = GeoreferenciaDelegate.queryAddressGeodata(address);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: addressValidator()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * 
     * @return
     */
    public String validarTipoDireccion() {
        String codigoPlaca = addressGeodata.getCodencont();
        String codigoAddress = addressGeodata.getCoddir();
        String complemento = codigoAddress.substring(codigoPlaca.length());
        String tipoDireccion = "";
        if (complemento != null) {
            if (codigoPlaca.equals("") && codigoAddress.equals("") && complemento.equals("")) {
                tipoDireccion = Constant.DIR_INTRADUCIBLE;
            } else {
                if (isSoloCeros(complemento)) {
                    //Es una direccion y se debe crear en DIRECCION
                    tipoDireccion = Constant.DIRECCION;
                } else {
                    //Es una Subdireccion y se debe crear DIRECCION y SUBDIRECCION
                    tipoDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
                }
            }
        }
        return tipoDireccion;
    }

    /**
     * 
     * @param diferencia
     * @return 
     */
    private static boolean isSoloCeros(String diferencia) {
        for (int i = 0; i < diferencia.length(); i++) {
            if (diferencia.charAt(i) != '0') {
                return false;
            }
        }
        return true;
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
     * Cargar ciudad
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

    /*
     * Hace las validaciones de los campos segun el tipo de solicitud
     */
    private String fieldsValidator() {
        message = "";
        String resultado = "";
        try{
        if (son_pais.equals("0")) {
            resultado = createMessageRequieredField("País");
        } else if (son_depto.equals("0")) {
            resultado = createMessageRequieredField("Departamento");
        } else if (!son_depto.equals("0")) {
            if (son_ciudad.equals("0")) {
                resultado = createMessageRequieredField("Ciudad");
            }
        } else if (!son_ciudad.equals("0")) {
            city = loadCity();
            if (city.getGpoMultiorigen().equalsIgnoreCase("1") && son_barrio.isEmpty()) {
                resultado = createMessageRequieredField("Barrio");
            }
        }
        if (!son_pais.equals("0") && !son_depto.equals("0") && !son_ciudad.equals("0")) {
            if (son_tDireccion.equals(Constant.SON_TDIRECCION_ESTANDARIZABLE)) {
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
        }
        if (!resultado.equals("")) {
            contador++;
        }
        
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: fieldsValidator()" + e.getMessage(), e, LOGGER);
        }
        return resultado;
    }

    /**
     * 
     * @return
     */
    public String validarDireccion() {
        message = "";
        String resultado = "";
        if (!son_pais.equals("0") && !son_depto.equals("0") && !son_ciudad.equals("0")) {
            if (showHpDirEstan) {
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
                }
            } else if (showHpDirNS) {
                if (son_dirNoEstandarizable.isEmpty()) {
                    resultado = createMessageRequieredField(" Dirección ");
                } else if (son_dirNoEstandarizable.length() < 10) {
                    resultado = createMessageWrongValueField(" Dirección ");
                } else if (son_dirNoEstandarizable.length() > 256) {
                    resultado = createMessageValueFieldExceedSize(" Dirección ");
                }
            }
        }
        return resultado;
    }

    /**Cancelar el Registro de solicitud de creación de HHPP
     * @throws ApplicationException 
     */
    public void onActionCancel()  {
        try {
            initVariables();
            initListBegin();
            initLists();
            message = "";
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en RegistroSolicitudModificacionMBean: onActionCancel()" + e.getMessage(), e, LOGGER);
        }
    }
    /*
     * Crea un mensaje cuando los datos del Campo contienen valores no validos
     */

    private String createMessageValueFieldExceedSize(String nombreCampo) {
        String messageWV = nombreCampo + ", Excede el tamaño , por favor verifique.";
        return messageWV;
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
    public List<SelectItem> getListDeptos() {
        return listDeptos;
    }

    /**
     * 
     * @param listDeptos
     */
    public void setListDeptos(List<SelectItem> listDeptos) {
        this.listDeptos = listDeptos;
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
     * @return
     */
    public List<SelectItem> getListEstados() {
        return listEstados;
    }

    /**
     * 
     * @param listEstados
     */
    public void setListEstados(List<SelectItem> listEstados) {
        this.listEstados = listEstados;
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
    public String getSon_depto() {
        return son_depto;
    }

    /**
     * 
     * @param son_depto
     */
    public void setSon_depto(String son_depto) {
        this.son_depto = son_depto;
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
    public String getSonCelSolicitante() {
        return sonCelSolicitante;
    }

    /**
     * 
     * @param sonCelSolicitante
     */
    public void setSonCelSolicitante(String sonCelSolicitante) {
        this.sonCelSolicitante = sonCelSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonCodSolicitante() {
        return sonCodSolicitante;
    }

    /**
     * 
     * @param sonCodSolicitante
     */
    public void setSonCodSolicitante(String sonCodSolicitante) {
        this.sonCodSolicitante = sonCodSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonContacto() {
        return sonContacto;
    }

    /**
     * 
     * @param sonContacto
     */
    public void setSonContacto(String sonContacto) {
        this.sonContacto = sonContacto;
    }

    /**
     * 
     * @return
     */
    public String getSonEmailSolicitante() {
        return sonEmailSolicitante;
    }

    /**
     * 
     * @param sonEmailSolicitante
     */
    public void setSonEmailSolicitante(String sonEmailSolicitante) {
        this.sonEmailSolicitante = sonEmailSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonMotivo() {
        return sonMotivo;
    }

    /**
     * 
     * @param sonMotivo
     */
    public void setSonMotivo(String sonMotivo) {
        this.sonMotivo = sonMotivo;
    }

    /**
     * 
     * @return
     */
    public String getSonNomSolicitante() {
        return sonNomSolicitante;
    }

    /**
     * 
     * @param sonNomSolicitante
     */
    public void setSonNomSolicitante(String sonNomSolicitante) {
        this.sonNomSolicitante = sonNomSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonTelContacto() {
        return sonTelContacto;
    }

    /**
     * 
     * @param sonTelContacto
     */
    public void setSonTelContacto(String sonTelContacto) {
        this.sonTelContacto = sonTelContacto;
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
    public String getSon_nuevoEstado() {
        return son_nuevoEstado;
    }

    /**
     * 
     * @param son_nuevoEstado
     */
    public void setSon_nuevoEstado(String son_nuevoEstado) {
        this.son_nuevoEstado = son_nuevoEstado;
    }

    /**
     * 
     * @return
     */
    public String getSon_nuevoEstrato() {
        return son_nuevoEstrato;
    }

    /**
     * 
     * @param son_nuevoEstrato
     */
    public void setSon_nuevoEstrato(String son_nuevoEstrato) {
        this.son_nuevoEstrato = son_nuevoEstrato;
    }

    /**
     * 
     * @return
     */
    public String getSon_nuevoNodo() {
        return son_nuevoNodo;
    }

    /**
     * 
     * @param son_nuevoNodo
     */
    public void setSon_nuevoNodo(String son_nuevoNodo) {
        this.son_nuevoNodo = son_nuevoNodo;
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
    public String getDir_estrato() {
        return dir_estrato;
    }

    /**
     * 
     * @param dir_estrato
     */
    public void setDir_estrato(String dir_estrato) {
        this.dir_estrato = dir_estrato;
    }

    /**
     * 
     * @return
     */
    public String getNewDirNoStandar() {
        return newDirNoStandar;
    }

    /**
     * 
     * @param newDirNoStandar
     */
    public void setNewDirNoStandar(String newDirNoStandar) {
        this.newDirNoStandar = newDirNoStandar;
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
    public boolean isRenderedAceptar() {
        return renderedAceptar;
    }

    /**
     * 
     * @param renderedAceptar
     */
    public void setRenderedAceptar(boolean renderedAceptar) {
        this.renderedAceptar = renderedAceptar;
    }

    /**
     * 
     * @return
     */
    public boolean isShowDirNoStand() {
        return showDirNoStand;
    }

    /**
     * 
     * @param showDirNoStand
     */
    public void setShowDirNoStand(boolean showDirNoStand) {
        this.showDirNoStand = showDirNoStand;
    }

    /**
     * 
     * @return
     */
    public boolean isShowDirEstan() {
        return showDirEstan;
    }

    /**
     * 
     * @param showDirEstan
     */
    public void setShowDirEstan(boolean showDirEstan) {
        this.showDirEstan = showDirEstan;
    }

    /**
     * 
     * @return
     */
    public boolean isShowConsultar() {
        return showConsultar;
    }

    /**
     * 
     * @param showConsultar
     */
    public void setShowConsultar(boolean showConsultar) {
        this.showConsultar = showConsultar;
    }

    /**
     * 
     * @return
     */
    public boolean isRenderedPais() {
        return renderedPais;
    }

    /**
     * 
     * @param renderedPais
     */
    public void setRenderedPais(boolean renderedPais) {
        this.renderedPais = renderedPais;
    }

    /**
     * 
     * @return
     */
    public boolean isShowHhpp() {
        return showHhpp;
    }

    /**
     * 
     * @param showHhpp
     */
    public void setShowHhpp(boolean showHhpp) {
        this.showHhpp = showHhpp;
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
    public boolean isShowGrid() {
        return showGrid;
    }

    /**
     * 
     * @param showGrid
     */
    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    /**
     * 
     * @return
     */
    public boolean isShowEstado() {
        return showEstado;
    }

    /**
     * 
     * @param showEstado
     */
    public void setShowEstado(boolean showEstado) {
        this.showEstado = showEstado;
    }

    /**
     * 
     * @return
     */
    public boolean isShowEstrato() {
        return showEstrato;
    }

    /**
     * 
     * @param showEstrato
     */
    public void setShowEstrato(boolean showEstrato) {
        this.showEstrato = showEstrato;
    }

    /**
     * 
     * @return
     */
    public boolean isShowNodo() {
        return showNodo;
    }

    /**
     * 
     * @param showNodo
     */
    public void setShowNodo(boolean showNodo) {
        this.showNodo = showNodo;
    }

    /**
     * 
     * @return
     */
    public boolean isShowGuardar() {
        return showGuardar;
    }

    /**
     * 
     * @param showGuardar
     */
    public void setShowGuardar(boolean showGuardar) {
        this.showGuardar = showGuardar;
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
    public boolean isShowHpDirEstan() {
        return showHpDirEstan;
    }

    /**
     * 
     * @param showHpDirEstan
     */
    public void setShowHpDirEstan(boolean showHpDirEstan) {
        this.showHpDirEstan = showHpDirEstan;
    }

    /**
     * 
     * @return
     */
    public boolean isShowHpDirNS() {
        return showHpDirNS;
    }

    /**
     * 
     * @param showHpDirNS
     */
    public void setShowHpDirNS(boolean showHpDirNS) {
        this.showHpDirNS = showHpDirNS;
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

    /**
     * 
     * @return
     */
    public Hhpp getHhpp() {
        return hhpp;
    }

    /**
     * 
     * @param hhpp
     */
    public void setHhpp(Hhpp hhpp) {
        this.hhpp = hhpp;
    }

    /**
     * 
     * @return
     */
    public Comunidad getComunidad() {
        return comunidad;
    }

    /**
     * 
     * @param comunidad
     */
    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }
}