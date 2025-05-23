/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "region")
public class CmtRegionDto {

    @XmlElement
    private BigDecimal regionId;
    @XmlElement
    private String name;
    @XmlElement
    private String technicalCode;

    public BigDecimal getRegionId() {
        return regionId;
    }

    public void setRegionId(BigDecimal regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*
     * optine el codidigo wfm
     * getTechnicalCode()
     * @return String 
     */
    public String getTechnicalCode() {
        return technicalCode;
    }
    /*
     * setea el codidigo wfm
     * setTechnicalCode()
     * @Param String
     */
    public void setTechnicalCode(String technicalCode) {
        this.technicalCode = technicalCode;
    }
    
    
}
