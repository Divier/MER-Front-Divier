/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.servlet;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.war.documentos.util.GestorDocumentalUtil;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Servlet usado para obtener una imagen de CCMM desde el Gestor Documental,
 * a trav&eacute;s de una petici&oacute;n <i>POST</i> a <b>UCM</b>.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@WebServlet("/view/MGL/CM/image/*")
public class CMImageServlet extends HttpServlet {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CMImageServlet.class);
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;

    /**
     * M&eacute;todo encargado de procesar las peticiones tipo GET.
     *
     * @param request  HTTP Servlet Request.
     * @param response HTTP Servlet Response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request != null && request.getPathInfo() != null && !request.getPathInfo().isEmpty()) {
                BigDecimal cuentaMatrizId = new BigDecimal(request.getPathInfo().substring(1));

                if (cuentaMatrizId.compareTo(BigDecimal.ZERO) > 0) {
                    // Buscar la cuenta matriz asociada al ID especificado como parametro.
                    CmtCuentaMatrizMgl cmtCuentaMatrizMgl = cmtCuentaMatrizMglFacadeLocal.findByIdCM(cuentaMatrizId);

                    if (cmtCuentaMatrizMgl != null) {
                        String imageURL = cmtCuentaMatrizMgl.getImgCuenta();
                        if (imageURL != null && !imageURL.isEmpty()) {
                            String imageName = imageURL.substring(imageURL.lastIndexOf("/"));

                            LOGGER.info("Obteniendo imagen de cuenta matriz " + cuentaMatrizId + ": " + imageURL);

                            try {

                                // Obtiene el InputStream del archivo obtenido del request.
                                InputStream content =
                                        GestorDocumentalUtil.getInputStreamFromUcmURL(imageURL);

                                // Obtiene los bytes del InputStream.
                                byte[] imageBytes = IOUtils.toByteArray(content);

                                if (imageBytes != null && imageBytes.length > 0) {
                                    // Envia los bytes del documento a la respuesta del servlet.
                                    response.setContentType(getServletContext().getMimeType(imageName));
                                    response.setContentLength(imageBytes.length);
                                    response.getOutputStream().write(imageBytes);
                                }

                            } catch (ApplicationException | IOException e) {
                                String msg = "No ha sido posible conectar con la URL del gestor documental, al momento de consultar la información de la cuenta matriz " + cuentaMatrizId + ":"+e.getMessage();
                                LOGGER.error(msg);
                                throw new ServletException(msg);
                            }

                        } else {
                            String msg = "La cuenta matriz " + cuentaMatrizId + " no posee imagen asociada.";
                            LOGGER.error(msg);
                            throw new ServletException(msg);
                        }
                    } else {
                        String msg = "No fue encontrada la cuenta matriz con identificador " + cuentaMatrizId;
                        LOGGER.error(msg);
                        throw new ServletException(msg);
                    }
                } else {
                    String msg = "El identificador de cuenta matriz proporcionado es incorrecto.";
                    LOGGER.error(msg);
                    throw new ServletException(msg);
                }
            } else {
                String msg = "No fue proporcionado el identificador de la cuenta matriz.";
                LOGGER.error(msg);
                throw new ServletException(msg);
            }
        } catch (ApplicationException | ServletException e) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ServletException(e);
        }

    }
}
