package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DireccionesValidacionManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.rest.dtos.CmtCityEntityDto;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtSuggestedNeighborhoodsDto;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.com.claro.ejb.mgl.address.dto.UnidadesConflictoDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
public class DireccionesValidacionFacade implements DireccionesValidacionFacadeLocal {

    private final DireccionesValidacionManager direccionesValidacionManager;
     private String user = "";
    private int perfil = 0;

    public DireccionesValidacionFacade() {
        this.direccionesValidacionManager = new DireccionesValidacionManager();
    }

    /**
     *
     * @param codCity
     * @return direccionesValidacionManager
     * @throws ApplicationException
     */
    @Override
    public CityEntity getCityData(String codCity) throws ApplicationException {
        return direccionesValidacionManager.getCityData(codCity);
    }

    /**
     *
     * @param detalleDireccionEntity
     * @param comunidad
     * @return direccionesValidacionManager
     */
    @Override
    public BigDecimal findSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad) {
        return direccionesValidacionManager.findSolicitudInProcess(detalleDireccionEntity, comunidad);
    }

    /**
     *
     * @param cityEntityRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public String validarDir(CityEntity cityEntityRequest, GeograficoPoliticoMgl centroPoblado) throws ApplicationException {
        return direccionesValidacionManager.validarDir(cityEntityRequest, false, centroPoblado);
    }

    /**
     *
     * @param cityEntityRequest
     * @return direccionesValidacionManager
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public AddressService validarDirAlterna(CityEntity cityEntityRequest) throws ApplicationException {
        AddressService addressService = new AddressService();
        addressService = direccionesValidacionManager.validarDirAlterna(cityEntityRequest);
        return addressService;
    }

    /**
     * @param address
     * @param cityEntityRequest
     * @param tipoDir
     * @param mallaNuevaAmbigua
     * @return
     * @throws ApplicationException
     */
    @Override
    public CityEntity validaDireccion(RequestConstruccionDireccion request, boolean mallaNuevaAmbigua) 
            throws ApplicationException {
        return direccionesValidacionManager.validaDireccion(request, mallaNuevaAmbigua);
    }

    /**
     * @param codCity
     * @return
     */
    @Override
    public boolean validarMultiorigen(String codCity) {
        return direccionesValidacionManager.validarMultiorigen(codCity);
    }

    /**
     * @param pcmlFacadeLocal
     * @param direccionEntity
     * @param codCity
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public UnidadStructPcml getUnidadesDir(DetalleDireccionEntity direccionEntity, String codCity) throws ApplicationException {
        return direccionesValidacionManager.getUnidadesDir(direccionEntity, codCity);
    }

    /**
     * @author yimy diaz
     * @param pcmlFacadeLocal
     * @param direccionEntity
     * @param codCity
     * @return List<UnidadStructPcml>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<UnidadStructPcml> getUnidadesCruce(
            DetalleDireccionEntity direccionEntity, String codCity)
            throws ApplicationException {
        return direccionesValidacionManager.getUnidadesCruce(
                direccionEntity, codCity);
    }

    @Override
    public List<AddressSuggested> obtenerBarrioSugerido(
            RequestConstruccionDireccion request) {
        return direccionesValidacionManager.obtenerBarrioSugerido(request, false);
    }
    
  /**
     * Valida estructura de una direccion.
     *
     * @author Juan David Hernandez
     * @param request objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public boolean validarEstructuraDireccion(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException {
        return direccionesValidacionManager.validarEstructuraDireccion(direccion, tipoValidacionCM);
    }
    
      /**
     * Valida estructura de una direccion.
     *
     * @author Juan David Hernandez
     * @param request objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public boolean validarEstructuraDireccionBoolean(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException {
        return direccionesValidacionManager.validarEstructuraDireccionBoolean(direccion, tipoValidacionCM);
    }
    
    
        /**
     * Función que realiza las validaciones de la dirección correspondientes 
     * para la tecnología BI
     * 
     *  @param direccion
     * @param identificadorApp
     *  @param tipoTecnologia tecnología con la que se esta realizando la
     *   validación
     *  @param tipoNivelApartamento tipo de nivel de la dirección
     *  @param apartamento valor del nivel de la dirección
     *
     * @author Juan David Hernandez
     * 
     *
     * @return verdadero si no se encuentran inconvenientes de validación
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public boolean validarConstruccionApartamentoBiDireccional
            (DrDireccion direccion,String identificadorApp, 
            String tipoNivelApartamento, String apartamento) throws ApplicationException {
        return direccionesValidacionManager
                .validarConstruccionApartamentoBiDireccional(direccion,
                identificadorApp,tipoNivelApartamento, apartamento);
    }

    @Override
    public boolean validarConstruccionApartamentoPorTecnologias(DrDireccion direccion, List<String> codigosTecnologias,
            Map<String, String> datosValidarApartamento) throws ApplicationException {
        return direccionesValidacionManager.validarConstruccionApartamentoPorTecnologias(
                direccion, codigosTecnologias, datosValidarApartamento);
    }


    /**
     * Función que resuelve los conflictos presentados en unidades
     *
     * @author Juan David Hernandez
     * @param cityEntity que contiene los listados de unidades antiguas y nuevas
     * @return Listado de unidades con conflicto sin resolver
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public UnidadesConflictoDto resolverConflictosUnidades(CityEntity cityEntity) throws ApplicationException {
        return direccionesValidacionManager.resolverConflictosUnidades (cityEntity);
    }
    
    /**
     * Autor: Victor Bocanegra
     * @param request
     * @return CmtCityEntityDto
     */
    @Override
    public CmtCityEntityDto validaDireccionHhpp(CmtRequestConstruccionDireccionDto request) 
            {
        Initialized.getInstance();
        return direccionesValidacionManager.validaDireccionHhpp(request);
    }
    
    
     /**
     * Autor: Victor Bocanegra
     * @param request
     * @return List<CmtAddressSuggestedDto>
     */
    @Override
     public CmtSuggestedNeighborhoodsDto obtenerBarrioSugeridoHhpp(
            CmtRequestConstruccionDireccionDto request) {
        Initialized.getInstance();
        return direccionesValidacionManager.obtenerBarrioSugeridoHhpp(request);
    }
    
    
    /**
     * Función que resuelve los conflictos presentados en unidades
     *
     * @author Juan David Hernandez
     * @param request con la información necesaria para la validación
     * @param drDireccion
     * @return Listado con validación de dirección ambigua en el primer registro
     * y en el segundo la dirección alterna. 1 si es ambigua 0 si no.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public List<String> validarDireccionAmbigua(String comunidad, String barrio,
            DrDireccion drDireccion) throws ApplicationException {
        return direccionesValidacionManager.validarDireccionAmbigua(comunidad,
                barrio,drDireccion);
    }

}
