package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtMarcacionesHhppVtMglManager;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Fachada para implementación de marcaciones para creacion de de HHPP VT
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
@Stateless
public class CmtMarcacionesHhppVtMglFacade implements CmtMarcacionesHhppVtMglFacadeLocal {

    @Override
    public FiltroConsultaMarcacionesHhppVtDto findTablasMarcacionesHhppVt(FiltroMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
        return cmtMarcacionesHhppVtMglManager.getTablasMarcacionesHhppVtSearch(filtro, firstResult, maxResults);
    }

    @Override
    public Boolean updateMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> marcacionesList, String usuario, int perfil)
            throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
       return cmtMarcacionesHhppVtMglManager.updateMarcacionesHhppVt(marcacionesList, usuario, perfil);
    }

    @Override
    public Boolean sincronizarRazones(FiltroMarcacionesHhppVtDto filtro, String usuario, int perfil)
            throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
        return cmtMarcacionesHhppVtMglManager.sincronizarRazones(filtro, usuario, perfil);
    }

    @Override
    public String consultarTiempoMinimoServicio() throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
        return cmtMarcacionesHhppVtMglManager.findTiempoMinimoServicio();
    }

    @Override
    public List<CmtMarcacionesHhppVtDto> findAllMarcacionesHhppIsAplica() throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
        return cmtMarcacionesHhppVtMglManager.findAllMarcacionesHhppIsAplica();
    }
}
