/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaSlaOtDto;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoDto;
import co.com.claro.mgl.dtos.TipoOtCmDirDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ortizjafs
 */
public interface CmtTipoOtMglFacadeLocal extends BaseFacadeLocal<CmtTipoOtMgl> {

    public void setUser(String user, int perfil) throws ApplicationException;

    @Override
    List<CmtTipoOtMgl> findAll() throws ApplicationException;

    List<CmtTipoOtMgl> findByBasicaId(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException;

    CmtTipoOtMgl findById(BigDecimal idTipoOt) throws ApplicationException;

    FiltroConsultaSlaOtDto findListTablaSlaOt(FiltroConsultaSubTipoDto filtro,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;
    
        /**
     * Busca el subtipo de ot por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la tabla
     * @return CmtTipoOtMgl encontrado
     * @throws ApplicationException
     */
     CmtTipoOtMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException;
     
         /**
     * Busca los subtipo de ot escluyendo los de acometida u otro
     *
     * @author Victor Bocanegra
     * @param idAco id de acometidas u otro
     * @return List<CmtTipoOtMgl> encontrado
     * @throws ApplicationException
     */
     List<CmtTipoOtMgl> findByNoIdAco(List<BigDecimal> idAco) throws ApplicationException;

             /**
     * Obtiene todos los Tipo de Ordenes de Trabajo que generan OT de acometidas
     * que existen en el repositorio.
     *
     * @param tipoOtAcometida tipo OT acometida
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
      List<CmtTipoOtMgl> findAllTipoOtGeneraAco(CmtTipoOtMgl tipoOtAcometida)
            throws ApplicationException; 
      
          /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo que generan acometidas
     * en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
     List<CmtTipoOtMgl> findAllTipoOtAcometidas()
            throws ApplicationException;
     
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo de un tipode trabajo
     * que no son acometidas son VTs en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl Tipo de trabajo
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    List<CmtTipoOtMgl> findByTipoTrabajoAndIsVT(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo Vts que generan
     * acometidas en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    List<CmtTipoOtMgl> findAllTipoOtVts()
            throws ApplicationException;
    
    
    
    
     /**
     * Obtiene lista de subtipo de orden ccmm y hhpp
     *
     * @author cardenaslb
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    List<TipoOtCmDirDto> findAllSubTipoOtCmHhpp()
            throws ApplicationException;
    
    
       /**
     * Obtiene todos los Sub Tipo de Ordenes de Trabajo segun la tecnologia
     *
     * @author bocanegravm
     * @param basicaTecnologia
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    List<CmtTipoOtMgl> findSubTipoOtByTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException;
}
