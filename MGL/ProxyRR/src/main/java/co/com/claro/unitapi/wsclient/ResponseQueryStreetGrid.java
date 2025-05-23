/**
 * ResponseQueryStreetGrid.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class ResponseQueryStreetGrid extends BasicMessageReponse implements java.io.Serializable {

    private java.lang.String GRID;

    private java.lang.String HOME;

    private java.lang.String STEO;

    public ResponseQueryStreetGrid() {
    }

    public ResponseQueryStreetGrid(
            java.lang.String MGTX,
            java.lang.String MGTY,
            java.lang.String messageText,
            java.lang.String messageType,
            java.lang.String GRID,
            java.lang.String HOME,
            java.lang.String STEO) {
        super(
                MGTX,
                MGTY,
                messageText,
                messageType);
        this.GRID = GRID;
        this.HOME = HOME;
        this.STEO = STEO;
    }

    /**
     * Gets the GRID value for this ResponseQueryStreetGrid.
     *
     * @return GRID
     */
    public java.lang.String getGRID() {
        return GRID;
    }

    /**
     * Sets the GRID value for this ResponseQueryStreetGrid.
     *
     * @param GRID
     */
    public void setGRID(java.lang.String GRID) {
        this.GRID = GRID;
    }

    /**
     * Gets the HOME value for this ResponseQueryStreetGrid.
     *
     * @return HOME
     */
    public java.lang.String getHOME() {
        return HOME;
    }

    /**
     * Sets the HOME value for this ResponseQueryStreetGrid.
     *
     * @param HOME
     */
    public void setHOME(java.lang.String HOME) {
        this.HOME = HOME;
    }

    /**
     * Gets the STEO value for this ResponseQueryStreetGrid.
     *
     * @return STEO
     */
    public java.lang.String getSTEO() {
        return STEO;
    }

    /**
     * Sets the STEO value for this ResponseQueryStreetGrid.
     *
     * @param STEO
     */
    public void setSTEO(java.lang.String STEO) {
        this.STEO = STEO;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
                if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResponseQueryStreetGrid)) {
            return false;
        }
        ResponseQueryStreetGrid other = (ResponseQueryStreetGrid) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.GRID == null && other.getGRID() == null)
                || (this.GRID != null
                && this.GRID.equals(other.getGRID())))
                && ((this.HOME == null && other.getHOME() == null)
                || (this.HOME != null
                && this.HOME.equals(other.getHOME())))
                && ((this.STEO == null && other.getSTEO() == null)
                || (this.STEO != null
                && this.STEO.equals(other.getSTEO())));
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
        if (getGRID() != null) {
            _hashCode += getGRID().hashCode();
        }
        if (getHOME() != null) {
            _hashCode += getHOME().hashCode();
        }
        if (getSTEO() != null) {
            _hashCode += getSTEO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc
            = new org.apache.axis.description.TypeDesc(ResponseQueryStreetGrid.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "responseQueryStreetGrid"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GRID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GRID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HOME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "HOME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STEO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STEO"));
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
