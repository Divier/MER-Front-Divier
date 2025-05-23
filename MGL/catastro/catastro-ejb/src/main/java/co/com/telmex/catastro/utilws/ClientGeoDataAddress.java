package co.com.telmex.catastro.utilws;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Clase ClientGeoDataAddress
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0
 */
public class ClientGeoDataAddress {
    
    private static final Logger LOGGER = LogManager.getLogger(ClientGeoDataAddress.class);

    /**
     *
     */
    public ClientGeoDataAddress() {
        System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl");
        System.setProperty("javax.xml.soap.SOAPConnectionFactory", "weblogic.wsee.saaj.SOAPConnectionFactoryImpl");
    }

    /**
     *
     * @param xmldoc
     * @param admin
     * @param pwd
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SOAPMessage buildRequestGeoData(String xmldoc, String admin, String pwd) throws ApplicationException {
        try {
            SOAPMessage message = null;
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            MessageFactory factory = MessageFactory.newInstance();

            message = factory.createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            envelope.addNamespaceDeclaration(ConstantWS.ADR_ONY_LABEL, ConstantWS.ADR_XMLNS);
            SOAPHeader header = message.getSOAPHeader();
            SOAPBody body = message.getSOAPBody();
            Name bodySipCentralizadoRequest = soapFactory.createName(ConstantWS.ADR_TAG_LOTES, ConstantWS.ADR_ONY_LABEL, null);
            SOAPBodyElement bodyElement = body.addBodyElement(bodySipCentralizadoRequest);

            addElement(bodyElement, ConstantWS.ADR_TAG_XMLENVIO, xmldoc);
            addElement(bodyElement, ConstantWS.ADR_TAG_USUARIO, admin);
            addElement(bodyElement, ConstantWS.ADR_TAG_CLAVE, pwd);
            return message;
        } catch (SOAPException ex) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            throw new ApplicationException("Error al momento de construir el request del GeoData. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param parent
     * @param name
     * @param value
     * @return
     * @throws SOAPException
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
    public String readMessage(SOAPMessage response) throws SOAPException, XPathExpressionException {
        String result = "";
        NodeList isbnNodes = response.getSOAPBody().getElementsByTagName(ConstantWS.ADR_TAG_RETURN);
        Node node = isbnNodes.item(0);
        if (node != null) {
            result = node.getTextContent();
        }
          
        return result;
    }
}
