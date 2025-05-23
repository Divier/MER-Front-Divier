package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.CmtTablasBasicasSincronizacionRRMglManager;
import co.com.claro.mgl.dao.impl.GeograficoPoliticoDaoImpl;
import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.RRDane;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.rest.dtos.GeoPoliticoDto;
import co.com.claro.mgl.rest.dtos.MglRequestGeoPoliticoDto;
import co.com.claro.mgl.rest.dtos.MglResponseGeoPoliticoDto;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.response.ResponseGeograficoPolitico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class GeograficoPoliticoManager {

    ParametrosMglManager parametrosMglManager;

    String HABILITAR_RR;

    GeograficoPoliticoDaoImpl geograficoPoliticoDaoImpl = new GeograficoPoliticoDaoImpl();

    private static final Logger LOGGER = LogManager.getLogger(GeograficoPoliticoManager.class);

    public GeograficoPoliticoManager() {

    }
    
    public GeograficoPoliticoMgl findById(BigDecimal id) throws ApplicationException {
        return geograficoPoliticoDaoImpl.find(id);
    }

    public List<GeograficoPoliticoMgl> findAll() throws ApplicationException {
        List<GeograficoPoliticoMgl> resulList;
        resulList = geograficoPoliticoDaoImpl.findAll();
        return resulList;
    }

    public List<GeograficoPoliticoMgl> findAllItems() throws ApplicationException {
        List<GeograficoPoliticoMgl> resulList;
        resulList = geograficoPoliticoDaoImpl.findAllItems();
        return resulList;
    }

    public GeograficoPoliticoMgl create(GeograficoPoliticoMgl t, String usuario) throws ApplicationException {

        GeograficoPoliticoMgl geograficoPoliticoMgl = null;
		
		geograficoPoliticoMgl = geograficoPoliticoDaoImpl.create(t);

        return geograficoPoliticoMgl;
    }

    public GeograficoPoliticoMgl update(GeograficoPoliticoMgl t, String usuario) throws ApplicationException {

        GeograficoPoliticoMgl geograficoPoliticoMgl = null;
        CmtTablasBasicasSincronizacionRRMglManager cmtTablasBasicasSincronizacionRRMglManager
                = new CmtTablasBasicasSincronizacionRRMglManager();

        if (cmtTablasBasicasSincronizacionRRMglManager.actualizarGeograficoPoliticoRr(t, usuario)) {
            geograficoPoliticoMgl = geograficoPoliticoDaoImpl.update(t);
        } else {
            throw new ApplicationException("No se pudo Actualizar el registro");
        }

        return geograficoPoliticoMgl;
    }

    public boolean delete(GeograficoPoliticoMgl t, String usuario) throws ApplicationException {
        boolean isDelete = false;
        CmtTablasBasicasSincronizacionRRMglManager cmtTablasBasicasSincronizacionRRMglManager
                = new CmtTablasBasicasSincronizacionRRMglManager();

        parametrosMglManager = new ParametrosMglManager();
        try {
            ParametrosMgl parametroRR = parametrosMglManager.findByAcronimoName(Constant.HABILITAR_RR);
            if (parametroRR != null) {
                HABILITAR_RR = parametroRR.getParDescripcion();
            } else {
                LOGGER.error("No se encuentra configurado el parámetro HABILITAR_RR.");
                HABILITAR_RR = null;
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error al momento de consultar los parámetros del administrador de geográfico político: " + ex.getMessage(), ex);
            HABILITAR_RR = null;
        }

        if (HABILITAR_RR != null) {
            if (HABILITAR_RR.equals("1")) {
                if (cmtTablasBasicasSincronizacionRRMglManager.eliminarGeograficoPoliticoRr(t, usuario)) {
                    isDelete = geograficoPoliticoDaoImpl.delete(t);
                } else {
                    throw new ApplicationException("No se pudo Eliminar el registro");
                }
            } else {
                isDelete = geograficoPoliticoDaoImpl.delete(t);
            }
        } else {
            throw new ApplicationException("No se encuentra configurado el parámetro HABILITAR_RR.");
        }

        return isDelete;
    }
    
        public GeograficoPoliticoMgl updateGeograficoPolitico(GeograficoPoliticoMgl geograficoPolitico, String usuario, int perfil) throws ApplicationException {
        return geograficoPoliticoDaoImpl.updateCm(geograficoPolitico, usuario, perfil);
    }

    public GeograficoPoliticoMgl find(GeograficoPoliticoMgl sqlData) throws ApplicationException {
        return geograficoPoliticoDaoImpl.find(sqlData.getGeoGpoId());
    }

    public List<GeograficoPoliticoMgl> findPaises() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findPaises();
    }

    public List<GeograficoPoliticoMgl> findDptos() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findDptos();
    }

    public List<GeograficoPoliticoMgl> findCiudades(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCiudades(geoGpoId);
    }

    public List<GeograficoPoliticoMgl> findAllCiudades() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findAllCiudades();
    }

    public List<GeograficoPoliticoMgl> findCentrosPoblados(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCentrosPoblados(geoGpoId);
    }

    public List<GeograficoPoliticoMgl> findCentroPoblado(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCiudades(geoGpoId);
    }

    public String esMultiorigen(BigDecimal gpoId) throws ApplicationException {
        return geograficoPoliticoDaoImpl.esMultiorigen(gpoId);
    }

    public List<GeograficoPoliticoMgl> findNombre(String nombre) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findNombre(nombre);
    }

    public List<GeograficoPoliticoMgl> findCiudadesDepartamentos() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCiudadesDepartamentos();
    }

    public GeograficoPoliticoMgl findCityByCodDane(String geoCodigoDane) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCityByCodDane(geoCodigoDane);
    }

    public GeograficoPoliticoMgl findCityByCodDaneCp(String geoCodigoDane) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCityByCodDaneCp(geoCodigoDane);
    }

    /**
     * Obtienw un ciudad.Permite obtener una ciudad por medio del codigo de la
 comunidad
     *
     * @author Johnnatan Ortiz
     * @param codComunidad codigo de la comunidad.
     * @return ciudad.
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findCityByCodComunidad(String codComunidad)
            throws ApplicationException {
        GeograficoPoliticoMgl result = null;
        RRDaneManager rrDaneManager = new RRDaneManager();
        RRDane rrDane = rrDaneManager.findByCodCiudad(codComunidad);
        if (rrDane != null
                && rrDane.getCodigoDane() != null
                && !rrDane.getCodigoDane().isEmpty()) {
            result = geograficoPoliticoDaoImpl.
                    findCityByCodDane(rrDane.getCodigoDane());
        }
        return result;
    }

    public GeograficoPoliticoMgl findPopulationCenterByCodDane(String geoCodigoDane)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findPopulationCenterByCodDane(geoCodigoDane);
    }

    public GeograficoPoliticoMgl findCityByComundidad(String comunidad) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCityByComundidad(comunidad);
    }

    /*author Juan David Hernandez*/
    public List<ResponseGeograficoPolitico> obtenerCentroPobladoList(String ciudadRr)
            throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
        List<GeograficoPoliticoMgl> centroPobladoList = new ArrayList();
        List<ResponseGeograficoPolitico> responseGeograficoPoliticoList = new ArrayList();
        CmtComunidadRr cmtComunidadRR = cmtComunidadRrManager.findComunidadByCodigo(ciudadRr);
        if (cmtComunidadRR != null) {
            centroPobladoList = geograficoPoliticoManager.findCentrosPoblados(cmtComunidadRR.getCiudad().getGeoGpoId());
                        
            if(centroPobladoList != null && !centroPobladoList.isEmpty()){
                for (GeograficoPoliticoMgl geograficoPoliticoMgl : centroPobladoList) {
                    responseGeograficoPoliticoList.add(parseGeograficoPoliticoMglToResponseGeograficoPolitico(geograficoPoliticoMgl));
               }
            }        
        }
        return responseGeograficoPoliticoList;
    }

    /**
     * valbuenayf Metodo para buscar la centro poblado por el codigo dane
     *
     * @param geoCodigoDane
     * @return
     */
    public GeograficoPoliticoMgl findCentroPobladoCodDane(String geoCodigoDane) {
        return geograficoPoliticoDaoImpl.findCentroPobladoCodDane(geoCodigoDane);
    }

    /**
     * valbuenayf Metodo para buscar la ciudad por el codigo dane
     *
     * @param geoCodigoDane
     * @return
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findCiudadCodDane(String geoCodigoDane)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCiudadCodDane(geoCodigoDane);
    }

    /**
     * valbuenayf Metodo para buscar el departamento por un id centro poblado *
     * (o) idciudad
     *
     * @param idCiuCenPob
     * @return
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findDepartamentoIdCiudadCenPob(BigDecimal idCiuCenPob) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findDepartamentoIdCiudadCenPob(idCiuCenPob);
    }

    /**
     * valbuenayf Metodo para buscar el pais por el id
     *
     * @param idPais
     * @return
     */
    public GeograficoPoliticoMgl findPaisId(BigDecimal idPais) {
        return geograficoPoliticoDaoImpl.findPaisId(idPais);
    }

    /**
     * Metodo para consultar centro poblado principal por id de ciudad
     *
     * @param idCiudad identificador del registro de ciudad
     * @return {@link GeograficoPoliticoMgl} registro de centro poblado
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl obtenerCentroPobladoPorCiudad(BigDecimal idCiudad)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.obtenerCentroPobladoPorCiudad(idCiudad);
    }

    public List<GeograficoPoliticoMgl> findCiudadesPaginacion(int page,
            int maxResults, BigDecimal geoGpoId) throws ApplicationException {

        int firstResult = 0;
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        return geograficoPoliticoDaoImpl.findCiudadesPaginacion(firstResult,
                maxResults, geoGpoId);
    }

    public int countCiudades(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoDaoImpl.countCiudades(geoGpoId);
    }

    /**
     * Metodo para consultar ciudad por codigo DANEl Autor: Victor Bocanegra
     *
     * @param codigoDane
     * @return {@link GeograficoPoliticoMgl} registro de ciudad
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findCityByCodigoDane(String codigoDane) throws ApplicationException {

        return geograficoPoliticoDaoImpl.findCityByCodigoDane(codigoDane);
    }


    public GeograficoPoliticoMgl findGeoPoliticoById(BigDecimal id)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findGeoPoliticoById(id);
    }

    public GeograficoPoliticoMgl findGeoPoliticoByCodigo(String codigo)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findGeoPoliticoByCodigo(codigo);
    }

    /**
     * Metodo para buscar una ciudad por centro poblado
     *
     * @param geoGpoId
     * @author Orlando Velasquez
     * @return {@link GeograficoPoliticoMgl} registro de ciudad
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findCiudadByCentroPoblado(BigDecimal geoGpoId)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCiudadByCentroPoblado(geoGpoId);
    }

    /**
     * Metodo para buscar un departamento por ciudad
     *
     * @param geoGpoId
     * @author Orlando Velasquez
     * @return {@link GeograficoPoliticoMgl} registro de ciudad
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findDepartamentoByCiudad(BigDecimal geoGpoId)
            throws ApplicationException {
        return geograficoPoliticoDaoImpl.findDepartamentoByCiudad(geoGpoId);
    }
    
    /**
     * Metodo para consultar geopolitico por nombre y tipo Autor: Victor
     * Bocanegra
     *
     * @param nombre
     * @param tipo
     * @return {@link GeograficoPoliticoMgl}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findGeoPolByNombreAndTipo(String nombre, String tipo)
            throws ApplicationException {

        return geograficoPoliticoDaoImpl.findGeoPolByNombreAndTipo(nombre, tipo);
    }
    
    
    
    public ResponseGeograficoPolitico parseGeograficoPoliticoMglToResponseGeograficoPolitico
            (GeograficoPoliticoMgl geograficoPolitico){
        
        ResponseGeograficoPolitico responseGeograficoPolitico = new ResponseGeograficoPolitico();
        
        if(geograficoPolitico.getGeoCodigoDane() != null && !geograficoPolitico.getGeoCodigoDane().isEmpty()){
            responseGeograficoPolitico.setGeoCodigoDane(geograficoPolitico.getGeoCodigoDane());
        }
        
        if(geograficoPolitico.getGeoGpoId()!= null){
            responseGeograficoPolitico.setGeoGpoId(geograficoPolitico.getGeoGpoId());
            
        }
        
        if(geograficoPolitico.getFechaCreacion() != null){
            responseGeograficoPolitico.setGpoFechaCreacion(geograficoPolitico.getFechaCreacion());
            
        }
        
        if(geograficoPolitico.getGpoId() != null){
            responseGeograficoPolitico.setGpoId(geograficoPolitico.getGpoId());
            
        }
        
        if(geograficoPolitico.getGpoNombre() != null && !geograficoPolitico.getGpoNombre().isEmpty()){
            responseGeograficoPolitico.setGpoNombre(geograficoPolitico.getGpoNombre());
        }
        
        if(geograficoPolitico.getGpoTipo() != null && !geograficoPolitico.getGpoTipo().isEmpty()){
            responseGeograficoPolitico.setGpoTipo(geograficoPolitico.getGpoTipo());
        }
        
        if(geograficoPolitico.getUsuarioCreacion() != null && !geograficoPolitico.getUsuarioCreacion().isEmpty()){
            responseGeograficoPolitico.setGpoUsuarioCreacion(geograficoPolitico.getUsuarioCreacion());
        }
        
        if(geograficoPolitico.getUsuarioCreacion() != null && !geograficoPolitico.getUsuarioCreacion().isEmpty()){
            responseGeograficoPolitico.setGpoUsuarioCreacion(geograficoPolitico.getUsuarioCreacion());
        }
        
         if(geograficoPolitico.getGpoMultiorigen() != null && !geograficoPolitico.getGpoMultiorigen().isEmpty()){
            responseGeograficoPolitico.setGpoMultiorigen(geograficoPolitico.getGpoMultiorigen());
        }
        
        return responseGeograficoPolitico;
        
    }
            
    /**
     * Metodo para consultar los nombres de todos los centro poblados Autor:
     * Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<String> findNamesCentroPoblado() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findNamesCentroPoblado();
    }
    
    /**
     * Metodo para consultar centro poblados por nombre Autor: Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<GeograficoPoliticoMgl> findCentroPobladosByNombre(String nombre) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCentroPobladosByNombre(nombre);
    }
    
    /**
     * Metodo para consultar centro poblados por nombre 
     * @author cardenaslb
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<GeograficoPoliticoMgl> findListCentroPoblado() throws ApplicationException {
       
        List<GeograficoPoliticoMgl> resultCentroPoblado = geograficoPoliticoDaoImpl.findListCentroPoblado();
        List<GeograficoPoliticoMgl> resultCiudades = geograficoPoliticoDaoImpl.findAllCiudades();

        if (resultCentroPoblado != null && !resultCentroPoblado.isEmpty()
                && resultCiudades != null && !resultCiudades.isEmpty()) {

            for (GeograficoPoliticoMgl geograficoPoliticoMgl : resultCentroPoblado) {

                for (GeograficoPoliticoMgl ciudad : resultCiudades) {
                    if (geograficoPoliticoMgl.getGeoGpoId().equals(ciudad.getGpoId())) {
                        geograficoPoliticoMgl.setNombreCiudad(ciudad.getGpoNombre());
                        break;
                    }
                }
            }
        }
        return resultCentroPoblado;

    }
 
    /**
     * Metodo para consultar Lista centro poblados Autor: Victor Bocanegra
     * Autor: Victor Bocanegra
     *
     * @return {@link List<CmtGeograficoPoliticoDto>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<CmtGeograficoPoliticoDto> findAllCentroPoblados() throws ApplicationException {
        return geograficoPoliticoDaoImpl.findAllCentroPoblados();
    }

    /**
     * Metodo para la consulta por id de un geografico politico o un grupo de
     * geografico politicos
     *
     * @author Victor Bocanegra
     * @param mglRequestGeoPoliticoDto Request con el ID de Geo
     * @return CmtResponseCreaSolicitudDto respuesta con el proceso de creacion
     * de la solicitud
     *
     */
    public MglResponseGeoPoliticoDto getListGeoPolByGroupId(MglRequestGeoPoliticoDto mglRequestGeoPoliticoDto) {

        MglResponseGeoPoliticoDto responses = new MglResponseGeoPoliticoDto();
        try {

            if (mglRequestGeoPoliticoDto != null) {
                if (mglRequestGeoPoliticoDto.getIdGeoPol() != null) {
                    List<GeograficoPoliticoMgl> lstGeograficoPoliticoMgls = geograficoPoliticoDaoImpl.
                            findCentroPoblado(mglRequestGeoPoliticoDto.getIdGeoPol());
                    if (lstGeograficoPoliticoMgls != null && !lstGeograficoPoliticoMgls.isEmpty()) {
                        List<GeoPoliticoDto> lsGeoPoliticoDtos = new ArrayList<>();
                        responses.setMessageType("I");
                        responses.setMessage("Consulta Exitosa");
                        for (GeograficoPoliticoMgl geograficoPoliticoMgl : lstGeograficoPoliticoMgls) {
                            GeoPoliticoDto geoPoliticoDto = new GeoPoliticoDto();
                            geoPoliticoDto.setGpoId(geograficoPoliticoMgl.getGpoId());
                            geoPoliticoDto.setGpoNombre(geograficoPoliticoMgl.getGpoNombre());
                            geoPoliticoDto.setGpoTipo(geograficoPoliticoMgl.getGpoTipo());
                            geoPoliticoDto.setGeoCodigoDane(geograficoPoliticoMgl.getGeoCodigoDane());
                            lsGeoPoliticoDtos.add(geoPoliticoDto);
                        }
                        responses.setGeoPoliticoList(lsGeoPoliticoDtos);
                    } else {
                        responses.setMessageType("E");
                        responses.setMessage("La consulta no obtiene registros para el numero: " + mglRequestGeoPoliticoDto.getIdGeoPol() + "");
                    }
                } else {
                    responses.setMessageType("E");
                    responses.setMessage("El campo idGeoPol es obligatorio para la consulta");
                }
            } else {
                responses.setMessageType("E");
                responses.setMessage("Debe ingresar un criterio de busqueda");
            }
        } catch (ApplicationException ex) {
            responses.setMessageType("E");
            responses.setMessage(ex.getMessage());
        }
        return responses;
    }

    public List<CmtGeograficoPoliticoDto> findListCiudades(List<String> listaIdCentroPoblado) throws ApplicationException {
        return geograficoPoliticoDaoImpl.findCentroPobladosById(listaIdCentroPoblado);
    }
}
