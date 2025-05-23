/**
 * Aliado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public class Aliado implements java.io.Serializable {

    private java.lang.String aliadoId;

    private java.lang.String aliadoNombre;

    private Cupo[] cuposAliados;

    public Aliado() {
    }

    public Aliado(
            java.lang.String aliadoId,
            java.lang.String aliadoNombre,
            Cupo[] cuposAliados) {
        this.aliadoId = aliadoId;
        this.aliadoNombre = aliadoNombre;
        this.cuposAliados = cuposAliados;
    }

    /**
     * Gets the aliadoId value for this Aliado.
     *
     * @return aliadoId
     */
    public java.lang.String getAliadoId() {
        return aliadoId;
    }

    /**
     * Sets the aliadoId value for this Aliado.
     *
     * @param aliadoId
     */
    public void setAliadoId(java.lang.String aliadoId) {
        this.aliadoId = aliadoId;
    }

    /**
     * Gets the aliadoNombre value for this Aliado.
     *
     * @return aliadoNombre
     */
    public java.lang.String getAliadoNombre() {
        return aliadoNombre;
    }

    /**
     * Sets the aliadoNombre value for this Aliado.
     *
     * @param aliadoNombre
     */
    public void setAliadoNombre(java.lang.String aliadoNombre) {
        this.aliadoNombre = aliadoNombre;
    }

    /**
     * Gets the cuposAliados value for this Aliado.
     *
     * @return cuposAliados
     */
    public Cupo[] getCuposAliados() {
        return cuposAliados;
    }

    /**
     * Sets the cuposAliados value for this Aliado.
     *
     * @param cuposAliados
     */
    public void setCuposAliados(Cupo[] cuposAliados) {
        this.cuposAliados = cuposAliados;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof Aliado)) {
            return false;
        }
        Aliado other = (Aliado) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.aliadoId == null && other.getAliadoId() == null)
                || (this.aliadoId != null
                && this.aliadoId.equals(other.getAliadoId())))
                && ((this.aliadoNombre == null && other.getAliadoNombre() == null)
                || (this.aliadoNombre != null
                && this.aliadoNombre.equals(other.getAliadoNombre())))
                && ((this.cuposAliados == null && other.getCuposAliados() == null)
                || (this.cuposAliados != null
                && java.util.Arrays.equals(this.cuposAliados, other.getCuposAliados())));
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
        if (getAliadoId() != null) {
            _hashCode += getAliadoId().hashCode();
        }
        if (getAliadoNombre() != null) {
            _hashCode += getAliadoNombre().hashCode();
        }
        if (getCuposAliados() != null) {
            for (int i = 0;
                    i < java.lang.reflect.Array.getLength(getCuposAliados());
                    i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCuposAliados(), i);
                if (obj != null
                        && !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(Aliado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "Aliado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aliadoId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AliadoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aliadoNombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AliadoNombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuposAliados");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CuposAliados"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "Cupo"));
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
