package co.com.telmex.catastro.data;

import java.io.Serializable;

/**
 * Clase AddressRequest
 * Representa Solicitud de direcciones.
 * implementa Serialización
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class AddressRequest implements Serializable {

    private String id = "";
    private String address = "";
    private String city = "";
    private String neighborhood = "";
    //Se agrega el nuevo atributo
    private String state = "";
    private String level = "";
    private String codDaneVt = "";
    private String tipoDireccion = "";

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
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Get the value of codDaneVt
     *
     * @return the value of codDaneVt
     */
    public String getCodDaneVt() {
        return codDaneVt;
    }

    /**
     * Set the value of codDaneVt
     *
     * @param codDaneVt new value of codDaneVt
     */
    public void setCodDaneVt(String codDaneVt) {
        this.codDaneVt = codDaneVt;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }
    
    
}
