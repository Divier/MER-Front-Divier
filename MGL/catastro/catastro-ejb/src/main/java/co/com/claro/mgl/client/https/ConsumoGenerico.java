/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.client.https;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgw.agenda.util.GetResourcesResponses;
import co.com.claro.mgw.agenda.util.GetTechnologiesResponse;
import co.com.claro.ofscCapacity.activityBookingOptions.GetActivityBookingResponses;
import static com.sun.jersey.core.util.ReaderWriter.BUFFER_SIZE;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpStatus;


/**
 *
 * @author bocanegravm
 */
public class ConsumoGenerico {

    private static final Logger LOGGER = LogManager.getLogger(ConsumoGenerico.class);

    public String metodoHTTP;
    public URL url;
    public String authorization;
    private Object requestApi;
    private Object requestEntrada;
    private Object response;
    private ParametrosMglManager parametrosMglManager;
    private String rutaCert;
    
    public ConsumoGenerico(){}

    public ConsumoGenerico(String metodoHTTP, URL url, String authorization,
            Object requestApi, Object requestEntrada, Object response) {
        this.metodoHTTP = metodoHTTP;
        this.url = url;
        this.authorization = authorization;
        this.requestApi = requestApi;
        this.requestEntrada = requestEntrada;
        this.response = response;
        parametrosMglManager = new ParametrosMglManager();

    }
  
    public String getMetodoHTTP() {
        return metodoHTTP;
    }

    public void setMetodoHTTP(String metodoHTTP) {
        this.metodoHTTP = metodoHTTP;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Object getRequestApi() {
        return requestApi;
    }

    public void setRequestApi(Object requestApi) {
        this.requestApi = requestApi;
    }

    public Object getRequestEntrada() {
        return requestEntrada;
    }

    public void setRequestEntrada(Object requestEntrada) {
        this.requestEntrada = requestEntrada;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getRutaCert() {
        return rutaCert;
    }

    public void setRutaCert(String rutaCert) {
        this.rutaCert = rutaCert;
    }

    public JSONObject consumirServicioHttps() throws ApplicationException, IllegalArgumentException {

        try {
            rutaCert = parametrosMglManager.findByAcronimo(ConstansClient.TRUST_STORE_RUTA)
                    .iterator().next().getParValor();
            LOGGER.error("RUTA:" + "" + rutaCert);
            System.setProperty(ConstansClient.TRUST_STORE, rutaCert);
            System.setProperty(ConstansClient.TRUST_STORE_PASSWORD, ConstansClient.TRUST_STORE_PASSWORD_NAME);
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            char[] passphrase = ConstansClient.TRUST_STORE_PASSWORD_NAME.toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");
            File file = new File(rutaCert.trim());
            keystore.load(new FileInputStream(file), passphrase); //path
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();
            HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
            httpsCon.setSSLSocketFactory(sf);
            httpsCon.setDoOutput(true);
            httpsCon.setRequestMethod(metodoHTTP);
            httpsCon.setAllowUserInteraction(false);
            httpsCon.setDoInput(true);
            httpsCon.setRequestProperty(ConstansClient.TYPE_HEADER, authorization);
            httpsCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (httpsCon.getResponseCode() != 200) {
                throw new ApplicationException("Error de consumo en ETA: " + "Codigo de error: "
                        + httpsCon.getResponseCode());
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (httpsCon.getInputStream())));
            String output, jsonResult = "";
            while ((output = br.readLine()) != null) {
                jsonResult += output;
            }
            httpsCon.disconnect();
            JSONObject jsonObj = new JSONObject(jsonResult);
            return jsonObj;
        } catch (ApplicationException | IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio. EX000: " + ex.getMessage(), ex);
        }

    }

    public JSONObject consumirServicioHttp() throws ApplicationException, IllegalArgumentException {

        try {
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod(metodoHTTP);
            httpCon.setAllowUserInteraction(false);
            httpCon.setDoInput(true);
            httpCon.setRequestProperty(ConstansClient.TYPE_HEADER, authorization);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (httpCon.getResponseCode() != 200) {
                throw new ApplicationException("Error de consumo en ETA: " + "Codigo de error: "
                        + httpCon.getResponseMessage());
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (httpCon.getInputStream())));
            String output, jsonResult = "";
            while ((output = br.readLine()) != null) {
                jsonResult += output;
            }
            httpCon.disconnect();
            JSONObject jsonObj = new JSONObject(jsonResult);
            return jsonObj;
        } catch (ApplicationException | IOException | JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio. EX000: " + ex.getMessage(), ex);
        }

    }

    public InputStream bajarArchivoHttp(String url, String usuario, String pass) throws ApplicationException, IllegalArgumentException {

        try {

            java.net.Authenticator myAuth = new java.net.Authenticator() {
                @Override
                protected java.net.PasswordAuthentication getPasswordAuthentication() {
                    return new java.net.PasswordAuthentication(usuario, pass.toCharArray());
                }
            };
            java.net.Authenticator.setDefault(myAuth);
            URL urlCon = new URL(url);
            URLConnection uc = urlCon.openConnection(); 
            InputStream in = uc.getInputStream();

            return in;
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de descargar el archivo. EX000: " + ex.getMessage(), ex);
        }

    }

