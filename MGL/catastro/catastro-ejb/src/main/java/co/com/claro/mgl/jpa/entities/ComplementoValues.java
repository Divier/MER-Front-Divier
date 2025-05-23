/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jdhernandez
 */
@XmlRootElement(name = "complementoValues")
public class ComplementoValues {

    /* Variables */
    @XmlElement 
    private List<OpcionIdNombre> complementoDir = new ArrayList<OpcionIdNombre>();

    
    /**
     * Constructor de la clase
     */
    public ComplementoValues() {

    }

    /* Getters and Setters */
    public List<OpcionIdNombre> getComplementoDir() {
        return complementoDir;
    }

    public void setComplementoDir(List<OpcionIdNombre> complementoDir) {
        this.complementoDir = complementoDir;
    }
}