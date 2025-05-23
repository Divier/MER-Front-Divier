/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "mglResponseGeoPoliticoDto")
public class MglResponseGeoPoliticoDto extends CmtDefaultBasicResponse {

    @XmlElement
    private List<GeoPoliticoDto> geoPoliticoList;

    public List<GeoPoliticoDto> getGeoPoliticoList() {
        return geoPoliticoList;
    }

    public void setGeoPoliticoList(List<GeoPoliticoDto> geoPoliticoList) {
        this.geoPoliticoList = geoPoliticoList;
    }

}
