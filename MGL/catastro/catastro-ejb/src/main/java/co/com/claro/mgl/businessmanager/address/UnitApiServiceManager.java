/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.unitapi.wsclient.Management;
import co.com.claro.unitapi.wsclient.ManagementProxy;
import co.com.claro.unitapi.wsclient.RequestQueryStreet;
import co.com.claro.unitapi.wsclient.ResponseQueryStreet;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

/**
 *
 * @author ortizjaf
 */
public class UnitApiServiceManager {

    private static final Logger LOGGER = LogManager.getLogger(UnitApiServiceManager.class);
    private String urlUnitApiService = "";

    public UnitApiServiceManager() {
        urlUnitApiService = Utilidades.queryParametrosConfig(Constant.PROPERTY_URL_WSRR)+
                Utilidades.queryParametrosConfig(Constant.PROPERTY_SERVICE_WSRR);
    }

    public ResponseQueryStreet streetQueryManager(String comunidad,
            String division, String calleRr) throws ApplicationException {
        try {
            String calle = calleRr.toUpperCase().replace("Ñ", "N");
            RequestQueryStreet requestCRUDUnit = new RequestQueryStreet("", 
                    comunidad, 
                    division, 
                    calle, 
                    "");

            ManagementProxy proxy = new ManagementProxy(urlUnitApiService);
            Management management = proxy.getManagement();
            ResponseQueryStreet responseQueryStreet = management.streetQueryManager(requestCRUDUnit);
            return responseQueryStreet;
        } catch (RemoteException e) {
            LOGGER.error("Error en streetQueryManager. " +e.getMessage(), e);  
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
