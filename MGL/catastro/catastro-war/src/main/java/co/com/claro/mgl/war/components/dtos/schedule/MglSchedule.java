/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.dtos.schedule;

/**
 * Clase que encapsula la informacion de una agenda visualmente
 *
 * @author villamilc
 */
public class MglSchedule {

    private boolean scheduled;
    private MglCapacityElement capacityElement;



    /**
     * Indicador de agendado
     *
     * @return
     */
    public boolean getScheduled() {
        return scheduled;
    }

    /**
     * Indicador de agendado
     *
     * @param scheduled
     */
    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    /**
     * Indicador de agendado
     *
     * @return
     */
    public MglCapacityElement getCapacityElement() {
        return capacityElement;
    }

    /**
     * elemto de agenda
     *
     * @param capacityElement
     */
    public void setCapacityElement(MglCapacityElement capacityElement) {
        this.capacityElement = capacityElement;
    }
}
