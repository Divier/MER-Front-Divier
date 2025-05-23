/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direccion.mbeans.vetoNodos;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.VetoMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuariosPortalFacadeLocal;
import co.com.claro.mgl.jpa.entities.VetoCanalMgl;
import co.com.claro.mgl.jpa.entities.VetoCiudadMgl;
import co.com.claro.mgl.jpa.entities.VetoMgl;
import co.com.claro.mgl.jpa.entities.VetoNodosMgl;
import co.com.claro.mgl.jpa.entities.ptlus.UsuariosPortal;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "vetoNodosDetalleBean")
public class VetoNodosDetalleBean implements Serializable {

    private VetoMgl vetoSeleccionado;
    private String usuarioVt = null;
    private int perfilVt = 0;   
    private UsuariosPortal usuarioLogin = new UsuariosPortal();
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private List<VetoCanalMgl> canalVetoPaginadaList; 
    private List<VetoCiudadMgl> ciudadPaginadaList;
    private List<VetoNodosMgl> nodoVetoPaginadaList;
    private String inicioPagina = "<< -";
    private String anteriorPagina ="< -";
    private String finPagina ="- >>";
    private String siguientePagina ="- >";
    private String pageActualVetoNodo;
    private String numPaginaVetoNodo = "1"; 
    private String pageActualVetoCiudad;
    private String numPaginaVetoCiudad = "1";
    private String pageActualVetoCanal;
    private String numPaginaVetoCanal = "1"; 
    private List<Integer> paginaListVetoNodo;
    private List<Integer> paginaListVetoCiudad;
    private List<Integer> paginaListVetoCanal;
    private boolean venta;
    private boolean creacion;
    private int actualVetoNodo;
    private int actualVetoCiudad;
    private int actualVetoCanal;    
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private HttpSession session = (HttpSession) 
            facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) 
            facesContext.getExternalContext().getResponse();    
    private static final Logger LOGGER = LogManager.getLogger(VetoNodosDetalleBean.class);
    
    @EJB
    private VetoMglFacadeLocal vetoMglFacadeLocal;
    @EJB
    private UsuariosPortalFacadeLocal usuariosPortalFacadeLocal;

    public VetoNodosDetalleBean() {
       try {            
           SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect
                      (securityLogin.redireccionarLogin());
                return;
            }
            usuarioVt = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            if (usuarioVt == null) {
                response.sendRedirect
                      (securityLogin.redireccionarLogin());
            }
        } catch (IOException e) {
            LOGGER.error("Error al arrancar la pantalla", e);
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        }
    }
    
    @PostConstruct
    public void init() {
        try {            
            vetoMglFacadeLocal.setUser(usuarioVt, perfilVt);
          
            // Instacia Bean de Session para obtener la solicitud seleccionada
            VetoNodosSessionBean  vetoNodosSessionBean = 
                    (VetoNodosSessionBean) JSFUtil.getSessionBean
                    (VetoNodosSessionBean.class);
            
            vetoSeleccionado = vetoNodosSessionBean.getVetoSeleccionado(); 
            validarTipoVetoSeleccionado(vetoSeleccionado);
            obtenerListadoVetoCanal(1);
            obtenerListadoVetoCiudad(1);
            obtenerListadoVetoNodo(1);
        } catch (ApplicationException e) {
            LOGGER.error("Error en el cargue inicial del detalle del Veto ", e);
            FacesUtil.mostrarMensajeError("Error en el cargue inicial del detalle del Veto " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en el cargue inicial del detalle del Veto " + e.getMessage(), e, LOGGER);
        }
    }
    
    /**
     * Función que valida que los datos a crear en el veto esten completos
     *
     * @author Juan David Hernandez  
     * return 1 venta 2 creacion 3 ambos
     * @return 
     */
    public String validarTipoVeto(){
        try {         
            if(venta && !creacion){
                    return "1"; 
               }else{
                   if(!venta && creacion){
                       return "2";
                   }else{
                       if(venta && creacion){
                           return "3";
                       }
                   }
            }            
            return "0";            
        } catch (Exception e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg,e);
            return "0";
        }
    }
    
     /**
     * Función que actualiza el registro de veto de nodos en la base de datos.
     * 
     * @author Juan David Hernandez
     */
    public void guardarCambiosVeto() {
        try {
            if (vetoSeleccionado != null) {
                if (validarDatosVeto()) {
                    if(vetoSeleccionado.getFechaFin() == null){                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
                        vetoSeleccionado.setFechaFin(sdf.parse("01/01/3000")); 
                    }
                    vetoSeleccionado.setTipoVeto(validarTipoVeto());
                    vetoMglFacadeLocal.updateVeto(vetoSeleccionado);
                    String msnError = "Veto Actualizado Correctamento";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msnError, ""));
                }
            }
        } catch (ApplicationException | ParseException e) {
            LOGGER.error("Error al intentar actualizar el registro de veto ", e);
            FacesUtil.mostrarMensajeError("Error al intentar actualizar el registro de veto " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar actualizar el registro de veto " + e.getMessage(), e, LOGGER);
        }
    }
    
   
     /**
     * Función que validar el valor del tipo de veto
     * 
     * @author Juan David Hernandez
     * @param vetoSeleccionado
     */
    public void validarTipoVetoSeleccionado(VetoMgl vetoSeleccionado) {
        try {
            if (vetoSeleccionado != null
                    && vetoSeleccionado.getTipoVeto() != null
                    && !vetoSeleccionado.getTipoVeto().isEmpty()) {
                if (vetoSeleccionado.getTipoVeto().equals("0")) {
                    venta = false;
                    creacion = false;
                } else {
                    if (vetoSeleccionado.getTipoVeto().equals("1")) {
                        venta = true;
                        creacion = false;
                    } else {
                        if (vetoSeleccionado.getTipoVeto().equals("2")) {
                            venta = false;
                            creacion = true;
                        } else {
                            if (vetoSeleccionado.getTipoVeto().equals("3")) {
                                venta = true;
                                creacion = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
             LOGGER.error("Error al intentar validar el tipo de veto ", e);
            String msnError = "Error al intentar validar el tipo de veto";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
        }
    }
    
    /**
     * Función que valida los datos del veto no se encuentren nulos
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarDatosVeto() {
        try {
            if (!venta && !creacion) {
                String msnError = "Debe seleccionar "
                        + "un tipo de "
                        + "Veto. VENTA ó CREACIÓN HHPP.";
                FacesContext.getCurrentInstance()
                        .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            }
            else{
               if (vetoSeleccionado.getFechaInicio() == null) {
                String msnError = "La FECHA INICIO no puede ir vacia.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msnError, ""));
                return false;
            } else {
                if (!validarFecha(vetoSeleccionado.getFechaInicio())) {
                    String msnError = "La FEHCA INICIO ingresada no es valida.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                            msnError, ""));
                    return false;
                }  else {
                        if (!validarFecha(vetoSeleccionado.getFechaFin())) {
                            String msnError = "La FEHCA FIN ingresada no es valida.";
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                            return false;
                        }else{
                            if (vetoSeleccionado.getFechaInicio() != null
                                    && vetoSeleccionado.getFechaFin() != null) {
                                SimpleDateFormat formatoFecha 
                                        = new SimpleDateFormat("dd/MM/yyyy");
                                String date1 
                                        = formatoFecha.format
                                        (vetoSeleccionado.getFechaInicio());
                                String date2
                                        = formatoFecha.format
                                        (vetoSeleccionado.getFechaFin());
                                int diferencia = dateComparator(date2, date1);
                                if (diferencia < 0) {
                                    String msnError = "La FECHA FIN "
                                            + "no puede ser anterior a "
                                            + "la FECHA INICIO.";
                                    FacesContext.getCurrentInstance()
                                            .addMessage(null,
                                            new FacesMessage
                                            (FacesMessage.SEVERITY_WARN, 
                                            msnError, ""));
                                    return false;
                                }
                            }                                
                        }
                    }                
            }
        }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al intentar validar los dato del veto ", e);
            String msnError = "Error al intentar validar los dato del veto";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msnError + e.getMessage(), ""));
            return false;
        }
    }
    
   
    
    
    /**
     * Función que validar si una fecha es superior a otra.
     *
     * @author Juan David Hernandez
     * @param _endDate
     * @return 
     */
    public int dateComparator(String _firstDate, String _endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
            sdf.setLenient(false);
            Date firstDate = (Date) sdf.parse(_firstDate);
            Date endDate = (Date) sdf.parse(_endDate);
            return firstDate.compareTo(endDate);
        } catch (ParseException e) {
            FacesUtil.mostrarMensajeError("Error en el cargue inicial del detalle del Veto " + e.getMessage(), e, LOGGER);
            return -100;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en el cargue inicial del detalle del Veto " + e.getMessage(), e, LOGGER);
        }
        return 0;
    }

     /**
     * Función que validar que la fecha ingresada sea una fecha valida.
     * 
     * @author Juan David Hernandez
     * @param fecha
     * @return 
     */
    public boolean validarFecha(Date fecha){
        try {

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatoFecha.format(fecha);
            
            formatoFecha.setLenient(false);
            formatoFecha.parse(date);
            
            return true; 
            
        } catch (ParseException e) {
            FacesUtil.mostrarMensajeError("Error al intentar validar una fecha " + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar validar una fecha " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    
      /**
     * Función que redirecciona a pantalla de veto de nodos
     * 
     * @author Juan David Hernandez
     */
    public void backVetoNodos() {
        try {
            FacesUtil.navegarAPagina(
                    "/view/MGL/VT/vetoNodos/vetoNodosVt/vetoNodosVt.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al intentar regresar a veto de nodos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al intentar regresar a veto de nodos " + e.getMessage(), e, LOGGER);
        }
    }
    
    
    
       /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoNodo(int page) {
        try {       
            if (vetoSeleccionado.getVetoNodosList() != null
                    && !vetoSeleccionado.getVetoNodosList().isEmpty()) {
                actualVetoNodo = page;
                getPageActualVetoNodos();

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) 
                        > vetoSeleccionado.getVetoNodosList().size()) {
                    maxResult = vetoSeleccionado.getVetoNodosList().size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                nodoVetoPaginadaList = new ArrayList<VetoNodosMgl>();
                for (int i = firstResult; i < maxResult; i++) {
                    nodoVetoPaginadaList
                            .add(vetoSeleccionado.getVetoNodosList().get(i));
                }
            }

        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación ", e);
            String msnError = "Error en lista de paginación";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoNodo() {
        try {
            obtenerListadoVetoNodo(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = actualVetoNodo - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoNodo);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            int nuevaPageActual = actualVetoNodo + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoNodo(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoNodo() {
        try {
            int totalPaginas = getTotalPaginasVetoNodo();
            obtenerListadoVetoNodo(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoNodo() {
        try {
            int totalPaginas = 0;
            int pageSol = vetoSeleccionado.getVetoNodosList().size();           
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoNodos() {
        paginaListVetoNodo = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoNodo();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoNodo.add(i);
        }
        pageActualVetoNodo = String.valueOf(actualVetoNodo) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoNodo == null) {
            numPaginaVetoNodo = "1";
        }
        numPaginaVetoNodo = String.valueOf(actualVetoNodo);
        
        return pageActualVetoNodo;
    }
    
     /**
     * Función que realiza paginación de la tabla de ciudades.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoCiudad(int page) {
        try {                   
             if (vetoSeleccionado.getVetoCiudadList() != null
                     && !vetoSeleccionado.getVetoCiudadList().isEmpty()) {
                 actualVetoCiudad = page;
                 getPageActualVetoCiudades();

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) > vetoSeleccionado.getVetoCiudadList().size()) {
                    maxResult = vetoSeleccionado.getVetoCiudadList().size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                ciudadPaginadaList = new ArrayList<VetoCiudadMgl>();
                for (int i = firstResult; i < maxResult; i++) {
                    ciudadPaginadaList
                            .add(vetoSeleccionado.getVetoCiudadList().get(i));
                }
            }

        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación de ciudad", e);
            String msnError = "Error en lista de paginación de ciudad";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados de ciudades
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoCiudad() {
        try {
            obtenerListadoVetoCiudad(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = actualVetoCiudad - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoCiudad);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (NumberFormatException ex) {
             FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            int nuevaPageActual = actualVetoCiudad + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCiudad(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoCiudad() {
        try {
            int totalPaginas = getTotalPaginasVetoCiudad();
            obtenerListadoVetoCiudad(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoCiudad() {
        try {
            int totalPaginas = 0;          
            int pageSol = vetoSeleccionado.getVetoCiudadList().size();
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoCiudades() {
        paginaListVetoCiudad = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoCiudad();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoCiudad.add(i);
        }
        pageActualVetoCiudad = String.valueOf(actualVetoCiudad) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoCiudad == null) {
            numPaginaVetoCiudad = "1";
        }
        numPaginaVetoCiudad = String.valueOf(actualVetoCiudad);
        
        return pageActualVetoCiudad;
    }
    /********************************************************/
    
        
     /**
     * Función que realiza paginación de la tabla.
     * 
     * @param  page;
     * @author Juan David Hernandez
     * @return 
     */
    public String obtenerListadoVetoCanal(int page) {
        try {       
            if (vetoSeleccionado.getVetoCanalList() != null 
                    && !vetoSeleccionado.getVetoCanalList().isEmpty()) {
                actualVetoCanal = page;
                getPageActualVetoCanales(); 

                int firstResult = 0;
                if (page > 1) {
                    firstResult = (filasPag * (page - 1));
                }

                //Obtenemos el rango de la paginación
                int maxResult = 0;
                if ((firstResult + filasPag) 
                        > vetoSeleccionado.getVetoCanalList().size()) {
                    maxResult = vetoSeleccionado.getVetoCanalList().size();
                } else {
                    maxResult = (firstResult + filasPag);
                }
              
               /*Obtenemos los objetos que se encuentran en el rango de
                * la paginación y los guardarmos en la lista que se va a 
                * desplegar en pantalla*/
                canalVetoPaginadaList = new ArrayList<VetoCanalMgl>();
                for (int i = firstResult; i < maxResult; i++) {
                    canalVetoPaginadaList
                            .add(vetoSeleccionado.getVetoCanalList().get(i));
                }
            }
   
        } catch (Exception e) {
             LOGGER.error("Error en lista de paginación ", e);
            String msnError = "Error en lista de paginación";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + e.getMessage(), ""));
        }
        return null;
    }

    
     /**
     * Función que carga la primera página de resultados
     * 
     * @author Juan David Hernandez
     */
    public void pageFirstVetoCanal() {
        try {
            obtenerListadoVetoCanal(1);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     * 
     * @author Juan David Hernandez
     */
    public void pagePreviousVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = actualVetoCanal - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (Exception ex) {
             LOGGER.error("Error direccionando a la página anterior", ex);
            String msnError = "Error direccionando a la página anterior";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

     /**
     * Función que permite ir directamente a la página seleccionada
     * de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void irPaginaVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = Integer.parseInt(numPaginaVetoCanal);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (NumberFormatException ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     * 
     *@author Juan David Hernandez
     */
    public void pageNextVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            int nuevaPageActual = actualVetoCanal + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            obtenerListadoVetoCanal(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la siguiente página", ex);
            String msnError = "Error direccionando a la siguiente página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que carga la última página de resultados
     * 
     *@author Juan David Hernandez
     */
    public void pageLastVetoCanal() {
        try {
            int totalPaginas = getTotalPaginasVetoCanal();
            obtenerListadoVetoCanal(totalPaginas);
        } catch (Exception ex) {
          LOGGER.error("Error direccionando a la última página", ex);
            String msnError = "Error direccionando a la última página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginasVetoCanal() {
        try {
            int totalPaginas = 0;
            int pageSol = vetoSeleccionado.getVetoCanalList().size();
             
            totalPaginas = (int) ((pageSol % filasPag != 0) ?
                    (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (Exception ex) {
            LOGGER.error("Error direccionando a la primera página", ex);
            String msnError = "Error direccionando a la primera página";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    msnError + ex.getMessage(), ""));
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario
     * en los resultados.
     * 
     *@author Juan David Hernandez
     * @return 
     */
    public String getPageActualVetoCanales() {
        paginaListVetoCanal = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginasVetoCanal();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListVetoCanal.add(i);
        }
        pageActualVetoCanal = String.valueOf(actualVetoCanal) + " de "
                + String.valueOf(totalPaginas);

        if (numPaginaVetoCanal == null) {
            numPaginaVetoCanal = "1";
        }
        numPaginaVetoCanal = String.valueOf(actualVetoCanal);
        
        return pageActualVetoCanal;
    }
    
 
    public VetoMgl getVetoSeleccionado() {
        return vetoSeleccionado;
    }

    public void setVetoSeleccionado(VetoMgl vetoSeleccionado) {
        this.vetoSeleccionado = vetoSeleccionado;
    }

    public List<VetoCanalMgl> getCanalVetoPaginadaList() {
        return canalVetoPaginadaList;
    }

    public void setCanalVetoPaginadaList(List<VetoCanalMgl> canalVetoPaginadaList) {
        this.canalVetoPaginadaList = canalVetoPaginadaList;
    }

    public List<VetoCiudadMgl> getCiudadPaginadaList() {
        return ciudadPaginadaList;
    }

    public void setCiudadPaginadaList(List<VetoCiudadMgl> ciudadPaginadaList) {
        this.ciudadPaginadaList = ciudadPaginadaList;
    }

    public List<VetoNodosMgl> getNodoVetoPaginadaList() {
        return nodoVetoPaginadaList;
    }

    public void setNodoVetoPaginadaList(List<VetoNodosMgl> nodoVetoPaginadaList) {
        this.nodoVetoPaginadaList = nodoVetoPaginadaList;
    }

    public String getInicioPagina() {
        return inicioPagina;
    }

    public void setInicioPagina(String inicioPagina) {
        this.inicioPagina = inicioPagina;
    }

    public String getAnteriorPagina() {
        return anteriorPagina;
    }

    public void setAnteriorPagina(String anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    public String getFinPagina() {
        return finPagina;
    }

    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    public String getPageActualVetoNodo() {
        return pageActualVetoNodo;
    }

    public void setPageActualVetoNodo(String pageActualVetoNodo) {
        this.pageActualVetoNodo = pageActualVetoNodo;
    }

    public String getNumPaginaVetoNodo() {
        return numPaginaVetoNodo;
    }

    public void setNumPaginaVetoNodo(String numPaginaVetoNodo) {
        this.numPaginaVetoNodo = numPaginaVetoNodo;
    }

    public String getPageActualVetoCiudad() {
        return pageActualVetoCiudad;
    }

    public void setPageActualVetoCiudad(String pageActualVetoCiudad) {
        this.pageActualVetoCiudad = pageActualVetoCiudad;
    }

    public String getNumPaginaVetoCiudad() {
        return numPaginaVetoCiudad;
    }

    public void setNumPaginaVetoCiudad(String numPaginaVetoCiudad) {
        this.numPaginaVetoCiudad = numPaginaVetoCiudad;
    }

    public String getPageActualVetoCanal() {
        return pageActualVetoCanal;
    }

    public void setPageActualVetoCanal(String pageActualVetoCanal) {
        this.pageActualVetoCanal = pageActualVetoCanal;
    }

    public String getNumPaginaVetoCanal() {
        return numPaginaVetoCanal;
    }

    public void setNumPaginaVetoCanal(String numPaginaVetoCanal) {
        this.numPaginaVetoCanal = numPaginaVetoCanal;
    }

    public List<Integer> getPaginaListVetoNodo() {
        return paginaListVetoNodo;
    }

    public void setPaginaListVetoNodo(List<Integer> paginaListVetoNodo) {
        this.paginaListVetoNodo = paginaListVetoNodo;
    }

    public List<Integer> getPaginaListVetoCiudad() {
        return paginaListVetoCiudad;
    }

    public void setPaginaListVetoCiudad(List<Integer> paginaListVetoCiudad) {
        this.paginaListVetoCiudad = paginaListVetoCiudad;
    }

    public List<Integer> getPaginaListVetoCanal() {
        return paginaListVetoCanal;
    }

    public void setPaginaListVetoCanal(List<Integer> paginaListVetoCanal) {
        this.paginaListVetoCanal = paginaListVetoCanal;
    }

    public int getActualVetoNodo() {
        return actualVetoNodo;
    }

    public void setActualVetoNodo(int actualVetoNodo) {
        this.actualVetoNodo = actualVetoNodo;
    }

    public int getActualVetoCiudad() {
        return actualVetoCiudad;
    }

    public void setActualVetoCiudad(int actualVetoCiudad) {
        this.actualVetoCiudad = actualVetoCiudad;
    }

    public int getActualVetoCanal() {
        return actualVetoCanal;
    }

    public void setActualVetoCanal(int actualVetoCanal) {
        this.actualVetoCanal = actualVetoCanal;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isCreacion() {
        return creacion;
    }

    public void setCreacion(boolean creacion) {
        this.creacion = creacion;
    }
    
    
    
    
    
}
