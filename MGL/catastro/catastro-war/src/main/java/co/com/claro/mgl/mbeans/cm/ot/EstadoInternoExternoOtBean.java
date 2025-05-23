package co.com.claro.mgl.mbeans.cm.ot;



import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoIntxExtMglFacaceLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
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
import java.util.Date;
import java.util.List;
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
@ManagedBean(name = "estadosExternosInternosOtBean")
@ViewScoped
public class EstadoInternoExternoOtBean implements Serializable {

    private BigDecimal idEstadoInternoOt;
     private BigDecimal idEstadoInternoOtFiltro;
    private BigDecimal idEstadoExternoOt;
    private CmtEstadoIntxExtMgl cmtEstadoIntxExtMglSeleccionada;
    private List<CmtBasicaMgl> estadoInternoOtList;
    private List<CmtBasicaMgl> estadoExternoOtList;
    private List<CmtEstadoIntxExtMgl> cmtEstadoIntxExtMglList;  
    private boolean showEstadosIntXExtList;
    private boolean showEditEstadosIntXExt;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private String regresarMenu = "<- Regresar Menú";
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    private SecurityLogin securityLogin;
    private String cedulaUsuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    //Opciones agregadas para Rol
    private final String ADDBTNCEI = "ADDBTNCEI";    
    private final String EDITBTNCEI = "EDITBTNCEI";
    private final String DELBTNCEI = "DELBTNCEI";

    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtEstadoIntxExtMglFacaceLocal cmtEstadoIntxExtMglFacaceLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private static final Logger LOGGER = LogManager.getLogger(EstadoInternoExternoOtBean.class);

    public EstadoInternoExternoOtBean() {
        try {
             securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();
            
            if (usuarioVT == null) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
            }
        } catch (IOException ex) {
            LOGGER.error("Se generea error en estados internos externos " + ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
         CmtTipoBasicaMgl tipoBasicaEstadoInternoOt
                 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_OT);

            estadoInternoOtList = cmtBasicaMglFacadeLocal
                    .findGrupoProyectoBasicaList(tipoBasicaEstadoInternoOt.getTipoBasicaId());            

            CmtTipoBasicaMgl tipoBasicaEstadoExternoOt
                 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_EXTERNO_OT);
            
            estadoExternoOtList = cmtBasicaMglFacadeLocal
                    .findProyectoBasicaList(tipoBasicaEstadoExternoOt.getTipoBasicaId());
            
