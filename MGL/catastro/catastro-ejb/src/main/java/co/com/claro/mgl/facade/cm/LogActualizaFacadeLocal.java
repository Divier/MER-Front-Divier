package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Facade para consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
public interface LogActualizaFacadeLocal extends BaseFacadeLocal<LogActualizaMaster> {
    
    List<LogActualizaMaster> getListLogActualizaByTroncal(String troncal) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaGroupByTroncal() throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByTipoTecnologia(String tipoTecnologia) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaGroupByTipoTec() throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByLikeNombreArchivo(String nombreArchivo) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByLikeDepartamento(String departamento) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaGroupByDepartamento() throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByCiudad(String ciudad) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaGroupByCiudad() throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByCentroPoblado(String centroPoblado) throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaGroupByCentroPoblado() throws ApplicationException;
    List<LogActualizaMaster> getListLogActualizaByLikeUsuarioCreacion(String usuario) throws ApplicationException;
    List<LogActualizaMaster> getListFichaToCreateByDate(int page, int PAGINACION_DIEZ_FILAS, boolean b, HashMap<String, Object> params, Date fechaInicial, Date fechaFinal) throws ApplicationException;
    
    
}
