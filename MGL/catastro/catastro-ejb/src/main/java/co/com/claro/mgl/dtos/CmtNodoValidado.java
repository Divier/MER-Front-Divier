/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.visitasTecnicas.business.NodoRR;

/**
 *
 * @author ADMIN
 */
public class CmtNodoValidado {
    private NodoRR nodeRR=new NodoRR();
    private NodoMgl nodoMgl = new NodoMgl();
    private String message="";

    public NodoRR getNodeRR() {
        return nodeRR;
    }

    public void setNodeRR(NodoRR nodeRR) {
        this.nodeRR = nodeRR;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
