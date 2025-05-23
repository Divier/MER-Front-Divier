package net.telmex.unit.client;

import co.com.claro.utils.Constants;
import co.com.claro.utils.WsUtils;
import net.telmex.unit.dto.ModifyResponse;
import net.telmex.unit.exceptions.PcmlException_Exception;
import net.telmex.unit.implement.ModifyUnitRequest;
import net.telmex.unit.implement.UnitService;
import net.telmex.unit.management.Management;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.WebServiceException;

/**
 * Cliente para realizar el consumo de UnitService JAX-WS
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
public class UnitServiceClient {
    private final UnitService unitService;
    private Management management;

    private static final Logger LOGGER = LogManager.getLogger(UnitServiceClient.class);

    public UnitServiceClient(String urlWsdl) {

        try {
            unitService = new UnitService(WsUtils.getUrl(urlWsdl));
        } catch (WebServiceException e) {
            String msj = Constants.MODIFY_UNIT + ": Error al leer el WSDL";
            LOGGER.error(msj, e);
            throw new RuntimeException(e);
        }
    }

    public ModifyResponse ModifyUnit(ModifyUnitRequest modifyUnitRequest) {
        try {
            management = unitService.getManagementPort();
            return management.modifyUnit(modifyUnitRequest);
        } catch (PcmlException_Exception e) {
            String msj = "Se presento un error en el consumo de: "
                    + Constants.MODIFY_UNIT;
            LOGGER.error(msj, e);
            throw new RuntimeException(e);
        }
    }
}
