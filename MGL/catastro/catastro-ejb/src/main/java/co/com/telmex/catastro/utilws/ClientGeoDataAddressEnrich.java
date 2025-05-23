package co.com.telmex.catastro.utilws;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressGeodata;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPathExpressionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 * Clase ClientGeoDataAddressEnrich
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0
 */
public class ClientGeoDataAddressEnrich {

    private static final Logger LOGGER = LogManager.getLogger(ClientGeoDataAddressEnrich.class);
    
    /**
     *
     */
    public ClientGeoDataAddressEnrich() {
        System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
        System.setProperty("javax.xml.soap.SOAPConnectionFactory", "weblogic.wsee.saaj.SOAPConnectionFactoryImpl");
    }

    /**
     *
     * @param adrress
     * @param state
     * @param city
     * @param neighborhood
     * @param user
     * @param pwd
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SOAPMessage buildRequestGeoDataEnrich(String adrress, String state, String city, String neighborhood, String user, String pwd) throws ApplicationException {

        try {
            SOAPMessage message = null;
            SOAPFactory soapFactory = SOAPFactory.newInstance(javax.xml.soap.SOAPConstants.SOAP_1_1_PROTOCOL);
            MessageFactory factory = MessageFactory.newInstance(javax.xml.soap.SOAPConstants.SOAP_1_1_PROTOCOL);

            message = factory.createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            envelope.addNamespaceDeclaration(ConstantWS.ENRICH_ONY_LABEL, ConstantWS.ENRICH_XMLNS);
            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            Name bodySipCentralizadoRequest = soapFactory.createName(ConstantWS.ENRICH_TAG_METHOD, ConstantWS.ENRICH_ONY_LABEL, null);
            SOAPBodyElement bodyElement = body.addBodyElement(bodySipCentralizadoRequest);

            addElement(bodyElement, ConstantWS.ENRICH_TAG_ADDRESS, adrress);
            addElement(bodyElement, ConstantWS.ENRICH_TAG_STATE, state);
            addElement(bodyElement, ConstantWS.ENRICH_TAG_CITY, city);
            addElement(bodyElement, ConstantWS.ENRICH_TAG_NEIGHBORHOOD, neighborhood);
            addElement(bodyElement, ConstantWS.ENRICH_USUARIO, user);
            addElement(bodyElement, ConstantWS.ENRICH_CLAVE, pwd);
            return message;
        } catch (SOAPException ex) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            throw new ApplicationException("Error al momento de construir el request del GeoData. EX000: " + ex.getMessage(), ex);
        }
    }

    /*
     * metodo armar mensaje soap
     */
    private static SOAPElement addElement(SOAPElement parent, String name, String value) throws SOAPException {
        QName element = new QName(name);
        SOAPElement soapelement = parent.addChildElement(element);
        soapelement.addTextNode(value);
        return soapelement;
    }

    /**
     *
     * @param msg
     * @return
     * @throws SOAPException
     * @throws MalformedURLException
     */
    public SOAPMessage sendRequest(SOAPMessage msg) throws SOAPException, MalformedURLException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnectionFactory.createConnection();
        URL endpointUrl = new URL(ConstantWS.ENDPOINT);
        SOAPMessage response = connection.call(msg, endpointUrl);
        connection.close();
        return response;
    }

    /**
     *
     * @param response
     * @return
     * @throws SOAPException
     * @throws XPathExpressionException
     */
    public List<String> readMessage(SOAPMessage response) throws SOAPException, XPathExpressionException {
        List<String> list = new ArrayList<String>();
        String value = null;
        NodeList isbnNodes = response.getSOAPBody().getElementsByTagName(ConstantWS.ENRICH_TAG_ITEM);

        int size = isbnNodes.getLength();
        for (int i = 0; i < size; i++) {
            value = isbnNodes.item(i).getTextContent();
            list.add(value);
        }
        return list;
    }

    /**
     *
     * @param parametros
     * @return
     */
    public AddressGeodata createAddressGeoFromList(List<String> parametros) {
        AddressGeodata addressGeodata = new AddressGeodata();

        if (parametros != null && parametros.size() > 0) {
            addressGeodata.setDirtrad(parametros.get(0));
            addressGeodata.setBartrad(parametros.get(1));
            addressGeodata.setCoddir(parametros.get(2));
            addressGeodata.setCodencont(parametros.get(3));
            addressGeodata.setFuente(parametros.get(4));
            addressGeodata.setDiralterna(parametros.get(5));
            addressGeodata.setAmbigua(parametros.get(6));
            addressGeodata.setValagreg(parametros.get(7));
            addressGeodata.setValplaca(parametros.get(8));
            addressGeodata.setManzana(parametros.get(9));
            addressGeodata.setBarrio(parametros.get(10));
            addressGeodata.setLocalidad(parametros.get(11));
            addressGeodata.setNivsocio(parametros.get(12));
            addressGeodata.setCx(parametros.get(13));
            addressGeodata.setCy(parametros.get(14));
            addressGeodata.setEstrato(parametros.get(15));
            addressGeodata.setActeconomica(parametros.get(16));
            addressGeodata.setNodo1(parametros.get(17));
            addressGeodata.setNodo2(parametros.get(18));
            addressGeodata.setNodo3(parametros.get(19));
            if (parametros.get(20).length() == 8) {
                addressGeodata.setCoddanedpto(parametros.get(20).substring(0, 2));
                addressGeodata.setCoddanemcpio(parametros.get(20).substring(2));
            }
            addressGeodata.setEstado(parametros.get(21));
        }
        return addressGeodata;
    }
}
