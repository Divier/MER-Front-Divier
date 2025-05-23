/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
@Getter
@Setter
public class CalculateNodeDistanceRestRequest {

    @JsonProperty("cx")
    @XmlElement(name = "cx", required = true)
    private String cx;
    @JsonProperty("cy")
    @XmlElement(name = "cy", required = true)
    private String cy;
    @JsonProperty("typeTechnology")
    @XmlElement(name = "typeTechnology", required = true)
    private String typeTechnology;
    @JsonProperty("codeDane")
    @XmlElement(name = "codeDane", required = true)
    private String codeDane;

    public CalculateNodeDistanceRestRequest(String cx, String cy, String typeTechnology, String codeDane) {
       this.cx = cx;
        this.cy = cy;
        this.typeTechnology = typeTechnology;
        this. codeDane= codeDane;
    }
    @Override
    public String toString() {
        return "CalculateNodeDistanceRestRequest{" + "cx=" + cx + ", cy=" + cy + ", typeTechnology=" + typeTechnology + ", codeDane=" + codeDane + '}';
    }
}