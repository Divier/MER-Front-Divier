package co.com.telmex.catastro.services.wservice;
import co.com.telmex.catastro.services.util.UrlProvGeo;

/**
 * Clase ResponseArrayFromWS
 *
 * @author 	Deiver Rovira. - Autogenerado
 * @version	1.0
 */
public class ResponseArrayFromWS implements java.io.Serializable {

    private java.lang.String[] geo;
    private java.lang.String message;

    /**
     * 
     */
    public ResponseArrayFromWS() {
    }

    /**
     * 
     * @param geo
     * @param message
     */
    public ResponseArrayFromWS(
            java.lang.String[] geo,
            java.lang.String message) {
        this.geo = geo;
        this.message = message;
    }

    /**
     * Gets the geo value for this Arrayresult2.
     * 
     * @return geo
     */
    public java.lang.String[] getGeo() {
        return geo;
    }

    /**
     * Sets the geo value for this Arrayresult2.
     * 
     * @param geo
     */
    public void setGeo(java.lang.String[] geo) {
        this.geo = geo;
    }

    /**
     * Gets the message value for this Arrayresult2.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }

    /**
     * Sets the message value for this Arrayresult2.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }
    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseArrayFromWS)) {
            return false;
        }
        ResponseArrayFromWS other = (ResponseArrayFromWS) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.geo == null && other.getGeo() == null)
                || (this.geo != null
                && java.util.Arrays.equals(this.geo, other.getGeo())))
                && ((this.message == null && other.getMessage() == null)
                || (this.message != null
                && this.message.equals(other.getMessage())));
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
        if (getGeo() != null) {
            for (int i = 0;
                    i < java.lang.reflect.Array.getLength(getGeo());
                    i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGeo(), i);
                if (obj != null
                        && !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ResponseArrayFromWS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(UrlProvGeo.getUrlProvGeo()+UrlProvGeo.getPathSitiData()+"/wsSitidataStandar", "arrayresult2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "geo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     * @return 
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     * @param mechType 
     * @param _javaType 
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
     * @param mechType 
     * @param _javaType
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
