package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para updateCaseStatusType complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="updateCaseStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caseId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caseReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caseIdMGL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caseStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caseCompleted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCaseStatusType", namespace = "http://www.ericsson.com/esb/message/customer/updateCaseStatusRequest/v1.0", propOrder = {
    "caseId",
    "caseReason",
    "caseIdMGL",
    "caseStatus",
    "caseCompleted"
})
public class UpdateCaseStatusType implements Serializable {

    @XmlElement(required = true)
    protected String caseId;
    @XmlElement(required = true)
    protected String caseReason;
    @XmlElement(required = true)
    protected String caseIdMGL;
    @XmlElement(required = true)
    protected String caseStatus;
    @XmlElement(required = true)
    protected String caseCompleted;

    /**
     * Obtiene el valor de la propiedad caseId.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Define el valor de la propiedad caseId.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCaseId(String value) {
        this.caseId = value;
    }

    /**
     * Obtiene el valor de la propiedad caseReason.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCaseReason() {
        return caseReason;
    }

    /**
     * Define el valor de la propiedad caseReason.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCaseReason(String value) {
        this.caseReason = value;
    }

    /**
     * Obtiene el valor de la propiedad caseIdMGL.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCaseIdMGL() {
        return caseIdMGL;
    }

    /**
     * Define el valor de la propiedad caseIdMGL.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCaseIdMGL(String value) {
        this.caseIdMGL = value;
    }

    /**
     * Obtiene el valor de la propiedad caseStatus.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCaseStatus() {
        return caseStatus;
    }

    /**
     * Define el valor de la propiedad caseStatus.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCaseStatus(String value) {
        this.caseStatus = value;
    }

    /**
     * Obtiene el valor de la propiedad caseCompleted.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCaseCompleted() {
        return caseCompleted;
    }

    /**
     * Define el valor de la propiedad caseCompleted.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCaseCompleted(String value) {
        this.caseCompleted = value;
    }
}
