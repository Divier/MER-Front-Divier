/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.ws.cm.response.ResponseValidacionViabilidad;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "hhpp")
public class CmtHhppDto {

    @XmlElement
    private BigDecimal hhppId;
    @XmlElement
    private String state;
    @XmlElement
    private String technology;
    @XmlElement
    private ResponseValidacionViabilidad viability;
    @XmlElement
    private CmtNodeDto node;
    @XmlElement
    private CmtSubCcmmTechnologyDto subCcmmTechnology;
    @XmlElement
    private List<CmtAddresMarksDto> listAddresMarks;
    @XmlElement
    private String constructionType;    
    @XmlElement
    private String rushtype;
    @XmlElement
    private List<String> notasHhpp;
    @XmlElement
    private String accountNumber;
    
    

    public BigDecimal getHhppId() {
        return hhppId;
    }

    public void setHhppId(BigDecimal hhppId) {
        this.hhppId = hhppId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public ResponseValidacionViabilidad getViability() {
        return viability;
    }

    public void setViability(ResponseValidacionViabilidad viability) {
        this.viability = viability;
    }

    public CmtNodeDto getNode() {
        return node;
    }

    public void setNode(CmtNodeDto node) {
        this.node = node;
    }

    public CmtSubCcmmTechnologyDto getSubCcmmTechnology() {
        return subCcmmTechnology;
    }

    public void setSubCcmmTechnology(CmtSubCcmmTechnologyDto subCcmmTechnology) {
        this.subCcmmTechnology = subCcmmTechnology;
    }

    public List<CmtAddresMarksDto> getListAddresMarks() {
        return listAddresMarks;
    }

    public void setListAddresMarks(List<CmtAddresMarksDto> listAddresMarks) {
        this.listAddresMarks = listAddresMarks;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public String getRushtype() {
        return rushtype;
    }

    public void setRushtype(String rushtype) {
        this.rushtype = rushtype;
    }

    public List<String> getNotasHhpp() {
        return notasHhpp;
    }

    public void setNotasHhpp(List<String> notasHhpp) {
        this.notasHhpp = notasHhpp;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    
}
