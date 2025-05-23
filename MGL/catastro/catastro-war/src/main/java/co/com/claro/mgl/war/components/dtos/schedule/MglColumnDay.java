/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.dtos.schedule;

import java.util.List;

/**
 * Clase que encapsula los valores de una agenda diaria
 *
 * @author villamilc
 */
public class MglColumnDay {

    private String dayDate;
    private String weekNameDay;
    private List<MglTimeSlotOfDay> mglHourOfDayList;
    private long totalQuota;
    private long totalAvailable;
    /**
     * Dia de agenda
     *
     * @return
     */
    public String getDayDate() {
        return dayDate;
    }

    /**
     * Dia de agenda
     *
     * @param dayDate
     */
    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    /**
     * nombre Dia semana de agenda
     *
     * @return
     */
    public String getWeekNameDay() {
        return weekNameDay;
    }

    /**
     * nombre Dia semana de agenda
     *
     * @param weekNameDay
     */
    public void setWeekNameDay(String weekNameDay) {
        this.weekNameDay = weekNameDay;
    }

    
    
    /**
     * minutos del dia para agenda
     *
     * @return
     */
    public List<MglTimeSlotOfDay> getMglHourOfDayList() {
        return mglHourOfDayList;
    }

    /**
     * minutos del dia para agenda
     *
     * @param mglHourOfDayList
     */
    public void setMglHourOfDayList(List<MglTimeSlotOfDay> mglHourOfDayList) {
        this.mglHourOfDayList = mglHourOfDayList;
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
