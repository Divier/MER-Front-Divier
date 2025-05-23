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
public class ResponseConsultaDealerList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaDealer> listConsultaDealer = new ArrayList<ResponseDataConsultaDealer>();

    public void cargarListaRespuesta(ArrayList parListConsultaDealer) {
        if (parListConsultaDealer.size() > 0) {
            for (Object linea : parListConsultaDealer) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaDealer responseDataConsultaDealer = new ResponseDataConsultaDealer(aux);
                if (responseDataConsultaDealer.getCodigo() != null
                        && !responseDataConsultaDealer.getCodigo().equals("")) {
                    listConsultaDealer.add(responseDataConsultaDealer);
                }
            }
        }
    }

    public List<ResponseDataConsultaDealer> getListConsultaDealer() {
        return listConsultaDealer;
    }

    public void setListConsultaDealer(List<ResponseDataConsultaDealer> listConsultaDealer) {
        this.listConsultaDealer = listConsultaDealer;
    }
}
