/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "ccmmMarks")
public class CmtCcmmMarksDto {

    @XmlElement
    private BigDecimal markId;
    @XmlElement
    private String descriptionMark;
    @XmlElement
    private String markCode;

    public BigDecimal getMarkId() {
        return markId;
    }

    public void setMarkId(BigDecimal markId) {
        this.markId = markId;
    }

    public String getDescriptionMark() {
        return descriptionMark;
    }

    public void setDescriptionMark(String descriptionMark) {
        this.descriptionMark = descriptionMark;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }
}
