/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CtmGestionSegCMConRequestDto;
import co.com.claro.mer.dtos.request.procedure.CtmGestionSegCMIntRequestDto;
import co.com.claro.mer.dtos.response.procedure.CtmGestionSegCMResponseDto;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCM;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dao de la entidad CtmGestionSegCMDaoImpl. Permite manejar
 * los datos de la cuenta matriz
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
public class CtmGestionSegCMDaoImpl extends GenericDaoImpl<CtmGestionSegCM> {
    
    private static final Logger LOGGER = LogManager.getLogger(CtmGestionSegCMDaoImpl.class);
 
  
    /**
     * Busca los gestion de seguridad por id cuenta matriz
     *
     * @author Carlos Caicedo
     * @param cuentaMatriz
     * @return gestion por id cuenta matriz
     * @throws ApplicationException
     */
    public CtmGestionSegCMDto findAllManagementAccount(BigDecimal cuentaMatriz) throws ApplicationException {
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_CON_GESTI_SEG_CM_PRC);

        CtmGestionSegCMConRequestDto requestDto = new CtmGestionSegCMConRequestDto(cuentaMatriz);
        storedProcedureUtil.addRequestData(requestDto);
        CtmGestionSegCMResponseDto response = storedProcedureUtil.executeStoredProcedure(CtmGestionSegCMResponseDto.class);
        if (response.getResultado().equalsIgnoreCase(Constant.PROCESO_EXITOSO)) {
            if(!response.getCursor().isEmpty()){
            return response.getCursor().get(0);
            }            
            return new CtmGestionSegCMDto();
        }
        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.CMT_CON_GESTI_SEG_CM_PRC
                +": " + response.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);

    }
    
 
     /**
     * guarda o actualiza un registro
     *
     * @author Carlos Caicedo
     * @param ctmGestionSegCMDto
     * @return gestion por id cuenta matriz
     * @throws ApplicationException
     */
    public CtmGestionSegCMDto save(CtmGestionSegCMDto ctmGestionSegCMDto) throws ApplicationException {
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_INS_GESTI_SEG_CM_PRC);
        
        BigDecimal id=ctmGestionSegCMDto.getId()!=null?ctmGestionSegCMDto.getId():null;
        BigDecimal tipoCerradElect=ctmGestionSegCMDto.getTipoCerradElect()!=null?ctmGestionSegCMDto.getTipoCerradElect():null;
        String serial=ctmGestionSegCMDto.getSerial()!= null && !ctmGestionSegCMDto.getSerial().isEmpty()?ctmGestionSegCMDto.getSerial():null;
        String fabricante=ctmGestionSegCMDto.getFabricante()!= null && !ctmGestionSegCMDto.getFabricante().isEmpty()?ctmGestionSegCMDto.getFabricante():null;
        CtmGestionSegCMIntRequestDto requestDto = new CtmGestionSegCMIntRequestDto(id,ctmGestionSegCMDto.getCmtCuentaMatrizMgl(),ctmGestionSegCMDto.getCerraduraElect(),tipoCerradElect,serial,fabricante,ctmGestionSegCMDto.getPropSitioNombre(),ctmGestionSegCMDto.getPropSitioCelular(),ctmGestionSegCMDto.getUsuarioActualiza(),ctmGestionSegCMDto.getNombreJefeZona(),ctmGestionSegCMDto.getCelularJefeZona());
        storedProcedureUtil.addRequestData(requestDto);
        System.out.println("Request: "+requestDto.toString());
        CtmGestionSegCMResponseDto response = storedProcedureUtil.executeStoredProcedure(CtmGestionSegCMResponseDto.class);
        if (response.getResultado().equalsIgnoreCase(Constant.PROCESO_EXITOSO)) {
            if (!response.getCursor().isEmpty()) {
                return response.getCursor().get(0);
            }
            return new CtmGestionSegCMDto();
        }
        String msgError = "Error en el procedimiento almacenado "
                +StoredProcedureNamesConstants.CMT_INS_GESTI_SEG_CM_PRC
                +": " + response.getResultado();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);

    }
}