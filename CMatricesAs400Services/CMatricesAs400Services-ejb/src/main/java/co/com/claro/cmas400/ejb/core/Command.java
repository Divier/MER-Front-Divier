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
public @interface Command {

    String documentName();

    String programName();
}
