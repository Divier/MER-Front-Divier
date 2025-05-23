
package co.claro.wcc.services.search.searchcuentasmatrices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import co.claro.wcc.schema.schemaoperations.RequestType;
import co.claro.wcc.schema.schemaoperations.ResponseType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-740-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "searchCuentasMatrices", targetNamespace = "http://claro.co/wcc/services/search/searchCuentasMatrices")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    co.claro.wcc.schema.schemaoperations.ObjectFactory.class,
    co.claro.wcc.schema.schemadocument.ObjectFactory.class
})
public interface SearchCuentasMatrices {


    /**
     * 
     * @param requestMessage
     * @return
     *     returns co.claro.wcc.schema.schemaoperations.ResponseType
     * @throws SearchCuentasMatricesFault
     */
    @WebMethod(action = "http://claro.co/wcc/services/search/searchCuentasMatrices/find")
    @WebResult(name = "documentResponse", targetNamespace = "http://claro.co/wcc/schema/SchemaOperations", partName = "responseMessage")
    public ResponseType find(
        @WebParam(name = "documentRequest", targetNamespace = "http://claro.co/wcc/schema/SchemaOperations", partName = "requestMessage")
        RequestType requestMessage)
        throws SearchCuentasMatricesFault
    ;

}
