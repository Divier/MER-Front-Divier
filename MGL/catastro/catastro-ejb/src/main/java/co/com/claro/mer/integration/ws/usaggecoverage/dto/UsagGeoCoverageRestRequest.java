/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggecoverage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author 45111073
 */
@Getter
@Setter
public class UsagGeoCoverageRestRequest {
    @JsonProperty("daneCode")
    @XmlElement(name = "daneCode", required = true)
    private String daneCode;
    @JsonProperty("nodeCode")
    @XmlElement(name = "nodeCode", required = true)
    private String nodeCode;
    @JsonProperty("technologyType")
    @XmlElement(name = "technologyType", required = true)
    private String technologyType;
    @JsonProperty("owner")
    @XmlElement(name = "owner", required = true)
    private String owner;


    public UsagGeoCoverageRestRequest(String daneCode,String nodeCode,String technologyType,String owner){
        this.daneCode = daneCode;
        this.nodeCode = nodeCode;
        this.technologyType = technologyType;
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "UsagGeoCoverageRestRequest{" + "daneCode=" + daneCode + ", nodeCode=" + nodeCode + ", technologyType=" + technologyType + ", owner=" + owner + '}';
    }
}
