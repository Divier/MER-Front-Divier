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
@XmlRootElement(name = "node")
public class CmtNodeDto {

    @XmlElement
    private String codeNode;
    @XmlElement
    private String state;
    @XmlElement
    private String qualificationDate;
    @XmlElement
    private String nodeName;
    @XmlElement
    private String technology;
    
    
    public String getCodeNode() {
        return codeNode;
    }

    public void setCodeNode(String codeNode) {
        this.codeNode = codeNode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(String qualificationDate) {
        this.qualificationDate = qualificationDate;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /*
     * Obtiene tecnologia nodo
     * getTechnology()
     * @return String
     */    
    public String getTechnology() {
        return technology;
    }
    /*
     * setea nivel tecnologia nodo
     * setTechnology()
     * @Param String 
     */
    public void setTechnology(String technology) {
        this.technology = technology;
    }
    
    
}
