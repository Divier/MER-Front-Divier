/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

/**
 *
 * @author ortizjaf
 */
public enum UsageType {
    
    INPUT("input"), INPUTOUTPUT("inputoutput"), OUTPUT("output");
    private final String value;

    private UsageType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    
}
