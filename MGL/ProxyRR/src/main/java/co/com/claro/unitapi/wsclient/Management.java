/**
 * Management.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public interface Management extends java.rmi.Remote {

    ResponseMessageUnit changeUnitAddress(ChangeUnitAddressRequest request) throws java.rmi.RemoteException, PcmlException;

    ResponseMessageUnit unitManager(AddUnitRequest requestAddUnit) throws java.rmi.RemoteException, PcmlException;

    ResponseCRUDUnit CRUDUnitManager(RequestCRUDUnit requestCRUDUnit) throws java.rmi.RemoteException, PcmlException;

    ResponseQueryStreet streetQueryManager(RequestQueryStreet requestCRUDUnit) throws java.rmi.RemoteException, PcmlException;

    ResponseQueryStreet unitAddInfManager(RequestUnitAddInf requestUnitAddInf) throws java.rmi.RemoteException, PcmlException;

    ResponseQueryStreet queryRegularUnit(RequestQueryRegularUnit requestQueryRegularUnit) throws java.rmi.RemoteException, PcmlException;

    ResponseMessageUnit specialUpdateManager(RequestSpecialUpdate requestSpecialUpdate) throws java.rmi.RemoteException, PcmlException;

    BasicMessageReponse updateStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException;

    ResponseQueryStreetGrid queryStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException;

    ResponseCreateServiceCall createServiceCall(CreateServiceCallRequest createServiceCallRequest) throws java.rmi.RemoteException, PcmlException;
}
