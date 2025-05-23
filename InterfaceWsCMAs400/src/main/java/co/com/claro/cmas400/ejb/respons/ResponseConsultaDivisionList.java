/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class ResponseConsultaDivisionList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaDivision> listConsultaDivision = 
            new ArrayList<ResponseDataConsultaDivision>();

    public void cargarListaRespuesta(ArrayList parListConsultaDivision) {
        if (parListConsultaDivision.size() > 0) {
            for (Object linea : parListConsultaDivision) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaDivision responseDataConsultaDivision = 
                        new ResponseDataConsultaDivision(aux);
                if (responseDataConsultaDivision.getCodigo() != null
                        && !responseDataConsultaDivision.getCodigo().equals("")) {
                    listConsultaDivision.add(responseDataConsultaDivision);
                }
            }
        }
    }

    public List<ResponseDataConsultaDivision> getListConsultaDivision() {
        return listConsultaDivision;
    }

    public void setListConsultaDivision(List<ResponseDataConsultaDivision> listConsultaDivision) {
        this.listConsultaDivision = listConsultaDivision;
    }
}
