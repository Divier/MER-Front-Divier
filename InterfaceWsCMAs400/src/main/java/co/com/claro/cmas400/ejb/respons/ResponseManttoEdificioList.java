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
public class ResponseManttoEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoEdificio> listManttoEdificios =
            new ArrayList<ResponseDataManttoEdificio>();

    public void cargarListaRespuesta(String parlistManttoEdificios)
             {
        if (parlistManttoEdificios.length() > 0) {

            ResponseDataManttoEdificio responseDataManttoEdificio =
                    new ResponseDataManttoEdificio(parlistManttoEdificios);
            listManttoEdificios.add(responseDataManttoEdificio);

        }
    }

    public List<ResponseDataManttoEdificio> getListManttoEdificios() {
        return listManttoEdificios;
    }

    public void setListManttoEdificios(List<ResponseDataManttoEdificio> listManttoEdificios) {
        this.listManttoEdificios = listManttoEdificios;
    }
}
