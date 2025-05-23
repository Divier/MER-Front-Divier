/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.storedprocedures;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CmtCruCtechRequestDto;
import co.com.claro.mer.dtos.response.procedure.CmtCruCtechResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.dtos.CmtFiltroProyectosDto;
import co.com.claro.mgl.dtos.CmtTecSiteSapRespDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementación de lógica asociada al consumo de plsql 
 * para cuenta matriz
 *
 * @author Johan Gomez
 * @version 1.1, 2024/12/09 Rev gomezjoj
 */
public class CmtCuentaMatrizImpl {
    
    private static final Logger LOGGER = LogManager.getLogger(CargueFichasImpl.class);
    
    public CmtTecSiteSapRespDto CmtTechnicalSiteSapSp(CmtFiltroProyectosDto filtroProy) throws ApplicationException{
        try{    
            
            
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_CRU_CTECH_CM);
            CmtCruCtechRequestDto requestDto = new CmtCruCtechRequestDto();
            assignInputParameters(filtroProy, requestDto);
            sp.addRequestData(requestDto);
            
            CmtCruCtechResponseDto responseSp = sp.executeStoredProcedure(CmtCruCtechResponseDto.class);
            CmtTecSiteSapRespDto response = new CmtTecSiteSapRespDto();
            
            response.setCodigo(responseSp.getCodigo());
            response.setResultado(responseSp.getResultado());
            response.setListaReadCtechDto(responseSp.getListaReadCtechDto());
            
            
            return response;
        
        } catch (ApplicationException ex) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_CRU_CTECH_CM;
            LOGGER.error(msgError, ex.getMessage());
            throw ex;

        } catch (Exception e) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." +StoredProcedureNamesConstants.SP_CRU_CTECH_CM+" Error en: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }
    
    private void assignInputParameters(CmtFiltroProyectosDto filtro, CmtCruCtechRequestDto request){
        
        request.setOpcion(filtro.getOpcion());
        request.setSitio(filtro.getSitio());
        request.setDaneMunicipio(filtro.getDaneMunicipio());
        request.setCentroPoblado(filtro.getCentroPoblado());
        request.setUbicacionTecnica(filtro.getUbicacionTecnica());
        request.setDaneCp(filtro.getDaneCp());
        request.setIdSitio(filtro.getIdSitio());
        request.setCcmm(filtro.getCcmm());
        request.setTipoSitio(filtro.getTipoSitio());
        request.setDisponibilidad(filtro.getDisponibilidad());
        
        request.setUsuarioCreacion(filtro.getUsuarioCreacion());
        request.setUsuarioEdicion(filtro.getUsuarioEdicion());
        request.setEstadoRegistro(filtro.getEstadoRegistro());
        
    
    }
    
}
