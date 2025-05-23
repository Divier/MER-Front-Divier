package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogQueryRelationBussiness;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase CatalogQueryRelationSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogQueryRelationSrv extends HttpServlet {
    
    private static final Logger LOGGER = LogManager.getLogger(CatalogQueryRelationSrv.class);

    private String operacion = null;

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
        String ctlid = null;
        String nombret = null;
        String archivo = null;
        nombret = request.getParameter("t");
        ctlid = request.getParameter("c");
        CatalogQueryRelationBussiness catalogQueryRelationBussiness = new CatalogQueryRelationBussiness();
        List CatalogDetails = null;
        try {
            catalogQueryRelationBussiness.buildResul(request, response, ctlid, nombret);
            ArrayList ar1 = new ArrayList();
            ArrayList nomc = new ArrayList();
            ArrayList ar2 = new ArrayList();
            int i;
            i = 0;

            request.setAttribute("CourseID", ar1);
            request.setAttribute("nombc", nomc);

            request.setAttribute("CourseName", ar2);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/catalogoquery.jsp");
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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList ar1 = new ArrayList();
        ArrayList nomc = new ArrayList();
        ArrayList ar2 = new ArrayList();
        int i;
        i = 0;
        request.setAttribute("CourseID", ar1);
        request.setAttribute("nombc", nomc);
        request.setAttribute("CourseName", ar2);

    }
}
