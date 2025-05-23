package co.com.claro.mgl.dao.impl;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CmtDisponibilidadGestionNodoCuadranteRequestDto;
import co.com.claro.mer.dtos.request.procedure.CmtSolicitudNodoCuadranteRequestDto;
import co.com.claro.mer.dtos.response.procedure.CmtDisponibilidadGestionNodoCuadranteResponseDto;
import co.com.claro.mer.dtos.response.procedure.CmtSolicitudNodoCuadranteResponseDto;
import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase DAO con la logica para consumir procedimiento almacenado CMT_SOL_NODO_CUADRANTE_SP 
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public class CmtSolicitudNodoCuadranteMglDaoImpl extends GenericDaoImpl<CmtSolicitudNodoCuadrante> {

    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudNodoCuadranteMglDaoImpl.class);

    /**
     * Busca informacion presente en la tabla CMT_SOLICITUD_NODO_CUADRANTE
     *
     * @author Divier Casas
     * @param solicitudId
     * @param codigoNodo
     * @param cuadranteId
     * @param codDivisional
     * @param legado
     * @param resultadoAsociacion
     * @param pageSize
     * @param ordenMayorMenor
     * @param page
     * @return auditoria por id cuenta matriz
     * @throws ApplicationException
     */
    public List<CmtSolicitudNodoCuadranteDto> findByAll(BigDecimal solicitudId, String codigoNodo, String cuadranteId, BigDecimal codDivisional, String legado, String resultadoAsociacion, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_SOL_NODO_CUADRANTE_SP);

        CmtSolicitudNodoCuadranteRequestDto requestDto = new CmtSolicitudNodoCuadranteRequestDto(solicitudId, codigoNodo, cuadranteId, codDivisional, legado, resultadoAsociacion);
        storedProcedureUtil.addRequestData(requestDto);
        CmtSolicitudNodoCuadranteResponseDto response = storedProcedureUtil.executeStoredProcedure(CmtSolicitudNodoCuadranteResponseDto.class);
        if (response.getCode().equals(BigDecimal.valueOf(1L)) || response.getCode().equals(BigDecimal.valueOf(0L))) {
            return pageList(response.getCursor(), ordenMayorMenor, page, pageSize);         
        }        
        String msgError = "Error en el procedimiento almacenado "
                + StoredProcedureNamesConstants.CMT_SOL_NODO_CUADRANTE_SP
                + ": " + response.getMessage();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }
    
    private List<CmtSolicitudNodoCuadranteDto> pageList(List<CmtSolicitudNodoCuadranteDto> result, boolean ordenMayorMenor, int page, int pageSize) {
        
        if(page != 0 && pageSize != 0) {
            if(ordenMayorMenor) {
                return result.stream().sorted(Comparator.comparing(obj -> obj.getSolicitudId(), Comparator.reverseOrder())).skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
            } 
            return result.stream().sorted(Comparator.comparing(obj -> obj.getSolicitudId(), Comparator.naturalOrder())).skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        }
        return result;
    }
    
    public void configDisponibilidadGestion(BigDecimal solicitudId, String disponibilidadGestion) throws ApplicationException {
        
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_UDP_NOD_CUAD_DISP_GS_SP);

        CmtDisponibilidadGestionNodoCuadranteRequestDto requestDto = new CmtDisponibilidadGestionNodoCuadranteRequestDto(solicitudId, disponibilidadGestion);
        storedProcedureUtil.addRequestData(requestDto);
        CmtDisponibilidadGestionNodoCuadranteResponseDto response = storedProcedureUtil.executeStoredProcedure(CmtDisponibilidadGestionNodoCuadranteResponseDto.class);
        if (response.getCode().equals(BigDecimal.valueOf(0L))) {
            return;
        }        
        String msgError = "Error en el procedimiento almacenado "
                + StoredProcedureNamesConstants.CMT_UDP_NOD_CUAD_DISP_GS_SP
                + ": " + response.getMessage();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }
}