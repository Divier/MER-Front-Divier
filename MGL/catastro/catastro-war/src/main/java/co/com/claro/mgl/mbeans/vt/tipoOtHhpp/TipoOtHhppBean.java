/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.tipoOtHhpp;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuariosPortalFacadeLocal;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "tipoOtHhppBean")
@ViewScoped
public class TipoOtHhppBean implements Serializable {

    
    private String usuarioVT = null;
    private int perfilVt = 0;
    private List<TipoOtHhppMgl> tipoOtHhppList;
    private String pageActual;
    private String numPagina = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private String regresarMenu = "<- Regresar Menú";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(TipoOtHhppBean.class);
    private SecurityLogin securityLogin;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @EJB
    private UsuariosPortalFacadeLocal usuariosPortalFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    //Opciones agregadas para Rol
    private final String COTTPBTGO = "COTTPBTGO";
    private final String CTOTGDBTN = "CTOTGDBTN";
 

    public TipoOtHhppBean() {
        try {
              securityLogin = new SecurityLogin(facesContext);
              if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }

        } catch (IOException e) {
            String msn = "Error al arrancar la pantalla.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al arrancar la pantalla.";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            tipoOtHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);
            cargarTipoOtHhppList();
        } catch (ApplicationException e) {
            String msn = "Error al realizar init de PostConstruct";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al realizar init de PostConstruct";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para cargar el listado de tipos de ot creadas en la
     * base de datos.
     *
     * @author Juan David Hernandez
     */
    public void cargarTipoOtHhppList() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            String msn = "Error al cargar listado de tipo de ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla de edición de un tipo
     * de ot existente en la base de datos.
     *
     * @author Juan David Hernandez
     * @param tipoOtSeleccionada
     */
    public void irEditarTipoOtHhpp(TipoOtHhppMgl tipoOtSeleccionada) {
        try {
            if (tipoOtSeleccionada != null
                    && tipoOtSeleccionada.getTipoOtId() != null) {
                // Instacia Bean de Session para obtener la solicitud seleccionada
                TipoOtHhppMglSessionBean tipoOtHhppMglSessionBean
                        = (TipoOtHhppMglSessionBean) JSFUtil.getSessionBean(TipoOtHhppMglSessionBean.class);
                tipoOtHhppMglSessionBean.setTipoOtHhppMglSeleccionado(tipoOtSeleccionada);
                FacesUtil.navegarAPagina("/view/MGL/VT/tipoOtHhpp/"
                                + "editarTipoOtHhpp.jsf");

            } else {
                String msnError = "Ha ocurrido un error seleccionando el"
                        + " tipo de ot, intente de nuevo por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msnError, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Error al redireccionar a editar de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al redireccionar a editar de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla de creación de Ot en
     * la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void irCrearTipoOtHhpp() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/tipoOtHhpp/"
                    + "crearTipoOtHhpp.jsf");
        } catch (ApplicationException e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función utilizada eliminar de manera lógica un registro de la base de
     * datos de tipo de ot
     *
     * @author Juan David Hernandez
     * @param tipoOtSeleccionado
     */
    public void eliminarTipoOTHhpp(TipoOtHhppMgl tipoOtSeleccionado) {
        try {
            if (tipoOtSeleccionado == null
                    || tipoOtSeleccionado.getTipoOtId().equals(BigDecimal.ZERO)) {
                String msn = "Ha ocurrido un error al seleccionar el registro. "
                        + "Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            if (!tipoOtHhppMglFacadeLocal
                    .validarTipoOtHhppEnUso(tipoOtSeleccionado.getTipoOtId())) {

                if (tipoOtHhppMglFacadeLocal.eliminarTipoOtHhpp(tipoOtSeleccionado)) {
                    cargarTipoOtHhppList();
                    String msn = "Tipo de Ot eliminada satisfactoriamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                } else {
                    String msn = "Ha ocurrido un error intentando eliminar"
                            + " el registro. Intente nuevamente por favor.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "La tipo de ot que desea eliminar se encuentra"
                        + " en uso asociada a una orden de trabajo. "
                        + "Es necesario cambiar la asociación en la orden "
                        + "de trabajo para poder eliminar este tipo de ot. ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error al redireccionar a creación de Tipo Ot";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @return 
     */
    public String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            tipoOtHhppList = tipoOtHhppMglFacadeLocal
                    .findAllTipoOtHhppPaginada(page, filasPag);

        } catch (ApplicationException e) {
            String msn = "Error en lista de paginación";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            String msn = "Error en lista de paginación";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }

        return null;
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            String msn = "Error direccionando a la primera página";
            FacesUtil.mostrarMensajeError(msn + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Error direccionando a la página anterior";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException ex) {
            String msn = "Error direccionando a página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            String msn = "Error direccionando a página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Error direccionando a la siguiente página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            String msn = "Error direccionando a la última página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            int pageSol = tipoOtHhppMglFacadeLocal.countAllTipoOtHhpp();

            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException ex) {
            String msn = "Error direccionando a la primera página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            String msn = "Error direccionando a la primera página";
            FacesUtil.mostrarMensajeError(msn + ex.getMessage() , ex, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public String getPageActual() {
        paginaList = new ArrayList<Integer>();
        int totalPaginas = getTotalPaginas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }
        pageActual = String.valueOf(actual) + " de "
                + String.valueOf(totalPaginas);

        if (numPagina == null) {
            numPagina = "1";
        }
        numPagina = String.valueOf(actual);

        return pageActual;
    }

    public List<TipoOtHhppMgl> getTipoOtHhppList() {
        return tipoOtHhppList;
    }

    public void setTipoOtHhppList(List<TipoOtHhppMgl> tipoOtHhppList) {
        this.tipoOtHhppList = tipoOtHhppList;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
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

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }
    
    
       // Validar Opciones por Rol
    
    public boolean validarOpcionCrearOT() {
        return validarEdicionRol(COTTPBTGO);
    }
    
    public boolean validarOpcionlinkID() {
        return validarEdicionRol(CTOTGDBTN);
    }
 
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin  );
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
