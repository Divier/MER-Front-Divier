/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author John Arevalo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {

    /**
     * 
     * @return name given to this array in the .pcml file
     */
    String pcmlName();

    /**
     * Fill the param with this String.
     * @return String used when it is necesary fill the param with fixed length
     */
    String completeWith() default "";

    /**
     * This param is used to fill this value with {@link #completeWith()}
     * @return max length for this data.
     */
    int length() default 0;

    /**
     * Specifies the usage of this param in the pcml. 
     * Default value is {@link UsageType#INPUTOUTPUT}
     * @return Usage of this param. 
     * @see UsageType
     */
    public UsageType usage() default UsageType.INPUTOUTPUT;
}
