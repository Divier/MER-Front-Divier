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
public class ResponseEquiposEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataEquiposEdificio> listEquiposEdificio = new ArrayList<ResponseDataEquiposEdificio>();

    public void cargarListaRespuesta(ArrayList parListEquiposEdificio)  {
        if (parListEquiposEdificio.size() > 0) {
            for (Object linea : parListEquiposEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataEquiposEdificio responseDataEquiposEdificio = new ResponseDataEquiposEdificio(aux);
                if (responseDataEquiposEdificio.getCodigoEdificio() != null
                        && !responseDataEquiposEdificio.getCodigoEdificio().equals("")) {
                    listEquiposEdificio.add(responseDataEquiposEdificio);
                }
            }
        }
    }

    public List<ResponseDataEquiposEdificio> getListEquiposEdificio() {
        return listEquiposEdificio;
    }

    public void setListEquiposEdificio(List<ResponseDataEquiposEdificio> listEquiposEdificio) {
        this.listEquiposEdificio = listEquiposEdificio;
    }
}
