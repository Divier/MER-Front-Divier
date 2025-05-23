package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.EnvioCorreo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase AtenderDolDisenoRedMBean Extiende de BaseMBean Implementa Serialización
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "gestionarSolicitudModificacionMBean")
public class GestionarSolicitudNegocioModificacionMBean extends BaseMBean implements Serializable {

    private static String NOMBRE_FUNCIONALIDAD = "GESTIONAR SOLICITUD DE NEGOCIO MODIFICACION";
    private String nombreLog;
    /**
     *
     */
    public String seleccionar = "";
    /**
     *
     */
    public String pais = "";
    /**
     *
     */
    public String departamento = "";
    /**
     *
     */
    public String ciudad = "";
    /**
     *
     */
    public String son_tipoSolicitud = "";
    /**
     *
     */
    public String solicituddir = "";
    /**
     *
     */
    public String sonNodoCodigo = "";
    /**
     *
     */
    public String sonBarrio = "";
    /**
     *
     */
    public String sonTipoSolucion = "";
    /**
     *
     */
    public String son_resGestion = "";
    /**
     *
     */
    public String sonTipoSolicitud = "";
    private BigDecimal solicitudid = BigDecimal.ZERO;
    /**
     *
     */
    public int contador = 2;
    /**
     *
     */
    public int scrollerPage = 0;
    /**
     *
     */
    public boolean disableGeografico = false;
    /**
     *
     */
    public boolean showTable = false;
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
    public boolean showValidar = true;
    /**
     *
     */
    public boolean showPopUp = false;
    /**
     *
     */
    public boolean renderedBotones = true;
    /**
     *
     */
    public boolean renderedPais = false;
    /**
     *
     */
    public boolean showDetail = false;
    /**
     *
     */
    public boolean showMain = true;
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
    public List<SolicitudNegocio> solicitudes = null;
    /**
     *
     */
    public List<SelectItem> lstTRespuesta = null;
    private List<Multivalor> tRespuestas = null;
    private GeograficoPolitico geograficoPolitico = null;
    private SolicitudNegocio solicitud = null;
    private Nodo nodo = null;
    /**
     *
     */
    public Nodo nodoNew = null;
    private static final Logger LOGGER = LogManager.getLogger(GestionarSolicitudNegocioModificacionMBean.class);

    /**
     *
     */
    public GestionarSolicitudNegocioModificacionMBean() {
        super();
        initVariables();
        initLists();

    }

    /**
     *
     * @return
     */
    public String prueba1Action() {
        return "gestionarSolicitudNegocioModificacion";
    }

