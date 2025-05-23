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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase MenuEJB implementa MenuEJBRemote
 *
 * @author Jose Luis Caicedo Gonzalez.
 * @version	1.0
 */
@Stateless(name = "menuEJB", mappedName = "menuEJB", description = "menu")
@Remote({MenuEJBRemote.class})
public class MenuEJB implements MenuEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(MenuEJB.class);

    /**
     *
     */
    public MenuEJB() {
    }

    /**
     *
     * @param idRol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Menu> queryMenu(BigDecimal idRol) throws ApplicationException {
        List<Menu> listmenus = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("rme1", idRol.toString());
            if (list != null) {
                listmenus = new ArrayList<Menu>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id = obj.getBigDecimal("MEN_ID");
                    String name = obj.getString("MEN_NOMBRE");
                    BigDecimal orden = obj.getBigDecimal("MEN_ORDEN");
                    String enlace = obj.getString("MEN_ENLACE");
                    BigDecimal parteID = obj.getBigDecimal("MEN_ID_PARTE");

                    Menu menu = new Menu();
                    menu.setMenId(id);
                    menu.setMenNombre(name);
                    menu.setMenOrden(orden.toBigInteger());
                    menu.setMenEnlace(enlace);

                    List<Menu> listItems = queryItems(adb, idRol.toString(), id.toString());
                    if (listItems != null) {
                        menu.setMenus(listItems);
                    }
                    listmenus.add(menu);
                }
            }
            DireccionUtil.closeConnection(adb);
            return listmenus;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el menú. EX000: " + e.getMessage(), e);
        }
    }

    private List<Menu> queryItems(AccessData adb, String idRol, String idMenu) throws ApplicationException {
        try {
            List<Menu> listItems = null;
            DataList list = adb.outDataList("rme2", idRol, idMenu);
            if (list != null) {
                listItems = new ArrayList<Menu>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id = obj.getBigDecimal("MEN_ID");
                    String name = obj.getString("MEN_NOMBRE");
                    BigDecimal orden = obj.getBigDecimal("MEN_ORDEN");
                    String enlace = obj.getString("MEN_ENLACE");
                    BigDecimal parteID = obj.getBigDecimal("MEN_ID_PARTE");

                    Menu menu = new Menu();
                    menu.setMenId(id);
                    menu.setMenNombre(name);
                    menu.setMenOrden(orden.toBigInteger());
                    menu.setMenEnlace(enlace);
                    listItems.add(menu);
                }
            }
            return listItems;
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consultar los ítems. EX000: " + ex.getMessage(), ex);
        }
    }
}
