package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.delegate.ConsultaEspecificaDelegate;
import co.com.telmex.catastro.delegate.ConsultaMasivaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
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
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase ConsultaEspecificaMBean Extiende de BaseMBean
 *
 * @author Deiver Rovira
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "consultaEspecificaMBean")
public class ConsultaEspecificaMBean extends BaseMBean {

    /**
     *
     */
    public String LABEL_FILTRO_EMPIEZA_POR = "Empieza por";
    /**
     *
     */
    public String LABEL_FILTRO_TERMINA_CON = "Termina con";
    /**
     *
     */
    public String LABEL_FILTRO_NO_CONTIENE = "No contiene";
    /**
     *
     */
    public String LABEL_FILTRO_ES_IGUAL_A = "Es igual a";
    /**
     *
     */
    public String LABEL_FILTRO_AUTOCOMPLETAR = "Autocompletar";
    /**
     *
     */
    public String LABEL_FILTRO_COMODIN = "Comodín de caracteres";
    /**
     *
     */
    public String VALUE_FILTRO_EMPIEZA_POR = "STARTWITH";
    /**
     *
     */
    public String VALUE_FILTRO_TERMINA_CON = "ENDSWITH";
    /**
     *
     */
    public String VALUE_FILTRO_NO_CONTIENE = "WHITOUT";
    /**
     *
     */
    public String VALUE_FILTRO_ES_IGUAL_A = "EQUALS";
    /**
     *
     */
    public String VALUE_FILTRO_AUTOCOMPLETAR = "AUTO";
    /**
     *
     */
    public String VALUE_FILTRO_COMODIN = "COMODIN";
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
    private static final String SEPARATOR = ";";
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
    public String dir_ingresadaTexto;
    /**
     *
     */
    public String dir_ingresadaAutocompletar;
    /**
     *
     */
    public String vacio;
    /**
     *
     */
    public String seleccionar;
    /**
     *
     */
    public String tipoCiudadSeleccionada;
    /**
     *
     */
    public List<SelectItem> listFiltros = null;
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
    //Variable para determinar si se debe validar el barrio
    private boolean validarBarrio = false;
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
    /**
     *
     */
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
    public List<SelectItem> listNodoUno = null;
    public List<SelectItem> listNodoDos = null;
    public List<SelectItem> listNodoTres = null;
    private String con_nodoUno = "";
    private String con_nodoDos = "";
    private String con_nodoTres = "";
    //Lista de la tabla de resultados
    /**
     *
     */
    public List<ConsultaMasivaTable> listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
    //Objeto que representa la direccion  consultada
    ConsultaMasivaTable dirConsultada = new ConsultaMasivaTable();
    //Variables para renderizar paneles
    private boolean showTipoBogota;
    private boolean showTipoMedellin;
    private boolean showTipoCali;
    private boolean showBotonConsultar;
    private boolean showBotonExportar;
    private boolean showAutocompletarInput = false;
    private boolean showDirIngresadaTexto = false;
    private boolean showPanelPrincipal = true;
    private boolean showPanelPropiedades = true;
    private boolean showPanelCruce;
    private boolean showTablaResultado;
    private boolean showMaestro = true;
    private boolean showDetalle = false;
    //variable de log para la clase
    private String nombreLog;
    //Variables resultado de la consulta
    private String cm_idDireccion;
    private String cm_direccion;
    private String cm_barrio;
    private String cm_tipoDireccion;
    private String cm_estrato;
    private String cm_nivelSocio;
    private String cm_confiabilidad;
    private String cm_localidad;
    private String cm_revisar;
    private String cm_barrioTemp;
    private String cm_localidadTemp;
    private Direccion dirAutocompletar;
    private ConsultaMasivaTable consultaMasiva;
    private List<Direccion> direcciones = new ArrayList<Direccion>();
    private ArrayList<String> direccionesString = new ArrayList<String>();
    private static final String ACRONIMO_MAX_CANTIDAD_REGISTROS = "MAX_CANT_REGISTROS";
    private static String MAX_CANTIDAD_REGISTROS = "1000";
    //Variables de secion
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ConsultaEspecificaMBean.class);

    /**
     *
     * @throws IOException
     */
    public ConsultaEspecificaMBean() throws IOException {
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
            FacesUtil.mostrarMensajeError("Se generea error en ConsultaEspecificaMBean class ..." + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConsultaEspecificaMBean class ..." + e.getMessage(), e, LOGGER);
        }
        initLists();
        initPropiedadesList();
    }

    /**
     * Inicializa las listas necesarias para crear la solicitud de Negocio
     */
    private void initLists() {
        try {
            obtenerParametros();
            cargarFiltros();
            cargarPaises();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en initLists" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Inicializa las listas necesarias de las propiedades de la dirección
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
            FacesUtil.mostrarMensajeError("Error en initPropiedadesList" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Autocopletar als direcciones
     *
     * @param suggest
     * @return
     */
    public List<Direccion> autocomplete(Object suggest) {
        String pref = (String) suggest;
        ArrayList<Direccion> result = new ArrayList<Direccion>();

        Iterator<Direccion> iterator = getDirecciones().iterator();
        while (iterator.hasNext()) {
            Direccion elem = ((Direccion) iterator.next());
            if ((elem.getDirFormatoIgac() != null && elem.getDirFormatoIgac().toLowerCase().indexOf(pref.toLowerCase()) == 0) || "".equals(pref)) {
                result.add(elem);
            }
        }
        return result;
    }

    /**
     *
     * @param igac
     * @return
     */
    public List<String> complete(String igac) {
        List<String> results = new ArrayList<String>();
        try {
            direcciones = (ArrayList<Direccion>) ConsultaEspecificaDelegate.queryAddressOnRepoByIgac(igac.toUpperCase(), dir_ciudad);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar las direccions por formato igac - autocompletar. Exception: {0}" + e.getMessage(), e, LOGGER);
        }
        if (direcciones != null && direcciones.size() > 0) {
            for (Direccion direccion : direcciones) {
                results.add(direccion.getDirFormatoIgac());
            }
        }
        return results;
    }

    /**
     * ActionListener ejecutado al seleccionar el tipo de consulta
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void doEnviar() throws ApplicationException {

        dir_ingresadaTexto = "";
        dir_ingresadaAutocompletar = "";

        if (dir_tipoConsulta.equals(VALUE_FILTRO_AUTOCOMPLETAR)) {
            //Se procede a buscar los primeros 10 registros que coincidan con los 4
            //primeros digitos ingresados
            //Limpia el form
            showAutocompletarInput = true;
            showDirIngresadaTexto = false;
        } else {
            showAutocompletarInput = false;
            showDirIngresadaTexto = true;
        }
        con_nodoUno = "";
        con_nodoDos = "";
        con_nodoTres = "";
        cargarNodos(dir_pais, dir_regional, dir_ciudad);
        message = "";
    }

    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void buscarDirecciones(ValueChangeEvent event) {
        try {
            Direccion dir = new Direccion();
            dir.setDirFormatoIgac("dir1");
            direcciones.add(dir);
            dir = new Direccion();
            dir.setDirFormatoIgac("dir2");
            direcciones.add(dir);
            dir = new Direccion();
            dir.setDirFormatoIgac("dir3");
            direcciones.add(dir);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarDirecciones" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * ActionListener ejecutado al solicitar la consulta de la direccion
     *
     * @param ev
     */
    public void doConsultar(ActionEvent ev) {
        message = "";

        //Se validan los campos obligatorios
        message = validarCamposObligatorios(validarBarrio);
        UIComponent cp = ev.getComponent();
        if (message.isEmpty()) {
            showTablaResultado = true;
            //Se consulta por las propiedades de la direccion
            try {
                //Se convierten los filtros para generar la consulta
                convertirFiltrosPropiedades();
                //Se debe identificar el tipo de filtro utilizado
                if (dir_tipoConsulta.equals(VALUE_FILTRO_EMPIEZA_POR)) {
                    //Se procede a llenar la tabla de resultados
                    consultaFiltroEmpiezaPor();
                } else if (dir_tipoConsulta.equals(VALUE_FILTRO_TERMINA_CON)) {
                    //Se procede a llenar la tabla de resultados
                    consultaFiltroTerminaCon();
                } else if (dir_tipoConsulta.equals(VALUE_FILTRO_NO_CONTIENE)) {
                    //Se procede a llenar la tabla de resultados
                    consultaFiltroNoContiene();
                } else if (dir_tipoConsulta.equals(VALUE_FILTRO_ES_IGUAL_A)) {
                    //Se procede a llenar la tabla de resultados
                    consultaFiltroEsIgualA();
                } else if (dir_tipoConsulta.equals(VALUE_FILTRO_AUTOCOMPLETAR)) {
                    //Se procede a buscar los primeros 10 registros que coincidan con los 3
                    //primeros digitos ingresados
                    dir_ingresadaTexto = dir_ingresadaAutocompletar;
                    consultaFiltroEsIgualA();
                } else if (dir_tipoConsulta.equals(VALUE_FILTRO_COMODIN)) {
                    //Se convierte el comodin ingresado al interpretado por Oracle
                    String dirConvertida = convertirComodin(dir_ingresadaTexto);
                    //Se procede a llenar la tabla de resultados
                    consultaFiltroComodin(dirConvertida);
                }
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al consultar por las propiedades de la dirección" + e.getMessage(), e, LOGGER);
                message = "Error al consultar por las propiedades de la dirección";
            }
        } else {
            showTablaResultado = false;
        }
    }

    /**
     *
     * @param ev
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void onSeleccionar(ActionEvent ev) throws ApplicationException {
        limpiarCamposPagDetalle();
        this.cm_idDireccion = ((BigDecimal) (((UIParameter) ev.getComponent().findComponent("idDir")).getValue())).toString();
        this.cm_barrioTemp = (String) (((UIParameter) ev.getComponent().findComponent("barrio")).getValue());
        this.cm_localidadTemp = (String) (((UIParameter) ev.getComponent().findComponent("localidad")).getValue());

        boolean hayError = false;
        if (cm_idDireccion != null) {
            Direccion direccion = null;
            try {
                direccion = ConsultaEspecificaDelegate.queryAddressOnRepoById(cm_idDireccion);
            } catch (Exception e) {
                LOGGER.error("Error en onSeleccionar" + e.getMessage(), e);
                hayError = true;
            }

            if (direccion != null) {
                cm_direccion = direccion.getDirFormatoIgac();
                cm_tipoDireccion = (direccion.getTipoDireccion() != null) ? direccion.getTipoDireccion().getTdiValor() : "";
                cm_estrato = (direccion.getDirEstrato() != null) ? direccion.getDirEstrato().toString() : "";
                cm_nivelSocio = (direccion.getDirNivelSocioecono() != null) ? direccion.getDirNivelSocioecono().toString() : "";
                cm_confiabilidad = (direccion.getDirConfiabilidad() != null) ? direccion.getDirConfiabilidad().toString() : "";
                cm_localidad = (cm_localidadTemp != null) ? cm_localidadTemp : "";
                cm_revisar = (direccion.getDirRevisar().equals("1")) ? "SI" : "NO";
                cm_barrio = (cm_barrioTemp != null) ? cm_barrioTemp : "";
                hayError = false;
            }

            if (hayError) {
                message = "Ocurrió un error al consultar esta dirección.";
            }
        }
    }

    /**
     *
     * @param dirIngresada
     * @return
     */
    private static String convertirComodin(String dirIngresada) {
        String igac = "";
        igac = dirIngresada.replace('*', '%');
        igac = igac.replace('?', '_');
        return igac;
    }

    /**
     *
     * @return @throws co.com.claro.mgl.error.ApplicationException
     */
    public String onIrAccion() throws ApplicationException {
        return "consultaEspecificaDetalle";
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
     * Consulta las direciones por filtro empieza por
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaFiltroEmpiezaPor() throws ApplicationException {
        listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";

            listConsultaEspecifica = ConsultaEspecificaDelegate.queryConsultaEspecificaFiltroEmpiezaPor(con_nodoUno, con_nodoDos, con_nodoTres, con_estrato,
                    con_nivelSocio, barrioAux, dir_ingresadaTexto, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (listConsultaEspecifica != null) {
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaFiltroEmpiezaPor. Error: Consulta Especifica-consultaFiltroEmpiezaPor,"
                    + " Exception:{0}Nodo: {1}Cuenta matriz:{2} tipoRed:{3} Estrato: {4} Nivel socio:{5} Barrio: {6}Dir ingresada:{7}Cod ciudad:{8}" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio != null && con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultaEspecifica == null || listConsultaEspecifica.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     * Consulta las direcciones por filtro Termina con
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaFiltroTerminaCon() throws ApplicationException {
        listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";
            listConsultaEspecifica = ConsultaEspecificaDelegate.queryConsultaEspecificaFiltroTerminaCon(con_nodoUno, con_nodoDos, con_nodoTres,
                    con_estrato, con_nivelSocio, barrioAux, dir_ingresadaTexto, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (listConsultaEspecifica != null) {
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaFiltroTerminaCon. Error: Consulta Especifica-consultaFiltroTerminaCon, "
                    + "Excetion:{0}Nodo: {1}Cuenta matriz:{2} tipoRed:{3} Estrato: {4} Nivel socio:{5} Barrio: {6}Dir ingresada:{7}Cod ciudad:{8}" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio != null && con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultaEspecifica == null || listConsultaEspecifica.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     * Consulta las direciones por filtro No Contiene
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaFiltroNoContiene() throws ApplicationException {
        listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";
            listConsultaEspecifica = ConsultaEspecificaDelegate.queryConsultaEspecificaFiltroNoContiene(con_nodoUno, con_nodoDos, con_nodoTres,
                    con_estrato, con_nivelSocio, barrioAux, dir_ingresadaTexto, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (listConsultaEspecifica != null) {
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaFiltroNoContiene" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio != null && con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultaEspecifica == null || listConsultaEspecifica.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     * Consulta las direciones por filtro Es Igual A
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaFiltroEsIgualA() throws ApplicationException {
        listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";

            listConsultaEspecifica = ConsultaEspecificaDelegate.queryConsultaEspecificaFiltroEsIgualA(con_nodoUno, con_nodoDos, con_nodoTres,
                    con_estrato, con_nivelSocio, barrioAux, dir_ingresadaTexto, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (listConsultaEspecifica != null) {
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaFiltroEsIgualA" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio != null && con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultaEspecifica == null || listConsultaEspecifica.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
            showBotonExportar = true;
        }
    }

    /**
     * Consulta las direciones por Comodín
     *
     * @param dirConvertida
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void consultaFiltroComodin(String dirConvertida) throws ApplicationException {
        listConsultaEspecifica = new ArrayList<ConsultaMasivaTable>();
        try {
            String barrioAux = (con_barrio.equals("G.GEO_NOMBRE")) ? "G.GEO_NOMBRE" : "'" + con_barrio.toUpperCase() + "'";
            listConsultaEspecifica = ConsultaEspecificaDelegate.queryConsultaEspecificaFiltroComodin(con_nodoUno, con_nodoDos, con_nodoTres,
                    con_estrato, con_nivelSocio, barrioAux, dirConvertida, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (listConsultaEspecifica != null) {
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaFiltroComodin" + e.getMessage(), e, LOGGER);
        }

        if (con_barrio != null && con_barrio.equals("G.GEO_NOMBRE")) {
            con_barrio = "";
        }

        if (listConsultaEspecifica == null || listConsultaEspecifica.isEmpty()) {
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
    private String convertirFiltrosPropiedades() {
        String msg = "";
        if (msg.isEmpty()) {
            if (con_nodo == null) {
                con_nodo = "H.NOD_ID";
            }
            if (con_cuentaMatriz == null) {
                con_cuentaMatriz = "U.UBI_CUENTA_MATRIZ";
            }
            if (con_tipoRed == null) {
                con_tipoRed = "TR.THR_ID";
            }
            if (con_estrato == null) {
                con_estrato = "D.DIR_ESTRATO";
            }
            if (con_nivelSocio == null) {
                con_nivelSocio = "D.DIR_NIVEL_SOCIOECONO";
            }
            if (con_barrio != null && con_barrio.isEmpty()) {
                con_barrio = "G.GEO_NOMBRE";
            }
            if (Utilidades.isVacioONulo(con_nodoUno)) {
                con_nodoUno = Constantes.DIR_NODOUNO;
            }
            if (Utilidades.isVacioONulo(con_nodoDos)) {
                con_nodoDos = Constantes.DIR_NODODOS;
            }
            if (Utilidades.isVacioONulo(con_nodoTres)) {
                con_nodoTres = Constantes.DIR_NODOTRES;
            }
        }
        return msg;
    }

    /**
     * Valida los campos obligatorios del formulario
     *
     * @return
     */
    private String validarCamposObligatorios(boolean validarBarrio) {
        message = "";

        if ("0".equals(dir_pais) || dir_pais == null) {
            message = Constant.OBLIGATORIO_PAIS;
        } else if ("0".equals(dir_regional) || dir_regional == null) {
            message = Constant.OBLIGATORIO_DEPARTAMENTO;
        } else if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = Constant.OBLIGATORIO_CIUDAD;
        } else if ("0".equals(dir_tipoConsulta) || dir_tipoConsulta == null) {
            message = Constant.OBLIGATORIO_TIPO_FILTRO;
        } else if (dir_ingresadaTexto.isEmpty() && dir_ingresadaAutocompletar.isEmpty()) {
            message = Constant.OBLIGATORIO_DIRECCION;
        } else if (!dir_ingresadaTexto.isEmpty()) {
            dir_ingresadaTexto = dir_ingresadaTexto.toUpperCase();
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
            FacesUtil.mostrarMensajeError("Error en updateCiudades" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en updateCiudades" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Selecciona el tipo de direccion a ingresar
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateTipoDireccion(ValueChangeEvent event) {
        String value = event.getNewValue().toString();
        if (value != null) {
            dir_tipoConsulta = value;
        }
    }

    /**
     *
     * @param event
     */
    public void updateValorCiudad(ValueChangeEvent event) {
        String value = event.getNewValue().toString();
        if (value != null && !value.isEmpty()) {
            dir_ciudad = value;
        }
    }

    /**
     * Limpia los campos que componen la direccion
     */
    private void limpiarCamposPagDetalle() {
        cm_direccion = "";
        cm_tipoDireccion = "";
        cm_estrato = "";
        cm_nivelSocio = "";
        cm_confiabilidad = "";
        cm_localidad = "";
        cm_revisar = "";
        cm_barrio = "";

        message = "";
//
    }

    /**
     * Carga los paises de la Base de datos
     */
    private void cargarPaises() {
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
            FacesUtil.mostrarMensajeError("Error en cargarPaises" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarPaises" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Carga los tipos de filtros
     */
    private void cargarFiltros() {
        listFiltros = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem();
        item.setValue(VALUE_FILTRO_EMPIEZA_POR);
        item.setLabel(LABEL_FILTRO_EMPIEZA_POR);
        listFiltros.add(item);
        item = new SelectItem();
        item.setValue(VALUE_FILTRO_TERMINA_CON);
        item.setLabel(LABEL_FILTRO_TERMINA_CON);
        listFiltros.add(item);
        item = new SelectItem();
        item.setValue(VALUE_FILTRO_NO_CONTIENE);
        item.setLabel(LABEL_FILTRO_NO_CONTIENE);
        listFiltros.add(item);
        item = new SelectItem();
        item.setValue(VALUE_FILTRO_ES_IGUAL_A);
        item.setLabel(LABEL_FILTRO_ES_IGUAL_A);
        listFiltros.add(item);
        item = new SelectItem();
        item.setValue(VALUE_FILTRO_AUTOCOMPLETAR);
        item.setLabel(LABEL_FILTRO_AUTOCOMPLETAR);
        listFiltros.add(item);
        item = new SelectItem();
        item.setValue(VALUE_FILTRO_COMODIN);
        item.setLabel(LABEL_FILTRO_COMODIN);
        listFiltros.add(item);
    }

    /**
     *
     * @throws Exception
     */
    private void obtenerParametros() throws ApplicationException {
        try {
            int maxCantRegistros = ConsultaEspecificaDelegate.queryCantMaxRegistrosFromParametros(ACRONIMO_MAX_CANTIDAD_REGISTROS);
            MAX_CANTIDAD_REGISTROS = String.valueOf(maxCantRegistros);
        } catch (Exception e) {
            MAX_CANTIDAD_REGISTROS = "1000";
        }
    }

    private void cargarNodos(String pais, String departamento, String ciudad) throws ApplicationException {

        try {
            listNodoUno = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoUno, pais, departamento, ciudad, "ND1");
            listNodoDos = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoDos, pais, departamento, ciudad, "ND2");
            listNodoTres = new ArrayList<SelectItem>();
            Utilidades.cargarNodo(listNodoTres, pais, departamento, ciudad, "ND3");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarNodos" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Metodo que exporta un dataTable a CSV
     */
    public final void doExportSelectedDataToCSV() {
        Utilidades.doExportSelectedDataToCSV(listConsultaEspecifica, false);
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
    public boolean isShowTablaResultado() {
        return showTablaResultado;
    }

    /**
     *
     * @return
     */
    public List<ConsultaMasivaTable> getListConsultaEspecifica() {
        return listConsultaEspecifica;
    }

    /**
     *
     * @param listConsultaEspecifica
     */
    public void setListConsultaEspecifica(List<ConsultaMasivaTable> listConsultaEspecifica) {
        this.listConsultaEspecifica = listConsultaEspecifica;
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
    public String getLABEL_FILTRO_AUTOCOMPLETAR() {
        return LABEL_FILTRO_AUTOCOMPLETAR;
    }

    /**
     *
     * @param LABEL_FILTRO_AUTOCOMPLETAR
     */
    public void setLABEL_FILTRO_AUTOCOMPLETAR(String LABEL_FILTRO_AUTOCOMPLETAR) {
        this.LABEL_FILTRO_AUTOCOMPLETAR = LABEL_FILTRO_AUTOCOMPLETAR;
    }

    /**
     *
     * @return
     */
    public String getLABEL_FILTRO_COMODIN() {
        return LABEL_FILTRO_COMODIN;
    }

    /**
     *
     * @param LABEL_FILTRO_COMODIN
     */
    public void setLABEL_FILTRO_COMODIN(String LABEL_FILTRO_COMODIN) {
        this.LABEL_FILTRO_COMODIN = LABEL_FILTRO_COMODIN;
    }

    /**
     *
     * @return
     */
    public String getLABEL_FILTRO_EMPIEZA_POR() {
        return LABEL_FILTRO_EMPIEZA_POR;
    }

    /**
     *
     * @param LABEL_FILTRO_EMPIEZA_POR
     */
    public void setLABEL_FILTRO_EMPIEZA_POR(String LABEL_FILTRO_EMPIEZA_POR) {
        this.LABEL_FILTRO_EMPIEZA_POR = LABEL_FILTRO_EMPIEZA_POR;
    }

    /**
     *
     * @return
     */
    public String getLABEL_FILTRO_ES_IGUAL_A() {
        return LABEL_FILTRO_ES_IGUAL_A;
    }

    /**
     *
     * @param LABEL_FILTRO_ES_IGUAL_A
     */
    public void setLABEL_FILTRO_ES_IGUAL_A(String LABEL_FILTRO_ES_IGUAL_A) {
        this.LABEL_FILTRO_ES_IGUAL_A = LABEL_FILTRO_ES_IGUAL_A;
    }

    /**
     *
     * @return
     */
    public String getLABEL_FILTRO_NO_CONTIENE() {
        return LABEL_FILTRO_NO_CONTIENE;
    }

    /**
     *
     * @param LABEL_FILTRO_NO_CONTIENE
     */
    public void setLABEL_FILTRO_NO_CONTIENE(String LABEL_FILTRO_NO_CONTIENE) {
        this.LABEL_FILTRO_NO_CONTIENE = LABEL_FILTRO_NO_CONTIENE;
    }

    /**
     *
     * @return
     */
    public String getLABEL_FILTRO_TERMINA_CON() {
        return LABEL_FILTRO_TERMINA_CON;
    }

    /**
     *
     * @param LABEL_FILTRO_TERMINA_CON
     */
    public void setLABEL_FILTRO_TERMINA_CON(String LABEL_FILTRO_TERMINA_CON) {
        this.LABEL_FILTRO_TERMINA_CON = LABEL_FILTRO_TERMINA_CON;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_AUTOCOMPLETAR() {
        return VALUE_FILTRO_AUTOCOMPLETAR;
    }

    /**
     *
     * @param VALUE_FILTRO_AUTOCOMPLETAR
     */
    public void setVALUE_FILTRO_AUTOCOMPLETAR(String VALUE_FILTRO_AUTOCOMPLETAR) {
        this.VALUE_FILTRO_AUTOCOMPLETAR = VALUE_FILTRO_AUTOCOMPLETAR;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_COMODIN() {
        return VALUE_FILTRO_COMODIN;
    }

    /**
     *
     * @param VALUE_FILTRO_COMODIN
     */
    public void setVALUE_FILTRO_COMODIN(String VALUE_FILTRO_COMODIN) {
        this.VALUE_FILTRO_COMODIN = VALUE_FILTRO_COMODIN;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_EMPIEZA_POR() {
        return VALUE_FILTRO_EMPIEZA_POR;
    }

    /**
     *
     * @param VALUE_FILTRO_EMPIEZA_POR
     */
    public void setVALUE_FILTRO_EMPIEZA_POR(String VALUE_FILTRO_EMPIEZA_POR) {
        this.VALUE_FILTRO_EMPIEZA_POR = VALUE_FILTRO_EMPIEZA_POR;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_ES_IGUAL_A() {
        return VALUE_FILTRO_ES_IGUAL_A;
    }

    /**
     *
     * @param VALUE_FILTRO_ES_IGUAL_A
     */
    public void setVALUE_FILTRO_ES_IGUAL_A(String VALUE_FILTRO_ES_IGUAL_A) {
        this.VALUE_FILTRO_ES_IGUAL_A = VALUE_FILTRO_ES_IGUAL_A;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_NO_CONTIENE() {
        return VALUE_FILTRO_NO_CONTIENE;
    }

    /**
     *
     * @param VALUE_FILTRO_NO_CONTIENE
     */
    public void setVALUE_FILTRO_NO_CONTIENE(String VALUE_FILTRO_NO_CONTIENE) {
        this.VALUE_FILTRO_NO_CONTIENE = VALUE_FILTRO_NO_CONTIENE;
    }

    /**
     *
     * @return
     */
    public String getVALUE_FILTRO_TERMINA_CON() {
        return VALUE_FILTRO_TERMINA_CON;
    }

    /**
     *
     * @param VALUE_FILTRO_TERMINA_CON
     */
    public void setVALUE_FILTRO_TERMINA_CON(String VALUE_FILTRO_TERMINA_CON) {
        this.VALUE_FILTRO_TERMINA_CON = VALUE_FILTRO_TERMINA_CON;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListFiltros() {
        return listFiltros;
    }

    /**
     *
     * @param listFiltros
     */
    public void setListFiltros(List<SelectItem> listFiltros) {
        this.listFiltros = listFiltros;
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
    public ConsultaMasivaTable getDirConsultada() {
        return dirConsultada;
    }

    /**
     *
     * @param dirConsultada
     */
    public void setDirConsultada(ConsultaMasivaTable dirConsultada) {
        this.dirConsultada = dirConsultada;
    }

    /**
     *
     * @return
     */
    public boolean isShowMaestro() {
        return showMaestro;
    }

    /**
     *
     * @param showMaestro
     */
    public void setShowMaestro(boolean showMaestro) {
        this.showMaestro = showMaestro;
    }

    /**
     *
     * @return
     */
    public boolean isShowDetalle() {
        return showDetalle;
    }

    /**
     *
     * @param showDetalle
     */
    public void setShowDetalle(boolean showDetalle) {
        this.showDetalle = showDetalle;
    }

    /**
     *
     * @return
     */
    public String getCm_confiabilidad() {
        return cm_confiabilidad;
    }

    /**
     *
     * @param cm_confiabilidad
     */
    public void setCm_confiabilidad(String cm_confiabilidad) {
        this.cm_confiabilidad = cm_confiabilidad;
    }

    /**
     *
     * @return
     */
    public String getCm_estrato() {
        return cm_estrato;
    }

    /**
     *
     * @param cm_estrato
     */
    public void setCm_estrato(String cm_estrato) {
        this.cm_estrato = cm_estrato;
    }

    /**
     *
     * @return
     */
    public String getCm_localidad() {
        return cm_localidad;
    }

    /**
     *
     * @param cm_localidad
     */
    public void setCm_localidad(String cm_localidad) {
        this.cm_localidad = cm_localidad;
    }

    /**
     *
     * @return
     */
    public String getCm_nivelSocio() {
        return cm_nivelSocio;
    }

    /**
     *
     * @param cm_nivelSocio
     */
    public void setCm_nivelSocio(String cm_nivelSocio) {
        this.cm_nivelSocio = cm_nivelSocio;
    }

    /**
     *
     * @return
     */
    public String getCm_revisar() {
        return cm_revisar;
    }

    /**
     *
     * @param cm_revisar
     */
    public void setCm_revisar(String cm_revisar) {
        this.cm_revisar = cm_revisar;
    }

    /**
     *
     * @return
     */
    public String getCm_tipoDireccion() {
        return cm_tipoDireccion;
    }

    /**
     *
     * @param cm_tipoDireccion
     */
    public void setCm_tipoDireccion(String cm_tipoDireccion) {
        this.cm_tipoDireccion = cm_tipoDireccion;
    }

    /**
     *
     * @return
     */
    public String getCm_barrio() {
        return cm_barrio;
    }

    /**
     *
     * @param cm_barrio
     */
    public void setCm_barrio(String cm_barrio) {
        this.cm_barrio = cm_barrio;
    }

    /**
     *
     * @return
     */
    public String getCm_direccion() {
        return cm_direccion;
    }

    /**
     *
     * @param cm_direccion
     */
    public void setCm_direccion(String cm_direccion) {
        this.cm_direccion = cm_direccion;
    }

    /**
     *
     * @return
     */
    public String getCm_idDireccion() {
        return cm_idDireccion;
    }

    /**
     *
     * @param cm_idDireccion
     */
    public void setCm_idDireccion(String cm_idDireccion) {
        this.cm_idDireccion = cm_idDireccion;
    }

    /**
     *
     * @return
     */
    public String getCm_barrioTemp() {
        return cm_barrioTemp;
    }

    /**
     *
     * @param cm_barrioTemp
     */
    public void setCm_barrioTemp(String cm_barrioTemp) {
        this.cm_barrioTemp = cm_barrioTemp;
    }

    /**
     *
     * @return
     */
    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    /**
     *
     * @param direcciones
     */
    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     *
     * @return
     */
    public Direccion getDirAutocompletar() {
        return dirAutocompletar;
    }

    /**
     *
     * @param dirAutocompletar
     */
    public void setDirAutocompletar(Direccion dirAutocompletar) {
        this.dirAutocompletar = dirAutocompletar;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDireccionesString() {
        return direccionesString;
    }

    /**
     *
     * @param direccionesString
     */
    public void setDireccionesString(ArrayList<String> direccionesString) {
        this.direccionesString = direccionesString;
    }

    /**
     *
     * @return
     */
    public boolean isShowAutocompletarInput() {
        return showAutocompletarInput;
    }

    /**
     *
     * @param showAutocompletarInput
     */
    public void setShowAutocompletarInput(boolean showAutocompletarInput) {
        this.showAutocompletarInput = showAutocompletarInput;
    }

    /**
     *
     * @return
     */
    public boolean isShowDirIngresadaTexto() {
        return showDirIngresadaTexto;
    }

    /**
     *
     * @param showDirIngresadaTexto
     */
    public void setShowDirIngresadaTexto(boolean showDirIngresadaTexto) {
        this.showDirIngresadaTexto = showDirIngresadaTexto;
    }

    /**
     *
     * @return
     */
    public String getDir_ingresadaAutocompletar() {
        return dir_ingresadaAutocompletar;
    }

    /**
     *
     * @param dir_ingresadaAutocompletar
     */
    public void setDir_ingresadaAutocompletar(String dir_ingresadaAutocompletar) {
        this.dir_ingresadaAutocompletar = dir_ingresadaAutocompletar;
    }

    /**
     *
     * @return
     */
    public String getCm_localidadTemp() {
        return cm_localidadTemp;
    }

    /**
     *
     * @param cm_localidadTemp
     */
    public void setCm_localidadTemp(String cm_localidadTemp) {
        this.cm_localidadTemp = cm_localidadTemp;
    }

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
}
