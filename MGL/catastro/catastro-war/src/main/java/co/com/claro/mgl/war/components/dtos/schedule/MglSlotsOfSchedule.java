/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.dtos.schedule;

/**
 * Clase que encapsula los valores de franja posibles
 *
 * @author villamilc
 */
public class MglSlotsOfSchedule {

    private String timeSlot;
    private int startHour;
    private int endHour;
    private int slotPotition;
    private long totalQuota;
    private long totalAvailable;
    /**
     * Encapsula Hora
     *
     * @return String
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Encapsula Hora
     *
     * @param timeSlot
     */
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Encapsula Hora
     *
     * @return String
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * Encapsula Hora
     *
     * @param startHour
     */
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getSlotPotition() {
        return slotPotition;
    }

    public void setSlotPotition(int slotPotition) {
        this.slotPotition = slotPotition;
    }

    public long getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(long totalQuota) {
        this.totalQuota = totalQuota;
    }

    public long getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(long totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

}
