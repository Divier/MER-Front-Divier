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
public class ResponseManttoAsesorGestionDeAvanzadaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataAsesorGestionDeAvanzada> listAsesorAvanzada = 
            new ArrayList<ResponseDataAsesorGestionDeAvanzada>();

    public void cargarListaRespuesta(ArrayList parListAsesorAvanzada)  {
        if (parListAsesorAvanzada.size() > 0) {
            for (Object linea : parListAsesorAvanzada) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataAsesorGestionDeAvanzada responseDataAsesorAvanzada = 
                        new ResponseDataAsesorGestionDeAvanzada(aux);
                if (responseDataAsesorAvanzada.getCodigo() != null
                        && !responseDataAsesorAvanzada.getCodigo().equals("")) {
                    listAsesorAvanzada.add(responseDataAsesorAvanzada);
                }
            }
        }
    }

    public List<ResponseDataAsesorGestionDeAvanzada> getListAsesorAvanzada() {
        return listAsesorAvanzada;
    }

    public void setListAsesorAvanzada(List<ResponseDataAsesorGestionDeAvanzada> listAsesorAvanzada) {
        this.listAsesorAvanzada = listAsesorAvanzada;
    }
}
