package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Fachada para interaccion de la vista marcaciones hacia procesos de negocio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
public interface CmtMarcacionesHhppVtMglFacadeLocal {

    FiltroConsultaMarcacionesHhppVtDto findTablasMarcacionesHhppVt(FiltroMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException;

    Boolean updateMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> marcacionesList, String usuario, int perfil)
            throws ApplicationException;

    Boolean sincronizarRazones(FiltroMarcacionesHhppVtDto filtro, String usuario, int perfil)
            throws ApplicationException;

    String consultarTiempoMinimoServicio() throws ApplicationException;

    /**
     * Consulta la lista de marcaciones que aplica para creación de HHPP Virtual
     *
     * @return lista de marcaciones
     */
    List<CmtMarcacionesHhppVtDto> findAllMarcacionesHhppIsAplica() throws ApplicationException;

}
