package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.rest.dtos.MglRequestGeoPoliticoDto;
import co.com.claro.mgl.rest.dtos.MglResponseGeoPoliticoDto;
import co.com.claro.mgl.ws.cm.response.ResponseGeograficoPolitico;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface GeograficoPoliticoMglFacadeLocal extends BaseFacadeLocal<GeograficoPoliticoMgl> {

    void setUser(String user, int perfil) throws ApplicationException;

    List<GeograficoPoliticoMgl> findPaises() throws ApplicationException;

    List<GeograficoPoliticoMgl> findDptos() throws ApplicationException;

    List<GeograficoPoliticoMgl> findCiudades(BigDecimal geoGpoId) throws ApplicationException;

    List<GeograficoPoliticoMgl> findAllCiudades() throws ApplicationException;

    List<GeograficoPoliticoMgl> findCentroPoblado(BigDecimal geoGpoId) throws ApplicationException;

    GeograficoPoliticoMgl findById(BigDecimal idGeograficoPoliticoMgl) throws ApplicationException;

    List<GeograficoPoliticoMgl> findNombre(String nombre) throws ApplicationException;

    List<GeograficoPoliticoMgl> findCiudadesDepartamentos() throws ApplicationException;

    List<GeograficoPoliticoMgl> findAllItems() throws ApplicationException;

    String esMultiorigen(BigDecimal geoGpoId) throws ApplicationException;

    List<GeograficoPoliticoMgl> findCentrosPoblados(BigDecimal geoGpoId) throws ApplicationException;

    GeograficoPoliticoMgl findCityByComundidad(String comunidad) throws ApplicationException;
    
    List<GeograficoPoliticoMgl> findCiudadesPaginacion(int page, int maxResults, 
            BigDecimal geoGpoId) throws ApplicationException;
    
    int countCiudades(BigDecimal geoGpoId) throws ApplicationException;
    

   GeograficoPoliticoMgl updateGeograficoPolitico(GeograficoPoliticoMgl t, String usuario, int perfil) throws ApplicationException;
   
   ResponseGeograficoPolitico parseGeograficoPoliticoMglToResponseGeograficoPolitico(GeograficoPoliticoMgl geograficoPolitico);
   
    /**
     * Metodo para consultar los nombres de todos los centro poblados Autor:
     * Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    List<String> findNamesCentroPoblado() throws ApplicationException;
    
        /**
     * Metodo para consultar centro poblados por nombre Autor: Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
   List<GeograficoPoliticoMgl> findCentroPobladosByNombre(String nombre) throws ApplicationException;
   
       /**
     * Metodo para consultar centro poblados por codigo Dane Autor: Victor
     * Bocanegra
     *
     * @param geoCodigoDane
     * @return GeograficoPoliticoMgl
     * @throws ApplicationException excepcion de registros inexistentes
     */
    GeograficoPoliticoMgl findCityByCodDane(String geoCodigoDane) throws ApplicationException;

    List<GeograficoPoliticoMgl> findListCentroPoblado() throws ApplicationException;
    
        /**
     * Metodo para consultar Lista centro poblados Autor: Victor Bocanegra
     * Autor: Victor Bocanegra
     *
     * @return {@link List<GeograficoPoliticoMgl>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
     List<CmtGeograficoPoliticoDto> findAllCentroPoblados() throws ApplicationException;
     
    /**
     * Metodo para la consulta por id de un geografico politico o un grupo de
     * geografico politicos
     *
     * @author Victor Bocanegra
     * @param mglRequestGeoPoliticoDto
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    MglResponseGeoPoliticoDto getListGeoPolByGroupId(MglRequestGeoPoliticoDto mglRequestGeoPoliticoDto);
    
    
}
