/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.migracion.login;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJBRemote;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import co.com.telmex.catastro.util.FacesUtil;

/**
 *
 * @author gsantos1
 */
public class Login extends HttpServlet {

  public static final String SERVER_DE_AGENDAMIENTO_PARA_AUTENTICACION = "http://agendamiento.cable.net.co/";

  public static final String URL_DE_INICIO_PARA_ROL_NVTADM = "/catastro-war/faces/view/MGL/VT/Migracion/index.xhtml";
  public static final String URL_DE_INICIO_PARA_ROL_NVTSOL = "/catastro-war/faces/view/MGL/VT/Migracion/index.xhtml";
  public static final String URL_DE_INICIO_PARA_ROL_NVTGES = "/catastro-war/faces/view/MGL/VT/Migracion/index.xhtml";
  public static final String URL_DE_INICIO_PARA_ROL_MEST = "/catastro-war/faces/view/MGL/VT/Migracion/index.xhtml";
  public static final String URL_DE_INICIO = "/catastro-war/faces/view/MGL/VT/Migracion/index.xhtml";
  private static final Logger LOGGER = LogManager.getLogger(Login.class);


  @EJB
  private ResourceEJBRemote resourceEJBRemote;

  private String urlServiceLogin;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      cargarUrlServiceLogin();


      CookieParser cookiepar = null;
      CookieData cookiedat = null;
      HttpSession session = request.getSession();

      String Usuario = "indefinido";
      Usuario = request.getParameter("Usuario");
      session.setAttribute("usuarioDir", Usuario);
      /* Solo para pruebas de ingreso directo*/
      session.setAttribute("usuarioDir", "hitss");

      String Verificacion = "indefinido";
      Verificacion = request.getParameter("Verificacion");
      session.setAttribute("verificacionDir", Verificacion);

      String cookie = "";
      //TODO codigo remplazado por el WEB-SERVICE de agendamiento
      LOGGER.info("ENTRANDO A OBTENER ROLES AL WEBSERVICES-------");
      LOGGER.info("usuario: " + Usuario);
      LOGGER.info("Verificacion: " + Verificacion);

      cookie = "<XUSUARIOS><XUSUARIO><IDUSUARIO>80203774</IDUSUARIO><USUARIO>hectorg</USUARIO><PERFIL>23954</PERFIL><NOMBRE>usuario pruebas hitss</NOMBRE><ROLES>AVTDIRA/AVTDIRU/MENVTN/MEST01/MEVT01/MEVT04/MEVT06/MEVT11/MEVT17/NVTADM/NVTADM/NVTGE0/NVTGE1/NVTGE2/NVTGE3/NVTGE4/NVTGE5/NVTGE6/NVTGE7/NVTGE8/NVTGE9/NVTGES/NVTGES/NVTSO0/NVTSO1/NVTSO2/NVTSO3/NVTSO4/NVTSO5/NVTSO6/NVTSO7/NVTSO8/NVTSO9/NVTSOL/NVTSOL/VTBP01/VTCD01/VTCE01/VTCE02/VTDIRA/VTDIRU/VTID01/VTIN01/VTPC01/VTVI17</ROLES><TIPOSTRABAJO></TIPOSTRABAJO><CIUDADES>BOG</CIUDADES><ALIADOS></ALIADOS><ALIADO>hitss</ALIADO><PARAMETROS></PARAMETROS></XUSUARIO></XUSUARIOS>";
      request.setAttribute("Usuario", "");
      request.setAttribute("Verificacion", "");

      cookiepar = new CookieParser(cookie);
      cookiedat = cookiepar.getData();
      session.setAttribute("idUser", cookiedat.getIdUsuario());
      LOGGER.info("usuario");
      LOGGER.info("Verifican COKK :" + cookie);
      LOGGER.info("Roles:-----: " + cookiedat.getRoles());

      session.setAttribute("cookieXml", cookie);
      if (cookiedat.getRoles().contains("NVTADM")) {
        LOGGER.info("ROL NVTADM");
        session.setAttribute("cookie", cookiedat);
        response.sendRedirect(URL_DE_INICIO_PARA_ROL_NVTADM);
      } else if (cookiedat.getRoles().contains("NVTGES")) {
        LOGGER.info("ROL NVTGES");
        session.setAttribute("cookie", cookiedat);
        response.sendRedirect(URL_DE_INICIO_PARA_ROL_NVTGES);
      } else if (cookiedat.getRoles().contains("MEST01")) {
        LOGGER.info("ROL MEST01");
        session.setAttribute("cookie", cookiedat);
        response.sendRedirect(URL_DE_INICIO_PARA_ROL_MEST);
      } else if (cookiedat.getRoles().contains("NVTSOL")) {
        LOGGER.info("ROL NVTSOL");
        session.setAttribute("cookie", cookiedat);
        response.sendRedirect(URL_DE_INICIO_PARA_ROL_NVTSOL);
        return;
      } else {
        LOGGER.info("ROL DEFAUL ...");
        response.sendRedirect(SERVER_DE_AGENDAMIENTO_PARA_AUTENTICACION);
      }
    } catch (IOException | SAXException ex) {
      FacesUtil.mostrarMensajeError("Error en processRequest" + ex.getMessage() , ex, LOGGER);
      response.sendRedirect(SERVER_DE_AGENDAMIENTO_PARA_AUTENTICACION);
      return;
    } catch (Exception ex) {
      FacesUtil.mostrarMensajeError("Error en processRequest" + ex.getMessage() , ex, LOGGER);
      response.sendRedirect(SERVER_DE_AGENDAMIENTO_PARA_AUTENTICACION);
      return;
    } finally {
      out.close();
    }
  }

  /**
   * @author Ana Maria Malambo
   * Generación de Logs
   * Agregado 24/04/2013
   * import org.apache.log4j.PropertyConfigurator;
   * realiza el cargue del log4jVisitas.properties empaquetado
   */
  public void config() {
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   * @return
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }
  // </editor-fold>

  private void cargarUrlServiceLogin() {
    try {
      Parametros param = resourceEJBRemote.queryParametros("URL_SERVICE_LOGIN");
      if (param != null && param.getValor() != null) {
        urlServiceLogin = param.getValor();
        LOGGER.info("Url servicio Login: " + urlServiceLogin);
      }
    } catch (ApplicationException e) {
      FacesUtil.mostrarMensajeError("Error consltando la url de servicio web de Login" + e.getMessage() , e, LOGGER);
    } catch (Exception e) {
      FacesUtil.mostrarMensajeError("Error consltando la url de servicio web de Login" + e.getMessage() , e, LOGGER);
    }
  }
}
