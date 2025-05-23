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
public class ResponseInformacionAdicionalSubEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInformacionAdicionalSubEdificio> listInformacionAdicionalSubEdificio =
            new ArrayList<ResponseDataInformacionAdicionalSubEdificio>();

    public void cargarListaRespuesta(String parListInformacionAdicionalSubEdificio)
             {
        if (parListInformacionAdicionalSubEdificio.length() > 0) {
            ResponseDataInformacionAdicionalSubEdificio responseDataInformacionAdicionalSubEdificio =
                    new ResponseDataInformacionAdicionalSubEdificio(parListInformacionAdicionalSubEdificio);
            listInformacionAdicionalSubEdificio.add(responseDataInformacionAdicionalSubEdificio);
        }
    }

    public List<ResponseDataInformacionAdicionalSubEdificio> getListInformacionAdicionalSubEdificio() {
        return listInformacionAdicionalSubEdificio;
    }

    public void setListInformacionAdicionalSubEdificio(List<ResponseDataInformacionAdicionalSubEdificio> listInformacionAdicionalSubEdificio) {
        this.listInformacionAdicionalSubEdificio = listInformacionAdicionalSubEdificio;
    }
}
