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
public class ResponseManttoPuntoInicialList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoPuntoInicial> listManttoPuntoInicial =
            new ArrayList<ResponseDataManttoPuntoInicial>();

    public void cargarListaRespuesta(ArrayList parListManttoPuntoInicial)
             {
        if (parListManttoPuntoInicial.size() > 0) {
            for (Object linea : parListManttoPuntoInicial) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoPuntoInicial responseDataManttoPuntoInicial =
                        new ResponseDataManttoPuntoInicial(aux);
                if (responseDataManttoPuntoInicial.getCodigo() != null
                        && !responseDataManttoPuntoInicial.getCodigo().equals("")) {
                    listManttoPuntoInicial.add(responseDataManttoPuntoInicial);
                }
            }
        }
    }

    public List<ResponseDataManttoPuntoInicial> getListManttoPuntoInicial() {
        return listManttoPuntoInicial;
    }

    public void setListManttoPuntoInicial(List<ResponseDataManttoPuntoInicial> listManttoPuntoInicial) {
        this.listManttoPuntoInicial = listManttoPuntoInicial;
    }
}
