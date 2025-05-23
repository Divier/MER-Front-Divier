/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SolicitudManager;
import co.com.claro.mgl.dao.impl.cm.CmtAvisoProgramadoHistoMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtAvisoProgramadoMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jms.ProductorMensajesTcrmFacade;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.rest.dtos.CmtAddressResponseDto;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 *
 * @author rodriguezluim
 */
public class CmtAvisoProgramadoMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtAvisoProgramadoMglManager.class);
    public static final String RZ_HHPP = "Cambio de HHPP a estado solicitado";
    public static final String RZ_CCMM = "Cambio de CCMM a estado solicitado";
    public static final String SI = "Y";
    public static final String NO = "N";
    public static final String ESTADO_FINALIZADO = "FINALIZADO";
    public static final String ESTADO_PENDIENTE = "PENDIENTE";

    public CmtAvisoProgramadoMglManager() {
    }

    /**
     * Crear un aviso programado para un HHPP.Este metodo permite crear un
 aviso para cuando un HHPP cambie de estado a disponible genere un aviso a
 TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param capm
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    public CmtDefaultBasicResponse crearAvisoProgramadoHhpp(CmtAvisosProgramadosMgl capm) {

        CmtDefaultBasicResponse cmtDefaultBasicResponse = new CmtAddressResponseDto();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        HhppMglManager hhppMglManager = new HhppMglManager();

        try {
            if (capm.getHhppId() == null && capm.getTecnologiaSubedificioId() == null) {
                cmtDefaultBasicResponse.setMessageType("E");
                cmtDefaultBasicResponse.setMessage("Es necesario ingresar el HhppId o el tecnologiaSubEdificioId para la creacion del aviso programado.");
                return cmtDefaultBasicResponse;
            }
            if (capm.getCasoTcrmId() == null || capm.getCasoTcrmId().equals("")) {
                cmtDefaultBasicResponse.setMessageType("E");
                cmtDefaultBasicResponse.setMessage("El CasoTcrmId es necesario para la creacion del aviso programado.");
                return cmtDefaultBasicResponse;
            }
            if (capm.getTecnologia() == null || capm.getTecnologia().equals("")) {
                cmtDefaultBasicResponse.setMessageType("E");
                cmtDefaultBasicResponse.setMessage("La tecnologia es necesaria para la creacion del aviso programado.");
                return cmtDefaultBasicResponse;
            }
            if (capm.getHhppId() != null && !capm.getHhppId().equals(BigDecimal.ZERO)) {
                HhppMgl hhppMgl = hhppMglManager.findById(capm.getHhppId());
                if (hhppMgl == null) {
                    cmtDefaultBasicResponse.setMessageType("E");
                    cmtDefaultBasicResponse.setMessage("No se encuentra el nodo con el identificador " + capm.getHhppId());
                    return cmtDefaultBasicResponse;
                }
            }
            if (capm.getTecnologiaSubedificioId() != null && !capm.getTecnologiaSubedificioId().equals(BigDecimal.ZERO)) {
                CmtTecnologiaSubMgl cmtTecnologiaSub = cmtTecnologiaSubMglManager.findIdTecnoSub(capm.getTecnologiaSubedificioId());
                if (cmtTecnologiaSub == null) {
                    cmtDefaultBasicResponse.setMessageType("E");
                    cmtDefaultBasicResponse.setMessage("No se encuentra el SubEdificio o CCMM con el identificador " + capm.getHhppId());
                    return cmtDefaultBasicResponse;
                }
            }
            if (capm.getSolicitudId() != null && !capm.getSolicitudId().equals(BigDecimal.ZERO)) {
                SolicitudManager solicitudManager = new SolicitudManager();
                Solicitud solicitud = solicitudManager.findById(capm.getSolicitudId());

                if (solicitud == null) {
                    cmtDefaultBasicResponse.setMessageType("E");
                    cmtDefaultBasicResponse.setMessage("No se encuentra la solicitud con el identificador " + capm.getSolicitudId());
                    return cmtDefaultBasicResponse;
                }
            }


            if (capm.getUsuarioCreacion() == null || capm.getUsuarioCreacion().isEmpty()) {
                capm.setUsuarioCreacion("USUARIOTEMPORAL");
                capm.setFechaCreacion(new Date());
                capm.setEstadoRegistro(1);
            }
            CmtAvisoProgramadoMglDaoImpl cmtAvisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            capm = cmtAvisoProgramadoMglDaoImpl.create(capm);

            cmtDefaultBasicResponse.setMessageType("I");
            cmtDefaultBasicResponse.setMessage("Se creo el aviso programado con exito. El identificador de este es " + capm.getAvisoId());
            return cmtDefaultBasicResponse;

        } catch (ApplicationException e) {
            LOGGER.error("Error en crearAvisoProgramadoHhpp. " +e.getMessage(), e);      
            cmtDefaultBasicResponse.setMessageType("E");
            cmtDefaultBasicResponse.setMessage("Se genero una excepcion en el metodo principal." + e.getMessage());
            return cmtDefaultBasicResponse;
        }
    }

    /**
     * Buscar avisos Programados por id del hhpp: busca en la tabla CMT avisos
     * programados los que contengan el id del hhpp para poder notificar a TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl
     * @param hhppId identificador del hhpp
     */
    public void enviarNotificacionByHhpp(HhppMgl hhppMgl) {
        List<CmtAvisosProgramadosMgl> listaAvisosProgramados = null;
        try {
            CmtAvisoProgramadoMglDaoImpl avisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            listaAvisosProgramados = avisoProgramadoMglDaoImpl.findByHhpp(hhppMgl.getHhpId());
            if (listaAvisosProgramados != null && !listaAvisosProgramados.isEmpty()) {
                for (CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl : listaAvisosProgramados) {
                    enviarNotificacionJmsTcrm(cmtAvisosProgramadosMgl, SI, RZ_HHPP);
                    crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en enviarNotificacionByHhpp. " +e.getMessage(), e);      
        }
    }

    /**
     * Buscar avisos Programados por id del hhpp: busca en la tabla CMT avisos
     * programados los que contengan el id del hhpp para poder notificar a TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtTecnologiaSubMgl
     * @param hhppId identificador del hhpp
     */
    public void enviarNotificacionByCcmm(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) {
        List<CmtAvisosProgramadosMgl> listaAvisosProgramados = null;
        try {
            CmtAvisoProgramadoMglDaoImpl avisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            listaAvisosProgramados = avisoProgramadoMglDaoImpl.findByTecnologiaCcmm(cmtTecnologiaSubMgl.getTecnoSubedificioId());
            if (listaAvisosProgramados != null && !listaAvisosProgramados.isEmpty()) {
                for (CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl : listaAvisosProgramados) {
                    enviarNotificacionJmsTcrm(cmtAvisosProgramadosMgl, SI, RZ_CCMM);
                    crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en enviarNotificacionByCcmm. " +e.getMessage(), e); 
        }
    }

    /**
     * Buscar avisos Programados por id de la solicitud: busca en la tabla CMT
     * avisos programados los que contengan el id de la solicitud para poder
     * notificar a TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param solicitud identificador de la solicitud de hhpp
     */
    public void enviarNotificacionBySolicitud(Solicitud solicitud) {
        CmtAvisoProgramadoMglDaoImpl cmtAvisoProgramadoMglDaoImpl;
        List<CmtAvisosProgramadosMgl> listaAvisosProgramados;

        try {

            cmtAvisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            CmtAvisoProgramadoMglDaoImpl avisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            listaAvisosProgramados = avisoProgramadoMglDaoImpl.findBySolicitud(solicitud.getIdSolicitud());

            if (listaAvisosProgramados != null && !listaAvisosProgramados.isEmpty()) {
                for (CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl : listaAvisosProgramados) {

                    //TODO se debe ajustar la solicitud cuando se crea para que guarde el CasoTcrmId 
                    if (solicitud.getCasoTcrmId() == null || solicitud.getCasoTcrmId().isEmpty()) {
                        solicitud.setCasoTcrmId(cmtAvisosProgramadosMgl.getCasoTcrmId());
                    }

                    //valbuenayf inicio ajuste RQ_2_ACTUALIZAR_CASO_SOBRE_TCRM_DESDE_MGL

                    if (solicitud.getCambioDir() != null && !solicitud.getCambioDir().trim().isEmpty()
                            && solicitud.getRptGestion() != null && !solicitud.getRptGestion().trim().isEmpty()) {

                        if (solicitud.getCambioDir().equals(Constant.RR_DIR_CREA_HHPP_0)) {//solicitud inicio tipo crear HHPP

                            if (solicitud.getRptGestion().equalsIgnoreCase(Constant.CREAR_HHPP_VERIFICACION_AGENDADA)
                                    && !solicitud.getEstado().trim().isEmpty() && solicitud.getEstado().equals(ESTADO_FINALIZADO)) {

                                OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
                                OtHhppMgl otHhppMgl = otHhppMglManager.findOrdenTrabajoByIdSolicitud(solicitud.getIdSolicitud());

                                CmtAvisosProgramadosMgl cmtAvisosProgramado = new CmtAvisosProgramadosMgl();
                                cmtAvisosProgramado.setEstadoRegistro(1);
                                cmtAvisosProgramado.setFechaCreacion(new Date());
                                if (otHhppMgl != null && otHhppMgl.getOtHhppId() != null) {
                                    cmtAvisosProgramado.setOtHhppId(otHhppMgl.getOtHhppId());
                                } else {
                                    cmtAvisosProgramado.setOtHhppId(null);
                                }
                                cmtAvisosProgramado.setPerfilCreacion(cmtAvisosProgramadosMgl.getPerfilCreacion());
                                cmtAvisosProgramado.setSolicitudId(solicitud.getIdSolicitud());
                                cmtAvisosProgramado.setCasoTcrmId(cmtAvisosProgramadosMgl.getCasoTcrmId());
                                cmtAvisosProgramado.setTecnologia(cmtAvisosProgramadosMgl.getTecnologia());
                                cmtAvisosProgramado.setUsuarioCreacion(solicitud.getUsuario());
                                cmtAvisoProgramadoMglDaoImpl.create(cmtAvisosProgramado);
                                solicitud.setEstado(ESTADO_PENDIENTE);
                                enviarNotificacionJmsTcrm(solicitud, null, null);
                                crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);

                            } else if (solicitud.getRptGestion().equalsIgnoreCase(Constant.CREAR_HHPP) && solicitud.getEstado() != null
                                    && !solicitud.getEstado().trim().isEmpty() && solicitud.getEstado().equals(ESTADO_FINALIZADO)) {
                                // HHPP Creado

                                enviarNotificacionJmsTcrm(solicitud, SI, null);
                                crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);

                            } else {
                                // HHPP rechazado

                                if (solicitud.getEstado() != null
                                        && !solicitud.getEstado().trim().isEmpty() && solicitud.getEstado().equals(ESTADO_FINALIZADO)) {

                                    enviarNotificacionJmsTcrm(solicitud, NO, null);
                                    crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                                }
                            }
                            //solicitud fin tipo crear HHPP
                        } else if (solicitud.getCambioDir().equals(Constant.RR_DIR_CAMBIO_ESTRATO_2) && solicitud.getEstado() != null
                                && !solicitud.getEstado().trim().isEmpty() && solicitud.getEstado().equals(ESTADO_FINALIZADO)) {//solicitud inicio tipo cambio estrato                
                            if (solicitud.getRptGestion().equalsIgnoreCase(Constant.CAMBIO_DE_ESTRATO_REALIZADO)) {
                                enviarNotificacionJmsTcrm(solicitud, SI, null);
                                crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                            } else {
                                enviarNotificacionJmsTcrm(solicitud, NO, null);
                                crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                            }
                        } //solicitud fin tipo cambio estrato
                    }
                    //valbuenayf fin ajuste RQ_2_ACTUALIZAR_CASO_SOBRE_TCRM_DESDE_MGL
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en enviarNotificacionBySolicitud. " +e.getMessage(), e); 
        }
    }

    /**
     * Hilo para verificacion del cambio de estado de un HHPP , CCMM o solicitud
     * Valida los estados contra el paramatro ESTADOS_PROGRAMADO_VALIDACION de
     * la tabla parametros.Si el estado es igual , busca si es necesario
 notificar.
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param parameterThread
     */
    public void verificarCambio(Object parameterThread) {

        List<CmtBasicaMgl> estados;

        try {

            if (parameterThread instanceof HhppMgl) {
                HhppMgl hhppMgl = (HhppMgl) parameterThread;
                String estadoHhpp = hhppMgl.getEhhId().getEhhID();
                estados = findListCmtBasicaMglByCodigoInternoApp(Constant.BASICA_EST_HHPP_TIPO_PRECABLEADO);
                estados.addAll(findListCmtBasicaMglByCodigoInternoApp(Constant.BASICA_EST_HHPP_TIPO_POTENCIAL));
                if (estados != null && !estados.isEmpty() && estadoHhpp != null && !estadoHhpp.trim().isEmpty()) {
                    for (CmtBasicaMgl e : estados) {
                        if (e.getCodigoBasica().trim().equalsIgnoreCase(estadoHhpp.trim())) {
                            enviarNotificacionByHhpp(hhppMgl);
                            break;
                        }
                    }
                }
            }

            if (parameterThread instanceof CmtTecnologiaSubMgl) {
                CmtTecnologiaSubMgl ccmm = (CmtTecnologiaSubMgl) parameterThread;
                String estadoTecnologiaSub = ccmm.getBasicaIdEstadosTec().getCodigoBasica().toString();
                estados = findListCmtBasicaMglByCodigoInternoApp(Constant.BASICA_TIPO_TEC_CABLE);
                if (estados != null && !estados.isEmpty() && estadoTecnologiaSub != null && !estadoTecnologiaSub.trim().isEmpty()) {
                    for (CmtBasicaMgl e : estados) {
                        if (e.getCodigoBasica().trim().equalsIgnoreCase(estadoTecnologiaSub.trim())) {
                            enviarNotificacionByCcmm(ccmm);
                            break;
                        }
                    }
                }
            }

            if (parameterThread instanceof Solicitud) {
                Solicitud solicitud = (Solicitud) parameterThread;
                enviarNotificacionBySolicitud(solicitud);
            }

            //valbuenayf inicio ajuste RQ_2_ACTUALIZAR_CASO_SOBRE_TCRM_DESDE_MGL
            if (parameterThread instanceof OtHhppMgl) {
                OtHhppMgl otHhppMgl = (OtHhppMgl) parameterThread;

                CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl = null;

                boolean flag = false;

                if (otHhppMgl.getOtHhppId() != null && otHhppMgl.getSolicitudId() != null
                        && otHhppMgl.getSolicitudId().getIdSolicitud() != null
                        && otHhppMgl.getEstadoGeneral() != null && otHhppMgl.getEstadoGeneral().getBasicaId() != null
                        && otHhppMgl.getEstadoGeneral().getIdentificadorInternoApp() != null) {


                    CmtAvisoProgramadoMglDaoImpl avisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
                    cmtAvisosProgramadosMgl = avisoProgramadoMglDaoImpl.findByordenTrabajoHhpp(otHhppMgl.getOtHhppId());
                    
                    //TODO se debe ajustar la solicitud cuando se crea para que guarde el CasoTcrmId 
                    if (otHhppMgl.getSolicitudId().getCasoTcrmId() == null
                            || otHhppMgl.getSolicitudId().getCasoTcrmId().isEmpty()) {
                        if (cmtAvisosProgramadosMgl != null) {
                            otHhppMgl.getSolicitudId().setCasoTcrmId(cmtAvisosProgramadosMgl.getCasoTcrmId());
                        }
                        else {
                            LOGGER.error("No fue asignado el CasoTcrmId a la solicitud "+otHhppMgl.getSolicitudId().getIdSolicitud());
                        }
                    }

                    if (otHhppMgl.getEstadoGeneral().getIdentificadorInternoApp().equals(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO)) {

                        if (otHhppMgl.getSolicitudId().getDireccionDetallada() != null
                                && otHhppMgl.getSolicitudId().getDireccionDetallada().getDireccionDetalladaId() != null) {

                            HhppMglManager hhppManager = new HhppMglManager();
                            List<HhppMgl> listaHhpp = null;

                            if (otHhppMgl.getSolicitudId().getDireccionDetallada().getSubDireccion() != null
                                    && otHhppMgl.getSolicitudId().getDireccionDetallada().getSubDireccion().getSdiId() != null) {
                                //validar HHPP por tecnologia subdireccion
                                listaHhpp = hhppManager.findHhppSubDireccion(
                                        otHhppMgl.getSolicitudId().getDireccionDetallada().getSubDireccion().getSdiId());

                            } else if (otHhppMgl.getSolicitudId().getDireccionDetallada().getDireccion() != null
                                    && otHhppMgl.getSolicitudId().getDireccionDetallada().getDireccion().getDirId() != null) {
                                //validar HHPP por tecnologia direccion
                                listaHhpp = hhppManager.findHhppDireccion(
                                        otHhppMgl.getSolicitudId().getDireccionDetallada().getDireccion().getDirId());
                            }
                            // validacion de la tecnologia
                            if (listaHhpp != null && !listaHhpp.isEmpty()) {
                                for (HhppMgl h : listaHhpp) {
                                    if (h.getNodId() != null && h.getNodId().getNodId() != null
                                            && h.getNodId().getNodTipo() != null && h.getNodId().getNodTipo().getBasicaId() != null) {
                                        if (otHhppMgl.getSolicitudId().getTipo() != null
                                                && !otHhppMgl.getSolicitudId().getTipo().trim().isEmpty()
                                                && h.getNodId().getNodTipo().getCodigoBasica().equalsIgnoreCase(
                                                asignarTecnologia(otHhppMgl.getSolicitudId().getTipo()))) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (flag) {
                                enviarNotificacionJmsTcrm(otHhppMgl, SI, Constant.CREAR_HHPP);
                                if (cmtAvisosProgramadosMgl != null && cmtAvisosProgramadosMgl.getAvisoId() != null) {
                                    crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                                }
                            } else {
                                enviarNotificacionJmsTcrm(otHhppMgl, NO, Constant.HHPP_NO_VIABLE);
                                if (cmtAvisosProgramadosMgl != null && cmtAvisosProgramadosMgl.getAvisoId() != null) {
                                    crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                                }
                            }
                        }
                    } else if (otHhppMgl.getEstadoGeneral().getIdentificadorInternoApp().equals(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO)) {
                        enviarNotificacionJmsTcrm(otHhppMgl, NO, Constant.HHPP_NO_VIABLE);
                        if (cmtAvisosProgramadosMgl != null && cmtAvisosProgramadosMgl.getAvisoId() != null) {
                            crearHistoricoEliminarAviso(cmtAvisosProgramadosMgl);
                        }
                    }
                }
            }
            //valbuenayf fin ajuste RQ_2_ACTUALIZAR_CASO_SOBRE_TCRM_DESDE_MGL
        } catch (ApplicationException e) {
            LOGGER.error("Error en verificarCambio. " +e.getMessage(), e); 
        }
    }

    /**
     * valbuenayf metodo para enviar las notificaciones al cola de JMS
     *
     * @param solicitud
     * @param caseCompleted
     */
    private void enviarNotificacionJmsTcrm(Object object, String caseCompleted, String rptGestion) {
        ProductorMensajesTcrmFacade productorMensajesTcrmFacade;
        try {
            LOGGER.error("ENVIANDO LA NOTIFICACION A TCRM");
            productorMensajesTcrmFacade = new ProductorMensajesTcrmFacade();
            productorMensajesTcrmFacade.setUrlJms(asignarUrlJms());
            if (object instanceof Solicitud) {
                Solicitud solicitud = (Solicitud) object;
                productorMensajesTcrmFacade.crearMensaje(solicitud.getCasoTcrmId(), solicitud.getRptGestion(),
                        solicitud.getIdSolicitud().toString(), solicitud.getEstado(), caseCompleted);
            } else if (object instanceof CmtAvisosProgramadosMgl) {
                CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl = (CmtAvisosProgramadosMgl) object;
                productorMensajesTcrmFacade.crearMensaje(cmtAvisosProgramadosMgl.getCasoTcrmId(), rptGestion,
                        cmtAvisosProgramadosMgl.getAvisoId().toString(), ESTADO_FINALIZADO, caseCompleted);
            } else if (object instanceof OtHhppMgl) {
                OtHhppMgl otHhppMgl = (OtHhppMgl) object;
                productorMensajesTcrmFacade.crearMensaje(otHhppMgl.getSolicitudId().getCasoTcrmId(), rptGestion,
                        otHhppMgl.getOtHhppId().toString(), ESTADO_FINALIZADO, caseCompleted);
            }


        } catch (ApplicationException e) {
            LOGGER.error("Error en enviarNotificacionJmsTcm. " +e.getMessage(), e); 
        }
    }

    /**
     * valbuenayf metodo para listar CmtBasicaMgl por identificador interno de
     * la app
     *
     * @param codigoInternoApp
     * @return
     * @throws ApplicationException
     */
    private List<CmtBasicaMgl> findListCmtBasicaMglByCodigoInternoApp(String codigoInternoApp) {
        CmtBasicaMglManager cmtBasicaMglManager;
        List<CmtBasicaMgl> respuesta;
        try {
            cmtBasicaMglManager = new CmtBasicaMglManager();
            respuesta = cmtBasicaMglManager.findListCmtBasicaMglByCodigoInternoApp(codigoInternoApp);
        } catch (ApplicationException e) {
            LOGGER.error("Error en findListCmtBasicaMglByCodigoInternoApp. " +e.getMessage(), e); 
            return null;
        }
        return respuesta;
    }

    /**
     * metodo para asignar la tenologia VTCASA, CREACION HHPP UNIDI
     *
     * @param tecnologia
     * @return
     */
    private String asignarTecnologia(String tecnologia) {
        String respuesta = "";
        try {
            if ("VTCASA".equalsIgnoreCase(tecnologia.trim())) {
                respuesta = "BI";
            } else if ("CREACION HHPP UNIDI".equalsIgnoreCase(tecnologia.trim())) {
                respuesta = "UNI";
            } else {
                respuesta = tecnologia;
            }

        } catch (Exception e) {
            LOGGER.error("Error en asignarTecnologia. " +e.getMessage(), e);      
        }
        return respuesta;
    }

    private void crearHistoricoEliminarAviso(CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl) {

        CmtAvisoProgramadoMglDaoImpl cmtAvisoProgramadoMglDaoImpl;
        CmtAvisoProgramadoHistoMglDaoImpl cmtAvisoProgramadoHistoMglDaoImpl;
        try {

            cmtAvisoProgramadoHistoMglDaoImpl = new CmtAvisoProgramadoHistoMglDaoImpl();
            cmtAvisoProgramadoHistoMglDaoImpl.createAvisoProgramadoHistorico(cmtAvisosProgramadosMgl);

            cmtAvisoProgramadoMglDaoImpl = new CmtAvisoProgramadoMglDaoImpl();
            cmtAvisoProgramadoMglDaoImpl.delete(cmtAvisosProgramadosMgl);

        } catch (ApplicationException e) {
            LOGGER.error("Error en crearHistoricoEliminarAviso de  CmtAvisoProgramadoMglManager: " + e);
        }
    }

    /**
     * valbuenayf metodo para asignar la url del servidor de jsm
     *
     * @return
     */
    private String asignarUrlJms() {
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametrosMgl;
        String respuesta = null;
        try {
            Initialized.getInstance();
            parametrosMgl = parametrosMglManager.findParametroMgl(Constant.URL_TCRM_JMS);
            if (parametrosMgl != null && parametrosMgl.getParValor() != null
                    && !parametrosMgl.getParValor().isEmpty()) {
                respuesta = parametrosMgl.getParValor();
            } else {
                String msn = " No se  encontró la URL del servidor de JSM para TCRM en la tabla parametros: nombre del parametro URL_TCRM_JMS";
                LOGGER.error(msn);
                throw new ApplicationException(msn);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error en consultar parametro URL_TCRM_JMS: en asignarUrlJms de  CmtAvisoProgramadoMglManager: " + ex);
        }
        return respuesta;
    }
                    }
