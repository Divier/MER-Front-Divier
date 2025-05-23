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
public class ResponseManttoEdificiosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoEdificios> listManttoEdificios =
            new ArrayList<ResponseDataManttoEdificios>();

    public void cargarListaRespuesta(ArrayList parlistManttoEdificios)
             {
        if (parlistManttoEdificios.size() > 0) {
            for (Object linea : parlistManttoEdificios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoEdificios responseDataManttoEdificios =
                        new ResponseDataManttoEdificios(aux);
                if (responseDataManttoEdificios.getCodigo() != null
                        && !responseDataManttoEdificios.getCodigo().equals("")) {
                    listManttoEdificios.add(responseDataManttoEdificios);
                }
            }
        }
    }

    public List<ResponseDataManttoEdificios> getListManttoEdificios() {
        return listManttoEdificios;
    }

    public void setListManttoEdificios(List<ResponseDataManttoEdificios> listManttoEdificios) {
        this.listManttoEdificios = listManttoEdificios;
    }
}
