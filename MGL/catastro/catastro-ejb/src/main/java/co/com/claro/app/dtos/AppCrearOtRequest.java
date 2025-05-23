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
@XmlRootElement(name = "appCrearOtRequest")
public class AppCrearOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String idDirDetalladaDestino;
    @XmlElement
    private String suggestedDate;
    @XmlElement
    private String otClass;
    @XmlElement
    private String segment;
    @XmlElement
    private String observations;
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
    private String onyxOtHija;
    @XmlElement
    private List<String> newNotes;
    @XmlElement
    private String idEnlace;
    @XmlElement
    private String ordenPurpose;
    @XmlElement
    private String otSubType;

  
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

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
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

    public String getOnyxOtHija() {
        return onyxOtHija;
    }

    public void setOnyxOtHija(String onyxOtHija) {
        this.onyxOtHija = onyxOtHija;
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

    public String getIdDirDetalladaDestino() {
        return idDirDetalladaDestino;
    }

    public void setIdDirDetalladaDestino(String idDirDetalladaDestino) {
        this.idDirDetalladaDestino = idDirDetalladaDestino;
    }

    public String getOrdenPurpose() {
        return ordenPurpose;
    }

    public void setOrdenPurpose(String ordenPurpose) {
        this.ordenPurpose = ordenPurpose;
    }

    public String getOtSubType() {
        return otSubType;
    }

    public void setOtSubType(String otSubType) {
        this.otSubType = otSubType;
    }
}
