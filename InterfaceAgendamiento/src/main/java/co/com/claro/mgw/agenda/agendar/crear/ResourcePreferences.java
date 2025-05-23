/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.agendar.crear;

/**
 *
 * @author bocanegravm
 */
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para TypeDataElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeDataElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.amx.com.co/SIEC/ETAdi
 * rect/WS_SIEC_CrearOrdenMGWInbound}TypeCommandsArrayCr" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourcePreferences", propOrder = {
    "resourcePreferences"
})
public class ResourcePreferences {

    /**
     *
     */
    public ResourcePreferences() {
    }

    /**
     *
     */
    @XmlElement(required = true)
    protected List<TypeItemElementCr> items;
    
    

    public List<TypeItemElementCr> getItems() {
        return items;
    }

    public void setItems(List<TypeItemElementCr> items) {
        this.items = items;
    }
    
}
