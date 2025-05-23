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
public class RequestQueryRegularUnit implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(RequestQueryRegularUnit.class);

    protected String apartmentNumber;
    protected String block;
    protected String building;
    protected String caby;
    protected String community;
    protected String division;
    protected String entrance;
    protected String houseNumber;
    protected String houseSuffix;
    protected String houseTwo;
    protected String neighborhood;
    protected String sector;
    protected String streetName;
    protected String unitNumber;

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

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

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseSuffix() {
        return houseSuffix;
    }

    public void setHouseSuffix(String houseSuffix) {
        this.houseSuffix = houseSuffix;
    }

    public String getHouseTwo() {
        return houseTwo;
    }

    public void setHouseTwo(String houseTwo) {
        this.houseTwo = houseTwo;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
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
            SOAPElement soapBodyElem = soapBody.addChildElement("QueryRegularUnit", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestQueryRegularUnit", "");
            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("apartmentNumber", "");
            soapBodyElem1.addTextNode(apartmentNumber);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("block", "");
            soapBodyElem2.addTextNode(block);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("building", "");
            soapBodyElem3.addTextNode(building);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("caby", "");
            soapBodyElem4.addTextNode(caby);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("community", "");
            soapBodyElem5.addTextNode(community);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem6.addTextNode(division);
            SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("entrance", "");
            soapBodyElem7.addTextNode(entrance);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("houseNumber", "");
            soapBodyElem8.addTextNode(houseNumber);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("houseSuffix", "");
            soapBodyElem9.addTextNode(houseSuffix);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("houseTwo", "");
            soapBodyElem10.addTextNode(houseTwo);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("neighborhood", "");
            soapBodyElem11.addTextNode(neighborhood);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("sector", "");
            soapBodyElem12.addTextNode(sector);
            SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem13.addTextNode(streetName);
            SOAPElement soapBodyElem14 = soapBodyElemRequest.addChildElement("unitNumber", "");
            soapBodyElem14.addTextNode(unitNumber);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "QueryRegularUnit");
            soapMessage.saveChanges();
            System.out.print("RequestQueryRegularUnit SOAP Message = ");
            soapMessage.writeTo(System.out);

            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: " + ex.getMessage());
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }

    }

    @Override
    public SOAPMessage createSOAPRequestHhPp(String url) throws ApplicationException {
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
            SOAPElement soapBodyElem = soapBody.addChildElement("QueryRegularUnit", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestQueryRegularUnit", "");

            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("apartmentNumber", "");
            soapBodyElem1.addTextNode(apartmentNumber);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("community", "");
            soapBodyElem5.addTextNode(community);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem6.addTextNode(division);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("houseNumber", "");
            soapBodyElem8.addTextNode(houseNumber);
            SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem13.addTextNode(streetName);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "QueryRegularUnit");
            soapMessage.saveChanges();
            System.out.print("RequestQueryRegularUnit SOAP Message = ");
            soapMessage.writeTo(System.out);

            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request de HHPP. EX000: " + ex.getMessage());
            throw new ApplicationException("Error al momento de crear el SOAP request de HHPP. EX000: " + ex.getMessage(), ex);
        }
    }

}
