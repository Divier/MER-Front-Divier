package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtReglaValidacionFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoValidacionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
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
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Bean respaldo a pantallas para administracion de la tabla
 * {@link CmtTipoValidacionMgl}.
 * 
* @author ortizjaf
 * @versión 1.0 revisión 15/05/2017
 */
@ManagedBean(name = "tipoValidacionBean")
@ViewScoped
public class TipoValidacionBean implements Serializable {

    /**
     * Injeccion de la interfaz que conecta la lógica de negocio con la vista de
     * tipo de validación.
     */
    @EJB
    private CmtTipoValidacionMglFacadeLocal cmtTipoValidacionMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    /**
     * Usuario de la sesión.
     */
    private String usuarioVT = null;
    /**
     * Perfil del usuario activo en la sesión.
     */
    private int perfilVT = 0;
    /**
     * Objeto de la entidad de la tabla basicas.
     */
    private CmtBasicaMgl TablasBasicasMgl = null;
    /**
     * Objeto de la entidad de la tabla tipo validación.
     */
    private CmtTipoValidacionMgl tipoValidacionMgl = null;
    /**
     * Lista de la entidad tablasBasicas.
     */
    private List<CmtBasicaMgl> tablasBasicasMglList;
    /**
     * Listado de los tipos de validacion.
     */
    private List<CmtTipoValidacionMgl> tipoValidacionMglList;
    /**
     * Lista de la entidad que representa la tabla tipo básica.
     */
    private List<CmtTipoBasicaMgl> tablasTipoBasicasMglList;
    /**
     * Id del sql seleccionado.
     */
    private String idSqlSelected;
    /**
     * Filtro seleccionado que contiene el valor seleccionado de la tabla
     * básica.
     */
    private String filtroTablaBasicaSelected;
    private String codigo;
    private String tablaTipoBasicaMgl;
    /**
     * True o false si el registro tipo de validación fue guardado.
     */
    private boolean guardado;
    /**
     * True o False para mostrar el detalle de creación de tipos de validación.
     */
    private boolean crearRegistro = false;
    private String certificado;
    /**
     * Identificador del registro de la tabla básica.
     */
    public BigDecimal idTablasBasicas;
    private boolean otroEstado;
    private List<String> brList;
    /**
     * Contiene la página actual del paginador.
     */
    private String pageActual;
    /**
     * Contiene el número de pagina inicial del paginador.
     */
    private String numPagina = "1";
    /**
     * Contiene la lista de páginas disponibles para una consulta específica.
     */
    private List<Integer> paginaList;
    /**
     * Contexto jsf.
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * Almacena la sesión.
     */
    private HttpSession session = (HttpSession) facesContext.getExternalContext()
            .getSession(false);
    /**
     * Maneja los mensajes de los tipos de validación.
     */
    private String message;
    /**
     * Maneja la respues por http.
     */
    private HttpServletResponse response = (HttpServletResponse) facesContext
            .getExternalContext().getResponse();
    /**
     * Constante para el manejo de log de la aplicación.
     */
    private static final Logger LOGGER = LogManager.getLogger(TipoValidacionBean.class);
    /**
     * Contiene el valor seleccionado de tipo básica.
     */
    private CmtTipoBasicaMgl cmtTipoBasicaMglSelected;
    /**
     * Injección de la interfaz tipo de validación.
     */
    @EJB
    private CmtTipoValidacionMglFacadeLocal cmtTipoValidacionMglFacade;
    /**
     * Injección interfaz tipo básica.
     */
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    /**
     * Injección interfaz regla de validación.
     */
    @EJB
    private CmtReglaValidacionFacadeLocal cmtReglaValidacionFacadeLocal;
    private String inputMetodo;
    private String inputClase;
    private int selectIdBasica;
    private String descripcionBasica = "";
    private String tipoBasicaDescripcion = "";
    /**
     * Tag utilizado para cambiar el color de la fuente.
     */
    private String estiloObligatorio = "<font color='red'>*</font>";
    /**
     * Dto de la consulta básica.
     */
    private FiltroConjsultaBasicasDto filtroConjsultaBasicasDto =
            new FiltroConjsultaBasicasDto();
    /**
     * Parametros para realizar filtros de consultas en los tipos de validación.
     */
    private Hashtable<String, String> parametros;
    /**
     * Contiene el tipo de validación seleccionado.
     */
    private BigDecimal seleccionTipoValidacion;
    /**
     * Contiene el el proyecto seleccionado.
     */
    private BigDecimal seleccionProyecto;
    /**
     * Contiene el número de la página actual.
     */
    private Integer paginaActual;
    /**
     * Objeto que representa la entidad tipo de validación para almacenar los
     * mensajes.
     */
    private List<CmtTipoValidacionMgl> mensajesTipoValidacion;
    /**
     * Total de páginas del paginador.
     */
    private Integer totalPaginas;
    /**
     * True o false si se llegó a la ultima página del paginador.
     */
    private Boolean ultimaPagina;
    /**
     * Almacena la constante para mover la pagina a la siguiente.
     */
    private String siguiente = MensajeTipoValidacion.SIGUIENTE.getValor();
    /**
     * Almacena la constante para mover la pagina atrás.
     */
    private String atras = MensajeTipoValidacion.ATRAS.getValor();
    /**
     * Almacena la constante para mover la pagina a la primera.
     */
    private String primera = MensajeTipoValidacion.PRIMERA.getValor();
    /**
     * Almacena la constante para mover la pagina a la última.
     */
    private String ultima = MensajeTipoValidacion.ULTIMA.getValor();
    /**
     * Almacena la constante para mover la pagina a una página determinada.
     */
    private String irA = MensajeTipoValidacion.IR_A_PAGINA.getValor();

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private final String FORMULARIO = "TIPOVALIDACIONLISTVIEW";
    //Opciones agregadas para Rol
    private final String BTNCRAVAL = "BTNCRAVAL";
    private final String ROLOPCDELTIPVAL = "ROLOPCDELTIPVAL";
    private SecurityLogin securityLogin;

