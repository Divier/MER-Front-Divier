/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;

/**
 *
 * @author rodriguezluim
 */
public interface CmtAvisoProgramadoMglFacadeLocal{
    
    /**
     * Crear un aviso programado para un HHPP. Este metodo permite crear un
     * aviso para cuando un HHPP cambie de estado a disponible genere un aviso a
     * TCRM
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param cmtAvisosProgramadosMgl. Objeto basico para la persistencia de el aviso programado
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    public CmtDefaultBasicResponse crearAvisoProgramadoHhpp(CmtAvisosProgramadosMgl cmtAvisosProgramadosMgl);
    
}
