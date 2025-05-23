/**
 * RetornoAgenda.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public class RetornoAgenda implements java.io.Serializable {

    private java.lang.String idOrdenTrabajo;
    private java.lang.String idAgenda;
    private java.lang.String diaAgenda;
    private java.lang.String aliadoId;
    private java.lang.String nombreAliado;
    private java.lang.String nombreTT;
    private java.lang.String idTT;
    private boolean agendado;
    private String msnError;

    public RetornoAgenda() {
    }

    public RetornoAgenda(
            java.lang.String idOrdenTrabajo,
            java.lang.String idAgenda,
            java.lang.String diaAgenda,
            java.lang.String aliadoId,
            java.lang.String nombreAliado,
            java.lang.String nombreTT,
            java.lang.String idTT) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.idAgenda = idAgenda;
        this.diaAgenda = diaAgenda;
        this.aliadoId = aliadoId;
        this.nombreAliado = nombreAliado;
        this.nombreTT = nombreTT;
        this.idTT = idTT;
    }

    /**
     * Gets the idOrdenTrabajo value for this RetornoAgenda.
     *
     * @return idOrdenTrabajo
     */
    public java.lang.String getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    /**
     * Sets the idOrdenTrabajo value for this RetornoAgenda.
     *
     * @param idOrdenTrabajo
     */
    public void setIdOrdenTrabajo(java.lang.String idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    /**
     * Gets the idAgenda value for this RetornoAgenda.
     *
     * @return idAgenda
     */
    public java.lang.String getIdAgenda() {
        return idAgenda;
    }

    /**
     * Sets the idAgenda value for this RetornoAgenda.
     *
     * @param idAgenda
     */
    public void setIdAgenda(java.lang.String idAgenda) {
        this.idAgenda = idAgenda;
    }

    /**
     * Gets the diaAgenda value for this RetornoAgenda.
     *
     * @return diaAgenda
     */
    public java.lang.String getDiaAgenda() {
        return diaAgenda;
    }

    /**
     * Sets the diaAgenda value for this RetornoAgenda.
     *
     * @param diaAgenda
     */
    public void setDiaAgenda(java.lang.String diaAgenda) {
        this.diaAgenda = diaAgenda;
    }

    /**
     * Gets the aliadoId value for this RetornoAgenda.
     *
     * @return aliadoId
     */
    public java.lang.String getAliadoId() {
        return aliadoId;
    }

    /**
     * Sets the aliadoId value for this RetornoAgenda.
     *
     * @param aliadoId
     */
    public void setAliadoId(java.lang.String aliadoId) {
        this.aliadoId = aliadoId;
    }

    /**
     * Gets the nombreAliado value for this RetornoAgenda.
     *
     * @return nombreAliado
     */
    public java.lang.String getNombreAliado() {
        return nombreAliado;
    }

    /**
     * Sets the nombreAliado value for this RetornoAgenda.
     *
     * @param nombreAliado
     */
    public void setNombreAliado(java.lang.String nombreAliado) {
        this.nombreAliado = nombreAliado;
    }

    /**
     * Gets the nombreTT value for this RetornoAgenda.
     *
     * @return nombreTT
     */
    public java.lang.String getNombreTT() {
        return nombreTT;
    }

    /**
     * Sets the nombreTT value for this RetornoAgenda.
     *
     * @param nombreTT
     */
    public void setNombreTT(java.lang.String nombreTT) {
        this.nombreTT = nombreTT;
    }

    /**
     * Gets the idTT value for this RetornoAgenda.
     *
     * @return idTT
     */
    public java.lang.String getIdTT() {
        return idTT;
    }

    /**
     * Sets the idTT value for this RetornoAgenda.
     *
     * @param idTT
     */
    public void setIdTT(java.lang.String idTT) {
        this.idTT = idTT;
    }
    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof RetornoAgenda)) {
            return false;
        }
        RetornoAgenda other = (RetornoAgenda) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.idOrdenTrabajo == null && other.getIdOrdenTrabajo() == null)
                || (this.idOrdenTrabajo != null
                && this.idOrdenTrabajo.equals(other.getIdOrdenTrabajo())))
                && ((this.idAgenda == null && other.getIdAgenda() == null)
                || (this.idAgenda != null
                && this.idAgenda.equals(other.getIdAgenda())))
                && ((this.diaAgenda == null && other.getDiaAgenda() == null)
                || (this.diaAgenda != null
                && this.diaAgenda.equals(other.getDiaAgenda())))
                && ((this.aliadoId == null && other.getAliadoId() == null)
                || (this.aliadoId != null
                && this.aliadoId.equals(other.getAliadoId())))
                && ((this.nombreAliado == null && other.getNombreAliado() == null)
                || (this.nombreAliado != null
                && this.nombreAliado.equals(other.getNombreAliado())))
                && ((this.nombreTT == null && other.getNombreTT() == null)
                || (this.nombreTT != null
                && this.nombreTT.equals(other.getNombreTT())))
                && ((this.idTT == null && other.getIdTT() == null)
                || (this.idTT != null
                && this.idTT.equals(other.getIdTT())));
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
        if (getIdOrdenTrabajo() != null) {
            _hashCode += getIdOrdenTrabajo().hashCode();
        }
        if (getIdAgenda() != null) {
            _hashCode += getIdAgenda().hashCode();
        }
        if (getDiaAgenda() != null) {
            _hashCode += getDiaAgenda().hashCode();
        }
        if (getAliadoId() != null) {
            _hashCode += getAliadoId().hashCode();
        }
        if (getNombreAliado() != null) {
            _hashCode += getNombreAliado().hashCode();
        }
        if (getNombreTT() != null) {
            _hashCode += getNombreTT().hashCode();
        }
        if (getIdTT() != null) {
            _hashCode += getIdTT().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(RetornoAgenda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/WebServices", "RetornoAgenda"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOrdenTrabajo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdOrdenTrabajo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAgenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdAgenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diaAgenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DiaAgenda"));
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
        elemField.setFieldName("nombreAliado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NombreAliado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreTT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NombreTT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdTT"));
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

    public boolean isAgendado() {
        return agendado;
    }

    public void setAgendado(boolean agendado) {
        this.agendado = agendado;
    }

    public String getMsnError() {
        return msnError;
    }

    public void setMsnError(String msnError) {
        this.msnError = msnError;
    }
}
