package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.EnvioCorreo;
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
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase GestionarSolicitudNegocioMBean Extiende de BaseMBean
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "gestionarSolicitudNegocioMBean")
public class GestionarSolicitudNegocioMBean extends BaseMBean {

    private static String NOMBRE_FUNCIONALIDAD = "GESTIONAR SOLICITUD DE NEGOCIO";
    private String DOMINIO = "";
    /**
     *
     */
    public String seleccionar = "";
    /**
     *
     */
    public String pais;
    /**
     *
     */
    public String son_tipoSolicitud = "";
    /**
     *
     */
    public String sonTipoSolicitud = "";
    /**
     *
     */
    public List<SelectItem> lstTSolicitud = null;
    private List<Multivalor> tSolicitudes = null;
    /**
     *
     */
    public List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> paises;
    /**
     *
     */
    public String regional;
    /**
     *
     */
    public List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> regionales = null;
    /**
     *
     */
    public String ciudad;
    /**
     *
     */
    public List<SelectItem> listCiudades = null;
    private List<GeograficoPolitico> ciudades = null;
    /**
     *
     */
    public boolean disableGeografico = false;
    /**
     *
     */
    public List<SolicitudNegocio> solicitudes = null;
    /**
     *
     */
    public boolean showTable = false;
    private BigDecimal solicitudid = BigDecimal.ZERO;
    private String solicituddir = "";
    private SolicitudNegocio solicitud = null;
    /**
     *
     */
    public List<SelectItem> lstTRespuesta = null;
    private List<Multivalor> tRespuestas = null;
    /**
     *
     */
    public List<SelectItem> listTred = null;
    private List<TipoHhppRed> tiposred = null;
    /**
     *
     */
    public List<SelectItem> listTconexion = null;
    private List<TipoHhppConexion> tiposconexion = null;
    /**
     *
     */
    public List<SelectItem> listTblackL = null;
    private List<Marcas> tiposBlackL = null;
    private BigDecimal sonId;
    private String sonBarrio;
    private String sonNodoCodigo;
    private Nodo nodo;
    private GeograficoPolitico geograficoPolitico;
    private String sonTipoSolucion;
    /**
     *
     */
    public String son_resGestion;
    /**
     *
     */
    public String son_tipo_red = "";
    /**
     *
     */
    public String son_tipo_conexion = "";
    /**
     *
     */
    public String son_blackl = "";
    private Marcas marcas;
    private TipoHhppRed tipoHhppRed;
    private TipoHhppConexion tipoHhppConexion;
    private Nodo nodoNew;
    /**
     *
     */
    public boolean gestionar = false;
    /**
     *
     */
    public boolean showPopUp = false;
    /**
     *
     */
    public boolean showValidar = true;
    /**
     *
     */
    public boolean showConsultar = true;
    /**
     *
     */
    public boolean showFooter = false;
    /**
     *
     */
    public String messagePopup;
    /**
     *
     */
    public int contador = 2;
    /**
     *
     */
    public int scrollerPage = 0;
    private static final Logger LOGGER = LogManager.getLogger(GestionarSolicitudNegocioMBean.class);

    /**
     *
     * @throws IOException
     */
    public GestionarSolicitudNegocioMBean() throws IOException {
        super();
        initVariables();
        initLists();
    }

