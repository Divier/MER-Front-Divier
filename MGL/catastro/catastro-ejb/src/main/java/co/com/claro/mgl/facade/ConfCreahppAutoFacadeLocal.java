/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfCreahppAuto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;

/**
 * Expone la informacion de la configuracion de para creacion de HHPP. Permite
 * obtener la informacion para conocer la configuracion de las ciudades para la
 * creacion autmatica de los HHPP desde la solicitud.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
public interface ConfCreahppAutoFacadeLocal extends BaseFacadeLocal<ConfCreahppAuto>{

    /**
     * Obtien la configuracion para una ciudad.Permite obtener los datos de
        configuracion para la creacion automatica de un HHPP en la creacion de la
        solicitud para una ciudad determinada.
     * @author Johnnatan Ortiz
     * @param city ciudad de la cual se desea consultar la configuracion.
     * @return configuracion de creacion automatica de la ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    ConfCreahppAuto findByIdCity(GeograficoPoliticoMgl city)
            throws ApplicationException;

    /**
     * Obtiene la configuracion para una comunidad.Permite obtener los datos de
        configuracion para la creacion automatica de un HHPP en la creacion de la
        solicitud para una ciudad por medio del codigo de la comunidad.
     * @author Johnnatan Ortiz
     * @param codComunidad codigo de la cominidad de la cual se desea consultar
     * la configuracion.
     * @return configuracion de creacion automatica de la comunidad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    ConfCreahppAuto findByCodComunidad(String codComunidad)
            throws ApplicationException;
}
