/**
 * PermisosLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package co.net.cable.Agendamiento.permsUsers;

import java.net.MalformedURLException;
import org.apache.axis.AxisFault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermisosLocator extends org.apache.axis.client.Service implements co.net.cable.Agendamiento.permsUsers.Permisos {
    
    private static final Logger LOGGER = LogManager.getLogger(PermisosLocator.class);
        
    // Use to get a proxy class for PermisosPort
    private java.lang.String PermisosPort_address = "http://10.244.143.19:8001/include/loginjava.php";
    
    public PermisosLocator() {
    }
    
    public PermisosLocator(String url) {
        LOGGER.error("********************************************************");
        LOGGER.error("Iniciando la url del servicio web para consulta de login");
        LOGGER.error("url:" + url);
        PermisosPort_address = url;
        LOGGER.error("PermisosPort_address:" + PermisosPort_address);
        LOGGER.error("********************************************************");
    }


    public PermisosLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PermisosLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    @Override
    public java.lang.String getPermisosPortAddress() {
        return PermisosPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PermisosPortWSDDServiceName = "PermisosPort";

    public java.lang.String getPermisosPortWSDDServiceName() {
        return PermisosPortWSDDServiceName;
    }

    public void setPermisosPortWSDDServiceName(java.lang.String name) {
        PermisosPortWSDDServiceName = name;
    }

    @Override
    public co.net.cable.Agendamiento.permsUsers.PermisosPortType getPermisosPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PermisosPort_address);
        }
        catch (java.net.MalformedURLException e) {
            LOGGER.error("Error en getPermisosPort. " +e.getMessage());  
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPermisosPort(endpoint);
    }

    @Override
    public co.net.cable.Agendamiento.permsUsers.PermisosPortType getPermisosPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            co.net.cable.Agendamiento.permsUsers.PermisosBindingStub _stub = new co.net.cable.Agendamiento.permsUsers.PermisosBindingStub(portAddress, this);
            _stub.setPortName(getPermisosPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            LOGGER.error("Error en getPermisosPort. " +e.getMessage(), e);  
            return null;
        }
    }

    public void setPermisosPortEndpointAddress(java.lang.String address) {
        PermisosPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.If this service has no port for the given interface,
 then ServiceException is thrown.
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (co.net.cable.Agendamiento.permsUsers.PermisosPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                co.net.cable.Agendamiento.permsUsers.PermisosBindingStub _stub = new co.net.cable.Agendamiento.permsUsers.PermisosBindingStub(new java.net.URL(PermisosPort_address), this);
                _stub.setPortName(getPermisosPortWSDDServiceName());
                return _stub;
            }
        }
        catch (MalformedURLException | AxisFault t) {
            LOGGER.error("Error en getPort. " +t.getMessage());  
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.If this service has no port for the given interface,
 then ServiceException is thrown.
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PermisosPort".equals(inputPortName)) {
            return getPermisosPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/permsUsers", "Permisos");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/permsUsers", "PermisosPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
     * @param address
     * @throws javax.xml.rpc.ServiceException
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PermisosPort".equals(portName)) {
            setPermisosPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
     * @param address
     * @throws javax.xml.rpc.ServiceException
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
