/**
 * Cupo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public class Cupo implements java.io.Serializable {

    private java.lang.String id;

    private java.lang.String rangoHora;

    private java.lang.String idRangoHora;

    private java.lang.String aliadoId;

    private java.lang.String cuposDisponibles;

    private java.lang.String fecha;

    private java.lang.String idFecha;

    public Cupo() {
    }

    public Cupo(
            java.lang.String id,
            java.lang.String rangoHora,
            java.lang.String idRangoHora,
            java.lang.String aliadoId,
            java.lang.String cuposDisponibles,
            java.lang.String fecha,
            java.lang.String idFecha) {
        this.id = id;
        this.rangoHora = rangoHora;
        this.idRangoHora = idRangoHora;
        this.aliadoId = aliadoId;
        this.cuposDisponibles = cuposDisponibles;
        this.fecha = fecha;
        this.idFecha = idFecha;
    }

    /**
     * Gets the id value for this Cupo.
     *
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the id value for this Cupo.
     *
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * Gets the rangoHora value for this Cupo.
     *
     * @return rangoHora
     */
    public java.lang.String getRangoHora() {
        return rangoHora;
    }

    /**
     * Sets the rangoHora value for this Cupo.
     *
     * @param rangoHora
     */
    public void setRangoHora(java.lang.String rangoHora) {
        this.rangoHora = rangoHora;
    }

    /**
     * Gets the idRangoHora value for this Cupo.
     *
     * @return idRangoHora
     */
    public java.lang.String getIdRangoHora() {
        return idRangoHora;
    }

    /**
     * Sets the idRangoHora value for this Cupo.
     *
     * @param idRangoHora
     */
    public void setIdRangoHora(java.lang.String idRangoHora) {
        this.idRangoHora = idRangoHora;
    }

    /**
     * Gets the aliadoId value for this Cupo.
     *
     * @return aliadoId
     */
    public java.lang.String getAliadoId() {
        return aliadoId;
    }

    /**
     * Sets the aliadoId value for this Cupo.
     *
     * @param aliadoId
     */
    public void setAliadoId(java.lang.String aliadoId) {
        this.aliadoId = aliadoId;
    }

    /**
     * Gets the cuposDisponibles value for this Cupo.
     *
     * @return cuposDisponibles
     */
    public java.lang.String getCuposDisponibles() {
        return cuposDisponibles;
    }

    /**
     * Sets the cuposDisponibles value for this Cupo.
     *
     * @param cuposDisponibles
     */
    public void setCuposDisponibles(java.lang.String cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    /**
     * Gets the fecha value for this Cupo.
     *
     * @return fecha
     */
    public java.lang.String getFecha() {
        return fecha;
    }

    /**
     * Sets the fecha value for this Cupo.
     *
     * @param fecha
     */
    public void setFecha(java.lang.String fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the idFecha value for this Cupo.
     *
     * @return idFecha
     */
    public java.lang.String getIdFecha() {
        return idFecha;
    }

    /**
     * Sets the idFecha value for this Cupo.
     *
     * @param idFecha
     */
    public void setIdFecha(java.lang.String idFecha) {
        this.idFecha = idFecha;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cupo)) {
            return false;
        }
        Cupo other = (Cupo) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.id == null && other.getId() == null)
                || (this.id != null
                && this.id.equals(other.getId())))
                && ((this.rangoHora == null && other.getRangoHora() == null)
                || (this.rangoHora != null
                && this.rangoHora.equals(other.getRangoHora())))
                && ((this.idRangoHora == null && other.getIdRangoHora() == null)
                || (this.idRangoHora != null
                && this.idRangoHora.equals(other.getIdRangoHora())))
                && ((this.aliadoId == null && other.getAliadoId() == null)
                || (this.aliadoId != null
                && this.aliadoId.equals(other.getAliadoId())))
                && ((this.cuposDisponibles == null && other.getCuposDisponibles() == null)
                || (this.cuposDisponibles != null
                && this.cuposDisponibles.equals(other.getCuposDisponibles())))
                && ((this.fecha == null && other.getFecha() == null)
                || (this.fecha != null
                && this.fecha.equals(other.getFecha())))
                && ((this.idFecha == null && other.getIdFecha() == null)
                || (this.idFecha != null
                && this.idFecha.equals(other.getIdFecha())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getRangoHora() != null) {
            _hashCode += getRangoHora().hashCode();
        }
        if (getIdRangoHora() != null) {
            _hashCode += getIdRangoHora().hashCode();
        }
        if (getAliadoId() != null) {
            _hashCode += getAliadoId().hashCode();
        }
        if (getCuposDisponibles() != null) {
            _hashCode += getCuposDisponibles().hashCode();
        }
        if (getFecha() != null) {
            _hashCode += getFecha().hashCode();
        }
        if (getIdFecha() != null) {
            _hashCode += getIdFecha().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(Cupo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "Cupo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rangoHora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RangoHora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRangoHora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdRangoHora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aliadoId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AliadoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuposDisponibles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CuposDisponibles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Fecha"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFecha");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdFecha"));
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
