/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao;

import co.com.claro.mgl.error.ApplicationException;
import java.math.BigDecimal;

/**
 *
 * @author User
 * @param <T>
 */
public interface IGenericDao <T>{    
    T create(T t) throws ApplicationException;
    T createCm(T t,String usuario, int Perfil) throws ApplicationException;
    T find(BigDecimal id) throws ApplicationException;
    T update(T t) throws ApplicationException;
    T updateCm(T t,String usuario, int Perfil) throws ApplicationException;
    boolean delete(T t) throws ApplicationException;    
    boolean deleteCm(T t, String usuario, int Perfil) throws ApplicationException;    
}
