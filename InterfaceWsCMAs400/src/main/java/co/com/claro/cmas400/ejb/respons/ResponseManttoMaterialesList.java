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
public class ResponseManttoMaterialesList extends BaseResponse {

    @XmlElement
    private List<ResponseDataManttoMateriales> listManttoMateriales =
            new ArrayList<ResponseDataManttoMateriales>();

    public void cargarListaRespuesta(ArrayList parListManttoMateriales)
             {
        if (parListManttoMateriales.size() > 0) {
            for (Object linea : parListManttoMateriales) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataManttoMateriales responseDataManttoMateriales =
                        new ResponseDataManttoMateriales(aux);
                if (responseDataManttoMateriales.getCodigo() != null
                        && !responseDataManttoMateriales.getCodigo().equals("")) {
                    listManttoMateriales.add(responseDataManttoMateriales);
                }
            }
        }
    }

    public List<ResponseDataManttoMateriales> getListManttoMateriales() {
        return listManttoMateriales;
    }

    public void setListManttoMateriales(List<ResponseDataManttoMateriales> listManttoMateriales) {
        this.listManttoMateriales = listManttoMateriales;
    }
}
