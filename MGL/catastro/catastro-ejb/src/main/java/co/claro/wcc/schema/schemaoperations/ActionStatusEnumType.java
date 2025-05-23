
package co.claro.wcc.schema.schemaoperations;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actionStatusEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="actionStatusEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="success"/>
 *     &lt;enumeration value="fail"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "actionStatusEnumType")
@XmlEnum
public enum ActionStatusEnumType {

    @XmlEnumValue("success")
    SUCCESS("success"),
    @XmlEnumValue("fail")
    FAIL("fail");
    private final String value;

    ActionStatusEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionStatusEnumType fromValue(String v) {
        for (ActionStatusEnumType c: ActionStatusEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
