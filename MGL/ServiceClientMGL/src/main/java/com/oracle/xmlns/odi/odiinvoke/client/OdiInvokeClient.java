package com.oracle.xmlns.odi.odiinvoke.client;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.utils.Constants;
import co.com.claro.utils.WsUtils;
import com.oracle.xmlns.odi.odiinvoke.implement.OdiInvoke;
import com.oracle.xmlns.odi.odiinvoke.dto.request.OdiStartLoadPlanRequest;
import com.oracle.xmlns.odi.odiinvoke.dto.response.OdiStartLoadPlanResponse;
import com.oracle.xmlns.odi.odiinvoke.service.OdiInvokeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.WebServiceException;

/**
 * Cliente para consumir ODI
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class OdiInvokeClient {

	private final OdiInvoke odiInvoke;
	private OdiInvokeService odiInvokeService;
	private final Logger LOGGER = LogManager.getLogger(OdiInvokeClient.class);

	public OdiInvokeClient(String urlWsdl) throws ApplicationException {
		try {
			odiInvoke = new OdiInvoke(WsUtils.getUrl(urlWsdl));
		} catch (WebServiceException e) {
			String msj = Constants.START_LOAD_PLAN + ": Error al leer el WSDL";
			LOGGER.error(msj, e);
			throw new ApplicationException(msj);
		}
	}

	public OdiStartLoadPlanResponse invokeStartLoadPlan(OdiStartLoadPlanRequest request) throws ApplicationException {
		if (isRequestValid(request)) {
			try {
				odiInvokeService = odiInvoke.getOdiInvokeRequestSOAP11Port0();
				return odiInvokeService.invokeStartLoadPlan(request);
			} catch (Exception ex) {
				String msj = "Se presento un error en el consumo de: " + Constants.START_LOAD_PLAN;
				LOGGER.error(msj, ex);
				throw new ApplicationException(msj, ex);
			}
		} else {
			String msj = "Error en metodo " + Constants.START_LOAD_PLAN + ": Request con parametros invalidos";
			LOGGER.error(msj);
			throw new ApplicationException(msj);
		}
	}

	private boolean isRequestValid(OdiStartLoadPlanRequest request){
		return request.getCredentials() != null && request.getStartLoadPlanRequest() != null;
	}
}
