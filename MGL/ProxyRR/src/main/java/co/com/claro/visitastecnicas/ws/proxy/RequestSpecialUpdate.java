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
public class RequestSpecialUpdate implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(RequestSpecialUpdate.class);

    protected String apartmentNumber;
    protected String block;
    protected String building;
    protected String caby;
    protected String comunity;
    protected String division;
    protected String entrance;
    protected String homeNumber;
    protected String homeNumber2;
    protected String homeSuffix;
    protected String lineNumber;
    protected String lines;
    protected String neighborhood;
    protected String notesExpiryDate;
    protected String sector;
    protected String streetName;
    protected String typeRequest;

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

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getHomeNumber2() {
        return homeNumber2;
    }

    public void setHomeNumber2(String homeNumber2) {
        this.homeNumber2 = homeNumber2;
    }

    public String getHomeSuffix() {
        return homeSuffix;
    }

    public void setHomeSuffix(String homeSuffix) {
        this.homeSuffix = homeSuffix;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNotesExpiryDate() {
        return notesExpiryDate;
    }

    public void setNotesExpiryDate(String notesExpiryDate) {
        this.notesExpiryDate = notesExpiryDate;
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

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
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
            SOAPElement soapBodyElem = soapBody.addChildElement("SpecialUpdateManager", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestSpecialUpdate", "");
            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("apartmentNumber", "");
            soapBodyElem1.addTextNode(apartmentNumber);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("block", "");
            soapBodyElem2.addTextNode(block);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("building", "");
            soapBodyElem3.addTextNode(building);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("caby", "");
            soapBodyElem4.addTextNode(caby);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("comunity", "");
            soapBodyElem5.addTextNode(comunity);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem6.addTextNode(division);
            SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("entrance", "");
            soapBodyElem7.addTextNode(entrance);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("homeNumber", "");
            soapBodyElem8.addTextNode(homeNumber);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("homeNumber2", "");
            soapBodyElem9.addTextNode(homeNumber2);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("homeSuffix", "");
            soapBodyElem10.addTextNode(homeSuffix);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("lineNumber", "");
            soapBodyElem11.addTextNode(lineNumber);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("lines", "");
            soapBodyElem12.addTextNode(lines);
            SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("neighborhood", "");
            soapBodyElem13.addTextNode(neighborhood);
            SOAPElement soapBodyElem14 = soapBodyElemRequest.addChildElement("notesExpiryDate", "");
            soapBodyElem14.addTextNode(notesExpiryDate);
            SOAPElement soapBodyElem15 = soapBodyElemRequest.addChildElement("sector", "");
            soapBodyElem15.addTextNode(sector);
            SOAPElement soapBodyElem16 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem16.addTextNode(streetName);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "SpecialUpdateManager");
            soapMessage.saveChanges();
            System.out.print("Request SOAP Message = ");
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
