/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.components.bussiness;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.war.components.dtos.schedule.MglCapacityElement;
import co.com.claro.mgl.war.components.dtos.schedule.MglColumnDay;
import co.com.claro.mgl.war.components.dtos.schedule.MglSchedule;
import co.com.claro.mgl.war.components.dtos.schedule.MglScheduleComponetDto;
import co.com.claro.mgl.war.components.dtos.schedule.MglSlotsOfSchedule;
import co.com.claro.mgl.war.components.dtos.schedule.MglTimeSlotOfDay;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author villamilc
 */
public class MglScheduleComponetBusiness {


    private List<MglCapacityElement> capacityList;

    /**
     * Debuelve el objeto para cargar la foma visual del componente
     *
     * @param capacityList Capacidades de OFSC
     * @param daysNum numero de dias del calendario a mostrar
     * @param StartDate
     * @return MglScheduleComponetDto componente para la representacion visual
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ParseException, ApplicationException
     */
    public MglScheduleComponetDto getScheduleView(
            List<MglCapacityElement> capacityList,
            int daysNum,
            Date StartDate) throws ApplicationException {

        MglScheduleComponetDto mglScheduleComponetDto =
                new MglScheduleComponetDto();

        this.capacityList = capacityList;
        mglScheduleComponetDto.setMglHoursOfDayList(getHoursList());
        mglScheduleComponetDto.setMglColumnDayList(
                getDaysColumn(daysNum, StartDate));
        getAssociatedColumnDayWithHours(mglScheduleComponetDto);
        MglSlotsOfSchedule mglSlotsOfSchedule2  = new MglSlotsOfSchedule();
        mglSlotsOfSchedule2.setTimeSlot("Total");
        mglScheduleComponetDto.getMglHoursOfDayList().add(mglSlotsOfSchedule2);
        return mglScheduleComponetDto;
    }

