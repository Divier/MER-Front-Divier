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
public class ResponseManttoAlimentacionElectList extends BaseResponse {

    @XmlElement
    private List<ResponceDataAlimentacionElectrica> listAlimentacionElectrica = new ArrayList<ResponceDataAlimentacionElectrica>();

    public void cargarListaRespuesta(ArrayList parListAlimentacionElectrica)  {
        if (parListAlimentacionElectrica.size() > 0) {
            for (Object linea : parListAlimentacionElectrica) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponceDataAlimentacionElectrica responceDataAlimentacionElectrica =
                        new ResponceDataAlimentacionElectrica(aux);
                if (responceDataAlimentacionElectrica.getCodigo() != null
                        && !responceDataAlimentacionElectrica.getCodigo().equals("")) {
                    listAlimentacionElectrica.add(responceDataAlimentacionElectrica);
                }
            }
        }
    }

    public List<ResponceDataAlimentacionElectrica> getListAlimentacionElectrica() {
        return listAlimentacionElectrica;
    }

    public void setListAlimentacionElectrica(List<ResponceDataAlimentacionElectrica> listAlimentacionElectrica) {
        this.listAlimentacionElectrica = listAlimentacionElectrica;
    }
}
