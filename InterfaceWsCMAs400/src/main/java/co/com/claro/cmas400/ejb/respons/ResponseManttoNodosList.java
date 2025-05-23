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
public class ResponseManttoNodosList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoNodos> listManttoNodos = new ArrayList<ResponseDataManttoNodos>();

    public void cargarListaRespuesta(ArrayList parListManttoNodos)  {
        if (parListManttoNodos.size() > 0) {
            for (Object linea : parListManttoNodos) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoNodos responseDataManttoNodos = new ResponseDataManttoNodos(aux);
                if (responseDataManttoNodos.getCodigo() != null
                        && !responseDataManttoNodos.getCodigo().equals("")) {
                    listManttoNodos.add(responseDataManttoNodos);
                }
            }
        }
    }

    public List<ResponseDataManttoNodos> getListManttoNodos() {
        return listManttoNodos;
    }

    public void setListManttoNodos(List<ResponseDataManttoNodos> listManttoNodos) {
        this.listManttoNodos = listManttoNodos;
    }
}
