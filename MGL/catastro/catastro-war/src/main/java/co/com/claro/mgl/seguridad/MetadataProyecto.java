/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.seguridad;

import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.jar.Manifest;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author
 */
public class MetadataProyecto {
    
    private static final Logger LOGGER = LogManager.getLogger(MetadataProyecto.class);

    private String version;
    private String fechaVersion;
    private String host;
    private Manifest manifiesto;
    private Properties properties;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getDefaultLocale() {
        return Locale.getDefault().getDisplayName();
    }

    /**
     * Obtiene la fecha de la versión de Maven.
     *
     * @return fecha de la versión de Maven con el formato especificado en el POM.
     */
    public String getFechaVersion() {
        return fechaVersion;
    }

    /**
     * Obtiene la versión de compilación definida en el POM de Maven para el EAR.
     *
     * @return versión
     */
    public String getVersion() {
        return version;
    }

    public Manifest getManifiesto() {
        return manifiesto;
    }

    public String getHost() {
        return host;
    }
    //</editor-fold>

    private void cargarManifiesto() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        URL resource = externalContext.getResource("/META-INF/MANIFEST.MF");
        if (resource != null) {
            String path = resource.getPath();
            manifiesto = new Manifest(new FileInputStream(new File(path)));
        }
    }
    
    
    /**
     * Realiza la lectura del archivo <i>build.properties</i>.
     * @throws IOException
     * @throws FileNotFoundException 
     */
    private void cargarBuildProperties() throws IOException, FileNotFoundException {
        properties = new Properties();
        String propFileName = "build.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("No se encuentra el archivo '" + propFileName + "' en el classpath.");
        }
    }
    

    private MetadataProyecto() {
        try {
            // Cargar la informacion desde el properties construido por Maven.
            try {
                cargarBuildProperties();
            } catch (IOException e) {
                LOGGER.error("Error cargando build.properties: " + e.getMessage());
                properties = null;
            }catch (Exception e) {
                LOGGER.error("Error cargando build.properties: " + e.getMessage());
                properties = null;
            }
            
            if (properties != null) {
                fechaVersion = properties.getProperty("build.date");
                if (fechaVersion == null || fechaVersion.isEmpty()) {
                    fechaVersion = "dd/MM/yyyy HH:mm";
                }
                version = properties.getProperty("build.version");
                host = properties.getProperty("build.hostname");
            } else {
                // Cargar informacion desde el MANIFEST (build-impl.xml).
                cargarManifiesto();
                if (manifiesto != null) {
                    fechaVersion = manifiesto.getMainAttributes().getValue("Build-Datetime");
                    if (fechaVersion != null) {
                        fechaVersion = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(
                                new SimpleDateFormat("ddMMyyyyHHmm").parse(fechaVersion));
                    } else {
                        fechaVersion = "dd/MM/yyyy HH:mm";
                    }
                    version = manifiesto.getMainAttributes().getValue("Build-Ant-Version");
                    host = manifiesto.getMainAttributes().getValue("Build-Hostname");
                }
            }
        } catch (IOException ex) {
                 String msg = "Se produjo un error al momento de ejecutar el método "
                    + " " + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método "
                    + " " + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } finally {
            if ("null".equals(fechaVersion)) {
                fechaVersion = null;
            }
            if ("null".equals(version)) {
                version = null;
            }
            if ("null".equals(host)) {
                host = null;
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    public static MetadataProyecto getInstance() {
        LOGGER.error("getInstance()");
        MetadataProyecto metaData = MetadataProyectoHolder.INSTANCE;
        return metaData;
    }

    private static class MetadataProyectoHolder {

        private static final MetadataProyecto INSTANCE = new MetadataProyecto();
    }
    //</editor-fold>
}

