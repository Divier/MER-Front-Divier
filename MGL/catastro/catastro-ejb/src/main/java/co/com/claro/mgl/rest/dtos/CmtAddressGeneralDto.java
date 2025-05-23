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
@XmlRootElement(name = "addressGeneral")
public class CmtAddressGeneralDto {

    @XmlElement
    private CmtDireccionGeneralTabuladaDto splitAddres;
    @XmlElement
    private boolean dirAlterna;

    public CmtDireccionGeneralTabuladaDto getSplitAddres() {
        return splitAddres;
    }

    public void setSplitAddres(CmtDireccionGeneralTabuladaDto splitAddres) {
        this.splitAddres = splitAddres;
    }

    public boolean isDirAlterna() {
        return dirAlterna;
    }

    public void setDirAlterna(boolean dirAlterna) {
        this.dirAlterna = dirAlterna;
    }
    
    
}
