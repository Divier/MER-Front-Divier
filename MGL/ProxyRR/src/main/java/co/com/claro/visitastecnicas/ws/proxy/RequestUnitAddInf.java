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
public class RequestUnitAddInf implements IRequest {

    private static final Logger LOGGER = LogManager.getLogger(RequestUnitAddInf.class);

    protected String apartmentNumber;
    protected String block;
    protected String building;
    protected String caby;
    protected String comunity;
    protected String division;
    protected String entrance;
    protected String field1;
    protected String field2;
    protected String field3;
    protected String field4;
    protected String homeNumber;
    protected String homeNumber2;
    protected String homeSuffix;
    protected String latitude;
    protected String longitude;
    protected String neighborhood;
    protected String placeCode;
    protected String sector;
    protected String streetName;
    protected String table01;
    protected String table02;
    protected String table03;
    protected String table04;
    protected String table05;
    protected String table06;
    protected String table07;
    protected String table08;
    protected String table09;
    protected String table10;
    protected String value01;
    protected String value02;
    protected String value03;
    protected String value04;
    protected String value05;
    protected String value06;
    protected String value07;
    protected String value08;
    protected String value09;
    protected String value10;

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

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
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

    public String getTable01() {
        return table01;
    }

    public void setTable01(String table01) {
        this.table01 = table01;
    }

    public String getTable02() {
        return table02;
    }

    public void setTable02(String table02) {
        this.table02 = table02;
    }

    public String getTable03() {
        return table03;
    }

    public void setTable03(String table03) {
        this.table03 = table03;
    }

    public String getTable04() {
        return table04;
    }

    public void setTable04(String table04) {
        this.table04 = table04;
    }

    public String getTable05() {
        return table05;
    }

    public void setTable05(String table05) {
        this.table05 = table05;
    }

    public String getTable06() {
        return table06;
    }

    public void setTable06(String table06) {
        this.table06 = table06;
    }

    public String getTable07() {
        return table07;
    }

    public void setTable07(String table07) {
        this.table07 = table07;
    }

    public String getTable08() {
        return table08;
    }

    public void setTable08(String table08) {
        this.table08 = table08;
    }

    public String getTable09() {
        return table09;
    }

    public void setTable09(String table09) {
        this.table09 = table09;
    }

    public String getTable10() {
        return table10;
    }

    public void setTable10(String table10) {
        this.table10 = table10;
    }

    public String getValue01() {
        return value01;
    }

    public void setValue01(String value01) {
        this.value01 = value01;
    }

    public String getValue02() {
        return value02;
    }

    public void setValue02(String value02) {
        this.value02 = value02;
    }

    public String getValue03() {
        return value03;
    }

    public void setValue03(String value03) {
        this.value03 = value03;
    }

    public String getValue04() {
        return value04;
    }

    public void setValue04(String value04) {
        this.value04 = value04;
    }

    public String getValue05() {
        return value05;
    }

    public void setValue05(String value05) {
        this.value05 = value05;
    }

    public String getValue06() {
        return value06;
    }

    public void setValue06(String value06) {
        this.value06 = value06;
    }

    public String getValue07() {
        return value07;
    }

    public void setValue07(String value07) {
        this.value07 = value07;
    }

    public String getValue08() {
        return value08;
    }

    public void setValue08(String value08) {
        this.value08 = value08;
    }

    public String getValue09() {
        return value09;
    }

    public void setValue09(String value09) {
        this.value09 = value09;
    }

    public String getValue10() {
        return value10;
    }

