/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.cmas400.ejb.request.RequestDataConstructorasMgl;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtCompaniaMglFacadeLocal extends BaseFacadeLocal<CmtCompaniaMgl> {

    CmtCompaniaMgl findById(BigDecimal id) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<CmtCompaniaMgl> findByTipoCompania(CmtTipoCompaniaMgl tipoCompania) throws ApplicationException;

    List<AuditoriaDto> construirAuditoria(CmtCompaniaMgl cmtCompaniaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * Busca las compañias Ascensores, Administracion y Constructoras de acuerdo
     * a la ciudad o centro poblado
     *
     * @author yimy diaz
     * @param municipio
     * @param tipoCompania
     * @return List<CmtCompaniaMgl>
     * @throws ApplicationException
     */
    List<CmtCompaniaMgl> findByMunicipioByTipeCompany(GeograficoPoliticoMgl municipio,
            CmtTipoCompaniaMgl tipoCompania) throws ApplicationException;

   List<CmtCompaniaMgl> findByfiltro(FiltroConsultaCompaniasDto filtro,boolean ordenarPorCodigo) throws ApplicationException;

    String buscarUltimoCodigoNumerico(CmtTipoCompaniaMgl tipoCompaniaMgl) throws ApplicationException;
    
    /**
     * Autor: Victor Bocanegra Metodo encargado de consultar los registros de
     * constructoras en la tabla CMT_COMPANIAS.
     *
     * @param request
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    ResponseConstructorasList constructorasQueryMgl(
            RequestDataConstructorasMgl request) throws ApplicationException;
    
      /**
     * *Victor Bocanegra Metodo para conseguir las compañias por los filtros de
     * la tabla y paginado
     *
     * @param paginaSelected
     * @param maxResults
     * @param filtro
     * @param ordenarPorCodigo
     * @return List<CmtCompaniaMgl>
     * @throws ApplicationException
     */
    List<CmtCompaniaMgl> findByfiltroAndPaginado(int paginaSelected,
            int maxResults, FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException;

    /**
     * *Victor Bocanegra Metodo para contar las compañias por los filtros de la
     * tabla
     *
     * @param filtro
     * @param ordenarPorCodigo
     * @return Long
     * @throws ApplicationException
     */
    Long countByCompaFiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException;
}
