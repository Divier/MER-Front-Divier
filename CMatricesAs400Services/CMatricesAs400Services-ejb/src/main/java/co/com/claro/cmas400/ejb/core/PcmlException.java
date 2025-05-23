/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

/**
 *
 * @author John Arevalo
 */
public class PcmlException extends Exception {
    
    /**
     * Constructor.
     * @param message Mensaje.
     */
    public PcmlException(String message) {
        super(message);
    }
    
    
    /**
     * Constructor.
     * @param cause Causa de la excepci&oacute;n.
     */
    public PcmlException(Throwable cause) {
        super(cause);
    }
    
    
    /**
     * Constructor.
     * @param message Mensaje.
     * @param cause Causa de la excepci&oacute;n.
     */
    public PcmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
