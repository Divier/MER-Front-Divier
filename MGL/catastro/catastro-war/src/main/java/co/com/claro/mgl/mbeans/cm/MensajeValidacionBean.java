package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoValidacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.ICmtMensajeTipoValidacionFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtMensajeTipoValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * bean de vista que adminsitra la vista o página de validación de mensajes.
 * Contiene todo el control de acciones de la página de validación de mensajes.
 *
 * @author Ricardo Cortés Rodríguez.
 * @version 1.0 revision 11/05/2017
 */
@ManagedBean(name = "mensajeValidacionBean")
@ViewScoped
public class MensajeValidacionBean {

    /**
     * Injeccion de interfaz de mensajes de validación.
     */
    @EJB
    private ICmtMensajeTipoValidacionFacadeLocal iMensaje;
    /**
     * Injeccion de interfaz de tipos de validación.
     */
    @EJB
    private CmtTipoValidacionMglFacadeLocal tipoValidacionMglFacadeLocal;
    /**
     * Injeccion de interfaz de basicas.
     */
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    /**
     * Objeto de entidad.
     */
    private CmtMensajeTipoValidacion mensajeTipVal;
    /**
     * Identificador del mensaje de validación.
     */
    private BigDecimal idMensaje;
    /**
     * Identificador del tipo de validación
     */
    private BigDecimal idValidacion;
    /**
     * Identificador del proyecto.
     */
    private BigDecimal idProyecto;
    /**
     * Representa los mensajes para si el proceso es satisfactorio.
     */
    private String mensajeSi;
    /**
     * Representa los mensajes cuando el proceso no es satisfactorio.
     */
    private String mensajeNo;
    /**
     * Representa los mensajes a un proceso/s
     */
    private String mensajeProceso;
    /**
     * Representa los mensajes en que la validación no aplica.
     */
    private String mensajeNa;
    /**
     * Mensaje global si.
     */
    private String mensajeSiGlobal;
    /**
     * Mensaje global no.
     */
    private String mensajeNoGlobal;
    /**
     * Mensaje global de proceso.
     */
    private String mensajeProcesoGlobal;
    /**
     * Mensaje global na.
     */
    private String mensajeNaGlobal;
    /**
     * Mensaje global restringido.
     */
    private String mensajeRestringidoGlobal;
    /**
     * Estado de los mensajes.
     */
    private Integer estado;
    /**
     * Lista de los mensajes mostrados en la consulta.
     */
    private List<CmtMensajeTipoValidacion> mensajesValidacion;
    /**
     * LIsta de los mensajes mostrados para la creación.
     */
    private List<CmtMensajeTipoValidacion> mensajesValidacionCreacion;
    /**
     * Lista de tipos de validación para asociar los mensajes.
     */
    private List<CmtTipoValidacionMgl> tiposValidacion;
    /**
     * Valores de tipo de validación seleccionada.
     */
    private List<CmtBasicaMgl> valorValidacionList;
    /**
     * Basicas por tipo de validación.
     */
    private List<CmtBasicaMgl> basicasXTV;
    /**
     * Habilita los campos.
     */
    private Boolean habilitarCampos;
    /**
     * Habilita los botones
     */
    private Boolean habilitarBoton;
    /**
     * Alamacena el tipo de validación seleccionado en la página.
     */
    private BigDecimal seleccionTipoValidacion;
    /**
     * Contiene true o false dependiendo de si se va ingresar a la funcionalidad
     * de detalle de mensajes.
     */
    private Boolean isActualizar;
    /**
     * Contiene la pagina actual del paginador de la página.
     */
    private Integer paginaActual;
    /**
     * Contiene true o false dependindo si el paginador se encuentra en la
     * última página.
     */
    private Boolean ultimaPagina;
    /**
     * Variable dinámica que contiene las variables de las consultas a realizar
     * en la lógica de negocio.
     */
    private Hashtable<String, String> parametros;
    /**
     * Contiene la cantidad de páginas del paginador.
     */
    private Integer totalPaginas;
    /**
     * Contiene el valor de la constante de pagina siguiente.
     */
    private String siguiente = MensajeTipoValidacion.SIGUIENTE.getValor();
    /**
     * Contiene el valor de la constante de pagina atrás.
     */
    private String atras = MensajeTipoValidacion.ATRAS.getValor();
    /**
     * Contiene el valor de la constante de la primera pagina.
     */
    private String primera = MensajeTipoValidacion.PRIMERA.getValor();
    /**
     * Contiene el valor de la constante de la última pagina.
     */
    private String ultima = MensajeTipoValidacion.ULTIMA.getValor();
    /**
     * Contiene el valor de la constante Ir a pagina para cuando se realiza el
     * evento de ir a una página determinada.
     */
    private String irA = MensajeTipoValidacion.IR_A_PAGINA.getValor();
    /**
     * Contiene los tipos de validación seleccionados.
     */
    private String tipoValidacionDetalleSelected;
    /**
     * Almacena el contexto del jsf de mensajes de validación.
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * HttpServerResponse para redirigir la autenticación de agendamiento.
     */
    private HttpServletResponse response = (HttpServletResponse) facesContext
            .getExternalContext().getResponse();
    /**
     * Usuario de la aplicación.
     */
    private String usuarioVT = null;
    /**
     * Perfil del usuario de la aplicación.
     */
    private int perfilVT = 0;
    /**
     * Almacena el mensaje de validación de los campos vacios.
     */
    private String campoVacio = MensajeTipoValidacion.CAMPOS_VACIOS_ESPECIFICOS
            .getValor();
    /**
     * Contiene true o false para cargar o no la funcionalidad de creación de
     * mensajes.
     */
    private Boolean isCrear;
    /**
     * Contiene true o false para cargar o no la funcionalidad de consultar de
     * mensajes.
     */
    private Boolean isConsultar;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private final String FORMULARIO = "MENSAJEVIABILIDAD";
    private SecurityLogin securityLogin;
    private static final Logger LOGGER = LogManager.getLogger(MensajeValidacionBean.class);
    private final String ROLOPCCREATEMSJVIA = "ROLOPCCREATEMSJVIA";
    private final String ROLOPCDELMSJVIA = "ROLOPCDELMSJVIA";

