/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtEstadoIntxExtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Relacion de Estados Internos y Externos. Contiene la logica de
 * negocio de la relacion de estados internos y externos en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtEstadoIntxExtMglManager {

    CmtEstadoIntxExtMglDaoImpl dao = new CmtEstadoIntxExtMglDaoImpl();
    
    public CmtEstadoIntxExtMgl createEstadoIntxExtMgl(CmtEstadoIntxExtMgl cmt, String usuario, int perfil) throws ApplicationException {
        cmt.setEstadoRegistro(1);
        return dao.createCm(cmt, usuario.toUpperCase(), perfil);
    }
    
    public CmtEstadoIntxExtMgl updateEstadoIntxExtMgl(CmtEstadoIntxExtMgl cmt, String usuario, int perfil) throws ApplicationException {
        return dao.updateCm(cmt, usuario.toUpperCase(), perfil);
    }

    /**
     * Obtiene la relacion estado interno externo. Obtiene la relacion estado
     * interno por estado externo por medio del estado interno.
     *
     * @author Johnnatan Ortiz
     * @param estadoInterno Estado Interno
     * @return Relacion de estado Interno y Externo
     * @throws ApplicationException
     */
    public CmtEstadoIntxExtMgl findByEstadoInterno(CmtBasicaMgl estadoInterno) throws ApplicationException {
        return dao.findByEstadoInterno(estadoInterno);
    }
    
        /**
     *
     * @author Juan David Hernandez
     * @param estadoInterno Estado Interno
     * @return Relacion de estado Interno y Externo
     * @throws ApplicationException
     */
    public CmtEstadoIntxExtMgl findByEstadoInternoXExterno(BigDecimal estadoInterno, 
            BigDecimal estadoExterno, int estadoRegistro) throws ApplicationException {
        return dao.findByEstadoInternoXExterno(estadoInterno,estadoExterno,estadoRegistro);
    }
    
        /**
     *
     * @param paginaSelected
     * @param maxResults
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtEstadoIntxExtMgl> findAllEstadoInternoExternoList(int paginaSelected, int maxResults) throws ApplicationException {
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return dao.findAllEstadoInternoExternoList(firstResult, maxResults);
    }
    
    /**
     *
     * @param paginaSelected
     * @param maxResults
     * @return resulList
     * @throws ApplicationException
     */
    public int countEstadoInternoExterno() throws ApplicationException {
        return dao.countEstadoInternoExterno();
    }
    
        
        /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtEstadoIntxExtMgl> findAll() throws ApplicationException {
        return dao.findAll();
    }
}
