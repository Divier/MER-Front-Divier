package co.com.claro.unitapi.wsclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManagementProxy implements Management {

    private static final Logger LOGGER = LogManager.getLogger(ManagementProxy.class);

    private String _endpoint = null;
    private Management management = null;

    public ManagementProxy() {
        _initManagementProxy();
    }

    public ManagementProxy(String endpoint) {
        _endpoint = endpoint;
        _initManagementProxy();
    }

    private void _initManagementProxy() {
        try {
            management = (new UnitService2Locator()).getmanagementPort();
            if (management != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) management)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) management)._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
            LOGGER.error("Error en _initManagementProxy. EX000 " + serviceException.getMessage(), serviceException);
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (management != null) {
            ((javax.xml.rpc.Stub) management)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public Management getManagement() {
        if (management == null) {
            _initManagementProxy();
        }
        return management;
    }

    @Override
    public ResponseMessageUnit changeUnitAddress(ChangeUnitAddressRequest request) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.changeUnitAddress(request);
    }

    @Override
    public ResponseMessageUnit unitManager(AddUnitRequest requestAddUnit) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.unitManager(requestAddUnit);
    }

    @Override
    public ResponseCRUDUnit CRUDUnitManager(RequestCRUDUnit requestCRUDUnit) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.CRUDUnitManager(requestCRUDUnit);
    }

    @Override
    public ResponseQueryStreet streetQueryManager(RequestQueryStreet requestCRUDUnit) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.streetQueryManager(requestCRUDUnit);
    }

    @Override
    public ResponseQueryStreet unitAddInfManager(RequestUnitAddInf requestUnitAddInf) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.unitAddInfManager(requestUnitAddInf);
    }

    @Override
    public ResponseQueryStreet queryRegularUnit(RequestQueryRegularUnit requestQueryRegularUnit) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.queryRegularUnit(requestQueryRegularUnit);
    }

    @Override
    public ResponseMessageUnit specialUpdateManager(RequestSpecialUpdate requestSpecialUpdate) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.specialUpdateManager(requestSpecialUpdate);
    }

    @Override
    public BasicMessageReponse updateStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.updateStreetGrid(requestStreetGrid);
    }

    @Override
    public ResponseQueryStreetGrid queryStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.queryStreetGrid(requestStreetGrid);
    }

    @Override
    public ResponseCreateServiceCall createServiceCall(CreateServiceCallRequest createServiceCallRequest) throws java.rmi.RemoteException, PcmlException {
        if (management == null) {
            _initManagementProxy();
        }
        return management.createServiceCall(createServiceCallRequest);
    }

}
