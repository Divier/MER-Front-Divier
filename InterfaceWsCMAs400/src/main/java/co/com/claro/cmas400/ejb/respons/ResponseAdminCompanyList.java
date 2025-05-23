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
public class ResponseAdminCompanyList extends BaseResponse {

    @XmlElement
    private List<ResponseDataAdminCompany> listAdminCompany = new ArrayList<ResponseDataAdminCompany>();

    public void cargarListaRespuesta(ArrayList parListAdminCompany) {
        if (parListAdminCompany.size() > 0) {
            for (Object linea : parListAdminCompany) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataAdminCompany responseDataAdminCompany = new ResponseDataAdminCompany(aux);
                if (responseDataAdminCompany.getCodigo() != null
                        && !responseDataAdminCompany.getCodigo().equals("")) {
                    listAdminCompany.add(responseDataAdminCompany);
                }
            }
        }
    }

    public List<ResponseDataAdminCompany> getListAdminCompany() {
        return listAdminCompany;
    }

    public void setListAdminCompany(List<ResponseDataAdminCompany> listAdminCompany) {
        this.listAdminCompany = listAdminCompany;
    }
}
