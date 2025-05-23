/**
 * RequestStreetGrid.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class RequestStreetGrid extends BasicMessage implements java.io.Serializable {

    private java.lang.String caby;

    private java.lang.String community;

    private java.lang.String division;

    private java.lang.String grpi;

    private java.lang.String streetName;

    public RequestStreetGrid() {
    }

    public RequestStreetGrid(
            java.lang.String caby,
            java.lang.String community,
            java.lang.String division,
            java.lang.String grpi,
            java.lang.String streetName) {
        this.caby = caby;
        this.community = community;
        this.division = division;
        this.grpi = grpi;
        this.streetName = streetName;
    }

    /**
     * Gets the caby value for this RequestStreetGrid.
     *
     * @return caby
     */
    public java.lang.String getCaby() {
        return caby;
    }

    /**
     * Sets the caby value for this RequestStreetGrid.
     *
     * @param caby
     */
    public void setCaby(java.lang.String caby) {
        this.caby = caby;
    }

    /**
     * Gets the community value for this RequestStreetGrid.
     *
     * @return community
     */
    public java.lang.String getCommunity() {
        return community;
    }

    /**
     * Sets the community value for this RequestStreetGrid.
     *
     * @param community
     */
    public void setCommunity(java.lang.String community) {
        this.community = community;
    }

    /**
     * Gets the division value for this RequestStreetGrid.
     *
     * @return division
     */
    public java.lang.String getDivision() {
        return division;
    }

    /**
     * Sets the division value for this RequestStreetGrid.
     *
     * @param division
     */
    public void setDivision(java.lang.String division) {
        this.division = division;
    }

    /**
     * Gets the grpi value for this RequestStreetGrid.
     *
     * @return grpi
     */
    public java.lang.String getGrpi() {
        return grpi;
    }

    /**
     * Sets the grpi value for this RequestStreetGrid.
     *
     * @param grpi
     */
    public void setGrpi(java.lang.String grpi) {
        this.grpi = grpi;
    }

    /**
     * Gets the streetName value for this RequestStreetGrid.
     *
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }

    /**
     * Sets the streetName value for this RequestStreetGrid.
     *
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof RequestStreetGrid)) {
            return false;
        }
        RequestStreetGrid other = (RequestStreetGrid) obj;
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
                && ((this.grpi == null && other.getGrpi() == null)
                || (this.grpi != null
                && this.grpi.equals(other.getGrpi())))
                && ((this.streetName == null && other.getStreetName() == null)
                || (this.streetName != null
                && this.streetName.equals(other.getStreetName())));
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
        if (getGrpi() != null) {
            _hashCode += getGrpi().hashCode();
        }
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(RequestStreetGrid.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "requestStreetGrid"));
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
        elemField.setFieldName("grpi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "grpi"));
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
