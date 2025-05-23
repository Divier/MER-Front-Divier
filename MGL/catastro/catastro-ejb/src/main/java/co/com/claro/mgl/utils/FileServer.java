package co.com.claro.mgl.utils;

import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Utilitarios relacionados con el File Server.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class FileServer {
    
    private static final Logger LOGGER = LogManager.getLogger(FileServer.class);
    
    private FileServer () {}

    /**
     * Obtiene un Hash a partir de la cadena suministrada.
     * @param s Cadena.
     * @return Hash.
     */
    private static String createaHash(String s) {
        try {
            // Create MD5 
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String fecha = format.format(date);
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            s += fecha;
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(String.format("%02X", 0xFF & messageDigest[i]));
            }

            return (hexString.toString()).toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(FileServer.class)+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return "";
    }
    public static Boolean uploadFileMultiServer(File uploadedFile,
            String fileName)
            throws Exception {
        ResourceEJB resourceEJB = new ResourceEJB();
        Parametros param, param2, param3;
        String ruta = "";
        String address;
        String server = "";
        String hash = "";
    
            param = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.URL_STORE_FILE);
            param2 = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.MULTIPASS_SERVER);
            param3 = resourceEJB.queryParametros(co.com.telmex.catastro.services.util.Constant.MULTISERVER_URL);
            
            if (param != null && param.getValor() != null) {
                ruta = param.getValor();
            }
            if (param2 != null && param2.getValor() != null) {
                hash = createaHash(param2.getValor());
            }
            if (param3 != null && param3.getValor() != null) {
                server = param3.getValor();
            }
            address =  server;
            
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(address);
        ContentBody cbFile = new FileBody(uploadedFile);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("nombreArchivo", cbFile);
        builder.addTextBody("validacion", hash);
        builder.addTextBody("localPath", ruta);
        builder.addTextBody("nombreArchivo", fileName);
        httppost.setEntity(builder.build());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        resEntity.consumeContent();
        
        httpclient.getConnectionManager().shutdown();
        return ((response.getStatusLine().toString()).equalsIgnoreCase("HTTP/1.1 200 OK"));
    }
}
