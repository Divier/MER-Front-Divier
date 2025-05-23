/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance.dto;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class Row {

    private String codeNode;
    private String distance;

    public String getCodeNode() {
        return codeNode;
    }

    public void setCodeNode(String codeNode) {
        this.codeNode = codeNode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Row{" + "codeNode=" + codeNode + ", distance=" + distance + '}';
    }
    
}
