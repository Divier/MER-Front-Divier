/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ConTblLineasMasivoRequestDto;
import co.com.claro.mer.dtos.request.procedure.CtmGestionSegCMAuditoriaRequestDto;
import co.com.claro.mer.dtos.response.procedure.ConTblLineasMasivoResponseDto;
import co.com.claro.mer.dtos.response.procedure.CtmGestionSegCMAuditoriaResponseDto;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMAuditoriaDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCMAuditoria;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dao de la pagina de seguridad. Permite manejar la
 * conexion a los datos de la entidad
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
public class CtmGestionSegCMAuditoriaMglDaoImpl extends GenericDaoImpl<CtmGestionSegCMAuditoria> {

    private static final Logger LOGGER = LogManager.getLogger(CtmGestionSegCMAuditoriaMglDaoImpl.class);

   
    /**
     * Busca las auditoria de candados de seguridad por id cuenta matriz
     *
     * @author Carlos Caicedo
     * @param cuentaMatriz
     * @return auditoria por id cuenta matriz
     * @throws ApplicationException
     */
   public List<CtmGestionSegCMAuditoriaDto> findAllAuditParentAccount(BigDecimal cuentaMatriz) throws ApplicationException {
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_GESTI_SEG_CM_AUD_PRC);

        CtmGestionSegCMAuditoriaRequestDto requestDto = new CtmGestionSegCMAuditoriaRequestDto(cuentaMatriz);
        storedProcedureUtil.addRequestData(requestDto);
        CtmGestionSegCMAuditoriaResponseDto response = storedProcedureUtil.executeStoredProcedure(CtmGestionSegCMAuditoriaResponseDto.class);
        if (response.getResultado().equalsIgnoreCase(Constant.PROCESO_EXITOSO)) {
            return response.getCursor();
        }
        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.CMT_GESTI_SEG_CM_AUD_PRC
                +": " + response.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);

    }
}
