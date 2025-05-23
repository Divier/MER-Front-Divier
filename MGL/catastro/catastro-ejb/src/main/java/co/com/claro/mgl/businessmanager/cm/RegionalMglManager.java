/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtRegionalMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaRegionalDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author JPeña
 */
public class RegionalMglManager {

    private CmtRegionalMglDaoImpl regionalMglDaoImpl = new CmtRegionalMglDaoImpl();

    private static final Logger LOGGER = LogManager.getLogger(CmtRegionalMglDaoImpl.class);

    /**
     * Jonathan Peña Metodo para buscar los nodos paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<CmtRegionalRr>
     * @throws ApplicationException
     */
    public PaginacionDto<CmtRegionalRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaRegionalDto consulta) throws ApplicationException {

        PaginacionDto<CmtRegionalRr> resultado = new PaginacionDto<CmtRegionalRr>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        if (consulta != null) {
            resultado.setNumPaginas(regionalMglDaoImpl.countByNodFiltro(consulta));
            resultado.setListResultado(regionalMglDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        } else {
            consulta = new CmtFiltroConsultaRegionalDto();
            resultado.setNumPaginas(regionalMglDaoImpl.countByNodFiltro(consulta));
            resultado.setListResultado(regionalMglDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        }
        return resultado;
    }

    /**
     * Jonathan Peña Metodo para insercion de regionales
     *
     * @param regionalMgl objeto a insercion
     * @return regionalResult objeto que devuelve el intento de insercion.
     * @throws ApplicationException Excepcion lanzada el intento de insercion
     */
    public CmtRegionalRr create(CmtRegionalRr regionalMgl) throws ApplicationException {
        LOGGER.error("Creaando Regional:" + regionalMgl.getRegionalRrId());

        CmtRegionalRr regionalResult = null;

        try {
            regionalResult = regionalMglDaoImpl.findByCodigoRR(regionalMgl.getCodigoRr());
            if (regionalResult == null) {
                regionalResult = regionalMglDaoImpl.create(regionalMgl);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("No creado Regional:" + regionalMgl.getRegionalRrId() + "" + ex);
        }

        return regionalResult;
    }

    /**
     * Jonathan Peña Metodo para actualizacion de regionales
     *
     * @param regionalMgl objeto a actualizacion
     * @return regionalResult objeto que devuelve el intento de actualizacion.
     * @throws ApplicationException Excepcion lanzada el intento de
     * actualizacion
     */
    public CmtRegionalRr update(CmtRegionalRr regionalMgl) throws ApplicationException {

        return regionalMglDaoImpl.update(regionalMgl);
    }

    
    public CmtRegionalRr findById(CmtRegionalRr regionalMgl) throws ApplicationException {
        BigDecimal id= regionalMgl.getRegionalRrId();
        return regionalMglDaoImpl.find(id);
    }
    
    
    /**
     * Jonathan Peña Metodo para eliminacion de regionales
     *
     * @param regionalMgl objeto a eliminacion
     * @return eliminado booleano que devuelve el intento de eliminacion.
     * @throws ApplicationException Excepcion lanzada el intento de eliminacion
     */
    public boolean delete(CmtRegionalRr regionalMgl) throws ApplicationException {

        regionalMgl.setEstadoRegistro(0);
        boolean eliminado = false;
        try {
            regionalMglDaoImpl.update(regionalMgl);

            eliminado = true;
        } catch (ApplicationException ex) {
            LOGGER.error("No eliminado Regional:" + regionalMgl.getRegionalRrId() + "" + ex);
        }
        return eliminado;
    }

    /**
     *Jonathan Peña
     * retorna toda la lsita de regionales
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtRegionalRr> findAll()
            throws ApplicationException {
        List<CmtRegionalRr> resulList;
        CmtRegionalMglDaoImpl cmtBasicaMglDaoImpl = new CmtRegionalMglDaoImpl();
        resulList = cmtBasicaMglDaoImpl.findAll();
        return resulList;
    }

    /**
     *Jonathan Peña
     * busca una regional por codigo RR
     * @param codigoRR
     * @return resulList
     * @throws ApplicationException
     */
      public CmtRegionalRr findByCodigoRR(String codigoRR) throws ApplicationException{
      return regionalMglDaoImpl.findByCodigoRR(codigoRR);
      }
}
