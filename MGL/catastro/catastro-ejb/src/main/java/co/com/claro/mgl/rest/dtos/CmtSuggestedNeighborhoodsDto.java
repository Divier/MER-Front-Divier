/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author villamilc
 */
@XmlRootElement(name = "suggestedNeighborhoodsResponse")
public class CmtSuggestedNeighborhoodsDto {
    
    @XmlElement    
    List<CmtAddressSuggestedDto> neighborhoodsList;

    public List<CmtAddressSuggestedDto> getNeighborhoodsList() {
        return neighborhoodsList;
    }

    public void setNeighborhoodsList(List<CmtAddressSuggestedDto> neighborhoodsList) {
        this.neighborhoodsList = neighborhoodsList;
    }    
}
