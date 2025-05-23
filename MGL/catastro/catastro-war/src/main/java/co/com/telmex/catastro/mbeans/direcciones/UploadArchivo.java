package co.com.telmex.catastro.mbeans.direcciones;

import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase UploadArchivo Extiende de HttpServlet
 *
 * @author Carlos Villamil
 * @version	1.0
 */
public class UploadArchivo extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(UploadArchivo.class);

    private static final long serialVersionUID = -3208409086358916855L;
    private String TEMPORAL_UPLOAD = "";
    private String ROOT_UPLOAD = "";
    private int SIZE_FILE = 0;

    /**
     *
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();

        Parametros param = null;
        try {
            param = recursos.queryParametros(Constant.UPLOAD_ARCHIVO);
            if (param != null) {
                TEMPORAL_UPLOAD = param.getValor();
            }
            param = recursos.queryParametros(Constant.ROOT_UPLOAD);
            if (param != null) {
                ROOT_UPLOAD = param.getValor();
            }
            param = recursos.queryParametros(Constant.SIZE_FILE);
            if (param != null) {
                SIZE_FILE = Integer.parseInt(param.getValor());
            }

        } catch (NumberFormatException ex) {
            LOGGER.error("Error al momento de consultar los parámetros de configuración. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de consultar los parámetros de configuración. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        queryParametrosConfig();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String message = "";
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        fileName.indexOf(ROOT_UPLOAD.substring(0, 1));
                        if (fileName.indexOf(ROOT_UPLOAD.substring(0, 1)) > -1) {
                            String Separator = ROOT_UPLOAD.substring(0, 1);
                            String[] ruta = fileName.replace(Separator, ",").toString().split(",");
                            fileName = ruta[ruta.length - 1];
                        }
                        String root = TEMPORAL_UPLOAD;
                        // verificamos si el archivo es > 0
                        if (item.getSize() > 0 && (item.getSize() / 1024) <= SIZE_FILE) {
                            // obtener el nombre, tipo y extension del archivo
                            String nombreArchivo = item.getName();
                            String tipo = item.getContentType();
                            String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
                            // si es un csv valido
                            if (tipo.compareTo("text/txt") == 0 || extension.compareToIgnoreCase(".txt") == 0 || tipo.compareTo("text/csv") == 0 || extension.compareToIgnoreCase(".csv") == 0) {

                                File path = new File(root);
                                if (!path.exists()) {
                                    path.mkdirs();
                                }
                                File uploadedFile = new File(root + ROOT_UPLOAD + fileName);
                                item.write(uploadedFile);

                                File file = new File(root + ROOT_UPLOAD + fileName);
                                try (InputStream inputStream = new FileInputStream(file)) {
                                    int size = (int) file.length();
                                    byte[] bytes = new byte[size];
                                    inputStream.read(bytes);
                                    HttpSession session = request.getSession(true);
                                    session.setAttribute("MyArchivo", bytes);
                                } catch (FileNotFoundException ex) {
                                    LOGGER.error("Error, no se ha podido localizar el archivo: ".concat(ex.getMessage()),
                                            ex);
                                } catch (IOException ex) {
                                    LOGGER.error("Error al momento de realizar lectura del archivo : ".concat(ex.getMessage()),
                                            ex);
                                }
                            } else {
                                message = "Extension no valida";
                            }
                        } else {
                            if (item.getSize() <= 0) {
                                message = "El archivo esta vacío.";
                            } else if ((item.getSize() / 1024) > SIZE_FILE) {
                                message = "El archivo supera el tamaño permitido: " + SIZE_FILE + " KB";
                            }
                        }
                    } else {
                        message = "Error en la carga del archivo";
                    }

                }
            } catch (FileUploadException ex) {
                LOGGER.error("Error al momento de realizar la petición. EX000: " + ex.getMessage(), ex);
            } catch (Exception ex) {
                LOGGER.error("Error al momento de realizar la petición. EX000: " + ex.getMessage(), ex);
            }
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<script type=\"text/javascript\">");
        if (!message.equals("")) {
            out.print("alert('Archivo no valido, " + message + "' );");
            out.println("javascript:window.history.back();");
            out.println("</script>");
        } else {
            out.println("alert('El Archivo subió en el servidor, se iniciará el proceso de cargue de datos');");
            out.println("javascript:window.opener.location= window.opener.location;self.window.close();");
            out.println("</script>");
        }
    }
}
