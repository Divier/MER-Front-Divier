/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.servlet;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.MglSaveMapJsFacadeLocal;
import co.com.claro.mgl.jpa.entities.MglSaveMapJs;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocanegravm
 */
@WebServlet("/view/MGL/ReturnJsMap/")
public class ReturnJsMapServlet extends HttpServlet implements javax.servlet.Servlet {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ReturnJsMapServlet.class);

    @EJB
    private ResourceEJBRemote resourceEJB;
    @EJB
    private MglSaveMapJsFacadeLocal mglSaveMapJsFacadeLocal;
    private String rutaMapaGoogle = "";

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

            //realizo consulta del js en BD
            List<MglSaveMapJs> consultaMapa = mglSaveMapJsFacadeLocal.findAll();

            if (consultaMapa != null && !consultaMapa.isEmpty()) {
                MglSaveMapJs mapa = consultaMapa.get(0);
                Date hoy = new Date();
                Date fecRegBd = mapa.getFechaSave();
                Date hoySinHoras = getZeroTimeDate(hoy);
                Date regBdSinHoras = getZeroTimeDate(fecRegBd);
                if (regBdSinHoras.before(hoySinHoras)) {
                    Parametros param = resourceEJB.queryParametros(Constant.RUTA_MAPA_GOOGLE);
                    if (param != null) {
                        rutaMapaGoogle = param.getValor();
                    }

                    //LLamado a a la url de google se crea el nuevo js en el servidor y se retorna la informacion
                    // Para hacer caso omiso de validacion SSL.
                    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }};

                    SSLContext sc = SSLContext.getInstance("SSL");

                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                    URL url = new URL(null, rutaMapaGoogle, new sun.net.www.protocol.https.Handler());

                    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

                    int responseCode = httpConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        response.setContentType("text/html");
                        httpConnection.connect();
                        InputStream archivo = url.openStream();
                        BufferedReader bufferedReader;
                        StringBuilder stringBuilder = new StringBuilder();

                        //Lectura del archivo 
                        if (archivo != null) {
                            bufferedReader = new BufferedReader(new InputStreamReader(archivo));

                            char[] charBuffer = new char[128];
                            int bytesRead;

                            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                                stringBuilder.append(charBuffer, 0, bytesRead);
                            }
                        }

                        String js = stringBuilder.toString();
                        //Actualizo el registro en BD con la fecha del dia
                        mapa.setFechaSave(hoySinHoras);
                        mapa.setJsMap(js);
                        mglSaveMapJsFacadeLocal.update(mapa);
                        //carga del response del servlet
                        PrintWriter out = response.getWriter();
                        out.append(js);
                        out.close();
                    }

                } else if (hoySinHoras.before(regBdSinHoras)) {
                    //Esto nunca se puede dar
                    LOGGER.error("La Fecha hoy es menor a la del archivo");
                } else {
                    //Se toma la informacion del  archivo que hay en BD y se retorna la informacion
                    response.setContentType("text/html");
                    String archivo = mapa.getJsMap();
                    PrintWriter out = response.getWriter();
                    out.append(archivo);
                    out.close();
                }

            } else {

                LOGGER.error("No hay registro en BD de informacion del mapa de google");
            }
        } catch (ApplicationException | NoSuchAlgorithmException | KeyManagementException e) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ServletException(e);
        }

    }

    public static Date getZeroTimeDate(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res = calendar.getTime();
        return res;
    }

}
