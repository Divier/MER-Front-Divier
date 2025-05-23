
package com.amx.co.schema.clarofault.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.amx.co.schema.clarofault.v1 package. 
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

    private final static QName _ClaroFault_QNAME = new QName("http://www.amx.com/CO/Schema/ClaroFault/v1", "claroFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amx.co.schema.clarofault.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ClaroFault }
     * 
     */
    public ClaroFault createClaroFault() {
        return new ClaroFault();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClaroFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/CO/Schema/ClaroFault/v1", name = "claroFault")
    public JAXBElement<ClaroFault> createClaroFault(ClaroFault value) {
        return new JAXBElement<ClaroFault>(_ClaroFault_QNAME, ClaroFault.class, null, value);
    }

}
