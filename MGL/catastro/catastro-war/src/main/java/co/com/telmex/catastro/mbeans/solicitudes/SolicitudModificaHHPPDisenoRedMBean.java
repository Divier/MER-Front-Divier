package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.richfaces.model.UploadedFile;

/**
 * Clase SolicitudModificaHHPPDisenoRedMBean Extiende de BaseMBean Implementa
 * Serialización
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "solicitudModificaHHPPDisenoRedMBean")
public class SolicitudModificaHHPPDisenoRedMBean extends BaseMBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudModificaHHPPDisenoRedMBean.class);

    /**
     *
     */
    protected static final String MSG_ERRROR_LECTURA_ARCHIVO = "error_carga_archivo";
    /**
     *
     */
    protected static final String MSG_ERROR_ESTRUCTURA = "La estructura del archivo no es correcta";
    /**
     *
     */
    protected static final String MSG_ERRROR_ARCHIVO_TAMANO_MAXIMO = "error_archivo_tamano_maximo";
    /**
     *
     */
    protected static final String MSG_ERRROR_ARCHIVO_REGISTROS_MAXIMO = "error_archivo_registros_maximo";
    /**
     *
     */
    protected static final String MSG_ERRROR_ARCHIVO_ESTRUCTURA_CARGA = "Debe corregir el archivo cargado";
    /**
     *
     */
    public UploadedFile file;
    /**
     *
     */
    public List<UploadedFile> files;
    /**
     *
     */
    public boolean createSol;
    /**
     *
     */
    public boolean geog = true;

    /**
     *
     * @return
     */
    public boolean getGeog() {
        return geog;
    }

    /**
     *
     * @param geog
     */
    public void setGeog(boolean geog) {
        this.geog = geog;
    }
    /**
     *
     */
    public boolean maxFilesQuantity;
    /**
     *
     */
    public int size;
    /**
     *
     */
    public int cantidad;
    private AddressService responseSrv;
    /**
     *
     */
    public String son_barrio = "";
    /**
     *
     */
    public String tipoSolicitud;
    private TipoHhppRed tipoRed = null;
    private TipoHhpp tHhpp = null;
    private static int totalError;
    private UnificadorEstructuraArchivo unificador = new UnificadorEstructuraArchivo();
    /**
     * Lista que contiene todos los registros del archivo que se va a cargar
     */
    private ArrayList<ArrayList<String>> registros;
    /**
     * Labels para identificar columnas
     */
    private String[] labels;
    private String[] datav;
    /**
     *
     */
    public String dir_pais = "";
    /**
     *
     */
    public String dir_regional = "";
    /**
     *
     */
    public String dir_ciudad = "";
    /**
     *
     */
    public String dir_barrio = "";
    /**
     *
     */
    public String vacio = "";
    private Hashtable<String, Integer> staticLabels;
    /* Separador entre lineas del archivo*/
    private String separator;
    private ArrayList<String[]> contenido;
    private String fileNameReport;
    /**
     *
     */
    public boolean downloadMistakes;
    /**
     *
     */
    public String ciudadSelected;
    /**
     *
     */
    public BigDecimal cityId;
    /**
     *
     */
    public GeograficoPolitico gpo_ciudad;
    /**
     *
     */
    public BigDecimal neighborhood;
    /**
     *
     */
    public String barrioSelected;
    /**
     *
     */
    public String observaciones;
    /**
     *
     */
    public boolean renderBarrio;
    /**
     *
     */
    public String array[] = new String[21];
    List<DetalleSolicitud> solicitudes;
    private List<GeograficoPolitico> paises;
    /**
     *
     */
    public List<SelectItem> listPaises = null;
    /**
     *
     */
    public boolean errors = false;
    /**
     *
     */
    public boolean showBotonGuardar = false;
    /**
     *
     */
    public boolean descargar = false;
    /**
     *
     */
    public List<SelectItem> listCiudades = null;
    private List<GeograficoPolitico> regionales = null;
    /**
     *
     */
    public List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> ciudades = null;
    /**
     *
     */
    public boolean uploadf = false;
    /**
     *
     */
    public List<SelectItem> listValidar = null;
    /**
     *
     */
    public List<AddressService> listae = new ArrayList<AddressService>();
    private Geografico geografico = null;
    /**
     *
     */
    public List<AddressRequest> listRequestRed = new ArrayList<AddressRequest>();
    /**
     *
     */
    public AddressRequest request = new AddressRequest();
    private BigDecimal ID_THC = BigDecimal.ZERO;
    private BigDecimal ID_THR = BigDecimal.ZERO;
    private String DOMINIO = "";
    private AddressGeodata addressGeodata = null;
    List<DetalleSolicitud> detalleS = null;
    private AddressRequest address = null;
    /**
     *
     */
    public Nodo nodo = null;
    private Nodo nodo1 = null;
    private Nodo nodo2 = null;
    private Nodo nodo3 = null;
    /**
     *
     */
    public BigDecimal confiabilidad = null;
    private GeograficoPolitico city = null;
    /**
     *
     */
    public boolean enviar = false;
    /**
     *
     */
    public boolean enviarv = false;

    /**
     *
     * @return
     */
    public boolean getEnviarv() {
        return enviarv;
    }

    /**
     *
     * @param enviarv
     */
    public void setEnviarv(boolean enviarv) {
        this.enviarv = enviarv;
    }
    /**
     *
     */
    public boolean errorscol = false;

    /**
     *
     * @return
     */
    public boolean getErrorscol() {
        return errorscol;
    }

    /**
     *
     * @return
     */
    public boolean getDescargar() {
        return descargar;
    }

    /**
     *
     * @param descargar
     */
    public void setDescargar(boolean descargar) {
        this.descargar = descargar;
    }

    /**
     *
     * @param errorscol
     */
    public void setErrorscol(boolean errorscol) {
        this.errorscol = errorscol;
    }

    /**
     *
     * @return
     */
    public boolean getEnviar() {
        return enviar;
    }

    /**
     *
     * @param enviar
     */
    public void setEnviar(boolean enviar) {
        this.enviar = enviar;
    }
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private AddressGeodata addresgeodata = null;
    /**
     *
     */
    public static final String NOMBRE_FUNCIONALIDAD = "SOLICITUD MODIFICACION DE HHPP PLANEACION DE RED";

    /**
     *
     * @return
     */
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     *
     * @param tipoSolicitud
     */
    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     *
     * @return
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /**
     *
     */
    public String[] lineas = null;
    private static List<OutputElementSolPlaneacionRed> result;
    private ArrayList<OutputElementSolPlaneacionRed> results;
    /**
     *
     */
    public List<OutputElementSolPlaneacionRed> detalleresult;

    /**
     *
     * @return
     */
    public List<OutputElementSolPlaneacionRed> getDetalleresult() {
        return detalleresult;
    }

    /**
     *
     * @param detalleresult
     */
    public void setDetalleresult(List<OutputElementSolPlaneacionRed> detalleresult) {
        this.detalleresult = detalleresult;
    }

    /**
     *
     */
    public SolicitudModificaHHPPDisenoRedMBean() {
        initLists();
        downloadMistakes = false;
        geog = true;
        enviar = false;
        solicitudes = new ArrayList<DetalleSolicitud>();
        Enumeration e = session.getAttributeNames();
        int i = 0;
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            if (attr.toUpperCase().equals("MyArchivo".toUpperCase())) {
                if (i == 0) {

                    byte[] bytes = (byte[]) session.getAttribute(attr);
                    session.removeAttribute(attr);
                    if (bytes.length > 0) {

                        enviarv = false;
                        geog = false;
                        i++;
                        try {
                            listener(bytes);
                            if (message != MSG_ERROR_ESTRUCTURA) {
                                enviar = true;
                                uploadf = false;
                            } else {
                                enviar = false;
                                uploadf = true;
                            }

                      
                        } catch (Exception ex) {
                            FacesUtil.mostrarMensajeError("Error al momento de cargar la información del formulario: " + ex.getMessage(), ex, LOGGER);
                        }
                        break;

                    }

                }
            }
        }

    }

    /**
     *
     * @return
     */
    public boolean getShowBotonGuardar() {
        return showBotonGuardar;
    }

    /**
     *
     * @param showBotonGuardar
     */
    public void setShowBotonGuardar(boolean showBotonGuardar) {
        this.showBotonGuardar = showBotonGuardar;
    }

    /**
     *
     * @return
     */
    public boolean getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     */
    public void setErrors(boolean errors) {
        this.errors = errors;
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
    public boolean isUploadf() {
        return uploadf;
    }

    /**
     *
     * @param uploadf
     */
    public void setUploadf(boolean uploadf) {
        this.uploadf = uploadf;
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
     * @param dir_regional
     */
    public void setDir_regional(String dir_regional) {
        this.dir_regional = dir_regional;
    }

    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateRegionales(ValueChangeEvent event)  {
        String value = event.getNewValue().toString();
        try {
            regionales = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            session.setAttribute("regionales", regionales);
            listRegionales = new ArrayList<SelectItem>();
            if (regionales != null) {
                for (GeograficoPolitico gpo : regionales) {
                    SelectItem item = new SelectItem();
                    item.setValue(gpo.getGpoId().toString());
                    item.setLabel(gpo.getGpoNombre());
                    listRegionales.add(item);
                }
                session.setAttribute("listRegion", regionales);
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);            
            FacesUtil.mostrarMensajeError("Error al momento de actualizar las regionales: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de actualizar las regionales: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @param regionS
     * @return
     */
    private GeograficoPolitico loadRegion(String regionS) {
        GeograficoPolitico geo = new GeograficoPolitico();
        List regionl = (List) session.getAttribute("listRegion");

        for (int i = 0; i < regionl.size(); i++) {
            GeograficoPolitico region = (GeograficoPolitico) regionl.get(i);
            if (region.getGpoId().equals(new BigDecimal(regionS))) {
                geo = region;
            }
        }
        return geo;
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        int c = Integer.parseInt(session.getAttribute("totalc").toString());
        List ciudade = (List) session.getAttribute("ciudades");
        for (int i = 0; i < c; i++) {
            GeograficoPolitico ciudad = (GeograficoPolitico) ciudade.get(i);
            if (ciudad.getGpoId().equals(new BigDecimal(session.getAttribute("city").toString()))) {
                geo = ciudad;
            }
        }
        return geo;
    }

    /**
     * Carga el archivo que se adjunta como soporte a la información
     * suministrada
     */
    public void init() {
        dir_barrio = "";
        dir_ciudad = "";
        dir_pais = "";
        dir_regional = "";

    }

    /**
     *
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();

        Parametros param = null;
        try {
            param = recursos.queryParametros(Constant.ID_THC);
            if (param != null) {
                ID_THC = new BigDecimal(param.getValor());
            }

            if (param != null) {
                ID_THR = new BigDecimal(param.getValor());
            }
            param = recursos.queryParametros(Constant.DOMINIO);
            if (param != null) {
                DOMINIO = param.getValor();
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de consultar los parámetros de configuración. EX000: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     */
    private void initLists() {
        queryParametrosConfig();
        listPaises = new ArrayList<SelectItem>();

        try {
            paises = SolicitudNegocioDelegate.queryPaises();

            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de inicializar las listas. EX000: " + ex.getMessage(), ex, LOGGER);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de inicializar las listas. EX000: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @param ev
     */
    public void validar(ActionEvent ev) {
        message = "";
        //Se validan los campos obligatorios
        message = validarCamposObligatorios();

        if (message.isEmpty()) {
            enviarv = false;
            uploadf = true;
            enviar = false;
            geog = false;

        }

    }

    /**
     *
     * @return
     */
    private String validarCamposObligatorios() {
        message = "";
        if ("0".equals(dir_pais) || dir_pais == null) {
            message = "\n El Pais es  obligatorio, por favor Seleccionelo.";
            return message;
        }

        if ("0".equals(dir_regional) || dir_regional == null) {
            message = "\n La regional es  obligatoria, por favor Seleccionelo.";

            return message;
        } else {
            session.setAttribute("regionales", dir_regional);
        }

        if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = "\n La ciudad es obligatoria, por favor diligenciela.";
            return message;

        } else {
            String nombreCiudad = null;
            try {
                nombreCiudad = SolicitudRedDelegate.queryGeograficoPoliticoId(new BigDecimal(dir_ciudad)).getGpoNombre();
                session.setAttribute("cityname", nombreCiudad);
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            session.setAttribute("city", dir_ciudad);
        }
        if ("0".equals(observaciones) || observaciones == null) {
            message = "\n Observaciones es obligatorio, por favor Ingreselo.";
            return message;
        } else {
            session.setAttribute("observaciones", observaciones);
        }
        return message;
    }

    /**
     *
     * @param data
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void listener(byte[] data)  {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            texto.append(c);
        }
        separator = System.getProperty("line.separator");
        if (data.length < 1) {
            this.message = MSG_ERRROR_LECTURA_ARCHIVO;
        } else {

            try {
                registros = new ArrayList<ArrayList<String>>();
                registros.addAll(loadtext(texto));
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: listener()" + ex.getMessage(), ex, LOGGER);
            }
        }
    }

    /**
     *
     * @return
     */
    public String onFinishLoad() {

        if (createSol) {
            createSol = true;
            downloadMistakes = false;
        } else if (downloadMistakes) {
            createSol = false;
        }
        return "";
    }

    /**
     * Descompone una cadena de texto String en renglones (salto de línea) y en
     * datos (separador |) ejemplo: este|es|un|ejemplo la descompone en 4
     * elementos de una lista; [este], [es], [un],[ejemplo]
     *
     * @param event
     * @throws ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event)  {
        String value = event.getNewValue().toString();
        try {
            ciudades = SolicitudNegocioDelegate.queryCiudades(new BigDecimal(value));
            listCiudades = new ArrayList<SelectItem>();
            int k = 0;
            if (ciudades != null) {
                for (GeograficoPolitico gpo : ciudades) {
                    SelectItem item = new SelectItem();
                    item.setValue(gpo.getGpoId().toString());
                    item.setLabel(gpo.getGpoNombre());
                    k++;
                    listCiudades.add(item);
                }
                session.setAttribute("ciudades", ciudades);
                session.setAttribute("totalc", k);
            }
        } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
             FacesUtil.mostrarMensajeError("Error al momento de actualizar las ciudades: " + e.getMessage(), e, LOGGER);
        } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de actualizar las ciudades: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     *
     * @param messag
     * @return
     */
    private String validarCampos(String messag) {
        enviar = false;
        errors = false;
        uploadf = true;
        message = messag;
        errors = false;
        descargar = false;

        return message;
    }

    /**
     *
     * @param messag
     * @return
     */
    private String validarEstructura(String messag) {
        enviar = false;
        errors = false;
        uploadf = true;
        message = messag;
        descargar = true;
        return message;
    }

    /**
     *
     * @param texto
     * @return
     * @throws IOException
     */
    private ArrayList<ArrayList<String>> loadtext(StringBuilder texto)  {

        String dato = "";
        registros = new ArrayList<ArrayList<String>>();
        separator = System.getProperty("line.separator");

        String[] lineas = texto.toString().split(separator);
        try{
        if (lineas.length != 0 && !"".equals(lineas.toString()) && lineas != null) {
            ArrayList<String> listaDatos = new ArrayList<>();
            results = new ArrayList<>();
            setCantidad(lineas.length);
            for (int i = 0; i < lineas.length; i++) {
                String[] linea = lineas[i].split("\t" + ",");

                if (linea.length <= 1) {

                    for (int j = 0; j < linea.length; j++) {
                        dato = linea[j].trim();
                        if (dato.isEmpty() || dato.equalsIgnoreCase("null")) {
                            dato = null;
                        }
                    }

                    listaDatos.add(dato);
                } else {
                    message = MSG_ERROR_ESTRUCTURA;
                    validarCampos(message);
                    setErrors(true);
                }
            }
            String splitLabels[] = listaDatos.get(0).split("\t");
            int totalLabel = 15;
            if (splitLabels.length == totalLabel) {
                for (int l = 0; l < listaDatos.size(); l++) {
                    ArrayList dat = null;
                    String data = null;
                    dat = new ArrayList();

                    if (l == 0) {
                        for (int j = 0; j < totalLabel; j++) {
                            String dataLabels = splitLabels[j];
                            switch (j) {

                                case 0:
                                    if (!("NOMBRECALL_ACTUAL").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);

                                    }
                                    break;

                                case 1:
                                    if (!("PLACAUNIDA_ACTUAL").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);

                                    }
                                    break;

                                case 2:
                                    if (!("NUMINT_ACTUAL").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);

                                    }
                                    break;

                                case 3:
                                    if (!("PISO_APARTAMENTO_ACTUAL").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 4:
                                    if (!("BARRIO_ACTUAL").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 5:
                                    if (!("NOMBRECALL_NUEVA").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 6:
                                    if (!("PLACAUNIDA_NUEVA").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 7:
                                    if (!("NUMINT_NUEVA").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 8:
                                    if (!("CASAAPARTAMENTO_NUEVA").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 9:
                                    if (!("CIUDAD").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 10:
                                    if (!("BARRIO").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 11:
                                    if (!("ESTRATO").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 12:
                                    if (!("NODO").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 13:
                                    if (!("VALIDARGEO").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                                case 14:
                                    if (!("TIPOMODIFICACION").equals(dataLabels)) {
                                        message = MSG_ERROR_ESTRUCTURA;
                                        validarCampos(message);
                                        setErrors(true);
                                    }
                                    break;

                            }
                        }
                    } else {
                        String[] splitarrayqq = listaDatos.get(l).split("\t");
                        if (splitarrayqq.length == 15) {

                            for (int n = 0; n < 15; n++) {
                                data = (splitarrayqq[n].toString());
                                dat.add(data);
                            }
                        } else {
                            message = MSG_ERROR_ESTRUCTURA;
                            validarCampos(message);
                            setErrors(true);
                        }

                    }
                }
            } else {
                message = MSG_ERROR_ESTRUCTURA;
                enviar = false;
                validarCampos(message);
                setErrors(true);
            }

        } else {
            message = MSG_ERROR_ESTRUCTURA;
            enviar = false;
            validarCampos(message);
            setErrors(true);
        }

        setDetalleresult(results);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error al momento de cargar el texto: " + ex.getMessage(), ex, LOGGER);
        }
        return registros;

    }

    /**
     *
     * @param s
     * @param valueIfInvalid
     * @return
     */
    public static int parseInt(final String s, final int valueIfInvalid) {
        try {
            if (s == null) {
                return valueIfInvalid;
            } else {
                return Integer.parseInt(s);
            }
        } catch (final NumberFormatException ex) {
            LOGGER.error("Error numérico al momento de procesar el entero '" + s + "': " + ex.getMessage());
            return valueIfInvalid;
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
                nodo1 = SolicitudNegocioDelegate.queryNodo(codNodo1, gpo_ciudad.getGpoId());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
   
            }
        }
        if (!codNodo2.isEmpty()) {
            try {
                nodo2 = SolicitudNegocioDelegate.queryNodo(codNodo2, gpo_ciudad.getGpoId());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                
            }
        }
        if (!codNodo3.isEmpty()) {
            try {
                nodo3 = SolicitudNegocioDelegate.queryNodo(codNodo3, gpo_ciudad.getGpoId());
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            }
        }

        if ((nodo1 != null && nodo2 != null && nodo3 != null)) {
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
        } else {
            ladoMz = "NG - NG - NG";
        }
        return ladoMz;
    }

    /**
     *
     * @param address
     * @return
     */
    private String getAppletFromAddress(String address) {
        String applet = "";
        if (address != null && address.length() == 99) {
            try {
                applet = "MZ" + address.substring(21, 27);
            } catch (NumberFormatException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);               
                applet = "";
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: getAppletFromAddress()" + ex.getMessage(), ex, LOGGER);
            }
        }
        return applet;
    }

    /**
     *
     * @param addressGeodata
     */
    public void validadorExistenciaGeografico(AddressGeodata addressGeodata) {
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
            } catch (ApplicationException ex) {
                LOGGER.error("Error al momento de validar la existencia del geográfico. EX000: " + ex.getMessage(), ex);
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
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
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error al momento de validar la existencia del geográfico: " + ex.getMessage(), ex, LOGGER);
                } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error al momento de validar la existencia del geográfico: " + ex.getMessage(), ex, LOGGER);
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
                 } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
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
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg);
                    } catch (Exception ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
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
                } catch (Exception ex) {
                    FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: validadorExistenciaGeografico()" + ex.getMessage(), ex, LOGGER);
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
                    } catch (ApplicationException ex) {
                        LOGGER.error("Error al momento de validar la existencia del geográfico. EX000: " + ex.getMessage(), ex);
                    } catch (Exception ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Error al momento de validar la existencia del geográfico: " + ex.getMessage(), ex, LOGGER);
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
     * @param ev
     * @return
     */
    public String create(ActionEvent ev) {
        return "";
    }

    /**
     *
     */
    private void createObjects() {

        SolicitudRed sre = new SolicitudRed();
        GeograficoPolitico gpo = new GeograficoPolitico();
        gpo.setGpoId(new BigDecimal(dir_ciudad));
        sre.setGeograficoPolitico(gpo);
        sre.setSreObservaciones(observaciones);
        sre.setSreUsuarioCreacion(user.getUsuNombre() + " " + user.getUsuApellidos());
        sre.setSreEstado(Constant.ESTADO_SPLRED_INICIAL);
    }

    /**
     *
     * @param nodo
     * @param gpoId
     * @return
     */
    private Nodo buscarNodo(String nodo, BigDecimal gpoId) {
        Nodo nodDir = new Nodo();
        try {
            nodDir = SolicitudRedDelegate.queryNodo(nodo, gpoId);
        } catch (ApplicationException ex) {
            message = "ERROR" + ex.getMessage();
            LOGGER.error(message, ex);
        }
        return nodDir;
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico buscarGeograficoPolitico() {
        GeograficoPolitico gpo = new GeograficoPolitico();
        try {
            gpo = SolicitudRedDelegate.queryGeograficoPolitico(dir_ciudad);
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
            LOGGER.error("Error al momento de buscar el geográfico político: " + ex.getMessage(), ex);
        }
        return gpo;
    }

    /**
     *
     * @return
     */
    public String clearUploadData() {
        files.clear();
        return null;
    }

    /**
     *
     * @return
     */
    public boolean isCreateSol() {
        return createSol;
    }

    /**
     *
     * @param createSol
     */
    public void setCreateSol(boolean createSol) {
        this.createSol = createSol;
    }

    /**
     *
     * @return
     */
    public boolean isRenderBarrio() {
        return renderBarrio;
    }

    /**
     *
     * @param renderBarrio
     */
    public void setRenderBarrio(boolean renderBarrio) {
        this.renderBarrio = renderBarrio;
    }

    /**
     *
     * @return
     */
    public boolean isMaxFilesQuantity() {
        return maxFilesQuantity;
    }

    /**
     *
     * @param maxFilesQuantity
     */
    public void setMaxFilesQuantity(boolean maxFilesQuantity) {
        this.maxFilesQuantity = maxFilesQuantity;
    }

    /**
     *
     * @return
     */
    public List<UploadedFile> getFiles() {
        return files;
    }

    /**
     *
     * @param files
     */
    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }

    /**
     *
     * @return
     */
    public String getBarrioSelected() {
        return barrioSelected;
    }

    /**
     *
     * @param barrioSelected
     */
    public void setBarrioSelected(String barrioSelected) {
        this.barrioSelected = barrioSelected;
    }

    /**
     *
     * @return
     */
    public String getCiudadSelected() {
        return ciudadSelected;
    }

    /**
     *
     * @param ciudadSelected
     */
    public void setCiudadSelected(String ciudadSelected) {
        this.ciudadSelected = ciudadSelected;
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
    public boolean isDownloadMistakes() {
        return downloadMistakes;
    }

    /**
     *
     * @param downloadMistakes
     */
    public void setDownloadMistakes(boolean downloadMistakes) {
        this.downloadMistakes = downloadMistakes;
    }
}
