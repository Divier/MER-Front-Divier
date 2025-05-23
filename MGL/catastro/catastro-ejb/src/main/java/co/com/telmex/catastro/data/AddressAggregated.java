package co.com.telmex.catastro.data;

import java.io.Serializable;

/**
 * Representa las Subdirecciones agregadas a una dirección
 * implementa Serialización
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class AddressAggregated implements Serializable {

    private String address = null;
    private String idSubAddress = null;

    /** Retorna la Dirección.
     * @return Cadena con la dirección
     */
    public String getAddress() {
        return address;
    }

    /** Establece la dirección.
     * @param address Cadena con la dirección
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Retorna el Id de Subdirección.
     * @return Cadena con el Id de la subdirección
     */
    public String getIdSubAddress() {
        return idSubAddress;
    }

    /** Establece el Id de Subdirección.
     * @param idSubAddress Id de la subdirección
     */
    public void setIdSubAddress(String idSubAddress) {
        this.idSubAddress = idSubAddress;
    }

    /** 
     * Sobre escritura del método toString().  
     * @return Cadena con las direcciones sugeridas.
     */
    @Override
    public String toString() {
        return "AddressSuggested{" + "Dirección=" + address + ", Barrio=" + idSubAddress + '}';
    }
}
