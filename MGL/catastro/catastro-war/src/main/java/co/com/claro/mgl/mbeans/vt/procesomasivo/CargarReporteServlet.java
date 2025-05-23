/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mgl.facade.procesomasivo.CargueMasivoHhppFacadeLocal;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Cargar Reporte
 * <p>
 * Con esta clase se puede controlar la acciones de la carga del reporte
 * que se realiza desde el navegador web.
 *
 * @author becerraarmr
 */
@WebServlet(name = "cargarReporteServlet",
        urlPatterns = {"/view/MGL/VT/procesoMasivo/cargar_reporte"})
@MultipartConfig
public class CargarReporteServlet extends HttpServlet {

  private static final Logger LOGGER = LogManager.getLogger(CargarReporteServlet.class);
  private static final long serialVersionUID = 1L;

  @EJB
  private CargueMasivoHhppFacadeLocal ejbCargueMasivoHhpp;
  /**
   * Nombre del archivo que se carga
   */
  private String fileName;

  /**
   * Procesar el request
   * <p>
   * Se procesa el request para generar el response
   *
   * @author becerraarmr
   * @param request  data de entrada de la web
   * @param response data de salida hacia la web
   *
   * @throws ServletException    si hay error por el servlet
   * @throws java.io.IOException si hay error con la manipulación de archivos
   */
  protected void processRequest(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, java.io.IOException {

    // Create path components para guardar el archivo
    try {

      final Part filePart = request.getPart("file");
      //Buscar el nombre del archivo
      fileName = getFileName(filePart);

      request.setCharacterEncoding("utf-8");

      HttpSession session = request.getSession();

      session.setAttribute("THREADCARGAMASIVA_MGL", null);
      if (!fileName.isEmpty()) {
        final String usuario = request.getParameter("usuario");
        final int perfil = Integer.parseInt(request.getParameter("perfil"));
        ejbCargueMasivoHhpp.execute(usuario,perfil, filePart,session);
      }

      response.setContentType("text/html");
      RequestDispatcher rd
              = request.getRequestDispatcher("/view/MGL/VT/procesoMasivo/cargar_reporte.jsf");
      rd.forward(request, response);
      } catch (IOException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      } catch (ServletException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      }
  }

  /**
   * Buscar el fileName del archivo
   * <p>
   * Se busca el fileName del archivo que contiene el objeto part
   *
   * @author becerraarmr
   *
   * @return el String que representa el fileName del archivo
   */
  private String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
   
    for (String content : part.getHeader("content-disposition").split(";")) {
      if (content.trim().startsWith("filename")) {
        return content.substring(
                content.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   *
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
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
   *
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

  /**
   * Verificar si es BigDecimal
   * <p>
   * Verifica que el valor que llega es un número.
   *
   * @author becerraarmr
   * @param valor valor String a evaluar
   *
   * @return resultado Ineger o null de la validación.
   */
  public BigDecimal valorBigDecimal(String valor) {
    try {
      return new BigDecimal(valor);
    } catch (Exception e) {
        //No es un número y retorna null como BigDecimal
        String msg = "Se produjo un error al momento de ejecutar el método '"
                + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
        LOGGER.error(msg);
      return null;
    }
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @return el valor que representa el atributo
   */
  public String getFileName() {
    return fileName;
  }
}
