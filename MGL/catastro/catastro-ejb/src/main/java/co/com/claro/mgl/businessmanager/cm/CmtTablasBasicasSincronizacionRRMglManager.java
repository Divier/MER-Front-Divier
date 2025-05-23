/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRHomologacionRequest;
import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRCenterPopulated;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMainteniceRRHomologations;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

/**
 *
 * @author aleal
 */
public class CmtTablasBasicasSincronizacionRRMglManager {

    ParametrosMglManager parametrosMglManager;
    RestClientBasicMainteniceRRHomologations basicMainteniceRRHomologations;
    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();

    RestClientBasicMaintenanceRRCenterPopulated basicMaintenanceRRCenterPopulated;

    String BASE_URI_RR;
    private static final Logger LOGGER = LogManager.getLogger(CmtTablasBasicasSincronizacionRRMglManager.class);

    public CmtTablasBasicasSincronizacionRRMglManager() throws ApplicationException {
        try {
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI_RR = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            basicMainteniceRRHomologations = new RestClientBasicMainteniceRRHomologations(BASE_URI_RR);
            basicMaintenanceRRCenterPopulated = new RestClientBasicMaintenanceRRCenterPopulated(BASE_URI_RR);

        } catch (ApplicationException ex) {
            LOGGER.error("error al llamar al pcml" + ex);
        }

    }

    public boolean crearComunidadRr(CmtComunidadRr cmtComunidadRr, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRHomologacionRequest homologacionRequest
                    = new MantenimientoBasicoRRHomologacionRequest();

            GeograficoPoliticoMgl centroPoblado = cmtComunidadRr.getCiudad();
            centroPoblado = geograficoPoliticoManager.findById(centroPoblado.getGpoId());

            GeograficoPoliticoMgl ciudad;
            ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());

