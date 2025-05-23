package co.com.claro.mgl.dao.impl;

import co.com.claro.cmas400.ejb.request.RequestDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.*;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias asociadas a validaciones impl de creación de HHPP Virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/08/29
 */
class HhppVirtualMglDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger(HhppVirtualMglDaoImplTest.class);

    //URI RSResourcesLocationRest QA
    private final String URI_VALIDATE_REASONS_RESOURCE =
            "http://100.123.246.49:8080/RSResourcesLocation-web/RSResourcesLocationRest/";

    /**
     * Validar que al pasar valores nulos se genere una excepción controlada.
     */
    @Test
    void validateBloqueoHomePassedWhenResponseAndCodigosBloqueoListAreNull() {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        assertThrows(ApplicationException.class,
                () -> hhppVirtualMglDao.validateBloqueoHomePassed(null, null));
    }

    /**
     * Validar conectividad del servicio Rest ValidateMoveReasonsResource
     *
     * @throws ApplicationException Excepción de la App
     */
    @Disabled("Se deshabilita ya que solo debe usar cuando se tenga conectividad con el servicio")
    @Test
    void callServiceValidateMoveReasonsResource() throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        RequestDataValidaRazonesCreaHhppVt request = new RequestDataValidaRazonesCreaHhppVt();
        request.setCuenta("70726233");
        request.setIdentificadorCalle("CR 3");
        request.setIdentificadorCasa("61-76");
        request.setIdentificadorApartamento("CASA");
        request.setDivisionalRegion("TVC");
        request.setComunidad("BOG");
        ResponseValidateMoveReasonsResource response = hhppVirtualMglDao
                .callServiceValidateMoveReasonsResource(request, URI_VALIDATE_REASONS_RESOURCE);

        LOGGER.info(response);
        assertEquals("OK", response.getHeaderResponse().getStatusCode());
    }

    /**
     * Validar conectividad del servicio Rest createVirtualHHPPResource
     *
     * @throws ApplicationException Excepción de la App
     */
    @Disabled("Se deshabilita ya que solo debe usar cuando se tenga conectividad con el servicio")
    @Test
    void callServiceCreateVirtualHhppResource() throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        RequestDataCreaHhppVirtual requestDataCreaHhppVirtual = new RequestDataCreaHhppVirtual();
        requestDataCreaHhppVirtual.setCuenta("70726233");
        requestDataCreaHhppVirtual.setCodigoDivision("RCE");
        requestDataCreaHhppVirtual.setCodigoComunidad("ACA");
        requestDataCreaHhppVirtual.setCalle("CR 35");
        requestDataCreaHhppVirtual.setNumeroCasa("11-67");
        requestDataCreaHhppVirtual.setApartamentoHhppPrimario("CASA");
        requestDataCreaHhppVirtual.setApartamentoHhppVirtual("CASA");
        requestDataCreaHhppVirtual.setUsuarioPeticion("MBARBOSA1");

        ResponseCreateVirtualHhppResource response = hhppVirtualMglDao
                .callServiceCreateVirtualHhppResource(requestDataCreaHhppVirtual, URI_VALIDATE_REASONS_RESOURCE);
        LOGGER.info(response);

        Optional<HeaderErrorResponseBase> headerErrorResponseBase = Optional.ofNullable(response)
                .map(ResponseCreateVirtualHhppResource::getHeaderResponse)
                .map(HeaderResponseBase::getHeaderError);

        assertFalse(headerErrorResponseBase.isPresent());

        Optional<String> codRespuesta = Optional.ofNullable(response)
                .map(ResponseCreateVirtualHhppResource::getCreateVirtualHhppResourceResponse)
                .map(ResponseDataCreaHhppVirtual::getCodigoRespuesta);

        assertTrue(codRespuesta.isPresent());
    }
}