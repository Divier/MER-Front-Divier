package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.delegate.ResourceDelegate;
import co.com.telmex.catastro.delegate.SolicitudesDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.EnvioCorreo;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 * Clase ActualizarEstadoSolManualMBean
 * Extiende de BaseMBean
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
@SessionScoped
@ManagedBean(name = "actualizarEstadoSolManualMBean")
public class ActualizarEstadoSolManualMBean extends BaseMBean {

    /**
     * 
     */
    public static final String NOMBRE_FUNCIONALIDAD = "ACTUALIZAR ESTADO DE SOLICITUDES";
    /**
     * 
     */
    public static final String EEROR_ENVIO_EMAIL_NO_FUERON_ENVIADOS_CORREC = " Algunos e-mail no fueron enviados correctamente.";
    /**
     * 
     */
    public static final String EMAIL_ASUNTO_SOLICITUD_CERRADA = "Repositorio direcciones, Solicitud Cerrada ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 = " con la dirección: ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 = "Su solicitud de ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO5 = "HHPP creado con la dirección: ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 = "Cualquier observación será atendida en el correo: ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO3 = ". La verificación agendada con la dirección: ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 = " ha sido CERRADA";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 = "Recuerde su solicitud de negocio tiene el código: ";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_RED_CONTENIDO2 = " ha sido CERRADA";
    /**
     * 
     */
    public static final String EMAIL_MESSAGE_SOL_RED_CONTENIDO1 = "Su solicitud de red con código ";
    /**
     * 
     */
    public static final String ERROR_AL_CONSULTAR_LAS_SOLICITUDES_POR_NOM = "Error al consultar las solicitudes por nombre de archivo";
    /**
     * 
     */
    public static final String NO_HAY_SOLICITUDES_DE_NEGOCIO_EN_PROCESO = "No hay solicitudes de negocio en proceso";
    /**
     * 
     */
    public static final String OBLIGATORIO_TIPO_DE_SOLICITUD = "Debe seleccionar un tipo de solicitud para continuar.";
    /**
     * 
     */
    public static final String OCURRIO_UN_ERROR_AL_CONSULTAR_SOL_NEGOCIO = "Ocurrió un ERROR al consultar las Solicitudes de Negocio.";
    /**
     * 
     */
    public static final String OBLIGATORIO_ESTADO_FINAL = "Debe seleccionar un estado final.";
    /**
     * 
     */
    public static final String NO_HAY_SOLICITUDES_DE_RED_EN_PROCESO = "No hay solicitudes de red en proceso";
    /**
     * 
     */
    public static final String OCURRIO_UN_ERROR_AL_CONSULTAR_SOL_RED = "Ocurrió un ERROR al consultar las Solicitudes de Red.";
    /**
     * 
     */
    public static final String ERROR_CONSULTA_SOL_NEG_ARCHIVO = "Error al consultar las solicitudes de negocio de este archivo.";
    /**
     * 
     */
    public static final String ERROR_CONSULTA_SOL_RED_ARCHIVO = "Error al consultar las solicitudes de red de este archivo.";
    /**
     * 
     */
    public String vacio = "";
    /**
     * 
     */
    public static final String TIPO_SOLICITUD_NEGOCIO = "SOL_NEG";
    /**
     * 
     */
    public static final String TIPO_SOLICITUD_RED = "SOL_RED";
    /**
     * 
     */
    public String sol_tipoSolicitud;
    /**
     * 
     */
    public List<SelectItem> listTipoSolicitud = null;
    /**
     * 
     */
    public String idSolNeg;
    /**
     * 
     */
    public String idSolRed;
    /**
     * 
     */
    public String solNegArchGenerado;
    /**
     * 
     */
    public SolicitudNegocio solNeg = null;
    /**
     * 
     */
    public SolicitudRed solRed = null;
    //Listas de los Tipos de solicitud existentes
    /**
     * 
     */
    public List<SolicitudNegocio> lstSolNegocio = null;
    /**
     * 
     */
    public List<SolicitudRed> lstSolRed = null;
    /**
     * 
     */
    public List<SelectItem> listEstadosFinal = null;
    //Atributos de la direccion parametrizada
    /**
     * 
     */
    public String seleccionar = "";
    //variable de log para la clase
    private String nombreLog;
    //variables de la solicitud de negocio a mostrar en pantalla
    /**
     * 
     */
    public String solNeg_gpo_nombre;
    /**
     * 
     */
    public String solNeg_nodo_nombre;
    /**
     * 
     */
    public String solNeg_tvi_nombre;
    /**
     * 
     */
    public String solNeg_geo_nombre;
    /**
     * 
     */
    public String solNeg_thr_codigo;
    /**
     * 
     */
    public String solNeg_thc_codigo;
    /**
     * 
     */
    public String solNeg_tma_codigo;
    /**
     * 
     */
    public String son_cuentaMatriz;
    /**
     * 
     */
    public String son_motivo;
    /**
     * 
     */
    public String son_nomSolicitante;
    /**
     * 
     */
    public String son_codSolicitante;
    /**
     * 
     */
    public String son_email;
    /**
     * 
     */
    public String son_cel_solicitante;
    /**
     * 
     */
    public String son_nomContacto;
    /**
     * 
     */
    public String son_telContacto;
    /**
     * 
     */
    public String son_observaciones;
    /**
     * 
     */
    public String son_tipoSolicitud;
    /**
     * 
     */
    public String son_estado_sol;
    /**
     * 
     */
    public String son_formatoIgac;
    /**
     * 
     */
    public String son_noStandar;
    /**
     * 
     */
    public String son_estrato;
    /**
     * 
     */
    public String son_zipcode;
    /**
     * 
     */
    public String son_dirAlterna;
    /**
     * 
     */
    public String son_localidad;
    /**
     * 
     */
    public String son_mz;
    /**
     * 
     */
    public String son_longitud;
    /**
     * 
     */
    public String son_latitud;
    /**
     * 
     */
    public String son_estado;
    /**
     * 
     */
    public String son_estado_final;
    /**
     * 
     */
    public String son_nombre_archivo;
    //Variables para mostrar la solicitud de Red seleccionada
    /**
     * 
     */
    public String sre_observaciones;
    /**
     * 
     */
    public String solRed_gpo_nombre;
    /**
     * 
     */
    public String sre_usuario_creacion;
    /**
     * 
     */
    public String sre_estado;
    /**
     * 
     */
    public String sre_estadoFinal;
    /**
     * 
     */
    public String sre_nombre_archivo;
    /**
     * 
     */
    public String solRedArchivoGeneradoRr;
    /**
     * 
     */
    public boolean disableBoton = Boolean.FALSE;
    /**
     * 
     */
    public boolean showQueryRed = Boolean.FALSE;
    /**
     * 
     */
    public boolean showQueryNeg = Boolean.FALSE;
    private int scrollerPageRed = 0;
    private int scrollerPageNeg = 0;
    private boolean showBotonActualizar;
    /**
     * 
     */
    public String message2 = "";
    /**
     * 
     */
    public String seleccione = "-- Seleccione --";
    /**
     * 
     */
    public boolean showTablaNegocio = false;
    /**
     * 
     */
    public boolean showTablaRed = false;
    /**
     * 
     */
    public boolean showPopUp = false;
    private boolean showBotonConfirmar = false;
    
