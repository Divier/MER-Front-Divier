/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.jpa.entities.EstadoHhpp;
import co.com.claro.mgl.jpa.entities.HhppMgl;

/**
 *
 * @author rodriguezluim
 */
public class CmtEstadoHhppMglManager {

    public EstadoHhpp cambioEstadoLiberacionHhpp(HhppMgl hhppMgl) {
        //Logica para validar si se puede liberar un nodo de hhpps
        EstadoHhpp temporal = new EstadoHhpp();
        temporal.setEhhNombre("RESPUESTA TEMPORAL REST");
        return temporal;
    }

    public EstadoHhpp cambioEstadoHhpp(HhppMgl hhppMgl, EstadoHhpp estadoHhpp) {
        //Logica para validar si se puede liberar un nodo de hhpps
        EstadoHhpp temporal = new EstadoHhpp();
        temporal.setEhhNombre("RESPUESTA TEMPORAL REST");
        return temporal;
    }
}
