/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.shareddata;

import co.com.claro.mgw.agenda.reagendar.TypeDataElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para MgwTypeReAgendarRequestElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="MgwTypeReAgendarRequestElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_RealizarReagendacionMGWInbound}MgwUserElement"/&gt;
 *         &lt;element name="head" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_RealizarReagendacionMGWInbound}MgwHeadElement"/&gt;
 *         &lt;element name="data" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_RealizarReagendacionMGWInbound}TypeDataElement"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInboundInterfaceElement", propOrder = {
    "user",
    "head",
    "data"
})
public class MgwTypeReAgendarRequestElement {

    /**
     *
     */
    public MgwTypeReAgendarRequestElement() {
    }

    /**
     *
     * @param user
     * @param head
     * @param data
     */
    public MgwTypeReAgendarRequestElement(MgwUserElement user, MgwHeadElement head, TypeDataElement data) {
        this.user = user;
        this.head = head;
        this.data = data;
    }
    /**
     *
     */
    @XmlElement(required = true)
    protected MgwUserElement user;
    /**
     *
     */
    @XmlElement(required = true)
    protected MgwHeadElement head;
    /**
     *
     */
    @XmlElement(required = true)
    protected TypeDataElement data;

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is {@link MgwUserElement }
     *
     */
    public MgwUserElement getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is {@link MgwUserElement }
     *
     */
    public void setUser(MgwUserElement value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad head.
     *
     * @return possible object is {@link MgwHeadElement }
     *
     */
    public MgwHeadElement getHead() {
        return head;
    }

    /**
     * Define el valor de la propiedad head.
     *
     * @param value allowed object is {@link MgwHeadElement }
     *
     */
    public void setHead(MgwHeadElement value) {
        this.head = value;
    }

    /**
     * Obtiene el valor de la propiedad data.
     *
     * @return possible object is {@link TypeDataElement }
     *
     */
    public TypeDataElement getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     *
     * @param value allowed object is {@link TypeDataElement }
     *
     */
    public void setData(TypeDataElement value) {
        this.data = value;
    }

}
