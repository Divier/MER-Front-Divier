/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.TecArcCamEstratoFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.AccionesVT;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoSolicitud;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Buscar en la base de datos la solicitud.
 * <p>
 * Por medio de la As400 y la base de datos de la aplicación se realiza la bus-
 * queda
 * @author becerraarmr
 * @see CreateSolicitud
 */
public class BusquedaSolicitud extends CreateSolicitud {

  private static final Logger LOGGER = LogManager.getLogger(BusquedaSolicitud.class);

  private String producto;
  private String telefono;
  private String direccion;
  private String contacto;
  private String unidades;
  private String torres;
  private String nodo;

  /**
   * Permite renderizar los datos de la solicitud consultada
   */
  public boolean veSolicitud = false;

  @EJB
  private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
  @EJB
  private TecArcCamEstratoFacadeLocal tecArcCamEstratoFacadeLocal;
  @EJB
  private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
  private List<TecArchivosCambioEstrato> tecArchivosCambioEstratos;
  private boolean botonListMigrada;
  private boolean botonListEstado;
  
 

  /**
   * Creates a new instance of BusquedaSolicitud
   */
  public BusquedaSolicitud() {
    JsfUtil.addSuccessMessage("Búsqueda de la Solicitud");
   
  }

  /**
   * Buscar la solicitud según su id.
   * <p>
   * Se busca la solicitud según su id y se carga el detalle de la misma.
   *
   * @return la dirección de la página a donde dirigue una vez realizada la
   *         consulta.
   *         Si returna Verificación de casas o HHPP Unidireccional se dirigue a las
   *         páginas
   *         contenidas en catastro-war.
   *
   * @author becerraarmr
   * @Exception si no puede realizar consulta en la base de datos
   */
  public String loadBusqueda() {
    try {
      setSolicitud(getSolicitudFacadeLocal().findById(
              getSolicitud().getIdSolicitud()));
      if (getSolicitud() != null) {
        buscaDatosEdificio();
        return renderizarRespuesta();
      } else {
        JsfUtil.addErrorMessage("El número de visita técnica ingresado no "
                + "devuelve valor en la base de datos. Por favor inténtelo de "
                + "nuevo o verifique su número de visita");
      }
    } catch (ApplicationException e) {
        String msg = "No se pudo conectar con la base de datos para "
                + "buscar la solicitud";
        LOGGER.error(msg + ": " + e.getMessage(), e);
        JsfUtil.addErrorMessage(msg);
        throw new Error(e);
    } catch (Exception e) {
      String msg = "No se pudo conectar con la base de datos para "
              + "buscar la solicitud";
        LOGGER.error(msg + ": " + e.getMessage(), e);
      JsfUtil.addErrorMessage(msg);
      throw new Error(e);
    }
    return "";
  }

