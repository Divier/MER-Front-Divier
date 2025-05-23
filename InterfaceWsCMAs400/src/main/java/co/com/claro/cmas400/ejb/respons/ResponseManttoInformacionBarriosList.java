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
public class ResponseManttoInformacionBarriosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoBarrios> listManttoBarrios =
            new ArrayList<ResponseDataManttoBarrios>();

    public void cargarListaRespuesta(ArrayList parlistManttoBarrios)
             {
        if (parlistManttoBarrios.size() > 0) {
            for (Object linea : parlistManttoBarrios) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoBarrios responseDataManttoBarrios =
                        new ResponseDataManttoBarrios(aux);
                if (responseDataManttoBarrios.getCodigo() != null
                        && !responseDataManttoBarrios.getCodigo().equals("")) {
                    listManttoBarrios.add(responseDataManttoBarrios);
                }
            }
        }
    }

    public List<ResponseDataManttoBarrios> getListManttoBarrios() {
        return listManttoBarrios;
    }

    public void setListManttoBarrios(List<ResponseDataManttoBarrios> listManttoBarrios) {
        this.listManttoBarrios = listManttoBarrios;
    }
}
