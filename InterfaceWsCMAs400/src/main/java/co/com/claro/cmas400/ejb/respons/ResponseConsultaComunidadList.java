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
public class ResponseConsultaComunidadList extends BaseResponse {

    @XmlElement
    private List<ResponseDataConsultaComunidad> listConsultaComunidad = 
            new ArrayList<ResponseDataConsultaComunidad>();

    public void cargarListaRespuesta(ArrayList parListConsultaComunidad) {
        if (parListConsultaComunidad.size() > 0) {
            for (Object linea : parListConsultaComunidad) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataConsultaComunidad responseDataConsultaComunidad = 
                        new ResponseDataConsultaComunidad(aux);
                if (responseDataConsultaComunidad.getCodigoComunidad() != null
                        && !responseDataConsultaComunidad.getCodigoComunidad().equals("")) {
                    listConsultaComunidad.add(responseDataConsultaComunidad);
                }
            }
        }
    }

    public List<ResponseDataConsultaComunidad> getListConsultaComunidad() {
        return listConsultaComunidad;
    }

    public void setListConsultaComunidad(List<ResponseDataConsultaComunidad> listConsultaComunidad) {
        this.listConsultaComunidad = listConsultaComunidad;
    }
}
