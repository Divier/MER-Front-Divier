package co.com.claro.visitastecnicas.ws.proxy;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class AddUnitRequest implements IRequest {

    private String addressableTapAddress = "";
    private String apartmentNumber = "";
    private String auditCompletedBy = "";
    private String auditCompletedDate = "";
    private String block = "";
    private String building = "";
    private String buildingName = "";
    private String caby = "";
    private String community = "";
    private String dealer = "";
    private String division = "";
    private String dropType = "";
    private String dropTypeCable = "";
    private String entrance = "";
    private String flat = "";
    private String floor = "";
    private String gridPosition = "";
    private String headEnd = "";
    private String houseNumber = "";
    private String houseSuffix = "";
    private String houseTwo = "";
    private String neighborhood = "";
    private String normalRateSchedule = "";
    private String ownershipOfCable = "";
    private String personalizeRateSchedule = "";
    private String plantLocType = "";
    private String plantLocation = "";
    private String postalCode = "";
    private String problemUnitCodes = "";
    private String project = "";
    private String sector = "";
    private String spigott = "";
    private String stratus = "";
    private String streetName = "";
    private String tagNumber = "";
    private String tap = "";
    private String typeRequest = "";
    private String unitStatus = "";
    private String unitTrappedFlag = "";
    private String unitType = "";

    public void setAddressableTapAddress(String addressableTapAddress) {
        this.addressableTapAddress = addressableTapAddress;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void setAuditCompletedBy(String auditCompletedBy) {
        this.auditCompletedBy = auditCompletedBy;
    }

    public void setAuditCompletedDate(String auditCompletedDate) {
        this.auditCompletedDate = auditCompletedDate;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setCaby(String caby) {
        this.caby = caby;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setDropType(String dropType) {
        this.dropType = dropType;
    }

    public void setDropTypeCable(String dropTypeCable) {
        this.dropTypeCable = dropTypeCable;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setGridPosition(String gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setHouseSuffix(String houseSuffix) {
        this.houseSuffix = houseSuffix;
    }

    public void setHouseTwo(String houseTwo) {
        this.houseTwo = houseTwo;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setNormalRateSchedule(String normalRateSchedule) {
        this.normalRateSchedule = normalRateSchedule;
    }

    public void setOwnershipOfCable(String ownershipOfCable) {
        this.ownershipOfCable = ownershipOfCable;
    }

    public void setPersonalizeRateSchedule(String personalizeRateSchedule) {
        this.personalizeRateSchedule = personalizeRateSchedule;
    }

    public void setPlantLocType(String plantLocType) {
        this.plantLocType = plantLocType;
    }

    public void setPlantLocation(String plantLocation) {
        this.plantLocation = plantLocation;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProblemUnitCodes(String problemUnitCodes) {
        this.problemUnitCodes = problemUnitCodes;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setSpigott(String spigott) {
        this.spigott = spigott;
    }

    public void setStratus(String stratus) {
        this.stratus = stratus;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public void setTap(String tap) {
        this.tap = tap;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public void setUnitTrappedFlag(String unitTrappedFlag) {
        this.unitTrappedFlag = unitTrappedFlag;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public SOAPMessage createSOAPRequest(String url) throws SOAPException, IOException {
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

        SOAPElement soapBodyElem = soapBody.addChildElement("UnitManager", "unit");
        SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("requestAddUnit", "");
        SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("addressableTapAddress", "");
        soapBodyElem1.addTextNode(addressableTapAddress);
        SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("apartmentNumber", "");
        soapBodyElem2.addTextNode(apartmentNumber);
        SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("auditCompletedBy", "");
        soapBodyElem3.addTextNode(auditCompletedBy);
        SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("auditCompletedDate", "");
        soapBodyElem4.addTextNode(auditCompletedDate);
        SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("block", "");
        soapBodyElem5.addTextNode(block);
        SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("building", "");
        soapBodyElem6.addTextNode(building);
        SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("buildingName", "");
        soapBodyElem7.addTextNode(buildingName);
        SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("caby", "");
        soapBodyElem8.addTextNode(caby);
        SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("community", "");
        soapBodyElem9.addTextNode(community);
        SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("dealer", "");
        soapBodyElem10.addTextNode(dealer);
        SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("division", "");
        soapBodyElem11.addTextNode(division);
        SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("dropType", "");
        soapBodyElem12.addTextNode(dropType);
        SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("dropTypeCable", "");
        soapBodyElem13.addTextNode(dropTypeCable);
        SOAPElement soapBodyElem14 = soapBodyElemRequest.addChildElement("entrance", "");
        soapBodyElem14.addTextNode(entrance);
        SOAPElement soapBodyElem15 = soapBodyElemRequest.addChildElement("flat", "");
        soapBodyElem15.addTextNode(flat);
        SOAPElement soapBodyElem16 = soapBodyElemRequest.addChildElement("floor", "");
        soapBodyElem16.addTextNode(floor);
        SOAPElement soapBodyElem17 = soapBodyElemRequest.addChildElement("gridPosition", "");
        soapBodyElem17.addTextNode(gridPosition);
        SOAPElement soapBodyElem18 = soapBodyElemRequest.addChildElement("headEnd", "");
        soapBodyElem18.addTextNode(headEnd);
        SOAPElement soapBodyElem19 = soapBodyElemRequest.addChildElement("houseNumber", "");
        soapBodyElem19.addTextNode(houseNumber);
        SOAPElement soapBodyElem20 = soapBodyElemRequest.addChildElement("houseSuffix", "");
        soapBodyElem20.addTextNode(houseSuffix);
        SOAPElement soapBodyElem21 = soapBodyElemRequest.addChildElement("houseTwo", "");
        soapBodyElem21.addTextNode(houseTwo);
        SOAPElement soapBodyElem22 = soapBodyElemRequest.addChildElement("neighborhood", "");
        soapBodyElem22.addTextNode(neighborhood);
        SOAPElement soapBodyElem23 = soapBodyElemRequest.addChildElement("normalRateSchedule", "");
        soapBodyElem23.addTextNode(normalRateSchedule);
        SOAPElement soapBodyElem24 = soapBodyElemRequest.addChildElement("ownershipOfCable", "");
        soapBodyElem24.addTextNode(ownershipOfCable);
        SOAPElement soapBodyElem25 = soapBodyElemRequest.addChildElement("personalizeRateSchedule", "");
        soapBodyElem25.addTextNode(personalizeRateSchedule);
        SOAPElement soapBodyElem26 = soapBodyElemRequest.addChildElement("plantLocation", "");
        soapBodyElem26.addTextNode(plantLocation);
        SOAPElement soapBodyElem27 = soapBodyElemRequest.addChildElement("plantLocType", "");
        soapBodyElem27.addTextNode(plantLocType);
        SOAPElement soapBodyElem28 = soapBodyElemRequest.addChildElement("postalCode", "");
        soapBodyElem28.addTextNode(postalCode);
        SOAPElement soapBodyElem29 = soapBodyElemRequest.addChildElement("problemUnitCodes", "");
        soapBodyElem29.addTextNode(problemUnitCodes);
        SOAPElement soapBodyElem30 = soapBodyElemRequest.addChildElement("project", "");
        soapBodyElem30.addTextNode(project);
        SOAPElement soapBodyElem31 = soapBodyElemRequest.addChildElement("sector", "");
        soapBodyElem31.addTextNode(sector);
        SOAPElement soapBodyElem32 = soapBodyElemRequest.addChildElement("spigott", "");
        soapBodyElem32.addTextNode(spigott);
        SOAPElement soapBodyElem33 = soapBodyElemRequest.addChildElement("stratus", "");
        soapBodyElem33.addTextNode(stratus);
        SOAPElement soapBodyElem34 = soapBodyElemRequest.addChildElement("streetName", "");
        soapBodyElem34.addTextNode(streetName);
        SOAPElement soapBodyElem35 = soapBodyElemRequest.addChildElement("tagNumber", "");
        soapBodyElem35.addTextNode(tagNumber);
        SOAPElement soapBodyElem36 = soapBodyElemRequest.addChildElement("tap", "");
        soapBodyElem36.addTextNode(tap);
        SOAPElement soapBodyElem37 = soapBodyElemRequest.addChildElement("typeRequest", "");
        soapBodyElem37.addTextNode(typeRequest);
        SOAPElement soapBodyElem38 = soapBodyElemRequest.addChildElement("unitStatus", "");
        soapBodyElem38.addTextNode(unitStatus);
        SOAPElement soapBodyElem39 = soapBodyElemRequest.addChildElement("unitTrappedFlag", "");
        soapBodyElem39.addTextNode(unitTrappedFlag);
        SOAPElement soapBodyElem40 = soapBodyElemRequest.addChildElement("unitType", "");
        soapBodyElem40.addTextNode(unitType);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", url + "UnitManager");
        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);
        return soapMessage;
    }

    @Override
    public SOAPMessage createSOAPRequestHhPp(String url) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
