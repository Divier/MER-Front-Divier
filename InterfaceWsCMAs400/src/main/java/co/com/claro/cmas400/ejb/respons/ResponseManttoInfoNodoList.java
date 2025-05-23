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
public class ResponseManttoInfoNodoList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoInfoNodo> listManttoInfoNodo =
            new ArrayList<ResponseDataManttoInfoNodo>();

    public void cargarListaRespuesta(ArrayList parListManttoInfoNodo)
             {
        if (parListManttoInfoNodo.size() > 0) {
            for (Object linea : parListManttoInfoNodo) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoInfoNodo responseDataManttoInfoNodo =
                        new ResponseDataManttoInfoNodo(aux);
                if (responseDataManttoInfoNodo.getCodigo() != null
                        && !responseDataManttoInfoNodo.getCodigo().equals("")) {
                    listManttoInfoNodo.add(responseDataManttoInfoNodo);
                }
            }
        }
    }

    public List<ResponseDataManttoInfoNodo> getListManttoInfoNodo() {
        return listManttoInfoNodo;
    }

    public void setListManttoInfoNodo(List<ResponseDataManttoInfoNodo> listManttoInfoNodo) {
        this.listManttoInfoNodo = listManttoInfoNodo;
    }
}
