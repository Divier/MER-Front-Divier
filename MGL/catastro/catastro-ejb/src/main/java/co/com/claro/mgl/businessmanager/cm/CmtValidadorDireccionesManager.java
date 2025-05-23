/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package co.com.claro.mgl.businessmanager.cm;


import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import static co.com.claro.mgl.businessmanager.address.DireccionesValidacionManager.estandarizaNodo;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.PcmlManager;
import co.com.claro.mgl.businessmanager.address.rr.NodoRRManager;
import co.com.claro.mgl.dtos.FichasGeoDrDireccionDto;
import co.com.claro.mgl.dtos.NodoDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesPreviasMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DetalleDireccionManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
*
* @author ADMIN
*/
public class CmtValidadorDireccionesManager {
   private static final Logger LOGGER = LogManager.getLogger(CmtValidadorDireccionesManager.class);
   @Getter
   private String errorDescription = "";

   /* ------------------------------------------ */

    public ResponseValidarDireccionDto validarDireccion(DrDireccion drDireccion, String direccion,
            GeograficoPoliticoMgl ciudad, 
            String barrio, boolean cambiarMallaDir) {
        ResponseValidarDireccionDto validarDireccionDto = null;
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        try {
        if (direccion != null && !direccion.isEmpty()) {
            validarDireccionDto = validarDireccion(direccion, ciudad, barrio, cambiarMallaDir);
             drDireccion = new DrDireccion();
        } else {
            if (drDireccion != null) {
                if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().isEmpty()) {
                    validarDireccionDto = validarDireccion(drDireccionManager.getDireccion(drDireccion), 
                            ciudad, barrio, cambiarMallaDir);
                    
                    validarDireccionDto.setDireccion("");
                } else {
                    validarDireccionDto = new ResponseValidarDireccionDto();
                    validarDireccionDto.setDireccion("");
                    DrDireccion drDirecciontmp = new DrDireccion();
                    drDirecciontmp.setIdTipoDireccion("IT");
                    validarDireccionDto.setDrDireccion(drDirecciontmp);
                    drDirecciontmp.setDireccionRespuestaGeo(null);
                    return validarDireccionDto;
                }
                } else {
                    validarDireccionDto = new ResponseValidarDireccionDto();
                    validarDireccionDto.setDireccion("");
                    DrDireccion drDirecciontmp = new DrDireccion();
                    drDirecciontmp.setIdTipoDireccion("IT");
                    validarDireccionDto.setDrDireccion(drDirecciontmp);
                    drDirecciontmp.setDireccionRespuestaGeo(null);
                    return validarDireccionDto;
                }

            }
        
             //Cuando la direccion fue al geo y regresa para ser validada su respuesta
            if (!validarDireccionDto.isIntradusible()) {
                if (barrio == null || barrio.isEmpty()) {
                    barrio = "";
                }
                DetalleDireccionEntity detalleDireccionEntity = tabularDireccion(validarDireccionDto, ciudad, barrio);
                validarDireccionDto.setDireccion("");
                if (detalleDireccionEntity != null) {
                    detalleDireccionEntity.setIdtipodireccion("CK");
                } else {
                    validarDireccionDto.setMostrarFormulario(true);
                    validarDireccionDto.setIntradusible(true);
                    validarDireccionDto.setDireccionRespuestaGeo(null);
                    if (drDireccion.getIdTipoDireccion() == null || drDireccion.getIdTipoDireccion().equalsIgnoreCase("")) {
                        drDireccion = new DrDireccion();
                        drDireccion.setIdTipoDireccion("BM");
                        validarDireccionDto.getValidationMessages().add("La dirección digitada no fue traducida con exito.");
                        validarDireccionDto.setDrDireccion(drDireccion);                        
                        validarDireccionDto.setValidacionExitosa(false);
                        return validarDireccionDto;
                    }

                    validarDireccionDto.setDrDireccion(drDireccion);
                    detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
                    detalleDireccionEntity.setMultiOrigen(ciudad.getGpoMultiorigen());
                    DireccionRRManager direccionRRManager;
                    try {
                        direccionRRManager = new DireccionRRManager(detalleDireccionEntity, "",null);
                    } catch (ApplicationException ex) {
                        validarDireccionDto.getValidationMessages().add(ex.getMessage());
                        validarDireccionDto.setValidacionExitosa(false);
                        validarDireccionDto.setDireccionRespuestaGeo(null);
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                        return validarDireccionDto;
                    }
                    validarDireccionDto.setDireccionRREntity(direccionRRManager.getDireccion());
                    validarDireccionDto.setDireccion(drDireccionManager.getDireccion(drDireccion));
                    if (validarDireccionDto.getDireccionRREntity() == null
                            || validarDireccionDto.getDireccionRREntity().getCalle() == null
                            || validarDireccionDto.getDireccionRREntity().getCalle().isEmpty()
                            || validarDireccionDto.getDireccionRREntity().getNumeroUnidad() == null
                            || validarDireccionDto.getDireccionRREntity().getNumeroUnidad().isEmpty()) {
                        validarDireccionDto.getValidationMessages().add("La dirección digitada no fue traducida con exito.");
                        validarDireccionDto.setValidacionExitosa(false);
                        validarDireccionDto.setDireccionRespuestaGeo(null);
                    } else {
                        validarDireccionDto.getValidationMessages().add("La validacion es exitosa, La direccion generada es:"
                                + "[" + validarDireccionDto.getDireccionRREntity().getCalle() + "][" + validarDireccionDto.getDireccionRREntity().getNumeroUnidad() + "]");
                        validarDireccionDto.setValidacionExitosa(true);
                    }
                    return validarDireccionDto;
                }
                detalleDireccionEntity.setMultiOrigen(ciudad.getGpoMultiorigen());
                DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity, "",ciudad);
                validarDireccionDto.setDireccionRREntity(direccionRRManager.getDireccion());
                validarDireccionDto.setDrDireccion(new DrDireccion());
                validarDireccionDto.getDrDireccion().obtenerFromDetalleDireccionEntity(detalleDireccionEntity);
                validarDireccionDto.setDireccion(drDireccionManager.getDireccion(validarDireccionDto.getDrDireccion()));

            } else {
                //Si la direccion es intraducible toma este camino                
                DetalleDireccionEntity detalleDireccionEntity;

                if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().isEmpty()) {
                    if (drDireccion.getIdTipoDireccion().equalsIgnoreCase("BM")) {
                        detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
                        drDireccion.setIdTipoDireccion("BM");
                        validarDireccionDto.setDrDireccion(drDireccion);
                        validarDireccionDto.setDireccion(drDireccionManager.getDireccion(drDireccion));
                        validarDireccionDto.setDireccionRespuestaGeo(null);
                    } else {
                        detalleDireccionEntity = drDireccion.convertToDetalleDireccionEntity();
                        drDireccion.setIdTipoDireccion("IT");
                        validarDireccionDto.setDrDireccion(drDireccion);
                        validarDireccionDto.setDireccion(drDireccionManager.getDireccion(drDireccion));
                        validarDireccionDto.setDireccionRespuestaGeo(null);
                    }
                } else {
                    DrDireccion drDirecciontmp = new DrDireccion();
                    drDirecciontmp.setIdTipoDireccion("IT");
                    detalleDireccionEntity = drDirecciontmp.convertToDetalleDireccionEntity();
                    validarDireccionDto.setDrDireccion(drDirecciontmp);
                    validarDireccionDto.setDireccionRespuestaGeo(null);
                }
                DireccionRRManager direccionRRManager = new DireccionRRManager(detalleDireccionEntity, "",ciudad);
                validarDireccionDto.setDireccionRREntity(direccionRRManager.getDireccion());
            }

            if (validarDireccionDto.getDireccionRREntity() == null
                    || validarDireccionDto.getDireccionRREntity().getCalle() == null
                    || validarDireccionDto.getDireccionRREntity().getCalle().isEmpty()
                    || validarDireccionDto.getDireccionRREntity().getNumeroUnidad() == null
                    || validarDireccionDto.getDireccionRREntity().getNumeroUnidad().isEmpty()) {
                validarDireccionDto.getValidationMessages().add("La dirección digitada no fue traducida con exito.");
                validarDireccionDto.setValidacionExitosa(false);
                validarDireccionDto.setDireccionRespuestaGeo(null);
            } else {
                validarDireccionDto.getValidationMessages().add("La validacion es exitosa, La direccion generada es:"
                        + "[" + validarDireccionDto.getDireccionRREntity().getCalle() + "][" + validarDireccionDto.getDireccionRREntity().getNumeroUnidad() + "]");
                validarDireccionDto.setValidacionExitosa(true);
            }
            
        } catch (ApplicationException ex) {
            if (validarDireccionDto != null) {
                validarDireccionDto.getValidationMessages().add(ex.getMessage());
                validarDireccionDto.setValidacionExitosa(false);
            }
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception ex) {
            if (validarDireccionDto != null) {
                validarDireccionDto.getValidationMessages().add(ex.getMessage());
                validarDireccionDto.setValidacionExitosa(false);
            }
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        
        return validarDireccionDto;
    }

    private DetalleDireccionEntity tabularDireccion(ResponseValidarDireccionDto validarDireccionDto,
            GeograficoPoliticoMgl ciudad, String barrio) throws Exception {
        try {
            DetalleDireccionManager detalleDireccionManager = new DetalleDireccionManager();
            return detalleDireccionManager.conversionADetalleDireccion(validarDireccionDto.getAddressService().getAddressCodeFound(), validarDireccionDto.getAddressService().getAddressCode(), ciudad.getGpoId(), barrio);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return null;
        }

    }

    private DetalleDireccionEntity tabularDireccion(AddressService addressService,
            GeograficoPoliticoMgl ciudad, String barrio) throws ApplicationException {
        try {
            DetalleDireccionManager detalleDireccionManager = new DetalleDireccionManager();
            return detalleDireccionManager.conversionADetalleDireccion(addressService.getAddressCodeFound(), addressService.getAddressCode(), ciudad.getGpoId(), barrio);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error en '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }

    }

    private ResponseValidarDireccionDto validarDireccion(String direccion, GeograficoPoliticoMgl ciudad, 
            String barrio, boolean cambiarMallaDir) throws Exception {

        ResponseValidarDireccionDto validarDireccionDto = new ResponseValidarDireccionDto();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        AddressService responseSrv;
        AddressRequest requestSrv = new AddressRequest();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();

        requestSrv.setAddress(direccion);

        requestSrv.setCity(ciudad.getGpoNombre());
        requestSrv.setCodDaneVt(ciudad.getGeoCodigoDane());
        requestSrv.setLevel("C");
        GeograficoPoliticoMgl municipio = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
        GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(municipio.getGeoGpoId());
        requestSrv.setState(departamento.getGpoNombre());
        if(barrio != null && !barrio.isEmpty()){
        requestSrv.setNeighborhood(barrio.toUpperCase());
        }
        responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

        if (!(Constant.DIRECCIONES_TRADUSIBLE).equalsIgnoreCase(responseSrv.getTraslate())) {

            validarDireccionDto.setMostrarFormulario(true);
            validarDireccionDto.setIntradusible(true);
            validarDireccionDto.setDireccionRespuestaGeo(null);
            return validarDireccionDto;
        } else {
            
            //direccion con respuesta sin alteraciones cuando es AVENIDA
            validarDireccionDto.setDireccionRespuestaGeo(responseSrv.getAddress());
            if (responseSrv.getSource() != null
                    && !responseSrv.getSource().isEmpty()
                    && responseSrv.getSource().equalsIgnoreCase(Constant.DIRECCIONES_ORIGEN_ANTIGUA)
                    && !cambiarMallaDir) {
                validarDireccionDto.setDireccionAntigua(responseSrv.getAddress());
                requestSrv.setAddress(responseSrv.getAlternateaddress());
                requestSrv.setCodDaneVt(ciudad.getGeoCodigoDane());
                requestSrv.setLevel("C");
                requestSrv.setState(departamento.getGpoNombre());
                if(barrio != null && !barrio.isEmpty()){
                requestSrv.setNeighborhood(barrio.toUpperCase());
                }
                responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
                validarDireccionDto.setAntigua(true);
                validarDireccionDto.getValidationMessages().add("Se encuentra una direccion mas reciente, se cambiará por esta;" + responseSrv.getAddress());
                //validar si la direccion antigua con avenida como llega
                validarDireccionDto.setDireccionRespuestaGeo(responseSrv.getAddress());
                
            } else {
                if (responseSrv.getSource() != null
                        && !responseSrv.getSource().isEmpty()
                        && responseSrv.getSource().equalsIgnoreCase(Constant.DIRECCIONES_ORIGEN_NUEVA)) {
                    validarDireccionDto.setDireccionAntigua(responseSrv.getAlternateaddress());
                    //direccion con respuesta sin alteraciones cuando es AVENIDA
                     validarDireccionDto.setDireccionRespuestaGeo(responseSrv.getAddress());
                }
                 validarDireccionDto.setDireccionRespuestaGeo(responseSrv.getAddress());
            }
        }
         // agregando campo Neighborhood
        validarDireccionDto.setDireccion(responseSrv.getAddress());
        if (ciudad.getGpoMultiorigen().equals(Constant.DIRECCIONES_MULTIORIGEN)) {
            if (responseSrv.getAddressSuggested() != null && !responseSrv.getAddressSuggested().isEmpty()) {
                for (AddressSuggested AddressAddress : responseSrv.getAddressSuggested()) {
                    if (AddressAddress.getAddress().contains(responseSrv.getAddress())) {
                        validarDireccionDto.getBarrios().add(AddressAddress.getNeighborhood().trim());
                    }
                }
            }  
        }
        if (responseSrv.getAddressSuggested() == null || responseSrv.getAddressSuggested().isEmpty()) {
            if (responseSrv.getNeighborhood() != null
                    && !responseSrv.getNeighborhood().isEmpty()
                    && !validarDireccionDto.getBarrios().contains(responseSrv.getNeighborhood())) {
                validarDireccionDto.getBarrios().add(responseSrv.getNeighborhood());
                validarDireccionDto.setBarrioGeo(responseSrv.getNeighborhood());
            }
        } else {
            if(responseSrv.getNeighborhood() != null
                    && !responseSrv.getNeighborhood().isEmpty()){
                 validarDireccionDto.setBarrioGeo(responseSrv.getNeighborhood());
            }
           
        }
        validarDireccionDto.setAddressService(responseSrv);
        validarDireccionDto.setExiste(responseSrv.getExist().equalsIgnoreCase("true"));
        validarDireccionDto.setMultiOrigen(ciudad.getGpoMultiorigen().equalsIgnoreCase("1"));
        return validarDireccionDto;
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new RuntimeException(ex);
        }
    }

    public AddressService calcularCobertura(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException {
        String direccion;
        String barrio;
        NodoRRManager nodoRRManager = new NodoRRManager();
        AddressRequest addressRequest = new AddressRequest();
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        DrDireccion drDireccion;
        drDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
        direccion = drDireccionManager.getDireccion(drDireccion);
        if (cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGpoMultiorigen().
                equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
                && cmtDireccionSolictudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
            barrio = cmtDireccionSolictudMgl.getBarrio();
        } else {
            barrio = "";
        }
        addressRequest.setLevel(Constant.WS_ADDRESS_CONSULTA_COMPLETA);
        addressRequest.setCity(cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGpoNombre());
        addressRequest.setState(cmtDireccionSolictudMgl.getSolicitudCMObj().getDepartamentoGpo().getGpoNombre());
        addressRequest.setCodDaneVt(cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGeoCodigoDane());
        addressRequest.setNeighborhood(barrio);
        addressRequest.setAddress(direccion);

        AddressService response = addressEJBRemote.queryAddress(addressRequest);
        
                //Si la ciudad es multiorigen el barrio debe coincidir con el que entrega el geo para retornar las coberturas
       if (response != null && cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoMultiorigen().
               equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
               && cmtDireccionSolictudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {

           if (response.getNeighborhood() != null
                   && response.getNeighborhood().equalsIgnoreCase(barrio)) {
               return response;
           } else {
               response.setNodoDos("");
               response.setNodoDth("");
               response.setNodoFtth("");
               response.setNodoMovil("");
               response.setNodoTres("");
               response.setNodoUno("");
               response.setNodoWifi("");
               response.setNodoZona3G("");
               response.setNodoZona4G("");
               response.setNodoZona5G("");
               response.setNodoZonaCoberturaCavs("");
               response.setNodoZonaCoberturaUltimaMilla("");
               response.setNodoZonaCurrier("");
               response.setNodoZonaGponDiseniado("");
               response.setNodoZonaMicroOndas("");
               response.setNodoZonaUnifilar("");
               response.setCx("");
               response.setCy("");
               return response;
           }
       }
        
        return response;
    }

    public HashMap<String, NodoDto> calcularNodoCercano(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl,
            String usuario, int perfil) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        NodoRRManager nodoRRManager = new NodoRRManager();
        HashMap< String, NodoDto> hashMapNodosCerticados = new HashMap<>();
        if (cmtDireccionSolictudMgl.getUnidadRr().contains("-")) {
            String crucePlaca = cmtDireccionSolictudMgl.getUnidadRr().split("-")[0];
            if (crucePlaca != null && !crucePlaca.isEmpty()) {
                List<UnidadStructPcml> unidadesCercanas = pcmlManager.getUnidades(cmtDireccionSolictudMgl.getCalleRr(), crucePlaca,
                        "", cmtDireccionSolictudMgl.getSolicitudCMObj().getComunidad());
                if (unidadesCercanas != null && !unidadesCercanas.isEmpty()) {
                    for (UnidadStructPcml usp : unidadesCercanas) {
                        if (!usp.getNodUnidad().toUpperCase().contains("NFI")) {
                            if (!hashMapNodosCerticados.containsKey(usp.getNodUnidad())) {
                                NodoDto nodoDto = new NodoDto();
                                nodoDto.setCodigo(usp.getNodUnidad());
                                nodoDto.setPlacaRr("[" + usp.getCalleUnidad() + "][" + usp.getCasaUnidad() + "]");
                                nodoDto.setActivado(nodoRRManager.isNodoRRCertificado(usp.getNodUnidad(), usuario));
                                nodoDto.setTipoRed(usp.getTipoRed());
                                hashMapNodosCerticados.put(usp.getNodUnidad(), nodoDto);
                            }
                        }
                    }
                }
            }
        }
        return hashMapNodosCerticados;
    }
    
      public HashMap<String, NodoDto> calcularNodoCercano(String calleRr, String unidadRr, String comunidad,
            String usuario, int perfil) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        NodoRRManager nodoRRManager = new NodoRRManager();
        HashMap< String, NodoDto> hashMapNodosCerticados = new HashMap<>();
        if (unidadRr.contains("-")) {
            String crucePlaca = unidadRr.split("-")[0];
            if (crucePlaca != null && !crucePlaca.isEmpty()) {
                List<UnidadStructPcml> unidadesCercanas = pcmlManager.getUnidades(calleRr, crucePlaca,
                        "", comunidad);
                if (unidadesCercanas != null && !unidadesCercanas.isEmpty()) { 
                    for (UnidadStructPcml usp : unidadesCercanas) {
                        if (usp.getNodUnidad()!=null
                                && !usp.getNodUnidad().trim().isEmpty()
                                && !usp.getNodUnidad().toUpperCase().contains("NFI")) {
                            if (!hashMapNodosCerticados.containsKey(usp.getNodUnidad())) {
                                NodoDto nodoDto = new NodoDto();
                                nodoDto.setCodigo(usp.getNodUnidad());
                                nodoDto.setPlacaRr("[" + usp.getCalleUnidad() + "][" + usp.getCasaUnidad() + "]");
                                nodoDto.setActivado(nodoRRManager.isNodoRRCertificado(usp.getNodUnidad(), usuario));
                                nodoDto.setTipoRed(usp.getTipoRed());
                                hashMapNodosCerticados.put(usp.getNodUnidad(), nodoDto);
                           }
                        }
                    }
                }
            }
        }
        return hashMapNodosCerticados;
    }

    public List<CmtUnidadesPreviasMgl> unidadesDeLaDireccion(CmtDireccionSolicitudMgl cmtDireccionSolictudMgl) throws ApplicationException {
        PcmlManager pcmlManager = new PcmlManager();
        List<UnidadStructPcml> unidadesCercanas;
        List<CmtUnidadesPreviasMgl> unidadesCercanasResponse = new ArrayList<>();

        unidadesCercanas = pcmlManager.getUnidades(cmtDireccionSolictudMgl.getCalleRr(), cmtDireccionSolictudMgl.getUnidadRr(),
                "", cmtDireccionSolictudMgl.getSolicitudCMObj().getComunidad());
        for (UnidadStructPcml structPcml : unidadesCercanas) {
            if (structPcml.getCalleUnidad().trim().equalsIgnoreCase(cmtDireccionSolictudMgl.getCalleRr().trim())
                    && structPcml.getCasaUnidad().trim().contains(cmtDireccionSolictudMgl.getUnidadRr().trim())) {
                CmtUnidadesPreviasMgl previasMgl = new CmtUnidadesPreviasMgl();
                unidadesCercanasResponse.add(previasMgl);
            }
        }

        if (cmtDireccionSolictudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
            AddressRequest addressRequest = new AddressRequest();
            AddressService addressService = null;
            DrDireccionManager direccionManager = new DrDireccionManager();
            DrDireccion drDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
            String direccion = direccionManager.getDireccion(drDireccion);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            if (cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGpoMultiorigen().equals("1")) {
                addressRequest.setNeighborhood(cmtDireccionSolictudMgl.getBarrio());
            } else {
                addressRequest.setNeighborhood("");
            }
            addressRequest.setState(cmtDireccionSolictudMgl.getSolicitudCMObj().getDepartamentoGpo().getGpoNombre());
            addressRequest.setCity(cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGpoNombre());
            addressRequest.setCodDaneVt(cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGeoCodigoDane());
            addressRequest.setLevel("C");
            addressRequest.setAddress(direccion);
            try {
                addressService = addressEJBRemote.queryAddress(addressRequest);
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(ex.getMessage());
            }
            if (addressService != null) {
                if (addressService.getTraslate().equalsIgnoreCase("true")) {
                    if (addressService.getAlternateaddress() != null && !addressService.getAlternateaddress().isEmpty()) {
                        addressRequest.setAddress(addressService.getAlternateaddress());
                        try {
                            addressService = addressEJBRemote.queryAddress(addressRequest);
                        } catch (ApplicationException ex) {
                            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                             LOGGER.error(msg);
                            throw new ApplicationException(ex.getMessage());
                        }

                        if (addressService.getTraslate().equalsIgnoreCase("true")) {

                            DetalleDireccionEntity direccionEntity = tabularDireccion(addressService, cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo(),
                                    cmtDireccionSolictudMgl.getBarrio());
                            direccionEntity.setIdtipodireccion("CK");
                            direccionEntity.setMultiOrigen(cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoMultiorigen());
                            DireccionRRManager direccionRRManager;
                            try {
                                direccionRRManager = new DireccionRRManager(direccionEntity, "",cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo());
                            } catch (ApplicationException ex) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException(ex.getMessage());
                            }

                            DireccionRREntity direccionRREntity = direccionRRManager.getDireccion();

                            if (!(direccionRREntity == null
                                    || direccionRREntity.getCalle() == null
                                    || direccionRREntity.getCalle().isEmpty()
                                    || direccionRREntity.getNumeroUnidad() == null
                                    || direccionRREntity.getNumeroUnidad().isEmpty())) {
                                unidadesCercanas = pcmlManager.getUnidades(direccionRREntity.getCalle(), direccionRREntity.getNumeroUnidad(),
                                        "", cmtDireccionSolictudMgl.getSolicitudCMObj().getComunidad());
                                for (UnidadStructPcml structPcml : unidadesCercanas) {
                                    if (structPcml.getCalleUnidad().trim().equalsIgnoreCase(direccionRREntity.getCalle().trim())
                                            && structPcml.getCasaUnidad().trim().contains(direccionRREntity.getNumeroUnidad().trim())) {
                                        CmtUnidadesPreviasMgl previasMgl = new CmtUnidadesPreviasMgl();
                                        unidadesCercanasResponse.add(previasMgl);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        return unidadesCercanasResponse;
    }

    public DireccionRREntity convertirDireccionStringADrDireccion(
            String direccion, String comunidad,String barrio) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoMglManager = new GeograficoPoliticoManager();

        if (comunidad == null || comunidad.isEmpty()) {
            throw new ApplicationException("El codigo dane de la ciudad es obligatorio en la comunidad");
        }
        
        GeograficoPoliticoMgl politicoMgl = geograficoPoliticoMglManager.findCityByCodDaneCp(comunidad);

        if (politicoMgl == null || politicoMgl.getGpoId() == null) {
            throw new ApplicationException("Comunidad no tiene codigo dane en Ciudades");
        }
        DrDireccion drDireccion = convertirDireccionStringADrDireccion(direccion, politicoMgl.getGpoId());
        if (drDireccion == null) {
            throw new ApplicationException("La direccion no es tradusible, debe digitarla tabulada.");
        }
        if(politicoMgl.getGpoMultiorigen().equalsIgnoreCase("1")){
            if(barrio==null || barrio.isEmpty()){
                throw new ApplicationException("para las direcciones de ciudades multiorigen, se debe digitar el barrio");
            }
            drDireccion.setBarrio(barrio);
        }
        DireccionRREntity rREntity = convertirDrDireccionARR(drDireccion, politicoMgl.getGpoMultiorigen());
        if (rREntity == null || rREntity.getCalle() == null || rREntity.getCalle().isEmpty()
                || rREntity.getNumeroUnidad() == null || rREntity.getNumeroUnidad().isEmpty()
                || rREntity.getNumeroApartamento() == null || rREntity.getNumeroApartamento().isEmpty()) {
            throw new ApplicationException("La direccion no se pudo transformar a formato RR (Claro Fija)");
        }
        rREntity.setNumeroApartamento("");
        if (rREntity.getCalle().trim().length() > 50
                || rREntity.getNumeroUnidad().trim().length() > 10) {
            throw new ApplicationException("Uno de los 2 componentes Calle(50 caracteres) y/o Unidad(10 caracteres) "
                    + "supero la longitud via Pincipal["+rREntity.getCalle()+"]-Placa["+rREntity.getNumeroUnidad()+"]");
        }
        return rREntity;
    }

    public DrDireccion convertirDireccionStringADrDireccion(String direccion, BigDecimal centroPoblado) throws ApplicationException {
        errorDescription = "";
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPoblado); 
        AddressService responseSrv;
        AddressRequest requestSrv = new AddressRequest();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        requestSrv.setAddress(direccion);
        requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
        requestSrv.setCodDaneVt(centroPobladoCompleto.getGeoCodigoDane());
        requestSrv.setLevel("C");
        requestSrv.setState(""); 
        responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
        if (("true").equalsIgnoreCase(responseSrv.getTraslate())) {
            DetalleDireccionEntity dirTabulada;
            try {
                dirTabulada = tabularDireccion(responseSrv, centroPobladoCompleto, "");
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                errorDescription = ex.getMessage();
                LOGGER.error(msg, ex);
                return null;
            }

            dirTabulada.setIdtipodireccion("CK");
            DrDireccion drDireccion = new DrDireccion();
            drDireccion.obtenerFromDetalleDireccionEntity(dirTabulada);
            drDireccion.setDireccionRespuestaGeo(responseSrv.getAddress());
            return drDireccion;
        } else {

            if (StringUtils.isNotBlank(responseSrv.getRecommendations())
                    && StringUtils.containsIgnoreCase(responseSrv.getRecommendations(), "ERROR")) {
                errorDescription = responseSrv.getRecommendations();
            }
            return null;
        }
    }
    
     public FichasGeoDrDireccionDto convertirDireccionStringADrDireccionFichas(String direccion, BigDecimal centroPoblado) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        FichasGeoDrDireccionDto fichasGeoDrDireccionDto = new FichasGeoDrDireccionDto();
        GeograficoPoliticoMgl centroPobladoCompleto = geograficoPoliticoManager.findById(centroPoblado);
        GeograficoPoliticoMgl ciudadGpo = geograficoPoliticoManager.findById(centroPobladoCompleto.getGeoGpoId());        
        GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudadGpo.getGeoGpoId());
        AddressService responseSrv;
        AddressRequest requestSrv = new AddressRequest();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        requestSrv.setAddress(direccion);
        requestSrv.setCity(centroPobladoCompleto.getGpoNombre());
        requestSrv.setCodDaneVt(centroPobladoCompleto.getGeoCodigoDane());
        requestSrv.setLevel("C");
        requestSrv.setState(departamento.getGpoNombre()); 
        responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
        if (("true").equalsIgnoreCase(responseSrv.getTraslate())) {
            DetalleDireccionEntity dirTabulada;
            try {
                dirTabulada = tabularDireccion(responseSrv, centroPobladoCompleto, "");
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                return null;
            }

            dirTabulada.setIdtipodireccion("CK");
            DrDireccion drDireccion = new DrDireccion();
            drDireccion.obtenerFromDetalleDireccionEntity(dirTabulada);
            fichasGeoDrDireccionDto.setDrDireccion(drDireccion);
            fichasGeoDrDireccionDto.setResponseGeo(responseSrv);
            return fichasGeoDrDireccionDto;
        } else {
            return null;
        }
    }

    public DireccionRREntity convertirDrDireccionARR(DrDireccion drDireccion, String multiorigen) throws ApplicationException {
        DetalleDireccionEntity direccionEntity = drDireccion.convertToDetalleDireccionEntity();
        direccionEntity.setMultiOrigen(multiorigen);
        try {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccionEntity, "",null);
            return direccionRRManager.getDireccion();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
    }

    public boolean actualizarDireccionMayaNueva(String direccion, GeograficoPoliticoMgl ciudad, String barrio, boolean idCalleCarrera) throws ApplicationException {

        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        AddressService responseSrv = new AddressService();
        AddressRequest requestSrv = new AddressRequest();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
        BigDecimal idDireccion = null;

        requestSrv.setAddress(direccion);

        requestSrv.setCity(ciudad.getGpoNombre());
        requestSrv.setCodDaneVt(ciudad.getGeoCodigoDane());
        requestSrv.setLevel("C");
        GeograficoPoliticoMgl departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
        requestSrv.setState(departamento.getGpoNombre());
        if (ciudad.getGpoMultiorigen().equalsIgnoreCase("1") && idCalleCarrera) {
            requestSrv.setNeighborhood(barrio);
        }
        try {
            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
        if (responseSrv.getIdaddress() != null && !responseSrv.getIdaddress().isEmpty() && responseSrv.getIdaddress().toUpperCase().contains("D")) {
            idDireccion = new BigDecimal(responseSrv.getIdaddress().replace("D", ""));
        }

        if (responseSrv.getAddress() != null && !responseSrv.getAddress().isEmpty()) {
            if (responseSrv.getSource() != null
                    && !responseSrv.getSource().isEmpty()
                   && responseSrv.getSource().equalsIgnoreCase(Constant.DIRECCIONES_ORIGEN_ANTIGUA)) {
                requestSrv.setAddress(responseSrv.getAlternateaddress());
                requestSrv.setCodDaneVt(ciudad.getGeoCodigoDane());
                requestSrv.setLevel("C");
                requestSrv.setState(departamento.getGpoNombre());
                requestSrv.setNeighborhood(barrio);
                if (ciudad.getGpoMultiorigen().equalsIgnoreCase("1") && idCalleCarrera) {
                    requestSrv.setNeighborhood(barrio);
                }
                try {
                    responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                    LOGGER.error(msg);
                    throw new ApplicationException(ex.getMessage());
                }
                if (responseSrv.getAlternateaddress() != null && !responseSrv.getAlternateaddress().isEmpty()) {
                    if (responseSrv.getSource() != null
                            && !responseSrv.getSource().isEmpty()
                            && responseSrv.getSource().equalsIgnoreCase(Constant.DIRECCIONES_ORIGEN_NUEVA)) {
                        if (idDireccion != null) {
                            DireccionMgl direccionMgl = new DireccionMgl();
                            direccionMgl.setDirId(idDireccion);
                            DireccionMglManager direccionMglManager = new DireccionMglManager();
                            direccionMgl = direccionMglManager.findById(direccionMgl);
                            direccionMgl.setDirFormatoIgac(responseSrv.getAddress());
                            direccionMgl.setDirNostandar(responseSrv.getAddress());
                            direccionMgl.setDirServinformacion(responseSrv.getAddressCodeFound());
                            direccionMglManager.update(direccionMgl);
                        }
                    } else if (responseSrv.getSource() != null
                            && !responseSrv.getSource().isEmpty()
                            && responseSrv.getSource().equalsIgnoreCase(Constant.DIRECCIONES_ORIGEN_NUEVA)) {
                        if (idDireccion == null) {
                            AddressService responseSrv2 = new AddressService();
                            AddressRequest requestSrv2 = new AddressRequest();
                            requestSrv2.setAddress(responseSrv.getAlternateaddress());
                            requestSrv2.setCity(ciudad.getGpoNombre());
                            requestSrv2.setCodDaneVt(ciudad.getGeoCodigoDane());
                            requestSrv2.setLevel("C");
                            requestSrv2.setState(departamento.getGpoNombre());
                            if (ciudad.getGpoMultiorigen().equalsIgnoreCase("1") && idCalleCarrera) {
                                requestSrv2.setNeighborhood(barrio);
                            }
                            try {
                                responseSrv2 = addressEJBRemote.queryAddressEnrich(requestSrv);
                            } catch (ApplicationException ex) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                                LOGGER.error(msg);
                                throw new ApplicationException(ex.getMessage());
                            }
                            if (responseSrv2.getIdaddress() != null
                                    && !responseSrv2.getIdaddress().isEmpty()
                                    && responseSrv2.getIdaddress().toUpperCase().contains("D")) {
                                idDireccion = new BigDecimal(responseSrv2.getIdaddress().replace("D", ""));
                                
                                    DireccionMgl direccionMgl = new DireccionMgl();
                                    direccionMgl.setDirId(idDireccion);
                                    DireccionMglManager direccionMglManager = new DireccionMglManager();
                                    direccionMgl = direccionMglManager.findById(direccionMgl);
                                    direccionMgl.setDirFormatoIgac(responseSrv.getAddress());
                                    direccionMgl.setDirNostandar(responseSrv.getAddress());
                                    direccionMgl.setDirServinformacion(responseSrv.getAddressCodeFound());
                                    direccionMglManager.update(direccionMgl);
                                

                            }
                        }
                    }
                }

            }
        }

        return true;
    }
    
    /**
     * Autor: Victor Bocanegra
     * Consulta las unidades previas desde MGL
     * desde la direccion Detallada
     * @param cmtDireccionSolictudMgl {@link CmtDireccionSolicitudMgl}
     * @return List<CmtUnidadesPreviasMgl>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
   public List<CmtUnidadesPreviasMgl> unidadesDeLaDireccionMgl(CmtDireccionSolicitudMgl
           cmtDireccionSolictudMgl) throws ApplicationException {
      
       List<CmtUnidadesPreviasMgl> unidadesCercanasResponse = new ArrayList<>();
       List<CmtDireccionDetalladaMgl> lstCmtDireccionDetalladaMgl;
      
       
       HhppMglManager hhppMglManager = new HhppMglManager();
       List<HhppMgl> lstHhppMgl;


       CmtDireccionDetalleMglManager cmtDireccionDetalleMglManager = new CmtDireccionDetalleMglManager();
       lstCmtDireccionDetalladaMgl = cmtDireccionDetalleMglManager.retornaDireccionDetUnidadesPrevias(cmtDireccionSolictudMgl);
     
               
       if (!lstCmtDireccionDetalladaMgl.isEmpty()) {

           for (CmtDireccionDetalladaMgl detalleMgl : lstCmtDireccionDetalladaMgl) {
               String infoTec="";
               CmtUnidadesPreviasMgl previasMgl = new CmtUnidadesPreviasMgl();
               BigDecimal idSubdireccion = detalleMgl.getSubDireccion() != null ? detalleMgl.getSubDireccion().getSdiId() : null;
               lstHhppMgl = hhppMglManager.findHhppByDirIdSubDirId(detalleMgl.getDireccion().getDirId(), idSubdireccion);

               if (!lstHhppMgl.isEmpty()) {
                   for (HhppMgl hhppMgl : lstHhppMgl) {

                       infoTec += hhppMgl.getNodId().getNodCodigo() + ";"
                               + hhppMgl.getNodId().getNodTipo().getNombreBasica() + ";"
                               + hhppMgl.getHhpEstadoUnit() + "|";

                   }
               }
               if (!lstHhppMgl.isEmpty()) {
                   previasMgl.setCmtDireccionDetalladaMgl(detalleMgl);
                   previasMgl.setInfoTecHhpp(infoTec);
                   previasMgl.setNivel5TipoNuevo(detalleMgl.getCpTipoNivel5());
                   previasMgl.setNivel5ValorNuevo(detalleMgl.getCpValorNivel5());
                   previasMgl.setNivel6TipoNuevo(detalleMgl.getCpTipoNivel6());
                   previasMgl.setNivel6ValorNuevo(detalleMgl.getCpValorNivel6());
                   unidadesCercanasResponse.add(previasMgl);
               }
           }
       }


        if (cmtDireccionSolictudMgl.getCodTipoDir().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {
            AddressRequest addressRequest = new AddressRequest();
            AddressService addressService;
            DrDireccionManager direccionManager = new DrDireccionManager();
            DrDireccion drDireccion = cmtDireccionSolictudMgl.getCamposDrDireccion();
            String direccion = direccionManager.getDireccion(drDireccion);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            if (cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoMultiorigen().equals("1")) {
                addressRequest.setNeighborhood(cmtDireccionSolictudMgl.getBarrio());
            } else {
                addressRequest.setNeighborhood("");
            }
            addressRequest.setState(cmtDireccionSolictudMgl.getSolicitudCMObj().getDepartamentoGpo().getGpoNombre());
            addressRequest.setCity(cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoNombre());
            addressRequest.setCodDaneVt(cmtDireccionSolictudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGeoCodigoDane());
            addressRequest.setLevel("C");
            addressRequest.setAddress(direccion);
            try {
                addressService = addressEJBRemote.queryAddress(addressRequest);

                if (addressService != null) {
                    if (addressService.getTraslate() != null) {
                        if (addressService.getTraslate().equalsIgnoreCase("true")) {
                            if (addressService.getAlternateaddress() != null && !addressService.getAlternateaddress().isEmpty()) {
                                addressRequest.setAddress(addressService.getAlternateaddress());
                                try {
                                    addressService = addressEJBRemote.queryAddress(addressRequest);
                                } catch (ApplicationException ex) {
                                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                                    LOGGER.error(msg);
                                    throw new ApplicationException(ex.getMessage());
                                }

                                if (addressService.getTraslate().equalsIgnoreCase("true")) {

                                    DetalleDireccionEntity direccionEntity = tabularDireccion(addressService, cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo(),
                                            cmtDireccionSolictudMgl.getBarrio());
                                    direccionEntity.setIdtipodireccion("CK");
                                    direccionEntity.setMultiOrigen(cmtDireccionSolictudMgl.getSolicitudCMObj().getCiudadGpo().getGpoMultiorigen());

                                    CmtDireccionSolicitudMglManager cmtDireccionSolicitudMglManager = new CmtDireccionSolicitudMglManager();
                                    cmtDireccionSolictudMgl = cmtDireccionSolicitudMglManager.parseDetalleDireccionEntityToCmtDireccionSolicitudMgl(direccionEntity);
                                    lstCmtDireccionDetalladaMgl = cmtDireccionDetalleMglManager.retornaDireccionDetUnidadesPrevias(cmtDireccionSolictudMgl);

                                    if (!lstCmtDireccionDetalladaMgl.isEmpty()) {

                                        for (CmtDireccionDetalladaMgl detalladaMgl : lstCmtDireccionDetalladaMgl) {
                                            String infoTec = "";
                                            CmtUnidadesPreviasMgl previasMgl = new CmtUnidadesPreviasMgl();
                                            lstHhppMgl = hhppMglManager.findHhppDireccion(detalladaMgl.getDireccion().getDirId());

                                            if (!lstHhppMgl.isEmpty()) {
                                                for (HhppMgl hhppMgl : lstHhppMgl) {

                                                    infoTec += hhppMgl.getNodId().getNodCodigo() + ";"
                                                            + hhppMgl.getNodId().getNodTipo().getNombreBasica() + ";"
                                                            + hhppMgl.getHhpEstadoUnit() + "|";

                                                }
                                            }
                                            previasMgl.setCmtDireccionDetalladaMgl(detalladaMgl);
                                            previasMgl.setInfoTecHhpp(infoTec);
                                            unidadesCercanasResponse.add(previasMgl);
                                        }

                                    }
                                }

                            }
                        }
                    }
               }

            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(ex.getMessage());
            }

        }        return unidadesCercanasResponse;
    }
   
   
        /**
     * Autor: cardenaslb
     *
     * @param request
     * @param mallaNuevaAmbigua
     * @param usuarios
     * @return List<AddressSuggested>
     * @throws Exception
     */

    public  List<String>  obtenerBarrios(DrDireccion drDireccion , String direccionCm ,BigDecimal ciudad, String usuarios,boolean mallaNuevaAmbigua) {
        List<AddressSuggested> barrioList;
          List<String> barrios = null;
        try {
            GeograficoPoliticoMgl centroPoblado;
            GeograficoPoliticoMgl ciudadEntity;
            GeograficoPoliticoMgl departamentoEntity;
            GeograficoPoliticoMgl paisEntity;
            CityEntity cityEntityRequest = new CityEntity();
             GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            centroPoblado = geograficoPoliticoManager.findById(ciudad);
            // ciudad 
            ciudadEntity = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
            // departamento
            departamentoEntity = geograficoPoliticoManager.findById(ciudadEntity.getGeoGpoId());
            // pais 
            paisEntity = geograficoPoliticoManager.findById(departamentoEntity.getGeoGpoId());
           //
            cityEntityRequest.setCodCity(centroPoblado.getGeoCodigoDane());
            DrDireccionManager drDireccionManager = new DrDireccionManager();
           String direccion;
            if (drDireccion != null) {
                direccion = drDireccionManager.getDireccion(drDireccion);
                cityEntityRequest.setAddress(direccion);
            } else {
                cityEntityRequest.setAddress(direccionCm);
            }
            
            //Enviamos tipo solicicitud '4' para que no realice la validacion de direccion antigua nueva
            cityEntityRequest.setTipoSolictud("4");
            CityEntity cityEntity=null;
            NegocioParamMultivalor param = new NegocioParamMultivalor();
            
            cityEntityRequest.setCityName(centroPoblado.getGpoNombre());
            cityEntityRequest.setDpto(departamentoEntity.getGpoNombre());
            cityEntityRequest.setPais(paisEntity.getGpoNombre());
            cityEntityRequest.setCodDane(centroPoblado.getGeoCodigoDane());

                       /*Consultar el WebService parera obtener los datos de la direccion
             * ingresada */
            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
           
            //Asignar la ciudad
            requestSrv.setCity(cityEntityRequest.getCityName());
            //Asignar el departamento
            requestSrv.setState(cityEntityRequest.getDpto());
            //Asignar la direccion a consultar
            requestSrv.setAddress(cityEntityRequest.getAddress());
            //Asignar el barrio
            requestSrv.setNeighborhood(cityEntityRequest.getBarrio());
            
            if(cityEntityRequest.getCodDane() != null && !cityEntityRequest.getCodDane().isEmpty()){
                requestSrv.setCodDaneVt(cityEntityRequest.getCodDane());
            }

            requestSrv.setLevel(co.com.telmex.catastro.services.util.Constant.TIPO_CONSULTA_COMPLETA);
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

            cityEntityRequest.setExistencia(responseSrv.getExist());
            //Estandarizacion del Nodo
            cityEntityRequest.setNodo1(estandarizaNodo(responseSrv.getNodoUno()));
            cityEntityRequest.setNodo2(estandarizaNodo(responseSrv.getNodoDos()));
            cityEntityRequest.setNodo3(estandarizaNodo(responseSrv.getNodoTres()));
            cityEntityRequest.setNodoDTH(estandarizaNodo(responseSrv.getNodoDth()));

            cityEntityRequest.setEstratoDir(responseSrv.getLeveleconomic() == null ? "0"
                    : responseSrv.getLeveleconomic());
            cityEntityRequest.setEstadoDir(responseSrv.getState() == null ? ""
                    : responseSrv.getState());

            if (responseSrv != null) {
                // si el barrio viene nulo o vacio asignamos el que nos entrega el GEO
                if (responseSrv.getNeighborhood() != null
                        && !responseSrv.getNeighborhood().isEmpty()
                        && !responseSrv.getNeighborhood().equalsIgnoreCase("N.N.")) {

                    cityEntityRequest.setBarrio(responseSrv.getNeighborhood());

                }
            }

            
            if (cityEntityRequest.getBarrioSugerido() != null
                    && !cityEntityRequest.getBarrioSugerido().isEmpty()
                    && cityEntityRequest.getDireccion() != null
                    && !cityEntityRequest.getDireccion().trim().isEmpty()) {
                barrioList = new ArrayList<AddressSuggested>();
                for (AddressSuggested addressSuggested : cityEntityRequest.getBarrioSugerido()) {
                    if (cityEntityRequest.getDireccion().trim().
                            equalsIgnoreCase(addressSuggested.getAddress().trim())) {
                        barrioList.add(addressSuggested);
                    }
                }
               barrios = new ArrayList<String>();
                 for (AddressSuggested barrio :barrioList) {
                     barrios.add(barrio.getNeighborhood());
                }
                
                
            }
            
            
        } catch (ApplicationException ex) {
            LOGGER.error("Error Cargando lista de Barrio sugerido " + ex.getMessage());
        }
        return barrios;
    }   
   
   
     /**
     * Función para obtener las coberturas de una dirección arrojadas por el GEO 
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
   public AddressService calcularCoberturaDrDireccion(DrDireccion drDireccion,
           GeograficoPoliticoMgl centroPoblado) throws ApplicationException {       
        String direccion;
        String barrio; 
        
        AddressRequest addressRequest = new AddressRequest();
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
  
        direccion = drDireccionManager.getDireccion(drDireccion);
               
        if (drDireccion.getBarrio() != null && centroPoblado.getGpoMultiorigen().
                equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
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

        AddressService response = addressEJBRemote.queryAddress(addressRequest);        

        //Si la ciudad es multiorigen el barrio debe coincidir con el que entrega el geo para retornar las coberturas
       if (response != null && centroPoblado.getGpoMultiorigen().
               equalsIgnoreCase(Constant.DIRECCIONES_MULTIORIGEN)
               && drDireccion.getIdTipoDireccion().equalsIgnoreCase(Constant.ADDRESS_TIPO_CK)) {

           if (response.getNeighborhood() != null
                   && response.getNeighborhood().equalsIgnoreCase(barrio)) {
               return response;
           } else {
               response.setNodoDos("");
               response.setNodoDth("");
               response.setNodoFtth("");
               response.setNodoMovil("");
               response.setNodoTres("");
               response.setNodoUno("");
               response.setNodoWifi("");
               response.setNodoZona3G("");
               response.setNodoZona4G("");
               response.setNodoZona5G("");
               response.setNodoZonaCoberturaCavs("");
               response.setNodoZonaCoberturaUltimaMilla("");
               response.setNodoZonaCurrier("");
               response.setNodoZonaGponDiseniado("");
               response.setNodoZonaMicroOndas("");
               response.setNodoZonaUnifilar("");
               return response;
           }
       }

        return response;
    }
}
