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
 * @author ortizjaf
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Program {

    /**
     * The document resource name of the PCML document for the programs to be
     * called. The resource name can be a package qualified name. For example,
     * "com.myCompany.myPackage.myPcml".
     *
     * @return The document resource name.
     * @see
     * com.ibm.as400.data.ProgramCallDocument#ProgramCallDocument(com.ibm.as400.access.AS400,
     * java.lang.String)
     */
    String documentName();

    /**
     *
     * @return The RPG program name
     */
    String programName();
}
