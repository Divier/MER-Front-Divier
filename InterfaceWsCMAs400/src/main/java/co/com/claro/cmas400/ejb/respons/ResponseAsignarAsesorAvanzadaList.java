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
public class ResponseAsignarAsesorAvanzadaList extends BaseResponse {

    @XmlElement
    private List<ResponseDataAsignarAsesorAvanzada> listAsignarAsesor = 
            new ArrayList<ResponseDataAsignarAsesorAvanzada>();

    public void cargarListaRespuesta(ArrayList parListAsignarAsesor) {
        if (parListAsignarAsesor.size() > 0) {
            for (Object linea : parListAsignarAsesor) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataAsignarAsesorAvanzada responseDataAsignarAsesor = 
                        new ResponseDataAsignarAsesorAvanzada(aux);
                if (responseDataAsignarAsesor.getCodigoEdificio() != null
                        && !responseDataAsignarAsesor.getCodigoEdificio().equals("")) {
                    listAsignarAsesor.add(responseDataAsignarAsesor);
                }
            }
        }
    }

    public List<ResponseDataAsignarAsesorAvanzada> getListAsignarAsesor() {
        return listAsignarAsesor;
    }

    public void setListAsignarAsesor(List<ResponseDataAsignarAsesorAvanzada> listAsignarAsesor) {
        this.listAsignarAsesor = listAsignarAsesor;
    }
}
