/*
 * To change this template, choose Tools | Templates
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
 * @author user
 */
public class RequestStreetGrid implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(RequestStreetGrid.class);

    protected String caby;
    protected String community;
    protected String division;
    protected String grpi;
    protected String streetName;

    public String getCaby() {
        return caby;
    }

    public void setCaby(String caby) {
        this.caby = caby;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getGrpi() {
        return grpi;
    }

    public void setGrpi(String grpi) {
        this.grpi = grpi;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public SOAPMessage createSOAPRequest(String url) throws IOException, SOAPException {
        System.setProperty(Constants.PROPERTY_SOAPFACTORY, Constants.VALUE_SOAPFACTORY);
        System.setProperty(Constants.PROPERTY_MESSAGEFACTORY, Constants.VALUE_MESSAGEFACTORY);
        System.setProperty(Constants.PROPERTY_SOAPCONNECTIONFACTORY, Constants.VALUE_SOAPCONNECTIONFACTORY);

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://unit.telmex.net/";
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("unit", serverURI);
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("updateStreetGrid", "unit");
        SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestStreetGrid", "");
        SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("caby", "");
        soapBodyElem1.addTextNode(caby);
        SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("community", "");
        soapBodyElem2.addTextNode(community);
        SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("division", "");
        soapBodyElem3.addTextNode(division);
        SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("grpi", "");
        soapBodyElem4.addTextNode(grpi);
        SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("streetName", "");
        soapBodyElem5.addTextNode(streetName);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", url + "updateStreetGrid");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);

        return soapMessage;

    }

    public SOAPMessage createSOAPRequestCreateGrid(String url) throws ApplicationException {
        try {
            System.setProperty(Constants.PROPERTY_SOAPFACTORY, Constants.VALUE_SOAPFACTORY);
            System.setProperty(Constants.PROPERTY_MESSAGEFACTORY, Constants.VALUE_MESSAGEFACTORY);
            System.setProperty(Constants.PROPERTY_SOAPCONNECTIONFACTORY, Constants.VALUE_SOAPCONNECTIONFACTORY);
            LOGGER.error("createSOAPRequest queryStreetGrid 1");

            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String serverURI = "http://unit.telmex.net/";

            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("unit", serverURI);
            SOAPBody soapBody = envelope.getBody();

            SOAPElement soapBodyElem = soapBody.addChildElement("queryStreetGrid", "unit");

            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestStreetGrid", "");

            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("caby", "");
            soapBodyElem1.addTextNode(caby);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("community", "");
            soapBodyElem2.addTextNode(community);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem3.addTextNode(division);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("grpi", "");
            soapBodyElem4.addTextNode(grpi);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem5.addTextNode(streetName);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "queryStreetGrid");
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
         LOGGER.error("Not supported yet. ");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
