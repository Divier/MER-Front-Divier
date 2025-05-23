/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase nodo
 *
 * @author Davide Marangoni
 * @version version 1.2
 * @date 2013/09/06
 */
public class Nodo implements Serializable {

    String nodo = "";
    private BigDecimal idNodo;

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * @return the idNodo
     */
    public BigDecimal getIdNodo() {
        return idNodo;
    }

    /**
     * @param idNodo the idNodo to set
     */
    public void setIdNodo(BigDecimal idNodo) {
        this.idNodo = idNodo;
    }
}
