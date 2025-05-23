package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ComponenteDireccionesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressService;
import java.math.BigDecimal;
import javax.ejb.Stateless;

/**
 * Delega las peticiones de base de datos, invoca los metodos necesarios para
 * insertar, consultar, editar y/o eliminar registros.
 * 
* @author alejandro.martine.ext@claro.com.co
 * 
* @versión 1.0
 */
@Stateless
public class ComponenteDireccionFacade implements ComponenteDireccionFacadeLocal {

    private final ComponenteDireccionesManager componenteDireccionManager;

    public ComponenteDireccionFacade() {
        this.componenteDireccionManager = new ComponenteDireccionesManager();
    }

    /**
     *
     * @param codCity
     * @return componenteDireccionManager
     * @throws ApplicationException
     */
    @Override
    public CityEntity getCityData(String codCity) throws ApplicationException {
        return componenteDireccionManager.getCityData(codCity);
    }

    /**
     *
     * @param detalleDireccionEntity
     * @param comunidad
     * @return componenteDireccionManager
     */
    @Override
    public BigDecimal findSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad) {
        return componenteDireccionManager.findSolicitudInProcess(detalleDireccionEntity, comunidad);
    }

    /**
     *
     * @param cityEntityRequest
     * @return
     */
    @Override
    public String validarDir(CityEntity cityEntityRequest) {
        return componenteDireccionManager.validarDir(cityEntityRequest);
    }

    /**
     *
     * @param cityEntityRequest
     * @return componenteDireccionManager
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public AddressService validarDirAlterna(CityEntity cityEntityRequest) throws ApplicationException {
        AddressService addressService = new AddressService();
        addressService = componenteDireccionManager.validarDirAlterna(cityEntityRequest);
        return addressService;
    }

    /**
     *
     * @param address
     * @param city
     * @param dpto
     * @param barrio
     * @param user
     * @return
     */
    @Override
    public String guardarDireccionRepo(String address, String city, String dpto, String barrio, String user, String codigoDane) {
        return componenteDireccionManager.guardarDireccionRepo(address, city, dpto, barrio, user, codigoDane);
    }
}
