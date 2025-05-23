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
 * @author bocanegravm
 */
public class OtHijaOnixRequest implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(OtHijaOnixRequest.class);

    private String idOtHija = "";

    public String getIdOtHija() {
        return idOtHija;
    }

    public void setIdOtHija(String idOtHija) {
        this.idOtHija = idOtHija;
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

            String serverURI = "http://www.claro.com.co/Onyx/wsConsultaMGLOnyx";

            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("wsc", serverURI);
            SOAPBody soapBody = envelope.getBody();

            SOAPElement soapBodyElem = soapBody.addChildElement("consultaMglOnyx_Request", "wsc");
            SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("Id_OtHija", "");
            soapBodyElem1.addTextNode(idOtHija);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "consultaMglOnyx_Request");
            soapMessage.saveChanges();
            soapMessage.writeTo(System.out);
            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: ");
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }
    }

    @Override
    public SOAPMessage createSOAPRequestHhPp(String url) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
