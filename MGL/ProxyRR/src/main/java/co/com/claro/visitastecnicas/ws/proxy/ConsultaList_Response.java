/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "consultaList_Response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaList_Response", propOrder = {
    "consultaMglOnyxList"
})
public class ConsultaList_Response {

    @XmlElement(name = "consultaMglOnyxList")
    protected ConsultaMglOnyxList consultaMglOnyxList;

    public ConsultaMglOnyxList getConsultaMglOnyxList() {
        return consultaMglOnyxList;
    }

    public void setConsultaMglOnyxList(ConsultaMglOnyxList consultaMglOnyxList) {
        this.consultaMglOnyxList = consultaMglOnyxList;
    }

}
