/**
 * AgendarConError.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public class AgendarConError implements java.io.Serializable {

    private java.lang.String errorNo;

    private java.lang.String errorDesc;

    private RetornoAgenda agendar;

    public AgendarConError() {
    }

    public AgendarConError(
            java.lang.String errorNo,
            java.lang.String errorDesc,
            RetornoAgenda agendar) {
        this.errorNo = errorNo;
        this.errorDesc = errorDesc;
        this.agendar = agendar;
    }

    /**
     * Gets the errorNo value for this AgendarConError.
     *
     * @return errorNo
     */
    public java.lang.String getErrorNo() {
        return errorNo;
    }

    /**
     * Sets the errorNo value for this AgendarConError.
     *
     * @param errorNo
     */
    public void setErrorNo(java.lang.String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Gets the errorDesc value for this AgendarConError.
     *
     * @return errorDesc
     */
    public java.lang.String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets the errorDesc value for this AgendarConError.
     *
     * @param errorDesc
     */
    public void setErrorDesc(java.lang.String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * Gets the agendar value for this AgendarConError.
     *
     * @return agendar
     */
    public RetornoAgenda getAgendar() {
        return agendar;
    }

    /**
     * Sets the agendar value for this AgendarConError.
     *
     * @param agendar
     */
    public void setAgendar(RetornoAgenda agendar) {
        this.agendar = agendar;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof AgendarConError)) {
            return false;
        }
        AgendarConError other = (AgendarConError) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.errorNo == null && other.getErrorNo() == null)
                || (this.errorNo != null
                && this.errorNo.equals(other.getErrorNo())))
                && ((this.errorDesc == null && other.getErrorDesc() == null)
                || (this.errorDesc != null
                && this.errorDesc.equals(other.getErrorDesc())))
                && ((this.agendar == null && other.getAgendar() == null)
                || (this.agendar != null
                && this.agendar.equals(other.getAgendar())));
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
        int _hashCode = 1;
        if (getErrorNo() != null) {
            _hashCode += getErrorNo().hashCode();
        }
        if (getErrorDesc() != null) {
            _hashCode += getErrorDesc().hashCode();
        }
        if (getAgendar() != null) {
            _hashCode += getAgendar().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(AgendarConError.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "AgendarConError"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ErrorNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ErrorDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agendar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Agendar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "RetornoAgenda"));
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
