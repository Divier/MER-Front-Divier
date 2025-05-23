
package co.claro.wcc.services.search.ordenesservicio;

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
@WebServiceClient(name = "searchOrdenesServicio", targetNamespace = "http://claro.co/wcc/services/search/OrdenesServicio/", wsdlLocation = "http://localhost:18000/WSOrdenesServicio/searchOrdenesServicio?WSDL")
public class SearchOrdenesServicio_Service
    extends Service
{

    private final static URL SEARCHORDENESSERVICIO_WSDL_LOCATION;
    private final static WebServiceException SEARCHORDENESSERVICIO_EXCEPTION;
    private final static QName SEARCHORDENESSERVICIO_QNAME = new QName("http://claro.co/wcc/services/search/OrdenesServicio/", "searchOrdenesServicio");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:18000/WSOrdenesServicio/searchOrdenesServicio?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SEARCHORDENESSERVICIO_WSDL_LOCATION = url;
        SEARCHORDENESSERVICIO_EXCEPTION = e;
    }

    public SearchOrdenesServicio_Service() {
        super(__getWsdlLocation(), SEARCHORDENESSERVICIO_QNAME);
    }

    public SearchOrdenesServicio_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), SEARCHORDENESSERVICIO_QNAME, features);
    }

    public SearchOrdenesServicio_Service(URL wsdlLocation) {
        super(wsdlLocation, SEARCHORDENESSERVICIO_QNAME);
    }

    public SearchOrdenesServicio_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SEARCHORDENESSERVICIO_QNAME, features);
    }

    public SearchOrdenesServicio_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SearchOrdenesServicio_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SearchOrdenesServicio
     */
    @WebEndpoint(name = "searchOrdenesServicioPort")
    public SearchOrdenesServicio getSearchOrdenesServicioPort() {
        return super.getPort(new QName("http://claro.co/wcc/services/search/OrdenesServicio/", "searchOrdenesServicioPort"), SearchOrdenesServicio.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SearchOrdenesServicio
     */
    @WebEndpoint(name = "searchOrdenesServicioPort")
    public SearchOrdenesServicio getSearchOrdenesServicioPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://claro.co/wcc/services/search/OrdenesServicio/", "searchOrdenesServicioPort"), SearchOrdenesServicio.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SEARCHORDENESSERVICIO_EXCEPTION!= null) {
            throw SEARCHORDENESSERVICIO_EXCEPTION;
        }
        return SEARCHORDENESSERVICIO_WSDL_LOCATION;
    }

}
