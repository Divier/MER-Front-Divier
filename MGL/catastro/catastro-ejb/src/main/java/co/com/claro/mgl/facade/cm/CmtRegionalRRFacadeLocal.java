/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public interface CmtRegionalRRFacadeLocal extends BaseCmFacadeLocal<CmtRegionalRr> {

    /**
     * Crea una Orden de Trabajo. Permite realizar la creacion de una Orden de
     * Trabajo en el repositorio.
     *
     * @author cardenaslb
     * @return lsita de regionales
     * @throws ApplicationException
     */
    List<CmtRegionalRr> findAllRegional() throws ApplicationException;
  /**
     * Crea una Orden de Trabajo. Permite realizar la creacion de una Orden de
     * Trabajo en el repositorio.
     *
     * @author cardenaslb
     * @return lsita de regionales
     * @throws ApplicationException
     */
    List<CmtRegionalRr> findAllRegionalActive() throws ApplicationException;
    /**
     * bocanegravm Metodo para consultar una regional por Id
     *
     * @param idRegional
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CmtRegionalRr findRegionalById(BigDecimal idRegional)
            throws ApplicationException;

    /**
     * bocanegravm Metodo para consultar una regional por codigo
     *
     * @param codigo
     * @return CmtRegionalRr
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CmtRegionalRr findRegionalByCod(String codigo)
            throws ApplicationException;
    
    /**
     * bocanegravm Metodo para consultar todas las regionales en BD
     *
     * @return List<CmtRegionalRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<CmtRegionalRr> findAllRegionales()
            throws ApplicationException;
}
