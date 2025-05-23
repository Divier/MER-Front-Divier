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
import java.io.Serializable;
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
public class ChangeUnitAddressRequest implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ChangeUnitAddressRequest.class);

    protected String apartment;
    protected String community;
    protected String division;
    protected String houseNumber;
    protected String newApartment;
    protected String newCommunity;
    protected String newDivision;
    protected String newHouseNumber;
    protected String newPostalCode;
    protected String newStreetName;
    protected String streetName;
    protected String userId;

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNewApartment() {
        return newApartment;
    }

    public void setNewApartment(String newApartment) {
        this.newApartment = newApartment;
    }

    public String getNewCommunity() {
        return newCommunity;
    }

    public void setNewCommunity(String newCommunity) {
        this.newCommunity = newCommunity;
    }

    public String getNewDivision() {
        return newDivision;
    }

    public void setNewDivision(String newDivision) {
        this.newDivision = newDivision;
    }

    public String getNewHouseNumber() {
        return newHouseNumber;
    }

    public void setNewHouseNumber(String newHouseNumber) {
        this.newHouseNumber = newHouseNumber;
    }

    public String getNewPostalCode() {
        return newPostalCode;
    }

    public void setNewPostalCode(String newPostalCode) {
        this.newPostalCode = newPostalCode;
    }

    public String getNewStreetName() {
        return newStreetName;
    }

    public void setNewStreetName(String newStreetName) {
        this.newStreetName = newStreetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    protected SOAPMessage createSOAPRequest(String url) throws ApplicationException {
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
            SOAPElement soapBodyElem = soapBody.addChildElement("changeUnitAddress", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("request", "");
            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("apartment", "");
            soapBodyElem1.addTextNode(apartment);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("community", "");
            soapBodyElem2.addTextNode(community);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem3.addTextNode(division);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("houseNumber", "");
            soapBodyElem4.addTextNode(houseNumber);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("newApartment", "");
            soapBodyElem5.addTextNode(newApartment);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("newCommunity", "");
            soapBodyElem6.addTextNode(newCommunity);
            SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("newDivision", "");
            soapBodyElem7.addTextNode(newDivision);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("newHouseNumber", "");
            soapBodyElem8.addTextNode(newHouseNumber);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("newPostalCode", "");
            soapBodyElem9.addTextNode(newPostalCode);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("newStreetName", "");
            soapBodyElem10.addTextNode(newStreetName);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem11.addTextNode(streetName);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("userId", "");
            soapBodyElem12.addTextNode(userId);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "changeUnitAddress");
            soapMessage.saveChanges();
            System.out.print("Request SOAP Message = ");
            soapMessage.writeTo(System.out);
            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: ");
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }
    }

}
