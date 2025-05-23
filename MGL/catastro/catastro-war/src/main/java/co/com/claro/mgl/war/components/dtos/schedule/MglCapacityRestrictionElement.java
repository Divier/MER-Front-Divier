/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgl.war.components.dtos.schedule;

/**
 *
 * @author imartipe
 */
public class MglCapacityRestrictionElement {

    /**
     *
     */
    public MglCapacityRestrictionElement() {
    }

    /**
     *
     * @param code
     * @param type
     * @param description
     */
    public MglCapacityRestrictionElement(String code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }
    /**
     *
     */
    protected String code;
    /**
     *
     */
    protected String type;
    /**
     *
     */
    protected String description;

    /**
     *
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
