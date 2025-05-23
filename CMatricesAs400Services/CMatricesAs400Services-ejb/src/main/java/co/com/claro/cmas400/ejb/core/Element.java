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
public @interface Element {

    /**
     * El nombre del elemento pcml
     * @return
     */
    String pcmlName();

    /**
     * indica un String para completar una cadena
     * @return
     */
    String completeWith() default "";

    /**
     * indica el tamaño del elemento
     * @return
     */
    int size() default 0;
}
