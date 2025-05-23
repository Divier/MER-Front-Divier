package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogReportBussiness;
import co.com.telmex.catastro.services.comun.CatalogEJB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase CatalogReportSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogReportSrv extends HttpServlet {
    
    private static final Logger LOGGER = LogManager.getLogger(CatalogReportSrv.class);


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
        HttpSession httpSession = request.getSession();
        String id = request.getParameter("id");

        httpSession.setAttribute("dataResult", null);
        httpSession.setAttribute("existdata", null);

        try {
            CatalogReportBussiness catalogreport = new CatalogReportBussiness();

            catalogreport.filterFormReport(request, response, id);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/catalogoreport.jsp");
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
        HttpSession session = request.getSession(true);
        CatalogEJB catalogEJB = new CatalogEJB();

        String post = request.getParameter("post");
        if (post.equals("0")) {
            String[] parameter;
            String totalfil = request.getParameter("total");
            int longitud = Integer.parseInt(totalfil);

            parameter = new String[longitud];
            String valor = null;

            for (int i = 0; i < longitud; i++) {
                valor = request.getParameter("valores" + i);
                parameter[i] = valor;
            }
            HttpSession httpSession = request.getSession();
            String id = request.getParameter("id");
            try {
                CatalogReportBussiness catalogreport = new CatalogReportBussiness();

                RequestDispatcher rd;

                catalogreport.filterFormResult(request, response, parameter, id);
                rd = getServletContext().getRequestDispatcher("/view/comun/catalogoreport.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                LOGGER.error("Error en doPost. " +e.getMessage(), e); 
            }

        } else {
            String exportar = null;

            String valores = null;
            String[] parameter;
            String totalcolum = null;
            String totalfilas = null;

            exportar = request.getParameter("export");
            if (exportar.equals("Exportar Excel")) {
                totalcolum = request.getParameter("totalcol");
                totalfilas = request.getParameter("totaldata");

                int longitudcolum = Integer.parseInt(totalcolum);
                int longitudfilas = Integer.parseInt(totalfilas);
                parameter = new String[longitudcolum];
                try {

                    String encoding = request.getCharacterEncoding();
                    if ((encoding != null) && (encoding.equalsIgnoreCase("utf-8"))) {
                        response.setContentType("application/vnd.ms-excel; charset=utf-8");
                    }
                    response.setHeader("Content-Disposition", "attachment; filename=Reporte General Catastro.xls");
                    response.setContentType("application/force.download");

                    WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());

                    WritableSheet s = w.createSheet("Reporte General", 0);

                    for (int i = 0; i < longitudcolum; i++) {
                        valores = request.getParameter("colum" + i);
                        parameter[i] = valores;
                        s.addCell(new Label(i, 0, parameter[i]));
                    }
                    for (int j = 0; j < longitudfilas; j++) {
                        for (int k = 0; k < longitudcolum; k++) {
                            valores = request.getParameter("vl" + j + "c" + k);
                            String cadenaUtf = new String(valores.getBytes("ISO-8859-1"), "UTF-8");
                            s.addCell(new Label(k, 1 + j, cadenaUtf));

                        }
                    }

                    w.write();
                    w.close();

                } catch (WriteException e) {
                    LOGGER.error("Error en doPost. " +e.getMessage(), e);     
                } catch (Exception e) {
                    LOGGER.error("Error en doPost. " +e.getMessage(), e);     
                }
            }

        }

    }
}