package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.delegate.ConsultaMasivaDelegate;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.delegate.ValidacionDirDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.mbeans.direcciones.common.Constantes;
import co.com.telmex.catastro.mbeans.direcciones.common.Utilidades;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import co.com.telmex.catastro.util.FacesUtil;
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
 * Clase ConsultaMasivaMBean Extiende de BaseMBean
 *
 * @author Deiver Rovira
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version	1.2 - Modificado por: Direcciones face II Davide Marangoni
 * 2013-09-05
 */
@ViewScoped
@ManagedBean(name = "consultaMasivaMBean")
public class ConsultaMasivaMBean extends BaseMBean {

    /**
     *
     */
    public String LABEL_CRUCE_DE_CALLES = "Cruce de Calles";
    /**
     *
     */
    public String VALUE_CRUCE_DE_CALLES = "CRUCE";
    /**
     *
     */
    public String LABEL_PROP_DIR = "Propiedades de la dirección";
    /**
     *
     */
    public String VALUE_PROP_DIR = "PROP";
    /**
     *
     */
    public String LABEL_PROP_SI_CTA_MATRIZ = "SI";
    /**
     *
     */
    public String VALUE_PROP_SI_CTA_MATRIZ = "1";
    /**
     *
     */
    public String LABEL_PROP_NO_CTA_MATRIZ = "NO";
    /**
     *
     */
    public String VALUE_PROP_NO_CTA_MATRIZ = "0";
    /**
     *
     */
    public String dir_tipoConsulta;
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
    public String dir_barrio;
    /**
     *
     */
    public String vacio;
    /**
     *
     */
    public String tipoCiudadSeleccionada;
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
    private List<SelectItem> listLetrasyNumeros = null;
    private List<Multivalor> letrasynumeros = null;
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
    //Variables para consulta de direcciones
    /**
     *
     */
    public String consulta_calle;
    /**
     *
     */
    public String consulta_carrera;
    private GeograficoPolitico city = null;
    private GeograficoPolitico dpto = null;
    //Scroll de la tabla de resultados
    private int scrollerPage = 1;
    //Variables del panel de propiedades de la direccion
    /**
     *
     */
    public String con_nodo = "";
    /**
     *
     */
    public String con_barrio = "";
    /**
     *
     */
    public String con_cuentaMatriz = "";
    /**
     *
     */
    public String con_tipoRed = "";
    /**
     *
     */
    public String con_estrato = "";
    /**
     *
     */
    public String con_nivelSocio = "";
    public List<SelectItem> listNodos = null;
    private List<Nodo> nodos = null;
    /**
     *
     */
    public List<SelectItem> listTipoRed = null;
    private List<TipoHhppRed> tiposRed = null;
    /**
     *
     */
    public List<SelectItem> listEstratos = null;
    private List<Multivalor> estratos = null;
    /**
     *
     */
    public List<SelectItem> listNivelSocio = null;
    private List<Multivalor> nivelSocio = null;
    // Inicio mdificacion Direcciones face II Davide Marangoni 2013-09-05
    public List<SelectItem> listNodoUno = null;
    public List<SelectItem> listNodoDos = null;
    public List<SelectItem> listNodoTres = null;
    private String con_nodoUno = "";
    private String con_nodoDos = "";
    private String con_nodoTres = "";
    // Fin mdificacion Direcciones face II Davide Marangoni 2013-09-05
    public List<ConsultaMasivaTable> listConsultamasiva = new ArrayList<ConsultaMasivaTable>();
    //Variables para renderizar paneles
    private boolean showTipoBogota;
    private boolean showTipoMedellin;
    private boolean showTipoCali;
    private boolean showBotonConsultar;
    private boolean showBotonExportar;
    private boolean showPanelPrincipal;
    private boolean showPanelPropiedades;
    private boolean showPanelCruce;
    private boolean showTablaResultado;
    //variable de log para la clase
    private String nombreLog;
    /**
     *
     */
    public ConsultaMasivaTable consultaMasiva;
    private static final String ACRONIMO_MAX_CANTIDAD_REGISTROS = "MAX_CANT_REGISTROS";
    private static String MAX_CANTIDAD_REGISTROS = "1000";
    //Variables de secion
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ConsultaMasivaMBean.class);

    /**
     *
     * @throws IOException
     */
    public ConsultaMasivaMBean() throws IOException {
        super();
        //Se agrega validacion de segurida y de session entre Visistas Tecnicas y Catastro
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ValidarDirUnoAUnoMBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ValidarDirUnoAUnoMBean class ..." + e.getMessage(), e, LOGGER);
        }
        initLists();
        cargarPaises();
    }

    /**
     * Carga los paises de la Base de datos
     */
    private void cargarPaises() {
        listPaises = new ArrayList<SelectItem>();
        try {
            obtenerParametros();
            paises = SolicitudNegocioDelegate.queryPaises();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarPaises" + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarNodos(String pais, String departamento, String ciudad) throws ApplicationException {

        try {
            this.listNodoUno = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoUno, pais, departamento, ciudad, "ND1");
            this.listNodoDos = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoDos, pais, departamento, ciudad, "ND2");
            this.listNodoTres = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoTres, pais, departamento, ciudad, "ND3");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarNodos" + e.getMessage(), e, LOGGER);
        }
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
            FacesUtil.mostrarMensajeError("Error en initLists." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en initLists." + e.getMessage(), e, LOGGER);
        }
    }

    /*Inicializa las listas necesarias de las propiedades de la dirección */
    /**
     *
     */
    private void initPropiedadesList() {
        listNodos = new ArrayList<SelectItem>();
        listTipoRed = new ArrayList<SelectItem>();
        listNivelSocio = new ArrayList<SelectItem>();
        listEstratos = new ArrayList<SelectItem>();
        try {
            nodos = ConsultaMasivaDelegate.queryNodos();
            tiposRed = SolicitudNegocioDelegate.queryTipoRed();
            nivelSocio = SolicitudNegocioDelegate.loadMultivalores("17");
            estratos = SolicitudNegocioDelegate.queryEstrato();

            for (Nodo nodo : nodos) {
                SelectItem item = new SelectItem();
                item.setValue(nodo.getNodId().toString());
                item.setLabel(nodo.getNodCodigo());
                listNodos.add(item);
            }
            for (TipoHhppRed letra : tiposRed) {
                SelectItem item = new SelectItem();
                item.setValue(letra.getThrId().toString());
                item.setLabel(letra.getThrCodigo());
                listTipoRed.add(item);
            }
            for (Multivalor prefijo : nivelSocio) {
                SelectItem item = new SelectItem();
                item.setValue(prefijo.getMulValor());
                item.setLabel(prefijo.getDescripcion());
                listNivelSocio.add(item);
            }
            for (Multivalor cardinal : estratos) {
                SelectItem item = new SelectItem();
                item.setValue(cardinal.getMulValor());
                item.setLabel(cardinal.getDescripcion());
                listEstratos.add(item);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en initPropiedadesList." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * ActionListener ejecutado al seleccionar el tipo de consulta
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void doEnviar() throws ApplicationException {
        limpiarCamposTipoDireccion();
        limpiarFiltrosPropiedades();
        if (validarCamposObligatorios().equals("")) {
            if (dir_tipoConsulta.equals(VALUE_CRUCE_DE_CALLES)) {
                showPanelPrincipal = true;
                showTablaResultado = false;
                showPanelCruce = true;
                showPanelPropiedades = false;
                showBotonConsultar = true;
                message = "";
            } else if (dir_tipoConsulta.equals(VALUE_PROP_DIR)) {
                showTablaResultado = false;
                showPanelPrincipal = true;
                showPanelCruce = false;
                showPanelPropiedades = true;
                try {
                    initPropiedadesList();
                } catch (Exception e) {
                    LOGGER.error("Error cargando las listas de las propiedades de la dirección." + e.getMessage(), e);
                    message = "Error cargando las listas de las propiedades de la dirección.";
                }
                showBotonConsultar = true;
                con_nodoUno = "";
                con_nodoDos = "";
                con_nodoTres = "";
                cargarNodos(dir_pais, dir_regional, dir_ciudad);
                message = "";
            } else {
                message = Constant.OBLIGATORIO_TIPO_CONSULTA;
                showBotonConsultar = false;
                showPanelPrincipal = false;
                showPanelCruce = false;
                showPanelPropiedades = false;
            }
        }
    }

    /**
     * ActionListener ejecutado al solicitar la validacion de la direccion
     *
     * @param ev
     */
    public void doConsultar(ActionEvent ev) {
        message = "";
        showTablaResultado = false;
        //Se validan los campos obligatorios

        if (validarCamposObligatorios().equals("")) {
            message = validacionSegmentosObligatorios();
            UIComponent cp = ev.getComponent();
            if (message.isEmpty()) {
                showTablaResultado = true;
                //Se muestra el panel de consulta sencilla
                if (dir_tipoConsulta.equals(VALUE_CRUCE_DE_CALLES)) {

                    String dirNoEstandar = crearCalleCarrera();
                    //Estandarizar los parametros ingresados por el usuario
                    AddressRequest request = new AddressRequest();
                    request.setAddress(dirNoEstandar);
                    city = loadCity();
                    request.setCity(city.getGpoNombre());
                    dpto = loadRegional();
                    request.setState(dpto.getGpoNombre());
                    request.setLevel(Constant.TIPO_CONSULTA_SENCILLA);
                    AddressGeodata geodata = null;
                    try {
                        geodata = GeoreferenciaDelegate.queryAddressGeodata(request);
                    } catch (ApplicationException e) {
                        message = "Error al consultar con los parámetros ingresados: " + e.getMessage();
                        LOGGER.error(message, e);
                        return;
                    } catch (Exception e) {
                        message = "Error al consultar con los parámetros ingresados: " + e.getMessage();
                        LOGGER.error(message, e);
                        return;
                    }
                    if (geodata != null && !geodata.getCoddir().isEmpty()) {
                        String codigoDireccion = geodata.getCoddir();
                        String codConsulta = "";
                        //Obtener el coddir dependiendo el tipoDireccion
                        if (tipoCiudadSeleccionada != null) {
                            if (tipoCiudadSeleccionada.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_MEDELLIN)) {
                                codConsulta = codigoDireccion.substring(10, 28);
                            } else if (tipoCiudadSeleccionada.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_BOGOTA)) {
                                codConsulta = codigoDireccion.substring(10, 28);
                            } else if (tipoCiudadSeleccionada.toUpperCase().equals(Constant.CODIGO_TIPO_CIUDAD_CALI)) {
                                codConsulta = codigoDireccion.substring(10, 36);
                            } else {
                                message = "Error, la ciudad seleccionada no tiene un tipo de codigo de dirección.";
                            }
                        }
                        try {
                            //Se procede a llenar la tabla de resultados
                            consultaPorCruce(codConsulta);
                            showBotonConsultar = true;
                        } catch (Exception e) {
                            showTablaResultado = false;
                            message = "Error al consultar la direccion en el repositorio con cod: " + codConsulta + ": " + e.getMessage();
                            LOGGER.error(message, e);
                        }
                    } else {
                        message = "Error al consultar la dirección con estos parámetros, por favor valide e inténtelo nuevamente";
                        showTablaResultado = false;
                        showBotonExportar = false;
                    }

                } //Se consulta por las propiedades de la direccion
                else if (dir_tipoConsulta.equals(VALUE_PROP_DIR)) {
                    try {
                        consultaPorPropiedades();
                    } catch (Exception e) {
                        message = "Error al consultar por las propiedades de la dirección: " + e.getMessage();
                        LOGGER.error(message + e.getMessage(), e);
                    }
                }
            } else {
                showTablaResultado = false;
            }
        }
    }

    /**
     *
     * @return
     */
    private String crearCalleCarrera() {
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
            LOGGER.error("Error en crearCalleCarrera: " + e.getMessage(), e);
            bool = 0;
        } catch (Exception e) {
            LOGGER.error("Error en crearCalleCarrera: " + e.getMessage(), e);
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
            LOGGER.error("Error en crearCalleCarrera: " + e.getMessage(), e);
            bool2 = 0;
        } catch (Exception e) {
            LOGGER.error("Error en crearCalleCarrera: " + e.getMessage(), e);
            bool2 = 0;
        }

        String separadorPlacaParte1 = "";
        if (bool2 != 0) {
            separadorPlacaParte1 = "-";
        }

        consulta_calle = tipocalle + " " + son_calle + " " + separadorCalleCarrera + son_letraCalle + " " + son_calle2 + " " + son_letraCalle2 + " " + son_prefijoCalle + " " + son_calle3 + " " + son_letraCalles + " " + cardinalCalle;

        consulta_carrera = " " + tipocalle2 + " " + son_placa1 + " " + separadorPlacaParte1 + son_letraPlaca + " " + son_calle4 + " " + son_letraCalle3 + " " + son_prefijoPlaca + " " + son_letraCalle4 + " 00 " + cardinalPlaca;

        return consulta_calle + " # " + consulta_carrera;
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

    /**
     * Metodo que exporta un dataTable a CSV
     */
    public final void doExportSelectedDataToCSV() {
        Utilidades.doExportSelectedDataToCSV(listConsultamasiva, false);
    }

    /**
     * Consulta las direciones por calle-carrera
     *
     * @param calle
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaPorCruce(String calle) throws ApplicationException {

        listConsultamasiva = new ArrayList<ConsultaMasivaTable>();
        listConsultamasiva = ConsultaMasivaDelegate.queryConsultaMasivaPorCruce(calle, dir_ciudad, MAX_CANTIDAD_REGISTROS);
        if (listConsultamasiva == null) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     * Consulta las direciones por calle-carrera
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaPorPropiedades() throws ApplicationException {
        message = validarCamposPropiedades();
        if (!message.isEmpty()) {
            showTablaResultado = false;
            return;
        }

        listConsultamasiva = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";

            listConsultamasiva = ConsultaMasivaDelegate.queryConsultaMasivaPorPropiedades(con_nodoUno, con_nodoDos, con_nodoTres,
                    con_estrato, con_nivelSocio, barrioAux, dir_ciudad, MAX_CANTIDAD_REGISTROS);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaPorPropiedades" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultamasiva == null || listConsultamasiva.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     *
     * @return
     */
    private String validarCamposPropiedades() {
        String msg = "";
        if ((con_nodoUno == null || con_nodoUno.isEmpty())
                && (con_nodoDos == null || con_nodoDos.isEmpty())
                && (con_nodoTres == null || con_nodoTres.isEmpty())
                && (con_estrato == null || con_estrato.isEmpty())
                && (con_nivelSocio == null || con_nivelSocio.isEmpty())
                && (con_barrio.isEmpty())) {

            msg = "Debe seleccionar por lo menos un criterio de búsqueda";
        }

        if (msg.isEmpty()) {
            if (con_nodoUno == null || con_nodoUno.isEmpty()) {
                con_nodoUno = Constantes.DIR_NODOUNO;
            }
            if (con_nodoDos == null || con_nodoDos.isEmpty()) {
                con_nodoDos = Constantes.DIR_NODODOS;
            }
            if (con_nodoTres == null || con_nodoTres.isEmpty()) {
                con_nodoTres = Constantes.DIR_NODOTRES;
            }
            if (con_estrato == null || con_estrato.isEmpty()) {
                con_estrato = "D.DIR_ESTRATO";
            }
            if (con_nivelSocio == null || con_nivelSocio.isEmpty()) {
                con_nivelSocio = "D.DIR_NIVEL_SOCIOECONO";
            }
            if (con_barrio != null && con_barrio.isEmpty()) {
                con_barrio = "G.GEO_NOMBRE";
            }

        }
        return msg;
    }

    /**
     *
     * @return
     */
    private String validacionSegmentosObligatorios() {
        message = "";
        if (dir_tipoConsulta.equals(VALUE_CRUCE_DE_CALLES)) {
            if ("0".equals(son_tcalle) || "".equals(son_calle)) {
                message = Constant.OBLIGATORIO_SEGMENTO_1;
            } else if ("".equals(son_placa1)) {
                message = Constant.OBLIGATORIO_SEGMENTO_2;
            }
        }
        return message;
    }

    /**
     * Valida los campos obligatorios del formulario
     *
     * @return
     */
    private String validarCamposObligatorios() {
        message = "";

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
        return message;
    }

    /**
     * Actualiza la lista de Regionales de acuerdo al pais seleccionado
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
            FacesUtil.mostrarMensajeError("Error en updateRegionales" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateRegionales" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Actualiza la lista de ciudades de acuerdo a la region seleccionada
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event) throws ApplicationException {
        showTipoBogota = false;
        showTipoCali = false;
        showTipoMedellin = false;
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
            FacesUtil.mostrarMensajeError("Error en updateCiudades." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateCiudades." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateTipoDireccion(ValueChangeEvent event) throws ApplicationException {
        showPanelPrincipal = true;
        String codCiudad = event.getNewValue().toString();
        String tipoCiudad = null;
        try {
            tipoCiudad = ValidacionDirDelegate.queryTipoCiudadByID(codCiudad);
            tipoCiudadSeleccionada = tipoCiudad;
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
            FacesUtil.mostrarMensajeError("Error en updateTipoDireccion." + e.getMessage(), e, LOGGER);
        }

        limpiarCamposTipoDireccion();
    }

    /**
     * Limpia los campos que componen la direccion
     */
    private void limpiarCamposTipoDireccion() {
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
        son_complemento = "";
        son_apto = "";
    }

    /**
     *
     */
    private void limpiarFiltrosPropiedades() {
        con_barrio = "";
        con_nodoDos = "";
        con_estrato = "";
        con_nivelSocio = "";
        con_nodoTres = "";
        con_nodoUno = "";
    }

    /**
     * Obtiene la cantidad maxima de registros a mostrar en pantalla
     *
     * @throws Exception
     */
    private void obtenerParametros() throws ApplicationException {
        try {
            int maxCantRegistros = ConsultaMasivaDelegate.queryCantMaxRegistrosFromParametros(ACRONIMO_MAX_CANTIDAD_REGISTROS);
            MAX_CANTIDAD_REGISTROS = String.valueOf(maxCantRegistros);
        } catch (Exception e) {
            LOGGER.error("Error en obtenerParametros: " + e.getMessage(), e);
            MAX_CANTIDAD_REGISTROS = "1000";
        }
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
    public String getLABEL_CRUCE_DE_CALLES() {
        return LABEL_CRUCE_DE_CALLES;
    }

    /**
     *
     * @param LABEL_CRUCE_DE_CALLES
     */
    public void setLABEL_CRUCE_DE_CALLES(String LABEL_CRUCE_DE_CALLES) {
        this.LABEL_CRUCE_DE_CALLES = LABEL_CRUCE_DE_CALLES;
    }

    /**
     *
     * @return
     */
    public String getLABEL_PROP_DIR() {
        return LABEL_PROP_DIR;
    }

    /**
     *
     * @param LABEL_PROP_DIR
     */
    public void setLABEL_PROP_DIR(String LABEL_PROP_DIR) {
        this.LABEL_PROP_DIR = LABEL_PROP_DIR;
    }

    /**
     *
     * @return
     */
    public String getVALUE_CRUCE_DE_CALLES() {
        return VALUE_CRUCE_DE_CALLES;
    }

    /**
     *
     * @param VALUE_CRUCE_DE_CALLES
     */
    public void setVALUE_CRUCE_DE_CALLES(String VALUE_CRUCE_DE_CALLES) {
        this.VALUE_CRUCE_DE_CALLES = VALUE_CRUCE_DE_CALLES;
    }

    /**
     *
     * @return
     */
    public String getVALUE_PROP_DIR() {
        return VALUE_PROP_DIR;
    }

    /**
     *
     * @param VALUE_PROP_DIR
     */
    public void setVALUE_PROP_DIR(String VALUE_PROP_DIR) {
        this.VALUE_PROP_DIR = VALUE_PROP_DIR;
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
    public String getNombreLog() {
        return nombreLog;
    }

    /**
     *
     * @param nombreLog
     */
    public void setNombreLog(String nombreLog) {
        this.nombreLog = nombreLog;
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
    public boolean isShowBotonConsultar() {
        return showBotonConsultar;
    }

    /**
     *
     * @param showBotonConsultar
     */
    public void setShowBotonConsultar(boolean showBotonConsultar) {
        this.showBotonConsultar = showBotonConsultar;
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
    public boolean isShowPanelCruce() {
        return showPanelCruce;
    }

    /**
     *
     * @param showPanelCruce
     */
    public void setShowPanelCruce(boolean showPanelCruce) {
        this.showPanelCruce = showPanelCruce;
    }

    /**
     *
     * @return
     */
    public boolean isShowPanelPropiedades() {
        return showPanelPropiedades;
    }

    /**
     *
     * @param showPanelPropiedades
     */
    public void setShowPanelPropiedades(boolean showPanelPropiedades) {
        this.showPanelPropiedades = showPanelPropiedades;
    }

    /**
     *
     * @return
     */
    public boolean isShowPanelPrincipal() {
        return showPanelPrincipal;
    }

    /**
     *
     * @param showPanelPrincipal
     */
    public void setShowPanelPrincipal(boolean showPanelPrincipal) {
        this.showPanelPrincipal = showPanelPrincipal;
    }

    /**
     *
     * @return
     */
    public String getCon_barrio() {
        return con_barrio;
    }

    /**
     *
     * @param con_barrio
     */
    public void setCon_barrio(String con_barrio) {
        this.con_barrio = con_barrio;
    }

    /**
     *
     * @return
     */
    public String getCon_cuentaMatriz() {
        return con_cuentaMatriz;
    }

    /**
     *
     * @param con_cuentaMatriz
     */
    public void setCon_cuentaMatriz(String con_cuentaMatriz) {
        this.con_cuentaMatriz = con_cuentaMatriz;
    }

    /**
     *
     * @return
     */
    public String getCon_estrato() {
        return con_estrato;
    }

    /**
     *
     * @param con_estrato
     */
    public void setCon_estrato(String con_estrato) {
        this.con_estrato = con_estrato;
    }

    /**
     *
     * @return
     */
    public String getCon_nivelSocio() {
        return con_nivelSocio;
    }

    /**
     *
     * @param con_nivelSocio
     */
    public void setCon_nivelSocio(String con_nivelSocio) {
        this.con_nivelSocio = con_nivelSocio;
    }

    /**
     *
     * @return
     */
    public String getCon_nodo() {
        return con_nodo;
    }

    /**
     *
     * @param con_nodo
     */
    public void setCon_nodo(String con_nodo) {
        this.con_nodo = con_nodo;
    }

    /**
     *
     * @return
     */
    public String getCon_tipoRed() {
        return con_tipoRed;
    }

    /**
     *
     * @param con_tipoRed
     */
    public void setCon_tipoRed(String con_tipoRed) {
        this.con_tipoRed = con_tipoRed;
    }

    /**
     *
     * @return
     */
    public ConsultaMasivaTable getConsultaMasiva() {
        return consultaMasiva;
    }

    /**
     *
     * @param consultaMasiva
     */
    public void setConsultaMasiva(ConsultaMasivaTable consultaMasiva) {
        this.consultaMasiva = consultaMasiva;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> getEstratos() {
        return estratos;
    }

    /**
     *
     * @param estratos
     */
    public void setEstratos(List<Multivalor> estratos) {
        this.estratos = estratos;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListEstratos() {
        return listEstratos;
    }

    /**
     *
     * @param listEstratos
     */
    public void setListEstratos(List<SelectItem> listEstratos) {
        this.listEstratos = listEstratos;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListNivelSocio() {
        return listNivelSocio;
    }

    /**
     *
     * @param listNivelSocio
     */
    public void setListNivelSocio(List<SelectItem> listNivelSocio) {
        this.listNivelSocio = listNivelSocio;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListNodos() {
        return listNodos;
    }

    /**
     *
     * @param listNodos
     */
    public void setListNodos(List<SelectItem> listNodos) {
        this.listNodos = listNodos;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListTipoRed() {
        return listTipoRed;
    }

    /**
     *
     * @param listTipoRed
     */
    public void setListTipoRed(List<SelectItem> listTipoRed) {
        this.listTipoRed = listTipoRed;
    }

    /**
     *
     * @return
     */
    public List<Multivalor> getNivelSocio() {
        return nivelSocio;
    }

    /**
     *
     * @param nivelSocio
     */
    public void setNivelSocio(List<Multivalor> nivelSocio) {
        this.nivelSocio = nivelSocio;
    }

    /**
     *
     * @return
     */
    public List<Nodo> getNodos() {
        return nodos;
    }

    /**
     *
     * @param nodos
     */
    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    /**
     *
     * @return
     */
    public List<TipoHhppRed> getTiposRed() {
        return tiposRed;
    }

    /**
     *
     * @param tiposRed
     */
    public void setTiposRed(List<TipoHhppRed> tiposRed) {
        this.tiposRed = tiposRed;
    }

    /**
     *
     * @return
     */
    public String getLABEL_PROP_NO_CTA_MATRIZ() {
        return LABEL_PROP_NO_CTA_MATRIZ;
    }

    /**
     *
     * @param LABEL_PROP_NO_CTA_MATRIZ
     */
    public void setLABEL_PROP_NO_CTA_MATRIZ(String LABEL_PROP_NO_CTA_MATRIZ) {
        this.LABEL_PROP_NO_CTA_MATRIZ = LABEL_PROP_NO_CTA_MATRIZ;
    }

    /**
     *
     * @return
     */
    public String getLABEL_PROP_SI_CTA_MATRIZ() {
        return LABEL_PROP_SI_CTA_MATRIZ;
    }

    /**
     *
     * @param LABEL_PROP_SI_CTA_MATRIZ
     */
    public void setLABEL_PROP_SI_CTA_MATRIZ(String LABEL_PROP_SI_CTA_MATRIZ) {
        this.LABEL_PROP_SI_CTA_MATRIZ = LABEL_PROP_SI_CTA_MATRIZ;
    }

    /**
     *
     * @return
     */
    public String getVALUE_PROP_NO_CTA_MATRIZ() {
        return VALUE_PROP_NO_CTA_MATRIZ;
    }

    /**
     *
     * @param VALUE_PROP_NO_CTA_MATRIZ
     */
    public void setVALUE_PROP_NO_CTA_MATRIZ(String VALUE_PROP_NO_CTA_MATRIZ) {
        this.VALUE_PROP_NO_CTA_MATRIZ = VALUE_PROP_NO_CTA_MATRIZ;
    }

    /**
     *
     * @return
     */
    public String getVALUE_PROP_SI_CTA_MATRIZ() {
        return VALUE_PROP_SI_CTA_MATRIZ;
    }

    /**
     *
     * @param VALUE_PROP_SI_CTA_MATRIZ
     */
    public void setVALUE_PROP_SI_CTA_MATRIZ(String VALUE_PROP_SI_CTA_MATRIZ) {
        this.VALUE_PROP_SI_CTA_MATRIZ = VALUE_PROP_SI_CTA_MATRIZ;
    }

    /**
     *
     * @return
     */
    public List<ConsultaMasivaTable> getListConsultamasiva() {
        return listConsultamasiva;
    }

    /**
     *
     * @param listConsultamasiva
     */
    public void setListConsultamasiva(List<ConsultaMasivaTable> listConsultamasiva) {
        this.listConsultamasiva = listConsultamasiva;
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
    public String getConsulta_calle() {
        return consulta_calle;
    }

    /**
     *
     * @param consulta_calle
     */
    public void setConsulta_calle(String consulta_calle) {
        this.consulta_calle = consulta_calle;
    }

    /**
     *
     * @return
     */
    public String getConsulta_carrera() {
        return consulta_carrera;
    }

    /**
     *
     * @param consulta_carrera
     */
    public void setConsulta_carrera(String consulta_carrera) {
        this.consulta_carrera = consulta_carrera;
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
    public GeograficoPolitico getDpto() {
        return dpto;
    }

    /**
     *
     * @param dpto
     */
    public void setDpto(GeograficoPolitico dpto) {
        this.dpto = dpto;
    }

    /**
     *
     * @return
     */
    public String getTipoCiudadSeleccionada() {
        return tipoCiudadSeleccionada;
    }

    /**
     *
     * @param tipoCiudadSeleccionada
     */
    public void setTipoCiudadSeleccionada(String tipoCiudadSeleccionada) {
        this.tipoCiudadSeleccionada = tipoCiudadSeleccionada;
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
    public boolean isShowBotonExportar() {
        return showBotonExportar;
    }

    /**
     *
     * @param showBotonExportar
     */
    public void setShowBotonExportar(boolean showBotonExportar) {
        this.showBotonExportar = showBotonExportar;
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
     * Inicio mdificacion Direcciones face II Davide Marangoni 2013-09-05
     *
     * @return
     */
    public List<SelectItem> getListNodoUno() {
        return listNodoUno;
    }

    public void setListNodoUno(List<SelectItem> listNodoUno) {
        this.listNodoUno = listNodoUno;
    }

    public List<SelectItem> getListNodoDos() {
        return listNodoDos;
    }

    public void setListNodoDos(List<SelectItem> listNodoDos) {
        this.listNodoDos = listNodoDos;
    }

    public List<SelectItem> getListNodoTres() {
        return listNodoTres;
    }

    public void setListNodoTres(List<SelectItem> listNodoTres) {
        this.listNodoTres = listNodoTres;
    }

    public String getCon_nodoUno() {
        return con_nodoUno;
    }

    public void setCon_nodoUno(String con_nodoUno) {
        this.con_nodoUno = con_nodoUno;
    }

    public String getCon_nodoDos() {
        return con_nodoDos;
    }

    public void setCon_nodoDos(String con_nodoDos) {
        this.con_nodoDos = con_nodoDos;
    }

    public String getCon_nodoTres() {
        return con_nodoTres;
    }

    public void setCon_nodoTres(String con_nodoTres) {
        this.con_nodoTres = con_nodoTres;
    }

    /**
     * Fin mdificacion Direcciones face II Davide Marangoni 2013-09-05
     */
}
