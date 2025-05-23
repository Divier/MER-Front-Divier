
package co.claro.wcc.schema.schemaoperations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import co.claro.wcc.schema.schemadocument.DocumentType;
import co.claro.wcc.schema.schemadocument.FileType;


/**
 * <p>Java class for fileRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fileRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="document" type="{http://claro.co/wcc/schema/SchemaDocument}documentType"/>
 *         &lt;element name="file" type="{http://claro.co/wcc/schema/SchemaDocument}fileType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileRequestType", propOrder = {
    "document",
    "file"
})
public class FileRequestType {

    @XmlElement(required = true)
    protected DocumentType document;
    @XmlElement(required = true)
    protected FileType file;

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentType }
     *     
     */
    public DocumentType getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentType }
     *     
     */
    public void setDocument(DocumentType value) {
        this.document = value;
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link FileType }
     *     
     */
    public FileType getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileType }
     *     
     */
    public void setFile(FileType value) {
        this.file = value;
    }

}
