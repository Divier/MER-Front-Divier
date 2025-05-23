/**
 * TipoTrabajo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public class TipoTrabajo implements java.io.Serializable {

    private java.lang.String tipoTrabajoId;

    private java.lang.String tipoTrabajoNombre;

    public TipoTrabajo() {
    }

    public TipoTrabajo(
            java.lang.String tipoTrabajoId,
            java.lang.String tipoTrabajoNombre) {
        this.tipoTrabajoId = tipoTrabajoId;
        this.tipoTrabajoNombre = tipoTrabajoNombre;
    }

    /**
     * Gets the tipoTrabajoId value for this TipoTrabajo.
     *
     * @return tipoTrabajoId
     */
    public java.lang.String getTipoTrabajoId() {
        return tipoTrabajoId;
    }

    /**
     * Sets the tipoTrabajoId value for this TipoTrabajo.
     *
     * @param tipoTrabajoId
     */
    public void setTipoTrabajoId(java.lang.String tipoTrabajoId) {
        this.tipoTrabajoId = tipoTrabajoId;
    }

    /**
     * Gets the tipoTrabajoNombre value for this TipoTrabajo.
     *
     * @return tipoTrabajoNombre
     */
    public java.lang.String getTipoTrabajoNombre() {
        return tipoTrabajoNombre;
    }

    /**
     * Sets the tipoTrabajoNombre value for this TipoTrabajo.
     *
     * @param tipoTrabajoNombre
     */
    public void setTipoTrabajoNombre(java.lang.String tipoTrabajoNombre) {
        this.tipoTrabajoNombre = tipoTrabajoNombre;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof TipoTrabajo)) {
            return false;
        }
        TipoTrabajo other = (TipoTrabajo) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.tipoTrabajoId == null && other.getTipoTrabajoId() == null)
                || (this.tipoTrabajoId != null
                && this.tipoTrabajoId.equals(other.getTipoTrabajoId())))
                && ((this.tipoTrabajoNombre == null && other.getTipoTrabajoNombre() == null)
                || (this.tipoTrabajoNombre != null
                && this.tipoTrabajoNombre.equals(other.getTipoTrabajoNombre())));
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
        if (getTipoTrabajoId() != null) {
            _hashCode += getTipoTrabajoId().hashCode();
        }
        if (getTipoTrabajoNombre() != null) {
            _hashCode += getTipoTrabajoNombre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(TipoTrabajo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "TipoTrabajo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTrabajoId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TipoTrabajoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTrabajoNombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TipoTrabajoNombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
