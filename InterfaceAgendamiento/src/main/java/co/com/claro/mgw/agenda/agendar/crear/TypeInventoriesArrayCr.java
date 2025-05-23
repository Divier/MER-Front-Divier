/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
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
 * <p>Clase Java para TypeInventoriesArrayCr complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeInventoriesArrayCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inventory" type="{http://www.amx.com.co/SIEC/ETAdi
 * rect/WS_SIEC_CrearOrdenMGWInbound}TypeInventoryElementCr" maxOccurs="unbounded"
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
@XmlType(name = "TypeInventories", propOrder = {
    "inventory"
})
public class TypeInventoriesArrayCr {

    /**
     *
     */
    public TypeInventoriesArrayCr() {
    }

    /**
     *
     * @param inventory
     */
    public TypeInventoriesArrayCr(List<TypeInventoryElementCr> inventory) {
        this.inventory = inventory;
    }
    /**
     *
     */
    protected List<TypeInventoryElementCr> inventory;

    /**
     *
     * @return List TypeInventoryElementCr
     */
    public List<TypeInventoryElementCr> getInventory() {
        if (inventory == null) {
            inventory = new ArrayList();
        }

        return inventory;
    }

    /**
     *
     * @param inventory
     */
    public void setInventory(List<TypeInventoryElementCr> inventory) {
        this.inventory = inventory;
    }

    /**
     *
     * @param c
     */
    public void add(TypeInventoryElementCr c) {
        if (this.inventory == null) {
            this.inventory = new ArrayList();
            this.inventory.add(c);
        } else {
            this.inventory.add(c);
        }
    }

}
