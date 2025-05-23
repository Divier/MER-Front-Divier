package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.delegate.ProcesarSolModHHPPSolRedDelegate;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.delegate.SolicitudesDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.ConsultaSolRedModificacion;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase ProcesarSolModHHPPSolRedMBean
 * Extiende de BaseMBean
 * Implementa Serialización
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
@ManagedBean(name = "procesarSolModHHPPSolRedMBean")
@ViewScoped
public class ProcesarSolModHHPPSolRedMBean extends BaseMBean implements Serializable {

    /**
     * 
     */
    public static final String NOMBRE_FUNCIONALIDAD = "PROCESAR MODIFICACION DE HHPP PLANEACION DE RED";
    private List<ConsultaSolRedModificacion> solicitudeRedCreadas = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> solicitudeRedRechazadas = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> solicitudeRedAProcesar = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> detalleSolCreadasTemporal = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> detalleSolAProcesarTemporal = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> detallesSolAProcesar = new ArrayList<ConsultaSolRedModificacion>();
    private List<ConsultaSolRedModificacion> detallesSolAExpotar = new ArrayList<ConsultaSolRedModificacion>();
    private int cantidadSolCreadas;
    int cantidadDetallesCreadasTemporal = 0;
    int cantidadDetallesAProcesarTemporal = 0;
    private String messageUser;
    /**
     * 
     */
    public boolean showPopUp = false;
    private boolean showBotonProcesar = true;
    private boolean showBotonConfirmar = false;
    private boolean showBotonDescargar = false;
    private boolean showTablaResultado;
    //Scroll de la tabla de resultados
    private int scrollerPage = 1;
    private String fileName = "";
    private int flag = 0;
    private static final Logger LOGGER = LogManager.getLogger(ProcesarSolModHHPPSolRedMBean.class);

    /**
     * 
     */
    public ProcesarSolModHHPPSolRedMBean() {
        if (flag == 0) {
            doProcesar();
        }
    }

