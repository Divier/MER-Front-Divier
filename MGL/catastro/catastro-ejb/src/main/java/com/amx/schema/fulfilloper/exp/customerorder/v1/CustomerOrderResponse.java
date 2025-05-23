
package com.amx.schema.fulfilloper.exp.customerorder.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cursor" type="{http://www.amx.com/schema/FulFillOper/EXP/CustomerOrder/v1.0}RowSet" minOccurs="0"/>
 *         &lt;element name="codTransaction" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cursor",
    "codTransaction",
    "message"
})
@XmlRootElement(name = "CustomerOrderResponse")
public class CustomerOrderResponse {

    protected RowSet cursor;
    protected Integer codTransaction;
    protected String message;

    /**
     * Obtiene el valor de la propiedad cursor.
     * 
     * @return
     *     possible object is
     *     {@link RowSet }
     *     
     */
    public RowSet getCursor() {
        return cursor;
    }

    /**
     * Define el valor de la propiedad cursor.
     * 
     * @param value
     *     allowed object is
     *     {@link RowSet }
     *     
     */
    public void setCursor(RowSet value) {
        this.cursor = value;
    }

    /**
     * Obtiene el valor de la propiedad codTransaction.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodTransaction() {
        return codTransaction;
    }

    /**
     * Define el valor de la propiedad codTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodTransaction(Integer value) {
        this.codTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
