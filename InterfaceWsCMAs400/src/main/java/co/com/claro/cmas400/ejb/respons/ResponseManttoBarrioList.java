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
public class ResponseManttoBarrioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoBarrio> listManttoBarrio =
            new ArrayList<ResponseDataManttoBarrio>();

    public void cargarListaRespuesta(ArrayList parListManttoBarrio)
             {
        if (parListManttoBarrio.size() > 0) {
            for (Object linea : parListManttoBarrio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoBarrio responseDataManttoBarrio =
                        new ResponseDataManttoBarrio(aux);
                if (responseDataManttoBarrio.getCodigo() != null
                        && !responseDataManttoBarrio.getCodigo().equals("")) {
                    listManttoBarrio.add(responseDataManttoBarrio);
                }
            }
        }
    }

    public List<ResponseDataManttoBarrio> getListManttoBarrio() {
        return listManttoBarrio;
    }

    public void setListManttoBarrio(List<ResponseDataManttoBarrio> listManttoBarrio) {
        this.listManttoBarrio = listManttoBarrio;
    }
}
