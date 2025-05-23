/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.restrition_ccmm;

import co.com.claro.mgw.agenda.util.ServicesAgendamientosResponse;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class SchedulerRestrictionResponse  extends ServicesAgendamientosResponse{
    
    
    private List<SchedulerRestritionCcmmDto> horariosPerRestricciones;
    private List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones;

    public List<SchedulerRestritionCcmmDto> getHorariosPerRestricciones() {
        return horariosPerRestricciones;
    }

    public void setHorariosPerRestricciones(List<SchedulerRestritionCcmmDto> horariosPerRestricciones) {
        this.horariosPerRestricciones = horariosPerRestricciones;
    }

    public List<SchedulerRestritionCcmmDto> getHorariosNomarcaRestricciones() {
        return horariosNomarcaRestricciones;
    }

    public void setHorariosNomarcaRestricciones(List<SchedulerRestritionCcmmDto> horariosNomarcaRestricciones) {
        this.horariosNomarcaRestricciones = horariosNomarcaRestricciones;
    }
    
}
