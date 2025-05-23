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
public class ResponseTipoCompetenciaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataTipoCompetencia> listTipoCompetencia =
            new ArrayList<ResponseDataTipoCompetencia>();

    public void cargarListaRespuesta(ArrayList parListTipoCompetencia)
             {
        if (parListTipoCompetencia.size() > 0) {
            for (Object linea : parListTipoCompetencia) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataTipoCompetencia responseDataTipoCompetencia =
                        new ResponseDataTipoCompetencia(aux);
                if (responseDataTipoCompetencia.getCodigo() != null
                        && !responseDataTipoCompetencia.getCodigo().equals("")) {
                    listTipoCompetencia.add(responseDataTipoCompetencia);
                }
            }
        }
    }

    public List<ResponseDataTipoCompetencia> getListTipoCompetencia() {
        return listTipoCompetencia;
    }

    public void setListTipoCompetencia(List<ResponseDataTipoCompetencia> listTipoCompetencia) {
        this.listTipoCompetencia = listTipoCompetencia;
    }
}
