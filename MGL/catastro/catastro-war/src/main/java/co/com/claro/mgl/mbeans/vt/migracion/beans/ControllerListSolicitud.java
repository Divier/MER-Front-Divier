/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.AccionesVT;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoSolicitud;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.PaginationDataModel;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlar las acciones en listado de solicitudes
 * <p>
 * Clase para controlar las acciones con las solicitudes listadas en pantalla
 *
 * @author becerraarmr
 *
 * @version 2017 revisión 1.0
 */
public class ControllerListSolicitud extends VisitasTecnicasController {
  
  private static final Logger LOGGER = LogManager.getLogger(ControllerListSolicitud.class);
  private DataModel items = null;
  private PaginationDataModel pagination;
  private int tamPagination = 10;//Por defecto muestra 10 registros
  
  private int selectedItemIndex;
  private TipoSolicitud tipoSolicitud = TipoSolicitud.TIPO_VTCASA;
  private Solicitud solicitud;
  private final String SOL_ESTADO_PENDIENTE = "PENDIENTE";

  private String idSolicitudBuscar;

  @EJB
  private CmtTipoSolicitudMglFacadeLocal solicitudMglFacadeLocal;
  @EJB
  private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
 
  //Opciones agregadas para Rol
  private final String BUSBTGSTR = "BUSBTGSTR";
  //Opciones agregadas para Rol
  private final String BUSBTCBES = "BUSBTCBES";
  //Opciones agregadas para Rol
  private final String BUSBTVINT = "BUSBTVINT";
  //Opciones agregadas para Rol
  private final String BUSBTVTEC = "BUSBTVTEC";
  //Opciones agregadas para Rol
  private final String BUSBGVTEC = "BUSBGVTEC";
  //Opciones agregadas para RolBSGCHHPPC
  private final String BUSBTCCCM = "BUSBTCCCM";
  //Opciones agregadas para Rol
  private final String BSGCHHPPC = "BSGCHHPPC";
  //Opciones agregadas para Rol
  private final String BSGMECMBN = "BSGMECMBN";
  
  
  //Opciones agregadas para Rol
  private final String VRTBTGSTI = "VRTBTGSTI";
    //Opciones agregadas para Rol
  private final String VRTBTCBES = "VRTBTCBES";
   //Opciones agregadas para Rol
  private final String VRTBTVINT = "VRTBTVINT";
  //Opciones agregadas para Rol
  private final String VRTBTVTEC = "VRTBTVTEC";
  //Opciones agregadas para Rol
  private final String VRTBGVTEC = "VRTBGVTEC";
  //Opciones agregadas para Rol
  private final String VRTBGCCCM = "VRTBGCCCM";
  //Opciones agregadas para Rol
  private final String VRGCHHPPC = "VRGCHHPPC";
  //Opciones agregadas para Rol
  private final String VRGMECMBN = "VRGMECMBN";
  
  //Opciones agregadas para Rol
  private final String FNBTNGCCM = "FNBTNGCCM";
  
  
  
  
  private boolean gestionarSolicitud = false;
  private SecurityLogin securityLogin;
  private String usuarioVT = null;
  private int perfilVT = 0;
  private String cedulaUsuarioVT = null;
  private FacesContext facesContext = FacesContext.getCurrentInstance();
  private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
  private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
  private boolean ordenMayorMenor = true;
  private String atributoOrdenar="idSolicitud";
  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   *
   * @author becerraarmr
   */
  public ControllerListSolicitud() {
    try{
      securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();

        } catch (IOException ex) {
            String msg = "Error al iniciar el formulario de solicitud de creaci&oacute;n CM:..." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
            LOGGER.error(msg, ex);
        } catch (Exception ex) {
            String msg = "Error al iniciar el formulario de solicitud de creaci&oacute;n CM:..." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
            LOGGER.error(msg, ex);
        }
      
  }
  public String obtenerColorAlerta() throws ApplicationException {
      Solicitud solAux = (Solicitud) getItems().getRowData();
      if (solAux != null) {
          return solicitudMglFacadeLocal.obtenerColorAlerta(solAux);
      }
      return "blue";
  }

