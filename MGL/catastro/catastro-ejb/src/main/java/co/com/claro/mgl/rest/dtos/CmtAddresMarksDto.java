/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "addresMarks")
public class CmtAddresMarksDto {

    @XmlElement
    private String markId;
    @XmlElement
    private String descriptionMark;

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getDescriptionMark() {
        return descriptionMark;
    }

    public void setDescriptionMark(String descriptionMark) {
        this.descriptionMark = descriptionMark;
    }
}
