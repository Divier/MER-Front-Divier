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
@XmlRootElement(name = "city")
public class CmtCityDto {

    @XmlElement
    private BigDecimal cityId;
    @XmlElement
    private String name;
    @XmlElement
    private String daneCode;
    @XmlElement
    private CmtUperGeographycLevelDto uperGeographycLevel;
    @XmlElement
    private CmtRegionDto region;
    @XmlElement
    private String claroCode;
    @XmlElement
    private String geographycLevelType;
    

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        this.cityId = cityId;
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

    public CmtUperGeographycLevelDto getUperGeographycLevel() {
        return uperGeographycLevel;
    }

    public void setUperGeographycLevel(CmtUperGeographycLevelDto uperGeographycLevel) {
        this.uperGeographycLevel = uperGeographycLevel;
    }

    public CmtRegionDto getRegion() {
        return region;
    }

    public void setRegion(CmtRegionDto region) {
        this.region = region;
    }

    /*
     * Obtiene Codigo Claro de la ciudad
     * getGpoClaroCode()
     * @return String 
     */
        
    public String getClaroCode() {
        return claroCode;
    }
    /*
     * setea Codigo Claro de la ciudad
     * setGpoClaroCode()
     * @Param String gpoClaroCode
     */
    public void setClaroCode(String claroCode) {
        this.claroCode = claroCode;
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
    
}