    public InputStream bajarArchivo(String urlArc) throws ApplicationException, IllegalArgumentException {

        try {
            rutaCert = parametrosMglManager.findByAcronimo(ConstansClient.TRUST_STORE_RUTA)
                    .iterator().next().getParValor();

            System.setProperty(ConstansClient.TRUST_STORE, rutaCert);
            System.setProperty(ConstansClient.TRUST_STORE_PASSWORD, ConstansClient.TRUST_STORE_PASSWORD_NAME);
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            char[] passphrase = ConstansClient.TRUST_STORE_PASSWORD_NAME.toCharArray(); //password
            KeyStore keystore = KeyStore.getInstance("JKS");

            keystore.load(new FileInputStream(rutaCert), passphrase); //path

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            SSLContext context = SSLContext.getInstance("TLSv1.2");
            TrustManager[] trustManagers = tmf.getTrustManagers();
            context.init(null, trustManagers, null);
            SSLSocketFactory sf = context.getSocketFactory();

            URL urlArchivo = new URL(urlArc);
            HttpsURLConnection httpsCon = (HttpsURLConnection) urlArchivo.openConnection();
            httpsCon.setSSLSocketFactory(sf);
            httpsCon.setDoOutput(true);
            httpsCon.setRequestMethod(metodoHTTP);
            httpsCon.setAllowUserInteraction(false);
            httpsCon.setDoInput(true);
            httpsCon.setRequestProperty(ConstansClient.TYPE_HEADER, authorization);
            httpsCon.setRequestProperty("Accept", "application/octet-stream");

            InputStream result = httpsCon.getInputStream();

            if (httpsCon.getResponseCode() != 200) {
                throw new ApplicationException("Error de consumo en ETA: " + "Codigo de error: "
                        + httpsCon.getResponseCode());
            }

            return result;
        } catch (ApplicationException | IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de descargar el archivo. EX000: " + ex.getMessage(), ex);
        }

    }

    public GetResourcesResponses consumirServicioResources() throws ApplicationException {

        try {
            GetResourcesResponses responsesResources = new GetResourcesResponses();
            JSONObject jsonObj = consumirServicioHttp();

            String nombreTec = jsonObj.getString(ConstansClient.ATR_NAME);
            responsesResources.setName(nombreTec);
            String idTec = jsonObj.getString(ConstansClient.ATR_ID_TEC);
            responsesResources.setResourceId(idTec);
            String idAliado = jsonObj.getString(ConstansClient.ATR_ID_ALIA);
            responsesResources.setXR_IdAliado(idAliado);

            return responsesResources;
        } catch (JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio. EX000: " + ex.getMessage(), ex);
        }
    }

    public List<String> consumirServicioActivities() throws ApplicationException {

        JSONObject jsonObj = consumirServicioHttp();
        List<String> documentos = new ArrayList<>();

        if (jsonObj.has("A_FicheroAdjunto")) {
            documentos.add(ConstansClient.FICHERO_ONE);
        }
        if (jsonObj.has("A_FicheroAdjunto2")) {
            documentos.add(ConstansClient.FICHERO_TWO);
        }
        if (jsonObj.has("A_FicheroAdjunto3")) {
            documentos.add(ConstansClient.FICHERO_THREE);
        }
        if (jsonObj.has("A_FicheroAdjunto4")) {
            documentos.add(ConstansClient.FICHERO_FOUR);
        }
        if (jsonObj.has("A_Adjunto02")) {
            documentos.add(ConstansClient.FICHERO_TWO_NODONE);
        }

        return documentos;

    }

