package co.com.claro.mgl.client.ftp;

import com.jcraft.jsch.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * Utilidad para conectar a FTP segura, subir y descargar archivos
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Builder
@Getter
@Setter
public class ClienteFTP {

    private static final Logger LOGGER = LogManager.getLogger(ClienteFTP.class);

    private Session session;
    String host;
    int port;
    String user;
    String password;

    /**
     * Se establece conexión con objeto ClientFTP creado con builder
     * @throws JSchException
     */
    public void abrirConexion() throws JSchException {
        if (session == null || !session.isConnected()) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } else {
            LOGGER.error("Sesion SFTP ya iniciada.");
        }
    }

    /**
     * Realizar desconexión a servidor FTP
     */
    public void cerrarConexion(){
        this.session.disconnect();
    }

    /**
     * Se realiza descarga de archivo a un directorio local
     * @param ftpPath - Ruta del archivo en servidor FTP
     * @param pathLocal - Ruta local donde se almacenará el archivo descargado
     * @param fileName - Nombre del archivo a descargar
     * @throws IllegalAccessException
     * @throws SftpException
     * @throws JSchException
     * @throws IOException
     */
    public final void getFile(String ftpPath, String pathLocal, String fileName) throws IllegalAccessException, SftpException, JSchException, IOException {

        if (this.session != null && this.session.isConnected()) {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;

            try{
                channelSftp.cd(ftpPath);
            }catch (SftpException sftpException){
                throw new FileNotFoundException("No existe el directorio seleccionado en el repositorio!");
            }
            Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(
                    new StringBuilder("*").append(fileName).toString());

            if (lsEntries.isEmpty()) {
                LOGGER.info("No existe el archivo seleccionado en el repositorio!");
                throw new FileNotFoundException("No existe el archivo seleccionado en el repositorio!");
            }

            for (ChannelSftp.LsEntry entry : lsEntries) {
                try {
                    channelSftp.get(entry.getFilename(),
                            new StringBuilder(pathLocal).append(entry.getFilename()).toString());
                } catch (SftpException sftpException) {
                    LOGGER.error("Failed to download the file the sftp folder location.");
                }
            }
            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }
    /**
     * Guardar archivo en servidor FTP
     * @param ftpPath
     * @param file
     * @param nameFile
     * @throws SftpException
     * @throws JSchException
     * @throws IOException
     */
    public final void putFile(String ftpPath, InputStream file, String nameFile) throws SftpException, JSchException, IOException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;

        try{
            channelSftp.cd(ftpPath);
            channelSftp.put(file, nameFile, ChannelSftp.OVERWRITE);
        }catch (SftpException sftpException){
            LOGGER.info("No se pudo subir el archivo al directorio seleccionado en el repositorio!");
            throw new FileNotFoundException("No se pudo subir el archivo al directorio seleccionado en el repositorio!");
        }
        channelSftp.exit();
        channelSftp.disconnect();
    }

}
