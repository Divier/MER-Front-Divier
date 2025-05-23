/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author bocanegravm
 */
@WebServlet("/view/MGL/ubicacion/download/")
public class DownloadResultadoUbicacionServlet extends HttpServlet implements javax.servlet.Servlet {

    
    private static final Logger LOGGER = LogManager.getLogger(DownloadResultadoUbicacionServlet.class);
    private final Locale LOCALE = new Locale("es", "CO");

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
        try {
            HttpSession session = request.getSession();
            List<String> resultado = (List<String>) session.getAttribute("resultUbicacion");
            session.removeAttribute("resultUbicacion");
            StringBuilder sb = new StringBuilder();

            if (resultado != null && !resultado.isEmpty()) {
                for (String registro : resultado) {
                    sb.append(registro);
                    sb.append("\n");
                }

                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss", LOCALE);
                String todayStr = formato.format(new Date());

                String fileName = "Reporte_Cargue_Masivo_Ubicacion" + "_" + todayStr + "." + "csv";

                InputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes(Charset.forName("UTF-8")));

                String disposition = "attachment; fileName= " + fileName + "";
                response.reset();
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", disposition);
                response.setHeader("content-Length", String.valueOf(stream(inputStream, response.getOutputStream())));

                response.setStatus(200);
                response.getOutputStream().flush();
                inputStream.close();
                response.getOutputStream().close();

            }
        } catch (IOException e) {
            LOGGER.error("Error | Accion no permitida : ".concat(e.getMessage()), e);
        }
    }

    private long stream(InputStream input, OutputStream output) throws IOException {

        try (ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output)) {
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }
            return size;
        }
    }
}