    public GetTechnologiesResponse consumirServicioTechnologies() throws ApplicationException {
        GetTechnologiesResponse getTechnologies = new GetTechnologiesResponse();
        JSONObject jsonObj = consumirServicioHttp();

        try {
            if (jsonObj.has("XA_NODO_HFCUNI_SELECT")) {                
                getTechnologies.setXA_NodoHfcUniSelect("XA_NODO_HFCUNI_SELECT");
            }

            if (jsonObj.has("XA_NODO_HFCBI_SELECT")) {
               getTechnologies.setXA_NodoHfcBiSelect("XA_NODO_HFCBI_SELECT");
            }

            if (jsonObj.has("XA_NODO_FOG_SELECT")) {
                getTechnologies.setXA_NodoFogSelect("XA_NODO_FOG_SELECT");
            }

            if (jsonObj.has("XA_NODO_DTH_SELECT")) {
                getTechnologies.setXA_NodoDthSelect("XA_NODO_DTH_SELECT");
            }

            if (jsonObj.has("XA_NODO_FTTH_SELECT")) {
               getTechnologies.setXA_NodoFtthSelect("XA_NODO_FTTH_SELECT");
            }

            if (jsonObj.has("XA_NODO_FOU_SELECT")) {
                getTechnologies.setXA_NodoFouSelect("XA_NODO_FOU_SELECT");
            }

            if (jsonObj.has("XA_NODO_BTS_SELEC")) {
                getTechnologies.setXA_NodoBtsSelect("XA_NODO_BTS_SELEC");
            }

            if (jsonObj.has("XA_NODO_WTH_SELECT")) {
                getTechnologies.setXA_NodoWthSelect("XA_NODO_WTH_SELECT");
            }

            if (jsonObj.has("XA_NODO_3G_SELECT")) {
               getTechnologies.setXA_Nodo3GSelect("XA_NODO_3G_SELECT");
            }

            if (jsonObj.has("XA_NODO_4G_SELECT")) {
                getTechnologies.setXA_Nodo4GSelect("XA_NODO_4G_SELECT");
            }

            if (jsonObj.has("XA_NODO_4.5G_SELECT")) {
                getTechnologies.setXA_Nodo4_5GSelect("XA_NODO_4.5G_SELECT");
            }

            if (jsonObj.has("XA_NODO_5G_SELECT")) {
               getTechnologies.setXA_Nodo5GSelect("XA_NODO_5G_SELECT");
            }

            if (jsonObj.has("XA_NODO_HFCUNI_AMPLIA")) {
               getTechnologies.setXA_NodoHfcUniAmplia("XA_NODO_HFCUNI_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_HFCBI_AMPLIA")) {
               getTechnologies.setXA_NodoHfcBiAmplia("XA_NODO_HFCBI_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_FOG_AMPLIA")) {
                getTechnologies.setXA_NodoFogAmplia("XA_NODO_FOG_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_DTH_AMPLIA")) {
               getTechnologies.setXA_NodoDthAmplia("XA_NODO_DTH_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_FTTH_AMPLIA")) {
                getTechnologies.setXA_NodoFtthAmplia("XA_NODO_FTTH_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_FOU_AMPLIA")) {
               getTechnologies.setXA_NodoFouAmplia("XA_NODO_FOU_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_BTS_AMPLIA")) {
                getTechnologies.setXA_NodoBtsAmplia("XA_NODO_BTS_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_WTH_AMPLIA")) {
               getTechnologies.setXA_NodoWthAmplia("XA_NODO_WTH_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_3G_AMPLIA")) {
                getTechnologies.setXA_Nodo3GAmplia("XA_NODO_3G_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_4G_AMPLIA")) {
                getTechnologies.setXA_Nodo4GAmplia("XA_NODO_4G_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_4.5G_AMPLIA")) {
               getTechnologies.setXA_Nodo4_5GAmplia("XA_NODO_4.5G_AMPLIA");
            }

            if (jsonObj.has("XA_NODO_5G_AMPLIA")) {
                getTechnologies.setXA_Nodo5GAmplia("XA_NODO_5G_AMPLIA");
            }
            return getTechnologies;
        } catch (JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio de tecnologías. EX000: " + ex.getMessage(), ex);
        }
    }
    
    
    public InputStream downloadForWorkforce(String urlArchivo) throws ApplicationException {
        try {
            // Para JDK7
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");

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
            
            final String typeSsh = "SSL";
            SSLContext sc = SSLContext.getInstance(typeSsh); 


            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL urlArc = new URL(null, urlArchivo, new sun.net.www.protocol.https.Handler());

            HttpsURLConnection connection = (HttpsURLConnection) urlArc.openConnection();
            connection.setRequestMethod(metodoHTTP);
            connection.setDoOutput(true);

            int timeout = 55000;
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

            connection.addRequestProperty("Accept", "application/octet-stream");
            connection.setRequestProperty("Authorization", authorization);
            return connection.getInputStream();
            
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de descargar de Workforce. EX000: " + ex.getMessage(), ex);
        }
    }
    
