/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm.business;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.FileServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author user
 */
public class SolicitudCMBusiness {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudCMBusiness.class);

    public Boolean uploadFileSolicitud(
            File uploadedFile, String usuario, String fileName, Date fecha)
            throws ApplicationException, Exception {

        try {
            boolean archivoCargado = FileServer.uploadFileMultiServer(uploadedFile, fileName);

            if (archivoCargado) {
                // Successfully Uploaded
                LOGGER.error("correcto al subir el archivo");
                return true;
            } else {
                LOGGER.error("error al subir el archivo");
                return false;
                // Did not upload. Add your logic here. Maybe you want to retry.
            }
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al cargar el archivo. EX000: " + ex.getMessage(), ex);
        }
    }
}
