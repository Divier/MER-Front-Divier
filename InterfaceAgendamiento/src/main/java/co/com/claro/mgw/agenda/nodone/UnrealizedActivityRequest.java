/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.nodone;

/**
 *
 * @author bocanegravm
 */
import co.com.claro.mgw.agenda.user.UserAut;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unrealizedActivityRequest", propOrder = {
    "user",
    "idMensaje",
    "orden",
    "idTecnico",
    "razon",
    "observaciones"
})
public class UnrealizedActivityRequest {

    @XmlElement(required = true)
    protected UserAut user;
    protected int idMensaje;
    @XmlElement(required = true)
    protected String orden;
    @XmlElement(required = true)
    protected String idTecnico;
    @XmlElement(required = true)
    protected String razon;
    @XmlElement(required = true)
    protected String observaciones;

    public UnrealizedActivityRequest() {
    }

    public UnrealizedActivityRequest(
            UserAut user, int idMensaje, String orden,
            String idTecnico, String razon, String observaciones) {
        this.user = user;
        this.idMensaje = idMensaje;
        this.orden = orden;
        this.idTecnico = idTecnico;
        this.razon = razon;
        this.observaciones = observaciones;
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
     * Obtiene el valor de la propiedad idMensaje.
     *
     * @return
     */
    public int getIdMensaje() {
        return idMensaje;
    }

    /**
     * Define el valor de la propiedad idMensaje.
     *
     * @param value
     */
    public void setIdMensaje(int value) {
        this.idMensaje = value;
    }

    /**
     * Obtiene el valor de la propiedad orden.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOrden() {
        return orden;
    }

    /**
     * Define el valor de la propiedad orden.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOrden(String value) {
        this.orden = value;
    }

    /**
     * Obtiene el valor de la propiedad idTecnico.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdTecnico() {
        return idTecnico;
    }

    /**
     * Define el valor de la propiedad idTecnico.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdTecnico(String value) {
        this.idTecnico = value;
    }

    /**
     * Obtiene el valor de la propiedad razon.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRazon() {
        return razon;
    }

    /**
     * Define el valor de la propiedad razon.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRazon(String value) {
        this.razon = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     *
     * @return possible object is {@link String }
     *
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     *
     * @param observaciones
     *
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
