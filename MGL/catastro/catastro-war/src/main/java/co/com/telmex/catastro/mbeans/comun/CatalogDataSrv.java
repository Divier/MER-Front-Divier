package co.com.telmex.catastro.mbeans.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogDataBussiness;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase CatalogDataSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogDataSrv extends HttpServlet {

  

    private static final Logger LOGGER = LogManager.getLogger(CatalogDataSrv.class);
    private String id;
    
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Se realiza validacion de session.
        try {
            SecurityLogin securityLogin = new SecurityLogin(request);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
        } catch (IOException ex) {
            LOGGER.error("Se generea error en CambiarEstadoDirPendientesMBean class ...", ex);
        }catch (Exception ex) {
            LOGGER.error("Se generea error en CambiarEstadoDirPendientesMBean class ...", ex);
        }
        
        try {
            String id = null;
            id = request.getParameter("id");
            String ctl = request.getParameter("ctl");
            CatalogDataBussiness catalogdata = new CatalogDataBussiness();
            catalogdata.filterForm(request, response, id, ctl);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/catalogodata.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            LOGGER.error("Error en doGet. " +e.getMessage(), e); 
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CatalogDataBussiness catalogdata = new CatalogDataBussiness();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ArrayList ar1 = new ArrayList();
            ArrayList nomc = new ArrayList();
            ArrayList ar2 = new ArrayList();
            String[] parameter;
            HttpSession session = request.getSession(true);
            request.setAttribute("id", ar1);
            request.setAttribute("nombc", nomc);
            request.setAttribute("valor", ar2);

            Integer totalfil = (Integer) session.getAttribute("totalfilter");
            parameter = new String[totalfil];
            for (int k = 0; k < totalfil; k++) {
                id = request.getParameter("valor" + k);
                if (k == 0) {
                    if (id.equals("")) {
                        parameter[k] = "null";
                    } else {
                        parameter[k] = new String(id);
                    }
                } else {
                    if (id == null) {
                        parameter[k] = "";
                    } else {
                        parameter[k] = new String(id);
                    }
                }
            }
            String ctld = request.getParameter("ctl");
            catalogdata.queryDataResultado(request, response, parameter, ctld);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/resultdatafilter.jsp");
            rd.forward(request, response);
        } catch (ApplicationException | IOException | NumberFormatException | ServletException ex) {
            LOGGER.error("Se produjo un error en doPost: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Se produjo un error en doPost: " + ex.getMessage(), ex);
        }

    }
}
