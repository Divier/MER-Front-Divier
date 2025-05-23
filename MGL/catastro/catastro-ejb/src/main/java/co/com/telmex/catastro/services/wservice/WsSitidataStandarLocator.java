package co.com.telmex.catastro.services.wservice;

import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.services.util.UrlProvGeo;
import java.net.MalformedURLException;
import org.apache.axis.AxisFault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase WsSitidataStandarLocator
 * Extiende de org.apache.axis.client.Service
 * Implementa co.com.telmex.catastro.services.wservice.WsSitidataStandar
 *
 * @author 	Deiver Rovira. - Autogenerado
 * @version	1.0
 * @version     1.1 - Modificado por: Nodo DTH 2015-09-30
 */
public class WsSitidataStandarLocator extends org.apache.axis.client.Service implements co.com.telmex.catastro.services.wservice.WsSitidataStandar {
    
    private static final Logger LOGGER = LogManager.getLogger(WsSitidataStandarLocator.class);
    /**
     * 
     */
    public WsSitidataStandarLocator() {
    }

    /**
     * 
     * @param config
     */
    public WsSitidataStandarLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    /**
     * 
     * @param wsdlLoc
     * @param sName
     * @throws javax.xml.rpc.ServiceException
     */
    public WsSitidataStandarLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    // Use to get a proxy class for wsSitidataStandarPort
    // Se asigna URL correspondiente a version del ws SitiData que trae el nodoDTH
     //Cambiar la ruta de pruebas (sitidataEnPruebas) por  (sitidataEn) cuando ServiInformacion proporcione la ruta definitiva
    private java.lang.String wsSitidataStandarPort_address = "http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php";

    /**
     * 
     * @return
     */
    @Override
    public java.lang.String getwsSitidataStandarPortAddress() {
        return wsSitidataStandarPort_address;
    }
    // The WSDD service name defaults to the port name.
    private java.lang.String wsSitidataStandarPortWSDDServiceName = "wsSitidataStandarPort";

    /**
     * 
     * @return
     */
    public java.lang.String getwsSitidataStandarPortWSDDServiceName() {
        return wsSitidataStandarPortWSDDServiceName;
    }

    /**
     * 
     * @param name
     */
    public void setwsSitidataStandarPortWSDDServiceName(java.lang.String name) {
        wsSitidataStandarPortWSDDServiceName = name;
    }

    /**
     * 
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType getwsSitidataStandarPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wsSitidataStandarPort_address);
        } catch (java.net.MalformedURLException e) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwsSitidataStandarPort(endpoint);
    }

    /**
     * 
     * @param portAddress
     * @return
     * @throws javax.xml.rpc.ServiceException
     */
    @Override
    public co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType getwsSitidataStandarPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            co.com.telmex.catastro.services.wservice.WsSitidataStandarBindingStub _stub = new co.com.telmex.catastro.services.wservice.WsSitidataStandarBindingStub(portAddress, this);
            _stub.setPortName(getwsSitidataStandarPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param address
     */
    public void setwsSitidataStandarPortEndpointAddress(java.lang.String address) {
        wsSitidataStandarPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * @param serviceEndpointInterface
     * @return
     * @throws javax.xml.rpc.ServiceException  
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                co.com.telmex.catastro.services.wservice.WsSitidataStandarBindingStub _stub = new co.com.telmex.catastro.services.wservice.WsSitidataStandarBindingStub(new java.net.URL(wsSitidataStandarPort_address), this);
                _stub.setPortName(getwsSitidataStandarPortWSDDServiceName());
                return _stub;
            }
        } catch (MalformedURLException | AxisFault t) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ t.getMessage());
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * @param portName 
     * @param serviceEndpointInterface 
     * @return 
     * @throws javax.xml.rpc.ServiceException 
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("wsSitidataStandarPort".equals(inputPortName)) {
            return getwsSitidataStandarPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "wsSitidataStandar");
    }
    private java.util.HashSet ports = null;

    /**
     * 
     * @return
     */
    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "wsSitidataStandarPort"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     * @param portName
     * @param address
     * @throws javax.xml.rpc.ServiceException  
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("wsSitidataStandarPort".equals(portName)) {
            setwsSitidataStandarPortEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     * @param portName 
     * @param address 
     * @throws javax.xml.rpc.ServiceException 
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }
}
