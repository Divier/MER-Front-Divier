/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.shareddata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author imartipe
 */
public class MgwProssUser {
    
    private static final Logger LOGGER = LogManager.getLogger(MgwProssUser.class);

    /**
     *
     */
    public static void setPropWs() {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty(
                "com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe."
                + "dump",
                "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }

    /**
     *
     * @param valor
     * @param tamano
     * @param caracter
     * @return String
     */
    public static String lpad(String valor, int tamano, String caracter) {
        String retorno = valor;

        while (retorno.length() < tamano) {
            retorno = caracter + retorno;
        }

        return retorno;
    }

    /**
     *
     * @return String
     */
    public static String newNowDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        df.setTimeZone(TimeZone.getTimeZone("GMT-5"));

        return df.format(new Date()).replaceAll("GMT", "");
    }

    /**
     *
     * @param pass
     * @param now
     * @return String
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String newPass(String pass, String now)
            throws NoSuchAlgorithmException {
        String resultado;

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        String primeraEncripcion =
                new BigInteger(1, md.digest()).toString(16);
        primeraEncripcion = lpad(primeraEncripcion, 32, "0");
        md.update((now + primeraEncripcion).getBytes());
        resultado = new BigInteger(1, md.digest()).toString(16);
        resultado = lpad(resultado, 32, "0");

        return resultado;
    }

    /**
     *
     * Esta data debe ser traida de base de datos
     *
     * @return MgwUserElement
     * @throws java.security.NoSuchAlgorithmException
     */
    public static MgwUserElement newTestUserLogin()
            throws NoSuchAlgorithmException {
        String loginUser = "soap";
        String company = "amx-co.test";
        String now = newNowDate();
        String pass = "Soap.2013";

        return new MgwUserElement(now, loginUser, company, newPass(pass, now));
    }

    public static MgwUserElement newTestUserLogin(String loginUser, String company, String pass)
            throws NoSuchAlgorithmException {
        String now = newNowDate();

        return new MgwUserElement(now, loginUser, company, newPass(pass, now));
    }

    /**
     *
     * @param date
     * @return XMLGregorianCalendar
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public static XMLGregorianCalendar newXmlGC(String date) throws
            DatatypeConfigurationException {
        String d[] = date.split("-");
        GregorianCalendar gc = new GregorianCalendar();

        XMLGregorianCalendar xc = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(gc);

        xc.setYear(Integer.parseInt(d[0]));
        xc.setMonth(Integer.parseInt(d[1]));
        xc.setDay(Integer.parseInt(d[2]));

        xc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        xc.setTime(
                DatatypeConstants.FIELD_UNDEFINED,
                DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);

        return xc;
    }

    /**
     *
     * @param user
     */
    public static void showUserElement(MgwUserElement user) {
        LOGGER.error(
                String.format(
                "authString{%s}\ncompany{%s}\nlogin{%s}\nnow{%s}\n",
                user.getAuth_string(), user.getCompany(), user.getLogin(),
                user.getNow()));
    }

    public static void showTab(Object value) {
        LOGGER.error(String.format("\t%s", value));
    }

    public static void showTab(Object value, boolean end) {
        if (end) {
            LOGGER.error(String.format("\t%s\n", value));
        } else {
            LOGGER.error(String.format("\t%s", value));
        }
    }

}
