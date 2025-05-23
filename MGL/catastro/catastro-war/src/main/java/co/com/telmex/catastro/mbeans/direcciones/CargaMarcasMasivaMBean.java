package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.MarcasDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.mbeans.comun.ConstantSystem;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
 * Clase CargaMarcasMasivaMBean Extiende de BaseMBean
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "cargaMarcasMasivaMBean")
public class CargaMarcasMasivaMBean extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(CargaMarcasMasivaMBean.class);

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
    public static final String NOMBRE_FUNCIONALIDAD = "CARGA MARCAS MASIVA";
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
    private ArrayList<String[]> contenido;
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
    public String array[] = new String[21];
    /**
     *
     */
    public boolean errors = false;
    /**
     *
     */
    public boolean upload = true;
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
    private static List<CargaMarcas> result;
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
    private Geografico geografico = null;
    private ArrayList<ArrayList<String>> registros;
    private AddressGeodata addressGeodata = null;
    List<DetalleSolicitud> detalleS = null;
    private AddressRequest address = null;
    private GeograficoPolitico city = null;
    private String nombreArchivo;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

    /**
     *
     */
    public CargaMarcasMasivaMBean() {

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
                    procesar = true;
                } catch (Exception ex) {
                    FacesUtil.mostrarMensajeError("Error en listener" + ex.getMessage() , ex, LOGGER);
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
     * Carga el archivo que se adjunta como soporte a la información
     * suministrada
     */
    public void init() {
        dir_barrio = "";
        dir_ciudad = "";
        dir_pais = "";
        dir_regional = "";

    }

    private String validarCamposObligatorios(String messag) {
        message = messag;
        upload = false;
        cargar = true;
        procesar = false;
        return message;
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
                FacesUtil.mostrarMensajeError("Error en adcionar registros " + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error en adcionar registros " + ex.getMessage(), ex);
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error en adcionar registros " + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error en adcionar registros " + ex.getMessage(), ex);
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
                dato = linea[j].trim();
                if (dato.isEmpty() || dato.equalsIgnoreCase("null")) {
                    dato = null;
                }
            }
            listaDatos.add(dato);

        }

        String splitLabels[] = listaDatos.get(0).split("\t");
        int totalLabel = 6;
        if (splitLabels.length == totalLabel) {
            for (int l = 0; l < listaDatos.size(); l++) {
                ArrayList datac = null;
                String data = null;
                datac = new ArrayList();

                if (l == 0) {
                    for (int j = 0; j < totalLabel; j++) {
                        String dataLabels = splitLabels[j];
                        switch (j) {
                            case 0:
                                if (!ConstantSystem.cargarPropiedad("DEPARTAMENTO").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);

                                }
                                break;

                            case 1:
                                if (!ConstantSystem.cargarPropiedad("CIUDAD").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);

                                }
                                break;

                            case 2:
                                if (!ConstantSystem.cargarPropiedad("BARRIO").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);

                                }
                                break;

                            case 3:
                                if (!ConstantSystem.cargarPropiedad("DIRECCION").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;

                            case 4:
                                if (!ConstantSystem.cargarPropiedad("MARCA").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;

                            case 5:
                                if (!ConstantSystem.cargarPropiedad("ACCION").equals(dataLabels)) {
                                    message = MSG_ERROR_ESTRUCTURA;
                                    validarCamposObligatorios(message);
                                    setErrors(true);
                                }
                                break;
                        }
                    }
                } else {
                    String splitarrayqq[] = listaDatos.get(l).split("\t");

                    for (int n = 0; n < 6; n++) {
                        data = (splitarrayqq[n].toString());
                        datac.add(data);
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
    private void validateColumn(ArrayList datav) {
        message = "";
        CargaMarcas oespr = null;
        oespr = new CargaMarcas();

        for (int column = 0; column <= datav.size() - 1; column++) {
            switch (column) {
                case 0:
                    //DEPARTAMENTO
                    oespr.setDepartamento(datav.get(column).toString());
                    if ("".equals(datav.get(column))) {
                        String errorc1 = "DEPARTAMENTO VACIO";
                        setErrorscol(true);

                        oespr.setErrores_presentados(errorc1);
                    }
                    break;

                case 1:
                    //CIUDAD
                    oespr.setCiudad(datav.get(column).toString());
                    if ("".equals(datav.get(column))) {
                        String errorc1 = "Ciudad vacia";
                        oespr.setErrores_presentados(errorc1);
                        setErrorscol(true);

                    }
                    break;

                case 2:
                    //BARRIO
                    oespr.setBarrio(datav.get(column).toString());
                    if ("".equals(datav.get(column).toString())) {
                        String errorc5 = "Barrio vacio";
                        oespr.setErrores_presentados(errorc5);
                        setErrorscol(true);
                    }

                    break;

                case 3:
                    //DIRECCION
                    oespr.setDireccion(datav.get(column).toString());
                    if ("".equals(datav.get(column).toString())) {
                        String errorc6 = "Direccion vacio";
                        oespr.setErrores_presentados(errorc6);
                        setErrorscol(true);
                    }
                    break;

                case 4:
                    //MARCA

                    oespr.setMarcas(datav.get(column).toString());
                    if ("".equals(datav.get(column))) {
                        String errorc7 = "Marcas vacio";
                        oespr.setErrores_presentados(errorc7);
                        setErrorscol(true);

                    }
                    break;

                case 5:
                    //ACCION
                    oespr.setAccion(datav.get(column).toString());
                    if ("".equals(datav.get(column))) {
                        String errorc8 = "Accion vacia";
                        oespr.setErrores_presentados(errorc8);
                        setErrorscol(true);

                    }
                    break;
            }

        }
        results.add(oespr);
        setDetalleresult(detalleresult);
    }

    /**
     *
     */
    public void descargarCvs() {
        final StringBuffer sb = new StringBuffer();

        sb.append("DFPARTAMENTO");
        sb.append(',');
        sb.append("CIUDAD");
        sb.append(',');
        sb.append("BARRIO");
        sb.append(',');
        sb.append("DIRECCION");
        sb.append(',');
        sb.append("MARCA");
        sb.append(',');
        sb.append("ACCION");
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
            sb.append(getDetalleresult().get(k).getMarcas());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getAccion());
            sb.append(',');
            sb.append(getDetalleresult().get(k).getErrores_presentados());
            sb.append("\n");
        }
        byte[] csvData = null;
        // in case you need some specific charset : 
        // here is an exemple with some standard utf-8 

        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException el) {
            FacesUtil.mostrarMensajeError("Error en csvData" + el.getMessage() , el, LOGGER);
        } catch (Exception el) {
            FacesUtil.mostrarMensajeError("Error en csvData" + el.getMessage() , el, LOGGER);
        }

        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        getNombreArchivo();
        String fileName = getNombreArchivo() + "Errores" + sdf.format(hoy);
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

        if (getErrorscol() == false) {

            final StringBuffer sb = new StringBuffer();
            sb.append("DFPARTAMENTO");
            sb.append(',');
            sb.append("CIUDAD");
            sb.append(',');
            sb.append("BARRIO");
            sb.append(',');
            sb.append("DIRECCION");
            sb.append(',');
            sb.append("MARCA");
            sb.append(',');
            sb.append("ACCION");
            sb.append(',');
            sb.append("ESTADO");
            sb.append("\n");

            CargaMarcas cargasMarcas = null;
            cargasMarcas = new CargaMarcas();
            if (getErrors() != true) {
                getDetalleresult();

                try {

                    for (int j = 0; j < getDetalleresult().size(); j++) {
                        if (getDetalleresult().get(j).getErrores_presentados().isEmpty()) {
                            address = new AddressRequest();
                            address.setAddress(getDetalleresult().get(j).getDireccion());
                            address.setLevel(Constant.TIPO_CONSULTA_SENCILLA);
                            address.setNeighborhood(getDetalleresult().get(j).getBarrio());
                            address.setCity(getDetalleresult().get(j).getCiudad());
                            address.setState(getDetalleresult().get(j).getDepartamento());
                            BigDecimal idHhpp = null;
                            try {
                                addressGeodata = GeoreferenciaDelegate.queryAddressGeodata(address);

                                if (addressGeodata != null) {
                                    cargasMarcas.setDireccion(addressGeodata.getCoddir());
                                    sb.append(getDetalleresult().get(j).getDepartamento());
                                    sb.append(',');
                                    sb.append(getDetalleresult().get(j).getCiudad());
                                    sb.append(',');
                                    sb.append(getDetalleresult().get(j).getBarrio());
                                    sb.append(',');
                                    sb.append(getDetalleresult().get(j).getDireccion());
                                    sb.append(',');
                                    sb.append(getDetalleresult().get(j).getMarcas());
                                    sb.append(',');
                                    sb.append(getDetalleresult().get(j).getAccion());
                                    sb.append(',');

                                    if (!addressGeodata.getCodencont().isEmpty()) {
                                        idHhpp = MarcasDelegate.queryHhppByIdDir(addressGeodata.getCodencont().toString());
                                        if (idHhpp != null) {
                                            BigDecimal marca = MarcasDelegate.searchMarca(getDetalleresult().get(j).getMarcas());
                                            if (getDetalleresult().get(j).getAccion().equals("I")) {
                                                if (marca != null) {
                                                    boolean marcahhp = MarcasDelegate.queryCreateMarcaHhpp(getDetalleresult().get(j).getMarcas(), idHhpp, marca, this.user.getUsuNombre().toString());
                                                    if (marcahhp == true) {
                                                        sb.append("OK");
                                                    } else {
                                                        sb.append("ERROR EN LA OPERACION INSERTAR MARCA");
                                                    }
                                                } else {
                                                    sb.append("ERROR NO EXISTE MARCA");
                                                }
                                            } else if (getDetalleresult().get(j).getAccion().toUpperCase().equals("D")) {
                                                if (marca != null) {
                                                    BigDecimal idmh = MarcasDelegate.searchMarcaHhpp(idHhpp, marca);
                                                    if (idmh != null) {
                                                        boolean marcadelethhp = MarcasDelegate.queryDeleteMarcaHhpp(getDetalleresult().get(j).getMarcas(), idHhpp, marca);
                                                        if (marcadelethhp == false) {
                                                            sb.append("ERROR EN LA OPERACION DELETE");
                                                        } else {
                                                            sb.append("OPERACION DELETE OK");
                                                        }
                                                    } else {
                                                        sb.append("ERROR NO EXISTE MARCA ASOCIADA AL HHPP");
                                                    }
                                                } else {
                                                    sb.append("ERROR NO EXISTE MARCA");
                                                }
                                            } else {
                                                sb.append("ACCION NO VALIDA,SOLO ACEPTA D,I");
                                            }

                                        } else {
                                            sb.append("ERROR NO EXISTE HHPP ASOCIADO A ESTA DIRECCION");
                                        }

                                    } else {
                                        sb.append("CODIGO DE DIRECCION VACIO");
                                    }

                                }

                            } catch (ApplicationException ex) {
                                FacesUtil.mostrarMensajeError("Error: al invocar al servicio.queryAddress() "+ ex.getMessage() , ex, LOGGER);
                            } catch (Exception ex) {
                                FacesUtil.mostrarMensajeError("Error: al invocar al servicio.queryAddress() "+ ex.getMessage() , ex, LOGGER);
                            }
                        }

                        sb.append("\n");
                    }

                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en getDetalleresult" + e.getMessage() , e, LOGGER);
                }

                message = "Archivo Procesado";
                validarCamposObligatorios(message);
                byte[] csvData = null;

                try {
                    csvData = sb.toString().getBytes("utf-8");
                } catch (final UnsupportedEncodingException el) {
                    FacesUtil.mostrarMensajeError("Error en csvData" + el.getMessage() , el, LOGGER);
                } catch (Exception ex) {
                    FacesUtil.mostrarMensajeError("Error en csvData" + ex.getMessage() , ex, LOGGER);
                }

                final FacesContext context = FacesContext.getCurrentInstance();
                final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                procesar = false;
                Date hoy = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
                String fileName = "Marcas" + sdf.format(hoy) + "Procesado";
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

        } else {
            errors = true;
            descargar = true;

            procesar = false;
            upload = false;
            cargar = true;
            message = "El archivo tiene inconsistencias de datos";
            validarCamposObligatorios(message);
            return message;
        }

        return message;
    }
}