    private static final Logger LOGGER = LogManager.getLogger(ActualizarEstadoSolManualMBean.class);

    /**
     * 
     * @throws IOException
     */
    public ActualizarEstadoSolManualMBean() throws IOException {
        super();
    }

    private String queryParametros() throws ApplicationException{
        String horas = null;
        Parametros p = ResourceDelegate.queryParametros("HORAS_POR_DIA");
        if (p != null) {
            horas = p.getDescripcion();
        }
        return horas;
    }

    /**
     * ActionListener ejecutado al seleccionar el tipo de consulta
     * @param ev 
     */
    public void doConsultar(ActionEvent ev) {
        message = validarCamposObligatorios();
        if (message.isEmpty()) {
            if (TIPO_SOLICITUD_NEGOCIO.equals(sol_tipoSolicitud)) {
                mostrarSolNegocio();
            } else if (TIPO_SOLICITUD_RED.equals(sol_tipoSolicitud)) {
                mostrarSolRed();
            }
        }
    }

    /**
     * Muestra la tabla de solicitudes de Negocio
     */
    public void mostrarSolNegocio() {
        boolean hayError = false;
        scrollerPageNeg = 0;
        showTablaRed = false;

        solNeg = new SolicitudNegocio();
        //Se consultan los nombres de archivos para las solicitudes en estado ARCHIVO GENERADO
        try {
            lstSolNegocio = SolicitudesDelegate.querySolicitudesNegocio();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("ERROR: Al consultar las Solicitudes de Negocio{0}" + e.getMessage() , e, LOGGER);
            hayError = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("ERROR: Al consultar las Solicitudes de Negocio{0}" + e.getMessage() , e, LOGGER);
            hayError = true;
        }

        if (lstSolNegocio == null || lstSolNegocio.size() < 1) {
            message = NO_HAY_SOLICITUDES_DE_NEGOCIO_EN_PROCESO;
            showTablaNegocio = false;
            showTablaRed = false;
        } else {
            //Se muestra el panel de paginacion
            showQueryNeg = Boolean.TRUE;
            showTablaNegocio = true;
        }

        if (hayError) {
            message = OCURRIO_UN_ERROR_AL_CONSULTAR_SOL_NEGOCIO;
            showTablaRed = false;
            showTablaNegocio = false;
        }
    }

    /**
     * Muestra la tabla de solicitudes de Red
     */
    public void mostrarSolRed() {
        boolean hayError = false;
        scrollerPageRed = 0;
        showTablaNegocio = false;
        //Se consulta la Solicitud de Red en el repositorio por el idSeleccionado para cargar todos sus atributos.
        try {
            lstSolRed = SolicitudesDelegate.querySolicitudesRed();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("ERROR: Al consultar las Solicitudes de Red{0}" + e.getMessage() , e, LOGGER);
            hayError = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("ERROR: Al consultar las Solicitudes de Red{0}" + e.getMessage() , e, LOGGER);
            hayError = true;
        }

        if (lstSolRed == null || lstSolRed.size() < 1) {
            message = NO_HAY_SOLICITUDES_DE_RED_EN_PROCESO;
        } else {
            //Se muestra el panel de paginacion
            showQueryRed = Boolean.TRUE;
            showTablaRed = true;
        }

        if (hayError) {
            message = OCURRIO_UN_ERROR_AL_CONSULTAR_SOL_RED;
            showTablaRed = false;
            showTablaNegocio = false;
        }
    }

