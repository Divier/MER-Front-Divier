package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase MarcasEJB implementa MarcasEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "marcasEJB", mappedName = "marcasEJB", description = "marcas")
@Remote({MarcasEJBRemote.class})
public class MarcasEJB implements MarcasEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(MarcasEJB.class);

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal queryHhppCodDir(String direccion) throws ApplicationException {
        BigDecimal hhpp_id = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("marc1", direccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                hhpp_id = obj.getBigDecimal("HHP_ID");
            }
            return hhpp_id;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar HHPP por código dirección. EX000 " + e.getMessage(), e);
        }

    }

    /**
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Hhpp queryHhpp(String codigo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            Hhpp hhpp = null;
            EstadoHhpp estadoHhpp = null;
            DataObject obj = adb.outDataObjec("aeh3", codigo);
            if (obj != null) {
                BigDecimal iddepto = obj.getBigDecimal("HHP_ID");
                String estado = obj.getString("EHH_ID");
                hhpp.setHhppId(iddepto);
                estadoHhpp.setEhhId(estado);
                hhpp.setEstadoHhpp(estadoHhpp);
            }
            DireccionUtil.closeConnection(adb);
            return hhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal consultar el HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal searchMarca(String codigo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            BigDecimal idmar = null;
            DataObject obj = adb.outDataObjec("marc2", codigo);
            if (obj != null) {
                idmar = obj.getBigDecimal("MAR_ID");
            }
            DireccionUtil.closeConnection(adb);
            return idmar;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al buscar la marca. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param hhpp
     * @param idMarca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal searchMarcaHhpp(BigDecimal hhpp, BigDecimal idMarca) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            BigDecimal idmarHhp = null;
            DataObject obj = adb.outDataObjec("marc6", hhpp.toString(), idMarca.toString());
            if (obj != null) {
                idmarHhp = obj.getBigDecimal("MHH_ID");
            }
            DireccionUtil.closeConnection(adb);
            return idmarHhp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al buscar la marca HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @param hhpp
     * @param idMarca
     * @param usuario
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean createMarcaHhp(String codigo, BigDecimal hhpp, BigDecimal idMarca, String usuario) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            BigDecimal id = null;
            BigDecimal idmarca = null;
            boolean insert = false;
            insert = adb.in("marc3", hhpp.toString(), idMarca.toString(), usuario);
            DireccionUtil.closeConnection(adb);
            return insert;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al crear la marca HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @param hhpp
     * @param idMarca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean deleteMarcaHhp(String codigo, BigDecimal hhpp, BigDecimal idMarca) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();

            BigDecimal id = null;
            BigDecimal idmarca = null;
            boolean delete = false;
            delete = adb.in("marc5", idMarca.toString(), hhpp.toString());
            DireccionUtil.closeConnection(adb);
            return delete;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al borrar la marca HHPP. EX000 " + e.getMessage(), e);
        }

    }

    /**
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Geografico searchComunidad(String codigo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            Geografico geografico = null;
            DataObject obj = adb.outDataObjec("aeh1", codigo);
            if (obj != null) {
                BigDecimal iddepto = obj.getBigDecimal("COD_DEPTO");
                String ciudad = obj.getString("CIUDAD");
                geografico.setGeoId(iddepto);
                geografico.setGeoNombre(ciudad);
            }
            DireccionUtil.closeConnection(adb);
            return geografico;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al buscar comunidad. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String searchDepto(BigDecimal codigo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String depto = null;
            DataObject obj = adb.outDataObjec("aeh2", codigo.toString());
            if (obj != null) {
                depto = obj.getString("CIUDAD");
            }
            DireccionUtil.closeConnection(adb);
            return depto;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al buscar el departamenteo. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estadoI
     * @param estadoF
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal searchEstadoHhp(String estadoI, String estadoF) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            BigDecimal estadoPermitido = null;
            DataObject obj = adb.outDataObjec("aeh4", estadoI, estadoF);
            if (obj != null) {
                estadoPermitido = obj.getBigDecimal("THH_ID");
            }
            DireccionUtil.closeConnection(adb);
            return estadoPermitido;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal buscar el estado del HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estado
     * @param idHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean updateEstadoHhp(String estado, BigDecimal idHhpp) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean update = false;
            update = adb.in("aeh5", idHhpp.toString(), estado);
            DireccionUtil.closeConnection(adb);
            return update;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg); 
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar el estado del HHPP. EX000 " + e.getMessage(), e);
        }
    }
}
