package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.data.Menu;
import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.delegate.AdministracionDelegate;
import co.com.telmex.catastro.util.ConstantWEB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase MenuSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class MenuSrv extends HttpServlet {
    
    private static final Logger LOGGER = LogManager.getLogger(MenuSrv.class);

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String menu = null;
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null && !httpSession.isNew()) {
            Rol rolsession = (Rol) httpSession.getAttribute(ConstantWEB.ROL_SESSION);
            String menusession = (String) httpSession.getAttribute(ConstantWEB.MENU_SESSION);
            if (rolsession != null) {
                if (menusession != null) {
                    menu = menusession;
                } else {
                    menu = createMenu(rolsession.getRolId(), request);
                    httpSession.setAttribute(ConstantWEB.MENU_SESSION, menu);
                }
                request.setAttribute(ConstantWEB.MENU_SESSION, menu);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/template/head.jsp");
                rd.forward(request, response);
            }
        }
    }

    /**
     * Crea un Men&uacute;.
     * @param idRol ID del Rol.
     * @param request HTTP Servlet Request.
     * @return
     */
    public String createMenu(BigDecimal idRol, HttpServletRequest request) {
        StringBuffer menuStr = new StringBuffer();
        //--- head
        menuStr.append("with(milonic=new menuname(\"Main Menu\")){ \n");
        menuStr.append("alwaysvisible=1; \n");
        menuStr.append("left=10; \n");
        menuStr.append("orientation=\"horizontal\"; \n");
        menuStr.append("style=menuStyle; \n");
        menuStr.append("top=5; \n");

        //dynamic
        List<Menu> list = queryMenuRol(idRol);

        for (Menu item : list) {
            menuStr.append("aI(\"showmenu=" + item.getMenId() + ";text=" + item.getMenNombre() + ";\"); \n");
        }
        menuStr.append("aI(\"text=Cerrar Sesión;url=view/seguridad/logout.jsp\"); \n");
        menuStr.append("} \n");

        for (Menu item : list) {
            String id_app = item.getMenId().toString();
            menuStr.append("with(milonic=new menuname(\"" + id_app + "\")){ \n");
            menuStr.append("style=menuStyle; \n");
            List<Menu> listsubitems = item.getMenus();
            if (listsubitems != null) {
                for (Menu subitem : listsubitems) {
                    menuStr.append("top=30; \n");
                    menuStr.append("aI(\"text=" + subitem.getMenNombre() + ";url=javascript:openIFrame('" + request.getContextPath() + "/view/comun/cleansession.jsf?pag=" + subitem.getMenEnlace() + "');\"); \n");
                }
            }
            menuStr.append("}	 \n");
        }
        return menuStr.toString();
    }

    /**
     * 
     * @param idRol
     * @return 
     */
    private List<Menu> queryMenuRol(BigDecimal idRol) {
        List<Menu> menu = null;
        try {
            menu = AdministracionDelegate.queryMenu(idRol);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de consultar el menú del rol. EX000: " + ex.getMessage(), ex);
        }
        return menu;
    }
}
