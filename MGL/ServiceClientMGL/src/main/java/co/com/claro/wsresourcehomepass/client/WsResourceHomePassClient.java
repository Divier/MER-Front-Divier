package co.com.claro.wsresourcehomepass.client;

import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.utils.Constants;
import co.com.claro.utils.WsUtils;
import co.com.claro.wsresourcehomepass.headers.HeaderRequest;
import co.com.claro.wsresourcehomepass.headers.HeaderResponse;
import co.com.claro.wsresourcehomepass.implement.*;
import co.com.claro.wsresourcehomepass.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cliente para consumir HomePass
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class WsResourceHomePassClient {

    private final WSResourceHomePass homePass;
    private Service service;

    private final Logger LOGGER = LogManager.getLogger(WsResourceHomePassClient.class);

    public WsResourceHomePassClient(String urlWsdl) throws ApplicationException {
        try {
            homePass = new WSResourceHomePass(WsUtils.getUrl(urlWsdl));
        } catch (WebServiceException e) {
            String msj = Constants.VALIDATE_REACTIVACION_RESOURCE + ": Error al leer el WSDL";
            LOGGER.error(msj, e);
            throw new ApplicationException(msj);
        }
    }

    public ValidateReactivationResourceResponse validateReactivationResource(ValidateReactivationResourceRequest request) throws ApplicationException {

        if (isValidateReactivationResourceRequestValid(request)) {
            HeaderRequest headerRequest = new HeaderRequest();
            Holder<HeaderResponse> holder = new Holder<HeaderResponse>();
            ValidateReactivationResource reactivationResourceParameters = new ValidateReactivationResource();

            reactivationResourceParameters.setValidateReactivationResourceRequest(request);

            headerRequest.setTransactionId("?");
            headerRequest.setSystem("MER");
            headerRequest.setTarget("?");
            headerRequest.setUser("MER");
            headerRequest.setPassword("?");
            headerRequest.setRequestDate(WsUtils.getCurrentDate());
            headerRequest.setIpApplication("?");
            headerRequest.setTraceabilityId("?");

            try {
                service = homePass.getServicePort();
                return service.validateReactivationResource(reactivationResourceParameters, headerRequest, holder);
            } catch (Exception ex) {
                String msj = "Se presento un error en el consumo de: " + Constants.VALIDATE_REACTIVACION_RESOURCE;
                LOGGER.error(msj, ex);
                throw new ApplicationException(msj, ex);
            }

        } else {
            String msj = "Error en metodo " + Constants.VALIDATE_REACTIVACION_RESOURCE + ": Request con parametros invalidos";
            LOGGER.error(msj);
            throw new ApplicationException(msj);
        }

    }

    private boolean isValidateReactivationResourceRequestValid(ValidateReactivationResourceRequest validateReactivationResourceRequest) {
        return validateReactivationResourceRequest.getCodNode() != null
                || validateReactivationResourceRequest.getDivision() != null
                || validateReactivationResourceRequest.getCommunity() != null
                || validateReactivationResourceRequest.getUserRR() != null;

    }

}
