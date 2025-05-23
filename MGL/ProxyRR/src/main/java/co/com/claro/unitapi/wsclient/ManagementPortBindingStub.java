/**
 * ManagementPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.rpc.ServiceException;

public class ManagementPortBindingStub extends org.apache.axis.client.Stub implements Management {

    private static final Logger LOGGER = LogManager.getLogger(ManagementPortBindingStub.class);

    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc[] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeUnitAddress");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "changeUnitAddressRequest"), ChangeUnitAddressRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseMessageUnit"));
        oper.setReturnClass(ResponseMessageUnit.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "response"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnitManager");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestAddUnit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "addUnitRequest"), AddUnitRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseMessageUnit"));
        oper.setReturnClass(ResponseMessageUnit.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseAddUnit"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CRUDUnitManager");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestCRUDUnit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestCRUDUnit"), RequestCRUDUnit.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseCRUDUnit"));
        oper.setReturnClass(ResponseCRUDUnit.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseCRUDUnit"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("streetQueryManager");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestCRUDUnit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestQueryStreet"), RequestQueryStreet.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreet"));
        oper.setReturnClass(ResponseQueryStreet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseStreetQuery"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnitAddInfManager");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestUnitAddInf"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestUnitAddInf"), RequestUnitAddInf.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreet"));
        oper.setReturnClass(ResponseQueryStreet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseUnitAddInf"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryRegularUnit");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestQueryRegularUnit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestQueryRegularUnit"), RequestQueryRegularUnit.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreet"));
        oper.setReturnClass(ResponseQueryStreet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryRegularUnitManager"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SpecialUpdateManager");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestSpecialUpdate"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestSpecialUpdate"), RequestSpecialUpdate.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseMessageUnit"));
        oper.setReturnClass(ResponseMessageUnit.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseSpecialUpdate"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateStreetGrid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestStreetGrid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestStreetGrid"), RequestStreetGrid.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "basicMessageReponse"));
        oper.setReturnClass(BasicMessageReponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseUpdateStreetGrid"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryStreetGrid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestStreetGrid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "requestStreetGrid"), RequestStreetGrid.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreetGrid"));
        oper.setReturnClass(ResponseQueryStreetGrid.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "responseQueryStreetGrid"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createServiceCall");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "CreateServiceCallRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://unit.telmex.net/", "createServiceCallRequest"), CreateServiceCallRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseCreateServiceCall"));
        oper.setReturnClass(ResponseCreateServiceCall.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ResponseCreateServiceCall"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                "PcmlException",
                new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException"),
                true
        ));
        _operations[9] = oper;

    }

    public ManagementPortBindingStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    public ManagementPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public ManagementPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "addUnitRequest");
        cachedSerQNames.add(qName);
        cls = AddUnitRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "basicMessage");
        cachedSerQNames.add(qName);
        cls = BasicMessage.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "basicMessageReponse");
        cachedSerQNames.add(qName);
        cls = BasicMessageReponse.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "changeUnitAddressRequest");
        cachedSerQNames.add(qName);
        cls = ChangeUnitAddressRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "createServiceCallRequest");
        cachedSerQNames.add(qName);
        cls = CreateServiceCallRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "PcmlException");
        cachedSerQNames.add(qName);
        cls = PcmlException.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestCRUDUnit");
        cachedSerQNames.add(qName);
        cls = RequestCRUDUnit.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestQueryRegularUnit");
        cachedSerQNames.add(qName);
        cls = RequestQueryRegularUnit.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestQueryStreet");
        cachedSerQNames.add(qName);
        cls = RequestQueryStreet.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestSpecialUpdate");
        cachedSerQNames.add(qName);
        cls = RequestSpecialUpdate.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestStreetGrid");
        cachedSerQNames.add(qName);
        cls = RequestStreetGrid.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "requestUnitAddInf");
        cachedSerQNames.add(qName);
        cls = RequestUnitAddInf.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "responseCreateServiceCall");
        cachedSerQNames.add(qName);
        cls = ResponseCreateServiceCall.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "responseCRUDUnit");
        cachedSerQNames.add(qName);
        cls = ResponseCRUDUnit.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "responseMessageUnit");
        cachedSerQNames.add(qName);
        cls = ResponseMessageUnit.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreet");
        cachedSerQNames.add(qName);
        cls = ResponseQueryStreet.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreetGrid");
        cachedSerQNames.add(qName);
        cls = ResponseQueryStreetGrid.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

    }

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
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName
                                = (javax.xml.namespace.QName) cachedSerQNames.get(i);
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
            LOGGER.error("Error en createCall. EX000 " + "Failure trying to get the Call object " + _t.getMessage(), _t);
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    @Override
    public ResponseMessageUnit changeUnitAddress(ChangeUnitAddressRequest request) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en changeUnitAddress. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "changeUnitAddress"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{request});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en changeUnitAddress. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseMessageUnit) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseMessageUnit) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseMessageUnit.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            LOGGER.error("Error en changeUnitAddress. EX000 " + ((PcmlException) axisFaultException).getMessage(), axisFaultException);
            throw axisFaultException;
        }
    }

    @Override
    public ResponseMessageUnit unitManager(AddUnitRequest requestAddUnit) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en unitManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "UnitManager"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestAddUnit});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en unitManager. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseMessageUnit) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseMessageUnit) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseMessageUnit.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en unitManager. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en unitManager. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            LOGGER.error("Error en unitManager. EX000 " + axisFaultException.getMessage(), axisFaultException);
            throw axisFaultException;
        }
    }

    @Override
    public ResponseCRUDUnit CRUDUnitManager(RequestCRUDUnit requestCRUDUnit) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en CRUDUnitManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "CRUDUnitManager"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestCRUDUnit});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en CRUDUnitManager. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseCRUDUnit) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseCRUDUnit) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseCRUDUnit.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en CRUDUnitManager. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en CRUDUnitManager. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseQueryStreet streetQueryManager(RequestQueryStreet requestCRUDUnit) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en streetQueryManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "streetQueryManager"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestCRUDUnit});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en streetQueryManager. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseQueryStreet) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en streetQueryManager. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseQueryStreet) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseQueryStreet.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en streetQueryManager. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en streetQueryManager. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseQueryStreet unitAddInfManager(RequestUnitAddInf requestUnitAddInf) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en unitAddInfManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "UnitAddInfManager"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestUnitAddInf});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en unitAddInfManager. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseQueryStreet) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en unitAddInfManager. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseQueryStreet) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseQueryStreet.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en unitAddInfManager. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en unitAddInfManager. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseQueryStreet queryRegularUnit(RequestQueryRegularUnit requestQueryRegularUnit) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en unitAddInfManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "QueryRegularUnit"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestQueryRegularUnit});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en changeUnitAddress. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseQueryStreet) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en queryRegularUnit. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseQueryStreet) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseQueryStreet.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en changeUnitAddress. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseMessageUnit specialUpdateManager(RequestSpecialUpdate requestSpecialUpdate) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en unitAddInfManager. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "SpecialUpdateManager"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestSpecialUpdate});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en changeUnitAddress. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseMessageUnit) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en specialUpdateManager. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseMessageUnit) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseMessageUnit.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en specialUpdateManager. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en specialUpdateManager. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public BasicMessageReponse updateStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en updateStreetGrid. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "updateStreetGrid"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestStreetGrid});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en updateStreetGrid. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (BasicMessageReponse) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en updateStreetGrid. EX000 " + _exception.getMessage(), _exception);
                    return (BasicMessageReponse) org.apache.axis.utils.JavaUtils.convert(_resp, BasicMessageReponse.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en updateStreetGrid. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en updateStreetGrid. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseQueryStreetGrid queryStreetGrid(RequestStreetGrid requestStreetGrid) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en queryStreetGrid. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "queryStreetGrid"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{requestStreetGrid});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en queryStreetGrid. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseQueryStreetGrid) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en queryStreetGrid. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseQueryStreetGrid) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseQueryStreetGrid.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en queryStreetGrid. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en queryStreetGrid. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

    @Override
    public ResponseCreateServiceCall createServiceCall(CreateServiceCallRequest createServiceCallRequest) throws java.rmi.RemoteException, PcmlException {
        if (super.cachedEndpoint == null) {
            LOGGER.error("Error en createServiceCall. EX000 cachedEndpoint = null");
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://unit.telmex.net/", "createServiceCall"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            java.lang.Object _resp = _call.invoke(new java.lang.Object[]{createServiceCallRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                LOGGER.error("Error en createServiceCall. EX000 " + ((java.rmi.RemoteException) _resp).getMessage(), (java.rmi.RemoteException) _resp);
                throw (java.rmi.RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (ResponseCreateServiceCall) _resp;
                } catch (java.lang.Exception _exception) {
                    LOGGER.error("Error en createServiceCall. EX000 " + _exception.getMessage(), _exception);
                    return (ResponseCreateServiceCall) org.apache.axis.utils.JavaUtils.convert(_resp, ResponseCreateServiceCall.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                    LOGGER.error("Error en createServiceCall. EX000 " + ((java.rmi.RemoteException) axisFaultException.detail).getMessage(), (java.rmi.RemoteException) axisFaultException.detail);
                    throw (java.rmi.RemoteException) axisFaultException.detail;
                }
                if (axisFaultException.detail instanceof PcmlException) {
                    LOGGER.error("Error en createServiceCall. EX000 " + ((PcmlException) axisFaultException.detail).getMessage(), (PcmlException) axisFaultException.detail);
                    throw (PcmlException) axisFaultException.detail;
                }
            }
            throw axisFaultException;
        }
    }

}
