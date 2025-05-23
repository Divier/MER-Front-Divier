package co.com.claro.mgl.dtos;

import java.util.List;

/**
 * Dto implementado para procesos de consulta en apoyo a paginación de marcaciones de HHPP
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
public class FiltroConsultaMarcacionesHhppVtDto {

    long numRegistros = 0;
    List<CmtMarcacionesHhppVtDto> listaTablasMarcacionesHhppVt;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtMarcacionesHhppVtDto> getListaTablasMarcacionesHhppVt() {
        return listaTablasMarcacionesHhppVt;
    }

    public void setListaTablasMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> listaTablasMarcacionesHhppVt) {
        this.listaTablasMarcacionesHhppVt = listaTablasMarcacionesHhppVt;
    }

}