  /**
   * Renderizar a la página correspondiente
   * <p>
   * Renderiza a la página según el tipo de solicitud.
   */
  private String renderizarRespuesta() {
    String render = "";
    if (getSolicitud() != null) {
      String tipoSol = getSolicitud().getTipo();
      if (tipoSol != null && !tipoSol.isEmpty()
              && (tipoSol.equalsIgnoreCase(
                      TipoSolicitud.TIPO_VTCASA.getValor())
              || tipoSol.equalsIgnoreCase(
                      TipoSolicitud.TIPO_VTHHPPUNIDI.getValor()))) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        session.setAttribute("idSolicitud",
                this.getSolicitud().getIdSolicitud());
        session.setAttribute("tipoSol", tipoSol);

        String url = "";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String initUrl = context.getRequestContextPath() + "/view/estado/";

        
        url += initUrl;
        url += "estadoSolicitud.jsf";
        session.setAttribute("RedirecUrlAddress", url);

        return "direc_cvduu";
      } else {
        //reenderiza parte de la página oculta con los datos de la consulta
        veSolicitud = true;
      }
    }
    return render;
  }

  /**
   * Buscar en la As400
   * <p>
   * Busca en la AS400 los datos del edificio como: Unidades, Nodo Torre,
   * Contacto, Calle, Casa, producto, telefono.
   * <p>
     * @throws co.com.claro.mgl.error.ApplicationException
   */
  public void buscaDatosEdificio() throws ApplicationException {
    if (getSolicitud() != null) {
      if (getSolicitud().getCuentaMatriz() != null) {

          List<CmtCuentaMatrizMgl> cuenta =
                  cuentaMatrizMglFacadeLocal.findByNumeroCM(new BigDecimal(getSolicitud().getCuentaMatriz()));

          if (cuenta.size() > 0) {
              CmtCuentaMatrizMgl cm = cuenta.get(0);

              if (cm.getSubEdificioGeneral() != null) {
                  this.producto = cm.getSubEdificioGeneral().getProductoObj() != null
                          ? cm.getSubEdificioGeneral().getProductoObj().getNombreBasica() : "Sin datos";
                  this.direccion = cm.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                  this.contacto = cm.getSubEdificioGeneral().getAdministrador()
                          != null ? cm.getSubEdificioGeneral().getAdministrador() : "";
                  this.nodo = cm.getSubEdificioGeneral().getNodoObj() != null
                          ? cm.getSubEdificioGeneral().getNodoObj().getNodCodigo() : "";
                  this.torres = String.valueOf(cm.getSubEdificiosMglNoGeneral().size());
                  this.telefono = cm.getSubEdificioGeneral().getTelefonoPorteria() != null
                          ? cm.getSubEdificioGeneral().getTelefonoPorteria() : "";
                  this.unidades = String.valueOf(cm.getSubEdificioGeneral().getUnidades());
              } else {
                  JsfUtil.addErrorMessage("Numero de cuenta matriz invalido");
              }

          }
          //carga la lista de links de documentos  
          tecArchivosCambioEstratos = tecArcCamEstratoFacadeLocal.
                  findUrlsByIdSolicitud(getSolicitud());
      }
    }
  }

  public boolean isVeSolicitud() {
    return veSolicitud;
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Busca el valor del atributo correspondiente.
   *
   * @author becerraarmr
   *
   * @return el String que representa el atributo
   */
  public String getProducto() {
    return producto;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public String getContacto() {
    return contacto;
  }

  public String getUnidades() {
    return unidades;
  }

  public String getTorres() {
    return torres;
  }

  public String getNodo() {
    return nodo;
  }

    public List<TecArchivosCambioEstrato> getTecArchivosCambioEstratos() {
        return tecArchivosCambioEstratos;
    }

    public void setTecArchivosCambioEstratos(List<TecArchivosCambioEstrato> tecArchivosCambioEstratos) {
        this.tecArchivosCambioEstratos = tecArchivosCambioEstratos;
    }
  
  
  
  public String gestionarSolicitud(AccionesVT action,Solicitud solicitud) throws ApplicationException{
    
    Solicitud solicitudSesion = (Solicitud)session.getAttribute("solicitud");
    botonListMigrada   =  (Boolean)session.getAttribute("botonVolLisMigrada");
    botonListEstado   =  (Boolean)session.getAttribute("botonVolLisEstado");
    
              
    if(solicitudSesion != null){
       solicitud= solicitudSesion; 
    }
    setSolicitud(solicitud);
    buscaDatosEdificio();
    
    AccionesVT accionesVt = (AccionesVT) session.getAttribute("accionVt");
      if (accionesVt != null) {
          action = accionesVt;
      }
      
    switch (action){
      case GESTIONAR_VT_EDIFICIO_CONJUNTO:{
        return "GESTIÓN VISITAS TECNICAS EDIFICIO CONJUNTO";
      }
      case GESTIONAR_VT_EN_CASAS:{
        return "GESTIÓN VISITAS TECNICAS EN CASAS";
      }
      case GESTIONAR_VT_REPLANTEAMIENTO:{
        return "GESTIÓN VISITAS TECNICAS REPLANTEAMIENTO";
      }
      case GESTIONAR_VIABILIDAD_INTERNET:{
        return "GESTIÓN VISITAS VIABILIDAD INTERNET";
      }
      case GESTIONAR_CREACION_CUENTA_MATRIZ:{
        return "GESTIONAR CREACIÓN DE CUENTA MATRIZ";
      }
      case GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ:{
        return "GESTIÓN CREACIÓN HHPP EN CUENTA MATRIZ";
      }
      case GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ:{
        return "GESTIÓN DE MODIFICAR ELIMINAR CUENTA MATRIZ";
      }
    }
    return "GESTIONAR";
  }
  
  public String estadoSolicitud(){
    Solicitud solicitudAux=getSolicitud();
    if(solicitudAux==null) return "";
    String tipoSolicitud=solicitudAux.getTipo();
    if(tipoSolicitud.equalsIgnoreCase("VTCONJUNTOS")){
      return "VISITAS TECNICAS EDIFICIO CONJUNTO";
    }else if(tipoSolicitud.equalsIgnoreCase("REPLANVTCON")){
      return "VISITAS TECNICAS REPLANTEAMIENTO";
    }else if(tipoSolicitud.equalsIgnoreCase("REPLANVTCASA")){
      return "VISITAS TECNICAS EN CASAS";
    }else if(tipoSolicitud.equalsIgnoreCase("CREACIONCM")){
      return "GESTIONAR CREACIÓN DE CUENTA MATRIZ";
    }else if(tipoSolicitud.equalsIgnoreCase("HHPPCMATRIZ")){
      return "CREACIÓN HHPP EN CUENTA MATRIZ";
    }else if(tipoSolicitud.equalsIgnoreCase("VTMODECM")){
      return "MODIFICAR ELIMINAR CUENTA MATRIZ";
    }else if(tipoSolicitud.equalsIgnoreCase("VIINTERNET")){
      return "VIABILIDAD INTERNET";
    }
    return "";
  }
  
  public String prepareVerEstado(){
    return "/view/MGL/VT/Migracion/Solicitudes/VerEstado.xhtml?faces-redirect=true";
  }

    public boolean isBotonListMigrada() {
        return botonListMigrada;
    }

    public void setBotonListMigrada(boolean botonListMigrada) {
        this.botonListMigrada = botonListMigrada;
    }

    public boolean isBotonListEstado() {
        return botonListEstado;
    }

    public void setBotonListEstado(boolean botonListEstado) {
        this.botonListEstado = botonListEstado;
    }
  
}