    /**
     * Procesa las solicitudes de Red en estado CREADA
     */
    public final void doProcesar() {

        //Generación del nobre del archivo
        StringBuilder nombreArchivo = new StringBuilder("ADCREDMODIFICACION");
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        nombreArchivo.append(sdf.format(hoy));
        this.fileName = nombreArchivo.toString();

        try {
            solicitudeRedCreadas = ProcesarSolModHHPPSolRedDelegate.querySolicitudesRedCreadas();
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doProcesar()" + e.getMessage(), e, LOGGER);
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
        if (solicitudeRedCreadas != null) {
            cantidadSolCreadas = solicitudeRedCreadas.size();
        }
        if (solicitudeRedCreadas != null) {
            //Se rrecorren cada una de las solicitudes
            for (ConsultaSolRedModificacion solicitudesCreadas : solicitudeRedCreadas) {
                //Se cargan los detalles de solicitud creados en cada solRed    
                try {
                    detalleSolCreadasTemporal = ProcesarSolModHHPPSolRedDelegate.queryDetalleSolicitudBySolRedId(solicitudesCreadas.getId_SolRed().toString());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doProcesar()" + e.getMessage(), e, LOGGER);
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                }

                if (detalleSolCreadasTemporal != null) {
                    cantidadDetallesCreadasTemporal = detalleSolCreadasTemporal.size();
                }

                //Se cargan los detalles de solicitud VALIDOS (con nodos validos) en cada solRed    
                try {
                    detalleSolAProcesarTemporal = ProcesarSolModHHPPSolRedDelegate.queryDetalleSolicitudBySolRedIdValidos(solicitudesCreadas.getId_SolRed().toString());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doProcesar()" + e.getMessage(), e, LOGGER);
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                }

                if (detalleSolAProcesarTemporal != null) {
                    cantidadDetallesAProcesarTemporal = detalleSolAProcesarTemporal.size();
                }

                if (cantidadDetallesAProcesarTemporal != 0 && cantidadDetallesAProcesarTemporal == cantidadDetallesCreadasTemporal) {
                    //todos los detalles asociados a esta solicitud son válidos
                    solicitudeRedAProcesar.add(solicitudesCreadas);
                } else {
                    solicitudesCreadas.setMensaje("La solicitud tiene detalles con nodos inválidos.");
                    solicitudeRedRechazadas.add(solicitudesCreadas);
                }
            }

            //No existen solicitudes para procesar
            if (cantidadSolCreadas < 1) {
                messageUser = "No hay solicitudes para procesar.";
                showBotonProcesar = false;
                showBotonConfirmar = false;
                showTablaResultado = false;
            } else {
                messageUser = "Existe (n) " + cantidadSolCreadas + " solicitud (es) para procesar.";
            }
        } else {
            messageUser = "No hay solicitudes para procesar.";
            showBotonProcesar = false;
            showBotonConfirmar = false;
            showTablaResultado = false;
            showBotonDescargar = false;

        }
    }

    /**
     * Procesa las solicitudes de Red en estado CREADA
     */
    public void doHabilitarConfirmar() {
        //Se habilita el PopUp y el boton de confirmar
        showBotonConfirmar = true;
        showPopUp = true;

    }

    /**
     * Action del botón procesar el cual procesa las solicitudes en estado CREADA
     * @throws ApplicationException 
     */
    public void doProcesarSolicitudesCreadas()  {

        message = "";
        //Se habilita la tabla de resultados  
        if (solicitudeRedRechazadas != null && solicitudeRedRechazadas.size() > 0) {
            showTablaResultado = true;
        } else {
            showTablaResultado = false;
        }

        if (solicitudeRedAProcesar != null) {
            boolean hayError = false;
            showPopUp = false;
            Hhpp hhppFromRed = new Hhpp();
            int solicitudesProcesadasOk = 0;
            //Se recorren las solicitudes validas para procesarlas individualmente
            for (ConsultaSolRedModificacion solicitudAProcesar : solicitudeRedAProcesar) {

                //Se cargan los detalles de solicitud VALIDOS (con nodos validos) en cada solRed    
                try {
                    detallesSolAProcesar = ProcesarSolModHHPPSolRedDelegate.queryDetalleSolicitudBySolRedIdValidos(solicitudAProcesar.getId_SolRed().toString());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                 } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doProcesarSolicitudesCreadas()" + e.getMessage(), e, LOGGER);
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                 }

                for (ConsultaSolRedModificacion detalleSolAprocesar : detallesSolAProcesar) {
                    BigDecimal subdirIdDir = null;
                    try {
                        String id_hhpp = (detalleSolAprocesar.getId_hhpp() != null) ? detalleSolAprocesar.getId_hhpp().toString() : "";
                        String id_nodoFromSolicitud = (detalleSolAprocesar.getId_nodo() != null) ? detalleSolAprocesar.getId_nodo().toString() : "";
                        String estadoHhppFromSolicitud = detalleSolAprocesar.getDso_estado_hhpp();
                        EstadoHhpp estadoFromHhpp = ProcesarSolModHHPPSolRedDelegate.queryEstadoHhpp(estadoHhppFromSolicitud);

                        //Consultar el HHPP
                        //Se cargan los datos del hhpp
                        hhppFromRed = ProcesarSolModHHPPSolRedDelegate.queryHhppById(id_hhpp);
                        if (hhppFromRed != null) {
                            //Se asignan los valores que son tomados del HHPP - Comunidad(Nodo), TipoHhpp, Lista de Marcas
                            if (hhppFromRed.getTipoHhpp() != null) {
                                detalleSolAprocesar.setTipoHhppFromHhpp(hhppFromRed.getTipoHhpp().getThhValor());
                            }
                            //Se asigna el valor de la comunidad por parte del HHPP                        
                            if (hhppFromRed.getNodo() != null && hhppFromRed.getNodo().getComunidad() != null) {
                                detalleSolAprocesar.setComunidadFromHhpp(hhppFromRed.getNodo().getComunidad().getComId());
                            }
                            //Se asignan las marcas del HHPP
                            if (hhppFromRed.getMarcas() != null) {
                                detalleSolAprocesar.setListaMarcas(hhppFromRed.getMarcas());
                            }
                            //Tomar la comunidad nueva desde el nodo asociado al detalle_solicitud
                            Nodo nodoFromSolicitud = null;
                            
                            nodoFromSolicitud = SolicitudRedDelegate.queryNodoById(id_nodoFromSolicitud);
                        
                            String comunidadFromNodo = "";
                            if (nodoFromSolicitud != null) {
                                //Se asigna la comunidad asociada al nodo (Nueva Comunidad)
                                if (nodoFromSolicitud.getComunidad() != null) {
                                    comunidadFromNodo = nodoFromSolicitud.getComunidad().getComId();
                                }
                                //Se asigna el codigo del nodo
                                detalleSolAprocesar.setCod_nodo(nodoFromSolicitud.getNodCodigo());
                                //Se asigna el tipo de nodo
                                detalleSolAprocesar.setTipoNodoFromNodo(nodoFromSolicitud.getNodTipo().toString());
                                //Se asigna el id del Geografico Politico del nodo
                                if (nodoFromSolicitud.getGeograficoPolitico() != null
                                        && nodoFromSolicitud.getGeograficoPolitico().getGpoId() != null) {

                                    detalleSolAprocesar.setIdGpoFromNodo(nodoFromSolicitud.getGeograficoPolitico().getGpoId().toString());
                                }
                            }
                            detalleSolAprocesar.setComunidadFromNodo(comunidadFromNodo);
                            //Se actualizan los datos del HHPP
                            hayError = actualizarHhpp(id_nodoFromSolicitud, id_hhpp, hhppFromRed, estadoFromHhpp, hayError);

                            //Se obtiene el Id de la Direccion asociada al HHPP para actualizarla

                            if (hhppFromRed.getSubDireccion() != null) {
                                subdirIdDir = hhppFromRed.getSubDireccion().getSdiId();
                            }
                            Direccion idDireccion = hhppFromRed.getDireccion();
                            AddressEJB addressEJB = new AddressEJB();
                            addressEJB.updateAddressEstrato(detalleSolAprocesar.getDso_estrato(), idDireccion, subdirIdDir, NOMBRE_FUNCIONALIDAD, this.user.getUsuLogin());
                        }
                    } catch (ApplicationException | IOException ex) {
                        message = "Error al procesar las solicitudes.";
                        showBotonConfirmar = false;
                        showBotonDescargar = false;
                        LOGGER.error(message.concat(ex.getMessage()), ex);
                        hayError = true;
                    } catch (Exception e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Se produjo un error al momento de procesar las solicitudes creadas: " + e.getMessage(), e, LOGGER);
                    }
                    //Si no hay error se agrega a la coleccion que se va a exportar para RR
                    try {
                        if (!hayError) {
                            ConsultaSolRedModificacion detalleSolAprocesarAux = complementarSalidaModHHPP(detalleSolAprocesar, subdirIdDir);
                            detallesSolAExpotar.add(detalleSolAprocesarAux);
                        }
                    } catch (Exception e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                        FacesUtil.mostrarMensajeError("Error al momento de procesar las solicitudes creadas: " + e.getMessage(), e, LOGGER);
                    }
                }//Fin del for de los detalles

                //Se consulta la Solicitud de Red para actualizarla
                SolicitudRed solRed = null;
                try {
                    solRed = SolicitudesDelegate.querySolRedById(solicitudAProcesar.getId_SolRed().toString());
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg, e);
                     hayError = true;
                } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg, e);
                    FacesUtil.mostrarMensajeError("Se produjo un error al momento de procesar las solicitudes creadas: " + e.getMessage(), e, LOGGER);
                }

