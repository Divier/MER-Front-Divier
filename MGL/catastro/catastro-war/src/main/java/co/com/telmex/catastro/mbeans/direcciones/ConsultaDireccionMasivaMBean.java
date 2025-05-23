package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.mbeans.comun.ConstantSystem;
import co.com.telmex.catastro.mbeans.solicitudes.OutputElementSolPlaneacionRed;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 * Clase ConsultaDireccionMasivaMBean Extiende de BaseMBean
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "consultaDireccionMasivaMBean")
public class ConsultaDireccionMasivaMBean extends BaseMBean {

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
    public boolean maxFilesQuantity;
    /**
     *
     */
    public int size;
    /**
     *
     */
    public String dir_tipoConsulta;
    /**
     *
     */
    public String vacio = "";
    /**
     *
     */
    public String tipo;
    /**
     *
     */
    private String message = "";
    private static List<OutputElementSolPlaneacionRed> result;
    private static int totalError;
    /**
     * Lista que contiene todos los registros del archivo que se va a cargar
     */
    private ArrayList<ArrayList<String>> registros;
    /**
     * Labels para identificar columnas
     */
    private String[] labels;
    private String[] datav;
    /* Labels Fijos*/
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
    public List<SelectItem> ciudades;
    /**
     *
     */
    public List<SelectItem> barrios;
    /**
     *
     */
    public ArrayList label;
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
    private ArrayList<ArrayList<String>> listaData;
    /**
     *
     */
    public int numRegistros;
    private static final Logger LOGGER = LogManager.getLogger(ConsultaDireccionMasivaMBean.class);

    /**
     *
     */
    public ConsultaDireccionMasivaMBean() {
        createSol = false;
        renderBarrio = false;
        maxFilesQuantity = true;
        files = new ArrayList<UploadedFile>();
        size = 0;
        cityId = BigDecimal.ZERO;
        gpo_ciudad = new GeograficoPolitico();
        observaciones = "";
        fileNameReport = "";
        downloadMistakes = false;
        solicitudes = new ArrayList<DetalleSolicitud>();
    }

    /**
     * Carga el archivo que se adjunta como soporte a la información
     * suministrada
     *
     * @param ev
     */
    public void doEnviarTipoConsulta(ActionEvent ev) {
        //Se cargan el formulario de ingreso
        UIComponent cp = ev.getComponent();
        UIComponent panelPcpal = cp.findComponent("panelPrincipal");
        UIComponent enviar = cp.findComponent("enviar");
        UIComponent validar = cp.findComponent("validar");
        if (dir_tipoConsulta.equals("S") || dir_tipoConsulta.equals("C")) {
            panelPcpal.setRendered(true);
            enviar.setRendered(false);
            validar.setRendered(true);
        }
    }

    /**
     *
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void listener(FileUploadEvent event) throws ApplicationException {
        maxFilesQuantity = false;
        UploadedFile item = event.getUploadedFile();
        this.file = item;
        this.files.add(this.file);
        size = files.size();

        byte[] data = item.getData();
        StringBuffer texto = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            texto.append(c);
        }

        int numeroRegistros = texto.toString().split(System.getProperty("line.separator")).length;
        setTotalRegister(numeroRegistros);
        if (numeroRegistros < 1) {
            setMessage("Archivo sin Datos,Por favor verificar la estructura del archivo a cargar");
            this.message = MSG_ERRROR_LECTURA_ARCHIVO;
        } else {
            try {
                registros = new ArrayList<ArrayList<String>>();
                registros.addAll(loadtext(texto));
                setData(registros);
                int y = Integer.parseInt(ConstantSystem.cargarPropiedad("QCOLUMNSMASIVA"));
            } catch (IOException ex) {
                FacesUtil.mostrarMensajeError("Error al adicionar registros" + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error al adicionar registros " + ex.getMessage(), ex);
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error al adicionar registros" + ex.getMessage() , ex, LOGGER);
                throw new ApplicationException("Error al adicionar registros " + ex.getMessage(), ex);
            }
        }
    }

    /**
     *
     * @return
     */
    public String onFinishLoad() {
        if (createSol) {
            downloadMistakes = false;
        } else if (downloadMistakes) {
            createSol = false;
        }
        message = "true";
        return message;
    }

    /**
     * Descompone una cadena de texto String en renglones (salto de línea) y en
     * datos (separador |) ejemplo: este|es|un|ejemplo la descompone en 4
     * elementos de una lista; [este], [es], [un],[ejemplo]
     *
     * @param StringBuffer que se va a procesar
     * @return Una lista de listas de cadenas de texto (String)
     * @throws IOException
     */
    private ArrayList<ArrayList<String>> loadtext(StringBuffer texto) throws IOException {
        registros = new ArrayList<ArrayList<String>>();
        separator = System.getProperty("line.separator");
        String[] lineas = texto.toString().split(separator);

        message = MSG_ERROR_ESTRUCTURA;
        for (int i = 0; i < lineas.length; i++) {
            String[] linea = lineas[i].split("\t" + ",");
            ArrayList<String> listaDatos = new ArrayList<String>();
            for (int j = 0; j < linea.length; j++) {
                String dato = linea[j].trim();
                if (dato.isEmpty() || dato.equalsIgnoreCase("null")) {
                    dato = null;
                }
                listaDatos.add(dato);
                if (j == 0) {
                }
            }
            if (i == 0) {
                setLabel(listaDatos);
            } else {
                ArrayList<String> listInfo = new ArrayList<String>();
                registros.add(listaDatos);
            }
        }
        return registros;
    }

