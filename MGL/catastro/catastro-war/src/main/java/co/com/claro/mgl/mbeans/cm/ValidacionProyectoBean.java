package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoValidacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtValidacionProyectoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtValidacionProyectoMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * Administrar la pagina de validacionProyectoListView Controla toda la
 * funcionalidad de la página y realiza el control de la funcionalidad de la
 * misma en la capa de presentación contra la capa de negocio.
 *
 * @author ortizjaf
 * @version 1.0 revision 11/05/2017.
 */
@ManagedBean(name = "validacionProyectoBean")
@ViewScoped
public class ValidacionProyectoBean {

    /**
     * Contiene el objeto para el manejo de logs en la página.
     */
    private static final Logger LOGGER = LogManager.getLogger(ValidacionProyectoBean.class);
    /**
     * Usuario logueado en la aplicación.
     */
    private String usuarioVT = null;
    /**
     * Perfil asociado al usuario logueado.
     */
    private int perfilVT = 0;
    /**
     * Contexto de la página.
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * Sessión de la página.
     */
    private HttpSession session = (HttpSession) facesContext.getExternalContext()
            .getSession(false);
    /**
     * Respuestas de la página.
     */
    private HttpServletResponse response = (HttpServletResponse) facesContext
            .getExternalContext().getResponse();
    /**
     * True o false para mostrar o no los componentes del formulario de
     * detalles.
     */
    private boolean renderDetalle;
    /**
     * True o false para mostrar o no los componentes del formulario de
     * creación.
     */
    private boolean renderCreacion;
    /**
     * True o false para mostrar o no los componentes del formulario de
     * busqueda.
     */
    private Boolean renderFiltro;
    /**
     * Contiene la lista de proyectos de la aplición.
     */
    private List<CmtBasicaMgl> basicaProyectoList;
    /**
     * Contiene la lista de tipos de validación.
     */
    private List<CmtTipoValidacionMgl> tipoValidacionList;
    /**
     * Contiene la lista de validaciones a crear al proyecto.
     */
    private List<CmtValidacionProyectoMgl> validacionProyectoCreacionList;
    /**
     * Objeto de persistencias de validaciones del proyecto.
     */
    private CmtValidacionProyectoMgl validacionProyectoSelected;
    /**
     * Lista de validaciones del proyecto consultadas.
     */
    private List<CmtValidacionProyectoMgl> validacionProyectoFiltroList;
    /**
     * Contiene el proyecto seleccionado en la página.
     */
    private String proyectoFiltroSelected;
    /**
     * Contiene el detalle del proyecto seleccionado en la página.
     */
    private String proyectoDetalleSelected;
    /**
     * Contiene el tipo de validación seleccionada.
     */
    private String tipoValFiltroSelected;
    /**
     * Contiene el número de la página actual.
     */
    private String pageActual;
    /**
     * Contiene la primer página de la tabla.
     */
    private String numPagina = "1";
    /**
     * Contiene la lista de las paginas de la consulta que se muestra en la
     * tabla.
     */
    private List<Integer> paginaList;
    /**
     * Variable axiliar de la pagina actual.
     */
    private int actual;
    /**
     * Contiene la cantidad de filas por página.
     */
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    /**
     * Almacena el mensaje de validación para los campos vacíos.
     */
    private String campoVacio = MensajeTipoValidacion.CAMPOS_VACIOS.getValor();
    /**
     * Injecta la interfaz de parametros
     */
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    /**
     * Injecta la interfaz de basicas
     */
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    /**
     * Injecta la interfaz de tipos de validación.
     */
    @EJB
    private CmtTipoValidacionMglFacadeLocal tipoValidacionMglFacadeLocal;
    /**
     * Injecta la interfaz de validaciones por proyecto.
     */
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    
    @EJB
    private CmtValidacionProyectoMglFacadeLocal validacionProyectoMglFacadeLocal;

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
      
      
    private final String FORMULARIO = "VALIDACIONPROYECTOLISTVIEW";
     //Opciones agregadas para Rol
    private final String BTCRACONF = "BTCRACONF";
    private final String ROLOPCCONFGVALPROY = "ROLOPCCONFGVALPROY";
    private final String ROLOPCIDVALPROY = "ROLOPCIDVALPROY";
    private final String ROLOPCDELVALPROY = "ROLOPCDELVALPROY";
    private SecurityLogin securityLogin;

