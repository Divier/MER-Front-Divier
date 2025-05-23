/**
 * ResponseMessageUnit.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class ResponseMessageUnit extends BasicMessage implements java.io.Serializable {

    private java.lang.String AKYN;

    private java.lang.String messageText;

    private java.lang.String messageType;

    private java.lang.String STRN;

    public ResponseMessageUnit() {
    }

    public ResponseMessageUnit(
            java.lang.String AKYN,
            java.lang.String messageText,
            java.lang.String messageType,
            java.lang.String STRN) {
        this.AKYN = AKYN;
        this.messageText = messageText;
        this.messageType = messageType;
        this.STRN = STRN;
    }

    /**
     * Gets the AKYN value for this ResponseMessageUnit.
     *
     * @return AKYN
     */
    public java.lang.String getAKYN() {
        return AKYN;
    }

    /**
     * Sets the AKYN value for this ResponseMessageUnit.
     *
     * @param AKYN
     */
    public void setAKYN(java.lang.String AKYN) {
        this.AKYN = AKYN;
    }

    /**
     * Gets the messageText value for this ResponseMessageUnit.
     *
     * @return messageText
     */
    public java.lang.String getMessageText() {
        return messageText;
    }

    /**
     * Sets the messageText value for this ResponseMessageUnit.
     *
     * @param messageText
     */
    public void setMessageText(java.lang.String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets the messageType value for this ResponseMessageUnit.
     *
     * @return messageType
     */
    public java.lang.String getMessageType() {
        return messageType;
    }

    /**
     * Sets the messageType value for this ResponseMessageUnit.
     *
     * @param messageType
     */
    public void setMessageType(java.lang.String messageType) {
        this.messageType = messageType;
    }

    /**
     * Gets the STRN value for this ResponseMessageUnit.
     *
     * @return STRN
     */
    public java.lang.String getSTRN() {
        return STRN;
    }

    /**
     * Sets the STRN value for this ResponseMessageUnit.
     *
     * @param STRN
     */
    public void setSTRN(java.lang.String STRN) {
        this.STRN = STRN;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResponseMessageUnit)) {
            return false;
        }
        ResponseMessageUnit other = (ResponseMessageUnit) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.AKYN == null && other.getAKYN() == null)
                || (this.AKYN != null
                && this.AKYN.equals(other.getAKYN())))
                && ((this.messageText == null && other.getMessageText() == null)
                || (this.messageText != null
                && this.messageText.equals(other.getMessageText())))
                && ((this.messageType == null && other.getMessageType() == null)
                || (this.messageType != null
                && this.messageType.equals(other.getMessageType())))
                && ((this.STRN == null && other.getSTRN() == null)
                || (this.STRN != null
                && this.STRN.equals(other.getSTRN())));
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
        if (getAKYN() != null) {
            _hashCode += getAKYN().hashCode();
        }
        if (getMessageText() != null) {
            _hashCode += getMessageText().hashCode();
        }
        if (getMessageType() != null) {
            _hashCode += getMessageType().hashCode();
        }
        if (getSTRN() != null) {
            _hashCode += getSTRN().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(ResponseMessageUnit.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseMessageUnit"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AKYN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AKYN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messageText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STRN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STRN"));
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
