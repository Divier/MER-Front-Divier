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
public class ResponseOtSubEdificiosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataOtSubEdificios> listOtSubEdificios = 
            new ArrayList<ResponseDataOtSubEdificios>();

    public void cargarListaRespuesta(ArrayList parListOtSubEdificios) {
        if (parListOtSubEdificios.size() > 0) {
            for (Object linea : parListOtSubEdificios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataOtSubEdificios responseDataOtSubEdificios = 
                        new ResponseDataOtSubEdificios(aux);
                if (responseDataOtSubEdificios.getTipoTrabajo() != null
                        && !responseDataOtSubEdificios.getTipoTrabajo().equals("")) {
                    listOtSubEdificios.add(responseDataOtSubEdificios);
                }
            }
        }
    }

    public List<ResponseDataOtSubEdificios> getListOtSubEdificios() {
        return listOtSubEdificios;
    }

    public void setListOtSubEdificios(List<ResponseDataOtSubEdificios> listOtSubEdificios) {
        this.listOtSubEdificios = listOtSubEdificios;
    }
}
