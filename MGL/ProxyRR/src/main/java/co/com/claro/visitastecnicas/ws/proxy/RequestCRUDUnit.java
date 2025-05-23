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
 * @author carlos.villa.ext
 */
public class RequestCRUDUnit implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(RequestCRUDUnit.class);

    protected String caby;
    protected String comunity;
    protected String division;
    protected String mode;
    protected String restriction;
    protected String sipo;
    protected String streetDirection;
    protected String streetName;
    protected String streetTittle;
    protected String streetType;
    protected String unitHigh;
    protected String unitLow;

    public String getCaby() {
        return caby;
    }

    public void setCaby(String caby) {
        this.caby = caby;
    }

    public String getComunity() {
        return comunity;
    }

    public void setComunity(String comunity) {
        this.comunity = comunity;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public String getSipo() {
        return sipo;
    }

    public void setSipo(String sipo) {
        this.sipo = sipo;
    }

    public String getStreetDirection() {
        return streetDirection;
    }

    public void setStreetDirection(String streetDirection) {
        this.streetDirection = streetDirection;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetTittle() {
        return streetTittle;
    }

    public void setStreetTittle(String streetTittle) {
        this.streetTittle = streetTittle;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getUnitHigh() {
        return unitHigh;
    }

    public void setUnitHigh(String unitHigh) {
        this.unitHigh = unitHigh;
    }

    public String getUnitLow() {
        return unitLow;
    }

    public void setUnitLow(String unitLow) {
        this.unitLow = unitLow;
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

            String serverURI = "http://unit.telmex.net/";
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("unit", serverURI);
            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement("CRUDUnitManager", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestCRUDUnit", "");
            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("caby", "");
            soapBodyElem1.addTextNode(caby);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("comunity", "");
            soapBodyElem2.addTextNode(comunity);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem3.addTextNode(division);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("mode", "");
            soapBodyElem4.addTextNode(mode);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("restriction", "");
            soapBodyElem5.addTextNode(restriction);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("sipo", "");
            soapBodyElem6.addTextNode(sipo);
            SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("streetDirection", "");
            soapBodyElem7.addTextNode(streetDirection);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem8.addTextNode(streetName);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("streetTittle", "");
            soapBodyElem9.addTextNode(streetTittle);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("streetType", "");
            soapBodyElem10.addTextNode(streetType);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("unitHigh", "");
            soapBodyElem11.addTextNode(unitHigh);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("unitLow", "");
            soapBodyElem12.addTextNode(unitLow);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "CRUDUnitManager");
            soapMessage.saveChanges();
            soapMessage.writeTo(System.out);

            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: " + ex.getMessage());
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }

    }

    @Override
    public SOAPMessage createSOAPRequestHhPp(String url) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
