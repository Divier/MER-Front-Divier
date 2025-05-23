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
public class ResponseCodigoBlackList extends BaseResponse {

    @XmlElement
    private List<ResponseDataCodigoBlackList> listBlackList = new ArrayList<ResponseDataCodigoBlackList>();

    public void cargarListaRespuesta(ArrayList parListBlackList)  {
        if (parListBlackList.size() > 0) {
            for (Object linea : parListBlackList) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataCodigoBlackList responseDataBlackList = new ResponseDataCodigoBlackList(aux);
                if (responseDataBlackList.getCodigo() != null
                        && !responseDataBlackList.getCodigo().equals("")) {
                    listBlackList.add(responseDataBlackList);
                }
            }
        }
    }

    public List<ResponseDataCodigoBlackList> getListBlackList() {
        return listBlackList;
    }

    public void setListBlackList(List<ResponseDataCodigoBlackList> listBlackList) {
        this.listBlackList = listBlackList;
    }
}