    /**
     * ActionListener que captura el nombre de archivo del tipo Solicitud de Negocio seleccionado
     * @param ev
     */
    public void onSeleccionarNeg(ActionEvent ev) {
        this.solNegArchGenerado = ((String) (((UIParameter) ev.getComponent().findComponent("solNegArchGenerado")).getValue())).toString();
        disableBoton = Boolean.FALSE;
        showBotonActualizar = true;
        showBotonConfirmar = false;
        showPopUp = false;
        son_estado_final = "";
        showTablaNegocio = false;
        showTablaRed = false;
        showQueryNeg = false;
        showQueryRed = false;
        message2 = "";

        if (!solNegArchGenerado.isEmpty()) {
            son_nombre_archivo = solNegArchGenerado;
        } else {
            message = ActualizarEstadoSolManualMBean.ERROR_CONSULTA_SOL_NEG_ARCHIVO;
            message2 = ActualizarEstadoSolManualMBean.ERROR_CONSULTA_SOL_NEG_ARCHIVO;
        }
    }

    /**
     * 
     */
    private void llenarFormSolNegocio() {
        son_nombre_archivo = solNeg.getSonArchivoGeneradoRr();
    }

    /**
     * Llena los campos del formulario de Solicitud de Red
     */
    private void llenarFormSolRed() {
        if (solRed != null && solRed.getGeograficoPolitico() != null) {
            sre_observaciones = solRed.getSreObservaciones();
            sre_estado = solRed.getSreEstado();
            solRed_gpo_nombre = solRed.getGeograficoPolitico().getGpoNombre();
            sre_usuario_creacion = solRed.getSreUsuarioCreacion();
        } else {
            message = "SOLICITUD DE RED NULL";
        }
    }

    /**
     * Llena la lista de los estados finales posibles del hhpp seleccionado
     * @param estadoInicial 
     */
    private void llenarEstadosFinales() {
        listEstadosFinal = new ArrayList<SelectItem>();
        SelectItem item1 = new SelectItem();
        item1.setLabel(Constant.ESTADO_SON_CERRADA);
        item1.setValue(Constant.ESTADO_SON_CERRADA);
        listEstadosFinal.add(item1);
    }

    /**
     * ActionListener que captura el id del tipo Solicitud de Red seleccionado
     * @param ev
     * @throws ApplicationException  
     */
    public void onSeleccionarRed(ActionEvent ev) {
        this.solRedArchivoGeneradoRr = ((String) (((UIParameter) ev.getComponent().findComponent("solRedArchivoGeneradoRr")).getValue())).toString();
        message2 = "";
        disableBoton = Boolean.FALSE;
        showBotonActualizar = true;
        showBotonConfirmar = false;
        showPopUp = false;
        sre_estadoFinal = "";
        showTablaNegocio = false;
        showTablaRed = false;
        showQueryNeg = false;
        showQueryRed = false;

        if (!solRedArchivoGeneradoRr.isEmpty()) {
            sre_nombre_archivo = solRedArchivoGeneradoRr;
        } else {
            message = ActualizarEstadoSolManualMBean.ERROR_CONSULTA_SOL_RED_ARCHIVO;
        }


    }

    /**
     * Action que me lleva a la pagina de resultado de solicitud Negocio
     * @return
     */
    public String onIrAccionNeg() {
        llenarEstadosFinales();
        return "modificarEstadoSolNegocio";
    }

    /**
     * Action que me lleva a la pagina de resultado de solicitud Red
     * @return
     */
    public String onIrAccionRed() {
        llenarEstadosFinales();
        return "modificarEstadoSolRed";
    }

    /**
     * 
     */
    public void modificar() {
        message2 = "";
        message = "";
        if (sol_tipoSolicitud.equals(TIPO_SOLICITUD_NEGOCIO)) {
            if (null == son_estado_final || "0".equals(son_estado_final)) {
                showBotonConfirmar = false;
                message2 = ActualizarEstadoSolManualMBean.OBLIGATORIO_ESTADO_FINAL;
            } else {
                showBotonConfirmar = true;
                showPopUp = true;
            }
        } else if (sol_tipoSolicitud.equals(TIPO_SOLICITUD_RED)) {
            if (null == sre_estadoFinal || "0".equals(sre_estadoFinal)) {
                showBotonConfirmar = false;
                message2 = ActualizarEstadoSolManualMBean.OBLIGATORIO_ESTADO_FINAL;
            } else {
                showBotonConfirmar = true;
                showPopUp = true;
            }
        }
    }

