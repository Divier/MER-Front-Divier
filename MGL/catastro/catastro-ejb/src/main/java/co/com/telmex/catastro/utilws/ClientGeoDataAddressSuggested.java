package co.com.telmex.catastro.utilws;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressSuggested;
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
 * Clase ClientGeoDataAddressSuggested
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0
 */
public class ClientGeoDataAddressSuggested {
    
    private static final Logger LOGGER = LogManager.getLogger(ClientGeoDataAddressSuggested.class);

    /**
     *
     */
    public ClientGeoDataAddressSuggested() {
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
    public SOAPMessage buildRequestGeoDataSuggested(String adrress, String state, String city, String neighborhood, String user, String pwd) throws ApplicationException {
        try {
            SOAPMessage message = null;
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            MessageFactory factory = MessageFactory.newInstance();

            message = factory.createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            envelope.addNamespaceDeclaration(ConstantWS.SUGGESTED_ONY_LABEL, ConstantWS.SUGGESTED_XMLNS);
            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            Name bodySipCentralizadoRequest = soapFactory.createName(ConstantWS.SUGGESTED_TAG_METHOD, ConstantWS.SUGGESTED_ONY_LABEL, null);
            SOAPBodyElement bodyElement = body.addBodyElement(bodySipCentralizadoRequest);

            addElement(bodyElement, ConstantWS.SUGGESTED_TAG_ADDRESS, adrress);
            addElement(bodyElement, ConstantWS.SUGGESTED_TAG_STATE, state);
            addElement(bodyElement, ConstantWS.SUGGESTED_TAG_CITY, city);
            addElement(bodyElement, ConstantWS.SUGGESTED_TAG_NEIGHBORHOOD, neighborhood);
            addElement(bodyElement, ConstantWS.SUGGESTED_SUFFIX_1, user);
            addElement(bodyElement, ConstantWS.SUGGESTED_SUFFIX_2, pwd);
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
        NodeList isbnNodes = response.getSOAPBody().getElementsByTagName(ConstantWS.SUGGESTED_TAG_ITEM);

        int size = isbnNodes.getLength();
        for (int i = 0; i < size; i++) {
            value = isbnNodes.item(i).getTextContent();
            list.add(value);
        }
        return list;
    }

    /**
     *
     * @param list
     * @return
     */
    public List<AddressSuggested> createListSuggested(List<String> list) {
        List<AddressSuggested> listAddress = new ArrayList<AddressSuggested>();
        String address = null;
        String neighborhood = null;
        int exist = 0;
        int exist1 = 0;
        int exist2 = 0;
        int length = 0;
        int length_suffix = ConstantWS.SUGGESTED_SUFFIX_1.length();

        for (String address_neighborhood : list) {
            length = address_neighborhood.length();
            if (address_neighborhood != null && length > 1) {
                exist1 = address_neighborhood.indexOf(ConstantWS.SUGGESTED_SUFFIX_1);
                exist2 = address_neighborhood.indexOf(ConstantWS.SUGGESTED_SUFFIX_2);
                if (exist1 > 0) {
                    exist = exist1;
                } else if (exist2 > 0) {
                    exist = exist2;
                }
                if (exist > 0) {
                    address = address_neighborhood.substring(0, exist);
                    neighborhood = address_neighborhood.substring(exist + length_suffix, length);
                    AddressSuggested addressSuggested = new AddressSuggested();
                    addressSuggested.setAddress(address);
                    addressSuggested.setNeighborhood(neighborhood);
                    listAddress.add(addressSuggested);
                }
            }
        }
        return listAddress;
    }
}
