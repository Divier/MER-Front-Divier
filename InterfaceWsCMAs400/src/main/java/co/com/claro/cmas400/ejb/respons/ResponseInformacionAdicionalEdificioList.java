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
public class ResponseInformacionAdicionalEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataInformacionAdicionalEdificio> listInformacionAdicionalEdificio =
            new ArrayList<ResponseDataInformacionAdicionalEdificio>();

    public void cargarListaRespuesta(String parListinfoAdicEdificio)
             {

        if (parListinfoAdicEdificio.length() > 0) {

            ResponseDataInformacionAdicionalEdificio responseDataInformacionAdicionalEdificio =
                    new ResponseDataInformacionAdicionalEdificio(parListinfoAdicEdificio);

            listInformacionAdicionalEdificio.add(responseDataInformacionAdicionalEdificio);
        }
    }

    public List<ResponseDataInformacionAdicionalEdificio> getListInformacionAdicionalEdificio() {
        return listInformacionAdicionalEdificio;
    }

    public void setListInformacionAdicionalEdificio(List<ResponseDataInformacionAdicionalEdificio> listInformacionAdicionalEdificio) {
        this.listInformacionAdicionalEdificio = listInformacionAdicionalEdificio;
    }
}