    /**
     * Actualiza el estado de las solicitudes de negocio pertenencientes al archivo seleccionado
     * @throws ApplicationException 
     */
    public void doActualizarEstadoNeg() throws ApplicationException {
        showPopUp = false;
        showBotonActualizar = false;
        showTablaNegocio = false;
        showTablaRed = false;
        showQueryNeg = false;
        showQueryRed = false;
        sol_tipoSolicitud = "";
        
        EnvioCorreo mail = new EnvioCorreo();
        String horas = null;
        try {
            horas = queryParametros();
        } catch (ApplicationException e) {
            message2 = "Error al consultar el número de horas por día de los parámetros generales.";
            FacesUtil.mostrarMensajeError(message2 + e.getMessage() , e, LOGGER);
            horas = "24";
        } catch (Exception e) {
            message2 = "Error al consultar el número de horas por día de los parámetros generales.";
            FacesUtil.mostrarMensajeError(message2 + e.getMessage() , e, LOGGER);
            horas = "24";
        }

        boolean hayErrores = false;
        List<SolicitudNegocio> listaSolNeg = null;
        try {
            //Se consultan los ids de las solicitudes afectadas
            listaSolNeg = SolicitudesDelegate.querySolNegociosByFileName(son_nombre_archivo);
        } catch (ApplicationException e) {
            message2 = ERROR_AL_CONSULTAR_LAS_SOLICITUDES_POR_NOM;
            FacesUtil.mostrarMensajeError("doActualizarEstadoNeg() - ERROR: Al actualizar el estado de la Solicitud. Message Exception {0}" + e.getMessage() , e, LOGGER);
            return;
        } catch (Exception e) {
            message2 = ERROR_AL_CONSULTAR_LAS_SOLICITUDES_POR_NOM;
            FacesUtil.mostrarMensajeError("doActualizarEstadoNeg() - ERROR: Al actualizar el estado de la Solicitud. Message Exception {0}" + e.getMessage() , e, LOGGER);
            return;
        }

        if (listaSolNeg == null || listaSolNeg.size() < 1) {
            message2 = ERROR_AL_CONSULTAR_LAS_SOLICITUDES_POR_NOM;
        } else {
            limpiarFormularios();
            for (SolicitudNegocio solicitudNegocio : listaSolNeg) {
                //Se actualiza la Solicitud de Negocio por el id
                try {
                    if (solicitudNegocio.getSonFechaCreacion() != null) {
                        String tiempoString = calculaDiferenciaFecha(solicitudNegocio.getSonFechaCreacion());
                        if (tiempoString != null) {
                            solicitudNegocio.setSonTiempohoras(tiempoString);
                        } else {
                            solicitudNegocio.setSonTiempohoras("0");
                        }
                    }

                    message2 = SolicitudesDelegate.updateStateSolNegocioById(Constant.ESTADO_SON_CERRADA, this.user.getUsuNombre(), solicitudNegocio, solicitudNegocio.getSonId().toString(), NOMBRE_FUNCIONALIDAD);
                    disableBoton = Boolean.TRUE;
                    String messageMail = "";
                    String dominio = "";
                    String correoSolucionador = "";

                    Parametros par;
                    ResourceEJB resEjb = new ResourceEJB();

                    par = resEjb.queryParametros("DOMINIO");
                    dominio = par.getValor();

                    correoSolucionador = user.getUsuLogin() + dominio;



                    //Enviar mensaje electrónico a cada uno de los usuarios que crearon la solicitud
                    try {
                        if (Constant.TIPO_SON_VERCASA.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                            messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO3 + " " + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                        } else if (Constant.TIPO_SON_HHPPUNI.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                            messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO5 + " " + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                        } else {

                            if (Constant.TIPO_SON_CAMBIONOD.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                                messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 + " cambio de nodo " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                            } else if (Constant.TIPO_SON_GRID.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                                messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 + " cambio de GRID " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                            } else if (Constant.TIPO_SON_CAMBIOEST.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                                messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 + " cambio de estado " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                            } else if (Constant.TIPO_SON_VERIFICACION.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                                messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 + " cambio de verificación " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                            } else if (Constant.TIPO_SON_CAMBIODIR.toString().equals(solicitudNegocio.getSonTipoSolicitud().toString())) {

                                messageMail = EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO6 + " cambio de dirección " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO7 + solicitudNegocio.getSonFormatoIgac() + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO2 + " " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO1 + " " + solicitudNegocio.getSonId() + "  " + EMAIL_MESSAGE_SOL_NEGOCIO_CONTENIDO4 + " " + correoSolucionador;
                            }
                        }
                        mail.envio(solicitudNegocio.getSonEmailSolicitante(), EMAIL_ASUNTO_SOLICITUD_CERRADA, messageMail);



                    } catch (ApplicationException e) {
                        hayErrores = true;
                        FacesUtil.mostrarMensajeError("doActualizarEstadoNeg().enviarEmail - ERROR: Message Exception con id:" + e.getMessage() , e, LOGGER);
                    } catch (Exception e) {
                        hayErrores = true;
                        FacesUtil.mostrarMensajeError("doActualizarEstadoNeg().enviarEmail - ERROR: Message Exception con id:" + e.getMessage() , e, LOGGER);
                    }

                } catch (ApplicationException e) {
                    FacesUtil.mostrarMensajeError("doActualizarEstadoNeg().enviarEmail - ERROR: Message Exception con id:" + e.getMessage() , e, LOGGER);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("doActualizarEstadoNeg().enviarEmail - ERROR: Message Exception con id:" + e.getMessage() , e, LOGGER);
                }
            }
            if (hayErrores) {
                message2 = message2 + EEROR_ENVIO_EMAIL_NO_FUERON_ENVIADOS_CORREC;
            }
        }
    }

