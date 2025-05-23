/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CapacidadAgendaDto;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appResponsesGetCapacityDto")
public class AppResponsesGetCapacityDto extends AppResponseCrearOtDto{
    
    
    @XmlElement
    private List<CapacidadAgendaDto> capacidad;

    
    public List<CapacidadAgendaDto> getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(List<CapacidadAgendaDto> capacidad) {
        this.capacidad = capacidad;
    }
    
    
    
}
