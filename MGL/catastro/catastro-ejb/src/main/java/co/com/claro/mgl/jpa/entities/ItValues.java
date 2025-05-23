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
 * @author camargomf
 */
@XmlRootElement(name = "itValues")
public class ItValues {

    /* Variables */
    @XmlElement 
    private List<OpcionIdNombre> intr1It = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> intr2It = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> intr3It = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> intr4It = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> intr5It = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> intr6It = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> placaIt = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> complementoIt = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> allItems = new ArrayList<OpcionIdNombre>();

    /* Getters and Setters */
    public List<OpcionIdNombre> getIntr1It() {
        return intr1It;
    }

    public void setIntr1It(List<OpcionIdNombre> intr1It) {
        this.intr1It = intr1It;
    }

    public List<OpcionIdNombre> getIntr2It() {
        return intr2It;
    }

    public void setIntr2It(List<OpcionIdNombre> intr2It) {
        this.intr2It = intr2It;
    }

    public List<OpcionIdNombre> getIntr3It() {
        return intr3It;
    }

    public void setIntr3It(List<OpcionIdNombre> intr3It) {
        this.intr3It = intr3It;
    }

    public List<OpcionIdNombre> getIntr4It() {
        return intr4It;
    }

    public void setIntr4It(List<OpcionIdNombre> intr4It) {
        this.intr4It = intr4It;
    }

    public List<OpcionIdNombre> getIntr5It() {
        return intr5It;
    }

    public void setIntr5It(List<OpcionIdNombre> intr5It) {
        this.intr5It = intr5It;
    }

    public List<OpcionIdNombre> getIntr6It() {
        return intr6It;
    }

    public void setIntr6It(List<OpcionIdNombre> intr6It) {
        this.intr6It = intr6It;
    }

    public List<OpcionIdNombre> getComplementoIt() {
        return complementoIt;
    }

    public List<OpcionIdNombre> getPlacaIt() {
        return placaIt;
    }

    public void setPlacaIt(List<OpcionIdNombre> placaIt) {
        this.placaIt = placaIt;
    }

    public void setComplementoIt(List<OpcionIdNombre> complementoIt) {
        this.complementoIt = complementoIt;
    }

    public List<OpcionIdNombre> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<OpcionIdNombre> allItems) {
        this.allItems = allItems;
    }
    
    

}
