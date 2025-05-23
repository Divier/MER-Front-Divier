package co.com.telmex.catastro.services.administracion;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Menu;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase RolmenuEJB implementa RolmenuEJBRemote
 *
 * @author Nataly Orozco.
 * @version	1.0
 */
@Stateless(name = "rolmenuEJB", mappedName = "rolmenuEJB", description = "rolmenu")
@Remote({RolmenuEJBRemote.class})
public class RolmenuEJB implements RolmenuEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(RolmenuEJB.class);

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Menu> queryMenu() throws ApplicationException {
        List<Menu> listmenu = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("men1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listmenu = new ArrayList<Menu>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id = obj.getBigDecimal("MEN_ID");
                    String name = obj.getString("MEN_NOMBRE");
                    BigDecimal orden = obj.getBigDecimal("MEN_ORDEN");
                    String modulo = obj.getString("MEN_MODULO");
                    String enlace = obj.getString("MEN_ENLACE");
                    Timestamp fecha_crea = obj.getTimestamp("MEN_FECHA_CREACION");
                    String usu_crea = obj.getString("MEN_USUARIO_CREACION");
                    BigDecimal parteId = obj.getBigDecimal("MEN_ID_PARTE");
                    Menu menu = new Menu();
                    menu.setMenId(id);
                    menu.setMenNombre(name);
                    menu.setMenOrden(orden.toBigInteger());
                    menu.setMenModulo(modulo);
                    menu.setMenEnlace(enlace);
                    menu.setMenFechaCreacion((Date) fecha_crea);
                    menu.setMenUsuarioCreacion(usu_crea);
                    Menu men = queryGrupoMenu(parteId);
                    menu.setMenuParte(men);
                    listmenu.add(menu);
                }
            }
            return listmenu;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el menú. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param parteId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Menu queryGrupoMenu(BigDecimal parteId) throws ApplicationException {
        Menu menu = new Menu();
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("men2");
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal id = obj.getBigDecimal("MEN_ID");
                String name = obj.getString("MEN_NOMBRE");
                BigDecimal orden = obj.getBigDecimal("MEN_ORDEN");
                String modulo = obj.getString("MEN_MODULO");
                String enlace = obj.getString("MEN_ENLACE");
                Timestamp fecha_crea = obj.getTimestamp("MEN_FECHA_CREACION");
                String usu_crea = obj.getString("MEN_USUARIO_CREACION");
                menu.setMenId(id);
                menu.setMenNombre(name);
                menu.setMenOrden(orden.toBigInteger());
                menu.setMenModulo(modulo);
                menu.setMenEnlace(enlace);
                menu.setMenFechaCreacion((Date) fecha_crea);
                menu.setMenUsuarioCreacion(usu_crea);
            }
            return menu;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el grupo menú. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Menu> queryGruposMenu() throws ApplicationException {
        List<Menu> listmenu = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("men3");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listmenu = new ArrayList<Menu>();
                for (DataObject obj : list.getList()) {
                    Menu m = new Menu();
                    m.setMenId(obj.getBigDecimal("MEN_ID"));
                    m.setMenNombre(obj.getString("MEN_NOMBRE"));
                    m.setMenOrden((obj.getBigDecimal("MEN_ORDEN")).toBigInteger());
                    m.setMenModulo(obj.getString("MEN_MODULO"));
                    m.setMenEnlace(obj.getString("MEN_ENLACE"));
                    m.setMenFechaCreacion((Date) (obj.getTimestamp("MEN_FECHA_CREACION")));
                    m.setMenUsuarioCreacion(obj.getString("MEN_USUARIO_CREACION"));
                    m.setMenFechaModificacion((Date) (obj.getTimestamp("MEN_FECHA_MODIFICACION")));
                    m.setMenUsuarioModificacion(obj.getString("MEN_USUARIO_MODIFICACION"));
                    listmenu.add(m);
                }
            }
            return listmenu;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los grupos de menú. EX000: " + e.getMessage(), e);
        }
    }
}
