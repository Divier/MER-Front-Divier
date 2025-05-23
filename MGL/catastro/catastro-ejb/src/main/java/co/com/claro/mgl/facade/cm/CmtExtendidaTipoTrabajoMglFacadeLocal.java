/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

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
public interface CmtExtendidaTipoTrabajoMglFacadeLocal {

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
            String usuarioCrea, int perfilCrea) throws ApplicationException;

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
            String usuarioCrea, int perfilCrea) throws ApplicationException;

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
            String usuarioCrea, int perfilCrea) throws ApplicationException;

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
    List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObj(FiltroConsultaExtendidaTipoTrabajoDtos filtro, 
            int paginaSelected, int maxResults, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;

    /**
     * Cuenta lista de complementos de un tipo de trabajo
     *
     * @author Victor Bocanegra
     * @param filtro para la consulta
     * @param cmtBasicaMgl
     * @return long
     * @throws ApplicationException
     */
    long findExtendidasSearchContar(FiltroConsultaExtendidaTipoTrabajoDtos filtro, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;
    
        /**
     * Busca un complemento de un tipo de trabajo asociado a una comunidad
     *
     * @author Victor Bocanegra
     * @param cmtComunidadRr
     * @param cmtBasicaMgl
     * @return List<CmtExtendidaTipoTrabajoMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
      List<CmtExtendidaTipoTrabajoMgl> findBytipoTrabajoObjAndCom
            (CmtComunidadRr cmtComunidadRr, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;
            
    /**
     * Cuenta total extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @return long
     * @throws ApplicationException
     */
    long findExtendidasTrabajoCount() throws ApplicationException; 
    
        /**
     * Busca lista de extendidas tipo trabajo
     *
     * @author Victor Bocanegra
     * @param paginaSelected
     * @param maxResults
     * @return List<CmtExtendidaTipoTrabajoMgl> encontradas en el repositorio
     * @throws ApplicationException
     */
    List<CmtExtendidaTipoTrabajoMgl> findExtendidasTipoTrabajo(
            int paginaSelected, int maxResults)
            throws ApplicationException; 
}
