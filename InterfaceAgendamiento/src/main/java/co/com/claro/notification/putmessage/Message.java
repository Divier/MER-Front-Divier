/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.notification.putmessage;

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message_element", propOrder = {
    "pushType",
    "typeCostumer",
    "customerId",
    "messageBox",
    "profileId",
    "communicationType",
    "communicationOrigin",
    "deliveryReceipts",
    "contentType",
    "messageContent",
    "variableMap",
    "subject"
})
public class Message {

    @XmlElement()
    protected String pushType;

    @XmlElement()
    protected String typeCostumer;

    @XmlElement
    protected List<String> customerId;
    
    @XmlElement()
    protected List<MessageBox1> messageBox;

    @XmlElement
    protected List<String> profileId;

    @XmlElement
    protected List<String> communicationType;

    @XmlElement
    protected String communicationOrigin;

    @XmlElement
    protected String deliveryReceipts;

    @XmlElement
    protected String contentType;

    @XmlElement
    protected String messageContent;

    @XmlElement
    public Map<String, String> variableMap;

    @XmlElement
    protected String subject;
    
    @XmlElement
    protected List<Attach> attach;

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getTypeCostumer() {
        return typeCostumer;
    }

    public void setTypeCostumer(String typeCostumer) {
        this.typeCostumer = typeCostumer;
    }

    public List<String> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(List<String> customerId) {
        this.customerId = customerId;
    }

    public List<MessageBox1> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(List<MessageBox1> messageBox) {
        this.messageBox = messageBox;
    }

    public List<String> getProfileId() {
        return profileId;
    }

    public void setProfileId(List<String> profileId) {
        this.profileId = profileId;
    }

    public List<String> getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(List<String> communicationType) {
        this.communicationType = communicationType;
    }

    public String getCommunicationOrigin() {
        return communicationOrigin;
    }

    public void setCommunicationOrigin(String communicationOrigin) {
        this.communicationOrigin = communicationOrigin;
    }

    public String getDeliveryReceipts() {
        return deliveryReceipts;
    }

    public void setDeliveryReceipts(String deliveryReceipts) {
        this.deliveryReceipts = deliveryReceipts;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Map<String, String> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(Map<String, String> variableMap) {
        this.variableMap = variableMap;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Attach> getAttach() {
        return attach;
    }

    public void setAttach(List<Attach> attach) {
        this.attach = attach;
    }
}
