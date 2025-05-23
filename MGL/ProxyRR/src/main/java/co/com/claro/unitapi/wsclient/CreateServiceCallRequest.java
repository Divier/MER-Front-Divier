/**
 * CreateServiceCallRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class CreateServiceCallRequest extends BasicMessage implements java.io.Serializable {

    private java.lang.String apartmentNumber;

    private java.lang.String block;

    private java.lang.String bookingDate;

    private java.lang.String bookingSEQ;

    private java.lang.String bookingTimeCode;

    private java.lang.String building;

    private java.lang.String caby;

    private java.lang.String callReceivedBy;

    private java.lang.String calledInName;

    private java.lang.String community;

    private java.lang.String contactPhoneNumber;

    private java.lang.String dateofCall;

    private java.lang.String division;

    private java.lang.String entrance;

    private java.lang.String houseNumber;

    private java.lang.String houseSuffix;

    private java.lang.String n_Y_Default;

    private java.lang.String neighborhood;

    private java.lang.String phoneRequireServices;

    private java.lang.String priority;

    private java.lang.String problemDescription;

    private java.lang.String reasonforService;

    private java.lang.String requestServiceDate;

    private java.lang.String requestServiceTimeHhmm;

    private java.lang.String scPoints;

    private java.lang.String secondContactNumber;

    private java.lang.String sector;

    private java.lang.String streetName;

    private java.lang.String subscriberAccount;

    private java.lang.String timeofCallHhmm;

    private java.lang.String truckRequiredFlag;

    private java.lang.String twoHouseNo;

    private java.lang.String unitNumber;

    private java.lang.String workForceCode;

    public CreateServiceCallRequest() {
    }

    public CreateServiceCallRequest(
            java.lang.String apartmentNumber,
            java.lang.String block,
            java.lang.String bookingDate,
            java.lang.String bookingSEQ,
            java.lang.String bookingTimeCode,
            java.lang.String building,
            java.lang.String caby,
            java.lang.String callReceivedBy,
            java.lang.String calledInName,
            java.lang.String community,
            java.lang.String contactPhoneNumber,
            java.lang.String dateofCall,
            java.lang.String division,
            java.lang.String entrance,
            java.lang.String houseNumber,
            java.lang.String houseSuffix,
            java.lang.String n_Y_Default,
            java.lang.String neighborhood,
            java.lang.String phoneRequireServices,
            java.lang.String priority,
            java.lang.String problemDescription,
            java.lang.String reasonforService,
            java.lang.String requestServiceDate,
            java.lang.String requestServiceTimeHhmm,
            java.lang.String scPoints,
            java.lang.String secondContactNumber,
            java.lang.String sector,
            java.lang.String streetName,
            java.lang.String subscriberAccount,
            java.lang.String timeofCallHhmm,
            java.lang.String truckRequiredFlag,
            java.lang.String twoHouseNo,
            java.lang.String unitNumber,
            java.lang.String workForceCode) {
        this.apartmentNumber = apartmentNumber;
        this.block = block;
        this.bookingDate = bookingDate;
        this.bookingSEQ = bookingSEQ;
        this.bookingTimeCode = bookingTimeCode;
        this.building = building;
        this.caby = caby;
        this.callReceivedBy = callReceivedBy;
        this.calledInName = calledInName;
        this.community = community;
        this.contactPhoneNumber = contactPhoneNumber;
        this.dateofCall = dateofCall;
        this.division = division;
        this.entrance = entrance;
        this.houseNumber = houseNumber;
        this.houseSuffix = houseSuffix;
        this.n_Y_Default = n_Y_Default;
        this.neighborhood = neighborhood;
        this.phoneRequireServices = phoneRequireServices;
        this.priority = priority;
        this.problemDescription = problemDescription;
        this.reasonforService = reasonforService;
        this.requestServiceDate = requestServiceDate;
        this.requestServiceTimeHhmm = requestServiceTimeHhmm;
        this.scPoints = scPoints;
        this.secondContactNumber = secondContactNumber;
        this.sector = sector;
        this.streetName = streetName;
        this.subscriberAccount = subscriberAccount;
        this.timeofCallHhmm = timeofCallHhmm;
        this.truckRequiredFlag = truckRequiredFlag;
        this.twoHouseNo = twoHouseNo;
        this.unitNumber = unitNumber;
        this.workForceCode = workForceCode;
    }

    /**
     * Gets the apartmentNumber value for this CreateServiceCallRequest.
     *
     * @return apartmentNumber
     */
    public java.lang.String getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * Sets the apartmentNumber value for this CreateServiceCallRequest.
     *
     * @param apartmentNumber
     */
    public void setApartmentNumber(java.lang.String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    /**
     * Gets the block value for this CreateServiceCallRequest.
     *
     * @return block
     */
    public java.lang.String getBlock() {
        return block;
    }

    /**
     * Sets the block value for this CreateServiceCallRequest.
     *
     * @param block
     */
    public void setBlock(java.lang.String block) {
        this.block = block;
    }

    /**
     * Gets the bookingDate value for this CreateServiceCallRequest.
     *
     * @return bookingDate
     */
    public java.lang.String getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the bookingDate value for this CreateServiceCallRequest.
     *
     * @param bookingDate
     */
    public void setBookingDate(java.lang.String bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets the bookingSEQ value for this CreateServiceCallRequest.
     *
     * @return bookingSEQ
     */
    public java.lang.String getBookingSEQ() {
        return bookingSEQ;
    }

    /**
     * Sets the bookingSEQ value for this CreateServiceCallRequest.
     *
     * @param bookingSEQ
     */
    public void setBookingSEQ(java.lang.String bookingSEQ) {
        this.bookingSEQ = bookingSEQ;
    }

    /**
     * Gets the bookingTimeCode value for this CreateServiceCallRequest.
     *
     * @return bookingTimeCode
     */
    public java.lang.String getBookingTimeCode() {
        return bookingTimeCode;
    }

    /**
     * Sets the bookingTimeCode value for this CreateServiceCallRequest.
     *
     * @param bookingTimeCode
     */
    public void setBookingTimeCode(java.lang.String bookingTimeCode) {
        this.bookingTimeCode = bookingTimeCode;
    }

    /**
     * Gets the building value for this CreateServiceCallRequest.
     *
     * @return building
     */
    public java.lang.String getBuilding() {
        return building;
    }

    /**
     * Sets the building value for this CreateServiceCallRequest.
     *
     * @param building
     */
    public void setBuilding(java.lang.String building) {
        this.building = building;
    }

    /**
     * Gets the caby value for this CreateServiceCallRequest.
     *
     * @return caby
     */
    public java.lang.String getCaby() {
        return caby;
    }

    /**
     * Sets the caby value for this CreateServiceCallRequest.
     *
     * @param caby
     */
    public void setCaby(java.lang.String caby) {
        this.caby = caby;
    }

    /**
     * Gets the callReceivedBy value for this CreateServiceCallRequest.
     *
     * @return callReceivedBy
     */
    public java.lang.String getCallReceivedBy() {
        return callReceivedBy;
    }

    /**
     * Sets the callReceivedBy value for this CreateServiceCallRequest.
     *
     * @param callReceivedBy
     */
    public void setCallReceivedBy(java.lang.String callReceivedBy) {
        this.callReceivedBy = callReceivedBy;
    }

    /**
     * Gets the calledInName value for this CreateServiceCallRequest.
     *
     * @return calledInName
     */
    public java.lang.String getCalledInName() {
        return calledInName;
    }

    /**
     * Sets the calledInName value for this CreateServiceCallRequest.
     *
     * @param calledInName
     */
    public void setCalledInName(java.lang.String calledInName) {
        this.calledInName = calledInName;
    }

    /**
     * Gets the community value for this CreateServiceCallRequest.
     *
     * @return community
     */
    public java.lang.String getCommunity() {
        return community;
    }

    /**
     * Sets the community value for this CreateServiceCallRequest.
     *
     * @param community
     */
    public void setCommunity(java.lang.String community) {
        this.community = community;
    }

    /**
     * Gets the contactPhoneNumber value for this CreateServiceCallRequest.
     *
     * @return contactPhoneNumber
     */
    public java.lang.String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    /**
     * Sets the contactPhoneNumber value for this CreateServiceCallRequest.
     *
     * @param contactPhoneNumber
     */
    public void setContactPhoneNumber(java.lang.String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    /**
     * Gets the dateofCall value for this CreateServiceCallRequest.
     *
     * @return dateofCall
     */
    public java.lang.String getDateofCall() {
        return dateofCall;
    }

    /**
     * Sets the dateofCall value for this CreateServiceCallRequest.
     *
     * @param dateofCall
     */
    public void setDateofCall(java.lang.String dateofCall) {
        this.dateofCall = dateofCall;
    }

    /**
     * Gets the division value for this CreateServiceCallRequest.
     *
     * @return division
     */
    public java.lang.String getDivision() {
        return division;
    }

    /**
     * Sets the division value for this CreateServiceCallRequest.
     *
     * @param division
     */
    public void setDivision(java.lang.String division) {
        this.division = division;
    }

    /**
     * Gets the entrance value for this CreateServiceCallRequest.
     *
     * @return entrance
     */
    public java.lang.String getEntrance() {
        return entrance;
    }

    /**
     * Sets the entrance value for this CreateServiceCallRequest.
     *
     * @param entrance
     */
    public void setEntrance(java.lang.String entrance) {
        this.entrance = entrance;
    }

    /**
     * Gets the houseNumber value for this CreateServiceCallRequest.
     *
     * @return houseNumber
     */
    public java.lang.String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets the houseNumber value for this CreateServiceCallRequest.
     *
     * @param houseNumber
     */
    public void setHouseNumber(java.lang.String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets the houseSuffix value for this CreateServiceCallRequest.
     *
     * @return houseSuffix
     */
    public java.lang.String getHouseSuffix() {
        return houseSuffix;
    }

    /**
     * Sets the houseSuffix value for this CreateServiceCallRequest.
     *
     * @param houseSuffix
     */
    public void setHouseSuffix(java.lang.String houseSuffix) {
        this.houseSuffix = houseSuffix;
    }

    /**
     * Gets the n_Y_Default value for this CreateServiceCallRequest.
     *
     * @return n_Y_Default
     */
    public java.lang.String getN_Y_Default() {
        return n_Y_Default;
    }

    /**
     * Sets the n_Y_Default value for this CreateServiceCallRequest.
     *
     * @param n_Y_Default
     */
    public void setN_Y_Default(java.lang.String n_Y_Default) {
        this.n_Y_Default = n_Y_Default;
    }

    /**
     * Gets the neighborhood value for this CreateServiceCallRequest.
     *
     * @return neighborhood
     */
    public java.lang.String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood value for this CreateServiceCallRequest.
     *
     * @param neighborhood
     */
    public void setNeighborhood(java.lang.String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the phoneRequireServices value for this CreateServiceCallRequest.
     *
     * @return phoneRequireServices
     */
    public java.lang.String getPhoneRequireServices() {
        return phoneRequireServices;
    }

    /**
     * Sets the phoneRequireServices value for this CreateServiceCallRequest.
     *
     * @param phoneRequireServices
     */
    public void setPhoneRequireServices(java.lang.String phoneRequireServices) {
        this.phoneRequireServices = phoneRequireServices;
    }

    /**
     * Gets the priority value for this CreateServiceCallRequest.
     *
     * @return priority
     */
    public java.lang.String getPriority() {
        return priority;
    }

    /**
     * Sets the priority value for this CreateServiceCallRequest.
     *
     * @param priority
     */
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }

    /**
     * Gets the problemDescription value for this CreateServiceCallRequest.
     *
     * @return problemDescription
     */
    public java.lang.String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Sets the problemDescription value for this CreateServiceCallRequest.
     *
     * @param problemDescription
     */
    public void setProblemDescription(java.lang.String problemDescription) {
        this.problemDescription = problemDescription;
    }

    /**
     * Gets the reasonforService value for this CreateServiceCallRequest.
     *
     * @return reasonforService
     */
    public java.lang.String getReasonforService() {
        return reasonforService;
    }

    /**
     * Sets the reasonforService value for this CreateServiceCallRequest.
     *
     * @param reasonforService
     */
    public void setReasonforService(java.lang.String reasonforService) {
        this.reasonforService = reasonforService;
    }

    /**
     * Gets the requestServiceDate value for this CreateServiceCallRequest.
     *
     * @return requestServiceDate
     */
    public java.lang.String getRequestServiceDate() {
        return requestServiceDate;
    }

    /**
     * Sets the requestServiceDate value for this CreateServiceCallRequest.
     *
     * @param requestServiceDate
     */
    public void setRequestServiceDate(java.lang.String requestServiceDate) {
        this.requestServiceDate = requestServiceDate;
    }

    /**
     * Gets the requestServiceTimeHhmm value for this CreateServiceCallRequest.
     *
     * @return requestServiceTimeHhmm
     */
    public java.lang.String getRequestServiceTimeHhmm() {
        return requestServiceTimeHhmm;
    }

    /**
     * Sets the requestServiceTimeHhmm value for this CreateServiceCallRequest.
     *
     * @param requestServiceTimeHhmm
     */
    public void setRequestServiceTimeHhmm(java.lang.String requestServiceTimeHhmm) {
        this.requestServiceTimeHhmm = requestServiceTimeHhmm;
    }

    /**
     * Gets the scPoints value for this CreateServiceCallRequest.
     *
     * @return scPoints
     */
    public java.lang.String getScPoints() {
        return scPoints;
    }

    /**
     * Sets the scPoints value for this CreateServiceCallRequest.
     *
     * @param scPoints
     */
    public void setScPoints(java.lang.String scPoints) {
        this.scPoints = scPoints;
    }

    /**
     * Gets the secondContactNumber value for this CreateServiceCallRequest.
     *
     * @return secondContactNumber
     */
    public java.lang.String getSecondContactNumber() {
        return secondContactNumber;
    }

    /**
     * Sets the secondContactNumber value for this CreateServiceCallRequest.
     *
     * @param secondContactNumber
     */
    public void setSecondContactNumber(java.lang.String secondContactNumber) {
        this.secondContactNumber = secondContactNumber;
    }

    /**
     * Gets the sector value for this CreateServiceCallRequest.
     *
     * @return sector
     */
    public java.lang.String getSector() {
        return sector;
    }

    /**
     * Sets the sector value for this CreateServiceCallRequest.
     *
     * @param sector
     */
    public void setSector(java.lang.String sector) {
        this.sector = sector;
    }

    /**
     * Gets the streetName value for this CreateServiceCallRequest.
     *
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }

    /**
     * Sets the streetName value for this CreateServiceCallRequest.
     *
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets the subscriberAccount value for this CreateServiceCallRequest.
     *
     * @return subscriberAccount
     */
    public java.lang.String getSubscriberAccount() {
        return subscriberAccount;
    }

    /**
     * Sets the subscriberAccount value for this CreateServiceCallRequest.
     *
     * @param subscriberAccount
     */
    public void setSubscriberAccount(java.lang.String subscriberAccount) {
        this.subscriberAccount = subscriberAccount;
    }

    /**
     * Gets the timeofCallHhmm value for this CreateServiceCallRequest.
     *
     * @return timeofCallHhmm
     */
    public java.lang.String getTimeofCallHhmm() {
        return timeofCallHhmm;
    }

    /**
     * Sets the timeofCallHhmm value for this CreateServiceCallRequest.
     *
     * @param timeofCallHhmm
     */
    public void setTimeofCallHhmm(java.lang.String timeofCallHhmm) {
        this.timeofCallHhmm = timeofCallHhmm;
    }

    /**
     * Gets the truckRequiredFlag value for this CreateServiceCallRequest.
     *
     * @return truckRequiredFlag
     */
    public java.lang.String getTruckRequiredFlag() {
        return truckRequiredFlag;
    }

    /**
     * Sets the truckRequiredFlag value for this CreateServiceCallRequest.
     *
     * @param truckRequiredFlag
     */
    public void setTruckRequiredFlag(java.lang.String truckRequiredFlag) {
        this.truckRequiredFlag = truckRequiredFlag;
    }

    /**
     * Gets the twoHouseNo value for this CreateServiceCallRequest.
     *
     * @return twoHouseNo
     */
    public java.lang.String getTwoHouseNo() {
        return twoHouseNo;
    }

    /**
     * Sets the twoHouseNo value for this CreateServiceCallRequest.
     *
     * @param twoHouseNo
     */
    public void setTwoHouseNo(java.lang.String twoHouseNo) {
        this.twoHouseNo = twoHouseNo;
    }

    /**
     * Gets the unitNumber value for this CreateServiceCallRequest.
     *
     * @return unitNumber
     */
    public java.lang.String getUnitNumber() {
        return unitNumber;
    }

    /**
     * Sets the unitNumber value for this CreateServiceCallRequest.
     *
     * @param unitNumber
     */
    public void setUnitNumber(java.lang.String unitNumber) {
        this.unitNumber = unitNumber;
    }

    /**
     * Gets the workForceCode value for this CreateServiceCallRequest.
     *
     * @return workForceCode
     */
    public java.lang.String getWorkForceCode() {
        return workForceCode;
    }

    /**
     * Sets the workForceCode value for this CreateServiceCallRequest.
     *
     * @param workForceCode
     */
    public void setWorkForceCode(java.lang.String workForceCode) {
        this.workForceCode = workForceCode;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CreateServiceCallRequest)) {
            return false;
        }
        CreateServiceCallRequest other = (CreateServiceCallRequest) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.apartmentNumber == null && other.getApartmentNumber() == null)
                || (this.apartmentNumber != null
                && this.apartmentNumber.equals(other.getApartmentNumber())))
                && ((this.block == null && other.getBlock() == null)
                || (this.block != null
                && this.block.equals(other.getBlock())))
                && ((this.bookingDate == null && other.getBookingDate() == null)
                || (this.bookingDate != null
                && this.bookingDate.equals(other.getBookingDate())))
                && ((this.bookingSEQ == null && other.getBookingSEQ() == null)
                || (this.bookingSEQ != null
                && this.bookingSEQ.equals(other.getBookingSEQ())))
                && ((this.bookingTimeCode == null && other.getBookingTimeCode() == null)
                || (this.bookingTimeCode != null
                && this.bookingTimeCode.equals(other.getBookingTimeCode())))
                && ((this.building == null && other.getBuilding() == null)
                || (this.building != null
                && this.building.equals(other.getBuilding())))
                && ((this.caby == null && other.getCaby() == null)
                || (this.caby != null
                && this.caby.equals(other.getCaby())))
                && ((this.callReceivedBy == null && other.getCallReceivedBy() == null)
                || (this.callReceivedBy != null
                && this.callReceivedBy.equals(other.getCallReceivedBy())))
                && ((this.calledInName == null && other.getCalledInName() == null)
                || (this.calledInName != null
                && this.calledInName.equals(other.getCalledInName())))
                && ((this.community == null && other.getCommunity() == null)
                || (this.community != null
                && this.community.equals(other.getCommunity())))
                && ((this.contactPhoneNumber == null && other.getContactPhoneNumber() == null)
                || (this.contactPhoneNumber != null
                && this.contactPhoneNumber.equals(other.getContactPhoneNumber())))
                && ((this.dateofCall == null && other.getDateofCall() == null)
                || (this.dateofCall != null
                && this.dateofCall.equals(other.getDateofCall())))
                && ((this.division == null && other.getDivision() == null)
                || (this.division != null
                && this.division.equals(other.getDivision())))
                && ((this.entrance == null && other.getEntrance() == null)
                || (this.entrance != null
                && this.entrance.equals(other.getEntrance())))
                && ((this.houseNumber == null && other.getHouseNumber() == null)
                || (this.houseNumber != null
                && this.houseNumber.equals(other.getHouseNumber())))
                && ((this.houseSuffix == null && other.getHouseSuffix() == null)
                || (this.houseSuffix != null
                && this.houseSuffix.equals(other.getHouseSuffix())))
                && ((this.n_Y_Default == null && other.getN_Y_Default() == null)
                || (this.n_Y_Default != null
                && this.n_Y_Default.equals(other.getN_Y_Default())))
                && ((this.neighborhood == null && other.getNeighborhood() == null)
                || (this.neighborhood != null
                && this.neighborhood.equals(other.getNeighborhood())))
                && ((this.phoneRequireServices == null && other.getPhoneRequireServices() == null)
                || (this.phoneRequireServices != null
                && this.phoneRequireServices.equals(other.getPhoneRequireServices())))
                && ((this.priority == null && other.getPriority() == null)
                || (this.priority != null
                && this.priority.equals(other.getPriority())))
                && ((this.problemDescription == null && other.getProblemDescription() == null)
                || (this.problemDescription != null
                && this.problemDescription.equals(other.getProblemDescription())))
                && ((this.reasonforService == null && other.getReasonforService() == null)
                || (this.reasonforService != null
                && this.reasonforService.equals(other.getReasonforService())))
                && ((this.requestServiceDate == null && other.getRequestServiceDate() == null)
                || (this.requestServiceDate != null
                && this.requestServiceDate.equals(other.getRequestServiceDate())))
                && ((this.requestServiceTimeHhmm == null && other.getRequestServiceTimeHhmm() == null)
                || (this.requestServiceTimeHhmm != null
                && this.requestServiceTimeHhmm.equals(other.getRequestServiceTimeHhmm())))
                && ((this.scPoints == null && other.getScPoints() == null)
                || (this.scPoints != null
                && this.scPoints.equals(other.getScPoints())))
                && ((this.secondContactNumber == null && other.getSecondContactNumber() == null)
                || (this.secondContactNumber != null
                && this.secondContactNumber.equals(other.getSecondContactNumber())))
                && ((this.sector == null && other.getSector() == null)
                || (this.sector != null
                && this.sector.equals(other.getSector())))
                && ((this.streetName == null && other.getStreetName() == null)
                || (this.streetName != null
                && this.streetName.equals(other.getStreetName())))
                && ((this.subscriberAccount == null && other.getSubscriberAccount() == null)
                || (this.subscriberAccount != null
                && this.subscriberAccount.equals(other.getSubscriberAccount())))
                && ((this.timeofCallHhmm == null && other.getTimeofCallHhmm() == null)
                || (this.timeofCallHhmm != null
                && this.timeofCallHhmm.equals(other.getTimeofCallHhmm())))
                && ((this.truckRequiredFlag == null && other.getTruckRequiredFlag() == null)
                || (this.truckRequiredFlag != null
                && this.truckRequiredFlag.equals(other.getTruckRequiredFlag())))
                && ((this.twoHouseNo == null && other.getTwoHouseNo() == null)
                || (this.twoHouseNo != null
                && this.twoHouseNo.equals(other.getTwoHouseNo())))
                && ((this.unitNumber == null && other.getUnitNumber() == null)
                || (this.unitNumber != null
                && this.unitNumber.equals(other.getUnitNumber())))
                && ((this.workForceCode == null && other.getWorkForceCode() == null)
                || (this.workForceCode != null
                && this.workForceCode.equals(other.getWorkForceCode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getApartmentNumber() != null) {
            _hashCode += getApartmentNumber().hashCode();
        }
        if (getBlock() != null) {
            _hashCode += getBlock().hashCode();
        }
        if (getBookingDate() != null) {
            _hashCode += getBookingDate().hashCode();
        }
        if (getBookingSEQ() != null) {
            _hashCode += getBookingSEQ().hashCode();
        }
        if (getBookingTimeCode() != null) {
            _hashCode += getBookingTimeCode().hashCode();
        }
        if (getBuilding() != null) {
            _hashCode += getBuilding().hashCode();
        }
        if (getCaby() != null) {
            _hashCode += getCaby().hashCode();
        }
        if (getCallReceivedBy() != null) {
            _hashCode += getCallReceivedBy().hashCode();
        }
        if (getCalledInName() != null) {
            _hashCode += getCalledInName().hashCode();
        }
        if (getCommunity() != null) {
            _hashCode += getCommunity().hashCode();
        }
        if (getContactPhoneNumber() != null) {
            _hashCode += getContactPhoneNumber().hashCode();
        }
        if (getDateofCall() != null) {
            _hashCode += getDateofCall().hashCode();
        }
        if (getDivision() != null) {
            _hashCode += getDivision().hashCode();
        }
        if (getEntrance() != null) {
            _hashCode += getEntrance().hashCode();
        }
        if (getHouseNumber() != null) {
            _hashCode += getHouseNumber().hashCode();
        }
        if (getHouseSuffix() != null) {
            _hashCode += getHouseSuffix().hashCode();
        }
        if (getN_Y_Default() != null) {
            _hashCode += getN_Y_Default().hashCode();
        }
        if (getNeighborhood() != null) {
            _hashCode += getNeighborhood().hashCode();
        }
        if (getPhoneRequireServices() != null) {
            _hashCode += getPhoneRequireServices().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getProblemDescription() != null) {
            _hashCode += getProblemDescription().hashCode();
        }
        if (getReasonforService() != null) {
            _hashCode += getReasonforService().hashCode();
        }
        if (getRequestServiceDate() != null) {
            _hashCode += getRequestServiceDate().hashCode();
        }
        if (getRequestServiceTimeHhmm() != null) {
            _hashCode += getRequestServiceTimeHhmm().hashCode();
        }
        if (getScPoints() != null) {
            _hashCode += getScPoints().hashCode();
        }
        if (getSecondContactNumber() != null) {
            _hashCode += getSecondContactNumber().hashCode();
        }
        if (getSector() != null) {
            _hashCode += getSector().hashCode();
        }
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        if (getSubscriberAccount() != null) {
            _hashCode += getSubscriberAccount().hashCode();
        }
        if (getTimeofCallHhmm() != null) {
            _hashCode += getTimeofCallHhmm().hashCode();
        }
        if (getTruckRequiredFlag() != null) {
            _hashCode += getTruckRequiredFlag().hashCode();
        }
        if (getTwoHouseNo() != null) {
            _hashCode += getTwoHouseNo().hashCode();
        }
        if (getUnitNumber() != null) {
            _hashCode += getUnitNumber().hashCode();
        }
        if (getWorkForceCode() != null) {
            _hashCode += getWorkForceCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(CreateServiceCallRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "createServiceCallRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apartmentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "apartmentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("block");
        elemField.setXmlName(new javax.xml.namespace.QName("", "block"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bookingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookingSEQ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bookingSEQ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookingTimeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bookingTimeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("building");
        elemField.setXmlName(new javax.xml.namespace.QName("", "building"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caby");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caby"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callReceivedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "callReceivedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calledInName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "calledInName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("community");
        elemField.setXmlName(new javax.xml.namespace.QName("", "community"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactPhoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contactPhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateofCall");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateofCall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("division");
        elemField.setXmlName(new javax.xml.namespace.QName("", "division"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entrance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entrance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("houseNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "houseNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("houseSuffix");
        elemField.setXmlName(new javax.xml.namespace.QName("", "houseSuffix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("n_Y_Default");
        elemField.setXmlName(new javax.xml.namespace.QName("", "n_Y_Default"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("neighborhood");
        elemField.setXmlName(new javax.xml.namespace.QName("", "neighborhood"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneRequireServices");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phoneRequireServices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("problemDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "problemDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reasonforService");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reasonforService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestServiceDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestServiceDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestServiceTimeHhmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestServiceTimeHhmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scPoints");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scPoints"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondContactNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secondContactNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sector"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriberAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subscriberAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeofCallHhmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeofCallHhmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("truckRequiredFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "truckRequiredFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("twoHouseNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "twoHouseNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workForceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "workForceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     *
     * @return
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     *
     * @param _xmlType
     * @return
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     *
     * @param _xmlType
     * @return
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(
                _javaType, _xmlType, typeDesc);
    }

}
