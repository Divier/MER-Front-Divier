/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.servlet;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtArchivosVtMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.war.documentos.util.GestorDocumentalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet usado para obtener una imagen de CCMM desde el Gestor Documental, a
 * trav&eacute;s de una petici&oacute;n <i>POST</i> a <b>UCM</b>.
 *
 * @author bocanegra victor
 */
@WebServlet("/view/MGL/document/download/*")
public class CMDocServlet extends HttpServlet {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CMDocServlet.class);
    
    @EJB
    private CmtArchivosVtMglFacadeLocal cmtArchivosVtMglFacadeLocal;


    /**
     * M&eacute;todo encargado de procesar las peticiones tipo GET.
     *
     * @param request HTTP Servlet Request.
     * @param response HTTP Servlet Response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request != null && request.getPathInfo() != null && !request.getPathInfo().isEmpty()) {
            String param = request.getPathInfo().substring(1);
            String url = null;
            if (param != null) {
                if (!param.toLowerCase().contains("http")) {
                    try {
                        BigDecimal archivosVTId = new BigDecimal(param);
                        CmtArchivosVtMgl archivosVtMgl = cmtArchivosVtMglFacadeLocal.findById(archivosVTId);
                        if (archivosVtMgl != null) {
                            url = archivosVtMgl.getRutaArchivo();
                        }
                    } catch (NumberFormatException | ApplicationException e) {
                        String msg = "Error al momento de procesar la petición GET. EX000: " + e.getMessage();
                        LOGGER.error(msg, e);
                        throw new ServletException(msg, e);
                    }
                } else {
                    url = param;
                }
            }

            if (url != null) {
                LOGGER.info("Obteniendo documento de la url:  " + url + "");

                try {

                    // Obtiene el InputStream del archivo obtenido del request.
                    InputStream content =
                            GestorDocumentalUtil.getInputStreamFromUcmURL(url);

                    String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());

                    response.reset();
                    response.setHeader("Content-Type", "application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");

                    OutputStream responseOutputStream = response.getOutputStream();

                    byte[] bytesBuffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = content.read(bytesBuffer)) > 0) {
                        responseOutputStream.write(bytesBuffer, 0, bytesRead);
                    }

                    responseOutputStream.flush();

                    content.close();
                    responseOutputStream.close();

                } catch (ApplicationException | IOException e) {
                    String msg = "No ha sido posible conectar con la URL del gestor documental,"
                            + " al momento de consultar la información.";
                    LOGGER.error(msg);
                    throw new ServletException(msg, e);
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            } else {
                String msg = "No fue posible obtener una URL con el parámetro " + param;
                LOGGER.error(msg);
                throw new ServletException(msg);
            }

        } else {
            String msg = "No se envio url del documento.";
            LOGGER.error(msg);
            throw new ServletException(msg);
        }
    }
}
