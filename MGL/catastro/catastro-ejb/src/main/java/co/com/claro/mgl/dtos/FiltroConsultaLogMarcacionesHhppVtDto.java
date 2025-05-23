package co.com.claro.mgl.dtos;

import java.util.List;

/**
 * Dto implementado para procesos de consulta en paginación
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */

public class FiltroConsultaLogMarcacionesHhppVtDto {

    long numRegistros = 0;
    List<CmtLogMarcacionesHhppVtDto> listaTablasLogMarcacionesHhppVt;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtLogMarcacionesHhppVtDto> getListaTablasLogMarcacionesHhppVt() {
        return listaTablasLogMarcacionesHhppVt;
    }

    public void setListaTablasLogMarcacionesHhppVt(List<CmtLogMarcacionesHhppVtDto> listaTablasLogMarcacionesHhppVt) {
        this.listaTablasLogMarcacionesHhppVt = listaTablasLogMarcacionesHhppVt;
    }

}
