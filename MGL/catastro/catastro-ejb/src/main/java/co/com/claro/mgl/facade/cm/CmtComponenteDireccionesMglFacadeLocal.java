/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.rest.dtos.CmtConfiguracionDto;
import co.com.claro.mgl.rest.dtos.CmtConfigurationAddressComponentDto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author camargomf
 */
@Local
public interface CmtComponenteDireccionesMglFacadeLocal {

    ConfigurationAddressComponent getConfiguracionComponente() 
            throws ApplicationException;
    
    ConfigurationAddressComponent getConfiguracionComponente(
            String tipoRed,  BigDecimal tipoTecnologiaBasicaId)
            throws ApplicationException;
    
        ConfigurationAddressComponent getConfiguracionComponente(
            String identificadorApp)
            throws ApplicationException;

   /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     * @param  cmtConfiguracionDto
     * @author Victor Bocanegra
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    CmtConfigurationAddressComponentDto getConfiguracionComponente(
            CmtConfiguracionDto cmtConfiguracionDto)
            throws ApplicationException;
    
    
    List<OpcionIdNombre>  obtenerCalleCarreraList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException;

    List<OpcionIdNombre>  obtenerBarrioManzanaList(
            ConfigurationAddressComponent configurationAddressComponent) 
            throws ApplicationException;

    List<OpcionIdNombre>  obtenerIntraducibleList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException;

    List<OpcionIdNombre>  obtenerApartamentoList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException;

    List<OpcionIdNombre>  obtenerComplementoList(
            ConfigurationAddressComponent configurationAddressComponent)
            throws ApplicationException;
    
    List<OpcionIdNombre> traerListasPorTipo(String tipo)
            throws ApplicationException;
}