     public  void downloadFile(String fileURL, String saveDir , String user, String pass)
            throws IOException {
        URL urlD = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) urlD.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            // opens input stream from the HTTP connection
            InputStream inputStreamDow = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            try (FileOutputStream outputStreamDow = new FileOutputStream(saveFilePath)) {
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStreamDow.read(buffer)) != -1) {
                    outputStreamDow.write(buffer, 0, bytesRead);
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("Error no se pudo completar la escritura del archivo : ".concat(e.getMessage()), e);
            }
            inputStreamDow.close();
        }
        httpConn.disconnect();
    }
     
   public void saveStream( String mURL, String ofile, String user, String pass ) throws Exception {
    InputStream in = null;
    FileOutputStream out = null;
    try {
        URL urlDow = new URL(mURL);
        URLConnection urlConn = urlDow.openConnection();
        in = urlConn.getInputStream();
        out = new FileOutputStream(ofile);
        int c;
        byte[] b = new byte[1024];
        while ((c = in.read(b)) != -1)
            out.write(b, 0, c);
    } finally {
        if (in != null)
            in.close();
        if (out != null)
            out.close();
    }
}
   
    public <T> T consultarTecnicos(String json, Class<T> response) throws IOException, JSONException, ApplicationException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream;
        BufferedReader in;
        StringBuilder error;
        String currentLine;
        String description;
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty(ConstansClient.TYPE_HEADER, authorization);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(metodoHTTP);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        switch (conn.getResponseCode()) {
            case HttpStatus.SC_OK:
                // read the response
                InputStream input = new BufferedInputStream(conn.getInputStream());
                String result = org.apache.commons.io.IOUtils.toString(input, "UTF-8");
                JSONObject jsonObject = new JSONObject(result);
                
                input.close();
                conn.disconnect();
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                
                T responseObject = mapper.readValue(jsonObject.toString(), response);
                return responseObject;
            case HttpStatus.SC_NOT_FOUND:
                inputStream = conn.getErrorStream();
                in = new BufferedReader(
                        new InputStreamReader(
                                inputStream));
                error = new StringBuilder();
                
                while ((currentLine = in.readLine()) != null) {
                    error.append(currentLine);
                }
                in.close();
                description = "404: para el servicio con anonimo: " + 
                        co.com.telmex.catastro.services.util.Constant.BASE_URI_TECNICOS_ANTICIPADOS;
                throw new ApplicationException(error.toString() + " " + description);
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                inputStream = conn.getErrorStream();
                in = new BufferedReader(
                        new InputStreamReader(
                                inputStream));
                error = new StringBuilder();
                
                while ((currentLine = in.readLine()) != null) {
                    error.append(currentLine);
                }
                in.close();
                description = "500: para el servicio con anonimo: " +
                        co.com.telmex.catastro.services.util.Constant.BASE_URI_TECNICOS_ANTICIPADOS;
                throw new ApplicationException(error.toString() + ":" + description);
            default:
                inputStream = conn.getErrorStream();
                in = new BufferedReader(
                        new InputStreamReader(
                                inputStream));
                error = new StringBuilder();
                
                while ((currentLine = in.readLine()) != null) {
                    error.append(currentLine);
                }
                in.close();
                description = ": para el servicio con anonimo: " + 
                        co.com.telmex.catastro.services.util.Constant.BASE_URI_TECNICOS_ANTICIPADOS;
                throw new ApplicationException(error.toString() + " " + description);
        }

    }
    
    
    public <T> T enviarCorreos(int timeOut, String json, Class<T> response) throws IOException, JSONException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(timeOut * 1000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod(metodoHTTP);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        JSONObject jsonObject = new JSONObject(result);

        in.close();
        conn.disconnect();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
        
        T responseObject = mapper.readValue(jsonObject.toString(), response);
        return responseObject;
    }
    
     public List<String> consultaFormularios() throws ApplicationException {

        JSONObject jsonObj = consumirServicioHttp();

        String notas = "";
        List<String> retornaNotas = new ArrayList<>();
        String notasTecnico = "";
        String notasFirma = "";
        String notasClosure = "";

        String formEntregaSer = "Formulario Entrega Servicios: \n";
        String infoEntregaSer = "";
        int controlEntregaSer = 0;
        String formPuestaEnMarcha = "Formulario Puesta En Marcha: \n";
        String infoPuestaEnMarcha = "";
        int controlPuestaEnMarcha = 0;
        String formEquiposMedElec = "Formulario Equipos Mediciones Electricas: \n";
        String infoEquiposMedElec = "";
        int controlEquiposMedElec = 0;
        String formSiteSurvey = "Formulario Site Survey: \n";
        String infoSiteSurvey = "";
        int controlSiteSurvey = 0;
        String formVisitaTecnica = "Formulario Visita Tecnica: \n";
        String infoVisitaTecnica = "";
        int controlVisitaTecnica = 0;
        String formResultadoAco = "Formulario Resultado Acometida: \n";
        String infoResultadoAco = "";
        int controlResultadoAco = 0;

        try {

            if (jsonObj.has("MER_Nombre_ing_PEM")) {
                infoEntregaSer = "Nombre ing PEM : " + jsonObj.getString("MER_Nombre_ing_PEM") + "\n";
                controlEntregaSer++;
            }

            if (jsonObj.has("MER_Tel_Ing_PEM")) {
                infoEntregaSer = infoEntregaSer + "Tel Ing. PEM : " + jsonObj.getString("MER_Tel_Ing_PEM") + "\n";
                controlEntregaSer++;
            }

            if (jsonObj.has("MER_Identificacion_PEM")) {
                infoEntregaSer = infoEntregaSer + "Identificación PEM : " + jsonObj.getString("MER_Identificacion_PEM") + "\n";
                controlEntregaSer++;
            }

            if (jsonObj.has("MER_Detalle_Servicio")) {
                infoEntregaSer = infoEntregaSer + "Detalle Servicio : " + jsonObj.getString("MER_Detalle_Servicio") + "\n";
                controlEntregaSer++;
            }

            if (jsonObj.has("MER_Impacto")) {
                infoEntregaSer = infoEntregaSer + "Impacto : " + jsonObj.getString("MER_Impacto") + "\n";
                controlEntregaSer++;
            }

            if (jsonObj.has("MER_Nombre_ing_PEM")) {
                infoPuestaEnMarcha = "Nombre ing PEM : " + jsonObj.getString("MER_Nombre_ing_PEM") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Tel_Ing_PEM")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Tel Ing. PEM : " + jsonObj.getString("MER_Tel_Ing_PEM") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Identificacion_PEM")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Identificación PEM : " + jsonObj.getString("MER_Identificacion_PEM") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Aliado_Entrega_S")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Aliado Entrega de Servicios : " + jsonObj.getString("MER_Aliado_Entrega_S") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Cant_OTS_Asociadas")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Cantidad OTS : " + jsonObj.getString("MER_Cant_OTS_Asociadas") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_1")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 1: " + jsonObj.getString("MER_OT_Asociada_1") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_2")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 2: " + jsonObj.getString("MER_OT_Asociada_2") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_3")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 3: " + jsonObj.getString("MER_OT_Asociada_3") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_4")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 4: " + jsonObj.getString("MER_OT_Asociada_4") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_5")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 5: " + jsonObj.getString("MER_OT_Asociada_5") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_6")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 6: " + jsonObj.getString("MER_OT_Asociada_6") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_7")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 7: " + jsonObj.getString("MER_OT_Asociada_7") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_8")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 8: " + jsonObj.getString("MER_OT_Asociada_8") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_9")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 9: " + jsonObj.getString("MER_OT_Asociada_9") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_10")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 10: " + jsonObj.getString("MER_OT_Asociada_10") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_11")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 11: " + jsonObj.getString("MER_OT_Asociada_11") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_12")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 12: " + jsonObj.getString("MER_OT_Asociada_12") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_13")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 13: " + jsonObj.getString("MER_OT_Asociada_13") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_14")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 14: " + jsonObj.getString("MER_OT_Asociada_14") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_15")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 15: " + jsonObj.getString("MER_OT_Asociada_15") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_16")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 16: " + jsonObj.getString("MER_OT_Asociada_16") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_17")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 17: " + jsonObj.getString("MER_OT_Asociada_17") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_18")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 18: " + jsonObj.getString("MER_OT_Asociada_18") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_19")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 19: " + jsonObj.getString("MER_OT_Asociada_19") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_OT_Asociada_20")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "OT Asociada 20: " + jsonObj.getString("MER_OT_Asociada_20") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Notas_Efectividad")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Notas Efectividad: " + jsonObj.getString("MER_Notas_Efectividad") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Valida_desplaza_proxima_Vt")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Requiere desplazamiento próxima visita: " + jsonObj.getString("MER_Valida_desplaza_proxima_Vt") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_Notas_reporte_ES")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "Notas reporte: " + jsonObj.getString("MER_Notas_reporte_ES") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_SW_DE_ACCESO_OLT")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "SW DE ACCESO / OLT: " + jsonObj.getString("MER_SW_DE_ACCESO_OLT") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_PE")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "PE: " + jsonObj.getString("MER_PE") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_PUERTO")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "PUERTO: " + jsonObj.getString("MER_PUERTO") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_IP_WAN")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "IP WAN: " + jsonObj.getString("MER_IP_WAN") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_IP_PUBLICA")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "IP PUBLICA: " + jsonObj.getString("MER_IP_PUBLICA") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_CPE_INS_RET")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "CPE INSTALADO Y/O REEMPLAZADO: " + jsonObj.getString("MER_CPE_INS_RET") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_PRECABLEDO")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "PRECABLEDO: " + jsonObj.getString("MER_PRECABLEDO") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_BTS")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "BTS: " + jsonObj.getString("MER_BTS") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("MER_IP_GESTION_DE_MARCADOR")) {
                infoPuestaEnMarcha = infoPuestaEnMarcha + "IP GESTIÓN DEMARCADOR : " + jsonObj.getString("MER_IP_GESTION_DE_MARCADOR") + "\n";
                controlPuestaEnMarcha++;
            }

            if (jsonObj.has("N&E_SD_Serial Del Analizador Ber")) {
                infoEquiposMedElec = "Serial Del Analizador : " + jsonObj.getString("N&E_SD_Serial Del Analizador Ber") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("N&E_SD_Serial_Del_Multimetro")) {
                infoEquiposMedElec = infoEquiposMedElec + "Serial del Multimetro : " + jsonObj.getString("N&E_SD_Serial_Del_Multimetro") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CPESNUM")) {
                infoEquiposMedElec = infoEquiposMedElec + "Cantidad Equipos: " + jsonObj.getString("Elec_CPESNUM") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_Polaridad")) {
                infoEquiposMedElec = infoEquiposMedElec + "Polaridad Toma 1: " + jsonObj.getString("Elec_CJ1_Polaridad") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_Vf-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase Tierra: " + jsonObj.getString("Elec_CJ1_Vf-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_Vf-Vn")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase neutro: " + jsonObj.getString("Elec_CJ1_Vf-Vn") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_Vn-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Neutro-Tierra " + jsonObj.getString("Elec_CJ1_Vn-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_TomaPared")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de tomas de pared propias de la unidad : " + jsonObj.getString("Elec_CJ1_TomaPared") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ1_MultiToma")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de Multitomas que afectan equipos de Claro: " + jsonObj.getString("Elec_CJ1_MultiToma") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_Polaridad")) {
                infoEquiposMedElec = infoEquiposMedElec + "Polaridad Toma 2: " + jsonObj.getString("Elec_CJ2_Polaridad") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_Vf-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase Tierra: " + jsonObj.getString("Elec_CJ2_Vf-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_Vf-Vn")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase neutro: " + jsonObj.getString("Elec_CJ2_Vf-Vn") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_Vn-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Neutro-Tierra " + jsonObj.getString("Elec_CJ2_Vn-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_TomaPared")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de tomas de pared propias de la unidad : " + jsonObj.getString("Elec_CJ2_TomaPared") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ2_MultiToma")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de Multitomas que afectan equipos de Claro: " + jsonObj.getString("Elec_CJ2_MultiToma") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_Polaridad")) {
                infoEquiposMedElec = infoEquiposMedElec + "Polaridad Toma 1: " + jsonObj.getString("Elec_CJ3_Polaridad") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_Vf-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase Tierra: " + jsonObj.getString("Elec_CJ3_Vf-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_Vf-Vn")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase neutro: " + jsonObj.getString("Elec_CJ3_Vf-Vn") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_Vn-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Neutro-Tierra " + jsonObj.getString("Elec_CJ3_Vn-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_TomaPared")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de tomas de pared propias de la unidad : " + jsonObj.getString("Elec_CJ3_TomaPared") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ3_MultiToma")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de Multitomas que afectan equipos de Claro: " + jsonObj.getString("Elec_CJ3_MultiToma") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_Polaridad")) {
                infoEquiposMedElec = infoEquiposMedElec + "Polaridad Toma 1: " + jsonObj.getString("Elec_CJ4_Polaridad") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_Vf-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase Tierra: " + jsonObj.getString("Elec_CJ4_Vf-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_Vf-Vn")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase neutro: " + jsonObj.getString("Elec_CJ4_Vf-Vn") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_Vn-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Neutro-Tierra " + jsonObj.getString("Elec_CJ4_Vn-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_TomaPared")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de tomas de pared propias de la unidad : " + jsonObj.getString("Elec_CJ4_TomaPared") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ4_MultiToma")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de Multitomas que afectan equipos de Claro: " + jsonObj.getString("Elec_CJ4_MultiToma") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_Polaridad")) {
                infoEquiposMedElec = infoEquiposMedElec + "Polaridad Toma 1: " + jsonObj.getString("Elec_CJ5_Polaridad") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_Vf-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase Tierra: " + jsonObj.getString("Elec_CJ5_Vf-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_Vf-Vn")) {
                infoEquiposMedElec = infoEquiposMedElec + "Fase neutro: " + jsonObj.getString("Elec_CJ5_Vf-Vn") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_Vn-Vt")) {
                infoEquiposMedElec = infoEquiposMedElec + "Neutro-Tierra " + jsonObj.getString("Elec_CJ5_Vn-Vt") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_TomaPared")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de tomas de pared propias de la unidad : " + jsonObj.getString("Elec_CJ5_TomaPared") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("Elec_CJ5_MultiToma")) {
                infoEquiposMedElec = infoEquiposMedElec + "Estado de Multitomas que afectan equipos de Claro: " + jsonObj.getString("Elec_CJ5_MultiToma") + "\n";
                controlEquiposMedElec++;
            }

            if (jsonObj.has("SITE_TECNOLOGIA")) {
                infoSiteSurvey = "Tecnologia : " + jsonObj.getString("SITE_TECNOLOGIA") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_Posiciones")) {
                infoSiteSurvey = infoSiteSurvey + "Posiciones : " + jsonObj.getString("SITE_Posiciones") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_SDS")) {
                infoSiteSurvey = infoSiteSurvey + "SDS : " + jsonObj.getString("SITE_SDS") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_OLT")) {
                infoSiteSurvey = infoSiteSurvey + "OLT(si aplica): " + jsonObj.getString("SITE_OLT") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_Equipo_red_acceso")) {
                infoSiteSurvey = infoSiteSurvey + "Equipo red  acceso : " + jsonObj.getString("SITE_Equipo_red_acceso") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_Estado_de_Sitio")) {
                infoSiteSurvey = infoSiteSurvey + "Estado del Sitio : " + jsonObj.getString("SITE_Estado_de_Sitio") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_Requiere_EOC_compleja")) {
                infoSiteSurvey = infoSiteSurvey + "EOC compleja : " + jsonObj.getString("SITE_Requiere_EOC_compleja") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("SITE_Observaciones")) {
                infoSiteSurvey = infoSiteSurvey + "Observaciones : " + jsonObj.getString("SITE_Observaciones") + "\n";
                controlSiteSurvey++;
            }

            if (jsonObj.has("MER_VT_Valor_cliente")) {
                infoVisitaTecnica = "Valor cliente : " + jsonObj.getInt("MER_VT_Valor_cliente") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Valor_Claro")) {
                infoVisitaTecnica = infoVisitaTecnica + "Valor Claro  : " + jsonObj.getInt("MER_VT_Valor_Claro") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("XCA_ViolenciaAseguramiento")) {
                infoVisitaTecnica = infoVisitaTecnica + "Violencia al Aseguramiento  : " + jsonObj.getString("XCA_ViolenciaAseguramiento") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Proyeccion_desde")) {
                infoVisitaTecnica = infoVisitaTecnica + "Proyecciòn desde : " + jsonObj.getString("MER_VT_Proyeccion_desde") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Numero_de_Empalme_Caja_de_distr")) {
                infoVisitaTecnica = infoVisitaTecnica + "Numero de Empalme / Caja de distribución  : " + jsonObj.getString("MER_VT_Numero_de_Empalme_Caja_de_distr") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Nombre_y_cargo_de_quien_autoriza")) {
                infoVisitaTecnica = infoVisitaTecnica + "Nombre y cargo de quien autoriza proyección (personal directo) : " + jsonObj.getString("MER_VT_Nombre_y_cargo_de_quien_autoriza") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Acompanamiento")) {
                infoVisitaTecnica = infoVisitaTecnica + "Acompañamiento : " + jsonObj.getString("MER_VT_Acompanamiento") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Horario_para_realizar_actividad")) {
                infoVisitaTecnica = infoVisitaTecnica + "Horario para realizar actividad : " + jsonObj.getString("MER_VT_Horario_para_realizar_actividad") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Observaciones_del_proyectista")) {
                infoVisitaTecnica = infoVisitaTecnica + "Observaciones del proyectista : " + jsonObj.getString("MER_VT_Observaciones_del_proyectista") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Presupuesto_del_Cliente_supera_lo")) {
                infoVisitaTecnica = infoVisitaTecnica + "Presupuesto del Cliente supera los $500.000? : " + jsonObj.getString("MER_VT_Presupuesto_del_Cliente_supera_lo") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_VT_Costo_Excedente")) {
                infoVisitaTecnica = infoVisitaTecnica + "Costo Excedente : " + jsonObj.getInt("MER_VT_Costo_Excedente") + "\n";
                controlVisitaTecnica++;
            }

            if (jsonObj.has("MER_AC_Costo_de_Ejecucion_Obra_Civil")) {
                infoResultadoAco = "Costo de Ejecución Obra Civil : " + jsonObj.getInt("MER_AC_Costo_de_Ejecucion_Obra_Civil") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Total_Salida_F_O_de_Bodega")) {
                infoResultadoAco = infoResultadoAco + "Total Salida F.O. de Bodega : " + jsonObj.getInt("MER_AC_Total_Salida_F_O_de_Bodega") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Cantidad_de_tendido_aereo")) {
                infoResultadoAco = infoResultadoAco + "Cantidad de tendido aéreo : " + jsonObj.getInt("MER_AC_Cantidad_de_tendido_aereo") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Cantidad_de_tendido_Canalizado")) {
                infoResultadoAco = infoResultadoAco + "Cantidad de tendido Canalizado  : " + jsonObj.getInt("MER_AC_Cantidad_de_tendido_Canalizado") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Cantidad_de_tendido_interno")) {
                infoResultadoAco = infoResultadoAco + "Cantidad de tendido interno : " + jsonObj.getInt("MER_AC_Cantidad_de_tendido_interno") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Total_de_F_O_ instalada")) {
                infoResultadoAco = infoResultadoAco + "Total de F.O., instalada : " + jsonObj.getInt("MER_AC_Total_de_F_O_ instalada") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_Reintegro_de_F_O_a_Bodega")) {
                infoResultadoAco = infoResultadoAco + "Reintegro de F.O a Bodega : " + jsonObj.getInt("MER_AC_Reintegro_de_F_O_a_Bodega") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Marquilla_cable")) {
                infoResultadoAco = infoResultadoAco + "Marquilla cable : " + jsonObj.getString("MER_AC_DT_Marquilla_cable") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Abscisa_en_Cliente_Rack")) {
                infoResultadoAco = infoResultadoAco + "Abscisa en Cliente / Rack : " + jsonObj.getString("MER_AC_DT_Abscisa_en_Cliente_Rack") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Marquilla_de_Empalme")) {
                infoResultadoAco = infoResultadoAco + "Marquilla de Empalme  : " + jsonObj.getString("MER_AC_DT_Marquilla_de_Empalme") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Direccion_Empalme")) {
                infoResultadoAco = infoResultadoAco + "Dirección Empalme : " + jsonObj.getString("MER_AC_DT_Direccion_Empalme") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Tipo_de_FO_Instalada")) {
                infoResultadoAco = infoResultadoAco + "Tipo de FO Instalada  : " + jsonObj.getString("MER_AC_DT_Tipo_de_FO_Instalada") + "\n";
                controlResultadoAco++;
            }

            if (jsonObj.has("MER_AC_DT_Abscisa_en_Empalme")) {
                infoResultadoAco = infoResultadoAco + "Abscisa en Empalme : " + jsonObj.getString("MER_AC_DT_Abscisa_en_Empalme") + "\n";
                controlResultadoAco++;
            }

            //Capturo Observaciones Tecnico
            if (jsonObj.has("A_NotasFirma")) {
                notasFirma = "Notas del Tecnico: " + jsonObj.getString("A_NotasFirma") + "\n";
            }
            
             if (jsonObj.has("closure_notes")) {
                notasClosure = "Notas del Tecnico: " + jsonObj.getString("closure_notes") + "\n";
            }
            //Fin Observaciones Tecnico

            if (controlEntregaSer > 0) {
                notas = formEntregaSer + infoEntregaSer;
            }

            if (controlPuestaEnMarcha > 0) {
                notas = notas + formPuestaEnMarcha + infoPuestaEnMarcha;
            }

            if (controlEquiposMedElec > 0) {
                notas = notas + formEquiposMedElec + infoEquiposMedElec;
            }

            if (controlSiteSurvey > 0) {
                notas = notas + formSiteSurvey + infoSiteSurvey;
            }

            if (controlVisitaTecnica > 0) {
                notas = notas + formVisitaTecnica + infoVisitaTecnica;
            }

            if (controlResultadoAco > 0) {
                notas = notas + formResultadoAco + infoResultadoAco;
            }
            
            if (!notasClosure.isEmpty()) {
                notasTecnico = notasClosure;
            } else if (!notasFirma.isEmpty()) {
                notasTecnico = notasFirma;
            }
            
            retornaNotas.add(notasTecnico);
            retornaNotas.add(notas);

            return retornaNotas;

        } catch (JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio de tecnologías. EX000: " + ex.getMessage(), ex);
        }
    }

    public JSONObject consumirServicioHttpFirma() throws ApplicationException, IllegalArgumentException {

        try {
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod(metodoHTTP);
            httpCon.setAllowUserInteraction(false);
            httpCon.setDoInput(true);
            httpCon.setRequestProperty(ConstansClient.TYPE_HEADER, authorization);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            JSONObject jsonObj = null;
            if (httpCon.getResponseCode() != 200) {
                return jsonObj;
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (httpCon.getInputStream())));
            String output, jsonResult = "";
            while ((output = br.readLine()) != null) {
                jsonResult += output;
            }
            httpCon.disconnect();
            jsonObj = new JSONObject(jsonResult);
            return jsonObj;
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio. EX000: " + ex.getMessage(), ex);
        }

    }
    
     public GetActivityBookingResponses consumirServicioZonas() throws ApplicationException {

        try {
            GetActivityBookingResponses responsesZonas = new GetActivityBookingResponses();
            JSONObject jsonObj = consumirServicioHttp();

            int duration = jsonObj.getInt(ConstansClient.ATR_DURATION);
            responsesZonas.setDuration(duration);          
            String workZone = jsonObj.getString(ConstansClient.ATR_WORKZONE);
            responsesZonas.setWorkZone(workZone);

            return responsesZonas;
        } catch (JSONException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consumir el servicio. EX000: " + ex.getMessage(), ex);
        }
    }
    
    public static void conectionWsdlTest(URL url, String wsdlConstant) throws ApplicationException, IOException {

        InputStream inputStream;
        BufferedReader in;
        StringBuilder error;
        String currentLine;
        String description;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            
            ParametrosMgl tiempoTimeOut = parametrosMglManager.findParametroMgl(Constant.TIME_OUT_SERVICIOS_AGENDAMIENTO);  
            connection.setConnectTimeout(Integer.parseInt(tiempoTimeOut.getParValor()));
            connection.connect();

            switch (connection.getResponseCode()) {
                case HttpStatus.SC_OK:
                    break;
                case HttpStatus.SC_NOT_FOUND:
                    inputStream = connection.getErrorStream();
                    in = new BufferedReader(
                            new InputStreamReader(
                                    inputStream));
                    error = new StringBuilder();

                    while ((currentLine = in.readLine()) != null) {
                        error.append(currentLine);
                    }
                    in.close();
                    description = "404: para el servicio con anonimo: " + wsdlConstant;
                    throw new ApplicationException(error.toString() + " " + description);
                case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                    inputStream = connection.getErrorStream();
                    in = new BufferedReader(
                            new InputStreamReader(
                                    inputStream));
                    error = new StringBuilder();

                    while ((currentLine = in.readLine()) != null) {
                        error.append(currentLine);
                    }
                    in.close();
                    description = "500: para el servicio con anonimo: " + wsdlConstant;
                    throw new ApplicationException(error.toString() + ":" + description);
                default:
                    inputStream = connection.getErrorStream();
                    in = new BufferedReader(
                            new InputStreamReader(
                                    inputStream));
                    error = new StringBuilder();

                    while ((currentLine = in.readLine()) != null) {
                        error.append(currentLine);
                    }
                    in.close();
                    description = ": para el servicio con anonimo: " + wsdlConstant;
                    throw new ApplicationException(error.toString() + " " + description);
            }

        } catch (ApplicationException ex) {

            throw new ApplicationException(ex.toString());

        }
    }
}
