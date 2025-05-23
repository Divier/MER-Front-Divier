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
public class ResponseCompetenciaEdificioList extends BaseResponse {

    @XmlElement
    private List<ResponseDataCompetenciaEdificio> listCompetenciaEdificio =
            new ArrayList<ResponseDataCompetenciaEdificio>();

    public void cargarListaRespuesta(ArrayList parListCompetenciaEdificio)
             {
        if (parListCompetenciaEdificio.size() > 0) {
            for (Object linea : parListCompetenciaEdificio) {
                String aux = (String) linea;
                if (aux.equals("")) {
                    return;
                }
                ResponseDataCompetenciaEdificio responseDataCompetenciaEdificio =
                        new ResponseDataCompetenciaEdificio(aux);
                if (responseDataCompetenciaEdificio.getCodigo() != null
                        && !responseDataCompetenciaEdificio.getCodigo().equals("")) {
                    listCompetenciaEdificio.add(responseDataCompetenciaEdificio);
                }
            }
        }
    }

    public List<ResponseDataCompetenciaEdificio> getListCompetenciaEdificio() {
        return listCompetenciaEdificio;
    }

    public void setListCompetenciaEdificio(List<ResponseDataCompetenciaEdificio> listCompetenciaEdificio) {
        this.listCompetenciaEdificio = listCompetenciaEdificio;
    }
}