    /**
     * Constructor que inicia el usuario y sus perfiles. Constructor que inicia
     * el usuario y sus perfiles.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public ValidacionProyectoBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
        } catch (IOException e) {
            String msn = "Ocurrio un error validando el usuario en ValidacionProyectoBean";
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validacionProyectoBean. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Inicializar parametros de la página. Inicia los parámetros de
     * formularios, objetos y variables de la página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    @PostConstruct
    private void init() {
        try {
            renderDetalle = false;
            renderCreacion = false;
            this.renderFiltro = true;
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PROYECTO_BASICA_ID);
            basicaProyectoList = basicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoProyecto);
            tipoValidacionList = tipoValidacionMglFacadeLocal.findAll();
            //Asignamos el usuario y perfil a los FacadeLocal
            validacionProyectoMglFacadeLocal.setUser(usuarioVT, perfilVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: init(). " + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: init()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Borrar las validaciones del proyecto. Borra las validaciones del
     * proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param valToRemove Validación de proyecto a eliminar.
     * @return Null
     */
    public String removeFromListConfiguracion(CmtValidacionProyectoMgl valToRemove) {
        try {
            validacionProyectoCreacionList.remove(valToRemove);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (Exception e) {
            LOGGER.error("Error al Borrar las validaciones del proyecto.  EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return null;
    }

    /**
     * Cargar la configuración previa de las validaciones del proyecto. Carga la
     * configuración previa de las validaciones del proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null.
     */
    public String cargarConfiguracionPrevia() {
        try {
            if (proyectoDetalleSelected != null
                    && !proyectoDetalleSelected.trim().isEmpty()) {
                CmtBasicaMgl proyectoSelected = new CmtBasicaMgl();
                proyectoSelected.setBasicaId(new BigDecimal(proyectoDetalleSelected));
                validacionProyectoCreacionList =
                        validacionProyectoMglFacadeLocal.
                        cargarConfiguracionPrevia(proyectoSelected);
                if (validacionProyectoCreacionList == null
                        && !validacionProyectoCreacionList.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            Constant.MSN_PROCESO_EXITOSO, ""));
                } else {
                    String msn = "No existen validaciones por configurar al proyecto";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }

            } else {
                String msn = "Debe seleccionar Proyecto";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ValidacionProyectoBean: cargarConfiguracionPrevia(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Crear la configuración de las validaciones por proyecto. Crea la
     * configuración de las validaciones por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null
     */
    public String crearConfiguracion() {
        try {
            if (validacionProyectoCreacionList != null
                    && !validacionProyectoCreacionList.isEmpty()) {
                validacionProyectoCreacionList =
                        validacionProyectoMglFacadeLocal.crearConfiguracion(
                        validacionProyectoCreacionList);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        Constant.MSN_PROCESO_EXITOSO, ""));
                validacionProyectoCreacionList = new ArrayList<CmtValidacionProyectoMgl>();
            } else {
                String msn = "No existe configuracion para cargar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: crearConfiguracion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Obtener las validaciones. Obtiene las validaciones segun el proyecto
     * seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null.
     */
    public String obtenerValidacionFiltro() {
        renderDetalle = false;
        renderCreacion = false;
        this.renderFiltro = true;
        if (proyectoFiltroSelected != null
                && !proyectoFiltroSelected.trim().isEmpty()) {
            listInfoByPage(1);
        } else {
            validacionProyectoFiltroList =
                    new ArrayList<CmtValidacionProyectoMgl>();
        }
        return null;
    }

    /**
     * Buscar y almacenar la lista de validaciones por proyecto. Busca y
     * almacenar la lista de validaciones por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param page Pagina actual.
     * @return null.
     */
    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            CmtBasicaMgl basicaProyecto = null;
            if (proyectoFiltroSelected != null
                    && !proyectoFiltroSelected.trim().isEmpty()) {
                basicaProyecto = new CmtBasicaMgl();
                basicaProyecto.setBasicaId(
                        new BigDecimal(proyectoFiltroSelected));
            }
            validacionProyectoFiltroList = validacionProyectoMglFacadeLocal.
                    findByFiltroPaginado(actual, filasPag, basicaProyecto);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Obtener el total de paginas según registros traidos. Obtiene el total de
     * paginas según registros traidos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el total de paginas o 1 por defecto.
     */
    public int getTotalPaginas() {
        try {
            CmtBasicaMgl basicaProyecto = null;
            if (proyectoFiltroSelected != null
                    && !proyectoFiltroSelected.trim().isEmpty()) {
                basicaProyecto = new CmtBasicaMgl();
                basicaProyecto.setBasicaId(
                        new BigDecimal(proyectoFiltroSelected));
            }
            int pageReglas = validacionProyectoMglFacadeLocal.
                    countByFiltroPaginado(basicaProyecto);
            int totalPaginas = (int) ((pageReglas % filasPag != 0)
                    ? (pageReglas / filasPag) + 1 : pageReglas / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Traer todas las validaciones. Trae todas las validaciones.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null
     */
    public String verTodas() {
        proyectoFiltroSelected = null;
        return listInfoByPage(1);
    }

    /**
     * Mover la tabla a la primera página. Mueve la tabla a la primera página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Mover la tabla a la anterior página. Mueve la tabla a la anterior página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
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
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Mover la tabla a una página determinada. Mueve la tabla a una página
     * determinada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
           FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: irPagina(). " + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validacionProyectoBean: irPagina(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Mover la tabla a una página posterior. Mueve la tabla a una página
     * posterior.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
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
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Mover la tabla a la última página. Mueve la tabla a la última página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtener la página actual. Obtiene la página actal.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La página actual.
     */
    public String getPageActual() {
        try {
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidacionProyectoBean: getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    /**
     * Habilitar el formulario de creación. Habilita el formulario de creación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null.
     */
    public String goCrear() {
        renderDetalle = false;
        renderCreacion = true;
        return null;
    }

    /**
     * Habilitar el formulario de actualización.Habilita el formulario de
 actualización.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param validacionSelected
     * @return null.
     */
    public String goActualizar(CmtValidacionProyectoMgl validacionSelected) {
        renderDetalle = true;
        renderCreacion = false;
        renderFiltro = false;
        proyectoDetalleSelected = "";
        validacionProyectoSelected = validacionSelected;
        return null;
    }

    /**
     * Eliminar la validación seleccionada. Eliminar la validación seleccionada
     * de la tabla de la página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param validacionSelected Validación seleccionada a eliminar.
     * @return null.
     */
    public String eliminarValidacionFromTable(CmtValidacionProyectoMgl validacionSelected) {

        try {
            validacionProyectoSelected = validacionSelected;
            return eliminarValidacion();

        } catch (Exception e) {
            LOGGER.error("Error al Eliminar la validación seleccionada de la tabla de la página . EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return null;
    }

    /**
     * Eliminar la validación de la aplicación. Elimina la validación de la
     * aplicación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return null.
     */
    public String eliminarValidacion() {
        try {
            validacionProyectoMglFacadeLocal.delete(
                    validacionProyectoSelected);
            renderDetalle = false;
            renderCreacion = false;
            renderFiltro = true;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));

            listInfoByPage(1);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidacionProyectoBean: eliminarValidacion(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * retornar el filtro tal como se encontraba antes. de ir al detalle. Se
     * retorna a la pantalla de filtro tal como se encontraba antes de ir al
     * detalle.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void volver() {
        this.renderDetalle = false;
        this.renderFiltro = true;
        listInfoByPage(actual);
    }

    /**
     * Actualizar la validación del proyecto seleccionada. Actualiza la
     * validación del proyecto seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void actualizar() {
        if (validacionProyectoSelected != null) {
            try {
                validacionProyectoMglFacadeLocal.update(validacionProyectoSelected);
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        ConstantsCM.MSN_PROCESO_EXITOSO, ""));
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError(ConstantsCM.MSN_PROCESO_FALLO + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error al ValidacionProyectoBean: actualizar() " + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION);
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidacionProyectoBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Obtener true o false para mostrar los componentes del detalle. Obtiene
     * true o false para mostrar los componentes del detalle.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para renderizar el formulario de detalle.
     */
    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    /**
     * Cambiar true o false para mostrar los componentes del detalle. cambia
     * true o false para mostrar los componentes del detalle.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param renderDetalle True o false para renderizar el formulario de
     * detalle.
     */
    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    /**
     * Obtener la lista de proyectos. Obtiene la lista de proyectos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return Lista de proyectos.
     */
    public List<CmtBasicaMgl> getBasicaProyectoList() {
        return basicaProyectoList;
    }

    /**
     * Cambiar la lista de proyectos. Cambia la lista de proyectos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param basicaProyectoList
     */
    public void setBasicaProyectoList(List<CmtBasicaMgl> basicaProyectoList) {
        this.basicaProyectoList = basicaProyectoList;
    }

    /**
     * Obtener la lista de tipos de validación. Obtiene la lista de tipos de
     * validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de tipos de validación.
     */
    public List<CmtTipoValidacionMgl> getTipoValidacionList() {
        return tipoValidacionList;
    }

    /**
     * Cambiar la lista de tipos de validación. Cambia la lista de tipos de
     * validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param tipoValidacionList La lista de tipos de validación.
     */
    public void setTipoValidacionList(List<CmtTipoValidacionMgl> tipoValidacionList) {
        this.tipoValidacionList = tipoValidacionList;
    }

    /**
     * Obtener el proyecto seleccionado. Obtiene el proyecto seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return El proyecto seleccionado.
     */
    public String getProyectoFiltroSelected() {
        return proyectoFiltroSelected;
    }

    /**
     * Cambiar el proyecto seleccionado. Cambia el proyecto seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param proyectoFiltroSelected El proyecto seleccionado.
     */
    public void setProyectoFiltroSelected(String proyectoFiltroSelected) {
        this.proyectoFiltroSelected = proyectoFiltroSelected;
    }

    /**
     * Obtener la validación seleccionada. Obtiene la validación seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La validación seleccionada.
     */
    public String getTipoValFiltroSelected() {
        return tipoValFiltroSelected;
    }

    /**
     * Cambiar la validación seleccionada. Cambia la validación seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param tipoValFiltroSelected La validación seleccionada.
     */
    public void setTipoValFiltroSelected(String tipoValFiltroSelected) {
        this.tipoValFiltroSelected = tipoValFiltroSelected;
    }

    /**
     * Obtener true o false para renderizar el formulario de creación. Obtiene
     * true o false para renderizar el formulario de creación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para renderizar el formulario de creación.
     */
    public boolean isRenderCreacion() {
        return renderCreacion;
    }

    /**
     * Cambiar true o false para renderizar el formulario de creación. Cambia
     * true o false para renderizar el formulario de creación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param renderCreacion true o false para renderizar el formulario de
     * creación.
     */
    public void setRenderCreacion(boolean renderCreacion) {
        this.renderCreacion = renderCreacion;
    }

    /**
     * Obtener el detalle del proyecto seleccionado. Obtiene el detalle del
     * proyecto seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return El detalle del proyecto seleccionado.
     */
    public String getProyectoDetalleSelected() {
        return proyectoDetalleSelected;
    }

    /**
     * Cambiar el detalle del proyecto seleccionado. Cambia el detalle del
     * proyecto seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param proyectoDetalleSelected El detalle del proyecto seleccionado.
     */
    public void setProyectoDetalleSelected(String proyectoDetalleSelected) {
        this.proyectoDetalleSelected = proyectoDetalleSelected;
    }

    /**
     * Obtener la lista de validaciones por proyecto. Obtiene la lista de
     * validaciones por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de validaciones por proyecto.
     */
    public List<CmtValidacionProyectoMgl> getValidacionProyectoCreacionList() {
        return validacionProyectoCreacionList;
    }

    /**
     * Cambiar la lista de validaciones por proyecto. Cambia la lista de
     * validaciones por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param validacionProyectoCreacionList la lista de validaciones por
     * proyecto.
     */
    public void setValidacionProyectoCreacionList(
            List<CmtValidacionProyectoMgl> validacionProyectoCreacionList) {
        this.validacionProyectoCreacionList = validacionProyectoCreacionList;
    }

    /**
     * Obtener el número de página. Obtiene el número de página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return El número de página.
     */
    public String getNumPagina() {
        return numPagina;
    }

    /**
     * Cambiar el número de página. Cambia el número de página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param numPagina El número de página.
     */
    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    /**
     * Obtener la lista de páginas. Obtiene la lista de páginas.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de páginas.
     */
    public List<Integer> getPaginaList() {
        return paginaList;
    }

    /**
     * Cambiar la lista de páginas. Cambia la lista de páginas.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param paginaList La lista de páginas.
     */
    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    /**
     * Obtener la página actual. Obtiene la página actual.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La página actual.
     */
    public int getActual() {
        return actual;
    }

    /**
     * Cambiar la página actual. Cambia la página actual.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param actual La página actual.
     */
    public void setActual(int actual) {
        this.actual = actual;
    }

    /**
     * Obtener filas de la página. Obtiene filas de la página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return Filas de la página.
     */
    public int getFilasPag() {
        return filasPag;
    }

    /**
     * Cambiar filas de la página. Cambia filas de la página.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param filasPag Filas de la página.
     */
    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    /**
     * Obtener la lista de validacion por proyecto. Obtiene la lista de
     * validacion por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return la lista de validacion por proyecto.
     */
    public List<CmtValidacionProyectoMgl> getValidacionProyectoFiltroList() {
        return validacionProyectoFiltroList;
    }

    /**
     * Cambiar la lista de validacion por proyecto. Cambia la lista de
     * validacion por proyecto.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param validacionProyectoFiltroList La lista de validacion por proyecto.
     */
    public void setValidacionProyectoFiltroList(
            List<CmtValidacionProyectoMgl> validacionProyectoFiltroList) {
        this.validacionProyectoFiltroList = validacionProyectoFiltroList;
    }

    /**
     * Obtener validacion del proyecto seleccionada. Obtiene validacion del
     * proyecto seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return La validacion del proyecto seleccionada.
     */
    public CmtValidacionProyectoMgl getValidacionProyectoSelected() {
        return validacionProyectoSelected;
    }

    /**
     * Cambiar validacion del proyecto seleccionada. Cambia validacion del
     * proyecto seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param validacionProyectoSelected La validacion del proyecto
     * seleccionada.
     */
    public void setValidacionProyectoSelected(
            CmtValidacionProyectoMgl validacionProyectoSelected) {
        this.validacionProyectoSelected = validacionProyectoSelected;
    }

    /**
     * Obtener el estado de renderización del panel de filtro en la página.
     * Obtiene el estado de renderización del panel de filtro en la página.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True si se renderiza el panel de filtro, de lo contrario false.
     */
    public Boolean getRenderFiltro() {
        return renderFiltro;
    }

    /**
     * Cambiar el estado de renderización del panel de filtro en la página.
     * Cambia el estado de renderización del panel de filtro en la página.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param renderFiltro True si se renderiza el panel de filtro, de lo
     * contrario false.
     */
    public void setRenderFiltro(Boolean renderFiltro) {
        this.renderFiltro = renderFiltro;
    }

    /**
     * Obtener mensaje cuando el campo se encuentre vacío. Obtiene mensaje
     * cuando el campo se encuentre vacío.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Mensaje cuando el campo se encuentra vacío.
     */
    public String getCampoVacio() {
        return campoVacio;
    }

    /**
     * Cambiar mensaje cuando el campo se encuentre vacío. Cambia mensaje cuando
     * el campo se encuentre vacío.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param campoVacio Mensaje cuando el campo se encuentre vacío.
     */
    public void setCampoVacio(String campoVacio) {
        this.campoVacio = campoVacio;
    }
    
    public boolean validarOpcionCreaConf() {
        return validarEdicionRol(BTCRACONF);
    }
    
    public boolean validarOpcionCreaNuevaConfRol() {
        return validarEdicionRol(ROLOPCCONFGVALPROY);
    }
    
    public boolean validarOpcionReglaIdRol() {
        return validarEdicionRol(ROLOPCIDVALPROY);
    }
    
    public boolean validarOpcionEliminarRol() {
        return validarEdicionRol(ROLOPCDELVALPROY);
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