    /**
     *
     */
    private void initLists() {
        pais = "";
        departamento = "";
        ciudad = "";
        disableGeografico = false;
        listPaises = new ArrayList<SelectItem>();
        lstTSolicitud = new ArrayList<SelectItem>();
        lstTRespuesta = new ArrayList<SelectItem>();
        try {
            tSolicitudes = SolicitudNegocioDelegate.loadTiposSolicitudModificacion();
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
            tRespuestas = SolicitudNegocioDelegate.loadTiposRespuestaModificacion();
            for (Multivalor tsol : tRespuestas) {
                SelectItem item = new SelectItem();
                item.setValue(tsol.getMulValor().toString());
                item.setLabel(tsol.getMulValor());
                lstTRespuesta.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al cargar listas iniciales: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar listas iniciales: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     */
    private void initVariables() {
        solicituddir = "";
        sonNodoCodigo = "";
        son_resGestion = "";
        sonTipoSolucion = "";
        sonTipoSolicitud = "";
        sonBarrio = "";
        sonNodoCodigo = "";

        solicitudid = BigDecimal.ZERO;

        contador = 2;

        showValidar = true;
        showPopUp = false;
        showMain = true;
        showDetail = false;

        solicitud = null;
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
     *
     * @param event
     * @throws ApplicationException
     */
    public void updateRegionales(ValueChangeEvent event) throws ApplicationException {
        String value = event.getNewValue().toString();
        try {
            departamentos = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            listDeptos = new ArrayList<SelectItem>();
            for (GeograficoPolitico gpo : departamentos) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listDeptos.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Excepciono al cargar Departamentos. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Excepciono al cargar Departamentos. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Accion ejecutada cuando el usuario selecciona una Regional para cargar
     * las ciudades correspondientes a la Regional seleccionada
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
            message = "At GestionarSolicitudNegocioModificacionMBean Exception cargando ciudades";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            message = "At GestionarSolicitudNegocioModificacionMBean Exception cargando ciudades";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Consultar las solicitudes en el estado que el usuario selecciono
     *
     * @throws ApplicationException
     */
    public void onActionQuery() throws ApplicationException {
        message = validateInfo();
        solicitudes = new ArrayList<SolicitudNegocio>();
        solicitudes = SolicitudNegocioDelegate.querySolicitudesPorGestionar(Constant.ESTADO_SON_CREADA, geograficoPolitico, son_tipoSolicitud);
        if (solicitudes != null) {
            doDesHabilitar();
        } else {
            message = "No hay solicitudes pendientes por Gestionar";
        }
    }

    /*Deshabilita el boton de consulta y los combos de Geografico*/
    /**
     *
     */
    private void doDesHabilitar() {
        showTable = true;
        disableGeografico = true;
        showConsultar = false;
        showValidar = true;
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
        if (pais.equalsIgnoreCase("0") && departamento.equalsIgnoreCase("0") && ciudad.equalsIgnoreCase("0")) {
            result = "Por favor ingrese todos los valores";
        } else if (pais.equalsIgnoreCase("0")) {
            result = "Por favor seleccione un Pais.";
        } else if (departamento.equalsIgnoreCase("0")) {
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
     * Acción a ejecutar cuando se selecciona una solicitud.
     *
     * @return
     */
    public String onSeleccionar() {
        String result = "";
        try {
            message = "";
            solicitud = SolicitudNegocioDelegate.querySolicitudNegocio(solicitudid);
            if (solicitud != null) {
                if (solicitud.getGeografico() != null) {
                    sonBarrio = solicitud.getGeografico().getGeoNombre();
                }
                nodo = nodo = solicitud.getNodo();
                sonNodoCodigo = nodo.getNodCodigo();
                sonTipoSolicitud = loadTipoSolicitud();
                showDetail = true;
                showMain = false;
                result = "detalleGestionSolicitudModificacion";
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al cargar detalle de solcitud seleccionada. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar detalle de solcitud seleccionada. " + e.getMessage(), e, LOGGER);
        }
        return result;
    }

    /**
     * Valida la información ingresada para poder realizar la gestión.
     */
    public void onActionValidar() {
        message = validateFields();
        if (!message.isEmpty()) {
            contador++;
        } else {
            message = "La información es correcta, por favor indique Gestionar";
            showPopUp = true;
            showValidar = false;
        }
    }

    /**
     * Realiza las operaciones correspondientes a Gestion de Solicitud.
     *
     * @return
     */
    public String onActionGestionar() {
        String action = "";
        String mensajeCorreo = "";

        solicitud.setSonTipoSolucion(sonTipoSolucion);
        solicitud.setSonResGestion(son_resGestion);
        solicitud.setSonUsuGestion(user.getUsuLogin());
        solicitud.setSonUsuarioModificacion(user.getUsuLogin());
        if (nodoNew != null) {
            solicitud.setNodo(nodoNew);
        }
        if (sonTipoSolucion.equalsIgnoreCase(Constant.SON_TIPO_SOL_MALREALIZADA)) {
            solicitud.setSonEstado(Constant.ESTADO_SON_RECHAZADA);
        } else {
            solicitud.setSonEstado(Constant.ESTADO_SON_PROCESADA);
        }
        try {
            boolean resUpdate = SolicitudNegocioDelegate.updateSolicitudNegocio(NOMBRE_FUNCIONALIDAD, user.getUsuLogin(), solicitud);
            if (resUpdate) {
                message = "La solicitud se Gestionó correctamente";
                if (sonTipoSolucion.equalsIgnoreCase(Constant.SON_TIPO_SOL_MALREALIZADA)) {
                    mensajeCorreo = "Solicitud " + solicitud.getSonEstado() + ", " + sonTipoSolucion + ", para: " + solicitud.getSonFormatoIgac() + " En la Ciudad:" + loadCity().getGpoNombre() + ", y Departamento:" + loadDepto().getGpoNombre()
                            + " porque," + solicitud.getSonResGestion() + "." + "\n" + "Cualquier inconveniente comunicarse con: " + solicitud.getSonUsuGestion();
                    try {
                        EnvioCorreo envioEmail = new EnvioCorreo();
                        envioEmail.envio(solicitud.getSonEmailSolicitante(), solicitud.getSonTipoSolucion(), mensajeCorreo);
                        solicitudes = null;
                    } catch (ApplicationException e) {
                        FacesUtil.mostrarMensajeError("Error al enviar correo al solicitante. " + e.getMessage(), e, LOGGER);
                        initVariables();
                    } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Error al enviar correo al solicitante. " + e.getMessage(), e, LOGGER);
                        initVariables();
                    }
                }
                solicitudes = SolicitudNegocioDelegate.querySolicitudesPorGestionar(Constant.ESTADO_SON_CREADA, geograficoPolitico, son_tipoSolicitud);
                showDetail = false;
                showMain = true;
                action = "gestionarSolicitudNegocioModificacion";
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
            FacesUtil.mostrarMensajeError("Error al realizar gestión de Solicitud. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al realizar gestión de Solicitud. " + e.getMessage(), e, LOGGER);
        }
        return action;
    }

    /*
     * Cancelar la Gestion de una solicitud
     */
    /**
     *
     * @return
     */
    public String onActionCancelGestion() {
        message = "";
        initVariables();
        return "gestionarSolicitudNegocioModificacion";
    }

    /**
     * OBJ: Validar los campos ingresados por el usuario return: Mensaje
     * informando el error presentado
     *
     * @return
     */
    public String validateFields() {
        String result = "";
        if (sonTipoSolucion.equalsIgnoreCase("0")) {
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

    /**
     * Carga el nodo solicitado por el usuario
     */
    private Nodo loadNodo() {
        Nodo nodoSol = null;

        try {
            nodoSol = SolicitudNegocioDelegate.queryNodo(sonNodoCodigo, solicitud.getGeograficoPolitico().getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No existe el nodo con codigo:" + sonNodoCodigo + " ,en la ciudad de:" + ciudad + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("No existe el nodo con codigo:" + sonNodoCodigo + " ,en la ciudad de:" + ciudad + e.getMessage(), e, LOGGER);
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
    private GeograficoPolitico loadDepto() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < departamentos.size(); i++) {
            GeograficoPolitico region = departamentos.get(i);
            if (region.getGpoId().equals(new BigDecimal(this.departamento))) {
                geo = region;
            }
        }
        return geo;
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
    public String onActionCancel() {
        initVariables();
        initLists();
        pais = "";
        departamento = "";
        ciudad = "";
        message = "";
        son_tipoSolicitud = "";
        solicitudes = null;
        disableGeografico = false;
        showConsultar = true;
        showTable = false;
        geograficoPolitico = null;
        return "gestionarSolicitudNegocioModificacion";
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
    public List<GeograficoPolitico> getDepartamentos() {
        return departamentos;
    }

    /**
     *
     * @param departamentos
     */
    public void setDepartamentos(List<GeograficoPolitico> departamentos) {
        this.departamentos = departamentos;
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
     * @param lstTSolicitud
     */
    public void setLstTSolicitud(List<SelectItem> lstTSolicitud) {
        this.lstTSolicitud = lstTSolicitud;
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

    /**
     *
     * @return
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     *
     * @param departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
    public String getSonTipoSolucion() {
        return sonTipoSolucion;
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
    public boolean isShowDetail() {
        return showDetail;
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
    public boolean isShowMain() {
        return showMain;
    }

    /**
     *
     * @param showMain
     */
    public void setShowMain(boolean showMain) {
        this.showMain = showMain;
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
}
