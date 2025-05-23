
package com.oracle.xmlns.odi.odiinvoke.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.oracle.xmlns.odi.odiinvoke.dto.request.OdiCredentialType;
import com.oracle.xmlns.odi.odiinvoke.dto.request.OdiStartLoadPlanRequest;
import com.oracle.xmlns.odi.odiinvoke.dto.request.StartLoadPlanRequestType;
import com.oracle.xmlns.odi.odiinvoke.dto.request.VariableType;
import com.oracle.xmlns.odi.odiinvoke.dto.response.OdiLoadPlanInstanceRunInformationType;
import com.oracle.xmlns.odi.odiinvoke.dto.response.OdiStartLoadPlanResponse;
import com.oracle.xmlns.odi.odiinvoke.dto.response.OdiStartLoadPlanType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.xmlns.odi.odiinvoke package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactoryOdiInvoke {

    private final static QName _OdiRefreshAgentConfigResponse_QNAME = new QName("xmlns.oracle.com/odi/OdiInvoke/", "OdiRefreshAgentConfigResponse");
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.xmlns.odi.odiinvoke
     * 
     */
    public ObjectFactoryOdiInvoke() {
    }


    /**
     * Create an instance of {@link OdiCredentialType }
     * 
     */
    public OdiCredentialType createOdiCredentialType() {
        return new OdiCredentialType();
    }


    /**
     * Create an instance of {@link OdiStartLoadPlanRequest }
     * 
     */
    public OdiStartLoadPlanRequest createOdiStartLoadPlanRequest() {
        return new OdiStartLoadPlanRequest();
    }

    /**
     * Create an instance of {@link StartLoadPlanRequestType }
     * 
     */
    public StartLoadPlanRequestType createStartLoadPlanRequestType() {
        return new StartLoadPlanRequestType();
    }

    /**
     * Create an instance of {@link OdiStartLoadPlanResponse }
     * 
     */
    public OdiStartLoadPlanResponse createOdiStartLoadPlanResponse() {
        return new OdiStartLoadPlanResponse();
    }

    /**
     * Create an instance of {@link OdiStartLoadPlanType }
     * 
     */
    public OdiStartLoadPlanType createOdiStartLoadPlanType() {
        return new OdiStartLoadPlanType();
    }

    /**
     * Create an instance of {@link VariableType }
     * 
     */
    public VariableType createVariableType() {
        return new VariableType();
    }

    /**
     * Create an instance of {@link OdiLoadPlanInstanceRunInformationType }
     * 
     */
    public OdiLoadPlanInstanceRunInformationType createOdiLoadPlanInstanceRunInformationType() {
        return new OdiLoadPlanInstanceRunInformationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "xmlns.oracle.com/odi/OdiInvoke/", name = "OdiRefreshAgentConfigResponse")
    public JAXBElement<String> createOdiRefreshAgentConfigResponse(String value) {
        return new JAXBElement<String>(_OdiRefreshAgentConfigResponse_QNAME, String.class, null, value);
    }

}
