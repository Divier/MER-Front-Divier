/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.CustomerPortType;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.CustomerService;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.EricssonFault;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.ResponseStatus;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.UpdateCaseStatusResponseTYPE;
import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.UpdateCaseStatusType;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jms.ProductorMensajesTcrmFacade;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author valbuenayf
 */
@Stateless(name = "cmtNotificarCambioTcrmMglManager")
public class CmtNotificarCambioTcrmMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtNotificarCambioTcrmMglManager.class);
    @EJB
    ProductorMensajesTcrmFacade productorMensajesTcrm;

    /**
     * valbuenayf metodo Temporal
     *
     * @param updateCaseStatusType
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws EricssonFault
     */
    @Deprecated
    public void crearmensajeTemporalTcrm(UpdateCaseStatusType updateCaseStatusType) throws ApplicationException, EricssonFault {
        try {
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametrosMgl = null;
            try {
                Initialized.getInstance();
                parametrosMgl = parametrosMglManager.findParametroMgl(Constant.URL_TCRM_JMS);
                if (parametrosMgl != null && parametrosMgl.getParValor() != null
                        && !parametrosMgl.getParValor().isEmpty()) {
                    productorMensajesTcrm.setUrlJms(parametrosMgl.getParValor());
                } else {
                    String msn = " No se  encontró la URL del servidor de JSM para TCRM en la tabla parametros: nombre del parametro URL_TCRM_JMS";
                    LOGGER.error(msn);
                    throw new ApplicationException(msn);
                }
            } catch (ApplicationException ex) {
                LOGGER.error("Error en consultar parametro URL_TCRM_JMS: " + ex);
            }

            productorMensajesTcrm.crearMensaje(updateCaseStatusType.getCaseId(), updateCaseStatusType.getCaseReason(),
                    updateCaseStatusType.getCaseIdMGL(), updateCaseStatusType.getCaseStatus(), updateCaseStatusType.getCaseCompleted());
        } catch (ApplicationException e) {
            LOGGER.error("Error en updateChangeStratumStatus de CmtNotificarCambioTcrmMglManager: " + e);
        }

    }

    /**
     * valbuenayf metodo para enviar las notificaciones a TCRM Cambio de estrato
     * Creación de HHPP Solicitud de aviso de cambio de estado de un HHPP o
     * CCMM.
     *
     * @param updateCaseStatusType
     * @return
     */
    public UpdateCaseStatusResponseTYPE enviarNotificacionCambioTcrm(UpdateCaseStatusType updateCaseStatusType) {

        UpdateCaseStatusResponseTYPE respuesta = null;

        try {

            CustomerService developService = new CustomerService();
            CustomerPortType customerPortType = developService.getCustomerPortType();
            respuesta = customerPortType.updateCaseStatus(updateCaseStatusType);

            if (respuesta != null && respuesta.getResponseStatus() == null) {
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setCode("1");
                responseStatus.setMessage("Sin respuesta");
                respuesta.setResponseStatus(responseStatus);
            }

        } catch (EricssonFault ex) {
            LOGGER.error("Error en updateChangeStratumStatus de CmtNotificarCambioTcrmMglManager: " + ex);
            respuesta = new UpdateCaseStatusResponseTYPE();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setCode("1");
            responseStatus.setMessage(ex.getMessage());
            respuesta.setResponseStatus(responseStatus);
            return respuesta;
        } catch (Exception e) {
            LOGGER.error("Error en updateChangeStratumStatus de CmtNotificarCambioTcrmMglManager: " + e);
            respuesta = new UpdateCaseStatusResponseTYPE();
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setCode("1");
            responseStatus.setMessage("Sin conexion " + e.getMessage());
            respuesta.setResponseStatus(responseStatus);
            return respuesta;
        }

        return respuesta;
    }
}
