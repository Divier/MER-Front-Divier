/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.agendar.crear;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para TypeMessageResponseElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeMessageResponseElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLS
 * chema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeMessageResponseElement", propOrder
        = {
            "result",
            "code",
            "action",
            "step",
            "type",
            "description",
            "clase",
            "line",
            "detail"
        })
public class TypeMessageResponseElementCr {

    /**
     *
     */
    public TypeMessageResponseElementCr() {
    }

    /**
     *
     * @param result
     * @param type
     * @param code
     * @param description
     */
    public TypeMessageResponseElementCr(String result, String type, String code,
            String description,String clase,String line,String detail) {
        this.result = result;
        this.type = type;
        this.code = code;
        this.description = description;
        this.clase = clase;
        this.line = line;
        this.detail = detail;
    }

    /**
     *
     */
    @XmlElement(required = false)
    protected String result;

    /**
     *
     */
    @XmlElement(required = false)
    protected String type;

    /**
     *
     */
    @XmlElement(required = false)
    protected String code;

    /**
     *
     */
    @XmlElement(required = false)
    protected String description;

    /**
     *
     */
    @XmlElement(required = false)
    protected String action;

    /**
     *
     */
    @XmlElement(required = false)
    protected String step;

    /**
     *
     */
    @XmlElement(required = false)
    protected String clase;

    /**
     *
     */
    @XmlElement(required = false)
    protected String line;

    /**
     *
     */
    @XmlElement(required = false)
    protected String detail;

    /**
     * Obtiene el valor de la propiedad result.
     *
     * @return possible object is {@link String }
     *
     */
    public String getResult() {
        return result;
    }

    /**
     * Define el valor de la propiedad result.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     *
     * @return possible object is {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad code.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad action.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAction() {
        return action;
    }

    /**
     * Define el valor de la propiedad action.
     *
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Obtiene el valor de la propiedad step.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStep() {
        return step;
    }

    /**
     * Define el valor de la propiedad step.
     *
     * @param step
     *
     */
    public void setStep(String step) {
        this.step = step;
    }

    /**
     * Obtiene el valor de la propiedad clase.
     *
     * @return possible object is {@link String }
     *
     */
    public String getClase() {
        return clase;
    }

    /**
     * Define el valor de la propiedad clase.
     *
     * @param clase
     *
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * Obtiene el valor de la propiedad line.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLine() {
        return line;
    }

    /**
     * Define el valor de la propiedad line.
     *
     * @param line
     *
     */
    public void setLine(String line) {
        this.line = line;
    }

    /**
     * Obtiene el valor de la propiedad detail.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Define el valor de la propiedad detail.
     *
     * @param detail
     *
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
