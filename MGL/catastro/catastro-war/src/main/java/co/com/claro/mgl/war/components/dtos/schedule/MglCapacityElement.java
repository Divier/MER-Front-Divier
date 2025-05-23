/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgl.war.components.dtos.schedule;

import java.util.Date;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

public class MglCapacityElement {

    /**
     *
     */
    protected String location;
    /**
     *
     */
    protected Date date;
    /**
     *
     */
    protected String timeSlot;
    /**
     *
     */
    protected String workSkill;
    /**
     *
     */
    protected long quota;
    /**
     *
     */
    protected long available;
    /**
     *
     */
    protected String bucket;
    /**
     *
     */
    protected List<MglCapacityRestrictionElement> restriction;
    
    /**
     *
     */
    protected boolean used;

    /**
     * Obtiene el valor de la propiedad location.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLocation() {
        return location;
    }

    /**
     * Define el valor de la propiedad location.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtiene el valor de la propiedad date.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public Date getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad timeSlot.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Define el valor de la propiedad timeSlot.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTimeSlot(String value) {
        this.timeSlot = value;
    }

    /**
     * Obtiene el valor de la propiedad workSkill.
     *
     * @return possible object is {@link String }
     *
     */
    public String getWorkSkill() {
        return workSkill;
    }

    /**
     * Define el valor de la propiedad workSkill.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setWorkSkill(String value) {
        this.workSkill = value;
    }

    /**
     * Obtiene el valor de la propiedad quota.
     *
     * @return
     */
    public long getQuota() {
        return quota;
    }

    /**
     * Define el valor de la propiedad quota.
     *
     * @param value
     */
    public void setQuota(long value) {
        this.quota = value;
    }
    /**
     * Define el valor de la propiedad bucket.
     *
     */
    public String getBucket() {
        return bucket;
    }
    /**
     * Define el valor de la propiedad bucket.
     *
     * @param bucket
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * Obtiene el valor de la propiedad available.
     *
     * @return
     */
    public long getAvailable() {
        return available;
    }

    /**
     * Define el valor de la propiedad available.
     *
     * @param value
     */
    public void setAvailable(long value) {
        this.available = value;
    }

    /**
     *
     * @return List CapacityRestrictionElement
     */
    public List<MglCapacityRestrictionElement> getRestriction() {
        return restriction;
    }

    /**
     *
     * @param restriction
     */
    public void setRestriction(List<MglCapacityRestrictionElement> restriction) {
        this.restriction = restriction;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    															 
    public String getRestrictionText() {
        if (restriction != null && !restriction.isEmpty()) {
            String result = "";
            for (MglCapacityRestrictionElement restrictionElement : restriction) {
                result += restrictionElement.getDescription() + "\n";
            }
            return result;
        } else {

            return "";
        }

    }
}
