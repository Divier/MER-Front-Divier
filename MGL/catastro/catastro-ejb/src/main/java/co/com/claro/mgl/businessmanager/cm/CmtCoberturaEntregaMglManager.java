/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtCoberturaEntregaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroCoberturasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public class CmtCoberturaEntregaMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtCoberturaEntregaMglManager.class);

    /**
     * valbuenayf Metodo para buscar la lista de covertura_entrega de un centro
     * poblado
     *
     * @param idCentroPoblado
     * @return
     */
    public List<String> buscarListaCoberturaEntregaIdCentroPoblado(BigDecimal idCentroPoblado) {
        CmtCoberturaEntregaMglDaoImpl cmtCovEntMglDaoImpl;
        List<String> resulList;
        try {
            cmtCovEntMglDaoImpl = new CmtCoberturaEntregaMglDaoImpl();
            resulList = cmtCovEntMglDaoImpl.buscarListaCoberturaEntregaIdCentroPoblado(idCentroPoblado);

        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglManager " + e);
            return null;
        }
        return resulList;
    }

    /**
     * Obtiene las coberturas de entrega de los operadores logisticos por centro
     * poblado
     *
     * @author Juan David Hernandez
     * @param idCentroPoblado
     * @return listado de coberturas
     */
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoId(BigDecimal idCentroPoblado) {
        CmtCoberturaEntregaMglDaoImpl cmtCovEntMglDaoImpl;
        List<CmtCoberturaEntregaMgl> resulList;
        try {
            cmtCovEntMglDaoImpl = new CmtCoberturaEntregaMglDaoImpl();
            resulList = cmtCovEntMglDaoImpl.findCoberturaEntregaListByCentroPobladoId(idCentroPoblado);

        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglManager " + e);
            return null;
        }
        return resulList;
    }

    /**
     * Obtiene las coberturas de entrega de los operadores logisticos por centro
     * poblado
     *
     * @author Juan David Hernandez
     * @param maxResults
     * @return listado de coberturas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtFiltroCoberturasDto findCoberturaEntregaListByCentroPoblado(
            HashMap<String, Object> filtro, boolean contar, int firstResult, int maxResults)
            throws ApplicationException {
        try {
            CmtCoberturaEntregaMglDaoImpl cmtCovEntMglDaoImpl=new CmtCoberturaEntregaMglDaoImpl();
            CmtFiltroCoberturasDto cmtFiltroCoberturasDto=new CmtFiltroCoberturasDto();
            if (contar) {
                cmtFiltroCoberturasDto.setNumRegistros(
                        cmtCovEntMglDaoImpl.findCoberturaEntregaListByCentroPobladoFlitroCount(
                        filtro));
            } else {
                cmtFiltroCoberturasDto.setListaCmtCoberturaEntregaMgl(
                        cmtCovEntMglDaoImpl.findCoberturaEntregaListByCentroPobladoFlitro(filtro, firstResult, maxResults));
            }
            return cmtFiltroCoberturasDto;
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglManager " + e);
            return null;
        }
    }
    
    public List<CmtCoberturaEntregaMgl> findAll() throws ApplicationException {
        CmtCoberturaEntregaMglDaoImpl dao = new CmtCoberturaEntregaMglDaoImpl();
        return dao.findAllItems();
    }

    public CmtCoberturaEntregaMgl create(CmtCoberturaEntregaMgl t) throws ApplicationException {
        CmtCoberturaEntregaMglDaoImpl dao = new CmtCoberturaEntregaMglDaoImpl();
        return dao.create(t);
    }

    public CmtCoberturaEntregaMgl update(CmtCoberturaEntregaMgl t) throws ApplicationException {
        CmtCoberturaEntregaMglDaoImpl dao = new CmtCoberturaEntregaMglDaoImpl();
        return dao.update(t);
    }

    public boolean delete(CmtCoberturaEntregaMgl t) throws ApplicationException {
        CmtCoberturaEntregaMglDaoImpl dao = new CmtCoberturaEntregaMglDaoImpl();
        return dao.delete(t);
    }

    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoIdOperadorId(BigDecimal centroPobladoId, BigDecimal operadorID) {
        CmtCoberturaEntregaMglDaoImpl cmtCovEntMglDaoImpl;
        List<CmtCoberturaEntregaMgl> resulList;
        try {
            cmtCovEntMglDaoImpl = new CmtCoberturaEntregaMglDaoImpl();
            resulList = cmtCovEntMglDaoImpl.findCoberturaEntregaListByCentroPobladoIdOperadorId(centroPobladoId, operadorID);

        } catch (Exception e) {
            LOGGER.error("Error en findCoberturaEntregaListByCentroPobladoIdOperadorId de CmtCoberturaEntregaMglManager " + e);
            return null;
        }
        return resulList;
    }
}
