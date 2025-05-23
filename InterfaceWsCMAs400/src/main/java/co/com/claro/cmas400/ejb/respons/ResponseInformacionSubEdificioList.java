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
public class ResponseInformacionSubEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInformacionSubEdificio> listInformacionSubEdificio =
            new ArrayList<ResponseDataInformacionSubEdificio>();

    public void cargarListaRespuesta(ArrayList parListInformacionSubEdificio)
             {
        if (parListInformacionSubEdificio.size() > 0) {
            for (Object linea : parListInformacionSubEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataInformacionSubEdificio responseDataInformacionSubEdificio =
                        new ResponseDataInformacionSubEdificio(aux);
                if (responseDataInformacionSubEdificio.getCodigo() != null
                        && !responseDataInformacionSubEdificio.getCodigo().equals("")) {
                    listInformacionSubEdificio.add(responseDataInformacionSubEdificio);
                }
            }
        }
    }

    public List<ResponseDataInformacionSubEdificio> getListInformacionSubEdificio() {
        return listInformacionSubEdificio;
    }

    public void setListInformacionSubEdificio(List<ResponseDataInformacionSubEdificio> listInformacionSubEdificio) {
        this.listInformacionSubEdificio = listInformacionSubEdificio;
    }
}
