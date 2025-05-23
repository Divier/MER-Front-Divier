/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 *
 * @author aleal
 */
public class OtOnyxRequest implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(OtOnyxRequest.class);

    private String incidentId = "";
    private String mode = "";

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public SOAPMessage createSOAPRequest(String url) throws ApplicationException {
        try {
            System.setProperty(Constants.PROPERTY_SOAPFACTORY, Constants.VALUE_SOAPFACTORY);
            System.setProperty(Constants.PROPERTY_MESSAGEFACTORY, Constants.VALUE_MESSAGEFACTORY);
            System.setProperty(Constants.PROPERTY_SOAPCONNECTIONFACTORY, Constants.VALUE_SOAPCONNECTIONFACTORY);

            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String serverURI = "http://onyx.integration.telmex.net/";

            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("onyx", serverURI);
            SOAPBody soapBody = envelope.getBody();

            SOAPElement soapBodyElem = soapBody.addChildElement("getIncidentInfo", "onyx");
            SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("incidentId", "");
            soapBodyElem1.addTextNode(incidentId);
            SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("mode", "");
            soapBodyElem2.addTextNode(mode);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "getIncidentInfo");
            soapMessage.saveChanges();
            soapMessage.writeTo(System.out);
            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: ");
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }
    }

    @Override
    public SOAPMessage createSOAPRequestHhPp(String url) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
