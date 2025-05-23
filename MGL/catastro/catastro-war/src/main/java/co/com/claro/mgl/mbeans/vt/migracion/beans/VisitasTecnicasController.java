/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalRRFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.facade.rr.As400FacadeLocal;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import javax.ejb.EJB;

/**
 * Controlar las acciones generales de visitas técnicas
 * <p>
 * Permite tener un control de las acciones generales de visitas técnicas
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 * @see Serializable
 */
public  class VisitasTecnicasController implements Serializable {
  

    private static final Logger LOGGER = LogManager.getLogger(VisitasTecnicasController.class);
  /**
   * Conexión con el EJB UsuariosFacadeLocal
   */
  @EJB
  private SolicitudFacadeLocal solicitudFacadeLocal;
  /**
   * Conexión con el EJB UsuariosFacadeLocal
   */
  @EJB
  private UsuarioServicesFacadeLocal usuariosFacade;
  /**
   * Conexión con el EJB As400FacadeLocal
   */
  @EJB
  private As400FacadeLocal as400FacadeLocal;
  /**
   * Conexión con el EJB ParametrosCallesFacadeLocal
   */
  @EJB
  protected ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
  
  @EJB
  protected CmtRegionalRRFacadeLocal rrRegionalesFacadeLocal;
  
  @EJB
  protected CmtComunidadRrFacadeLocal rrCiudadesFacadeLocal;
  /**
   * Usuario logeado en el sistema
   */
  private UsuariosServicesDTO usuario;
  
  

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   *
   * @author becerraarmr
   */
  public VisitasTecnicasController() {
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
  public SolicitudFacadeLocal getSolicitudFacadeLocal() {
    return solicitudFacadeLocal;
  }

  /**
   * Buscar los datos del usuario logeado.
   * <p>
   * Cargar el usuario logeado.
   *
   * @author becerraarmr
   * @version 2017 revisión 1.0
     * @param usuarioSesion
   *
   */
  protected void cargarUsuario(String usuarioSesion) {
    try {
  
        if (usuarioSesion != null) {
            usuario = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
        }
    } catch (ApplicationException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
      }catch (Exception e) {
          String msg = "Se produjo un error al momento de ejecutar el método '"
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
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
 public UsuariosServicesDTO getUsuario() {
    return usuario;
  }


  /**
   * Buscar el valor del atributo as400FacadeLocal.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   * @versión 2017 revisión 1.0
   *
   * @return el object que representa el atributo
   */
  public As400FacadeLocal getAs400FacadeLocal() {
    return as400FacadeLocal;
  }
}
