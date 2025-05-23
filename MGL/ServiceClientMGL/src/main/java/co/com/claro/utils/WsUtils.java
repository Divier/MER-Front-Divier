package co.com.claro.utils;

import co.com.claro.mgl.error.ApplicationException;

import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utilidad para acceder la URL del cliente que consumira ODI
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class WsUtils {
    public static URL getUrl(String stringUrl)  {
        URL url;
        WebServiceException e = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException ex) {
            throw new WebServiceException(ex);
        }
        return url;
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