    /**
     * Constructor del bean, se inicia la seguridad y se obtiene el usuario y su
     * perfil.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public MensajeValidacionBean() {
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
            String msn = "Ocurrio un error validando el usuario en MensajeValidacionBean";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
              FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Inicio de los objetos.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    @PostConstruct
    public void init() {
        try {
            this.mensajesValidacion = new ArrayList<CmtMensajeTipoValidacion>();
            obtenerTiposValidacion();
            this.paginaActual = 1;
            this.ultimaPagina = false;
            valorValidacionList = new ArrayList<CmtBasicaMgl>();
            iMensaje.setUser(usuarioVT, perfilVT);
            this.isActualizar = false;
            this.isCrear = false;
            this.isConsultar = true;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtener la lista de tipos de validación del formulario. Obtiene la lista
     * de tipos de validación del formulario.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    private void obtenerTiposValidacion() {
        try {
            tiposValidacion = tipoValidacionMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(MensajeValidacionBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtener la lista de los valores de la validacion del formulario. Obtiene
     * la lista de los valores de la validacion del formulario.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void obtenerValorValidacion() {
        try {
            if (tipoValidacionDetalleSelected != null
                    && !tipoValidacionDetalleSelected.trim().isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaSelected = new CmtTipoBasicaMgl();
                tipoBasicaSelected.setTipoBasicaId(
                        new BigDecimal(tipoValidacionDetalleSelected));
                valorValidacionList =
                        basicaMglFacadeLocal.findByTipoBasica(tipoBasicaSelected);
            } else {
                valorValidacionList = new ArrayList<CmtBasicaMgl>();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(MensajeValidacionBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Acturalizar la tabla de mensajes en la página. Se encarga de actualizar
     * la tabla de mensajes de la pantalla de consulta según tipo de validación
     * seleccionada.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param movimiento Movimiento que realizará el paginador.
     */
    public void actualizarTablaMensajes(String movimiento) {
        if (isCrear) {
            this.setSeleccionTipoValidacion(new BigDecimal(tipoValidacionDetalleSelected));
        }

        isActualizar = false;
        isCrear = false;
        isConsultar = true;
        parametros = new Hashtable<String, String>();

        if (seleccionTipoValidacion != null) {
            parametros.put("tipoValidacion", seleccionTipoValidacion.toString());
        }

        parametros.put("pagina", this.paginaActual.toString());
        parametros.put("movimiento", movimiento);

        try {
            mensajesValidacion = iMensaje.findMensajesPorFiltros(parametros);
            this.totalPaginas = iMensaje.obtenerCantidadPaginas(parametros);
            ultimaPagina = Boolean.valueOf(parametros.get("ultimaPagina"));
            paginaActual = new Integer(parametros.get("pagina"));
            this.totalPaginas = new Integer(parametros.get("totalPaginas"));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(MensajeValidacionBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Cambiar el estado cuando se presiona un link o el botón crear. Cambia el
     * estado cuando se presiona un link o el botón crear.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void irCrearMensaje() {
        limpiarCamposMensajesGlobales();
        tipoValidacionDetalleSelected = "";
        this.isActualizar = false;
        this.isConsultar = false;
        this.isCrear = true;
        this.mensajesValidacionCreacion = new ArrayList<CmtMensajeTipoValidacion>();
    }

    /**
     * Persistir un mensaje nuevo en el sistema. Persiste un mensaje nuevo en el
     * sistema.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void crearMensajes() {
        try {
            if (tipoValidacionDetalleSelected != null
                    && !tipoValidacionDetalleSelected.trim().isEmpty()) {
                iMensaje.createListaMensajes(mensajesValidacionCreacion);
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajeTipoValidacion.EXITOSOS.getValor(), ""));
                actualizarTablaMensajes(primera);
            } else {
                String msn = "Debe seleccionar un tipo de validación";
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msn, ""));
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al momento de crear mensajes: "+ ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de crear mensajes: "+ e.getMessage(), e, LOGGER);
        }
    }

    /**
     * volver a la página de inicio. vuelve a la página de inicio.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void volver() {
        this.isActualizar = false;
        this.isCrear = false;
        this.isConsultar = true;
    }

    /**
     * Encargado de mostrar el formulario de detalle.Metodo encargado del
 cambio de formulario para mostrar el detalle del registro seleccionado.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensaje Datos del mensaje.
     * @return 
     */
    public String irActualizarMensaje(CmtMensajeTipoValidacion mensaje) {
        try {
            this.isActualizar = true;
            this.isConsultar = false;
            this.isCrear = false;
            mensajeTipVal = iMensaje.findForId(mensaje.getIdMensaje());
            tipoValidacionDetalleSelected = mensajeTipVal.getIdValidacion().
                    getTipoBasicaObj().getTipoBasicaId().toString();
            obtenerValorValidacion();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(MensajeValidacionBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Encargado de realizar la actualización de mensajes. Encargado de realizar
     * la actualización de mensajes.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void actualizarMensaje() {
        if (mensajeTipVal != null) {
            try {
                iMensaje.update(mensajeTipVal);
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajeTipoValidacion.ACTUALIZA_OK.getValor()
                        + mensajeTipVal.getIdMensaje(), ""));
                actualizarTablaMensajes(primera);
            } catch (ApplicationException e) {
                   FacesUtil.mostrarMensajeError(MensajeValidacionBean.class.getName()+ e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     * Aplicar tipos de mensajes en el sistema. Aplica los mensajes de los tipos
     * de respuesta si/no/procesos/na/restringidos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void aplicarMensajeGlobales() {
        try {
            if (mensajesValidacionCreacion != null && !mensajesValidacionCreacion.isEmpty()) {
                for (CmtMensajeTipoValidacion cmtMensajeTipoValidacion : mensajesValidacionCreacion) {

                    cmtMensajeTipoValidacion.setMensajeSi(mensajeSiGlobal);
                    cmtMensajeTipoValidacion.setMensajeNo(mensajeNoGlobal);
                    cmtMensajeTipoValidacion.setMensajeProcesos(mensajeProcesoGlobal);
                    cmtMensajeTipoValidacion.setMensajeNa(mensajeNaGlobal);
                    cmtMensajeTipoValidacion.setMensajeRestringido(mensajeRestringidoGlobal);
                }
                limpiarCamposMensajesGlobales();
                String msn = "Mensajes aplicados satisfactoriamente.";
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        msn, ""));
            } else {
                String msn = "Debe existir al menos un registro en la tabla.";
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msn, ""));
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al momento de aplicar mensajes globales: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Limpiar los campos de los mensajes. Limpia los campos de mensajes
     * globales.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void limpiarCamposMensajesGlobales() {
        mensajeSiGlobal = "";
        mensajeNoGlobal = "";
        mensajeProcesoGlobal = "";
        mensajeNaGlobal = "";
        mensajeRestringidoGlobal = "";
    }

    /**
     * Eliminar los mensajes globales. Elimina los mensajes globales.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     */
    public void limpiarMensajeGlobales() {
        try {
            if (mensajesValidacionCreacion != null && !mensajesValidacionCreacion.isEmpty()) {
                limpiarCamposMensajesGlobales();

                for (CmtMensajeTipoValidacion cmtMensajeTipoValidacion : mensajesValidacionCreacion) {

                    cmtMensajeTipoValidacion.setMensajeSi("");
                    cmtMensajeTipoValidacion.setMensajeNo("");
                    cmtMensajeTipoValidacion.setMensajeProcesos("");
                    cmtMensajeTipoValidacion.setMensajeNa("");
                    cmtMensajeTipoValidacion.setMensajeRestringido("");
                }
            } else {
                String msn = "Debe existir al menos un registro en la tabla.";
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        msn, ""));
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error al momento de limpiar mensajes globales: " + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Borra el mensaje seleccionado de la tabla de consula. Encargado de borrar
     * el mensaje seleccionado de la tabla de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mesajeToDelete Mensaje a realizar eliminado lógico.
     */
    public void eliminarMensajeFromTable(CmtMensajeTipoValidacion mesajeToDelete) {
        mensajeTipVal = mesajeToDelete;
        borrarMensaje();
    }

    /**
     * Borra el mensaje del sistema. Encargado de borrar el mensaje seleccionado
     * del sistema.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void borrarMensaje() {
        if (this.mensajeTipVal != null) {
            try {
                iMensaje.delete(mensajeTipVal);
                FacesContext.getCurrentInstance().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        MensajeTipoValidacion.BORRADO_OK.getValor(), ""));
                mensajesValidacion = new ArrayList<CmtMensajeTipoValidacion>();
                actualizarTablaMensajes(primera);

            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError(MensajeTipoValidacion.BORRADO_FALLIDO.getValor() + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
            }
        }
    }

    /**
     * Trae los mensajes no configurados. Llena la tabla de creación con los
     * mensajes que no se han configurado para un tipo de validación
     * seleccionado.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     */
    public void llenarTablaCreacion() {
        if (tipoValidacionDetalleSelected != null && !tipoValidacionDetalleSelected.isEmpty()) {
            mensajesValidacionCreacion = iMensaje.findMensajesNoConfiguradosPorTipoBasica(
                    new BigDecimal(tipoValidacionDetalleSelected));
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
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION+ e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en MensajeValidacionBean  class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATEMSJVIA);
    }
    
    public boolean validarEliminarRol() {
        return validarEdicion(ROLOPCDELMSJVIA);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Obtener el identificador del mensaje de validación. Obtiene el
     * identificador del mensaje de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El identificador del mensaje de validación.
     */
    public BigDecimal getIdMensaje() {
        return idMensaje;
    }

    /**
     * Cambiar el identificador del mensaje de validación. Cambia el
     * identificador del mensaje de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idMensaje El identificador del mensaje de validación.
     */
    public void setIdMensaje(BigDecimal idMensaje) {
        this.idMensaje = idMensaje;
    }

    /**
     * Obtener el identificador del tipo de validación. Obtiene el identificador
     * del tipo de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El identificador del tipo de validación.
     */
    public BigDecimal getIdValidacion() {
        return idValidacion;
    }

    /**
     * Cambiar el identificador del tipo de validación. Cambia el identificador
     * del tipo de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idValidacion El identificador del tipo de validación.
     */
    public void setIdValidacion(BigDecimal idValidacion) {
        this.idValidacion = idValidacion;
    }

    /**
     * Obtener el identificador del proyecto. Obtiene el identificador del
     * proyecto.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El identificador del proyecto.
     */
    public BigDecimal getIdProyecto() {
        return idProyecto;
    }

    /**
     * Cambiar el identificador del proyecto. Cambia el identificador del
     * proyecto.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idProyecto El identificador del proyecto.
     */
    public void setIdProyecto(BigDecimal idProyecto) {
        this.idProyecto = idProyecto;
    }

    /**
     * Obtener los mensajes del si. Obtiene los mensajes del si.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes del si.
     */
    public String getMensajeSi() {
        return mensajeSi;
    }

    /**
     * Cambiar los mensajes del si. Cambia los mensajes del si.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeSi Los mensajes del si.
     */
    public void setMensajeSi(String mensajeSi) {
        this.mensajeSi = mensajeSi;
    }

    /**
     * Obtener el mensaje del no. Obtiene el mensaje del no.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El mensaje del no.
     */
    public String getMensajeNo() {
        return mensajeNo;
    }

    /**
     * Cambiar el mensaje del no. Cambia el mensaje del no.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNo El mensaje del no.
     */
    public void setMensajeNo(String mensajeNo) {
        this.mensajeNo = mensajeNo;
    }

    /**
     * Obtener los mensajes de proceso. Obtiene los mensajes de proceso.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes de proceso.
     */
    public String getMensajeProceso() {
        return mensajeProceso;
    }

    /**
     * Cambiar los mensajes de proceso. Cambia los mensajes de proceso.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeProceso Los mensajes de proceso.
     */
    public void setMensajeProceso(String mensajeProceso) {
        this.mensajeProceso = mensajeProceso;
    }

    /**
     * Obtener los mensajes del no aplica. Obtiene los mensajes del no aplica.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes del no aplica.
     */
    public String getMensajeNa() {
        return mensajeNa;
    }

    /**
     * Cambiar los mensajes del no aplica. Cambia los mensajes del no aplica.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNa Los mensajes del no aplica.
     */
    public void setMensajeNa(String mensajeNa) {
        this.mensajeNa = mensajeNa;
    }

    /**
     * Obtiene el estado del mensaje de validación. Obtiene el estado del
     * mensaje de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El estado del mensaje de validación.
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * Cambiar el estado del mensaje de validación. Cambia el estado del mensaje
     * de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param estado El estado del mensaje de validación.
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * Obtener la lista de mensajes de validación. Obtiene la lista de mensajes
     * de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de mensajes de validación.
     */
    public List<CmtMensajeTipoValidacion> getMensajesValidacion() {
        return mensajesValidacion;
    }

    /**
     * Cambiar la lista de mensajes de validación. Cambia la lista de mensajes
     * de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajesValidacion La lista de mensajes de validación.
     */
    public void setMensajesValidacion(List<CmtMensajeTipoValidacion> mensajesValidacion) {
        this.mensajesValidacion = mensajesValidacion;
    }

    /**
     * Obtener la lista de tipos de validación. Obtiene la lista de tipos de
     * validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de tipos de validación.
     */
    public List<CmtTipoValidacionMgl> getTiposValidacion() {
        return tiposValidacion;
    }

    /**
     * Cambiar la lista de tipos de validación. Cambia la lista de tipos de
     * validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param tiposValidacion La lista de tipos de validación.
     */
    public void setTiposValidacion(List<CmtTipoValidacionMgl> tiposValidacion) {
        this.tiposValidacion = tiposValidacion;
    }

    /**
     * Habilitar los campos que contengan este atributo. Habilita los campos que
     * contengan este atributo.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para habilitar o deshabilitar los campos.
     */
    public Boolean getHabilitarCampos() {
        return habilitarCampos;
    }

    /**
     * Cambiar los campos que contengan este atributo. Cambia los campos que
     * contengan este atributo.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param habilitarCampos True o false para habilitar o deshabilitar los
     * campos.
     */
    public void setHabilitarCampos(Boolean habilitarCampos) {
        this.habilitarCampos = habilitarCampos;
    }

    /**
     * Obtener true o false para habilitar el boton. Obtiene true o false para
     * habilitar el boton.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para habilitar el boton.
     */
    public Boolean getHabilitarBoton() {
        return habilitarBoton;
    }

    /**
     * Cambiar true o false para habilitar el boton. Cambia true o false para
     * habilitar el boton.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param habilitarBoton True o false para habilitar el boton.
     */
    public void setHabilitarBoton(Boolean habilitarBoton) {
        this.habilitarBoton = habilitarBoton;
    }

    /**
     * Obtener el item seleccionado del combo de tipos de validación.Obtiene el
 item seleccionado del combo de tipos de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return 
     */
    public BigDecimal getSeleccionTipoValidacion() {
        return seleccionTipoValidacion;
    }

    /**
     * Cambiar el item seleccionado del combo de tipos de validación. Cambia el
     * item seleccionado del combo de tipos de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param tipoValidacionSeleccion El item seleccionado del combo de tipos de
     * validación.
     */
    public void setSeleccionTipoValidacion(BigDecimal tipoValidacionSeleccion) {
        this.seleccionTipoValidacion = tipoValidacionSeleccion;
    }

    /**
     * Obtener el valor apara mostrar el panel de actualizar. Obtiene el valor
     * que indica si el panel mostrado es el detalle o la consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True para habilitar el panel detalle de lo contrario el panel
     * consulta.
     */
    public Boolean getIsActualizar() {
        return isActualizar;
    }

    /**
     * Cambiar el valor para mostrar los componentes de la funcionalidad de
     * actualizar. Cambia el valor que indica si el panel mostrado es el detalle
     * o la consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param isActualizar True para habilitar el panel detalle de lo contrario
     * el panel consulta.
     */
    public void setIsActualizar(Boolean isActualizar) {
        this.isActualizar = isActualizar;
    }

    /**
     * Obtener la pagina actual de la tabla en el panel de consulta. Obtiene la
     * pagina actual de la tabla en el panel de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La pagina actual de la tabla en el panel de consulta.
     */
    public Integer getPaginaActual() {
        return paginaActual;
    }

    /**
     * Cambiar la pagina actual de la tabla en el panel de consulta. Cambia la
     * pagina actual de la tabla en el panel de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param paginaActual La pagina actual de la tabla en el panel de consulta.
     */
    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }

    /**
     * Obtener la última pagina del paginador. Obtiene true o false cuando se
     * lleaga a la última página del paginador de la pantalla de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True o false cuando se lleaga a la última página del paginador de
     * la pantalla de consulta
     */
    public Boolean getUltimaPagina() {
        return ultimaPagina;
    }

    /**
     * Cambia el valor de la última página del paginador. Cambia true o false
     * cuando se lleaga a la última página del paginador de la pantalla de
     * consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param ultimaPagina True o false cuando se lleaga a la última página del
     * paginador de la pantalla de consulta
     */
    public void setUltimaPagina(Boolean ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    /**
     * Obtener el total de paginas del paginador. Obtiene el total de páginas
     * que puede tener el paginador segun datos del tipo de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El total de páginas que puede tener el paginador segun datos del
     * tipo de validación.
     */
    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * Cambiar el total de paginas del paginador. Cambia el total de páginas que
     * puede tener el paginador segun datos del tipo de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param totalPaginas El total de páginas que puede tener el paginador
     * segun datos del tipo de validación.
     */
    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * Obtener el mensaje seleccionado. Obtiene el mensaje seleccionado.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El mensaje seleccionado.
     */
    public CmtMensajeTipoValidacion getMensajeTipVal() {
        return mensajeTipVal;
    }

    /**
     * Cambiar el mensaje seleccionado. Cambia el mensaje seleccionado.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeTipVal El mensaje seleccionado.
     */
    public void setMensajeTipVal(CmtMensajeTipoValidacion mensajeTipVal) {
        this.mensajeTipVal = mensajeTipVal;
    }

    /**
     * Obtener a la siguiente página del paginador. Obtiene a la siguiente
     * página del paginador.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La siguiente página del paginador.
     */
    public String getSiguiente() {
        return siguiente;
    }

    /**
     * Obtener la psgina anterior del paginador. Obtiene la psgina anterior del
     * paginador.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La psgina anterior del paginador.
     */
    public String getAtras() {
        return atras;
    }

    /**
     * Obtener la primera página del paginador Obtiene la primera página del
     * paginador
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La primera página del paginador
     */
    public String getPrimera() {
        return primera;
    }

    /**
     * Obtener la última página del paginador Obtiene la última página del
     * paginador
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La última página del paginador
     */
    public String getUltima() {
        return ultima;
    }

    /**
     * Obtener la página especifica donde se moverá el paginador Obtiene la
     * página especifica donde se moverá el paginador
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La página especifica donde se moverá el paginador
     */
    public String getIrA() {
        return irA;
    }

    /**
     * Obtener el mensaje de validación seleccionado de la tabla de búsqueda
     * Obtiene el mensaje de validación seleccionado de la tabla de búsqueda
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return tipoValidacionDetalleSelected El mensaje de validación
     * seleccionado de la tabla de búsqueda
     */
    public String getTipoValidacionDetalleSelected() {
        return tipoValidacionDetalleSelected;
    }

    /**
     * Cambiar el mensaje de validación seleccionado de la tabla de búsqueda
     * Cambia el mensaje de validación seleccionado de la tabla de búsqueda
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param tipoValidacionDetalleSelected El mensaje de validación
     * seleccionado de la tabla de búsqueda
     */
    public void setTipoValidacionDetalleSelected(String tipoValidacionDetalleSelected) {
        this.tipoValidacionDetalleSelected = tipoValidacionDetalleSelected;
    }

    /**
     * Obtener las lista de basicas asociadas al tipo de validacion. Obtiene las
     * lista de basicas asociadas al tipo de validacion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return valorValidacionList Las lista de basicas asociadas al tipo de
     * validacion.
     */
    public List<CmtBasicaMgl> getValorValidacionList() {
        return valorValidacionList;
    }

    /**
     * Cambiar las lista de basicas asociadas al tipo de validacion. Cambia las
     * lista de basicas asociadas al tipo de validacion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param valorValidacionList Las lista de basicas asociadas al tipo de
     * validacion.
     */
    public void setValorValidacionList(List<CmtBasicaMgl> valorValidacionList) {
        this.valorValidacionList = valorValidacionList;
    }

    /**
     * Obtener la lista de basicas asociadas al tipo de validacion. Obtiene la
     * lista de basicas asociadas al tipo de validacion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La lista de basicas asociadas al tipo de validacion.
     */
    public List<CmtBasicaMgl> getBasicasXTV() {
        return basicasXTV;
    }

    /**
     * Cambiar la lista de basicas asociadas al tipo de validacion. Cambia la
     * lista de basicas asociadas al tipo de validacion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param basicasXTV La lista de basicas asociadas al tipo de validacion.
     */
    public void setBasicasXTV(List<CmtBasicaMgl> basicasXTV) {
        this.basicasXTV = basicasXTV;
    }

    /**
     * Obtener el mensaje de validación si los campos se encuentran vacios.
     * Obtiene el mensaje de validación si los campos se encuentran vacios.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El mensaje de validación si los campos se encuentran vacios.
     */
    public String getCampoVacio() {
        return campoVacio;
    }

    /**
     * Cambiar el mensaje de validación si los campos se encuentran vacios.
     * Cambia el mensaje de validación si los campos se encuentran vacios.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param campoVacio El mensaje de validación si los campos se encuentran
     * vacios.
     */
    public void setCampoVacio(String campoVacio) {
        this.campoVacio = campoVacio;
    }

    /**
     * Obtener true o false para renderizar la pantalla de creación. Obtiene
     * true o false para renderizar la pantalla de creación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para renderizar la pantalla de creación.
     */
    public Boolean getIsCrear() {
        return isCrear;
    }

    /**
     * Cambiar true o false para renderizar la pantalla de creación. Cambia true
     * o false para renderizar la pantalla de creación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param isCrear True o false para renderizar la pantalla de creación.
     */
    public void setIsCrear(Boolean isCrear) {
        this.isCrear = isCrear;
    }

    /**
     * Obtener true o false para renderizar la pantalla de consulta. Obtiene
     * true o false para renderizar la pantalla de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True o false para renderizar la pantalla de consulta.
     */
    public Boolean getIsConsultar() {
        return isConsultar;
    }

    /**
     * Cambiar true o false para renderizar la pantalla de consulta. Cambia true
     * o false para renderizar la pantalla de consulta.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param isConsultar True o false para renderizar la pantalla de consulta.
     */
    public void setIsConsultar(Boolean isConsultar) {
        this.isConsultar = isConsultar;
    }

    /**
     * Obtener la lista de validación de mensajes en la pantalla de creación.
     * Obtiene la lista de validación de mensajes en la pantalla de creación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return la lista de validación de mensajes en la pantalla de creación.
     */
    public List<CmtMensajeTipoValidacion> getMensajesValidacionCreacion() {
        return mensajesValidacionCreacion;
    }

    /**
     * Cambiar la lista de validación de mensajes en la pantalla de creación.
     * Cambia la lista de validación de mensajes en la pantalla de creación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajesValidacionCreacion La lista de validación de mensajes en
     * la pantalla de creación.
     */
    public void setMensajesValidacionCreacion(List<CmtMensajeTipoValidacion> mensajesValidacionCreacion) {
        this.mensajesValidacionCreacion = mensajesValidacionCreacion;
    }

    /**
     * Obtener el mensaje global si. Obtiene el mensaje global si.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje global si.
     */
    public String getMensajeSiGlobal() {
        return mensajeSiGlobal;
    }

    /**
     * Cambiar el mensaje global si. Cambia el mensaje global si.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeSiGlobal el mensaje global si.
     */
    public void setMensajeSiGlobal(String mensajeSiGlobal) {
        this.mensajeSiGlobal = mensajeSiGlobal;
    }

    /**
     * Obtener el mensaje no global. Obtiene el mensaje no global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje no global.
     */
    public String getMensajeNoGlobal() {
        return mensajeNoGlobal;
    }

    /**
     * Obtener el mensaje no global. Obtiene el mensaje no global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNoGlobal el mensaje no global.
     */
    public void setMensajeNoGlobal(String mensajeNoGlobal) {
        this.mensajeNoGlobal = mensajeNoGlobal;
    }

    /**
     * Obtener el mensaje global de los procesos. Obtiene el mensaje global de
     * los procesos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje global de los procesos.
     */
    public String getMensajeProcesoGlobal() {
        return mensajeProcesoGlobal;
    }

    /**
     * Cambiar el mensaje global de los procesos. Cambia el mensaje global de
     * los procesos.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeProcesoGlobal el mensaje global de los procesos.
     */
    public void setMensajeProcesoGlobal(String mensajeProcesoGlobal) {
        this.mensajeProcesoGlobal = mensajeProcesoGlobal;
    }

    /**
     * Obtener el mensaje na global. Obtiene el mensaje na global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje na global.
     */
    public String getMensajeNaGlobal() {
        return mensajeNaGlobal;
    }

    /**
     * Cambiar el mensaje na global. Cambia el mensaje na global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNaGlobal
     */
    public void setMensajeNaGlobal(String mensajeNaGlobal) {
        this.mensajeNaGlobal = mensajeNaGlobal;
    }

    /**
     * Obtener el mensaje restringido global. Obtiene el mensaje restringido
     * global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje restringido global.
     */
    public String getMensajeRestringidoGlobal() {
        return mensajeRestringidoGlobal;
    }

    /**
     * Cambiar el mensaje restringido global. Cambia el mensaje restringido
     * global.
     *
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeRestringidoGlobal
     */
    public void setMensajeRestringidoGlobal(String mensajeRestringidoGlobal) {
        this.mensajeRestringidoGlobal = mensajeRestringidoGlobal;
    }

    //Opciones agregadas para Rol
    private final String BTNCRAMNS = "BTNCRAMNS";
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionCrear() {
        return validarEdicionRol(BTNCRAMNS);
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
