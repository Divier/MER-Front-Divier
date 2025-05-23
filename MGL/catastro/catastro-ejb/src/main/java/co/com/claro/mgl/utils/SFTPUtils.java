package co.com.claro.mgl.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Utilidades para el manejo de transferencia de archivos SFTP (FTP seguro).
 *
 * @author Camilo Miranda <i>mirandaca</i>.
 */
public class SFTPUtils {

    /**
     * Sesion SFTP establecida.
     */
    private Session session;
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(SFTPUtils.class.getName());

    /**
     * Establece una conexion SFTP.
     *
     * @param username Nombre de usuario.
     * @param password Contrase&ntilde;a.
     * @param host Host a conectar.
     * @param port Puerto del Host.
     *
     * @throws JSchException Cualquier error al establecer conexión SFTP.
     * @throws IllegalAccessException Indica que ya existe una conexion SFTP
     * establecida.
     */
    public void connect(String username, String password, String host, int port)
            throws JSchException, IllegalAccessException {

        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();

            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password);

            // Parametro para no validar key de conexion.
            this.session.setConfig("StrictHostKeyChecking", "no");

            this.session.connect();
        } else {
            LOGGER.error("Sesion SFTP ya iniciada.");
        }
    }

    /**
     * A&ntilde;ade un archivo al directorio FTP usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se agregar&aacute; el archivo.
     * @param filePath Directorio donde se encuentra el archivo a subir en
     * disco.
     * @param fileName Nombre que tendra el archivo en el destino.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final void putFile(String ftpPath, String filePath,
            String fileName) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);


            LOGGER.info(String.format("Creando archivo %s en el "
                    + "directorio %s", fileName, ftpPath));
            channelSftp.put(filePath, fileName);

            LOGGER.info("Archivo subido exitosamente");

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }

    /**
     * Cierra la sesion SFTP.
     */
    public final void disconnect() {
        if (this.session != null) {
            this.session.disconnect();
        }
    }

    /**
     * Obtiene un archivo del directorio FTP, usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se encuentra el archivo.
     * @param destPath Ruta donde ser&aacute; descargado el archivo.
     * @param fileName Nombre del archivo que se desea obtener.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final void getFile(String ftpPath, String destPath,
            String fileName) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);

            LOGGER.info(String.format("Obteniendo archivo %s del "
                    + "directorio %s", fileName, ftpPath));
            channelSftp.get(fileName, destPath);

            LOGGER.info("Archivo descargado exitosamente");

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }
    
    
      /**
     * Obtiene un archivo del directorio FTP, usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se encuentra el archivo.
     * @param fileName Nombre del archivo que se desea obtener.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final String getFileString(String ftpPath,
            String fileName) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        InputStream archivo;
        String respuesta;

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);

            LOGGER.info(String.format("Obteniendo archivo %s del "
                    + "directorio %s", fileName, ftpPath));

            archivo = channelSftp.get(fileName);

            respuesta = new BufferedReader(new InputStreamReader(archivo))
                    .lines().collect(Collectors.joining("\n"));

            LOGGER.info("Archivo descargado exitosamente");

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
        return respuesta;
    }
    
      /**
     * Borra un archivo del directorio FTP, usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se encuentra el archivo.
     * @param fileName Nombre del archivo que se desea borrar.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final boolean deleteFile(String ftpPath,
            String fileName) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        boolean borro;

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);

            LOGGER.info(String.format("Borrando archivo %s del "
                    + "directorio %s", fileName, ftpPath));

            channelSftp.rm(ftpPath+fileName);

            borro = true;

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
        return borro;
    }

    /**
     * Obtiene el listado completo de archivos contenidos en el directorio FTP,
     * usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se encuentra el archivo.
     * @param destPath Ruta donde ser&aacute; descargado el archivo.
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final void getFiles(String ftpPath, String destPath) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);


            LOGGER.info(String.format("Obteniendo archivos del "
                    + "directorio %s", ftpPath));

            Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(ftpPath);

            if (lsEntries != null && !lsEntries.isEmpty()) {
                for (ChannelSftp.LsEntry entry : lsEntries) {
                    try {
                        channelSftp.get(entry.getFilename(), new StringBuilder(destPath)
                                .append(File.separator).append(entry.getFilename()).toString());
                    } catch (SftpException e) {
                        LOGGER.error("No pudo descargarse el archivo " + entry.getFilename() + ": " + e.getMessage());
                    }
                }
            }

            LOGGER.info("Proceso de descarga finalizado.");

            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }

    /**
     * Obtiene el listado completo de archivos contenidos en el directorio FTP,
     * usando el protocolo SFTP.
     *
     * @param ftpPath Path del FTP donde se encuentra el archivo.
     * @return 
     *
     * @throws IllegalAccessException Excepci&oacute;n lanzada cuando no hay
     * conexi&oacute;n establecida.
     * @throws JSchException Excepci&oacute;n lanzada por alg&uacute;n error en
     * la ejecuci&oacute;n del comando SFTP.
     * @throws SftpException Error al utilizar comandos SFTP.
     * @throws IOException Excepci&oacute;n al leer el texto arrojado luego de
     * la ejecuci&oacute;n del comando SFTP.
     */
    public final List<String> getNameFiles(String ftpPath) throws IllegalAccessException, IOException,
            SftpException, JSchException {

        List<String> nombreArchivos = new ArrayList<String>();

        if (this.session != null && this.session.isConnected()) {

            // Abrimos un canal SFTP. Es como abrir una consola.
            ChannelSftp channelSftp = (ChannelSftp) this.session.
                    openChannel("sftp");

            channelSftp.connect();

            // Nos ubicamos en el directorio del FTP.
            channelSftp.cd(ftpPath);

            LOGGER.info(String.format("Obteniendo el nombre de los archivos del "
                    + "directorio %s", ftpPath));

            Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(ftpPath);

            if (lsEntries != null && !lsEntries.isEmpty()) {
                for (ChannelSftp.LsEntry entry : lsEntries) {

                    if (entry.getFilename().equalsIgnoreCase(".")
                            || entry.getFilename().equalsIgnoreCase("..")) {
                        LOGGER.info("Nombre desconocido");
                    } else {
                        nombreArchivos.add(entry.getFilename());
                    }
                }
            }
            LOGGER.info("Proceso de descarga finalizado.");
            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
        return nombreArchivos;
    }

    public boolean deleteWithChildren(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        return this.deleteChildren(file) && file.delete();
    }

    private boolean deleteChildren(File dir) {
        File[] children = dir.listFiles();
        boolean childrenDeleted = true;
        for (int i = 0; children != null && i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory()) {
                childrenDeleted = this.deleteChildren(child) && childrenDeleted;
            }
            if (child.exists()) {
                childrenDeleted = child.delete() && childrenDeleted;
            }
        }
        return childrenDeleted;
    }
    
    public List<String> listarFicherosPorCarpeta(final File carpeta) {

        List<String> nombreArchivos = new ArrayList<>();
        
        if (carpeta != null) {
            for (final File ficheroEntrada : carpeta.listFiles()) {
                if (ficheroEntrada.isDirectory()) {
                    listarFicherosPorCarpeta(ficheroEntrada);
                } else {
                    nombreArchivos.add(ficheroEntrada.getName());
                }
            }
        }
        return nombreArchivos;
    }
}
