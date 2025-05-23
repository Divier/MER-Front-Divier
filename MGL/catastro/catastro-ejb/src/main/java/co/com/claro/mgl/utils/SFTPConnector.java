/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que permite realizar la conexion SFTP para subir y bajar los archivos
 * procesados por ODI. Es ODI quien se encarga de generar los archivos *.csv en
 * estos directorios remotos
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class SFTPConnector {

    private Session session;

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
            throw new IllegalAccessException("Sesion SFTP ya iniciada.");
        }
    }

    public final void addfile(String ftpPath, InputStream archivo, String fileName) throws IllegalAccessException, SftpException, JSchException, IOException {
        if (this.session != null && this.session.isConnected()) {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(ftpPath);
            channelSftp.put(archivo, fileName);
            channelSftp.exit();
            channelSftp.disconnect();
        } else {
            throw new IllegalAccessException("No existe sesion SFTP iniciada.");
        }
    }

    public InputStream getfile(String usuario, String ip, int puerto, String paswword, String rutaarchivo) throws JSchException, SftpException, IOException {
        JSch ssh = new JSch();
        Session sessionget = ssh.getSession(usuario, ip, puerto);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        sessionget.setConfig(config);
        sessionget.setPassword(paswword);
        sessionget.connect();
        Channel channel = sessionget.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        InputStream in = sftp.get(rutaarchivo);
        return in;
    }

    public void writeToFile(String usuario, String ip, int puerto, String paswword, String rutaarchivo, String data) throws JSchException, SftpException, IOException {
        JSch ssh = new JSch();
        Session sessionget = ssh.getSession(usuario, ip, puerto);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        sessionget.setConfig(config);
        sessionget.setPassword(paswword);
        sessionget.connect();
        Channel channel = sessionget.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;          
        InputStream inputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));        
        sftp.put(inputStream, rutaarchivo);        
        inputStream.close();        
        sftp.disconnect();
        sessionget.disconnect();
    }

    public final void disconnect() {
        this.session.disconnect();
    }

}
