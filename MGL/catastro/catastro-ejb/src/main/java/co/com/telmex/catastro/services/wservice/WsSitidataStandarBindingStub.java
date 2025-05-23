package co.com.telmex.catastro.services.wservice;

import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.services.util.UrlProvGeo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.rpc.ServiceException;

/**
 * Clase WsSitidataStandarBindingStub
 * Extiende de org.apache.axis.client.Stub
 * Implementa co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType
 *
 * @author 	Deiver Rovira. - Autogenerado
 * @version	1.0
 */
public class WsSitidataStandarBindingStub extends org.apache.axis.client.Stub implements co.com.telmex.catastro.services.wservice.WsSitidataStandarPortType {

    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();
    static org.apache.axis.description.OperationDesc[] _operations;
    private static final Logger LOGGER = LogManager.getLogger(WsSitidataStandarBindingStub.class);

    static {
        _operations = new org.apache.axis.description.OperationDesc[4];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enrich");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "address"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "state"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "city"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "district"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayresult2"));
        oper.setReturnClass(co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enrichAssist");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "address"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "state"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "city"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "district"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayresult2"));
        oper.setReturnClass(co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enrichAssistComplement");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "address"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "state"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "city"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "district"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayresult2"));
        oper.setReturnClass(co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enrichXmlBatch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xmlSend"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

    }

    /**
     * 
     * @throws org.apache.axis.AxisFault
     */
    public WsSitidataStandarBindingStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    /**
     * 
     * @param endpointURL
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public WsSitidataStandarBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    /**
     * 
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public WsSitidataStandarBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
        java.lang.Class cls;
        javax.xml.namespace.QName qName;
        javax.xml.namespace.QName qName2;
        java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayData");
        cachedSerQNames.add(qName);
        cls = java.lang.String[].class;
        cachedSerClasses.add(cls);
        qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
        qName2 = null;
        cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
        cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

        qName = new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayresult2");
        cachedSerQNames.add(qName);
        cls = co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

    }

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class) cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        } catch (ServiceException _t) {
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ _t.getMessage());
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrich(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php/enrich");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName(
                getNameSpaceUrlSitiData(UrlProvGeo.getUrlProvGeo(), UrlProvGeo.getPathSitiData()), "enrich"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{address, state, city, district, user, password});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) _resp;
                } catch (java.lang.Exception _exception) {
					String msg = "Se produjo un error al momento de ejecutar el método '"+
					ClassUtils.getCurrentMethodName(this.getClass())+"': " + _exception.getMessage();
					LOGGER.error(msg);
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) org.apache.axis.utils.JavaUtils.convert(_resp, co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
             throw axisFaultException;
        }
    }

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrichAssist(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php/enrichAssist");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName(getNameSpaceUrlSitiData(
                UrlProvGeo.getUrlProvGeo(), UrlProvGeo.getPathSitiData()), "enrichAssist"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{address, state, city, district, user, password});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) _resp;
                } catch (java.lang.Exception _exception) {
					String msg = "Se produjo un error al momento de ejecutar el método '"+
					ClassUtils.getCurrentMethodName(this.getClass())+"': " + _exception.getMessage();
					LOGGER.error(msg,_exception);
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) org.apache.axis.utils.JavaUtils.convert(_resp, co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ axisFaultException.getMessage());
            throw axisFaultException;
        }
    }

    /**
     * 
     * @param address
     * @param state
     * @param city
     * @param district
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public co.com.telmex.catastro.services.wservice.ResponseArrayFromWS enrichAssistComplement(java.lang.String address, java.lang.String state, java.lang.String city, java.lang.String district, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php/enrichAssistComplement");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName(
                getNameSpaceUrlSitiData(UrlProvGeo.getUrlProvGeo(), UrlProvGeo.getPathSitiData()), "enrichAssistComplement"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{address, state, city, district, user, password});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) _resp;
                } catch (java.lang.Exception _exception) {
                    return (co.com.telmex.catastro.services.wservice.ResponseArrayFromWS) org.apache.axis.utils.JavaUtils.convert(_resp, co.com.telmex.catastro.services.wservice.ResponseArrayFromWS.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ axisFaultException.getMessage());
            throw axisFaultException;
        }
    }

    /**
     * 
     * @param xmlSend
     * @param user
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public java.lang.String enrichXmlBatch(java.lang.String xmlSend, java.lang.String user, java.lang.String password) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php/enrichXmlBatch");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName(
                getNameSpaceUrlSitiData(UrlProvGeo.getUrlProvGeo(), UrlProvGeo.getPathSitiData()), "enrichXmlBatch"));
        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{xmlSend, user, password});
            
            LOGGER.info("consumo enrichXmlBatch.."+"http://"+UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar.php/enrichXmlBatch");    
            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (java.lang.String) _resp;
                } catch (java.lang.Exception _exception) {
                    return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ axisFaultException.getMessage());
            throw axisFaultException;
        }
    }

    /**
     * Obtiene la url con el namespace de SitiData.
     *
     * @return URL con namespace de SitiData.
     * @author Gildardo Mora
     */
    private String getNameSpaceUrlSitiData(String urlProvGeo, String pathSitiData) {
        if (StringUtils.isBlank(urlProvGeo)) {
            throw new IllegalArgumentException("IP_WS_GEO en el parámetro no ha sido configurada.");
        }

        if (StringUtils.isBlank(pathSitiData)) {
            throw new IllegalArgumentException("PATH_SITIDATA en el parámetro no ha sido configurada.");
        }

        String nameSpaceUrlSitiData = urlProvGeo.replaceAll(":(\\d+)", "");
        return nameSpaceUrlSitiData + pathSitiData + "/wsSitidataStandar.php";
    }

}
