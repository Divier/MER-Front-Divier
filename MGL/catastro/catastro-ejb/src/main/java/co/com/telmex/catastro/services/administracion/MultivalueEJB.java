package co.com.telmex.catastro.services.administracion;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.GrupoMultivalor;
import co.com.telmex.catastro.data.Multivalor;
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
 * Clase MultivalueEJB implementa MultivalueEJBRemote
 *
 * @author Jose Luis Caicedo Gonzalez.
 * @version	1.0
 */
@Stateless(name = "multivalueEJB", mappedName = "multivalueEJB", description = "multivalor")
@Remote({MultivalueEJBRemote.class})
public class MultivalueEJB implements MultivalueEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(MultivalueEJB.class);

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<GrupoMultivalor> queryGroupMultivalue() throws ApplicationException {
        List<GrupoMultivalor> listgroup = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gmu1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listgroup = new ArrayList<GrupoMultivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id = obj.getBigDecimal("GMU_ID");
                    String name = obj.getString("GMU_NOMBRE");
                    GrupoMultivalor group = new GrupoMultivalor();
                    group.setGmuId(id);
                    group.setGmuNombre(name);
                    listgroup.add(group);
                }
            }
            return listgroup;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los grupos multivalor. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gmuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Multivalor> queryMultivalue(BigDecimal gmuId) throws ApplicationException {
        List<Multivalor> listvalue = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul1", gmuId.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listvalue = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    Multivalor m = new Multivalor();
                    BigDecimal id = obj.getBigDecimal("MUL_ID");
                    String description = obj.getString("MUL_DESCRIPCION");
                    String value = obj.getString("MUL_VALOR");

                    m.setMulId(id);
                    m.setDescripcion(description);
                    m.setMulValor(value);
                    listvalue.add(m);
                }
            }
            return listvalue;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el multivalor. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param nrow
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Multivalor> queryMultivalueTest(Long nrow) throws ApplicationException {
        List<Multivalor> listvalue = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            long limit = nrow.longValue() + 5;
            DataList list = adb.outDataList("tst1", String.valueOf(limit), nrow.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listvalue = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    Multivalor m = new Multivalor();
                    m.setId(obj.getBigDecimal("RN"));
                    m.setMulId(obj.getBigDecimal("MUL_ID"));
                    m.setMulValor(obj.getString("MUL_VALOR"));
                    listvalue.add(m);
                }
            }
            return listvalue;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el multivalor. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gmuId
     * @param row
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Multivalor> queryMultivalueByGroup(BigDecimal gmuId, Long row) throws ApplicationException {
        List<Multivalor> listvalue = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            long limit = row.longValue() + 10;
            DataList list = adb.outDataList("mul3", gmuId.toString(), String.valueOf(limit), row.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listvalue = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    Multivalor m = new Multivalor();
                    m.setId(obj.getBigDecimal("RN"));
                    m.setMulId(obj.getBigDecimal("MUL_ID"));
                    m.setMulValor(obj.getString("MUL_VALOR"));
                    m.setDescripcion(obj.getString("MUL_DESCRIPCION"));
                    listvalue.add(m);
                }
            }
            return listvalue;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el multivalor por grupo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gmuId
     * @param mul_valor
     * @param mul_descripcion
     * @param user_crea
     * @param user_mod
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertMultivalueAtGroup(BigDecimal gmuId, String mul_valor, String mul_descripcion,
            String user_crea, String user_mod) throws ApplicationException {
        BigDecimal id = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            adb.in("mul2", gmuId.toString(), mul_valor, mul_descripcion, user_crea, user_mod);
            id = getIdMultivalue(adb);
            DireccionUtil.closeConnection(adb);
            return id;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de insertar un multivalor. EX000: " + e.getMessage(), e);
        }
    }

    private BigDecimal getIdMultivalue(AccessData adb) throws ApplicationException {
        try {
            BigDecimal id = null;
            DataObject obj = adb.outDataObjec("mul4");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
            return id;
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener el ID del multivalor. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param mul_valor
     * @param mul_descripcion
     * @param user_mod
     * @param idMulti
     * @param idGroup
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean updateMultivalueAtGroup(String mul_valor, String mul_descripcion, String user_mod,
            BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            res = adb.in("mul5", mul_valor, mul_descripcion, user_mod, idMulti.toString(), idGroup.toString());
            DireccionUtil.closeConnection(adb);
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de actualizar el multivalor. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idMulti
     * @param idGroup
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean deleteMultivalueAtGroup(BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            res = adb.in("mul6", idMulti.toString(), idGroup.toString());
            DireccionUtil.closeConnection(adb);
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de eliminar el multivalor. EX000: " + e.getMessage(), e);
        }
    }
}
