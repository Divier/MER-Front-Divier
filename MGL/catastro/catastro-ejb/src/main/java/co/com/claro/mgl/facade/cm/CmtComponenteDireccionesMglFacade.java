/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtComponenteDireccionesMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.rest.dtos.CmtConfiguracionDto;
import co.com.claro.mgl.rest.dtos.CmtConfigurationAddressComponentDto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author camargomf
 */
@Stateless
public class CmtComponenteDireccionesMglFacade implements CmtComponenteDireccionesMglFacadeLocal {

    private final CmtComponenteDireccionesMglManager cmtComponenteDireccionesMglManager
            = new CmtComponenteDireccionesMglManager();

    @Override
    public ConfigurationAddressComponent getConfiguracionComponente() throws ApplicationException {
        return cmtComponenteDireccionesMglManager.getConfiguracionComponente();
    }
    
    @Override
    public ConfigurationAddressComponent getConfiguracionComponente(
            String identificadorApp) throws ApplicationException {
         return cmtComponenteDireccionesMglManager.
                 getConfiguracionComponente(identificadorApp);
    }
    
    @Override
        public ConfigurationAddressComponent getConfiguracionComponente(
            String tipoRed, BigDecimal tipoTecnologiaBasicaId) throws ApplicationException {
         return cmtComponenteDireccionesMglManager.
                 getConfiguracionComponente(tipoRed, tipoTecnologiaBasicaId);
    }

    @Override
    public List<OpcionIdNombre> obtenerCalleCarreraList(
            ConfigurationAddressComponent configurationAddressComponent) 
            throws ApplicationException {
        return cmtComponenteDireccionesMglManager.
                obtenerCalleCarreraList(configurationAddressComponent);
    }
     
    @Override
     public List<OpcionIdNombre>  obtenerBarrioManzanaList(
            ConfigurationAddressComponent configurationAddressComponent)
             throws ApplicationException {
        return cmtComponenteDireccionesMglManager.
                obtenerBarrioManzanaList(configurationAddressComponent);
    }
      
    @Override
      public List<OpcionIdNombre>  obtenerIntraducibleList(
            ConfigurationAddressComponent configurationAddressComponent)
              throws ApplicationException {
        return cmtComponenteDireccionesMglManager.
                obtenerIntraducibleList(configurationAddressComponent);
    }
       
    @Override
       public List<OpcionIdNombre>  obtenerApartamentoList(
            ConfigurationAddressComponent configurationAddressComponent)
               throws ApplicationException {
        return cmtComponenteDireccionesMglManager.
                obtenerAptoList(configurationAddressComponent);
    }

    @Override
        public List<OpcionIdNombre>  obtenerComplementoList(
            ConfigurationAddressComponent configurationAddressComponent)
                throws ApplicationException {
        return cmtComponenteDireccionesMglManager.
                obtenerComplementoList(configurationAddressComponent);
}

    /**
     * Obtine todos los niveles para una direccion.Permite obtener todos los
 niveles necesarios para los diferentes niveles de una direccion segun el
 tipo de red
     *
     * @param cmtConfiguracionDto
     * @author Victor Bocanegra
     * @return Configuracion de los niveles de la direccion segun el tipo de red
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public CmtConfigurationAddressComponentDto getConfiguracionComponente(
            CmtConfiguracionDto cmtConfiguracionDto)
            throws ApplicationException {

        return cmtComponenteDireccionesMglManager.getConfiguracionComponente(cmtConfiguracionDto);
    }
    
     @Override
    public List<OpcionIdNombre> traerListasPorTipo(String tipo)
            throws ApplicationException {
        return cmtComponenteDireccionesMglManager.traerListasPorTipo(tipo);
    }
    
   
}
