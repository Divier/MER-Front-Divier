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
public class ResponseManttoTipoNotasList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoTipoNotas> listManttoTipoNotas =
            new ArrayList<ResponseDataManttoTipoNotas>();

    public void cargarListaRespuesta(ArrayList parListManttoTipoNotas)
             {
        if (parListManttoTipoNotas.size() > 0) {
            for (Object linea : parListManttoTipoNotas) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoTipoNotas responseDataManttoTipoNotas =
                        new ResponseDataManttoTipoNotas(aux);
                if (responseDataManttoTipoNotas.getCodigo() != null
                        && !responseDataManttoTipoNotas.getCodigo().equals("")) {
                    listManttoTipoNotas.add(responseDataManttoTipoNotas);
                }
            }
        }
    }

    public List<ResponseDataManttoTipoNotas> getListManttoTipoNotas() {
        return listManttoTipoNotas;
    }

    public void setListManttoTipoNotas(List<ResponseDataManttoTipoNotas> listManttoTipoNotas) {
        this.listManttoTipoNotas = listManttoTipoNotas;
    }
}