    /**
     *
     * @param daysNum
     * @param StartDate
     * @return
     */
    private List<MglColumnDay> getDaysColumn(int daysNum, Date StartDate) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String weekdays[] = dfs.getWeekdays();
        Calendar starDay = Calendar.getInstance();
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        starDay.setTime(StartDate);
        List<MglColumnDay> columnDayList = new ArrayList<>();
        for (int countDays = 0; countDays < daysNum; countDays++) {
            MglColumnDay columnDay = new MglColumnDay();
            columnDay.setWeekNameDay(weekdays[starDay.get(Calendar.DAY_OF_WEEK)].toUpperCase());
            columnDay.setDayDate(formattedDate.format(starDay.getTime()));
            starDay.add(Calendar.DATE, 1);
            columnDayList.add(columnDay);
        }
        return columnDayList;
    }

    /**
     *
     * @param mglScheduleComponetDto
     */
    private void getAssociatedColumnDayWithHours(
            MglScheduleComponetDto mglScheduleComponetDto) {
        for (MglColumnDay columnDay
                : mglScheduleComponetDto.getMglColumnDayList()) {
            columnDay.setMglHourOfDayList(new ArrayList<>());
            for (MglSlotsOfSchedule mglSlotsOfSchedule
                    : mglScheduleComponetDto.getMglHoursOfDayList()) {
                MglTimeSlotOfDay mglTimeSlotOfDay = new MglTimeSlotOfDay();
                mglTimeSlotOfDay.setAssociatedMglColumnDay(columnDay);
                mglTimeSlotOfDay.setAssociatedMglSlotsOfSchedule(mglSlotsOfSchedule);
                mglTimeSlotOfDay.setScheduleByHour(findCapacityOfTimeSlot(mglTimeSlotOfDay));
                if(mglTimeSlotOfDay.getScheduleByHour()!=null){
                        columnDay.setTotalQuota(columnDay.getTotalQuota()+
                                mglTimeSlotOfDay.getScheduleByHour().
                                getCapacityElement().getQuota());
                        columnDay.setTotalAvailable(columnDay.getTotalAvailable()+
                                mglTimeSlotOfDay.getScheduleByHour().
                                getCapacityElement().getAvailable());
                        mglSlotsOfSchedule.setTotalQuota(mglSlotsOfSchedule.getTotalQuota()+
                                mglTimeSlotOfDay.getScheduleByHour().
                                getCapacityElement().getQuota());
                        mglSlotsOfSchedule.setTotalAvailable(mglSlotsOfSchedule.
                                getTotalAvailable()+mglTimeSlotOfDay.getScheduleByHour().
                                getCapacityElement().getAvailable());
                }        
                columnDay.getMglHourOfDayList().add(mglTimeSlotOfDay);
            }
            MglTimeSlotOfDay mglTimeSlotOfDay2 = new MglTimeSlotOfDay();
            columnDay.getMglHourOfDayList().add(mglTimeSlotOfDay2);
        }
    }

    private MglSchedule findCapacityOfTimeSlot(
            MglTimeSlotOfDay mglTimeSlotOfDay) {
        MglSchedule mglSchedule = new MglSchedule();
        for (MglCapacityElement mce : capacityList) {
            if (mglTimeSlotOfDay.getAssociatedMglColumnDay().getDayDate()
                    .contains(convertDateToString(mce.getDate()))) {
                if (isSlotInCapacity(mglTimeSlotOfDay.getAssociatedMglSlotsOfSchedule()                        ,
                        mce.getTimeSlot())) {
                    mglSchedule.setCapacityElement(mce);
                    mglSchedule.setScheduled(Boolean.FALSE);
                    return mglSchedule;
                }
            }
        }
        return null;
    }

    private boolean isSlotInCapacity(MglSlotsOfSchedule associatedMglSlotsOfSchedule,
            String slot) {
        if (associatedMglSlotsOfSchedule.getTimeSlot().equalsIgnoreCase(slot)) {
            return true;
        } else {
            return false;
        }
    }

    private String convertDateToString(Date xc) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(xc);
    }

    /**
     *
     * @param startHour
     * @param starMinute
     * @param minutesSlotSize
     * @param slotNum
     * @return
     * @throws Exception
     */
    private List<MglSlotsOfSchedule> getHoursList() throws ApplicationException {

        List<MglSlotsOfSchedule> mglHoursOfDaylist = new ArrayList<>();
        int seqSlotPotition = 0;
        List<String> timeSlotUnicos= new ArrayList<>();

        for (MglCapacityElement element : capacityList) {
                timeSlotUnicos.add(element.getTimeSlot());
        }
        
        Set<String> hashSet = new HashSet<>(timeSlotUnicos);
        timeSlotUnicos.clear();
        timeSlotUnicos.addAll(hashSet);
        Collections.sort(timeSlotUnicos);
        boolean hayDuranteEldia = false;
       
        for (String element : timeSlotUnicos) {
            if (element.equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                hayDuranteEldia = true;
                MglSlotsOfSchedule slotsOfSchedule = new MglSlotsOfSchedule();
                slotsOfSchedule.setSlotPotition(0);
                slotsOfSchedule.setTimeSlot(element);
                mglHoursOfDaylist.add(slotsOfSchedule);
                seqSlotPotition = 1;
            }
        }
       
        if (hayDuranteEldia) {
            for (String element : timeSlotUnicos) {
                MglSlotsOfSchedule slotsOfSchedule = new MglSlotsOfSchedule();
                if (!element.equalsIgnoreCase(Constant.DURANTE_EL_DIA)) {
                    slotsOfSchedule.setSlotPotition(seqSlotPotition);
                    slotsOfSchedule.setTimeSlot(element);
                    mglHoursOfDaylist.add(slotsOfSchedule);
                }
                seqSlotPotition++;
            }
        } else {
            for (String element : timeSlotUnicos) {
                MglSlotsOfSchedule slotsOfSchedule = new MglSlotsOfSchedule();
                slotsOfSchedule.setSlotPotition(seqSlotPotition);
                slotsOfSchedule.setTimeSlot(element);
                mglHoursOfDaylist.add(slotsOfSchedule);
                seqSlotPotition++;
            }
        }

        return mglHoursOfDaylist;
    }

}
