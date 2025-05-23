/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.controler;

import co.com.claro.mgl.war.components.dtos.schedule.MglSchedule;
import co.com.claro.mgl.war.components.dtos.schedule.MglScheduleComponetDto;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

/**
 *
 * @author villamilc
 */
@FacesComponent("co.com.claro.mgl.war.components.controler.MglScheduleControler")
public class MglScheduleControler extends UINamingContainer {
    
    public MglScheduleControler() {
    }
    
    private static final int NUM_DIAS_SEMANA = 7;

    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public String goMoveADay() {
        MglScheduleComponetDto localMglScheduleComponetDto = (MglScheduleComponetDto) this.getAttributes().get("mglSchedule");
        int diasMostrar = localMglScheduleComponetDto.getMglColumnDayList().size();
        int firstDayToShow = localMglScheduleComponetDto.getFirstDayToShow();

        if (firstDayToShow == 0 && firstDayToShow + (NUM_DIAS_SEMANA * 2) > diasMostrar) {
            firstDayToShow = diasMostrar - NUM_DIAS_SEMANA;
            localMglScheduleComponetDto.setFirstDayToShow(firstDayToShow);
            return "";
        }

        if (firstDayToShow + (NUM_DIAS_SEMANA * 2) > diasMostrar) {
            firstDayToShow = diasMostrar - NUM_DIAS_SEMANA;
            localMglScheduleComponetDto.setFirstDayToShow(firstDayToShow);
            return "";
        }

        localMglScheduleComponetDto.setFirstDayToShow(firstDayToShow + NUM_DIAS_SEMANA);
        return null;
    }

    public String goBackMoveADay() {
        MglScheduleComponetDto localMglScheduleComponetDto = (MglScheduleComponetDto) this.getAttributes().get("mglSchedule");
        int firstDayToShow = localMglScheduleComponetDto.getFirstDayToShow();

        if (firstDayToShow < NUM_DIAS_SEMANA){
            firstDayToShow = 0;
            localMglScheduleComponetDto.setFirstDayToShow(firstDayToShow);
            return "";
        }

        localMglScheduleComponetDto.setFirstDayToShow(firstDayToShow - NUM_DIAS_SEMANA);
        return null;
    }

    public boolean isLastColumnDay() {
        MglScheduleComponetDto localMglScheduleComponetDto = (MglScheduleComponetDto) this.getAttributes().get("mglSchedule");
        if (localMglScheduleComponetDto.getFirstDayToShow() + 7 >= (localMglScheduleComponetDto.getMglColumnDayList().size())) {
            return true;
        } else {
            return false;
        }
    }

    public String changueSelected(MglSchedule mglSchedule) {
        MglScheduleComponetDto localMglScheduleComponetDto = (MglScheduleComponetDto) this.getAttributes().get("mglSchedule");
        if (mglSchedule.getScheduled()) {
            mglSchedule.setScheduled(false);
            if (localMglScheduleComponetDto.getMglSchedulesSelected().contains(mglSchedule)) {
                localMglScheduleComponetDto.getMglSchedulesSelected().remove(mglSchedule);
            }
        } else {
            mglSchedule.setScheduled(true);
            localMglScheduleComponetDto.getMglSchedulesSelected().add(mglSchedule);
        }
        return "";
    }

    public int getNumbers(String num) {
        return (new Integer(num));
    }
    public String getMonthName(String date){
        int numMonth=new Integer(date.substring(5,7));
    
        switch(numMonth){
            case 1:
                return "Ene";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dic";
            default:  
                return " ";
        }
    }
    
    public int percentCalculte(int a, int b){
        float ap=a; 
        float bp=b;        
        return Math.round((ap/bp)*100);
    }
}