    /**
     *
     * @return
     */
    public String doLaunchProcess() {
        message = "L";
        setObservaciones("ddfgdd");
        return message;
    }

    /**
     *
     */
    private void validateColumn() {
        List<AddressGeodata> listae = null;
        List<AddressRequest> listRequest = new ArrayList<>();
        String data;
        for (int j = 0; j < getData().size(); j++) {
            getData().get(j);
            data = getData().get(j).toString();
            String[] splitdata = data.split("\t");

            AddressRequest request = new AddressRequest();
            for (int k = 0; k < Integer.parseInt(ConstantSystem.cargarPropiedad("QCOLUMNSMASIVA")); k++) {
                if (k == 1) {
                    request.setCity(splitdata[k]);
                } else if (k == 4) {
                    request.setAddress(splitdata[k]);
                }
                listRequest.add(request);
            }
        }

        try {
            listae = GeoreferenciaDelegate.queryListAddressGeodata(listRequest, "c");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en GeoreferenciaDelegate" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GeoreferenciaDelegate" + e.getMessage() , e, LOGGER);
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
            LOGGER.error("Error en parseInt" + ex.getMessage() , ex);
            return valueIfInvalid;
        } catch (Exception ex) {
            LOGGER.error("Error en parseInt" + ex.getMessage() , ex);
            return valueIfInvalid;
        }
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void downloadFile() throws IOException {
        FileInputStream is = new FileInputStream("C:/prueba.xls");
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment; filename=\"archivo.pdf\"");
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int read = is.read(buffer);
            while (read >= 0) {
                if (read > 0) {
                    os.write(buffer, 0, read);
                }
                read = is.read(buffer);
            }
            is.close();
            os.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al momento de descargar el archivo: " + e.getMessage() , e, LOGGER);
            try {
                is.close();
            } catch (IOException ex) {
                LOGGER.error("Error cerrando la conexión: " + ex.getMessage() , ex);
            } catch (Exception ex) {
                LOGGER.error("Error cerrando la conexión: " + ex.getMessage() , ex);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al descargar el archivo: " + e.getMessage() , e, LOGGER);
            try {
                is.close();
            } catch (IOException ex) {
                LOGGER.error("Error cerrando la conexión: " + ex.getMessage() , ex);
            } catch (Exception ex) {
                LOGGER.error("Error cerrando la conexión: " + ex.getMessage() , ex);
            }
        }
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
    public List<SelectItem> getBarrios() {
        return barrios;
    }

    /**
     *
     * @param barrios
     */
    public void setBarrios(List<SelectItem> barrios) {
        this.barrios = barrios;
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
    public List<SelectItem> getCiudades() {
        return ciudades;
    }

    /**
     *
     * @param ciudades
     */
    public void setCiudades(List<SelectItem> ciudades) {
        this.ciudades = ciudades;
    }

    /**
     *
     * @return
     */
    public ArrayList getLabel() {
        return label;
    }

    /**
     *
     * @param label
     */
    public void setLabel(ArrayList label) {
        this.label = label;
    }

    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    /**
     *
     * @return
     */
    public String getDir_tipoConsulta() {
        return dir_tipoConsulta;
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
     * @param dir_tipoConsulta
     */
    public void setDir_tipoConsulta(String dir_tipoConsulta) {
        this.dir_tipoConsulta = dir_tipoConsulta;
    }

    /**
     *
     * @return
     */
    public String validateLabels() {
        Boolean err = false;
        if (getTotalRegister() > 0) {
            String countLabel = ConstantSystem.cargarPropiedad("QCOLUMNSMASIVA");
            String atributo = (String) getLabel().get(0);
            String splitarray[] = atributo.split("\t");
            for (int column = 0; column < Integer.parseInt(ConstantSystem.cargarPropiedad("QCOLUMNSMASIVA")); column++) {
                switch (column) {
                    case 0:
                        if ("Departamento".equals(splitarray[column]) == false) {
                            setObservaciones("Por favor verificar la estructura del archivo a cargar.");
                            err = true;
                        }
                        break;
                    case 1:
                        if ("Ciudad".equals(splitarray[column]) == false) {
                            setObservaciones("Por favor verificar la estructura del archivo a cargar.");
                            err = true;
                        }
                        break;
                    case 2:
                        if ("Barrio".equals(splitarray[column]) == false) {
                            setObservaciones("Por favor verificar la estructura del archivo a cargar.");
                            err = true;
                        }
                        break;
                    case 3:
                        if ("Direccion".equals(splitarray[column]) == false) {
                            setObservaciones("Por favor verificar la estructura del archivo a cargar.");
                            setMessage("message");
                            err = true;
                        }
                        break;
                }
            }

            if (err.equals(false)) {
                validateColumn();
            }
        } else {
            setObservaciones("Archivo sin datos,Por favor verificar la estructura del archivo a cargar.");
        }
        return message;
    }

    /**
     *
     * @return
     */
    public String validate() {
        message = "Datos invalidos";
        return message;
    }

    /**
     *
     * @param listaData
     */
    public void setData(ArrayList<ArrayList<String>> listaData) {
        this.listaData = listaData;
    }

    /**
     *
     * @return
     */
    public ArrayList<ArrayList<String>> getData() {
        return listaData;
    }

    /**
     *
     * @param numRegistros
     */
    public void setTotalRegister(int numRegistros) {
        this.numRegistros = numRegistros;
    }

    /**
     *
     * @return
     */
    public int getTotalRegister() {
        return numRegistros;
    }

}
