package co.com.claro.mgl.facade;

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
import co.com.claro.ejb.mgl.address.dto.UnidadesConflictoDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * La clase funciona como interfaz para los metodos implementados en
 * ComponenteDireccionFacade.
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 * @version 1.0
 */
public interface DireccionesValidacionFacadeLocal {

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
     * @param centroPoblado
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String validarDir(CityEntity cityEntityRequest, GeograficoPoliticoMgl centroPoblado)throws ApplicationException ;

    /**
     * Valida una direccion.Realiza las operaciones de validacion en el
        repositorio para verificar existencia. Incluye la validacion de direccion
        nueva y antigua.
     * @author Carlos Leonardo Villamil
     * @param request objeto con la informacion de la direccion a validar.
     * @param mallaNuevaAmbigua TRUE indica si es una dirección ambigua ubicada en malla vial Nueva.
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CityEntity validaDireccion(RequestConstruccionDireccion request, boolean mallaNuevaAmbigua) 
            throws ApplicationException;

    /**
     * @param codCity
     * @return
     */
    public boolean validarMultiorigen(String codCity);

    /**
     * @param pcmlFacadeLocal
     * @param direccionEntity
     * @param codCity
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public UnidadStructPcml getUnidadesDir(DetalleDireccionEntity direccionEntity, String codCity) throws ApplicationException;

    /**
     * @author yimy diaz
     * @param pcmlFacadeLocal
     * @param direccionEntity
     * @param codCity
     * @return List<UnidadStructPcml>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<UnidadStructPcml> getUnidadesCruce(
            DetalleDireccionEntity direccionEntity, String codCity)
            throws ApplicationException;
        
   List<AddressSuggested> obtenerBarrioSugerido(RequestConstruccionDireccion request);
    
    
    /**
     * Valida estructura de una direccion.
     *
     * @author Juan David Hernandez
     * @param direccion
     * @param request objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     public boolean validarEstructuraDireccion(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException; 
     
         /**
     * Valida estructura de una direccion.
     *
     * @author Juan David Hernandez
     * @param direccion
     * @param request objeto con la informacion de la direccion a validar.
     * @param tipoValidacionCM
     * @return resultado de la validacion direccion.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     public boolean validarEstructuraDireccionBoolean(DrDireccion direccion, String tipoValidacionCM) throws ApplicationException;

    /**
     * Función que realiza las validaciones de la dirección correspondientes
     * para la tecnología BI
     *
     * @param direccion            {@link DrDireccion}
     * @param identificadorApp     tecnología con la que se esta realizando la validación
     * @param tipoNivelApartamento tipo de nivel de la dirección
     * @param apartamento          valor del nivel de la dirección
     * @return verdadero si no se encuentran inconvenientes de validación
     * @throws co.com.claro.mgl.error.ApplicationException
     * @author Juan David Hernandez
     */
     boolean validarConstruccionApartamentoBiDireccional(DrDireccion direccion,String identificadorApp,
            String tipoNivelApartamento, String apartamento) throws ApplicationException;


    boolean validarConstruccionApartamentoPorTecnologias(DrDireccion direccion, List<String> codigosTecnologias,
           Map<String, String> datosValidarApartamento) throws ApplicationException;
     /**
     * Función que resuelve los conflictos presentados en unidades
     *
     * @author Juan David Hernandez
     * @param cityEntity que contiene los listados de unidades antiguas y nuevas
     * 
     * @return Listado de unidades con conflicto sin resolver
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public UnidadesConflictoDto resolverConflictosUnidades 
            (CityEntity cityEntity) throws ApplicationException;
    
    
         /**
     * Función que resuelve los conflictos presentados en unidades
     *
     * @author Juan David Hernandez
     * @param request con la información necesaria para la validación
     * @param drDireccion
     * @return Listado con validación de dirección ambigua en el primer 
     * registro y en el segundo la dirección alterna. 1 si es ambigua 0 si no.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<String> validarDireccionAmbigua 
            (String comunidad, String barrio, DrDireccion drDireccion) throws ApplicationException;
    
    /**
     * Autor: Victor Bocanegra
     *
     * @param request
     * @return List<CmtAddressSuggestedDto>
     */
    CmtSuggestedNeighborhoodsDto obtenerBarrioSugeridoHhpp(CmtRequestConstruccionDireccionDto request);

    /**
     * Valida una direccion. Realiza las operaciones de validacion en el
     * repositorio para verificar existencia. Incluye la validacion de direccion
     * nueva y antigua.
     *
     * @author Victor Bocanegra
     * @param request objeto con la informacion de la direccion a validar.
     * @return CmtCityEntityDto resultado de la validacion direccion.
     */
    CmtCityEntityDto validaDireccionHhpp(CmtRequestConstruccionDireccionDto request);
}
