package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase ValidacionDirEJB implementa ValidacionDirEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "validacionDirEJB", mappedName = "validacionDirEJB", description = "validacionDir")
@Remote({ValidacionDirEJBRemote.class})
public class ValidacionDirEJB implements ValidacionDirEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ValidacionDirEJB.class);

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudConsulta> queryCuentaMatriz(String direccion) throws ApplicationException {
        List<SolicitudConsulta> direcciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("cons1", direccion);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direcciones = new ArrayList<SolicitudConsulta>();
                for (DataObject obj : list.getList()) {
                    BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                    String dir = obj.getString("DIR_FORMATO_IGAC");
                    BigDecimal estrato = obj.getBigDecimal("DIR_ESTRATO");
                    SolicitudConsulta dirc = new SolicitudConsulta();
                    dirc.setIdentificador(dir_id);
                    dirc.setDireccionm(dir);
                    dirc.setNivsocio(estrato);
                    direcciones.add(dirc);
                }
            }
            return direcciones;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la CCMM. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String queryTipoCiudadByID(String idCiudad) throws ApplicationException {
        String tipoCiudad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo6", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoCiudad = obj.getString("GPO_COD_TIPO_DIRECCION");
            }
            return tipoCiudad;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo de ciudad. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException {
        boolean multiorigen = false;
        String ciudad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo7", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ciudad = obj.getString("GPO_MULTIORIGEN");
                if (ciudad != null && "1".equals(ciudad)) {
                    multiorigen = Boolean.TRUE;
                }
            }
            return multiorigen;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la ciudad multiorigen. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public BigDecimal[] queryRolesAdministradores() throws ApplicationException {
        BigDecimal[] roles = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("rol1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                int tamanio = list.size();
                int i = 0;
                roles = new BigDecimal[tamanio];
                for (DataObject obj : list.getList()) {
                    BigDecimal dir_id = obj.getBigDecimal("ROL_ID");
                    roles[i++] = dir_id;
                }
            }
            return roles;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los roles administradores. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public int queryNivelConfiabilidadFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        String nivelConf = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("par1", acronimo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nivelConf = obj.getString("PAR_VALOR");
                nivelConfiabilidad = Integer.valueOf(nivelConf);
            }
            return nivelConfiabilidad;
        } catch (ExceptionDB | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nivel de confiabilidad. EX000: " + e.getMessage(), e);
        }
    }
}
