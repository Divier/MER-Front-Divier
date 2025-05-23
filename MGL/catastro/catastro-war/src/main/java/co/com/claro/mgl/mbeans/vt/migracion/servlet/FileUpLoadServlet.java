/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.servlet;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.CierreMasivo;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo;
import co.com.claro.visitasTecnicas.facade.TmMasVtCarguemasivoFacadeLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
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
 *
 * @author becerraarmr
 */

@WebServlet(name = "FileUpLoadServlet", urlPatterns = {"/faces/view/MGL/VT/Migracion/Solicitudes/masivo"})
@MultipartConfig
public class FileUpLoadServlet extends HttpServlet {

  @EJB
  private TmMasVtCarguemasivoFacadeLocal ejbCargueMasivo;
  @EJB
  private SolicitudFacadeLocal solicitudFacadeLocal;

  private static final Logger LOGGER = LogManager.getLogger(FileUpLoadServlet.class);


  protected void processRequest(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, java.io.IOException {

    // Create path components para guardar el archivo
    try {

      final Part filePart = request.getPart("file");

      final String fileName = getFileName(filePart);
      
      HttpSession session=request.getSession();
      
      if(fileName.isEmpty()){
        session.setAttribute(CierreMasivo.RESPUESTA_CM.getValor(),"No hay Archivo");
      }else{

      InputStream filecontent = filePart.getInputStream();
      
      String data = getData(filecontent);
      
      
      session.setAttribute(CierreMasivo.RESPUESTA_CM.getValor(), data);
      session.setAttribute(CierreMasivo.NAME_FILE_CM.getValor(), fileName);
      }
      
      RequestDispatcher rd=request.getRequestDispatcher("/faces/view/MGL/VT/Migracion/Solicitudes/CerrarMasivamente.xhtml");
      rd.forward(request, response);
      } catch (IOException | ServletException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      } catch (Exception e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      }
  }

  /**
   *
   * @param inputStream
   *
   * @return
   */
  private String getStringFromInputStream(InputStream inputStream) {
    //Para buferear el archivo
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {

      br = new BufferedReader(new InputStreamReader(inputStream));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

    } catch (IOException e) {
       String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
    }catch (Exception e) {
      String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
           String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
        }catch (Exception e) {
           String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
        }
      }
    }

    return sb.toString();

  }

  /**
   *
   * @param inputStream
   *
   * @return
   */
  private String getData(InputStream inputStream) {
    //Para buferear el archivo
    BufferedReader br = null;
    String informe = "";
    String line;
    try {
      br = new BufferedReader(new InputStreamReader(inputStream));
      int i = 0;//Registros leidos
      String infoNoLeido = "1. Registros no leídos:\n";
      String infoNoRegistrado = "2. Registros no guardados en TM_MAS_VT_CARGUEMASIVO::\n";
      String infoRegistrado = "3. Registros guardados en TM_MAS_VT_CARGUEMASIVO:\n";
      String infoUpdateSolicitud = "4. Solicitudes actualizadas:\n";
      BigDecimal maxArchivo = ejbCargueMasivo.findMaxArchivo();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(";");
        if (i == 0) {//revisar el archivo a leer
          String[] header = {"ID_SOLICITUD", "RESULTADOG", "USUARIOC", "RESPUESTAC"};
          if (data.length == 4) {
            for (int j = 0; j < 4; j++) {
              if (!header[j].equalsIgnoreCase(data[j])) {
                return null;
              }
            }
          } else {
            return null;
          }
          i++;
          continue;
        }
        TmMasVtCarguemasivo aux = getTmMasVtCarguemasivo(data);

        if (aux != null) {
          aux.setArchivo("" + maxArchivo);
          if (ejbCargueMasivo.find(aux)) {//buscar el registro
            infoNoRegistrado += "Registro No:" + i + " idSolicitud " + data[0] + " "
                    + "ya está registrado\n";
          } else if (!ejbCargueMasivo.create(aux)) {//Intenta crear el registro
            infoNoRegistrado += "Registro No:" + i + " idSolicitud " + data[0] + " "
                    + "no se pudo registrar en la base de datos\n";
          } else {
            infoRegistrado += "Registro No:" + i + " idSolicitud " + data[0] + " "
                    + " se  registró en la base de datos\n";
          }
          Solicitud solicitud = solicitudFacadeLocal.findById(aux.getIdSolicitud());
          //Si la solicitud existe intente actualizarla            
          if (solicitud != null) {
            try {
              solicitud.setRptGestion(aux.getResultadog());
              solicitud.setUsuario(aux.getUsuarioc());
              solicitud.setRespuesta(aux.getRespuestac());
              solicitud.setEstado("Finalizado");
              solicitud.setFechaCancelacion(new Date());
              solicitudFacadeLocal.update(solicitud);
              infoUpdateSolicitud += "Registro No:" + i + " idSolicitud " + data[0] + " "
                      + "fue actualizada\n";
              } catch (ApplicationException e) {
                  infoUpdateSolicitud += "Registro No:" + i + " idSolicitud " + data[0] + " "
                          + "no se pudo actualizar\n";
                  String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                  LOGGER.error(msg);
              } catch (Exception e) {
                  infoUpdateSolicitud += "Registro No:" + i + " idSolicitud " + data[0] + " "
                          + "no se pudo actualizar\n";
                  String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                  LOGGER.error(msg);
              }
          } else {
            infoUpdateSolicitud += "Registro No:" + i + " idSolicitud " + data[0] + " "
                    + "no encotrada\n";
          }
        } else {
          infoNoLeido += "Registro No:" + i + " idSolicitud " + data[0] + " "
                  + "no se pudo leer\n";
        }
        i++;
      }
      return infoNoLeido + infoRegistrado + infoNoRegistrado + infoUpdateSolicitud;
    } catch (IOException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      } catch (Exception e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      } finally {
      if (br != null) {
        try {
          br.close();
            } catch (IOException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
            }
        }
  }
      return null;
 }
  
  private TmMasVtCarguemasivo getTmMasVtCarguemasivo(String[] valor) {
    //Si son cuatro valores para leer
    if (valor.length == 4) {
      BigDecimal idSolicitud = JsfUtil.valorBigDecimal(valor[0]);
      if (idSolicitud != null) {
        TmMasVtCarguemasivo aux = new TmMasVtCarguemasivo();
        aux.setIdSolicitud(idSolicitud);
        aux.setResultadog(valor[1]);
        aux.setUsuarioc(valor[2]);
        aux.setRespuestac(valor[3]);
        return aux;
      }
    }
    return null;
  }

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

  protected FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext == null) {

      FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
      LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
      Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

      facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);

      // Set using our inner class
      InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

      // set a new viewRoot, otherwise context.getViewRoot returns null
      UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
      facesContext.setViewRoot(view);
    }
    return facesContext;
  }

  private abstract static class InnerFacesContext extends FacesContext {

    protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
      FacesContext.setCurrentInstance(facesContext);
    }
  }
}