/**
 * BasicMessageReponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class BasicMessageReponse extends BasicMessage implements java.io.Serializable {

    private java.lang.String MGTX;

    private java.lang.String MGTY;

    private java.lang.String messageText;

    private java.lang.String messageType;

    public BasicMessageReponse() {
    }

    public BasicMessageReponse(
            java.lang.String MGTX,
            java.lang.String MGTY,
            java.lang.String messageText,
            java.lang.String messageType) {
        this.MGTX = MGTX;
        this.MGTY = MGTY;
        this.messageText = messageText;
        this.messageType = messageType;
    }

    /**
     * Gets the MGTX value for this BasicMessageReponse.
     *
     * @return MGTX
     */
    public java.lang.String getMGTX() {
        return MGTX;
    }

    /**
     * Sets the MGTX value for this BasicMessageReponse.
     *
     * @param MGTX
     */
    public void setMGTX(java.lang.String MGTX) {
        this.MGTX = MGTX;
    }

    /**
     * Gets the MGTY value for this BasicMessageReponse.
     *
     * @return MGTY
     */
    public java.lang.String getMGTY() {
        return MGTY;
    }

    /**
     * Sets the MGTY value for this BasicMessageReponse.
     *
     * @param MGTY
     */
    public void setMGTY(java.lang.String MGTY) {
        this.MGTY = MGTY;
    }

    /**
     * Gets the messageText value for this BasicMessageReponse.
     *
     * @return messageText
     */
    public java.lang.String getMessageText() {
        return messageText;
    }

    /**
     * Sets the messageText value for this BasicMessageReponse.
     *
     * @param messageText
     */
    public void setMessageText(java.lang.String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets the messageType value for this BasicMessageReponse.
     *
     * @return messageType
     */
    public java.lang.String getMessageType() {
        return messageType;
    }

    /**
     * Sets the messageType value for this BasicMessageReponse.
     *
     * @param messageType
     */
    public void setMessageType(java.lang.String messageType) {
        this.messageType = messageType;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BasicMessageReponse)) {
            return false;
        }
        BasicMessageReponse other = (BasicMessageReponse) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.MGTX == null && other.getMGTX() == null)
                || (this.MGTX != null
                && this.MGTX.equals(other.getMGTX())))
                && ((this.MGTY == null && other.getMGTY() == null)
                || (this.MGTY != null
                && this.MGTY.equals(other.getMGTY())))
                && ((this.messageText == null && other.getMessageText() == null)
                || (this.messageText != null
                && this.messageText.equals(other.getMessageText())))
                && ((this.messageType == null && other.getMessageType() == null)
                || (this.messageType != null
                && this.messageType.equals(other.getMessageType())));
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
        if (getMGTX() != null) {
            _hashCode += getMGTX().hashCode();
        }
        if (getMGTY() != null) {
            _hashCode += getMGTY().hashCode();
        }
        if (getMessageText() != null) {
            _hashCode += getMessageText().hashCode();
        }
        if (getMessageType() != null) {
            _hashCode += getMessageType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(BasicMessageReponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "basicMessageReponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MGTX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MGTX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MGTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MGTY"));
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
