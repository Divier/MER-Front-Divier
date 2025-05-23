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
@XmlRootElement(name = "uperGeographycLevel")
public class CmtUperGeographycLevelDto {

    @XmlElement
    private BigDecimal geographyStateId;
    @XmlElement
    private String name;
    @XmlElement
    private String daneCode;
    @XmlElement
    private String geographycLevelType;   
    
    private CmtUperGeographycLevelDto uperGeographycLevel;

    public BigDecimal getGeographyStateId() {
        return geographyStateId;
    }

    public void setGeographyStateId(BigDecimal geographyStateId) {
        this.geographyStateId = geographyStateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDaneCode() {
        return daneCode;
    }

    public void setDaneCode(String daneCode) {
        this.daneCode = daneCode;
    }
    /*
     * Obtiene tipo geografico
     * getGeographycLevelType()
     * @return String 
     */
    public String getGeographycLevelType() {
        return geographycLevelType;
    }
    /*
     * setea tipo de geografico
     * setGeographycLevelType()
     * @Param String geographycLevelType
     */
    public void setGeographycLevelType(String geographycLevelType) {
        this.geographycLevelType = geographycLevelType;
    }    

    /*
     * Obtiene nivel superior geografico
     * CmtUperGeographycLevelDto()
     * @return CmtUperGeographycLevelDto 
     */
    public CmtUperGeographycLevelDto getUperGeographycLevel() {
        return uperGeographycLevel;
    }
    /*
     * setea nivel superior de geografico
     * setUperGeographycLevel()
     * @Param String CmtUperGeographycLevelDto
     */
    public void setUperGeographycLevel(CmtUperGeographycLevelDto uperGeographycLevel) {
        this.uperGeographycLevel = uperGeographycLevel;
    }
    
    
}
