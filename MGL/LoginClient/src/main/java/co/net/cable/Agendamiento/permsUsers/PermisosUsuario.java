/**
 * PermisosUsuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package co.net.cable.Agendamiento.permsUsers;

public class PermisosUsuario  implements java.io.Serializable {
    private java.lang.String errorNo;

    private java.lang.String errorDesc;

    private java.lang.String permisos;

    public PermisosUsuario() {
    }

    public PermisosUsuario(
           java.lang.String errorNo,
           java.lang.String errorDesc,
           java.lang.String permisos) {
           this.errorNo = errorNo;
           this.errorDesc = errorDesc;
           this.permisos = permisos;
    }


    /**
     * Gets the errorNo value for this PermisosUsuario.
     * 
     * @return errorNo
     */
    public java.lang.String getErrorNo() {
        return errorNo;
    }


    /**
     * Sets the errorNo value for this PermisosUsuario.
     * 
     * @param errorNo
     */
    public void setErrorNo(java.lang.String errorNo) {
        this.errorNo = errorNo;
    }


    /**
     * Gets the errorDesc value for this PermisosUsuario.
     * 
     * @return errorDesc
     */
    public java.lang.String getErrorDesc() {
        return errorDesc;
    }


    /**
     * Sets the errorDesc value for this PermisosUsuario.
     * 
     * @param errorDesc
     */
    public void setErrorDesc(java.lang.String errorDesc) {
        this.errorDesc = errorDesc;
    }


    /**
     * Gets the permisos value for this PermisosUsuario.
     * 
     * @return permisos
     */
    public java.lang.String getPermisos() {
        return permisos;
    }


    /**
     * Sets the permisos value for this PermisosUsuario.
     * 
     * @param permisos
     */
    public void setPermisos(java.lang.String permisos) {
        this.permisos = permisos;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PermisosUsuario)) return false;
        PermisosUsuario other = (PermisosUsuario) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errorNo==null && other.getErrorNo()==null) || 
             (this.errorNo!=null &&
              this.errorNo.equals(other.getErrorNo()))) &&
            ((this.errorDesc==null && other.getErrorDesc()==null) || 
             (this.errorDesc!=null &&
              this.errorDesc.equals(other.getErrorDesc()))) &&
            ((this.permisos==null && other.getPermisos()==null) || 
             (this.permisos!=null &&
              this.permisos.equals(other.getPermisos())));
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
        if (getPermisos() != null) {
            _hashCode += getPermisos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PermisosUsuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Agendamiento.cable.net.co/permsUsers", "permisosUsuario"));
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
        elemField.setFieldName("permisos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Permisos"));
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
     * @param _xmlType
     * @return 
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     * @param _xmlType
     * @return 
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
