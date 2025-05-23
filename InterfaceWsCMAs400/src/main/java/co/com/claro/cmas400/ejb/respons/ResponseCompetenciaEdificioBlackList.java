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
public class ResponseCompetenciaEdificioBlackList extends BaseResponse {

    @XmlElement
    private List<ResponseDataCompetenciaEdificioBlackList> listBlackList =
            new ArrayList<ResponseDataCompetenciaEdificioBlackList>();

    public void cargarListaRespuesta(ArrayList parListBlackList)
             {
        if (parListBlackList.size() > 0) {
            for (Object linea : parListBlackList) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataCompetenciaEdificioBlackList responseDataBlackList =
                        new ResponseDataCompetenciaEdificioBlackList(aux);
                if (responseDataBlackList.getCodigo() != null
                        && !responseDataBlackList.getCodigo().equals("")) {
                    listBlackList.add(responseDataBlackList);
                }
            }
        }
    }

    public List<ResponseDataCompetenciaEdificioBlackList> getListBlackList() {
        return listBlackList;
    }

    public void setListBlackList(
            List<ResponseDataCompetenciaEdificioBlackList> listBlackList) {
        this.listBlackList = listBlackList;
    }
}
