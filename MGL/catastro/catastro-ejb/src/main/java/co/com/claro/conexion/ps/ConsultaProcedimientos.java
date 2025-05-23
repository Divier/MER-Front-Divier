package co.com.claro.conexion.ps;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import co.com.claro.mgl.rest.dtos.ConsultarNodosPorRecursoRequest;
import com.jlcg.db.sql.ManageAccess;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Identificar y agrupar las conexiones a procedimientos. Da la estructura para
 * las conexiones a la base de datos con respecto a las conexiones.
 *
 * @author Hitts - Leidy Montero
 * @versión V1.0
 */
public class ConsultaProcedimientos {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaProcedimientos.class);

    /**
     * Identifica el consumo a un procedimiento. Contiene el flujo
     * de consumo al procedimiento MGL_SCHEME.PRC_CONSULTA_TECNODO.
     *
     * @author Hitts - Leidy Montero
     * @param getNodesBy Paramentros de entrada
     * @return retorna la respuesta del procedimiento
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public BigDecimal PrcConsultaTecNodo(ConsultarNodosPorRecursoRequest getNodesBy) throws ApplicationException {
        BigDecimal TempString = new BigDecimal("0");
        String sql = "{ CALL MGL_SCHEME.PRC_CONSULTA_TECNODO(?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = ManageAccess.getDatasource().getConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (getNodesBy.getType() == null) {
                cs.setNull(1, OracleTypes.VARCHAR);
            } else {
                cs.setString(1, getNodesBy.getType());
            }
            if (getNodesBy.getValue() == null) {
                cs.setNull(2, OracleTypes.NUMBER);
            } else {
                cs.setString(2, getNodesBy.getValue());
            }
            cs.setString(3, getNodesBy.getTechnology());
            cs.setString(4, getNodesBy.getStateTechnology());
            cs.registerOutParameter(5, OracleTypes.NUMBER);
            cs.registerOutParameter(6, OracleTypes.NUMBER);
            cs.registerOutParameter(7, OracleTypes.NUMBER);
            cs.registerOutParameter(8, OracleTypes.VARCHAR);
            cs.setQueryTimeout(5);
            cs.execute();
            TempString = cs.getBigDecimal(5);
        } catch (NumberFormatException | SQLException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException("Error obteniendo informacion. EX000: " + e.getMessage(), e);
        }
        return TempString;
    }
    
    /**
     * Identifica el consumo a un procedimiento. Contiene el flujo
     * de consumo al procedimiento MGL_SCHEME.PRC_LOG_CONSUL_DETALLE_BATCH
     *
     * @author Hitts - Fabian Duarte
     * @param logMaster
     * @param estado
     * @return retorna la respuesta del procedimiento
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<LogActualizaDetalle> prcConsultaLogDetalleBatch(LogActualizaMaster logMaster, String estado) throws ApplicationException {
        List<LogActualizaDetalle> detalle = new ArrayList<>();
        String sql = "{ CALL MGL_SCHEME.PKG_CRU_NAP.PRC_LOG_CONSUL_DETALLE_BATCH (?, ?, ?, ?, ?, ?) }";
        try (Connection conn = ManageAccess.getDatasource().getConnection();
                CallableStatement cs = conn.prepareCall(sql);) {
            if (logMaster.getIdNap() == null) {
                cs.setNull(1, OracleTypes.NUMBER);
            } else {
                cs.setString(1, (logMaster.getIdNap()).toString());
            }
            if (logMaster.getNombreArchivo() == null) {
                cs.setNull(2, OracleTypes.VARCHAR);
            } else {
                cs.setString(2, logMaster.getNombreArchivo());
            }
            if (estado == null) {
                cs.setNull(3, OracleTypes.VARCHAR);
            } else {
                cs.setString(3, estado);
            }
            cs.registerOutParameter(4, OracleTypes.VARCHAR);
            cs.registerOutParameter(5, OracleTypes.VARCHAR);
            cs.registerOutParameter(6, OracleTypes.CURSOR);
            cs.setQueryTimeout(5);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(6);
            String codigo = cs.getObject(4).toString();
            if (codigo.equals("0")) {     
                while (rs.next()) {
                    LogActualizaDetalle tempDetalle = new LogActualizaDetalle();
                    tempDetalle.setHandle((rs.getObject(3) == null)? "" : rs.getObject(3).toString());
                    tempDetalle.setBlockname((rs.getObject(4) == null)? "" : rs.getObject(4).toString());
                    tempDetalle.setDirreccion((rs.getObject(11) == null)? "" : rs.getObject(11).toString());
                    tempDetalle.setCordX((rs.getObject(15) == null)? "" : rs.getObject(15).toString());
                    tempDetalle.setCordY((rs.getObject(16) == null)? "" : rs.getObject(16).toString());
                    tempDetalle.setNpcd((rs.getObject(17) == null)? "" : rs.getObject(17).toString());
                    tempDetalle.setNombreConjunto((rs.getObject(18) == null)? "" : rs.getObject(18).toString());
                    tempDetalle.setNombreCall((rs.getObject(19) == null)? "" : rs.getObject(19).toString());
                    tempDetalle.setPlacaUnida((rs.getObject(20) == null)? "" :rs.getObject(20).toString());
                    tempDetalle.setNumCasas((rs.getObject(21) == null)? "" : rs.getObject(21).toString());
                    tempDetalle.setAptos((rs.getObject(22) == null)? "" : rs.getObject(22).toString());
                    tempDetalle.setLocales((rs.getObject(23) == null)? "" : rs.getObject(23).toString());
                    tempDetalle.setPisos((rs.getObject(24) == null)? "" : rs.getObject(24).toString());
                    tempDetalle.setBarrio((rs.getObject(25) == null)? "" : rs.getObject(25).toString());
                    tempDetalle.setInterior((rs.getObject(26) == null)? "" : rs.getObject(26).toString());
                    tempDetalle.setNombreCallAnt2((rs.getObject(27) == null) ? "" : rs.getObject(27).toString());
                    tempDetalle.setPlacaUnidaAnt2((rs.getObject(28) == null) ? "" : rs.getObject(28).toString());
                    tempDetalle.setNombreCallAnt3((rs.getObject(29) == null) ? "" : rs.getObject(29).toString());
                    tempDetalle.setPlacaUnidaAnt3((rs.getObject(30) == null) ? "" : rs.getObject(30).toString());
                    tempDetalle.setDistribucion((rs.getObject(31) == null) ? "" : rs.getObject(31).toString());
                    tempDetalle.setAmp((rs.getObject(32) == null) ? "" : rs.getObject(32).toString());
                    tempDetalle.setCordx((rs.getObject(33) == null) ? "" : rs.getObject(33).toString());
                    tempDetalle.setCordy((rs.getObject(34) == null) ? "" : rs.getObject(34).toString());
                    tempDetalle.setTroncal((rs.getObject(35) == null)? "" : rs.getObject(35).toString());
                    tempDetalle.setNap((rs.getObject(36) == null)? "" : rs.getObject(36).toString());
                    tempDetalle.setEstadoTransaccion((rs.getObject(37) == null)? "" : rs.getObject(37).toString());
                    tempDetalle.setDetalle((rs.getObject(39) == null)? "" : rs.getObject(39).toString());
                    detalle.add(tempDetalle);
                }
            } else {
                return null;
            }
        } catch (NumberFormatException | SQLException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException("Error obteniendo informacion. EX000: " + e.getMessage(), e);
        }
        return detalle;
    }
}