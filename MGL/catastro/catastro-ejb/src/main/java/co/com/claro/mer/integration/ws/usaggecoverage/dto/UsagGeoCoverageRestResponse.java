/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggecoverage.dto;

/**
 *
 * @author 45111073
 */
public class UsagGeoCoverageRestResponse {
    private String daneCode;
    private String nodeCode;
    private String technologyType;
    private Coords coords;
    private String code;
    private String message;
    
     @Override
    public String toString() {
        return "UsagGeoCoverageRestResponse{" + "daneCode=" + daneCode + ", nodeCode=" + nodeCode + ", technologyType=" + technologyType + ", coords=" + coords.toString() + ", message=" + message + ", code=" + code + '}';
    }

    public String getDaneCode() {
        return daneCode;
    }

    public void setDaneCode(String daneCode) {
        this.daneCode = daneCode;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

}
