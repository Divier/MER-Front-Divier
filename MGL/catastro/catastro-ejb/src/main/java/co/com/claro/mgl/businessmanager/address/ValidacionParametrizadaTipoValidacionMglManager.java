/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtValidadorDireccionesManager;
import co.com.claro.mgl.dao.impl.ValidacionParametrizadaTipoValidacionMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ValidacionParametrizadaTipoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.data.AddressService;
import co.com.claro.ejb.mgl.address.dto.ValidacionParametrizadaDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class ValidacionParametrizadaTipoValidacionMglManager {

    private static final Logger LOGGER = LogManager.getLogger(ValidacionParametrizadaTipoValidacionMglManager.class);
    ValidacionParametrizadaTipoValidacionMglDaoImpl validacionParametrizadaMglDaoImpl = new ValidacionParametrizadaTipoValidacionMglDaoImpl();

    
     /**
     * Obtiene el listado de validaciones por tipo de la base de datos
     *
     * @param tipoValidacion tipo de validacion que se desea obtener.
     * @author Juan David Hernandez
     * @return Listado de validaciones
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ValidacionParametrizadaTipoValidacionMgl> findValidacionParametrizadaByTipo(BigDecimal tipoValidacion)
            throws ApplicationException {
        List<ValidacionParametrizadaTipoValidacionMgl> result;
        result = validacionParametrizadaMglDaoImpl.findValidacionParametrizadaByTipo(tipoValidacion);
        return result;
    }
    
     public ValidacionParametrizadaTipoValidacionMgl update(ValidacionParametrizadaTipoValidacionMgl validacion) throws ApplicationException {
        return validacionParametrizadaMglDaoImpl.update(validacion);
    }
     
     
     /**
     * Función que valida si una direccion tiene cobertura en una tecnologia desea,
     * en caso de que No, devuelve el nombre de las tecnologias en las que si tiene cobertura,
     * en caso de que no tenga cobertura en ninguna tecnologia, valida si se cuenta con nodo cercano
     * y si no se tiene nodo cercano el resultado es que la dirección esta fuera de zona.
     * 
     * @author Juan David Hernandez
     * @param val
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */ 
    public ValidacionParametrizadaDto validarCoberturasSolicitudHhpp(ValidacionParametrizadaDto val) throws ApplicationException {
        ValidacionParametrizadaDto result = new ValidacionParametrizadaDto();
        try {            
            if (val.getDrDireccion() != null && val.getCentroPoblado() != null
                    && val.getDepartamento() != null && val.getTecnologiaBasicaId() != null) {

                HhppMglManager hhppMglManager = new HhppMglManager();
                CmtDireccionDetalleMglManager ddManager = new CmtDireccionDetalleMglManager();
                CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl = ddManager.parseDrDireccionToCmtDireccionDetalladaMgl(val.getDrDireccion());
                boolean resultadoValidacion = false;

                //Validacion nodo cercano
                List<NodoMgl> nodoCercanoList = hhppMglManager.obtenerNodosCercanoSolicitudHhpp(cmtDireccionDetalladaMgl,
                        val.getTecnologiaBasicaId(), val.getCentroPoblado().getGpoId());

                if (nodoCercanoList != null && !nodoCercanoList.isEmpty()) {
                    resultadoValidacion = true;
                }
                
                //Si ya tiene nodo cercano no es necesario realizar la validacion de coberturas
                if(!resultadoValidacion){

                //Validacion coberturas
                AddressService addressService = direccionesManager
                        .calcularCoberturaDrDireccion(val.getDrDireccion(), val.getCentroPoblado());

                if (addressService != null) {

                    if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.DTH)) {
                        if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                            result.setResultadoValidacion(true);                            
                        } else {
                            if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";     
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";  
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";   
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                                result.setResultadoValidacion(false);

                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                    result.setResultadoValidacion(false);
                                }
                            }
                        }
                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.FIBRA_FTTTH)) {

                        if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                            result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas ni nodo cercano, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }
                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.HFC_BID)) {
                        if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                           result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                
                                boolean cobertura = false;
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }

                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.HFC_UNI)) {
                        if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                           result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                               String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                               
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                               if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                   msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas ni nodo cercano, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }
                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_GPON)) {
                        if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                            result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                               String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                               
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas ni nodo cercano.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }

                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.LTE_INTERNET)) {
                        if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                            result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas ni nodo cercano, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }
                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.BTS_MOVIL)) {
                        if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                            result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                    || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoZonaUnifilar()!= null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoZonaUnifilar()!= null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";   
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }
                    } else if (val.getTecnologiaBasicaId().getIdentificadorInternoApp().equals(Constant.FIBRA_OP_UNI)) {
                        if (addressService.getNodoZonaUnifilar() != null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                            result.setResultadoValidacion(true);
                        } else {
                            if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                    || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                    || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                    || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                    || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                    || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                    || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()) {

                                String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                        + "deseada pero si en las siguientes tecnologías, "
                                        + "si desea reinicie la solicitud seleccionando alguna de ellas: ";
                                
                                boolean cobertura = false;
                                if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                    msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                    msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                    msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                    cobertura = true;
                                }
                                if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                    msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                    cobertura = true;
                                }
                                 if (addressService.getNodoWifi()!= null && !addressService.getNodoWifi().isEmpty()) {
                                    msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + " ";
                                    cobertura = true;
                                }
                                
                                if (cobertura) {
                                    result.setMensajeValidacion(msnValCoberturas);
                                } else {
                                    msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setMensajeValidacion(msnValCoberturas);
                                }                                
                                result.setResultadoValidacion(false);
                                
                            } else {
                                if (!resultadoValidacion) {
                                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas ni nodo cercano, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                                    result.setResultadoValidacion(false);
                                    result.setMensajeValidacion(msnValCoberturas);
                                }
                            }
                        }
                    } else {

                        if (addressService.getNodoUno() != null || !addressService.getNodoUno().isEmpty()
                                || addressService.getNodoDos() != null || !addressService.getNodoDos().isEmpty()
                                || addressService.getNodoTres() != null || !addressService.getNodoTres().isEmpty()
                                || addressService.getNodoDth() != null || !addressService.getNodoDth().isEmpty()
                                || addressService.getNodoMovil() != null || !addressService.getNodoMovil().isEmpty()
                                || addressService.getNodoFtth() != null || !addressService.getNodoFtth().isEmpty()
                                || addressService.getNodoWifi() != null || !addressService.getNodoWifi().isEmpty()
                                || addressService.getNodoZonaUnifilar() != null || !addressService.getNodoZonaUnifilar().isEmpty()) {

                            String msnValCoberturas = "La dirección no cuenta con cobertura en la tecnologia "
                                    + "deseada pero si en las siguientes tecnologías, "
                                    + "si desea reinicie la solicitud seleccionando alguna de ellas: ";

                            boolean cobertura = false;
                            if (addressService.getNodoUno() != null && !addressService.getNodoUno().isEmpty()) {
                                msnValCoberturas += Constant.HFC_BID_NOMBRE + ", ";
                                cobertura = true;
                            }
                            if (addressService.getNodoDos() != null && !addressService.getNodoDos().isEmpty()) {
                                msnValCoberturas += Constant.HFC_UNI_NOMBRE + ", ";
                                cobertura = true;
                            }
                            if (addressService.getNodoTres() != null && !addressService.getNodoTres().isEmpty()) {
                                msnValCoberturas += Constant.FIBRA_OP_GPON_NOMBRE + ", ";
                                cobertura = true;
                            }
                            if (addressService.getNodoDth() != null && !addressService.getNodoDth().isEmpty()) {
                                msnValCoberturas += Constant.DTH_NOMBRE + ", ";
                                cobertura = true;
                            }
                            if (addressService.getNodoMovil() != null && !addressService.getNodoMovil().isEmpty()) {
                                msnValCoberturas += Constant.BTS_MOVIL_NOMBRE + ", ";
                                cobertura = true;
                            }
                            if (addressService.getNodoFtth() != null && !addressService.getNodoFtth().isEmpty()) {
                                msnValCoberturas += Constant.FIBRA_FTTTH_NOMBRE + " ";
                                cobertura = true;
                            }
                            if (addressService.getNodoWifi() != null && !addressService.getNodoWifi().isEmpty()) {
                                msnValCoberturas += Constant.LTE_INTERNET_NOMBRE + " ";
                                cobertura = true;
                            }
                            if (addressService.getNodoZonaUnifilar() != null && !addressService.getNodoZonaUnifilar().isEmpty()) {
                                msnValCoberturas += Constant.FIBRA_OP_UNI_NOMBRE + " ";
                                cobertura = true;
                            }

                            if (cobertura) {
                                result.setMensajeValidacion(msnValCoberturas);
                            } else {
                                msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                        + " a que no se encontraron coberturas, "
                                        + "sin embargo puede continuar con la creación de la solicitud.";
                                result.setResultadoValidacion(false);
                                result.setMensajeValidacion(msnValCoberturas);
                            }
                        }
                    }

                } else {
                    // no tiene coberturas y no tiene nodo cercano   
                    String msnValCoberturas = "La dirección se encuentra fuera de zona debido"
                                            + " a que no se encontraron coberturas, "
                                            + "sin embargo puede continuar con la creación de la solicitud.";
                    result.setMensajeValidacion(msnValCoberturas);
                    result.setResultadoValidacion(false);                      
                }
            }else{
                    result.setResultadoValidacion(true);
                }                 
            }else {
                //No llego la informacion completa para hacer las validaciones
                result.setMensajeValidacion("No fue posible realizar la "
                        + "viabilidad de la solicitud por falta de información"
                        + " pero puede continuar con la creación de la solicitud o si lo desea "
                        + "Intente de nuevo por favor");
                result.setResultadoValidacion(false);
            }
        } catch (Exception e) {
            LOGGER.error("Error en validarCoberturaSolicitudHhpp. " +e.getMessage(), e);  
            result.setMensajeValidacion("Ocurrio un error intentando realizar"
                    + " la validacion de viabilidad de la solicitud. "
                    + "Intente de nuevo por favor");
            result.setResultadoValidacion(false);
        }
        return result;
    }
}