            GeograficoPoliticoMgl departamento;
            departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());

            homologacionRequest.setIDUSER(user);

            homologacionRequest.setCODDIV(cmtComunidadRr.getRegionalRr().getCodigoRr());
            homologacionRequest.setCODCOM(cmtComunidadRr.getCodigoRr());
            homologacionRequest.setNOMREG(cmtComunidadRr.getNombreCortoRegional());
            homologacionRequest.setNOMDEP(departamento.getGpoNombre());
            homologacionRequest.setNOMMUN(ciudad.getGpoNombre());
            homologacionRequest.setISMUNI("SI");
            homologacionRequest.setCODDAN(cmtComunidadRr.getCiudad().getGeoCodigoDane());
            homologacionRequest.setHOMDEP(departamento.getGpoNombre());
            homologacionRequest.setHOMMUN(cmtComunidadRr.getCiudad().getGpoNombre());
            homologacionRequest.setCODUBI(cmtComunidadRr.getUbicacion().toString());
            String estado = cmtComunidadRr.getEstadoRegistro() == 0 ? "D" : "A";
            homologacionRequest.setESTREG(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMainteniceRRHomologations.callWebServiceMethodPOST(
                            EnumeratorServiceName.BASICRR_HOMOLOGACION_CREAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            homologacionRequest
                    );
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals(Constant.ACCION_EXITOSA_RR)) {
                validacion = false;
                cmtComunidadRr.setComunidadRrId(new BigDecimal(0));
                throw new ApplicationException(basicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    public boolean actualizarComunidadRr(CmtComunidadRr cmtComunidadRr, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRHomologacionRequest homologacionRequest
                    = new MantenimientoBasicoRRHomologacionRequest();

            GeograficoPoliticoMgl centroPoblado = cmtComunidadRr.getCiudad();
            centroPoblado = geograficoPoliticoManager.findById(centroPoblado.getGpoId());

            GeograficoPoliticoMgl ciudad;
            ciudad = geograficoPoliticoManager.findById(centroPoblado.getGpoId());
            ciudad.setGpoId(centroPoblado.getGpoId());

            GeograficoPoliticoMgl departamento;
            departamento = geograficoPoliticoManager.findById(ciudad.getGpoId());
            departamento.setGpoId(ciudad.getGpoId());

            homologacionRequest.setIDUSER(user);
            homologacionRequest.setCODDIV(cmtComunidadRr.getRegionalRr().getCodigoRr());
            homologacionRequest.setCODCOM(cmtComunidadRr.getCodigoRr());
            homologacionRequest.setNOMREG(cmtComunidadRr.getNombreCortoRegional());
            homologacionRequest.setNOMDEP(departamento.getGpoNombre());
            homologacionRequest.setNOMMUN(ciudad.getGpoNombre());
            homologacionRequest.setISMUNI("SI");
            homologacionRequest.setCODDAN(cmtComunidadRr.getCiudad().getGeoCodigoDane());
            homologacionRequest.setHOMDEP(departamento.getGpoNombre());
            homologacionRequest.setHOMMUN(centroPoblado.getGpoNombre());

            homologacionRequest.setCODUBI(cmtComunidadRr.getUbicacion().toString());
            String estado = cmtComunidadRr.getEstadoRegistro() == 0 ? "D" : "A";
            homologacionRequest.setESTREG(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMainteniceRRHomologations.callWebServiceMethodPUT(
                            EnumeratorServiceName.BASICRR_HOMOLOGACION_ACTUALIZAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            homologacionRequest);
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals(Constant.ACCION_EXITOSA_RR)) {
                validacion = false;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    public boolean eliminarComunidadRr(CmtComunidadRr cmtComunidadRr, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRHomologacionRequest homologacionRequest
                    = new MantenimientoBasicoRRHomologacionRequest();

            GeograficoPoliticoMgl centroPoblado = cmtComunidadRr.getCiudad();
            centroPoblado = geograficoPoliticoManager.findById(centroPoblado.getGpoId());

            GeograficoPoliticoMgl ciudad;
            ciudad = geograficoPoliticoManager.findById(centroPoblado.getGpoId());
            ciudad.setGpoId(centroPoblado.getGpoId());

            GeograficoPoliticoMgl departamento;
            departamento = geograficoPoliticoManager.findById(ciudad.getGpoId());
            departamento.setGpoId(ciudad.getGpoId());

            homologacionRequest.setIDUSER(user);
            homologacionRequest.setCODDIV(cmtComunidadRr.getRegionalRr().getCodigoRr());
            homologacionRequest.setCODCOM(cmtComunidadRr.getCodigoRr());
            homologacionRequest.setNOMREG(cmtComunidadRr.getNombreCortoRegional());
            homologacionRequest.setNOMDEP(departamento.getGpoNombre());
            homologacionRequest.setNOMMUN(ciudad.getGpoNombre());
            homologacionRequest.setISMUNI("SI");
            homologacionRequest.setCODDAN(cmtComunidadRr.getCiudad().getGeoCodigoDane());
            homologacionRequest.setHOMDEP(departamento.getGpoNombre());
            homologacionRequest.setHOMMUN(centroPoblado.getGpoNombre());

            homologacionRequest.setCODUBI(cmtComunidadRr.getUbicacion().toString());
            String estado = cmtComunidadRr.getEstadoRegistro() == 0 ? "D" : "A";
            homologacionRequest.setESTREG(estado);
            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMainteniceRRHomologations.callWebServiceMethodPUT(
                            EnumeratorServiceName.BASICRR_HOMOLOGACION_ELIMINAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            homologacionRequest);
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals(Constant.ACCION_EXITOSA_RR)) {
                validacion = false;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    public boolean crearGeograficoPoliticoRr(GeograficoPoliticoMgl geograficoPoliticoMgl, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest
                    = new MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest();
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setIduser(user);
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddane(geograficoPoliticoMgl.getGeoCodigoDane());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDspobld(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddepto(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsdepto(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdmpio(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsmpio(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdclase(geograficoPoliticoMgl.getGpoCodigo());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setNummanz(geograficoPoliticoMgl.getNumManzana());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setAnocrea(String.valueOf(geograficoPoliticoMgl.getFechaCreacion().getYear()));
            String estado;
            if (geograficoPoliticoMgl.getEstadoRegistro() == 0) {
                estado = "NO";
            } else {
                estado = "SI";
            }
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setEstvig(estado);

            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMaintenanceRRCenterPopulated.callWebServiceMethodPost(
                            EnumeratorServiceName.BASICRR_POPULATED_CENTER_CREAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest);
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
            }
        } catch (UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    public boolean actualizarGeograficoPoliticoRr(GeograficoPoliticoMgl geograficoPoliticoMgl, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest
                    = new MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest();
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setIduser(user);
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddane(geograficoPoliticoMgl.getGeoCodigoDane());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDspobld(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddepto(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsdepto(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdmpio(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsmpio(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdclase(geograficoPoliticoMgl.getGpoCodigo());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setNummanz(geograficoPoliticoMgl.getNumManzana());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setAnocrea(String.valueOf(geograficoPoliticoMgl.getFechaCreacion().getYear()));
            String estado;
            if (geograficoPoliticoMgl.getEstadoRegistro() == 0) {
                estado = "NO";
            } else {
                estado = "SI";
            }
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setEstvig(estado);

            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMaintenanceRRCenterPopulated.callWebServiceMethodPut(
                            EnumeratorServiceName.BASICRR_POPULATED_CENTER_ACTUALIZAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest);
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
            }
        } catch (UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

    public boolean eliminarGeograficoPoliticoRr(GeograficoPoliticoMgl geograficoPoliticoMgl, String user)
            throws ApplicationException {
        boolean validacion = true;
        try {
            MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest
                    = new MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest();
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setIduser(user);
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddane(geograficoPoliticoMgl.getGeoCodigoDane());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDspobld(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCddepto(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsdepto(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdmpio(geograficoPoliticoMgl.getGpoDepartamentoCodigoZip());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setDsmpio(geograficoPoliticoMgl.getGpoNombre());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setCdclase(geograficoPoliticoMgl.getGpoCodigo());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setNummanz(geograficoPoliticoMgl.getNumManzana());
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setAnocrea(String.valueOf(geograficoPoliticoMgl.getFechaCreacion().getYear()));
            String estado;
            if (geograficoPoliticoMgl.getEstadoRegistro() == 0) {
                estado = "NO";
            } else {
                estado = "SI";
            }
            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest.setEstvig(estado);

            MantenimientoBasicoRRBaseResponse basicoRRBaseResponse
                    = basicMaintenanceRRCenterPopulated.callWebServiceMethodPut(
                            EnumeratorServiceName.BASICRR_POPULATED_CENTER_ELIMINAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRMunicipioCentroPobladoDaneRequest);
            if (!basicoRRBaseResponse.getCodigoDeRespuesta().equals("INS0000")) {
                validacion = false;
                throw new ApplicationException("(As400-ws)" + basicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return validacion;
    }

}
