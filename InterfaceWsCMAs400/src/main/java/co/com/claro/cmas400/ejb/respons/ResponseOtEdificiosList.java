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
public class ResponseOtEdificiosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataOtEdificios> listOtEdificios = new ArrayList<ResponseDataOtEdificios>();

    public void cargarListaRespuesta(ArrayList parListOtEdificios) {
        if (parListOtEdificios.size() > 0) {
            for (Object linea : parListOtEdificios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataOtEdificios responseDataOtEdificios = new ResponseDataOtEdificios(aux);
                if (responseDataOtEdificios.getTipoTrabajo() != null
                        && !responseDataOtEdificios.getTipoTrabajo().equals("")) {
                    listOtEdificios.add(responseDataOtEdificios);
                }
            }
        }
    }

    public List<ResponseDataOtEdificios> getListOtEdificios() {
        return listOtEdificios;
    }

    public void setListOtEdificios(List<ResponseDataOtEdificios> listOtEdificios) {
        this.listOtEdificios = listOtEdificios;
    }
}
