package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.GrupoMultivalor;
import co.com.telmex.catastro.data.Menu;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.services.administracion.MenuEJBRemote;
import co.com.telmex.catastro.services.administracion.MultivalueEJBRemote;
import co.com.telmex.catastro.services.administracion.RolmenuEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase AdministracionDelegate
 *
 * @author Jose Luis Caicedo G.
 * @version	1.0
 */
public class AdministracionDelegate {

    private static final Logger LOGGER = LogManager.getLogger(AdministracionDelegate.class);

    private static String MULTIVALUESEJB = "multivalueEJB#co.com.telmex.catastro.services.administracion.MultivalueEJBRemote";
    private static String ROLMENUSEJB = "rolmenuEJB#co.com.telmex.catastro.services.administracion.RolmenuEJBRemote";
    private static String MENUSEJB = "menuEJB#co.com.telmex.catastro.services.administracion.MenuEJBRemote";

    /**
     *
     * @return
     * @throws javax.naming.NamingException @throws Exception
     */
    public static MenuEJBRemote getMenuEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        MenuEJBRemote obj = (MenuEJBRemote) ctx.lookup(MENUSEJB);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static MultivalueEJBRemote getMultivalueEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        MultivalueEJBRemote obj = (MultivalueEJBRemote) ctx.lookup(MULTIVALUESEJB);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static RolmenuEJBRemote getRolmenuEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        RolmenuEJBRemote obj = (RolmenuEJBRemote) ctx.lookup(ROLMENUSEJB);
        return obj;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<GrupoMultivalor> queryGroupMultivalue() throws ApplicationException {
        List<GrupoMultivalor> list = null;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                list = obj.queryGroupMultivalue();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar grupo multivalor. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar grupo multivalor. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param gmuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Multivalor> queryMultivalue(BigDecimal gmuId) throws ApplicationException {
        List<Multivalor> list = null;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                list = obj.queryMultivalue(gmuId);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar multivalor. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar multivalor. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param row
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Multivalor> queryMultivalueTest(Long row) throws ApplicationException {
        List<Multivalor> list = null;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                list = obj.queryMultivalueTest(row);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar test multivalor. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar test multivalor. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param gmuId
     * @param row
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Multivalor> queryMultivalueByGroup(BigDecimal gmuId, Long row) throws ApplicationException {
        List<Multivalor> list = null;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                list = obj.queryMultivalueByGroup(gmuId, row);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar multivalor por grupo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar multivalor por grupo. EX000 " + ex.getMessage(), ex);
        }
        return list;
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
     */
    public static BigDecimal insertMultivalueAtGroup(BigDecimal gmuId, String mul_valor, String mul_descripcion,
            String user_crea, String user_mod) throws ApplicationException {
        BigDecimal id = null;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                id = obj.insertMultivalueAtGroup(gmuId, mul_valor, mul_descripcion, user_crea, user_mod);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al agregar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al agregar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }
        return id;
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
     */
    public static boolean updateMultivalueAtGroup(String mul_valor, String mul_descripcion,
            String user_mod, BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException {
        boolean res = false;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                res = obj.updateMultivalueAtGroup(mul_valor, mul_descripcion, user_mod, idMulti, idGroup);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al actualizar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al actualizar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }
        return res;
    }

    /**
     *
     * @param idMulti
     * @param idGroup
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static boolean deleteMultivalueAtGroup(BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException {
        boolean res = false;
        try {
            MultivalueEJBRemote obj = getMultivalueEjb();
            if (obj != null) {
                res = obj.deleteMultivalueAtGroup(idMulti, idGroup);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al borrar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al borrar multivalor al grupo. EX000 " + ex.getMessage(), ex);
        }
        return res;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Menu> queryMenu() throws ApplicationException {
        List<Menu> list = null;
        try {
            RolmenuEJBRemote obj = getRolmenuEjb();
            if (obj != null) {
                list = obj.queryMenu();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar menú. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar menú. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     *
     * @param parteId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static Menu queryGrupoMenu(BigDecimal parteId) throws ApplicationException {
        Menu menu = null;
        try {
            RolmenuEJBRemote obj = getRolmenuEjb();
            if (obj != null) {
                menu = obj.queryGrupoMenu(parteId);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar grupo de menú. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar grupo de menú. EX000 " + ex.getMessage(), ex);
        }
        return menu;
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    public static List<Menu> queryGruposMenu() throws ApplicationException {
        List<Menu> listGrupos = null;
        try {
            RolmenuEJBRemote obj = getRolmenuEjb();
            if (obj != null) {
                listGrupos = obj.queryGruposMenu();
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar grupos de menú. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar grupos de menú. EX000 " + ex.getMessage(), ex);
        }
        return listGrupos;
    }

    /**
     *
     * @param rolId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Menu> queryMenu(BigDecimal rolId) throws ApplicationException {
        List<Menu> listMenus = null;
        try {
            MenuEJBRemote obj = getMenuEjb();
            if (obj != null) {
                listMenus = obj.queryMenu(rolId);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar menú. EX000 " + ex.getMessage(), ex);
        }catch (Exception ex) {
            LOGGER.error("Error al consultar menú. EX000 " + ex.getMessage(), ex);
        }
        return listMenus;
    }
}
