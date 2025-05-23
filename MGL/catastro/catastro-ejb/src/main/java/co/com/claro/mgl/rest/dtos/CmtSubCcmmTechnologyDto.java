/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "subCcmmTechnology")
public class CmtSubCcmmTechnologyDto {

    @XmlElement
    private BigDecimal subCcmmTechnologyId;
    @XmlElement
    private String state;
    @XmlElement
    private String technology;
    @XmlElement
    private String qualificationDate;
    @XmlElement
    private BigDecimal subCcmmId;
    @XmlElement
    private BigDecimal ccmmId;
    @XmlElement
    private String subBuildingName;
    @XmlElement
    private String buildingName;
    @XmlElement
    private List<CmtCcmmMarksDto> listCcmmMarks;
    @XmlElement
    private CmtNodeDto tecnologyCcmmNode;
    @XmlElement
    private List<CmtOtherDirectionsDto> otherAddressList;
    @XmlElement
    private int addressesWithService;
    @XmlElement
    private String typeDistribution;
    @XmlElement
    private String buildingAddress;
    @XmlElement
    private String buildingContact;
    @XmlElement
    private String phoneContactOne;
    @XmlElement
    private String phoneContactTwo;
    @XmlElement
    private String elevatorCompany;
    @XmlElement
    private String managementCompany;
    @XmlElement
    private String emailCompany;

    public String getEmailCompany() {
        return emailCompany;
    }

    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    public BigDecimal getSubCcmmTechnologyId() {
        return subCcmmTechnologyId;
    }

    public void setSubCcmmTechnologyId(BigDecimal subCcmmTechnologyId) {
        this.subCcmmTechnologyId = subCcmmTechnologyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(String qualificationDate) {
        this.qualificationDate = qualificationDate;
    }

    public BigDecimal getSubCcmmId() {
        return subCcmmId;
    }

    public void setSubCcmmId(BigDecimal subCcmmId) {
        this.subCcmmId = subCcmmId;
    }

    public BigDecimal getCcmmId() {
        return ccmmId;
    }

    public void setCcmmId(BigDecimal ccmmId) {
        this.ccmmId = ccmmId;
    }

    public String getSubBuildingName() {
        return subBuildingName;
    }

    public void setSubBuildingName(String subBuildingName) {
        this.subBuildingName = subBuildingName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<CmtCcmmMarksDto> getListCcmmMarks() {
        return listCcmmMarks;
    }

    public void setListCcmmMarks(List<CmtCcmmMarksDto> listCcmmMarks) {
        this.listCcmmMarks = listCcmmMarks;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public CmtNodeDto getTecnologyCcmmNode() {
        return tecnologyCcmmNode;
    }

    public void setTecnologyCcmmNode(CmtNodeDto tecnologyCcmmNode) {
        this.tecnologyCcmmNode = tecnologyCcmmNode;
    }

    public List<CmtOtherDirectionsDto> getOtherAddressList() {
        return otherAddressList;
    }

    public void setOtherAddressList(List<CmtOtherDirectionsDto> otherAddressList) {
        this.otherAddressList = otherAddressList;
    }

    public int getAddressesWithService() {
        return addressesWithService;
    }

    public void setAddressesWithService(int addressesWithService) {
        this.addressesWithService = addressesWithService;
    }

    public String getTypeDistribution() {
        return typeDistribution;
    }

    public void setTypeDistribution(String typeDistribution) {
        this.typeDistribution = typeDistribution;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getBuildingContact() {
        return buildingContact;
    }

    public void setBuildingContact(String buildingContact) {
        this.buildingContact = buildingContact;
    }

    public String getPhoneContactOne() {
        return phoneContactOne;
    }

    public void setPhoneContactOne(String phoneContactOne) {
        this.phoneContactOne = phoneContactOne;
    }

    public String getPhoneContactTwo() {
        return phoneContactTwo;
    }

    public void setPhoneContactTwo(String phoneContactTwo) {
        this.phoneContactTwo = phoneContactTwo;
    }

    public String getElevatorCompany() {
        return elevatorCompany;
    }

    public void setElevatorCompany(String elevatorCompany) {
        this.elevatorCompany = elevatorCompany;
    }

    public String getManagementCompany() {
        return managementCompany;
    }

    public void setManagementCompany(String managementCompany) {
        this.managementCompany = managementCompany;
    }

}
