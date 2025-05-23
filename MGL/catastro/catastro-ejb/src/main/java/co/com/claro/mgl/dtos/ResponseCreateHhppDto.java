/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cardenaslb
 */
public class ResponseCreateHhppDto {
    private DrDireccion drDireccion;
    private ArrayList<String> validationMessages = new ArrayList<>();
    private Map<String, String> validationMessages2 = new HashMap<>();
    private boolean creacionExitosa = false;
    private boolean alreadyprocess = false;
  
    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public ArrayList<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(ArrayList<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public Map<String, String> getValidationMessages2() {
        return validationMessages2;
    }

    public void setValidationMessages2(Map<String, String> validationMessages2) {
        this.validationMessages2 = validationMessages2;
    }

    public boolean isCreacionExitosa() {
        return creacionExitosa;
    }

    public void setCreacionExitosa(boolean creacionExitosa) {
        this.creacionExitosa = creacionExitosa;
    }

    public boolean isAlreadyprocess() {
        return alreadyprocess;
    }

    public void setAlreadyprocess(boolean alreadyprocess) {
        this.alreadyprocess = alreadyprocess;
    }
    
    
    
    
    
    
}
