package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.mbeans.comun.ConstantSystem;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase ConsultaDireccionMasivaWebServiceMBean Extiende de BaseMBean Implementa
 * Serialización.
 *
 * @author Deiver Rovira
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version	1.2 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 */
@ViewScoped
@ManagedBean(name = "consultaDireccionMasivaWebServiceMBean")
public class ConsultaDireccionMasivaWebServiceMBean extends BaseMBean implements Serializable {

    /**
     *
     */
    protected static final String MSG_ERRROR_LECTURA_ARCHIVO = "ERROR ARCHIVO VACIO.";
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
    protected static final String MSG_ERRROR_ARCHIVO_REGISTROS_VACIO = "el archivo tiene inconsistencias de datos";
    /**
     *
     */
    public static final String NOMBRE_FUNCIONALIDAD = "CARGA CONSULTA VALIDACION MASIVA";
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
    /**
     *
     */
    public String son_barrio = "";
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
    private String separator;
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
    public boolean renderBarrio;
    /**
     *
     */
    public boolean tipoconsulta = true;
    /**
     *
     */
    public String array[] = new String[21];
    /**
     *
     */
    public boolean errors = false;
    /**
     *
     */
    public boolean upload = false;
    /**
     *
     */
    public boolean errorscol = false;
    /**
     *
     */
    public boolean cargar = true;
    /**
     *
     */
    public boolean procesar = true;
    /**
     *
     */
    public boolean uploadCarga = true;
    /**
     *
     */
    public boolean descargar = true;
    /**
     *
     */
    public boolean descarga = false;
    /**
     *
     */
    public boolean showBotonGuardar = false;
    /**
     *
     */
    public StringBuffer texto = null;
    /**
     *
     */
    public String[] lineas = null;
    /**
     *
     */
    public String dir_tipoConsulta;
    private ArrayList<CargaMarcas> results;
    /**
     *
     */
    public List<CargaMarcas> detalleresult;
    File fichero = null;
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
    /**
     *
     */
    public BigDecimal confiabilidad = null;
    private ArrayList<ArrayList<String>> registros;
    List<DetalleSolicitud> detalleS = null;
    private String nombreArchivo;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    /**
     *
     */
    public ArrayList<AddressRequest> addressRequest = new ArrayList<AddressRequest>();

    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ConsultaDireccionMasivaWebServiceMBean.class);

    /**
     *
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
    public ConsultaDireccionMasivaWebServiceMBean() {

        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se generea error en ConsultaDireccionMasivaWebServiceMBean class" + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Se generea error en ConsultaDireccionMasivaWebServiceMBean class" + ex.getMessage() , ex, LOGGER);
        }

        renderBarrio = false;
        maxFilesQuantity = true;
        size = 0;
        message = "";
        descarga = false;
        descargar = false;
        procesar = false;

        results = new ArrayList<CargaMarcas>();
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            if (attr.toUpperCase().equals("MyArchivo".toUpperCase())) {
                byte[] bytes = (byte[]) session.getAttribute(attr);
                session.removeAttribute(attr);
                try {
                    listener(bytes);
                    if (!errors) {
                        procesar = true;
                        upload = false;
                        tipoconsulta = false;
                    } else {
                        if (message.equals("")) {
                            message = MSG_ERRROR_ARCHIVO_REGISTROS_VACIO;
                        }
                        procesar = false;
                        upload = true;
                        tipoconsulta = false;
                    }

                } catch (Exception ex) {
                    message = MSG_ERROR_ESTRUCTURA;
                    FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
                    procesar = false;
                    upload = true;
                    tipoconsulta = false;
                }
            }
        }
    }

    /**
     *
     * @param messag
     * @return
     */
    private String validarCamposObligatorios(String messag) {
        message = messag;
        upload = false;
        cargar = true;
        procesar = false;
        return message;
    }

    /**
     *
     * @param ev
     */
    public void doValidar(ActionEvent ev) {
        session.setAttribute("consulta", dir_tipoConsulta);
        upload = true;
        tipoconsulta = false;
        procesar = false;
    }

    /**
     *
     * @param data
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void listener(byte[] data) throws ApplicationException {
        upload = true;
        StringBuffer texto = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            texto.append(c);
        }

        separator = System.getProperty("line.separator");
        int numeroRegistros = texto.toString().split(System.getProperty("line.separator")).length;

        if (data.length < 1) {
            this.message = MSG_ERRROR_LECTURA_ARCHIVO;
            validarCamposObligatorios(MSG_ERRROR_LECTURA_ARCHIVO);

            setMessage(this.message);
        } else if (numeroRegistros == 0) {
            this.message = MSG_ERRROR_LECTURA_ARCHIVO;
            validarCamposObligatorios(MSG_ERRROR_LECTURA_ARCHIVO);
            setMessage(this.message);
        } else {
            try {
                registros = new ArrayList<ArrayList<String>>();
                registros.addAll(loadtext(texto));
            } catch (IOException ex) {
                FacesUtil.mostrarMensajeError("Error al adicionar los registros" + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error al adicionar los registros"  + ex.getMessage(), ex);
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error al adicionar los registros" + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error al adicionar los registros"  + ex.getMessage(), ex);
            }
        }
    }

    /**
     *
     * @param texto
     * @return
     * @throws IOException
     */
    private ArrayList<ArrayList<String>> loadtext(StringBuffer texto) throws IOException {
        String dato = "";
        registros = new ArrayList<ArrayList<String>>();
        separator = System.getProperty("line.separator");
        String[] lineas = texto.toString().split(separator);
        ArrayList<String> listaDatos = new ArrayList<String>();
        setCantidad(lineas.length);
        for (int i = 0; i < lineas.length; i++) {
            String[] linea = lineas[i].split("\t" + ",");

            for (int j = 0; j < linea.length; j++) {
                dato = linea[j].toString();
                if (dato.isEmpty() || dato.equalsIgnoreCase("null")) {
                    dato = null;
                }

            }
            listaDatos.add(dato);
        }
        String splitLabels[] = listaDatos.get(0).split(",");
        int totalLabel = 4;
        if (splitLabels.length == totalLabel) {
            for (int l = 0; l < listaDatos.size(); l++) {
                String[] datac;
                String data = null;
                datac = new String[4];

                if (l == 0) {
                    for (int j = 0; j < totalLabel; j++) {
                        String dataLabels = splitLabels[j].trim();
                        switch (j) {
                            case 0:
                                if (!ConstantSystem.cargarPropiedad("DEPARTAMENTO").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA + "DEPARTAMENTO";
                                    procesar = false;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;

                            case 1:
                                if (!ConstantSystem.cargarPropiedad("CIUDAD").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA + "CIUDAD";
                                    procesar = false;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;

                            case 2:
                                if (!ConstantSystem.cargarPropiedad("BARRIO").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA + "BARRIO";
                                    procesar = false;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;

                            case 3:
                                if (!ConstantSystem.cargarPropiedad("DIRECCION").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA + "DIRECCION" + ">--<" + dataLabels + ">";
                                    procesar = false;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;
                        }
                    }
                } else {
                    String splitarrayqq[] = listaDatos.get(l).split(",");

                    if (splitarrayqq.length == 4) {
                        for (int n = 0; n < 4; n++) {
                            data = (splitarrayqq[n].toString());
                            datac[n] = data;
                        }
                    } else {
                        message = MSG_ERROR_ESTRUCTURA;
                        procesar = false;
                        validarCamposObligatorios(message);
                        setErrors(true);
                    }
                }
                if (l != 0) {
                    validateColumn(datac);
                }
            }
        } else {
            message = MSG_ERROR_ESTRUCTURA;
            procesar = false;
            validarCamposObligatorios(message);
            setErrors(true);
        }
        setDetalleresult(results);
        return registros;
    }

    /**
     *
     * @param datav
     */
    private void validateColumn(String[] datav) {
        message = "";
        CargaMarcas oespr = null;
        oespr = new CargaMarcas();
        int j = 0;

        for (int column = 0; column <= datav.length - 1; column++) {
            switch (column) {
                case 0://DEPARTAMENTO
                    oespr.setDepartamento(datav[column].toString());
                    if ("".equals(oespr.getDepartamento().trim())) {
                    }
                    break;

                case 1://CIUDAD
                    oespr.setCiudad(datav[column].toString());
                    if ("".equals(oespr.getCiudad().trim())) {
                    }
                    break;

                case 2://BARRIO
                    oespr.setBarrio(datav[column].toString());
                    break;

                case 3://DIRECCION
                    oespr.setDireccion(datav[column].toString());
                    if ("".equals(oespr.getDireccion().trim())) {
                    }
                    break;
            }
        }
        results.add(oespr);
        setDetalleresult(results);
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
            FacesUtil.mostrarMensajeError("Error en parseInt" + ex.getMessage() , ex, LOGGER);
            return valueIfInvalid;
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en parseInt" + ex.getMessage() , ex, LOGGER);
            return valueIfInvalid;
        }
    }

    /**
     *
     * @param ev
     * @return
     */
    public String create(ActionEvent ev) {
        if (true) {
            final StringBuffer sb = new StringBuffer();

            if (getErrors() != true) {
                for (int j = 0; j < getDetalleresult().size(); j++) {

                    if (getDetalleresult().get(j).getErrores_presentados().isEmpty()) {
                        request = new AddressRequest();
                        request.setId(j + "");
                        request.setAddress(getDetalleresult().get(j).getDireccion());
                        request.setCity(getDetalleresult().get(j).getCiudad());
                        request.setLevel(session.getAttribute("consulta").toString());
                        request.setNeighborhood(getDetalleresult().get(j).getBarrio());
                        request.setState(getDetalleresult().get(j).getDepartamento());
                        addressRequest.add(request);
                    }
                }

                if (addressRequest.size() > 0) {
                    try {
                        listae = GeoreferenciaDelegate.queryAddressBatch(addressRequest);
                    } catch (ApplicationException ex) {
                         FacesUtil.mostrarMensajeError("Error en create" + ex.getMessage() , ex, LOGGER);
                    }catch (Exception ex) {
                         FacesUtil.mostrarMensajeError("Error en create" + ex.getMessage() , ex, LOGGER);
                    }
                    try {
                        if (listae != null) {
                            if (session.getAttribute("consulta").toString().equals("S")) {

                                sb.append("DIRECCION ESTANDAR");
                                sb.append(',');
                                sb.append("DIRECCION ALTERNA");
                                sb.append(',');
                                sb.append("ACTIVIDAD ECONOMICA");
                                sb.append(',');
                                sb.append("CODIGO DE MANZANA");
                                sb.append(',');
                                sb.append("BARRIO");
                                sb.append(',');//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                sb.append("NIVEL ECONOMICO");//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                sb.append(',');
                                sb.append("ESTRATO");
                                sb.append(',');
                                sb.append("EXISTENCIA");
                                sb.append(',');
                                sb.append("OBSERVACIONES");
                                sb.append("\n");
                                for (AddressService obj : listae) {
                                    sb.append(obj.getAddress());
                                    sb.append(',');
                                    sb.append(obj.getAlternateaddress());
                                    sb.append(',');
                                    sb.append(obj.getActivityeconomic());
                                    sb.append(',');
                                    sb.append(obj.getAppletstandar());
                                    sb.append(',');
                                    sb.append(obj.getNeighborhood());
                                    sb.append(',');
                                    sb.append(obj.getLeveleconomic());
                                    sb.append(',');//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                    sb.append(obj.getEstratoDef());//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                    sb.append(',');
                                    sb.append(obj.getExist());
                                    sb.append(',');
                                    sb.append(obj.getRecommendations());
                                    sb.append("\n");
                                }

                            } else if (session.getAttribute("consulta").toString().equals("C")) {
                                sb.append("DIRECCION ESTANDAR");
                                sb.append(',');
                                sb.append("DIRECCION ALTERNA");
                                sb.append(',');
                                sb.append("DIRECCION SUGERIDA");
                                sb.append(',');
                                sb.append("BARRIO");
                                sb.append(',');
                                sb.append("CALIFICADOR");
                                sb.append(',');//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                sb.append("NIVEL ECONOMICO");//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                sb.append(',');
                                sb.append("ESTRATO");
                                sb.append(',');
                                sb.append("EXISTENCIA");
                                sb.append(',');
                                sb.append("ACTIVIDAD ECONOMICA");
                                sb.append(',');
                                sb.append("NIVEL DE CALIDAD");
                                sb.append(',');
                                sb.append("CODIGO DE MANZANA");
                                sb.append(',');
                                //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1                                            
                                sb.append("NODO BI");
                                sb.append(',');
                                sb.append("NODO UNI UNO");
                                sb.append(',');
                                sb.append("NODO UNI DOS");
                                sb.append(',');
                                //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1            
                                sb.append("OBSERVACIONES");
                                sb.append("\n");
                                for (AddressService obj : listae) {
                                    sb.append(obj.getAddress());
                                    sb.append(',');
                                    sb.append(obj.getAlternateaddress());
                                    sb.append(',');
                                    sb.append(obj.getAddressSuggested());
                                    sb.append(',');
                                    sb.append(obj.getNeighborhood());
                                    sb.append(',');
                                    sb.append(obj.getQualifiers());
                                    sb.append(',');
                                    sb.append(obj.getLeveleconomic());
                                    sb.append(',');//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                    sb.append(obj.getEstratoDef());//Modificado por: Direcciones face I Carlos Vilamil 2013-05-11 V 1.2
                                    sb.append(',');
                                    sb.append(obj.getExist());
                                    sb.append(',');
                                    sb.append(obj.getActivityeconomic());
                                    sb.append(',');
                                    sb.append(obj.getLevellive());
                                    sb.append(',');
                                    sb.append(obj.getAppletstandar());
                                    sb.append(',');
                                    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1            
                                    sb.append(obj.getNodoUno());
                                    sb.append(',');
                                    sb.append(obj.getNodoDos());
                                    sb.append(',');
                                    sb.append(obj.getNodoTres());
                                    sb.append(',');
                                    //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1            
                                    sb.append(obj.getRecommendations());
                                    sb.append("\n");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        FacesUtil.mostrarMensajeError("Error en session" + ex.getMessage() , ex, LOGGER);
                    }
                }
                sb.append("\n");
            }
            message = "Archivo Procesado";
            validarCamposObligatorios(message);
            byte[] csvData = null;

            try {
                csvData = sb.toString().getBytes("utf-8");
            } catch (final UnsupportedEncodingException ex) {
                FacesUtil.mostrarMensajeError("Error en csvData" + ex.getMessage() , ex, LOGGER);
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error en csvData" + ex.getMessage() , ex, LOGGER);
            }

            final FacesContext context = FacesContext.getCurrentInstance();
            final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            procesar = false;
            Date hoy = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            String fileName = "Consulta Direccion Masiva" + sdf.format(hoy) + "Procesado";
            response.setHeader("Content-disposition", "attached; filename=\"" + fileName + ".csv\"");
            response.setContentType("application/force.download");

            try {
                response.getOutputStream().write(csvData);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                context.responseComplete();
                //Telling the framework that the response has been completed. 
                FacesContext.getCurrentInstance().responseComplete();
            } catch (final IOException e) {
                FacesUtil.mostrarMensajeError("Error en response" + e.getMessage() , e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en response" + e.getMessage() , e, LOGGER);
            }

            uploadCarga = true;
        }
        return message;
    }

    /**
     *
     */
    public void descargarCvs() {
        final StringBuffer sb = new StringBuffer();
        sb.append("DEPARTAMENTO");
        sb.append(',');
        sb.append("CIUDAD");
        sb.append(',');
        sb.append("BARRIO");
        sb.append(',');
        sb.append("DIRECCION");
        sb.append(',');
        sb.append("ERRORES");
        sb.append("\n");
        for (int k = 0; k < getDetalleresult().size(); k++) {
            sb.append(getDetalleresult().get(k).getDepartamento());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getCiudad());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getBarrio());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getDireccion());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getErrores_presentados());
            sb.append("\n");
        }
        byte[] csvData = null;
        // in case you need some specific charset : 
        // here is an exemple with some standard utf-8 

        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException e1) {
            FacesUtil.mostrarMensajeError("Error en csvData" + e1.getMessage() , e1, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en csvData" + ex.getMessage() , ex, LOGGER);
        }

        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        getNombreArchivo();
        String fileName = "Consulta Direccion Masiva Errores" + sdf.format(hoy);
        response.setHeader("Content-disposition", "attached; filename=\"" + fileName + ".csv\"");
        response.setContentType("application/force.download");

        try {
            response.getOutputStream().write(csvData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            //Telling the framework that the response has been completed. 
            FacesContext.getCurrentInstance().responseComplete();
        } catch (final IOException e) {
            FacesUtil.mostrarMensajeError("Error en response" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en response" + e.getMessage() , e, LOGGER);
        }

        setErrors(false);
        setMessage("");

        setErrorscol(false);

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
    public boolean isCargar() {
        return cargar;
    }

    /**
     *
     * @param cargar
     */
    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    /**
     *
     * @return
     */
    public boolean getErrorscol() {
        return errorscol;
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
    public boolean getUpload() {
        return upload;
    }

    /**
     *
     * @param upload
     */
    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    /**
     *
     * @return
     */
    public boolean isDescargar() {
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
    public boolean getUploadCarga() {
        return uploadCarga;
    }

    /**
     *
     * @param uploadCarga
     */
    public void setUploadCarga(boolean uploadCarga) {
        this.uploadCarga = uploadCarga;
    }

    /**
     *
     * @return
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     *
     * @param nombreArchivo
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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
     * @return
     */
    public List<CargaMarcas> getDetalleresult() {
        return detalleresult;
    }

    /**
     *
     * @param detalleresult
     */
    public void setDetalleresult(List<CargaMarcas> detalleresult) {
        this.detalleresult = detalleresult;
    }

    /**
     *
     * @return
     */
    public boolean getProcesar() {
        return procesar;
    }

    /**
     *
     * @param procesar
     */
    public void setProcesar(boolean procesar) {
        this.procesar = procesar;
    }

    /**
     *
     * @return
     */
    public boolean getTipoconsulta() {
        return tipoconsulta;
    }

    /**
     *
     * @param tipoconsulta
     */
    public void setTipoconsulta(boolean tipoconsulta) {
        this.tipoconsulta = tipoconsulta;
    }
}
