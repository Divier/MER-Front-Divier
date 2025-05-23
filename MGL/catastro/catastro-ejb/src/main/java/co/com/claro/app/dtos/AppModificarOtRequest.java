/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appModificarOtRequest")
public class AppModificarOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String otLocation;
    @XmlElement
    private String idOt;
    @XmlElement
    private String internaStatus;
    @XmlElement
    private String suggestedDate;
    @XmlElement
    private String otClass;
    @XmlElement
    private String contactName;
    @XmlElement
    private String contactPhone;
    @XmlElement
    private String contactEmail;
    @XmlElement
    private String generalStatus;
    @XmlElement
    private String initialRazon;
    @XmlElement
    private List<String> newNotes;
    @XmlElement
    private String idEnlace;

    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }

    public String getIdOt() {
        return idOt;
    }

    public void setIdOt(String idOt) {
        this.idOt = idOt;
    }

    public String getInternaStatus() {
        return internaStatus;
    }

    public void setInternaStatus(String internaStatus) {
        this.internaStatus = internaStatus;
    }

    public String getSuggestedDate() {
        return suggestedDate;
    }

    public void setSuggestedDate(String suggestedDate) {
        this.suggestedDate = suggestedDate;
    }

    public String getOtClass() {
        return otClass;
    }

    public void setOtClass(String otClass) {
        this.otClass = otClass;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(String generalStatus) {
        this.generalStatus = generalStatus;
    }

    public String getInitialRazon() {
        return initialRazon;
    }

    public void setInitialRazon(String initialRazon) {
        this.initialRazon = initialRazon;
    }

    public List<String> getNewNotes() {
        return newNotes;
    }

    public void setNewNotes(List<String> newNotes) {
        this.newNotes = newNotes;
    }

    public String getIdEnlace() {
        return idEnlace;
    }

    public void setIdEnlace(String idEnlace) {
        this.idEnlace = idEnlace;
    }

}
