package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtConvergenciaViabilidadEstratoMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadSegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import java.util.List;

/**
 * Manager Viabilidad HHPP. Contiene la logica de negocio para el
 * manejo de la viabilidad de HHPP en el repositorio.
 *
 * @author Carlos Villamil - HITSS
 * @version 1.00.000
 */
public class CmtConvergenciaViabilidadEstratoMglManager {

    private final CmtConvergenciaViabilidadEstratoMglDaoImpl cmtConvergenciaViabilidadEstratoMglDaoImpl =
            new CmtConvergenciaViabilidadEstratoMglDaoImpl();

    public CmtConvergenciaViabilidadEstratoMgl findByRegla(
            CmtConvergenciaViabilidadEstratoMgl viabilidadEstratoMgl)
            throws ApplicationException {
        return cmtConvergenciaViabilidadEstratoMglDaoImpl.findByRegla(viabilidadEstratoMgl);
    }
    
    public List<CmtConvergenciaViabilidadEstratoMgl> findAll() throws ApplicationException {
        List<CmtConvergenciaViabilidadEstratoMgl> resulList;
        resulList = cmtConvergenciaViabilidadEstratoMglDaoImpl.findAll();
        return resulList;
    }

    public CmtConvergenciaViabilidadEstratoMgl create(CmtConvergenciaViabilidadEstratoMgl cmtConvergenciaViabilidad, String usuario, int perfil) throws ApplicationException {
        return cmtConvergenciaViabilidadEstratoMglDaoImpl.createCm(cmtConvergenciaViabilidad, usuario, perfil);
    }

    public CmtConvergenciaViabilidadEstratoMgl update(CmtConvergenciaViabilidadEstratoMgl cmtConvergenciaViabilidad, String usuario, int perfil) throws ApplicationException {
        return cmtConvergenciaViabilidadEstratoMglDaoImpl.updateCm(cmtConvergenciaViabilidad, usuario, perfil);
    }
    
    /**
     * Busca las Viabilidades de Segmento Estratos. Permite realizar la busqueda de
     * las Viabilidades de HHPP paginando el resultado.
     *
     * @author Laura Carolina Muñoz - HITTSS
     * @param filtro para la consulta
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @return Viabilidades de Segmento Estratos
     * @throws ApplicationException
     */
    public List<CmtConvergenciaViabilidadEstratoMgl> findViabilidadSegEstratoPaginacion(FiltroConsultaViabilidadSegmentoDto filtro, 
            int paginaSelected, int maxResults) throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return cmtConvergenciaViabilidadEstratoMglDaoImpl.findViabilidadSegEstratoPaginacion(filtro,firstResult, maxResults);
    }
    
     /**
     * Cuenta las Viabilidades de Segmento Estratos. Permite realizar el
     * conteo de las Viabilidades de Segmento Estratos.
     * @param filtro para la consulta
     * @author Laura Carolina Muñoz - HITSS
     * @return Conteo Viabilidades de Segmento Estratos
     * @throws ApplicationException
     */
    public int getCountFindByAll(FiltroConsultaViabilidadSegmentoDto filtro) throws ApplicationException {
        return cmtConvergenciaViabilidadEstratoMglDaoImpl.getCountFindByAll(filtro);
    }
}
