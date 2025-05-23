/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "appResponsesAgendaDto")
public class AppResponsesAgendaDto extends CmtDefaultBasicResponse {

    @XmlElement
    private List<AgendasMglDto> agendasOtCcmmHhpp;

    public List<AgendasMglDto> getAgendasOtCcmmHhpp() {
        return agendasOtCcmmHhpp;
    }

    public void setAgendasOtCcmmHhpp(List<AgendasMglDto> agendasOtCcmmHhpp) {
        this.agendasOtCcmmHhpp = agendasOtCcmmHhpp;
    }
}
