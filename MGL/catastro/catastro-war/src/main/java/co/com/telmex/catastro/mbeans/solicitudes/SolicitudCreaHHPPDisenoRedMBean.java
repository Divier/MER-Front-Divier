package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.direccion.file.manager.ValidateFileRed;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.util.ConstantWEB;
import co.com.telmex.catastro.util.ConstantWEB.NAME_COLUMN;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase SolicitudCreaHHPPDisenoRedMBean Extiende de BaseMBean Implementa
 * Serialización
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "solicitudCreaHHPPPlanRedMBean")
public class SolicitudCreaHHPPDisenoRedMBean extends BaseMBean implements Serializable {

    //Variables de secion
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
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
    List<String> errorColumnasArchivo;
    private boolean errorColumnas;
    private static final Logger LOGGER = LogManager.getLogger(SolicitudCreaHHPPDisenoRedMBean.class);
    /**
     *
     */
    public int cantidad;
    /**
     * Lista que contiene todos los registros del archivo que se va a cargar
     */
    private ArrayList<ArrayList<String>> registros;
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
    public String vacio = "";
    /* Separador entre lineas del archivo*/
    private String separator;
    private ArrayList<String[]> contenido;
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
    List<DetalleSolicitud> detalleS = null;
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
     */
    public static final String NOMBRE_FUNCIONALIDAD = "SOLICITUD CREACION DE HHPP PLANEACION DE RED";

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
    public List<String> todasLasDirecciones;

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

    private boolean geog = true;

    /**
     *
     */
    private boolean pintarNuevo = false;

    private boolean enviarv = true;
    private SecurityLogin securityLogin;

    public SolicitudCreaHHPPDisenoRedMBean() {
        super();
        try {
            if (securityLogin != null) {
                securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
            }
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de cargar la información de la solicitud: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de cargar la información de la solicitud: " + e.getMessage(), e, LOGGER);
        }
        initLists();
        byte[] bytes = (byte[]) session.getAttribute("MyArchivo");
        if (bytes != null) {
            session.removeAttribute("MyArchivo");
            
            if (bytes.length > 0) {
                try {
                    uploadf = false;
                    geog = false;
                    results = null;
                    enviarv = false;
                    System.gc();
                    listener(bytes);
                 } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                    FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean" + e.getMessage(), e, LOGGER);
                }
            }
        } else {
            uploadf = false;
            geog = true;
            results = null;
            enviarv = true;
            pintarNuevo = false;
            System.gc();
        }

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
            session.setAttribute("regionalesLista", regionales);
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
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de actualizar las regionales: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de actualizar las regionales: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        List<GeograficoPolitico> ciudadesLista;
        String selCity = (String) session.getAttribute("city");
        ciudadesLista = (List<GeograficoPolitico>) session.getAttribute("ciudadesLista");
        for (int i = 0; i < ciudadesLista.size(); i++) {
            if (ciudadesLista.get(i).getGpoId().toString().trim().equals(selCity.trim())) {
                geo = ciudadesLista.get(i);
            }
        }

