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
 * objetivo de la clase. Clase respuesta soap de el lamado a call services.
 *
 * @author Carlos Leonardo Villamil.
 * @versión 1.00.000
 */
public class CreateServiceCallRequest implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(CreateServiceCallRequest.class);

    private String caby;
    private String SubscriberAccount;
    private String unitNumber;
    private String houseNumber;
    private String houseSuffix;
    private String TwoHouseNo;
    private String streetName;
    private String apartmentNumber;
    private String community;
    private String division;
    private String building;
    private String neighborhood;
    private String block;
    private String Entrance;
    private String Sector;
    private String phoneRequireServices;
    private String contactPhoneNumber;
    private String secondContactNumber;
    private String calledInName;
    private String callReceivedBy;
    private String dateofCall;
    private String timeofCallHhmm;
    private String workForceCode;
    private String problemDescription;
    private String requestServiceDate;
    private String requestServiceTimeHhmm;
    private String truckRequiredFlag;
    private String reasonforService;
    private String Priority;
    private String bookingDate;
    private String BookingTimeCode;
    private String bookingSEQ;
    private String ScPoints;
    private String N_Y_Default;

    public String getCaby() {
        return caby;
    }

    public void setCaby(String caby) {
        this.caby = caby;
    }

    public String getSubscriberAccount() {
        return SubscriberAccount;
    }

    public void setSubscriberAccount(String SubscriberAccount) {
        this.SubscriberAccount = SubscriberAccount;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
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

    public String getTwoHouseNo() {
        return TwoHouseNo;
    }

    public void setTwoHouseNo(String TwoHouseNo) {
        this.TwoHouseNo = TwoHouseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getEntrance() {
        return Entrance;
    }

    public void setEntrance(String Entrance) {
        this.Entrance = Entrance;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String Sector) {
        this.Sector = Sector;
    }

    public String getPhoneRequireServices() {
        return phoneRequireServices;
    }

    public void setPhoneRequireServices(String phoneRequireServices) {
        this.phoneRequireServices = phoneRequireServices;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getSecondContactNumber() {
        return secondContactNumber;
    }

    public void setSecondContactNumber(String secondContactNumber) {
        this.secondContactNumber = secondContactNumber;
    }

    public String getCalledInName() {
        return calledInName;
    }

    public void setCalledInName(String calledInName) {
        this.calledInName = calledInName;
    }

    public String getCallReceivedBy() {
        return callReceivedBy;
    }

    public void setCallReceivedBy(String callReceivedBy) {
        this.callReceivedBy = callReceivedBy;
    }

    public String getDateofCall() {
        return dateofCall;
    }

    public void setDateofCall(String dateofCall) {
        this.dateofCall = dateofCall;
    }

    public String getTimeofCallHhmm() {
        return timeofCallHhmm;
    }

    public void setTimeofCallHhmm(String timeofCallHhmm) {
        this.timeofCallHhmm = timeofCallHhmm;
    }

    public String getWorkForceCode() {
        return workForceCode;
    }

    public void setWorkForceCode(String workForceCode) {
        this.workForceCode = workForceCode;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getRequestServiceDate() {
        return requestServiceDate;
    }

    public void setRequestServiceDate(String requestServiceDate) {
        this.requestServiceDate = requestServiceDate;
    }

    public String getRequestServiceTimeHhmm() {
        return requestServiceTimeHhmm;
    }

    public void setRequestServiceTimeHhmm(String requestServiceTimeHhmm) {
        this.requestServiceTimeHhmm = requestServiceTimeHhmm;
    }

    public String getTruckRequiredFlag() {
        return truckRequiredFlag;
    }

    public void setTruckRequiredFlag(String truckRequiredFlag) {
        this.truckRequiredFlag = truckRequiredFlag;
    }

    public String getReasonforService() {
        return reasonforService;
    }

    public void setReasonforService(String reasonforService) {
        this.reasonforService = reasonforService;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTimeCode() {
        return BookingTimeCode;
    }

    public void setBookingTimeCode(String BookingTimeCode) {
        this.BookingTimeCode = BookingTimeCode;
    }

    public String getBookingSEQ() {
        return bookingSEQ;
    }

    public void setBookingSEQ(String bookingSEQ) {
        this.bookingSEQ = bookingSEQ;
    }

    public String getScPoints() {
        return ScPoints;
    }

    public void setScPoints(String ScPoints) {
        this.ScPoints = ScPoints;
    }

    public String getN_Y_Default() {
        return N_Y_Default;
    }

    public void setN_Y_Default(String N_Y_Default) {
        this.N_Y_Default = N_Y_Default;
    }

    /**
     * Descripción del objetivo del método.Crea el request sopa al unit2 para
     * crear Laamadas de servicio.
     *
     * @author Carlos Leonardo Villamil
     * @version 1.00.001
     * @param url
     * @throws co.com.claro.mgl.error.ApplicationException
     * @Created 2008-09-09
     * @ModifiedDate 2015-09-22
     * @return SOAPMessage Sopa Message.
     */
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
            SOAPElement soapBodyElem = soapBody.addChildElement("createServiceCall", "unit");
            SOAPElement soapBodyElemRequest = soapBodyElem.addChildElement("CreateServiceCallRequest", "");
            SOAPElement soapBodyElem1 = soapBodyElemRequest.addChildElement("caby", "");
            soapBodyElem1.addTextNode(caby);
            SOAPElement soapBodyElem2 = soapBodyElemRequest.addChildElement("subscriberAccount", "");
            soapBodyElem2.addTextNode(SubscriberAccount);
            SOAPElement soapBodyElem3 = soapBodyElemRequest.addChildElement("unitNumber", "");
            soapBodyElem3.addTextNode(unitNumber);
            SOAPElement soapBodyElem4 = soapBodyElemRequest.addChildElement("houseNumber", "");
            soapBodyElem4.addTextNode(houseNumber);
            SOAPElement soapBodyElem5 = soapBodyElemRequest.addChildElement("houseSuffix", "");
            soapBodyElem5.addTextNode(houseSuffix);
            SOAPElement soapBodyElem6 = soapBodyElemRequest.addChildElement("twoHouseNo", "");
            soapBodyElem6.addTextNode(TwoHouseNo);
            SOAPElement soapBodyElem7 = soapBodyElemRequest.addChildElement("streetName", "");
            soapBodyElem7.addTextNode(streetName);
            SOAPElement soapBodyElem8 = soapBodyElemRequest.addChildElement("apartmentNumber", "");
            soapBodyElem8.addTextNode(apartmentNumber);
            SOAPElement soapBodyElem9 = soapBodyElemRequest.addChildElement("community", "");
            soapBodyElem9.addTextNode(community);
            SOAPElement soapBodyElem10 = soapBodyElemRequest.addChildElement("division", "");
            soapBodyElem10.addTextNode(division);
            SOAPElement soapBodyElem11 = soapBodyElemRequest.addChildElement("building", "");
            soapBodyElem11.addTextNode(building);
            SOAPElement soapBodyElem12 = soapBodyElemRequest.addChildElement("neighborhood", "");
            soapBodyElem12.addTextNode(neighborhood);
            SOAPElement soapBodyElem13 = soapBodyElemRequest.addChildElement("block", "");
            soapBodyElem13.addTextNode(block);
            SOAPElement soapBodyElem14 = soapBodyElemRequest.addChildElement("entrance", "");
            soapBodyElem14.addTextNode(Entrance);
            SOAPElement soapBodyElem15 = soapBodyElemRequest.addChildElement("sector", "");
            soapBodyElem15.addTextNode(Sector);
            SOAPElement soapBodyElem16 = soapBodyElemRequest.addChildElement("phoneRequireServices", "");
            soapBodyElem16.addTextNode(phoneRequireServices);
            SOAPElement soapBodyElem17 = soapBodyElemRequest.addChildElement("contactPhoneNumber", "");
            soapBodyElem17.addTextNode(contactPhoneNumber);
            SOAPElement soapBodyElem18 = soapBodyElemRequest.addChildElement("secondContactNumber", "");
            soapBodyElem18.addTextNode(secondContactNumber);
            SOAPElement soapBodyElem19 = soapBodyElemRequest.addChildElement("calledInName", "");
            soapBodyElem19.addTextNode(calledInName);
            SOAPElement soapBodyElem20 = soapBodyElemRequest.addChildElement("callReceivedBy", "");
            soapBodyElem20.addTextNode(callReceivedBy);
            SOAPElement soapBodyElem21 = soapBodyElemRequest.addChildElement("dateofCall", "");
            soapBodyElem21.addTextNode(dateofCall);
            SOAPElement soapBodyElem22 = soapBodyElemRequest.addChildElement("timeofCallHhmm", "");
            soapBodyElem22.addTextNode(timeofCallHhmm);
            SOAPElement soapBodyElem23 = soapBodyElemRequest.addChildElement("workForceCode", "");
            soapBodyElem23.addTextNode(workForceCode);
            SOAPElement soapBodyElem24 = soapBodyElemRequest.addChildElement("problemDescription", "");
            soapBodyElem24.addTextNode(problemDescription);
            SOAPElement soapBodyElem25 = soapBodyElemRequest.addChildElement("requestServiceDate", "");
            soapBodyElem25.addTextNode(requestServiceDate);
            SOAPElement soapBodyElem26 = soapBodyElemRequest.addChildElement("requestServiceTimeHhmm", "");
            soapBodyElem26.addTextNode(requestServiceTimeHhmm);
            SOAPElement soapBodyElem27 = soapBodyElemRequest.addChildElement("truckRequiredFlag", "");
            soapBodyElem27.addTextNode(truckRequiredFlag);
            SOAPElement soapBodyElem28 = soapBodyElemRequest.addChildElement("reasonforService", "");
            soapBodyElem28.addTextNode(reasonforService);
            SOAPElement soapBodyElem29 = soapBodyElemRequest.addChildElement("priority", "");
            soapBodyElem29.addTextNode(Priority);
            SOAPElement soapBodyElem30 = soapBodyElemRequest.addChildElement("bookingDate", "");
            soapBodyElem30.addTextNode(bookingDate);
            SOAPElement soapBodyElem31 = soapBodyElemRequest.addChildElement("bookingTimeCode", "");
            soapBodyElem31.addTextNode(BookingTimeCode);
            SOAPElement soapBodyElem32 = soapBodyElemRequest.addChildElement("bookingSEQ", "");
            soapBodyElem32.addTextNode(bookingSEQ);
            SOAPElement soapBodyElem33 = soapBodyElemRequest.addChildElement("scPoints", "");
            soapBodyElem33.addTextNode(ScPoints);
            SOAPElement soapBodyElem34 = soapBodyElemRequest.addChildElement("n_Y_Default", "");
            soapBodyElem34.addTextNode(N_Y_Default);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", url + "createServiceCall");
            soapMessage.saveChanges();
            System.out.print("Request SOAP Message = ");
            soapMessage.writeTo(System.out);

            return soapMessage;
        } catch (SOAPException | IOException ex) {
            LOGGER.error("Error al momento de crear el SOAP request. EX000: " + ex.getMessage());
            throw new ApplicationException("Error al momento de crear el SOAP request. EX000: " + ex.getMessage(), ex);
        }
    }
}
