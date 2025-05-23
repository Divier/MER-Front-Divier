/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.dtos.schedule;

/**
 * Clase que encapsula los valores de la franja de un dia
 *
 * @author villamilc
 */
public class MglTimeSlotOfDay {

    private MglColumnDay associatedMglColumnDay;
    private MglSlotsOfSchedule associatedMglSlotsOfSchedule;
    private MglSchedule scheduleByHour;


    /**
     * Dia asociado
     * @return
     */
    public MglColumnDay getAssociatedMglColumnDay() {
        return associatedMglColumnDay;
    }

    /**
     * Dia asociado
     * @param associatedMglColumnDay
     */
    public void setAssociatedMglColumnDay(MglColumnDay associatedMglColumnDay) {
        this.associatedMglColumnDay = associatedMglColumnDay;
    }

    /**
     * franja de tiempo asociada
     * @return
     */
    public MglSlotsOfSchedule getAssociatedMglSlotsOfSchedule() {
        return associatedMglSlotsOfSchedule;
    }

    /**
     * franja de tiempo asociada
     * @param associatedMglSlotsOfSchedule
     */
    public void setAssociatedMglSlotsOfSchedule(MglSlotsOfSchedule associatedMglSlotsOfSchedule) {
        this.associatedMglSlotsOfSchedule = associatedMglSlotsOfSchedule;
    }

    /**
     * optine el slot .
     * @return
     */
    public MglSchedule getScheduleByHour() {
        return scheduleByHour;
    }

    /**
     * setea el  slot.
     * @param scheduleByHour
     * @params chedulesByMinute
     */
    public void setScheduleByHour(MglSchedule scheduleByHour) {
        this.scheduleByHour = scheduleByHour;
    }
}
