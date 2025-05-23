package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.util.List;
import java.util.Map;

/**
 * Clase EJB Fachada para obtener informacion de procedimiento almacenado CMT_SOL_NODO_CUAD_FRONT_SP 
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public interface ICmtSolicitudNodoCuadFrontFacadeLocal extends BaseFacadeLocal<CmtSolicitudNodoCuadrante> {

    public List<CmtSolicitudNodoCuadranteDto> findByFilters(Map<String, Object> mFilters, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException;
    public List<String> findAllNodes() throws ApplicationException;
}