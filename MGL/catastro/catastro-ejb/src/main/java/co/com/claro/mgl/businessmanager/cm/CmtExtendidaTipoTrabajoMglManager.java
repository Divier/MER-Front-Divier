/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtExtendidaTipoTrabajoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTipoTrabajoMgl;
import co.com.claro.mgl.dtos.FiltroConsultaExtendidaTipoTrabajoDtos;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class CmtExtendidaTipoTrabajoMglManager {

    CmtExtendidaTipoTrabajoMglDaoImpl dao = new CmtExtendidaTipoTrabajoMglDaoImpl();

    /**
     * Crea un complemento de tipo de trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtExtendidaTipoTrabajoMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un complemento de tipo de trabajo
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtExtendidaTipoTrabajoMgl create(CmtExtendidaTipoTrabajoMgl cmtExtendidaTipoTrabajoMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        cmtExtendidaTipoTrabajoMgl = dao.createCm(cmtExtendidaTipoTrabajoMgl, usuarioCrea, perfilCrea);
        return cmtExtendidaTipoTrabajoMgl;
    }

    /**
     * Actualiza un complemento de tipo de trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtExtendidaTipoTrabajoMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un complemento de tipo de trabajo
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public CmtExtendidaTipoTrabajoMgl update(CmtExtendidaTipoTrabajoMgl cmtExtendidaTipoTrabajoMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        cmtExtendidaTipoTrabajoMgl = dao.updateCm(cmtExtendidaTipoTrabajoMgl, usuarioCrea, perfilCrea);
        return cmtExtendidaTipoTrabajoMgl;
    }

    /**
     * Elimina un complemento de tipo de trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtExtendidaTipoTrabajoMgl
     * @param cmtExtendidaTecnologiaMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return true si la transacion es satisfatoria false si no
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public boolean delete(CmtExtendidaTipoTrabajoMgl cmtExtendidaTipoTrabajoMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        return dao.deleteCm(cmtExtendidaTipoTrabajoMgl, usuarioCrea, perfilCrea);

    }

    /**
     * Busca lista de complementos de un tipo de trabajo
     *
     * @author Victor Bocanegra
     * @param filtro para la consulta
     * @param paginaSelected
     * @param maxResults
     * @param cmtBasicaMgl
     * @return List<CmtExtendidaTipoTrabajoMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObj(FiltroConsultaExtendidaTipoTrabajoDtos filtro, 
            int paginaSelected, int maxResults, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return dao.findBytipoTrabajoObj(filtro, firstResult, maxResults, cmtBasicaMgl);
    }
    
        /**
     * Cuenta lista de complementos de un tipo de trabajo
     *
     * @author Victor Bocanegra
     * @param filtro para la consulta
     * @param cmtBasicaMgl
     * @return long
     * @throws ApplicationException
     */
    public long findExtendidasSearchContar(FiltroConsultaExtendidaTipoTrabajoDtos filtro, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        
         return dao.findExtendidasSearchContar(filtro,cmtBasicaMgl);
    }
    
    /**
     * Busca un complemento de un tipo de trabajo asociado a una comunidad
     *
     * @author Victor Bocanegra
     * @param cmtComunidadRr
     * @param cmtBasicaMgl
     * @return List<CmtExtendidaTipoTrabajoMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObjAndCom
            (CmtComunidadRr cmtComunidadRr, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        return dao.findBytipoTrabajoObjAndCom(cmtComunidadRr, cmtBasicaMgl);
    }
            
      /**
     * Cuenta total extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @return long
     * @throws ApplicationException
     */
    public long findExtendidasTrabajoCount() throws ApplicationException {

        return dao.findExtendidasTrabajoCount();
    }
    
    /**
     * Busca lista de extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @param paginaSelected
     * @param maxResults
     * @return List<CmtExtendidaTipoTrabajoMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtExtendidaTipoTrabajoMgl> findExtendidasTipoTrabajo(
            int paginaSelected, int maxResults)
            throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        return dao.findExtendidasTipoTrabajo(firstResult, maxResults);
    }
}