            cmtEstadoIntxExtMglFacaceLocal
                    .setUser(usuarioVT, perfilVt);
            cargarEstadosInternosExternos(1);
            showListar();
        } catch (ApplicationException e) {
            LOGGER.error("Se genera error en cargue de listados "
                    + "de internos y externos", e);
            String msn = "Se genera error en cargue de listados "
                    + "de internos y externos";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función utilizada para hacer el cargue del listado que se despliega en la
     * tabla principal.
     *
     * @author Juan David Hernandez
     * @param paginaSeleccionada
     */
    public void cargarEstadosInternosExternos(int paginaSeleccionada) {
        try {
            actual = paginaSeleccionada;
            getPageActual();
            cmtEstadoIntxExtMglList = cmtEstadoIntxExtMglFacaceLocal
                    .findAllEstadoInternoExternoList(paginaSeleccionada, filasPag);

        } catch (ApplicationException e) {
            LOGGER.error("Se genera error en cargue de listado de tabla de"
                    + " estado internos y externos", e);
            String msn = "Se genera error en cargue de listado de tabla de"
                    + " estado internos y externos";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: cargarEstadosInternosExternos() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Realiza redireccionamiento a pantalla de editar
     *
     * @author Juan David Hernandez
     * @param configuracion
     */
    public void goEditarRelacionEstados(CmtEstadoIntxExtMgl configuracion) {
        try {
            if (configuracion != null) {
                cmtEstadoIntxExtMglSeleccionada = new CmtEstadoIntxExtMgl();
                cmtEstadoIntxExtMglSeleccionada = configuracion.clone();
                showEditar();
            } else {
                String msn = "Se ha presentado un error seleccionado "
                        + "la relacion de estados. Intente de nuevo por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (Exception e) {
            LOGGER.error("Se genera error al redireccionar a editar "
                    + "configuración", e);
            String msn = "Se genera error al redireccionar a editar "
                    + "configuración";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }

    /**
     * Función que realiza inserción del registro en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void guardarRelacionEstadoInXExt() {
        try {
            if (validarDatosCrearRelacionEstados()) {
                if(validarExistenciaRelacionEstados(idEstadoInternoOt,null)){
                    if (validarRelacionEstadoEliminadaPreviamente(idEstadoInternoOt, idEstadoExternoOt)) {
 
                CmtEstadoIntxExtMgl cmtEstadoIntxExtMgl = new CmtEstadoIntxExtMgl();
                CmtBasicaMgl estadoInternoBasicaId = new CmtBasicaMgl();
                CmtBasicaMgl estadoExternoBasicaId = new CmtBasicaMgl();
                estadoInternoBasicaId.setBasicaId(idEstadoInternoOt);
                estadoExternoBasicaId.setBasicaId(idEstadoExternoOt);
                
                cmtEstadoIntxExtMgl.setIdEstadoInt(estadoInternoBasicaId);
                cmtEstadoIntxExtMgl.setIdEstadoExt(estadoExternoBasicaId);
                cmtEstadoIntxExtMgl.setDetalle("CREACION");
                cmtEstadoIntxExtMgl.setUsuarioCreacion(usuarioVT);
                cmtEstadoIntxExtMgl.setPerfilCreacion(perfilVt);
                            
                cmtEstadoIntxExtMglFacaceLocal
                        .createEstadoIntxExtMgl(cmtEstadoIntxExtMgl, usuarioVT, perfilVt);
                limpiar();
                String msn = "Relación de estados creada satisfactoriamente.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }else{
                        limpiar();
                        String msn = "Relación de estados creada satisfactoriamente";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));  
                    }
                }else{
                    String msn = "El estado interno que desea agregar ya se encuentra configurado.";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Se genera error al crear relacion de estados", e);
            String msn = "Se genera error al crear relacion de estados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: guardarRelacionEstadoInXExt() " + e.getMessage(), e, LOGGER);
        }
    }
    
    /**
     * Función que realiza inserción del registro en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void filtrarByEstadoInterno() {
        try {
            if (validarFiltroByEstadoInterno()) {
                CmtBasicaMgl estadoInternoBasicaIdFiltro = new CmtBasicaMgl();
                estadoInternoBasicaIdFiltro.setBasicaId(idEstadoInternoOtFiltro);
                   CmtEstadoIntxExtMgl cmtEstadoIntxExtMglFiltrado 
                           = cmtEstadoIntxExtMglFacaceLocal.findByEstadoInterno(estadoInternoBasicaIdFiltro);
                   cmtEstadoIntxExtMglList = new ArrayList();
                if (cmtEstadoIntxExtMglFiltrado != null) {
                    cmtEstadoIntxExtMglList.add(cmtEstadoIntxExtMglFiltrado);
                }

            }
        } catch (ApplicationException e) {
            LOGGER.error("Se genera error filtrar por estado interno", e);
            String msn = "Se genera error filtrar por estado interno";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
       } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: filtrarByEstadoInterno() " + e.getMessage(), e, LOGGER);
        }
    }
    
    /**
     * Función que limpiar los datos de la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void limpiar() {
        cargarEstadosInternosExternos(1);
        limpiarForm();        
    }

    /**
     * Función que por estado interno
     *
     * @author Juan David Hernandez
     */
    public void editarRelacionEstados() {
        try {
            if (validarEditarConfiguracion()) {
                cmtEstadoIntxExtMglFacaceLocal
                        .updateEstadoIntxExtMgl(cmtEstadoIntxExtMglSeleccionada, usuarioVT, perfilVt);
                limpiar();
                showListar();
                String msn = "Relacion de estados editada satisfactoriamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: guardarRelacionEstadoInXExt() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que elimina lógicamente el registro en la base de datos.
     *
     * @author Juan David Hernandez
     * @param relacionEstadosDelete
     */
    public void eliminarRelacionEstados(CmtEstadoIntxExtMgl relacionEstadosDelete) {
        try {
            if (relacionEstadosDelete == null || relacionEstadosDelete.getIdEstadoIntExt() == null) {
                String msn = "Ha ocurrido un error al seleccionar el registro. "
                        + "Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));               
            }else{
            //se hace borrado logico del registro
            relacionEstadosDelete.setEstadoRegistro(0);
            cmtEstadoIntxExtMglFacaceLocal
                    .updateEstadoIntxExtMgl(relacionEstadosDelete, usuarioVT, perfilVt);
            limpiarForm();
            cargarEstadosInternosExternos(1);
            String msn = "Relacion de estados eliminada satisfactoriamente";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationException e) {
            LOGGER.error("Se genera error en eliminar relacion", e);
            String msn = "Se genera error en eliminar relacion";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: eliminarRelacionEstados() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que valida que la información a persistir no se encuentre nula.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarDatosCrearRelacionEstados() {
        try {
            if (idEstadoInternoOt == null || idEstadoInternoOt.equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un estado interno por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            if (idEstadoExternoOt == null || idEstadoExternoOt.equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un estado externo por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }

            return true;
        } catch (Exception e) {
            LOGGER.error("Se genera error al validar datos de crear"
                    + " configuración", e);
            String msn = "Se genera error al validar datos de crear"
                    + " configuración";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }
    
    public boolean validarExistenciaRelacionEstados(BigDecimal idEstadoInternoOt, BigDecimal idEstadoExternoOt) {
        try {
            CmtEstadoIntxExtMgl cmtEstadoIntxExtBuscado = cmtEstadoIntxExtMglFacaceLocal
                    .findByEstadoInternoXExterno(idEstadoInternoOt, idEstadoExternoOt,1);
            //Si se encuentra una relacion de estados igual return false
            if (cmtEstadoIntxExtBuscado != null) {
                return false;
                
            }
            return true;
        } catch (ApplicationException e) {
            LOGGER.error("Se genera error al validar"
                    + " la existencia de una relacion actual de estados", e);
            String msn = "Se genera error al validar la existencia de una "
                    + "relacion actual de estados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: validarExistenciaRelacionEstados() " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public boolean validarRelacionEstadoEliminadaPreviamente(BigDecimal idEstadoInternoOt,
            BigDecimal idEstadoExternoOt) {
        try {
            CmtEstadoIntxExtMgl cmtEstadoIntxExtBuscado = null;
            
            cmtEstadoIntxExtBuscado = cmtEstadoIntxExtMglFacaceLocal
                    .findByEstadoInternoXExterno(idEstadoInternoOt, idEstadoExternoOt, 0);
            /*Si el registro ya se encuentra eliminado anteriormente lo que se hace es volver a habilitarlo
            de lo contrario se crea de manera normal*/
            if (cmtEstadoIntxExtBuscado != null && cmtEstadoIntxExtBuscado.getIdEstadoIntExt() != null) {
                cmtEstadoIntxExtBuscado.setEstadoRegistro(1);
                cmtEstadoIntxExtBuscado.setFechaCreacion(new Date());
                cmtEstadoIntxExtMglFacaceLocal
                        .updateEstadoIntxExtMgl(cmtEstadoIntxExtBuscado, usuarioVT, perfilVt);
                return false;
            }
            return true;
        } catch (ApplicationException e) {
            LOGGER.error("Se genera error al validar"
                    + " la existencia de una relacion actual de estados", e);
            String msn = "Se genera error al validar la existencia de una "
                    + "relacion actual de estados";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn + e.getMessage(), ""));
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: validarRelacionEstadoEliminadaPreviamente() " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    

    /**
     * Función que valida que la información a editar no se encuentre nula.
     *
     * @author Juan David Hernandez
     * @return boolean
     */
    public boolean validarEditarConfiguracion() {
        try {
            if (cmtEstadoIntxExtMglSeleccionada.getIdEstadoInt()== null
                    || cmtEstadoIntxExtMglSeleccionada.getIdEstadoInt().equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un estado interno por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            if (cmtEstadoIntxExtMglSeleccionada.getIdEstadoExt() == null
                    || cmtEstadoIntxExtMglSeleccionada.getIdEstadoExt().equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un estado externo por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }            
           
            if(!validarExistenciaRelacionEstados(cmtEstadoIntxExtMglSeleccionada.getIdEstadoInt().getBasicaId(), 
                    cmtEstadoIntxExtMglSeleccionada.getIdEstadoExt().getBasicaId())){
                String msn = "La relación que desea crear ya se encuentra registrada en la base de datos";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;                
            }
            
            return true;
        } catch (Exception e) {
            LOGGER.error("Se genera error al validar datos de editar "
                    + "configuración", e);
            String msn = "Se genera error al validar datos de editar"
                    + " configuración";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }
    
    /**
     * Función que valida los datos para filtrarpor estado interno.
     *
     * @author Juan David Hernandez
     * @return boolean
     */
    public boolean validarFiltroByEstadoInterno() {
        try {
            if (idEstadoInternoOtFiltro == null
                    || idEstadoInternoOtFiltro.equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un estado interno para filtrar por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            } 
            return true;
        } catch (Exception e) {
            LOGGER.error("Se genera error al validar datos de filtro "
                    + "por estado", e);
            String msn = "Se genera error al validar datos de filtro"
                    + " por estado";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
            return false;
        }
    }


    /**
     * Función que carga listado de configuraciones.
     *
     * @author Juan David Hernandez
     */
    public void goListadoEstadosInternosExternosList() {
        try {
            limpiarForm();
            cargarEstadosInternosExternos(1);
            showListar();
        } catch (Exception e) {
            LOGGER.error("Se genera error al intentar regresar atrás a cargar"
                    + " listado", e);
            String msn = "Se genera error al intentar regresar atrás a cargar"
                    + " listado";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
        }
    }

    /**
     * Función que limpia la información de la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void limpiarForm() {
        idEstadoInternoOt = BigDecimal.ZERO;
        idEstadoExternoOt = BigDecimal.ZERO;
        idEstadoInternoOtFiltro = BigDecimal.ZERO;
        cmtEstadoIntxExtMglSeleccionada = new CmtEstadoIntxExtMgl();
    }

    /**
     * Función que redirecciona al menú principal del a aplicación.
     *
     * @author Juan David Hernandez
     * @throws java.io.IOException
     */
    public void redirecToMenu()  {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            LOGGER.error("Se genera error al itentar regresar al menú"
                    + " principal", e);
            String msn = "Se genera error al itentar regresar al menú"
                    + " principal";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    msn + e.getMessage(), ""));
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en EstadoInternoExternoOtBean: redirecToMenu() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @return null;
     * @author Juan David Hernandez
     */
    private void listInfoByPage(int page) {
        cargarEstadosInternosExternos(page);
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error cargar la primera página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al cargar la página anterior. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a la página seleccionada. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al cargar la siguiente página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al cargar la última página. EX000 " + ex.getMessage(), ex);
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
            int pageSol = cmtEstadoIntxExtMglFacaceLocal.countEstadoInternoExterno();
            return ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al obtener el total de páginas de resultados. EX000 " + ex.getMessage(), ex);
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

    /**
     * Función que permite rederizar la página de editar.
     *
     * @author Juan David Hernandez
     */
    public void showEditar() {
        showEditEstadosIntXExt = true;
        showEstadosIntXExtList = false;
    }

    /**
     * Función que permite rederizar la página de listado.
     *
     * @author Juan David Hernandez
     */
    public void showListar() {
        showEditEstadosIntXExt = false;
        showEstadosIntXExtList = true;
    }

    public boolean isShowEstadosIntXExtList() {
        return showEstadosIntXExtList;
    }

    public void setShowEstadosIntXExtList(boolean showEstadosIntXExtList) {
        this.showEstadosIntXExtList = showEstadosIntXExtList;
    }

    public boolean isShowEditEstadosIntXExt() {
        return showEditEstadosIntXExt;
    }

    public void setShowEditEstadosIntXExt(boolean showEditEstadosIntXExt) {
        this.showEditEstadosIntXExt = showEditEstadosIntXExt;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
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
    
    

    public List<CmtBasicaMgl> getEstadoInternoOtList() {
        return estadoInternoOtList;
    }

    public void setEstadoInternoOtList(List<CmtBasicaMgl> estadoInternoOtList) {
        this.estadoInternoOtList = estadoInternoOtList;
    }

    public List<CmtBasicaMgl> getEstadoExternoOtList() {
        return estadoExternoOtList;
    }

    public void setEstadoExternoOtList(List<CmtBasicaMgl> estadoExternoOtList) {
        this.estadoExternoOtList = estadoExternoOtList;
    }

    public List<CmtEstadoIntxExtMgl> getCmtEstadoIntxExtMglList() {
        return cmtEstadoIntxExtMglList;
    }

    public void setCmtEstadoIntxExtMglList(List<CmtEstadoIntxExtMgl> cmtEstadoIntxExtMglList) {
        this.cmtEstadoIntxExtMglList = cmtEstadoIntxExtMglList;
    }

    public CmtEstadoIntxExtMgl getCmtEstadoIntxExtMglSeleccionada() {
        return cmtEstadoIntxExtMglSeleccionada;
    }

    public void setCmtEstadoIntxExtMglSeleccionada(CmtEstadoIntxExtMgl cmtEstadoIntxExtMglSeleccionada) {
        this.cmtEstadoIntxExtMglSeleccionada = cmtEstadoIntxExtMglSeleccionada;
    }



    public BigDecimal getIdEstadoInternoOt() {
        return idEstadoInternoOt;
    }

    public void setIdEstadoInternoOt(BigDecimal idEstadoInternoOt) {
        this.idEstadoInternoOt = idEstadoInternoOt;
    }

    public BigDecimal getIdEstadoExternoOt() {
        return idEstadoExternoOt;
    }

    public void setIdEstadoExternoOt(BigDecimal idEstadoExternoOt) {
        this.idEstadoExternoOt = idEstadoExternoOt;
    }


    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

    public BigDecimal getIdEstadoInternoOtFiltro() {
        return idEstadoInternoOtFiltro;
    }

    public void setIdEstadoInternoOtFiltro(BigDecimal idEstadoInternoOtFiltro) {
        this.idEstadoInternoOtFiltro = idEstadoInternoOtFiltro;
    }
    
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionAgregar() {
        return validarEdicionRol(ADDBTNCEI);
    }
    public boolean validarOpcionEditar() {
        return validarEdicionRol(EDITBTNCEI);
    }
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(DELBTNCEI);
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
