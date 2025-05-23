/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.softclose;

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
 * <p>Clase Java para softCloseRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera 
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="softCloseRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" 
 *          type="{http://ws.ordermanager.globalhitss.com.co/}userAut"/&gt;
 *         &lt;element name="idMensaje" 
 *          type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="appt_number" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="origenId" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iims1" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iims2"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="iims3"
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tecnicoId" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="actividades" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cambios" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="observacion" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="razon" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="respuestaEncuesta" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="im" 
 *          type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softCloseRequest", propOrder = {
    "user",
    "idMensaje",
    "apptNumber",
    "origenId",
    "iims1",
    "iims2",
    "iims3",
    "tecnicoId",
    "actividades",
    "cambios",
    "observacion",
    "razon",
    "respuestaEncuesta",
    "im"
})
public class SoftCloseRequest {

    @XmlElement(required = true)
    protected UserAut user;
    protected int idMensaje;
    @XmlElement(name = "appt_number", required = true)
    protected String apptNumber;
    @XmlElement(required = true)
    protected String origenId;
    protected String iims1;
    protected String iims2;
    protected String iims3;
    @XmlElement(required = true)
    protected String tecnicoId;
    protected String actividades;
    protected String cambios;
    protected String observacion;
    protected String razon;
    protected String respuestaEncuesta;
    protected String im;

    public SoftCloseRequest()
    {}

    public SoftCloseRequest
    (
        UserAut user, int idMensaje, String apptNumber,
        String origenId, String iims1, String iims2, String iims3,
        String tecnicoId, String actividades, String cambios,
        String observacion, String razon,
        String respuestaEncuesta, String im
    )
    {
        this.user = user;
        this.idMensaje = idMensaje;
        this.apptNumber = apptNumber;
        this.origenId = origenId;
        this.iims1 = iims1;
        this.iims2 = iims2;
        this.iims3 = iims3;
        this.tecnicoId = tecnicoId;
        this.actividades = actividades;
        this.cambios = cambios;
        this.observacion = observacion;
        this.razon = razon;
        this.respuestaEncuesta = respuestaEncuesta;
        this.im = im;
    }


    
    /**
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link UserAut }
     *     
     */
    public UserAut getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAut }
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
     * Obtiene el valor de la propiedad apptNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApptNumber() {
        return apptNumber;
    }

    /**
     * Define el valor de la propiedad apptNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApptNumber(String value) {
        this.apptNumber = value;
    }

    /**
     * Obtiene el valor de la propiedad origenId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenId() {
        return origenId;
    }

    /**
     * Define el valor de la propiedad origenId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenId(String value) {
        this.origenId = value;
    }

    /**
     * Obtiene el valor de la propiedad iims1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIims1() {
        return iims1;
    }

    /**
     * Define el valor de la propiedad iims1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIims1(String value) {
        this.iims1 = value;
    }

    /**
     * Obtiene el valor de la propiedad iims2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIims2() {
        return iims2;
    }

    /**
     * Define el valor de la propiedad iims2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIims2(String value) {
        this.iims2 = value;
    }

    /**
     * Obtiene el valor de la propiedad iims3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIims3() {
        return iims3;
    }

    /**
     * Define el valor de la propiedad iims3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIims3(String value) {
        this.iims3 = value;
    }

    /**
     * Obtiene el valor de la propiedad tecnicoId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTecnicoId() {
        return tecnicoId;
    }

    /**
     * Define el valor de la propiedad tecnicoId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTecnicoId(String value) {
        this.tecnicoId = value;
    }

    /**
     * Obtiene el valor de la propiedad actividades.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActividades() {
        return actividades;
    }

    /**
     * Define el valor de la propiedad actividades.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActividades(String value) {
        this.actividades = value;
    }

    /**
     * Obtiene el valor de la propiedad cambios.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCambios() {
        return cambios;
    }

    /**
     * Define el valor de la propiedad cambios.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCambios(String value) {
        this.cambios = value;
    }

    /**
     * Obtiene el valor de la propiedad observacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Define el valor de la propiedad observacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservacion(String value) {
        this.observacion = value;
    }

    /**
     * Obtiene el valor de la propiedad razon.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazon() {
        return razon;
    }

    /**
     * Define el valor de la propiedad razon.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazon(String value) {
        this.razon = value;
    }

    /**
     * Obtiene el valor de la propiedad respuestaEncuesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespuestaEncuesta() {
        return respuestaEncuesta;
    }

    /**
     * Define el valor de la propiedad respuestaEncuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespuestaEncuesta(String value) {
        this.respuestaEncuesta = value;
    }

    /**
     * Obtiene el valor de la propiedad im.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIm() {
        return im;
    }

    /**
     * Define el valor de la propiedad im.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIm(String value) {
        this.im = value;
    }

}
