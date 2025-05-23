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
public class ResponseNotasCuentaMatrizList extends BaseResponse {

    @XmlElement
    private List<ResponseDataNotasCuentaMatriz> listNotasCuentaMatriz =
            new ArrayList<ResponseDataNotasCuentaMatriz>();

    public void cargarListaRespuesta(ArrayList parListNotasCuentaMatriz)
             {
        if (parListNotasCuentaMatriz.size() > 0) {
            for (Object linea : parListNotasCuentaMatriz) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataNotasCuentaMatriz responseDataNotasCuentaMatriz =
                        new ResponseDataNotasCuentaMatriz(aux);
                if (responseDataNotasCuentaMatriz.getCodigoNota() != null
                        && !responseDataNotasCuentaMatriz.getCodigoNota().equals("")) {
                    listNotasCuentaMatriz.add(responseDataNotasCuentaMatriz);
                }
            }
        }
    }

    public List<ResponseDataNotasCuentaMatriz> getListNotasCuentaMatriz() {
        return listNotasCuentaMatriz;
    }

    public void setListNotasCuentaMatriz(List<ResponseDataNotasCuentaMatriz> listNotasCuentaMatriz) {
        this.listNotasCuentaMatriz = listNotasCuentaMatriz;
    }
}
