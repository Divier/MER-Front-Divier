package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogRelationBussiness;
import co.com.telmex.catastro.services.comun.CatalogEJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase CatalogRelationSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogRelationSrv extends HttpServlet {
    
    private static final Logger LOGGER = LogManager.getLogger(CatalogRelationSrv.class);

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idf = null;
        String id = request.getParameter("id");
        idf = request.getParameter("idcol");
        String op = request.getParameter("tipooperacion");
        String name = request.getParameter("name");
        try {
            CatalogRelationBussiness catalogRelationBussiness = new CatalogRelationBussiness();
            catalogRelationBussiness.buildRelation(request, response, id, idf, name);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/catalogorelacion.jsp");
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
        String resul;
        String operacion;
        String id;
        String transaccion = "ok";
        operacion = request.getParameter("accion");
        HttpSession session = request.getSession(true);
        CatalogEJB catalogEJB = new CatalogEJB();
        String valor;
        if (operacion.equals("Eliminar")) {
            id = (String) session.getAttribute("delete");
            String[] parameter = new String[1];
            try {
                parameter[0] = request.getParameter("id");
                resul = catalogEJB.deleteEntity(id, parameter);
            } catch (Exception e) {
                LOGGER.error("Error en doPost. " +e.getMessage(), e);     
            }
        } else if (operacion.equals("Guardar") || operacion.equals("Modificar")) {
            String[] parameter;
            Integer totalfil = (Integer) session.getAttribute("tot");
            parameter = new String[totalfil + 1];
            if (operacion.equals("Modificar")) {
                parameter = new String[totalfil + 1];
                for (int k = 0; k < totalfil + 1; k++) {
                    valor = request.getParameter("valores" + k);
                    if (k == totalfil) {
                        parameter[k] = request.getParameter("id");
                    } else {
                        parameter[k] = valor;

                    }
                }
            } else if (operacion.equals("Guardar")) {
                parameter = new String[totalfil];
                for (int k = 0; k < totalfil; k++) {
                    valor = request.getParameter("valores" + k);
                    parameter[k] = valor;
                }
            }
            try {
                if (operacion.equals("Guardar")) {
                    id = (String) session.getAttribute("create");
                    resul = catalogEJB.insertEntity(id, parameter);
                } else if (operacion.equals("Modificar")) {
                    id = (String) session.getAttribute("update");
                    resul = catalogEJB.updateEntity(id, parameter);
                }
            } catch (Exception e) {
                LOGGER.error("Error en doPost. " +e.getMessage(), e);     
            }

        }
        request.setAttribute("transaccion", transaccion);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (transaccion == "ok") {
            out.println("<script type=\"text/javascript\">");
            out.println("javascript:window.history.go(-2);");
            out.println("alert('Data Modificados exitosamente');");
            out.println("javascript:window.location.reload();");
            out.println("</script>");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
