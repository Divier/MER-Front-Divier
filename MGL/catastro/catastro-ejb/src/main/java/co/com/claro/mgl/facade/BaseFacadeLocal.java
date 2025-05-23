/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import java.util.List;

/**
 *
 * @author User
 * @param <T>
 */
public interface BaseFacadeLocal<T> {

    List<T> findAll() throws ApplicationException;

    T create(T t) throws ApplicationException;

    T update(T t) throws ApplicationException;

    boolean delete(T t) throws ApplicationException;

    T findById(T sqlData) throws ApplicationException;
}
