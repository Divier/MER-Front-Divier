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
public class ResponseTipoMaterialesList extends BaseResponse {

    @XmlElement
    private List<ResponseDataTipoMateriales> listTipoMateriales = new ArrayList<ResponseDataTipoMateriales>();

    public void cargarListaRespuesta(ArrayList parListTipoMateriales)  {
        if (parListTipoMateriales.size() > 0) {
            for (Object linea : parListTipoMateriales) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataTipoMateriales responseDataTipoMateriales = new ResponseDataTipoMateriales(aux);
                if (responseDataTipoMateriales.getCodigo() != null
                        && !responseDataTipoMateriales.getCodigo().equals("")) {
                    listTipoMateriales.add(responseDataTipoMateriales);
                }
            }
        }
    }

    public List<ResponseDataTipoMateriales> getListTipoMateriales() {
        return listTipoMateriales;
    }

    public void setListTipoMateriales(List<ResponseDataTipoMateriales> listTipoMateriales) {
        this.listTipoMateriales = listTipoMateriales;
    }
}
