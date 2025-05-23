package co.com.telmex.catastro.data;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase AddressSuggested
 * Representa la dirección sugerida de una dirección solicitada.
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
@XmlRootElement
public class AddressSuggested implements Serializable {

    @XmlElement
    private String address = null;
    @XmlElement
    private String neighborhood = null;

    /**
     * 
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * 
     * @param neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public String toString() {
        return "AddressSuggested{" + "Dirección=" + address + ", Barrio=" + neighborhood + '}';
    }
}
