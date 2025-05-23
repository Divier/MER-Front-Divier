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
public class ResponseManttoCompetenciaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoCompetencia> listmanttoCompetencia =
            new ArrayList<ResponseDataManttoCompetencia>();

    public void cargarListaRespuesta(ArrayList parListManttoCompetneica)
             {
        if (parListManttoCompetneica.size() > 0) {
            for (Object linea : parListManttoCompetneica) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoCompetencia responseDataManttoCompetencia =
                        new ResponseDataManttoCompetencia(aux);
                if (responseDataManttoCompetencia.getCodigo() != null
                        && !responseDataManttoCompetencia.getCodigo().equals("")) {
                    listmanttoCompetencia.add(responseDataManttoCompetencia);
                }
            }
        }
    }

    public List<ResponseDataManttoCompetencia> getListmanttoCompetencia() {
        return listmanttoCompetencia;
    }

    public void setListmanttoCompetencia(List<ResponseDataManttoCompetencia> listmanttoCompetencia) {
        this.listmanttoCompetencia = listmanttoCompetencia;
    }
}
