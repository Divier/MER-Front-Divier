package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.data.Usuario;
import co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogDetailBussiness;
import co.com.telmex.catastro.services.comun.CatalogEJB;
import co.com.telmex.catastro.util.ConstantWEB;
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
 * Clase CatalogDetailSrv
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogDetailSrv extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CatalogDetailSrv.class);

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

        HttpSession httpSession = request.getSession();

        String idf = null;
        String id = request.getParameter("id");
        idf = request.getParameter("idcol");
        String op = request.getParameter("tipooperacion");

        try {
            CatalogDetailBussiness catalogdata = new CatalogDetailBussiness();
            Rol rolsession = (Rol) httpSession.getAttribute(ConstantWEB.ROL_SESSION);
            
//          Se quitan control de ROL de Catastro para se manejen por Visitas Tecnicas  DIEGO BARRERA 2013-09-06 V 1.2
            catalogdata.buildForms(request, response, id, idf, op);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/view/comun/catalogodetail.jsp");
            rd.forward(request, response);
        } catch (Exception e){
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
        String id;
        String transaccion = "ok";
        operacion = request.getParameter("accion");
        HttpSession httpSession = request.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute(ConstantWEB.USER_SESSION);
        String nombre = usuario.getUsuNombre();
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
            String pkv;
            Integer totalfil = (Integer) session.getAttribute("tot");
            parameter = new String[totalfil];
            if (operacion.equals("Modificar")) {
                int t = 0;
                for (int i = 0; i < totalfil; i++) {
                    if (request.getParameter("pk" + i).equals("true")) {
                        t = t + 1;
                    }
                }


                parameter = new String[totalfil - t + 1];

                int m = 0;
                for (int k = 0; k < totalfil; k++) {
                    if (m == k) {
                        m = k;
                    } else {
                        m = m + 1;
                    }
                    String fecha = request.getParameter("fecha" + k);
                    String user = request.getParameter("fecha" + k);
                    boolean resultado = fecha.endsWith("FECHA_MODIFICACION");
                    boolean userModificacion = user.endsWith("USUARIO_MODIFICACION");

                    if (resultado) {
                        valor = "CURRENT_DATE";

                    } else if (userModificacion) {
                        valor = nombre;

                    } else {
                        valor = request.getParameter("valores" + k);
                    }

                    pkv = request.getParameter("pk" + k);

                    if (request.getParameter("pk" + k).equals("false")) {
                        if (m <= totalfil - t) {
                            parameter[m] = valor;
                        }
                    } else {
                        m = m - 1;
                    }
                    parameter[m + 1] = request.getParameter("id");

                }
            } else if (operacion.equals("Guardar")) {
                int t = 0;
                for (int i = 0; i < totalfil; i++) {
                    if (request.getParameter("pk" + i).equals("true")) {
                        t = t + 1;
                    }
                }


                parameter = new String[totalfil - t];

                int m = 0;
                for (int k = 0; k < totalfil; k++) {
                    if (m == k) {
                        m = k;
                    } else {
                        m = m + 1;
                    }


                    String userCrea = request.getParameter("fecha" + k);
                    boolean userCrecion = userCrea.endsWith("USUARIO_CREACION");

                    if (userCrecion) {

                        valor = nombre;
                    } else {
                        valor = request.getParameter("valores" + k);
                    }


                    pkv = request.getParameter("pk" + k);

                    if (request.getParameter("pk" + k).equals("false")) {
                        if (m <= totalfil - t) {
                            parameter[m] = valor;
                        }
                    } else {
                        m = m - 1;
                    }

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
            out.println("alert('Datos Modificados exitosamente');");
            out.println("javascript:window.location.reload();");
            out.println("</script>");

        }
    }
}