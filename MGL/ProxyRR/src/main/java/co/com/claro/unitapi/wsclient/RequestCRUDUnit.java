/**
 * RequestCRUDUnit.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.com.claro.unitapi.wsclient;

public class RequestCRUDUnit extends BasicMessage implements java.io.Serializable {

    private java.lang.String caby;

    private java.lang.String comunity;

    private java.lang.String division;

    private java.lang.String mode;

    private java.lang.String restriction;

    private java.lang.String sipo;

    private java.lang.String streetDirection;

    private java.lang.String streetName;

    private java.lang.String streetTittle;

    private java.lang.String streetType;

    private java.lang.String unitHigh;

    private java.lang.String unitLow;

    public RequestCRUDUnit() {
    }

    public RequestCRUDUnit(
            java.lang.String caby,
            java.lang.String comunity,
            java.lang.String division,
            java.lang.String mode,
            java.lang.String restriction,
            java.lang.String sipo,
            java.lang.String streetDirection,
            java.lang.String streetName,
            java.lang.String streetTittle,
            java.lang.String streetType,
            java.lang.String unitHigh,
            java.lang.String unitLow) {
        this.caby = caby;
        this.comunity = comunity;
        this.division = division;
        this.mode = mode;
        this.restriction = restriction;
        this.sipo = sipo;
        this.streetDirection = streetDirection;
        this.streetName = streetName;
        this.streetTittle = streetTittle;
        this.streetType = streetType;
        this.unitHigh = unitHigh;
        this.unitLow = unitLow;
    }

    /**
     * Gets the caby value for this RequestCRUDUnit.
     *
     * @return caby
     */
    public java.lang.String getCaby() {
        return caby;
    }

    /**
     * Sets the caby value for this RequestCRUDUnit.
     *
     * @param caby
     */
    public void setCaby(java.lang.String caby) {
        this.caby = caby;
    }

    /**
     * Gets the comunity value for this RequestCRUDUnit.
     *
     * @return comunity
     */
    public java.lang.String getComunity() {
        return comunity;
    }

    /**
     * Sets the comunity value for this RequestCRUDUnit.
     *
     * @param comunity
     */
    public void setComunity(java.lang.String comunity) {
        this.comunity = comunity;
    }

    /**
     * Gets the division value for this RequestCRUDUnit.
     *
     * @return division
     */
    public java.lang.String getDivision() {
        return division;
    }

    /**
     * Sets the division value for this RequestCRUDUnit.
     *
     * @param division
     */
    public void setDivision(java.lang.String division) {
        this.division = division;
    }

    /**
     * Gets the mode value for this RequestCRUDUnit.
     *
     * @return mode
     */
    public java.lang.String getMode() {
        return mode;
    }

    /**
     * Sets the mode value for this RequestCRUDUnit.
     *
     * @param mode
     */
    public void setMode(java.lang.String mode) {
        this.mode = mode;
    }

    /**
     * Gets the restriction value for this RequestCRUDUnit.
     *
     * @return restriction
     */
    public java.lang.String getRestriction() {
        return restriction;
    }

    /**
     * Sets the restriction value for this RequestCRUDUnit.
     *
     * @param restriction
     */
    public void setRestriction(java.lang.String restriction) {
        this.restriction = restriction;
    }

    /**
     * Gets the sipo value for this RequestCRUDUnit.
     *
     * @return sipo
     */
    public java.lang.String getSipo() {
        return sipo;
    }

    /**
     * Sets the sipo value for this RequestCRUDUnit.
     *
     * @param sipo
     */
    public void setSipo(java.lang.String sipo) {
        this.sipo = sipo;
    }

    /**
     * Gets the streetDirection value for this RequestCRUDUnit.
     *
     * @return streetDirection
     */
    public java.lang.String getStreetDirection() {
        return streetDirection;
    }

    /**
     * Sets the streetDirection value for this RequestCRUDUnit.
     *
     * @param streetDirection
     */
    public void setStreetDirection(java.lang.String streetDirection) {
        this.streetDirection = streetDirection;
    }

    /**
     * Gets the streetName value for this RequestCRUDUnit.
     *
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }

    /**
     * Sets the streetName value for this RequestCRUDUnit.
     *
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets the streetTittle value for this RequestCRUDUnit.
     *
     * @return streetTittle
     */
    public java.lang.String getStreetTittle() {
        return streetTittle;
    }

    /**
     * Sets the streetTittle value for this RequestCRUDUnit.
     *
     * @param streetTittle
     */
    public void setStreetTittle(java.lang.String streetTittle) {
        this.streetTittle = streetTittle;
    }

    /**
     * Gets the streetType value for this RequestCRUDUnit.
     *
     * @return streetType
     */
    public java.lang.String getStreetType() {
        return streetType;
    }

    /**
     * Sets the streetType value for this RequestCRUDUnit.
     *
     * @param streetType
     */
    public void setStreetType(java.lang.String streetType) {
        this.streetType = streetType;
    }

    /**
     * Gets the unitHigh value for this RequestCRUDUnit.
     *
     * @return unitHigh
     */
    public java.lang.String getUnitHigh() {
        return unitHigh;
    }

    /**
     * Sets the unitHigh value for this RequestCRUDUnit.
     *
     * @param unitHigh
     */
    public void setUnitHigh(java.lang.String unitHigh) {
        this.unitHigh = unitHigh;
    }

    /**
     * Gets the unitLow value for this RequestCRUDUnit.
     *
     * @return unitLow
     */
    public java.lang.String getUnitLow() {
        return unitLow;
    }

    /**
     * Sets the unitLow value for this RequestCRUDUnit.
     *
     * @param unitLow
     */
    public void setUnitLow(java.lang.String unitLow) {
        this.unitLow = unitLow;
    }

    private java.lang.Object __equalsCalc = null;

    @Override
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof RequestCRUDUnit)) {
            return false;
        }
        RequestCRUDUnit other = (RequestCRUDUnit) obj;
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj)
                && ((this.caby == null && other.getCaby() == null)
                        || (this.caby != null
                                && this.caby.equals(other.getCaby())))
                && ((this.comunity == null && other.getComunity() == null)
                        || (this.comunity != null
                                && this.comunity.equals(other.getComunity())))
                && ((this.division == null && other.getDivision() == null)
                        || (this.division != null
                                && this.division.equals(other.getDivision())))
                && ((this.mode == null && other.getMode() == null)
                        || (this.mode != null
                                && this.mode.equals(other.getMode())))
                && ((this.restriction == null && other.getRestriction() == null)
                        || (this.restriction != null
                                && this.restriction.equals(other.getRestriction())))
                && ((this.sipo == null && other.getSipo() == null)
                        || (this.sipo != null
                                && this.sipo.equals(other.getSipo())))
                && ((this.streetDirection == null && other.getStreetDirection() == null)
                        || (this.streetDirection != null
                                && this.streetDirection.equals(other.getStreetDirection())))
                && ((this.streetName == null && other.getStreetName() == null)
                        || (this.streetName != null
                                && this.streetName.equals(other.getStreetName())))
                && ((this.streetTittle == null && other.getStreetTittle() == null)
                        || (this.streetTittle != null
                                && this.streetTittle.equals(other.getStreetTittle())))
                && ((this.streetType == null && other.getStreetType() == null)
                        || (this.streetType != null
                                && this.streetType.equals(other.getStreetType())))
                && ((this.unitHigh == null && other.getUnitHigh() == null)
                        || (this.unitHigh != null
                                && this.unitHigh.equals(other.getUnitHigh())))
                && ((this.unitLow == null && other.getUnitLow() == null)
                        || (this.unitLow != null
                                && this.unitLow.equals(other.getUnitLow())));
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
        if (getCaby() != null) {
            _hashCode += getCaby().hashCode();
        }
        if (getComunity() != null) {
            _hashCode += getComunity().hashCode();
        }
        if (getDivision() != null) {
            _hashCode += getDivision().hashCode();
        }
        if (getMode() != null) {
            _hashCode += getMode().hashCode();
        }
        if (getRestriction() != null) {
            _hashCode += getRestriction().hashCode();
        }
        if (getSipo() != null) {
            _hashCode += getSipo().hashCode();
        }
        if (getStreetDirection() != null) {
            _hashCode += getStreetDirection().hashCode();
        }
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        if (getStreetTittle() != null) {
            _hashCode += getStreetTittle().hashCode();
        }
        if (getStreetType() != null) {
            _hashCode += getStreetType().hashCode();
        }
        if (getUnitHigh() != null) {
            _hashCode += getUnitHigh().hashCode();
        }
        if (getUnitLow() != null) {
            _hashCode += getUnitLow().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            RequestCRUDUnit.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://unit.telmex.net/", "requestCRUDUnit"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caby");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caby"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comunity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("division");
        elemField.setXmlName(new javax.xml.namespace.QName("", "division"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("restriction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "restriction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetDirection");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetDirection"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetTittle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetTittle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streetType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitHigh");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitHigh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitLow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitLow"));
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
