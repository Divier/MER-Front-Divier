package co.com.claro.mgl.facade;

import co.com.claro.cmas400.ejb.request.RequestDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.respons.ResponseDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.dto.CodigoRazonSubrazon;
import co.com.claro.mgl.businessmanager.address.HhppVirtualMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Fachada de exposición de procesos asociados a hhpp virtual
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/05/23
 */

@Stateless
public class HhppVirtualMglFacade implements HhppVirtualMglFacadeLocal {


    @Override
    public String findTiempoMinimoCuenta() throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.findTiempoMinimoCuenta();
    }

    @Override
    public Optional<ResponseDataValidaRazonesCreaHhppVt> callServiceValidateMoveReasonsResource(RequestDataValidaRazonesCreaHhppVt requestDataValidaRazonesCreaHhppVt) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.callServiceValidateMoveReasonsResource(requestDataValidaRazonesCreaHhppVt);
    }

    @Override
    public Optional<ResponseDataCreaHhppVirtual> callServiceCreateVirtualHhppResource(RequestDataCreaHhppVirtual requestDataCreaHhppVirtual) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.callServiceCreateVirtualHhppResource(requestDataCreaHhppVirtual);
    }

    @Override
    public boolean validateMarcacionesHomePassed(List<CodigoRazonSubrazon> codigoRazonSubrazonList) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateMarcacionesHomePassed(codigoRazonSubrazonList);
    }

    @Override
    public boolean validateEstadoHhppRr(String estadoHhpp) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateEstadoHhppRr(estadoHhpp);
    }

    @Override
    public boolean validateBloqueoHomePassed(ResponseDataValidaRazonesCreaHhppVt responseDataValidaRazonesCreaHhppVt)
            throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateBloqueoHomePassed(responseDataValidaRazonesCreaHhppVt);
    }

    @Override
    public boolean validateEstadoCuenta(String estadoCuenta) {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateEstadoCuenta(estadoCuenta);
    }

    @Override
    public boolean validateExistenciaDireccion(String codigoRespuestaHhpp) {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateExistenciaDireccion(codigoRespuestaHhpp);
    }

    @Override
    public boolean validateVigenciaFechaActivacionCuenta(String fechaActivacionCuenta) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateVigenciaFechaActivacionCuenta(fechaActivacionCuenta);
    }

    @Override
    public boolean validateExistenciaCuentaClienteTrasladar(String estadoCuenta) {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateExistenciaCuentaClienteTrasladar(estadoCuenta);
    }

    @Override
    public Map<String, Object> validateTrasladoHhppBloqueado(ResponseDataValidaRazonesCreaHhppVt responseValidateReasonsRs,
            String numeroCuenta) throws ApplicationException {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.validateTrasladoHhppBloqueado(responseValidateReasonsRs, numeroCuenta);
    }

    @Override
    public Optional<RequestDataValidaRazonesCreaHhppVt> generateRequestForServiceValidate(HhppMgl hhppTraslado, String numCuentaClienteTraslado) {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.generateRequestForServiceValidate(hhppTraslado, numCuentaClienteTraslado);
    }

    @Override
    public boolean isActiveCcmmTrasladoHhppBloqueado() {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.isActiveCcmmTrasladoHhppBloqueado();
    }

    @Override
    public boolean isActiveHhppTrasladoHhppBloqueado() {
        HhppVirtualMglManager hhppVirtualMglManager = new HhppVirtualMglManager();
        return hhppVirtualMglManager.isActiveHhppTrasladoHhppBloqueado();
    }

}
