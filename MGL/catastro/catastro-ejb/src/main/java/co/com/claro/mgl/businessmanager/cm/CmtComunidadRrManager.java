/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtComunidadRrDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadesRrDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public class CmtComunidadRrManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtComunidadRrManager.class);

    /**
     * valbuenayf Metodo para la comunidad y su regional
     *
     * @param idCiudad
     * @param codigoTecnologia
     * @return
     */
    public CmtComunidadRr findComunidadRegional(BigDecimal idCiudad, String codigoTecnologia) {
        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadRegional(idCiudad, codigoTecnologia);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }
    
        /**
     * Victor Bocanegra Metodo para trar la comunidad por el codigo RR
     *
     * @param codigoRR
     * @return CmtComunidadRr
     */
    public CmtComunidadRr findComunidadByCodigo(String codigoRR) {

        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadByCodigo(codigoRR);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }
    
    
            /**
     * Lenis Cardenas Metodo para trar la comunidad por el codigo de la comunidad RR
     *
     * @param comunidadRR
     * @return CmtComunidadRr
     */
    public CmtComunidadRr findComunidadByComunidad(String comunidadRR) {

        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadByCodigo(comunidadRR);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }
 
    /**
     * Victor Bocanegra Metodo para traer todas las comunidades
     *
     * @return List<CmtComunidadRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtComunidadRr> findAll() throws ApplicationException {

        CmtComunidadRrDaoImpl comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
        return comunidadRrDaoImpl.findAll();
    }
    
    /**
     * *Victor Bocanegra Metodo para buscar los Backlogs paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<CmtComunidadRr>
     * @throws ApplicationException
     */
    public PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadesRrDto consulta) throws ApplicationException {

        PaginacionDto<CmtComunidadRr> resultado = new PaginacionDto<CmtComunidadRr>();
        CmtComunidadRrDaoImpl dao = new CmtComunidadRrDaoImpl();

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        resultado.setNumPaginas(dao.countByComunidadFiltro(consulta));
        resultado.setListResultado(dao.findComunidadByFiltro(firstResult, maxResults, consulta));

        return resultado;

    }
    
    /**
     * *Victor Bocanegra Metodo para buscar las comunidades de una region
     *
     * @param idRegional
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    public List<CmtComunidadRr> findByIdRegional(BigDecimal idRegional)
            throws ApplicationException {

        CmtComunidadRrDaoImpl dao = new CmtComunidadRrDaoImpl();
        return dao.findByIdRegional(idRegional);
    }
    
    /**
     * *Victor Bocanegra Metodo para buscar una comunidad por id
     *
     * @param idComunidad
     * @return CmtComunidadRr
     * @throws ApplicationException
     */
    public CmtComunidadRr findByIdComunidad(BigDecimal idComunidad)
            throws ApplicationException {

        CmtComunidadRrDaoImpl dao = new CmtComunidadRrDaoImpl();
        return dao.find(idComunidad);
    }
    
    public List<CmtComunidadRr> findByListRegional(List<BigDecimal> idRegional) throws ApplicationException {
        CmtComunidadRrDaoImpl dao = new CmtComunidadRrDaoImpl();
        return dao.findByListRegional(idRegional);
    }

    public List<CmtComunidadRr> findByListComunidad(List<BigDecimal> idRegional) throws ApplicationException {
        CmtComunidadRrDaoImpl dao = new CmtComunidadRrDaoImpl();
        return dao.findByListComunidad(idRegional);
    }
}
