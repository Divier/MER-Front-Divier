package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtLogMarcacionesHhppVtMglManager;
import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroLogMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Fachada para implementación de log marcaciones para creación  de HHPP VT
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
@Stateless
public class CmtLogMarcacionesHhppVtMglFacade implements CmtLogMarcacionesHhppVtMglFacadeLocal {

    @Override
    public FiltroConsultaLogMarcacionesHhppVtDto findTablasLogMarcacionesHhppVt(FiltroLogMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        CmtLogMarcacionesHhppVtMglManager cmtLogMarcacionesHhppVtMglManager = new CmtLogMarcacionesHhppVtMglManager();
        return cmtLogMarcacionesHhppVtMglManager.getTablasLogMarcacionesHhppVtSearch(filtro, firstResult, maxResults);
    }

    @Override
    public Boolean createLogMarcacion(List<CmtLogMarcacionesHhppVtDto> logMarcacionList, String usuario, int perfil) throws ApplicationException {
        CmtLogMarcacionesHhppVtMglManager cmtLogMarcacionesHhppVtMglManager = new CmtLogMarcacionesHhppVtMglManager();
        return cmtLogMarcacionesHhppVtMglManager.createLogMarcacionesHhppVt(logMarcacionList, usuario, perfil);
    }

}
