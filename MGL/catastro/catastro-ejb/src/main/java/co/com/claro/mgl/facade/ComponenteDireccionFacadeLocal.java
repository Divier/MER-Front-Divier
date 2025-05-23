
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressService;
import java.math.BigDecimal;

/**
 * La clase funciona como interfaz para los metodos implementados en 
 * ComponenteDireccionFacade.
 * 
 * @author alejandro.martine.ext@claro.com.co
 * 
 * @version 1.0
 */
public interface ComponenteDireccionFacadeLocal {
    
    public AddressService validarDirAlterna(CityEntity cityEntityRequest) throws ApplicationException;

    /**
     *
     * @param codCity
     * @return
     * @throws ApplicationException
     */
    public CityEntity getCityData(String codCity) throws ApplicationException;

    /**
     *
     * @param detalleDireccionEntity
     * @param comunidad
     * @return
     */
    public BigDecimal findSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad);

    /**
     *
     * @param cityEntityRequest
     * @return
     */
    public String validarDir(CityEntity cityEntityRequest);

    /**
     *
     * @param address
     * @param city
     * @param dpto
     * @param barrio
     * @param user
     * @param codigoDane
     * @return
     */
    public String guardarDireccionRepo(String address, String city, String dpto, String barrio, String user, String codigoDane);
    
}
