/**
 * AgendaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

import java.net.MalformedURLException;
import org.apache.axis.AxisFault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AgendaLocator extends org.apache.axis.client.Service implements Agenda {

    private static final Logger LOGGER = LogManager.getLogger(AgendaLocator.class);

    public AgendaLocator() {
    }

    public AgendaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AgendaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AgendaPort
    private java.lang.String AgendaPort_address = "http://agendamiento.cable.net.co/webservices/agendaWS.php";

    @Override
    public java.lang.String getAgendaPortAddress() {
        return AgendaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AgendaPortWSDDServiceName = "AgendaPort";

    public java.lang.String getAgendaPortWSDDServiceName() {
        return AgendaPortWSDDServiceName;
    }

    public void setAgendaPortWSDDServiceName(java.lang.String name) {
        AgendaPortWSDDServiceName = name;
    }

    @Override
    public AgendaPortType getAgendaPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AgendaPort_address);
        } catch (java.net.MalformedURLException e) {
            LOGGER.error("Error en getAgendaPort " + e.getMessage(), e);
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAgendaPort(endpoint);
    }

    @Override
    public AgendaPortType getAgendaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AgendaBindingStub _stub = new AgendaBindingStub(portAddress, this);
            _stub.setPortName(getAgendaPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {  
            LOGGER.error("Error en getAgendaPort " + e.getMessage(),e);
            return null;
        }
    }

    public void setAgendaPortEndpointAddress(java.lang.String address) {
        AgendaPort_address = address;
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
            if (AgendaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                AgendaBindingStub _stub = new AgendaBindingStub(new java.net.URL(AgendaPort_address), this);
                _stub.setPortName(getAgendaPortWSDDServiceName());
                return _stub;
            }
        } catch (MalformedURLException | AxisFault t) {  
            LOGGER.error("Error en getPort " + t.getMessage());
            throw new javax.xml.rpc.ServiceException(t);
        }
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
        if ("AgendaPort".equals(inputPortName)) {
            return getAgendaPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "Agenda");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "AgendaPort"));
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

        if ("AgendaPort".equals(portName)) {
            setAgendaPortEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
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
