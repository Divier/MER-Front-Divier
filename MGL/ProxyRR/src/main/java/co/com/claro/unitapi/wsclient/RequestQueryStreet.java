/**
 * RequestQueryStreet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class RequestQueryStreet extends BasicMessage implements java.io.Serializable {

    private java.lang.String caby;

    private java.lang.String community;

    private java.lang.String division;

    private java.lang.String streetName;

    private java.lang.String streetNumber;

    public RequestQueryStreet() {
    }

    public RequestQueryStreet(
            java.lang.String caby,
            java.lang.String community,
            java.lang.String division,
            java.lang.String streetName,
            java.lang.String streetNumber) {
        this.caby = caby;
        this.community = community;
        this.division = division;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    /**
     * Gets the caby value for this RequestQueryStreet.
     *
     * @return caby
     */
    public java.lang.String getCaby() {
        return caby;
    }

    /**
     * Sets the caby value for this RequestQueryStreet.
     *
     * @param caby
     */
    public void setCaby(java.lang.String caby) {
        this.caby = caby;
    }

    /**
     * Gets the community value for this RequestQueryStreet.
     *
     * @return community
     */
    public java.lang.String getCommunity() {
        return community;
    }

    /**
     * Sets the community value for this RequestQueryStreet.
     *
     * @param community
     */
    public void setCommunity(java.lang.String community) {
        this.community = community;
    }

    /**
     * Gets the division value for this RequestQueryStreet.
     *
     * @return division
     */
    public java.lang.String getDivision() {
        return division;
    }

    /**
     * Sets the division value for this RequestQueryStreet.
     *
     * @param division
     */
    public void setDivision(java.lang.String division) {
        this.division = division;
    }

    /**
     * Gets the streetName value for this RequestQueryStreet.
     *
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }

    /**
     * Sets the streetName value for this RequestQueryStreet.
     *
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets the streetNumber value for this RequestQueryStreet.
     *
     * @return streetNumber
     */
    public java.lang.String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the streetNumber value for this RequestQueryStreet.
     *
     * @param streetNumber
     */
    public void setStreetNumber(java.lang.String streetNumber) {
        this.streetNumber = streetNumber;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof RequestQueryStreet)) {
            return false;
        }
        RequestQueryStreet other = (RequestQueryStreet) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.caby == null && other.getCaby() == null)
                || (this.caby != null
                && this.caby.equals(other.getCaby())))
                && ((this.community == null && other.getCommunity() == null)
                || (this.community != null
                && this.community.equals(other.getCommunity())))
                && ((this.division == null && other.getDivision() == null)
                || (this.division != null
                && this.division.equals(other.getDivision())))
                && ((this.streetName == null && other.getStreetName() == null)
                || (this.streetName != null
                && this.streetName.equals(other.getStreetName())))
                && ((this.streetNumber == null && other.getStreetNumber() == null)
                || (this.streetNumber != null
                && this.streetNumber.equals(other.getStreetNumber())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCaby() != null) {
            _hashCode += getCaby().hashCode();
        }
        if (getCommunity() != null) {
            _hashCode += getCommunity().hashCode();
        }
        if (getDivision() != null) {
            _hashCode += getDivision().hashCode();
        }
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        if (getStreetNumber() != null) {
            _hashCode += getStreetNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(RequestQueryStreet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "requestQueryStreet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caby");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caby"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("community");
        elemField.setXmlName(new javax.xml.namespace.QName("", "community"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("division");
        elemField.setXmlName(new javax.xml.namespace.QName("", "division"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     *
     * @return
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     *
     * @param _xmlType
     * @return
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     *
     * @param _xmlType
     * @return
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(
                _javaType, _xmlType, typeDesc);
    }

}