    /**
     *
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();

        Parametros param;
        try {
            param = recursos.queryParametros(Constant.DOMINIO);
            if (param != null) {
                DOMINIO = param.getValor();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en Creación Solicitud Creacion de HHPP cargando parametros" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     *
     */
    private void initLists() {
        queryParametrosConfig();
        pais = "";
        regional = "";
        ciudad = "";
        disableGeografico = false;
        listPaises = new ArrayList<SelectItem>();
        listTred = new ArrayList<SelectItem>();
        listTconexion = new ArrayList<SelectItem>();
        listTblackL = new ArrayList<SelectItem>();
        lstTSolicitud = new ArrayList<SelectItem>();
        lstTRespuesta = new ArrayList<SelectItem>();
        try {
            tSolicitudes = SolicitudNegocioDelegate.loadTiposSolicitud();
            for (Multivalor tsol : tSolicitudes) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor());
                item.setLabel(tsol.getDescripcion());
                lstTSolicitud.add(item);
            }
            paises = SolicitudNegocioDelegate.queryPaises();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }

            tiposred = SolicitudNegocioDelegate.queryTipoRed();
            tiposconexion = SolicitudNegocioDelegate.queryTipoConexion();
            tiposBlackL = SolicitudNegocioDelegate.queryBlackList();

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
            for (Marcas tbl : tiposBlackL) {
                SelectItem item = new SelectItem();
                item.setValue(tbl.getMarId().toString());
                item.setLabel(tbl.getMarNombre());
                listTblackL.add(item);
            }
            tRespuestas = SolicitudNegocioDelegate.loadTiposRespuesta();
            for (Multivalor tsol : tRespuestas) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor().toString());
                item.setLabel(tsol.getMulValor());
                lstTRespuesta.add(item);
            }
        } catch (ApplicationException ex) {
            message = "Excepcionó en metodo initLists con mensaje de Excepcion:" + ex.getMessage();
            FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            message = "Excepcionó en metodo initLists con mensaje de Excepcion:" + ex.getMessage();
            FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
        }
        
    }

    /**
     *
     */
    private void initVariables() {
        seleccionar = "";
        solicitudid = BigDecimal.ZERO;
        solicituddir = "";
        sonTipoSolicitud = "";
        solicitud = null;
        sonId = BigDecimal.ZERO;
        sonBarrio = "";
        sonNodoCodigo = "";
        nodo = null;
        sonTipoSolucion = "";
        son_resGestion = "";
        son_tipo_red = "";
        son_tipo_conexion = "";
        son_blackl = "";
        messagePopup = "";
        marcas = null;
        tipoHhppRed = null;
        tipoHhppConexion = null;
        nodoNew = null;
        gestionar = false;
        showPopUp = false;
        showValidar = true;
        scrollerPage = 0;
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
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
            FacesUtil.mostrarMensajeError("Error al cargar Regionales" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar Regionales" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Accion ejecutada cuando el usuario selecciona una Regional para cargar
     * las ciudades correspondientes a la Regional seleccionada
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
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
            FacesUtil.mostrarMensajeError("At GestionarSolicitudNegocioMBean Exception cargando ciudades" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("At GestionarSolicitudNegocioMBean Exception cargando ciudades" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Consultar las solicitudes en el estado que el usuario selecciono
     *
     * @param ev
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onActionQuery(ActionEvent ev) throws ApplicationException {
        message = validateInfo();
        solicitudes = new ArrayList<SolicitudNegocio>();
        solicitudes = SolicitudNegocioDelegate.querySolicitudesPorGestionar(Constant.ESTADO_SON_CREADA, geograficoPolitico, son_tipoSolicitud);
        if (solicitudes != null) {
            if (solicitudes.size() > 0) {
                doDesHabilitar();
            }
        } else {
            message = "No hay solicitudes pendientes por Gestionar";
        }
    }

    /**
     * Acción a ejecutar cuando se selecciona una solicitud.
     *
     * @return
     */
    public String onSeleccionar() {
        String result = "";
        try {
            message = "";
            sonId = solicitudid;
            solicitud = SolicitudNegocioDelegate.querySolicitudNegocio(sonId);
            sonTipoSolicitud = loadTipoSolicitud();
            if (solicitud != null) {
                if (solicitud.getGeografico() != null) {
                    sonBarrio = solicitud.getGeografico().getGeoNombre();
                }
                this.nodo = solicitud.getNodo();
                sonNodoCodigo = nodo.getNodCodigo();
                result = "detalleSolicitudNegocio";
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("At GestionarSolicitudNegocioMBean - onSeleccionar solicitud " + solicitud.auditoria() + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("At GestionarSolicitudNegocioMBean - onSeleccionar solicitud " + solicitud.auditoria() + e.getMessage() , e, LOGGER);
        }

        return result;
    }

    /**
     * Valida la información ingresada para poder realizar la gestión.
     */
    public void onActionValidar() {
        message = validateFields();
        if (message.isEmpty()) {
            loadElements();
            if (tipoHhppRed == null) {
                message = "Error al cargar Tipo Red";
            }
            if (tipoHhppConexion == null) {
                message = "Error al cargar Tipo Conexion";
             }
            if (marcas == null) {
                message = "Error al cargar Marca";
            }
            if (tipoHhppRed != null && tipoHhppConexion != null && marcas != null) {
                message = "La información es correcta por favor indique Gestionar.";
                messagePopup = "¿Esta seguro que desea Gestionar esta solicitud?";
                showPopUp = true;
                showValidar = false;
            }
        } else {
            contador++;
        }
    }

    /**
     * Cargar los elementos seleccionado necesario para finalizar la gestion.
     */
    public void loadElements() {
        tipoHhppRed = loadTipoHhppred();
        tipoHhppConexion = loadTipoHhppconexion();
        marcas = loadBlacklist();
    }

    /**
     * Realiza las operaciones correspondientes a Gestion de Solicitud.
     *
     * @return
     */
    public String onActionGestionar() {
        String action = "";
        String mensajeCorreo;
        
        solicitud.setTipoHhppRed(tipoHhppRed);
        solicitud.setTipoHhppConexion(tipoHhppConexion);
        solicitud.setMarcas(marcas);
        solicitud.setSonTipoSolucion(sonTipoSolucion);
        solicitud.setSonResGestion(son_resGestion);
        solicitud.setSonUsuGestion(user.getUsuLogin());
        solicitud.setSonUsuarioModificacion(user.getUsuLogin());
        if (nodoNew != null) {
            solicitud.setNodo(nodoNew);
        }
        if (!sonTipoSolucion.equalsIgnoreCase(Constant.SON_TIPO_SOL_HHPPCREADO)) {
            solicitud.setSonEstado(Constant.ESTADO_SON_RECHAZADA);
        } else {
            solicitud.setSonEstado(Constant.ESTADO_SON_PROCESADA);
        }
        try {
            boolean resUpdate = SolicitudNegocioDelegate.updateSolicitudNegocio(NOMBRE_FUNCIONALIDAD, user.getUsuLogin(), solicitud);
            if (resUpdate) {
                message = "La solicitud se Gestionó correctamente";
                if (!sonTipoSolucion.equalsIgnoreCase(Constant.SON_TIPO_SOL_HHPPCREADO)) {
                    mensajeCorreo = "Solicitud " + solicitud.getSonEstado() + ", " + sonTipoSolucion + ", para: " + solicitud.getSonFormatoIgac() + " En la Ciudad:" + loadCity().getGpoNombre() + ", y Departamento:" + loadRegion().getGpoNombre()
                            + " porque," + solicitud.getSonResGestion() + "." + "\n" + "Cualquier inconveniente comunicarse con: " + solicitud.getSonUsuGestion() + DOMINIO;
                    try {
                        EnvioCorreo envioEmail = new EnvioCorreo();
                        envioEmail.envio(solicitud.getSonEmailSolicitante(), solicitud.getSonTipoSolucion(), mensajeCorreo);
                        solicitudes = null;
                    } catch (ApplicationException ex) {
                        message = "Se produjo un error enviando correo al solicitante.";
                        initVariables();
                        FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
                    } catch (Exception ex) {
                        message = "Se produjo un error enviando correo al solicitante.";
                        initVariables();
                        FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
                    }
                }
                solicitudes = SolicitudNegocioDelegate.querySolicitudesPorGestionar(Constant.ESTADO_SON_CREADA, geograficoPolitico, son_tipoSolicitud);
                action = "gestionarSolicitudNegocio";
                if (solicitudes == null || solicitudes.size() < 0) {
                    showTable = false;
                    showConsultar = true;
                    disableGeografico = false;
                    message = "No hay solicitudes Pendientes por Gestionar.";
                } else if (solicitudes.size() < 10) {
                    showFooter = false;
                }
                initVariables();
            } else {
                message = "Error al realizar la gestion de la solicitud.";
            }
        } catch (ApplicationException e) {
            message = "Error al realizar gestión de Solicitud.";
            FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            message = "Error al realizar gestión de Solicitud.";
            FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
        }

        return action;
    }

    /**
     * OBJ: Validar los campos ingresados por el usuario return: Mensaje
     * informando el error presentado
     *
     * @return
     */
    public String validateFields() {
        String result = "";
        if (son_tipo_red.equalsIgnoreCase("0")) {
            result = createMessageRequieredField("Tipo Red");
        } else if (son_tipo_conexion.equalsIgnoreCase("0")) {
            result = createMessageRequieredField("Tipo Conexión");
        } else if (son_blackl.equalsIgnoreCase("0")) {
            result = createMessageRequieredField("Tipo BlackList");
        } else if (sonTipoSolucion.equalsIgnoreCase("0")) {
            result = createMessageRequieredField("Tipo Respuesta");
        }
        if (son_resGestion.isEmpty()) {
            result = createMessageRequieredField("Justificación Respuesta");
        } else if (son_resGestion.length() > 300) {
            result = createMessageValueFieldExceedSize("Justificación Respuesta");
        }
        if (sonNodoCodigo.length() != 6) {
            result = createMessageWrongValueField("Código Nodo Asignado");
        } else {
            if (!sonNodoCodigo.equals(nodo.getNodCodigo())) {
                Nodo nodoR = loadNodo();
                if (nodoR == null) {
                    result = "El nodo:" + sonNodoCodigo + ", no Existe en :" + ciudad;
                } else {
                    nodoNew = nodoR;
                }
            }
        }
        return result;
    }

    /*
     * Crea un mensaje cuando los datos del Campo contienen valores no validos
     */
    private String createMessageWrongValueField(String nombreCampo) {
        String messageWV = nombreCampo + ", contiene valores no validos, por favor verifique.";
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
    private String createMessageValueFieldExceedSize(String nombreCampo) {
        String messageWV = nombreCampo + ", Excede el tamaño , por favor verifique.";
        return messageWV;
    }

    /**
     *
     * @return
     */
    public String onActionCancelGestion() {
        message = "";
        initVariables();
        return "gestionarSolicitudNegocio";
    }

    /**
     *
     * @return
     */
    public String onActionCancel() {
        initVariables();
        initLists();
        pais = "";
        regional = "";
        ciudad = "";
        message = "";
        son_tipoSolicitud = "";
        solicitudes = null;
        disableGeografico = false;
        showConsultar = true;
        showTable = false;
        geograficoPolitico = null;
        return "gestionarSolicitudNegocio";
    }

    /*Deshabilita el boton de consulta y los combos de Geografico*/
    private void doDesHabilitar() {
        showTable = true;
        disableGeografico = true;
        showConsultar = false;
        showValidar = true;
        showPopUp = false;
        if (solicitudes != null) {
            if (solicitudes.size() > 10) {
                showFooter = true;
                scrollerPage = 1;
            }
        }
    }
    /*
     *Validar los campos de Geografico y estado de las solicitudes
     */

    private String validateInfo() {

        String result = "";
        if (pais.equalsIgnoreCase("0") && regional.equalsIgnoreCase("0") && ciudad.equalsIgnoreCase("0")) {
            result = "Por favor ingrese todos los valores";
        } else if (pais.equalsIgnoreCase("0")) {
            result = "Por favor seleccione un Pais.";
        } else if (regional.equalsIgnoreCase("0")) {
            result = "Por favor seleccione una Regional.";
        } else if (ciudad.equalsIgnoreCase("0")) {
            result = "Por favor seleccione una Ciudad.";
        } else if (son_tipoSolicitud.equals("0")) {
            result = "Por favor seleccione un Tipo de Solicitud";
        }
        if (result.equals("")) {
            geograficoPolitico = new GeograficoPolitico();
            for (int i = 0; i < ciudades.size(); i++) {
                if (ciudades.get(i).getGpoId().toString().equals(ciudad)) {
                    geograficoPolitico = ciudades.get(i);
                }
            }
        }
        return result;
    }

    /**
     * Asigna el nodo bidireccional retornado por el georreferenciador
     */
    private Nodo loadNodo() {
        Nodo nodoSol = null;

        try {
            nodoSol = SolicitudNegocioDelegate.queryNodo(sonNodoCodigo, solicitud.getGeograficoPolitico().getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No existe el nodo con codigo:" + sonNodoCodigo + " ,en la ciudad de:" + ciudad + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("No existe el nodo con codigo:" + sonNodoCodigo + " ,en la ciudad de:" + ciudad + e.getMessage() , e, LOGGER);
        }

        return nodoSol;
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < ciudades.size(); i++) {
            GeograficoPolitico city = ciudades.get(i);
            if (city.getGpoId().equals(new BigDecimal(this.ciudad))) {
                geo = city;
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
            if (region.getGpoId().equals(new BigDecimal(this.regional))) {
                geo = region;
            }
        }
        return geo;
    }

    /*Cargar el Tipo Hhpp Red seleccionado por usuario*/
    /**
     *
     * @return
     */
    public TipoHhppRed loadTipoHhppred() {
        TipoHhppRed thr = null;
        for (int i = 0; i < tiposred.size(); i++) {
            TipoHhppRed t = tiposred.get(i);
            if (t.getThrId().toString().equalsIgnoreCase(son_tipo_red)) {
                thr = t;
                i = tiposred.size();
            }
        }
        return thr;
    }

    /*Cargar el Tipo de Solicitud seleccionado por usuario*/
    /**
     *
     * @return
     */
    public String loadTipoSolicitud() {
        String tipoSol = null;
        for (int i = 0; i < tSolicitudes.size(); i++) {
            Multivalor ts = tSolicitudes.get(i);
            if (ts.getMulValor().equalsIgnoreCase(son_tipoSolicitud)) {
                tipoSol = ts.getDescripcion();
                i = tSolicitudes.size();
            }
        }
        return tipoSol;
    }
    /*Cargar el Tipo Hhpp Conexion seleccionado por usuario*/

    /**
     *
     * @return
     */
    public TipoHhppConexion loadTipoHhppconexion() {
        TipoHhppConexion thc = null;
        for (int i = 0; i < tiposconexion.size(); i++) {
            TipoHhppConexion t = tiposconexion.get(i);
            if (t.getThcId().toString().equalsIgnoreCase(son_tipo_conexion)) {
                thc = t;
                i = tiposconexion.size();
            }
        }
        return thc;
    }

    /*Cargar el BlackList seleccionado por usuario*/
    /**
     *
     * @return
     */
    public Marcas loadBlacklist() {
        Marcas mar = null;
        for (int i = 0; i < tiposBlackL.size(); i++) {
            Marcas m = tiposBlackL.get(i);
            if (m.getMarId().toString().equalsIgnoreCase(son_blackl)) {
                mar = m;
                i = tiposBlackL.size();
            }
        }
        return mar;
    }

    /**
     *
     * @return
     */
    public boolean isDisableGeografico() {
        return disableGeografico;
    }

    /**
     *
     * @param disableGeografico
     */
    public void setDisableGeografico(boolean disableGeografico) {
        this.disableGeografico = disableGeografico;
    }

    /**
     *
     * @return
     */
    public String getPais() {
        return pais;
    }

    /**
     *
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     *
     * @return
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     *
     * @param ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
    public String getRegional() {
        return regional;
    }

    /**
     *
     * @param regional
     */
    public void setRegional(String regional) {
        this.regional = regional;
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
     * @param seleccionar
     */
    public void setSeleccionar(String seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     *
     * @return
     */
    public List<SolicitudNegocio> getSolicitudes() {
        return solicitudes;
    }

    /**
     *
     * @param solicitudes
     */
    public void setSolicitudes(List<SolicitudNegocio> solicitudes) {
        this.solicitudes = solicitudes;
    }

    /**
     *
     * @return
     */
    public boolean isShowTable() {
        return showTable;
    }

    /**
     *
     * @param showTable
     */
    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    /**
     *
     * @return
     */
    public SolicitudNegocio getSolicitud() {
        return solicitud;
    }

    /**
     *
     * @param solicitud
     */
    public void setSolicitud(SolicitudNegocio solicitud) {
        this.solicitud = solicitud;
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
    public String getSonNodoCodigo() {
        return sonNodoCodigo;
    }

    /**
     *
     * @param sonNodoCodigo
     */
    public void setSonNodoCodigo(String sonNodoCodigo) {
        this.sonNodoCodigo = sonNodoCodigo;
    }

    /**
     *
     * @return
     */
    public String getSonBarrio() {
        return sonBarrio;
    }

    /**
     *
     * @param sonBarrio
     */
    public void setSonBarrio(String sonBarrio) {
        this.sonBarrio = sonBarrio;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSonId() {
        return sonId;
    }

    /**
     *
     * @param sonId
     */
    public void setSonId(BigDecimal sonId) {
        this.sonId = sonId;
    }

    /**
     *
     * @return
     */
    public String getSonTipoSolucion() {
        return sonTipoSolucion;
    }

    /**
     *
     * @return
     */
    public String getSonTipoSolicitud() {
        return sonTipoSolicitud;
    }

    /**
     *
     * @param sonTipoSolicitud
     */
    public void setSonTipoSolicitud(String sonTipoSolicitud) {
        this.sonTipoSolicitud = sonTipoSolicitud;
    }

    /**
     *
     * @param sonTipoSolucion
     */
    public void setSonTipoSolucion(String sonTipoSolucion) {
        this.sonTipoSolucion = sonTipoSolucion;
    }

    /**
     *
     * @return
     */
    public String getSon_blackl() {
        return son_blackl;
    }

    /**
     *
     * @param son_blackl
     */
    public void setSon_blackl(String son_blackl) {
        this.son_blackl = son_blackl;
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

    /**
     *
     * @return
     */
    public List<SelectItem> getLstTRespuesta() {
        return lstTRespuesta;
    }

    /**
     *
     * @param lstTRespuesta
     */
    public void setLstTRespuesta(List<SelectItem> lstTRespuesta) {
        this.lstTRespuesta = lstTRespuesta;
    }

    /**
     *
     * @return
     */
    public String getSon_resGestion() {
        return son_resGestion;
    }

    /**
     *
     * @param son_resGestion
     */
    public void setSon_resGestion(String son_resGestion) {
        this.son_resGestion = son_resGestion;
    }

    /**
     *
     * @return
     */
    public boolean isGestionar() {
        return gestionar;
    }

    /**
     *
     * @param gestionar
     */
    public void setGestionar(boolean gestionar) {
        this.gestionar = gestionar;
    }

    /**
     *
     * @return
     */
    public boolean isShowValidar() {
        return showValidar;
    }

    /**
     *
     * @param showValidar
     */
    public void setShowValidar(boolean showValidar) {
        this.showValidar = showValidar;
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
    public String getSolicituddir() {
        return solicituddir;
    }

    /**
     *
     * @param solicituddir
     */
    public void setSolicituddir(String solicituddir) {
        this.solicituddir = solicituddir;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSolicitudid() {
        return solicitudid;
    }

    /**
     *
     * @param solicitudid
     */
    public void setSolicitudid(BigDecimal solicitudid) {
        this.solicitudid = solicitudid;
    }

    /**
     *
     * @return
     */
    public boolean isShowFooter() {
        return showFooter;
    }

    /**
     *
     * @param showFooter
     */
    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    /**
     *
     * @return
     */
    public int getScrollerPage() {
        return scrollerPage;
    }

    /**
     *
     * @param scrollerPage
     */
    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }

}