    /**
     * Construir del Bean, sin argumentos. Constructor del Bean, sin argumentos,
     * y obtener el usuario y su perfil.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @construct
     */
    public TipoValidacionBean() {

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
            FacesUtil.mostrarMensajeError("Error al TipoValidacionBean. " + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoValidacionBean. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Inicia los atributos de tipo de validación. Metodo de ejecucion posterior
     * al constructor para inicializacion de logica posterior al arranque del
     * Bean.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     */
    @PostConstruct
    public void fillSqlList() {

        try {
            crearRegistroRender();
            this.paginaActual = 1;
            this.ultimaPagina = false;
            actualizarTablaMensajes(primera);
            tablasTipoBasicasMglList = new ArrayList<CmtTipoBasicaMgl>();
            tablasTipoBasicasMglList = cmtTipoBasicaMglFacade.findAll();


            cmtTipoValidacionMglFacade.setUser(usuarioVT, perfilVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al TipoValidacionBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoValidacionBean. " + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * Crear el tipo de validación. Reraliza la captura de los datos, para
     * persistirlo en la entidad, previa validadaciones del negocio Valida que
     * no exista el idTipoBasica para poder insertar de lo contrario devuelve
     * mensaje, en caso exitoso retorna mensaje a la vista.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     */
    public void crearTipoValidacionMgl() {
        try {
            String msn;
            Severity tipoSeveridad = FacesMessage.SEVERITY_ERROR;

            if (tipoValidacionMgl.getTipoBasicaObj().getTipoBasicaId() != null) {
                if (!cmtTipoValidacionMglFacade.existsTipoBasica(
                        tipoValidacionMgl.getTipoBasicaObj())) {
                    cmtTipoValidacionMglFacade.create(tipoValidacionMgl);
                    setGuardado(false);
                    msn = Constant.MSN_PROCESO_EXITOSO;
                    tipoSeveridad = FacesMessage.SEVERITY_INFO;
                    actualizarTablaMensajes(primera);
                    crearRegistroRender();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(tipoSeveridad, msn, ""));
                } else {
                    msn = "La validacion ya ha sido agregada";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(tipoSeveridad, msn, ""));
                }

            } else {
                msn = Constant.MSN_TIPO_VALIDACION_REQUERIDO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(tipoSeveridad, msn, ""));
            }
        } catch (ApplicationException e) {
            setGuardado(false);
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: crearTipoValidacionMgl(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Actualizar los tipo de validación.Metodo para realizar la accion U del
 CRUD, actualizaicon de los campos definidos para edicion por el negocio
 No parametros.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return 
     */
    public String actualizarTipoValidacionMgl() {
        try {

            cmtTipoValidacionMglFacade.update(tipoValidacionMgl);
            crearRegistro = false;
            actualizarTablaMensajes(primera);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
           FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: actualizarTipoValidacionMgl(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: actualizarTipoValidacionMgl(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Borrar los tipos de validación.Metodo que realiza la accion D del CRUD
 eliminar de manera logica los elementos de la entidad tipoValidacionMgl.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValSelected Tipo de validación seleccionado.
     * @return 
     */
    public String eliminarTipoValFromTable(CmtTipoValidacionMgl tipoValSelected) {
        try {

            if (tipoValSelected != null) {

                if (!validarUsoTipoValidacion(tipoValSelected)) {

                    cmtTipoValidacionMglFacade.delete(tipoValSelected);
                    actualizarTablaMensajes(primera);
                    tipoValidacionMglList = cmtTipoValidacionMglFacadeLocal
                            .findAll();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            Constant.MSN_PROCESO_EXITOSO, ""));
                    return null;
                } else {
                    String msn = "NO es posible eliminar el resgitro. El tipo de"
                            + " validación que desea eliminar se encuentra"
                            + " en uso";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msn, ""));

                }
            } else {
                String msn = "Se presento un error al intentar eliminar el"
                        + " registro seleccionado";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: eliminarTipoValFromTable(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Validar el uso del tipo de validación. Valida el uso del tipo de
     * validación para evitar redundancia.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValSelected Tipo de validación a validar y seleccionada por el
     * usuario.
     * @return True si el tipo de validación está en uso de los contrario false.
     */
    public boolean validarUsoTipoValidacion(CmtTipoValidacionMgl tipoValSelected) {
        try {

            boolean countTipoValidacion = true;
            return countTipoValidacion = cmtTipoValidacionMglFacade
                    .findTipoValidacionEnUso(tipoValSelected.getIdTipoValidacion());

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            return true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: validarUsoTipoValidacion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Eliminar el tipo de validación.Metodo que realiza la accion D del CRUD
 eliminar de manera logica los elementos de la entidad tipoValidacionMgl.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return 
     */
    public String eliminarTipoValidacionMgl() {
        try {
            cmtTipoValidacionMglFacade.delete(tipoValidacionMgl);
            crearRegistro = false;
            actualizarTablaMensajes(primera);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean:  eliminarTipoValidacionMgl(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Navegar a la pagina tipoValidacionListView. Metodo de ayuda a la vista
     * retorna a la vista general usese cuando requiere navegar entre diferentes
     * vistas.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return Página tipoValidacionListView.xhtml para navegar hacia ella.
     */
    public String volverList() {
        return "tipoValidacionListView";
    }

    /**
     * Renderizar la pantalla de creación. Realiza la vista de los datos que
     * deben quedar en los input para la edicion.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param registroDetalle Tipo de validación seleccionado.
     * @return null.
     */
    public String goActualizar(CmtTipoValidacionMgl registroDetalle) {
        crearRegistro = true;
        tipoValidacionMgl = registroDetalle;
        return null;
    }

    /**
     * Definir variales para la creación de registros. Creacion de registro los
     * valores a los atributos del bean que muestran u ocultan contenido en la
     * vista.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     */
    public void crearRegistroRender() {
        tipoValidacionMgl = new CmtTipoValidacionMgl();
        cmtTipoBasicaMglSelected = new CmtTipoBasicaMgl();
        cmtTipoBasicaMglSelected.setNombreTipo("");
        tipoValidacionMgl.setTipoBasicaObj(cmtTipoBasicaMglSelected);
    }

    /**
     * Actualizar el Table de los registros mediante mandos segun paginador.
     * Actualiza el Table de los registros mediante mandos segun paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param movimiento dirección del desplazamiento del paginador
     */
    public void actualizarTablaMensajes(String movimiento) {
        parametros = new Hashtable<String, String>();

        parametros.put("pagina", this.paginaActual.toString());
        parametros.put("movimiento", movimiento);
        parametros.put("estadoRegistro", "1");

        try {
            tipoValidacionMglList = cmtTipoValidacionMglFacadeLocal
                    .findMensajesPorFiltros(parametros);
            this.totalPaginas = cmtTipoValidacionMglFacadeLocal
                    .obtenerCantidadPaginas(parametros);
            ultimaPagina = new Boolean(parametros.get("ultimaPagina"));
            paginaActual = new Integer(parametros.get("pagina"));
            this.totalPaginas = new Integer(parametros.get("totalPaginas"));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: actualizarTablaMensajes(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: actualizarTablaMensajes(). " + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * 
     * @return 
     */
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO);
    }

    /**
     * 
     * @return 
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     * 
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @return boolean indicador para verificar si se visualizan o no los componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TipoValidacionBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * retornar la ultimaPagina para el elemento Paginador retorna la
     * ultimaPagina para el elemento Paginador
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return True si el paginador se encuentra en la última página de los
     * contrario false.
     */
    public Boolean getUltimaPagina() {
        return ultimaPagina;
    }

    /**
     * set del valor de la UltimaPagina para el paginador. set del valor de la
     * UltimaPagina para el paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param ultimaPagina true si es la última página del paginador de los
     * contrario false.
     */
    public void setUltimaPagina(Boolean ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    /**
     * retorna totalPaginas paginador. retorna totalPaginas paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return TotalPaginas paginador.
     */
    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * get del totalPaginas Paginador. get del totalPaginas Paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param totalPaginas Total de páginas Paginador.
     */
    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * Retornar paginaActual Int Retorna paginaActual Int
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La página actual.
     */
    public Integer getPaginaActual() {
        return paginaActual;
    }

    /**
     * Set la paginaActual Int Set la paginaActual Int
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param paginaActual La página actual.
     */
    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }

    /**
     * Obtener el item seleccionado del combo de tipos de validación. Obtiene el
     * item seleccionado del combo de tipos de validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El item seleccionado del combo de tipos de validación.
     */
    public BigDecimal getSeleccionTipoValidacion() {
        return seleccionTipoValidacion;
    }

    /**
     * Cambiar el item seleccionado del combo de tipos de validación. Cambia el
     * item seleccionado del combo de tipos de validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionSeleccion El item seleccionado del combo de tipos de
     * validación.
     */
    public void setSeleccionTipoValidacion(BigDecimal tipoValidacionSeleccion) {
        this.seleccionTipoValidacion = tipoValidacionSeleccion;
    }

    /**
     * retornar la instancia del facadeLocal. retorna la instancia del
     * facadeLocal.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La instancia del facadeLocal.
     */
    public CmtTipoValidacionMglFacadeLocal getCmtTipoValidacionMglFacadeLocal() {
        return cmtTipoValidacionMglFacadeLocal;
    }

    /**
     * setea el valor del atributo cmtTipoValidacionMglFacadeLocal. setea el
     * valor del atributo cmtTipoValidacionMglFacadeLocal.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param cmtTipoValidacionMglFacadeLocal El valor del atributo
     * cmtTipoValidacionMglFacadeLocal.
     */
    public void setCmtTipoValidacionMglFacadeLocal(
            CmtTipoValidacionMglFacadeLocal cmtTipoValidacionMglFacadeLocal) {
        this.cmtTipoValidacionMglFacadeLocal = cmtTipoValidacionMglFacadeLocal;
    }

    /**
     * Obtener el usuario de sesión. Obtiene el usuario de sesión.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El usuario de sesión.
     */
    public String getUsuarioVT() {
        return usuarioVT;
    }

    /**
     * Cambiar el usuario de sesión. Cambia el usuario de sesión.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param usuarioVT El usuario de sesión.
     */
    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    /**
     * Obtener el perfil del usuario. Obtiene el perfil del usuario.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El perfil del usuario.
     */
    public int getPerfilVT() {
        return perfilVT;
    }

    /**
     * /**
     * Cambiar el perfil del usuario. Cambia el perfil del usuario.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param perfilVT El perfil del usuario.
     */
    public void setPerfilVT(int perfilVT) {
        this.perfilVT = perfilVT;
    }

    /**
     * Obtener el tipo de validación. Obtiene el tipo de validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El tipo de validación.
     */
    public CmtTipoValidacionMgl getTipoValidacionMgl() {
        return tipoValidacionMgl;
    }

    /**
     * Cambiar el tipo de validación. Cambia el tipo de validación.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionMgl El tipo de validación.
     */
    public void setTipoValidacionMgl(CmtTipoValidacionMgl tipoValidacionMgl) {
        this.tipoValidacionMgl = tipoValidacionMgl;
    }

    /**
     * Cambiar true o false de crear registro. Cambia true o false de crear
     * registro.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param crearRegistro True o false de crear registro.
     */
    public void setCrearRegistro(boolean crearRegistro) {
        this.crearRegistro = crearRegistro;
    }

    /**
     * Obtener la lista de basicas. Obtiene la lista de basicas.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La lista de basicas.
     */
    public List<CmtBasicaMgl> getTablasBasicasMglList() {
        return tablasBasicasMglList;
    }

    /**
     * Obtener true o false de crear registro. Obtiene true o false de crear
     * registro.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return True o false de crear registro.
     */
    public boolean isCrearRegistro() {
        return crearRegistro;
    }

    /**
     * Cambiar la lista de basicas. Cambia la lista de basicas.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tablasBasicasMglList La lista de basicas.
     */
    public void setTablasBasicasMglList(List<CmtBasicaMgl> tablasBasicasMglList) {
        this.tablasBasicasMglList = tablasBasicasMglList;
    }

    /**
     * Obtener la lista tipo básica. Obtiene la lista tipo básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La lista tipo básica.
     */
    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return tablasTipoBasicasMglList;
    }

    /**
     * Cambiar la lista tipo básica. Cambia la lista tipo básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param TablasTipoBasicasMglList La lista tipo básica.
     */
    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> TablasTipoBasicasMglList) {
        this.tablasTipoBasicasMglList = TablasTipoBasicasMglList;
    }

    /**
     * Obtener el id del sql seleccionado. Obtiene el id del sql seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return el id del sql seleccionado.
     */
    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    /**
     * Cambiar el id del sql seleccionado. Cambia el id del sql seleccionado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param idSqlSelected
     */
    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    /**
     * Obtener el valor de la basica seleccionada. Obtiene el valor de la basica
     * seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El valor de la basica seleccionada.
     */
    public String getFiltroTablaBasicaSelected() {
        return filtroTablaBasicaSelected;
    }

    /**
     * Cambiar el valor de la basica seleccionada. Cambia el valor de la basica
     * seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param filtroTablaBasicaSelected El valor de la basica seleccionada.
     */
    public void setFiltroTablaBasicaSelected(String filtroTablaBasicaSelected) {
        this.filtroTablaBasicaSelected = filtroTablaBasicaSelected;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTablaTipoBasicaMgl() {
        return tablaTipoBasicaMgl;
    }

    public void setTablaTipoBasicaMgl(String tablaTipoBasicaMgl) {
        this.tablaTipoBasicaMgl = tablaTipoBasicaMgl;
    }

    /**
     * Obtener true o false si el registro tipo de validación fue guardado.
     * Obtiene true o false si el registro tipo de validación fue guardado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return True o false si el registro tipo de validación fue guardado.
     */
    public boolean isGuardado() {
        return guardado;
    }

    /**
     * Cambiar true o false si el registro tipo de validación fue guardado.
     * Cambia true o false si el registro tipo de validación fue guardado.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param guardado True o false si el registro tipo de validación fue
     * guardado.
     */
    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    /**
     * Obtener el identificador del registro de la tabla básica. Obtiene el
     * identificador del registro de la tabla básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return el identificador del registro de la tabla básica.
     */
    public BigDecimal getIdTablasBasicas() {
        return idTablasBasicas;
    }

    /**
     * Cambiar el identificador del registro de la tabla básica. Cambia el
     * identificador del registro de la tabla básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param idTablasBasicas El identificador del registro de la tabla básica.
     */
    public void setIdTablasBasicas(BigDecimal idTablasBasicas) {
        this.idTablasBasicas = idTablasBasicas;
    }

    public boolean isOtroEstado() {
        return otroEstado;
    }

    public void setOtroEstado(boolean otroEstado) {
        this.otroEstado = otroEstado;
    }

    public List<String> getBrList() {
        return brList;
    }

    public String getInputMetodo() {
        return inputMetodo;
    }

    public String getInputClase() {
        return inputClase;
    }

    public void setSelectIdBasica(int selectIdBasica) {
        this.selectIdBasica = selectIdBasica;
    }

    public int getSelectIdBasica() {
        return selectIdBasica;
    }

    public void setInputMetodo(String inputMetodo) {
        this.inputMetodo = inputMetodo;
    }

    public void setInputClase(String inputClase) {
        this.inputClase = inputClase;
    }

    public void setBrList(List<String> brList) {
        this.brList = brList;
    }

    /**
     * Cambiar la pagina actual del paginador. Cambia la pagina actual del
     * paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param pageActual La pagina actual del paginador.
     */
    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    /**
     * Obtener el número de pagina inicial del paginador. Obtiene el número de
     * pagina inicial del paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El número de pagina inicial del paginador.
     */
    public String getNumPagina() {
        return numPagina;
    }

    /**
     * Cambiar el número de pagina inicial del paginador. Cambia el número de
     * pagina inicial del paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param numPagina El número de pagina inicial del paginador.
     */
    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    /**
     * Obtener la lista de paginas actual del paginador. Obtiene la lista de
     * paginas actual del paginador.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La lista de paginas actual del paginador.
     */
    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    /**
     * Obtener el contexto jsf. Obtiene el contexto jsf.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El contexto jsf.
     */
    public FacesContext getFacesContext() {
        return facesContext;
    }

    /**
     * Cambiar el contexto jsf. Cambia el contexto jsf.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param facesContext El contexto jsf.
     */
    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    /**
     * Obtener el mensaje. Obtiene el mensaje.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El mensaje.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Cambiar el mensaje. Cambia el mensaje.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param message El mensaje.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtener la respues por http. Obtiene la respues por http.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La respues por http.
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Obtener la respues por http. Obtiene la respues por http.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param response La respues por http.
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Obtener el tipo de básica seleccionada. Obtiene el tipo de básica
     * seleccionada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El tipo de básica seleccionada.
     */
    public CmtTipoBasicaMgl getCmtTipoBasicaMglSelected() {
        return cmtTipoBasicaMglSelected;
    }

    /**
     * Cambiar Tipo de básica seleccinada. Cambia Tipo de básica seleccinada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param cmtTipoBasicaMglSelected El tipo de básica seleccinada.
     */
    public void setCmtTipoBasicaMglSelected(
            CmtTipoBasicaMgl cmtTipoBasicaMglSelected) {
        this.cmtTipoBasicaMglSelected = cmtTipoBasicaMglSelected;
    }

    /**
     * Obtener la interfaz de tipo básica. Obtiene la interfaz de tipo básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La interfaz de tipo básica.
     */
    public CmtTipoBasicaMglFacadeLocal getCmtTipoBasicaMglFacade() {
        return cmtTipoBasicaMglFacade;
    }

    /**
     * Cambiar la interfaz de tipo básica. Cambia la interfaz de tipo básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param cmtTipoBasicaMglFacade La interfaz de tipo básica.
     */
    public void setCmtTipoBasicaMglFacade(
            CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade) {
        this.cmtTipoBasicaMglFacade = cmtTipoBasicaMglFacade;
    }

    public String getDescripcionBasica() {
        return descripcionBasica;
    }

    public void setDescripcionBasica(String descripcionBasica) {
        this.descripcionBasica = descripcionBasica;
    }

    public String getTipoBasicaDescripcion() {
        return tipoBasicaDescripcion;
    }

    public void setTipoBasicaDescripcion(String tipoBasicaDescripcion) {
        this.tipoBasicaDescripcion = tipoBasicaDescripcion;
    }

    /**
     * Obtener el tag utilizado para cambiar el color de la fuente. Obtiene el
     * tag utilizado para cambiar el color de la fuente.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El tag utilizado para cambiar el color de la fuente.
     */
    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    /**
     * Cambiar el tag utilizado para cambiar el color de la fuente. Cambia el
     * tag utilizado para cambiar el color de la fuente.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param estiloObligatorio
     */
    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    /**
     * Obtener dto de la consulta básica. Obtiene dto de la consulta básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El dto de la consulta básica.
     */
    public FiltroConjsultaBasicasDto getFiltroConjsultaBasicasDto() {
        return filtroConjsultaBasicasDto;
    }

    /**
     * Cambiar dto de la consulta básica. Cambia dto de la consulta básica.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param filtroConjsultaBasicasDto El dto de la consulta básica.
     */
    public void setFiltroConjsultaBasicasDto(
            FiltroConjsultaBasicasDto filtroConjsultaBasicasDto) {
        this.filtroConjsultaBasicasDto = filtroConjsultaBasicasDto;
    }

    /**
     * Obtener el listado de los tipos de validacion. Obtiene el listado de los
     * tipos de validacion.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return El listado de los tipos de validacion.
     */
    public List<CmtTipoValidacionMgl> getTipoValidacionMglList() {
        return tipoValidacionMglList;
    }

    /**
     * Cambiar el listado de los tipos de validacion. Cambia el listado de los
     * tipos de validacion.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionMglList El listado de los tipos de validacion.
     */
    public void setTipoValidacionMglList(
            List<CmtTipoValidacionMgl> tipoValidacionMglList) {
        this.tipoValidacionMglList = tipoValidacionMglList;
    }

    /**
     * Obtener la constante para mover la pagina a la siguiente. Obtiene la
     * constante para mover la pagina a la siguiente.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La constante para mover la pagina a la siguiente.
     */
    public String getSiguiente() {
        return siguiente;
    }

    /**
     * Obtener la constante para mover la pagina atrás. Obtiene la constante
     * para mover la pagina atrás.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La constante para mover la pagina a la siguiente.
     */
    public String getAtras() {
        return atras;
    }

    /**
     * Obtener la constante para mover la pagina a la primera. Obtiene la
     * constante para mover la pagina a la primera.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La constante para mover la pagina a la primera.
     */
    public String getPrimera() {
        return primera;
    }

    /**
     * Obtener la constante para mover la pagina a la última. Obtiene la
     * constante para mover la pagina a la última.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La constante para mover la pagina a la última.
     */
    public String getUltima() {
        return ultima;
    }

    /**
     * Obtener la constante para mover la pagina a una página determinada.
     * Obtiene la constante para mover la pagina a una página determinada.
     *
     * @author ortizjaf.
     * @version 1.0 revision 15/05/2017.
     * @return La constante para mover la pagina a una página determinada.
     */
    public String getIrA() {
        return irA;
    }
    


       // Validar Opciones por Rol
    
    public boolean validarOpcionCrearTabla() {
        return validarEdicionRol(BTNCRAVAL);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(ROLOPCDELTIPVAL);
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
