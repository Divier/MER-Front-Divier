/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.agendar.crear;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeInventoriesArray complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeInventoriesArray"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inventory" type="{http://www.amx.com.co/SIEC/ETAdi
 * rect/WS_SIEC_CrearOrdenMGWInbound}TypeInventoryElement" maxOccurs="unbounded"
 * /&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInventoriesData", propOrder = {
    "inventory"
})
public class TypeInventoriesArrayDataCr {

    /**
     *
     */
    protected List<TypeInventoryReportElementCr> inventory;

    /**
     *
     * @return List TypeInventoryReportElement
     */
    public List<TypeInventoryReportElementCr> getInventory() {
        if (inventory == null) {
            inventory = new ArrayList();
        }

        return inventory;
    }

    /**
     *
     * @param inventory
     */
    public void setInventory(List<TypeInventoryReportElementCr> inventory) {
        this.inventory = inventory;
    }
}