  @PostConstruct
  private void init() {
      cargarUsuario(usuarioVT);
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @return un objeto con la información de la paginación
   */
  public PaginationDataModel getPagination() {
    if (pagination == null) {
      pagination = new PaginationDataModel(tamPagination) {
        /**
         * Buscar el valor del atributo.
         * <p>
         * Busca la cantidad de items encontrados según el tipo de solicitud y
         * el estado cuando está 'PENDIENTE'.
         *
         * @author becerraarmr
         *
         * @return la cantidad de items encontrados
         */
        @Override
        public int getItemsCount() {
          return getSolicitudFacadeLocal().
                  count(tipoSolicitud.getValor(), SOL_ESTADO_PENDIENTE);
        }

        /**
         * Buscar el valor del atributo.
         * <p>
         * Busca el listado de registros según un rango establecido
         * un tipo de solicitud y un estado.
         *
         * @author becerraarmr
         *
         * @return un DataModel con los registros encontrados
         */
        @Override
        public DataModel createPageData() {
          return new ListDataModel(getSolicitudFacadeLocal().
                  findRange(new int[]{getPageFirstItem(),
            getPageLastItem()}, tipoSolicitud.getValor(),
                  SOL_ESTADO_PENDIENTE,ordenMayorMenor,atributoOrdenar));
        }
      };
    }
    return pagination;
  }

  public void prepareList() {
    cargarUsuario(cedulaUsuarioVT);
    recargarPagination();
    recreateModel();
    this.solicitud = null;
  }

  public String prepareList(AccionesVT accion) {
          cargarUsuario(usuarioVT);
        recreateModel();
        switch (accion) {
            case GESTIONAR_VT_EDIFICIO_CONJUNTO: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_VT_EDIFICIO_CONJUNTO;
                return "Gestión de solicitudes Edificio Conjunto";
            }
            case GESTIONAR_VT_REPLANTEAMIENTO: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_REPLANTEAMIENTO;
                return "Gestión de replantamientos";
            }
            case GESTIONAR_VT_EN_CASAS: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_VT_CASAS;
                return "Gestión de visitas técnicas en casas";
            }
            case GESTIONAR_CREACION_CUENTA_MATRIZ: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_CREACION_CUENTA_MATRIZ;
                return "Gestión de creación de cuenta matriz";
            }
            case GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_CREACION_HHPP_CUENTA_MATRIZ;
                return "Gestión de creación de HHPP en cuenta matriz";
            }
            case GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_MODIFICAR_ELIMINAR_CUENTA_MATRIZ;
                return "Gestión Modificar eliminar cuenta matriz";
            }
            case GESTIONAR_VIABILIDAD_INTERNET: {
                tipoSolicitud = TipoSolicitud.TIPO_SOL_VIABILIDAD_INTERNET;
                return "Gestión de Viabilidad en internet";
            }
            case GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO: {
                tipoSolicitud = TipoSolicitud.TIPO_CAMBIO_ESTRATO;
                return "Gestión de Cambio de Estrato";
            }
        }
        return "Listado";
    }

    public String volverList() throws ApplicationException {
        try {
            solicitud.setDisponibilidadGestion("0");
            solicitud.setCodigoVerificacion(getUsuario().getUsuario() + "");
            getSolicitudFacadeLocal().update(solicitud);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);

        }
        recreateModel();
        return "Lista.xhtml?faces-redirect=true";
    }

    public String prepareEdit(int disponibilidad) throws ApplicationException {
         
        Solicitud solSeleccionada;
        String usuarioSesion;
        SolicitudFacadeLocal solicitudFacadeLocal;
      
        try {
            
        solSeleccionada = (Solicitud) session.getAttribute("solicitud");
        usuarioSesion = (String) session.getAttribute("usuario");
        solicitudFacadeLocal = (SolicitudFacadeLocal) session.getAttribute("facade");

            if (solSeleccionada == null) {
                solSeleccionada = (Solicitud) getItems().getRowData();
                selectedItemIndex = pagination.getPageFirstItem()
                        + getItems().getRowIndex();
                solSeleccionada.setCodigoVerificacion(getUsuario().getUsuario() + "");
                solSeleccionada.setDisponibilidadGestion(disponibilidad + "");
                this.solicitud = solSeleccionada;
                getSolicitudFacadeLocal().update(solSeleccionada);
                session.setAttribute("botonVolLisMigrada", true);
                session.setAttribute("botonVolLisEstado", false);
            } else {
                solSeleccionada.setCodigoVerificacion(usuarioSesion + "");
                solSeleccionada.setDisponibilidadGestion(disponibilidad + "");
                this.solicitud = solSeleccionada;
                solicitudFacadeLocal.update(solSeleccionada);
                session.setAttribute("botonVolLisMigrada", false);
                session.setAttribute("botonVolLisEstado", true);
            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método ' "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
        recreateModel();
        return "Gestionar.xhtml?faces-redirect=true";
    }

  public String update() {
    try {
      getSolicitudFacadeLocal().update(getSolicitud());
      JsfUtil.addSuccessMessage("Solicitud Actualizada");
      return "";
      } catch (ApplicationException e) {
          String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
          return null;
      } catch (Exception e) {
          String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
          return null;
      }
  }

  public String destroy() {
    setSolicitud((Solicitud) getItems().getRowData());
    selectedItemIndex = pagination.getPageFirstItem()
            + getItems().getRowIndex();
    performDestroy();
    recreatePagination();
    recreateModel();
    return "";
  }

  public String destroyAndView() {
    performDestroy();
    recreateModel();
    updateCurrentItem();
    if (selectedItemIndex >= 0) {
      return "";
    } else {
      // all items were removed - go back to list
      recreateModel();
      return "";
    }
  }

  private void performDestroy() {
    try {
      getSolicitudFacadeLocal().delete(getSolicitud());
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomerDeleted"));
    } catch (ApplicationException e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
      String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
    }catch (Exception e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
         String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
      LOGGER.error(msg);
    }
  }

  private void updateCurrentItem() {
    int count = getSolicitudFacadeLocal().count(tipoSolicitud.getValor(), SOL_ESTADO_PENDIENTE);
    if (selectedItemIndex >= count) {
      // selected index cannot be bigger than number of items:
      selectedItemIndex = count - 1;
      // go to previous page if last page disappeared:
      if (pagination.getPageFirstItem() >= count) {
        pagination.previousPage();
      }
    }
    if (selectedItemIndex >= 0) {
      setSolicitud(getSolicitudFacadeLocal().
              findRange(new int[]{selectedItemIndex, selectedItemIndex + 1},
              tipoSolicitud.getValor(), SOL_ESTADO_PENDIENTE,ordenMayorMenor,atributoOrdenar).get(0));
    }
  }

  public DataModel<Solicitud> getItems() {
    if (items == null) {
      items = getPagination().createPageData();
    }
    return items;
  }

  public Solicitud getSolicitud(BigDecimal id) {
    return getSolicitudFacadeLocal().findById(id);
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
  public int getTamPagination() {
    return tamPagination;
  }

  /**
   * Actualizar el valor del atributo.
   * <p>
   * Actualiza el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @param tamPagination valor actualizar
   */
  public void setTamPagination(int tamPagination) {
    this.tamPagination = tamPagination;
  }

  /**
   * @return the solicitud
   */
  public Solicitud getSolicitud() {
    if (solicitud == null) {
      solicitud = new Solicitud();
    }
    return solicitud;
  }

  /**
   * @param solicitud the solicitud to set
   */
  public void setSolicitud(Solicitud solicitud) {
    this.solicitud = solicitud;
  }

  /**
   * Buscar solicitud según la id de la solicitud
   * <p>
   * Busca un solicitud según el id, el estado 'PENDIENTE' Y el tipo que esté
   * gestionando.
   *
   * @author becerraarmr
   *
   * @return un String para renderizar
   */
  public String buscarSolicitud() {
    BigDecimal valor = JsfUtil.valorBigDecimal(idSolicitudBuscar);
    if (valor == null) {
      return "";
    }
    final Solicitud sol = getSolicitudFacadeLocal().
            findSolicitud(valor,
                    SOL_ESTADO_PENDIENTE,
                    tipoSolicitud.getValor());
    if (sol != null) {
      recreateModel();
      recreatePagination();
      pagination = new PaginationDataModel(1) {
        @Override
        public int getItemsCount() {
          return 1;
        }

        @Override
        public DataModel createPageData() {
          List<Solicitud> list = new ArrayList();
          list.add(sol);
          DataModel dm = new ListDataModel(list);
          return dm;
        }
      };
    } else {
      JsfUtil.addSuccessMessage("Solicitud: " + idSolicitudBuscar
              + " no fue encontrada");
    }
    idSolicitudBuscar = null;
    return "";
  }

  /**
   * Buscar todas las solicitudes según el tipo
   * <p>
   * Busca las solicitudes según el tipo de solicitud.
   *
   * @return espacio en blanco para que se quede en la misma
   *         página de la sesión.
   */
  public String buscarTodas() {
    solicitud = null;
    recreateModel();
    recreatePagination();
    return "";
  }

  public String gestionarSolcitud() {
    gestionarSolicitud = true;
    return "";
  }

  public boolean isGestionarSolicitud() {
    return gestionarSolicitud;
  }

  /**
   * Verificar la disponibilidad.
   * <p>
   * Se verifica la disponilidad de una solicitud para su gestión
   *
   * @author becerraarmr
   * @return true o false
   */
  public String disponibilidadSolicitud() {
    Solicitud item = (Solicitud) getItems().getRowData();
    if (item != null) {
      Integer disp = JsfUtil.valorInteger(item.getDisponibilidadGestion());
      if (disp != null && disp == 1) {//esta ocupado
          if (getUsuario() != null && item.getCodigoVerificacion() != null
                  && item.getCodigoVerificacion().trim().
                  equalsIgnoreCase(getUsuario().getUsuario() + "")) {
              return "Desbloquear";
          } else {
              return item.getCodigoVerificacion();
          }
      }
    }
    return "Gestionar";
  }

    public void setIdSolicitudBuscar(String idSolicitudBuscar) {
        this.idSolicitudBuscar = idSolicitudBuscar;
    }

    public String getIdSolicitudBuscar() {
        return idSolicitudBuscar;
    }

    public boolean isOrdenMayorMenor() {
        return ordenMayorMenor;
    }

    public void setOrdenMayorMenor(boolean ordenMayorMenor) {
        this.ordenMayorMenor = ordenMayorMenor;
    }

  

  public String next() {
    getPagination().nextPage();
    recreateModel();
    return "";
  }

  public String previous() {
    getPagination().previousPage();
    recreateModel();
    return "";
  }

  public String start() {
    getPagination().start();
    recreateModel();
    return "";
  }

  public String end() {
    getPagination().end();
    recreateModel();
    return "";
  }

  public String recargarPagination() {
    recreatePagination();
    recreateModel();
    return "";
  }

  public void recreatePagination() {
    pagination = null;
  }

  public void recreateModel() {
    items = null;
  }

  public long calcularTiempo(Date fecha) {
    if (fecha != null) {
      Date aux = new Date();
      Long diferenciaMils = aux.getTime() - fecha.getTime();
      return diferenciaMils / (1000 * 60);
    }
    return 0;
  }
  
  public String prepareGestorSolicitudes(){
    return "/view/MGL/VT/Migracion/Solicitudes/Lista.xhtml?faces-redirect=true";
  }
  
  
    public String findNombreCiiudad(String codigo)
            throws ApplicationException {

        CmtComunidadRr ciudad = rrCiudadesFacadeLocal.findByCodigoRR(codigo);
        String nombreCiudad = "";

        if (ciudad != null) {
            nombreCiudad = ciudad.getNombreComunidad();
        }
        return nombreCiudad;
    }
    
    public String findNombreRegional(String codigo)
            throws ApplicationException {

        CmtRegionalRr regional = rrRegionalesFacadeLocal.findRegionalByCod(codigo);
        String nombreRegional = "";

        if (regional != null) {
            nombreRegional = regional.getNombreRegional();
        }
        return nombreRegional;
    }


    public void volverListEstSol() throws ApplicationException {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/solicitudes/estadoSolicitud/estadoSolicitudHhpp.jsf");
        } catch (ApplicationException e) {
            throw new ApplicationException("Error al momento de volver al listado: " + e.getMessage(), e);
        }

    }
    
     public void cambiarOrdenMayorMenorListado() {
        if (ordenMayorMenor) {
            ordenMayorMenor = false;
        } else {
            ordenMayorMenor = true;
        }
      atributoOrdenar ="idSolicitud";
      recreatePagination();
      recreateModel();
        
    }
     
      public void cambiarOrdenMayorMenorListadoFecha() {
        if (ordenMayorMenor) {
            ordenMayorMenor = false;
        } else {
            ordenMayorMenor = true;
        }
      atributoOrdenar ="fechaIngreso";
      recreatePagination();
      recreateModel();
        
    }
      @FacesConverter(forClass = Solicitud.class)
      public static class ControllerListSolicitudConverter implements Converter {
          
          @Override
          public Object getAsObject(FacesContext facesContext,
                  UIComponent component, String value) {
              if (value == null || value.length() == 0) {
                  return null;
              }
              ControllerListSolicitud controller
                      = (ControllerListSolicitud) facesContext.getApplication().
                              getELResolver().
                              getValue(facesContext.getELContext(), null,
                                      "controllerListSolicitud");
              return controller.getSolicitud(getKey(value));
          }
          
          BigDecimal getKey(String value) {
              BigDecimal key;
              key = new BigDecimal(value);
              return key;
          }
          
          String getStringKey(BigDecimal value) {
              StringBuilder sb = new StringBuilder();
              sb.append(value);
              return sb.toString();
          }
          
          @Override
          public String getAsString(FacesContext facesContext,
                  UIComponent component, Object object) {
              if (object == null) {
                  return null;
              }
              if (object instanceof Solicitud) {
                  Solicitud o = (Solicitud) object;
                  return getStringKey(o.getIdSolicitud());
              } else {
                  throw new IllegalArgumentException("object " + object + " is of type "
                          + object.getClass().getName() + "; expected type: "
                          + Solicitud.class.getName());
              }
          }
      }

      
    // Validar Opciones por Rol
    public boolean validarOpcionBuscar(AccionesVT accion) {
       switch (accion) {
            case GESTIONAR_VT_EDIFICIO_CONJUNTO: {
          
                return validarEdicionRol(BUSBTVTEC);
            }
            case GESTIONAR_VT_REPLANTEAMIENTO: {
               return validarEdicionRol(BUSBTGSTR);
            }
            case GESTIONAR_VT_EN_CASAS: {
                // "Gestión de visitas técnicas en casas";
                return validarEdicionRol(BUSBGVTEC);
            }
            case GESTIONAR_CREACION_CUENTA_MATRIZ: {
                // "Gestión de creación de cuenta matriz"; 
                 return validarEdicionRol(BUSBTCCCM);
            }
            case GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ: {
                 // "Gestión de creación de HHPP en cuenta matriz";
                  return  validarEdicionRol(BSGCHHPPC);
            }
            case GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ: {
                 
                 // "Gestión Modificar eliminar cuenta matriz";
                  return  validarEdicionRol(BSGMECMBN);
            }
            case GESTIONAR_VIABILIDAD_INTERNET: {
                
                //"Gestión de Viabilidad en internet";
                  return  validarEdicionRol(BUSBTVINT);
            }
            case GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO: {
                 
                 // "Gestión de Cambio de Estrato";
                  return validarEdicionRol(BUSBTCBES);
            }
            default:  
                return true;
        }
        
    } 
    public boolean validarOpcionVerTodas(AccionesVT accion) {
        
       switch (accion) {
            case GESTIONAR_VT_EDIFICIO_CONJUNTO: {
          
                return validarEdicionRol(VRTBTVTEC);
            }
            case GESTIONAR_VT_REPLANTEAMIENTO: {
               return validarEdicionRol(VRTBTGSTI);
            }
            case GESTIONAR_VT_EN_CASAS: {
                // "Gestión de visitas técnicas en casas";
                return validarEdicionRol(VRTBGVTEC);
            }
            case GESTIONAR_CREACION_CUENTA_MATRIZ: {
                // "Gestión de creación de cuenta matriz"; 
                 return validarEdicionRol(VRTBGCCCM);
            }
            case GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ: {
                 // "Gestión de creación de HHPP en cuenta matriz";
                  return  validarEdicionRol(VRGCHHPPC);
            }
            case GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ: {
                 
                 // "Gestión Modificar eliminar cuenta matriz";
                return  validarEdicionRol(VRGMECMBN);
            }
            case GESTIONAR_VIABILIDAD_INTERNET: {
                
                //"Gestión de Viabilidad en internet";
                  return  validarEdicionRol(VRTBTVINT);
            }
            case GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO: {
                 
                 // "Gestión de Cambio de Estrato";
                  return validarEdicionRol(VRTBTCBES);
            }
            default:  
                return true;
        }
    } 
    
    public boolean validarOpcionGestionarDesbloquear() {
        return validarEdicionRol(FNBTNGCCM);
    }
    
    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
}