    public void setValue10(String value10) {
        this.value10 = value10;
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
            SOAPElement soapBodyElem = soapBody.addChildElement("UnitAddInfManager", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestUnitAddInf", "");
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
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("field1", "");
            soapBodyElem8.addTextNode(field1);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("field2", "");
            soapBodyElem9.addTextNode(field2);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("field3", "");
            soapBodyElem10.addTextNode(field3);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("field4", "");
            soapBodyElem11.addTextNode(field4);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("homeNumber", "");
            soapBodyElem12.addTextNode(homeNumber);
            SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("homeNumber2", "");
            soapBodyElem13.addTextNode(homeNumber2);
            SOAPElement soapBodyElem14 = soapBodyElemRequest.addChildElement("homeSuffix", "");
            soapBodyElem14.addTextNode(homeSuffix);
            SOAPElement soapBodyElem15 = soapBodyElemRequest.addChildElement("latitude", "");
            soapBodyElem15.addTextNode(latitude);
            SOAPElement soapBodyElem16 = soapBodyElemRequest.addChildElement("longitude", "");
            soapBodyElem16.addTextNode(longitude);
            SOAPElement soapBodyElem17 = soapBodyElemRequest.addChildElement("neighborhood", "");
            soapBodyElem17.addTextNode(neighborhood);
            SOAPElement soapBodyElem18 = soapBodyElemRequest.addChildElement("placeCode", "");
            soapBodyElem18.addTextNode(placeCode);
            SOAPElement soapBodyElem19 = soapBodyElemRequest.addChildElement("sector", "");
            soapBodyElem19.addTextNode(sector);
            SOAPElement soapBodyElem20 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem20.addTextNode(streetName);
            SOAPElement soapBodyElem21 = soapBodyElemRequest.addChildElement("table01", "");
            soapBodyElem21.addTextNode(table01);
            SOAPElement soapBodyElem22 = soapBodyElemRequest.addChildElement("table02", "");
            soapBodyElem22.addTextNode(table02);
            SOAPElement soapBodyElem23 = soapBodyElemRequest.addChildElement("table03", "");
            soapBodyElem23.addTextNode(table03);
            SOAPElement soapBodyElem24 = soapBodyElemRequest.addChildElement("table04", "");
            soapBodyElem24.addTextNode(table04);
            SOAPElement soapBodyElem25 = soapBodyElemRequest.addChildElement("table05", "");
            soapBodyElem25.addTextNode(table05);
            SOAPElement soapBodyElem26 = soapBodyElemRequest.addChildElement("table06", "");
            soapBodyElem26.addTextNode(table06);
            SOAPElement soapBodyElem27 = soapBodyElemRequest.addChildElement("table07", "");
            soapBodyElem27.addTextNode(table07);
            SOAPElement soapBodyElem28 = soapBodyElemRequest.addChildElement("table08", "");
            soapBodyElem28.addTextNode(table08);
            SOAPElement soapBodyElem29 = soapBodyElemRequest.addChildElement("table09", "");
            soapBodyElem29.addTextNode(table09);
            SOAPElement soapBodyElem30 = soapBodyElemRequest.addChildElement("table10", "");
            soapBodyElem30.addTextNode(table10);
            SOAPElement soapBodyElem31 = soapBodyElemRequest.addChildElement("value01", "");
            soapBodyElem31.addTextNode(value01);
            SOAPElement soapBodyElem32 = soapBodyElemRequest.addChildElement("value02", "");
            soapBodyElem32.addTextNode(value02);
            SOAPElement soapBodyElem33 = soapBodyElemRequest.addChildElement("value03", "");
            soapBodyElem33.addTextNode(value03);
            SOAPElement soapBodyElem34 = soapBodyElemRequest.addChildElement("value04", "");
            soapBodyElem34.addTextNode(value04);
            SOAPElement soapBodyElem35 = soapBodyElemRequest.addChildElement("value05", "");
            soapBodyElem35.addTextNode(value05);
            SOAPElement soapBodyElem36 = soapBodyElemRequest.addChildElement("value06", "");
            soapBodyElem36.addTextNode(value06);
            SOAPElement soapBodyElem37 = soapBodyElemRequest.addChildElement("value07", "");
            soapBodyElem37.addTextNode(value07);
            SOAPElement soapBodyElem38 = soapBodyElemRequest.addChildElement("value08", "");
            soapBodyElem38.addTextNode(value08);
            SOAPElement soapBodyElem39 = soapBodyElemRequest.addChildElement("value09", "");
            soapBodyElem39.addTextNode(value09);
            SOAPElement soapBodyElem40 = soapBodyElemRequest.addChildElement("value10", "");
            soapBodyElem40.addTextNode(value10);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "UnitAddInfManager");
            soapMessage.saveChanges();
            System.out.print("UnitAddInfManagerRequest SOAP Message = ");
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
