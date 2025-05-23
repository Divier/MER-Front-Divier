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
public class ResponseCuentaMatriz extends BaseResponse {

    @XmlElement
    private List<ResponseDataCuentaMatriz> listResponseDataCuentaMatriz =
            new ArrayList<ResponseDataCuentaMatriz>();

    public void cargarListaRespuesta(
            String parListCuentaMatriz) {
        if (parListCuentaMatriz.length() > 0) {
            ResponseDataCuentaMatriz responseDataCuentaMatrizByCod =
                    new ResponseDataCuentaMatriz(parListCuentaMatriz);
            listResponseDataCuentaMatriz.add(responseDataCuentaMatrizByCod);
        }
    }

    public List<ResponseDataCuentaMatriz> getListResponseDataCuentaMatriz() {
        return listResponseDataCuentaMatriz;
    }

    public void setListResponseDataCuentaMatriz(List<ResponseDataCuentaMatriz> listResponseDataCuentaMatriz) {
        this.listResponseDataCuentaMatriz = listResponseDataCuentaMatriz;
    }
}
