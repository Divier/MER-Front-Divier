/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.core;

import javax.ejb.Local;

/**
 *
 * @author John Arevalo
 */
@Local
public interface ManagerLocal {

    void invoke(Object command) throws PcmlException;
}
