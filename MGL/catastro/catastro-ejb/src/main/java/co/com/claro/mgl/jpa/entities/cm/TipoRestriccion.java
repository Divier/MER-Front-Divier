/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

/**
 *
 * @author Admin
 */
public enum TipoRestriccion {

    DISPONIBLE("Disponible"),
    NO_DISPONIBLE("No Disponible"),
    RESTRICCION("Restricción");
    
    
    public String tipoRestriccion; 
    
    private TipoRestriccion(String tipoRestriccion){
        this.tipoRestriccion = tipoRestriccion; 
    }   
    
    public String getTipoRestriccion(){
        return tipoRestriccion; 
    }
}
