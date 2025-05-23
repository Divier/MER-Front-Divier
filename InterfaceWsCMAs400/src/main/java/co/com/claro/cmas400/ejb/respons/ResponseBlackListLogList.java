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
public class ResponseBlackListLogList extends BaseResponse {

    @XmlElement
    private List<ResponseDataBlackListLog> listBlackListLog = new ArrayList<ResponseDataBlackListLog>();

    public void cargarListaRespuesta(ArrayList parListBlackListLog) {
        if (parListBlackListLog.size() > 0) {
            for (Object linea : parListBlackListLog) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataBlackListLog responseDataBlackListLog = new ResponseDataBlackListLog(aux);
                if (responseDataBlackListLog.getCodigo() != null
                        && !responseDataBlackListLog.getCodigo().equals("")) {
                    listBlackListLog.add(responseDataBlackListLog);
                }
            }
        }
    }

    public List<ResponseDataBlackListLog> getListBlackListLog() {
        return listBlackListLog;
    }

    public void setListBlackListLog(List<ResponseDataBlackListLog> listBlackListLog) {
        this.listBlackListLog = listBlackListLog;
    }
}
