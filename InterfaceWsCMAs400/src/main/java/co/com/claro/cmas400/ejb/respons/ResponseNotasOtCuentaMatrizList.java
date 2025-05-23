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
public class ResponseNotasOtCuentaMatrizList extends BaseResponse {

    @XmlElement
    private List<ResponseDataNotasOtCuentaMatriz> listNotasOtCm = 
            new ArrayList<ResponseDataNotasOtCuentaMatriz>();

    public void cargarListaRespuesta(ArrayList parListNotasOtCm) {
        if (parListNotasOtCm.size() > 0) {
            for (Object linea : parListNotasOtCm) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataNotasOtCuentaMatriz responseDataNotasOtCm = 
                        new ResponseDataNotasOtCuentaMatriz(aux);
                if (responseDataNotasOtCm.getCodigoNota() != null
                        && !responseDataNotasOtCm.getCodigoNota().equals("")) {
                    listNotasOtCm.add(responseDataNotasOtCm);
                }
            }
        }
    }

    public List<ResponseDataNotasOtCuentaMatriz> getListNotasOtCm() {
        return listNotasOtCm;
    }

    public void setListNotasOtCm(List<ResponseDataNotasOtCuentaMatriz> listNotasOtCm) {
        this.listNotasOtCm = listNotasOtCm;
    }
}
