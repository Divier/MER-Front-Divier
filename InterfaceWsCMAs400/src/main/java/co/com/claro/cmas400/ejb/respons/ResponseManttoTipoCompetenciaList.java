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
public class ResponseManttoTipoCompetenciaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoTipoCompetencia> listManttoTipoCompetencia = 
            new ArrayList<ResponseDataManttoTipoCompetencia>();

    public void cargarListaRespuesta(ArrayList parListTipoCompetencia)  {
        if (parListTipoCompetencia.size() > 0) {
            for (Object linea : parListTipoCompetencia) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoTipoCompetencia responseDataTipoCompetencia = 
                        new ResponseDataManttoTipoCompetencia(aux);
                if (responseDataTipoCompetencia.getCodigo() != null
                        && !responseDataTipoCompetencia.getCodigo().equals("")) {
                    listManttoTipoCompetencia.add(responseDataTipoCompetencia);
                }
            }
        }
    }

    public List<ResponseDataManttoTipoCompetencia> getListManttoTipoCompetencia() {
        return listManttoTipoCompetencia;
    }

    public void setListManttoTipoCompetencia(List<ResponseDataManttoTipoCompetencia> listManttoTipoCompetencia) {
        this.listManttoTipoCompetencia = listManttoTipoCompetencia;
    }
}
