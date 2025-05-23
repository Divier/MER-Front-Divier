/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadesRrDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface CmtComunidadRrFacadeLocal {

    /**
     * Victor Bocanegra Metodo para traer todas las comunidades
     *
     * @return List<CmtComunidadRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtComunidadRr> findAll() throws ApplicationException;

    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<CmtComunidadRr>
     * @throws ApplicationException
     */
    PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadesRrDto consulta) throws ApplicationException;

    CmtComunidadRr findByCodigoRR(String codigo) throws ApplicationException;

    /**
     * *Victor Bocanegra Metodo para buscar las comunidades de una region
     *
     * @param idRegional
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    List<CmtComunidadRr> findByIdRegional(BigDecimal idRegional)
            throws ApplicationException;
    
    /**
     * *Victor Bocanegra Metodo para buscar una comunidad por id
     *
     * @param idComunidad
     * @return CmtComunidadRr
     * @throws ApplicationException
     */
    CmtComunidadRr findByIdComunidad(BigDecimal idComunidad)
            throws ApplicationException;
    
    
    
     /**
     * *cardenaslb Metodo para buscar las comunidades de una region por lista de Regionales
     *
     * @param idRegional
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    List<CmtComunidadRr> findByListRegional(List<BigDecimal> idRegional)
            throws ApplicationException;
    
    
    
    
     /**
     * *cardenaslb Metodo para buscar las comunidades de una region por lista de Regionales
     *
     * @param idComunidad
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    List<CmtComunidadRr> findByListComunidad(List<BigDecimal> idComunidad)
            throws ApplicationException;
}
