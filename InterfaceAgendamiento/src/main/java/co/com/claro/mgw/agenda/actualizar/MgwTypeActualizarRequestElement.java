/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author eloaizao-everis
 * 
 */
package co.com.claro.mgw.agenda.actualizar;

import co.com.claro.mgw.agenda.shareddata.MgwHeadElement;
import co.com.claro.mgw.agenda.shareddata.MgwUserElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para MgwTypeActualizarRequestElement complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="MgwTypeAgendarRequestElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_CrearOrdenMGWInbound}TypeUserElement"/&gt;
 *         &lt;element name="head" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_CrearOrdenMGWInbound}TypeHeadElement"/&gt;
 *         &lt;element name="data" type="{http://www.amx.com.co/SIEC/ETAdirect/
 WS_SIEC_CrearOrdenMGWInbound}TypeDataElementUp"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInboundInterfaceElement", propOrder =
{
    "user",
    "head",
    "data"
})
public class MgwTypeActualizarRequestElement
{
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
    protected TypeDataElementUp data;

    /**
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link MgwUserElement }
     *     
     */
    public MgwUserElement getUser()
	{
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link MgwUserElement }
     *     
     */
    public void setUser(MgwUserElement value)
	{
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad head.
     * 
     * @return
     *     possible object is
     *     {@link MgwHeadElement }
     *     
     */
    public MgwHeadElement getHead()
	{
        return head;
    }

    /**
     * Define el valor de la propiedad head.
     * 
     * @param value
     *     allowed object is
     *     {@link MgwHeadElement }
     *     
     */
    public void setHead(MgwHeadElement value)
	{
        this.head = value;
    }

    /**
     * Obtiene el valor de la propiedad data.
     * 
     * @return
     *     possible object is
     *     {@link TypeDataElementUp }
     *     
     */
    public TypeDataElementUp getData()
    {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDataElementUp }
     *     
     */
    public void setData(TypeDataElementUp value)
	{
        this.data = value;
    }
}
