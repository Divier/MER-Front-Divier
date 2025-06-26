package co.com.claro.mgl.dao.impl;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CmtSolicitudNodoCuadFrontRequestDto;
import co.com.claro.mer.dtos.response.procedure.CmtSolicitudNodoCuadranteResponseDto;
import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase DAO con la logica para consumir procedimiento almacenado CMT_SOL_NODO_CUAD_FRONT_SP 
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public class CmtSolicitudNodoCuadFrontMglDaoImpl extends GenericDaoImpl<CmtSolicitudNodoCuadrante> {

    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudNodoCuadranteMglDaoImpl.class);
    private static final String ALL_NODES = "ALL";

    /**
     * Busca informacion presente en la tabla CMT_SOLICITUD_NODO_CUADRANTE
     *
     * @author Divier Casas
     * @param mFilters
     * @param pageSize
     * @param ordenMayorMenor
     * @param page
     * @return auditoria por id cuenta matriz
     * @throws ApplicationException
     */
    public List<CmtSolicitudNodoCuadranteDto> findByFilters(Map<String, Object> mFilters, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_SOL_NODO_CUAD_FRONT_SP);

        CmtSolicitudNodoCuadFrontRequestDto requestDto = configFilters(mFilters);
        storedProcedureUtil.addRequestData(requestDto);
        CmtSolicitudNodoCuadranteResponseDto response = storedProcedureUtil.executeStoredProcedure(CmtSolicitudNodoCuadranteResponseDto.class);
        if (response.getCode().equals(BigDecimal.valueOf(1L)) || response.getCode().equals(BigDecimal.valueOf(0L))) {
            return pageList(response.getCursor(), ordenMayorMenor, page, pageSize);         
        }        
        String msgError = "Error en el procedimiento almacenado "
                + StoredProcedureNamesConstants.CMT_SOL_NODO_CUAD_FRONT_SP
                + ": " + response.getMessage();

        LOGGER.error(msgError);
        throw new ApplicationException(msgError);
    }
    
    public List<String> findAllNodes() throws ApplicationException {
        
        StoredProcedureUtil storedProcedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_SOL_NODO_CUAD_FRONT_SP);

        CmtSolicitudNodoCuadFrontRequestDto requestDto = new CmtSolicitudNodoCuadFrontRequestDto();
        requestDto.setCodigoNodo(ALL_NODES);
        storedProcedureUtil.addRequestData(requestDto);
        CmtSolicitudNodoCuadranteResponseDto response = storedProcedureUtil.executeStoredProcedure(CmtSolicitudNodoCuadranteResponseDto.class);
        if (response.getCode().equals(BigDecimal.valueOf(1L))) {
            return response.getCursor().stream().map(CmtSolicitudNodoCuadranteDto::getCodigoNodo).distinct().sorted().collect(Collectors.toList());
        }  
        String msgError = "Error en el procedimiento almacenado "
                + StoredProcedureNamesConstants.CMT_SOL_NODO_CUAD_FRONT_SP
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
        
    private CmtSolicitudNodoCuadFrontRequestDto configFilters(Map<String, Object> mFilters) {
                
        BigDecimal solicitudId = mFilters.get("solicitudId") != null ? (BigDecimal) mFilters.get("solicitudId") : null;
        String cuadranteId = mFilters.get("cuadranteId") != null ? mFilters.get("cuadranteId").toString() : null;
        BigDecimal codDivisional = mFilters.get("codDivisional") != null ? (BigDecimal) mFilters.get("codDivisional") : null;
        BigDecimal codDepto = mFilters.get("codDepto") != null ? (BigDecimal) mFilters.get("codDepto") : null;
        BigDecimal codCiudad = mFilters.get("codCiudad") != null ? (BigDecimal) mFilters.get("codCiudad") : null;
        BigDecimal codCentroPoblado = mFilters.get("codCentroPoblado") != null ? (BigDecimal) mFilters.get("codCentroPoblado") : null;
        String codigoNodo = mFilters.get("codigoNodo") != null ? (String) mFilters.get("codigoNodo") : null;
        Integer codEstado = mFilters.get("codEstado") != null ? (Integer) mFilters.get("codEstado") : null;
        String legado = mFilters.get("legado") != null ? mFilters.get("legado").toString() : null;
        String resultadoAsociacion = mFilters.get("resultadoAsociacion") != null ? mFilters.get("resultadoAsociacion").toString() : null;
        
        return new CmtSolicitudNodoCuadFrontRequestDto(solicitudId, cuadranteId, codDivisional, codDepto, codCiudad, codCentroPoblado, codigoNodo, codEstado, legado, resultadoAsociacion);
    }
}

