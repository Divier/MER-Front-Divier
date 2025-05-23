package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtMarcacionesHhppVtMglDaoImpl;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Controlador para acciones del crud asociadas a marcaciones para hhpp
 * virtuales.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
public class CmtMarcacionesHhppVtMglManager {

    /**
     * Obtiene la información del total de registros o la información completa, en función del filtro aplicado
     *
     * @param filtro      Parámetros de filtro para la consulta de marcaciones
     * @param firstResult Inicio del rango de registros a devolver
     * @param maxResults  Fin del rango de registros de marcaciones a devolver
     * @return filtroConsulta Retorna la cantidad de registros o la lista de registros completa según el caso
     * @throws ApplicationException Excepción personalizada de la aplicacion
     * @see FiltroConsultaMarcacionesHhppVtDto
     */
    public FiltroConsultaMarcacionesHhppVtDto getTablasMarcacionesHhppVtSearch(FiltroMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        List<CmtMarcacionesHhppVtDto> tablasMarcacionesHhppMglList;

        FiltroConsultaMarcacionesHhppVtDto filtroConsulta;
        filtroConsulta = cmtMarcacionesHhppVtMglDaoImpl.findTablasMarcacionesHhppVtSearch(filtro, firstResult, maxResults);

        tablasMarcacionesHhppMglList = filtroConsulta.getListaTablasMarcacionesHhppVt();
        filtroConsulta.setListaTablasMarcacionesHhppVt(tablasMarcacionesHhppMglList);
        return filtroConsulta;
    }

    /**
     * Consulta el tiempo minimo de traslado de hhpp virtual, asumido como
     * tiempo minimo del servicio en la vista.
     *
     * @return tiempoMinServicio Retorna el valor del tiempo mínimo del servicio
     * @throws ApplicationException Excepción de la App
     */
    public String findTiempoMinimoServicio() throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        return cmtMarcacionesHhppVtMglDaoImpl.findTiempoMinimoServicio();
    }

    /**
     * Sincroniza las marcaciones en MER con las razones y sub razones del
     * servicio rest.
     *
     * @param filtro  Parámetros de filtro aplicables en la consulta de marcaciones al realizar la sincronización
     * @param usuario Nombre de usuario actual conectado en la sesión de la aplicación
     * @param perfil  Número de perfil del usuario actual conectado en la sesión
     * @return {@link Boolean} Retorna true si se ejecuta la sincronización de razones (marcaciones)
     * @throws ApplicationException Excepción de la App
     */
    public Boolean sincronizarRazones(FiltroMarcacionesHhppVtDto filtro, String usuario, int perfil)
            throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        return cmtMarcacionesHhppVtMglDaoImpl.sincronizarRazones(filtro, usuario, perfil);
    }

    /**
     * Consulta las marcaciones para creacion de hhpp virtual
     *
     * @param filtro Parámetros usados para filtrar la consulta de marcaciones de HHPP
     */
    public void consultarMarcaciones(FiltroMarcacionesHhppVtDto filtro) throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        cmtMarcacionesHhppVtMglDaoImpl.consultarMarcacionesHhppSp(filtro);
    }

    /**
     * Actualiza las marcaciones de hhpp
     *
     * @param marcacionesList Lista de marcaciones que van a ser actualizadas
     * @param usuario         Nombre de usuario conectado actualmente en la sesión de la aplicación
     * @param perfil          Número de perfil del usuario conectado actualmente en la sesión de la aplicación
     * @return {@link Boolean} Retorna true cuando se ejecuta una actualización exitosa de marcaciones.
     */
    public Boolean updateMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> marcacionesList, String usuario, int perfil) throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        return cmtMarcacionesHhppVtMglDaoImpl.updateMarcacionesHhppVt(marcacionesList, usuario, perfil,false);
    }

    /**
     * Consulta las marcaciones que si aplican para creación de HHPP Virtual
     *
     * @return {@link List<CmtMarcacionesHhppVtDto>} Lista de marcaciones
     * @throws ApplicationException Excepción de la APP {@link ApplicationException}
     */
    public List<CmtMarcacionesHhppVtDto> findAllMarcacionesHhppIsAplica() throws ApplicationException {
        CmtMarcacionesHhppVtMglDaoImpl cmtMarcacionesHhppVtMglDaoImpl = new CmtMarcacionesHhppVtMglDaoImpl();
        return cmtMarcacionesHhppVtMglDaoImpl.findAllMarcacionesHhppIsAplica();
    }
}
