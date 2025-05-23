package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;
import java.util.Optional;

/**
 * Permite consultar los nodos a partir de una dirección a través de georeferenciación con SITIDATA
 *
 * @author Gildardo Mora
 * @version 1.0 , 2022/02/16
 */
public class ConsultaNodoSitiData {
    private static final Logger LOGGER = LogManager.getLogger(ConsultaNodoSitiData.class);

    /**
     * Se encarga de obtener los nodos siti data de la dirección
     *
     * @param drDireccion   Detalles de la dirección
     * @param centroPoblado Información del centro poblado
     * @return response
     * @throws ApplicationException Excepcion personalizada de la aplicación
     */
    public AddressService findNodosSitiData(DrDireccion drDireccion, GeograficoPoliticoMgl centroPoblado)
            throws ApplicationException {

        String direccion;
        String barrio;
        AddressRequest addressRequest = new AddressRequest();
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();

        if (Objects.isNull(drDireccion) || Objects.isNull(centroPoblado)) {
            LOGGER.info("No hay datos validos asociados para consultar a SITIDATA");
            AddressService response = new AddressService();
            return generateDefaultResponse(response);
        }

        //consulta los datos de la dirección
        direccion = drDireccionManager.getDireccion(drDireccion);

        if (Objects.nonNull(drDireccion.getBarrio())
                && centroPoblado.getGpoMultiorigen().equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
                && drDireccion.getIdTipoDireccion().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
            barrio = drDireccion.getBarrio();
        } else {
            barrio = "";
        }

        addressRequest.setLevel(Constant.WS_ADDRESS_CONSULTA_COMPLETA);
        addressRequest.setCity(centroPoblado.getGeoCodigoDane());
        addressRequest.setState("");
        addressRequest.setCodDaneVt(centroPoblado.getGeoCodigoDane());
        addressRequest.setNeighborhood(barrio);
        addressRequest.setAddress(direccion);
        AddressService responseQueryAddress = addressEJBRemote.queryAddress(addressRequest);

        //Si la ciudad es multiorigen el barrio debe coincidir con el que entrega el geo para retornar las coberturas
        if (Objects.nonNull(responseQueryAddress)
                && centroPoblado.getGpoMultiorigen().equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
                && drDireccion.getIdTipoDireccion().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {

            if (Objects.nonNull(responseQueryAddress.getNeighborhood())
                    && responseQueryAddress.getNeighborhood().equalsIgnoreCase(barrio)) {
                return responseQueryAddress;
            }

            return generateDefaultResponse(responseQueryAddress);
        }

        String recommendations = Optional.ofNullable(responseQueryAddress).map(AddressService::getRecommendations).orElse("");

        if (recommendations.startsWith("ERROR:")) {
            String msg = recommendations + " Por favor intente de nuevo mas tarde..";
            throw new ApplicationException(msg);
        }
        return responseQueryAddress;
    }

    private  AddressEJBRemote lookupaddressEJBBean() throws ApplicationException {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new ApplicationException(ne);
        }
    }

    /**
     * Genera la respuesta por defecto de la información de nodos
     *
     * @return responseQueryAddress {@link AddressService}
     */
    private  AddressService generateDefaultResponse(AddressService responseQueryAddress) {
        responseQueryAddress.setNodoDos("");
        responseQueryAddress.setNodoDth("");
        responseQueryAddress.setNodoFtth("");
        responseQueryAddress.setNodoMovil("");
        responseQueryAddress.setNodoTres("");
        responseQueryAddress.setNodoUno("");
        responseQueryAddress.setNodoCuatro("");
        responseQueryAddress.setNodoWifi("");
        responseQueryAddress.setNodoZona3G("");
        responseQueryAddress.setNodoZona4G("");
        responseQueryAddress.setNodoZona5G("");
        responseQueryAddress.setNodoZonaCoberturaCavs("");
        responseQueryAddress.setNodoZonaCoberturaUltimaMilla("");
        responseQueryAddress.setNodoZonaCurrier("");
        responseQueryAddress.setNodoZonaGponDiseniado("");
        responseQueryAddress.setNodoZonaMicroOndas("");
        responseQueryAddress.setNodoZonaUnifilar("");
        return responseQueryAddress;
    }
}
