/**
 * UnitService2Locator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

import java.net.MalformedURLException;
import org.apache.axis.AxisFault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnitService2Locator extends org.apache.axis.client.Service implements UnitService2 {

    private static final Logger LOGGER = LogManager.getLogger(UnitService2Locator.class);

    public UnitService2Locator() {
    }

    public UnitService2Locator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UnitService2Locator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for managementPort
    private java.lang.String managementPort_address = "http://172.31.3.61:8080/UnitService2/management";

    @Override
    public java.lang.String getmanagementPortAddress() {
        return managementPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String managementPortWSDDServiceName = "managementPort";

    public java.lang.String getmanagementPortWSDDServiceName() {
        return managementPortWSDDServiceName;
    }

    public void setmanagementPortWSDDServiceName(java.lang.String name) {
        managementPortWSDDServiceName = name;
    }

    @Override
    public Management getmanagementPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(managementPort_address);
        } catch (java.net.MalformedURLException e) {
            LOGGER.error("Error en getmanagementPort. EX000 ");
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getmanagementPort(endpoint);
    }

    @Override
    public Management getmanagementPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ManagementPortBindingStub _stub = new ManagementPortBindingStub(portAddress, this);
            _stub.setPortName(getmanagementPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) { 
            LOGGER.error("Error en getmanagementPort. EX000 ");
            return null;
        }
    }

    public void setmanagementPortEndpointAddress(java.lang.String address) {
        managementPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.If this service has
     * no port for the given interface, then ServiceException is thrown.
     *
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Management.class.isAssignableFrom(serviceEndpointInterface)) {
                ManagementPortBindingStub _stub = new ManagementPortBindingStub(new java.net.URL(managementPort_address), this);
                _stub.setPortName(getmanagementPortWSDDServiceName());
                return _stub;
            }
        } catch (MalformedURLException | AxisFault t) { 
            LOGGER.error("Error en getPort. EX000 ");
            throw new javax.xml.rpc.ServiceException(t);
        }
         LOGGER.error("Error en getPort. EX000 ");
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.If this service has
     * no port for the given interface, then ServiceException is thrown.
     *
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("managementPort".equals(inputPortName)) {
            return getmanagementPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://unit.telmex.net/", "UnitService2");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://unit.telmex.net/", "managementPort"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     *
     * @param address
     * @throws javax.xml.rpc.ServiceException
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("managementPort".equals(portName)) {
            setmanagementPortEndpointAddress(address);
        } else { // Unknown Port Name
             LOGGER.error("Cannot set Endpoint Address for Unknown Port" + portName);
            throw new javax.xml.rpc.ServiceException("Cannot set Endpoint Address for Unknown Port" + portName );
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     *
     * @param address
     * @throws javax.xml.rpc.ServiceException
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
