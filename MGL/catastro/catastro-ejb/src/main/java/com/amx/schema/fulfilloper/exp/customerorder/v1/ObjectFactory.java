
package com.amx.schema.fulfilloper.exp.customerorder.v1;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.amx.schema.fulfilloper.exp.customerorder.v1 package. 
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
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amx.schema.fulfilloper.exp.customerorder.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RowSet }
     * 
     */
    public RowSet createRowSet() {
        return new RowSet();
    }

    /**
     * Create an instance of {@link RowSet.Row }
     * 
     */
    public RowSet.Row createRowSetRow() {
        return new RowSet.Row();
    }

    /**
     * Create an instance of {@link CustomerOrderResponse }
     * 
     */
    public CustomerOrderResponse createCustomerOrderResponse() {
        return new CustomerOrderResponse();
    }

    /**
     * Create an instance of {@link CustomerOrderRequest }
     * 
     */
    public CustomerOrderRequest createCustomerOrderRequest() {
        return new CustomerOrderRequest();
    }

    /**
     * Create an instance of {@link RowSet.Row.Column }
     * 
     */
    public RowSet.Row.Column createRowSetRowColumn() {
        return new RowSet.Row.Column();
    }

}
