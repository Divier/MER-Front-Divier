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
public class ResponseManttoTipoDistribucionRedInternaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoTipoDistribucionRedInterna> listManttoTipoDistribucionRedInterna =
            new ArrayList<ResponseDataManttoTipoDistribucionRedInterna>();

    public void cargarListaRespuesta(ArrayList parListManttoTipoDistribucionRedInterna)
             {
        if (parListManttoTipoDistribucionRedInterna.size() > 0) {
            for (Object linea : parListManttoTipoDistribucionRedInterna) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoTipoDistribucionRedInterna responseDataManttoTipoDistribucionRedInterna =
                        new ResponseDataManttoTipoDistribucionRedInterna(aux);
                if (responseDataManttoTipoDistribucionRedInterna.getCodigo() != null
                        && !responseDataManttoTipoDistribucionRedInterna.getCodigo().equals("")) {
                    listManttoTipoDistribucionRedInterna.add(responseDataManttoTipoDistribucionRedInterna);
                }
            }
        }
    }

    public List<ResponseDataManttoTipoDistribucionRedInterna> getListManttoTipoDistribucionRedInterna() {
        return listManttoTipoDistribucionRedInterna;
    }

    public void setListManttoTipoDistribucionRedInterna(List<ResponseDataManttoTipoDistribucionRedInterna> listManttoTipoDistribucionRedInterna) {
        this.listManttoTipoDistribucionRedInterna = listManttoTipoDistribucionRedInterna;
    }
}