                //Se actualiza el estado de la solicitud de red y el nombre del archivo generado
                if (!hayError && solRed != null) {
                    try {
                        message = ProcesarSolModHHPPSolRedDelegate.updateSolicitudRedById(Constant.ESTADO_SRE_ARCH_GENERADO,
                                fileName, this.user.getUsuNombre(), NOMBRE_FUNCIONALIDAD, solRed);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg, e);
                     } catch (Exception e) {
                        FacesUtil.mostrarMensajeError("Se produjo un error al momento de procesar las solicitudes creadas: " + e.getMessage(), e, LOGGER);
                        String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                     }
                    solicitudesProcesadasOk++;
                }

            }//Fin del for de solicitudes

            if (!hayError) {
                showBotonProcesar = false;
                showBotonConfirmar = false;
                showTablaResultado = true;
                if (!detallesSolAExpotar.isEmpty()) {
                    messageUser = "Se procesó exitosamente " + solicitudesProcesadasOk + " solicitud (es).";
                    showBotonDescargar = true;
                } else {
                    messageUser = "No se procesó ninguna solicitud.";
                }
            } else {
                messageUser = "Ocurrió un error durante el procesamiento de las solicitudes.";
                showBotonProcesar = true;
                showBotonConfirmar = false;
                showBotonDescargar = false;
                showTablaResultado = false;
            }
        }
        
      
    }

    /**
     * 
     * @param dirFromHHPP
     * @param hayError
     * @return 
     */
    private boolean actualizarDireccion(Direccion dirFromHHPP, boolean hayError) {
        try {
            message = ProcesarSolModHHPPSolRedDelegate.updateDireccion(dirFromHHPP, NOMBRE_FUNCIONALIDAD, this.user.getUsuNombre());
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            hayError = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : actualizarDireccion()" + e.getMessage(), e, LOGGER);
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
        return hayError;
    }

    /**
     * 
     * @param detalleAConsultar
     * @param subdirIdDir
     * @return
     * @throws ApplicationException 
     */
    private ConsultaSolRedModificacion complementarSalidaModHHPP(ConsultaSolRedModificacion detalleAConsultar, BigDecimal subdirIdDir)  {
        try {
            if (detalleAConsultar != null) {
                detalleAConsultar = ProcesarSolModHHPPSolRedDelegate.complementarSalidaModHHPP(detalleAConsultar, subdirIdDir);
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : complementarSalidaModHHPP()" + e.getMessage(), e, LOGGER);
        }
        return detalleAConsultar;
    }

    /**
     * Actualiza las propiedades del HHPP pasado por parámetro
     * @param id_nodoFromSolicitud
     * @param id_hhpp
     * @param hhppFromRed
     * @param estadoFromHhpp
     * @param hayError
     * @return
     * @throws ApplicationException 
     */
    private boolean actualizarHhpp(String id_nodoFromSolicitud, String id_hhpp, Hhpp hhppFromRed, EstadoHhpp estadoFromHhpp, boolean hayError)  {
      try{
        //Se actualiza el id del nodo
        if (!id_nodoFromSolicitud.isEmpty() && !id_hhpp.isEmpty()) {

            if (estadoFromHhpp != null) {
                //Se actualiza el estado de HHPP
                hhppFromRed.setEstadoHhpp(estadoFromHhpp);
            }
            //Se actualiza el Nodo del HHPP
            Nodo nodo = new Nodo();
            nodo.setNodId(new BigDecimal(id_nodoFromSolicitud));
            hhppFromRed.setNodo(nodo);
            //Se actualiza el nodo en el HHPP con el nodo que viene en la solicitud
            message = ProcesarSolModHHPPSolRedDelegate.updateHhpp(hhppFromRed, NOMBRE_FUNCIONALIDAD, this.user.getUsuNombre());
            showBotonProcesar = false;
            showBotonConfirmar = false;
            showBotonDescargar = true;
        } else {
            hayError = true;
            showBotonConfirmar = false;
            showBotonDescargar = false;
        }
        
        } catch (Exception e) {
          String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
          LOGGER.error(msg);
          FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : actualizarHhpp()" + e.getMessage(), e, LOGGER);
        }
        return hayError;
    }

    /**
     * Action para exportar el archivo generado
     */
    public void doExportarArchivo() {
        flag = 1;

        final StringBuffer sb = new StringBuffer();
        for (ConsultaSolRedModificacion data : detallesSolAExpotar) {
            //Campo cuenta
            sb.append("0");
            sb.append(Constant.SEPARATOR);
            //Campo CALLE
            sb.append("dumyCalle");
            sb.append(Constant.SEPARATOR);
            //Campo PLACA
            sb.append("dumyPlaca");
            sb.append(Constant.SEPARATOR);
            //Campo APTO
            sb.append(data.getDso_apto());
            sb.append(Constant.SEPARATOR);
            //Campo COMUNIDAD
            sb.append(data.getComunidadFromHhpp());
            sb.append(Constant.SEPARATOR);
            //Campo GRID
            sb.append(data.getComunidadFromHhpp());
            sb.append(Constant.SEPARATOR);
            //Campo NUEVA COMUNIDAD
            sb.append(data.getComunidadFromNodo());
            sb.append(Constant.SEPARATOR);
            //Campo NUEVO GRID
            sb.append(data.getComunidadFromNodo());
            sb.append(Constant.SEPARATOR);
            //Campo HE
            sb.append(Constant.FILE_RR_HE);
            sb.append(Constant.SEPARATOR);
            //Campo NUMERO HE
            sb.append("dumyNumeroHE");
            sb.append(Constant.SEPARATOR);
            //Campo CT
            sb.append(Constant.FILE_RR_CT);
            sb.append(Constant.SEPARATOR);
            //Campo NUMERO CT
            sb.append("dumyNumeroCT");
            sb.append(Constant.SEPARATOR);
            //Campo SIGLA_ND
            sb.append(Constant.FILE_RR_SIGLA_ND);
            sb.append(Constant.SEPARATOR);
            //Campo NODO
            sb.append(data.getCod_nodo());
            sb.append(Constant.SEPARATOR);
            //Campo BARRIO
            sb.append(data.getGeo_barrio());
            sb.append(Constant.SEPARATOR);
            //Campo ESTRATO
            sb.append(data.getDso_estrato());
            sb.append(Constant.SEPARATOR);
            //Campo Dirección Alterna
            sb.append(data.getDso_dir_alt_sta());
            sb.append(Constant.SEPARATOR);
            //Campo LOCALIDAD
            sb.append(data.getGeo_localidad());
            sb.append(Constant.SEPARATOR);
            //Se aplica la regla de traslape nodos
            //TODO: ELIMINAR implementacion dummy
            data.setDso_nd1_geo("nodo1");
            data.setDso_nd2_geo("nodo2");
            data.setDso_nd3_geo("nodo3");
            String traslapeNodos = "";
            traslapeNodos = generarTraslapeNodos(data, traslapeNodos);
            //Campo TRASLAPE NODOS
            sb.append(traslapeNodos);
            sb.append(Constant.SEPARATOR);
            //Campo Manzana
            sb.append(data.getGeo_manzana());
            sb.append(Constant.SEPARATOR);
            //Campo ZIP CODE
            sb.append("dummyZipCode");
            sb.append(Constant.SEPARATOR);
            //Campo ESTADO UNIDAD
            sb.append(data.getDso_estado_hhpp());
            sb.append(Constant.SEPARATOR);
            //Campo TIPO UNIDAD
            sb.append(data.getTipoHhppFromHhpp());
            sb.append(Constant.SEPARATOR);
            //Campo TIPO ACOMETIDA
            sb.append(data.getTipoacometida());
            sb.append(Constant.SEPARATOR);
            //Campo TIPO CABLE
            sb.append(data.getTipoconexion());
            sb.append(Constant.SEPARATOR);
            //Campo Propietario CABLE
            sb.append("dumyPropietarioCable");
            sb.append(Constant.SEPARATOR);
            //Campo VENDEDOR
            sb.append(Constant.FILE_RR_VENDEDOR);
            sb.append(Constant.SEPARATOR);
            //BLACK LIST 1,2... 9
            int contadorMarcas = 0;
            for (Marcas marcas : data.getListaMarcas()) {
                if (contadorMarcas == 9) {
                    break;
                }
                sb.append(marcas.getMarCodigo());
                sb.append(Constant.SEPARATOR);
                contadorMarcas++;
            }
            while (contadorMarcas < 9) {
                sb.append("");
                sb.append(Constant.SEPARATOR);
                contadorMarcas++;
            }
            //INFORMACION ADICIONAL 1,2... 10
            sb.append("\n");
        }

        byte[] csvData = null;
        // in case you need some specific charset : 
        // here is an exemple with some standard utf-8 

        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException ex) {
            message = "Error en la generación del archivo.";
            LOGGER.error(message + ex.getMessage(), ex);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doExportarArchivo()" + e.getMessage(), e, LOGGER);
        }
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();


        response.setHeader("Content-disposition", "attached; filename=\"" + fileName + ".csv\"");
        // provided you want to ensure the file will be downloaded 
        // up to you to do it another way if you don't mind that the navigator 
        // manages the file on itself
        response.setContentType("application/force.download");
        try {
            response.getOutputStream().write(csvData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            //Telling the framework that the response has been completed. 
            FacesContext.getCurrentInstance().responseComplete();
            showBotonDescargar = false;
        } catch (final IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            FacesUtil.mostrarMensajeError("Error en ProcesarSolModHHPPSolRedMBean : doExportarArchivo()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Genera el traslapeNodos de acuerdo a la logica definida
     * @param data Objeto con la información del detalle de la solicitud
     * @return 
     */
    private String generarTraslapeNodos(ConsultaSolRedModificacion data, String traslapeNodos) {

        //Nodo pertenece a bogota id=54
        if (data.getIdGpoFromNodo() != null) {
            if (data.getIdGpoFromNodo().equals("54")) {
                if (data.getTipoNodoFromNodo().equals(Constant.NODO_BIDIRECCIONAL)) {
                    traslapeNodos = data.getCod_nodo();
                } else if (data.getTipoNodoFromNodo().equals(Constant.NODO_UNIDIRECCIONAL)) {
                    //Si el nodo del detalle conincide con alguno de los del georeferenciador
                    if (data.getCod_nodo().equals(data.getDso_nd1_geo())
                            || data.getCod_nodo().equals(data.getDso_nd2_geo())
                            || data.getCod_nodo().equals(data.getDso_nd3_geo())) {
                        traslapeNodos = "NG-" + data.getDso_nd2_geo() + "-" + data.getCod_nodo();
                    } else {
                        traslapeNodos = data.getDso_nd1_geo() + "-" + data.getDso_nd2_geo() + "-" + data.getDso_nd3_geo();
                    }
                }
            } else {
                //Otras ciudades diferentes a bogota
                traslapeNodos = (data.getTipoNodoFromNodo().equals(Constant.NODO_BIDIRECCIONAL)) ? data.getCod_nodo() : "NG-" + data.getCod_nodo();
            }
        }

        return traslapeNodos;
    }

    /**
     * 
     * @return
     */
    public int getCantidadSolCreadas() {
        return cantidadSolCreadas;
    }

    /**
     * 
     * @param cantidadSolCreadas
     */
    public void setCantidadSolCreadas(int cantidadSolCreadas) {
        this.cantidadSolCreadas = cantidadSolCreadas;
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

    /**
     * 
     * @return
     */
    public boolean isShowBotonProcesar() {
        return showBotonProcesar;
    }

    /**
     * 
     * @param showBotonProcesar
     */
    public void setShowBotonProcesar(boolean showBotonProcesar) {
        this.showBotonProcesar = showBotonProcesar;
    }

    /**
     * 
     * @return
     */
    public List<ConsultaSolRedModificacion> getSolicitudeRedAProcesar() {
        return solicitudeRedAProcesar;
    }

    /**
     * 
     * @param solicitudeRedAProcesar
     */
    public void setSolicitudeRedAProcesar(List<ConsultaSolRedModificacion> solicitudeRedAProcesar) {
        this.solicitudeRedAProcesar = solicitudeRedAProcesar;
    }

    /**
     * 
     * @return
     */
    public List<ConsultaSolRedModificacion> getSolicitudeRedCreadas() {
        return solicitudeRedCreadas;
    }

    /**
     * 
     * @param solicitudeRedCreadas
     */
    public void setSolicitudeRedCreadas(List<ConsultaSolRedModificacion> solicitudeRedCreadas) {
        this.solicitudeRedCreadas = solicitudeRedCreadas;
    }

    /**
     * 
     * @return
     */
    public List<ConsultaSolRedModificacion> getSolicitudeRedRechazadas() {
        return solicitudeRedRechazadas;
    }

    /**
     * 
     * @param solicitudeRedRechazadas
     */
    public void setSolicitudeRedRechazadas(List<ConsultaSolRedModificacion> solicitudeRedRechazadas) {
        this.solicitudeRedRechazadas = solicitudeRedRechazadas;
    }

    /**
     * 
     * @return
     */
    public boolean isShowTablaResultado() {
        return showTablaResultado;
    }

    /**
     * 
     * @param showTablaResultado
     */
    public void setShowTablaResultado(boolean showTablaResultado) {
        this.showTablaResultado = showTablaResultado;
    }

    /**
     * 
     * @return
     */
    public int getScrollerPage() {
        return scrollerPage;
    }

    /**
     * 
     * @param scrollerPage
     */
    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }

    /**
     * 
     * @return
     */
    public boolean isShowBotonDescargar() {
        return showBotonDescargar;
    }

    /**
     * 
     * @param showBotonDescargar
     */
    public void setShowBotonDescargar(boolean showBotonDescargar) {
        this.showBotonDescargar = showBotonDescargar;
    }

    /**
     * 
     * @return
     */
    public String getMessageUser() {
        return messageUser;
    }

    /**
     * 
     * @param messageUser
     */
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
}