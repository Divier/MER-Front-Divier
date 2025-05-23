/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.restrition_ccmm;

import co.com.claro.mgw.agenda.user.UserAut;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para unrealizedActivityRequest complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="unrealizedActivityRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user"
 *          type="{http://ws.ordermanager.globalhitss.com.co/}userX"/&gt;
 *         &lt;element name="idMensaje"
 *          type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="orden"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idTecnico"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="razon"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "restritionCcmmRequest", propOrder = {
    "user",
    "hhppId",
    "subedificioId"
})
public class RestritionCcmmRequest {

    @XmlElement(required = true)
    protected UserAut user;
    @XmlElement
    private BigDecimal hhppId;
    @XmlElement
    private BigDecimal subedificioId;

    public RestritionCcmmRequest() {
    }

    public RestritionCcmmRequest(
            UserAut user, BigDecimal hhppId, BigDecimal subedificioId) {
        this.user = user;
        this.hhppId = hhppId;
        this.subedificioId = subedificioId;
    }

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is {@link UserAut }
     *
     */
    public UserAut getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is {@link UserAut }
     *
     */
    public void setUser(UserAut value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad hhppId.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getHhppId() {
        return hhppId;
    }

    /**
     * Define el valor de la propiedad hhppId.
     *
     * @param hhppId
     *
     */
    public void setHhppId(BigDecimal hhppId) {
        this.hhppId = hhppId;
    }

    /**
     * Obtiene el valor de la propiedad subedificioId.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getSubedificioId() {
        return subedificioId;
    }

    /**
     * Define el valor de la propiedad subedificioId.
     *
     * @param subedificioId
     *
     */
    public void setSubedificioId(BigDecimal subedificioId) {
        this.subedificioId = subedificioId;
    }
}
