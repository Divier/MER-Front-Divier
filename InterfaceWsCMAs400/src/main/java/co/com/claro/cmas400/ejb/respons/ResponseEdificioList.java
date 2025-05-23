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
 * @author camargomf
 */
@XmlRootElement
public class ResponseEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataEdificio> listEdificio =
            new ArrayList<ResponseDataEdificio>();

    public void cargarListaRespuesta(ArrayList parListEdificio)
             {
        if (parListEdificio.size() > 0) {
            for (Object linea : parListEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataEdificio responseDataEdificio =
                        new ResponseDataEdificio(aux);
                if (responseDataEdificio.getCodigo() != null
                        && !responseDataEdificio.getCodigo().equals("")) {
                    listEdificio.add(responseDataEdificio);
                }
            }
        }
    }

    public List<ResponseDataEdificio> getListEdificio() {
        return listEdificio;
    }

    public void setListEdificio(List<ResponseDataEdificio> listEdificio) {
        this.listEdificio = listEdificio;
    }
}