        return geo;
    }

    private GeograficoPolitico loadState() {
        GeograficoPolitico geo = new GeograficoPolitico();
        List<GeograficoPolitico> regionalesLista;
        String selDepartamento = (String) session.getAttribute("departamento");
        regionalesLista = (List<GeograficoPolitico>) session.getAttribute("regionalesLista");
        for (int i = 0; i < regionalesLista.size(); i++) {
            if (regionalesLista.get(i).getGpoId().toString().trim().equals(selDepartamento.trim())) {
                geo = regionalesLista.get(i);
            }
        }
        return geo;
    }

    /**
     * Carga el archivo que se adjunta como soporte a la información
     * suministrada
     */
    public void init() {
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
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
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
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de cargar las listas: " + e.getMessage(), e, LOGGER);
          } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de cargar las listas: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param ev
     */
    public void validar(ActionEvent ev) {
        LOGGER.error("Prueba de pasar");
        message = "";
        //Se validan los campos obligatorios
        message = validarCamposObligatorios();
        LOGGER.error("Prueba de pasar1");
        if (!message.isEmpty()) {
            LOGGER.error("Prueba de pasar2");
            geog = true;
            enviarv = true;
            pintarNuevo = false;
            uploadf = false;
        } else {
            LOGGER.error("Prueba de pasar2.1");
            geog = false;
            enviarv = false;
            pintarNuevo = false;
            uploadf = true;
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
            session.setAttribute("departamento", dir_regional);
        }

        if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = "\n La ciudad es  obligatoria, por favor diligenciela.";
            return message;

        } else {
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

        StringBuffer texto = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            texto.append(c);
        }
        errorColumnasArchivo = new ArrayList<String>();
        separator = System.getProperty("line.separator");
        if (data.length < 1) {
            this.message = MSG_ERRROR_LECTURA_ARCHIVO;
        } else {

            try {
                String[] lineas = texto.toString().split(System.getProperty("line.separator"));
                for (int contLineas = 0; contLineas <= lineas.length - 1; contLineas++) {
                    String linea = lineas[contLineas];
                    String[] columnas = linea.split("\t", -1);

                    if (columnas.length != ConstantWEB.MAX_NUMERO_COLUM_ARCHIVO_RED) {
                        errorColumnasArchivo.add(linea.replace("\n", "") + ",Los registros deben contener " + ConstantWEB.MAX_NUMERO_COLUM_ARCHIVO_RED + " Columnas \n");
                        this.message = MSG_ERROR_ESTRUCTURA;
                        errorColumnas = true;
                        descargar = true;
                    } else {
                        errorColumnasArchivo.add(linea.replace("\n", "") + "\n");
                    }
                }
                if (errorColumnas) {
                    pintarNuevo = true;
                    return;
                }

                registros = new ArrayList<ArrayList<String>>();

                registros.addAll(loadtext(texto));
                pintarNuevo = true;
                if (descargar) {
                    return;
                }
                LOGGER.error("llamdo del proceso");
                create();
                LOGGER.error("fin llamdo del proceso");
           
             } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: listener()" + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     * Descompone una cadena de texto String en renglones (salto de línea) y en
     * datos (separador |) ejemplo: este|es|un|ejemplo la descompone en 4
     * elementos de una lista; [este], [es], [un],[ejemplo]
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event) {
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
                session.setAttribute("ciudadesLista", ciudades);
                session.setAttribute("totalc", k);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: updateCiudades()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: updateCiudades()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param messag
     * @return
     */
    private String validarCampos(String messag) {
        errors = true;
        uploadf = false;
        message = messag;
        descargar = true;
        return message;
    }

    private String validarCampos2(String messag) {
        errors = true;
        uploadf = false;
        message = messag;
        descargar = false;
        return message;
    }

    /**
     *
     * @param texto
     * @return
     * @throws IOException
     */
    private ArrayList<ArrayList<String>> loadtext(StringBuffer texto)  {
        try {
            registros = new ArrayList<ArrayList<String>>();
            separator = System.getProperty("line.separator");
            String[] lineas = texto.toString().split(separator);
            ArrayList<String> listaDatos = new ArrayList<String>();
            results = new ArrayList<OutputElementSolPlaneacionRed>();
            setCantidad(lineas.length);

            if (lineas.length > 1) {
                for (int i = 0; i < lineas.length; i++) {
                    listaDatos.add(lineas[i]);
                }
                String splitLabels[] = listaDatos.get(0).split("\t");

                //Maxmino de columnas permitidas
                int totalLabel = ConstantWEB.MAX_NUMERO_COLUM_ARCHIVO_RED;
                if (splitLabels.length == totalLabel) {
                    ArrayList columUser = new ArrayList();
                    ArrayList columPara = new ArrayList();
                    int cont = 0;
                    //Se realiza adecuacion de datos para realiza verificacion de nombre de columnas
                    for (NAME_COLUMN val : ConstantWEB.NAME_COLUMN.values()) {
                        String valor = val.toString().toUpperCase();
                        String dataLabels = splitLabels[cont++].trim().replace(" ", "").toUpperCase();
                        if (splitLabels.length >= cont) {
                            columUser.add(dataLabels);
                        }
                        columPara.add(valor);
                    }
                    //Se verifica que el nombre del las comunas sea el correcto
                    errorColumnasArchivo = new ArrayList<String>();
                    for (int i = 0; i < columUser.size(); i++) {
                        if (!columPara.contains(columUser.get(i))) {
                            message = MSG_ERROR_ESTRUCTURA;
                            errorColumnasArchivo.add(message + " El nombre de la Columna " + columUser.get(i) + " No es valido el nombre correcto es: " + columPara.get(i) + " \n");
                            errorColumnas = true;
                            descargar = true;
                            LOGGER.error(message);

                        }
                    }
                    if (errorColumnas) {
                        return registros;
                    }
                    todasLasDirecciones = new ArrayList<String>();
                    //Se procesan el resto de filas del archivo para obtener los HHPP
                    for (int l = 1; l < listaDatos.size(); l++) {
                        ArrayList dataArray = null;
                        String data = null;
                        dataArray = new ArrayList();

                        String splitarrayqq[] = listaDatos.get(l).split("\t", -1);
                        //obtenemos la informacion de cada fila
                        for (int n = 0; n < totalLabel; n++) {
                            data = (splitarrayqq[n].toString());
                            dataArray.add(data);
                        }
                        validateRow(dataArray, columUser, l);
                    }

                } else {
                    message = MSG_ERROR_ESTRUCTURA + "; El numero de columnas debe ser de : " + totalLabel;
                    setErrors(true);
                }
            } else {
                message = MSG_ERROR_ESTRUCTURA + " El documento no contiene datos para validar ";
                setErrors(true);
            }

            if (errors) {
                validarCampos(message);
            }
            setDetalleresult(results);

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: loadtext()" + e.getMessage(), e, LOGGER);
        }
        return registros;
    }

    /**
     *
     * @param datav
     * @param position
     */
    private void validateRow(ArrayList datav, ArrayList nameColumn, int numFila) {
        GeograficoPolitico city = loadCity();
        OutputElementSolPlaneacionRed oespr = new OutputElementSolPlaneacionRed();
        ValidateFileRed valideFile = new ValidateFileRed(datav.get(1).toString(), datav.get(2).toString(), datav.get(3).toString(), datav.get(4).toString(), datav.get(6).toString(), datav.get(5).toString(), datav.get(7).toString(), datav.get(9).toString());

        LOGGER.error(datav.get(1).toString() + ">--<" + datav.get(2).toString() + ">--<" + datav.get(3).toString() + ">--<" + datav.get(4).toString() + ">--<" + datav.get(6).toString() + ">--<" + datav.get(5).toString() + ">--<" + datav.get(7).toString() + ">--<" + datav.get(9).toString());

        message = "";

        //si no tiene informacion el archivo
        if (datav.size() < 2) {
            return;
        }
        //si la cantidad de informacion coincide con en numero de columnas
        if (datav.size() == nameColumn.size()) {
            String columnToProcess = "";
            String data = "";
            //procesamos la informacion de cada elemento de la fila
            for (int columnNumber = 0; columnNumber < datav.size(); columnNumber++) {
                columnToProcess = nameColumn.get(columnNumber).toString().trim().replace(" ", "");
                data = datav.get(columnNumber).toString();
                errorscol = false;
                try {
                    ConstantWEB.NAME_COLUMN columnName = ConstantWEB.NAME_COLUMN.valueOf(columnToProcess.toUpperCase());
                    switch (columnName) {
                        case NOMBRE:
                            errorscol = valideFile.validateNombre(data);
                            break;
                        case VIAPRINCIPAL:
                            errorscol = valideFile.validateCalles(data);
                            break;
                        case VIAGENERADORA:
                            errorscol = valideFile.validatePlaca(data);
                            break;
                        case TOTALHHPP:
                            errorscol = valideFile.validateTotalPphh(data);
                            break;
                        case APTOS:
                            errorscol = valideFile.validateAtpos(data);
                            break;
                        case LOCALES:
                            errorscol = valideFile.validateLocales(data);
                            break;
                        case OFICINAS:
                            errorscol = valideFile.validateOficinas(data);
                            break;
                        case PISOS:
                            errorscol = valideFile.validatePisos(data);
                            break;
                        case NODO:
                            errorscol = valideFile.validateNodo(data, city);
                            break;
                        case BARRIO:
                            errorscol = valideFile.validateBarrio(data, city);
                            break;
                        case ESTRATO:
                            errorscol = valideFile.validateEstrato(data);
                            break;
                        case DISTRIBUCION:
                            errorscol = valideFile.valDistHhpp(data);
                            break;
                        case VALIDAR:
                            errorscol = valideFile.valValidar(data);
                            break;
                    }

                } catch (IllegalArgumentException e) {
                    LOGGER.error("Se produjo un error al momento de validar la fila: " + e.getMessage(), e);
                    oespr.setErrores_presentados("Error: el nombre de la columna [" + columnToProcess + "] no  ");
                    errorscol = true;
                } catch (Exception e) {
                    LOGGER.error("Se produjo un error al momento de validar la fila: " + e.getMessage(), e);
                    oespr.setErrores_presentados("Error: el nombre de la columna [" + columnToProcess + "] no  ");
                    errorscol = true;
                }

                oespr = valideFile.getOespr();
                if (errorscol) {
                    oespr.setErrores_presentados(valideFile.setMesageError(columnToProcess, data, numFila + ""));
                    message = MSG_ERROR_ESTRUCTURA;
                    setMessage(message);
                    showBotonGuardar = false;
                    errors = true;
                }
            }
            boolean errorGeo = false;
            oespr.setDepartamento(loadState().getGpoNombre());
            oespr.setCiudad(city.getGpoNombre());

            oespr.setDirecciones(valideFile.getHHPPs());
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setAddress(oespr.getCalle() + " # " + oespr.getPlaca().split("/")[0]);
            addressRequest.setCity(oespr.getCiudad());
            addressRequest.setLevel("C");
            addressRequest.setState(oespr.getDepartamento());
            addressRequest.setNeighborhood(oespr.getBarrio());
            try {
                AddressService addressService = GeoreferenciaDelegate.queryAddress(addressRequest);
                oespr.setAddressValidated(addressService);
                if (addressService.getAddressCode() == null || addressService.getAddressCode().isEmpty()) {
                    errorGeo = true;
                    oespr.setErrores_presentados("; Las direcciones no traducibles no se pueden procesar, en ficha nodo visitas tecnicas");
                }
                if (oespr.getValidar().toUpperCase().replace("\n", "").contains("S")) {
                    if (!addressService.getExist().toUpperCase().equals("TRUE")) {
                        errorGeo = true;
                        oespr.setErrores_presentados("; Direccion no existe según georeferenciación");
                    }
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Se produjo un error al momento de validar la fila: " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se produjo un error al momento de validar la fila: " + e.getMessage(), e, LOGGER);
            }
            if (errorGeo) {
                errorscol = true;
                message = MSG_ERROR_ESTRUCTURA;
                setMessage(message);
                showBotonGuardar = false;
                errors = true;
            }
            if (repeatThisAddress(todasLasDirecciones, oespr.getDirecciones())) {
                oespr.setErrores_presentados("; Direccion repetida en el Archivo de Cargue");
                errorscol = true;
                message = MSG_ERROR_ESTRUCTURA;
                setMessage(message);
                showBotonGuardar = false;
                errors = true;
            } else {
                todasLasDirecciones.addAll(oespr.getDirecciones());
            }
        }

        if (errors) {
            message = MSG_ERROR_ESTRUCTURA;
            setMessage(message);
            showBotonGuardar = false;
        }
        LOGGER.error(oespr.toString() + "\n");
        results.add(oespr);
        setDetalleresult(results);
    }

    private boolean repeatThisAddress(List<String> direccionesTotal, List<String> direccionesAVerificar) {
        for (String dirVerActual : direccionesAVerificar) {
            for (String Verficadas : direccionesTotal) {
                if (Verficadas.equals(dirVerActual)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     */
    public void descargarCvs() {
        final StringBuffer sb = new StringBuffer();
        if (errorColumnas) {
            for (int i = 0; i < errorColumnasArchivo.size(); i++) {
                sb.append(errorColumnasArchivo.get(i));
            }
            errorColumnasArchivo.clear();
            errorColumnasArchivo = null;
            errorColumnas = false;
            descargar = false;
        } else {
            sb.append("NOMBRE");
            sb.append(',');
            sb.append("VIAPRINCIPAL");
            sb.append(',');
            sb.append("VIAGENERADORA");
            sb.append(',');
            sb.append("TOTALHHPP");
            sb.append(',');
            sb.append("APTOS");
            sb.append(',');
            sb.append("LOCALES");
            sb.append(',');
            sb.append("OFICINAS");
            sb.append(',');
            sb.append("PISOS");
            sb.append(',');
            sb.append("BARRIO");
            sb.append(',');
            sb.append("DISTRIBUCION");
            sb.append(',');
            sb.append("NODO");
            sb.append(',');
            sb.append("ESTRATO");
            sb.append(',');
            sb.append("VALIDAR");
            sb.append(',');
            sb.append("ERRORES");
            sb.append("\n");

            for (OutputElementSolPlaneacionRed data : results) {
                sb.append(data.getNombre());
                sb.append(',');
                sb.append(data.getCalle());
                sb.append(',');
                sb.append(data.getPlaca());
                sb.append(',');
                sb.append(data.getTotalHhpp());
                sb.append(',');
                sb.append(data.getAptos());
                sb.append(',');
                sb.append(data.getLocales());
                sb.append(',');
                sb.append(data.getOficinas());
                sb.append(',');
                sb.append(data.getPisos());
                sb.append(',');
                sb.append(data.getBarrio());
                sb.append(',');
                sb.append(data.getDistribucion());
                sb.append(',');
                sb.append(data.getNodo());
                sb.append(',');
                sb.append(data.getEstrato());
                sb.append(',');
                sb.append(data.getValidar());
                sb.append(',');
                sb.append(data.getErrores_presentados());
                sb.append("\n");
            }
        }

        byte[] csvData = null;

        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException e) {
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de descargar el CSV: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se produjo un error al momento de descargar el CSV: " + e.getMessage(), e, LOGGER);
        }
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attached; filename=\"consultaMasiva.csv\"");
        response.setContentType("application/force.download");
        try {
            response.getOutputStream().write(csvData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (final IOException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: descargarCvs()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: descargarCvs()" + e.getMessage(), e, LOGGER);
        }
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
            LOGGER.error("Error de formato numérico: " + ex.getMessage());
            return valueIfInvalid;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: parseInt()" + e.getMessage(), e, LOGGER);
            String msg = "Error en SolicitudCreaHHPPDisenoRedMBean: parseInt()" + e.getMessage();
            LOGGER.error(msg);
        }
        return 0;
    }

    /**
     *
     */
    //jaof
    public void create() {

        if (getDescargar() == false) {
            try {
                List<DetalleSolicitud> DetallesSolicitud;
                SolicitudRed headSolictudRed = new SolicitudRed();
                headSolictudRed.setSreObservaciones((String) session.getAttribute("observaciones"));
                headSolictudRed.setGeograficoPolitico(loadCity());
                headSolictudRed.setSreUsuarioCreacion("");
                headSolictudRed.setSreUsuarioModificacion("");
                DetallesSolicitud = createDetalleSolicitud(detalleresult);
                DetallesSolicitud.get(0).setSolicitudRed(headSolictudRed);
                BigDecimal idSol = SolicitudRedDelegate.createSolicitudPlaneacionRedMasivo(DetallesSolicitud);
                session.removeAttribute("observaciones");

                if (idSol != null && idSol.compareTo(BigDecimal.ZERO) != 0) {
                    this.message = "Creación de Solicitud de red con id " + idSol + " exitosa";
                } else {
                    errors = true;
                    validarCampos2(message);
                    this.message = "El archivo tiene inconsistencias de datos: verifique que la ciudad corresponda con la dirección, si el problema persiste genere caso HelpDesk";
                }
            } catch (NullPointerException ne) {
                this.message = "El archivo tiene inconsistencias de datos: verifique que la ciudad corresponda con la dirección, si el problema persiste genere caso HelpDesk";
                errors = true;
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ne.getMessage();
                LOGGER.error(msg);
                validarCampos2(message);
            } catch (ApplicationException ex) {
                errors = true;
                this.message = "El archivo tiene inconsistencias de datos: verifique que la ciudad corresponda con la dirección, si el problema persiste genere caso HelpDesk";
                validarCampos2(message);
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);

             } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                FacesUtil.mostrarMensajeError("Error en SolicitudCreaHHPPDisenoRedMBean: create()" + e.getMessage(), e, LOGGER);
            }
        } else {
            errors = true;
            this.message = "El archivo tiene inconsistencias de Estructura";
            validarCampos(message);
        }
    }

    private List<DetalleSolicitud> createDetalleSolicitud(List<OutputElementSolPlaneacionRed> solPlaneacionRed) {
        List<DetalleSolicitud> detallesSolicitud = new ArrayList<DetalleSolicitud>();
        DetalleSolicitud newRegistro;
        for (OutputElementSolPlaneacionRed reg : solPlaneacionRed) {
            for (String dir : reg.getDirecciones()) {
                newRegistro = new DetalleSolicitud();
                newRegistro.setNombre(reg.getNombre());
                newRegistro.setCalle(reg.getCalle());
                newRegistro.setPlaca(reg.getPlaca());
                newRegistro.setBarrio(reg.getBarrio());
                newRegistro.setDireccionSta(reg.getAddressValidated().getAddress());
                newRegistro.setBarrioGeo(reg.getAddressValidated().getNeighborhood());
                newRegistro.setConfiabilidad(BigDecimal.valueOf(Long.parseLong((reg.getAddressValidated().getQualifiers().isEmpty()) ? "0" : reg.getAddressValidated().getQualifiers())));
                newRegistro.setDireccionAPlaca(reg.getCalle() + " " + reg.getPlaca().split("/")[0]);
                newRegistro.setDireccionAComplemento(dir.toString());
                newRegistro.setDireccionAltSta(reg.getAddressValidated().getAlternateaddress());
                newRegistro.setExiste(reg.getAddressValidated().getExist());
                newRegistro.setFuenteuente(reg.getAddressValidated().getSource());
                newRegistro.setLatitud(reg.getAddressValidated().getCy());
                newRegistro.setLocalidad(reg.getAddressValidated().getLocality());
                newRegistro.setLongitud(reg.getAddressValidated().getCx());
                newRegistro.setManzana(reg.getAddressValidated().getAppletstandar());
                if (reg.getEstrato().isEmpty()) {
                    newRegistro.setNivelSocioeconomico(BigDecimal.valueOf(Long.parseLong((reg.getAddressValidated().getLeveleconomic().isEmpty()) ? "-1" : reg.getAddressValidated().getLeveleconomic())));
                } else {
                    newRegistro.setNivelSocioeconomico(BigDecimal.valueOf(Long.parseLong(reg.getEstrato())));
                }
                newRegistro.setNodo(reg.getNodo());
                newRegistro.setServinformacion(reg.getAddressValidated().getAddressCode());
                newRegistro.setZipcode(reg.getAddressValidated().getZipCode().substring(0, 3));
                newRegistro.setTipoSolictud(Constant.TYPE_SOLICITUD_FICHANODO);
                newRegistro.setNumeroUnidadRR("");
                newRegistro.setCalleRR("");
                newRegistro.setNumeroAoartamentoRR("");
                newRegistro.setEstadoHHPP(Constant.HHPP_STATE_NUNCA);
                newRegistro.setActividadEconomica(reg.getAddressValidated().getActivityeconomic());
                newRegistro.setValidadar(reg.getValidar());
                newRegistro.setTipoRedHHPP(Constant.HHPP_BIDIRECCIONAL);
                newRegistro.setTipoConexionHHPP(Constant.HHPP_TIPO_CONEXION_CAJA_RELIANCE);
                newRegistro.setTipoHhpp("");
                newRegistro.setUsuarioCreacion("");
                newRegistro.setUsuarioModificacion("");
                detallesSolicitud.add(newRegistro);
            }
        }
        return detallesSolicitud;
    }

    public String newStart() {
        geog = true;
        enviarv = true;
        results = null;
        pintarNuevo = false;
        dir_pais = "";
        dir_regional = "";
        dir_ciudad = "";
        observaciones = "";
        uploadf = false;
        message = "";
        descargar = false;
        observaciones = "";
        System.gc();
        return null;
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
    public boolean getUploadf() {
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
     * @return the geog
     */
    public boolean isGeog() {
        return geog;
    }

    /**
     * @param geog the geog to set
     */
    public void setGeog(boolean geog) {
        this.geog = geog;
    }

    /**
     * @return the enviarv
     */
    public boolean getEnviarv() {
        return enviarv;
    }

    /**
     * @param enviarv the enviarv to set
     */
    public void setEnviarv(boolean enviarv) {
        this.enviarv = enviarv;
    }

    /**
     * @return the pintarNuevo
     */
    public boolean getPintarNuevo() {
        return pintarNuevo;
    }

    /**
     * @param pintarNuevo the pintarNuevo to set
     */
    public void setPintarNuevo(boolean pintarNuevo) {
        this.pintarNuevo = pintarNuevo;
    }
}
