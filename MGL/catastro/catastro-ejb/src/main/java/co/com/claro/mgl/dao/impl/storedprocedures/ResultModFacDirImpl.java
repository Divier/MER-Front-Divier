/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.storedprocedures;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ValParamHabCreacionRequestDto;
import co.com.claro.mer.dtos.response.procedure.ValParamHabCreacionResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementación de lógica asociada al consumo de plsql para ResultModFacDir
 *
 * @author Johan Gomez
 * @version 1.1, 2023/07/18 Rev gomezjoj
 */
public class ResultModFacDirImpl {

    private static final Logger LOGGER = LogManager.getLogger(ResultModFacDirImpl.class);
    
    public int valParamHabCreacion(String codPerfil) throws ApplicationException {
        try {
            StoredProcedureUtil sp = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_VAL_PARAM_HAB_CREACION);
            ValParamHabCreacionRequestDto requestDto = new ValParamHabCreacionRequestDto();
            requestDto.setCodPerfil(codPerfil);
            
            sp.addRequestData(requestDto);
            ValParamHabCreacionResponseDto response = sp.executeStoredProcedure(ValParamHabCreacionResponseDto.class);
            int habCreacion = 0;                        

            if (response.getCodigo() == 0) {
                habCreacion = response.getHabilitaCreacion();
            }
            return habCreacion;
        } catch (ApplicationException ex) {
            String msgError = "Error ejecutando el Procedimiento almacenado.." + StoredProcedureNamesConstants.SP_VAL_PARAM_HAB_CREACION;
            LOGGER.error(msgError, ex.getMessage());
            throw ex;

        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    
    }
}