    /**
     * Calcula la diferencia en horas de la fecha actual con la fecha enviada por
     * parametro
     * @param fecha
     * @return 
     */
    private String calculaDiferenciaFecha(Date fecha) {
        String diferencia = null;
        //Formateador de fecha con un decimal 
        DecimalFormat formateador = new DecimalFormat("###.#");
        Date hoy = new Date();
        double segs_hoy = hoy.getTime();
        double segs_fecha = fecha.getTime();
        //Diferencia en horas
        double dif = (segs_hoy - segs_fecha) / 3600000;
        diferencia = formateador.format(dif);
        return diferencia;
    }

    /**
     * Actualiza el estado de la Solicitud de Red Seleccionada
     * @throws ApplicationException 
     */
    public void doActualizarEstadoRed() throws ApplicationException {
        showPopUp = false;
        showBotonActualizar = false;
        showTablaNegocio = false;
        showTablaRed = false;
        showQueryNeg = false;
        showQueryRed = false;
        String dominio = "";

        Parametros par;
        ResourceEJB resEjb = new ResourceEJB();

        par = resEjb.queryParametros("DOMINIO");
        dominio = par.getValor();

        String correoSolucionador = user.getUsuLogin() + dominio;


        boolean hayErrores = false;
        List<SolicitudRed> listaSolRed = SolicitudesDelegate.querySolRedesByFileName(sre_nombre_archivo);

        if (listaSolRed == null || listaSolRed.size() < 1) {
            message2 = ERROR_AL_CONSULTAR_LAS_SOLICITUDES_POR_NOM;
        } else {
            //Resetea formularios
            showTablaNegocio = false;
            showTablaRed = false;
            limpiarFormularios();

            for (SolicitudRed solicitudRed : listaSolRed) {
                //Se actualiza la Solicitud de Red por el id
                try {
                    message2 = SolicitudesDelegate.updateStateSolRedById(Constant.ESTADO_SON_CERRADA, this.user.getUsuNombre(), solicitudRed, solicitudRed.getSreId().toString(), NOMBRE_FUNCIONALIDAD);
                    disableBoton = Boolean.TRUE;
                } catch (ApplicationException x) {
                    FacesUtil.mostrarMensajeError("doActualizarEstadoRed().enviarEmail - ERROR: Al enviar correo electronico al usuario de la solicitud con id:" + x.getMessage() , x, LOGGER);
                } catch (Exception e) {
                    hayErrores = true;
                    FacesUtil.mostrarMensajeError("doActualizarEstadoRed().enviarEmail - ERROR: Al enviar correo electronico al usuario de la solicitud con id:" + e.getMessage() , e, LOGGER);
                }
            }
            if (hayErrores) {
                message2 = message2 + EEROR_ENVIO_EMAIL_NO_FUERON_ENVIADOS_CORREC;
            }
        }
    }

    /**
     * 
     */
    private void limpiarFormularios() {
        sol_tipoSolicitud = "";
        sre_estadoFinal = "";
        son_estado_final = "";
        message = "";
    }

    /**
     * Valida los campos obligatorios del formulario
     * @return 
     */
    private String validarCamposObligatorios() {
        message = "";

        if ("".equals(sol_tipoSolicitud) || sol_tipoSolicitud == null) {
            message = ActualizarEstadoSolManualMBean.OBLIGATORIO_TIPO_DE_SOLICITUD;
        }
        return message;
    }

    //Atributos de la direccion
    /**
     * 
     * @return
     */
    public String getSeleccionar() {
        return seleccionar;
    }

