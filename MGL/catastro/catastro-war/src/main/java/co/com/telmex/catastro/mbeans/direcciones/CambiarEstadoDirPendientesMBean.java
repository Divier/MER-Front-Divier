package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.delegate.CambiarEstadoDirPendientesDelegate;
import co.com.telmex.catastro.delegate.ConsultaEspecificaDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import static co.com.telmex.catastro.mbeans.direcciones.CargaMarcasMasivaMBean.MSG_ERRROR_LECTURA_ARCHIVO;
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
import java.util.Enumeration;
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
import javax.servlet.http.HttpSession;


/**
 * Clase CambiarEstadoDirPendientesMBean Extiende de BaseMBean
 *
 * @author Deiver Rovira
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "cambiarEstadoDirPendientesMBean")
public class CambiarEstadoDirPendientesMBean extends BaseMBean {

    /**
     *
     */
    public static final String NOMBRE_FUNCIONALIDAD = "CAMBIAR ESTADO DE DIRECCIONES PENDIENTES DE VALIDACION";
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
    public String vacio;
    /**
     *
     */
    public String seleccionar;
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
    //Variables para consulta de direcciones
    private GeograficoPolitico city = null;
    private GeograficoPolitico dpto = null;
    //Scroll de la tabla de resultados
    private int scrollerPage = 1;
    //Lista de la tabla de resultados
    /**
     *
     */
    public List<ConsultaMasivaTable> direcciones = new ArrayList<ConsultaMasivaTable>();
    //Objeto que representa la direccion  consultada
    ConsultaMasivaTable dirConsultada = new ConsultaMasivaTable();
    //Variables para renderizar paneles
    private boolean showBotonConsultar;
    private boolean showPanelPrincipal = true;
    private boolean showTablaResultado;
    private boolean showMaestro = true;
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
    private ConsultaMasivaTable consultaMasiva;
    private static final String ACRONIMO_MAX_CANTIDAD_REGISTROS = "MAX_CANT_REGISTROS";
    private static String MAX_CANTIDAD_REGISTROS = Constant.CANT_MAXIMA_REGISTROS_DEFAULT_VAUE;
    //Variables de secion
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(CambiarEstadoDirPendientesMBean.class);
    private boolean showBotonExportar;
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

    /**
     *
     * @throws IOException
     */
    public CambiarEstadoDirPendientesMBean() throws IOException {
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
            //adiccion para cargue masivo de direcciones Ivan Turriago
            Enumeration e = session.getAttributeNames();
            while (e.hasMoreElements()) {
                String attr = (String) e.nextElement();
                if (attr.toUpperCase().equals("MyArchivo".toUpperCase())) {
                    byte[] bytes = (byte[]) session.getAttribute(attr);
                    session.removeAttribute(attr);
                    try {
                        listener(bytes);
                    } catch (Exception ex) {
                        message = "Ha ocurrido un error cargando el archivo csv";
                        FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
                    }
                }
            }
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se genera error en CambiarEstadoDirPendientesMBean " + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Se genera error en CambiarEstadoDirPendientesMBean " + ex.getMessage() , ex, LOGGER);
        }
        initLists();
    }

    /**
     *
     */
    private void initLists() {
        try {
            obtenerParametros();
            cargarPaises();
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en initLists" + ex.getMessage() , ex, LOGGER);
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
        message = validarCamposObligatorios();
        UIComponent cp = ev.getComponent();
        if (message.isEmpty()) {
            showTablaResultado = true;
            //Se consulta por las propiedades de la direccion
            try {
                //Se buca la direccion en el repositorio
                consultaFiltroEmpiezaPor();
            } catch (Exception e) {
                message = "Error al consultar por las propiedades de la dirección";
                FacesUtil.mostrarMensajeError(message + e.getMessage() , e, LOGGER);
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
        boolean hayError = false;
        this.cm_idDireccion = ((BigDecimal) (((UIParameter) ev.getComponent().findComponent("idDir")).getValue())).toString();

        if (cm_idDireccion != null) {
            Direccion direccion = null;
            try {
                direccion = ConsultaEspecificaDelegate.queryAddressOnRepoById(cm_idDireccion);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error: Consultando la direccion " + e.getMessage() , e, LOGGER);
                hayError = true;
            }

            if (direccion == null) {
                message = "Error al consultar la direccion con id: " + cm_idDireccion;
                return;
            }

            try {
                //Se actualiza el estado a false
                direccion.setDirRevisar("0");
                //Se actualiza el estado a validado=false
                hayError = CambiarEstadoDirPendientesDelegate.updateDirRevisarByIdDir(direccion, this.user.getUsuNombre(), NOMBRE_FUNCIONALIDAD);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error: Actualizando la direccion " + e.getMessage() , e, LOGGER);
                hayError = true;
            }
        }
        if (hayError) {
            message = "Error en la operación.";
        } else {
            message = "Operación exitosa.";
            showTablaResultado = false;
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public String onIrAccion() throws ApplicationException {
        return "";
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
        direcciones = new ArrayList<ConsultaMasivaTable>();
        try {
            direcciones = CambiarEstadoDirPendientesDelegate.queryConsultaEspecificaFiltroEmpiezaPor(dir_ingresadaTexto, dir_ciudad, MAX_CANTIDAD_REGISTROS);
            if (direcciones != null) {
                showBotonExportar = true;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error: Consulta Especifica-consultaFiltroEmpiezaPor, Excetion:Dir ingresada:{0}Cod ciudad:{1}" + e.getMessage() , e, LOGGER);
        }

        if (direcciones == null || direcciones.isEmpty()) {
            message = "No se encontraron datos para los parámetros ingresados, por favor inténtelo nuevamente.";
            showTablaResultado = false;
            return;
        } else {
            showTablaResultado = true;
        }
    }

    /**
     * Valida los campos obligatorios del formulario
     *
     * @return
     */
    private String validarCamposObligatorios() {
        message = "";

        if ("0".equals(dir_pais) || dir_pais == null) {
            message = Constant.OBLIGATORIO_PAIS;
        } else if ("0".equals(dir_regional) || dir_regional == null) {
            message = Constant.OBLIGATORIO_DEPARTAMENTO;
        } else if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = Constant.OBLIGATORIO_CIUDAD;
        } else if (dir_ingresadaTexto.isEmpty()) {
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
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en update Regionales" + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en update Regionales" + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Actualiza la lista de ciudades de acuerdo a la region seleccionada
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
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en update Ciudades" + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en update Ciudades" + ex.getMessage() , ex, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en cargar Paises" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargar Paises" + e.getMessage() , e, LOGGER);
        }
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
            MAX_CANTIDAD_REGISTROS = Constant.CANT_MAXIMA_REGISTROS_DEFAULT_VAUE;
            FacesUtil.mostrarMensajeError("Error al cargar la cantidad maxima de registros " + e.getMessage() , e, LOGGER);
        }
    }

    /**
     *
     * Este metodo contiene la logica del cargue masivo de direcciones para
     * modificar el campo dir_validar
     *
     * @param data
     * @throws co.com.claro.mgl.error.ApplicationException
     * @autor Ivan Turriago
     */
    public void listener(byte[] data) throws ApplicationException {

        StringBuffer texto = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            texto.append(c);
        }

        String separator = System.getProperty("line.separator");
        int numeroRegistros = texto.toString().split(separator).length;

        if (data.length < 1) {
            message = MSG_ERRROR_LECTURA_ARCHIVO;
        } else if (numeroRegistros == 0) {
            message = MSG_ERRROR_LECTURA_ARCHIVO;
        } else {
            try {
                List<String> registros = loadtext(texto);
                List<String> registrosInvalidos = CambiarEstadoDirPendientesDelegate.queryVerificarExistenciaPorLista(registros);
                StringBuffer mensajeError = new StringBuffer("El proceso de carga de datos ha finalizado exitosamente\n");

                if (registrosInvalidos != null && registrosInvalidos.size() > 0) {
                    mensajeError = new StringBuffer("\nOcurrio un error con los siguientes registros:\n");
                    for (Iterator<String> it = registrosInvalidos.iterator(); it.hasNext();) {
                        mensajeError.append(it.next());
                        mensajeError.append("\n");
                    }
                }
                message = mensajeError.toString();
            } catch (IOException ex) {
                LOGGER.error("Error en Cambiar Estado Dir Pendientes Delegate " + ex.getMessage(), ex);
                throw new ApplicationException("Error en Cambiar Estado Dir Pendientes Delegate " + ex.getMessage(), ex);
            } catch (Exception ex) {
                LOGGER.error("Error en Cambiar Estado Dir Pendientes Delegate " + ex.getMessage(), ex);
                throw new ApplicationException("Error en Cambiar Estado Dir Pendientes Delegate " + ex.getMessage(), ex);
            }
        }

    }

    /**
     * Este metodo contiene la logica de la lectura del archivo csv que contiene
     * la informacion de id's a modificar
     *
     * @param texto
     * @return
     * @throws IOException
     */
    private List<String> loadtext(StringBuffer texto) throws IOException {

        String dato = "";
        List<String> registros = new ArrayList<String>();
        String separator = System.getProperty("line.separator");
        String[] lineas = texto.toString().split(separator);
        for (int i = 0; i < lineas.length; i++) {
            String[] linea = lineas[i].split("\t" + ",");

            for (int j = 0; j < linea.length; j++) {
                dato = linea[j].trim();
                if (dato.isEmpty() || dato.equalsIgnoreCase("null")) {
                    dato = null;
                }
            }
            registros.add(dato);
        }
        return registros;
    }

    /**
     * Metodo que exporta un dataTable a CSV
     */
    public final void doExportSelectedDataToCSV() {
        Utilidades.doExportSelectedDataToCSV(direcciones, true);
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
    public boolean isShowTablaResultado() {
        return showTablaResultado;
    }

    /**
     *
     * @return
     */
    public List<ConsultaMasivaTable> getDirecciones() {
        return direcciones;
    }

    /**
     *
     * @param direcciones
     */
    public void setDirecciones(List<ConsultaMasivaTable> direcciones) {
        this.direcciones = direcciones;
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
}
