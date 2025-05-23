package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtConvergenciaViabilidadHhppMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import java.util.List;

/**
 * Manager Viabilidad HHPP. Contiene la logica de negocio para el
 * manejo de la viabilidad de HHPP en el repositorio.
 *
 * @author Carlos Villamil - HITSS
 * @version 1.00.000
 */
public class CmtConvergenciaViabilidadHhppMglManager {

    private CmtConvergenciaViabilidadHhppMglDaoImpl cmtConvergenciaViabilidadHhppMglDaoImpl =
            new CmtConvergenciaViabilidadHhppMglDaoImpl();

    public CmtConvergenciaViabilidadHhppMgl findByRegla(
            CmtConvergenciaViabilidadHhppMgl cmtConvergenciaViabilidadHhppMgl)
            throws ApplicationException {
        return cmtConvergenciaViabilidadHhppMglDaoImpl.findByRegla(cmtConvergenciaViabilidadHhppMgl);
    }
    
    public List<CmtConvergenciaViabilidadHhppMgl> findAll() throws ApplicationException {
        List<CmtConvergenciaViabilidadHhppMgl> resulList;
        resulList = cmtConvergenciaViabilidadHhppMglDaoImpl.findAll();
        return resulList;
    }

    public CmtConvergenciaViabilidadHhppMgl create(CmtConvergenciaViabilidadHhppMgl cmtConvergenciaViabilidad, String usuario, int perfil) throws ApplicationException {
        return cmtConvergenciaViabilidadHhppMglDaoImpl.createCm(cmtConvergenciaViabilidad, usuario, perfil);
    }

    public CmtConvergenciaViabilidadHhppMgl update(CmtConvergenciaViabilidadHhppMgl cmtConvergenciaViabilidad, String usuario, int perfil) throws ApplicationException {
        return cmtConvergenciaViabilidadHhppMglDaoImpl.updateCm(cmtConvergenciaViabilidad, usuario, perfil);
    }
    
      /**
     * Busca las Viabilidades de HHPP. Permite realizar la busqueda de
     * las Viabilidades de HHPP paginando el resultado.
     *
     * @author Laura Carolina Muñoz - HITTSS
     * @param filtro filtro para la consulta
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @return Viabilidades de HHPP
     * @throws ApplicationException
     */
    public List<CmtConvergenciaViabilidadHhppMgl> findViabilidadHhppPaginacion
        (FiltroConsultaViabilidadHhppDto filtro,
            int paginaSelected, int maxResults) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return cmtConvergenciaViabilidadHhppMglDaoImpl.findViabilidadHhppPaginacion(filtro, firstResult, maxResults);
    }

    /**
     * Cuenta las Viabilidades de HHPP. Permite realizar el
     * conteo de las Viabilidades de HHPP.
     * @param filtro para la consulta
     * @author Laura Carolina Muñoz - HITSS
     * @return Conteo Viabilidades de HHPP
     * @throws ApplicationException
     */
    public int getCountFindByAll(FiltroConsultaViabilidadHhppDto filtro) throws ApplicationException {
        return cmtConvergenciaViabilidadHhppMglDaoImpl.getCountFindByAll(filtro);
    }
}
