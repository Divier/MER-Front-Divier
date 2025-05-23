/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author cardenaslb
 */
public class CellTec {
    
    
    
    @XmlElement(name = "cellTec")
    protected String cellTec;

    public String getCellTec() {
        return cellTec;
    }

    public void setCellTec(String cellTec) {
        this.cellTec = cellTec;
    }
    
    
    
}
