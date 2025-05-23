package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.rest.dtos.MglRequestGeoPoliticoDto;
import co.com.claro.mgl.rest.dtos.MglResponseGeoPoliticoDto;
import co.com.claro.mgl.ws.cm.response.ResponseGeograficoPolitico;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class GeograficoPoliticoMglFacade implements GeograficoPoliticoMglFacadeLocal {

    private final GeograficoPoliticoManager geograficoPoliticoManager;
    private String user;
    private Integer perfil;

    public GeograficoPoliticoMglFacade() {
        this.geograficoPoliticoManager = new GeograficoPoliticoManager();
    }

    @Override
    public List<GeograficoPoliticoMgl> findAll() throws ApplicationException {
        return geograficoPoliticoManager.findAll();
    }

    @Override
    public GeograficoPoliticoMgl create(GeograficoPoliticoMgl t) throws ApplicationException {
        return geograficoPoliticoManager.create(t, user);
    }

    @Override
    public GeograficoPoliticoMgl update(GeograficoPoliticoMgl t) throws ApplicationException {
        return geograficoPoliticoManager.update(t, user);
    }

    @Override
    public boolean delete(GeograficoPoliticoMgl t) throws ApplicationException {
        return geograficoPoliticoManager.delete(t, user);
    }

    @Override
    public GeograficoPoliticoMgl findById(BigDecimal id) throws ApplicationException {
        return geograficoPoliticoManager.findById(id);
    }

    @Override
    public GeograficoPoliticoMgl findById(GeograficoPoliticoMgl sqlData) throws ApplicationException {
        return geograficoPoliticoManager.find(sqlData);
    }

    @Override
    public List<GeograficoPoliticoMgl> findPaises() throws ApplicationException {
        return geograficoPoliticoManager.findPaises();
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<GeograficoPoliticoMgl> findDptos() throws ApplicationException {
        return geograficoPoliticoManager.findDptos();
    }

    @Override
    public List<GeograficoPoliticoMgl> findCiudades(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.findCiudades(geoGpoId);
    }

    @Override
    public List<GeograficoPoliticoMgl> findAllCiudades() throws ApplicationException {
        return geograficoPoliticoManager.findAllCiudades();
    }

    @Override
    public List<GeograficoPoliticoMgl> findCentroPoblado(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.findCentroPoblado(geoGpoId);
    }

    @Override
    public String esMultiorigen(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.esMultiorigen(geoGpoId);
    }

    @Override
    public List<GeograficoPoliticoMgl> findNombre(String nombre) throws ApplicationException {
        return geograficoPoliticoManager.findNombre(nombre);
    }

    @Override
    public List<GeograficoPoliticoMgl> findCiudadesDepartamentos() throws ApplicationException {
        return geograficoPoliticoManager.findCiudadesDepartamentos();
    }

    @Override
    public List<GeograficoPoliticoMgl> findAllItems() throws ApplicationException {
        return geograficoPoliticoManager.findAllItems();
    }

    public GeograficoPoliticoMgl findEsMultiOrigen(BigDecimal geoGpoId) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GeograficoPoliticoMgl> findCentrosPoblados(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.findCentrosPoblados(geoGpoId);
    }

    @Override
    public GeograficoPoliticoMgl findCityByComundidad(String comunidad) throws ApplicationException {
        return geograficoPoliticoManager.findCityByComundidad(comunidad);
    }

    @Override
    public List<GeograficoPoliticoMgl> findCiudadesPaginacion(int page,
            int maxResults, BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.findCiudadesPaginacion(page, maxResults, geoGpoId);
    }

    @Override
    public int countCiudades(BigDecimal geoGpoId) throws ApplicationException {
        return geograficoPoliticoManager.countCiudades(geoGpoId);
    }
    
        @Override
    public GeograficoPoliticoMgl updateGeograficoPolitico(GeograficoPoliticoMgl geograficoPoliticoMgl, String usuario, int perfil) throws ApplicationException {
        return geograficoPoliticoManager.updateGeograficoPolitico(geograficoPoliticoMgl, usuario, perfil);
    }
    
    
    @Override
    public ResponseGeograficoPolitico parseGeograficoPoliticoMglToResponseGeograficoPolitico(GeograficoPoliticoMgl geograficoPolitico) {
        return geograficoPoliticoManager.parseGeograficoPoliticoMglToResponseGeograficoPolitico(geograficoPolitico);
    }
    
    /**
     * Metodo para consultar los nombres de todos los centro poblados Autor:
     * Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public List<String> findNamesCentroPoblado() throws ApplicationException {
        return geograficoPoliticoManager.findNamesCentroPoblado();
    }
    
    /**
     * Metodo para consultar centro poblados por nombre Autor: Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public List<GeograficoPoliticoMgl> findCentroPobladosByNombre(String nombre) throws ApplicationException {
        return geograficoPoliticoManager.findCentroPobladosByNombre(nombre);
    }
    /**
     * Metodo para consultar centro poblados por codigo Dane Autor: Victor
     * Bocanegra
     *
     * @param geoCodigoDane
     * @return GeograficoPoliticoMgl
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public GeograficoPoliticoMgl findCityByCodDane(String geoCodigoDane) throws ApplicationException {
        return geograficoPoliticoManager.findCentroPobladoCodDane(geoCodigoDane);
    }
    
    @Override
     public List<GeograficoPoliticoMgl> findListCentroPoblado()  throws ApplicationException {
          return geograficoPoliticoManager.findListCentroPoblado();
     }
     
        
    /**
     * Metodo para consultar Lista centro poblados Autor: Victor Bocanegra
     * Autor: Victor Bocanegra
     *
     * @return {@link List<CmtGeograficoPoliticoDto>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    @Override
    public List<CmtGeograficoPoliticoDto> findAllCentroPoblados() throws ApplicationException {
        return geograficoPoliticoManager.findAllCentroPoblados();
    }
    
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
    @Override
    public MglResponseGeoPoliticoDto getListGeoPolByGroupId(MglRequestGeoPoliticoDto mglRequestGeoPoliticoDto) {
        return geograficoPoliticoManager.getListGeoPolByGroupId(mglRequestGeoPoliticoDto);
    }

    }
