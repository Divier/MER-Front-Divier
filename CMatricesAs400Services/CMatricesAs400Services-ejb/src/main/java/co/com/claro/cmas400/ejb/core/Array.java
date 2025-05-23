/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author John Arevalo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Array {

    /**
     * 
     * @return The length of this array. Given by the attribute "count" in data 
     * element.
     */
    int size();

    /**
     * 
     * @return name given to this array in the .pcml file.
     */
    String pcmlName();

    Class<?> type();

    /**
     * Specifies the usage of this param in the pcml. 
     * Default value is {@link UsageType#INPUTOUTPUT}
     * @return Usage of this param. 
     * @see UsageType
     */
    public UsageType usage() default UsageType.INPUTOUTPUT;
}
