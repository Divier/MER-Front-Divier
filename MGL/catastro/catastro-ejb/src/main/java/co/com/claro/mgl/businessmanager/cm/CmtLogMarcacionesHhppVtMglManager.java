package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtLogMarcacionesHhppVtMglDaoImpl;
import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroLogMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Controlador para acciones del crud asociadas a marcaciones para hhpp
 * virtuales.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
public class CmtLogMarcacionesHhppVtMglManager {

    /**
     * @param filtro      Información para filtro en consulta de registros de log marcaciones
     * @param firstResult Inicio de rango de selección de datos al momento de paginado
     * @param maxResults  Terminación de rango de selección de datos al momento de paginado
     * @return filtroConsulta Resultado del proceso de consulta de información en función del filtro aplicado
     * @throws ApplicationException Excepción de la App
     * @see FiltroConsultaLogMarcacionesHhppVtDto
     */
    public FiltroConsultaLogMarcacionesHhppVtDto getTablasLogMarcacionesHhppVtSearch(
            FiltroLogMarcacionesHhppVtDto filtro, int firstResult, int maxResults) throws ApplicationException {

        CmtLogMarcacionesHhppVtMglDaoImpl cmtLogMarcacionesHhppVtMglDaoImpl = new CmtLogMarcacionesHhppVtMglDaoImpl();
        List<CmtLogMarcacionesHhppVtDto> tablasLogMarcacionesHhppMglList;
        FiltroConsultaLogMarcacionesHhppVtDto filtroConsulta;

        filtroConsulta = cmtLogMarcacionesHhppVtMglDaoImpl.findTablasLogMarcacionesHhppVtSearch(filtro, firstResult, maxResults);
        tablasLogMarcacionesHhppMglList = filtroConsulta.getListaTablasLogMarcacionesHhppVt();

        filtroConsulta.setListaTablasLogMarcacionesHhppVt(tablasLogMarcacionesHhppMglList);
        return filtroConsulta;
    }

    /**
     * Crear las marcaciones para creación de HHPP virtual
     *
     * @param logMarcacionesList Listado de logs de marcaciones a crear.
     * @param usuario            Nombre del usuario actual conectado en la sesión
     * @param perfil             Número del perfil de usuario conectado en la sesión
     * @return {@link Boolean} Retorna true cuando se realiza el registro de marcaciones.
     */
    public Boolean createLogMarcacionesHhppVt(List<CmtLogMarcacionesHhppVtDto> logMarcacionesList, String usuario, int perfil)
            throws ApplicationException {
        CmtLogMarcacionesHhppVtMglDaoImpl cmtLogMarcacionesHhppVtMglDaoImpl = new CmtLogMarcacionesHhppVtMglDaoImpl();
        return cmtLogMarcacionesHhppVtMglDaoImpl.createLogMarcacionesHhppVt(logMarcacionesList, usuario, perfil);
    }

}
