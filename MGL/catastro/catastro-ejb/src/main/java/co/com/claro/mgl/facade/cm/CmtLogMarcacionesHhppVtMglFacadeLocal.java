package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroLogMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Fachada para interaccion del bean de la vista log de marcaciones hacia las
 * acciones internas sobre BD.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
public interface CmtLogMarcacionesHhppVtMglFacadeLocal {

    FiltroConsultaLogMarcacionesHhppVtDto findTablasLogMarcacionesHhppVt(FiltroLogMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException;

    Boolean createLogMarcacion(List<CmtLogMarcacionesHhppVtDto> logMarcacionList, String usuario, int perfil) throws ApplicationException;
}