    /**
     * 
     * @param seleccionar
     */
    public void setSeleccionar(String seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     * 
     * @return
     */
    public String getNombreLog() {
        return nombreLog;
    }

    /**
     * 
     * @return
     */
    public List<SelectItem> getListTipoSolicitud() {
        return listTipoSolicitud;
    }

    /**
     * 
     * @param listTipoSolicitud
     */
    public void setListTipoSolicitud(List<SelectItem> listTipoSolicitud) {
        this.listTipoSolicitud = listTipoSolicitud;
    }

    /**
     * 
     * @return
     */
    public List<SolicitudNegocio> getLstSolNegocio() {
        return lstSolNegocio;
    }

    /**
     * 
     * @param lstSolNegocio
     */
    public void setLstSolNegocio(List<SolicitudNegocio> lstSolNegocio) {
        this.lstSolNegocio = lstSolNegocio;
    }

    /**
     * 
     * @return
     */
    public String getSol_tipoSolicitud() {
        return sol_tipoSolicitud;
    }

    /**
     * 
     * @param sol_tipoSolicitud
     */
    public void setSol_tipoSolicitud(String sol_tipoSolicitud) {
        this.sol_tipoSolicitud = sol_tipoSolicitud;
    }

    /**
     * 
     * @return
     */
    public String getVacio() {
        return vacio;
    }

    /**
     * 
     * @param vacio
     */
    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    /**
     * 
     * @return
     */
    public List<SolicitudRed> getLstSolRed() {
        return lstSolRed;
    }

    /**
     * 
     * @param lstSolRed
     */
    public void setLstSolRed(List<SolicitudRed> lstSolRed) {
        this.lstSolRed = lstSolRed;
    }

    /**
     * 
     * @return
     */
    public String getIdSolNeg() {
        return idSolNeg;
    }

    /**
     * 
     * @param idSolNeg
     */
    public void setIdSolNeg(String idSolNeg) {
        this.idSolNeg = idSolNeg;
    }

    /**
     * 
     * @return
     */
    public String getIdSolRed() {
        return idSolRed;
    }

    /**
     * 
     * @param idSolRed
     */
    public void setIdSolRed(String idSolRed) {
        this.idSolRed = idSolRed;
    }

    /**
     * 
     * @return
     */
    public SolicitudNegocio getSolNeg() {
        return solNeg;
    }

    /**
     * 
     * @param solNeg
     */
    public void setSolNeg(SolicitudNegocio solNeg) {
        this.solNeg = solNeg;
    }

    /**
     * 
     * @return
     */
    public SolicitudRed getSolRed() {
        return solRed;
    }

    /**
     * 
     * @param solRed
     */
    public void setSolRed(SolicitudRed solRed) {
        this.solRed = solRed;
    }

    /**
     * 
     * @return
     */
    public String getSon_estado() {
        return son_estado;
    }

    /**
     * 
     * @param son_estado
     */
    public void setSon_estado(String son_estado) {
        this.son_estado = son_estado;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_geo_nombre() {
        return solNeg_geo_nombre;
    }

    /**
     * 
     * @param solNeg_geo_nombre
     */
    public void setSolNeg_geo_nombre(String solNeg_geo_nombre) {
        this.solNeg_geo_nombre = solNeg_geo_nombre;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_gpo_nombre() {
        return solNeg_gpo_nombre;
    }

    /**
     * 
     * @param solNeg_gpo_nombre
     */
    public void setSolNeg_gpo_nombre(String solNeg_gpo_nombre) {
        this.solNeg_gpo_nombre = solNeg_gpo_nombre;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_nodo_nombre() {
        return solNeg_nodo_nombre;
    }

    /**
     * 
     * @param solNeg_nodo_nombre
     */
    public void setSolNeg_nodo_nombre(String solNeg_nodo_nombre) {
        this.solNeg_nodo_nombre = solNeg_nodo_nombre;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_thc_codigo() {
        return solNeg_thc_codigo;
    }

    /**
     * 
     * @param solNeg_thc_codigo
     */
    public void setSolNeg_thc_codigo(String solNeg_thc_codigo) {
        this.solNeg_thc_codigo = solNeg_thc_codigo;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_thr_codigo() {
        return solNeg_thr_codigo;
    }

    /**
     * 
     * @param solNeg_thr_codigo
     */
    public void setSolNeg_thr_codigo(String solNeg_thr_codigo) {
        this.solNeg_thr_codigo = solNeg_thr_codigo;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_tma_codigo() {
        return solNeg_tma_codigo;
    }

    /**
     * 
     * @param solNeg_tma_codigo
     */
    public void setSolNeg_tma_codigo(String solNeg_tma_codigo) {
        this.solNeg_tma_codigo = solNeg_tma_codigo;
    }

    /**
     * 
     * @return
     */
    public String getSolNeg_tvi_nombre() {
        return solNeg_tvi_nombre;
    }

    /**
     * 
     * @param solNeg_tvi_nombre
     */
    public void setSolNeg_tvi_nombre(String solNeg_tvi_nombre) {
        this.solNeg_tvi_nombre = solNeg_tvi_nombre;
    }

    /**
     * 
     * @return
     */
    public String getSon_cel_solicitante() {
        return son_cel_solicitante;
    }

    /**
     * 
     * @param son_cel_solicitante
     */
    public void setSon_cel_solicitante(String son_cel_solicitante) {
        this.son_cel_solicitante = son_cel_solicitante;
    }

    /**
     * 
     * @return
     */
    public String getSon_codSolicitante() {
        return son_codSolicitante;
    }

    /**
     * 
     * @param son_codSolicitante
     */
    public void setSon_codSolicitante(String son_codSolicitante) {
        this.son_codSolicitante = son_codSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSon_cuentaMatriz() {
        return son_cuentaMatriz;
    }

    /**
     * 
     * @param son_cuentaMatriz
     */
    public void setSon_cuentaMatriz(String son_cuentaMatriz) {
        this.son_cuentaMatriz = son_cuentaMatriz;
    }

    /**
     * 
     * @return
     */
    public String getSon_dirAlterna() {
        return son_dirAlterna;
    }

    /**
     * 
     * @param son_dirAlterna
     */
    public void setSon_dirAlterna(String son_dirAlterna) {
        this.son_dirAlterna = son_dirAlterna;
    }

    /**
     * 
     * @return
     */
    public String getSon_email() {
        return son_email;
    }

    /**
     * 
     * @param son_email
     */
    public void setSon_email(String son_email) {
        this.son_email = son_email;
    }

    /**
     * 
     * @return
     */
    public String getSon_estado_final() {
        return son_estado_final;
    }

    /**
     * 
     * @param son_estado_final
     */
    public void setSon_estado_final(String son_estado_final) {
        this.son_estado_final = son_estado_final;
    }

    /**
     * 
     * @return
     */
    public String getSon_estado_sol() {
        return son_estado_sol;
    }

    /**
     * 
     * @param son_estado_sol
     */
    public void setSon_estado_sol(String son_estado_sol) {
        this.son_estado_sol = son_estado_sol;
    }

    /**
     * 
     * @return
     */
    public String getSon_estrato() {
        return son_estrato;
    }

    /**
     * 
     * @param son_estrato
     */
    public void setSon_estrato(String son_estrato) {
        this.son_estrato = son_estrato;
    }

    /**
     * 
     * @return
     */
    public String getSon_formatoIgac() {
        return son_formatoIgac;
    }

    /**
     * 
     * @param son_formatoIgac
     */
    public void setSon_formatoIgac(String son_formatoIgac) {
        this.son_formatoIgac = son_formatoIgac;
    }

    /**
     * 
     * @return
     */
    public String getSon_latitud() {
        return son_latitud;
    }

    /**
     * 
     * @param son_latitud
     */
    public void setSon_latitud(String son_latitud) {
        this.son_latitud = son_latitud;
    }

    /**
     * 
     * @return
     */
    public String getSon_localidad() {
        return son_localidad;
    }

    /**
     * 
     * @param son_localidad
     */
    public void setSon_localidad(String son_localidad) {
        this.son_localidad = son_localidad;
    }

    /**
     * 
     * @return
     */
    public String getSon_longitud() {
        return son_longitud;
    }

    /**
     * 
     * @param son_longitud
     */
    public void setSon_longitud(String son_longitud) {
        this.son_longitud = son_longitud;
    }

    /**
     * 
     * @return
     */
    public String getSon_motivo() {
        return son_motivo;
    }

    /**
     * 
     * @param son_motivo
     */
    public void setSon_motivo(String son_motivo) {
        this.son_motivo = son_motivo;
    }

    /**
     * 
     * @return
     */
    public String getSon_mz() {
        return son_mz;
    }

    /**
     * 
     * @param son_mz
     */
    public void setSon_mz(String son_mz) {
        this.son_mz = son_mz;
    }

    /**
     * 
     * @return
     */
    public String getSon_noStandar() {
        return son_noStandar;
    }

    /**
     * 
     * @param son_noStandar
     */
    public void setSon_noStandar(String son_noStandar) {
        this.son_noStandar = son_noStandar;
    }

    /**
     * 
     * @return
     */
    public String getSon_nomContacto() {
        return son_nomContacto;
    }

    /**
     * 
     * @param son_nomContacto
     */
    public void setSon_nomContacto(String son_nomContacto) {
        this.son_nomContacto = son_nomContacto;
    }

    /**
     * 
     * @return
     */
    public String getSon_nomSolicitante() {
        return son_nomSolicitante;
    }

    /**
     * 
     * @param son_nomSolicitante
     */
    public void setSon_nomSolicitante(String son_nomSolicitante) {
        this.son_nomSolicitante = son_nomSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSon_observaciones() {
        return son_observaciones;
    }

    /**
     * 
     * @param son_observaciones
     */
    public void setSon_observaciones(String son_observaciones) {
        this.son_observaciones = son_observaciones;
    }

    /**
     * 
     * @return
     */
    public String getSon_telContacto() {
        return son_telContacto;
    }

    /**
     * 
     * @param son_telContacto
     */
    public void setSon_telContacto(String son_telContacto) {
        this.son_telContacto = son_telContacto;
    }

    /**
     * 
     * @return
     */
    public String getSon_tipoSolicitud() {
        return son_tipoSolicitud;
    }

    /**
     * 
     * @param son_tipoSolicitud
     */
    public void setSon_tipoSolicitud(String son_tipoSolicitud) {
        this.son_tipoSolicitud = son_tipoSolicitud;
    }

    /**
     * 
     * @return
     */
    public String getSon_zipcode() {
        return son_zipcode;
    }

    /**
     * 
     * @param son_zipcode
     */
    public void setSon_zipcode(String son_zipcode) {
        this.son_zipcode = son_zipcode;
    }

    /**
     * 
     * @return
     */
    public String getSon_nombre_archivo() {
        return son_nombre_archivo;
    }

    /**
     * 
     * @param son_nombre_archivo
     */
    public void setSon_nombre_archivo(String son_nombre_archivo) {
        this.son_nombre_archivo = son_nombre_archivo;
    }

    /**
     * 
     * @return
     */
    public List<SelectItem> getListEstadosFinal() {
        return listEstadosFinal;
    }

    /**
     * 
     * @param listEstadosFinal
     */
    public void setListEstadosFinal(List<SelectItem> listEstadosFinal) {
        this.listEstadosFinal = listEstadosFinal;
    }

    /**
     * 
     * @return
     */
    public String getSolRed_gpo_nombre() {
        return solRed_gpo_nombre;
    }

    /**
     * 
     * @param solRed_gpo_nombre
     */
    public void setSolRed_gpo_nombre(String solRed_gpo_nombre) {
        this.solRed_gpo_nombre = solRed_gpo_nombre;
    }

    /**
     * 
     * @return
     */
    public String getSre_estado() {
        return sre_estado;
    }

    /**
     * 
     * @param sre_estado
     */
    public void setSre_estado(String sre_estado) {
        this.sre_estado = sre_estado;
    }

    /**
     * 
     * @return
     */
    public String getSre_observaciones() {
        return sre_observaciones;
    }

    /**
     * 
     * @param sre_observaciones
     */
    public void setSre_observaciones(String sre_observaciones) {
        this.sre_observaciones = sre_observaciones;
    }

    /**
     * 
     * @return
     */
    public String getSre_usuario_creacion() {
        return sre_usuario_creacion;
    }

    /**
     * 
     * @param sre_usuario_creacion
     */
    public void setSre_usuario_creacion(String sre_usuario_creacion) {
        this.sre_usuario_creacion = sre_usuario_creacion;
    }

    /**
     * 
     * @return
     */
    public String getSre_estadoFinal() {
        return sre_estadoFinal;
    }

    /**
     * 
     * @param sre_estadoFinal
     */
    public void setSre_estadoFinal(String sre_estadoFinal) {
        this.sre_estadoFinal = sre_estadoFinal;
    }

    /**
     * 
     * @return
     */
    public boolean isDisableBoton() {
        return disableBoton;
    }

    /**
     * 
     * @param disableBoton
     */
    public void setDisableBoton(boolean disableBoton) {
        this.disableBoton = disableBoton;
    }

    /**
     * 
     * @return
     */
    public boolean isShowQueryRed() {
        return showQueryRed;
    }

    /**
     * 
     * @param showQueryRed
     */
    public void setShowQueryRed(boolean showQueryRed) {
        this.showQueryRed = showQueryRed;
    }

    /**
     * 
     * @return
     */
    public int getScrollerPageRed() {
        return scrollerPageRed;
    }

    /**
     * 
     * @param scrollerPageRed
     */
    public void setScrollerPageRed(int scrollerPageRed) {
        this.scrollerPageRed = scrollerPageRed;
    }

    /**
     * 
     * @return
     */
    public boolean isShowQueryNeg() {
        return showQueryNeg;
    }

    /**
     * 
     * @param showQueryNeg
     */
    public void setShowQueryNeg(boolean showQueryNeg) {
        this.showQueryNeg = showQueryNeg;
    }

    /**
     * 
     * @return
     */
    public int getScrollerPageNeg() {
        return scrollerPageNeg;
    }

    /**
     * 
     * @param scrollerPageNeg
     */
    public void setScrollerPageNeg(int scrollerPageNeg) {
        this.scrollerPageNeg = scrollerPageNeg;
    }

    /**
     * 
     * @return
     */
    public String getSolNegArchGenerado() {
        return solNegArchGenerado;
    }

    /**
     * 
     * @param solNegArchGenerado
     */
    public void setSolNegArchGenerado(String solNegArchGenerado) {
        this.solNegArchGenerado = solNegArchGenerado;
    }

    /**
     * 
     * @return
     */
    public String getSeleccione() {
        return seleccione;
    }

    /**
     * 
     * @param seleccione
     */
    public void setSeleccione(String seleccione) {
        this.seleccione = seleccione;
    }

    /**
     * 
     * @return
     */
    public boolean isShowBotonActualizar() {
        return showBotonActualizar;
    }

    /**
     * 
     * @param showBotonActualizar
     */
    public void setShowBotonActualizar(boolean showBotonActualizar) {
        this.showBotonActualizar = showBotonActualizar;
    }

    /**
     * 
     * @return
     */
    public String getMessage2() {
        return message2;
    }

    /**
     * 
     * @param message2
     */
    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    /**
     * 
     * @return
     */
    public String getSre_nombre_archivo() {
        return sre_nombre_archivo;
    }

    /**
     * 
     * @param sre_nombre_archivo
     */
    public void setSre_nombre_archivo(String sre_nombre_archivo) {
        this.sre_nombre_archivo = sre_nombre_archivo;
    }

    /**
     * 
     * @return
     */
    public String getSolRedArchivoGeneradoRr() {
        return solRedArchivoGeneradoRr;
    }

    /**
     * 
     * @param solRedArchivoGeneradoRr
     */
    public void setSolRedArchivoGeneradoRr(String solRedArchivoGeneradoRr) {
        this.solRedArchivoGeneradoRr = solRedArchivoGeneradoRr;
    }

    /**
     * 
     * @return
     */
    public boolean isShowTablaNegocio() {
        return showTablaNegocio;
    }

    /**
     * 
     * @param showTablaNegocio
     */
    public void setShowTablaNegocio(boolean showTablaNegocio) {
        this.showTablaNegocio = showTablaNegocio;
    }

    /**
     * 
     * @return
     */
    public boolean isShowTablaRed() {
        return showTablaRed;
    }

    /**
     * 
     * @param showTablaRed
     */
    public void setShowTablaRed(boolean showTablaRed) {
        this.showTablaRed = showTablaRed;
    }

    /**
     * 
     * @return
     */
    public boolean isShowPopUp() {
        return showPopUp;
    }

    /**
     * 
     * @param showPopUp
     */
    public void setShowPopUp(boolean showPopUp) {
        this.showPopUp = showPopUp;
    }

    /**
     * 
     * @return
     */
    public boolean isShowBotonConfirmar() {
        return showBotonConfirmar;
    }

    /**
     * 
     * @param showBotonConfirmar
     */
    public void setShowBotonConfirmar(boolean showBotonConfirmar) {
        this.showBotonConfirmar = showBotonConfirmar;
    }
}
