/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.dtos.schedule;

import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import co.com.claro.mgl.dtos.RestriccionAgendaDto;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula el calendario de agendamiento
 *
 * @author villamilc
 */
public class MglScheduleComponetDto {

    private List<MglColumnDay> mglColumnDayList = new ArrayList<MglColumnDay>();
    private List<MglSlotsOfSchedule> mglHoursOfDayList = new ArrayList<MglSlotsOfSchedule>();
    private int firstDayToShow = 0;
    private boolean hasError=false;
    private String errorMessage="";
    private List<MglSchedule> mglSchedulesSelected=new ArrayList<MglSchedule>();
    private List<CapacidadAgendaDto> capacidadAgendaDtos = new ArrayList<CapacidadAgendaDto>();    
    

    /**
     * Lista de culumnas de dia de el calendario
     *
     * @return List<MglColumnDay>
     */
    public List<MglColumnDay> getMglColumnDayList() {
        return mglColumnDayList;
    }

    /**
     * Lista de culumnas de dia de el calendario
     *
     * @param mglColumnDayList
     */
    public void setMglColumnDayList(List<MglColumnDay> mglColumnDayList) {
        this.mglColumnDayList = mglColumnDayList;
    }

    /**
     * Slot temporales de la agenda
     *
     * @return
     */
    public List<MglSlotsOfSchedule> getMglHoursOfDayList() {
        return mglHoursOfDayList;
    }

    /**
     * Slot temporales de la agenda
     *
     * @param mglHoursOfDayList
     */
    public void setMglHoursOfDayList(List<MglSlotsOfSchedule> mglHoursOfDayList) {
        this.mglHoursOfDayList = mglHoursOfDayList;
    }

    /**
     * Primer dia a mostrar en el calendario
     *
     * @return int
     */
    public int getFirstDayToShow() {
        return firstDayToShow;
    }

    /**
     * Primer dia a mostrar en el calendario
     *
     * @param firstDayToShow
     */
    public void setFirstDayToShow(int firstDayToShow) {
        this.firstDayToShow = firstDayToShow;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<MglSchedule> getMglSchedulesSelected() {
        return mglSchedulesSelected;
    }

    public void setMglSchedulesSelected(List<MglSchedule> mglSchedulesSelected) {
        this.mglSchedulesSelected = mglSchedulesSelected;
    }
    
    public List<CapacidadAgendaDto> getCapacidadAgendaDtos() {

        capacidadAgendaDtos = new ArrayList<>();
        
        if (mglSchedulesSelected.size() > 0) {

            for (MglSchedule mglSchedule : mglSchedulesSelected) {
                if (mglSchedule.getScheduled()) {
                    CapacidadAgendaDto capacidadAgendaDto = new CapacidadAgendaDto();
                    MglCapacityElement mglCapacityElement = mglSchedule.getCapacityElement();
                    capacidadAgendaDto.setDate(mglCapacityElement.getDate());
                    capacidadAgendaDto.setWorkSkill(mglCapacityElement.getWorkSkill());
                    Long available = mglCapacityElement.getAvailable();
                    capacidadAgendaDto.setBucket(mglCapacityElement.getBucket());
                    int aval = available.intValue();
                    capacidadAgendaDto.setAvailable(aval);
                    capacidadAgendaDto.setTimeSlot(mglCapacityElement.getTimeSlot());
                    Long quota = mglCapacityElement.getQuota();
                    int quo = quota.intValue();
                    capacidadAgendaDto.setQuota(quo);
                    if (mglCapacityElement.getRestriction() != null) {
                        if (mglCapacityElement.getRestriction().size() > 0) {
                            List<RestriccionAgendaDto> restricciones = new ArrayList<>();
                            for (MglCapacityRestrictionElement mglCapacityRestrictionElement
                                    : mglCapacityElement.getRestriction()) {
                                RestriccionAgendaDto restriccionAgendaDto = new RestriccionAgendaDto();
                                restriccionAgendaDto.setCode(mglCapacityRestrictionElement.getCode());
                                restriccionAgendaDto.setDescription(mglCapacityRestrictionElement.getDescription());
                                restriccionAgendaDto.setType(mglCapacityRestrictionElement.getType());
                                restricciones.add(restriccionAgendaDto);
}
                            capacidadAgendaDto.setRestricciones(restricciones);
                        }
                    }
                    capacidadAgendaDtos.add(capacidadAgendaDto);
                }

            }

        }
        return capacidadAgendaDtos;
    }

    public void setCapacidadAgendaDtos(List<CapacidadAgendaDto> capacidadAgendaDtos) {
        this.capacidadAgendaDtos = capacidadAgendaDtos;
    }
}
