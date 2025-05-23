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
public class ResponseConsultaEdificiosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaEdificios> listConsultaEdificios = new ArrayList<ResponseDataConsultaEdificios>();

    public void cargarListaRespuesta(ArrayList parListConsultaEdificios) {
        if (parListConsultaEdificios.size() > 0) {
            for (Object linea : parListConsultaEdificios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaEdificios responseDataConsultaEdificios = new ResponseDataConsultaEdificios(aux);
                if (responseDataConsultaEdificios.getCodigo() != null
                        && !responseDataConsultaEdificios.getCodigo().equals("")) {
                    listConsultaEdificios.add(responseDataConsultaEdificios);
                }
            }
        }
    }

    public List<ResponseDataConsultaEdificios> getListConsultaEdificios() {
        return listConsultaEdificios;
    }

    public void setListConsultaEdificios(List<ResponseDataConsultaEdificios> listConsultaEdificios) {
        this.listConsultaEdificios = listConsultaEdificios;
    }
}
