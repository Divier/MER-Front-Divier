
package co.claro.wcc.services.upload.ordenesservicio;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-740-
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "uploadOrdenesServicio", targetNamespace = "http://claro.co/wcc/services/upload/OrdenesServicio/", wsdlLocation = "http://localhost:18000/WSOrdenesServicio/uploadOrdenesServicio?WSDL")
public class UploadOrdenesServicio_Service
    extends Service
{

    private final static URL UPLOADORDENESSERVICIO_WSDL_LOCATION;
    private final static WebServiceException UPLOADORDENESSERVICIO_EXCEPTION;
    private final static QName UPLOADORDENESSERVICIO_QNAME = new QName("http://claro.co/wcc/services/upload/OrdenesServicio/", "uploadOrdenesServicio");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:18000/WSOrdenesServicio/uploadOrdenesServicio?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        UPLOADORDENESSERVICIO_WSDL_LOCATION = url;
        UPLOADORDENESSERVICIO_EXCEPTION = e;
    }

    public UploadOrdenesServicio_Service() {
        super(__getWsdlLocation(), UPLOADORDENESSERVICIO_QNAME);
    }

    public UploadOrdenesServicio_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), UPLOADORDENESSERVICIO_QNAME, features);
    }

    public UploadOrdenesServicio_Service(URL wsdlLocation) {
        super(wsdlLocation, UPLOADORDENESSERVICIO_QNAME);
    }

    public UploadOrdenesServicio_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, UPLOADORDENESSERVICIO_QNAME, features);
    }

    public UploadOrdenesServicio_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UploadOrdenesServicio_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns UploadOrdenesServicio
     */
    @WebEndpoint(name = "uploadOrdenesServicioPort")
    public UploadOrdenesServicio getUploadOrdenesServicioPort() {
        return super.getPort(new QName("http://claro.co/wcc/services/upload/OrdenesServicio/", "uploadOrdenesServicioPort"), UploadOrdenesServicio.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UploadOrdenesServicio
     */
    @WebEndpoint(name = "uploadOrdenesServicioPort")
    public UploadOrdenesServicio getUploadOrdenesServicioPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://claro.co/wcc/services/upload/OrdenesServicio/", "uploadOrdenesServicioPort"), UploadOrdenesServicio.class, features);
    }

    private static URL __getWsdlLocation() {
        if (UPLOADORDENESSERVICIO_EXCEPTION!= null) {
            throw UPLOADORDENESSERVICIO_EXCEPTION;
        }
        return UPLOADORDENESSERVICIO_WSDL_LOCATION;
    }

}
